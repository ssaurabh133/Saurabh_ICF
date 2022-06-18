package com.cp.vo;

public class CreditProcessingDocCollectionVo
{
	private String documentName;
	private String status;
	private String receivedDate;
	private String deferredTillDate;
	private String expiryDate;
	private String remarks;
	
	
	
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getDeferredTillDate() {
		return deferredTillDate;
	}
	public void setDeferredTillDate(String deferredTillDate) {
		this.deferredTillDate = deferredTillDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
