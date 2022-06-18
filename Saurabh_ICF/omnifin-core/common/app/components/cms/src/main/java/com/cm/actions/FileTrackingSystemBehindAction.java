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

import com.cm.dao.FileTrackingDAO;
import com.cm.vo.FileTrackingVO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class FileTrackingSystemBehindAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(FileTrackingSystemBehindAction.class.getName());

	
	public ActionForward operationFileTracking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("In operationFileTracking");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = "";
	
		if (userobj != null) {
			userId = userobj.getUserId();
	
		} else {
			logger
					.info("here in execute  method of FileTrackingSystemBehindAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		FileTrackingVO fileTrackVO = new FileTrackingVO();
		boolean flag = false;

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
		
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(fileTrackVO,
				FileTrackingDynaValidatorForm);
		
		logger.info("current page link .......... "
				+ request.getParameter("d-49520-p"));

		int currentPageLink = 0;
		if (request.getParameter("d-49520-p") == null
				|| request.getParameter("d-49520-p").equalsIgnoreCase("0")) {
			currentPageLink = 1;
		} else {
			currentPageLink = Integer.parseInt(request
					.getParameter("d-49520-p"));
		}

		logger.info("current page link ...currentPageLink............. "
				+ currentPageLink);

		fileTrackVO.setCurrentPageLink(currentPageLink);
		fileTrackVO.setUserId(userId);

		FileTrackingDAO dao = (FileTrackingDAO) DaoImplInstanceFactory
				.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + dao.getClass());
		String lbxLoanNo=CommonFunction.checkNull(request.getParameter("lbxLoanNoHIDSearch"));
		fileTrackVO.setLbxLoanNoHIDSearch(lbxLoanNo);
		
		String fileTrackStatus=CommonFunction.checkNull(request.getParameter("fileTrackStatus"));
		fileTrackVO.setFileTrackStatus(fileTrackStatus);
		logger.info("fileTrackStatus: "+fileTrackStatus);
		fileTrackVO.setTrackStatus("SS,RS"); // not avail this loan in operation 
        fileTrackVO.setStageStep("operation"); // for operation file tracking 
		ArrayList list = dao.searchfileTracking(fileTrackVO);
		request.setAttribute("list", list);
     	return mapping.findForward("operationFileTracking");

	}
	
	public ActionForward branchFileTracking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("In branchFileTracking");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = "";
		
		if (userobj != null) {
			userId = userobj.getUserId();
		
		} else {
			logger.info("here in execute  method of FileTrackingSystemBehindAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}
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
		FileTrackingVO fileTrackVO = new FileTrackingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(fileTrackVO,
				FileTrackingDynaValidatorForm);
		
		logger.info("current page link .......... "
				+ request.getParameter("d-49520-p"));

		int currentPageLink = 0;
		if (request.getParameter("d-49520-p") == null
				|| request.getParameter("d-49520-p").equalsIgnoreCase("0")) {
			currentPageLink = 1;
		} else {
			currentPageLink = Integer.parseInt(request
					.getParameter("d-49520-p"));
		}

		logger.info("current page link ...currentPageLink............. "
				+ currentPageLink);

		fileTrackVO.setCurrentPageLink(currentPageLink);
		fileTrackVO.setUserId(userId);

		FileTrackingDAO dao = (FileTrackingDAO) DaoImplInstanceFactory
				.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + dao.getClass());
		String lbxLoanNo=CommonFunction.checkNull(request.getParameter("lbxLoanNoHIDSearch"));
		fileTrackVO.setLbxLoanNoHIDSearch(lbxLoanNo);
		
		String fileTrackStatus=CommonFunction.checkNull(request.getParameter("fileTrackStatus"));
		fileTrackVO.setFileTrackStatus(fileTrackStatus);
		logger.info("fileTrackStatus: "+fileTrackStatus);
        fileTrackVO.setTrackStatus("SO"); // For loan visible at branch file tracking
        fileTrackVO.setStageStep("branch");// For opening branch file tracking
		ArrayList list = dao.searchfileTracking(fileTrackVO);
		request.setAttribute("list", list);
   		return mapping.findForward("branchFileTracking");

	}
	public ActionForward storeFileTracking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("In storeFileTracking");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = "";
	
		if (userobj != null) {
			userId = userobj.getUserId();
		
		} else {
			logger
					.info("here in execute  method of FileTrackingSystemBehindAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		FileTrackingVO fileTrackVO = new FileTrackingVO();
		boolean flag = false;

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
		
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(fileTrackVO,
				FileTrackingDynaValidatorForm);
		
		logger.info("current page link .......... "
				+ request.getParameter("d-49520-p"));

		int currentPageLink = 0;
		if (request.getParameter("d-49520-p") == null
				|| request.getParameter("d-49520-p").equalsIgnoreCase("0")) {
			currentPageLink = 1;
		} else {
			currentPageLink = Integer.parseInt(request
					.getParameter("d-49520-p"));
		}

		logger.info("current page link ...currentPageLink............. "
				+ currentPageLink);

		fileTrackVO.setCurrentPageLink(currentPageLink);
		fileTrackVO.setUserId(userId);

		FileTrackingDAO dao = (FileTrackingDAO) DaoImplInstanceFactory
				.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + dao.getClass());
		String lbxLoanNo=CommonFunction.checkNull(request.getParameter("lbxLoanNoHIDSearch"));
		fileTrackVO.setLbxLoanNoHIDSearch(lbxLoanNo);
		String fileTrackStatus=CommonFunction.checkNull(request.getParameter("fileTrackStatus"));
		fileTrackVO.setFileTrackStatus(fileTrackStatus);
		logger.info("fileTrackStatus: "+fileTrackStatus);
        fileTrackVO.setTrackStatus("SS,RS");// only visible these loan
        fileTrackVO.setStageStep("store"); // for opening store
		ArrayList list = dao.searchfileTracking(fileTrackVO);
		request.setAttribute("list", list);
     	return mapping.findForward("storeFileTracking");

	}
		
	public ActionForward viewFileTracking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("In viewFileTracking");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = "";
	
		if (userobj != null) {
			userId = userobj.getUserId();
	
		} else {
			logger
					.info("here in execute  method of FileTrackingSystemBehindAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		FileTrackingVO fileTrackVO = new FileTrackingVO();
		boolean flag = false;

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
		
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(fileTrackVO,
				FileTrackingDynaValidatorForm);
		
		logger.info("current page link .......... "
				+ request.getParameter("d-49520-p"));

		int currentPageLink = 0;
		if (request.getParameter("d-49520-p") == null
				|| request.getParameter("d-49520-p").equalsIgnoreCase("0")) {
			currentPageLink = 1;
		} else {
			currentPageLink = Integer.parseInt(request
					.getParameter("d-49520-p"));
		}

		logger.info("current page link ...currentPageLink............. "
				+ currentPageLink);

		fileTrackVO.setCurrentPageLink(currentPageLink);
		fileTrackVO.setUserId(userId);

		FileTrackingDAO dao = (FileTrackingDAO) DaoImplInstanceFactory
				.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + dao.getClass());
		String lbxLoanNo=CommonFunction.checkNull(request.getParameter("lbxLoanNoHIDSearch"));
		fileTrackVO.setLbxLoanNoHIDSearch(lbxLoanNo);
		ArrayList list = dao.getFileTrackingDataForView(fileTrackVO);
		request.setAttribute("list", list);
        return mapping.findForward("viewFileTracking");

	}
	//Manish Shukla for Branch BulkFileTracking
	public ActionForward branchBulkFileTracking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("In branchFileTracking");
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = null;
		String branchId=null;
		
		if (userobj != null) {
			userId = userobj.getUserId();
			branchId=userobj.getBranchId();
		
		} else {
			logger.info("here in execute  method of FileTrackingSystemBehindAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		Object sessionId = session.getAttribute("sessionID");

		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
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
		FileTrackingVO fileTrackVO = new FileTrackingVO();
		DynaValidatorForm FileTrackingDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(fileTrackVO,FileTrackingDynaValidatorForm);
		FileTrackingDAO dao = (FileTrackingDAO) DaoImplInstanceFactory.getDaoImplInstance(FileTrackingDAO.IDENTITY);
		logger.info("Implementation class: " + dao.getClass());
		fileTrackVO.setUserId(userId);
		logger.info("allBranches: >>" + fileTrackVO.getAllBranches());
		logger.info(" startDate: " + fileTrackVO.getStartDate());
		logger.info("endDate: " + fileTrackVO.getEndDate());
		logger.info(" loanID: " + fileTrackVO.getLbxLoanNoHID());
		logger.info(" branchID: " + fileTrackVO.getLbxBranchId());
		logger.info("all bracnh "+fileTrackVO.getAllBranches());
		String search=CommonFunction.checkNull(request.getParameter("search")).trim();
		if(CommonFunction.checkNull(search).trim().equalsIgnoreCase(""))
			fileTrackVO.setLbxBranchId(branchId);
		ArrayList list = dao.searchBulkfileTracking(fileTrackVO);
		request.setAttribute("list", list);
		session.setAttribute("userId", userId);
   		return mapping.findForward("bulkFileTracking");

	}
}
