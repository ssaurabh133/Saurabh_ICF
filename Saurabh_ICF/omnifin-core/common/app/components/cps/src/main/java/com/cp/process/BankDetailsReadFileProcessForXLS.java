package com.cp.process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.connect.CommonFunction;
import com.cp.vo.CommonXlsVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class BankDetailsReadFileProcessForXLS {
	private static final Logger logger = Logger.getLogger(BankDetailsReadFileProcessForXLS.class.getName());
	public static Map<String, Map<String, String>> readingFileProcess(String path, String fileName) {
		
		UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
		vo.setDocType("BS");
		String csvFile = path+"//"+fileName;//"D://indusind excel.csv";
		logger.info("File name "+csvFile);
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		boolean flag = false;
		int count = 0;
		int i=0;
		int lineCounter = 0;
		int readingFileCount=0;
		boolean headerFlag = false;
		Map<String, String> storeValue = new LinkedHashMap<String, String>();
		Map<String, String> headerMap = new LinkedHashMap<String, String>();
		Map<String, String> headerKeyMap = new LinkedHashMap<String, String>();
		Map<String, Map<String, String>> outerMap = new LinkedHashMap<String, Map<String, String>>();
		
		try {
			
			Workbook wb = FileUploadProcessTemplete
					.getFileReadConnectionForXls(path,fileName);
			System.out.println(path+"--"+fileName);
			Sheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;

			int rows = 0; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			int cols = 0; // No of columns
			int tmp = 0;

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
			
			
			Map<String, String> map = 	NomenClatureProcess.fileReadPrcess(vo);
			System.out.println(map);
			for (int r = 0; r < rows; r++) 
			{
				
				lineCounter += 1;
				
				row = sheet.getRow(r);
				if(!headerFlag && row!=null)
				{
					count = 0;
				for (int c = 0; c < cols ; c++) 
				{
				leb:	for(Map.Entry<String, String> entry : map.entrySet())
				{
						String str =CommonFunction.checkNull(row.getCell((short) c));
						if(!str.trim().equalsIgnoreCase(""))
						{
							if (entry.getValue().toUpperCase().contains(str.trim().toUpperCase())) 
							{
								
									headerMap.put(c+"", str.trim());
									headerKeyMap.put(c+"", entry.getKey());
								count += 1;
								
							}
							
						}
					}
					
				}
				}
				
				if (count >= 4) 
				{
					headerFlag=true;
					storeValue = storeValueWithMap(sheet, cols, rows, readingFileCount, vo, map, headerMap,headerKeyMap, r);
					
					if(readingFileCount==0)
						flag = false;
					else
						flag=true;
					readingFileCount+=1;
				}
				if (flag && storeValue.size() > 0) 
				{
					outerMap.put(lineCounter + "", storeValue);

					flag = false;
				}

				// printValue(storeValue);
			}
		
			
			System.out.println(outerMap);
			
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
		System.out.println("Done");
		return outerMap;
	}
	// start .xls for code
	public static Map<String,String> storeValueWithMap(HSSFSheet sheet, int cols,int rows,int readingFileLineCount,UnderwritingDocUploadVo vo,Map<String, String> map,Map<String,String> headerMap,Map<String,String> headerKeyMap,int lineRead)
	{
		//System.out.println("----"+headerMap);
		boolean flag = false;
		Map<String, String> valuerMap = null;
		Map<String, Map<String,String>> outerMap = 	new LinkedHashMap<String, Map<String,String>>();;
		String mapKey = "";
		String mapValue = "";
		int i =0;
		HSSFRow row;
		HSSFCell cell;
		List<String> list = new ArrayList<String>();
		
		/*for(Map.Entry<String, String> entry1 : map.entrySet())
		{*/
		/*for (int r = lineRead; r < rows; r++) 
				{*/
			valuerMap = 	new LinkedHashMap<String, String>();
						row = sheet.getRow(lineRead);
						for (int c = 0; c < cols ; c++) 
						{
					
						
						if(headerMap.containsKey(c+""))
						{	
							if (row != null) 
							{
								cell = row.getCell((short) c);
								String str ="";
								if(headerMap.get(c+"").toUpperCase().contains("DATE"))
									 str = CommonFunction.checkNullForDate(cell);
								else
									 str = CommonFunction.checkNull(cell);
								//if(!str.trim().equalsIgnoreCase(""))
									if (headerMap.get(c+"") != null) 
									{
										flag = true;
										mapValue = str; 
										
												if(!headerMap.get(c+"".trim()).equalsIgnoreCase(mapValue.trim()))
													valuerMap.put(headerKeyMap.get(c+"".trim()), mapValue.trim());
									}
								}
							/*}*/
					}
						if(valuerMap.size()>0 )
						outerMap.put(lineRead+"", valuerMap);
				}
		System.out.println(headerMap+"==="+valuerMap);
		return headerMap.size()<=valuerMap.size()?valuerMap: new LinkedHashMap<String, String>();
	}
	// end .xls code
	// for Common Code for .xls and .xlsx code start
	public static Map<String,String> storeValueWithMap(Sheet sheet, int cols,int rows,int readingFileLineCount,UnderwritingDocUploadVo vo,Map<String, String> map,Map<String,String> headerMap,Map<String,String> headerKeyMap,int lineRead)
	{
		//System.out.println("----"+headerMap);
		boolean flag = false;
		Map<String, String> valuerMap = null;
		Map<String, Map<String,String>> outerMap = 	new LinkedHashMap<String, Map<String,String>>();;
		String mapKey = "";
		String mapValue = "";
		int i =0;
		Row row;
		Cell cell;
		List<String> list = new ArrayList<String>();
		
		/*for(Map.Entry<String, String> entry1 : map.entrySet())
		{*/
		/*for (int r = lineRead; r < rows; r++) 
				{*/
			valuerMap = 	new LinkedHashMap<String, String>();
						row = sheet.getRow(lineRead);
						for (int c = 0; c < cols ; c++) 
						{
					
						
						if(headerMap.containsKey(c+""))
						{	
							if (row != null) 
							{
								cell = row.getCell((short) c);
								String str ="";
								if(headerMap.get(c+"").toUpperCase().contains("DATE"))
									 str = CommonFunction.checkNullForDate(cell);
								else
									 str = CommonFunction.checkNull(cell);
								//if(!str.trim().equalsIgnoreCase(""))
									if (headerMap.get(c+"") != null) 
									{
										flag = true;
										mapValue = str; 
										
												if(!headerMap.get(c+"".trim()).equalsIgnoreCase(mapValue.trim()))
													valuerMap.put(headerKeyMap.get(c+"".trim()), mapValue.trim());
									}
								}
							/*}*/
					}
						if(valuerMap.size()>0 )
						outerMap.put(lineRead+"", valuerMap);
				}
		System.out.println(headerMap+"==="+valuerMap);
		return headerMap.size()<=valuerMap.size()?valuerMap: new LinkedHashMap<String, String>();
	}
	// end for .xlsx code
	
	public static void main(String[] args) {
		BankDetailsReadFileProcessForXLS obj = new BankDetailsReadFileProcessForXLS();
		com.cp.vo.UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
		vo.setDocType("BS");
	Map<String,Map<String,String>> map = obj.readingFileProcess("D:\\upload Format\\OmniFin-eCal-UAT test data\\CASE1_SUNIL\\bs","979797_SAORA_PRAPPL_BS_ABHYD_12345.xls");
	
	
	}
}
