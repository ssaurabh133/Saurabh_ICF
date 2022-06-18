package com.genericUpload.beans;

import java.math.BigDecimal;

import com.genericUpload.daoImplMYSQL.GenericUploadDAOImpl;

public class InstrumentUploadBean 
{
	private String serial_no;
	private String loan_id;
	private String instrument_mode;
	private String purpose;
	private String pdc_ecs_given_by;
	private String name_of_pdc_ecs_givenby;
	private String from_installment;
	private String to_installment;
	private String starting_instrument_no;
	private String ending_instrument_no;
	private String bank_id;
	private String branch_id;
	private BigDecimal instrument_amount;
	private String bank_account;
	private String location;
	private String customer_ac_type;
	private String clearing_type;
	private String ecs_transaction_code;
	private String sponsor_bank_branch_code;
	private String utility_number;
	private String bin_no;
	private String instrument_micr_nonmicr;
	private String maker_remark;
	private String user_id;
	private String batch_id;
	
	
	
	
	public InstrumentUploadBean()
	{
		BatchIDAndUserIDBean b = GenericUploadDAOImpl.getBatchIDAndUserIDBean();
		user_id = b.getUserid();
		batch_id = b.getBatchid();
	}

	
	
	public String getSerial_no() {
		return serial_no;
	}



	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}



	public String getLoan_id() {
		return loan_id;
	}


	public void setLoan_id(String loan_id) {
		this.loan_id = loan_id;
	}


	public String getInstrument_mode() {
		return instrument_mode;
	}


	public void setInstrument_mode(String instrument_mode) {
		this.instrument_mode = instrument_mode;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public String getPdc_ecs_given_by() {
		return pdc_ecs_given_by;
	}


	public void setPdc_ecs_given_by(String pdc_ecs_given_by) {
		this.pdc_ecs_given_by = pdc_ecs_given_by;
	}


	public String getName_of_pdc_ecs_givenby() {
		return name_of_pdc_ecs_givenby;
	}


	public void setName_of_pdc_ecs_givenby(String name_of_pdc_ecs_givenby) {
		this.name_of_pdc_ecs_givenby = name_of_pdc_ecs_givenby;
	}


	public String getFrom_installment() {
		return from_installment;
	}


	public void setFrom_installment(String from_installment) {
		this.from_installment = from_installment;
	}


	public String getTo_installment() {
		return to_installment;
	}


	public void setTo_installment(String to_installment) {
		this.to_installment = to_installment;
	}


	public String getStarting_instrument_no() {
		return starting_instrument_no;
	}


	public void setStarting_instrument_no(String starting_instrument_no) {
		this.starting_instrument_no = starting_instrument_no;
	}


	public String getEnding_instrument_no() {
		return ending_instrument_no;
	}


	public void setEnding_instrument_no(String ending_instrument_no) {
		this.ending_instrument_no = ending_instrument_no;
	}


	public String getBank_id() {
		return bank_id;
	}


	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}


	public String getBranch_id() {
		return branch_id;
	}


	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}


	public BigDecimal getInstrument_amount() {
		return instrument_amount;
	}


	public void setInstrument_amount(BigDecimal instrument_amount) {
		this.instrument_amount = instrument_amount;
	}


	public String getBank_account() {
		return bank_account;
	}


	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getCustomer_ac_type() {
		return customer_ac_type;
	}


	public void setCustomer_ac_type(String customer_ac_type) {
		this.customer_ac_type = customer_ac_type;
	}


	public String getClearing_type() {
		return clearing_type;
	}


	public void setClearing_type(String clearing_type) {
		this.clearing_type = clearing_type;
	}


	public String getEcs_transaction_code() {
		return ecs_transaction_code;
	}


	public void setEcs_transaction_code(String ecs_transaction_code) {
		this.ecs_transaction_code = ecs_transaction_code;
	}


	public String getSponsor_bank_branch_code() {
		return sponsor_bank_branch_code;
	}


	public void setSponsor_bank_branch_code(String sponsor_bank_branch_code) {
		this.sponsor_bank_branch_code = sponsor_bank_branch_code;
	}


	public String getUtility_number() {
		return utility_number;
	}


	public void setUtility_number(String utility_number) {
		this.utility_number = utility_number;
	}


	public String getBin_no() {
		return bin_no;
	}


	public void setBin_no(String bin_no) {
		this.bin_no = bin_no;
	}


	public String getInstrument_micr_nonmicr() {
		return instrument_micr_nonmicr;
	}


	public void setInstrument_micr_nonmicr(String instrument_micr_nonmicr) {
		this.instrument_micr_nonmicr = instrument_micr_nonmicr;
	}


	public String getMaker_remark() {
		return maker_remark;
	}


	public void setMaker_remark(String maker_remark) {
		this.maker_remark = maker_remark;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getBatch_id() {
		return batch_id;
	}


	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}




	
	
	
}
