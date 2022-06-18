package com.cm.actions;

import com.cm.dao.ReportsDAO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.UploadDocument;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.DynaActionForm;

public class CibilReportDownLoadAction extends Action
{
  private static final Logger logger = Logger.getLogger(CibilReportDownLoadAction.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    logger.info("In CibilReportDownLoadAction");
    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");

    String cibilResult = null;
    try
    {
      HttpSession session = request.getSession();

      UserObject userobj = (UserObject)session.getAttribute("userobject");
      String businessDate = "";
      if (userobj != null) {
        businessDate = userobj.getBusinessdate();
      } else {
        logger.info(" in execute method of CibilReportDownLoadAction  action the session is out----------------");
        return mapping.findForward("sessionOut");
      }
      Object sessionId = session.getAttribute("sessionID");

      ServletContext context = getServlet().getServletContext();
      String strFlag = "";

      if (sessionId != null) {
        strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
      }
      logger.info("strFlag .............. " + strFlag);
      if (!strFlag.equalsIgnoreCase(""))
      {
        if (strFlag.equalsIgnoreCase("sameUserSession"))
        {
          context.removeAttribute("msg");
          context.removeAttribute("msg1");
        }
        else if (strFlag.equalsIgnoreCase("BODCheck"))
        {
          context.setAttribute("msg", "B");
        }
        return mapping.findForward("logout");
      }

      Calendar currentDate = Calendar.getInstance();
      SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
      String currantDate = formatter.format(currentDate.getTime());
      String dbType = resource.getString("lbl.dbType");
      String userId = userobj.getUserId();

      businessDate = businessDate.substring(0, 2) + businessDate.substring(3, 5) + businessDate.substring(6);
      DynaActionForm daf = (DynaActionForm)form;
      String fromDate = (String)daf.get("fromDate");
      String toDate = (String)daf.get("toDate");
      String cutOffDate = (String)daf.get("cutOffDate");
      String fromDateDB = CommonFunction.changeFormat(fromDate);
      String toDateDB = CommonFunction.changeFormat(toDate);
      String reportName = (String)daf.get("report");

      ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance("REPORTD");
      logger.info("Implementation class: " + dao.getClass());
      if ((!CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("CIBIL_INDIVIDUAL_DOWNLOAD")) && (!CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("CIBIL_CORPORATE_DOWNLOAD")) && (!CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("CIBIL_CORPORATE_DOWNLOAD_LC"))) {
        if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("cibilReport"))
        {
          boolean status = dao.checkNoOfLoan(fromDateDB, toDateDB);
          if (!status)
          {
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);
            request.setAttribute("overFlow", "overFlow");
            int loanLimit = dao.getMaxLoanLimit();
            request.setAttribute("loanLimit", Integer.valueOf(loanLimit));
            String cutDate = dao.getcutOffDate();
            request.setAttribute("cutOffDate", cutDate);
            return mapping.findForward("error");
          }
        }
        ArrayList result = dao.getCibilReport(currantDate, businessDate, fromDateDB, toDateDB, cutOffDate, userId, reportName);
        String error = (String)result.get(0);
        String s1 = (String)result.get(1);
        String s2 = (String)result.get(2);
        if ((error.trim().equalsIgnoreCase("error")) || (s1.trim().equalsIgnoreCase("E")))
        {
          String errorMsg = "";
          if (s2.trim().equalsIgnoreCase(""))
            errorMsg = "Some error occure! Please contact administrator.";
          else
            errorMsg = s2;
          request.setAttribute("error", errorMsg);
          String cutDate = dao.getcutOffDate();
          request.setAttribute("cutOffDate", cutDate);
          return mapping.findForward("error");
        }

        Connection connectDatabase = ConnectionReportDumpsDAO.getConnection();
        Map hashMap = new HashMap();
        hashMap.put("IS_IGNORE_PAGINATION", Boolean.valueOf(true));
        hashMap.put("userId", userId);

        String reportPath = "/reports/";
        if (dbType.equalsIgnoreCase("MSSQL"))
          reportPath = reportPath + "MSSQLREPORTS/";
        else {
          reportPath = reportPath + "MYSQLREPORTS/";
        }
        InputStream reportStream = getServlet().getServletConfig().getServletContext().getResourceAsStream(reportPath + reportName + ".jasper");
        JasperPrint jasperPrint = null;
        try
        {
          jasperPrint = JasperFillManager.fillReport(reportStream, hashMap, connectDatabase);
          if (reportName.trim().equalsIgnoreCase("cibilReport"))
            methodForExport(reportName, hashMap, connectDatabase, response, jasperPrint, request);
          else
            methodForExcel(reportName, hashMap, connectDatabase, response, jasperPrint);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        finally
        {
          result.clear();
          daf.reset(mapping, request);
          currantDate = null;
          dbType = null;
          userId = null;
          cutOffDate = null;
          fromDateDB = null;
          toDateDB = null;
          reportName = null;
          error = null;
          s1 = null;
          s2 = null;
          reportPath = null;
          strFlag = null;
          businessDate = null;
          ConnectionReportDumpsDAO.closeConnection(connectDatabase, null);
          hashMap.clear();
        }

      }
      else if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("CIBIL_INDIVIDUAL_DOWNLOAD"))
      {
        String result = dao.CibilIndividualFileGenerator(fromDate, userId);
        logger.info("Respone of proc got on Action::: " + result.length());
        String FileName = dao.CibilFileNameGenerator(fromDate);

        String pathQury = "Select parameter_value from parameter_mst where parameter_key='UPLOAD_PATH'";
        logger.info("query for fetching path::::" + pathQury);
        String Path = CommonFunction.checkNull(ConnectionDAO.singleReturn(pathQury));
        logger.info("Path::::" + Path);

        if (!CommonFunction.checkNull(result).equalsIgnoreCase(""))
        {
          methodForText(Path, FileName, result, response, request);
          request.setAttribute("FileName", FileName);
          request.setAttribute("msg", "S");
          cibilResult = null;
        }
        else {
          request.setAttribute("msg", "E");
          cibilResult = "error";
        }

      }
      else if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("CIBIL_CORPORATE_DOWNLOAD_LC"))
      {
        String result = dao.CibilCorporateLCFileGenerator(fromDate, userId);
        logger.info("Respone of proc got on Action::: " + result);
        String FileName = dao.CibilFileNameGenerator(fromDate);

        String pathQury = "Select parameter_value from parameter_mst where parameter_key='UPLOAD_PATH'";
        logger.info("query for fetching path::::" + pathQury);
        String Path = CommonFunction.checkNull(ConnectionDAO.singleReturn(pathQury));
        logger.info("Path::::" + Path);

        if (!CommonFunction.checkNull(result).equalsIgnoreCase(""))
        {
          methodForText(Path, FileName, result, response, request);
          request.setAttribute("FileName", FileName);
          request.setAttribute("msg", "S");
          cibilResult = null;
        }
        else
        {
          request.setAttribute("msg", "E");
          cibilResult = "error";
        }

      }
      else
      {
        String result = dao.CibilCorporateFileGenerator(fromDate, userId);
        logger.info("Respone of proc got on Action::: " + result.length());
        String FileName = dao.CibilFileNameGenerator(fromDate);

        String pathQury = "Select parameter_value from parameter_mst where parameter_key='UPLOAD_PATH'";
        logger.info("query for fetching path::::" + pathQury);
        String Path = CommonFunction.checkNull(ConnectionDAO.singleReturn(pathQury));
        logger.info("Path::::" + Path);

        if (!CommonFunction.checkNull(result).equalsIgnoreCase(""))
        {
          methodForText(Path, FileName, result, response, request);
          request.setAttribute("FileName", FileName);
          request.setAttribute("msg", "S");
          cibilResult = null;
        }
        else
        {
          request.setAttribute("msg", "E");
          cibilResult = "error";
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (cibilResult != null)
    {
      return mapping.findForward("error");
    }

    return null;
  }

  public void methodForExport(Object reportName, Map<Object, Object> hashMap, Connection connectDatabase, HttpServletResponse response, JasperPrint jasperPrint, HttpServletRequest request) throws Exception
  {
    String excelReportFileName = reportName + ".tap";
    JRCsvExporter exporter = new JRCsvExporter();
    exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, "\n");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
    exporter.exportReport();
    byte[] output = baos.toByteArray();
    response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
    response.setContentType("tap/plain");
    response.setContentLength(output.length);
    ServletOutputStream ouputStream = response.getOutputStream();
    ouputStream.write(output);
    ouputStream.flush();
    ouputStream.close();
  }

  public void methodForExcel(String reportName, Map<Object, Object> hashMap, Connection connectDatabase, HttpServletResponse response, JasperPrint jasperPrint) throws Exception {
    String excelReportFileName = reportName + ".xls";
    JExcelApiExporter exporterXLS = new JExcelApiExporter();
    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
    response.setHeader("Content-Disposition", "attachment;filename=" + excelReportFileName);
    response.setContentType("application/vnd.ms-excel");
    exporterXLS.exportReport();
  }

  public void methodForText(String FilePath, String fileName, String Result, HttpServletResponse response, HttpServletRequest request) throws Exception
  {
    String path = FilePath + "/" + fileName;
    FileWriter writer = new FileWriter(path, true);
    BufferedWriter bufferedWriter = new BufferedWriter(writer);
    bufferedWriter.write(Result);
    bufferedWriter.close();
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
    UploadDocument.downloadFile(request, response, path);
  }
}