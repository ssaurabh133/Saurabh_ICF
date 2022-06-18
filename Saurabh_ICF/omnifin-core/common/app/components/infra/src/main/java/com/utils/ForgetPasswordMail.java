package com.utils;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.*;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

public class ForgetPasswordMail {

	private static final Logger logger = Logger.getLogger(ForgetPasswordMail.class.getName());


      
    public synchronized static boolean sendMail(String userName, String passWord, String host, String port, String starttls,String to,String subject, String text)
    {      
        
        boolean send=false;
        try {
            Properties props = new Properties();
            props.setProperty("mail.host", host);
            props.setProperty("mail.smtp.port", port);
            //props.setProperty("mail.smtp.auth", "true");
           // props.setProperty("mail.smtp.starttls.enable", starttls);

            Session session=null;
            String qry="select parameter_value from parameter_mst where parameter_key='SMTP_AUTH' ";
			String result=ConnectionDAO.singleReturn(qry);
            Authenticator auth = new SMTPAuthenticatorPassword(userName,passWord);
            logger.info("After Authenticator MAil auth:------------"+auth+" passWord-->"+passWord+" userName--->"+userName);
           // String message=CommonFunction.checkNull(ConnectionDAO.singleReturn("select MESSAGE from comm_sms_data where event_name='CHANGE_PASSWORD' limit 1"));
			if(CommonFunction.checkNull(result).equalsIgnoreCase("true"))
			    {
				session = Session.getInstance(props, auth);
			    }
			    else{
			    	session = Session.getInstance(props);
			    }
            // session = Session.getInstance(props, auth);
			session.setDebug(true);
            logger.info("cheksession-->");

            MimeMessage msg = new MimeMessage(session);
            logger.info("msg"+msg);
            msg.setText(text);
            msg.setContent("Dear User,<br><br>We have received a request to reset your password.<br>Your New Password is '"+text+"'", "text/Html");
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(userName));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            Transport.send(msg);
            logger.info("Mail send:------------");
            send=true;

        } catch (AuthenticationFailedException ex) {
           
        } catch (AddressException ex) {
            
        } catch (MessagingException ex) {
           
        }
           
        return send;
    }
}
class SMTPAuthenticatorPassword extends Authenticator {

    private PasswordAuthentication authentication;

    public SMTPAuthenticatorPassword(String login, String password) {
        authentication = new PasswordAuthentication(login, password);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }
}
    

