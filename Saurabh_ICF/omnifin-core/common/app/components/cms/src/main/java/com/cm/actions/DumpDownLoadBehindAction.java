package com.cm.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cm.vo.DumpDownLoadVO;
import com.connect.CommonFunction;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class DumpDownLoadBehindAction extends Action {
	private static final Logger logger = Logger.getLogger(DumpDownLoadBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info(" in execute() of  DumpDownLoadBehindAction ");
		HttpSession session = request.getSession();
	//	boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info(" in execute() of  DumpDownLoadBehindAction action the session is out----------------");
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
		
//		logger.info("current page link .......... "+request.getParameter("d-49520-p"));
//		
//		int currentPageLink = 0;
//		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
//			currentPageLink=1;
//		else
//			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
//		logger.info("current page link ................ "+request.getParameter("d-49520-p"));
//		
//		String knockOffId="";
//		KnockOffDAO service = new KnockOffDAOImpl();
//		ArrayList<KnockOffMakerVO> KOCList = service.searchKOCData(knockOffId,currentPageLink,"F");
//		request.setAttribute("defaultKOCList", KOCList);
		
		String restrictedParm=request.getParameter("restrictedParm");
		session.setAttribute("dumpRestrictedParm", restrictedParm);
		//request.setAttribute("restrictedParm", arg1)
		
		request.setAttribute("AsOnDate","N");
		request.setAttribute("dateRange","N");
		
		strFlag=null;
		return mapping.findForward("success");
	}
	

}

