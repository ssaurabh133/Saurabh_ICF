//Author Name : Vinod Kumar Gupta-->
//Date of Creation : 30-04-2013 -->
//Purpose  : VO for Repo Detail Vo-->
//Documentation : -->

package com.repo.vo;

import java.io.Serializable;

/*
 * @author A3S
 *
 */
public class RepoDetailVo implements Serializable{
	
	private String lbxLoanId;
	private String searchRepoId;
	private String searchLoanDesc;
	private String saveForwardFlag;
	private String repoId;
	private String loanId;
	private String loanNo;	
	private String customerId;
	private String customerName;
	private String productId;
	private String productDesc;
	private String schemeDesc;
	private String assetClass;
	private String assetDesc;
	private String assetCost;
	private String repossessedBy;
	private String agencyId;
	private String agencyName;
	private String repoAdd1;
	private String repoAdd2;
	private String lbxState;
	private String stateDesc;
	private String lbxDistrict;
	private String districtDesc;
	private String repoFrom;
	private String relationToCustomer;
	private String repoDate;
	private String repoTime;
	private String lbxStockYard;
	private String stockYardDesc;
	private String agencyRemarks;
	private String checkList;
	private String checkListStatus;
	private String checkListRemarks;
	private	String makerId;
	private String makerDate;
	private	String autherId;
	private String autherDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String branchId;
	
	
	
	
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getLbxLoanId() {
		return lbxLoanId;
	}
	public void setLbxLoanId(String lbxLoanId) {
		this.lbxLoanId = lbxLoanId;
	}
	public String getSaveForwardFlag() {
		return saveForwardFlag;
	}
	public void setSaveForwardFlag(String saveForwardFlag) {
		this.saveForwardFlag = saveForwardFlag;
	}
	public String getCheckList() {
		return checkList;
	}
	public void setCheckList(String checkList) {
		this.checkList = checkList;
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
	public String getSearchRepoId() {
		return searchRepoId;
	}
	public void setSearchRepoId(String searchRepoId) {
		this.searchRepoId = searchRepoId;
	}
	public String getSearchLoanDesc() {
		return searchLoanDesc;
	}
	public void setSearchLoanDesc(String searchLoanDesc) {
		this.searchLoanDesc = searchLoanDesc;
	}
	public String getRepoId() {
		return repoId;
	}
	public void setRepoId(String repoId) {
		this.repoId = repoId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getSchemeDesc() {
		return schemeDesc;
	}
	public void setSchemeDesc(String schemeDesc) {
		this.schemeDesc = schemeDesc;
	}
	public String getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}
	public String getAssetDesc() {
		return assetDesc;
	}
	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
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
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getRepoAdd1() {
		return repoAdd1;
	}
	public void setRepoAdd1(String repoAdd1) {
		this.repoAdd1 = repoAdd1;
	}
	public String getRepoAdd2() {
		return repoAdd2;
	}
	public void setRepoAdd2(String repoAdd2) {
		this.repoAdd2 = repoAdd2;
	}
	public String getLbxState() {
		return lbxState;
	}
	public void setLbxState(String lbxState) {
		this.lbxState = lbxState;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public String getLbxDistrict() {
		return lbxDistrict;
	}
	public void setLbxDistrict(String lbxDistrict) {
		this.lbxDistrict = lbxDistrict;
	}
	public String getDistrictDesc() {
		return districtDesc;
	}
	public void setDistrictDesc(String districtDesc) {
		this.districtDesc = districtDesc;
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
	public String getLbxStockYard() {
		return lbxStockYard;
	}
	public void setLbxStockYard(String lbxStockYard) {
		this.lbxStockYard = lbxStockYard;
	}
	public String getStockYardDesc() {
		return stockYardDesc;
	}
	public void setStockYardDesc(String stockYardDesc) {
		this.stockYardDesc = stockYardDesc;
	}
	public String getAgencyRemarks() {
		return agencyRemarks;
	}
	public void setAgencyRemarks(String agencyRemarks) {
		this.agencyRemarks = agencyRemarks;
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
