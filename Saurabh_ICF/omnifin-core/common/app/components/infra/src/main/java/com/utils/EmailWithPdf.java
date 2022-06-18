package com.utils;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart; 
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Multipart;

public class EmailWithPdf {
	
	private static final Logger logger = Logger.getLogger(EmailWithPdf.class.getName());

    public void sendEmail(String host, String port, String userName, String password,
            String toAddress, String subject, String message, ArrayList<String> attachFiles)
                throws AddressException, MessagingException {
    	
    	logger.info("host:------------"+host);
    	logger.info("port:------------"+port);
    	logger.info("userName:------------"+userName);
    	logger.info("password:------------"+password);
    	logger.info("toAddress:------------"+toAddress);
    	logger.info("subject:------------"+subject);
    	logger.info("message:------------"+message);
    	logger.info("attachFiles:------------"+attachFiles);
    	
        // sets SMTP properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
        
        // creates a new session with an authenticator
        Authenticator auth = new SMTPAuthenticator(userName, password);
        Session session = Session.getInstance(properties, auth);
        
        // creates a new e-mail message
        MimeMessage msg = new MimeMessage(session);
        
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};        
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
        
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);       
        
        // adds attachments
        if (attachFiles != null && attachFiles.size() > 0) {
            for (String filePath : attachFiles) {
                addAttachment(multipart, filePath);
            }
        }
        
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
        
        // sends the e-mail
        Transport.send(msg);
        
    }
    
    private void addAttachment(Multipart multipart, String filePath) throws MessagingException {
        MimeBodyPart attachPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filePath);
        attachPart.setDataHandler(new DataHandler(source));
        attachPart.setFileName(new File(filePath).getName());
 
        multipart.addBodyPart(attachPart);       
    }
    
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        private String userName;
        private String password;
        
        public SMTPAuthenticator(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
        
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }   
}