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

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.BankAccountMasterVo;


public class BankAccountMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(BankAccountMasterDispatchAction.class.getName());
	

	public ActionForward openAddBankAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddBankAccount()");
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

	
	public ActionForward saveBankAccountDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		//HttpSession session=request.getSession(false);
		
		userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		DynaValidatorForm BankAccountMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		
		BankAccountMasterVo vo = new BankAccountMasterVo();
		
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BankAccountMasterAddDynaValidatorForm);	

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String sms="";
		
		boolean status = cpm.insertBankAccountMaster(vo);
		
		logger.info("Inside Bank Account Master Action.....displaying status...."+status);
		if(status){
			sms="S";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
	    logger.info("status"+status);
	    
		return mapping.getInputForward();
	}
	

	public ActionForward openEditBankAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		BankAccountMasterVo vo=new BankAccountMasterVo(); 
				logger.info("In openEditBankAccountMaster");
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
				vo.setLbxBankSearchID(request.getParameter("bankSearchCode"));
				logger.info("In openEditBankAccount------ by getpara-"+request.getParameter("bankSearchCode"));  
				
				vo.setLbxBranchSearchID(request.getParameter("bankBranchSearchName"));
				logger.info("In openEditBankAccount----bankBranchName- by getpara-"+request.getParameter("bankBranchSearchName"));
				
				vo.setAccountNo(request.getParameter("accountNo"));
				logger.info("In openEditBankAccount----accountNo- by getpara-"+request.getParameter("accountNo"));
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				ArrayList<BankAccountMasterVo> list = cpm.searchBankAccountData(vo);
				
				logger.info("In openEditBankAccount list"+list.size());
				request.setAttribute("list", list);
				BankAccountMasterVo bMVo=new BankAccountMasterVo();
				bMVo=list.get(0);

				request.setAttribute("status", bMVo.getBankAccountStatus());
				
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
	
	
	public ActionForward updateBankAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateBankAccount.......");
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
		
		BankAccountMasterVo vo=new BankAccountMasterVo(); 
		DynaValidatorForm BankAccountMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BankAccountMasterAddDyanavalidatiorForm);	
		
		String bankCode = request.getParameter("lbxBankID");
		vo.setLbxBankID(bankCode);
		logger.info("bankCode....."+vo.getLbxBankID());

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        boolean status=cpm.updateBankAccountData(vo);
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
