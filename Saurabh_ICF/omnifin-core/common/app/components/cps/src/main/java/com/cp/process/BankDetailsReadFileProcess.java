package com.cp.process;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cp.actions.UploadAction;
import com.cp.daoImplMYSQL.FileUploadDaoImpl;
import com.cp.vo.UnderwritingDocUploadVo;
public class BankDetailsReadFileProcess {
	private static final Logger logger = Logger.getLogger(BankDetailsReadFileProcess.class.getName());
	public static void main(String[] args) {

		BankDetailsReadFileProcess obj = new BankDetailsReadFileProcess();
		Map<String,Map<String,String>> map = obj.readingFileProcess("D://dictionay for labels","UPLOADSDF_SAORA_PRAPPL_BS_2013.csv");
		System.out.println(map+"====");
		com.cp.vo.UnderwritingDocUploadVo vo = new UnderwritingDocUploadVo();
		ArrayList list = new ArrayList();
	}

	public static Map<String, Map<String, String>> readingFileProcess(String path, String fileName) {

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
		Map<String, String> storeValue = new HashMap<String, String>();
		Map<Integer, String> headerMap = new HashMap<Integer, String>();
		Map<String, Map<String, String>> outerMap = new HashMap<String, Map<String, String>>();
		try {

			String strTemp = "DOCUMENT_SEQ_ID,STATMENT_DATE,TYPE,NARRATION,TOTAL_DR,TOTAL_CR,BALANCE_AMOUNT,SOL,BRANCH_CODE,CHEQUE_NO,TRANSACTION_ID,VALUE_DATE";
			
			
			
			br = new BufferedReader(new FileReader(csvFile));
			Map<String, String> map = FileUploadProcessTemplete.checkNomenClature("D://dictionay for labels","dictionayForLabels.csv");// manualFillValueForHeader();
			//printValue(map);
			while ((line = br.readLine()) != null) 
			{
				lineCounter += 1;
				String[] readValue = line.split(cvsSplitBy);

				for (i = 0; i < readValue.length; i++) 
				{
					for(Map.Entry<String, String> entry : map.entrySet())
					{
						String str =readValue[i];
						if(!str.trim().equalsIgnoreCase(""))
						if (entry.getValue().contains(readValue[i])) 
						{
							//System.out.println("Line Count : " + lineCounter + "  readValue[i]" + readValue[i] + " = " + i);
								headerMap.put(i, entry.getKey());
						
							count += 1;
						}
					
					}
					
				}
				if (count >= 4) 
				{
					
					storeValue = FileUploadProcessTemplete.mapperValueWithHeader(headerMap, line, cvsSplitBy,map,readingFileCount);
					
					if(readingFileCount==0)
						flag = false;
					else
						flag=true;
					readingFileCount+=1;
				}
				if (flag && storeValue.size() > 0) 
				{
					// System.out.println("===="+lineCounter);
					outerMap.put(lineCounter + "", storeValue);

					flag = false;
				}

				// printValue(storeValue);
			}
			FileUploadProcessTemplete.printNestedMapValue(outerMap);
			System.out.println("Size ="+outerMap.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
		return outerMap;
	}


	/*public Map<String, String> normalClosure() {
		Map<String, String> map = new HashMap<String, String>();
		String csvFile = "D://Narrasion//Narrasion.csv";
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

	public BufferedReader getConnection(String path) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return br;
	}

	public void closeConnction(BufferedReader br) {
		if (br != null) {
			try {
				br.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public Map<String, String> fillMapValue(Map<String, String> map,
			String line, String cvsSplitBy) {
		String[] strArr = line.split(cvsSplitBy);
		String mapKey = "";
		String mapValue = "";
		if (strArr.length > 0 && strArr.length < 2) {
			mapKey = strArr[0];
			mapValue = strArr[0];
		} else if (strArr.length > 1) {

			for (int i = 0; i < strArr.length; i++) {
				mapKey = strArr[i].trim();
				mapValue = strArr[i].trim(); // map.put(strArr[0],
										// strArr.length>1?strArr[0])
				map.put(mapKey, mapValue);
			}

		}

		return map;
	}

	public Map<String, String> mapperValueWithHeader(
			Map<Integer, String> headerMap, String line, String cvsSplitBy) {
		boolean flag = false;
		Map<String, String> valuerMap = new HashMap<String, String>();
	//	Map<String, Map<String, String>> outerMap = new HashMap<String, Map<String, String>>();
		String[] strArr = line.split(cvsSplitBy);
		String mapKey = "";
		String mapValue = "";
		for (int i = 0; i < strArr.length; i++) {
			if (headerMap.get(i) != null) {
				flag = true;
				mapValue = strArr[i]; // map.put(strArr[0],
										// strArr.length>1?strArr[0])
				valuerMap.put(headerMap.get(i), mapValue);
				
			}
			if (flag)
				outerMap.put(i + "", valuerMap);
		}
		
		return valuerMap;
	}

	public void printValue(Map<String, String> map) {

		for (Map.Entry<String, String> entry : map.entrySet())
			System.out.println("getKey: " + entry.getKey() + "  getValue: "
					+ entry.getValue());
	}

	public void printNestedMapValue(Map<String, Map<String, String>> map) {

		// map.entrySet();
		for (Map.Entry<String, Map<String, String>> entry : map.entrySet())
			System.out.println("getKey: " + entry.getKey() + "  getValue: "
					+ entry.getValue());
		
		
		Set<Entry<String, Map<String, String>>>  set  = map.entrySet(); 
		Iterator<Entry<String, Map<String, String>>> it =set.iterator();
		while (it.hasNext()) {
			HashMap<String,  String> entry = (HashMap<String, String>) it
					.next();
			System.out.println("getKey: "+entry);
		}
		
		
			
		
		}*/
}
