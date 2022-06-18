//Author Name : Ritu Jindal-->
//Date of Creation : 12 May 2011-->
//Purpose  : VO for Charge Master-->
//Documentation : -->
package com.masters.vo;

import java.io.Serializable;

	public class ChargeMasterVo implements Serializable{
		private String chargeId;
		private String lbxCharge;
		private String productId;
		private String lbxProductID;
		private String schemeId;
		private String lbxScheme;
		private String stageId;
		private String lbxStage;
		private String appStageId;
		private String lbxAppStage;
		private String chargeBPType;
		private String hBuyerSupplierBPType;
		private String chargeCode;
		private String chargeType;
		private String chargeMethod;
		private String calculatedOn;
	
		private String tdsStatus;
		private String tdsRate;
		private String taxStatus;
		private String taxInclusiveStatus;
		private String taxRate1;
		private String taxRate2;
		private String minChargeAmount;
		private String chargeStatus;
		private String chargeAmount;
		private String makerId;
		private String makerDate;
		private String lbxCalculatedOn;
		private String chargeDes;
		private String chargePercentage;
		private String chargeSearchCode;
		private String lbxChargeSearch;
		private String chargeSearchDes;
		private String productSearchId;
		private String lbxProductSearchID;
		private String schemeSearchId;
		private String lbxSchemeSearch;
		private String lbxChargeModify;
		private int currentPageLink;
		private int totalRecordSize;
		private String charge;
		private String minchargeMethod;
		private String minCalculatedOn;
		private String lbxminCalculatedOn;
		
		
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
		public String getLbxChargeModify() {
			return lbxChargeModify;
		}
		public void setLbxChargeModify(String lbxChargeModify) {
			this.lbxChargeModify = lbxChargeModify;
		}
		public String getChargeSearchCode() {
			return chargeSearchCode;
		}
		public void setChargeSearchCode(String chargeSearchCode) {
			this.chargeSearchCode = chargeSearchCode;
		}
		public String getLbxChargeSearch() {
			return lbxChargeSearch;
		}
		public void setLbxChargeSearch(String lbxChargeSearch) {
			this.lbxChargeSearch = lbxChargeSearch;
		}
		public String getChargeSearchDes() {
			return chargeSearchDes;
		}
		public void setChargeSearchDes(String chargeSearchDes) {
			this.chargeSearchDes = chargeSearchDes;
		}
		public String getProductSearchId() {
			return productSearchId;
		}
		public void setProductSearchId(String productSearchId) {
			this.productSearchId = productSearchId;
		}
		public String getLbxProductSearchID() {
			return lbxProductSearchID;
		}
		public void setLbxProductSearchID(String lbxProductSearchID) {
			this.lbxProductSearchID = lbxProductSearchID;
		}
		public String getSchemeSearchId() {
			return schemeSearchId;
		}
		public void setSchemeSearchId(String schemeSearchId) {
			this.schemeSearchId = schemeSearchId;
		}
		public String getLbxSchemeSearch() {
			return lbxSchemeSearch;
		}
		public void setLbxSchemeSearch(String lbxSchemeSearch) {
			this.lbxSchemeSearch = lbxSchemeSearch;
		}
		public void setChargeId(String chargeId) {
			this.chargeId = chargeId;
		}
		public String getChargeId() {
			return chargeId;
		}
		public void setLbxCharge(String lbxCharge) {
			this.lbxCharge = lbxCharge;
		}
		public String getLbxCharge() {
			return lbxCharge;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getProductId() {
			return productId;
		}
		public void setSchemeId(String schemeId) {
			this.schemeId = schemeId;
		}
		public String getSchemeId() {
			return schemeId;
		}
		public void setLbxScheme(String lbxScheme) {
			this.lbxScheme = lbxScheme;
		}
		public String getLbxScheme() {
			return lbxScheme;
		}
		public void setStageId(String stageId) {
			this.stageId = stageId;
		}
		public String getStageId() {
			return stageId;
		}
		public void setLbxStage(String lbxStage) {
			this.lbxStage = lbxStage;
		}
		public String getLbxStage() {
			return lbxStage;
		}
		public void setAppStageId(String appStageId) {
			this.appStageId = appStageId;
		}
		public String getAppStageId() {
			return appStageId;
		}
		public void setLbxAppStage(String lbxAppStage) {
			this.lbxAppStage = lbxAppStage;
		}
		public String getLbxAppStage() {
			return lbxAppStage;
		}
		public void setChargeBPType(String chargeBPType) {
			this.chargeBPType = chargeBPType;
		}
		public String getChargeBPType() {
			return chargeBPType;
		}
		public void setChargeCode(String chargeCode) {
			this.chargeCode = chargeCode;
		}
		public String getChargeCode() {
			return chargeCode;
		}
		public void setChargeType(String chargeType) {
			this.chargeType = chargeType;
		}
		public String getChargeType() {
			return chargeType;
		}
		public void setChargeMethod(String chargeMethod) {
			this.chargeMethod = chargeMethod;
		}
		public String getChargeMethod() {
			return chargeMethod;
		}
		public void setCalculatedOn(String calculatedOn) {
			this.calculatedOn = calculatedOn;
		}
		public String getCalculatedOn() {
			return calculatedOn;
		}
		public void setTdsStatus(String tdsStatus) {
			this.tdsStatus = tdsStatus;
		}
		public String getTdsStatus() {
			return tdsStatus;
		}
		public void setTdsRate(String tdsRate) {
			this.tdsRate = tdsRate;
		}
		public String getTdsRate() {
			return tdsRate;
		}
		public void setTaxStatus(String taxStatus) {
			this.taxStatus = taxStatus;
		}
		public String getTaxStatus() {
			return taxStatus;
		}
		public void setTaxInclusiveStatus(String taxInclusiveStatus) {
			this.taxInclusiveStatus = taxInclusiveStatus;
		}
		public String getTaxInclusiveStatus() {
			return taxInclusiveStatus;
		}
		public void setTaxRate1(String taxRate1) {
			this.taxRate1 = taxRate1;
		}
		public String getTaxRate1() {
			return taxRate1;
		}
		public void setTaxRate2(String taxRate2) {
			this.taxRate2 = taxRate2;
		}
		public String getTaxRate2() {
			return taxRate2;
		}
		public void setMinChargeAmount(String minChargeAmount) {
			this.minChargeAmount = minChargeAmount;
		}
		public String getMinChargeAmount() {
			return minChargeAmount;
		}
		public void setChargeStatus(String chargeStatus) {
			this.chargeStatus = chargeStatus;
		}
		public String getChargeStatus() {
			return chargeStatus;
		}
		public void setChargeAmount(String chargeAmount) {
			this.chargeAmount = chargeAmount;
		}
		public String getChargeAmount() {
			return chargeAmount;
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
		public void setLbxProductID(String lbxProductID) {
			this.lbxProductID = lbxProductID;
		}
		public String getLbxProductID() {
			return lbxProductID;
		}
		public void sethBuyerSupplierBPType(String hBuyerSupplierBPType) {
			this.hBuyerSupplierBPType = hBuyerSupplierBPType;
		}
		public String gethBuyerSupplierBPType() {
			return hBuyerSupplierBPType;
		}
		public void setLbxCalculatedOn(String lbxCalculatedOn) {
			this.lbxCalculatedOn = lbxCalculatedOn;
		}
		public String getLbxCalculatedOn() {
			return lbxCalculatedOn;
		}
		public void setChargeDes(String chargeDes) {
			this.chargeDes = chargeDes;
		}
		public String getChargeDes() {
			return chargeDes;
		}
		public void setChargePercentage(String chargePercentage) {
			this.chargePercentage = chargePercentage;
		}
		public String getChargePercentage() {
			return chargePercentage;
		}
		public void setCharge(String charge) {
			this.charge = charge;
		}
		public String getCharge() {
			return charge;
		}
		public void setMinchargeMethod(String minchargeMethod) {
			this.minchargeMethod = minchargeMethod;
		}
		public String getMinchargeMethod() {
			return minchargeMethod;
		}
		public void setLbxminCalculatedOn(String lbxminCalculatedOn) {
			this.lbxminCalculatedOn = lbxminCalculatedOn;
		}
		public String getLbxminCalculatedOn() {
			return lbxminCalculatedOn;
		}
		public void setMinCalculatedOn(String minCalculatedOn) {
			this.minCalculatedOn = minCalculatedOn;
		}
		public String getMinCalculatedOn() {
			return minCalculatedOn;
		}
	

	}


