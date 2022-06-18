package com.cm.vo;

public class WaiveOffVO 
{
	private String loanAccountNo; // This is loan ID
	private String lbxLoanNoHID;
	private String customerName;
	private String businessPartnerType;
	private String businessPartnerDescription;
	private String businessPartnerName;
	private String chargeType;
	private String chargeDescription;
	private String adviceAmount;
	private String balanceAmount;
	private String waivOffAmount;
	private String remarks;
	private String chargeID;
	private String lbxBusinessPartnearHID;	
	private String makerID;
	private String MakerDate;
	private String authorID;
	private String authorDate;
	private String decision;
	private String lbxChargeCodeHID;
	private String waveOffId;
	private String n;
	private String taxAmount1;
	private String taxAmount2;
	private String chargeAmount;
	private String txnAdjustedAmount;
	private String waveAmountForTaxAmt1;
	private String waveAmountForTaxAmt2;
	private String txnAdviceId;
	private String totalWaiveOffAmt; 
	private String newChargeAmt;
	private String newTaxAmt1;
	private String newTaxAmt2;
	private String totalWaveOff;
	private String newAdviceAmt;
	private int companyId;
	private String taxRate1;
	private String taxRate2;
	private String waiveOffAmountNotUsed;
	private String branchId;
	private String authorRemarks;
	private String userId;
	private String totalWaveOffAmt;
	private String userName;
	private String reportingToUserId;
	private String lbxUserId;
	private String amountInProcess;
	private String newBalanceAmount;
	
	private String loanRecStatus;
	
	private String valueDate;
	
	//Surendra Code goes here..
	private String approvedBy;
	private String lbxapprovedBy;
	
	
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
	public String getNewBalanceAmount() {
		return newBalanceAmount;
	}
	public void setNewBalanceAmount(String newBalanceAmount) {
		this.newBalanceAmount = newBalanceAmount;
	}
	public String getAmountInProcess() {
		return amountInProcess;
	}
	public void setAmountInProcess(String amountInProcess) {
		this.amountInProcess = amountInProcess;
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
	
	public String getTotalWaveOffAmt() {
		return totalWaveOffAmt;
	}
	public void setTotalWaveOffAmt(String totalWaveOffAmt) {
		this.totalWaveOffAmt = totalWaveOffAmt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	public String getWaiveOffAmountNotUsed() {
		return waiveOffAmountNotUsed;
	}
	public void setWaiveOffAmountNotUsed(String waiveOffAmountNotUsed) {
		this.waiveOffAmountNotUsed = waiveOffAmountNotUsed;
	}
	public String getTaxRate1() {
		return taxRate1;
	}
	public void setTaxRate1(String taxRate1) {
		this.taxRate1 = taxRate1;
	}
	public String getTaxRate2() {
		return taxRate2;
	}
	public void setTaxRate2(String taxRate2) {
		this.taxRate2 = taxRate2;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getNewChargeAmt() {
		return newChargeAmt;
	}
	public void setNewChargeAmt(String newChargeAmt) {
		this.newChargeAmt = newChargeAmt;
	}
	public String getNewTaxAmt1() {
		return newTaxAmt1;
	}
	public void setNewTaxAmt1(String newTaxAmt1) {
		this.newTaxAmt1 = newTaxAmt1;
	}
	public String getNewTaxAmt2() {
		return newTaxAmt2;
	}
	public void setNewTaxAmt2(String newTaxAmt2) {
		this.newTaxAmt2 = newTaxAmt2;
	}
	public String getTotalWaveOff() {
		return totalWaveOff;
	}
	public void setTotalWaveOff(String totalWaveOff) {
		this.totalWaveOff = totalWaveOff;
	}
	public String getNewAdviceAmt() {
		return newAdviceAmt;
	}
	public void setNewAdviceAmt(String newAdviceAmt) {
		this.newAdviceAmt = newAdviceAmt;
	}
	public String getTotalWaiveOffAmt() {
		return totalWaiveOffAmt;
	}
	public void setTotalWaiveOffAmt(String totalWaiveOffAmt) {
		this.totalWaiveOffAmt = totalWaiveOffAmt;
	}
	public String getTxnAdviceId() {
		return txnAdviceId;
	}
	public void setTxnAdviceId(String txnAdviceId) {
		this.txnAdviceId = txnAdviceId;
	}
	public String getWaveAmountForTaxAmt1() {
		return waveAmountForTaxAmt1;
	}
	public void setWaveAmountForTaxAmt1(String waveAmountForTaxAmt1) {
		this.waveAmountForTaxAmt1 = waveAmountForTaxAmt1;
	}
	public String getWaveAmountForTaxAmt2() {
		return waveAmountForTaxAmt2;
	}
	public void setWaveAmountForTaxAmt2(String waveAmountForTaxAmt2) {
		this.waveAmountForTaxAmt2 = waveAmountForTaxAmt2;
	}
	public String getTxnAdjustedAmount() {
		return txnAdjustedAmount;
	}
	public void setTxnAdjustedAmount(String txnAdjustedAmount) {
		this.txnAdjustedAmount = txnAdjustedAmount;
	}
	public String getTaxAmount1() {
		return taxAmount1;
	}
	public void setTaxAmount1(String taxAmount1) {
		this.taxAmount1 = taxAmount1;
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
	public String getWaveOffId() {
		return waveOffId;
	}
	public void setWaveOffId(String waveOffId) {
		this.waveOffId = waveOffId;
	}
	public String getLbxChargeCodeHID() {
		return lbxChargeCodeHID;
	}
	public void setLbxChargeCodeHID(String lbxChargeCodeHID) {
		this.lbxChargeCodeHID = lbxChargeCodeHID;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getLbxBusinessPartnearHID() {
		return lbxBusinessPartnearHID;
	}
	public void setLbxBusinessPartnearHID(String lbxBusinessPartnearHID) {
		this.lbxBusinessPartnearHID = lbxBusinessPartnearHID;
	}
	public String getMakerID() {
		return makerID;
	}
	public void setMakerID(String makerID) {
		this.makerID = makerID;
	}
	public String getMakerDate() {
		return MakerDate;
	}
	public void setMakerDate(String makerDate) {
		MakerDate = makerDate;
	}
	public String getAuthorID() {
		return authorID;
	}
	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}

	
	
	
	public String getLoanAccountNo() {
		return loanAccountNo;
	}
	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
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
	public String getBusinessPartnerName() {
		return businessPartnerName;
	}
	public void setBusinessPartnerName(String businessPartnerName) {
		this.businessPartnerName = businessPartnerName;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getChargeDescription() {
		return chargeDescription;
	}
	public void setChargeDescription(String chargeDescription) {
		this.chargeDescription = chargeDescription;
	}
	public String getAdviceAmount() {
		return adviceAmount;
	}
	public void setAdviceAmount(String adviceAmount) {
		this.adviceAmount = adviceAmount;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getWaivOffAmount() {
		return waivOffAmount;
	}
	public void setWaivOffAmount(String waivOffAmount) {
		this.waivOffAmount = waivOffAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getChargeID() {
		return chargeID;
	}
	public void setChargeID(String chargeID) {
		this.chargeID = chargeID;
	}
	public void setBusinessPartnerDescription(String businessPartnerDescription) {
		this.businessPartnerDescription = businessPartnerDescription;
	}
	public String getBusinessPartnerDescription() {
		return businessPartnerDescription;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
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
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getLbxapprovedBy() {
		return lbxapprovedBy;
	}
	public void setLbxapprovedBy(String lbxapprovedBy) {
		this.lbxapprovedBy = lbxapprovedBy;
	}
	
}
