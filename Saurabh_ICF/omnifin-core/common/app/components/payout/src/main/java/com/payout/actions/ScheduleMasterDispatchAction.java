package com.payout.actions;

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

import com.business.PayoutCilent.PayoutBusinessMasterSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.payout.vo.ScheduleMasterVO;



public class ScheduleMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ScheduleMasterDispatchAction.class.getName());
	public ActionForward openAddSchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In ScheduleMasterDispatchAction........openAddSchedule()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openAddSchedule method of ScheduleMasterDispatchAction action the session is out----------------");
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
			/*else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}*/
			return mapping.findForward("logout");
		}
		
		PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
     
		return mapping.findForward("openAdd");
	}
		public ActionForward saveSchedule(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In ScheduleMasterDispatchAction........saveSchedule()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here saveSchedule method of ScheduleMasterDispatchAction action the session is out----------------");
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
				/*else if(strFlag.equalsIgnoreCase("BODCheck"))
				{
					context.setAttribute("msg", "B");
				}*/
				return mapping.findForward("logout");
			}
			
			ScheduleMasterVO scheduleMasterVO=new ScheduleMasterVO();
			DynaValidatorForm scheduleMasterDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(scheduleMasterVO, scheduleMasterDynaForm);
			scheduleMasterVO.setMakerId(userobj.getUserId());
			scheduleMasterVO.setMakerDate(userobj.getBusinessdate());
			
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
	        logger.info("ActivityVode: "+scheduleMasterVO.getLbxActivityCode());
	   
	        String resultStr=payMaster.saveScheduleMaster(scheduleMasterVO);
	        
			String sms="";
			logger.info("resultStr value in dispatch Action="+resultStr);
			if(resultStr.equalsIgnoreCase("saved")){
				sms="S";
				logger.info("resultStr value in dispatch Action inside saved ="+resultStr);
				request.setAttribute("sms",sms);
				request.setAttribute("msg",sms);
		
			}else if(resultStr.equalsIgnoreCase("Already")){
				sms="Already";
				request.setAttribute("sms",sms);
				request.setAttribute("msg",sms);
			}
			//else if(resultStr.iequalsIgnoreCase("DATA IS NOT VALID")||resultStr.equalsIgnoreCase("PAY_BP_SAVE ---UNKNOWN EXCEPTION IN SQL EXECUTION, CONTACT SYSTEM ADMINISTRATOR...")){
			else if(resultStr.startsWith("E"))	{
			sms="Invalid";
			String  resultStrSplit=resultStr.substring(1);
				System.out.println("resultStr$$$$"+resultStr);
				System.out.println("resultStrSplit$$$$"+resultStrSplit);
				request.setAttribute("sms",sms);
				request.setAttribute("msg",resultStrSplit);
			}
			else{
				sms="E";
				request.setAttribute("sms",sms);
				request.setAttribute("msg",sms);
			}
			return mapping.findForward("openAdd");
			}
		
		
		public ActionForward updateSchedule(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In ScheduleMasterDispatchAction updateSchedule()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here updateSchedule method of ScheduleMasterDispatchAction action the session is out----------------");
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
			ScheduleMasterVO masterVO=new ScheduleMasterVO();
			DynaValidatorForm scheduleMasterDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(masterVO, scheduleMasterDynaForm);
			masterVO.setMakerId(userobj.getUserId());
			masterVO.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String resultStr=payMaster.updateScheduleMaster(masterVO);
		/*	ArrayList<ActivityMasterVO> sourceList=payMaster.sourceList();
		    request.setAttribute("sourceList", sourceList);*/
			String sms="";
			if(resultStr.equalsIgnoreCase("saved")){
				sms="S";
				logger.info("resultStr value in dispatch Action inside saved ="+resultStr);
				request.setAttribute("sms",sms);
				request.setAttribute("msg",sms);
		
			}else if(resultStr.equalsIgnoreCase("Already")){
				sms="Already";
				request.setAttribute("sms",sms);
				request.setAttribute("msg",sms);
			}
			//else if(resultStr.iequalsIgnoreCase("DATA IS NOT VALID")||resultStr.equalsIgnoreCase("PAY_BP_SAVE ---UNKNOWN EXCEPTION IN SQL EXECUTION, CONTACT SYSTEM ADMINISTRATOR...")){
			else if(resultStr.startsWith("E"))	{
			sms="Invalid";
			String  resultStrSplit=resultStr.substring(1);
				System.out.println("resultStr$$$$"+resultStr);
				System.out.println("resultStrSplit$$$$"+resultStrSplit);
				request.setAttribute("sms",sms);
				request.setAttribute("msg",resultStrSplit);
			}
			else{
				sms="E";
				request.setAttribute("sms",sms);
				request.setAttribute("msg",sms);
			}
			return mapping.findForward("openAdd");
			}
		
		public ActionForward openEditScheduleMaster(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In ScheduleMasterDispatchAction openEditScheduleMaster()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openEditScheduleMaster method of ScheduleMasterDispatchAction action the session is out----------------");
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
			
			
			ScheduleMasterVO scheduleMasterVO=new ScheduleMasterVO();
			//TaxMasterVO taxMasterVO=new TaxMasterVO();
			DynaValidatorForm scheduleMasterDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(scheduleMasterVO, scheduleMasterDynaForm);
			logger.info("activityId :-------------"+request.getParameter("activityId"));
			scheduleMasterVO.setActivityId(CommonFunction.checkNull(request.getParameter("activityId")));
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request); 
	        
	        ArrayList<ScheduleMasterVO> list=new ArrayList<ScheduleMasterVO>();
	        list= payMaster.openEditScheduleMaster(scheduleMasterVO);
	        
			request.setAttribute("status", list.get(0).getRecStatus());
			request.setAttribute("list",list);
			request.setAttribute("editVal","editVal");
			return mapping.findForward("openAdd");
		}	
	

}
