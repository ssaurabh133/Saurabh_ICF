//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class DistrictMasterVo implements Serializable{
	
	private String districtId;
	private String districtDes;
	private String stateId;
	private String districtStatus;
	private String lbxStateId;
	private String makerId;
	private String makerDate;
	private String districtSearchDes;
	private String districtIdModify;
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
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getDistrictDes() {
		return districtDes;
	}
	public void setDistrictDes(String districtDes) {
		this.districtDes = districtDes;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getDistrictStatus() {
		return districtStatus;
	}
	public void setDistrictStatus(String districtStatus) {
		this.districtStatus = districtStatus;
	}
	public void setLbxStateId(String lbxStateId) {
		this.lbxStateId = lbxStateId;
	}
	public String getLbxStateId() {
		return lbxStateId;
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
	public void setDistrictSearchDes(String districtSearchDes) {
		this.districtSearchDes = districtSearchDes;
	}
	public String getDistrictSearchDes() {
		return districtSearchDes;
	}
	public void setDistrictIdModify(String districtIdModify) {
		this.districtIdModify = districtIdModify;
	}
	public String getDistrictIdModify() {
		return districtIdModify;
	}

	
	
	
}
