package com.masters.vo;

import java.io.Serializable;

public class BankMasterVo implements Serializable{
	private String bankCode;
	private String bankName;
	//private String micrCode;
	private String bankStatus;
	private String makerId;
	private String makerDate;
	private String bankSearchCode;
	private String bankSearchName;
	private String bankCodeModify;
	private int currentPageLink;
	private int totalRecordSize;
	
	
	
	
	
	
	
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankName() {
		return bankName;
	}
	
	public void setBankStatus(String bankStatus) {
		this.bankStatus = bankStatus;
	}
	public String getBankStatus() {
		return bankStatus;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setBankSearchCode(String bankSearchCode) {
		this.bankSearchCode = bankSearchCode;
	}
	public String getBankSearchCode() {
		return bankSearchCode;
	}
	public void setBankSearchName(String bankSearchName) {
		this.bankSearchName = bankSearchName;
	}
	public String getBankSearchName() {
		return bankSearchName;
	}
	public void setBankCodeModify(String bankCodeModify) {
		this.bankCodeModify = bankCodeModify;
	}
	public String getBankCodeModify() {
		return bankCodeModify;
	}


}
