package com.payout.actions;

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

import com.business.PayoutCilent.PayoutBusinessMasterSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.payout.vo.SchemeBpMapVO;

public class SchemeBpMapDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(SchemeBpMapDispatchAction.class.getName());
	public ActionForward openAddSchemeBpMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In SchemeBpMapDispatchAction........openAddSchemeBpMap()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openAddSchemeBpMap method of SchemeBpMapDispatchAction action the session is out----------------");
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

		
		return mapping.findForward("openAdd");
	}
		public ActionForward saveSchemeBpMap(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In SchemeBpMapDispatchAction........saveSchemeBpMap()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here saveSchemeBpMap method of SchemeBpMapDispatchAction action the session is out----------------");
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
			SchemeBpMapVO schemeBpMapVO=new SchemeBpMapVO();
			DynaValidatorForm schemBpMapDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(schemeBpMapVO, schemBpMapDynaForm);
			schemeBpMapVO.setMakerId(userobj.getUserId());
			schemeBpMapVO.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String resultStr=payMaster.saveSchemeBPMap(schemeBpMapVO);
			String sms="";
			if(resultStr.equalsIgnoreCase("Saved")){
				sms="S";
				request.setAttribute("sms",sms);
			}else if(resultStr.equalsIgnoreCase("already")){
				sms="A";
				request.setAttribute("sms",sms);
			}else if(resultStr.equalsIgnoreCase("priority")){
				sms="P";
				request.setAttribute("sms",sms);
				
			}else{
				sms="E";
				request.setAttribute("sms",sms);
			}
			return mapping.findForward("openAdd");
			}
		public ActionForward updateSchemeBpMap(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In SchemeBpMapDispatchAction updateSchemeBpMap()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here updateSchemeBpMap method of SchemeBpMapDispatchAction action the session is out----------------");
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
			SchemeBpMapVO schemeBpMapVO=new SchemeBpMapVO();
			DynaValidatorForm schemBpMapDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(schemeBpMapVO, schemBpMapDynaForm);
			schemeBpMapVO.setMakerId(userobj.getUserId());
			schemeBpMapVO.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String resultStr=payMaster.updateSchemeBPMap(schemeBpMapVO);
			ArrayList<SchemeBpMapVO> list=new ArrayList<SchemeBpMapVO>();
			String sms="";
			if(resultStr.equalsIgnoreCase("Saved")){
				sms="S";
				request.setAttribute("sms",sms);
			}else if(resultStr.equalsIgnoreCase("already")){
				sms="A";
				request.setAttribute("sms",sms);
				list= payMaster.openEditSchemeBPMap(schemeBpMapVO);
				request.setAttribute("status", list.get(0).getRecStatus());
			     request.setAttribute("list",list);
				 request.setAttribute("editVal","editVal");
			}else if(resultStr.equalsIgnoreCase("priority")){
				sms="P";
				request.setAttribute("sms",sms);
				list= payMaster.openEditSchemeBPMap(schemeBpMapVO);
				request.setAttribute("status", list.get(0).getRecStatus());
			     request.setAttribute("list",list);
				request.setAttribute("editVal","editVal");
			}else{
				sms="E";
				request.setAttribute("sms",sms);
				list= payMaster.openEditSchemeBPMap(schemeBpMapVO);
				request.setAttribute("status", list.get(0).getRecStatus());
			     request.setAttribute("list",list);
				request.setAttribute("editVal","editVal");
			}
			return mapping.findForward("openAdd");
			}
		
		public ActionForward openEditSchemeBpMap(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In SchemeBpMapDispatchAction openEditSchemeBpMap()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openEditSchemeBpMap method of SchemeBpMapDispatchAction action the session is out----------------");
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
			
			
			
			SchemeBpMapVO schemeBpMapVO=new SchemeBpMapVO();
			DynaValidatorForm schemBpMapDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(schemeBpMapVO, schemBpMapDynaForm);
			schemeBpMapVO.setMakerId(userobj.getUserId());
			schemeBpMapVO.setMakerDate(userobj.getBusinessdate());
			logger.info("openEditSchemeBpMap mapId:-------------"+request.getParameter("mapId"));
			schemeBpMapVO.setMapId(CommonFunction.checkNull(request.getParameter("mapId")));
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	       
			ArrayList<SchemeBpMapVO> list=new ArrayList<SchemeBpMapVO>();
			list= payMaster.openEditSchemeBPMap(schemeBpMapVO);
			request.setAttribute("status", list.get(0).getRecStatus());
			request.setAttribute("specific", list.get(0).getSpecificTar());
		     request.setAttribute("list",list);
			 request.setAttribute("editVal","editVal");
			
			return mapping.findForward("openAdd");
		}	
	


}
