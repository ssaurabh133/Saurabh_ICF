package com.masters.actions;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
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

import com.business.Security.MasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.UserAccessMasterVo;


public class UserAccessMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(UserAccessMasterDispatchAction.class.getName());
	public ActionForward openAddUserAccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				LoggerMsg.info(" in openAddUserAccess()");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				request.setAttribute("save", "save");
			    return mapping.findForward("openAdd");	
			}
	public ActionForward openEditUserAccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
		        UserAccessMasterVo userAccessMasterVo=new UserAccessMasterVo(); 
				logger.info("In openEditUserAccess");
				
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
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
				
				userAccessMasterVo.setUserAccessId(request.getParameter("userAccessId"));
				logger.info("In openEditUserAccess---userId---- by getpara-"+request.getParameter("userAccessId"));  
				
				MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList<UserAccessMasterVo> list = mb.searchUserAccessData(userAccessMasterVo);
				userAccessMasterVo=list.get(0);
				logger.info("In openEditUserAccess userMasterVo list"+list.size());
				request.setAttribute("list", list);
				logger.info("Status:--------"+userAccessMasterVo.getUserAccessStatus());
				request.setAttribute("userAccessId", request.getParameter("userAccessId"));
				request.setAttribute("userAccessStatus", userAccessMasterVo.getUserAccessStatus());
				request.setAttribute("editVal", "editVal");
			    return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveUserAccessDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		DynaValidatorForm UserAccessMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		UserAccessMasterVo userAccessMasterVo = new UserAccessMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(userAccessMasterVo, UserAccessMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		userAccessMasterVo.setMakerId(userId);
		userAccessMasterVo.setMakerDate(bDate);
		String sms="";
		
		boolean status = mb.insertUserAccessMaster(userAccessMasterVo);
		logger.info("Inside User Access Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
	    logger.info("status"+status);
	  
		return mapping.getInputForward();
	}
	public ActionForward updateSearchUserAccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
			
		UserAccessMasterVo userAccessMasterVo = new UserAccessMasterVo();
		DynaValidatorForm UserAccessMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(userAccessMasterVo, UserAccessMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String userId=(String)userAccessMasterVo.getUserId();
		logger.info("userAccessId"+userId);
		
		ArrayList<UserAccessMasterVo> detailList = mb.searchUserAccessData(userAccessMasterVo);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}
	public ActionForward updateUserAccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateUserAccess.......");
		HttpSession session = request.getSession();
		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		UserAccessMasterVo userAccessMasterVo=new UserAccessMasterVo(); 
		DynaValidatorForm UserAccessMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(userAccessMasterVo, UserAccessMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		userAccessMasterVo.setMakerId(userId);
		userAccessMasterVo.setMakerDate(bDate);
		logger.info("In updateUserAccess---status-----"+userAccessMasterVo.getUserAccessStatus());
		userAccessMasterVo.setUserAccessStatus(request.getParameter("userAccessStatus"));
		logger.info("In updateUserAccess---status---- by getpara-"+request.getParameter("userAccessStatus"));
		
		userAccessMasterVo.setLbxUserId(request.getParameter("userId"));
		logger.info("In updateUserAccess---userId---- by getpara-"+request.getParameter("userId")); 

        boolean status=mb.updateUserAccessData(userAccessMasterVo);
        String sms="";
        logger.info("In getModuleStatus "+userAccessMasterVo.getModuleStatus()); 
        if(CommonFunction.checkNull(userAccessMasterVo.getModuleStatus()).equalsIgnoreCase("X")){
        	sms="X";
        	
        	request.setAttribute("sms",sms);
        }
        else if(status){
			sms="M";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
		
        return mapping.getInputForward();
       
	}

}
