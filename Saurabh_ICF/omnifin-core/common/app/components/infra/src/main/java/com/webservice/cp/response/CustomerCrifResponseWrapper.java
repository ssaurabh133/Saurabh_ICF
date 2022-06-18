package com.webservice.cp.response;

import com.webservice.cp.crifvo.*;

public class CustomerCrifResponseWrapper {
	
	private String operationStatus;
	
	private String operationMessage;
	
	private CustomerCrifDetail crifDetail;

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

	public CustomerCrifDetail getCrifDetail() {
		return crifDetail;
	}

	public void setCrifDetail(CustomerCrifDetail crifDetail) {
		this.crifDetail = crifDetail;
	}

	@Override
	public String toString() {
		return "CustomerCibilResponseWrapper [operationStatus="
				+ operationStatus + ", operationMessage=" + operationMessage
				+ ", cibilDetail=" + crifDetail + "]";
	}
	
}
