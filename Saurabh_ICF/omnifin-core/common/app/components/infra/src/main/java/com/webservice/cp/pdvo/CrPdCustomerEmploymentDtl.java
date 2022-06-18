package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdCustomerEmploymentDtl {
	
	private Integer pdCustomerEmploymentId;
	
	private Integer pdCustomerId;
	
	private String isCurrent;
	
	private String companyName;
	
	private String typeOfCompany;
	
	private String designation;
	
	private String jobProfile;
	
	private Integer noOfYears;
	
	private BigDecimal grossIncome;
	
	private String contactPersonName;
	
	private String contactPersonNumber;
	
	private String saveAsDraft;
	
	private String makerId;
	
	private String makerDate;
	
	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;

	public Integer getPdCustomerEmploymentId() {
		return pdCustomerEmploymentId;
	}

	public void setPdCustomerEmploymentId(Integer pdCustomerEmploymentId) {
		this.pdCustomerEmploymentId = pdCustomerEmploymentId;
	}

	public Integer getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(Integer pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTypeOfCompany() {
		return typeOfCompany;
	}

	public void setTypeOfCompany(String typeOfCompany) {
		this.typeOfCompany = typeOfCompany;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getJobProfile() {
		return jobProfile;
	}

	public void setJobProfile(String jobProfile) {
		this.jobProfile = jobProfile;
	}

	public Integer getNoOfYears() {
		return noOfYears;
	}

	public void setNoOfYears(Integer noOfYears) {
		this.noOfYears = noOfYears;
	}

	public BigDecimal getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(BigDecimal grossIncome) {
		this.grossIncome = grossIncome;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactPersonNumber() {
		return contactPersonNumber;
	}

	public void setContactPersonNumber(String contactPersonNumber) {
		this.contactPersonNumber = contactPersonNumber;
	}

	public String getSaveAsDraft() {
		return saveAsDraft;
	}

	public void setSaveAsDraft(String saveAsDraft) {
		this.saveAsDraft = saveAsDraft;
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

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	@Override
	public String toString() {
		return "CrPdCustomerEmploymentDtl [pdCustomerEmploymentId="
				+ pdCustomerEmploymentId + ", pdCustomerId=" + pdCustomerId
				+ ", isCurrent=" + isCurrent + ", companyName=" + companyName
				+ ", typeOfCompany=" + typeOfCompany + ", designation="
				+ designation + ", jobProfile=" + jobProfile + ", noOfYears="
				+ noOfYears + ", grossIncome=" + grossIncome
				+ ", contactPersonName=" + contactPersonName
				+ ", contactPersonNumber=" + contactPersonNumber
				+ ", saveAsDraft=" + saveAsDraft + ", makerId=" + makerId
				+ ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress + "]";
	}
}
