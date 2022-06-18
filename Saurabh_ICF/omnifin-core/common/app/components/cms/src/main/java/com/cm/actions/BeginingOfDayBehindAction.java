
package com.cm.actions;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.cm.dao.BeginOfDayProcessDAO;
import com.connect.DaoImplInstanceFactory;

import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class BeginingOfDayBehindAction extends Action
{
	private static final Logger logger = Logger.getLogger(BeginingOfDayBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		
		LoggerMsg.info("in BeginingOfDayBehindAction() .... ");		
		HttpSession session =  request.getSession(false);
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("in execute() of  BeginingOfDayBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		String busnissDate = userobj.getBusinessdate();
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		//for check User session start
		String functionId="";
		String strFlag = "";
		
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
			
		}
		logger.info("function id **************************** "+functionId);
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"4001001",request);
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

		try
		{
		session.removeAttribute("disbButtonBOD"); // By Amit to enable Start Process button.
		session.removeAttribute("ProcessDone");
    	session.removeAttribute("ProcessNotDone");
    	//session.removeAttribute("ProcDone");
    	
		//CreditManagementDAO service= new CreditManagementDAOImpl();
    	//CAHNGE BY SACHIN
    	BeginOfDayProcessDAO service=(BeginOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(BeginOfDayProcessDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
        //END BY SACHIN
//    	BeginOfDayProcessDAO service= new BeginOfDayProcessDAOImpl();
		String bodFlag=service.getBodStatus();
		session.setAttribute("bodFlag", bodFlag);
		logger.info("In endOfDayBehindAction execute()..bodFlag:  "+bodFlag);	
		
		 ArrayList	list=service.showBodData(busnissDate);
		if(list.size()>0)
		{
		logger.info("In BeginingOfDayBehindAction execute()..true"+list);	
		session.setAttribute("eodData", list);
		
		}
		else
		{
			logger.info("In BeginingOfDayBehindAction execute()..false"+list);	
		}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			
		}
		return mapping.findForward("beginingOfDay");

	}

}

