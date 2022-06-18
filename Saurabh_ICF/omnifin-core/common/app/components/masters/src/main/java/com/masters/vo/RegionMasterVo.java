//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class RegionMasterVo implements Serializable{
	
	private String regionId;
	private String regionDes;
	private String regionStatus;
	private String makerId;
	private String makerDate;
	private String regionSearchDes;
	private String regionIdModify;
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
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getRegionDes() {
		return regionDes;
	}
	public void setRegionDes(String regionDes) {
		this.regionDes = regionDes;
	}
	public String getRegionStatus() {
		return regionStatus;
	}
	public void setRegionStatus(String regionStatus) {
		this.regionStatus = regionStatus;
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
	public void setRegionSearchDes(String regionSearchDes) {
		this.regionSearchDes = regionSearchDes;
	}
	public String getRegionSearchDes() {
		return regionSearchDes;
	}
	public void setRegionIdModify(String regionIdModify) {
		this.regionIdModify = regionIdModify;
	}
	public String getRegionIdModify() {
		return regionIdModify;
	}
	
}