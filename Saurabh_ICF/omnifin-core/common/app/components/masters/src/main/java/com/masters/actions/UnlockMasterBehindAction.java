//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : Behind Action For Agency Master-->
//Documentation : -->


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
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.MasterDAO;


import com.masters.vo.UnlockUserVo;


public class UnlockMasterBehindAction extends Action {
	
	private static final Logger logger = Logger.getLogger(UnlockMasterBehindAction.class.getName());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
		
		HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		ArrayList list=new ArrayList();
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
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
		logger.info("In UnlockMasterBehindAction .......");

		   UnlockUserVo vo = new UnlockUserVo();
		   DynaValidatorForm unlockUserMasterForm= (DynaValidatorForm)form;
		   org.apache.commons.beanutils.BeanUtils.copyProperties(vo, unlockUserMasterForm);
   
		 
		logger.info("--------getUserId---1--->"+vo.getUserId());
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
		MasterDAO masterDAO=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+masterDAO.getClass());
		//MasterDAO masterDAO = new MasterDAOImpl();
		vo.setCurrentPageLink(currentPageLink);
		logger.info("--------getUserId------>"+vo.getUserId());
		logger.info("--------getUserName------>"+vo.getUserName());
		list= masterDAO.searchUnlockuser(vo);

	    logger.info("In UnlockMasterBehindAction....list"+list.size());
		if(list.size()>0){
	    request.setAttribute("list", list);
		}
		else {
			request.setAttribute("sms","No");
		}
		logger.info("----request.getAttribute sms---->"+request.getAttribute("sms"));
		if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
			request.setAttribute("sms","No");
		}
		
        return mapping.findForward("success");
	}

}
