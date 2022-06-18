package com.communication.engn.servlets;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

import com.connect.CommonFunction;

 public class MessageScheduler extends TimerTask 
 {
	 private final static long FIRST_TIME = 10000*60;
		private final static long TIME_INTERVAL = 1000*60*60*24;
	static String  path="";
    long currennTime = System.currentTimeMillis();
    public void run() 
    {
    	new CommMessageData().startEmailTask(path);
    	System.out.println("Sucessfully Sent mail to All Users");
		System.out.println("Scheduler will call start Next day at same time: ==>> "+new Date());
	}
    public void startMessageTask(String apath,String hour,String mimute,String second)
    {
    	path=apath;    	
    	Timer timer = new Timer();
		Calendar date = Calendar.getInstance();
	
		if(CommonFunction.checkNull(hour).trim().equalsIgnoreCase(""))
			hour="0";
		if(CommonFunction.checkNull(mimute).trim().equalsIgnoreCase(""))
			mimute="0";
		if(CommonFunction.checkNull(second).trim().equalsIgnoreCase(""))
			second="0";		
		int hr=Integer.parseInt(CommonFunction.checkNull(hour).trim().trim());
		int mnt=Integer.parseInt(CommonFunction.checkNull(mimute).trim().trim());
		int sct=Integer.parseInt(CommonFunction.checkNull(second).trim().trim());		
		date.set(Calendar.HOUR, hr);
		date.set(Calendar.MINUTE, mnt);
		date.set(Calendar.SECOND, sct);
		date.set(Calendar.MILLISECOND, 0);
		// Schedule to run every midnight	
		timer.schedule(new MessageScheduler(),date.getTime(),1000*60*60*24);	
			
    }
}