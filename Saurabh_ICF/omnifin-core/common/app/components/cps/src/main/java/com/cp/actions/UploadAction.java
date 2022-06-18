//start : Abhishek Mathur

package com.cp.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.business.ejbClient.CreditProcessingMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.cp.dao.FileUplaodDao;
import com.cp.dao.FileUtilityDao;
import com.cp.dao.OcrDAO;
import com.cp.process.FileUploadProcessTemplete;
import com.cp.process.NomenClatureProcess;
import com.cp.vo.CodeDescVo;
import com.cp.vo.FinancialAnalysisVo;
import com.cp.vo.UnderwritingDocUploadVo;
import com.cp.vo.UploadTypeVo;

public class UploadAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(UploadAction.class
			.getName());
	private String businessDate;

	/*public ActionForward openDocumentUpload(ActionMapping mapping,
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
		// FormFile file = UploadForm.getDocFile();

		// Get the servers upload directory real path name
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
		} // end comment

		return mapping.findForward("success");
	}

	

	

	

	public ActionForward downloadUploadedFileOmniFin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String downloadPath = null;
		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");
		if (userobj == null) {
			logger.info("here downloadUploadedFileOmniFin method of uploadDocumentFile  action the session is out----------------");
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

		String txnId = CommonFunction.checkNull(request.getParameter("txnId"));
		String txnType = CommonFunction.checkNull(request
				.getParameter("txnType"));
		String fileName = CommonFunction.checkNull(request
				.getParameter("fileName"));
		// sachin
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
		// OcrDAO service = new OcrDAOImpl();

		downloadPath = service.getDownLoadFileInfoOmniFin(txnId, txnType,
				fileName);

		// end by sachin
		logger.info("File Name downloadPath: " + downloadPath);
		if (!CommonFunction.checkNull(downloadPath).equalsIgnoreCase("")) {
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
			}

			else if (fileType.trim().equalsIgnoreCase("rtf")) {
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
			ArrayList<UploadTypeVo> doctype = service.getDocType();
			ArrayList<UploadTypeVo> formatType = service.getFormatType();
			ArrayList<CodeDescVo> bankStmntDateFormat = service.getBankStmntDateFormat();
			request.setAttribute("bankStmntDateFormatList", bankStmntDateFormat);
			request.setAttribute("docType", doctype);
			request.setAttribute("formatType", formatType);
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
					.getLookUpInstance(
							CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
							request);
			ArrayList list1 = cpm.getEntity();

			request.setAttribute("docEntity", list1);
		}
		fileName = null;
		strFlag = null;
		txnType = null;
		txnId = null;
		return null;
	}
*/
	public ActionForward forwardDocument(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In UpdateUploadDocData in Underwriting Upload Processing Action");
		boolean status = false;
		boolean checkStatus = false;

		String bDate = "";
		String companyId = "";
		String userId = "";
		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in UpdateUploadDocData method of uploadDocumentFile action the session is out----------------");
			return mapping.findForward("sessionOut");
		} else {
			bDate = userobj.getBusinessdate();
			companyId = "" + userobj.getCompanyId();
			userId = userobj.getUserId();
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

		String[] dealNoArr = (String[]) request.getParameterValues("dealNoArr");
		String dealId = request.getParameter("lbxDealNo");
		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
		String custRef = "";
		String docEntity = request.getParameter("docEntity");
		if (!CommonFunction.checkNull(dealId).equalsIgnoreCase("")) {
			String query = "select DEAL_NO from cr_deal_dtl where deal_id='"
					+ dealId + "'";
			custRef = ConnectionDAO.singleReturn(query);
			logger.info("custRef::::::" + custRef);
		}
		ArrayList uploadedDocList = new ArrayList();
		ArrayList uploadedDoc = new ArrayList();
		String msg = "";
		String a = "";
		
		boolean checkDocStatus = service.checkDocStatus(dealId);
		if(!checkDocStatus)
		{
			msg = "CDSF";
			request.setAttribute("msg", msg);
			a = "success";
		}
		else
		{
			
			checkStatus = service.checkUploadFileData(dealId);
		//	boolean QueryInitiationStatus = service.getStatusDetail(dealId);//hina
			if (checkStatus/* && !QueryInitiationStatus*/)
				{
					status = service.updateDocumentUploadEntry(docEntity, dealId);
					if (status) 
						{
							msg = "DS";
							logger.info("msg============" + msg);
							request.setAttribute("msg", msg);
							System.out.println("msg P2 STDO==="+ request.getAttribute("msg"));
							uploadedDocList = service.makerSearch(uwDocVo);
							request.setAttribute("uploadedDocList", uploadedDocList);
							String checkStageM = CommonFunction.stageMovement(companyId,"DUM", "F", dealId, "DUM", bDate, userId);
							logger.info("checkStageM : " + checkStageM);
							if (!CommonFunction.checkNull(checkStageM).equalsIgnoreCase("S"))
								request.setAttribute("checkStageM", checkStageM);
							a = "onsuccess";
						} 
					else 
						{
							msg = "DE";
							request.setAttribute("msg", msg);
							uploadedDocList = service.DocumentType(dealId, docEntity,custRef);
							request.setAttribute("uploadedDocList", uploadedDocList);
							uploadedDoc = service.getUploadUnderwritingData(dealId);
							request.setAttribute("uploadDoc", uploadedDoc);
							a = "success";
						}
				} 
			else 
				{
					//if (QueryInitiationStatus) 
						//{
						//	msg= "QueryInitiationPending";
						//}
					//else
					//	{
							msg = "EF";
					//	}
					request.setAttribute("msg", msg);
					uploadedDocList = service.DocumentType(dealId, docEntity, custRef);
					request.setAttribute("uploadedDocList", uploadedDocList);
					uploadedDoc = service.getUploadUnderwritingData(dealId);
					request.setAttribute("uploadDoc", uploadedDoc);
					a = "success";
				}
		ArrayList<UploadTypeVo> doctype = service.getDocType();
		ArrayList<UploadTypeVo> formatType = service.getFormatType();
		ArrayList<CodeDescVo> bankStmntDateFormat = service.getBankStmntDateFormat();
		request.setAttribute("bankStmntDateFormatList", bankStmntDateFormat);
		request.setAttribute("docType", doctype);
		request.setAttribute("formatType", formatType);
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
				.getLookUpInstance(
						CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
						request);
		ArrayList list1 = cpm.getEntity("DUM");

		request.setAttribute("docEntity", list1);
		uwDocVo = null;
		service = null;
		strFlag = null;
	}	
		form.reset(mapping, request);
		return mapping.findForward(a);

	}

	public ActionForward deleteUploadDocData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In deleteUploadDocData in Underwriting Upload Processing Action");
		boolean deleteStatus = false;

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in deleteUploadDocData method of uploadDocumentFile action the session is out----------------");
			return mapping.findForward("sessionOut");
		}

		Object sessionId = session.getAttribute("sessionID");
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
		if (session.getAttribute("cmdocupload") != null) {
			flag1 = session.getAttribute("cmdocupload").toString();
		}

		String dealId = request.getParameter("dealId");
		logger.info("Deal_ID=======================" + dealId);
		String chk = request.getParameter("chk");
		logger.info("chk In Action=======================" + chk);
		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
				.getDaoImplInstance(FileUtilityDao.IDENTITY);
		FileUplaodDao uploadDao = (FileUplaodDao) DaoImplInstanceFactory
				.getDaoImplInstance(FileUplaodDao.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh

		uwDocVo.setRadioButton(chk);
		logger.info("radioButton::;;;;;;;" + uwDocVo.getRadioButton());
		String radioButton = (CommonFunction.checkNull(request
				.getParameter("radioButton")).trim());
		 String financialYear = dao.getFinancialYearFromCustomerDemeographics(dealId);
		    /*financialYear = financialYear.substring(financialYear.lastIndexOf("-")+1,financialYear.length());*/
		    financialYear = financialYear.substring(0, 4);
		    request.setAttribute("financialYear", financialYear);
		boolean uploadStatus = DocUpload.docDelete(chk, dealId);
		String docType = dao.getDocType(chk);
		String refId = dao.getApplicationReferenceNo(dealId);
		boolean deleteDataFlag = false;
		logger.info("Doc_id In Action ================ " + chk);
		if (CommonFunction.checkNull(docType).equalsIgnoreCase("BS")
				&& uploadStatus)
			deleteDataFlag = uploadDao.deleteBankDetailsData(refId,
					userobj.getUserId(), chk);
		else if (CommonFunction.checkNull(docType).equalsIgnoreCase("P&L")
				&& uploadStatus)
			deleteDataFlag = uploadDao.deleteFinancialDetailsData(dealId, "P",
					userobj.getUserId(), chk);
		else if (CommonFunction.checkNull(docType).equalsIgnoreCase("BALS")
				&& uploadStatus)
			deleteDataFlag = uploadDao.deleteFinancialDetailsData(dealId, "B",
					userobj.getUserId(), chk);
		
		else if (CommonFunction.checkNull(docType).equalsIgnoreCase("CO")
				&& uploadStatus)
			deleteDataFlag = uploadDao.deleteCibilObligationRecord(dealId, chk);
		
		else if (CommonFunction.checkNull(docType).equalsIgnoreCase("ANU")
				&& uploadStatus)
			deleteDataFlag = uploadDao.deleteAccountLoanNumberRecord(dealId);
		
		if (deleteDataFlag)
			deleteStatus = service.deleteDocumentUploadEntry(chk, dealId);

		if (deleteStatus)
			request.setAttribute("message", "deletedoc");
		else
			request.setAttribute("message", "notDeletedoc");
		request.setAttribute("limitOfDocument", "limitOfDocument");

		ArrayList uploadedDocList = new ArrayList();
		ArrayList uploadDocListForCp = new ArrayList();
		ArrayList uploadedDoc = new ArrayList();

		uploadedDoc = service.getUploadUnderwritingData(dealId);
		request.setAttribute("uploadDoc", uploadedDoc);
		ArrayList<UploadTypeVo> doctype = service.getDocType();
		ArrayList<UploadTypeVo> formatType = service.getFormatType();
		ArrayList<CodeDescVo> bankStmntDateFormat = service.getBankStmntDateFormat();
		request.setAttribute("bankStmntDateFormatList", bankStmntDateFormat);
		request.setAttribute("docType", doctype);
		request.setAttribute("formatType", formatType);
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
		return mapping.findForward("delete");

	}
	public ActionForward downloadUnderwritingFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		String source = request.getParameter("source");

		if (source == null) {
			dealId = (String) request.getParameter("dealNo");
		} else {
			dealId = (String) request.getParameter("dealNo");
		}
		String fileName = "";// request.getParameter("fileName");

		logger.info("fileName=======" + fileName);

		logger.info("dealId===" + dealId);
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh

		String docId = CommonFunction.checkNull(request.getParameter("docID"));
		FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
				.getDaoImplInstance(FileUtilityDao.IDENTITY);
		downloadPath = dao.getDocPath(docId);
		fileName = dao.getFileName(docId);
		logger.info("downloadPath======" + downloadPath);

		logger.info("File Name downloadPath: " + downloadPath);

		if (!CommonFunction.checkNull(downloadPath).equalsIgnoreCase("")) {
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
			ArrayList<UploadTypeVo> doctype = service.getDocType();
			ArrayList<UploadTypeVo> formatType = service.getFormatType();
			ArrayList<CodeDescVo> bankStmntDateFormat = service.getBankStmntDateFormat();
			request.setAttribute("bankStmntDateFormatList", bankStmntDateFormat);
			request.setAttribute("docType", doctype);
			request.setAttribute("formatType", formatType);
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
					.getLookUpInstance(
							CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
							request);
			ArrayList list1 = cpm.getEntity("DUM");

			request.setAttribute("docEntity", list1);
		}
		flag1 = null;
		fileName = null;

		strFlag = null;

		return null;
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
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
		// OcrDAO service = new OcrDAOImpl();

		String dealId = (CommonFunction.checkNull(request
				.getParameter("dealId")).trim());
		uwDocVo.setDealId(dealId);
		FileUtilityDao dUtilityDao = (FileUtilityDao) DaoImplInstanceFactory
				.getDaoImplInstance(FileUtilityDao.IDENTITY);
		 String financialYear = dUtilityDao.getFinancialYearFromCustomerDemeographics(dealId);
		    /*financialYear = financialYear.substring(financialYear.lastIndexOf("-")+1,financialYear.length());*/
		 financialYear = financialYear.substring(0, 4);
		    request.setAttribute("financialYear", financialYear);
		session.setAttribute("caseId", dealId);
		logger.info("dealIdid::;;;;;;;" + uwDocVo.getDealId());
		String CustomerType = (CommonFunction.checkNull(request
				.getParameter("docEntity")).trim());
		uwDocVo.setDocEntity(CustomerType);
		logger.info("CustomerType::;;;;;;;" + uwDocVo.getDocEntity());
		String CustomerName = (CommonFunction.checkNull(request
				.getParameter("cust")).trim());
		uwDocVo.setCustomerName(CustomerName);
		logger.info("CustomerName::;;;;;;;" + uwDocVo.getCustomerName());
		String custRef = (CommonFunction.checkNull(request
				.getParameter("custRef")).trim());
		uwDocVo.setCustRef(custRef);
		logger.info("custRef::;;;;;;;" + uwDocVo.getCustRef());

		ArrayList<UploadTypeVo> doctype = service.getDocType();

		ArrayList<UploadTypeVo> formatType = service.getFormatType();
		ArrayList<CodeDescVo> bankStmntDateFormat = service.getBankStmntDateFormat();
		ArrayList<CodeDescVo> accountType = service.getAccountType();
		request.setAttribute("bankStmntDateFormatList", bankStmntDateFormat);
		request.setAttribute("docType", doctype);
		request.setAttribute("accountType", accountType);

		logger.info("docType::;;;;;;;" + doctype);

		logger.info("docTypeSize::;;;;;;;" + doctype.size());

		request.setAttribute("formatType", formatType);

		/*
		 * // Abhishek Start Change For Delete Button
		 * 
		 * String Doc_desc = request.getParameter("Doc_desc");
		 * uwDocVo.setRadioButton(Doc_desc);
		 * logger.info("Doc_desc::;;;;;;;"+uwDocVo.Doc_desc()); String
		 * Doc_desc=(
		 * CommonFunction.checkNull(request.getParameter("Doc_desc")).trim());
		 * 
		 * // Abhishek end Change For Delete Button
		 */

		ArrayList uploadedDocList = new ArrayList();
		ArrayList uploadedDoc = new ArrayList();

		uploadedDocList = service.DocumentType(dealId, CustomerType, custRef);

		/* Doc_des Null */
		// uploadedDocList = service.getUploadUnderwritingData(dealId );

		request.setAttribute("uploadedDocList", uploadedDocList);

		uploadedDoc = service.getUploadUnderwritingData(dealId);

		request.setAttribute("uploadDoc", uploadedDoc);

		logger.info("grid Data::::::::::::" + uploadedDocList);

		logger.info("customerReferenceNo   " + uwDocVo.getCustRef());

		request.setAttribute("List", uwDocVo.getCustRef());
		request.setAttribute("List1", uwDocVo.getCustomerName());
		request.setAttribute("List2", uwDocVo.getDocEntity());
		request.setAttribute("List3", uwDocVo.getDealId());

		request.setAttribute("List4", uwDocVo.getBankId());
		request.setAttribute("List5", uwDocVo.getBranchId());
		request.setAttribute("List6", uwDocVo.getAccountNo());
		request.setAttribute("List7", uwDocVo.getToDate());
		request.setAttribute("List8", uwDocVo.getFromDate());

		/*
		 * // Abhishek start Change For Delete Button
		 * 
		 * request.setAttribute("List4", uwDocVo.getChk());
		 * 
		 * // Abhishek end Change For Delete Button
		 */
		logger.info("list......................."
				+ request.getAttribute("List"));
		logger.info("list1......................."
				+ request.getAttribute("List1"));
		logger.info("list2......................."
				+ request.getAttribute("List2"));
		logger.info("list3......................."
				+ request.getAttribute("List3"));

		logger.info("list4......................."
				+ request.getAttribute("List4"));
		logger.info("list5......................."
				+ request.getAttribute("List5"));
		logger.info("list6......................."
				+ request.getAttribute("List6"));
		logger.info("list7......................."
				+ request.getAttribute("List7"));
		logger.info("list8......................."
				+ request.getAttribute("List8"));

		/*
		 * // Abhishek Start Change For Delete Button
		 * 
		 * logger.info("list4......................."+request.getAttribute("List4"
		 * ));
		 * 
		 * // Abhishek End Change For Delete Button
		 */
		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
				.getLookUpInstance(
						CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
						request);
		ArrayList list1 = cpm.getEntity("DUM");

		request.setAttribute("docEntity", list1);

		uwDocVo = null;
		service = null;
		strFlag = null;
		// request.removeAttribute(dealId);
		// request.removeAttribute(CustomerType);
		form.reset(mapping, request);
		return mapping.findForward("success");
	}
	public ActionForward makerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In makerSearch in Underwriting Upload Processing Action");
		boolean deleteStatus = false;

		HttpSession session = request.getSession();
		boolean flag = false;
		String userId = "";
		String branchId = "";
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in makerSearch method of uploadDocumentFile action the session is out----------------");
			return mapping.findForward("sessionOut");
		} else {
			userId = userobj.getUserId();
			branchId = userobj.getBranchId();
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
		if (session.getAttribute("cmdocupload") != null) {
			flag1 = session.getAttribute("cmdocupload").toString();
		}
		
		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
		// OcrDAO service = new OcrDAOImpl();
		uwDocVo.setMakerId(userId);
		String dealId = request.getParameter("dealId");
		String customerName = request.getParameter("customerName");
		String docEntity = request.getParameter("docEntity");
		uwDocVo.setBranchId(branchId);

		uwDocVo.setDealId(dealId);
		uwDocVo.setCustomerName(customerName);
		uwDocVo.setDocEntity(docEntity);

		/*
		 * ArrayList<UploadTypeVo> doctype= service.getDocType(); //
		 * ArrayList<UploadTypeVo> formatType= service.getFormatType();
		 * request.setAttribute("docType", doctype);
		 */
		// request.setAttribute("formatType", formatType);
		ArrayList<UploadTypeVo> doctype = service.getDocType();
		ArrayList<UploadTypeVo> formatType = service.getFormatType();
		ArrayList<CodeDescVo> bankStmntDateFormat = service.getBankStmntDateFormat();
		request.setAttribute("bankStmntDateFormatList", bankStmntDateFormat);
		request.setAttribute("docType", doctype);
		request.setAttribute("formatType", formatType);
		ArrayList uploadedDocList = new ArrayList();

		uploadedDocList = service.makerSearch(uwDocVo);
		request.setAttribute("uploadedDocList", uploadedDocList);
		/*
		 * uploadedDocList = service.getUploadUnderwritingData(dealId);
		 * request.setAttribute("uploadedDocList", uploadedDocList);
		 * 
		 * if(uploadedDocList.size()>0) {
		 * request.setAttribute("uploadedDocList", uploadedDocList); }
		 * if(uploadDocListForCp.size() > 0) {
		 * request.setAttribute("uploadDocListForCp", uploadDocListForCp); }
		 */
		if (uploadedDocList.size() <= 0) {
			request.setAttribute("msg", "Z");
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

	public ActionForward documentUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("In documentUpload in Underwriting Upload Processing Action");
		boolean deleteStatus = false;

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in makerSearch method of documentUpload action the session is out----------------");
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
		if (session.getAttribute("cmdocupload") != null) {
			flag1 = session.getAttribute("cmdocupload").toString();
		}

		UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
		ArrayList<UploadTypeVo> doctype = service.getDocType();
		ArrayList<UploadTypeVo> formatType = service.getFormatType();
		ArrayList<CodeDescVo> bankStmntDateFormat = service.getBankStmntDateFormat();
		ArrayList<CodeDescVo> accountType = service.getAccountType();
		request.setAttribute("bankStmntDateFormatList", bankStmntDateFormat);
		request.setAttribute("docType", doctype);
		request.setAttribute("formatType", formatType);
		request.setAttribute("accountType", accountType);
		

		ArrayList uploadedDocList = new ArrayList();

		
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
		return mapping.findForward("success");

	}

	/*public ActionForward viewAllDocument(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("In documentUpload in Underwriting Upload Processing Action");
		boolean deleteStatus = false;

		HttpSession session = request.getSession();
		boolean flag = false;
		UserObject userobj = (UserObject) session.getAttribute("userobject");

		if (userobj == null) {
			logger.info("in makerSearch method of documentUpload action the session is out----------------");
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
		DynaValidatorForm CustomerDemographicsDynaValidatorForm = (DynaValidatorForm) form;
		org.apache.commons.beanutils.BeanUtils.copyProperties(uwDocVo,
				CustomerDemographicsDynaValidatorForm);

		OcrDAO service = (OcrDAO) DaoImplInstanceFactory
				.getDaoImplInstance(OcrDAO.IDENTITY);
		ArrayList<UnderwritingDocUploadVo> viewAllDocument = service
				.getViewAllDocumentList(uwDocVo);
		request.setAttribute("viewAllDocument", viewAllDocument);

		ArrayList<UploadTypeVo> doctype = service.getDocType();
		ArrayList<UploadTypeVo> formatType = service.getFormatType();
		ArrayList<CodeDescVo> bankStmntDateFormat = service.getBankStmntDateFormat();
		request.setAttribute("bankStmntDateFormatList", bankStmntDateFormat);
		request.setAttribute("docType", doctype);
		request.setAttribute("formatType", formatType);

		CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
				.getLookUpInstance(
						CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
						request);
		ArrayList list1 = cpm.getEntity("DUM");
		request.setAttribute("docEntity", list1);

		return mapping.findForward("success");

	}

	
	 * public ActionForward uploadDocList(ActionMapping mapping, ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response) throws
	 * Exception {
	 * logger.info("In documentUpload in Underwriting Upload Processing Action"
	 * ); boolean deleteStatus = false;
	 * 
	 * HttpSession session = request.getSession(); boolean flag=false;
	 * UserObject userobj=(UserObject)session.getAttribute("userobject");
	 * 
	 * if(userobj==null) { logger.info(
	 * "in makerSearch method of documentUpload action the session is out----------------"
	 * ); return mapping.findForward("sessionOut"); }
	 * 
	 * Object sessionId = session.getAttribute("sessionID"); //for check User
	 * session start ServletContext context = getServlet().getServletContext();
	 * String strFlag=""; if(sessionId!=null) { strFlag =
	 * UserSessionCheck.checkSameUserSession
	 * (userobj,sessionId.toString(),"",request); }
	 * 
	 * logger.info("strFlag .............. "+strFlag);
	 * 
	 * if(!"".equalsIgnoreCase(strFlag)) {
	 * if(strFlag.equalsIgnoreCase("sameUserSession")) {
	 * context.removeAttribute("msg"); context.removeAttribute("msg1"); } else
	 * if(strFlag.equalsIgnoreCase("BODCheck")) { context.setAttribute("msg",
	 * "B"); } return mapping.findForward("logout"); }
	 * 
	 * UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
	 * DynaValidatorForm CustomerDemographicsDynaValidatorForm=
	 * (DynaValidatorForm)form;
	 * org.apache.commons.beanutils.BeanUtils.copyProperties(uwDocVo,
	 * CustomerDemographicsDynaValidatorForm);
	 * 
	 * OcrDAO
	 * service=(OcrDAO)DaoImplInstanceFactory.getDaoImplInstance(OcrDAO.IDENTITY
	 * ); String docEntity= request.getParameter("docEntity");
	 * request.setAttribute("DocumentType",documentUpload(docEntity));
	 * 
	 * ArrayList<UploadTypeVo> doctype= service.getDocType();
	 * ArrayList<UploadTypeVo> formatType= service.getFormatType();
	 * request.setAttribute("docType", doctype);
	 * request.setAttribute("formatType", formatType);
	 * 
	 * CreditProcessingMasterBussinessSessionBeanRemote cpm =
	 * (CreditProcessingMasterBussinessSessionBeanRemote)
	 * LookUpInstanceFactory.getLookUpInstance
	 * (CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
	 * request); ArrayList list1 = cpm.getEntity("DUM");
	 * request.setAttribute("docEntity", list1);
	 * 
	 * return mapping.findForward("success");
	 * 
	 * }
	 

	*/
	public ActionForward uploadDocumentFile(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	throws Exception
		{
			logger.info("Inside uploadDocumentFile(uploadUnderwritingDocData)");
			HttpSession session = request.getSession();
			boolean flag = false;
			UserObject userobj = (UserObject) session.getAttribute("userobject");
			String userName = "";
			String msg = null;
			String sourceType = "";
			boolean b = false;
			if (userobj != null)
				{
					userName = userobj.getUserId();
				}
			else
				return mapping.findForward("sessionOut");

			Object sessionId = session.getAttribute("sessionID");
			ServletContext context = getServlet().getServletContext();
			String strFlag = "";

			if (sessionId != null)
				{
				strFlag = UserSessionCheck.checkSameUserSession(userobj,sessionId.toString(), "", request);
				}
			if (!"".equalsIgnoreCase(strFlag))
				{
					if (strFlag.equalsIgnoreCase("sameUserSession")) 
						{
							context.removeAttribute("msg");
							context.removeAttribute("msg1");
						}
					else if (strFlag.equalsIgnoreCase("BODCheck"))
						{
							context.setAttribute("msg", "B");
						}
					return mapping.findForward("logout");
				}

			String flag1 = null;
			if (session.getAttribute("cmdocupload") != null)
			flag1 = session.getAttribute("cmdocupload").toString();

			String dealId = (String) request.getParameter("lbxDealNo");
			logger.info("case_id------------------------------>"+session.getAttribute("caseId"));
			if(dealId.equalsIgnoreCase(""))
				{
					dealId = (String)session.getAttribute("caseId");
				} 
			UnderwritingDocUploadVo uwDocVo = new UnderwritingDocUploadVo();
			DynaValidatorForm UnderwriterUploadDynaValidatorForm = (DynaValidatorForm) form;
			org.apache.commons.beanutils.BeanUtils.copyProperties(uwDocVo,UnderwriterUploadDynaValidatorForm);
			uwDocVo.setUserName("" + userName);
			uwDocVo.setDealId(dealId);
			uwDocVo.setCaseId(dealId);
			request.setAttribute("caseId", dealId);
			FileUtilityDao dUtilityDao = (FileUtilityDao) DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			String financialYear = dUtilityDao.getFinancialYearFromCustomerDemeographics(dealId);
			//financialYear = financialYear.substring(financialYear.lastIndexOf("-")+1,financialYear.length());
			financialYear = financialYear.substring(0, 4);
			request.setAttribute("financialYear", financialYear);
			logger.info("bankId=======" + uwDocVo.getLbxBankID());
			uwDocVo.setBankId(uwDocVo.getLbxBankID());
			logger.info("docType=======" + uwDocVo.getDocType());
			if (CommonFunction.checkNull(uwDocVo.getDocType()).equalsIgnoreCase("P&L"))
			sourceType = "P";
			else if (CommonFunction.checkNull(uwDocVo.getDocType()).equalsIgnoreCase("BALS"))
				{
					sourceType = "B";
				} 
			else
			sourceType = "BD";
		
			uwDocVo.setSourceType(sourceType);
		
			logger.info("fromDate=======" + uwDocVo.getFromDate());
			logger.info("todate=======" + uwDocVo.getToDate());
			logger.info("accountType=======" + uwDocVo.getAccountType());
			logger.info("DocFormat=======" + uwDocVo.getFormatType());
			logger.info("AccountNo=======" + uwDocVo.getAccountNo());
			logger.info("BankBranch=======" + uwDocVo.getBankBranch());
		
			String docEntity = request.getParameter("docEntity");
			logger.info("docEntity=======" + docEntity);
			uwDocVo.setLbxDocId(docEntity);
			logger.info("Doc_id=======" + uwDocVo.getLbxDocId());
			uwDocVo.setMakerId(userobj.getUserId());
			uwDocVo.setMakerDate(userobj.getBusinessdate());
			uwDocVo.setRefId(uwDocVo.getDealNo());
			OcrDAO service = (OcrDAO) DaoImplInstanceFactory.getDaoImplInstance(OcrDAO.IDENTITY);
			logger.info("Implementation class: " + service.getClass()); // changed
																	// by asesh
			boolean uploadStatus = false;
			boolean status = false;
			int maxId = 0;
			ArrayList uploadedDocList = new ArrayList();
			ArrayList uploadedDocList1 = new ArrayList();
			ArrayList uploadDocListForCp = new ArrayList();
			int limitNoOfDocument = service.getLimitOfDocumentUpload();
			String message = "You can Upload " + limitNoOfDocument+ " Documents only";
			FormFile file = uwDocVo.getDocFile();
			logger.info("file======" + file);
			logger.info("flag1======" + flag1);
		
			ArrayList<UploadTypeVo> doctype = service.getDocType();
			ArrayList<UploadTypeVo> formatType = service.getFormatType();
			ArrayList<CodeDescVo> bankStmntDateFormat = service.getBankStmntDateFormat();
			ArrayList<CodeDescVo> accountTypeList = service.getAccountType();
		
			request.setAttribute("bankStmntDateFormatList", bankStmntDateFormat);
			request.setAttribute("docType", doctype);
			request.setAttribute("accountType", accountTypeList);
			
			logger.info("docType::;;;;;;;" + doctype);
			logger.info("docTypeSize::;;;;;;;" + doctype.size());
			request.setAttribute("formatType", formatType);
	
			String customerId = dUtilityDao.getCustomerID(uwDocVo.getDealId());
			String customerRoleType = dUtilityDao.getCustomerRoleType(uwDocVo.getDealId());
			logger.info("uwDocVo.getEntityCustomerId()---------"+ uwDocVo.getEntityCustomerId());
			logger.info("uwDocVo.getDocEntity()---------"+ uwDocVo.getDocEntity());
		
			String customerName = dUtilityDao.getCustomerNameByCustomerId(uwDocVo.getEntityCustomerId());
			String bankName = dUtilityDao.getBankName(uwDocVo.getBankId());
			customerId=uwDocVo.getEntityCustomerId();
			customerRoleType= uwDocVo.getDocEntity();
			uwDocVo.setCustomerId(customerId);
			uwDocVo.setCustomerRoleType(customerRoleType);
			uwDocVo.setCustomerName(customerName);
			uwDocVo.setBankName(bankName);
			FileUplaodDao uplaodDao = (FileUplaodDao) DaoImplInstanceFactory.getDaoImplInstance(FileUplaodDao.IDENTITY);
			CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory.getLookUpInstance(CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,request);
			// Check Account No upload Status
			/*if(uwDocVo.getDocType().equalsIgnoreCase("BS"))
				{
					boolean vanuFlag = uplaodDao.getVerificationAccountNumberUpload(dealId,customerId);
					if(!vanuFlag)
						{
							msg="ANU";
							request.setAttribute("msg", msg);
							ArrayList list1 = cpm.getEntity();
							request.setAttribute("docEntity", list1);
							uploadedDocList = service.getUploadUnderwritingData(dealId);
							request.setAttribute("uploadDoc", uploadedDocList);
							return mapping.findForward("success");
						//}
				}*/
			//end space 
			// 	file validation code Start
			logger.info("check required value available in vo");
			logger.info("Application no " + uwDocVo.getDealNo() + "EntityName "+ uwDocVo.getCustomerName());
			
			String accountType = uwDocVo.getAccountType();
			logger.info("accountType----------------"+accountType);
			String accDesc  = service.getAccountTypeDesc(accountType);
			uwDocVo.setAccountTypeDesc(accDesc);
			logger.info("Accout Type Description ------------->"+accDesc);
			
		boolean fileValFlag = FileUploadProcessTemplete.fileNameValidation(uwDocVo);
		// end
		logger.info("return flag " + fileValFlag);
		ArrayList uploadedDoc = new ArrayList();
		if (fileValFlag)
			{
				if (CommonFunction.checkNull(flag1).equalsIgnoreCase(""))
					{
						if (service.checkUploadFeasibility(uwDocVo.getDealId()))
							{
							logger.info("IF1...................");
							uploadStatus = DocUpload.docUpload(request,
							uwDocVo.getDocFile(), uwDocVo.getDealId(), uwDocVo);
							logger.info("uploadStatus-------" + uploadStatus);
							uwDocVo.setFileName(request.getAttribute("fileName").toString());
							uwDocVo.setDocPath(request.getAttribute("filePath").toString());
							request.setAttribute("limitOfDocument", "limitOfDocument");
							}
						else 
							{
								logger.info("IF2...................");
								uploadedDocList = service.DocumentType(dealId,uwDocVo.getDocEntity(), uwDocVo.getCustRef());
								uploadedDocList = service.getUploadUnderwritingData(dealId);
								request.setAttribute("uploadedDocList", uploadedDocList);
								request.setAttribute("message", "FiveDocOnly");
								request.setAttribute("limitOfDocument", message);
							}
					}	
				if (uploadStatus)
					{
						logger.info("uploadStatus IF5...................");
						if (CommonFunction.checkNull(flag1).equalsIgnoreCase(""))
							{
								logger.info("IF6...................");
								maxId = service.uploadUnderwritingData(uwDocVo);
								uploadedDocList = service.DocumentType(dealId,uwDocVo.getDocEntity(), uwDocVo.getCustRef());
								uploadedDocList1 = service.getUploadUnderwritingData(dealId);
							} 
						else 
							{
								logger.info("IF7...................");
								status = service.uploadUnderwritingDataForCm(uwDocVo);
								uploadedDocList = service.getUploadUnderwritingDataForCm(dealId);
								uploadDocListForCp = service.getUploadUnderwritingDataForCmCp(dealId);
							}
						if (maxId > 0) 
							{
								logger.info("status IF8...................");
								// request.setAttribute("message", "UploadSuccessful");
								request.setAttribute("limitOfDocument", "limitOfDocument");
							}
						if (uploadedDocList.size() > 0)
							{
								logger.info("IF9...................");
								request.setAttribute("uploadedDocList", uploadedDocList);
							}
						if (uploadDocListForCp.size() > 0)
							{
								logger.info("IF10...................");
								request.setAttribute("uploadDocListForCp",uploadDocListForCp);
							}
					} 
				else
					{
						if (CommonFunction.checkNull(flag1).equalsIgnoreCase(""))
							{
								logger.info("else IF12...................");
								uploadedDocList = service.getUploadUnderwritingData(dealId);
								request.setAttribute("uploadDoc", uploadedDocList);
							} 
						else 
							{
								logger.info("else IF13...................");
								uploadedDocList = service.getUploadUnderwritingDataForCm(dealId);
								uploadDocListForCp = service.getUploadUnderwritingDataForCmCp(dealId);
								request.setAttribute("uploadDoc", uploadedDocList);
								request.setAttribute("uploadedDocList1", uploadedDocList1);
								request.setAttribute("uploadDocListForCp",uploadDocListForCp);
							}
					}
				if (maxId > 0)
					{
						uwDocVo.setDocId(maxId + "");
						if (uwDocVo.getDocType().equalsIgnoreCase("P&L")|| uwDocVo.getDocType().equalsIgnoreCase("BALS"))
							{
								String fileValValue = NomenClatureProcess.nomenClatureForHeading(uwDocVo);
								logger.info("fileValValue -----------" + fileValValue);
								status = FileUploadProcessTemplete.fileTypeValidation(uwDocVo, fileValValue);
							} 
						else if (uwDocVo.getDocType().equalsIgnoreCase("BS") || uwDocVo.getDocType().equalsIgnoreCase("CO"))
							status = true;

					}
				if (status)
					{
						OCRMDBFileUploadProcess.sendToUploadOCRMDB(mapping, request,response, UnderwriterUploadDynaValidatorForm, uwDocVo.getMdbvo(uwDocVo));
						uploadedDocList = service.getUploadUnderwritingData(dealId);
						request.setAttribute("uploadDoc", uploadedDocList);
						request.setAttribute("message", "UploadProcess");
						/*
						 * if(!b) { msg="FR"; } else { b =
						 * uplaodDao.checkBankDataisValidation(uwDocVo); if(!b) {
						 * msg="R"; } }
						 */

					}
				else 
					{
						boolean deleteStatus = DocUpload.docDelete(maxId + "", dealId);
						deleteStatus = service.deleteDocumentUploadEntry(maxId + "",dealId);
						uploadedDocList = service.getUploadUnderwritingData(dealId);
						request.setAttribute("uploadDoc", uploadedDocList);
						msg = "fileTypeVal";
					}

			} 
		else 
			{
				msg = "fileNameValidation";
				logger.info("In File Name Validation-------------------");
				uploadedDoc = service.getUploadUnderwritingData(dealId);
				request.setAttribute("uploadDoc", uploadedDoc);
			}
		
		/*
		 * if(CommonFunction.checkNull(request.getAttribute("message")).
		 * equalsIgnoreCase(""))
		 */
		 
		request.setAttribute("msg", msg);
		/*CreditProcessingMasterBussinessSessionBeanRemote cpm = (CreditProcessingMasterBussinessSessionBeanRemote) LookUpInstanceFactory
		.getLookUpInstance(
				CreditProcessingMasterBussinessSessionBeanRemote.REMOTE_IDENTITY,
				request);*/
		ArrayList list1 = cpm.getEntity("DUM");
		request.setAttribute("docEntity", list1);
		service = null;
		uwDocVo = null;
		flag1 = null;
		userName = null;
		strFlag = null;
		return mapping.findForward("success");
	}
	
}