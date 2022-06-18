package com.caps.action;

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

import com.caps.VO.ClassificationProcessVo;
import com.caps.dao.CollDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class ClassificationProcessAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ClassificationProcessAction.class.getName());

	
	public ActionForward openProcessJsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		logger.info("In openProcessJsp:----------");
		
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here openProcessJsp method of  ClassificationProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		request.setAttribute("notIntiated", "Y");
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
		 CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
		  logger.info("Implementation class: "+collDAO.getClass());
		ArrayList<ClassificationProcessVo> classificationProcessData=new ArrayList<ClassificationProcessVo>();
		logger.info(" In openProcessJsp Action Before Calling DAO.............. ");
		classificationProcessData=collDAO.getClassificationData(bDate);
		request.setAttribute("classificationData",classificationProcessData);
		String refreshFlag=collDAO.updateGetAutoRefreshFlag("N");
		request.setAttribute("refreshFlag",refreshFlag);
		return mapping.findForward("openJsp");
	}
	public ActionForward startClassificationProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");	
		
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here startClassificationProcess method of  ClassificationProcessAction action the session is out----------------");
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
		 CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
		  logger.info("Implementation class: "+collDAO.getClass());
		ArrayList<ClassificationProcessVo> classificationProcessData=new ArrayList<ClassificationProcessVo>();
		logger.info(" In startClassificationProcess Action Before Calling DAO.............. ");
		String refreshFlag=CommonFunction.checkNull(request.getParameter("refreshFlag"));
		logger.info("refreshFlag in startClassificationProcess: "+refreshFlag);
		refreshFlag=collDAO.updateGetAutoRefreshFlag(refreshFlag);
		classificationProcessData=collDAO.startClasificationProcess(bDate, userId);
		logger.info(" In startClassificationProcess Action After Calling DAO.............. "+classificationProcessData.size());
		request.setAttribute("classificationData",classificationProcessData);
		request.setAttribute("refreshFlag",refreshFlag);
		request.setAttribute("processflag","yes");
		return mapping.findForward("openJsp");
	}
	//
	public ActionForward refreshGrid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		logger.info("In openProcessJsp Refresh Grid:----------");
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");	
		
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here refreshGrid method of  ClassificationProcessAction action the session is out----------------");
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
		 request.setAttribute("notIntiated", "Y");
		 CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
		  logger.info("Implementation class: "+collDAO.getClass());
		 String refreshFlag=CommonFunction.checkNull(request.getParameter("refreshFlag"));
		logger.info("refreshFlag in refreshGrid: "+refreshFlag);
		refreshFlag=collDAO.updateGetAutoRefreshFlag(refreshFlag);
		ArrayList<ClassificationProcessVo> classificationProcessData=new ArrayList<ClassificationProcessVo>();
		classificationProcessData=collDAO.getClassificationData(bDate);
		request.setAttribute("classificationData",classificationProcessData);
		request.setAttribute("refreshFlag",refreshFlag);
		request.setAttribute("processflag","yes");
		return mapping.findForward("openAjaxJsp");
	}
}
