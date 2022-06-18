package com.genericUpload.dto;

import java.io.Serializable;



public class VoucherUploadParametersDTO implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private String ktrpath;
	private String excelfilepath;
	private String batch_id;
	private String user_id;
	private String company_id;
	private String branch_id;
	private String business_date;
	private String filename;
	private String extra;
	private int total_number_of_vouchers;
	private String voucher_date;
	private boolean isValidated;
	
	
	public String getKtrpath() {
		return ktrpath;
	}
	public void setKtrpath(String ktrpath) {
		this.ktrpath = ktrpath;
	}
	public String getExcelfilepath() {
		return excelfilepath;
	}
	public void setExcelfilepath(String excelfilepath) {
		this.excelfilepath = excelfilepath;
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
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public String getBusiness_date() {
		return business_date;
	}
	public void setBusiness_date(String business_date) {
		this.business_date = business_date;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public int getTotal_number_of_vouchers() {
		return total_number_of_vouchers;
	}
	public void setTotal_number_of_vouchers(int total_number_of_vouchers) {
		this.total_number_of_vouchers = total_number_of_vouchers;
	}
	public boolean isValidated() {
		return isValidated;
	}
	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
	public String getVoucher_date() {
		return voucher_date;
	}
	public void setVoucher_date(String voucher_date) {
		this.voucher_date = voucher_date;
	}
	
	
	
}

