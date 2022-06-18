package com.communication.engn.utils;

import javax.mail.*;
import javax.mail.internet.*;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

import java.util.*;


public class SendMailUsingAuthentication
{
 
	
	 private static  String SMTP_HOST_NAME ;//or simply "localhost"
	 private static  String SMTP_AUTH_USER ;
	 private static  String SMTP_AUTH_PWD ;
	 private static  String SMTP_PORT ;
 
   
 public SendMailUsingAuthentication(String SMTP_HOST_NAME,String SMTP_AUTH_USER,String SMTP_AUTH_PWD,String SMTP_PORT)
 {
	  this.SMTP_HOST_NAME=SMTP_HOST_NAME;
	  this.SMTP_AUTH_USER=SMTP_AUTH_USER;
	  this.SMTP_AUTH_PWD=SMTP_AUTH_PWD;
	  this.SMTP_PORT=SMTP_PORT;
  }

  public void mailAuthenticationFunc(String[] emailList ,String emailSubjectTxt,String emailMsgTxt,String emailFromAddress) throws Exception
  {
    postMail( emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
    System.out.println("Sucessfully  mail sent********");
  }

  public void postMail( String recipients[ ], String subject,String message , String from) throws MessagingException, AuthenticationFailedException
  {

	  String qry="select parameter_value from parameter_mst where parameter_key='SMTP_AUTH' ";
	  String result=ConnectionDAO.singleReturn(qry);
	  System.out.println("Authentication:::::"+result);
    Properties props = new Properties();
    props.setProperty("mail.host", SMTP_HOST_NAME);
    props.setProperty("mail.smtp.port", SMTP_PORT);
    if(CommonFunction.checkNull(result).equalsIgnoreCase("true"))
    {
    props.setProperty("mail.smtp.auth", "true");
    }
    props.setProperty("mail.smtp.starttls.enable", "true");    
    Authenticator auth = new SMTPAuthenticator(SMTP_AUTH_USER,SMTP_AUTH_PWD);
   // Authenticator auth1 = new SMTPAuthenticator("","");  
    Session session=null;
    if(CommonFunction.checkNull(result).equalsIgnoreCase("true"))
    {
     session = Session.getInstance(props, auth);
    }
    else{
    	 session = Session.getInstance(props);
    }
    session.setDebug(true);
    MimeMessage msg = new MimeMessage(session);
    msg.setContent(message, "text/Html");
    msg.setSubject(subject);    
    InternetAddress addressFrom = new InternetAddress(SMTP_AUTH_USER);
    msg.setFrom(addressFrom);   
    String addressTo="customercare@inteccapital.com";
    if(recipients.length>0)
    addressTo=recipients[0];
    //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
    msg.addRecipients(Message.RecipientType.TO,InternetAddress.parse(addressTo));
    Transport.send(msg);
    System.out.println("This is regarding sending mail check");
    
    System.out.println("This is regarding sending mail check Part Twoooo");
 }

  class SMTPAuthenticator extends Authenticator {
	    private PasswordAuthentication authentication;
	    public SMTPAuthenticator(String login, String password) {
	        authentication = new PasswordAuthentication(login, password);
	    }
	    protected PasswordAuthentication getPasswordAuthentication() {
	        return authentication;
	    }
	}
  
  public void postMailCC( String recipientsTO[ ],String recipientsCC[ ], String subject,String message , String from) throws MessagingException, AuthenticationFailedException
  {

	  String qry="select parameter_value from parameter_mst where parameter_key='SMTP_AUTH' ";
	  String result=ConnectionDAO.singleReturn(qry);
	  System.out.println("Authentication:::::"+result);
    Properties props = new Properties();
    props.setProperty("mail.host", SMTP_HOST_NAME);
    props.setProperty("mail.smtp.port", SMTP_PORT);
    if(CommonFunction.checkNull(result).equalsIgnoreCase("true"))
    {
    props.setProperty("mail.smtp.auth", "true");
    }
    props.setProperty("mail.smtp.starttls.enable", "true");    
    Authenticator auth = new SMTPAuthenticator(SMTP_AUTH_USER,SMTP_AUTH_PWD);
   // Authenticator auth1 = new SMTPAuthenticator("","");  
    Session session=null;
    if(CommonFunction.checkNull(result).equalsIgnoreCase("true"))
    {
     session = Session.getInstance(props, auth);
    }
    else{
    	 session = Session.getInstance(props);
    }
    session.setDebug(true);
    MimeMessage msg = new MimeMessage(session);
    msg.setContent(message, "text/Html");
    msg.setSubject(subject);    
    InternetAddress addressFrom = new InternetAddress(SMTP_AUTH_USER);
    msg.setFrom(addressFrom);   
   
    String addressTo="customercare@inteccapital.com";
    if(recipientsTO.length>0)
    addressTo=recipientsTO[0];
    
    System.out.println("addressTO-->"+addressTo);
    
    String addressCC="customercare@inteccapital.com";
    if(recipientsCC.length>0)
    addressCC=recipientsCC[0];
    System.out.println("addressCC-->"+addressCC);
    
   // msg.addRecipients(Message.RecipientType.TO, addressTo);
    //msg.addRecipients(Message.RecipientType.CC, addressCC);
   
    msg.addRecipients(Message.RecipientType.TO,InternetAddress.parse(addressTo));
    msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(addressCC));
    
    Transport.send(msg);
    System.out.println("This is regarding sending mail check");
    
    System.out.println("This is regarding sending mail check Part Twoooo");
 }
  
  public void mailAuthenticationFuncCC(String[] ToemailList ,String[] CCemailList,String emailSubjectTxt,String emailMsgTxt,String emailFromAddress) throws Exception
  {
    postMailCC( ToemailList,CCemailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
    System.out.println("Sucessfully  mail sent********");
  }

}

