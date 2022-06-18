package com.payout.vo;

import java.io.Serializable;

public class ScheduleMasterVO implements Serializable {
	
private String activityId;
private String startDate;
private String endDate;
private String startMonth;
private String endMonth;
private String recStatus;
private int currentPageLink;
private int totalRecordSize;
private String lbxActivityCode;
private String makerId;
private String searchActivityId;
private String searchStartDate;
private String searchEndDate;
private String batch_sch_id;
private String searchStartMonth;
private String searchEndMonth;
public String getSearchEndMonth() {
	return searchEndMonth;
}
public void setSearchEndMonth(String searchEndMonth) {
	this.searchEndMonth = searchEndMonth;
}
public String getBatch_sch_id() {
	return batch_sch_id;
}
public void setBatch_sch_id(String batchSchId) {
	batch_sch_id = batchSchId;
}
public String getSearchActivityId() {
	return searchActivityId;
}
public void setSearchActivityId(String searchActivityId) {
	this.searchActivityId = searchActivityId;
}
public String getSearchStartDate() {
	return searchStartDate;
}
public void setSearchStartDate(String searchStartDate) {
	this.searchStartDate = searchStartDate;
}
public String getSearchEndDate() {
	return searchEndDate;
}
public void setSearchEndDate(String searchEndDate) {
	this.searchEndDate = searchEndDate;
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
private String makerDate;
public String getLbxActivityCode() {
	return lbxActivityCode;
}
public void setLbxActivityCode(String lbxActivityCode) {
	this.lbxActivityCode = lbxActivityCode;
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
public String getActivityId() {
	return activityId;
}
public void setActivityId(String activityId) {
	this.activityId = activityId;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getRecStatus() {
	return recStatus;
}
public void setRecStatus(String recStatus) {
	this.recStatus = recStatus;
}
public String getStartMonth() {
	return startMonth;
}
public void setStartMonth(String startMonth) {
	this.startMonth = startMonth;
}
public String getEndMonth() {
	return endMonth;
}
public void setEndMonth(String endMonth) {
	this.endMonth = endMonth;
}
public String getSearchStartMonth() {
	return searchStartMonth;
}
public void setSearchStartMonth(String searchStartMonth) {
	this.searchStartMonth = searchStartMonth;
}

}
