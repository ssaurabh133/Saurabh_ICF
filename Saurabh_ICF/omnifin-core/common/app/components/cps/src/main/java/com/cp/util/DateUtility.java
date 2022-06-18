package com.cp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class DateUtility {
	private static final Logger logger = Logger.getLogger(DateUtility.class.getName());
    private static List<SimpleDateFormat>     dateFormats = new ArrayList<SimpleDateFormat>() {{
            add(new SimpleDateFormat("M/dd/yyyy"));
            add(new SimpleDateFormat("dd.M.yyyy"));
            add(new SimpleDateFormat("M/dd/yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.M.yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.MMM.yyyy"));
            add(new SimpleDateFormat("dd-MMM-yyyy"));
            add(new SimpleDateFormat("dd-MM-yyyy"));
            add(new SimpleDateFormat("dd/MM/yyyy"));
            add(new SimpleDateFormat("dd/MMM/yyyy"));
        }
    };
 
   
    public static Date convertToDate(String input) {
    	
        Date date = null;
        if(null == input) {
            return null;
        }
        for (SimpleDateFormat format : dateFormats) {
            try {
                format.setLenient(false);
                date = format.parse(input.trim());
            } catch (ParseException e) {
                //Shhh.. try other formats
            }
            if (date != null) {
                break;
            }
        }
 
        return date;
    }
    
  public static  String getMonthInString(String no)
    {
    	String month = null;
    	int i;
		try {
			i = Integer.parseInt(no);
		} catch (NumberFormatException e) {
			i=0;
			e.printStackTrace();
		}
    	switch (i) {
    	case 1: month="Jan"; 		
    		break;
    	case 2: month="Feb"; 		
		break;
    	
    	case 3: month="Mar"; 		
		break;
    	case 4: month="Apr"; 		
		break;
    	case 5: month="May"; 		
		break;
    	case 6: month="Jun"; 		
		break;
    	case 7: month="Jul"; 		
		break;
    	case 8: month="Aug"; 		
		break;
    	case 9: month="Sep"; 		
		break;
    	case 10: month="Oct"; 		
		break;
    	case 11: month="Nov"; 		
		break;
    	case 12: month="Dec"; 		
		break;

    	default:
    		month="Invalid month";
    		break;
    	}
    	return month;
    }

  		public static Date convertToDate(String input, String formatType)
  		{
  			Date date = null;
  	        if(null == input) {
  	            return null;
  	        }
  	        SimpleDateFormat format = new SimpleDateFormat(formatType);
  	    
  	            try {
  	                format.setLenient(false);
  	                date = format.parse(input.trim());
  	            } catch (ParseException e) {
  	               e.printStackTrace();
  	            }
  	            
  	        
  	 
  	        return date;
  		}
  
}

