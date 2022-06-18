package com.login.action;

import java.util.ArrayList;
import com.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.connect.DaoImplInstanceFactory;
import com.login.actionForm.LoginActionForm;
import com.login.dao.LoginDao;
import com.login.roleManager.UserObject;




public class ForgetLoginAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ForgetLoginAction.class.getName());
	
	String username=null;
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");


	public ActionForward getquestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		 username=request.getParameter("name");
			logger.info("in ForgetLoginAction----- name----"+username);
			 String result=null;
			 if(request.getParameter("name")!=null)
			 {
				 username=request.getParameter("name");
			 }
			 LoginDao logindao=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
			    logger.info("Implementation class: "+logindao.getClass());
			int count=logindao.matchuserid(username);
		
		 if((!username.equalsIgnoreCase(""))&&(count>0)){
			
		 ArrayList list=null;
		 list=logindao.question(username);
		 request.setAttribute("questionList", list);
		 request.setAttribute("name", username);
	
		 }
	 else
		 {
		 request.setAttribute("login","error");
		 
		 }
		return mapping.findForward("successlog");
	}
	public ActionForward checkanswers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginActionForm  loginForm = (LoginActionForm) form;
		UserObject userobj=new UserObject();

		if(request.getParameter("name")!=null)
		 {
			 username=request.getParameter("name");
		 }
			logger.info("in checkanswers----- name----"+username);
		 String result=null;
		 ArrayList list=null;
		 LoginDao logindao=(LoginDao)DaoImplInstanceFactory.getDaoImplInstance(LoginDao.IDENTITY);
		    logger.info("Implementation class: "+logindao.getClass());
		 if(!username.equalsIgnoreCase("")){
			 list=logindao.question(username);
			 request.setAttribute("questionList", list);
			 request.setAttribute("name", username);
		 int counter=logindao.matchAnswer(username,loginForm);
		 String businessdate=logindao.businessdate();
		 if(counter>0){
			 
		//	 request.setAttribute("answer","correct"); 
			 String password=logindao.updateUserPassword(username,businessdate);
			//*******************Asesh Kumar   **********************************************************************
			 String userMailId=logindao.getuserEmailId(username);
			 String smtpHost=logindao.getSmtpHost();
			 String smtpPort=logindao.getSmtpPort();
			 String smtpMail=logindao.getSmtpMailAddress();
			 String smtpPassword=logindao.getSmtpMailPassword();
			 logger.info("user email   ************************="+userMailId);
			 logger.info("smtpHost is  *************************= "+smtpHost);
			 logger.info("smtpPort is  *************************= "+smtpPort);
			 ForgetPasswordMail.sendMail(smtpMail,smtpPassword,smtpHost,smtpPort,"true",userMailId,"OmniFin-login Password Changed",password);
			 logger.info("New generated password "+password);
			//*******************Asesh Kumar end  **********************************************************************
			 String answer=password;
			 request.setAttribute("answer","correct");	  
		 }else
		 {
			 request.setAttribute("answer","incorrect");	 
		 }
		 }
		return mapping.findForward("successlog");
	}
	//public ActionForward sendemail(ActionMapping mapping, ActionForm form,
	//		HttpServletRequest request, HttpServletResponse response)
		//	throws Exception {
		//String port="";
		//String host="";
		//String companyemail="";
		//String useremail="";
	//	String userpass="";
		//String subject=resource.getString("msg.emailsubject");
		
		// username=request.getParameter("name");
		// logger.info("in sendemail----- name----"+username);
		//String password=request.getParameter("password");
	//	port=logindao.portno();
	//	host=logindao.hostname();
		//companyemail=logindao.companyemail();
		//userpass=logindao.userpass();
		//useremail=logindao.useremail(username);
		//String bodytext="Your password corresponding to your email is:"+password;
		// logger.info("in sendemail----- bodytext----"+bodytext);
	    // FirstMail fm = new FirstMail();
		// request.setAttribute("port",port);	 
		// request.setAttribute("host",host);	 
		// request.setAttribute("companyemail",companyemail);	 
		// request.setAttribute("useremail",useremail);	
		// request.setAttribute("bodytext",bodytext);
		// request.setAttribute("subject",subject);
		// request.setAttribute("userpass",userpass);
        // fm.sendMail(companyemail,host,port, useremail, bodytext, subject);
         
	//	return mapping.findForward("mailSend");
//	}
	
	
}
	
