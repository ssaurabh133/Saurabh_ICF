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

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 12-12-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DealMovementBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DealMovementBehindAction.class.getName());
	public ActionForward trackDealStages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In DealMovementBehindAction(execute)");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
				bDate=userobj.getBusinessdate();					
		}else{
			logger.info("here in execute method of DealMovementBehindAction action the session is out----------------");
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

		String dealId = "";
//		String status = CommonFunction
//				.checkNull(request.getParameter("status"));
//		logger.info("status *********************** "+status);
//		if (!status.equalsIgnoreCase("")) {
//			request.setAttribute("status", status);
//			if(status.equalsIgnoreCase("np"))
//			{
//				request.setAttribute("status", request.getParameter("status"));
//				session.removeAttribute("viewDeal");
//			}
//		}
		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}

		
		logger.info("In DealMovementBehindAction(execute) dealid: " + dealId);
		if ((dealId != null && !dealId.equalsIgnoreCase(""))) {
			
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			//CreditProcessingDAO service = new CreditProcessingDAOImpl();
			
			ArrayList dealHeader = service.getDealHeader(dealId);
			request.setAttribute("dealHeader", dealHeader);
			
	
			ArrayList dealMovementList = service.getDealMovementDetail(dealId,userId);
			logger.info("dealMovementList size .................................. "+dealMovementList.size());
			request.setAttribute("dealMovementList", dealMovementList);
//			FinancialDAO dao =  new FinancialDAOImpl();
//			CommonDealVo vo = new CommonDealVo();
//			vo.setLbxDealNo(dealId);
//			vo.setRecStatus("F");
//			vo.setLbxProductID("");
//			vo.setLbxscheme("");
//			vo.setCustomername("");
//			vo.setInitiationDate("");
//			vo.setBranchId(branchId);
//			vo.setBusinessdate(bDate);
//			vo.setUserId(userId);
			
			//ArrayList<CommonDealVo> dealDetail = dao.financialSearchGetDetail(vo);
			// session.setAttribute("dealDetail", dealDetail);
			 
			return mapping.findForward("success");
		}

		else {
			request.setAttribute("back", "B");
			return mapping.findForward("backToFirst");
		}
	}
	
	
	public ActionForward trackVerificationAssigned(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("In DealMovementBehindAction(trackVerificationAssigned)");
		HttpSession session = request.getSession();
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
				bDate=userobj.getBusinessdate();					
		}else{
			logger.info("here in trackVerificationAssigned method of DealMovementBehindAction action the session is out----------------");
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

		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}

		
		logger.info("In DealMovementBehindAction(execute) dealid: " + dealId);
		if ((dealId != null && !dealId.equalsIgnoreCase(""))) {
			
			
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			
			ArrayList dealHeader = service.getDealHeader(dealId);
			request.setAttribute("dealHeader", dealHeader);
			
	
			ArrayList dealVerificationMovementList = service.getDealVerificationMovementDetail(dealId,userId);
			logger.info("dealVerificationMovementList size .................................. "+dealVerificationMovementList.size());
			request.setAttribute("dealVerificationMovementList", dealVerificationMovementList);
		 
			return mapping.findForward("verificationDetails");
		}

		else {
			request.setAttribute("back", "B");
			return mapping.findForward("backToFirst");
		}
	}
	
	//start by sachin
	public ActionForward underwriterDealTracking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
			
			{
		logger.info("In DealMovementBehindAction(underwriterDealTracking)");
		HttpSession session = request.getSession();
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
				bDate=userobj.getBusinessdate();					
		}else{
			logger.info("here in underwriterDealTracking method of DealMovementBehindAction action the session is out----------------");
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

		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}

		
		logger.info("In DealMovementBehindAction(underwriterDealTracking) dealid: " + dealId);
		if ((dealId != null && !dealId.equalsIgnoreCase(""))) {
			
			
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			
			ArrayList dealHeader = service.getDealHeader(dealId);
			request.setAttribute("dealHeader", dealHeader);
			ArrayList dealVerificationMovementList=null;
			String status=service.callProcUnderWriterUsersQueue(dealId,userId,bDate,branchId);
			if(CommonFunction.checkNull(status).equalsIgnoreCase("S"))
			{
				dealVerificationMovementList = service.getUnderWriterDealDetail(dealId,userId);
			}
		
			logger.info("In DealMovementBehindAction(underwriterDealTracking)............................ "+dealVerificationMovementList.size());
			request.setAttribute("dealVerificationMovementList", dealVerificationMovementList);
		 
			return mapping.findForward("underwriterDealTracking");
		}

		else {
			request.setAttribute("back", "B");
			return mapping.findForward("backToFirst");
		}
	}

	
	public ActionForward deviationDealTracking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
			
			{
		logger.info("In DealMovementBehindAction(underwriterDealTracking)");
		HttpSession session = request.getSession();
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				branchId=userobj.getBranchId();
				bDate=userobj.getBusinessdate();					
		}else{
			logger.info("here in deviationDealTracking method of DealMovementBehindAction action the session is out----------------");
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

		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}

		
		logger.info("In DealMovementBehindAction(deviationDealTracking) dealid: " + dealId);
		if ((dealId != null && !dealId.equalsIgnoreCase(""))) {
			
			
			CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
	        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
			
			ArrayList dealHeader = service.getDealHeader(dealId);
			request.setAttribute("dealHeader", dealHeader);
			
	
			ArrayList dealVerificationMovementList = service.getDeviationDealDetail(dealId);
			logger.info("In DealMovementBehindAction(deviationDealTracking)............................ "+dealVerificationMovementList.size());
			request.setAttribute("dealVerificationMovementList", dealVerificationMovementList);
		 
			return mapping.findForward("deviationDealTracking");
		}

		else {
			request.setAttribute("back", "B");
			return mapping.findForward("backToFirst");
		}
	}
	

	//end by sachin
	
}