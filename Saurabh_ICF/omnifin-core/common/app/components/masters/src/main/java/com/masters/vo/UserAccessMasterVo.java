package com.masters.vo;

import java.io.Serializable;

public class UserAccessMasterVo implements Serializable{
       private String userAccessId;
       private String roleId;
       private String lbxRoleId;
       private String userId;
       private String lbxUserId;
       private String moduleId;
       private String lbxModule;
       private String userAccessStatus;
       private String makerId;
       private String makerDate;
       private String userSearchId;
       private String lbxUserSearchId;
       private String moduleSearchId;
       private String lbxModuleSearch;
       private String userAccessIdModify;
       private String moduleStatus;
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
	public String getModuleStatus() {
		return moduleStatus;
	}
	public void setModuleStatus(String moduleStatus) {
		this.moduleStatus = moduleStatus;
	}
	public String getUserSearchId() {
		return userSearchId;
	}
	public void setUserSearchId(String userSearchId) {
		this.userSearchId = userSearchId;
	}
	public String getLbxUserSearchId() {
		return lbxUserSearchId;
	}
	public void setLbxUserSearchId(String lbxUserSearchId) {
		this.lbxUserSearchId = lbxUserSearchId;
	}
	public String getModuleSearchId() {
		return moduleSearchId;
	}
	public void setModuleSearchId(String moduleSearchId) {
		this.moduleSearchId = moduleSearchId;
	}
	public String getLbxModuleSearch() {
		return lbxModuleSearch;
	}
	public void setLbxModuleSearch(String lbxModuleSearch) {
		this.lbxModuleSearch = lbxModuleSearch;
	}
	public String getRoleSearchId() {
		return roleSearchId;
	}
	public void setRoleSearchId(String roleSearchId) {
		this.roleSearchId = roleSearchId;
	}
	public String getLbxRoleSearchId() {
		return lbxRoleSearchId;
	}
	public void setLbxRoleSearchId(String lbxRoleSearchId) {
		this.lbxRoleSearchId = lbxRoleSearchId;
	}
	private String roleSearchId;
       private String lbxRoleSearchId;
	
       public String getUserAccessId() {
		return userAccessId;
	}
	public void setUserAccessId(String userAccessId) {
		this.userAccessId = userAccessId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getLbxRoleId() {
		return lbxRoleId;
	}
	public void setLbxRoleId(String lbxRoleId) {
		this.lbxRoleId = lbxRoleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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
	public void setUserAccessStatus(String userAccessStatus) {
		this.userAccessStatus = userAccessStatus;
	}
	public String getUserAccessStatus() {
		return userAccessStatus;
	}
	public void setLbxModule(String lbxModule) {
		this.lbxModule = lbxModule;
	}
	public String getLbxModule() {
		return lbxModule;
	}
	
	public void setUserAccessIdModify(String userAccessIdModify) {
		this.userAccessIdModify = userAccessIdModify;
	}
	public String getUserAccessIdModify() {
		return userAccessIdModify;
	}
}
