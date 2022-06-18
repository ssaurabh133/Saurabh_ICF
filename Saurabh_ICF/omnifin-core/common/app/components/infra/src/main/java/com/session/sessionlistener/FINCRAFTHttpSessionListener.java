package com.session.sessionlistener;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.cp.actions.ApproveCustomerAction;
import com.logger.LoggerMsg;
import com.login.roleManager.LoginManger;
import com.login.roleManager.UserObject;




public class FINCRAFTHttpSessionListener implements 
		HttpSessionListener {
	private static final Logger logger = Logger.getLogger(ApproveCustomerAction.class.getName());	
	private int sessionCount;
	

	public FINCRAFTHttpSessionListener() {
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
		if(userobj!=null)
		{
				String userId=userobj.getUserId();
				
			  LoginManger loginMgr = new LoginManger();
			  try {
				boolean status=loginMgr.saveLogOutDetails(userId);
				logger.info("status: "+status);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
