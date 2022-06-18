/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
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
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.cp.financialDao.FinancialDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.MasterVo;

/** 
 * MyEclipse Struts
 * Creation date: 11-25-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class RuleMasterProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(RuleMasterProcessAction.class.getName());
	public ActionForward addRuleMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
				logger.info(" in addRuleMaster()");
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

				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList ruleList = cpm.getRoleType();
		        ArrayList subRuleList = cpm.getSubRoleType();
				ArrayList paramList = cpm.getRuleMasterParam("");
				request.setAttribute("ruleList", ruleList);
				request.setAttribute("subRuleList", subRuleList);
				request.setAttribute("paramList", paramList);
				request.setAttribute("save", "save");
				request.setAttribute("Add", "Add");
				request.setAttribute("status","Active");
			    return mapping.findForward("openAdd");	
			}
	
	public ActionForward saveRuleMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		DynaValidatorForm ruleMasterAddDynaValidatorForm= (DynaValidatorForm)form;
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
		
	
		MasterVo vo = new MasterVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ruleMasterAddDynaValidatorForm);	
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		vo.setSessionId(sessionId.toString());
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
		vo.setRuleCodeModify("I");
		String sms="";
		String status="";
		
		status = cpm.saveRuleDetail(vo);
			logger.info("Inside saveRuleMaster.....displaying status...."+status);
			if(status.equalsIgnoreCase("S"))
			{
				sms="S";
				request.setAttribute("sms",sms);
				
				//ruleMasterAddDynaValidatorForm.reset(mapping, request);
			}
			else if(status.equalsIgnoreCase(""))
			{
				sms="EX";
				request.setAttribute("sms",sms);
				ArrayList ruleDetail =new ArrayList();
				if (vo.getRuleStatus() != null
						&& vo.getRuleStatus().equals("on")) {
					vo.setRuleStatus("A");
				} else {
					vo.setRuleStatus("X");
				}
				ruleDetail.add(vo);
				request.setAttribute("ruleDetail", ruleDetail);
				ArrayList ruleList = cpm.getRoleType();
				request.setAttribute("ruleList", ruleList);
			}
			else
			{
				sms="E";
				request.setAttribute("error", status);
				request.removeAttribute("sms");
				//sms="E";
				ArrayList ruleDetail =new ArrayList();
				if (vo.getRuleStatus() != null
						&& vo.getRuleStatus().equals("on")) {
					vo.setRuleStatus("A");
				} else {
					vo.setRuleStatus("X");
				}
				ruleDetail.add(vo);
				request.setAttribute("ruleDetail", ruleDetail);
				ArrayList ruleList = cpm.getRoleType();
				request.setAttribute("ruleList", ruleList);
	//			ArrayList list1 = masterDAO.getAgency();
	//			request.setAttribute("agencyType", list1);
				//request.setAttribute("sms",sms);
			}
//		}
//		else
//		{
//			request.setAttribute("sms","V");
//			ArrayList ruleDetail =new ArrayList();
//			if (vo.getRuleStatus() != null
//					&& vo.getRuleStatus().equals("on")) {
//				vo.setRuleStatus("A");
//			} else {
//				vo.setRuleStatus("X");
//			}
//			ruleDetail.add(vo);
//			request.setAttribute("ruleDetail", ruleDetail);
//			ArrayList ruleList = masterDAO.getRoleType();
//			request.setAttribute("ruleList", ruleList);
//		}
			FinancialDAO dao=(FinancialDAO)DaoImplInstanceFactory.getDaoImplInstance(FinancialDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
		ArrayList paramList = cpm.getRuleMasterParam("");
		request.setAttribute("paramList", paramList);
	    logger.info("status"+status);
		ArrayList subRuleList = cpm.getSubRoleType();
		request.setAttribute("subRuleList", subRuleList);
		return mapping.getInputForward();
	}
	
	public ActionForward openEditRuleMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		        MasterVo vo=new MasterVo(); 
				logger.info("In openEditRuleMaster");
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
				
				vo.setRuleCode(request.getParameter("ruleCode"));
			
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
		        ArrayList<MasterVo> ruleData = cpm.ruleMasterSearch(vo);
				
				logger.info("In openEditRuleMaster vo list"+ruleData.size());
				request.setAttribute("ruleData", ruleData);
				
				FinancialDAO dao=(FinancialDAO)DaoImplInstanceFactory.getDaoImplInstance(FinancialDAO.IDENTITY);
				logger.info("Implementation class: "+dao.getClass());
				//MasterDAO mDao = new MasterDAOImpl();
				ArrayList ruleList = cpm.getRoleType();
				ArrayList subRuleList = cpm.getSubRoleType();
				ArrayList paramList = cpm.getRuleMasterParam("");
				request.setAttribute("ruleList", ruleList);
				request.setAttribute("subRuleList", subRuleList);
				request.setAttribute("paramList", paramList);
				
				MasterVo mVo=new MasterVo();
				mVo=ruleData.get(0);
				logger.info("In openEditRuleMaster---status---- by   getpara by vo-"+mVo.getRuleStatus());
				request.setAttribute("status", mVo.getRuleStatus());
				request.setAttribute("editVal", "editVal");
			   return mapping.findForward("openAdd");	
			}
	
	public ActionForward updateRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("In updateRule.......");
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
		String userId="";
		String bDate="";
		if(userobj!=null)
		{
				userId=userobj.getUserId();
				bDate=userobj.getBusinessdate();
		}
		
		MasterVo vo=new MasterVo(); 
		DynaValidatorForm ruleMasterAddDynaValidatorForm= (DynaValidatorForm)form;
		  
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, ruleMasterAddDynaValidatorForm);	
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        vo.setSessionId(sessionId.toString());
		vo.setMakerId(userId);
		vo.setMakerDate(bDate);
        
		vo.setRuleStatus(request.getParameter("ruleStatus"));
		logger.info("In updateRule---status---- by getpara-"+request.getParameter("ruleStatus"));
		
		vo.setRuleCode(request.getParameter("ruleCode"));
		vo.setRuleCodeModify("U");
        String status="";
		
	        status=cpm.updateRuleDetail(vo);
	        String sms="";
	        if(status.equalsIgnoreCase("S")){
				sms="M";
				request.setAttribute("sms",sms);
				//ruleMasterAddDynaValidatorForm.reset(mapping, request);
			}
			else{
				//sms="E";
				
				request.setAttribute("error", status);
				request.removeAttribute("sms");
				if (vo.getRuleStatus() != null
						&& vo.getRuleStatus().equals("on")) {
					vo.setRuleStatus("A");
				} else {
					vo.setRuleStatus("X");
				}
				ArrayList ruleDetail =new ArrayList();
				ruleDetail.add(vo);
				request.setAttribute("ruleData", ruleDetail);
				ArrayList ruleList = cpm.getRoleType();
				request.setAttribute("ruleList", ruleList);
				request.setAttribute("editVal", "editVal");
			}


	        ArrayList subRuleList = cpm.getSubRoleType();
	        request.setAttribute("subRuleList", subRuleList);
	        FinancialDAO dao=(FinancialDAO)DaoImplInstanceFactory.getDaoImplInstance(FinancialDAO.IDENTITY);
			logger.info("Implementation class: "+dao.getClass());
		ArrayList paramList = cpm.getRuleMasterParam("");
		request.setAttribute("paramList", paramList);
        return mapping.getInputForward();
       
		
	}
}