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
import com.payout.vo.TaxMasterVO;


public class TaxMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(TaxMasterDispatchAction.class.getName());
	public ActionForward openAddTax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In TaxMasterDispatchAction........openAddTax()......");
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here openAddTax method of TaxMasterDispatchAction action the session is out----------------");
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
		public ActionForward saveTax(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In TaxMasterDispatchAction........saveActivity()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here saveActivity method of TaxMasterDispatchAction action the session is out----------------");
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
			TaxMasterVO taxMasterVO=new TaxMasterVO();
			DynaValidatorForm CommonPayDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(taxMasterVO, CommonPayDynaForm);
			taxMasterVO.setMakerId(userobj.getUserId());
			taxMasterVO.setMakerDate(userobj.getBusinessdate());
		
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
			
	        logger.info("ActivityVode: "+taxMasterVO.getLbxActivityCode());
	        String resultStr=payMaster.saveTaxMaster(taxMasterVO);

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
		public ActionForward updateTax(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In TaxMasterDispatchAction updateActivity()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here updateActivity method of TaxMasterDispatchAction action the session is out----------------");
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
			TaxMasterVO taxMasterVO=new TaxMasterVO();
			DynaValidatorForm TaxMasterDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(taxMasterVO, TaxMasterDynaForm);
			taxMasterVO.setMakerId(userobj.getUserId());
			taxMasterVO.setMakerDate(userobj.getBusinessdate());
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String resultStr=payMaster.updateTaxMaster(taxMasterVO);

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
		
		public ActionForward openEditTax(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			logger.info("In TaxMasterDispatchAction openEditActivity()......");
			HttpSession session = request.getSession();
			boolean flag=false;
			UserObject userobj=(UserObject)session.getAttribute("userobject");
			if(userobj==null){
				logger.info("here openEditActivity method of TaxMasterDispatchAction action the session is out----------------");
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
			
			
			
			TaxMasterVO taxMasterVO=new TaxMasterVO();
			DynaValidatorForm TaxMasterDynaForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(taxMasterVO, TaxMasterDynaForm);
			logger.info("taxId :-------------"+request.getParameter("taxId"));
			taxMasterVO.setTaxId(CommonFunction.checkNull(request.getParameter("taxId")));
			
			PayoutBusinessMasterSessionBeanRemote payMaster = (PayoutBusinessMasterSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(PayoutBusinessMasterSessionBeanRemote.REMOTE_IDENTITY, request);
			
	        ArrayList<TaxMasterVO> list=new ArrayList<TaxMasterVO>();
	        list= payMaster.openEditTax(taxMasterVO);

			request.setAttribute("status", list.get(0).getRecStatus());
			request.setAttribute("list",list);
			request.setAttribute("editVal","editVal");
			return mapping.findForward("openAdd");
		}	
	

}
