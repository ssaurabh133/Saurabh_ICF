package com.cp.process;

import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.connect.CommonFunction;
import com.cp.daoImplMYSQL.FileUploadDaoImpl;
import com.cp.vo.CommonXlsVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class ProfitLossFileReadProcess {
	public static ArrayList<CommonXlsVo> profitAndLossSheetFileReading(Object ob) {
		UnderwritingDocUploadVo vo =(UnderwritingDocUploadVo)ob;
		ArrayList<CommonXlsVo> list = new ArrayList<CommonXlsVo>();
		CommonXlsVo CommonXlsVo = null;
		String[] profitSheet;
		String[] lossSheet;
		String singleEntrySheet[];
		boolean profitFlag=false;
		boolean lossFlag=false;
		boolean flag=false;
		boolean arrayFlag=false;
		
		
		try {
			Workbook wb = FileUploadProcessTemplete
					.getFileReadConnectionForXls(vo.getDocPath(),
							vo.getFileName());
			
			Sheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;

			int rows = 0; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			int cols = 0; // No of columns
			int tmp = 0;

			// This trick ensures that we get the data properly even if it
			// doesn't start from first few rows
		
			for (int i = 0; i < 10 || i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}
			
			
			 
			
			if(CommonFunction.checkNull(vo.getFormatType()).equalsIgnoreCase("F1"))
			{
				list = FileUploadProcessTemplete.doubleEntryFile(sheet,  cols, rows,ob);
				
			}
			else if(CommonFunction.checkNull(vo.getFormatType()).equalsIgnoreCase("F2")){
				list = FileUploadProcessTemplete.singleEntryFile(sheet, cols, rows,ob);
			}
			else if(CommonFunction.checkNull(vo.getFormatType()).equalsIgnoreCase("F3")){
				list = FileUploadProcessTemplete.doubleEntryFile(sheet, cols, rows,ob);
			}
		
		//	FileUploadProcessTemplete.printListValue(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public static void main(String[] args) {
		UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
		
		vo.setDocType("P&L");
		vo.setFormatType("F1");
		vo.setFileName("APP0084_SAORA_PRAPPL_P&L_2014.xls");
		vo.setDocPath("C:\\Users\\saorabh.kumar/Downloads/");
		vo.setFromDate("30-4-2014");
		vo.setToDate("30-4-2010");
		ArrayList<CommonXlsVo> list= new ProfitLossFileReadProcess().profitAndLossSheetFileReading(vo);
	
		//new FileUploadDaoImpl().saveProfitAndLossSheet(ob, list);
	}
}
