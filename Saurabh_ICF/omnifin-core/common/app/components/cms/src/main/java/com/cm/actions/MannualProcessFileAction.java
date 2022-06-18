package com.cm.actions;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.cm.dao.CaseMarkingAuthorDAO;
//import com.cm.dao.CaseMarkingAuthorDAOImpl;
import com.cm.dao.DataUploadAuthorDAO;
import com.cm.dao.DataUploadAuthorDAOImpl;
import com.cm.dao.assetInsuranceDAO;
//import com.cm.dao.assetInsuranceDAOImpl;
import com.cm.vo.CaseMarkingAuthorVO;
import com.cm.vo.DataUploadVO;
import com.connect.CommonFunction;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

public class MannualProcessFileAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(MannualProcessFileAction.class.getName());

	public ActionForward approvedFile(ActionMapping mapping, ActionForm form,
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
					.info("here in approvedFile  method of MannualProcessFileAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("assetInsuranceID");
		session.removeAttribute("datatoapproveList");

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

		logger.info("current page link ................ "
				+ request.getParameter("d-49520-p"));

		logger.info("file Ritesh " + request.getParameter("fileNameAll"));
		logger.info("file Ritesh authorRemarks"
				+ request.getParameter("authorRemarks"));

		String fileNameAll = request.getParameter("fileNameAll");
		String authorRemarks = request.getParameter("authorRemarks");
		DataUploadAuthorDAO dao = new DataUploadAuthorDAOImpl();
		long checksumTable = dao.searchCheckSumTable(fileNameAll);
		long checksumFile = dao.calculateCheckSum(fileNameAll);

		if (checksumTable != checksumFile) {
			System.out.println("checksumvalueall " + checksumTable + " "
					+ checksumFile);
			request.setAttribute("checksum", "change");
			return mapping.findForward("success");
		} else {
			// boolean result = dao.ftpData(fileNameAll);
			boolean result = dao.moveFileToAuthorFold(fileNameAll);
			try {
				dao.callJobManually();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result == true) {
				dao.updateFlag(fileNameAll, authorRemarks);
				request.setAttribute("message", "success");
				request.setAttribute("errorLog", "errorLog");
			} else {
				request.setAttribute("message", "notsuccess");
			}
			return mapping.findForward("success");
		}
	}

	public ActionForward rejectFile(ActionMapping mapping, ActionForm form,
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
					.info("here in rejectFile  method of MannualProcessFileAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("assetInsuranceID");
		session.removeAttribute("datatoapproveList");

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

		logger.info("current page link ................ "
				+ request.getParameter("d-49520-p"));

		logger.info("file Ritesh " + request.getParameter("fileNameAll"));
		boolean statusRemove = false;
		String fileNameAll = request.getParameter("fileNameAll");
		String authorRemarks = request.getParameter("authorRemarks");
		DataUploadAuthorDAO dao = new DataUploadAuthorDAOImpl();

		statusRemove = dao.removeFile(request.getParameter("fileNameAll")
				.toString());
		if (statusRemove) {
			dao.updateRejectFlag(fileNameAll, authorRemarks);
			request.setAttribute("message", "reject");
		} else {
			request.setAttribute("message", "notreject");
		}

		return mapping.findForward("success");

	}

	public ActionForward sendBackFile(ActionMapping mapping, ActionForm form,
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
					.info("here in sendBackFile  method of MannualProcessFileAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("assetInsuranceID");
		session.removeAttribute("datatoapproveList");

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

		logger.info("current page link ................ "
				+ request.getParameter("d-49520-p"));

		logger.info("file Ritesh " + request.getParameter("fileNameAll"));

		String fileNameAll = request.getParameter("fileNameAll");
		String authorRemarks = request.getParameter("authorRemarks");
		DataUploadAuthorDAO dao = new DataUploadAuthorDAOImpl();

		dao.updateSendBackFlag(fileNameAll, authorRemarks);
		request.setAttribute("message", "sendBack");

		return mapping.findForward("success");

	}

	public ActionForward showErrorInProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		String userId = "";
		String branchId = "";
		if (userobj != null) {
			userId = userobj.getUserId();
			branchId = userobj.getBranchId();
		} else {
			logger
					.info("here in rejectFile  method of MannualProcessFileAction  the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		session.removeAttribute("pParentGroup");
		session.removeAttribute("assetID");
		session.removeAttribute("lbxAssetID");
		session.removeAttribute("assetInsuranceID");
		session.removeAttribute("datatoapproveList");

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

		logger.info("current page link ................ "
				+ request.getParameter("d-49520-p"));

		
		String filename = "a";
		DataUploadAuthorDAO dao = new DataUploadAuthorDAOImpl();
		ArrayList<DataUploadVO> errorList = dao.fetchErrorLog();
		if (errorList.size() != 0) {
		logger.info("errorList.get(0).getFileName() "
				+ errorList.get(0).getFileName());
		filename = CommonFunction.checkNull(errorList.get(0).getFileName());
		}
		if (filename == "") {
			if (errorList.size() == 0) {
				request.setAttribute("noError", "noError");
				return mapping.findForward("errorjspHeader");
				
			}
			if (errorList.size() > 0) {
				logger.info("errorList.size() " + errorList.size());
				request.setAttribute("dataList", "dataList");
				request.setAttribute("errorList", errorList);
			}

			return mapping.findForward("errorjsp");
		} else {
			
			if (errorList.size() == 0) {
				request.setAttribute("noError", "noError");
				return mapping.findForward("errorjspHeader");
				}
			if (errorList.size() > 0) {
				logger.info("errorList.size() " + errorList.size());
				request.setAttribute("dataList", "dataList");
				request.setAttribute("errorList", errorList);
			}

			return mapping.findForward("errorjspHeader");
		}
	}
}
