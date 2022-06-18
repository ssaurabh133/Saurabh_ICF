//Richa
package com.legal.vo;

import java.io.Serializable;

public class CourtTypeMasterVo implements Serializable{
	
	private String courtTypeCode;
	private String courtTypeDesc;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	
	
	public String getCourtTypeCode() {
		return courtTypeCode;
	}
	public void setCourtTypeCode(String courtTypeCode) {
		this.courtTypeCode = courtTypeCode;
	}
	public String getCourtTypeDesc() {
		return courtTypeDesc;
	}
	public void setCourtTypeDesc(String courtTypeDesc) {
		this.courtTypeDesc = courtTypeDesc;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
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

	
	
}
