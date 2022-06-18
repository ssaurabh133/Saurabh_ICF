package com.communication.engn.servlets;

import com.communication.engn.dao.EmailDAO;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.communication.engn.vo.EmailVO;
import com.communication.engn.vo.SmsVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionUploadDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class VoucherUploadSummaryEmail extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(VoucherUploadSummaryEmail.class.getName());

  public ActionForward mailAuthenticationFunc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    sendCSVMail();
    System.out.println("Sucessfully  mail sent********");
    return null;
  }

  public void sendCSVMail()
    throws MessagingException, AuthenticationFailedException
  {
    SmsVO vo = new SmsVO();

    vo.setEventName("VOUCHER_UPL_SUMM_MAIL");
    String bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY LIKE 'BUSINESS_DATE'"));
    vo.setbDate(bdate);
    vo.setStage("ONCLICK");

    StringBuilder bufInsSql = new StringBuilder();
    bufInsSql.append("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = 'VOUCHER_UPL_EMAIL' AND REC_STATUS = 'A'");
    String strMails = ConnectionDAO.singleReturn(bufInsSql.toString());
    long timestamp = Calendar.getInstance().getTimeInMillis();
    String strPath = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CSV_EMAIl_PATH' "));
    String strFilename = vo.getEventName() + "_" + bdate + "_" + timestamp + ".csv";
    strPath = strPath + File.separator + vo.getEventName() + "_" + bdate + "_" + timestamp + ".csv";
    logger.info("File Location:" + strPath);
    String qury2 = "select REC_STATUS  from comm_event_list_m where EVENT_NAME='" + vo.getEventName() + "' and TEMPLATE_TYPE='E' ";
    String recemail = CommonFunction.checkNull(ConnectionDAO.singleReturn(qury2.toString()));
    if (recemail.equalsIgnoreCase("A")) {
      vo.setTemplate("E");
      vo.setEmailId(strMails);
      boolean status = new SmsDAOImpl().InsertData(vo);

      String ToEmailId = ConnectionDAO.singleReturn("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = 'VOUCHER_UPL_EMAIL' AND REC_STATUS = 'A' AND PARENT_VALUE = 'TO'");
      String CCEmailId = ConnectionDAO.singleReturn("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = 'VOUCHER_UPL_EMAIL' AND REC_STATUS = 'A' AND PARENT_VALUE = 'CC'");
      Connection con = ConnectionUploadDAO.getConnection();
      ResultSet rs = getDataForCSV(con);
      if (rs != null)
      {
        try
        {
          rsToCSV(rs, strPath, true, con);
        }
        catch (FileNotFoundException e)
        {
          e.printStackTrace();
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
      boolean SendEmailStatus = SendEmail(vo, ToEmailId, CCEmailId, strPath, strFilename);
      logger.info("Send Email -->" + SendEmailStatus);
      if (SendEmailStatus) {
        StringBuffer updatLoan = new StringBuffer();
        ArrayList queryList = new ArrayList();
        updatLoan.append("UPDATE COMM_EVENT_DATA SET RECORD_STATUS = 'A' WHERE EVENT_NAME = 'VOUCHER_UPL_SUMM_MAIL'");
        PrepStmtObject prepStmt = new PrepStmtObject();
        prepStmt.setSql(updatLoan.toString());
        queryList.add(prepStmt);
        try {
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
        }
        catch (RemoteException e) {
          e.printStackTrace();
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
        logger.info("Update Event Flag : " + status);
      }
      else
      {
        logger.info("Email is not active on event: '" + vo.getEventName() + "'");
      }
    }
  }

  private boolean SendEmail(SmsVO vo, String toEmailId, String cCEmailId, String strPath, String fileName) {
    boolean flag = true;
    EmailVO emailVO = new EmailVO();

    String qury = "select EMAIL_SUBJECT from comm_event_list_m where EVENT_NAME='" + vo.getEventName() + "' and TEMPLATE_TYPE='E' and REC_STATUS='A' ";
    String subject = ConnectionDAO.singleReturn(qury.toString());
    String message = CommonFunction.checkNull(ConnectionDAO.singleReturn("select MESSAGE from comm_sms_data where event_name='VOUCHER_UPL_SUMM_MAIL' limit 1"));
    vo.setSubject(subject);
    EmailDAO dao = (EmailDAO)DaoImplInstanceFactory.getDaoImplInstance("COMMEMAIL");
    emailVO = dao.getSmtpParameter();
    try
    {
      Properties props = new Properties();
      props.setProperty("mail.host", emailVO.getSmtpHost());
      props.setProperty("mail.smtp.port", emailVO.getPort());
      Session session = null;
      String qry = "select parameter_value from parameter_mst where parameter_key='SMTP_AUTH' ";
      String result = ConnectionDAO.singleReturn(qry);
      if(CommonFunction.checkNull(result).equalsIgnoreCase("true"))
      {
      props.setProperty("mail.smtp.auth", "true");
      }
      props.setProperty("mail.smtp.starttls.enable", "true"); 
      Authenticator auth = new SMTPAuthenticator(emailVO.getSmtpMailAddress(), emailVO.getSmtpMailPassword());
      if (CommonFunction.checkNull(result).equalsIgnoreCase("true"))
      {
        session = Session.getInstance(props, auth);
      }
      else {
        session = Session.getInstance(props);
      }
      session.setDebug(true);
      MimeMessage msg = new MimeMessage(session);

      msg.setSubject(subject);
      InternetAddress addressFrom = new InternetAddress(emailVO.getSmtpMailAddress());
      msg.setFrom(addressFrom);

      String addressTo = "Omnifinsupport@indostarcapital.com";
      if ((toEmailId != null) && (!toEmailId.equals(""))) {
        addressTo = toEmailId;
      }
      System.out.println("addressTO-->" + addressTo);

      String addressCC = "Omnifinsupport@indostarcapital.com";
      if ((cCEmailId != null) && (!cCEmailId.equals("")))
        addressCC = cCEmailId;
      System.out.println("addressCC-->" + addressCC);

   //   msg.addRecipients(Message.RecipientType.TO, addressTo);
     // msg.addRecipients(Message.RecipientType.CC, addressCC);

      msg.addRecipients(Message.RecipientType.TO,InternetAddress.parse(addressTo));
      msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(addressCC));
      
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(message);
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);
      messageBodyPart = new MimeBodyPart();

      DataSource source = new FileDataSource(strPath);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(fileName);
      multipart.addBodyPart(messageBodyPart);
      msg.setContent(multipart);

      Transport.send(msg);
      System.out.println("This is regarding sending mail check");

      System.out.println("This is regarding sending mail check Part Twoooo");
    }
    catch (Exception e)
    {
      flag = false;
      e.printStackTrace();
    }
    return flag;
  }

  public void rsToCSV(ResultSet rs, String url, boolean columnNames, Connection con) throws SQLException, FileNotFoundException, IOException
  {
	    File fl = new File(url);
		
		//get metadata from ResultSet
	    ResultSetMetaData metaData = rs.getMetaData();
	    
	    //get number of columns from metadata
	    int columnCount = metaData.getColumnCount();
	    
	    //write 3 bytes to output stream to encode csv file in UTF8
	    OutputStream os = new FileOutputStream(fl);
	    try 
	    {
	       // os.write(239);
	      //  os.write(187);
	       // os.write(191);
	        
	        
	        //initialize PrintWriter class
	        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"UTF-8"));
	        
	        try
	        {
	            //if columnNames is true column names will be written in csv file
	            //if columnNames is false column names will be skipped
	            if(columnNames)
	            {
	                //this will loop until number of columns is reached
	                for(int i = 1; i<=columnCount; i++)
	                {
	                    //this writes column name to csv file
	                    pw.print(metaData.getColumnName(i));
	                    
	                    //if i is less then column count
	                    //comma will be written to separate column names
	                    if(i<columnCount)
	                    {
	                        pw.print(",");
	                        pw.flush();
	                    }
	                    
	                    //if column count is reached new line will be written
	                    if(i==columnCount)
	                    {
	                        pw.println();
	                        pw.flush();
	                    }
	                }
	            }
	            
	            //while result set holds rows
	            while (rs.next())
	            {
	                //this will loop until column count is reached
	                for (int i = 1; i <=columnCount; i++)
	                {
	                    //data from column of index i is written to csv
	                    pw.print(rs.getObject(i));
	                    //if columns index is less then column count
	                    //comma will be written to separate data
	                    if(i<columnCount)
	                    {
	                        pw.print(",");
	                        pw.flush();
	                    }
	                    //if column count is reached
	                    //new line will be written
	                    if(i==columnCount)
	                    {
	                        pw.println();
	                        pw.flush();
	                    }
	                }
	            }
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
	        
	        pw.flush();
	        pw.close();
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	    finally
	    {
	    	con.close();
	    }
	    
	    os.flush();
	    os.close();
}

  private ResultSet getDataForCSV(Connection con)
  {
    ResultSet result = null;
    String strQuery = "";
    try
    {
      String query = CommonFunction.checkNull(ConnectionDAO.singleReturn("select content_query from comm_event_list_m where event_name='VOUCHER_UPL_SUMM_MAIL' limit 1"));
      if ((query != null) && (!"".equalsIgnoreCase(query)))
      {
        strQuery = query;
      }
      else
      {
        strQuery = "SELECT ifnull(BATCH_ID,'')BATCH_ID, ifnull(UPLOAD_TYPE,'')UPLOAD_TYPE, ifnull(UPLOAD_DATE,'')UPLOAD_DATE, ifnull(NO_OF_RECORDS,0) AS 'AVAILED COUNT',  count(distinct voucher_no)AS UPLOADED_COUNT,  (ifnull(NO_OF_RECORDS,0)- count(distinct voucher_no)) AS 'VOUCHER COUNT DIFFERENCE',ifnull(SUM(ifnull(DR_AMOUNT,0)),0)DR_AMOUNT,   ifnull(SUM(ifnull(CR_AMOUNT,0)),0)CR_AMOUNT,ifnull(SUM(ifnull(DR_AMOUNT,0))-SUM(ifnull(CR_AMOUNT,0)),0) AS 'TOTAL DR-CR DIFFERENCE',   ifnull(A.MAKER_ID,'')MAKER_ID, ifnull(A.MAKER_DATE,'')MAKER_DATE, ifnull(A.AUTHOR_ID,'')AUTHOR_ID,   ifnull(A.AUTHOR_DATE,'')AUTHOR_DATE, ifnull(FILE_NAME,'')FILE_NAME,    CASE WHEN  STATUS = 'A' THEN 'APPROVED' WHEN  STATUS = 'EK' THEN 'ERROR IN DATA COPY'  WHEN  STATUS = 'F' THEN 'FORWARDED'  WHEN  STATUS = 'EP' THEN 'ERROR IN VALIDATION' WHEN  STATUS = 'P' THEN 'PENDDING'  WHEN  STATUS = 'W' THEN 'AUTHOR IN PROCESS' WHEN  STATUS = 'W' THEN 'AUTHOR IN PROCESS'  WHEN  STATUS = 'X' THEN 'REJECTED' ELSE ''   END AS 'UPLOAD STATUS' FROM ALL_UPLOAD_SUMMARY A Join gl_voucher_dtl V on A.Batch_Id = V.voucher_upload_id  WHERE  FTP_VOUCHER_BATCH = 'Y' AND A.STATUS <> 'X' AND UPLOAD_TYPE = 'Voucher Upload'    AND UPLOAD_DATE = (select date_sub(parameter_value, INTERVAL 1 DAY) from parameter_mst where parameter_key='BUSINESS_DATE') group by  BATCH_ID ";
      }

      PreparedStatement ps = con.prepareStatement(strQuery);

      result = ps.executeQuery();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    return result;
  }
  class SMTPAuthenticator extends Authenticator {
    private PasswordAuthentication authentication;

    public SMTPAuthenticator(String login, String password) {
      this.authentication = new PasswordAuthentication(login, password);
    }
    protected PasswordAuthentication getPasswordAuthentication() {
      return this.authentication;
    }
  }
}