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
import com.masters.vo.BankMasterVo;

public class BankMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(BankMasterDispatchAction.class.getName());
	public ActionForward openAddBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{ServletContext context = getServlet().getServletContext();
				logger.info(" in openAddBank()");
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

	
	public ActionForward saveBankDetails(ActionMapping mapping, ActionForm form,
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
		UserObject userobj1=(UserObject)session.getAttribute("userobject");
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
			
		DynaValidatorForm BankMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
			BankMasterVo vo = new BankMasterVo();
			
			vo.setMakerId(userId);
			vo.setMakerDate(bDate);
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BankMasterAddDyanavalidatiorForm);	

			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
			
			String sms="";
		
			String status = cpm.insertBankMaster(vo);
			logger.info("Inside Bank Master Action.....displaying status...."+status);
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
	


	
		public ActionForward openEditBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		BankMasterVo bankMasterVo=new BankMasterVo(); 
				logger.info("In openEditBank");
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
				
				bankMasterVo.setBankSearchCode(request.getParameter("bankSearchCode"));
				logger.info("In openEditBank---status---- by getpara-"+request.getParameter("bankSearchCode"));  
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList<BankMasterVo> list = cpm.searchBankData(bankMasterVo);
				logger.info("In openEditBank bankMasterVo list"+list.size());
				request.setAttribute("list", list);
				
				bankMasterVo=list.get(0);
				BankMasterVo docVo=new BankMasterVo();
				docVo=list.get(0);
				logger.info("In openEditBank---status---- by   getpara by vo-"+docVo.getBankStatus());
				request.setAttribute("status", bankMasterVo.getBankStatus());
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("editBank");	
			}
	
	
		
	public ActionForward updateSearchBank(ActionMapping mapping, ActionForm form,
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
		BankMasterVo vo = new BankMasterVo();
		DynaValidatorForm BankMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BankMasterAddDyanavalidatiorForm);	
		
		String bankCode=(String)vo.getBankCode();

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		logger.info("bankCode"+bankCode);
		ArrayList<BankMasterVo> detailList = cpm.searchBankData(bankCode);
		
		request.setAttribute("detailList", detailList);
		
		return mapping.findForward("add");
	}

	public ActionForward updateBank(ActionMapping mapping, ActionForm form,
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
		userobj=(UserObject)session.getAttribute("userobject");
		
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		logger.info("In updateBankMaster.......");
		BankMasterVo vo=new BankMasterVo(); 
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		DynaValidatorForm BankMasterAddDyanavalidatiorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, BankMasterAddDyanavalidatiorForm);	

		logger.info("In updateBankDetails---status by form- bnakCode----"+BankMasterAddDyanavalidatiorForm.getString("bankCode"));  
		vo.setBankStatus(request.getParameter("bankStatus"));
		logger.info("In updateBankDetails---status-----"+vo.getBankStatus());
		vo.setBankName(request.getParameter("bankName"));

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        boolean status=cpm.updateBankData(vo);
        String sms="";
        if(status){
        	sms="M";
			request.setAttribute("sms",sms);
		}
		else{
			sms="E";
			request.setAttribute("sms",sms);
		}
		logger.info("status"+status);
    
        return mapping.getInputForward();
      
		
	}

}
		

