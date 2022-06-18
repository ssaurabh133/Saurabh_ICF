//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.legal.vo;

import java.io.Serializable;

public class LawFirmMasterVo implements Serializable{
	
	private String lawFirmCode;
	private String lawFirmDesc;
	private String branchId;
	private String branchDesc;
	private String lbxBranchIds;
	private String address1;
	private String address2;
	private String countryCode;
	private String countryDesc;
	
	private String country;
	private String txtCountryCode;
	private String state;
	private String txtStateCode;
	private String dist;
	private String txtDistCode;
	
	
	
	private String stateCode;
	private String stateDesc;
	private String cityCode;
	private String cityDesc;
	private String zipCode;
	private String phone1;
	private String phone2;
	private String panNo;
	private String sapCode;
	private String bankAccNo;
	private String mobileNo;
	private String emailId;
	private String dateOfAppointment;
	private String constitutionOfFirm;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private	String autherId;
	private String autherDate;
	private int currentPageLink;
	private int totalRecordSize;
	
	
	public String getLawFirmCode() {
		return lawFirmCode;
	}
	public void setLawFirmCode(String lawFirmCode) {
		this.lawFirmCode = lawFirmCode;
	}
	public String getLawFirmDesc() {
		return lawFirmDesc;
	}
	public void setLawFirmDesc(String lawFirmDesc) {
		this.lawFirmDesc = lawFirmDesc;
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
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getSapCode() {
		return sapCode;
	}
	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
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
	public String getDateOfAppointment() {
		return dateOfAppointment;
	}
	public void setDateOfAppointment(String dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
	}
	public String getConstitutionOfFirm() {
		return constitutionOfFirm;
	}
	public void setConstitutionOfFirm(String constitutionOfFirm) {
		this.constitutionOfFirm = constitutionOfFirm;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
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
	public String getAutherId() {
		return autherId;
	}
	public void setAutherId(String autherId) {
		this.autherId = autherId;
	}
	public String getAutherDate() {
		return autherDate;
	}
	public void setAutherDate(String autherDate) {
		this.autherDate = autherDate;
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
	
	public String getLbxBranchIds() {
		return lbxBranchIds;
	}
	public void setLbxBranchIds(String lbxBranchIds) {
		this.lbxBranchIds = lbxBranchIds;
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
	
	
	
	
}
