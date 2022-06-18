//Author Name : Nazia-->
//Date of Creation : 12 Mar 2013-->
//Purpose  : VO for notice Master-->
//Documentation : -->

package com.legal.vo;

import java.io.Serializable;

public class NoticeTypeMasterVo implements Serializable{
	
	private String noticeTypeCode;
	private String noticeTypeDesc;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	
	
	
	

	public String getNoticeTypeCode() {
		return noticeTypeCode;
	}
	public void setNoticeTypeCode(String noticeTypeCode) {
		this.noticeTypeCode = noticeTypeCode;
	}
	public String getNoticeTypeDesc() {
		return noticeTypeDesc;
	}
	public void setNoticeTypeDesc(String noticeTypeDesc) {
		this.noticeTypeDesc = noticeTypeDesc;
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
