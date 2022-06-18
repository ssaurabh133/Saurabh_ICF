package com.masters.vo;

import java.io.Serializable;

public class DepartmentMasterVo implements Serializable{
	private String departmentId;
	private String departmentDes;
	private String departmentStatus;
	private String makerId;
	private String makerDate;
	private String departmentSearchDes;
	private String departmentIdModify;
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
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentDes(String departmentDes) {
		this.departmentDes = departmentDes;
	}
	public String getDepartmentDes() {
		return departmentDes;
	}
	public void setDepartmentStatus(String departmentStatus) {
		this.departmentStatus = departmentStatus;
	}
	public String getDepartmentStatus() {
		return departmentStatus;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setDepartmentSearchDes(String departmentSearchDes) {
		this.departmentSearchDes = departmentSearchDes;
	}
	public String getDepartmentSearchDes() {
		return departmentSearchDes;
	}
	public void setDepartmentIdModify(String departmentIdModify) {
		this.departmentIdModify = departmentIdModify;
	}
	public String getDepartmentIdModify() {
		return departmentIdModify;
	}

}
