package com.masters.actions;


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

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.UserProductAccessVo;


public class UserProductAccessBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(UserProductAccessBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ServletContext context = getServlet().getServletContext();
		logger.debug("## In execute() : ");
		
			HttpSession session = request.getSession();
			//boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			String strFlag="";	
			if(sessionId!=null && userobj != null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}
			
			logger.debug("strFlag .............. "+strFlag);
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
			logger.info("## In execute() : current page link ==>>"+request.getParameter("d-49520-p"));			
			int currentPageLink = 0;
			if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				currentPageLink=1;
			else
				currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
			logger.debug("## In execute() : current page link ==>> "+request.getParameter("d-49520-p"));
			
			
			
			UserProductAccessVo vo=new UserProductAccessVo();
			vo.setCurrentPageLink(currentPageLink);
			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
	        ArrayList list= cpm.getRecordAtSearch(currentPageLink);
			request.setAttribute("list",list);
			logger.info("## In execute(): before find forward:::::");
			return mapping.findForward("UserProductAccessSearch");
			
	}
	
	

}
