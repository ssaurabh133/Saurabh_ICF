package com.masters.vo;

import java.io.Serializable;

public class RoleAccessMasterVo implements Serializable{
	private String roleId;
	private String lbxRoleId;
	private String moduleId;
	private String lbxModuleId;
	private String funName;
	private String funNameforCheckBox;
	private String funDesc;
	
	private String roleAccessStatus;
	private String makerId;
	private String makerDate;
	private String parentId;
	
	
	public String getFunNameforCheckBox() {
		return funNameforCheckBox;
	}
	public void setFunNameforCheckBox(String funNameforCheckBox) {
		this.funNameforCheckBox = funNameforCheckBox;
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
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getLbxModuleId() {
		return lbxModuleId;
	}
	public void setLbxModuleId(String lbxModuleId) {
		this.lbxModuleId = lbxModuleId;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getFunDesc() {
		return funDesc;
	}
	public void setFunDesc(String funDesc) {
		this.funDesc = funDesc;
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
	public void setRoleAccessStatus(String roleAccessStatus) {
		this.roleAccessStatus = roleAccessStatus;
	}
	public String getRoleAccessStatus() {
		return roleAccessStatus;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentId() {
		return parentId;
	}

}
