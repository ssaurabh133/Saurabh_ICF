package com.cp.vo;

public class SalesAnalysisVo {
	
	private String salesMonthAndYear;
	private String netsales[];
	private String netSales;
	private String userId;
	private String businessDate;
	private String dealId;
	private String salesId;
	private String averageSale;
	private String increaseInSale;
	private String avgsales[];
	private String interest[];
	
	public String getNetSales() {
		return netSales;
	}
	public void setNetSales(String netSales) {
		this.netSales = netSales;
	}
	public String getAverageSale() {
		return averageSale;
	}
	public void setAverageSale(String averageSale) {
		this.averageSale = averageSale;
	}
	public String getIncreaseInSale() {
		return increaseInSale;
	}
	public void setIncreaseInSale(String increaseInSale) {
		this.increaseInSale = increaseInSale;
	}
	public String[] getAvgsales() {
		return avgsales;
	}
	public void setAvgsales(String[] avgsales) {
		this.avgsales = avgsales;
	}
	public String[] getInterest() {
		return interest;
	}
	public void setInterest(String[] interest) {
		this.interest = interest;
	}
	private String year;
	private String month;
	private String startMonth;
	private String startYear;
	private String endMonth;
	private String endYear;
	private String nextMonIncreaseSale;
	
	
	
	public String getNextMonIncreaseSale() {
		return nextMonIncreaseSale;
	}
	public void setNextMonIncreaseSale(String nextMonIncreaseSale) {
		this.nextMonIncreaseSale = nextMonIncreaseSale;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getStartYear() {
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public String getEndYear() {
		return endYear;
	}
	public void setEndYear(String endYear) {
		this.endYear = endYear;
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

	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
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
	public String getSalesMonthAndYear() {
		return salesMonthAndYear;
	}
	public void setSalesMonthAndYear(String salesMonthAndYear) {
		this.salesMonthAndYear = salesMonthAndYear;
	}
	public String[] getNetsales() {
		return netsales;
	}
	public void setNetsales(String[] netsales) {
		this.netsales = netsales;
	}
	
	
	

}
