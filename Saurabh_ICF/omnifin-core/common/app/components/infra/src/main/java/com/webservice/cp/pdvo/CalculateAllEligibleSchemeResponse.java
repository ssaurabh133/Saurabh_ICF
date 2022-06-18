package com.webservice.cp.pdvo;

public class CalculateAllEligibleSchemeResponse {
	
	private String operationMessage;
	private String operationStatus;
	private String eligibleScheme;
	public String getOperationMessage() {
		return operationMessage;
	}
	public void setOperationMessage(String operationMessage) {
		this.operationMessage = operationMessage;
	}
	public String getOperationStatus() {
		return operationStatus;
	}
	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
	public String getEligibleScheme() {
		return eligibleScheme;
	}
	public void setEligibleScheme(String eligibleScheme) {
		this.eligibleScheme = eligibleScheme;
	}
	@Override
	public String toString() {
		return "CalculateAllEligibleSchemeResponse [operationMessage="
				+ operationMessage + ", operationStatus=" + operationStatus
				+ ", eligibleScheme=" + eligibleScheme + "]";
	}
}
