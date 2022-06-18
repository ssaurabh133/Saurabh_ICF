//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : Dispatch Action For Industry Master-->
//Documentation : -->

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

import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.IndustryMasterVo;


public class IndustryMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(IndustryMasterDispatchAction.class.getName());
	public ActionForward openAddIndustry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddIndustry()");
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
	public ActionForward openEditIndustry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		IndustryMasterVo industryMasterVo=new IndustryMasterVo(); 
		ServletContext context = getServlet().getServletContext();
				logger.info("In openEditIndustry");
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
				industryMasterVo.setIndustryId(request.getParameter("industryId"));
				logger.info("In openEditIndustry---status---- by getpara-"+request.getParameter("industryId"));  
				
				
				CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		        
				ArrayList<IndustryMasterVo> list = bp.searchIndustryData(industryMasterVo);
				industryMasterVo=list.get(0);
				logger.info("In openEditIndustry industryMasterVo list"+list.size());
				request.setAttribute("list", list);
				request.setAttribute("status", industryMasterVo.getIndustryStatus());
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveIndustryDetails(ActionMapping mapping, ActionForm form,
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
		
		DynaValidatorForm IndustryMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		IndustryMasterVo industryMasterVo = new IndustryMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(industryMasterVo, IndustryMasterAddDynaValidatorForm);	
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		industryMasterVo.setMakerId(userId);
		industryMasterVo.setMakerDate(bDate);
		
		String sms="";
		
		String status = bp.insertIndustryMaster(industryMasterVo);
		logger.info("Inside Industry Master Action.....displaying status...."+status);
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
	public ActionForward updateIndustryBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		IndustryMasterVo industryMasterVo = new IndustryMasterVo();
		DynaValidatorForm IndustryMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(industryMasterVo, IndustryMasterAddDynaValidatorForm);	
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String industryDesc=(String)industryMasterVo.getIndustryDesc();
//		boolean status = masterDAO.insertAgencyMaster(vo);
		
		logger.info("industryDesc"+industryDesc);
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
		ArrayList<IndustryMasterVo> detailList = bp.searchIndustryData(industryDesc);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}
	public ActionForward updateIndustry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateIndustry.......");
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
		IndustryMasterVo industryMasterVo=new IndustryMasterVo(); 
		DynaValidatorForm IndustryMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(industryMasterVo, IndustryMasterAddDynaValidatorForm);	
		
		CommonMasterBussinessSessionBeanRemote bp = (CommonMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CommonMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		industryMasterVo.setIndustryStatus(request.getParameter("industryStatus"));
		
		
		industryMasterVo.setIndustryId(request.getParameter("industryId"));
	 
     
        boolean status=bp.updateIndustryData(industryMasterVo);
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
