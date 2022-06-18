package com.webservice.cp.pdvo;

public class PdFetchRequest {
	
	private UserCredentials userCredentials;
	private String dealNo;
	private String panNo;
	private Integer dealId;
	private Integer pdId;
	private String mobileNumber;
	private String applicationFormNo;
	private String aadharNo;
	private Integer customerId;
	private String customerName;
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public Integer getDealId() {
		return dealId;
	}
	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}
	public Integer getPdId() {
		return pdId;
	}
	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getApplicationFormNo() {
		return applicationFormNo;
	}
	public void setApplicationFormNo(String applicationFormNo) {
		this.applicationFormNo = applicationFormNo;
	}
	public String getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	@Override
	public String toString() {
		return "PdFetchRequest [userCredentials=" + userCredentials
				+ ", dealNo=" + dealNo + ", panNo=" + panNo + ", dealId="
				+ dealId + ", pdId=" + pdId + ", mobileNumber=" + mobileNumber
				+ ", applicationFormNo=" + applicationFormNo + ", aadharNo="
				+ aadharNo + ", customerId=" + customerId + ", customerName="
				+ customerName + "]";
	}
}
