package com.masters.actions;


import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.MasterDAO;
import com.masters.vo.BankBranchMasterVo;



public class BankBranchDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(BankBranchDispatchAction.class.getName());
		public ActionForward addBranchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
			ServletContext context = getServlet().getServletContext();
				logger.info(" IN BankBranchDispatchAction addBranchDetails()....");
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
				MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
//				MasterDAO dao = new MasterDAOImpl();
				ArrayList defaultcountry=dao.defaultCountry();
				request.setAttribute("defaultcountry", defaultcountry);
				request.setAttribute("save", "save");
			    return mapping.findForward("addBranchDetails");	
			}
	
	
	public ActionForward saveBankBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	 throws Exception{
		ServletContext context = getServlet().getServletContext();
		HttpSession session = request.getSession();
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
		
		DynaValidatorForm BankBranchDynaValidatorForm=(DynaValidatorForm)form;
		BankBranchMasterVo bankBranchMasterVo= new BankBranchMasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(bankBranchMasterVo, BankBranchDynaValidatorForm);
		
		bankBranchMasterVo.setMakerId(userId);
		bankBranchMasterVo.setMakerDate(bDate);
		String ecsStatus="";
		String achStatus="";//added by Rohit for ACH
		String msg="";
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		boolean ifscCode=false;
		String ifsc=CommonFunction.checkNull(bankBranchMasterVo.getBranchIFCSCode()).trim();
		if(!CommonFunction.checkNull(ifsc).trim().equalsIgnoreCase(""))
		   ifscCode=cpm.ifscBankBranchMaster(bankBranchMasterVo);
        if(!ifscCode)
        {
        	boolean result = cpm.insertBankBranchMaster(bankBranchMasterVo);
        	if(result)
        	{
        		msg="S";
        		request.setAttribute("msg",msg);
        	}else{
        		msg="E";
        		request.setAttribute("msg",msg);
        	}
        }
        else{
        	if (bankBranchMasterVo.getEcsStatus() != null
					&& bankBranchMasterVo.getEcsStatus()
							.equalsIgnoreCase("on")) {
				ecsStatus = "A";
				request.setAttribute("ECS", ecsStatus);
			} 
        	//Rohit Changes for ACH Status
        	if (bankBranchMasterVo.getAchStatus() != null
					&& bankBranchMasterVo.getAchStatus()
							.equalsIgnoreCase("on")) {
				ecsStatus = "A";
				request.setAttribute("ACH", ecsStatus);
			}
        	//Rohit end
        	msg="EXIT";
        	request.setAttribute("msg",msg);
        	}
		
		logger.info("In BankBranchDispatchAction saveBankBranch msg is...."+msg);
		request.setAttribute("msg", msg);	
		return mapping.getInputForward();
	}
	
	public ActionForward modifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In BankBranchDispatchAction modifyDetails()....");
				
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
				
				DynaValidatorForm BankBranchDynaValidatorForm=(DynaValidatorForm)form;
				BankBranchMasterVo bankBranchMasterVo= new BankBranchMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(bankBranchMasterVo, BankBranchDynaValidatorForm);

				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				String bankbranchSearchId=CommonFunction.checkNull(request.getParameter("bankbranchSearchId"));
		        bankBranchMasterVo.setBankBranchId(CommonFunction.checkNull(request.getParameter("bankbranchSearchId")));
				logger.info(".........."+request.getParameter("bankbranchSearchId"));
				ArrayList<BankBranchMasterVo> detailList = cpm.modifyBankDetailsDao(bankBranchMasterVo);
				request.setAttribute("list", detailList);	
				request.setAttribute("bankbranchSearchId",bankbranchSearchId);
				request.setAttribute("modify", "modify");
				request.setAttribute("status", detailList.get(0).getBankBranchStatus());
				request.setAttribute("ECS", detailList.get(0).getEcsStatus());
				request.setAttribute("ACH", detailList.get(0).getAchStatus());//added by Rohit for ACH
			   return mapping.findForward("modifyDetails");	
			}
	
	public ActionForward saveModifyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In BankBranchDispatchAction saveModifyDetails()....");
				
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
				
				DynaValidatorForm BankBranchDynaValidatorForm=(DynaValidatorForm)form;
				BankBranchMasterVo bankBranchMasterVo= new BankBranchMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(bankBranchMasterVo, BankBranchDynaValidatorForm);

				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				String bankbranchSearchId = request.getParameter("bankBranchId");
				request.setAttribute("bankbranchSearchId",bankbranchSearchId);
				bankBranchMasterVo.setBankBranchId(bankbranchSearchId);
				String msg="";
				String stat = "";
				String ecsStatus = "";
				String achStatus = "";//added by Rohit for ACH
				boolean ifscCode=false;
				String ifsc=CommonFunction.checkNull(bankBranchMasterVo.getBranchIFCSCode()).trim();
				if(!CommonFunction.checkNull(ifsc).trim().equalsIgnoreCase(""))
					ifscCode=cpm.ifscBankBranchMaster(bankBranchMasterVo);
			    if(!ifscCode)
			    {
			    	boolean result = cpm.saveModifyBankDetailsDao(bankBranchMasterVo);
			
				
			    	if(result){
					msg="M";
					request.setAttribute("msg",msg);
			    	}else{
					msg="E";
					request.setAttribute("msg",msg);
			    	}
			    }
			    else
			    {
			    	if (bankBranchMasterVo.getBankBranchStatus() != null
							&& bankBranchMasterVo.getBankBranchStatus().equalsIgnoreCase("on")) {
						stat = "Active";
						request.setAttribute("status", stat);
						
					} 
					if (bankBranchMasterVo.getEcsStatus() != null
							&& bankBranchMasterVo.getEcsStatus()
									.equalsIgnoreCase("on")) {
						ecsStatus = "A";
						request.setAttribute("ECS", ecsStatus);
					} 
					//Rohit Changes for ACH Starts
					if (bankBranchMasterVo.getAchStatus() != null
							&& bankBranchMasterVo.getAchStatus()
									.equalsIgnoreCase("on")) {
						achStatus = "A";
						request.setAttribute("ACH", ecsStatus);
					}
					//Rohit end
			    	msg="EXIT";
		        	request.setAttribute("msg",msg);
		        }
				ArrayList detailList=new ArrayList();
				detailList.add(bankBranchMasterVo);
				request.setAttribute("list", detailList);	
				logger.info("In BankBranchDispatchAction saveModifyDetails msg is...."+msg);
				request.setAttribute("msg", msg);	
				request.setAttribute("modify", "modify");
			   return mapping.getInputForward();	
			}
	
	
}
