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
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.TradeCheckDAO;
import com.cp.vo.TradeCheckInitVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 04-04-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class TradeCheckCapBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(TradeCheckCapBehindAction.class.getName());
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
	public ActionForward execute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		logger.info("In TradeCheckBehindAction (TradeCheckCap detail) to open tradeCheckCap.jsp page ");

		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
		}else{
			logger.info("here in execute method of TradeCheckCapBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		
//		String userName=userobj.getUserName();
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
		TradeCheckDAO crDAO=(TradeCheckDAO)DaoImplInstanceFactory.getDaoImplInstance(TradeCheckDAO.IDENTITY);
        logger.info("Implementation class: "+crDAO.getClass()); 
		//TradeCheckDAO crDAO = new TradeCheckDAOImpl();
		
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
		TradeCheckInitVo vo = new TradeCheckInitVo();

		if(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))
				{ 
					vo.setReportingToUserId(userId);
				   //logger.info("When user id is not selected by the user:::::"+userId);
				}
//		vo.setMakerId(userId);
//		vo.setUserName(userName);
		vo.setCurrentPageLink(currentPageLink);
		vo.setMakerId(userId);
		
		ArrayList<TradeCheckInitVo> list = crDAO.searchTradeCheckCapDataAuto(vo,"F", request);
		request.setAttribute("list",list);
		return mapping.findForward("tradCapSearch");
		}
	}

	