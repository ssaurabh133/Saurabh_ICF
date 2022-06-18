//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class CountryMasterVo implements Serializable{
	
	private String countryId;
	private String countryDes;
	private String currencyId;
	private String countryStatus;
	private String makerId;
	private String makerDate;
	private String countrySearchDes;
	private String countryIdModify;
	private int currentPageLink;
	private int totalRecordSize;

	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
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
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountryDes() {
		return countryDes;
	}
	public void setCountryDes(String countryDes) {
		this.countryDes = countryDes;
	}
	public String getcurrencyId() {
		return currencyId;
	}
	public void setcurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getCountryStatus() {
		return countryStatus;
	}
	public void setCountryStatus(String countryStatus) {
		this.countryStatus = countryStatus;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setCountrySearchDes(String countrySearchDes) {
		this.countrySearchDes = countrySearchDes;
	}
	public String getCountrySearchDes() {
		return countrySearchDes;
	}
	public void setCountryIdModify(String countryIdModify) {
		this.countryIdModify = countryIdModify;
	}
	public String getCountryIdModify() {
		return countryIdModify;
	}
	
}
