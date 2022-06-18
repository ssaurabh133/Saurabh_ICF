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
import com.masters.vo.ruleParamMasterVo;

public class RuleMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(RuleMasterDispatchAction.class.getName());	
	public ActionForward initialruleMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN initialruleMaster()....");
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
				DynaValidatorForm RuleParameterDynaValidator=(DynaValidatorForm) form;
				ruleParamMasterVo ob= new ruleParamMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(ob,RuleParameterDynaValidator);
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				
				int currentPageLink = 0;
				if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
				}
				
				logger.info("current page link ................ "+request.getParameter("d-49520-p"));
				
				ob.setCurrentPageLink(currentPageLink);
				ArrayList list=new ArrayList();
				list= cpm.searchRuleMaster(ob);
				
				
				request.setAttribute("list",list);
//				if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
//					request.setAttribute("sms","No");
//				}
				
			    return mapping.findForward("ruleParameterList");	
			}
	
	
	public ActionForward ruleMasterList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info(" IN MasterSearch ruleMasterList()....");
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
				DynaValidatorForm RuleParameterDynaValidator=(DynaValidatorForm) form;
				ruleParamMasterVo ob= new ruleParamMasterVo();
				org.apache.commons.beanutils.BeanUtils.copyProperties(ob,RuleParameterDynaValidator);
				
				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				int currentPageLink = 0;
				if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
				{
					currentPageLink=1;
				}
				else
				{
					currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
				}
				
				logger.info("current page link ................ "+request.getParameter("d-49520-p"));
				
				ob.setCurrentPageLink(currentPageLink);
				ArrayList list=new ArrayList();
				list= cpm.searchRuleMaster(ob);
				
				
				request.setAttribute("list",list);
				if(CommonFunction.checkNull(request.getAttribute("flag")).toString().equalsIgnoreCase("yes")){
					request.setAttribute("sms","No");
				}
				
			    return mapping.findForward("ruleParameterList");	
			}
	
	
	public ActionForward addRuleMasterDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ServletContext context = getServlet().getServletContext();
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
		ruleParamMasterVo ruleParamMasterVo= new ruleParamMasterVo();
		
		DynaValidatorForm RuleParameterDynaValidator=(DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(ruleParamMasterVo, RuleParameterDynaValidator);

		request.setAttribute("save", "save");
	    return mapping.findForward("ruleAdd");
	}
	
	
	public ActionForward insertRuleMasterDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
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
		
		DynaValidatorForm RuleParameterDynaValidator=(DynaValidatorForm) form;
		logger.info("-----------------------above the code");
		ruleParamMasterVo ob= new ruleParamMasterVo();
		logger.info("-----------------------below the vo");
		org.apache.commons.beanutils.BeanUtils.copyProperties(ob,RuleParameterDynaValidator);
		logger.info("-----------------------below the save bean");
	
		ob.setMakerId(userId);
		ob.setMakerDate(bDate);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        String msg="";
		
		boolean result = cpm.insertRuleScoreMaster(ob);
		
		if(result){
			
			request.setAttribute("msg","S");
		}else{
			
			request.setAttribute("msg","E");
		}
		request.setAttribute("save", "save");
	    return mapping.findForward("addRuleNew");
	}
	
	
	
	public ActionForward openRuleMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
					
						ruleParamMasterVo ruleParamMasterVo= new ruleParamMasterVo();
						logger.info("In openRuleMaster");
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
						
						ruleParamMasterVo.setParameterCode(request.getParameter("ruleCode"));
						ruleParamMasterVo.setParamName(request.getParameter("paramDesc"));
						
						CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
						
				        ArrayList<ruleParamMasterVo> list = cpm.searchRuleMaster(ruleParamMasterVo);
						logger.info("In openRuleMaster list"+list.size());
						request.setAttribute("list", list);
					
						ruleParamMasterVo docVo =new ruleParamMasterVo();
						
						docVo=list.get(0);						
						request.setAttribute("status",docVo.getParameterStatus());
						request.setAttribute("editVal", "editVal");
						
					   return mapping.findForward("editRuleDetails");	
					}
			
			
	public ActionForward updateRuleDetails(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
				logger.info("In updateRuleDetails.......");
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
				
				ruleParamMasterVo ruleParamMasterVo=new ruleParamMasterVo(); 
				DynaValidatorForm RuleParameterDynaValidator= (DynaValidatorForm)form;
				org.apache.commons.beanutils.BeanUtils.copyProperties(ruleParamMasterVo, RuleParameterDynaValidator);	

				CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
				
				logger.info("In updateRuleDetails---------");    

		        boolean status=cpm.updateRuleMaster(ruleParamMasterVo);
		        String sms="";
		        if(status){
					sms="M";
					request.setAttribute("msg",sms);
					request.setAttribute("editVal", "editVal");
				}
				else{
					sms="E";
					request.setAttribute("msg",sms);
					ArrayList<ruleParamMasterVo> list =new ArrayList<ruleParamMasterVo>();
					list.add(ruleParamMasterVo);
					logger.info("In updateRuleDetails list"+ list.size());
					
					request.setAttribute("editVal", "editVal");
					request.setAttribute("status", ruleParamMasterVo.getParameterStatus());
					
				}
		      
		       // countryMastervo.setCountryId(request.getParameter("CountryId"));
				logger.info("In updateRuleDetails---status-----"+ruleParamMasterVo.getParameterStatus());
				logger.info("in updateRuleDetails ------description-------"+ruleParamMasterVo.getParameterStatus());
				
				return mapping.findForward("updateRule");
		      
				
			}		
}
