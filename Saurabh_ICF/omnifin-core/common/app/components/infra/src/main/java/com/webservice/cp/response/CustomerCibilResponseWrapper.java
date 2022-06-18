package com.webservice.cp.response;

import com.webservice.cp.cibilvo.CustomerCibilDetail;

public class CustomerCibilResponseWrapper {
	
	private String operationStatus;
	
	private String operationMessage;
	
	private CustomerCibilDetail cibilDetail;

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

	public CustomerCibilDetail getCibilDetail() {
		return cibilDetail;
	}

	public void setCibilDetail(CustomerCibilDetail cibilDetail) {
		this.cibilDetail = cibilDetail;
	}

	@Override
	public String toString() {
		return "CustomerCibilResponseWrapper [operationStatus="
				+ operationStatus + ", operationMessage=" + operationMessage
				+ ", cibilDetail=" + cibilDetail + "]";
	}
	
}
