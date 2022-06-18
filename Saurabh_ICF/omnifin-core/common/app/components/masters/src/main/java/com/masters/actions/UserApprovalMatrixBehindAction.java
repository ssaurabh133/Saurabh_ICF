package com.masters.actions;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.UserApprovalMatrixVo;


public class UserApprovalMatrixBehindAction extends Action 
{
	private static final Logger logger = Logger.getLogger(UserApprovalMatrixBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ServletContext context = getServlet().getServletContext();
			logger.info("In UserApprovalMatrixBehindAction.........");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			Object sessionId = session.getAttribute("sessionID");
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
			
			int currentPageLink = 0;
			if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				currentPageLink=1;
			else
				currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
			logger.info("current page link ................ "+request.getParameter("d-49520-p"));
				
			UserApprovalMatrixVo vo=new UserApprovalMatrixVo();
			vo.setCurrentPageLink(currentPageLink);
			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
	      //Nishant Space starts
			String result=cpm.makerAuthorFlag();
			logger.info("Maker author flag : " + result + "searchbyStatus : " + vo.getStatusCase());
			ArrayList<UserApprovalMatrixVo> list;
			if(CommonFunction.checkNull(result).equalsIgnoreCase("Y"))
			{
				list= cpm.getApprovedUser(currentPageLink,"Y");
				request.setAttribute("makerAuthor", "makerAuthor");
			}
			else
			{
				list= cpm.getApprovedUser(currentPageLink,"N");
			}
				
			//Nishant Space end
	       // ArrayList<UserApprovalMatrixVo> list= cpm.getApprovedUser(currentPageLink);
			request.setAttribute("list",list);
			request.setAttribute("search","search");
			//request.setAttribute("totalRecordSize",list.size());
			session.removeAttribute("update");
			
	        return mapping.findForward("success");
			
	}
}