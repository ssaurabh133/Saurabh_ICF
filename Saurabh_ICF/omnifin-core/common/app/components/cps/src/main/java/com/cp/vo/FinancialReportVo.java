package com.cp.vo;

import java.io.Serializable;

public class FinancialReportVo implements Serializable{

private String caseId;
private String reportType;
private String makerId;
private String makerDate;
private String ignoreFlag;
private String refId;
private String customerName;
private String stageId;
private int totalRecordSize;
public String getCaseId() {
	return caseId;
}
public void setCaseId(String caseId) {
	this.caseId = caseId;
}
public String getReportType() {
	return reportType;
}
public void setReportType(String reportType) {
	this.reportType = reportType;
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
public String getIgnoreFlag() {
	return ignoreFlag;
}
public String getRefId() {
	return refId;
}
public void setRefId(String refId) {
	this.refId = refId;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public void setIgnoreFlag(String ignoreFlag) {
	this.ignoreFlag = ignoreFlag;
}
public int getTotalRecordSize() {
	return totalRecordSize;
}
public void setTotalRecordSize(int totalRecordSize) {
	this.totalRecordSize = totalRecordSize;
}
public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

}
