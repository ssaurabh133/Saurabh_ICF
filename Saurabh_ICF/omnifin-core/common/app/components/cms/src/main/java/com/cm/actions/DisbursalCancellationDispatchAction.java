package com.cm.actions;

import java.util.ArrayList;
import java.util.ListIterator;


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

import com.cm.dao.CancellationDAO;


import com.cm.dao.DisbCancellationDAO;

import com.cm.dao.EarlyClosureDAO;

import com.cm.dao.LoanInitiationDAO;
import com.cm.dao.MaturityClosureDAO;

import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureAuthorVO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.DisbCancellationVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class DisbursalCancellationDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(DisbursalCancellationDispatchAction.class.getName());
	
	public ActionForward disbCancellationMakerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside disbCancellationMakerSearch...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("DisbursaleCancellationDispatchAction action the session is out----------------");
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
		DisbCancellationVO vo = new DisbCancellationVO();
		vo.setCurrentPageLink(currentPageLink);

		String status="P";		
		DynaValidatorForm DisbCancellationSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DisbCancellationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);	
		DisbCancellationDAO service=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//DisbCancellationDAO service = new DisbCancellationDAOImpl();
		ArrayList<DisbCancellationVO> searchDataList = service.searchDisbCancellationData(vo,status);
		request.setAttribute("disbCancellationMakerSearch", "disbCancellationMakerSearch");
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);
		
		return mapping.findForward("searchLoanData");
	}
	
	
	public ActionForward disbCancellationAuthorSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside disbCancellationAuthorSearch...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("disbCancellationAuthorSearch action the session is out----------------");
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
		DisbCancellationVO vo = new DisbCancellationVO();
		vo.setCurrentPageLink(currentPageLink);

		String status="F";		
		DynaValidatorForm DisbCancellationSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DisbCancellationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		DisbCancellationDAO service=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//DisbCancellationDAO service = new DisbCancellationDAOImpl();
		ArrayList<DisbCancellationVO> searchDataList = service.searchDisbCancellationData(vo,status);
		request.setAttribute("disbCancellationAuthorSearch", "disbCancellationAuthorSearch");
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);		
		return mapping.findForward("searchLoanData");
	}
	public ActionForward openNewCancellation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("Inside openNewCancellation...........");
		HttpSession session =  request.getSession();
		session.removeAttribute("disbCancellationAuthorWithDetails");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here  session is out----------------");
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
		
		    String fwd="";
		
			fwd="openNewDisbCancellation";			
			request.setAttribute("newDisCancellationMaker","newDisCancellationMaker");		
		return mapping.findForward(fwd);
	}
	
	public ActionForward cancellationClosureAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside cancellationClosureAuthor...........");
		
		HttpSession session =  request.getSession();
		session.removeAttribute("disbCancellationAuthorWithDetails");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in cancellationClosureAuthor method of ClosureSearchDispatchAction action the session is out----------------");
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
		DisbCancellationVO vo = new DisbCancellationVO();
		vo.setCurrentPageLink(currentPageLink);
		String status="F";
		String type="X";
		DynaValidatorForm DisbCancellationSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DisbCancellationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		CancellationDAO service=(CancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(CancellationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//CancellationDAO service = new CancellationDAOImpl();
		ArrayList<ClosureSearchVO> searchDataList = service.searchClosureData(vo,status,type);
		request.setAttribute("cancellationClosureAuthor", "cancellationClosureAuthor");
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);

	
		return mapping.findForward("searchLoanData");
	}

	public ActionForward getDisbursalDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		logger.info("Inside getDisbursalDetails...........");
		
		HttpSession session =  request.getSession();
		session.removeAttribute("disbCancellationAuthorWithDetails");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in retriveDisbCancellationValues method of AjaxActionforCM  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		String sms="";	
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
		int loanId=Integer.parseInt(request.getParameter("loanId"));
		
		DisbCancellationDAO dao=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		//DisbCancellationDAO dao = new DisbCancellationDAOImpl();
		ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
		ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
		request.setAttribute("arrList1", arrList1);
		request.setAttribute("arrList2", arrList2);
		logger.info("loanDetailList    Size:---"+arrList1.size());
		logger.info("DisbursalDetailList    Size:---"+arrList2.size());
		if(arrList2.size()<=0 ){
			sms="ND";
			request.setAttribute("sms", sms);
		}
		request.setAttribute("DisCancellationMakerWithDetails", "DisCancellationMakerWithDetails");
		
		
		return mapping.findForward("showLoanDisbursalDetails");
	}
	
	public ActionForward saveCancellationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside ...........saveCancellationDetails ");
		
		HttpSession session =  request.getSession();
		session.removeAttribute("disbCancellationAuthorWithDetails");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String bDate ="";
		String billFlag="";
		String recType="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in saveCancellationDetails method of EarlyClosureDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}	
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		String sms="";
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
		
		DynaValidatorForm DisbCancellationDynaValidatorForm=(DynaValidatorForm)form;
		DisbCancellationVO vo = new DisbCancellationVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DisbCancellationDynaValidatorForm);
		
		
		int loanId=Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
		
		vo.setCancellationMakerID(makerId);
		vo.setCancellationMakerDate(bDate);		

		DisbCancellationDAO dao=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		//DisbCancellationDAO dao = new DisbCancellationDAOImpl();
		
		ArrayList<DisbCancellationVO> adviceList=dao.getAdviceDetails(vo);
		DisbCancellationVO vo1 = adviceList.get(0);
		String txnAmount=vo1.getTxnAdjustedAmt();
		String amtInProcess=vo1.getAmtInProcess();		
		logger.info("txnAmount"+txnAmount);
		logger.info("amtInProcess"+txnAmount);
		//TA Flag Value...
		ArrayList<DisbCancellationVO> taFlagDtl=dao.getTAFlagDetails(vo);
		String taFlag="";
		String taLoanNO="";
		String taLoanID="";
		logger.info("taFlagDtl size----------------"+taFlagDtl.size());
		if(taFlagDtl.size()>0)
		{
			DisbCancellationVO voTA = taFlagDtl.get(0);		
	        taLoanNO=voTA.getTaLoanNo();
	        taLoanID=voTA.getTaLoanID();
	        vo.setTaLoanID(taLoanID);
			logger.info("taLoanNO----------------"+taLoanNO);
			logger.info("taLoanID----------------"+taLoanID);
			//Check for advice
			ArrayList<DisbCancellationVO> taAdvice=dao.getAdviceTAFlag(vo);
			DisbCancellationVO voAdviceTA = taAdvice.get(0);
			taFlag=voAdviceTA.getTaFlag();
			logger.info("taFlag----------------"+taFlag);
		
		
		}
		//Billing Flag Value...
		ArrayList<DisbCancellationVO> disbFlagDtl=dao.getDisbursalFlag(vo);
		DisbCancellationVO voDisbFlag = disbFlagDtl.get(0);
		String disbFlag=voDisbFlag.getDisbFlag();
		logger.info("disbFlag----------------"+disbFlag);
		
		if(disbFlag.equalsIgnoreCase("F")){
		ArrayList<DisbCancellationVO> billingFlagDtl=dao.getBillFlagDetails(vo);
		DisbCancellationVO voBilling = billingFlagDtl.get(0);
		billFlag=voBilling.getBillFlag();
		recType=voBilling.getRecType();
		logger.info("billFlag----------------"+billFlag+"recType--------------"+recType);
		}
		
		
		
		 if(taFlag.equalsIgnoreCase("Y")){
			//TA Flag Check
			logger.info("In TA Flag Condition 1 ");
			sms="TA";
			ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
			ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
			logger.info("arrList1 size "+arrList1.size()+"arrList2 size "+arrList2.size());
			if(arrList2.size()>0)
			{
				request.setAttribute("checkDataSavedFlag", "N");				
			}
			request.setAttribute("arrList1", arrList1);
			request.setAttribute("arrList2", arrList2);
			request.setAttribute("taLoanNO", taLoanNO);
			
		}		 
		else if(billFlag.equalsIgnoreCase("Y")){
			//Billing Flag Check
			logger.info("In Billing Flag Condition 2");
			sms="B";
			ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
			ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
			logger.info("arrList1 size "+arrList1.size()+"arrList2 size "+arrList2.size());
			if(arrList2.size()>0)
			{
				request.setAttribute("checkDataSavedFlag", "N");				
			}
			request.setAttribute("arrList1", arrList1);
			request.setAttribute("arrList2", arrList2);
		 }
		else if(recType.equalsIgnoreCase("P")){
			//Billing Flag Check
			logger.info("In recType Flag Condition 2");
			sms="P";
			ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
			ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
			logger.info("arrList1 size "+arrList1.size()+"arrList2 size "+arrList2.size());
			if(arrList2.size()>0)
			{
				request.setAttribute("checkDataSavedFlag", "N");				
			}
			request.setAttribute("arrList1", arrList1);
			request.setAttribute("arrList2", arrList2);
		 }
		else if(txnAmount.equalsIgnoreCase("Y")||amtInProcess.equalsIgnoreCase("Y")){
			logger.info("In Advice Check Condition");
			sms="N";
			ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
			ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
			logger.info("arrList1 size "+arrList1.size()+"arrList2 size "+arrList2.size());
			if(arrList2.size()>0)
			{
				request.setAttribute("checkDataSavedFlag", "N");				
			}
			request.setAttribute("arrList1", arrList1);
			request.setAttribute("arrList2", arrList2);
			
		}
		else{
			logger.info("In Save Process.... Condition");
		    boolean status= dao.saveCancellationData(vo);
		    logger.info("status"+status);
			
			if(status)
			{
				logger.info("Cancellation Data Saved Successfuly.");
				sms="S";
				ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
				ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);				
				request.setAttribute("savedDetails", "savedDetails");
				request.setAttribute("arrList1", arrList1);
				request.setAttribute("arrList2", arrList2);
				request.setAttribute("checkDataSavedFlag", "Y");
				request.setAttribute("cancDateAvail", "cancDateAvail");
								
				logger.info("Inside EarlyClosureDispatchAction........SaveCancellation for Cancellation");
			}
			else{
				logger.info("Cancellation Data Not Saved.");
				ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
				ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
				request.setAttribute("arrList1", arrList1);
				request.setAttribute("arrList2", arrList2);		
				request.setAttribute("checkDataSavedFlag", "Y");
				sms="E";
			}
		}
		logger.info("In Process.....");	
		request.setAttribute("sms",sms);	
			request.setAttribute("DisCancellationMakerWithDetails", "DisCancellationMakerWithDetails");		
		return mapping.findForward("showLoanDisbursalDetails");
	}
	public ActionForward forwardCancellationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside ...........forwardCancellationDetails() ");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerId="";
		String bDate ="";
		String billFlag="";
		String recType="";
		if(userobj!=null){
			makerId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in forwardCancellationDetails method of EarlyClosureDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}	
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		String sms="";
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
		
		DynaValidatorForm DisbCancellationDynaValidatorForm=(DynaValidatorForm)form;
		DisbCancellationVO vo = new DisbCancellationVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DisbCancellationDynaValidatorForm);
		
		
		int loanId=Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
		
		vo.setCancellationMakerID(makerId);
		vo.setCancellationMakerDate(bDate);
		String fwd="showLoanDisbursalDetails";		

		DisbCancellationDAO dao=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		   // DisbCancellationDAO dao = new DisbCancellationDAOImpl();
		    
		    ArrayList<DisbCancellationVO> adviceList=dao.getAdviceDetails(vo);
			DisbCancellationVO vo1 = adviceList.get(0);
			String txnAmount=vo1.getTxnAdjustedAmt();
			String amtInProcess=vo1.getAmtInProcess();		
			logger.info("txnAmount"+txnAmount);
			//TA Flag Value...
			ArrayList<DisbCancellationVO> taFlagDtl=dao.getTAFlagDetails(vo);
			String taFlag="";
			String taLoanNO="";
			String taLoanID="";
			logger.info("taFlagDtl size----------------"+taFlagDtl.size());
			if(taFlagDtl.size()>0)
			{
				DisbCancellationVO voTA = taFlagDtl.get(0);		
		        taLoanNO=voTA.getTaLoanNo();
		        taLoanID=voTA.getTaLoanID();
		        vo.setTaLoanID(taLoanID);
				logger.info("taLoanNO----------------"+taLoanNO);
				logger.info("taLoanID----------------"+taLoanID);
				//Check for advice
				ArrayList<DisbCancellationVO> taAdvice=dao.getAdviceTAFlag(vo);
				DisbCancellationVO voAdviceTA = taAdvice.get(0);
				taFlag=voAdviceTA.getTaFlag();
				logger.info("taFlag----------------"+taFlag);
			
			
			}
			//Billing Flag Value...
			ArrayList<DisbCancellationVO> disbFlagDtl=dao.getDisbursalFlag(vo);
			DisbCancellationVO voDisbFlag = disbFlagDtl.get(0);
			String disbFlag=voDisbFlag.getDisbFlag();
			
			logger.info("disbFlag----------------"+disbFlag);
			
			if(disbFlag.equalsIgnoreCase("F")){
			ArrayList<DisbCancellationVO> billingFlagDtl=dao.getBillFlagDetails(vo);
			DisbCancellationVO voBilling = billingFlagDtl.get(0);
			billFlag=voBilling.getBillFlag();
			recType=voBilling.getRecType();
			logger.info("billFlag----------------"+billFlag+"recType---------"+recType);
			}
			
			
			
			 if(taFlag.equalsIgnoreCase("Y")){
				//TA Flag Check
				logger.info("In TA Flag Condition 1 ");
				sms="TA";
				ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
				ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
				logger.info("arrList1 size "+arrList1.size()+"arrList2 size "+arrList2.size());
				if(arrList2.size()>0)
				{
					request.setAttribute("checkDataSavedFlag", "N");				
				}
				request.setAttribute("arrList1", arrList1);
				request.setAttribute("arrList2", arrList2);
				request.setAttribute("taLoanNO", taLoanNO);
				request.setAttribute("DisCancellationMakerWithDetails", "DisCancellationMakerWithDetails");	
			}
			else if(billFlag.equalsIgnoreCase("Y")){
				//Billing Flag Check
				logger.info("In Billing Flag Condition 2");
				sms="B";
				ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
				ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
				logger.info("arrList1 size "+arrList1.size()+"arrList2 size "+arrList2.size());
				if(arrList2.size()>0)
				{
					request.setAttribute("checkDataSavedFlag", "N");				
				}
				request.setAttribute("arrList1", arrList1);
				request.setAttribute("arrList2", arrList2);
				request.setAttribute("DisCancellationMakerWithDetails", "DisCancellationMakerWithDetails");	
			 }
			else if(recType.equalsIgnoreCase("P")){
				//Rec Type Flag Check
				logger.info("In Rec Type Flag Condition 2");
				sms="P";
				ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
				ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
				logger.info("arrList1 size "+arrList1.size()+"arrList2 size "+arrList2.size());
				if(arrList2.size()>0)
				{
					request.setAttribute("checkDataSavedFlag", "N");				
				}
				request.setAttribute("arrList1", arrList1);
				request.setAttribute("arrList2", arrList2);
				request.setAttribute("DisCancellationMakerWithDetails", "DisCancellationMakerWithDetails");	
			 }
			else if(txnAmount.equalsIgnoreCase("Y")||amtInProcess.equalsIgnoreCase("Y")){
				sms="N";
				ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
				ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
				logger.info("arrList1 size "+arrList1.size()+"arrList2 size "+arrList2.size());
				if(arrList2.size()>0)
				{
					request.setAttribute("checkDataSavedFlag", "N");
				}
				request.setAttribute("arrList1", arrList1);
				request.setAttribute("arrList2", arrList2);
				
				request.setAttribute("DisCancellationMakerWithDetails", "DisCancellationMakerWithDetails");	
			}
			else{
			
		    boolean status= dao.forwardCancellationData(vo);
			
			if(status)
			{
				logger.info("Cancellation Data Saved Successfuly.");
				sms="FS";
				
				logger.info("Inside forwardCancellationDetails........ for Cancellation");
			}
			else{
				logger.info("Cancellation Data Not Saved.");
				request.setAttribute("DisCancellationMakerWithDetails", "DisCancellationMakerWithDetails");	
				request.setAttribute("savedDetails", "savedDetails");
				sms="FN";
				fwd="showLoanDisbursalDetails";
			}
			}
			logger.info("In Process....");
			request.setAttribute("sms",sms);					
		return mapping.findForward(fwd);
	}
	public ActionForward openNewCancellationWithValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("Inside ...........openNewCancellationWithValues() ");
		HttpSession session =  request.getSession();
		session.removeAttribute("disbCancellationAuthorWithDetails");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewCancellationWithValues method --- session is out----------------");
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
		
		    String fwd="";		
			fwd="showLoanDisbursalDetails";	
			int loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));
			DisbCancellationDAO dao=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			//DisbCancellationDAO dao = new DisbCancellationDAOImpl();
			ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
			ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetails(loanId);
			logger.info("arrList1 size "+arrList1.size()+"arrList2 size "+arrList2.size());
			if(arrList2.size()>0)
			{
				request.setAttribute("checkDataSavedFlag", "Y");
			}
			request.setAttribute("savedDetails", "savedDetails");					
			request.setAttribute("arrList1", arrList1);
			request.setAttribute("arrList2", arrList2);
			
			request.setAttribute("DisCancellationMakerWithDetails", "DisCancellationMakerWithDetails");			
		return mapping.findForward(fwd);
	}
	
	
	public ActionForward openCancellationAuthorWithValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("in openCancellationAuthorWithValues()...........");
		
		HttpSession session =  request.getSession();
		session.removeAttribute("disbCancellationAuthorWithDetails");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewCancellationWithValues method --- session is out----------------");
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
		
		    String fwd="";	
		    String sessionLoanId;
		    int loanId;
			fwd="disbCancellationAuthorWithDetails";
			loanId=Integer.parseInt(CommonFunction.checkNull(request.getParameter("loanId")));		
			logger.info("in openCancellationAuthorWithValues() loanId--- "+loanId+ "sessionLoanId"+loanId);
			DisbCancellationDAO dao=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			//DisbCancellationDAO dao = new DisbCancellationDAOImpl();
			ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
			ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetailsInAuthor(loanId);				
			//request.setAttribute("disbCancellationAuthor", "disbCancellationAuthor");
			session.setAttribute("arrList1", arrList1); 
			session.setAttribute("arrList2", arrList2);
			session.setAttribute("sessionLoanId", loanId);
			session.setAttribute("disbCancellationAuthorWithDetails", "disbCancellationAuthorWithDetails");	
			
			logger.info("arrList1 size : "+arrList1.size());
		return mapping.findForward(fwd);
	}
	
	public ActionForward openCancellationMakerOnAtuhor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("in openCancellationMakerOnAtuhor()...........");
		
		HttpSession session =  request.getSession();
		session.removeAttribute("disbCancellationAuthorWithDetails");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openCancellationMakerOnAtuhor method --- session is out----------------");
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
		
		    String fwd="";	
		    String sessionLoanId;
		    int loanId;
			fwd="openCancellationMakerOnAuthor";
			
		    loanId=Integer.parseInt(CommonFunction.checkNull(session.getAttribute("sessionLoanId")).toString());
			
			logger.info("in openCancellationAuthorWithValues() loanId--- "+loanId+ "sessionLoanId"+loanId);
			DisbCancellationDAO dao=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass()); 
			//DisbCancellationDAO dao = new DisbCancellationDAOImpl();
			ArrayList<DisbCancellationVO> arrList1= dao.getLoanDetails(loanId);
			ArrayList<DisbCancellationVO> arrList2= dao.getDisbursalDetailsInAuthor(loanId);				
			//request.setAttribute("disbCancellationAuthor", "disbCancellationAuthor");
			session.setAttribute("arrList1", arrList1); 
			session.setAttribute("arrList2", arrList2);
			session.setAttribute("sessionLoanId", loanId);
			session.setAttribute("disbCancellationAuthorWithDetails", "disbCancellationAuthorWithDetails");	
			
			logger.info("arrList1 size : "+arrList1.size());
		return mapping.findForward(fwd);
	}
	
	
	public ActionForward openAuthorPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		
		logger.info("In openAuthorPage()............");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String businessDate="";
		if(userobj != null)
		{
			businessDate=userobj.getBusinessdate();
		}
		else
		{
			logger.info("here in openAuthorPage the session is out----------------");
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
		
		return mapping.findForward("openCancellationAuthor");
	}
	
	public ActionForward saveCancellationAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String bDate ="";
		int cID =0;
		String billFlag="";
		String recType="";
		if(userobj!=null){
			userId = userobj.getUserId();
			bDate=userobj.getBusinessdate();
			cID=userobj.getCompanyId();
		}else{
			logger.info("here in saveCancellationAuthor action the session is out----------------");
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
		
		logger.info("saveCancellationAuthor strFlag .............. "+strFlag);
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
		DynaValidatorForm DisbCancellationDynaValidatorForm = (DynaValidatorForm) form;
		DisbCancellationVO vo = new DisbCancellationVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DisbCancellationDynaValidatorForm);	
		
		int loanId=Integer.parseInt(CommonFunction.checkNull(session.getAttribute("sessionLoanId")).toString());
		logger.info("Inside saveCancellationAuthor.........loan id: "+loanId);

		vo.setCompanyId(cID);
		vo.setAuthorId(userId);
		vo.setAuthorDate(bDate);
		vo.setLbxLoanNoHID(String.valueOf(loanId));
		DisbCancellationDAO service=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//DisbCancellationDAO service = new DisbCancellationDAOImpl();
		ArrayList<DisbCancellationVO> adviceList=service.getAdviceDetailsOnAuthor(vo);
		DisbCancellationVO vo1 = adviceList.get(0);
		String txnAmount=vo1.getTxnAdjustedAmt();
		String amtInProcess=vo1.getAmtInProcess();		
		logger.info("txnAmount"+txnAmount);
		logger.info("amtInProcess"+amtInProcess);
	   logger.info("Decision----------"+vo.getDecision());	   
	 
		//Disb-Billing Flag Value...
		ArrayList<DisbCancellationVO> disbFlagDtl=service.getDisbursalFlag(vo);
		DisbCancellationVO voDisbFlag = disbFlagDtl.get(0);
		String disbFlag=voDisbFlag.getDisbFlag();			
		logger.info("disbFlag----------------"+disbFlag);
		//TA Flag Value...			
		ArrayList<DisbCancellationVO> taFlagDtl=service.getTAFlagDetailsAuthor(vo);
		//ArrayList<DisbCancellationVO> taFlagDtl=service.getTAFlagDetails(vo);
		String taFlag="";
		String taLoanNO="";
		String taLoanID="";
		logger.info("taFlagDtl size----------------"+taFlagDtl.size());
		if(taFlagDtl.size()>0)
		{
			DisbCancellationVO voTA = taFlagDtl.get(0);		
	        taLoanNO=voTA.getTaLoanNo();
	        taLoanID=voTA.getTaLoanID();
	        vo.setTaLoanID(taLoanID);
			logger.info("taLoanNO----------------"+taLoanNO);
			logger.info("taLoanID----------------"+taLoanID);
			//Check for advice
			ArrayList<DisbCancellationVO> taAdvice=service.getAdviceTAFlagOnAuthor(vo);
			DisbCancellationVO voAdviceTA = taAdvice.get(0);
			taFlag=voAdviceTA.getTaFlag();
			logger.info("taFlag----------------"+taFlag);
		
		
		}
		
		//Bill Flag Check
		if(disbFlag.equalsIgnoreCase("F")){
		ArrayList<DisbCancellationVO> billingFlagDtl=service.getBillFlagDetails(vo);
		DisbCancellationVO voBilling = billingFlagDtl.get(0);
		billFlag=voBilling.getBillFlag();
		recType=voBilling.getRecType();
		logger.info("billFlag----------------"+billFlag);
		}
	   
		 if(vo.getDecision().equalsIgnoreCase("X")&&taFlag.equalsIgnoreCase("Y")){
			logger.info("In TA Flag Condition 2");
			request.setAttribute("message","TA");
			request.setAttribute("taLoanNO", taLoanNO);
			
		}
		else if(vo.getDecision().equalsIgnoreCase("X")&&billFlag.equalsIgnoreCase("Y")){
			//Billing Flag Check
			logger.info("In Billing Flag Condition 2");
			String sms="B";
			request.setAttribute("message",sms);
		 }
		else if(vo.getDecision().equalsIgnoreCase("X")&&recType.equalsIgnoreCase("P")){
			//Billing Flag Check
			logger.info("In Billing Flag Condition 2");
			String sms="P";
			request.setAttribute("message",sms);
		 }
		else if(vo.getDecision().equalsIgnoreCase("X")&&(txnAmount.equalsIgnoreCase("Y")||amtInProcess.equalsIgnoreCase("Y"))){			
			//ArrayList<DisbCancellationVO> arrList1= service.getLoanDetails(loanId);
			//ArrayList<DisbCancellationVO> arrList2= service.getDisbursalDetails(loanId);
			//request.setAttribute("arrList1", arrList1);
			//request.setAttribute("arrList2", arrList2);
			request.setAttribute("message","N");
			
		}	
		
		else{
		
		
		String status="";
		status=service.saveCancelLtionAuthor(vo);
		logger.info("Status from saveCancellationAuthor: "+status);
		if(status.equalsIgnoreCase("S"))
		{
			request.setAttribute("message","S");
		}
		else
		{
			request.setAttribute("message","E");
			request.setAttribute("status",status);
		}
		}
		return mapping.findForward("cancellationAuthorSaved");
	}
	
	
	public ActionForward disbCancellationSearchM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside disbCancellationSearchM...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("disbCancellationSearchM the session is out----------------");
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
		DisbCancellationVO vo = new DisbCancellationVO();
		vo.setCurrentPageLink(currentPageLink);
        
        String status="P";
        logger.info("Stage  flag	 status"+status);
        DynaValidatorForm DisbCancellationSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DisbCancellationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		DisbCancellationDAO service=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//DisbCancellationDAO service = new DisbCancellationDAOImpl();
		ArrayList<DisbCancellationVO> searchDataList = service.searchCancellationData(vo,status);
		request.setAttribute("disbCancellationMakerSearch", "disbCancellationMakerSearch");
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);		
		return mapping.findForward("searchLoanData");
	}
	
	public ActionForward disbCancellationSearchA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside disbCancellationSearchA...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("disbCancellationSearch action the session is out----------------");
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
		DisbCancellationVO vo = new DisbCancellationVO();
		vo.setCurrentPageLink(currentPageLink);
        String status="F";
        DynaValidatorForm DisbCancellationSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, DisbCancellationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(status);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		DisbCancellationDAO service=(DisbCancellationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbCancellationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//DisbCancellationDAO service = new DisbCancellationDAOImpl();
		ArrayList<DisbCancellationVO> searchDataList = service.searchCancellationData(vo,status);
		request.setAttribute("disbCancellationAuthorSearch", "disbCancellationAuthorSearch");
		request.setAttribute("searchDataList", "searchDataList");
		request.setAttribute("list", searchDataList);		
		return mapping.findForward("searchLoanData");
	}
}
