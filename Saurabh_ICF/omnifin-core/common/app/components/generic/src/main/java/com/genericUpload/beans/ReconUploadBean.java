package com.genericUpload.beans;

import java.math.BigDecimal;
import java.text.*;
import java.util.Date;

import com.genericUpload.daoImplMYSQL.GenericUploadDAOImpl;

public class ReconUploadBean 
{
	private int serial_no;
	private String voucher_bank;
	 private String loan_no;
	private String voucher_no;
	private int sequence_no;
	private BigDecimal voucher_dr_amt;
	private BigDecimal voucher_cr_amt;
	private long bank_transaction_id;
	private BigDecimal bank_dr_amt;
	private BigDecimal bank_cr_amt;
	private long bank_ledger_id;
	private String recon_type;
	private String recon_date;
	private String batch_id;
	private String user_id;
	
	
	public ReconUploadBean() 
	{
		BatchIDAndUserIDBean b = GenericUploadDAOImpl.getBatchIDAndUserIDBean();
		user_id = b.getUserid();
		batch_id = b.getBatchid();
	}
	public int getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(int serial_no) {
		this.serial_no = serial_no;
	}
	public String getVoucher_bank() {
		return voucher_bank;
	}
	public void setVoucher_bank(String voucher_bank) {
		this.voucher_bank = voucher_bank;
	}
	public String getVoucher_no() {
		return voucher_no;
	}
	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
	}
	public int getSequence_no() {
		return sequence_no;
	}
	public void setSequence_no(int sequence_no) {
		this.sequence_no = sequence_no;
	}
	public BigDecimal getVoucher_dr_amt() {
		return voucher_dr_amt;
	}
	public void setVoucher_dr_amt(BigDecimal voucher_dr_amt) {
		this.voucher_dr_amt = voucher_dr_amt;
	}
	public BigDecimal getVoucher_cr_amt() {
		return voucher_cr_amt;
	}
	public void setVoucher_cr_amt(BigDecimal voucher_cr_amt) {
		this.voucher_cr_amt = voucher_cr_amt;
	}
	public long getBank_transaction_id() {
		return bank_transaction_id;
	}
	public void setBank_transaction_id(long bank_transaction_id) {
		this.bank_transaction_id = bank_transaction_id;
	}
	public BigDecimal getBank_dr_amt() {
		return bank_dr_amt;
	}
	public void setBank_dr_amt(BigDecimal bank_dr_amt) {
		this.bank_dr_amt = bank_dr_amt;
	}
	public BigDecimal getBank_cr_amt() {
		return bank_cr_amt;
	}
	public void setBank_cr_amt(BigDecimal bank_cr_amt) {
		this.bank_cr_amt = bank_cr_amt;
	}
	public long getBank_ledger_id() {
		return bank_ledger_id;
	}
	public void setBank_ledger_id(long bank_ledger_id) {
		this.bank_ledger_id = bank_ledger_id;
	}
	public String getRecon_type() {
		return recon_type;
	}
	public void setRecon_type(String recon_type) {
		this.recon_type = recon_type;
	}
	public String getRecon_date() {
		return recon_date;
	}
	public void setRecon_date(String recon_date) 
	{
		try
		{
			String dateReceivedFromUser = recon_date;  
			DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
			DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
			Date date = userDateFormat.parse(dateReceivedFromUser);  
			String convertedDate = dateFormatNeeded.format(date); 
			this.recon_date = convertedDate;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	 public String getLoan_no() {
		    return this.loan_no;
		  }
		  public void setLoan_no(String loan_no) {
		    this.loan_no = loan_no;
		  }
	
	
	
	
}
