package com.login.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.login.dao.UserSessionCheck;
import com.login.roleManager.Menu;
import com.login.roleManager.UserObject;
import com.login.roleManager.UserProfileManager;



public class LeftSideMenuConfigureAction extends Action

{	private static final Logger logger = Logger.getLogger(LeftSideMenuConfigureAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("You are in LeftSideMenuConfigureAction class execute function.");
		HttpSession session=request.getSession(false);
		UserObject sessUser=(UserObject) session.getAttribute("userobject");
		String userIDForMenu="";
		String flag="error";
	
		
		if(sessUser!=null){	
			userIDForMenu=sessUser.getUserId();			
	    }else{
			logger.info(" In  execute method of  LeftSideMenuConfigureAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		  }
	
	  
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(sessUser,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag 1111.............. "+strFlag);
		if(!strFlag.equalsIgnoreCase("") && !strFlag.equalsIgnoreCase("BODCheck"))
		{
			if(strFlag.equalsIgnoreCase("sameUserSession"))
			{
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			}			
			return mapping.findForward("logout");
//			else if(strFlag.equalsIgnoreCase("BODCheck"))
//			{
//				context.setAttribute("msg", "B");
//			}
			
		}
		
		   UserProfileManager profileManager=new UserProfileManager();
	
			String headerMenuID=request.getParameter("modid");
			String moduleDb=request.getParameter("moduleDb");
	
			int roleID=Integer.parseInt(request.getParameter("roleid"));
			if((!headerMenuID.equalsIgnoreCase(""))&&(roleID>0)){
			session.setAttribute("roleID", roleID);
		
			session.removeAttribute("leftsidemenulist");
			session.removeAttribute("modulename");
			session.removeAttribute("moduleID");
			
			String modname=profileManager.modulename(headerMenuID,moduleDb);
			
			session.setAttribute("modulename",modname);
			session.setAttribute("moduleID",headerMenuID);
			session.setAttribute("moduleDb",moduleDb);
			
			
			int modaccess=profileManager.modaccess(userIDForMenu,headerMenuID,roleID,moduleDb);
			if(modaccess>0){
			List<Menu> leftMenuList=profileManager.getleftMenuBar(headerMenuID,roleID,moduleDb);
	
			if(!leftMenuList.isEmpty()){			
			session.setAttribute("leftsidemenulist", leftMenuList);
			
			
			}flag="success";
			
			}}else{
				flag="error";
			}
			return mapping.findForward(flag);
	
	}
}
