package com.cp.actions;

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

public class RmChangeBehindAction extends DispatchAction{
private static final Logger logger = Logger.getLogger(RmChangeBehindAction.class.getName());
	
	public ActionForward getRmChange(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception {
	
		logger.info("In getRmChange().........  ");
		
		HttpSession session = request.getSession();
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
		
			return mapping.findForward("sessionOut");
		}
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
		String branchId=userobj.getBranchId();
		session.setAttribute("rmChBranchId",branchId);
		session.setAttribute("roChBranchId",branchId);
		form.reset(mapping, request);
		return mapping.findForward("rmSuccess");	
	}
	
	public ActionForward searchRmChangeAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
	
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		String userId = userobj.getUserId();
		request.setAttribute("userID", userId);
		form.reset(mapping, request);
		return mapping.findForward("searchRmAuthor");
	}
}
