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
import com.connect.CommonFunction;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.vo.ManulaDeviationVO;


public class ManualDeviationMasterProcessAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ManualDeviationMasterProcessAction.class.getName());
	public ActionForward openManualDeviationAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In ManualDeviationMasterProcessAction..openManualDeviationAdd()...");
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
		
	        ManulaDeviationVO vo = new ManulaDeviationVO();		
		    DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);				
			logger.info("current page link .......... "+request.getParameter("d-49520-p"));			
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
			 vo.setCurrentPageLink(String.valueOf(currentPageLink));
			 ArrayList list=new ArrayList();
			 ArrayList subRuleList = cpm.getSubRoleType();
			 request.setAttribute("subRuleList", subRuleList);
			 if(list.size()>0){
				    ManulaDeviationVO newVo=(ManulaDeviationVO) list.get(0);
					String status=newVo.getStatus().trim();
					if(status.trim().equals("X"))
					request.setAttribute("X","X");
					if(status.trim().equals("A"))
					request.setAttribute("A","A");			
					request.setAttribute("list",list);					
				}
			 //mradul startsfor approvel level .
			 int list1  = cpm.getApprovalfromPmst();
				
				logger.info("----------Manual deviation addJsp--"+list1);
				if(list1>2 && list1<10){
					request.setAttribute("pmstSize", list1);					
				}else if(list1<3 || list1>9){
					request.setAttribute("pmstSize", 3);
				}else {
					request.setAttribute("pmstSize", 3);
				}
			//mradul ends for approvel level .
			 request.setAttribute("X","X");
			 request.setAttribute("list", list);		 //for checking....
		
	        return mapping.findForward("AddPage");
		}
	public ActionForward saveManualDeviation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In ManualDeviationMasterProcessAction..saveManualDeviation()...");
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
		
	        ManulaDeviationVO vo = new ManulaDeviationVO();		
	    
		    DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(vo, CommonDynaValidatorForm);				
			logger.info("current page link .......... "+request.getParameter("d-49520-p"));			
			boolean status=false;			
			if(userobj != null)
			{		
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());		
			String recStatus=request.getParameter("status");
			if(recStatus!=null)
			 vo.setStatus("A");
			else
			 vo.setStatus("X");
			}	
			String manualid=CommonFunction.checkNull(request.getParameter("manualid"));	
			vo.setManualid(manualid);
			logger.info("manualid======"+manualid);
			logger.info("Approval Id:-"+vo.getApprovalLevel());
			logger.info("Approval Id By Dyna:-"+CommonDynaValidatorForm.getString("approvalLevel"));
			logger.info("Approval Id BY  parameter:-"+request.getParameter("approvalLevel"));
			String approvalLevel=CommonFunction.checkNull(request.getParameter("approvalLevel"));
			vo.setApprovalLevel(approvalLevel);
			ArrayList subRuleList = cpm.getSubRoleType();
			request.setAttribute("subRuleList", subRuleList);
			status=cpm.saveManualDeviation(vo);
			if(status)
			{
				if(manualid!="")
				{
					request.setAttribute("update", "update");
				}
				else
				{
				request.setAttribute("save", "save");
				}
			}
			else
			{
				request.setAttribute("save", "save");
			}
			logger.info("in ManualDeviationMasterProcessAction saveManualDeviation() staus for save"+status);
	        return mapping.findForward("SavePage");
		}
	public ActionForward searchManualDeviation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in searchManualDeviation()......................................");		
		ServletContext context = getServlet().getServletContext();
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null && userobj != null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
				if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
				else if(strFlag.equalsIgnoreCase("BODCheck"))
					context.setAttribute("msg","B");
				return mapping.findForward("logout");
		}
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));			
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+currentPageLink);
		ManulaDeviationVO vo=new ManulaDeviationVO();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		vo.setCurrentPageLink(String.valueOf(currentPageLink));
		ArrayList<ManulaDeviationVO> list= cpm.searchManualDeviationList(vo);
		request.setAttribute("list",list);			       
		return mapping.findForward("SearchPage");
	}
	public ActionForward updateManualDeviationMaster(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info(" in updateManualDeviationMaster()......................................");		
		ServletContext context = getServlet().getServletContext();
		HttpSession session =  request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
		String strFlag="";	
		if(sessionId!=null && userobj != null)
			strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(),"",request);
		logger.info("strFlag .............. "+strFlag);
		if(!strFlag.equalsIgnoreCase(""))
		{
				if(strFlag.equalsIgnoreCase("sameUserSession"))
				{
					context.removeAttribute("msg");
					context.removeAttribute("msg1");
				}
				else if(strFlag.equalsIgnoreCase("BODCheck"))
					context.setAttribute("msg","B");
				return mapping.findForward("logout");
		}
		logger.info("current page link .......... "+request.getParameter("d-49520-p"));			
		int currentPageLink = 0;
		if(request.getParameter("d-49520-p")==null || request.getParameter("d-49520-p").equalsIgnoreCase("0"))
			currentPageLink=1;
		else
			currentPageLink =Integer.parseInt(request.getParameter("d-49520-p"));
		logger.info("current page link ................ "+currentPageLink);
		ManulaDeviationVO vo=new ManulaDeviationVO();
		DynaValidatorForm formbeen= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, formbeen);
		
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String manualid=CommonFunction.checkNull(request.getParameter("manualid"));
		ArrayList subRuleList = cpm.getSubRoleType();
		request.setAttribute("subRuleList", subRuleList);
		ArrayList<ManulaDeviationVO> list= cpm.getSingleRecord(manualid);
		if(list.size()>0)
		{
			ManulaDeviationVO newVo=list.get(0);
			String status=newVo.getStatus().trim();
			if(status.trim().equals("X"))
			request.setAttribute("X","X");
			if(status.trim().equals("A"))
			request.setAttribute("A","A");		
		    request.setAttribute("list",list);		
		}
		 //mradul startsfor approvel level .
		 int list1  = cpm.getApprovalfromPmst();
			
			logger.info("----------Manual deviation addJsp--"+list1);
			if(list1>2 && list1<10){
				request.setAttribute("pmstSize", list1);					
			}else if(list1<3 || list1>9){
				request.setAttribute("pmstSize", 3);
			}else {
				request.setAttribute("pmstSize", 3);
			}
		//mradul ends for approvel level.
		return mapping.findForward("updatepage");
	}
	
	
	}