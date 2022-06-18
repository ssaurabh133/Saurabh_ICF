package com.cp.vo;

public class SpecialConditionVo {
	
	private String specialCondition;
	private String dealId;
	private String makerId;
	private String makerDate;
	private String dealLoanId;
	private String  specialDealId;
	private String specialRemarks;
	private String[] chk;
	private String specRemark;
	private String loanId;
	private String loanDisbursalId;
	private String specConditionStatus;
	private String specConditionStatusCode;
	private String specialCategory;
	private String[] specRemarks; //Add Abhishek 
	private String[] specConditionstatus; //Add Abhishek
	
	
	
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getLoanDisbursalId() {
		return loanDisbursalId;
	}

	public void setLoanDisbursalId(String loanDisbursalId) {
		this.loanDisbursalId = loanDisbursalId;
	}

	public String[] getChk() {
		return chk;
	}

	public void setChk(String[] chk) {
		this.chk = chk;
	}
	public String getSpecRemark() {
		return specRemark;
	}

	public void setSpecRemark(String specRemark) {
		this.specRemark = specRemark;
	}

	public String getDealLoanId() {
		return dealLoanId;
	}

	public void setDealLoanId(String dealLoanId) {
		this.dealLoanId = dealLoanId;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
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

	public void setSpecialCondition(String specialCondition) {
		this.specialCondition = specialCondition;
	}

	public String getSpecialCondition() {
		return specialCondition;
	}

	public void setSpecialDealId(String specialDealId) {
		this.specialDealId = specialDealId;
	}

	public String getSpecialDealId() {
		return specialDealId;
	}

	public void setSpecialRemarks(String specialRemarks) {
		this.specialRemarks = specialRemarks;
	}

	public String getSpecialRemarks() {
		return specialRemarks;
	}

	public void setSpecConditionStatus(String specConditionStatus) {
		this.specConditionStatus = specConditionStatus;
	}

	public String getSpecConditionStatus() {
		return specConditionStatus;
	}

	public void setSpecConditionStatusCode(String specConditionStatusCode) {
		this.specConditionStatusCode = specConditionStatusCode;
	}

	public String getSpecConditionStatusCode() {
		return specConditionStatusCode;
	}

	public void setSpecialCategory(String specialCategory) {
		this.specialCategory = specialCategory;
	}

	public String getSpecialCategory() {
		return specialCategory;
	}

	public String[] getSpecRemarks() {
		return specRemarks;
}

	public void setSpecRemarks(String[] specRemarks) {
		this.specRemarks = specRemarks;
	}

	public String[] getSpecConditionstatus() {
		return specConditionstatus;
	}

	public void setSpecConditionstatus(String[] specConditionstatus) {
		this.specConditionstatus = specConditionstatus;
	}

}
