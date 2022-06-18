package com.masters.vo;

import java.io.Serializable;

public class MobileUserMappingVo implements Serializable {

	
	private String mobileUserId;
	private String mobileUserName;
	private String userId;
	private String mobile;
	private String mobileNo;
	private String imeiNo;
	private String userStatus;
	private String idModify;
	private String mobileUserNameSearch;
	private int	TotalRecordSize;
	private int  currentPageLink;
	
	
	
	
	
	
	
	public String getMobileUserNameSearch() {
		return mobileUserNameSearch;
	}
	public void setMobileUserNameSearch(String mobileUserNameSearch) {
		this.mobileUserNameSearch = mobileUserNameSearch;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public int getTotalRecordSize() {
		return TotalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		TotalRecordSize = totalRecordSize;
	}
	public String getIdModify() {
		return idModify;
	}
	public void setIdModify(String idModify) {
		this.idModify = idModify;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getMobileUserId() {
		return mobileUserId;
	}
	public void setMobileUserId(String mobileUserId) {
		this.mobileUserId = mobileUserId;
	}
	public String getMobileUserName() {
		return mobileUserName;
	}
	public void setMobileUserName(String mobileUserName) {
		this.mobileUserName = mobileUserName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getImeiNo() {
		return imeiNo;
	}
	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}


	
}
