package com.cm.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PaymentMakerForSaveAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(PaymentMakerForSaveAction.class.getName());

	public ActionForward saveAllData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		logger.info("In saveAllData .............. ");
		String makerID="";
		String bDate ="";
		String defaultBranch ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
			defaultBranch=userobj.getBranchId();
		}else{
			logger.info(" in saveAllData method of PaymentMakerForSaveAction action the session is out----------------");
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
			strFlag=null;
			return mapping.findForward("logout");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		session.removeAttribute("taStatus");
		//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		DynaValidatorForm PaymentCMDynaValidatorForm = (DynaValidatorForm)form;
		PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(paymentMakerForCMVO, PaymentCMDynaValidatorForm);
		if(paymentMakerForCMVO.getAuthorRemarks().contains("[Ljava.lang.String;")){
			paymentMakerForCMVO.setAuthorRemarks("");	
		}
		paymentMakerForCMVO.setDefaultBranch(defaultBranch);
		paymentMakerForCMVO.setMakerId(makerID);
		paymentMakerForCMVO.setBusinessDate(bDate);
		
		int existInstrumentNo=dao.existData(paymentMakerForCMVO);

		 ArrayList result=new ArrayList();
		 String msg="";
		 ArrayList detailList = new ArrayList();
	    //mradul starts
		 ArrayList<PaymentMakerForCMVO> payableList=new ArrayList<PaymentMakerForCMVO>();
		//	String paymentStatus="";
			String paymentInventoryStatus="1";
			if(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode()).equalsIgnoreCase("Q") || CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode()).equalsIgnoreCase("D"))
				paymentInventoryStatus=dao.checkPaymentStatusFromInventory(paymentMakerForCMVO);
		 	logger.info("paymentInventoryStatus : " + paymentInventoryStatus);
			String Inventorymsg="";
		
		//mradul changes 13 july 2013		
		if(existInstrumentNo==0)
		{		
			if(paymentInventoryStatus.equalsIgnoreCase("2"))
			{
				Inventorymsg="RNU";
				
				 request.setAttribute("paymentInventoryStatus", Inventorymsg);
				 payableList.add(paymentMakerForCMVO);
				 request.setAttribute("payableList", payableList);
				 request.setAttribute("canForward", "canForward");
			}else{
			if(!paymentInventoryStatus.equalsIgnoreCase("0")){
				result=dao.saveEnteredData(paymentMakerForCMVO);
			    logger.info("result"+result);
			 }else{			 
				 Inventorymsg="CNI";
				
				 request.setAttribute("paymentInventoryStatus", Inventorymsg);
				 payableList.add(paymentMakerForCMVO);
				 request.setAttribute("payableList", payableList);
				 request.setAttribute("canForward", "canForward");
			 }}		 
		  //mradul ends changes 13 july 2013
		 //Ankit change convert in proc 13 dec 2013	
			if(result.size()>0)
			{
				String stat=CommonFunction.checkNull(result.get(0));
				logger.info("stat "+stat);
				if(CommonFunction.checkNull(stat).equalsIgnoreCase("S"))
				{
//			 if(result){
				 msg="S";

				// String instrumentId=dao.getinstrumentId();
				 String instrumentId=CommonFunction.checkNull(result.get(1));
				
				 paymentMakerForCMVO.setInstrumentID(instrumentId);
				 
				 payableList.add(paymentMakerForCMVO);
				 request.setAttribute("payableList", payableList);
				 request.setAttribute("canForward", "canForward");
				 //Manish Baranwal
				 String loanID=CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID());
				 String loanRecStatus=dao.getLoanRecStatusPayment(loanID);
				 /*StringBuilder query = new StringBuilder();
				 String loanRecStatus="";
					try{
						query.append("select rec_status from cr_loan_dtl where loan_id="+CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID())+"");
						
						loanRecStatus=ConnectionDAO.singleReturn(query.toString());
				  		request.setAttribute("loanRecStatus", loanRecStatus);
				  	
						
				    }
				  	   catch(Exception e){
								e.printStackTrace();
							}finally{
								query=null;
							}*/
					request.setAttribute("loanRecStatus", loanRecStatus);
			 }
			}
			 else {				 
			 msg="E";		
		    
			 }		 
		}
		else{
			msg="D";
			detailList.add(paymentMakerForCMVO);
			request.setAttribute("payableList", detailList);
		 
		}		
		request.setAttribute("msg", msg);	
		request.setAttribute("laonId", paymentMakerForCMVO.getLbxLoanNoHID());
		if(CommonFunction.checkNull(paymentMakerForCMVO.getTaStatus()).equalsIgnoreCase("on"))
		{
 		    request.setAttribute("taStatus","Y");
		}
		form.reset(mapping, request);
		dao=null;
		makerID=null;
		bDate=null;
		defaultBranch=null;
		strFlag=null;
		paymentInventoryStatus=null;
		paymentInventoryStatus=null;
		return mapping.findForward("SUCCESS");
	}
	
	public ActionForward paymentForwardCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info(" in paymentForwardCheck method ----------------");
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		String defaultBranch="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
			defaultBranch=userobj.getBranchId();
		}else{
			logger.info(" in paymentForwardCheck method of PaymentMakerForSaveAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();
		
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
		
		DynaValidatorForm PaymentCMDynaValidatorForm = (DynaValidatorForm)form;
		PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(paymentMakerForCMVO, PaymentCMDynaValidatorForm);

		paymentMakerForCMVO.setMakerId(userId);
		paymentMakerForCMVO.setBusinessDate(bDate);
		paymentMakerForCMVO.setDefaultBranch(defaultBranch);		
	
		String amount=request.getParameter("amount");
		String tdsAmount=request.getParameter("tdsAmount");
		//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		String checkStatus=dao.checkOnPaymentForward(paymentMakerForCMVO);		
		int existInstrumentNo=dao.existInsNoinPay(paymentMakerForCMVO);	
		
		 String resultproc="";
		 String msg="";
		 ArrayList detailList = new ArrayList();
		 //changed by neeraj tripathi start
		 String frdLoanID=paymentMakerForCMVO.getLbxLoanNoHID();		
		 request.setAttribute("frdLoanID",frdLoanID);
		 //changed by neeraj tripathi end 
		 
if(existInstrumentNo == 0)
{
	if(checkStatus.equalsIgnoreCase("NA")){
		msg="NA";
		detailList.add(paymentMakerForCMVO);
		request.setAttribute("payableList", detailList);
		request.setAttribute("canForward", "canForward");
		//Manish
		String loanID=CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID());
		String loanRecStatus=dao.getLoanRecStatusPayment(loanID);
		 /*StringBuilder query = new StringBuilder();
		 String loanRecStatus="";
			try{
				query.append("select rec_status from cr_loan_dtl where loan_id="+CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID())+"");
				
				loanRecStatus=ConnectionDAO.singleReturn(query.toString());
		    }
		  	   catch(Exception e){
						e.printStackTrace();
					}*/
		request.setAttribute("loanRecStatus", loanRecStatus);
	}
	else if((checkStatus.equalsIgnoreCase("A"))){		

		String instrumentId=CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim();
		String checkDateCount=dao.getCheckDateCountPayment(instrumentId);
		//String checkDateQuery="select count(*) from cr_instrument_dtl I " +
				              //" inner join cr_pmnt_dtl P on I.INSTRUMENT_ID=P.INSTRUMENT_ID AND P.PMNT_DATE=I.RECEIVED_DATE " +
				              //" where I.INSTRUMENT_ID='"+CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()+"' AND INSTRUMENT_TYPE='P' ";
		
		//logger.info("checkDateQuery: "+checkDateQuery);
		
		//String checkDateCount=ConnectionDAO.singleReturn(checkDateQuery);		
		
		if(CommonFunction.checkNull(checkDateCount).equalsIgnoreCase("0"))
		{
			msg="CHECKDATE";
			detailList.add(paymentMakerForCMVO);
			request.setAttribute("payableList", detailList);
			request.setAttribute("canForward", "canForward");			
		}
		else
		{
			ArrayList<PaymentMakerForCMVO> payableList=new ArrayList<PaymentMakerForCMVO>();
			String paymentStatus="";
			String paymentInventoryStatus="1";
			if(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode()).equalsIgnoreCase("Q") || CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode()).equalsIgnoreCase("D"))
				paymentInventoryStatus=dao.checkPaymentStatusFromInventory(paymentMakerForCMVO);
			String Inventorymsg="";
			
			//mradul changes 13 july 2013
			if(paymentInventoryStatus.equalsIgnoreCase("2"))
			{
				Inventorymsg="RNU";			
				 request.setAttribute("paymentInventoryStatus", Inventorymsg);
				 payableList.add(paymentMakerForCMVO);
				 request.setAttribute("payableList", payableList);
				 request.setAttribute("canForward", "canForward");
			}else{	
			if(!paymentInventoryStatus.equalsIgnoreCase("0")){
				resultproc=dao.saveAllForwardedData(paymentMakerForCMVO,amount,tdsAmount);			  
			 }else{
				 Inventorymsg="CNI";
				
				 request.setAttribute("paymentInventoryStatus", Inventorymsg);
				 payableList.add(paymentMakerForCMVO);
				 request.setAttribute("payableList", payableList);
				 request.setAttribute("canForward", "canForward");
			 }}		 
		//mradul changes 13 july 2013		
//		 if(result){
		 if(resultproc.equalsIgnoreCase("queryexecuted")){			 
			 // Prashant change here
			 String paramValue=dao.getParamValuePayment();
			 //String paramQuery="select PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PAYMENT_AUTHORIZATION_FORWARD'";
			 //String paramValue=ConnectionDAO.singleReturn(paramQuery);
			 //logger.info("paramQuery: "+paramQuery+" paramValue:  "+paramValue);
		if(!CommonFunction.checkNull(paramValue).equalsIgnoreCase("")&& CommonFunction.checkNull(paramValue).equalsIgnoreCase("Y"))
		{
			paymentMakerForCMVO.setDecision("A");
			paymentMakerForCMVO.setComments("AUTHORIZED BY MAKER");
			
			 String procval="";
			 procval=dao.updateFlagByPaymentAuthor(paymentMakerForCMVO);
			 logger.info("In onSaveAuthor,,,,,"+procval);
//			 if(result){
			 if((procval!="")||!procval.equalsIgnoreCase("NONE"))
			 {
				 msg="A";
				 logger.info("00msg"+msg);
			 }
			 else{
					request.setAttribute("procvalForAuthor", procval);
					msg="E";
					String msgInventry="RU";
					request.setAttribute("paymentInventoryStatus", msgInventry);
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
		}
	 // prashant change here			 
		 }
		 else{
			 msg="E";
			 if(!resultproc.equalsIgnoreCase("F")){
				 request.setAttribute("procval", resultproc);
			 }
			detailList.add(paymentMakerForCMVO);
			request.setAttribute("payableList", detailList);			
		 }		
	  }
		
	}
}	
					else{
						msg="D";
						request.setAttribute("procval", resultproc);
						detailList.add(paymentMakerForCMVO);
						request.setAttribute("payableList", detailList);					  
					}
	request.setAttribute("msg", msg);
	if(session.getAttribute("taStatus")!=null)
	{
		 session.removeAttribute("taStatus");
	}
	if(CommonFunction.checkNull(paymentMakerForCMVO.getTaStatus()).equalsIgnoreCase("on"))
	{
		    request.setAttribute("taStatus","Y");
	}	
	
	form.reset(mapping, request);
	dao=null;
	paymentMakerForCMVO=null;
	return mapping.getInputForward();
} 
	
	public ActionForward updateSavedData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("in updateSavedData..........");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
	    String makerID="";
		String bDate ="";
		String defaultBranch ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			bDate=userobj.getBusinessdate();
			defaultBranch=userobj.getBranchId();
		}else{
			logger.info(" in updateSavedData method of PaymentMakerForSaveAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}		
		String sessionId = session.getAttribute("sessionID").toString();
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
		
		DynaValidatorForm PaymentCMDynaValidatorForm = (DynaValidatorForm)form;
		PaymentMakerForCMVO paymentVO=new PaymentMakerForCMVO();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(paymentVO, PaymentCMDynaValidatorForm);
	
	    paymentVO.setDefaultBranch(defaultBranch);
	    paymentVO.setMakerId(makerID);
	    paymentVO.setBusinessDate(bDate);
		
	    //PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
	    PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
	
		 boolean result=false;
		 String msg="";
		//mradul starts
		 ArrayList<PaymentMakerForCMVO> payableList=new ArrayList<PaymentMakerForCMVO>();
			String paymentStatus="";
			String paymentInventoryStatus="1";
			if(CommonFunction.checkNull(paymentVO.getPaymentMode()).equalsIgnoreCase("Q") || CommonFunction.checkNull(paymentVO.getPaymentMode()).equalsIgnoreCase("D"))
				paymentInventoryStatus=dao.checkPaymentStatusFromInventory(paymentVO);
			String Inventorymsg="";	 
		 
		//mradul changes 13 july 2013
			if(paymentInventoryStatus.equalsIgnoreCase("2"))
			{
				Inventorymsg="RNU";				
				 request.setAttribute("paymentInventoryStatus", Inventorymsg);
				 payableList.add(paymentVO);
				 request.setAttribute("payableList", payableList);
				 request.setAttribute("canForward", "canForward");
			}else{		
			if(!paymentInventoryStatus.equalsIgnoreCase("0")){
				result=dao.updateOnSave(paymentVO);
			 
			 }else{			 
				 Inventorymsg="CNI";
				
				 request.setAttribute("paymentInventoryStatus", Inventorymsg);
				 payableList.add(paymentVO);
				 request.setAttribute("payableList", payableList);
				 request.setAttribute("canForward", "canForward");
			 }}
		//mradul ends
		 if(result){
			 msg="S";
			 logger.info("00msg"+msg);			
			 payableList.add(paymentVO);
			 request.setAttribute("payableList", payableList);
			 request.setAttribute("canForward", "canForward");
			 String loanID=CommonFunction.checkNull(paymentVO.getLbxLoanNoHID());
			 String loanRecStatus="";
			 loanRecStatus=dao.getLoanRecStatusPayment(loanID);
			 /*StringBuilder query = new StringBuilder();
			 String loanRecStatus="";
				try{
					query.append("select rec_status from cr_loan_dtl where loan_id="+CommonFunction.checkNull(paymentVO.getLbxLoanNoHID())+"");
					
					loanRecStatus=ConnectionDAO.singleReturn(query.toString());
			   		request.setAttribute("loanRecStatus", loanRecStatus);
			  		
			    }
			  	   catch(Exception e){
							e.printStackTrace();
						}*/
		 }
	else{
		msg="E";
		if(!request.getParameter("loanRecStatus").equalsIgnoreCase(""))
		{
			 request.setAttribute("loanRecStatus", request.getParameter("loanRecStatus"));
		}	   
	}
	request.setAttribute("msg", msg);
	if(session.getAttribute("taStatus")!=null)
	{
		 session.removeAttribute("taStatus");
	}
	if(CommonFunction.checkNull(paymentVO.getTaStatus()).equalsIgnoreCase("on"))
	{
		    request.setAttribute("taStatus","Y");
	}
	
	form.reset(mapping, request);
	paymentVO=null;
	paymentStatus=null;
	paymentInventoryStatus=null;
	dao=null;
	return mapping.findForward("updateOnSave");
	}	
	
	public ActionForward paymentForwardedWithoutCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info(" in paymentForwardedWithoutCheck method ----------------");
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");

		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info(" in paymentForwardedWithoutCheck method of PaymentMakerForSaveAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String sessionId = session.getAttribute("sessionID").toString();
		
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
		
		DynaValidatorForm PaymentCMDynaValidatorForm = (DynaValidatorForm)form;
		PaymentMakerForCMVO paymentMakerForCMVO=new PaymentMakerForCMVO();	
		org.apache.commons.beanutils.BeanUtils.copyProperties(paymentMakerForCMVO, PaymentCMDynaValidatorForm);

		paymentMakerForCMVO.setMakerId(userId);
		paymentMakerForCMVO.setBusinessDate(bDate);
	
		String amount=request.getParameter("amount");	
		String tdsAmount=request.getParameter("tdsAmount");
	
		//PaymentReceiptBusinessBeanRemote dao=(PaymentReceiptBusinessBeanRemote) LookUpInstanceFactory.getLookUpInstance(PaymentReceiptBusinessBeanRemote.REMOTE_IDENTITY, request);
		PaymentDAO dao=(PaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PaymentDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		int existInstrumentNo=dao.existInsNoinPay(paymentMakerForCMVO);
	
		 boolean result=false;
		 String resultproc="";
		 String msg="";
		 ArrayList detailList = new ArrayList();
		 //changed by neeraj tripathi start
		 String frdLoanID=paymentMakerForCMVO.getLbxLoanNoHID();
	
		 request.setAttribute("frdLoanID",frdLoanID);
		 //changed by neeraj tripathi end 
		 
if(existInstrumentNo == 0)
{	
	 String payAmountCheck= dao.payableAmountCheck(paymentMakerForCMVO);
	 if(payAmountCheck.equalsIgnoreCase("S"))
	 {
		resultproc=dao.saveAllForwardedDataWithoutCheck(paymentMakerForCMVO,amount,tdsAmount);

		if(resultproc.equalsIgnoreCase("queryexecuted")){
			 
			 // Prashant change here
			 String paramValue=dao.getParamValuePayment();
			 //String paramQuery="select PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PAYMENT_AUTHORIZATION_FORWARD'";
			 //String paramValue=ConnectionDAO.singleReturn(paramQuery);
			 //logger.info("paramQuery: "+paramQuery+" paramValue:  "+paramValue);
		if(!CommonFunction.checkNull(paramValue).equalsIgnoreCase("")&& CommonFunction.checkNull(paramValue).equalsIgnoreCase("Y"))
		{
			paymentMakerForCMVO.setDecision("A");
			paymentMakerForCMVO.setComments("AUTHORIZED BY MAKER");
			
			 String procval="";
			 procval=dao.updateFlagByPaymentAuthor(paymentMakerForCMVO);

			 if((procval!="")||!procval.equalsIgnoreCase("NONE"))
			 {
				 msg="A";	
			 }
			 else{
					request.setAttribute("procvalForAuthor", procval);
					msg="E";		
			 }
		}
		else
		{
			 msg="F";
		}		
	 // prashant change here			 
		 }
		 else{
			 msg="E";
			 if(!resultproc.equalsIgnoreCase("F")){
				 request.setAttribute("procval", resultproc);
			 }
			detailList.add(paymentMakerForCMVO);
			request.setAttribute("payableList", detailList);		
		 }
	  }
	 else
	 {
		msg="VALIDPAYMENT";
		detailList.add(paymentMakerForCMVO);
		request.setAttribute("payableList", detailList);	
	}	
}	
					else{
						msg="D";
						request.setAttribute("procval", resultproc);
						detailList.add(paymentMakerForCMVO);
						request.setAttribute("payableList", detailList);
			
					}
	request.setAttribute("msg", msg);
	if(session.getAttribute("taStatus")!=null)
	{
		 session.removeAttribute("taStatus");
	}
	if(CommonFunction.checkNull(paymentMakerForCMVO.getTaStatus()).equalsIgnoreCase("on"))
	{
		    request.setAttribute("taStatus","Y");
	}
	
	form.reset(mapping, request);
	paymentMakerForCMVO=null;
	dao=null;
	return mapping.getInputForward();
} 	
	
}
