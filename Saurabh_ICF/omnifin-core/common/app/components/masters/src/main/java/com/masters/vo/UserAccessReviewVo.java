package com.masters.vo;

import java.io.Serializable;

public class UserAccessReviewVo implements Serializable {
	 private String reportformat;
	 private String userType;
	 private String fromDate;
	 private String toDate;
	 private String UserDeactivatedStartDate;
	 private String UserDeactivatedEndDate;
	 public String getReportformat() {
		return reportformat;
	}
	public void setReportformat(String reportformat) {
		this.reportformat = reportformat;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getUserDeactivatedStartDate() {
		return UserDeactivatedStartDate;
	}
	public void setUserDeactivatedStartDate(String userDeactivatedStartDate) {
		UserDeactivatedStartDate = userDeactivatedStartDate;
	}
	public String getUserDeactivatedEndDate() {
		return UserDeactivatedEndDate;
	}
	public void setUserDeactivatedEndDate(String userDeactivatedEndDate) {
		UserDeactivatedEndDate = userDeactivatedEndDate;
	}
	
	 
	 
}
