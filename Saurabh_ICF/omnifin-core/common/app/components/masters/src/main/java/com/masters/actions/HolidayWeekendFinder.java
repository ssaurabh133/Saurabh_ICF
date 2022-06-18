/**
 * This class is used to find the weekend from given date in a year
 * @author Vishal Singh
 * @Date  31 March 2012
 * 
 */
package com.masters.actions;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.text.SimpleDateFormat;
public class HolidayWeekendFinder {
	
	private Calendar cal = null;
	private String year ;
	
	private ArrayList weekendList = null;
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	  String dateFormat = resource.getString("lbl.dateForDisbursal");
	
	public HolidayWeekendFinder(String year){
		this.year = year;
		
		cal = Calendar.getInstance();
		try{
             
			cal.setTime(new SimpleDateFormat(dateFormat).parse(this.year));
		}catch(java.text.ParseException e){
			System.err.println("Error parsing date: " + e.getMessage());
			e.printStackTrace();
		}
		
		weekendList = new ArrayList(53);
	}

	public ArrayList findWeekendsSunday(int x){
        
		
		while(cal.get(Calendar.YEAR) == Integer.valueOf(this.year.substring(6))){
    
		switch(cal.get(Calendar.DAY_OF_WEEK)){		
						case Calendar.SUNDAY:				
					if(cal.get(Calendar.WEEK_OF_MONTH)==x){
						weekendList.add(cal.getTime());					
					}
				break;
		   }
             
		cal.add(Calendar.DAY_OF_YEAR, 1);
	}
	return weekendList;
}
	
    public ArrayList findWeekendsSaturday(int x){
    	
    	
      
	while(cal.get(Calendar.YEAR) == (int)Integer.valueOf(this.year.substring(6))){
         
		switch(cal.get(Calendar.DAY_OF_WEEK)){
		
				case Calendar.SATURDAY:
					    if(cal.get(Calendar.WEEK_OF_MONTH)==x){
						weekendList.add(cal.getTime());
					      }
					    break;
				
		}
            
		cal.add(Calendar.DAY_OF_YEAR, 1);
	}	
	return weekendList;
}	


}
