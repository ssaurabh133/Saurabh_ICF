package com.caps.capsUtills;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Logger;



public class CapsUtills {
	private static final Logger logger = Logger.getLogger(CapsUtills.class.getName());
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	
	  private final static int fONE_DAY = 1;
	  private final static int fFOUR_AM =  Integer.parseInt(resource.getString("lbl.autoMailTimeHour"));
	  private final static int fZERO_MINUTES =Integer.parseInt(resource.getString("lbl.autoMailTimeMin"));

	  public static Date getTomorrowMorning4am(){
		logger.info("autoMailTime Hour:------"+fFOUR_AM);
		logger.info("autoMailTime fZERO_MINUTES:------"+fZERO_MINUTES);
	    Calendar tomorrow = new GregorianCalendar();
	    tomorrow.add(Calendar.DATE, fONE_DAY);
	    Calendar result = new GregorianCalendar(
	      tomorrow.get(Calendar.YEAR),
	      tomorrow.get(Calendar.MONTH),
	      tomorrow.get(Calendar.DATE),
	      fFOUR_AM,
	      fZERO_MINUTES
	    );
	    return result.getTime();
	  }
	 
}
