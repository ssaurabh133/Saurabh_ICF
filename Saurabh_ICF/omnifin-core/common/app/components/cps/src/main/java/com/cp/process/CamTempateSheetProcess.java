package com.cp.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.EligibilityCalculationProcessDao;
import com.cp.dao.FileUtilityDao;
import com.cp.vo.UnderwritingDocUploadVo;

public class CamTempateSheetProcess {
	private static final Logger logger = Logger.getLogger(CamTempateSheetProcess.class.getName());
	public static boolean AddRadioSheet(String filePath,UnderwritingDocUploadVo vo, String creationSheetName) 
	{
		logger.info("in AddRadioSheet----------");
		EligibilityCalculationProcessDao dao = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		
		ArrayList  coList = dao.getCoapplCustomerListWithSequence(vo);
		ArrayList  gauList = dao.getGaurantorCustomerListWithSequence(vo);
		
		int coListSize = coList==null?0:coList.size();
		int gauListSize = gauList==null?0:gauList.size();
		boolean status = true;
		try {
		FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
		HSSFWorkbook wb = new HSSFWorkbook(fsIP); // Access the workbook
		
		String ratioWorkSheet1Name = creationSheetName+"1";
		HSSFSheet ratioWorkSheet1 = wb.getSheet(ratioWorkSheet1Name);
		int ratioSheetsRequired = coListSize+gauListSize;
		int i = 2;
		for (int ratioSheetCurrentindex = 2; ratioSheetCurrentindex <= ratioSheetsRequired; ratioSheetCurrentindex++) {
			String tempSheetName=creationSheetName+ratioSheetCurrentindex;
					
					if(wb.getSheet(tempSheetName)!=null){
						
						//writeSystemParameters$Targets(wb,filePath, vo, i++);
						//Skip current sheet no since already existing. Continue to next iteration.
						continue;
					}
					
					Sheet tempWorksheet = wb.cloneSheet(wb
							.getSheetIndex(ratioWorkSheet1));
					wb.setSheetName(wb.getSheetIndex(tempWorksheet),
							creationSheetName + ratioSheetCurrentindex);
					//wb.cloneSheet(32);
					//writeSystemParameters$Targets(wb,filePath, vo, i++);
				} 
				fsIP.close(); // Close the InputStream
				fsIP=null;
	
				FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
											// updates
	
				wb.write(output_file); // write changes
	
				output_file.close(); // close the stream
		}catch (Exception e) {
			status = false;
			logger.info("Problem Occer  AddRadioSheet in Excel Sheet "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
		eligiDAO.saveDataInExcelErrorLog(vo);
		e.printStackTrace();
		}
		
		return status;
	}
	
	
	
	public static boolean writeSystemParameters$Targets(HSSFWorkbook wb,String filePath,UnderwritingDocUploadVo vo,int noOfRadtioSheet)
	{
		boolean status = true;
		try {
		//FileInputStream fsIP = new FileInputStream(filePath); // Read the spreadsheet that needs to be
		String sheetName = "SystemParameters_Targets";
		vo.setExcelSheetName(sheetName);
		HSSFSheet worksheet = wb.getSheet(sheetName);
		HSSFRow row;
		HSSFCell cell;
		
		int rows = 0; // No of rows
		rows = worksheet.getPhysicalNumberOfRows();
		int cols = 0; // No of columns
		int tmp = 0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
	
		/*for (int i = 0; i < 10 || i < rows; i++) {
			row = worksheet.getRow(i);
			if (row != null) {
				tmp = worksheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols)
					cols = tmp;
			}
		}*/
		int copyRow = rows;
		
		for (int r = 0; r < (rows-1)/(noOfRadtioSheet-1); r++) 
		{
			copyRow(wb, worksheet, r+1, copyRow++,noOfRadtioSheet);
		}
		/*fsIP.close(); // Close the InputStream
		fsIP=null;

		FileOutputStream output_file = new FileOutputStream(new File(filePath)); // Open FileOutputStream to write
									// updates

		wb.write(output_file); // write changes

		output_file.close();*/
		} catch (Exception e) 
		{
			status = false;
			logger.info("Problem Occer writeSystemParameters$Targets in Excel Sheet "+e.getMessage());
			
			vo.setErrorMsg(e.getMessage());
			EligibilityCalculationProcessDao eligiDAO  = (EligibilityCalculationProcessDao)DaoImplInstanceFactory.getDaoImplInstance(EligibilityCalculationProcessDao.identity);
			eligiDAO.saveDataInExcelErrorLog(vo);
			e.printStackTrace();
	}
	
	return status;
	}
	
	 private static void copyRow(HSSFWorkbook workbook, HSSFSheet worksheet, int sourceRowNum, int destinationRowNum,int noOfRadioSheet) {
	        // Get the source / new row
	        HSSFRow newRow = worksheet.getRow(destinationRowNum);
	        HSSFRow sourceRow = worksheet.getRow(sourceRowNum);

	        // If the row exist in destination, push down all rows by 1 else create a new row
	        if (newRow != null) {
	            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
	        } else {
	            newRow = worksheet.createRow(destinationRowNum);
	        }
	        HSSFCell newCell1 = newRow.createCell(0);
	        newCell1.setCellValue(destinationRowNum);
	      
	        HSSFCell newCell2 = newRow.createCell(1);
	        newCell2.setCellValue("Ratio Sheet "+noOfRadioSheet+"/FA Sheet");
	        // Loop through source columns to add to new row
	        if(sourceRow!=null)
	        for (int i = 2; i < sourceRow.getLastCellNum(); i++) {
	            // Grab a copy of the old/new cell
	            HSSFCell oldCell = sourceRow.getCell(i);
	            HSSFCell newCell = newRow.createCell(i);

	            // If the old cell is null jump to next cell
	            if (oldCell == null) {
	                newCell = null;
	                continue;
	            }

	            // Copy style from old cell and apply to new cell
	            HSSFCellStyle newCellStyle = workbook.createCellStyle();
	            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
	            ;
	            newCell.setCellStyle(newCellStyle);

	            // If there is a cell comment, copy
	            if (oldCell.getCellComment() != null) {
	                newCell.setCellComment(oldCell.getCellComment());
	            }

	            // If there is a cell hyperlink, copy
	            if (oldCell.getHyperlink() != null) {
	                newCell.setHyperlink(oldCell.getHyperlink());
	            }

	            // Set the cell data type
	            newCell.setCellType(oldCell.getCellType());

	            // Set the cell data value
	            switch (oldCell.getCellType()) {
	                case Cell.CELL_TYPE_BLANK:
	                    newCell.setCellValue(oldCell.getStringCellValue());
	                    break;
	                case Cell.CELL_TYPE_BOOLEAN:
	                    newCell.setCellValue(oldCell.getBooleanCellValue());
	                    break;
	                case Cell.CELL_TYPE_ERROR:
	                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
	                    break;
	                case Cell.CELL_TYPE_FORMULA:
	                    newCell.setCellFormula(oldCell.getCellFormula());
	                    break;
	                case Cell.CELL_TYPE_NUMERIC:
	                    newCell.setCellValue(oldCell.getNumericCellValue());
	                    break;
	                case Cell.CELL_TYPE_STRING:
	                    newCell.setCellValue(oldCell.getRichStringCellValue());
	                    break;
	            }
	        }

	        // If there are are any merged regions in the source row, copy to new row
	        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
	            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
	            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
	                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
	                        (newRow.getRowNum() +
	                                (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
	                                        )),
	                        cellRangeAddress.getFirstColumn(),
	                        cellRangeAddress.getLastColumn());
	                worksheet.addMergedRegion(newCellRangeAddress);
	            }
	        }
	    }
	
}
