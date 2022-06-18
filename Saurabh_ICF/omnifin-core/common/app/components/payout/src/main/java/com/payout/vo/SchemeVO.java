package com.payout.vo;

import java.io.Serializable;

public class SchemeVO implements Serializable{
	private String searchNarration;
	private String searchSchemeName;
	 private String recStatus;
	 private String makerId;
	 private String makerDate;
	 private int totalRecordSize;
	 private int currentPageLink;
	 private String schemeId;
	 private String schemeName;
	 private String narration;
	 private String effectiveDate;
	 private String schemeParameter;
	 private String serviceTaxApp;
	 private String tdsApp;
	 private String districtId;
	 private String tat;
	 private String cpDistCode;
	 private String[] caseFrom;
	 private String[] caseTo;
	 private String[] caseFromP;
	 private String[] caseToP ;
	 private String[] commissionR;
	 private String[] commissionP;
	 private String[] commissionA;
	 private String[] caseFromA;
	 private String[] caseToA ;
	 private String commissionPerCaseR;
	 private String commissionPerCaseP;
	
	 private String caseFromStr;
	 private String caseToStr;
	 private String commissionRStr;
	 private String commissionPStr;
	 private String slabOn;
	 
	 
	public String[] getCaseFromA() {
		return caseFromA;
	}
	public void setCaseFromA(String[] caseFromA) {
		this.caseFromA = caseFromA;
	}
	public String[] getCaseToA() {
		return caseToA;
	}
	public void setCaseToA(String[] caseToA) {
		this.caseToA = caseToA;
	}
	public String[] getCommissionA() {
		return commissionA;
	}
	public void setCommissionA(String[] commissionA) {
		this.commissionA = commissionA;
	}
	public String getSlabOn() {
		return slabOn;
	}
	public void setSlabOn(String slabOn) {
		this.slabOn = slabOn;
	}
	public String[] getCaseFromP() {
		return caseFromP;
	}
	public void setCaseFromP(String[] caseFromP) {
		this.caseFromP = caseFromP;
	}
	public String[] getCaseToP() {
		return caseToP;
	}
	public void setCaseToP(String[] caseToP) {
		this.caseToP = caseToP;
	}
	public String getCaseFromStr() {
		return caseFromStr;
	}
	public void setCaseFromStr(String caseFromStr) {
		this.caseFromStr = caseFromStr;
	}
	public String getCaseToStr() {
		return caseToStr;
	}
	public void setCaseToStr(String caseToStr) {
		this.caseToStr = caseToStr;
	}
	public String getCommissionRStr() {
		return commissionRStr;
	}
	public void setCommissionRStr(String commissionRStr) {
		this.commissionRStr = commissionRStr;
	}
	public String getCommissionPStr() {
		return commissionPStr;
	}
	public void setCommissionPStr(String commissionPStr) {
		this.commissionPStr = commissionPStr;
	}
	public String getCommissionPerCaseR() {
		return commissionPerCaseR;
	}
	public void setCommissionPerCaseR(String commissionPerCaseR) {
		this.commissionPerCaseR = commissionPerCaseR;
	}
	public String getCommissionPerCaseP() {
		return commissionPerCaseP;
	}
	public void setCommissionPerCaseP(String commissionPerCaseP) {
		this.commissionPerCaseP = commissionPerCaseP;
	}
	public String getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getSchemeParameter() {
		return schemeParameter;
	}
	public void setSchemeParameter(String schemeParameter) {
		this.schemeParameter = schemeParameter;
	}
	public String getServiceTaxApp() {
		return serviceTaxApp;
	}
	public void setServiceTaxApp(String serviceTaxApp) {
		this.serviceTaxApp = serviceTaxApp;
	}
	public String getTdsApp() {
		return tdsApp;
	}
	public void setTdsApp(String tdsApp) {
		this.tdsApp = tdsApp;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getTat() {
		return tat;
	}
	public void setTat(String tat) {
		this.tat = tat;
	}
	public String getCpDistCode() {
		return cpDistCode;
	}
	public void setCpDistCode(String cpDistCode) {
		this.cpDistCode = cpDistCode;
	}
	public String[] getCaseFrom() {
		return caseFrom;
	}
	public void setCaseFrom(String[] caseFrom) {
		this.caseFrom = caseFrom;
	}
	public String[] getCaseTo() {
		return caseTo;
	}
	public void setCaseTo(String[] caseTo) {
		this.caseTo = caseTo;
	}
	public String[] getCommissionR() {
		return commissionR;
	}
	public void setCommissionR(String[] commissionR) {
		this.commissionR = commissionR;
	}
	public String[] getCommissionP() {
		return commissionP;
	}
	public void setCommissionP(String[] commissionP) {
		this.commissionP = commissionP;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
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
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	
	public String getSearchNarration() {
		return searchNarration;
	}
	public void setSearchNarration(String searchNarration) {
		this.searchNarration = searchNarration;
	}
	public String getSearchSchemeName() {
		return searchSchemeName;
	}
	public void setSearchSchemeName(String searchSchemeName) {
		this.searchSchemeName = searchSchemeName;
	}
	
}
