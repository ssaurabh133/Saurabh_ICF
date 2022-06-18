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
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.DebtManagementBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.capsVO.ActionCodeMasterVo;


public class ActionCodeMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ActionCodeMasterDispatchAction.class.getName());
	
	public ActionForward openAddActionCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
				
				logger.info(" in openAddActionCode()");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info("here openAddActionCode method of ActionCodeMasterDispatchAction action the session is out----------------");
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
				request.setAttribute("save", "save");
			    return mapping.findForward("openAdd");	
			}
	
	public ActionForward openEditActionCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ActionCodeMasterVo actionCodeMasterVo=new ActionCodeMasterVo(); 
				logger.info("In openEditActionCode");
				HttpSession session = request.getSession();
				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				if(userobj==null){
					logger.info("here openEditActionCode method of ActionCodeMasterDispatchAction action the session is out----------------");
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
				
				actionCodeMasterVo.setCodeIdModify(CommonFunction.checkNull(request.getParameter("codeId")));
				logger.info("In openEditActionCode---ID---- by getpara-"+request.getParameter("codeId"));  
				
				DebtManagementBussinessSessionBeanRemote dm = (DebtManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(DebtManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				ArrayList<ActionCodeMasterVo> list = dm.searchActionCodeData(actionCodeMasterVo);
				
				logger.info("In openEditActionCode ActionCodeMasterVo list"+list.size());
				request.setAttribute("list", list);
				
				ActionCodeMasterVo docVo=new ActionCodeMasterVo();
				docVo=list.get(0);
				logger.info("In openEditActionCode---status---- by   getpara by vo-"+docVo.getCodeStatus());
				request.setAttribute("status", docVo.getCodeStatus());
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveActionCodeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		//UserObject userobj=new UserObject();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here saveActionCodeDetails action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		boolean flag=false;
		
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
		DynaValidatorForm ActionCodeMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		ActionCodeMasterVo actionCodeMasterVo = new ActionCodeMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(actionCodeMasterVo, ActionCodeMasterAddDyanavalidatiorForm);
		
		DebtManagementBussinessSessionBeanRemote dm = (DebtManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(DebtManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		logger.info("In saveActionCodeDetails...."+ActionCodeMasterAddDyanavalidatiorForm.getString("codeDesc"));

		actionCodeMasterVo.setMakerId(userId);
		actionCodeMasterVo.setMakerDate(bDate);
		String sms="";
		
		boolean status = dm.insertActionCodeMaster(actionCodeMasterVo);
		logger.info("Inside Action Code Master Action.....displaying status...."+status);
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
	
	
	public ActionForward updateActionCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateActionCode.......");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			bDate=userobj.getBusinessdate();
		}else{
			logger.info("here updateActionCode method of  ActionCodeMasterDispatchAction action the session is out----------------");
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
		
		ActionCodeMasterVo actionCodeMasterVo=new ActionCodeMasterVo(); 
		DynaValidatorForm ActionCodeMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		
		org.apache.commons.beanutils.BeanUtils.copyProperties(actionCodeMasterVo, ActionCodeMasterAddDyanavalidatiorForm);	
		
		DebtManagementBussinessSessionBeanRemote dm = (DebtManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(DebtManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		logger.info("In updateSearchActionCodeDetails---status-----"+actionCodeMasterVo.getCodeStatus());
		actionCodeMasterVo.setCodeStatus(CommonFunction.checkNull(request.getParameter("codeStatus")));
		logger.info("In updateSearchActionCodeDetails---status---- by getpara-"+request.getParameter("codeStatus"));
		
		actionCodeMasterVo.setCodeId(CommonFunction.checkNull(request.getParameter("codeId")));
		logger.info("In updateSearchActionCodeDetails---status---- codeId-"+request.getParameter("codeId"));  

			
		actionCodeMasterVo.setMakerId(userId);
		actionCodeMasterVo.setMakerDate(bDate);
        boolean status=dm.updateActionCodeData(actionCodeMasterVo);
        String sms="";
        if(status){
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
