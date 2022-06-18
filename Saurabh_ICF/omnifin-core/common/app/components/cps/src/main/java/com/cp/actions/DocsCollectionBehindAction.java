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
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 05-04-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class DocsCollectionBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(DocsCollectionBehindAction.class.getName());
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("In DocsCollectionBehindAction(execute)");
		HttpSession session = request.getSession();
		session.removeAttribute("loanAmount");
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of DocsCollectionBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
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
		//code added by neeraj
		String source="NE";
		String functionId=(String)session.getAttribute("functionId");
		int funid=Integer.parseInt(functionId);		
		if(funid==4000122 || funid==4000123)
			source="ED";
		//neeraj space end
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		if ((dealId != null && !dealId.equalsIgnoreCase(""))) {
			logger.info("In DocsCollectionBehindAction(execute) in Deal id "
					+ dealId);
			String loanFlag="";
			if(request.getAttribute("status")!=null){
				 loanFlag=request.getAttribute("status").toString();
			}
			if(request.getParameter("status")!=null){
				 loanFlag=request.getParameter("status").toString();
			}
			if(CommonFunction.checkNull(loanFlag).equalsIgnoreCase("UWA")){
			session.setAttribute("loanFlag", loanFlag);
			}else{
				session.removeAttribute("loanFlag");
			}
		//	String message=service.collectDocuments("DC", dealId, "PRS");// IS DEALID
		//	session.setAttribute("message", message);
			if(!CommonFunction.checkNull(loanFlag).equalsIgnoreCase("UWA")){
			 String status=service.collectDocuments("DC", dealId, "PRS",source);// IS DEALID
			 //if(!status.equalsIgnoreCase("S")){
			 session.setAttribute("procvalue", status);
			}
			 
			return mapping.findForward("success");
		}

		else {
			request.setAttribute("back", "B");
			return mapping.findForward("backToFirst");
		}
	}
}