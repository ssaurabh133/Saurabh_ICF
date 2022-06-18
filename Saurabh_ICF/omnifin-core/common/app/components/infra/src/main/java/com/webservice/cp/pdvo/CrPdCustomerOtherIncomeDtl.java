package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdCustomerOtherIncomeDtl {
	
	private Integer pdCustomerIncomeId;
	
	private Integer pdCustomerId;
	
	private String typeOfIncome;
	
	private String sourceOfIncome;
	
	private BigDecimal monthlyIncome;
	
	private String otherName;
	
	private String relationship;
	
	private String saveAsDraft;
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;
	
	private String otherSourceOfIncome;
	
	public String getOtherSourceOfIncome() {
		return otherSourceOfIncome;
	}

	public void setOtherSourceOfIncome(String otherSourceOfIncome) {
		this.otherSourceOfIncome = otherSourceOfIncome;
	}

	public Integer getPdCustomerIncomeId() {
		return pdCustomerIncomeId;
	}

	public void setPdCustomerIncomeId(Integer pdCustomerIncomeId) {
		this.pdCustomerIncomeId = pdCustomerIncomeId;
	}

	public Integer getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(Integer pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public String getTypeOfIncome() {
		return typeOfIncome;
	}

	public void setTypeOfIncome(String typeOfIncome) {
		this.typeOfIncome = typeOfIncome;
	}

	public String getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
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
		return "CrPdCustomerOtherIncomeDtl [pdCustomerIncomeId="
				+ pdCustomerIncomeId + ", pdCustomerId=" + pdCustomerId
				+ ", typeOfIncome=" + typeOfIncome + ", sourceOfIncome="
				+ sourceOfIncome + ", monthlyIncome=" + monthlyIncome
				+ ", otherName=" + otherName + ", relationship=" + relationship
				+ ", saveAsDraft=" + saveAsDraft + ", makerId=" + makerId
				+ ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress
				+ ", otherSourceOfIncome=" + otherSourceOfIncome + "]";
	}
}
