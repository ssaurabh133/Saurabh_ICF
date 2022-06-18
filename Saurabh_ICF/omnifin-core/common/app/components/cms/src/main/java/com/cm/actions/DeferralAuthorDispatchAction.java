package com.cm.actions;


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

import com.cm.dao.DeferralDAO;
//import com.cm.dao.EarlyClosureDAO;
//import com.cm.dao.DeferralDAOImpl;

import com.cm.vo.PartPrePaymentAuthorVO;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class DeferralAuthorDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(DeferralAuthorDispatchAction.class.getName());
	
	public ActionForward showDeferralDataAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoggerMsg.info("Inside ........showDeferralDataAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in showDeferralDataAuthor method of DeferralAuthorDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		session.getAttribute("deferralData");
		session.getAttribute("loanId");
		session.getAttribute("reschId");
		return mapping.findForward("showDeferralDataAuthor");
	}
	
	public ActionForward openDeferralAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........openDeferralAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null){
				userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
		}else{
			logger.info("here in openDeferralAuthor method of DeferralAuthorDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String reschId=(session.getAttribute("reschId")).toString();
		String makerDate=service.getMakerDate(reschId);
		request.setAttribute("businessDate",businessDate);
		request.setAttribute("makerDate",makerDate);
		return mapping.findForward("openDeferralAuthor");
	}
	
	public ActionForward saveDeferralAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........saveDeferralAuthor");
		
		HttpSession session =  request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String bDate ="";
		int cId =0;
		if(userobj!=null){
			userId = userobj.getUserId();
			bDate=userobj.getBusinessdate();
			cId=userobj.getCompanyId();
		}else{
			logger.info("here in saveDeferralAuthor method of DeferralAuthorDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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
		session.getAttribute("loanId");
		session.getAttribute("reschId");
		
		String status = "";
		DynaValidatorForm PartPrePaymentAuthorDynaValidatorForm = (DynaValidatorForm)form;
		PartPrePaymentAuthorVO vo = new PartPrePaymentAuthorVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,PartPrePaymentAuthorDynaValidatorForm);

		vo.setAuthorId(userId);
		vo.setAuthorDate(bDate);
		vo.setCompanyId(cId);
		//DeferralDAO service=new DeferralDAOImpl();
		DeferralDAO service=(DeferralDAO)DaoImplInstanceFactory.getDaoImplInstance(DeferralDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		status = service.saveDeferralAuthor(vo);
		if(status.equalsIgnoreCase("S"))
		{
			request.setAttribute("message","S");
		}
		else
		{
			request.setAttribute("message","E");
			request.setAttribute("status",status);
		}
		return mapping.findForward("saveDeferralAuthor");
	}

}
