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

import com.cm.dao.DeferralDAO;
//import com.cm.dao.DeferralDAOImpl;
import com.cm.vo.DeferralMakerVo;
import com.cm.vo.PartPrePaymentSearchVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class DeferralSearchDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DeferralSearchDispatchAction.class.getName());
	
	public ActionForward openNewDeferral(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside openNewDeferral");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewDeferral method of DeferralSearchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		Object sessionId = session.getAttribute("sessionID");
		//Object sessionId = session.getAttribute("sessionID");
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
		session.removeAttribute("deferralDataAuthor");
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		
		request.setAttribute("deferralNew","deferralNew");
		return mapping.findForward("openNewDeferral");
	}
	
	public ActionForward searchDeferral(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........searchPartPrePayment");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in searchDeferral method of DeferralSearchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		PartPrePaymentSearchVO vo = new PartPrePaymentSearchVO();
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
		vo.setCurrentPageLink(currentPageLink);
		
		String type = CommonFunction.checkNull(request.getParameter("type"));
		DynaValidatorForm SDLiquidationSearchDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(type);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<PartPrePaymentSearchVO> deferralSearchList = service.searchDeferralData(vo,type);
		if(deferralSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("deferralMakerSearch","deferralMakerSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("deferralAuthorSearch","deferralAuthorSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("deferralMakerSearch","deferralMakerSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
				request.setAttribute("list", deferralSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("deferralAuthorSearch","deferralAuthorSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
				request.setAttribute("list", deferralSearchList);
			}
		}
		return mapping.findForward("searchDeferral");
	} 
	
	public ActionForward showDeferralDataMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........showDeferralDataMaker");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		
		}else{
			logger.info("here in showDeferralDataMaker method of DeferralSearchDispatchAction  action the session is out----------------");
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
		session.removeAttribute("deferralDataAuthor");
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		
		logger.info("function id is .................."+session.getAttribute("functionId").toString());
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
			request.setAttribute("deferralMakerSearch","deferralMakerSearch");
			return mapping.findForward("searchDeferral");
		}
		}
		ArrayList<DeferralMakerVo> deferralData = service.selectDeferralData(loanId,reschId,"P");
		request.setAttribute("deferralData", deferralData);
		request.setAttribute("reschId",reschId);
		return mapping.findForward("showDeferralDataMaker");
	}
	
	public ActionForward showDeferralDataAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........showDeferralDataAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		if(userobj!=null){
			userId = userobj.getUserId();
		}else{
			logger.info("here in showDeferralDataAuthor method of DeferralSearchDispatchAction action the session is out----------------");
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
		session.removeAttribute("deferralDataAuthor");
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		
		logger.info("function id is ........................................"+session.getAttribute("functionId").toString());
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
			request.setAttribute("deferralAuthorSearch","deferralAuthorSearch");
			return mapping.findForward("searchDeferral");
		}
		}
		ArrayList<DeferralMakerVo> deferralDataAuthor = service.selectDeferralData(loanId,reschId,"P");
		session.setAttribute("deferralDataAuthor", deferralDataAuthor);
		session.setAttribute("reschId",reschId);
		session.setAttribute("loanId",loanId);
		return mapping.findForward("showDeferralDataAuthor");
	}

	public ActionForward deferralMakerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside deferralMakerSearch...........");
		
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
			logger.info("here in deferralMakerSearch method of DeferralSearchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		PartPrePaymentSearchVO vo = new PartPrePaymentSearchVO();
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
		vo.setCurrentPageLink(currentPageLink);
		
		String type = "P";
		DynaValidatorForm SDLiquidationSearchDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(type);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<PartPrePaymentSearchVO> deferralSearchList = service.searchDeferralData(vo,type);
		if(deferralSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("deferralMakerSearch","deferralMakerSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
				
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("deferralAuthorSearch","deferralAuthorSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("deferralMakerSearch","deferralMakerSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
				request.setAttribute("list", deferralSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("deferralAuthorSearch","deferralAuthorSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
				request.setAttribute("list", deferralSearchList);
			}
		}
		return mapping.findForward("searchDeferral");
	}
	
	
	public ActionForward deferralAuthorSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside deferralAuthor...........");
		
		HttpSession session =  request.getSession();
		//boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in deferralAuthorSearch method of DeferralSearchDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		PartPrePaymentSearchVO vo = new PartPrePaymentSearchVO();
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
		vo.setCurrentPageLink(currentPageLink);
		
		String type = "F";
		DynaValidatorForm SDLiquidationSearchDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setStage(type);
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<PartPrePaymentSearchVO> deferralSearchList = service.searchDeferralData(vo,type);
		if(deferralSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("deferralMakerSearch","deferralMakerSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("deferralAuthorSearch","deferralAuthorSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("deferralMakerSearch","deferralMakerSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
				request.setAttribute("list", deferralSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("deferralAuthorSearch","deferralAuthorSearch");
				request.setAttribute("deferralSearchList", "deferralSearchList");
				request.setAttribute("list", deferralSearchList);
			}
		}
		return mapping.findForward("searchDeferral");
	}
}
