package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdResidenceStandardLivingDtl {

	private Integer id;

	private Integer pdId;

	private String assetInHouse;

	private Integer assetPurchaseYear;

	private String landlineNo;

	private BigDecimal purchaseCost;

	private String purchaseFinancedCost;

	private String fiName;

	private String makerDate;

	private String makerId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getAssetInHouse() {
		return assetInHouse;
	}

	public void setAssetInHouse(String assetInHouse) {
		this.assetInHouse = assetInHouse;
	}

	public Integer getAssetPurchaseYear() {
		return assetPurchaseYear;
	}

	public void setAssetPurchaseYear(Integer assetPurchaseYear) {
		this.assetPurchaseYear = assetPurchaseYear;
	}

	public String getLandlineNo() {
		return landlineNo;
	}

	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}

	public BigDecimal getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(BigDecimal purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public String getPurchaseFinancedCost() {
		return purchaseFinancedCost;
	}

	public void setPurchaseFinancedCost(String purchaseFinancedCost) {
		this.purchaseFinancedCost = purchaseFinancedCost;
	}

	public String getFiName() {
		return fiName;
	}

	public void setFiName(String fiName) {
		this.fiName = fiName;
	}

	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	@Override
	public String toString() {
		return "CrPdResidenceStandardLivingDtl [id=" + id + ", pdId=" + pdId
				+ ", assetInHouse=" + assetInHouse + ", assetPurchaseYear="
				+ assetPurchaseYear + ", landlineNo=" + landlineNo
				+ ", purchaseCost=" + purchaseCost + ", purchaseFinancedCost="
				+ purchaseFinancedCost + ", fiName=" + fiName + ", makerDate="
				+ makerDate + ", makerId=" + makerId + "]";
	}
}
