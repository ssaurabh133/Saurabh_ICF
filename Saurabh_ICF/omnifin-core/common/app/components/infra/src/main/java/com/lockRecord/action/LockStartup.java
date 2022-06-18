package com.lockRecord.action;



import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.caps.capsUtills.CapsUtills;
import com.caps.capsUtills.TimeScheduler;
import com.commonFunction.dao.commonDao;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

import java.util.ArrayList;



public class LockStartup extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(LockStartup.class.getName());	

	public void init() throws ServletException
	{
		   ServletContext context = getServletContext();
		   String q="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'EOD_BOD_FLAG'";
		   String newFlg=ConnectionDAO.singleReturn(q);
			if(newFlg.equalsIgnoreCase("E"))
				 context.setAttribute("BODCheck", "open");
			else
				 context.setAttribute("BODCheck", "block");
			java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");//Added by ajay   31/03/2016
			String Viability=resource.getString("tab.Viability");
			String fleetDetail=resource.getString("tab.fleetDetail");
			String SarvaSurksha=resource.getString("tab.SarvaSurksha");
			context.setAttribute("Viability", Viability);
			context.setAttribute("fleetDetail", fleetDetail);
			context.setAttribute("SarvaSurksha", SarvaSurksha);
			logger.info("Viability ....Flag......."+Viability);
			logger.info("fleetDetail....Flag......."+fleetDetail);
			logger.info("SarvaSurksha....Flag......."+SarvaSurksha);
			 String q1="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'LOV_COUNT_QUERY'";
			 String pagination=ConnectionDAO.singleReturn(q1);
			 if(CommonFunction.checkNull(pagination).equalsIgnoreCase(""))
				 pagination="Y";
			 context.setAttribute("pagination", pagination);
			 
		   LockSingleton singleton =  LockSingleton.getInstance();
		 
		   logger.info("LockStartup load on startup 1 ...........");
	
		   TimerTask schedule = new TimeScheduler();
		   Timer timer = new Timer();
			Date date=new Date();
			logger.info(" LockStartup load on startup:--"+date);
		   timer.scheduleAtFixedRate(schedule, CapsUtills.getTomorrowMorning4am(), 1000*60*60*24);
		   logger.info(" LockStartup recordAccessObject size .......... "+singleton.recordAccessObject.size());
		   context.setAttribute("recordAccessObject", singleton.recordAccessObject);
		   
	}
	
}
