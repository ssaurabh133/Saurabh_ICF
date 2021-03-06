/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.cp.collateralVerification.actions;

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

import com.cp.collateralVerification.dao.CollateralVerificationDAO;
import com.cp.collateralVerification.dao.CollateralVerificationDAOImpl;
import com.cp.collateralVerification.vo.CollateralCapturingVO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/** 
 * MyEclipse Struts
 * Creation date: 09-19-2011
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class CollateralCapturingPopupDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(CollateralCapturingPopupDispatchAction.class.getName());
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveMachineVerificationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
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

		boolean status=false;
		
		
	    String makerId=userobj.getUserId();
	    String makerDate= userobj.getBusinessdate();
	    DynaValidatorForm CollateralCapturingMachineDynaValidatorForm = (DynaValidatorForm)form;
	    CollateralCapturingVO vo = new CollateralCapturingVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CollateralCapturingMachineDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		status=service.saveMachineVerificationDetails(vo);
		if(status)
		{
			request.setAttribute("sms","S");
		}
		else
		{
			request.setAttribute("sms","E");
		}
		return mapping.findForward("saveMachineVerificationDetails");
	}
	
	
	public ActionForward savePropertyVerificationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
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

		boolean status=false;
		
		
	    String makerId=userobj.getUserId();
	    String makerDate= userobj.getBusinessdate();
	    DynaValidatorForm CollateralCapturingMachineDynaValidatorForm = (DynaValidatorForm)form;
	    CollateralCapturingVO vo = new CollateralCapturingVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CollateralCapturingMachineDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		status=service.savePropertyVerificationDetails(vo);
		if(status)
		{
			request.setAttribute("sms","S");
		}
		else
		{
			request.setAttribute("sms","E");
		}
		return mapping.findForward("savePropertyVerificationDetails");
	}
	
	
	public ActionForward saveVehicleVerificationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
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

		boolean status=false;
		
		
	    String makerId=userobj.getUserId();
	    String makerDate= userobj.getBusinessdate();
	    DynaValidatorForm CollateralCapturingMachineDynaValidatorForm = (DynaValidatorForm)form;
	    CollateralCapturingVO vo = new CollateralCapturingVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CollateralCapturingMachineDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		status=service.saveVehicleVerificationDetails(vo);
		if(status)
		{
			request.setAttribute("sms","S");
		}
		else
		{
			request.setAttribute("sms","E");
		}
		return mapping.findForward("saveVehicleVerificationDetails");
	}
	
	public ActionForward saveStockVerificationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
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

		boolean status=false;
		
		
	    String makerId=userobj.getUserId();
	    String makerDate= userobj.getBusinessdate();
	    DynaValidatorForm CollateralCapturingMachineDynaValidatorForm = (DynaValidatorForm)form;
	    CollateralCapturingVO vo = new CollateralCapturingVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CollateralCapturingMachineDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		status=service.saveStockVerificationDetails(vo);
		if(status)
		{
			request.setAttribute("sms","S");
		}
		else
		{
			request.setAttribute("sms","E");
		}
		return mapping.findForward("saveStockVerificationDetails");
	}
	
	public ActionForward saveOtherVerificationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		boolean flag=false;
		UserObject userobj=(UserObject)session.getAttribute("userobject");
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
		boolean status=false;
		
		
	    String makerId=userobj.getUserId();
	    String makerDate= userobj.getBusinessdate();
	    DynaValidatorForm CollateralCapturingMachineDynaValidatorForm = (DynaValidatorForm)form;
	    CollateralCapturingVO vo = new CollateralCapturingVO();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,CollateralCapturingMachineDynaValidatorForm);
		vo.setMakerId(makerId);
		vo.setMakerDate(makerDate);		
		CollateralVerificationDAO service = new CollateralVerificationDAOImpl();
		status=service.saveOtherVerificationDetails(vo);
		if(status)
		{
			request.setAttribute("sms","S");
		}
		else
		{
			request.setAttribute("sms","E");
		}
		return mapping.findForward("saveOtherVerificationDetails");
	}
}