/*
Created By:- Abhishek Mathur
Date of Creation:- 14/10/2015
Purpose:- Rejected deals would be available at deal capturing stage for re-processing 
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
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class DealReprocessingBehindAction extends Action 
	{
		private static final Logger logger = Logger.getLogger(StageMoveBehindAction.class.getName());
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
		public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
			{
				logger.info("In DealReprocessingBehindAction (execute)");
				HttpSession session = request.getSession();
				boolean flag=false;
				String bDate="";
				String companyId="";
				String status ="";
				String userId="";
				
				UserObject userobj=(UserObject)session.getAttribute("userobject");		
				if(userobj!=null)
					{
						bDate=userobj.getBusinessdate();
						companyId=""+userobj.getCompanyId();
						userId=userobj.getUserId();
					}
				else
					{
						logger.info("here in execute method of DealReprocessingBehindAction action the session is out----------------");
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
			String FunctionId = CommonFunction.checkNull(session.getAttribute("functionId"));
			logger.info("FunctionId In DealReprocessingBehindAction ------> "+FunctionId);
			request.setAttribute("FunctionId", FunctionId);
			return mapping.findForward("openDealReprocessing");
		}
	}
