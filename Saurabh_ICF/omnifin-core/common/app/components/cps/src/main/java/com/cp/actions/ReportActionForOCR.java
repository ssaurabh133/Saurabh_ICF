package com.cp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import net.sf.jasperreports.engine.export.JExcelApiExporter;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRStyledText.Run;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.connect.ConnectionDAO;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cp.dao.FieldVerificationDAO;
import com.cp.dao.FileUtilityDao;
import com.cp.dao.FinancialReportDao;
import com.cp.dao.IndividualFinancialAnalysisDAO;
import com.cp.eCalMacroPOC.PushDataInTargetSheet;
import com.cp.process.ConstantValue;
import com.cp.process.EligibilityCalculationProcess;
import com.cp.vo.FinancialReportVo;
import com.cp.vo.UnderwritingDocUploadVo;
import com.cp.vo.FundFlowDownloadUploadVo;

public class ReportActionForOCR extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(ReportActionForOCR.class.getName());

	public ActionForward generateCamReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In generateCamReport()");

		String userId = null;

		String branch = null;

		String bDate = null;

		UserObject userobj = null;

		try {

			HttpSession session = request.getSession();

			userobj = (UserObject) session.getAttribute("userobject");

			if (userobj != null) {
				userId = userobj.getUserId();

				branch = userobj.getBranchId();

				bDate = userobj.getBusinessdate();
			} else {
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			// for check User session start
			ServletContext context = getServlet().getServletContext();

			String strFlag = "";

			if (sessionId != null) {
				strFlag = UserSessionCheck.checkSameUserSession(userobj,
						sessionId.toString(), "", request);
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

			String caseId = CommonFunction.checkNull(session
					.getAttribute("caseId"));
			if (caseId.equalsIgnoreCase(""))
				caseId = CommonFunction.checkNull(session
						.getAttribute("financialDealId"));

			logger.info("case_id---------" + caseId);

			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);

			String refId = dao.getApplicationReferenceNo(caseId);

			String productId = dao.getProductId(caseId);
			String camTemplateId = dao.getProductCamTemplateId(productId);
			productId=camTemplateId;
			logger.info("refId---------" + refId);

			String camTemplateFile = dao
					.getParameterMSTInfo(ConstantValue.CAM_TEMPLATE_FILE);

			/*String camTemplatePath = dao
					.getParameterMSTInfo(ConstantValue.FILE_PATH);*/
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			String readFile = camTemplatePath + "/" + camTemplateFile + "_"
					+ productId + "_" + refId + ".xls";

			logger.info("readFile---------" + readFile);

			ArrayList outMessage = dao.saveTargetValuesTmp(caseId,
					userobj.getBusinessdate(), userobj.getUserId());
			logger.info("s1---" + CommonFunction.checkNull(outMessage.get(0))
					+ "---s2---" + CommonFunction.checkNull(outMessage.get(1)));
			if (CommonFunction.checkNull(outMessage.get(0)).equalsIgnoreCase(
					"S"))
				new PushDataInTargetSheet().pushDataInTargetSheet(readFile,
						caseId);

			File file = new File(readFile);

			response.setContentType("application/vnd.ms-excel");

			response.addHeader("Content-Disposition", "attachment; filename="
					+ camTemplateFile + "_" + refId + ".xls");

			response.setContentLength((int) file.length());

			FileInputStream fileInputStream = new FileInputStream(file);

			OutputStream responseOutputStream = response.getOutputStream();

			int bytes;

			while ((bytes = fileInputStream.read()) != -1) {

				responseOutputStream.write(bytes);
			}
			fileInputStream.close();

			responseOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward generateBankAccAnalysisReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In generateBankAccAnalysisReport()");
		String userId = null;
		String branch = null;
		String bDate = null;
		UserObject userobj = null;

		try {

			HttpSession session = request.getSession();
			userobj = (UserObject) session.getAttribute("userobject");
			if (userobj != null) {
				userId = userobj.getUserId();
				branch = userobj.getBranchId();
				bDate = userobj.getBusinessdate();
			} else {
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			// for check User session start
			ServletContext context = getServlet().getServletContext();
			String strFlag = "";
			if (sessionId != null) {
				strFlag = UserSessionCheck.checkSameUserSession(userobj,
						sessionId.toString(), "", request);
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

			String caseId = "";
			if (session.getAttribute("fundFlowDealId") != null
					&& CommonFunction.checkNull(
							session.getAttribute("functionId"))
							.equalsIgnoreCase("3000261")) {
				caseId = session.getAttribute("fundFlowDealId").toString();
			}

			else
				caseId = CommonFunction.checkNull(session
						.getAttribute("financialDealId"));
			String functionId = CommonFunction.checkNull(session
					.getAttribute("functionId"));
			String stageId = "";
			if (functionId.equalsIgnoreCase("3000261"))
				stageId = "DUM";
			else if (functionId.equalsIgnoreCase("3000276")) {
				stageId = "DUM";
			}
			logger.info("case_id---------" + caseId);
			String timeStamp = System.nanoTime() + "";
			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);
			String workFlowStatus = dao.checkWorkFlowStatus(caseId, stageId);
			logger.info("workFlowStatus--------" + workFlowStatus);
			if (!workFlowStatus.equalsIgnoreCase("A")) {
				FieldVerificationDAO fieldVerificationdao = (FieldVerificationDAO) DaoImplInstanceFactory
						.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
				logger.info("Implementation class: "
						+ fieldVerificationdao.getClass());
				ArrayList verifMethodList = fieldVerificationdao
						.getVerifMethodListList();
				request.setAttribute("verifMethodList", verifMethodList);
				request.setAttribute("sms", "wfs");
				return mapping.findForward("bankReportMsg");
			}
			String refId = dao.getApplicationReferenceNo(caseId);
			logger.info("refId---------" + refId);
			UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
			vo.setCustRef(refId);
			vo.setCaseId(caseId);
			String camTemplateFile = dao
					.getParameterMSTInfo(ConstantValue.CAM_TEMPLATE_BANK_ACC_ANALYSIS_REPORT);
			/*String camTemplatePath = dao
					.getParameterMSTInfo(ConstantValue.FILE_PATH);*/
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			boolean flag = EligibilityCalculationProcess
					.readAndWriteFileForReport(vo, camTemplateFile, timeStamp);
			String readFile = camTemplatePath + "/" + camTemplateFile + "_"
					+ refId + "_" + timeStamp + ".xls";
			EligibilityCalculationProcess.systemInputData$BS(readFile, vo);
			// RunMacro.executeMacro(readFile, vo);
			// logger.info("readFile---------"+readFile);
			File file = new File(readFile);
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ camTemplateFile + "_" + refId + ".xls");
			response.setContentLength((int) file.length());

			FileInputStream fileInputStream = new FileInputStream(file);
			OutputStream responseOutputStream = response.getOutputStream();
			int bytes;
			while ((bytes = fileInputStream.read()) != -1) {
				responseOutputStream.write(bytes);
			}
			fileInputStream.close();
			responseOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward generateObligationReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In generateObligationReport()");
		String userId = null;
		String branch = null;
		String bDate = null;
		UserObject userobj = null;

		try {

			HttpSession session = request.getSession();
			userobj = (UserObject) session.getAttribute("userobject");
			if (userobj != null) {
				userId = userobj.getUserId();
				branch = userobj.getBranchId();
				bDate = userobj.getBusinessdate();
			} else {
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			// for check User session start
			ServletContext context = getServlet().getServletContext();
			String strFlag = "";
			if (sessionId != null) {
				strFlag = UserSessionCheck.checkSameUserSession(userobj,
						sessionId.toString(), "", request);
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

			String caseId = "";
			if (session.getAttribute("fundFlowDealId") != null
					&& CommonFunction.checkNull(
							session.getAttribute("functionId"))
							.equalsIgnoreCase("3000261")) {
				caseId = session.getAttribute("fundFlowDealId").toString();
			}

			String functionId = CommonFunction.checkNull(session
					.getAttribute("functionId"));
			String stageId = "";
			if (functionId.equalsIgnoreCase("3000261"))
				stageId = "DUM";
			else if (functionId.equalsIgnoreCase("3000276")) {
				stageId = "DUM";
			}
			logger.info("case_id---------" + caseId);
			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);
			String workFlowStatus = dao.checkWorkFlowStatus(caseId, stageId);
			if (!workFlowStatus.equalsIgnoreCase("A")) {
				IndividualFinancialAnalysisDAO dao1 = (IndividualFinancialAnalysisDAO) DaoImplInstanceFactory
						.getDaoImplInstance(IndividualFinancialAnalysisDAO.IDENTITY);
				logger.info("Implementation class: " + dao.getClass());
				ArrayList customerTypeList = dao1.getCustomerTypeList(caseId);
				ArrayList obligationTypeList = dao1.getObligationTypeList();
				ArrayList customerNameList = dao1.getCustomerName(caseId,
						"PRAPPL");
				ArrayList typeOfLoanList = dao1.getTypeOfLoan();

				request.setAttribute("customerNameList", customerNameList);
				request.setAttribute("customerTypeList", customerTypeList);
				request.setAttribute("obligationTypeList", obligationTypeList);
				request.setAttribute("typeOfLoanList", typeOfLoanList);
				FieldVerificationDAO fieldVerificationdao = (FieldVerificationDAO) DaoImplInstanceFactory
						.getDaoImplInstance(FieldVerificationDAO.IDENTITY);
				logger.info("Implementation class: "
						+ fieldVerificationdao.getClass());
				ArrayList verifMethodList = fieldVerificationdao
						.getVerifMethodListList();
				request.setAttribute("verifMethodList", verifMethodList);
				request.setAttribute("sms", "wfs");
				return mapping.findForward("obligationReportMsg");
			}
			String refId = dao.getApplicationReferenceNo(caseId);
			logger.info("refId---------" + refId);
			UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
			vo.setCustRef(refId);
			vo.setCaseId(caseId);
			String timeStamp = System.nanoTime() + "";
			String camTemplateFile = dao
					.getParameterMSTInfo(ConstantValue.CAM_TEMPLATE_OBLIGATION_REPORT);
			/*String camTemplatePath = dao
					.getParameterMSTInfo(ConstantValue.FILE_PATH);*/
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			String writeCamTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			boolean flag = EligibilityCalculationProcess
					.readAndWriteFileForReport(vo, camTemplateFile, timeStamp);
			String readFile = camTemplatePath + "/" + camTemplateFile + "_"
					+ refId + "_" + timeStamp + ".xls";
			
			String productId = dao.getProductId(caseId);
			vo.setProductId(productId);
			EligibilityCalculationProcess.systemInputData$Obligation(readFile,
					vo,"Obligation & RTR- BIL");
			EligibilityCalculationProcess.systemInputData$CustDemographic(
					readFile, vo);
			logger.info("readFile---------" + readFile);
			// RunMacro.executeMacro(readFile, vo);
			File file = new File(readFile);
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ camTemplateFile + "_" + refId + ".xls");
			response.setContentLength((int) file.length());

			FileInputStream fileInputStream = new FileInputStream(file);
			OutputStream responseOutputStream = response.getOutputStream();
			int bytes;
			while ((bytes = fileInputStream.read()) != -1) {
				responseOutputStream.write(bytes);
			}
			fileInputStream.close();
			responseOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward generateRatioReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In generateRatioReport()");
		String userId = null;
		String branch = null;
		String bDate = null;
		UserObject userobj = null;

		try {

			HttpSession session = request.getSession();
			userobj = (UserObject) session.getAttribute("userobject");
			if (userobj != null) {
				userId = userobj.getUserId();
				branch = userobj.getBranchId();
				bDate = userobj.getBusinessdate();
			} else {
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			// for check User session start
			ServletContext context = getServlet().getServletContext();
			String strFlag = "";
			if (sessionId != null) {
				strFlag = UserSessionCheck.checkSameUserSession(userobj,
						sessionId.toString(), "", request);
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

			String caseId = "";
			if (session.getAttribute("financialDealId") != null) {
				caseId = session.getAttribute("financialDealId").toString();
			}
			String functionId = CommonFunction.checkNull(session
					.getAttribute("functionId"));
			String stageId = "";
			if (functionId.equalsIgnoreCase("3000261"))
				stageId = "DUM";
			else if (functionId.equalsIgnoreCase("3000276")) {
				stageId = "DUM";
			}
			logger.info("case_id---------" + caseId);
			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);
			String workFlowStatus = dao.checkWorkFlowStatus(caseId, stageId);
			if (!workFlowStatus.equalsIgnoreCase("A")) {
				request.setAttribute("sms", "wfs");
				return mapping.findForward("ratioReportMsg");
			}
			String refId = dao.getApplicationReferenceNo(caseId);
			logger.info("refId---------" + refId);
			UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
			vo.setCustRef(refId);
			vo.setCaseId(caseId);
			String timeStamp = System.nanoTime() + "";
			String camTemplateFile = dao
					.getParameterMSTInfo(ConstantValue.CAM_TEMPLATE_RATIO_REPORT);
			boolean flag = EligibilityCalculationProcess
					.readAndWriteFileForReport(vo, camTemplateFile, timeStamp);
			/*String camTemplatePath = dao
					.getParameterMSTInfo(ConstantValue.FILE_PATH);*/
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			String readFile = camTemplatePath + "/" + camTemplateFile + "_"
					+ refId + "_" + timeStamp + ".xls";
			EligibilityCalculationProcess.systemInputData$BALS(readFile, vo);
			EligibilityCalculationProcess.systemInputData$PL(readFile, vo);
			logger.info("readFile---------" + readFile);
			// RunMacro.executeMacro(readFile, vo);
			File file = new File(readFile);
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ camTemplateFile + "_" + refId + ".xls");
			response.setContentLength((int) file.length());

			FileInputStream fileInputStream = new FileInputStream(file);
			OutputStream responseOutputStream = response.getOutputStream();
			int bytes;
			while ((bytes = fileInputStream.read()) != -1) {
				responseOutputStream.write(bytes);
			}
			fileInputStream.close();
			responseOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward generateMDBProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In generateMDBProcess()");
		String userId = null;
		String branch = null;
		String bDate = null;
		UserObject userobj = null;

		try {

			HttpSession session = request.getSession();
			userobj = (UserObject) session.getAttribute("userobject");
			if (userobj != null) {
				userId = userobj.getUserId();
				branch = userobj.getBranchId();
				bDate = userobj.getBusinessdate();
			} else {
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			// for check User session start
			ServletContext context = getServlet().getServletContext();
			String strFlag = "";
			if (sessionId != null) {
				strFlag = UserSessionCheck.checkSameUserSession(userobj,
						sessionId.toString(), "", request);
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
			String caseId = CommonFunction.checkNull(session.getAttribute("dealId"));
			logger.info("session.getAttribute(dealId).toString() ----"+CommonFunction.checkNull(session.getAttribute("dealId")));
			/*if (session.getAttribute("fundFlowDealId") != null
					&& CommonFunction.checkNull(
							session.getAttribute("functionId"))
							.equalsIgnoreCase("3000261")) {
				logger.info("in fundFlowDealId block");
				caseId = session.getAttribute("fundFlowDealId").toString();
			} else if (CommonFunction.checkNull(
					session.getAttribute("functionId")).equalsIgnoreCase(
					"3000276"))
				caseId = CommonFunction.checkNull(session
						.getAttribute("financialDealId"));*/

			String reportType = CommonFunction.checkNull(request
					.getParameter("reportName"));
			String stageId = "";
			/*if (CommonFunction.checkNull(session.getAttribute("functionId"))
					.equalsIgnoreCase("3000261"))
				stageId = "FFA";
			else
				stageId = "FA";*/
			stageId="UW";
			FinancialReportVo vo = new FinancialReportVo();
			vo.setMakerId(userId);
			vo.setMakerDate(bDate);
			vo.setCaseId(caseId);
			vo.setReportType(reportType);
			vo.setStageId(stageId);
			FinancialReportMDBProcess.sendToOCRMDB(mapping, request, response,
					form, vo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("refresh");
	}

	public ActionForward refreshForReportLink(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In refreshForReportLink()");
		String userId = null;
		String branch = null;
		String bDate = null;
		UserObject userobj = null;

		try {

			HttpSession session = request.getSession();
			userobj = (UserObject) session.getAttribute("userobject");
			if (userobj != null) {
				userId = userobj.getUserId();
				branch = userobj.getBranchId();
				bDate = userobj.getBusinessdate();
			} else {
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			// for check User session start
			ServletContext context = getServlet().getServletContext();
			String strFlag = "";
			if (sessionId != null) {
				strFlag = UserSessionCheck.checkSameUserSession(userobj,
						sessionId.toString(), "", request);
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
			String caseId = CommonFunction.checkNull(session.getAttribute("dealId"));
			logger.info("session.getAttribute(dealId).toString() ----"+CommonFunction.checkNull(session.getAttribute("dealId")));
			String stageId="UW";
			
			/*if (session.getAttribute("fundFlowDealId") != null
					&& (CommonFunction.checkNull(
							session.getAttribute("functionId"))
							.equalsIgnoreCase("3000261") || CommonFunction.checkNull(
									session.getAttribute("functionId"))
									.equalsIgnoreCase("3000283") || CommonFunction.checkNull(
											session.getAttribute("functionId"))
											.equalsIgnoreCase("3000280")) ) {
				stageId = "FFA";
				caseId = session.getAttribute("fundFlowDealId").toString();
			} else
			{
				caseId = CommonFunction.checkNull(session
						.getAttribute("financialDealId"));
				stageId = "FA";
			}*/

			String reportType = CommonFunction.checkNull(request
					.getParameter("reportName"));
			FinancialReportVo vo = new FinancialReportVo();
			FinancialReportDao dao = (FinancialReportDao) DaoImplInstanceFactory
					.getDaoImplInstance(FinancialReportDao.IDENTITY);
			FileUtilityDao utilityDao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);
			String refId = utilityDao.getApplicationReferenceNo(caseId);
			String custId = utilityDao.getCustomerID(caseId);
			String customerName = utilityDao
					.getCustomerNameByCustomerId(custId);
		/*	String stageId = "";
			if (CommonFunction.checkNull(session.getAttribute("functionId"))
					.equalsIgnoreCase("3000261") || CommonFunction.checkNull(
							session.getAttribute("functionId"))
							.equalsIgnoreCase("3000283") || CommonFunction.checkNull(
									session.getAttribute("functionId"))
									.equalsIgnoreCase("3000280"))
				stageId = "FFA";
			else
				stageId = "FA";*/
			boolean isCreditApproval;
			
			if(CommonFunction.checkNull(session.getAttribute("functionId")).equalsIgnoreCase("3000280"))
				isCreditApproval=true;
			else
				isCreditApproval=false;
			
			ArrayList linkList = dao.getReportLink(caseId, userId, reportType, refId,
					customerName, stageId, isCreditApproval );
			logger.info("linkList size()--" + linkList.size());
			request.setAttribute("linkList", linkList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("refresh");
	}

	public ActionForward downloadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In downloadFile()");
		String userId = null;
		String branch = null;
		String bDate = null;
		UserObject userobj = null;

		try {

			HttpSession session = request.getSession();
			userobj = (UserObject) session.getAttribute("userobject");
			if (userobj != null) {
				userId = userobj.getUserId();
				branch = userobj.getBranchId();
				bDate = userobj.getBusinessdate();
			} else {
				return mapping.findForward("sessionOut");
			}
			Object sessionId = session.getAttribute("sessionID");
			// for check User session start
			ServletContext context = getServlet().getServletContext();
			String strFlag = "";
			if (sessionId != null) {
				strFlag = UserSessionCheck.checkSameUserSession(userobj,
						sessionId.toString(), "", request);
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
			String caseId = "";
			if (session.getAttribute("fundFlowDealId") != null
					&& CommonFunction.checkNull(
							session.getAttribute("functionId"))
							.equalsIgnoreCase("3000261")) {
				caseId = session.getAttribute("fundFlowDealId").toString();
			} else
				caseId = CommonFunction.checkNull(session
						.getAttribute("financialDealId"));

			String reportType = CommonFunction.checkNull(request
					.getParameter("reportName"));

			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);

			/*String camTemplatePath = dao
					.getParameterMSTInfo(ConstantValue.FILE_PATH);*/
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			String readFile = camTemplatePath + reportType;
			logger.info("readFile-----------" + readFile);
			try {
				File file = new File(readFile);
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition",
						"attachment; filename=" + reportType + " ");
				response.setContentLength((int) file.length());

				FileInputStream fileInputStream = new FileInputStream(file);
				OutputStream responseOutputStream = response.getOutputStream();
				int bytes;
				while ((bytes = fileInputStream.read()) != -1) {
					responseOutputStream.write(bytes);
				}
				fileInputStream.close();
				responseOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
// GenerateSchemeReport start
	public ActionForward generateSchemeReport(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("Inside generateSchemeReport........>>");
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormat = resource.getString("lbl.dateInDao");
		String dbType = resource.getString("lbl.dbType");

		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		String userName = "";
		String userId = "";
		String businessDate = "";
		String companyName = "";
		if (userobj != null) {
			userName = userobj.getUserName();
			userId = userobj.getUserId();
			businessDate = userobj.getBusinessdate();
			companyName = userobj.getConpanyName();
		} else {
			logger.info("here in  generateSchemeReport method of ReportActionForOCR action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		String caseId = CommonFunction.checkNull(session.getAttribute("dealId"));
		/*String caseId = CommonFunction.checkNull(session.getAttribute("caseId"));*/
		/*if (caseId.equalsIgnoreCase(""))
			        caseId = CommonFunction.checkNull(session.getAttribute("financialDealId"));*/
		String loanAmount = CommonFunction.checkNull(session.getAttribute("loanAmount"));
		String product = CommonFunction.checkNull(session.getAttribute("product"));
		String scheme = CommonFunction.checkNull(session.getAttribute("scheme"));
		
		
		
		String reportName="Scheme_Wise_Eligibility_Amount";
		String reportPath = "/reports/";
		if (dbType.equalsIgnoreCase("MSSQL"))
			reportPath = reportPath + "MSSQLREPORTS/";
		else
			reportPath = reportPath + "MYSQLREPORTS/";

		Map<Object, Object> hashMap = new HashMap<Object, Object>();
		Connection connectDatabase = ConnectionDAO.getConnection();		

		String p_company_logo = getServlet().getServletContext().getRealPath("/")+ "reports/CommonLogo.jpg";

		//businessDate = formate(businessDate);
		//fromdate = CommonFunction.changeFormat(fromdate);
		//todate = CommonFunction.changeFormat(todate);
		String SUBREPORT_DIR = getServlet().getServletContext()
				.getRealPath("/") + "reports\\";
		if (dbType.equalsIgnoreCase("MSSQL")) {
			SUBREPORT_DIR = SUBREPORT_DIR + "MSSQLREPORTS\\";

		} else {
			SUBREPORT_DIR = SUBREPORT_DIR + "MYSQLREPORTS\\";
		}

//		hashMap.put("p_report_format", reporttype);
		hashMap.put("p_printed_by", userId);
		hashMap.put("p_printed_date", businessDate);
		hashMap.put("p_case_id", caseId);
		hashMap.put("p_company_name",companyName);
		hashMap.put("p_company_logo",p_company_logo);
		
	
		/*
		 * hashMap.put("fday", fday); hashMap.put("sday", sday);
		 * hashMap.put("tday", tday);
		 */

		logger.info("report Name  :  " + reportName + ".jasper");
		InputStream reportStream = getServlet().getServletConfig()
				.getServletContext()
				.getResourceAsStream(reportPath + reportName + ".jasper");
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
			//if (reporttype.equals("P"))
			methodForPDF(reportName, hashMap, connectDatabase, response,jasperPrint, request);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionDAO.closeConnection(connectDatabase, null);

		}

		return null;
	}
	
	public ActionForward generateLoanstructure(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("Inside generateLoanstructure........>>");
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormat = resource.getString("lbl.dateInDao");
		String dbType = resource.getString("lbl.dbType");

		HttpSession session = request.getSession();
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		String userName = "";
		String userId = "";
		String businessDate = "";
		String companyName = "";
		if (userobj != null) {
			userName = userobj.getUserName();
			userId = userobj.getUserId();
			businessDate = userobj.getBusinessdate();
			companyName = userobj.getConpanyName();
		} else {
			logger.info("here in  generateLoanstructure method of ReportActionForOCR action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		DynaValidatorForm FundFlowSummaryDynaValidatorForm = (DynaValidatorForm)form;//ajay
		FundFlowDownloadUploadVo vo=new FundFlowDownloadUploadVo();//ajay
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,FundFlowSummaryDynaValidatorForm);//ajay
		String caseId = CommonFunction.checkNull(session.getAttribute("caseId"));
		if (caseId.equalsIgnoreCase(""))
			        caseId = CommonFunction.checkNull(session.getAttribute("financialDealId"));
		String loanAmount = CommonFunction.checkNull(session.getAttribute("loanAmount"));
		String product = CommonFunction.checkNull(session.getAttribute("product"));
		String scheme = CommonFunction.checkNull(session.getAttribute("scheme"));
		String reportName = CommonFunction.checkNull(request.getParameter("reportName"));
		String reporttype=CommonFunction.checkNull(vo.getReporttype());//ajay
		//String reporttype =CommonFunction.checkNull(request.getParameter("reporttype"));
		logger.info("loanAmount==========="+loanAmount);
		logger.info("product============="+product);
		logger.info("scheme============="+scheme);
		logger.info("reportName==========="+reportName);
		logger.info("reporttype============"+reporttype);
		logger.info("caseId============"+caseId);
		
		if(reportName.equalsIgnoreCase("LOAN_STRUCTURE"))
			reportName="Loan_Structure_Report";
		else if(reportName.equalsIgnoreCase("Shareholing"))
			reportName="Shareholding_Report";
		String reportPath = "/reports/";
		if (dbType.equalsIgnoreCase("MSSQL"))
			reportPath = reportPath + "MSSQLREPORTS/";
		else
			reportPath = reportPath + "MYSQLREPORTS/";

		Map<Object, Object> hashMap = new HashMap<Object, Object>();
		Connection connectDatabase = ConnectionDAO.getConnection();		

		String p_company_logo = getServlet().getServletContext().getRealPath("/")+ "reports/CommonLogo.jpg";

		//businessDate = formate(businessDate);
		//fromdate = CommonFunction.changeFormat(fromdate);
		//todate = CommonFunction.changeFormat(todate);
		String SUBREPORT_DIR = getServlet().getServletContext()
				.getRealPath("/") + "reports\\";
		if (dbType.equalsIgnoreCase("MSSQL")) {
			SUBREPORT_DIR = SUBREPORT_DIR + "MSSQLREPORTS\\";

		} else {
			SUBREPORT_DIR = SUBREPORT_DIR + "MYSQLREPORTS\\";
		}

		hashMap.put("p_report_format", reporttype);
		hashMap.put("p_printed_by", userId);
		hashMap.put("p_printed_date", businessDate);
		hashMap.put("p_case_id", caseId);
		hashMap.put("p_company_name",companyName);
		hashMap.put("p_company_logo",p_company_logo);
		
	
		/*
		 * hashMap.put("fday", fday); hashMap.put("sday", sday);
		 * hashMap.put("tday", tday);
		 */

		logger.info("report Name  :  " + reportName + ".jasper");
		InputStream reportStream = getServlet().getServletConfig()
				.getServletContext()
				.getResourceAsStream(reportPath + reportName + ".jasper");
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(reportStream, hashMap,connectDatabase);
	
			if(reporttype.equals("P"))
				methodForPDF(reportName,hashMap,connectDatabase,response, jasperPrint,request);
			else if(reporttype.equals("E"))				
				methodForExcel(reportName,hashMap,connectDatabase,response, jasperPrint);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionDAO.closeConnection(connectDatabase, null);

		}

		return null;
	}
	
	
	// GenerateSchemeReport start
	public void methodForPDF(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint, HttpServletRequest request)throws Exception
	{		 
		JasperExportManager.exportReportToPdfFile(jasperPrint,request.getRealPath("/reports") + "/" +reportName+".pdf");
		File f=new File(request.getRealPath("/reports") + "/" +reportName+".pdf");
		FileInputStream fin = new FileInputStream(f);
		ServletOutputStream outStream = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename='"+reportName+"'.pdf");
		byte[] buffer = new byte[1024];
		int n = 0;
		while ((n = fin.read(buffer)) != -1) 
			outStream.write(buffer, 0, n);			
		outStream.flush();
		fin.close();
		outStream.close();
	}
	
	
	public void methodForExcel(String reportName,Map<Object,Object> hashMap,Connection connectDatabase,HttpServletResponse response,JasperPrint jasperPrint)throws Exception
	{
		String excelReportFileName=reportName+".xls";		
		JExcelApiExporter exporterXLS = new JExcelApiExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
		response.setContentType("application/vnd.ms-excel");
		exporterXLS.exportReport();
	}
}
