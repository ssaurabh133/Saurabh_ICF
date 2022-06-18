
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
import com.cm.dao.KnockOffDAO;
import com.cm.vo.KnockOffMakerVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class KnockOffCancellationAuthorBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(KnockOffCancellationAuthorBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info(" in execute() of  KnockOffCancellationAuthorBehindAction ");
		HttpSession session = request.getSession();
		String userId="";
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj!=null){
			userId=userobj.getUserId();
		}else{
			logger.info(" in execute() of  KnockOffCancellationAuthorBehindAction action the session is out----------------");
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

		session.removeAttribute("hideLov");
		session.removeAttribute("forAuthor");
		session.removeAttribute("headerList");
		session.removeAttribute("receivableList");
		session.removeAttribute("payableList");
		session.removeAttribute("totalReceivable");
		session.removeAttribute("totalPayable");
		
		request.setAttribute("userId", userId);
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
		
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
		
		String knockOffId="";
		KnockOffDAO service=(KnockOffDAO)DaoImplInstanceFactory.getDaoImplInstance(KnockOffDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<KnockOffMakerVO> KOCList = service.searchKOCData(knockOffId,currentPageLink,"F",userId);
		
		request.setAttribute("defaultKOCList", KOCList);
		return mapping.findForward("success");
	}
	

}

