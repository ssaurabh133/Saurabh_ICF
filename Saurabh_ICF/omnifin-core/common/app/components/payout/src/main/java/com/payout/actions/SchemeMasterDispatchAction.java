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
import com.payout.vo.SchemeVO;


public class SchemeMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(SchemeMasterDispatchAction.class.getName());
	public ActionForward openAddScheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In SchemeMasterDispatchAction........openAddTax()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openAddScheme method of SchemeMasterDispatchAction action the session is out----------------");
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
		public ActionForward saveScheme(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In SchemeMasterDispatchAction........saveActivity()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here saveActivity method of SchemeMasterDispatchAction action the session is out----------------");
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
			SchemeVO schemeVO=new SchemeVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(schemeVO, CommonPayDynaForm);
			schemeVO.setMakerId(userobj.getUserId());
			schemeVO.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
	        String resultStr=payMaster.saveSchemeMaster(schemeVO);

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
		public ActionForward updateScheme(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In SchemeMasterDispatchAction updateScheme()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here updateScheme method of SchemeMasterDispatchAction action the session is out----------------");
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
			SchemeVO schemeVO=new SchemeVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(schemeVO, CommonPayDynaForm);
			schemeVO.setMakerId(userobj.getUserId());
			schemeVO.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
			String resultStr=payMaster.updateSchemeMaster(schemeVO);
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
		
		public ActionForward openEditScheme(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In SchemeMasterDispatchAction openEditActivity()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openEditActivity method of SchemeMasterDispatchAction action the session is out----------------");
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
			
			
			
			SchemeVO schemeVO=new SchemeVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(schemeVO, CommonPayDynaForm);
			logger.info("schemeName:--------"+request.getParameter("schemeName"));
			schemeVO.setSchemeName(CommonFunction.checkNull(request.getParameter("schemeName")));
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
	        
	        ArrayList<SchemeVO> list=new ArrayList<SchemeVO>();
	        list= payMaster.openEditSchemeM(schemeVO);

	        ArrayList<SchemeVO> listDtl=new ArrayList<SchemeVO>();
	        listDtl= payMaster.openEditSchemeDtl(schemeVO);
	        logger.info("slab On : "+list.get(0).getSlabOn());
	        logger.info("SchemeParameter : "+list.get(0).getSchemeParameter());
	        if(CommonFunction.checkNull(list.get(0).getSchemeParameter()).equalsIgnoreCase("CW")
	         ||CommonFunction.checkNull(list.get(0).getSchemeParameter()).equalsIgnoreCase("PW")){
	        	 logger.info("slab On if: "+list.get(0).getSlabOn());
	        	 request.setAttribute("slab", "N");
	        }else{
	        	 request.setAttribute("slab", "Y");
	        	 logger.info("slab On else: "+list.get(0).getSlabOn());
	        }
			request.setAttribute("status", list.get(0).getRecStatus());
			request.setAttribute("taxApp", list.get(0).getServiceTaxApp());
			request.setAttribute("tdsApp", list.get(0).getTdsApp());
			
			request.setAttribute("list",list);
			request.setAttribute("listDtl",listDtl);
			request.setAttribute("editVal","editVal");
			return mapping.findForward("openAdd");
		}	
	

}
