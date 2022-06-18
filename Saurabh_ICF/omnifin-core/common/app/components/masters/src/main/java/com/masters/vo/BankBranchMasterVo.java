package com.masters.vo;

import java.io.Serializable;

public class BankBranchMasterVo implements Serializable{

	private String bankBranchCode;
	private String bankBranchName;
	private String branchMICRCode;
	private String branchIFCSCode;
	private String bank;
	private String contactPerson;
	private String personDesignation;
	private String address1;
	private String address2;
	private String address3;
	private String dist;
	private String state;
	private String country;
	private String zipCode;
	private String phone1;
	private String phone2;
	private String fax;
	private String email;
	private String makerId;
	private String makerDate;
	private String bankBranchSearchCode;
	private String bankBranchSearchName;
	private String txtCountryCode;
	private String txtStateCode;
	private String txtDistCode;
	private String lbxBankID;
	private String bankBranchStatus;
	private String bankBranchCodeModify;
	private int currentPageLink;
	private int totalRecordSize;
	private String  defaultcountryid;
	private String  defaultcountryname;
	private String  ecsStatus; 
	private String bankBranchId;
	private String  achStatus; 
	
	public String getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(String bankBranchId) {
		this.bankBranchId = bankBranchId;
	}
	public String getDefaultcountryid() {
		return defaultcountryid;
	}
	public void setDefaultcountryid(String defaultcountryid) {
		this.defaultcountryid = defaultcountryid;
	}
	public String getDefaultcountryname() {
		return defaultcountryname;
	}
	public void setDefaultcountryname(String defaultcountryname) {
		this.defaultcountryname = defaultcountryname;
	}
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
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}
	public String getLbxBankID() {
		return lbxBankID;
	}
	public void setLbxBankID(String lbxBankID) {
		this.lbxBankID = lbxBankID;
	}
	public String getTxtCountryCode() {
		return txtCountryCode;
	}
	public void setTxtCountryCode(String txtCountryCode) {
		this.txtCountryCode = txtCountryCode;
	}
	public String getTxtStateCode() {
		return txtStateCode;
	}
	public void setTxtStateCode(String txtStateCode) {
		this.txtStateCode = txtStateCode;
	}
	public String getTxtDistCode() {
		return txtDistCode;
	}
	public void setTxtDistCode(String txtDistCode) {
		this.txtDistCode = txtDistCode;
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
	public String getBankBranchCode() {
		return bankBranchCode;
	}
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getBranchMICRCode() {
		return branchMICRCode;
	}
	public void setBranchMICRCode(String branchMICRCode) {
		this.branchMICRCode = branchMICRCode;
	}
	public String getBranchIFCSCode() {
		return branchIFCSCode;
	}
	public void setBranchIFCSCode(String branchIFCSCode) {
		this.branchIFCSCode = branchIFCSCode;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getPersonDesignation() {
		return personDesignation;
	}
	public void setPersonDesignation(String personDesignation) {
		this.personDesignation = personDesignation;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setBankBranchSearchCode(String bankBranchSearchCode) {
		this.bankBranchSearchCode = bankBranchSearchCode;
	}
	public String getBankBranchSearchCode() {
		return bankBranchSearchCode;
	}
	public void setBankBranchSearchName(String bankBranchSearchName) {
		this.bankBranchSearchName = bankBranchSearchName;
	}
	public String getBankBranchSearchName() {
		return bankBranchSearchName;
	}
	public void setBankBranchStatus(String bankBranchStatus) {
		this.bankBranchStatus = bankBranchStatus;
	}
	public String getBankBranchStatus() {
		return bankBranchStatus;
	}
	public void setBankBranchCodeModify(String bankBranchCodeModify) {
		this.bankBranchCodeModify = bankBranchCodeModify;
	}
	public String getBankBranchCodeModify() {
		return bankBranchCodeModify;
	}
	public void setEcsStatus(String ecsStatus) {
		this.ecsStatus = ecsStatus;
	}
	public String getEcsStatus() {
		return ecsStatus;
	}
	public String getAchStatus() {
		return achStatus;
	}
	public void setAchStatus(String achStatus) {
		this.achStatus = achStatus;
	}
	
}
