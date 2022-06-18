//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : VO for Agency Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class DealerMasterVo implements Serializable{

	
	private String dealerId;
	private String dealerDes;
	private String dealerType;
	private String dealerStatus;
	private String lbxdealerType;
	private String makerId;
	private String makerDate;
	private String dealerSearchDes;
	private String dealerIdModify;
	private int currentPageLink;
	private int totalRecordSize;
	//Ritu Code
	private String userId;
	private String userDesc;
	//Start Surendra Code
	private String accountNo;
	private String bankCode;
	private String lbxBankID;
	private String bankBranchName;
	private String lbxBranchID;
	private String 	micrCode;
	private String 	ifscCode;
	private String dealerSearchCode;
	//End Surendra Code
	//sachin start
	private String addressDealer1;
	private String addressDealer2;
	private String addressDealer3;
	private String country;
	private String txtCountryCode;
	private String state;
	private String txtStateCode;
	private String dist;
	private String txtDistCode;
	private String pincode;
	private String contractPerson;
	private String phoneOff;
	private String phoneRes;
	private String email;
	private String registrationNo;
	private String empanelStatus;
	
	// Kumar Aman Changes Started
	private String pan;
	// Kumar Aman Changes Ended

	
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getEmpanelStatus() {
		return empanelStatus;
	}
	public void setEmpanelStatus(String empanelStatus) {
		this.empanelStatus = empanelStatus;
	}
	public String getAddressDealer1() {
		return addressDealer1;
	}
	public void setAddressDealer1(String addressDealer1) {
		this.addressDealer1 = addressDealer1;
	}
	public String getAddressDealer2() {
		return addressDealer2;
	}
	public void setAddressDealer2(String addressDealer2) {
		this.addressDealer2 = addressDealer2;
	}
	public String getAddressDealer3() {
		return addressDealer3;
	}
	public void setAddressDealer3(String addressDealer3) {
		this.addressDealer3 = addressDealer3;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTxtCountryCode() {
		return txtCountryCode;
	}
	public void setTxtCountryCode(String txtCountryCode) {
		this.txtCountryCode = txtCountryCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTxtStateCode() {
		return txtStateCode;
	}
	public void setTxtStateCode(String txtStateCode) {
		this.txtStateCode = txtStateCode;
	}
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}
	public String getTxtDistCode() {
		return txtDistCode;
	}
	public void setTxtDistCode(String txtDistCode) {
		this.txtDistCode = txtDistCode;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getContractPerson() {
		return contractPerson;
	}
	public void setContractPerson(String contractPerson) {
		this.contractPerson = contractPerson;
	}
	public String getPhoneOff() {
		return phoneOff;
	}
	public void setPhoneOff(String phoneOff) {
		this.phoneOff = phoneOff;
	}
	public String getPhoneRes() {
		return phoneRes;
	}
	public void setPhoneRes(String phoneRes) {
		this.phoneRes = phoneRes;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	//end by sachin
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	
	public String getMakerDate() {
		return makerDate;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerDes(String dealerDes) {
		this.dealerDes = dealerDes;
	}
	public String getDealerDes() {
		return dealerDes;
	}
	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}
	public String getDealerType() {
		return dealerType;
	}
	public void setDealerStatus(String dealerStatus) {
		this.dealerStatus = dealerStatus;
	}
	public String getDealerStatus() {
		return dealerStatus;
	}
	public void setLbxdealerType(String lbxdealerType) {
		this.lbxdealerType = lbxdealerType;
	}
	public String getLbxdealerType() {
		return lbxdealerType;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setDealerSearchDes(String dealerSearchDes) {
		this.dealerSearchDes = dealerSearchDes;
	}
	public String getDealerSearchDes() {
		return dealerSearchDes;
	}
	public void setDealerIdModify(String dealerIdModify) {
		this.dealerIdModify = dealerIdModify;
	}
	public String getDealerIdModify() {
		return dealerIdModify;
	}

	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	
	public String getMicrCode() {
		return micrCode;
	}
	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getDealerSearchCode() {
		return dealerSearchCode;
	}
	public void setDealerSearchCode(String dealerSearchCode) {
		this.dealerSearchCode = dealerSearchCode;
	}
	public String getLbxBankID() {
		return lbxBankID;
	}
	public void setLbxBankID(String lbxBankID) {
		this.lbxBankID = lbxBankID;
	}
	public String getLbxBranchID() {
		return lbxBranchID;
	}
	public void setLbxBranchID(String lbxBranchID) {
		this.lbxBranchID = lbxBranchID;
	}
	
	
	
}
