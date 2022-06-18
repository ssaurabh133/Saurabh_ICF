/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

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

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.FacilityDetailsVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.mysql.jdbc.Connection;

/** 
 * MyEclipse Struts
 * Creation date: 10-01-2012
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class FacilityDetailsDispatchAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	private static final Logger logger = Logger.getLogger(FacilityDetailsDispatchAction.class.getName());
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward openFacilityDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		logger.info("In (openFacilityDetails)");
		HttpSession session = request.getSession();
		//boolean flag=false;
		boolean status=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				
		}else{
			logger.info("here in openFacilityDetails method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		String functionId=(String)session.getAttribute("functionId");
		int id=Integer.parseInt(functionId);
			if(id==3000296 ){
				 session.removeAttribute("viewDeal");
				 session.removeAttribute("cmAuthor");
				 session.removeAttribute("facilityDetailsDV");
			}else{
				session.setAttribute("viewDeal","viewDeal");
				session.setAttribute("cmAuthor","cmAuthor");
				session.setAttribute("facilityDetailsDV", "facilityDetailsDV"); 
			}
		String dealId = null;
		if(request.getParameter("dealId")!=null)
		{
			dealId=CommonFunction.checkNull(request.getParameter("dealId"));
		}
		else if ((session.getAttribute("maxId") != null) && CommonFunction.checkNull(dealId).equalsIgnoreCase("")) 
		{
			dealId = session.getAttribute("maxId").toString();
		}
		else
		{
			dealId =  CommonFunction.checkNull(session.getAttribute("dealId"));
		}
		String deal_Loan_Id="select Deal_Loan_id from CR_DEAL_FACILITY_DTL where deal_id='"+dealId+"' order by DEAL_LOAN_ID asc limit 1";
		String SdealLoanId=ConnectionDAO.singleReturn(deal_Loan_Id); 
		String dealLoanId =request.getParameter("dealLoanId"); 
		logger.info("dealLoanId: "+dealLoanId);
		if(SdealLoanId==null){
		session.setAttribute("dealId", dealId);
		//logger.info("deal id "+dealId);
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
		//CreditProcessingDAO dao = new CreditProcessingDAOImpl();
        ArrayList dealHeader = dao.getDealHeader(dealId);
		session.setAttribute("dealHeader", dealHeader);
		//status=dao.transferFacilityData(dealId);
		ArrayList facilityDetailsList = dao.getFacilityDetailsList(dealId,functionId);
		request.setAttribute("facilityDetailsList", facilityDetailsList);
		
		ArrayList baseRateList = dao.getBaseRateList(userobj.getBusinessdate());
		ArrayList alDealProductInfo = dao.getDealProductDetails(dealId);
		request.setAttribute("dealProductDetailList", alDealProductInfo);
		request.setAttribute("baseRateList", baseRateList);
        
      /*  ArrayList loanList = dao.getLoanProductFacilityList(dealId);
        session.setAttribute("loanList", loanList);*/
		
	//	form.reset(mapping, request);
		dao=null;
		dealId=null;
		strFlag=null;
		}
		else
		{	
			session.setAttribute("dealId", dealId);
			//logger.info("deal id "+dealId);
			CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
			//CreditProcessingDAO dao = new CreditProcessingDAOImpl();
	        ArrayList dealHeader = dao.getDealHeader(dealId);
			session.setAttribute("dealHeader", dealHeader);
			status=dao.transferFacilityData(dealId); 
			ArrayList facilityDetailsList = dao.getSFacilityDetailsList(dealId,functionId);
			request.setAttribute("facilityDetailsList", facilityDetailsList);
			
			ArrayList baseRateList = dao.getBaseRateList(userobj.getBusinessdate());
			ArrayList alDealProductInfo = dao.getDealProductDetails(dealId);
			request.setAttribute("dealProductDetailList", alDealProductInfo);
			request.setAttribute("baseRateList", baseRateList);
			if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000951") || CommonFunction.checkNull(functionId).equalsIgnoreCase("4001231")){

				request.setAttribute("facilityDetailsDV", "facilityDetailsDV");
			}
	      /*  ArrayList loanList = dao.getLoanProductFacilityList(dealId);
	        session.setAttribute("loanList", loanList);*/
			
		//	form.reset(mapping, request);
			dao=null;
			dealId=null;
			strFlag=null;
			
			
			
		}
		
		
		return mapping.findForward("openFacilityDetails");
	}
	
	public ActionForward saveFacilityDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		logger.info("In (saveFacilityDetails)");
		HttpSession session = request.getSession();
	//	boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId=null;
		String bDate=null;
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				
		}else{
			logger.info("here in saveFacilityDetails method of action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		String dealId = null;
		String status="E";
		String dealLoanId="0";
		if(request.getParameter("dealId")!=null)
		{
			dealId=CommonFunction.checkNull(request.getParameter("dealId"));
		}
		else
		{
			dealId =  CommonFunction.checkNull(session.getAttribute("dealId"));
		}
		session.setAttribute("dealId", dealId);
		logger.info("deal id "+dealId);
		String functionId=(String)session.getAttribute("functionId");
		
		DynaValidatorForm FacilityDetailsDynaValidatorForm = (DynaValidatorForm) form;
		FacilityDetailsVo vo =new FacilityDetailsVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,FacilityDetailsDynaValidatorForm);		
		vo.setDealId(dealId);
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		vo.setFunctionId(functionId);
		logger.info("deal_loanId:::"+vo.getDealLoanId());
		if(request.getParameter("dealLoanId")!=null){
			vo.setDealLoanId(CommonFunction.checkNull(request.getParameter("dealLoanId")).toString());
		
		}
		logger.info("deal_loanId:::"+vo.getDealLoanId());
		
// 		vo.setDealLoanId(dealLoanId);
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh

        logger.info("vo.getFacilityDetailsID(): "+vo.getFacilityDetailsID());
		String sanctionAmountCheck="S";
		String updateFlag="";
		if(request.getParameter("updateFlag")!=null){
		updateFlag=request.getParameter("updateFlag").toString();
		logger.info("updateFlag::::"+updateFlag);
		}
		if(updateFlag.equalsIgnoreCase("Y")){
			
		}else{
			vo.setDealLoanId("");
		}
		if(!functionId.equalsIgnoreCase("3000297")){
		 sanctionAmountCheck = dao.validateFacilityAmountSanctionAmount(vo);
		}else{
			sanctionAmountCheck="S";
			logger.info("Check bypass for increase the amount ");
		}
		logger.info("sanctionAmountCheck result : "+sanctionAmountCheck);
		
		if (sanctionAmountCheck.equalsIgnoreCase("S"))
		{
			logger.info("Action Mode  : "+vo.getActionMode());
			if("I".equalsIgnoreCase(vo.getActionMode())){
				dealLoanId=dao.saveFacilityDetails(vo);
				vo.setDealLoanId(dealLoanId);
			}
			String q1="select count(1) from cr_deal_facility_dtl where deal_id='"+vo.getDealId()+"'";
			int r1=Integer.parseInt(ConnectionDAO.singleReturn(q1));
			if(r1==0){
				dealLoanId=dao.saveFacilityDetails(vo);
				vo.setDealLoanId(dealLoanId);
			}
			status=dao.updateFacilityDetails(vo);			
			
			if(CommonFunction.checkNull(status).equalsIgnoreCase("S") && sanctionAmountCheck.equalsIgnoreCase("S")){
				request.setAttribute("msg", "S");				
			}
			else{
				request.setAttribute("msg", "E");
			}
		} else {
			request.setAttribute("msg", "V");
		}
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000951") || CommonFunction.checkNull(functionId).equalsIgnoreCase("4001231")){

			request.setAttribute("facilityDetailsDV", "facilityDetailsDV");
		}
		dealId=null;
		status=null;
		return mapping.findForward("saveFacilityDetails");
	}
	
	public ActionForward fetchFacilityDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		logger.info("In (fetchFacilityDetails)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				
		}else{
			logger.info("here in fetchFacilityDetails method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		String functionId=(String)session.getAttribute("functionId");
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
		String dealId = null;
		if(request.getParameter("dealId")!=null)
		{
			dealId=CommonFunction.checkNull(request.getParameter("dealId"));
		}
		else
		{
			dealId =  CommonFunction.checkNull(session.getAttribute("dealId"));
		}
		String deal_Loan_Id="select Deal_Loan_id from CR_DEAL_FACILITY_DTL where deal_id='"+dealId+"' order by DEAL_LOAN_ID asc limit 1";
		String sdealLoanId=ConnectionDAO.singleReturn(deal_Loan_Id); 
		String dealLoanId =request.getParameter("dealLoanId"); 
		logger.info("dealLoanId: "+dealLoanId);
		if(sdealLoanId==null)
		{
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
    	ArrayList baseRateList = dao.getBaseRateList(bDate);
		ArrayList fetchFacilityDetailsList = dao.fetchFacilityDetailsList(dealLoanId,functionId);
		request.setAttribute("fetchFacilityDetailsList", fetchFacilityDetailsList);
		String schemequery="select DEAL_SCHEME from CR_DEAL_Loan_DTL where deal_id='"+dealId+"' ";
		String scheme=ConnectionDAO.singleReturn(schemequery); 
		ArrayList allList = dao.getFacilityDetailScheme(scheme,bDate);
		ArrayList facilityDetailsList = dao.getFacilityDetailsList(dealId,functionId);
		request.setAttribute("facilityDetailsList", facilityDetailsList);
		request.setAttribute("baseRateList", baseRateList);
		request.setAttribute("actionMode", "U");
		request.setAttribute("allDetail",allList );
		}
		else
		{	
			/*if(request.getParameter("dealLoanId")!=null)
			{
				dealLoanId=CommonFunction.checkNull(request.getParameter("dealLoanId"));
			}
			else
			{
				dealLoanId =  CommonFunction.checkNull(session.getAttribute("dealLoanId"));
			}*/
			//dealLoanId =request.getParameter("dealLoanId"); 
			CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
	    	ArrayList baseRateList = dao.getBaseRateList(bDate);
			ArrayList fetchFacilityDetailsList = dao.sFetchFacilityDetailsData(dealLoanId,functionId);
			request.setAttribute("fetchFacilityDetailsList", fetchFacilityDetailsList);
			String schemequery="select DEAL_SCHEME from CR_DEAL_Loan_DTL where deal_id='"+dealId+"' ";
			String scheme=ConnectionDAO.singleReturn(schemequery); 
			ArrayList allList = dao.getFacilityDetailScheme(scheme,bDate);
			ArrayList facilityDetailsList = dao.getSFacilityDetailsList(dealId,functionId);
			request.setAttribute("facilityDetailsList", facilityDetailsList);
			request.setAttribute("baseRateList", baseRateList);
			request.setAttribute("actionMode", "U");
			request.setAttribute("allDetail",allList );
		}
		
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000951") || CommonFunction.checkNull(functionId).equalsIgnoreCase("4001231")){

			request.setAttribute("facilityDetailsDV", "facilityDetailsDV");
		}
		//logger.info("facilityDetailsList size: "+facilityDetailsList.size());
		return mapping.findForward("fetchFacilityDetails");
	}
	
	public ActionForward deleteFacilityDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		logger.info("In (deleteFacilityDetails)");
		HttpSession session = request.getSession();
	//	boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		//String userId=null;
	//	String bDate="";
		
		if(userobj!=null)
		{
			//	userId=userobj.getUserId();
			//	bDate=userobj.getBusinessdate();
				
		}else{
			logger.info("here in deleteFacilityDetails method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		String dealId = null;
		if(request.getParameter("dealId")!=null)
		{
			dealId=CommonFunction.checkNull(request.getParameter("dealId"));
		}
		else
		{
			dealId =  CommonFunction.checkNull(session.getAttribute("dealId"));
		}
		boolean status=false;
		String dealLoanId[]=request.getParameterValues("chk");
		logger.info("deal id "+dealId+" specialId: "+dealLoanId.length);
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
        String query="select min(deal_loan_id) from cr_deal_facility_dtl where deal_id='"+dealId+"' ";
        String minDealLoanId=ConnectionDAO.singleReturn(query);
        if(CommonFunction.checkNull(dealLoanId[0]).equalsIgnoreCase(minDealLoanId)){
        	request.setAttribute("msg", "N");
        }else{
		 status=dao.deleteFacilityDetails(dealLoanId);
        }
		ArrayList alDealProductInfo = dao.getDealProductDetails(dealId);
		request.setAttribute("dealProductDetailList", alDealProductInfo);
	//	logger.info("special Condition status: "+status);
		if(status)
		{
			request.setAttribute("msg", "D");
		}
		else
		{
			request.setAttribute("msg", "N");
		}
		String functionId=(String)session.getAttribute("functionId");
		ArrayList baseRateList = dao.getBaseRateList(userobj.getBusinessdate());
		String deal_Loan_Id="select Deal_Loan_id from CR_DEAL_FACILITY_DTL where deal_id='"+dealId+"' order by DEAL_LOAN_ID asc limit 1";
		String sdealLoanId=ConnectionDAO.singleReturn(deal_Loan_Id); 
		//String dealLoanId =request.getParameter("dealLoanId"); 
		logger.info("dealLoanId: "+dealLoanId);
		if(sdealLoanId==null)
		{		
		ArrayList facilityDetailsList = dao.getFacilityDetailsList(dealId,functionId);
		request.setAttribute("facilityDetailsList", facilityDetailsList);
		request.setAttribute("baseRateList", baseRateList);
		//form.reset(mapping, request);
		dao=null;
		}
		else 
		{
			ArrayList facilityDetailsList = dao.getSFacilityDetailsList(dealId,functionId);
			request.setAttribute("facilityDetailsList", facilityDetailsList);
			request.setAttribute("baseRateList", baseRateList);
			//form.reset(mapping, request);
			dao=null;
		}
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000951") || CommonFunction.checkNull(functionId).equalsIgnoreCase("4001231")){

			request.setAttribute("facilityDetailsDV", "facilityDetailsDV");
		}
		return mapping.findForward("deleteFacilityDetails");
	}
	
	public ActionForward viewFacilityDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info("In (viewFacilityDetails)");
		HttpSession session = request.getSession();
	//	boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		//String userId="";
	//	String bDate="";
		
		if(userobj!=null)
		{
			//	userId=userobj.getUserId();
			//	bDate=userobj.getBusinessdate();
				
		}else{
			logger.info("here in viewFacilityDetails method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		//code added by neeraj 
		String functionId=(String)session.getAttribute("functionId");
		if(CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
			functionId="0";
		int id=Integer.parseInt(functionId);
		if(id==4000122 || id==4000123)
		{
			session.setAttribute("cmAuthor","cmAuthor");
			session.setAttribute("viewLoan","viewLoan");
		}
		//neeraj space end 
		
		String loanId = null;
		if(!CommonFunction.checkNull(request.getParameter("loanId")).equalsIgnoreCase(""))
		{
			loanId=CommonFunction.checkNull(request.getParameter("loanId"));
		}
		else{
			if(session.getAttribute("loanId")!=null)
				loanId=session.getAttribute("loanId").toString();
			else if(session.getAttribute("maxIdInCM")!=null)
				loanId=session.getAttribute("maxIdInCM").toString();
		}
		session.setAttribute("loanId", loanId);
	//	logger.info("loanId id "+loanId);
		CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
		ArrayList facilityDetailsList = dao.getShowFacilityDetailsList(loanId);
		request.setAttribute("facilityDetailsList", facilityDetailsList);
		request.setAttribute("viewFacilityDetails", "viewFacilityDetails");
		//logger.info("facilityDetailsList size: "+facilityDetailsList.size());
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("4000121"))
		{
			request.setAttribute("authorFacilityDetails", "authorFacilityDetails");
		}
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("4000111") || CommonFunction.checkNull(functionId).equalsIgnoreCase("4001231"))
		{
			request.setAttribute("loanHeaderView", "loanHeaderView");
			request.setAttribute("authorFacilityDetails", "authorFacilityDetails");
		}
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("4000106"))
		{
			request.setAttribute("loanHeaderView", "loanHeaderView");
		}
		//form.reset(mapping, request);	
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000951") || CommonFunction.checkNull(functionId).equalsIgnoreCase("4001231") || CommonFunction.checkNull(functionId).equalsIgnoreCase("500000123")){

			request.setAttribute("facilityDetailsDV", "facilityDetailsDV");
		}
		return mapping.findForward("viewFacilityDetails");
	}
	
	public ActionForward updateFacilityDetailsRemarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		logger.info("In (updateFacilityDetailsRemarks)");
		HttpSession session = request.getSession();
	//	boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId=null;
		String bDate=null;
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				
		}else{
			logger.info("here in saveFacilityDetails method of action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
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
		String loanId = null;
		String status="E";
		if(request.getParameter("dealId")!=null)
		{
			loanId=CommonFunction.checkNull(request.getParameter("loanId"));
		}
		else
		{
			loanId =  CommonFunction.checkNull(session.getAttribute("loanId"));
		}
	
		
		 String loanDisbursalId =  CommonFunction.checkNull(session.getAttribute("loanDisbursalId"));
		
		logger.info("loanId "+loanId+" loanDisbursalId: "+loanDisbursalId);
		DynaValidatorForm FacilityDetailsDynaValidatorForm = (DynaValidatorForm) form;
		FacilityDetailsVo vo =new FacilityDetailsVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,FacilityDetailsDynaValidatorForm);

		/*vo.setLoanId(loanId);
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
        vo.setLoanDisbursalId(loanDisbursalId);
        */CreditProcessingDAO dao=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 	//changed by asesh
		String [] specialDealId=request.getParameterValues("chk");
		//logger.info("vo.getSpecialDealId(): "+vo.getSpecialDealId());
//		vo.setChk(specialDealId);
		status=dao.updateFacilityDetailsRemarks(vo);
		
		if(CommonFunction.checkNull(status).equalsIgnoreCase("S"))
		{
			request.setAttribute("msg", "S");
		}
		else
		{
			request.setAttribute("msg", "E");
		}
	//	form.reset(mapping, request);
		loanId=null;
		dao=null;
		status=null;
		return mapping.findForward("updateFacilityDetailsRemarks");
	}
	
}