//Author Name : Surendra-->
//Date of Creation : -->
//Purpose  : VO for Sub Dealer Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class SubDealerMasterVo implements Serializable{

	private String subDealerID;
	private String subDealerCode;
	private String subDealerDes;
	private String subDealerType;
	private String subDealerStatus;
	private String lbxdealerType;
	private String makerId;
	private String makerDate;
	private String subDealerSearchCode;
	private String subDealerSearchDes;
	private String subDealerIdModify;
	private int currentPageLink;
	private int totalRecordSize;
	private String dealerID;
	private String dealerDes;
	private String subDealerBankAC;
	private String subDealerCodeModify;
	private String dealerSearchID;
	private String dealerSearchDesc;
	
	public String getDealerSearchDesc() {
		return dealerSearchDesc;
	}
	public void setDealerSearchDesc(String dealerSearchDes) {
		this.dealerSearchDesc = dealerSearchDes;
	}
	public String getSubDealerID() {
		return subDealerID;
	}
	public void setSubDealerID(String subDealerID) {
		this.subDealerID = subDealerID;
	}
	public String getSubDealerCodeModify() {
		return subDealerCodeModify;
	}
	public void setSubDealerCodeModify(String subDealerCodeModify) {
		this.subDealerCodeModify = subDealerCodeModify;
	}
	//Surendra Code
	private String userId;
	
	public String getSubDealerCode() {
		return subDealerCode;
	}
	public void setSubDealerCode(String subDealerCode) {
		this.subDealerCode = subDealerCode;
	}
	public String getSubDealerDes() {
		return subDealerDes;
	}
	public void setSubDealerDes(String subDealerDes) {
		this.subDealerDes = subDealerDes;
	}
	public String getSubDealerType() {
		return subDealerType;
	}
	public void setSubDealerType(String subDealerType) {
		this.subDealerType = subDealerType;
	}
	public String getSubDealerStatus() {
		return subDealerStatus;
	}
	public void setSubDealerStatus(String subDealerStatus) {
		this.subDealerStatus = subDealerStatus;
	}
	public String getSubDealerSearchDes() {
		return subDealerSearchDes;
	}
	public void setSubDealerSearchDes(String subDealerSearchDes) {
		this.subDealerSearchDes = subDealerSearchDes;
	}
	public String getSubDealerIdModify() {
		return subDealerIdModify;
	}
	public void setSubDealerIdModify(String subDealerIdModify) {
		this.subDealerIdModify = subDealerIdModify;
	}
	private String userDesc;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	
	public String getMakerDate() {
		return makerDate;
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
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public void setLbxdealerType(String lbxdealerType) {
		this.lbxdealerType = lbxdealerType;
	}
	public String getLbxdealerType() {
		return lbxdealerType;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public String getDealerID() {
		return dealerID;
	}
	public void setDealerID(String dealerID) {
		this.dealerID = dealerID;
	}
	public String getDealerDes() {
		return dealerDes;
	}
	public void setDealerDes(String dealerDes) {
		this.dealerDes = dealerDes;
	}
	public String getSubDealerBankAC() {
		return subDealerBankAC;
	}
	public void setSubDealerBankAC(String subDealerBankAC) {
		this.subDealerBankAC = subDealerBankAC;
	}
	public String getSubDealerSearchCode() {
		return subDealerSearchCode;
	}
	public void setSubDealerSearchCode(String subDealerSearchCode) {
		this.subDealerSearchCode = subDealerSearchCode;
	}
	public String getDealerSearchID() {
		return dealerSearchID;
	}
	public void setDealerSearchID(String dealerSearchID) {
		this.dealerSearchID = dealerSearchID;
	}

	
}
