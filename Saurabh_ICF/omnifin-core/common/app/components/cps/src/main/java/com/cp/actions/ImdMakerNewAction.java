package com.cp.actions;



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
import com.cp.dao.ImdDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ImdMakerNewAction extends Action{
	private static final Logger logger = Logger.getLogger(ImdMakerNewAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		logger.info(" in ImdMakerNewAction----------");	
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in execute method of ImdMakerNewAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		
		String strFlag="";	
		
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
		
		ImdDAO dao=(ImdDAO)DaoImplInstanceFactory.getDaoImplInstance(ImdDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		String receiptNoFlag=dao.receiptNoCheckFlag();
		session.setAttribute("receiptNoFlag",receiptNoFlag);
		
		ArrayList purposeList = dao.receiptPurposeList();
		//logger.info(" In the newInstrument-"+purposeList.get(0));
		request.setAttribute("purposeList", purposeList);
		
		session.removeAttribute("pParentGroup");
		session.removeAttribute("loanID");
		session.removeAttribute("instumentID");
		session.removeAttribute("datatoapproveList");
		session.removeAttribute("businessPartnerType");
		ServletContext context = getServlet().getServletContext();
		//boolean flag=false;
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

		logger.info(" In the ImdMakerNewAction NEW----------");
		return mapping.findForward("new");
		}


}
