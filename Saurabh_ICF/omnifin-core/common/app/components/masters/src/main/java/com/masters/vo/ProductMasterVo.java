package com.masters.vo;

import java.io.Serializable;

public class ProductMasterVo implements Serializable{
	private String productId;
	private String productDes;
	private String productCategory;
	private String repaymentType;
	private String revolvingFlag;
	private String recStatus;
	private String assetFlag;
	private String makerId;
	private String makerDate;
	private String daysBasis;
	private String daysPerYear;
	private String insRound;
	private String insRounding;
	private String intRounType;
	private String intRounding;
	private String opportunityRate;
	private String productSearchDes;
	private String productSearchId;
	private String productIdModify;

	private int currentPageLink;
	private int totalRecordSize;

	private String repaymentTypeFlag;
	private String asset;
	private String category;
	private String categoryDesc;
	private String oneDealOneLoan;
	private String productLoanType;
	private String assetMandatoryAtDeal;
	private String interestAdvance;
	private String productName;
	private String singleBorrowerLimit;
	private String groupBorrowerLimit;
	private String lbxProductID;
	private String product;

	
	public String getAssetMandatoryAtDeal() {
		return assetMandatoryAtDeal;
	}
	public void setAssetMandatoryAtDeal(String assetMandatoryAtDeal) {
		this.assetMandatoryAtDeal = assetMandatoryAtDeal;
	}
	public String getOneDealOneLoan() {
		return oneDealOneLoan;
	}
	public void setOneDealOneLoan(String oneDealOneLoan) {
		this.oneDealOneLoan = oneDealOneLoan;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
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

	public String getRepaymentTypeFlag() {
		return repaymentTypeFlag;
	}
	public void setRepaymentTypeFlag(String repaymentTypeFlag) {
		this.repaymentTypeFlag = repaymentTypeFlag;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getProductIdModify() {
		return productIdModify;
	}
	public void setProductIdModify(String productIdModify) {
		this.productIdModify = productIdModify;
	}
	public String getDaysBasis() {
		return daysBasis;
	}
	public void setDaysBasis(String daysBasis) {
		this.daysBasis = daysBasis;
	}
	public String getDaysPerYear() {
		return daysPerYear;
	}
	public void setDaysPerYear(String daysPerYear) {
		this.daysPerYear = daysPerYear;
	}
	public String getInsRound() {
		return insRound;
	}
	public void setInsRound(String insRound) {
		this.insRound = insRound;
	}
	public String getInsRounding() {
		return insRounding;
	}
	public void setInsRounding(String insRounding) {
		this.insRounding = insRounding;
	}
	public String getIntRounType() {
		return intRounType;
	}
	public void setIntRounType(String intRounType) {
		this.intRounType = intRounType;
	}
	public String getIntRounding() {
		return intRounding;
	}
	public void setIntRounding(String intRounding) {
		this.intRounding = intRounding;
	}
	public String getOpportunityRate() {
		return opportunityRate;
	}
	public void setOpportunityRate(String opportunityRate) {
		this.opportunityRate = opportunityRate;
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
	public void setProductDes(String productDes) {
		this.productDes = productDes;
	}
	public String getProductDes() {
		return productDes;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRevolvingFlag(String revolvingFlag) {
		this.revolvingFlag = revolvingFlag;
	}
	public String getRevolvingFlag() {
		return revolvingFlag;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductId() {
		return productId;
	}
	public void setAssetFlag(String assetFlag) {
		this.assetFlag = assetFlag;
	}
	public String getAssetFlag() {
		return assetFlag;
	}
	public void setProductSearchDes(String productSearchDes) {
		this.productSearchDes = productSearchDes;
	}
	public String getProductSearchDes() {
		return productSearchDes;
	}
	public void setProductSearchId(String productSearchId) {
		this.productSearchId = productSearchId;
	}
	public String getProductSearchId() {
		return productSearchId;
	}
	public String getProductLoanType() {
		return productLoanType;
	}
	public void setProductLoanType(String productLoanType) {
		this.productLoanType = productLoanType;
	}
	public String getInterestAdvance() {
		return interestAdvance;
	}
	public void setInterestAdvance(String interestAdvance) {
		this.interestAdvance = interestAdvance;
	}
		//pooja code Starts for SBL & GBL master
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getLbxProductID() {
		return lbxProductID;
	}
	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSingleBorrowerLimit() {
		return singleBorrowerLimit;
	}
	public void setSingleBorrowerLimit(String singleBorrowerLimit) {
		this.singleBorrowerLimit = singleBorrowerLimit;
	}
	public String getGroupBorrowerLimit() {
		return groupBorrowerLimit;
	}
	public void setGroupBorrowerLimit(String groupBorrowerLimit) {
		this.groupBorrowerLimit = groupBorrowerLimit;
	}
}
