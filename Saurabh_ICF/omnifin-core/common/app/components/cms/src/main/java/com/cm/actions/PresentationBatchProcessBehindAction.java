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

import com.connect.CommonFunction;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PresentationBatchProcessBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(PresentationBatchProcessBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info(" in execute() of  PresentationBatchProcessBehindAction ");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String prestDate="";
		if(userobj==null){
			logger.info(" in execute() of  PresentationBatchProcessBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			prestDate=userobj.getBusinessdate();
		}
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
		String finalDate=CommonFunction.changeFormat(prestDate);
		request.setAttribute("finalDate",finalDate);
		return mapping.findForward("success");
	}
	

}


