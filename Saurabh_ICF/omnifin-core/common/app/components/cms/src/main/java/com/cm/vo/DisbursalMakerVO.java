package com.cm.vo;

public class DisbursalMakerVO {
	private String lbxLoanNoHID;
	private String loanNo;
	private String customerName;
	private String loanAmt;
	private String loanApprovalDate;
	private String product;
	private String scheme;
	private String disbursalNo;
	private String disbursedAmount;
	private String disbursalDescription;
	private String proposedShortPayAmount;
	private String adjustedShortPayAmount;
	private String disbursalDate;
	private String shortPayAmount;
	private String disbursalAmount;
	private String makerId;
	private String makerDate;
	private String authorId;
	private String authorDate;
	private String finalDisbursal;
	private String proposedDisbursalDate;
	private String proposedDisbursalAmount;
	private String repayEffDate;
	private String authorRemarks;
	private String cycleDate;
	private String cycleDateValue;
	private String cycleDateDesc;
	private String repayMode;
	private String pdcDepositCount;
	private String nextDueDate;
	private String currentMonthEMI;
	private String preEMINextMonth;
	private String proposedDisbursalFlag;
	private String loanDisbursalId;
	private String lbxBusinessPartnearHID;
	private String disbursalTo;
	private String maxExpectedPayDate;
	private String expectedPaymentDate;
	private String taLoanNo;
	
	private String lbxTaLoanNoHID;
	private String taCustomerName;
	
	private String maxDisbursalDate;
	/*code by arun for disbursal with payment Starts here*/
	private String  netAmount;
	private String totalPayable;
	private String adjustTotalPayable;
	private String totalReceivable;
	private String adjustTotalReceivable;
	private String paymentFlag;
	private String taFlag;
	private String paymentMode;
	private String paymentDate;
	private String instrumentNumber;
	private String instrumentDate;
	private String bankAccount;
	private String lbxbankAccountID;
	private String bank;
	private String lbxBankID;
	private String branch;
	private String lbxBranchID;
	private String micr;
	private String ifsCode;
	private String paymentAmount;
	private String tdsAmount;
	private String remarks;
	private String loanDisbursalAddId;
	private String taLoanId;
	private String bankName;
	private String branchName;
	private String penalIntCalcDate;
	private String addId[];
	private String disbursalBatchId;
	private String from;
	private String payTo;
	private String PayeeName;
	private String lbxpayTo;
	private String lbxpayeeName;
	private String disbursedAmountTemp;
	private String revolvingFlag;
	private String balancePrinc;
	private String forwardedAmt;
	private String defaultBranch;
	private String totalReceivableCustomer;
	private String paymentTypeArr[];
	private String favouringArr[];
	private String instrumentModeArr[];
	private String lbxbankAccountIDArr[];
	private String paymentType;
	private String favouring;
	private String instrumentMode;
	private String lbxInstrumentMode;
	private String installmentType;
	private String searchLoanNo;
	private String prePartmentAmount;
	private String lbxDealNo;
	private String searchCustomerName;	
	private String loanCurtail;
	/*code by arun for disbursal with payment Ends here*/
	private String interestDueDate;
	private String interestCalculationMethod;
	private String interestFrequency;
	private String interestCompoundingFrequency;
	
	// Virender Start for Beneficiary
	private String beneficiary_bankCode;
	private String beneficiary_bankBranchName;
	private String beneficiary_accountNo;
	private String beneficiary_ifscCode;
	private String beneficiary_lbxBankID;
	private String beneficiary_lbxBranchID;
	
	private String beneficiaryBankCode;
	private String beneficiaryBankBranchName;
	private String beneficiaryAccountNo;
	private String beneficiaryIfscCode;
	private String beneficiaryLbxBankID;
	private String beneficiaryLbxBranchID;
	private String micrCode;
	// Virender End for Beneficiary
	/* Code Added for Edit Due Date on Disbursal Screen| Start | 26022018 */
	private String editDueDate;
	private String oldRepayEffDate;
	private String loanTenure; //added by Brijesh Pathak
	private String maturityDate1;//added by Brijesh Pathak
	private String loanMaturityDate;//added by Brijesh Pathak
	//private String installmentType;//added by Brijesh Pathak
	
	
	
	public String getOldRepayEffDate() {
		return oldRepayEffDate;
	}
	public void setOldRepayEffDate(String oldRepayEffDate) {
		this.oldRepayEffDate = oldRepayEffDate;
	}
	public String getEditDueDate() {
		return editDueDate;
	}
	public void setEditDueDate(String editDueDate) {
		this.editDueDate = editDueDate;
	}
	
	/* Code Added for Edit Due Date on Disbursal Screen| End */
	public String getInterestCalculationMethod() {
		return interestCalculationMethod;
	}
	public void setInterestCalculationMethod(String interestCalculationMethod) {
		this.interestCalculationMethod = interestCalculationMethod;
	}
	public String getInterestFrequency() {
		return interestFrequency;
	}
	public void setInterestFrequency(String interestFrequency) {
		this.interestFrequency = interestFrequency;
	}
	public String getInterestCompoundingFrequency() {
		return interestCompoundingFrequency;
	}
	public void setInterestCompoundingFrequency(String interestCompoundingFrequency) {
		this.interestCompoundingFrequency = interestCompoundingFrequency;
	}
	public String getInterestDueDate() {
		return interestDueDate;
	}
	public void setInterestDueDate(String interestDueDate) {
		this.interestDueDate = interestDueDate;
	}
	public String getSearchLoanNo() {
		return searchLoanNo;
	}
	public void setSearchLoanNo(String searchLoanNo) {
		this.searchLoanNo = searchLoanNo;
	}
	public String getLbxDealNo() {
		return lbxDealNo;
	}
	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}
	public String getSearchCustomerName() {
		return searchCustomerName;
	}
	public void setSearchCustomerName(String searchCustomerName) {
		this.searchCustomerName = searchCustomerName;
	}
	public String getDefaultBranch() {
		return defaultBranch;
	}
	public void setDefaultBranch(String defaultBranch) {
		this.defaultBranch = defaultBranch;
	}
	public String getBalancePrinc() {
		return balancePrinc;
	}
	public void setBalancePrinc(String balancePrinc) {
		this.balancePrinc = balancePrinc;
	}
	public String getForwardedAmt() {
		return forwardedAmt;
	}
	public void setForwardedAmt(String forwardedAmt) {
		this.forwardedAmt = forwardedAmt;
	}
	public String getRevolvingFlag() {
		return revolvingFlag;
	}
	public void setRevolvingFlag(String revolvingFlag) {
		this.revolvingFlag = revolvingFlag;
	}
	public String getDisbursedAmountTemp() {
		return disbursedAmountTemp;
	}
	public void setDisbursedAmountTemp(String disbursedAmountTemp) {
		this.disbursedAmountTemp = disbursedAmountTemp;
	}
	public String getLbxpayTo() {
		return lbxpayTo;
	}
	public void setLbxpayTo(String lbxpayTo) {
		this.lbxpayTo = lbxpayTo;
	}
	public String getLbxpayeeName() {
		return lbxpayeeName;
	}
	public void setLbxpayeeName(String lbxpayeeName) {
		this.lbxpayeeName = lbxpayeeName;
	}
	public String getPayTo() {
		return payTo;
	}
	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}
	public String getPayeeName() {
		return PayeeName;
	}
	public void setPayeeName(String payeeName) {
		PayeeName = payeeName;
	}
	public String[] getAddId() {
		return addId;
	}
	public void setAddId(String[] addId) {
		this.addId = addId;
	}
	public String getBankName() {
		return bankName;
	}
	public String getPenalIntCalcDate() {
		return penalIntCalcDate;
	}
	public void setPenalIntCalcDate(String penalIntCalcDate) {
		this.penalIntCalcDate = penalIntCalcDate;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getMaxDisbursalDate() {
		return maxDisbursalDate;
	}
	public String getTaLoanId() {
		return taLoanId;
	}
	public void setTaLoanId(String taLoanId) {
		this.taLoanId = taLoanId;
	}
	public String getLoanDisbursalAddId() {
		return loanDisbursalAddId;
	}
	public void setLoanDisbursalAddId(String loanDisbursalAddId) {
		this.loanDisbursalAddId = loanDisbursalAddId;
	}
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	public String getTotalPayable() {
		return totalPayable;
	}
	public void setTotalPayable(String totalPayable) {
		this.totalPayable = totalPayable;
	}
	public String getAdjustTotalPayable() {
		return adjustTotalPayable;
	}
	public void setAdjustTotalPayable(String adjustTotalPayable) {
		this.adjustTotalPayable = adjustTotalPayable;
	}
	public String getTotalReceivable() {
		return totalReceivable;
	}
	public void setTotalReceivable(String totalReceivable) {
		this.totalReceivable = totalReceivable;
	}
	public String getAdjustTotalReceivable() {
		return adjustTotalReceivable;
	}
	public void setAdjustTotalReceivable(String adjustTotalReceivable) {
		this.adjustTotalReceivable = adjustTotalReceivable;
	}
	public String getPaymentFlag() {
		return paymentFlag;
	}
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	public String getTaFlag() {
		return taFlag;
	}
	public void setTaFlag(String taFlag) {
		this.taFlag = taFlag;
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
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getLbxbankAccountID() {
		return lbxbankAccountID;
	}
	public void setLbxbankAccountID(String lbxbankAccountID) {
		this.lbxbankAccountID = lbxbankAccountID;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getLbxBankID() {
		return lbxBankID;
	}
	public void setLbxBankID(String lbxBankID) {
		this.lbxBankID = lbxBankID;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getLbxBranchID() {
		return lbxBranchID;
	}
	public void setLbxBranchID(String lbxBranchID) {
		this.lbxBranchID = lbxBranchID;
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
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getTdsAmount() {
		return tdsAmount;
	}
	public void setTdsAmount(String tdsAmount) {
		this.tdsAmount = tdsAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setMaxDisbursalDate(String maxDisbursalDate) {
		this.maxDisbursalDate = maxDisbursalDate;
	}
	public String getTaLoanNo() {
		return taLoanNo;
	}
	public void setTaLoanNo(String taLoanNo) {
		this.taLoanNo = taLoanNo;
	}
	
	public String getTaCustomerName() {
		return taCustomerName;
	}
	public void setTaCustomerName(String taCustomerName) {
		this.taCustomerName = taCustomerName;
	}
	public String getLbxTaLoanNoHID() {
		return lbxTaLoanNoHID;
	}
	public void setLbxTaLoanNoHID(String lbxTaLoanNoHID) {
		this.lbxTaLoanNoHID = lbxTaLoanNoHID;
	}
	public String getMaxExpectedPayDate() {
		return maxExpectedPayDate;
	}
	public void setMaxExpectedPayDate(String maxExpectedPayDate) {
		this.maxExpectedPayDate = maxExpectedPayDate;
	}
	public String getExpectedPaymentDate() {
		return expectedPaymentDate;
	}
	public void setExpectedPaymentDate(String expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}
	private String supplierDesc;
	private String businessPartnerTypeDesc;
	
	public String getBusinessPartnerTypeDesc() {
		return businessPartnerTypeDesc;
	}
	public void setBusinessPartnerTypeDesc(String businessPartnerTypeDesc) {
		this.businessPartnerTypeDesc = businessPartnerTypeDesc;
	}
	public String getSupplierDesc() {
		return supplierDesc;
	}
	public void setSupplierDesc(String supplierDesc) {
		this.supplierDesc = supplierDesc;
	}
	public String getDisbursalTo() {
		return disbursalTo;
	}
	public void setDisbursalTo(String disbursalTo) {
		this.disbursalTo = disbursalTo;
	}
	public String getLbxBusinessPartnearHID() {
		return lbxBusinessPartnearHID;
	}
	public void setLbxBusinessPartnearHID(String lbxBusinessPartnearHID) {
		this.lbxBusinessPartnearHID = lbxBusinessPartnearHID;
	}
	
	public String getProposedDisbursalFlag() {
		return proposedDisbursalFlag;
	}
	public void setProposedDisbursalFlag(String proposedDisbursalFlag) {
		this.proposedDisbursalFlag = proposedDisbursalFlag;
	}
	public String getLoanDisbursalId() {
		return loanDisbursalId;
	}
	public void setLoanDisbursalId(String loanDisbursalId) {
		this.loanDisbursalId = loanDisbursalId;
	}
	public String getPreEMINextMonth() {
		return preEMINextMonth;
	}
	public void setPreEMINextMonth(String preEMINextMonth) {
		this.preEMINextMonth = preEMINextMonth;
	}
	public String getCurrentMonthEMI() {
		return currentMonthEMI;
	}
	public void setCurrentMonthEMI(String currentMonthEMI) {
		this.currentMonthEMI = currentMonthEMI;
	}

	public String getNextDueDate() {
		return nextDueDate;
	}
	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	public String getRepayEffDate() {
		return repayEffDate;
	}
	public void setRepayEffDate(String repayEffDate) {
		this.repayEffDate = repayEffDate;
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
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
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
	public void setDisbursalNo(String disbursalNo) {
		this.disbursalNo = disbursalNo;
	}
	public String getDisbursalNo() {
		return disbursalNo;
	}
	public String getDisbursedAmount() {
		return disbursedAmount;
	}
	public void setDisbursedAmount(String disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}
	public String getAdjustedShortPayAmount() {
		return adjustedShortPayAmount;
	}
	public void setAdjustedShortPayAmount(String adjustedShortPayAmount) {
		this.adjustedShortPayAmount = adjustedShortPayAmount;
	}
	public void setDisbursalDescription(String disbursalDescription) {
		this.disbursalDescription = disbursalDescription;
	}
	public String getDisbursalDescription() {
		return disbursalDescription;
	}
	public void setProposedShortPayAmount(String proposedShortPayAmount) {
		this.proposedShortPayAmount = proposedShortPayAmount;
	}
	public String getProposedShortPayAmount() {
		return proposedShortPayAmount;
	}
	public void setDisbursalDate(String disbursalDate) {
		this.disbursalDate = disbursalDate;
	}
	public String getDisbursalDate() {
		return disbursalDate;
	}
	public void setShortPayAmount(String shortPayAmount) {
		this.shortPayAmount = shortPayAmount;
	}
	public String getShortPayAmount() {
		return shortPayAmount;
	}
	public void setDisbursalAmount(String disbursalAmount) {
		this.disbursalAmount = disbursalAmount;
	}
	public String getDisbursalAmount() {
		return disbursalAmount;
	}
	public void setFinalDisbursal(String finalDisbursal) {
		this.finalDisbursal = finalDisbursal;
	}
	public String getFinalDisbursal() {
		return finalDisbursal;
	}
	public void setProposedDisbursalDate(String proposedDisbursalDate) {
		this.proposedDisbursalDate = proposedDisbursalDate;
	}
	public String getProposedDisbursalDate() {
		return proposedDisbursalDate;
	}
	public void setProposedDisbursalAmount(String proposedDisbursalAmount) {
		this.proposedDisbursalAmount = proposedDisbursalAmount;
	}
	public String getProposedDisbursalAmount() {
		return proposedDisbursalAmount;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setCycleDateValue(String cycleDateValue) {
		this.cycleDateValue = cycleDateValue;
	}
	public String getCycleDateValue() {
		return cycleDateValue;
	}
	public void setCycleDateDesc(String cycleDateDesc) {
		this.cycleDateDesc = cycleDateDesc;
	}
	public String getCycleDateDesc() {
		return cycleDateDesc;
	}
	public void setCycleDate(String cycleDate) {
		this.cycleDate = cycleDate;
	}
	public String getCycleDate() {
		return cycleDate;
	}
	public void setRepayMode(String repayMode) {
		this.repayMode = repayMode;
	}
	public String getRepayMode() {
		return repayMode;
	}
	public void setPdcDepositCount(String pdcDepositCount) {
		this.pdcDepositCount = pdcDepositCount;
	}
	public String getPdcDepositCount() {
		return pdcDepositCount;
	}
	public void setDisbursalBatchId(String disbursalBatchId) {
		this.disbursalBatchId = disbursalBatchId;
	}
	public String getDisbursalBatchId() {
		return disbursalBatchId;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFrom() {
		return from;
	}
	public void setTotalReceivableCustomer(String totalReceivableCustomer) {
		this.totalReceivableCustomer = totalReceivableCustomer;
	}
	public String getTotalReceivableCustomer() {
		return totalReceivableCustomer;
	}
	public String[] getPaymentTypeArr() {
		return paymentTypeArr;
	}
	public void setPaymentTypeArr(String[] paymentTypeArr) {
		this.paymentTypeArr = paymentTypeArr;
	}
	public String[] getFavouringArr() {
		return favouringArr;
	}
	public void setFavouringArr(String[] favouringArr) {
		this.favouringArr = favouringArr;
	}
	public String[] getInstrumentModeArr() {
		return instrumentModeArr;
	}
	public void setInstrumentModeArr(String[] instrumentModeArr) {
		this.instrumentModeArr = instrumentModeArr;
	}
	public String[] getLbxbankAccountIDArr() {
		return lbxbankAccountIDArr;
	}
	public void setLbxbankAccountIDArr(String[] lbxbankAccountIDArr) {
		this.lbxbankAccountIDArr = lbxbankAccountIDArr;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getFavouring() {
		return favouring;
	}
	public void setFavouring(String favouring) {
		this.favouring = favouring;
	}
	public String getInstrumentMode() {
		return instrumentMode;
	}
	public void setInstrumentMode(String instrumentMode) {
		this.instrumentMode = instrumentMode;
	}
	public String getLbxInstrumentMode() {
		return lbxInstrumentMode;
	}
	public void setLbxInstrumentMode(String lbxInstrumentMode) {
		this.lbxInstrumentMode = lbxInstrumentMode;
	}
	public String getInstallmentType() {
		return installmentType;
	}
	public void setInstallmentType(String installmentType) {
		this.installmentType = installmentType;
	}
	public String getPrePartmentAmount() {
		return prePartmentAmount;
	}
	public void setPrePartmentAmount(String prePartmentAmount) {
		this.prePartmentAmount = prePartmentAmount;
	}
public String getLoanCurtail() {
		return loanCurtail;
	}
	public void setLoanCurtail(String loanCurtail) {
		this.loanCurtail = loanCurtail;
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
	//start here | Brijesh Pathak
	public String getLoanTenure() {
		return loanTenure;
	}
	public void setLoanTenure(String loanTenure) {
		this.loanTenure = loanTenure;
	}
	
	public String getMaturityDate1() {
		return maturityDate1;
	}
	public void setMaturityDate1(String maturityDate1) {
		this.maturityDate1 = maturityDate1;
	}
	public String getLoanMaturityDate() {
		return loanMaturityDate;
	}
	public void setLoanMaturityDate(String loanMaturityDate) {
		this.loanMaturityDate = loanMaturityDate;
	}
	
	/*public String getInstallmentType() {
		return installmentType;
	}
	public void setInstallmentType(String installmentType) {
		this.installmentType = installmentType;
	}*/
	// end here | Brijesh Pathak
}
