package com.payout.vo;

import java.io.Serializable;

public class TaxMasterVO implements Serializable{
	
 private String taxId;
 private String taxName;
 private String activityCode;
 private String taxPer;
 private String stateId;
 private String stateName;
 private String recStatus;
 private String makerId;
 private String makerDate;
 private int totalRecordSize;
 private int currentPageLink;
 private String activityDesc;
 private String searchTaxCode;
 private String searchTaxPer;
 private String searchStateId;
 private String searchActivityCode;
 private String lbxActivityCode;
 private String txtStateCode;
 
 
 
public String getLbxActivityCode() {
	return lbxActivityCode;
}
public void setLbxActivityCode(String lbxActivityCode) {
	this.lbxActivityCode = lbxActivityCode;
}
public String getTxtStateCode() {
	return txtStateCode;
}
public void setTxtStateCode(String txtStateCode) {
	this.txtStateCode = txtStateCode;
}
public String getActivityDesc() {
	return activityDesc;
}
public void setActivityDesc(String activityDesc) {
	this.activityDesc = activityDesc;
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
public int getTotalRecordSize() {
	return totalRecordSize;
}
public void setTotalRecordSize(int totalRecordSize) {
	this.totalRecordSize = totalRecordSize;
}
public int getCurrentPageLink() {
	return currentPageLink;
}
public void setCurrentPageLink(int currentPageLink) {
	this.currentPageLink = currentPageLink;
}
public String getTaxId() {
	return taxId;
}
public void setTaxId(String taxId) {
	this.taxId = taxId;
}
public String getTaxName() {
	return taxName;
}
public void setTaxName(String taxName) {
	this.taxName = taxName;
}
public String getActivityCode() {
	return activityCode;
}
public void setActivityCode(String activityCode) {
	this.activityCode = activityCode;
}
public String getTaxPer() {
	return taxPer;
}
public void setTaxPer(String taxPer) {
	this.taxPer = taxPer;
}
public String getStateId() {
	return stateId;
}
public void setStateId(String stateId) {
	this.stateId = stateId;
}
public String getStateName() {
	return stateName;
}
public void setStateName(String stateName) {
	this.stateName = stateName;
}
public String getRecStatus() {
	return recStatus;
}
public void setRecStatus(String recStatus) {
	this.recStatus = recStatus;
}
public String getSearchTaxCode() {
	return searchTaxCode;
}
public void setSearchTaxCode(String searchTaxCode) {
	this.searchTaxCode = searchTaxCode;
}
public String getSearchTaxPer() {
	return searchTaxPer;
}
public void setSearchTaxPer(String searchTaxPer) {
	this.searchTaxPer = searchTaxPer;
}
public String getSearchStateId() {
	return searchStateId;
}
public void setSearchStateId(String searchStateId) {
	this.searchStateId = searchStateId;
}
public String getSearchActivityCode() {
	return searchActivityCode;
}
public void setSearchActivityCode(String searchActivityCode) {
	this.searchActivityCode = searchActivityCode;
}
	
 
}
