package com.cp.vo;

import java.io.Serializable;

public class LinkCustomerVo implements Serializable{
	
	
	private String customerId;
	private String customerName;
	private String panNo;
	private String registrationNo;
//	private String serviceTaxNo;
	private String status;
	private String custType;
	private String constitution;
	private String businessSegment;
	private String lbxcustomerId;
	private String loanNO;
	private String dealNO;
	private String fatherHusband;
	private String birthDate;
	private String moblieNo;
	private String telephoneNo;
	private String drivingLicense;
	private String vehicleNo;
	private String aadhaar;
	
	public String getFatherHusband() {
		return fatherHusband;
	}
	public void setFatherHusband(String fatherHusband) {
		this.fatherHusband = fatherHusband;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getMoblieNo() {
		return moblieNo;
	}
	public void setMoblieNo(String moblieNo) {
		this.moblieNo = moblieNo;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getDrivingLicense() {
		return drivingLicense;
	}
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getLoanNO() {
		return loanNO;
	}
	public void setLoanNO(String loanNO) {
		this.loanNO = loanNO;
	}
	public String getLoanID() {
		return loanID;
	}
	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}
	public String getAppFormNo() {
		return appFormNo;
	}
	public void setAppFormNo(String appFormNo) {
		this.appFormNo = appFormNo;
	}
	private String loanID;
	private String appFormNo;	
	
	
	public String getLbxcustomerId() {
		return lbxcustomerId;
	}
	public void setLbxcustomerId(String lbxcustomerId) {
		this.lbxcustomerId = lbxcustomerId;
	}
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	public String getBusinessSegment() {
		return businessSegment;
	}
	public void setBusinessSegment(String businessSegment) {
		this.businessSegment = businessSegment;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setDealNO(String dealNO) {
		this.dealNO = dealNO;
	}
	public String getDealNO() {
		return dealNO;
	}
	public String getAadhaar() {
	    return this.aadhaar;
	  }
	  public void setAadhaar(String aadhaar) {
	    this.aadhaar = aadhaar;
	  }


}
