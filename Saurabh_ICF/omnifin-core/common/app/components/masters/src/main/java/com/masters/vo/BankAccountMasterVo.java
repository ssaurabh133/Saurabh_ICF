package com.masters.vo;

import java.io.Serializable;

public class BankAccountMasterVo implements Serializable{


	private String bankCode;
	private String bankCodeModify;
	private String bankBranchName;
	private String ifscCode;
	private String micrCode;		
	private String makerId;
	private String makerDate;
	private String accountNo;
	private String glCode;	
	private String bankAccountStatus;
	private String lbxBankID;
	private String bankSearchCode;
	private String bankBranchSearchName;
	private String lbxBranchID;
	private String lbxBankSearchID;
	private String lbxBranchSearchID;
	private String clientCode;
	//Manish
	private String drawingPower;
	
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	private String accountType;
	private int currentPageLink;
	private int totalRecordSize;
	
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getBankCodeModify() {
		return bankCodeModify;
	}
	public void setBankCodeModify(String bankCodeModify) {
		this.bankCodeModify = bankCodeModify;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getMicrCode() {
		return micrCode;
	}
	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public String getBankAccountStatus() {
		return bankAccountStatus;
	}
	public void setBankAccountStatus(String bankAccountStatus) {
		this.bankAccountStatus = bankAccountStatus;
	}
	public String getLbxBankID() {
		return lbxBankID;
	}
	public void setLbxBankID(String lbxBankID) {
		this.lbxBankID = lbxBankID;
	}
	public String getBankSearchCode() {
		return bankSearchCode;
	}
	public void setBankSearchCode(String bankSearchCode) {
		this.bankSearchCode = bankSearchCode;
	}
	public String getBankBranchSearchName() {
		return bankBranchSearchName;
	}
	public void setBankBranchSearchName(String bankBranchSearchName) {
		this.bankBranchSearchName = bankBranchSearchName;
	}
	public String getLbxBranchID() {
		return lbxBranchID;
	}
	public void setLbxBranchID(String lbxBranchID) {
		this.lbxBranchID = lbxBranchID;
	}
	public String getLbxBankSearchID() {
		return lbxBankSearchID;
	}
	public void setLbxBankSearchID(String lbxBankSearchID) {
		this.lbxBankSearchID = lbxBankSearchID;
	}
	public String getLbxBranchSearchID() {
		return lbxBranchSearchID;
	}
	public void setLbxBranchSearchID(String lbxBranchSearchID) {
		this.lbxBranchSearchID = lbxBranchSearchID;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountType() {
		return accountType;
	}
	public String getDrawingPower() {
		return drawingPower;
	}
	public void setDrawingPower(String drawingPower) {
		this.drawingPower = drawingPower;
	}

}
