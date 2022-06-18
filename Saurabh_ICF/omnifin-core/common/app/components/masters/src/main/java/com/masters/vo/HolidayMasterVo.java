/**
 * This class has setter and getter method for the property
 * @author Vishal Singh
 * @Date  31 March 2012
 * 
 */

package com.masters.vo;

import java.io.Serializable;

public class HolidayMasterVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String holidayId;
	private String holidayDes;
	private String holidayDate;
	
	private String makerId;
	private String makerDate;
	private String authorId;
	private String authorDate;
	private String holidayStatus;
	private int totalRecordSize;
	private int currentPageLink;
	
	private String holidaySearchDate;
	private String holidaySearchDes;
	
	private String holidayIdModify;
	
	private String holidayType;
	private String holidayDay;
	private String weekendDay;
	private String holidayDateModify;
	
	private String holidayTypeSearch;

	
	public String getHolidayTypeSearch() {
		return holidayTypeSearch;
	}
	public void setHolidayTypeSearch(String holidayTypeSearch) {
		this.holidayTypeSearch = holidayTypeSearch;
	}
	public String getHolidayDateModify() {
		return holidayDateModify;
	}
	public void setHolidayDateModify(String holidayDateModify) {
		this.holidayDateModify = holidayDateModify;
	}
	public String getHolidayDay() {
		return holidayDay;
	}
	public void setHolidayDay(String holidayDay) {
		this.holidayDay = holidayDay;
	}
	public String getWeekendDay() {
		return weekendDay;
	}
	public void setWeekendDay(String weekendDay) {
		this.weekendDay = weekendDay;
	}
	public String getHolidayType() {
		return holidayType;
	}
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}
	public String getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}
	public String getHolidayDes() {
		return holidayDes;
	}
	public void setHolidayDes(String holidayDes) {
		this.holidayDes = holidayDes;
	}
	public String getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
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
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public void setHolidayStatus(String holidayStatus) {
		this.holidayStatus = holidayStatus;
	}
	public String getHolidayStatus() {
		return holidayStatus;
	}
	
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	
	public String getholidaySearchDate() {
		return holidaySearchDate;
	}
	public void setholidaySearchDate(String holidaySearchDate) {
		this.holidaySearchDate = holidaySearchDate;
	}
	
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	
	public String getholidaySearchDes() {
		return holidaySearchDes;
	}
	public void setholidaySearchDes(String holidaySearchDes) {
		this.holidaySearchDes = holidaySearchDes;
	}
	
	public void setHolidayIdModify(String holidayIdModify) {
		this.holidayIdModify = holidayIdModify;
	}
	public String getHolidayIdModify() {
		return holidayIdModify;
	}
	

	
	
	
}

