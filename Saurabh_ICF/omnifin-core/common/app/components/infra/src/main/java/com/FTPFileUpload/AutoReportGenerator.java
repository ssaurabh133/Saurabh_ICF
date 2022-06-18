package com.FTPFileUpload;

import com.commonFunction.daoImplMYSQL.commonFunctionDaoImpl;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.communication.engn.vo.SmsVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.ConnectionUploadDAO;
import com.connect.PrepStmtObject;

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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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

public class AutoReportGenerator extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(commonFunctionDaoImpl.class.getName());

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
    try
    {
      ArrayList qryList = new ArrayList();
      String qry = "select record_id, report_name,extension from sched_repoer_generation_dtl where record_status = 'P' AND TARGET_PROCESS = 'R' ";
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
          InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(new StringBuilder().append(reportPath).append(reportName).append(".jasper").toString());
          boolean Generationstatus = ReportGenerator(p_company_logo, p_barcode_logo, p_indian_rupee_logo, p_imageBox, p_imageCheckbox, reportPath, reportName, reportStream, sub_reports_location, SUBREPORT_DIR, ReportTxnId, extension, response);

          boolean Updatestatus = updateRecordStatus(ReportTxnId, Generationstatus);

          sendEmail("FTP_UPLOAD_TRUE", reportName);

          dataList.clear();
          dataList = null;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      sendEmail("FTP_UPLOAD_FALSE", reportName);
    }

    String filelocation = getParameterValue("AUTO_GENERATED_DATA_FILE");
    String SanctionDumpFileName = getParameterValue("MIS_SANCTION_FILE_NAME");
    String LoginDumpFileName = getParameterValue("MIS_LOGIN_FILE_NAME");
    String MISFTPFlag = getParameterValue("MIS_FTP_FLAG");
    String MISLocalLoginForlder = getParameterValue("MIS_LOCAL_LOGIN_FOLDER_NAME");
    String MISLocalSanctionForlder = getParameterValue("MIS_LOCAL_SANCTION_FOLDER_NAME");

    String disbursalDumpFileName = getParameterValue("MIS_DISBURSAL_FILE_NAME");
    String overdueDumpFileName = getParameterValue("MIS_OVERDUE_FILE_NAME");
    String MISLocalDisbursalForlder = getParameterValue("MIS_LOCAL_DISBURSAL_FOLDER_NAME");
    String MISLocalOverdueForlder = getParameterValue("MIS_LOCAL_OVERDUE_FOLDER_NAME");
    try
    {
      ArrayList qryList = new ArrayList();
      String qry = "select record_id, report_name,extension from sched_repoer_generation_dtl where record_status = 'P' AND TARGET_PROCESS = 'D' ";
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
          String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
    	    
          if(CommonFunction.checkNull(reportName).equalsIgnoreCase("FUTURE EMI RECEIVABLE REPORT")){
        	  if (CommonFunction.checkNull(Day).equalsIgnoreCase("01")) {
        	  String loanCountQuery="select count(1) from cr_loan_dtl ";
        		int loanCount=Integer.parseInt(ConnectionDAO.singleReturn(loanCountQuery));
        		int j=0;
        		int k=1;
        		while(j<=loanCount){
        			int start=j;
        			int end=start+1000;
        	  dumpGeneratorFutureEmi(ReportTxnId, reportName, filelocation, extension,start,end,k);
             
              j=end;
              k++;
        		}
        	  }	
          }else{
        	  dumpGenerator(ReportTxnId, reportName, filelocation, extension);
          }
  	    
          updateRecordStatus(ReportTxnId, true);
          sendEmail("FTP_UPLOAD_TRUE", reportName);
          dataList.clear();
          dataList = null;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      updateRecordStatus(ReportTxnId, false);
      sendEmail("FTP_UPLOAD_FALSE", reportName);
    }

    return null;
  }

  public void dumpGenerator(String ReportTxnId, String reportName, String filelocation, String extension) {
    logger.info(new StringBuilder().append("In dumpGenerator Dump Name..............").append(reportName).toString());
    Connection con = ConnectionUploadDAO.getConnection();

    String dataQuery = CommonFunction.checkNull(ConnectionUploadDAO.singleReturn(new StringBuilder().append("SELECT QUERY FROM CR_DUMP_DOWNLOAD_DTL WHERE RECORD_DESC = '").append(reportName).append("'").toString()));
    String Todate = "";
    String Impli = CommonFunction.checkNull(getParameterValue("BI_FTP_PATH"));

    if ((CommonFunction.checkNull(reportName).equalsIgnoreCase("CIBIL_INDIVIDUAL_UPLOAD_DUMP")) && (CommonFunction.checkNull(Impli).equalsIgnoreCase("/ftp_smebi")))
    {
      Todate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 DAY),'%d%m%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
    }
    else Todate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 DAY),'%Y-%m-%d') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));

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
    String ImpliName = getParameterValue("MIS_IMPLI_NAME");
    try
    {
      PreparedStatement FinalDataPrepared = con.prepareStatement(dataQuery);
      ArrayList resultList = new ArrayList();
      resultList = ConnectionReportDumpsDAO.sqlSelect(dataQuery);

      ResultSet FinalDataResult = FinalDataPrepared.executeQuery();
      String bDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d%m%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
      reportName = reportName.replaceAll("_BI", "");
      String FileName = new StringBuilder().append(reportName).append(".").append(extension).toString();
      String DumpURL = new StringBuilder().append(filelocation).append("\\").append(FileName).toString();
      logger.info(new StringBuilder().append("Login Dump URL : ").append(DumpURL).toString());

      rsToCSV(FinalDataResult, DumpURL, true, FileName, resultList);
      con.close();
      String FTPFolderName = reportName;

      if (CommonFunction.checkNull(FTPFolderName).equalsIgnoreCase("ASSET/COLLATORAL DUMP")) {
        FTPFolderName = "ASSET_COLLATORAL_DUMP";
      }
      sendFileToFTP(DumpURL, FTPFolderName, FileName);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public boolean ReportGenerator(String p_company_logo, String p_barcode_logo, String p_indian_rupee_logo, String p_imageBox, String p_imageCheckbox, String reportPath, String reportName, InputStream reportStream, String sub_reports_location, String SUBREPORT_DIR, String ReportTxnId, String extension, HttpServletResponse response)
  {
    logger.info(new StringBuilder().append("In Report Generator.......").append(reportName).toString());

    String actualReportName = reportName;
    String filelocation = getParameterValue("AUTO_GENERATED_DATA_FILE");
    sub_reports_location = new StringBuilder().append(sub_reports_location).append("MYSQLREPORTS\\").toString();
    SUBREPORT_DIR = new StringBuilder().append(SUBREPORT_DIR).append("MYSQLREPORTS\\").toString();
    boolean generationStatus = true;
    Map reportParameterMap = SetReportFilters(reportName, SUBREPORT_DIR, sub_reports_location, p_imageCheckbox, p_imageBox, p_indian_rupee_logo, p_barcode_logo, p_company_logo);
    String bDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d%m%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    String ImpliName = getParameterValue("MIS_IMPLI_NAME");
    reportName = new StringBuilder().append(reportName).append(".").append(extension).toString();
    reportName = reportName.replaceAll(":", "-");
    try
    {
      Connection connectDatabase = ConnectionDAO.getConnection();
      JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, reportParameterMap, connectDatabase);

      JExcelApiExporter exporterXLS = new JExcelApiExporter();
      exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
      exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
      exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
      exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
      exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, new StringBuilder().append(filelocation).append("\\").append(reportName).toString());
      exporterXLS.exportReport();

      if (generationStatus)
        generationStatus = sendFileToFTP(new StringBuilder().append(filelocation).append("\\").append(reportName).toString(), actualReportName, reportName);
    }
    catch (Exception e) {
      generationStatus = false;
      updateRecordStatus(ReportTxnId, generationStatus);
      e.printStackTrace();
    }

    return generationStatus;
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
          fileNameFormat.append('"');
          fileNameFormat.append(CommonFunction.checkNull(subList.get(j)));
          fileNameFormat.append('"');
          fileNameFormat.append(',');
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

  public boolean sendFileToFTP(String sourceURL, String FileName, String GenerateFileName)
  {
    String FTPAbsolutePath = getParameterValue("BI_FTP_PATH");
    boolean FTPUploadStatus = true;
    boolean checkMonthEnd = isMonthEnd();

    if (checkMonthEnd) {
      FTPAbsolutePath = new StringBuilder().append(FTPAbsolutePath).append("/").append("Monthly/").append(FileName).toString();
      FTPUploadStatus = UploadOverFTP(FTPAbsolutePath, GenerateFileName, sourceURL);
    }
    FTPAbsolutePath = getParameterValue("BI_FTP_PATH");
    FTPAbsolutePath = new StringBuilder().append(FTPAbsolutePath).append("/").append("Daily/").append(FileName).toString();
    FTPUploadStatus = UploadOverFTP(FTPAbsolutePath, GenerateFileName, sourceURL);

    return FTPUploadStatus;
  }

  private Map SetReportFilters(String reportName, String SUBREPORT_DIR, String sub_reports_location, String p_imageCheckbox, String p_imageBox, String p_indian_rupee_logo, String p_barcode_logo, String p_company_logo)
  {
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

    String p_date_to = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_SUB(PARAMETER_VALUE, INTERVAL 1 DAY) FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
    String p_date_from = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT CONCAT(DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 0 MONTH ),'%Y-%m'),'-01') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));

    String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));

    if (CommonFunction.checkNull(Day).equalsIgnoreCase("01")) {
      p_date_from = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT CONCAT(DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 MONTH ),'%Y-%m'),'-01') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
    }
    if(CommonFunction.checkNull(reportName).equalsIgnoreCase("Deals_Pending_Approved_Approved_Rejected_Cancelled_During_a_Period_Quaterly") || CommonFunction.checkNull(reportName).equalsIgnoreCase("Deals_Pending_Approved_Rejected_Cancelled_During_a_Period_Quaterly") || CommonFunction.checkNull(reportName).equalsIgnoreCase("Disbursal_Done_During_a_Period_Quaterly") ){
      	 p_date_from = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT date_add(CONCAT(DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 0 MONTH ),'%Y-%m'),'-01'), INTERVAL -3 MONTH) FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
      }
    logger.info(new StringBuilder().append("p_date_to : ").append(p_date_to).toString());
    logger.info(new StringBuilder().append("p_date_from : ").append(p_date_from).toString());

    if (CommonFunction.checkNull(reportName).equalsIgnoreCase("Disbursal_Done_During_a_Period") ||  CommonFunction.checkNull(reportName).equalsIgnoreCase("Disbursal_Done_During_a_Period_Quaterly") || CommonFunction.checkNull(reportName).equalsIgnoreCase("PRESENTAION_RECEIPT_STATUS") || CommonFunction.checkNull(reportName).equalsIgnoreCase("paymentReport")
    		|| CommonFunction.checkNull(reportName).equalsIgnoreCase("earlyClosureReport") 
    		|| CommonFunction.checkNull(reportName).equalsIgnoreCase("Delinquency_Report") 
    		|| CommonFunction.checkNull(reportName).equalsIgnoreCase("EMI_DUE_Report")) {
      String date_to = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 DAY),'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
      String date_from = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT CONCAT('01-',DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 0 MONTH ),'%m-%Y')) FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));

      if (CommonFunction.checkNull(Day).equalsIgnoreCase("01")) {
        date_from = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT CONCAT('01-',DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 MONTH ),'%m-%Y')) FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
      }
      if(CommonFunction.checkNull(reportName).equalsIgnoreCase("Disbursal_Done_During_a_Period_Quaterly") ){
    	  date_from = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT CONCAT('01-',DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 3 MONTH ),'%m-%Y')) FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
       }
      reportParameterMap.put("p_date_to", date_to);
      reportParameterMap.put("p_date_from", date_from);
      reportParameterMap.put("p_printed_by", "A3S1186");
      reportParameterMap.put("p_printed_date", p_date_to);

      String p_p_date = "";
      if (CommonFunction.checkNull(Day).equalsIgnoreCase("01"))
        p_p_date = new StringBuilder().append(" From   ").append(ConnectionDAO.singleReturn(" SELECT CONCAT('01-',DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 MONTH ),'%M-%Y')) FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE'")).append("   To   ").append(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 DAY),'%d-%M-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' ")).append(" ").toString();
      else {
        p_p_date = new StringBuilder().append(" From   ").append(ConnectionDAO.singleReturn(" SELECT CONCAT('01-',DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 0 MONTH ),'%M-%Y')) FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' ")).append("   To   ").append(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 DAY),'%d-%M-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' ")).append(" ").toString();
      }
      if(CommonFunction.checkNull(reportName).equalsIgnoreCase("Disbursal_Done_During_a_Period_Quaterly") ){
    	  p_p_date = new StringBuilder().append(" From   ").append(ConnectionDAO.singleReturn(" SELECT CONCAT('01-',DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 3 MONTH ),'%M-%Y')) FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' ")).append("   To   ").append(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 DAY),'%d-%M-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' ")).append(" ").toString();
    	
       }
      if (CommonFunction.checkNull(reportName).equalsIgnoreCase("PRESENTAION_RECEIPT_STATUS"))
      {
        reportParameterMap.put("reporttype", "E");
        reportParameterMap.put("p_current_from_date", date_from);
        reportParameterMap.put("p_current_to_date", date_to);
        reportParameterMap.put("p_user_id", "A3S1186");
        ArrayList in = new ArrayList();
        ArrayList out = new ArrayList();
        ArrayList outMessages = new ArrayList();
        String s1 = "";
        String s2 = "";
        
        in.add(CommonFunction.changeFormat(date_from));
        in.add(CommonFunction.changeFormat(date_to));
        in.add("A3S1186");
        out.add(s1);
        out.add(s2);
        try
        {
          logger.info(new StringBuilder().append("RPT_PRESENTAION_RECEIPT_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
        	  outMessages = (ArrayList)ConnectionDAO.callSP("RPT_PRESENTAION_RECEIPT_REPORT", in, out);
          s1 = CommonFunction.checkNull(outMessages.get(0));
          s2 = CommonFunction.checkNull(outMessages.get(1));
          logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
          logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
          if (s1.equalsIgnoreCase("E"))
            logger.info("In RPT_PRESENTAION_RECEIPT_REPORT reports can't be generate ");
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }

      }
      if (CommonFunction.checkNull(reportName).equalsIgnoreCase("earlyClosureReport"))
      {
    	  reportParameterMap.put("reporttype", "E");
        ArrayList in = new ArrayList();
        ArrayList out = new ArrayList();
        ArrayList outMessages = new ArrayList();
        String s1 = "";
        String s2 = "";

        in.add(p_p_date);
        in.add(CommonFunction.changeFormat(date_from));
        in.add(CommonFunction.changeFormat(date_to));
        in.add("All");
        in.add("");
        in.add("A");
        in.add("");
        in.add("");
        in.add("All");
        in.add("A3S1186");
        out.add(s1);
        out.add(s2);
        try
        {
          logger.info(new StringBuilder().append("RPT_EARLY_CLOSURE_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
          outMessages = (ArrayList)ConnectionDAO.callSP("RPT_EARLY_CLOSURE_REPORT", in, out);
          s1 = CommonFunction.checkNull(outMessages.get(0));
          s2 = CommonFunction.checkNull(outMessages.get(1));
          logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
          logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
          if (s1.equalsIgnoreCase("E"))
            logger.info("In RPT_EARLY_CLOSURE_REPORT reports can't be generate ");
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      if (CommonFunction.checkNull(reportName).equalsIgnoreCase("Delinquency_Report"))
      {
    	reportParameterMap.put("reporttype", "E");
        ArrayList in = new ArrayList();
        ArrayList out = new ArrayList();
        ArrayList outMessages = new ArrayList();
        String s1 = "";
        String s2 = "";

        in.add(CommonFunction.changeFormat(date_to));
        in.add("All");
        in.add("");
        in.add("A");
        in.add("");
        in.add("");
        in.add("A3S1186");
        out.add(s1);
        out.add(s2);
        try
        {
          logger.info(new StringBuilder().append("RPT_DELINQUENCY_DUMP_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
          outMessages = (ArrayList)ConnectionDAO.callSP("RPT_DELINQUENCY_DUMP_REPORT", in, out);
          s1 = CommonFunction.checkNull(outMessages.get(0));
          s2 = CommonFunction.checkNull(outMessages.get(1));
          logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
          logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
          if (s1.equalsIgnoreCase("E"))
            logger.info("In RPT_DELINQUENCY_DUMP_REPORT reports can't be generate ");
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      if(CommonFunction.checkNull(reportName).equalsIgnoreCase("EMI_DUE_Report")){
    	  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
    	  String dateFormat = resource.getString("lbl.dateInDao");
    	  reportParameterMap.put("reporttype", "E");
    	  reportParameterMap.put("loan_date", "");  
    	  reportParameterMap.put("p_date_format", dateFormat);
    	  reportParameterMap.put("p_from_installment_date", p_date_from);
    	  reportParameterMap.put("p_to_installment_date", p_date_to);
      }
      reportParameterMap.put("p_p_date", p_p_date);
      reportParameterMap.put("p_loan_type", "A");
      reportParameterMap.put("p_loan_from", "All");
      reportParameterMap.put("p_loan_to", "All");
      reportParameterMap.put("p_branch", "All");
      reportParameterMap.put("p_branch_type", "All");
      reportParameterMap.put("p_printed_loan", "All");
      reportParameterMap.put("loanClass", "All");
      reportParameterMap.put("productCategory", "All");
      reportParameterMap.put("p_user_id", "A3S1186");
      reportParameterMap.put("p_report_format", "E");
      reportParameterMap.put("p_status", "All");
    } else {
      reportParameterMap.put("p_report_format", "E");
      reportParameterMap.put("p_date_to", p_date_to);
      reportParameterMap.put("p_date_from", p_date_from);
      reportParameterMap.put("p_printed_by", "A3S1186");
      reportParameterMap.put("p_printed_date", p_date_to);
      reportParameterMap.put("p_p_date", p_date_to);
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
      reportParameterMap.put("p_p_date_to", CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d-%m-%Y%') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' ")));
      if (CommonFunction.checkNull(reportName).equalsIgnoreCase("BOOKING_REPORT_MONTHLY"))
      {
        reportParameterMap.put("reporttype", "E");
        ArrayList in = new ArrayList();
        ArrayList out = new ArrayList();
        ArrayList outMessages = new ArrayList();
        String s1 = "";
        String s2 = "";

        String p_current_from_date = p_date_from;
        in.add(p_current_from_date);
        String p_current_to_date = p_date_to;
        in.add(p_current_to_date);
        in.add("A3S1186");
        out.add(s1);
        out.add(s2);
        try
        {
          logger.info(new StringBuilder().append("GENERATE_BOOKING_REPORT (").append(in.toString()).append(",").append(out.toString()).toString());
          outMessages = (ArrayList)ConnectionDAO.callSP("GENERATE_BOOKING_REPORT_MONTHLY", in, out);
          s1 = CommonFunction.checkNull(outMessages.get(0));
          s2 = CommonFunction.checkNull(outMessages.get(1));
          logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
          logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
          if (s1.equalsIgnoreCase("E"))
            logger.info("In GENERATE_BOOKING_REPORT reports can't be generate ");
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }

      }

    }

    return reportParameterMap;
  }

  public String getParameterValue(String parameterKey) {
    String ParameterValue = CommonFunction.checkNull(ConnectionUploadDAO.singleReturn(new StringBuilder().append(" SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = '").append(parameterKey).append("'").toString()));
    logger.info(new StringBuilder().append("In getParameterValue and Key and Value :").append(parameterKey).append(" : ").append(ParameterValue).toString());
    return ParameterValue;
  }

  public boolean updateRecordStatus(String id, boolean Generationstatus) {
    StringBuilder queryUpdate = new StringBuilder();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    ArrayList qryList = new ArrayList();

    String status = "";
    if (Generationstatus)
      status = "A";
    else {
      status = "E";
    }
    boolean UpdateStatus = false;
    queryUpdate.append(" update sched_repoer_generation_dtl set RECORD_STATUS = ? where RECORD_ID = ? ");

    if (CommonFunction.checkNull(status).trim().equalsIgnoreCase(""))
      insertPrepStmtObject.addNull();
    else {
      insertPrepStmtObject.addString(status.trim());
    }
    if (CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
      insertPrepStmtObject.addNull();
    else {
      insertPrepStmtObject.addString(id.trim());
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

  public boolean isMonthEnd() {
    String currentMonth = CommonFunction.checkNull(ConnectionDAO.singleReturn(" select date_format(parameter_value,'%m') from parameter_mst where parameter_key = 'business_date' "));
    String currentDay = CommonFunction.checkNull(ConnectionDAO.singleReturn(" select date_format(parameter_value,'%d') from parameter_mst where parameter_key = 'business_date' "));
    boolean status = false;
    if ((currentMonth.equalsIgnoreCase("01")) || (currentMonth.equalsIgnoreCase("03")) || (currentMonth.equalsIgnoreCase("05")) || (currentMonth.equalsIgnoreCase("07")) || (currentMonth.equalsIgnoreCase("08")) || (currentMonth.equalsIgnoreCase("10")) || (currentMonth.equalsIgnoreCase("12")))
    {
      if (currentDay.equalsIgnoreCase("31"))
        status = true;
    }
    else if ((currentMonth.equalsIgnoreCase("04")) || (currentMonth.equalsIgnoreCase("06")) || (currentMonth.equalsIgnoreCase("09")) || (currentMonth.equalsIgnoreCase("11")))
    {
      if (currentDay.equalsIgnoreCase("30"))
        status = true;
    }
    else if ((currentMonth.equalsIgnoreCase("02")) && 
      (currentDay.equalsIgnoreCase("28"))) {
      status = true;
    }

    return status;
  }

  public boolean sendEmail(String EventName, String FileName) {
    boolean status = false;
    try
    {
      int count = Integer.parseInt(CommonFunction.checkNull(ConnectionDAO.singleReturn(new StringBuilder().append("SELECT COUNT(1) FROM COMM_EVENT_LIST_M WHERE EVENT_NAME = '").append(EventName).append("' AND REC_STATUS = 'A' ").toString())));

      if (count > 0)
      {
        String FTPAbsolutePath = getParameterValue("BI_FTP_PATH");
        boolean checkMonthEnd = isMonthEnd();

        if (checkMonthEnd)
          FTPAbsolutePath = new StringBuilder().append(FTPAbsolutePath).append("/").append("Monthly/").append(FileName).toString();
        else {
          FTPAbsolutePath = new StringBuilder().append(FTPAbsolutePath).append("/").append("Daily/").append(FileName).toString();
        }

        SmsVO vo = new SmsVO();
        String bdate = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT date_format(PARAMETER_VALUE,'%d-%m-%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY LIKE 'BUSINESS_DATE'"));

        String emailList = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = 'FTP_FILE_UPLOAD_EMAIL' AND REC_STATUS = 'A'"));

        vo.setbDate(bdate);
        vo.setStage("ONCLICK");
        vo.setEventName(EventName);
        String Message = CommonFunction.checkNull(ConnectionDAO.singleReturn(new StringBuilder().append("SELECT MESSAGE FROM COMM_SMS_DATA WHERE EVENT_NAME = '").append(EventName).append("'").toString()));

        Message = Message.replaceAll("<<REPORT_NAME>>", FileName);
        Message = Message.replaceAll("<<REPORT_PATH>>", FTPAbsolutePath);

        String subject = CommonFunction.checkNull(ConnectionDAO.singleReturn(new StringBuilder().append("SELECT EMAIL_SUBJECT FROM COMM_EVENT_LIST_M WHERE EVENT_NAME = '").append(EventName).append("'").toString()));
        vo.setMessage(Message);
        vo.setTemplate("E");
        vo.setEmailId(emailList);
        vo.setSubject(subject);
        String ToEmailId = ConnectionDAO.singleReturn("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = 'FTP_FILE_UPLOAD_EMAIL'  AND REC_STATUS = 'A' AND PARENT_VALUE = 'TO'");

        String CCEmailId = ConnectionDAO.singleReturn("SELECT GROUP_CONCAT(VALUE) FROM GENERIC_MASTER WHERE GENERIC_KEY = 'FTP_FILE_UPLOAD_EMAIL' AND REC_STATUS = 'A' AND PARENT_VALUE = 'CC'");

        status = new SmsDAOImpl().InsertData(vo);
        status = new SmsDAOImpl().MultipleInToSendEmail(vo, ToEmailId, CCEmailId);
        logger.info(new StringBuilder().append("Send Email -->").append(status).toString());

        if (status) {
          StringBuffer updatLoan = new StringBuffer();
          ArrayList queryList = new ArrayList();
          updatLoan.append(new StringBuilder().append("UPDATE COMM_EVENT_DATA SET RECORD_STATUS = 'A' WHERE EVENT_NAME = '").append(vo.getEventName()).append("'").toString());
          PrepStmtObject prepStmt = new PrepStmtObject();
          prepStmt.setSql(updatLoan.toString());
          queryList.add(prepStmt);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
          logger.info(new StringBuilder().append("Update Event Flag : ").append(status).toString());
        }
      }
      else {
        logger.info("**************FTP_FILE_UPLOAD Event Is Disabled***********************");
        status = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return status;
  }

  public boolean UploadOverFTP(String FTPAbsolutePath, String GenerateFileName, String sourceURL) {
    boolean FTPUploadStatus = true;

    String IP = getParameterValue("BI_FTP_SERVER_IP");
    String server_port = getParameterValue("BI_FTP_SERVER_PORT");
    String user_id = getParameterValue("BI_FTP_SERVER_USER_ID");
    String passwprd = getParameterValue("BI_FTP_SERVER_PASSWORD");
    logger.info(new StringBuilder().append("Final FTP Path : ").append(FTPAbsolutePath).toString());

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
      String firstRemoteFile = GenerateFileName;

      File firstLocalFile = new File(sourceURL);
      InputStream inputStream = new FileInputStream(firstLocalFile);

      System.out.println("Start uploading first file");

      boolean done = ftpClient.changeWorkingDirectory(FTPAbsolutePath);

      if (done)
        done = ftpClient.storeFile(firstRemoteFile, inputStream);
      else {
        logger.error("Unable to change to FTP working directly");
      }

      inputStream.close();

      if (done)
        logger.info(new StringBuilder().append("File Uploaded Successfully At : ").append(FTPAbsolutePath).toString());
      else
        logger.error(new StringBuilder().append("File upload Failed ").append(FTPAbsolutePath).toString());
    }
    catch (Exception ex) {
      FTPUploadStatus = false;
      ex.printStackTrace();
    }
    return FTPUploadStatus;
  }
  private void dumpGeneratorFutureEmi(String reportTxnId, String reportName,
			String filelocation, String extension, int start, int end,int k) {
		    logger.info(new StringBuilder().append("In dumpGenerator Dump Name..............").append(reportName).toString());
		    Connection con = ConnectionUploadDAO.getConnection();

		    String dataQuery = CommonFunction.checkNull(ConnectionUploadDAO.singleReturn(new StringBuilder().append("SELECT QUERY FROM CR_DUMP_DOWNLOAD_DTL WHERE RECORD_DESC = '").append(reportName).append("'").toString()));
		    String Todate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 DAY),'%Y-%m-%d') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
		    String Fromdate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT CONCAT(DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 0 MONTH ),'%Y-%m'),'-01') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
		    String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
		    if (CommonFunction.checkNull(Day).equalsIgnoreCase("01")) {
		      Fromdate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT CONCAT(DATE_FORMAT(DATE_SUB(PARAMETER_VALUE, INTERVAL 1 MONTH ),'%Y-%m'),'-01') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
		    }
		    String l1=start+"";
		    String l2=end+"";
		    dataQuery = dataQuery.replaceAll("#l1",l1 );
		    dataQuery = dataQuery.replaceAll("#l2", l2);
		    
		    dataQuery = new StringBuilder().append(dataQuery).append(")").toString();

		    logger.info(new StringBuilder().append("Login Query : -> ").append(dataQuery).toString());
		    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		    String ImpliName = getParameterValue("MIS_IMPLI_NAME");
		    try
		    {
		      PreparedStatement FinalDataPrepared = con.prepareStatement(dataQuery);
		      ArrayList resultList = new ArrayList();
		      resultList = ConnectionReportDumpsDAO.sqlSelect(dataQuery);

		      ResultSet FinalDataResult = FinalDataPrepared.executeQuery();
		      String bDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DATE_FORMAT(PARAMETER_VALUE,'%d%m%Y') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'BUSINESS_DATE' "));
		      reportName = reportName.replaceAll("_BI", "");
		      String FileName = new StringBuilder().append(reportName+k).append(".").append(extension).toString();
		      String DumpURL = new StringBuilder().append(filelocation).append("\\").append(FileName).toString();
		      logger.info(new StringBuilder().append("Login Dump URL : ").append(DumpURL).toString());

		      rsToCSV(FinalDataResult, DumpURL, true, FileName, resultList);
		      con.close();
		      String FTPFolderName = reportName;

		      sendFileToFTP(DumpURL, FTPFolderName, FileName);
		    }
		    catch (Exception ex)
		    {
		      ex.printStackTrace();
		    }
		  }
}