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
import com.connect.DealSanctionLetterInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.DealSanctionLetterDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


/**
 * MyEclipse Struts Creation date: 05-05-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class DocumentUploadBehindLIMAction extends Action {
	private static final Logger logger = Logger.getLogger(DocumentUploadBehindLIMAction.class.getName());
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

		logger.info("Inside DocumentUploadBehindLIMAction(execute)");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in execute method of DocumentUploadBehindLIMAction action the session is out----------------");
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
		if(!"".equalsIgnoreCase(strFlag))
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
	
		CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 		
		
		
		String loanId = "";	
				
		if(session.getAttribute("loanId")!=null)
			loanId=session.getAttribute("loanId").toString();
		else if(session.getAttribute("maxIdInCM")!=null)
			loanId=session.getAttribute("maxIdInCM").toString();
		logger.info("In DocumentUploadBehindLIMAction loan id: " +loanId);
		if((loanId!=null && !loanId.equalsIgnoreCase("")))
		{
			logger.info("In DocumentUploadBehindLIMAction execute loanId: " +loanId);
	
			 ArrayList uploadedDocList = service.getUploadUnderwritingDataForCm(loanId); 
			 ArrayList uploadDocListForCp = service.getUploadUnderwritingDataForCmCp(loanId); 
			 
				DealSanctionLetterDAO dao=(DealSanctionLetterDAO)DealSanctionLetterInstanceFactory.getDaoImplInstance(DealSanctionLetterDAO.IDENTITY);
			 ArrayList uploadedDocSanctioLetterList = dao.getUploadUnderwritingDataForSanctionLetter(loanId); 
	
			 request.setAttribute("uploadedDocList", uploadedDocList);
			 request.setAttribute("uploadDocListForCp", uploadDocListForCp);
			 request.setAttribute("uploadedDocSanctioLetterList", uploadedDocSanctioLetterList);
			service=null;
			strFlag=null;

	
			return mapping.findForward("success");
		}
		else
		{
			request.setAttribute("back", "back");
			return mapping.findForward("backSuccess");
		}
	}
}