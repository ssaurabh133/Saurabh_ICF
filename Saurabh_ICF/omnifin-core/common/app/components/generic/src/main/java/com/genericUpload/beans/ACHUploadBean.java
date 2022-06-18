package com.genericUpload.beans;

import com.genericUpload.beans.BatchIDAndUserIDBean;
import com.genericUpload.daoImplMYSQL.GenericUploadDAOImpl;

public class ACHUploadBean {
	private String user_id;
	private String batch_id;
	private String LOAN_ID;
	private String UMRN_NO;
	private String ACH_STATUS;
	private String FACILITY;
	private String VENDOR_NAME;
	private String serial_no;
	private String STATUS_DATE;
	private String REMARKS;
	
	public ACHUploadBean()
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
	
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	

	
	public String getUMRN_NO() {
		return UMRN_NO;
	}
	public void setUMRN_NO(String uMRN_NO) {
		UMRN_NO = uMRN_NO;
	}
	
	public String getVENDOR_NAME() {
		return VENDOR_NAME;
	}
	public void setVENDOR_NAME(String vENDOR_NAME) {
		VENDOR_NAME = vENDOR_NAME;
	}
	public String getACH_STATUS() {
		return ACH_STATUS;
	}
	public void setACH_STATUS(String aCH_STATUS) {
		ACH_STATUS = aCH_STATUS;
	}
	public String getFACILITY() {
		return FACILITY;
	}
	public void setFACILITY(String fACILITY) {
		FACILITY = fACILITY;
	}
	public String getSTATUS_DATE() {
		return STATUS_DATE;
	}
	public void setSTATUS_DATE(String sTATUS_DATE) {
		STATUS_DATE = sTATUS_DATE;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}
	public String getLOAN_ID() {
		return LOAN_ID;
	}
	public void setLOAN_ID(String lOAN_ID) {
		LOAN_ID = lOAN_ID;
	}


	
}
