package com.webservice.cp.cibilvo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CibilAddress {

	private String addressType;
	
	private Integer addressResidenceCode;
	
	private String address;
	
	private String addressCity;
	
	private Integer addressPin;
	
	private String addressState;

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public Integer getAddressResidenceCode() {
		return addressResidenceCode;
	}

	public void setAddressResidenceCode(Integer addressResidenceCode) {
		this.addressResidenceCode = addressResidenceCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public Integer getAddressPin() {
		return addressPin;
	}

	public void setAddressPin(Integer addressPin) {
		this.addressPin = addressPin;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	@Override
	public String toString() {
		return "Address [addressType=" + addressType
				+ ", addressResidenceCode=" + addressResidenceCode
				+ ", address=" + address + ", addressCity=" + addressCity
				+ ", addressPin=" + addressPin + ", addressState="
				+ addressState + "]";
	}

}
