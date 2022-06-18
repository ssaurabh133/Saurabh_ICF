package com.cm.actions;

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

import com.cm.dao.HandSightingDAO;
import com.cm.vo.HandSightingVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class HandSightingDispatchAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(HandSightingDispatchAction.class.getName());

	public ActionForward searchFileTrackingDataForView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" in searchFileTrackingDataForView......");
		String forward="viewSuccess";
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
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

		HandSightingVO vo = new HandSightingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FileTrackingDynaValidatorForm);
		if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		HandSightingDAO fileTrackDAO = (HandSightingDAO) DaoImplInstanceFactory.getDaoImplInstance(HandSightingDAO.IDENTITY);
		logger.info("Implementation class: " + fileTrackDAO.getClass()
				+ ".........Tracking_id......." + vo.getTrackingId());
		
		logger.info("LbxLoanNoHID................."+request.getParameter("lbxLoanNoHIDSearch"));
		
		vo.setLbxLoanNoHIDSearch(CommonFunction.checkNull(request.getParameter("lbxLoanNoHIDSearch")));
		ArrayList list = fileTrackDAO.getFileTrackingDataForView(vo);
		
		logger.info("Inside searchFileTrackingDataForView..displaying status...." + list);
			request.setAttribute("list", list);
			if(CommonFunction.checkNull(request.getParameter("viewMode")).equalsIgnoreCase("viewMode")){
			request.setAttribute("editVal", "editVal");
			request.setAttribute("viewMode", "viewMode");
			forward="saveFileTracking";
			}
		return mapping.findForward(forward);
	}

	public ActionForward openNewFileTracking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Inside openNewFileTracking...........");

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		if (userobj == null) {
			logger
					.info("here in openNewFileTracking method of FileTrackingDispatchAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
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
		session.removeAttribute("loanId");
		return mapping.findForward("openNewFileTracking");
	}

	public ActionForward saveFileTrackingRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" in saveFileTrackingRecord......");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
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

		HandSightingVO vo = new HandSightingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FileTrackingDynaValidatorForm);
		/* changed by asesh */
		if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		HandSightingDAO fileTrackDAO = (HandSightingDAO) DaoImplInstanceFactory.getDaoImplInstance(HandSightingDAO.IDENTITY);
		logger.info("Implementation class: " + fileTrackDAO.getClass()); // changed

		boolean status = fileTrackDAO.insertFileTracking(vo);
		logger
				.info("Inside saveFileTrackingRecord Action.....displaying status...."
						+ status);
		if (status) {
			ArrayList<HandSightingVO> list = new ArrayList<HandSightingVO>();
			list.add(vo);
			request.setAttribute("list", list);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("sms", "S");
		} else {

			request.setAttribute("sms", "E");
		}
		logger.info("status" + status + vo.getLbxDealNo());
		return mapping.findForward("saveFileTracking");
	}

	public ActionForward updateFileTrackingRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" in updateFileTrackingRecord......");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
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

		HandSightingVO vo = new HandSightingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FileTrackingDynaValidatorForm);
		/* changed by asesh */
		if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		HandSightingDAO fileTrackDAO = (HandSightingDAO) DaoImplInstanceFactory.getDaoImplInstance(HandSightingDAO.IDENTITY);
		logger.info("Implementation class: " + fileTrackDAO.getClass()); // changed

		boolean status = fileTrackDAO.updateFileTracking(vo);
		logger.info("Inside updateFileTrackingRecord Action.....displaying status...." + status);
		if (status) {
			ArrayList<HandSightingVO> list = new ArrayList<HandSightingVO>();
			list.add(vo);
			request.setAttribute("list", list);
			request.setAttribute("editVal", "editVal");
			request.setAttribute("sms", "S");
		} else {

			request.setAttribute("sms", "E");
		}
		return mapping.findForward("saveFileTracking");
	}

	public ActionForward getFileTrackingData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" in getFileTrackingData......");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
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

		HandSightingVO vo = new HandSightingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FileTrackingDynaValidatorForm);
		if (userobj != null) {
			vo.setMakerId(userobj.getUserId());
			vo.setMakerDate(userobj.getBusinessdate());
		}
		HandSightingDAO fileTrackDAO = (HandSightingDAO) DaoImplInstanceFactory.getDaoImplInstance(HandSightingDAO.IDENTITY);
		logger.info("Implementation class: " + fileTrackDAO.getClass()
				+ ".........Tracking_id......." + vo.getTrackingId());
		
		logger.info("LbxLoanNoHID................."+request.getParameter("LbxLoanNoHID"));
		
		vo.setLbxLoanNoHID(request.getParameter("LbxLoanNoHID"));
		ArrayList list = fileTrackDAO.getFileTrackingData(vo);
		
		logger.info("Inside getFileTrackingData..displaying status...." + list);
		if (list.size() > 0) {
			request.setAttribute("list", list);
			request.setAttribute("editVal", "editVal");
		}
		return mapping.findForward("saveFileTracking");
	}

}
