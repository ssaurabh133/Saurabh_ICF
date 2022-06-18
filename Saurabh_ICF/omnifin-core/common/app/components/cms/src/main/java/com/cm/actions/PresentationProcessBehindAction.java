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

import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.UploadDocumentDAO;

public class PresentationProcessBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(PresentationProcessBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();

		UserObject userobj=(UserObject)session.getAttribute("userobject");
		 String userId="";
			if(userobj!=null){
				userId= userobj.getUserId();
			}else{
				logger.info("in execute method of  PresentationProcessBehindAction action the session is out----------------");
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
				logger.info(" In the PresentationProcessBehindAction----------");
				
				session.removeAttribute("arryList");
				session.removeAttribute("arrList");
				session.removeAttribute("loanID");
				session.removeAttribute("author");
				session.removeAttribute("releasenotCheck");
				session.removeAttribute("notCheck");
				session.removeAttribute("data");
			
				UploadDocumentDAO service=(UploadDocumentDAO)DaoImplInstanceFactory.getDaoImplInstance(UploadDocumentDAO.IDENTITY);
				logger.info("Implementation class: "+service.getClass());
				
				StringBuffer uploadData=service.getUploadData(userId);
				logger.info("in UploadBehindAction File Name is:"+uploadData);
				request.setAttribute("fileName", uploadData.toString());	
				request.setAttribute("result","result");
				return mapping.findForward("success");
	}
}
