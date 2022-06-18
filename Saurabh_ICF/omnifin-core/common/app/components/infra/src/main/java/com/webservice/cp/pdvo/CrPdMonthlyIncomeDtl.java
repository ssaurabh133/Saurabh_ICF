package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdMonthlyIncomeDtl {

	private Integer id;
	private Integer pdId;
	private Integer customerId;
	private String customerName;
	private String applicantType;
	private String relationship;
	private String business;
	private BigDecimal salary;
	private String agriculture;
	private String argiAllied;
	private String rental;
	private String others;
	private BigDecimal householdIncome;

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

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public void setHouseholdIncome(BigDecimal householdIncome) {
		this.householdIncome = householdIncome;
	}
	public BigDecimal getHouseholdIncome() {
		return householdIncome;
	}
	public String getAgriculture() {
		return agriculture;
	}

	public void setAgriculture(String agriculture) {
		this.agriculture = agriculture;
	}

	public String getArgiAllied() {
		return argiAllied;
	}

	public void setArgiAllied(String argiAllied) {
		this.argiAllied = argiAllied;
	}

	public String getRental() {
		return rental;
	}

	public void setRental(String rental) {
		this.rental = rental;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}


	@Override
	public String toString() {
		return "CrPdMonthlyIncomeDtl [id=" + id + ", pdId=" + pdId
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", applicantType=" + applicantType
				+ ", relationship=" + relationship + ", business=" + business
				+ ", salary=" + salary + ", agriculture=" + agriculture
				+ ", argiAllied=" + argiAllied + ", rental=" + rental
				+ ", others=" + others + ", householdIncome=" + householdIncome
				+ "]";
	}

	
}
