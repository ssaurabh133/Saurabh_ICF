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


import com.cm.dao.ManualAdviceUploadDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class NHBPartPrePayUploadBehindAction extends Action {
	private static  final Logger logger=Logger.getLogger(NHBPartPrePayUploadBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest 
								request,HttpServletResponse response)throws Exception{
		logger.info("in execute method of NHBPartPrePayUploadBehindAction::::::::::::::::::::::::::::::::::::");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in execute method of NHBPartPrePayUploadBehindAction action the session is out----------------");
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
		session.removeAttribute("Processfile");
		ManualAdviceUploadDAO service=(ManualAdviceUploadDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualAdviceUploadDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

	 
		//logger.info("in NHBPartPrePayUploadBehindAction maker id is:"+userId);
		String  uploadData=service.getUploadManualData(userId);
		//logger.info("in NHBUploadBehindAction File Name is:"+uploadData);
		request.setAttribute("fileName", uploadData.toString());
		
		return mapping.findForward("success");
	}
	
	
	
	

}

