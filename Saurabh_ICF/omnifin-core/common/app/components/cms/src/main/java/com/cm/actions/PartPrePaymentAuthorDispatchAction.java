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
import com.cm.dao.PartPrePaymentDAO;
import com.cm.vo.PartPrePaymentAuthorVO;
import com.connect.DaoImplInstanceFactory;
import com.logger.LoggerMsg;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class PartPrePaymentAuthorDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(PartPrePaymentAuthorDispatchAction.class.getName());
	
	public ActionForward showPartPrePaymentDataAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoggerMsg.info("Inside ........showPartPrePaymentDataAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null){
			logger.info("here in showPartPrePaymentDataAuthor method of PartPrePaymentAuthorDispatchAction action the session is out----------------");
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
		session.getAttribute("partPrePaymentDataAuthor");
		session.getAttribute("loanId");
		session.getAttribute("reschId");
		return mapping.findForward("showPartPrePaymentDataAuthor");
	}
	
	public ActionForward openPartPrePaymentAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........openPartPrePaymentAuthor");
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		if(userobj!=null){
				userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
		}else{
			logger.info("here in openPartPrePaymentAuthor method of PartPrePaymentAuthorDispatchAction action the session is out----------------");
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
		PartPrePaymentDAO service=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		String reschId=(session.getAttribute("reschId")).toString();
		String makerDate=service.getMakerDate(reschId);
		request.setAttribute("businessDate",businessDate);
		request.setAttribute("makerDate",makerDate);
		return mapping.findForward("openPartPrePaymentAuthor");
	}
	
	public ActionForward savePartPrePaymentAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("Inside ........savePartPrePaymentAuthor");
		
		HttpSession session =  request.getSession();
		
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId ="";
		String businessDate ="";
		int compid =0;
		if(userobj!=null){
			userId = userobj.getUserId();
				businessDate=userobj.getBusinessdate();
				compid=userobj.getCompanyId();
		}else{
			logger.info("here in savePartPrePaymentAuthor method of PartPrePaymentAuthorDispatchAction  action the session is out----------------");
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
		vo.setAuthorDate(businessDate);
		vo.setCompanyId(compid);
		PartPrePaymentDAO service=(PartPrePaymentDAO)DaoImplInstanceFactory.getDaoImplInstance(PartPrePaymentDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass());
		status = service.savePartPrePaymentAuthor(vo);
		if(status.equalsIgnoreCase("S"))
		{
			request.setAttribute("message","S");
		}
		else
		{
			request.setAttribute("message","E");
			request.setAttribute("status",status);
		}
		return mapping.findForward("savePartPrePaymentAuthor");
	}

}
