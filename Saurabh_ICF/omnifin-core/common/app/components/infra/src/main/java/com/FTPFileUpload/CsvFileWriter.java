package com.FTPFileUpload;

import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CsvFileWriter extends DispatchAction
{
  private final Logger logger = Logger.getLogger(CsvFileWriter.class);

  public ActionForward GenerateCSVFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    this.logger.info("In GenerateCSVFile...........");

    String filelocation = getParameterValue("MIS_LOCAL_DUMP_LOCALATION");
    String SanctionDumpFileName = getParameterValue("MIS_SANCTION_FILE_NAME");
    String LoginDumpFileName = getParameterValue("MIS_LOGIN_FILE_NAME");
    String MISFTPFlag = getParameterValue("MIS_FTP_FLAG");
    String MISLocalLoginForlder = getParameterValue("MIS_LOCAL_LOGIN_FOLDER_NAME");
    String MISLocalSanctionForlder = getParameterValue("MIS_LOCAL_SANCTION_FOLDER_NAME");

    this.logger.info("SanctionDumpFileName == " + SanctionDumpFileName);
    this.logger.info("LoginDumpFileName == " + LoginDumpFileName);
    this.logger.info("filelocation == " + filelocation);
    this.logger.info("MISFTPFlag == " + MISFTPFlag);
    try
    {
      if (CommonFunction.checkNull(MISFTPFlag).equalsIgnoreCase("Y")) {
        Connection con = ConnectionUploadDAO.getConnection();
        String LoginDumpQuery = CommonFunction.checkNull(ConnectionUploadDAO.singleReturn("SELECT QUERY FROM CR_DUMP_DOWNLOAD_DTL WHERE RECORD_DESC = 'MIS SUMMARY LOGIN DATA'"));
        String SanctionDumpQuery = CommonFunction.checkNull(ConnectionUploadDAO.singleReturn("SELECT QUERY FROM CR_DUMP_DOWNLOAD_DTL WHERE RECORD_DESC = 'MIS SUMMARY SANCTION DATA'"));
        LoginDumpQuery = LoginDumpQuery + ")";
        SanctionDumpQuery = SanctionDumpQuery + ")";
        this.logger.info("Login Query : -> " + LoginDumpQuery);
        this.logger.info("Sanction Query : -> " + SanctionDumpQuery);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String ImpliName = getParameterValue("MIS_IMPLI_NAME");

        PreparedStatement Loginps = con.prepareStatement(LoginDumpQuery);
        ResultSet LoginResult = Loginps.executeQuery();
        PreparedStatement Sanctionps = con.prepareStatement(SanctionDumpQuery);
        ResultSet SanctionResult = Sanctionps.executeQuery();

        String LoginFileName = ImpliName + "_" + timestamp + "_" + LoginDumpFileName;
        LoginFileName = LoginFileName.replaceAll(":", "-");
        String SanctionFileName = ImpliName + "_" + timestamp + "_" + SanctionDumpFileName;
        SanctionFileName = SanctionFileName.replaceAll(":", "-");

        String LoginDumpURL = filelocation + "\\" + MISLocalLoginForlder + "\\" + LoginFileName;
        String SanctionDumpURL = filelocation + "\\" + MISLocalSanctionForlder + "\\" + SanctionFileName;

        this.logger.info("Login Dump URL : " + LoginDumpURL);
        this.logger.info("Sanction Dump URL : " + SanctionDumpURL);

        rsToCSV(LoginResult, LoginDumpURL, true);
        rsToCSV(SanctionResult, SanctionDumpURL, true);
        con.close();

        sendFileToFTP(LoginDumpURL, "L", LoginFileName);
        sendFileToFTP(SanctionDumpURL, "S", SanctionFileName);
      } else {
        this.logger.info("*****************MIS DUMP UPLOAD OVER FTP IS DISABLED, PLEASE CHECK 'MIS_FTP_FLAG' KEY VALUE******************");
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public void rsToCSV(ResultSet rs, String url, boolean columnNames)
    throws SQLException, FileNotFoundException, IOException
  {
    File fl = new File(url);
    ResultSetMetaData metaData = rs.getMetaData();
    int columnCount = metaData.getColumnCount();
    OutputStream os = new FileOutputStream(fl);
    try
    {
      PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
      try {
        if (columnNames) {
          for (int i = 1; i <= columnCount; i++)
          {
            pw.print(metaData.getColumnName(i));
            if (i < columnCount) {
              pw.print(",");
              pw.flush();
            }
            if (i == columnCount) {
              pw.println();
              pw.flush();
            }
          }
        }
        while (rs.next()) {
          for (int i = 1; i <= columnCount; i++)
          {
            pw.print(rs.getObject(i));
            if (i < columnCount) {
              pw.print(",");
              pw.flush();
            }
            if (i == columnCount)
            {
              pw.println();
              pw.flush();
            }
          }
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      pw.flush();
      pw.close();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    os.flush();
    os.close();
  }

  public void sendFileToFTP(String sourceURL, String sourceType, String FileName)
  {
    String IP = getParameterValue("MIS_FTP_SERVER_IP");
    String server_port = getParameterValue("MIS_FTP_SERVER_PORT");
    String user_id = getParameterValue("MIS_FTP_SERVER_USER_ID");
    String passwprd = getParameterValue("MIS_FTP_SERVER_PASSWORD");
    String FTPAbsolutePath = getParameterValue("MIS_FTP_PATH");

    String server = IP;
    int port = Integer.parseInt(server_port);
    String user = user_id;
    String pass = passwprd;
    FTPClient ftpClient = new FTPClient();
    try
    {
      ftpClient.connect(server, port);
      ftpClient.login(user, pass);
      ftpClient.enterLocalPassiveMode();
      ftpClient.setFileType(2);
      String firstRemoteFile = FileName;

      File firstLocalFile = new File(sourceURL);
      InputStream inputStream = new FileInputStream(firstLocalFile);

      System.out.println("Start uploading first file");

      boolean done = ftpClient.changeWorkingDirectory(FTPAbsolutePath);
      done = ftpClient.storeFile(firstRemoteFile, inputStream);

      inputStream.close();

      if (done)
        this.logger.info("The first file is uploaded successfully.");
      else {
        this.logger.info("The first file is upload Failed");
      }

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public String getParameterValue(String parameterKey)
  {
    String ParameterValue = CommonFunction.checkNull(ConnectionUploadDAO.singleReturn(" SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = '" + parameterKey + "'"));
    this.logger.info("In getParameterValue and Key and Value :" + parameterKey + " : " + ParameterValue);
    return ParameterValue;
  }
}