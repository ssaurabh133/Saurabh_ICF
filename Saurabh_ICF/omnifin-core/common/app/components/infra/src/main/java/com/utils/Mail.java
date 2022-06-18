package com.utils;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	private static final Logger logger = Logger.getLogger(Mail.class.getName());


      
    public synchronized static boolean sendMail(String userName, String passWord, String host, String port, String starttls,String to,String subject, String text)
    {      
        
        boolean send=false;
        try {
            Properties props = new Properties();
            props.setProperty("mail.host", host);
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", starttls);

            Authenticator auth = new SMTPAuthenticator(userName,passWord);
            logger.info("After Authenticator MAil auth:------------"+auth);
            Session session = Session.getInstance(props, auth);

            MimeMessage msg = new MimeMessage(session);
            msg.setText(text);
            msg.setContent("", "text/Html");
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
class SMTPAuthenticator extends Authenticator {

    private PasswordAuthentication authentication;

    public SMTPAuthenticator(String login, String password) {
        authentication = new PasswordAuthentication(login, password);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }
}
    

