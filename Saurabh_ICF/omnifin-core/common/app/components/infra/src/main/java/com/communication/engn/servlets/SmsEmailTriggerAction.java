/*start  : Abhishek Mathur*/

package com.communication.engn.servlets;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class SmsEmailTriggerAction extends DispatchAction {
	

	public ActionForward callSmsEmailTrigger(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		new CommMessageData().startEmailTask("WINDOWS");
		System.out.println("WINDOWS Sucessfully Sent mail to All Users");
		System.out.println("WINDOWS Scheduler will call start Next day at same time: ==>> "+new Date());
			
		return null;
	}
	
	
}