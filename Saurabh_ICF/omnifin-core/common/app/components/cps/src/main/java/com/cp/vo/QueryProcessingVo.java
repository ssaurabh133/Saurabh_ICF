package com.cp.vo;

public class QueryProcessingVo {
	private String dealId;
	private String queryDate;
	private String queryTime;
	private String queryRemarks;
	private String resolutionStatus;
	private String resolutionDate;
	private String resolutionTime;
	private String resolutionRemarks;
	private String userId;
	private String makerDate;
	private String lbxUserId;
	private String resolution;
	private String dealQueryId;
	private String dealQueryResponseId[];
	private String queryRaised;
	private String initiatedBy;
	private String initiatedTo;
	private String queryType;
	private String queryTypeDesc;
	
	
	public String getQueryTypeDesc() {
		return queryTypeDesc;
	}
	public void setQueryTypeDesc(String queryTypeDesc) {
		this.queryTypeDesc = queryTypeDesc;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getInitiatedTo() {
		return initiatedTo;
	}
	public void setInitiatedTo(String initiatedTo) {
		this.initiatedTo = initiatedTo;
	}
	public String getInitiatedBy() {
		return initiatedBy;
	}
	public void setInitiatedBy(String initiatedBy) {
		this.initiatedBy = initiatedBy;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getDealId() {
		return dealId;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}
	public String getQueryRemarks() {
		return queryRemarks;
	}
	public void setQueryRemarks(String queryRemarks) {
		this.queryRemarks = queryRemarks;
	}
	public String getResolutionStatus() {
		return resolutionStatus;
	}
	public void setResolutionStatus(String resolutionStatus) {
		this.resolutionStatus = resolutionStatus;
	}
	public String getResolutionDate() {
		return resolutionDate;
	}
	public void setResolutionDate(String resolutionDate) {
		this.resolutionDate = resolutionDate;
	}
	public String getResolutionTime() {
		return resolutionTime;
	}
	public void setResolutionTime(String resolutionTime) {
		this.resolutionTime = resolutionTime;
	}
	public String getResolutionRemarks() {
		return resolutionRemarks;
	}
	public void setResolutionRemarks(String resolutionRemarks) {
		this.resolutionRemarks = resolutionRemarks;
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
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getResolution() {
		return resolution;
	}
	public void setDealQueryId(String dealQueryId) {
		this.dealQueryId = dealQueryId;
	}
	public String getDealQueryId() {
		return dealQueryId;
	}
	public void setDealQueryResponseId(String dealQueryResponseId[]) {
		this.dealQueryResponseId = dealQueryResponseId;
	}
	public String[] getDealQueryResponseId() {
		return dealQueryResponseId;
	}
	public void setQueryRaised(String queryRaised) {
		this.queryRaised = queryRaised;
	}
	public String getQueryRaised() {
		return queryRaised;
	}
	

}
