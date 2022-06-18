//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.legal.vo;

import java.io.Serializable;

public class AdvocateMasterVo implements Serializable{
	
	private	String	advocateCode;
	private	String	lbxadvocateCode;
	public String getLbxadvocateCode() {
		return lbxadvocateCode;
	}
	public void setLbxadvocateCode(String lbxadvocateCode) {
		this.lbxadvocateCode = lbxadvocateCode;
	}
	private	String	advocateDesc;
	private	String	advocateTypeFlag;
	private	String	lbxUserId;
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	private	String	lawFirmCode;
	private	String	address1;
	private	String	address2;
	private	String	countryCode;
	private	String	countryDesc;
	private	String	country;
	private	String	txtCountryCode;
	private	String	stateCode;
	private	String	stateDesc;
	private	String	state;
	private	String	txtStateCode;
	private	String	cityCode;
	private	String	cityDesc;
	private	String	dist;
	private	String	txtDistCode;
	private	String	zipCode;
	private	String	phone1;
	private	String	phone2;
	private	String	mobileNo;
	private	String	emailId;
	private	String	panNo;
	private	String	bankAccNo;
	private	String	dateOfAppointment;
	private	String	membershipLicNo;
	private	String	tenureOfRetainership;
	private	String	regularRetainerFlag;
	private	String	retainershipValidUpto;
	private	String	recStatus;
	private	String	lbxBranchIds;
	private	String	branchId;
	private	String	branchDesc;
	private	String	lbxCaseTypeCodes;
	private	String	caseTypeCode;
	private	String	caseTypeDesc;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String lbxlawFirmCode;
	private String sapCode;
	private String branch;
	private String lbxBranchId;
	private String classification;

	
	
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSapCode() {
		return sapCode;
	}
	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}
	public String getLbxlawFirmCode() {
		return lbxlawFirmCode;
	}
	public void setLbxlawFirmCode(String lbxlawFirmCode) {
		this.lbxlawFirmCode = lbxlawFirmCode;
	}
	public String getAdvocateCode() {
		return advocateCode;
	}
	public void setAdvocateCode(String advocateCode) {
		this.advocateCode = advocateCode;
	}
	public String getAdvocateDesc() {
		return advocateDesc;
	}
	public void setAdvocateDesc(String advocateDesc) {
		this.advocateDesc = advocateDesc;
	}
	public String getAdvocateTypeFlag() {
		return advocateTypeFlag;
	}
	public void setAdvocateTypeFlag(String advocateTypeFlag) {
		this.advocateTypeFlag = advocateTypeFlag;
	}
	public String getLawFirmCode() {
		return lawFirmCode;
	}
	public void setLawFirmCode(String lawFirmCode) {
		this.lawFirmCode = lawFirmCode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryDesc() {
		return countryDesc;
	}
	public void setCountryDesc(String countryDesc) {
		this.countryDesc = countryDesc;
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
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
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
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityDesc() {
		return cityDesc;
	}
	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getDateOfAppointment() {
		return dateOfAppointment;
	}
	public void setDateOfAppointment(String dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
	}
	public String getMembershipLicNo() {
		return membershipLicNo;
	}
	public void setMembershipLicNo(String membershipLicNo) {
		this.membershipLicNo = membershipLicNo;
	}
	public String getTenureOfRetainership() {
		return tenureOfRetainership;
	}
	public void setTenureOfRetainership(String tenureOfRetainership) {
		this.tenureOfRetainership = tenureOfRetainership;
	}
	public String getRegularRetainerFlag() {
		return regularRetainerFlag;
	}
	public void setRegularRetainerFlag(String regularRetainerFlag) {
		this.regularRetainerFlag = regularRetainerFlag;
	}
	public String getRetainershipValidUpto() {
		return retainershipValidUpto;
	}
	public void setRetainershipValidUpto(String retainershipValidUpto) {
		this.retainershipValidUpto = retainershipValidUpto;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getLbxBranchIds() {
		return lbxBranchIds;
	}
	public void setLbxBranchIds(String lbxBranchIds) {
		this.lbxBranchIds = lbxBranchIds;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchDesc() {
		return branchDesc;
	}
	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}
	public String getLbxCaseTypeCodes() {
		return lbxCaseTypeCodes;
	}
	public void setLbxCaseTypeCodes(String lbxCaseTypeCodes) {
		this.lbxCaseTypeCodes = lbxCaseTypeCodes;
	}
	public String getCaseTypeCode() {
		return caseTypeCode;
	}
	public void setCaseTypeCode(String caseTypeCode) {
		this.caseTypeCode = caseTypeCode;
	}
	public String getCaseTypeDesc() {
		return caseTypeDesc;
	}
	public void setCaseTypeDesc(String caseTypeDesc) {
		this.caseTypeDesc = caseTypeDesc;
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
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	

	
	
	
	
}
