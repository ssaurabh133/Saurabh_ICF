package com.masters.vo;

import java.io.Serializable;

public class UserApprovalMatrixVo implements Serializable{
	private String userId;
	private String role;
	private String level;
	private String amountFrom;
	private String amountTo;
	private String lbxUserId;
	private String recStatus;
	private String makerId;
	private String makerDate;
	private String authorId;
	private String authorDate;
	private int totalRecordSize;
	private int currentPageLink;
	private String checkBoxDis;
	private String branchName;
	private String productName;
	private String subRuleType;
	private String subRuleTypeP;
	private String statusCase;
	private String decison;
	private String textArea;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAmountFrom() {
		return amountFrom;
	}
	public void setAmountFrom(String amountFrom) {
		this.amountFrom = amountFrom;
	}
	public String getAmountTo() {
		return amountTo;
	}
	public void setAmountTo(String amountTo) {
		this.amountTo = amountTo;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
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
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
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
	public String getCheckBoxDis() {
		return checkBoxDis;
	}
	public void setCheckBoxDis(String checkBoxDis) {
		this.checkBoxDis = checkBoxDis;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSubRuleType() {
		return subRuleType;
	}
	public void setSubRuleType(String subRuleType) {
		this.subRuleType = subRuleType;
	}
	public String getSubRuleTypeP() {
		return subRuleTypeP;
	}
	public void setSubRuleTypeP(String subRuleTypeP) {
		this.subRuleTypeP = subRuleTypeP;
	}
	public void setStatusCase(String statusCase) {
		this.statusCase = statusCase;
	}
	public String getStatusCase() {
		return statusCase;
	}
	public void setDecison(String decison) {
		this.decison = decison;
	}
	public String getDecison() {
		return decison;
	}
	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}
	public String getTextArea() {
		return textArea;
	}

}
