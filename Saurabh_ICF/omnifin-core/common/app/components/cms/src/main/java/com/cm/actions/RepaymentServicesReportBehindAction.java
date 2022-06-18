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
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.actionform.ReportsForm;
import com.cm.dao.ReportsDAO;


public class RepaymentServicesReportBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(RepaymentServicesReportBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {	
	
		logger.info("In  RepaymentServicesReportBehindAction"); 
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in execute method of RepaymentServicesReportBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		userId=userobj.getUserId();
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
		session.setAttribute("userId", userId);
		ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		ArrayList<ReportsForm> list1=dao.getReportFormat();
		String dateRengeLimit=dao.getDateRangeLimit();
		
		ArrayList<ReportsForm> product=dao.getProductName();
		ArrayList<ReportsForm> loanClassification=dao.getLoanClassification();
		request.setAttribute("dateRengeLimit",dateRengeLimit);
		request.setAttribute("productlist",product);	
		request.setAttribute("loanClasslist",loanClassification);
		request.setAttribute("list",list1);	
		return mapping.findForward("success");
	}
}
