//start : Abhishek Mathur

package com.cp.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.cm.actions.UploadDocuments;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.masters.dao.MasterDAO;
import com.cp.dao.CreditApprovalDAO;
import com.cp.dao.DataAuthenticationDao;
import com.cp.dao.FileUplaodDao;
import com.cp.dao.FileUtilityDao;

import com.cp.dao.OcrDAO;
import com.cp.process.ConstantValue;
import com.cp.util.FileUtility;
import com.cp.vo.CaseMovementVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.FinancialAnalysisVo;
import com.cp.vo.QuesBankVo;
import com.cp.vo.QuestionStageVo;
import com.cp.vo.UnderwritingDocUploadVo;
import com.cp.vo.UploadTypeVo;

import org.apache.struts.validator.DynaValidatorForm;
import org.pentaho.di.trans.steps.update.Update;

import java.util.*;

public class UploadAuthorAction extends DispatchAction {
	private static final Logger logger = Logger
			.getLogger(UploadAuthorAction.class.getName());
	private String businessDate;
	
	public ActionForward makerSearch1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = getServlet().getServletContext();
		logger.info("In UploadAuthorBehindAction.........");
		HttpSession session = request.getSession();
		boolean flag=false;
		String userId="";
		String branchId="";
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		if(userobj==null)
		{
			logger.info("here in searchCustomer method of UploadAuthorBehindAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		}
		else
		{
			userId=userobj.getUserId();
			branchId=userobj.getBranchId();
		}
		Object sessionId = session.getAttribute("sessionID");
		//for check User session start
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

		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);

		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
		// OcrDAO service = new OcrDAOImpl();
		String dealId = request.getParameter("dealId");
		String customerName = request.getParameter("customerName");
		String docEntity = request.getParameter("docEntity");
		logger.info("BranchId In Action-------------------"+branchId);
		uwDocVo.setDealId(dealId);
		uwDocVo.setCustomerName(customerName);
		uwDocVo.setDocEntity(docEntity);
		uwDocVo.setBranchId(branchId);
		logger.info("BranchId In Action from Vo-------------------"+uwDocVo.getBranchId());

		ArrayList uploadedDocList = new ArrayList();

		/*
		 * uploadedDocList = service.AuthorSearch(dealId,uwDocVo.getDocEntity(),
		 * userobj.getUserId());
		 */
		uploadedDocList = service.AuthorSearch(uwDocVo, userobj.getUserId());
		request.setAttribute("uploadedDocList", uploadedDocList);

		if (uploadedDocList.size() > 0) {
			request.setAttribute("uploadedDocList", uploadedDocList);
		}
		if (uploadedDocList.size() <= 0) {
			request.setAttribute("msg", "E");
		}

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
				.getLookUpInstance(
						CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
						request);
		ArrayList list1 = cpm.getEntity("DUM");
		request.setAttribute("docEntity", list1);

		uwDocVo = null;
		service = null;
		strFlag = null;
		
		form.reset(mapping, request);

		return mapping.findForward("status");

	}
	
	public ActionForward getDocumentForOCR(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In getDocumentForOCR in Underwriting Upload Processing Action");
		boolean deleteStatus = false;

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in getDocumentForOCR method of uploadDocumentFile action the session is out----------------");
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

		if (!"".equalsIgnoreCase(strFlag)) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		QuesBankVo quesBankVo = new QuesBankVo();
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh

		String caseId = "";

		if (!CommonFunction.checkNull(session.getAttribute("caseId"))
				.equalsIgnoreCase("")) {
			caseId = (String) session.getAttribute("caseId");
		} else {
			caseId = (CommonFunction.checkNull(request.getParameter("dealId"))
					.trim());
		}

		uwDocVo.setDealId(caseId);
		logger.info("caseId::;;;;;;;" + caseId);

		session.setAttribute("caseId", uwDocVo.getDealId());

		String CustomerType = (CommonFunction.checkNull(request
				.getParameter("docEntity")).trim());
		uwDocVo.setDocEntity(CustomerType);
		logger.info("CustomerType::;;;;;;;" + uwDocVo.getDocEntity());

		String custRef = (CommonFunction.checkNull(request
				.getParameter("custRef")).trim());
		uwDocVo.setCustRef(custRef);
		logger.info("custRef::;;;;;;;" + uwDocVo.getCustRef());

		String fileName = (CommonFunction.checkNull(request
				.getParameter("fileName")).trim());
		uwDocVo.setFileName(fileName);
		logger.info("FileName::;;;;;;;" + uwDocVo.getFileName());

		String FunctionId = CommonFunction.checkNull(session
				.getAttribute("functionId"));

		ArrayList uploadedDocList = new ArrayList();
		uploadedDocList = service.DocumentType1(uwDocVo.getDealId(),
				CustomerType, custRef, FunctionId);
		request.setAttribute("uploadedDocList", uploadedDocList);

		request.setAttribute("List", uwDocVo.getDealId());
		request.setAttribute("List1", uwDocVo.getCustomerName());
		request.setAttribute("List2", uwDocVo.getDocEntity());
		request.setAttribute("List3", uwDocVo.getFileName());

		logger.info("list......................."
				+ request.getAttribute("List"));
		logger.info("list1......................."
				+ request.getAttribute("List1"));
		logger.info("list2......................."
				+ request.getAttribute("List2"));
		logger.info("list3......................."
				+ request.getAttribute("List3"));

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
				.getLookUpInstance(
						CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
						request);
		ArrayList list1 = cpm.getEntity("DUM");
		request.setAttribute("docEntity", list1);
		ArrayList uploadedDocList1 = new ArrayList();

		session.removeAttribute("viewContentVerification");
		/*if (CommonFunction.checkNull(session.getAttribute("functionId"))
				.equalsIgnoreCase("3000281"))
			session.setAttribute("viewInfo", "viewInfo");*/
		if (CommonFunction.checkNull(session.getAttribute("functionId"))
				.equalsIgnoreCase("3000281")
				|| CommonFunction.checkNull(session.getAttribute("functionId"))
						.equalsIgnoreCase("9000513")
				|| CommonFunction.checkNull(session.getAttribute("functionId"))
						.equalsIgnoreCase("3000280") || CommonFunction.checkNull(session.getAttribute("functionId"))
						.equalsIgnoreCase("3000283")) {
			ArrayList<UnderwritingDocUploadVo> viewList = service
					.DocumentViewer(uwDocVo.getDealId(), CustomerType, custRef,
							FunctionId);
			request.setAttribute("viewList", viewList);
			logger.info("viewList  " + viewList.size());

			return mapping.findForward("viewer");

		}

		uwDocVo = null;
		service = null;
		strFlag = null;

		form.reset(mapping, request);

		return mapping.findForward("viewer");
	}

	public ActionForward downloadUnderwritingFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In Download Methor..............");
		String downloadPath = null;
		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		logger.info("In downloadUnderwritingFile=======");
		if (userobj == null) {
			logger.info("here downloadUnderwritingFile method of uploadDocumentFile  action the session is out----------------");
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

		if (!"".equalsIgnoreCase(strFlag)) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		// sachin

		String flag1 = null;
		if (session.getAttribute("cmdocupload") != null) {
			flag1 = session.getAttribute("cmdocupload").toString();
		}
		// sachin

		String dealId = null;

		String fileName = request.getParameter("fileName");
		// sachin

		int index = fileName.lastIndexOf("_");
		dealId = fileName.substring(index + 1, fileName.length());
		logger.info("fileName=======" + fileName);

		if (index != -1)
			fileName = fileName.substring(0, index);

		logger.info("dealId===" + dealId);

		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh

		downloadPath = request.getParameter("uploadPath");
		logger.info("downloadPath======" + downloadPath);

		logger.info("File Name downloadPath: " + downloadPath);

		if (!downloadPath.equals(null)) {
			downloadPath = downloadPath + "\\" + fileName;
			logger.info("Download Path of File is: " + downloadPath);
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length());
			fileType = fileType.toLowerCase();
			logger.info("File Type: " + fileType);
			logger.info("File Name: " + fileName);

			if (fileType.trim().equalsIgnoreCase("xls")) {
				logger.info("File Type in xls: " + fileType);
				response.setContentType("application/vnd.ms-excel");
			} else if (fileType.trim().equalsIgnoreCase("xlsx")) {
				logger.info("File Type in xlsx: " + fileType);
				response.setContentType("application/vnd.ms-excel");
			} else if (fileType.trim().equalsIgnoreCase("pdf")) {
				logger.info("File Type in pdf: " + fileType);
				response.setContentType("application/pdf");
			} else if (fileType.trim().equalsIgnoreCase("doc")) {
				logger.info("File Type in doc: " + fileType);
				response.setContentType("application/msword");
			} else if (fileType.trim().equalsIgnoreCase("docx")) {
				logger.info("File Type in docx: " + fileType);
				response.setContentType("application/msword");
			} else if (fileType.trim().equalsIgnoreCase("jpg")) {
				logger.info("File Type in jpg: " + fileType);
				response.setContentType("image/jpeg");
			} else if (fileType.trim().equalsIgnoreCase("jpeg")) {
				logger.info("File Type in jpeg: " + fileType);
				response.setContentType("image/jpeg");
			} else if (fileType.trim().equalsIgnoreCase("zip")
					|| fileType.trim().equalsIgnoreCase("rar")) {
				logger.info("File Type in zip/rar: " + fileType);
				response.setContentType("application/x-zip-compressed");
			} else if (fileType.trim().equalsIgnoreCase("rtf")) {
				logger.info("File Type in rtf: " + fileType);
				response.setContentType("application/rtf");
			} else if (fileType.trim().equalsIgnoreCase("csv")) {
				logger.info("File Type in csv: " + fileType);
				response.setContentType("text/comma-separated-values");
			} else if (fileType.trim().equalsIgnoreCase("pptx")) {
				logger.info("File Type in pptx: " + fileType);
				response.setContentType("application/mspowerpoint");
			} else if (fileType.trim().equalsIgnoreCase("ppt")) {
				logger.info("File Type in ppt: " + fileType);
				response.setContentType("application/mspowerpoint");
			} else if (fileType.trim().equalsIgnoreCase("tif")) {
				logger.info("File Type in tif: " + fileType);
				response.setContentType("image/tif");
			} else {
				logger.info("File Type in default: " + fileType);
				response.setContentType("application/octet-stream");
			}
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ fileName);

			// executing download function
			DocUpload.downloadFile(request, response, downloadPath);

		}
		flag1 = null;
		fileName = null;

		strFlag = null;

		return null;
	}
	public ActionForward captureDataForAuthor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In captureDataForAuthor in Underwriting Upload Processing Action");
		boolean captureStatus = false;

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in captureDataForAuthor method of uploadDocumentFile action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		Object sessionId = session.getAttribute("sessionID");
		businessDate = userobj.getBusinessdate();
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj,
					sessionId.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);

		if (!"".equalsIgnoreCase(strFlag)) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		FileUtilityDao fileUtility = (FileUtilityDao) DaoImplInstanceFactory
				.getDaoImplInstance(FileUtilityDao.IDENTITY);
		String functionId = CommonFunction.checkNull(session
				.getAttribute("functionId"));
		String stageId = "";
		String noOFQuestion = "";

		if (functionId.equalsIgnoreCase("9000513")) {
			stageId = "DUA";
			noOFQuestion = fileUtility
					.getParameterMSTInfo(ConstantValue.NUMBER_OF_QUESTION_DUA);
		}
		/*if (functionId.equalsIgnoreCase("3000282")) {
			stageId = "CV";
			noOFQuestion = fileUtility
					.getParameterMSTInfo(ConstantValue.NUMBER_OF_QUESTION_CV);
		}
*/
		int noQuestion = 0;
		try {
			noQuestion = Integer.parseInt(noOFQuestion);
		} catch (Exception e) {
			noQuestion = 10;
		}

		String caseId = "";
		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();

		caseId = (CommonFunction.checkNull(request.getParameter("caseId"))
				.trim());

		/*
		 * if(caseId.equalsIgnoreCase("")) caseId =
		 * (CommonFunction.checkNull(session.getAttribute("dealId")).trim());
		 */
		uwDocVo.setCaseId(caseId);
		request.setAttribute("caseId", caseId);

		logger.info("caseId In captureDataForAuthor =============== " + caseId);

		String docType = (CommonFunction.checkNull(request
				.getParameter("docType")).trim());

		String docId = (CommonFunction.checkNull(request.getParameter("docId"))
				.trim());
		logger.info("DocId In Action==============" + docId);
		uwDocVo.setDocType(docType);

		logger.info("DocType===================" + uwDocVo.getDocType());
		request.setAttribute("docType", docType);

		uwDocVo.setLbxDocId(docId);
		uwDocVo.setDocId(docId);
		logger.info("DocId===================" + uwDocVo.getDocId());
		request.setAttribute("docId", docId);

		DataAuthenticationDao dao = (DataAuthenticationDao) DaoImplInstanceFactory
				.getDaoImplInstance(DataAuthenticationDao.identity);
		logger.info("Dao Class +" + dao.getClass());

		ArrayList<QuesBankVo> quesBankVosList = new ArrayList<QuesBankVo>();

		ArrayList<QuesBankVo> list = dao.getQuestionForAuthentication(caseId,
				stageId, docType, businessDate, docId);

		logger.info("DocId===================" + uwDocVo.getDocId());
		if (session.getAttribute("viewInfo") == null) {
			Random t = new Random();

			int listSize = list.size();
			logger.info("listSize===================" + listSize);
			noQuestion = noQuestion < list.size() ? noQuestion : listSize;
			logger.info("noQuestion===================" + noQuestion);
			Set set = new HashSet();
			int randomNumber = 0;
			if (listSize > noQuestion)
				for (int c = 1; c <= listSize; c++) {
					/* randomNumber = t.nextInt(listSize); */
					addUniqueRandomNumber(set, t, listSize, list,
							quesBankVosList);
					// quesBankVosList.add(list.get(randomNumber));
					if (set.size() == noQuestion)
						break;
				}
			else
				quesBankVosList.addAll(list);

			logger.info("quesBankVosList-----"+quesBankVosList);
			boolean status = false;
			boolean quesBankStatus = dao.checkQuestionBankStatus(caseId,
					stageId, docType, businessDate, docId);
			if (quesBankStatus)
				status = dao.insertDataInTable(caseId, docType, businessDate,
						userobj.getUserId(), docId, quesBankVosList);
			boolean statusForQuestionAnswerFlag = dao.statusForQuestionAnswer(
					caseId, stageId, docType, businessDate, docId);
			if (!statusForQuestionAnswerFlag) {
				logger.info("getQuestionAnswerList with answer");
				request.setAttribute("updateFlag", "Y");
				quesBankVosList = dao.getQuestionAnswerList(caseId, stageId,
						docType, businessDate, docId);
			}
		} else {
			logger.info("view mode of getQuestionAnswerList with answer");
			request.setAttribute("updateFlag", "Y");
			if (session.getAttribute("viewContentVerification") != null)
				stageId = "CV";
			else
				stageId = "DUA";
			quesBankVosList = dao.getQuestionAnswerList(caseId, stageId,
					docType, businessDate, docId);
		}
		logger.info("makerDate In captureDataForAuthor =============== "
				+ businessDate);
		request.setAttribute("businessDate", businessDate);

		/*
		 * String currentYearQuery =
		 * "select date_format(todat) from cr_case_dtl where case_id = '"
		 * +caseId+"'"; String currentYear =
		 * CommonFunction.checkNull(ConnectionDAO
		 * .singleReturn(currentYearQuery)); currentYear =
		 * currentYear.equalsIgnoreCase
		 * ("")?"":currentYear.substring(currentYear.
		 * lastIndexOf("-")+1,currentYear.length());
		 * logger.info("currentYearQuery--"+currentYearQuery);
		 * logger.info("current year--"+currentYear);
		 * request.setAttribute("year", currentYear);
		 */

		if (quesBankVosList.size() > 0) {
			request.setAttribute("paramList", quesBankVosList);

			logger.info("paramList size----------------->"
					+ quesBankVosList.size());
		}
		return mapping.findForward("open");

	}
	public ArrayList<QuesBankVo> addUniqueRandomNumber(Set set, Random t,
			int listSize, ArrayList<QuesBankVo> dataList,
			ArrayList<QuesBankVo> quesBankVosList) {
		int randomNumber = t.nextInt(listSize);
		boolean flag = false;
		if (set != null) {
			flag = set.add(randomNumber);
		}
		if (flag)
			quesBankVosList.add(dataList.get(randomNumber));
		else
			addUniqueRandomNumber(set, t, listSize, dataList, quesBankVosList);
		return quesBankVosList;
	}
	
	public ActionForward getDocumentForAuthor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In getDocumentForAuthor ");
		boolean deleteStatus = false;

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in getDocumentForOCR method  session is out----------------");
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

		if (!"".equalsIgnoreCase(strFlag)) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
		// OcrDAO service = new OcrDAOImpl();

		String caseId = (CommonFunction.checkNull(session
				.getAttribute("caseId")).trim());
		uwDocVo.setDealId(caseId);
		logger.info("caseId::;;;;;;;" + caseId);

		String CustomerType = (CommonFunction.checkNull(request
				.getParameter("docEntity")).trim());
		uwDocVo.setDocEntity(CustomerType);
		logger.info("CustomerType::;;;;;;;" + uwDocVo.getDocEntity());

		String custRef = (CommonFunction.checkNull(request
				.getParameter("custRef")).trim());
		uwDocVo.setCustRef(custRef);
		logger.info("custRef::;;;;;;;" + uwDocVo.getCustRef());

		ArrayList uploadedDocList = new ArrayList();

		uploadedDocList = service.DocumentType(caseId, CustomerType, custRef);
		request.setAttribute("uploadedDocList", uploadedDocList);

		request.setAttribute("List", uwDocVo.getDealId());
		request.setAttribute("List1", uwDocVo.getCustomerName());
		request.setAttribute("List2", uwDocVo.getDocEntity());

		logger.info("list......................."
				+ request.getAttribute("List"));
		logger.info("list1......................."
				+ request.getAttribute("List1"));
		logger.info("list2......................."
				+ request.getAttribute("List2"));

		CreditApprovalDAO dao = (CreditApprovalDAO) DaoImplInstanceFactory
				.getDaoImplInstance(CreditApprovalDAO.IDENTITY);
		ArrayList<CodeDescVo> list = dao.getCaseMovementList(caseId);
		request.setAttribute("stagemovement", list);
		request.setAttribute("caseId", caseId);

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
				.getLookUpInstance(
						CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
						request);
		ArrayList list1 = cpm.getEntity("DUM");
		request.setAttribute("docEntity", list1);
		boolean flag1 = service.checkAuthorContentVerificationStatus(caseId,
				"DUA");
		if (!flag1)
			request.setAttribute("back", "back");

		uwDocVo = null;
		service = null;
		strFlag = null;
		form.reset(mapping, request);
		return mapping.findForward("success");
	}
	public ActionForward openDocumentUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ServletContext context = getServlet().getServletContext();
		logger.info("In openDocumentUpload.........");
		HttpSession session = request.getSession();
		boolean flag = false;
		String userId = "";
		String branchId = "";

		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("here in openDocumentUpload method of UploadAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		} else {
			userId = userobj.getUserId();
			branchId = userobj.getBranchId();

		}
		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
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

		FinancialAnalysisVo vo = new FinancialAnalysisVo();
		DynaValidatorForm UploadForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo, UploadForm);

		String filePath = getServlet().getServletContext().getRealPath("/")
				+ "upload";

		// create the upload folder if not exists
		File folder = new File(filePath);
		if (!folder.exists()) {
			folder.mkdir();
		}

		String fileName = getServlet().getServletContext() + "uplaod";// file.getFileName();

		if (!("").equals(fileName)) {
			System.out.println("Server path:" + filePath);
			File newFile = new File(filePath, fileName);

			if (!newFile.exists()) {
				FileOutputStream fos = new FileOutputStream(newFile);
				// fos.write(file.getFileData());
				fos.flush();
				fos.close();
			}

			request.setAttribute("uploadedFilePath", newFile.getAbsoluteFile());
			request.setAttribute("uploadedFileName", newFile.getName());
		}

		ArrayList uploadedDocList = new ArrayList();
		request.setAttribute("uploadedDocList", uploadedDocList);

		return mapping.findForward("success");
	}
	public ActionForward saveClosureAuthor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("In saveClosureAuthor");
		boolean deleteStatus = false;

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		String bDate = "";
		String companyId = "";
		String userId = "";

		if (userobj == null) {
			logger.info("in saveClosureAuthor method session is out----------------");
			return mapping.findForward("sessionOut");
		} else {
			bDate = userobj.getBusinessdate();
			companyId = "" + userobj.getCompanyId();
			userId = userobj.getUserId();
		}

		if (userobj == null) {

			logger.info("in makerSearch method  session is out----------------");
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

		if (!"".equalsIgnoreCase(strFlag)) {

			if (strFlag.equalsIgnoreCase("sameUserSession")) {

				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {

				context.setAttribute("msg", "B");
			}

			return mapping.findForward("logout");

		}

		String flag1 = null;

		
		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		DynaValidatorForm UploadForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(uwDocVo, UploadForm);
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);

		logger.info("Implementation class: " + service.getClass());

		String caseId = CommonFunction
				.checkNull(session.getAttribute("caseId"));
		logger.info("caseId=========" + caseId);
		uwDocVo.setDealId(caseId);
		String customerName = request.getParameter("customerName");
		logger.info("customerName......." + customerName);
		String docEntity = request.getParameter("docEntity");
		logger.info("docEntity......." + docEntity);
		String decision = CommonFunction.checkNull(request
				.getParameter("decision"));
		logger.info("decision......." + decision);
		uwDocVo.setDealId(caseId);
		uwDocVo.setCustomerName(customerName);
		uwDocVo.setDocEntity(docEntity);
		uwDocVo.setdecision(decision);
		String CustomerType = "";

		ArrayList uploadedDocList = new ArrayList();
		boolean status = false;
		boolean status1 = false;
		String queValue = "";
		CaseMovementVo moveVo = null;
		String checkStageM = null;

		boolean QueryInitiationStatus = service.getStatusDetail(caseId);//hina



		if (decision.equalsIgnoreCase("A")) {
			queValue = service.getQueAnsResultMatch(caseId, "DUA");
			logger.info(" getQueAnsResultMatch --" + queValue);
		}

		if (decision.equalsIgnoreCase("A") && queValue.equalsIgnoreCase("") && !QueryInitiationStatus) {
			 checkStageM = CommonFunction.stageMovement(companyId, "DUA",
					"A", caseId, "DUA", bDate, userId);
			logger.info("checkStageM : " + checkStageM);
			if (CommonFunction.checkNull(checkStageM).equalsIgnoreCase("S")) {
				uploadedDocList = service.AuthorSearch(uwDocVo, userId);
				request.setAttribute("uploadedDocList", uploadedDocList);
				status = service.approvedDoc(uwDocVo);
			} else {
				request.setAttribute("checkStageM", checkStageM);
			}
		}
		 else if(decision.equalsIgnoreCase("P"))
		 {
			 moveVo=new CaseMovementVo();
				moveVo.setLbxcaseNo(caseId);
				moveVo.setSendBackStage(uwDocVo.getStage());
				moveVo.setStage("DUA");
				moveVo.setRemarks(uwDocVo.getComments());
				moveVo.setOtherRemarks("");
				moveVo.setRpStageFlag("Y");
				moveVo.setUserId(userId);
				moveVo.setSendBackDate(bDate);
				checkStageM=CommonFunction.caseSandBack(moveVo);
				logger.info("Sand BAck To Stage : "+checkStageM); 
				if(!checkStageM.equalsIgnoreCase("S") )
				    request.setAttribute("checkStageM", checkStageM);
		 }
		
		
		
		if (!decision.equalsIgnoreCase("A"))
			status = service.approvedDoc(uwDocVo);

		logger.info("Descision In asdasd .........." + uwDocVo.getDecision());

		if (status && !queValue.equalsIgnoreCase("")
				&& decision.equalsIgnoreCase("A")) {
			request.setAttribute("msg", "QAR");
			request.setAttribute("ansNotMatch", "Answer does not Match for "
					+ queValue + " file");
		} else {
			if (!status) {
				logger.info("Stauts............." + status);
				request.setAttribute("msg", "F");
				request.setAttribute("ansNotMatch",
						"Answer does not Match for " + queValue + " file");
			}
			if (status) {
				logger.info("Stauts............." + status);
				request.setAttribute("msg", "G");
			}
			if (status && uwDocVo.getDecision().equalsIgnoreCase("P")) {
				logger.info("In desicsion is send-Back.............");
				request.setAttribute("msg", "L");

			}

			if (status && uwDocVo.getDecision().equalsIgnoreCase("X")) {
				logger.info("In desicsion is Reject.............");
				request.setAttribute("msg", "X");

			}
			if (QueryInitiationStatus && decision.equalsIgnoreCase("A") ) {
				logger.info("QueryInitiationStatus............"+QueryInitiationStatus);
				 request.setAttribute("msg", "QueryInitiationPending");	

			}
		}

		MasterDAO entity = (MasterDAO) DaoImplInstanceFactory
				.getDaoImplInstance(MasterDAO.IDENTITY);
		ArrayList list1 = entity.getEntity("DUM");
		request.setAttribute("docEntity", list1);

		/* session.removeAttribute("caseId"); */
		uwDocVo = null;
		service = null;
		strFlag = null;
		flag1 = null;
		caseId = null;
		// form.reset(mapping, request);

		logger.info("CaseId2 After Remove==========" + caseId);
		return mapping.findForward("status");

	}
	public ActionForward saveFinancialBalanceSheetDetails(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ResourceBundle resource = ResourceBundle
				.getBundle("com.yourcompany.struts.ApplicationResources");
		logger.info("In captureDataForAuthor in Underwriting Upload Processing Action");
		boolean captureStatus = false;

		// boolean flag =false;
		// HttpSession session = request.getSession();
		// UserObject userobj=(UserObject)session.getAttribute("userobject");
		String userId = "";
		String bgDate = "";
		String idss = "";
		String resultstr = "E";

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in captureDataForAuthor method of uploadDocumentFile action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		Object sessionId = session.getAttribute("sessionID");
		businessDate = userobj.getBusinessdate();
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj,
					sessionId.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);

		if (!"".equalsIgnoreCase(strFlag)) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		String functionId = CommonFunction.checkNull(session
				.getAttribute("functionId"));
		String stageId = "";
		if (functionId.equalsIgnoreCase("9000513"))
			stageId = "DUA";
		/*if (functionId.equalsIgnoreCase("3000282"))
			stageId = "CV";*/

		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		String caseId = (CommonFunction.checkNull(request
				.getParameter("caseId")));
		uwDocVo.setDealId(caseId);
		logger.info("caseId In saveFinancialBalanceSheetDetails  ============== "
				+ caseId);
		logger.info("StageId In saveFinancialBalanceSheetDetails ============== "
				+ stageId);

		String docId = (CommonFunction.checkNull(request.getParameter("docId"))
				.trim());
		logger.info("DocId In saveFinancialBalanceSheetDetails=============="
				+ docId);
		String docType = (CommonFunction.checkNull(request
				.getParameter("docType")).trim());
		logger.info("DocId In saveFinancialBalanceSheetDetails=============="
				+ docType);

		FinancialAnalysisVo vo = new FinancialAnalysisVo();

		DynaValidatorForm FinancialAnalysisDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FinancialAnalysisDynaValidatorForm);

		DataAuthenticationDao dao = (DataAuthenticationDao) DaoImplInstanceFactory
				.getDaoImplInstance(DataAuthenticationDao.identity);
		// FinancialDAO
		// dao=(FinancialDAO)DaoImplInstanceFactory.getDaoImplInstance(FinancialDAO.IDENTITY);
		logger.info("vo.updateFlag() ----" + vo.getUpdateFlag());
		String updateFlag = request.getParameter("updateFlag");
		logger.info("request.getParameter(updateFlag)-------"
				+ request.getParameter("updateFlag"));
		vo.setUpdateFlag(updateFlag);
		logger.info("Implementation class: " + dao.getClass());
		String source = CommonFunction
				.checkNull(request.getParameter("source"));
		String status = null;
		Date date;
		DateFormat formatter;
		formatter = new SimpleDateFormat("dd-MM-yyyy");
		String businessDate = "";

		if (userobj != null) {
			businessDate = userobj.getBusinessdate();
		}
		logger.info("businessDate================in save = " + businessDate);

		/*
		 * date = (Date) formatter.parse(businessDate); SimpleDateFormat
		 * simpleDateformat = new SimpleDateFormat("yyyy"); int year =
		 * Integer.parseInt(simpleDateformat.format(date));
		 */
		int year = 0;
		if (!CommonFunction.checkNull(request.getParameter("yr01"))
				.equalsIgnoreCase("")) {
			year = Integer.parseInt(request.getParameter("yr01"));
		}
		logger.info("Year=======================" + year);
		int previousYear = 0;
		int j = dao.getYearForFinancialAnalysis();
		String[] analysisYear = new String[6];
		/*
		 * analysisYear[1]=year-(j-1)+0+""; analysisYear[2]=year-(j-1)+1+"";
		 * analysisYear[3]=year-(j-1)+2+""; analysisYear[4]=year-(j-1)+3+"";
		 * analysisYear[5]=year-(j-1)+4+"";
		 */
		/*
		 * for(int i=1; i<=j; i++) { previousYear = year - i; String setYear =
		 * "year"+(j-i); if(i != j){ request.setAttribute(setYear,
		 * previousYear); }else{ request.setAttribute("year"+j, year); } }
		 * for(int i=j+1; i<=5; i++) { year = year + 1;
		 * request.setAttribute("year"+i, year); }
		 */

		vo.setAnalysisYear(analysisYear);
		String dealId = null;
		if (session.getAttribute("financialDealId") == null
				|| session.getAttribute("financialDealId").toString() != "")
			dealId = (String) session.getAttribute("financialDealId");

		// code for upload functionality start here
		/*
		 * if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("U")) {
		 * status
		 * =uploadFile(request,response,(FormFile)FinancialAnalysisDynaValidatorForm
		 * .get("docFile"),userId,businessDate,dealId,"B");
		 * logger.info("Uploaded and processing status  :  "+status);
		 * if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("")) {
		 * ArrayList data=dao.getUploadedData(dealId,userId,"B");
		 * vo.setpCode((String[])data.get(0));
		 * vo.setYear1((String[])data.get(1));
		 * vo.setYear2((String[])data.get(2));
		 * vo.setYear3((String[])data.get(3));
		 * vo.setYear4((String[])data.get(4));
		 * vo.setYear5((String[])data.get(5));
		 * 
		 * } else { String uploadError="";
		 * if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("MXE"))
		 * uploadError=resource.getString("lbl.maxCount");//MXE else
		 * if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("MNE"))
		 * uploadError=resource.getString("lbl.smsk");//MXE else
		 * if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("FNE"))
		 * uploadError=resource.getString("lbl.smks");//FNE else
		 * if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("NUE"))
		 * uploadError=resource.getString("lbl.numericError");//NUE else
		 * if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("CLE"))
		 * uploadError=resource.getString("lbl.formatInvalid");//CLE else
		 * if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("PRE"))
		 * uploadError=resource.getString("lbl.errorHeader");//PRE else
		 * if(CommonFunction.checkNull(status).trim().equalsIgnoreCase("UPE"))
		 * uploadError=resource.getString("msg.notsuccess");//UPE else {
		 * if(CommonFunction.checkNull(status).trim().length()>4)
		 * uploadError=status.substring(4); }
		 * request.setAttribute("uploadError",uploadError); ArrayList paramList
		 * = dao.getParamDetailDetails("B"); if(paramList.size()>0)
		 * request.setAttribute("paramList", paramList); return
		 * mapping.getInputForward(); } }
		 */

		vo.setLbxDealNo(dealId);
		vo.setUserId(userId);
		vo.setBusinessDate(bgDate);
		String paramName[] = request.getParameterValues("pName");
		vo.setpName(paramName);
		String update = "";
		logger.info("In FinancialBalanceSheetAction................... ");
		if (request.getParameter("update") != null) {
			update = request.getParameter("update");
			vo.setUpdate(request.getParameter("update"));
		}

		logger.info("Year1=======================" + year);

		String ids = dao.saveBalanceSheet(vo, caseId, stageId, docType, docId,
				businessDate, userobj.getUserId(), year);
		resultstr = "S";
		ArrayList paramMinusList = dao.getParamMinusDetails("B");
		CommonDealVo cvo = new CommonDealVo();
		cvo.setLbxDealNo(vo.getLbxDealNo());
		cvo.setSourceType("B");
		if (ids != null && !ids.equalsIgnoreCase("")) {
			ArrayList paramList = dao.getParamDetailDetails("B");
			String pCode[] = vo.getpCode();
			/*
			 * String year1[] = vo.getYear1(); String year2[] = vo.getYear2();
			 * String year3[] = vo.getYear3(); String year4[] = vo.getYear4();
			 * String year5[] = vo.getYear5();
			 */
			String financialId[] = vo.getFinancialIds();
			int len = vo.getFinancialIds().length;
			ArrayList paramCodeValues = new ArrayList();
			String[] idForParam = ids.split(",");
			logger.info("idsssssssss in action*********FOR PARAM CODE******* "
					+ ids);
			for (int i = 0, k = 0; i < pCode.length; i++) {
				String id = "";
				k = 0;
				FinancialAnalysisVo vo1 = new FinancialAnalysisVo();
				vo1 = dao.getParamCode(pCode[i], vo1);
				/*
				 * vo1.setParameCode(StringEscapeUtils.escapeHtml(CommonFunction.
				 * checkNull(pCode[i])).trim());
				 * vo1.setFirstYear(StringEscapeUtils
				 * .escapeHtml(CommonFunction.checkNull(year1[i])).trim());
				 * vo1.setSecondYear
				 * (StringEscapeUtils.escapeHtml(CommonFunction.
				 * checkNull(year2[i])).trim());
				 * vo1.setThirdYear(StringEscapeUtils
				 * .escapeHtml(CommonFunction.checkNull(year3[i])).trim());
				 * vo1.setFourthYear
				 * (StringEscapeUtils.escapeHtml(CommonFunction.
				 * checkNull(year4[i])).trim());
				 * vo1.setFifthYear(StringEscapeUtils
				 * .escapeHtml(CommonFunction.checkNull(year5[i])).trim());
				 */
				vo1.setParamName(StringEscapeUtils.escapeHtml(
						CommonFunction.checkNull(paramName[i])).trim());
				vo1.setFinancialId(StringEscapeUtils.escapeHtml(
						CommonFunction.checkNull(financialId[i])).trim());
				if (vo.getFinancialIds() != null
						&& vo.getFinancialIds().length > 0) {
					if (financialId[i].length() > 0) {
						if (len > 0) {
							vo1.setFinancialId(financialId[i]);
							len--;
						} else {
							id = idForParam[k++];
							for (int s = 1; s < 5; s++) {
								id = id + "," + idForParam[k++];
							}
							vo1.setFinancialId(id);
						}
					}
				} else {
					logger.info("idForParam[k++]" + idForParam[k++]);
					id = idForParam[k++];
					for (int s = 1; s < 5; s++) {
						logger.info("id-----in if---->" + id);
						id = id + "," + idForParam[k++];
					}
					logger.info("id-----out--else-->" + id);
					vo1.setFinancialId(id);

				}
				paramCodeValues.add(vo1);
			}
			session.setAttribute("balanceSheetAllDetailsByDeal",
					paramCodeValues);
			resultstr = "S";
		}
		request.setAttribute("sms", resultstr);
		return mapping.getInputForward();
	}

	/*
	 * String functionId =
	 * CommonFunction.checkNull(session.getAttribute("functionId")); String
	 * stageId = ""; if(functionId.equalsIgnoreCase("3000279")) stageId = "DUA";
	 * if(functionId.equalsIgnoreCase("3000282")) stageId="CV";
	 * 
	 * FileUtilityDao fileUtility =
	 * (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance
	 * (FileUtilityDao.IDENTITY);
	 * 
	 * String noOFQuestion =
	 * fileUtility.getParameterMSTInfo(ConstantValue.NUMBER_OF_QUESTION); int
	 * noQuestion = 0; try { noQuestion = Integer.parseInt(noOFQuestion); }
	 * catch (Exception e) { noQuestion=10; }
	 * 
	 * UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo(); String
	 * caseId=(CommonFunction.checkNull(session.getAttribute("caseId")).trim());
	 * uwDocVo.setDealId(caseId);
	 * logger.info("caseId In captureDataForAuthor =============== "+caseId);
	 * logger.info("stageId In Method============"+stageId); String
	 * docType=(CommonFunction
	 * .checkNull(request.getParameter("docType")).trim()); String
	 * docId=(CommonFunction.checkNull(request.getParameter("docId")).trim());
	 * logger.info("DocId=============="+docId); uwDocVo.setDocType(docType);
	 * logger.info("DocType==================="+uwDocVo.getDocType());
	 * request.setAttribute("docType", docType);
	 * 
	 * 
	 * uwDocVo.setLbxDocId(docId);
	 * logger.info("DocId==================="+uwDocVo.getDocId());
	 * request.setAttribute("docId", docId);
	 * 
	 * 
	 * DataAuthenticationDao dao =
	 * (DataAuthenticationDao)DaoImplInstanceFactory.
	 * getDaoImplInstance(DataAuthenticationDao.identity);
	 * logger.info("Dao Class +"+ dao.getClass());
	 * 
	 * 
	 * logger.info("caseId=====================In method = "+caseId);
	 * 
	 * ArrayList<QuesBankVo> list = dao.getQuestionForAuthentication(caseId,
	 * stageId, docType, businessDate); ArrayList<QuesBankVo> quesBankVosList =
	 * new ArrayList<QuesBankVo>();
	 * 
	 * 
	 * //Random t = new Random(); int listSize = list.size();
	 * quesBankVosList.add(list.get((listSize))); noQuestion
	 * =noQuestion<list.size()?noQuestion:listSize; for(int
	 * c=1;c<=noQuestion;c++) {
	 * quesBankVosList.add(list.get(t.nextInt(listSize))); }
	 * 
	 * boolean status = dao.saveDataForAuthor(caseId , docType ,
	 * businessDate,userobj.getUserId(),docId,quesBankVosList );
	 * 
	 * 
	 * Date date; DateFormat formatter ; formatter = new
	 * SimpleDateFormat("dd-MM-yyyy");
	 * 
	 * date =(Date)formatter.parse(businessDate);
	 * 
	 * SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy"); int year
	 * =Integer.parseInt(simpleDateformat.format(date)); FinancialDAO
	 * financialDao
	 * =(FinancialDAO)DaoImplInstanceFactory.getDaoImplInstance(FinancialDAO
	 * .IDENTITY); //Code by sanjog for set previous & forwarded Year int
	 * previousYear = 0; int j = financialDao.getYearForFinancialAnalysis();
	 * for(int i=1; i<=j; i++){ previousYear = year - i; String setYear =
	 * "year"+(j-i); if(i != j){ request.setAttribute(setYear, previousYear);
	 * }else{ request.setAttribute("year"+j, year); }
	 * logger.info("============================"
	 * +request.getAttribute("year"+i)); } for(int i=j+1; i<=5; i++){ year =
	 * year + 1; request.setAttribute("year"+i, year);
	 * logger.info("============================"
	 * +request.getAttribute("year"+i)); }
	 * 
	 * //Code by sanjog for set previous & forwarded Year
	 * 
	 * if(quesBankVosList.size()>0) { request.setAttribute("paramList",
	 * quesBankVosList);
	 * logger.info("paramList size----------------->"+quesBankVosList.size()); }
	 * return mapping.findForward("open");
	 * 
	 * }
	 */

	String uploadFile(HttpServletRequest request, HttpServletResponse response,
			FormFile myFile, String makerName, String makerDate, String dealId,
			String source) {
		logger.info("Inside uploadFile");
		String result = "";
		boolean uploadStatus = false;
		try {
			uploadStatus = UploadDocuments.docUpload(request, myFile);
			if (uploadStatus)
				result = UploadDocuments.readExcelforFinancialUpload(request,
						response, myFile.toString(), makerName, makerDate,
						dealId, source);
			else
				result = "UPE";
		} catch (Exception e) {
			result = "PRE";
			e.printStackTrace();
		}
		return result;

	}
	/*

	
	
	
	// Method For Upload Document Viewer

	public ActionForward viewerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ServletContext context = getServlet().getServletContext();
		logger.info("In UploadViewerBehindAction.........");
		HttpSession session = request.getSession();
		boolean flag = false;
		String userId = "";
		String branchId = "";
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		if (userobj == null) {
			logger.info("here in searchCustomer method of UploadViewerBehindAction  action the session is out----------------");
			return mapping.findForward("sessionOut");
		} else {
			userId = userobj.getUserId();
			branchId = userobj.getBranchId();
		}

		Object sessionId = session.getAttribute("sessionID");
		// for check User session start
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

		String flag1 = null;

		if (session.getAttribute("cmdocupload") != null) {

			flag1 = session.getAttribute("cmdocupload").toString();
		}

		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);

		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
		// OcrDAO service = new OcrDAOImpl();
		String dealId = request.getParameter("dealId");
		String customerName = request.getParameter("customerName");
		String docEntity = request.getParameter("docEntity");
		String FunctionId = CommonFunction.checkNull(session
				.getAttribute("functionId"));
		uwDocVo.setDealId(dealId);
		uwDocVo.setCustomerName(customerName);
		uwDocVo.setDocEntity(docEntity);

		ArrayList uploadedDocList = new ArrayList();

		uploadedDocList = service.viewerSearch(uwDocVo);
		request.setAttribute("uploadedDocList", uploadedDocList);

		uploadedDocList = service.ViewerSearch(dealId, uwDocVo.getDocEntity(),
				userId, FunctionId,branchId);

		request.setAttribute("uploadedDocList", uploadedDocList);

		if (uploadedDocList.size() <= 0) {
			request.setAttribute("msg", "E");
		}

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
				.getLookUpInstance(
						CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
						request);
		ArrayList list1 = cpm.getEntity("DUM");
		request.setAttribute("docEntity", list1);

		uwDocVo = null;
		service = null;
		strFlag = null;
		flag1 = null;
		form.reset(mapping, request);

		return mapping.findForward("status");

	}

	// Viewer Screen For Sub jsp

	public ActionForward ViewDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In ViewDoc in Underwriting Upload Processing Action");
		boolean deleteStatus = false;

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in ViewDoc method of uploadDocumentFile action the session is out----------------");
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

		if (!"".equalsIgnoreCase(strFlag)) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}

		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
		// OcrDAO service = new OcrDAOImpl();

		String dealId = (CommonFunction.checkNull(request
				.getParameter("dealId")).trim());
		uwDocVo.setDealId(dealId);
		logger.info("dealIdid::;;;;;;;" + uwDocVo.getDealId());
		String CustomerType = (CommonFunction.checkNull(request
				.getParameter("docEntity")).trim());
		uwDocVo.setDocEntity(CustomerType);
		logger.info("CustomerType::;;;;;;;" + uwDocVo.getDocEntity());
		String custRef = (CommonFunction.checkNull(request
				.getParameter("custRef")).trim());
		uwDocVo.setCustRef(custRef);
		logger.info("custRef::;;;;;;;" + uwDocVo.getCustRef());
		String CustomerName = (CommonFunction.checkNull(request
				.getParameter("cust")).trim());
		uwDocVo.setCustomerName(CustomerName);
		logger.info("CustomerName::;;;;;;;" + uwDocVo.getCustomerName());

		String FunctionId = CommonFunction.checkNull(session
				.getAttribute("functionId"));
		ArrayList uploadedDocList = new ArrayList();

		logger.info("In Action................................................");

		uploadedDocList = service.DocumentViewer(dealId, CustomerType, custRef,
				FunctionId);
		logger.info("dealId=====" + dealId);
		logger.info("CustomerType=====" + CustomerType);
		request.setAttribute("uploadedDocList", uploadedDocList);
		logger.info("uploadedDocList====" + uploadedDocList);

		request.setAttribute("List", uwDocVo.getDealId());
		request.setAttribute("List1", uwDocVo.getCustomerName());
		request.setAttribute("List2", uwDocVo.getDocEntity());

		logger.info("list......................."
				+ request.getAttribute("List"));
		logger.info("list1......................."
				+ request.getAttribute("List1"));
		logger.info("list2......................."
				+ request.getAttribute("List2"));

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
				.getLookUpInstance(
						CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
						request);
		ArrayList list1 = cpm.getEntity();
		request.setAttribute("docEntity", list1);
		ArrayList<UnderwritingDocUploadVo> uploadDocViewer = service
				.getViewAllDocumentList(uwDocVo);
		request.setAttribute("uploadDocViewer", uploadDocViewer);

		uwDocVo = null;
		service = null;
		strFlag = null;

		form.reset(mapping, request);
		return mapping.findForward("viewScreen");
	}

	 Method For Author 

	

	 Method For Capture Data For Author 

	

	 Method For Save Data From Author 

	

	public ActionForward dataValidationProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ResourceBundle resource = ResourceBundle
				.getBundle("com.yourcompany.struts.ApplicationResources");
		logger.info("In dataValidationProcess Action...................................................");
		boolean captureStatus = false;

		String userId = "";
		String bgDate = "";
		String idss = "";
		String resultstr = "E";

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in captureDataForAuthor method of uploadDocumentFile action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		Object sessionId = session.getAttribute("sessionID");
		businessDate = userobj.getBusinessdate();
		// for check User session start
		ServletContext context = getServlet().getServletContext();
		String strFlag = "";
		if (sessionId != null) {
			strFlag = UserSessionCheck.checkSameUserSession(userobj,
					sessionId.toString(), "", request);
		}

		logger.info("strFlag .............. " + strFlag);

		if (!"".equalsIgnoreCase(strFlag)) {
			if (strFlag.equalsIgnoreCase("sameUserSession")) {
				context.removeAttribute("msg");
				context.removeAttribute("msg1");
			} else if (strFlag.equalsIgnoreCase("BODCheck")) {
				context.setAttribute("msg", "B");
			}
			return mapping.findForward("logout");
		}
		DataAuthenticationDao service = (DataAuthenticationDao) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		String functionId = CommonFunction.checkNull(session
				.getAttribute("functionId"));
		String stageId = "";
		if (functionId.equalsIgnoreCase("3000279")) {
			stageId = "DUA";
		}
		if (functionId.equalsIgnoreCase("3000282")) {
			stageId = "CV";
		}

		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		String caseId = (CommonFunction.checkNull(session
				.getAttribute("caseId")).trim());
		uwDocVo.setDealId(caseId);
		logger.info("caseId In 	dataValidationProcess  ============== "
				+ caseId);
		logger.info("StageId In dataValidationProcess ============== "
				+ stageId);

		String docId = (CommonFunction.checkNull(request.getParameter("docId"))
				.trim());
		logger.info("DocId In dataValidationProcesss==============" + docId);
		String docType = (CommonFunction.checkNull(request
				.getParameter("docType")).trim());
		logger.info("DocType In dataValidationProcess==============" + docType);

		FinancialAnalysisVo vo = new FinancialAnalysisVo();
		DynaValidatorForm FinancialAnalysisDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(vo,
				FinancialAnalysisDynaValidatorForm);

		DataAuthenticationDao dao = (DataAuthenticationDao) DaoImplInstanceFactory
				.getDaoImplInstance(DataAuthenticationDao.identity);

		logger.info("vo.updateFlag() ----" + vo.getUpdateFlag());
		String updateFlag = request.getParameter("updateFlag");
		logger.info("request.getParameter(updateFlag)-------"
				+ request.getParameter("updateFlag"));
		vo.setUpdateFlag(updateFlag);
		logger.info("Implementation class: " + dao.getClass());

		String source = CommonFunction
				.checkNull(request.getParameter("source"));
		String status = null;

		Date date;
		DateFormat formatter;

		formatter = new SimpleDateFormat("dd-MM-yyyy");
		String businessDate = "";

		if (userobj != null) {
			businessDate = userobj.getBusinessdate();
		}

		logger.info("businessDate================in save = " + businessDate);

		date = (Date) formatter.parse(businessDate);
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(simpleDateformat.format(date));

		String dealId = null;
		if (session.getAttribute("financialDealId") == null
				|| session.getAttribute("financialDealId").toString() != "")
			dealId = (String) session.getAttribute("financialDealId");

		vo.setLbxDealNo(dealId);
		vo.setUserId(userId);
		vo.setBusinessDate(bgDate);
		String paramName[] = request.getParameterValues("pName");
		vo.setpName(paramName);
		String update = "";
		logger.info("In FinancialBalanceSheetAction................... ");
		if (request.getParameter("update") != null) {
			update = request.getParameter("update");
			vo.setUpdate(request.getParameter("update"));
		}

		ArrayList dataValidationList = new ArrayList();

		logger.info("In Action................................................");

		dataValidationList = service.dataValidationProcess(caseId,
				businessDate, docId);

		return null;
	}

	public ArrayList<QuesBankVo> addUniqueRandomNumber(Set set, Random t,
			int listSize, ArrayList<QuesBankVo> dataList,
			ArrayList<QuesBankVo> quesBankVosList) {
		int randomNumber = t.nextInt(listSize);
		boolean flag = false;
		if (set != null) {
			flag = set.add(randomNumber);
		}
		if (flag)
			quesBankVosList.add(dataList.get(randomNumber));
		else
			addUniqueRandomNumber(set, t, listSize, dataList, quesBankVosList);
		return quesBankVosList;
	}
*/
}
