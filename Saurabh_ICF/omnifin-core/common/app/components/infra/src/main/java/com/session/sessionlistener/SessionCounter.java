package com.session.sessionlistener;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.login.roleManager.LoginManger;
import com.login.roleManager.UserObject;


public class SessionCounter implements HttpSessionListener {

	private static int activeSessions = 0;

	public void sessionCreated(HttpSessionEvent se) {
		activeSessions++;
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		if(activeSessions > 0)
			activeSessions--;
		
		HttpSession session = se.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
	
	LoginManger loginMgr = new LoginManger();
		try {
			loginMgr.saveLogOutDetails(userobj.getUserId());
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getActiveSessions() {
		return activeSessions;
	}
}