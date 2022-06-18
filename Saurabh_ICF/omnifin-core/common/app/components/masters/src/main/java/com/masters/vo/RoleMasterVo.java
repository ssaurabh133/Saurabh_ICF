package com.masters.vo;

import java.io.Serializable;

public class RoleMasterVo implements Serializable{
	private String roleId;
	private String roleDesc;
	private String moduleId;
	private String lbxModule;
	private String roleStatus;
	private String makerId;
	private String makerDate;
	private String roleSearchDesc;
	private String roleIdModify;
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
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}
	public String getRoleStatus() {
		return roleStatus;
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
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setLbxModule(String lbxModule) {
		this.lbxModule = lbxModule;
	}
	public String getLbxModule() {
		return lbxModule;
	}
	public void setRoleSearchDesc(String roleSearchDesc) {
		this.roleSearchDesc = roleSearchDesc;
	}
	public String getRoleSearchDesc() {
		return roleSearchDesc;
	}
	public void setRoleIdModify(String roleIdModify) {
		this.roleIdModify = roleIdModify;
	}
	public String getRoleIdModify() {
		return roleIdModify;
	}

}
