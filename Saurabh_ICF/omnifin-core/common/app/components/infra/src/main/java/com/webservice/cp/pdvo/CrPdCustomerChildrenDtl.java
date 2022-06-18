package com.webservice.cp.pdvo;

import java.math.BigDecimal;
public class CrPdCustomerChildrenDtl {
	
	private Integer pdCustomerChildrenId;
	
	private Integer pdCustomerId;
	
	private String type;
	
	private String name;
	
	private String dob;
	
	private Integer age;
	
	private String instituteEmployer;
	
	private BigDecimal fee;
	
	private BigDecimal income;
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;
	
	private Integer numberOfChildren;

	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public Integer getPdCustomerChildrenId() {
		return pdCustomerChildrenId;
	}

	public void setPdCustomerChildrenId(Integer pdCustomerChildrenId) {
		this.pdCustomerChildrenId = pdCustomerChildrenId;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getInstituteEmployer() {
		return instituteEmployer;
	}

	public void setInstituteEmployer(String instituteEmployer) {
		this.instituteEmployer = instituteEmployer;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
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
		return "CrPdCustomerChildrenDtl [pdCustomerChildrenId="
				+ pdCustomerChildrenId + ", pdCustomerId=" + pdCustomerId
				+ ", type=" + type + ", name=" + name + ", dob=" + dob
				+ ", age=" + age + ", instituteEmployer=" + instituteEmployer
				+ ", fee=" + fee + ", income=" + income + ", makerId="
				+ makerId + ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress
				+ ", numberOfChildren=" + numberOfChildren + "]";
	}

}
