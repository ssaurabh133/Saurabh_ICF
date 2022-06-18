//Richa
package com.legal.vo;

import java.io.Serializable;

public class CourtNameMasterVo implements Serializable{
	
	private String courtNameCode;
	private String courtNameDesc;
	private String courtType;
	private String branch;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String lbxCourtTypeCode;
	private String lbxBranchId;
	public String getLbxCourtTypeCode() {
		return lbxCourtTypeCode;
	}
	public void setLbxCourtTypeCode(String lbxCourtTypeCode) {
		this.lbxCourtTypeCode = lbxCourtTypeCode;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	
	
	
	public String getCourtNameCode() {
		return courtNameCode;
	}
	public void setCourtNameCode(String courtNameCode) {
		this.courtNameCode = courtNameCode;
	}
	public String getCourtNameDesc() {
		return courtNameDesc;
	}
	public void setCourtNameDesc(String courtNameDesc) {
		this.courtNameDesc = courtNameDesc;
	}
	public String getCourtType() {
		return courtType;
	}
	public void setCourtType(String courtType) {
		this.courtType = courtType;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
