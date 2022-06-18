package com.login.action;

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
import com.lockRecord.action.ReleaseRecordFromObject;
import com.login.actionForm.LoginActionForm;
import com.login.dao.LoginDao;
import com.login.roleManager.LoginManger;
import com.login.roleManager.UserObject;

public class LogoutAction extends Action {
	
	private static final Logger logger = Logger.getLogger(LogoutAction.class
			.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginActionForm loginForm = (LoginActionForm) form;
		HttpSession session=request.getSession(false);
		UserObject userobj=new UserObject();
		String userName = request.getParameter("userName");

		userobj=(UserObject)session.getAttribute("userobject");
		//Surendra Code goes here for email contact no dynamic 
		LoginDao logindao=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
	    logger.info("Implementation class: "+logindao.getClass());
	    ArrayList aboutCompany= logindao.getAboutCompany();
	    logger.info("In aboutCompany  : "+aboutCompany.size());  
	    request.setAttribute("aboutCompany",aboutCompany);	
	    session.removeAttribute("randomAlphaNumericSalt");
	    session.removeAttribute("randomAlphaNumericSaltISAlredy");
		// Code Ended..
		
		LoginManger loginMgr = new LoginManger();
		boolean status=false;
		String flag="";
		String result="manualLogout";
		
		if(userobj!=null){
			logger.info(" inside userobj----------"+userobj);
			if(request.getParameter("stopUpdateQuery")!=null && request.getParameter("stopUpdateQuery").equalsIgnoreCase("stopUpdateQuery"))
			{
				
			}
			else
			{
			String userId=userobj.getUserId();
			status=loginMgr.saveLogOutDetails(userId);
			logger.info("IN LOGOUT ACTION----------"+status);
			session.invalidate();
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			result="manualLogout";
			}	
		}
		else
		{
			String userId=CommonFunction.checkNull(request.getParameter("userIdAutoLogout"));
			status=loginMgr.saveLogOutDetails(userId);
			logger.info("IN LOGOUT ACTION----------"+status);
			session.invalidate();
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			result="manualLogout";
		}
//		if(request.getParameter("eodComplete")!=null && request.getParameter("eodComplete").equalsIgnoreCase("C"))
//		{
//			request.setAttribute("msg1", "R");
//		}
		
		ServletContext context = getServlet().getServletContext();
		context.removeAttribute("msg");
		context.removeAttribute("msg1");
		request.setAttribute("userName", userName);
		request.setAttribute("flag", flag);
		//for releasing lock record from application level object 
		if(context!=null && userobj!=null)
		{
			boolean Lflag = ReleaseRecordFromObject.releaselockedRecord(userobj.getUserId(), context);
		}
		
	    String eodBodFlag = "";
	    eodBodFlag = CommonFunction.checkNull(request.getParameter("flag"));
	    if (eodBodFlag.equalsIgnoreCase("EOD"))
	    {
	      result = "eodBod";
	      context.setAttribute("msg", "B");
	    }
		
		return mapping.findForward(result);
	}
	
}
