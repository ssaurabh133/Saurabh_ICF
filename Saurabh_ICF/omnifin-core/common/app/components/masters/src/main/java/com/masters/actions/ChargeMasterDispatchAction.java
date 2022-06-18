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

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ChargeMasterVo;


public class ChargeMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ChargeMasterDispatchAction.class.getName());
	public ActionForward openAddCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddCharge()");
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

	public ActionForward saveChargeDetails(ActionMapping mapping, ActionForm form,
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
		boolean flag=false;
		ServletContext context = getServlet().getServletContext();
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
		DynaValidatorForm ChargeMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		ChargeMasterVo chargeMasterVo = new ChargeMasterVo();
		logger.info("lbxCalculatedOn...."+request.getParameter("lbxCalculatedOn"));
		chargeMasterVo.setMakerId(userId);
		chargeMasterVo.setMakerDate(bDate);
		org.apache.commons.beanutils.BeanUtils.copyProperties(chargeMasterVo, ChargeMasterAddDynaValidatorForm);	
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String sms="";
		logger.info("lbxCalculatedOn...chargeMasterVo."+chargeMasterVo.getLbxCalculatedOn());
		boolean status = cpm.insertChargeMaster(chargeMasterVo);
		
		logger.info("Inside Charge Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
	    logger.info("status"+status);
	    //ArrayList<ChargeMasterVo> list = masterDAO.getChargeData();
	    //logger.info("list: "+list.size());
		//request.setAttribute("list", list);
		return mapping.getInputForward();
	}
	public ActionForward openEditCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ChargeMasterVo chargeMasterVo=new ChargeMasterVo(); 
		ServletContext context = getServlet().getServletContext();
				logger.info("In openEditCharge");
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
				chargeMasterVo.setCharge(request.getParameter("chargeSearchId"));
				//chargeMasterVo.setLbxChargeSearch(request.getParameter("chargeSearchId"));
				logger.info("In openEditCharge---chargeId---- by getpara-"+request.getParameter("chargeSearchId"));  
				
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
			      ArrayList<ChargeMasterVo> list = cpm.searchChargeData(chargeMasterVo);
				
				logger.info("In openEditCharge chargeMasterVo list"+list.size());
				request.setAttribute("list", list);
				ChargeMasterVo cMVo=new ChargeMasterVo();
				cMVo=list.get(0);
				logger.info("In openEditCharge---ChargeStatus---- by   getpara by vo-"+cMVo.getChargeStatus());
				logger.info("In openEditCharge---TdsStatus---- by   getpara by vo-"+cMVo.getTdsStatus());
				logger.info("In openEditCharge---TaxStatus---- by   getpara by vo-"+cMVo.getTaxStatus());
				logger.info("In openEditCharge---TaxInclusiveStatus---- by   getpara by vo-"+cMVo.getTaxInclusiveStatus());
				
				request.setAttribute("chargeStatus", cMVo.getChargeStatus());
				request.setAttribute("taxInclusiveStatus", cMVo.getTaxInclusiveStatus());
				request.setAttribute("taxStatus", cMVo.getTaxStatus());
				request.setAttribute("tdsStatus", cMVo.getTdsStatus());
				
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
		
	public ActionForward updateSearchCharge(ActionMapping mapping, ActionForm form,
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
		ChargeMasterVo chargeMasterVo = new ChargeMasterVo();
		DynaValidatorForm ChargeMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(chargeMasterVo, ChargeMasterAddDynaValidatorForm);	
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String chargeId=(String)chargeMasterVo.getChargeId();
//		boolean status = masterDAO.insertAgencyMaster(vo);
		
		logger.info("chargeId"+chargeId);
		
		ArrayList<ChargeMasterVo> detailList = cpm.searchChargeData(chargeId);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}
	public ActionForward updateCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateCharge.......");
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
		ChargeMasterVo chargeMasterVo=new ChargeMasterVo(); 
		DynaValidatorForm ChargeMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		 String chargeId= request.getParameter("chargeId"); 
		org.apache.commons.beanutils.BeanUtils.copyProperties(chargeMasterVo, ChargeMasterAddDynaValidatorForm);	
        
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        boolean status=cpm.updateChargeData(chargeMasterVo,chargeId);
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
