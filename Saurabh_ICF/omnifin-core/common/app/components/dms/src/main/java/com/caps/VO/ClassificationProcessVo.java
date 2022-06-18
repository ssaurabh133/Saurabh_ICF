package com.caps.VO;



public class ClassificationProcessVo  {
	
	String addressType;
	String district;
	String state;
	String country;
	String mobileNo;
	String fax;
	String pinCode;
	int currentPageLink;
	int TotalRecordSize;
	private String checkBoxDis;
	String address1;
	/*For Classification process BOD*/
	private String processName;
	private String lastStartTime;
	private String lastEndStartTime;
	private String status;
	private String procmessage;
	public String getProcmessage() {
		return procmessage;
	}
	public void setProcmessage(String procmessage) {
		this.procmessage = procmessage;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getLastStartTime() {
		return lastStartTime;
	}
	public void setLastStartTime(String lastStartTime) {
		this.lastStartTime = lastStartTime;
	}
	public String getLastEndStartTime() {
		return lastEndStartTime;
	}
	public void setLastEndStartTime(String lastEndStartTime) {
		this.lastEndStartTime = lastEndStartTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

 
	
	
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public int getTotalRecordSize() {
		return TotalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		TotalRecordSize = totalRecordSize;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public void setCheckBoxDis(String checkBoxDis) {
		this.checkBoxDis = checkBoxDis;
	}
	public String getCheckBoxDis() {
		return checkBoxDis;
	}
	

}
