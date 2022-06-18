package com.cp.vo;

public class ExistingAssetVo {
	private String openType;
	private String dealNo;
	private String lbxDealNo;
	private String lbxLoanNoHID;
	private String customerName;
	private String lbxCollateralId;
	private String assetType;
	private String assetClass;
	private String assetDesc;
	private String assetValue;
	private String userId;
	private String makerDate;
	private String assetId;
	private String applicantCustId;
	
	
	
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getAssetValue() {
		return assetValue;
	}
	public void setAssetValue(String assetValue) {
		this.assetValue = assetValue;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}
	public String getAssetDesc() {
		return assetDesc;
	}
	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLbxCollateralId() {
		return lbxCollateralId;
	}
	public void setLbxCollateralId(String lbxCollateralId) {
		this.lbxCollateralId = lbxCollateralId;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}
	public String getLbxDealNo() {
		return lbxDealNo;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	public String getOpenType() {
		return openType;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getAssetId() {
		return assetId;
	}
	
	public void setApplicantCustId(String applicantCustId) {
		this.applicantCustId = applicantCustId;
	}
	public String getApplicantCustId() {
		return applicantCustId;
	}
	
	
}
