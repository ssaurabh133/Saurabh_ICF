//Author Name : Ritu Jindal-->
//Date of Creation : 9 May 2011-->
//Purpose  : VO for Document Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class DocumentMasterVo implements Serializable{
	
	private String documentId;
	private String documentDesc;
	private String documentStatus;
	private String makerId;
	private String makerDate;
	private String documentSearchDesc;
	private String documentIdModify;
	private String documentCategory;
	 
	
	private int currentPageLink;
	private int totalRecordSize;
	
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
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}
	public String getDocumentDesc() {
		return documentDesc;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentSearchDesc(String documentSearchDesc) {
		this.documentSearchDesc = documentSearchDesc;
	}
	public String getDocumentSearchDesc() {
		return documentSearchDesc;
	}
	public void setDocumentIdModify(String documentIdModify) {
		this.documentIdModify = documentIdModify;
	}
	public String getDocumentIdModify() {
		return documentIdModify;
	}
	public String getDocumentCategory() {
		return documentCategory;
	}
	
	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}
}
