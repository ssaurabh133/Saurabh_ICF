package com.webservice.cp.pdvo;
public class OmnifinPANRequest {

	private String panNumber;
	private String customerName;
	private String source;
	private String txnType;
	private String applicationFormNo;
	private Integer txnId;
	private String makerId;
	private String sourceId;

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getApplicationFormNo() {
		return applicationFormNo;
	}

	public void setApplicationFormNo(String applicationFormNo) {
		this.applicationFormNo = applicationFormNo;
	}

	public Integer getTxnId() {
		return txnId;
	}

	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Override
	public String toString() {
		return "OmnifinPANRequest [panNumber=" + panNumber + ", customerName="
				+ customerName + ", source=" + source + ", txnType=" + txnType
				+ ", applicationFormNo=" + applicationFormNo + ", txnId="
				+ txnId + ", makerId=" + makerId + ", sourceId=" + sourceId
				+ "]";
	}
	
}
