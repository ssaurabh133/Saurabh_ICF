package com.cm.vo;

import java.io.Serializable;

public class DisbursalSearchVO implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;
	private String loanNo;
	private String lbxLoanNoHID;
	private String disbursalNo;
	private String loanApprovalDate;
	private String customerName;
	private String loanAmt;
	private String product;
	private String scheme;
	private String userName;
	private String reportingToUserId;
	private String lbxProductID;
	private String lbxscheme;
	private String loanDisbursalId;	
	private String branchId;
	private String currentStatus;
	private String recStatus;
	private String dealNo;
	private String lbxDealNo;
	private String userId;
	private String stage;
	private String lbxUserId;
	private int currentPageLink;
	private int totalRecordSize;
	private String modifyNo;
	private String terminationId;
	private String panNo;
	private String appFormNo;
	private String loanStatus;
	private String disbursalStatus;
	private String npaStatus;
	private String loanBranch;
	private String maker;
	private String author;
	private String dealId;
	private String allBranches;
	private String lbxBranchId;
	private String mbNumber;
	private String drivingLic;
	private String voterId;
	private String vehicleNo;
	private String fatherHusband;
	private String dob;
	private String addr;
	private String legalRepoFlag;
	private String fromdateforsd;
	private String todateforsd;
	private String restrictedFlag;
	private String fundingType;
	private String sendBackStage;
	private String rpStageFlag;
	private String lbxsendBackStage;
	private String sendBackStageButton;
	
	public String getFundingType() {
		return fundingType;
	}
	public void setFundingType(String fundingType) {
		this.fundingType = fundingType;
	}
	
	
	public String getFromdateforsd() {
		return fromdateforsd;
	}
	public void setFromdateforsd(String fromdateforsd) {
		this.fromdateforsd = fromdateforsd;
	}
	public String getTodateforsd() {
		return todateforsd;
	}
	public void setTodateforsd(String todateforsd) {
		this.todateforsd = todateforsd;
	}
	public String getSendBackStage() {
		return sendBackStage;
	}
	public void setSendBackStage(String sendBackStage) {
		this.sendBackStage = sendBackStage;
	}
	public String getRpStageFlag() {
		return rpStageFlag;
	}
	public void setRpStageFlag(String rpStageFlag) {
		this.rpStageFlag = rpStageFlag;
	}
	public String getLbxsendBackStage() {
		return lbxsendBackStage;
	}
	public void setLbxsendBackStage(String lbxsendBackStage) {
		this.lbxsendBackStage = lbxsendBackStage;
	}
	public String getSendBackStageButton() {
		return sendBackStageButton;
	}
	public void setSendBackStageButton(String sendBackStageButton) {
		this.sendBackStageButton = sendBackStageButton;
	}	
	
	public String getLegalRepoFlag() {
		return legalRepoFlag;
	}
	public void setLegalRepoFlag(String legalRepoFlag) {
		this.legalRepoFlag = legalRepoFlag;
	}
	public String getFatherHusband() {
		return fatherHusband;
	}
	public void setFatherHusband(String fatherHusband) {
		this.fatherHusband = fatherHusband;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getMbNumber() {
		return mbNumber;
	}
	public void setMbNumber(String mbNumber) {
		this.mbNumber = mbNumber;
	}
	public String getDrivingLic() {
		return drivingLic;
	}
	public void setDrivingLic(String drivingLic) {
		this.drivingLic = drivingLic;
	}
	public String getVoterId() {
		return voterId;
	}
	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDealBranch() {
		return dealBranch;
	}
	public void setDealBranch(String dealBranch) {
		this.dealBranch = dealBranch;
	}
	private String dealBranch;
	
	
	
	
	
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getLoanBranch() {
		return loanBranch;
	}
	public void setLoanBranch(String loanBranch) {
		this.loanBranch = loanBranch;
	}
	public String getNpaStatus() {
		return npaStatus;
	}
	public void setNpaStatus(String npaStatus) {
		this.npaStatus = npaStatus;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getDisbursalStatus() {
		return disbursalStatus;
	}
	public void setDisbursalStatus(String disbursalStatus) {
		this.disbursalStatus = disbursalStatus;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getAppFormNo() {
		return appFormNo;
	}
	public void setAppFormNo(String appFormNo) {
		this.appFormNo = appFormNo;
	}
	public String getTerminationId() {
		return terminationId;
	}
	public void setTerminationId(String terminationId) {
		this.terminationId = terminationId;
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
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
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
	public String getLoanDisbursalId() {
		return loanDisbursalId;
	}
	public void setLoanDisbursalId(String loanDisbursalId) {
		this.loanDisbursalId = loanDisbursalId;
	}
	public String getLbxProductID() {
		return lbxProductID;
	}
	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}
	public String getLbxscheme() {
		return lbxscheme;
	}
	public void setLbxscheme(String lbxscheme) {
		this.lbxscheme = lbxscheme;
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
	public void setLoanApprovalDate(String loanApprovalDate) {
		this.loanApprovalDate = loanApprovalDate;
	}
	public String getLoanApprovalDate() {
		return loanApprovalDate;
	}
	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getLoanAmt() {
		return loanAmt;
	}
	public void setDisbursalNo(String disbursalNo) {
		this.disbursalNo = disbursalNo;
	}
	public String getDisbursalNo() {
		return disbursalNo;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getLbxDealNo() {
		return lbxDealNo;
	}
	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setModifyNo(String modifyNo) {
		this.modifyNo = modifyNo;
	}
	public String getModifyNo() {
		return modifyNo;
	}
	public void setAllBranches(String allBranches) {
		this.allBranches = allBranches;
	}
	public String getAllBranches() {
		return allBranches;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public String getRestrictedFlag() {
		return restrictedFlag;
	}
	public void setRestrictedFlag(String restrictedFlag) {
		this.restrictedFlag = restrictedFlag;
	}
	
	
}
