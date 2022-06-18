package com.cp.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.FileUtilityDao;
import com.cp.eCalMacroPOC.PushDataInTargetSheet;

import com.cp.vo.FinancialReportVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class GenerateFinancialReport {
	private static final Logger logger = Logger
			.getLogger(GenerateFinancialReport.class.getName());

	public static String geneareteReport(FinancialReportVo vo) {
		logger.info("geneareteReport=====");
		logger.info("Report type =====" + vo.getReportType()
				+ "-------case_id=====" + vo.getCaseId());
		// docUpload( vo.getReportType(), vo.getCaseId(), vo);
		return docUpload("CAM", vo.getCaseId(), vo);
	}

	public static String docUpload(String myFile, String caseID, Object ob) {
		boolean status = false;
		String fileName = "";

		FinancialReportVo vo = (FinancialReportVo) ob;
		try {

			if (!myFile.equals("")) {
				logger.info("FileName :" + myFile);

				// Create file
				// File fileToCreate = new File(filePath, fileName);
				if (myFile.equalsIgnoreCase("BANK")) {
					fileName = generateBankAccAnalysisReport(vo);
				} else if (myFile.equalsIgnoreCase("OBLIGATION")) {
					fileName = generateObligationReport(vo);
				} else if (myFile.equalsIgnoreCase("RATIO")) {
					fileName = generateRatioReport(vo);
				} else if (myFile.equalsIgnoreCase("CAM")) {
					fileName = generateCamReport(vo);
				}
				else if (myFile.equalsIgnoreCase("LOAN_SHEET")) {
					fileName = generateLoanSheetReport(vo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileName;

	}

	public static String generateCamReport(FinancialReportVo vo)
			throws Exception {
		logger.info("In generateCamReport()");
		String returnString = "";

		try {

			String caseId = CommonFunction.checkNull(vo.getCaseId());

			logger.info("Deal Id---------" + caseId);

			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);

			String refId = dao.getApplicationReferenceNo(caseId);

			String productId = dao.getProductId(caseId);
			String camTemplateId = dao.getProductCamTemplateId(productId);
			//productId=camTemplateId;
			logger.info("Deal No---------" + refId);

			String timeStamp = System.nanoTime() + "";
			UnderwritingDocUploadVo vo1 = new UnderwritingDocUploadVo();
			vo1.setCustRef(refId);
			vo1.setCaseId(caseId);
			vo1.setProductId(productId);
			String camTemplateFile = dao
					.getParameterMSTInfo(ConstantValue.CAM_TEMPLATE_FILE);

			/*String camTemplatePath = dao
					.getParameterMSTInfo(ConstantValue.FILE_PATH);*/
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);

			boolean flag = EligibilityCalculationProcess
					.readAndWriteFileForReport(vo1, camTemplateFile, timeStamp);

			String readFile = camTemplatePath + "/" + camTemplateFile 
					+ "_" + refId + "_" + timeStamp + ".xls";

			returnString = "/" + camTemplateFile + "_" 
					+ refId + "_" + timeStamp + ".xls";

			boolean flag1 = false;
			
			
				
				//CamTempateSheetProcess.AddRadioSheet(readFile, vo1, "Fin");
				EligibilityCalculationForProductSpecific e = new EligibilityCalculationForProductSpecific();
				
				ArrayList rtrValueList = dao.getRTRObligationValue(caseId,"RTR-DC");
				e.systemInputData$RTR(readFile, vo1, rtrValueList, "RTR-DC", "LAP");
				
			
				EligibilityCalculationProcess.systemInputData$CustDetailsIndv(readFile, vo1);
				EligibilityCalculationProcess.systemInputData$BALS(readFile, vo1);
				EligibilityCalculationProcess.systemInputData$PL(readFile, vo1);
				EligibilityCalculationProcess.systemInputData$OTHER(readFile, vo1);
			
			
			logger.info("readFile---------" + readFile);
			EligibilityCalculationProcess.schemeWiseEligibilityCalcResult( readFile,vo1, productId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public static String generateBankAccAnalysisReport(FinancialReportVo vo)
			throws Exception {
		logger.info("In generateBankAccAnalysisReport()");

		String caseId = CommonFunction.checkNull(vo.getCaseId());
		String returnString = "";
		try {
			logger.info("case_id---------" + caseId);
			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);

			String refId = dao.getApplicationReferenceNo(caseId);
			logger.info("refId---------" + refId);
			UnderwritingDocUploadVo vo1 = new UnderwritingDocUploadVo();
			vo1.setCustRef(refId);
			vo1.setCaseId(caseId);
			String timeStamp = System.nanoTime() + "";
			String camTemplateFile = dao
					.getParameterMSTInfo(ConstantValue.CAM_TEMPLATE_BANK_ACC_ANALYSIS_REPORT);
			/*String camTemplatePath = dao
					.getParameterMSTInfo(ConstantValue.FILE_PATH);*/
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			returnString = "/" + camTemplateFile + "_" + refId + "_"
					+ timeStamp + ".xls";
			boolean flag = EligibilityCalculationProcess
					.readAndWriteFileForReport(vo1, camTemplateFile, timeStamp);
			String readFile = camTemplatePath + "/" + camTemplateFile + "_"
					+ refId + "_" + timeStamp + ".xls";

			logger.info("readFile---------" + readFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public static String generateObligationReport(FinancialReportVo finvo)
			throws Exception {
		logger.info("In generateObligationReport()");
		String returnString = "";
		try {

			String timeStamp = System.nanoTime() + "";
			String caseId = CommonFunction.checkNull(finvo.getCaseId());

			logger.info("case_id---------" + caseId);
			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);

			String refId = dao.getApplicationReferenceNo(caseId);
			logger.info("refId---------" + refId);
			UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
			vo.setCustRef(refId);
			vo.setCaseId(caseId);
			String camTemplateFile = dao
					.getParameterMSTInfo(ConstantValue.CAM_TEMPLATE_OBLIGATION_REPORT);
			/*String camTemplatePath = dao
					.getParameterMSTInfo(ConstantValue.FILE_PATH);*/
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			boolean flag = EligibilityCalculationProcess
					.readAndWriteFileForReport(vo, camTemplateFile, timeStamp);
			String readFile = camTemplatePath + "/" + camTemplateFile + "_"
					+ refId + "_" + timeStamp + ".xls";
			returnString = "/" + camTemplateFile + "_" + refId + "_"
					+ timeStamp + ".xls";
			String productId = dao.getProductId(caseId);
			vo.setProductId(productId);
			EligibilityCalculationProcess.systemInputData$Obligation(readFile,
					vo,"Obligation & RTR- BIL");

			logger.info("readFile---------" + readFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	public static String generateRatioReport(FinancialReportVo finVO)
			throws Exception {
		logger.info("In generateRatioReport()");
		String returnString = "";

		try {

			String timeStamp = System.nanoTime() + "";

			String caseId = finVO.getCaseId();

			logger.info("case_id---------" + caseId);
			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);

			String refId = dao.getApplicationReferenceNo(caseId);
			logger.info("refId---------" + refId);
			UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
			vo.setCustRef(refId);
			vo.setCaseId(caseId);
			String camTemplateFile = dao
					.getParameterMSTInfo(ConstantValue.CAM_TEMPLATE_RATIO_REPORT);
			boolean flag = EligibilityCalculationProcess
					.readAndWriteFileForReport(vo, camTemplateFile, timeStamp);
			/*String camTemplatePath = dao
					.getParameterMSTInfo(ConstantValue.FILE_PATH);*/
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			String readFile = camTemplatePath + "/" + camTemplateFile + "_"
					+ refId + "_" + timeStamp + ".xls";
			returnString = "/" + camTemplateFile + "_" + refId + "_"
					+ timeStamp + ".xls";

			logger.info("readFile---------" + readFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}
	public static String generateLoanSheetReport(FinancialReportVo finVO)
			throws Exception {
		logger.info("In generateLoanSheetReport()");
		String returnString = "";
		try {

			String timeStamp = System.nanoTime() + "";
			String caseId = CommonFunction.checkNull(finVO.getCaseId());

			logger.info("case_id---------" + caseId);
			FileUtilityDao dao = (FileUtilityDao) DaoImplInstanceFactory
					.getDaoImplInstance(FileUtilityDao.IDENTITY);

			String refId = dao.getApplicationReferenceNo(caseId);
			logger.info("refId---------" + refId);
			UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
			vo.setCustRef(refId);
			vo.setCaseId(caseId);
			String camTemplateFile = dao
					.getParameterMSTInfo(ConstantValue.LOAN_SHEET_TEMPLATE_REPORT);
		
			String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
			boolean flag = EligibilityCalculationProcess
					.readAndWriteFileForReport(vo, camTemplateFile, timeStamp);
			String readFile = camTemplatePath + "/" + camTemplateFile + "_"
					+ refId + "_" + timeStamp + ".xls";
			returnString = "/" + camTemplateFile + "_" + refId + "_"
					+ timeStamp + ".xls";
			String productId = dao.getProductId(caseId);
			vo.setProductId(productId);
			EligibilityCalculationProcess.systemInputData$LoanSheetObligation(readFile,
					vo,"Loan Sheet");

			logger.info("readFile---------" + readFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	
}
