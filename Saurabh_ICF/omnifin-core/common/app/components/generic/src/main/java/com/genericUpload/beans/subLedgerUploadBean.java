package com.genericUpload.beans;

import com.genericUpload.daoImplMYSQL.GenericUploadDAOImpl;

public class subLedgerUploadBean {
	private String user_id;
	private String batch_id;
	private String sl_code;
	private String sl_desc;
	private String selective_All;
	private String ledgers;
	private String serial_no;
	
	public subLedgerUploadBean()
	{
		BatchIDAndUserIDBean b = GenericUploadDAOImpl.getBatchIDAndUserIDBean();
		user_id = b.getUserid();
		batch_id = b.getBatchid();
	}
	public String getUser_id() {
		return user_id.trim();
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getBatch_id() {
		return batch_id.trim();
	}


	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}



	public String getSl_code() {
		return sl_code.trim();
	}



	public void setSl_code(String sl_code) {
		this.sl_code = sl_code;
	}



	public String getSl_desc() {
		return sl_desc.trim();
	}



	public void setSl_desc(String sl_desc) {
		this.sl_desc = sl_desc;
	}



	public String getSelective_All() {
		return selective_All.trim();
	}



	public void setSelective_All(String selective_All) {
		this.selective_All = selective_All;
	}



	public String getLedgers() {
		return ledgers.trim();
	}



	public void setLedgers(String ledgers) {
		this.ledgers = ledgers;
	}
	public String getSerial_no() {
		return serial_no.trim();
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
}
