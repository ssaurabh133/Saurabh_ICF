package com.masters.vo;

import java.io.Serializable;

public class UsedVehiclePricingVo implements Serializable{
	private String usedVehicleMakeSearch;
	private String usedVehicleModelSearch;
	private String usedVehicleYear;
	private String usedVehiclePrice;
	private String usedVehicleState;
	private String usedVehicleBranch;
	private int currentPageLink;
	private int totalRecordSize;
	private String makerId;
	private String makerDate;
	private String usedState;
	private String usedBranch;
	private String vehicleMake;
	private String[] usedVehicleYearArr;
	private String[] usedVehiclePriceArr;
	private String[] usedVehicleStateArr;
	private String[] usedVehicleBranchArr;
	private String[] usedBranchArr;
	private String[] usedStateArr;
	private String makeModelId;
	private String authorId;
	private String authorDate;
	private String refinanceId;
	/*private String searchUsedVehiclePricing;
	private String getNoOfYearAtUsedVehicle;
	private String getUsedVehiclePricingData;
	private String selectUsedVehiclePricing;
	private String updateVehiclePricingdata;*/
	/*public String getUpdateVehiclePricingdata() {
		return updateVehiclePricingdata;
	}
	public void setUpdateVehiclePricingdata(String updateVehiclePricingdata) {
		this.updateVehiclePricingdata = updateVehiclePricingdata;
	}*/
	/*public String getSelectUsedVehiclePricing() {
		return selectUsedVehiclePricing;
	}
	public void setSelectUsedVehiclePricing(String selectUsedVehiclePricing) {
		this.selectUsedVehiclePricing = selectUsedVehiclePricing;
	}
	public String getGetUsedVehiclePricingData() {
		return getUsedVehiclePricingData;
	}
	public void setGetUsedVehiclePricingData(String getUsedVehiclePricingData) {
		this.getUsedVehiclePricingData = getUsedVehiclePricingData;
	}
	public String getGetNoOfYearAtUsedVehicle() {
		return getNoOfYearAtUsedVehicle;
	}
	public void setGetNoOfYearAtUsedVehicle(String getNoOfYearAtUsedVehicle) {
		this.getNoOfYearAtUsedVehicle = getNoOfYearAtUsedVehicle;
	}
	public String getSearchUsedVehiclePricing() {
		return searchUsedVehiclePricing;
	}
	public void setSearchUsedVehiclePricing(String searchUsedVehiclePricing) {
		this.searchUsedVehiclePricing = searchUsedVehiclePricing;
	}*/
	public String getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public String getUsedState() {
		return usedState;
	}
	public void setUsedState(String usedState) {
		this.usedState = usedState;
	}
	public String getUsedBranch() {
		return usedBranch;
	}
	public void setUsedBranch(String usedBranch) {
		this.usedBranch = usedBranch;
	}
	public void setUsedVehicleMakeSearch(String usedVehicleMakeSearch) {
		this.usedVehicleMakeSearch = usedVehicleMakeSearch;
	}
	public String getUsedVehicleMakeSearch() {
		return usedVehicleMakeSearch;
	}
	public void setUsedVehicleModelSearch(String usedVehicleModelSearch) {
		this.usedVehicleModelSearch = usedVehicleModelSearch;
	}
	public String getUsedVehicleModelSearch() {
		return usedVehicleModelSearch;
	}
	
	
	public String getUsedVehiclePrice() {
		return usedVehiclePrice;
	}
	public void setUsedVehiclePrice(String usedVehiclePrice) {
		this.usedVehiclePrice = usedVehiclePrice;
	}
	public String getUsedVehicleState() {
		return usedVehicleState;
	}
	public void setUsedVehicleState(String usedVehicleState) {
		this.usedVehicleState = usedVehicleState;
	}
	public String getUsedVehicleBranch() {
		return usedVehicleBranch;
	}
	public void setUsedVehicleBranch(String usedVehicleBranch) {
		this.usedVehicleBranch = usedVehicleBranch;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
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
	public String getUsedVehicleYear() {
		return usedVehicleYear;
	}
	public void setUsedVehicleYear(String usedVehicleYear) {
		this.usedVehicleYear = usedVehicleYear;
	}
	public String[] getUsedVehicleYearArr() {
		return usedVehicleYearArr;
	}
	public void setUsedVehicleYearArr(String[] usedVehicleYearArr) {
		this.usedVehicleYearArr = usedVehicleYearArr;
	}
	public String[] getUsedVehiclePriceArr() {
		return usedVehiclePriceArr;
	}
	public void setUsedVehiclePriceArr(String[] usedVehiclePriceArr) {
		this.usedVehiclePriceArr = usedVehiclePriceArr;
	}
	public String[] getUsedVehicleStateArr() {
		return usedVehicleStateArr;
	}
	public void setUsedVehicleStateArr(String[] usedVehicleStateArr) {
		this.usedVehicleStateArr = usedVehicleStateArr;
	}
	public String[] getUsedVehicleBranchArr() {
		return usedVehicleBranchArr;
	}
	public void setUsedVehicleBranchArr(String[] usedVehicleBranchArr) {
		this.usedVehicleBranchArr = usedVehicleBranchArr;
	}
	public void setUsedBranchArr(String[] usedBranchArr) {
		this.usedBranchArr = usedBranchArr;
	}
	public String[] getUsedBranchArr() {
		return usedBranchArr;
	}
	public void setUsedStateArr(String[] usedStateArr) {
		this.usedStateArr = usedStateArr;
	}
	public String[] getUsedStateArr() {
		return usedStateArr;
	}
	public void setMakeModelId(String makeModelId) {
		this.makeModelId = makeModelId;
	}
	public String getMakeModelId() {
		return makeModelId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public String getRefinanceId() {
		return refinanceId;
	}
	public void setRefinanceId(String refinanceId) {
		this.refinanceId = refinanceId;
	}
	
	
	
	
	
	
}
	