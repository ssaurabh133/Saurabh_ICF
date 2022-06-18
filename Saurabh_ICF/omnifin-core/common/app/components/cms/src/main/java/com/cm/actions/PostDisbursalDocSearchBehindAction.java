/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cm.actions;

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


import com.cm.dao.DisbursalInitiationDAO;
//import com.cm.dao.DisbursalInitiationDAOImpl;
import com.cm.vo.PostDisbursalDocVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts
 * Creation date: 07-04-2011
 *
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class PostDisbursalDocSearchBehindAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(PostDisbursalDocSearchBehindAction.class.getName());

	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward postDisbursalDocMakerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside PostDisbursalDocSearchBehindAction..........postDisbursalDocMakerSearch");

		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in postDisbursalDocMakerSearch method of PostDisbursalDocSearchBehindAction action the session is out----------------");
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
		//DisbursalInitiationDAO service = new DisbursalInitiationDAOImpl();
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<PostDisbursalDocVO> docStage = service.getDocumentStage();
		request.setAttribute("docStage",docStage);
		request.setAttribute("postDisbursalDocMakerSearch","postDisbursalDocMakerSearch");
		return mapping.findForward("postDisbursalDocMakerSearch");
	}

	public ActionForward postDisbursalDocAuthorSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside PostDisbursalDocSearchBehindAction..........postDisbursalDocAuthorSearch");

		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in postDisbursalDocAuthorSearch method of PostDisbursalDocSearchBehindAction action the session is out----------------");
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
		DisbursalInitiationDAO service=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<PostDisbursalDocVO> docStage = service.getDocumentStage();
		request.setAttribute("docStage",docStage);
		request.setAttribute("postDisbursalDocAuthorSearch","postDisbursalDocAuthorSearch");
		return mapping.findForward("postDisbursalDocAuthorSearch");
	}
}