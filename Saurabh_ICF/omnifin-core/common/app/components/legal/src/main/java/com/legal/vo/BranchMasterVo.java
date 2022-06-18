 package com.legal.vo;
 
 import java.io.Serializable;
 
 public class BranchMasterVo
   implements Serializable
 {
   private String areaCode;
   private String areaDesc;
   private String branchId;
   private String branchDesc;
   private String branchAccount;
   private String companyId;
   private String lbxCompanyID;
   private String regionId;
   private String lbxRegionID;
   private String branchStatus;
   private String MakerId;
   private String MakerDate;
   private String branchSearchDesc;
   private String branchIdModify;
   private int currentPageLink;
   private int totalRecordSize;
   private String branchShortCode;
   private String defaultcountryid;
   private String defaultcountryname;
   private String state;
   private String country;
   private String district;
   private String txtDistCode;
   private String txtCountryCode;
   private String txtStateCode;
   private String[] areaCodename;
   private String[] lbxareaCodeVal;
   private String zone;
   private String txtZoneCode;
   private String txtclusterCode;
   private String cluster;
 
   public String[] getAreaCodename()
   {
/* */     return this.areaCodename;
   }
   public void setAreaCodename(String[] areaCodename) {
/* */     this.areaCodename = areaCodename;
   }
   public String[] getLbxareaCodeVal() {
/* */     return this.lbxareaCodeVal;
   }
   public void setLbxareaCodeVal(String[] lbxareaCodeVal) {
/* */     this.lbxareaCodeVal = lbxareaCodeVal;
   }
   public String getAreaCode() {
/* */     return this.areaCode;
   }
   public void setAreaCode(String areaCode) {
/* */     this.areaCode = areaCode;
   }
   public String getAreaDesc() {
/* */     return this.areaDesc;
   }
   public void setAreaDesc(String areaDesc) {
/* */     this.areaDesc = areaDesc;
   }
   public String getState() {
/* */     return this.state;
   }
   public void setState(String state) {
/* */     this.state = state;
   }
   public String getCountry() {
/* */     return this.country;
   }
   public void setCountry(String country) {
/* */     this.country = country;
   }
   public String getTxtCountryCode() {
/* */     return this.txtCountryCode;
   }
   public void setTxtCountryCode(String txtCountryCode) {
/* */     this.txtCountryCode = txtCountryCode;
   }
   public String getTxtStateCode() {
/* */     return this.txtStateCode;
   }
   public void setTxtStateCode(String txtStateCode) {
/* */     this.txtStateCode = txtStateCode;
   }
   public String getDefaultcountryid() {
/* */     return this.defaultcountryid;
   }
   public void setDefaultcountryid(String defaultcountryid) {
/* */     this.defaultcountryid = defaultcountryid;
   }
   public String getDefaultcountryname() {
/* */     return this.defaultcountryname;
   }
   public void setDefaultcountryname(String defaultcountryname) {
    this.defaultcountryname = defaultcountryname;
   }
   public String getBranchShortCode() {
    return this.branchShortCode;
   }
   public void setBranchShortCode(String branchShortCode) {
    this.branchShortCode = branchShortCode;
   }
   public int getCurrentPageLink() {
    return this.currentPageLink;
   }
   public void setCurrentPageLink(int currentPageLink) {
    this.currentPageLink = currentPageLink;
   }
   public int getTotalRecordSize() {
    return this.totalRecordSize;
   }
   public void setTotalRecordSize(int totalRecordSize) {
    this.totalRecordSize = totalRecordSize;
   }
   public String getBranchIdModify() {
    return this.branchIdModify;
   }
   public void setBranchIdModify(String branchIdModify) {
    this.branchIdModify = branchIdModify;
   }
   public String getBranchSearchDesc() {
    return this.branchSearchDesc;
   }
   public void setBranchSearchDesc(String branchSearchDesc) {
    this.branchSearchDesc = branchSearchDesc;
   }
   public void setBranchDesc(String branchDesc) {
    this.branchDesc = branchDesc;
   }
   public String getBranchDesc() {
    return this.branchDesc;
   }
   public void setBranchAccount(String branchAccount) {
    this.branchAccount = branchAccount;
   }
   public String getBranchAccount() {
    return this.branchAccount;
   }
   public void setCompanyId(String companyId) {
    this.companyId = companyId;
   }
   public String getCompanyId() {
    return this.companyId;
   }
   public void setRegionId(String regionId) {
    this.regionId = regionId;
   }
   public String getRegionId() {
    return this.regionId;
   }
   public void setBranchStatus(String branchStatus) {
    this.branchStatus = branchStatus;
   }
   public String getBranchStatus() {
    return this.branchStatus;
   }
   public void setLbxCompanyID(String lbxCompanyID) {
    this.lbxCompanyID = lbxCompanyID;
   }
   public String getLbxCompanyID() {
    return this.lbxCompanyID;
   }
   public void setLbxRegionID(String lbxRegionID) {
    this.lbxRegionID = lbxRegionID;
   }
   public String getLbxRegionID() {
    return this.lbxRegionID;
   }
   public void setBranchId(String branchId) {
    this.branchId = branchId;
   }
   public String getBranchId() {
    return this.branchId;
   }
   public void setMakerId(String makerId) {
    this.MakerId = makerId;
   }
   public String getMakerId() {
    return this.MakerId;
   }
   public void setMakerDate(String makerDate) {
    this.MakerDate = makerDate;
   }
   public String getMakerDate() {
    return this.MakerDate;
   }
   public void setDistrict(String district) {
    this.district = district;
   }
   public String getDistrict() {
    return this.district;
   }
   public void setTxtDistCode(String txtDistCode) {
    this.txtDistCode = txtDistCode;
   }
   public String getTxtDistCode() {
    return this.txtDistCode;
   }
   public void setZone(String zone) {
    this.zone = zone;
   }
   public String getZone() {
    return this.zone;
   }
   public void setTxtZoneCode(String txtZoneCode) {
    this.txtZoneCode = txtZoneCode;
   }
   public String getTxtZoneCode() {
    return this.txtZoneCode;
   }
 
   public void setCluster(String cluster) {
    this.cluster = cluster;
   }
   public String getCluster() {
    return this.cluster;
   }
   public void setTxtclusterCode(String txtclusterCode) {
    this.txtclusterCode = txtclusterCode;
   }
   public String getTxtclusterCode() {
    return this.txtclusterCode;
   }
 }

