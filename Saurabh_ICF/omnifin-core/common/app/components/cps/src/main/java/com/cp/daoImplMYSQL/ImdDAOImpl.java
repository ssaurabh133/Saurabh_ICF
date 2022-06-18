package com.cp.daoImplMYSQL;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.ImdDAO;
import com.cp.vo.ChargeVo;
import com.cp.vo.ImdMakerVO;
import com.logger.LoggerMsg;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class ImdDAOImpl
  implements ImdDAO
{
  private static final Logger logger = Logger.getLogger(ImdDAOImpl.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
  String dateFormatWithTime = this.resource.getString("lbl.dateWithTimeInDao");
  String dateFormat = this.resource.getString("lbl.dateInDao");
  String dateForDisbursal = this.resource.getString("lbl.dateForDisbursal");
  DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
  int no = Integer.parseInt(this.resource.getString("msg.pageSizeForMaster"));

  public ArrayList<ImdMakerVO> imdMakerGrid(ImdMakerVO vo)
  {
    logger.info("In imdMakerGrid() of ImdDAOImpl");
    StringBuilder dealNo = new StringBuilder();
    StringBuilder customerName = new StringBuilder();
    StringBuilder businessPartnerType = new StringBuilder();
    StringBuilder businessPartnerID = new StringBuilder();
    StringBuilder receiptAmount = new StringBuilder();
    StringBuilder instrumentNumber = new StringBuilder();
    StringBuilder receiptMode = new StringBuilder();

    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;
    ArrayList receiptMakerSearchGrid = new ArrayList();

    logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ " + vo.getReportingToUserId());
    try
    {
      ArrayList searchlist = new ArrayList();
      logger.info("In imdMakerGrid....................");
      StringBuffer bufInsSql = new StringBuffer();
      StringBuffer bufInsSqlTempCount = new StringBuffer();
      boolean appendSQL = false;

      dealNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()));
      logger.info("In imdMakerGrid......dealNo:::::::  " + dealNo);
      customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
      businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
      businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
      if (!CommonFunction.checkNull(vo.getReceiptAmount()).equalsIgnoreCase(""))
      {
        receiptAmount.append(this.myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
        logger.info("In imdMakerGrid......receiptAmount  " + receiptAmount);
      }
      instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
      receiptMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptMode()).trim()));
      logger.info("In imdMakerGrid......receiptMode  " + receiptMode);

      bufInsSql.append(" SELECT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME ,CID.INSTRUMENT_AMOUNT, ");
      bufInsSql.append("CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID ");
      bufInsSql.append(" FROM CR_DEAL_CUSTOMER_M GCM ,CR_DEAL_DTL CDL, CR_INSTRUMENT_DTL CID, BUSINESS_PARTNER_VIEW BPV,");
      bufInsSql.append(" GENERIC_MASTER GM WHERE CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID  AND BPV.BP_ID=CID.BPID AND  BPV.DEAL_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE ");
      bufInsSql.append(" AND CDL.DEAL_ID=CID.TXNID    AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='P' and INSTRUMENT_TYPE='R' ");
      bufInsSql.append(" AND CID.MAKER_ID='" + vo.getReportingToUserId() + "' AND CID.DEFAULT_BRANCH='" + vo.getBranchId() + "' AND CID.TXN_TYPE='DC' ");

      bufInsSqlTempCount.append(" SELECT  count(1)  FROM CR_DEAL_CUSTOMER_M GCM ,CR_DEAL_DTL CDL, CR_INSTRUMENT_DTL CID, BUSINESS_PARTNER_VIEW BPV,");
      bufInsSqlTempCount.append(" GENERIC_MASTER GM WHERE CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID  AND BPV.BP_ID=CID.BPID AND  BPV.DEAL_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE ");
      bufInsSqlTempCount.append(" AND CDL.DEAL_ID=CID.TXNID    AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='P' and INSTRUMENT_TYPE='R' ");
      bufInsSqlTempCount.append(" AND CID.MAKER_ID='" + vo.getReportingToUserId() + "' AND CID.DEFAULT_BRANCH='" + vo.getBranchId() + "' AND CID.TXN_TYPE='DC' ");

      if (!dealNo.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append(" AND");
        bufInsSql.append(" CDL.DEAL_ID='" + dealNo + "' ");
        bufInsSqlTempCount.append(" AND");
        bufInsSqlTempCount.append(" CDL.DEAL_ID='" + dealNo + "' ");
        appendSQL = true;
      }

      if (!customerName.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append(" AND");
        bufInsSql.append(" GCM.CUSTOMER_NAME LIKE '%" + customerName + "%' ");
        bufInsSqlTempCount.append(" AND");
        bufInsSqlTempCount.append(" GCM.CUSTOMER_NAME LIKE '%" + customerName + "%' ");
        appendSQL = true;
      }

      if (!businessPartnerType.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append(" AND");
        bufInsSql.append(" CID.BPTYPE='" + businessPartnerType.toString().trim() + "' ");
        bufInsSqlTempCount.append(" AND");
        bufInsSqlTempCount.append(" CID.BPTYPE='" + businessPartnerType.toString().trim() + "' ");
        appendSQL = true;
      }

      if (!businessPartnerID.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append(" AND");
        bufInsSql.append(" CID.BPID='" + businessPartnerID + "' ");
        bufInsSqlTempCount.append(" AND");
        bufInsSqlTempCount.append(" CID.BPID='" + businessPartnerID + "' ");
        appendSQL = true;
      }

      if (!receiptAmount.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append(" AND");
        bufInsSql.append(" CID.INSTRUMENT_AMOUNT='" + receiptAmount + "' ");
        bufInsSqlTempCount.append(" AND");
        bufInsSqlTempCount.append(" CID.INSTRUMENT_AMOUNT='" + receiptAmount + "' ");
        appendSQL = true;
      }

      if (!instrumentNumber.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append(" AND");
        bufInsSql.append(" CID.INSTRUMENT_NO='" + instrumentNumber + "' ");
        bufInsSqlTempCount.append(" AND");
        bufInsSqlTempCount.append(" CID.INSTRUMENT_NO='" + instrumentNumber + "' ");
        appendSQL = true;
      }

      if (!receiptMode.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append(" AND");
        bufInsSql.append(" CID.INSTRUMENT_MODE='" + receiptMode + "'");
        bufInsSqlTempCount.append(" AND");
        bufInsSqlTempCount.append(" CID.INSTRUMENT_MODE='" + receiptMode + "'");
        appendSQL = true;
      }

      if (((dealNo.toString().trim() == null) && (customerName.toString().trim() == null) && (businessPartnerType.toString() == null) && (businessPartnerID.toString().trim() == null) && (receiptAmount.toString().trim() == null) && (instrumentNumber.toString().trim() == null) && (receiptMode.toString().trim() == null)) || ((dealNo.toString().trim().equalsIgnoreCase("")) && (customerName.toString().trim().equalsIgnoreCase("")) && (businessPartnerType.toString().trim().equalsIgnoreCase("")) && (businessPartnerID.toString().trim().equalsIgnoreCase("")) && (receiptAmount.toString().trim().equalsIgnoreCase("")) && (instrumentNumber.toString().trim().equalsIgnoreCase("")) && (receiptMode.toString().trim().equalsIgnoreCase(""))) || (vo.getCurrentPageLink() > 1))
      {
        LoggerMsg.info("current PAge Link no .................... " + vo.getCurrentPageLink());
        if (vo.getCurrentPageLink() > 1)
        {
          startRecordIndex = (vo.getCurrentPageLink() - 1) * this.no;
          endRecordIndex = this.no;
          logger.info("startRecordIndex .................... " + startRecordIndex);
          logger.info("endRecordIndex .................... " + endRecordIndex);
        }

        bufInsSql.append(" limit " + startRecordIndex + "," + endRecordIndex);
      }
      else
      {
        startRecordIndex = 0;
        endRecordIndex = this.no;
        bufInsSql.append(" limit " + startRecordIndex + "," + endRecordIndex);
      }

      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

      logger.info("imdMakerGrid search Data size is...." + searchlist.size());
      logger.info("imdMakerGrid    ........main query" + bufInsSql.toString());
      logger.info("imdMakerGrid    .......count query." + bufInsSqlTempCount.toString());
      for (int i = 0; i < searchlist.size(); i++) {
        logger.info("ImdMakerGrid search List " + searchlist.get(i).toString());
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          ImdMakerVO receiptvo = new ImdMakerVO();
          receiptvo.setModifyNo("<a href=imdMakerViewAction.do?method=savedDataOfReceipt&stage=DC&lbxDealNo=" + 
            CommonFunction.checkNull(data.get(0)).toString() + 
            "&instrumentID=" + CommonFunction.checkNull(data.get(10)).toString() + ">" + 
            CommonFunction.checkNull(data.get(1)).toString() + "</a>");

          receiptvo.setLbxDealNo(CommonFunction.checkNull(data.get(0)).trim());
          receiptvo.setDealNo(CommonFunction.checkNull(data.get(1)).trim());
          logger.info("setCustomerName:-------------" + data.get(2));
          receiptvo.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          receiptvo.setLbxBPType(CommonFunction.checkNull(data.get(3)).trim());
          receiptvo.setBusinessPartnerType(CommonFunction.checkNull(data.get(4)).trim());
          receiptvo.setLbxBPNID(CommonFunction.checkNull(data.get(5)).toString());
          logger.info("setLbxBPNID:-------------" + data.get(5));
          receiptvo.setBusinessPartnerName(CommonFunction.checkNull(data.get(6)).trim());
          logger.info("setCustomerName:-------------" + data.get(6));
          Number ReceiptAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(7)).equals(""))
          {
            ReceiptAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(7)).trim());
          }

          logger.info("ReceiptAmount: " + ReceiptAmount);
          receiptvo.setReceiptAmount(this.myFormatter.format(ReceiptAmount));

          receiptvo.setInstrumentNumber(CommonFunction.checkNull(data.get(8)).trim());
          receiptvo.setReceiptMode(CommonFunction.checkNull(data.get(9)).trim());
          if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("C")) {
            receiptvo.setReceiptMode("Cash");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("Q")) {
            receiptvo.setReceiptMode("Cheque");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("D")) {
            receiptvo.setReceiptMode("DD");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("N")) {
            receiptvo.setReceiptMode("NEFT");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("R")) {
            receiptvo.setReceiptMode("RTGS");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("S")) {
            receiptvo.setReceiptMode("Adjustment");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("DIR")) {
            receiptvo.setReceiptMode("Direct Debit");
          }
          receiptvo.setInstrumentID(CommonFunction.checkNull(data.get(10)).trim());

          receiptvo.setTotalRecordSize(count);
          receiptvo.setReportingToUserId(CommonFunction.checkNull(data.get(11)).trim());
          receiptMakerSearchGrid.add(receiptvo);
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      dealNo = null;
      customerName = null;
      businessPartnerType = null;
      businessPartnerID = null;
      receiptAmount = null;
      instrumentNumber = null;
      receiptMode = null;
    }
    return receiptMakerSearchGrid;
  }

  public ArrayList<ImdMakerVO> imdAuthorGrid(ImdMakerVO vo)
  {
    logger.info("In imdAuthorGrid() of ImdDAOImpl");
    StringBuilder dealNo = new StringBuilder();
    StringBuilder businessPartnerType = new StringBuilder();
    StringBuilder businessPartnerID = new StringBuilder();
    StringBuilder receiptAmount = new StringBuilder();
    StringBuilder instrumentNumber = new StringBuilder();
    StringBuilder customerName = new StringBuilder();
    StringBuilder receiptMode = new StringBuilder();

    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;

    ArrayList authordetailList = new ArrayList();

    logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ " + vo.getReportingToUserId());
    try
    {
      ArrayList searchlist = new ArrayList();
      logger.info("In imdAuthorGrid....................");
      StringBuffer bufInsSql = new StringBuffer();
      StringBuffer bufInsSqlTempCount = new StringBuffer();
      boolean appendSQL = false;
      dealNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()));
      logger.info("In imdMakerGrid......dealNo:::::::  " + dealNo);
      customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
      businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
      businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
      if (!CommonFunction.checkNull(vo.getReceiptAmount()).equalsIgnoreCase(""))
      {
        receiptAmount.append(this.myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
        logger.info("In imdAuthorGrid......imdAmount  " + receiptAmount);
      }
      instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
      receiptMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptMode()).trim()));
      logger.info("In imdAuthorGrid......imdMode  " + receiptMode);

      bufInsSql.append("SELECT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME ,CID.INSTRUMENT_AMOUNT, CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID FROM CR_DEAL_CUSTOMER_M GCM ,CR_DEAL_DTL CDL, CR_INSTRUMENT_DTL CID, BUSINESS_PARTNER_VIEW BPV, GENERIC_MASTER GM WHERE CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID AND BPV.BP_ID=CID.BPID AND  BPV.DEAL_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE AND CDL.DEAL_ID=CID.TXNID    AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='F' and INSTRUMENT_TYPE='R' AND CID.TXN_TYPE='DC'");

      bufInsSqlTempCount.append("SELECT DISTINCT COUNT(1)FROM( SELECT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME , CID.INSTRUMENT_AMOUNT,CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID FROM CR_DEAL_CUSTOMER_M GCM ,CR_DEAL_DTL CDL, CR_INSTRUMENT_DTL CID, BUSINESS_PARTNER_VIEW BPV, GENERIC_MASTER GM WHERE CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID AND BPV.BP_ID=CID.BPID AND  BPV.DEAL_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE AND CDL.DEAL_ID=CID.TXNID    AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='F' and INSTRUMENT_TYPE='R' AND CID.TXN_TYPE='DC'");

      if ((dealNo.toString().trim().equalsIgnoreCase("")) || (customerName.toString().trim().equalsIgnoreCase("")) || (businessPartnerType.toString().trim().equalsIgnoreCase("")) || (businessPartnerID.toString().trim().equalsIgnoreCase("")) || (receiptAmount.toString().trim().equalsIgnoreCase("")) || (instrumentNumber.toString().trim().equalsIgnoreCase("")) || (receiptMode.toString().trim().equalsIgnoreCase("")) || (vo.getLbxUserId().equalsIgnoreCase(""))) {
        appendSQL = true;
      }

      if (appendSQL) {
        logger.info("In Where Clause");
        bufInsSql.append(" AND CID.DEFAULT_BRANCH='" + vo.getBranchId() + "' AND CID.MAKER_ID!='" + vo.getUserId() + "'");
        bufInsSqlTempCount.append("  AND CID.DEFAULT_BRANCH='" + vo.getBranchId() + "' AND CID.MAKER_ID!='" + vo.getUserId() + "'");
      }

      if (!dealNo.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("AND CDL.DEAL_ID='" + dealNo + "' ");
        bufInsSqlTempCount.append("AND CDL.DEAL_ID='" + dealNo + "' ");
        appendSQL = true;
      }

      if (!customerName.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("AND GCM.CUSTOMER_NAME LIKE '%" + customerName + "%' ");
        bufInsSqlTempCount.append("AND GCM.CUSTOMER_NAME LIKE '%" + customerName + "%' ");
        appendSQL = true;
      }

      if (!businessPartnerType.toString().trim().trim().equalsIgnoreCase("")) {
        bufInsSql.append("AND CID.BPTYPE='" + businessPartnerType.toString().trim() + "' ");
        bufInsSqlTempCount.append("AND CID.BPTYPE='" + businessPartnerType.toString().trim() + "' ");
        appendSQL = true;
      }

      if (!businessPartnerID.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("AND CID.BPID='" + businessPartnerID + "' ");
        bufInsSqlTempCount.append("AND CID.BPID='" + businessPartnerID + "' ");
        appendSQL = true;
      }

      if (!receiptAmount.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("AND CID.INSTRUMENT_AMOUNT='" + receiptAmount + "' ");
        bufInsSqlTempCount.append("AND CID.INSTRUMENT_AMOUNT='" + receiptAmount + "' ");
        appendSQL = true;
      }

      if (!instrumentNumber.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("AND CID.INSTRUMENT_NO='" + instrumentNumber + "'");
        bufInsSqlTempCount.append("AND CID.INSTRUMENT_NO='" + instrumentNumber + "'");
        appendSQL = true;
      }

      if (!receiptMode.toString().trim().equalsIgnoreCase("")) {
        bufInsSql.append("AND CID.INSTRUMENT_MODE='" + receiptMode + "'");
        bufInsSqlTempCount.append("AND CID.INSTRUMENT_MODE='" + receiptMode + "'");
        appendSQL = true;
      }
      if (!vo.getLbxUserId().equalsIgnoreCase("")) {
        bufInsSql.append(" AND CID.MAKER_ID='" + vo.getLbxUserId() + "'");
        bufInsSqlTempCount.append("AND CID.MAKER_ID='" + vo.getLbxUserId() + "'");
        appendSQL = true;
      }
      bufInsSqlTempCount.append(" )AS B");

      logger.info("In appendSQL true---- " + appendSQL);

      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

      if (((dealNo.toString().trim() == null) && (customerName.toString().trim() == null) && (businessPartnerType.toString() == null) && (businessPartnerID.toString().trim() == null) && (receiptAmount.toString().trim() == null) && (instrumentNumber.toString().trim() == null) && (receiptMode.toString().trim() == null)) || ((dealNo.toString().trim().equalsIgnoreCase("")) && (customerName.toString().trim().equalsIgnoreCase("")) && (businessPartnerType.toString().trim().equalsIgnoreCase("")) && (businessPartnerID.toString().trim().equalsIgnoreCase("")) && (receiptAmount.toString().trim().equalsIgnoreCase("")) && (instrumentNumber.toString().trim().equalsIgnoreCase("")) && (receiptMode.toString().trim().equalsIgnoreCase(""))) || (vo.getCurrentPageLink() > 1))
      {
        LoggerMsg.info("current PAge Link no .................... " + vo.getCurrentPageLink());
        if (vo.getCurrentPageLink() > 1)
        {
          startRecordIndex = (vo.getCurrentPageLink() - 1) * this.no;
          endRecordIndex = this.no;
          LoggerMsg.info("startRecordIndex .................... " + startRecordIndex);
          LoggerMsg.info("endRecordIndex .................... " + endRecordIndex);
        }

        bufInsSql.append(" limit " + startRecordIndex + "," + endRecordIndex);
      }

      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

      logger.info("imdAuthorGrid search Data size is...." + searchlist.size());
      logger.info("imdAuthorGrid    ........" + bufInsSql.toString());

      for (int i = 0; i < searchlist.size(); i++) {
        logger.info("imdAuthorGrid search List " + searchlist.get(i).toString());
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          ImdMakerVO receiptVO = new ImdMakerVO();
          receiptVO.setModifyNo("<a href=imdAuthorProcessAction.do?method=getReceipttoApprove&lbxLoanNoHID=" + 
            CommonFunction.checkNull(data.get(0)).toString() + 
            "&instrumentID=" + CommonFunction.checkNull(data.get(10)).toString() + 
            "&lbxBPType=" + CommonFunction.checkNull(data.get(3)).toString() + ">" + 
            CommonFunction.checkNull(data.get(1)).toString() + "</a>");

          receiptVO.setLbxLoanNoHID(CommonFunction.checkNull(data.get(0)).trim());
          receiptVO.setLoanAccountNumber(CommonFunction.checkNull(data.get(1)).trim());
          logger.info("imdAuthorGrid-------------" + data.get(2));
          receiptVO.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          receiptVO.setLbxBPType(CommonFunction.checkNull(data.get(3)).trim());
          receiptVO.setBusinessPartnerType(CommonFunction.checkNull(data.get(4)).trim());
          receiptVO.setLbxBPNID(CommonFunction.checkNull(data.get(5)).trim());
          receiptVO.setBusinessPartnerName(CommonFunction.checkNull(data.get(6)).trim());
          Number ReceiptAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(7)).equals(""))
          {
            ReceiptAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(7)).trim());
          }
          logger.info("ReceiptAmount: " + ReceiptAmount);
          receiptVO.setReceiptAmount(this.myFormatter.format(ReceiptAmount));

          receiptVO.setInstrumentNumber(CommonFunction.checkNull(data.get(8)).trim());
          receiptVO.setReceiptMode(CommonFunction.checkNull(data.get(9)).trim());
          if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("C")) {
            receiptVO.setReceiptMode("Cash");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("Q")) {
            receiptVO.setReceiptMode("Cheque");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("D")) {
            receiptVO.setReceiptMode("DD");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("N")) {
            receiptVO.setReceiptMode("NEFT");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("R")) {
            receiptVO.setReceiptMode("RTGS");
          }
          else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("S")) {
            receiptVO.setReceiptMode("Adjustment");
          }
          receiptVO.setInstrumentID(CommonFunction.checkNull(data.get(10)));

          receiptVO.setTotalRecordSize(count);
          receiptVO.setReportingToUserId(CommonFunction.checkNull(data.get(11)));
          authordetailList.add(receiptVO);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      dealNo = null;
      businessPartnerType = null;
      businessPartnerID = null;
      receiptAmount = null;
      instrumentNumber = null;
      customerName = null;
      receiptMode = null;
    }
    return authordetailList;
  }

  public ArrayList<ImdMakerVO> getImdList(ImdMakerVO vo)
  {
    logger.info("In getImdList() of ImdDAOImpl");

    ArrayList savedReceipt = new ArrayList();
    try {
      ArrayList searchlist = new ArrayList();
      logger.info("In savedReceipt....................");
      StringBuffer bufInsSql = new StringBuffer();

      bufInsSql.append(" SELECT DISTINCT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE,GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME ,   CID.INSTRUMENT_MODE,date_format(CID.RECEIVED_DATE,'%d-%m-%Y'),");
      bufInsSql.append("CID.INSTRUMENT_AMOUNT,date_format(CID.INSTRUMENT_DATE,'%d-%m-%Y'),CID.INSTRUMENT_NO, CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,");
      bufInsSql.append("CID.ISSUEING_BRANCH_ID  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME, CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.REMARKS,CID.INSTRUMENT_ID,CID.TDS_AMOUNT,");
      bufInsSql.append("CID.RECIPT_NO,CID.MAKER_REMARKS,CDL.REC_STATUS,CID.RECEIPT_PURPOSE,TRANSACTION_REF,CDL.DEAL_BRANCH,CID.MANUAL_AUTO_FLAG  from cr_instrument_dtl CID,cr_deal_customer_m GCM , cr_deal_dtl CDL,business_partner_view BPV,generic_master  GM ");
      bufInsSql.append("where CID.TXNID=CDL.DEAL_ID  AND BPV.BP_ID=CID.BPID AND BPV.DEAL_ID=CID.TXNID AND CID.BPTYPE=BPV.BP_TYPE AND GM.VALUE=CID.BPTYPE and CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID  AND GM.GENERIC_KEY='BPTYPE'   and INSTRUMENT_TYPE='R'AND CID.REC_STATUS='P'");
      bufInsSql.append(" and ifnull(CID.TXN_TYPE,'')='DC' AND CID.TXNID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()) + "' ");
      bufInsSql.append(" AND CID.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim()) + "' ");

      logger.info("In savedReceipt......... query111..........." + bufInsSql.toString());
      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      logger.info("savedReceipt search Data size is...." + searchlist.size());

      for (int i = 0; i < searchlist.size(); i++) {
        logger.info("savedReceipt search List " + searchlist.get(i).toString());
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          ImdMakerVO receiptvo = new ImdMakerVO();
          receiptvo.setLbxDealNo(CommonFunction.checkNull(data.get(0)).trim());
          receiptvo.setDealNo(CommonFunction.checkNull(data.get(1)).trim());
          logger.info(" savedReceipt setLbxDealNo:-------------" + receiptvo.getLbxDealNo());
          receiptvo.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          logger.info(" savedReceipt setCustomerName:-------------" + data.get(2));
          receiptvo.setLbxBPType(CommonFunction.checkNull(data.get(3)).trim());
          receiptvo.setBusinessPartnerType(CommonFunction.checkNull(data.get(4)).trim());
          logger.info("setBusinessPartnerType: " + data.get(4));
          receiptvo.setLbxBPNID(CommonFunction.checkNull(data.get(5)).trim());
          receiptvo.setBusinessPartnerName(CommonFunction.checkNull(data.get(6)).trim());
          if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("Q")) {
            receiptvo.setLbxreceiptMode("B");
          }
          else if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("D")) {
            receiptvo.setLbxreceiptMode("B");
          }
          else if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("N")) {
            receiptvo.setLbxreceiptMode("B");
          }
          else if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("R")) {
            receiptvo.setLbxreceiptMode("B");
          }
          else if (CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("S")) {
            receiptvo.setLbxreceiptMode("S");
          }
          receiptvo.setReceiptMode(CommonFunction.checkNull(data.get(7)).trim());
          receiptvo.setReceiptDate(CommonFunction.checkNull(data.get(8)).trim());
          Number ReceiptAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(9)).equals(""))
          {
            ReceiptAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(9)).trim());
          }
          logger.info("ReceiptAmount: " + ReceiptAmount);
          receiptvo.setReceiptAmount(this.myFormatter.format(ReceiptAmount));
          receiptvo.setInstrumentDate(CommonFunction.checkNull(data.get(10)).trim());
          receiptvo.setInstrumentNumber(CommonFunction.checkNull(data.get(11)).trim());
          receiptvo.setBankAccount(CommonFunction.checkNull(data.get(12)).trim());
          receiptvo.setLbxBankID(CommonFunction.checkNull(data.get(13)).trim());
          logger.info(" savedReceipt setLbxBankID:-------------" + data.get(13));
          receiptvo.setBank(CommonFunction.checkNull(data.get(14)).trim());
          receiptvo.setLbxBranchID(CommonFunction.checkNull(data.get(15)).trim());
          logger.info(" savedReceipt setLbxBranchID:-------------" + data.get(15));
          receiptvo.setBranch(CommonFunction.checkNull(data.get(16)).trim());
          receiptvo.setMicr(CommonFunction.checkNull(data.get(17)).trim());
          receiptvo.setIfsCode(CommonFunction.checkNull(data.get(18)).trim());
          receiptvo.setAuthorRemarks(CommonFunction.checkNull(data.get(19)).trim());
          receiptvo.setInstrumentID(CommonFunction.checkNull(data.get(20)).trim());
          logger.info(" savedReceipt setInstrumentID:-------------" + data.get(20));
          Number TdsAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(21)).equalsIgnoreCase(""))
          {
            TdsAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(21)).trim());
            logger.info("TdsAmount: " + TdsAmount);
          }

          receiptvo.setTdsAmount(this.myFormatter.format(TdsAmount));
          receiptvo.setReceiptNo(CommonFunction.checkNull(data.get(22)).trim());
          receiptvo.setRemarks(CommonFunction.checkNull(data.get(23)).trim());
          receiptvo.setLoanRecStatus(CommonFunction.checkNull(data.get(24)).trim());
          receiptvo.setPurpose(CommonFunction.checkNull(data.get(25)).trim());
          logger.info("setPurpose::::::::::::" + receiptvo.getPurpose());
          receiptvo.setTransactionRefNo(CommonFunction.checkNull(data.get(26)).trim());
          receiptvo.setLoanBranch(CommonFunction.checkNull(data.get(27)).trim());
          receiptvo.setStatusReceipt(CommonFunction.checkNull(data.get(28)).trim());
          savedReceipt.add(receiptvo);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return savedReceipt;
  }

  public ArrayList<ImdMakerVO> receiptdatatoApprove(ImdMakerVO vo) {
    logger.info("In receiptdatatoApprove() of ReceiptDAOImpl");

    ArrayList receiptApproveList = new ArrayList();
    try {
      ArrayList searchlist = new ArrayList();
      logger.info("In searchAuthorData....................");
      StringBuffer bufInsSql = new StringBuffer();

      bufInsSql.append(" SELECT DISTINCT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, ");
      bufInsSql.append("   GM.DESCRIPTION,CID.BPID ,CDL.REC_STATUS , ");
      bufInsSql.append("  CID.INSTRUMENT_MODE,date_format(CID.RECEIVED_DATE,'" + this.dateFormat + "'),CID.INSTRUMENT_AMOUNT,date_format(CID.INSTRUMENT_DATE,'" + this.dateFormat + "'),CID.INSTRUMENT_NO,");
      bufInsSql.append(" CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID");
      bufInsSql.append("  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME,");
      bufInsSql.append(" CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.REMARKS,CID.INSTRUMENT_ID,CID.TDS_AMOUNT,CID.RECIPT_NO,CID.MAKER_REMARKS,CDL.REC_STATUS,(select DESCRIPTION from generic_master where value=CID.RECEIPT_PURPOSE AND GENERIC_KEY='RECEIPT_PURPOSE' ) as RECEIPT_PURPOSE,CID.TRANSACTION_REF,CDL.DEAL_BRANCH,CID.MANUAL_AUTO_FLAG");
      bufInsSql.append("  from cr_instrument_dtl CID,cr_deal_customer_m GCM ,");
      bufInsSql.append(" cr_deal_dtl CDL, generic_master  GM");
      bufInsSql.append(" where CID.TXNID=CDL.DEAL_ID");
      bufInsSql.append("  AND GM.VALUE=CID.BPTYPE and CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID ");
      bufInsSql.append("  AND GM.GENERIC_KEY='BPTYPE' ");
      bufInsSql.append("  and INSTRUMENT_TYPE='R'AND CID.REC_STATUS='F'");
      bufInsSql.append(" and ifnull(CID.TXN_TYPE,'')='DC' AND CID.TXNID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()) + "' ");
      bufInsSql.append(" AND CID.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim()) + "' ");

      logger.info("In savedReceipt......... query..........." + bufInsSql.toString());
      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      logger.info("savedReceipt search Data size is...." + searchlist.size());

      for (int i = 0; i < searchlist.size(); i++) {
        logger.info("savedReceipt search List " + searchlist.get(i).toString());
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          ImdMakerVO receiptvo = new ImdMakerVO();

          receiptvo.setLbxDealNo(CommonFunction.checkNull(data.get(0)).trim());
          receiptvo.setDealNo(CommonFunction.checkNull(data.get(1)).trim());
          receiptvo.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          logger.info(" savedReceipt setCustomerName:-------------" + data.get(2));
          receiptvo.setLbxBPType(CommonFunction.checkNull(data.get(3)).trim());
          receiptvo.setBusinessPartnerType(CommonFunction.checkNull(data.get(4)).trim());
          receiptvo.setLbxBPNID(CommonFunction.checkNull(data.get(5)).trim());

          receiptvo.setReceiptMode(CommonFunction.checkNull(data.get(7)).trim());
          receiptvo.setReceiptDate(CommonFunction.checkNull(data.get(8)).trim());
          Number ReceiptAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(9)).equals(""))
          {
            ReceiptAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(9)).trim());
          }
          logger.info("ReceiptAmount: " + ReceiptAmount);
          receiptvo.setReceiptAmount(this.myFormatter.format(ReceiptAmount));
          receiptvo.setInstrumentDate(CommonFunction.checkNull(data.get(10)).trim());
          receiptvo.setInstrumentNumber(CommonFunction.checkNull(data.get(11)).trim());
          receiptvo.setBankAccount(CommonFunction.checkNull(data.get(12)).trim());
          receiptvo.setLbxBankID(CommonFunction.checkNull(data.get(13)).trim());
          logger.info(" savedReceipt setLbxBankID:-------------" + data.get(13));
          receiptvo.setBank(CommonFunction.checkNull(data.get(14)).trim());
          receiptvo.setLbxBranchID(CommonFunction.checkNull(data.get(15)).trim());
          logger.info(" savedReceipt setLbxBranchID:-------------" + data.get(15));
          receiptvo.setBranch(CommonFunction.checkNull(data.get(16)).trim());
          receiptvo.setMicr(CommonFunction.checkNull(data.get(17)).trim());
          receiptvo.setIfsCode(CommonFunction.checkNull(data.get(18)).trim());
          receiptvo.setAuthorRemarks(CommonFunction.checkNull(data.get(19)).trim());
          receiptvo.setInstrumentID(CommonFunction.checkNull(data.get(20)).trim());

          logger.info(" savedReceipt setInstrumentID:-------------" + data.get(20));
          Number TdsAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(data.get(21)).equalsIgnoreCase(""))
          {
            TdsAmount = this.myFormatter.parse(CommonFunction.checkNull(data.get(21)).trim());
            logger.info("TdsAmount: " + TdsAmount);
          }

          receiptvo.setTdsAmount(this.myFormatter.format(TdsAmount));
          receiptvo.setReceiptNo(CommonFunction.checkNull(data.get(22)).trim());
          receiptvo.setRemarks(CommonFunction.checkNull(data.get(23)).trim());

          receiptvo.setLoanRecStatus(CommonFunction.checkNull(data.get(24)).trim());
          receiptvo.setPurpose(CommonFunction.checkNull(data.get(25)).trim());
          receiptvo.setTransactionRefNo(CommonFunction.checkNull(data.get(26)).trim());
          receiptvo.setLoanBranch(CommonFunction.checkNull(data.get(27)).trim());
          receiptvo.setStatusReceipt(CommonFunction.checkNull(data.get(28)).trim());
          receiptApproveList.add(receiptvo);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return receiptApproveList;
  }

  public boolean saveViewReceivable(ImdMakerVO receiptVO)
  {
    boolean status = false;
    StringBuilder bptype = new StringBuilder();
    StringBuilder loanId = new StringBuilder();
    StringBuilder deleteCount = new StringBuilder();
    StringBuilder data = new StringBuilder();
    try
    {
      ArrayList queryList = new ArrayList();
      ArrayList subList = new ArrayList();
      ArrayList subList1 = new ArrayList();
      ArrayList list = new ArrayList();
      ArrayList dataList = new ArrayList();
      PrepStmtObject insertPrepStmtObject = null;
      StringBuffer bufInsSql = null;

      String[] allotedAmount = receiptVO.getAllocatedArry();
      String[] adviseId = receiptVO.getTxnAdvicedIDArry();
      String[] chargeCode = receiptVO.getChargeCodeIDArry();
      String[] tdsAllotedAmount = receiptVO.getTdsAllocatedArry();

      String deletePmntDtlQuery = "SELECT COUNT(1) FROM cr_imd_dtl WHERE INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()) + "'";
      logger.info("In saveViewReceivable..deletePmntDtlQuery. " + deletePmntDtlQuery);
      String deletePmntDtlCount = ConnectionDAO.singleReturn(deletePmntDtlQuery);
      logger.info("In saveViewReceivable... deletePmntDtlCount" + deletePmntDtlCount);

      if (!deletePmntDtlCount.equalsIgnoreCase("0"))
      {
        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql = new StringBuffer();
        bufInsSql.append(" DELETE FROM cr_imd_dtl WHERE INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()) + "'");
        logger.info("### bufInsSql delete PMNT Query #### " + bufInsSql.toString());
        insertPrepStmtObject.setSql(bufInsSql.toString());
        queryList.add(insertPrepStmtObject);
      }

      status = true;

      for (int i = 0; i < adviseId.length; i++)
      {
        insertPrepStmtObject = null;
        bufInsSql = null;
        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql = new StringBuffer();
        String alloc = CommonFunction.checkNull(allotedAmount[i].trim());

        if (alloc.equalsIgnoreCase(""))
        {
          alloc = "0";
        }
        double allocatedAmnt = 0.0D;
        allocatedAmnt = this.myFormatter.parse(alloc).doubleValue();
        logger.info("### In double adviseId #### " + adviseId);
        logger.info("### In double allocatedAmnt #### " + allocatedAmnt);

        if (allocatedAmnt > 0.0D)
        {
          status = false;

          bufInsSql.append("insert into cr_imd_dtl( IMD_DATE,DEAL_ID,DEAL_CHARGE_DTL_ID,DEAL_CHARGE_CODE,IMD_AMOUNT,INSTRUMENT_ID,REC_STATUS,MAKER_ID,TDS_AMOUNT ,MAKER_DATE)");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" (select RECEIVED_DATE from cr_instrument_dtl where INSTRUMENT_ID=? limit 1),");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" 'P',");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + this.dateFormatWithTime + "'),INTERVAL CURTIME() HOUR_SECOND))");

          if (CommonFunction.checkNull(receiptVO.getInstrumentID()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim());
          if (CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim());
          if (CommonFunction.checkNull(adviseId[i]).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(adviseId[i]).trim());
          if (CommonFunction.checkNull(chargeCode[i]).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(chargeCode[i]).trim());
          if (CommonFunction.checkNull(allotedAmount[i]).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(this.myFormatter.parse(allotedAmount[i].trim()).toString());
          if (CommonFunction.checkNull(receiptVO.getInstrumentID()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim());
          if (CommonFunction.checkNull(receiptVO.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getMakerId()).trim());
          }
          insertPrepStmtObject.addString("0.00");

          if (CommonFunction.checkNull(receiptVO.getBusinessDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
          }
          insertPrepStmtObject.setSql(bufInsSql.toString());
          queryList.add(insertPrepStmtObject);

          logger.info("In insert of  cr_imd_dtl" + insertPrepStmtObject.printQuery());

          insertPrepStmtObject = new PrepStmtObject();
          bufInsSql = new StringBuffer();
        }

      }

      if (queryList.size() > 0) {
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
      }
      logger.info("In saveViewReceivable,,,,," + status);
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

  public String existReceiptData(Object ob)
  {
    String result = "";
    ImdMakerVO vo = (ImdMakerVO)ob;
    StringBuilder existCount = new StringBuilder();
    try {
    	if(CommonFunction.checkNull(vo.getLbxBPType()).equalsIgnoreCase("")){
    		vo.setLbxBPType("CS");
    		
    	}
      existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()) + "'" + 
        " and cid.ISSUEING_BANK_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim()) + "'and cid.ISSUEING_BRANCH_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchID()).trim()) + "' " + 
        " and BPID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()) + "'" + 
        " and BPTYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()) + "' and INSTRUMENT_TYPE='R' AND REC_STATUS<>'X'");

      result = ConnectionDAO.singleReturn(existCount.toString());
      logger.info("IN existReceiptData  ### " + existCount);
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
  public String existReceiptForNR(Object ob) {
    String result = "";
    ImdMakerVO vo = (ImdMakerVO)ob;
    StringBuilder existCount = new StringBuilder();
    try {
      existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()) + "'" + 
        " and cid.ISSUEING_BANK_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim()) + "' " + 
        " and BPID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()) + "'" + 
        " and BPTYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()) + "' and INSTRUMENT_TYPE='R' AND REC_STATUS<>'X'");

      result = ConnectionDAO.singleReturn(existCount.toString());
      logger.info("IN existReceiptForNR Query--------- ### " + existCount);
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

  public boolean saveImdData(Object ob) {
    ImdMakerVO vo = (ImdMakerVO)ob;
    vo.getDefaultBranch();
    logger.info("defaultBranch amandeep testing 123" + vo.getDefaultBranch());

    ArrayList getDataList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    boolean status = false;
    boolean status1 = false;
    logger.info("In saveReceiptData DAOImpl......................");
    ArrayList qryList = new ArrayList();
    try
    {
      StringBuffer query = new StringBuffer();
      query.append(" SELECT COUNT(1) FROM BUSINESS_PARTNER_VIEW WHERE BP_TYPE='CS' AND LOAN_ID='0' AND DEAL_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()) + "'");
      logger.info("Check Deal Existence in  BUSINESS_PARTNER_VIEW  query### " + query);
      String DealExistence = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
      logger.info("In saveReceiptData DAOImpl DealExistence....." + DealExistence);
      int count = Integer.parseInt(DealExistence);
      boolean deleteStatus = false;
      if (count > 0) {
        logger.info("in the delete statement count:::::::" + count);
        StringBuilder query1 = new StringBuilder();
        query1.append("Delete from BUSINESS_PARTNER_VIEW WHERE BP_TYPE='CS' AND LOAN_ID='0' and  DEAL_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()) + "'");
        ArrayList qryList1 = new ArrayList();
        qryList1.add(query1);
        deleteStatus = ConnectionDAO.sqlInsUpdDelete(qryList1);
        logger.info("Deletion Status :" + deleteStatus);
        query1 = null;
      }

      if ((deleteStatus) || (DealExistence.equalsIgnoreCase("0")))
      {
        StringBuffer bufInsSql1 = new StringBuffer();
        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql1.append("INSERT INTO BUSINESS_PARTNER_VIEW (DEAL_ID,LOAN_ID,BP_TYPE,BP_ID,BP_NAME)");
        bufInsSql1.append(" values ( ");
        bufInsSql1.append(" ?,");
        bufInsSql1.append(" '0',");
        bufInsSql1.append(" 'CS',");
        bufInsSql1.append(" ?,");
        bufInsSql1.append(" ? )");

        if (CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getLbxDealNo()).trim());
        if (CommonFunction.checkNull(vo.getLbxBPNID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getLbxBPNID()).trim());
        if (CommonFunction.checkNull(vo.getCustomerName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getCustomerName()).trim());
        }
        insertPrepStmtObject.setSql(bufInsSql1.toString());
        logger.info("IN SaveDatafor() insert BUSINESS_PARTNER_VIEW query ### " + insertPrepStmtObject.printQuery());
        qryList.add(insertPrepStmtObject);
      }
      StringBuffer bufInsSql = new StringBuffer();
      insertPrepStmtObject = new PrepStmtObject();
      bufInsSql.append("insert into cr_instrument_dtl (TXNID,TXN_TYPE,BPTYPE,BPID,INSTRUMENT_MODE,RECEIVED_DATE, INSTRUMENT_AMOUNT,INSTRUMENT_DATE,INSTRUMENT_NO,ISSUEING_BANK_ACCOUNT,ISSUEING_BANK_ID,ISSUEING_BRANCH_ID, ISSUING_MICR_CODE,ISSUING_IFSC_CODE,MAKER_REMARKS,TDS_AMOUNT,RECIPT_NO,REC_STATUS,INSTRUMENT_TYPE,PDC_FLAG,DEFAULT_BRANCH,RECEIPT_PURPOSE,TRANSACTION_REF,MAKER_ID,MAKER_DATE,MANUAL_AUTO_FLAG)");

      bufInsSql.append(" values ( ");
      bufInsSql.append(" ?,");
      bufInsSql.append(" 'DC',");
      bufInsSql.append(" 'CS',");

      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" STR_TO_DATE(?,'" + this.dateFormat + "'),");
      bufInsSql.append(" ?,");
      bufInsSql.append(" STR_TO_DATE(?,'" + this.dateFormat + "'),");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" 'P',");
      bufInsSql.append(" 'R',");
      bufInsSql.append(" 'N',");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + this.dateFormatWithTime + "'),INTERVAL CURTIME() HOUR_SECOND),");
      bufInsSql.append(" ? )");

      if (CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getLbxDealNo()).trim());
      }

      if (CommonFunction.checkNull(vo.getLbxBPNID()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getLbxBPNID()).trim());
      if (CommonFunction.checkNull(vo.getReceiptMode()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getReceiptMode()).trim());
      if (CommonFunction.checkNull(vo.getReceiptDate()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getReceiptDate()).trim());
      if (CommonFunction.checkNull(vo.getReceiptAmount()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addString("0");
      else
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getReceiptAmount()).trim()).toString());
      if (CommonFunction.checkNull(vo.getInstrumentDate()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getInstrumentDate()).trim());
      if (CommonFunction.checkNull(vo.getInstrumentNumber()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getInstrumentNumber()).trim());
      if (CommonFunction.checkNull(vo.getBankAccount()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getBankAccount()).trim());
      if (CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getLbxBankID()).trim());
      if (CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getLbxBranchID()).trim());
      if (CommonFunction.checkNull(vo.getMicr()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMicr()).trim());
      }
      if (CommonFunction.checkNull(vo.getIfsCode()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getIfsCode()).trim());
      }
      if (CommonFunction.checkNull(vo.getRemarks()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getRemarks()).trim());
      }
      if (CommonFunction.checkNull(vo.getTdsAmount()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addString("0.00");
      else {
        insertPrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(vo.getTdsAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(vo.getReceiptNo()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getReceiptNo()).trim());
      }
      if (CommonFunction.checkNull(vo.getDefaultBranch()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getDefaultBranch()).trim());
      if (CommonFunction.checkNull(vo.getPurpose()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getPurpose()).trim());
      if (CommonFunction.checkNull(vo.getTransactionRefNo()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getTransactionRefNo()).trim());
      if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerId()).trim());
      if (CommonFunction.checkNull(vo.getBusinessDate()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getBusinessDate()).trim());
      }
      if (CommonFunction.checkNull(vo.getStatusReceipt()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getStatusReceipt()).trim());
      }

      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info("IN SaveDatafor() insert cr_instrument_dtl query1 ### " + insertPrepStmtObject.printQuery());
      qryList.add(insertPrepStmtObject);

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info("In saveReceiptData. cr_instrument_dtl ....................." + status);
      StringBuffer last = new StringBuffer();
      ArrayList qryList1 = new ArrayList();
      last.append(" select last_insert_id() ");
      String last_id = CommonFunction.checkNull(ConnectionDAO.singleReturn(last.toString()));
      logger.info("In Checklast_id..." + last_id);
      insertPrepStmtObject = new PrepStmtObject();
      StringBuffer recipt = new StringBuffer();
      recipt.append(" select concat('" + last_id + "','/',B.branch_short_code)from cr_instrument_dtl A join com_branch_m B on B.branch_id=A.default_branch and A.rec_status='A' and A.default_branch='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDefaultBranch()).trim()) + "' ");
      logger.info("Check recipt  query### " + recipt);
      String recipt2 = CommonFunction.checkNull(ConnectionDAO.singleReturn(recipt.toString()));
      StringBuffer buf = new StringBuffer();
      buf.append("update cr_instrument_dtl set RECIPT_NO=? where INSTRUMENT_ID='" + last_id + "' ");

      if (CommonFunction.checkNull(vo.getStatusReceipt()).trim().equalsIgnoreCase("A"))
        insertPrepStmtObject.addString(CommonFunction.checkNull(recipt2).trim());
      else {
        insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getReceiptNo()).trim());
      }

      logger.info("receipt no update query: " + buf.toString());
      insertPrepStmtObject.setSql(buf.toString());

      qryList1.add(insertPrepStmtObject);
      status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    if ((status) && (status1)) {
      return status;
    }
    return false;
  }

  public boolean saveForwardUpdateOnReceipt(ImdMakerVO receiptVO)
  {
    ArrayList getDataList = new ArrayList();
    PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
    StringBuffer bufInsSql = new StringBuffer();
    String defaultBarnch = receiptVO.getDefaultBranch();
    boolean status = false;
    logger.info("In updateOnSave,,,,,");
    StringBuffer instrument = new StringBuffer();
    instrument.append(" select instrument_id from cr_instrument_dtl where  txn_type='dc' and txnid ='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim()) + "' ");
    logger.info("Check instrument  query### " + instrument);
    String instru = CommonFunction.checkNull(ConnectionDAO.singleReturn(instrument.toString()));
    logger.info("instru::" + instru);
    logger.info("default_branch_aman_testing::" + defaultBarnch);

    StringBuffer recipt = new StringBuffer();
    recipt.append(" select concat('" + instru + "','/',B.branch_short_code)from cr_instrument_dtl A join com_branch_m B on B.branch_id=A.default_branch and A.rec_status='A' and A.default_branch='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getDefaultBranch()).trim()) + "' ");
    logger.info("Check recipt  query### " + recipt);
    String recipt2 = CommonFunction.checkNull(ConnectionDAO.singleReturn(recipt.toString()));
    logger.info("recipt2::" + recipt2);

    ArrayList queryList = new ArrayList();
    try
    {
      bufInsSql.append("update cr_instrument_dtl set ");
      bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=STR_TO_DATE(?,'" + this.dateFormat + "'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'" + this.dateFormat + "'),");
      bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
      bufInsSql.append(" REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '" + this.dateFormatWithTime + "'),INTERVAL CURTIME() HOUR_SECOND), REC_STATUS='F',MANUAL_AUTO_FLAG=? where TXNID=?  ");
      bufInsSql.append(" AND INSTRUMENT_ID=?");

      if (CommonFunction.checkNull(receiptVO.getReceiptMode()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getReceiptMode()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getReceiptDate()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getReceiptDate()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addString("0");
      else {
        updatePrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getBankAccount()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getBankAccount()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getLbxBankID()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxBankID()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getMicr()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getMicr()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getIfsCode()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getIfsCode()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getRemarks()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getRemarks()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getTdsAmount()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addString("0.00");
      else {
        updatePrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(receiptVO.getTdsAmount()).trim()).toString());
      }

      if (CommonFunction.checkNull(receiptVO.getStatusReceipt()).trim().equalsIgnoreCase("A"))
      {
        updatePrepStmtObject.addString(CommonFunction.checkNull(recipt2).trim());
      }
      else
      {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getReceiptNo()).trim());
      }

      if (CommonFunction.checkNull(receiptVO.getMakerId()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else
        updatePrepStmtObject.addString(receiptVO.getMakerId().trim());
      if (CommonFunction.checkNull(receiptVO.getBusinessDate()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getStatusReceipt()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getStatusReceipt()).trim());
      }

      if (CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim());
      }

      if (CommonFunction.checkNull(receiptVO.getInstrumentID()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim());
      }
      updatePrepStmtObject.setSql(bufInsSql.toString());
      logger.info("IN saveForwardReceiptData cr_instrument_dtl query1 ### " + updatePrepStmtObject.printQuery());
      queryList.add(updatePrepStmtObject);

      logger.info("In saveAll update cr_instrument_dtl" + bufInsSql.toString());

      PrepStmtObject updatePrepStmtObject1 = new PrepStmtObject();
      StringBuffer bufInsSql1 = new StringBuffer();
      StringBuilder inventoryFlagInMst = new StringBuilder();
      StringBuilder existInStationary = new StringBuilder();
      String inventoryFlag = "";
      String checkAllBranch = "";
      String checkUserAvailable = "";
      StringBuilder allBranch = new StringBuilder();
      StringBuilder checkUser = new StringBuilder();
      String resultStationary = "";
      boolean updateStatus = false;
      String IssuingUser = receiptVO.getMakerId();
      logger.info("IssuingUser:::" + IssuingUser);
      logger.info("receipt default branch" + receiptVO.getDefaultBranch());

      inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='INVENTORY_FLAG'");
      logger.info("IN checkReciptStatus inventoryFlag ### " + inventoryFlagInMst.toString());
      inventoryFlag = ConnectionDAO.singleReturn(inventoryFlagInMst.toString());

      if ((inventoryFlag.equalsIgnoreCase("Y")) && (!CommonFunction.checkNull(receiptVO.getReceiptNo()).equalsIgnoreCase("")))
      {
        allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl INNER JOIN CR_STATIONARY_DTL ON CR_STATIONARY_DTL.BOOK_NO=CR_STATIONARY_STATUS_DTL.BOOK_NO WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='" + receiptVO.getReceiptNo() + "' AND STATUS='A' AND IFNULL(CR_STATIONARY_DTL.RETURN_TO_HO_FLAG,'')<>'R' ");
        logger.info("IN checkAllBranch ### " + allBranch.toString());
        checkAllBranch = ConnectionDAO.singleReturn(allBranch.toString());

        if (!checkAllBranch.equalsIgnoreCase("0"))
        {
          if (CommonFunction.checkNull(receiptVO.getRemarks()).equalsIgnoreCase(""))
          {
            bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='USED BY IMD MAKER',USED_BY ='" + receiptVO.getMakerId() + "',USED_DATE=STR_TO_DATE('" + receiptVO.getBusinessDate() + "', '" + this.dateFormatWithTime + "')  where RECEPT_CHEQUE_NO='" + receiptVO.getReceiptNo() + "'");
          }
          else
          {
            bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='" + receiptVO.getRemarks() + "',USED_BY ='" + receiptVO.getMakerId() + "',USED_DATE=STR_TO_DATE('" + receiptVO.getBusinessDate() + "', '" + this.dateFormatWithTime + "')  where RECEPT_CHEQUE_NO='" + receiptVO.getReceiptNo() + "'");
          }

          logger.info("IN updateStationary  ### " + bufInsSql1.toString() + receiptVO.getReceiptNo());
          updatePrepStmtObject1.setSql(bufInsSql1.toString());
          queryList.add(updatePrepStmtObject1);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
        } else {
          checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + receiptVO.getReceiptNo() + "' AND IFNULL(ISSUING_USER,'')<>''");
          logger.info("IN checkInventory inventoryFlag ### " + checkUser.toString());
          checkUserAvailable = ConnectionDAO.singleReturn(checkUser.toString());
          if (!checkUserAvailable.equalsIgnoreCase("0"))
          {
            existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + receiptVO.getReceiptNo() + "'  AND IFNULL(ISSUING_USER,'" + IssuingUser + "')='" + IssuingUser + "' ");
            logger.info("IN checkUserAvailability  in existInStationary ### " + existInStationary.toString());
            resultStationary = ConnectionDAO.singleReturn(existInStationary.toString());
          } else {
            existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + receiptVO.getReceiptNo() + "'  AND ISSUING_BRANCH='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getDefaultBranch()).trim()) + "'");
            logger.info("IN checkReciptStatus  in existInStationary ### " + existInStationary.toString());
            resultStationary = ConnectionDAO.singleReturn(existInStationary.toString());
          }
          if (!resultStationary.equalsIgnoreCase("0"))
          {
            if (CommonFunction.checkNull(receiptVO.getRemarks()).equalsIgnoreCase(""))
            {
              bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='USED BY IMD MAKER',USED_BY ='" + receiptVO.getMakerId() + "',USED_DATE=STR_TO_DATE('" + receiptVO.getBusinessDate() + "', '" + this.dateFormatWithTime + "')  where RECEPT_CHEQUE_NO='" + receiptVO.getReceiptNo() + "'");
            }
            else
            {
              bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='" + receiptVO.getRemarks() + "',USED_BY ='" + receiptVO.getMakerId() + "',USED_DATE=STR_TO_DATE('" + receiptVO.getBusinessDate() + "', '" + this.dateFormatWithTime + "')  where RECEPT_CHEQUE_NO='" + receiptVO.getReceiptNo() + "'");
            }
            logger.info("IN updateStationary  ### " + bufInsSql1.toString() + receiptVO.getReceiptNo());
            updatePrepStmtObject1.setSql(bufInsSql1.toString());
            queryList.add(updatePrepStmtObject1);
            status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
          } else {
            status = false;
          }
          logger.info("In saveForwardUpdateOnReceipt....................." + status);
        }
      }
      else {
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
      }
      updatePrepStmtObject1 = null;
      existInStationary = null;
      bufInsSql1 = null;
      queryList.clear();
      queryList = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return status;
  }

  public boolean updateOnReceiptSave(ImdMakerVO receiptVO)
  {
    ArrayList getDataList = new ArrayList();
    PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
    StringBuffer bufInsSql = new StringBuffer();
    boolean status = false;
    logger.info("In updateOnSave,,,,,");
    ArrayList queryList = new ArrayList();

    StringBuffer instrument = new StringBuffer();
    instrument.append(" select instrument_id from cr_instrument_dtl where  txn_type='dc' and txnid ='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim()) + "' ");
    logger.info("Check instrument  query### " + instrument);
    String instru = CommonFunction.checkNull(ConnectionDAO.singleReturn(instrument.toString()));
    logger.info("instru::" + instru);

    StringBuffer recipt = new StringBuffer();
    recipt.append(" select concat('" + instru + "','/',B.branch_short_code)from cr_instrument_dtl A join com_branch_m B on B.branch_id=A.default_branch and A.rec_status='A' and A.default_branch='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getDefaultBranch()).trim()) + "' ");
    logger.info("Check recipt  query### " + recipt);
    String recipt2 = CommonFunction.checkNull(ConnectionDAO.singleReturn(recipt.toString()));
    logger.info("recipt2::" + recipt2);
    try
    {
      bufInsSql.append(" update cr_instrument_dtl set ");
      bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=STR_TO_DATE(?,'" + this.dateFormat + "'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'" + this.dateFormatWithTime + "'),");
      bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,");
      bufInsSql.append("  ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
      bufInsSql.append("  MAKER_REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?,DEFAULT_BRANCH=?,RECEIPT_PURPOSE=?,TRANSACTION_REF=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '" + this.dateFormatWithTime + "'),INTERVAL CURTIME() HOUR_SECOND),MANUAL_AUTO_FLAG=?  where TXNID=? and INSTRUMENT_ID=?");

      if (CommonFunction.checkNull(receiptVO.getReceiptMode()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getReceiptMode()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getReceiptDate()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getReceiptDate()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addString("0");
      else {
        updatePrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getBankAccount()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getBankAccount()).trim());
      }

      if (CommonFunction.checkNull(receiptVO.getLbxBankID()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxBankID()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getMicr()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getMicr()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getIfsCode()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getIfsCode()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getRemarks()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getRemarks()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getTdsAmount()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addString("0.00");
      else {
        updatePrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(receiptVO.getTdsAmount()).trim()).toString());
      }
      if (CommonFunction.checkNull(receiptVO.getStatusReceipt()).trim().equalsIgnoreCase("A"))
      {
        updatePrepStmtObject.addString(CommonFunction.checkNull(recipt2).trim());
      }
      else
      {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getReceiptNo()).trim());
      }

      if (CommonFunction.checkNull(receiptVO.getDefaultBranch()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else
        updatePrepStmtObject.addString(receiptVO.getDefaultBranch().trim());
      if (CommonFunction.checkNull(receiptVO.getPurpose()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else
        updatePrepStmtObject.addString(receiptVO.getPurpose().trim());
      if (CommonFunction.checkNull(receiptVO.getTransactionRefNo()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else
        updatePrepStmtObject.addString(receiptVO.getTransactionRefNo().trim());
      if (CommonFunction.checkNull(receiptVO.getMakerId()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else
        updatePrepStmtObject.addString(receiptVO.getMakerId().trim());
      if (CommonFunction.checkNull(receiptVO.getBusinessDate()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
      }
      if (CommonFunction.checkNull(receiptVO.getStatusReceipt()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getStatusReceipt()).trim());
      }

      if (CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim());
      }

      if (CommonFunction.checkNull(receiptVO.getInstrumentID()).trim().equalsIgnoreCase(""))
        updatePrepStmtObject.addNull();
      else {
        updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim());
      }
      updatePrepStmtObject.setSql(bufInsSql.toString());
      logger.info("IN SaveDatafor() insert cr_instrument_dtl query1 ### " + updatePrepStmtObject.printQuery());
      queryList.add(updatePrepStmtObject);

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
      logger.info("In updateOnSave....................." + status);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return status;
  }

  public String checkFesiblityOnForward(ImdMakerVO receiptVO)
  {
    String status = "";
    StringBuilder query = new StringBuilder();
    StringBuilder query1 = new StringBuilder();
    ArrayList List = new ArrayList();
    String receiptAmount = CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim();
    logger.info("In receiptAuthorGrid......receiptAmount  " + receiptAmount);
    double receiptAmnt = 0.0D;
    double tdsAmnt = 0.0D;
    try {
      if (receiptAmount.equalsIgnoreCase(""))
        receiptAmount = "0";
      else {
        receiptAmount = this.myFormatter.parse(CommonFunction.checkNull(receiptVO.getReceiptAmount())).toString();
      }
      receiptAmnt = Double.parseDouble(receiptAmount);
      String tdsAmount = CommonFunction.checkNull(receiptVO.getTdsAmount()).trim();
      logger.info("In receiptAuthorGrid......tdsAmount  " + tdsAmount);
      if (tdsAmount.equalsIgnoreCase(""))
      {
        tdsAmount = "0";
      }
      else {
        tdsAmount = this.myFormatter.parse(CommonFunction.checkNull(receiptVO.getTdsAmount())).toString();
      }
      tdsAmnt = Double.parseDouble(tdsAmount);
      double Amount = receiptAmnt + tdsAmnt;
      logger.info("In receiptAuthorGrid......Amount  " + Amount);

      query.append(" SELECT ifnull(SUM(IMD_AMOUNT),0) FROM cr_imd_dtl WHERE INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()) + "'");
      logger.info("IN checkAllocation  ### " + query);

      String AllocatedAmount = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
      double VAllocatedAmount = 0.0D;
      if (AllocatedAmount.equalsIgnoreCase("")) {
        AllocatedAmount = "0";
      }
      VAllocatedAmount = Double.parseDouble(CommonFunction.checkNull(AllocatedAmount));

      logger.info("VAllocatedAmount----" + VAllocatedAmount);

      query1.append(" SELECT ifnull(SUM(TDS_AMOUNT),0) FROM cr_imd_dtl WHERE INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()) + "'");
      logger.info("IN checkAllocation 1  ### " + query1);
      String TdsAllocatedAmount = ConnectionDAO.singleReturn(query1.toString());

      double VTdsAllocatedAmount = 0.0D;
      if (TdsAllocatedAmount.equalsIgnoreCase("")) {
        TdsAllocatedAmount = "0";
      }
      VTdsAllocatedAmount = Double.parseDouble(CommonFunction.checkNull(TdsAllocatedAmount));
      logger.info("VTdsAllocatedAmount----" + VTdsAllocatedAmount);

      if (VAllocatedAmount == 0.0D) {
        status = "DA";
      }
      else if ((Amount >= VAllocatedAmount) && (tdsAmnt == VTdsAllocatedAmount))
      {
        status = "A";
      }
      else if ((Amount < VAllocatedAmount) || (tdsAmnt != VTdsAllocatedAmount))
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
  public int existInsNo(ImdMakerVO vo) {
    int result = 0;
    StringBuilder existCount = new StringBuilder();
    StringBuilder res = new StringBuilder();
    try
    {
      existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()) + "'" + 
        " and cid.ISSUEING_BANK_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim()) + "'and cid.ISSUEING_BRANCH_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchID()).trim()) + "'" + 
        " and BPID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()) + "'" + 
        " and BPTYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()) + "' " + 
        " and INSTRUMENT_ID <>'" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim()) + "' and INSTRUMENT_TYPE='R'  AND REC_STATUS<>'X'");

      res.append(ConnectionDAO.singleReturn(existCount.toString()));

      if (!CommonFunction.checkNull(res.toString()).equalsIgnoreCase(""))
      {
        result = Integer.parseInt(res.toString());
      }
      logger.info("IN existReceiptData  ### " + result);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      existCount = null;
      res = null;
    }

    return result;
  }
  public int existInsNForNR(ImdMakerVO vo) {
    int result = 0;
    StringBuilder existCount = new StringBuilder();
    StringBuilder res = new StringBuilder();
    try
    {
      existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()) + "'" + 
        " and cid.ISSUEING_BANK_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim()) + "'" + 
        " and BPID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()) + "'" + 
        " and BPTYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()) + "' " + 
        " and INSTRUMENT_ID <>'" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim()) + "' and INSTRUMENT_TYPE='R' AND REC_STATUS<>'X'");

      res.append(ConnectionDAO.singleReturn(existCount.toString()));

      if (!CommonFunction.checkNull(res.toString()).equalsIgnoreCase(""))
      {
        result = Integer.parseInt(res.toString());
      }
      logger.info("IN existReceiptData  ### " + result);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      existCount = null;
      res = null;
    }

    return result;
  }

  public String saveForwardReceiptData(ImdMakerVO receiptVO, float amount)
  {
    ArrayList getDataList = new ArrayList();
    PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
    StringBuffer bufInsSql = new StringBuffer();
    boolean status = false;
    logger.info("In saveForwardReceiptData,,,,,");
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
      ArrayList txnAdviceId = new ArrayList();
      ArrayList amtInProcess = new ArrayList();
      ArrayList amtAllocated = new ArrayList();

      int statusProc = 0;
      logger.info("BeforeProc: Imd_Maker_Validation_M ");

      in.add(Float.valueOf(amount));
      in.add(CommonFunction.checkNull(receiptVO.getInstrumentID().trim()));

      out.add(s1.toString());
      out.add(s2.toString());

      outMessages = (ArrayList)ConnectionDAO.callSP("Imd_Maker_Validation_M", in, out);
      s1.append(CommonFunction.checkNull(outMessages.get(0)));
      s2.append(CommonFunction.checkNull(outMessages.get(1)));
      logger.info("Imd_Maker_Validation_M s1: " + s1);
      logger.info("Imd_Maker_Validation_M s2: " + s2);
      logger.info("Imd_Maker_Validation_M Status for Proc: " + statusProc);

      procval = CommonFunction.checkNull(s2.toString());

      if ((!s1.toString().equalsIgnoreCase("E")) && 
        (!s2.toString().equalsIgnoreCase("F"))) {
        logger.info("After Proc inside If ");

        bufInsSql.append("update cr_instrument_dtl set ");
        bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=STR_TO_DATE(?,'" + this.dateFormat + "'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'" + this.dateFormat + "'),");
        bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
        bufInsSql.append(" REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?, REC_STATUS='F',MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '" + this.dateFormatWithTime + "'),INTERVAL CURTIME() HOUR_SECOND) where TXNID=?  ");
        bufInsSql.append(" AND INSTRUMENT_ID=?");

        if (CommonFunction.checkNull(receiptVO.getReceiptMode()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getReceiptMode()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getReceiptDate()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getReceiptDate()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addString("0.00");
        else {
          updatePrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim()).toString());
        }
        if (CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getBankAccount()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getBankAccount()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getLbxBankID()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxBankID()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getMicr()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getMicr()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getIfsCode()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getIfsCode()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getRemarks()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getRemarks()).trim());
        }
        if (CommonFunction.checkNull(receiptVO.getTdsAmount()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addString("0.00");
        else {
          updatePrepStmtObject.addString(this.myFormatter.parse(CommonFunction.checkNull(receiptVO.getTdsAmount()).trim()).toString());
        }
        if (CommonFunction.checkNull(receiptVO.getReceiptNo()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getReceiptNo()).trim());
        }

        if (CommonFunction.checkNull(receiptVO.getMakerId()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(receiptVO.getMakerId().trim());
        }
        if (CommonFunction.checkNull(receiptVO.getBusinessDate()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(receiptVO.getBusinessDate().trim());
        }
        if (CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim());
        }

        if (CommonFunction.checkNull(receiptVO.getInstrumentID()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim());
        }
        updatePrepStmtObject.setSql(bufInsSql.toString());
        logger.info("IN saveForwardReceiptData cr_instrument_dtl query1 ### " + updatePrepStmtObject.printQuery());
        queryList.add(updatePrepStmtObject);

        String getPmntIdsQuery = "select IMD_ID from cr_imd_dtl where ifnull(IMD_AMOUNT,0.00)=0.00 ";
        logger.info("In getPmntIdsQuery---" + getPmntIdsQuery);
        ArrayList getImdIds = ConnectionDAO.sqlSelect(getPmntIdsQuery);
        logger.info("In getPmntIds  size---" + getImdIds);
        if (getImdIds.size() > 0)
        {
          for (int j = 0; j < getImdIds.size(); j++)
          {
            ArrayList subgetPmntIdsList = (ArrayList)getImdIds.get(j);
            if (subgetPmntIdsList.size() > 0) {
              String pmntId = subgetPmntIdsList.get(0).toString();
              logger.info("In ImdId  size---" + pmntId);
              updatePrepStmtObject = new PrepStmtObject();
              bufInsSql = new StringBuffer();
              bufInsSql.append(" Delete from cr_imd_dtl ");
              bufInsSql.append(" where imd_ID=?");

              if (CommonFunction.checkNull(pmntId).trim().equalsIgnoreCase(""))
                updatePrepStmtObject.addNull();
              else {
                updatePrepStmtObject.addString(CommonFunction.checkNull(pmntId).trim());
              }

              updatePrepStmtObject.setSql(bufInsSql.toString());
              logger.info("IN at saveForwardReceiptData() delete cr_imd_dtl query1 ### " + updatePrepStmtObject.printQuery());
              queryList.add(updatePrepStmtObject);
            }

          }

        }

        updatePrepStmtObject = new PrepStmtObject();
        bufInsSql = new StringBuffer();

        bufInsSql.append(" update cr_imd_dtl set REC_STATUS='F',MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '" + this.dateFormatWithTime + "'),INTERVAL CURTIME() HOUR_SECOND) where   INSTRUMENT_ID=?");

        if (CommonFunction.checkNull(receiptVO.getMakerId()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(receiptVO.getMakerId().trim());
        }
        if (CommonFunction.checkNull(receiptVO.getBusinessDate()).trim().equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(receiptVO.getBusinessDate().trim());
        }
        if (CommonFunction.checkNull(receiptVO.getInstrumentID()).equalsIgnoreCase(""))
          updatePrepStmtObject.addNull();
        else {
          updatePrepStmtObject.addString(receiptVO.getInstrumentID().trim());
        }
        updatePrepStmtObject.setSql(bufInsSql.toString());
        logger.info("IN saveForwardReceiptData cr_imd_dtl query1 ### " + updatePrepStmtObject.printQuery());
        queryList.add(updatePrepStmtObject);

        logger.info("Update Query----- " + queryList);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
        logger.info("In saveForwardReceiptData,,,,," + status);
      }

      if (status)
        procval = "queryexecuted";
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      s1 = null;
      s2 = null;
      getProcessammountQuery = null;
      getAllotedAmtQuery = null;
    }

    return procval;
  }

  public ArrayList<ImdMakerVO> onAllocatedReceivable(ImdMakerVO receiptVO, int loanId, String bpType, int instrumentId)
  {
    ArrayList allocatedList = new ArrayList();

    StringBuilder query = new StringBuilder();
    try
    {
      ArrayList mainList = new ArrayList();
      ArrayList subList = new ArrayList();

      logger.info(" In onAllocatedReceivable....");

      query.append("SELECT  distinct DATE_FORMAT(ADVICE_DATE,'" + this.dateFormat + "')," + 
        " (Select CHARGE_DESC From com_charge_code_m Where CHARGE_CODE=CTD.CHARGE_CODE_ID) CHARGE, ADVICE_AMOUNT,  " + 
        " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,   " + 
        " AMOUNT_IN_PROCESS,CTD.TDS_AMOUNT, PMNT_AMOUNT,CPD.TDS_AMOUNT AS TDS_ALLOCATED_AMOUNT " + 
        " from cr_txnadvice_dtl CTD,cr_pmnt_dtl CPD  where CPD.TXNADVICEID=CTD.TXNADVICE_ID " + 
        " AND ADVICE_TYPE='R' " + 
        "  AND LOAN_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(loanId)).trim()) + "'" + 
        " AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "' " + 
        " AND CPD.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(instrumentId)).trim()) + "'" + 
        "  Order By CTD.ADVICE_DATE ASC ");

      logger.info("In onAllocatedReceivable" + query);

      mainList = ConnectionDAO.sqlSelect(query.toString());
      for (int i = 0; i < mainList.size(); i++)
      {
        subList = (ArrayList)mainList.get(i);
        if (subList.size() > 0)
        {
          ImdMakerVO VO = new ImdMakerVO();
          VO.setReceiptDate(CommonFunction.checkNull(subList.get(0)).trim());
          logger.info("setPaymentDate: " + subList.get(0));
          VO.setChargeDesc(CommonFunction.checkNull(subList.get(1)).trim());
          Number OriginalAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(2)).equals(""))
          {
            OriginalAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(2)).trim());
          }
          logger.info("OriginalAmount: " + OriginalAmount);
          VO.setOriginalAmount(this.myFormatter.format(OriginalAmount));
          Number BalanceAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(3)).equals(""))
          {
            BalanceAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
          }
          logger.info("BalanceAmount: " + BalanceAmount);
          VO.setBalanceAmount(this.myFormatter.format(BalanceAmount));
          Number AmountInProcess = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(4)).equals(""))
          {
            AmountInProcess = this.myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
          }
          logger.info("AmountInProcess: " + AmountInProcess);
          VO.setAmountInProcess(this.myFormatter.format(AmountInProcess));
          Number TdsadviseAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(5)).equals(""))
          {
            TdsadviseAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
          }
          logger.info("TdsadviseAmount: " + TdsadviseAmount);
          VO.setTdsadviseAmount(this.myFormatter.format(TdsadviseAmount));
          Number AllotedAmount = Integer.valueOf(0);
          if (!CommonFunction.checkNull(subList.get(6)).equals(""))
          {
            AllotedAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(6)).trim());
          }
          logger.info("AllotedAmount: " + AllotedAmount);
          VO.setAllotedAmount(this.myFormatter.format(AllotedAmount));
          if (!CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("")) {
            Number TdsAllocatedAmount = this.myFormatter.parse(CommonFunction.checkNull(subList.get(7)).trim());
            logger.info("TdsAllocatedAmount: " + TdsAllocatedAmount);
            VO.setTdsAllocatedAmount(this.myFormatter.format(TdsAllocatedAmount));
          }
          else {
            VO.setTdsAllocatedAmount("0");
          }

          allocatedList.add(VO);
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
  public String onSaveofImdAuthor(ImdMakerVO receiptVO) {
    boolean status = false;
    String procval = "";
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    try
    {
      ArrayList queryList = new ArrayList();

      int statusProc = 0;

      logger.info(" In pay_approveReject_Rec onSaveofReceiptAuthor BeforeProc: ");
      logger.info("In pay_approveReject_Rec onSaveofReceiptAuthor getInstrumentID()" + receiptVO.getInstrumentID());
      logger.info("In onSaveofReceiptAuthor getDecision" + receiptVO.getDecision());
      logger.info("In onSaveofReceiptAuthor getComments()" + receiptVO.getComments());

      in.add(receiptVO.getInstrumentID());
      in.add(receiptVO.getDecision());
      in.add(receiptVO.getComments());
      in.add("R");
      in.add(CommonFunction.checkNull(receiptVO.getUserId().trim()));
      String date = CommonFunction.changeFormat(receiptVO.getBusinessDate());
      in.add(date);
      out.add(s1.toString());
      out.add(s2.toString());

      outMessages = (ArrayList)ConnectionDAO.callSP("IMD_ApproveReject_Rec", in, out);
      s1.append(CommonFunction.checkNull(outMessages.get(0)));
      s2.append(CommonFunction.checkNull(outMessages.get(1)));
      logger.info("s1: " + s1);
      logger.info("s2: " + s2);
      logger.info("pay_approveReject_Rec Status for Proc: " + statusProc);
      logger.info("pay_approveReject_Rec After Proc: ");
      procval = s2.toString();
      if (!s1.toString().equalsIgnoreCase("S")) {
        logger.info("After Proc inside If ");
        status = false;
      } else {
        status = true;
      }if (s1.toString().equalsIgnoreCase("S"))
      {
        logger.info("After proc call..commit.error message." + s2);
      }
      else
      {
        logger.info("After proc call..rollback.error message." + s2);
      }

      logger.info("In onSaveofReceiptAuthor,,,,," + status);
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

  public ArrayList<ImdMakerVO> getchequeDetailR(String bankAccount, String accountType)
  {
    ArrayList getChequeList = new ArrayList();
    StringBuilder query = new StringBuilder();
    try
    {
      ArrayList mainList = new ArrayList();
      ArrayList subList = new ArrayList();

      query.append(" SELECT CBA.BANK_ACCOUNT,CBA.BANK_ID,CB.BANK_NAME,CBA.BANK_BRANCH_ID,CBB.BANK_BRANCH_NAME,  CBA.BRANCH_MICR_CODE,CBA.BRANCH_IFCS_CODE FROM com_bank_m CB,com_bankbranch_m CBB,com_bank_accounts_m CBA  WHERE CB.BANK_ID=CBA.BANK_ID AND CBB.BANK_BRANCH_ID=CBA.BANK_BRANCH_ID AND CBA.REC_STATUS='A' AND CBA.BANK_ACCOUNT='" + 
        StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankAccount).trim()) + "'" + 
        " AND ACCOUNT_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(accountType).trim()) + "'");

      logger.info("In getListOfValues" + query);

      mainList = ConnectionDAO.sqlSelect(query.toString());
      for (int i = 0; i < mainList.size(); i++)
      {
        subList = (ArrayList)mainList.get(i);
        if (subList.size() > 0) {
          ImdMakerVO receiptVo = new ImdMakerVO();
          receiptVo.setBankAccount(CommonFunction.checkNull(subList.get(0)).trim());
          receiptVo.setLbxBankID(CommonFunction.checkNull(subList.get(1)).trim());
          receiptVo.setBank(CommonFunction.checkNull(subList.get(2)).trim());
          receiptVo.setLbxBranchID(CommonFunction.checkNull(subList.get(3)).trim());
          receiptVo.setBranch(CommonFunction.checkNull(subList.get(4)).trim());
          receiptVo.setMicr(CommonFunction.checkNull(subList.get(5)).trim());
          receiptVo.setIfsCode(CommonFunction.checkNull(subList.get(6)).trim());
          getChequeList.add(receiptVo);
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

  public boolean deleteReceipt(String id)
  {
    boolean status = false;
    boolean status1 = false;
    ArrayList list = new ArrayList();
    ArrayList list1 = new ArrayList();
    StringBuilder query = new StringBuilder();
    StringBuilder query1 = new StringBuilder();
    try {
      query.append("delete from cr_instrument_dtl where instrument_id='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim()) + "' ");
      list.add(query.toString());
      logger.info("delete()     query------------------" + query.toString());

      query1.append("delete from cr_pmnt_dtl where instrument_id='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim()) + "'");
      list1.add(query1.toString());
      logger.info("delete()     query1------------------" + query1.toString());
      status1 = ConnectionDAO.sqlInsUpdDelete(list1);
      status = ConnectionDAO.sqlInsUpdDelete(list);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return status;
  }

  public String getTotalRec(String loanId, String bPType)
  {
    logger.info("In getTotalRec().");
    String amount = "0.00";
    StringBuilder query = new StringBuilder();

    query = null;

    return amount;
  }

  public String receiptNoCheckFlag()
  {
    String flag = "";
    StringBuilder query = new StringBuilder();
    query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='RECEIPT_NO_CHECK_FLAG'");
    logger.info("Query for getting receiptNoCheckFlag from parameter_mst  : " + query.toString());
    try
    {
      flag = ConnectionDAO.singleReturn(query.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }

  public ArrayList receiptPurposeList() {
    ArrayList arrList = new ArrayList();
    ArrayList subList = new ArrayList();
    ArrayList mainList = new ArrayList();
    StringBuilder query = new StringBuilder();
    logger.info("In receiptPurposeList...");
    try
    {
      query.append("select value,description from generic_master where generic_key = 'RECEIPT_PURPOSE' and rec_status ='A' ");
      arrList = ConnectionDAO.sqlSelect(query.toString());
      for (int i = 0; i < arrList.size(); i++)
      {
        subList = (ArrayList)arrList.get(i);
        logger.info("In receiptPurposeList..." + subList.size());

        if (subList.size() > 0)
        {
          ImdMakerVO receiptVo = new ImdMakerVO();
          receiptVo.setReceiptPurposeValue(CommonFunction.checkNull(subList.get(0)).trim());
          receiptVo.setReceiptPurposeDesc(CommonFunction.checkNull(subList.get(1)).trim());
          mainList.add(receiptVo);
        }
      }

    }
    catch (Exception e)
    {
      logger.debug("IOException In downLoadErrorLog() ==>> " + e.getMessage());
    }
    finally
    {
      query = null;
      subList = null;
      arrList = null;
    }

    return mainList;
  }

  public String checkReciptStatus(Object ob)
  {
    String result = "";
    ImdMakerVO vo = (ImdMakerVO)ob;
    StringBuilder existCount = new StringBuilder();
    try {
      existCount.append("SELECT COUNT(1)  from cr_instrument_dtl where IFNULL(REC_STATUS,'')<>'X' AND RECIPT_NO<>'' AND RECIPT_NO='" + vo.getReceiptNo() + "' ");
      if(!CommonFunction.checkNull(vo.getInstrumentID()).equalsIgnoreCase("")){
      existCount.append(" AND INSTRUMENT_ID <>'"+vo.getInstrumentID()+"'");
      }
      result = ConnectionDAO.singleReturn(existCount.toString());
      logger.info("IN checkReceiptstatus  ### " + existCount);
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

  public String checkReciptUpdateStatus(Object ob)
  {
    String result = "";
    ImdMakerVO vo = (ImdMakerVO)ob;
    StringBuilder existCount = new StringBuilder();
    try {
    	if(CommonFunction.checkNull(vo.getReceiptNo()).equalsIgnoreCase("")){
    		result="0";
    	}else{
      existCount.append("SELECT COUNT(1)  from cr_instrument_dtl where IFNULL(REC_STATUS,'')<>'X' AND RECIPT_NO='" + vo.getReceiptNo() + "' AND INSTRUMENT_ID<>'" + vo.getInstrumentID() + "' ");

      result = ConnectionDAO.singleReturn(existCount.toString());
      logger.info("IN checkReciptUpdateStatus  ### " + existCount);
    	}
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

  public String getinstrumentId()
  {
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

  public ArrayList getchargesDetail(String dealCap, String dealId)
  {
    ArrayList list = new ArrayList();
    try
    {
      ChargeVo vo = null;
      StringBuilder query = new StringBuilder();
      query.append("select t.DEAL_CHARGE_DTL_ID,DEAL_CHARGE_TYPE,t.DEAL_CHARGE_CODE,CHARGE_DESC,");
      query.append(" GM.DESCRIPTION,v.DEALER_DESC,");
      query.append(" DEAL_CHARGE_CALCULATED_AMOUNT,DEAL_CHARGE_FINAL_AMOUNT,d.CUSTOMER_NAME,p.DEAL_LOAN_AMOUNT,(IFNULL(P.DEAL_ASSET_COST,0)-IFNULL(P.DEAL_LOAN_AMOUNT,0)) AS 'MARGIN AMOUNT',");
      query.append(" DEAL_CHARGE_CALCULATED_ON,if(DEAL_CHARGE_METHOD='P','PERCENTAGE','FLAT'),if(DEAL_CHARGE_TAX_INCLUSIVE='N','NO','YES'),");
      query.append(" DEAL_CHARGE_TAX_RATE1,DEAL_CHARGE_TAX_RATE2,DEAL_CHARGE_BP_TYPE,if(DEAL_MIN_CHARGE_METHOD='P','PERCENTAGE','FLAT'),DEAL_CHARGE_MIN_CHARGE_AMOUNT,");
      query.append(" DEAL_CHARGE_TAX_APPLICABLE,DEAL_CHARGE_TDS_APPLICABLE,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_TAX_AMOUNT2,DEAL_CHARGE_MIN_CHARGE_AMOUNT,DEAL_CHARGE_TDS_RATE,DEAL_CHARGE_TDS_AMOUNT,DEAL_CHARGE_NET_AMOUNT,DEAL_CHARGE_APPLICATION_STAGE ");
      query.append(" from cr_deal_txncharges_dtl t ");
      query.append(" left join com_charge_code_m c on t.DEAL_CHARGE_CODE=c.CHARGE_CODE ");
      query.append(" left join cr_dsa_dealer_m v on v.DEALER_ID=t.DEAL_CHARGE_BP_ID and v.BP_TYPE=t.DEAL_CHARGE_BP_TYPE and v.REC_STATUS='A' ");
      query.append(" left join cr_deal_customer_m d on d.CUSTOMER_ID=t.DEAL_CHARGE_BP_ID ");
      query.append(" left join cr_deal_loan_dtl p on p.DEAL_ID=t.DEAL_ID  ");
      query.append(" left join generic_master GM on GM.VALUE=DEAL_CHARGE_BP_TYPE and GM.GENERIC_KEY='BPTYPE' ");
      query.append(" where t.DEAL_CHARGE_APPLICATION_STAGE='LIM' AND t.DEAL_CHARGE_TYPE='R' AND t.DEAL_CHARGE_BP_TYPE='CS'");
      query.append(" AND DEAL_CHARGE_NET_AMOUNT >0 AND t.DEAL_ID=" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
      logger.info("getchargesDetail in deal  query " + query);
      ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
      logger.info("getchargesDetail in deal  size " + charges.size());

      query = null;

      for (int i = 0; i < charges.size(); i++)
      {
        ArrayList subcharges = (ArrayList)charges.get(i);
        if (subcharges.size() > 0)
        {
          vo = new ChargeVo();
          vo.setChargeId(CommonFunction.checkNull(subcharges.get(0)).trim());
          if (CommonFunction.checkNull(subcharges.get(1)).trim().equals("R"))
          {
            vo.setChargeType("Receivable");
          }
          else if (CommonFunction.checkNull(subcharges.get(1)).trim().equals("P"))
          {
            vo.setChargeType("Payable");
          }
          vo.setChargeCode(CommonFunction.checkNull(subcharges.get(2)).trim());
          vo.setChargeDesc(CommonFunction.checkNull(subcharges.get(3)).trim());
          vo.setChargeBPType(CommonFunction.checkNull(subcharges.get(4)).trim());
          if (CommonFunction.checkNull(subcharges.get(16)).trim().equals("CS"))
          {
            vo.setChargeBPId(CommonFunction.checkNull(subcharges.get(8)).trim());
          }
          else
          {
            vo.setChargeBPId(CommonFunction.checkNull(subcharges.get(5)).trim());
          }
          if (!CommonFunction.checkNull(subcharges.get(6)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(6)).trim());
            vo.setChargeCal(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(7)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(7)).trim());
            vo.setChargeFinal(this.myFormatter.format(reconNum));
          }

          vo.setLoanAmount(CommonFunction.checkNull(subcharges.get(9)).trim());
          vo.setMarginAmount(CommonFunction.checkNull(subcharges.get(10)).trim());
          vo.setChargeCalculatedOn(CommonFunction.checkNull(subcharges.get(11)).trim());
          vo.setChargeMethod(CommonFunction.checkNull(subcharges.get(12)).trim());
          vo.setTaxsInclusive(CommonFunction.checkNull(subcharges.get(13)).trim());
          if (!CommonFunction.checkNull(subcharges.get(14)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(14)).trim());
            vo.setTaxtRat1(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(15)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(15)).trim());
            logger.info("setTaxtRat2: " + reconNum);
            vo.setTaxtRat2(this.myFormatter.format(reconNum));
          }
          vo.setMinChargeMethod(CommonFunction.checkNull(subcharges.get(17)).trim());
          vo.setMinChargeCalculatedOn(CommonFunction.checkNull(subcharges.get(18)).trim());
          vo.setDealChargeTaxApp(CommonFunction.checkNull(subcharges.get(19)).trim());
          vo.setDealChargeTdsApp(CommonFunction.checkNull(subcharges.get(20)).trim());
          if (!CommonFunction.checkNull(subcharges.get(21)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(21)).trim());
            vo.setDealChargeTaxAmount1(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(22)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(22)).trim());

            vo.setDealChargeTaxAmount2(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(23)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(23)).trim());
            vo.setDealChargeMinChargeAmount(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(24)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(24)).trim());

            vo.setDealChargeTdsRate(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(25)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(25)).trim());
            vo.setDealChargeTdsAmount(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(26)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(26)).trim());

            vo.setDealChargeNetAmount(this.myFormatter.format(reconNum));
          }
          vo.setApplStage(CommonFunction.checkNull(subcharges.get(27)));
          list.add(vo);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return list;
  }

  public ArrayList getAlocChargesDetail(String instrument, String dealId)
  {
    ArrayList list = new ArrayList();
    String instID = "";
    instID = StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrument)).trim();
    logger.info("instID instID instID ::::::::::" + instID);
    try
    {
      ChargeVo vo = null;
      StringBuilder query = new StringBuilder();
      query.append("select t.DEAL_CHARGE_DTL_ID,DEAL_CHARGE_TYPE,t.DEAL_CHARGE_CODE,CHARGE_DESC,");
      query.append(" GM.DESCRIPTION,v.DEALER_DESC,");
      query.append(" DEAL_CHARGE_CALCULATED_AMOUNT,DEAL_CHARGE_FINAL_AMOUNT,d.CUSTOMER_NAME,p.DEAL_LOAN_AMOUNT,(IFNULL(P.DEAL_ASSET_COST,0)-IFNULL(P.DEAL_LOAN_AMOUNT,0)) AS 'MARGIN AMOUNT',");
      query.append(" DEAL_CHARGE_CALCULATED_ON,if(DEAL_CHARGE_METHOD='P','PERCENTAGE','FLAT'),if(DEAL_CHARGE_TAX_INCLUSIVE='N','NO','YES'),");
      query.append(" DEAL_CHARGE_TAX_RATE1,DEAL_CHARGE_TAX_RATE2,DEAL_CHARGE_BP_TYPE,if(DEAL_MIN_CHARGE_METHOD='P','PERCENTAGE','FLAT'),DEAL_CHARGE_MIN_CHARGE_AMOUNT,");
      query.append(" DEAL_CHARGE_TAX_APPLICABLE,DEAL_CHARGE_TDS_APPLICABLE,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_TAX_AMOUNT2,DEAL_CHARGE_MIN_CHARGE_AMOUNT,DEAL_CHARGE_TDS_RATE,DEAL_CHARGE_TDS_AMOUNT,DEAL_CHARGE_NET_AMOUNT,DEAL_CHARGE_APPLICATION_STAGE, ");
      query.append(" (DEAL_CHARGE_FINAL_AMOUNT-ifnull(imd.imd_amount,0))balanceAmount, ifnull(currentImd.imd_amount,0)imd_amount");
      query.append(" from cr_deal_txncharges_dtl t ");
      query.append(" left join ( select deal_id,deal_charge_code,sum(imd_amount) imd_amount from cr_imd_dtl where instrument_id !='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrument)).trim() + "' and rec_status <>'X' ");
      query.append(" group by deal_id,deal_charge_code\t)imd on(imd.deal_id= t.DEAL_ID and t.deal_charge_code=imd.deal_charge_code) ");
      query.append(" left join\t( select deal_id,deal_charge_code,sum(imd_amount) imd_amount from cr_imd_dtl where instrument_id ='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrument)).trim() + "' and rec_status <>'X' ");
      query.append(" group by deal_id,deal_charge_code\t)currentImd on(currentImd.deal_id= t.DEAL_ID and t.deal_charge_code=currentImd.deal_charge_code) ");
      query.append(" left join com_charge_code_m c on t.DEAL_CHARGE_CODE=c.CHARGE_CODE ");
      query.append(" left join cr_dsa_dealer_m v on v.DEALER_ID=t.DEAL_CHARGE_BP_ID and v.BP_TYPE=t.DEAL_CHARGE_BP_TYPE and v.REC_STATUS='A' ");
      query.append(" left join cr_deal_customer_m d on d.CUSTOMER_ID=t.DEAL_CHARGE_BP_ID ");
      query.append(" left join cr_deal_loan_dtl p on p.DEAL_ID=t.DEAL_ID  ");
      query.append(" left join generic_master GM on GM.VALUE=DEAL_CHARGE_BP_TYPE and GM.GENERIC_KEY='BPTYPE' ");
      query.append(" where t.DEAL_CHARGE_APPLICATION_STAGE='LIM' AND t.DEAL_CHARGE_TYPE='R' AND t.DEAL_CHARGE_BP_TYPE='CS'");
      query.append(" AND DEAL_CHARGE_NET_AMOUNT >0 AND t.DEAL_ID=" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
      logger.info("getchargesDetail in deal  query " + query);
      ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
      logger.info("getchargesDetail in deal  size " + charges.size());

      query = null;

      for (int i = 0; i < charges.size(); i++)
      {
        ArrayList subcharges = (ArrayList)charges.get(i);
        if (subcharges.size() > 0)
        {
          vo = new ChargeVo();
          vo.setChargeId(CommonFunction.checkNull(subcharges.get(0)).trim());
          if (CommonFunction.checkNull(subcharges.get(1)).trim().equals("R"))
          {
            vo.setChargeType("Receivable");
          }
          else if (CommonFunction.checkNull(subcharges.get(1)).trim().equals("P"))
          {
            vo.setChargeType("Payable");
          }
          vo.setChargeCode(CommonFunction.checkNull(subcharges.get(2)).trim());
          vo.setChargeDesc(CommonFunction.checkNull(subcharges.get(3)).trim());
          vo.setChargeBPType(CommonFunction.checkNull(subcharges.get(4)).trim());
          if (CommonFunction.checkNull(subcharges.get(16)).trim().equals("CS"))
          {
            vo.setChargeBPId(CommonFunction.checkNull(subcharges.get(8)).trim());
          }
          else
          {
            vo.setChargeBPId(CommonFunction.checkNull(subcharges.get(5)).trim());
          }
          if (!CommonFunction.checkNull(subcharges.get(6)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(6)).trim());
            vo.setChargeCal(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(7)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(7)).trim());
            vo.setChargeFinal(this.myFormatter.format(reconNum));
          }

          vo.setLoanAmount(CommonFunction.checkNull(subcharges.get(9)).trim());
          vo.setMarginAmount(CommonFunction.checkNull(subcharges.get(10)).trim());
          vo.setChargeCalculatedOn(CommonFunction.checkNull(subcharges.get(11)).trim());
          vo.setChargeMethod(CommonFunction.checkNull(subcharges.get(12)).trim());
          vo.setTaxsInclusive(CommonFunction.checkNull(subcharges.get(13)).trim());
          if (!CommonFunction.checkNull(subcharges.get(14)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(14)).trim());
            vo.setTaxtRat1(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(15)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(15)).trim());
            logger.info("setTaxtRat2: " + reconNum);
            vo.setTaxtRat2(this.myFormatter.format(reconNum));
          }
          vo.setMinChargeMethod(CommonFunction.checkNull(subcharges.get(17)).trim());
          vo.setMinChargeCalculatedOn(CommonFunction.checkNull(subcharges.get(18)).trim());
          vo.setDealChargeTaxApp(CommonFunction.checkNull(subcharges.get(19)).trim());
          vo.setDealChargeTdsApp(CommonFunction.checkNull(subcharges.get(20)).trim());
          if (!CommonFunction.checkNull(subcharges.get(21)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(21)).trim());
            vo.setDealChargeTaxAmount1(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(22)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(22)).trim());

            vo.setDealChargeTaxAmount2(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(23)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(23)).trim());
            vo.setDealChargeMinChargeAmount(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(24)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(24)).trim());

            vo.setDealChargeTdsRate(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(25)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(25)).trim());
            vo.setDealChargeTdsAmount(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(26)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(26)).trim());

            vo.setDealChargeNetAmount(this.myFormatter.format(reconNum));
          }
          vo.setApplStage(CommonFunction.checkNull(subcharges.get(27)));
          if (!CommonFunction.checkNull(subcharges.get(28)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(28)).trim());
            logger.info("setBalanceAmount: " + reconNum);
            vo.setBalanceAmount(this.myFormatter.format(reconNum));
          }
          if (!CommonFunction.checkNull(subcharges.get(29)).equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subcharges.get(29)).trim());
            logger.info("setImdAllocatedAmount: " + reconNum);
            vo.setImdAllocatedAmount(this.myFormatter.format(reconNum));
          }
          list.add(vo);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return list;
  }

  public String checkReciptStatusFromInventory(ImdMakerVO vo)
  {
    String result = "1";
    String inventoryFlag = "";
    String bookNoNonMandatory = "";
    String bookNoValidate = "";
    String bookNoValidate2 = "";
    String checkAllBranch = "";
    StringBuilder existCount = new StringBuilder();
    StringBuilder inventoryFlagInMst = new StringBuilder();
    StringBuilder existInStationary = new StringBuilder();
    StringBuilder allBranch = new StringBuilder();
    StringBuilder checkUser = new StringBuilder();
    StringBuilder nonMandatory = new StringBuilder();
    StringBuilder bookNoValidatation = new StringBuilder();
    StringBuilder bookNoValidation2 = new StringBuilder();
    String checkUserAvailable = "";
    String IssuingUser = vo.getMakerId();
    String receiptNo = vo.getReceiptNo();
    logger.info("receipt no::::::::::::::" + vo.getReceiptNo());
    int receiptChequeNo = 0;
    try
    {
      nonMandatory.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOOK_NO_NON_MANDATORY'");
      bookNoNonMandatory = ConnectionDAO.singleReturn(nonMandatory.toString());

      inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='INVENTORY_FLAG'");
      logger.info("IN checkPaymentStatusFromInventory inventoryFlag ### " + inventoryFlagInMst.toString());
      inventoryFlag = ConnectionDAO.singleReturn(inventoryFlagInMst.toString());
      if ((inventoryFlag.equalsIgnoreCase("Y")) && (!CommonFunction.checkNull(vo.getReceiptNo()).equalsIgnoreCase("")) && (CommonFunction.checkNull(vo.getStatusReceipt()).equalsIgnoreCase("M")))
      {
        allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl INNER JOIN CR_STATIONARY_DTL ON CR_STATIONARY_DTL.BOOK_NO=CR_STATIONARY_STATUS_DTL.BOOK_NO WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='" + vo.getReceiptNo() + "' AND STATUS='A' AND IFNULL(CR_STATIONARY_DTL.RETURN_TO_HO_FLAG,'')<>'R' ");
        logger.info("IN checkAllBranch ### " + allBranch.toString());
        checkAllBranch = ConnectionDAO.singleReturn(allBranch.toString());
        if (!CommonFunction.checkNull(receiptNo).equalsIgnoreCase(""))
        {
          receiptChequeNo = Integer.parseInt(receiptNo) - 1;
        }
        if (checkAllBranch.equalsIgnoreCase("0"))
        {
          if (bookNoNonMandatory.equalsIgnoreCase("N"))
          {
            checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + vo.getReceiptNo() + "' AND IFNULL(ISSUING_USER,'')<>''");
            logger.info("IN checkInventory inventoryFlag ### " + checkUser.toString());
            checkUserAvailable = ConnectionDAO.singleReturn(checkUser.toString());
          } else {
            checkUserAvailable = "1";
          }
          if (!checkUserAvailable.equalsIgnoreCase("0"))
          {
            existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + vo.getReceiptNo() + "'  AND IFNULL(ISSUING_USER,'" + IssuingUser + "')='" + IssuingUser + "' ");
            logger.info("IN checkUserAvailability  in existInStationary ### " + existInStationary.toString());
            result = ConnectionDAO.singleReturn(existInStationary.toString());
          } else {
            existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='" + vo.getReceiptNo() + "'  AND ISSUING_BRANCH='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDefaultBranch()).trim()) + "'");
            logger.info("IN checkReciptStatusFromInventory  in existInStationary ### " + existInStationary.toString());
            result = ConnectionDAO.singleReturn(existInStationary.toString());
          }
        } else {
          result = "1";
        }
        if (bookNoNonMandatory.equalsIgnoreCase("Y"))
        {
          bookNoValidatation.append(" SELECT BOOK_NO FROM CR_STATIONARY_STATUS_DTL WHERE RECEPT_CHEQUE_NO='" + vo.getReceiptNo() + "' AND STATUS='A'");
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

  public ArrayList<ImdMakerVO> imdDetailsGrid(ImdMakerVO vo, String dealId)
  {
    logger.info("In imdMakerGrid() of ImdDAOImpl");
    StringBuilder customerName = new StringBuilder();
    StringBuilder businessPartnerType = new StringBuilder();
    StringBuilder businessPartnerID = new StringBuilder();
    StringBuilder receiptAmount = new StringBuilder();
    StringBuilder instrumentNumber = new StringBuilder();
    StringBuilder receiptMode = new StringBuilder();
    logger.info("In imdMakerGrid() test deal id::" + dealId);

    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;
    ArrayList receiptMakerSearchGrid = new ArrayList();

    logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ " + vo.getReportingToUserId());
    try
    {
      ArrayList searchlist = new ArrayList();
      logger.info("In imdMakerGrid....................");
      StringBuffer bufInsSql = new StringBuffer();
      StringBuffer bufInsSqlTempCount = new StringBuffer();
      boolean appendSQL = false;

      logger.info("In imdMakerGrid......dealNo:::::::  " + dealId);
      customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
      businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
      businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
      if (!CommonFunction.checkNull(vo.getReceiptAmount()).equalsIgnoreCase(""))
      {
        receiptAmount.append(this.myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
        logger.info("In imdMakerGrid......receiptAmount  " + receiptAmount);
      }
      instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
      receiptMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptMode()).trim()));
      logger.info("In imdMakerGrid......receiptMode  " + receiptMode);

      bufInsSql.append(" select ifnull(b.deal_charge_code,'NA'),ifnull(C.CHARGE_DESC,'NOT ALLOCATED'),ifnull(b.imd_date,a.instrument_date),b.imd_amount,a.maker_id,date_format(a.maker_date,'%d-%m-%Y'),a.author_id,date_format(a.author_date,'%d-%m-%Y'),a.instrument_mode,a.instrument_amount,a.REC_STATUS,ifnull(B.REC_STATUS,a.REC_STATUS),a.instrument_id,ifnull(b.instrument_id,a.instrument_id),date_format(a.instrument_date,'%d-%m-%Y'),a.received_date,a.recipt_no");
      bufInsSql.append(" from cr_instrument_dtl a left join cr_imd_dtl b on a.txnid=b.deal_id and a.INSTRUMENT_ID=b.INSTRUMENT_ID ");
      bufInsSql.append(" left JOIN com_charge_code_m C ON C.CHARGE_CODE=B.DEAL_CHARGE_CODE where a.txn_type='DC' and a.txnid='" + dealId + "' ");

      bufInsSqlTempCount.append(" SELECT  count(1) ");
      bufInsSqlTempCount.append(" from cr_instrument_dtl a left join cr_imd_dtl b on a.txnid=b.deal_id  ");
      bufInsSqlTempCount.append(" where a.txn_type='DC' and a.txnid='" + dealId + "' ");

      LoggerMsg.info("current PAge Link no .................... " + vo.getCurrentPageLink());

      startRecordIndex = 0;
      endRecordIndex = this.no;
      bufInsSql.append(" limit " + startRecordIndex + "," + endRecordIndex);

      logger.info("imdMakerGrid    ........main query" + bufInsSql.toString());
      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

      logger.info("imdMakerGrid search Data size is...." + searchlist.size());

      logger.info("imdMakerGrid    .......count query." + bufInsSqlTempCount.toString());
      for (int i = 0; i < searchlist.size(); i++) {
        logger.info("ImdMakerGrid search List## " + searchlist.get(i).toString());
        ArrayList data = (ArrayList)searchlist.get(i);
        logger.info("ImdMakerGrid CommonFunction.checkNull(data.get(10) " + CommonFunction.checkNull(data.get(10)));
        if (data.size() > 0) {
          ImdMakerVO receiptvo = new ImdMakerVO();
          receiptvo.setChargeCode(CommonFunction.checkNull(data.get(0)).trim());
          if (CommonFunction.checkNull(data.get(12)).equalsIgnoreCase(CommonFunction.checkNull(data.get(13))))
          {
            receiptvo.setChargeDesc(CommonFunction.checkNull(data.get(1)).trim());
          }
          else
          {
            receiptvo.setChargeDesc("NOT ALLOCATED");
          }

          if (CommonFunction.checkNull(data.get(12)).equalsIgnoreCase(CommonFunction.checkNull(data.get(13))))
          {
            receiptvo.setImdDate(CommonFunction.checkNull(data.get(2)).trim());
          }
          else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("C"))
          {
            receiptvo.setImdDate(CommonFunction.checkNull(data.get(15)).trim());
          }
          else
          {
            receiptvo.setImdDate(CommonFunction.checkNull(data.get(14)).trim());
          }

          if (CommonFunction.checkNull(data.get(12)).equalsIgnoreCase(CommonFunction.checkNull(data.get(13))))
          {
            receiptvo.setImdAmount(CommonFunction.checkNull(data.get(3)).trim());
          }
          else
          {
            receiptvo.setImdAmount(CommonFunction.checkNull(data.get(9)).trim());
          }
          receiptvo.setMakerId(CommonFunction.checkNull(data.get(4)).trim());
          receiptvo.setMakerDate(CommonFunction.checkNull(data.get(5)).trim());
          receiptvo.setAuthorId(CommonFunction.checkNull(data.get(6)).trim());
          receiptvo.setAuthorDate(CommonFunction.checkNull(data.get(7)).toString());
          receiptvo.setInstrumentMode(CommonFunction.checkNull(data.get(8)).trim());
          if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("C")) {
            receiptvo.setInstrumentMode("Cash");
          }
          else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("Q")) {
            receiptvo.setInstrumentMode("Cheque");
          }
          else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("D")) {
            receiptvo.setInstrumentMode("DD");
          }
          else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("N")) {
            receiptvo.setInstrumentMode("NEFT");
          }
          else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("R")) {
            receiptvo.setInstrumentMode("RTGS");
          }
          else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("S")) {
            receiptvo.setInstrumentMode("Adjustment");
          }
          else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("DIR")) {
            receiptvo.setInstrumentMode("Direct Debit");
          }
          receiptvo.setInstrumentAmount(CommonFunction.checkNull(data.get(9)).trim());

          if (CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("P")) {
            receiptvo.setStatus("Pending");
          }
          else if (CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("B")) {
            receiptvo.setStatus("Bounced");
          }
          else if (CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("D")) {
            receiptvo.setStatus("Deposited");
          }
          else if (CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("R")) {
            receiptvo.setStatus("Realized");
          }
          else if (CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("X")) {
            receiptvo.setStatus("Cancelled");
          }
          else if (CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("A")) {
            receiptvo.setStatus("Approved");
          }
          else if (CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("F")) {
            receiptvo.setStatus("Forwarded");
          }

          if (CommonFunction.checkNull(data.get(12)).equalsIgnoreCase(CommonFunction.checkNull(data.get(13))))
          {
            receiptvo.setImdStatus(CommonFunction.checkNull(data.get(11)).trim());
            if (CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("P")) {
              receiptvo.setImdStatus("Pending");
            }
            else if (CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("F")) {
              receiptvo.setImdStatus("Forwarded");
            }
            else if (CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("A")) {
              receiptvo.setImdStatus("Approved");
            }
            else if (CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("B")) {
              receiptvo.setImdStatus("Bounced");
            }
            else if (CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("D")) {
              receiptvo.setImdStatus("Deposited");
            }
            else if (CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("R")) {
              receiptvo.setImdStatus("Realized");
            }
            else if (CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("X")) {
              receiptvo.setImdStatus("Cancelled");
            }

          }
          else
          {
            receiptvo.setImdStatus("NA");
          }

          receiptvo.setInstrumentID(CommonFunction.checkNull(data.get(12)).trim());
          receiptvo.setInstrument(CommonFunction.checkNull(data.get(13)).trim());
          receiptvo.setInstrumentDate(CommonFunction.checkNull(data.get(14)).trim());
          receiptvo.setReceivedDate(CommonFunction.checkNull(data.get(15)).trim());
          receiptvo.setReceiptNo(CommonFunction.checkNull(data.get(16)).trim());
          receiptMakerSearchGrid.add(receiptvo);
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      dealId = null;
      customerName = null;
      businessPartnerType = null;
      businessPartnerID = null;
      receiptAmount = null;
      instrumentNumber = null;
      receiptMode = null;
    }
    return receiptMakerSearchGrid;
  }

@Override
public ArrayList<ImdMakerVO> getpreDealImdList(ImdMakerVO vo) {
    logger.info("In getpreDealImdList() of ImdDAOImpl");

    ArrayList savedReceipt = new ArrayList();
    try {
      ArrayList searchlist = new ArrayList();
      logger.info("In getpreDealImdList....................");
      StringBuffer bufInsSql = new StringBuffer();

      bufInsSql.append("SELECT DISTINCT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,BPV.BP_TYPE,GM.DESCRIPTION,BPV.BP_ID ,BPV.BP_NAME ,CDL.REC_STATUS,CDL.DEAL_BRANCH ");
      bufInsSql.append("from cr_deal_dtl CDL ");
      bufInsSql.append("join cr_deal_customer_m GCM on GCM.CUSTOMER_ID=CDL.DEAL_CUSTOMER_ID ");
      bufInsSql.append("join business_partner_view BPV on CDL.DEAL_ID=BPV.DEAL_ID ");
      bufInsSql.append("join generic_master  GM on  GM.GENERIC_KEY='BPTYPE' and GM.VALUE=BPV.BP_TYPE ");
      bufInsSql.append("where CDL.DEAL_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()) + "' ");
     
      

      logger.info("In savedReceipt......... query111..........." + bufInsSql.toString());
      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      logger.info("savedReceipt search Data size is...." + searchlist.size());

      for (int i = 0; i < searchlist.size(); i++) {
        logger.info("savedReceipt search List " + searchlist.get(i).toString());
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          ImdMakerVO receiptvo = new ImdMakerVO();
          receiptvo.setLbxDealNo(CommonFunction.checkNull(data.get(0)).trim());
          receiptvo.setDealNo(CommonFunction.checkNull(data.get(1)).trim());
          receiptvo.setCustomerName(CommonFunction.checkNull(data.get(2)).trim());
          receiptvo.setLbxBPType(CommonFunction.checkNull(data.get(3)).trim());
          receiptvo.setBusinessPartnerType(CommonFunction.checkNull(data.get(4)).trim());
          receiptvo.setLbxBPNID(CommonFunction.checkNull(data.get(5)).trim());
          receiptvo.setBusinessPartnerName(CommonFunction.checkNull(data.get(6)).trim());
          receiptvo.setLoanRecStatus(CommonFunction.checkNull(data.get(7)).trim());
          receiptvo.setLoanBranch(CommonFunction.checkNull(data.get(8)).trim());
          savedReceipt.add(receiptvo);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return savedReceipt;
  }
}