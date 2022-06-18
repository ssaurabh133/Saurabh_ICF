package com.cp.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.FileUplaodDao;
import com.cp.util.DateUtility;
import com.cp.util.FormatUtility;
import com.cp.vo.CommonXlsVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class FileUploadProcessTemplete {
	private static final Logger logger = Logger
			.getLogger(FileUploadProcessTemplete.class.getName());
	public static boolean fileNameValidation(Object ob)
	{
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)ob;
		String fileName = vo.getDocFile().getFileName();
		
		fileName=fileName.substring(0,fileName.indexOf("."));
		String[] fileNameContent =fileName.split("_");
		String fileCustomerNameMantion = fileNameContent[1];
		int fileCustomerNameMantionSize=0;
		if(CommonFunction.checkNull(fileCustomerNameMantion).length()<3 || CommonFunction.checkNull(fileCustomerNameMantion).length()>5)
		{
			return false;
		}
		else
		{
			fileCustomerNameMantionSize=CommonFunction.checkNull(fileCustomerNameMantion).length();
			logger.info("fileCustomerNameMantionSize-------"+fileCustomerNameMantionSize);
		}
		logger.info("fileNameContent-----------"+fileNameContent.toString());
		boolean returnFlag = true;
		String custmerName = vo.getCustomerName().replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "");
		
		System.out.println(fileName.contains(vo.getRefId()));
		System.out.println(fileName.contains(custmerName.substring(0,fileCustomerNameMantionSize))+"    "+custmerName.substring(0,fileCustomerNameMantionSize));
		System.out.println(fileName.contains(vo.getDocEntity()));
		System.out.println(fileName.contains(vo.getDocType()));
		System.out.println(vo.getDocType()+"-----------^^^^^^^^^^^----------"+fileName);
		System.out.println(fileName.contains(vo.getFromDate().subSequence(6, 10))+"     "+vo.getFromDate().subSequence(6,10));
		//String result = str.replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "");
		
		logger.info("customer Name "+custmerName);
		if(fileNameContent.length<5 && !vo.getDocType().equalsIgnoreCase("CO"))
		{
			return false ;
		}
	
		System.out.println("vo.getDocType()---"+vo.getDocType()+"--vo.getBankName()---"+vo.getBankName());
		
		if(vo.getDocType().equalsIgnoreCase("BS"))
		{
			System.out.println("in Bs block---");
			String bankName = vo.getBankName().replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "");
			String accDesc = vo.getAccountTypeDesc().replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "");
			if(!vo.getRefId().equalsIgnoreCase(fileNameContent[0]))
			{
				logger.info("getRefId--"+fileNameContent[0]);
			return false;
			}
			if( custmerName.length()<fileCustomerNameMantionSize  || !custmerName.substring(0,fileCustomerNameMantionSize).equalsIgnoreCase(fileNameContent[1]))
			{
				logger.info("getCustomerName--"+fileNameContent[1]);
				return false;
			}
			if(  !vo.getDocEntity().equalsIgnoreCase(fileNameContent[2]))
			{
				logger.info("getDocEntity--"+fileNameContent[2]);
				return false;
			}
			if(  !vo.getDocType().equalsIgnoreCase(fileNameContent[3]))
			{
				logger.info("getDocType--"+fileNameContent[3]);
				return false;
			}
			if( bankName.length()<5 || !bankName.substring(0,5).equalsIgnoreCase(fileNameContent[4]))
			{
				logger.info("getBankName--"+fileNameContent[4]);
				return false;
			}
			if( accDesc.length()<4  || !accDesc.substring(0,2).equalsIgnoreCase(fileNameContent[5]))
			{	
				logger.info("getAccountTypeDesc--"+fileNameContent[5]);
				return false;
			}
			if(  vo.getAccountNo().length()<5  || !vo.getAccountNo().substring(vo.getAccountNo().length()-5).equalsIgnoreCase(fileNameContent[6]))
			{	
				logger.info("getAccountNo--"+fileNameContent[6]);
				return false;
			}
				return returnFlag;
		}
		else if(vo.getDocType().equalsIgnoreCase("CO"))
		{
			System.out.println(vo.getDocType()+"-----");
			System.out.println(!vo.getRefId().equalsIgnoreCase(fileNameContent[0])+"-----!vo.getRefId().equalsIgnoreCase(fileNameContent[0]");
			
			if(!vo.getRefId().equalsIgnoreCase(fileNameContent[0]))
				return false;
			if( custmerName.length()<fileCustomerNameMantionSize || !custmerName.substring(0,fileCustomerNameMantionSize).equalsIgnoreCase(fileNameContent[1]))
					return false;
			if(  !vo.getDocEntity().equalsIgnoreCase(fileNameContent[2]))
					return false;
			if(  !"CIBIL".equalsIgnoreCase(fileNameContent[3]))
					return false;
			
		returnFlag =true;
		}
		else{
			System.out.println("in else block-----");
				if(!vo.getRefId().equalsIgnoreCase(fileNameContent[0]))
					return false;
				if( custmerName.length()<fileCustomerNameMantionSize || !custmerName.substring(0,fileCustomerNameMantionSize).equalsIgnoreCase(fileNameContent[1]))
						return false;
				if(  !vo.getDocEntity().equalsIgnoreCase(fileNameContent[2]))
						return false;
				if(  !vo.getDocType().equalsIgnoreCase(fileNameContent[3]))
						return false;
				if(  !vo.getFromDate().substring(6,10).equalsIgnoreCase(fileNameContent[4]))
						return false;
			returnFlag =true;
		}

			
		
		return returnFlag;
	}

	/*public static HSSFWorkbook getFileReadConnectionForXls(String path,String fileName)
	{
		String file = path+"//"+fileName;
		logger.info("file ----"+path+"//"+fileName);
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			 fs = new POIFSFileSystem(new FileInputStream(file));
			 wb = new HSSFWorkbook(fs);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
		return wb;
	}*/
	
	public static org.apache.poi.ss.usermodel.Workbook getFileReadConnectionForXls(String path,String fileName)
	{
		String file = path+"//"+fileName;
		logger.info("file ----"+path+"//"+fileName);
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		XSSFWorkbook xwb = null;
		Workbook workbook=null;
		try {
			if(fileName.endsWith(".xls"))
			{
			 fs = new POIFSFileSystem(new FileInputStream(file));
			 wb = new HSSFWorkbook(fs);
			}
			else
			{
				
				xwb = new XSSFWorkbook(new FileInputStream(file));
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
		return wb!=null?wb:xwb;
	}
	
	public static XSSFWorkbook getFileReadConnectionForXlsx(String path,String fileName)
	{
		String file = path+"//"+fileName;
		logger.info("file ----"+path+"//"+fileName);
		POIFSFileSystem fs = null;
		XSSFWorkbook wb = null;
		try {
			OPCPackage pkg = OPCPackage.open(new FileInputStream(new File(file)));
			   POIXMLProperties props;
			   props = new POIXMLProperties(pkg);
			   System.out.println("The title is " + props.getCoreProperties().getTitle());
			   
			 wb = new XSSFWorkbook(pkg);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
		return wb;
	}
	
	/*public static void printListValue(ArrayList<CommonXlsVo> list) {
		Iterator<CommonXlsVo> iterator = list.iterator();
		while (iterator.hasNext()) {
			CommonXlsVo CommonXlsVo = (CommonXlsVo) iterator.next();
			String[] sheetValue = CommonXlsVo.getProfitSheet();
			if(sheetValue!=null){
			for (String str : sheetValue) {
				System.out.print(str + "    ");
			}
			System.out.print("  ||||  ");
			}
			String[] Value = CommonXlsVo.getLossSheet();
			if(Value!=null){
			for (String str : Value) {
				System.out.print(str + "    ");
			}
			
		}System.out.println("   ");
		
		String[] single = CommonXlsVo.getSingleEntrySheet();
		if(single!=null){
		for (String str : single) {
			System.out.print(str + "    ");
		}}
		}
	}*/
	public static void printListValueBalanceSheet(ArrayList<CommonXlsVo> list) {
		Iterator<CommonXlsVo> iterator = list.iterator();
		while (iterator.hasNext()) {
			CommonXlsVo CommonXlsVo = (CommonXlsVo) iterator.next();
			String[] sheet = CommonXlsVo.getBalanceSheet();
			for (String str : sheet) {
				System.out.print(str + "    ");
			}
			System.out.println("   ");
			}
	}
	
	public static Map<String, String> checkNomenClature(String path,String fileName) {
		Map<String, String> map = new HashMap<String, String>();
		String csvFile = path+"//"+fileName;//"D://Narrasion//Narrasion.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		boolean flag = false;
		try {
			br = getConnection(csvFile);
			while ((line = br.readLine()) != null) {
				// System.out.println("------");
				map = fillMapValue(map, line, cvsSplitBy);
				// System.out.println(line);
			}
			printValue(map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnction(br);
		}
		return map;

	}
	public static BufferedReader getConnection(String path) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return br;
	}

	public static void closeConnction(BufferedReader br) {
		if (br != null) {
			try {
				br.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public static Map<String, String> fillMapValue(Map<String, String> map,
			String line, String cvsSplitBy) {
	//	String[] strArr = line.split(cvsSplitBy);
		String mapKey = "";
		StringBuilder mapValue = new StringBuilder();
		mapKey= line.substring(0,line.indexOf(','));
		System.out.println("mapKey "+mapKey);
		mapValue.append(line.replace(",,", ""));
		//mapValue.replace(mapValue.lastIndexOf(","), mapValue.lastIndexOf(","), mapValue.toString());
		
		 /*if (strArr.length >= 1) {

			for (int i = 0; i < strArr.length; i++) {
				mapKey = strArr[i].trim();
				mapValue = strArr[i].trim(); // map.put(strArr[0],
										// strArr.length>1?strArr[0])
				map.put(mapKey, mapValue);
			}

		}*/
		if(!mapKey.equalsIgnoreCase(""))
		map.put(mapKey, mapValue.toString());
		return map;
	}

	public static Map<String, String> mapperValueWithHeader(
			Map<Integer, String> headerMap, String line, String cvsSplitBy,Map<String, String> map,int readingFileLineCount) {
		boolean flag = false;
		Map<String, String> valuerMap = 	new LinkedHashMap<String, String>();;
		String[] strArr = line.split(cvsSplitBy);
		String mapKey = "";
		String mapValue = "";
		int i =0;
		//System.out.println("headMap "+headerMap);
		//System.out.println("Map "+map);
	
		for(Map.Entry<String, String> entry1 : map.entrySet())
		{
			if(headerMap.containsValue(entry1.getKey()))
			{	
					for ( i = 0; i < strArr.length; i++)
					{
						if (headerMap.get(i) != null) {
							flag = true;
							mapValue = strArr[i]; // map.put(strArr[0],
							
									if(readingFileLineCount>0)
										valuerMap.put(headerMap.get(i), mapValue);
									
								
							
						}
					}
			}
				else
				{
					
					valuerMap.put(entry1.getKey(), null);
				}
			
		}
		
		
		
		
		
		/*for (int i = 0; i < strArr.length; i++) {
			if (headerMap.get(i) != null) {
				flag = true;
				mapValue = strArr[i]; // map.put(strArr[0],
					if(!headerMap.get(i).equalsIgnoreCase(strArr[i]))					// strArr.length>1?strArr[0])
					{
						
							valuerMap.put(headerMap.get(i), mapValue);
					}
				
			}
			if (flag)
				outerMap.put(i + "", valuerMap);
		}*/
		
		return headerMap.size()<valuerMap.size()?valuerMap: new LinkedHashMap<String, String>();
	}

	public static void printValue(Map<String, String> map) {

		for (Map.Entry<String, String> entry : map.entrySet())
			System.out.println("getKey: " + entry.getKey() + "  getValue: "
					+ entry.getValue());
	}

	public static void printNestedMapValue(Map<String, Map<String, String>> map) {

		// map.entrySet();
		for (Map.Entry<String, Map<String, String>> entry : map.entrySet())
			System.out.println("getKey: " + entry.getKey() + "  getValue: "
					+ entry.getValue());
		
		
		/*Set<Entry<String, Map<String, String>>>  set  = map.entrySet(); 
		Iterator<Entry<String, Map<String, String>>> it =set.iterator();
		while (it.hasNext()) {
			HashMap<String,  String> entry = (HashMap<String, String>) it
					.next();
			System.out.println("getKey: "+entry);
		}*/
		
		
			
		
		}	
	public static boolean checkNomenClatureName(Map<String, String> map, String cellValue )
	{
		
		return map.containsKey(cellValue)?true:false;
	}
	
	public static ArrayList<CommonXlsVo> singleEntryFile(HSSFSheet sheet, int cols,int rows,Object ob)
	{
	boolean	singleEntryFlag=false;
	String singleEntrySheet[] = null ;
	HSSFRow row;
	HSSFCell cell;
	int count=0;
	UnderwritingDocUploadVo unVo = (UnderwritingDocUploadVo)ob;
	ArrayList<CommonXlsVo>    volist = new ArrayList<CommonXlsVo>();
	ArrayList<CommonXlsVo> list =new ArrayList<CommonXlsVo>();
	int r = 0,c=0;
	if(unVo.getDocType().equalsIgnoreCase("P&L"))
	{
		count=1;
		cols=cols-1;
	}
	try {
		for ( r = 3; r < rows; r++) {
			CommonXlsVo	CommonXlsVo = new CommonXlsVo();
			singleEntrySheet = new String[cols];
			row = sheet.getRow(r);
			if (row != null) {
				for ( c = 0; c < cols ; c++) {
					cell = row.getCell((short) c+count);
					
					if (cell != null ) {
						
						singleEntrySheet[c] = CommonFunction.checkNull(cell);
						singleEntryFlag = true;
					}
				}
				
				if(singleEntryFlag)
				{
				CommonXlsVo.setSingleEntrySheet(singleEntrySheet);
				}
				
				list.add(CommonXlsVo);
				
			}
			
		}
		    volist =   singleEntryWrapperDataInVO(list,ob);
	} catch (Exception e) {
		logger.info("Problem in file Reading  "+e.getMessage());
		saveErrorRecods(r,c,unVo);
	}
	
		return volist;
	}
	public static ArrayList<CommonXlsVo> doubleEntryFile(HSSFSheet sheet, int cols,int rows,Object ob)
	{
		String[] liabilities = new String[cols/2];
		String[] assets = new String[cols/2];
		boolean profitFlag=false;
		boolean lossFlag=false;
		HSSFRow row;
		HSSFCell cell;
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)ob;
		int r = 0,c=0;
		ArrayList<CommonXlsVo> volist = new ArrayList<CommonXlsVo>();
		ArrayList<CommonXlsVo> list =new ArrayList<CommonXlsVo>();
		try {
			
		
			for ( r = 3; r < rows; r++) {
				CommonXlsVo	CommonXlsVo = new CommonXlsVo();
				liabilities = new String[cols/2];
				assets = new String[cols/2];
				profitFlag=false;
				lossFlag=false;
				row = sheet.getRow(r);
				if (row != null) {
					lab:for ( c = 0; c < cols ; c++) {
						cell = row.getCell((short) c);
						if (cell != null && !CommonFunction.checkNull(cell).equals("") ) {
							if (cell != null && !CommonFunction.checkNull(cell).equals("") ) {
								if(c<(cols/2))
								{
									liabilities[c] = CommonFunction.checkNull(cell);
								profitFlag = true;
								}
								else 
								{
									assets[c-(cols/2)] =  CommonFunction.checkNull(cell);
								lossFlag = true;
								}
								
							}
						}
					}
					
					if(profitFlag)
					{
					CommonXlsVo.setLiabilities(liabilities);
					}
					if(lossFlag)
					{
						CommonXlsVo.setAssets(assets);
					}
					list.add(CommonXlsVo);
					
				}
				
			}
			 volist =   doubleEntryWrapperDataInVO(list,ob);
		} catch (Exception e) {
			logger.info("Problem in file Reading  "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			saveErrorRecods(r,c,ob);
		}
			
			return volist;
	}
	public static ArrayList<CommonXlsVo> tripleEntryFile(HSSFSheet sheet, int cols,int rows,Object ob)
	{
		String[] liabilities = new String[cols/2];
		String[] assets = new String[cols/2];
		boolean profitFlag=false;
		boolean lossFlag=false;
		HSSFRow row;
		HSSFCell cell;
		int count=0;
		int r=0,c=0;
		UnderwritingDocUploadVo unVo = (UnderwritingDocUploadVo)ob;
		if(unVo.getDocType().equalsIgnoreCase("P&L"))
		{
			count=1;
		}
		ArrayList<CommonXlsVo> volist = new ArrayList<CommonXlsVo>();
		ArrayList<CommonXlsVo> list =new ArrayList<CommonXlsVo>();
		try {
			
		
			for ( r = 4; r < rows; r++) {
				CommonXlsVo	CommonXlsVo = new CommonXlsVo();
				liabilities = new String[cols/2];
				assets = new String[cols/2];
				profitFlag=false;
				lossFlag=false;
				row = sheet.getRow(r);
				if (row != null) {
					lab:for ( c = count; c < cols ; c++) {
						cell = row.getCell((short) c);
						if (cell != null && !CommonFunction.checkNull(cell.toString()).equals("") ) {
							if (cell != null && !CommonFunction.checkNull(cell.toString()).equals("") ) {
								if(c<(cols/2))
								{
									liabilities[c] =  CommonFunction.checkNull(cell);
								profitFlag = true;
								}
								else 
								{
									assets[c-(cols/2)] =  CommonFunction.checkNull(cell);
								lossFlag = true;
								}
								
							}
						}
					}
					
					if(profitFlag)
					{
					CommonXlsVo.setLiabilities(liabilities);
					}
					if(lossFlag)
					{
						CommonXlsVo.setAssets(assets);
					}
					list.add(CommonXlsVo);
					
				}
				
			}
			 volist =   tripleEntryWrapperDataInVO(list,ob);
		}
			catch (Exception e) {
				logger.info("Problem in file Reading  "+e.getMessage());
				saveErrorRecods(r,c,ob);
			}
		return volist;
	}
	
	
	
	
	// for common code sheet 
	public static ArrayList<CommonXlsVo> singleEntryFile(Sheet sheet, int cols,int rows,Object ob)
	{
	boolean	singleEntryFlag=false;
	String singleEntrySheet[] = null ;
	Row row;
	Cell cell;
	int count=0;
	UnderwritingDocUploadVo unVo = (UnderwritingDocUploadVo)ob;
	ArrayList<CommonXlsVo>    volist = new ArrayList<CommonXlsVo>();
	ArrayList<CommonXlsVo> list =new ArrayList<CommonXlsVo>();
	int r = 0,c=0;
	if(unVo.getDocType().equalsIgnoreCase("P&L"))
	{
		count=1;
		cols=cols-1;
	}
	try {
		for ( r = 3; r < rows; r++) {
			CommonXlsVo	CommonXlsVo = new CommonXlsVo();
			singleEntrySheet = new String[cols];
			row = sheet.getRow(r);
			if (row != null) {
				for ( c = 0; c < cols ; c++) {
					cell = row.getCell((short) c+count);
					
					if (cell != null ) {
						
						singleEntrySheet[c] = CommonFunction.checkNull(cell);
						singleEntryFlag = true;
					}
				}
				
				if(singleEntryFlag)
				{
				CommonXlsVo.setSingleEntrySheet(singleEntrySheet);
				}
				
				list.add(CommonXlsVo);
				
			}
			
		}
		    volist =   singleEntryWrapperDataInVO(list,ob);
	} catch (Exception e) {
		logger.info("Problem in file Reading  "+e.getMessage());
		saveErrorRecods(r,c,unVo);
	}
	
		return volist;
	}
	public static ArrayList<CommonXlsVo> doubleEntryFile(Sheet sheet, int cols,int rows,Object ob)
	{
		String[] liabilities = new String[cols/2];
		String[] assets = new String[cols/2];
		boolean profitFlag=false;
		boolean lossFlag=false;
		Row row;
		Cell cell;
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)ob;
		int r = 0,c=0;
		ArrayList<CommonXlsVo> volist = new ArrayList<CommonXlsVo>();
		ArrayList<CommonXlsVo> list =new ArrayList<CommonXlsVo>();
		try {
			
		
			for ( r = 3; r < rows; r++) {
				CommonXlsVo	CommonXlsVo = new CommonXlsVo();
				liabilities = new String[cols/2];
				assets = new String[cols/2];
				profitFlag=false;
				lossFlag=false;
				row = sheet.getRow(r);
				if (row != null) {
					lab:for ( c = 0; c < cols ; c++) {
						cell = row.getCell((short) c);
						if (cell != null && !CommonFunction.checkNull(cell).equals("") ) {
							if (cell != null && !CommonFunction.checkNull(cell).equals("") ) {
								if(c<(cols/2))
								{
									liabilities[c] = CommonFunction.checkNull(cell);
								profitFlag = true;
								}
								else 
								{
									assets[c-(cols/2)] =  CommonFunction.checkNull(cell);
								lossFlag = true;
								}
								
							}
						}
					}
					
					if(profitFlag)
					{
					CommonXlsVo.setLiabilities(liabilities);
					}
					if(lossFlag)
					{
						CommonXlsVo.setAssets(assets);
					}
					list.add(CommonXlsVo);
					
				}
				
			}
			 volist =   doubleEntryWrapperDataInVO(list,ob);
		} catch (Exception e) {
			logger.info("Problem in file Reading  "+e.getMessage());
			vo.setErrorMsg(e.getMessage());
			saveErrorRecods(r,c,ob);
		}
			
			return volist;
	}
	public static ArrayList<CommonXlsVo> tripleEntryFile(Sheet sheet, int cols,int rows,Object ob)
	{
		String[] liabilities = new String[cols/2];
		String[] assets = new String[cols/2];
		boolean profitFlag=false;
		boolean lossFlag=false;
		Row row;
		Cell cell;
		int count=0;
		int r=0,c=0;
		UnderwritingDocUploadVo unVo = (UnderwritingDocUploadVo)ob;
		if(unVo.getDocType().equalsIgnoreCase("P&L"))
		{
			count=1;
		}
		ArrayList<CommonXlsVo> volist = new ArrayList<CommonXlsVo>();
		ArrayList<CommonXlsVo> list =new ArrayList<CommonXlsVo>();
		try {
			
		
			for ( r = 4; r < rows; r++) {
				CommonXlsVo	CommonXlsVo = new CommonXlsVo();
				liabilities = new String[cols/2];
				assets = new String[cols/2];
				profitFlag=false;
				lossFlag=false;
				row = sheet.getRow(r);
				if (row != null) {
					lab:for ( c = count; c < cols ; c++) {
						cell = row.getCell((short) c);
						if (cell != null && !CommonFunction.checkNull(cell.toString()).equals("") ) {
							if (cell != null && !CommonFunction.checkNull(cell.toString()).equals("") ) {
								if(c<(cols/2))
								{
									liabilities[c] =  CommonFunction.checkNull(cell);
								profitFlag = true;
								}
								else 
								{
									assets[c-(cols/2)] =  CommonFunction.checkNull(cell);
								lossFlag = true;
								}
								
							}
						}
					}
					
					if(profitFlag)
					{
					CommonXlsVo.setLiabilities(liabilities);
					}
					if(lossFlag)
					{
						CommonXlsVo.setAssets(assets);
					}
					list.add(CommonXlsVo);
					
				}
				
			}
			 volist =   tripleEntryWrapperDataInVO(list,ob);
		}
			catch (Exception e) {
				logger.info("Problem in file Reading  "+e.getMessage());
				saveErrorRecods(r,c,ob);
			}
		return volist;
	}
	// end 
	
	public static ArrayList singleEntryWrapperDataInVO(ArrayList<CommonXlsVo> list,Object ob)
	{
		int size = list!=null?list.size():0;
		UnderwritingDocUploadVo uvo = (UnderwritingDocUploadVo)ob;
		CommonXlsVo vo =null;
		CommonXlsVo currentReportingvo = null;
		CommonXlsVo priviousReportingvo = null;
		ArrayList<CommonXlsVo> returnList= new ArrayList<CommonXlsVo>();
		String fromDate = FormatUtility.dateFormat(DateUtility.convertToDate(uvo.getFromDate()));
		String toDate =  FormatUtility.dateFormat(DateUtility.convertToDate(uvo.getToDate()));
		
		try {
			
		
		if(size>0)
		{
			for(int i=0;i<size;i++)
			{
				vo = list.get(i);
				currentReportingvo = new CommonXlsVo();
				
				priviousReportingvo= new CommonXlsVo();
			String[] str =	vo.getSingleEntrySheet();
			if(str!=null)
				{
				currentReportingvo.setParticulars(str[0]);
				currentReportingvo.setNotes(str[1]);
				currentReportingvo.setReportingAmount(str[2]);
				currentReportingvo.setYear(FormatUtility.getYear(toDate));
				currentReportingvo.setMonth(FormatUtility.getMonth(toDate));
				
				priviousReportingvo.setParticulars(str[0]);
				priviousReportingvo.setNotes(str[1]);
				priviousReportingvo.setReportingAmount(str[3]);
				priviousReportingvo.setYear(FormatUtility.getYear(fromDate));
				priviousReportingvo.setMonth(FormatUtility.getMonth(fromDate));
				returnList.add(currentReportingvo);
				returnList.add(priviousReportingvo);
				}
			}
		}
		} catch (Exception e) {
			logger.info("File format Problem "+e.getMessage());
			return null;
		}
		return returnList;
		
	}
	public static ArrayList doubleEntryWrapperDataInVO(ArrayList<CommonXlsVo> list,Object ob)
	{
		int size = list!=null?list.size():0;
		UnderwritingDocUploadVo uvo = (UnderwritingDocUploadVo)ob;
		CommonXlsVo vo =null;
		CommonXlsVo liabilities = null;
		CommonXlsVo assets = null;
		ArrayList<CommonXlsVo> returnList= new ArrayList<CommonXlsVo>();
		String fromDate = FormatUtility.dateFormat(DateUtility.convertToDate(uvo.getFromDate()));
		String toDate =  FormatUtility.dateFormat(DateUtility.convertToDate(uvo.getToDate()));
		
		try {
			
		
		if(size>0)
		{
			for(int i=0;i<size;i++)
			{
				vo = list.get(i);
				liabilities = new CommonXlsVo();
				
				assets= new CommonXlsVo();
			String[] lib =	vo.getLiabilities();
			String[] ass = vo.getAssets();
			if(lib!=null)
				{
				liabilities.setParticulars(lib[0]);
				
				liabilities.setReportingAmount(lib[1]);
				liabilities.setYear(FormatUtility.getYear(toDate));
				liabilities.setMonth(FormatUtility.getMonth(toDate));
				returnList.add(liabilities);
				}
			if(ass!=null)
				{
				assets.setParticulars(ass[0]);
				
				assets.setReportingAmount(ass[1]);
				assets.setYear(FormatUtility.getYear(toDate));
				assets.setMonth(FormatUtility.getMonth(toDate));
				
				returnList.add(assets);
				}
			}
		}
		} catch (Exception e) {
			logger.info("File format Problem "+e.getMessage());
			return null;
		}
		return returnList;
		
	}
	
	
	public static ArrayList tripleEntryWrapperDataInVO(ArrayList<CommonXlsVo> list,Object ob)
	{
		int size = list!=null?list.size():0;
		UnderwritingDocUploadVo uvo = (UnderwritingDocUploadVo)ob;
		CommonXlsVo vo =null;
		CommonXlsVo liabilities = null;
		CommonXlsVo assets = null;
		
		CommonXlsVo currliabilitiesYear = null;
		CommonXlsVo preliabilitiesYear = null;
		CommonXlsVo currAssetsYear = null;
		CommonXlsVo preAssetsYear = null;
		
		ArrayList<CommonXlsVo> returnList= new ArrayList<CommonXlsVo>();
		String fromDate = FormatUtility.dateFormat(DateUtility.convertToDate(uvo.getFromDate()));
		String toDate =  FormatUtility.dateFormat(DateUtility.convertToDate(uvo.getToDate()));
		
		try {
			
		
		if(size>0)
		{
			for(int i=0;i<size;i++)
			{
				vo = list.get(i);
				liabilities = new CommonXlsVo();
				assets= new CommonXlsVo();
				currliabilitiesYear =new CommonXlsVo();
				 preliabilitiesYear = new CommonXlsVo();
				 currAssetsYear = new CommonXlsVo();
				 preAssetsYear = new CommonXlsVo();
				
				
			String[] lib =	vo.getLiabilities();
			String[] ass = vo.getAssets();
			if(lib!=null)
			{
			currliabilitiesYear.setParticulars(lib[0]);
			preliabilitiesYear.setParticulars(lib[0]);
			preliabilitiesYear.setReportingAmount(lib[2]);
			currliabilitiesYear.setReportingAmount(lib[1]);
			
			preliabilitiesYear.setYear(FormatUtility.getYear(fromDate));
			currliabilitiesYear.setYear(FormatUtility.getYear(toDate));
			preliabilitiesYear.setMonth(FormatUtility.getMonth(fromDate));
			currliabilitiesYear.setMonth(FormatUtility.getMonth(toDate));
			
			}
			
			if(ass!=null)
			{
			currAssetsYear.setParticulars(ass[0]);
			preAssetsYear.setParticulars(ass[0]);
			preAssetsYear.setReportingAmount(ass[2]);
			currAssetsYear.setReportingAmount(ass[1]);
			
			preAssetsYear.setYear(FormatUtility.getYear(fromDate));
			currAssetsYear.setYear(FormatUtility.getYear(toDate));
			preAssetsYear.setMonth(FormatUtility.getMonth(fromDate));
			currAssetsYear.setMonth(FormatUtility.getMonth(toDate));
			
			returnList.add(currliabilitiesYear);
			returnList.add(preliabilitiesYear);
			returnList.add(currAssetsYear);
			returnList.add(preAssetsYear);
			}
			}
		}
		} catch (Exception e) {
			logger.info("File format Problem "+e.getMessage());
			return null;
		}
		return returnList;
		
	}
	
	public static boolean fileTypeValidation(Object ob,String fileVal)
	{
		logger.info(" fileTypeValidation  ");
				UnderwritingDocUploadVo vo =(UnderwritingDocUploadVo)ob;
				boolean profitFlag=false;
				boolean lossFlag=false;
				boolean flag=false;
				boolean arrayFlag=false;
				int count = 0;
				
				try {
					
					org.apache.poi.ss.usermodel.Workbook wb = FileUploadProcessTemplete
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
					
					leb: for (int r = 2; r < 5; r++) {
						count=0;
						row = sheet.getRow(r);
						if (row != null) {
							for (int c = 0; c < cols ; c++) {
								cell = row.getCell((short) c);
								//logger.info("file value----"+fileVal.trim()+"   cell.toString().trim()-------"+cell.toString().trim()+"  ---fileVal.trim().contains(cell.toString().trim())--"+fileVal.trim().contains(cell.toString().trim()));
								if (cell != null && !CommonFunction.checkNull(cell).equalsIgnoreCase("")) {
									if(fileVal.trim().contains(cell.toString().trim()))
									{
									count ++;
									fileVal = fileVal.trim().replace(cell.toString().trim(), "$");
									logger.info("-------"+cell.toString());
									}
									if(count>=2)
										break leb;
								}
							}
						}
						
					}
					
					
				}catch(Exception e)	
				{
					e.printStackTrace();
				}
					
		return count>=2?true:false;			
	}
	
	public static void saveErrorRecods(int row,int col,Object ob)
	{
		FileUplaodDao dao = (FileUplaodDao)DaoImplInstanceFactory.getDaoImplInstance(FileUplaodDao.IDENTITY);
		UnderwritingDocUploadVo uwDocVo =(UnderwritingDocUploadVo)ob;
		dao.saveErrorRecords( row, col,uwDocVo);
	}
	
	public static void main(String[] args) {
		UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
		String value = NomenClatureProcess.nomenClatureForHeading(vo);
	}
}
