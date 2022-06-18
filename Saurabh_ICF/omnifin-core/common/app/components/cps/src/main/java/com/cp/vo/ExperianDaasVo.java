package com.cp.vo;

import java.io.Serializable;

public class ExperianDaasVo implements Serializable {

	private static final long serialVersionUID = -6162775202272331602L;
	
	private int dealId;
	private int experianDaasId;
	private String entityType;
	private String entityName;
	private String ruleCode;
	private String ruleDescription;
	private String ruleResult;
	private String customerRole;
	private String customerName;
	private String score;
	
	public String getCustomerRole() {
		return customerRole;
	}
	public void setCustomerRole(String customerRole) {
		this.customerRole = customerRole;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public int getDealId() {
		return dealId;
	}
	public void setDealId(int dealId) {
		this.dealId = dealId;
	}
	public int getExperianDaasId() {
		return experianDaasId;
	}
	public void setExperianDaasId(int experianDaasId) {
		this.experianDaasId = experianDaasId;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public String getRuleResult() {
		return ruleResult;
	}
	public void setRuleResult(String ruleResult) {
		this.ruleResult = ruleResult;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ExperianDaasVo [dealId=" + dealId + ", experianDaasId="
				+ experianDaasId + ", entityType=" + entityType
				+ ", entityName=" + entityName + ", ruleCode=" + ruleCode
				+ ", ruleDescription=" + ruleDescription + ", ruleResult="
				+ ruleResult + ", customerRole=" + customerRole
				+ ", customerName=" + customerName + ", score=" + score + "]";
	}
}
