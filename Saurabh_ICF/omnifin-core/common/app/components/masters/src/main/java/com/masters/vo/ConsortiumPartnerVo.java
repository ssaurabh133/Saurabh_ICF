package com.masters.vo;

import java.io.Serializable;

public class ConsortiumPartnerVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String consortiumPartnerId;
	private String consortiumPartnerIdSearch;
	private String consortiumPartnerName;
	private String consortiumPartnerNameSearch;
	private String defaultPercentageLoan;
	private String defaultAgreedLoan;
	private String consortiumStatus;
	private int currentPageLink;
	private int totalRecordSize;
	private String makerId;
	private String makerDate;
	private String consortiumPartnerIdModify;
	private String consortiumPartnerStatus;
	
	
	
	public String getConsortiumPartnerIdModify() {
		return consortiumPartnerIdModify;
	}
	public void setConsortiumPartnerIdModify(String consortiumPartnerIdModify) {
		this.consortiumPartnerIdModify = consortiumPartnerIdModify;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public String getConsortiumStatus() {
		return consortiumStatus;
	}
	public void setConsortiumStatus(String consortiumStatus) {
		this.consortiumStatus = consortiumStatus;
	}
	public String getConsortiumPartnerId() {
		return consortiumPartnerId;
	}
	public void setConsortiumPartnerId(String consortiumPartnerId) {
		this.consortiumPartnerId = consortiumPartnerId;
	}
	public String getConsortiumPartnerName() {
		return consortiumPartnerName;
	}
	public void setConsortiumPartnerName(String consortiumPartnerName) {
		this.consortiumPartnerName = consortiumPartnerName;
	}
	public String getDefaultPercentageLoan() {
		return defaultPercentageLoan;
	}
	public void setDefaultPercentageLoan(String defaultPercentageLoan) {
		this.defaultPercentageLoan = defaultPercentageLoan;
	}
	public String getDefaultAgreedLoan() {
		return defaultAgreedLoan;
	}
	public void setDefaultAgreedLoan(String defaultAgreedLoan) {
		this.defaultAgreedLoan = defaultAgreedLoan;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getConsortiumPartnerNameSearch() {
		return consortiumPartnerNameSearch;
	}
	public void setConsortiumPartnerNameSearch(String consortiumPartnerNameSearch) {
		this.consortiumPartnerNameSearch = consortiumPartnerNameSearch;
	}
	public String getConsortiumPartnerIdSearch() {
		return consortiumPartnerIdSearch;
	}
	public void setConsortiumPartnerIdSearch(String consortiumPartnerIdSearch) {
		this.consortiumPartnerIdSearch = consortiumPartnerIdSearch;
	}
	public String getConsortiumPartnerStatus() {
		return consortiumPartnerStatus;
	}
	public void setConsortiumPartnerStatus(String consortiumPartnerStatus) {
		this.consortiumPartnerStatus = consortiumPartnerStatus;
	}
	
	
	

}
