package com.cm.daoImplMSSQL;

import com.cm.actionform.ReportsForm;
import com.cm.dao.ReportsDAO;
import com.cm.vo.ChequeAllocationVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.PrepStmtObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MSSQLReportsDAOImpl
  implements ReportsDAO
{
  private static final Logger logger = Logger.getLogger(MSSQLReportsDAOImpl.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
  String dateFormatWithTime = this.resource.getString("lbl.dateWithTimeInDao");
  String dateFormat = this.resource.getString("lbl.dateInDao");

  String dbType = this.resource.getString("lbl.dbType");
  String dbo = this.resource.getString("lbl.dbPrefix");

  DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
  int no = Integer.parseInt(this.resource.getString("msg.pageSizeForMaster"));

  public ArrayList getReportFormat()
  {
    ArrayList list = new ArrayList();
    ReportsForm reportformat = null;
    try {
      logger.info("In getReportFormat..........................DAOImpl");

      StringBuilder query = new StringBuilder();
      query.append("SELECT VALUE,DESCRIPTION FROM generic_master WHERE GENERIC_KEY='REPORT_FORMAT' and rec_status ='A' order by case when value=");
      query.append("ISNULL((select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'DEFAULT_REPORT_FORMAT'),'H') then 0 else 1 end , VALUE desc ");
      logger.info(new StringBuilder().append("In....getReportFormat").append(query.toString()).toString());
      ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      logger.info(new StringBuilder().append("getReportFormat()----> ").append(formatlist.size()).toString());
      for (int i = 0; i < formatlist.size(); i++)
      {
        ArrayList data = (ArrayList)formatlist.get(i);
        if (data.size() > 0)
        {
          reportformat = new ReportsForm();
          reportformat.setReportformat(data.get(0).toString());
          reportformat.setReportformatid(data.get(1).toString());
          list.add(reportformat);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList getReportFormat_NEW()
  {
    ArrayList list = new ArrayList();
    ReportsForm reportformat = null;
    try {
      logger.info("In getReportFormat_NEW..........................DAOImpl");

      StringBuilder query = new StringBuilder();
      query.append("SELECT VALUE,DESCRIPTION FROM generic_master WHERE GENERIC_KEY='REPORT_FORMAT_NEW' and rec_status ='A' order by case when value=");
      query.append("ISNULL((select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'DEFAULT_REPORT_FORMAT'),'H') then 0 else 1 end , VALUE desc ");
      logger.info(new StringBuilder().append("In....getReportFormat_NEW").append(query.toString()).toString());
      ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      logger.info(new StringBuilder().append("getReportFormat_NEW()----> ").append(formatlist.size()).toString());
      for (int i = 0; i < formatlist.size(); i++)
      {
        ArrayList data = (ArrayList)formatlist.get(i);
        if (data.size() > 0)
        {
          reportformat = new ReportsForm();
          reportformat.setReportformat(data.get(0).toString());
          reportformat.setReportformatid(data.get(1).toString());
          list.add(reportformat);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList getCompanyAddress(int company_ID) {
    ArrayList list = new ArrayList();
    ReportsForm companyadd = null;
    ArrayList addresslist = new ArrayList();
    try {
      if (this.dbType.equalsIgnoreCase("MSSQL")) {
        StringBuilder query1 = new StringBuilder();
        query1.append("select COMPANY_MSG,concat(isnull(ADDRESS_LINE1,''),' ',isnull(ADDRESS_LINE2,''),' ',isnull(ADDRESS_LINE3,''))addressLine1,");
        query1.append("iif(CITY is null,' ',concat(CITY,' ')),iif(PINCODE is null,' ',concat('-',PINCODE)) as PINCODE,iif(PHONE_NO is null,' ',");
        query1.append("concat('Phone No-:',PHONE_NO))as PHONE_NO,iif(FAX is null,' ',concat('Fax: ',FAX))as FAX,");
        query1.append("iif(EMAIL_ID is null,' ',concat('Email :',EMAIL_ID))as EMAIL_ID,iif(dbo.trim(isnull(WEBSITE,''))='',' ',");
        query1.append(new StringBuilder().append("concat('Website:',WEBSITE))as WEBSITE from com_company_m where company_id='").append(company_ID).append("'").toString());
        logger.info(new StringBuilder().append("query....for MSSQL........ ").append(query1.toString()).toString());
        addresslist = ConnectionReportDumpsDAO.sqlSelect(query1.toString());
      } else {
        logger.info("In getCompanyAddress..........................DAOImpl");
        StringBuilder query = new StringBuilder();
        query.append("select COMPANY_MSG,concat(isnull(ADDRESS_LINE1,''),' ',isnull(ADDRESS_LINE2,''),' ',isnull(ADDRESS_LINE3,''))addressLine1,iif(CITY is null,' ',concat(CITY,' ')),iif(PINCODE is null,' ',concat('-',PINCODE)) as PINCODE,iif(PHONE_NO is null,' ',concat('Phone No-:',PHONE_NO))as PHONE_NO,iif(FAX is null,' ',concat('Fax: ',FAX))as FAX,iif(EMAIL_ID is null,' ',concat('Email :',EMAIL_ID))as EMAIL_ID,iif(dbo.TRIM(isnull(WEBSITE,''))='',' ',concat('Website:',WEBSITE))as WEBSITE from com_company_m where company_id='1'");
        logger.info(new StringBuilder().append("query....... ").append(query.toString()).toString());
        addresslist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      }

      logger.info(new StringBuilder().append("getCompanyAddress()----> ").append(addresslist.size()).toString());
      if (addresslist.size() > 0)
      {
        for (int i = 0; i < addresslist.size(); i++)
        {
          ArrayList data = (ArrayList)addresslist.get(i);
          if (data.size() > 0)
          {
            companyadd = new ReportsForm();
            companyadd.setMsg(CommonFunction.checkNull(data.get(0)).trim());
            companyadd.setAddress1(CommonFunction.checkNull(data.get(1)).trim());
            companyadd.setCity(CommonFunction.checkNull(data.get(2)).trim());
            companyadd.setPincode(CommonFunction.checkNull(data.get(3)).trim());
            companyadd.setPhone(CommonFunction.checkNull(data.get(4)).trim());
            companyadd.setFax(CommonFunction.checkNull(data.get(5)).trim());
            companyadd.setEmail(CommonFunction.checkNull(data.get(6)).trim());
            companyadd.setWebsite(CommonFunction.checkNull(data.get(7)).trim());
            list.add(companyadd);
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public boolean checkNoOfLoan(String fromDateDB, String toDateDB)
  {
    boolean status = false;
    logger.info(new StringBuilder().append("In checkNoOfLoan() fromLoanID  :  ").append(fromDateDB).append("     toLoanID :  ").append(toDateDB).toString());
    StringBuilder query = new StringBuilder();
    query.append("SELECT COUNT(DISTINCT a.LOAN_ID) FROM cr_loan_dtl a JOIN gcd_customer_m c on(c.CUSTOMER_ID=a.LOAN_CUSTOMER_ID)");
    query.append("JOIN cr_deal_loan_dtl e ON(e.DEAL_ID=a.LOAN_DEAL_ID)WHERE ");
    query.append(this.dbo);
    query.append(new StringBuilder().append("date(a.LOAN_APPROVAL_DATE) >='").append(fromDateDB).append("' ").toString());
    query.append("AND ");
    query.append(this.dbo);
    query.append(new StringBuilder().append("date(a.LOAN_APPROVAL_DATE) <='").append(toDateDB).append("' and a.REC_STATUS in('A','C') and a.LOAN_ID not in(select LOAN_ID from closed_loans)").toString());
    logger.info(new StringBuilder().append("In checkNoOfLoan() Query for chek no of loan is selected :  ").append(query.toString()).toString());
    StringBuilder query2 = new StringBuilder();
    query2.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CIBIL_REPORT_SIZE'");
    logger.info(new StringBuilder().append("In checkNoOfLoan() Query for getting maximum loan limit  :  ").append(query2.toString()).toString());
    try
    {
      int noOfLoan = Integer.parseInt(ConnectionReportDumpsDAO.singleReturn(query.toString()));
      int loanLimit = Integer.parseInt(ConnectionReportDumpsDAO.singleReturn(query2.toString()));
      logger.info(new StringBuilder().append("In checkNoOfLoan() noOfLoan  :  ").append(noOfLoan).toString());
      logger.info(new StringBuilder().append("In checkNoOfLoan() loanLimit  :  ").append(loanLimit).toString());
      if (loanLimit >= noOfLoan)
        status = true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      query = null;
      query2 = null;
    }
    return status;
  }

  public int getMaxLoanLimit()
  {
    int loanLimit = 0;
    StringBuilder query = new StringBuilder();
    query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CIBIL_REPORT_SIZE'");
    logger.info(new StringBuilder().append("In checkNoOfLoan() Query for getting maximum loan limit  :  ").append(query.toString()).toString());
    try
    {
      loanLimit = Integer.parseInt(ConnectionReportDumpsDAO.singleReturn(query.toString()));
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      query = null;
    }return loanLimit;
  }

  public ArrayList<Object> getCibilReport(String currantDate, String businessDate, String fromDateDB, String toDateDB, String cutOffDate, String userId, String reportName)
  {
    logger.info("In getCibilReport()");
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    ArrayList result = new ArrayList();
    String s1 = "";
    String s2 = "";
    String error = "";
    try
    {
      if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase("cibilReport"))
      {
        in.add(currantDate);
        in.add(businessDate);
        in.add(fromDateDB);
        in.add(toDateDB);
        if (CommonFunction.checkNull(cutOffDate).trim().equalsIgnoreCase(""))
          in.add(CommonFunction.changeFormat("0000-00-00"));
        else
          in.add(CommonFunction.changeFormat(cutOffDate));
        out.add(s1);
        out.add(s2);
        logger.info(new StringBuilder().append("CIBIL_REPORT_GENERATOR (").append(in.toString()).append(",").append(out.toString()).append(")").toString());
        outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("CIBIL_REPORT_GENERATOR", in, out);
      }
      else
      {
        if (CommonFunction.checkNull(cutOffDate).trim().equalsIgnoreCase(""))
          in.add(toDateDB);
        else
          in.add(CommonFunction.changeFormat(cutOffDate));
        in.add(fromDateDB);
        in.add(toDateDB);
        in.add(CommonFunction.checkNull(userId).trim());
        out.add(s1);
        out.add(s2);
        logger.info(new StringBuilder().append("CIBIL_GUARANTOR_REPORT_GENERATOR (").append(in.toString()).append(",").append(out.toString()).append(")").toString());
        outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("CIBIL_GUARANTOR_REPORT_GENERATOR", in, out);
      }
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
      logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
      result.add(error);
      result.add(s1);
      result.add(s2);
    }
    catch (Exception e)
    {
      error = "error";
      result.add(error);
      result.add(s1);
      result.add(s2);
      e.printStackTrace();
    }
    finally
    {
      in = null;
      out = null;
      outMessages = null;
      s1 = null;
      s2 = null;
      error = null;
    }
    return result;
  }

  public ArrayList<ReportsForm> getReportName(int role_id, int function_Id, String module_id)
  {
    ArrayList reportlist = new ArrayList();
    ReportsForm reportName = null;
    try {
      logger.info("In getReportName..........................DAOImpl");
      String query = new StringBuilder().append("select cr_report_m.REPORT_NAME,reports.REPORT_DESC from  cr_report_m  join reports on reports.REPORT_NAME=cr_report_m.REPORT_NAME  where cr_report_m.ROLE_ID='").append(role_id).append("' and reports.MODULE_ID='").append(module_id).append("' and reports.FUNCTION_ID='").append(function_Id).append("'   and reports.REC_STATUS='A' ORDER BY reports.REPORT_DESC").toString();

      logger.info(new StringBuilder().append("query....... ").append(query).toString());
      ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      logger.info(new StringBuilder().append("getReportName()----> ").append(formatlist.size()).toString());
      for (int i = 0; i < formatlist.size(); i++)
      {
        ArrayList data = (ArrayList)formatlist.get(i);
        if (data.size() > 0)
        {
          reportName = new ReportsForm();
          reportName.setReportName(CommonFunction.checkNull(data.get(0)));
          reportName.setReportNameId(CommonFunction.checkNull(data.get(1)));
          reportlist.add(reportName);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return reportlist;
  }

  public ArrayList<ReportsForm> getReportNameForCp(int role_id, int function_Id, String module_id) {
    ArrayList reportlist = new ArrayList();
    ReportsForm reportName = null;
    try {
      logger.info("In getReportNameForCp..........................DAOImpl");
      String query = new StringBuilder().append("select cr_report_m.REPORT_NAME,reports.REPORT_DESC from  cr_report_m  join reports on reports.REPORT_NAME=cr_report_m.REPORT_NAME  where cr_report_m.ROLE_ID='").append(role_id).append("' and reports.MODULE_ID='").append(module_id).append("' and reports.FUNCTION_ID='").append(function_Id).append("'   and reports.REC_STATUS='A' ORDER BY reports.REPORT_DESC").toString();

      logger.info(new StringBuilder().append("query....... ").append(query).toString());
      ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      logger.info(new StringBuilder().append("getReportNameForCp()----> ").append(formatlist.size()).toString());
      for (int i = 0; i < formatlist.size(); i++)
      {
        ArrayList data = (ArrayList)formatlist.get(i);
        if (data.size() > 0)
        {
          reportName = new ReportsForm();
          reportName.setReportName(data.get(0).toString());
          reportName.setReportNameId(data.get(1).toString());
          reportlist.add(reportName);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return reportlist;
  }

  public String getCustomerName(String frdLoanID)
  {
    logger.info("In getCustomerName.");
    String customerName = "";
    try
    {
      String query = new StringBuilder().append("select c.CUSTOMER_NAME from cr_loan_dtl a left join gcd_customer_m c on(c.CUSTOMER_ID=a.LOAN_CUSTOMER_ID)where a.loan_id=").append(frdLoanID.trim()).toString();
      logger.info(new StringBuilder().append("Query to get customer name  :   ").append(query).toString());
      customerName = ConnectionReportDumpsDAO.singleReturn(query);
    }
    catch (Exception e) {
      e.printStackTrace();
    }return customerName;
  }

  public String getCoApplicantName(String p_deal)
  {
    String p_coApplicant = "";
    ArrayList header = new ArrayList();
    StringBuffer bufInsSql = new StringBuffer();
    bufInsSql.append(new StringBuilder().append(" select cr_deal_customer_m.CUSTOMER_NAME  from cr_deal_dtl left outer join cr_deal_customer_role on (cr_deal_dtl.DEAL_ID=cr_deal_customer_role.DEAL_ID)left outer join cr_deal_customer_m on (cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID) WHERE DEAL_CUSTOMER_ROLE_TYPE='COAPPL' and cr_deal_dtl.DEAL_ID='").append(p_deal).append("'").toString());

    logger.info(new StringBuilder().append("query to get Co-applicant List  :  ").append(bufInsSql.toString()).toString());
    try
    {
      header = ConnectionReportDumpsDAO.sqlSelect(bufInsSql.toString());
      int size = header.size();
      logger.info(new StringBuilder().append("List size  :  ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        ArrayList header1 = (ArrayList)header.get(i);
        p_coApplicant = new StringBuilder().append(" ").append(p_coApplicant).append(" ").append(i + 1).append(".").append(header1.get(0)).toString();
      }
      logger.info(new StringBuilder().append("p_coApplicant  :  ").append(p_coApplicant).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return p_coApplicant;
  }

  public String getGaurantorName(String p_deal)
  {
    String p_gaurantor = "";
    ArrayList header = new ArrayList();
    StringBuffer bufInsSql = new StringBuffer();
    bufInsSql.append(new StringBuilder().append(" select cr_deal_customer_m.CUSTOMER_NAME  from cr_deal_dtl left outer join cr_deal_customer_role on (cr_deal_dtl.DEAL_ID=cr_deal_customer_role.DEAL_ID)left outer join cr_deal_customer_m on (cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID) WHERE DEAL_CUSTOMER_ROLE_TYPE='GUARANTOR' and cr_deal_dtl.DEAL_ID='").append(p_deal).append("'").toString());

    logger.info(new StringBuilder().append("query to get Gaurantor List  :  ").append(bufInsSql.toString()).toString());
    try
    {
      header = ConnectionReportDumpsDAO.sqlSelect(bufInsSql.toString());
      int size = header.size();
      logger.info(new StringBuilder().append("List size  :  ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        ArrayList header1 = (ArrayList)header.get(i);
        p_gaurantor = new StringBuilder().append(" ").append(p_gaurantor).append(" ").append(i + 1).append(".").append(header1.get(0)).toString();
      }
      logger.info(new StringBuilder().append("p_gaurantor  :  ").append(p_gaurantor).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return p_gaurantor;
  }

  public String getStartingDate(String businessDate)
  {
    String stDate = "";
    StringBuffer bufInsSql = new StringBuffer();
    bufInsSql.append(" SELECT ");
    bufInsSql.append(this.dbo);
    bufInsSql.append(new StringBuilder().append("DATE_FORMAT(DATE_ADD(LAST_DAY(DATE_ADD('").append(businessDate.trim()).append("',INTERVAL -1 MONTH)),INTERVAL 1 DAY),'%d-%m-%Y')").toString());
    logger.info(new StringBuilder().append("query to getStartingDate  :  ").append(bufInsSql.toString()).toString());
    try
    {
      stDate = ConnectionReportDumpsDAO.singleReturn(bufInsSql.toString());
      logger.info(new StringBuilder().append("Starting Date of this Month : ").append(stDate).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return stDate;
  }

  public String getEndDate() {
    String edDate = "";
    StringBuffer bufInsSql = new StringBuffer();
    bufInsSql.append(" SELECT EOMONTH((select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BUSINESS_DATE'))");
    logger.info(new StringBuilder().append("query to getEndDate  :  ").append(bufInsSql.toString()).toString());
    try
    {
      edDate = ConnectionReportDumpsDAO.singleReturn(bufInsSql.toString());
      logger.info(new StringBuilder().append("EndDate Date of this Month : ").append(edDate).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return edDate;
  }

  public String getLastMonthDate(String date)
  {
    String edDate = "";
    StringBuffer bufInsSql = new StringBuffer();
    bufInsSql.append(new StringBuilder().append("select dbo.date_format(DATEADD(MONTH, -1,dbo.str_to_date('").append(date).append("','%d-%m-%Y')),'%d-%m-%Y')").toString());
    logger.info(new StringBuilder().append("query to getEndDate  :  ").append(bufInsSql.toString()).toString());
    try
    {
      edDate = ConnectionReportDumpsDAO.singleReturn(bufInsSql.toString());
      logger.info(new StringBuilder().append("EndDate Date of this Month : ").append(edDate).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return edDate;
  }

  public String getAppServerPath()
  {
    String path = "";
    StringBuffer bufInsSql = new StringBuffer();
    bufInsSql.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'APPLICATION_PATH'");
    logger.info(new StringBuilder().append("query to getAppServerPath  :  ").append(bufInsSql.toString()).toString());
    try
    {
      path = ConnectionReportDumpsDAO.singleReturn(bufInsSql.toString());
      logger.info(new StringBuilder().append("Application Server Path : ").append(path).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return path;
  }

  public ArrayList<ReportsForm> getSponsorCode()
  {
    logger.info("In getSponsorCode()");
    ArrayList reportlist = new ArrayList();
    ReportsForm reportName = null;
    try
    {
      String query = "select value,description from generic_master where GENERIC_KEY='SPONSOR_BANK_CODE' and rec_status='A'";
      logger.info(new StringBuilder().append("query....... ").append(query).toString());
      ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      for (int i = 0; i < formatlist.size(); i++)
      {
        ArrayList data = (ArrayList)formatlist.get(i);
        reportName = new ReportsForm();
        reportName.setSponsorCode(data.get(0).toString());
        reportName.setSponsorDesc(data.get(1).toString());
        reportlist.add(reportName);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return reportlist;
  }

  public ArrayList<ReportsForm> getfinanceYear()
  {
    logger.info("In getfinanceYear()");
    ArrayList reportlist = new ArrayList();
    ReportsForm reportName = null;
    try
    {
      StringBuilder query = new StringBuilder();
      query.append("select ");
      query.append(this.dbo);
      query.append("DATE_FORMAT(start_date,'%d-%m-%Y')year from gl_accounting_year_m order by start_date");
      logger.info(new StringBuilder().append("query To get finantialYear   :  ").append(query.toString()).toString());
      ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      for (int i = 0; i < formatlist.size(); i++)
      {
        ArrayList data = (ArrayList)formatlist.get(i);
        reportName = new ReportsForm();
        reportName.setFinanceYear(data.get(0).toString());
        reportlist.add(reportName);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return reportlist;
  }

  public String getMaxDefaultDate()
  {
    String date = "";
    try
    {
      StringBuilder query = new StringBuilder();
      query.append("select ");
      query.append(this.dbo);
      query.append("DATE_FORMAT(min(end_date),'%d-%m-%Y')year from gl_accounting_year_m");
      logger.info(new StringBuilder().append("query To get default max Finantial Date   :  ").append(query.toString()).toString());
      date = ConnectionReportDumpsDAO.singleReturn(query.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return date;
  }

  public String getcutOffDate()
  {
    logger.info("In getcutOffDate()");
    String date = "";
    try
    {
      StringBuilder query = new StringBuilder();
      query.append("select ");
      query.append(this.dbo);
      query.append("DATE_FORMAT(max(MVMT_DATE),'%d-%m-%Y')date from cr_loan_dtl_eod");
      logger.info(new StringBuilder().append("Query for find cutOffDate   :  ").append(query.toString()).toString());
      date = ConnectionReportDumpsDAO.singleReturn(query.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return date;
  }

  public ArrayList<ReportsForm> getScheme()
  {
    logger.info("In getScheme()");
    ArrayList reportlist = new ArrayList();
    ReportsForm reportName = null;
    try
    {
      String query = "select scheme_id,scheme_desc_l from cr_scheme_m where rec_status='A' and scheme_id in(4,5,6)";
      logger.info(new StringBuilder().append("query....... ").append(query).toString());
      ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      for (int i = 0; i < formatlist.size(); i++)
      {
        ArrayList data = (ArrayList)formatlist.get(i);
        reportName = new ReportsForm();
        reportName.setSchemeID(data.get(0).toString());
        reportName.setSchemeDesc(data.get(1).toString());
        reportlist.add(reportName);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return reportlist;
  }

  public ArrayList getDONo(String pLoanId, String userId, String nhbDate)
  {
    logger.info("In getDONo() of ReportDAOImpl.   ");
    ArrayList result = new ArrayList();
    String doNo = "";
    String record = "";
    int count = 1;
    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = null;
    try
    {
      StringBuilder query = new StringBuilder();
      query.append(new StringBuilder().append("select count(1) from cr_do_dtl where loan_id='").append(CommonFunction.checkNull(pLoanId).trim()).append("'").toString());
      logger.info(new StringBuilder().append("In getDONo() of ReportDAOImpl.   query    :   ").append(query).toString());
      count = Integer.parseInt(ConnectionReportDumpsDAO.singleReturn(query.toString()));
      logger.info(new StringBuilder().append("In getDONo() of ReportDAOImpl.   count    :   ").append(count).toString());
      if (count == 0)
      {
        record = "Original";
        insertPrepStmtObject = new PrepStmtObject();
        query.append("insert into cr_do_dtl(LOAN_ID,USER_ID,REPORT_DATE) values(?,?,");

        query.append(this.dbo);
        query.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )").toString());

        if (CommonFunction.checkNull(pLoanId).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(pLoanId.trim());
        if (CommonFunction.checkNull(userId).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(userId.trim());
        if (CommonFunction.checkNull(nhbDate).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(nhbDate.trim());
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("print query.....").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In getDONo() of ReportDAOImpl.   status    :   ").append(status).toString());
      }
      else {
        record = "Duplicate";
      }query.append(new StringBuilder().append("select DO_SEQUENCE from cr_do_dtl where loan_id='").append(CommonFunction.checkNull(pLoanId).trim()).append("'").toString());
      logger.info(new StringBuilder().append("In getDONo() of ReportDAOImpl.   query    :   ").append(query).toString());
      doNo = ConnectionReportDumpsDAO.singleReturn(query.toString());
      logger.info(new StringBuilder().append("In getDONo() of ReportDAOImpl.   doNo    :   ").append(doNo).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }result.add(doNo);
    result.add(record);
    return result;
  }

  public String getCAMReport(String dealId)
  {
    String reportName = "";
    String query = new StringBuilder().append("select cam_report from cr_category_cam_mapping a join cr_deal_loan_dtl b on(deal_product_category=product_category)where deal_id='").append(CommonFunction.checkNull(dealId).trim()).append("' ").toString();
    logger.info(new StringBuilder().append("In getCAMReport of ReportDAOImpl   query    :   ").append(query).toString());
    reportName = ConnectionReportDumpsDAO.singleReturn(query);
    return reportName;
  }

  public String getCutoffDateForIncipient() {
    StringBuilder bufInsSql = new StringBuilder();
    bufInsSql.append("SELECT STUFF((SELECT '|' +  dbo.DATE_FORMAT(MAKER_DATE,'%d-%m-%Y') FROM cr_incipient_tmp_dtl FOR XML PATH ('')), 1, 1, '')");
    logger.info(new StringBuilder().append("Query  to get list off cut_off_date   : ").append(bufInsSql.toString()).toString());
    String cutOffDate = "";
    try
    {
      cutOffDate = ConnectionReportDumpsDAO.singleReturn(bufInsSql.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }logger.info(new StringBuilder().append("cutOffDate   :   ").append(cutOffDate).toString());
    return cutOffDate;
  }

  public ArrayList generateSOA(int companyId, String pLoanId, String pBusinessDate, String userID, String source, String fromDate, String toDate, String soaFor)
  {
    ArrayList result = new ArrayList();
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    String query = "";
    String s1 = "";
    String s2 = "";
    String s3 = "";
    try
    {
      in.add(Integer.valueOf(companyId));
      in.add(Integer.valueOf(0));
      in.add(CommonFunction.checkNull(soaFor).trim());
      in.add(Integer.valueOf(Integer.parseInt(pLoanId)));
      in.add(pBusinessDate);
      in.add(userID);
      out.add(s1);
      out.add(s2);
      out.add(s3);
      logger.info(new StringBuilder().append("Generate_SOA (").append(in.toString()).append(",").append(out.toString()).toString());
      outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("Generate_SOA", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
      logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
      result.add(s1);
      result.add(s2);

      if (s1.equalsIgnoreCase("S")) {
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("NR"))
        {
          logger.info(new StringBuilder().append("p_loan_id  : ").append(pLoanId).toString());
          logger.info(new StringBuilder().append("userId     : ").append(userID).toString());
          query = new StringBuilder().append("select isnull(SEQ_NO,0)SEQ_NO,(select sum(DISBURSAL_AMOUNT)DISBURSAL_AMOUNT from cr_loan_disbursal_dtl where rec_status='A' and loan_id='").append(pLoanId).append("')DISBURSED_AMOUNT,").append(" replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(isnull(upper(address_dtl.customer_name),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')customer_name,").append(" replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(isnull(upper(address_dtl.addressLine1),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')addressLine1,").append(" replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(isnull(upper(address_dtl.addressLine2),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')addressLine2,").append(" phone, concat(upper(com_branch_m.branch_desc),' ')as branch_desc,cr_loan_dtl.LOAN_NO,cr_loan_dtl.LOAN_id,concat(upper(cr_product_m.product_desc),' ')as product_desc,").append(" concat(upper(cr_scheme_m.scheme_desc),' ')as scheme_desc,cr_loan_dtl.loan_approval_date as loan_start_date,").append(" st_date.instl_date as install_start_date, ed_date.instl_date as install_end_date,").append(" isnull(cr_loan_dtl.loan_tenure,0) as total_tenure,CASE lOAN_REPAYMENT_FREQ WHEN 'M' THEN 'MONTHLY ' WHEN 'B' THEN 'BIMONTHLY ' WHEN 'Q' THEN 'QUARTERLY ' WHEN 'H' THEN 'HALF YEARLY  'else 'YEARLY ' end as freqency,").append(" isnull(cr_loan_dtl.LOAN_NO_OF_INSTALLMENT,0) as no_of_instalments,isnull(cr_loan_dtl.LOAN_RECEIVED_PRINCIPAL,0) as LOAN_RECEIVED_PRINCIPAL,").append(" (SELECT sum(INSTL_AMOUNT_RECD/INSTL_AMOUNT) FROM CR_REPAYSCH_DTL WHERE BILL_FLAG='Y' AND INSTL_AMOUNT_RECD>0 AND REC_TYPE='I' AND LOAN_ID='").append(pLoanId).append("' group by loan_id) AS EMI_RECD,").append(" (isnull(CR_LOAN_DTL.LOAN_RECEIVED_INSTL_AMOUNT,0)-isnull(CR_LOAN_DTL.LOAN_RECEIVED_PRINCIPAL,0)) AS RECD_INTEREST,").append(" (select count(loan_id) from cr_statement_tmp where loan_id='").append(pLoanId).append("' and next_status='B' and USER_ID='").append(userID).append("') as no_bounce,").append(" isnull(cr_loan_dtl.loan_loan_amount,0)loan_loan_amount,isnull(cr_loan_dtl.loan_emi_amount,0) as emi_amount, isnull(cr_loan_dtl.loan_balance_principal,0) as principal_os,isnull(instlDue.instl_due,0) over_due_amount,").append(" isnull(B.other_charge_due,0)as other_charge_due,cr_loan_dtl.REC_STATUS, IIF(cr_loan_dtl.REC_STATUS='C', 'CLOSED ',concat(cr_loan_dtl.npa_flag,' '))  as loan_status,").append(" isnull(cm.collateral_amount,0)collateral_amount,cr_loan_dtl.loan_no,L.TXN_DATE AS TXN_DATE,concat(upper(L.PARTICULARS),' ')as PARTICULARS,").append(" L.DEBIT,L.CREDIT, CASE cr_loan_dtl.LOAN_RATE_TYPE WHEN 'F' THEN 'FLAT ' WHEN 'P' THEN 'PTPM ' WHEN 'E' THEN 'EFFECTIVE ' END AS  LOAN_RATE_TYPE,").append(" cr_loan_dtl.LOAN_FINAL_RATE,0.00 as Opening_bls,cr_loan_dtl.LOAN_MATURITY_DATE,ISNULL(TERM.TERMINATION_DATE,'')   AS TERMINATION_DATE, ").append(" concat(case isnull(LEGAL_FLAG,'NA') when 'X' then 'NO' when 'A' then 'YES' else 'NA' end,'|',case isnull(REPO_FLAG,'NA') when 'X' then 'NO' when 'A' then 'YES' else 'NA' end)legalRepoFlag ").append(" ,concat(Vehicle.VEHICLE_ASSET_DETAILS,' ')as VEHICLE_ASSET_DETAILS,ISNULL(EA.EXCESS,0) as EXCESS,concat(Vehicles.VEHICLE_NO,'')as VEHICLE_No ").append(" ,concat(Vehicles.VEHICLE_MAKE,' ')as  VEHICLE_MAKE,concat(Vehicles.VEHICLE_MODEL,' ')as  VEHICLE_MODEL,concat(Vehicles.ENGINE_NUMBER,' ')as  ENGINE_NUMBER,concat(Vehicles.VEHICLE_CHASIS_NUMBER,' ')as  VEHICLE_CHASIS_NUMBER ").append(" from  cr_loan_dtl   ").append(" LEFT JOIN CR_TERMINATION_DTL TERM ON cr_loan_dtl.LOAN_ID=TERM.LOAN_ID AND TERM.REC_STATUS='A'  ").append(" left outer join ").append(" (").append(" \t\tselect top 1 cr_loan_dtl.LOAN_ID,concat(upper(gcd_customer_m.customer_name),' ')as customer_name,").append(" \t\tIIF(ADDRESS_LINE1 is null,IIF(ADDRESS_LINE2 is null,IIF(ADDRESS_LINE3 is null,' ',concat(upper(ADDRESS_LINE3),' ')), IIF(ADDRESS_LINE3 is null,concat(upper(ADDRESS_LINE2),' '),concat(concat(upper(ADDRESS_LINE2),','),concat(upper(ADDRESS_LINE3),' ')))), IIF(ADDRESS_LINE2 is null,IIF(ADDRESS_LINE3 is null,concat(upper(ADDRESS_LINE1),' '),concat(concat(upper(ADDRESS_LINE1),','), concat(upper(ADDRESS_LINE3),' '))),IIF(ADDRESS_LINE3 is null,concat(concat(upper(ADDRESS_LINE1),','),concat(upper(ADDRESS_LINE2),' ')), concat(concat(concat(upper(ADDRESS_LINE1),','),concat(upper(ADDRESS_LINE2),',')),concat(upper(ADDRESS_LINE3),' ')))))as addressLine1,").append(" \t\tconcat(IIF(DISTRICT_DESC is null,IIF(STATE_DESC is null,IIF(COUNTRY_DESC is null,' ',concat('(',upper(COUNTRY_DESC),') ')), IIF(COUNTRY_DESC is null,concat(upper(STATE_DESC),' '),concat(concat(upper(STATE_DESC),','),concat('(',upper(COUNTRY_DESC),') ')))), IIF(STATE_DESC is null,IIF(COUNTRY_DESC is null,concat(upper(DISTRICT_DESC),' '),concat(concat(upper(DISTRICT_DESC),','), concat('(',upper(COUNTRY_DESC),') '))),IIF(COUNTRY_DESC is null,concat(concat(upper(DISTRICT_DESC),','),concat(upper(STATE_DESC),' ')), concat(concat(concat(upper(DISTRICT_DESC),','),concat(upper(STATE_DESC),',')),concat('(',upper(COUNTRY_DESC),') '))))), IIF(PINCODE is null,' ',concat('- ',PINCODE)))as addressLine2,").append(" \t\tIIF(PRIMARY_PHONE is null,IIF(ALTERNATE_PHONE is null,' ', concat('PHONE NO.-',concat(ALTERNATE_PHONE,' '))),IIF(ALTERNATE_PHONE is null,concat('PHONE NO.-',concat(PRIMARY_PHONE, ' ')),concat('PHONE NO.-',concat(concat(PRIMARY_PHONE,','),concat(ALTERNATE_PHONE,' '))))) as phone").append(" \t\tfrom  cr_loan_dtl  ").append(" \t\tjoin gcd_customer_m on(cr_loan_dtl.loan_customer_id=gcd_customer_m.customer_id     and cr_loan_dtl.LOAN_ID='").append(pLoanId).append("') ").append(" \t\tleft outer join com_address_m  on((com_address_m.BPID=gcd_customer_m.CUSTOMER_ID) and(com_address_m.COMMUNICATION_ADDRESS='Y'))  ").append(" \t\tleft outer join com_country_m on(com_country_m.COUNTRY_ID=com_address_m.COUNTRY)").append(" \t\tleft outer join com_state_m  on((com_state_m.COUNTRY_ID=com_address_m.COUNTRY)and(com_state_m.STATE_ID=com_address_m.STATE))  ").append(" \t\tleft outer join  com_district_m on(com_district_m.STATE_ID=com_address_m.STATE)and(com_district_m.DISTRICT_ID=com_address_m.DISTRICT)").append(" ) address_dtl on cr_loan_dtl.loan_id=address_dtl.loan_id   ").append(" join com_branch_m  on(com_branch_m.branch_id=cr_loan_dtl.loan_branch)").append(" join cr_product_m  on(cr_product_m.product_id=cr_loan_dtl.loan_product) ").append(" join cr_scheme_m   on(cr_scheme_m.scheme_id=cr_loan_dtl.loan_scheme)  ").append(" left outer join ").append(" (").append(" \t\tselect loan_id,instl_date,instl_no  ").append(" \t\tfrom cr_repaysch_dtl where instl_no=(select min(instl_no) from cr_repaysch_dtl where ISNULL(REC_TYPE,'I')='I' AND LOAN_ID='").append(pLoanId).append("')    and LOAN_ID='").append(pLoanId).append("'").append(" ) as st_date on(st_date.loan_id=cr_loan_dtl.loan_id)  ").append(" left outer join ").append("(").append(" \t\tselect loan_id,instl_date,instl_no ").append(" \t\tfrom cr_repaysch_dtl  ").append(" \t\twhere instl_no=(select max(instl_no) from cr_repaysch_dtl where LOAN_ID='").append(pLoanId).append("') and  LOAN_ID='").append(pLoanId).append("'").append(" ) as ed_date on(ed_date.loan_id=cr_loan_dtl.loan_id) ").append(" left outer join ").append(" (").append(" \t\tselect sum(cr_sd_dtl.SD_AMOUNT) as collateral_amount,cr_sd_dtl.LOAN_ID ").append(" \t\tfrom cr_sd_dtl where rec_status not in('X','L','P','F') and  cr_sd_dtl.LOAN_ID='").append(pLoanId).append("' ").append(" \t\tgroup by cr_sd_dtl.LOAN_ID").append(" ) as cm on(cr_loan_dtl.LOAN_ID=cm.LOAN_ID)  ").append(" left outer join").append(" (").append(" \t\tselect sum(isnull(cr_txnadvice_dtl.advice_amount,0))- sum(isnull(txn_adjusted_amount,0)) as other_charge_due,loan_id ").append(" \t\tfrom cr_txnadvice_dtl where advice_type = 'R'  and CHARGE_CODE_ID NOT IN ('7','27','8') and loan_id='").append(pLoanId).append("' group by loan_id").append(" ) as B  on(B.loan_id=cr_loan_dtl.loan_id)  ").append(" left outer join ").append(" (").append(" \t\tselect sum(isnull(cr_txnadvice_dtl.advice_amount,0))- sum(isnull(txn_adjusted_amount,0)) as instl_due,loan_id ").append(" \t\tfrom cr_txnadvice_dtl where advice_type = 'R' and CHARGE_CODE_ID IN(7,27) and loan_id='").append(pLoanId).append("' ").append(" \t\tgroup by loan_id").append(" ) as instlDue on(instlDue.loan_id=cr_loan_dtl.loan_id)  ").append(" LEFT OUTER JOIN ").append(" (").append(" \t\tselect SEQ_NO,TXN_DATE,LOAN_ID, PARTICULARS , DEBIT,CREDIT  FROM CR_STATEMENT_TMP where USER_ID='").append(userID).append("'").append(" ) AS L ON(L.LOAN_ID=cr_loan_dtl.LOAN_ID) ").append(" LEFT OUTER JOIN ").append(" (").append("\t\tSELECT LOAN_ID,STUFF((SELECT '|' +  concat(g.ASSET_COLLATERAL_DESC,' ') FROM cr_asset_collateral_m g where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_ASSET_DETAILS ").append("\t\tFROM  CR_LOAN_COLLATERAL_M S  ").append("\t\tLEFT JOIN CR_ASSET_COLLATERAL_M T ON T.ASSET_ID=S.ASSETID ").append("\t\tWHERE S.LOAN_ID='").append(pLoanId).append("' ").append(" ) as Vehicle on (Vehicle.LOAN_ID=cr_loan_dtl.LOAN_ID) ").append(" LEFT OUTER JOIN ").append(" (").append(" \t\tSELECT LOAN_ID,STUFF((SELECT '|' +  iif(g.ASSET_COLLATERAL_CLASS='VEHICLE',g.vehicle_registration_no,'') FROM cr_asset_collateral_m g where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_No,").append("\t\tSTUFF((SELECT '|' +  g.VEHICLE_MAKE FROM cr_asset_collateral_m g  where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_MAKE, STUFF((SELECT '|' +  g.VEHICLE_MODEL FROM cr_asset_collateral_m g  where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_MODEL, ").append("\t\tSTUFF((SELECT '|' +  g.ENGINE_NUMBER FROM cr_asset_collateral_m g  where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as ENGINE_NUMBER, STUFF((SELECT '|' +  g.VEHICLE_CHASIS_NUMBER FROM cr_asset_collateral_m g  where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_CHASIS_NUMBER ").append("\t\tFROM  CR_LOAN_COLLATERAL_M S ").append("\t\tLEFT JOIN CR_ASSET_COLLATERAL_M T ON T.ASSET_ID=S.ASSETID AND T.ASSET_COLLATERAL_CLASS='VEHICLE' ").append("\t\tWHERE S.LOAN_ID='").append(pLoanId).append("'").append(" ) AS VEHICLES ON (VEHICLES.LOAN_ID=CR_LOAN_DTL.LOAN_ID) ").append(" LEFT OUTER JOIN ").append(" (").append("\t\tSELECT LOAN_ID,SUM(ISNULL(ISNULL(ADVICE_AMOUNT,0)-ISNULL(TXN_ADJUSTED_AMOUNT,0)-ISNULL(AMOUNT_IN_PROCESS,0),0.00)) AS EXCESS ").append("\t\tFROM cr_txnadvice_dtl WHERE ADVICE_TYPE='P' and CHARGE_CODE_ID=10 ").append("\t\tand LOAN_ID='").append(pLoanId).append("' ").append("\t\tgroup by LOAN_ID ").append(" ) as EA on (EA.LOAN_ID=cr_loan_dtl.LOAN_ID) ").append(" where cr_loan_dtl.LOAN_ID='").append(pLoanId).append("' and cr_loan_dtl.REC_STATUS<>'X' order by L.TXN_DATE,SEQ_NO").toString();
        }
        else
        {
          query = new StringBuilder().append("  select isnull(SEQ_NO,0)SEQ_NO,(select sum(DISBURSAL_AMOUNT)DISBURSAL_AMOUNT from cr_loan_disbursal_dtl where rec_status='A' and loan_id='").append(pLoanId).append("')DISBURSED_AMOUNT,").append(" replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(isnull(upper(add_dtl.customer_name),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')customer_name,").append(" replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(isnull(upper(add_dtl.addressLine1),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')addressLine1,").append(" replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(concat(isnull(upper(add_dtl.addressLine2),''),' '),'!','! '),'%','% '),'(','( '),')',') '),'-','- '),'{','{ '),'[','[ '),'}','} '),']','] '),':',': '),';','; '),'\"','\" '),'/','/ '),'?','? '),'.','. '),',',', ')addressLine2,").append(" phone,concat(upper(com_branch_m.branch_desc),' ')as branch_desc,cr_loan_dtl.LOAN_NO,cr_loan_dtl.LOAN_id,concat(upper(cr_product_m.product_desc),' ')as product_desc,").append(" concat(upper(cr_scheme_m.scheme_desc),' ')as scheme_desc,cr_loan_dtl.loan_approval_date as loan_start_date,").append(" st_date.instl_date as install_start_date, ed_date.instl_date as install_end_date,").append(" isnull(cr_loan_dtl.loan_tenure,0) as total_tenure,CASE lOAN_REPAYMENT_FREQ  WHEN 'M' THEN 'MONTHLY ' WHEN 'B' THEN 'BIMONTHLY ' WHEN 'Q' THEN 'QUARTERLY ' WHEN 'H' THEN  'HALF YEARLY  'else 'YEARLY ' end as freqency,").append(" isnull(cr_loan_dtl.LOAN_NO_OF_INSTALLMENT,0) as no_of_instalments, isnull(cr_loan_dtl.LOAN_RECEIVED_PRINCIPAL,0) as LOAN_RECEIVED_PRINCIPAL,").append(" (SELECT sum(INSTL_AMOUNT_RECD/INSTL_AMOUNT) FROM CR_REPAYSCH_DTL WHERE BILL_FLAG='Y' AND INSTL_AMOUNT_RECD>0 AND REC_TYPE='I' AND LOAN_ID='").append(pLoanId).append("' group by loan_id) AS EMI_RECD,").append(" (isnull(CR_LOAN_DTL.LOAN_RECEIVED_INSTL_AMOUNT,0)- isnull(CR_LOAN_DTL.LOAN_RECEIVED_PRINCIPAL,0)) AS RECD_INTEREST,").append(" (select count(loan_id) from cr_statement_tmp where  loan_id='").append(pLoanId).append("' and next_status='B' and USER_ID='").append(userID).append("') as no_bounce,").append(" isnull(cr_loan_dtl.loan_loan_amount,0)loan_loan_amount, isnull(cr_loan_dtl.loan_emi_amount,0) as emi_amount,isnull(cr_loan_dtl.loan_balance_principal,0) as principal_os, isnull(instlDue.instl_due,0) over_due_amount,").append(" isnull(B.other_charge_due,0)as other_charge_due,cr_loan_dtl.REC_STATUS, IIF(cr_loan_dtl.REC_STATUS='C', 'CLOSED ', concat(cr_loan_dtl.npa_flag,' '))  as loan_status,").append(" isnull(cm.collateral_amount,0)collateral_amount,cr_loan_dtl.loan_no,L.TXN_DATE AS TXN_DATE,concat(L.PARTICULARS,' ')as PARTICULARS,").append(" L.DEBIT,L.CREDIT, CASE cr_loan_dtl.LOAN_RATE_TYPE WHEN 'F' THEN 'FLAT ' WHEN 'P' THEN 'PTPM ' WHEN 'E' THEN 'EFFECTIVE ' END AS LOAN_RATE_TYPE,").append(" cr_loan_dtl.LOAN_FINAL_RATE,isnull(opBls.balance,0) as Opening_bls,cr_loan_dtl.LOAN_MATURITY_DATE,ISNULL(TERM.TERMINATION_DATE,'')  AS TERMINATION_DATE, ").append(" concat(case isnull(LEGAL_FLAG,'NA') when 'X' then 'NO' when 'A' then 'YES' else 'NA' end,'|',case isnull(REPO_FLAG,'NA') when 'X' then 'NO' when 'A' then 'YES' else 'NA' end)legalRepoFlag ").append(" ,concat(Vehicle.VEHICLE_ASSET_DETAILS,' ')as VEHICLE_ASSET_DETAILS,ISNULL(EA.EXCESS,0) as EXCESS,concat(Vehicles.VEHICLE_NO,'')as VEHICLE_No ").append(" ,concat(Vehicles.VEHICLE_MAKE,' ')as  VEHICLE_MAKE,concat(Vehicles.VEHICLE_MODEL,' ')as  VEHICLE_MODEL,concat(Vehicles.ENGINE_NUMBER,' ')as  ENGINE_NUMBER,concat(Vehicles.VEHICLE_CHASIS_NUMBER,' ')as  VEHICLE_CHASIS_NUMBER ").append(" from  cr_loan_dtl ").append(" LEFT JOIN CR_TERMINATION_DTL TERM ON cr_loan_dtl.LOAN_ID=TERM.LOAN_ID AND TERM.REC_STATUS='A'  ").append(" left outer join").append(" (").append(" \t\tselect top 1 cr_loan_dtl.LOAN_ID,concat(upper(gcd_customer_m.customer_name),' ')as customer_name,").append(" \t\tIIF(ADDRESS_LINE1 is null, IIF(ADDRESS_LINE2 is null,IIF(ADDRESS_LINE3 is null,' ',concat(upper(ADDRESS_LINE3),' ')),IIF(ADDRESS_LINE3 is null,concat(upper(ADDRESS_LINE2),' '), concat(concat(upper(ADDRESS_LINE2),','),concat(upper(ADDRESS_LINE3),' ')))),IIF(ADDRESS_LINE2 is null,IIF(ADDRESS_LINE3 is null,concat(upper(ADDRESS_LINE1), ' '),concat(concat(upper(ADDRESS_LINE1),','),concat(upper(ADDRESS_LINE3),' '))),IIF(ADDRESS_LINE3 is null,concat(concat(upper(ADDRESS_LINE1),','), concat(upper(ADDRESS_LINE2),' ')),concat(concat(concat(upper(ADDRESS_LINE1),','),concat(upper(ADDRESS_LINE2),',')),concat(upper(ADDRESS_LINE3),' ')))))as  addressLine1,").append(" \t\tconcat(IIF(DISTRICT_DESC is null,IIF(STATE_DESC is null,IIF(COUNTRY_DESC is null,' ',concat('(',upper(COUNTRY_DESC),') ')), IIF(COUNTRY_DESC is null,concat(upper(STATE_DESC),' '),concat(concat(upper(STATE_DESC),','),concat('(',upper(COUNTRY_DESC),') ')))),IIF(STATE_DESC is null, IIF(COUNTRY_DESC is null,concat(upper(DISTRICT_DESC),' '),concat(concat(upper(DISTRICT_DESC),','),concat('(',upper(COUNTRY_DESC),') '))), IIF(COUNTRY_DESC is null,concat(concat(upper(DISTRICT_DESC),','),concat(upper(STATE_DESC),' ')),concat(concat(concat(upper(DISTRICT_DESC),','),concat(upper(STATE_DESC),',')),concat('(',upper(COUNTRY_DESC),') '))))),IIF(PINCODE is null,' ',concat('- ',PINCODE)))as addressLine2,").append(" \t\tIIF(PRIMARY_PHONE is null,IIF(ALTERNATE_PHONE is null,' ',concat('PHONE NO.-',concat(ALTERNATE_PHONE,' '))),IIF(ALTERNATE_PHONE is null,concat('PHONE NO.-',concat(PRIMARY_PHONE,' ')),concat('PHONE NO.-',concat(concat(PRIMARY_PHONE,','),concat(ALTERNATE_PHONE,' '))))) as phone  ").append(" \t\tfrom  cr_loan_dtl  ").append(" \t\tjoin gcd_customer_m on(cr_loan_dtl.loan_customer_id=gcd_customer_m.customer_id  and cr_loan_dtl.LOAN_ID='").append(pLoanId).append("') ").append(" \t\tleft outer join com_address_m on((com_address_m.BPID=gcd_customer_m.CUSTOMER_ID) and(com_address_m.COMMUNICATION_ADDRESS='Y'))  ").append(" \t\tleft outer join com_country_m on(com_country_m.COUNTRY_ID=com_address_m.COUNTRY) ").append(" \t\tleft outer join com_state_m on((com_state_m.COUNTRY_ID=com_address_m.COUNTRY)and(com_state_m.STATE_ID=com_address_m.STATE)) ").append(" \t\tleft outer join com_district_m on(com_district_m.STATE_ID=com_address_m.STATE)and(com_district_m.DISTRICT_ID=com_address_m.DISTRICT)").append(" ) add_dtl on cr_loan_dtl.loan_id=add_dtl.loan_id  ").append(" left outer join ").append(" (").append(" \t\tselect (a.dr-a.cr)as balance ").append(" \t\tfrom ").append(" \t\t(").append(" \t\t\tselect sum(debit)dr,sum(credit)cr from CR_STATEMENT_TMP where user_id='").append(userID).append("' ").append(" \t\t\tand txn_date < '").append(fromDate).append("'").append(" \t\t)a").append(" )opBls on 'a'='a' ").append(" join com_branch_m on(com_branch_m.branch_id=cr_loan_dtl.loan_branch) ").append(" join cr_product_m on(cr_product_m.product_id=cr_loan_dtl.loan_product)  ").append(" join cr_scheme_m  on(cr_scheme_m.scheme_id=cr_loan_dtl.loan_scheme) ").append(" left outer join ").append(" (").append(" \t\tselect DISTINCT loan_id,instl_date,instl_no ").append(" \t\tfrom cr_repaysch_dtl where instl_no=(select min(instl_no) from cr_repaysch_dtl where  ISNULL(REC_TYPE,'I')='I' AND LOAN_ID='").append(pLoanId).append("')  and LOAN_ID='").append(pLoanId).append("'").append(" ) as st_date on(st_date.loan_id=cr_loan_dtl.loan_id) ").append(" left outer join ").append(" (").append(" \t\tselect  loan_id,instl_date,instl_no ").append(" \t\tfrom cr_repaysch_dtl ").append(" \t\twhere instl_no=(select max(instl_no) from cr_repaysch_dtl where LOAN_ID='").append(pLoanId).append("') and LOAN_ID='").append(pLoanId).append("'").append(" ) AS ed_date on(ed_date.loan_id=cr_loan_dtl.loan_id)  ").append(" left outer join ").append(" ( ").append(" \t\tselect  sum(cr_sd_dtl.SD_AMOUNT) as collateral_amount,cr_sd_dtl.LOAN_ID  ").append(" \t\tfrom cr_sd_dtl where rec_status  not in('X','L','P','F') ").append(" \t\tand  cr_sd_dtl.LOAN_ID='").append(pLoanId).append("' ").append(" \t\tGROUP by cr_sd_dtl.LOAN_ID").append(" ) as cm on(cr_loan_dtl.LOAN_ID=cm.LOAN_ID) ").append(" left outer join ").append(" ( ").append(" \t\tselect sum(isnull(cr_txnadvice_dtl.advice_amount,0)- isnull(txn_adjusted_amount,0)) as other_charge_due,loan_id ").append(" \t\tfrom cr_txnadvice_dtl ").append(" \t\twhere advice_type = 'R' and CHARGE_CODE_ID NOT IN ('7','27','8')  and loan_id='").append(pLoanId).append("' ").append(" \t\tand advice_date>='").append(fromDate).append("' and advice_date<='").append(toDate).append("' ").append(" \t\tgroup by loan_id ").append(" ) as B on(B.loan_id=cr_loan_dtl.loan_id)  ").append(" left outer join ").append(" (").append(" \t\tselect sum(isnull(cr_txnadvice_dtl.advice_amount,0)- isnull(txn_adjusted_amount,0)) as instl_due,loan_id ").append(" \t\tfrom cr_txnadvice_dtl ").append(" \t\twhere advice_type = 'R' and CHARGE_CODE_ID in(7,27)  and loan_id='").append(pLoanId).append("' ").append(" \t\tand advice_date>='").append(fromDate).append("' and advice_date<='").append(toDate).append("' group by loan_id").append(" ) as instlDue on(instlDue.loan_id=cr_loan_dtl.loan_id)  ").append(" LEFT OUTER JOIN ").append(" (").append(" \t\tselect 0 SEQ_NO,'").append(fromDate).append("' TXN_DATE,'").append(pLoanId).append("' LOAN_ID, 'OPENING BALANCE ' PARTICULARS,0 DEBIT,0 CREDIT ").append(" \t\tunion all").append(" \t\tselect SEQ_NO,TXN_DATE,LOAN_ID, upper(PARTICULARS) , DEBIT,CREDIT FROM CR_STATEMENT_TMP where USER_ID='").append(userID).append("' ").append(" \t\tand TXN_DATE >='").append(fromDate).append("' and TXN_DATE <='").append(toDate).append("'").append(" ) AS L ON(L.LOAN_ID=cr_loan_dtl.LOAN_ID) ").append(" LEFT OUTER JOIN ").append(" (").append("\t\tSELECT LOAN_ID,STUFF((SELECT '|' +  concat(g.ASSET_COLLATERAL_DESC,' ') FROM cr_asset_collateral_m g where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_ASSET_DETAILS ").append("\t\tFROM  CR_LOAN_COLLATERAL_M S  ").append("\t\tLEFT JOIN CR_ASSET_COLLATERAL_M T ON T.ASSET_ID=S.ASSETID ").append("\t\tWHERE S.LOAN_ID='").append(pLoanId).append("' ").append(" ) as Vehicle on (Vehicle.LOAN_ID=cr_loan_dtl.LOAN_ID) ").append(" LEFT OUTER JOIN ").append(" (").append(" \t\tSELECT LOAN_ID,STUFF((SELECT '|' +  iif(g.ASSET_COLLATERAL_CLASS='VEHICLE',g.vehicle_registration_no,'') FROM cr_asset_collateral_m g where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_No,").append("\t\tSTUFF((SELECT '|' +  g.VEHICLE_MAKE FROM cr_asset_collateral_m g  where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_MAKE, STUFF((SELECT '|' +  g.VEHICLE_MODEL FROM cr_asset_collateral_m g  where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_MODEL, ").append("\t\tSTUFF((SELECT '|' +  g.ENGINE_NUMBER FROM cr_asset_collateral_m g  where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as ENGINE_NUMBER, STUFF((SELECT '|' +  g.VEHICLE_CHASIS_NUMBER FROM cr_asset_collateral_m g  where g.ASSET_ID=s.ASSETID FOR XML PATH ('')), 1, 1, '') as VEHICLE_CHASIS_NUMBER ").append("\t\tFROM  CR_LOAN_COLLATERAL_M S ").append("\t\tLEFT JOIN CR_ASSET_COLLATERAL_M T ON T.ASSET_ID=S.ASSETID AND T.ASSET_COLLATERAL_CLASS='VEHICLE' ").append("\t\tWHERE S.LOAN_ID='").append(pLoanId).append("'").append(" ) AS VEHICLES ON (VEHICLES.LOAN_ID=CR_LOAN_DTL.LOAN_ID) ").append(" LEFT OUTER JOIN ").append(" (").append("\t\tSELECT LOAN_ID,SUM(ISNULL(ISNULL(ADVICE_AMOUNT,0)-ISNULL(TXN_ADJUSTED_AMOUNT,0)-ISNULL(AMOUNT_IN_PROCESS,0),0.00)) AS EXCESS ").append("\t\tFROM cr_txnadvice_dtl WHERE ADVICE_TYPE='P' and CHARGE_CODE_ID=10 ").append("\t\tand LOAN_ID='").append(pLoanId).append("' ").append("\t\tgroup by LOAN_ID ").append(" ) as EA on (EA.LOAN_ID=cr_loan_dtl.LOAN_ID) ").append(" where cr_loan_dtl.LOAN_ID='").append(pLoanId).append("' and cr_loan_dtl.REC_STATUS<>'X' order by L.TXN_DATE,SEQ_NO ").toString();
        }

      }

      result.add(query);
      logger.info(new StringBuilder().append("Statement of Account Query  :  ").append(query).toString());
    }
    catch (Exception e) {
      result.add("E");
      result.add("Some exception occur,Please contact Administrator...");
      e.printStackTrace();
    }
    return result;
  }

  public String getDateRangeLimit()
  {
    logger.info("in getDateRangeLimit() of MSSQLReportsDAOImpl ");
    StringBuilder bufInsSql = new StringBuilder();
    bufInsSql.append(" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='REPORT_DATE_RANGE_LIMIT' ");
    logger.info(new StringBuilder().append("Query  to getDateRangeLimit    : ").append(bufInsSql.toString()).toString());
    String dateRengeLimit = "";
    try
    {
      dateRengeLimit = ConnectionReportDumpsDAO.singleReturn(bufInsSql.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }logger.info(new StringBuilder().append("dateRengeLimit   :   ").append(dateRengeLimit).toString());
    return dateRengeLimit;
  }

  public String getDateRangeLimitSpecial()
  {
    logger.info("in getDateRangeLimitSpecial() of MSSQLReportsDAOImpl ");
    StringBuilder bufInsSql = new StringBuilder();
    bufInsSql.append(" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='REPORT_DATE_LIMIT_MAX' ");
    logger.info(new StringBuilder().append("Query  to getDateRangeLimitSpecial    : ").append(bufInsSql.toString()).toString());
    String DateRangeLimitSpecial = "";
    try
    {
      DateRangeLimitSpecial = ConnectionReportDumpsDAO.singleReturn(bufInsSql.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }logger.info(new StringBuilder().append("DateRangeLimitSpecial   :   ").append(DateRangeLimitSpecial).toString());
    return DateRangeLimitSpecial;
  }

  public String checkLinkedLoan(String p_loan_id) {
    logger.info("in getDateRangeLimit() of ReportsDAOImpl ");

    StringBuilder bufInsSql = new StringBuilder();
    bufInsSql.append(new StringBuilder().append(" SELECT COUNT(1) FROM CR_LINK_LOAN_DTL WHERE PRIMARY_LOAN='").append(p_loan_id).append("' OR LINK_LOAN='").append(p_loan_id).append("' ").toString());
    logger.info(new StringBuilder().append("Query  to get exist loan    : ").append(bufInsSql.toString()).toString());
    String existLoanCount = "";
    try
    {
      existLoanCount = ConnectionReportDumpsDAO.singleReturn(bufInsSql.toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    logger.info(new StringBuilder().append("existLoans   :   ").append(existLoanCount).toString());
    return existLoanCount;
  }
  public String checkLinkLoanFlag() { logger.info("in  getMakerCheckerFlag:::::::::::::: ");
    String linkLoanFlag = "";
    try {
      String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='LINK_LOAN_FLAG'";
      logger.info(new StringBuilder().append("Query for link loan flag is::::::").append(query).toString());

      linkLoanFlag = ConnectionReportDumpsDAO.singleReturn(query);
      logger.info(new StringBuilder().append("flak ki value hai::::::::").append(linkLoanFlag).toString());
      query = null;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return linkLoanFlag;
  }

  public boolean getEligibilityRecord(String pDealId, String pBusinessDate, String userId)
  {
    boolean status = false;
    ArrayList in = null;
    ArrayList out = null;
    ArrayList outMessages = null;
    String query = "";
    String s1 = "";
    String s2 = "";
    String s3 = "";
    String s4 = "";
    try
    {
      in = new ArrayList();
      out = new ArrayList();
      outMessages = new ArrayList();
      in.add(pDealId);
      in.add(CommonFunction.changeFormat(pBusinessDate));
      in.add(userId);
      out.add(s1);
      out.add(s2);
      logger.info(new StringBuilder().append("ELIGIBILITY_CALCULATION(").append(in.toString()).append(",").append(out.toString()).toString());
      outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("ELIGIBILITY_CALCULATION", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      logger.info(new StringBuilder().append("ELIGIBILITY_CALCULATION   s1  : ").append(s1).toString());
      logger.info(new StringBuilder().append("ELIGIBILITY_CALCULATION   s2  : ").append(s2).toString());
      in.clear();
      in = null;
      out.clear();
      out = null;
      outMessages.clear();
      outMessages = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }return status;
  }

  public String getDefaultFormateSOA() {
    logger.info("in  getDefaultFormateSOA().");
    String defaultFormate = "";
    try
    {
      String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='SOA_FROM_LOAN_VIEWER_TYPE'";
      logger.info(new StringBuilder().append("Query for getting defaultFormate of SOA  :  ").append(query).toString());
      defaultFormate = ConnectionReportDumpsDAO.singleReturn(query);
      logger.info(new StringBuilder().append("DefaultFormate of SOA  :  ").append(defaultFormate).toString());
      query = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }return defaultFormate;
  }

  public ArrayList getNocCount(String pLoanId, String userId, String nhbDate)
  {
    ArrayList result = new ArrayList();
    logger.info("In getNocCount() of ReportDAOImpl.   ");
    String doNo = "";
    String record = "";
    int count = 1;
    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = null;
    try
    {
      String query = new StringBuilder().append("select count(1) from cr_NOC_dtl where loan_id='").append(CommonFunction.checkNull(pLoanId).trim()).append("'").toString();
      logger.info(new StringBuilder().append("In getDONo() of ReportDAOImpl.   query    :   ").append(query).toString());
      count = Integer.parseInt(ConnectionReportDumpsDAO.singleReturn(query));
      logger.info(new StringBuilder().append("In getDONo() of ReportDAOImpl.   count    :   ").append(count).toString());
      if (count == 0)
      {
        record = "Original";
        insertPrepStmtObject = new PrepStmtObject();
        query = new StringBuilder().append("insert into cr_NOC_dtl(LOAN_ID,USER_ID,REPORT_DATE) values(?,?,STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)))").toString();
        if (CommonFunction.checkNull(pLoanId).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(pLoanId.trim());
        if (CommonFunction.checkNull(userId).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(userId.trim());
        if (CommonFunction.checkNull(nhbDate).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(nhbDate.trim());
        }
        insertPrepStmtObject.setSql(query);
        logger.info(new StringBuilder().append("print query.....").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In cr_NOC_dtl() of ReportDAOImpl.   status    :   ").append(status).toString());
      }
      else {
        record = "Duplicate";
      }query = new StringBuilder().append("select NOC_SEQUENCE from cr_NOC_dtl where loan_id='").append(CommonFunction.checkNull(pLoanId).trim()).append("'").toString();
      logger.info(new StringBuilder().append("In getNOCNo() of ReportDAOImpl.   query    :   ").append(query).toString());
      doNo = ConnectionReportDumpsDAO.singleReturn(query);
      logger.info(new StringBuilder().append("In NOC_SEQUENCE() of ReportDAOImpl.   NOC_SEQUENCE    :   ").append(doNo).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }result.add(doNo);
    result.add(record);
    return result;
  }

  public ArrayList getChargesDetail(String loanId)
  {
    ArrayList resultList = new ArrayList();
    ArrayList list1 = new ArrayList();
    ArrayList list2 = new ArrayList();
    ArrayList list3 = new ArrayList();
    ArrayList subList1 = new ArrayList();
    ArrayList subList2 = new ArrayList();
    ArrayList subList3 = new ArrayList();
    String query = "";
    try
    {
      query = new StringBuilder().append("select ADVICE_TYPE,sum(ADVICE_AMOUNT),sum(TXN_ADJUSTED_AMOUNT),(sum(ADVICE_AMOUNT)-sum(TXN_ADJUSTED_AMOUNT)) from cr_txnadvice_dtl where CHARGE_CODE_ID=133 and loan_id='").append(CommonFunction.checkNull(loanId).trim()).append("' group by loan_id").toString();
      logger.info(new StringBuilder().append("query To get detail for EMI with Insurance   :  ").append(query).toString());
      list1 = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      if (list1.size() > 0)
        subList1 = (ArrayList)list1.get(0);
      if (subList1.size() == 4)
      {
        resultList.add(CommonFunction.checkNull(subList1.get(0)).trim());
        resultList.add(CommonFunction.checkNull(subList1.get(1)).trim());
        resultList.add(CommonFunction.checkNull(subList1.get(2)).trim());
        resultList.add(CommonFunction.checkNull(subList1.get(3)).trim());
      }
      else
      {
        resultList.add("");
        resultList.add("0.00");
        resultList.add("0.00");
        resultList.add("0.00");
      }

      query = new StringBuilder().append("select ADVICE_TYPE,sum(ADVICE_AMOUNT),sum(TXN_ADJUSTED_AMOUNT),(sum(ADVICE_AMOUNT)-sum(TXN_ADJUSTED_AMOUNT)) from cr_txnadvice_dtl where CHARGE_CODE_ID in(123,207) and loan_id='").append(CommonFunction.checkNull(loanId).trim()).append("'group by loan_id").toString();
      logger.info(new StringBuilder().append("query To get detail for Legal Charges   :  ").append(query).toString());
      list2 = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      if (list2.size() > 0)
        subList2 = (ArrayList)list2.get(0);
      if (subList2.size() == 4)
      {
        resultList.add(CommonFunction.checkNull(subList2.get(0)).trim());
        resultList.add(CommonFunction.checkNull(subList2.get(1)).trim());
        resultList.add(CommonFunction.checkNull(subList2.get(2)).trim());
        resultList.add(CommonFunction.checkNull(subList2.get(3)).trim());
      }
      else
      {
        resultList.add("");
        resultList.add("0.00");
        resultList.add("0.00");
        resultList.add("0.00");
      }

      query = new StringBuilder().append("select ADVICE_TYPE,sum(ADVICE_AMOUNT),sum(TXN_ADJUSTED_AMOUNT),(sum(ADVICE_AMOUNT)-sum(TXN_ADJUSTED_AMOUNT)) from cr_txnadvice_dtl where CHARGE_CODE_ID in(206,232) and loan_id='").append(CommonFunction.checkNull(loanId).trim()).append("'group by loan_id ").toString();
      logger.info(new StringBuilder().append("query To get detail for Legal Charges   :  ").append(query).toString());
      list3 = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      if (list3.size() > 0)
        subList3 = (ArrayList)list3.get(0);
      if (subList3.size() == 4)
      {
        resultList.add(CommonFunction.checkNull(subList3.get(0)).trim());
        resultList.add(CommonFunction.checkNull(subList3.get(1)).trim());
        resultList.add(CommonFunction.checkNull(subList3.get(2)).trim());
        resultList.add(CommonFunction.checkNull(subList3.get(3)).trim());
      }
      else
      {
        resultList.add("");
        resultList.add("0.00");
        resultList.add("0.00");
        resultList.add("0.00");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      list1 = null;
      list2 = null;
      list3 = null;
      subList1 = null;
      subList2 = null;
      subList3 = null;
    }
    return resultList;
  }

  public ArrayList getReportData(int currentPageLink, ChequeAllocationVo chequeAllocationVo1)
  {
    ArrayList datalist = new ArrayList();
    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;
    ArrayList list = new ArrayList();

    StringBuilder bufInsSql = new StringBuilder();
    StringBuilder bufInsSqlCount = new StringBuilder();
    try
    {
      bufInsSql.append(new StringBuilder().append("SELECT INSTRUMENT_ID,CLD.LOAN_NO,gcm.CUSTOMER_NAME,cbm.BRANCH_DESC,RECIPT_NO, INSTRUMENT_NO, CASE INSTRUMENT_TYPE WHEN 'K' THEN 'KNOCKOFF' WHEN 'P' THEN 'PAYABLE' WHEN 'R' THEN 'RECEIVABLE' END AS INSTRUMENT_TYPE ,INSTRUMENT_AMOUNT, CASE CID.REC_STATUS WHEN 'A' THEN 'APPROVED' WHEN 'C' THEN 'SEND TO CUSTOMER' WHEN 'D' THEN 'DEPOSITED'WHEN 'R' THEN 'REALIZED' WHEN 'P' THEN 'PENDING' WHEN 'X' THEN 'CANCELLED' WHEN 'B' THEN 'BOUNCED' WHEN 'F' THEN 'FORWARDED' END AS REC_STATUS,TDS_AMOUNT,dbo.DATE_FORMAT(INSTRUMENT_DATE,'%d-%m-%Y'),BM.BANK_NAME,BRM.BANK_BRANCH_NAME,CID.DEPOSIT_BANK_ACCOUNT,CB.BRANCH_DESC from CR_INSTRUMENT_DTL CID inner join CR_LOAN_DTL CLD on CLD.LOAN_ID=CID.TXNID inner join gcd_customer_m gcm on gcm.customer_id= CLD.LOAN_CUSTOMER_ID inner join com_branch_m cbm on cbm.BRANCH_ID= CLD.LOAN_BRANCH LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=CID.DEPOSIT_BANK_ID LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=CID.DEPOSIT_BRANCH_ID LEFT JOIN COM_BRANCH_M CB ON CB.BRANCH_ID=CID.DEFAULT_BRANCH WHERE INSTRUMENT_TYPE='").append(CommonFunction.checkNull(chequeAllocationVo1.getInstrumentTypesearch())).append("' ").toString());
      bufInsSqlCount.append(new StringBuilder().append("SELECT COUNT(1) from CR_INSTRUMENT_DTL CID inner join CR_LOAN_DTL CLD on CLD.LOAN_ID=CID.TXNID inner join gcd_customer_m gcm on gcm.customer_id= CLD.LOAN_CUSTOMER_ID inner join com_branch_m cbm on cbm.BRANCH_ID= CLD.LOAN_BRANCH LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=CID.DEPOSIT_BANK_ID LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=CID.DEPOSIT_BRANCH_ID LEFT JOIN COM_BRANCH_M CB ON CB.BRANCH_ID=CID.DEFAULT_BRANCH WHERE  INSTRUMENT_TYPE='").append(CommonFunction.checkNull(chequeAllocationVo1.getInstrumentTypesearch())).append("' ").toString());

      if (!CommonFunction.checkNull(chequeAllocationVo1.getLbx_loan_from_id()).equalsIgnoreCase(""))
      {
        bufInsSql.append(new StringBuilder().append(" AND TXNID='").append(chequeAllocationVo1.getLbx_loan_from_id()).append("'").toString());
        bufInsSqlCount.append(new StringBuilder().append(" AND TXNID='").append(chequeAllocationVo1.getLbx_loan_from_id()).append("'").toString());
      }
      if ((!CommonFunction.checkNull(chequeAllocationVo1.getFromDate()).equalsIgnoreCase("")) && (!CommonFunction.checkNull(chequeAllocationVo1.getToDate()).equalsIgnoreCase("")))
      {
        bufInsSql.append(new StringBuilder().append(" AND INSTRUMENT_DATE >='").append(CommonFunction.changeFormat(chequeAllocationVo1.getFromDate())).append("'").toString());
        bufInsSql.append(new StringBuilder().append(" AND INSTRUMENT_DATE <='").append(CommonFunction.changeFormat(chequeAllocationVo1.getToDate())).append("'").toString());

        bufInsSqlCount.append(new StringBuilder().append(" AND INSTRUMENT_DATE >='").append(CommonFunction.changeFormat(chequeAllocationVo1.getFromDate())).append("'").toString());
        bufInsSqlCount.append(new StringBuilder().append(" AND INSTRUMENT_DATE <='").append(CommonFunction.changeFormat(chequeAllocationVo1.getToDate())).append("'").toString());
      }
      if (!CommonFunction.checkNull(chequeAllocationVo1.getInstrumentNoSearch()).equalsIgnoreCase(""))
      {
        bufInsSql.append(new StringBuilder().append(" AND INSTRUMENT_NO='").append(chequeAllocationVo1.getInstrumentNoSearch()).append("'").toString());
        bufInsSqlCount.append(new StringBuilder().append(" AND INSTRUMENT_NO='").append(chequeAllocationVo1.getInstrumentNoSearch()).append("'").toString());
      }

      if (!CommonFunction.checkNull(chequeAllocationVo1.getReciptNo()).equalsIgnoreCase(""))
      {
        bufInsSql.append(new StringBuilder().append(" AND RECIPT_NO='").append(chequeAllocationVo1.getReciptNo()).append("'").toString());
        bufInsSqlCount.append(new StringBuilder().append(" AND RECIPT_NO='").append(chequeAllocationVo1.getReciptNo()).append("'").toString());
      }

      if (currentPageLink > 1)
      {
        startRecordIndex = (currentPageLink - 1) * this.no;
        endRecordIndex = this.no;
      }

      bufInsSql.append(" ORDER BY CLD.LOAN_NO OFFSET ");
      bufInsSql.append(startRecordIndex);
      bufInsSql.append(" ROWS FETCH next ");
      bufInsSql.append(endRecordIndex);
      bufInsSql.append(" ROWS ONLY ");

      logger.info(new StringBuilder().append("main query is::::::::::::::::::::").append(bufInsSql.toString()).toString());

      datalist = ConnectionReportDumpsDAO.sqlSelect(bufInsSql.toString());
      bufInsSql = null;
      count = Integer.parseInt(ConnectionReportDumpsDAO.singleReturn(bufInsSqlCount.toString()));
      bufInsSqlCount = null;
      int size = datalist.size();
      if (size > 0)
      {
        for (int i = 0; i < size; i++)
        {
          ArrayList data = (ArrayList)datalist.get(i);
          if (data.size() > 0)
          {
            ChequeAllocationVo chequeAllocationVo = new ChequeAllocationVo();

            chequeAllocationVo.setInstrumentId(new StringBuilder().append("<a href=chequeAllocationReportAction.do?instrumentNo=").append(CommonFunction.checkNull(data.get(0))).append("&reportFormat=").append(CommonFunction.checkNull(chequeAllocationVo1.getReportformat())).append(">").append(CommonFunction.checkNull(data.get(0)).toString()).append("</a>").toString());

            chequeAllocationVo.setLoanNumber(CommonFunction.checkNull(data.get(1)).trim());
            chequeAllocationVo.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
            chequeAllocationVo.setBranch(CommonFunction.checkNull(data.get(3)).trim());
            chequeAllocationVo.setReciptNo(CommonFunction.checkNull(data.get(4)).trim());
            chequeAllocationVo.setInstrumentno(CommonFunction.checkNull(data.get(5)).trim());

            chequeAllocationVo.setInstrumentType(CommonFunction.checkNull(data.get(6)).trim());

            if (!CommonFunction.checkNull(data.get(7)).equalsIgnoreCase(""))
            {
              Number Insamount = CommonFunction.decimalNumberConvert(CommonFunction.checkNull(data.get(7)).trim());
              chequeAllocationVo.setInstrumentAmount(this.myFormatter.format(Insamount));
              Insamount = null;
            }

            chequeAllocationVo.setInstrumentStatus(CommonFunction.checkNull(data.get(8)).trim());
            chequeAllocationVo.setTdsAmount(CommonFunction.checkNull(data.get(9)).trim());
            chequeAllocationVo.setInstrumentDate(CommonFunction.checkNull(data.get(10)).trim());

            chequeAllocationVo.setDepositBank(CommonFunction.checkNull(data.get(11)).trim());
            chequeAllocationVo.setDepositBankBranch(CommonFunction.checkNull(data.get(12)).trim());
            chequeAllocationVo.setDepositBankAccount(CommonFunction.checkNull(data.get(13)).trim());
            chequeAllocationVo.setDefaultBranch(CommonFunction.checkNull(data.get(14)).trim());

            chequeAllocationVo.setTotalRecordSize(count);

            list.add(chequeAllocationVo);
            chequeAllocationVo = null;
          }
          data = null;
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    chequeAllocationVo1 = null;
    return list;
  }

  public String getReceiptReportName()
  {
    logger.info("in  getReceiptReportName().");
    String reportName = "";
    try
    {
      String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='RECEIPT_REPORT'";
      logger.info(new StringBuilder().append("Query for getReceiptReportName :  ").append(query).toString());
      reportName = ConnectionReportDumpsDAO.singleReturn(query);
      logger.info(new StringBuilder().append("Receipt Report Name :  ").append(reportName).toString());
      query = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }return reportName;
  }

  public String getEarlyClosureReport(String loanId) {
    logger.info("in  getEarlyClosureReport().");
    String reportName = "";
    try
    {
      String query = new StringBuilder().append("SELECT EARLY_CLOSURE_REPORT FROM cr_category_cam_mapping A JOIN CR_LOAN_DTL B ON(B.LOAN_PRODUCT_CATEGORY=A.PRODUCT_CATEGORY) WHERE LOAN_ID='").append(CommonFunction.checkNull(loanId).trim()).append("' ").toString();
      logger.info(new StringBuilder().append("Query for getReceiptReportName :  ").append(query).toString());
      reportName = ConnectionReportDumpsDAO.singleReturn(query);
      logger.info(new StringBuilder().append("Early Closure Report Name :  ").append(reportName).toString());
      query = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }return reportName;
  }

  public boolean deleteUserOldRecord(String pUserId) {
    boolean status = false;
    String query = "";
    PrepStmtObject stmt = null;
    ArrayList list = new ArrayList();
    try
    {
      query = new StringBuilder().append("delete from cr_file_tracking_report where USER_ID='").append(CommonFunction.checkNull(pUserId).trim()).append("' ").toString();
      stmt = new PrepStmtObject();
      stmt.setSql(query);
      logger.info(new StringBuilder().append("IN deleteUserOldRecord() Delete query :  ").append(stmt).toString());
      list.add(stmt);
      status = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(list);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      query = null;
      stmt = null;
      list.clear();
    }
    return status;
  }

  public boolean getLoanDetails(String noc_loan_id, String nhb_date)
  {
    StringBuffer sb = new StringBuffer();
    ArrayList qryList = new ArrayList();
    boolean status = false;
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try
    {
      sb.append(new StringBuilder().append("UPDATE CR_LOAN_DTL SET NOC_FLAG='Y',NOC_DATE=dbo.STR_TO_DATE(?,'").append(this.dateFormat).append("') WHERE LOAN_ID=? ").toString());

      if (CommonFunction.checkNull(nhb_date).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(nhb_date);
      }
      if (CommonFunction.checkNull(noc_loan_id).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(noc_loan_id);
      }
      insertPrepStmtObject.setSql(sb.toString());

      logger.info(new StringBuilder().append("IN generate report update query1 for noc  ### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);
      status = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(qryList);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally {
      sb = null;
      insertPrepStmtObject = null;
      qryList.clear();
      qryList = null;
    }
    return status;
  }

  public String getEarlyClosureType()
  {
    logger.info("in  getEarlyClosureType().");
    String closureType = "";
    try
    {
      String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='EARLY_CLOSURE_AT_CS'";
      logger.info(new StringBuilder().append("Query for getting closureType ON CS  :  ").append(query).toString());
      closureType = ConnectionReportDumpsDAO.singleReturn(query);
      logger.info(new StringBuilder().append("Closure Type  : ").append(closureType).toString());
      query = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }return closureType;
  }

  public String getDvReport(String loanId)
  {
    String reportName = "";
    String query = new StringBuilder().append("SELECT DV_REPORT FROM CR_CATEGORY_CAM_MAPPING A JOIN CR_LOAN_DTL B ON(B.LOAN_PRODUCT_CATEGORY=A.PRODUCT_CATEGORY) WHERE B.LOAN_ID = '").append(CommonFunction.checkNull(loanId).trim()).append("' ").toString();
    logger.info(new StringBuilder().append("In getDvReport of ReportDAOImpl   query    :   ").append(query).toString());
    reportName = ConnectionReportDumpsDAO.singleReturn(query);
    return reportName;
  }

  public ArrayList getCmsDownLoadReport(String pCompanyId, String pPrintedDate, String fromDate, String toDate, String asonDate, String puser_id, String reporttype, String reportId, String branchId) {
    logger.info("In getCmsDownLoadReport()");
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    ArrayList result = new ArrayList();
    String s1 = "";
    String s2 = "";
    String error = "";
    try
    {
      in.add(pCompanyId);
      if (CommonFunction.checkNull(pPrintedDate).trim().equalsIgnoreCase(""))
        in.add("0000-00-00");
      else {
        in.add(CommonFunction.changeFormat(pPrintedDate));
      }
      in.add(reportId);
      if (CommonFunction.checkNull(fromDate).trim().equalsIgnoreCase(""))
        in.add("0000-00-00");
      else
        in.add(CommonFunction.changeFormat(fromDate));
      if (CommonFunction.checkNull(toDate).trim().equalsIgnoreCase(""))
        in.add("0000-00-00");
      else {
        in.add(CommonFunction.changeFormat(toDate));
      }
      if (CommonFunction.checkNull(asonDate).trim().equalsIgnoreCase(""))
        in.add("0000-00-00");
      else {
        in.add(CommonFunction.changeFormat(asonDate));
      }
      in.add(CommonFunction.checkNull(puser_id).trim());
      in.add(reporttype);
      in.add(branchId);
      out.add(s1);
      out.add(s2);

      logger.info(new StringBuilder().append("CMS_DOWNLOAD_PROC (").append(in.toString()).append(",").append(out.toString()).append(")").toString());
      outMessages = (ArrayList)ConnectionReportDumpsDAO.callSP("CMS_DOWNLOAD_PROC", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
      logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
      result.add(error);
      result.add(s1);
      result.add(s2);
    }
    catch (Exception e)
    {
      error = "error";
      result.add(error);
      result.add(s1);
      result.add(s2);
      e.printStackTrace();
    }
    finally
    {
      in = null;
      out = null;
      outMessages = null;
      s1 = null;
      s2 = null;
      error = null;
    }
    return result;
  }

  public String getCAMReportAtLoan(String loanId) {
    String reportName = "";
    String query = new StringBuilder().append("select LOAN_CAM_REPORT from cr_category_cam_mapping a join cr_loan_dtl b on(loan_product_category=product_category)where loan_id='").append(CommonFunction.checkNull(loanId).trim()).append("' ").toString();
    logger.info(new StringBuilder().append("In getCAMReportAtLoan of ReportDAOImpl   query    :   ").append(query).toString());
    reportName = ConnectionReportDumpsDAO.singleReturn(query);
    return reportName;
  }

  public String getDealId(String loanId)
  {
    String dealId = "";
    String query = new StringBuilder().append("SELECT LOAN_DEAL_ID FROM CR_LOAN_DTL WHERE LOAN_ID='").append(CommonFunction.checkNull(loanId).trim()).append("' ").toString();
    logger.info(new StringBuilder().append("In getDealId of ReportDAOImpl   query    :   ").append(query).toString());
    dealId = ConnectionReportDumpsDAO.singleReturn(query);
    return dealId;
  }

  public String getSanctionReport(String loanId)
  {
    String reportName = "";
    String query = new StringBuilder().append("select sanction_letter from cr_category_cam_mapping a join cr_loan_dtl b on(loan_product_category=product_category)where loan_id='").append(CommonFunction.checkNull(loanId).trim()).append("' ").toString();
    logger.info(new StringBuilder().append("In getCAMReport of ReportDAOImpl   query    :   ").append(query).toString());
    reportName = ConnectionDAO.singleReturn(query);
    return reportName;
  }

  public String getWelcomeReportQry(String loanDate, String disbursalId, String fromDisbursalDate, String toDisbursalDate, String productCategory, String source)
  {
    StringBuffer qry = new StringBuffer();
    qry.append(" select  a.LOAN_ID,LOAN_DISBURSAL_ID,DISBURSAL_DATE,DISBURSAL_FLAG,com.COMPANY_DESC,com.phone_no,com.email_id,");
    qry.append(" concat(ifnull(APPLICANT_WELCOME_REPORT,'au_sub_welcome_report'),'.jasper')welcomeReport");
    qry.append(" from cr_loan_disbursal_dtl a");
    qry.append(" join cr_loan_dtl b on(a.loan_id=b.loan_id)");
    if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("B"))
      qry.append(" join BULK_REPORT_GENERATION_TMP bulk on(bulk.WL_LOAN_ID=b.loan_id) ");
    qry.append(" left join cr_category_cam_mapping c on(b.LOAN_PRODUCT_CATEGORY=c.PRODUCT_CATEGORY)");
    qry.append(" left join (select COMPANY_DESC,phone_no,email_id from com_company_m where company_id=1)com on(true)");
    qry.append(" WHERE bulk.upload_flag='Y'");
    if (!CommonFunction.checkNull(source).trim().equalsIgnoreCase("B"))
    {
      qry.append(" where IF($P{loan_date}='L',LOAN_DISBURSAL_ID=$P{disbursal_id},(DATE(DISBURSAL_DATE)>=STR_TO_DATE($P{p_Approval_date_from},$P{p_date_format}))");
      qry.append(" AND(DATE(DISBURSAL_DATE)<=STR_TO_DATE($P{p_Approval_date_to},$P{p_date_format}))) and a.rec_status='A' and b.rec_status='A'");
      qry.append(" and (($P{PRODUCT_CATEGORY}='' and 'a'='a')or(b.LOAN_PRODUCT_CATEGORY=$P{PRODUCT_CATEGORY}))");
    }
    logger.info(new StringBuilder().append("Final query : ").append(qry.toString()).toString());
    return qry.toString();
  }

  public String getForeClosureReportQry() {
    StringBuffer qry = new StringBuffer();
    qry.append("select fc_loan_id LOAN_ID from BULK_REPORT_GENERATION_TMP where fc_category<>'NA'");
    logger.info(new StringBuilder().append("Final query : ").append(qry.toString()).toString());
    return qry.toString();
  }

  public String getWlGuaranterReportQry()
  {
    StringBuffer qry = new StringBuffer();
    qry.append("SELECT d.gcd_id GCD_ID FROM BULK_REPORT_GENERATION_TMP a ");
    qry.append("LEFT JOIN cr_category_cam_mapping b on(a.FC_CATEGORY=b.PRODUCT_CATEGORY) ");
    qry.append("left join  cr_loan_dtl c on c.loan_id=a.wl_loan_id ");
    qry.append("left join  cr_deal_customer_role d on d.deal_id=c.loan_deal_id WHERE fc_category<>'NA'group by a.wl_loan_Id");
    logger.info(new StringBuilder().append("Final query : ").append(qry.toString()).toString());
    return qry.toString();
  }

  public boolean updateReportGenerationFlag()
  {
    logger.info("to update report generation flag..");
    ArrayList updatelist = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    boolean status = false;
    String stat = "";
    try {
      StringBuffer bufInsSql = new StringBuffer();
      bufInsSql.append(" update bulk_report_generation_tmp set REPORT_GENERAT_FLAG='N' ");
      insertPrepStmtObject.setSql(bufInsSql.toString());
      updatelist.add(insertPrepStmtObject);
      logger.info(new StringBuilder().append("to update report generation flag..").append(bufInsSql).toString());
      status = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(updatelist);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return status;
  }

  public boolean checkReportGeneration()
  {
    logger.info("to update report generation flag..");
    boolean status = false;
    String stat = "";
    try {
      String sqlquery1 = " SELECT  BULK_REPORT_ID FROM bulk_report_generation_tmp WHERE REPORT_GENERAT_FLAG='Y'";
      logger.info(new StringBuilder().append("to update report generation flag..").append(sqlquery1).toString());
      status = ConnectionReportDumpsDAO.checkStatus(sqlquery1);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    logger.info(new StringBuilder().append("status of report flage is ").append(status).toString());
    return status;
  }

  public boolean checkUpdateFlag()
  {
    logger.info("to update flag..");
    boolean status = false;
    String stat = "";
    try {
      String sqlquery1 = " SELECT UPLOAD_FLAG FROM bulk_report_generation_tmp WHERE UPLOAD_FLAG='Y'";
      logger.info(new StringBuilder().append("query to check update flag..").append(sqlquery1).toString());
      status = ConnectionReportDumpsDAO.checkStatus(sqlquery1);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    logger.info(new StringBuilder().append("status of update flag  is ").append(status).toString());
    return status;
  }

  public void saveFunctionLogData(String userId, String moduleID, String functionId, String accessDate, String ipAddress, String sessionNo, String reportName, String reportParam)
  {
    logger.info("Inside MSSQLReportsDAOImpl.............saveFunctionLogData()");
    String dateFormatWithTime = this.resource.getString("lbl.dateWithTimeInDao");
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    StringBuffer bufInsSql = new StringBuffer();

    ArrayList qryList = new ArrayList();
    try {
      String qry = "select convert(varchar(8),getdate(),108)";
      String bTime = ConnectionReportDumpsDAO.singleReturn(qry);
      accessDate = accessDate.concat(new StringBuilder().append(" ").append(bTime).toString());

      bufInsSql.append("insert into SEC_USER_FUNCTION_LOG(USER_ID,MODULE_ID,FUNCTION_ID,ACCESS_DATE,IP_ADDRESS,SESSION_NO,REPORT_NAME,REPORT_PARAM)");
      bufInsSql.append(" values ( ");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(this.dbo);
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(dateFormatWithTime).append("'),").toString());
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ? )");

      if (CommonFunction.checkNull(userId).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(userId).trim());
      }
      if (CommonFunction.checkNull(moduleID).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(moduleID.trim());
      }

      if (CommonFunction.checkNull(functionId).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(functionId.trim());
      }
      if (CommonFunction.checkNull(accessDate).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(accessDate.trim());
      }
      insertPrepStmtObject.addNull();

      insertPrepStmtObject.addNull();

      if (CommonFunction.checkNull(reportName).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(reportName.trim());
      }
      insertPrepStmtObject.addString(reportParam);

      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN saveUserFunctionLog() from reports insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);
      boolean status1 = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In saveUserFunctionLog from Reports......................").append(status1).toString());
    }
    catch (Exception e)
    {
      logger.info(new StringBuilder().append("In saveUserFunctionLog......................").append(e).toString());
    }
    finally
    {
      insertPrepStmtObject = null;
      qryList = null;
      bufInsSql = null;
      dateFormatWithTime = null;
      moduleID = null;
    }
  }

  public ArrayList<ReportsForm> getProductName()
  {
    ArrayList productlist = new ArrayList();
    ReportsForm reportName = null;
    try {
      logger.info("In getProductName..........................DAOImpl");
      String query = "select PRODUCT_CATEGORY,PRODUCT_CATEGORY_DESC from CR_PRODUCTCATEGORY_M";

      logger.info(new StringBuilder().append("query....... ").append(query).toString());
      ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      logger.info(new StringBuilder().append("getProductName()----> ").append(formatlist.size()).toString());
      for (int i = 0; i < formatlist.size(); i++)
      {
        ArrayList data = (ArrayList)formatlist.get(i);
        if (data.size() > 0)
        {
          reportName = new ReportsForm();
          reportName.setProducTCategory(data.get(0).toString());
          reportName.setProducTCategoryID(data.get(1).toString());
          productlist.add(reportName);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return productlist;
  }

  public ArrayList<ReportsForm> getLoanClassification()
  {
    ArrayList productlist = new ArrayList();
    ReportsForm reportName = null;
    try {
      logger.info("In getProductName..........................DAOImpl");
      String query = "select value,description from generic_master where generic_key='sale_off_flag' ";

      logger.info(new StringBuilder().append("query....... ").append(query).toString());
      ArrayList formatlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
      logger.info(new StringBuilder().append("getProductName()----> ").append(formatlist.size()).toString());
      for (int i = 0; i < formatlist.size(); i++)
      {
        ArrayList data = (ArrayList)formatlist.get(i);
        if (data.size() > 0)
        {
          reportName = new ReportsForm();
          reportName.setProducTCategory(data.get(0).toString());
          reportName.setProducTCategoryID(data.get(1).toString());
          productlist.add(reportName);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return productlist;
  }

  public ArrayList getForClosureLoanId()
  {
    ArrayList loanIdlist = new ArrayList();
    ArrayList loanId = new ArrayList();
    StringBuffer qry = new StringBuffer();
    try
    {
      qry.append("select fc_loan_id LOAN_ID from BULK_REPORT_GENERATION_TMP where fc_category<>'NA'");
      logger.info(new StringBuilder().append("query....... ").append(qry).toString());
      loanIdlist = ConnectionReportDumpsDAO.sqlSelect(qry.toString());
      logger.info(new StringBuilder().append("loanIdlist  :: ").append(loanIdlist).toString());
      for (int i = 0; i < loanIdlist.size(); i++)
      {
        ArrayList data = (ArrayList)loanIdlist.get(i);
        if (data.size() > 0)
        {
          loanId.add(data.get(0));
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return loanId;
  }

  public boolean deleteTerminationDtl(String userId)
  {
    boolean status = false;
    String query = "";
    PrepStmtObject stmt = null;
    ArrayList list = new ArrayList();
    try
    {
      query = new StringBuilder().append(" DELETE FROM cr_terminatin_dtl_temp WHERE USER_ID='").append(CommonFunction.checkNull(userId).trim()).append("' ").toString();
      stmt = new PrepStmtObject();
      stmt.setSql(query);
      logger.info(new StringBuilder().append("IN deleteTerminationDtl() Delete query :  ").append(stmt).toString());
      list.add(stmt);
      status = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(list);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      query = null;
      stmt = null;
      list.clear();
    }
    return status;
  }

  public String getSnoForGoldLoanReport(String pLoanId, String userId, String nhbDate)
  {
    ArrayList result = new ArrayList();
    logger.info("In getSnoForGoldLoanReport() of ReportDAOImpl.   ");
    String queryResult = "";
    boolean status = false;
    int count = 1;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = null;
    try
    {
      String query = new StringBuilder().append("select count(1) from cr_duplicate_gold_loan_dtl where loan_id='").append(CommonFunction.checkNull(pLoanId).trim()).append("'").toString();
      logger.info(new StringBuilder().append("In getSnoForGoldLoanReport() of ReportDAOImpl.   query    :   ").append(query).toString());
      count = Integer.parseInt(ConnectionReportDumpsDAO.singleReturn(query));
      logger.info(new StringBuilder().append("In getSnoForGoldLoanReport() of ReportDAOImpl.   count    :   ").append(count).toString());
      if (count == 0)
      {
        queryResult = "Original ";
        insertPrepStmtObject = new PrepStmtObject();
        query = new StringBuilder().append("insert into CR_DUPLICATE_GOLD_LOAN_DTL(LOAN_ID,USER_ID,REPORT_DATE) values(?,?,DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND))").toString();
        if (CommonFunction.checkNull(pLoanId).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(pLoanId.trim());
        if (CommonFunction.checkNull(userId).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(userId.trim());
        if (CommonFunction.checkNull(nhbDate).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(nhbDate.trim());
        }
        insertPrepStmtObject.setSql(query);
        logger.info(new StringBuilder().append("print query.....").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In getSnoForGoldLoanReport() of ReportDAOImpl.   status    :   ").append(status).toString());
      }
      else
      {
        queryResult = "Duplicate ";
        status = true;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return queryResult;
  }

  public String getcamTemplatePath() {
    return null;
  }

  public String CibilIndividualFileGenerator(String fromDate, String userId)
  {
    return null;
  }

  public String CibilCorporateFileGenerator(String fromDate, String userId)
  {
    return null;
  }

  public String CibilFileNameGenerator(String fromDate)
  {
    return null;
  }

@Override
public ArrayList generateSOAForCancelLoan(int paramInt, String paramString1,
		String paramString2, String paramString3, String paramString4,
		String paramString5, String paramString6, String paramString7) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String CibilCorporateLCFileGenerator(String paramString1,
		String paramString2) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList NeslFileGenerator(String paramString1,
		String paramString2) {
	// TODO Auto-generated method stub
 	return null;
}

@Override
public ArrayList NeslLCFileGenerator(String paramString1,
		String paramString2) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList fetchInstlDate(String paramString1, String paramString2) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList fetchLoanList(String paramString1, String paramString2) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList fetchPrinAmtList(String paramString, ArrayList paramArrayList) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList fetchIntAmtList(String paramString, ArrayList paramArrayList) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList fetchAmtList(String paramString1, String paramString2,
		String paramString3) {
	// TODO Auto-generated method stub
	return null;
}
}