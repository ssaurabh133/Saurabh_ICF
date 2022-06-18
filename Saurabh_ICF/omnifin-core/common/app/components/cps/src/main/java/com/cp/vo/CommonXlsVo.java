package com.cp.vo;

public class CommonXlsVo {
	private String particulars;
	private String notes;
	private String currentReportingPeriodAmount;
	private String previousReportingPeriodAmount;
	private String reportingAmount;
	private String[] balanceSheet;
	private String[] liabilities;
	private String[] assets;
	private String singleEntrySheet[];
	private String errorMsg;
	private String year;
	private String month;
	/*private String[] leftSheet;
	private String[] rightSheet;*/
	
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCurrentReportingPeriodAmount() {
		return currentReportingPeriodAmount;
	}
	public void setCurrentReportingPeriodAmount(String currentReportingPeriodAmount) {
		this.currentReportingPeriodAmount = currentReportingPeriodAmount;
	}
	public String getPreviousReportingPeriodAmount() {
		return previousReportingPeriodAmount;
	}
	public void setPreviousReportingPeriodAmount(
			String previousReportingPeriodAmount) {
		this.previousReportingPeriodAmount = previousReportingPeriodAmount;
	}
	
	public String[] getBalanceSheet() {
		return balanceSheet;
	}
	public void setBalanceSheet(String[] balanceSheet) {
		this.balanceSheet = balanceSheet;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String[] getSingleEntrySheet() {
		return singleEntrySheet;
	}
	public void setSingleEntrySheet(String[] singleEntrySheet) {
		this.singleEntrySheet = singleEntrySheet;
	}
	public String getReportingAmount() {
		return reportingAmount;
	}
	public void setReportingAmount(String reportingAmount) {
		this.reportingAmount = reportingAmount;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String[] getLiabilities() {
		return liabilities;
	}
	public void setLiabilities(String[] liabilities) {
		this.liabilities = liabilities;
	}
	public String[] getAssets() {
		return assets;
	}
	public void setAssets(String[] assets) {
		this.assets = assets;
	}
	/*public String[] getLeftSheet() {
		return leftSheet;
	}
	public void setLeftSheet(String[] leftSheet) {
		this.leftSheet = leftSheet;
	}
	public String[] getRightSheet() {
		return rightSheet;
	}
	public void setRightSheet(String[] rightSheet) {
		this.rightSheet = rightSheet;
	}*/
	
	
}
