//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.repo.vo;

import java.io.Serializable;

public class RepoDetailStockyardVo implements Serializable{
	
	private String loanNo;
	private String loanId;
	private String customer;
	private String assetDescription;
	private String product;
	private String scheme;
	private String assetCost;
	private String repossesedBy;
	private String agencyName;
	private String repoAddress1;
	private String repoAddress2;
	private String state;
	private String district;
	private String repoFrom;
	private String relationToCustomer;
	private String repoDate;
	private String repoTime;
	private String stockyard;
	private String stockyardManager;
	private int currentPageLink;
	private int totalRecordSize;
	private String repoId;
	private String updateForwardFlag;
	private String assetChecklist;
	private String checkListStatus;
	private String checkListRemarks;
	private	String makerId;
	private String makerDate;
	private String productId;
	private String assetId;
	private String lbxLoanIdSearch;
	private String searchRepoId;
	private String stockyardEntryDate;
	private String stockyardEntryTime;
	private String stockyardRemarks;
	private String branchId;
	
	
	
	
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getStockyardEntryDate() {
		return stockyardEntryDate;
	}
	public void setStockyardEntryDate(String stockyardEntryDate) {
		this.stockyardEntryDate = stockyardEntryDate;
	}
	public String getStockyardEntryTime() {
		return stockyardEntryTime;
	}
	public void setStockyardEntryTime(String stockyardEntryTime) {
		this.stockyardEntryTime = stockyardEntryTime;
	}
	public String getStockyardRemarks() {
		return stockyardRemarks;
	}
	public void setStockyardRemarks(String stockyardRemarks) {
		this.stockyardRemarks = stockyardRemarks;
	}
	public String getLbxLoanIdSearch() {
		return lbxLoanIdSearch;
	}
	public void setLbxLoanIdSearch(String lbxLoanIdSearch) {
		this.lbxLoanIdSearch = lbxLoanIdSearch;
	}
	public String getSearchRepoId() {
		return searchRepoId;
	}
	public void setSearchRepoId(String searchRepoId) {
		this.searchRepoId = searchRepoId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
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
	public String getCheckListStatus() {
		return checkListStatus;
	}
	public void setCheckListStatus(String checkListStatus) {
		this.checkListStatus = checkListStatus;
	}
	public String getCheckListRemarks() {
		return checkListRemarks;
	}
	public void setCheckListRemarks(String checkListRemarks) {
		this.checkListRemarks = checkListRemarks;
	}
	public String getAssetChecklist() {
		return assetChecklist;
	}
	public void setAssetChecklist(String assetChecklist) {
		this.assetChecklist = assetChecklist;
	}	
	public String getUpdateForwardFlag() {
		return updateForwardFlag;
	}
	public void setUpdateForwardFlag(String updateForwardFlag) {
		this.updateForwardFlag = updateForwardFlag;
	}
	public String getRepoId() {
		return repoId;
	}
	public void setRepoId(String repoId) {
		this.repoId = repoId;
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
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getAssetDescription() {
		return assetDescription;
	}
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getAssetCost() {
		return assetCost;
	}
	public void setAssetCost(String assetCost) {
		this.assetCost = assetCost;
	}
	public String getRepossesedBy() {
		return repossesedBy;
	}
	public void setRepossesedBy(String repossesedBy) {
		this.repossesedBy = repossesedBy;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getRepoAddress1() {
		return repoAddress1;
	}
	public void setRepoAddress1(String repoAddress1) {
		this.repoAddress1 = repoAddress1;
	}
	public String getRepoAddress2() {
		return repoAddress2;
	}
	public void setRepoAddress2(String repoAddress2) {
		this.repoAddress2 = repoAddress2;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getRepoFrom() {
		return repoFrom;
	}
	public void setRepoFrom(String repoFrom) {
		this.repoFrom = repoFrom;
	}
	public String getRelationToCustomer() {
		return relationToCustomer;
	}
	public void setRelationToCustomer(String relationToCustomer) {
		this.relationToCustomer = relationToCustomer;
	}
	public String getRepoDate() {
		return repoDate;
	}
	public void setRepoDate(String repoDate) {
		this.repoDate = repoDate;
	}
	public String getRepoTime() {
		return repoTime;
	}
	public void setRepoTime(String repoTime) {
		this.repoTime = repoTime;
	}
	public String getStockyard() {
		return stockyard;
	}
	public void setStockyard(String stockyard) {
		this.stockyard = stockyard;
	}
	public String getStockyardManager() {
		return stockyardManager;
	}
	public void setStockyardManager(String stockyardManager) {
		this.stockyardManager = stockyardManager;
	}

	
	

}
