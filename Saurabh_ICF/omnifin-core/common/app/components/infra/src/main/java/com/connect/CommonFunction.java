package com.connect;

import com.cp.vo.CaseMovementVo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.commons.lang.StringUtils;

public class CommonFunction
{
  private static final Logger logger = Logger.getLogger(CommonFunction.class.getName());
  static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
  static String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
  String dateFormat = resource.getString("lbl.dateInDao");
  String dateForDisbursal = resource.getString("lbl.dateForDisbursal");

  String dbType = resource.getString("lbl.dbType");
  String dbo = resource.getString("lbl.dbPrefix");

  String singleRowNumber = resource.getString("lbl.singleRowNumber");
  String doubleRowNumber = resource.getString("lbl.doubleRowNumber");
  String between = resource.getString("lbl.between");

  DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");

  public static String checkNull(Object requestString) {
    if (requestString != null) {
      return requestString.toString();
    }
    requestString = null;
    return "";
  }

  public static String checkNull(HSSFCell cell)
  {
    String strValue = null;
    Double amountValue = null;

    if ((cell != null) && (cell.getCellType() == 3)) {
      strValue = "";
    } else if ((cell != null) && (cell.getCellType() == 2))
    {
      int cachedFormulaResultType = cell.getCachedFormulaResultType();
      if (0 == cachedFormulaResultType)
      {
        amountValue = Double.valueOf(cell.getNumericCellValue());
        if (amountValue != null) {
          strValue = new BigDecimal(amountValue.doubleValue()).setScale(2, RoundingMode.FLOOR).toPlainString();
        }
      }
      else
      {
        strValue = cell.getStringCellValue().replaceAll("'", "");
      }
    }
    else if ((cell != null) && (cell.getCellType() == 0)) {
      amountValue = Double.valueOf(cell.getNumericCellValue());
      if (amountValue != null)
        strValue = new BigDecimal(amountValue.doubleValue()).setScale(2, RoundingMode.FLOOR).toPlainString();
    }
    else if (cell != null) {
      strValue = cell.toString();
    }

    if (strValue != null) {
      return strValue.trim();
    }
    strValue = null;
    return "";
  }

  public static String checkNull(Cell cell)
  {
    String strValue = null;
    Double amountValue = null;

    if ((cell != null) && (cell.getCellType() == 3)) {
      strValue = "";
    } else if ((cell != null) && (cell.getCellType() == 2))
    {
      int cachedFormulaResultType = cell.getCachedFormulaResultType();
      if (0 == cachedFormulaResultType)
      {
        amountValue = Double.valueOf(cell.getNumericCellValue());
        if (amountValue != null) {
          strValue = new BigDecimal(amountValue.doubleValue()).setScale(2, RoundingMode.FLOOR).toPlainString();
        }
      }
      else
      {
        strValue = cell.getStringCellValue().replaceAll("'", "");
      }
    }
    else if ((cell != null) && (cell.getCellType() == 0)) {
      amountValue = Double.valueOf(cell.getNumericCellValue());
      if (amountValue != null)
        strValue = new BigDecimal(amountValue.doubleValue()).setScale(2, RoundingMode.FLOOR).toPlainString();
    }
    else if (cell != null) {
      strValue = cell.toString();
    }

    if (strValue != null) {
      return strValue.trim();
    }
    strValue = null;
    return "";
  }

  public static String checkNullForDate(Cell cell)
  {
    String strValue = null;
    java.util.Date dateValue = null;
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

    if (cell != null) {
      strValue = cell.toString();
    }

    logger.info(new StringBuilder().append("date value---").append(strValue).toString());

    if (strValue != null) {
      return strValue.trim();
    }
    strValue = null;
    return "";
  }

  public static boolean check(String date)
  {
    boolean status = false;
    if ((date != null) && (date.trim().length() == 10))
    {
      int i = 0;
      for (i = 0; i < 10; i++)
      {
        if ((i == 2) || (i == 5))
        {
          if (date.charAt(i) != '-')
            return status;
        }
        else
        {
          if ((date.charAt(i) < '0') || (date.charAt(i) > '9'))
            return status;
          if ((date.charAt(0) == '0') && (date.charAt(1) == '0'))
            return status;
          if ((date.charAt(3) == '0') && (date.charAt(4) == '0'))
            return status;
          if ((date.charAt(6) == '0') && (date.charAt(7) == '0') && (date.charAt(8) == '0') && (date.charAt(9) == '0'))
            return status;
        }
      }
      if (i == 10)
        status = true;
    }
    date = null;
    return status;
  }

  public static boolean check2(String date) {
    boolean status = false;
    if ((date != null) && (date.trim().length() == 10))
    {
      int i = 0;
      for (i = 0; i < 10; i++)
      {
        if ((i == 4) || (i == 7))
        {
          if (date.charAt(i) != '-')
            return status;
        }
        else
        {
          if ((date.charAt(i) < '0') || (date.charAt(i) > '9'))
            return status;
          if ((date.charAt(8) == '0') && (date.charAt(9) == '0'))
            return status;
          if ((date.charAt(5) == '0') && (date.charAt(6) == '0'))
            return status;
          if ((date.charAt(0) == '0') && (date.charAt(1) == '0') && (date.charAt(2) == '0') && (date.charAt(3) == '0'))
            return status;
        }
      }
      if (i == 10)
        status = true;
    }
    date = null;
    return status;
  }

  public static String changeFormat(String strDate) {
    String date = "";
    boolean status = false;
    status = check(strDate);
    if (status) {
      date = new StringBuilder().append(strDate.substring(6)).append("-").append(strDate.substring(3, 5)).append("-").append(strDate.substring(0, 2)).toString();
    }
    strDate = null;
    return date;
  }

  public static String changeFormatJSP(String strDate) {
    String date = "";
    boolean status = false;
    status = check2(strDate);
    if (status) {
      date = new StringBuilder().append(strDate.substring(8)).append("-").append(strDate.substring(5, 7)).append("-").append(strDate.substring(0, 4)).toString();
    }
    strDate = null;
    return date;
  }

  public static String dateFormatConvert(String strDate)
    throws ParseException
  {
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");
    SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
    String conDate = sdf1.format(Long.valueOf(sdf.parse(strDate).getTime()));
    strDate = null;
    sdf = null;
    sdf1 = null;
    return conDate;
  }
  public static String editableFlag() {
    String editableEuery = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EDITABLE_FLAG'";
    String flag = ConnectionDAO.singleReturn(editableEuery);
    editableEuery = null;
    return flag;
  }

  public static boolean insertsecurity(String loanId) {
    boolean status = false;

    ArrayList qryList = new ArrayList();
    ArrayList subList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    StringBuffer bufInsSql = null;
    String chk = null;
    String query = null;
    String dealId = null;
    StringBuilder bufInsQuery = null;
    String sdQuery = null;
    ArrayList mainlist = null;
    ArrayList dataSd = null;
    String freq = null;
    String rate = null;
    String tenure = null;
    try
    {
      query = new StringBuilder().append("select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID=").append(loanId).toString();

      logger.info(new StringBuilder().append("LOAN_DEAL_ID from cr_loan_dtl query ").append(query).toString());

      dealId = ConnectionDAO.singleReturn(query);

      bufInsQuery = new StringBuilder();
      bufInsQuery.append("select ");
      String dbType = resource.getString("lbl.dbType");
      String dbo = resource.getString("lbl.dbPrefix");
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        bufInsQuery.append("TOP 1 ");
      }
      bufInsQuery.append(new StringBuilder().append("DEAL_CHARGE_FINAL_AMOUNT from  cr_loan_dtl L,cr_txncharges_dtl C where L.LOAN_ID=").append(loanId).append(" and L.LOAN_ID=C.TXN_ID and c.DEAL_CHARGE_CODE=103 and TXN_TYPE='LIM' ").toString());
      if ((!dbType.equalsIgnoreCase("MSSQL")) && (dbo.equalsIgnoreCase("")))
      {
        bufInsQuery.append("LIMIT 1");
      }
      logger.info(new StringBuilder().append("SD Amount from cr_txncharges_dtl query ").append(bufInsQuery.toString()).toString());
      String sdAmount = ConnectionDAO.singleReturn(bufInsQuery.toString());

      sdQuery = new StringBuilder().append("select SD_COMPOUNDING_FREQ,SD_INTEREST_RATE,SD_INTEREST_TYPE,SD_TENURE,SD_INTEREST,REC_STATUS,MAKER_ID,MAKER_DATE,SD_ADJUSTMENT from cr_deal_sd_m where DEAL_ID='").append(dealId).append("'").toString();

      logger.info(new StringBuilder().append("LOAN_DEAL_ID from cr_deal_sd_m query ").append(sdQuery).toString());
      mainlist = ConnectionDAO.sqlSelect(sdQuery);

      if ((mainlist != null) && (mainlist.size() > 0))
      {
        double totalInt = 0.0D;
        int freqMonth = 0;
        int reqFreq = 0;
        dataSd = (ArrayList)mainlist.get(0);
        freq = checkNull(dataSd.get(0)).trim();
        rate = checkNull(dataSd.get(1)).trim();
        tenure = checkNull(dataSd.get(3)).trim();

        if (checkNull(dataSd.get(2)).equalsIgnoreCase("S"))
        {
          if ((!sdAmount.equalsIgnoreCase("")) && (!tenure.equalsIgnoreCase("")) && (!rate.equalsIgnoreCase(""))) {
            totalInt = Double.parseDouble(sdAmount) * Double.parseDouble(rate) * Double.parseDouble(tenure) / 1200.0D;
          }
        }

        if (checkNull(dataSd.get(2)).equalsIgnoreCase("C"))
        {
          if (freq.equalsIgnoreCase("M"))
          {
            freqMonth = 1;
          }
          else if (freq.equalsIgnoreCase("B"))
          {
            freqMonth = 2;
          }
          else if (freq.equalsIgnoreCase("Q"))
          {
            freqMonth = 3;
          }
          else if (freq.equalsIgnoreCase("H"))
          {
            freqMonth = 6;
          }
          else if (freq.equalsIgnoreCase("Y"))
          {
            freqMonth = 12;
          }
          if (freqMonth != 0)
          {
            reqFreq = 12 / freqMonth;
          }

          if ((!sdAmount.equalsIgnoreCase("")) && (!tenure.equalsIgnoreCase("")) && (!rate.equalsIgnoreCase(""))) {
            totalInt = Double.parseDouble(sdAmount) * Math.pow(1.0D + Double.parseDouble(rate) / (reqFreq * 100), Double.parseDouble(tenure) * reqFreq / 12.0D) - Double.parseDouble(sdAmount);
          }

        }

        query = new StringBuilder().append("select count(TXN_ID) from cr_sd_m where TXN_ID=").append(loanId).toString();
        chk = ConnectionDAO.singleReturn(query);

        if (Integer.parseInt(chk) <= 0)
        {
          bufInsSql = new StringBuffer();
          bufInsSql.append("insert into cr_sd_m(TXN_TYPE,TXN_ID,SD_AMOUNT,SD_INTEREST_TYPE,SD_INTEREST_RATE,SD_COMPOUNDING_FREQ,SD_TENURE,SD_INTEREST,SD_ADJUSTMENT,REC_STATUS,MAKER_ID,MAKER_DATE)");

          bufInsSql.append(" values (");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ? )");
          insertPrepStmtObject.addString("LIM");
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(loanId).trim()));
          if (checkNull(sdAmount).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(sdAmount).trim()));
          }
          int size = mainlist.size();
          for (int i = 0; i < size; i++)
          {
            subList = (ArrayList)mainlist.get(i);

            if (subList.size() > 0)
            {
              if (StringEscapeUtils.escapeHtml(checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
              else {
                insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(2)).trim()));
              }
              if (StringEscapeUtils.escapeHtml(checkNull(subList.get(1)).trim()).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
              else
                insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(1)).trim()));
              if (StringEscapeUtils.escapeHtml(checkNull(subList.get(0)).trim()).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
              else
                insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(0)).trim()));
              if (StringEscapeUtils.escapeHtml(checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
              else
                insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(3)).trim()));
              if (StringEscapeUtils.escapeHtml(checkNull(Double.valueOf(totalInt)).trim()).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
              else
                insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(Double.valueOf(totalInt)).trim()));
              if (StringEscapeUtils.escapeHtml(checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
              else {
                insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(8)).trim()));
              }
              if (StringEscapeUtils.escapeHtml(checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
              else {
                insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(5)).trim()));
              }
              if (StringEscapeUtils.escapeHtml(checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
              else
                insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(6)).trim()));
              if (StringEscapeUtils.escapeHtml(checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
              else {
                insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(7)).trim()));
              }
            }
          }
          insertPrepStmtObject.setSql(bufInsSql.toString());
          logger.info(new StringBuilder().append("insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
          qryList.add(insertPrepStmtObject);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
          logger.info(new StringBuilder().append("status......................").append(status).toString());
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
      e = null;
    }
    finally
    {
      loanId = null;
      insertPrepStmtObject = null;
      qryList.clear();
      qryList = null;
      bufInsSql = null;
      chk = null;
      query = null;
      dealId = null;
      bufInsQuery = null;
      sdQuery = null;
      mainlist = null;
      dataSd = null;
      freq = null;
      rate = null;
      tenure = null;
    }

    return status;
  }

  public static boolean insertInstallment(int loanId)
  {
    boolean status = false;
    ArrayList qryList = new ArrayList();
    ArrayList subList = null;
    String query = null;
    StringBuffer bufInsSql = null;
    PrepStmtObject insertPrepStmtObject = null;
    String dealId = null;
    String sdQuery = null;
    ArrayList mainlist = null;
    try
    {
      query = new StringBuilder().append("select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID=").append(loanId).toString();

      logger.info(new StringBuilder().append("LOAN_DEAL_ID from cr_loan_dtl query ").append(query).toString());

      dealId = ConnectionDAO.singleReturn(query);

      sdQuery = new StringBuilder().append("select SEQ_NO,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE,RECOVERY_TYPE from cr_deal_installment_plan where DEAL_ID='").append(dealId).append("'").toString();

      logger.info(new StringBuilder().append(" from cr_deal_installment_plan query ").append(sdQuery).toString());
      mainlist = ConnectionDAO.sqlSelect(sdQuery);
      logger.info(new StringBuilder().append("LOAN_DEAL_ID from cr_deal_sd_m query ").append(mainlist).toString());

      query = new StringBuilder().append("delete from cr_installment_plan where LOAN_ID=").append(loanId).toString();
      ArrayList delQ = new ArrayList();
      delQ.add(query);
      boolean st = ConnectionDAO.sqlInsUpdDelete(delQ);
      logger.info(new StringBuilder().append("delete installment plan: ").append(query).append(" status: ").append(st).toString());

      if (mainlist != null)
      {
        int max = mainlist.size();

        for (int i = 0; i < max; i++)
        {
          bufInsSql = new StringBuffer();
          insertPrepStmtObject = new PrepStmtObject();
          bufInsSql.append("insert into cr_installment_plan(LOAN_ID,SEQ_NO,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE,RECOVERY_TYPE)");

          bufInsSql.append(" values (");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ? )");

          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(Integer.valueOf(loanId)).trim()));

          subList = (ArrayList)mainlist.get(i);

          if (subList.size() > 0)
          {
            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(0)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else {
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(0)).trim()));
            }
            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(1)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(1)).trim()));
            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(2)).trim()));
            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(3)).trim()));
            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(4)).trim()));
            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(5)).trim()));
            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(6)).trim()));
            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else {
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(7)).trim()));
            }
            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else {
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(8)).trim()));
            }

            if (StringEscapeUtils.escapeHtml(checkNull(subList.get(9)).trim()).equalsIgnoreCase(""))
              insertPrepStmtObject.addNull();
            else {
              insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(checkNull(subList.get(9)).trim()));
            }

          }

          insertPrepStmtObject.setSql(bufInsSql.toString());
          logger.info(new StringBuilder().append("insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
          qryList.add(insertPrepStmtObject);
        }

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("status......................").append(status).toString());
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      query = null;
      qryList.clear();
      qryList = null;
      insertPrepStmtObject = null;
      bufInsSql = null;
      dealId = null;
      sdQuery = null;
      mainlist = null;
      subList = null;
    }

    return status;
  }

  public static String stageMovement(String companyId, String txnType, String action, String dealId, String stage, String bDate, String makerId)
  {
    String s1 = "";
    String s2 = "";
    String status = "";
    String productType = "";
    String dbType = resource.getString("lbl.dbType");
    String dbo = resource.getString("lbl.dbPrefix");
    String chekQ = "";
    String checkCount = "";
    StringBuilder pTypeQ = null;
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    try
    {
      if (action.equalsIgnoreCase("I"))
      {
        productType = "SME";
      }
      else
      {
        pTypeQ = new StringBuilder();
        pTypeQ.append("select ");
        if (dbType.equalsIgnoreCase("MSSQL"))
        {
          pTypeQ.append("TOP 1 ");
        }
        pTypeQ.append(new StringBuilder().append("DEAL_PRODUCT_CATEGORY from cr_deal_loan_dtl where DEAL_ID=").append(dealId.trim()).append(" ").toString());
        if ((!dbType.equalsIgnoreCase("MSSQL")) && (dbo.equalsIgnoreCase("")))
        {
          pTypeQ.append("LIMIT 1");
        }
        productType = ConnectionDAO.singleReturn(pTypeQ.toString());
      }
      in.add(companyId);
      in.add(txnType);
      in.add(dealId);
      in.add(productType);
      in.add(action);
      in.add(stage);
      in.add(changeFormat(bDate));
      in.add(makerId);
      out.add(s1);
      out.add(s2);
      try
      {
        logger.info(new StringBuilder().append("Stage_Movement(").append(in.toString()).append(",").append(out.toString()).toString());
        outMessages = (ArrayList)ConnectionDAO.callSP("Stage_Movement", in, out);
        s1 = checkNull(outMessages.get(0));
        s2 = checkNull(outMessages.get(1));
        logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
        logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
        if ((s1 != null) && (s1.equalsIgnoreCase("S")))
        {
          status = s1;
          chekQ = new StringBuilder().append("select count(POLICY_DECISION_ID) from cr_policy_decision \twhere DEAL_ID ='").append(dealId).append("' and RULE_ACTION = 'S'").toString();
          checkCount = ConnectionDAO.singleReturn(chekQ);
          if (!checkNull(checkCount).equalsIgnoreCase("0"))
          {
            s1 = "E";
            status = "SOME RULES ARE FAILED, PLEASE CHECK";
          }
        }
        else
        {
          status = s2;
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      s1 = null;
      s2 = null;
      productType = null;
      dbType = null;
      dbo = null;
      chekQ = null;
      checkCount = null;
      pTypeQ = null;
      in.clear();
      out.clear();
      in = null;
      out = null;
      outMessages.clear();
      outMessages = null;
    }
    return status;
  }

  public static String stageMovementOld(String companyId, String txnType, String action, String dealId, String stage, String bDate, String makerId) {
    CallableStatement cst = null;
    String s1 = null;
    String s2 = null;
    String status = null;
    String productType = null;
    Connection con = ConnectionDAO.getConnection();
    String dbType = resource.getString("lbl.dbType");
    String dbo = resource.getString("lbl.dbPrefix");
    String chekQ = null;
    String checkCount = null;
    StringBuilder bufSql = null;
    StringBuilder pTypeQ = null;
    try {
      con.setAutoCommit(false);
      if (action.equalsIgnoreCase("I"))
      {
        productType = "SME";
      }
      else
      {
        pTypeQ = new StringBuilder();
        pTypeQ.append("select ");
        if (dbType.equalsIgnoreCase("MSSQL"))
        {
          pTypeQ.append("TOP 1 ");
        }
        pTypeQ.append(new StringBuilder().append("DEAL_PRODUCT_CATEGORY from cr_deal_loan_dtl where DEAL_ID=").append(dealId.trim()).append(" ").toString());
        if ((!dbType.equalsIgnoreCase("MSSQL")) && (dbo.equalsIgnoreCase("")))
        {
          pTypeQ.append("LIMIT 1");
        }

        productType = ConnectionDAO.singleReturn(pTypeQ.toString());
      }

      logger.info(new StringBuilder().append("In stageMovement: action(Insert/Forward) ").append(action).append(" dealId: ").append(dealId).append(" stage: ").append(stage).append(" maker Id: ").append(makerId).append(" bDate: ").append(bDate).append(" txnType: ").append(txnType).append("companyId: ").append(companyId).append("productType: ").append(productType).toString());

      bufSql = new StringBuilder();

      bufSql.append("{call Stage_Movement(?,?,?,?,?,?,?,?,?,?)}");
      cst = con.prepareCall(bufSql.toString());
      cst.setString(1, companyId);
      cst.setString(2, txnType);
      cst.setString(3, dealId);
      cst.setString(4, productType);
      cst.setString(5, action);
      cst.setString(6, stage);
      cst.setString(7, changeFormat(bDate));
      cst.setString(8, makerId);
      cst.registerOutParameter(9, 1);
      cst.registerOutParameter(10, 1);

      cst.execute();
      s1 = cst.getString(9);
      s2 = cst.getString(10);
      if ((s1 != null) && (s1.equalsIgnoreCase("S")))
      {
        status = s1;
        con.commit();
        chekQ = new StringBuilder().append("select count(POLICY_DECISION_ID) from cr_policy_decision \twhere DEAL_ID ='").append(dealId).append("' and RULE_ACTION = 'S'").toString();
        checkCount = ConnectionDAO.singleReturn(chekQ);
        if (!checkNull(checkCount).equalsIgnoreCase("0"))
        {
          s1 = "E";
          status = "SOME RULES ARE FAILED, PLEASE CHECK";
        }

      }
      else
      {
        status = s2;
        con.rollback();
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
      e = null;
    }
    finally
    {
      try
      {
        cst.close();
        con.close();
        cst = null;
        con = null;
      }
      catch (SQLException e) {
        e.printStackTrace();
        e = null;
      }
      companyId = null;
      txnType = null;
      action = null;
      dealId = null;
      stage = null;
      bDate = null;
      makerId = null;
      s1 = null;
      s2 = null;
      chekQ = null;
      checkCount = null;
      bufSql = null;
      pTypeQ = null;
      productType = null;
    }

    return status;
  }

  public static boolean isParsableToInt(String i)
  {
    try
    {
      Integer.parseInt(i);
      i = null;
      return true;
    }
    catch (NumberFormatException nfe)
    {
      nfe = null;
    }return false;
  }

  public static Object removePadding(Object number)
  {
    Object minVal = number;
    if (!checkNull(number).equalsIgnoreCase(""))
    {
      String num = number.toString();

      minVal = Integer.valueOf(Integer.parseInt(num));
    }

    number = null;
    return minVal;
  }

  public static String loanStageMovement(String loanId, String txnType, String bDate, String makerId) {
    CallableStatement cst = null;
    String s1 = null;
    String s2 = null;
    String status = null;
    String chekQ = null;
    String checkCount = null;
    StringBuilder bufSql = null;
    String dbType = resource.getString("lbl.dbType");

    Connection con = ConnectionDAO.getConnection();
    try
    {
      con.setAutoCommit(false);

      logger.info(new StringBuilder().append("In loanStageMovement: action(Insert/Forward) loanId: ").append(loanId).append(" txnType: ").append(txnType).append(" maker Id: ").append(makerId).append(" bDate: ").append(bDate).toString());
      bufSql = new StringBuilder();
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        bufSql.append("{call Loan_Stage_Movement(?,?,?,?,?,?)}");
      }
      else
      {
        bufSql.append(new StringBuilder().append("{call Loan_Stage_Movement(?,?,STR_TO_DATE(?,'").append(dateFormatWithTime).append("'),?,?,?)}").toString());
      }

      cst = con.prepareCall(bufSql.toString());
      cst.setString(1, loanId);
      cst.setString(2, txnType);
      cst.setString(3, bDate);
      cst.setString(4, makerId);
      cst.registerOutParameter(5, 1);
      cst.registerOutParameter(6, 1);

      cst.execute();
      s1 = cst.getString(5);
      s2 = cst.getString(6);
      if ((s1 != null) && (s1.equalsIgnoreCase("S")))
      {
        status = s1;
        con.commit();
        chekQ = new StringBuilder().append("select count(LOAN_DEVIATION_ID) from cr_loan_deviation_dtl \twhere LOAN_ID ='").append(loanId).append("' and RULE_ACTION = 'S'").toString();
        checkCount = ConnectionDAO.singleReturn(chekQ);
        if (!checkNull(checkCount).equalsIgnoreCase("0"))
        {
          s1 = "E";
          status = "SOME RULES ARE FAILED, PLEASE CHECK";
        }

      }
      else
      {
        status = s2;
        con.rollback();
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
      e = null;
    }
    finally
    {
      try
      {
        cst.close();
        con.close();
        cst = null;
        con = null;
      }
      catch (SQLException e) {
        e.printStackTrace();
        e = null;
      }
      s1 = null;
      s2 = null;
      chekQ = null;
      checkCount = null;
      dbType = null;
      bufSql = null;
      loanId = null;
      bDate = null;
      makerId = null;
      txnType = null;
    }

    return status;
  }

  public static String editableDisbursalScheduleFlag() {
    String q = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='DISBURSAL_SCHEDULE_EDITABLE'";
    String flag = ConnectionDAO.singleReturn(q);
    q = null;
    return flag;
  }

  public static String rowNumber(String unq1)
  {
    String rowNum = "";
    if (!checkNull(new CommonFunction().singleRowNumber).equalsIgnoreCase(""))
    {
      rowNum = new CommonFunction().singleRowNumber.replace("UNQ1", unq1);
    }
    unq1 = null;
    return rowNum;
  }

  public static Double checkDouble(Object requestString)
  {
    Double integer = Double.valueOf(0.0D);
    String str = "0.0";
    try {
      if (requestString != null)
        str = requestString.toString();
      else {
        str = "0.0";
      }

      integer = Double.valueOf(Double.parseDouble(str));
    } catch (Exception e) {
      integer = Double.valueOf(0.0D);
    }
    return integer;
  }

  public static String checkNullForDate(HSSFCell cell)
  {
    String strValue = null;
    java.util.Date dateValue = null;
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

    if (cell != null) {
      strValue = cell.toString();
    }

    if (strValue != null) {
      return strValue.trim();
    }
    strValue = null;
    return "";
  }

  public static java.sql.Date changeStringToDate(String stringDate)
  {
    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
    String dateFormatStr = resource.getString("lbl.dateForDisbursal");

    java.util.Date date = null;
    java.sql.Date convertDate = null;
    try {
      date = new SimpleDateFormat(dateFormatStr).parse(stringDate);
      convertDate = new java.sql.Date(date.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return convertDate;
  }

  public static BigDecimal decimalNumberConvert(String decimalNumber) {
    if (checkNull(decimalNumber).equalsIgnoreCase(""))
    {
      decimalNumber = "0";
    }
    decimalNumber = decimalNumber.replace(",", "");
    BigDecimal decimalNum = new BigDecimal(decimalNumber);
    decimalNumber = null;
    return decimalNum;
  }

  public static String caseSandBack(CaseMovementVo vo) {
    logger.info(new StringBuilder().append("In save & update Case Send back Status......CaseId:  ").append(vo.getLbxcaseNo()).append("CURRENT_STAGE:  ").append(vo.getStage()).append("SEND_BACK_STAGE:  ").append(vo.getSendBackStage()).append("Reprocessing flag:  ").append(vo.getRpStageFlag()).append("Remarks:  ").append(vo.getRemarks()).append("UserId:  ").append(vo.getUserId()).append(" Send Back Date: ").append(vo.getSendBackDate()).toString());
    CallableStatement cst = null;
    String statusProc = "";

    boolean status = false;
    try
    {
      ArrayList in = new ArrayList();
      ArrayList out = new ArrayList();
      ArrayList outMessages = new ArrayList();
      String s1 = "";
      String s2 = "";
      in.add(vo.getLbxcaseNo());
      in.add(checkNull(vo.getStage()));

      in.add(vo.getSendBackStage());
      in.add(vo.getRpStageFlag());
      in.add(vo.getRemarks());
      in.add(vo.getOtherRemarks());
      in.add(vo.getUserId());

      String date = changeFormat(vo.getSendBackDate());
      in.add(date);
      out.add(s1);
      out.add(s2);

      logger.info(new StringBuilder().append("CASE_SEND_BACK (").append(in.toString()).append(",").append(out.toString()).append(")").toString());
      outMessages = (ArrayList)ConnectionDAO.callSP("CASE_SEND_BACK", in, out);
      s1 = checkNull(outMessages.get(0));
      s2 = checkNull(outMessages.get(1));
      logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
      logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
      if (s1.equalsIgnoreCase("S"))
      {
        statusProc = s1;
        status = true;
        logger.info(new StringBuilder().append("Procedure Error Message----").append(s2).toString());
      }
      else
      {
        statusProc = s2;
        logger.info(new StringBuilder().append("Procedure Error Message----").append(s2).toString());
      }
      in.clear();
      in = null;
      out.clear();
      out = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      vo = null;
    }
    return statusProc;
  }
//pooja code starts 
	public static String minDealLoanId(String dealId){
		String dealloanId = "0";
		String dealQuery = "select MIN(DEAL_LOAN_ID) from cr_deal_loan_dtl where DEAL_ID = '"+dealId+"' ";
		logger.info("In Deal Loan Query: " + dealQuery);
		dealloanId = ConnectionDAO.singleReturn(dealQuery);
		logger.info("In dealloanId:  "+ dealloanId);
		return dealloanId;
	}
	  public static String getParameterMSTValue(String key) {
			String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='"+key+"'";
			String flag=ConnectionDAO.singleReturn(query);
			query=null;
			return flag;
		}
	public static Map<String, String> getDmsCredential() {
	    Map dmsCredential = new HashMap();
	    dmsCredential.put("DMS_URL", "");
	    dmsCredential.put("DMS_USER_PASSWORD", "");
	    dmsCredential.put("DMS_USER_ID", "");
	    dmsCredential.put("DMS_CATEGORY_NAME", "");
	    dmsCredential.put("DMS_DEAL_ROOM", "");
	    dmsCredential.put("DMS_DOC_SET_CHILD_NAME", "");
	    dmsCredential.put("DMS_DOC_SET_PARENT_NAME", "");
	    dmsCredential.put("DMS_IG_NAME", "");
	    dmsCredential.put("DMS_IM_NAME", "");
	    dmsCredential.put("DMAC_VERSION", "");
	    dmsCredential.put("DMS_IMF_NAME", "");
	    try {
	      String query = " SELECT PARAMETER_KEY,PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY IN('DMS_UI_SWITCHING_UPLOAD_URL','DMS_UI_SWITCHING_USERID','DMS_UI_SWITCHING_PASSWORD','DMS_CATEGORY_NAME','DMS_DEAL_ROOM','DMS_DOC_SET_PARENT_NAME','DMS_IG_NAME','DMS_IM_NAME','DMS_DOC_SET_CHILD_NAME','DMAC_VERSION','DMS_IMF_NAME')";
	      logger.info(new StringBuilder().append("DMS service credential Qry : ").append(query).toString());
	      ArrayList source = ConnectionDAO.sqlSelect(query.toString());
	      query = null;
	      for (int i = 0; i < source.size(); i++) {
	        ArrayList subsource = (ArrayList)source.get(i);
	        if (StringUtils.equalsIgnoreCase("DMS_UI_SWITCHING_UPLOAD_URL", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMS_URL", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMS_UI_SWITCHING_PASSWORD", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMS_USER_PASSWORD", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMS_UI_SWITCHING_USERID", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMS_USER_ID", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMS_CATEGORY_NAME", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMS_CATEGORY_NAME", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMS_DEAL_ROOM", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMS_DEAL_ROOM", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMS_DOC_SET_CHILD_NAME", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMS_DOC_SET_CHILD_NAME", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMS_DOC_SET_PARENT_NAME", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMS_DOC_SET_PARENT_NAME", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMS_IG_NAME", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMS_IG_NAME", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMS_IM_NAME", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMS_IM_NAME", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMAC_VERSION", checkNull(subsource.get(0)).trim())) {
	          dmsCredential.put("DMAC_VERSION", checkNull(subsource.get(1)).trim());
	        }
	        else if (StringUtils.equalsIgnoreCase("DMS_IMF_NAME", checkNull(subsource.get(0)).trim()))
	          dmsCredential.put("DMS_IMF_NAME", checkNull(subsource.get(1)).trim());
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return dmsCredential;
	  }
	  public static Map<String, String> getOmniFinServiceCredential(){
			Map<String, String>webserviceCredential=new HashMap<String,String>();
			webserviceCredential.put("SERVICE_USER_ID", "");
			webserviceCredential.put("SERVICE_USER_PASSWORD", "");
			webserviceCredential.put("SERVICE_URL", "");
			webserviceCredential.put("CIBIL_PROVIDER", "");
			try{
				String query=" SELECT USR.USER_ID,USR.USER_PASSWORD,PM.PARAMETER_KEY,PM.PARAMETER_VALUE SERVICE_URL "
							+ " FROM SEC_USER_M USR "
							+ " JOIN PARAMETER_MST PM ON(PM.PARAMETER_KEY IN('OMNIFIN_WEBSERVICE_BASE_URL','CIBIL_PROVIDER')) "
							+ " WHERE USR.USER_ID='APPUSER' ";
				logger.info("Omnifin Service credential Qry : "+query);
				ArrayList source = ConnectionDAO.sqlSelect(query.toString());
				query=null;
				for(int i=0;i<source.size();i++){
					ArrayList subsource=(ArrayList)source.get(i);
					if(subsource.size()>0)
					{
						webserviceCredential.put("SERVICE_USER_ID",  CommonFunction.checkNull(subsource.get(0)).trim());
						webserviceCredential.put("SERVICE_USER_PASSWORD",  CommonFunction.checkNull(subsource.get(1)).trim());
						if(StringUtils.equalsIgnoreCase(CommonFunction.checkNull(subsource.get(2)).trim(), "OMNIFIN_WEBSERVICE_BASE_URL")){
							webserviceCredential.put("SERVICE_URL",  CommonFunction.checkNull(subsource.get(3)).trim());
						}
						if(StringUtils.equalsIgnoreCase(CommonFunction.checkNull(subsource.get(2)).trim(), "CIBIL_PROVIDER")){
							webserviceCredential.put("CIBIL_PROVIDER",  CommonFunction.checkNull(subsource.get(3)).trim());
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return webserviceCredential;
		}	  
}