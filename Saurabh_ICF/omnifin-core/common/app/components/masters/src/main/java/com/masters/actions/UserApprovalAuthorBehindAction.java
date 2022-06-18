package com.masters.actions;

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
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.UserApprovalMatrixVo;


public class UserApprovalAuthorBehindAction extends Action 
{
	private static final Logger logger = Logger.getLogger(UserApprovalAuthorBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ServletContext context = getServlet().getServletContext();
			logger.info("In UserApprovalAuthorBehindAction.........");
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
			DynaValidatorForm formbeen= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
			vo.setCurrentPageLink(currentPageLink);
			
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String result=cpm.makerAuthorFlag();
			String userId=CommonFunction.checkNull(vo.getLbxUserId());
			String userRole=CommonFunction.checkNull(vo.getRole());
			ArrayList<UserApprovalMatrixVo> list;
			list= cpm.getAuthorSearchUser(currentPageLink,userId,userRole);
			request.setAttribute("list",list);
			request.setAttribute("search","search");
			session.removeAttribute("update");
	        return mapping.findForward("success");
			
	}
}