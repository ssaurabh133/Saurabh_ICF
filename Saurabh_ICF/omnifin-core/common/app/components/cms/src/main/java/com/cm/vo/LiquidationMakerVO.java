package com.cm.vo;

public class LiquidationMakerVO {
	private String loanNo;
	private String lbxLoanNoHID;
	private String customerName;
	private String product;
	private String scheme;
	private String loanAmt;
	private String loanApprovalDate;
	private String loanStatus;
	private String disbursalStatus;
	private String sdNo;
	private String sdLiquidationId;
	private String lbxSdNoHid;
	private String sdAmount;
	private String sdInterestType;
	private String sdInterestRate;
	private String liquidationFlag;
	private String gapInterest;
	private String gapTDS;
	private String gapInterestF;
	private String gapTDSF;
	private String sdCompoundingFreq;
	private String sdStartDate;
	private String sdMaturityDate;
	private String sdInterestAccrued;
	private String sdInterestAccruedDate;
	private String sdTDSRate;
	private String sdTDSDeducted;
	private String liquidatedAmountPrincipal;
	private String liquidationAmountPrincipal;
	private String liquidatedAmountInterest;
	private String liquidationAmountInterest;
	private String remarks;
	private String makerId;
	private String makerDate;
	private String authorRemarks;
	private String sdFinalInterest;
	
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public String getSdFinalInterest() {
		return sdFinalInterest;
	}
	public void setSdFinalInterest(String sdFinalInterest) {
		this.sdFinalInterest = sdFinalInterest;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
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
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
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
	public String getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getDisbursalStatus() {
		return disbursalStatus;
	}
	public void setDisbursalStatus(String disbursalStatus) {
		this.disbursalStatus = disbursalStatus;
	}
	public String getSdNo() {
		return sdNo;
	}
	public void setSdNo(String sdNo) {
		this.sdNo = sdNo;
	}
	public String getLbxSdNoHid() {
		return lbxSdNoHid;
	}
	public void setLbxSdNoHid(String lbxSdNoHid) {
		this.lbxSdNoHid = lbxSdNoHid;
	}
	public String getSdAmount() {
		return sdAmount;
	}
	public void setSdAmount(String sdAmount) {
		this.sdAmount = sdAmount;
	}
	public String getSdInterestType() {
		return sdInterestType;
	}
	public void setSdInterestType(String sdInterestType) {
		this.sdInterestType = sdInterestType;
	}
	public String getSdInterestRate() {
		return sdInterestRate;
	}
	public void setSdInterestRate(String sdInterestRate) {
		this.sdInterestRate = sdInterestRate;
	}
	public String getGapInterest() {
		return gapInterest;
	}
	public void setGapInterest(String gapInterest) {
		this.gapInterest = gapInterest;
	}
	public String getLiquidationFlag() {
		return liquidationFlag;
	}
	public void setLiquidationFlag(String liquidationFlag) {
		this.liquidationFlag = liquidationFlag;
	}
	public String getGapTDS() {
		return gapTDS;
	}
	public void setGapTDS(String gapTDS) {
		this.gapTDS = gapTDS;
	}
	public String getSdCompoundingFreq() {
		return sdCompoundingFreq;
	}
	public void setSdCompoundingFreq(String sdCompoundingFreq) {
		this.sdCompoundingFreq = sdCompoundingFreq;
	}
	public String getSdStartDate() {
		return sdStartDate;
	}
	public void setSdStartDate(String sdStartDate) {
		this.sdStartDate = sdStartDate;
	}
	public String getSdMaturityDate() {
		return sdMaturityDate;
	}
	public void setSdMaturityDate(String sdMaturityDate) {
		this.sdMaturityDate = sdMaturityDate;
	}
	public String getSdInterestAccrued() {
		return sdInterestAccrued;
	}
	public void setSdInterestAccrued(String sdInterestAccrued) {
		this.sdInterestAccrued = sdInterestAccrued;
	}
	public String getSdInterestAccruedDate() {
		return sdInterestAccruedDate;
	}
	public void setSdInterestAccruedDate(String sdInterestAccruedDate) {
		this.sdInterestAccruedDate = sdInterestAccruedDate;
	}
	public String getSdTDSRate() {
		return sdTDSRate;
	}
	public void setSdTDSRate(String sdTDSRate) {
		this.sdTDSRate = sdTDSRate;
	}
	public String getSdTDSDeducted() {
		return sdTDSDeducted;
	}
	public void setSdTDSDeducted(String sdTDSDeducted) {
		this.sdTDSDeducted = sdTDSDeducted;
	}
	public String getLiquidatedAmountPrincipal() {
		return liquidatedAmountPrincipal;
	}
	public void setLiquidatedAmountPrincipal(String liquidatedAmountPrincipal) {
		this.liquidatedAmountPrincipal = liquidatedAmountPrincipal;
	}
	public String getLiquidationAmountPrincipal() {
		return liquidationAmountPrincipal;
	}
	public void setLiquidationAmountPrincipal(String liquidationAmountPrincipal) {
		this.liquidationAmountPrincipal = liquidationAmountPrincipal;
	}
	public String getLiquidatedAmountInterest() {
		return liquidatedAmountInterest;
	}
	public void setLiquidatedAmountInterest(String liquidatedAmountInterest) {
		this.liquidatedAmountInterest = liquidatedAmountInterest;
	}
	public String getLiquidationAmountInterest() {
		return liquidationAmountInterest;
	}
	public void setLiquidationAmountInterest(String liquidationAmountInterest) {
		this.liquidationAmountInterest = liquidationAmountInterest;
	}
	public void setLoanApprovalDate(String loanApprovalDate) {
		this.loanApprovalDate = loanApprovalDate;
	}
	public String getLoanApprovalDate() {
		return loanApprovalDate;
	}
	public void setSdLiquidationId(String sdLiquidationId) {
		this.sdLiquidationId = sdLiquidationId;
	}
	public String getSdLiquidationId() {
		return sdLiquidationId;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setGapInterestF(String gapInterestF) {
		this.gapInterestF = gapInterestF;
	}
	public String getGapInterestF() {
		return gapInterestF;
	}
	public void setGapTDSF(String gapTDSF) {
		this.gapTDSF = gapTDSF;
	}
	public String getGapTDSF() {
		return gapTDSF;
	}
}
