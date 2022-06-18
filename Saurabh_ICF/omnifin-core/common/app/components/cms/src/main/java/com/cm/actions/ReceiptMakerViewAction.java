package com.cm.actions;

import java.util.ArrayList;
import java.math.BigDecimal;

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
import com.cm.dao.ReceiptDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cm.vo.ReceiptMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ReceiptMakerViewAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ReceiptMakerViewAction.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	public ActionForward allocateReceivableData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String uId=null;
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
		session.removeAttribute("charges");
		session.removeAttribute("otherAddCharges");
		session.removeAttribute("loanId");
		
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
		
	
		int loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		int instrumentId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("instrumentId")));
		
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));
		int bpId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("bpId")));
		
	    String amount=request.getParameter("amount");
	   
	    
	    String receiptamount=request.getParameter("receiptamount");
	   
	    String tdsAmount=request.getParameter("tdsAmount");
	
	   //By Manish Baranwal on 16-04-2014
	    //PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		ArrayList<ReceiptMakerVO> getReceivableList= dao.getViewReceivable(loanId, bpType,bpId,uId,amount,instrumentId,tdsAmount,receiptamount);
		if(getReceivableList.size()>0){
		String procval=getReceivableList.get(0).getProcVal();
	    logger.info("procval...."+procval);
		request.setAttribute("procval", procval);
		request.setAttribute("getReceivableList", getReceivableList);
		}
		
		
		request.setAttribute("loanId", loanId);
		request.setAttribute("bpType", bpType);
		request.setAttribute("amount",amount);
		request.setAttribute("instrumentId",instrumentId);
		request.setAttribute("tdsAmount",tdsAmount);
		request.setAttribute("canForward", "canForward");
		logger.info("getReceivableList    Size:---"+getReceivableList.size());
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
	    PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();
	    //Manish Baranwal
	    //PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
	    ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		int loanId=0;
		if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase("")){
			loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
		}
		logger.info("In viewReceivable---loanId-"+loanId); 
		String bpType=(CommonFunction.checkNull(request.getParameter("bpType")));

		int bpId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("bpId")));
	

		ArrayList<PaymentMakerForCMVO> viewReceivabList= dao.onViewReceivable(paymentMakerForCMVO,loanId, bpType,bpId);
		request.setAttribute("ReceivableList", viewReceivabList);
		
		//  Code by Rajesh Kumar - Start

		double totalOriginalAmount=0;
		double totalWaivedOffAmount=0;
		double totalAdviceAmount=0;
		double totalAdjustedAmount=0;
		double totalAmountInProcess=0;
		double totalBalanceAmount=0;

		for (PaymentMakerForCMVO paybleList : viewReceivabList) {
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
		return mapping.findForward("viewReceivable");
		
		
	}

	public ActionForward onSaveViewReceivable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		logger.info("onSaveViewReceivable   ");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
//		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerID=null;
		String bDate=null;
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
		//Manish Baranwal
		 //PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		DynaValidatorForm ReceiptMakerDynaValidatorForm = (DynaValidatorForm)form;
		ReceiptMakerVO receiptVO = new ReceiptMakerVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(receiptVO, ReceiptMakerDynaValidatorForm);

		receiptVO.setMakerId(makerID);
		receiptVO.setBusinessDate(bDate);
		int instrumentID=Integer.parseInt(CommonFunction.checkNull(request.getParameter("instrumentID")));
		
		receiptVO.setInstrumentID(CommonFunction.checkNull(request.getParameter("instrumentID")));
	    request.setAttribute("instrumentID", CommonFunction.checkNull(request.getParameter("instrumentID")));
	    request.setAttribute("lbxLoanNoHID", CommonFunction.checkNull(request.getParameter("lbxLoanNoHID")));
	    //String dataFromInstrumentDtlQuery="";
	    int loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("lbxLoanNoHID")));
	    //Manish 
	    String instrmtID=StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID).trim());
	    String txnID=StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim());
	    ArrayList dataFromInstrumentList=dao.getDataFromInstrumentList(instrmtID,txnID);
	   /* if(dbType.equalsIgnoreCase("MSSQL"))
	    {
	    	  dataFromInstrumentDtlQuery="select top 1 BPTYPE,BPID,INSTRUMENT_AMOUNT,TDS_AMOUNT from cr_instrument_dtl where INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID).trim())+"' AND TXN_TYPE='LIM' AND TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' ";
	 	      logger.info("dataFromInstrumentDtlQuery: "+dataFromInstrumentDtlQuery);
	    }
	    else
	    {
	    	  dataFromInstrumentDtlQuery="select BPTYPE,BPID,INSTRUMENT_AMOUNT,TDS_AMOUNT from cr_instrument_dtl where INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID).trim())+"' AND TXN_TYPE='LIM' AND TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' limit 1";
	 	      logger.info("dataFromInstrumentDtlQuery: "+dataFromInstrumentDtlQuery);
	    }*/
	   
	    ArrayList subDataInstrument=null;
	    String bpType="";
	    int bpId=0;
	    String amount="0.00";
	    String tdsAmount="0.00";
	    String receiptamount="0.00";
	    //ArrayList dataFromInstrumentList=ConnectionDAO.sqlSelect(dataFromInstrumentDtlQuery);
	    if(dataFromInstrumentList.size()>0)
	    {
	    	 subDataInstrument=(ArrayList)dataFromInstrumentList.get(0);
	    
		 bpType=(CommonFunction.checkNull(subDataInstrument.get(0)));
		 bpId=Integer.parseInt(CommonFunction.checkNull(subDataInstrument.get(1)));
		
	
	   // int uId=Integer.parseInt(userId);
		
		if(!CommonFunction.checkNull(subDataInstrument.get(2)).equalsIgnoreCase(""))
		{
			amount=subDataInstrument.get(2).toString();
		}
	    
	   
	    
	    
		if(!CommonFunction.checkNull(subDataInstrument.get(2)).equalsIgnoreCase(""))
		{
			receiptamount=subDataInstrument.get(2).toString();
		}
	 
	    
	   
		if(!CommonFunction.checkNull(subDataInstrument.get(3)).equalsIgnoreCase(""))
		{
			tdsAmount=subDataInstrument.get(3).toString();
		}
		
	   }
	
	
		boolean result=false;
		 String msg="";
		result= dao.saveViewReceivable(receiptVO);
		if(result)
		dao.getViewReceivable(loanId, bpType,bpId,makerID,amount,instrumentID,tdsAmount,receiptamount);
		
		 if(result){
			 msg="S";
			
		request.setAttribute("setID","sess");
		
		request.setAttribute("beforeForward", "beforeForward");
		 }
			else{
				msg="E";
			
			    
			   
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
		String businessDate="";
		if(userobj!=null){
			userId= userobj.getUserId();
			defaultBranch=userobj.getBranchId();
			businessDate=userobj.getBusinessdate();
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
		session.removeAttribute("loanId");
		session.removeAttribute("repoFlagMarked");
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
	
		//Manish
		//PaymentReceiptBusinessBeanRemote dao = (PaymentReceiptBusinessBeanRemote)LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request); 
		ReceiptDAO dao=(ReceiptDAO)DaoImplInstanceFactory.getDaoImplInstance(ReceiptDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());


		ReceiptMakerVO vo = new ReceiptMakerVO();
		vo.setLbxLoanNoHID(request.getParameter("lbxLoanNoHID"));
		vo.setInstrumentID(request.getParameter("instrumentID"));
		
		logger.info("In savedDataOfReceipt---status---- by getpara-"+request.getParameter("loanId"));  
		logger.info("In savedDataOfReceipt---instrumentID"+request.getParameter("instrumentID"));  
		
		String lbxLoanNoHID=request.getParameter("lbxLoanNoHID");
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
		flag = LockRecordCheck.lockCheck(userId,functionId,lbxLoanNoHID,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("msg", "Locked");
			request.setAttribute("recordId", lbxLoanNoHID);
			request.setAttribute("instrumentID", instrumentID);
			return mapping.findForward("receiptMakerSearch");
		}
		}
	    ArrayList<ReceiptMakerVO> savedReceipt= dao.getReceiptList(vo);
	    ReceiptMakerVO vodb=savedReceipt.get(0);
	    String bpType=vodb.getLbxBPType();
	    
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
	    
		//ArrayList purposeList = dao.receiptPurposeList();
		
		//request.setAttribute("purposeList", purposeList);
	    
		String receiptNoFlag=dao.receiptNoCheckFlag();
		session.setAttribute("receiptNoFlag",receiptNoFlag);
		session.setAttribute("loanId",lbxLoanNoHID);
	    request.setAttribute("bpType  :  ", bpType);
	    String amount=dao.getTotalRec(lbxLoanNoHID,bpType);
		request.setAttribute("amount",amount);
		
	   
		request.setAttribute("savedReceipt", savedReceipt);
		request.setAttribute("canForward", "canForward");
		
		 
		request.setAttribute("loanRecStatus", vodb.getLoanRecStatus());
		
		//Nishant space starts for charges
		//Manish Baranwawal
		String chargeFlagQuery=dao.getChargeReceipt();
		//String chargeFlagQuery=ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");
		if(CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y"))
		{
			ReceiptMakerVO receiptVO = new ReceiptMakerVO();
			receiptVO.setInstrumentID(instrumentID);
			ArrayList charges= new ArrayList();
			//charges = dao.getchargesDetailOnReceipt(receiptVO); by neeraj
			vo.setLbxBPType(bpType);
			vo.setBusinessDate(businessDate);
			charges = dao.getchargesDetailBeforeSave(vo);
			request.setAttribute("charges",charges);
			ArrayList otherAddCharges = dao.getOtherAddChargesDetailOnReceipt(receiptVO);
			request.setAttribute("otherAddCharges",otherAddCharges);
			ArrayList showTotal = dao.getshowTotalOnReceipt(receiptVO);
			request.setAttribute("showTotal",showTotal);
			logger.info("In charges: "+charges.size());
			String allocationChargeCode = dao.getAllocationChargeCode(vo.getLbxLoanNoHID());
			session.setAttribute("allocationChargeCode", allocationChargeCode);
		}
		//Nishant space end for charges
		String allocationGridReceipt=dao.getAllocationGridReceipt();
		session.setAttribute("allocationGridReceipt",allocationGridReceipt);
		String cashDepositFlag=dao.cashDepositFlag();
		String nonCashDepositFlag=dao.nonCashDepositFlag();
		session.setAttribute("cashDepositFlag",cashDepositFlag);
		session.setAttribute("nonCashDepositFlag",nonCashDepositFlag);
		String tdsreceiptStatus=dao.getTDSreceiptStatus();
		session.setAttribute("tdsreceiptStatus",tdsreceiptStatus);
		String saveForwardReceipt = dao.getsaveForwardReceipt();
		session.setAttribute("saveForwardReceipt", saveForwardReceipt);
		String repoFlag= dao.getRepoFlag(lbxLoanNoHID);
		session.setAttribute("repoFlagMarked", repoFlag);
		return mapping.findForward("SavedReceipt");
	}
	

}