package com.webservice.cp.pdvo;

import java.math.BigDecimal;


public class CrPdCustomerAddressDtl {
	

	private Integer pdCustomerAddressId;
	
	
	private Integer pdCustomerId;
	
	
	private String addressType;
	private Integer noOfUnit;
	
	private String address;
	
	private String isOwned;
	
	private String ownership;
	
	private Integer stayingSince;
	
	private BigDecimal rent;
	
	private String relationShip;
	
	private String saveAsDraft;
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;

	public Integer getPdCustomerAddressId() {
		return pdCustomerAddressId;
	}

	public void setPdCustomerAddressId(Integer pdCustomerAddressId) {
		this.pdCustomerAddressId = pdCustomerAddressId;
	}

	public Integer getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(Integer pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsOwned() {
		return isOwned;
	}

	public void setIsOwned(String isOwned) {
		this.isOwned = isOwned;
	}

	public Integer getNoOfUnit() {
		return noOfUnit;
	}

	public void setNoOfUnit(Integer noOfUnit) {
		this.noOfUnit = noOfUnit;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public Integer getStayingSince() {
		return stayingSince;
	}

	public void setStayingSince(Integer stayingSince) {
		this.stayingSince = stayingSince;
	}

	public BigDecimal getRent() {
		return rent;
	}

	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
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
		return "CrPdCustomerAddressDtl [pdCustomerAddressId="
				+ pdCustomerAddressId + ", pdCustomerId=" + pdCustomerId
				+ ", addressType=" + addressType + ", noOfUnit=" + noOfUnit
				+ ", address=" + address + ", isOwned=" + isOwned
				+ ", ownership=" + ownership + ", stayingSince=" + stayingSince
				+ ", rent=" + rent + ", relationShip=" + relationShip
				+ ", saveAsDraft=" + saveAsDraft + ", makerId=" + makerId
				+ ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress + "]";
	}
}
