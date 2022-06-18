package com.scz.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.scz.dao.PoolCreationDAO;
import com.scz.daoImplMYSQL.PoolCreationDAOImpl;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PoolCreationBehindAction extends Action{
	private static final Logger logger = Logger.getLogger(PoolCreationBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception {
		logger.info("In  PoolCreationBehindAction"); 
		HttpSession session = request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
	    String userId="";
	    if(userobj!=null){
	    	userId= userobj.getUserId();
	    }else{
			logger.info(" in execute method of PoolCreationBehindAction action the session is out----------------");
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
		//CreditManagementDAO service=new CreditManagementDAOImpl();
		// PoolCreationDAO service=(PoolCreationDAO)DaoImplInstanceFactory.getDaoImplInstance(PoolCreationDAO.IDENTITY);
		PoolCreationDAO service= new PoolCreationDAOImpl();
		logger.info("Implementation class: "+service.getClass());
		
		session.removeAttribute("authorPoolIdSavedList");
		session.removeAttribute("myList");
		logger.info("in PoolCreationBehindAction maker id is:"+userId);
		StringBuffer uploadData=service.getUploadPoolData(userId);
		logger.info("in PoolCreationBehindAction File Name is:"+uploadData);
		request.setAttribute("fileName", uploadData.toString());
 
		return mapping.findForward("onSuccess");	
	
	}


}
