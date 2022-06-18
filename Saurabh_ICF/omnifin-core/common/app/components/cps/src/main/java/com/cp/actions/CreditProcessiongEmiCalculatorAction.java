/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.connect.CommonFunction;
import com.connect.md5;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;



public class CreditProcessiongEmiCalculatorAction extends Action {
	private static final Logger logger = Logger.getLogger(CreditProcessiongEmiCalculatorAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session=request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String flag=CommonFunction.checkNull(request.getParameter("CRM"));	
		String userId="";
		String userId1="";
		String bDate="";
		String branchId="";
			
	//Abhi code starts here	
		try{
		if(flag.equals(md5.en("CRM")))
		{
		int companyId =1;
		userId1=CommonFunction.checkNull(request.getParameter("userId"));
		bDate=CommonFunction.checkNull(request.getParameter("bDate"));
		branchId=CommonFunction.checkNull(request.getParameter("branchId"));
		if(userId1.equals("")||bDate.equals("")||branchId.equals(""))
		{
			return mapping.findForward("sessionOut");
		}
		else
		{
		logger.info("Credantial---"+userId1+bDate+branchId+flag);
		userobj= new UserObject();
		userobj.setCompanyId(companyId);
		userobj.setUserId(userId1);
		userobj.setBusinessdate(bDate);
		userobj.setBranchId(branchId);
		session.setAttribute("userobject", userobj);
		session.setAttribute("branchId", branchId);
		}
		}
	// Abhi code ends here....
		else
		{
		userobj=(UserObject)session.getAttribute("userobject");
		if(userobj!=null)
		{
 			userId=userobj.getUserId();
		}else{
			logger.info("here in execute  method of CreditProcessiongEmiCalculatorAction session is out----------------");
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
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return mapping.findForward("logout");
		}
		finally
		{
			userId1=null;
			bDate=null;
			branchId=null;
			flag=null;
			
		}
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
		{
			currentPageLink=1;
		}
		else
		{
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		}
		
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		return mapping.findForward("success");	
		
	}
}