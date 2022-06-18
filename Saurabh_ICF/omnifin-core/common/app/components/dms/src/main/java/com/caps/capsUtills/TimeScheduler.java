package com.caps.capsUtills;

import java.util.TimerTask;
import java.util.logging.Logger;
import com.caps.dao.CollDAO;
import com.connect.DaoImplInstanceFactory;

public class TimeScheduler extends TimerTask{
	
	private static final Logger logger = Logger.getLogger(TimeScheduler.class.getName());
			
	  /**
	  * Implements TimerTask's abstract run method.
	  */
	  public void run(){
	    //toy implementation
		  CollDAO collDAO=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY);
		  logger.info("Implementation class: "+collDAO.getClass());
	    System.out.println("esclatedCaseSendMailToUserAuto Is called...");
	    collDAO.esclatedCaseSendMailToUserAuto();
	  }

	  // PRIVATE ////

	  //expressed in milliseconds
	 

}
