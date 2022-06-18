//Author Name : Ritu Jindal-->
//Date of Creation : -->
//Purpose  : VO for Agency Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class MasterVo implements Serializable{

	
	private String agencyCode;
	private String agencyDesc;
	private String agencyStatus;
	private String agencyType;
	private String makerId;
	private String makerDate;
	private String authorId;
	private String authorDate;
	private String agencySearchCode;
	private String agencySearchDesc;
	private String agencyCodeModify;
	private String agencyDescription;
	private String agencyValue;
	private int currentPageLink;
	private int totalRecordSize;
	private String ratioCode;
	private String ratioName;
	private String ratioCodeModify;
	private String ratioStatus;
	private String ratioStatusDesc;
	private String expression;
	private String constant;
	private String operator;
	private String parameterCode;
	
	private String ruleCode;
	private String ruleName;
	private String ruleStatus;
	private String ruleStatusDesc;
	private String ruleCodeModify;
	private String ruleType;
	private String subRuleType;
	private String ruleValue;
	private String subRuleValue;
	public String getSubRuleValue() {
		return subRuleValue;
	}
	public void setSubRuleValue(String subRuleValue) {
		this.subRuleValue = subRuleValue;
	}
	private String ruleDescription;

	private String subRuleDescription;
	public String getSubRuleDescription() {
		return subRuleDescription;
	}
	public void setSubRuleDescription(String subRuleDescription) {
		this.subRuleDescription = subRuleDescription;
	}
	private String lbxUserIds;
	private String userName;

	private String allParamCodes;

	private String sessionId;
	
	private String stageForRule;
	
	
	
	public String getStageForRule() {
		return stageForRule;
	}
	public void setStageForRule(String stageForRule) {
		this.stageForRule = stageForRule;
	}
	
	public String getSubRuleType() {
		return subRuleType;
	}
	public void setSubRuleType(String subRuleType) {
		this.subRuleType = subRuleType;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getAllParamCodes() {
		return allParamCodes;
	}
	public void setAllParamCodes(String allParamCodes) {
		this.allParamCodes = allParamCodes;
	}
	public String getRatioStatusDesc() {
		return ratioStatusDesc;
	}
	public void setRatioStatusDesc(String ratioStatusDesc) {
		this.ratioStatusDesc = ratioStatusDesc;
	}
	public String getRuleStatusDesc() {
		return ruleStatusDesc;
	}
	public void setRuleStatusDesc(String ruleStatusDesc) {
		this.ruleStatusDesc = ruleStatusDesc;
	}
	public String getRuleValue() {
		return ruleValue;
	}
	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getRuleCodeModify() {
		return ruleCodeModify;
	}
	public void setRuleCodeModify(String ruleCodeModify) {
		this.ruleCodeModify = ruleCodeModify;
	}
	public String getRuleStatus() {
		return ruleStatus;
	}
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getConstant() {
		return constant;
	}
	public void setConstant(String constant) {
		this.constant = constant;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getRatioStatus() {
		return ratioStatus;
	}
	public void setRatioStatus(String ratioStatus) {
		this.ratioStatus = ratioStatus;
	}
	public String getRatioCodeModify() {
		return ratioCodeModify;
	}
	public void setRatioCodeModify(String ratioCodeModify) {
		this.ratioCodeModify = ratioCodeModify;
	}
	public String getRatioCode() {
		return ratioCode;
	}
	public void setRatioCode(String ratioCode) {
		this.ratioCode = ratioCode;
	}
	public String getRatioName() {
		return ratioName;
	}
	public void setRatioName(String ratioName) {
		this.ratioName = ratioName;
	}
	public String getAgencyDescription() {
		return agencyDescription;
	}
	public void setAgencyDescription(String agencyDescription) {
		this.agencyDescription = agencyDescription;
	}
	public String getAgencyValue() {
		return agencyValue;
	}
	public void setAgencyValue(String agencyValue) {
		this.agencyValue = agencyValue;
	}
	private String activebox;
	
	public String getActivebox() {
		return activebox;
	}
	public void setActivebox(String activebox) {
		this.activebox = activebox;
	}



	public void setAgencyDesc(String agencyDesc) {
		this.agencyDesc = agencyDesc;
	}
	public String getAgencyDesc() {
		return agencyDesc;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyStatus(String agencyStatus) {
		this.agencyStatus = agencyStatus;
	}
	public String getAgencyStatus() {
		return agencyStatus;
	}
	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}
	public String getAgencyType() {
		return agencyType;
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
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAgencySearchCode(String agencySearchCode) {
		this.agencySearchCode = agencySearchCode;
	}
	public String getAgencySearchCode() {
		return agencySearchCode;
	}
	public void setAgencySearchDesc(String agencySearchDesc) {
		this.agencySearchDesc = agencySearchDesc;
	}
	public String getAgencySearchDesc() {
		return agencySearchDesc;
	}
	public void setAgencyCodeModify(String agencyCodeModify) {
		this.agencyCodeModify = agencyCodeModify;
	}
	public String getAgencyCodeModify() {
		return agencyCodeModify;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setLbxUserIds(String lbxUserIds) {
		this.lbxUserIds = lbxUserIds;
	}
	public String getLbxUserIds() {
		return lbxUserIds;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}


}
