package com.webservice.cp.pdvo;
import java.util.List;

public class PdFetchResponse {
	
	private String operationMessage;
	private String operationStatus;
	private List<CrPdDtl> crPdDtlList;
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
	public List<CrPdDtl> getCrPdDtlList() {
		return crPdDtlList;
	}
	public void setCrPdDtlList(List<CrPdDtl> crPdDtlList) {
		this.crPdDtlList = crPdDtlList;
	}
	@Override
	public String toString() {
		return "PdFetchResponse [operationMessage=" + operationMessage
				+ ", operationStatus=" + operationStatus + ", crPdDtlList="
				+ crPdDtlList + "]";
	}
}
