package com.masters.vo;

import java.io.Serializable;

public class ManulaDeviationVO implements Serializable
{
	 private String product;
     private String lbxProductID;
	 private String scheme;
	 private String lbxSchemeID;
	 private String stage;
	 private String lbxPCDStage;
	 private String ruleDescription;
	 private String status;
	 private String currentPageLink;
	 private String makerId;
	 private String makerDate;
	 private String manualid;
	 private int totalRecordSize; 
	 
	 private String deviationType;
	 private String ruleAction;
	 private String approvalLevel;
	 private String subRuleType;
	 
	
	 
	 public String getSubRuleType() {
		return subRuleType;
	}
	public void setSubRuleType(String subRuleType) {
		this.subRuleType = subRuleType;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getManualid() {
		return manualid;
	}
	public void setManualid(String manualid) {
		this.manualid = manualid;
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
	

	public String getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(String currentPageLink) {
		this.currentPageLink = currentPageLink;
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
	
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getRuleDescription() {
		return ruleDescription;
	}
	public String getLbxProductID() {
		return lbxProductID;
	}
	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}
	public String getLbxSchemeID() {
		return lbxSchemeID;
	}
	public void setLbxSchemeID(String lbxSchemeID) {
		this.lbxSchemeID = lbxSchemeID;
	}

	public String getLbxPCDStage() {
		return lbxPCDStage;
	}
	public void setLbxPCDStage(String lbxPCDStage) {
		this.lbxPCDStage = lbxPCDStage;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeviationType() {
		return deviationType;
	}
	public void setDeviationType(String deviationType) {
		this.deviationType = deviationType;
	}
	public String getRuleAction() {
		return ruleAction;
	}
	public void setRuleAction(String ruleAction) {
		this.ruleAction = ruleAction;
	}
	public String getApprovalLevel() {
		return approvalLevel;
	}
	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}
	
	
	
}
