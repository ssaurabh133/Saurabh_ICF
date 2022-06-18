package com.masters.actions;

import com.connect.CommonFunction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

	
public class ChangePasswordBehindAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ChangePasswordBehindAction.class.getName());
	
	public ActionForward fromLoginJSp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("In ChangePasswordBehindAtion");
		//HttpSession session = request.getSession();
	/*	boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		if(sessionId!=null)
		{
			flag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString());
		}
		
		if(flag)
		{
			logger.info("logout in action");
			return mapping.findForward("logout");
		}*/
			request.setAttribute("flagLogin", "CL");
			logger.info("-------flag---fromLoginJSp----"+request.getAttribute("flagLogin"));
	        return mapping.findForward("success");
		}
	
	public ActionForward fromchangePassJSp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("flagCP", "CP");
		logger.info("-------flag-------"+request.getAttribute("flagCP"));
		HttpSession session=request.getSession(false);
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
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
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		logger.info("-------user-------"+userId);
		request.setAttribute("user",userId);
		logger.info("-------user-------"+request.getAttribute("user"));
		String randomAlphaNumericSaltFroPass = CommonFunction.checkNull(session.getAttribute("randomAlphaNumericSalt"));
	    request.setAttribute("randomAlphaNumericSaltFroPass", randomAlphaNumericSaltFroPass);
	        return mapping.findForward("success");
		}
	public ActionForward fromchangePassJSpppp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-------flag-------"+request.getAttribute("logmsg"));
		HttpSession session =request.getSession();
		/*boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		if(sessionId!=null)
		{
			flag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString());
		}
		
		if(flag)
		{
			logger.info("logout in action");
			return mapping.findForward("logout");
		}*/
		request.setAttribute("flagLogin", "CL");
		String logmsg = (String) request.getAttribute("logmsg");
		logger.info("-------flag-------"+logmsg);
		request.setAttribute("logmsg",logmsg);
		String name = request.getParameter("name");
		logger.info("-------flag--fromchangePassJSpppp-----"+name);
		request.setAttribute("name", name);
		 
	    return mapping.findForward("success");
		}

	}
