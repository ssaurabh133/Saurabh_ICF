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
import com.cm.vo.PartPrePaymentSearchVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class DeferralSearchBehind extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DeferralSearchBehind.class.getName());
	
	
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
			logger.info("here in deferralMakerSearch method of DeferralSearchBehind action the session is out----------------");
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
		vo.setStage(type);

		vo.setBranchId(branchId);
		vo.setUserId(userId);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
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
		return mapping.findForward("deferralMakerSearch");
	}
	
	
	public ActionForward deferralAuthorSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside deferralAuthor...........");
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
			logger.info("here in deferralAuthorSearch method of DeferralSearchBehind action the session is out----------------");
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
		vo.setStage(type);
	
	
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
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
		return mapping.findForward("deferralAuthorSearch");
	}
}