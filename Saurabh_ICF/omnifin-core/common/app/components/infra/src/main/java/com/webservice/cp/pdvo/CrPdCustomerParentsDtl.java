package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdCustomerParentsDtl {
	
	private Integer pdCustomerParentsId;
	
	private Integer pdCustomerId;
	
	private String type;
	
	private String name;
	
	private Integer age;
	
	private String passedAway;
	
	private String dob;
	
	private String sourceOfIncome;
	
	private BigDecimal income;
	
	private String occupation;
	
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;

	public Integer getPdCustomerParentsId() {
		return pdCustomerParentsId;
	}

	public void setPdCustomerParentsId(Integer pdCustomerParentsId) {
		this.pdCustomerParentsId = pdCustomerParentsId;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPassedAway() {
		return passedAway;
	}

	public void setPassedAway(String passedAway) {
		this.passedAway = passedAway;
	}

	public String getDob() {
		return dob;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setDob(String dob) {
		this.dob = dob;
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
		return "CrPdCustomerParentsDtl [pdCustomerParentsId="
				+ pdCustomerParentsId + ", pdCustomerId=" + pdCustomerId
				+ ", type=" + type + ", name=" + name + ", age=" + age
				+ ", passedAway=" + passedAway + ", dob=" + dob
				+ ", sourceOfIncome=" + sourceOfIncome + ", income=" + income
				+ ", occupation=" + occupation + ", makerId=" + makerId
				+ ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress + "]";
	}
}
