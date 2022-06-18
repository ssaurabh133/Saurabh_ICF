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

public class ReceiptAuthorBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(ReceiptAuthorBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		
		logger.info("In ReceiptAuthorBehindAction  ");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of ReceiptAuthorBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instrumentID");
		session.removeAttribute("businessPartnerType");
		
		Object sessionId = session.getAttribute("sessionID");

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

		request.setAttribute("fromAuthor", "fromAuthor");

		
		 return mapping.findForward("success");
	}

}
