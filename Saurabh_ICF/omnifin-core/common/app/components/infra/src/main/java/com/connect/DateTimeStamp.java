package com.connect;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


public class DateTimeStamp {
    private static final Logger logger = Logger.getLogger(DateTimeStamp.class.getName());

    //Static literals
    private static final String DEFAULT_DATE_FORMAT = "dd-mmm-yyyy";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private static final String STRING_DELIMITER = ":";

    public DateTimeStamp() {
    }

    /**
     * Returns a java.sql.Tilmestamp object corresponding to the date
     */
    public Timestamp getTimeStamp(Date date) {
        Timestamp timeStamp = new Timestamp(date.getTime());

        return timeStamp;
    }

    /**
     * Returns a java.sql.Tilmestamp object corresponding to a date in string format
     */
    public Timestamp getTimeStamp(String date) {
        Timestamp timeStamp = null;

        if ((date == null) || date.equals("")) {
            return null;
        }

        try {
            Date userDate = (new SimpleDateFormat(DEFAULT_DATE_FORMAT)).parse(date);

            timeStamp = new Timestamp(userDate.getTime());
        } catch (ParseException ex) {
            logger.error("In Timestamp ParseException():: " + ex.getMessage());
        }

        return timeStamp;
    }

    /**
     *  Returns a java.sql.Tilmestamp object corresponding to a date and time
     */
    public Timestamp getTimeStamp(String date, String time) {
        Timestamp timeStamp = null;

        try {            

            Date userDate = (new SimpleDateFormat(DEFAULT_DATE_FORMAT)).parse(date);
            StringTokenizer tokenizer = new StringTokenizer(time,
                    STRING_DELIMITER);

            int intCount = 0;

            while (tokenizer.hasMoreElements()) {
                if (intCount == 0) {                    
                    intCount = intCount + 1;
                } else {                    
                }
            }

            timeStamp = new Timestamp(userDate.getTime());
        } catch (ParseException ex) {
            logger.error("In getTimeStamp() ParseException is:: " + ex.getMessage());
        }

        return timeStamp;
    }

    /**
     * Returns the date
     */
    public String getDate(Timestamp timeStamp) {
        if (timeStamp == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

            String date = sdf.format(timeStamp);

            return date;
        }
    }

    /**
      * Returns the current system date
      */
    public String getSystemdate() {
        String strSystemDate = null;
        Date date = new Date();

        Timestamp timeStamp = null;
        timeStamp = getTimeStamp(date);

        strSystemDate = timeStamp.toString().substring(0,
                timeStamp.toString().indexOf(" "));
        strSystemDate = strSystemDate + " " + getTime(timeStamp);

        return strSystemDate;
    }


    public String getTime(Timestamp timeStamp) {
        if (timeStamp == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
            String date = sdf.format(timeStamp);

            return date;
        }
    }

    public int getDayDiff(Timestamp t1, Timestamp t2) {
        return 0;
    }

    public int getDayDiff(Date d1, Date d2) {
        long mill = 1000 * 60 * 60 * 24;

        long result = (d1.getTime() - d2.getTime()) / mill;

        int diff = new Integer(new Long(result).toString()).intValue();

        return diff;
    }

    public Date getSysdate() {
        return new Date();
    }

     public static Date getDate(String dateTime,String dateTimeFormat) {
	        Date userDate = null;

	        try {

	            if ((dateTime == null) || dateTime.equals("")) {
		                return null;
	        	}

	            if ((dateTimeFormat == null) || dateTimeFormat.equals("")) {
		    	                return null;
	        	}


	            userDate = (new SimpleDateFormat(dateTimeFormat)).parse(dateTime);

	        } catch (ParseException ex) {
	            logger.error("In getDate ParseException():: " + ex.getMessage());
	        }

	        return userDate;
    }
}
