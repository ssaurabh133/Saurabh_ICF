package com.cp.eCalMacroPOC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.EligibilityCalculationProcessDao;
import com.cp.vo.PushDataInTargetSheetVo;

public class PushDataInTargetSheet 
{
	private static final Logger logger = Logger.getLogger(PushDataInTargetSheet.class.getName());

	HSSFSheet worksheet = null; // Access the worksheet, so
	
	/*public  boolean pushDataInTargetSheet( String filePath,PushDataInTargetSheetVo vo,String caseId)*/
	public  boolean pushDataInTargetSheet( String filePath,String caseId)
	{
		logger.info("in pushDataInTargetSheet");
		boolean flag = true;
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		
		try 
		{
			logger.info("filePath-------"+filePath);
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			String sheetName = null;
			
			ArrayList<PushDataInTargetSheetVo> valueList = 	eligiDAO.getTargetFieldValueData(caseId);
			
			int size = valueList!=null?valueList.size():0;
			
			PushDataInTargetSheetVo dataInTargetSheetVo = null;
			
			for(int i=0;i<size;i++)
			{
				 dataInTargetSheetVo = valueList.get(i);
				 //logger.info("dataInTargetSheetVo----------"+dataInTargetSheetVo);
				 sheetName = dataInTargetSheetVo.getTargetWorksheetName();
				
				 logger.info("sheetName-------"+sheetName);
				 
				 worksheet = wb.getSheet(sheetName);
				
				 String targetWorksheetCellLocation  = dataInTargetSheetVo.getTargetWorksheetCell();
				
				 ArrayList<Integer> rowCellLocation = getTargetWorksheetCellLocation(targetWorksheetCellLocation);
				
				 pushDataInWorkSheet(rowCellLocation,worksheet,dataInTargetSheetVo);
			}
			
			((HSSFSheet) worksheet).setForceFormulaRecalculation(true);
			
			fsIP.close();
			
			fsIP= null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes

			output_file.close(); // close the stream
		} catch (Exception e) {
			
			flag = false;
			
			logger.info("Problem Occer in pushDataInTargetSheet Excel Sheet "+e.getMessage());
			e.printStackTrace();
			/*vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();*/
			}
		return flag;
	}
	
	

	
	public void pushDataInWorkSheet(ArrayList<Integer> rowColsLocation,HSSFSheet sheet,PushDataInTargetSheetVo vo)throws IOException
	{
		HSSFRow row;
		HSSFCell cell;
		int rows = 0; // No of rows
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;
		int rowLocationStart = 0;
		int rowLocationEnd = 0;
		int cellLocation = 0;
		
		if(rowColsLocation.size()>2)
		{
			
			rowLocationStart = rowColsLocation.get(0);
			rowLocationEnd = rowColsLocation.get(1);
			cellLocation = rowColsLocation.get(2);
			/*for (int r = rowLocationStart; r < rowLocationEnd; r++) 
			{
					row = sheet.getRow(r);
					if (row != null) 
					{
									cell = row.getCell(cellLocation);
									cell.setCellValue(vo.getValue());
					}	
			}*/
			
			row = sheet.getRow(rowLocationStart);
			
			if (row != null) 
			{
				cell = row.getCell( cellLocation);
				String value = CommonFunction.checkNull(vo.getValue());
				
				logger.info("Push in target Sheet value-------------"+value+"^^^^^^^^^^-----------");
				
				logger.info("cell.getCellType 11111111 : "+cell.getCellType());
				
				if(!value.equalsIgnoreCase(""))
				{
					if(cell.getCellType()==cell.CELL_TYPE_NUMERIC )
					{
						BigDecimal decimalValue = new BigDecimal(value);
						cell.setCellValue(decimalValue.doubleValue());
					}
					else
						cell.setCellValue(value);
				}
				((HSSFSheet) worksheet).setForceFormulaRecalculation(true);
			}
			sheet.addMergedRegion(new CellRangeAddress(rowLocationStart,rowLocationEnd,cellLocation,cellLocation));
		}
		else
		{
			rowLocationStart = rowColsLocation.get(0);
			cellLocation = rowColsLocation.get(1);
			row = sheet.getRow(rowLocationStart);
			if (row != null) 
			{
				cell = row.getCell( cellLocation);
				
				String value = CommonFunction.checkNull(vo.getValue());
				
				logger.info("Push in target Sheet value-------------"+value+"^^^^^^^----------");
				logger.info("cell------------"+cell+" ----------cellLocation---"+cellLocation+"--------rowLocationStart----"+rowLocationStart);
				
				logger.info("cell.getCellType 22222222 : "+cell.getCellType());
				
				if(!value.equalsIgnoreCase(""))
				{
					if(cell.getCellType()==cell.CELL_TYPE_NUMERIC )
					{
						BigDecimal decimalValue = new BigDecimal(value);
						cell.setCellValue(decimalValue.doubleValue());
					}
					else
						cell.setCellValue(value);
				}
				((HSSFSheet) worksheet).setForceFormulaRecalculation(true);
			}
		}
	}
	
	
	public ArrayList<Integer> getTargetWorksheetCellLocation(String targetWorksheetCellLocation)
	{
		ArrayList<Integer> rowCellLocation = new ArrayList<Integer>();
		String[] location =null;
		if(targetWorksheetCellLocation.contains(":"))
		{
			targetWorksheetCellLocation = targetWorksheetCellLocation.replace(":","");
			location = targetWorksheetCellLocation.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
			int rowLocatinStart  = Integer.parseInt(location[1]);
			rowCellLocation.add(rowLocatinStart-1);
			int rowLocatinEnd  = Integer.parseInt(location[3]);
			rowCellLocation.add(rowLocatinEnd-1);
			int cellLocation =Character.getNumericValue(location[0].charAt(0))-10;
			rowCellLocation.add(cellLocation);// cell location find
			
		}
		else
		{
			location = 	targetWorksheetCellLocation.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
			int rowLocatin  = Integer.parseInt(location[1]);
			rowCellLocation.add(rowLocatin-1); // row location find
			int cellLocation =Character.getNumericValue(location[0].charAt(0))-10;
			rowCellLocation.add(cellLocation);// cell location find
			
		}
		
		
		return rowCellLocation;
		
	}
	
	/*public static void main(String[] args) {
		String filePath = "D:/NOMEN CLATURE/CapFirst_eCal_CAM_Template_v0.1_BIL.xls";
		
		
		PushDataInTargetSheetVo vo = new PushDataInTargetSheetVo();
		vo.setTargetWorksheetCell("C3");
		vo.setTargetWorksheetName("BIL-Net Profit");
		vo.setValue("A");
		
		PushDataInTargetSheetVo vo1 = new PushDataInTargetSheetVo();
		vo1.setTargetWorksheetCell("C4");
		vo1.setTargetWorksheetName("BIL-Net Profit");
		vo1.setValue("100000");
		
		PushDataInTargetSheetVo vo2 = new PushDataInTargetSheetVo();
		vo2.setTargetWorksheetCell("C5");
		vo2.setTargetWorksheetName("BIL-Net Profit");
		vo2.setValue("Fixed");
		
		PushDataInTargetSheetVo vo3 = new PushDataInTargetSheetVo();
		vo3.setTargetWorksheetCell("C6");
		vo3.setTargetWorksheetName("BIL-Net Profit");
		vo3.setValue("20.25%");
		
		PushDataInTargetSheetVo vo4 = new PushDataInTargetSheetVo();
		vo4.setTargetWorksheetCell("C7");
		vo4.setTargetWorksheetName("BIL-Net Profit");
		vo4.setValue("42");
		
		PushDataInTargetSheetVo vo5 = new PushDataInTargetSheetVo();
		vo5.setTargetWorksheetCell("E2");
		vo5.setTargetWorksheetName("BIL-Net Profit");
		vo5.setValue("782");
		
		PushDataInTargetSheetVo vo6 = new PushDataInTargetSheetVo();
		vo6.setTargetWorksheetCell("C2");
		vo6.setTargetWorksheetName("BIL-Net Profit");
		vo6.setValue("Noida");
		
		PushDataInTargetSheetVo vo7 = new PushDataInTargetSheetVo();
		vo7.setTargetWorksheetCell("M12:M14");
		vo7.setTargetWorksheetName("Banking Details");
		vo7.setValue("4514");
		
		PushDataInTargetSheetVo vo6 = new PushDataInTargetSheetVo();
		vo6.setTargetWorksheetCell("C16:C22");
		vo6.setTargetWorksheetName("BIL-Net Profit");
		vo6.setValue("782");
		
		new PushDataInTargetSheet().pushDataInTargetSheet(filePath, vo7,"1");
		new PushDataInTargetSheet().pushDataInTargetSheet(filePath, vo,"1");
		new PushDataInTargetSheet().pushDataInTargetSheet(filePath, vo1,"1");
		new PushDataInTargetSheet().pushDataInTargetSheet(filePath, vo2,"1");
		new PushDataInTargetSheet().pushDataInTargetSheet(filePath, vo3,"1");
		new PushDataInTargetSheet().pushDataInTargetSheet(filePath, vo4,"1");
		new PushDataInTargetSheet().pushDataInTargetSheet(filePath, vo5,"1");
		
		
	}*/
/*	public boolean pushDataInBankingReport(String filePath,String caseId,String rowCellLocation,String targetSheetName)
	{
		logger.info("in pushDataInTargetSheet");
		boolean flag = true;
		EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		try {
			logger.info("filePath-------"+filePath);
			FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
			
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
			
			String sheetName = null;
			
			HSSFSheet worksheet = null; // Access the worksheet, so
			
			ArrayList<PushDataInTargetSheetVo> valueList = 	eligiDAO.getTargetFieldValueData(caseId);
			
			int size = valueList!=null?valueList.size():0;
			
			PushDataInTargetSheetVo dataInTargetSheetVo = null;
			
			for(int i=0;i<size;i++)
			{
				 dataInTargetSheetVo = valueList.get(i);
				 
				 sheetName = dataInTargetSheetVo.getTargetWorksheetName();
				
				 logger.info("sheetName-------"+sheetName);
				 
				 worksheet = wb.getSheet(sheetName);
				
				 String targetWorksheetCellLocation  = dataInTargetSheetVo.getTargetWorksheetCell();
				
				 ArrayList<Integer> rowCellLocation = getTargetWorksheetCellLocation(targetWorksheetCellLocation);
				
				 pushDataInWorkSheet(rowCellLocation,worksheet,dataInTargetSheetVo);
			}
			
			fsIP.close();
			
			fsIP= null;
			
			FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
										// updates
			wb.write(output_file); // write changes

			output_file.close(); // close the stream
		} catch (Exception e) {
			
			flag = false;
			
			logger.info("Problem Occer in pushDataInTargetSheet Excel Sheet "+e.getMessage());
			e.printStackTrace();
			vo.setErrorMsg(e.getMessage());
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
			}
		return flag;
		
		
		
		boolean flag = false;
	//	ArrayList list = getTargetWorksheetCellLocation(rowCellLocation);
		// pushDataInWorkSheet(rowCellLocation,worksheet,dataInTargetSheetVo);
		return false;
		
	}*/
}
