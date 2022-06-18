package com.communication.engn.servlets;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;


public class ExceptionDataDumpOverEmail extends DispatchAction

{

	private static final Logger logger = Logger.getLogger(ExceptionDataDumpOverEmail.class.getName());


public ActionForward mailAuthenticationFunc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception

{
  sendCSVMail( );
  System.out.println("Exception Data Dump Sucessfully  mail sent********");
  return null;
}

public void sendCSVMail() throws MessagingException, AuthenticationFailedException
{
	
  
    SmsVO vo = new SmsVO();
    // Code Start for Maintain Data Of EMAIL Event 
    vo.setEventName("DAILY_EXCEPTION_REPORT");
    String bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY LIKE 'BUSINESS_DATE'"));
	vo.setbDate(bdate);
	vo.setStage("ONCLICK");
	//vo.setMessage(outBuffer.toString());
	StringBuilder bufInsSql =new StringBuilder();
	bufInsSql.append("SELECT GROUP_CONCAT(ifnull(VALUE,'')) FROM GENERIC_MASTER WHERE GENERIC_KEY = 'EXCEPTION_EMAIL_ID' AND REC_STATUS = 'A'");
	String strMails= ConnectionDAO.singleReturn(bufInsSql.toString());
	long timestamp = Calendar.getInstance().getTimeInMillis();
	String strPath= CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CSV_EMAIl_PATH' "));
	String strFilename=vo.getEventName()+"_"+bdate+"_"+timestamp+".csv";
	strPath=strPath+File.separator+vo.getEventName()+"_"+bdate+"_"+timestamp+".csv";
	logger.info("File Location:"+strPath);
	String qury2="select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' ";
		String recemail=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury2.toString()));
		if(recemail.equalsIgnoreCase("A")){
			vo.setTemplate("E");
			vo.setEmailId(strMails);
			boolean status= new SmsDAOImpl().InsertData(vo);
			// Code Start for Maintain Data Of EMAIL Event 
			String ToEmailId = ConnectionDAO.singleReturn("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = 'EXCEPTION_EMAIL_ID' AND REC_STATUS = 'A' AND PARENT_VALUE = 'TO'");
			String CCEmailId = ConnectionDAO.singleReturn("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = 'EXCEPTION_EMAIL_ID' AND REC_STATUS = 'A' AND PARENT_VALUE = 'CC'");
			Connection con = ConnectionUploadDAO.getConnection();
			ResultSet rs=getDataForCSV(con);
			if (rs!=null)
			{
				try {
					
					rsToCSV(rs,strPath,true,con);
					
					} 
				catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			boolean SendEmailStatus= SendEmail(vo,ToEmailId,CCEmailId,strPath,strFilename);
			logger.info("Send Email -->"+SendEmailStatus);
  			if(SendEmailStatus){
  				StringBuffer updatLoan = new StringBuffer();
  				ArrayList queryList=new ArrayList();
            	updatLoan.append("UPDATE COMM_EVENT_DATA SET RECORD_STATUS = 'A' WHERE EVENT_NAME = 'DAILY_EXCEPTION_REPORT'");
            	PrepStmtObject prepStmt = new PrepStmtObject();
            	prepStmt.setSql(updatLoan.toString());	
            	queryList.add(prepStmt);		           
            	try {
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            	logger.info("Update Event Flag : "+status);
  			}
  			else
	  		{
	  			logger.info("Email is not active on event: '"+vo.getEventName()+"'");
	  		}
		}
    
}
private boolean SendEmail(SmsVO vo, String toEmailId, String cCEmailId, String strPath,String fileName) {
	boolean flag =true;
	EmailVO emailVO = new EmailVO();
		
		String qury="select EMAIL_SUBJECT from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' and REC_STATUS='A' ";
		String subject=ConnectionDAO.singleReturn(qury.toString());
		String message=CommonFunction.checkNull(ConnectionDAO.singleReturn("select MESSAGE from comm_sms_data where event_name='DAILY_EXCEPTION_REPORT' limit 1"));
		vo.setSubject(subject);
		EmailDAO dao=(EmailDAO)DaoImplInstanceFactory.getDaoImplInstance(EmailDAO.IDENTITY);
		emailVO=dao.getSmtpParameter();
		String finalData;
		try
		{
				Properties props = new Properties();
			    props.setProperty("mail.host", emailVO.getSmtpHost());
			    props.setProperty("mail.smtp.port", emailVO.getPort()); 
			    Session session=null;
			    String qry="select parameter_value from parameter_mst where parameter_key='SMTP_AUTH' ";
				String result=ConnectionDAO.singleReturn(qry);
				if(CommonFunction.checkNull(result).equalsIgnoreCase("true"))
			    {
			    props.setProperty("mail.smtp.auth", "true");
			    }
			    props.setProperty("mail.smtp.starttls.enable", "true"); 
			    Authenticator auth = new SMTPAuthenticator(emailVO.getSmtpMailAddress(),emailVO.getSmtpMailPassword());
			    if(CommonFunction.checkNull(result).equalsIgnoreCase("true"))
			    {
			     session = Session.getInstance(props, auth);
			    }
			    else{
			    	 session = Session.getInstance(props);
			    }
			    session.setDebug(true);
			    MimeMessage msg = new MimeMessage(session);
			    //msg.setContent(message, "text/Html");
			    msg.setSubject(subject);    
			    InternetAddress addressFrom = new InternetAddress(emailVO.getSmtpMailAddress());
			    msg.setFrom(addressFrom);   

			    String addressTo="Omnifinsupport@indostarcapital.com";
			    if(toEmailId!=null && !toEmailId.equals(""))
			    addressTo=toEmailId;
			    
			    System.out.println("addressTO-->"+addressTo);
			    
			    String addressCC="Omnifinsupport@indostarcapital.com";
			    if(cCEmailId!=null && !cCEmailId.equals(""))
			    addressCC=cCEmailId;
			    System.out.println("addressCC-->"+addressCC);
			    
			   // msg.addRecipients(Message.RecipientType.TO, addressTo);
			   // msg.addRecipients(Message.RecipientType.CC, addressCC);
			    msg.addRecipients(Message.RecipientType.TO,InternetAddress.parse(addressTo));
			    msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(addressCC));
			    //msg.setSubject("Testing Subject");
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
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
	return flag;
}

public void rsToCSV(ResultSet rs, String url, boolean columnNames,Connection con) throws SQLException, FileNotFoundException, IOException
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


private ResultSet getDataForCSV(Connection con){
	
	ResultSet result=null;
	String strQuery="";
	try
	{
		String query=CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT CONTENT_QUERY FROM COMM_EVENT_LIST_M WHERE EVENT_NAME = 'DAILY_EXCEPTION_REPORT' "));
		if(null!=query && !"".equalsIgnoreCase(query))
		{
			strQuery=query+"";
		}
		else
		{	
			
		strQuery =	"select Loan_Status as 'Loan Status',CUSTOMER_NAME as 'CUSTOMER_NAME',LOAN_PRODUCT_CATEGORY as 'LOAN PRODUCT CATEGORY',Dealer_Name as 'Dealer Name', "+
		" Branch as 'Branch',replace(reportReplaceSpChar(contact_no),',',' & ') as 'contact_no',replace(reportReplaceSpChar(Customer_Address),',',' ') as 'Customer_Address', "+
		" NPA_FLAG as 'NPA FLAG',LOAN_SCHEME as 'LOAN SCHEME',LOAN_ID as 'LOAN ID',replace(LOAN_REFERENCE_NO,',',' ') AS 'LOAN_REFERENCE_NO',Installment_ADVICE_AMOUNT AS 'INSTALLMENT ADVICE AMOUNT', "+
		" Installment_ADVICE_AMOUNT_Adjusted AS 'INSTALLMENT ADVICE AMOUNT ADJUSTED',Installment_ADVICE_AMOUNT_Balance AS 'INSTALLMENT ADVICE AMOUNT BALANCE', "+
		" Principal_ADVICE_AMOUNT as 'Principal_ADVICE_AMOUNT',Principal_ADVICE_AMOUNT_Adjusted as 'Principal_ADVICE_AMOUNT_Adjusted', "+
		" Principal_ADVICE_AMOUNT_Balance as 'Principal_ADVICE_AMOUNT_Balance',Interest_ADVICE_AMOUNT as 'Interest_ADVICE_AMOUNT', "+
		" Interest_ADVICE_AMOUNT_Adjusted as 'Interest_ADVICE_AMOUNT_Adjusted',Interest_ADVICE_AMOUNT_Balance as 'Interest_ADVICE_AMOUNT_Balance', "+
		" Prin_Closure_ADVICE_AMOUNT as 'Prin_Closure_ADVICE_AMOUNT',Prin_Closure_ADVICE_AMOUNT_Adjusted as 'Prin_Closure_ADVICE_AMOUNT_Adjusted', "+
		" Prin_Closure_ADVICE_AMOUNT_Balance as 'Prin_Closure_ADVICE_AMOUNT_Balance',PEMI_ADVICE_AMOUNT as 'PEMI_ADVICE_AMOUNT', "+
		" PEMI_ADVICE_AMOUNT_Adjusted as 'PEMI_ADVICE_AMOUNT_Adjusted',PEMI_ADVICE_AMOUNT_Balance as 'PEMI_ADVICE_AMOUNT_Balance', "+
		" Processing_Fee_ADVICE_AMOUNT as 'Processing_Fee_ADVICE_AMOUNT',Processing_Fee_ADVICE_AMOUNT_Adjusted as 'Processing_Fee_ADVICE_AMOUNT_Adjusted', "+
		" Processing_Fee_ADVICE_AMOUNT_Balance as 'Processing_Fee_ADVICE_AMOUNT_Balance',Excess_ADVICE_AMOUNT as 'Excess_ADVICE_AMOUNT', "+
		" Excess_ADVICE_AMOUNT_Adjusted as 'Excess_ADVICE_AMOUNT_Adjusted',Excess_ADVICE_AMOUNT_Balance as 'Excess_ADVICE_AMOUNT_Balance', "+
		" Other_ADVICE_AMOUNT as 'Other_ADVICE_AMOUNT',Other_ADVICE_AMOUNT_Adjusted as 'Other_ADVICE_AMOUNT_Adjusted', "+
		" Other_Advice_Balance as 'Other_Advice_Balance',LMS_TOTAL_BALANCE_FOR_ALL 'LMS_TOTAL_BALANCE_FOR_ALL', "+
		" SL_NO as 'SL_NO',GLBalance as 'GLBalance',Difference as 'Difference',Creditors_Balance as 'Creditors_Balance', "+
		" DISBURSAL_AMOUNT as 'DISBURSAL_AMOUNT',DISBURSAL_STATUS as 'DISBURSAL_STATUS',Total_Principal as 'Total_Principal', "+
		" Total_Principal_Billed as 'Total_Principal_Billed',Disbursal_Advice as 'Disbursal_Advice',Disbursal_Adjusted_Amount as 'Disbursal_Adjusted_Amount', "+
		" if(temp_latest_overduedump.loan_status='A', if(LOAN_REPAYMENT_TYPE='I',LMS_POS,(select sum(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT) from cr_txnadvice_dtl where loan_id= temp_latest_overduedump.loan_id and charge_code_id =8)),0) as 'LMS_POS', "+
		" Undisbursed as 'Undisbursed',GL_POS_Total as 'GL_POS_Total',Gl_Balance_Principal as 'Gl_Balance_Principal',UMFC as 'UMFC', "+
		" Total_Interest_Billed as 'Total_Interest_Billed',Balance_Int_LMS as 'Balance_Int_LMS',Gl_Balance_Int as 'Gl_Balance_Int', "+
		" Overdue_Principal as 'Overdue_Principal',Overdue_Int as 'Overdue_Int', "+
		" if(temp_latest_overduedump.loan_status='A', if(LOAN_REPAYMENT_TYPE='I',LMS_POS,(select sum(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT) from cr_txnadvice_dtl where loan_id= temp_latest_overduedump.loan_id and charge_code_id =8)),0)-ifnull(Gl_Balance_Principal,0) as 'POS DIFFERENCE' "+
		" from temp_latest_overduedump  "+
		" where (DIFFERENCE <> 0 or if(temp_latest_overduedump.loan_status='A', if(LOAN_REPAYMENT_TYPE='I',LMS_POS,(select sum(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT) from cr_txnadvice_dtl where loan_id= temp_latest_overduedump.loan_id and charge_code_id =8)),0)-ifnull(Gl_Balance_Principal,0) <>0) ";
	
		}
           
            
		
		PreparedStatement ps = con.prepareStatement(strQuery);
		
		result = ps.executeQuery();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
		
	}

	return result;
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

}
