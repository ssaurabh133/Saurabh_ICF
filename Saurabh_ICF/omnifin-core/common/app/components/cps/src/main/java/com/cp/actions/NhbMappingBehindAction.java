package com.cp.actions;

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


import com.connect.DaoImplInstanceFactory;
import com.cp.dao.NhbMappingDAO;
import com.cp.vo.NhbMappingVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class NhbMappingBehindAction extends DispatchAction 
{
	private static final Logger logger = Logger.getLogger(NhbMappingBehindAction.class.getName());
	static boolean fs = false;
	public ActionForward searchNhbMapping(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In searchNhbMapping() OF NhbMappingBehindAction");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");		
		if(userobj==null)
		{
			logger.info("here in openSpecialCondition method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
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
		
		NhbMappingVo vo=new NhbMappingVo();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		
		NhbMappingDAO dao=(NhbMappingDAO)DaoImplInstanceFactory.getDaoImplInstance(NhbMappingDAO.IDENTITY);
        logger.info("Implementation class: "+dao.getClass()); 
        
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
		
		vo.setCurrentPageLink(currentPageLink);

        ArrayList gridList=dao.getSearchNhbMapping(vo,request);
        if(gridList.size()>0){
        	request.setAttribute("list",gridList);
        }
		return mapping.findForward("success");
	}
	public ActionForward openAddNhbMapping(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In openAddNhbMapping() OF NhbMappingAction");
		HttpSession session = request.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");		
		if(userobj==null)
		{
			logger.info("here in openSpecialCondition method of  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		ServletContext context = getServlet().getServletContext();
		String strFlag="";	
		if(sessionId!=null)
		{
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		}
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
		
		request.setAttribute("save", "save");
		return mapping.findForward("opennewpage");
	}

	
}