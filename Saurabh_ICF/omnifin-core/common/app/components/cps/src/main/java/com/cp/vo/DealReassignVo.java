package com.cp.vo;

public class DealReassignVo {
	
	private String userId;	
	private String dealNo;
	private String 	userName;
	private String 	branchid;
	private String  lbxDealNo;
	private String  reportingToUserId;
	private String 	lbxBranchId;
	private String  customername;
	private String makerName;
	private String currentDealStage;
	private int totalRecordSize;
	private String checkBoxLink;
	private String  dealNumber;
	private String[]  dealIds;
	private String reportingToUserIdGrid;
	
	
	

	public String getReportingToUserIdGrid() {
		return reportingToUserIdGrid;
	}
	public void setReportingToUserIdGrid(String reportingToUserIdGrid) {
		this.reportingToUserIdGrid = reportingToUserIdGrid;
	}
	public String[] getDealIds() {
		return dealIds;
	}
	public void setDealIds(String[] dealIds) {
		this.dealIds = dealIds;
	}
	public String getDealNumber() {
		return dealNumber;
	}
	public void setDealNumber(String dealNumber) {
		this.dealNumber = dealNumber;
	}
	public String getCheckBoxLink() {
		return checkBoxLink;
	}
	public void setCheckBoxLink(String checkBoxLink) {
		this.checkBoxLink = checkBoxLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getMakerName() {
		return makerName;
	}
	public void setMakerName(String makerName) {
		this.makerName = makerName;
	}
	public String getCurrentDealStage() {
		return currentDealStage;
	}
	public void setCurrentDealStage(String currentDealStage) {
		this.currentDealStage = currentDealStage;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getReportingToUserId() {
		return reportingToUserId;
	}
	public void setReportingToUserId(String reportingToUserId) {
		this.reportingToUserId = reportingToUserId;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	public String getLbxDealNo() {
		return lbxDealNo;
	}
	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

}
