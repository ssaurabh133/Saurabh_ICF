package com.cm.vo;

public class DisbCancellationVO {
	
	private String loanAc;
	private String lbxLoanNoHID;
	private String customerName;
	private String loanDate;
	private String loanAmt;
	private String product;
	private String scheme;					
	private String disbursalNumber;
	private String disbursedAmount;	
	private String disbursedDate;			
	private String disbursedTo;
	private String toName;					
	private String authorRemarks;
	private String cancellationFlag;
	private String cancellationMakerID;
	private String cancellationMakerDate;
	private String cancellationAuthorID;
	private String cancellationAuthorDate;
	private String cancellationRemarks;
	private String cancellationDate[];
	private String cancDate;
	private String stage;
	private String lbxUserId;
	private String branchId;
	private String userId;
	private String chk[];
	private String decision;
	private String comments;
	private String authorId;
	private String authorDate;
	private String closureStatus;
	private int    companyId;
	private String    txnAdjustedAmt;
	private String    amtInProcess;
	private String chkNew;
	private String billFlag;
	private String taFlag;
	private String taLoanNo;
	private String disbFlag;
	private String recType;
	private String disbursalID[];
	private String disbursalIDNew;
	private String billFlagYesOrNo;
	
	private String taLoanID;
	
	public String getTaLoanID() {
		return taLoanID;
	}
	public void setTaLoanID(String taLoanID) {
		this.taLoanID = taLoanID;
	}
	public String getChkNew() {
		return chkNew;
	}
	public void setChkNew(String chkNew) {
		this.chkNew = chkNew;
	}
	public String[] getCancellationDate() {
		return cancellationDate;
	}
	public void setCancellationDate(String[] cancellationDate) {
		this.cancellationDate = cancellationDate;
	}
	public String[] getChk() {
		return chk;
	}
	public void setChk(String[] chk) {
		this.chk = chk;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
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
	private String reportingToUserId;
	private int currentPageLink;
	private int totalRecordSize;
	
	
	public String getCancellationFlag() {
		return cancellationFlag;
	}
	public void setCancellationFlag(String cancellationFlag) {
		this.cancellationFlag = cancellationFlag;
	}
	public String getCancellationMakerID() {
		return cancellationMakerID;
	}
	public void setCancellationMakerID(String cancellationMakerID) {
		this.cancellationMakerID = cancellationMakerID;
	}
	public String getCancellationMakerDate() {
		return cancellationMakerDate;
	}
	public void setCancellationMakerDate(String cancellationMakerDate) {
		this.cancellationMakerDate = cancellationMakerDate;
	}
	public String getCancellationAuthorID() {
		return cancellationAuthorID;
	}
	public void setCancellationAuthorID(String cancellationAuthorID) {
		this.cancellationAuthorID = cancellationAuthorID;
	}
	public String getCancellationAuthorDate() {
		return cancellationAuthorDate;
	}
	public void setCancellationAuthorDate(String cancellationAuthorDate) {
		this.cancellationAuthorDate = cancellationAuthorDate;
	}
	public String getCancellationRemarks() {
		return cancellationRemarks;
	}
	public void setCancellationRemarks(String cancellationRemarks) {
		this.cancellationRemarks = cancellationRemarks;
	}
	public String getLoanAc() {
		return loanAc;
	}
	public void setLoanAc(String loanAc) {
		this.loanAc = loanAc;
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
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
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
	
	public String getDisbursalNumber() {
		return disbursalNumber;
	}
	public void setDisbursalNumber(String disbursalNumber) {
		this.disbursalNumber = disbursalNumber;
	}
	public String getDisbursedAmount() {
		return disbursedAmount;
	}
	public void setDisbursedAmount(String disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}
	public String getDisbursedDate() {
		return disbursedDate;
	}
	public void setDisbursedDate(String disbursedDate) {
		this.disbursedDate = disbursedDate;
	}
	public String getDisbursedTo() {
		return disbursedTo;
	}
	public void setDisbursedTo(String disbursedTo) {
		this.disbursedTo = disbursedTo;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	public String getClosureStatus() {
		return closureStatus;
	}
	public void setClosureStatus(String closureStatus) {
		this.closureStatus = closureStatus;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getTxnAdjustedAmt() {
		return txnAdjustedAmt;
	}
	public void setTxnAdjustedAmt(String txnAdjustedAmt) {
		this.txnAdjustedAmt = txnAdjustedAmt;
	}
	public String getAmtInProcess() {
		return amtInProcess;
	}
	public void setAmtInProcess(String amtInProcess) {
		this.amtInProcess = amtInProcess;
	}
	public String getCancDate() {
		return cancDate;
	}
	public void setCancDate(String cancDate) {
		this.cancDate = cancDate;
	}
	public String getBillFlag() {
		return billFlag;
	}
	public void setBillFlag(String billFlag) {
		this.billFlag = billFlag;
	}
	public String getTaFlag() {
		return taFlag;
	}
	public void setTaFlag(String taFlag) {
		this.taFlag = taFlag;
	}
	public String getTaLoanNo() {
		return taLoanNo;
	}
	public void setTaLoanNo(String taLoanNo) {
		this.taLoanNo = taLoanNo;
	}
	public String getDisbFlag() {
		return disbFlag;
	}
	public void setDisbFlag(String disbFlag) {
		this.disbFlag = disbFlag;
	}
	public String getRecType() {
		return recType;
	}
	public void setRecType(String recType) {
		this.recType = recType;
	}
	public String[] getDisbursalID() {
		return disbursalID;
	}
	public void setDisbursalID(String[] disbursalID) {
		this.disbursalID = disbursalID;
	}
	public String getDisbursalIDNew() {
		return disbursalIDNew;
	}
	public void setDisbursalIDNew(String disbursalIDNew) {
		this.disbursalIDNew = disbursalIDNew;
	}
	
	/*     */ 
	/*     */   public String getBillFlagYesOrNo() {
	/* 335 */     return this.billFlagYesOrNo;
	/*     */   }
	/*     */   public void setBillFlagYesOrNo(String billFlagYesOrNo) {
	/* 338 */     this.billFlagYesOrNo = billFlagYesOrNo;
	/*     */   }
	
	
	
}
