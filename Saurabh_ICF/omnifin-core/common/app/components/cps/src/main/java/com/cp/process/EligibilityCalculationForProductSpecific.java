package com.cp.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.EligibilityCalculationProcessDao;
import com.cp.dao.FileUtilityDao;
import com.cp.vo.UnderwritingDocUploadVo;

public class EligibilityCalculationForProductSpecific {
	private static final Logger logger = Logger.getLogger(EligibilityCalculationForProductSpecific.class.getName());
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	public boolean systemInputData$BankingInput(String writeFilePath,UnderwritingDocUploadVo vo)
	{
		boolean flag = true;
		logger.info("systemInputData$BankingInput--------");
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(writeFilePath); // Read the spreadsheet that needs to be
			
			String sheetName = "Banking Input";
			
			vo.setExcelSheetName(sheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(sheetName); // Access the worksheet, so
			
			
			StringBuffer bufIns = new StringBuffer();
			
			EligibilityCalculationProcessDao dao1 = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
			ArrayList list =	dao1.getDataForBankingInput( vo);
			
			 writeDataInBankingInput(list, worksheet);
			
			worksheet.setForceFormulaRecalculation(true);
			
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(writeFilePath)); // Open FileOutputStream to write
	
			wb.write(output_file); // write changes
			
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in systemInputData$BankingInput Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
		
	}
	
	public boolean systemInputData$BankingProgram(String writeFilePath,UnderwritingDocUploadVo vo, String bankingProgramValue,String workSheetName,String productType)
	{
		boolean flag = true;
		logger.info("systemInputData$BankingInput$BankingReport--------");
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(writeFilePath); // Read the spreadsheet that needs to be
			
			
			vo.setExcelSheetName(workSheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(workSheetName); // Access the worksheet, so
			
			
			
			StringBuffer bufIns = new StringBuffer();
			
			//EligibilityCalculationProcessDao dao1 = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			
		//	ArrayList list =	dao1.getDataForBankingInput( vo);
			
			writeDataInBankingProgram(bankingProgramValue, worksheet,productType);
			
			worksheet.setForceFormulaRecalculation(true);
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(writeFilePath)); // Open FileOutputStream to write
	
			wb.write(output_file); // write changes
			
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in systemInputData$BankingInput Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	public void writeDataInBankingInput(ArrayList list, HSSFSheet worksheet)
	{
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row;
		HSSFCell cell;
		
		int rows = 0; // No of rows
		int cols = 0; // No of columns
		ArrayList<Integer> rowCellCountValue = EligibilityCalculationProcess.getExcelRowCellCount(worksheet);
		rows = rowCellCountValue.get(0);
		cols = rowCellCountValue.get(1);
		logger.info("list size --------"+list.size());
		int size=list.size();
		boolean flag = false;
		
				row = worksheet.getRow(35);
				if (row != null) 
				{
							
								for(int i = 0; i<list.size();i++)
								{
									sublist = (ArrayList)list.get(i);
									if(sublist.size()>0)
									{
									cell = row.getCell(Integer.parseInt(CommonFunction.checkNull(sublist.get(2))));
									cell.getCellStyle().setLocked(false);
									cell.setCellFormula(null);
									cell.setCellValue(Double.parseDouble(CommonFunction.checkNull(sublist.get(0))));
									cell.getCellStyle().setLocked(true);
									}
									
								}
							
							
							
				}	
	}
	
	
	public void writeDataInBankingProgram(String bankingProgramValue, HSSFSheet worksheet,String productType)
	{
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row;
		HSSFCell cell;
		
		int rows = 0; // No of rows
		int cols = 0; // No of columns
		ArrayList<Integer> rowCellCountValue = EligibilityCalculationProcess.getExcelRowCellCount(worksheet);
		rows = rowCellCountValue.get(0);
		cols = rowCellCountValue.get(1);
		boolean flag = false;
		int rowLocation = 0;
		int cellLocation = 0;
		if(productType.equalsIgnoreCase("LAP"))
		{
			rowLocation=15;
			cellLocation=5;
		}
		else if(productType.equalsIgnoreCase("HL"))
		{
			rowLocation=16;
			cellLocation=5;
		}
		
		row = worksheet.getRow(rowLocation);
		if (row != null) 
		{
							row.getCell(cellLocation).getCellStyle().setLocked(false);
							
							cell = row.getCell(cellLocation);
							cell.setCellFormula(null);
							cell.setCellValue(Double.parseDouble(bankingProgramValue));
							row.getCell(cellLocation).getCellStyle().setLocked(true);
		}
	}
	public boolean systemInputData$RTR(String writeFilePath,UnderwritingDocUploadVo vo, ArrayList rtrValueList,String workSheetName,String productType)
	{
		boolean flag = true;
		logger.info("systemInputData$RTR---------");
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(writeFilePath); // Read the spreadsheet that needs to be
			
			
			vo.setExcelSheetName(workSheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(workSheetName); // Access the worksheet, so
			
			
			
			StringBuffer bufIns = new StringBuffer();
			if(workSheetName.equalsIgnoreCase("RTR-DC"))
			{
				logger.info("In RTR-DC Block--"+workSheetName);
			writeDataInRTR(rtrValueList, worksheet,productType);
			}
			else
			{
				logger.info("In RTR Top up--"+workSheetName);
				writeDataInTopUp(rtrValueList, worksheet,productType);
			}
			worksheet.setForceFormulaRecalculation(true);
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(writeFilePath)); // Open FileOutputStream to write
	
			wb.write(output_file); // write changes
			
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in systemInputData$RTR Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	
	
	public boolean systemInputData$ObligationTable(String writeFilePath,UnderwritingDocUploadVo vo, ArrayList obligationTableList,String workSheetName,String productType)
	{
		boolean flag = true;
		logger.info("systemInputData$ObligationTable---------");
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		try {
			FileUtilityDao doa  = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
			
			FileInputStream fsIP = new FileInputStream(writeFilePath); // Read the spreadsheet that needs to be
			
			
			vo.setExcelSheetName(workSheetName);
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			HSSFSheet worksheet = wb.getSheet(workSheetName); // Access the worksheet, so
			
			
			
			StringBuffer bufIns = new StringBuffer();
			
			writeDataInObligationTableForLAP(obligationTableList, worksheet,productType);
			
			worksheet.setForceFormulaRecalculation(true);
			fsIP.close(); // Close the InputStream
			
			fsIP=null;
			
			FileOutputStream output_file = new FileOutputStream(new File(writeFilePath)); // Open FileOutputStream to write
	
			wb.write(output_file); // write changes
			
			output_file.close(); // close the stream
		} catch (Exception e) {
			flag = false;
			logger.info("Problem Occer in systemInputData$BankingInput Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
	}
	
	
	
	
	public void writeDataInRTR(ArrayList rtrValueList, HSSFSheet worksheet,String productType)
	{
		logger.info("writeDataInRTR--------");
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row =null ;
		HSSFCell cell = null;
		
		int rows = 0; // No of rows
		int cols = 0; // No of columns
		ArrayList<Integer> rowCellCountValue = EligibilityCalculationProcess.getExcelRowCellCount(worksheet);
		rows = rowCellCountValue.get(0);
		cols = rowCellCountValue.get(1);
		logger.info("rows-----"+rows+"---------cols-----"+cols);
		boolean flag = false;
		int rowLocation = 0;
		
		int i=0,j=0;
		int c=0;
		int sublistSize=0;
		//int listSize=rtrValueList.size()>=30?30+4:rtrValueList.size()+4;
		int listSize = rtrValueList.size()+2;
		ArrayList rtrSubValueList = new ArrayList();
		for(rowLocation=2;rowLocation<listSize;rowLocation++,i++)
		{
		row = worksheet.getRow(rowLocation);
			if (row != null) 
			{
				rtrSubValueList = (ArrayList)rtrValueList.get(i);
				sublistSize = rtrSubValueList.size();
				for(j=0,c=0;j<sublistSize;j++,c++)
				{
					if(j!=10 )
					{	 
					cell = row.getCell(j);
						cell.setCellFormula(null);
						
						 String value = CommonFunction.checkNull(rtrSubValueList.get(c));
							 if((isNumeric(value) ||cell.getCellType()==cell.CELL_TYPE_NUMERIC) && !value.equalsIgnoreCase(""))
							 {
								
								 BigDecimal decimalValue = new BigDecimal(value);
							     cell.setCellValue(decimalValue.doubleValue());
							 }
							else
								cell.setCellValue(value);
							 ((HSSFSheet) worksheet).setForceFormulaRecalculation(true);
						} 
						
						
				}
								
			}
		}
	}
	
	
	public void writeDataInTopUp(ArrayList rtrValueList, HSSFSheet worksheet,String productType)
	{
		logger.info("writeDataInTopUp--------");
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row =null ;
		HSSFCell cell = null;
		
		int rows = 0; // No of rows
		int cols = 0; // No of columns
		ArrayList<Integer> rowCellCountValue = EligibilityCalculationProcess.getExcelRowCellCount(worksheet);
		rows = rowCellCountValue.get(0);
		cols = rowCellCountValue.get(1);
		//logger.info("rows-----"+rows+"---------cols-----"+cols);
		boolean flag = false;
		int rowLocation = 0;
		
		int i=0,j=0;
		int c=0;
		int sublistSize=0;
		int listSize=rtrValueList.size()>=30?30+4:rtrValueList.size()+4;
		ArrayList rtrSubValueList = new ArrayList();
		for(rowLocation=4;rowLocation<listSize;rowLocation++,i++)
		{
		row = worksheet.getRow(rowLocation);
			if (row != null) 
			{
				rtrSubValueList = (ArrayList)rtrValueList.get(i);
				sublistSize = rtrSubValueList.size()+9;
				for(j=9,c=0;j<sublistSize;j++,c++)
				{
					if(j!=20 && j!=21 && j!=22 && j!=24)
					{
					 cell = row.getCell(j);
					 cell.setCellFormula(null);
					 String value = CommonFunction.checkNull(rtrSubValueList.get(c));
					 
					 if((isNumeric(value) ||cell.getCellType()==cell.CELL_TYPE_NUMERIC) && !value.equalsIgnoreCase(""))
					 {
						 logger.info("writeDataInTopUp--------"+cell.CELL_TYPE_NUMERIC);
						 BigDecimal decimalValue = new BigDecimal(value);
					     cell.setCellValue(decimalValue.doubleValue());
					     logger.info("writeDataInTopUp--------"+decimalValue.doubleValue());
					 }
					else
					{
						logger.info("writeDataInTopUp   else--------"+value);
						cell.setCellValue(value);
					}
					}
					 ((HSSFSheet) worksheet).setForceFormulaRecalculation(true);
					 
				}
								
			}
		}
	}
	
	
	public void writeDataInObligationTableForLAP(ArrayList obligationValueList, HSSFSheet worksheet,String productType)
	{
		String returnValue= "";
		ArrayList sublist = null;
		
		HSSFRow row =null ;
		HSSFCell cell = null;
		
		int rows = 0; // No of rows
		int cols = 0; // No of columns
		ArrayList<Integer> rowCellCountValue = EligibilityCalculationProcess.getExcelRowCellCount(worksheet);
		rows = rowCellCountValue.get(0);
		cols = rowCellCountValue.get(1);
		boolean flag = false;
		int rowLocation = 0;
		int i=0,j=0;
		int c=0;
		int sublistSize=0;
		int listSize=obligationValueList.size()>=30?30+2:obligationValueList.size()+2;
		ArrayList obligationSubValueList = new ArrayList();
		for(rowLocation=2;rowLocation<listSize;rowLocation++,i++)
		{
			//logger.info("rowLocation---"+rowLocation+"    i------"+i+" obligationValueList.size()   "+obligationValueList.size());
		row = worksheet.getRow(rowLocation);
		//logger.info("row---"+row);
			if (row != null) 
			{
				obligationSubValueList = (ArrayList)obligationValueList.get(i);
				sublistSize = obligationSubValueList.size()+1;
				for(j=1,c=0;j<sublistSize;j++,c++)
				{
					 cell = row.getCell(j);
					
					 String value = CommonFunction.checkNull(obligationSubValueList.get(c));
					 if(cell.getCellType()==cell.CELL_TYPE_NUMERIC && !value.equalsIgnoreCase(""))
					 {
						
						 Double d= 0.0;
							if(!value.equalsIgnoreCase(""))
							{
								try
								{
									d = Double.parseDouble(value);
									BigDecimal decimalValue = new BigDecimal(value);
								    cell.setCellValue(decimalValue.doubleValue());
								}
								catch(Exception e)
								{
									cell.setCellValue(value);
								}
								
							}
					 }
					else
								cell.setCellValue(value);
					 
					 if(c==obligationSubValueList.size()-1)
						{
							//logger.info("Date Value-------- if Block");
							String str =	CommonFunction.checkNull(obligationSubValueList.get(c));
							int k = 13;
							String[] dateValue=null;
							if(!str.equalsIgnoreCase(""))
							{
								dateValue = str.split(",");
								dateValue = EligibilityCalculationProcess.uniqueValueFromMonthWise(dateValue);
								logger.info("Date Value-------- str-----"+str);
								for(int l = 0;l<dateValue.length; l++)
								{
									logger.info("Date Value-------- "+dateValue[l]);
									cell = row.getCell(k);
									cell.setCellValue(dateValue[l]);
									k++;
								}
							}
						}
							
				}
								
			}
		}
	}
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	public static void main(String[] args) {
		ArrayList list = new ArrayList();
		ArrayList subList = null;
		
		for(int i=0;i<27;i++)
		{
			subList =  new ArrayList();
			
				
				subList.add(i+"--+sdf");
				subList.add("Home");
				subList.add(1000);
				subList.add(5);
				subList.add(50);
				subList.add(40);
				subList.add(100);
				subList.add("5");
				subList.add("Y");
				subList.add("Y2");
				
				subList.add(98);
				
				
				
				
			
		list.add(subList);
		}
		
		new EligibilityCalculationForProductSpecific().systemInputData$RTR("D:/NOMEN CLATURE/CapFirst_eCal_CAM_Template_v0.1_LAP - Copy (2).xls", null, list, "Top ", "LAP");
	}
}
