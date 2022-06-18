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
import com.masters.vo.RoleMasterVo;


public class RoleMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(RoleMasterDispatchAction.class.getName());
	public ActionForward openAddRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				LoggerMsg.info(" in openAddRole()");
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
	public ActionForward openEditRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		        RoleMasterVo roleMasterVo=new RoleMasterVo(); 
				logger.info("In openEditRole");
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
				
				roleMasterVo.setRoleId(request.getParameter("roleId"));
				logger.info("In openEditRole---roleDesc---- by getpara-"+request.getParameter("roleId"));  
				logger.info("In openEditRole---roleDesc---- by getpara by vo-"+roleMasterVo.getRoleId());
				
				roleMasterVo.setRoleDesc(request.getParameter("roleDesc"));
				logger.info("In openEditRole---roleDesc---- by getpara-"+request.getParameter("roleDesc"));  
				logger.info("In openEditRole---roleDesc---- by getpara by vo-"+roleMasterVo.getRoleDesc());
				
				MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		        
				ArrayList<RoleMasterVo> list = mb.searchRoleData(roleMasterVo);
				
				logger.info("In openEditRole roleMastervo list"+list.size());
				request.setAttribute("list", list);
				
				RoleMasterVo roleMVo=new RoleMasterVo();
				roleMVo=list.get(0);
				logger.info("In openEditRole---status---- by   getpara by vo-"+roleMVo.getRoleStatus());
				request.setAttribute("status", roleMVo.getRoleStatus());
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveRoleDetails(ActionMapping mapping, ActionForm form,
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
		
		DynaValidatorForm RoleMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		RoleMasterVo roleMasterVo = new RoleMasterVo();
		roleMasterVo.setMakerId(userId);
		roleMasterVo.setMakerDate(bDate);
		org.apache.commons.beanutils.BeanUtils.copyProperties(roleMasterVo, RoleMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String sms="";
		
		boolean status = mb.insertRoleMaster(roleMasterVo);
		logger.info("Inside Role Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			request.setAttribute("save", "save");
		}
	    logger.info("status"+status);

		return mapping.getInputForward();
	}
		public ActionForward updateSearchRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			ServletContext context = getServlet().getServletContext();
		RoleMasterVo roleMasterVo = new RoleMasterVo();
		DynaValidatorForm RoleMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(roleMasterVo, RoleMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String roleDesc=(String)roleMasterVo.getRoleDesc();
//		boolean status = masterDAO.insertAgencyMaster(vo);
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
		logger.info("roleDesc"+roleDesc);
		
		ArrayList<RoleMasterVo> detailList = mb.searchRoleData(roleDesc);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}
	public ActionForward updateRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateRole.......");
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
		RoleMasterVo roleMasterVo=new RoleMasterVo(); 
		DynaValidatorForm RoleMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(roleMasterVo, RoleMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		logger.info("In updateRole---status-----"+roleMasterVo.getRoleStatus());
		roleMasterVo.setRoleStatus(request.getParameter("roleStatus"));
		
		roleMasterVo.setRoleId(request.getParameter("roleId"));
		logger.info("In updateRole---roleDesc---- by getpara-"+request.getParameter("roleId"));  
		
        boolean status=mb.updateRoleData(roleMasterVo);
        String sms="";
        if(status){
			sms="M";
			request.setAttribute("sms",sms);
			request.setAttribute("editValUpdate", "editValUpdate");
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
			ArrayList<RoleMasterVo> list =new ArrayList<RoleMasterVo>();
			list.add(roleMasterVo);
			logger.info("In openEditNPAStage list"+ list.size());
			
			request.setAttribute("editVal", "editVal");
			request.setAttribute("list", list);
			request.setAttribute("status", roleMasterVo.getRoleStatus());
			
		}
		
        return mapping.getInputForward();
       
		
	}
	
}
