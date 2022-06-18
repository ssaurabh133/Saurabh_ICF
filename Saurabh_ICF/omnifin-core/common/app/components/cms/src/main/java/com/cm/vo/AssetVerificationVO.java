package com.cm.vo;

public class AssetVerificationVO {
	 
	 private String loanAccNo;
	 private String loanID;
	 private String assetID; 
	 private String assetDescription;
	 private String makerID;
	 private String makerDate;
     private String lbxLoanNoHID;
	 private String appraiser;
	 private String internalAppraiser;
	 private String externalAppraiser;
	 private String lbxextApprHID;
	 private String assetVerificationID;
	 private String customerName;
	 private String loanProduct;
	 private String loanScheme;
	 private String loanAmount;
	 private String loanApprovalDate;
	 private String appraiserName;
	 private String userName;
	 private String reportingToUserId;
	
	 public String getUserName() {
		return userName;
	}
	public String getReportingToUserId() {
		return reportingToUserId;
	}
	public void setReportingToUserId(String reportingToUserId) {
		this.reportingToUserId = reportingToUserId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private String assetLocation;
	 private String visitDate;
	 private String assetCondition ;
	 private String invoiceCollected ;
	 private String invoiceNumber;
	 private String comments;
	 private String approvalDate;
	 private String decision;
	 private String loanNo;
	 private String appraiserType;
	 private String assetCost;
	 private String assetManufaturer;
	 private String assetSupplier;
	 private String assetVerId;
	 private String lbxUserId;
	 private String lbxLoanNo;
	 private String lbxAssetID;
	 private String branchId;
	 private String verCodeModify;
	 private String initDate;
	 private String appName;
	 private String recStatus;
	 private String modifyID;
	 private String userId;
	 private String makerRemarks;
	 private String authorRemarks;
	 
	  private int currentPageLink;
	  private int totalRecordSize;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLbxLoanNo() {
		return lbxLoanNo;
	}
	public void setLbxLoanNo(String lbxLoanNo) {
		this.lbxLoanNo = lbxLoanNo;
	}
	public String getLbxAssetID() {
		return lbxAssetID;
	}
	public void setLbxAssetID(String lbxAssetID) {
		this.lbxAssetID = lbxAssetID;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getAssetCost() {
		return assetCost;
	}
	public void setAssetCost(String assetCost) {
		this.assetCost = assetCost;
	}
	public String getAssetManufaturer() {
		return assetManufaturer;
	}
	public void setAssetManufaturer(String assetManufaturer) {
		this.assetManufaturer = assetManufaturer;
	}
	public String getAssetSupplier() {
		return assetSupplier;
	}
	public void setAssetSupplier(String assetSupplier) {
		this.assetSupplier = assetSupplier;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getAssetLocation() {
		return assetLocation;
	}
	public void setAssetLocation(String assetLocation) {
		this.assetLocation = assetLocation;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public String getAssetCondition() {
		return assetCondition;
	}
	public void setAssetCondition(String assetCondition) {
		this.assetCondition = assetCondition;
	}
	public String getInvoiceCollected() {
		return invoiceCollected;
	}
	public void setInvoiceCollected(String invoiceCollected) {
		this.invoiceCollected = invoiceCollected;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public String getLoanScheme() {
		return loanScheme;
	}
	public void setLoanScheme(String loanScheme) {
		this.loanScheme = loanScheme;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanApprovalDate() {
		return loanApprovalDate;
	}
	public void setLoanApprovalDate(String loanApprovalDate) {
		this.loanApprovalDate = loanApprovalDate;
	}
	public String getAppraiserName() {
		return appraiserName;
	}
	public void setAppraiserName(String appraiserName) {
		this.appraiserName = appraiserName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAssetVerificationID() {
		return assetVerificationID;
	}
	public void setAssetVerificationID(String assetVerificationID) {
		this.assetVerificationID = assetVerificationID;
	}
	public String getAppraiser() {
		return appraiser;
	}
	 
	public void setAppraiser(String appraiser) {
		this.appraiser = appraiser;
	}
	public String getInternalAppraiser() {
		return internalAppraiser;
	}
	public void setInternalAppraiser(String internalAppraiser) {
		this.internalAppraiser = internalAppraiser;
	}
	public String getExternalAppraiser() {
		return externalAppraiser;
	}
	public void setExternalAppraiser(String externalAppraiser) {
		this.externalAppraiser = externalAppraiser;
	}
	public String getLbxextApprHID() {
		return lbxextApprHID;
	}
	public void setLbxextApprHID(String lbxextApprHID) {
		this.lbxextApprHID = lbxextApprHID;
	}
	public String getMakerID() {
		return makerID;
	}
	public void setMakerID(String makerID) {
		this.makerID = makerID;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getLoanAccNo() {
		return loanAccNo;
	}
	public void setLoanAccNo(String loanAccNo) {
		this.loanAccNo = loanAccNo;
	}
	public String getLoanID() {
		return loanID;
	}
	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}
	public String getAssetID() {
		return assetID;
	}
	public void setAssetID(String assetID) {
		this.assetID = assetID;
	}
	public String getAssetDescription() {
		return assetDescription;
	}
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	public void setAppraiserType(String appraiserType) {
		this.appraiserType = appraiserType;
	}
	public String getAppraiserType() {
		return appraiserType;
	}
	public void setAssetVerId(String assetVerId) {
		this.assetVerId = assetVerId;
	}
	public String getAssetVerId() {
		return assetVerId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRecStatus() {
		return recStatus;
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
	public void setVerCodeModify(String verCodeModify) {
		this.verCodeModify = verCodeModify;
	}
	public String getVerCodeModify() {
		return verCodeModify;
	}
	public void setModifyID(String modifyID) {
		this.modifyID = modifyID;
	}
	public String getModifyID() {
		return modifyID;
	}
	public void setMakerRemarks(String makerRemarks) {
		this.makerRemarks = makerRemarks;
	}
	public String getMakerRemarks() {
		return makerRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}
	public String getInitDate() {
		return initDate;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppName() {
		return appName;
	} 
  
    
}
