package com.cm.daoImplMYSQL;

import com.cm.dao.PaymentDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class PaymentDAOImpl
  implements PaymentDAO
{
  private static final Logger logger = Logger.getLogger(PaymentDAOImpl.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
  String dateFormatWithTime = this.resource.getString("lbl.dateWithTimeInDao");
  String dateFormat = this.resource.getString("lbl.dateInDao");
  String dateForDisbursal = this.resource.getString("lbl.dateForDisbursal");
  DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
  int no = Integer.parseInt(this.resource.getString("msg.pageSizeForMaster"));

  public Map paymentMakerGrid(PaymentMakerForCMVO vo)
  {
    StringBuilder loanNo = new StringBuilder();
    StringBuilder customerName = new StringBuilder();
    StringBuilder businessPartnerType = new StringBuilder();
    StringBuilder businessPartnerID = new StringBuilder();
    StringBuilder paymentAmount = new StringBuilder();
    StringBuilder instrumentNumber = new StringBuilder();
    StringBuilder instrumentId = new StringBuilder();
    StringBuilder paymentMode = new StringBuilder();
    HashMap map = new HashMap();

    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;

    ArrayList detailListGrid = new ArrayList();
    logger.info("In paymentMakerGrid().....userid....  " + vo.getReportingToUserId());
    try
    {
      ArrayList searchlist = new ArrayList();
      boolean appendSQL = false;
      StringBuffer bufInsSql = new StringBuffer();
      StringBuffer bufInsSqlTempCount = new StringBuffer();

      loanNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));

      logger.info("In paymentMakerGrid......loanNo  " + loanNo);

      customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
      businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
      businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));

      logger.info("businessPartnerType .. " + businessPartnerType.toString());
      logger.info("businessPartnerID... " + businessPartnerID.toString());

      if (!CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
      {
        paymentAmount.append(this.myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPaymentAmount()).trim())).toString());
        logger.info("In paymentMakerGrid......paymentAmount  " + paymentAmount);
      }
      instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
      instrumentId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim()));
      logger.info("In paymentMakerGrid......instrumentId  " + instrumentId);
      paymentMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPaymentMode()).trim()));
      logger.info("In paymentMakerGrid......paymentMode  " + paymentMode);

      bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE,    GM.DESCRIPTION,CID.BPID ,    BPV.BP_NAME ,  CID.INSTRUMENT_AMOUNT,CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE, CID.INSTRUMENT_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID= CID.MAKER_ID) MAKER_ID    from gcd_customer_m GCM ,cr_loan_dtl CLD, cr_instrument_dtl CID, business_partner_view BPV, generic_master  GM     WHERE CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID  AND BPV.BP_ID=CID.BPID AND  BPV.LOAN_ID=CID.TXNID and  BPV.BP_TYPE=cid.BPTYPE and CLD.LOAN_ID=CID.TXNID    AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='P' and INSTRUMENT_TYPE='P' and ifnull(CID.TXN_TYPE,'')='LIM' AND CID.MAKER_ID='" + 
        vo.getReportingToUserId() + "'  AND CLD.REC_STATUS NOT IN ('X','L') ");

      bufInsSqlTempCount.append(" SELECT COUNT(DISTINCT CLD.LOAN_ID) ");
      bufInsSqlTempCount.append(" FROM CR_LOAN_DTL CLD ");
      bufInsSqlTempCount.append(" JOIN CR_INSTRUMENT_DTL CID ON(ifnull(CID.TXN_TYPE,'')='LIM' AND CLD.LOAN_ID=CID.TXNID  AND INSTRUMENT_TYPE='P' AND CID.REC_STATUS='P' AND CID.MAKER_ID='" + vo.getReportingToUserId() + "' ) ");
      if (!customerName.toString().trim().equalsIgnoreCase("")) {
        bufInsSqlTempCount.append(" JOIN GCD_CUSTOMER_M GCM ON(CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID )  ");
      }
      bufInsSqlTempCount.append(" WHERE CLD.REC_STATUS NOT IN ('X','L') ");

      if (!loanNo.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append(" AND CLD.LOAN_ID='" + loanNo + "'");
        bufInsSqlTempCount.append("  AND CLD.LOAN_ID='" + loanNo + "' ");
        appendSQL = true;
      }

      if (!customerName.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("  AND GCM.CUSTOMER_NAME LIKE '%" + customerName + "%' ");
        bufInsSqlTempCount.append("  AND GCM.CUSTOMER_NAME LIKE '%" + customerName + "%' ");
        appendSQL = true;
      }

      if (!businessPartnerType.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("  AND CID.BPTYPE='" + businessPartnerType + "' ");
        bufInsSqlTempCount.append("  AND CID.BPTYPE='" + businessPartnerType + "' ");
        appendSQL = true;
      }

      if (!businessPartnerID.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("  AND CID.BPID='" + businessPartnerID + "' ");
        bufInsSqlTempCount.append("  AND CID.BPID='" + businessPartnerID + "' ");
        appendSQL = true;
      }

      if (!paymentAmount.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("  AND CID.INSTRUMENT_AMOUNT='" + paymentAmount + "' ");
        bufInsSqlTempCount.append("  AND CID.INSTRUMENT_AMOUNT='" + paymentAmount + "' ");
        appendSQL = true;
      }

      if (!instrumentNumber.toString().trim().equalsIgnoreCase(""))
      {
        bufInsSql.append("  AND CID.INSTRUMENT_NO='" + instrumentNumber + "' ");
        bufInsSqlTempCount.append("  AND CID.INSTRUMENT_NO='" + instrumentNumber + "' ");
        appendSQL = true;
      }

      if (!paymentMode.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("  AND CID.INSTRUMENT_MODE='" + paymentMode + "'");
        bufInsSqlTempCount.append("  AND CID.INSTRUMENT_MODE='" + paymentMode + "'");
        appendSQL = true;
      }

      logger.info("In paymentMakerGrid() COUNT Query.... " + bufInsSqlTempCount.toString());

      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
      bufInsSqlTempCount = null;

      logger.info("current PAge Link no .................... " + vo.getCurrentPageLink());
      if (vo.getCurrentPageLink() > 1)
      {
        startRecordIndex = (vo.getCurrentPageLink() - 1) * this.no;
        endRecordIndex = this.no;
        logger.info("startRecordIndex .................... " + startRecordIndex);
        logger.info("endRecordIndex .................... " + endRecordIndex);
      }

      bufInsSql.append(" limit " + startRecordIndex + "," + endRecordIndex);

      logger.info("In paymentMakerGrid() MAIN Query.... " + bufInsSql.toString());

      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

      bufInsSql = null;

      for (int i = 0; i < searchlist.size(); i++)
      {
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();

          paymentVO.setModifyNo("<a href=paymentCMProcessAction.do?method=openEditpaymentMaker&lbxLoanNoHID=" + 
            CommonFunction.checkNull(data.get(0)).toString() + 
            "&instrumentID=" + CommonFunction.checkNull(data.get(10)).toString() + ">" + 
            CommonFunction.checkNull(data.get(1)).toString() + "</a>");

          paymentVO.setLbxLoanNoHID(CommonFunction.checkNull(data.get(0)).trim());
          paymentVO.setLoanAccountNumber(CommonFunction.checkNull(data.get(1)).trim());
          paymentVO.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          paymentVO.setLbxBPType(CommonFunction.checkNull(data.get(3)).trim());
          paymentVO.setBusinessPartnerType(CommonFunction.checkNull(data.get(4)).trim());
          paymentVO.setLbxBPNID(CommonFunction.checkNull(data.get(5)).trim());
          paymentVO.setBusinessPartnerName(CommonFunction.checkNull(data.get(6)).trim());
          Number PaymentAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("")) {
            PaymentAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(7)).trim());
          }

          paymentVO.setPaymentAmount(PaymentAmount.toString());

          paymentVO.setInstrumentNumber(CommonFunction.checkNull(data.get(8)).trim());
          paymentVO.setPaymentMode(CommonFunction.checkNull(data.get(9)).trim());
          if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("C")) {
            paymentVO.setPaymentMode("Cash");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("Q")) {
            paymentVO.setPaymentMode("Cheque");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("D")) {
            paymentVO.setPaymentMode("DD");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("N")) {
            paymentVO.setPaymentMode("NEFT");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("R")) {
            paymentVO.setPaymentMode("RTGS");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("S")) {
            paymentVO.setPaymentMode("Adjustment");
          }
          paymentVO.setInstrumentID(CommonFunction.checkNull(data.get(10)).trim());

          paymentVO.setTotalRecordSize(count);
          paymentVO.setReportingToUserId(CommonFunction.checkNull(data.get(11)).trim());
          detailListGrid.add(paymentVO);
          paymentVO = null;
        }
        data = null;
      }

      map.put("searchlist", searchlist);
      map.put("detailListGrid", detailListGrid);

      searchlist = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      loanNo = null;
      customerName = null;
      businessPartnerType = null;
      businessPartnerID = null;
      paymentAmount = null;
      instrumentNumber = null;
      instrumentId = null;
      paymentMode = null;
    }
    return map;
  }

  public ArrayList<PaymentMakerForCMVO> onAllocatedPayable(PaymentMakerForCMVO paymentMakerForCMVO, int loanId, String bpType, int instrumentId)
  {
    ArrayList allocatedList = new ArrayList();
    StringBuilder query = new StringBuilder();
    try
    {
      ArrayList mainList = new ArrayList();
      ArrayList subList = new ArrayList();

      logger.info(" In onAllocatedPayable....");

      query.append("SELECT  distinct DATE_FORMAT(ADVICE_DATE,'" + this.dateFormat + "'), " + 
        "(Select CHARGE_DESC From com_charge_code_m Where CHARGE_CODE=CTD.CHARGE_CODE_ID) CHARGE, ADVICE_AMOUNT,  " + 
        " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,  " + 
        " AMOUNT_IN_PROCESS, CTD.TDS_AMOUNT, PMNT_AMOUNT,CPD.TDS_AMOUNT AS TDS_ALLOCATED_AMOUNT " + 
        " from cr_txnadvice_dtl CTD,cr_pmnt_dtl CPD  where CPD.TXNADVICEID=CTD.TXNADVICE_ID " + 
        " AND ADVICE_TYPE='P' " + 
        "  AND LOAN_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(loanId)).trim()) + "'" + 
        " AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "' " + 
        " AND CPD.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(instrumentId)).trim()) + "' " + 
        " Order By CTD.ADVICE_DATE ASC ");

      logger.info("In onAllocatedPayable" + query);

      mainList = ConnectionDAO.sqlSelect(query.toString());
      for (int i = 0; i < mainList.size(); i++)
      {
        subList = (ArrayList)mainList.get(i);
        if (subList.size() > 0)
        {
          PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
          paymentVO.setPaymentDate(CommonFunction.checkNull(subList.get(0)).trim());
          logger.info("setPaymentDate: " + subList.get(0));
          paymentVO.setChargeDesc(CommonFunction.checkNull(subList.get(1)).trim());
          Number OriginalAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase(""))
          {
            OriginalAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
          }
          logger.info("OriginalAmount: " + OriginalAmount);
          paymentVO.setOriginalAmount(this.myFormatter.format(OriginalAmount));
          Number BalanceAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase(""))
          {
            BalanceAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
          }
          logger.info("BalanceAmount: " + BalanceAmount);
          paymentVO.setBalanceAmount(this.myFormatter.format(BalanceAmount));
          Number AmountInProcess = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase(""))
          {
            AmountInProcess = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
          }
          logger.info("AmountInProcess: " + AmountInProcess);
          paymentVO.setAmountInProcess(this.myFormatter.format(AmountInProcess));
          Number TdsadviseAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase(""))
          {
            TdsadviseAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
          }
          logger.info("TdsadviseAmount: " + TdsadviseAmount);
          paymentVO.setTdsadviseAmount(this.myFormatter.format(TdsadviseAmount));
          Number AllotedAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase(""))
          {
            AllotedAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(6)).trim());
          }
          logger.info("AllotedAmount: " + AllotedAmount);
          paymentVO.setAllotedAmount(this.myFormatter.format(AllotedAmount));
          Number TdsAllocatedAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase(""))
          {
            TdsAllocatedAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(7)).trim());
          }
          logger.info("TdsAllocatedAmount: " + TdsAllocatedAmount);
          paymentVO.setTdsAllocatedAmount(this.myFormatter.format(TdsAllocatedAmount));

          allocatedList.add(paymentVO);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      query = null;
    }

    return allocatedList;
  }

  public ArrayList<PaymentMakerForCMVO> paymentAuthorGrid(PaymentMakerForCMVO vo)
  {
    StringBuilder loanNo = new StringBuilder();
    StringBuilder customerName = new StringBuilder();
    StringBuilder businessPartnerType = new StringBuilder();
    StringBuilder businessPartnerID = new StringBuilder();
    StringBuilder paymentAmount = new StringBuilder();
    StringBuilder instrumentNumber = new StringBuilder();
    StringBuilder paymentMode = new StringBuilder();

    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;

    ArrayList authordetailList = new ArrayList();

    logger.info("In paymentAuthorGrid..........userid.......... " + vo.getReportingToUserId());
    try
    {
      ArrayList searchlist = new ArrayList();
      StringBuffer bufInsSql = new StringBuffer();
      StringBuffer bufInsSqlTempCount = new StringBuffer();
      boolean appendSQL = false;

      loanNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
      logger.info("In paymentMakerGrid......loanNo  " + loanNo);

      customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
      businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
      businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));

      if (!CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
      {
        paymentAmount.append(this.myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPaymentAmount()).trim())).toString());
        logger.info("In paymentAuthor......paymentAmount  " + paymentAmount);
      }
      instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
      paymentMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPaymentMode()).trim()));
      logger.info("In paymentMakerGrid......paymentMode  " + paymentMode);

      bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE,GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME, CID.INSTRUMENT_AMOUNT,CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID   FROM CR_LOAN_DTL CLD  JOIN CR_INSTRUMENT_DTL CID ON(ifnull(CID.TXN_TYPE,'')='LIM' AND CLD.LOAN_ID=CID.TXNID  AND INSTRUMENT_TYPE='P'  AND CID.REC_STATUS='F' AND CID.MAKER_ID!='" + 
        vo.getUserId() + "' )" + 
        " JOIN GCD_CUSTOMER_M GCM ON(CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID )" + 
        " JOIN business_partner_view BPV ON( BPV.BP_ID=CID.BPID AND BPV.LOAN_ID=CID.TXNID AND BPV.BP_TYPE=cid.BPTYPE) " + 
        " JOIN generic_master  GM ON(GM.VALUE=CID.BPTYPE  AND GM.GENERIC_KEY='BPTYPE') WHERE CLD.REC_STATUS NOT IN ('X','L') ");

      bufInsSqlTempCount.append(" SELECT COUNT(DISTINCT CLD.LOAN_ID) ");
      bufInsSqlTempCount.append(" FROM CR_LOAN_DTL CLD ");
      bufInsSqlTempCount.append(" JOIN CR_INSTRUMENT_DTL CID ON(ifnull(CID.TXN_TYPE,'')='LIM' AND CLD.LOAN_ID=CID.TXNID  AND INSTRUMENT_TYPE='P'  AND CID.REC_STATUS='F' AND CID.MAKER_ID!='" + vo.getUserId() + "' ) ");
      if (!customerName.toString().trim().equalsIgnoreCase("")) {
        bufInsSqlTempCount.append(" JOIN GCD_CUSTOMER_M GCM ON(CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID )  ");
      }
      bufInsSqlTempCount.append(" WHERE CLD.REC_STATUS NOT IN ('X','L') ");

      if (!StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanNo.toString().trim())).equalsIgnoreCase("")) {
        bufInsSql.append(" AND CLD.LOAN_ID='" + loanNo + "' ");
        bufInsSqlTempCount.append("AND CLD.LOAN_ID='" + loanNo + "' ");
        appendSQL = true;
      }

      if (!StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerName.toString().trim())).equalsIgnoreCase("")) {
        bufInsSql.append(" AND GCM.CUSTOMER_NAME LIKE '%" + customerName + "%' ");
        bufInsSqlTempCount.append(" AND GCM.CUSTOMER_NAME LIKE '%" + customerName + "%' ");
        appendSQL = true;
      }

      if (!StringEscapeUtils.escapeSql(CommonFunction.checkNull(businessPartnerType.toString().trim())).equalsIgnoreCase("")) {
        bufInsSql.append(" AND CID.BPTYPE='" + businessPartnerType + "' ");
        bufInsSqlTempCount.append(" AND CID.BPTYPE='" + businessPartnerType + "' ");
        appendSQL = true;
      }

      if (!StringEscapeUtils.escapeSql(CommonFunction.checkNull(businessPartnerID.toString().trim())).equalsIgnoreCase("")) {
        bufInsSql.append(" AND CID.BPID='" + businessPartnerID + "' ");
        bufInsSqlTempCount.append(" AND CID.BPID='" + businessPartnerID + "' ");
        appendSQL = true;
      }

      if (!StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentAmount.toString().trim())).equalsIgnoreCase("")) {
        bufInsSql.append(" AND CID.INSTRUMENT_AMOUNT='" + paymentAmount + "' ");
        bufInsSqlTempCount.append(" AND CID.INSTRUMENT_AMOUNT='" + paymentAmount + "' ");
        appendSQL = true;
      }

      if (!StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentNumber.toString().trim())).equalsIgnoreCase("")) {
        bufInsSql.append(" AND CID.INSTRUMENT_NO='" + instrumentNumber + "'");
        bufInsSqlTempCount.append(" AND CID.INSTRUMENT_NO='" + instrumentNumber + "'");
        appendSQL = true;
      }
      if (!paymentMode.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append(" AND CID.INSTRUMENT_MODE='" + paymentMode + "'");
        bufInsSqlTempCount.append(" AND CID.INSTRUMENT_MODE='" + paymentMode + "'");
        appendSQL = true;
      }
      if (!CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase("")) {
        bufInsSql.append(" AND CID.MAKER_ID='" + vo.getLbxUserId() + "'");
        bufInsSqlTempCount.append(" AND CID.MAKER_ID='" + vo.getLbxUserId() + "'");
        appendSQL = true;
      }

      logger.info("In paymentAuthorGrid() COUNT QUERY IS..... " + bufInsSqlTempCount.toString());

      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
      bufInsSqlTempCount = null;

      logger.info("current PAge Link no .................... " + vo.getCurrentPageLink());
      if (vo.getCurrentPageLink() > 1)
      {
        startRecordIndex = (vo.getCurrentPageLink() - 1) * this.no;
        endRecordIndex = this.no;
        logger.info("startRecordIndex .................... " + startRecordIndex);
        logger.info("endRecordIndex .................... " + endRecordIndex);
      }

      bufInsSql.append(" limit " + startRecordIndex + "," + endRecordIndex);

      logger.info("In paymentAuthorGrid() MAIN QUERY IS..... " + bufInsSql.toString());

      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

      logger.info("paymentAuthor search Data size is...." + searchlist.size());

      for (int i = 0; i < searchlist.size(); i++)
      {
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();

          paymentVO.setModifyNo("<a href=paymentAuthorProcessAction.do?method=getDatatoApprove&lbxLoanNoHID=" + 
            CommonFunction.checkNull(data.get(0)).toString() + 
            "&instrumentID=" + CommonFunction.checkNull(data.get(10)).toString() + 
            "&lbxBPType=" + CommonFunction.checkNull(data.get(3)).toString() + ">" + 
            CommonFunction.checkNull(data.get(1)).toString() + "</a>");

          paymentVO.setLbxLoanNoHID(CommonFunction.checkNull(data.get(0)).trim());
          paymentVO.setLoanAccountNumber(CommonFunction.checkNull(data.get(1)).trim());
          paymentVO.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          paymentVO.setLbxBPType(CommonFunction.checkNull(data.get(3)).trim());
          paymentVO.setBusinessPartnerType(CommonFunction.checkNull(data.get(4)).trim());
          paymentVO.setLbxBPNID(CommonFunction.checkNull(data.get(5)).trim());
          paymentVO.setBusinessPartnerName(CommonFunction.checkNull(data.get(6)).trim());
          Number PaymentAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(7)).equalsIgnoreCase(""))
          {
            PaymentAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(7)).trim());
          }
          logger.info("PaymentAmount: " + PaymentAmount);
          paymentVO.setPaymentAmount(this.myFormatter.format(PaymentAmount));

          paymentVO.setInstrumentNumber(CommonFunction.checkNull(data.get(8)).trim());
          paymentVO.setPaymentMode(CommonFunction.checkNull(data.get(9)).trim());
          if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("C")) {
            paymentVO.setPaymentMode("Cash");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("Q")) {
            paymentVO.setPaymentMode("Cheque");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("D")) {
            paymentVO.setPaymentMode("DD");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("N")) {
            paymentVO.setPaymentMode("NEFT");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("R")) {
            paymentVO.setPaymentMode("RTGS");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("S")) {
            paymentVO.setPaymentMode("Adjustment");
          }
          paymentVO.setInstrumentID(CommonFunction.checkNull(data.get(10)).trim());

          paymentVO.setTotalRecordSize(count);
          paymentVO.setReportingToUserId(CommonFunction.checkNull(data.get(11)).trim());
          authordetailList.add(paymentVO);
          paymentVO = null;
        }
        data = null;
      }
      searchlist = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      loanNo = null;
      customerName = null;
      businessPartnerType = null;
      businessPartnerID = null;
      paymentAmount = null;
      instrumentNumber = null;
      paymentMode = null;
    }
    return authordetailList;
  }

  public ArrayList<PaymentMakerForCMVO> searchPaymentData(PaymentMakerForCMVO paymentMakerForCMVO)
  {
    ArrayList payableList = new ArrayList();

    ArrayList searchlist = new ArrayList();
    logger.info("In searchPaymentData....................");
    StringBuffer bufInsSql = new StringBuffer();
    try {
      bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE,    GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME ,   CID.INSTRUMENT_MODE,date_format(CID.RECEIVED_DATE,'" + 
        this.dateFormat + "'),CID.INSTRUMENT_NO,date_format(CID.INSTRUMENT_DATE,'" + this.dateFormat + "')," + 
        " CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" + 
        "  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME," + 
        "  CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.INSTRUMENT_AMOUNT,CID.TDS_AMOUNT ,CID.REMARKS,CID.INSTRUMENT_ID,CID.MAKER_REMARKS,CID.PAY_TO,CID.PAYEE_NAME,CLD.REC_STATUS,CID.TA_Adjustment_FLAG,CID.TA_LOAN_ID,(SELECT LOAN_NO FROM CR_LOAN_DTL CLD JOIN CR_INSTRUMENT_DTL CID  ON CLD.LOAN_ID=CID.TA_LOAN_ID WHERE CID.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()) + "' ) AS TA_LOAN_NO,CID.TA_ACCOUNT_NAME," + 
        " BENEFICIARY_BANK_ID,bank_name,BENEFICIARY_BANK_BRANCH_ID,bank_branch_name,BENEFICIARY_BANK_ACCOUNT,BENEFICIARY_BRANCH_IFCS_CODE" + 
        "  from cr_instrument_dtl CID left join com_bank_m cm on cm.bank_code=CID.BENEFICIARY_BANK_ID " + 
        " left join com_bankBranch_m cbm on cbm.bank_branch_code=CID.BENEFICIARY_BANK_BRANCH_ID," + 
        " gcd_customer_m GCM, cr_loan_dtl CLD, business_partner_view BPV,generic_master  GM " + 
        " where CID.TXNID=CLD.LOAN_ID" + 
        "  AND BPV.BP_ID=CID.BPID AND CID.BPTYPE=BPV.BP_TYPE AND BPV.LOAN_ID=CID.TXNID" + 
        "  AND GM.VALUE=CID.BPTYPE and CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID" + 
        "  AND GM.GENERIC_KEY='BPTYPE' " + 
        "  and INSTRUMENT_TYPE='P'AND CID.REC_STATUS='P' and ifnull(CID.TXN_TYPE,'')='LIM' " + 
        " AND CID.TXNID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim()) + "' " + 
        " AND CID.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()) + "' ");

      logger.info("In searchPaymentData......... query..........." + bufInsSql.toString());
      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      logger.info("paymentMaker search Data size is...." + searchlist.size());

      for (int i = 0; i < searchlist.size(); i++) {
        logger.info("paymentMaker search List " + searchlist.get(i).toString());
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
          paymentVO.setLbxLoanNoHID(CommonFunction.checkNull(data.get(0)).trim());
          paymentVO.setLoanAccountNumber(CommonFunction.checkNull(data.get(1)).trim());
          paymentVO.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          logger.info(" searchPaymentData setCustomerName:-------------" + data.get(2));
          paymentVO.setLbxBPType(CommonFunction.checkNull(data.get(3)).trim());
          paymentVO.setBusinessPartnerType(CommonFunction.checkNull(data.get(4)).trim());
          logger.info("setBusinessPartnerType:-------------" + data.get(4));
          paymentVO.setLbxBPNID(CommonFunction.checkNull(data.get(5)).trim());
          paymentVO.setBusinessPartnerName(CommonFunction.checkNull(data.get(6)).trim());
          if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("Q")) {
            paymentVO.setLbxpaymentMode("B");
          }
          else if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("D")) {
            paymentVO.setLbxpaymentMode("B");
          }
          else if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("N")) {
            paymentVO.setLbxpaymentMode("B");
          }
          else if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("R")) {
            paymentVO.setLbxpaymentMode("B");
          }
          else if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("S")) {
            paymentVO.setLbxpaymentMode("S");
          }
          paymentVO.setPaymentMode(CommonFunction.checkNull(data.get(7)).trim());
          paymentVO.setPaymentDate(CommonFunction.checkNull(data.get(8)).trim());
          paymentVO.setInstrumentNumber(CommonFunction.checkNull(data.get(9)).trim());
          paymentVO.setInstrumentDate(CommonFunction.checkNull(data.get(10)).trim());
          paymentVO.setBankAccount(CommonFunction.checkNull(data.get(11)).trim());
          logger.info(" searchPaymentData setBankAccount:-------------" + data.get(11));
          paymentVO.setLbxBankID(CommonFunction.checkNull(data.get(12)).trim());
          paymentVO.setBank(CommonFunction.checkNull(data.get(13)).trim());
          paymentVO.setLbxBranchID(CommonFunction.checkNull(data.get(14)).trim());
          paymentVO.setBranch(CommonFunction.checkNull(data.get(15)).trim());
          paymentVO.setMicr(CommonFunction.checkNull(data.get(16)).trim());
          paymentVO.setIfsCode(CommonFunction.checkNull(data.get(17)).trim());
          Number PaymentAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(18)).equalsIgnoreCase(""))
          {
            PaymentAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(18)).trim());
          }
          logger.info("PaymentAmount: " + PaymentAmount);
          paymentVO.setPaymentAmount(this.myFormatter.format(PaymentAmount));
          Number TdsAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(19)).equalsIgnoreCase(""))
          {
            TdsAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(19)).trim());
            logger.info("TdsAmount: " + TdsAmount);
          }

          paymentVO.setTdsAmount(this.myFormatter.format(TdsAmount));
          paymentVO.setAuthorRemarks(CommonFunction.checkNull(data.get(20)).trim());
          paymentVO.setInstrumentID(CommonFunction.checkNull(data.get(21)).trim());
          paymentVO.setRemarks(CommonFunction.checkNull(data.get(22)).trim());
          paymentVO.setLbxpayTo(CommonFunction.checkNull(data.get(23)).trim());
          if (CommonFunction.checkNull(data.get(23)).trim().equalsIgnoreCase("CS"))
          {
            paymentVO.setPayTo("CUSTOMER");
          }
          else if (CommonFunction.checkNull(data.get(23)).trim().equalsIgnoreCase("MF"))
          {
            paymentVO.setPayTo("MANUFACTURER");
          }
          else if (CommonFunction.checkNull(data.get(23)).trim().equalsIgnoreCase("SU"))
          {
            paymentVO.setPayTo("SUPPLIER/DEALER/VENDOR ");
          }
          else if (CommonFunction.checkNull(data.get(23)).trim().equalsIgnoreCase("RSP"))
          {
            paymentVO.setPayTo("RSP ");
          }
          else if (CommonFunction.checkNull(data.get(23)).trim().equalsIgnoreCase("OTH"))
          {
            if (CommonFunction.checkNull(data.get(23)).trim().equalsIgnoreCase("OTH")) {
              paymentVO.setPayTo("OTHERS");
            }
          }

          paymentVO.setPayeeName(CommonFunction.checkNull(data.get(24)).trim());
          paymentVO.setLoanRecStatus(CommonFunction.checkNull(data.get(25)).trim());
          paymentVO.setTaStatus(CommonFunction.checkNull(data.get(26)).trim());
          paymentVO.setLbxTaLoanNoHID(CommonFunction.checkNull(data.get(27)).trim());
          paymentVO.setTaLoanNo(CommonFunction.checkNull(data.get(28)).trim());
          paymentVO.setTaCustomerName(CommonFunction.checkNull(data.get(29)).trim());

          paymentVO.setBeneficiary_lbxBankID(CommonFunction.checkNull(data.get(30)).trim());
          paymentVO.setBeneficiary_bankCode(CommonFunction.checkNull(data.get(31)).trim());
          paymentVO.setBeneficiary_lbxBranchID(CommonFunction.checkNull(data.get(32)).trim());
          paymentVO.setBeneficiary_bankBranchName(CommonFunction.checkNull(data.get(33)).trim());
          paymentVO.setBeneficiary_accountNo(CommonFunction.checkNull(data.get(34)).trim());
          paymentVO.setBeneficiary_ifscCode(CommonFunction.checkNull(data.get(35)).trim());

          logger.info(" searchPaymentData TaStatus:-------------" + data.get(26));

          payableList.add(paymentVO);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } finally {
      bufInsSql = null;
    }

    return payableList;
  }

  public ArrayList<PaymentMakerForCMVO> getbussinessPartner()
  {
    ArrayList getBPList = new ArrayList();
    StringBuilder query = new StringBuilder();
    try {
      ArrayList mainList = new ArrayList();
      ArrayList subList = new ArrayList();

      query.append("select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='BPTYPE' and rec_status ='A' ");

      logger.info("In getBPList:------" + query);

      mainList = ConnectionDAO.sqlSelect(query.toString());
      for (int i = 0; i < mainList.size(); i++)
      {
        subList = (ArrayList)mainList.get(i);
        if (subList.size() > 0) {
          PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
          paymentVO.setBusinessPartnerType(CommonFunction.checkNull(subList.get(0)).trim());
          paymentVO.setBusinessPartnerDesc(CommonFunction.checkNull(subList.get(1)).trim());

          getBPList.add(paymentVO);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      query = null;
    }
    return getBPList;
  }

  public ArrayList<PaymentMakerForCMVO> saveddatatoApprove(PaymentMakerForCMVO paymentMakerForCMVO)
  {
    ArrayList datatoapproveList = new ArrayList();

    ArrayList searchlist = new ArrayList();
    logger.info("In searchAuthorData....................");
    StringBuffer bufInsSql = new StringBuffer();
    try {
      bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE,    GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME,    CID.INSTRUMENT_MODE,date_format(CID.RECEIVED_DATE,'" + 
        this.dateFormat + "'),CID.INSTRUMENT_NO,date_format(CID.INSTRUMENT_DATE,'" + this.dateFormat + "')," + 
        " CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" + 
        "  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME," + 
        " CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.INSTRUMENT_AMOUNT,CID.TDS_AMOUNT ,CID.REMARKS,CID.INSTRUMENT_ID,CID.MAKER_REMARKS,CID.PAY_TO,CID.PAYEE_NAME,CLD.REC_STATUS" + 
        "  ,(SELECT DESCRIPTION FROM generic_master WHERE GENERIC_KEY='PAY_TO' AND VALUE=CID.PAY_TO) PAY_TO_DESC " + 
        " ,CID.TA_Adjustment_FLAG,(SELECT LOAN_NO FROM CR_LOAN_DTL CLD JOIN CR_INSTRUMENT_DTL CID  ON CLD.LOAN_ID=CID.TA_LOAN_ID WHERE CID.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()) + "' ) AS TA_LOAN_NO " + 
        " ,CID.TA_LOAN_ID,CID.TA_ACCOUNT_NAME,BENEFICIARY_BANK_ID,bank_name,BENEFICIARY_BANK_BRANCH_ID,bank_branch_name,BENEFICIARY_BANK_ACCOUNT,BENEFICIARY_BRANCH_IFCS_CODE" + 
        ",CID.DEPOSIT_BANK_ACCOUNT,CID.DEPOSIT_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.DEPOSIT_BANK_ID)DEPOSIT_BANK_NAME,CID.DEPOSIT_BRANCH_ID," + 
        " (SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.DEPOSIT_BRANCH_ID)DEPOSIT_BRANCH_NAME,CID.DEPOSIT_MICR_CODE,CID.DEPOSIT_IFSC_CODE " + 
        "  from cr_instrument_dtl CID left join com_bank_m cm on cm.bank_code=CID.BENEFICIARY_BANK_ID " + 
        " left join com_bankBranch_m cbm on cbm.bank_branch_code=CID.BENEFICIARY_BANK_BRANCH_ID,gcd_customer_m GCM ," + 
        " cr_loan_dtl CLD , business_partner_view BPV,generic_master  GM" + 
        " where CID.TXNID=CLD.LOAN_ID" + 
        "  AND BPV.BP_ID=CID.BPID AND BPV.BP_TYPE=CID.BPTYPE " + 
        "  AND GM.VALUE=CID.BPTYPE  and CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID" + 
        "  AND GM.GENERIC_KEY='BPTYPE' " + 
        "  and INSTRUMENT_TYPE='P'AND CID.REC_STATUS='F' and ifnull(CID.TXN_TYPE,'')='LIM' " + 
        " AND CID.TXNID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim()) + "' " + 
        " AND CID.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()) + "' ");

      logger.info("In searchAuthorData......... query..........." + bufInsSql.toString());
      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      logger.info("searchAuthorData search Data size is...." + searchlist.size());
      bufInsSql = null;
      for (int i = 0; i < searchlist.size(); i++) {
        logger.info("paymentMasearchAuthorDataker search List " + searchlist.get(i).toString());
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
          paymentVO.setLbxLoanNoHID(CommonFunction.checkNull(data.get(0)).trim());
          paymentVO.setLoanAccountNumber(CommonFunction.checkNull(data.get(1)).trim());
          paymentVO.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          logger.info(" searchPaymentData setCustomerName:-------------" + data.get(2));
          paymentVO.setLbxBPType(CommonFunction.checkNull(data.get(3)).trim());
          paymentVO.setBusinessPartnerType(CommonFunction.checkNull(data.get(4)).trim());
          paymentVO.setLbxBPNID(CommonFunction.checkNull(data.get(5)).trim());
          paymentVO.setBusinessPartnerName(CommonFunction.checkNull(data.get(6)).trim());
          paymentVO.setPaymentMode(CommonFunction.checkNull(data.get(7)).trim());
          paymentVO.setPaymentDate(CommonFunction.checkNull(data.get(8)).trim());
          paymentVO.setInstrumentNumber(CommonFunction.checkNull(data.get(9)).trim());
          paymentVO.setInstrumentDate(CommonFunction.checkNull(data.get(10)).trim());
          paymentVO.setBankAccount(CommonFunction.checkNull(data.get(11)).trim());
          logger.info(" searchPaymentData setBankAccount:-------------" + data.get(11));
          paymentVO.setLbxBankID(CommonFunction.checkNull(data.get(12)).trim());
          paymentVO.setBank(CommonFunction.checkNull(data.get(13)).trim());
          paymentVO.setLbxBranchID(CommonFunction.checkNull(data.get(14)).trim());
          paymentVO.setBranch(CommonFunction.checkNull(data.get(15)).trim());
          paymentVO.setMicr(CommonFunction.checkNull(data.get(16)).trim());
          paymentVO.setIfsCode(CommonFunction.checkNull(data.get(17)).trim());
          Number PaymentAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(18)).equalsIgnoreCase("")) {
            PaymentAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(18)).trim());
          }
          logger.info("PaymentAmount: " + PaymentAmount);
          paymentVO.setPaymentAmount(this.myFormatter.format(PaymentAmount));
          Number TdsAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(19)).equalsIgnoreCase(""))
          {
            TdsAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(19)).trim());
          }logger.info("TdsAmount: " + TdsAmount);
          paymentVO.setTdsAmount(this.myFormatter.format(TdsAmount));

          paymentVO.setAuthorRemarks(CommonFunction.checkNull(data.get(20)).trim());
          paymentVO.setInstrumentID(CommonFunction.checkNull(data.get(21)).trim());
          paymentVO.setRemarks(CommonFunction.checkNull(data.get(22)).trim());

          paymentVO.setPayeeName(CommonFunction.checkNull(data.get(24)).trim());
          paymentVO.setLoanRecStatus(CommonFunction.checkNull(data.get(25)).trim());

          paymentVO.setPayTo(CommonFunction.checkNull(data.get(26)).trim());

          paymentVO.setTaStatus(CommonFunction.checkNull(data.get(27)).trim());
          paymentVO.setTaLoanNo(CommonFunction.checkNull(data.get(28)).trim());
          paymentVO.setLbxTaLoanNoHID(CommonFunction.checkNull(data.get(29)).trim());
          paymentVO.setTaCustomerName(CommonFunction.checkNull(data.get(30)).trim());

          paymentVO.setBeneficiary_lbxBankID(CommonFunction.checkNull(data.get(31)).trim());
          paymentVO.setBeneficiary_bankCode(CommonFunction.checkNull(data.get(32)).trim());
          paymentVO.setBeneficiary_lbxBranchID(CommonFunction.checkNull(data.get(33)).trim());
          paymentVO.setBeneficiary_bankBranchName(CommonFunction.checkNull(data.get(34)).trim());
          paymentVO.setBeneficiary_accountNo(CommonFunction.checkNull(data.get(35)).trim());
          paymentVO.setBeneficiary_ifscCode(CommonFunction.checkNull(data.get(36)).trim());

          paymentVO.setDepositAccountNo(CommonFunction.checkNull(data.get(37)).trim());
          paymentVO.setDepositLbxBankID(CommonFunction.checkNull(data.get(38)).trim());
          paymentVO.setDepositBankCode(CommonFunction.checkNull(data.get(39)).trim());
          paymentVO.setDepositLbxBranchID(CommonFunction.checkNull(data.get(40)).trim());
          paymentVO.setDepositBankBranchName(CommonFunction.checkNull(data.get(41)).trim());
          paymentVO.setDepositmicrCode(CommonFunction.checkNull(data.get(42)).trim());
          paymentVO.setDepositIfscCode(CommonFunction.checkNull(data.get(43)).trim());

          logger.info(" searchPaymentData setInstrumentID:-------------" + data.get(21));

          datatoapproveList.add(paymentVO);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      searchlist.clear();
      bufInsSql = null;
    }
    return datatoapproveList;
  }

  public boolean closeViewPayable(PaymentMakerForCMVO paymentMakerForCMVO)
  {
    logger.info("###  In closeViewPayable #### ");
    boolean status = false;
    StringBuilder bptype = new StringBuilder();
    StringBuilder loanId = new StringBuilder();
    StringBuilder deleteCount = new StringBuilder();
    StringBuilder data = new StringBuilder();
    try {
      ArrayList queryList = new ArrayList();
      ArrayList subList = new ArrayList();
      ArrayList list = new ArrayList();
      ArrayList subList1 = new ArrayList();
      ArrayList dataList = new ArrayList();
      PrepStmtObject insertPrepStmtObject = null;
      StringBuffer bufInsSql = null;

      String[] allotedAmount = paymentMakerForCMVO.getAllocatedArry();

      logger.info("### In closeViewPayable  allotedAmount size #### " + allotedAmount.length);

      String[] adviseId = paymentMakerForCMVO.getTxnAdvicedIDArry();

      logger.info("### In closeViewPayable  adviseId size #### " + adviseId.length);

      String[] tdsAllotedAmount = paymentMakerForCMVO.getTdsAllocatedArry();

      logger.info("### In closeViewPayable  tdsAllotedAmount size #### " + tdsAllotedAmount.length);

      deleteCount.append(ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='" + paymentMakerForCMVO.getInstrumentID() + "'"));

      logger.info("### In closeViewPayable  deleteCount #### " + deleteCount);
      if (!deleteCount.toString().equalsIgnoreCase("0"))
      {
        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql = new StringBuffer();
        bufInsSql.append(" DELETE FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='" + CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim() + "'");
        logger.info("### In closeViewPayable  bufInsSql #### " + bufInsSql.toString());
        insertPrepStmtObject.setSql(bufInsSql.toString());
        queryList.add(insertPrepStmtObject);
      }

      for (int i = 0; i < allotedAmount.length; i++)
      {
        insertPrepStmtObject = null;
        bufInsSql = null;
        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql = new StringBuffer();

        String alloc = CommonFunction.checkNull(allotedAmount[i]);
        if (alloc.equalsIgnoreCase("")) {
          alloc = "0";
        }
        double allocatedAmnt = this.myFormatter.parse(alloc).doubleValue();
        logger.info("### In double allotedAmount #### " + allocatedAmnt);
        if (allocatedAmnt > 0.0D)
        {
          bufInsSql.append("insert into cr_pmnt_dtl( PMNT_DATE,PMNT_AMOUNT,TDS_AMOUNT,INSTRUMENT_ID,TXNADVICEID,REC_STATUS,MAKER_ID,MAKER_DATE )");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" (select RECEIVED_DATE from cr_instrument_dtl where INSTRUMENT_ID=? limit 1),");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" 'P',");
          bufInsSql.append(" ?,");
          bufInsSql.append(" STR_TO_DATE(?,'" + this.dateFormatWithTime + "'))");

          if (CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim());
          if (CommonFunction.checkNull(alloc).equalsIgnoreCase(""))
            insertPrepStmtObject.addString("0");
          else
            insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(alloc).trim()).toString());
          if (CommonFunction.checkNull(tdsAllotedAmount[i]).equalsIgnoreCase(""))
            insertPrepStmtObject.addString("0");
          else
            insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(tdsAllotedAmount[i]).trim()).toString());
          if (CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim());
          if (CommonFunction.checkNull(adviseId[i]).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(adviseId[i]).trim());
          if (CommonFunction.checkNull(paymentMakerForCMVO.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getMakerId()).trim());
          }
          if (CommonFunction.checkNull(paymentMakerForCMVO.getBusinessDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getBusinessDate()).trim());
          }
          insertPrepStmtObject.setSql(bufInsSql.toString());
          queryList.add(insertPrepStmtObject);

          logger.info("In insert of  cr_pmnt_dtl" + insertPrepStmtObject.printQuery());
        }

      }

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
      logger.info("In closeViewPayable,,,,," + status);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      bptype = null;
      loanId = null;
      deleteCount = null;
      data = null;
    }

    return status;
  }

  public String getpmntId()
  {
    String pmntId = "";
    StringBuilder paymentId = new StringBuilder();
    try {
      paymentId.append("SELECT max(PMNT_ID) from cr_pmnt_dtl for update");

      pmntId = ConnectionDAO.singleReturn(paymentId.toString());
      logger.info("IN paymentId ### " + paymentId);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      paymentId = null;
    }

    return pmntId;
  }

  public String updateFlagByPaymentAuthor(PaymentMakerForCMVO paymentMakerForCMVO) {
    boolean status = false;
    String procval = "";
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    try
    {
      StringBuffer bufInsSql = new StringBuffer();
      ArrayList queryList = new ArrayList();
      ArrayList in = new ArrayList();
      ArrayList out = new ArrayList();
      ArrayList outMessages = new ArrayList();

      int statusProc = 0;
      PrepStmtObject updatePrepStmtObject = new PrepStmtObject();

      logger.info(" In updateFlagByPaymentAuthor BeforeProc: ");
      logger.info("In updateFlagByPaymentAuthor getInstrumentID()" + paymentMakerForCMVO.getInstrumentID());
      logger.info("In updateFlagByPaymentAuthor getDecision" + paymentMakerForCMVO.getDecision());
      logger.info("In updateFlagByPaymentAuthor getInstrumentID()" + paymentMakerForCMVO.getComments());
      logger.info(" In pay_approveReject_Rec Procedure ");

      in.add(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID().trim()));
      in.add(CommonFunction.checkNull(paymentMakerForCMVO.getDecision().trim()));
      in.add(CommonFunction.checkNull(paymentMakerForCMVO.getComments().trim()));
      in.add("P");
      in.add(CommonFunction.checkNull(paymentMakerForCMVO.getUserId().trim()));
      String date = CommonFunction.changeFormat(paymentMakerForCMVO.getBusinessDate());
      in.add(date);

      out.add(s1.toString());
      out.add(s2.toString());

      outMessages = (ArrayList)ConnectionDAO.callSP("pay_approveReject_Rec", in, out);
      s1.append(CommonFunction.checkNull(outMessages.get(0)));
      s2.append(CommonFunction.checkNull(outMessages.get(1)));
      logger.info("s1: " + s1);
      logger.info("s2: " + s2);
      logger.info("Status for Proc: " + statusProc);
      logger.info("After Proc: ");
      procval = s2.toString();
      if (s1.toString().equalsIgnoreCase("S"))
      {
        logger.info("After proc call..commit.error message." + s2);
      }
      else
      {
        logger.info("After proc call..rollback.error message." + s2);
      }

      if (!s1.toString().equalsIgnoreCase("S")) {
        logger.info("After Proc inside If ");
        status = false;
      } else {
        status = true;
      }

      logger.info("In updateFlagByPaymentAuthor,,,,," + status);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      s1 = null;
      s2 = null;
    }

    return procval;
  }

  public ArrayList<PaymentMakerForCMVO> onViewPayable(PaymentMakerForCMVO paymentMakerForCMVO, int loanId, String bpType, int bpId) {
    ArrayList getPayableList = new ArrayList();
    StringBuilder query = new StringBuilder();
    try {
      ArrayList mainList = new ArrayList();
      ArrayList subList = new ArrayList();

      logger.info(" In onViewPayable....");

      query.append("SELECT DATE_FORMAT(A.ADVICE_DATE,'" + this.dateFormat + "'),(Select CHARGE_DESC From com_charge_code_m " + 
        " Where CHARGE_CODE=a.CHARGE_CODE_ID) CHARGE,A.ORG_ADVICE_AMOUNT,A.WAIVE_OFF_AMOUNT,A.TDS_AMOUNT," + 
        " A.TXN_ADJUSTED_AMOUNT,A.AMOUNT_IN_PROCESS," + 
        " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT, A.TXNADVICE_ID,A.ADVICE_AMOUNT  " + 
        "  FROM cr_txnadvice_dtl A   WHERE A.REC_STATUS IN ('A', 'F') and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 " + 
        "  AND  A.ADVICE_TYPE='P' AND LOAN_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(loanId)).trim()) + "'" + 
        " AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "'" + 
        " AND A.BP_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(bpId)).trim()) + "' ORDER BY A.ADVICE_DATE ASC");

      logger.info("In onViewPayable" + query);

      mainList = ConnectionDAO.sqlSelect(query.toString());
      for (int i = 0; i < mainList.size(); i++)
      {
        subList = (ArrayList)mainList.get(i);
        if (subList.size() > 0)
        {
          PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
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

          paymentVO.setTxnAdvicedID(CommonFunction.checkNull(subList.get(8)).trim());
          if (CommonFunction.checkNull(subList.get(9)).trim().equalsIgnoreCase("")) {
            paymentVO.setAdviceAmount("0");
          }
          else {
            Number adviceAmt = this.myFormatter.parse(CommonFunction.checkNull(subList.get(9)).trim());
            paymentVO.setAdviceAmount(this.myFormatter.format(adviceAmt));
          }

          getPayableList.add(paymentVO);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      query = null;
    }

    return getPayableList;
  }

  public ArrayList<PaymentMakerForCMVO> getViewPayable(int loanId, String bpType, int bpId, String uId, String amt, int instrumentId, String tdsAmount) {
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    StringBuilder procval = new StringBuilder();
    StringBuilder query = new StringBuilder();

    ArrayList getPayableList = new ArrayList();
    try
    {
      ArrayList mainList = new ArrayList();
      ArrayList subList = new ArrayList();

      logger.info(" In getViewPayable Before proc call....");
      logger.info("loanId" + loanId);
      logger.info("bpType" + bpType);
      logger.info("bpId" + bpId);
      logger.info("uId" + uId);
      logger.info("amt" + amt);
      logger.info("instrumentId" + instrumentId);
      logger.info("tdsAmount" + tdsAmount);
      logger.info(" IN Advice_Pay_Rec PROCEDURE");

      in.add("P");
      in.add(Integer.valueOf(loanId));
      in.add(bpType);
      in.add(Integer.valueOf(bpId));
      in.add(uId);
      in.add(amt);
      in.add(Integer.valueOf(instrumentId));
      in.add(tdsAmount);

      out.add(s1.toString());
      out.add(s2.toString());

      outMessages = (ArrayList)ConnectionDAO.callSP("Advice_Pay_Rec", in, out);

      s1.append(CommonFunction.checkNull(outMessages.get(0)));
      s2.append(CommonFunction.checkNull(outMessages.get(1)));
      logger.info("s1: " + s1);
      logger.info("s2: " + s2);

      if (s1.toString().equalsIgnoreCase("S"))
      {
        logger.info("After proc call..commit.error message." + s2);
        procval = s2;
      }
      else
      {
        logger.info("After proc call..rollback.error message." + s2);
        procval = s2;
      }

      logger.info("After proc call....");
      query.append("SELECT DATE_FORMAT(A.ADVICE_DATE,'" + this.dateFormat + "'),A.CHARGE,A.ADVICE_AMOUNT,A.BALANCE_AMOUNT," + 
        " IFNULL(A.PMNT_AMOUNT,0) PMNT_AMOUNT,A.AMOUNT_IN_PROCESS,A.TDS_AMOUNT ,A.TXNADVICE_ID, IFNULL(A.TDS_ALLOCATED_AMOUNT,0)TDS_ALLOCATED_AMOUNT" + 
        " FROM tmp_adv_payrec A LEFT OUTER JOIN cr_pmnt_dtl cpd  ON CPD.TXNADVICEID=A.TXNADVICE_ID AND CPD.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(instrumentId)).trim()) + "' " + 
        " WHERE  A.LOAN_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(loanId)).trim()) + "' AND A.ADVICE_TYPE='P' " + 
        " and A.BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "' and A.BP_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(bpId)).trim()) + "'" + 
        " and A.MAKER_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(uId).trim()) + "' " + 
        " Order By A.ADVICE_DATE, A.TXNADVICE_ID ASC; ");

      logger.info("In getListOfValues" + query);

      mainList = ConnectionDAO.sqlSelect(query.toString());
      for (int i = 0; i < mainList.size(); i++)
      {
        subList = (ArrayList)mainList.get(i);
        if (subList.size() > 0) {
          PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
          paymentVO.setPaymentDate(CommonFunction.checkNull(subList.get(0)).trim());
          paymentVO.setChargeDesc(CommonFunction.checkNull(subList.get(1)).trim());
          if (CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase("")) {
            paymentVO.setOriginalAmount("0");
          }
          else {
            Number OriginalAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
            logger.info("OriginalAmount: " + OriginalAmount);
            paymentVO.setOriginalAmount(this.myFormatter.format(OriginalAmount));
          }
          if (CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase("")) {
            paymentVO.setBalanceAmount("0");
          }
          else {
            Number BalanceAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
            logger.info("BalanceAmount: " + BalanceAmount);
            paymentVO.setBalanceAmount(this.myFormatter.format(BalanceAmount));
          }
          if (CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase("")) {
            paymentVO.setAllotedAmount("0");
          }
          else {
            Number AllotedAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
            logger.info("AllotedAmount: " + AllotedAmount);
            paymentVO.setAllotedAmount(this.myFormatter.format(AllotedAmount));
          }
          if (CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("")) {
            paymentVO.setAmountInProcess("0");
          }
          else
          {
            Number AmountInProcess = this.myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
            logger.info("AmountInProcess: " + AmountInProcess);
            paymentVO.setAmountInProcess(this.myFormatter.format(AmountInProcess));
          }
          if (CommonFunction.checkNull(subList.get(6)).trim().equalsIgnoreCase("")) {
            paymentVO.setTdsadviseAmount("0");
          }
          else
          {
            Number TdsadviseAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(6)).trim());
            logger.info("TdsadviseAmount: " + TdsadviseAmount);
            paymentVO.setTdsadviseAmount(this.myFormatter.format(TdsadviseAmount));
          }
          paymentVO.setTxnAdvicedID(CommonFunction.checkNull(subList.get(7)).trim());
          if (CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("")) {
            paymentVO.setTdsadviseAmount("0");
          }
          else {
            Number TdsAllocatedAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(8)).trim());
            logger.info("TdsAllocatedAmount: " + TdsAllocatedAmount);
            paymentVO.setTdsAllocatedAmount(this.myFormatter.format(TdsAllocatedAmount));
          }
          logger.info("procval:----> " + CommonFunction.checkNull(procval.toString()));
          paymentVO.setProcVal(CommonFunction.checkNull(procval.toString()));

          getPayableList.add(paymentVO);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      in = null;
      out = null;
      outMessages = null;
      s1 = null;
      s2 = null;
      procval = null;
      query = null;
    }

    return getPayableList;
  }

  public int existData(Object ob) {
    int result = 0;
    PaymentMakerForCMVO paymentVO = (PaymentMakerForCMVO)ob;
    StringBuilder existCount = new StringBuilder();
    try {
      existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getInstrumentNumber()).trim()) + "'" + 
        " and cid.ISSUEING_BANK_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getLbxBankID()).trim()) + "'and cid.ISSUEING_BRANCH_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getLbxBranchID()).trim()) + "' " + 
        " and BPID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getLbxBPNID()).trim()) + "'" + 
        " and BPTYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getLbxBPType()).trim()) + "' and INSTRUMENT_TYPE='P' AND REC_STATUS<>'X'");

      result = Integer.parseInt(ConnectionDAO.singleReturn(existCount.toString()));
      logger.info("IN existData  ### " + existCount);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      existCount = null;
    }

    return result;
  }

  public String checkPaymentStatusFromInventory(Object ob)
  {
    String result = "1";
    String inventoryFlag = "";
    String bookNoNonMandatory = "";
    String bookNoValidate = "";
    String bookNoValidate2 = "";
    String checkAllBranch = "";
    PaymentMakerForCMVO paymentVO = (PaymentMakerForCMVO)ob;
    StringBuilder existCount = new StringBuilder();
    StringBuilder inventoryFlagInMst = new StringBuilder();
    StringBuilder existInStationary = new StringBuilder();
    StringBuilder allBranch = new StringBuilder();
    StringBuilder checkUser = new StringBuilder();
    StringBuilder nonMandatory = new StringBuilder();
    StringBuilder bookNoValidatation = new StringBuilder();
    StringBuilder bookNoValidation2 = new StringBuilder();
    String checkUserAvailable = "";
    String IssuingUser = paymentVO.getMakerId();
    String instrumentNumber = paymentVO.getInstrumentNumber();
    int receiptChequeNo = 0;
    try
    {
      nonMandatory.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOOK_NO_NON_MANDATORY'");
      bookNoNonMandatory = ConnectionDAO.singleReturn(nonMandatory.toString());

      inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CHEQUE_INVENTORY_FLAG'");
      logger.info("IN checkPaymentStatusFromInventory inventoryFlag ### " + inventoryFlagInMst.toString());
      inventoryFlag = ConnectionDAO.singleReturn(inventoryFlagInMst.toString());
      if (inventoryFlag.equalsIgnoreCase("Y"))
      {
        allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='" + paymentVO.getInstrumentNumber() + "' AND STATUS='A'");
        logger.info("IN checkAllBranch ### " + allBranch.toString());
        checkAllBranch = ConnectionDAO.singleReturn(allBranch.toString());
        if (checkAllBranch.equalsIgnoreCase("0"))
        {
          if (bookNoNonMandatory.equalsIgnoreCase("N"))
          {
            checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + paymentVO.getInstrumentNumber() + "' AND IFNULL(ISSUING_USER,'')<>''");
            logger.info("IN checkInventory inventoryFlag ### " + checkUser.toString());
            checkUserAvailable = ConnectionDAO.singleReturn(checkUser.toString());
          } else {
            checkUserAvailable = "1";
          }
          if (!checkUserAvailable.equalsIgnoreCase("0"))
          {
            existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + paymentVO.getInstrumentNumber() + "'  AND IFNULL(ISSUING_USER,'" + IssuingUser + "')='" + IssuingUser + "' ");
            logger.info("IN checkUserAvailability  in existInStationary ### " + existInStationary.toString());
            result = ConnectionDAO.singleReturn(existInStationary.toString());
          } else {
            existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + paymentVO.getInstrumentNumber() + "'  AND ISSUING_BRANCH='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getDefaultBranch()).trim()) + "'");
            logger.info("IN checkReciptStatusFromInventory  in existInStationary ### " + existInStationary.toString());
            result = ConnectionDAO.singleReturn(existInStationary.toString());
          }
        } else {
          result = "1";
        }
        if (bookNoNonMandatory.equalsIgnoreCase("Y"))
        {
          bookNoValidatation.append(" SELECT BOOK_NO FROM CR_STATIONARY_STATUS_DTL WHERE RECEPT_CHEQUE_NO='" + paymentVO.getInstrumentNumber() + "' AND STATUS='A'");
          bookNoValidate = ConnectionDAO.singleReturn(bookNoValidatation.toString());
          bookNoValidation2.append(" SELECT IFNULL(STATUS,'') FROM CR_STATIONARY_STATUS_DTL WHERE RECEPT_CHEQUE_NO=" + receiptChequeNo + " AND BOOK_NO='" + bookNoValidate + "'");
          bookNoValidate2 = ConnectionDAO.singleReturn(bookNoValidation2.toString());
        }
        if (CommonFunction.checkNull(bookNoValidate2).equalsIgnoreCase("A"))
        {
          result = "2";
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      existCount = null;
      inventoryFlagInMst = null;
      existInStationary = null;
    }
    return result;
  }

  public ArrayList saveEnteredData(Object ob)
  {
    logger.info("In saveEnteredData DAOImpl......................");
    PaymentMakerForCMVO paymentVO = (PaymentMakerForCMVO)ob;
    ArrayList list = new ArrayList();
    ArrayList status = new ArrayList();
    String taStatus = null;
    String makerDate = null;
    String para = "";
    String bnkBrchId = "";
    String brancMicr = "";
    String brancIFCS = "";
    String instDate = "";
    String bankId = "";
    if (CommonFunction.checkNull(paymentVO.getTaStatus()).equalsIgnoreCase("on"))
    {
      taStatus = "Y";
      paymentVO.setPaymentMode("S");
    } else {
      taStatus = "N";
    }

    try
    {
      if (CommonFunction.checkNull(taStatus).trim().equalsIgnoreCase("Y"))
      {
        StringBuffer paramval = new StringBuffer();
        paramval.append(" SELECT PARAMETER_VALUE  FROM PARAMETER_MST WHERE PARAMETER_KEY = 'TA_ADJUSTMENT_ACCOUNT' ");

        logger.info("Check paramval  query### " + paramval);
        para = CommonFunction.checkNull(ConnectionDAO.singleReturn(paramval.toString()));
        logger.info("para::" + para);

        String query = "SELECT BANK_ID, BANK_BRANCH_ID, BANK_ACCOUNT, BRANCH_MICR_CODE, BRANCH_IFCS_CODE FROM COM_BANK_ACCOUNTS_M WHERE ACCOUNT_TYPE = 'S' AND REC_STATUS = 'A' and BANK_ACCOUNT ='" + para + "'";
        logger.info("in selecting bankid,bankbranchid,bnkacc,micr,ifcs Query :  " + query);
        ArrayList source = ConnectionDAO.sqlSelect(query);
        int size = source.size();
        logger.info("bnkDetails" + source.size());

        for (int i = 0; i < size; i++)
        {
          ArrayList subEduDetail = (ArrayList)source.get(i);
          if (subEduDetail.size() > 0)
          {
            bankId = CommonFunction.checkNull(subEduDetail.get(0)).toString();
            bnkBrchId = CommonFunction.checkNull(subEduDetail.get(1)).toString();
            para = CommonFunction.checkNull(subEduDetail.get(2)).toString();
            brancMicr = CommonFunction.checkNull(subEduDetail.get(3)).toString();
            brancIFCS = CommonFunction.checkNull(subEduDetail.get(4)).toString();
          }

        }

      }

      String date = null;
      String insDate = null;
      ArrayList in = new ArrayList();
      ArrayList out = new ArrayList();
      ArrayList outMessages = new ArrayList();
      StringBuilder s1 = new StringBuilder();
      StringBuilder s2 = new StringBuilder();
      StringBuilder instrumentId = new StringBuilder();
      try
      {
        in.add("");
        in.add(CommonFunction.checkNull(paymentVO.getLbxLoanNoHID()).trim());
        in.add("LIM");
        in.add(CommonFunction.checkNull(paymentVO.getLbxBPType()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getLbxBPNID()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getPaymentMode()).trim());

        date = CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getPaymentDate()).trim());
        in.add(date);

        in.add(CommonFunction.checkNull(paymentVO.getInstrumentNumber()).trim());

        if (CommonFunction.checkNull(taStatus).trim().equalsIgnoreCase("Y"))
        {
          insDate = CommonFunction.changeFormat(CommonFunction.checkNull(instDate).trim());
          in.add(date);
          in.add(CommonFunction.checkNull(para).trim());
          in.add(CommonFunction.checkNull(bankId).trim());
          in.add(CommonFunction.checkNull(bnkBrchId).trim());
          in.add(CommonFunction.checkNull(brancMicr).trim());
          in.add(CommonFunction.checkNull(brancIFCS).trim());
          in.add(CommonFunction.checkNull(para).trim());
          in.add(CommonFunction.checkNull(bankId).trim());
          in.add(CommonFunction.checkNull(bnkBrchId).trim());
          in.add(CommonFunction.checkNull(brancMicr).trim());
          in.add(CommonFunction.checkNull(brancIFCS).trim());
        }
        else
        {
          insDate = CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getInstrumentDate()).trim());
          if (CommonFunction.checkNull(insDate).trim().equalsIgnoreCase(""))
            in.add(date);
          else {
            in.add(insDate);
          }

          in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryAccountNo()).trim());

          if (CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBankID()).trim().equalsIgnoreCase(""))
            in.add("0");
          else {
            in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBankID()).trim()).toString());
          }
          if (CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBranchID()).trim().equalsIgnoreCase(""))
            in.add("0");
          else
            in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBranchID()).trim()).toString());
          in.add(CommonFunction.checkNull(paymentVO.getMicrCode()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryIfscCode()).trim());

          in.add(CommonFunction.checkNull(paymentVO.getBankAccount()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getLbxBankID()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getLbxBranchID()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getMicr()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getIfsCode()).trim());
        }

        if (CommonFunction.checkNull(paymentVO.getPaymentAmount()).trim().equalsIgnoreCase(""))
          in.add("0.00");
        else {
          in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getPaymentAmount()).trim()).toString());
        }

        if (CommonFunction.checkNull(paymentVO.getTdsAmount()).trim().equalsIgnoreCase(""))
          in.add("0.00");
        else {
          in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getTdsAmount()).trim()).toString());
        }
        in.add(CommonFunction.checkNull(paymentVO.getRemarks()).trim());
        in.add("P");
        in.add("P");
        in.add("N");
        in.add(CommonFunction.checkNull(paymentVO.getDefaultBranch()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getMakerId()).trim());

        makerDate = CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getBusinessDate()).trim());
        in.add(makerDate);
        in.add(CommonFunction.checkNull(paymentVO.getLbxpayTo()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getPayeeName()).trim());
        in.add(CommonFunction.checkNull(taStatus).trim());

        if (!CommonFunction.checkNull(paymentVO.getLbxTaLoanNoHID()).trim().equalsIgnoreCase(""))
          in.add(paymentVO.getLbxTaLoanNoHID());
        else {
          in.add(Integer.valueOf(0));
        }

        in.add(CommonFunction.checkNull(paymentVO.getTaCustomerName()).trim());

        if (CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBankID()).trim().equalsIgnoreCase(""))
          in.add("0");
        else {
          in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBankID()).trim()).toString());
        }
        if (CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBranchID()).trim().equalsIgnoreCase(""))
          in.add("0");
        else {
          in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBranchID()).trim()).toString());
        }

        in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryAccountNo()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryIfscCode()).trim());

        out.add(instrumentId.toString());
        out.add(s1.toString());
        out.add(s2.toString());

        outMessages = (ArrayList)ConnectionDAO.callSP("SAVE_UPDATE_FORWARD_PAYMENT", in, out);

        instrumentId.append(CommonFunction.checkNull(outMessages.get(0)));
        s1.append(CommonFunction.checkNull(outMessages.get(1)));
        s2.append(CommonFunction.checkNull(outMessages.get(2)));
        if (CommonFunction.checkNull(s1).equalsIgnoreCase("S"))
        {
          status.add(0, CommonFunction.checkNull(s1.toString()));
          status.add(1, CommonFunction.checkNull(instrumentId.toString()));
        }
        else
        {
          status.add(0, CommonFunction.checkNull(s2.toString()));
          status.add(1, CommonFunction.checkNull(instrumentId.toString()));
        }
        logger.info("SAVE_UPDATE_FORWARD_PAYMENT instrumentId: " + CommonFunction.checkNull(instrumentId).toString() + " s1: " + CommonFunction.checkNull(s1).toString() + CommonFunction.checkNull(s2).toString());
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        in = null;
        out = null;
        outMessages = null;
        s1 = null;
        s2 = null;
        instrumentId = null;
        taStatus = null;
        makerDate = null;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return status;
  }

  public String getinstrumentId() {
    String instId = "";
    StringBuilder instrumentId = new StringBuilder();
    try {
      instrumentId.append("SELECT max(INSTRUMENT_ID) from cr_instrument_dtl for update");

      instId = ConnectionDAO.singleReturn(instrumentId.toString());
      logger.info("IN paymentId ### " + instrumentId);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      instrumentId = null;
    }

    return instId;
  }

  public String checkOnPaymentForward(PaymentMakerForCMVO paymentMakerForCMVO)
  {
    String status = "";
    StringBuilder query = new StringBuilder();
    StringBuilder query1 = new StringBuilder();
    ArrayList List = new ArrayList();
    String receiptAmount = CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount()).trim();
    logger.info("In receiptAuthorGrid......receiptAmount  " + receiptAmount);
    double receiptAmnt = 0.0D;
    double tdsAmnt = 0.0D;
    try {
      if (receiptAmount.equalsIgnoreCase(""))
        receiptAmount = "0";
      else {
        receiptAmount = this.myFormatter.parse(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount())).toString();
      }
      receiptAmnt = Double.parseDouble(receiptAmount);
      String tdsAmount = CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount()).trim();
      logger.info("In receiptAuthorGrid......tdsAmount  " + tdsAmount);
      if (tdsAmount.equalsIgnoreCase("")) {
        tdsAmount = "0";
      }
      else {
        tdsAmount = this.myFormatter.parse(CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount())).toString();
      }
      tdsAmnt = Double.parseDouble(tdsAmount);
      double Amount = receiptAmnt + tdsAmnt;
      logger.info("In receiptAuthorGrid......Amount  " + Amount);

      query.append(" SELECT ifnull(SUM(PMNT_AMOUNT),0) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()) + "'");
      logger.info("IN checkAllocation  ### " + query);

      String AllocatedAmount = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
      double VAllocatedAmount = 0.0D;
      if (AllocatedAmount.equalsIgnoreCase("")) {
        AllocatedAmount = "0";
      }
      VAllocatedAmount = Double.parseDouble(CommonFunction.checkNull(AllocatedAmount));

      logger.info("VAllocatedAmount----" + VAllocatedAmount);

      query1.append(" SELECT ifnull(SUM(TDS_AMOUNT),0) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()) + "'");
      logger.info("IN checkAllocation 1  ### " + query1);
      String TdsAllocatedAmount = ConnectionDAO.singleReturn(query1.toString());

      double VTdsAllocatedAmount = 0.0D;
      if (TdsAllocatedAmount.equalsIgnoreCase("")) {
        TdsAllocatedAmount = "0";
      }
      VTdsAllocatedAmount = Double.parseDouble(CommonFunction.checkNull(TdsAllocatedAmount));
      logger.info("VTdsAllocatedAmount----" + VTdsAllocatedAmount);

      if (VAllocatedAmount == 0.0D) {
        status = "NA";
      }
      else if ((Amount == VAllocatedAmount) && (tdsAmnt == VTdsAllocatedAmount))
      {
        status = "A";
      }
      else if ((Amount != VAllocatedAmount) || (tdsAmnt != VTdsAllocatedAmount))
      {
        status = "NA";
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      query = null;
      query1 = null;
    }

    return status;
  }
  public int existInsNoinPay(PaymentMakerForCMVO vo) {
    int result = 0;
    StringBuilder existCount = new StringBuilder();
    try {
      existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()) + "'" + 
        " and cid.ISSUEING_BANK_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim()) + "'and cid.ISSUEING_BRANCH_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchID()).trim()) + "'" + 
        " and BPID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()) + "'" + 
        " and BPTYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()) + "' " + 
        " and INSTRUMENT_ID <>'" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim()) + "' and INSTRUMENT_TYPE='P' AND REC_STATUS<>'X'");

      String res = "";
      res = ConnectionDAO.singleReturn(existCount.toString());

      if (!CommonFunction.checkNull(res).equalsIgnoreCase(""))
      {
        result = Integer.parseInt(res);
      }
      logger.info("IN existInsNoinPay  ### " + result);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      existCount = null;
    }

    return result;
  }

  public String saveAllForwardedData(PaymentMakerForCMVO paymentMakerForCMVO, String amount, String tdsAmount)
  {
    boolean status = false;
    logger.info("In saveAllForwardedData,,,,,");

    String procval = "";
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();

    String para = "";
    String bankId = "";
    String bnkBrchId = "";
    String brancMicr = "";
    String brancIFCS = "";
    String taStatus = "";
    if (CommonFunction.checkNull(paymentMakerForCMVO.getTaStatus()).equalsIgnoreCase("on"))
    {
      taStatus = "Y";
      paymentMakerForCMVO.setPaymentMode("S");
    } else {
      taStatus = "N";
    }

    try
    {
      int statusProc = 0;
      in.add(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID().trim()));
      in.add(CommonFunction.checkNull(paymentMakerForCMVO.getLbxBPType().trim()));
      in.add("P");
      in.add(amount);
      in.add(tdsAmount);
      in.add(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID().trim()));
      out.add(s1.toString());
      out.add(s2.toString());
      outMessages = (ArrayList)ConnectionDAO.callSP("Payment_Maker_Validation_M", in, out);
      s1.append(CommonFunction.checkNull(outMessages.get(0)));
      s2.append(CommonFunction.checkNull(outMessages.get(1)));
      logger.info("s1: " + s1);
      logger.info("s2: " + s2);
      logger.info("Status for Proc: " + statusProc);
      logger.info("After Proc: ");

      procval = CommonFunction.checkNull(s2.toString());

      if ((!s1.toString().equalsIgnoreCase("E")) && 
        (!s2.toString().equalsIgnoreCase("F"))) {
        logger.info("After Proc inside If ");

        String makerDate = null;
        String date = null;
        String insDate = null;
        ArrayList frdIn = new ArrayList();
        ArrayList frdOut = new ArrayList();
        ArrayList frdOutMessages = new ArrayList();
        StringBuilder frds1 = new StringBuilder();
        StringBuilder frds2 = new StringBuilder();
        StringBuilder instrumentId = new StringBuilder();

        if (CommonFunction.checkNull(taStatus).equalsIgnoreCase("Y"))
        {
          StringBuffer paramval = new StringBuffer();
          paramval.append(" SELECT PARAMETER_VALUE  FROM PARAMETER_MST WHERE PARAMETER_KEY = 'TA_ADJUSTMENT_ACCOUNT' ");

          logger.info("Check paramval  query### " + paramval);
          para = CommonFunction.checkNull(ConnectionDAO.singleReturn(paramval.toString()));
          logger.info("para::" + para);

          String query = "SELECT BANK_ID, BANK_BRANCH_ID, BANK_ACCOUNT, BRANCH_MICR_CODE, BRANCH_IFCS_CODE FROM COM_BANK_ACCOUNTS_M WHERE ACCOUNT_TYPE = 'S' AND REC_STATUS = 'A' and BANK_ACCOUNT ='" + para + "'";
          logger.info("in selecting bankid,bankbranchid,bnkacc,micr,ifcs Query :  " + query);
          ArrayList source = ConnectionDAO.sqlSelect(query);
          int size = source.size();
          logger.info("bnkDetails" + source.size());

          for (int i = 0; i < size; i++)
          {
            ArrayList subEduDetail = (ArrayList)source.get(i);
            if (subEduDetail.size() > 0)
            {
              bankId = CommonFunction.checkNull(subEduDetail.get(0)).toString();
              bnkBrchId = CommonFunction.checkNull(subEduDetail.get(1)).toString();
              para = CommonFunction.checkNull(subEduDetail.get(2)).toString();
              brancMicr = CommonFunction.checkNull(subEduDetail.get(3)).toString();
              brancIFCS = CommonFunction.checkNull(subEduDetail.get(4)).toString();
            }

          }

        }

        frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim());
        frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim());
        frdIn.add("");
        frdIn.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getLbxBPType()).trim()));
        frdIn.add("0");
        frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode()).trim());

        date = CommonFunction.changeFormat(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentDate()).trim());
        frdIn.add(date);

        frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentNumber()).trim());

        if (CommonFunction.checkNull(taStatus).equalsIgnoreCase("Y"))
        {
          frdIn.add(date);
          frdIn.add(CommonFunction.checkNull(para).trim());
          frdIn.add(CommonFunction.checkNull(bankId).trim());
          frdIn.add(CommonFunction.checkNull(bnkBrchId).trim());
          frdIn.add(CommonFunction.checkNull(brancMicr).trim());
          frdIn.add(CommonFunction.checkNull(brancIFCS).trim());
          frdIn.add(CommonFunction.checkNull(para).trim());
          frdIn.add(CommonFunction.checkNull(bankId).trim());
          frdIn.add(CommonFunction.checkNull(bnkBrchId).trim());
          frdIn.add(CommonFunction.checkNull(brancMicr).trim());
          frdIn.add(CommonFunction.checkNull(brancIFCS).trim());
        }
        else
        {
          insDate = CommonFunction.changeFormat(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentDate()).trim());
          if (CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentDate()).trim().equalsIgnoreCase(""))
            frdIn.add(date);
          else {
            frdIn.add(insDate);
          }

          frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getBeneficiaryAccountNo()).trim());

          if (!CommonFunction.checkNull(paymentMakerForCMVO.getBeneficiaryLbxBankID()).trim().equalsIgnoreCase(""))
            frdIn.add(paymentMakerForCMVO.getBeneficiaryLbxBankID());
          else {
            frdIn.add(Integer.valueOf(0));
          }
          if (!CommonFunction.checkNull(paymentMakerForCMVO.getBeneficiaryLbxBranchID()).trim().equalsIgnoreCase(""))
            frdIn.add(paymentMakerForCMVO.getBeneficiaryLbxBranchID());
          else {
            frdIn.add(Integer.valueOf(0));
          }

          frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getMicrCode()).trim());
          frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getBeneficiaryIfscCode()).trim());
          frdIn.add("");
          frdIn.add("0");
          frdIn.add("0");
          frdIn.add("");
          frdIn.add("");
        }

        if (CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount()).trim().equalsIgnoreCase(""))
          frdIn.add("0.00");
        else {
          frdIn.add(this.myFormatter.parse(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount()).trim()).toString());
        }
        if (CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount()).trim().equalsIgnoreCase(""))
          frdIn.add("0.00");
        else {
          frdIn.add(this.myFormatter.parse(CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount()).trim()).toString());
        }
        frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getRemarks()).trim());
        frdIn.add("F");
        frdIn.add("");
        frdIn.add("");
        frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getDefaultBranch()).trim());
        frdIn.add(paymentMakerForCMVO.getMakerId().trim());

        makerDate = CommonFunction.changeFormat(CommonFunction.checkNull(paymentMakerForCMVO.getBusinessDate().trim()));
        frdIn.add(makerDate);
        frdIn.add("");
        frdIn.add("");
        frdIn.add("");
        if (!CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim().equalsIgnoreCase(""))
          frdIn.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim()));
        else {
          frdIn.add(Integer.valueOf(0));
        }
        frdIn.add("");

        if (!CommonFunction.checkNull(paymentMakerForCMVO.getBeneficiaryLbxBankID()).trim().equalsIgnoreCase(""))
          frdIn.add(paymentMakerForCMVO.getBeneficiaryLbxBankID());
        else {
          frdIn.add(Integer.valueOf(0));
        }
        if (!CommonFunction.checkNull(paymentMakerForCMVO.getBeneficiaryLbxBranchID()).trim().equalsIgnoreCase(""))
          frdIn.add(paymentMakerForCMVO.getBeneficiaryLbxBranchID());
        else {
          frdIn.add(Integer.valueOf(0));
        }
        frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getBeneficiaryAccountNo()).trim());
        frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getBeneficiaryIfscCode()).trim());

        frdOut.add(instrumentId.toString());
        frdOut.add(s1.toString());
        frdOut.add(s2.toString());

        frdOutMessages = (ArrayList)ConnectionDAO.callSP("SAVE_UPDATE_FORWARD_PAYMENT", frdIn, frdOut);

        instrumentId.append(CommonFunction.checkNull(frdOutMessages.get(0)));
        frds1.append(CommonFunction.checkNull(frdOutMessages.get(1)));
        frds2.append(CommonFunction.checkNull(frdOutMessages.get(2)));

        if (CommonFunction.checkNull(frds1).equalsIgnoreCase("S")) {
          status = true;
        }

        if (status)
          procval = "queryexecuted";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      in = null;
      out = null;
      outMessages = null;
      s1 = null;
      s2 = null;
    }

    return procval;
  }

  public boolean updateOnSave(PaymentMakerForCMVO paymentVO)
  {
    boolean status = false;

    String taStatus = "";
    String para = "";
    String bnkBrchId = "";
    String brancMicr = "";
    String brancIFCS = "";
    String instDate = "";
    String bankId = "";
    logger.info("In updateOnSave,,,,,");
    if (CommonFunction.checkNull(paymentVO.getTaStatus()).equalsIgnoreCase("on"))
    {
      taStatus = "Y";
      paymentVO.setPaymentMode("S");
      String instNo = "ADJ-P-";
      instNo = instNo.concat(paymentVO.getInstrumentID());
      paymentVO.setInstrumentNumber(instNo);
      instNo = null;
    } else {
      taStatus = "N";
    }

    try
    {
      if (CommonFunction.checkNull(taStatus) == "Y")
      {
        StringBuffer paramval = new StringBuffer();
        paramval.append(" SELECT PARAMETER_VALUE  FROM PARAMETER_MST WHERE PARAMETER_KEY = 'TA_ADJUSTMENT_ACCOUNT' ");

        logger.info("Check paramval  query### " + paramval);
        para = CommonFunction.checkNull(ConnectionDAO.singleReturn(paramval.toString()));
        logger.info("para::" + para);

        String query = "SELECT BANK_ID, BANK_BRANCH_ID, BANK_ACCOUNT, BRANCH_MICR_CODE, BRANCH_IFCS_CODE FROM COM_BANK_ACCOUNTS_M WHERE ACCOUNT_TYPE = 'S' AND REC_STATUS = 'A' and BANK_ACCOUNT ='" + para + "'";
        logger.info("in selecting bankid,bankbranchid,bnkacc,micr,ifcs Query :  " + query);
        ArrayList source = ConnectionDAO.sqlSelect(query);
        int size = source.size();
        logger.info("bnkDetails" + source.size());

        for (int i = 0; i < size; i++)
        {
          ArrayList subEduDetail = (ArrayList)source.get(i);
          if (subEduDetail.size() > 0)
          {
            bankId = CommonFunction.checkNull(subEduDetail.get(0)).toString();
            bnkBrchId = CommonFunction.checkNull(subEduDetail.get(1)).toString();
            para = CommonFunction.checkNull(subEduDetail.get(2)).toString();
            brancMicr = CommonFunction.checkNull(subEduDetail.get(3)).toString();
            brancIFCS = CommonFunction.checkNull(subEduDetail.get(4)).toString();
          }

        }

      }

      ArrayList queryList = new ArrayList();

      String makerDate = null;
      String date = null;
      String insDate = null;
      ArrayList in = new ArrayList();
      ArrayList out = new ArrayList();
      ArrayList outMessages = new ArrayList();
      StringBuilder s1 = new StringBuilder();
      StringBuilder s2 = new StringBuilder();
      StringBuilder instrumentId = new StringBuilder();
      try
      {
        in.add(CommonFunction.checkNull(paymentVO.getInstrumentID()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getLbxLoanNoHID()).trim());
        in.add("");
        in.add(CommonFunction.checkNull(paymentVO.getLbxBPType()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getLbxBPNID()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getPaymentMode()).trim());

        date = CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getPaymentDate()).trim());
        in.add(date);

        in.add(CommonFunction.checkNull(paymentVO.getInstrumentNumber()).trim());

        if (CommonFunction.checkNull(taStatus) == "Y")
        {
          insDate = CommonFunction.changeFormat(CommonFunction.checkNull(instDate).trim());
          in.add(date);

          in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryAccountNo()).trim());

          if (CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBankID()).trim().equalsIgnoreCase(""))
            in.add("0");
          else {
            in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBankID()).trim()).toString());
          }
          if (CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBranchID()).trim().equalsIgnoreCase(""))
            in.add("0");
          else
            in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBranchID()).trim()).toString());
          in.add(CommonFunction.checkNull(paymentVO.getMicrCode()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryIfscCode()).trim());

          in.add(CommonFunction.checkNull(para).trim());
          in.add(CommonFunction.checkNull(bankId).trim());
          in.add(CommonFunction.checkNull(bnkBrchId).trim());
          in.add(CommonFunction.checkNull(brancMicr).trim());
          in.add(CommonFunction.checkNull(brancIFCS).trim());
        }
        else
        {
          insDate = CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getInstrumentDate()).trim());
          in.add(insDate);

          in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryAccountNo()).trim());

          if (CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBankID()).trim().equalsIgnoreCase(""))
            in.add("0");
          else {
            in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBankID()).trim()).toString());
          }
          if (CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBranchID()).trim().equalsIgnoreCase(""))
            in.add("0");
          else
            in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBranchID()).trim()).toString());
          in.add(CommonFunction.checkNull(paymentVO.getMicrCode()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryIfscCode()).trim());

          in.add(CommonFunction.checkNull(paymentVO.getBankAccount()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getLbxBankID()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getLbxBranchID()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getMicr()).trim());
          in.add(CommonFunction.checkNull(paymentVO.getIfsCode()).trim());
        }

        if (CommonFunction.checkNull(paymentVO.getPaymentAmount()).trim().equalsIgnoreCase(""))
          in.add("0.00");
        else {
          in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getPaymentAmount()).trim()).toString());
        }
        if (CommonFunction.checkNull(paymentVO.getTdsAmount()).trim().equalsIgnoreCase(""))
          in.add("0.00");
        else {
          in.add(this.myFormatter.parse(CommonFunction.checkNull(paymentVO.getTdsAmount()).trim()).toString());
        }
        in.add(CommonFunction.checkNull(paymentVO.getRemarks()).trim());
        in.add("");
        in.add("");
        in.add("");
        in.add(CommonFunction.checkNull(paymentVO.getDefaultBranch()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getMakerId()).trim());

        makerDate = CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getBusinessDate()).trim());
        in.add(makerDate);
        in.add(CommonFunction.checkNull(paymentVO.getLbxpayTo()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getPayeeName()).trim());
        in.add(CommonFunction.checkNull(taStatus).trim());

        if (!CommonFunction.checkNull(paymentVO.getLbxTaLoanNoHID()).trim().equalsIgnoreCase(""))
          in.add(paymentVO.getLbxTaLoanNoHID());
        else {
          in.add(Integer.valueOf(0));
        }

        in.add(CommonFunction.checkNull(paymentVO.getTaCustomerName()).trim());

        if (!CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBankID()).trim().equalsIgnoreCase(""))
          in.add(paymentVO.getBeneficiaryLbxBankID());
        else {
          in.add(Integer.valueOf(0));
        }
        if (!CommonFunction.checkNull(paymentVO.getBeneficiaryLbxBranchID()).trim().equalsIgnoreCase(""))
          in.add(paymentVO.getBeneficiaryLbxBranchID());
        else {
          in.add(Integer.valueOf(0));
        }
        in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryAccountNo()).trim());
        in.add(CommonFunction.checkNull(paymentVO.getBeneficiaryIfscCode()).trim());

        out.add(instrumentId.toString());
        out.add(s1.toString());
        out.add(s2.toString());

        outMessages = (ArrayList)ConnectionDAO.callSP("SAVE_UPDATE_FORWARD_PAYMENT", in, out);

        instrumentId.append(CommonFunction.checkNull(outMessages.get(0)));
        s1.append(CommonFunction.checkNull(outMessages.get(1)));
        s2.append(CommonFunction.checkNull(outMessages.get(2)));

        if (CommonFunction.checkNull(s1).equalsIgnoreCase("S")) {
          status = true;
        }

      }
      catch (Exception e)
      {
        e.printStackTrace();
      } finally {
        taStatus = null;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return status;
  }

  public ArrayList<PaymentMakerForCMVO> getchequeDetail(String bankAccountId, String accountType)
  {
    ArrayList getChequeList = new ArrayList();
    StringBuilder query = new StringBuilder();
    try {
      ArrayList mainList = new ArrayList();
      ArrayList subList = new ArrayList();
      query.append(" SELECT CBA.BANK_ACCOUNT,CBA.BANK_ID,CB.BANK_NAME,CBA.BANK_BRANCH_ID,CBB.BANK_BRANCH_NAME,  CBA.BRANCH_MICR_CODE,CBA.BRANCH_IFCS_CODE FROM com_bank_m CB,com_bankbranch_m CBB,com_bank_accounts_m CBA  WHERE CB.BANK_ID=CBA.BANK_ID AND CBB.BANK_BRANCH_ID=CBA.BANK_BRANCH_ID AND CBA.REC_STATUS= 'A' AND CBA.BANK_ACCOUNT_ID='" + 
        StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankAccountId).trim()) + "'" + 
        " AND ACCOUNT_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(accountType).trim()) + "'");

      logger.info("In getListOfValues" + query);

      mainList = ConnectionDAO.sqlSelect(query.toString());
      for (int i = 0; i < mainList.size(); i++)
      {
        subList = (ArrayList)mainList.get(i);
        if (subList.size() > 0) {
          PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
          paymentVO.setBankAccount(CommonFunction.checkNull(subList.get(0)).trim());
          paymentVO.setLbxBankID(CommonFunction.checkNull(subList.get(1)).trim());
          paymentVO.setBank(CommonFunction.checkNull(subList.get(2)).trim());
          paymentVO.setLbxBranchID(CommonFunction.checkNull(subList.get(3)).trim());
          paymentVO.setBranch(CommonFunction.checkNull(subList.get(4)).trim());
          paymentVO.setMicr(CommonFunction.checkNull(subList.get(5)).trim());
          paymentVO.setIfsCode(CommonFunction.checkNull(subList.get(6)).trim());
          getChequeList.add(paymentVO);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      query = null;
    }

    return getChequeList;
  }

  public boolean deletePayment(String id)
  {
    boolean status = false;
    boolean status1 = false;
    ArrayList list = new ArrayList();
    ArrayList list1 = new ArrayList();
    StringBuilder query = new StringBuilder();
    StringBuilder query1 = new StringBuilder();
    try {
      query.append("delete from cr_instrument_dtl where instrument_id='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim()) + "'");
      list.add(query.toString());
      logger.info("delete()     query------------------" + query.toString());
      status = ConnectionDAO.sqlInsUpdDelete(list);

      query1.append("delete from cr_pmnt_dtl where instrument_id='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim()) + "'");
      list1.add(query1.toString());
      logger.info("delete()     query1------------------" + query1.toString());
      status1 = ConnectionDAO.sqlInsUpdDelete(list1);
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      list.clear();
      list1.clear();
      query = null;
      query1 = null;
    }
    return status;
  }

  public String saveAllForwardedDataWithoutCheck(PaymentMakerForCMVO paymentMakerForCMVO, String amount, String tdsAmount)
  {
    ArrayList getDataList = new ArrayList();
    PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
    StringBuffer sBUpdQry = new StringBuffer();
    boolean status = false;
    String para = "";
    String bnkBrchId = "";
    String brancMicr = "";
    String brancIFCS = "";
    String instDate = "";
    String bankId = "";
    logger.info("In saveAllForwardedDataWithoutCheck,,,,,::::" + paymentMakerForCMVO.getTaStatus());

    String taStatus = "";
    if (CommonFunction.checkNull(paymentMakerForCMVO.getTaStatus()).equalsIgnoreCase("on"))
    {
      taStatus = "Y";
      paymentMakerForCMVO.setPaymentMode("S");
      String instNo = "ADJ-P-";
      instNo = instNo.concat(paymentMakerForCMVO.getInstrumentID());
      paymentMakerForCMVO.setInstrumentNumber(instNo);
      instNo = null;
    } else {
      taStatus = "N";
    }
    ArrayList queryList = new ArrayList();
    ArrayList arrList = new ArrayList();
    String procval = "";
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    StringBuilder getProcessammountQuery = new StringBuilder();
    StringBuilder getAllotedAmtQuery = new StringBuilder();
    try
    {
      if (CommonFunction.checkNull(taStatus).trim().equalsIgnoreCase("Y"))
      {
        StringBuffer paramval = new StringBuffer();
        paramval.append(" SELECT PARAMETER_VALUE  FROM PARAMETER_MST WHERE PARAMETER_KEY = 'TA_ADJUSTMENT_ACCOUNT' ");

        logger.info("Check paramval  query### " + paramval);
        para = CommonFunction.checkNull(ConnectionDAO.singleReturn(paramval.toString()));
        logger.info("para::" + para);

        String query = "SELECT BANK_ID, BANK_BRANCH_ID, BANK_ACCOUNT, BRANCH_MICR_CODE, BRANCH_IFCS_CODE FROM COM_BANK_ACCOUNTS_M WHERE ACCOUNT_TYPE = 'S' AND REC_STATUS = 'A' and BANK_ACCOUNT ='" + para + "'";
        logger.info("in selecting bankid,bankbranchid,bnkacc,micr,ifcs Query :  " + query);
        ArrayList source = ConnectionDAO.sqlSelect(query);
        int size = source.size();
        logger.info("bnkDetails" + source.size());

        for (int i = 0; i < size; i++)
        {
          ArrayList subEduDetail = (ArrayList)source.get(i);
          if (subEduDetail.size() > 0)
          {
            bankId = CommonFunction.checkNull(subEduDetail.get(0)).toString();
            bnkBrchId = CommonFunction.checkNull(subEduDetail.get(1)).toString();
            para = CommonFunction.checkNull(subEduDetail.get(2)).toString();
            brancMicr = CommonFunction.checkNull(subEduDetail.get(3)).toString();
            brancIFCS = CommonFunction.checkNull(subEduDetail.get(4)).toString();
          }

        }

      }

      ArrayList txnAdviceId = new ArrayList();
      ArrayList amtInProcess = new ArrayList();
      ArrayList amtAllocated = new ArrayList();
      ArrayList tdsAmtAllocated = new ArrayList();

      sBUpdQry.append(" update cr_instrument_dtl set ");
      sBUpdQry.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=STR_TO_DATE(?,'" + this.dateFormat + "'),");
      sBUpdQry.append(" INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'" + this.dateFormat + "'), ISSUEING_BANK_ACCOUNT=?,");
      sBUpdQry.append(" ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?, ");
      sBUpdQry.append(" MAKER_REMARKS=?,TDS_AMOUNT=?, REC_STATUS='F',MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '" + this.dateFormatWithTime + "'),INTERVAL CURTIME() HOUR_SECOND),TA_Adjustment_FLAG='" + taStatus + "',TA_LOAN_ID=?,TA_ACCOUNT_NAME=? where TXNID=? ");
      sBUpdQry.append(" AND INSTRUMENT_ID=? ");

      if (CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode()).trim());
      }

      if (CommonFunction.checkNull(paymentMakerForCMVO.getPaymentDate()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentDate()).trim());
      }

      if (CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addString("0.00");
      else {
        updatePrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentNumber()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentNumber()).trim());
      }
      if (CommonFunction.checkNull(taStatus).trim().equalsIgnoreCase("Y"))
      {
        updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentDate()).trim());
        updatePrepStmtObject.addString(CommonFunction.checkNull(para).trim());
        updatePrepStmtObject.addString(CommonFunction.checkNull(bankId).trim());
        updatePrepStmtObject.addString(CommonFunction.checkNull(bnkBrchId).trim());
        updatePrepStmtObject.addString(CommonFunction.checkNull(brancMicr).trim());
        updatePrepStmtObject.addString(CommonFunction.checkNull(brancIFCS).trim());
      }
      else
      {
        if (CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentDate()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentDate()).trim());
        }
        if (CommonFunction.checkNull(paymentMakerForCMVO.getBankAccount()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getBankAccount()).trim());
        }
        if (CommonFunction.checkNull(paymentMakerForCMVO.getLbxBankID()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getLbxBankID()).trim());
        }
        if (CommonFunction.checkNull(paymentMakerForCMVO.getLbxBranchID()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getLbxBranchID()).trim());
        }
        if (CommonFunction.checkNull(paymentMakerForCMVO.getMicr()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getMicr()).trim());
        }

        if (CommonFunction.checkNull(paymentMakerForCMVO.getIfsCode()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getIfsCode()).trim());
        }

      }

      if (CommonFunction.checkNull(paymentMakerForCMVO.getRemarks()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getRemarks()).trim());
      }

      if (CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addString("0.00");
      else {
        updatePrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(paymentMakerForCMVO.getMakerId()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(paymentMakerForCMVO.getMakerId().trim());
      }
      if (CommonFunction.checkNull(paymentMakerForCMVO.getBusinessDate()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(paymentMakerForCMVO.getBusinessDate().trim());
      }
      if (CommonFunction.checkNull(paymentMakerForCMVO.getLbxTaLoanNoHID()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getLbxTaLoanNoHID()).trim());
      }
      if (CommonFunction.checkNull(paymentMakerForCMVO.getTaCustomerName()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getTaCustomerName()).trim());
      }

      if (CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim());
      }

      if (CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim());
      }
      updatePrepStmtObject.setSql(sBUpdQry.toString());

      queryList.add(updatePrepStmtObject);
      logger.info("IN saveAllForwardedData  cr_instrument_dtl query2222 ### " + updatePrepStmtObject.printQuery());

      PrepStmtObject updatePrepStmtObject1 = new PrepStmtObject();
      StringBuffer bufInsSql1 = new StringBuffer();
      StringBuilder inventoryFlagInMst = new StringBuilder();
      StringBuilder existInStationary = new StringBuilder();
      String inventoryFlag = "";
      String checkAllBranch = "";
      String resultStationary = "";
      StringBuilder checkUser = new StringBuilder();
      StringBuilder allBranch = new StringBuilder();
      String checkUserAvailable = "";
      boolean updateStatus = false;
      String IssuingUser = paymentMakerForCMVO.getMakerId();
      logger.info("receipt default branch" + paymentMakerForCMVO.getDefaultBranch());

      inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CHEQUE_INVENTORY_FLAG'");
      logger.info("IN checkReciptStatus inventoryFlag ### " + inventoryFlagInMst.toString());
      inventoryFlag = ConnectionDAO.singleReturn(inventoryFlagInMst.toString());
      if (inventoryFlag.equalsIgnoreCase("Y"))
      {
        allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='" + paymentMakerForCMVO.getInstrumentNumber() + "' AND STATUS='A'");
        logger.info("IN checkAllBranch ### " + allBranch.toString());
        checkAllBranch = ConnectionDAO.singleReturn(allBranch.toString());

        if (!checkAllBranch.equalsIgnoreCase("0"))
        {
          bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U' where RECEPT_CHEQUE_NO='" + paymentMakerForCMVO.getInstrumentNumber() + "'");
          logger.info("IN updateStationary  ### " + bufInsSql1.toString() + paymentMakerForCMVO.getInstrumentNumber());
          updatePrepStmtObject1.setSql(bufInsSql1.toString());
          queryList.add(updatePrepStmtObject1);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
        } else {
          checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + paymentMakerForCMVO.getInstrumentNumber() + "' AND IFNULL(ISSUING_USER,'')<>''");
          logger.info("IN checkInventory inventoryFlag ### " + checkUser.toString());
          checkUserAvailable = ConnectionDAO.singleReturn(checkUser.toString());

          if (!checkUserAvailable.equalsIgnoreCase("0"))
          {
            existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + paymentMakerForCMVO.getInstrumentNumber() + "'  AND IFNULL(ISSUING_USER,'" + IssuingUser + "')='" + IssuingUser + "' ");
            logger.info("IN checkUserAvailability  in existInStationary ### " + existInStationary.toString());
            resultStationary = ConnectionDAO.singleReturn(existInStationary.toString());
          } else {
            existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + paymentMakerForCMVO.getInstrumentNumber() + "'  AND ISSUING_BRANCH='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getDefaultBranch()).trim()) + "'");
            logger.info("IN checkReciptStatus  in existInStationary ### " + existInStationary.toString());
            resultStationary = ConnectionDAO.singleReturn(existInStationary.toString());
          }
          if (!resultStationary.equalsIgnoreCase("0"))
          {
            bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U' where RECEPT_CHEQUE_NO='" + paymentMakerForCMVO.getInstrumentNumber() + "'");
            logger.info("IN updateStationary  ### " + bufInsSql1.toString() + paymentMakerForCMVO.getInstrumentNumber());
            updatePrepStmtObject1.setSql(bufInsSql1.toString());
            queryList.add(updatePrepStmtObject1);
            status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
          }
          else {
            status = false;
          }

          logger.info("In saveForwardUpdateOnReceipt....................." + status);
        }
      } else {
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);

        logger.info("In saveAllForwardedData..........::" + status);
        updatePrepStmtObject1 = null;
        bufInsSql1 = null;
      }

      if (status)
        procval = "queryexecuted";
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      in.clear();
      in = null;
      out.clear();
      out = null;
      outMessages.clear();
      outMessages = null;
      s1 = null;
      s2 = null;
      getProcessammountQuery = null;
      getAllotedAmtQuery = null;
      getDataList.clear();
      getDataList = null;
      updatePrepStmtObject = null;
      paymentMakerForCMVO = null;
      queryList.clear();
      queryList = null;
    }
    return procval;
  }

  public String allocatePayableCheckFlag()
  {
    String flag = "";
    StringBuilder query = new StringBuilder();
    query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='ALLOCATE_PAYABLE_FLAG'");
    logger.info("Query for getting allocatePayableCheckFlag from parameter_mst  : " + query.toString());
    try
    {
      flag = ConnectionDAO.singleReturn(query.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      query = null;
    }
    return flag;
  }

  public String payableAmountCheck(Object ob)
  {
    PaymentMakerForCMVO vo = (PaymentMakerForCMVO)ob;
    String payableAmount = "";
    String status = "E";
    StringBuilder query = new StringBuilder();
    query.append("SELECT IFNULL(L.LOAN_LOAN_AMOUNT,0)-IFNULL(SUM(A.ADVICE_AMOUNT),0) FROM cr_loan_dtl L ");

    query.append(" LEFT JOIN cr_txnadvice_dtl A ON A.LOAN_ID=L.LOAN_ID AND ADVICE_TYPE='P' AND CHARGE_CODE_ID IN(2,50) AND A.REC_STATUS='A' ");
    query.append(" WHERE  L.LOAN_ID='" + vo.getLbxLoanNoHID() + "' ");
    logger.info("Query for getting payableAmountCheck  : " + query.toString());
    try
    {
      payableAmount = ConnectionDAO.singleReturn(query.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    logger.info("payableAmount(L.LOAN_LOAN_AMOUNT-SUM(A.ADVICE_AMOUNT) and ADVICE_TYPE='P'  AND CHARGE_CODE_ID IN(2,50) AND L.REC_STATUS='A': " + payableAmount);
    if (!CommonFunction.checkNull(vo.getPaymentAmount()).trim().equalsIgnoreCase(""))
    {
      try
      {
        Number num = this.myFormatter.parse(CommonFunction.checkNull(vo.getPaymentAmount()).trim());
        logger.info("Payment Amount From Front: " + num.doubleValue());
        Double nemDouble = Double.valueOf(num.doubleValue());
        if (!payableAmount.equalsIgnoreCase(""))
        {
          Double payNum = Double.valueOf(Double.parseDouble(payableAmount));
          if (payNum.compareTo(nemDouble) >= 0)
          {
            status = "S";
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      } finally {
        payableAmount = null;
        query = null;
      }

    }

    return status;
  }

  public String cashPaymentLimit() {
    String amount = null;
    StringBuilder query = new StringBuilder();
    query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CASH_PAYMENT_LIMIT'");
    logger.info("Query for getting cashPaymentLimit from parameter_mst  : " + query.toString());
    try
    {
      amount = ConnectionDAO.singleReturn(query.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      query = null;
    }
    return amount;
  }

  public ArrayList getDataFromInstrumentPayment(String instrumentId, String loanID)
  {
    String dataFromInstrumentDtlQuery = "select BPTYPE,BPID,INSTRUMENT_AMOUNT,TDS_AMOUNT from cr_instrument_dtl where INSTRUMENT_ID='" + instrumentId + "' AND TXN_TYPE='LIM' AND TXNID='" + loanID + "' limit 1";
    logger.info("dataFromInstrumentDtlQuery: " + dataFromInstrumentDtlQuery);
    ArrayList dataFromInstrumentList = null;
    try {
      dataFromInstrumentList = ConnectionDAO.sqlSelect(dataFromInstrumentDtlQuery);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    dataFromInstrumentDtlQuery = null;
    return dataFromInstrumentList;
  }
  public String getLoanRecStatusPayment(String loanID) {
    StringBuilder query = new StringBuilder();
    String loanRecStatus = "";
    try {
      query.append("select rec_status from cr_loan_dtl where loan_id=" + loanID);

      loanRecStatus = ConnectionDAO.singleReturn(query.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      query = null;
    }
    return loanRecStatus;
  }

  public String getCheckDateCountPayment(String instrumentId)
  {
    String checkDateQuery = "select count(*) from cr_instrument_dtl I  inner join cr_pmnt_dtl P on I.INSTRUMENT_ID=P.INSTRUMENT_ID AND P.PMNT_DATE=I.RECEIVED_DATE  where I.INSTRUMENT_ID='" + 
      instrumentId + "' AND INSTRUMENT_TYPE='P' ";

    logger.info("checkDateQuery: " + checkDateQuery);

    String checkDateCount = ConnectionDAO.singleReturn(checkDateQuery);
    checkDateQuery = null;
    return checkDateCount;
  }

  public String getParamValuePayment()
  {
    String paramQuery = "select PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PAYMENT_AUTHORIZATION_FORWARD'";
    String paramValue = ConnectionDAO.singleReturn(paramQuery);
    logger.info("paramQuery: " + paramQuery + " paramValue:  " + paramValue);
    paramValue = null;
    return paramQuery;
  }
}