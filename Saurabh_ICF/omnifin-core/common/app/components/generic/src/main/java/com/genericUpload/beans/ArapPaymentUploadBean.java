package com.genericUpload.beans;

import com.genericUpload.daoImplMYSQL.GenericUploadDAOImpl;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArapPaymentUploadBean
{
  private int serial_no;
  private int bill_gen_id;
  private int supplier_id;
  private String bill_no;
  private String settlement_mode;
  private String settlement_date;
  private BigDecimal settlement_amount;
  private String bank_account_no;
  private int bank_id;
  private int bank_branch_id;
  private String branch_micr;
  private String ifsc;
  private String instrument_no;
  private String maker_remarks;
  private String batch_id;
  private String user_id;

  public int getBill_gen_id()
  {
    return this.bill_gen_id;
  }

  public void setBill_gen_id(int bill_gen_id)
  {
    this.bill_gen_id = bill_gen_id;
  }

  public ArapPaymentUploadBean()
  {
    BatchIDAndUserIDBean b = GenericUploadDAOImpl.getBatchIDAndUserIDBean();
    this.user_id = b.getUserid();
    this.batch_id = b.getBatchid();
  }

  public String getMaker_remarks()
  {
    return this.maker_remarks;
  }

  public void setMaker_remarks(String maker_remarks)
  {
    this.maker_remarks = maker_remarks;
  }

  public int getSerial_no()
  {
    return this.serial_no;
  }
  public void setSerial_no(int serial_no) {
    this.serial_no = serial_no;
  }

  public String getBill_no() {
    return this.bill_no;
  }

  public void setBill_no(String bill_no) {
    this.bill_no = bill_no;
  }

  public String getSettlement_mode() {
    return this.settlement_mode;
  }
  public void setSettlement_mode(String settlement_mode) {
    this.settlement_mode = settlement_mode;
  }
  public String getSettlement_date() {
    return this.settlement_date;
  }

  public void setSettlement_date(String settlement_date)
  {
    try {
      String dateReceivedFromUser = settlement_date;
      DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");
      DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
      Date date = userDateFormat.parse(dateReceivedFromUser);
      String convertedDate = dateFormatNeeded.format(date);
      this.settlement_date = convertedDate;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public BigDecimal getSettlement_amount() {
    return this.settlement_amount;
  }
  public void setSettlement_amount(BigDecimal settlement_amount) {
    this.settlement_amount = settlement_amount;
  }

  public String getBank_account_no()
  {
    return this.bank_account_no;
  }

  public void setBank_account_no(String bank_account_no)
  {
    this.bank_account_no = bank_account_no;
  }

  public int getBank_id()
  {
    return this.bank_id;
  }
  public void setBank_id(int bank_id) {
    this.bank_id = bank_id;
  }
  public int getBank_branch_id() {
    return this.bank_branch_id;
  }
  public void setBank_branch_id(int bank_branch_id) {
    this.bank_branch_id = bank_branch_id;
  }
  public String getBranch_micr() {
    return this.branch_micr;
  }
  public void setBranch_micr(String branch_micr) {
    this.branch_micr = branch_micr;
  }
  public String getIfsc() {
    return this.ifsc;
  }
  public void setIfsc(String ifsc) {
    this.ifsc = ifsc;
  }
  public String getInstrument_no() {
    return this.instrument_no;
  }
  public void setInstrument_no(String instrument_no) {
    this.instrument_no = instrument_no;
  }
  public String getBatch_id() {
    return this.batch_id;
  }
  public void setBatch_id(String batch_id) {
    this.batch_id = batch_id;
  }
  public String getUser_id() {
    return this.user_id;
  }
  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public int getSupplier_id() {
    return this.supplier_id;
  }

  public void setSupplier_id(int supplier_id) {
    this.supplier_id = supplier_id;
  }
}