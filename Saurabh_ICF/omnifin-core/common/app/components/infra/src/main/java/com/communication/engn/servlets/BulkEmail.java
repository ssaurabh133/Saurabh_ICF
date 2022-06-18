package com.communication.engn.servlets;

import com.communication.engn.dao.EmailDAO;
import com.communication.engn.daoImplMySql.EmailDAOImpl;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.communication.engn.servlets.ExceptionDataDumpOverEmail.SMTPAuthenticator;
import com.communication.engn.vo.EmailVO;
import com.communication.engn.vo.SmsVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionUploadDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BulkEmail extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(BulkEmail.class.getName());

  public ActionForward mailAuthenticationFunc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    sendPdfMail(request,response);
    System.out.println("Sucessfully  mail sent********");
    return null;
  }

  @SuppressWarnings("rawtypes")
public void sendPdfMail(HttpServletRequest request, HttpServletResponse response)
    throws MessagingException, AuthenticationFailedException, SQLException
  {
    SmsVO vo = new SmsVO();
    String eventQuery="SELECT COUNT(1) FROM COMM_EVENT_LIST_M WHERE EVENT_NAME='WELCOME_LETTER' AND TEMPLATE_TYPE='E' AND REC_STATUS='A'";
    int count=Integer.parseInt(ConnectionDAO.singleReturn(eventQuery));
    if(count>0){
    	 vo.setEventName("WELCOME_LETTER");
    	    String bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%Y-%m-%d') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'"));
    	    vo.setbDate(bdate);
    	    vo.setStage("ONCLICK");
    	    
    	    
    	    String loanQuery="SELECT LOAN_ID FROM CR_LOAN_DISBURSAL_DTL WHERE REC_STATUS='A' AND DISBURSAL_NO=1 AND DATE_ADD(DISBURSAL_DATE,INTERVAL 1 DAY)='"+bdate+"'";
    	    ArrayList list= ConnectionDAO.sqlSelect(loanQuery);
			  for(int i=0;i<list.size();i++){
		    	    
		  	    	ArrayList bdetails1=(ArrayList)list.get(i);
		  	    	if(bdetails1.size()>0)
		  			{
		  	    	String loanId=(CommonFunction.checkNull(bdetails1.get(0))).trim();	
		  	    	vo.setLoanId(loanId);
		  	    	StringBuilder bufInsSql = new StringBuilder();
		    	    bufInsSql.append("select distinct a.Loan_NO,b.customer_name,CUSTOMER_EMAIL,d.primary_phone,round(a.LOAN_LOAN_AMOUNT),br.branch_desc ");
						bufInsSql.append("from cr_loan_dtl a ");
						bufInsSql.append("join cr_loan_disbursal_dtl disb on disb.loan_id=a.loan_id ");
						bufInsSql.append("left join gcd_customer_m b on b.customer_id=a.loan_customer_id  ");
						bufInsSql.append("left join cr_deal_dtl c on a.loan_deal_id= c.deal_id ");
						bufInsSql.append("left join com_address_m d on d.bpid= b.customer_id and d.COMMUNICATION_ADDRESS='Y' ");
						bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
						bufInsSql.append("where a.loan_id='"+loanId+"' and IFNULL(CUSTOMER_EMAIL,'')<>'' and a.loan_id not in (SELECT LOAN_ID FROM COMM_EVENT_DATA WHERE EVENT_NAME='WELCOME_LETTER' AND TEMPLATE_TYPE='E' AND RECORD_STATUS='A')" );
						ArrayList list1= ConnectionDAO.sqlSelect(bufInsSql.toString());
						  for(int j=0;j<list1.size();j++){
					    	    
					  	    	ArrayList bdetails=(ArrayList)list1.get(j);
					  	    	if(bdetails.size()>0)
					  			{
						  	    		vo.setApplicationNo((CommonFunction.checkNull(bdetails.get(0))).trim());
						  	    		vo.setCustomerName((CommonFunction.checkNull(bdetails.get(1))).trim());
						  	    		vo.setEmailId((CommonFunction.checkNull(bdetails.get(2))).trim());
						  	    		vo.setMobileNo((CommonFunction.checkNull(bdetails.get(3))).trim());
						  	    		vo.setLoanAmount((CommonFunction.checkNull(bdetails.get(4))).trim());
						  	    	   bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'"));
						  	    	   vo.setbDate(bdate);
						      	  
						  	    		vo.setBranch((CommonFunction.checkNull(bdetails.get(5))).trim());
						  	    		
						  	    		String query2="select MESSAGE from comm_sms_data where EVENT_NAME='"+vo.getEventName()+"' and REC_STATUS = 'A' ";
						  				String mail_text= CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
						  				String temp1=mail_text.replaceAll("<<DEAL_NO>>", vo.getApplicationNo());
						  	    		String temp2=temp1.replaceAll("<<LOAN_NO>>", vo.getApplicationNo());
						  	    		String temp3=temp2.replaceAll("<<CUSTOMER_NAME>>", vo.getCustomerName());
						  	    		String temp4=temp3.replaceAll("<<DATE>>", vo.getbDate());
						  	    		String temp5=temp4.replaceAll("<<AMT>>", vo.getLoanAmount());
						  	    		String temp6=temp5.replaceAll("<<BRANCH>>", vo.getBranch());
						  	    		String temp7=temp6.replaceAll("<<PRODUCT>>", vo.getProduct());
						  	    		String temp8=temp7.replaceAll("<<USER_NAME>>", vo.getUserName());
						  	    		String mesg=temp8.replaceAll("<<EMI_DATE>>", vo.getbDate());
						  	    		
						  	    		vo.setMessage(mesg);
						  	    		vo.setTemplate("E");
						  	    		boolean status = new SmsDAOImpl().InsertData(vo);
						  	    		String ToEmailId=vo.getEmailId(); 
						  	    		 String strPath = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='PDF_EMAIL_PATH' "));
						  	    		
						  	    		 
						  	    		 String p_company_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/logo.bmp").toString();
						  	    	    String p_barcode_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/barcode.gif").toString();
						  	    	    String p_indian_rupee_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/rupee.bmp").toString();
						  	    	    String p_imageBox = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/imageBox.bmp").toString();
						  	    	    String p_imageCheckbox = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/imageCheckbox.bmp").toString();
						  	    	    String sub_reports_location = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports\\").toString();
						  	    	    String SUBREPORT_DIR = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports\\").toString();
						  	    	    String reportPath = "/reports/MYSQLREPORTS/";
						  	    	    String reportName="welcome_letter_report";
						  	    	  InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(new StringBuilder().append(reportPath).append(reportName).append(".jasper").toString());
						  	          boolean Generationstatus = ReportGenerator(p_company_logo, p_barcode_logo, p_indian_rupee_logo, p_imageBox, p_imageCheckbox, reportPath, reportName, reportStream, sub_reports_location, SUBREPORT_DIR,strPath, response,loanId);

						  	    		String strFilename=reportName+".pdf";
						  	    		boolean SendEmailStatus=true;
						  	    		if(Generationstatus){
						  	    		SendEmailStatus = SendEmail(vo, ToEmailId, "", strPath, strFilename);
						  	    		}
						  	    		logger.info("Send Email -->" + SendEmailStatus);
						  	    	      if (SendEmailStatus) {
						  	    	        StringBuffer updatLoan = new StringBuffer();
						  	    	        ArrayList queryList = new ArrayList();
						  	    	        updatLoan.append("UPDATE COMM_EVENT_DATA SET RECORD_STATUS = 'A' WHERE EVENT_NAME = '"+vo.getEventName()+"'");
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
		  			}
			  }    					  	    			
    }
    String currentDateQuery="SELECT date_format(PARAMETER_VALUE,'%d-%m') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'";
    String currentDate=ConnectionDAO.singleReturn(currentDateQuery);
    
    String sendDateQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='INT_SEND_DATE'";
    String sendDate=ConnectionDAO.singleReturn(sendDateQuery);
    
    String sendDateQuery1="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='INT_SEND_DATE_1'";
    String sendDate1=ConnectionDAO.singleReturn(sendDateQuery1);
    if(CommonFunction.checkNull(currentDate).equalsIgnoreCase(sendDate) || CommonFunction.checkNull(currentDate).equalsIgnoreCase(sendDate1)){
     eventQuery="SELECT COUNT(1) FROM COMM_EVENT_LIST_M WHERE EVENT_NAME='INTEREST_CERTIFICATE' AND TEMPLATE_TYPE='E' AND REC_STATUS='A'";
     count=Integer.parseInt(ConnectionDAO.singleReturn(eventQuery));
    if(count>0){
    	 vo.setEventName("INTEREST_CERTIFICATE");
    	    String bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%Y-%m-%d') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'"));
    	    vo.setbDate(bdate);
    	    vo.setStage("ONCLICK");
    	    String startDate=ConnectionDAO.singleReturn("select if(date_format(parameter_value,'%m')<=4,concat(date_format(parameter_value,'%Y')-1,'-04-01'),concat(date_format(parameter_value,'%Y'),'-04-01'))from parameter_mst where parameter_key='BUSINESS_DATE'");
    	    String endDate=ConnectionDAO.singleReturn("select if(date_format(parameter_value,'%m')<=4,concat(date_format(parameter_value,'%Y'),'-03-31'),concat(date_format(parameter_value,'%Y')+1,'-03-31')) from parameter_mst where parameter_key='BUSINESS_DATE'");
    	    
    	    
    	    String loanQuery="SELECT DISTINCT a.LOAN_ID FROM CR_LOAN_DTL a join cr_repaysch_dtl b on a.loan_id=b.loan_id and instl_date>='"+startDate+"' and instl_date<='"+endDate+"'  WHERE 'ALL'='ALL'  and a.rec_status in ('A','C')";
    	    ArrayList list= ConnectionDAO.sqlSelect(loanQuery);
			  for(int i=0;i<list.size();i++){
		    	    
		  	    	ArrayList bdetails1=(ArrayList)list.get(i);
		  	    	if(bdetails1.size()>0)
		  			{
		  	    	String loanId=(CommonFunction.checkNull(bdetails1.get(0))).trim();	
		  	    	vo.setLoanId(loanId);
		  	    	StringBuilder bufInsSql = new StringBuilder();
		    	    bufInsSql.append("select distinct a.Loan_NO,b.customer_name,CUSTOMER_EMAIL,d.primary_phone,round(a.LOAN_LOAN_AMOUNT),br.branch_desc ");
						bufInsSql.append("from cr_loan_dtl a ");
						bufInsSql.append("left join cr_loan_disbursal_dtl disb on disb.loan_id=a.loan_id ");
						bufInsSql.append("left join gcd_customer_m b on b.customer_id=a.loan_customer_id  ");
						bufInsSql.append("left join cr_deal_dtl c on a.loan_deal_id= c.deal_id ");
						bufInsSql.append("left join com_address_m d on d.bpid= b.customer_id and d.COMMUNICATION_ADDRESS='Y' ");
						bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
						bufInsSql.append("where a.loan_id='"+loanId+"' and IFNULL(CUSTOMER_EMAIL,'')<>'' and a.loan_id not in (SELECT LOAN_ID FROM COMM_EVENT_DATA WHERE EVENT_NAME='"+vo.getEventName()+"' AND TEMPLATE_TYPE='E' AND RECORD_STATUS='A' and DATE_FORMAT(PROCESS_DATE,'%Y-%m-%d')='"+vo.getbDate()+"')" );
						ArrayList list1= ConnectionDAO.sqlSelect(bufInsSql.toString());
						  for(int j=0;j<list1.size();j++){
					    	    
					  	    	ArrayList bdetails=(ArrayList)list1.get(j);
					  	    	if(bdetails.size()>0)
					  			{
						  	    		vo.setApplicationNo((CommonFunction.checkNull(bdetails.get(0))).trim());
						  	    		vo.setCustomerName((CommonFunction.checkNull(bdetails.get(1))).trim());
						  	    		vo.setEmailId((CommonFunction.checkNull(bdetails.get(2))).trim());
						  	    		vo.setMobileNo((CommonFunction.checkNull(bdetails.get(3))).trim());
						  	    		vo.setLoanAmount((CommonFunction.checkNull(bdetails.get(4))).trim());
						  	    	   bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'"));
						  	    	   vo.setbDate(bdate);
						      	  
						  	    		vo.setBranch((CommonFunction.checkNull(bdetails.get(5))).trim());
						  	    		
						  	    		String query2="select MESSAGE from comm_sms_data where EVENT_NAME='"+vo.getEventName()+"' and REC_STATUS = 'A' ";
						  				String mail_text= CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
						  				String temp1=mail_text.replaceAll("<<DEAL_NO>>", vo.getApplicationNo());
						  	    		String temp2=temp1.replaceAll("<<LOAN_NO>>", vo.getApplicationNo());
						  	    		String temp3=temp2.replaceAll("<<CUSTOMER_NAME>>", vo.getCustomerName());
						  	    		String temp4=temp3.replaceAll("<<DATE>>", vo.getbDate());
						  	    		String temp5=temp4.replaceAll("<<AMT>>", vo.getLoanAmount());
						  	    		String temp6=temp5.replaceAll("<<BRANCH>>", vo.getBranch());
						  	    		String temp7=temp6.replaceAll("<<PRODUCT>>", vo.getProduct());
						  	    		String temp8=temp7.replaceAll("<<USER_NAME>>", vo.getUserName());
						  	    		String mesg=temp8.replaceAll("<<EMI_DATE>>", vo.getbDate());
						  	    		
						  	    		vo.setMessage(mesg);
						  	    		vo.setTemplate("E");
						  	    		boolean status = new SmsDAOImpl().InsertData(vo);
						  	    		String ToEmailId=vo.getEmailId(); 
						  	    		 String strPath = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='PDF_EMAIL_PATH' "));
						  	    		
						  	    		 
						  	    		 String p_company_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/logo.bmp").toString();
						  	    	    String p_barcode_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/barcode.gif").toString();
						  	    	    String p_indian_rupee_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/rupee.bmp").toString();
						  	    	    String p_imageBox = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/imageBox.bmp").toString();
						  	    	    String p_imageCheckbox = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/imageCheckbox.bmp").toString();
						  	    	    String sub_reports_location = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports\\").toString();
						  	    	    String SUBREPORT_DIR = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports\\").toString();
						  	    	    String reportPath = "/reports/MYSQLREPORTS/";
						  	    	    String reportName="Yearly_Interest_Certificate";
						  	    	  InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(new StringBuilder().append(reportPath).append(reportName).append(".jasper").toString());
						  	          boolean Generationstatus = ReportGenerator(p_company_logo, p_barcode_logo, p_indian_rupee_logo, p_imageBox, p_imageCheckbox, reportPath, reportName, reportStream, sub_reports_location, SUBREPORT_DIR,strPath, response,loanId);

						  	    		String strFilename=reportName+".pdf";
						  	    		boolean SendEmailStatus=true;  
						  	    		if(Generationstatus){
						  	    		 SendEmailStatus = SendEmail(vo, ToEmailId, "", strPath, strFilename);
						  	    		}
						  	    		logger.info("Send Email -->" + SendEmailStatus);
						  	    	      if (SendEmailStatus) {
						  	    	        StringBuffer updatLoan = new StringBuffer();
						  	    	        ArrayList queryList = new ArrayList();
						  	    	        updatLoan.append("UPDATE COMM_EVENT_DATA SET RECORD_STATUS = 'A' WHERE EVENT_NAME = '"+vo.getEventName()+"'");
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
		  			}
			  }    					  	    			
    }
			  	    	   		
  }		  	    
}
  public void sendManualMail(String reportName, Map reportParameterMap,String loanId,String eventName,InputStream reportStream)
		    throws MessagingException, AuthenticationFailedException, SQLException
		  {
		    SmsVO vo = new SmsVO();
		    String eventQuery="SELECT COUNT(1) FROM COMM_EVENT_LIST_M WHERE EVENT_NAME='"+eventName+"' AND TEMPLATE_TYPE='E' AND REC_STATUS='A'";
		    int count=Integer.parseInt(ConnectionDAO.singleReturn(eventQuery));
		    if(count>0){
		    	 vo.setEventName(eventName);
		    	    String bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%Y-%m-%d') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'"));
		    	    vo.setbDate(bdate);
		    	    vo.setStage("ONCLICK");
		    	    	
				  	    	vo.setLoanId(loanId);
				  	    	StringBuilder bufInsSql = new StringBuilder();
				    	    bufInsSql.append("select distinct a.Loan_NO,b.customer_name,CUSTOMER_EMAIL,d.primary_phone,round(a.LOAN_LOAN_AMOUNT),br.branch_desc ");
								bufInsSql.append("from cr_loan_dtl a ");
								bufInsSql.append("left join cr_loan_disbursal_dtl disb on disb.loan_id=a.loan_id ");
								bufInsSql.append("left join gcd_customer_m b on b.customer_id=a.loan_customer_id  ");
								bufInsSql.append("left join cr_deal_dtl c on a.loan_deal_id= c.deal_id ");
								bufInsSql.append("left join com_address_m d on d.bpid= b.customer_id and d.COMMUNICATION_ADDRESS='Y' ");
								bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
								bufInsSql.append("where a.loan_id='"+loanId+"' and IFNULL(CUSTOMER_EMAIL,'')<>'' " );
								if(CommonFunction.checkNull(eventName).equalsIgnoreCase("STATEMENT_OF_ACCOUNT")){
									bufInsSql.append(" and a.rec_status='A' ");
								}
								ArrayList list1= ConnectionDAO.sqlSelect(bufInsSql.toString());
								  for(int j=0;j<list1.size();j++){
							    	    
							  	    	ArrayList bdetails=(ArrayList)list1.get(j);
							  	    	if(bdetails.size()>0)
							  			{
								  	    		vo.setApplicationNo((CommonFunction.checkNull(bdetails.get(0))).trim());
								  	    		vo.setCustomerName((CommonFunction.checkNull(bdetails.get(1))).trim());
								  	    		vo.setEmailId((CommonFunction.checkNull(bdetails.get(2))).trim());
								  	    		vo.setMobileNo((CommonFunction.checkNull(bdetails.get(3))).trim());
								  	    		vo.setLoanAmount((CommonFunction.checkNull(bdetails.get(4))).trim());
								  	    	   bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'"));
								  	    	   vo.setbDate(bdate);
								      	  
								  	    		vo.setBranch((CommonFunction.checkNull(bdetails.get(5))).trim());
								  	    		
								  	    		String query2="select MESSAGE from comm_sms_data where EVENT_NAME='"+vo.getEventName()+"' and REC_STATUS = 'A' ";
								  				String mail_text= CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
								  				String temp1=mail_text.replaceAll("<<DEAL_NO>>", vo.getApplicationNo());
								  	    		String temp2=temp1.replaceAll("<<LOAN_NO>>", vo.getApplicationNo());
								  	    		String temp3=temp2.replaceAll("<<CUSTOMER_NAME>>", vo.getCustomerName());
								  	    		String temp4=temp3.replaceAll("<<DATE>>", vo.getbDate());
								  	    		String temp5=temp4.replaceAll("<<AMT>>", vo.getLoanAmount());
								  	    		String temp6=temp5.replaceAll("<<BRANCH>>", vo.getBranch());
								  	    		String temp7=temp6.replaceAll("<<PRODUCT>>", vo.getProduct());
								  	    		String temp8=temp7.replaceAll("<<USER_NAME>>", vo.getUserName());
								  	    		String mesg=temp8.replaceAll("<<EMI_DATE>>", vo.getbDate());
								  	    		
								  	    		vo.setMessage(mesg);
								  	    		vo.setTemplate("E");
								  	    		boolean status = new SmsDAOImpl().InsertData(vo);
								  	    		String ToEmailId=vo.getEmailId(); 
								  	    		 String strPath = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='PDF_EMAIL_PATH' "));
								  	    		
								  	    	 String bDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d%m%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
								  	    	  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
								  	    	  String passwordQuery="select date_format(g.CUSTOMER_DOB,'%d%m%Y') from cr_loan_dtl l join gcd_customer_m g on g.customer_id=l.loan_customer_id where loan_id='"+loanId+"'";
								  	    		String pass=ConnectionDAO.singleReturn(passwordQuery);
								  	    		logger.info("passwordQuery: "+passwordQuery);
								  	    		logger.info("pass: "+pass);
								  	    		String ownerPasswordQuery="SELECT IFNULL(VALUE,'')  FROM GENERIC_MASTER WHERE GENERIC_KEY='OWNER_PASSWORD' AND REC_STATUS='A' AND PARENT_VALUE='NA' ";
								  	    		String owner=ConnectionDAO.singleReturn(ownerPasswordQuery);
								  	    		if(CommonFunction.checkNull(owner).equalsIgnoreCase("")){
								  	    			owner=pass;
								  	    		}
								  	    	  try
								  	    	  {
								  	    	    Connection connectDatabase = ConnectionDAO.getConnection();
								  	    	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, reportParameterMap, connectDatabase);
								  	    	    JRPdfExporter exporter = new JRPdfExporter();
								  	    	    
								  	    		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
								  	    		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, strPath + "/" +reportName+".pdf");
								  	    		exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
								  	    		exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
								  	    		exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD,pass);
								  	    		exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, owner);
								  	    		exporter.setParameter(
								  	    	    JRPdfExporterParameter.PERMISSIONS, 
								  	    	    new Integer(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING)
								  	    	    );
								  	    	  	exporter.exportReport();
								  	    	  connectDatabase.close();
								  	    	jasperPrint=null;
								  	    	exporter=null;
								  	    	reportStream=null;
								  	    	  }
								  	    	  catch (Exception e) {
								  	    	   
								  	    	    e.printStackTrace();
								  	    	  }
								  	    		String strFilename=reportName+".pdf";
								  	    		boolean SendEmailStatus=true;
								  	    		File homeDir1 = new File(strPath);
								  				boolean isHomeDir1 = homeDir1.isDirectory();
								  				if (isHomeDir1) {
								  				      logger.info("the name you have entered is a directory  : "  +homeDir1);
								  				      logger.info("Path1:" + strPath);
								  					}else{
								  						 logger.info("the name you have entered is not a directory  : "  +homeDir1);
								  						logger.info("Path1:" + strPath);
								  					}
								  	    		File homeDir = new File(strPath+"/"+strFilename);
								  	    		 if (homeDir.exists()){ 
								  	               System.out.println("Exists"+homeDir); 
								  	    		 }else{
								  	               System.out.println("Does not Exists"+homeDir); 
								  	    		 }
								  	    		strPath=strPath+"/";
								  	    		SendEmailStatus = SendEmail(vo, ToEmailId, "", strPath, strFilename);
								  	    		
								  	    		logger.info("Send Email -->" + SendEmailStatus);
								  	    	      if (SendEmailStatus) {
								  	    	        StringBuffer updatLoan = new StringBuffer();
								  	    	        ArrayList queryList = new ArrayList();
								  	    	        updatLoan.append("UPDATE COMM_EVENT_DATA SET RECORD_STATUS = 'A' WHERE EVENT_NAME = '"+vo.getEventName()+"'");
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
				  			
					   					  	    			
		    }
		      
		}
  
private boolean SendEmail(SmsVO vo, String toEmailId, String cCEmailId, String strPath, String fileName) {
	boolean flag =true;
	EmailVO emailVO = new EmailVO();
		
		String qury="select EMAIL_SUBJECT from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' and REC_STATUS='A' ";
		String subject=ConnectionDAO.singleReturn(qury.toString());
		String message=vo.getMessage();
		vo.setSubject(subject);
		EmailDAO dao=(EmailDAO)DaoImplInstanceFactory.getDaoImplInstance(EmailDAO.IDENTITY);
		emailVO=dao.getSmtpParameter();
		String finalData;
		try
		{
				Properties props = new Properties();
			    props.setProperty("mail.host", emailVO.getSmtpHost());
			    props.setProperty("mail.smtp.port", emailVO.getPort()); 
			  //  props.setProperty("mail.smtp.socketFactory.port", emailVO.getPort());    
			    //props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    

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
			   // msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(addressCC));
			    //msg.setSubject("Testing Subject");
			    BodyPart messageBodyPart = new MimeBodyPart();
			    messageBodyPart.setText(message);
			    Multipart multipart = new MimeMultipart();
			    multipart.addBodyPart(messageBodyPart);
			    messageBodyPart = new MimeBodyPart();
			    File att = new File(new File(strPath), fileName);
			    ((MimeBodyPart) messageBodyPart).attachFile(att);
			    /*DataSource source = new FileDataSource(strPath);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);*/
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
public boolean ReportGenerator(String p_company_logo, String p_barcode_logo, String p_indian_rupee_logo, String p_imageBox, String p_imageCheckbox, String reportPath, String reportName, InputStream reportStream, String sub_reports_location, String SUBREPORT_DIR,String filelocation,  HttpServletResponse response,String loanId)
{
  logger.info(new StringBuilder().append("In Report Generator.......").append(reportName).toString());

  String actualReportName = reportName;
  
  sub_reports_location = new StringBuilder().append(sub_reports_location).append("MYSQLREPORTS\\").toString();
  SUBREPORT_DIR = new StringBuilder().append(SUBREPORT_DIR).append("MYSQLREPORTS\\").toString();
  boolean generationStatus = true;
 // Map reportParameterMap = SetReportFilters(reportName, SUBREPORT_DIR, sub_reports_location, p_imageCheckbox, p_imageBox, p_indian_rupee_logo, p_barcode_logo, p_company_logo);
  Map reportParameterMap = new HashMap();
  String p_company_name = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT COMPANY_DESC FROM COM_COMPANY_M limit 1"));
  reportParameterMap.put("sub_reports_location", sub_reports_location);
  reportParameterMap.put("SUBREPORT_DIR", SUBREPORT_DIR);
  reportParameterMap.put("p_company_logo", p_company_logo);
  reportParameterMap.put("p_barcode_logo", p_barcode_logo);
  reportParameterMap.put("p_indian_rupee_logo", p_indian_rupee_logo);
  reportParameterMap.put("p_imageBox", p_imageBox);
  reportParameterMap.put("p_imageCheckbox", p_imageCheckbox);
  reportParameterMap.put("p_company_name", p_company_name);
  reportParameterMap.put("p_printed_deal", "ALL");
  reportParameterMap.put("p_branch", "ALL");
  reportParameterMap.put("p_branch_type", "ALL");
  reportParameterMap.put("p_loan_type", "ALL");
  reportParameterMap.put("p_status", "ALL");
  reportParameterMap.put("status", "ALL");
  reportParameterMap.put("p_deal_from", "ALL");
  reportParameterMap.put("p_deal_to", "ALL");
  reportParameterMap.put("p_branch_id", "ALL");
  reportParameterMap.put("productCategory", "ALL");
  reportParameterMap.put("p_user_id", "A3S1186");
  reportParameterMap.put("loanClass", "ALL");
  reportParameterMap.put("p_p_date_from", "01-01-2015");
  reportParameterMap.put("p_loan_from", "ALL");
  reportParameterMap.put("p_loan_to", "ALL");
  reportParameterMap.put("P_printed_loan", "ALL");
  reportParameterMap.put("p_partnerShipType", "ALL");
  reportParameterMap.put("p_partnerCode", "ALL");
  reportParameterMap.put("p_Loan_Number",loanId);
  reportParameterMap.put("p_branch_type","All");	
  reportParameterMap.put("loan_date","L");
  reportParameterMap.put("productCategory","All");
  reportParameterMap.put("loanClass","All");
  reportParameterMap.put("p_user_id","A3S1186");
  String bDateQuery="Select date_format(Parameter_value,'%d-%m-%Y') from parameter_mst where parameter_key='BUSINESS_DATE' ";
  String busDate=ConnectionDAO.singleReturn(bDateQuery);
  reportParameterMap.put("p_welcome_date",busDate);
  reportParameterMap.put("p_loan_id",loanId);
  
  reportParameterMap.put("p_printed_date",busDate);
  reportParameterMap.put("p_printed_by","A3S1186");
  
  if(CommonFunction.checkNull(reportName).equalsIgnoreCase("Yearly_Interest_Certificate")){
	  String fromInterestDate=ConnectionDAO.singleReturn("select if(date_format(parameter_value,'%m')<=4,concat(date_format(parameter_value,'%Y')-1,'-04-01'),concat(date_format(parameter_value,'%Y'),'-04-01'))from parameter_mst where parameter_key='BUSINESS_DATE'");
	    String toInterestDate=ConnectionDAO.singleReturn("select if(date_format(parameter_value,'%m')<=4,concat(date_format(parameter_value,'%Y'),'-03-31'),concat(date_format(parameter_value,'%Y')+1,'-03-31')) from parameter_mst where parameter_key='BUSINESS_DATE'");
	   
  StringBuilder query=new StringBuilder();
  query.append(" SELECT CLD.LOAN_NO,cld.loan_id,CONCAT(GCD.CUSTOMER_NAME,' ') AS CUSTOMER_NAME, ");
		query.append(" if(ifnull(CAM.ADDRESS_LINE1,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.ADDRESS_LINE1,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',','),','))addressLine1, "); 
		query.append(" if(ifnull(CAM.ADDRESS_LINE2,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.ADDRESS_LINE2,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',',')))addressLine2, ");
		query.append(" if(ifnull(CAM.ADDRESS_LINE3,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.ADDRESS_LINE3,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',','),','))addressLine3, ");
		query.append(" if(ifnull(CAM.landmark,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.landmark,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',','),','))landMark, ");
		query.append(" if(ifnull(CSM1.STATE_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CSM1.STATE_DESC,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',','),','))state, ");
		query.append(" if(ifnull(CDM1.DISTRICT_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CDM1.DISTRICT_DESC,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',',')))district, ");
		query.append(" if(ifnull(CAM.PINCODE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.PINCODE,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',',')))pincode, ");
		query.append(" if(ifnull(CAM.PRIMARY_PHONE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(ifnull(CAM.PRIMARY_PHONE,''),''),'!','! '),'%','% '),'(','('),')',')'),'-','-'),'{','{'),'[','[ '),'}','}'),']',']'),':',':'),';',';'),'/','/'),'?','?'),'.','.'),',',',')))PRIMARY_PHONE, ");
		query.append(" CP.product_desc as product_desc,CP.product_id as product_id, ");
		query.append(" ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE1),', '),'')as COMP_ADDRESS_LINE1,ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE2),', '),'')as COMP_ADDRESS_LINE2, ");
		query.append(" ifnull(concat(TRIM(BOTH ',' from com.ADDRESS_LINE3),', '),'')as COMP_ADDRESS_LINE3,ifnull(concat(com.CITY,' - '),'') as comp_city,ifnull(concat(COM.PINCODE,','),'')as comp_PINCODE,ifnull(CCM2.COUNTRY_DESC,'') as comp_country,com.CIN,ifnull(COM.website,' ') as website,ifnull(com.email,' ')as email,ifnull(com.FAX,'')as FAX,ifnull(com.PHONE_NO,'')as PHONE_NO ");
		query.append(",ifnull(com.COMPANY_SHORT_CODE,'') as COMPANY_SHORT_CODE, ");
		query.append(" REPLACE((SELECT GROUP_CONCAT(CUSTOMER_NAME,' |') FROM cr_loan_customer_role CLCR ");
		query.append(" JOIN gcd_customer_m GCM ON GCM.CUSTOMER_ID=CLCR.GCD_ID WHERE  LOAN_CUSTOMER_ROLE_TYPE='COAPPL' AND LOAN_ID=CLD.loan_id),'|','') AS COPPL,format(cld.loan_final_rate,2)as loan_final_rate,format(CLD.LOAN_LOAN_AMOUNT,2) as LOAN_LOAN_AMOUNT,CLD.loan_product as PRODUCT ");
		query.append(",DATE_FORMAT(STR_TO_DATE('"+fromInterestDate+"','%d-%m-%Y'),'%Y') as from_year , ");
		query.append(" DATE_FORMAT(STR_TO_DATE('"+toInterestDate+"' ,'%d-%m-%Y'),'%Y') as to_year, ");
		
		
		
		query.append(" format((ifnull(sum(INT_COMP_RECD),0))+(select ifnull(sum(ctd.txn_adjusted_amount),0) "
						+ " from cr_txnadvice_dtl CTD "
						+ " where CTD.LOAN_ID = '"+loanId+"' and charge_code_id in('6','9') and txn_type in ('ECA','PEM') "
						+ " and advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') "
						+ " and advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y')),2) as interest_component,  ");
					
		
		
		
		query.append(" format(ifnull(sum(if(PRIN_COMP_RECD<0,0,PRIN_COMP_RECD)),0)+(select ifnull(sum(ctd.txn_adjusted_amount),0) "
				+ " from cr_txnadvice_dtl CTD where CTD.LOAN_ID = '"+loanId+"' and charge_code_id in(18) and txn_type='ECA' "
				+ " and advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') "
				+ " and advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y')),2) as PRINCIPAL_component, ");
		
		
		query.append(" (SELECT DATE_FORMAT(PARAMETER_VALUE,'%d/%m/%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE' )AS lod_date1, ");
		
		
		query.append(" format(ifnull(((ifnull(sum(if(PRIN_COMP_RECD<0,0,PRIN_COMP_RECD)),''))+((ifnull(sum(INT_COMP_RECD),0)+(select ifnull(sum(ctd.txn_adjusted_amount),0) "
				+ " from cr_txnadvice_dtl CTD where CTD.LOAN_ID = '"+loanId+"'  and charge_code_id in('6','9','18') and "
				+ " txn_type in ('ECA','PEM') and advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') "
				+ " and advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y'))))),''),2)as Total  ");
		
		
		query.append(" FROM CR_LOAN_DTL CLD ");
		query.append(" left join cr_repaysch_dtl CRD on CLD.LOAN_ID=CRD.LOAN_ID  and instl_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y') and instl_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y') and instl_amount<>0  ");
		query.append(" left join cr_txnadvice_dtl CTD on CTD.LOAN_ID=CLD.loan_id and charge_code_id in ('6') and advice_date>=str_to_date('"+fromInterestDate+"','%d-%m-%Y')  and advice_date<=str_to_date('"+toInterestDate+"','%d-%m-%Y') ");
		query.append(" left JOIN GCD_CUSTOMER_M GCD ON(CLD.LOAN_CUSTOMER_ID=GCD.CUSTOMER_ID) ");
		query.append(" left join cr_product_m CP on (CLD.LOAN_PRODUCT= CP.product_id) and CP.rec_status='A' ");
		query.append(" left join com_address_m CAM on CAM.BPID=GCD.CUSTOMER_ID and COMMUNICATION_ADDRESS='Y' ");
		query.append(" left join com_district_m CDM1 on (CAM.DISTRICT=CDM1.DISTRICT_ID) ");
		query.append(" left join com_state_m CSM1 on (CAM.STATE=CSM1.STATE_ID) ");
		query.append(" left join com_country_m CCM1 on (CAM.COUNTRY=CCM1.COUNTRY_ID) ");
		query.append(" left join com_company_m com on true ");
		query.append(" left join com_country_m CCM2 on (com.COUNTRY=CCM2.COUNTRY_ID) ");
		query.append(" where 1=1 and  CLD.loan_id='"+loanId+"' ");
		logger.info(" Yearly Interest Certificate Query  :  "+query.toString());
	
		StringBuilder subquery=new StringBuilder();
		subquery.append("select @a:=@a+1 as sno,cacm.PROPERTY_OWNER, ");
		subquery.append("CONCAT(concat(if(ifnull(cacm.Property_address,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
		subquery.append("(concat(ifnull(TRIM(BOTH ',' from cacm.Property_address),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
		subquery.append("if(ifnull(cacm.ADDRESS_LINE2,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
		subquery.append("(concat(ifnull(TRIM(BOTH ',' from cacm.ADDRESS_LINE2),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),','))), ");
		subquery.append("concat(if(ifnull(cacm.ADDRESS_LINE3,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
		subquery.append("(concat(ifnull(TRIM(BOTH ',' from cacm.ADDRESS_LINE3),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),'')) ");
		subquery.append("), ");
		subquery.append("concat(if(ifnull(CDM.DISTRICT_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
		subquery.append("(concat(ifnull(CDM.DISTRICT_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
		subquery.append("if(ifnull(CSM.STATE_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
		subquery.append("(concat(ifnull(CSM.STATE_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),',')), ");
		subquery.append("if(ifnull(CCM.COUNTRY_DESC,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
		subquery.append("(concat(ifnull(CCM.COUNTRY_DESC,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),', ')), ");
		subquery.append("if(ifnull(cacm.PINCODE,'')='','',concat(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace ");
		subquery.append("(concat(ifnull(cacm.PINCODE,''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'/','/ '),'?','? '),'.','. '),',',', '),' ')) ");
		subquery.append(")) as prop_address ");
		subquery.append("from (SELECT @a := 0) r,cr_asset_collateral_m cacm ");
		subquery.append("join cr_loan_collateral_m cdca on (cdca.assetid=cacm.asset_id) ");
		subquery.append("join cr_loan_dtl cdd on (cdca.Loan_id=cdd.loan_id) ");
		subquery.append("left outer join com_district_m CDM on (cacm.DISTRICT=CDM.DISTRICT_ID) ");
		subquery.append("left outer join com_state_m CSM on (cacm.STATE=CSM.STATE_ID) ");
		subquery.append("left outer join com_country_m CCM on (cacm.COUNTRY=CCM.COUNTRY_ID) ");
		subquery.append("where cdd.loan_id='"+loanId+"' ");
		subquery.append("and ASSET_COLLATERAL_CLASS ='PROPERTY' order by cacm.asset_id");
		
		logger.info("Yearly Interest Certificate SubQuery  :  "+subquery.toString());

  reportParameterMap.put("query",query.toString());
  reportParameterMap.put("subQuery",subquery.toString());
  }
  
  
  String bDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d%m%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
  String passwordQuery="select date_format(g.CUSTOMER_DOB,'%d%m%Y') from cr_loan_dtl l join gcd_customer_m g on g.customer_id=l.loan_customer_id where loan_id='"+loanId+"'";
	String pass=ConnectionDAO.singleReturn(passwordQuery);
	logger.info("passwordQuery: "+passwordQuery);
	logger.info("pass: "+pass);
	String ownerPasswordQuery="SELECT IFNULL(VALUE,'')  FROM GENERIC_MASTER WHERE GENERIC_KEY='OWNER_PASSWORD' AND REC_STATUS='A' AND PARENT_VALUE='NA' ";
	String owner=ConnectionDAO.singleReturn(ownerPasswordQuery);
	if(CommonFunction.checkNull(owner).equalsIgnoreCase("")){
		owner=pass;
	}
  try
  {
    Connection connectDatabase = ConnectionDAO.getConnection();
    JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, reportParameterMap, connectDatabase);
    JRPdfExporter exporter = new JRPdfExporter();
    
	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filelocation + "/" +reportName+".pdf");
	exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
	exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
	exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD,pass);
	exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, owner);
	exporter.setParameter(
    JRPdfExporterParameter.PERMISSIONS, 
    new Integer(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING)
    );
  	exporter.exportReport();
	  connectDatabase.close();
	jasperPrint=null;
	exporter=null;
	reportStream=null;
  }
  catch (Exception e) {
   
    e.printStackTrace();
  }

  return generationStatus;
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