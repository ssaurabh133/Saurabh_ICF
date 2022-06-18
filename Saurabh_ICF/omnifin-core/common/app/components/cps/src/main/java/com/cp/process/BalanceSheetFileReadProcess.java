package com.cp.process;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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

public class BalanceSheetFileReadProcess {
	public static  ArrayList<CommonXlsVo> balanceSheetFileReading(Object ob) {
		ArrayList<CommonXlsVo> list = new ArrayList<CommonXlsVo>();
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)ob;
		CommonXlsVo CommonXlsVo = null;
		String[] balanceSheet;
		Map<String, String> map = null;
		String extention = vo.getFileName().substring(vo.getFileName().lastIndexOf("."),vo.getFileName().length());
		try {
				Workbook wb=	FileUploadProcessTemplete.getFileReadConnectionForXls(vo.getDocPath(), vo.getFileName());
					Sheet sheet = wb.getSheetAt(0);
					Row row;
					Cell cell;
					
					// map = FileUploadProcessTemplete.checkNomenClature("D://Balance Sheet", "BalanceSheetNarration.csv");
					int rows; // No of rows
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
					
					if(CommonFunction.checkNull(vo.getFormatType()).equalsIgnoreCase("F1"))
					{
						list = FileUploadProcessTemplete.singleEntryFile(sheet,  cols, rows,vo);
						
					}
					else if(CommonFunction.checkNull(vo.getFormatType()).equalsIgnoreCase("F2")){
						list = FileUploadProcessTemplete.tripleEntryFile(sheet, cols, rows,vo);
					}
					else if(CommonFunction.checkNull(vo.getFormatType()).equalsIgnoreCase("F3")){
						list = FileUploadProcessTemplete.doubleEntryFile(sheet, cols, rows,vo);
					}
					
			
			
			
		/*	boolean flag = false;
			// int count = 0;

			for (int r = 0; r < rows; r++) {
				CommonXlsVo = new CommonXlsVo();
				// count = 0;
				row = sheet.getRow(r);
				balanceSheet = new String[cols];
				if (row != null) {
					for (int c = 0; c < cols; c++) {
						cell = row.getCell((short) c);
					//	flag = FileUploadProcessTemplete.checkNomenClatureName(map,CommonFunction.checkNull(cell));
						if (cell != null && !cell.toString().equals("") ) {
							balanceSheet[c] = cell.toString();
							
						}
						

					}

				}
				
				CommonXlsVo.setBalanceSheet(balanceSheet);
				list.add(CommonXlsVo);
				
			}*/
			//FileUploadProcessTemplete.printListValueBalanceSheet(list);
		} catch (Exception ioe) {
			 CommonXlsVo = new CommonXlsVo();
			 CommonXlsVo.setErrorMsg("error");
			 list.clear();
			 list.add(CommonXlsVo);
			 ioe.printStackTrace();
			
		}
		return list;
	}

	
public static void main(String[] args) {
	UnderwritingDocUploadVo ob = new UnderwritingDocUploadVo();
	ob.setFormatType("FormatType3");
	ob.setDocPath("D://dictionay for labels");
	ob.setFileName("1000008_SAORA_PRAPPL_BALS_2013.xls");ob.setFromDate("30-01-2015");ob.setToDate("30-01-2015");
ArrayList<CommonXlsVo>	list = new BalanceSheetFileReadProcess().balanceSheetFileReading(ob);

	new FileUploadDaoImpl().saveBalanceSheet(ob, list);
}
}
