/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cm.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.DisbursalInitiationDAO;
//import com.cm.dao.DisbursalInitiationDAOImpl;
import com.cm.vo.DisbursalMakerVO;
import com.cm.vo.DisbursalSearchVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.connect.DaoImplInstanceFactory;
/** 
 * MyEclipse Struts
 * Creation date: 06-06-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DisbursalSearchDispatchAction extends DispatchAction {
private static final Logger logger = Logger.getLogger(DisbursalSearchDispatchAction.class.getName());
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward openNewDisbursal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside DisbursalSearchDispatch........openNewDisbursal");
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		//String bussinessDate="";
		String userId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj!=null){
		//	bussinessDate=userobj.getBusinessdate();
			userId=userobj.getUserId();
		}
		else{
			logger.info(" in action openNewDisbursalthe method 0f DisbursalSearchDispatchAction session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		session.setAttribute("userId",userId);
		logger.info("userIDDDDDD------>>>>>>>" +userId);
		//for check User session start		
		String strFlag=null;	
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
		
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
		ArrayList<DisbursalMakerVO> cycleDate = service.getCycleDateList();
		
		
		session.removeAttribute("disbursalDataAuthor");
		session.removeAttribute("disbursalPaymentAddDtl");
		session.removeAttribute("fianlDisb");
		session.removeAttribute("cycleDate");
	
		session.removeAttribute("loanDisbursalId");
		request.setAttribute("disbursalNew","disbursalNew");
		request.setAttribute("cycleDate",cycleDate);
		return mapping.findForward("openNewDisbursal");
	}
	
	public ActionForward searchDisbursal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside DisbursalSearchDispatch........searchDisbursal");
		
		HttpSession session =  request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null){
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in searchDisbursal method of disbursalsearchDispatchAction the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		//boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");
		session.setAttribute("userId",userId);
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		DisbursalSearchVO vo = new DisbursalSearchVO();
		DynaValidatorForm DisbursalSearchDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DisbursalSearchDynaValidatorForm);
		String strFlag=null;

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
		int currentPageLink = 0;
		if(request.getParameter("d-5213373-p")==null || request.getParameter("d-5213373-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-5213373-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-5213373-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		
		String type = request.getParameter("type");
		String userID=request.getParameter("userId");
	
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
			vo.setReportingToUserId(userId);
		vo.setBranchId(branchId);
		vo.setStage(type);
		vo.setUserId(userID);
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
		ArrayList<DisbursalSearchVO> disbursalSearchList = service.searchDisbursalData(vo,type,request);
		
		if(disbursalSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("disbursalMaker","disbursalMaker");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("disbursalAuthor","disbursalAuthor");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("disbursalMaker","disbursalMaker");
				request.setAttribute("disbursalSearchList", disbursalSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("disbursalAuthor","disbursalAuthor");
				request.setAttribute("disbursalSearchList", disbursalSearchList);
			}
		}
		session.removeAttribute("loanDisbursalId");
		type=null;
		userID=null;
		return mapping.findForward("searchDisbursal");
	}
	
	public ActionForward searchDisbursalMakerLnik(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside DisbursalSearchDispatch........searchDisbursalMakerLnik");
		
		HttpSession session =  request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in searchDisbursalMakerLnik method of disbursal search dispatchAction the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		//boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");
		session.setAttribute("userId",userId);
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		DisbursalSearchVO vo = new DisbursalSearchVO();
		DynaValidatorForm DisbursalSearchDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DisbursalSearchDynaValidatorForm);
		
		String strFlag=null;	
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
		int currentPageLink = 0;
		if(request.getParameter("d-5213373-p")==null || request.getParameter("d-5213373-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-5213373-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-5213373-p"));
		
		vo.setCurrentPageLink(currentPageLink);
		String type = "P";
	
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		vo.setStage(type);
		
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
		String totalReceivableCurrShortPay = ConnectionDAO.singleReturn("select PARAMETER_VALUE from parameter_mst WHERE PARAMETER_KEY='TOTAL_RECEIVABLE_CURRENT_SHORT_PAY'");
		session.setAttribute("totalReceivableCurrShortPay", totalReceivableCurrShortPay);
		
		logger.info("totalReceivableCurrShortPay ************************ "+totalReceivableCurrShortPay);
		
		ArrayList<DisbursalSearchVO> disbursalSearchList = service.searchDisbursalData(vo,type,request);
		
		if(disbursalSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("disbursalMaker","disbursalMaker");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("disbursalAuthor","disbursalAuthor");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("disbursalMaker","disbursalMaker");
				request.setAttribute("disbursalSearchList", disbursalSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("disbursalAuthor","disbursalAuthor");
				request.setAttribute("disbursalSearchList", disbursalSearchList);
			}
		}
		session.removeAttribute("loanDisbursalId");
		type=null;
		userId=null;
		branchId=null;
		return mapping.findForward("searchDisbursal");
	}
	
	public ActionForward searchDisbursalAuthorLnik(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside DisbursalSearchDispatch........searchDisbursalAuthorLnik");
		
		HttpSession session =  request.getSession();		
		String userId="";
		String branchId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");		
		if(userobj!=null){
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info(" in searchDisbursalAuthorLnik method of disbursalSearchDispatchAction the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		//boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");
		logger.info("userId................" +userId);
		session.setAttribute("userId",userId);
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		DisbursalSearchVO vo = new DisbursalSearchVO();
		DynaValidatorForm DisbursalSearchDynaValidatorForm = (DynaValidatorForm)form;
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,DisbursalSearchDynaValidatorForm);
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
		logger.info("current page link ................ "+request.getParameter("d-5213373-p"));
		int currentPageLink = 0;
		if(request.getParameter("d-5213373-p")==null || request.getParameter("d-5213373-p").equalsIgnoreCase("0"))
		{
			
			currentPageLink=1;
		}
		else
		{
			
			currentPageLink =Integer.parseInt(request.getParameter("d-5213373-p").toString());
		}
		logger.info("current page link ................ "+request.getParameter("d-5213373-p"));
		logger.info("current page link ................ "+currentPageLink);
		
		vo.setCurrentPageLink(currentPageLink);
		
		String type = "F";
		
		
		vo.setBranchId(branchId);
		vo.setStage(type);
		vo.setUserId(userId);
		logger.info("userId................" +vo.getUserId());
		
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
			vo.setReportingToUserId(userId); 
		logger.info("user Id:::::"+vo.getReportingToUserId());
		
	
		
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
		ArrayList<DisbursalSearchVO> disbursalSearchList = service.searchDisbursalData(vo,type,request);
		
		if(disbursalSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("disbursalMaker","disbursalMaker");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("disbursalAuthor","disbursalAuthor");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("disbursalMaker","disbursalMaker");
				request.setAttribute("disbursalSearchList", disbursalSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("disbursalAuthor","disbursalAuthor");
				request.setAttribute("disbursalSearchList", disbursalSearchList);
			}
		}
		session.removeAttribute("loanDisbursalId");
		session.removeAttribute("loanId");
		type=null;
		userId=null;
		branchId=null;
		return mapping.findForward("searchDisbursal");
	}
	
	public ActionForward openDisbursalValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside DisbursalSearchDispatch........openDisbursalValues");
		
		HttpSession session =  request.getSession();		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
	//	String bussinessDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		//	bussinessDate=userobj.getBusinessdate();
		}else{
			logger.info(" in openDisbursalValues method of disbursalSearchDispatchAction the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");
		session.setAttribute("userId",userId);
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

		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String disbursalNo = CommonFunction.checkNull(request.getParameter("disbursalNo"));
		String loanDisbursalId = CommonFunction.checkNull(request.getParameter("loanDisbursalId"));
		logger.info("loanDisbursalId-------->"+loanDisbursalId);
		if(session.getAttribute("loanDisbursalId")!=null)
		{
			session.removeAttribute("loanDisbursalId");
		}
		else
		{
			session.setAttribute("loanDisbursalId", loanDisbursalId);
		}
		
		session.removeAttribute("disbursalDataAuthor");
		session.removeAttribute("disbursalPaymentAddDtl");
		session.removeAttribute("fianlDisb");
		session.removeAttribute("cycleDate");
	
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
		logger.info("function id is ........................................"+session.getAttribute("functionId"));
		String functionId="";

		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
			flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
			logger.info("Flag ........................................ "+flag);
			if(!flag)
			{
				logger.info("Record is Locked");			
				request.setAttribute("message", "Locked");
				request.setAttribute("recordId", loanId);
				request.setAttribute("disbursalMaker","disbursalMaker");
				return mapping.getInputForward();
			}
		}
		
			String revolvingFlag=service.getrevolvingFlag(loanId);
			session.setAttribute("revolvingFlag",revolvingFlag);
			String balancePrinc=service.getBalancePrinc(loanId);
			session.setAttribute("balancePrinc",balancePrinc);
			String forwardedAmt=service.getForwardedAmt(loanId);
			session.setAttribute("forwardedAmt",forwardedAmt);
			//Nishant space starts
			String recoveryType=service.getRecoveryType(loanId);
			request.setAttribute("recoveryType", recoveryType);
			String disbursalFlag=service.getDisbursalFlag(loanId,"DIM");
			request.setAttribute("disbursalFlag", disbursalFlag);
			String repaymentType=service.getRepaymentType(loanId);
			request.setAttribute("repaymentType", repaymentType);
			String installmentType=service.getInstallmentType(loanId);
			request.setAttribute("installmentType", installmentType);
			//Nishant space ends
			ArrayList<DisbursalMakerVO> cycleDate = service.getCycleDateList();
			request.setAttribute("cycleDate",cycleDate);
			String bp_type = ConnectionDAO.singleReturn("SELECT DISBURSAL_TO FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID="+loanId+" AND DISBURSAL_NO="+disbursalNo+" ");
			DisbursalMakerVO vo1 = new DisbursalMakerVO();
			/*ArrayList<DisbursalMakerVO> disbursalData = service.selectDisbursalData(loanId,disbursalNo,bussinessDate,bp_type);
		
			vo1=(DisbursalMakerVO) disbursalData.get(0);
			//logger.info("Disbursal Flag from Dispatch Action......openDisbursalValues: "+vo1.getFinalDisbursal());
			request.setAttribute("fianlDisb",vo1.getFinalDisbursal());
			request.setAttribute("disbursalData", disbursalData);
			request.setAttribute("disbursalTo", vo1.getDisbursalTo());
			request.setAttribute("maxDisbursalDate", CommonFunction.checkNull(vo1.getMaxDisbursalDate()));
			String disbursalTo ="";
			String disbursalToId ="";
			if(disbursalData.size()>0)
			{
				disbursalTo =CommonFunction.checkNull(disbursalData.get(0).getDisbursalTo().toString().trim());
				disbursalToId =CommonFunction.checkNull(disbursalData.get(0).getLbxBusinessPartnearHID().toString().trim());
				
				session.setAttribute("supManufId", disbursalToId);
				session.setAttribute("bpType", disbursalTo);
			}*/
			vo1.setLbxLoanNoHID(loanId);
			vo1.setDisbursalNo(disbursalNo);
			vo1.setLoanDisbursalId(loanDisbursalId);
			ArrayList disbursalPaymentAddDtl=service.selectAddDetailsList(vo1,"M");
			ArrayList loanAndDisbAmountList=service.getLoanAndDisburdesAmount(vo1);
			DisbursalMakerVO vo=(DisbursalMakerVO)loanAndDisbAmountList.get(0);
			logger.info("vo disbursed amount"+vo.getDisbursedAmount());
			logger.info("vo loan amount"+vo.getLoanAmt());
			request.setAttribute("loanAndDisbAmountList", loanAndDisbAmountList);	
			logger.info("Inside DisbursalSearchDispatch........openDisbursalValuesAuthor"+loanAndDisbAmountList.get(0));
			if(disbursalPaymentAddDtl.size()>0){
				request.setAttribute("disbursalPaymentAddDtl", disbursalPaymentAddDtl);	
				
				
			}		
			// Added by Rahul papneja| 06032018
			
			String getEditDueDate= service.getEditDueDateStatus(loanId);
			//request.setAttribute("getEditDueDate", getEditDueDate);
			logger.info("getEditDueDate: "+ getEditDueDate);
			request.setAttribute("editDueDate", getEditDueDate);
			logger.info("vo.getRepayEffDate(): "+ service.getRepayEffDate(loanId));
			String loanRepayEffDate= service.getRepyEffDateOfLoan(loanId);
			logger.info("loanRepayEffDate: "+ loanRepayEffDate);
			request.setAttribute("oldRepayEffDate", loanRepayEffDate);
			// start here | Brijesh Pathak
			String loanTenure= service.getLoanTenure(loanId);
			logger.info("loanTenure: "+ loanTenure);
			request.setAttribute("loanTenure", loanTenure);
			// end here | Brijesh Pathak

	  /*      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date loanRepayEffectiveDate = sdf.parse(loanRepayEffDate);
	       // SimpleDateFormat sd = new SimpleDateFormat("dd-mm-yyyy");
	        Date disbRepayEffectiveDate = sdf.parse(service.getRepayEffDate(loanId));
	        if (loanRepayEffectiveDate.compareTo(disbRepayEffectiveDate) != 0 && getEditDueDate.equalsIgnoreCase("Y")) {
	        	request.setAttribute("fwdStatusFlag", "N");
	        }
	        else
	        {
	        	request.setAttribute("fwdStatusFlag", "Y");
	        }*/


			// Ends Here
			
			
			
			
		//	return mapping.findForward("showDisbursalDataMaker");
			logger.info("Recovery type ::::::::::::::: " + request.getAttribute("recoveryType") + " test : " + recoveryType);
			
			userId=null;
			vo1=null;
			return mapping.findForward("showDisbursalDataMakerWithPayment");
		
	}
	
	public ActionForward openDisbursalValuesAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside DisbursalSearchDispatch........openDisbursalValuesAuthor");
		
		HttpSession session =  request.getSession();	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
	//	String bussinessDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
	//		bussinessDate=userobj.getBusinessdate();
		}else{
			logger.info(" in openDisbursalValuesAuthor method of disbursalSearchDispatchAction the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");
		session.setAttribute("userId",userId);
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag=null;	
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
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String disbursalNo = CommonFunction.checkNull(request.getParameter("disbursalNo"));
		String loanDisbursalId = CommonFunction.checkNull(request.getParameter("loanDisbursalId"));
		String batchId = CommonFunction.checkNull(request.getParameter("batchId"));
		
		String disbursalFinalFlagForAuthorQuery="Select DISBURSAL_FLAG FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID='"+loanId+"' ";
		
		logger.info("In openDisbursalValuesAuthor() :disbursalFinalFlagForAuthorQuery :==>>"+disbursalFinalFlagForAuthorQuery);
		String disbursalFinalFlagForAuthor = ConnectionDAO.singleReturn(disbursalFinalFlagForAuthorQuery);
		logger.info("In openDisbursalValuesAuthor() : disbursalFinalFlagForAuthor :==>> "+disbursalFinalFlagForAuthor);
		
		session.setAttribute("disbursalFinalFlagForAuthor", disbursalFinalFlagForAuthor);
		
	
		session.setAttribute("loanId", loanId);
		session.setAttribute("disbursalNo",disbursalNo);
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
		logger.info("function id is ........................................"+session.getAttribute("functionId"));
		String functionId="";

		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		
		
		//ServletContext context=getServlet().getServletContext();
		if(context!=null)
		{
			
		flag = LockRecordCheck.lockCheck(userId,functionId,loanId,context);
		logger.info("Flag ........................................ "+flag);
		if(!flag)
		{
			logger.info("Record is Locked");			
			request.setAttribute("message", "Locked");
			request.setAttribute("recordId", loanId);
			request.setAttribute("disbursalAuthor","disbursalAuthor");
			return mapping.getInputForward();
		}
		}
		DisbursalMakerVO vo1 = new DisbursalMakerVO();
		ArrayList<DisbursalMakerVO> cycleDate = service.getCycleDateList();
		session.setAttribute("cycleDate",cycleDate);
		String bp_type = ConnectionDAO.singleReturn("SELECT DISBURSAL_TO FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID="+loanId+" AND DISBURSAL_NO="+disbursalNo+" ");
		//ArrayList<DisbursalMakerVO> disbursalDataAuthor = service.selectDisbursalData(loanId,disbursalNo,bussinessDate,bp_type);
		//
		//vo1=(DisbursalMakerVO) disbursalDataAuthor.get(0);
		//logger.info("Disbursal Flag from Dispatch Action......openDisbursalValuesAuthor: "+vo1.getFinalDisbursal());
		//	logger.info("vo1.getDisbursalTo()......................... "+vo1.getDisbursalTo());
		//session.setAttribute("disbursalTo", vo1.getDisbursalTo());
		
		session.removeAttribute("loanDisbursalId");
		session.removeAttribute("disbursalPaymentAddDtl");
		//session.setAttribute("fianlDisb",vo1.getFinalDisbursal());
		//session.setAttribute("disbursalDataAuthor", disbursalDataAuthor);
		session.setAttribute("batchId", batchId);
		logger.info("loanDisbursalId: "+loanDisbursalId);
		session.setAttribute("loanDisbursalId", loanDisbursalId);
		vo1.setLoanDisbursalId(loanDisbursalId);
		vo1.setDisbursalBatchId(batchId); 
		vo1.setLbxLoanNoHID(loanId);
		ArrayList disbursalPaymentAddDtl=service.selectAddDetailsList(vo1,"A");
		session.setAttribute("disAuthor","disAuthor");
		logger.info("vo1.getDisbursalTo().............size............ "+disbursalPaymentAddDtl.size());
		if(disbursalPaymentAddDtl.size()>0){
			session.setAttribute("disbursalPaymentAddDtl", disbursalPaymentAddDtl);	
			
		}	
		session.setAttribute("disbursalInitionAuthor", "disbursalInitionAuthor");
		userId=null;
		bp_type=null;
		vo1=null;
		return mapping.findForward("showDisbursalDataAuthor");
	}
	public ActionForward deleteDisbursal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("deleteDisbursal()----------->");
		HttpSession session =  request.getSession();	
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
		String id=request.getParameter("loandisbursalid");
		boolean status=service.deletedisbursal(id);
		logger.info("status----------->"+status);
		ArrayList<DisbursalMakerVO> cycleDate = service.getCycleDateList();
		request.setAttribute("disbursalNew","disbursalNew");
		request.setAttribute("cycleDate",cycleDate);
		if(status){
			
			request.setAttribute("message", "D");
		}
		session.removeAttribute("loanDisbursalId");
		return mapping.findForward("openNewDisbursal");
	}
	//method added by neeraj tripathi
	public ActionForward generateDisbursalVoucherReport(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Inside generateDisbursalVoucherReport()");		
		HttpSession session =  request.getSession();	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
			userId=userobj.getUserId();
		else
			return mapping.findForward("sessionOut");
	//	boolean flag=false;
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
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
		String p_loan_id=request.getParameter("loanID");
		String p_user_id=userId;
		String p_company_logo=getServlet().getServletContext().getRealPath("/")+"reports/logo.bmp";
		String SUBREPORT_DIR=getServlet().getServletContext().getRealPath("/")+"reports\\";
		String reportName="subsequentDisbursementVoucher";
		
		logger.info("p_loan_id       :  "+p_loan_id);	
		logger.info("p_user_id       :  "+p_user_id);	
		logger.info("p_company_logo  :  "+p_company_logo);	
		logger.info("SUBREPORT_DIR   :  "+SUBREPORT_DIR);	
		
		Connection connectDatabase = ConnectionDAO.getConnection();		
		Map<Object,Object> hashMap = new HashMap<Object,Object>();
		hashMap.put("p_loan_id",p_loan_id);
		hashMap.put("p_user_id",p_user_id);
		hashMap.put("p_company_logo",p_company_logo);
		hashMap.put("SUBREPORT_DIR",SUBREPORT_DIR);
		
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
			userId=null;
		}
		return null;
	}
    //method added by neeraj tripathi
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
	/*Added By arun For new Disbursal With PAyment*/
	public ActionForward openNewDisbursalWithPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside DisbursalSearchDispatch........openNewDisbursalWithPayment");
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		String bussinessDate="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj!=null){
			bussinessDate=userobj.getBusinessdate();
		}
		else{
			logger.info(" in action openNewDisbursalthe method 0f DisbursalSearchDispatchAction session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start		
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
		
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
	    logger.info("Implementation class: "+service.getClass());
		ArrayList<DisbursalMakerVO> cycleDate = service.getCycleDateList();
		
		
		session.removeAttribute("disbursalDataAuthor");
		session.removeAttribute("disbursalPaymentAddDtl");
		session.removeAttribute("fianlDisb");
		session.removeAttribute("cycleDate");
		session.removeAttribute("disAuthor");
		
		session.removeAttribute("loanDisbursalId");
		request.setAttribute("disbursalNew","disbursalNew");
		request.setAttribute("cycleDate",cycleDate);
		return mapping.findForward("openNewDisbursalPayment");
	}
}