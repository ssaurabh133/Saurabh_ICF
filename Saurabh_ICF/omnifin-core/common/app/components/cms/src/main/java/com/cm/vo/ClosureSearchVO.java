package com.cm.vo;

public class ClosureSearchVO {
	
	private String lbxLoanNoHID;
	private String loanAc;
	private String customerName;
	private String product;
	private String scheme;
	private String loanAmount;
	private String loanDate;
	private String terminationId;
	private String branchId;
	private String userId;
	private String stage;
	private String lbxUserId;
	private String allBranches;
	private String lbxBranchId;

	public String getAllBranches() {
		return allBranches;
	}
	public void setAllBranches(String allBranches) {
		this.allBranches = allBranches;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	private String reportingToUserId;
	private int currentPageLink;
	private int totalRecordSize;
	
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
	public String getTerminationId() {
		return terminationId;
	}
	public void setTerminationId(String terminationId) {
		this.terminationId = terminationId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getLoanAc() {
		return loanAc;
	}
	public void setLoanAc(String loanAc) {
		this.loanAc = loanAc;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
