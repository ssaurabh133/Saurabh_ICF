package com.masters.vo;

import java.io.Serializable;

public class FinancialAnalysisVo implements Serializable{
	private String dealNo;
	private String lbxDealNo;
	private String financialYear;
	private String sourceType;
	private String parameCode;
	private String paramValue;
	private String userId;
	private String businessDate;
	private String lbxparameCode;
	private String currBusinessDateYear;
	private String ratioExpr;
	private String financialId;
	private String[] financialIds;
	private String[] pCode;
	private String[] year1;
	private String[] year2;
	private String[] year3;
	private String[] year4;
	private String[] year5;
	
	private String paramCodes;
	private String firstYear;
	private String secondYear;
	private String thirdYear;
	private String fourthYear;
	private String fifthYear;
	
	private String update;
	
	private String ratioFirstYear;
	private String ratioSecondYear;
	private String ratioThirdYear;
	private String ratioFourthYear;
	private String ratioFifthYear;
	private String ratioParamCode;
	
	private String[] month;
	private String[] year;
	
	private String monthValue;
	private String yearValue;
	
	public String getMonthValue() {
		return monthValue;
	}
	public void setMonthValue(String monthValue) {
		this.monthValue = monthValue;
	}
	public String getYearValue() {
		return yearValue;
	}
	public void setYearValue(String yearValue) {
		this.yearValue = yearValue;
	}
	public String[] getMonth() {
		return month;
	}
	public void setMonth(String[] month) {
		this.month = month;
	}
	public String[] getYear() {
		return year;
	}
	public void setYear(String[] year) {
		this.year = year;
	}
	public String getFinancialId() {
		return financialId;
	}
	public void setFinancialId(String financialId) {
		this.financialId = financialId;
	}
	public String[] getFinancialIds() {
		return financialIds;
	}
	public void setFinancialIds(String[] financialIds) {
		this.financialIds = financialIds;
	}
	public String getRatioParamCode() {
		return ratioParamCode;
	}
	public void setRatioParamCode(String ratioParamCode) {
		this.ratioParamCode = ratioParamCode;
	}
	public String getRatioFirstYear() {
		return ratioFirstYear;
	}
	public void setRatioFirstYear(String ratioFirstYear) {
		this.ratioFirstYear = ratioFirstYear;
	}
	public String getRatioSecondYear() {
		return ratioSecondYear;
	}
	public void setRatioSecondYear(String ratioSecondYear) {
		this.ratioSecondYear = ratioSecondYear;
	}
	public String getRatioThirdYear() {
		return ratioThirdYear;
	}
	public void setRatioThirdYear(String ratioThirdYear) {
		this.ratioThirdYear = ratioThirdYear;
	}
	public String getRatioFourthYear() {
		return ratioFourthYear;
	}
	public void setRatioFourthYear(String ratioFourthYear) {
		this.ratioFourthYear = ratioFourthYear;
	}
	public String getRatioFifthYear() {
		return ratioFifthYear;
	}
	public void setRatioFifthYear(String ratioFifthYear) {
		this.ratioFifthYear = ratioFifthYear;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getRatioExpr() {
		return ratioExpr;
	}
	public void setRatioExpr(String ratioExpr) {
		this.ratioExpr = ratioExpr;
	}
	public String getParamCodes() {
		return paramCodes;
	}
	public void setParamCodes(String paramCodes) {
		this.paramCodes = paramCodes;
	}
	public String getFirstYear() {
		return firstYear;
	}
	public void setFirstYear(String firstYear) {
		this.firstYear = firstYear;
	}
	public String getSecondYear() {
		return secondYear;
	}
	public void setSecondYear(String secondYear) {
		this.secondYear = secondYear;
	}
	public String getThirdYear() {
		return thirdYear;
	}
	public void setThirdYear(String thirdYear) {
		this.thirdYear = thirdYear;
	}
	public String getFourthYear() {
		return fourthYear;
	}
	public void setFourthYear(String fourthYear) {
		this.fourthYear = fourthYear;
	}
	public String getFifthYear() {
		return fifthYear;
	}
	public void setFifthYear(String fifthYear) {
		this.fifthYear = fifthYear;
	}
	public String getCurrBusinessDateYear() {
		return currBusinessDateYear;
	}
	public void setCurrBusinessDateYear(String currBusinessDateYear) {
		this.currBusinessDateYear = currBusinessDateYear;
	}
	public String[] getpCode() {
		return pCode;
	}
	public void setpCode(String[] pCode) {
		this.pCode = pCode;
	}
	public String[] getYear1() {
		return year1;
	}
	public void setYear1(String[] year1) {
		this.year1 = year1;
	}
	public String[] getYear2() {
		return year2;
	}
	public void setYear2(String[] year2) {
		this.year2 = year2;
	}
	public String[] getYear3() {
		return year3;
	}
	public void setYear3(String[] year3) {
		this.year3 = year3;
	}
	public String[] getYear4() {
		return year4;
	}
	public void setYear4(String[] year4) {
		this.year4 = year4;
	}
	public String[] getYear5() {
		return year5;
	}
	public void setYear5(String[] year5) {
		this.year5 = year5;
	}
	public String getLbxparameCode() {
		return lbxparameCode;
	}
	public void setLbxparameCode(String lbxparameCode) {
		this.lbxparameCode = lbxparameCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getLbxDealNo() {
		return lbxDealNo;
	}
	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getParameCode() {
		return parameCode;
	}
	public void setParameCode(String parameCode) {
		this.parameCode = parameCode;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
}
