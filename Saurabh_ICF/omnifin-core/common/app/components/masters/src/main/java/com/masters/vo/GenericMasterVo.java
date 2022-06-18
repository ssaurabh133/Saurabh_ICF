package com.masters.vo;

import java.io.Serializable;

public class GenericMasterVo implements Serializable{

	String genericKey;
	String parentValue;
	String genericval;
	String description;
	String status;
	private	String makerId;
	private	String makerDate;
	private String genericSearchKey;
	private String searchDescription;
	private String genericKeyModify;
	private String lbxGenericId;
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
	public String getGenericSearchKey() {
		return genericSearchKey;
	}
	public void setGenericSearchKey(String genericSearchKey) {
		this.genericSearchKey = genericSearchKey;
	}
	public String getSearchDescription() {
		return searchDescription;
	}
	public void setSearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
	}
	public String getGenericKey() {
		return genericKey;
	}
	public void setGenericKey(String genericKey) {
		this.genericKey = genericKey;
	}
	public String getParentValue() {
		return parentValue;
	}
	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}


	public String getGenericval() {
		return genericval;
	}
	public void setGenericval(String genericval) {
		this.genericval = genericval;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public void setGenericKeyModify(String genericKeyModify) {
		this.genericKeyModify = genericKeyModify;
	}
	public String getGenericKeyModify() {
		return genericKeyModify;
	}
	public void setLbxGenericId(String lbxGenericId) {
		this.lbxGenericId = lbxGenericId;
	}
	public String getLbxGenericId() {
		return lbxGenericId;
	}


}
