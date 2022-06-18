package com.cp.vo;



public class BankAccountAnalysisVo
{
	private String bankAcAnId;
	private String lbxDealNo;
	private String dealNo;
	private String userId; 
	private String businessDate;
	private String lbxBankID;
	private String bankName;
	private String lbxBranchID;
	private String bankBranchName;
	private String accountType;
	private String accountNo; 
	private String statementMonthAndYear;
	private String totalEMI;
	private String credit;
	private String debit;
	private String highestBalance;
	private String lowestBalance;
	private String checkBounceAmount;
	private String checkBounceFrequency;  
	private String overLimitAmount;
	private String overLimitFrequency;
	private String swingAmt;
	private String swingPercent;
	private String customerName;
	private String bankBranch;
	private String month;
	private String year;
	private String chequeBouncing;
	private String limitObligation;
	private String lbxCustomerId;
	private String lbxCustomerRoleType;
	
	/* Arun Code for Individual BAnk account analysis starts here */
	private String totalCreditTxn;
	private String totalDebitTxn;
	private String varificationMethod;
	private String balanceAmount;
	private String remarks;
	
	private String inwardChequeReturnCount;
	private String outwardChequeReturnCount;
	private String totalDebitEntry;
	private String totalCreditEntry;
	private String totalInwardBouncingPerc;
	private String totalOutwardBouncingPerc;
	/*private String documentId;*/
	
	private String repaymentFromThisAccount;
	private String bankingSince;
	private String jointHolding;
	private String primaryAccount;
	private String avgBankBalance;
	private String inputWReturn;
	private String outputWReturn;
	private String odccimitamount;
	private String collateralDetails;
	
	private String bankAcCustName;
	private String customerList;
	private String dealCustomerName;
	//Abhishek Start
	private String documentId;
	private String applicantType;
    private String balanceAsOn;
    private String odccLimitUtilization;
    private String balanceAsOnDate1;
    private String balanceAsOnDate2;
    private String balanceAsOnDate3;
    private String balanceAsOnDate4;
    private String balanceAsOnDate1Amount;
    private String balanceAsOnDate2Amount;
    private String balanceAsOnDate3Amount;
    private String balanceAsOnDate4Amount;

    private String inwardAmount;
    private String outwardAmount;
    private String customerRoleType;
    private String customerRoleValue;
    private String customerId;
	private String customerType;
	/*private String odccHighestUtilization;
    private String odccMonthlyInterest;
//Abhishek End
	
	public String getDealCustomerName() {
		return dealCustomerName;
	}
	public String getInwardAmount() {
		return inwardAmount;
	}
	public void setInwardAmount(String inwardAmount) {
		this.inwardAmount = inwardAmount;
	}
	public String getOutwardAmount() {
		return outwardAmount;
	}
	public void setOutwardAmount(String outwardAmount) {
		this.outwardAmount = outwardAmount;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	public String getBalanceAsOn() {
		return balanceAsOn;
	}
	public void setBalanceAsOn(String balanceAsOn) {
		this.balanceAsOn = balanceAsOn;
	}
	public String getOdccLimitUtilization() {
		return odccLimitUtilization;
	}
	public void setOdccLimitUtilization(String odccLimitUtilization) {
		this.odccLimitUtilization = odccLimitUtilization;
	}
	public String getOdccHighestUtilization() {
		return odccHighestUtilization;
	}
	public void setOdccHighestUtilization(String odccHighestUtilization) {
		this.odccHighestUtilization = odccHighestUtilization;
	}
	public String getOdccMonthlyInterest() {
		return odccMonthlyInterest;
	}
	public void setOdccMonthlyInterest(String odccMonthlyInterest) {
		this.odccMonthlyInterest = odccMonthlyInterest;
	}
	private String odccHighestUtilization;
    private String odccMonthlyInterest;*/
	
	public String getRepaymentFromThisAccount() {
		return repaymentFromThisAccount;
	}
	public void setRepaymentFromThisAccount(String repaymentFromThisAccount) {
		this.repaymentFromThisAccount = repaymentFromThisAccount;
	}
	public String getBankingSince() {
		return bankingSince;
	}
	public void setBankingSince(String bankingSince) {
		this.bankingSince = bankingSince;
	}
	public String getJointHolding() {
		return jointHolding;
	}
	public void setJointHolding(String jointHolding) {
		this.jointHolding = jointHolding;
	}
	public String getPrimaryAccount() {
		return primaryAccount;
	}
	public void setPrimaryAccount(String primaryAccount) {
		this.primaryAccount = primaryAccount;
	}
	public String getAvgBankBalance() {
		return avgBankBalance;
	}
	public void setAvgBankBalance(String avgBankBalance) {
		this.avgBankBalance = avgBankBalance;
	}
	public String getInputWReturn() {
		return inputWReturn;
	}
	public void setInputWReturn(String inputWReturn) {
		this.inputWReturn = inputWReturn;
	}
	public String getOutputWReturn() {
		return outputWReturn;
	}
	public void setOutputWReturn(String outputWReturn) {
		this.outputWReturn = outputWReturn;
	}
	public String getOdccimitamount() {
		return odccimitamount;
	}
	public void setOdccimitamount(String odccimitamount) {
		this.odccimitamount = odccimitamount;
	}
	public String getCollateralDetails() {
		return collateralDetails;
	}
	public void setCollateralDetails(String collateralDetails) {
		this.collateralDetails = collateralDetails;
	}
	public String getInwardChequeReturnCount() {
		return inwardChequeReturnCount;
	}
	public void setInwardChequeReturnCount(String inwardChequeReturnCount) {
		this.inwardChequeReturnCount = inwardChequeReturnCount;
	}
	public String getOutwardChequeReturnCount() {
		return outwardChequeReturnCount;
	}
	public void setOutwardChequeReturnCount(String outwardChequeReturnCount) {
		this.outwardChequeReturnCount = outwardChequeReturnCount;
	}
	public String getTotalDebitEntry() {
		return totalDebitEntry;
	}
	public void setTotalDebitEntry(String totalDebitEntry) {
		this.totalDebitEntry = totalDebitEntry;
	}
	public String getTotalCreditEntry() {
		return totalCreditEntry;
	}
	public void setTotalCreditEntry(String totalCreditEntry) {
		this.totalCreditEntry = totalCreditEntry;
	}
	public String getTotalInwardBouncingPerc() {
		return totalInwardBouncingPerc;
	}
	public void setTotalInwardBouncingPerc(String totalInwardBouncingPerc) {
		this.totalInwardBouncingPerc = totalInwardBouncingPerc;
	}
	public String getTotalOutwardBouncingPerc() {
		return totalOutwardBouncingPerc;
	}
	public void setTotalOutwardBouncingPerc(String totalOutwardBouncingPerc) {
		this.totalOutwardBouncingPerc = totalOutwardBouncingPerc;
	}
	public String getTotalCreditTxn() {
		return totalCreditTxn;
	}
	public void setTotalCreditTxn(String totalCreditTxn) {
		this.totalCreditTxn = totalCreditTxn;
	}
	public String getTotalDebitTxn() {
		return totalDebitTxn;
	}
	public void setTotalDebitTxn(String totalDebitTxn) {
		this.totalDebitTxn = totalDebitTxn;
	}
	public String getVarificationMethod() {
		return varificationMethod;
	}
	public void setVarificationMethod(String varificationMethod) {
		this.varificationMethod = varificationMethod;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/* Arun Code for Individual BAnk account analysis ends here */
	public String getChequeBouncing() {
		return chequeBouncing;
	}
	public void setChequeBouncing(String chequeBouncing) {
		this.chequeBouncing = chequeBouncing;
	}
	public String getLimitObligation() {
		return limitObligation;
	}
	public void setLimitObligation(String limitObligation) {
		this.limitObligation = limitObligation;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSwingAmt() {
		return swingAmt;
	}
	public void setSwingAmt(String swingAmt) {
		this.swingAmt = swingAmt;
	}
	public String getSwingPercent() {
		return swingPercent;
	}
	public void setSwingPercent(String swingPercent) {
		this.swingPercent = swingPercent;
	}
	public String getBankAcAnId() {
		return bankAcAnId;
	}
	public void setBankAcAnId(String bankAcAnId) {
		this.bankAcAnId = bankAcAnId;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
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
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
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
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getStatementMonthAndYear() {
		return statementMonthAndYear;
	}
	public void setStatementMonthAndYear(String statementMonthAndYear) {
		this.statementMonthAndYear = statementMonthAndYear;
	}
	public String getTotalEMI() {
		return totalEMI;
	}
	public void setTotalEMI(String totalEMI) {
		this.totalEMI = totalEMI;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getHighestBalance() {
		return highestBalance;
	}
	public void setHighestBalance(String highestBalance) {
		this.highestBalance = highestBalance;
	}
	public String getLowestBalance() {
		return lowestBalance;
	}
	public void setLowestBalance(String lowestBalance) {
		this.lowestBalance = lowestBalance;
	}
	public String getCheckBounceAmount() {
		return checkBounceAmount;
	}
	public void setCheckBounceAmount(String checkBounceAmount) {
		this.checkBounceAmount = checkBounceAmount;
	}
	public String getCheckBounceFrequency() {
		return checkBounceFrequency;
	}
	public void setCheckBounceFrequency(String checkBounceFrequency) {
		this.checkBounceFrequency = checkBounceFrequency;
	}
	public String getOverLimitAmount() {
		return overLimitAmount;
	}
	public void setOverLimitAmount(String overLimitAmount) {
		this.overLimitAmount = overLimitAmount;
	}
	public String getOverLimitFrequency() {
		return overLimitFrequency;
	}
	public void setOverLimitFrequency(String overLimitFrequency) {
		this.overLimitFrequency = overLimitFrequency;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	public String getBalanceAsOn() {
		return balanceAsOn;
	}
	public void setBalanceAsOn(String balanceAsOn) {
		this.balanceAsOn = balanceAsOn;
	}
	public String getOdccLimitUtilization() {
		return odccLimitUtilization;
	}
	public void setOdccLimitUtilization(String odccLimitUtilization) {
		this.odccLimitUtilization = odccLimitUtilization;
	}
	public String getBalanceAsOnDate1() {
		return balanceAsOnDate1;
	}
	public void setBalanceAsOnDate1(String balanceAsOnDate1) {
		this.balanceAsOnDate1 = balanceAsOnDate1;
	}
	public String getBalanceAsOnDate2() {
		return balanceAsOnDate2;
	}
	public void setBalanceAsOnDate2(String balanceAsOnDate2) {
		this.balanceAsOnDate2 = balanceAsOnDate2;
	}
	public String getBalanceAsOnDate3() {
		return balanceAsOnDate3;
	}
	public void setBalanceAsOnDate3(String balanceAsOnDate3) {
		this.balanceAsOnDate3 = balanceAsOnDate3;
	}
	public String getBalanceAsOnDate4() {
		return balanceAsOnDate4;
	}
	public void setBalanceAsOnDate4(String balanceAsOnDate4) {
		this.balanceAsOnDate4 = balanceAsOnDate4;
	}
	public String getBalanceAsOnDate1Amount() {
		return balanceAsOnDate1Amount;
	}
	public void setBalanceAsOnDate1Amount(String balanceAsOnDate1Amount) {
		this.balanceAsOnDate1Amount = balanceAsOnDate1Amount;
	}
	public String getBalanceAsOnDate2Amount() {
		return balanceAsOnDate2Amount;
	}
	public void setBalanceAsOnDate2Amount(String balanceAsOnDate2Amount) {
		this.balanceAsOnDate2Amount = balanceAsOnDate2Amount;
	}
	public String getBalanceAsOnDate3Amount() {
		return balanceAsOnDate3Amount;
	}
	public void setBalanceAsOnDate3Amount(String balanceAsOnDate3Amount) {
		this.balanceAsOnDate3Amount = balanceAsOnDate3Amount;
	}
	public String getBalanceAsOnDate4Amount() {
		return balanceAsOnDate4Amount;
	}
	public void setBalanceAsOnDate4Amount(String balanceAsOnDate4Amount) {
		this.balanceAsOnDate4Amount = balanceAsOnDate4Amount;
	}
	public String getInwardAmount() {
		return inwardAmount;
	}
	public void setInwardAmount(String inwardAmount) {
		this.inwardAmount = inwardAmount;
	}
	public String getOutwardAmount() {
		return outwardAmount;
	}
	public void setOutwardAmount(String outwardAmount) {
		this.outwardAmount = outwardAmount;
	}
	public String getCustomerRoleType() {
		return customerRoleType;
	}
	public void setCustomerRoleType(String customerRoleType) {
		this.customerRoleType = customerRoleType;
	}
	public String getCustomerRoleValue() {
		return customerRoleValue;
	}
	public void setCustomerRoleValue(String customerRoleValue) {
		this.customerRoleValue = customerRoleValue;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/*public String getOdccHighestUtilization() {
		return odccHighestUtilization;
	}
	public void setOdccHighestUtilization(String odccHighestUtilization) {
		this.odccHighestUtilization = odccHighestUtilization;
	}
	public String getOdccMonthlyInterest() {
		return odccMonthlyInterest;
	}
	public void setOdccMonthlyInterest(String odccMonthlyInterest) {
		this.odccMonthlyInterest = odccMonthlyInterest;
	}*/
	public String getBankAcCustName() {
		return bankAcCustName;
	}
	public void setBankAcCustName(String bankAcCustName) {
		this.bankAcCustName = bankAcCustName;
	}
	public String getCustomerList() {
		return customerList;
	}
	public void setCustomerList(String customerList) {
		this.customerList = customerList;
	}
	public String getDealCustomerName() {
		return dealCustomerName;
	}
	public void setDealCustomerName(String dealCustomerName) {
		this.dealCustomerName = dealCustomerName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getLbxCustomerId() {
		return lbxCustomerId;
	}
	public void setLbxCustomerId(String lbxCustomerId) {
		this.lbxCustomerId = lbxCustomerId;
	}
	public String getLbxCustomerRoleType() {
		return lbxCustomerRoleType;
	}
	public void setLbxCustomerRoleType(String lbxCustomerRoleType) {
		this.lbxCustomerRoleType = lbxCustomerRoleType;
	}
	
	
}
