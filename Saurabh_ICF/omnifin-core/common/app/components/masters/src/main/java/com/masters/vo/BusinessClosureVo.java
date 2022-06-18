package com.masters.vo;

import java.io.Serializable;

/**
 * @author A3S
 *
 */
public class BusinessClosureVo implements Serializable{
	private String businessMonth;
	private String businessYear;
	private String businessMonthSearch;
	private String businessMonthss;
	private String businessYearSearch;
	private String startDate;
	private String endDate;
	private String recStatus;
	private String makerId;
	private String makerDate;
	private int totalRecordSize;
	private int currentPageLink;
	
	public String getBusinessMonthss() {
		return businessMonthss;
	}

	public void setBusinessMonthss(String businessMonthss) {
		this.businessMonthss = businessMonthss;
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

	public String getBusinessYear() {
		return businessYear;
	}

	public void setBusinessYear(String businessYear) {
		this.businessYear = businessYear;
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

	public String getBusinessMonth() {
		return businessMonth;
	}

	public void setBusinessMonth(String businessMonth) {
		this.businessMonth = businessMonth;
	}
	public String getBusinessMonthSearch() {
		return businessMonthSearch;
	}

	public void setBusinessMonthSearch(String businessMonthSearch) {
		this.businessMonthSearch = businessMonthSearch;
	}

	public String getBusinessYearSearch() {
		return businessYearSearch;
	}

	public void setBusinessYearSearch(String businessYearSearch) {
		this.businessYearSearch = businessYearSearch;
	}
	public String getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}

}
