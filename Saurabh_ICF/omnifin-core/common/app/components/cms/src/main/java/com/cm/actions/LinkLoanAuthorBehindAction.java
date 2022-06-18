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
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.LinkLoanDAO;
import com.cm.vo.LinkLoanVo;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class LinkLoanAuthorBehindAction extends Action {
	private static final Logger logger=Logger.getLogger(LinkLoanAuthorBehindAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,
								HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		logger.info("in execute method of LinkLoanAuthorBehindAction:::::::::::::::::");
		
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			businessDate = userobj.getBusinessdate();
		}else{
			logger.info("here in execute method of LinkLoanAuthorBehindAction action the session is out----------------");
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
		
		LinkLoanVo linkLoanVo=new LinkLoanVo();

		DynaValidatorForm linkLoanDynaValidatorForm = (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(linkLoanVo, linkLoanDynaValidatorForm);		
		linkLoanVo.setCurrentPageLink(currentPageLink);
		linkLoanVo.setUserId(userId);
		
		LinkLoanDAO dao=(LinkLoanDAO)DaoImplInstanceFactory.getDaoImplInstance(LinkLoanDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		
		ArrayList<LinkLoanVo> searchList= dao.getAuthorLoanDetails(linkLoanVo);		
		
		if((searchList.size())> 0)
		{
			request.setAttribute("list", searchList);
		}
		else{
			request.setAttribute("list", "");
		}

	return mapping.findForward("success");
	}
	

}
