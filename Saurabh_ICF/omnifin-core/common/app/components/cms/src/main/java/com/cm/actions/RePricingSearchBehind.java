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
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class RePricingSearchBehind extends DispatchAction {
	private static final Logger logger = Logger.getLogger(RePricingSearchBehind.class.getName());
	
	
	public ActionForward rePricingMakerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside rePricingMakerSearch...........");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String makerID="";
		String branchId ="";
		if(userobj!=null){
			makerID= userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in rePricingMakerSearch method of RePricingSearchBehind action the session is out----------------");
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
		vo.setUserId(makerID);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,SDLiquidationSearchDynaValidatorForm);
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
		return mapping.findForward("rePricingMakerSearch");
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
			logger.info("here in rePricingAuthorSearch method of RePricingSearchBehind action the session is out----------------");
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
		return mapping.findForward("rePricingAuthorSearch");
	}
}