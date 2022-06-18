//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.repo.vo;

import java.io.Serializable;

public class StockyardMasterVo implements Serializable{
	
	private String stockyard;
	private String stockyardDesc;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String lbxUserId; 
	
	
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getStockyard() {
		return stockyard;
	}
	public void setStockyard(String stockyard) {
		this.stockyard = stockyard;
	}
	public String getStockyardDesc() {
		return stockyardDesc;
	}
	public void setStockyardDesc(String stockyardDesc) {
		this.stockyardDesc = stockyardDesc;
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
