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
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.DumpFunctionAccessVo;

	public class DumpFunctionAccessAction extends DispatchAction{
		private static final Logger logger = Logger.getLogger(DumpFunctionAccessAction.class.getName());
		public ActionForward searchDumpFunctionAccess(ActionMapping mapping, ActionForm form,
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
			DumpFunctionAccessVo dumpFunctionAccessVo = new DumpFunctionAccessVo();
			DynaValidatorForm RoleAccessMasterAddDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(dumpFunctionAccessVo, RoleAccessMasterAddDynaValidatorForm);	
			
			MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);

			String roleDesc=(String)dumpFunctionAccessVo.getRoleId();
			String moduleDesc=(String)dumpFunctionAccessVo.getModuleId();
			String  moduleId=(String)dumpFunctionAccessVo.getLbxModuleId();
			String  roleId=(String)dumpFunctionAccessVo.getLbxRoleId();
			
			
			logger.info("In searchRole roleId"+roleId);
		    logger.info("In searchRole moduleId"+moduleId);
			logger.info("In searchRole moduleDesc"+moduleDesc);
			
			ArrayList<DumpFunctionAccessVo> detailList = mb.getDumpReportName(dumpFunctionAccessVo);
			request.setAttribute("list", detailList);
			request.setAttribute("roleId", roleId);
			request.setAttribute("moduleId", moduleId);
			request.setAttribute("roleDesc",roleDesc);
			request.setAttribute("moduleDesc", moduleDesc);			
			return mapping.findForward("searchPage");
		}
		public ActionForward saveDumpList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("in saveReportList ............");
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
			DumpFunctionAccessVo dumpFunctionAccessVo = new DumpFunctionAccessVo();
			DynaValidatorForm RoleAccessMasterAddDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(dumpFunctionAccessVo, RoleAccessMasterAddDynaValidatorForm);	
			
			MasterBussinessSessionBeanRemote mb = (MasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(MasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String dumpCheckedList=request.getParameter("dumpCheckedList");
			String dumpUnCheckedList=request.getParameter("dumpUnCheckedList");
			logger.info("In saveReportList() dumpCheckedList   :  "+dumpCheckedList);
			logger.info("In saveReportList() dumpUnCheckedList   :  "+dumpUnCheckedList);
		
			String[] checkedList = dumpCheckedList.split(",");
			String[] unCheckedList =dumpUnCheckedList.split(",");
			
		
			String roleDesc=(String)dumpFunctionAccessVo.getRoleId();
			String moduleDesc=(String)dumpFunctionAccessVo.getModuleId();
			String  moduleId=(String)dumpFunctionAccessVo.getLbxModuleId();
			String  roleId=(String)dumpFunctionAccessVo.getLbxRoleId();	
			
			boolean deleteStatus=mb.deleteDumpFunction(moduleId, roleId);
			logger.info("Delete status   :   " + deleteStatus);
			boolean insertStatus=false;
			if(deleteStatus)
			   insertStatus = mb.insertDump(moduleId,roleId,checkedList,unCheckedList);
			logger.info("Insert  status   :   "+insertStatus);
			if(insertStatus)
				request.setAttribute("success","success");
			else
				request.setAttribute("error","error");
			
		return mapping.findForward("searchPage");
		
		}
	}
	
	
