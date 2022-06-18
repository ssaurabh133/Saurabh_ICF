package com.scz.vo;


public class FutureFlowLoanUploadVO {

	private int id;
	private String makerID;
	private String makerDate;
	private int currentPageLink;
	private int poolID;
	private String lbxPoolID;
	private String loanNoButton;
	private String poolName;
	private String poolCreationDate;
	private String month;
	private String mm;
	private String year;
	
	
	
	public String getMonth() {
		month = "01-"+getMm()+"-"+getYear();
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMakerID() {
		return makerID;
	}
	public void setMakerID(String makerID) {
		this.makerID = makerID;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getPoolID() {
		return poolID;
	}
	public void setPoolID(int poolID) {
		this.poolID = poolID;
	}
	public String getLbxPoolID() {
		return lbxPoolID;
	}
	public void setLbxPoolID(String lbxPoolID) {
		this.lbxPoolID = lbxPoolID;
	}
	public String getLoanNoButton() {
		return loanNoButton;
	}
	public void setLoanNoButton(String loanNoButton) {
		this.loanNoButton = loanNoButton;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getPoolCreationDate() {
		return poolCreationDate;
	}
	public void setPoolCreationDate(String poolCreationDate) {
		this.poolCreationDate = poolCreationDate;
	}
	
	
	
	
	
}
