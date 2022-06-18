//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : VO for SubIndustry Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class SubIndustryMasterVo implements Serializable{
	
	private String subIndustryId;
	private String subIndustryDesc;
	private String industryId;
	private String subIndustryStatus;
	private String lbxIndustry;
	private String makerId;
	private String makerDate;
	private String subIndustrySearchDesc;
	private String subIndustryIdModify;
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
	public void setSubIndustryDesc(String subIndustryDesc) {
		this.subIndustryDesc = subIndustryDesc;
	}
	public String getSubIndustryDesc() {
		return subIndustryDesc;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setSubIndustryStatus(String subIndustryStatus) {
		this.subIndustryStatus = subIndustryStatus;
	}
	public String getSubIndustryStatus() {
		return subIndustryStatus;
	}
	public String getLbxIndustry() {
		return lbxIndustry;
	}
	public void setLbxIndustry(String lbxIndustry) {
		this.lbxIndustry = lbxIndustry;
	}
	public void setSubIndustryId(String subIndustryId) {
		this.subIndustryId = subIndustryId;
	}
	public String getSubIndustryId() {
		return subIndustryId;
	}
	public void setSubIndustrySearchDesc(String subIndustrySearchDesc) {
		this.subIndustrySearchDesc = subIndustrySearchDesc;
	}
	public String getSubIndustrySearchDesc() {
		return subIndustrySearchDesc;
	}
	public void setSubIndustryIdModify(String subIndustryIdModify) {
		this.subIndustryIdModify = subIndustryIdModify;
	}
	public String getSubIndustryIdModify() {
		return subIndustryIdModify;
	}

}
