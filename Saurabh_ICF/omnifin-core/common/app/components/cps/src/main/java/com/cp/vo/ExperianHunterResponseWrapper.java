package com.cp.vo;

public class ExperianHunterResponseWrapper {
	
	private String operationStatus;
	private String operationMessage;
	private Integer applicantMatchFound;
	private Integer jointMatchFound;
	private Integer hunterId;
	
	public Integer getHunterId() {
		return hunterId;
	}

	public void setHunterId(Integer hunterId) {
		this.hunterId = hunterId;
	}

	public Integer getApplicantMatchFound() {
		return applicantMatchFound;
	}

	public void setApplicantMatchFound(Integer applicantMatchFound) {
		this.applicantMatchFound = applicantMatchFound;
	}

	public Integer getJointMatchFound() {
		return jointMatchFound;
	}

	public void setJointMatchFound(Integer jointMatchFound) {
		this.jointMatchFound = jointMatchFound;
	}

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

	@Override
	public String toString() {
		return "ExperianHunterResponseWrapper [operationStatus="
				+ operationStatus + ", operationMessage=" + operationMessage
				+ ", applicantMatchFound=" + applicantMatchFound
				+ ", jointMatchFound=" + jointMatchFound + ", hunterId="
				+ hunterId + "]";
	}
}
	