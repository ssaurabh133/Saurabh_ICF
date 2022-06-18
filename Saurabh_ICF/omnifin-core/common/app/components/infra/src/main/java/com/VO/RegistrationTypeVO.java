package com.VO;

import java.io.Serializable;

public class RegistrationTypeVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String registrationTypeCode;
	private String registrationTypeDesc;
	private String businessSegmentCode;
	private String businessSegmentDesc;
	private String natureOfBusDesc;
	private String natureOfBusCode;
	
	public String getBusinessSegmentCode() {
		return businessSegmentCode;
	}
	public void setBusinessSegmentCode(String businessSegmentCode) {
		this.businessSegmentCode = businessSegmentCode;
	}
	public String getBusinessSegmentDesc() {
		return businessSegmentDesc;
	}
	public void setBusinessSegmentDesc(String businessSegmentDesc) {
		this.businessSegmentDesc = businessSegmentDesc;
	}
	public String getRegistrationTypeCode() {
		return registrationTypeCode;
	}
	public void setRegistrationTypeCode(String registrationTypeCode) {
		this.registrationTypeCode = registrationTypeCode;
	}
	public String getRegistrationTypeDesc() {
		return registrationTypeDesc;
	}
	public void setRegistrationTypeDesc(String registrationTypeDesc) {
		this.registrationTypeDesc = registrationTypeDesc;
	}
	public String getNatureOfBusDesc() {
		return natureOfBusDesc;
	}
	public void setNatureOfBusDesc(String natureOfBusDesc) {
		this.natureOfBusDesc = natureOfBusDesc;
	}
	public String getNatureOfBusCode() {
		return natureOfBusCode;
	}
	public void setNatureOfBusCode(String natureOfBusCode) {
		this.natureOfBusCode = natureOfBusCode;
	}
}
