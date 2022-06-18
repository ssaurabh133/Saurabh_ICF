package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdCustomerInvestmentDtl {
	
	private Integer pdCustomerInvestmentId;
	
	private Integer pdCustomerId;
	
	private String type;
	
	private String description;
	
	private String ownerName;
	
	private BigDecimal value;
	
	private String saveAsDraft;
	
	private String makerId;
	
	private String makerDate;
	
	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;

	public Integer getPdCustomerInvestmentId() {
		return pdCustomerInvestmentId;
	}

	public void setPdCustomerInvestmentId(Integer pdCustomerInvestmentId) {
		this.pdCustomerInvestmentId = pdCustomerInvestmentId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
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
		return "CrPdCustomerInvestmentDtl [pdCustomerInvestmentId="
				+ pdCustomerInvestmentId + ", pdCustomerId=" + pdCustomerId
				+ ", type=" + type + ", description=" + description
				+ ", ownerName=" + ownerName + ", value=" + value
				+ ", saveAsDraft=" + saveAsDraft + ", makerId=" + makerId
				+ ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress + "]";
	}
}
