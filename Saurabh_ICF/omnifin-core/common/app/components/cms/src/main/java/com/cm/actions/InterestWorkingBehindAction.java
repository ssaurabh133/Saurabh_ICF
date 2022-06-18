/**
 * 
 */
package com.cm.actions;

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
import com.login.roleManager.UserObject;

/**
 * @author pranaya.gajpure
 *
 */
public class InterestWorkingBehindAction extends Action
{
	private static final Logger logger = Logger.getLogger(InterestWorkingBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		HttpSession session = request.getSession();
		logger.info(" In the InterestWorkingBehindAction----------");
		

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of  InterestWorkingBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		logger.info("strFlag .............. "+strFlag);
		if(!"".equalsIgnoreCase(strFlag))
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
		return mapping.findForward("success");
	}
}
