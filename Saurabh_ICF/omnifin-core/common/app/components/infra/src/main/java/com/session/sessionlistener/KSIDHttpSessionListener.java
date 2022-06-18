package com.session.sessionlistener;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.logger.LoggerMsg;
import com.login.roleManager.LoginManger;
import com.login.roleManager.UserObject;




public class KSIDHttpSessionListener implements 
		HttpSessionListener {

	private int sessionCount;
	

	public KSIDHttpSessionListener() {
		this.sessionCount = 0;
	}

	/**
	 * Record the fact that a session has been created.
	 * 
	 * @param se
	 *            The session event
	 */
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		
		
		//session.setMaxInactiveInterval(60);
		synchronized (this) {
			sessionCount++;
		}
		String id = session.getId();
		
		//session.setAttribute("isActive", true);

		Date now = new Date();
		String message = new StringBuffer("New Session created on ").append(
				now.toString()).append("--ID: ").append(id).append("  :---")
				.append("There are now ").append("" + sessionCount).append(
						" live sessions in the application.").toString();

		LoggerMsg.info(message);

		// System.out.println(message);

	}

	/**
	 * Record the fact that a session has been destroyed.
	 * 
	 * @param se
	 *            The session event
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		UserObject userobj=(UserObject)session.getAttribute("userobject");
		String id = session.getId();
	LoginManger loginMgr = new LoginManger();
		try {
			loginMgr.saveLogOutDetails(userobj.getUserId());
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (this) {
			--sessionCount;
		}
		String message = new StringBuffer("Session destroyed"
				+ "----:--Value of destroyed session ID is").append("" + id)
				.append(":--:").append("There are now ").append(
						"" + sessionCount).append(
						" live sessions in the application.").toString();

		LoggerMsg.info(message);

		

	}

	

}
