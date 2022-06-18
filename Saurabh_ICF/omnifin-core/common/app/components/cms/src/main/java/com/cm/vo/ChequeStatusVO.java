package com.cm.vo;

public class ChequeStatusVO {
	 
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
     private String lbxBranchId;
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
	 private String businessPartnerDesc;
	 private String pdcFlag;
	 private String paymentMode;
	 private String lbxBPNID;
	 private String chequeStatus;
	 private String lbxBPTypeHID;
	 private int noOfRecords;
	 private String reasonLov;
	 private int totalRecordSize;
	 private String statusName;
	 private String hiddenInstrumentNo;
	 private String hiddenDate;
	 private String hiddenInstrumentAmount;
	 private String hiddenBusParDesc;
	 private String hiddenBusParType;
	 private String hiddenBank;
	 private String hiddenBranch;
	 private String defaultBranch;
	
	 private String hiddenStatus;
	 private String hiddenTDS;
	 private String hiddenPDCInstrumentID;	 
	 private int currentPageLink;
	 private String instrumentDate;
	//Start Here By Prashant
	 private String valueDate;
	 private String  hideDate;
	//End Here By Prashant
	 
	private String loanRecStatus;
	private String reciptNo;
	private String reciptDate;
	private String txnType;
	private String dealNo;
	private String lbxDealNo;
	private String receivedDate;
	private String authorId;
	
	private String reasonRemarks;
	private String depositBank;
	private String depositBankBranch;
	private String depositBankAccount;
	private String checkedinstrumentNo; 
	private String instrumentModeDesc;
	private int bounceCount;
	private String lbxPayInSlipHID;
	private String payInSlipId;
	private String receiptSource;
	
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
    public String getDealNo() {
		return dealNo;
	}
	public String getInstrumentModeDesc() {
		return instrumentModeDesc;
	}
	public void setInstrumentModeDesc(String instrumentModeDesc) {
		this.instrumentModeDesc = instrumentModeDesc;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getLbxDealNo() {
		return lbxDealNo;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getReciptNo() {
		return reciptNo;
	}
	public void setReciptNo(String reciptNo) {
		this.reciptNo = reciptNo;
	}
	public String getReciptDate() {
		return reciptDate;
	}
	public void setReciptDate(String reciptDate) {
		this.reciptDate = reciptDate;
	}
	public String getLoanRecStatus() {
			return loanRecStatus;
		}
		public void setLoanRecStatus(String loanRecStatus) {
			this.loanRecStatus = loanRecStatus;
		}
	public String getInstrumentDate() {
		return instrumentDate;
	}
	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public String getHiddenInstrumentNo() {
		return hiddenInstrumentNo;
	}
	public void setHiddenInstrumentNo(String hiddenInstrumentNo) {
		this.hiddenInstrumentNo = hiddenInstrumentNo;
	}
	public String getHiddenDate() {
		return hiddenDate;
	}
	public void setHiddenDate(String hiddenDate) {
		this.hiddenDate = hiddenDate;
	}
	public String getHiddenInstrumentAmount() {
		return hiddenInstrumentAmount;
	}
	public void setHiddenInstrumentAmount(String hiddenInstrumentAmount) {
		this.hiddenInstrumentAmount = hiddenInstrumentAmount;
	}
	public String getHiddenBusParDesc() {
		return hiddenBusParDesc;
	}
	public void setHiddenBusParDesc(String hiddenBusParDesc) {
		this.hiddenBusParDesc = hiddenBusParDesc;
	}
	public String getHiddenBusParType() {
		return hiddenBusParType;
	}
	public void setHiddenBusParType(String hiddenBusParType) {
		this.hiddenBusParType = hiddenBusParType;
	}
	public String getHiddenBank() {
		return hiddenBank;
	}
	public void setHiddenBank(String hiddenBank) {
		this.hiddenBank = hiddenBank;
	}
	public String getHiddenBranch() {
		return hiddenBranch;
	}
	public void setHiddenBranch(String hiddenBranch) {
		this.hiddenBranch = hiddenBranch;
	}
	public String getHiddenStatus() {
		return hiddenStatus;
	}
	public void setHiddenStatus(String hiddenStatus) {
		this.hiddenStatus = hiddenStatus;
	}
	public String getHiddenTDS() {
		return hiddenTDS;
	}
	public void setHiddenTDS(String hiddenTDS) {
		this.hiddenTDS = hiddenTDS;
	}
	public String getHiddenPDCInstrumentID() {
		return hiddenPDCInstrumentID;
	}
	public void setHiddenPDCInstrumentID(String hiddenPDCInstrumentID) {
		this.hiddenPDCInstrumentID = hiddenPDCInstrumentID;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getReasonLov() {
		return reasonLov;
	}
	public void setReasonLov(String reasonLov) {
		this.reasonLov = reasonLov;
	}

	private String reason;
	 private String lbxReasonHID;
     private int companyID;
	 private String checkBox;
	 
	 public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	public String getPdcInstrumentId() {
		return pdcInstrumentId;
	}
	public void setPdcInstrumentId(String pdcInstrumentId) {
		this.pdcInstrumentId = pdcInstrumentId;
	}
	private String tdsAmount;
	 private String pdcInstrumentId;
     
     public String getTdsAmount() {
		return tdsAmount;
	}
	public void setTdsAmount(String tdsAmount) {
		this.tdsAmount = tdsAmount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLbxReasonHID() {
		return lbxReasonHID;
	}
	public void setLbxReasonHID(String lbxReasonHID) {
		this.lbxReasonHID = lbxReasonHID;
	}
	public int getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	public String getPdcFlag() {
		return pdcFlag;
	}
	public void setPdcFlag(String pdcFlag) {
		this.pdcFlag = pdcFlag;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getLbxBPNID() {
		return lbxBPNID;
	}
	public void setLbxBPNID(String lbxBPNID) {
		this.lbxBPNID = lbxBPNID;
	}
	public String getChequeStatus() {
		return chequeStatus;
	}
	public void setChequeStatus(String chequeStatus) {
		this.chequeStatus = chequeStatus;
	}
	public String getLbxBPTypeHID() {
		return lbxBPTypeHID;
	}
	public void setLbxBPTypeHID(String lbxBPTypeHID) {
		this.lbxBPTypeHID = lbxBPTypeHID;
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
		public void setCheckBox(String checkBox) {
			this.checkBox = checkBox;
		}
		public String getCheckBox() {
			return checkBox;
		}
		public void setValueDate(String valueDate) {
			this.valueDate = valueDate;
		}
		public String getValueDate() {
			return valueDate;
		}
		
		public void setHideDate(String hideDate) {
			this.hideDate = hideDate;
		}
		public String getHideDate() {
			return hideDate;
		}
		public void setDefaultBranch(String defaultBranch) {
			this.defaultBranch = defaultBranch;
		}
		public String getDefaultBranch() {
			return defaultBranch;
		}
		public void setLbxBranchId(String lbxBranchId) {
			this.lbxBranchId = lbxBranchId;
		}
		public String getLbxBranchId() {
			return lbxBranchId;
		}
		public void setReasonRemarks(String reasonRemarks) {
			this.reasonRemarks = reasonRemarks;
		}
		public String getReasonRemarks() {
			return reasonRemarks;
		}
		public void setDepositBank(String depositBank) {
			this.depositBank = depositBank;
		}
		public String getDepositBank() {
			return depositBank;
		}
		public void setDepositBankBranch(String depositBankBranch) {
			this.depositBankBranch = depositBankBranch;
		}
		public String getDepositBankBranch() {
			return depositBankBranch;
		}
		public void setDepositBankAccount(String depositBankAccount) {
			this.depositBankAccount = depositBankAccount;
		}
		public String getDepositBankAccount() {
			return depositBankAccount;
		}
		public String getCheckedinstrumentNo() {
			return checkedinstrumentNo;
		}
		public void setCheckedinstrumentNo(String checkedinstrumentNo) {
			this.checkedinstrumentNo = checkedinstrumentNo;
		}
		public int getBounceCount() {
			return bounceCount;
		}
		public void setBounceCount(int bounceCount) {
			this.bounceCount = bounceCount;
		}
		public String getLbxPayInSlipHID() {
			return lbxPayInSlipHID;
		}
		public void setLbxPayInSlipHID(String lbxPayInSlipHID) {
			this.lbxPayInSlipHID = lbxPayInSlipHID;
		}
		public String getPayInSlipId() {
			return payInSlipId;
		}
		public void setPayInSlipId(String payInSlipId) {
			this.payInSlipId = payInSlipId;
		}
		public String getReceiptSource() {
			return receiptSource;
		}
		public void setReceiptSource(String receiptSource) {
			this.receiptSource = receiptSource;
		}
	
}
