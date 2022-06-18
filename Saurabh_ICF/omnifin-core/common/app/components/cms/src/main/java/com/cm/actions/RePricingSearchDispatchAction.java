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

import com.cm.dao.RepricingDAO;

import com.cm.vo.PartPrePaymentSearchVO;
import com.cm.vo.RepricingMakerVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.vo.CodeDescVo;
import com.lockRecord.action.LockRecordCheck;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class RePricingSearchDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(RePricingSearchDispatchAction.class.getName());
	
	public ActionForward openNewRePricing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside openNewRePricing");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in openNewRePricing method of RePricingSearchDispatchAction action the session is out----------------");
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
		session.removeAttribute("rePricingDataAuthor");
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		session.removeAttribute("baseRateList");
		
		request.setAttribute("rePricingNew","rePricingNew");
		return mapping.findForward("openNewRePricing");
	}
	
	public ActionForward searchRepricing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........searchRepricing");
		
		HttpSession session = request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in searchRepricing method of RePricingSearchDispatchAction action the session is out----------------");
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
		RepricingDAO service=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<PartPrePaymentSearchVO> rePricingSearchList = service.searchRepricing(vo,type);
		if(rePricingSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("rePricingMakerSearch","rePricingMakerSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("rePricingAuthorSearch","rePricingAuthorSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("rePricingMakerSearch","rePricingMakerSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
				request.setAttribute("list", rePricingSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("rePricingAuthorSearch","rePricingAuthorSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
				request.setAttribute("list", rePricingSearchList);
			}
		}
		return mapping.findForward("searchRepricing");
	} 
	
	public ActionForward showRepricingDataMaker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........showRepricingDataMaker");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId = "";
		String bDate = "";
		if(userobj!=null){
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in showRepricingDataMaker method of RePricingSearchDispatchAction action the session is out----------------");
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
		session.removeAttribute("rePricingDataAuthor");
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		session.removeAttribute("baseRateList");
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		RepricingDAO service=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
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
			request.setAttribute("rePricingMakerSearch","rePricingMakerSearch");
			return mapping.findForward("searchRepricing");
		}
		}
		ArrayList<RepricingMakerVo> rePricingData = service.selectRericingData(loanId,reschId,"P");
		request.setAttribute("rePricingData", rePricingData);
		ArrayList<CodeDescVo> baseRateList = service.getBaseRateList(bDate);
		request.setAttribute("baseRateList",baseRateList);
		request.setAttribute("reschId",reschId);
		return mapping.findForward("showRepricingDataMaker");
	}
	
	public ActionForward showRepricingDataAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........showRepricingDataAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate ="";
		if(userobj!=null){
			userId= userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here in showRepricingDataAuthor method of RePricingSearchDispatchAction action the session is out----------------");
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
		session.removeAttribute("rePricingDataAuthor");
		session.removeAttribute("reschId");
		session.removeAttribute("loanId");
		session.removeAttribute("baseRateList");
		
		String loanId = CommonFunction.checkNull(request.getParameter("loanId"));
		String reschId = CommonFunction.checkNull(request.getParameter("reschId"));
		
		//CreditManagementDAO service = new CreditManagementDAOImpl();
		RepricingDAO service=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
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
			request.setAttribute("rePricingAuthorSearch","rePricingAuthorSearch");
			return mapping.findForward("searchRepricing");
		}
		}
		ArrayList<RepricingMakerVo> rePricingDataAuthor = service.selectRericingData(loanId,reschId,"P");
		session.setAttribute("rePricingDataAuthor", rePricingDataAuthor);
		ArrayList<CodeDescVo> baseRateList = service.getBaseRateList(bDate);
		session.setAttribute("baseRateList",baseRateList);
		session.setAttribute("reschId",reschId);
		session.setAttribute("loanId",loanId);
		return mapping.findForward("showRepricingDataAuthor");
	}
	
	public ActionForward rePricingMakerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside rePricingMakerSearch...........");
		
		
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
			logger.info("here in rePricingMakerSearch method of  RePricingSearchDispatchAction action the session is out----------------");
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
		RepricingDAO service=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<PartPrePaymentSearchVO> rePricingSearchList = service.searchRepricing(vo,type);
		if(rePricingSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("rePricingMakerSearch","rePricingMakerSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("rePricingAuthorSearch","rePricingAuthorSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("rePricingMakerSearch","rePricingMakerSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
				request.setAttribute("list", rePricingSearchList);
				
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("rePricingAuthorSearch","rePricingAuthorSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
				request.setAttribute("list", rePricingSearchList);
			}
		}
		return mapping.findForward("searchRepricing");
	}
	
	public ActionForward rePricingAuthorSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside rePricingAuthorSearch...........");
		
		
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
			logger.info("here in rePricingAuthorSearch method of RePricingSearchDispatchAction  action the session is out----------------");
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
		RepricingDAO service=(RepricingDAO)DaoImplInstanceFactory.getDaoImplInstance(RepricingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList<PartPrePaymentSearchVO> rePricingSearchList = service.searchRepricing(vo,type);
		if(rePricingSearchList.size()==0)
		{
			request.setAttribute("message","N");
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("rePricingMakerSearch","rePricingMakerSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("rePricingAuthorSearch","rePricingAuthorSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
			}
		}
		else
		{
			if(type.equalsIgnoreCase("P"))
			{
				request.setAttribute("rePricingMakerSearch","rePricingMakerSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
				request.setAttribute("list", rePricingSearchList);
			}
			
			else if(type.equalsIgnoreCase("F"))
			{
				request.setAttribute("rePricingAuthorSearch","rePricingAuthorSearch");
				request.setAttribute("rePricingSearchList", "rePricingSearchList");
				request.setAttribute("list", rePricingSearchList);
			}
		}
		return mapping.findForward("searchRepricing");
	}

}
