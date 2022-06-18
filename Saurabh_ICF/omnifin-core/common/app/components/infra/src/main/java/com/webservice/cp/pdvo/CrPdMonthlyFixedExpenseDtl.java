package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdMonthlyFixedExpenseDtl {

	private Integer id;
	private Integer pdId;
	private Integer customerId;
	private String customerName;
	private String applicantType;
	private String relationship;
	private BigDecimal existingLoanEmi;
	private BigDecimal satinEmi;
	
	

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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	
	
	public BigDecimal getExistingLoanEmi() {
		return existingLoanEmi;
	}

	public void setExistingLoanEmi(BigDecimal existingLoanEmi) {
		this.existingLoanEmi = existingLoanEmi;
	}

	public BigDecimal getSatinEmi() {
		return satinEmi;
	}

	public void setSatinEmi(BigDecimal satinEmi) {
		this.satinEmi = satinEmi;
	}

	@Override
	public String toString() {
		return "CrPdMonthlyFixedExpenseDtl [id=" + id + ", pdId=" + pdId
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", applicantType=" + applicantType
				+ ", relationship=" + relationship + ", existingLoanEmi="
				+ existingLoanEmi + ", satinEmi=" + satinEmi + "]";
	}
}
