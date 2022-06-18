package com.caps.VO;

public class CollectionSummaryVO {
	
	private String totalAllocatedCase;
	private String followupDueToday;
	private String escalatedCases;
	private String escalatedRecive;
	private String totalPTPDoneToday;
	private String userId;
	private String businessDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getTotalAllocatedCase() {
		return totalAllocatedCase;
	}
	public void setTotalAllocatedCase(String totalAllocatedCase) {
		this.totalAllocatedCase = totalAllocatedCase;
	}
	public String getFollowupDueToday() {
		return followupDueToday;
	}
	public void setFollowupDueToday(String followupDueToday) {
		this.followupDueToday = followupDueToday;
	}
	public String getEscalatedCases() {
		return escalatedCases;
	}
	public void setEscalatedCases(String escalatedCases) {
		this.escalatedCases = escalatedCases;
	}
	public String getEscalatedRecive() {
		return escalatedRecive;
	}
	public void setEscalatedRecive(String escalatedRecive) {
		this.escalatedRecive = escalatedRecive;
	}
	public String getTotalPTPDoneToday() {
		return totalPTPDoneToday;
	}
	public void setTotalPTPDoneToday(String totalPTPDoneToday) {
		this.totalPTPDoneToday = totalPTPDoneToday;
	}
	
	

}
