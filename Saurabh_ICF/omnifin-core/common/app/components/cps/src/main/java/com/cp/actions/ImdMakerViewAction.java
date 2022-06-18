package com.cp.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import com.cp.vo.ChargeVo;
import com.cm.vo.PaymentMakerForCMVO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.cp.dao.ImdDAO;
//import com.cm.vo.PaymentMakerForCMVO;
import com.cp.vo.ImdMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import java.math.BigDecimal;

public class ImdMakerViewAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ImdMakerViewAction.class.getName());
	
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	//change by sachin
	String dbType=resource.getString("lbl.dbType");

	public ActionForward allocateReceivableData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();

		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String uId="";
		if(userobj!=null){
			uId= userobj.getUserId();
		}else{
			logger.info(" in  allocateReceivableData method of  ReceiptMakerViewAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("datatoapproveList");
	  
		Object sessionId = session.getAttribute("sessionID");
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
		
		//ReceiptMakerVO receiptVO = new ReceiptMakerVO();
		logger.info("In  ReceiptMakerViewAction allocateReceivableData()----------------------------"); 
		logger.info("In  ReceiptMakerViewAction allocateReceivableData()--------loanId--------------------"+request.getParameter("loanId")); 
		logger.info("In  ReceiptMakerViewAction allocateReceivableData()----------instrumentId------------------"+request.getParameter("instrumentId")); 
		logger.info("In  ReceiptMakerViewAction tdsAmount()----------------------------"+request.getParameter("tdsAmount")); 
		logger.info("In  ReceiptMakerViewAction amount()----------------------------"+request.getParameter("amount")); 
		int loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		int instrumentId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("instrumentId")));
		logger.info("instrumentId...."+instrumentId);
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		int bpId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("bpId")));
		logger.info("bpId...."+bpId);
	
	   // int uId=Integer.parseInt(userId);
	    String amount=request.getParameter("amount");
	    logger.info("amount...."+amount);
	    
	    String receiptamount=request.getParameter("receiptamount");
	    logger.info("receiptamount...."+receiptamount);
	    String tdsAmount=request.getParameter("tdsAmount");
		logger.info("tdsAmount...."+tdsAmount);
	   
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		ArrayList viewReceivabList = dao.getAlocChargesDetail(""+instrumentId, ""+loanId);
		request.setAttribute("ReceivableList", viewReceivabList);
		logger.info("ReceivableList in DC:::::::"+viewReceivabList.size());
		
//		ArrayList<ImdMakerVO> getReceivableList= dao.getViewReceivable(loanId, bpType,bpId,uId,amount,instrumentId,tdsAmount,receiptamount);
//		if(getReceivableList.size()>0){
//		String procval=getReceivableList.get(0).getProcVal();
//	    logger.info("procval...."+procval);
//		request.setAttribute("procval", procval);
//		request.setAttribute("getReceivableList", getReceivableList);
//		}
		
		
		request.setAttribute("loanId", loanId);
		request.setAttribute("dealNo", loanId);
		request.setAttribute("bpType", bpType);
		request.setAttribute("amount",amount);
		request.setAttribute("receiptamount",receiptamount);
		request.setAttribute("instrumentId",instrumentId);
		request.setAttribute("tdsAmount",tdsAmount);
		request.setAttribute("canForward", "canForward");
//		logger.info("getReceivableList    Size:---"+getReceivableList.size());
		return mapping.findForward("allocateReceivable");
		
		
	}
	public ActionForward viewReceivable(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info("In  ReceiptMakerViewAction viewReceivable()----------------------------"); 
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in viewReceivable  method of ReceiptMakerViewAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		//boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");
		
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		ServletContext context = getServlet().getServletContext();
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
		
	 //   String userId=userobj.getUserId();
	 //   PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
	    ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		int loanId=0;
		if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase("")){
			loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		}
		logger.info("In viewReceivable---dealNo-"+loanId); 
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		logger.info("In viewReceivable---bpType-"+bpType); 
		int bpId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("bpId")));
		logger.info("bpId...."+bpId);
		
		ArrayList viewReceivabList = dao.getchargesDetail("DC", ""+loanId);
		request.setAttribute("ReceivableList", viewReceivabList);
		logger.info("ReceivableList in DC:::::::"+viewReceivabList.size());

		//  Code by Rajesh Kumar - Start

		double totalFinalAmount=0;

		ChargeVo chargeList;

		for (int i = 0; i < viewReceivabList.size(); i++){
			chargeList=(ChargeVo)viewReceivabList.get(i);
			if(null != chargeList.getChargeFinal())
			{
				totalFinalAmount += Double.valueOf(CommonFunction.checkNull(chargeList.getChargeFinal()).replaceAll(",",""));
			}
		}

		if(totalFinalAmount!=0)
		{
			request.setAttribute("printTotalAmounts", "printTotalAmounts");
			request.setAttribute("totalFinalAmount", BigDecimal.valueOf(totalFinalAmount).toPlainString());
		}

		

		//  Code by Rajesh Kumar - End
		
	//	ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
	//	request.setAttribute("bussinessPartnerList", bussinessPartnerList);
	//	ArrayList<PaymentMakerForCMVO> viewReceivabList= dao.onViewReceivable(paymentMakerForCMVO,loanId, bpType,bpId);
	//	request.setAttribute("ReceivableList", viewReceivabList);
	//	logger.info("ReceivableList"+viewReceivabList.size());
		return mapping.findForward("viewReceivable");
		
		
	}

	public ActionForward onSaveViewReceivable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("onSaveViewReceivable   ");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
//		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerID="";
		String bDate="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info(" in onSaveViewReceivable method of ReceiptMakerViewAction action the session is out----------------");
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
		
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm)form;
		ImdMakerVO receiptVO = new ImdMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(receiptVO, ReceiptMakerDynaValidatorForm);

		receiptVO.setMakerId(makerID);
		receiptVO.setBusinessDate(bDate);
		int instrumentID=Integer.parseInt(CommonFunction.checkNull(request.getParameter("instrumentID")));
		logger.info("instrumentId...."+instrumentID);
		receiptVO.setInstrumentID(CommonFunction.checkNull(request.getParameter("instrumentID")));
	    request.setAttribute("instrumentID", CommonFunction.checkNull(request.getParameter("instrumentID")));
	    request.setAttribute("lbxLoanNoHID", CommonFunction.checkNull(request.getParameter("lbxLoanNoHID")));
	    
	    int loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("lbxLoanNoHID")));
	    String dataFromInstrumentDtlQuery=null;
	    if(dbType.equalsIgnoreCase("MSSQL"))
	    {
	    	dataFromInstrumentDtlQuery="select top 1 BPTYPE,BPID,INSTRUMENT_AMOUNT,TDS_AMOUNT from cr_instrument_dtl where INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID).trim())+"' AND TXN_TYPE='DC' AND TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' ";
	    }
	    else
	    {
	    	dataFromInstrumentDtlQuery="select BPTYPE,BPID,INSTRUMENT_AMOUNT,TDS_AMOUNT from cr_instrument_dtl where INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID).trim())+"' AND TXN_TYPE='DC' AND TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' limit 1";
	    }
	    

	    logger.info("dataFromInstrumentDtlQuery: "+dataFromInstrumentDtlQuery);
	    ArrayList subDataInstrument=null;
	    String bpType="";
	    int bpId=0;
	    String amount="0.00";
	    String tdsAmount="0.00";
	    String receiptamount="0.00";
	    ArrayList dataFromInstrumentList=ConnectionDAO.sqlSelect(dataFromInstrumentDtlQuery);
	    if(dataFromInstrumentList.size()>0)
	    {
	    	 subDataInstrument=(ArrayList)dataFromInstrumentList.get(0);
	    
		 bpType=(CommonFunction.checkNull(subDataInstrument.get(0)));
		 bpId=Integer.parseInt(CommonFunction.checkNull(subDataInstrument.get(1)));
		logger.info("bpId...."+bpId);
	
	   // int uId=Integer.parseInt(userId);
		
		if(!CommonFunction.checkNull(subDataInstrument.get(2)).equalsIgnoreCase(""))
		{
			amount=subDataInstrument.get(2).toString();
		}
	    
	    logger.info("amount...."+amount);
	    
	    
		if(!CommonFunction.checkNull(subDataInstrument.get(2)).equalsIgnoreCase(""))
		{
			receiptamount=subDataInstrument.get(2).toString();
		}
	    logger.info("receiptamount...."+receiptamount);
	    
	   
		if(!CommonFunction.checkNull(subDataInstrument.get(3)).equalsIgnoreCase(""))
		{
			tdsAmount=subDataInstrument.get(3).toString();
		}
		logger.info("tdsAmount...."+tdsAmount);
	   }
	
	
		boolean result=false;
		 String msg="";
		result= dao.saveViewReceivable(receiptVO);
		if(result){
			ArrayList viewReceivabList = dao.getchargesDetail("DC", ""+loanId);
			request.setAttribute("ReceivableList", viewReceivabList);
			logger.info("ReceivableList in DC:::::::"+viewReceivabList.size());
		}
//		dao.getViewReceivable(loanId, bpType,bpId,makerID,amount,instrumentID,tdsAmount,receiptamount);
		 logger.info("result"+result);
		 if(result){
			 msg="S";
			 logger.info("00msg"+msg);		
		request.setAttribute("setID","sess");
		
		request.setAttribute("beforeForward", "beforeForward");
		 }
			else{
				msg="E";
			
			    logger.info("msg1"+msg);
			   
			}
			request.setAttribute("msg", msg);
			logger.info("result"+result);
			

		return mapping.findForward("SaveViewReceivable");
	}

	public ActionForward savedDataOfReceipt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("In savedDataOfReceipt........");
		boolean flag;
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String defaultBranch ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			defaultBranch=userobj.getBranchId();
		}
		else{
			logger.info(" in savedDataOfReceipt method of ReceiptMakerViewAction action the session is out----------------");
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
	
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		//ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
		//request.setAttribute("bussinessPartnerList", bussinessPartnerList);

		ImdMakerVO vo = new ImdMakerVO();
		vo.setLbxLoanNoHID(request.getParameter("lbxLoanNoHID"));
		vo.setLbxDealNo(request.getParameter("lbxDealNo"));
		vo.setInstrumentID(request.getParameter("instrumentID"));
		
		logger.info("In savedDataOfReceipt---status---- by getpara-"+request.getParameter("loanId"));  
		logger.info("In savedDataOfReceipt---instrumentID"+request.getParameter("instrumentID"));  
		
		String lbxLoanNoHID=request.getParameter("lbxLoanNoHID");
		String lbxDealNo=request.getParameter("lbxDealNo");
		String instrumentID=request.getParameter("instrumentID");
		
		logger.info("function id is ........................................"+session.getAttribute("functionId"));
		String functionId="";

		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,lbxDealNo,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", lbxDealNo);
			request.setAttribute("instrumentID", instrumentID);
			//request.setAttribute("userId", userId);
			return mapping.findForward("imdMakerSearch");
		}
		}
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000205")){
			String q1="select instrument_id from cr_instrument_dtl where TXNID='"+lbxDealNo+"' and TXN_TYPE='DC' AND REC_STATUS='P' ";
			instrumentID=ConnectionDAO.singleReturn(q1);
			request.setAttribute("instrumentID", instrumentID);
			vo.setInstrumentID(instrumentID);
		}
		ArrayList<ImdMakerVO> savedReceipt=new ArrayList<ImdMakerVO>();
		if(!CommonFunction.checkNull(instrumentID).equalsIgnoreCase("")){
	    savedReceipt= dao.getImdList(vo);
		}else{
			savedReceipt= dao.getpreDealImdList(vo);
		}
	    ImdMakerVO vodb=savedReceipt.get(0);
	    String bpType=vodb.getLbxBPType();
	    
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
	
		ArrayList purposeList = dao.receiptPurposeList();
		//logger.info(" In the newInstrument-"+purposeList.get(0));
		request.setAttribute("purposeList", purposeList);
	    
		String receiptNoFlag=dao.receiptNoCheckFlag();
		session.setAttribute("receiptNoFlag",receiptNoFlag);
	    
	    request.setAttribute("bpType  :  ", bpType);
	    String amount=dao.getTotalRec(lbxLoanNoHID,bpType);
		logger.info("Total Receivable   :  "+amount);
		request.setAttribute("amount",amount);
		
	    logger.info("savedReceipt    Size:---"+savedReceipt.size());
		request.setAttribute("savedReceipt", savedReceipt);
		request.setAttribute("canForward", "canForward");
		
		 
		request.setAttribute("loanRecStatus", vodb.getLoanRecStatus());
		

		return mapping.findForward("SavedReceipt");
	}
	public ActionForward viewAllocatedReceivable(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In viewAllocatedReceivable  ");
		
		HttpSession session = request.getSession();
	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		  String userId=null;
			if(userobj!=null){
				userId= userobj.getUserId();
			}
			else{
				logger.info(" in viewAllocatedReceivable method of ReceiptAuthorProcessAction action the session is out----------------");
				return mapping.findForward("sessionOut");
			}
		Object sessionId = session.getAttribute("sessionID");
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

		ImdMakerVO receiptVO = new ImdMakerVO();	
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		int loanId=0;
		if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase("")){
			loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		}			
		logger.info("In onAllocatedReceivableIMD---loanId-"+loanId); 
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		logger.info("In onAllocatedReceivableIMD---bpType-"+bpType); 
		int instrumentId=0;
		if(!CommonFunction.checkNull(request.getParameter("instrumentID")).equalsIgnoreCase("")){
		instrumentId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("instrumentID")));
		logger.info("instrumentId22222:::::::"+instrumentId);
	     }	
		ArrayList viewReceivabList = dao.getAlocChargesDetail(""+instrumentId, ""+loanId);
		request.setAttribute("ReceivableList", viewReceivabList);
		request.setAttribute("viewReceivableList", "viewReceivableList");
		logger.info("ReceivableList in DC:::::::"+viewReceivabList.size());
		
		return mapping.findForward("allocateReceivable");
		
		
	}
	

}