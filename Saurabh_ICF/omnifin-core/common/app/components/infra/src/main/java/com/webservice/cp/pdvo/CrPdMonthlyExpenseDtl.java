package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdMonthlyExpenseDtl {

	private Integer id;
	private Integer pdId;
	private Integer customerId;
	private String customerName;
	private String applicantType;
	private String relationship;
	private BigDecimal foodAndClothing;
	private BigDecimal educational;
	private BigDecimal rent;
	private BigDecimal medical;
	private BigDecimal others;


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

	public BigDecimal getFoodAndClothing() {
		return foodAndClothing;
	}

	public void setFoodAndClothing(BigDecimal foodAndClothing) {
		this.foodAndClothing = foodAndClothing;
	}

	public BigDecimal getEducational() {
		return educational;
	}

	public void setEducational(BigDecimal educational) {
		this.educational = educational;
	}


	public BigDecimal getRent() {
		return rent;
	}

	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}

	public BigDecimal getMedical() {
		return medical;
	}

	public void setMedical(BigDecimal medical) {
		this.medical = medical;
	}

	public BigDecimal getOthers() {
		return others;
	}

	public void setOthers(BigDecimal others) {
		this.others = others;
	}
	@Override
	public String toString() {
		return "CrPdMonthlyExpenseDtl [id=" + id + ", pdId=" + pdId
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", applicantType=" + applicantType
				+ ", relationship=" + relationship + ", foodAndClothing="
				+ foodAndClothing + ", educational=" + educational + ", rent="
				+ rent + ", medical=" + medical + ", others=" + others + "]";
	}
}
