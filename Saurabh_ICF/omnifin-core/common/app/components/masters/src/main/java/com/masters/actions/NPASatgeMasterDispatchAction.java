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
import com.business.ejbClient.CreditManagementBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.NPAMasterVo;


public class NPASatgeMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(NPASatgeMasterDispatchAction.class.getName());
	public ActionForward openAddNPAStage(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception {
				ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddNPAStage()");
				HttpSession session = request.getSession();
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				
				CreditManagementBussinessSessionBeanRemote dm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				ArrayList<NPAMasterVo> proDesc = dm.getProductId();
				request.setAttribute("product", proDesc);  
				
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
				form.reset(mapping, request);
			    return mapping.findForward("openAdd");	
			}

	public ActionForward saveNPAStageDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In saveNPAStageDetails().........");
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
		
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		
		CreditManagementBussinessSessionBeanRemote dm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		ArrayList<NPAMasterVo> proDesc = dm.getProductId();
		request.setAttribute("product", proDesc);  
		String npa = request.getParameter("npaSearchStage");
		String product = dm.getProduct(npa);
		request.setAttribute("productId", product); 
		
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
		String userId=null;
		String bDate=null;
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		DynaValidatorForm NPAStageMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		
		NPAMasterVo vo = new NPAMasterVo();
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, NPAStageMasterAddDynaValidatorForm);	
		CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		String result = cm.insertNPAStageMaster(vo);
		request.setAttribute("save", "save");

        if((CommonFunction.checkNull(result)).trim().equalsIgnoreCase("REGULAR"))
			request.setAttribute("REGULAR", "REGULAR");

        if((CommonFunction.checkNull(result)).trim().equalsIgnoreCase("ERROR"))
			request.setAttribute("ERROR", "ERROR");

        if((CommonFunction.checkNull(result)).trim().equalsIgnoreCase("EXIST"))
			request.setAttribute("EXIST", "EXIST");
        
        if((CommonFunction.checkNull(result)).trim().equalsIgnoreCase("SAVE"))
			request.setAttribute("SAVE", "SAVE");
				
	    ArrayList<NPAMasterVo> list = cm.getNPAStageData();
		request.setAttribute("list", list);
		form.reset(mapping, request);
		vo=null;
		userId=null;
		bDate=null;
		cm=null;
		return mapping.getInputForward();
	}
	
	public ActionForward openEditNPAStage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
				NPAMasterVo vo=new NPAMasterVo(); 
				ServletContext context = getServlet().getServletContext();
				logger.info("In openEditNPAStage");
				HttpSession session = request.getSession();
//				boolean flag=false;
				UserObject userobj=(UserObject)session.getAttribute("userobject");
				Object sessionId = session.getAttribute("sessionID");
				//for check User session start
				
				CreditManagementBussinessSessionBeanRemote dm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				ArrayList<NPAMasterVo> proDesc = dm.getProductId();
				request.setAttribute("product", proDesc); 
				String proid = request.getParameter("productId");
				vo.setProductId(proid);
				String npa = request.getParameter("npaSearchStage");
				String product = dm.getProduct(npa);
				request.setAttribute("productId", product);
				
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
				vo.setNpaSearchStage(request.getParameter("npaSearchStage"));	
				vo.setSequenceNoSearch(request.getParameter("sequenceNoSearch"));
				CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				ArrayList<NPAMasterVo> list = cm.searchNPAStageData(vo);
				
				NPAMasterVo cMVo=new NPAMasterVo();
				cMVo=list.get(0);
				
				if(CommonFunction.checkNull(cMVo.getNpaStage()).equalsIgnoreCase("regular")){
					request.setAttribute("regular", "regular");
				}
				request.setAttribute("list", list);
				request.setAttribute("status", cMVo.getNpaStageStatus());
				request.setAttribute("billingStatus", cMVo.getBillingFlagStatus());
				request.setAttribute("AccrualStatus", cMVo.getAccrualFlagStatus());
				request.setAttribute("SdAccrualStatus", cMVo.getSdAccrualFlag());
				request.setAttribute("editVal", "editVal");
				form.reset(mapping, request);
				vo=null;
				cMVo=null;
				cm=null;
			   return mapping.findForward("openAdd");	
			}
	
	
	public ActionForward updateNPAStage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateNPAStage.......");
		HttpSession session = request.getSession();
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
		NPAMasterVo vo=new NPAMasterVo(); 
		DynaValidatorForm NPAStageMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, NPAStageMasterAddDyanavalidatiorForm);	
		
		CreditManagementBussinessSessionBeanRemote cm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String npaStage = request.getParameter("npaStage");
		vo.setNpaStage(npaStage);
		
		CreditManagementBussinessSessionBeanRemote dm = (CreditManagementBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditManagementBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		ArrayList<NPAMasterVo> proDesc = dm.getProductId();
		request.setAttribute("product", proDesc);  
	    String npa = request.getParameter("npaSearchStage");
		String product = dm.getProduct(npaStage);
		request.setAttribute("productId", product); 

		
         String result=cm.updateNPAStageData(vo);
		
         if((CommonFunction.checkNull(result)).trim().equalsIgnoreCase("ERROR"))
			request.setAttribute("ERROR", "ERROR");

		if((CommonFunction.checkNull(result)).trim().equalsIgnoreCase("EXIST"))
		{
			request.setAttribute("EXIST", "EXIST");
			ArrayList<NPAMasterVo> list =new ArrayList<NPAMasterVo>();
			list.add(vo);				
			request.setAttribute("editValUpdate", "editValUpdate");
			request.setAttribute("list", list);
			request.setAttribute("status", vo.getNpaStageStatus());
			request.setAttribute("billingStatus", vo.getBillingFlagStatus());
			request.setAttribute("AccrualStatus", vo.getAccrualFlagStatus());
			request.setAttribute("SdAccrualStatus ", vo.getSdAccrualFlag());
		}
		if(result.trim().equals("UPDATE"))
		{
			request.setAttribute("editValUpdate", "editValUpdate");
			request.setAttribute("productId",vo.getProductId());
			request.setAttribute("UPDATE", "UPDATE");
		}
			
		form.reset(mapping, request);
		cm=null;
		vo=null;
		npaStage=null;
        return mapping.getInputForward();
       
		
	}
	
	

}
