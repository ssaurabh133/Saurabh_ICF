package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdCustomerAssetDtl {
	
	private Integer pdCustomerAssetId;
	
	private Integer pdCustomerId;
	
	private Integer noOfUnit;
	
	private String assetType;
	
	private String ownerName;
	
	private String place;
	
	private BigDecimal estimatedvalue;
	
	private String propertyType;
	
	private String vehicleDesc;
	
	private String saveAsDraft;
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;
	

	public void setNoOfUnit(Integer noOfUnit) {
		this.noOfUnit = noOfUnit;
	}

	public Integer getPdCustomerAssetId() {
		return pdCustomerAssetId;
	}

	public void setPdCustomerAssetId(Integer pdCustomerAssetId) {
		this.pdCustomerAssetId = pdCustomerAssetId;
	}

	public Integer getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(Integer pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public BigDecimal getEstimatedvalue() {
		return estimatedvalue;
	}

	public void setEstimatedvalue(BigDecimal estimatedvalue) {
		this.estimatedvalue = estimatedvalue;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getVehicleDesc() {
		return vehicleDesc;
	}

	public void setVehicleDesc(String vehicleDesc) {
		this.vehicleDesc = vehicleDesc;
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

	public Integer getNoOfUnit() {
		return noOfUnit;
	}

	@Override
	public String toString() {
		return "CrPdCustomerAssetDtl [pdCustomerAssetId=" + pdCustomerAssetId
				+ ", pdCustomerId=" + pdCustomerId + ", noOfUnit=" + noOfUnit
				+ ", assetType=" + assetType + ", ownerName=" + ownerName
				+ ", place=" + place + ", estimatedvalue=" + estimatedvalue
				+ ", propertyType=" + propertyType + ", vehicleDesc="
				+ vehicleDesc + ", saveAsDraft=" + saveAsDraft + ", makerId="
				+ makerId + ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress + "]";
	}
}
