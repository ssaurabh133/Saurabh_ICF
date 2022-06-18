package com.cm.vo;

public class PostDisbursalDocVO {
	private String loanNo;
	private String lbxLoanNoHID;
	private String lbxDealNo;
	private String dealNo;
	private String docStage;
	private String stageValue;
	private String stageDescription;
	private String customerName;
	private String loanAmt;
	private String loanApprovalDate;
	private String product;
	private String scheme;
	private String userId;
	private int currentPageLink;
	private int totalRecordSize;
	private String searchStatusPDD;
	
	
	public String getSearchStatusPDD() {
		return searchStatusPDD;
	}
	public void setSearchStatusPDD(String searchStatusPDD) {
		this.searchStatusPDD = searchStatusPDD;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getLbxDealNo() {
		return lbxDealNo;
	}
	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getDocStage() {
		return docStage;
	}
	public void setDocStage(String docStage) {
		this.docStage = docStage;
	}
	public void setStageDescription(String stageDescription) {
		this.stageDescription = stageDescription;
	}
	public String getStageDescription() {
		return stageDescription;
	}
	public void setStageValue(String stageValue) {
		this.stageValue = stageValue;
	}
	public String getStageValue() {
		return stageValue;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getLoanApprovalDate() {
		return loanApprovalDate;
	}
	public void setLoanApprovalDate(String loanApprovalDate) {
		this.loanApprovalDate = loanApprovalDate;
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
