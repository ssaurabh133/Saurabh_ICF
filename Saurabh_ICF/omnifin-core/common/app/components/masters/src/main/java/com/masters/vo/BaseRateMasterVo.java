//Author Name : Ritu Jindal-->
//Date of Creation : 10 May 2011-->
//Purpose  : VO for Base Rate Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class BaseRateMasterVo implements Serializable{
	
	private String baseRateDesc;
	private String baseRateType;
	private String effectiveFromDate;
	private String baseRate;
	private String baseRateStatus;
	private String makerId;
	private String makerDate;
	private String baseRateTypeSearch;
	private String effectiveFromDateSearch;
	private String baseRateDescSearch;
	private String baseRateTypeModify;
	private String effectiveFromDateEdit;
	private String bRTypeCode;
	
	
	public String getbRTypeCode() {
		return bRTypeCode;
	}
	public void setbRTypeCode(String bRTypeCode) {
		this.bRTypeCode = bRTypeCode;
	}
	public String getbRTypeDesc() {
		return bRTypeDesc;
	}
	public void setbRTypeDesc(String bRTypeDesc) {
		this.bRTypeDesc = bRTypeDesc;
	}
	private String bRTypeDesc;
	private int currentPageLink;
	private int totalRecordSize;
	
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
	public String getBaseRateTypeSearch() {
		return baseRateTypeSearch;
	}
	public void setBaseRateTypeSearch(String baseRateTypeSearch) {
		this.baseRateTypeSearch = baseRateTypeSearch;
	}
	public String getEffectiveFromDateSearch() {
		return effectiveFromDateSearch;
	}
	public void setEffectiveFromDateSearch(String effectiveFromDateSearch) {
		this.effectiveFromDateSearch = effectiveFromDateSearch;
	}
	public String getBaseRateDescSearch() {
		return baseRateDescSearch;
	}
	public void setBaseRateDescSearch(String baseRateDescSearch) {
		this.baseRateDescSearch = baseRateDescSearch;
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
	public void setBaseRateDesc(String baseRateDesc) {
		this.baseRateDesc = baseRateDesc;
	}
	public String getBaseRateDesc() {
		return baseRateDesc;
	}
	public void setBaseRateType(String baseRateType) {
		this.baseRateType = baseRateType;
	}
	public String getBaseRateType() {
		return baseRateType;
	}
	public void setEffectiveFromDate(String effectiveFromDate) {
		this.effectiveFromDate = effectiveFromDate;
	}
	public String getEffectiveFromDate() {
		return effectiveFromDate;
	}
	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}
	public String getBaseRate() {
		return baseRate;
	}
	public void setBaseRateStatus(String baseRateStatus) {
		this.baseRateStatus = baseRateStatus;
	}
	public String getBaseRateStatus() {
		return baseRateStatus;
	}
	public void setBaseRateTypeModify(String baseRateTypeModify) {
		this.baseRateTypeModify = baseRateTypeModify;
	}
	public String getBaseRateTypeModify() {
		return baseRateTypeModify;
	}
	public void setEffectiveFromDateEdit(String effectiveFromDateEdit) {
		this.effectiveFromDateEdit = effectiveFromDateEdit;
	}
	public String getEffectiveFromDateEdit() {
		return effectiveFromDateEdit;
	}

}
