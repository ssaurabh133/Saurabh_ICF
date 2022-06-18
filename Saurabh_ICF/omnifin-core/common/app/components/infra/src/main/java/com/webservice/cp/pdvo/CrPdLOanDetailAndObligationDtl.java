package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdLOanDetailAndObligationDtl {

	private Integer id;
	private Integer pdId;
	private String loanName;
	private String loanType;
	private String loanTakenFrom;
	private BigDecimal loanAmount;
	private BigDecimal emi;
	private Integer loanTenure;
	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	private BigDecimal balNoOfEmi;
	private String considerInObligation;
	private String obligationRemark;
	private String dpdOverDue;
	private String observationClient;
	private String soaAvailable;
	private String observationCredit;
	private String obligation;
	private String totalObligation;
	private String customerRemark;
	private String justificationByCredit;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}


	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLoanTakenFrom() {
		return loanTakenFrom;
	}

	public void setLoanTakenFrom(String loanTakenFrom) {
		this.loanTakenFrom = loanTakenFrom;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getEmi() {
		return emi;
	}

	public void setEmi(BigDecimal emi) {
		this.emi = emi;
	}

	public Integer getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(Integer loanTenure) {
		this.loanTenure = loanTenure;
	}

	public BigDecimal getBalNoOfEmi() {
		return balNoOfEmi;
	}

	public void setBalNoOfEmi(BigDecimal balNoOfEmi) {
		this.balNoOfEmi = balNoOfEmi;
	}

	public String getConsiderInObligation() {
		return considerInObligation;
	}

	public void setConsiderInObligation(String considerInObligation) {
		this.considerInObligation = considerInObligation;
	}

	public String getObligationRemark() {
		return obligationRemark;
	}

	public void setObligationRemark(String obligationRemark) {
		this.obligationRemark = obligationRemark;
	}

	public String getDpdOverDue() {
		return dpdOverDue;
	}

	public void setDpdOverDue(String dpdOverDue) {
		this.dpdOverDue = dpdOverDue;
	}

	public String getObservationClient() {
		return observationClient;
	}

	public void setObservationClient(String observationClient) {
		this.observationClient = observationClient;
	}

	public String getSoaAvailable() {
		return soaAvailable;
	}

	public void setSoaAvailable(String soaAvailable) {
		this.soaAvailable = soaAvailable;
	}

	public String getObservationCredit() {
		return observationCredit;
	}

	public void setObservationCredit(String observationCredit) {
		this.observationCredit = observationCredit;
	}

	public String getObligation() {
		return obligation;
	}

	public void setObligation(String obligation) {
		this.obligation = obligation;
	}

	public String getTotalObligation() {
		return totalObligation;
	}

	public void setTotalObligation(String totalObligation) {
		this.totalObligation = totalObligation;
	}

	public String getCustomerRemark() {
		return customerRemark;
	}

	public void setCustomerRemark(String customerRemark) {
		this.customerRemark = customerRemark;
	}

	public String getJustificationByCredit() {
		return justificationByCredit;
	}

	public void setJustificationByCredit(String justificationByCredit) {
		this.justificationByCredit = justificationByCredit;
	}

	@Override
	public String toString() {
		return "CrPdLOanDetailAndObligationDtl [id=" + id + ", pdId=" + pdId
				+ ", loanName=" + loanName + ", loanType=" + loanType
				+ ", loanTakenFrom=" + loanTakenFrom + ", loanAmount="
				+ loanAmount + ", emi=" + emi + ", loanTenure=" + loanTenure
				+ ", balNoOfEmi=" + balNoOfEmi + ", considerInObligation="
				+ considerInObligation + ", obligationRemark="
				+ obligationRemark + ", dpdOverDue=" + dpdOverDue
				+ ", observationClient=" + observationClient
				+ ", soaAvailable=" + soaAvailable + ", observationCredit="
				+ observationCredit + ", obligation=" + obligation
				+ ", totalObligation=" + totalObligation + ", customerRemark="
				+ customerRemark + ", justificationByCredit="
				+ justificationByCredit + "]";
	}
}
