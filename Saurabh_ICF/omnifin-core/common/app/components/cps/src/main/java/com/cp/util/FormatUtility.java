package com.cp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.connect.ConnectionDAOforEJB;
import com.ibm.icu.text.DateFormat;

public class FormatUtility {
	private static final Logger logger = Logger.getLogger(FormatUtility.class.getName());
		public static String dateFormat(Date date)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String returnDate = "";
			try {
				returnDate = dateFormat.format(date);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return returnDate;
		}
		
		public static String dateFormat(Date date,String formatType)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
			String returnDate = "";
			try {
				returnDate = dateFormat.format(date);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return returnDate;
		}
		
		public static String dateFormat(String date,String formatType)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
			String returnDate = "";
			Date d =null;
			try {
				d = dateFormat1.parse(date);
				returnDate = dateFormat.format(d);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			return returnDate;
		}
		public static String getYear(String date)
		{
			
			
			return date.substring(0,4);
		}
		public static String getMonth(String date)
		{
			
			
			return date.substring(5,7);
			
		}
		public static String getDay(String date)
		{
			
			
			return date.substring(8,10);
		}
		public static void main(String[] args) {
			String date="";
			System.out.println(getYear(date)+"--"+getMonth(date)+"--"+getDay(date));
		}
}
