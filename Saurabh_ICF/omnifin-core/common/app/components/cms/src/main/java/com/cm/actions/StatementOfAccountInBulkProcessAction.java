package com.cm.actions;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.apache.log4j.Logger;
import org.apache.struts.actions.DispatchAction;

public class StatementOfAccountInBulkProcessAction extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(StatementOfAccountInBulkProcessAction.class.getName());
  static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
  static String dbType = resource.getString("lbl.dbType");
  static String dbo = resource.getString("lbl.dbPrefix");
  List<String> fileList = new ArrayList();

  static int count = 0;

  public String generateReport(HttpServletRequest request, HttpServletResponse response, String qry, Map<Object, Object> hashMap, String reportPath, String reportName, InputStream reportStream, String businessDate, String userId, String p_loan_id,String passwordPdf) throws Exception
  {
    Connection connectDatabase = ConnectionDAO.getConnection();
    logger.info("In generateReport :::::::::");
    logger.info("report Name  :  " + reportName + ".jasper");
    logger.info("report Path  :  " + reportPath);
    String reporttype = (String)request.getAttribute("reporttype");
    JasperPrint jasperPrint = null;
    String result = null;
    try
    {
      jasperPrint = JasperFillManager.fillReport(reportStream, hashMap, connectDatabase);
      String path = null;
      String loanId = "";
      String FinalLoanId = "A";
      if (reportName.equalsIgnoreCase("statement_of_account"))
      {
        path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
      }

      if ((reportName.equalsIgnoreCase("PDC_Replinishment_letter")) || (reportName.equalsIgnoreCase("Repricing_Reduction_ROI")) || (reportName.equalsIgnoreCase("Repricing_Inc_ROI")) || (reportName.equalsIgnoreCase("Yearly_Interest_Certificate")) || (reportName.equalsIgnoreCase("bulk_rp_repayment_calculation")) || (reportName.equalsIgnoreCase("Repricing_Letter_for_increased_ROI_interest_rate_policy")))
      {
        path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
      }

      if (reportName.equalsIgnoreCase("BOUNCE_LETTER")) {
        path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
        String q1 = "SELECT TXNID FROM CR_INSTRUMENT_DTL WHERE INSTRUMENT_ID='" + p_loan_id + "' ";
        loanId = ConnectionDAO.singleReturn(q1);
        FinalLoanId = loanId;
      }
      if (reportName.equalsIgnoreCase("Proc_Fees_Invoice"))
      {
        path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'PROCESSING_FEES_REPORT_PATH'";
      }
      String rptPath = ConnectionDAO.singleReturn(path);

      String outFileName = "";
      if (reportName.equalsIgnoreCase("BOUNCE_LETTER"))
        outFileName = rptPath + File.separator + FinalLoanId + "_" + p_loan_id + "_" + userId + "_" + businessDate;
      else {
        outFileName = rptPath + File.separator + p_loan_id + "_" + userId + "_" + businessDate;
      }
      if (reporttype.equals("P"))
      {
        outFileName = outFileName + ".pdf";
        /*changes by shubham*/
    	if(CommonFunction.checkNull(passwordPdf).equalsIgnoreCase("Y")) {
    		String loanIdPass=p_loan_id;
    		if(reportName.equalsIgnoreCase("BOUNCE_LETTER")){
    			loanIdPass=FinalLoanId;
    		}
    		String passwordQuery="select date_format(g.CUSTOMER_DOB,'%d%m%Y') from cr_loan_dtl l join gcd_customer_m g on g.customer_id=l.loan_customer_id where loan_id='"+loanIdPass+"'";
    		String pass=ConnectionDAO.singleReturn(passwordQuery);
    		logger.info("passwordQuery: "+passwordQuery);
    		logger.info("pass: "+pass);
    		String ownerPasswordQuery="select ifnull(primary_phone,'') from cr_loan_dtl l join gcd_customer_m g on g.customer_id=l.loan_customer_id Join com_address_m a on a.BPID=g.customer_id and a.BPTYPE='CS' and a.COMMUNICATION_ADDRESS='Y' where loan_id='"+loanIdPass+"' limit 1";
    		String owner=ConnectionDAO.singleReturn(ownerPasswordQuery);
    		if(CommonFunction.checkNull(owner).equalsIgnoreCase("")){
    			owner=pass;
    		}
    		JRPdfExporter exporter = new JRPdfExporter();
  			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
  			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
  			exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
  			exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
  			exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD,owner);
  			exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD,pass);
  			/*exporter.setParameter(
  		    JRPdfExporterParameter.PERMISSIONS, 
  		    new Integer(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING)
  		    );*/
  		  	exporter.exportReport();
  		  logger.info("report generated for loan: "+loanIdPass);
  		  logger.info("report generated for loan: "+FinalLoanId);
    	}else{
    	JasperExportManager.exportReportToPdfFile(jasperPrint,outFileName);
    	} // end
      }
      else if (reporttype.equals("E"))
      {
        outFileName = outFileName + ".xls";
        JRExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.exportReport();
      }
      else if (reporttype.equals("H"))
      {
        outFileName = outFileName + ".html";
        JRExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.exportReport();
      }

      File archivo = new File(outFileName);
      String[] fileArray = new String[3];
      fileArray[0] = reportName;

      FileInputStream fis = new FileInputStream(outFileName);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];
      int readNum;
      while ((readNum = fis.read(buf)) != -1)
      {
        bos.write(buf, 0, readNum);
      }

      result = "a";
    }
    catch (Exception e)
    {
      e.getMessage();
      e.printStackTrace();
    }
    finally
    {
      ConnectionDAO.closeConnection(connectDatabase, null);
    }
    return result;
  }

  public void methodForZip(String[] sourceFiles, HttpServletResponse response, HttpServletRequest request, String businessDate, String userId, String reportName) throws Exception
  {
    try
    {
      String path = null;

      if (reportName.equalsIgnoreCase("statement_of_account"))
      {
        path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
      }
      if ((reportName.equalsIgnoreCase("PDC_Replinishment_letter")) || (reportName.equalsIgnoreCase("BOUNCE_LETTER")) || (reportName.equalsIgnoreCase("Repricing_Reduction_ROI")) || (reportName.equalsIgnoreCase("Repricing_Inc_ROI")) || (reportName.equalsIgnoreCase("Yearly_Interest_Certificate")) || (reportName.equalsIgnoreCase("Repricing_Letter_for_increased_ROI_interest_rate_policy")))
      {
        path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'ZIP_REPORT_PATH'";
      }
      if (reportName.equalsIgnoreCase("Proc_Fees_Invoice"))
      {
        path = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'PROCESSING_FEES_REPORT_PATH'";
      }

      String reportPath = ConnectionDAO.singleReturn(path);
      String zipFile = null;

      if (reportName.equalsIgnoreCase("statement_of_account"))
      {
        zipFile = "SOA_IN_BULK_" + userId + "_" + businessDate + ".zip";
      }
      if (reportName.equalsIgnoreCase("PDC_Replinishment_letter"))
      {
        zipFile = "PDC_Replinishment_letter_" + userId + "_" + businessDate + ".zip";
      }
      if (reportName.equalsIgnoreCase("Repricing_Inc_ROI")) {
        zipFile = "Repricing_Inc_ROI" + userId + "_" + businessDate + ".zip";
      }
      if (reportName.equalsIgnoreCase("Repricing_Reduction_ROI")) {
        zipFile = "Repricing_Reduction_ROI" + userId + "_" + businessDate + ".zip";
      }
      if (reportName.equalsIgnoreCase("Yearly_Interest_Certificate")) {
        zipFile = "Yearly_Interest_Certificate" + userId + "_" + businessDate + ".zip";
      }
      if (reportName.equalsIgnoreCase("BOUNCE_LETTER"))
      {
        zipFile = "BOUNCE_LETTER_" + userId + "_" + businessDate + ".zip";
      }
      if (reportName.equalsIgnoreCase("Proc_Fees_Invoice"))
      {
        zipFile = "PROC_FEES_BULK" + userId + "_" + businessDate + ".zip";
      }
      if (reportName.equalsIgnoreCase("bulk_rp_repayment_calculation"))
      {
        zipFile = "bulk_rp_repayment_calculation" + userId + "_" + businessDate + ".zip";
      }
      if (reportName.equalsIgnoreCase("Repricing_Letter_for_increased_ROI_interest_rate_policy")) {
        zipFile = "RepricingLetterforIncreasedROI" + userId + "_" + businessDate + ".zip";
      }
      byte[] buffer = new byte[1024];

      response.setContentType("application/zip");
      response.setHeader("Content-Disposition", "attachment; filename=" + zipFile);

      FileOutputStream fos = new FileOutputStream(zipFile);
      ZipOutputStream zos = new ZipOutputStream(fos);
      for (String fileName : sourceFiles)
      {
        File reportFile = new File(fileName);
        addToZipFile(reportFile, fileName, zos);
      }
      zos.close();
      fos.close();
      File zipFileName = new File(zipFile);
      FileInputStream fileIn = new FileInputStream(zipFileName);
      ServletOutputStream out = response.getOutputStream();

      byte[] outputByte = new byte[1024];

      while (fileIn.read(outputByte, 0, 1024) != -1)
      {
        out.write(outputByte, 0, 1024);
      }

      fileIn.close();
      out.flush();
      out.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void addToZipFile(File file, String path, ZipOutputStream zos)
    throws FileNotFoundException, IOException
  {
    logger.info("Writing '" + path + "' to zip file");

    FileInputStream fis = new FileInputStream(file);
    ZipEntry zipEntry = new ZipEntry(file.getName());
    zos.putNextEntry(zipEntry);

    byte[] bytes = new byte[1024];
    int length;
    while ((length = fis.read(bytes)) >= 0) {
      zos.write(bytes, 0, length);
    }

    zos.closeEntry();
    fis.close();
  }

  public String[] getLoanList(String loanId, String branchId, String productId, String fromDate, String toDate, String reportName, String loanFrom, String loanTo)
  {
    ArrayList loanList = new ArrayList();
    StringBuffer query = new StringBuffer();
    String[] loanArr = null;
    loanId = loanId.replace("|", ",");
    branchId = branchId.replace(",", "','");

    if ((!reportName.equals("Proc_Fees_Invoice")) && (!CommonFunction.checkNull(productId).trim().equalsIgnoreCase("")))
    {
      productId = productId.replace("|", "','");
    }

    logger.info("productId in getLoanList ...................." + productId);
    try
    {
      if ((!CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")) && (!CommonFunction.checkNull(loanTo).equalsIgnoreCase(""))) {
        String loanrangeQuery = "Select loan_id from cr_loan_dtl where loan_no='" + loanFrom + "' ";
        loanFrom = ConnectionDAO.singleReturn(loanrangeQuery);
        String loanTorangeQuery = "Select loan_id from cr_loan_dtl where loan_no='" + loanTo + "' ";
        loanTo = ConnectionDAO.singleReturn(loanTorangeQuery);
      }
      if (CommonFunction.checkNull(reportName).equalsIgnoreCase("PDC_Replinishment_letter"))
      {
        query.append("SELECT DISTINCT a.LOAN_ID FROM CR_LOAN_DTL a  join cr_pdc_instrument_dtl b on b.pdc_loan_id=a.loan_id and PDC_PURPOSE='INSTALLMENT'  AND PDC_STATUS  IN ('A','D') AND PDC_INSTRUMENT_MODE IN ('Q','E','H') WHERE 'ALL'='ALL'  and a.rec_status='A' ");
        query.append(" AND  (select max(PDC_INSTRUMENT_DATE) from cr_pdc_instrument_dtl where pdc_loan_id=b.pdc_loan_id and PDC_PURPOSE='INSTALLMENT'  AND PDC_STATUS  IN ('A','D') AND PDC_INSTRUMENT_MODE IN ('Q','E','H')) <='" + fromDate + "' ");
        query.append(" and (select PDC_INSTRUMENT_MODE from cr_pdc_instrument_dtl  where pdc_loan_id=b.pdc_loan_id and PDC_INSTRUMENT_ID = ");
        query.append(" (select max(PDC_INSTRUMENT_ID) from cr_pdc_instrument_dtl ");
        query.append("\t where pdc_loan_id=b.pdc_loan_id and PDC_PURPOSE='INSTALLMENT'  AND PDC_STATUS  IN ('A','D') AND PDC_INSTRUMENT_MODE IN ('Q','E','H'))) ");
        query.append("\t not in ('E','H') ");
        query.append(" and (select max(Instl_no) from cr_repaysch_dtl where loan_id=b.pdc_loan_id)<>(select max(PDC_INSTL_NO) from cr_pdc_instrument_dtl where pdc_loan_id=b.pdc_loan_id and PDC_PURPOSE='INSTALLMENT'  AND PDC_STATUS  IN ('A','D') AND PDC_INSTRUMENT_MODE IN ('Q','E','H')) ");
        if ((!CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")) && (!CommonFunction.checkNull(loanTo).equalsIgnoreCase(""))) {
          query.append(" AND a.LOAN_ID>=" + loanFrom + " and a.LOAN_ID<=" + loanTo + " ");
        }
        if ((!CommonFunction.checkNull(loanId).equalsIgnoreCase("")) && (CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")))
          query.append("AND a.LOAN_ID IN (" + loanId.trim() + ") ");
        if (!CommonFunction.checkNull(branchId).equalsIgnoreCase("")) {
          query.append("AND a.LOAN_BRANCH IN ('" + branchId.trim() + "') ");
        }
        if (!productId.trim().equalsIgnoreCase("ALL"))
          query.append("AND a.LOAN_PRODUCT IN ('" + productId.trim() + "') ");
      }
      else if ((reportName.equalsIgnoreCase("Repricing_Inc_ROI")) || (reportName.equalsIgnoreCase("Repricing_Reduction_ROI")))
      {
        query.append("SELECT DISTINCT a.LOAN_ID FROM CR_LOAN_DTL a  join cr_resch_dtl CRD on CRD.LOAN_ID=a.LOAN_ID and CRD.resch_type in('R','F') WHERE 'ALL'='ALL'  and a.rec_status='A' ");
        if (reportName.equalsIgnoreCase("Repricing_Reduction_ROI"))
          query.append(" AND  OLD_RATE>NEW_RATE  and CRD.rec_status='A' and date_format(CRD.AUTHOR_DATE,'%Y-%m-%d')>='" + fromDate + "' and date_format(CRD.AUTHOR_DATE,'%Y-%m-%d')<='" + toDate + "'  and CRD.old_rate > CRD.new_rate ");
        if (reportName.equalsIgnoreCase("Repricing_Inc_ROI"))
          query.append(" AND  OLD_RATE<=NEW_RATE   and CRD.rec_status='A' and date_format(CRD.AUTHOR_DATE,'%Y-%m-%d')>='" + fromDate + "' and date_format(CRD.AUTHOR_DATE,'%Y-%m-%d')<='" + toDate + "' and CRD.old_rate < CRD.new_rate ");
        if ((!CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")) && (!CommonFunction.checkNull(loanTo).equalsIgnoreCase(""))) {
          query.append(" AND a.LOAN_ID>=" + loanFrom + " and a.LOAN_ID<=" + loanTo + " ");
        }
        if ((!CommonFunction.checkNull(loanId).equalsIgnoreCase("")) && (CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")))
          query.append("AND a.LOAN_ID IN (" + loanId.trim() + ") ");
        if (!CommonFunction.checkNull(branchId).equalsIgnoreCase("")) {
          query.append("AND a.LOAN_BRANCH IN ('" + branchId.trim() + "') ");
        }
        if (!productId.trim().equalsIgnoreCase("ALL"))
          query.append("AND a.LOAN_PRODUCT IN ('" + productId.trim() + "') ");
      }
      else if (reportName.equalsIgnoreCase("Repricing_Letter_for_increased_ROI_interest_rate_policy")) {
        query.append("select distinct loan_id from cr_resch_dtl where rec_status='A' and resch_date=str_to_date('" + fromDate + "','%d-%m-%Y') and (More_than_2_continuous_bounce_in_April_to_Sept='Y' || More_than_2_continuous_bounce_in_Oct_to_March='Y' || DPD_IN_30_TO_90='Y' || DPD_90_ABOVE='Y')");
      }
      else if (reportName.equalsIgnoreCase("BOUNCE_LETTER"))
      {
        query.append("select distinct CID.INSTRUMENT_ID,a.loan_id,IU.INSTRUMENT_DATE FROM CR_LOAN_DTL a join cr_instrument_dtl CID on a.LOAN_ID=CID.TXNID  and CID.TXN_TYPE='LIM' ");
        query.append("  join cr_instrument_update_dtl IU on CID.INSTRUMENT_ID=IU.INSTRUMENT_ID and IU.CURRENT_STATUS = 'B' and IU.NEXT_STATUS = 'X' and IU.INSTRUMENT_DATE>='" + fromDate + "' and IU.INSTRUMENT_DATE<='" + toDate + "' ");
        query.append("join cr_pmnt_dtl_hst pmnt on pmnt.INSTRUMENT_ID=CID.INSTRUMENT_ID ");
        query.append("join cr_txnadvice_dtl txn on txn.TXNADVICE_ID=pmnt.TXNADVICEID and CHARGE_CODE_ID='7'   WHERE 'ALL'='ALL'  and a.rec_status='A' ");

        if ((!CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")) && (!CommonFunction.checkNull(loanTo).equalsIgnoreCase(""))) {
          query.append(" AND a.LOAN_ID>=" + loanFrom + " and a.LOAN_ID<=" + loanTo + " ");
        }
        if ((!CommonFunction.checkNull(loanId).equalsIgnoreCase("")) && (CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")))
          query.append("AND a.LOAN_ID IN (" + loanId.trim() + ") ");
        if (!CommonFunction.checkNull(branchId).equalsIgnoreCase("")) {
          query.append("AND a.LOAN_BRANCH IN ('" + branchId.trim() + "') ");
        }
        if (!productId.trim().equalsIgnoreCase("ALL"))
          query.append("AND a.LOAN_PRODUCT IN ('" + productId.trim() + "') ");
      }
      else if (reportName.equalsIgnoreCase("Yearly_Interest_Certificate"))
      {
        query.append("SELECT DISTINCT a.LOAN_ID FROM CR_LOAN_DTL a join cr_repaysch_dtl b on a.loan_id=b.loan_id and instl_date>=str_to_date('" + fromDate + "','%d-%m-%Y') and instl_date<=str_to_date('" + toDate + "','%d-%m-%Y')  WHERE 'ALL'='ALL'  and a.rec_status in ('A','C')  ");

        if ((!CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")) && (!CommonFunction.checkNull(loanTo).equalsIgnoreCase(""))) {
          query.append(" AND a.LOAN_ID>=" + loanFrom + " and a.LOAN_ID<=" + loanTo + " ");
        }
        if ((!CommonFunction.checkNull(loanId).equalsIgnoreCase("")) && (CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")))
          query.append("AND a.LOAN_ID IN (" + loanId.trim() + ") ");
        if (!CommonFunction.checkNull(branchId).equalsIgnoreCase("")) {
          query.append("AND a.LOAN_BRANCH IN ('" + branchId.trim() + "') ");
        }
        if (!productId.trim().equalsIgnoreCase("ALL"))
          query.append("AND a.LOAN_PRODUCT IN ('" + productId.trim() + "') ");
        logger.info("Query for " + reportName + " : " + query.toString());
      }
      else if (reportName.equalsIgnoreCase("bulk_rp_repayment_calculation"))
      {
        if (loanId.equalsIgnoreCase("D")) {
          logger.info("Query for DISBURSERD CASE");

          query.append("SELECT distinct C.LOAN_ID,LOAN_NO FROM cr_loan_dtl c join cr_loan_disbursal_dtl cldd on cldd.LOAN_ID=c.LOAN_ID where cldd.REC_STATUS='A' and c.REC_STATUS='A' and cldd.disbursal_flag='F' ");

          if ((!CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")) && (!CommonFunction.checkNull(loanTo).equalsIgnoreCase(""))) {
            query.append(" AND cldd.LOAN_ID>=" + loanFrom + " and  cldd.LOAN_ID<=" + loanTo + " ");
          }
          if ((!CommonFunction.checkNull(fromDate).equalsIgnoreCase("")) && (!CommonFunction.checkNull(toDate).equalsIgnoreCase("")))
            query.append("AND cldd.disbursal_date BETWEEN str_to_date('" + fromDate + "','%Y-%m-%d') and str_to_date('" + toDate + "','%Y-%m-%d')");
          if (!CommonFunction.checkNull(branchId).equalsIgnoreCase(""))
            query.append("AND c.LOAN_BRANCH IN ('" + branchId.trim() + "') ");
          if (!productId.trim().equalsIgnoreCase("ALL"))
            query.append("AND c.LOAN_PRODUCT IN ('" + productId.trim() + "') ");
        }
        if (loanId.equalsIgnoreCase("RS")) {
          logger.info("Query for RESCHDULED CASE");
					 query.append("SELECT distinct C.LOAN_ID,LOAN_NO FROM cr_loan_dtl c join cr_resch_dtl crd on crd.LOAN_ID=c.LOAN_ID where crd.resch_type in('R','D') and crd.REC_STATUS='A' and c.REC_STATUS='A' ");

          if ((!CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")) && (!CommonFunction.checkNull(loanTo).equalsIgnoreCase(""))) {
            query.append(" AND crd.LOAN_ID>=" + loanFrom + " and  crd.LOAN_ID<=" + loanTo + " ");
          }
          if ((!CommonFunction.checkNull(fromDate).equalsIgnoreCase("")) && (!CommonFunction.checkNull(toDate).equalsIgnoreCase("")))
            query.append("AND crd.RESCH_DATE BETWEEN str_to_date('" + fromDate + "','%Y-%m-%d') and str_to_date('" + toDate + "','%Y-%m-%d')");
          if (!CommonFunction.checkNull(branchId).equalsIgnoreCase(""))
            query.append("AND c.LOAN_BRANCH IN ('" + branchId.trim() + "') ");
          if (!productId.trim().equalsIgnoreCase("ALL"))
            query.append("AND c.LOAN_PRODUCT IN ('" + productId.trim() + "') ");
        }
      }
      else if(reportName.equalsIgnoreCase("statement_of_account")){
			
			query.append("SELECT DISTINCT LOAN_ID FROM CR_LOAN_DTL  WHERE 'ALL'='ALL' and  ifnull(REC_STATUS,'P')<>'X' ");
			
			if(!CommonFunction.checkNull(loanFrom).equalsIgnoreCase("") && !CommonFunction.checkNull(loanTo).equalsIgnoreCase("") ){
				query.append(" AND LOAN_ID>="+loanFrom+" and LOAN_ID<="+loanTo+" ");
			}
			if (!(CommonFunction.checkNull(loanId).equalsIgnoreCase("")) && CommonFunction.checkNull(loanFrom).equalsIgnoreCase(""))
				query.append("AND LOAN_ID IN ("+loanId.trim()+") ");
			if (!(CommonFunction.checkNull(branchId).equalsIgnoreCase("")))
				query.append("AND LOAN_BRANCH IN ('"+branchId.trim()+"') ");
			
			
			
		}
      else
      {
        query.append("SELECT DISTINCT a.LOAN_ID FROM CR_LOAN_DTL a join cr_repaysch_dtl b on a.loan_id=b.loan_id and instl_date>=str_to_date('" + fromDate + "','%d-%m-%Y') and instl_date<=str_to_date('" + toDate + "','%d-%m-%Y')  WHERE 'ALL'='ALL'  and a.rec_status='A' ");

        if ((!CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")) && (!CommonFunction.checkNull(loanTo).equalsIgnoreCase(""))) {
          query.append(" AND a.LOAN_ID>=" + loanFrom + " and a.LOAN_ID<=" + loanTo + " ");
        }
        if ((!CommonFunction.checkNull(loanId).equalsIgnoreCase("")) && (CommonFunction.checkNull(loanFrom).equalsIgnoreCase("")))
          query.append("AND a.LOAN_ID IN (" + loanId.trim() + ") ");
        if (!CommonFunction.checkNull(branchId).equalsIgnoreCase("")) {
          query.append("AND a.LOAN_BRANCH IN ('" + branchId.trim() + "') ");
        }
        if (!productId.trim().equalsIgnoreCase("ALL")) {
          query.append("AND a.LOAN_PRODUCT IN ('" + productId.trim() + "') ");
        }
      }

      logger.info("query....... " + query);
      loanList = ConnectionDAO.sqlSelect(query.toString());
      logger.info("loanList  :: " + loanList);
      int size = loanList.size();
      logger.info("size of loanList  :: " + size);
      if (size > 0)
      {
        loanArr = new String[size];
        for (int i = 0; i < size; i++)
        {
          ArrayList data = (ArrayList)loanList.get(i);
          if (data.size() > 0)
          {
            loanArr[i] = ((String)data.get(0));
          }
        }

      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return loanArr;
  }
}