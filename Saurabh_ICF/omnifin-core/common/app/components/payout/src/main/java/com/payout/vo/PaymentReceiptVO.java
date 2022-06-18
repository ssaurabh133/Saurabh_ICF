package com.payout.vo;

import java.io.Serializable;

public class PaymentReceiptVO implements Serializable{
	private String makerId;
	private String makerDate;
	private int totalRecordSize;
	private int currentPageLink;
	private String searchBpName;
	private String searchPaymentType;
	private String searchPaymentAmount;
	private String bpId;
	private String lbxBpId;
	private String bpName;
	private String lbxBpName;
	private String paymentId;
	private String paymentType;
	private String paymentMode;
	private String paymentDate;
	private String paymentAmount;
	private String instrumentNo;
	private String instrumentDate;
	private String recStatus;
	private String makerRemark;
	
	private String txnCaseId;
	private String orgAmount;
	private String balanceAmount;
	private String allocatedAmount;
	private String activityCode;
	private String activityDesc;
	private String loanNo;
	private String loanId;
	private String allocateFlag;
	private String decision;
	private String authorRemark;
	private String schemeName;
	private String schemeDesc;
	private String 	allocateAmount;
	private String 	tds;
	private String 	totalAllocatedAmount;

	
	public String getAllocateAmount() {
		return allocateAmount;
	}
	public void setAllocateAmount(String allocateAmount) {
		this.allocateAmount = allocateAmount;
	}
	public String getTds() {
		return tds;
	}
	public void setTds(String tds) {
		this.tds = tds;
	}
	public String getTotalAllocatedAmount() {
		return totalAllocatedAmount;
	}
	public void setTotalAllocatedAmount(String totalAllocatedAmount) {
		this.totalAllocatedAmount = totalAllocatedAmount;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getSchemeDesc() {
		return schemeDesc;
	}
	public void setSchemeDesc(String schemeDesc) {
		this.schemeDesc = schemeDesc;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getAuthorRemark() {
		return authorRemark;
	}
	public void setAuthorRemark(String authorRemark) {
		this.authorRemark = authorRemark;
	}
	public String getAllocateFlag() {
		return allocateFlag;
	}
	public void setAllocateFlag(String allocateFlag) {
		this.allocateFlag = allocateFlag;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getTxnCaseId() {
		return txnCaseId;
	}
	public void setTxnCaseId(String txnCaseId) {
		this.txnCaseId = txnCaseId;
	}
	public String getOrgAmount() {
		return orgAmount;
	}
	public void setOrgAmount(String orgAmount) {
		this.orgAmount = orgAmount;
	}
	
	
	public String getAllocatedAmount() {
		return allocatedAmount;
	}
	public void setAllocatedAmount(String allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getMakerRemark() {
		return makerRemark;
	}
	public void setMakerRemark(String makerRemark) {
		this.makerRemark = makerRemark;
	}
	public String getBpId() {
		return bpId;
	}
	public void setBpId(String bpId) {
		this.bpId = bpId;
	}
	public String getLbxBpId() {
		return lbxBpId;
	}
	public void setLbxBpId(String lbxBpId) {
		this.lbxBpId = lbxBpId;
	}
	public String getBpName() {
		return bpName;
	}
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}
	public String getLbxBpName() {
		return lbxBpName;
	}
	public void setLbxBpName(String lbxBpName) {
		this.lbxBpName = lbxBpName;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getInstrumentNo() {
		return instrumentNo;
	}
	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}
	public String getInstrumentDate() {
		return instrumentDate;
	}
	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getSearchBpName() {
		return searchBpName;
	}
	public void setSearchBpName(String searchBpName) {
		this.searchBpName = searchBpName;
	}
	public String getSearchPaymentType() {
		return searchPaymentType;
	}
	public void setSearchPaymentType(String searchPaymentType) {
		this.searchPaymentType = searchPaymentType;
	}
	public String getSearchPaymentAmount() {
		return searchPaymentAmount;
	}
	public void setSearchPaymentAmount(String searchPaymentAmount) {
		this.searchPaymentAmount = searchPaymentAmount;
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
	
	
}
