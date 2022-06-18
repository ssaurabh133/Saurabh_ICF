package com.cp.vo;

import java.io.Serializable;

public class ManualAdviceCreationVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  lbxLoanNoHID;
	private String loanNo;
	
	private String  chargeAmount;
	private String customersName;
	private String  hBuyerSupplierBPType;
	private String bpTypeDesc;
	
	private String  lbxCharge;
	private String chargeCodeDesc;
	
	private String  lbxBPNID;
	private String bpNameDesc;
	
	private String  adviceType;
	private String  taxApplicable;
	
	
	private String  taxRate1;
	private String  taxAmount1;
	private String  taxRate2;
	private String  taxAmount2;
	
	private String  tdsApplicable;
	private String  tdsRate;
	private String  tdsAmount;
	private String  adviceAmount;
	private String  transactionDate;
	private String  remarks;
	private String makerId;
	private String makerDate;
	private String branchId;
	private String authorRemarks;
	private String userId;
	private String stage;
	private String newBusinessDate;
	private String userName;
	private String reportingToUserId;
	private String lbxUserId;
	private String lbxProductId;
	private String lbxSchemeId;
	
	private String loanRecStatus;
	private String valueDate;
	
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	public String getLoanRecStatus() {
		return loanRecStatus;
	}
	public void setLoanRecStatus(String loanRecStatus) {
		this.loanRecStatus = loanRecStatus;
	}
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
	
	
	public String getNewBusinessDate() {
		return newBusinessDate;
	}
	public void setNewBusinessDate(String newBusinessDate) {
		this.newBusinessDate = newBusinessDate;
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
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
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
	private String manaulId;
	private String recStatus;
	private String businessPartnerType;
	private String comments;
	private String decision;
	private String lbxBusinessPartnearHID;
	private String businessPartnerName;

	private String taxInclusive;
	private String chargeId;
	private String autherDate;
	
	private String autherId;
	private int companyId;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getAutherId() {
		return autherId;
	}
	public void setAutherId(String autherId) {
		this.autherId = autherId;
	}
	
	public String getAutherDate() {
		return autherDate;
	}
	public void setAutherDate(String autherDate) {
		this.autherDate = autherDate;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public String getTaxInclusive() {
		return taxInclusive;
	}
	public void setTaxInclusive(String taxInclusive) {
		this.taxInclusive = taxInclusive;
	}
	public String getBusinessPartnerName() {
		return businessPartnerName;
	}
	public void setBusinessPartnerName(String businessPartnerName) {
		this.businessPartnerName = businessPartnerName;
	}
	public String getLbxBusinessPartnearHID() {
		return lbxBusinessPartnearHID;
	}
	public void setLbxBusinessPartnearHID(String lbxBusinessPartnearHID) {
		this.lbxBusinessPartnearHID = lbxBusinessPartnearHID;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getBusinessPartnerType() {
		return businessPartnerType;
	}
	public void setBusinessPartnerType(String businessPartnerType) {
		this.businessPartnerType = businessPartnerType;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getManaulId() {
		return manaulId;
	}
	public void setManaulId(String manaulId) {
		this.manaulId = manaulId;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getBpTypeDesc() {
		return bpTypeDesc;
	}
	public void setBpTypeDesc(String bpTypeDesc) {
		this.bpTypeDesc = bpTypeDesc;
	}
	public String getChargeCodeDesc() {
		return chargeCodeDesc;
	}
	public void setChargeCodeDesc(String chargeCodeDesc) {
		this.chargeCodeDesc = chargeCodeDesc;
	}
	public String getBpNameDesc() {
		return bpNameDesc;
	}
	public void setBpNameDesc(String bpNameDesc) {
		this.bpNameDesc = bpNameDesc;
	}
	public String getCustomersName() {
		return customersName;
	}
	public void setCustomersName(String customersName) {
		this.customersName = customersName;
	}
	public String getTaxRate2() {
		return taxRate2;
	}
	public void setTaxRate2(String taxRate2) {
		this.taxRate2 = taxRate2;
	}
	public String getTaxAmount2() {
		return taxAmount2;
	}
	public void setTaxAmount2(String taxAmount2) {
		this.taxAmount2 = taxAmount2;
	}
	public String getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String gethBuyerSupplierBPType() {
		return hBuyerSupplierBPType;
	}
	public void sethBuyerSupplierBPType(String hBuyerSupplierBPType) {
		this.hBuyerSupplierBPType = hBuyerSupplierBPType;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getLbxCharge() {
		return lbxCharge;
	}
	public void setLbxCharge(String lbxCharge) {
		this.lbxCharge = lbxCharge;
	}
	public String getLbxBPNID() {
		return lbxBPNID;
	}
	public void setLbxBPNID(String lbxBPNID) {
		this.lbxBPNID = lbxBPNID;
	}
	public String getAdviceType() {
		return adviceType;
	}
	public void setAdviceType(String adviceType) {
		this.adviceType = adviceType;
	}
	public String getTaxApplicable() {
		return taxApplicable;
	}
	public void setTaxApplicable(String taxApplicable) {
		this.taxApplicable = taxApplicable;
	}
	public String getTaxRate1() {
		return taxRate1;
	}
	public void setTaxRate1(String taxRate1) {
		this.taxRate1 = taxRate1;
	}
	public String getTaxAmount1() {
		return taxAmount1;
	}
	public void setTaxAmount1(String taxAmount1) {
		this.taxAmount1 = taxAmount1;
	}
	public String getTdsApplicable() {
		return tdsApplicable;
	}
	public void setTdsApplicable(String tdsApplicable) {
		this.tdsApplicable = tdsApplicable;
	}
	public String getTdsRate() {
		return tdsRate;
	}
	public void setTdsRate(String tdsRate) {
		this.tdsRate = tdsRate;
	}
	public String getTdsAmount() {
		return tdsAmount;
	}
	public void setTdsAmount(String tdsAmount) {
		this.tdsAmount = tdsAmount;
	}
	public String getAdviceAmount() {
		return adviceAmount;
	}
	public void setAdviceAmount(String adviceAmount) {
		this.adviceAmount = adviceAmount;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public void setLbxProductId(String lbxProductId) {
		this.lbxProductId = lbxProductId;
	}
	public String getLbxProductId() {
		return lbxProductId;
	}
	public void setLbxSchemeId(String lbxSchemeId) {
		this.lbxSchemeId = lbxSchemeId;
	}
	public String getLbxSchemeId() {
		return lbxSchemeId;
	}
	

}
