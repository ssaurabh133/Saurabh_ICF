package com.cm.vo;

public class InstructionCapMakerVO {
	 
	 private String loanAccNo;
	 private String loanID;
	 private String customerName;
     private String instrumentType;
	 private String totalInstallments;
     private String installmentAmount;
     private String fromInstallment;
     private String toInstallment;
     private String startingChequeNo;
     private String endingChequeNo;
     
	private String bank;
     private String branch;
     private String micr;
     private String location;
     private String binNo;
     private String instrumentAmount;
     private String date;
     private String lbxLoanNoHID;
     private String lbxBankID;
     private String lbxBranchID;
     private String comments;
     private String decision;
     private int makerId;
     private String product;
     private String status;
     private String scheme;
     private String loanAmount;
     private String loanApprovalDate;
     private String instrumentID;
     private String lbxProductID;
     private String userID;
     private String makerID;
     private String makerDate;
     private String holdReason;
     private String instrumentNo;
     private String[] statusvalue;
     private String[] holdReasonvalue;
     private String fromStatus;
     private String toStatus;
     private String ifscCode;
     private String clearingType;
     private String bankAccount;
     private String allotedAmount;
     private String purpose;
     private String installmentNo;
 	 private String businessPartnerType;
 	 private String pdcPurposeValue;
 	 private String pdcPurposeDesc;
 	 private String remarks;
 	 private String ecsTransactionCodeDesc;
 	 private String ecsTransactionCodeValue;
 	 private String customeracTypeDesc;
  	 private String customeracTypeValue;
 	 private String spnserBnkBrncCodeDesc;
 	 private String spnserBnkBrncCodeValue;
 	 private String utilityNoDesc;
 	 private String ecsTransactionCode;
 	 private String customeracType;
 	 private String spnserBnkBrncCode;
 	 private String utilityNo;
	 private String userName;
	 private int totalRecordSize;
 	 private String procvalue;
 	 private String holdRemarks;
 	 private String authorRemarks;
 	 private int currentPageLink;
 	 private String reportingToUserId;
 	 private String lbxUserId;
 	 private String currentPurpose;
 	 private String[] checkedDateArr;
 	 private String[] instrumentId;
 	 private String repayment;
 	 private String totalEMI;
 	 private String currentStatus;
 	 private String reason;
 	 private String authorName;
 	 private String authorDate;
 	 
 	 
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getCurrentPurpose() {
		return currentPurpose;
	}
	public void setCurrentPurpose(String currentPurpose) {
		this.currentPurpose = currentPurpose;
	}
	private String installmentType;
	
	private String instrumentMode;
	private String	micrNonmicr;
	private String	maker;
	private String	holdUser;
	//added by neraj
	private String	submitBy;
	private String	clTypeCode;
	private String	clTypeDesc;
	
	private String otherInstallmentCharges;
	 private String totalChargeInstallmentAmount;
	 private String pdcSubmitCustomerName;
	 
	public String getOtherInstallmentCharges() {
		return otherInstallmentCharges;
	}
	public void setOtherInstallmentCharges(String otherInstallmentCharges) {
		this.otherInstallmentCharges = otherInstallmentCharges;
	}
	public String getTotalChargeInstallmentAmount() {
		return totalChargeInstallmentAmount;
	}
	public void setTotalChargeInstallmentAmount(String totalChargeInstallmentAmount) {
		this.totalChargeInstallmentAmount = totalChargeInstallmentAmount;
	}
	public String getClTypeCode() {
		return clTypeCode;
	}
	public void setClTypeCode(String clTypeCode) {
		this.clTypeCode = clTypeCode;
	}
	public String getClTypeDesc() {
		return clTypeDesc;
	}
	public void setClTypeDesc(String clTypeDesc) {
		this.clTypeDesc = clTypeDesc;
	}
	private String instrumentSelectedIds;
	private String	instrumentPreEmiDate;
	
    

	public String getInstrumentSelectedIds() {
		return instrumentSelectedIds;
	}
	public void setInstrumentSelectedIds(String instrumentSelectedIds) {
		this.instrumentSelectedIds = instrumentSelectedIds;
	}
	public String getSubmitBy() {
		return submitBy;
	}
	public void setSubmitBy(String submitBy) {
		this.submitBy = submitBy;
	}
	public String getMaker() {
		return maker;
	}
	public String getHoldUser() {
		return holdUser;
	}
	public void setHoldUser(String holdUser) {
		this.holdUser = holdUser;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getMicrNonmicr() {
		return micrNonmicr;
	}
	public void setMicrNonmicr(String micrNonmicr) {
		this.micrNonmicr = micrNonmicr;
	}
	public String getInstrumentMode() {
		return instrumentMode;
	}
	public void setInstrumentMode(String instrumentMode) {
		this.instrumentMode = instrumentMode;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getInstallmentType() {
		return installmentType;
	}
	public void setInstallmentType(String installmentType) {
		this.installmentType = installmentType;
	}
	public String getReportingToUserId() {

 		return reportingToUserId;
 	}
 	public void setReportingToUserId(String reportingToUserId) {
 		this.reportingToUserId = reportingToUserId;
 	}
	public String getProcvalue() {
			return procvalue;
	}
	public void setProcvalue(String procvalue) {
			this.procvalue = procvalue;
	}
	
  public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	public String getHoldRemarks() {
		return holdRemarks;
	}
	public void setHoldRemarks(String holdRemarks) {
		this.holdRemarks = holdRemarks;
	}
	public String getEcsTransactionCode() {
		return ecsTransactionCode;
	}
	public void setEcsTransactionCode(String ecsTransactionCode) {
		this.ecsTransactionCode = ecsTransactionCode;
	}
	public String getCustomeracType() {
		return customeracType;
	}
	public void setCustomeracType(String customeracType) {
		this.customeracType = customeracType;
	}
	public String getSpnserBnkBrncCode() {
		return spnserBnkBrncCode;
	}
	public void setSpnserBnkBrncCode(String spnserBnkBrncCode) {
		this.spnserBnkBrncCode = spnserBnkBrncCode;
	}
	public String getUtilityNo() {
		return utilityNo;
	}
	public void setUtilityNo(String utilityNo) {
		this.utilityNo = utilityNo;
	}
	public String getEcsTransactionCodeDesc() {
		return ecsTransactionCodeDesc;
	}
	public void setEcsTransactionCodeDesc(String ecsTransactionCodeDesc) {
		this.ecsTransactionCodeDesc = ecsTransactionCodeDesc;
	}
	public String getEcsTransactionCodeValue() {
		return ecsTransactionCodeValue;
	}
	public void setEcsTransactionCodeValue(String ecsTransactionCodeValue) {
		this.ecsTransactionCodeValue = ecsTransactionCodeValue;
	}
	public String getCustomeracTypeDesc() {
		return customeracTypeDesc;
	}
	public void setCustomeracTypeDesc(String customeracTypeDesc) {
		this.customeracTypeDesc = customeracTypeDesc;
	}
	public String getCustomeracTypeValue() {
		return customeracTypeValue;
	}
	public void setCustomeracTypeValue(String customeracTypeValue) {
		this.customeracTypeValue = customeracTypeValue;
	}
	public String getSpnserBnkBrncCodeDesc() {
		return spnserBnkBrncCodeDesc;
	}
	public void setSpnserBnkBrncCodeDesc(String spnserBnkBrncCodeDesc) {
		this.spnserBnkBrncCodeDesc = spnserBnkBrncCodeDesc;
	}
	public String getSpnserBnkBrncCodeValue() {
		return spnserBnkBrncCodeValue;
	}
	public void setSpnserBnkBrncCodeValue(String spnserBnkBrncCodeValue) {
		this.spnserBnkBrncCodeValue = spnserBnkBrncCodeValue;
	}
	public String getUtilityNoDesc() {
		return utilityNoDesc;
	}
	public void setUtilityNoDesc(String utilityNoDesc) {
		this.utilityNoDesc = utilityNoDesc;
	}
	public String getUtilityNoValue() {
		return utilityNoValue;
	}
	public void setUtilityNoValue(String utilityNoValue) {
		this.utilityNoValue = utilityNoValue;
	}
	private String utilityNoValue;
 	 
 	 
 	 
 	 
 	 
 	 
	 public String getPdcPurposeValue() {
		return pdcPurposeValue;
	}
	public void setPdcPurposeValue(String pdcPurposeValue) {
		this.pdcPurposeValue = pdcPurposeValue;
	}
	public String getPdcPurposeDesc() {
		return pdcPurposeDesc;
	}
	public void setPdcPurposeDesc(String pdcPurposeDesc) {
		this.pdcPurposeDesc = pdcPurposeDesc;
	}
	public String getPdcDate() {
		return pdcDate;
	}
	public void setPdcDate(String pdcDate) {
		this.pdcDate = pdcDate;
	}
	private String businessPartnerDesc;
	 private String pdcStatus;
	 private String branchId;
     private String pdcDate;
     
     public String getPdcStatus() {
		return pdcStatus;
	}
	public void setPdcStatus(String pdcStatus) {
		this.pdcStatus = pdcStatus;
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
	public String getInstallmentNo() {
 		return installmentNo;
 	}
 	public void setInstallmentNo(String installmentNo) {
 		this.installmentNo = installmentNo;
 	}
     public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getAllotedAmount() {
		return allotedAmount;
	}
	public void setAllotedAmount(String allotedAmount) {
		this.allotedAmount = allotedAmount;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getClearingType() {
		return clearingType;
	}
	public void setClearingType(String clearingType) {
		this.clearingType = clearingType;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getFromStatus() {
		return fromStatus;
	}
	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}
	public String getToStatus() {
		return toStatus;
	}
	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}
	public String[] getStatusvalue() {
		return statusvalue;
	}
	public void setStatusvalue(String[] statusvalue) {
		this.statusvalue = statusvalue;
	}
	public String[] getHoldReasonvalue() {
		return holdReasonvalue;
	}
	public void setHoldReasonvalue(String[] holdReasonvalue) {
		this.holdReasonvalue = holdReasonvalue;
	}
	
     
     
     
     public String getInstrumentNo() {
		return instrumentNo;
	}
	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}
	public String getHoldReason() {
		return holdReason;
	}
	public void setHoldReason(String holdReason) {
		this.holdReason = holdReason;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getUserID() {
 		return userID;
 	}
 	public void setUserID(String userID) {
 		this.userID = userID;
 	}
 	public String getMakerID() {
 		return makerID;
 	}
 	public void setMakerID(String makerID) {
 		this.makerID = makerID;
 	}
  
	public String getLbxProductID() {
		return lbxProductID;
	}
	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}
	public String getInstrumentID() {
		return instrumentID;
	}
	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
	}
	public String getLoanApprovalDate() {
		return loanApprovalDate;
	}
	public void setLoanApprovalDate(String loanApprovalDate) {
		this.loanApprovalDate = loanApprovalDate;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getMakerId() {
		return makerId;
	}
	public void setMakerId(int makerId) {
		this.makerId = makerId;
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
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInstrumentAmount() {
		return instrumentAmount;
	}
	public void setInstrumentAmount(String instrumentAmount) {
		this.instrumentAmount = instrumentAmount;
	}
	public String getLoanAccNo() {
		return loanAccNo;
	}
	public void setLoanAccNo(String loanAccNo) {
		this.loanAccNo = loanAccNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getTotalInstallments() {
		return totalInstallments;
	}
	public void setTotalInstallments(String totalInstallments) {
		this.totalInstallments = totalInstallments;
	}
	public String getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(String installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public String getFromInstallment() {
		return fromInstallment;
	}
	public void setFromInstallment(String fromInstallment) {
		this.fromInstallment = fromInstallment;
	}
	public String getToInstallment() {
		return toInstallment;
	}
	public void setToInstallment(String toInstallment) {
		this.toInstallment = toInstallment;
	}
	public String getStartingChequeNo() {
		return startingChequeNo;
	}
	public void setStartingChequeNo(String startingChequeNo) {
		this.startingChequeNo = startingChequeNo;
	}
	public String getEndingChequeNo() {
		return endingChequeNo;
	}
	public void setEndingChequeNo(String endingChequeNo) {
		this.endingChequeNo = endingChequeNo;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBinNo() {
		return binNo;
	}
	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}
	public String getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}
	  public String getLoanID() {
			return loanID;
		}
		public void setLoanID(String loanID) {
			this.loanID = loanID;
		}
		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}
		public String getBranchId() {
			return branchId;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setInstrumentPreEmiDate(String instrumentPreEmiDate) {
			this.instrumentPreEmiDate = instrumentPreEmiDate;
		}
		public String getInstrumentPreEmiDate() {
			return instrumentPreEmiDate;
		}

		public void setPdcSubmitCustomerName(String pdcSubmitCustomerName) {
			this.pdcSubmitCustomerName = pdcSubmitCustomerName;
		}
		public String getPdcSubmitCustomerName() {
			return pdcSubmitCustomerName;
		}
		public void setCheckedDateArr(String[] checkedDateArr) {
			this.checkedDateArr = checkedDateArr;
		}
		public String[] getCheckedDateArr() {
			return checkedDateArr;
		}
		public void setInstrumentId(String[] instrumentId) {
			this.instrumentId = instrumentId;
		}
		public String[] getInstrumentId() {
			return instrumentId;
		}
		public void setTotalEMI(String totalEMI) {
			this.totalEMI = totalEMI;
		}
		public String getTotalEMI() {
			return totalEMI;
		}
}
