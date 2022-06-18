package com.webservice.cp.pdvo;

import java.util.List;

public class ExperianDaasResponseWrapper {
	
	private String operationStatus;
	private String operationMessage;
	private Integer daasId;
	private List<ExperianDaasScoreDtl>scoreDtl;
	private List<ExperianDaasPolicyDtl>policyDtl;
	
	
	public String getOperationStatus() {
		return operationStatus;
	}
	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
	public String getOperationMessage() {
		return operationMessage;
	}
	public void setOperationMessage(String operationMessage) {
		this.operationMessage = operationMessage;
	}
	public Integer getDaasId() {
		return daasId;
	}
	public void setDaasId(Integer daasId) {
		this.daasId = daasId;
	}
	
	public List<ExperianDaasScoreDtl> getScoreDtl() {
		return scoreDtl;
	}
	public void setScoreDtl(List<ExperianDaasScoreDtl> scoreDtl) {
		this.scoreDtl = scoreDtl;
	}
	public List<ExperianDaasPolicyDtl> getPolicyDtl() {
		return policyDtl;
	}
	public void setPolicyDtl(List<ExperianDaasPolicyDtl> policyDtl) {
		this.policyDtl = policyDtl;
	}
	@Override
	public String toString() {
		return "ExperianDaasResponseWrapper [operationStatus="
				+ operationStatus + ", operationMessage=" + operationMessage
				+ ", daasId=" + daasId + ", scoreDtl=" + scoreDtl
				+ ", policyDtl=" + policyDtl + "]";
	}
}
	