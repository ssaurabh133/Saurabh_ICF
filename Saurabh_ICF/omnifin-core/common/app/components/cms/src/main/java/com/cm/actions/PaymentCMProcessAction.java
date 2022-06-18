package com.cm.actions;

import java.util.ArrayList;
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
import com.cm.dao.PaymentDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import java.math.BigDecimal;

public class PaymentCMProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(PaymentCMProcessAction.class.getName());	
	
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	public ActionForward openEditpaymentMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("IN openEditpaymentMaker...");
		HttpSession session = request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId = "";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info(" in openEditPaymentMaker method of PaymentCMProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		boolean flag=false;
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
			strFlag=null;
			return mapping.findForward("logout");
		}
		PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
		String lbxLoanNoHID="";
	    lbxLoanNoHID=request.getParameter("lbxLoanNoHID");
	    String instrumentID="";
	    instrumentID=request.getParameter("instrumentID");
	    //String instrumentID=request.getParameter("instrumentID");
	//	logger.info("In openEditpaymentMaker---status---- by getpara-"+request.getParameter("lbxLoanNoHID")); 
	//	logger.info("In openEditpaymentMaker---instrumentID-"+request.getParameter("instrumentID")); 
		paymentMakerForCMVO.setLbxLoanNoHID(request.getParameter("lbxLoanNoHID"));
		paymentMakerForCMVO.setInstrumentID(request.getParameter("instrumentID"));
		//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
	//	logger.info("function id is ........................................"+session.getAttribute("functionId"));
		String functionId="";
		

		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
		flag = LockRecordCheck.lockCheck(userId,functionId,lbxLoanNoHID,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", lbxLoanNoHID);
			request.setAttribute("instrumentID", instrumentID);
			//request.setAttribute("userId", userId);
			return mapping.findForward("success");
		}
		}
		ArrayList<PaymentMakerForCMVO> bussinessPartnerList= dao.getbussinessPartner();
		request.setAttribute("bussinessPartnerList", bussinessPartnerList);
		//PaymentMakerForCMVO vo = new PaymentMakerForCMVO();
		ArrayList<PaymentMakerForCMVO> payableList= dao.searchPaymentData(paymentMakerForCMVO);
		logger.info("payableList    Size:---"+payableList.size());
		logger.info("TaStatus():---"+payableList.get(0).getTaStatus());
		if(CommonFunction.checkNull(payableList.get(0).getTaStatus()).equalsIgnoreCase("Y"))
		{
 		    request.setAttribute("taStatus","Y");
		}
		String allocatePayFlag=dao.allocatePayableCheckFlag();
		session.setAttribute("allocatePayFlag",allocatePayFlag);
		//Nishant space starts
		String paymentLimit=dao.cashPaymentLimit();
		session.setAttribute("cashPaymentLimit", paymentLimit);
		//Nishant space ends
		//start by Ravi
		if(payableList.size()>0)
		{
			request.setAttribute("loanRecStatus", CommonFunction.checkNull(payableList.get(0).getLoanRecStatus()).trim());
		}
		//end by Ravi
		request.setAttribute("payableList", payableList);
		request.setAttribute("canForward", "canForward");
		strFlag=null;
		functionId=null;
		return mapping.findForward("payable");
	}
	public ActionForward viewPayble(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info("In  PaymentCMProcessAction viewPayble()----------------------------");
		
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
	    String userId="";
	    if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info(" in  viewPayble method of PaymentCMProcessAction action the session is out----------------");
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
			strFlag=null;
			return mapping.findForward("logout");
		}
		

	    PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
	    //PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
	    PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		int loanId=0;
		logger.info(request.getParameter("loanId"));
		if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase("")){
			loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		}
	//	logger.info("In viewPayble---loanId-"+loanId); 
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
	//	logger.info("In viewPayble---loanId-"+bpType); 
		int bpId=0;
		if(!CommonFunction.checkNull(request.getParameter("bpId")).equalsIgnoreCase(""))
		bpId=Integer.parseInt(request.getParameter("bpId"));
		//logger.info("bpId...."+bpId);
		ArrayList<PaymentMakerForCMVO> getPayableList= dao.onViewPayable(paymentMakerForCMVO,loanId, bpType,bpId);
		request.setAttribute("viewPayableList", getPayableList);
		//logger.info("viewPayableList"+getPayableList.size());

		//  Code by Rajesh Kumar - Start
		
		double totalOriginalAmount=0;
		double totalWaivedOffAmount=0;
		double totalAdviceAmount=0;
		double totalAdjustedAmount=0;
		double totalAmountInProcess=0;
		double totalBalanceAmount=0;

		for (PaymentMakerForCMVO paybleList : getPayableList) {
			totalOriginalAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getOriginalAmount()).replaceAll(",",""));
			totalWaivedOffAmount +=Double.valueOf(CommonFunction.checkNull(paybleList.getWaiveOffAmount()).replaceAll(",",""));
			totalAdviceAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getAdviceAmount()).replaceAll(",",""));
			totalAdjustedAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getAdjustedAmount()).replaceAll(",",""));
			totalAmountInProcess += Double.valueOf(CommonFunction.checkNull(paybleList.getAmountInProcess()).replaceAll(",",""));
			totalBalanceAmount += Double.valueOf(CommonFunction.checkNull(paybleList.getBalanceAmount()).replaceAll(",",""));
		}
		
		
		request.setAttribute("printTotalAmounts", "printTotalAmounts");
		request.setAttribute("totalOriginalAmount", BigDecimal.valueOf(totalOriginalAmount).toPlainString());
		request.setAttribute("totalWaivedOffAmount", BigDecimal.valueOf(totalWaivedOffAmount).toPlainString());
		request.setAttribute("totalAdviceAmount", BigDecimal.valueOf(totalAdviceAmount).toPlainString());
		request.setAttribute("totalAdjustedAmount",BigDecimal.valueOf(totalAdjustedAmount).toPlainString());
		request.setAttribute("totalAmountInProcess",BigDecimal.valueOf( totalAmountInProcess).toPlainString());
		request.setAttribute("totalBalanceAmount",BigDecimal.valueOf(totalBalanceAmount).toPlainString());
		
		//  Code by Rajesh Kumar - End
		
		strFlag=null;
		bpType=null;
		userId=null;
		return mapping.findForward("viewPayable");
		
		
	}
	public ActionForward allocatePayble(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();

		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String uId="";
		if(userobj!=null)
		{
			uId=userobj.getUserId();
		}else{
			logger.info(" in allocatePayble method of PaymentCMProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
	  
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
			strFlag=null;
			return mapping.findForward("logout");
		}
		//PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
//		logger.info("In  PaymentCMProcessAction allocatePayble()----------------------------"); 
//		logger.info("In  PaymentCMProcessAction loanId()----------------------------"+request.getParameter("loanId")); 
//		logger.info("In  PaymentCMProcessAction instrumentId()----------------------------"+request.getParameter("instrumentId")); 
//		logger.info("In  PaymentCMProcessAction tdsAmount()----------------------------"+request.getParameter("tdsAmount")); 
//		logger.info("In  PaymentCMProcessAction paymentAmount()----------------------------"+request.getParameter("paymentAmount")); 
//		logger.info("In  PaymentCMProcessAction amount()----------------------------"+request.getParameter("amount")); 
		
		int loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		int instrumentId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("instrumentId")));
	//	logger.info("instrumentId...."+instrumentId);
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		int bpId=0;
		if(!CommonFunction.checkNull(request.getParameter("bpId")).equalsIgnoreCase(""))
		bpId=Integer.parseInt(request.getParameter("bpId"));
	//	logger.info("bpId...."+bpId);

		String amount=CommonFunction.checkNull(request.getParameter("amount"));
	//    logger.info("In  PaymentCMProcessAction Double amount...."+amount);

	    String tdsAmount=CommonFunction.checkNull(request.getParameter("tdsAmount"));
	//	logger.info("In  PaymentCMProcessAction tdsAmount...."+tdsAmount);
		
		String paymentAmount=CommonFunction.checkNull(request.getParameter("paymentAmount"));
	//	logger.info("In  PaymentCMProcessAction  paymentAmount...."+paymentAmount);
		
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
	//	logger.info("Implementation class: "+dao.getClass());
		ArrayList<PaymentMakerForCMVO> getPayableList= dao.getViewPayable(loanId, bpType,bpId,uId,amount,instrumentId,tdsAmount);
		if(getPayableList.size()>0){
			String procValue=getPayableList.get(0).getProcVal();	
			request.setAttribute("procval", procValue);
			request.setAttribute("getPayableList", getPayableList);
		}
		
		request.setAttribute("loanId", loanId);
		request.setAttribute("bpType", bpType);
		//request.setAttribute("adviseId", adviseId);
		request.setAttribute("canForward", "canForward");
		//logger.info("getPayableList    Size:---"+getPayableList.size());
		request.setAttribute("amount",amount);
		request.setAttribute("paymentAmount",paymentAmount);
		request.setAttribute("instrumentId",instrumentId);
		request.setAttribute("tdsAmount",tdsAmount);
		strFlag=null;
		return mapping.findForward("allocateViewPayable");
		
		
	}
	
	public ActionForward inCloseViewPayable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("closeViewPayable   ");
		
		HttpSession session = request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		//UserObject sessUser=(UserObject)session.getAttribute("userobject");
		String makerID="";
		String bDate ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info(" in inCloseViewPayable method of PaymentCMProcessAction action the session is out----------------");
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
			strFlag=null;
			return mapping.findForward("logout");
		}
		
		//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		DynaValidatorForm PaymentCMDynaValidatorForm = (DynaValidatorForm)form;
		PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(paymentMakerForCMVO, PaymentCMDynaValidatorForm);
		  
		//logger.info("### In inCloseViewPayable  tdsAllotedAmount size #### "+paymentMakerForCMVO.getTdsAllocatedArry().length);
	//	logger.info("### In inCloseViewPayable  tdsAllotedAmount  #### "+paymentMakerForCMVO.getTdsAllocatedArry().toString());
		  
		paymentMakerForCMVO.setMakerId(makerID);
		paymentMakerForCMVO.setBusinessDate(bDate);
	//	logger.info(" paymentMakerForCMVO.getBusinessDate():-"+paymentMakerForCMVO.getBusinessDate());
		boolean result=false;
		int instrumentID=Integer.parseInt(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()));
	//	logger.info("instrumentId...."+instrumentID);
		 int loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("lbxLoanNoHID")));
	//	    logger.info("loanId: "+loanId);
		    String instrumentId=StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim());
		    String loanID=StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim());
		   // ArrayList dataFromInstrumentList=dao.getDataFromInstrumentPayment(instrumentId, loanID);
		    String dataFromInstrumentDtlQuery="";
		    if(dbType.equalsIgnoreCase("MSSQL"))
		    {
		    	dataFromInstrumentDtlQuery="select top 1 BPTYPE,BPID,INSTRUMENT_AMOUNT,TDS_AMOUNT from cr_instrument_dtl where INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+"' AND TXN_TYPE='LIM' AND TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'";
			    logger.info("dataFromInstrumentDtlQuery: "+dataFromInstrumentDtlQuery);
		    }
		    else
		    {
		    	dataFromInstrumentDtlQuery="select BPTYPE,BPID,INSTRUMENT_AMOUNT,TDS_AMOUNT from cr_instrument_dtl where INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+"' AND TXN_TYPE='LIM' AND TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' limit 1";
			    logger.info("dataFromInstrumentDtlQuery: "+dataFromInstrumentDtlQuery);
		    }
		    
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
		 String msg="";
		 result= dao.closeViewPayable(paymentMakerForCMVO);
		 logger.info("result"+result);
		 if(result){
				dao.getViewPayable(loanId, bpType,bpId,makerID,amount,instrumentID,tdsAmount);
			 msg="S";
			 logger.info("00msg"+msg);		
		request.setAttribute("setID","sess");
		String pmntId=dao.getpmntId();
		request.setAttribute("pmntId",pmntId);		
		logger.info("pmntId"+pmntId);
		
		ArrayList<PaymentMakerForCMVO> payableList= dao.searchPaymentData(paymentMakerForCMVO);
		logger.info("payableList    Size:---"+payableList.size());
		request.setAttribute("payableList", payableList);
		request.setAttribute("beforeForward", "beforeForward");

			 }
		else{
			msg="E";
		
		    logger.info("msg1"+msg);
		   
		}
		request.setAttribute("msg", msg);
		logger.info("result"+result);
		makerID=null;
		bDate=null;
		strFlag=null;
		//dataFromInstrumentDtlQuery=null;
		return mapping.findForward("closeView");
	}
	
	//Ritu
	public ActionForward deletePayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In PaymentCMProcessAction deletePayment().... ");
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
			logger.info("here in deletePayment () of PaymentCMProcessAction action the session is out----------------");
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
			strFlag=null;
			return mapping.findForward("logout");
		}

		String instrumentID = "";

		instrumentID = request.getParameter("instrumentID");
		
		logger.info("In   deletePayment ----1-------------->instrumentID:-" + instrumentID);
		//logger.info("In ConsumerDispatchAction  execute id: " + userobj.getBranchId());

		
		PaymentDAO service=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		//PaymentReceiptBusinessBeanRemote service=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		logger.info("Implementation class: "+service.getClass());
		

		
		boolean status = service.deletePayment(instrumentID);
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
		bDate=null;
		branchId=null;
		strFlag=null;
		instrumentID=null;

		return mapping.findForward("payable");
		
	}
	public ActionForward setSuppManufIdInSessForPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	
		logger.info("In setSuppManufIdInSessForPayment Class ()- --------");
		
		HttpSession session =  request.getSession();
	
		String bpId = CommonFunction.checkNull(request.getParameter("bpId"));
		String bpType = CommonFunction.checkNull(request.getParameter("bpType"));
//		logger.info(" In supManufId---"+bpId);
//		logger.info(" In bpType---"+bpType);
		
		session.setAttribute("supManufId", bpId);
		session.setAttribute("bpType", bpType);
		return mapping.findForward("supManuf");
	}
}