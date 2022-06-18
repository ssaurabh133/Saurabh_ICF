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
import com.cm.dao.EndOfDayProcessDAO;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class endOfDayBehindAction extends Action
{
	private static final Logger logger = Logger.getLogger(endOfDayBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		
		LoggerMsg.info("in endOfDayBehindAction() .... ");
		
		HttpSession session =  request.getSession(false);		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String businessDate = "";
		if(userobj!=null){
		businessDate =  userobj.getBusinessdate();
		}else{
			logger.info("in endOfDayBehindAction() endOfDayBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
	
		ServletContext context = getServlet().getServletContext();
		//for check User session start
		String strFlag = "";
		
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
		
		try
		{
			
		//HttpSession session=request.getSession(false);
		session.removeAttribute("disbButtonEOD"); // By Amit to enable Start Process button.
		session.removeAttribute("ProcessDone");
    	session.removeAttribute("ProcessNotDone");
    	//session.removeAttribute("ProcDone");
    	
		//CreditManagementDAO service= new CreditManagementDAOImpl();
    	EndOfDayProcessDAO service=(EndOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(EndOfDayProcessDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
    	//CAHNGE BY SACHIN
    	BeginOfDayProcessDAO Bservice=(BeginOfDayProcessDAO)DaoImplInstanceFactory.getDaoImplInstance(BeginOfDayProcessDAO.IDENTITY);
        logger.info("Implementation class: "+Bservice.getClass());
        //END BY SACHIN
   // 	BeginOfDayProcessDAO Bservice= new BeginOfDayProcessDAOImpl();
		ArrayList list;
		list=service.showEodBodData(businessDate);
		String bodFlag=Bservice.getBodStatus();
		session.setAttribute("bodFlag", bodFlag);
		logger.info("In endOfDayBehindAction execute()..bodFlag:  "+bodFlag);	
		if(list.size()>0)
		{
		logger.info("In endOfDayBehindAction execute()..true"+list);	
		session.setAttribute("eodData", list);
		
		}
		else
		{
			logger.info("In endOfDayBehindAction execute()..false"+list);	
		}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			
		}
		return mapping.findForward("endOfDay");

	}

}

