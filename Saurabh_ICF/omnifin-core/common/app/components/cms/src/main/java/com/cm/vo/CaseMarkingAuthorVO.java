package com.cm.vo;

public class CaseMarkingAuthorVO {
private String [] loanNoArr;
private String [] loanIdArr;
private String [] customerNameArr;
private String [] customerIdArr;
private String [] loanAmountArr;
private String [] loanOverdueAmountArr;
private String [] balancePrincipalArr;
private String [] loanDPDArr;
private String [] legalFlagArr;
private String [] legalDateArr;
private String [] legalRemarksArr;
private String [] repoFlagArr;
private String [] repoDateArr;
private String [] repoRemarksArr;
private String[] recStatusArr;
private String[] makerDateArr;
private String[] authorDateArr;
private String[] authorRemarksArr;


private String searchLoanNo;
private String searchCustomerName;
private String lbxDealNo;
private int currentPageLink;
private String reportingToUserId;
private String userId;
private String branchId;
private int totalRecordSize;

private String loanNo;
private String loanId;
private String customerName;
private String customerId;
private String loanAmount;
private String loanOverdueAmount;
private String balancePrincipal;
private String loanDPD;
private String legalFlag;
private String legalDate;
private String legalRemarks;
private String repoFlag;
private String repoDate;
private String repoRemarks;
private String recStatus;
private String makerDate;
private String authorDate;
private String authorRemarks;
private String[] markCheckBoxArr;
private String[] selectDropDownArr;
private String markCheckBox;
private String chkHidden;
private String[] legalFlagArrForward;
private String[] repoFlagArrForward;
private String stockyardDetail;
private String agency;
private String lbxAgencyId;
private String markingDate;
private String otherDetails;
private String remarks;
private String caseStatus;
private String decision;
private String caseId;
private String chk;




public String getChk() {
	return chk;
}
public void setChk(String chk) {
	this.chk = chk;
}
public String getCaseId() {
	return caseId;
}
public void setCaseId(String caseId) {
	this.caseId = caseId;
}
public String getDecision() {
	return decision;
}
public void setDecision(String decision) {
	this.decision = decision;
}
public String getOtherDetails() {
	return otherDetails;
}
public void setOtherDetails(String otherDetails) {
	this.otherDetails = otherDetails;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public String getCaseStatus() {
	return caseStatus;
}
public void setCaseStatus(String caseStatus) {
	this.caseStatus = caseStatus;
}
public String getMarkingDate() {
	return markingDate;
}
public void setMarkingDate(String markingDate) {
	this.markingDate = markingDate;
}
public String getLbxAgencyId() {
	return lbxAgencyId;
}
public void setLbxAgencyId(String lbxAgencyId) {
	this.lbxAgencyId = lbxAgencyId;
}
public String getStockyardDetail() {
	return stockyardDetail;
}
public void setStockyardDetail(String stockyardDetail) {
	this.stockyardDetail = stockyardDetail;
}
public String getAgency() {
	return agency;
}
public void setAgency(String agencyName) {
	this.agency = agencyName;
}


public String[] getLegalFlagArrValue() {
	return legalFlagArrValue;
}
public void setLegalFlagArrValue(String[] legalFlagArrValue) {
	this.legalFlagArrValue = legalFlagArrValue;
}
public String[] getRepoFlagArrValue() {
	return repoFlagArrValue;
}
public void setRepoFlagArrValue(String[] repoFlagArrValue) {
	this.repoFlagArrValue = repoFlagArrValue;
}
private String[] legalFlagArrValue;
private String[] repoFlagArrValue;

public String[] getLegalFlagArrForward() {
	return legalFlagArrForward;
}
public void setLegalFlagArrForward(String[] legalFlagArrForward) {
	this.legalFlagArrForward = legalFlagArrForward;
}
public String[] getRepoFlagArrForward() {
	return repoFlagArrForward;
}
public void setRepoFlagArrForward(String[] repoFlagArrForward) {
	this.repoFlagArrForward = repoFlagArrForward;
}
public String getMarkCheckBox() {
	return markCheckBox;
}
public void setMarkCheckBox(String markCheckBox) {
	this.markCheckBox = markCheckBox;
}
public String getChkHidden() {
	return chkHidden;
}
public void setChkHidden(String chkHidden) {
	this.chkHidden = chkHidden;
}
public String[] getMarkCheckBoxArr() {
	return markCheckBoxArr;
}
public void setMarkCheckBoxArr(String[] markCheckBoxArr) {
	this.markCheckBoxArr = markCheckBoxArr;
}
public String[] getSelectDropDownArr() {
	return selectDropDownArr;
}
public void setSelectDropDownArr(String[] selectDropDownArr) {
	this.selectDropDownArr = selectDropDownArr;
}
public String getLoanNo() {
	return loanNo;
}
public void setLoanNo(String loanNo) {
	this.loanNo = loanNo;
}
public String getLoanId() {
	return loanId;
}
public void setLoanId(String loanId) {
	this.loanId = loanId;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public String getCustomerId() {
	return customerId;
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
public String getLoanAmount() {
	return loanAmount;
}
public void setLoanAmount(String loanAmount) {
	this.loanAmount = loanAmount;
}
public String getLoanOverdueAmount() {
	return loanOverdueAmount;
}
public void setLoanOverdueAmount(String loanOverdueAmount) {
	this.loanOverdueAmount = loanOverdueAmount;
}
public String getBalancePrincipal() {
	return balancePrincipal;
}
public void setBalancePrincipal(String balancePrincipal) {
	this.balancePrincipal = balancePrincipal;
}
public String getLoanDPD() {
	return loanDPD;
}
public void setLoanDPD(String loanDPD) {
	this.loanDPD = loanDPD;
}
public String getLegalFlag() {
	return legalFlag;
}
public void setLegalFlag(String legalFlag) {
	this.legalFlag = legalFlag;
}
public String getLegalDate() {
	return legalDate;
}
public void setLegalDate(String legalDate) {
	this.legalDate = legalDate;
}
public String getLegalRemarks() {
	return legalRemarks;
}
public void setLegalRemarks(String legalRemarks) {
	this.legalRemarks = legalRemarks;
}
public String getRepoFlag() {
	return repoFlag;
}
public void setRepoFlag(String repoFlag) {
	this.repoFlag = repoFlag;
}
public String getRepoDate() {
	return repoDate;
}
public void setRepoDate(String repoDate) {
	this.repoDate = repoDate;
}
public String getRepoRemarks() {
	return repoRemarks;
}
public void setRepoRemarks(String repoRemarks) {
	this.repoRemarks = repoRemarks;
}
public String getRecStatus() {
	return recStatus;
}
public void setRecStatus(String recStatus) {
	this.recStatus = recStatus;
}
public String getMakerDate() {
	return makerDate;
}
public void setMakerDate(String makerDate) {
	this.makerDate = makerDate;
}
public String getAuthorDate() {
	return authorDate;
}
public void setAuthorDate(String authorDate) {
	this.authorDate = authorDate;
}
public String getAuthorRemarks() {
	return authorRemarks;
}
public void setAuthorRemarks(String authorRemarks) {
	this.authorRemarks = authorRemarks;
}
public String[] getLoanNoArr() {
	return loanNoArr;
}
public void setLoanNoArr(String[] loanNoArr) {
	this.loanNoArr = loanNoArr;
}
public String[] getLoanIdArr() {
	return loanIdArr;
}
public void setLoanIdArr(String[] loanIdArr) {
	this.loanIdArr = loanIdArr;
}
public String[] getCustomerNameArr() {
	return customerNameArr;
}
public void setCustomerNameArr(String[] customerNameArr) {
	this.customerNameArr = customerNameArr;
}
public String[] getCustomerIdArr() {
	return customerIdArr;
}
public void setCustomerIdArr(String[] customerIdArr) {
	this.customerIdArr = customerIdArr;
}
public String[] getLoanAmountArr() {
	return loanAmountArr;
}
public void setLoanAmountArr(String[] loanAmountArr) {
	this.loanAmountArr = loanAmountArr;
}
public String[] getLoanOverdueAmountArr() {
	return loanOverdueAmountArr;
}
public void setLoanOverdueAmountArr(String[] loanOverdueAmountArr) {
	this.loanOverdueAmountArr = loanOverdueAmountArr;
}
public String[] getBalancePrincipalArr() {
	return balancePrincipalArr;
}
public void setBalancePrincipalArr(String[] balancePrincipalArr) {
	this.balancePrincipalArr = balancePrincipalArr;
}
public String[] getLoanDPDArr() {
	return loanDPDArr;
}
public void setLoanDPDArr(String[] loanDPDArr) {
	this.loanDPDArr = loanDPDArr;
}
public String[] getLegalFlagArr() {
	return legalFlagArr;
}
public void setLegalFlagArr(String[] legalFlagArr) {
	this.legalFlagArr = legalFlagArr;
}
public String[] getLegalDateArr() {
	return legalDateArr;
}
public void setLegalDateArr(String[] legalDateArr) {
	this.legalDateArr = legalDateArr;
}
public String[] getLegalRemarksArr() {
	return legalRemarksArr;
}
public void setLegalRemarksArr(String[] legalRemarksArr) {
	this.legalRemarksArr = legalRemarksArr;
}
public String[] getRepoFlagArr() {
	return repoFlagArr;
}
public void setRepoFlagArr(String[] repoFlagArr) {
	this.repoFlagArr = repoFlagArr;
}
public String[] getRepoDateArr() {
	return repoDateArr;
}
public void setRepoDateArr(String[] repoDateArr) {
	this.repoDateArr = repoDateArr;
}
public String[] getRepoRemarksArr() {
	return repoRemarksArr;
}
public void setRepoRemarksArr(String[] repoRemarksArr) {
	this.repoRemarksArr = repoRemarksArr;
}
public String getSearchLoanNo() {
	return searchLoanNo;
}
public void setSearchLoanNo(String searchLoanNo) {
	this.searchLoanNo = searchLoanNo;
}
public String getSearchCustomerName() {
	return searchCustomerName;
}
public void setSearchCustomerName(String searchCustomerName) {
	this.searchCustomerName = searchCustomerName;
}
public String getLbxDealNo() {
	return lbxDealNo;
}
public void setLbxDealNo(String lbxDealNo) {
	this.lbxDealNo = lbxDealNo;
}
public int getCurrentPageLink() {
	return currentPageLink;
}
public void setCurrentPageLink(int currentPageLink) {
	this.currentPageLink = currentPageLink;
}
public String getReportingToUserId() {
	return reportingToUserId;
}
public void setReportingToUserId(String reportingToUserId) {
	this.reportingToUserId = reportingToUserId;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getBranchId() {
	return branchId;
}
public void setBranchId(String branchId) {
	this.branchId = branchId;
}
public int getTotalRecordSize() {
	return totalRecordSize;
}
public void setTotalRecordSize(int totalRecordSize) {
	this.totalRecordSize = totalRecordSize;
}
public String[] getRecStatusArr() {
	return recStatusArr;
}
public void setRecStatusArr(String[] recStatusArr) {
	this.recStatusArr = recStatusArr;
}
public String[] getMakerDateArr() {
	return makerDateArr;
}
public void setMakerDateArr(String[] makerDateArr) {
	this.makerDateArr = makerDateArr;
}
public String[] getAuthorDateArr() {
	return authorDateArr;
}
public void setAuthorDateArr(String[] authorDateArr) {
	this.authorDateArr = authorDateArr;
}
public String[] getAuthorRemarksArr() {
	return authorRemarksArr;
}
public void setAuthorRemarksArr(String[] authorRemarksArr) {
	this.authorRemarksArr = authorRemarksArr;
}



}
