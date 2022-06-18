//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : VO for Industry Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class IndustryMasterVo implements Serializable{
	
	private String industryId;
	private String industryDesc;
	private String industryStatus;
	private String makerId;
	private String makerDate;
	private String industrySearchDesc;
	private String industryIdModify;
	private int currentPageLink;
	private int totalRecordSize;
	private String manufacturer;
	private String trader;
	private String service;

	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public void setIndustryDesc(String industryDesc) {
		this.industryDesc = industryDesc;
	}
	public String getIndustryDesc() {
		return industryDesc;
	}
	public void setIndustryStatus(String industryStatus) {
		this.industryStatus = industryStatus;
	}
	public String getIndustryStatus() {
		return industryStatus;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setIndustrySearchDesc(String industrySearchDesc) {
		this.industrySearchDesc = industrySearchDesc;
	}
	public String getIndustrySearchDesc() {
		return industrySearchDesc;
	}
	public void setIndustryIdModify(String industryIdModify) {
		this.industryIdModify = industryIdModify;
	}
	public String getIndustryIdModify() {
		return industryIdModify;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getTrader() {
		return trader;
	}
	public void setTrader(String trader) {
		this.trader = trader;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}

}
