package com.session.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class SessionRenew {
	private static final Logger logger = Logger.getLogger(SessionRenew.class.getName());	

	
	public static HttpSession  recreateSesssion (HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		
				
	   
	    {
	    	logger.info("Old session id during login: "+session.getId());
		session.invalidate();
		session = request.getSession(true);
		logger.info("NEW session id during login: "+session.getId());
		return session;
		

	     }
	    }
}

	
