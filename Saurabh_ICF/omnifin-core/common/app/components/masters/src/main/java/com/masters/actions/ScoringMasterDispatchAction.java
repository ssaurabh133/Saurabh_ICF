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
import com.masters.vo.ScoringMasterVO;


public class ScoringMasterDispatchAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger(ScoringMasterDispatchAction.class.getName());
	public ActionForward openAddScoringMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In openAddScoringMaster of ScoringMasterDispatchAction ....................");
		HttpSession session = request.getSession();
		ServletContext context = getServlet().getServletContext();
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
		return mapping.findForward("openAdd");
	}
	public ActionForward saveScoringMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In saveScoringMaster of ScoringMasterDispatchAction ....................");
		HttpSession session = request.getSession();
		ServletContext context = getServlet().getServletContext();
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
		DynaValidatorForm scoringMasterDynaValidatorForm=(DynaValidatorForm)form;
		ScoringMasterVO vo=new ScoringMasterVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, scoringMasterDynaValidatorForm);
		
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String result=cpm.saveScoringMaster(vo);
		if(CommonFunction.checkNull(result).equalsIgnoreCase("saved")){
			request.setAttribute("sms", "S");
		}else if(CommonFunction.checkNull(result).equalsIgnoreCase("already")){
			request.setAttribute("sms", "A");
		}else{
			request.setAttribute("sms", "E");
		}
		return mapping.findForward("openAdd");
	}
	public ActionForward editOpenScoringMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In editOpenScoringMaster of ScoringMasterDispatchAction ....................");
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
		DynaValidatorForm scoringMasterDynaValidatorForm=(DynaValidatorForm)form;
		ScoringMasterVO vo=new ScoringMasterVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, scoringMasterDynaValidatorForm);
		
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
        vo.setScoringId(request.getParameter("scoringId"));
        logger.info("scoringId:--------------------------------"+request.getParameter("scoringId"));
        ArrayList<ScoringMasterVO> scoringHeaderList=cpm.editScoringMasterHeader(vo);
        ArrayList<ScoringMasterVO> scoringDtlList=cpm.editScoringMasterdtl(vo);
        request.setAttribute("scoringHeaderList", scoringHeaderList);
        request.setAttribute("scoringDtlList", scoringDtlList);
        request.setAttribute("edit", "edit");
        if(scoringHeaderList!=null&&scoringHeaderList.size()>0){
        	 request.setAttribute("status", scoringHeaderList.get(0).getRecStatus());
        }
        request.setAttribute("edit", "edit");
        return mapping.findForward("openAdd");
	}
	public ActionForward updateScoringMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In updateScoringMaster of ScoringMasterDispatchAction ....................");
		HttpSession session = request.getSession();
		ServletContext context = getServlet().getServletContext();
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
		DynaValidatorForm scoringMasterDynaValidatorForm=(DynaValidatorForm)form;
		ScoringMasterVO vo=new ScoringMasterVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, scoringMasterDynaValidatorForm);
		
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		logger.info("Save methof is called in action..........");
		logger.info("Save methof is called in action.........vo."+vo);
        String result=cpm.updateScoringMaster(vo);
        logger.info("Save method is called in action.......result..."+result);
		if(CommonFunction.checkNull(result).equalsIgnoreCase("saved")){
			request.setAttribute("sms", "S");
		}else if(CommonFunction.checkNull(result).equalsIgnoreCase("already")){
			request.setAttribute("sms", "A");
		}else{
			request.setAttribute("sms", "E");
		}
		return mapping.findForward("openAdd");
	}
	public ActionForward openParameterValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In openParameterValue of ScoringMasterDispatchAction ....................");
		HttpSession session = request.getSession();
		ServletContext context = getServlet().getServletContext();
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
		DynaValidatorForm scoringMasterDynaValidatorForm=(DynaValidatorForm)form;
		ScoringMasterVO vo=new ScoringMasterVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, scoringMasterDynaValidatorForm);
		
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String scoringId=request.getParameter("scoringId");
		logger.info("scoringId:----------"+scoringId);
		vo.setScoringId(scoringId);
		ArrayList<ScoringMasterVO> paramValueList=cpm.editScoringParamValueDtl(vo);
		if(paramValueList.size()>0){
			request.setAttribute("paramValueList", paramValueList);	
		}
		request.setAttribute("scoringId", scoringId);
		return mapping.findForward("openParamValue");
	}
	public ActionForward saveScoringMasterParamValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In saveScoringMasterParamValue of ScoringMasterDispatchAction ....................");
		HttpSession session = request.getSession();
		ServletContext context = getServlet().getServletContext();
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
		DynaValidatorForm scoringMasterDynaValidatorForm=(DynaValidatorForm)form;
		ScoringMasterVO vo=new ScoringMasterVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, scoringMasterDynaValidatorForm);
		
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY, request);
		
		String result=cpm.saveScoringMasterParamValue(vo);
		if(CommonFunction.checkNull(result).equalsIgnoreCase("saved")){
			request.setAttribute("sms", "S");
		}else{
			request.setAttribute("sms", "E");
		}
		return mapping.findForward("openParamValue");
	}
}
