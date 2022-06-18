package com.cm.vo;


import java.io.Serializable;



import java.io.Serializable;

public class PaymentMakerForCMVO  implements Serializable

{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loanAccountNumber;
	private String customerName;
	private String businessPartnerType;
	private String lbxBPType;
	private String businessPartnerDesc;
	private String businessPartnerName;
	private String lbxBPNID;
	private String paymentDate;
	private String paymentMode;
	private String paymentAmount;
	private String instrumentNumber;
	private String hidPmntId;
	private String instrumentID;
	private String instrumentDate;
	private String bank;
	private String branch;
	private String micr;
	private String ifsCode;
	private String remarks;
	private String dealId;
	private String lbxLoanNoHID;
	private String lbxBankID;
	private String lbxBranchID;
	private String chargeDesc;
	private String originalAmount;
	private String adviceAmount;
	private String waiveOffAmount;
	private String adjustedAmount;
	private String balanceAmount;
	private String amountInProcess;
	private String allotedAmount;
	private String txnAdvicedID;
	private String radiobutton;
	private String lovKey;
	private String lovValue;
	private String lovPageTitle;
	private String lovKeyTitle;
	private String lovValueTitle;
	private String lovIdComponent;
	private String comments;
	private String decision;
	private String businessDate;
	private String makerId;
	private String[] allocatedArry;
	private String[] txnAdvicedIDArry;
	private String[] amountInProcessArry;
	private String tdsAmount;
	private String tdsadviseAmount;
	private String[] tdsAmountArry;
	private String tdsAllocatedAmount;
	private String[] tdsAllocatedArry;
	private String bankAccount;
	private String branchId;
	private String authorRemarks;
	private String userId;
	private String modifyNo;
	private int currentPageLink;
	private int totalRecordSize;
	private String lbxpaymentMode;
	private String procVal;
	private String payTo;
	private String lbxpayTo;
	private String payeeName;
	private String lbxpayeeName;
	private String userName;
	private String reportingToUserId;
	private String lbxUserId;
	private String loanRecStatus;
	private String defaultBranch;
	private String	taStatus;
	private String lbxTaLoanNoHID;
	private String taCustomerName;
	private String taLoanNo;
	
	// Virender Start for Beneficiary
	private String beneficiary_bankCode;
	private String beneficiary_bankBranchName;
	private String beneficiary_accountNo;
	private String beneficiary_ifscCode;
	private String beneficiary_lbxBankID;
	private String beneficiary_lbxBranchID;
	// Virender End for Beneficiary
	
	private String beneficiaryBankCode;
	private String beneficiaryBankBranchName;
	private String beneficiaryAccountNo;
	private String beneficiaryIfscCode;
	private String beneficiaryLbxBankID;
	private String beneficiaryLbxBranchID;
	private String micrCode;
	
	private String depositBankCode;
	private String depositBankBranchName;
	private String depositAccountNo;
	private String depositIfscCode;
	private String depositLbxBankID;
	private String depositLbxBranchID;
	private String depositmicrCode;
	
	public String getTaStatus() {
		return taStatus;
	}
	public void setTaStatus(String taStatus) {
		this.taStatus = taStatus;
	}
	public String getLbxTaLoanNoHID() {
		return lbxTaLoanNoHID;
	}
	public void setLbxTaLoanNoHID(String lbxTaLoanNoHID) {
		this.lbxTaLoanNoHID = lbxTaLoanNoHID;
	}
	public String getTaCustomerName() {
		return taCustomerName;
	}
	public void setTaCustomerName(String taCustomerName) {
		this.taCustomerName = taCustomerName;
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

	public String getPayTo() {
		return payTo;
	}
	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}
	public String getLbxpayTo() {
		return lbxpayTo;
	}
	public void setLbxpayTo(String lbxpayTo) {
		this.lbxpayTo = lbxpayTo;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getLbxpayeeName() {
		return lbxpayeeName;
	}
	public void setLbxpayeeName(String lbxpayeeName) {
		this.lbxpayeeName = lbxpayeeName;
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
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getLoanAccountNumber() {
		return loanAccountNumber;
	}
	public void setLoanAccountNumber(String loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
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
	public String getLbxBPType() {
		return lbxBPType;
	}
	public void setLbxBPType(String lbxBPType) {
		this.lbxBPType = lbxBPType;
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
	public String getLbxBPNID() {
		return lbxBPNID;
	}
	public void setLbxBPNID(String lbxBPNID) {
		this.lbxBPNID = lbxBPNID;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getInstrumentNumber() {
		return instrumentNumber;
	}
	public void setInstrumentNumber(String instrumentNumber) {
		this.instrumentNumber = instrumentNumber;
	}
	public String getHidPmntId() {
		return hidPmntId;
	}
	public void setHidPmntId(String hidPmntId) {
		this.hidPmntId = hidPmntId;
	}
	public String getInstrumentID() {
		return instrumentID;
	}
	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
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
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
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
	public String getTxnAdvicedID() {
		return txnAdvicedID;
	}
	public void setTxnAdvicedID(String txnAdvicedID) {
		this.txnAdvicedID = txnAdvicedID;
	}
	public String getRadiobutton() {
		return radiobutton;
	}
	public void setRadiobutton(String radiobutton) {
		this.radiobutton = radiobutton;
	}
	public String getLovKey() {
		return lovKey;
	}
	public void setLovKey(String lovKey) {
		this.lovKey = lovKey;
	}
	public String getLovValue() {
		return lovValue;
	}
	public void setLovValue(String lovValue) {
		this.lovValue = lovValue;
	}
	public String getLovPageTitle() {
		return lovPageTitle;
	}
	public void setLovPageTitle(String lovPageTitle) {
		this.lovPageTitle = lovPageTitle;
	}
	public String getLovKeyTitle() {
		return lovKeyTitle;
	}
	public void setLovKeyTitle(String lovKeyTitle) {
		this.lovKeyTitle = lovKeyTitle;
	}
	public String getLovValueTitle() {
		return lovValueTitle;
	}
	public void setLovValueTitle(String lovValueTitle) {
		this.lovValueTitle = lovValueTitle;
	}
	public String getLovIdComponent() {
		return lovIdComponent;
	}
	public void setLovIdComponent(String lovIdComponent) {
		this.lovIdComponent = lovIdComponent;
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
	public String getTdsAmount() {
		return tdsAmount;
	}
	public void setTdsAmount(String tdsAmount) {
		this.tdsAmount = tdsAmount;
	}
	public String getTdsadviseAmount() {
		return tdsadviseAmount;
	}
	public void setTdsadviseAmount(String tdsadviseAmount) {
		this.tdsadviseAmount = tdsadviseAmount;
	}
	public String[] getTdsAmountArry() {
		return tdsAmountArry;
	}
	public void setTdsAmountArry(String[] tdsAmountArry) {
		this.tdsAmountArry = tdsAmountArry;
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
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setWaiveOffAmount(String waiveOffAmount) {
		this.waiveOffAmount = waiveOffAmount;
	}
	public String getWaiveOffAmount() {
		return waiveOffAmount;
	}
	public void setAdjustedAmount(String adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}
	public String getAdjustedAmount() {
		return adjustedAmount;
	}
	public void setAdviceAmount(String adviceAmount) {
		this.adviceAmount = adviceAmount;
	}
	public String getAdviceAmount() {
		return adviceAmount;
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
	public String getLbxpaymentMode() {
		return lbxpaymentMode;
	}
	public void setLbxpaymentMode(String lbxpaymentMode) {
		this.lbxpaymentMode = lbxpaymentMode;
	}
	public void setTaLoanNo(String taLoanNo) {
		this.taLoanNo = taLoanNo;
	}
	public String getTaLoanNo() {
		return taLoanNo;
	}
	
	public String getBeneficiary_bankCode() {
		return beneficiary_bankCode;
	}
	public void setBeneficiary_bankCode(String beneficiary_bankCode) {
		this.beneficiary_bankCode = beneficiary_bankCode;
	}
	public String getBeneficiary_bankBranchName() {
		return beneficiary_bankBranchName;
	}
	public void setBeneficiary_bankBranchName(String beneficiary_bankBranchName) {
		this.beneficiary_bankBranchName = beneficiary_bankBranchName;
	}
	public String getBeneficiary_accountNo() {
		return beneficiary_accountNo;
	}
	public void setBeneficiary_accountNo(String beneficiary_accountNo) {
		this.beneficiary_accountNo = beneficiary_accountNo;
	}
	public String getBeneficiary_ifscCode() {
		return beneficiary_ifscCode;
	}
	public void setBeneficiary_ifscCode(String beneficiary_ifscCode) {
		this.beneficiary_ifscCode = beneficiary_ifscCode;
	}
	public String getBeneficiary_lbxBankID() {
		return beneficiary_lbxBankID;
	}
	public void setBeneficiary_lbxBankID(String beneficiary_lbxBankID) {
		this.beneficiary_lbxBankID = beneficiary_lbxBankID;
	}
	public String getBeneficiary_lbxBranchID() {
		return beneficiary_lbxBranchID;
	}
	public void setBeneficiary_lbxBranchID(String beneficiary_lbxBranchID) {
		this.beneficiary_lbxBranchID = beneficiary_lbxBranchID;
	}
	public String getBeneficiaryBankCode() {
		return beneficiaryBankCode;
	}
	public void setBeneficiaryBankCode(String beneficiaryBankCode) {
		this.beneficiaryBankCode = beneficiaryBankCode;
	}
	public String getBeneficiaryBankBranchName() {
		return beneficiaryBankBranchName;
	}
	public void setBeneficiaryBankBranchName(String beneficiaryBankBranchName) {
		this.beneficiaryBankBranchName = beneficiaryBankBranchName;
	}
	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}
	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}
	public String getBeneficiaryIfscCode() {
		return beneficiaryIfscCode;
	}
	public void setBeneficiaryIfscCode(String beneficiaryIfscCode) {
		this.beneficiaryIfscCode = beneficiaryIfscCode;
	}
	public String getBeneficiaryLbxBankID() {
		return beneficiaryLbxBankID;
	}
	public void setBeneficiaryLbxBankID(String beneficiaryLbxBankID) {
		this.beneficiaryLbxBankID = beneficiaryLbxBankID;
	}
	public String getBeneficiaryLbxBranchID() {
		return beneficiaryLbxBranchID;
	}
	public void setBeneficiaryLbxBranchID(String beneficiaryLbxBranchID) {
		this.beneficiaryLbxBranchID = beneficiaryLbxBranchID;
	}
	public String getMicrCode() {
		return micrCode;
	}
	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}
	public String getDepositBankCode() {
		return depositBankCode;
	}
	public void setDepositBankCode(String depositBankCode) {
		this.depositBankCode = depositBankCode;
	}
	public String getDepositBankBranchName() {
		return depositBankBranchName;
	}
	public void setDepositBankBranchName(String depositBankBranchName) {
		this.depositBankBranchName = depositBankBranchName;
	}
	public String getDepositAccountNo() {
		return depositAccountNo;
	}
	public void setDepositAccountNo(String depositAccountNo) {
		this.depositAccountNo = depositAccountNo;
	}
	public String getDepositIfscCode() {
		return depositIfscCode;
	}
	public void setDepositIfscCode(String depositIfscCode) {
		this.depositIfscCode = depositIfscCode;
	}
	public String getDepositLbxBankID() {
		return depositLbxBankID;
	}
	public void setDepositLbxBankID(String depositLbxBankID) {
		this.depositLbxBankID = depositLbxBankID;
	}
	public String getDepositLbxBranchID() {
		return depositLbxBranchID;
	}
	public void setDepositLbxBranchID(String depositLbxBranchID) {
		this.depositLbxBranchID = depositLbxBranchID;
	}
	public String getDepositmicrCode() {
		return depositmicrCode;
	}
	public void setDepositmicrCode(String depositmicrCode) {
		this.depositmicrCode = depositmicrCode;
	}
	
	
	
}
