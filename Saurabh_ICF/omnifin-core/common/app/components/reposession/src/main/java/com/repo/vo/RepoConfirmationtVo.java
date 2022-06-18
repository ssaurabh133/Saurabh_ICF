//Author Name : Nazia-->
//Date of Creation : 13 Mar 2013-->
//Purpose  : VO for notice Master-->
//Documentation : -->

package com.repo.vo;

import java.io.Serializable;

public class RepoConfirmationtVo implements Serializable{
	
	
	private String searchRepoId;
	private String lbxLoanIdSearch;
	private String loanNo;
	private String loanId;
	private String repoId;
	private String customerName;
	private String assetDescription;
	private String productDescription;
	private String assetDesc;
	private String scheme;
	private String assetCost;
	private String repossessedBy;
	private String agencyName;
	private String repoAddress1;
	private String repoAddress2;
	private String state;
	private String district;
	private String repoFrom;
	private String repoDate;
	private String relationToCustomer;
	private String stockyard;
	private String stockyardEntryDate;
	private String stockyardManagerName;
	private String stockyardEntryTime;
	private String agencyRemarks;
	private String stockyardRemarks;
	private String repoTime;
	private String assetChecklist;
	private String checkListStatus;
	private String checkListRemarks;
	private String product;
	private String productId;
	private String assetId;
	private String comments;
	private String decision;
	private String recStatus;
	private	String autherId;
	private String autherDate;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String branchId;
	
	
	
	
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
	public String getSearchRepoId() {
		return searchRepoId;
	}
	public void setSearchRepoId(String searchRepoId) {
		this.searchRepoId = searchRepoId;
	}
	public String getLbxLoanIdSearch() {
		return lbxLoanIdSearch;
	}
	public void setLbxLoanIdSearch(String lbxLoanIdSearch) {
		this.lbxLoanIdSearch = lbxLoanIdSearch;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
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
	public String getRepoId() {
		return repoId;
	}
	public void setRepoId(String repoId) {
		this.repoId = repoId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAssetDescription() {
		return assetDescription;
	}
	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getAssetDesc() {
		return assetDesc;
	}
	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
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
	public String getRepossessedBy() {
		return repossessedBy;
	}
	public void setRepossessedBy(String repossessedBy) {
		this.repossessedBy = repossessedBy;
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
	public String getRepoDate() {
		return repoDate;
	}
	public void setRepoDate(String repoDate) {
		this.repoDate = repoDate;
	}
	public String getRelationToCustomer() {
		return relationToCustomer;
	}
	public void setRelationToCustomer(String relationToCustomer) {
		this.relationToCustomer = relationToCustomer;
	}
	public String getStockyard() {
		return stockyard;
	}
	public void setStockyard(String stockyard) {
		this.stockyard = stockyard;
	}
	public String getStockyardEntryDate() {
		return stockyardEntryDate;
	}
	public void setStockyardEntryDate(String stockyardEntryDate) {
		this.stockyardEntryDate = stockyardEntryDate;
	}
	public String getStockyardManagerName() {
		return stockyardManagerName;
	}
	public void setStockyardManagerName(String stockyardManagerName) {
		this.stockyardManagerName = stockyardManagerName;
	}
	public String getStockyardEntryTime() {
		return stockyardEntryTime;
	}
	public void setStockyardEntryTime(String stockyardEntryTime) {
		this.stockyardEntryTime = stockyardEntryTime;
	}
	public String getAgencyRemarks() {
		return agencyRemarks;
	}
	public void setAgencyRemarks(String agencyRemarks) {
		this.agencyRemarks = agencyRemarks;
	}
	public String getStockyardRemarks() {
		return stockyardRemarks;
	}
	public void setStockyardRemarks(String stockyardRemarks) {
		this.stockyardRemarks = stockyardRemarks;
	}
	public String getRepoTime() {
		return repoTime;
	}
	public void setRepoTime(String repoTime) {
		this.repoTime = repoTime;
	}
	public String getAssetChecklist() {
		return assetChecklist;
	}
	public void setAssetChecklist(String assetChecklist) {
		this.assetChecklist = assetChecklist;
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
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
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
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getAutherId() {
		return autherId;
	}
	public void setAutherId(String autherId) {
		this.autherId = autherId;
	}
	public String getAutherDate() {
		return autherDate;
	}
	public void setAutherDate(String autherDate) {
		this.autherDate = autherDate;
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
	
	
	
	
	