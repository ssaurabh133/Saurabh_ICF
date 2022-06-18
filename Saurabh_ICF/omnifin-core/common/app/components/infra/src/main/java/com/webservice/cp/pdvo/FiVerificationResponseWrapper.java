package com.webservice.cp.pdvo;


public class FiVerificationResponseWrapper {
	String operationStatus;
	String operationMessage;
	AstuteFiVerificationResponseWrapper astuteFiVerificationResponseWrapper;
	
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
	
	public AstuteFiVerificationResponseWrapper getAstuteFiVerificationResponseWrapper() {
		return astuteFiVerificationResponseWrapper;
	}
	public void setAstuteFiVerificationResponseWrapper(
			AstuteFiVerificationResponseWrapper astuteFiVerificationResponseWrapper) {
		this.astuteFiVerificationResponseWrapper = astuteFiVerificationResponseWrapper;
	}
	@Override
	public String toString() {
		return "FiVerificationResponseWrapper [operationStatus="
				+ operationStatus + ", operationMessage=" + operationMessage
				+ ", astuteFiVerificationResponseWrapper="
				+ astuteFiVerificationResponseWrapper + "]";
	}
}
