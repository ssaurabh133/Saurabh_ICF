//Richa
package com.legal.vo;

import java.io.Serializable;

public class POAMasterVo implements Serializable{
	
	private String lbxBranchId;
	private String courtNameDesc;
	public String getCourtNameDesc() {
		return courtNameDesc;
	}
	public void setCourtNameDesc(String courtNameDesc) {
		this.courtNameDesc = courtNameDesc;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	private String poaCode;
	private String poa;
	public String getPoa() {
		return poa;
	}
	public void setPoa(String poa) {
		this.poa = poa;
	}
	private String branch;
	private String poaDesc;	
	public String getPoaDesc() {
		return poaDesc;
	}
	public void setPoaDesc(String poaDesc) {
		this.poaDesc = poaDesc;
	}
	private String courtName;
	
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String userId;

	private String lbxUserId;
	private String lbxCourtNameCode;
	
	public String getLbxCourtNameCode() {
		return lbxCourtNameCode;
	}
	public void setLbxCourtNameCode(String lbxCourtNameCode) {
		this.lbxCourtNameCode = lbxCourtNameCode;
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
	public  String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCourtName() {
		return courtName;
	}
	public void setCourtName(String courtName) {
		this.courtName = courtName;
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
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	public String getPoaCode() {
		return poaCode;
	}

	
	
}
