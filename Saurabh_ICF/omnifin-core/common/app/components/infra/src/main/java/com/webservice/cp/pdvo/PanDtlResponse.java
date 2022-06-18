package com.webservice.cp.pdvo;
public class PanDtlResponse {
	private Integer panId;
	private String  source;
	private String  sourceId;
	private Integer  txnId;
	private String  txnType;
	private String  initiatedBy;
	private String  applicationFormNo;
	private String  customerName;
	private String  panNo;
	private String  initiationDate;
	private String  acknowledgementId;
	private String  applicationId;
	private String  requestReceivedTime;
	private String  responseType;
	private String  txnStatus;
	private String  txnError;
	private String  nsdlStatus;
	private String  nsdlError;
	private String  panStatus;
	private String  nsdlPanNo;
	private String  nsdlFirstName;
	private String  nsdlMiddleName;
	private String  nsdlLastName;
	private String  nsdlPanTitle;
	private String  nsdlLastUpdateDate;
	private String  responseDate;
	private String nameOnCard;
	
	
	
	
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public Integer getPanId() {
		return panId;
	}
	public void setPanId(Integer panId) {
		this.panId = panId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getTxnId() {
		return txnId;
	}
	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getInitiatedBy() {
		return initiatedBy;
	}
	public void setInitiatedBy(String initiatedBy) {
		this.initiatedBy = initiatedBy;
	}
	public String getApplicationFormNo() {
		return applicationFormNo;
	}
	public void setApplicationFormNo(String applicationFormNo) {
		this.applicationFormNo = applicationFormNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getInitiationDate() {
		return initiationDate;
	}
	public void setInitiationDate(String initiationDate) {
		this.initiationDate = initiationDate;
	}
	public String getAcknowledgementId() {
		return acknowledgementId;
	}
	public void setAcknowledgementId(String acknowledgementId) {
		this.acknowledgementId = acknowledgementId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getRequestReceivedTime() {
		return requestReceivedTime;
	}
	public void setRequestReceivedTime(String requestReceivedTime) {
		this.requestReceivedTime = requestReceivedTime;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getTxnError() {
		return txnError;
	}
	public void setTxnError(String txnError) {
		this.txnError = txnError;
	}
	public String getNsdlStatus() {
		return nsdlStatus;
	}
	public void setNsdlStatus(String nsdlStatus) {
		this.nsdlStatus = nsdlStatus;
	}
	public String getNsdlError() {
		return nsdlError;
	}
	public void setNsdlError(String nsdlError) {
		this.nsdlError = nsdlError;
	}
	public String getPanStatus() {
		return panStatus;
	}
	public void setPanStatus(String panStatus) {
		this.panStatus = panStatus;
	}
	public String getNsdlPanNo() {
		return nsdlPanNo;
	}
	public void setNsdlPanNo(String nsdlPanNo) {
		this.nsdlPanNo = nsdlPanNo;
	}
	public String getNsdlFirstName() {
		return nsdlFirstName;
	}
	public void setNsdlFirstName(String nsdlFirstName) {
		this.nsdlFirstName = nsdlFirstName;
	}
	public String getNsdlMiddleName() {
		return nsdlMiddleName;
	}
	public void setNsdlMiddleName(String nsdlMiddleName) {
		this.nsdlMiddleName = nsdlMiddleName;
	}
	public String getNsdlLastName() {
		return nsdlLastName;
	}
	public void setNsdlLastName(String nsdlLastName) {
		this.nsdlLastName = nsdlLastName;
	}
	public String getNsdlPanTitle() {
		return nsdlPanTitle;
	}
	public void setNsdlPanTitle(String nsdlPanTitle) {
		this.nsdlPanTitle = nsdlPanTitle;
	}
	public String getNsdlLastUpdateDate() {
		return nsdlLastUpdateDate;
	}
	public void setNsdlLastUpdateDate(String nsdlLastUpdateDate) {
		this.nsdlLastUpdateDate = nsdlLastUpdateDate;
	}
	public String getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}
	@Override
	public String toString() {
		return "PanDtlResponse [panId=" + panId + ", source=" + source
				+ ", sourceId=" + sourceId + ", txnId=" + txnId + ", txnType="
				+ txnType + ", initiatedBy=" + initiatedBy
				+ ", applicationFormNo=" + applicationFormNo
				+ ", customerName=" + customerName + ", panNo=" + panNo
				+ ", initiationDate=" + initiationDate + ", acknowledgementId="
				+ acknowledgementId + ", applicationId=" + applicationId
				+ ", requestReceivedTime=" + requestReceivedTime
				+ ", responseType=" + responseType + ", txnStatus=" + txnStatus
				+ ", txnError=" + txnError + ", nsdlStatus=" + nsdlStatus
				+ ", nsdlError=" + nsdlError + ", panStatus=" + panStatus
				+ ", nsdlPanNo=" + nsdlPanNo + ", nsdlFirstName="
				+ nsdlFirstName + ", nsdlMiddleName=" + nsdlMiddleName
				+ ", nsdlLastName=" + nsdlLastName + ", nsdlPanTitle="
				+ nsdlPanTitle + ", nsdlLastUpdateDate=" + nsdlLastUpdateDate
				+ ", responseDate=" + responseDate + "]";
	}
}
