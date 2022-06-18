package com.payout.vo;

import java.io.Serializable;

public class ActivityMasterVO implements Serializable {
	private String activityCode;
	private String activityDesc;
	private String recStatus;
	private String searchActivityCode;
	private String searchActivityDesc;
	private String makerId;
	private String makerDate;
	private int totalRecordSize;
	private int currentPageLink;
	private String activityCodeModify;
	private String sourceId;
	private String sourcedesc;
	private String mappingCode;
	
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourcedesc() {
		return sourcedesc;
	}
	public void setSourcedesc(String sourcedesc) {
		this.sourcedesc = sourcedesc;
	}
	public String getMappingCode() {
		return mappingCode;
	}
	public void setMappingCode(String mappingCode) {
		this.mappingCode = mappingCode;
	}
	public String getActivityCodeModify() {
		return activityCodeModify;
	}
	public void setActivityCodeModify(String activityCodeModify) {
		this.activityCodeModify = activityCodeModify;
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
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getSearchActivityCode() {
		return searchActivityCode;
	}
	public void setSearchActivityCode(String searchActivityCode) {
		this.searchActivityCode = searchActivityCode;
	}
	public String getSearchActivityDesc() {
		return searchActivityDesc;
	}
	public void setSearchActivityDesc(String searchActivityDesc) {
		this.searchActivityDesc = searchActivityDesc;
	}

}
