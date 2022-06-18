package com.cm.vo;

public class KnockOffSearchVO {
	private String loanNo;
	private String lbxLoanNoHID;
	private String customerName;
	private String businessPartnerType;
	private String lbxBPNID;
	private String businessPartnerName;
	private String knockOffAmount;
	private String knockOffId;
	private String branchId;
	private String userId;
	private String stage;
	private String userName;
	private String reportingToUserId;
	private String lbxUserId;
	
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getReportingToUserId() {
		return reportingToUserId;
	}
	public void setReportingToUserId(String reportingToUserId) {
		this.reportingToUserId = reportingToUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private int currentPageLink;
	private int totalRecordSize;
	
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getKnockOffId() {
		return knockOffId;
	}
	public void setKnockOffId(String knockOffId) {
		this.knockOffId = knockOffId;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getBusinessPartnerType() {
		return businessPartnerType;
	}
	public void setBusinessPartnerType(String businessPartnerType) {
		this.businessPartnerType = businessPartnerType;
	}
	public String getLbxBPNID() {
		return lbxBPNID;
	}
	public void setLbxBPNID(String lbxBPNID) {
		this.lbxBPNID = lbxBPNID;
	}
	public String getBusinessPartnerName() {
		return businessPartnerName;
	}
	public void setBusinessPartnerName(String businessPartnerName) {
		this.businessPartnerName = businessPartnerName;
	}
	public String getKnockOffAmount() {
		return knockOffAmount;
	}
	public void setKnockOffAmount(String knockOffAmount) {
		this.knockOffAmount = knockOffAmount;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
}
