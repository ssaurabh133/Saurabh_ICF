package com.cp.actions;

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
import org.apache.struts.actions.DispatchAction;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class DealMovementSearchBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(DealMovementSearchBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
	
		logger.info("In DealMovementSearchBehindAction----  ");
		
		HttpSession session = request.getSession();
		
		boolean flag=false;
		String userId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
		}
		session.setAttribute("userId",userId);
		logger.info("userIDDDDDD------>>>>>>>" +userId);
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
		
		return mapping.findForward("onSuccess");	
	
	}

}
