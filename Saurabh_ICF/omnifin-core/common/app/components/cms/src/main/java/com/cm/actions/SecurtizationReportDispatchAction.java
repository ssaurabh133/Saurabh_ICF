package com.cm.actions;

import com.cm.dao.ReportsDAO;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;

public class SecurtizationReportDispatchAction extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(SecurtizationReportDispatchAction.class.getName());

  public ActionForward securtizationReportGenerator(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("Pooja code for Securtization");
    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String userId = null;
    if (userobj != null)
      userId = userobj.getUserId();
    else {
      return mapping.findForward("sessionOut");
    }
    Object sessionId = session.getAttribute("sessionID");
    ServletContext context = getServlet().getServletContext();
    String strFlag = null;
    if (sessionId != null) {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }

    logger.info("strFlag .............. " + strFlag);
    if (!strFlag.equalsIgnoreCase("")) {
      if (strFlag.equalsIgnoreCase("sameUserSession")) {
        context.removeAttribute("msg");
        context.removeAttribute("msg1");
      } else if (strFlag.equalsIgnoreCase("BODCheck")) {
        context.setAttribute("msg", "B");
      }
      return mapping.findForward("logout");
    }

    String fromDate = request.getParameter("fromDate");
    String toDate = request.getParameter("toDate");
    ArrayList instlDateList = new ArrayList();
    ArrayList loanList = new ArrayList();
    ArrayList prinAmtList = new ArrayList();
    ArrayList intAmtList = new ArrayList();
    ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance("REPORTD");
    logger.info("Implementation class: " + dao.getClass());
    instlDateList = dao.fetchInstlDate(fromDate, toDate);
    int instlDateListSize = instlDateList.size();
    loanList = dao.fetchLoanList(fromDate, toDate);
    int loanListSize = loanList.size();

    logger.info("instlDateListSize---->" + instlDateListSize);
    try
    {
      String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UPLOAD_PATH'";
      String teplaterpt = ConnectionDAO.singleReturn(query);
      teplaterpt = teplaterpt + "\\Past_Cash_flow.xls";
      String query1 = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CERSAI_UPLOAD_PATH'";
      String reportRpt = ConnectionDAO.singleReturn(query1);
      reportRpt = reportRpt + "\\Past_Cash_flow.xls";
      FileInputStream myxls = new FileInputStream(teplaterpt);
      HSSFWorkbook PastCash = new HSSFWorkbook(myxls);

      HSSFSheet worksheet = PastCash.getSheetAt(0);
      Row row = worksheet.createRow(2);
      int k = 7;
      for (int i = 0; i < instlDateListSize; i++) {
        row.createCell(k++).setCellValue("Principal Demand");
        row.createCell(k++).setCellValue("Principal Collected");
      }
      Row row1 = worksheet.createRow(3);
      row1.createCell(0).setCellValue("Customer ID");
      row1.createCell(1).setCellValue("Loan ID");
      row1.createCell(2).setCellValue("Status");
      row1.createCell(3).setCellValue("Securitized/Non-Securitized");
      row1.createCell(4).setCellValue("Pool ID");
      row1.createCell(5).setCellValue("Pool Name");
      row1.createCell(6).setCellValue("Agency");
      int j = 7;
      for (int i = 0; i < instlDateListSize; i++) {
        row1.createCell(j).setCellValue(instlDateList.get(i).toString().replace('[', ' ').replace(']', ' ').trim());
        j += 2;
      }

      HSSFSheet worksheet1 = PastCash.getSheetAt(1);
      Row iRow = worksheet1.createRow(2);
      int l = 7;
      for (int i = 0; i < instlDateListSize; i++) {
        iRow.createCell(l++).setCellValue("Interest Demand");
        iRow.createCell(l++).setCellValue("Interest Collected");
      }
      Row iRow1 = worksheet1.createRow(3);
      iRow1.createCell(0).setCellValue("Customer ID");
      iRow1.createCell(1).setCellValue("Loan ID");
      iRow1.createCell(2).setCellValue("Status");
      iRow1.createCell(3).setCellValue("Securitized/Non-Securitized");
      iRow1.createCell(4).setCellValue("Pool ID");
      iRow1.createCell(5).setCellValue("Pool Name");
      iRow1.createCell(6).setCellValue("Agency");
      int m = 7;
      for (int i = 0; i < instlDateListSize; i++) {
        iRow1.createCell(m).setCellValue(instlDateList.get(i).toString().replace('[', ' ').replace(']', ' ').trim());
        m += 2;
      }

      int lastRow = row1.getRowNum();
      int intLastRow = iRow1.getRowNum();
      for (int i = 0; i < loanListSize; i++) {
        String loanId = loanList.get(i).toString().replace('[', ' ').replace(']', ' ').trim();

        prinAmtList = dao.fetchPrinAmtList(loanId, instlDateList);
        ArrayList data = (ArrayList)prinAmtList.get(0);
        int prinAmtListSize = data.size();
        Row pAmtRow = worksheet.createRow(++lastRow);
        for (int n = 0; n < prinAmtListSize; n++) {
          pAmtRow.createCell(n).setCellValue(data.get(n).toString().replace('[', ' ').replace(']', ' ').trim());
        }
      }

      for (int i = 0; i < loanListSize; i++) {
        String loanId = loanList.get(i).toString().replace('[', ' ').replace(']', ' ').trim();
        intAmtList = dao.fetchIntAmtList(loanId, instlDateList);
        ArrayList data1 = (ArrayList)intAmtList.get(0);
        int intAmtListSize = data1.size();
        Row iAmtRow = worksheet1.createRow(++intLastRow);
        for (int n1 = 0; n1 < intAmtListSize; n1++) {
          iAmtRow.createCell(n1).setCellValue(data1.get(n1).toString().replace('[', ' ').replace(']', ' ').trim());
        }
      }

      myxls.close();
      FileOutputStream output_file = new FileOutputStream(new File(reportRpt));
      PastCash.write(output_file);
      output_file.close();
      System.out.println(" is successfully written");

      String fileName = "Past_Cash_flow.xls";
      String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
      fileType = fileType.toLowerCase();
      logger.info("File Type: " + fileType);
      if (fileType.trim().equalsIgnoreCase("xls")) {
        logger.info("File Type in xls: " + fileType);
        response.setContentType("application/vnd.ms-excel");
      }
      else
      {
        logger.info("File Type in default: " + fileType);
        response.setContentType("application/octet-stream");
      }
      response.setHeader("content-disposition", "attachement;filename=" + fileName + "");

      File f = new File(reportRpt);
      System.out.println("downloadPath In DownloadFile Method=====" + reportRpt);
      FileInputStream in = new FileInputStream(f);
      int formDataLength = (int)f.length();
      ServletOutputStream out = response.getOutputStream();
      System.out.println("Check Point formDataLength ..." + formDataLength);

      byte[] outputByte = new byte[formDataLength * 2];

      while (in.read(outputByte, 0, formDataLength) != -1)
      {
        out.write(outputByte, 0, formDataLength);
      }
      in.close();
      out.flush();
      out.close();
      fileName = null;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public ActionForward securtizationRSDataReportGenerator(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("Pooja code for Securtization");
    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String userId = null;
    if (userobj != null)
      userId = userobj.getUserId();
    else {
      return mapping.findForward("sessionOut");
    }
    Object sessionId = session.getAttribute("sessionID");
    ServletContext context = getServlet().getServletContext();
    String strFlag = null;
    if (sessionId != null) {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }

    logger.info("strFlag .............. " + strFlag);
    if (!strFlag.equalsIgnoreCase("")) {
      if (strFlag.equalsIgnoreCase("sameUserSession")) {
        context.removeAttribute("msg");
        context.removeAttribute("msg1");
      } else if (strFlag.equalsIgnoreCase("BODCheck")) {
        context.setAttribute("msg", "B");
      }
      return mapping.findForward("logout");
    }

    String fromDate = request.getParameter("fromDate");
    String toDate = request.getParameter("toDate");
    ArrayList instlDateList = new ArrayList();
    ArrayList loanList = new ArrayList();
    ArrayList amtList = new ArrayList();
    ReportsDAO dao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance("REPORTD");
    logger.info("Implementation class: " + dao.getClass());
    instlDateList = dao.fetchInstlDate(fromDate, toDate);
    int instlDateListSize = instlDateList.size();
    loanList = dao.fetchLoanList(fromDate, toDate);
    int loanListSize = loanList.size();

    logger.info("instlDateListSize---->" + instlDateListSize);
    try
    {
      String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UPLOAD_PATH'";
      String teplaterpt = ConnectionDAO.singleReturn(query);
      teplaterpt = teplaterpt + "\\RSData.xls";
      String query1 = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CERSAI_UPLOAD_PATH'";
      String reportRpt = ConnectionDAO.singleReturn(query1);
      reportRpt = reportRpt + "\\RSData.xls";
      FileInputStream myxls = new FileInputStream(teplaterpt);
      HSSFWorkbook PastCash = new HSSFWorkbook(myxls);

      HSSFSheet worksheet = PastCash.getSheetAt(0);
      Row row = worksheet.createRow(2);
      Row row1 = worksheet.getRow(2);

      row.createCell(0).setCellValue("Customer ID");
      row.createCell(1).setCellValue("Loan ID");
      row.createCell(2).setCellValue("Status");
      row.createCell(3).setCellValue("Securitized/Non-Securitized");
      row.createCell(4).setCellValue("Pool ID");
      row.createCell(5).setCellValue("Pool Name");
      row.createCell(6).setCellValue("Agency");
      int k = 7;
      for (int i = 0; i < instlDateListSize; i++) {
        row.createCell(k++).setCellValue("Date");
        row.createCell(k++).setCellValue("Principal Payment");
        row.createCell(k++).setCellValue("Interest Payment");
      }

      int lastRow = row.getRowNum();
      for (int i = 0; i < loanListSize; i++) {
        String loanId = loanList.get(i).toString().replace('[', ' ').replace(']', ' ').trim();

        amtList = dao.fetchAmtList(loanId, fromDate, toDate);
        ArrayList data = (ArrayList)amtList.get(0);
        int amtListSize = data.size();
        Row amtRow = worksheet.createRow(++lastRow);
        for (int n = 0; n < amtListSize; n++) {
          amtRow.createCell(n).setCellValue(data.get(n).toString().replace('[', ' ').replace(']', ' ').trim());
        }
      }
      myxls.close();
      FileOutputStream output_file = new FileOutputStream(new File(reportRpt));
      PastCash.write(output_file);
      output_file.close();
      System.out.println(" is successfully written");

      String fileName = "RSData.xls";
      String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
      fileType = fileType.toLowerCase();
      logger.info("File Type: " + fileType);
      if (fileType.trim().equalsIgnoreCase("xls")) {
        logger.info("File Type in xls: " + fileType);
        response.setContentType("application/vnd.ms-excel");
      }
      else
      {
        logger.info("File Type in default: " + fileType);
        response.setContentType("application/octet-stream");
      }
      response.setHeader("content-disposition", "attachement;filename=" + fileName + "");

      File f = new File(reportRpt);
      System.out.println("downloadPath In DownloadFile Method=====" + reportRpt);
      FileInputStream in = new FileInputStream(f);
      int formDataLength = (int)f.length();
      ServletOutputStream out = response.getOutputStream();
      System.out.println("Check Point formDataLength ..." + formDataLength);

      byte[] outputByte = new byte[formDataLength * 2];

      while (in.read(outputByte, 0, formDataLength) != -1)
      {
        out.write(outputByte, 0, formDataLength);
      }
      in.close();
      out.flush();
      out.close();
      fileName = null;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}