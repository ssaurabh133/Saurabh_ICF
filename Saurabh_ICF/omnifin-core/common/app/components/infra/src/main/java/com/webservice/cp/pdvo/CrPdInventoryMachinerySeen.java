package com.webservice.cp.pdvo;


public class CrPdInventoryMachinerySeen {
	
	private Integer pdInventoryMachineryId;
	
	private Integer pdCustomerId;
	
	private String type;
	
	private String item;
	
	private String noOfUnit;
	
	private String estimatedValue;
	
	private String totalValue;
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;

	public Integer getPdInventoryMachineryId() {
		return pdInventoryMachineryId;
	}

	public void setPdInventoryMachineryId(Integer pdInventoryMachineryId) {
		this.pdInventoryMachineryId = pdInventoryMachineryId;
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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getNoOfUnit() {
		return noOfUnit;
	}

	public void setNoOfUnit(String noOfUnit) {
		this.noOfUnit = noOfUnit;
	}

	public String getEstimatedValue() {
		return estimatedValue;
	}

	public void setEstimatedValue(String estimatedValue) {
		this.estimatedValue = estimatedValue;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
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
		return "CrPdInventoryMachinerySeen [pdInventoryMachineryId="
				+ pdInventoryMachineryId + ", pdCustomerId=" + pdCustomerId
				+ ", type=" + type + ", item=" + item + ", noOfUnit="
				+ noOfUnit + ", estimatedValue=" + estimatedValue
				+ ", totalValue=" + totalValue + ", makerId=" + makerId
				+ ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress + "]";
	}
}
