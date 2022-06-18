package com.business.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class PropertyConfigurationController {

	private static String fileName;
	private static String returnKey;
	private static String returnParseDate;
	private static String uigetDate;
	private static String changedbformat;
	private static String UIgetDateAndTime;
	/**
	 * 
	 * @param requestDate
	 * @return the DB Format date This Method will take input as UI date format
	 *         and changed To DB format yyyy-MM-dd utill.properties is the
	 *         propertie file is configured all types format in that
	 * 
	 */
	public static String getDateConvertion(String requestDate) {

		try {
			if(requestDate.length() >=20)
			{
				Date date=new Date();
				SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy");
				String finals=dateFormat.format(date);
				requestDate=finals;
				
			}
			fileName = "com.yourcompany.struts.utill.properties";
			Properties properties = PropertyLoader.loadProperties(fileName);

			String readKey = "UIDateParse";
			String nextKey = "UIgetDate";
			returnKey = properties.getProperty(readKey);
			uigetDate = properties.getProperty(nextKey);
			SimpleDateFormat dateFormat=new SimpleDateFormat(uigetDate);
			Date UIdate = dateFormat.parse(requestDate);

			returnParseDate = new SimpleDateFormat(returnKey).format(UIdate);

			System.out.println(returnParseDate);
			// }

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return returnParseDate;

	}
	
	public static String getDateForMatePatternForUI() {

		try {
			fileName = "com.yourcompany.struts.utill.properties";
			Properties properties = PropertyLoader.loadProperties(fileName);			
			String nextKey = "UIgetDate";			
			uigetDate = properties.getProperty(nextKey);				

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return uigetDate;

	}
	 public static  String getDateAndTimeFormatPatternForUI(String  dateandtime)  {
		 String outputDate=null;
		 SimpleDateFormat outputDateFormat=null;
		 fileName = "com.yourcompany.struts.utill.properties";
		 Properties properties = PropertyLoader.loadProperties(fileName);
		 String nextKey = "UIgetDateAndTime";	
		 SimpleDateFormat dateFormat=null;
		 try{
			 
			 uigetDate = properties.getProperty(nextKey);
			 dateFormat=new SimpleDateFormat(uigetDate);
	     Date pardedDate = (Date)dateFormat.parse(dateandtime);
	     outputDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	     outputDate=outputDateFormat.format(pardedDate);
	     System.out.println(outputDate);
		 }catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
	     return(outputDate);
	    
		  }
	 public static  String getDateAndMonthFormatPatternForUI(String  dateandtime)  {
		  String outputDate=null;
		  SimpleDateFormat outputDateFormat=null;
		  fileName = "com.yourcompany.struts.utill.properties";
			 Properties properties = PropertyLoader.loadProperties(fileName);
			 String nextKey = "UIDateParse";	
			 SimpleDateFormat dateFormat=null;
		   
		  try{
			  uigetDate = properties.getProperty(nextKey);
			  dateFormat=new SimpleDateFormat(uigetDate);
	       Date pardedDate = (Date)dateFormat.parse(dateandtime);
	       outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	       outputDate=outputDateFormat.format(pardedDate);
		 }catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
	     return(outputDate);
	    
		  }
	 public static String getGenericDateForMatePatternForUI(String format) {

			try {
				fileName = "com.yourcompany.struts.utill.properties";
				Properties properties = PropertyLoader.loadProperties(fileName);			
				String nextKey = format;			
				uigetDate = properties.getProperty(nextKey);				

			}

			catch (Exception e) {
				e.printStackTrace();
			}
			return uigetDate;

		}
	 
	 
	 

public static String changeuidatewithtimetodbFormatTime(String format) {
	String outputdta=null;
		try {
			fileName = "com.yourcompany.struts.utill.properties";
			Properties properties = PropertyLoader.loadProperties(fileName);	
			
			String  readkey="changedbformat";
			String  nextkey="UIgetDateAndTime";
			changedbformat = properties.getProperty(readkey);
			UIgetDateAndTime=properties.getProperty(nextkey);
			SimpleDateFormat dateFormat=new SimpleDateFormat(changedbformat);
			
			Date changeddate=(Date)dateFormat.parse(format);
			
			SimpleDateFormat dateFormat2=new SimpleDateFormat(UIgetDateAndTime);
			
			outputdta=dateFormat2.format(changeddate);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return outputdta;
	}

	 
	
	
	 
	 
	 
	 
}
