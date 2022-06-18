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
import com.payout.vo.ActivityMasterVO;

public class ActivityMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ActivityMasterDispatchAction.class.getName());
	public ActionForward openAddActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In ActivityMasterDispatchAction........OpenAddActivity()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here OpenAddActivity method of ActivityMasterDispatchAction action the session is out----------------");
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
		
        ArrayList<ActivityMasterVO> sourceList=payMaster.sourceList();
        request.setAttribute("sourceList", sourceList);
		return mapping.findForward("openAdd");
	}
		public ActionForward saveActivity(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In ActivityMasterDispatchAction........saveActivity()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here saveActivity method of ActivityMasterDispatchAction action the session is out----------------");
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
			ActivityMasterVO activityMasterVo=new ActivityMasterVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(activityMasterVo, CommonPayDynaForm);
			activityMasterVo.setMakerId(userobj.getUserId());
			activityMasterVo.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String resultStr=payMaster.saveActivityCode(activityMasterVo);
			ArrayList<ActivityMasterVO> sourceList=payMaster.sourceList();
		    request.setAttribute("sourceList", sourceList);
			String sms="";
			if(resultStr.equalsIgnoreCase("saved")){
				sms="S";
				request.setAttribute("sms",sms);
			}else if(resultStr.equalsIgnoreCase("Already")){
				sms="Already";
				request.setAttribute("sms",sms);
			}
			else{
				sms="E";
				request.setAttribute("sms",sms);
			}
			return mapping.findForward("openAdd");
			}
		public ActionForward updateActivity(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In ActivityMasterDispatchAction updateActivity()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here updateActivity method of ActivityMasterDispatchAction action the session is out----------------");
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
			ActivityMasterVO activityMasterVo=new ActivityMasterVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(activityMasterVo, CommonPayDynaForm);
			activityMasterVo.setMakerId(userobj.getUserId());
			activityMasterVo.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String resultStr=payMaster.updateActivityCode(activityMasterVo);
			ArrayList<ActivityMasterVO> sourceList=payMaster.sourceList();
		    request.setAttribute("sourceList", sourceList);
			String sms="";
			if(resultStr.equalsIgnoreCase("saved")){
				sms="M";
				request.setAttribute("sms",sms);
			}else if(resultStr.equalsIgnoreCase("Already")){
				sms="Already";
				request.setAttribute("sms",sms);
			}
			else{
				sms="E";
				request.setAttribute("sms",sms);
			}
			return mapping.findForward("openAdd");
			}
		
		public ActionForward openEditActivity(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In ActivityMasterDispatchAction openEditActivity()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openEditActivity method of ActivityMasterDispatchAction action the session is out----------------");
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
			
			
			
			ActivityMasterVO activityMasterVo=new ActivityMasterVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(activityMasterVo, CommonPayDynaForm);
			logger.info("Activirt Code:-------------"+request.getParameter("activityCode"));
			activityMasterVo.setActivityCode(CommonFunction.checkNull(request.getParameter("activityCode")));
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	       
			ArrayList<ActivityMasterVO> list=new ArrayList<ActivityMasterVO>();
			ArrayList<ActivityMasterVO> sourceList=payMaster.sourceList();
		    request.setAttribute("sourceList", sourceList);
			list= payMaster.openEditActivity(activityMasterVo);
			request.setAttribute("status", list.get(0).getRecStatus());
			
			 request.setAttribute("list",list);
			 request.setAttribute("editVal","editVal");
			return mapping.findForward("openAdd");
		}	
	
}
