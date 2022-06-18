package com.cp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
//import com.cm.dao.PaymentDAO;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.ImdDAO;
import com.cp.vo.ImdMakerVO;
import com.cm.dao.LoanInitiationDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ImdMakerProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ImdMakerProcessAction.class.getName());
	public ActionForward saveForReceipt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
//		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerID="";
		String bDate ="";
		String defaultBranch ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
			defaultBranch=userobj.getBranchId();
		}else{
			logger.info(" in saveForReceipt method of ReceiptMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("datatoapproveList");
		Object sessionId = session.getAttribute("sessionID");
		
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
	
		
		logger.info("in saveReceiptData..........");
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		DynaValidatorForm ImdMakerDynaValidatorForm = (DynaValidatorForm)form;
		//PaymentDAO Pdao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		//logger.info("Implementation class: "+Pdao.getClass());
		ImdMakerVO vo = new ImdMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ImdMakerDynaValidatorForm);
		
		logger.info("defaultBranch::::::::::::::"+defaultBranch);
	
		vo.setDefaultBranch(defaultBranch);
		vo.setMakerId(makerID);
		vo.setBusinessDate(bDate);
		logger.info("getBusinessDate():-"+vo.getBusinessDate());
		String receiptMode=CommonFunction.checkNull(vo.getReceiptMode());
		logger.info("receiptMode():-"+receiptMode);
		String existInstrumentNo="";
		
		if(CommonFunction.checkNull(receiptMode).equalsIgnoreCase("Q") || CommonFunction.checkNull(receiptMode).equalsIgnoreCase("D")){
		 existInstrumentNo=dao.existReceiptData(vo);
		 logger.info("existInstrumentNo -Q and D---"+existInstrumentNo);
		}
		else if(CommonFunction.checkNull(receiptMode).equalsIgnoreCase("N") || CommonFunction.checkNull(receiptMode).equalsIgnoreCase("R")){
	     existInstrumentNo=dao.existReceiptForNR(vo);
			 logger.info("existInstrumentNo-- N and R--- "+existInstrumentNo);
			}
		else
		{
			existInstrumentNo="0";
		} 
		
			
			
		 logger.info("existInstrumentNo after loop"+existInstrumentNo);
		// ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
		// request.setAttribute("bussinessPartnerList", bussinessPartnerList);
		boolean result=false;
		String reciptStatus="";
		String receiptInventoryStatus="";
		
		 String msg="";
		
		 ArrayList detailList = new ArrayList();
		 //change by sachin
		 reciptStatus=dao.checkReciptStatus(vo);
		 logger.info("reciptnumber status"+reciptStatus);
		//mradul changes
		 logger.info("receipt no12345::::::::::::::"+vo.getReceiptNo());
		 receiptInventoryStatus=dao.checkReciptStatusFromInventory(vo);
		 logger.info("in saveReceiptData reciptnumber status"+reciptStatus);
		 logger.info("in saveReceiptData receiptInventoryStatus "+receiptInventoryStatus);
		 
		if(existInstrumentNo.equals("0"))
		{
			//change by sachin
			
			if(reciptStatus.equalsIgnoreCase("0"))
			{
				//mradul changes 13 july 2013
				if(receiptInventoryStatus.equalsIgnoreCase("2"))
				{
					 msg="RNU";
					 logger.info("receiptInventoryStatus *****************"+ msg);
					 request.setAttribute("receiptInventoryStatus", msg);
					 ArrayList<ImdMakerVO> savedReceipt= new ArrayList<ImdMakerVO>();
					 savedReceipt.add(vo);
					 request.setAttribute("newCase","newCase");
					 request.setAttribute("savedReceipt", savedReceipt);
				}else{		
				if(!receiptInventoryStatus.equalsIgnoreCase("0")){
					result=dao.saveImdData(vo);
					logger.info("result"+result);
			}else{
				 
				 msg="RNI";
				 logger.info("receiptInventoryStatus *****************"+ msg);
				 request.setAttribute("receiptInventoryStatus", msg);
				 ArrayList<ImdMakerVO> savedReceipt= new ArrayList<ImdMakerVO>();
				 savedReceipt.add(vo);
				 request.setAttribute("newCase","newCase");
				 request.setAttribute("savedReceipt", savedReceipt);
			 }}
			}else{
				 
				 msg="RS";
				 request.setAttribute("reciptStatusNo", msg);
				 logger.info("Recipt number status"+msg);
				 ArrayList<ImdMakerVO> savedReceipt= new ArrayList<ImdMakerVO>();
			 	 savedReceipt.add(vo);
				 request.setAttribute("newCase","newCase");
				 request.setAttribute("savedReceipt", savedReceipt);
			 
			 
			}
			//end by sachin
			
			 if(result)
			 {
				 msg="S";
				 request.setAttribute("msg", msg);
				 logger.info("00msg"+msg);
				 String instrumentId=dao.getinstrumentId();
				 vo.setInstrumentID(instrumentId);
			// Start By Prashant	 
				 String amount=dao.getTotalRec(vo.getLbxLoanNoHID(),vo.getLbxBPType());
				 logger.info("Total Receivable   :  "+amount);
				 request.setAttribute("amount",amount);
			// End By Prashant	 
				ArrayList<ImdMakerVO> savedReceipt= dao.getImdList(vo);
				request.setAttribute("savedReceipt", savedReceipt);
				 request.setAttribute("canForward", "canForward");
				 
				 if(savedReceipt.size()>0)
				 {
					 request.setAttribute("loanRecStatus", savedReceipt.get(0).getLoanRecStatus());
					 if(defaultBranch.equalsIgnoreCase(savedReceipt.get(0).getLoanBranch()))
					 {
						 logger.info("login branch in if :::::::::::  "+defaultBranch);
						 logger.info("loan branch in if :::::::::::  "+savedReceipt.get(0).getLoanBranch());
						 request.setAttribute("loanBranchStatus", 'N');
					 }
					 else
					 {
						 logger.info("login branch in else:::::::::::  "+defaultBranch);
						 logger.info("loan branch in else :::::::::::  "+savedReceipt.get(0).getLoanBranch());
						 request.setAttribute("loanBranchStatus", 'Y');
					 }
				 }
			 }
			 else {
			msg="E";
		
		    logger.info("msg"+msg);
		}   
		}
		else{
			msg="D";
			detailList.add(vo);
			request.setAttribute("savedReceipt", detailList);
		    logger.info("msg"+msg);
		    
		}
		
		request.setAttribute("lbxDealNo", vo.getLbxDealNo());
		request.setAttribute("dealNo", vo.getDealNo());
		
		
		request.setAttribute("msg", msg);
		request.setAttribute("laonId", vo.getLbxLoanNoHID());
		ArrayList purposeList = dao.receiptPurposeList();
		request.setAttribute("purposeList", purposeList);
		return mapping.findForward("updateOnSave");
		
		// return mapping.getInputForward();
	}
	
	//method added by neeraj tripathi for generate receipt report
public ActionForward generateRecptReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
{	
	logger.info("In generateRecptReport");
	try
	{
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String cname ="";
		String username ="";
		String bDate ="";
		if(userobj!=null)
		{
			cname = userobj.getConpanyName();
			username=userobj.getUserName();
			bDate=userobj.getBusinessdate();
		}
		else
		{
			logger.info(" in generateRecptReport() session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
				if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
				else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}
				return mapping.findForward("logout");
		}
		String p_txnID=request.getParameter("frdLoanID");	
		String p_instrumentID=request.getParameter("frdInstrumentID");
		String p_printed_by=username+" ";
		String p_printed_date=CommonFunction.changeFormat(bDate);
		String p_company_name=cname+" ";
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String reportName="receiptMemoReport";
			
		Connection connectDatabase = ConnectionDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		hashMap.put("p_txnID",p_txnID);
		hashMap.put("p_instrumentID",p_instrumentID);
		hashMap.put("p_printed_by",p_printed_by);
		hashMap.put("p_printed_date",p_printed_date);
		hashMap.put("p_company_name",p_company_name);
		hashMap.put("p_company_logo",p_company_logo);
		logger.info("p_txnID  :  "+p_txnID);
		logger.info("p_instrumentID  :  "+p_instrumentID);
		logger.info("p_printed_by    :  "+p_printed_by);
		logger.info("p_printed_date  :  "+p_printed_date);
		logger.info("p_company_name  :  "+p_company_name);
		logger.info("p_company_logo  :  "+p_company_logo);
				
			
		InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream("/reports/" + reportName + ".jasper");
		JasperPrint jasperPrint = null;
		try
		{
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			finally
			{
				ConnectionDAO.closeConnection(connectDatabase, null);
				hashMap.clear();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	return null;
}
public void methodForPDF(Object reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
{
    JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
	File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
	FileInputStream fin = new FileInputStream(f);
	ServletOutputStream outStream = response.getOutputStream();
	response.setContentType("application/pdf");
	response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
	byte[] buffer = new byte[1024];
	int n = 0;
	while ((n = fin.read(buffer)) != -1) 
		outStream.write(buffer, 0, n);			
	outStream.flush();
	fin.close();
	outStream.close();
}		
       
	     
	public ActionForward onDirectForwardOnReceipt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("in onDirectForwardOnReceipt..........");
		HttpSession session=request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerID="";
		String bDate="";
		String defaultBranch="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
			defaultBranch=userobj.getBranchId();
			}
		else{
				logger.info(" in onDirectForwardOnReceipt method of ReceiptMakerProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
		Object sessionId = session.getAttribute("sessionID");
		
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		//added by neeraj
		String forwardInstrumentID=request.getParameter("forwardInstrumentID");
		String forwardLoanID=request.getParameter("forwardLoanID");
		request.setAttribute("forwardInstrumentID",forwardInstrumentID);
		request.setAttribute("forwardLoanID",forwardLoanID);		
		//neeraj space end
		
//		UserObject userobj=(UserObject)session.getAttribute("userobject");
		DynaValidatorForm ImdMakerDynaValidatorForm = (DynaValidatorForm)form;
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		ImdMakerVO receiptVO = new ImdMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(receiptVO, ImdMakerDynaValidatorForm);

		receiptVO.setMakerId(makerID);
		receiptVO.setBusinessDate(bDate);
		receiptVO.setDefaultBranch(defaultBranch);
		
	    boolean result=false;
		String msg="";
		String receiptInventoryStatus="";
		receiptInventoryStatus=dao.checkReciptStatusFromInventory(receiptVO);
		//mradul changes 13 july 2013
		if(receiptInventoryStatus.equalsIgnoreCase("2"))
		{
			 msg="RNU";
			 logger.info("receiptInventoryStatus *****************"+ msg);
			 request.setAttribute("receiptInventoryStatus", msg);
			 ArrayList<ImdMakerVO> savedReceipt= new ArrayList<ImdMakerVO>();
			 request.setAttribute("newCase","newCase");
			 request.setAttribute("savedReceipt", savedReceipt);
		}else{		
		if (!(receiptInventoryStatus.equalsIgnoreCase("0")))
		
			result=dao.saveForwardUpdateOnReceipt(receiptVO);
		else
			result=false;
		}
			logger.info("In receiptSaveForwardData,,,,,"+result);
		if(result){
			 msg="F";
			 logger.info("00msg"+msg);
			// Start By Prashant	 
			 String amount=dao.getTotalRec(receiptVO.getLbxLoanNoHID(),receiptVO.getLbxBPType());
			 logger.info("Total Receivable   :  "+amount);
			 request.setAttribute("amount",amount);
		// End By Prashant
		 }
	else{
		msg="E";
	
	    logger.info("msg1"+msg);
	    String msgInventry="RU";
		request.setAttribute("receiptInventoryStatus", msgInventry);
	   
	}
	request.setAttribute("msg", msg);
	logger.info("result"+result);
		
		//request.setAttribute("beforeForward", "beforeForward");
	//return mapping.findForward("receiptForward");
	return mapping.getInputForward();
}        
	public ActionForward updateReceiptSavedData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("in updateSavedData..........");
		
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
//		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerID="";
		String bDate ="";
		String defaultBranch ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
			defaultBranch=userobj.getBranchId();
		}else{
			logger.info(" in updateReceiptSavedData metohd of ReceiptMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		
		Object sessionId = session.getAttribute("sessionID");
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());   
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm)form;
		ImdMakerVO receiptVO = new ImdMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(receiptVO, ReceiptMakerDynaValidatorForm);

		receiptVO.setDefaultBranch(defaultBranch);
		receiptVO.setMakerId(makerID);
		receiptVO.setBusinessDate(bDate);

		String receiptMode=CommonFunction.checkNull(receiptVO.getReceiptMode());
		int existInstrumentNo=0;
		if(CommonFunction.checkNull(receiptMode).equalsIgnoreCase("Q") || CommonFunction.checkNull(receiptMode).equalsIgnoreCase("D")){
			 existInstrumentNo=dao.existInsNo(receiptVO);
			 logger.info("existInstrumentNo -Q and D---"+existInstrumentNo);
			}
			else if(CommonFunction.checkNull(receiptMode).equalsIgnoreCase("N") || CommonFunction.checkNull(receiptMode).equalsIgnoreCase("R")){
		     existInstrumentNo=dao.existInsNForNR(receiptVO);
				 logger.info("existInstrumentNo-- N and R--- "+existInstrumentNo);
				}
			else
			{
				existInstrumentNo=0;
			}
			
		
		
		 boolean result=false;
		 String reciptStatus="";
		//mradul starts
		 String receiptInventoryStatus="";
		 String msg="";
		 reciptStatus=dao.checkReciptUpdateStatus(receiptVO);
		 logger.info("recipt number status"+reciptStatus);
		 receiptInventoryStatus=dao.checkReciptStatusFromInventory(receiptVO);
		 logger.info("receiptInventoryStatus status"+receiptInventoryStatus);
		 if(existInstrumentNo == 0)
			{
			 //change by sachin
			 
			 if(reciptStatus.equalsIgnoreCase("0"))
			 {
				//mradul changes 13 july 2013
					if(receiptInventoryStatus.equalsIgnoreCase("2"))
					{
						 msg="RNU";
						 logger.info("receiptInventoryStatus *****************"+ msg);
						 request.setAttribute("receiptInventoryStatus", msg);
						 ArrayList<ImdMakerVO> savedReceipt= new ArrayList<ImdMakerVO>();
						 request.setAttribute("newCase","newCase");
						 request.setAttribute("savedReceipt", savedReceipt);
					}else{	
				 if(!receiptInventoryStatus.equalsIgnoreCase("0")){
				 result=dao.updateOnReceiptSave(receiptVO);
				 logger.info("In updateSavedData,,,,,"+result);	
				 }else{
					 
					 msg="RNI";
					 request.setAttribute("receiptInventoryStatus", msg);
					 ArrayList<ImdMakerVO> savedReceipt= dao.getImdList(receiptVO);
					 request.setAttribute("savedReceipt", savedReceipt);
					 request.setAttribute("canForward", "canForward");
				 }
				//mradul changes
				 }}
			 else
			 {
				msg="RS";
				logger.info("msg"+msg);
				request.setAttribute("reciptStatusNo", msg);
				ArrayList<ImdMakerVO> savedReceipt= dao.getImdList(receiptVO);
				 request.setAttribute("savedReceipt", savedReceipt);
				 request.setAttribute("canForward", "canForward");
						
			 }
			 //end by sachin
		 if(result){
		  msg="S";
		  logger.info("00msg"+msg);
//		  ArrayList<ReceiptMakerVO> savedReceipt=new ArrayList<ReceiptMakerVO>();
//			 savedReceipt.add(receiptVO);
		// Start By Prashant	 
			 String amount=dao.getTotalRec(receiptVO.getLbxLoanNoHID(),receiptVO.getLbxBPType());
			 logger.info("Total Receivable   :  "+amount);
			 request.setAttribute("amount",amount);
		// End By Prashant
			 
			
		  ArrayList<ImdMakerVO> savedReceipt= dao.getImdList(receiptVO);
			 request.setAttribute("savedReceipt", savedReceipt);
			 request.setAttribute("canForward", "canForward");
			 
			 if(savedReceipt.size()>0)
			 {
				
				 request.setAttribute("loanRecStatus", savedReceipt.get(0).getLoanRecStatus());
				 if(defaultBranch.equalsIgnoreCase(savedReceipt.get(0).getLoanBranch()))
				 {
					
					 request.setAttribute("loanBranchStatus", 'N');
				 }
				 else
				 {
					
					 request.setAttribute("loanBranchStatus", 'Y');
				 }
			 }
		 }
	else{
		msg="E";
		if(request.getParameter("loanRecStatus")!=null && !request.getParameter("loanRecStatus").equalsIgnoreCase(""))
		{
			 request.setAttribute("loanRecStatus", request.getParameter("loanRecStatus"));
		}
		
	    logger.info("msg1"+msg);
	}  
	}
		 else{
				msg="D";
				logger.info("msg2"+msg);
		 }
		 
	ArrayList purposeList = dao.receiptPurposeList();
	//logger.info(" In the newInstrument-"+purposeList.get(0));
	request.setAttribute("purposeList", purposeList);
	
	request.setAttribute("msg", msg);
	logger.info("result"+result);		 
	return mapping.findForward("updateOnSave");
} 
	
	public ActionForward receiptForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("in receiptForward..........");
		HttpSession session=request.getSession();
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerID="";
		String bDate="";
		String defaultBranch = "";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate= userobj.getBusinessdate();
			defaultBranch=userobj.getBranchId();
		}else{
			logger.info(" in receiptForward method of ReceiptMakerProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		//added by neeraj start
		String loanID=request.getParameter("loanID");
		String instrumentID=request.getParameter("instrumentID");
		request.setAttribute("forwardLoanID",loanID);
		request.setAttribute("forwardInstrumentID",instrumentID);
		// neeraj space end
//		UserObject userobj=(UserObject)session.getAttribute("userobject");
		DynaValidatorForm ImdMakerDynaValidatorForm = (DynaValidatorForm)form;
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		ImdMakerVO receiptVO = new ImdMakerVO();
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(receiptVO, ImdMakerDynaValidatorForm);

		receiptVO.setMakerId(makerID);
		receiptVO.setBusinessDate(bDate);
		receiptVO.setDefaultBranch(defaultBranch);
		logger.info("brancheieeiei::"+receiptVO.getDefaultBranch());
		String checkStatus=dao.checkFesiblityOnForward(receiptVO);
	
	    float amount=(float)Double.parseDouble(request.getParameter("amount"));

		String receiptMode=CommonFunction.checkNull(receiptVO.getReceiptMode());
		int existInstrumentNo=0;
		if(CommonFunction.checkNull(receiptMode).equalsIgnoreCase("Q") || CommonFunction.checkNull(receiptMode).equalsIgnoreCase("D")){
			 existInstrumentNo=dao.existInsNo(receiptVO);
			 logger.info("existInstrumentNo -Q and D---"+existInstrumentNo);
			}
			else if(CommonFunction.checkNull(receiptMode).equalsIgnoreCase("N") || CommonFunction.checkNull(receiptMode).equalsIgnoreCase("R")){
		     existInstrumentNo=dao.existInsNForNR(receiptVO);
				 logger.info("existInstrumentNo-- N and R--- "+existInstrumentNo);
				}
			else
			{
				existInstrumentNo=0;
			}
			 logger.info("existInstrumentNo after loop"+existInstrumentNo);
		
		
	//	int existInstrumentNo=dao.existInsNo( receiptVO);
		 logger.info("existInstrumentNo"+existInstrumentNo);
	    boolean result=false;
		String msg="";
		String resultproc="";
		ArrayList detailList = new ArrayList();
		if(existInstrumentNo == 0)
		{
		if(checkStatus.equalsIgnoreCase("DA")){
			msg="DA";
			detailList.add(receiptVO);
			request.setAttribute("savedReceipt", detailList);
			 request.setAttribute("canForward", "canForward");
			 logger.info("00msg--"+msg);
		}
		
		else if(checkStatus.equalsIgnoreCase("NA")){
			msg="NA";
			detailList.add(receiptVO);
			request.setAttribute("savedReceipt", detailList);
			request.setAttribute("canForward", "canForward");
			 logger.info("msssg--"+msg);
		}
		else if((checkStatus.equalsIgnoreCase("A"))){
		
		
			String checkDateQuery="select count(*) from cr_instrument_dtl I " +
            " inner join cr_imd_dtl P on I.INSTRUMENT_ID=P.INSTRUMENT_ID AND P.imd_date=I.RECEIVED_DATE " +
            " where I.INSTRUMENT_ID='"+CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()+"' AND INSTRUMENT_TYPE='R' ";

			logger.info("checkDateQuery: "+checkDateQuery);

			String checkDateCount=ConnectionDAO.singleReturn(checkDateQuery);

			logger.info("checkDateCount: "+checkDateCount);


			if(CommonFunction.checkNull(checkDateCount).equalsIgnoreCase("0"))
			{
				msg="CHECKDATE";
				detailList.add(receiptVO);
				request.setAttribute("savedReceipt", detailList);
				request.setAttribute("canForward", "canForward");
				logger.info("msssg--"+msg);
			}
			else
			{		
				resultproc=dao.saveForwardReceiptData(receiptVO,amount);
		        logger.info("In receiptSaveForwardData,,,,,: "+resultproc);
		        
	//	if(result){
		 if(CommonFunction.checkNull(resultproc).equalsIgnoreCase("queryexecuted")){
			 
			 // Prashant change here
			 String paramQuery="select PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='RECEIPT_AUTHORIZATION_FORWARD'";
			 String paramValue=ConnectionDAO.singleReturn(paramQuery);
			 logger.info("paramQuery: "+paramQuery+" paramValue:  "+paramValue);
		if(!CommonFunction.checkNull(paramValue).equalsIgnoreCase("")&& CommonFunction.checkNull(paramValue).equalsIgnoreCase("Y"))
		{
			receiptVO.setDecision("A");
			receiptVO.setComments("AUTHORIZED BY MAKER");
			logger.info("In receiptForward getInstrumentID()"+receiptVO.getInstrumentID());
			logger.info("In receiptForward getDecision"+receiptVO.getDecision());
			logger.info("In receiptForward getComments()"+receiptVO.getComments());
			 String procval="";
			 procval=dao.onSaveofImdAuthor(receiptVO);
			 logger.info("In receiptForward,,,,,"+procval);
//			 if(result){
			 if((procval!="")||!procval.equalsIgnoreCase("NONE"))
			 {
				 String recNoQuery=" SELECT RECIPT_NO FROM CR_INSTRUMENT_DTL WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()))+"'";
				    logger.info("recNoQuery: "+recNoQuery);
				    String receiptNo=ConnectionDAO.singleReturn(recNoQuery);
				    recNoQuery=null;
				 if((CommonFunction.checkNull(receiptNo)).trim().equalsIgnoreCase(""))
					{
					    receiptNo=null;
						String branchCodeQuery="SELECT BRANCH_SHORT_CODE FROM COM_BRANCH_M WHERE BRANCH_ID = (SELECT DEFAULT_BRANCH FROM CR_INSTRUMENT_DTL WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()))+"')";
						logger.info("branchCodeQuery: "+branchCodeQuery);
						String branchCode=ConnectionDAO.singleReturn(branchCodeQuery);
						branchCodeQuery=null;
						String generatedReceiptNo=receiptVO.getInstrumentID()+"/"+branchCode;
						String updateReceiptNoQuery="UPDATE CR_INSTRUMENT_DTL SET RECIPT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(generatedReceiptNo))+"'  WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()))+"'";
						logger.info("updateReceiptNoQuery: "+updateReceiptNoQuery);
						ArrayList list=new ArrayList();
						list.add(updateReceiptNoQuery);
						ConnectionDAO.sqlInsUpdDelete(list);
						generatedReceiptNo=null;
						branchCode=null;
						updateReceiptNoQuery=null;
						list.clear();
						list=null;
				   }
				 msg="A";
				 logger.info("00msg"+msg);
			 }
			 else{
					request.setAttribute("procvalForAuthor", procval);
					msg="E";
					logger.info("msg1"+msg);
					if(!request.getParameter("loanRecStatus").equalsIgnoreCase(""))
					{
						 request.setAttribute("loanRecStatus", request.getParameter("loanRecStatus"));
					}
			 }
		}
		else
		{
			 msg="F";
			 if(!request.getParameter("loanRecStatus").equalsIgnoreCase(""))
				{
					 request.setAttribute("loanRecStatus", request.getParameter("loanRecStatus"));
				}
			 logger.info("00msg"+msg);
			 //request.setAttribute("procval", resultproc);
		}
		
	 // prashant change here
			
		 }
		else{
			msg="E";
			request.setAttribute("procval", CommonFunction.checkNull(resultproc));
			detailList.add(receiptVO);
			request.setAttribute("savedReceipt", detailList);
		    logger.info("msg1"+msg);
		   
		}		
			
	}
		}
	}	
		else{
			msg="D";
			detailList.add(receiptVO);
			request.setAttribute("procval", CommonFunction.checkNull(resultproc));
			request.setAttribute("savedReceipt", detailList);
		    logger.info("msg"+msg);
		}
	
		// Start By Prashant	 
		 String amountReceiptForward=dao.getTotalRec(receiptVO.getLbxLoanNoHID(),receiptVO.getLbxBPType());
		 logger.info("Total Receivable   :  "+amountReceiptForward);
		 request.setAttribute("amount",amountReceiptForward);
	// End By Prashant	
	request.setAttribute("msg", msg);
	
		
	//request.setAttribute("beforeForward", "beforeForward");
	return mapping.getInputForward();
} 
	//Ritu
	public ActionForward deleteReceipt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In ReceiptMakerViewAction deleteReceipt().... ");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String branchId="";
		//String result="success";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				branchId=userobj.getBranchId();
		}else{
			logger.info("here in deleteReceipt () of ReceiptMakerViewAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();

		// String cond = request.getParameter("saveForward");
		// logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+cond);

		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
	
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		String instrumentID = "";

		instrumentID = request.getParameter("instrumentID");
		
		logger.info("In   deleteReceipt ----1-------------->instrumentID " + instrumentID);
		//logger.info("In ConsumerDispatchAction  execute id: " + userobj.getBranchId());

		ImdDAO service=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

		
		boolean status = service.deleteReceipt(instrumentID);
		String msg="";
        if(status){
        	msg="DS";
			request.setAttribute("msg",msg);
			 
		}
		else{
			msg="DN";
			request.setAttribute("msg",msg);
		}
       
		logger.info("In   deletePayment ----1-------------->status:-" + status);
		return mapping.findForward("SUCCESS");
	}
	
	public ActionForward imdDetailsGrid(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In searchDetail  ");
		
		HttpSession session = request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in Execute method of  ReceiptMakerSearch action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("receiptNoFlag");
		session.setAttribute("userId", userId);// for lov
		session.setAttribute("branchId", branchId);//for lov
		//boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");

		ImdMakerVO vo = new ImdMakerVO();
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		
		logger.info("Implementation class: "+dao.getClass());
        logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
	{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		String dealId="";
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setCurrentPageLink(currentPageLink);
	
			dealId = CommonFunction.checkNull(session.getAttribute("dealId"));
		CreditProcessingDAO creditProcessing=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		ArrayList dealHeader = creditProcessing.getDealHeader(dealId);
		session.setAttribute("dealHeader", dealHeader);
		
		
		logger.info("In openSectorType dealId " + dealId);
		vo.setDealNo(dealId);
		session.setAttribute("dealId", dealId);
		
		
		//dealNo = session.getAttribute("loanDealId").toString();
		logger.info("dealNo::::"+dealId);
		ArrayList<ImdMakerVO> imdDetailsGrid = dao.imdDetailsGrid(vo,dealId);
		request.setAttribute("list1", imdDetailsGrid);
		request.setAttribute("imdDetailsGrid","imdDetailsGrid");
		
		
//Neeraj Tripathi start
		//receiptMakerSearch.do?forward=forward&frdLoanID="+frdLoanID+"&frdInstrumentID="+frdInstrumentID;" +
		String forward=request.getParameter("forward");
		String search=request.getParameter("search");
		logger.info(" forward    "+forward);
		
		String frdLoanID="";
		String frdInstrumentID="";
		if(forward==null)
		{
			forward="No";
			frdLoanID="0";
			frdInstrumentID="0";
		}
		else
		{
			forward="Yes";
			frdLoanID=request.getParameter("frdLoanID");
			frdInstrumentID=request.getParameter("frdInstrumentID");
		}
		if(search!=null)
		{
			forward="No";
			frdLoanID="0";
			frdInstrumentID="0";
		}
		
		request.setAttribute("forward",forward);
		request.setAttribute("frdLoanID",frdLoanID);
		request.setAttribute("frdInstrumentID",frdInstrumentID);
//Neeraj Tripathi end
		return mapping.findForward("imdMakerSearch");	
	
	}

}
