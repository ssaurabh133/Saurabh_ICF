/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.masters.actions;

import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

	public class PCDaddNewBehindAction extends Action {
		private static final Logger logger = Logger.getLogger(PCDSearchBehindAction.class.getName());
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			ServletContext context = getServlet().getServletContext();
			
			
			logger.info("In searchPolicyCheckDetailsSceen.....");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
			//for check User session start
			String strFlag="";	
			if(sessionId!=null)
			{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
			}

			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			int list1  = cpm.getApprovalfromPmst();
			logger.info("-----------------------------------------1111111111--"+list1);
			if(list1>2 && list1<10){
				request.setAttribute("pmstSize", list1);					
			}else if(list1<3 || list1>9){
				request.setAttribute("pmstSize", 3);
			}else {
				request.setAttribute("pmstSize", 3);
			}
				return mapping.findForward("success");
		}
			
		}