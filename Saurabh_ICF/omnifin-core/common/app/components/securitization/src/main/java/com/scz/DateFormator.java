package com.scz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateFormator {
	static Logger logger = Logger.getLogger(DateFormator.class);

	public static void main(String[] args) {
		try{
			//String dt = "04-02-2016";  
			//String dt1 = dateFrom(dt);
		
		}catch (Exception e) {
			
		}
	}
	
	
	public static String dateConvert(String D) {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = null;
		try {
			date = format1.parse(D);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = format2.format(date);
		return ((dateString));
	}
	
	
	
	public static String ymdToDmy(String D) {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = format1.parse(D);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = format2.format(date);
		return ((dateString));
	}

	
	public static String dmyToYMD(String D) {

		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format1.parse(D);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = format2.format(date);
		return ((dateString));
	}

	public static String dateFrom(String inDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatter1 = new SimpleDateFormat("EEEE, dd-MMM-yyyy a");
		String strDate = "";
		try {
			Date date = formatter.parse(inDate);
			strDate = formatter1.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	public static void daysBetweenDates(String fromDate, String toDate) {

		// HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(fromDate);
			d2 = format.parse(toDate);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			// long diffSeconds = diff / 1000 % 60;
			// long diffMinutes = diff / (60 * 1000) % 60;
			// long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			// System.out.print(diffHours + " hours, ");
			// System.out.print(diffMinutes + " minutes, ");
			// System.out.print(diffSeconds + " seconds.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void compareTwoDates() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse("2009-12-31");
			Date date2 = sdf.parse("2010-01-31");

			System.out.println(sdf.format(date1));
			System.out.println(sdf.format(date2));

			if (date1.compareTo(date2) > 0) {
				System.out.println("Date1 is after Date2");
			} else if (date1.compareTo(date2) < 0) {
				System.out.println("Date1 is before Date2");
			} else if (date1.compareTo(date2) == 0) {
				System.out.println("Date1 is equal to Date2");
			} else {
				System.out.println("How to get here?");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static Date getNextDate(Date nowDate) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(nowDate);
	    c.add(Calendar.MONTH, 1);
	    c.set(Calendar.DATE, c.getMaximum(Calendar.DATE));
	    Date nextDate = c.getTime();
	    return nextDate;
	}
	
	public static int monthsBetween(Date minuend, Date subtrahend)  
	{  
	Calendar cal = Calendar.getInstance();  
	cal.setTime(minuend);  
	int minuendMonth =  cal.get(Calendar.MONTH);  
	int minuendYear = cal.get(Calendar.YEAR);  
	cal.setTime(subtrahend);  
	int subtrahendMonth =  cal.get(Calendar.MONTH);  
	int subtrahendYear = cal.get(Calendar.YEAR);  
	 
	return ((minuendYear - subtrahendYear) * cal.getMaximum(Calendar.MONTH)) +    
	(minuendMonth - subtrahendMonth);  
	}  
	
	
	
	
	public static String dmyToMonths(String D) {

		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = null;
		try {
			date = format1.parse(D);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = format2.format(date);
		return ((dateString));
	}
	public static String increaseMonth(String inDate)  {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(inDate));
			c.add(Calendar.MONTH, 1);  
			inDate = sdf.format(c.getTime());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return inDate;
	}
	
	public static String decreaseMonth(String inDate)  {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(inDate));
			c.add(Calendar.MONTH, -1);  
			inDate = sdf.format(c.getTime());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return inDate;
	}
	
	public static Date strToDate(String D) {

		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = format1.parse(D);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (date);
	}
	
	public static String lastDateOfMonth(String inDate)  {
		Date lastDayOfMonth=null;
		DateFormat sdf=null;
		try{
			Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(strToDate(inDate)); 
	        calendar.add(Calendar.MONTH, 1);  
	        calendar.set(Calendar.DAY_OF_MONTH, 1);  
	        calendar.add(Calendar.DATE, -1);  

	        lastDayOfMonth = calendar.getTime();  
	        sdf = new SimpleDateFormat("dd-MM-yyyy");  
		}catch (Exception e) {
			// TODO: handle exception
		}
		return sdf.format(lastDayOfMonth);
	}
	
	public static Date strDateToDateTime(String D) {

		DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    Date date = new Date();
		return (date);
	}
	
	public static java.sql.Date dmyToSQL(String date){
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date parsed=null;
		try{
        	 parsed = format.parse(date);
        }catch(Exception e){
        	
        }
		java.sql.Date sql = new java.sql.Date(parsed.getTime());
		return sql;
	}
	
	public void methodCall(){
		java.sql.Date dt = dmyToSQL("12-05-2014");
		System.out.println(dt);
		DateFormator df=new DateFormator();
		df.setInstlDate(dt);
		System.out.println(df.getInstlDate());
	}
	
	private java.sql.Date instlDate;
	
	public java.sql.Date getInstlDate() {
		return instlDate;
	}
	public void setInstlDate(java.sql.Date instlDate) {
		this.instlDate = instlDate;
	}
	
}
