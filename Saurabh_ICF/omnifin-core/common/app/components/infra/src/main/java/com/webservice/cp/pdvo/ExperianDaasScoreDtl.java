package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class ExperianDaasScoreDtl {
	
	private String entityType;
	private String entityName;
	private BigDecimal daasScore;
	
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
	public BigDecimal getDaasScore() {
		return daasScore;
	}
	public void setDaasScore(BigDecimal daasScore) {
		this.daasScore = daasScore;
	}
	
	@Override
	public String toString() {
		return "ExperianDaasScoreDtl [entityType=" + entityType
				+ ", entityName=" + entityName + ", daasScore=" + daasScore
				+ "]";
	}
	
}
	