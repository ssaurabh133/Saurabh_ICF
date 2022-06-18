package com.cp.actions;

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

import com.connect.DaoImplInstanceFactory;
import com.cp.dao.FieldVerificationDAO;
import com.cp.vo.FieldVerificationVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

/**
 * MyEclipse Struts Creation date: 05-13-2011
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class FieldVerificationCapturingBehindAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(FieldVerificationCapturingBehindAction.class.getName());

	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward verificationCapturingCreditProcessing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = "";
		String branchId = "";
		if (userobj != null) {
			userId = userobj.getUserId();
			branchId = userobj.getBranchId();
		} else {
			logger
					.info("here in verificationCapturingCreditProcessing method of FieldVerificationCapturingBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		logger.info("In FieldVerificationCapturingBehindAction...verificationCapturingCreditProcessing... ");

		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		DynaValidatorForm FieldVarificationDynaValidatorForm = (DynaValidatorForm) form;// TODO
		/*Changed by asesh*/
		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldDao.getClass()); 
		/*Changed by asesh*/
		FieldVerificationVo vo = new FieldVerificationVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FieldVarificationDynaValidatorForm);

		logger.info("current page link .......... "
				+ request.getParameter("d-7061068-p"));

		int currentPageLink = 0;
		if (request.getParameter("d-7061068-p") == null
				|| request.getParameter("d-7061068-p").equalsIgnoreCase("0")) {
			currentPageLink = 1;
		} else {
			currentPageLink = Integer.parseInt(request
					.getParameter("d-7061068-p"));
		}

		logger.info("current page link ................ " + currentPageLink);
		vo.setCurrentPageLink(currentPageLink);
		vo.setMakerId(userId);
		vo.setBranchId(branchId);
		session.setAttribute("userId", userId);
		session.removeAttribute("verificationId");
		session.removeAttribute("dealId");
		session.removeAttribute("entityId");
		session.removeAttribute("dealHeader");
		String functionId="";
		String ID="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		vo.setFunctionId(functionId);
		ArrayList defaultlist = fieldDao.getDefaultCaptureData(vo);
	   	request.setAttribute("defaultlist", defaultlist);
		return mapping.findForward("success");
	}
	
	public ActionForward verificationCapturingCreditManagement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = "";
		String branchId = "";
		if (userobj != null) {
			userId = userobj.getUserId();
			branchId = userobj.getBranchId();
		} else {
			logger
					.info("here in verificationCapturingCreditManagement method of FieldVerificationCapturingBehindAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		logger.info("In FieldVerificationCapturingBehindAction.(verificationCapturingCreditManagement)..... ");

		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId
					.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);
		if (!strFlag.equalsIgnoreCase("")) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		DynaValidatorForm FieldVarificationDynaValidatorForm = (DynaValidatorForm) form;// TODO
		/*Changed by asesh*/
		FieldVerificationDAO fieldDao=(FieldVerificationDAO)DaoImplInstanceFactory.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
        logger.info("Implementation class: "+fieldDao.getClass()); 
		/*Changed by asesh*/
		FieldVerificationVo vo = new FieldVerificationVo();
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FieldVarificationDynaValidatorForm);

		logger.info("current page link .......... "
				+ request.getParameter("d-7061068-p"));

		int currentPageLink = 0;
		if (request.getParameter("d-7061068-p") == null
				|| request.getParameter("d-7061068-p").equalsIgnoreCase("0")) {
			currentPageLink = 1;
		} else {
			currentPageLink = Integer.parseInt(request
					.getParameter("d-7061068-p"));
		}

		logger.info("current page link ................ " + currentPageLink);
		vo.setCurrentPageLink(currentPageLink);
		vo.setMakerId(userId);
		vo.setBranchId(branchId);
		session.setAttribute("userId", userId);
		session.removeAttribute("verificationId");
		session.removeAttribute("dealId");
		session.removeAttribute("entityId");
		session.removeAttribute("dealHeader");
		String functionId="";
		String ID="";
		if(session.getAttribute("functionId")!=null)
		{
			functionId=session.getAttribute("functionId").toString();
		}
		logger.info("functionId: "+functionId);
		vo.setFunctionId(functionId);
       	ArrayList defaultlist = fieldDao.getDefaultCaptureDataAtCM(vo);
		request.setAttribute("defaultlist", defaultlist);
		return mapping.findForward("success");
	}
}
