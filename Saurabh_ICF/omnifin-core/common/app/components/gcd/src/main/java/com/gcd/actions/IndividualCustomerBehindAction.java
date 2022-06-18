package com.gcd.actions;


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
import com.gcd.VO.ShowCustomerDetailVo;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class IndividualCustomerBehindAction extends Action 
{
	private static final Logger logger = Logger.getLogger(IndividualCustomerBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info(" in execute method of  IndividualCustomerBehindAction .");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in execute method of  IndividualCustomerBehindAction action the session is out----------------");
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
				context.setAttribute("msg","B");
			}
			return mapping.findForward("logout");
		}
		session.removeAttribute("updateInMaker");
		session.removeAttribute("approve");
		logger.info("current page link .......... "+request.getParameter("d-5875444-p"));		
		int currentPageLink = 0;
		if(request.getParameter("d-5875444-p")==null || request.getParameter("d-5875444-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-5875444-p"));
		logger.info("current page link ................ "+request.getParameter("d-5875444-p"));
		
		CorporateDAO dao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass());
		ArrayList<ShowCustomerDetailVo> list=dao.getDefaultIndividualList(currentPageLink);
		request.setAttribute("indigridList",list);	
		return mapping.findForward("success");
	}
	

}
