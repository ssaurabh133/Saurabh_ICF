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

public class UploadBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(UploadBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info("in UploadBehindAction ");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		 String userId="";
			if(userobj!=null){
				userId= userobj.getUserId();
			}else{
				logger.info("in  UploadBehindAction method of  action UploadBehindAction the session is out----------------");
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
		session.removeAttribute("Processfile");
		UploadDocumentDAO service=(UploadDocumentDAO)DaoImplInstanceFactory.getDaoImplInstance(UploadDocumentDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

	 
		logger.info("in UploadBehindAction maker id is:"+userId);
		StringBuffer uploadData=service.getUploadData(userId);
		logger.info("in UploadBehindAction File Name is:"+uploadData);
		request.setAttribute("fileName", uploadData.toString());
		userId=null;
		strFlag=null;
		service=null;
		return mapping.findForward("UploadBehindAction");

	}

}

