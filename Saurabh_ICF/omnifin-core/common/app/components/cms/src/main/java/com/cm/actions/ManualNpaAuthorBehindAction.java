package com.cm.actions;

import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.ManualIntCalcDAO;
import com.cm.dao.ManualnpaMovementDAO;
import com.cm.vo.ManualnpaMovementVO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class ManualNpaAuthorBehindAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ManualNpaAuthorBehindAction.class.getName());
	public ActionForward searchManualNpaAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Inside searchManualNpaAuthor()...........");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("here in searchManualNpaAuthor () session is out----------------");
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
		ManualnpaMovementVO vo = new ManualnpaMovementVO();
		DynaValidatorForm ManualNpaMovementSearchDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ManualNpaMovementSearchDynaValidatorForm);
		vo.setCurrentPageLink(currentPageLink);

		vo.setBranchId(branchId);
		vo.setUserId(userId);
	
		

		//ManualnpaMovementDAO service = new ManualnpaMovementDAOImpl();
		ManualnpaMovementDAO service=(ManualnpaMovementDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualnpaMovementDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		ArrayList<ManualnpaMovementVO> searchDataList = service.searchManualNpaAuthor(vo);
		request.setAttribute("author", "author");
		request.setAttribute("userId", userId);
		request.setAttribute("list", searchDataList);
		return mapping.findForward("success");
	}
	
	
}
