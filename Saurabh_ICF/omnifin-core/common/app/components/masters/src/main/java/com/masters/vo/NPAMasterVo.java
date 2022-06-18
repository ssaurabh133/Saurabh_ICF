package com.masters.vo;

import java.io.Serializable;

public class NPAMasterVo implements Serializable{
	
	private String npaSearchStage;
	private String sequenceNoSearch;
	private String npaStage;
	private String sequenceNo;
	private String npaCriteria;
	private String npaCriteriaValue;
	private String moveToNext;
	private String moveToPrevious;
	private String billingFlagStatus;
	private String accrualFlagStatus;
	private String npaStageStatus;
	private String makerId;
	private String makerDate;
	private String npaStageModify;
	private String npaCriteriaModify;
	private String moveToNextModify;
	private String moveToPreviousModify;
	private int currentPageLink;
	private int totalRecordSize;
	private String sdAccrualFlag;
	private String productId;
	private String proDesc;
	
	
	public String getSdAccrualFlag() {
		return sdAccrualFlag;
	}
	public void setSdAccrualFlag(String sdAccrualFlag) {
		this.sdAccrualFlag = sdAccrualFlag;
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
	public String getNpaCriteriaModify() {
		return npaCriteriaModify;
	}
	public void setNpaCriteriaModify(String npaCriteriaModify) {
		this.npaCriteriaModify = npaCriteriaModify;
	}
	public String getMoveToNextModify() {
		return moveToNextModify;
	}
	public void setMoveToNextModify(String moveToNextModify) {
		this.moveToNextModify = moveToNextModify;
	}
	public String getMoveToPreviousModify() {
		return moveToPreviousModify;
	}
	public void setMoveToPreviousModify(String moveToPreviousModify) {
		this.moveToPreviousModify = moveToPreviousModify;
	}
	public String getNpaSearchStage() {
		return npaSearchStage;
	}
	public void setNpaSearchStage(String npaSearchStage) {
		this.npaSearchStage = npaSearchStage;
	}
	public String getSequenceNoSearch() {
		return sequenceNoSearch;
	}
	public void setSequenceNoSearch(String sequenceNoSearch) {
		this.sequenceNoSearch = sequenceNoSearch;
	}
	public String getNpaStage() {
		return npaStage;
	}
	public void setNpaStage(String npaStage) {
		this.npaStage = npaStage;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getNpaCriteria() {
		return npaCriteria;
	}
	public void setNpaCriteria(String npaCriteria) {
		this.npaCriteria = npaCriteria;
	}
	public String getNpaCriteriaValue() {
		return npaCriteriaValue;
	}
	public void setNpaCriteriaValue(String npaCriteriaValue) {
		this.npaCriteriaValue = npaCriteriaValue;
	}
	public String getMoveToNext() {
		return moveToNext;
	}
	public void setMoveToNext(String moveToNext) {
		this.moveToNext = moveToNext;
	}
	public String getMoveToPrevious() {
		return moveToPrevious;
	}
	public void setMoveToPrevious(String moveToPrevious) {
		this.moveToPrevious = moveToPrevious;
	}
	public String getBillingFlagStatus() {
		return billingFlagStatus;
	}
	public void setBillingFlagStatus(String billingFlagStatus) {
		this.billingFlagStatus = billingFlagStatus;
	}
	
	public String getNpaStageStatus() {
		return npaStageStatus;
	}
	public void setNpaStageStatus(String npaStageStatus) {
		this.npaStageStatus = npaStageStatus;
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
	public void setAccrualFlagStatus(String accrualFlagStatus) {
		this.accrualFlagStatus = accrualFlagStatus;
	}
	public String getAccrualFlagStatus() {
		return accrualFlagStatus;
	}
	public void setNpaStageModify(String npaStageModify) {
		this.npaStageModify = npaStageModify;
	}
	public String getNpaStageModify() {
		return npaStageModify;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProDesc() {
		return proDesc;
	}
	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}
	
}
