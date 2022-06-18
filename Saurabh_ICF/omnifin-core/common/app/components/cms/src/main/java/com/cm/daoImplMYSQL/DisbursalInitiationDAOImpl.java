package com.cm.daoImplMYSQL;

import com.cm.dao.DisbursalInitiationDAO;
import com.cm.vo.DisbursalAuthorVO;
import com.cm.vo.DisbursalMakerVO;
import com.cm.vo.DisbursalSearchVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cm.vo.PostDisbursalDocVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.OtherChargesPlanVo;
import com.cp.vo.RepayScheduleVo;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class DisbursalInitiationDAOImpl
  implements DisbursalInitiationDAO
{
  private static final Logger logger = Logger.getLogger(DisbursalInitiationDAOImpl.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
  String dateFormatWithTime = this.resource.getString("lbl.dateWithTimeInDao");
  String dateFormat = this.resource.getString("lbl.dateInDao");
  String dateForDisbursal = this.resource.getString("lbl.dateForDisbursal");
  DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
  int no = Integer.parseInt(this.resource.getString("msg.pageSizeForMaster"));

  public ArrayList<DisbursalMakerVO> getCycleDateList() {
    ArrayList list = new ArrayList();
    String query = "";
    DisbursalMakerVO vo = null;
    ArrayList product = null;
    ArrayList data = null;
    try {
      logger.info("In getCycleDateList..........................DAOImpl");
      query = "select gm.value, gm.description from generic_master gm,generic_master_keys gmk where gm.generic_key='CYCLE_DATE'and gmk.generic_key='CYCLE_DATE' and rec_status ='A'";

      product = ConnectionDAO.sqlSelect(query);
      int size = product.size();
      logger.info(new StringBuilder().append("getCycleDateList ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        data = (ArrayList)product.get(i);
        if (data.size() > 0) {
          vo = new DisbursalMakerVO();
          vo.setCycleDateValue(CommonFunction.checkNull(data.get(0)).trim());
          vo.setCycleDateDesc(CommonFunction.checkNull(data.get(1)).trim());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      query = null;
      vo = null;
      product = null;
      data = null;
    }
    return list;
  }

  public ArrayList<DisbursalSearchVO> searchDisbursalData(DisbursalSearchVO vo, String type, HttpServletRequest request)
  {
    String loanId = "";
    String loanNo = "";
    String customerName = "";
    String loanAmtStr = "";
    String loanApprovalDate = "";
    String product = "";
    String productId = "";
    String scheme = "";
    String schemeId = "";

    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;
    ArrayList searchlist = new ArrayList();
    StringBuilder bufInsSql = new StringBuilder();
    StringBuilder bufInsSqlTempCount = new StringBuilder();
    ArrayList data = new ArrayList();
    DisbursalSearchVO vo1 = null;
    String userName = "";

    logger.info(new StringBuilder().append("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ").append(vo.getReportingToUserId()).toString());
    try {
      String userNameQ = new StringBuilder().append("select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='").append(vo.getReportingToUserId()).append("'").toString();
      userName = ConnectionDAO.singleReturn(userNameQ);
      logger.info(new StringBuilder().append("userNameQ: ").append(userNameQ).append(" userName: ").append(userName).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }

    ArrayList detailList = new ArrayList();
    try {
      logger.info("In searchDisbursalData().....................................Dao Impl");

      loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim());
      loanNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanNo()).trim());
      customerName = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim());

      if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanAmt()).trim()).equalsIgnoreCase(""))
      {
        loanAmtStr = "";
      }
      else
      {
        loanAmtStr = this.myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanAmt()).trim())).toString();
      }
      loanApprovalDate = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanApprovalDate()).trim());
      product = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getProduct()).trim());
      productId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim());
      scheme = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getScheme()).trim());
      schemeId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim());
      boolean appendSQL = false;

      String forwarded = request.getParameter("forwarded");
      request.setAttribute("forwardedLoanId", "");
      if (CommonFunction.checkNull(forwarded).trim().equalsIgnoreCase("Y"))
      {
        request.setAttribute("forwardedLoanId", loanId);
        loanId = "";
        customerName = "";
        loanAmtStr = "";
        loanApprovalDate = "";
        productId = "";
        schemeId = "";
      }

      String table = "";
      if (type.equalsIgnoreCase("P"))
        table = "cr_loan_disbursal_dtl_temp";
      else {
        table = "cr_loan_disbursal_dtl";
      }

      String branch = "";
      if (vo.getAllBranches().equalsIgnoreCase("on"))
      {
        String branchMappedToUserQuery = new StringBuilder().append("SELECT GROUP_CONCAT(BRANCH_ID) FROM sec_user_branch_dtl WHERE USER_ID='").append(CommonFunction.checkNull(vo.getReportingToUserId()).trim()).append("' AND REC_STATUS='A'").toString();
        branch = ConnectionDAO.singleReturn(branchMappedToUserQuery);
        logger.info(new StringBuilder().append("branchMappedToUserQuery: ").append(branchMappedToUserQuery).append("      branches maped to user:   ").append(branch).toString());
      }
      else if (!vo.getLbxBranchId().equalsIgnoreCase(""))
      {
        branch = vo.getLbxBranchId();
      }
      else
      {
        String allBranch = "SELECT GROUP_CONCAT(BRANCH_ID) FROM com_branch_m WHERE REC_STATUS='A'";
        branch = ConnectionDAO.singleReturn(allBranch);
        logger.info(new StringBuilder().append("allBranchQuery: ").append(allBranch).append("      allBranch maped to user:::::   ").append(branch).toString());
      }

      bufInsSql.append(new StringBuilder().append("select distinct a.loan_no, e.disbursal_no, b.customer_name, a.loan_loan_amount, DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("'),c.product_desc, d.scheme_desc,").toString());
      bufInsSql.append("a.loan_id,e.LOAN_DISBURSAL_ID,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=e.MAKER_ID) MAKER_ID");
      if (type.equalsIgnoreCase("F")) {
        bufInsSql.append(" ,DISBURSAL_BATCH_ID ");
      }
      bufInsSql.append(" from cr_loan_dtl a,gcd_customer_m b,");
      bufInsSql.append(new StringBuilder().append(" cr_product_m c, cr_scheme_m d, ").append(table).append(" e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product").toString());
      bufInsSql.append(new StringBuilder().append(" and d.scheme_id = a.loan_scheme and e.rec_status='").append(type).append("' and a.REC_STATUS='A' and e.loan_id=a.loan_id AND A.LOAN_BRANCH IN (").append(StringEscapeUtils.escapeSql(branch).trim()).append(") ").toString());

      bufInsSqlTempCount.append(new StringBuilder().append("select distinct COUNT(1) FROM  cr_loan_dtl a,gcd_customer_m b, cr_product_m c, cr_scheme_m d, cr_loan_disbursal_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product and d.scheme_id = a.loan_scheme and e.rec_status='").append(type).append("' and a.REC_STATUS='A' and e.loan_id=a.loan_id AND A.LOAN_BRANCH IN (").append(StringEscapeUtils.escapeSql(branch).trim()).append(") ").toString());

      if ((vo.getStage() != null) && (!vo.getStage().equalsIgnoreCase("F")))
      {
        bufInsSql.append(new StringBuilder().append(" AND e.MAKER_ID='").append(vo.getReportingToUserId()).append("'").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND e.MAKER_ID='").append(vo.getReportingToUserId()).append("'").toString());
      }
      else if ((vo.getStage() != null) && (vo.getStage().equalsIgnoreCase("F")))
      {
        bufInsSql.append(new StringBuilder().append(" AND e.MAKER_ID!='").append(vo.getUserId()).append("'").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND e.MAKER_ID!='").append(vo.getUserId()).append("'").toString());
      }

      if ((!loanId.equalsIgnoreCase("")) && (!customerName.equalsIgnoreCase("")) && (!loanAmtStr.equalsIgnoreCase("")) && (!loanApprovalDate.equalsIgnoreCase("")) && (!productId.equalsIgnoreCase("")) && (!schemeId.equalsIgnoreCase("")))
      {
        bufInsSql.append(new StringBuilder().append(" AND a.Loan_Id in (select loan_id from ").append(table).append(" where loan_id='").append(loanId).append("' and rec_status='").append(type).append("') AND b.customer_name like'%").append(customerName).append("%' AND a.loan_loan_amount='").append(loanAmtStr).append("' AND DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("')='").append(loanApprovalDate).append("' AND c.product_id='").append(productId).append("' AND d.scheme_id ='").append(schemeId).append("'").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND a.Loan_Id in (select loan_id from ").append(table).append(" where loan_id='").append(loanId).append("' and rec_status='").append(type).append("') AND b.customer_name like'%").append(customerName).append("%' AND a.loan_loan_amount='").append(loanAmtStr).append("' AND DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("')='").append(loanApprovalDate).append("' AND c.product_id='").append(productId).append("' AND d.scheme_id ='").append(schemeId).append("'").toString());
      }
      if ((!loanId.equalsIgnoreCase("")) || (!customerName.equalsIgnoreCase("")) || (!loanAmtStr.equalsIgnoreCase("")) || (!loanApprovalDate.equalsIgnoreCase("")) || (!productId.equalsIgnoreCase("")) || (!schemeId.equalsIgnoreCase(""))) {
        appendSQL = true;
      }

      if (appendSQL) {
        bufInsSql.append(" AND ");
        bufInsSqlTempCount.append(" AND ");
      }

      if (!loanId.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" a.Loan_Id in (select loan_id from ").append(table).append(" where loan_id='").append(loanId).append("' and rec_status='").append(type).append("') AND").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" a.Loan_Id in (select loan_id from ").append(table).append(" where loan_id='").append(loanId).append("' and rec_status='").append(type).append("') AND").toString());
        appendSQL = true;
      }

      if (!customerName.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" b.customer_name like'%").append(customerName).append("%' AND").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" b.customer_name like'%").append(customerName).append("%' AND").toString());
        appendSQL = true;
      }
      if (!loanAmtStr.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" a.loan_loan_amount='").append(loanAmtStr).append("' AND").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" a.loan_loan_amount='").append(loanAmtStr).append("' AND").toString());
        appendSQL = true;
      }
      if (!loanApprovalDate.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("')='").append(loanApprovalDate).append("' AND").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("')='").append(loanApprovalDate).append("' AND").toString());
        appendSQL = true;
      }
      if (!product.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" c.product_id='").append(productId).append("' AND").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" c.product_id='").append(productId).append("' AND").toString());
        appendSQL = true;
      }
      if (!scheme.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" d.scheme_id ='").append(schemeId).append("' AND").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" d.scheme_id ='").append(schemeId).append("' AND").toString());
        appendSQL = true;
      }

      if ((!vo.getLbxUserId().equalsIgnoreCase("")) && (vo.getStage().equalsIgnoreCase("F"))) {
        bufInsSql.append(new StringBuilder().append(" AND e.MAKER_ID='").append(StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()).append("' ").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND e.MAKER_ID='").append(StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()).append("'").toString());
        appendSQL = true;
      }

      logger.info(new StringBuilder().append("In appendSQL true---- ").append(appendSQL).toString());

      if (appendSQL) {
        String tmp = bufInsSql.toString();
        String tmp1 = bufInsSqlTempCount.toString();
        logger.info(new StringBuilder().append("In searchDisbursalData() ## tmp ## ").append(tmp).toString());
        logger.info(new StringBuilder().append("In appendSQL true----  in check index Of").append(tmp.lastIndexOf("AND")).append("------").append(tmp.length() - 3).toString());
        if ((tmp.lastIndexOf("AND") == tmp.length() - 3) || (tmp1.lastIndexOf("AND") == tmp1.length() - 3))
        {
          logger.info("In appendSQL true----  in check index Of");
          tmp = tmp.substring(0, tmp.length() - 4).trim();
          tmp1 = tmp1.substring(0, tmp1.length() - 4).trim();
          logger.info(new StringBuilder().append("search Query...tmp. ").append(tmp).toString());
          searchlist = ConnectionDAO.sqlSelect(tmp);
          count = Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
        }
        else
        {
          logger.info(new StringBuilder().append("search Query...tmp.").append(tmp).toString());
          searchlist = ConnectionDAO.sqlSelect(tmp);
          count = Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
        }
      }
      else
      {
        logger.info(new StringBuilder().append("search Query...else-------.").append(bufInsSql).toString());
        logger.info(new StringBuilder().append("bufInsSqlTempCount **************************** : ").append(bufInsSqlTempCount.toString()).toString());

        count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

        logger.info(new StringBuilder().append("current PAge Link no .................... ").append(vo.getCurrentPageLink()).toString());
        if (vo.getCurrentPageLink() > 1)
        {
          startRecordIndex = (vo.getCurrentPageLink() - 1) * this.no;
          endRecordIndex = this.no;
          logger.info(new StringBuilder().append("startRecordIndex .................... ").append(startRecordIndex).toString());
          logger.info(new StringBuilder().append("endRecordIndex .................... ").append(endRecordIndex).toString());
        } else {
          bufInsSql.append(" GROUP BY a.loan_no ");
          bufInsSqlTempCount.append(" GROUP BY a.loan_no ");
        }

        bufInsSql.append(new StringBuilder().append(" limit ").append(startRecordIndex).append(",").append(endRecordIndex).toString());

        logger.info(new StringBuilder().append("query : ").append(bufInsSql).toString());

        searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      }

      int size = searchlist.size();
      logger.info(new StringBuilder().append("searchDisbursalData: ").append(size).toString());
      for (int i = 0; i < size; i++) {
        logger.info(new StringBuilder().append("searchDisbursalDataList: ").append(searchlist.get(i).toString()).toString());
        data = (ArrayList)searchlist.get(i);
        if (data.size() > 0)
        {
          vo1 = new DisbursalSearchVO();
          if (type.equalsIgnoreCase("P"))
          {
            vo1.setModifyNo(new StringBuilder().append("<a href=disbursalSearch.do?method=openDisbursalValues&loanId=").append(CommonFunction.checkNull(data.get(7)).trim()).append("&disbursalNo=").append(CommonFunction.checkNull(data.get(1)).trim()).append("&loanDisbursalId=").append(CommonFunction.checkNull(data.get(8)).trim()).append(">").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
          }

          if (type.equalsIgnoreCase("F"))
          {
            vo1.setModifyNo(new StringBuilder().append("<a href=disbursalSearch.do?method=openDisbursalValuesAuthor&loanId=").append(CommonFunction.checkNull(data.get(7)).trim()).append("&disbursalNo=").append(CommonFunction.checkNull(data.get(1)).trim()).append("&loanDisbursalId=").append(CommonFunction.checkNull(data.get(8)).trim()).append("&batchId=").append(CommonFunction.checkNull(data.get(10)).trim()).append(">").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
          }

          vo1.setLoanNo(CommonFunction.checkNull(data.get(0)).trim());
          vo1.setDisbursalNo(CommonFunction.checkNull(data.get(1)).trim());
          vo1.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          Number loanAmt = this.myFormatter.parse(CommonFunction.checkNull(data.get(3)).trim());
          vo1.setLoanAmt(this.myFormatter.format(loanAmt));
          vo1.setLoanApprovalDate(CommonFunction.checkNull(data.get(4)).trim());
          vo1.setProduct(CommonFunction.checkNull(data.get(5)).trim());
          vo1.setScheme(CommonFunction.checkNull(data.get(6)).trim());
          vo1.setLbxLoanNoHID(CommonFunction.checkNull(data.get(7)).trim());
          vo1.setLoanDisbursalId(CommonFunction.checkNull(data.get(8)).trim());
          vo1.setReportingToUserId(CommonFunction.checkNull(data.get(9)).trim());
          vo1.setTotalRecordSize(count);
          detailList.add(vo1);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      loanId = null;
      loanNo = null;
      customerName = null;
      loanAmtStr = null;
      loanApprovalDate = null;
      product = null;
      productId = null;
      scheme = null;
      schemeId = null;
      searchlist = null;
      bufInsSql = null;
      bufInsSqlTempCount = null;
      data = null;
      vo1 = null;
    }
    return detailList;
  }

  public String saveDisbursalAuthor(DisbursalAuthorVO vo)
  {
    logger.info("In saveDisbursalAuthor ........ DAOImpl");
    String status = "";

    String query1 = new StringBuilder().append("select disbursal_flag from cr_loan_disbursal_dtl where loan_id='").append(vo.getLoanId()).append("'").append(" and disbursal_no='").append(vo.getDisbursalNo()).append("'").toString();

    logger.info(new StringBuilder().append("In saveDisbursalAuthor...........select query: ").append(query1).toString());
    String flag = ConnectionDAO.singleReturn(query1);

    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    String s1 = "";
    String s2 = "";
    try
    {
      in.add(Integer.valueOf(Integer.parseInt(CommonFunction.checkNull(Integer.valueOf(vo.getCompanyId())).trim())));
      in.add(Integer.valueOf(Integer.parseInt(CommonFunction.checkNull(vo.getLoanId()).trim())));
      in.add(Integer.valueOf(Integer.parseInt(CommonFunction.checkNull(vo.getDisbursalBatchId()).trim())));
      String date = CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim());
      if (date != null)
        in.add(date);
      in.add(CommonFunction.checkNull(vo.getAuthorId()).trim());

      in.add(CommonFunction.checkNull(vo.getDecision()).trim());
      in.add(CommonFunction.checkNull(vo.getComments()).trim());

      out.add(s1);
      out.add(s2);

      logger.info("In Disbursal_Authorization Procedure  ");
      logger.info(new StringBuilder().append("Input :   ").append(in.toString()).toString());

      logger.info(new StringBuilder().append("Disbursal_Authorization_WithPayment Procedure  ").append(in).append("").append(out).toString());

      outMessages = (ArrayList)ConnectionDAO.callSP("Disbursal_Authorization", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));

      if ((s1 != null) && (s1.equalsIgnoreCase("S")))
      {
        status = s1;
      }
      else if ((s1 != null) && (s1.equalsIgnoreCase("E")))
      {
        status = s2;
      }
      logger.info(new StringBuilder().append("status: ").append(s1).toString());
      logger.info(new StringBuilder().append("s2: ").append(s2).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      query1 = null;
      flag = null;
      s1 = null;
      s2 = null;
      in.clear();
      in = null;
      out.clear();
      out = null;
      outMessages.clear();
      outMessages = null;
    }
    return status;
  }

  public ArrayList<DisbursalMakerVO> selectDisbursalData(String loanId, String disbursalNo, String businessDate, String bp_type)
  {
    logger.info("In selectDisbursalData.....DAOImpl");
    ArrayList disbursalData = new ArrayList();
    DisbursalMakerVO vo = null;
    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    StringBuilder query1 = new StringBuilder();
    String proposedShortPayAmtStr = "";
    StringBuilder query2 = new StringBuilder();
    String disbursedAmountStr = "";
    StringBuilder query3 = new StringBuilder();
    String shortAmountPaidStr = "";
    StringBuilder query = new StringBuilder();
    StringBuilder query5 = new StringBuilder();
    StringBuilder query7 = new StringBuilder();
    String maxDisbursalDate = "";
    String query6 = "";
    String query8 = "";
    try
    {
      query1.append(new StringBuilder().append("select SUM(IFNULL(ADVICE_AMOUNT,0) - IFNULL(TXN_ADJUSTED_AMOUNT,0) - IFNULL(AMOUNT_IN_PROCESS,0)) FROM CR_TXNADVICE_DTL WHERE LOAN_ID = '").append(loanId).append("' AND BP_TYPE = '").append(bp_type).append("' AND ADVICE_TYPE = 'R' AND REC_STATUS = 'A'AND ADVICE_DATE<=STR_TO_DATE('").append(businessDate).append("', '").append(this.dateFormat).append("') ").toString());

      logger.info(new StringBuilder().append("In selectDisbursalData : ").append(query1).toString());
      proposedShortPayAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
      logger.info(new StringBuilder().append("In selectDisbursalData.....proposedShortPayAmt: ").append(proposedShortPayAmtStr).toString());

      query2.append(new StringBuilder().append("select SUM(IFNULL(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(loanId).append("' AND REC_STATUS='A'").toString());

      disbursedAmountStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
      logger.info(new StringBuilder().append("In selectDisbursalData.....disbursedAmount: ").append(disbursedAmountStr).toString());

      query3.append(new StringBuilder().append("select SUM(IFNULL(SHORT_AMOUNT_ADJUSTED,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(loanId).append("' AND REC_STATUS='A'").toString());

      shortAmountPaidStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
      logger.info(new StringBuilder().append("In selectDisbursalData.....shortAmountPaid: ").append(shortAmountPaidStr).toString());

      String maxExpectedPayDate = "";
      query6 = new StringBuilder().append("select DATE_FORMAT(max(PAYMENT_DATE),'").append(this.dateFormat).append("') from cr_loan_disbursal_dtl where loan_id='").append(loanId).append("' and REC_STATUS='A'").toString();
      logger.info(new StringBuilder().append("In selectDisbursalData : ").append(query6).toString());

      maxExpectedPayDate = ConnectionDAO.singleReturn(query6);
      logger.info(new StringBuilder().append("In selectDisbursalData maxExpectedPayDate : ").append(maxExpectedPayDate).toString());

      query8 = new StringBuilder().append("select DATE_FORMAT(max(DISBURSAL_DATE) ,'").append(this.dateFormat).append("')from cr_loan_disbursal_dtl where LOAN_ID='").append(loanId).append("' and REC_STATUS='A'").toString();
      logger.info(new StringBuilder().append("query8:----------").append(query8).toString());
      maxDisbursalDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(query8));
      logger.info(new StringBuilder().append("In selectDisbursalData maxDisbursalDate : ").append(maxDisbursalDate).toString());

      query.append(new StringBuilder().append("select a.loan_no, b.customer_name, a.loan_loan_amount, DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("'), c.product_desc, d.scheme_desc,").append(" a.loan_id, e.disbursal_description, DATE_FORMAT(e.disbursal_date,'").append(this.dateFormat).append("'),").append(" e.short_amount_adjusted, e.disbursal_amount, e.disbursal_no, e.disbursal_flag,DATE_FORMAT(e.REPAY_EFF_DATE,'").append(this.dateFormat).append("'),e.remarks,").append(" e.loan_due_day,a.loan_repayment_type,").append(" (select count(PDC_LOAN_ID) from cr_pdc_instrument_dtl where  PDC_STATUS not in('X','R') and PDC_PURPOSE='INSTALLMENT' and PDC_LOAN_ID='").append(loanId).append("'),DATE_FORMAT(e.NEXT_DUE_DATE ,'%d-%m-%Y'),e.CUR_MONTH_EMI,e.NEXT_MONTH_EMI,e.LOAN_DISBURSAL_ID,e.DISBURSAL_TO_ID,e.DISBURSAL_TO, DATE_FORMAT(e.PAYMENT_DATE,'").append(this.dateFormat).append("'),e.TA_LOAN_ID, DATE_FORMAT(e.PENAL_INT_CALC_DATE ,'%d-%m-%Y')").append(" from cr_loan_dtl a,gcd_customer_m b,").append(" cr_product_m c, cr_scheme_m d, cr_loan_disbursal_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product").append(" and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.loan_id='").append(loanId).append("' and e.disbursal_no='").append(disbursalNo).append("'").toString());

      logger.info(new StringBuilder().append("In selectDisbursalData ::::::::::::: ").append(query).toString());
      mainlist = ConnectionDAO.sqlSelect(query.toString());
      int size = mainlist.size();
      logger.info(new StringBuilder().append("In selectDisbursalData.....mainlist size: ").append(size).toString());
      if (size != 0)
      {
        for (int i = 0; i < size; i++)
        {
          subList = (ArrayList)mainlist.get(i);

          if (subList.size() > 0) {
            vo = new DisbursalMakerVO();
            vo.setLoanNo(CommonFunction.checkNull(subList.get(0)).trim());
            vo.setCustomerName(CommonFunction.checkNull(subList.get(1)).trim());

            if (CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
            {
              vo.setLoanAmt("0.00");
            }
            else
            {
              Number loanAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
              vo.setLoanAmt(this.myFormatter.format(loanAmount));
            }

            vo.setLoanApprovalDate(CommonFunction.checkNull(subList.get(3)).trim());
            vo.setProduct(CommonFunction.checkNull(subList.get(4)).trim());
            vo.setScheme(CommonFunction.checkNull(subList.get(5)).trim());
            vo.setLbxLoanNoHID(CommonFunction.checkNull(subList.get(6)).trim());
            vo.setDisbursalDescription(CommonFunction.checkNull(subList.get(7)).trim());
            vo.setDisbursalDate(CommonFunction.checkNull(subList.get(8)).trim());

            if (CommonFunction.checkNull(subList.get(9)).trim().equalsIgnoreCase(""))
            {
              vo.setShortPayAmount("0");
            }
            else
            {
              Number shortPayAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(9)).trim());
              vo.setShortPayAmount(this.myFormatter.format(shortPayAmount));
            }

            if (CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase(""))
            {
              vo.setDisbursalAmount("0");
            }
            else
            {
              Number disbursalAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(10)).trim());
              vo.setDisbursalAmount(this.myFormatter.format(disbursalAmount));
            }

            vo.setDisbursalNo(CommonFunction.checkNull(subList.get(11)).trim());
            vo.setFinalDisbursal(CommonFunction.checkNull(subList.get(12)).trim());

            if (CommonFunction.checkNull(proposedShortPayAmtStr).trim().equalsIgnoreCase(""))
            {
              vo.setProposedShortPayAmount("0");
            }
            else
            {
              Number proposedShortPayAmt = this.myFormatter.parse(CommonFunction.checkNull(proposedShortPayAmtStr).trim());
              vo.setProposedShortPayAmount(this.myFormatter.format(proposedShortPayAmt));
            }

            if (CommonFunction.checkNull(disbursedAmountStr).trim().equalsIgnoreCase(""))
            {
              vo.setDisbursedAmount("0");
            }
            else
            {
              Number disbursedAmount = this.myFormatter.parse(CommonFunction.checkNull(disbursedAmountStr).trim());
              vo.setDisbursedAmount(this.myFormatter.format(disbursedAmount));
            }

            if (CommonFunction.checkNull(shortAmountPaidStr).trim().equalsIgnoreCase(""))
            {
              vo.setAdjustedShortPayAmount("0");
            }
            else
            {
              Number shortAmountPaid = this.myFormatter.parse(CommonFunction.checkNull(shortAmountPaidStr).trim());
              vo.setAdjustedShortPayAmount(this.myFormatter.format(shortAmountPaid));
            }

            vo.setRepayEffDate(CommonFunction.checkNull(subList.get(13)).trim());
            vo.setAuthorRemarks(CommonFunction.checkNull(subList.get(14)).trim());
            vo.setCycleDateValue(CommonFunction.checkNull(subList.get(15)).trim());
            vo.setRepayMode(CommonFunction.checkNull(subList.get(16)).trim());
            if (CommonFunction.checkNull(subList.get(17)).trim().equalsIgnoreCase(""))
              vo.setPdcDepositCount("0");
            else {
              vo.setPdcDepositCount(CommonFunction.checkNull(subList.get(17)).trim());
            }
            vo.setNextDueDate(CommonFunction.checkNull(subList.get(18)).trim());

            if (CommonFunction.checkNull(subList.get(19)).trim().equalsIgnoreCase("")) {
              vo.setCurrentMonthEMI("");
            }
            else {
              Number amount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(19)).trim());
              vo.setCurrentMonthEMI(this.myFormatter.format(amount));
            }
            if (CommonFunction.checkNull(subList.get(20)).trim().equalsIgnoreCase("")) {
              vo.setPreEMINextMonth("");
            }
            else {
              Number amount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(20)).trim());
              vo.setPreEMINextMonth(this.myFormatter.format(amount));
            }
            vo.setLoanDisbursalId(CommonFunction.checkNull(subList.get(21)).trim());

            vo.setDisbursalTo(CommonFunction.checkNull(subList.get(23)).trim());

            vo.setLbxBusinessPartnearHID(CommonFunction.checkNull(subList.get(22)).trim());
            vo.setExpectedPaymentDate(CommonFunction.checkNull(subList.get(24)).trim());

            vo.setMaxDisbursalDate(maxDisbursalDate);
            vo.setPenalIntCalcDate(CommonFunction.checkNull(subList.get(26)).trim());

            query5.append(new StringBuilder().append("SELECT distinct v.BP_NAME from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='").append(loanId).append("' and v.BP_ID='").append(CommonFunction.checkNull(subList.get(22)).trim()).append("' and v.BP_TYPE='").append(CommonFunction.checkNull(subList.get(23)).trim()).append("'").toString());

            logger.info(new StringBuilder().append("In disbursal supplier desc..... : ").append(query1).toString());
            String supplierName = CommonFunction.checkNull(ConnectionDAO.singleReturn(query5.toString()));
            logger.info(new StringBuilder().append("In selectDisbursalData.....proposedShortPayAmt: ").append(proposedShortPayAmtStr).toString());
            vo.setSupplierDesc(supplierName);
            logger.info(new StringBuilder().append("getDisbursalTo in DAO Impl................:------").append(vo.getDisbursalTo()).toString());

            if ((CommonFunction.checkNull(subList.get(25)).trim() != null) && (!CommonFunction.checkNull(subList.get(25)).trim().equalsIgnoreCase("")))
            {
              query7.append(new StringBuilder().append("SELECT C.LOAN_ID,C.LOAN_NO,G.CUSTOMER_NAME FROM CR_LOAN_DTL C,GCD_CUSTOMER_M G WHERE C.LOAN_CUSTOMER_ID=G.CUSTOMER_ID AND C.LOAN_ID=").append(CommonFunction.checkNull(subList.get(25)).trim()).append(" AND REC_STATUS='A'").toString());
              logger.info(new StringBuilder().append("In TA Loan details ::::::::::::: ").append(query7.toString()).toString());

              ArrayList taLoanList = ConnectionDAO.sqlSelect(query7.toString());
              int size1 = taLoanList.size();
              logger.info(new StringBuilder().append("In selectDisbursalData.....taLoanList size: ").append(size).toString());
              if (size1 > 0)
              {
                for (int k = 0; k < size1; k++)
                {
                  ArrayList subtaLoanList = (ArrayList)taLoanList.get(k);

                  if (subtaLoanList.size() > 0)
                  {
                    vo.setLbxTaLoanNoHID(CommonFunction.checkNull(subtaLoanList.get(0)).trim());
                    vo.setTaLoanNo(CommonFunction.checkNull(subtaLoanList.get(1)).trim());
                    vo.setTaCustomerName(CommonFunction.checkNull(subtaLoanList.get(2)).trim());
                  }
                }
              }
            }
            vo.setMaxExpectedPayDate(CommonFunction.checkNull(maxExpectedPayDate));
            disbursalData.add(vo);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      vo = null;
      mainlist = null;
      subList = null;
      query1 = null;
      proposedShortPayAmtStr = null;
      query2 = null;
      disbursedAmountStr = null;
      query3 = null;
      shortAmountPaidStr = null;
      query = null;
    }
    return disbursalData;
  }

  public String disbursalFeasibility(DisbursalMakerVO vo)
  {
    logger.info("Inside disbursalFeasibility........DAOImpl");
    String status = "";

    String query = "";
    String disbursedAmountStr = "";
    BigDecimal disbursedAmount = null;
    String query1 = "";
    String proposedShortPayAmt = "";
    BigDecimal proposedShortPayAmount = null;
    String query2 = "";
    String shortAmountPaidStr = "";
    BigDecimal shortAmountPaid = null;
    String query4 = "";
    String loanAmtStr = "";
    String query5 = "";
    String maxDisbursalNo = "";
    String query6 = "";
    String disbursalDate = "";
    String query7 = "";
    String repayType = "";
    BigDecimal loanAmount = null;
    BigDecimal disbursalAmount = null;
    BigDecimal shortPayAmount = null;
    BigDecimal balanceAmountBig = null;
    BigDecimal netAmountBig = null;
    BigDecimal blsPrin = null;
    BigDecimal frdAmt = null;
    try
    {
      query = new StringBuilder().append("select SUM(IFNULL(disbursal_amount,0)) as disbursal_amt FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()).append("' AND REC_STATUS='A'").toString();

      disbursedAmountStr = ConnectionDAO.singleReturn(query);
      if (disbursedAmountStr.equals(""))
      {
        disbursedAmountStr = "0";
      }
      logger.info(new StringBuilder().append("In disbursedAmountStr:--->").append(disbursedAmountStr).toString());
      disbursedAmount = new BigDecimal(disbursedAmountStr);
      logger.info(new StringBuilder().append("In disbursedAmount:---->").append(disbursedAmount).toString());
      logger.info(new StringBuilder().append(" vo.getDisbursalTo():---->").append(vo.getDisbursalTo()).toString());

      query1 = new StringBuilder().append("select SUM(IFNULL(ADVICE_AMOUNT,0) - IFNULL(TXN_ADJUSTED_AMOUNT,0) - IFNULL(AMOUNT_IN_PROCESS,0)) FROM CR_TXNADVICE_DTL WHERE LOAN_ID = '").append(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()).append("' AND BP_TYPE = '").append(vo.getDisbursalTo()).append("' AND ADVICE_TYPE = 'R' AND REC_STATUS = 'A'").toString();

      proposedShortPayAmt = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1));
      if (proposedShortPayAmt.equals(""))
      {
        proposedShortPayAmt = "0";
      }
      logger.info(new StringBuilder().append("In selectDisbursalData.....proposedShortPayAmt: ").append(proposedShortPayAmt).toString());
      proposedShortPayAmount = new BigDecimal(proposedShortPayAmt);

      query2 = new StringBuilder().append("select SUM(IFNULL(SHORT_AMOUNT_ADJUSTED,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()).append("' AND REC_STATUS='A'").toString();

      shortAmountPaidStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2));
      if (shortAmountPaidStr.equals(""))
      {
        shortAmountPaidStr = "0";
      }
      logger.info(new StringBuilder().append("In getDisbursalValues.....shortAmountPaid: ").append(shortAmountPaidStr).toString());
      shortAmountPaid = new BigDecimal(shortAmountPaidStr);

      query4 = new StringBuilder().append("select loan_loan_amount FROM CR_LOAN_DTL WHERE LOAN_ID = '").append(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()).append("'").toString();
      logger.info(new StringBuilder().append("In getDisbursalValues : ").append(query4).toString());
      loanAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query4));
      if (loanAmtStr.equals(""))
      {
        loanAmtStr = "0";
      }
      logger.info(new StringBuilder().append("In getDisbursalValues.....loanAmt: ").append(loanAmtStr).toString());

      query5 = new StringBuilder().append("select (max(a.disbursal_no)-1) from cr_loan_disbursal_dtl a, cr_loan_dtl b where b.disbursal_status='P' and b.loan_id=a.loan_id and a.loan_id='").append(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()).append("'").toString();
      maxDisbursalNo = CommonFunction.checkNull(ConnectionDAO.singleReturn(query5));
      logger.info(new StringBuilder().append("MaxDisbursal No.: ").append(maxDisbursalNo).toString());

      query6 = new StringBuilder().append("select Date_format(disbursal_date,'").append(this.dateFormat).append("') from cr_loan_disbursal_dtl where disbursal_no='").append(maxDisbursalNo).append("' and loan_id='").append(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()).append("'").toString();
      disbursalDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(query6));

      query7 = new StringBuilder().append("select loan_repayment_type from cr_loan_dtl where loan_id='").append(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()).append("'").toString();
      repayType = CommonFunction.checkNull(ConnectionDAO.singleReturn(query7));
      logger.info(new StringBuilder().append("Repayment Type: ").append(repayType).toString());

      loanAmount = new BigDecimal(loanAmtStr);
      String balanceAmount = "";
      if (CommonFunction.checkNull(vo.getPaymentFlag()).equalsIgnoreCase("T")) {
        String query8 = new StringBuilder().append("select ifnull(ADVICE_AMOUNT,0)-ifnull(TXN_ADJUSTED_AMOUNT,0)-ifnull(AMOUNT_IN_PROCESS,0) from cr_txnadvice_dtl t where t.loan_id='").append(vo.getLbxTaLoanNoHID()).append("' and t.CHARGE_CODE_ID=8  group by t.loan_id").toString();
        balanceAmount = CommonFunction.checkNull(ConnectionDAO.singleReturn(query8));
      }
      if (!balanceAmount.equalsIgnoreCase("")) {
        balanceAmountBig = new BigDecimal(this.myFormatter.parse(CommonFunction.checkNull(balanceAmount).trim()).toString());
      }
      if (!CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase("")) {
        netAmountBig = new BigDecimal(this.myFormatter.parse(CommonFunction.checkNull(vo.getNetAmount()).trim()).toString());
      }
      try
      {
        if (!CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase("")) {
          disbursalAmount = new BigDecimal(this.myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmount()).trim()).toString());
        }
        if (!CommonFunction.checkNull(vo.getShortPayAmount()).equalsIgnoreCase(""))
          shortPayAmount = new BigDecimal(this.myFormatter.parse(CommonFunction.checkNull(vo.getShortPayAmount()).trim()).toString());
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      if (repayType.equalsIgnoreCase("I"))
      {
        String revolvingFlag = CommonFunction.checkNull(vo.getRevolvingFlag()).trim();
        if (CommonFunction.checkNull(revolvingFlag).trim().equalsIgnoreCase(""))
          revolvingFlag = "N";
        String balancePrinc = CommonFunction.checkNull(vo.getBalancePrinc()).trim();
        if (CommonFunction.checkNull(balancePrinc).trim().equalsIgnoreCase(""))
          balancePrinc = "0";
        blsPrin = new BigDecimal(this.myFormatter.parse(CommonFunction.checkNull(balancePrinc).trim()).toString());
        String forwardedAmt = CommonFunction.checkNull(vo.getForwardedAmt()).trim();
        if (CommonFunction.checkNull(forwardedAmt).trim().equalsIgnoreCase(""))
          forwardedAmt = "0";
        frdAmt = new BigDecimal(this.myFormatter.parse(CommonFunction.checkNull(forwardedAmt).trim()).toString());
        if (CommonFunction.checkNull(revolvingFlag).trim().equalsIgnoreCase("N"))
        {
          if ((disbursedAmount != null) && (disbursedAmount.add(disbursalAmount).compareTo(loanAmount) > 0))
            status = "DisbAmt";
          else if ((disbursedAmount != null) && (disbursedAmount.add(disbursalAmount).compareTo(loanAmount) < 0))
            status = "LoanAmt";
          else if ((disbursedAmount != null) && (disbursedAmount.add(disbursalAmount).compareTo(loanAmount) == 0)) {
            status = "eqDisbAmt";
          }
        }
        if (CommonFunction.checkNull(revolvingFlag).trim().equalsIgnoreCase("Y"))
        {
          if (disbursalAmount.compareTo(loanAmount.subtract(blsPrin).subtract(frdAmt)) > 0)
            status = "DisbAmtRV";
          else
            status = "LoanAmt";
        }
        if ((shortPayAmount != null) && (shortPayAmount.compareTo(proposedShortPayAmount) > 0))
        {
          status = "ShrtPayAmt";
        }

      }
      else if (repayType.equalsIgnoreCase("N"))
      {
        logger.info("Checking Disbursal Feasibility for Non Installment based loan.");
        if (shortPayAmount != null)
        {
          if (shortPayAmount.compareTo(proposedShortPayAmount) > 0)
          {
            status = "ShrtPayAmt";
          }
        }
        else status = "LoanAmt";

      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      query = null;
      disbursedAmountStr = null;
      disbursedAmount = null;
      query1 = null;
      proposedShortPayAmt = null;
      proposedShortPayAmount = null;
      query2 = null;
      shortAmountPaidStr = null;
      shortAmountPaid = null;
      query4 = null;
      loanAmtStr = null;
      query5 = null;
      maxDisbursalNo = null;
      query6 = null;
      disbursalDate = null;
      query7 = null;
      repayType = null;
      loanAmount = null;
      disbursalAmount = null;
      shortPayAmount = null;
    }
    return status;
  }

  public String getDisbursalAmountCheck(DisbursalMakerVO vo)
  {
    logger.info("Inside getDisbursalAmountCheck.....DAOImpl");
    String status = "";

    StringBuilder query3 = new StringBuilder();
    String proposedDisbDateStr = "";
    String query = "";
    SimpleDateFormat dateFormat1 = new SimpleDateFormat(this.dateForDisbursal);
    StringBuilder query1 = new StringBuilder();
    String proposedAmtStr = "";
    BigDecimal proposedDisbursalAmt = null;
    StringBuilder query2 = new StringBuilder();
    String disbursedAmtStr = "";
    BigDecimal disbursedAmt = null;
    BigDecimal loanAmt = null;
    String balanceAmtStr = "";
    BigDecimal balanceAmt = null;
    try
    {
      if (CommonFunction.checkNull(vo.getProposedDisbursalFlag()).equalsIgnoreCase("Y")) {
        String revolvingFlagQuery = new StringBuilder().append("  select P.REVOLVING_FLAG from cr_loan_dtl L  inner join cr_product_m P on P.PRODUCT_ID=L.LOAN_PRODUCT and P.REC_STATUS='A'  where L.LOAN_ID=").append(vo.getLbxLoanNoHID()).append(" and L.REC_STATUS='A'").toString();

        String revolvingFlagStatus = ConnectionDAO.singleReturn(revolvingFlagQuery);
        logger.info(new StringBuilder().append("revolvingFlagQuery: ").append(revolvingFlagQuery).append(" revolvingFlag: ").append(revolvingFlagStatus).toString());

        if (vo.getRepayMode().equalsIgnoreCase("I"))
        {
          query3.append(new StringBuilder().append("select max(Date_format(PROPOSED_DISBURSAL_DATE,'").append(this.dateFormat).append("')) from cr_loan_disbursalsch_dtl ").append("where loan_id='").append(vo.getLbxLoanNoHID()).append("' ").append("and PROPOSED_DISBURSAL_DATE <=STR_TO_DATE('").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalDate()))).append("','").append(this.dateFormat).append("')").toString());

          proposedDisbDateStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
          logger.info(new StringBuilder().append("proposedDisbDateStr: ").append(proposedDisbDateStr).toString());
          if (proposedDisbDateStr.equalsIgnoreCase(""))
          {
            query = new StringBuilder().append("select Date_format(proposed_disbursal_date,'").append(this.dateFormat).append("') from cr_loan_disbursalsch_dtl ").append("where loan_id='").append(vo.getLbxLoanNoHID()).append("' ").append("and disbursal_no='1'").toString();

            logger.info(new StringBuilder().append("query   :   ").append(query).toString());
            proposedDisbDateStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
            logger.info(new StringBuilder().append("proposedDisbDateStr: ").append(proposedDisbDateStr).toString());
          }

          try
          {
            Date proposedDisbDate = dateFormat1.parse(proposedDisbDateStr);
            logger.info(new StringBuilder().append("proposedDisbDate: ").append(proposedDisbDate).toString());
            Date actDisbDate = dateFormat1.parse(vo.getDisbursalDate());
            if (proposedDisbDate.after(actDisbDate))
            {
              status = "preProposedDisbDate";
              logger.info(new StringBuilder().append("status: ").append(status).toString());
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
          if (status.equalsIgnoreCase(""))
          {
            query = new StringBuilder().append("select count(loan_id) from cr_loan_disbursalsch_dtl where loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))).append("'").toString();
            logger.info(query);
            int count = Integer.parseInt(ConnectionDAO.singleReturn(query));
            if (count > 1)
            {
              query1.append(new StringBuilder().append("select IFNULL(sum(PROPOSED_DISBURSAL_AMOUNT),0) from cr_loan_disbursalsch_dtl where PROPOSED_DISBURSAL_DATE <= STR_TO_DATE('").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalDate()))).append("','").append(this.dateFormat).append("') ").append(" and loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))).append("'").toString());

              proposedAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
              logger.info(proposedAmtStr);
              proposedDisbursalAmt = new BigDecimal(proposedAmtStr);
              logger.info(new StringBuilder().append("proposedDisbursalAmt: ").append(proposedDisbursalAmt).toString());
              query2.append(new StringBuilder().append("select IFNULL(sum(DISBURSAL_AMOUNT),0) from cr_loan_disbursal_dtl where DISBURSAL_DATE <= STR_TO_DATE('").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalDate()))).append("','").append(this.dateFormat).append("') ").append(" and loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))).append("' and rec_status='A'").toString());

              disbursedAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
              logger.info(disbursedAmtStr);
              disbursedAmt = new BigDecimal(disbursedAmtStr);
              logger.info(new StringBuilder().append("disbursed Amt: ").append(disbursedAmt).toString());
              logger.info(new StringBuilder().append("disbursal Amt: ").append(vo.getDisbursalAmount()).toString());
              int res = 0;
              try
              {
                res = proposedDisbursalAmt.subtract(disbursedAmt).compareTo(new BigDecimal(this.myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString()));
                logger.info(new StringBuilder().append("res: ").append(res).toString());
                if (res < 0)
                {
                  status = "disbursalAmount";
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          }

        }

        if ((vo.getRepayMode().equalsIgnoreCase("N")) && (revolvingFlagStatus.equalsIgnoreCase("Y")))
        {
          query1.append(new StringBuilder().append("select loan_loan_amount from cr_loan_dtl where loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))).append("'").toString());

          String loanAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
          loanAmt = new BigDecimal(loanAmtStr);
          logger.info(new StringBuilder().append("loanAmt: ").append(loanAmt).toString());
          query2.append(new StringBuilder().append("select loan_balance_principal from cr_loan_dtl where loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))).append("'").toString());

          balanceAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
          balanceAmt = new BigDecimal(balanceAmtStr);
          logger.info(new StringBuilder().append("balanceAmt: ").append(balanceAmt).toString());
          logger.info(new StringBuilder().append("disbursal Amt: ").append(vo.getDisbursalAmount()).toString());
          int res = 0;
          try {
            res = loanAmt.subtract(balanceAmt).compareTo(new BigDecimal(this.myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString()));
            logger.info(new StringBuilder().append("res: ").append(res).toString());
            if (res < 0)
            {
              status = "disbursalAmtForNonInst";
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else if (vo.getRepayMode().equalsIgnoreCase("N"))
        {
          query1.append(new StringBuilder().append("select loan_loan_amount from cr_loan_dtl where loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))).append("'").toString());

          String loanAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
          loanAmt = new BigDecimal(loanAmtStr);
          logger.info(new StringBuilder().append("loanAmt: ").append(loanAmt).toString());
          query2.append(new StringBuilder().append("select SUM(IFNULL(disbursal_amount,0)) from CR_LOAN_DISBURSAL_DTL where LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))).append("'").toString());

          logger.info(new StringBuilder().append("query2: ").append(query2).toString());
          balanceAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));

          balanceAmt = new BigDecimal(balanceAmtStr);
          logger.info(new StringBuilder().append("balanceAmt: ").append(balanceAmt).toString());

          String totDisAmt = "";

          totDisAmt = CommonFunction.checkNull(vo.getDisbursalAmount()).trim();

          logger.info(new StringBuilder().append("Total disbursed amount:111 ").append(totDisAmt).toString());
          logger.info(new StringBuilder().append("Total disbursed amount:222 ").append(new BigDecimal(totDisAmt)).toString());
          logger.info(new StringBuilder().append("Total disbursed amount: ").append(vo.getDisbursalAmount()).toString());
          int res = 0;
          try {
            res = loanAmt.subtract(balanceAmt).compareTo(new BigDecimal(this.myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString()));
            logger.info(new StringBuilder().append("res: ").append(res).toString());
            if (res < 0)
            {
              status = "disbursalAmtForNonInst";
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      query3 = null;
      proposedDisbDateStr = null;
      query = null;
      dateFormat1 = null;
      query1 = null;
      proposedAmtStr = null;
      proposedDisbursalAmt = null;
      query2 = null;
      disbursedAmtStr = null;
      disbursedAmt = null;
      loanAmt = null;
      balanceAmtStr = null;
      balanceAmt = null;
    }
    return status;
  }

  public int saveDisbursalData(DisbursalMakerVO vo, String disbursalFlag)
  {
    boolean status = false;
    logger.info("In saveDisbursalData.....................................Dao Impl....111");
    String loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
    String loanDueDay = "";
    String loanRepayEff = "";
    String nextDueDate = "";
    ArrayList qryList = new ArrayList();
    ArrayList loanData = new ArrayList();
    ArrayList subList = new ArrayList();
    ArrayList qryListUpdate = new ArrayList();
    StringBuilder bufInsSql = new StringBuilder();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject2 = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject3 = new PrepStmtObject();
    StringBuilder query2 = new StringBuilder();
    StringBuilder query3 = new StringBuilder();
    String bpId = "";
    int maxId = 0;
    try
    {
      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS"))
      {
        query3.append(new StringBuilder().append("SELECT distinct v.BP_ID from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='").append(loanId).append("' and v.BP_TYPE='").append(CommonFunction.checkNull(vo.getDisbursalTo()).trim()).append("'").toString());

        logger.info(new StringBuilder().append("In disbursal bpid..... : ").append(query3).toString());
        bpId = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));

        vo.setLbxBusinessPartnearHID(bpId);
      }

      logger.info(new StringBuilder().append("In insert disbursal Data part").append(vo.getNextDueDate()).toString());
      if ((vo.getNextDueDate() != null) && (vo.getNextDueDate().equals("undefined"))) {
        vo.setNextDueDate("");
      }
      loanData = ConnectionDAO.sqlSelect(new StringBuilder().append("SELECT LOAN_DUE_DAY,LOAN_REPAY_EFF_DATE,NEXT_DUE_DATE FROM CR_LOAN_DTL WHERE LOAN_ID=").append(vo.getLbxLoanNoHID().trim()).toString());
      logger.info(new StringBuilder().append("loanData...").append(loanData).toString());

      int size = loanData.size();

      if (size != 0)
      {
        for (int i = 0; i < size; i++)
        {
          subList = (ArrayList)loanData.get(i);

          if (subList.size() > 0)
          {
            loanDueDay = CommonFunction.checkNull(subList.get(0)).trim();
            loanRepayEff = CommonFunction.checkNull(subList.get(1)).trim();
            nextDueDate = CommonFunction.checkNull(subList.get(2)).trim();
          }
        }

      }

      bufInsSql = new StringBuilder();
      insertPrepStmtObject = new PrepStmtObject();
      bufInsSql.append("insert into cr_loan_disbursal_dtl(loan_id, disbursal_no, disbursal_description,");
      bufInsSql.append("disbursal_date,disbursal_amount,loan_due_day,NEXT_DUE_DATE,short_amount_adjusted,disbursal_flag,");
      bufInsSql.append("rec_status,maker_id,maker_date,CUR_MONTH_EMI,NEXT_MONTH_EMI,REPAY_EFF_DATE,DISBURSAL_TO_ID,DISBURSAL_TO,PAYMENT_DATE,TA_LOAN_ID,old_repay_eff_Date,old_LOAN_DUE_DAY,old_NEXT_DUE_DATE)");
      bufInsSql.append(" values ( ");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" 'P',");
      bufInsSql.append(" ?,");
      bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
      bufInsSql.append(" ?,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? )");

      if (CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxLoanNoHID().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalNo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalNo().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalDescription().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalDate().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getCycleDate()).trim());
      }
      if (CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getNextDueDate().trim());
      }
      if (CommonFunction.checkNull(vo.getShortPayAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getShortPayAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(disbursalFlag.trim());
      }

      if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerId().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerDate().trim());
      }
      if (CommonFunction.checkNull(vo.getCurrentMonthEMI()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getCurrentMonthEMI()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getPreEMINextMonth()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getPreEMINextMonth()).trim()).toString());
      }
      if (CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase("F"))
        insertPrepStmtObject.addString(vo.getRepayEffDate().trim());
      else if ((CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase("P")) && (CommonFunction.checkNull(vo.getRepayMode()).equalsIgnoreCase("N")))
      {
        insertPrepStmtObject.addString(vo.getRepayEffDate().trim());
      }
      else {
        insertPrepStmtObject.addNull();
      }
      if (CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBusinessPartnearHID().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalTo().trim());
      }
      if (CommonFunction.checkNull(vo.getExpectedPaymentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getExpectedPaymentDate().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxTaLoanNoHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxTaLoanNoHID().trim());
      }
      if (CommonFunction.checkNull(loanRepayEff).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(loanRepayEff.trim());
      }
      if (CommonFunction.checkNull(loanDueDay).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(loanDueDay.trim());
      }
      if (CommonFunction.checkNull(nextDueDate).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(nextDueDate.trim());
      }
      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN saveDisbursalData() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In saveDisbursalData.........update status: ").append(status).toString());

      updatePrepStmtObject = new PrepStmtObject();
      logger.info(new StringBuilder().append("CommonFunction.checkNull(vo.getNextDueDate()): ").append(CommonFunction.checkNull(vo.getNextDueDate())).toString());
      query2.append("update cr_loan_dtl set ");

      if (CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")) {
        query2.append("LOAN_REPAY_EFF_DATE=LOAN_REPAY_EFF_DATE,");
      }
      if (!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")) {
        query2.append(new StringBuilder().append("LOAN_REPAY_EFF_DATE=STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),").toString());
      }
      if (CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")) {
        query2.append(" LOAN_DUE_DAY=LOAN_DUE_DAY,");
      }
      if (!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")) {
        query2.append(" LOAN_DUE_DAY=?,");
      }
      if (CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")) {
        query2.append("NEXT_DUE_DATE=NEXT_DUE_DATE,");
      }
      if (!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")) {
        query2.append(new StringBuilder().append("NEXT_DUE_DATE=STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),").toString());
      }

      if (CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append("loan_tenure=loan_tenure,");
      }
      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append(" loan_tenure=?,");
      }
      if (CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append("LOAN_NO_OF_INSTALLMENT=LOAN_NO_OF_INSTALLMENT,");
      }
      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append(" LOAN_NO_OF_INSTALLMENT=?");
      }

      query2.append(" where loan_id=? ");

      if (!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getRepayEffDate().trim());
      }

      if (!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getCycleDate().trim());
      }

      if (!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getNextDueDate().trim());
      }

      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getLoanTenure().trim());
      }

      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getLoanTenure().trim());
      }

      updatePrepStmtObject.addString(loanId);
      logger.info(new StringBuilder().append("IN saveDisbursalData() update cr_loan_dtl query3 ###1 sachinnn").append(updatePrepStmtObject.printQuery()).toString());

      updatePrepStmtObject.setSql(query2.toString());

      logger.info(new StringBuilder().append("IN saveDisbursalData() update cr_loan_dtl query3 ###2 ").append(updatePrepStmtObject.printQuery()).toString());
      qryListUpdate.add(updatePrepStmtObject);
      logger.info(new StringBuilder().append("In saveDisbursalData ....cr_loan_dtl.... update query: ").append(query2).toString());

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      logger.info(new StringBuilder().append("In saveDisbursalData.........update3 status: ").append(status).toString());

      String maxIdQuery = "Select distinct max(LOAN_DISBURSAL_ID) from cr_loan_disbursal_dtl";

      String id = ConnectionDAO.singleReturn(maxIdQuery);
      maxId = Integer.parseInt(id.toString());
      logger.info(new StringBuilder().append("maxId : ").append(maxId).toString());

      StringBuilder queryUpdate2 = new StringBuilder();
      updatePrepStmtObject2 = new PrepStmtObject();
      queryUpdate2.append(new StringBuilder().append("update cr_loan_dtl set loan_maturity_date=(select loan_maturity_date from cr_loan_disbursal_dtl_temp where loan_id='").append(vo.getLbxLoanNoHID()).append("')").toString());
      updatePrepStmtObject2.setSql(queryUpdate2.toString());
      qryListUpdate.add(updatePrepStmtObject2);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      logger.info(" Update maturity date :: 1");

      StringBuilder queryUpdate3 = new StringBuilder();
      updatePrepStmtObject3 = new PrepStmtObject();
      queryUpdate3.append(new StringBuilder().append("delete from cr_resch_installment_plan where loan_id='").append(vo.getLbxLoanNoHID()).append("'").toString());
      updatePrepStmtObject3.setSql(queryUpdate3.toString());
      qryListUpdate.add(updatePrepStmtObject3);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      logger.info(" Delete Installment Plan :: 1");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      loanId = null;
      qryList = null;
      bufInsSql = null;
      insertPrepStmtObject = null;
      updatePrepStmtObject = null;
      query2 = null;
      qryListUpdate = null;
    }
    return maxId;
  }

  public boolean updateDisbursalData(DisbursalMakerVO vo, String recStatus, String disbursalFlag)
  {
    boolean status = false;
    logger.info("In updateDisbursalData.....................................Dao Impl....111");
    String loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
    ArrayList qryList = new ArrayList();
    ArrayList qryListUpdate = new ArrayList();
    StringBuilder query = new StringBuilder();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject2 = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject3 = new PrepStmtObject();
    StringBuilder query2 = new StringBuilder();
    StringBuilder query3 = new StringBuilder();
    String bpId = "";
    try
    {
      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS"))
      {
        query3.append(new StringBuilder().append("SELECT distinct v.BP_ID from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='").append(loanId).append("' and v.BP_TYPE='").append(CommonFunction.checkNull(vo.getDisbursalTo()).trim()).append("'").toString());

        logger.info(new StringBuilder().append("In disbursal bpid..... : ").append(query3).toString());
        bpId = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));

        vo.setLbxBusinessPartnearHID(bpId);
      }

      query.append(new StringBuilder().append("update cr_loan_disbursal_dtl set disbursal_date=STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),").toString());
      query.append(new StringBuilder().append(" short_amount_adjusted=?, disbursal_amount=?, loan_due_day=?,NEXT_DUE_DATE=DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), disbursal_description=?, rec_status=?, disbursal_flag=?,").toString());
      query.append(new StringBuilder().append(" maker_id=?, maker_date=DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),CUR_MONTH_EMI=?,NEXT_MONTH_EMI=?,REPAY_EFF_DATE=DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),DISBURSAL_TO_ID=?,DISBURSAL_TO=?,PAYMENT_DATE=STR_TO_DATE(?, '").append(this.dateFormat).append("'),TA_LOAN_ID=? where loan_id=? and disbursal_no=?").toString());

      String date = vo.getNextDueDate();
      String amount = vo.getPreEMINextMonth();
      if (CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalDate().trim());
      }
      if (CommonFunction.checkNull(vo.getShortPayAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getShortPayAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getCycleDate().trim());
      }
      if (CommonFunction.checkNull(disbursalFlag).trim().equalsIgnoreCase("P"))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(date.trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalDescription().trim());
      }
      if (CommonFunction.checkNull(recStatus).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(recStatus.trim());
      }
      if (CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(disbursalFlag.trim());
      }
      if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerId().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerDate().trim());
      }
      if (CommonFunction.checkNull(vo.getCurrentMonthEMI()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getCurrentMonthEMI()).trim()).toString());
      }
      if (CommonFunction.checkNull(amount).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(amount).trim()).toString());
      }
      if (CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase("F"))
      {
        insertPrepStmtObject.addString(vo.getRepayEffDate().trim());
      }
      else if ((CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase("P")) && (CommonFunction.checkNull(vo.getRepayMode()).equalsIgnoreCase("N")))
      {
        insertPrepStmtObject.addString(vo.getRepayEffDate().trim());
      }
      else {
        insertPrepStmtObject.addNull();
      }
      if (CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBusinessPartnearHID().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalTo().trim());
      }
      if (CommonFunction.checkNull(vo.getExpectedPaymentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getExpectedPaymentDate().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxTaLoanNoHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxTaLoanNoHID().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxLoanNoHID().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalNo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalNo().trim());
      }

      insertPrepStmtObject.setSql(query.toString());
      logger.info(new StringBuilder().append("IN updateDisbursalData() update query1   :   ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);

      logger.info(new StringBuilder().append("In updateDisbursalData.........update status: ").append(status).toString());
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In updateDisbursalData.........update status: ").append(status).toString());

      query2.append("update cr_loan_dtl set ");

      if (CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")) {
        query2.append("LOAN_REPAY_EFF_DATE=LOAN_REPAY_EFF_DATE,");
      }
      if (!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")) {
        query2.append(new StringBuilder().append("LOAN_REPAY_EFF_DATE=STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),").toString());
      }
      if (CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")) {
        query2.append(" LOAN_DUE_DAY=LOAN_DUE_DAY,");
      }
      if (!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")) {
        query2.append(" LOAN_DUE_DAY=?,");
      }
      if (CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")) {
        query2.append("NEXT_DUE_DATE=NEXT_DUE_DATE,");
      }
      if (!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")) {
        query2.append(new StringBuilder().append("NEXT_DUE_DATE=STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),").toString());
      }

      if (CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append("loan_tenure=loan_tenure,");
      }
      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append(" loan_tenure=?,");
      }
      if (CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append("LOAN_NO_OF_INSTALLMENT=LOAN_NO_OF_INSTALLMENT,");
      }
      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append(" LOAN_NO_OF_INSTALLMENT=?");
      }

      query2.append(" where loan_id=? ");

      if (!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getRepayEffDate().trim());
      }

      if (!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getCycleDate().trim());
      }

      if (!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getNextDueDate().trim());
      }

      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getLoanTenure().trim());
      }

      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getLoanTenure().trim());
      }

      updatePrepStmtObject.addString(loanId);
      logger.info(new StringBuilder().append("IN saveDisbursalData() update cr_loan_dtl query3 ###3 ").append(updatePrepStmtObject.printQuery()).toString());

      updatePrepStmtObject.setSql(query2.toString());

      logger.info(new StringBuilder().append("IN saveDisbursalData() update cr_loan_dtl query3 ###4 ").append(updatePrepStmtObject.printQuery()).toString());
      qryListUpdate.add(updatePrepStmtObject);
      logger.info(new StringBuilder().append("In saveDisbursalData ....cr_loan_dtl.... update query: ").append(query2).toString());

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      logger.info(new StringBuilder().append("In saveDisbursalData.........update3 status: ").append(status).toString());

      StringBuilder queryUpdate2 = new StringBuilder();
      updatePrepStmtObject2 = new PrepStmtObject();
      queryUpdate2.append(new StringBuilder().append("update cr_loan_dtl set loan_maturity_date=(select loan_maturity_date from cr_loan_disbursal_dtl_temp where loan_id='").append(vo.getLbxLoanNoHID()).append("')").toString());
      updatePrepStmtObject2.setSql(queryUpdate2.toString());
      qryListUpdate.add(updatePrepStmtObject2);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      logger.info(" Update maturity date :: 2");

      StringBuilder queryUpdate3 = new StringBuilder();
      updatePrepStmtObject3 = new PrepStmtObject();
      queryUpdate3.append(new StringBuilder().append("delete from cr_resch_installment_plan where loan_id='").append(vo.getLbxLoanNoHID()).append("'").toString());
      updatePrepStmtObject3.setSql(queryUpdate3.toString());
      qryListUpdate.add(updatePrepStmtObject3);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      logger.info(" Delete Installment Plan :: 2");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      loanId = null;
      qryList = null;
      query = null;
      insertPrepStmtObject = null;
      updatePrepStmtObject = null;
      query2 = null;
      qryListUpdate = null;
    }
    return status;
  }

  public ArrayList<DisbursalMakerVO> getDisbursalSchedule(String loanId) {
    logger.info("Inside getDisbursalSchedule.........DAOImpl");
    ArrayList disbursalData = new ArrayList();
    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    DisbursalMakerVO vo = null;
    String query = new StringBuilder().append("select a.loan_no, e.disbursal_no, e.disbursal_description, DATE_FORMAT(e.proposed_disbursal_date,'").append(this.dateFormat).append("'),").append(" e.proposed_disbursal_amount, e.disbursal_flag,case when e.PAYMENT_TYPE='D' then 'Deferral ' when e.PAYMENT_TYPE='P' then 'Payment ' end AS PAYMENT_TYPE,e.FAVOURING,case when e.INSTRUMENT_MODE='C' then 'Cash' when e.INSTRUMENT_MODE='Q' then 'Cheque' when e.INSTRUMENT_MODE='D' then 'DD' when e.INSTRUMENT_MODE='N' then 'NEFT' when e.INSTRUMENT_MODE='R' then 'RTGS' when e.INSTRUMENT_MODE='S' then 'ADJUSTMENT' end as INSTRUMENT_MODE,e.BANK_ID,c.BANK_NAME from cr_loan_dtl a ").append(" left join cr_loan_disbursalsch_dtl e on a.loan_id=e.loan_id left join com_bank_m c on e.BANK_ID=c.BANK_ID where e.loan_id='").append(loanId).append("'").toString();
    try
    {
      logger.info(new StringBuilder().append("In getDisbursalSchedule : ").append(query).toString());
      mainlist = ConnectionDAO.sqlSelect(query);
      int size = mainlist.size();
      logger.info(new StringBuilder().append("In getDisbursalSchedule.....mainlist size: ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        subList = (ArrayList)mainlist.get(i);
        if (subList.size() > 0) {
          vo = new DisbursalMakerVO();
          vo.setLoanNo(CommonFunction.checkNull(subList.get(0)).trim());
          vo.setDisbursalNo(CommonFunction.checkNull(subList.get(1)).trim());
          vo.setDisbursalDescription(CommonFunction.checkNull(subList.get(2)).trim());
          vo.setProposedDisbursalDate(CommonFunction.checkNull(subList.get(3)).trim());
          if (CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
          {
            vo.setProposedDisbursalAmount("0");
          }
          else
          {
            Number proposedDisbursalAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
            vo.setProposedDisbursalAmount(this.myFormatter.format(proposedDisbursalAmount));
          }
          if (CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("N"))
            vo.setFinalDisbursal("Not Disbursed Yet");
          if (CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("P"))
            vo.setFinalDisbursal("Partial");
          if (CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("F"))
            vo.setFinalDisbursal("Final");
          if (CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("")) {
            vo.setFinalDisbursal("");
          }
          vo.setPaymentType(CommonFunction.checkNull(subList.get(6)).trim());
          vo.setFavouring(CommonFunction.checkNull(subList.get(7)).trim());
          vo.setInstrumentMode(CommonFunction.checkNull(subList.get(8)).trim());
          if (CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("Q")) {
            vo.setLbxInstrumentMode("B");
          }
          else if (CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("D")) {
            vo.setLbxInstrumentMode("B");
          }
          else if (CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("N")) {
            vo.setLbxInstrumentMode("B");
          }
          else if (CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("R")) {
            vo.setLbxInstrumentMode("B");
          }
          else if (CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("S")) {
            vo.setLbxInstrumentMode("S");
          }
          else {
            vo.setLbxInstrumentMode(CommonFunction.checkNull(subList.get(8)).trim());
          }
          vo.setLbxbankAccountID(CommonFunction.checkNull(subList.get(9)).trim());
          vo.setBankAccount(CommonFunction.checkNull(subList.get(10)).trim());

          disbursalData.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      mainlist = null;
      subList = null;
      vo = null;
      query = null;
    }
    return disbursalData;
  }

  public ArrayList<DisbursalMakerVO> getDisbursalData(String loanId)
  {
    logger.info("Inside getDisbursalData.........DAOImpl");
    ArrayList disbursalData = new ArrayList();
    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    DisbursalMakerVO vo = null;
    String query = new StringBuilder().append("select a.loan_no, e.disbursal_no, e.disbursal_description, DATE_FORMAT(e.disbursal_date,'").append(this.dateFormat).append("'),").append(" e.disbursal_amount, e.disbursal_flag from cr_loan_dtl a,").append(" cr_loan_disbursal_dtl e where a.loan_id=e.loan_id and e.loan_id='").append(loanId).append("' and  e.REC_STATUS='A'").toString();
    try
    {
      logger.info(new StringBuilder().append("In getDisbursalData : ").append(query).toString());
      mainlist = ConnectionDAO.sqlSelect(query);
      int size = mainlist.size();
      logger.info(new StringBuilder().append("In getDisbursalData.....mainlist size: ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        subList = (ArrayList)mainlist.get(i);
        if (subList.size() > 0) {
          vo = new DisbursalMakerVO();
          vo.setLoanNo(CommonFunction.checkNull(subList.get(0)).trim());
          vo.setDisbursalNo(CommonFunction.checkNull(subList.get(1)).trim());
          vo.setDisbursalDescription(CommonFunction.checkNull(subList.get(2)).trim());
          vo.setDisbursalDate(CommonFunction.checkNull(subList.get(3)).trim());
          if (CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
          {
            vo.setDisbursalAmount("0");
          }
          else
          {
            Number disbursalAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
            vo.setDisbursalAmount(this.myFormatter.format(disbursalAmount));
          }
          if (CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("P"))
            vo.setFinalDisbursal("Partial");
          if (CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("F"))
            vo.setFinalDisbursal("Final");
          if (CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
            vo.setFinalDisbursal("Not Disbursed Yet");
          disbursalData.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      mainlist = null;
      subList = null;
      vo = null;
      query = null;
    }
    return disbursalData;
  }

  public ArrayList<DisbursalMakerVO> calculateEMI(String disbursalAmount, String disbursalDate, String loan_id, String curDate, String userId, String repayEffDate, String disbursalStatus)
  {
    logger.info("In getDisbursalValues..................DAOImpl");
    ArrayList EMIList = new ArrayList();
    DisbursalMakerVO vo = new DisbursalMakerVO();
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    String CurrentMonthEMI = "";
    String NextMonthEMI = "";
    String s1 = "";
    String s2 = "";
    int company_id = 0;
    try
    {
      String company_id_1 = ConnectionDAO.singleReturn("select company_id from com_company_m ");
      logger.info(".............");
      logger.info(new StringBuilder().append("..company_id_1..........").append(company_id_1).toString());
      int lonID = Integer.parseInt(loan_id.trim());
      int company_id_2 = Integer.parseInt(company_id_1.trim());
      in.add(Integer.valueOf(company_id_2));
      in.add(Integer.valueOf(0));
      in.add(Integer.valueOf(lonID));
      in.add("D");
      String disDate = CommonFunction.changeFormat(disbursalDate.trim());
      if (disDate.equalsIgnoreCase(""))
      {
        disDate = "0000-00-00";
      }
      in.add(disDate);
      logger.info(new StringBuilder().append("Disbursal date......").append(disDate).toString());

      in.add(userId.trim());
      out.add(s1);
      out.add(s2);
      logger.info(new StringBuilder().append("Pre_EMI_Billing (").append(in.toString()).append(",").append(out.toString()).toString());
      outMessages = (ArrayList)ConnectionDAO.callSP("Pre_EMI_Billing", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      logger.info(new StringBuilder().append("s1  : ").append(s1).toString());
      logger.info(new StringBuilder().append("s2  : ").append(s2).toString());
      if (s1.trim().equals("E"))
      {
        if (CommonFunction.checkNull(CurrentMonthEMI).equals("")) {
          vo.setCurrentMonthEMI("0.00");
        }
        else {
          Number curAmount = this.myFormatter.parse(CurrentMonthEMI);
          vo.setCurrentMonthEMI(this.myFormatter.format(curAmount));
        }
        if (CommonFunction.checkNull(NextMonthEMI).equals("")) {
          vo.setPreEMINextMonth("0.00");
        }
        else {
          Number nextAmount = this.myFormatter.parse(NextMonthEMI);
          vo.setPreEMINextMonth(this.myFormatter.format(nextAmount));
        }
      }
      else
      {
        vo.setCurrentMonthEMI("0.00");
        vo.setPreEMINextMonth("0.00");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      s1 = null;
      s2 = null;
      in.clear();
      in = null;
      out.clear();
      out = null;
      outMessages.clear();
      outMessages = null;
    }
    EMIList.add(vo);
    return EMIList;
  }

  public ArrayList<DisbursalMakerVO> getDisbursalValues(int lbxLoanNoHID, String userId, String businessDate) {
    logger.info("In getDisbursalValues..................DAOImpl");
    ArrayList disbursalList = new ArrayList();
    int count = 0;
    String countQueryPF = new StringBuilder().append("Select count(loan_id) from cr_loan_disbursal_dtl where loan_id='").append(lbxLoanNoHID).append("' and rec_status in ('P','F')").toString();
    String countQueryPFTemp = new StringBuilder().append("Select count(loan_id) from cr_loan_disbursal_dtl_temp where loan_id='").append(lbxLoanNoHID).append("' and rec_status in ('P','F') and maker_id <> '").append(userId).append("'").toString();

    String tempCountPF = ConnectionDAO.singleReturn(countQueryPF);
    String tempCountPFTemp = ConnectionDAO.singleReturn(countQueryPFTemp);
    int countPF = Integer.parseInt(tempCountPF);
    int countPFTemp = Integer.parseInt(tempCountPFTemp);
    logger.info(new StringBuilder().append("count from select query= ").append(countPF).toString());
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    String query1 = "";
    String query2 = "";
    String query3 = "";
    String query4 = "";
    String query5 = "";
    String query6 = "";
    String maxDisbursalNoStr = "";
    String maxExpectedPayDate = "";
    String maxDisbursalDate = "";
    StringBuilder query = new StringBuilder();
    StringBuilder query7 = new StringBuilder();
    StringBuilder query8 = new StringBuilder();
    DisbursalMakerVO vo = null;
    try
    {
      if ((countPF == 0) && (countPFTemp == 0))
      {
        try
        {
          query1 = new StringBuilder().append("select SUM(IFNULL(ADVICE_AMOUNT,0) - IFNULL(TXN_ADJUSTED_AMOUNT,0) - IFNULL(AMOUNT_IN_PROCESS,0)) FROM CR_TXNADVICE_DTL WHERE LOAN_ID = '").append(lbxLoanNoHID).append("' AND BP_TYPE = 'CS' AND ADVICE_TYPE = 'R' AND REC_STATUS = 'A' AND ADVICE_DATE<=STR_TO_DATE('").append(businessDate).append("', '").append(this.dateFormat).append("')").toString();

          String proposedShortPayAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1));

          query2 = new StringBuilder().append("select SUM(IFNULL(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(lbxLoanNoHID).append("' AND REC_STATUS='A'").toString();

          String disbursedAmountStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2));

          query3 = new StringBuilder().append("select SUM(IFNULL(SHORT_AMOUNT_ADJUSTED,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(lbxLoanNoHID).append("' AND REC_STATUS='A'").toString();

          String shortAmountPaidStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3));
          logger.info(new StringBuilder().append("In getDisbursalValues.....shortAmountPaid: ").append(shortAmountPaidStr).toString());

          query4 = new StringBuilder().append("select max(disbursal_no) from cr_loan_disbursal_dtl where loan_id='").append(lbxLoanNoHID).append("'").toString();

          maxDisbursalNoStr = ConnectionDAO.singleReturn(query4);

          query5 = new StringBuilder().append("select DATE_FORMAT(max(PAYMENT_DATE),'").append(this.dateFormat).append("') from cr_loan_disbursal_dtl where loan_id='").append(lbxLoanNoHID).append("' and REC_STATUS='A'").toString();

          maxExpectedPayDate = ConnectionDAO.singleReturn(query5);

          query6 = new StringBuilder().append("select DATE_FORMAT(max(DISBURSAL_DATE) ,'").append(this.dateFormat).append("')from cr_loan_disbursal_dtl where LOAN_ID='").append(lbxLoanNoHID).append("' and REC_STATUS='A'").toString();
          maxDisbursalDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(query6));

          query7.append(new StringBuilder().append("SELECT SUM(IFNULL(DISBURSAL_AMOUNT,0)) FROM CR_LOAN_DISBURSAL_DTL_TEMP WHERE LOAN_ID = '").append(lbxLoanNoHID).append("' AND REC_STATUS='P'").toString());

          String disbursedAmountStrTemp = CommonFunction.checkNull(ConnectionDAO.singleReturn(query7.toString()));

          int maxDisbursalNo = 0;
          if ((CommonFunction.checkNull(maxDisbursalNoStr).equalsIgnoreCase("")) || (maxDisbursalNoStr == null))
            maxDisbursalNo = 1;
          else
            maxDisbursalNo = Integer.parseInt(CommonFunction.checkNull(maxDisbursalNoStr)) + 1;
          logger.info(new StringBuilder().append("Disbursal number: ").append(maxDisbursalNo).toString());
          String maxDisbursalNoStrNew = null;
          String queryMaxDis = null;
          queryMaxDis = new StringBuilder().append("select max(disbursal_no) from cr_loan_disbursal_dtl_temp where loan_id='").append(lbxLoanNoHID).append("'").toString();

          maxDisbursalNoStrNew = ConnectionDAO.singleReturn(queryMaxDis);
          logger.info(new StringBuilder().append("In getDisbursalValues.....maxDisbursalNo: ").append(maxDisbursalNoStrNew).toString());
          if (!CommonFunction.checkNull(maxDisbursalNoStrNew).equalsIgnoreCase("")) {
            maxDisbursalNo = Integer.parseInt(CommonFunction.checkNull(maxDisbursalNoStrNew)) + 1;
          }

          query.append(new StringBuilder().append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("'), c.product_desc, d.scheme_desc").toString());
          query.append(",a.loan_id,a.loan_due_day,a.loan_repayment_type,");
          query.append(new StringBuilder().append(" (select count(PDC_LOAN_ID) from cr_pdc_instrument_dtl where  PDC_STATUS='A' and PDC_PURPOSE='INSTALLMENT' and PDC_LOAN_ID='").append(lbxLoanNoHID).append("')").toString());
          query.append(" ,(select REVOLVING_FLAG from cr_product_m where product_id=a.LOAN_PRODUCT)REVOLVING_FLAG,LOAN_BALANCE_PRINCIPAL");
          query.append(new StringBuilder().append(" ,ifnull((select sum(disbursal_amount)disbursal_amount from cr_loan_disbursal_dtl where rec_status='F' and loan_id='").append(CommonFunction.checkNull(Integer.valueOf(lbxLoanNoHID))).append("'),0.00)disbursal_amount,a.LOAN_INSTALLMENT_TYPE , ifnull(sum(r.prepayment_amount),'0.00')prepayment_amount  ").toString());
          query.append(new StringBuilder().append(" , DATE_FORMAT(a.INTEREST_DUE_DATE,'").append(this.dateFormat).append("') as INTEREST_DUE_DATE  ").toString());
          query.append(new StringBuilder().append(" ,a.INT_METHOD,a.INT_FREQ ,a.INT_COMP_FREQ,DATE_FORMAT( a.LOAN_REPAY_EFF_DATE,'").append(this.dateFormat).append("'), a.EDIT_DUE_DATE,a.LOAN_TENURE ").toString());
          query.append(" from cr_loan_dtl a,gcd_customer_m b,");
          query.append(" cr_product_m c, cr_scheme_m d, cr_resch_dtl r where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product");
          query.append(new StringBuilder().append(" and d.scheme_id = a.loan_scheme and a.loan_id = r.loan_id and a.loan_id='").append(lbxLoanNoHID).append("' and a.rec_status='A' ").toString());

          logger.info(new StringBuilder().append("In getDisbursalValues By using Ajax:::::::::::: ").append(query).toString());
          mainlist = ConnectionDAO.sqlSelect(query.toString());
          int size = mainlist.size();
          logger.info(new StringBuilder().append("In getDisbursalValues.....mainlist size: ").append(size).toString());
          for (int i = 0; i < size; i++)
          {
            subList = (ArrayList)mainlist.get(i);
            if (subList.size() > 0)
            {
              vo = new DisbursalMakerVO();
              vo.setLoanNo(CommonFunction.checkNull(subList.get(0)).trim());
              vo.setCustomerName(CommonFunction.checkNull(subList.get(1)).trim());

              if (CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
              {
                vo.setLoanAmt("0");
              }
              else
              {
                Number loanAmt = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
                vo.setLoanAmt(this.myFormatter.format(loanAmt));
              }

              vo.setLoanApprovalDate(CommonFunction.checkNull(subList.get(3)).trim());
              vo.setProduct(CommonFunction.checkNull(subList.get(4)).trim());
              vo.setScheme(CommonFunction.checkNull(subList.get(5)).trim());
              vo.setLbxLoanNoHID(CommonFunction.checkNull(subList.get(6)).trim());
              vo.setCycleDateValue(CommonFunction.checkNull(subList.get(7)).trim());
              vo.setRepayMode(CommonFunction.checkNull(subList.get(8)).trim());
              if (CommonFunction.checkNull(subList.get(9)).trim().equalsIgnoreCase(""))
                vo.setPdcDepositCount("0");
              else {
                vo.setPdcDepositCount(CommonFunction.checkNull(subList.get(9)).trim());
              }

              if (CommonFunction.checkNull(proposedShortPayAmtStr).trim().equalsIgnoreCase(""))
              {
                vo.setProposedShortPayAmount("0");
              }
              else
              {
                Number proposedShortPayAmt = this.myFormatter.parse(CommonFunction.checkNull(proposedShortPayAmtStr).trim());
                vo.setProposedShortPayAmount(this.myFormatter.format(proposedShortPayAmt));
              }

              if (CommonFunction.checkNull(disbursedAmountStr).trim().equalsIgnoreCase(""))
              {
                vo.setDisbursedAmount("0");
              }
              else
              {
                Number disbursedAmount = this.myFormatter.parse(CommonFunction.checkNull(disbursedAmountStr).trim());
                vo.setDisbursedAmount(this.myFormatter.format(disbursedAmount));
              }

              if (CommonFunction.checkNull(shortAmountPaidStr).trim().equalsIgnoreCase(""))
              {
                vo.setAdjustedShortPayAmount("0");
              }
              else
              {
                Number shortAmountPaid = this.myFormatter.parse(CommonFunction.checkNull(shortAmountPaidStr).trim());
                vo.setAdjustedShortPayAmount(this.myFormatter.format(shortAmountPaid));
              }

              if (CommonFunction.checkNull(disbursedAmountStrTemp).trim().equalsIgnoreCase(""))
              {
                vo.setDisbursedAmountTemp("0");
              }
              else
              {
                Number disbursedAmountTemp = this.myFormatter.parse(CommonFunction.checkNull(disbursedAmountStrTemp).trim());
                vo.setDisbursedAmountTemp(this.myFormatter.format(disbursedAmountTemp));
              }

              vo.setDisbursalNo(CommonFunction.checkNull(Integer.valueOf(maxDisbursalNo)).trim());

              vo.setMaxExpectedPayDate(CommonFunction.checkNull(maxExpectedPayDate).trim());

              vo.setDisbursalDate(maxDisbursalDate);

              vo.setRevolvingFlag(CommonFunction.checkNull(subList.get(10)).trim());
              vo.setBalancePrinc(CommonFunction.checkNull(subList.get(11)).trim());
              vo.setForwardedAmt(CommonFunction.checkNull(subList.get(12)).trim());
              vo.setInstallmentType(CommonFunction.checkNull(subList.get(13)).trim());
              vo.setPrePartmentAmount(CommonFunction.checkNull(subList.get(14)).trim());
              vo.setInterestDueDate(CommonFunction.checkNull(subList.get(15)).trim());

              vo.setInterestCalculationMethod(CommonFunction.checkNull(subList.get(16)).trim());
              vo.setInterestFrequency(CommonFunction.checkNull(subList.get(17)).trim());
              vo.setInterestCompoundingFrequency(CommonFunction.checkNull(subList.get(18)).trim());
              logger.info(new StringBuilder().append("Object Value: ").append(CommonFunction.checkNull(subList.get(19)).trim()).append("------------").append(CommonFunction.checkNull(subList.get(20)).trim()).toString());
              vo.setOldRepayEffDate(CommonFunction.checkNull(subList.get(19)).trim());
              vo.setEditDueDate(CommonFunction.checkNull(subList.get(20)).trim());
              vo.setLoanTenure(CommonFunction.checkNull(subList.get(21)).trim());
              disbursalList.add(vo);
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      else if (count > 0)
      {
        logger.info(new StringBuilder().append("Disbursal List Size: ").append(disbursalList.size()).toString());
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      countQueryPF = null;
      tempCountPF = null;
      insertPrepStmtObject = null;
      mainlist = null;
      subList = null;
      query1 = null;
      query2 = null;
      query3 = null;
      query4 = null;
      query7 = null;
      maxDisbursalNoStr = null;
      query = null;
      vo = null;
    }
    return disbursalList;
  }

  public ArrayList<PaymentMakerForCMVO> viewReceivableForDisbursal(int loanId)
  {
    ArrayList viewReceivabList = new ArrayList();

    ArrayList mainList = new ArrayList();
    ArrayList subList = new ArrayList();
    StringBuilder query = new StringBuilder();
    PaymentMakerForCMVO paymentVO = null;
    try {
      logger.info(" In viewReceivableForDisbursal....");
      query.append(new StringBuilder().append("SELECT DATE_FORMAT(ADVICE_DATE,'").append(this.dateFormat).append("'),(Select CHARGE_DESC From com_charge_code_m ").append(" Where CHARGE_CODE=CHARGE_CODE_ID) CHARGE,ORG_ADVICE_AMOUNT,WAIVE_OFF_AMOUNT,TDS_AMOUNT,").append(" TXN_ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,").append(" (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,ADVICE_AMOUNT,ADVICE_TYPE,BP_TYPE").append(" FROM cr_txnadvice_dtl  WHERE REC_STATUS in('A','F') ").append(" AND ADVICE_TYPE in ('R','P') AND LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(loanId)).trim())).append("'").append(" AND BP_TYPE in(select bp_type from business_partner_view where LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(loanId)).trim())).append("') ").append(" ORDER BY ADVICE_DATE ASC").toString());

      logger.info(new StringBuilder().append("In viewReceivableForDisbursal: ").append(query).toString());

      mainList = ConnectionDAO.sqlSelect(query.toString());
      int size = mainList.size();
      for (int i = 0; i < size; i++)
      {
        subList = (ArrayList)mainList.get(i);
        if (subList.size() > 0) {
          paymentVO = new PaymentMakerForCMVO();
          paymentVO.setPaymentDate(CommonFunction.checkNull(subList.get(0)).trim());
          paymentVO.setChargeDesc(CommonFunction.checkNull(subList.get(1)).trim());
          if (CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase("")) {
            paymentVO.setOriginalAmount("0");
          }
          else {
            Number orgAmt = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
            paymentVO.setOriginalAmount(this.myFormatter.format(orgAmt));
          }

          if (CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase("")) {
            paymentVO.setWaiveOffAmount("0");
          }
          else {
            Number waivedOffAmt = this.myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
            paymentVO.setWaiveOffAmount(this.myFormatter.format(waivedOffAmt));
          }

          if (CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase("")) {
            paymentVO.setTdsadviseAmount("0");
          }
          else {
            Number tdsAdviceAmt = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
            paymentVO.setTdsadviseAmount(this.myFormatter.format(tdsAdviceAmt));
          }

          if (CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("")) {
            paymentVO.setAdjustedAmount("0");
          }
          else {
            Number adjustedAmt = this.myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
            paymentVO.setAdjustedAmount(this.myFormatter.format(adjustedAmt));
          }

          if (CommonFunction.checkNull(subList.get(6)).trim().equalsIgnoreCase("")) {
            paymentVO.setAmountInProcess("0");
          }
          else {
            Number amtInProc = this.myFormatter.parse(CommonFunction.checkNull(subList.get(6)).trim());
            paymentVO.setAmountInProcess(this.myFormatter.format(amtInProc));
          }

          if (CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase("")) {
            paymentVO.setBalanceAmount("0");
          }
          else {
            Number balAmt = this.myFormatter.parse(CommonFunction.checkNull(subList.get(7)).trim());
            paymentVO.setBalanceAmount(this.myFormatter.format(balAmt));
          }

          if (CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("")) {
            paymentVO.setAdviceAmount("0");
          }
          else {
            Number adviceAmt = this.myFormatter.parse(CommonFunction.checkNull(subList.get(8)).trim());
            paymentVO.setAdviceAmount(this.myFormatter.format(adviceAmt));
          }
          if (CommonFunction.checkNull(subList.get(9)).trim().equalsIgnoreCase("P"))
            paymentVO.setPaymentMode("Payable");
          else {
            paymentVO.setPaymentMode("Receivable");
          }
          if (CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase("CS"))
            paymentVO.setBusinessPartnerType("CUSTOMER");
          else if (CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase("MF"))
            paymentVO.setBusinessPartnerType("MANUFACTURER ");
          else if (CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase("SU"))
            paymentVO.setBusinessPartnerType("SUPPLIER");
          else if (CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase("OTH"))
            paymentVO.setBusinessPartnerType("OTHER");
          else if (CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase("CP")) {
            paymentVO.setBusinessPartnerType("CONSORTIUM PARTINER");
          }
          viewReceivabList.add(paymentVO);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      mainList = null;
      subList = null;
      query = null;
      paymentVO = null;
    }
    return viewReceivabList;
  }

  public ArrayList getFromLoanDtl(String loanId)
  {
    ArrayList list = new ArrayList();

    logger.info("In getFromLoanDtl: ");

    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    StringBuilder query = new StringBuilder();
    StringBuilder bussIrrQ = new StringBuilder();
    RepayScheduleVo repvo = null;
    try
    {
      query.append("select LOAN_RATE_METHOD,LOAN_FLAT_RATE,LOAN_EFF_RATE,LOAN_IRR1,LOAN_IRR2,UPFRONT_ROUNDING_AMOUNT ");
      query.append(new StringBuilder().append(" from cr_loan_dtl where LOAN_ID=").append(loanId).append("").toString());
      logger.info(new StringBuilder().append("Query in getFromLoanDtl-----").append(query.toString()).toString());
      bussIrrQ.append(new StringBuilder().append("select DEAL_BUSINESS_IRR from cr_deal_loan_dtl where DEAL_LOAN_ID=(select LOAN_DEAL_LOAN_ID from cr_loan_dtl where LOAN_ID=").append(loanId).append(")").toString());
      logger.info(new StringBuilder().append("Query in getFromLoanDtl--DEAL_BUSINESS_IRR---").append(bussIrrQ.toString()).toString());
      String bussIrr = ConnectionDAO.singleReturn(bussIrrQ.toString());
      logger.info(new StringBuilder().append("Query in getFromLoanDtl--bussIrr---").append(bussIrr).toString());
      mainlist = ConnectionDAO.sqlSelect(query.toString());
      int size = mainlist.size();
      for (int i = 0; i < size; i++) {
        subList = (ArrayList)mainlist.get(i);
        repvo = new RepayScheduleVo();
        if (subList.size() > 0)
        {
          repvo.setLoanRateMethod(CommonFunction.checkNull(subList.get(0)).trim());
          if (!CommonFunction.checkNull(subList.get(1)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(1)).trim());
            repvo.setFinalRate(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
            repvo.setEffectiveRate(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
            repvo.setMktIRR1(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
            repvo.setMktIRR2(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
          {
            Number UPFRONT_ROUNDING_AMOUNT = this.myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
            repvo.setUpfrontRoundingAmount(this.myFormatter.format(UPFRONT_ROUNDING_AMOUNT));
          }
          else {
            repvo.setUpfrontRoundingAmount("0.00");
          }

          if (CommonFunction.checkNull(subList.get(0)).trim().equalsIgnoreCase("E"))
          {
            repvo.setFinalRate("");
          }
          repvo.setBussIrr(bussIrr);
        }

        list.add(repvo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      mainlist = null;
      subList = null;
      query = null;
      bussIrrQ = null;
      repvo = null;
    }
    return list;
  }

  public ArrayList getRepaySched(String loanId)
  {
    ArrayList list = new ArrayList();
    logger.info("In getRepaySched: ");
    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    StringBuilder query = new StringBuilder();
    RepayScheduleVo repvo = null;
    try
    {
      query.append(new StringBuilder().append("select INSTL_NO,DATE_FORMAT(INSTL_DATE,'").append(this.dateFormat).append("'),INSTL_AMOUNT,PRIN_COMP,INT_COMP,EXCESS_INT,").toString());
      query.append(new StringBuilder().append(" if(ADV_FLAG='Y','YES','NO'),PRIN_OS,OTHER_CHARGES,vat_amount,service_amount from cr_repaysch_dtl where LOAN_ID=").append(loanId).append("").toString());

      logger.info(new StringBuilder().append("Query in getRepaySched-----").append(query.toString()).toString());
      mainlist = ConnectionDAO.sqlSelect(query.toString());
      int size = mainlist.size();
      for (int i = 0; i < size; i++) {
        subList = (ArrayList)mainlist.get(i);
        repvo = new RepayScheduleVo();
        if (subList.size() > 0) {
          repvo.setInstNo(CommonFunction.checkNull(subList.get(0)).trim());
          repvo.setDueDate(CommonFunction.checkNull(subList.get(1)).trim());

          if (!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
            repvo.setInstAmount(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
            repvo.setPrinciple(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
            repvo.setInstCom(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
            repvo.setExcess(this.myFormatter.format(reconNum));
          }

          repvo.setAdvFlag(CommonFunction.checkNull(subList.get(6)).trim());
          if (!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(7)).trim());
            repvo.setPrinOS(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(8)).trim());
            repvo.setOtherCharges(this.myFormatter.format(reconNum));
          }
          else
          {
            repvo.setOtherCharges("0.00");
          }

          if (!CommonFunction.checkNull(subList.get(9)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(9)).trim());
            repvo.setVatAmount(this.myFormatter.format(reconNum));
          }
          else
          {
            repvo.setVatAmount("0.00");
          }
          if (!CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(10)).trim());
            repvo.setServiceAmount(this.myFormatter.format(reconNum));
          }
          else
          {
            repvo.setServiceAmount("0.00");
          }
        }

        list.add(repvo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      mainlist = null;
      subList = null;
      query = null;
      repvo = null;
    }
    return list;
  }

  public String generateRepySchedule(String loanId, String makerId)
  {
    boolean status = false;

    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    String s1 = "";
    String s2 = "";
    String procval = "";
    try
    {
      logger.info("In Generate_Repayment_schedule Procedure: ");
      in.add("LIM");
      in.add(loanId);

      out.add(s1);
      out.add(s2);

      outMessages = (ArrayList)ConnectionDAO.callSP("Generate_Repayment_schedule", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));

      if ((s1 != null) && (s1.equalsIgnoreCase("S")))
      {
        status = true;
        procval = s1;
      } else {
        procval = s2;
      }
      logger.info(new StringBuilder().append("status: ").append(status).toString());
      logger.info(new StringBuilder().append("s2: ").append(s2).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      s1 = null;
      s2 = null;
      in.clear();
      in = null;
      out.clear();
      out = null;
      outMessages.clear();
      outMessages = null;
    }
    return procval;
  }

  public ArrayList<DisbursalMakerVO> getLoanDueDay(String lbxLoanNoHID)
  {
    ArrayList list = new ArrayList();

    String query = "";
    DisbursalMakerVO vo = null;
    ArrayList product = null;
    ArrayList data = null;
    try {
      logger.info("In getLoanDueDay..........................DAOImpl");
      query = new StringBuilder().append("select loan_due_day from cr_loan_dtl where loan_id='").append(lbxLoanNoHID).append("'").toString();

      product = ConnectionDAO.sqlSelect(query);
      int size = product.size();
      logger.info(new StringBuilder().append("getCycleDateList ").append(size).toString());
      for (int i = 0; i < size; i++) {
        logger.info(new StringBuilder().append("getNoteCode ").append(CommonFunction.checkNull(product.get(i)).toString()).toString());
        data = (ArrayList)product.get(i);
        if (data.size() > 0) {
          vo = new DisbursalMakerVO();
          vo.setCycleDateValue(CommonFunction.checkNull(data.get(0)).trim());
          list.add(vo);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      query = null;
      vo = null;
      product = null;
      data = null;
    }
    return list;
  }
  public ArrayList<PostDisbursalDocVO> getDocumentStage() {
    ArrayList list = new ArrayList();
    try {
      logger.info("In getStage..........................DAOImpl");
      String query = "select Description,value from generic_master where generic_key='DOC_STAGE' and rec_status ='A'";
      PostDisbursalDocVO vo = null;
      ArrayList product = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getStage ").append(product.size()).toString());
      for (int i = 0; i < product.size(); i++)
      {
        ArrayList data = (ArrayList)product.get(i);
        for (int k = 0; k < data.size(); k++)
        {
          vo = new PostDisbursalDocVO();
          vo.setStageDescription(data.get(0).toString());
          vo.setStageValue(data.get(1).toString());
        }
        list.add(vo);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }
  public ArrayList<PostDisbursalDocVO> searchPostDisbursalDocs(PostDisbursalDocVO vo, String type) {
    logger.info("In searchPostDisbursalDocs():::::");
    String dealId = null;
    String loanId = null;
    String customerName = null;
    String docStage = null;
    String searchStatus = null;
    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;
    ArrayList searchlist = new ArrayList();
    ArrayList detailList = new ArrayList();
    StringBuffer bufInsSql = new StringBuffer();
    StringBuffer bufInsSqlTempCount = new StringBuffer();
    String newType = null;
    try {
      logger.info("In searchPostDisbursalDocs().......Dao Impl");

      loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
      dealId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()));
      customerName = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()));
      docStage = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDocStage()));
      searchStatus = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchStatusPDD()));
      if ((docStage.equalsIgnoreCase("POD")) && (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A")))
      {
        logger.info("Running Proc for POD Documents.");
        collectDocuments("LIM", loanId, "POD");
      }
      if ((type.equalsIgnoreCase("P")) && (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P")))
      {
        newType = "P";
      }
      else if (type.equalsIgnoreCase("F"))
      {
        newType = "F";
      }
      else
      {
        newType = "A";
      }

      if (!loanId.equalsIgnoreCase(""))
      {
        bufInsSql.append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, ");
        bufInsSql.append(new StringBuilder().append("DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("'),c.product_desc, d.scheme_desc,a.loan_id ").toString());
        bufInsSql.append(" from cr_loan_dtl a,gcd_customer_m b, cr_product_m c, cr_scheme_m d,");

        if ((CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P")) || (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("F")))
        {
          bufInsSql.append(" cr_document_dtl_temp e ");
          bufInsSql.append(new StringBuilder().append(" where b.customer_id = a.loan_customer_id  and c.product_id = a.loan_product and d.scheme_id = a.loan_scheme and e.rec_status = '").append(newType).append("'").append(" and e.stage_id='").append(docStage).append("'").append(" and a.loan_id=e.txnid").toString());
        }
        else
        {
          bufInsSql.append(" cr_document_dtl e ");
          bufInsSql.append(new StringBuilder().append(" where b.customer_id = a.loan_customer_id  and c.product_id = a.loan_product and d.scheme_id = a.loan_scheme  and e.stage_id='").append(docStage).append("'").append(" and a.loan_id=e.txnid").toString());
        }

        if (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
        {
          bufInsSql.append(" and e.txnid not in(select txnid from cr_document_dtl_temp  ");
          bufInsSql.append(" where  rec_status in('P','F') ");
          bufInsSql.append(new StringBuilder().append(" and stage_id='").append(docStage).append("'").toString());
          bufInsSql.append(new StringBuilder().append(" and txnid=").append(loanId).append(")").toString());
        }

        bufInsSqlTempCount.append("select count(1) from (select distinct a.loan_no, b.customer_name, a.loan_loan_amount, ");

        bufInsSqlTempCount.append(new StringBuilder().append("DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("')loan_approvaldate,c.product_desc, ").append(" d.scheme_desc,a.loan_id from cr_loan_dtl a,gcd_customer_m b,").append(" cr_product_m c, cr_scheme_m d, ").toString());

        if ((CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P")) || (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("F")))
        {
          bufInsSqlTempCount.append("cr_document_dtl_temp e ");
          bufInsSqlTempCount.append(new StringBuilder().append(" where b.customer_id = a.loan_customer_id  and c.product_id = a.loan_product and d.scheme_id = a.loan_scheme and e.rec_status = '").append(newType).append("'").append(" and e.stage_id='").append(docStage).append("'").append(" and a.loan_id=e.txnid").toString());
        }
        else
        {
          bufInsSqlTempCount.append("cr_document_dtl e ");
          bufInsSqlTempCount.append(new StringBuilder().append(" where b.customer_id = a.loan_customer_id  and c.product_id = a.loan_product and d.scheme_id = a.loan_scheme  and e.stage_id='").append(docStage).append("'").append(" and a.loan_id=e.txnid").toString());
        }

        if (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
        {
          bufInsSqlTempCount.append(" and e.txnid not in(select txnid from cr_document_dtl_temp  ");
          bufInsSqlTempCount.append(" where  rec_status in('P','F') ");
          bufInsSqlTempCount.append(new StringBuilder().append(" and stage_id='").append(docStage).append("'").toString());
          bufInsSqlTempCount.append(new StringBuilder().append(" and txnid=").append(loanId).append(")").toString());
        }
      }
      if (!dealId.equalsIgnoreCase("")) {
        bufInsSql.append("select distinct a.deal_no, b.customer_name, c.product_desc, d.scheme_desc,a.deal_id ");

        bufInsSql.append(" from cr_deal_dtl a,cr_deal_loan_dtl f, cr_deal_customer_m b, cr_product_m c, cr_scheme_m d, ");

        if ((CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P")) || (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("F")))
        {
          bufInsSql.append(" cr_document_dtl_temp e ");
          bufInsSql.append(new StringBuilder().append(" where a.deal_id=f.deal_id and b.customer_id = a.deal_customer_id and c.product_id = f.deal_product and d.scheme_id = f.deal_scheme and e.rec_status = '").append(newType).append("'").append(" and e.stage_id='").append(docStage).append("'").append(" and a.deal_id=e.txnid").toString());
        }
        else
        {
          bufInsSql.append(" cr_document_dtl e ");
          bufInsSql.append(new StringBuilder().append(" where a.deal_id=f.deal_id and b.customer_id = a.deal_customer_id and c.product_id = f.deal_product and d.scheme_id = f.deal_scheme  and e.stage_id='").append(docStage).append("'").append(" and a.deal_id=e.txnid").toString());
        }

        if (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
        {
          bufInsSql.append(" and e.txnid not in(select txnid from cr_document_dtl_temp  ");
          bufInsSql.append(" where  rec_status in('P','F') ");
          bufInsSql.append(new StringBuilder().append(" and stage_id='").append(docStage).append("'").toString());
          bufInsSql.append(new StringBuilder().append(" and txnid=").append(dealId).append(")").toString());
        }
        bufInsSqlTempCount.append("select count(1) from  (select distinct a.deal_no, b.customer_name, c.product_desc, d.scheme_desc, a.deal_id from cr_deal_dtl a,cr_deal_loan_dtl f, cr_deal_customer_m b, cr_product_m c, cr_scheme_m d, ");

        if ((CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P")) || (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("F")))
        {
          bufInsSqlTempCount.append("cr_document_dtl_temp e ");
          bufInsSqlTempCount.append(new StringBuilder().append(" where a.deal_id=f.deal_id and b.customer_id = a.deal_customer_id and c.product_id = f.deal_product and d.scheme_id = f.deal_scheme and e.rec_status = '").append(newType).append("'").append(" and e.stage_id='").append(docStage).append("'").append(" and a.deal_id=e.txnid").toString());
        }
        else
        {
          bufInsSqlTempCount.append("cr_document_dtl e ");
          bufInsSqlTempCount.append(new StringBuilder().append(" where a.deal_id=f.deal_id and b.customer_id = a.deal_customer_id and c.product_id = f.deal_product and d.scheme_id = f.deal_scheme  and e.stage_id='").append(docStage).append("'").append(" and a.deal_id=e.txnid").toString());
        }

        if (CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
        {
          bufInsSqlTempCount.append(" and e.txnid not in(select txnid from cr_document_dtl_temp  ");
          bufInsSqlTempCount.append(" where rec_status in('P','F') ");
          bufInsSqlTempCount.append(new StringBuilder().append(" and stage_id='").append(docStage).append("'").toString());
          bufInsSqlTempCount.append(new StringBuilder().append(" and txnid=").append(dealId).append(")").toString());
        }
      }
      boolean appendSQL = false;

      if ((loanId.equalsIgnoreCase("")) && (customerName.equalsIgnoreCase("")) && (dealId.equalsIgnoreCase("")) && (docStage.equalsIgnoreCase(""))) {
        bufInsSqlTempCount.append(") as b");
      }

      if ((!loanId.equalsIgnoreCase("")) && (!customerName.equalsIgnoreCase("")) && (!dealId.equalsIgnoreCase("")) && (!docStage.equalsIgnoreCase(""))) {
        bufInsSql.append(new StringBuilder().append(" and a.deal_id='").append(dealId).append("' AND a.Loan_Id ='").append(loanId).append("' AND b.Customer_Name like'%").append(customerName).append("%'").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" and a.deal_id='").append(dealId).append("' AND a.Loan_Id ='").append(loanId).append("' AND b.Customer_Name like'%").append(customerName).append("%')as b").toString());
      }

      if ((loanId.equalsIgnoreCase("")) || (customerName.equalsIgnoreCase("")) || (dealId.equalsIgnoreCase("")) || (docStage.equalsIgnoreCase(""))) {
        appendSQL = true;
      }

      if (!dealId.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" AND a.deal_id='").append(dealId).append("' ").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND a.deal_id='").append(dealId).append("' ").toString());
        appendSQL = true;
      }

      if (!loanId.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" AND a.Loan_Id ='").append(loanId).append("' ").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND a.Loan_Id ='").append(loanId).append("' ").toString());
        appendSQL = true;
      }

      if (!customerName.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" AND b.Customer_Name like'%").append(customerName).append("%' ").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND b.Customer_Name like'%").append(customerName).append("%' ").toString());
        appendSQL = true;
      }

      if ((!loanId.equalsIgnoreCase("")) || (!customerName.equalsIgnoreCase("")) || (!dealId.equalsIgnoreCase("")) || (!docStage.equalsIgnoreCase(""))) {
        bufInsSqlTempCount.append(") as b");
        appendSQL = true;
      }

      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
      logger.info(new StringBuilder().append("  searchPostDisbursalDocs() bufInsSqlTempCount : ").append(bufInsSqlTempCount.toString()).toString());

      if (((dealId == null) && (loanId == null) && (customerName == null) && (docStage == null)) || ((dealId.equalsIgnoreCase("")) && (loanId.equalsIgnoreCase("")) && (customerName.equalsIgnoreCase("")) && (docStage.equalsIgnoreCase(""))) || (vo.getCurrentPageLink() > 1))
      {
        logger.info(new StringBuilder().append("current PAge Link no .................... ").append(vo.getCurrentPageLink()).toString());
        if (vo.getCurrentPageLink() > 1)
        {
          startRecordIndex = (vo.getCurrentPageLink() - 1) * this.no;
          endRecordIndex = this.no;
          logger.info(new StringBuilder().append("startRecordIndex .................... ").append(startRecordIndex).toString());
          logger.info(new StringBuilder().append("endRecordIndex .................... ").append(endRecordIndex).toString());
        }
        bufInsSql.append(new StringBuilder().append(" limit ").append(startRecordIndex).append(",").append(endRecordIndex).toString());
      }
      logger.info(new StringBuilder().append("search Query...else-------.").append(bufInsSql).toString());
      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

      int size = searchlist.size();
      for (int i = 0; i < size; i++) {
        ArrayList data = (ArrayList)searchlist.get(i);
        if (data.size() > 0) {
          if (!loanId.equalsIgnoreCase(""))
          {
            PostDisbursalDocVO vo1 = new PostDisbursalDocVO();
            if ((newType.equalsIgnoreCase("A")) || (newType.equalsIgnoreCase("P")))
            {
              vo1.setLoanNo(new StringBuilder().append("<a href=postDisbursalDocSearch.do?method=openPostDisbursalDocMaker&loanId=").append(CommonFunction.checkNull(data.get(6)).trim()).append("&stage=").append(CommonFunction.checkNull(vo.getDocStage()).trim()).append(">").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
            }

            if (newType.equalsIgnoreCase("F"))
            {
              vo1.setLoanNo(new StringBuilder().append("<a href=postDisbursalDocSearch.do?method=openPostDisbursalDocAuthor&loanId=").append(CommonFunction.checkNull(data.get(6)).trim()).append("&stage=").append(CommonFunction.checkNull(vo.getDocStage()).trim()).append(">").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
            }

            vo1.setCustomerName(CommonFunction.checkNull(data.get(1)).trim());
            vo1.setLoanAmt(CommonFunction.checkNull(data.get(2)).trim());
            vo1.setLoanApprovalDate(CommonFunction.checkNull(data.get(3)).trim());
            vo1.setProduct(CommonFunction.checkNull(data.get(4)).trim());
            vo1.setScheme(CommonFunction.checkNull(data.get(5)).trim());
            vo1.setLbxLoanNoHID(CommonFunction.checkNull(data.get(6)).trim());
            vo1.setDocStage(vo.getDocStage());
            vo1.setTotalRecordSize(count);
            detailList.add(vo1);
          }
          if (!dealId.equalsIgnoreCase(""))
          {
            PostDisbursalDocVO vo1 = new PostDisbursalDocVO();
            if ((newType.equalsIgnoreCase("A")) || (newType.equalsIgnoreCase("P")))
            {
              vo1.setDealNo(new StringBuilder().append("<a href=postDisbursalDocSearch.do?method=openPostDisbursalDocMaker&dealId=").append(CommonFunction.checkNull(data.get(4)).trim()).append("&stage=").append(CommonFunction.checkNull(vo.getDocStage()).trim()).append(">").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
            }

            if (newType.equalsIgnoreCase("F"))
            {
              vo1.setDealNo(new StringBuilder().append("<a href=postDisbursalDocSearch.do?method=openPostDisbursalDocAuthor&dealId=").append(CommonFunction.checkNull(data.get(4)).trim()).append("&stage=").append(CommonFunction.checkNull(vo.getDocStage()).trim()).append(">").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
            }

            vo1.setCustomerName(CommonFunction.checkNull(data.get(1)).trim());
            vo1.setProduct(CommonFunction.checkNull(data.get(2)).toString());
            vo1.setScheme(CommonFunction.checkNull(data.get(3)).trim());
            vo1.setLbxDealNo(CommonFunction.checkNull(data.get(4)).trim());
            vo1.setDocStage(vo.getDocStage());
            vo1.setTotalRecordSize(count);
            detailList.add(vo1);
          }
        }
        data = null;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      vo = null;
      dealId = null;
      loanId = null;
      customerName = null;
      docStage = null;
      searchStatus = null;
      type = null;
      bufInsSql = null;
      bufInsSqlTempCount = null;
      newType = null;
      searchlist.clear();
      searchlist = null;
    }

    return detailList;
  }

  public boolean collectDocuments(String txnType, String dealId, String stage)
  {
    boolean status = false;

    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    String s1 = "";
    String s2 = "";
    try {
      logger.info("In collectDocuments proc( Insert_Document_Checklist )");
      logger.info(new StringBuilder().append("collectDocuments txnType: ").append(txnType).append(" dealId: ").append(dealId).append(" stage: ").append(stage).toString());

      in.add(txnType);
      in.add(dealId);
      in.add(stage);
      in.add("NE");

      out.add(s1);
      out.add(s2);

      outMessages = (ArrayList)ConnectionDAO.callSP("Insert_Document_Checklist", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      if ((s1 != null) && (s1.equalsIgnoreCase("S")))
      {
        status = true;
      }

      logger.info(new StringBuilder().append("s1: ").append(s1).toString());
      logger.info(new StringBuilder().append("s2: ").append(s2).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      s1 = null;
      s2 = null;
      in.clear();
      in = null;
      out.clear();
      out = null;
      outMessages.clear();
      outMessages = null;
    }

    return status;
  }

  public ArrayList<DisbursalSearchVO> searchCMGrid(DisbursalSearchVO vo)
  {
    String loanNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanNo()).trim());
    String lbxLoanNoHID = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim());
    String dealNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDealNo()).trim());
    String lbxDealNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim());
    String customerName = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim());
    String panNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPanNo()).trim());
    String appFormNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAppFormNo()).trim());
    String mbNumber = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMbNumber()).trim());
    String drivingLic = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDrivingLic()).trim());
    String voterId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVoterId()).trim());
    String vehicleNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVehicleNo()).trim());
    ArrayList detailListGrid = new ArrayList();
    StringBuilder bufInsSql = new StringBuilder();
    DisbursalSearchVO disVO = null;
    ArrayList subList = null;
    try
    {
      ArrayList searchlist = new ArrayList();
      boolean appendSQL = false;
      logger.info(new StringBuilder().append("In searchCMGrid......loanNo=  ").append(loanNo).append(" And Loan Id=").append(lbxLoanNoHID).toString());

      bufInsSql.append(" Select DISTINCT CLD.LOAN_DEAL_ID,CDD.DEAL_NO, CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME, CPM.PRODUCT_DESC,CSM.SCHEME_DESC, ");
      bufInsSql.append(" if(CLD.REC_STATUS='P','Pending' ,if(CLD.REC_STATUS='A','Active', if(CLD.REC_STATUS='L','Cancelled', if(CLD.REC_STATUS='X','Rejected',     if(CLD.REC_STATUS='C','Closed', 'NA'))))) AS REC_STATUS , ");
      bufInsSql.append(" if(CLD.DISBURSAL_STATUS='N','Not Disbursed', if(CLD.DISBURSAL_STATUS='P','Partially Disbursed',if(CLD.DISBURSAL_STATUS='F','Fully Disbursed','NA'   ))) AS DISBURSAL_STATUS , ");
      bufInsSql.append(" CLD.NPA_FLAG  ,CTD.TERMINATION_ID,CLD.LOAN_REFERENCE_NO,GCM.CUSTMER_PAN,CBM.BRANCH_DESC,ifnull(maker.USER_NAME,'') maker,ifnull(author.USER_NAME,'') author,  ");
      bufInsSql.append(new StringBuilder().append(" GCM.FATHER_HUSBAND_NAME,DATE_FORMAT(GCM.CUSTOMER_DOB,'").append(this.dateFormat).append("'),CONCAT(IFNULL(CAM.ADDRESS_LINE1,''),'  ',IFNULL(CAM.ADDRESS_LINE2,''),'  ',IFNULL(CAM.ADDRESS_LINE3,'')) as address ").toString());
      bufInsSql.append(" ,concat(if(ifnull(LEGAL_FLAG,'NA')='A','LEGAL|',''),if(ifnull(REPO_FLAG,'NA')='A','REPO|',''),IFNULL((select GROUP_CONCAT(DESCRIPTION SEPARATOR '|')from generic_master where generic_key='CASE_MARKING_FLAG' AND INSTR(CONCAT( '|',CLD.CASE_MARKING_FLAG,'|'),CONCAT('|',VALUE,'|') ) > 0 ),''))CASE_MARKING_FLAG,IF(CLD.RESTRICTED_FLAG='Y','YES','NO'),IFNULL(funding_type,'') ");
      bufInsSql.append(" from cr_loan_dtl CLD ");
      bufInsSql.append(" INNER join cr_deal_dtl as CDD on(CLD.LOAN_DEAL_ID=CDD.DEAL_ID) ");
      bufInsSql.append(" INNER join gcd_customer_m as GCM on  (GCM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID) ");
      bufInsSql.append(" INNER join   com_address_m as CAM on (CAM.BPID=GCM.CUSTOMER_ID AND CAM.COMMUNICATION_ADDRESS='Y')");
      bufInsSql.append(" INNER  join cr_product_m as CPM on(CLD.LOAN_PRODUCT=CPM.PRODUCT_ID) ");
      bufInsSql.append(" INNER  join cr_scheme_m as CSM on (CLD.LOAN_SCHEME=CSM.SCHEME_ID) ");
      if (!vehicleNo.equalsIgnoreCase("")) {
        bufInsSql.append("INNER join cr_loan_collateral_m as CLCM on(CLCM.LOAN_ID=CLD.LOAN_ID) ");
        bufInsSql.append("INNER join  cr_asset_collateral_m  as CACM on (CACM.ASSET_ID=CLCM.ASSETID and CACM.ASSET_TYPE='ASSET' and CACM.ASSET_COLLATERAL_CLASS='VEHICLE')");
      }
      bufInsSql.append(" left outer join com_branch_m as CBM on (CLD.LOAN_BRANCH=CBM.BRANCH_ID) ");
      bufInsSql.append(" left outer join sec_user_m as maker on (CLD.MAKER_ID=maker.USER_ID) ");
      bufInsSql.append(" left outer join sec_user_m as author on (CLD.AUTHOR_ID=author.USER_ID) ");

      bufInsSql.append(" left outer join cr_termination_dtl as CTD on(CTD.LOAN_ID=CLD.LOAN_ID) and CTD.rec_status='A' ");

      bufInsSql.append(" where true ");

      if (!dealNo.equalsIgnoreCase(""))
        bufInsSql.append(new StringBuilder().append(" and CLD.LOAN_DEAL_ID='").append(lbxDealNo).append("'").toString());
      if (!loanNo.equalsIgnoreCase(""))
        bufInsSql.append(new StringBuilder().append(" and CLD.LOAN_ID='").append(lbxLoanNoHID).append("'").toString());
      if ((!customerName.equalsIgnoreCase("")) && (dealNo.equalsIgnoreCase("")) && (loanNo.equalsIgnoreCase("")))
        bufInsSql.append(new StringBuilder().append(" and GCM.CUSTOMER_NAME LIKE '%").append(customerName).append("%'").toString());
      if (appFormNo.length() != 0)
        bufInsSql.append(new StringBuilder().append(" and CLD.LOAN_REFERENCE_NO='").append(appFormNo).append("'").toString());
      if (panNo.length() != 0) {
        bufInsSql.append(new StringBuilder().append(" and GCM.CUSTMER_PAN='").append(panNo).append("'").toString());
      }
      if (!mbNumber.equalsIgnoreCase(""))
        bufInsSql.append(new StringBuilder().append(" and  CAM.PRIMARY_PHONE LIKE '%").append(mbNumber).append("%'").toString());
      if (!drivingLic.equalsIgnoreCase(""))
        bufInsSql.append(new StringBuilder().append(" and  GCM.DRIVING_LICENSE LIKE '%").append(drivingLic).append("%'").toString());
      if (!voterId.equalsIgnoreCase(""))
        bufInsSql.append(new StringBuilder().append(" and GCM.VOTER_ID LIKE '%").append(voterId).append("%'").toString());
      if (!vehicleNo.equalsIgnoreCase("")) {
        bufInsSql.append(new StringBuilder().append(" and CACM.VEHICLE_REGISTRATION_NO LIKE '%").append(vehicleNo).append("%'").toString());
      }
      logger.info(new StringBuilder().append("query :::::::::::::::::::::: ").append(bufInsSql).toString());

      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      int size = searchlist.size();
      for (int i = 0; i < size; i++)
      {
        subList = (ArrayList)searchlist.get(i);
        if (subList.size() > 0)
        {
          disVO = new DisbursalSearchVO();
          disVO.setLbxDealNo(CommonFunction.checkNull(subList.get(0)).trim());
          disVO.setDealNo(CommonFunction.checkNull(subList.get(1)).trim());
          disVO.setLbxLoanNoHID(CommonFunction.checkNull(subList.get(2)).trim());
          disVO.setLoanNo(CommonFunction.checkNull(subList.get(3)).trim());
          disVO.setCustomerName(CommonFunction.checkNull(subList.get(4)).trim());
          disVO.setProduct(CommonFunction.checkNull(subList.get(5)).trim());
          disVO.setScheme(CommonFunction.checkNull(subList.get(6)).trim());
          disVO.setLoanStatus(CommonFunction.checkNull(subList.get(7)).trim());
          disVO.setDisbursalStatus(CommonFunction.checkNull(subList.get(8)).trim());
          disVO.setNpaStatus(CommonFunction.checkNull(subList.get(9)).trim());
          disVO.setTerminationId(CommonFunction.checkNull(subList.get(10)).trim());
          disVO.setAppFormNo(CommonFunction.checkNull(subList.get(11)).trim());
          disVO.setPanNo(CommonFunction.checkNull(subList.get(12)).trim());
          disVO.setLoanBranch(CommonFunction.checkNull(subList.get(13)).trim());
          disVO.setMaker(CommonFunction.checkNull(subList.get(14)).trim());
          disVO.setAuthor(CommonFunction.checkNull(subList.get(15)).trim());
          disVO.setFatherHusband(CommonFunction.checkNull(subList.get(16)).trim());
          disVO.setDob(CommonFunction.checkNull(subList.get(17)).trim());
          disVO.setAddr(CommonFunction.checkNull(subList.get(18)).trim());
          disVO.setLegalRepoFlag(CommonFunction.checkNull(subList.get(19)).trim());
          disVO.setRestrictedFlag(CommonFunction.checkNull(subList.get(20)).trim());
          disVO.setFundingType(CommonFunction.checkNull(subList.get(21)).trim());
          detailListGrid.add(disVO);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      bufInsSql = null;
      disVO = null;
      subList = null;
    }
    return detailListGrid;
  }
  public String loandisbursalid() {
    logger.info("Inside getDisbursalSchedule.........DAOImpl");

    String loandisbursalid = "";
    String query = "SELECT LOAN_DISBURSAL_ID FROM cr_loan_disbursal_dtl   ORDER BY LOAN_DISBURSAL_ID DESC LIMIT 1";
    try
    {
      logger.info(new StringBuilder().append("In getDisbursalSchedule : ").append(query).toString());
      loandisbursalid = ConnectionDAO.singleReturn(query);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    logger.info(new StringBuilder().append("In loandisbursalid : ").append(loandisbursalid).toString());
    return loandisbursalid;
  }
  public boolean deletedisbursal(String disbursalloanid) {
    logger.info("Inside deletedisbursal().........DAOImpl");
    ArrayList list = new ArrayList();
    boolean status = false;
    StringBuilder bufInsSql = null;
    StringBuilder bufInsSqlTemp = null;
    try
    {
      bufInsSql = new StringBuilder();
      bufInsSqlTemp = new StringBuilder();
      bufInsSql.append(new StringBuilder().append("delete  from cr_loan_disbursal_dtl  where LOAN_DISBURSAL_ID = '").append(CommonFunction.checkNull(disbursalloanid).trim()).append("'").toString());
      bufInsSqlTemp.append(new StringBuilder().append("delete  from cr_loan_special_condition_dtl  where LOAN_DISBURSAL_ID = '").append(CommonFunction.checkNull(disbursalloanid).trim()).append("'").toString());
      list.add(bufInsSqlTemp);
      list.add(bufInsSql);
      logger.info(new StringBuilder().append("In deletedisbursal() :bufInsSql ").append(bufInsSql.toString()).append(" bufInsSqlTemp: ").append(bufInsSqlTemp.toString()).toString());
      status = ConnectionDAO.sqlInsUpdDelete(list);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      list.clear();
      list = null;
      bufInsSql = null;
      bufInsSqlTemp = null;
    }
    return status;
  }

  public String getProposedDisbursalCheck() {
    logger.info("In getEditableFlag()");
    String ediFlag = "";
    try
    {
      StringBuilder query = new StringBuilder();
      query.append("select parameter_value from parameter_mst where parameter_key='PROPOSED_DISBURSAL_CHECK'");
      logger.info(new StringBuilder().append("In getEditableFlag() query  :  ").append(query.toString()).toString());
      ediFlag = ConnectionDAO.singleReturn(query.toString());
      logger.info(new StringBuilder().append("In getEditableFlag() ediFlag  :  ").append(ediFlag).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return ediFlag;
  }

  public ArrayList<DisbursalMakerVO> getShortpayOnDisbursalTo(int lbxLoanNoHID, String userId, String businessDate, String disbursalTo)
  {
    logger.info("In getShortpayOnDisbursalTo..................DAOImpl");
    ArrayList disbursalList = new ArrayList();

    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    String query1 = "";
    String query2 = "";
    String shortpayValue = "";
    String shortpay = "";
    String shortAmountPaidStr = "";
    DisbursalMakerVO vo = new DisbursalMakerVO();
    try
    {
      query1 = new StringBuilder().append("select SUM(IFNULL(ADVICE_AMOUNT,0) - IFNULL(TXN_ADJUSTED_AMOUNT,0) - IFNULL(AMOUNT_IN_PROCESS,0)) FROM CR_TXNADVICE_DTL WHERE LOAN_ID = '").append(lbxLoanNoHID).append("' AND BP_TYPE = '").append(disbursalTo).append("' AND ADVICE_TYPE = 'R' AND REC_STATUS = 'A' AND ADVICE_DATE<=STR_TO_DATE('").append(businessDate).append("', '").append(this.dateFormat).append("')").toString();

      logger.info(new StringBuilder().append("In getShortpayOnDisbursalTo : ").append(query1).toString());
      shortpayValue = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1));
      logger.info(new StringBuilder().append("In getShortpayOnDisbursalTo.proposedShortPayAmtStr....shortpayValue: ").append(shortpayValue).toString());
      logger.info(new StringBuilder().append("shortpayValue proposedShortPayAmtStr:::::::::::::::::::::::::: ").append(shortpayValue).toString());
      if (shortpayValue.equalsIgnoreCase(""))
      {
        shortpay = "0.00";
      }
      else
      {
        Number shortval = this.myFormatter.parse(CommonFunction.checkNull(shortpayValue).trim());
        shortpay = this.myFormatter.format(shortval);
      }

      vo.setProposedShortPayAmount(shortpay);
      logger.info(new StringBuilder().append("shortpay (TOTAL RECEIVABLE):::::::::::::::::::::::::: ").append(shortpay).toString());

      query2 = new StringBuilder().append("select SUM(IFNULL(TXN_ADJUSTED_AMOUNT,0)) FROM cr_txnadvice_dtl WHERE LOAN_ID = '").append(lbxLoanNoHID).append("' AND REC_STATUS='A' AND BP_TYPE = '").append(disbursalTo).append("'").toString();

      shortAmountPaidStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2));
      logger.info(new StringBuilder().append("In getDisbursalValues.....shortAmountPaid: ").append(shortAmountPaidStr).toString());
      if (shortAmountPaidStr.equalsIgnoreCase(""))
      {
        vo.setAdjustedShortPayAmount("0.00");
      }
      else
      {
        Number adjustshortAmt = this.myFormatter.parse(CommonFunction.checkNull(shortAmountPaidStr).trim());
        vo.setAdjustedShortPayAmount(this.myFormatter.format(adjustshortAmt).toString());
      }

      mainlist.add(vo);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      mainlist = null;
      subList = null;
      query1 = null;
      query2 = null;
      shortpayValue = null;
      shortpay = null;
      shortAmountPaidStr = null;
      disbursalTo = null;
      vo = null;
    }
    return mainlist;
  }

  public String getDisbAuthorParameter(DisbursalAuthorVO vo)
  {
    String query = "select parameter_value from parameter_mst where parameter_key='DISBURSAL_AUTHOR_NEXT_BUSINESS_DATE'";
    String parameterValue = CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
    String query1 = new StringBuilder().append("select count(1) from cr_loan_disbursal_dtl where MAKER_DATE>=str_to_date('").append(vo.getAuthorDate()).append("','%d-%m-%Y') and loan_disbursal_id='").append(vo.getLoanDisbursalId()).append("'").toString();
    String res = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1));
    String retStr = "";
    if ((parameterValue.equalsIgnoreCase("Y")) && (res.equalsIgnoreCase("0")))
      retStr = "DisbursalAllowedWithAlert";
    else if ((parameterValue.equalsIgnoreCase("Y")) && (res.equalsIgnoreCase("1")))
      retStr = "";
    else if ((parameterValue.equalsIgnoreCase("N")) && (res.equalsIgnoreCase("0")))
      retStr = "DisbursalNotAllowed";
    else if ((parameterValue.equalsIgnoreCase("N")) && (res.equalsIgnoreCase("1")))
      retStr = "";
    return retStr;
  }

  public String checkSpecialConditionAvail(DisbursalMakerVO vo, String disbId, String recStatus)
  {
    String status = "S";

    String specialRequiredQuery = "SELECT PARAMETER_VALUE  FROM parameter_mst where PARAMETER_KEY='SPECIAL_CONDITION_REQUIRED'";
    String specialRequired = ConnectionDAO.singleReturn(specialRequiredQuery);
    logger.info(new StringBuilder().append("specialRequiredQuery: ").append(specialRequiredQuery).append(" specialRequired: ").append(specialRequired).toString());

    if ((recStatus.equalsIgnoreCase("F")) && (CommonFunction.checkNull(specialRequired).equalsIgnoreCase("Y")))
    {
      String specialConditionAtDealQuery = new StringBuilder().append("SELECT COUNT(1) FROM cr_deal_special_condition_m WHERE DEAL_ID=(SELECT LOAN_DEAL_ID FROM CR_LOAN_DTL WHERE LOAN_ID='").append(vo.getLbxLoanNoHID()).append("' )").toString();
      logger.info(new StringBuilder().append("specialConditionAtDealQuery: ").append(specialConditionAtDealQuery).toString());
      String specialConditionAtDealCount = ConnectionDAO.singleReturn(specialConditionAtDealQuery);
      logger.info(new StringBuilder().append("specialConditionAtDealCount: ").append(specialConditionAtDealCount).toString());
      if (!CommonFunction.checkNull(specialConditionAtDealCount).equalsIgnoreCase("0"))
      {
        String specialConditionAtDisbursalQuery = new StringBuilder().append("SELECT COUNT(1) FROM cr_loan_special_condition_dtl WHERE LOAN_ID='").append(vo.getLbxLoanNoHID()).append("'").toString();
        logger.info(new StringBuilder().append("specialConditionAtDisbursalQuery: ").append(specialConditionAtDisbursalQuery).toString());
        String specialConditionAtDisbursalCount = ConnectionDAO.singleReturn(specialConditionAtDisbursalQuery);
        logger.info(new StringBuilder().append("specialConditionAtDisbursalCount: ").append(specialConditionAtDisbursalCount).toString());
        if (CommonFunction.checkNull(specialConditionAtDisbursalCount).equalsIgnoreCase("0"))
        {
          status = "E";
        }
        else {
          status = "S";
        }
      }

      logger.info(new StringBuilder().append("checkSpecialConditionAvail status: ").append(status).toString());
    }
    return status;
  }

  public String saveDisbursalDataWithPayment(DisbursalMakerVO vo)
  {
    boolean status = false;
    logger.info("In saveDisbursalDataWithPayment.....................................Dao Impl...");
    String loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
    String oldLoanDueDay = "";
    String oldLoanRepayEff = "";
    String oldNextDueDate = "";
    ArrayList qryList = new ArrayList();
    ArrayList loanData = new ArrayList();
    ArrayList subList = new ArrayList();
    ArrayList qryListUpdate = new ArrayList();
    StringBuilder bufInsSql = new StringBuilder();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject2 = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject3 = new PrepStmtObject();
    StringBuilder query2 = new StringBuilder();
    StringBuilder query3 = new StringBuilder();
    String bpId = "";
    int maxId = 0;
    String disbursalFlag = "";
    String loanCurtail = "";
    try
    {
      if ((vo.getFinalDisbursal() != null) && (vo.getFinalDisbursal().equalsIgnoreCase("on")))
        disbursalFlag = "F";
      else {
        disbursalFlag = "P";
      }
      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS"))
      {
        query3.append(new StringBuilder().append("SELECT distinct v.BP_ID from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='").append(loanId).append("' and v.BP_TYPE='").append(CommonFunction.checkNull(vo.getDisbursalTo()).trim()).append("'").toString());

        logger.info(new StringBuilder().append("In saveDisbursalDataWithPayment bpid..... : ").append(query3).toString());
        bpId = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));

        vo.setLbxBusinessPartnearHID(bpId);
      }

      logger.info(new StringBuilder().append("In saveDisbursalDataWithPayment insert disbursal Data part").append(vo.getNextDueDate()).toString());
      if ((vo.getNextDueDate() != null) && (vo.getNextDueDate().equals("undefined"))) {
        vo.setNextDueDate("");
      }
      loanData = ConnectionDAO.sqlSelect(new StringBuilder().append("SELECT LOAN_DUE_DAY,LOAN_REPAY_EFF_DATE,DATE_FORMAT(NEXT_DUE_DATE,'").append(this.dateFormat).append("') FROM CR_LOAN_DTL WHERE LOAN_ID=").append(vo.getLbxLoanNoHID().trim()).toString());
      logger.info(new StringBuilder().append("In saveDisbursalDataWithPayment loanData...").append(loanData).toString());

      int size = loanData.size();
      logger.info(new StringBuilder().append("In saveDisbursalDataWithPayment selectLoandetail.....mainlist size: ").append(size).toString());

      if ((vo.getLoanCurtail() != null) && (vo.getLoanCurtail().equalsIgnoreCase("on")))
        loanCurtail = "Y";
      else {
        loanCurtail = "N";
      }

      if (size != 0)
      {
        for (int i = 0; i < size; i++)
        {
          subList = (ArrayList)loanData.get(i);
          logger.info(new StringBuilder().append("In selectDisbursalData......sublist size: ").append(subList.size()).toString());
          if (subList.size() > 0)
          {
            oldLoanRepayEff = CommonFunction.checkNull(subList.get(0)).trim();
            oldLoanRepayEff = CommonFunction.checkNull(subList.get(1)).trim();
            oldNextDueDate = CommonFunction.checkNull(subList.get(2)).trim();
          }
        }
      }
      logger.info(new StringBuilder().append(" vo.getRepayEffDate():---------------").append(vo.getRepayEffDate()).toString());
      logger.info(new StringBuilder().append(" vo.getBusinessPartnerTypeDesc():---------------").append(vo.getBusinessPartnerTypeDesc()).toString());

      bufInsSql = new StringBuilder();
      insertPrepStmtObject = new PrepStmtObject();
      bufInsSql.append("insert into cr_loan_disbursal_dtl_temp (loan_id, disbursal_no, disbursal_description,");
      bufInsSql.append("disbursal_date,disbursal_amount,loan_due_day,NEXT_DUE_DATE,disbursal_flag,");
      bufInsSql.append("rec_status,maker_id,maker_date,CUR_MONTH_EMI,NEXT_MONTH_EMI,REPAY_EFF_DATE,");
      bufInsSql.append("old_repay_eff_Date,old_LOAN_DUE_DAY,old_NEXT_DUE_DATE,PENAL_INT_CALC_DATE,");
      bufInsSql.append("DISBURSAL_TO,DISBURSAL_TO_ID,DISBURSAL_TO_NAME,NET_AMOUNT,ADJUST_TOTAL_PAYABLE,");
      bufInsSql.append("PAYMENT_FLAG,TA_LOAN_ID,TA_PARTY_NAME,PMNT_MODE,PMNT_DATE,");
      bufInsSql.append("INSTRUMENT_NO,INSTRUMENT_DATE,BANK_ACCOUNT,BANK_ID,BRANCH_ID,MICR_CODE,IFSC_CODE,");
      bufInsSql.append("PMNT_AMOUNT,TDS_AMOUNT,PMNT_REMARK,SHORT_AMOUNT_ADJUSTED,PAY_TO,PAYEE_NAME,LOAN_CURTAIL,DEFAULT_BRANCH,");
      bufInsSql.append("BENEFICIARY_BANK_ID,BENEFICIARY_BANK_BRANCH_ID,BENEFICIARY_BANK_ACCOUNT,BENEFICIARY_BRANCH_IFCS_CODE,INTEREST_DUE_DATE,LOAN_MATURITY_DATE )");
      bufInsSql.append(" values ( ");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(" ?,");
      bufInsSql.append(" 'P',");
      bufInsSql.append(" ?,");
      bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
      bufInsSql.append(" ?,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'), ").toString());
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("') ,").toString());
      bufInsSql.append(" ? ,");
      bufInsSql.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("') ,").toString());
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(" ? ,");
      bufInsSql.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'))").toString());
      if (CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxLoanNoHID().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalNo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalNo().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalDescription().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalDate().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getCycleDate()).trim());
      }
      if (CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getNextDueDate().trim());
      }

      if (CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(disbursalFlag.trim());
      }
      if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerId().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerDate().trim());
      }
      if (CommonFunction.checkNull(vo.getCurrentMonthEMI()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getCurrentMonthEMI()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getPreEMINextMonth()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getPreEMINextMonth()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getRepayEffDate().trim());
      }
      if (CommonFunction.checkNull(oldLoanRepayEff).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(oldLoanRepayEff.trim());
      }
      if (CommonFunction.checkNull(oldLoanDueDay).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(oldLoanDueDay.trim());
      }
      if (CommonFunction.checkNull(oldNextDueDate).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(oldNextDueDate.trim());
      }
      if (CommonFunction.checkNull(vo.getRepayMode()).equalsIgnoreCase("N")) {
        if (CommonFunction.checkNull(vo.getPenalIntCalcDate()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getPenalIntCalcDate().trim());
      }
      else insertPrepStmtObject.addNull();

      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalTo().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBusinessPartnearHID().trim());
      }
      if (CommonFunction.checkNull(vo.getBusinessPartnerTypeDesc()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBusinessPartnerTypeDesc().trim());
      }
      if (CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getNetAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getAdjustTotalPayable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getAdjustTotalPayable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getPaymentFlag()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPaymentFlag().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxTaLoanNoHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxTaLoanNoHID().trim());
      }
      if (CommonFunction.checkNull(vo.getTaCustomerName()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getTaCustomerName().trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPaymentMode().trim());
      }

      if (CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPaymentDate().trim());
      }
      if (CommonFunction.checkNull(vo.getInstrumentNumber()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getInstrumentNumber().trim());
      }
      if (CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getInstrumentDate().trim());
      }
      if (CommonFunction.checkNull(vo.getBankAccount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBankAccount().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBankID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBankID().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBranchID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBranchID().trim());
      }
      if (CommonFunction.checkNull(vo.getMicr()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMicr().trim());
      }
      if (CommonFunction.checkNull(vo.getIfsCode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getIfsCode().trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getPaymentAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getTdsAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getTdsAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getRemarks().trim());
      }
      if (CommonFunction.checkNull(vo.getAdjustTotalReceivable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getAdjustTotalReceivable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getLbxpayTo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxpayTo().trim());
      }
      if (CommonFunction.checkNull(vo.getPayeeName()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPayeeName().trim());
      }

      if (CommonFunction.checkNull(loanCurtail).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(loanCurtail.trim());
      }

      if (CommonFunction.checkNull(vo.getPaymentFlag()).equalsIgnoreCase("P"))
      {
        if (CommonFunction.checkNull(vo.getDefaultBranch()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDefaultBranch().trim());
        }
      }
      else {
        insertPrepStmtObject.addNull();
      }

      if (CommonFunction.checkNull(vo.getBeneficiaryLbxBankID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBeneficiaryLbxBankID().trim());
      }
      if (CommonFunction.checkNull(vo.getBeneficiaryLbxBranchID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBeneficiaryLbxBranchID().trim());
      }
      if (CommonFunction.checkNull(vo.getBeneficiaryAccountNo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBeneficiaryAccountNo().trim());
      }
      if (CommonFunction.checkNull(vo.getBeneficiaryIfscCode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBeneficiaryIfscCode().trim());
      }

      if (CommonFunction.checkNull(vo.getInterestDueDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getInterestDueDate().trim());
      }

      if (CommonFunction.checkNull(vo.getMaturityDate1()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMaturityDate1().trim());
      }

      logger.info(new StringBuilder().append("PaymentFlag:::::::::: ").append(vo.getDefaultBranch()).toString());
      logger.info(new StringBuilder().append("DefaultBranch:::::::::: ").append(vo.getDefaultBranch()).toString());

      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN saveDisbursalDataWithPayment() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In saveDisbursalDataWithPayment.........update status: ").append(status).toString());

      String maxIdQuery = "Select distinct max(LOAN_DISBURSAL_ID) from cr_loan_disbursal_dtl_temp";
      String id = ConnectionDAO.singleReturn(maxIdQuery);
      maxId = Integer.parseInt(id.toString());
      logger.info(new StringBuilder().append("maxId : ").append(maxId).toString());

      StringBuilder queryUpdate2 = new StringBuilder();
      updatePrepStmtObject2 = new PrepStmtObject();
      queryUpdate2.append(new StringBuilder().append("update cr_loan_dtl set loan_maturity_date=(select loan_maturity_date from cr_loan_disbursal_dtl_temp where loan_id='").append(vo.getLbxLoanNoHID()).append("')").toString());
      updatePrepStmtObject2.setSql(queryUpdate2.toString());
      qryListUpdate.add(updatePrepStmtObject2);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      logger.info(" Update maturity date :: 3");

      StringBuilder queryUpdate3 = new StringBuilder();
      updatePrepStmtObject3 = new PrepStmtObject();
      queryUpdate3.append(new StringBuilder().append("delete from cr_resch_installment_plan where loan_id='").append(vo.getLbxLoanNoHID()).append("'").toString());
      updatePrepStmtObject3.setSql(queryUpdate3.toString());
      qryListUpdate.add(updatePrepStmtObject3);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      logger.info(" Delete Installment Plan :: 3");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      loanId = null;
      qryList = null;
      bufInsSql = null;
      insertPrepStmtObject = null;
      updatePrepStmtObject = null;
      query2 = null;
      qryListUpdate = null;
    }
    return new StringBuilder().append(maxId).append("").toString();
  }
  public ArrayList getDisbursalDataWithPayment(DisbursalMakerVO vo) {
    logger.info("In selectDisbursalData.....DAOImpl");
    ArrayList disbursalData = new ArrayList();
    DisbursalMakerVO disVo = null;
    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    StringBuilder query1 = new StringBuilder();
    String proposedShortPayAmtStr = "";
    StringBuilder query2 = new StringBuilder();
    String disbursedAmountStr = "";
    StringBuilder query3 = new StringBuilder();
    String shortAmountPaidStr = "";
    StringBuilder query = new StringBuilder();
    StringBuilder query5 = new StringBuilder();
    StringBuilder query7 = new StringBuilder();
    String maxDisbursalDate = "";
    String query6 = "";
    String query8 = "";
    try
    {
      query2.append(new StringBuilder().append("select SUM(IFNULL(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(vo.getLbxLoanNoHID()).append("' AND REC_STATUS='A'").toString());

      disbursedAmountStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
      logger.info(new StringBuilder().append("In selectDisbursalData.....disbursedAmount: ").append(disbursedAmountStr).toString());

      query3.append(new StringBuilder().append("select SUM(IFNULL(SHORT_AMOUNT_ADJUSTED,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(vo.getLbxLoanNoHID()).append("' AND REC_STATUS='A'").toString());

      shortAmountPaidStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
      logger.info(new StringBuilder().append("In selectDisbursalData.....shortAmountPaid: ").append(shortAmountPaidStr).toString());

      String maxExpectedPayDate = "";
      query6 = new StringBuilder().append("select DATE_FORMAT(max(PAYMENT_DATE),'").append(this.dateFormat).append("') from cr_loan_disbursal_dtl where loan_id='").append(vo.getLbxLoanNoHID()).append("' and REC_STATUS='A'").toString();
      logger.info(new StringBuilder().append("In selectDisbursalData : ").append(query6).toString());

      maxExpectedPayDate = ConnectionDAO.singleReturn(query6);
      logger.info(new StringBuilder().append("In selectDisbursalData maxExpectedPayDate : ").append(maxExpectedPayDate).toString());

      query8 = new StringBuilder().append("select DATE_FORMAT(max(DISBURSAL_DATE) ,'").append(this.dateFormat).append("')from cr_loan_disbursal_dtl where LOAN_ID='").append(vo.getLbxLoanNoHID()).append("' and REC_STATUS='A'").toString();
      logger.info(new StringBuilder().append("query8:----------").append(query8).toString());
      maxDisbursalDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(query8));
      logger.info(new StringBuilder().append("In selectDisbursalData maxDisbursalDate : ").append(maxDisbursalDate).toString());

      query.append(new StringBuilder().append("select a.loan_no, b.customer_name, a.loan_loan_amount, DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("'), c.product_desc, d.scheme_desc,").append(" a.loan_id, e.disbursal_description, DATE_FORMAT(e.disbursal_date,'").append(this.dateFormat).append("'),").append(" e.short_amount_adjusted, e.disbursal_amount, e.disbursal_no, e.disbursal_flag,DATE_FORMAT(e.REPAY_EFF_DATE,'").append(this.dateFormat).append("'),e.remarks,").append(" e.loan_due_day,a.loan_repayment_type,").append(" (select count(PDC_LOAN_ID) from cr_pdc_instrument_dtl where  PDC_STATUS not in('X','R') and PDC_PURPOSE='INSTALLMENT' and PDC_LOAN_ID='").append(vo.getLbxLoanNoHID()).append("'),").append(" DATE_FORMAT(e.NEXT_DUE_DATE ,'%d-%m-%Y'),e.CUR_MONTH_EMI,e.NEXT_MONTH_EMI,e.LOAN_DISBURSAL_ID,").append(" e.DISBURSAL_TO_ID,e.DISBURSAL_TO, DATE_FORMAT(e.PAYMENT_DATE,'").append(this.dateFormat).append("'),e.TA_LOAN_ID, DATE_FORMAT(e.PENAL_INT_CALC_DATE ,'").append(this.dateFormat).append("'),DATE_FORMAT(a.LOAN_REPAY_EFF_DATE ,'").append(this.dateFormat).append("'), a.edit_due_date,e.LOAN_MATURITY_DATE").append("  from cr_loan_dtl a,gcd_customer_m b,").append(" cr_product_m c, cr_scheme_m d, cr_loan_disbursal_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product").append(" and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.loan_id='").append(vo.getLbxLoanNoHID()).append("' and e.disbursal_no='").append(vo.getDisbursalNo()).append("'").toString());

      logger.info(new StringBuilder().append("In selectDisbursalData ::::::::::::: ").append(query).toString());
      mainlist = ConnectionDAO.sqlSelect(query.toString());
      int size = mainlist.size();
      logger.info(new StringBuilder().append("In selectDisbursalData.....mainlist size: ").append(size).toString());
      if (size != 0)
      {
        for (int i = 0; i < size; i++)
        {
          subList = (ArrayList)mainlist.get(i);
          logger.info(new StringBuilder().append("In selectDisbursalData......sublist size: ").append(subList.size()).toString());
          if (subList.size() > 0) {
            disVo = new DisbursalMakerVO();
            disVo.setLoanNo(CommonFunction.checkNull(subList.get(0)).trim());
            disVo.setCustomerName(CommonFunction.checkNull(subList.get(1)).trim());

            if (CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
            {
              disVo.setLoanAmt("0.00");
            }
            else
            {
              Number loanAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
              disVo.setLoanAmt(this.myFormatter.format(loanAmount));
            }

            disVo.setLoanApprovalDate(CommonFunction.checkNull(subList.get(3)).trim());
            disVo.setProduct(CommonFunction.checkNull(subList.get(4)).trim());
            disVo.setScheme(CommonFunction.checkNull(subList.get(5)).trim());
            disVo.setLbxLoanNoHID(CommonFunction.checkNull(subList.get(6)).trim());
            disVo.setDisbursalDescription(CommonFunction.checkNull(subList.get(7)).trim());
            disVo.setDisbursalDate(CommonFunction.checkNull(subList.get(8)).trim());

            if (CommonFunction.checkNull(subList.get(9)).trim().equalsIgnoreCase(""))
            {
              disVo.setShortPayAmount("0");
            }
            else
            {
              Number shortPayAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(9)).trim());
              disVo.setShortPayAmount(this.myFormatter.format(shortPayAmount));
            }

            if (CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase(""))
            {
              disVo.setDisbursalAmount("0");
            }
            else
            {
              Number disbursalAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(10)).trim());
              disVo.setDisbursalAmount(this.myFormatter.format(disbursalAmount));
            }

            disVo.setDisbursalNo(CommonFunction.checkNull(subList.get(11)).trim());
            disVo.setFinalDisbursal(CommonFunction.checkNull(subList.get(12)).trim());

            if (CommonFunction.checkNull(proposedShortPayAmtStr).trim().equalsIgnoreCase(""))
            {
              disVo.setProposedShortPayAmount("0");
            }
            else
            {
              Number proposedShortPayAmt = this.myFormatter.parse(CommonFunction.checkNull(proposedShortPayAmtStr).trim());
              disVo.setProposedShortPayAmount(this.myFormatter.format(proposedShortPayAmt));
            }

            if (CommonFunction.checkNull(disbursedAmountStr).trim().equalsIgnoreCase(""))
            {
              disVo.setDisbursedAmount("0");
            }
            else
            {
              Number disbursedAmount = this.myFormatter.parse(CommonFunction.checkNull(disbursedAmountStr).trim());
              disVo.setDisbursedAmount(this.myFormatter.format(disbursedAmount));
            }

            if (CommonFunction.checkNull(shortAmountPaidStr).trim().equalsIgnoreCase(""))
            {
              disVo.setAdjustedShortPayAmount("0");
            }
            else
            {
              Number shortAmountPaid = this.myFormatter.parse(CommonFunction.checkNull(shortAmountPaidStr).trim());
              disVo.setAdjustedShortPayAmount(this.myFormatter.format(shortAmountPaid));
            }

            disVo.setRepayEffDate(CommonFunction.checkNull(subList.get(13)).trim());
            vo.setAuthorRemarks(CommonFunction.checkNull(subList.get(14)).trim());
            disVo.setCycleDateValue(CommonFunction.checkNull(subList.get(15)).trim());
            disVo.setCycleDate(CommonFunction.checkNull(subList.get(15)).trim());
            disVo.setRepayMode(CommonFunction.checkNull(subList.get(16)).trim());
            if (CommonFunction.checkNull(subList.get(17)).trim().equalsIgnoreCase(""))
              disVo.setPdcDepositCount("0");
            else {
              disVo.setPdcDepositCount(CommonFunction.checkNull(subList.get(17)).trim());
            }
            disVo.setNextDueDate(CommonFunction.checkNull(subList.get(18)).trim());

            if (CommonFunction.checkNull(subList.get(19)).trim().equalsIgnoreCase("")) {
              disVo.setCurrentMonthEMI("");
            }
            else {
              Number amount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(19)).trim());
              disVo.setCurrentMonthEMI(this.myFormatter.format(amount));
            }
            if (CommonFunction.checkNull(subList.get(20)).trim().equalsIgnoreCase("")) {
              disVo.setPreEMINextMonth("");
            }
            else {
              Number amount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(20)).trim());
              disVo.setPreEMINextMonth(this.myFormatter.format(amount));
            }
            disVo.setLoanDisbursalId(CommonFunction.checkNull(subList.get(21)).trim());

            disVo.setDisbursalTo(CommonFunction.checkNull(subList.get(23)).trim());

            disVo.setLbxBusinessPartnearHID(CommonFunction.checkNull(subList.get(22)).trim());
            disVo.setExpectedPaymentDate(CommonFunction.checkNull(subList.get(24)).trim());

            disVo.setMaxDisbursalDate(maxDisbursalDate);

            disVo.setMaxExpectedPayDate(CommonFunction.checkNull(maxExpectedPayDate));
            disVo.setPenalIntCalcDate(CommonFunction.checkNull(subList.get(26)).trim());
            disVo.setOldRepayEffDate(CommonFunction.checkNull(subList.get(27)).trim());
            disVo.setEditDueDate(CommonFunction.checkNull(subList.get(28)).trim());

            disbursalData.add(disVo);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      vo = null;
      mainlist = null;
      subList = null;
      query1 = null;
      proposedShortPayAmtStr = null;
      query2 = null;
      disbursedAmountStr = null;
      query3 = null;
      shortAmountPaidStr = null;
      query = null;
    }
    return disbursalData;
  }

  public String updateDisbursalDataWithPayment(DisbursalMakerVO vo, String forStatus) {
    boolean status = false;
    logger.info("In updateDisbursalDataWithPayment.....................................Dao Impl....111");
    String loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
    ArrayList qryList = new ArrayList();
    ArrayList qryListUpdate = new ArrayList();
    StringBuilder query = new StringBuilder();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject2 = new PrepStmtObject();
    PrepStmtObject updatePrepStmtObject3 = new PrepStmtObject();
    StringBuilder query2 = new StringBuilder();
    StringBuilder query3 = new StringBuilder();
    String bpId = "";
    String result = "";
    String disbursalFlag = "";
    String loanCurtail = "";
    String recStatus = forStatus;
    try
    {
      if (!CommonFunction.checkNull(recStatus).equalsIgnoreCase("F")) {
        recStatus = "P";
      }
      logger.info(new StringBuilder().append("recStatus-------------------").append(forStatus).toString());

      if ((vo.getFinalDisbursal() != null) && (vo.getFinalDisbursal().equalsIgnoreCase("on")))
        disbursalFlag = "F";
      else {
        disbursalFlag = "P";
      }
      logger.info(new StringBuilder().append("disbursalFlag-------------------").append(disbursalFlag).toString());
      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS"))
      {
        query3.append(new StringBuilder().append("SELECT distinct v.BP_ID from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='").append(vo.getLbxLoanNoHID()).append("' and v.BP_TYPE='").append(CommonFunction.checkNull(vo.getDisbursalTo()).trim()).append("'").toString());

        logger.info(new StringBuilder().append("In disbursal bpid..... : ").append(query3).toString());
        bpId = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));

        vo.setLbxBusinessPartnearHID(bpId);
      }

      if ((vo.getLoanCurtail() != null) && (vo.getLoanCurtail().equalsIgnoreCase("on")))
        loanCurtail = "Y";
      else {
        loanCurtail = "N";
      }

      logger.info(new StringBuilder().append(" vo.getRepayEffDate():---------------").append(vo.getRepayEffDate()).toString());
      logger.info(new StringBuilder().append(" vo.getBusinessPartnerTypeDesc():---------------").append(vo.getBusinessPartnerTypeDesc()).toString());
      query.append(new StringBuilder().append("update cr_loan_disbursal_dtl_temp set disbursal_description=?,disbursal_date=STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      query.append(new StringBuilder().append(" disbursal_amount=?, loan_due_day=?,NEXT_DUE_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'), disbursal_description=?, rec_status=?, disbursal_flag=?,").toString());
      query.append(new StringBuilder().append(" maker_id=?, maker_date=DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
      query.append(new StringBuilder().append(" CUR_MONTH_EMI=?,NEXT_MONTH_EMI=?,REPAY_EFF_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'),PENAL_INT_CALC_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      query.append("DISBURSAL_TO=?,DISBURSAL_TO_ID=?,DISBURSAL_TO_NAME=?,NET_AMOUNT=?,ADJUST_TOTAL_PAYABLE=?,");
      query.append(new StringBuilder().append("PAYMENT_FLAG=?,TA_LOAN_ID=?,TA_PARTY_NAME=?,PMNT_MODE=?,PMNT_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      query.append(new StringBuilder().append("INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'),BANK_ACCOUNT=?,BANK_ID=?,BRANCH_ID=?,MICR_CODE=?,IFSC_CODE=?,").toString());
      query.append(new StringBuilder().append("PMNT_AMOUNT=?,TDS_AMOUNT=?,PMNT_REMARK=?,SHORT_AMOUNT_ADJUSTED=?,PAY_TO=?,PAYEE_NAME=?,LOAN_CURTAIL=?,INTEREST_DUE_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'),").append("BENEFICIARY_BANK_ID=?,BENEFICIARY_BANK_BRANCH_ID=?,BENEFICIARY_BANK_ACCOUNT=?,BENEFICIARY_BRANCH_IFCS_CODE=?,LOAN_MATURITY_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("') ").toString());

      query.append(" where LOAN_DISBURSAL_ID=?");

      String date = vo.getNextDueDate();
      String amount = vo.getPreEMINextMonth();

      if (CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalDescription().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalDate().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(disbursalFlag).trim().equalsIgnoreCase("P"))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getCycleDate().trim());
      }

      if (CommonFunction.checkNull(disbursalFlag).trim().equalsIgnoreCase("P"))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(date.trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalDescription().trim());
      }
      if (CommonFunction.checkNull(recStatus).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(recStatus.trim());
      }
      if (CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(disbursalFlag.trim());
      }
      if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerId().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerDate().trim());
      }
      if (CommonFunction.checkNull(vo.getCurrentMonthEMI()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addNull();
      logger.info(new StringBuilder().append("vo.getCurrentMonthEMI()......................").append(vo.getCurrentMonthEMI()).toString());

      if (CommonFunction.checkNull(amount).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(amount).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getRepayEffDate().trim());
      }
      if (CommonFunction.checkNull(vo.getRepayMode()).equalsIgnoreCase("N")) {
        if (CommonFunction.checkNull(vo.getPenalIntCalcDate()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getPenalIntCalcDate().trim());
      }
      else insertPrepStmtObject.addNull();

      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalTo().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBusinessPartnearHID().trim());
      }
      if (CommonFunction.checkNull(vo.getBusinessPartnerTypeDesc()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBusinessPartnerTypeDesc().trim());
      }
      if (CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getNetAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getAdjustTotalPayable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getAdjustTotalPayable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getPaymentFlag()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPaymentFlag().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxTaLoanNoHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxTaLoanNoHID().trim());
      }
      if (CommonFunction.checkNull(vo.getTaCustomerName()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getTaCustomerName().trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPaymentMode().trim());
      }

      if (CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPaymentDate().trim());
      }
      if (CommonFunction.checkNull(vo.getInstrumentNumber()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getInstrumentNumber().trim());
      }
      if (CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getInstrumentDate().trim());
      }
      if (CommonFunction.checkNull(vo.getBankAccount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBankAccount().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBankID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBankID().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBranchID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBranchID().trim());
      }
      if (CommonFunction.checkNull(vo.getMicr()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMicr().trim());
      }
      if (CommonFunction.checkNull(vo.getIfsCode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getIfsCode().trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getPaymentAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getTdsAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getTdsAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getRemarks().trim());
      }
      if (CommonFunction.checkNull(vo.getAdjustTotalReceivable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getAdjustTotalReceivable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getLbxpayTo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxpayTo().trim());
      }
      if (CommonFunction.checkNull(vo.getPayeeName()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPayeeName().trim());
      }
      if (CommonFunction.checkNull(loanCurtail).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(loanCurtail.trim());
      if (CommonFunction.checkNull(vo.getInterestDueDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getInterestDueDate().trim());
      }

      if (CommonFunction.checkNull(vo.getBeneficiaryLbxBankID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBeneficiaryLbxBankID().trim());
      }
      if (CommonFunction.checkNull(vo.getBeneficiaryLbxBranchID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBeneficiaryLbxBranchID().trim());
      }
      if (CommonFunction.checkNull(vo.getBeneficiaryAccountNo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBeneficiaryAccountNo().trim());
      }
      if (CommonFunction.checkNull(vo.getBeneficiaryIfscCode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBeneficiaryIfscCode().trim());
      }
      if (CommonFunction.checkNull(vo.getMaturityDate1()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMaturityDate1().trim());
      }

      if (CommonFunction.checkNull(vo.getLoanDisbursalId()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLoanDisbursalId().trim());
      }
      insertPrepStmtObject.setSql(query.toString());
      logger.info(new StringBuilder().append("IN updateDisbursalDataWithPayment() update query1   :   ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);

      logger.info(new StringBuilder().append("In updateDisbursalDataWithPayment.........update status: ").append(status).toString());
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In updateDisbursalDataWithPayment.........update status: ").append(status).toString());

      query2.append("update cr_loan_dtl set ");

      if (CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")) {
        query2.append("LOAN_REPAY_EFF_DATE=LOAN_REPAY_EFF_DATE,");
      }
      if (!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")) {
        query2.append(new StringBuilder().append("LOAN_REPAY_EFF_DATE=STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),").toString());
      }
      if (CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")) {
        query2.append(" LOAN_DUE_DAY=LOAN_DUE_DAY,");
      }
      if (!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")) {
        query2.append(" LOAN_DUE_DAY=?,");
      }
      if (CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")) {
        query2.append("NEXT_DUE_DATE=NEXT_DUE_DATE,");
      }
      if (!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")) {
        query2.append(new StringBuilder().append("NEXT_DUE_DATE=STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),").toString());
      }

      if (CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append("loan_tenure=loan_tenure,");
      }
      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append(" loan_tenure=?,");
      }
      if (CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append("LOAN_NO_OF_INSTALLMENT=LOAN_NO_OF_INSTALLMENT");
      }
      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        query2.append(" LOAN_NO_OF_INSTALLMENT=?");
      }
      logger.info(new StringBuilder().append(" At Update with payment Method ::").append(CommonFunction.checkNull(vo.getLoanTenure())).toString());

      query2.append(" where loan_id=? ");

      if (!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getRepayEffDate().trim());
      }

      if (!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getCycleDate().trim());
      }

      if (!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getNextDueDate().trim());
      }

      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getLoanTenure().trim());
      }

      if (!CommonFunction.checkNull(vo.getLoanTenure()).equalsIgnoreCase("")) {
        updatePrepStmtObject.addString(vo.getLoanTenure().trim());
      }

      updatePrepStmtObject.addString(vo.getLbxLoanNoHID());
      logger.info(new StringBuilder().append("IN updateDisbursalDataWithPayment() update cr_loan_dtl query3 ###5 ").append(updatePrepStmtObject.printQuery()).toString());

      updatePrepStmtObject.setSql(query2.toString());

      logger.info(new StringBuilder().append("IN updateDisbursalDataWithPayment() update cr_loan_dtl query3 ###6 ").append(updatePrepStmtObject.printQuery()).toString());
      qryListUpdate.add(updatePrepStmtObject);
      logger.info(new StringBuilder().append("In updateDisbursalDataWithPayment ....cr_loan_dtl.... update query: ").append(query2).toString());

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      StringBuilder queryUpdate2 = new StringBuilder();
      updatePrepStmtObject2 = new PrepStmtObject();
      queryUpdate2.append(new StringBuilder().append("update cr_loan_dtl set loan_maturity_date=(select loan_maturity_date from cr_loan_disbursal_dtl_temp where loan_id='").append(vo.getLbxLoanNoHID()).append("')").toString());
      updatePrepStmtObject2.setSql(queryUpdate2.toString());
      qryListUpdate.add(updatePrepStmtObject2);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      logger.info(" Update maturity date :: 4");

      if (CommonFunction.checkNull(forStatus).equalsIgnoreCase("F")) {
        StringBuilder queryUpdate = new StringBuilder();
        queryUpdate.append(new StringBuilder().append("upadte cr_loan_disbursal_add_dtl set REC_STATUS='F' where LOAN_DISBURSAL_ID='").append(vo.getLbxLoanNoHID()).append("'").toString());
        updatePrepStmtObject = null;
        updatePrepStmtObject = new PrepStmtObject();
        updatePrepStmtObject.setSql(queryUpdate.toString());
        logger.info(new StringBuilder().append("queryUpdate:--cr_loan_disbursal_add_dtl--in ").append(queryUpdate).toString());
        qryListUpdate.add(queryUpdate.toString());
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
      }

      logger.info(new StringBuilder().append("In updateDisbursalDataWithPayment.........update3 status: ").append(status).toString());
      if (status)
        result = "saved";
      else {
        result = "notsaved";
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      loanId = null;
      qryList = null;
      query = null;
      insertPrepStmtObject = null;
      updatePrepStmtObject = null;
      query2 = null;
      qryListUpdate = null;
    }
    return result;
  }

  public String saveDisbursalPaymentAddDetails(DisbursalMakerVO vo)
  {
    String paymentFlag = "";
    String taFlag = "";
    boolean status = false;
    String resultStr = "";
    ArrayList qryList = new ArrayList();
    ArrayList qryListUpdate = new ArrayList();
    StringBuilder query = new StringBuilder();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try {
      logger.info("In saveDisbursalPaymentAddDetails:---");
      if ((vo.getPaymentFlag() != null) && (vo.getPaymentFlag().equalsIgnoreCase("on")))
        paymentFlag = "Y";
      else {
        paymentFlag = "N";
      }
      if ((vo.getTaFlag() != null) && (vo.getTaFlag().equalsIgnoreCase("on")))
        taFlag = "Y";
      else {
        taFlag = "N";
      }
      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS")) {
        String selQry = new StringBuilder().append("select LOAN_CUSTOMER_ID from cr_loan_dtl where loan_id=(select Loan_id from cr_loan_disbursal_dtl where loan_disbursal_id='").append(vo.getLoanDisbursalId()).append("')").toString();
        String cusId = ConnectionDAO.singleReturn(selQry);
        logger.info(new StringBuilder().append("cusId from vo:----").append(vo.getLbxBusinessPartnearHID()).toString());
        logger.info(new StringBuilder().append("cusId:----").append(cusId).toString());
        vo.setLbxBusinessPartnearHID(cusId);
      }
      logger.info(new StringBuilder().append("cusId from vo after Select:----").append(vo.getLbxBusinessPartnearHID()).toString());
      query.append("insert into   CR_LOAN_DISBURSAL_ADD_DTL(LOAN_DISBURSAL_ID,");
      query.append("LOAN_ID,DISBURSAL_NO,DISBURSAL_TO,DISBURSAL_TO_ID,DISBURSAL_TO_NAME,");
      query.append("DISBURSAL_AMOUNT,NET_AMOUNT,TOTAL_PAYABLE,");
      query.append("ADJUST_TOTAL_PAYABLE,TOTAL_RECEIVABLE,ADJUST_TOTAL_RECEIVABLE,");
      query.append("PAYMENT_FLAG,TA_FLAG,TA_LOAN_ID,TA_PARTY_NAME,");
      query.append("PMNT_MODE,PMNT_DATE,INSTRUMENT_NO,INSTRUMENT_DATE,BANK_ACCOUNT,");
      query.append("BANK_ID,BRANCH_ID,MICR_CODE,IFSC_CODE,PMNT_AMOUNT,");
      query.append("TDS_AMOUNT,PMNT_REMARK,REC_STATUS,MAKER_ID,MAKER_DATE)");
      query.append("values(?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      query.append("?,");
      query.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append("?,");
      query.append(" 'P',");
      query.append(" ?,");
      query.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND))").toString());

      if (CommonFunction.checkNull(vo.getLoanDisbursalId()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLoanDisbursalId().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxLoanNoHID().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalNo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalNo().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalTo().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBusinessPartnearHID().trim());
      }
      if (CommonFunction.checkNull(vo.getBusinessPartnerTypeDesc()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBusinessPartnerTypeDesc().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getNetAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getTotalPayable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getTotalPayable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getAdjustTotalPayable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getAdjustTotalPayable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getTotalReceivable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getTotalReceivable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getAdjustTotalReceivable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getAdjustTotalReceivable()).trim()).toString());
      }
      if (CommonFunction.checkNull(paymentFlag).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(paymentFlag).trim());
      }
      if (CommonFunction.checkNull(taFlag).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(taFlag).trim());
      }
      if (CommonFunction.checkNull(vo.getTaLoanNo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getTaLoanNo()).trim());
      }
      if (CommonFunction.checkNull(vo.getTaCustomerName()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getTaCustomerName()).trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getPaymentMode()).trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getPaymentDate()).trim());
      }
      if (CommonFunction.checkNull(vo.getInstrumentNumber()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getInstrumentNumber()).trim());
      }
      if (CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getInstrumentDate()).trim());
      }
      if (CommonFunction.checkNull(vo.getBankAccount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBankAccount().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBankID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBankID().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBranchID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBranchID().trim());
      }
      if (CommonFunction.checkNull(vo.getMicr()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMicr().trim());
      }
      if (CommonFunction.checkNull(vo.getIfsCode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getIfsCode().trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getPaymentAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getTdsAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getTdsAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getRemarks().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerId().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerDate().trim());
      }
      insertPrepStmtObject.setSql(query.toString());

      logger.info(new StringBuilder().append("IN saveDisbursalDataAddDetails() insert query### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      if (status)
        resultStr = "saved";
      else {
        resultStr = "notsaved";
      }
      logger.info(new StringBuilder().append("In saveDisbursalPaymentAddDetails    status:---").append(status).toString());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      paymentFlag = null;
      taFlag = null;
      qryList = null;
      qryListUpdate = null;
      query = null;
    }
    return resultStr;
  }
  public String updateDisbursalPaymentAddDetails(DisbursalMakerVO vo) {
    String paymentFlag = "";
    String taFlag = "";
    boolean status = false;
    String resultStr = "";
    ArrayList qryList = new ArrayList();
    ArrayList qryListUpdate = new ArrayList();
    StringBuilder query = new StringBuilder();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try {
      logger.info("In updateDisbursalPaymentAddDetails.....");
      if ((vo.getPaymentFlag() != null) && (vo.getPaymentFlag().equalsIgnoreCase("on")))
        paymentFlag = "Y";
      else {
        paymentFlag = "N";
      }
      if ((vo.getTaFlag() != null) && (vo.getTaFlag().equalsIgnoreCase("on")))
        taFlag = "Y";
      else {
        taFlag = "N";
      }
      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS")) {
        String selQry = new StringBuilder().append("select LOAN_CUSTOMER_ID from cr_loan_dtl where loan_id=(select Loan_id from cr_loan_disbursal_dtl where loan_disbursal_id='").append(vo.getLoanDisbursalId()).append("')").toString();
        String cusId = ConnectionDAO.singleReturn(selQry);
        logger.info(new StringBuilder().append("cusId from vo:----").append(vo.getLbxBusinessPartnearHID()).toString());
        logger.info(new StringBuilder().append("cusId:----").append(cusId).toString());
        vo.setLbxBusinessPartnearHID(cusId);
      }
      logger.info(new StringBuilder().append("cusId from vo after Select:----").append(vo.getLbxBusinessPartnearHID()).toString());
      query.append("Update CR_LOAN_DISBURSAL_ADD_DTL set ");
      query.append("DISBURSAL_TO=?,DISBURSAL_TO_ID=?,DISBURSAL_TO_NAME=?,");
      query.append("DISBURSAL_AMOUNT=?,NET_AMOUNT=?,TOTAL_PAYABLE=?,");
      query.append("ADJUST_TOTAL_PAYABLE=?,TOTAL_RECEIVABLE=?,ADJUST_TOTAL_RECEIVABLE=?,");
      query.append("PAYMENT_FLAG=?,TA_FLAG=?,TA_LOAN_ID=?,TA_PARTY_NAME=?,");
      query.append(new StringBuilder().append("PMNT_MODE=?,PMNT_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'),INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'),BANK_ACCOUNT=?,").toString());
      query.append("BANK_ID=?,BRANCH_ID=?,MICR_CODE=?,IFSC_CODE=?,PMNT_AMOUNT=?,");
      query.append(new StringBuilder().append("TDS_AMOUNT=?,REC_STATUS='P',PMNT_REMARK=?,MAKER_ID=?,MAKER_DATE= DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ").toString());
      query.append(" where LOAN_DISBURSAL_ADD_ID=?");

      if (CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDisbursalTo().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBusinessPartnearHID().trim());
      }
      if (CommonFunction.checkNull(vo.getBusinessPartnerTypeDesc()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBusinessPartnerTypeDesc().trim());
      }
      if (CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getNetAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getTotalPayable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getTotalPayable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getAdjustTotalPayable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getAdjustTotalPayable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getTotalReceivable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getTotalReceivable()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getAdjustTotalReceivable()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getAdjustTotalReceivable()).trim()).toString());
      }
      if (CommonFunction.checkNull(paymentFlag).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(paymentFlag).trim());
      }
      if (CommonFunction.checkNull(taFlag).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(taFlag).trim());
      }
      if (CommonFunction.checkNull(vo.getTaLoanNo()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getTaLoanNo()).trim());
      }
      if (CommonFunction.checkNull(vo.getTaCustomerName()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getTaCustomerName()).trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getPaymentMode()).trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getPaymentDate()).trim());
      }
      if (CommonFunction.checkNull(vo.getInstrumentNumber()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getInstrumentNumber()).trim());
      }
      if (CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getInstrumentDate()).trim());
      }
      if (CommonFunction.checkNull(vo.getBankAccount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBankAccount().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBankID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBankID().trim());
      }
      if (CommonFunction.checkNull(vo.getLbxBranchID()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxBranchID().trim());
      }
      if (CommonFunction.checkNull(vo.getMicr()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMicr().trim());
      }
      if (CommonFunction.checkNull(vo.getIfsCode()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getIfsCode().trim());
      }
      if (CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getPaymentAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getTdsAmount()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getTdsAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getRemarks().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerId().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerDate().trim());
      }
      if (CommonFunction.checkNull(vo.getLoanDisbursalAddId()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLoanDisbursalAddId().trim());
      }
      insertPrepStmtObject.setSql(query.toString());

      logger.info(new StringBuilder().append("IN updateDisbursalPaymentAddDetails() update query### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      if (status)
        resultStr = "saved";
      else
        resultStr = "notsaved";
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } finally {
      paymentFlag = null;
      taFlag = null;
      qryList = null;
      qryListUpdate = null;
      query = null;
    }
    return resultStr;
  }
  public ArrayList selectAddDetailsForUpdate(DisbursalMakerVO vo) {
    ArrayList mainList = new ArrayList();
    ArrayList subList = new ArrayList();
    StringBuilder query = new StringBuilder();
    StringBuilder query2 = new StringBuilder();
    ArrayList returnList = new ArrayList();
    String disbursedAmountStr = "";
    try
    {
      query2.append(new StringBuilder().append("select SUM(IFNULL(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(vo.getLbxLoanNoHID()).append("' AND REC_STATUS='A'").toString());

      disbursedAmountStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
      logger.info(new StringBuilder().append("In selectAddDetailsForUpdate.....disbursedAmount: ").append(disbursedAmountStr).toString());
      String table = "";
      if (CommonFunction.checkNull(vo.getFrom()).equalsIgnoreCase("A"))
        table = "cr_loan_disbursal_dtl";
      else {
        table = "cr_loan_disbursal_dtl_temp";
      }
      query.append(new StringBuilder().append("SELECT a.loan_no, b.customer_name, a.loan_loan_amount, DATE_FORMAT(a.loan_approval_date,'").append(this.dateFormat).append("'), c.product_desc, d.scheme_desc,").toString());
      query.append(" a.LOAN_ID,e.disbursal_no, e.disbursal_description,");
      query.append(new StringBuilder().append(" DATE_FORMAT(e.disbursal_date,'").append(this.dateFormat).append("'),e.disbursal_amount,e.loan_due_day,DATE_FORMAT(e.NEXT_DUE_DATE,'").append(this.dateFormat).append("'),e.disbursal_flag,").toString());
      query.append(new StringBuilder().append(" e.CUR_MONTH_EMI,e.NEXT_MONTH_EMI,DATE_FORMAT(e.REPAY_EFF_DATE,'").append(this.dateFormat).append("'),").toString());
      query.append(new StringBuilder().append(" DATE_FORMAT(e.PENAL_INT_CALC_DATE,'").append(this.dateFormat).append("'),").toString());
      query.append(" e.DISBURSAL_TO,e.DISBURSAL_TO_ID,e.DISBURSAL_TO_NAME,e.NET_AMOUNT,e.ADJUST_TOTAL_PAYABLE,e.SHORT_AMOUNT_ADJUSTED,");
      query.append(" e.PAYMENT_FLAG,e.TA_LOAN_ID,(select loan_no from cr_loan_dtl cld where cld.loan_id=e.TA_LOAN_ID) TA_LOAN_NO,");
      query.append(new StringBuilder().append(" e.TA_PARTY_NAME,e.PMNT_MODE,DATE_FORMAT(e.PMNT_DATE,'").append(this.dateFormat).append("'),").toString());
      query.append(new StringBuilder().append(" e.INSTRUMENT_NO,DATE_FORMAT(e.INSTRUMENT_DATE,'").append(this.dateFormat).append("'),e.BANK_ACCOUNT,e.BANK_ACCOUNT as Bank_acc,").toString());

      query.append("e.BANK_ID,(Select BANK_NAME from com_bank_m bank where bank.BANK_ID=e.BANK_ID) Bank_Name,");
      query.append("e.BRANCH_ID,(Select BANK_BRANCH_NAME from com_bankbranch_m br where br.BANK_BRANCH_ID=e.BRANCH_ID) Branch_Name,");
      query.append(" e.MICR_CODE,e.IFSC_CODE,e.PMNT_AMOUNT,e.TDS_AMOUNT,e.PMNT_REMARK,a.loan_repayment_type,");
      query.append(new StringBuilder().append(" (select count(PDC_LOAN_ID) from cr_pdc_instrument_dtl where  PDC_STATUS = 'A' and PDC_PURPOSE='INSTALLMENT' and PDC_LOAN_ID='").append(vo.getLbxLoanNoHID()).append("'),e.LOAN_DISBURSAL_ID,e.PAY_TO,e.PAYEE_NAME,e.REMARKS, ").toString());
      query.append(new StringBuilder().append("(SELECT IFNULL(SUM(IFNULL(DISBURSAL_AMOUNT,0)),0)FROM CR_LOAN_DISBURSAL_DTL_TEMP WHERE LOAN_ID ='").append(vo.getLbxLoanNoHID()).append("' AND e.LOAN_DISBURSAL_ID !='").append(vo.getLoanDisbursalId()).append("' AND REC_STATUS='P')as disbursedAmountTemp, ").toString());
      query.append(new StringBuilder().append(" (select VALUE from generic_master where  GENERIC_KEY='BPTYPE'  and DESCRIPTION=e.PAY_TO),e.remarks, case e.pay_to when 'cs' then 'CUSTOMER' when 'MF' then 'MANUFACTURER'  when 'OTH'  then\t'OTHER' when 'RSP' then 'RSP' when 'SU' then 'SUPPLIER' end as payto,e.loan_curtail,DATE_FORMAT(e.INTEREST_DUE_DATE,'").append(this.dateFormat).append("'),").append(" BENEFICIARY_BANK_ID,bank_name,BENEFICIARY_BANK_BRANCH_ID,bank_branch_name,BENEFICIARY_BANK_ACCOUNT,BENEFICIARY_BRANCH_IFCS_CODE ,a.edit_due_date,DATE_FORMAT(a.LOAN_REPAY_EFF_DATE,'").append(this.dateFormat).append("'),a.loan_tenure,DATE_FORMAT(e.LOAN_MATURITY_DATE,'").append(this.dateFormat).append("'),a.loan_installment_type ").append(" from  cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, ").append(table).append(" e ").append(" left join com_bank_m cm on cm.bank_id=e.BENEFICIARY_BANK_ID left join com_bankBranch_m cbm on cbm.bank_branch_code=e.BENEFICIARY_BANK_BRANCH_ID").toString());

      query.append(new StringBuilder().append(" where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.LOAN_DISBURSAL_ID='").append(vo.getLoanDisbursalId()).append("'").toString());
      logger.info(new StringBuilder().append("in selectAddDetailsForUpdate Select query :--------").append(query.toString()).toString());
      mainList = ConnectionDAO.sqlSelect(query.toString());
      int size = mainList.size();
      if (size != 0)
        for (int i = 0; i < size; i++)
        {
          subList = (ArrayList)mainList.get(i);
          int subSize = subList.size();
          if (subSize > 0) {
            DisbursalMakerVO disVo = new DisbursalMakerVO();
            disVo.setLoanNo(CommonFunction.checkNull(subList.get(0)));
            disVo.setCustomerName(CommonFunction.checkNull(subList.get(1)));
            if (CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase("")) {
              disVo.setLoanAmt("0.00");
            } else {
              Number loanAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
              disVo.setLoanAmt(this.myFormatter.format(loanAmount));
            }

            disVo.setLoanApprovalDate(CommonFunction.checkNull(subList.get(3)).trim());
            disVo.setProduct(CommonFunction.checkNull(subList.get(4)).trim());
            disVo.setScheme(CommonFunction.checkNull(subList.get(5)).trim());
            disVo.setLbxLoanNoHID(CommonFunction.checkNull(subList.get(6)).trim());
            disVo.setDisbursalNo(CommonFunction.checkNull(subList.get(7)).trim());
            disVo.setDisbursalDescription(CommonFunction.checkNull(subList.get(8)).trim());
            disVo.setDisbursalDate(CommonFunction.checkNull(subList.get(9)).trim());
            logger.info(new StringBuilder().append("setDisbursalAmount:======").append(subList.get(10)).toString());
            if (CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase(""))
            {
              disVo.setDisbursalAmount("0.0");
            }
            else
            {
              Number disbursalAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(10)).trim());
              disVo.setDisbursalAmount(this.myFormatter.format(disbursalAmount));
            }

            disVo.setCycleDateValue(CommonFunction.checkNull(subList.get(11)).trim());
            disVo.setNextDueDate(CommonFunction.checkNull(subList.get(12)).trim());
            disVo.setFinalDisbursal(CommonFunction.checkNull(subList.get(13)).trim());
            logger.info(new StringBuilder().append("setFinalDisbursal:======").append(subList.get(13)).toString());
            if (CommonFunction.checkNull(subList.get(14)).trim().equalsIgnoreCase("")) {
              disVo.setCurrentMonthEMI("");
            } else {
              Number amount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(14)).trim());
              disVo.setCurrentMonthEMI(this.myFormatter.format(amount));
            }
            if (CommonFunction.checkNull(subList.get(15)).trim().equalsIgnoreCase("")) {
              disVo.setPreEMINextMonth("");
            } else {
              Number amount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(15)).trim());
              disVo.setPreEMINextMonth(this.myFormatter.format(amount));
            }
            disVo.setRepayEffDate(CommonFunction.checkNull(subList.get(16)));
            disVo.setPenalIntCalcDate(CommonFunction.checkNull(subList.get(17)));
            disVo.setDisbursalTo(CommonFunction.checkNull(subList.get(18)).trim());
            disVo.setLbxBusinessPartnearHID(CommonFunction.checkNull(subList.get(19)).trim());
            disVo.setBusinessPartnerTypeDesc(CommonFunction.checkNull(subList.get(20)).trim());
            logger.info(new StringBuilder().append("setBusinessPartnerTypeDesc:---------------------").append(CommonFunction.checkNull(subList.get(20)).trim()).toString());
            if (CommonFunction.checkNull(subList.get(21)).trim().equalsIgnoreCase("")) {
              disVo.setNetAmount("0.00");
            } else {
              Number netAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(21)).trim());
              disVo.setNetAmount(this.myFormatter.format(netAmount));
            }
            if (CommonFunction.checkNull(subList.get(22)).trim().equalsIgnoreCase("")) {
              disVo.setAdjustTotalPayable("0.00");
            } else {
              Number netAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(22)).trim());
              disVo.setAdjustTotalPayable(this.myFormatter.format(netAmount));
            }
            if (CommonFunction.checkNull(subList.get(23)).trim().equalsIgnoreCase("")) {
              disVo.setAdjustTotalReceivable("0.00");
            } else {
              Number netAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(23)).trim());
              disVo.setAdjustTotalReceivable(this.myFormatter.format(netAmount));
            }
            disVo.setPaymentFlag(CommonFunction.checkNull(subList.get(24)));
            disVo.setLbxTaLoanNoHID(CommonFunction.checkNull(subList.get(25)));
            disVo.setTaLoanNo(CommonFunction.checkNull(subList.get(26)));
            disVo.setTaCustomerName(CommonFunction.checkNull(subList.get(27)));
            disVo.setPaymentMode(CommonFunction.checkNull(subList.get(28)));
            disVo.setPaymentDate(CommonFunction.checkNull(subList.get(29)));
            disVo.setInstrumentNumber(CommonFunction.checkNull(subList.get(30)));
            disVo.setInstrumentDate(CommonFunction.checkNull(subList.get(31)));
            disVo.setLbxbankAccountID(CommonFunction.checkNull(subList.get(32)));
            disVo.setBankAccount(CommonFunction.checkNull(subList.get(33)));
            disVo.setLbxBankID(CommonFunction.checkNull(subList.get(34)));
            disVo.setBank(CommonFunction.checkNull(subList.get(35)));
            disVo.setLbxBranchID(CommonFunction.checkNull(subList.get(36)));
            disVo.setBranch(CommonFunction.checkNull(subList.get(37)));
            disVo.setMicr(CommonFunction.checkNull(subList.get(38)));
            disVo.setIfsCode(CommonFunction.checkNull(subList.get(39)));
            if (CommonFunction.checkNull(subList.get(40)).trim().equalsIgnoreCase(""))
            {
              disVo.setPaymentAmount("0.0");
            }
            else
            {
              Number payAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(40)).trim());
              disVo.setPaymentAmount(this.myFormatter.format(payAmount));
            }

            if (CommonFunction.checkNull(subList.get(41)).trim().equalsIgnoreCase(""))
            {
              disVo.setTdsAmount("0.0");
            }
            else
            {
              Number tdsAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(41)).trim());
              disVo.setTdsAmount(this.myFormatter.format(tdsAmount));
            }

            disVo.setRemarks(CommonFunction.checkNull(subList.get(42)).trim());
            disVo.setRepayMode(CommonFunction.checkNull(subList.get(43)).trim());
            logger.info(new StringBuilder().append("setRepayMode:======").append(subList.get(43)).toString());
            disVo.setPdcDepositCount(CommonFunction.checkNull(subList.get(44)).trim());
            disVo.setLoanDisbursalId(CommonFunction.checkNull(subList.get(45)).trim());
            ArrayList recList = getTotalPayableReceiable(vo.getLbxLoanNoHID(), disVo.getDisbursalTo());
            disVo.setTotalPayable(recList.get(0).toString());
            disVo.setTotalReceivable(recList.get(1).toString());
            disVo.setTotalReceivableCustomer(recList.get(2).toString());
            if (CommonFunction.checkNull(disbursedAmountStr).equalsIgnoreCase(""))
            {
              disVo.setDisbursedAmount("0.0");
            }
            else
            {
              Number disb = this.myFormatter.parse(CommonFunction.checkNull(disbursedAmountStr).trim());
              disVo.setDisbursedAmount(this.myFormatter.format(disb));
            }

            disVo.setPayTo(CommonFunction.checkNull(subList.get(52)).trim());
            disVo.setPayeeName(CommonFunction.checkNull(subList.get(47)).trim());
            disVo.setAuthorRemarks(CommonFunction.checkNull(subList.get(48)).trim());
            disVo.setDisbursedAmountTemp(CommonFunction.checkNull(subList.get(49)).trim());
            disVo.setLbxpayTo(CommonFunction.checkNull(subList.get(46)).trim());
            disVo.setAuthorRemarks(CommonFunction.checkNull(subList.get(51)).trim());
            disVo.setLoanCurtail(CommonFunction.checkNull(subList.get(53)).trim());
            disVo.setInterestDueDate(CommonFunction.checkNull(subList.get(54)).trim());

            disVo.setBeneficiaryLbxBankID(CommonFunction.checkNull(subList.get(55)).trim());
            disVo.setBeneficiaryBankCode(CommonFunction.checkNull(subList.get(56)).trim());
            disVo.setBeneficiaryLbxBranchID(CommonFunction.checkNull(subList.get(57)).trim());
            disVo.setBeneficiaryBankBranchName(CommonFunction.checkNull(subList.get(58)).trim());
            disVo.setBeneficiaryAccountNo(CommonFunction.checkNull(subList.get(59)).trim());
            disVo.setBeneficiaryIfscCode(CommonFunction.checkNull(subList.get(60)).trim());

            disVo.setEditDueDate(CommonFunction.checkNull(subList.get(61)).trim());
            disVo.setOldRepayEffDate(CommonFunction.checkNull(subList.get(62)).trim());
            disVo.setLoanTenure(CommonFunction.checkNull(subList.get(63)).trim());
            disVo.setMaturityDate1(CommonFunction.checkNull(subList.get(64)).trim());
            disVo.setInstallmentType(CommonFunction.checkNull(subList.get(65)).trim());
            logger.info(new StringBuilder().append("setInstallmentType123:---------------------").append(CommonFunction.checkNull(subList.get(65)).trim()).toString());
            logger.info(new StringBuilder().append("setMaturityDate1:---------------------").append(CommonFunction.checkNull(subList.get(64)).trim()).toString());

            returnList.add(disVo);
            disVo = null;
          }
        }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally {
      mainList = null;
      subList = null;
      query = null;
      query2 = null;
      disbursedAmountStr = null;
    }
    return returnList;
  }

  public ArrayList selectAddDetailsList(DisbursalMakerVO vo, String from) {
    ArrayList mainList = new ArrayList();
    ArrayList subList = new ArrayList();
    StringBuilder query = new StringBuilder();
    ArrayList returnList = new ArrayList();
    String recStatus = "";
    try
    {
      String table = "";
      if (!CommonFunction.checkNull(from).equalsIgnoreCase("A")) {
        recStatus = "P";
        table = "cr_loan_disbursal_dtl_temp";
      } else {
        recStatus = "F";
        table = "cr_loan_disbursal_dtl";
      }
      query.append(new StringBuilder().append("Select LOAN_DISBURSAL_ID,a.LOAN_ID,b.LOAN_NO,DISBURSAL_NO,DISBURSAL_DESCRIPTION,DATE_FORMAT(DISBURSAL_DATE ,'").append(this.dateFormat).append("') DISBURSAL_DATE,").toString());
      query.append("DISBURSAL_AMOUNT ,DISBURSAL_FLAG,(select DESCRIPTION from generic_master where  GENERIC_KEY='BPTYPE'  and value=DISBURSAL_TO),remarks ");
      query.append(new StringBuilder().append("from ").append(table).append(" a join cr_loan_dtl b on a.loan_id=b.loan_id ").toString());
      query.append(new StringBuilder().append(" where a.loan_id='").append(vo.getLbxLoanNoHID()).append("' and a.REC_STATUS='").append(recStatus).append("' ").toString());
      if (recStatus.equalsIgnoreCase("F")) {
        query.append(new StringBuilder().append("and DISBURSAL_BATCH_ID='").append(vo.getDisbursalBatchId()).append("'").toString());
      }

      logger.info(new StringBuilder().append("in selectAddDetailsList Select query :--------").append(query.toString()).toString());
      mainList = ConnectionDAO.sqlSelect(query.toString());
      int size = mainList.size();
      if (size != 0)
        for (int i = 0; i < size; i++)
        {
          subList = (ArrayList)mainList.get(i);
          int subSize = subList.size();
          if (subSize > 0) {
            DisbursalMakerVO disVo = new DisbursalMakerVO();
            disVo.setLoanDisbursalId(CommonFunction.checkNull(subList.get(0)));
            if (!CommonFunction.checkNull(from).equalsIgnoreCase("A")) {
              disVo.setLoanNo(new StringBuilder().append("<a href=# onclick=openEditDisbursalDetails('").append(CommonFunction.checkNull(subList.get(0))).append("','").append(CommonFunction.checkNull(subList.get(1))).append("','").append(CommonFunction.checkNull(subList.get(3))).append("','M') >").append(CommonFunction.checkNull(subList.get(2)).trim()).append("</a>").toString());
            }
            else {
              disVo.setLoanNo(new StringBuilder().append("<a href=# onclick=openEditDisbursalDetails('").append(CommonFunction.checkNull(subList.get(0))).append("','").append(CommonFunction.checkNull(subList.get(1))).append("','").append(CommonFunction.checkNull(subList.get(3))).append("','A') >").append(CommonFunction.checkNull(subList.get(2)).trim()).append("</a>").toString());
            }

            disVo.setLbxLoanNoHID(CommonFunction.checkNull(subList.get(1)));
            disVo.setDisbursalNo(CommonFunction.checkNull(subList.get(3)).trim());

            disVo.setDisbursalDescription(CommonFunction.checkNull(subList.get(4)).trim());
            disVo.setDisbursalDate(CommonFunction.checkNull(subList.get(5)).trim());
            if (CommonFunction.checkNull(subList.get(6)).trim().equalsIgnoreCase(""))
            {
              disVo.setDisbursalAmount("0.00");
            }
            else
            {
              Number netAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(6)).trim());
              disVo.setDisbursalAmount(this.myFormatter.format(netAmount));
            }
            disVo.setFinalDisbursal(CommonFunction.checkNull(subList.get(7)).trim());
            disVo.setDisbursalTo(CommonFunction.checkNull(subList.get(8)).trim());
            disVo.setAuthorRemarks(CommonFunction.checkNull(subList.get(9)).trim());
            returnList.add(disVo);
            disVo = null;
          }
        }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally {
      mainList = null;
      subList = null;
      query = null;
      recStatus = null;
      from = null;
      vo = null;
    }
    return returnList;
  }

  public ArrayList getTotalPayableReceiable(String loanid, String customerType) {
    StringBuilder qryPayable = new StringBuilder();
    StringBuilder qryReceiable = new StringBuilder();
    StringBuilder qryReceiableCustomer = new StringBuilder();
    ArrayList returnList = new ArrayList();
    String totalpayable = "";
    String totalReceiable = "";
    String totalReceiveable = "";
    String flagVal = "";
    String totalReceiableAmount = "";
    String query = "";
    String totalReceivedAmount = "";
    try {
      qryPayable.append("SELECT sum((ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))) totalBalance FROM cr_txnadvice_dtl ");
      qryPayable.append(" WHERE REC_STATUS in('A','F')  AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0  AND");
      qryPayable.append(new StringBuilder().append(" ADVICE_TYPE='P' AND LOAN_ID='").append(loanid).append("' AND BP_TYPE ='").append(customerType).append("'").toString());
      logger.info(new StringBuilder().append(" Query String of total payable:----").append(qryPayable.toString()).toString());
      totalpayable = CommonFunction.checkNull(ConnectionDAO.singleReturn(qryPayable.toString()));
      if (totalpayable.equalsIgnoreCase("")) {
        returnList.add("0.0");
      } else {
        Number payAmount = this.myFormatter.parse(CommonFunction.checkNull(totalpayable).trim());
        returnList.add(this.myFormatter.format(payAmount));
      }

      qryReceiable.append("SELECT sum((ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))) totalBalance FROM cr_txnadvice_dtl ");
      qryReceiable.append(" WHERE REC_STATUS in('A','F')  AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0  AND");
      qryReceiable.append(new StringBuilder().append(" ADVICE_TYPE='R' AND LOAN_ID='").append(loanid).append("' AND IFNULL(CHARGE_CODE_ID,0)<>8  AND BP_TYPE ='").append(customerType).append("'").toString());
      logger.info(new StringBuilder().append(" query String of total qryReceiable:----").append(qryReceiable.toString()).toString());
      totalReceiable = ConnectionDAO.singleReturn(qryReceiable.toString());
      if (totalReceiable.equalsIgnoreCase("")) {
        returnList.add("0.0");
      } else {
        Number recAmount = this.myFormatter.parse(CommonFunction.checkNull(totalReceiable).trim());
        returnList.add(this.myFormatter.format(recAmount));
      }

      totalReceiveable = "Select parameter_value from parameter_mst where parameter_key='TOTAL_RECEIVABLE_CURRENT_SHORT_PAY'";
      logger.info(new StringBuilder().append("short Receivable status :----").append(totalReceiveable).toString());
      flagVal = CommonFunction.checkNull(ConnectionDAO.singleReturn(totalReceiveable));
      if (CommonFunction.checkNull(flagVal).equalsIgnoreCase("Y"))
      {
        qryReceiableCustomer.append("SELECT sum((ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))) totalBalance FROM cr_txnadvice_dtl ");
        qryReceiableCustomer.append(" WHERE REC_STATUS in('A','F')  AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0  AND");
        qryReceiableCustomer.append(new StringBuilder().append(" ADVICE_TYPE='R' AND LOAN_ID='").append(loanid).append("' AND BP_TYPE ='CS' ").toString());
        logger.info(new StringBuilder().append(" query String of total qryReceiableCustomer:----").append(qryReceiableCustomer.toString()).toString());
        totalReceiableAmount = ConnectionDAO.singleReturn(qryReceiableCustomer.toString());
        if (totalReceiableAmount.equalsIgnoreCase("")) {
          returnList.add("0.0");
        } else {
          query = new StringBuilder().append("select SUM(SHORT_AMOUNT_ADJUSTED) from cr_loan_disbursal_dtl_temp where loan_id='").append(loanid).append("' and disbursal_to='CS'").toString();
          logger.info(new StringBuilder().append("query for totalReceiableAmount received : ").append(query).toString());
          totalReceivedAmount = ConnectionDAO.singleReturn(query);
          if (!CommonFunction.checkNull(totalReceivedAmount).equalsIgnoreCase(""))
          {
            totalReceiableAmount = Double.toString(Double.parseDouble(totalReceiableAmount) - Double.parseDouble(totalReceivedAmount));
          }
          Number recAmount = this.myFormatter.parse(CommonFunction.checkNull(totalReceiableAmount).trim());
          returnList.add(this.myFormatter.format(recAmount));
        }
      }
      else
      {
        returnList.add("0.0");
      }

    }
    catch (Exception e)
    {
    }
    finally
    {
      qryPayable = null;
      qryReceiable = null;
      qryReceiableCustomer = null;
      loanid = null;
      customerType = null;
      totalpayable = null;
      totalReceiable = null;
      totalReceiveable = null;
      flagVal = null;
      totalReceiableAmount = null;
      query = null;
      totalReceivedAmount = null;
    }
    return returnList;
  }

  public ArrayList getTotalShortPay(String loanid, String customerType) {
    StringBuilder qryReceiable = new StringBuilder();
    ArrayList returnList = new ArrayList();
    try {
      qryReceiable.append(new StringBuilder().append(" select SHORT_AMOUNT_ADJUSTED from cr_loan_disbursal_dtl_temp AND LOAN_ID='").append(loanid).append("' AND DISBURSAL_TO ='").append(customerType).append("'").toString());
      logger.info(new StringBuilder().append(" query String of total getTotalShortPay:----").append(qryReceiable.toString()).toString());
      String totalReceiable = ConnectionDAO.singleReturn(qryReceiable.toString());
      if (totalReceiable.equalsIgnoreCase("")) {
        returnList.add("0.0");
      } else {
        Number recAmount = this.myFormatter.parse(CommonFunction.checkNull(totalReceiable).trim());
        returnList.add(this.myFormatter.format(recAmount));
      }
    }
    catch (Exception e) {
    }
    return returnList;
  }

  public String deleteAddDetails(DisbursalMakerVO vo, String checkedStr)
  {
    String resultStr = "";

    ArrayList qryList = new ArrayList();
    StringBuilder query = new StringBuilder();
    PrepStmtObject deletePrepStmtObject = new PrepStmtObject();
    try {
      String[] addId = vo.getAddId();
      logger.info(new StringBuilder().append("addId:----------").append(addId).toString());
      String addIdStr = "";

      if (!CommonFunction.checkNull(checkedStr).equalsIgnoreCase("")) {
        addIdStr = checkedStr.substring(0, checkedStr.length() - 1);
        logger.info(new StringBuilder().append("addIdStr:----------").append(addIdStr).toString());
        String querySel = new StringBuilder().append("Select count(1) from cr_loan_disbursal_dtl_temp where  LOAN_DISBURSAL_ID in (").append(addIdStr).append(")").toString();
        String deleteFlag = ConnectionDAO.singleReturn(querySel);
        if (!CommonFunction.checkNull(deleteFlag).equalsIgnoreCase("0")) {
          query.append(new StringBuilder().append(" Delete  from cr_loan_disbursal_dtl_temp where  LOAN_DISBURSAL_ID in (").append(addIdStr).append(")").toString());
          logger.info(new StringBuilder().append("Delete query : ").append(query.toString()).toString());
          deletePrepStmtObject.setSql(query.toString());
          qryList.add(deletePrepStmtObject);
          boolean status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
          logger.info(new StringBuilder().append("status:----------").append(status).toString());
          if (status)
            resultStr = "deleted";
          else
            resultStr = "notdeleted";
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return resultStr;
  }

  public ArrayList getLoanAndDisburdesAmount(DisbursalMakerVO vo) {
    StringBuilder query1 = new StringBuilder();
    StringBuilder query2 = new StringBuilder();
    String disbursedAmountStr = "";
    String loanAmount = "";
    ArrayList returnList = new ArrayList();
    try {
      query1.append(new StringBuilder().append("select SUM(IFNULL(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '").append(vo.getLbxLoanNoHID()).append("' AND REC_STATUS='A'").toString());
      logger.info(new StringBuilder().append("In getDisbursalValues : ").append(query1).toString());
      disbursedAmountStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
      logger.info(new StringBuilder().append("In selectDisbursalData.....disbursedAmount: ").append(disbursedAmountStr).toString());
      query2.append(new StringBuilder().append("Select loan_loan_amount FROM CR_LOAN_DTL WHERE LOAN_ID = '").append(vo.getLbxLoanNoHID()).append("' AND REC_STATUS='A'").toString());
      logger.info(new StringBuilder().append("In getDisbursalValues : ").append(query2).toString());
      loanAmount = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
      DisbursalMakerVO nvo = new DisbursalMakerVO();
      nvo.setDisbursedAmount(disbursedAmountStr);
      nvo.setLoanAmt(loanAmount);
      returnList.add(nvo);
    }
    catch (Exception e) {
    }
    return returnList;
  }
  public String forwardDisbursal(DisbursalMakerVO vo, String[] loanDisbursalId) {
    String resultStr = "";
    StringBuilder bufInsSql = new StringBuilder();
    String loanAddIdstr = "";
    ArrayList qryList = new ArrayList();
    boolean status = false;
    boolean updateStatus = false;

    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try {
      String batchQuery = "Select VALUE from generate_sequence_tbl  where SEQ_KEY='DISBURSAL_BATCH_ID' ";
      String batchValueStr = ConnectionDAO.singleReturn(batchQuery);
      int batchValue = Integer.parseInt(batchValueStr);
      batchValue++;
      logger.info(new StringBuilder().append("batchValue:--------------------").append(batchValue).toString());

      for (int i = 0; i < loanDisbursalId.length; i++) {
        loanAddIdstr = new StringBuilder().append(loanAddIdstr).append(loanDisbursalId[i]).append(",").toString();
      }

      String DISBURSAL_NO_MAX = "0";
      String query = new StringBuilder().append(" select MAX(DISBURSAL_NO) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID='").append(vo.getLbxLoanNoHID()).append("' ").toString();
      logger.info(new StringBuilder().append("IN forwardDisbursal() MAX(DISBURSAL_NO)query### ").append(query).toString());
      DISBURSAL_NO_MAX = ConnectionDAO.singleReturn(query);
      if (CommonFunction.checkNull(DISBURSAL_NO_MAX).trim().equalsIgnoreCase("")) {
        DISBURSAL_NO_MAX = "0";
      }
      for (int i = 0; i < loanDisbursalId.length; i++)
      {
        int DISBURSAL_NO = Integer.parseInt(DISBURSAL_NO_MAX) + i + 1;
        insertPrepStmtObject = null;
        StringBuilder bufInsSql1 = new StringBuilder();
        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql1.append(new StringBuilder().append("UPDATE CR_LOAN_DISBURSAL_DTL_TEMP SET DISBURSAL_NO='").append(DISBURSAL_NO).append("'").toString());
        bufInsSql1.append(new StringBuilder().append(" WHERE LOAN_DISBURSAL_ID='").append(loanDisbursalId[i]).append("'").toString());
        insertPrepStmtObject.setSql(bufInsSql1.toString());
        logger.info(new StringBuilder().append("IN forwardDisbursal() update DISBURSAL_NO query### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        updateStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
      bufInsSql = null;
      insertPrepStmtObject = null;
      bufInsSql = new StringBuilder();
      insertPrepStmtObject = new PrepStmtObject();

      loanAddIdstr = loanAddIdstr.substring(0, loanAddIdstr.length() - 1);
      bufInsSql.append("Insert into cr_loan_disbursal_dtl(loan_id, disbursal_no, disbursal_description,");
      bufInsSql.append("disbursal_date,disbursal_amount,loan_due_day,NEXT_DUE_DATE,disbursal_flag,");
      bufInsSql.append("rec_status,maker_id,maker_date,CUR_MONTH_EMI,NEXT_MONTH_EMI,REPAY_EFF_DATE,");
      bufInsSql.append("old_repay_eff_Date,old_LOAN_DUE_DAY,old_NEXT_DUE_DATE,PENAL_INT_CALC_DATE,");
      bufInsSql.append("DISBURSAL_TO,DISBURSAL_TO_ID,DISBURSAL_TO_NAME,NET_AMOUNT,ADJUST_TOTAL_PAYABLE,");
      bufInsSql.append("PAYMENT_FLAG,TA_LOAN_ID,TA_PARTY_NAME,PMNT_MODE,PMNT_DATE,");
      bufInsSql.append("INSTRUMENT_NO,INSTRUMENT_DATE,BANK_ACCOUNT,BANK_ID,BRANCH_ID,MICR_CODE,IFSC_CODE,");
      bufInsSql.append("PMNT_AMOUNT,TDS_AMOUNT,PMNT_REMARK,SHORT_AMOUNT_ADJUSTED,DISBURSAL_BATCH_ID,PAY_TO,PAYEE_NAME,DEFAULT_BRANCH,remarks,LOAN_CURTAIL,BENEFICIARY_BANK_ID,BENEFICIARY_BANK_BRANCH_ID,BENEFICIARY_BANK_ACCOUNT,BENEFICIARY_BRANCH_IFCS_CODE,LOAN_MATURITY_DATE,INTEREST_DUE_DATE)");

      bufInsSql.append(" Select loan_id, disbursal_no, disbursal_description,");
      bufInsSql.append("disbursal_date,disbursal_amount,loan_due_day,NEXT_DUE_DATE,disbursal_flag,");
      bufInsSql.append(new StringBuilder().append("'F','").append(vo.getMakerId()).append("',DATE_ADD(STR_TO_DATE('").append(vo.getMakerDate()).append("', '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
      bufInsSql.append("CUR_MONTH_EMI,NEXT_MONTH_EMI,REPAY_EFF_DATE,");
      bufInsSql.append("old_repay_eff_Date,old_LOAN_DUE_DAY,old_NEXT_DUE_DATE,PENAL_INT_CALC_DATE,");
      bufInsSql.append("DISBURSAL_TO,DISBURSAL_TO_ID,DISBURSAL_TO_NAME,NET_AMOUNT,ADJUST_TOTAL_PAYABLE,");
      bufInsSql.append("PAYMENT_FLAG,TA_LOAN_ID,TA_PARTY_NAME,PMNT_MODE,PMNT_DATE,");
      bufInsSql.append("INSTRUMENT_NO,INSTRUMENT_DATE,BANK_ACCOUNT,BANK_ID,BRANCH_ID,MICR_CODE,IFSC_CODE,");
      bufInsSql.append(new StringBuilder().append("PMNT_AMOUNT,TDS_AMOUNT,PMNT_REMARK,SHORT_AMOUNT_ADJUSTED,'").append(batchValue).append("',PAY_TO,PAYEE_NAME,DEFAULT_BRANCH,remarks,LOAN_CURTAIL,").append("BENEFICIARY_BANK_ID,BENEFICIARY_BANK_BRANCH_ID,BENEFICIARY_BANK_ACCOUNT,BENEFICIARY_BRANCH_IFCS_CODE,LOAN_MATURITY_DATE,INTEREST_DUE_DATE FROM cr_loan_disbursal_dtl_temp ").toString());

      bufInsSql.append(new StringBuilder().append(" where LOAN_DISBURSAL_ID in (").append(loanAddIdstr).append(")").toString());
      insertPrepStmtObject.setSql(bufInsSql.toString());

      logger.info(new StringBuilder().append("IN forwardDisbursal() Insert query### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);
      bufInsSql = null;
      insertPrepStmtObject = null;
      bufInsSql = new StringBuilder();
      insertPrepStmtObject = new PrepStmtObject();

      bufInsSql.append("DELETE from cr_loan_disbursal_dtl_temp");
      bufInsSql.append(new StringBuilder().append(" where LOAN_DISBURSAL_ID in (").append(loanAddIdstr).append(")").toString());
      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN forwardDisbursal() Delete query### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);

      bufInsSql = null;
      insertPrepStmtObject = null;
      bufInsSql = new StringBuilder();
      insertPrepStmtObject = new PrepStmtObject();
      bufInsSql.append(new StringBuilder().append("UPDATE generate_sequence_tbl set VALUE='").append(batchValue).append("' where SEQ_KEY='DISBURSAL_BATCH_ID' ").toString());
      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN forwardDisbursal() Update Sequence query### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      if (status)
      {
        String disbIdQuery = "SELECT MAX(LOAN_DISBURSAL_ID) FROM CR_LOAN_DISBURSAL_DTL";
        String disbId = ConnectionDAO.singleReturn(disbIdQuery);
        bufInsSql = null;
        insertPrepStmtObject = null;
        qryList.clear();
        bufInsSql = new StringBuilder();
        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql.append(new StringBuilder().append("UPDATE CR_RESCH_INSTALLMENT_PLAN SET LOAN_DISBURSAL_ID='").append(disbId).append("' WHERE LOAN_DISBURSAL_ID_TEMP in (").append(loanAddIdstr).append(") AND LOAN_ID='").append(vo.getLbxLoanNoHID()).append("'").toString());
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN forwardDisbursal() Update CR_RESCH_INSTALLMENT_PLAN query### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        bufInsSql = null;
        insertPrepStmtObject = null;
        bufInsSql = new StringBuilder();
        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql.append(new StringBuilder().append("UPDATE CR_LOAN_CHARGE_PLAN_DTL SET LOAN_DISBURSAL_ID='").append(disbId).append("' WHERE LOAN_DISBURSAL_ID_TEMP in (").append(loanAddIdstr).append(") AND LOAN_ID='").append(vo.getLbxLoanNoHID()).append("'").toString());
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN forwardDisbursal() Update CR_LOAN_CHARGE_PLAN_DTL query### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);

        resultStr = "saved";
      } else {
        resultStr = "notsaved";
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return resultStr;
  }
  public String generateRepyScheduleDisbursalPayment(String loanId, String makerId, String finalDisbursalFlag, String txnType) {
    boolean status = false;

    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    String s1 = "";
    String s2 = "";
    String installCount = "";
    String flatRate = "";
    String effRate = "";
    String mktIrr1 = "";
    String mktIrr2 = "";
    String procval = "";
    try
    {
      logger.info("In Generate_Repayment_schedule_Resch Procedure: ");
      in.add(txnType);
      in.add(loanId);
      in.add(finalDisbursalFlag);
      out.add(installCount);
      out.add(flatRate);
      out.add(effRate);
      out.add(mktIrr1);
      out.add(mktIrr2);
      out.add(s1);
      out.add(s2);
      outMessages = (ArrayList)ConnectionDAO.callSP("Generate_Repayment_schedule_Resch", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(5));
      s2 = CommonFunction.checkNull(outMessages.get(6));

      if ((s1 != null) && (s1.equalsIgnoreCase("S")))
      {
        status = true;
        procval = s1;
      } else {
        procval = s2;
      }
      logger.info(new StringBuilder().append("status: ").append(status).toString());
      logger.info(new StringBuilder().append("s2: ").append(s2).append(" s1: ").append(s1).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      s1 = null;
      s2 = null;
      in.clear();
      in = null;
      out.clear();
      out = null;
      outMessages.clear();
      outMessages = null;
      installCount = null;
      flatRate = null;
      effRate = null;
      mktIrr1 = null;
      mktIrr2 = null;
    }

    return procval;
  }

  public String getrevolvingFlag(String loanId)
  {
    String revolvingFlag = "N";
    String query = new StringBuilder().append(" select REVOLVING_FLAG from cr_loan_dtl a join cr_product_m b on(a.LOAN_PRODUCT=b.PRODUCT_ID)  where loan_id='").append(CommonFunction.checkNull(loanId).trim()).append("'").toString();

    revolvingFlag = ConnectionDAO.singleReturn(query);
    if (CommonFunction.checkNull(revolvingFlag).trim().equalsIgnoreCase(""))
      revolvingFlag = "N";
    return revolvingFlag;
  }

  public String getBalancePrinc(String loanId) {
    String balancePrinc = "0";
    String query = new StringBuilder().append(" select LOAN_BALANCE_PRINCIPAL from cr_loan_dtl where loan_id='").append(CommonFunction.checkNull(loanId).trim()).append("'").toString();
    balancePrinc = ConnectionDAO.singleReturn(query);
    if (CommonFunction.checkNull(balancePrinc).trim().equalsIgnoreCase(""))
      balancePrinc = "0.00";
    return balancePrinc;
  }

  public String getForwardedAmt(String loanId)
  {
    String forwardedAmt = "0";
    String query = new StringBuilder().append(" select sum(disbursal_amount)disbursal_amount from cr_loan_disbursal_dtl where rec_status='F' and loan_id='").append(CommonFunction.checkNull(loanId).trim()).append("'").toString();
    forwardedAmt = ConnectionDAO.singleReturn(query);
    if (CommonFunction.checkNull(forwardedAmt).trim().equalsIgnoreCase(""))
      forwardedAmt = "0.00";
    return forwardedAmt;
  }

  public String getAmountInProcessLoan(int lbxLoanNoHID, String userId, String bDate)
  {
    String amountInProcess = "0.00";
    String totalReceiveable = "Select parameter_value from parameter_mst where parameter_key='TOTAL_RECEIVABLE_CURRENT_SHORT_PAY'";
    logger.info(new StringBuilder().append("short Receivable status :----").append(totalReceiveable).toString());
    String flagVal = CommonFunction.checkNull(ConnectionDAO.singleReturn(totalReceiveable));
    if (CommonFunction.checkNull(flagVal).equalsIgnoreCase("Y"))
    {
      StringBuilder amountInProcessQuery = new StringBuilder();
      amountInProcessQuery.append("SELECT IFNULL(sum(AMOUNT_IN_PROCESS),0) totalBalance FROM cr_txnadvice_dtl ");
      amountInProcessQuery.append(new StringBuilder().append(" WHERE ADVICE_TYPE='R' AND LOAN_ID='").append(lbxLoanNoHID).append("' AND BP_TYPE ='CS' ").toString());
      amountInProcessQuery.append("  ");
      logger.info(new StringBuilder().append("amountInProcessQuery: ").append(amountInProcessQuery.toString()).toString());
      amountInProcess = ConnectionDAO.singleReturn(amountInProcessQuery.toString());
    }
    if (CommonFunction.checkNull(amountInProcess).trim().equalsIgnoreCase("")) {
      amountInProcess = "0.00";
    }
    return amountInProcess;
  }

  public String deleteNewAddDetails(DisbursalMakerVO vo, String checkedStr)
  {
    String resultStr = "";

    ArrayList qryList = new ArrayList();
    StringBuilder query = new StringBuilder();
    PrepStmtObject deletePrepStmtObject = new PrepStmtObject();
    try {
      String[] addId = vo.getAddId();
      logger.info(new StringBuilder().append("addId:----------").append(addId).toString());
      String addIdStr = "";

      if (!CommonFunction.checkNull(checkedStr).equalsIgnoreCase("")) {
        addIdStr = checkedStr.substring(0, checkedStr.length() - 1);
        logger.info(new StringBuilder().append("addIdStr:----------").append(addIdStr).toString());
        String querySel = new StringBuilder().append("Select count(1) from cr_resch_installment_plan where  LOAN_DISBURSAL_ID in (").append(addIdStr).append(")").toString();
        String deleteFlag = ConnectionDAO.singleReturn(querySel);
        if (!CommonFunction.checkNull(deleteFlag).equalsIgnoreCase("0")) {
          query.append(new StringBuilder().append(" Delete  from cr_resch_installment_plan where  LOAN_DISBURSAL_ID in (").append(addIdStr).append(")").toString());
          logger.info(new StringBuilder().append("Delete query : ").append(query.toString()).toString());
          deletePrepStmtObject.setSql(query.toString());
          qryList.add(deletePrepStmtObject);
          boolean status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
          logger.info(new StringBuilder().append("status:----------").append(status).toString());
          if (status)
            resultStr = "deleted";
          else
            resultStr = "notdeleted";
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return resultStr;
  }

  public String getRecoveryType(String loanid)
  {
    String recoveryTypeQuery = null;
    String recoveryType = null;
    try {
      recoveryTypeQuery = new StringBuilder().append(" select RECOVERY_TYPE from cr_installment_plan WHERE LOAN_ID='").append(loanid).append("'").toString();
      logger.info(new StringBuilder().append(" query String of getRecoveryType:----").append(recoveryTypeQuery).toString());
      recoveryType = ConnectionDAO.singleReturn(recoveryTypeQuery);
      logger.info(new StringBuilder().append("Recovery Type : ").append(recoveryType).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      recoveryTypeQuery = null;
    }
    return recoveryType;
  }
  public String getDisbursalFlag(String loanid, String txnType) {
    String disbursalFlagQuery = null;
    String disbursalFlag = null;
    String table = null;
    try {
      if (CommonFunction.checkNull(txnType).equalsIgnoreCase("DIM"))
        table = "CR_LOAN_DISBURSAL_DTL_TEMP";
      else
        table = "CR_LOAN_DISBURSAL_DTL";
      disbursalFlagQuery = new StringBuilder().append("SELECT DISBURSAL_FLAG FROM ").append(table).append(" WHERE DISBURSAL_FLAG='F' AND LOAN_ID='").append(loanid).append("'").toString();
      logger.info(new StringBuilder().append(" query String of getDisbursalFlag:----").append(disbursalFlagQuery).toString());
      disbursalFlag = ConnectionDAO.singleReturn(disbursalFlagQuery);
      if (CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
        disbursalFlag = "P";
      logger.info(new StringBuilder().append("Recovery Type : ").append(disbursalFlag).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      disbursalFlagQuery = null;
      table = null;
    }
    return disbursalFlag;
  }
  public String getRepaymentType(String loanid) {
    String repayTypeQuery = null;
    String repayType = null;
    try {
      repayTypeQuery = new StringBuilder().append("SELECT LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='").append(loanid).append("'").toString();
      logger.info(new StringBuilder().append(" query String of getDisbursalFlag:----").append(repayTypeQuery).toString());
      repayType = ConnectionDAO.singleReturn(repayTypeQuery);
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      repayTypeQuery = null;
    }
    return repayType;
  }

  public ArrayList getFromLoanDtlDisbursalPayment(String loanId)
  {
    ArrayList list = new ArrayList();

    logger.info("In getFromLoanDtlDisbursalPayment: ");

    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    StringBuilder query = new StringBuilder();
    StringBuilder bussIrrQ = new StringBuilder();
    RepayScheduleVo repvo = null;
    try
    {
      query.append("select LOAN_RATE_METHOD,LOAN_FLAT_RATE,LOAN_EFF_RATE,LOAN_IRR1,LOAN_IRR2,UPFRONT_ROUNDING_AMOUNT ");
      query.append(new StringBuilder().append(" from cr_loan_dtl where LOAN_ID=").append(loanId).append("").toString());
      logger.info(new StringBuilder().append("Query in getFromLoanDtl-----").append(query.toString()).toString());
      bussIrrQ.append(new StringBuilder().append("select DEAL_BUSINESS_IRR from cr_deal_loan_dtl where DEAL_LOAN_ID=(select LOAN_DEAL_LOAN_ID from cr_loan_dtl where LOAN_ID=").append(loanId).append(")").toString());
      logger.info(new StringBuilder().append("Query in getFromLoanDtlDisbursalPayment--DEAL_BUSINESS_IRR---").append(bussIrrQ.toString()).toString());
      String bussIrr = ConnectionDAO.singleReturn(bussIrrQ.toString());
      logger.info(new StringBuilder().append("Query in getFromLoanDtlDisbursalPayment--bussIrr---").append(bussIrr).toString());
      mainlist = ConnectionDAO.sqlSelect(query.toString());
      int size = mainlist.size();
      for (int i = 0; i < size; i++) {
        subList = (ArrayList)mainlist.get(i);
        repvo = new RepayScheduleVo();
        if (subList.size() > 0)
        {
          repvo.setLoanRateMethod(CommonFunction.checkNull(subList.get(0)).trim());
          if (!CommonFunction.checkNull(subList.get(1)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(1)).trim());
            repvo.setFinalRate(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
            repvo.setEffectiveRate(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
            repvo.setMktIRR1(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
            repvo.setMktIRR2(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
          {
            Number UPFRONT_ROUNDING_AMOUNT = this.myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
            repvo.setUpfrontRoundingAmount(this.myFormatter.format(UPFRONT_ROUNDING_AMOUNT));
          }
          else {
            repvo.setUpfrontRoundingAmount("0.00");
          }

          if (CommonFunction.checkNull(subList.get(0)).trim().equalsIgnoreCase("E"))
          {
            repvo.setFinalRate("");
          }
          repvo.setBussIrr(bussIrr);
        }

        list.add(repvo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      mainlist = null;
      subList = null;
      query = null;
      bussIrrQ = null;
      repvo = null;
    }
    return list;
  }

  public ArrayList getRepaySchedDisbursalPayment(String loanId, String makerId)
  {
    ArrayList list = new ArrayList();
    logger.info("In getRepaySchedDisbursalPayment: ");
    ArrayList mainlist = new ArrayList();
    ArrayList subList = new ArrayList();
    StringBuilder query = new StringBuilder();
    RepayScheduleVo repvo = null;
    try
    {
      query.append(new StringBuilder().append("select R_Seq_No,DATE_FORMAT(R_Due_Date,'").append(this.dateFormat).append("'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp,").append(" if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS,R_OTHER_CHARGES from Repay_Temp where R_LOAN_ID=").append(loanId).append(" order by R_DUE_DATE,R_ORG_SEQ_NO").toString());

      logger.info(new StringBuilder().append("Query in getRepaySchedDisbursalPayment-----").append(query.toString()).toString());
      mainlist = ConnectionDAO.sqlSelect(query.toString());
      int size = mainlist.size();
      for (int i = 0; i < size; i++) {
        subList = (ArrayList)mainlist.get(i);
        repvo = new RepayScheduleVo();
        if (subList.size() > 0) {
          repvo.setInstNo(CommonFunction.checkNull(subList.get(0)).trim());
          repvo.setDueDate(CommonFunction.checkNull(subList.get(1)).trim());

          if (!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
            repvo.setInstAmount(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
            repvo.setPrinciple(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
            repvo.setInstCom(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
            repvo.setExcess(this.myFormatter.format(reconNum));
          }

          repvo.setAdvFlag(CommonFunction.checkNull(subList.get(6)).trim());
          if (!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(7)).trim());
            repvo.setPrinOS(this.myFormatter.format(reconNum));
          }

          if (!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(8)).trim());
            repvo.setOtherCharges(this.myFormatter.format(reconNum));
          }
          else
          {
            repvo.setOtherCharges("0.00");
          }
        }
        list.add(repvo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      mainlist = null;
      subList = null;
      query = null;
      repvo = null;
    }
    return list;
  }

  public boolean saveOtherChargesPlanAtDisbursal(OtherChargesPlanVo ipvo) {
    logger.info("In saveOtherChargesPlanAtDisbursal");
    String[] FromInstallment = ipvo.getFromInstall();

    String[] ToInstallment = ipvo.getToInstall();

    String[] type = ipvo.getType();

    String InstallmentType = ipvo.getInstallmentType();

    String TotalInstallment = ipvo.getTotalInstallment();

    String toInstallment = ipvo.getToInstallment();

    String[] amount = ipvo.getAmount();

    String[] chargeCode = ipvo.getChargehiddenFld();
    String loanId = ipvo.getLoanId();

    ArrayList qryList = new ArrayList();
    boolean status = false;
    try
    {
      PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
      StringBuffer bufInsSql = new StringBuffer();
      StringBuilder checkQ = new StringBuilder();
      checkQ.append(new StringBuilder().append("select count(*) from cr_loan_charge_plan_dtl where LOAN_ID=").append(CommonFunction.checkNull(ipvo.getLoanId()).trim()).append(" AND IFNULL(LOAN_DISBURSAL_ID_TEMP,'')<>''").toString());
      String count = ConnectionDAO.singleReturn(checkQ.toString());

      checkQ = null;

      if (Integer.parseInt(count) > 0)
      {
        StringBuilder qry = new StringBuilder();
        qry.append(new StringBuilder().append("DELETE FROM cr_loan_charge_plan_dtl WHERE LOAN_ID='").append(CommonFunction.checkNull(ipvo.getLoanId()).trim()).append("' AND IFNULL(LOAN_DISBURSAL_ID_TEMP,'')<>''").toString());
        logger.info(new StringBuilder().append("DELETE query from cr_loan_charge_plan_dtl : ").append(qry.toString()).toString());
        insertPrepStmtObject.setSql(qry.toString());
        qryList.add(insertPrepStmtObject);

        qry = null;
      }

      for (int k = 0; k < FromInstallment.length; k++)
      {
        bufInsSql = new StringBuffer();
        insertPrepStmtObject = new PrepStmtObject();

        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql.append(new StringBuilder().append("insert into cr_loan_charge_plan_dtl (LOAN_ID,LOAN_DISBURSAL_ID_TEMP,FROM_INSTL_NO,TO_INSTL_NO,CHARGE_TYPE,AMOUNT,CHARGE_CODE,REC_STATUS,MAKER_ID,MAKER_DATE) values(?,?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND))").toString());

        if (CommonFunction.checkNull(ipvo.getLoanId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(ipvo.getLoanId().trim());
        }
        if (CommonFunction.checkNull(ipvo.getDisbursalId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(ipvo.getDisbursalId().trim());
        }
        if (CommonFunction.checkNull(FromInstallment[k]).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(FromInstallment[k].trim());
        }
        if (CommonFunction.checkNull(ToInstallment[k]).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(ToInstallment[k].trim());
        }

        insertPrepStmtObject.addString("F");

        if (CommonFunction.checkNull(amount[k]).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addString("0.00");
        else {
          insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(amount[k]).trim()).toString());
        }
        if (CommonFunction.checkNull(chargeCode[k]).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(chargeCode[k].trim());
        }
        insertPrepStmtObject.addString("P");

        if (CommonFunction.checkNull(ipvo.getMakerId()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(ipvo.getMakerId()).trim());
        }
        if (CommonFunction.checkNull(ipvo.getMakerDate()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(ipvo.getMakerDate()).trim());
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveOtherChargesPlanAtDisbursal() LOAN insert query1 ### DAO IMPL ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        bufInsSql = null;
      }

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
    } catch (Exception e) {
      e.printStackTrace();
    }

    logger.info(new StringBuilder().append("In saveOtherChargesPlanAtDisbursal......................").append(status).toString());
    return status;
  }

  public ArrayList getOtherPeriodicalChargeDetailAtDisbursal(String id)
  {
    ArrayList list = new ArrayList();
    ArrayList mainList = new ArrayList();
    ArrayList subList = new ArrayList();
    String count = null;
    StringBuilder chkQuery = new StringBuilder();
    try {
      StringBuilder query = new StringBuilder();
      chkQuery.append(new StringBuilder().append("SELECT COUNT(LOAN_ID) FROM CR_LOAN_CHARGE_PLAN_DTL WHERE LOAN_ID=").append(id).append(" AND IFNULL(LOAN_DISBURSAL_ID_TEMP,'')<>''").toString());
      count = ConnectionDAO.singleReturn(chkQuery.toString());
      chkQuery = null;
      if (Integer.parseInt(count) > 0)
      {
        query.append(new StringBuilder().append("select distinct D.FROM_INSTL_NO,D.TO_INSTL_NO,D.CHARGE_TYPE,D.AMOUNT ,C.CHARGE_CODE,C.CHARGE_DESC,L.LOAN_INSTALLMENT_TYPE,L.LOAN_NO_OF_INSTALLMENT,L.LOAN_RATE_TYPE,L.LOAN_LOAN_AMOUNT     from cr_loan_dtl L left JOIN cr_loan_charge_plan_dtl D on D.LOAN_ID=L.LOAN_ID ,com_charge_code_m C  where L.LOAN_ID=").append(id).append(" AND C.CHARGE_CODE=D.CHARGE_CODE AND IFNULL(LOAN_DISBURSAL_ID_TEMP,'')<>''").toString());
      }
      else
      {
        query.append(new StringBuilder().append("select distinct D.FROM_INSTL_NO,D.TO_INSTL_NO,D.CHARGE_TYPE,D.AMOUNT ,C.CHARGE_CODE,C.CHARGE_DESC,L.LOAN_INSTALLMENT_TYPE,L.LOAN_NO_OF_INSTALLMENT,L.LOAN_RATE_TYPE,L.LOAN_LOAN_AMOUNT     from cr_loan_dtl L left JOIN cr_loan_charge_plan_dtl D on D.LOAN_ID=L.LOAN_ID ,com_charge_code_m C  where L.LOAN_ID=").append(id).append(" AND C.CHARGE_CODE=D.CHARGE_CODE").toString());
      }

      logger.info(new StringBuilder().append("getOtherPeriodicalChargeDetailAtDisbursal Queryl: ").append(query).toString());

      mainList = ConnectionDAO.sqlSelect(query.toString());

      query = null;

      for (int i = 0; i < mainList.size(); i++)
      {
        subList = (ArrayList)mainList.get(i);
        if (subList.size() > 0) {
          OtherChargesPlanVo ipVo = null;
          ipVo = new OtherChargesPlanVo();

          ipVo.setFromInstallment(CommonFunction.checkNull(subList.get(0)).trim());
          ipVo.setToInstallment(CommonFunction.checkNull(subList.get(1)).trim());

          ipVo.setChargeType("FLAT");
          if (!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase(""))
          {
            Number reconNum = Double.valueOf(0.0D);
            try {
              reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
            }
            catch (ParseException e) {
              e.printStackTrace();
            }
            ipVo.setChargeAmount(this.myFormatter.format(reconNum));
          }
          else
          {
            ipVo.setChargeAmount("0.00");
          }

          ipVo.setChargeCode(CommonFunction.checkNull(subList.get(4)).trim());
          ipVo.setChargeDesc(CommonFunction.checkNull(subList.get(5)).trim());

          ipVo.setInstallmentType(CommonFunction.checkNull(subList.get(6)).trim());
          ipVo.setTotalInstallment(CommonFunction.checkNull(subList.get(7)).trim());

          ipVo.setRateType(CommonFunction.checkNull(subList.get(8)).trim());

          if (!CommonFunction.checkNull(subList.get(9)).equalsIgnoreCase(""))
          {
            Number instam = this.myFormatter.parse(CommonFunction.checkNull(subList.get(9)).trim());

            logger.info(new StringBuilder().append("setLoanAmount: ").append(instam).toString());
            ipVo.setLoanAmount(this.myFormatter.format(instam));
          }
          else
          {
            ipVo.setLoanAmount("0.00");
          }

          list.add(ipVo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public String getdisbrsalAmt(String loanId) {
    String disbrsalAmt = "0";
    String query = new StringBuilder().append(" select sum(disbursal_amount)disbursal_amount from cr_loan_disbursal_dtl_temp where loan_id='").append(CommonFunction.checkNull(loanId).trim()).append("'").toString();
    disbrsalAmt = ConnectionDAO.singleReturn(query);
    if (CommonFunction.checkNull(disbrsalAmt).trim().equalsIgnoreCase(""))
      disbrsalAmt = "0.00";
    query = null;
    return disbrsalAmt;
  }
  public String newInstallmentPlanStatus(DisbursalMakerVO vo) {
    String newInstPlanQuery = null;
    String newInstPlan = null;
    String result = null;
    try {
      newInstPlanQuery = new StringBuilder().append("SELECT INSTALLMENT_PLAN_ID FROM CR_RESCH_INSTALLMENT_PLAN WHERE LOAN_ID='").append(vo.getLbxLoanNoHID()).append("'").toString();
      logger.info(new StringBuilder().append(" query String of getRecoveryType:----").append(newInstPlanQuery).toString());
      newInstPlan = ConnectionDAO.singleReturn(newInstPlanQuery);
      if (CommonFunction.checkNull(newInstPlan).equalsIgnoreCase(""))
        result = "Y";
      else
        result = "N";
      logger.info(new StringBuilder().append("newInstPlan result : ").append(result).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      newInstPlanQuery = null;
      newInstPlan = null;
    }
    return result;
  }

  public boolean paymentAmountAtDisbursal(DisbursalMakerVO vo) {
    boolean status = false;
    String paymentAmount = null;
    String TotalPaidAmount = null;
    String cashPaymentLimit = null;
    try
    {
      paymentAmount = CommonFunction.checkNull(vo.getPaymentAmount()).replace(",", "");
      TotalPaidAmount = ConnectionDAO.singleReturn(new StringBuilder().append("SELECT IFNULL(SUM(PMNT_AMOUNT),0) AS PAYMENT_AMOUNT FROM CR_LOAN_DISBURSAL_DTL_TEMP WHERE PMNT_MODE='C' AND LOAN_ID='").append(CommonFunction.checkNull(vo.getLbxLoanNoHID())).append("'").toString());
      cashPaymentLimit = ConnectionDAO.singleReturn("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CASH_PAYMENT_LIMIT'");
      if (paymentAmount.equalsIgnoreCase(""))
        paymentAmount = "0.00";
      BigDecimal pa = new BigDecimal(paymentAmount);
      BigDecimal tpa = new BigDecimal(TotalPaidAmount);
      BigDecimal cpl = new BigDecimal(cashPaymentLimit);
      BigDecimal total = pa.add(tpa);

      int res = total.compareTo(cpl);
      if (res == 1)
        status = false;
      else
        status = true;
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      paymentAmount = null;
      TotalPaidAmount = null;
      cashPaymentLimit = null;
    }
    return status;
  }

  public String getInstallmentType(String loanid) {
    String instlTypeQuery = null;
    String instlType = null;
    try {
      instlTypeQuery = new StringBuilder().append("SELECT LOAN_INSTALLMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='").append(loanid).append("'").toString();
      logger.info(new StringBuilder().append(" query String of getInstallmentType:----").append(instlTypeQuery).toString());
      instlType = ConnectionDAO.singleReturn(instlTypeQuery);
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      instlTypeQuery = null;
    }
    return instlType;
  }

  public String validationOnLeadPartner(String loanId) {
    StringBuilder queryResult = new StringBuilder();
    StringBuilder queryResult1 = new StringBuilder();
    StringBuilder queryResult2 = new StringBuilder();
    String finalResult = "ALLRIGHT";
    int result = 0;
    try {
      queryResult.append(new StringBuilder().append(" SELECT COUNT(1) FROM cr_business_partner_dtl WHERE LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())).append("  AND IFNULL(LEAD_PARTNER_FLAG,'N')='Y' AND IFNULL(SELF_FLAG,'N') not in ('N','Y')").toString());
      result = Integer.parseInt(ConnectionDAO.singleReturn(queryResult.toString()));
      if (result > 0) {
        queryResult2.append(" select COUNT(1) from consortium_repayment_dtl a ");
        queryResult2.append(" join cr_business_partner_dtl b on a.loan_id=b.LOAN_ID and ifnull(b.LEAD_PARTNER_FLAG,'N')='Y' AND A.PARTNER_ID=B.partner_ID ");
        queryResult2.append(new StringBuilder().append(" where a.loan_id=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())).append(" and A.REFPAY_EFF_RATE IS NOT NULL ").toString());
        result = Integer.parseInt(ConnectionDAO.singleReturn(queryResult2.toString()));
        if (result == 0) {
          finalResult = "CAPTURELEAD";
        }
        else {
          finalResult = "ALLRIGHT";
        }

        if (CommonFunction.checkNull(finalResult).trim().equalsIgnoreCase("ALLRIGHT"))
        {
          queryResult1.append(new StringBuilder().append(" SELECT COUNT(1) FROM cr_business_partner_dtl A WHERE IFNULL(A.LEAD_PARTNER_FLAG,'N')='Y' AND A.LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())).append(" AND IFNULL(ROUND(A.LOAN_AMOUNT,2),0)= ").toString());
          queryResult1.append(new StringBuilder().append(" (SELECT IFNULL(ROUND(SUM(B.PRIN_COMP),2),0) FROM consortium_repayment_dtl B WHERE B.LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())).append(" AND B.PARTNER_ID=A.PARTNER_ID AND REFPAY_EFF_RATE IS NOT NULL )  ").toString());
          result = Integer.parseInt(ConnectionDAO.singleReturn(queryResult1.toString()));
          if (result == 0) {
            finalResult = "AMTNOTMATCH";
          }
          else
            finalResult = "ALLRIGHT";
        }
      }
      else
      {
        finalResult = "ALLRIGHT";
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      e = null;
    }
    finally
    {
      loanId = null;
      queryResult = null;
      queryResult1 = null;
    }
    return finalResult;
  }

  public ArrayList selectOtherLoanDetails(int lbxLoanNoHID) {
    return null;
  }

  public String getDateForDisbursalCheck(DisbursalMakerVO vo) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date disbursalDate = null;
    try {
      disbursalDate = dateFormat.parse(vo.getDisbursalDate());
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    String dDate = dateFormat1.format(disbursalDate);
    logger.info(new StringBuilder().append("dDate-----------").append(dDate).append("---------disbursalDate------------").append(disbursalDate).toString());
    String returnString = "F";
    logger.info("in getDateForDisbursalCheck()------------------ ");
    String query = new StringBuilder().append("select count(1) from cr_loan_disbursal_dtl where loan_id ='").append(vo.getLbxLoanNoHID()).append("' and disbursal_flag = 'P' and DISBURSAL_DATE > '").append(dDate).append("'").toString();

    logger.info(new StringBuilder().append("DisDate ----------").append(query).toString());
    String disDate = ConnectionDAO.singleReturn(query);

    String query1 = new StringBuilder().append("select count(1) from cr_resch_dtl where loan_id ='").append(vo.getLbxLoanNoHID()).append("' and resch_type = 'P' and resch_date > '").append(dDate).append("'").toString();

    logger.info(new StringBuilder().append("REsDATE for -----").append(query1).toString());
    String resDate = ConnectionDAO.singleReturn(query1);

    if ((resDate.equalsIgnoreCase("0")) && (disDate.equalsIgnoreCase("0")))
      returnString = "T";
    return returnString;
  }

  public String getRecStatusForPartPayment(int lbxLoanNoHID, String userId, String bDate)
  {
    logger.info("---------------In getRecTypeForPartPayment()-----------");
    String query = new StringBuilder().append("select count(1) from cr_resch_dtl where loan_id = '").append(lbxLoanNoHID).append("' and rec_status in( 'F','P')").toString();
    String str = "";

    str = ConnectionDAO.singleReturn(query);

    return str;
  }

  public String getEditDueDateStatus(String lbxLoanNoHID)
  {
    logger.info("---------------In getEditDueDateStatus()-----------");
    String query = new StringBuilder().append("select edit_Due_Date from cr_loan_dtl where loan_id = '").append(lbxLoanNoHID).append("'").toString();
    String str = "";

    str = ConnectionDAO.singleReturn(query);

    return str;
  }

  public String getRepyEffDateOfLoan(String lbxLoanNoHID)
  {
    logger.info("---------------In getEditDueDateStatus()-----------");
    String query = new StringBuilder().append("select DATE_FORMAT(OLD_REPAY_EFF_DATE,'").append(this.dateFormat).append("') from cr_loan_disbursal_dtl_temp where loan_id = '").append(lbxLoanNoHID).append("'").toString();
    logger.info(new StringBuilder().append("String Query Rahul: ").append(query).toString());
    String str = "";

    str = ConnectionDAO.singleReturn(query);

    return str;
  }

  public String getRepayEffDate(String lbxLoanNoHID)
  {
    logger.info("---------------In getEditDueDateStatus()-----------");
    String query = new StringBuilder().append("select DATE_FORMAT(REPAY_EFF_DATE,'").append(this.dateFormat).append("') from cr_loan_disbursal_dtl_temp where loan_id = '").append(lbxLoanNoHID).append("'").toString();
    logger.info(new StringBuilder().append("String Query Rahul: ").append(query).toString());
    String str = "";

    str = ConnectionDAO.singleReturn(query);

    return str;
  }

  public String getLoanTenure(String lbxLoanNoHID)
  {
    logger.info("---------------In getLoanTenure()-----------");
    String query = new StringBuilder().append("select loan_tenure from cr_loan_dtl where loan_id='").append(lbxLoanNoHID).append("'").toString();
    logger.info(new StringBuilder().append("String Query Loan Tenure: ").append(query).toString());
    String str = "";

    str = ConnectionDAO.singleReturn(query);

    return str;
  }
}