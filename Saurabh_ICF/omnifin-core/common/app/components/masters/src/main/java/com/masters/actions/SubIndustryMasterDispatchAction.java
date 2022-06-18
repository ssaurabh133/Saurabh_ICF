//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : Dispatch Action For SubIndustry Master-->
//Documentation : -->

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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.SubIndustryMasterVo;


public class SubIndustryMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(SubIndustryMasterDispatchAction.class.getName());
	public ActionForward openAddSubIndustry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddSubIndustry()");
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
	public ActionForward openEditSubIndustry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
		        SubIndustryMasterVo subIndustryMasterVo=new SubIndustryMasterVo(); 
				logger.info("In openEditSubIndustry");
				
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
				
				subIndustryMasterVo.setSubIndustryId(request.getParameter("subIndustryId"));
				logger.info("In openEditSubIndustry---Desc---- by getpara-"+request.getParameter("subIndustryId"));  
				
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList<SubIndustryMasterVo> list = bp.searchSubIndustryData(subIndustryMasterVo);
				subIndustryMasterVo=list.get(0);
				logger.info("In openEditSubIndustry subIndustryMasterVo list"+list.size());
				request.setAttribute("list", list);
				logger.info("Status:--------"+subIndustryMasterVo.getSubIndustryStatus());
				
				request.setAttribute("status", subIndustryMasterVo.getSubIndustryStatus());
				request.setAttribute("editVal", "editVal");
			    return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveSubIndustryDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(false);
		UserObject userobj=new UserObject();
		userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
				
		}
		
		ServletContext context = getServlet().getServletContext();
		boolean flag=false;
		
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
		DynaValidatorForm SubIndustryMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		SubIndustryMasterVo subIndustryMasterVo = new SubIndustryMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(subIndustryMasterVo, SubIndustryMasterAddDynaValidatorForm);	
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		subIndustryMasterVo.setMakerId(userId);
		subIndustryMasterVo.setMakerDate(bDate);
		
		String sms="";
		
		String status = bp.insertSubIndustryMaster(subIndustryMasterVo);
		logger.info("Inside Sub Industry Master Action.....displaying status...."+status);
		if(status.equalsIgnoreCase("S")){
			sms="S";
			request.setAttribute("sms",sms);
		}
		if(status.equalsIgnoreCase("E")){
			sms="E";
			request.setAttribute("sms",sms);
		}
		if(status.equalsIgnoreCase("EX")){
			sms="EX";
			request.setAttribute("sms",sms);
		}
	    logger.info("status"+status);
	
		return mapping.getInputForward();
	}
	public ActionForward updateSubIndustryBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SubIndustryMasterVo subIndustryMasterVo = new SubIndustryMasterVo();
		DynaValidatorForm SubIndustryMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(subIndustryMasterVo, SubIndustryMasterAddDynaValidatorForm);	
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String subIndustryId=(String)subIndustryMasterVo.getSubIndustryId();
//		boolean status = masterDAO.insertAgencyMaster(vo);
		ServletContext context = getServlet().getServletContext();
		logger.info("subIndustryDesc"+subIndustryId);
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
		
		ArrayList<SubIndustryMasterVo> detailList = bp.searchSubIndustryData(subIndustryId);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}
	public ActionForward updateSubIndustry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateIndustry.......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		ServletContext context = getServlet().getServletContext();
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
		SubIndustryMasterVo subIndustryMasterVo=new SubIndustryMasterVo(); 
		DynaValidatorForm SubIndustryMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(subIndustryMasterVo, SubIndustryMasterAddDynaValidatorForm);	
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
        
		logger.info("In updateSubIndustryDetails---status-----"+subIndustryMasterVo.getSubIndustryStatus());
		subIndustryMasterVo.setSubIndustryStatus(request.getParameter("subIndustryStatus"));
		logger.info("In updateSubIndustryDetails---status---- by getpara-"+request.getParameter("subIndustryStatus"));
		
		subIndustryMasterVo.setSubIndustryId(request.getParameter("subIndustryId"));
		logger.info("In updateSubIndustryDetails---status---- by getpara-"+request.getParameter("subIndustryId"));  
		logger.info("In updateSubIndustryDetails---status---- by getpara by vo-"+subIndustryMasterVo.getSubIndustryId());
		logger.info("In updateSubIndustryDetails---------");    
 
        boolean status=bp.updateSubIndustryData(subIndustryMasterVo);
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
