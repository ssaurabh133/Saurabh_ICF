package com.cm.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.WaiveOffDAO;
import com.cm.vo.WaiveOffVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class WaiveOffAuthorBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(WaiveOffAuthorBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info("in WaiveOffAuthorBehindAction ");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("in execute method of WaiveOffAuthorBehindAction saveActionCodeDetails action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		WaiveOffVO vo= new WaiveOffVO();
		
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
		
		session.removeAttribute("searchWaiveOffAuthor");
		session.removeAttribute("searchWaiveOffAuthorNoData");
		
				
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
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
		
		DynaValidatorForm WaiveOffMakerDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, WaiveOffMakerDynaValidatorForm);
		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
		{ 
			vo.setReportingToUserId(userId);
		   //logger.info("When user id is not selected by the user:::::"+userId);
		}
		logger.info("user Id:::::"+vo.getReportingToUserId());
		vo.setBranchId(branchId);
		vo.setUserId(userId);
		//CreditManagementDAO service=new CreditManagementDAOImpl();
		WaiveOffDAO service=(WaiveOffDAO)DaoImplInstanceFactory.getDaoImplInstance(WaiveOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		ArrayList searchWaiveOffAuthor=service.searchWaiveOffAuthor(vo,request);
		if(searchWaiveOffAuthor.size()==0)
		{
			request.setAttribute("searchList", searchWaiveOffAuthor);
			//logger.info("in searchWaiveOffAuthor.1111.......... "+searchWaiveOffAuthor);
		}
		else
		{
			logger.info("in searchWaiveOffAuthor.22222.......... "+searchWaiveOffAuthor);
			request.setAttribute("searchList", searchWaiveOffAuthor);		
		   // session.setAttribute("list", searchWaiveOffAuthor);		
		}
		return mapping.findForward("waiveoffBehindAuthor");

	}

}

