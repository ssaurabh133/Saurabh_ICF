package com.masters.vo;

import java.io.Serializable;


public class ScoringBenchmarkMasterVo implements Serializable{
	
	String weightage;
	String industry;
	String lbxIndustryID;
	String scorecard;
	String lbxScorecardID;
	String benchmarkCodeId;
	String dataType;
	String[] rating;
	String[] numericValueFrom;
	String[] numericValueTo;
	String[] characterValue;
	String rating1;
	String recStatus;
	String makerId;
	String makerDate;
	String numericValueFrom1;
	String numericValueTo1;
	String characterValue1;
	
	public String getRating1() {
		return rating1;
	}
	public void setRating1(String rating1) {
		this.rating1 = rating1;
	}
	public String getNumericValueFrom1() {
		return numericValueFrom1;
	}
	public void setNumericValueFrom1(String numericValueFrom1) {
		this.numericValueFrom1 = numericValueFrom1;
	}
	public String getNumericValueTo1() {
		return numericValueTo1;
	}
	public void setNumericValueTo1(String numericValueTo1) {
		this.numericValueTo1 = numericValueTo1;
	}
	public String getCharacterValue1() {
		return characterValue1;
	}
	public void setCharacterValue1(String characterValue1) {
		this.characterValue1 = characterValue1;
	}
		
	public String getBenchmarkCodeId() {
		return benchmarkCodeId;
	}
	public void setBenchmarkCodeId(String benchmarkCodeId) {
		this.benchmarkCodeId = benchmarkCodeId;
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
	public String getDataType() {
		return dataType;
	}
	public String[] getCharacterValue() {
		return characterValue;
	}
	public String[] getRating() {
		return rating;
	}
	public void setRating(String[] rating) {
		this.rating = rating;
	}
	public String[] getNumericValueFrom() {
		return numericValueFrom;
	}
	public void setNumericValueFrom(String[] numericValueFrom) {
		this.numericValueFrom = numericValueFrom;
	}
	public String[] getNumericValueTo() {
		return numericValueTo;
	}
	public void setNumericValueTo(String[] numericValueTo) {
		this.numericValueTo = numericValueTo;
	}
	public void setCharacterValue(String[] characterValue) {
		this.characterValue = characterValue;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getLbxIndustryID() {
		return lbxIndustryID;
	}
	public void setLbxIndustryID(String lbxIndustryID) {
		this.lbxIndustryID = lbxIndustryID;
	}
	public String getScorecard() {
		return scorecard;
	}
	public void setScorecard(String scorecard) {
		this.scorecard = scorecard;
	}
	public String getLbxScorecardID() {
		return lbxScorecardID;
	}
	public void setLbxScorecardID(String lbxScorecardID) {
		this.lbxScorecardID = lbxScorecardID;
	}
	
	
	
}