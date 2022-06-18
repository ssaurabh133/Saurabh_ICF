package com.scz.vo;

import java.sql.Date;

public class DownloadedReportFlags {
	private int id;
	private int poolID;
	private Date monthOfReport;
	private String reportType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPoolID() {
		return poolID;
	}
	public void setPoolID(int poolID) {
		this.poolID = poolID;
	}
	public Date getMonthOfReport() {
		return monthOfReport;
	}
	public void setMonthOfReport(Date monthOfReport) {
		this.monthOfReport = monthOfReport;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	
}
