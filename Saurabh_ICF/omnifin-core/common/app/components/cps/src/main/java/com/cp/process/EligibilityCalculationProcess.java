package com.cp.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;

import com.cp.vo.FundFlowDownloadUploadVo;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.EligibilityCalculationProcessDao;
import com.cp.dao.FileUplaodDao;
import com.cp.dao.FileUtilityDao;
import com.cp.eCalMacroPOC.PushDataInTargetSheet;
import com.cp.util.DateUtility;
import com.cp.vo.UnderwritingDocUploadVo;

public class EligibilityCalculationProcess {
	private static final Logger logger = Logger.getLogger(EligibilityCalculationProcess.class.getName());
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	public static boolean readAndWriteFile( UnderwritingDocUploadVo vo)
	{
		boolean status=false;
		FileUtilityDao dao  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
		
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		
		FileUplaodDao uploadDao = (FileUplaodDao) DaoImplInstanceFactory.getDaoImplInstance(FileUplaodDao.IDENTITY);
		
		String productId = dao.getProductId(vo.getCaseId());
		String camTemplateId = dao.getProductCamTemplateId(productId);
		productId=camTemplateId;
		
		uploadDao.updateWorkFlowRecords(vo, "OCRExcelQueue", "P",vo.getStageCode());
		
		String camTemplate = dao.getParameterMSTInfo(ConstantValue.CAM_TEMPLATE_FILE);

		String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.FILE_PATH);
		String writeCamTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
		
		String readFilePath =camTemplatePath+"//"+ camTemplate+"_"+productId+".xls"; // "D://Rajman Overseas Pvt.xls";
		
		String writeFilePath =writeCamTemplatePath+"//"+ camTemplate+"_"+productId+"_"+vo.getCustRef()+".xls";
		 
		vo.setDocPath(writeFilePath);
		vo.setProductId(productId);
		File f = null;
		
		File file = null;
		
		FileInputStream in = null;
		
		FileOutputStream out=null;
		
		vo.setExcelSheetName(camTemplate);
		
		try
		{
		
			 f = new File(readFilePath);
		
			 file= new File(writeFilePath);
			 
			 in =new FileInputStream(f);
			
			 int formDataLength =(int)f.length();
			
			 out = new FileOutputStream(file);
          
			if(formDataLength>0){ 
		        byte[] outputByte = new byte[formDataLength*2];
		     
		        while(in.read(outputByte, 0, formDataLength) != -1){
		        	out.write(outputByte, 0, formDataLength);
		        }
		        status=true;
			}else{
				status=false;
			}
			
			out.close();
			f=null;
			file=null;
			
			boolean flag1 = false;
			
			if(productId.equalsIgnoreCase("BIL"))
			{
				CamTempateSheetProcess.AddRadioSheet(writeFilePath, vo, "FA & CF ");
			if(status)
				status =	systemInputData$BankingReport(writeFilePath,vo,"Banking Details");
			if(status)
				status =	systemInputData$Obligation(writeFilePath, vo,"Obligation & RTR- BIL");
			EligibilityCalculationProcess.systemInputData$EcalManual(writeFilePath, vo);
			EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "Consolidated FA & CF");
			EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "Consolidated FA & CF(Lakhs)");
			EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "FA_BALANCE_SHEET");
			EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "FA_PROFIT_AND_LOSS_SHEET");
			EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "DSCR");
			}
			else if(productId.equalsIgnoreCase("LAP") || productId.equalsIgnoreCase("LP-CP") || productId.equalsIgnoreCase("HL-LAP"))
			{
				CamTempateSheetProcess.AddRadioSheet(writeFilePath, vo, "Ratio Sheet ");
				if(status)
				{
					EligibilityCalculationForProductSpecific e = new EligibilityCalculationForProductSpecific();
				
					status =	e.systemInputData$BankingInput(writeFilePath,vo);
					ArrayList BankingProgramOutMessage = dao.generateBankingProgram(vo.getCaseId(), vo.getMakerDate(), vo.getMakerId());
					logger.info("s1---"+CommonFunction.checkNull(BankingProgramOutMessage.get(0))+"---s2---"+CommonFunction.checkNull(BankingProgramOutMessage.get(1)));
					if(CommonFunction.checkNull(BankingProgramOutMessage.get(0)).equalsIgnoreCase("S"))
					{
						String bankingProgValue = CommonFunction.checkNull(BankingProgramOutMessage.get(1));
						e.systemInputData$BankingProgram(writeFilePath, vo,bankingProgValue,"Banking Program","LAP");
					}
					ArrayList rtrValueList = dao.getRTRObligationValue(vo.getCaseId(),"RTR");
					//e.systemInputData$RTR(writeFilePath, vo, rtrValueList, "RTR", "LAP");
					
					ArrayList topUpValueList = dao.getRTRObligationValue(vo.getCaseId(),"Top Up");
					e.systemInputData$RTR(writeFilePath, vo, topUpValueList, "Top Up", "LAP");
					e.systemInputData$RTR(writeFilePath, vo, rtrValueList, "RTR_obligation", "LAP");
					
					ArrayList obligationTableList = dao.getRTRObligationValue(vo.getCaseId(),"Obligation Table");
					e.systemInputData$ObligationTable(writeFilePath, vo, obligationTableList, "obligation table", "LAP");
					EligibilityCalculationProcess.systemInputData$EcalManual(writeFilePath, vo);
					EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "Consolidated FA & CF");
					EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "Consolidated FA & CF(Lakhs)");
					EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "FA_BALANCE_SHEET (4)");
					EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "FA_PROFIT_AND_LOSS_SHEET (5)");
					EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "Banking_VAT");
					EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "Pure Rental");
				}
			}
			else if(productId.equalsIgnoreCase("HL") || productId.equalsIgnoreCase("HL-FL"))
			{
				CamTempateSheetProcess.AddRadioSheet(writeFilePath, vo, "Ratio Sheet ");
				EligibilityCalculationForProductSpecific e = 	new EligibilityCalculationForProductSpecific();
			 	e.systemInputData$BankingInput(writeFilePath, vo);
				ArrayList BankingProgramOutMessage = dao.generateBankingProgram(vo.getCaseId(), vo.getMakerDate(), vo.getMakerId());
				logger.info("s1---"+CommonFunction.checkNull(BankingProgramOutMessage.get(0))+"---s2---"+CommonFunction.checkNull(BankingProgramOutMessage.get(1)));
				if(CommonFunction.checkNull(BankingProgramOutMessage.get(0)).equalsIgnoreCase("S"))
				{
					String bankingProgValue = CommonFunction.checkNull(BankingProgramOutMessage.get(1));
					e.systemInputData$BankingProgram(writeFilePath, vo,bankingProgValue,"Banking","HL");
				}
				systemInputData$Obligation(writeFilePath, vo,"Obligation & RTR-SE");
				systemInputData$Obligation(writeFilePath, vo,"Obligation & RTR - Sal");
				EligibilityCalculationProcess.systemInputData$EcalManual(writeFilePath, vo);
				EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "Consolidated FA & CF");
				EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "Consolidated FA & CF(Lakhs)");
				EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "FA_BALANCE_SHEET (4)");
				EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "FA_PROFIT_AND_LOSS_SHEET (5)");
				EligibilityCalculationProcess.forceFullyCalculateFormula(writeFilePath, vo, "Banking_VAT");
			}
			else if(productId.equalsIgnoreCase("PL"))
			{
			if(status)
				status =	systemInputData$BankingReport(writeFilePath,vo,"Banking.");
			EligibilityCalculationProcess.systemInputData$EcalManual(writeFilePath, vo);
			}
			systemInputData$CustDetailsIndv(writeFilePath, vo);
			//Changes start for scheme upload on 17-10-15
			schemeWiseEligibilityCalcResult(readFilePath,vo,productId);
			//Changes End  for scheme upload
			ArrayList outMessage = dao.saveTargetValuesTmp(vo.getCaseId(), vo.getMakerDate(), vo.getMakerId());
			logger.info("s1---"+CommonFunction.checkNull(outMessage.get(0))+"---s2---"+CommonFunction.checkNull(outMessage.get(1)));
			if(CommonFunction.checkNull(outMessage.get(0)).equalsIgnoreCase("S"))
			{
			 flag1  = new PushDataInTargetSheet().pushDataInTargetSheet(writeFilePath, vo.getCaseId());
			 logger.info("pushDataInTargetSheet--------"+flag1);
			}
			
			
			
			if(!status)
				uploadDao.updateWorkFlowRecords(vo, "OCRExcelQueue", "F",vo.getStageCode());
			else
				uploadDao.updateWorkFlowRecords(vo, "OCRExcelQueue", "A",vo.getStageCode());
			
		}catch(Exception e){
			status = false;
			logger.info("Problem Occer in Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		finally{
	
			/*f=null;
			file=null;*/
			try{
			in.close();
			out.close();
			}catch(Exception e)
			{
				logger.info("Problem Occer in Excel Sheet "+e.getCause());
				vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			}
		}
		
		return status;
	}
	
	public static boolean systemInputData$CustDetailsIndv( String filePath,UnderwritingDocUploadVo vo)
	{
		boolean flag = true;
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		try {
			
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			String sheetName = "SystemInputData_CustDetailsIndv";
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			//ArrayList list =	dao.getSystemInputData$CustDetailsIndv(caseId, CustomerId, filePath, vo);
			
			String qryString  =  readFileColumnName(worksheet);
			
			ArrayList list =	dao.getSystemInputData$CustDetailsIndv( vo,qryString);
			
			String status = writeFile(list, worksheet);
			
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes
			
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_CustDetailsIndv Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	public static boolean systemInputData$CustDetailsCorp( String filePath,UnderwritingDocUploadVo vo)
	{
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			String sheetName = "SystemInputData_CustDetailsCorp";
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName);
			
			worksheet.setForceFormulaRecalculation(true);
			
			vo.setExcelSheetName(sheetName);// Access the worksheet, so
			
			StringBuffer bufIns = new StringBuffer();
			
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			//ArrayList list =	dao.getSystemInputData$CustDetailsIndv(caseId, CustomerId, filePath, vo);
			
			String qryString  =  readFileColumnName(worksheet);
			
			ArrayList list =	dao.getSystemInputData$CustDetailsCorp( vo,qryString);
			
			String status = writeFile(list, worksheet);
			
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes
			
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_CustDetailsCorp Excel Sheet "+e.getCause());
			logger.info("Problem Occer in SystemInputData_CustDetailsCorp Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	
	public static boolean systemInputData$CustDemographic( String filePath,UnderwritingDocUploadVo vo)
	{
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			String sheetName = "SystemInputData_CustDemographic";
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			//ArrayList list =	dao.getSystemInputData$CustDetailsIndv(caseId, CustomerId, filePath, vo);
			
			String qryString  =  readFileColumnName(worksheet);
			
			ArrayList list =	dao.getSystemInputData$CustDemographic( vo,qryString);
			
			String status = writeFile(list, worksheet);
			
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_CustDemographic Excel Sheet "+e.getCause());
			logger.info("Problem Occer in SystemInputData_CustDemographic Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	public static boolean systemInputData$Obligation( String filePath,UnderwritingDocUploadVo vo,String sheetName)
	{
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			 //sheetName = "Obligation & RTR- BIL";
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			//ArrayList list =	dao.getSystemInputData$CustDetailsIndv(caseId, CustomerId, filePath, vo);
			
			//String qryString  =  readFileColumnName(worksheet);
			
			ArrayList list =	dao.getSystemInputData$Obligation( vo,sheetName);
			logger.info("list------------"+list.size());
			String status = writeFile$Obligation(list, worksheet,sheetName);
			worksheet.setForceFormulaRecalculation(true);
			fsIP.close(); // Close the InputStream

			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_Obligation Excel Sheet "+e.getCause());
			logger.info("Problem Occer in SystemInputData_Obligation Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	public static boolean systemInputData$BS( String filePath,UnderwritingDocUploadVo vo)
	{
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			String sheetName = "SystemInputData_BS";
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			//ArrayList list =	dao.getSystemInputData$CustDetailsIndv(caseId, CustomerId, filePath, vo);
			
			String qryString  =  readFileColumnName(worksheet);
			
			ArrayList list =	dao.getSystemInputData$BS( vo,qryString);
			
			String status = writeFile(list, worksheet);
			
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_BS Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	public static boolean systemInputData$BALS( String filePath,UnderwritingDocUploadVo vo)
	{
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			String sheetName = "SystemInputData_BALS";
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			//ArrayList list =	dao.getSystemInputData$CustDetailsIndv(caseId, CustomerId, filePath, vo);
			
			String qryString  =  readFileColumnName(worksheet);
			
			ArrayList list =	dao.getSystemInputData$BALS( vo,"B");
			
			String status = writeFileForBALandPL(list, worksheet,"BALS");
			
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_BALS Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	public static boolean systemInputData$PL( String filePath,UnderwritingDocUploadVo vo)
	{
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			String sheetName = "SystemInputData_P&L";
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			ArrayList list =	dao.getSystemInputData$BALS( vo,"P");
			
			String status = writeFileForBALandPL(list, worksheet,"PL");
			
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
	
			wb.write(output_file); // write changes
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_PL Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	
	public static boolean systemInputData$OTHER( String filePath,UnderwritingDocUploadVo vo)
	{
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			String sheetName = "SystemInputData_OTHER";
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			ArrayList list =	dao.getSystemInputData$BALS( vo,"O");
			
			String status = writeFileForBALandPL(list, worksheet,"OTHER");
			
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
	
			wb.write(output_file); // write changes
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_PL Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	
	
	public static boolean systemInputData$EcalManual( String filePath,UnderwritingDocUploadVo vo)
	{
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			String sheetName = "systemInputData_EcalManual";
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			ArrayList list =	dao.getSystemInputData$EcalManual( vo);
			
			String status = writeFileForEcalManul(list, worksheet);
			
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
	
			wb.write(output_file); // write changes
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in systemInputData$EcalManual Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	public static String readFileColumnName(HSSFSheet sheet){
		StringBuffer buffer = new StringBuffer(); 
		
				//HSSFWorkbook wb = FileUploadProcessTemplete.getFileReadConnectionForXls(filePathWithName,"");
						
				
				// sheet = wb.getSheetAt(0);
				HSSFRow row;
				HSSFCell cell;
				
				int rows = 0; // No of rows
				rows = sheet.getPhysicalNumberOfRows();
				int cols = 0; // No of columns
				int tmp = 0;

			
				for (int i = 0; i < 10 || i < rows; i++) {
					row = sheet.getRow(i);
					if (row != null) {
						tmp = sheet.getRow(i).getPhysicalNumberOfCells();
						if (tmp > cols)
							cols = tmp;
					}
				}
				
				for (int r = 0; r < 1; r++) 
				{
						
						row = sheet.getRow(r);
						if (row != null) 
						{
									for (int c = 0; c < cols ; c++) 
									{
										cell = row.getCell((short) c);
										if(!CommonFunction.checkNull(cell).equalsIgnoreCase(""))
										{
											String str = CommonFunction.checkNull(cell);
											
											buffer.append(str).append(",") ;
										}
											
									}
									
						}	
				}
							
			System.out.println("query ----------"+buffer.toString());
				
		 
		return buffer.deleteCharAt(buffer.length()-1).toString();
	}
	
	
	
	
	public static String addWhiteCharacter(String str)
	{
		return str.trim().replaceAll(" +", "_");
	}
	
	public static String writeFile(ArrayList list, HSSFSheet worksheet)
	{
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row;
		HSSFCell cell;
		
		int rows = 0; // No of rows
		rows = worksheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
	
		for (int i = 0; i < 10 || i < rows; i++) {
			row = worksheet.getRow(i);
			if (row != null) {
				tmp = worksheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;
			}
		}
		
		
		for (int r = 0; r < list.size(); r++) 
		{
			sublist = (ArrayList) list.get(r);
				row = worksheet.createRow(r+2);
				if (row != null) 
				{
							for (int c = 0; c < sublist.size() ; c++) 
							{
								cell = row.createCell(c);
								
									String str =	CommonFunction.checkNull(sublist.get(c));
									cell.setCellValue(str);
								
							}
							
				}	
		}
		returnValue="S";
		
	return	returnValue;
		
	}
	
	
	
	public static String writeFileForBALandPL(ArrayList list, HSSFSheet worksheet,String sheetType)
	{
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row;
		HSSFCell cell;
		
		int rows = 0; // No of rows
		rows = worksheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
	
		for (int i = 0; i < 10 || i < rows; i++) {
			row = worksheet.getRow(i);
			if (row != null) {
				tmp = worksheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;
			}
		}
		
		
		for (int r = 0; r < list.size(); r++) 
		{
			sublist = (ArrayList) list.get(r);
			if(r==0)
			{

			String[] year =	arrangeValueForCurrentYear(CommonFunction.checkNull(sublist.get(2)),CommonFunction.checkNull(sublist.get(1)),"YEAR");
			 row = worksheet.getRow(0);
				 for(int n=2,m=0;n<=(5+2);n++,m++)
				 {
				 cell = row.getCell(n);
				 if(cell!=null)
				 cell.setCellValue(CommonFunction.checkNull(year[m]));
				 }
			 }
			
				row = worksheet.createRow(r+1);
				boolean flag =true;
				if (row != null) 
				{
						leb:	for (int c = 0; c < cols ; c++) 
							{
								cell = row.createCell(c);
								if(c==0)
								{
									if(sheetType.equalsIgnoreCase("PL"))
									cell.setCellValue(ConstantValue.FINACIAL_ANALYSIS_PL);
									else
										cell.setCellValue(ConstantValue.FINACIAL_ANALYSIS_BALS);
								}
								if(c==1)
								{
									cell.setCellValue(CommonFunction.checkNull(sublist.get(0)));
								}
								if(c>1 && flag )
								{
								String[] yearValue =	arrangeValueForCurrentYear(CommonFunction.checkNull(sublist.get(2)),CommonFunction.checkNull(sublist.get(1)),"VALUE");
								for(int i = 0;i<yearValue.length;i++)
									{
										cell.setCellValue(CommonFunction.checkNull(yearValue[i]));
										cell = row.createCell(c+i+1);
									}
									//row.removeCell(cell);
									break leb;
								}
								
								}
								
							}
							
				}	
		returnValue="S";
		
	return	returnValue;
		
	}
	
	public static String[] arrangeValueForCurrentYear(String year, String yearValue,String sortingBase)
	{
		
		String yArr[] = year.split(",");
		String vArr[] = yearValue.split(",");
		String yTmp="";
		String vTmp="";
		for(int i=0;i<yArr.length;i++)
		{
			for(int j=i+1;j<yArr.length;j++)
			{
				if (Integer.parseInt(yArr[i]) > Integer.parseInt(yArr[j])) {
					yTmp = yArr[i];
	                yArr[i] = yArr[j];
	                yArr[j] = yTmp;
	                
	                
	                vTmp = vArr[i];
	                vArr[i] = vArr[j];
	                vArr[j] = vTmp;
	               
	            }
			}	
			//System.out.println(yArr[i]+"      "+ vArr[i]);	
		}
		/*for(int i = yArr.length-1;i>=0;i--)
		{
			System.out.println(yArr[i]+"      "+ vArr[i]);	
		}*/
		return sortingBase.equalsIgnoreCase("VALUE")?vArr:yArr;
	}
	
	
	public static boolean readAndWriteFileForReport( UnderwritingDocUploadVo vo, String fileName, String timeStemps)
	{
		boolean status=false;
		FileUtilityDao dao  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		FileUplaodDao uploadDao = (FileUplaodDao) DaoImplInstanceFactory.getDaoImplInstance(FileUplaodDao.IDENTITY);
		String productId = dao.getProductId(vo.getCaseId());
		String camTemplateId = dao.getProductCamTemplateId(productId);
		//productId=camTemplateId;
		//uploadDao.updateWorkFlowRecords(vo, "OCRExcelQueue", "P",vo.getStageCode());
		String camTemplate = fileName;//dao.getParameterMSTInfo(fileName);
		String camTemplatePath   = dao.getParameterMSTInfo(ConstantValue.FILE_PATH);
		String writeCamTemplatePath   = dao.getParameterMSTInfo(ConstantValue.WRITE_FILE_PATH);
		String readFilePath = "";
		String writeFilePath="";
		logger.info("vo.getCustRef()--------"+vo.getCustRef());
		logger.info("File name---------"+fileName);
		if(fileName.contains("CAM"))
		{
			logger.info("File name In block---------"+fileName);
			readFilePath =camTemplatePath+"//"+ camTemplate+".xls"; // "D://Rajman Overseas Pvt.xls";
			writeFilePath =writeCamTemplatePath+"//"+ camTemplate+"_"+vo.getCustRef()+"_"+timeStemps+".xls";
		}
		 else
		 {
			 readFilePath =camTemplatePath+"//"+ camTemplate+".xls"; // "D://Rajman Overseas Pvt.xls";
			 writeFilePath =writeCamTemplatePath+"//"+ camTemplate+"_"+vo.getCustRef()+"_"+timeStemps+".xls";
		 }
		File f = null;
		File file = null;
		FileInputStream in = null;
		FileOutputStream out=null;
		vo.setExcelSheetName(camTemplate);
		try
		{
		
			 f = new File(readFilePath);
			 file= new File(writeFilePath);
			 in =new FileInputStream(f);
			int formDataLength =(int)f.length();
			
			 out = new FileOutputStream(file);
          
			if(formDataLength>0){ 
		        byte[] outputByte = new byte[formDataLength*2];
		     
		        while(in.read(outputByte, 0, formDataLength) != -1){
		        	out.write(outputByte, 0, formDataLength);
		        }
		        status=true;
			}else{
				status=false;
			}
			
		}catch(Exception e){
			status = false;
			logger.info("Problem Occer in Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
		eligiDAO.saveDataInExcelErrorLog(vo);
		e.printStackTrace();
		}
		finally{
			f=null;
			file=null;
			try{
			out.close();
			}catch(Exception e)
			{
				logger.info("Problem Occer in Excel Sheet "+e.getCause());
				vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			}
		}
		
		return status;
	}
	
	public static boolean systemInputData$BankingReport(String filePath,UnderwritingDocUploadVo vo,String workSheetName)
	{
		logger.info("systemInputData$BankingReport--------");
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			logger.info("workSheetName----"+workSheetName);
			vo.setExcelSheetName(workSheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(workSheetName); // Access the worksheet, so
			
			
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			ArrayList list =	dao.getDataEntryForBankingReport( vo);
			
			String status = writeDataInBankingDetails(list, worksheet);
			String btoValue = dao.getBTOValue(vo);
			 writeDataInBankingDetailsForBTO(btoValue, worksheet);
			
			worksheet.setForceFormulaRecalculation(true);
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
	
			wb.write(output_file); // write changes
			
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_Banking Report Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
		
	
		
	}
	
	
	public static String writeDataInBankingDetails(ArrayList list, HSSFSheet worksheet)
	{
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row;
		HSSFCell cell;
		
		int rows = 0; // No of rows
		rows = worksheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
	
		for (int i = 0; i < 10 || i < rows; i++) {
			row = worksheet.getRow(i);
			if (row != null) {
				tmp = worksheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;
			}
		}
		
		logger.info("list size --------"+list.size());
		int size=11+list.size()*3;
		boolean flag = false;
		for (int r = 11,s=0; r < size; s++) 
		{
			sublist = (ArrayList) list.get(s);
			logger.info("sublist = (ArrayList) list.get(r);"+sublist.size());
			//r=r+2;
			flag = true;
				row = worksheet.getRow(r);
				if (row != null) 
				{
							for (int c = 0,k=0; c < sublist.size()-1 ; c++,k++) 
							{
								String str ="";
								cell = row.getCell(c);
								//cell.getCellStyle().setLocked(false);
								//cell.setCellFormula(null);
								if(c==0)
								{
									 str =	DateUtility.getMonthInString(CommonFunction.checkNull(sublist.get(k)));
									 str = str+"-"+CommonFunction.checkNull(sublist.get(1));
									 k++;
									 logger.info(str);
									 cell.setCellValue(str);
									// cell.getCellStyle().setLocked(true);
								}
								else
								{
									 str = str+sublist.get(k);
									 cell.setCellValue(Double.parseDouble(str));
									// cell.getCellStyle().setLocked(true);
								}
								
								 logger.info(str+"row location --"+r+"  cell location--"+c);
								if(flag)
								{
									r+=3;
									flag= false;
								}
									//cell.setCellValue();
								
									worksheet.addMergedRegion(new CellRangeAddress(r,r,c,c));
									
								
							}
							
							
				}	
		}
		returnValue="S";
		
	return	returnValue;
		
	}
	
	
	public static String writeDataInBankingDetailsForBTO(String btoValue, HSSFSheet worksheet)
	{
		logger.info("writeDataInBankingDetailsForBTO----------"+btoValue);
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row;
		HSSFCell cell;
		
		int rows = 0; // No of rows
		rows = worksheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;

		
		row = worksheet.getRow(5);
		 cell = row.getCell(3);
		 cell.setCellFormula(null);
		cell.setCellValue(btoValue);
		cell.getCellStyle().setLocked(true);
		
		returnValue="S";
		
	return	returnValue;
		
	}
	public static ArrayList<Integer> getExcelRowCellCount(HSSFSheet sheet){
		StringBuffer buffer = new StringBuffer(); 
		
				//HSSFWorkbook wb = FileUploadProcessTemplete.getFileReadConnectionForXls(filePathWithName,"");
						
				
				// sheet = wb.getSheetAt(0);
				HSSFRow row;
				HSSFCell cell;
				
				int rows = 0; // No of rows
				rows = sheet.getPhysicalNumberOfRows();
				int cols = 0; // No of columns
				int tmp = 0;

			
				for (int i = 0; i < 10 || i < rows; i++) {
					row = sheet.getRow(i);
					if (row != null) {
						tmp = sheet.getRow(i).getPhysicalNumberOfCells();
						if (tmp > cols)
							cols = tmp;
					}
				}
				ArrayList<Integer> rowCount = new ArrayList<Integer>();
				rowCount.add(rows);
				rowCount.add(cols);
				return rowCount;
	}
	
	public static String writeFile$Obligation(ArrayList list, HSSFSheet worksheet,String sheetName)
	{
		String returnValue= "";
		ArrayList sublist = null;
		int j = 0;
		
		HSSFRow row;
		HSSFCell cell;
		
		int rows = 0; // No of rows
		rows = worksheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
	
		for (int i = 0; i < 10 || i < rows; i++) {
			row = worksheet.getRow(i);
			if (row != null) {
				tmp = worksheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;
			}
		}
		
		
		for (int r = 0; r < list.size(); r++) 
		{
			sublist = (ArrayList) list.get(r);
				row = worksheet.getRow(r+2);
				if (row != null) 
				{
							for (int c = 0; c < sublist.size() ; c++) 
							{
								/*if(c<=4){
									cell = row.getCell(c);
									String str =	CommonFunction.checkNull(sublist.get(c));
									cell.setCellValue(str);
								}
								else if(c>=5)
								{
									cell = row.getCell(c);
									String str =	CommonFunction.checkNull(sublist.get(c));
									Double d= 0.0;
									if(!str.equalsIgnoreCase(""))
									{
										d = Double.parseDouble(str);
										cell.setCellValue(d);
									}
									else
									cell.setCellValue(str);
								}*/
								
								 cell = row.getCell(c);
									
								 String str =	CommonFunction.checkNull(sublist.get(c));
								 if(cell.getCellType()==cell.CELL_TYPE_NUMERIC && !str.equalsIgnoreCase(""))
								 {
									 Double d= 0.0;
										if(!str.equalsIgnoreCase(""))
										{
											try
											{
												d = Double.parseDouble(str);
												BigDecimal decimalValue = new BigDecimal(str);
											    cell.setCellValue(decimalValue.doubleValue());
											}
											catch(Exception e)
											{
												cell.setCellValue(str);
											}
											
										}
										
								 }
								else
											cell.setCellValue(str);
								
								if(c==sublist.size()-1)
								{
									if("Loan Sheet".equalsIgnoreCase(sheetName))
									{
										j=21;
									}
									else 
									{
										j=14;
									}
									logger.info("Date Value-------- if Block");
									String str1 =	CommonFunction.checkNull(sublist.get(c));
									
									String[] dateValue=null;
									if(!str1.equalsIgnoreCase(""))
									{
										dateValue = str1.split(",");
										dateValue = uniqueValueFromMonthWise(dateValue);
										
										logger.info("Date Value-------- str-----"+str);
										for(int i = 0;i<dateValue.length; i++)
										{
											cell = row.getCell(j);
											cell.setCellValue(dateValue[i]);
											j++;
										}
									}
									
								}
							}
							
				}	
		}
		returnValue="S";
		
	return	returnValue;
		
	}
	

	public static boolean forceFullyCalculateFormula( String filePath,UnderwritingDocUploadVo vo,String sheetName)
	{
		logger.info("in forceFullyCalculateFormula----------");
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in forceFullyCalculateFormula Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	public static String writeFileForEcalManul(ArrayList list, HSSFSheet worksheet)
	{
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row;
		HSSFCell cell;
		
		int rows = 0; // No of rows
		rows = worksheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
	
		for (int i = 0; i < 10 || i < rows; i++) {
			row = worksheet.getRow(i);
			if (row != null) {
				tmp = worksheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;
			}
		}
		
		
		for (int r = 0; r < list.size(); r++) 
		{
			sublist = (ArrayList) list.get(r);
				row = worksheet.createRow(r+1);
				boolean flag =true;
				if (row != null) 
				{
						leb:	for (int c = 0; c < cols ; c++) 
							{
								
								
								
								if(c==0)
								{
									cell = row.createCell(c);
									cell.setCellValue(CommonFunction.checkNull(sublist.get(0))+"_"+CommonFunction.checkNull(sublist.get(1)));
										
										cell = row.createCell(c+1);
										cell.setCellValue(CommonFunction.checkNull(sublist.get(0)));
								}
								if(c==1)
								{
									cell = row.createCell(c+1);
										cell.setCellValue(CommonFunction.checkNull(sublist.get(1)));
								}
								
								if(c==2 && flag )
								{
									cell = row.createCell(c+1);
								String[] yearValue =	arrangeValueForCurrentYear(CommonFunction.checkNull(sublist.get(3)),CommonFunction.checkNull(sublist.get(2)),"VALUE");
								for(int i = yearValue.length-1,j=0;i>=0;i--,j++)
									{
										/*cell.setCellValue(CommonFunction.checkNull(yearValue[i]));*/
								
										try{
											
											Double d =  Double.parseDouble(yearValue[i]);
											cell.setCellType(Cell.CELL_TYPE_NUMERIC);
											cell.setCellValue(d);
											logger.info("In Try ..................");
											}
											catch(Exception e)
											{
												logger.info("In Catch ..................");
												cell.setCellValue(CommonFunction.checkNull(yearValue[i]));
											}
										cell = row.createCell(c+j+2);
									}
									//row.removeCell(cell);
									break leb;
								}
								
								}
								
							}
							
				}	
		returnValue="S";
		
	return	returnValue;
		
	}
	
	public static void main(String[] args) {
		//readFileColumnName("D://cam//CapFirst_eCal_CAM_Template_v0.1");
		String value = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
		String year = "2016,2016,2017,2013,2014,2015,2013,2013,2014,2015,2016,2017,2015,2016,2017,2013,2014,2013,2014,2015,2016,2017,2015,2016,2017,2013,2014,2017,2017,2013,2014,2015,2016,2014,2014,2015,2016,2017,2013,2016,2017,2013,2014,2015,2013,2014,2015,2016,2017,2016,2017,2013,2014,2015,2013,2014,2015,2016,2017,2015,2015,2016,2017,2013,2014,2017,2013,2014,2015,2016,2014,2015,2016,2017,2013,2017,2013,2014,2015,2016,2014,2015,2016,2017,2013";
		arrangeValueForCurrentYear(year, value,"VALUE");
	}
	public static boolean schemeWiseEligibilityCalcResult( String filePath,UnderwritingDocUploadVo undvo,String productId)
	{
		
		boolean status=false;
		
		ArrayList in = new ArrayList();
    try{
    	File file1 = new File(filePath);
    	FileInputStream file = new FileInputStream(file1);
    	EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		System.out.println(filePath+"--"+filePath);
		HSSFWorkbook workbook = new HSSFWorkbook(file);
	
		HSSFSheet sheet = workbook.getSheet("SystemOutputsheet");
		HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(workbook);
		//HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
		((HSSFSheet) sheet).setForceFormulaRecalculation(true);
		sheet.isActive();
		file.close();
		Row row;
		Cell cell;
		FundFlowDownloadUploadVo vo=null;

		int rows = 0; // No of rows
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;
		int lineCounter = 0;
		int readingFileCount=0;
		 List data = new ArrayList();
		ArrayList<FundFlowDownloadUploadVo> list =new ArrayList();

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
	
		for (int j = 0; j < 10 || j < rows; j++) {
			row = sheet.getRow(j);
			if (row != null) {
				tmp = sheet.getRow(j).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;

			}
		}
		
		for (int k = 1; k < rows; k++) {
			row = sheet.getRow(k);						
			if(row!=null)
			{
			vo=new FundFlowDownloadUploadVo();
			vo.setCaseId(CommonFunction.checkNull(undvo.getCaseId()));
			
			cell=row.getCell(1);
			String strValue = null;
			Double amountValue = null;
			try{
				
				if (cell!=null) {
					switch (evaluator.evaluateInCell(cell).getCellType()) {
					case HSSFCell.CELL_TYPE_BOOLEAN:
					System.out.println("B"+cell.getBooleanCellValue());
					break;
					case HSSFCell.CELL_TYPE_NUMERIC:
					System.out.println("N"+cell.getNumericCellValue());
					break;
					case HSSFCell.CELL_TYPE_STRING:
					System.out.println("S"+cell.getRichStringCellValue().getString());
					break;
					case HSSFCell.CELL_TYPE_BLANK:
					break;
					case HSSFCell.CELL_TYPE_ERROR:
					System.out.println("E"+cell.getErrorCellValue());
					break;
					 
					// CELL_TYPE_FORMULA will never occur
					case HSSFCell.CELL_TYPE_FORMULA:
						System.out.println("E--------");
						System.out.println("E"+cell.getCachedFormulaResultType());
					break;
					}
					}
				
				
				
				
				
					if(cell !=null && cell.getCellType()==Cell.CELL_TYPE_BLANK){
						strValue = "";
					}else if(cell !=null && cell.getCellType()==Cell.CELL_TYPE_FORMULA)
					{
						logger.info("before -------- cachedFormulaResultType--------");
						int cachedFormulaResultType  = cell.getCachedFormulaResultType();
						logger.info("cachedFormulaResultType--------"+cachedFormulaResultType);
						if(Cell.CELL_TYPE_NUMERIC==cachedFormulaResultType)
						{
							amountValue = cell.getNumericCellValue();
							logger.info("amountValue--------"+amountValue);
							if(amountValue!=null){
								strValue = new BigDecimal(amountValue).setScale(2, RoundingMode.FLOOR).toPlainString();
								logger.info("strValue--------"+strValue);
							}
						}
						else
						{
							strValue = cell.getStringCellValue().replaceAll("'", "");
							logger.info("else block strValue--------"+strValue);
						}
					}
					else if(cell !=null && cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
						amountValue = cell.getNumericCellValue();
						logger.info("CELL_TYPE_NUMERIC block amountValue--------"+amountValue);
						if(amountValue!=null){
							strValue = new BigDecimal(amountValue).setScale(2, RoundingMode.FLOOR).toPlainString();
						}
					}else if(cell !=null){
						strValue = cell.toString();
					}
					
					
					if (strValue != null) {
						strValue= strValue.trim();
						
					} else {
						strValue=null;
						
					}
					logger.info("before  Double.parseDouble(strValue)--------"+strValue);
					  double d = Double.parseDouble(strValue);  
					 
				}catch(Exception e)
				{
					
					logger.info("Target Sheet Name--------"+(CommonFunction.checkNull(row.getCell(0))));
					logger.info("cell.getCellType()---------"+cell.getCellType());
					logger.info("strValue--------"+strValue);
					strValue="NA";		
					
				}
			logger.info("row.getCell(1)-----"+strValue);
			vo.setLoanAmount(CommonFunction.checkNull(strValue));
			vo.setScheme(CommonFunction.checkNull(row.getCell(0)));
			vo.setType(CommonFunction.checkNull(row.getCell(2)));
			vo.setProduct(CommonFunction.checkNull(productId));
			list.add(vo);
			}
		}
		if(list.size()>0)
		{
			status=eligiDAO.insertSchemeWiseEligibilityAmount(list);
			
		}
		
    }
 catch (Exception e) {
			e.printStackTrace();
		}
    finally{
    	
    }
		return status;
	}
	public static String[] uniqueValueFromMonthWise(String[] arr)
	{
		logger.info("uniqueValueFromMonthWise---------");
		ArrayList<String> arrayList = new ArrayList<String> ();
		String firstArrValue="";
		String secondArrValue="";
		String subFirstArrValue="";
		String subSecondArrValue="";
		
		for(int i = 0;i<arr.length; i++)
		{
			firstArrValue=arr[i];
			subFirstArrValue = firstArrValue.substring(3);
			
			for(int j = i+1;j<arr.length; j++)
			{
				
				secondArrValue=arr[j];
				
				subSecondArrValue = secondArrValue.substring(3);
				if(!subSecondArrValue.equalsIgnoreCase(subFirstArrValue))
				{
					i=j-1;
					break;
				}
				else
					firstArrValue=secondArrValue;
			}
			if(!arrayList.contains(firstArrValue))
			arrayList.add(firstArrValue);
		}
		logger.info("arrayList.size()---"+arrayList.size());
		System.out.println(arrayList);
		String[] strArr = new String[arrayList.size()];
		strArr = arrayList.toArray(strArr);
		return strArr;
	}
	
	public static boolean systemInputData$LoanSheetObligation( String filePath,UnderwritingDocUploadVo vo,String sheetName)
	{
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		boolean flag = true;
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
					
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			worksheet.setForceFormulaRecalculation(true);
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
					
			ArrayList list =	dao.getSystemInputData$LoanSheetObligation( vo,sheetName);
			logger.info("list------------"+list.size());
			String status = writeFile$Obligation(list, worksheet,sheetName);
			worksheet.setForceFormulaRecalculation(true);
			fsIP.close(); // Close the InputStream

			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in SystemInputData_LoanSheetObligation Excel Sheet "+e.getCause());
			logger.info("Problem Occer in SystemInputData_LoanSheetObligation Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	
	/*public static void main(String[] args) {
		String value = "02-11-2015,30-11-2015,30-11-201530-11-2015,01-12-2015,10-11-2015";
		String arr[] = value.split(",");
		uniqueValueFromMonthWise(arr);
	}*/
}
