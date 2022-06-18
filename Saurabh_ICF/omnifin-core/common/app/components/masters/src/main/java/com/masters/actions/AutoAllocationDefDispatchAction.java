package com.masters.actions;

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

import com.business.ejbClient.CreditManagementBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.AutoAllocationDefinitionVo;



public class AutoAllocationDefDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(AutoAllocationDefDispatchAction.class.getName());
	public ActionForward openAddAutoAllocationDefinition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddAutoAllocationDefinition()");
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
				
			    return mapping.findForward("openAdd");	
			}

	public ActionForward saveAutoAllocationDefDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info(" in saveAutoAllocationDefDetails() IN ACTION");
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
		
		DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		
		AutoAllocationDefinitionVo vo = new AutoAllocationDefinitionVo();
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);	
		
        
        CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String sms="";
		String result="";
		
		result = cm.saveAutoAllocationDetailBean(vo);
		
		logger.info("In saveAutoAllocationDefDetails Result : "+result);
		
		//request.setAttribute("save", "save");
		if(result.trim().equals("ERROR"))
			request.setAttribute("ERROR", "ERROR");
		if(result.trim().equals("EXIST"))
			request.setAttribute("EXIST", "EXIST");
		if(result.trim().equals("SAVE"))
		{
			request.setAttribute("SAVE", "SAVE");
//			ArrayList<AutoAllocationDefinitionVo> list = cm.getAutoAllocationDefDataBean(vo);
//			logger.info("list: "+list.size());
//			request.setAttribute("list", list);
		}
	   
		return mapping.getInputForward();
	}
	
	public ActionForward openEditAutoAllocationDef(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		AutoAllocationDefinitionVo vo = new AutoAllocationDefinitionVo();
		ServletContext context = getServlet().getServletContext();
				logger.info("In openEditAutoAllocationDef");
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
				vo.setNpaStage(request.getParameter("npaStage"));
				logger.info("repayType ::::::::::::::::::::::::::::: "+request.getParameter("repayType"));
				vo.setRepayType(request.getParameter("repayType"));
				
				CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
								
				ArrayList<AutoAllocationDefinitionVo> list = cm.getAutoAllocationDefDataBean(vo);
				
				if(list.size()>0)
				{
					
					AutoAllocationDefinitionVo vo2 = (AutoAllocationDefinitionVo)list.get(0);
					logger.info("allocation status :  "+vo2.getAllocationStatus());
					request.setAttribute("status", vo2.getAllocationStatus());
					logger.info("list: "+list.size());
					request.setAttribute("list", list);
				}
				
				//request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
	
	
	public ActionForward updateAutoAllocationDefDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateAutoAllocationDefDetails.......");
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
		
		AutoAllocationDefinitionVo vo = new AutoAllocationDefinitionVo();
		DynaValidatorForm commonDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, commonDynaValidatorForm);	
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		
		CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);

		
		logger.info("NpaStage....."+vo.getNpaStage());
		
        String result ="";
        result=cm.updateAutoAllocationDetailBean(vo);
        
    	logger.info("In updateAutoAllocationDefDetails() Result : "+result);
		
		if(result.trim().equals("UPDATE"))
		{
			//request.setAttribute("editValUpdate", "editValUpdate");
			request.setAttribute("UPDATE", "UPDATE");
		}
		else
		{
			request.setAttribute("ERROR", "ERROR");
		}

        return mapping.getInputForward();
		
	}
	
	public ActionForward searchAutoAllocationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AutoAllocationDefinitionVo vo = new AutoAllocationDefinitionVo();
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
        DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);
		
		CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		ArrayList<AutoAllocationDefinitionVo> list = new ArrayList<AutoAllocationDefinitionVo>();
		
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
		
		vo.setCurrentPageLink(currentPageLink);
		list= cm.searchAutoAllocationBean(vo);

	    logger.info("In NPASatgeMasterBehindAction....list"+list.size());
		
	    request.setAttribute("list", list);
		
		logger.info("list.isEmpty(): "+list.isEmpty());
		request.setAttribute("list",list);
		if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
			request.setAttribute("sms","No");
		}
        return mapping.findForward("success");
	}
	
	

}
