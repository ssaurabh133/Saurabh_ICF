package com.masters.vo;

import java.io.Serializable;

public class UserMasterVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
    private String empId;
	private String userDept;
	private String branchId;
	private String lbxBranchId;
	private String lbxBranchIds;
	private String userDesg;
	private String lbxDesignationId;
	private String phone1;
	private String phone2;
	private String email;
	private String password;
	private String userStatus;
	private String makerId;
	private String makerDate;
	private String lbxDepartmentId;
	private String userSearchId;
	private String userSearchName;
	private String branchSearchId;
	private String lbxBranchSearchId;
	private String userIdModify;
	private String branchDesc;
	private int currentPageLink;
	private int totalRecordSize;
	private String lbxReportingUser;
	private String reportingto;
	private String lbxUserSearchId;
	private String[]allselection;
	private String selectionAccess;
	private String report;
	private String[] selection;
	private String levelAccess;
	private String levelID;
	private String levelDesc;
	private String validityDate;
	private String remarks;

	public String getLevelAccess() {
		return levelAccess;
	}
	public void setLevelAccess(String levelAccess) {
		this.levelAccess = levelAccess;
	}
	public String getLevelID() {
		return levelID;
	}
	public void setLevelID(String levelID) {
		this.levelID = levelID;
	}
	public String getLevelDesc() {
		return levelDesc;
	}
	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}
	public String[] getSelection() {
		return selection;
	}
	public void setSelection(String[] selection) {
		this.selection = selection;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getSelectionAccess() {
		return selectionAccess;
	}
	public void setSelectionAccess(String selectionAccess) {
		this.selectionAccess = selectionAccess;
	}
	public String[] getAllselection() {
		return allselection;
	}
	public void setAllselection(String[] allselection) {
		this.allselection = allselection;
	}
	public String getLbxUserSearchId() {
		return lbxUserSearchId;
	}
	public void setLbxUserSearchId(String lbxUserSearchId) {
		this.lbxUserSearchId = lbxUserSearchId;
	}
	public String getLbxReportingUser() {
		return lbxReportingUser;
	}
	public void setLbxReportingUser(String lbxReportingUser) {
		this.lbxReportingUser = lbxReportingUser;
	}
	public String getReportingto() {
		return reportingto;
	}
	public void setReportingto(String reportingto) {
		this.reportingto = reportingto;
	}
	public String getLbxBranchIds() {
		return lbxBranchIds;
	}
	public void setLbxBranchIds(String lbxBranchIds) {
		this.lbxBranchIds = lbxBranchIds;
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
	public String getUserSearchId() {
		return userSearchId;
	}
	public void setUserSearchId(String userSearchId) {
		this.userSearchId = userSearchId;
	}
	public String getUserSearchName() {
		return userSearchName;
	}
	public void setUserSearchName(String userSearchName) {
		this.userSearchName = userSearchName;
	}
	public String getBranchSearchId() {
		return branchSearchId;
	}
	public void setBranchSearchId(String branchSearchId) {
		this.branchSearchId = branchSearchId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDept() {
		return userDept;
	}
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	public String getUserDesg() {
		return userDesg;
	}
	public void setUserDesg(String userDesg) {
		this.userDesg = userDesg;
	}
	public String getLbxDesignationId() {
		return lbxDesignationId;
	}
	public void setLbxDesignationId(String lbxDesignationId) {
		this.lbxDesignationId = lbxDesignationId;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
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
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setLbxDepartmentId(String lbxDepartmentId) {
		this.lbxDepartmentId = lbxDepartmentId;
	}
	public String getLbxDepartmentId() {
		return lbxDepartmentId;
	}
	public void setLbxBranchSearchId(String lbxBranchSearchId) {
		this.lbxBranchSearchId = lbxBranchSearchId;
	}
	public String getLbxBranchSearchId() {
		return lbxBranchSearchId;
	}
	public void setUserIdModify(String userIdModify) {
		this.userIdModify = userIdModify;
	}
	public String getUserIdModify() {
		return userIdModify;
	}
	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}
	public String getBranchDesc() {
		return branchDesc;
	}
	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
	public String getValidityDate() {
		return validityDate;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}

}
