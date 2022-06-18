package com.cm.vo;


import java.io.Serializable;


import java.io.Serializable;

public class ReceiptMakerVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loanAccountNumber;
	private String lbxLoanNoHID;
	private String customerName;
	private String businessPartnerType;
	private String businessPartnerDesc;
	private String businessPartnerName;
	private String lbxBPType;
	private String lbxBPNID;
	private String receiptDate;
	private String receiptMode;
	private String receiptAmount;
	private String instrumentNumber;
	private String instrumentDate;
	private String bank;
	private String branch;
	private String micr;
	private String ifsCode;
	private String remarks;
	private String comments;
	private String decision;
	private String hidPmntId;
	private String txnAdvicedID;
	private String lbxBankID;
	private String lbxBranchID;
	private String instrumentID;
	private String tdsAmount;
	private String[] tdsAmountArry;
	private String chargeDesc;
	private String originalAmount;
	private String balanceAmount;
	private String amountInProcess;
	private String allotedAmount;
	private String[] allocatedArry;
	private String[] txnAdvicedIDArry;
	private String[] amountInProcessArry;
	private String businessDate;
	private String makerId;
	private String bankAccount;
	private String tdsadviseAmount;
	private String tdsAllocatedAmount;
	private String[] tdsAllocatedArry;
	private String branchId;
	private String receiptNo;
	private String authorRemarks;
	private String userId;
	private String modifyNo;
	private int currentPageLink;
	private int totalRecordSize;
	private String lbxreceiptMode;
	private String VallocatedAmount;
	private String VtdsAmount;
	private String procVal;
	private String userName;
	private String reportingToUserId;
	private String lbxUserId;
	private String loanRecStatus;
	private String defaultBranch;
	private String receiptPurposeValue;
	private String receiptPurposeDesc;
	private String purpose;
	private String transactionRefNo;
	private String loanBranch;
	private String receiptAmountNew;
	private String depositBank;
	private String depositBankID;
	private String depositBranch;
	private String depositBranchID;
	private String depositMicr;
	private String depositIfscCode;
	private String depositBankAccount;
	private int companyId;
	private String allocChargeDesc;
	private String allocAdviceAmount;
	private String allocAdjustedAmount;
	private String allocAmountInProcess;
	private String allocBalAmount;
	private String allocAmount;
	private String allocTdsAllocatedAmount;
	private String [] allocAmountStringArray;
	private String [] allocTdsAllocatedAmountStringArray;
	
	private String [] lbxAllocChargeStringArray;
	private String [] allocOtherBalAmountStringArray;
	private String [] allocOtherAmountStringArray;
	private String [] allocTdsOtherAllAmtStringArray;
    
	private String otherAddChargeDesc;
	private String otherAddChargeCode;
	private String otherAddBalanceAmount;
	private String otherAddAllocateAmount;
	private String otherAddTDSAmount;
	
	private String showTotalBalanceAmt;
	private String showTotalAllocatedAmount;
	private String showTotalTdsAmount;
	
	private String loanRepaymentType;

    private String receivedFrom;
	
	private String contactNo;
	
	private String allocChargeCode;
	
	private String [] allocChargeCodeArray;
	private String [] allocAdviceAmountArray;
	private String [] allocAdjustedAmountArray;
	private String [] allocAmountInProcessArray;
	private String [] allocBalAmountArray;
	private String recStatus;
	private String statusReceipt;
	private String []  allocChargeDes;
	private String vehicleNo;
	
	
	
	public String getStatusReceipt() {
		return statusReceipt;
	}
	public void setStatusReceipt(String statusReceipt) {
		this.statusReceipt = statusReceipt;
	}
	public String[] getAllocChargeCodeArray() {
		return allocChargeCodeArray;
	}
	public void setAllocChargeCodeArray(String[] allocChargeCodeArray) {
		this.allocChargeCodeArray = allocChargeCodeArray;
	}
	public String[] getAllocAdviceAmountArray() {
		return allocAdviceAmountArray;
	}
	public void setAllocAdviceAmountArray(String[] allocAdviceAmountArray) {
		this.allocAdviceAmountArray = allocAdviceAmountArray;
	}
	public String[] getAllocAdjustedAmountArray() {
		return allocAdjustedAmountArray;
	}
	public void setAllocAdjustedAmountArray(String[] allocAdjustedAmountArray) {
		this.allocAdjustedAmountArray = allocAdjustedAmountArray;
	}
	public String[] getAllocAmountInProcessArray() {
		return allocAmountInProcessArray;
	}
	public void setAllocAmountInProcessArray(String[] allocAmountInProcessArray) {
		this.allocAmountInProcessArray = allocAmountInProcessArray;
	}
	public String getAllocChargeDesc() {
		return allocChargeDesc;
	}
	public void setAllocChargeDesc(String allocChargeDesc) {
		this.allocChargeDesc = allocChargeDesc;
	}
	public String getAllocAdviceAmount() {
		return allocAdviceAmount;
	}
	public void setAllocAdviceAmount(String allocAdviceAmount) {
		this.allocAdviceAmount = allocAdviceAmount;
	}
	public String getAllocAdjustedAmount() {
		return allocAdjustedAmount;
	}
	public void setAllocAdjustedAmount(String allocAdjustedAmount) {
		this.allocAdjustedAmount = allocAdjustedAmount;
	}
	public String getAllocAmountInProcess() {
		return allocAmountInProcess;
	}
	public void setAllocAmountInProcess(String allocAmountInProcess) {
		this.allocAmountInProcess = allocAmountInProcess;
	}
	public String getAllocBalAmount() {
		return allocBalAmount;
	}
	public void setAllocBalAmount(String allocBalAmount) {
		this.allocBalAmount = allocBalAmount;
	}
	public String getAllocAmount() {
		return allocAmount;
	}
	public void setAllocAmount(String allocAmount) {
		this.allocAmount = allocAmount;
	}
	public String getAllocTdsAllocatedAmount() {
		return allocTdsAllocatedAmount;
	}
	public void setAllocTdsAllocatedAmount(String allocTdsAllocatedAmount) {
		this.allocTdsAllocatedAmount = allocTdsAllocatedAmount;
	}
	public String getDepositBank() {
		return depositBank;
	}
	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	public String getDepositBankID() {
		return depositBankID;
	}
	public void setDepositBankID(String depositBankID) {
		this.depositBankID = depositBankID;
	}
	public String getDepositBranch() {
		return depositBranch;
	}
	public void setDepositBranch(String depositBranch) {
		this.depositBranch = depositBranch;
	}
	public String getDepositBranchID() {
		return depositBranchID;
	}
	public void setDepositBranchID(String depositBranchID) {
		this.depositBranchID = depositBranchID;
	}
	public String getDepositMicr() {
		return depositMicr;
	}
	public void setDepositMicr(String depositMicr) {
		this.depositMicr = depositMicr;
	}
	public String getDepositIfscCode() {
		return depositIfscCode;
	}
	public void setDepositIfscCode(String depositIfscCode) {
		this.depositIfscCode = depositIfscCode;
	}
	public String getDepositBankAccount() {
		return depositBankAccount;
	}
	public void setDepositBankAccount(String depositBankAccount) {
		this.depositBankAccount = depositBankAccount;
	}
	public String getLoanBranch() {
		return loanBranch;
	}
	public void setLoanBranch(String loanBranch) {
		this.loanBranch = loanBranch;
	}
	public String getTransactionRefNo() {
		return transactionRefNo;
	}
	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getReceiptPurposeDesc() {
		return receiptPurposeDesc;
	}
	public void setReceiptPurposeDesc(String receiptPurposeDesc) {
		this.receiptPurposeDesc = receiptPurposeDesc;
	}

	public String getReceiptPurposeValue() {
		return receiptPurposeValue;
	}
	public void setReceiptPurposeValue(String receiptPurposeValue) {
		this.receiptPurposeValue = receiptPurposeValue;
	}
	public String getDefaultBranch() {
		return defaultBranch;
	}
	public void setDefaultBranch(String defaultBranch) {
		this.defaultBranch = defaultBranch;
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
	public String getProcVal() {
		return procVal;
	}
	public void setProcVal(String procVal) {
		this.procVal = procVal;
	}
	public String getModifyNo() {
		return modifyNo;
	}
	public void setModifyNo(String modifyNo) {
		this.modifyNo = modifyNo;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
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
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getLoanAccountNumber() {
		return loanAccountNumber;
	}
	public void setLoanAccountNumber(String loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
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
	public String getBusinessPartnerDesc() {
		return businessPartnerDesc;
	}
	public void setBusinessPartnerDesc(String businessPartnerDesc) {
		this.businessPartnerDesc = businessPartnerDesc;
	}
	public String getBusinessPartnerName() {
		return businessPartnerName;
	}
	public void setBusinessPartnerName(String businessPartnerName) {
		this.businessPartnerName = businessPartnerName;
	}
	public String getLbxBPType() {
		return lbxBPType;
	}
	public void setLbxBPType(String lbxBPType) {
		this.lbxBPType = lbxBPType;
	}
	public String getLbxBPNID() {
		return lbxBPNID;
	}
	public void setLbxBPNID(String lbxBPNID) {
		this.lbxBPNID = lbxBPNID;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getReceiptMode() {
		return receiptMode;
	}
	public void setReceiptMode(String receiptMode) {
		this.receiptMode = receiptMode;
	}
	public String getReceiptAmount() {
		return receiptAmount;
	}
	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	public String getInstrumentNumber() {
		return instrumentNumber;
	}
	public void setInstrumentNumber(String instrumentNumber) {
		this.instrumentNumber = instrumentNumber;
	}
	public String getInstrumentDate() {
		return instrumentDate;
	}
	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getMicr() {
		return micr;
	}
	public void setMicr(String micr) {
		this.micr = micr;
	}
	public String getIfsCode() {
		return ifsCode;
	}
	public void setIfsCode(String ifsCode) {
		this.ifsCode = ifsCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getHidPmntId() {
		return hidPmntId;
	}
	public void setHidPmntId(String hidPmntId) {
		this.hidPmntId = hidPmntId;
	}
	public String getTxnAdvicedID() {
		return txnAdvicedID;
	}
	public void setTxnAdvicedID(String txnAdvicedID) {
		this.txnAdvicedID = txnAdvicedID;
	}
	public String getLbxBankID() {
		return lbxBankID;
	}
	public void setLbxBankID(String lbxBankID) {
		this.lbxBankID = lbxBankID;
	}
	public String getLbxBranchID() {
		return lbxBranchID;
	}
	public void setLbxBranchID(String lbxBranchID) {
		this.lbxBranchID = lbxBranchID;
	}
	public String getInstrumentID() {
		return instrumentID;
	}
	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
	}
	public String getTdsAmount() {
		return tdsAmount;
	}
	public void setTdsAmount(String tdsAmount) {
		this.tdsAmount = tdsAmount;
	}
	public String[] getTdsAmountArry() {
		return tdsAmountArry;
	}
	public void setTdsAmountArry(String[] tdsAmountArry) {
		this.tdsAmountArry = tdsAmountArry;
	}
	public String getChargeDesc() {
		return chargeDesc;
	}
	public void setChargeDesc(String chargeDesc) {
		this.chargeDesc = chargeDesc;
	}
	public String getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getAmountInProcess() {
		return amountInProcess;
	}
	public void setAmountInProcess(String amountInProcess) {
		this.amountInProcess = amountInProcess;
	}
	public String getAllotedAmount() {
		return allotedAmount;
	}
	public void setAllotedAmount(String allotedAmount) {
		this.allotedAmount = allotedAmount;
	}
	public String[] getAllocatedArry() {
		return allocatedArry;
	}
	public void setAllocatedArry(String[] allocatedArry) {
		this.allocatedArry = allocatedArry;
	}
	public String[] getTxnAdvicedIDArry() {
		return txnAdvicedIDArry;
	}
	public void setTxnAdvicedIDArry(String[] txnAdvicedIDArry) {
		this.txnAdvicedIDArry = txnAdvicedIDArry;
	}
	public String[] getAmountInProcessArry() {
		return amountInProcessArry;
	}
	public void setAmountInProcessArry(String[] amountInProcessArry) {
		this.amountInProcessArry = amountInProcessArry;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getTdsadviseAmount() {
		return tdsadviseAmount;
	}
	public void setTdsadviseAmount(String tdsadviseAmount) {
		this.tdsadviseAmount = tdsadviseAmount;
	}
	public String getTdsAllocatedAmount() {
		return tdsAllocatedAmount;
	}
	public void setTdsAllocatedAmount(String tdsAllocatedAmount) {
		this.tdsAllocatedAmount = tdsAllocatedAmount;
	}
	public String[] getTdsAllocatedArry() {
		return tdsAllocatedArry;
	}
	public void setTdsAllocatedArry(String[] tdsAllocatedArry) {
		this.tdsAllocatedArry = tdsAllocatedArry;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getLbxreceiptMode() {
		return lbxreceiptMode;
	}
	public void setLbxreceiptMode(String lbxreceiptMode) {
		this.lbxreceiptMode = lbxreceiptMode;
	}
	public String getVallocatedAmount() {
		return VallocatedAmount;
	}
	public void setVallocatedAmount(String vallocatedAmount) {
		VallocatedAmount = vallocatedAmount;
	}
	public String getVtdsAmount() {
		return VtdsAmount;
	}
	public void setVtdsAmount(String vtdsAmount) {
		VtdsAmount = vtdsAmount;
	}
	public String getReceiptAmountNew() {
		return receiptAmountNew;
	}
	public void setReceiptAmountNew(String receiptAmountNew) {
		this.receiptAmountNew = receiptAmountNew;
	}
	public void setCompanyId(int i) {
		this.companyId = i;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setAllocTdsAllocatedAmountStringArray(
			String [] allocTdsAllocatedAmountStringArray) {
		this.allocTdsAllocatedAmountStringArray = allocTdsAllocatedAmountStringArray;
	}
	public String [] getAllocTdsAllocatedAmountStringArray() {
		return allocTdsAllocatedAmountStringArray;
	}
	public void setAllocAmountStringArray(String [] allocAmountStringArray) {
		this.allocAmountStringArray = allocAmountStringArray;
	}
	public String [] getAllocAmountStringArray() {
		return allocAmountStringArray;
	}
	public void setLbxAllocChargeStringArray(String [] lbxAllocChargeStringArray) {
		this.lbxAllocChargeStringArray = lbxAllocChargeStringArray;
	}
	public String [] getLbxAllocChargeStringArray() {
		return lbxAllocChargeStringArray;
	}
	public void setAllocOtherBalAmountStringArray(
			String [] allocOtherBalAmountStringArray) {
		this.allocOtherBalAmountStringArray = allocOtherBalAmountStringArray;
	}
	public String [] getAllocOtherBalAmountStringArray() {
		return allocOtherBalAmountStringArray;
	}
	public void setAllocOtherAmountStringArray(
			String [] allocOtherAmountStringArray) {
		this.allocOtherAmountStringArray = allocOtherAmountStringArray;
	}
	public String [] getAllocOtherAmountStringArray() {
		return allocOtherAmountStringArray;
	}
	public void setAllocTdsOtherAllAmtStringArray(
			String [] allocTdsOtherAllAmtStringArray) {
		this.allocTdsOtherAllAmtStringArray = allocTdsOtherAllAmtStringArray;
	}
	public String [] getAllocTdsOtherAllAmtStringArray() {
		return allocTdsOtherAllAmtStringArray;
	}
	public void setOtherAddChargeDesc(String otherAddChargeDesc) {
		this.otherAddChargeDesc = otherAddChargeDesc;
	}
	public String getOtherAddChargeDesc() {
		return otherAddChargeDesc;
	}
	public void setOtherAddChargeCode(String otherAddChargeCode) {
		this.otherAddChargeCode = otherAddChargeCode;
	}
	public String getOtherAddChargeCode() {
		return otherAddChargeCode;
	}
	public void setOtherAddBalanceAmount(String otherAddBalanceAmount) {
		this.otherAddBalanceAmount = otherAddBalanceAmount;
	}
	public String getOtherAddBalanceAmount() {
		return otherAddBalanceAmount;
	}
	public void setOtherAddAllocateAmount(String otherAddAllocateAmount) {
		this.otherAddAllocateAmount = otherAddAllocateAmount;
	}
	public String getOtherAddAllocateAmount() {
		return otherAddAllocateAmount;
	}
	public void setOtherAddTDSAmount(String otherAddTDSAmount) {
		this.otherAddTDSAmount = otherAddTDSAmount;
	}
	public String getOtherAddTDSAmount() {
		return otherAddTDSAmount;
	}
	public void setShowTotalBalanceAmt(String showTotalBalanceAmt) {
		this.showTotalBalanceAmt = showTotalBalanceAmt;
	}
	public String getShowTotalBalanceAmt() {
		return showTotalBalanceAmt;
	}
	public void setShowTotalAllocatedAmount(String showTotalAllocatedAmount) {
		this.showTotalAllocatedAmount = showTotalAllocatedAmount;
	}
	public String getShowTotalAllocatedAmount() {
		return showTotalAllocatedAmount;
	}
	public void setShowTotalTdsAmount(String showTotalTdsAmount) {
		this.showTotalTdsAmount = showTotalTdsAmount;
	}
	public String getShowTotalTdsAmount() {
		return showTotalTdsAmount;
	}
	public void setLoanRepaymentType(String loanRepaymentType) {
		this.loanRepaymentType = loanRepaymentType;
	}
	public String getLoanRepaymentType() {
		return loanRepaymentType;
	}
	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}
	public String getReceivedFrom() {
		return receivedFrom;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setAllocChargeCode(String allocChargeCode) {
		this.allocChargeCode = allocChargeCode;
	}
	public String getAllocChargeCode() {
		return allocChargeCode;
	}
	public void setAllocBalAmountArray(String [] allocBalAmountArray) {
		this.allocBalAmountArray = allocBalAmountArray;
	}
	public String [] getAllocBalAmountArray() {
		return allocBalAmountArray;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setAllocChargeDes(String [] allocChargeDes) {
		this.allocChargeDes = allocChargeDes;
	}
	public String [] getAllocChargeDes() {
		return allocChargeDes;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
}