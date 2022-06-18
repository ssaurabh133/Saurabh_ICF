package com.cm.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;





import com.cm.dao.MobilePayInSlipDAO;
import com.cm.vo.MobilePayInSlipVo;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;


public class MobilePayInSlipDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(MobilePayInSlipDispatchAction.class.getName());
	
	public ActionForward mobliePayInSlipSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside mobilePayInSlipDispatchAction.....mobliePayInSlipSearch");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("in   method of mobilePayInSlipDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

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
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String path=request.getContextPath();
		MobilePayInSlipVo mobVo = new MobilePayInSlipVo();
		DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(mobVo, CommonDynaValidatorForm);
		if(!StringUtils.isBlank(userId))
		{
			mobVo.setBranchId(branchId);
			mobVo.setMakerId(userId);
		}
		
		MobilePayInSlipDAO service=(MobilePayInSlipDAO)DaoImplInstanceFactory.getDaoImplInstance(MobilePayInSlipDAO.IDENTITY);
		
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
		mobVo.setCurrentPageLink(currentPageLink);
		List list=service.mobilePayInSlipSearchDtl(mobVo,path);	
		request.setAttribute("list", list);	
		
		return mapping.findForward("mobliePayInSlipSearch");
	}
	
	public ActionForward mobliePayInSlipSearchDtl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside mobilePayInSlipDispatchAction.....mobliePayInSlipSearch");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("in   method of mobilePayInSlipDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

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
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		String path=request.getContextPath();
		MobilePayInSlipVo mobVo = new MobilePayInSlipVo();
		DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(mobVo, CommonDynaValidatorForm);
		logger.info("created by : "+mobVo.getLbxUserId());
		String fromDate=(String)CommonDynaValidatorForm.get("payFromDate");
		String toDate=(String)CommonDynaValidatorForm.get("payToDate");
		logger.info("From Date : "+mobVo.getPayFromDate()+"To Date : "+mobVo.getPayToDate()+"User Name : "+mobVo.getUserName());
		MobilePayInSlipDAO service=(MobilePayInSlipDAO)DaoImplInstanceFactory.getDaoImplInstance(MobilePayInSlipDAO.IDENTITY);
		
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
		mobVo.setCurrentPageLink(currentPageLink);
		List list=service.mobilePayInSlipSearch(mobVo,path);
		request.setAttribute("list", list);	
		
		
		return mapping.findForward("mobliePayInSlipSearch");
	}
	public ActionForward mobileInstruementDtl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside mobilePayInSlipDispatchAction.....mobliePayInSlipSearch");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("in   method of mobilePayInSlipDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

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
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		MobilePayInSlipVo mobVo = new MobilePayInSlipVo();
		DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(mobVo, CommonDynaValidatorForm);
		logger.info("created by : "+mobVo.getLbxUserId());
		MobilePayInSlipDAO service=(MobilePayInSlipDAO)DaoImplInstanceFactory.getDaoImplInstance(MobilePayInSlipDAO.IDENTITY);
		String mobileId=request.getParameter("mobileId");
		List mobInstrumentDtl=service.mobileInstruementDtl(mobileId);	
		logger.info("mobInstrumentDtl : "+mobInstrumentDtl.get(0));
		request.setAttribute("mobInstrumentDtl", mobInstrumentDtl);	
		return mapping.findForward("mobileInstrumentDtl");
	}
	public ActionForward mobilePayInSlipPicture(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("Inside mobilePayInSlipDispatchAction.....mobliePayInSlipSearch");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId="";
		String branchId="";
		if(userobj!=null)
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}else{
			logger.info("in   method of mobilePayInSlipDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

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
			else if(strFlag.equalsIgnoreCase("BODCheck"))
			{
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		
		MobilePayInSlipVo mobVo = new MobilePayInSlipVo();
		DynaValidatorForm CommonDynaValidatorForm= (DynaValidatorForm)form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(mobVo, CommonDynaValidatorForm);
		logger.info("created by : "+mobVo.getLbxUserId());
		MobilePayInSlipDAO service=(MobilePayInSlipDAO)DaoImplInstanceFactory.getDaoImplInstance(MobilePayInSlipDAO.IDENTITY);
		String mobileId=request.getParameter("mobileId");
		List imagePicture=service.mobilePayInSlipPicture(mobileId);	
		logger.info("imagePicture : "+imagePicture.get(0));
		request.setAttribute("imagePicture", imagePicture);	
		return mapping.findForward("picturePage");
	}
}