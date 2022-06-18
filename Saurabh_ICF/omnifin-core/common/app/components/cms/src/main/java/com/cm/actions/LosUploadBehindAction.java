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

import com.cm.dao.LosUploadDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class LosUploadBehindAction extends Action {
	private static  final Logger logger=Logger.getLogger(LosUploadBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		logger.info("Virender :LosUploadBehindAction|execute()");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("Session Out in execute method of LosUploadBehindAction");
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
		
		//Virender 
		
		String functionId=(String)session.getAttribute("functionId");
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000827")){
			request.setAttribute("LOS","LOS");
		}
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("10000828")){
			request.setAttribute("LMS","LMS");
		}

		LosUploadDAO service=(LosUploadDAO)DaoImplInstanceFactory.getDaoImplInstance(LosUploadDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());

		logger.info("in LosUploadBehindAction maker id is:"+userId);
		String  uploadData=service.getUploadLOSData(userId);
		logger.info("in UploadBehindAction File Name is:"+uploadData);
		request.setAttribute("fileName", "Deal_upload_template.xls");
		
		return mapping.findForward("success");
	}
	
	
	
	

}

