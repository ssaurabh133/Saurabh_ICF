package com.cm.actions;

import java.util.Vector;

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

import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cm.dao.DataUploadAuthorDAO;
import com.cm.daoImplMYSQL.DataUploadAuthorDAOImpl;
import com.cm.vo.DataUploadVO;

public class DataUploadProcessAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(DataUploadProcessAction.class.getName());

	public ActionForward uploadData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		boolean flag1 = false;
		String userId ="";
		String businessDate ="";
		int compid =0;
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		int row_count = 0;
		double total_txn_amount=0.0;
		if (userobj == null) {
			logger.info("in  uploadData method of  UploadReceiptProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}if(userobj!=null){
			userId = userobj.getUserId();
			businessDate=userobj.getBusinessdate();
			compid=userobj.getCompanyId();
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

		session.removeAttribute("Processfile");
		logger.info("Inside Processing Action of Data Upload");
		// uploadVO uwDocVo = new uploadVO();
		DataUploadVO uwDocVo = new DataUploadVO();
		DynaValidatorForm UploadDynaValidatorForm = (DynaValidatorForm) form;
		logger.info("Copying form data to Vo");
		org.apache.commons.beanutils.BeanUtils.copyProperties(uwDocVo,
				UploadDynaValidatorForm);
		// CreditManagementDAO service = new CreditManagementDAOImpl();
		String sms = "";
		boolean uploadStatus = false;
		long checksumValue = 0;
		boolean insrtData = false;
		boolean checkFileStatus=false;
		uwDocVo.setMakerId(userId);
		uwDocVo.setMakerDate(businessDate);
		
		
		DataUploadAuthorDAO dao=new DataUploadAuthorDAOImpl();
		
		checkFileStatus=dao.checkDuplicateFile(uwDocVo.getDocFile().getFileName());
		if(checkFileStatus==true){
			
			request.setAttribute("error", "fileAlreadExist");
			return mapping.findForward("success");	
		}
		uploadStatus = dao.docUpload(request, uwDocVo
				.getDocFile());

		uwDocVo.setFileName(request.getAttribute("fileName").toString());
		uwDocVo.setDocPath(request.getAttribute("filePath").toString());
		String flag = (String) request.getAttribute("message");
		if (uploadStatus) {
			
			Vector excel_vector = dao.readFile(uwDocVo.getDocFile().getFileName());
			row_count = (excel_vector.size()) - 1;
			 total_txn_amount = dao.read(excel_vector);
			 logger.info("row_count $$$$$$$$$$$ "+row_count);
			 logger.info("total_txn_amount $$$$$$$$$ "+total_txn_amount);
			checksumValue = dao.calculateCheckSum(uwDocVo.getDocFile().getFileName());
			insrtData = dao.insertImportMannualFileDtl(request,uwDocVo.getDocFile(), checksumValue,row_count,total_txn_amount,uwDocVo);
			logger.info("insrtData $$$$$$$$$$$$$ "+insrtData);	
			}

		if (flag == "O") {
			request.setAttribute("row_count", row_count);
			request.setAttribute("total_txn_amount", total_txn_amount);
			request.setAttribute("sms", "");
		}
		if (flag == "E") {
			request.setAttribute("smsno", "");
		}

		return mapping.findForward("success");
	}
	
	public ActionForward makerForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		boolean flag1 = false;
		String userId ="";
		String businessDate ="";
		int compid =0;
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		int row_count = 0;
		double total_txn_amount=0.0;
		if (userobj == null) {
			logger.info("in  makerForward method of  UploadReceiptProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}if(userobj!=null){
			userId = userobj.getUserId();
			businessDate=userobj.getBusinessdate();
			compid=userobj.getCompanyId();
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
		boolean status=false;
		session.removeAttribute("Processfile");
		
		logger.info("Inside Processing Action of Data Upload");
		logger.info("fileNameAll$$$$$$$$$$$ "+request.getParameter("fileNameAll"));
		
		DataUploadAuthorDAO dao=new DataUploadAuthorDAOImpl();
		status=dao.forwardFile(request.getParameter("fileNameAll").toString());
		if (status){
		request.setAttribute("status", "forward");
		}else{
			request.setAttribute("status", "notforward");
		}
		return mapping.findForward("success");
	}
	
	public ActionForward makerDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		boolean flag1 = false;
		String userId ="";
		String businessDate ="";
		int compid =0;
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		int row_count = 0;
		double total_txn_amount=0.0;
		if (userobj == null) {
			logger.info("in  makerDelete method of  UploadReceiptProcessAction action the session is out----------------");
			return mapping.findForward("sessionOut");
		}if(userobj!=null){
			userId = userobj.getUserId();
			businessDate=userobj.getBusinessdate();
			compid=userobj.getCompanyId();
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
		boolean statusDelete=false;
		boolean statusRemove=false;
		session.removeAttribute("Processfile");
		
		com.cm.dao.DataUploadAuthorDAO dao=new DataUploadAuthorDAOImpl();
		
		statusRemove=dao.removeFile(request.getParameter("fileNameAll").toString());
		if (statusRemove){
			statusDelete=dao.deleteFile(request.getParameter("fileNameAll").toString());
		}
		if (statusRemove && statusDelete){
		request.setAttribute("status", "delete");
		}else{
			request.setAttribute("status", "notdelete");
		}
		return mapping.findForward("success");
	}
}