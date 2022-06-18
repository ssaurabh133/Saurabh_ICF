//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.legal.vo;

import java.io.Serializable;

public class CaseTypeMasterVo implements Serializable{
	
	private String caseTypeCode;
	private String caseTypeDesc;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	
	
	public String getCaseTypeCode() {
		return caseTypeCode;
	}
	public void setCaseTypeCode(String caseTypeCode) {
		this.caseTypeCode = caseTypeCode;
	}
	public String getCaseTypeDesc() {
		return caseTypeDesc;
	}
	public void setCaseTypeDesc(String caseTypeDesc) {
		this.caseTypeDesc = caseTypeDesc;
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
