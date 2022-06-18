package com.masters.vo;

import java.io.Serializable;

public class ScoringMasterVO implements Serializable{
	private String scoringId;
	private String scoreingDesc;
	private String product;
	private String lbxProductID;
	private String lbxSchemeId;
	private String scheme;
	private String scoringParam[];
	private String weightage[];
	private String defaultValue[];
	private String recStatus;	
	private String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String scoringParamStr;
	private String weightageStr;
	private String defaultValueStr;
	private String searchScoringDesc;
	private String scoringParamCode;
	private String  lbxScoringParam[];
	private String  from[];
	private String  to[];
	private String  charValue[];
	private String  score[];
	private String  fromStr;
	private String  toStr;
	private String  charValueStr;
	private String  scoreStr;	
	private String  dataType;		
	
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String[] getFrom() {
		return from;
	}
	public void setFrom(String[] from) {
		this.from = from;
	}
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public String[] getCharValue() {
		return charValue;
	}
	public void setCharValue(String[] charValue) {
		this.charValue = charValue;
	}
	public String[] getScore() {
		return score;
	}
	public void setScore(String[] score) {
		this.score = score;
	}
	public String getFromStr() {
		return fromStr;
	}
	public void setFromStr(String fromStr) {
		this.fromStr = fromStr;
	}
	public String getToStr() {
		return toStr;
	}
	public void setToStr(String toStr) {
		this.toStr = toStr;
	}
	public String getCharValueStr() {
		return charValueStr;
	}
	public void setCharValueStr(String charValueStr) {
		this.charValueStr = charValueStr;
	}
	public String getScoreStr() {
		return scoreStr;
	}
	public void setScoreStr(String scoreStr) {
		this.scoreStr = scoreStr;
	}
	public String[] getLbxScoringParam() {
		return lbxScoringParam;
	}
	public void setLbxScoringParam(String[] lbxScoringParam) {
		this.lbxScoringParam = lbxScoringParam;
	}
	public String getScoringParamCode() {
		return scoringParamCode;
	}
	public void setScoringParamCode(String scoringParamCode) {
		this.scoringParamCode = scoringParamCode;
	}
	public String getSearchScoringDesc() {
		return searchScoringDesc;
	}
	public void setSearchScoringDesc(String searchScoringDesc) {
		this.searchScoringDesc = searchScoringDesc;
	}
	public String getScoringParamStr() {
		return scoringParamStr;
	}
	public void setScoringParamStr(String scoringParamStr) {
		this.scoringParamStr = scoringParamStr;
	}
	public String getWeightageStr() {
		return weightageStr;
	}
	public void setWeightageStr(String weightageStr) {
		this.weightageStr = weightageStr;
	}
	public String getDefaultValueStr() {
		return defaultValueStr;
	}
	public void setDefaultValueStr(String defaultValueStr) {
		this.defaultValueStr = defaultValueStr;
	}
	public String[] getScoringParam() {
		return scoringParam;
	}
	public void setScoringParam(String[] scoringParam) {
		this.scoringParam = scoringParam;
	}
	public String[] getWeightage() {
		return weightage;
	}
	public void setWeightage(String[] weightage) {
		this.weightage = weightage;
	}
	public String[] getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String[] defaultValue) {
		this.defaultValue = defaultValue;
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


	public String getScoringId() {
		return scoringId;
	}
	public void setScoringId(String scoringId) {
		this.scoringId = scoringId;
	}
	public String getScoreingDesc() {
		return scoreingDesc;
	}
	public void setScoreingDesc(String scoreingDesc) {
		this.scoreingDesc = scoreingDesc;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getLbxProductID() {
		return lbxProductID;
	}
	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}
	public String getLbxSchemeId() {
		return lbxSchemeId;
	}
	public void setLbxSchemeId(String lbxSchemeId) {
		this.lbxSchemeId = lbxSchemeId;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	
	
	
}
