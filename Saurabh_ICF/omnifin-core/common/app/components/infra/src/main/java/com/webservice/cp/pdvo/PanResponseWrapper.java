package com.webservice.cp.pdvo;
public class PanResponseWrapper {
	private String operationMessage;
	private String operationStatus;
	private PanDtlResponse panDetail;
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
	public PanDtlResponse getPanDetail() {
		return panDetail;
	}
	public void setPanDetail(PanDtlResponse panDetail) {
		this.panDetail = panDetail;
	}
	@Override
	public String toString() {
		return "PanResponseWrapper [operationMessage=" + operationMessage
				+ ", operationStatus=" + operationStatus + ", panDetail="
				+ panDetail + "]";
	}
}
