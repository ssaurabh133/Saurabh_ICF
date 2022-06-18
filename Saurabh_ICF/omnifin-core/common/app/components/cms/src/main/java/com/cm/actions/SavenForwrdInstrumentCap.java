package com.cm.actions;

import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.cm.dao.InstrumentCapturingDAO;
import com.cm.dao.LoanInitiationDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class SavenForwrdInstrumentCap extends DispatchAction{
	private static final Logger logger = Logger.getLogger(SavenForwrdInstrumentCap.class.getName());
	public ActionForward savenForPDCInstrument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IllegalAccessException, InvocationTargetException ,Exception{
		
		logger.info("In savenForPDCInstrument Method---------");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in savenForPDCInstrument method of SavenForwrdInstrumentCap action the session is out----------------");
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
		
		String sms="";
		int id=Integer.parseInt(request.getParameter("id"));
		logger.info("id"+id);
		//InstrumentCapturingDAO dao = new InstrumentCapturingDAOImpl();
		InstrumentCapturingDAO dao=(InstrumentCapturingDAO)DaoImplInstanceFactory.getDaoImplInstance(InstrumentCapturingDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
	    boolean status=dao.updateFlag(id);
	    logger.info("status" +status);
	    	return mapping.getInputForward();
  
	}
}
