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
import com.payout.vo.BPMasterVO;

public class BpMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(BpMasterDispatchAction.class.getName());
	public ActionForward openAddBp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In BpMasterDispatchAction........openAddBp()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openAddBp method of BpMasterDispatchAction action the session is out----------------");
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
		public ActionForward saveBP(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In BpMasterDispatchAction........saveActivity()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here saveActivity method of BpMasterDispatchAction action the session is out----------------");
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
			BPMasterVO bpMasterVO=new BPMasterVO();
			DynaValidatorForm bpMasterDynaForm= (DynaValidatorForm)form;
			 logger.info("txtDistCode : "+request.getParameter("txtDistCode"));
			 logger.info("txtStateCode : "+request.getParameter("txtStateCode"));
			org.apache.commons.beanutils.BeanUtils.copyProperties(bpMasterVO, bpMasterDynaForm);
			bpMasterVO.setMakerId(userobj.getUserId());
			bpMasterVO.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
	        
			String resultStr=payMaster.saveBpMaster(bpMasterVO);
			ArrayList<ActivityMasterVO> sourceList=payMaster.sourceList();
		    request.setAttribute("sourceList", sourceList);
			String sms="";
			if(resultStr.equalsIgnoreCase("S")){
				sms="S";
				request.setAttribute("sms",sms);
			}else{
				sms="E";
				request.setAttribute("sms",sms);
			}
			return mapping.findForward("openAdd");
			}
		public ActionForward updateBP(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In BpMasterDispatchAction updateBP()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here updateBP method of BpMasterDispatchAction action the session is out----------------");
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
			BPMasterVO bpMasterVO=new BPMasterVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(bpMasterVO, CommonPayDynaForm);
			bpMasterVO.setMakerId(userobj.getUserId());
			bpMasterVO.setMakerDate(userobj.getBusinessdate());
			logger.info("bpId:-"+bpMasterVO.getBpId());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String resultStr=payMaster.updateBpMaster(bpMasterVO);
			ArrayList<ActivityMasterVO> sourceList=payMaster.sourceList();
		    request.setAttribute("sourceList", sourceList);
			String sms="";
			if(resultStr.equalsIgnoreCase("S")){
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
		
		public ActionForward openEditBp(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In BpMasterDispatchAction openEditBp()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openEditBp method of BpMasterDispatchAction action the session is out----------------");
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
			
			
			
			BPMasterVO bpMasterVO=new BPMasterVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(bpMasterVO, CommonPayDynaForm);
			logger.info("openEditBp bpId:-------------"+request.getParameter("bpId"));
			bpMasterVO.setBpId(CommonFunction.checkNull(request.getParameter("bpId")));
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	       
			ArrayList<BPMasterVO> list=new ArrayList<BPMasterVO>();
			ArrayList<ActivityMasterVO> sourceList=payMaster.sourceList();
		    request.setAttribute("sourceList", sourceList);
			list= payMaster.openEditBp(bpMasterVO);
			ArrayList<BPMasterVO> activityList=payMaster.openEditBpActivity(bpMasterVO);
			String activityStr="";
			for(int i=0;i<activityList.size();i++){
				activityStr=activityStr+"|"+activityList.get(i).getLbxActivityCode();
			}
			request.setAttribute("status", list.get(0).getRecStatus());
			request.setAttribute("activityList", activityList);
			 request.setAttribute("list",list);
			 request.setAttribute("editVal","editVal");
			 request.setAttribute("activityStr", activityStr);
			return mapping.findForward("openAdd");
		}	
	

}
