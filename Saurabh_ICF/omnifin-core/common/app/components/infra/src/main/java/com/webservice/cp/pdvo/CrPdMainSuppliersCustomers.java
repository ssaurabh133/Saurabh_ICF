package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdMainSuppliersCustomers {
	
	private Integer pdSupplierId;
	
	private Integer pdCustomerId;
	
	private String type;
	
	private String companyName;
	
	private String primaryBusiness;
	
	private String address;
	
	private String contact;
	
	private BigDecimal avgMonthlyPurchase;
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;

	public Integer getPdSupplierId() {
		return pdSupplierId;
	}

	public void setPdSupplierId(Integer pdSupplierId) {
		this.pdSupplierId = pdSupplierId;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPrimaryBusiness() {
		return primaryBusiness;
	}

	public void setPrimaryBusiness(String primaryBusiness) {
		this.primaryBusiness = primaryBusiness;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public BigDecimal getAvgMonthlyPurchase() {
		return avgMonthlyPurchase;
	}

	public void setAvgMonthlyPurchase(BigDecimal avgMonthlyPurchase) {
		this.avgMonthlyPurchase = avgMonthlyPurchase;
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
		return "CrPdMainSuppliersCustomers [pdSupplierId=" + pdSupplierId
				+ ", pdCustomerId=" + pdCustomerId + ", type=" + type
				+ ", companyName=" + companyName + ", primaryBusiness="
				+ primaryBusiness + ", address=" + address + ", contact="
				+ contact + ", avgMonthlyPurchase=" + avgMonthlyPurchase
				+ ", makerId=" + makerId + ", makerDate=" + makerDate
				+ ", operationDate=" + operationDate + ", longitude="
				+ longitude + ", latitude=" + latitude + ", locationAddress="
				+ locationAddress + "]";
	}
}
