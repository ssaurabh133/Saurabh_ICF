package com.webservice.cp.cibilvo;

public class CibilPhone {

	private String phoneType;
	
	private String phoneNumber;
	
	private Integer phoneExtn;
	
	private Integer stdCode;

	public String getPhoneType() {
		return phoneType;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getPhoneExtn() {
		return phoneExtn;
	}

	public void setPhoneExtn(Integer phoneExtn) {
		this.phoneExtn = phoneExtn;
	}

	public Integer getStdCode() {
		return stdCode;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public void setStdCode(Integer stdCode) {
		this.stdCode = stdCode;
	}
	@Override
	public String toString() {
		return "CibilPhone [phoneType=" + phoneType + ", phoneNumber="
				+ phoneNumber + ", phoneExtn=" + phoneExtn + ", stdCode="
				+ stdCode + "]";
	}

}
