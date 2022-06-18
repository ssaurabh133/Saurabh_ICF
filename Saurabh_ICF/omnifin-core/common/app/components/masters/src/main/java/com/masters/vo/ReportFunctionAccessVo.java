package com.masters.vo;

import java.io.Serializable;

public class ReportFunctionAccessVo implements Serializable{
	private String roleId;
	private String lbxRoleId;
	private String moduleId;
	private String lbxModuleId;
	private String reportName;
	private String status;
	private String reportDesc;
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
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReportDesc() {
		return reportDesc;
	}
	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	
	

}
