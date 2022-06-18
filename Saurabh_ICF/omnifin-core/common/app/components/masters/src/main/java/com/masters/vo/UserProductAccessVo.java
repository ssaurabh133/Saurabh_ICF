package com.masters.vo;

import java.io.Serializable;

public class UserProductAccessVo implements Serializable{
	
	private String showUserDesc;
	private String showUserDescSearch;
	private String lbxUserId;
	private String showProductDesc;
	private String showProductDescSearch;
	private String lbxProductId;
	private String recStatus;
	private String radioSelection;
	private String makerId;
	private String makerDate;
	private String authorId;
	private String authorDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String [] product;
	
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
	
	public String getShowUserDesc() {
		return showUserDesc;
	}
	public void setShowUserDesc(String showUserDesc) {
		this.showUserDesc = showUserDesc;
	}
	public String getShowUserDescSearch() {
		return showUserDescSearch;
	}
	public void setShowUserDescSearch(String showUserDescSearch) {
		this.showUserDescSearch = showUserDescSearch;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getShowProductDesc() {
		return showProductDesc;
	}
	public void setShowProductDesc(String showProductDesc) {
		this.showProductDesc = showProductDesc;
	}
	public String getShowProductDescSearch() {
		return showProductDescSearch;
	}
	public void setShowProductDescSearch(String showProductDescSearch) {
		this.showProductDescSearch = showProductDescSearch;
	}
	public String getLbxProductId() {
		return lbxProductId;
	}
	public void setLbxProductId(String lbxProductId) {
		this.lbxProductId = lbxProductId;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRadioSelection() {
		return radioSelection;
	}
	public void setRadioSelection(String radioSelection) {
		this.radioSelection = radioSelection;
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
	public String[] getProduct() {
		return product;
	}
	public void setProduct(String[] product) {
		this.product = product;
	}
	

}
