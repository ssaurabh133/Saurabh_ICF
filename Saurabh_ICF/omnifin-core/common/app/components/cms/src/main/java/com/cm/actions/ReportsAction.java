

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
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.actionform.ReportsForm;
import com.cm.dao.ReportsDAO;
import com.cm.daoImplMYSQL.ReportsDAOImpl;

public class ReportsAction extends Action {
	private static final Logger logger = Logger.getLogger(ReportsAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in execute method of  ReportsAction action the session is out----------------");
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
		ReportsDAO dao = new ReportsDAOImpl();
		ArrayList<ReportsForm> list1=dao.getReportFormat();
		request.setAttribute("list",list1);	
		
		int role_id;
		role_id=Integer.parseInt(session.getAttribute("roleID").toString());
		logger.info("role_id  "+role_id);
		ReportsDAO reportdao = new ReportsDAOImpl();
		int function_Id=Integer.parseInt(session.getAttribute("functionId").toString());
		String module_id=session.getAttribute("moduleID").toString();
		
		logger.info("Role_id is--->"+role_id);
		logger.info("function_id is--->"+function_Id);
		logger.info("module_Id is--->"+module_id);
		
		ArrayList<ReportsForm> report=reportdao.getReportNameForCp(role_id,function_Id,module_id);
		ArrayList<ReportsForm> sponsor=reportdao.getSponsorCode();
		ArrayList<ReportsForm> financeYear=reportdao.getfinanceYear();
		request.setAttribute("reportlist",report);	
		request.setAttribute("sponsorlist",sponsor);
		request.setAttribute("financeYear",financeYear);
		//code added by neeraj tripathi
		String functionId=(String)session.getAttribute("functionId");
		logger.info("functionId  :  "+functionId);
		if(functionId.trim().equalsIgnoreCase("4000206"))
		{
			String cutOffDate=reportdao.getcutOffDate();
			request.setAttribute("cutOffDate",cutOffDate);	
		}
		//tripathi's space end
		//Nishant starts
		if(functionId.trim().equalsIgnoreCase("4000201"))
		{
			String cutOffDateForIncipient=reportdao.getCutoffDateForIncipient();
			logger.info("cutOffDateForIncipient : "+cutOffDateForIncipient);
			request.setAttribute("cutOffDateForIncipient",cutOffDateForIncipient);	
		}
		//Nishant End
		
						
//		ReportsDAO service = new ReportsDAOImpl();
//		ArrayList list = service.getStage();
//		logger.info("Size of list: "+list.size());
//		request.setAttribute("docStage", list);
		
		return mapping.findForward("success");
	}
	

}
