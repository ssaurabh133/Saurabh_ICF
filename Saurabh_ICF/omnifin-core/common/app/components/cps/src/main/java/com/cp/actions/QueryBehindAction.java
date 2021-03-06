/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.QueryProcessingVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 05-02-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class QueryBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(QueryBehindAction.class.getName());
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
	public ActionForward showQueryDataFirst(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.info("In QueryBehindAction showQueryDataFirst ");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in showQueryDataFirst method of QueryBehindAction action the session is out----------------");
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

		String dealId = "";
		if(request.getParameter("dealId")!= null)
		{
			dealId=request.getParameter("dealId").toString();
			session.setAttribute("dealId",dealId);
		}

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		String functionId="";
		String userId=CommonFunction.checkNull(userobj.getUserId());
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("In QueryBehindAction showQueryDataFirst id: " + dealId);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
		//CreditProcessingDAO service = new CreditProcessingDAOImpl();
		ArrayList queryList = new ArrayList();
		if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000359"))
		{
        	 queryList = service.getQueryInitiationData(dealId,userId);
		}
        else
			queryList = service.getUnderwritingQueryData(dealId);
		request.setAttribute("queryList", queryList);
		if(request.getParameter("dealId")!= null)
		{
			ArrayList dealHeader = service.getDealHeader(dealId);
			request.setAttribute("dealHeader",dealHeader);
		}
		return mapping.findForward("success");
	}

	public ActionForward showQueryData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in showQueryData method of QueryBehindAction action the session is out----------------");
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
		QueryProcessingVo cr = new QueryProcessingVo();
		String dealId = "";

		if (session.getAttribute("dealId") != null) {

			dealId = session.getAttribute("dealId").toString();
		} else if (session.getAttribute("maxId") != null) {
			dealId = session.getAttribute("maxId").toString();
		}
		logger.info("In QueryBehindAction showQueryData id: " + dealId);
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass()); 			// changed by asesh
		cr.setDealQueryId(CommonFunction.checkNull(request.getParameter("dealQueryId")));
		ArrayList showList = service.showUnderwritingQueryData(cr);
		logger.info("In showQueryData QueryProcessingVo list"
				+ showList.size());
		ArrayList queryList = new ArrayList();
		String functionId="";
		String userId=CommonFunction.checkNull(userobj.getUserId());
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
        if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000359"))
		{
        	 queryList = service.getQueryInitiationData(dealId,userId);
		}
        else
        	 queryList = service.getUnderwritingQueryData(dealId);
		request.setAttribute("queryList", queryList);
		request.setAttribute("showList", showList);
		ArrayList dealHeader = service.getDealHeader(dealId);
		request.setAttribute("dealHeader",dealHeader);
		return mapping.findForward("show");
	}
	
	
}