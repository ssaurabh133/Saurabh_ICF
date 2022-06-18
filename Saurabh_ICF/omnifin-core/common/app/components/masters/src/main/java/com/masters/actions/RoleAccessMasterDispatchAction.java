package com.masters.actions;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.Security.MasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.RoleAccessMasterVo;


public class RoleAccessMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(RoleAccessMasterDispatchAction.class.getName());
	public ActionForward searchRoleAccess(ActionMapping mapping, ActionForm form,
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
		RoleAccessMasterVo roleAccessMasterVo = new RoleAccessMasterVo();
		DynaValidatorForm RoleAccessMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(roleAccessMasterVo, RoleAccessMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);

		String roleDesc=(String)roleAccessMasterVo.getRoleId();
		String moduleDesc=(String)roleAccessMasterVo.getModuleId();
		String  moduleId=(String)roleAccessMasterVo.getLbxModuleId();
		String  roleId=(String)roleAccessMasterVo.getLbxRoleId();
		
		logger.info("In searchRole roleId"+roleId);
		logger.info("In searchRole moduleId"+moduleId);
		logger.info("In searchRole moduleDesc"+moduleDesc);
		
		ArrayList<RoleAccessMasterVo> detailList = mb.searchRoleAccessData(roleAccessMasterVo);
		request.setAttribute("list", detailList);
		request.setAttribute("roleId", roleId);
		request.setAttribute("moduleId", moduleId);
		request.setAttribute("roleDesc",roleDesc);
		request.setAttribute("moduleDesc", moduleDesc);
		
		return mapping.findForward("searchPage");
	}
	
	public ActionForward saveRoleAccessDetails(ActionMapping mapping, ActionForm form,
		
		HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		DynaValidatorForm RoleAccessMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		RoleAccessMasterVo roleAccessMasterVo = new RoleAccessMasterVo();
		roleAccessMasterVo.setMakerId(userId);
		roleAccessMasterVo.setMakerDate(bDate);
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(roleAccessMasterVo, RoleAccessMasterAddDynaValidatorForm);	
		
		MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String checkbox[] = request.getParameterValues("chkCases");
		String roleCode=roleAccessMasterVo.getLbxRoleId();
		String moduleCode=roleAccessMasterVo.getLbxModuleId();
		
		logger.info("---- roleCode is ---"+roleCode);
		logger.info("---- moduleCode is ---"+moduleCode);
		
		String sms="";
		if(checkbox==null && CommonFunction.checkNull(roleCode).equalsIgnoreCase("")&& CommonFunction.checkNull(moduleCode).equalsIgnoreCase(""))
		{
			sms="F";
			request.setAttribute("sms",sms);
		}		
		else
		{
             boolean status = mb.insertRoleAccessMaster(roleAccessMasterVo,checkbox);
			
			logger.info("Inside Role Access Master Action.....displaying status...."+status);
			if(status)
			{
				if(checkbox!=null && !checkbox.equals("")){
					sms="S";
					request.setAttribute("sms",sms);
				}else{
				sms="S";
				request.setAttribute("sms",sms);
				}
			}
			else
			{
				sms="E";
				request.setAttribute("sms",sms);
			}
			logger.info("status"+status);
		    ArrayList<RoleAccessMasterVo> list = mb.searchRoleAccessData(roleAccessMasterVo);
		    logger.info("list: "+list.size());
			request.setAttribute("list", list);
		}
		
		
		request.setAttribute("sms",sms);
		return mapping.findForward("searchPage");
	}	

}
