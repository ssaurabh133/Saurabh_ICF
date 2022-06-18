package com.FTPFileUpload;

import com.cm.actionform.ExcelSheetUploadFormCM;
import com.commonFunction.daoImplMYSQL.commonFunctionDaoImpl;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.communication.engn.vo.SmsVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.ConnectionUploadDAO;
import com.connect.PrepStmtObject;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.BufferedOutputStream;
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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class AutoReportGeneratorSftp extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(commonFunctionDaoImpl.class.getName());
  Connection con = null;
  PreparedStatement ptmt = null;
  ResultSet rs = null;
  Statement stmt = null;
  CallableStatement csmt = null;
  public ActionForward SFTPInsertDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		    throws Exception
		  {
	  ArrayList qryList = new ArrayList();
	  boolean status=false;
	   String reportName = "";
	    String ReportTxnId = "";
	    String extension = "";
	    String filelocation = getParameterValue("SFTP_AUTO_GENERATED_DATA_FILE");
		    try
		    {
		      String qry = "INSERT INTO SFTP_SCHED_REPOER_GENERATION_DTL(TARGET_PROCESS,REPORT_NAME,DUMP_ID,DUMP_NAME,REQUEST_TEMPLATE,REQUEST,SCHEDULE_DATE,SCHEDULE_TIME,RECORD_STATUS,EXTENSION) SELECT IFNULL(TARGET_PROCESS,''),IFNULL(REPORT_NAME ,'') ,IFNULL(DUMP_ID, ''),IFNULL(DUMP_NAME,''),IFNULL(REQUEST_TEMPLATE ,''),IFNULL(REQUEST,''),SCHEDULE_DATE,now(),'P',EXTENSION FROM SFTP_SCHED_REPOER_GENERATION_M  WHERE rec_status='A'";
		      logger.info(new StringBuilder().append("In SFTPInsertDetail() query   :   ").append(qry).toString());
		      qryList.add(qry);
		      status = ConnectionDAO.sqlInsUpdDelete(qryList);
		      logger.info("status: "+status);
		    /*  if(status){
		    //Code for input file starts
		      try
		      {
		         qryList=new ArrayList();
		        String qry1 = "select record_id, report_name,extension from sftp_sched_repoer_generation_dtl where record_status = 'P' AND TARGET_PROCESS = 'D' ";
		        logger.info(new StringBuilder().append("In AutoGenerator()  query   :   ").append(qry1).toString());
		        qryList = ConnectionDAO.sqlSelect(qry1);
		        int size = qryList.size();
		        for (int i = 0; i < size; i++)
		        {
		          ArrayList dataList = (ArrayList)qryList.get(i);
		          if (dataList.size() > 0)
		          {
		            ReportTxnId = CommonFunction.checkNull(dataList.get(0));
		            reportName = CommonFunction.checkNull(dataList.get(1));
		            extension = CommonFunction.checkNull(dataList.get(2));
		           String fileName=dumpGenerator(ReportTxnId, reportName, filelocation, extension);
		            updateRecordStatus(ReportTxnId,fileName, "F");
		          //  sendEmail("FTP_UPLOAD_TRUE", reportName);
		            dataList.clear();
		            dataList = null;
		          }
		        }
		      } catch (Exception e) {
		        e.printStackTrace();
		        updateRecordStatus(ReportTxnId,"", "E");
		       // sendEmail("FTP_UPLOAD_FALSE", reportName);
		      }
		      //Code for input file end
		      }*/
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return null;
		  }
  public ActionForward AutoGenerator(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String p_company_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/logo.bmp").toString();
    String p_barcode_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/barcode.gif").toString();
    String p_indian_rupee_logo = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/rupee.bmp").toString();
    String p_imageBox = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/imageBox.bmp").toString();
    String p_imageCheckbox = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports/imageCheckbox.bmp").toString();
    String sub_reports_location = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports\\").toString();
    String SUBREPORT_DIR = new StringBuilder().append(getServlet().getServletContext().getRealPath("/")).append("reports\\").toString();
    String reportPath = "/reports/MYSQLREPORTS/";
    String reportName = "";
    String ReportTxnId = "";
    String extension = "";

    String filelocation = getParameterValue("SFTP_AUTO_GENERATED_DATA_FILE");
    //code for Reading output file starts
    try
    {
      ArrayList qryList = new ArrayList();
      String qry = "select record_id, concat(INPUT_FILE_NAME,'_R'),extension from sftp_sched_repoer_generation_dtl where record_status = 'F' AND TARGET_PROCESS = 'D' ";
      logger.info(new StringBuilder().append("In AutoGenerator()  query   :   ").append(qry).toString());
      qryList = ConnectionDAO.sqlSelect(qry);
      int size = qryList.size();
      for (int i = 0; i < size; i++)
      {
        ArrayList dataList = (ArrayList)qryList.get(i);
        if (dataList.size() > 0)
        {
          ReportTxnId = CommonFunction.checkNull(dataList.get(0));
          reportName = CommonFunction.checkNull(dataList.get(1));
          extension = CommonFunction.checkNull(dataList.get(2));
        
          String SFTPFolderName = getParameterValue("SFTP_OUPTPUT_FILE_LOCATION");
          String SFTPHistoryFolderName = getParameterValue("SFTP_OUTPUT_HST_FILE_LOCATION");
          String outputFilelocation = getParameterValue("SFTP_AUTO_OUTPUT_GENERATED_DATA_FILE");
          String FileName = new StringBuilder().append(reportName).append(".").append(extension).toString();
          boolean status=downloadSftpFromPath(FileName,outputFilelocation,SFTPFolderName);
          
          String DumpURL = new StringBuilder().append(outputFilelocation).append("\\").append(FileName).toString();
          logger.info(new StringBuilder().append("Login Dump URL : ").append(DumpURL).toString());
          
          if(status){
          uploadSftpFromPath(DumpURL,SFTPHistoryFolderName);
          boolean st=UploadUCIC(DumpURL);
          if(st){
         boolean st1=updateUCICStatus(reportName);  
         if(st1){
          updateRecordStatus(ReportTxnId,reportName, "A");
         }
          }
          }
    
          dataList.clear();
          dataList = null;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      updateRecordStatus(ReportTxnId,"", "E");
     // sendEmail("FTP_UPLOAD_FALSE", reportName);
    }
    //code end for reading output file
    //Code for input file starts
    try
    {
      ArrayList qryList = new ArrayList();
      String qry = "select record_id, report_name,extension from sftp_sched_repoer_generation_dtl where record_status = 'P' AND TARGET_PROCESS = 'D' ";
      logger.info(new StringBuilder().append("In AutoGenerator()  query   :   ").append(qry).toString());
      qryList = ConnectionDAO.sqlSelect(qry);
      int size = qryList.size();
      for (int i = 0; i < size; i++)
      {
        ArrayList dataList = (ArrayList)qryList.get(i);
        if (dataList.size() > 0)
        {
          ReportTxnId = CommonFunction.checkNull(dataList.get(0));
          reportName = CommonFunction.checkNull(dataList.get(1));
          extension = CommonFunction.checkNull(dataList.get(2));
         String fileName=dumpGenerator(ReportTxnId, reportName, filelocation, extension);
          updateRecordStatus(ReportTxnId,fileName, "F");
        //  sendEmail("FTP_UPLOAD_TRUE", reportName);
          dataList.clear();
          dataList = null;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      updateRecordStatus(ReportTxnId,"", "E");
     // sendEmail("FTP_UPLOAD_FALSE", reportName);
    }
    //Code for input file end
   

    return null;
  }

  public String dumpGenerator(String ReportTxnId, String reportName, String filelocation, String extension) {
    logger.info(new StringBuilder().append("In dumpGenerator Dump Name..............").append(reportName).toString());
    Connection con = ConnectionUploadDAO.getConnection();
    String FileName="";
    String dataQuery = CommonFunction.checkNull(ConnectionUploadDAO.singleReturn(new StringBuilder().append("SELECT QUERY FROM CR_DUMP_DOWNLOAD_DTL WHERE RECORD_DESC = '").append(reportName).append("'").toString()));
    String Todate = "";

    Todate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 DAY),'%Y-%m-%d') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));

    String Fromdate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT CONCAT(DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 0 MONTH ),'%Y-%m'),'-01') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
    String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
    if (CommonFunction.checkNull(Day).equalsIgnoreCase("01")) {
      Fromdate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT CONCAT(DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 MONTH ),'%Y-%m'),'-01') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
    }

    dataQuery = dataQuery.replaceAll("#p3", Todate);
    dataQuery = dataQuery.replaceAll("#p1", Fromdate);
    dataQuery = dataQuery.replaceAll("#p2", Todate);
    dataQuery = new StringBuilder().append(dataQuery).append(")").toString();

    logger.info(new StringBuilder().append("Login Query : -> ").append(dataQuery).toString());
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    try
    {
      PreparedStatement FinalDataPrepared = con.prepareStatement(dataQuery);
      ArrayList resultList = new ArrayList();
      resultList = ConnectionReportDumpsDAO.sqlSelect(dataQuery);

      ResultSet FinalDataResult = FinalDataPrepared.executeQuery();
      String bDate = CommonFunction.checkNull(ConnectionDAO.singleReturn("select DATE_FORMAT(now(),'%d%b%Y') "));
      reportName = reportName.replaceAll("_BI", "");
      reportName=reportName+"_"+bDate;
      String q1="select count(1) from sftp_sched_repoer_generation_dtl where INPUT_FILE_NAME like '%"+reportName+"%'";
      String count=ConnectionDAO.singleReturn(q1);
      int cnt=Integer.parseInt(count);
      if(cnt>0){
      reportName=reportName+"_"+count;
      }
       FileName = new StringBuilder().append(reportName).append(".").append(extension).toString();
      String DumpURL = new StringBuilder().append(filelocation).append("\\").append(FileName).toString();
      logger.info(new StringBuilder().append("Login Dump URL : ").append(DumpURL).toString());
      logger.info("resultList size: "+resultList.size());
      if(resultList.size()>1){
      rsToCSV(FinalDataResult, DumpURL, true, FileName, resultList);
      con.close();
      String SFTPFolderName = getParameterValue("SFTP_INPUT_FILE_LOCATION");
      String SFTPHistoryFolderName = getParameterValue("SFTP_INPUT_HST_FILE_LOCATION");
      uploadSftpFromPath(DumpURL,SFTPFolderName);
      uploadSftpFromPath(DumpURL,SFTPHistoryFolderName);
      }else{
    	  reportName="";
    	  con.close();
      }
     
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return reportName;
  }
  private ChannelSftp setupJsch() throws JSchException {
	    String user = getParameterValue("SFTP_USER_ID");
	    String pass = getParameterValue("SFTP_PASSWORD");
	    String server =getParameterValue("SFTP_HOST"); 
	    int port=Integer.parseInt(getParameterValue("SFTP_PORT"));
	    Session session = null;
	      Channel     channel     = null;
	      ChannelSftp channelSftp = null;
	    JSch jsch = new JSch();
		 session = jsch.getSession(user,server,port);
		 session.setPassword(pass);
		 java.util.Properties config = new java.util.Properties();
		 config.put("StrictHostKeyChecking", "no");
		 session.setConfig(config);
		 session.connect();
		 //here open the channel
		 channel = session.openChannel("sftp");
	    return (ChannelSftp) session.openChannel("sftp");
	  }
  public boolean uploadSftpFromPath(String localFile, String sftpFile) {
	    ChannelSftp channelSftp = null;
	    try {
	      channelSftp = setupJsch();
	    } catch (JSchException e) {
	      e.printStackTrace();
	    }
	    try {
	      channelSftp.connect();
	    } catch (JSchException e) {
	    	e.printStackTrace();
	    }
	    try{
	      channelSftp.put(localFile, sftpFile);
	      System.out.println("Upload Complete");
	    } catch (SftpException e) {
	    	e.printStackTrace();
	    }
	    channelSftp.exit();
	    return true;
	  }
  public boolean downloadSftpFromPath(String localFile,String filelocation, String sftpFile) throws IOException {
	    ChannelSftp channelSftp = null;
	    try {
	      channelSftp = setupJsch();
	    } catch (JSchException e) {
	      e.printStackTrace();
	    }
	    try {
	      channelSftp.connect();
	    } catch (JSchException e) {
	    	e.printStackTrace();
	    }
	    try{
	    	channelSftp = (ChannelSftp)channelSftp;

            String remoteFile1 = sftpFile+"/"+localFile;
            logger.info("Expected file == "+localFile);
            //here change the directory
            channelSftp.cd(remoteFile1);
            Vector ftpfiles = channelSftp.ls(sftpFile);
            if (!ftpfiles.isEmpty()) 
            {
   	        for(int i= 0 ; i < ftpfiles.size(); i++)
   	          {
                    //Represents a directory entry, representing a remote file or directory
   	        	 LsEntry ftpfile = (LsEntry) ftpfiles.get(i);
       	         String rmtfilename = ftpfile.getFilename();
                  	
   	
       	         if (rmtfilename.equalsIgnoreCase(localFile))
       	             {
       		           logger.info("GOT THE FILE");
   		
       		           logger.info("remoteFile1 == "+ localFile);
   		
       	           	   String dwnldlocation = filelocation+"/"+localFile;
       	
       		           logger.info("dwnldlocation == "+ dwnldlocation);
           
           
       		           File downloadFile1 = new File(dwnldlocation);
       		           OutputStream outputStream1;
					try {
						outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
						  channelSftp.get(remoteFile1, outputStream1);
	       		            outputStream1.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
          
       		         
        
           	
                       }
            } ftpfiles = null;
            }
	    } catch (SftpException e) {
	    	e.printStackTrace();
	    }
	    channelSftp.exit();
	    return true;
	  }

  public void rsToCSV(ResultSet rs, String url, boolean columnNames, String FileName, ArrayList list)
    throws SQLException, FileNotFoundException, IOException
  {
    logger.info(new StringBuilder().append("Report Size  :  ").append(list.size()).toString());
    int size = list.size();
    StringBuffer fileNameFormat = new StringBuffer();
    String fileName = "";
    String outputFileName = "";
    String outputFileNameOnly = "";
    FileInputStream inputStream = null;
    logger.info("CSV FILE GENERATION STARTS...");
    fileName = new StringBuilder().append(FileName).append(".csv").toString();

    File fl = new File(url);
    OutputStream os = new FileOutputStream(fl);

    PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
    ArrayList subList = new ArrayList();
    try {
      for (int i = 0; i < size; i++) {
        subList = (ArrayList)list.get(i);
        int subSize = subList.size();
        for (int j = 0; j < subSize; j++) {
        //  fileNameFormat.append('"');
          fileNameFormat.append(CommonFunction.checkNull(subList.get(j)));
         // fileNameFormat.append('"');
          fileNameFormat.append('|');
        }

        fileNameFormat.append("\n");
      }
      pw.write(fileNameFormat.toString());
    } catch (Exception e) {
      logger.info(new StringBuilder().append("error is:--").append(e).toString());
      e.printStackTrace();
    } finally {
      subList.clear();
      list.clear();
      fileName = null;
      fileNameFormat.setLength(0);
      fileNameFormat = null;
      pw.flush();
      pw.close();
      os.flush();
      os.close();
    }
  }
  public String getParameterValue(String parameterKey) {
    String ParameterValue = CommonFunction.checkNull(ConnectionUploadDAO.singleReturn(new StringBuilder().append(" SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = '").append(parameterKey).append("'").toString()));
    logger.info(new StringBuilder().append("In getParameterValue and Key and Value :").append(parameterKey).append(" : ").append(ParameterValue).toString());
    return ParameterValue;
  }

  public boolean updateRecordStatus(String id,String fileName ,String Generationstatus) {
    StringBuilder queryUpdate = new StringBuilder();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    ArrayList qryList = new ArrayList();

    String status = "";
    
    boolean UpdateStatus = false;
    if(CommonFunction.checkNull(fileName).equalsIgnoreCase("")){
    	String remarks="NO DATA FOUND";
        queryUpdate.append(" update sftp_sched_repoer_generation_dtl set RECORD_STATUS = 'A',REMARKS=? where RECORD_ID = ? ");

        
        if (CommonFunction.checkNull(remarks).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(remarks.trim());
          }
        if (CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(id.trim());
        }
        insertPrepStmtObject.setSql(queryUpdate.toString());
        }else{
    queryUpdate.append(" update sftp_sched_repoer_generation_dtl set RECORD_STATUS = ?,INPUT_FILE_NAME=? where RECORD_ID = ? ");

    if (CommonFunction.checkNull(Generationstatus).trim().equalsIgnoreCase(""))
      insertPrepStmtObject.addNull();
    else {
      insertPrepStmtObject.addString(Generationstatus.trim());
    }
    if (CommonFunction.checkNull(fileName).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(fileName.trim());
      }
    if (CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
      insertPrepStmtObject.addNull();
    else {
      insertPrepStmtObject.addString(id.trim());
    }
    insertPrepStmtObject.setSql(queryUpdate.toString());
    }
    logger.info(new StringBuilder().append("IN updateRecordStatus() update query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
    qryList.add(insertPrepStmtObject);
    logger.info(new StringBuilder().append("In updateRecordStatus ........ update query: ").append(queryUpdate).toString());
    try
    {
      UpdateStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In updateRecordStatus.........update status: ").append(UpdateStatus).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return UpdateStatus;
  }
  public boolean UploadUCIC(String fileNameWithPath) {
		  ArrayList alDeleteQuery = new ArrayList(1);
		  StringBuffer makerID = null;
		  boolean status = false;
		  try {
		    String query1 = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GL_CSV_EXCELUPLOAD'";
		    String rpt1 = ConnectionUploadDAO.singleReturn(query1);
		    query1 = null;

		    String strDeleteQuery = "DELETE FROM TEMP_UCIC_UPLOAD ";
		    alDeleteQuery.add(strDeleteQuery);
		    boolean status1 = ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);

		    if (status1)
		      logger.info("In UploadUCIC() Rows is deleted....");
		    else {
		      logger.info("In UploadUCIC Row is not deleted......");
		    }

		    KettleEnvironment.init(false);
		    EnvUtil.environmentInit();
		    TransMeta transMeta = new TransMeta(rpt1.concat("\\ucic-batch-uploading.ktr"));
		    Trans trans = new Trans(transMeta);

		    trans.setParameterValue("filePathWithName", fileNameWithPath);
		    

		    trans.execute(null);
		    trans.waitUntilFinished();

		    if (trans.getErrors() > 0)
		    {
		      status = false;
		      throw new RuntimeException("There were errors during transformation execution." + trans.getErrors());
		    }
		    status = true;

		    transMeta.clearCaches();
		    transMeta.clear();
		    rpt1 = null;
		    trans.clearParameters();
		    trans.cleanup();
		    strDeleteQuery = null;
		  }
		  catch (Exception e) {
		    logger.info("In UploadUCIC() Problem is ---->" + e.getMessage());
		  } finally {
		    makerID = null;
		    ConnectionUploadDAO.closeConnection(this.con, this.csmt, this.rs);
		  }

		  return status;
}
  public boolean updateUCICStatus(String fileName) {
	    StringBuilder queryUpdate = new StringBuilder();
	    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	    ArrayList qryList = new ArrayList();

	    String status = "";
	    
	    boolean UpdateStatus = false;
	    queryUpdate.append(" UPDATE GCD_CUSTOMER_M A JOIN TEMP_UCIC_UPLOAD B ON A.CUSTOMER_ID=B.CUSTCODE ");
	    queryUpdate.append(" SET A.UCIC_NO=B.UCIC,A.UCIC_REJECT_REASON=B.REJECTEDREASON,A.MATCH_TYPE=B.MATCHTYPE,A.UCIC_OUTPUT_FILE_NAME=? ");
	    queryUpdate.append(" WHERE B.CUSTCODE IS NOT NULL; ");

	    
	    if (CommonFunction.checkNull(fileName).trim().equalsIgnoreCase(""))
	        insertPrepStmtObject.addNull();
	      else {
	        insertPrepStmtObject.addString(fileName.trim());
	      }
	    insertPrepStmtObject.setSql(queryUpdate.toString());

	    logger.info(new StringBuilder().append("IN updateRecordStatus() update query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
	    qryList.add(insertPrepStmtObject);
	    logger.info(new StringBuilder().append("In updateRecordStatus ........ update query: ").append(queryUpdate).toString());
	    try
	    {
	      UpdateStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	      logger.info(new StringBuilder().append("In updateRecordStatus.........update status: ").append(UpdateStatus).toString());
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }

	    return UpdateStatus;
	  }
}