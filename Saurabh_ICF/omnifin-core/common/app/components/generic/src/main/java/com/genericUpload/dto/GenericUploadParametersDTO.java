package com.genericUpload.dto;

import java.io.Serializable;



public class GenericUploadParametersDTO implements Serializable
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
	private int total_number_of_records;
	private String voucher_date;
	private boolean isValidated;
	private String smooksfilepath;
	private String functionid;
	private String maker_proc_name;
	private String author_proc_name;
	private String tame_table_name;
	
	
	
	
	public String getTame_table_name() {
		return tame_table_name;
	}
	public void setTame_table_name(String tame_table_name) {
		this.tame_table_name = tame_table_name;
	}
	public String getMaker_proc_name() {
		return maker_proc_name;
	}
	public void setMaker_proc_name(String maker_proc_name) {
		this.maker_proc_name = maker_proc_name;
	}
	public String getAuthor_proc_name() {
		return author_proc_name;
	}
	public void setAuthor_proc_name(String author_proc_name) {
		this.author_proc_name = author_proc_name;
	}
	public String getFunctionid() {
		return functionid;
	}
	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}
	public String getSmooksfilepath() {
		return smooksfilepath;
	}
	public void setSmooksfilepath(String smooksfilepath) {
		this.smooksfilepath = smooksfilepath;
	}
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
	
	public int getTotal_number_of_records() {
		return total_number_of_records;
	}
	public void setTotal_number_of_records(int total_number_of_records) {
		this.total_number_of_records = total_number_of_records;
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

