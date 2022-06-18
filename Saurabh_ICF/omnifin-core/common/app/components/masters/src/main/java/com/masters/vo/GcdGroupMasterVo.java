package com.masters.vo;

import java.io.Serializable;

public class GcdGroupMasterVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupDescription;
	private String groupExposureLimit;
	private String recStatus;
	private String makerId;
	private String makerDate;
	private String gcdgroupId;
	private String groupSearchDescription;
	private String gcdgroupIdModify;
	private int currentPageLink;
	private int totalRecordSize;
	private String customerId;
	private String customerName;
	private String groupType;	
	private String groupNameText;
	private String hGroupId;
	private String dealId;
	private String currentExposureLimit;
	private String applicantCat;
	private String loanBalansePrincipal;
	private String loanOverduePrincipal;
	private String sdAdviceAmount;
	private String sdCharge;
	private String exposureAmt;

	
	
	public String getLoanBalansePrincipal() {
		return loanBalansePrincipal;
	}
	public void setLoanBalansePrincipal(String loanBalansePrincipal) {
		this.loanBalansePrincipal = loanBalansePrincipal;
	}
	public String getLoanOverduePrincipal() {
		return loanOverduePrincipal;
	}
	public void setLoanOverduePrincipal(String loanOverduePrincipal) {
		this.loanOverduePrincipal = loanOverduePrincipal;
	}
	public String getSdAdviceAmount() {
		return sdAdviceAmount;
	}
	public void setSdAdviceAmount(String sdAdviceAmount) {
		this.sdAdviceAmount = sdAdviceAmount;
	}
	public String getSdCharge() {
		return sdCharge;
	}
	public void setSdCharge(String sdCharge) {
		this.sdCharge = sdCharge;
	}
	public String getExposureAmt() {
		return exposureAmt;
	}
	public void setExposureAmt(String exposureAmt) {
		this.exposureAmt = exposureAmt;
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
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	public String getGroupExposureLimit() {
		return groupExposureLimit;
	}
	public void setGroupExposureLimit(String groupExposureLimit) {
		this.groupExposureLimit = groupExposureLimit;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public void setGcdgroupId(String gcdgroupId) {
		this.gcdgroupId = gcdgroupId;
	}
	public String getGcdgroupId() {
		return gcdgroupId;
	}
	public void setGroupSearchDescription(String groupSearchDescription) {
		this.groupSearchDescription = groupSearchDescription;
	}
	public String getGroupSearchDescription() {
		return groupSearchDescription;
	}
	public void setGcdgroupIdModify(String gcdgroupIdModify) {
		this.gcdgroupIdModify = gcdgroupIdModify;
	}
	public String getGcdgroupIdModify() {
		return gcdgroupIdModify;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupNameText(String groupNameText) {
		this.groupNameText = groupNameText;
	}
	public String getGroupNameText() {
		return groupNameText;
	}
	public void sethGroupId(String hGroupId) {
		this.hGroupId = hGroupId;
	}
	public String gethGroupId() {
		return hGroupId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getDealId() {
		return dealId;
	}
	public void setCurrentExposureLimit(String currentExposureLimit) {
		this.currentExposureLimit = currentExposureLimit;
	}
	public String getCurrentExposureLimit() {
		return currentExposureLimit;
	}
	public void setApplicantCat(String applicantCat) {
		this.applicantCat = applicantCat;
	}
	public String getApplicantCat() {
		return applicantCat;
	}
	
	
}
