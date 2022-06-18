package com.masters.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.BaseRateMasterVo;


public class BaseRateMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(BaseRateMasterDispatchAction.class.getName());
	public ActionForward openAddBaseRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddBaseRate()");
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
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList baseRateTypeList = cpm.getbaseRateTypeList();
				request.setAttribute("baseRateTypeList", baseRateTypeList);
				
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
	public ActionForward openEditBaseRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		BaseRateMasterVo baseRateMasterVo=new BaseRateMasterVo(); 
				logger.info("In openEditBranch");
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
				
				baseRateMasterVo.setBaseRateTypeSearch(request.getParameter("baseRateTypeSearch"));
				baseRateMasterVo.setEffectiveFromDateSearch(request.getParameter("effectiveFromDateSearch"));
				
				logger.info("In openEditBaseRate---type---- by getpara-"+StringEscapeUtils.escapeHtml(request.getParameter("baseRateTypeSearch")));  
			
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList baseRateTypeList = cpm.getbaseRateTypeList();
				request.setAttribute("baseRateTypeList", baseRateTypeList);
				ArrayList<BaseRateMasterVo> list = cpm.searchBaseRateData(baseRateMasterVo);
				if(list.size()>0)
				baseRateMasterVo=list.get(0);
				logger.info("In openEditBaseRate baseRateMasterVo list"+list.size());
				request.setAttribute("list", list);
				request.setAttribute("status", baseRateMasterVo.getBaseRateStatus());
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveBaseRateDetails(ActionMapping mapping, ActionForm form,
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
		
		userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		DynaValidatorForm BaseRateMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		BaseRateMasterVo baseRateMasterVo = new BaseRateMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(baseRateMasterVo, BaseRateMasterAddDynaValidatorForm);
		
		baseRateMasterVo.setMakerId(userId);
		baseRateMasterVo.setMakerDate(bDate);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String sms="";
		
		boolean status = cpm.insertBaseRateMaster(baseRateMasterVo);
		logger.info("Inside Base Rate Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
	    logger.info("status"+status);
	    ArrayList baseRateTypeList = cpm.getbaseRateTypeList();
		request.setAttribute("baseRateTypeList", baseRateTypeList);
	    ArrayList<BaseRateMasterVo> list = cpm.getBaseRateData();
	    logger.info("list: "+list.size());
		request.setAttribute("list", list);
		return mapping.getInputForward();
	}

	public ActionForward updateSearchBaseRate(ActionMapping mapping, ActionForm form,
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
		
		BaseRateMasterVo baseRateMasterVo = new BaseRateMasterVo();
		DynaValidatorForm BaseRateMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(baseRateMasterVo, BaseRateMasterAddDynaValidatorForm);	
		String baseRateType=(String)baseRateMasterVo.getBaseRateType();

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		logger.info("baseRateType"+baseRateType);
		
		ArrayList<BaseRateMasterVo> detailList = cpm.searchBaseRateData(baseRateType);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}
	public ActionForward updateBaseRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateBaseRate.......");
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
		
		BaseRateMasterVo baseRateMasterVo=new BaseRateMasterVo(); 
		DynaValidatorForm BaseRateMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(baseRateMasterVo, BaseRateMasterAddDynaValidatorForm);	
		
		logger.info("In updateBaseRate---status-----"+baseRateMasterVo.getBaseRateStatus());
		baseRateMasterVo.setBaseRateStatus(request.getParameter("baseRateStatus"));
		logger.info("In updateBaseRate---status---- by getpara-"+request.getParameter("baseRateStatus"));
		
		baseRateMasterVo.setBaseRateType(request.getParameter("baseRateType"));
		logger.info("In updateBaseRate---status---- by getpara-"+request.getParameter("baseRateType"));  
		logger.info("In updateBaseRate---status---- by getpara by vo-"+baseRateMasterVo.getBaseRateType());
		logger.info("In updateBaseRate---------");    

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        boolean status=cpm.updateBaseRateData(baseRateMasterVo);
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
