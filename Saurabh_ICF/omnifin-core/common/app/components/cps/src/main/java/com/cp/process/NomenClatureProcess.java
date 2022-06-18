package com.cp.process;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.FileUtilityDao;
import com.cp.vo.CommonXlsVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class NomenClatureProcess {
	private static final Logger logger = Logger.getLogger(NomenClatureProcess.class.getName());
 public static Map<String,String>fileReadPrcess(Object ob)
 {
	 UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)ob;
	 Map<String,String> map = new LinkedHashMap<String, String>();
	 FileUtilityDao dao = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
		String filePath = dao.getNomenClatureFilePath();
		String fileName = dao.getNomenClatureFileForFormatHeading(ConstantValue.BN_HEADING);
		logger.info(filePath+"---"+fileName);
	 try {
		 
			Workbook wb = FileUploadProcessTemplete
					.getFileReadConnectionForXls(filePath,fileName+".xls");
					//("C://Users//saorabh.kumar//Desktop//ocr//documernt","Document_labels_dictionary.xls");
					/*(vo.getDocPath(),
							vo.getFileName());*/
			
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
		map = nomenClatureValueStore(sheet, cols, rows,vo);
		System.out.println("map=="+map);
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 
	 return map;
 }
 
 public static Map<String,String> nomenClatureValueStore(Sheet sheet, int cols,int rows,UnderwritingDocUploadVo vo)
 {
	 System.out.print("nomenClatureValueStore");
	 List<String> list = new ArrayList<String>();
	 Map<String, String> map =new LinkedHashMap<String, String>();
	 if(vo.getDocType().equalsIgnoreCase("BS"))
		{
		map =	nomenClatureValueStoreForBankDetails(sheet, cols, rows, vo);
		}
		else if(vo.getDocType().equalsIgnoreCase("BALS")) {
			nomenClatureValueStoreForBalanceSheet(sheet, cols, rows, vo);
		}
		else if (vo.getDocType().equalsIgnoreCase("P&L")) {
			nomenClatureValueStoreForProfitAndLoss(sheet, cols, rows, vo);
		}
	 
		return map;
 }
 
 public static boolean nomenClatureValueStoreForBalanceSheet(Sheet sheet, int cols,int rows,UnderwritingDocUploadVo vo)
 {
	
	 System.out.println("nomenClatureValueStoreForBalanceSheet");
	 Row row;
		Cell cell;
		List<String> list = new ArrayList<String>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		leb:	for (int r = 1; r < rows; r++) 
		{
				CommonXlsVo	CommonXlsVo = new CommonXlsVo();
				row = sheet.getRow(r);
				if (row != null) 
				{
					cell = 	row.getCell((short) 0);
					if (cell != null && !CommonFunction.checkNull(cell).equals("") )
					{
						if(vo.getDocType().equalsIgnoreCase(CommonFunction.checkNull(cell)))
						{
							for (int c = 3; c < cols ; c++) 
							{
								cell = row.getCell((short) c);
								if( !CommonFunction.checkNull(cell).equals(""))
								map.put(cell.toString().trim(),cell.toString().trim());
							}
						}
					}
				}
				
		}
		//FileUploadProcessTemplete.printValue(map);		
		
	 return false;
 }
 public  static Map<String,String>  nomenClatureValueStoreForBankDetails(Sheet sheet, int cols,int rows,UnderwritingDocUploadVo vo)
 {
	 System.out.println("nomenClatureValueStoreForBankDetails");
	 	Row row;
		Cell cell;
		List<String> list = new ArrayList<String>();
		String key="";
		String value="";
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int r = 1; r < rows; r++) 
		{
				
				row = sheet.getRow(r);
				if (row != null) 
				{
					cell = 	row.getCell((short) 0);
					if (cell != null && !CommonFunction.checkNull(cell).equals("") )
					{
						if(vo.getDocType().equalsIgnoreCase(CommonFunction.checkNull(cell)))
						{
							key=row.getCell((short) 4).toString().trim();
							value="";
							for (int c = 4; c < cols ; c++) 
							{
								cell = row.getCell((short) c);
								if( !CommonFunction.checkNull(cell).equals(""))
								{
									
									value =value+cell.toString().trim()+",";
								}
									map.put(key, value);
							}
							
							
						}
					}
				}
				
		}
		FileUploadProcessTemplete.printValue(map);	
	return map;
 }
 public static boolean nomenClatureValueStoreForProfitAndLoss(Sheet sheet, int cols,int rows,UnderwritingDocUploadVo vo)
 {
	 return false;
 }
	public static void printList(List<String> list)
	{
		System.out.println("print list");
		for(String str : list)
		System.out.print(str+"--");
	}
	public static void main(String[] args) {
		UnderwritingDocUploadVo vo =new UnderwritingDocUploadVo();
		vo.setDocType("BS");
		fileReadPrcess(vo);
	}
	
	public static String nomenClatureForHeading(Object ob)
	{
		 System.out.println("nomenClatureForHeading");
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)ob;
		FileUtilityDao dao = (FileUtilityDao)DaoImplInstanceFactory.getDaoImplInstance(FileUtilityDao.IDENTITY);
		String filePath = dao.getNomenClatureFilePath();
		String fileName = dao.getNomenClatureFileForFormatHeading(ConstantValue.FORMAT_HEADING);
		String key="";
		String value ="";
		logger.info(filePath+"---"+fileName);
		 try {
				Workbook wb = FileUploadProcessTemplete
						.getFileReadConnectionForXls(filePath,fileName+".xls");
						//("C://Users//saorabh.kumar//Desktop//ocr//documernt","Document_labels_dictionary.xls");
						/*(vo.getDocPath(),
								vo.getFileName());*/
				
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
				
				
				
				 	
					List<String> list = new ArrayList<String>();
					
					Map<String, String> map = new LinkedHashMap<String, String>();
					for (int r = 1; r < rows; r++) 
					{
							
							row = sheet.getRow(r);
							if (row != null) 
							{
								String docType = 	CommonFunction.checkNull(row.getCell((short) 0)).trim();
								String formatType = 	CommonFunction.checkNull(row.getCell((short) 1)).trim();
								if (docType != null && !CommonFunction.checkNull(docType).equals("") )
								{
									if(vo.getDocType().equalsIgnoreCase(CommonFunction.checkNull(docType)))
									{
										if(vo.getFormatType().equalsIgnoreCase(formatType.toString().trim()))
										{
										
										value="";
										for (int c = 2; c < cols ; c++) 
										{
											cell = row.getCell((short) c);
											if( !CommonFunction.checkNull(cell).equals(""))
											{
												
												value =value+cell.toString().trim()+",";
											}
												
										}
										
										}	
									}
								}
							}
							
					}
				
				
				
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		return value;
	}
	
}
