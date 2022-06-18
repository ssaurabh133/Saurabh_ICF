//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class StateMasterVo implements Serializable{
	
	private String stateId;
	private String stateDes;
	private String countryId;
	private String stateStatus;
	private String txtCountryCode;
	private String makerId;
	private String makerDate;
	private String stateSearchDes;
	private String stateIdModify;
	private int currentPageLink;
	private int totalRecordSize;
	private String vatPercent;
	private String serviceTax;
	private String gstId;  //added by priyanka
	private String unionterritory; // added by priyanka
	private String gstCode; // added by priyanka
	
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
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getStateDes() {
		return stateDes;
	}
	public void setStateDes(String stateDes) {
		this.stateDes = stateDes;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getStateStatus() {
		return stateStatus;
	}
	public void setStateStatus(String stateStatus) {
		this.stateStatus = stateStatus;
	}
	public void setTxtCountryCode(String txtCountryCode) {
		this.txtCountryCode = txtCountryCode;
	}
	public String getTxtCountryCode() {
		return txtCountryCode;
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
	public void setStateSearchDes(String stateSearchDes) {
		this.stateSearchDes = stateSearchDes;
	}
	public String getStateSearchDes() {
		return stateSearchDes;
	}
	public void setStateIdModify(String stateIdModify) {
		this.stateIdModify = stateIdModify;
	}
	public String getStateIdModify() {
		return stateIdModify;
	}
	public String getVatPercent() {
		return vatPercent;
	}
	public void setVatPercent(String vatPercent) {
		this.vatPercent = vatPercent;
	}
	public String getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}
	public String getUnionterritory() {
		return unionterritory;
	}
	public void setUnionterritory(String unionterritory) {
		this.unionterritory = unionterritory;
	}
	public String getGstId() {
		return gstId;
	}
	public void setGstId(String gstId) {
		this.gstId = gstId;
	}
	public String getGstCode() {
		return gstCode;
	}
	public void setGstCode(String gstCode) {
		this.gstCode = gstCode;
	}
	
	
}
