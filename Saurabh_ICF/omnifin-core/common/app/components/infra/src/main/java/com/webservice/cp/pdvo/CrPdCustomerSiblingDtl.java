package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdCustomerSiblingDtl {
	
	private Integer pdCustomerSiblingId;
	
	private Integer pdCustomerId;
	
	private String type;
	
	private String name;
	
	private String maritalStatus;
	
	private String passedAway;
	
	private String sourceOfIncome;
	
	private BigDecimal income;
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;
	
	private Integer noOfSibling;

	public Integer getNoOfSibling() {
		return noOfSibling;
	}

	public void setNoOfSibling(Integer noOfSibling) {
		this.noOfSibling = noOfSibling;
	}

	public Integer getPdCustomerSiblingId() {
		return pdCustomerSiblingId;
	}

	public void setPdCustomerSiblingId(Integer pdCustomerSiblingId) {
		this.pdCustomerSiblingId = pdCustomerSiblingId;
	}

	public Integer getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(Integer pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPassedAway() {
		return passedAway;
	}

	public void setPassedAway(String passedAway) {
		this.passedAway = passedAway;
	}

	public String getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
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
		return "CrPdCustomerSiblingDtl [pdCustomerSiblingId="
				+ pdCustomerSiblingId + ", pdCustomerId=" + pdCustomerId
				+ ", type=" + type + ", name=" + name + ", maritalStatus="
				+ maritalStatus + ", passedAway=" + passedAway
				+ ", sourceOfIncome=" + sourceOfIncome + ", income=" + income
				+ ", makerId=" + makerId + ", makerDate=" + makerDate
				+ ", operationDate=" + operationDate + ", longitude="
				+ longitude + ", latitude=" + latitude + ", locationAddress="
				+ locationAddress + ", noOfSibling=" + noOfSibling + "]";
	}
}
