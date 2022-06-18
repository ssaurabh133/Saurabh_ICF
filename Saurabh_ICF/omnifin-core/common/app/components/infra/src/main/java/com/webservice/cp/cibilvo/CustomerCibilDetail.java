package com.webservice.cp.cibilvo;


public class CustomerCibilDetail{

	private Integer cibilId;
	
	private Integer trackingId;
	
	private String bureau;
	
	private String product;
	
	private String initiationDate;
	
	private Integer cibilScore;
	
	private String match;
	
	private String acknowledgementId;
	
	private String applicationId;
	
	private String responseType;
	
	private String custId;
	
	private String requestReceivedTime;
	
	private String status;
	
	private String enquiryStatus;
	
	private String cibilReportPath;
	
	private String makerDate;
	
	private String bureauString;
	
	private String finalResponse;
	
	private String finalResponseReceived;
	
	private Integer documentId;
	
	private String rankScore;
	
	private String rankName;

	private String pdfStream;


	private String noEnquiriesWithInABFL;
	

	private String noEnquiriesOtherThenABFL;
	

	private String totalAccounts;
	
	private String totalOverdueAccount;
	
	private String sanctionAmount;
	
	private String overdueBalance;
	
	private String currentBalance;
	
	private String noOfEnqueries;
	
	
	public String getNoEnquiriesWithInABFL() {
		return noEnquiriesWithInABFL;
	}

	public void setNoEnquiriesWithInABFL(String noEnquiriesWithInABFL) {
		this.noEnquiriesWithInABFL = noEnquiriesWithInABFL;
	}

	public String getNoEnquiriesOtherThenABFL() {
		return noEnquiriesOtherThenABFL;
	}

	public void setNoEnquiriesOtherThenABFL(String noEnquiriesOtherThenABFL) {
		this.noEnquiriesOtherThenABFL = noEnquiriesOtherThenABFL;
	}

	public String getTotalAccounts() {
		return totalAccounts;
	}

	public void setTotalAccounts(String totalAccounts) {
		this.totalAccounts = totalAccounts;
	}

	public String getTotalOverdueAccount() {
		return totalOverdueAccount;
	}

	public void setTotalOverdueAccount(String totalOverdueAccount) {
		this.totalOverdueAccount = totalOverdueAccount;
	}

	public String getSanctionAmount() {
		return sanctionAmount;
	}

	public void setSanctionAmount(String sanctionAmount) {
		this.sanctionAmount = sanctionAmount;
	}

	public String getOverdueBalance() {
		return overdueBalance;
	}

	public void setOverdueBalance(String overdueBalance) {
		this.overdueBalance = overdueBalance;
	}

	public String getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getNoOfEnqueries() {
		return noOfEnqueries;
	}

	public void setNoOfEnqueries(String noOfEnqueries) {
		this.noOfEnqueries = noOfEnqueries;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public Integer getCibilId() {
		return cibilId;
	}

	public void setCibilId(Integer cibilId) {
		this.cibilId = cibilId;
	}

	public Integer getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
	}

	public String getBureau() {
		return bureau;
	}

	public void setBureau(String bureau) {
		this.bureau = bureau;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getCibilScore() {
		return cibilScore;
	}

	public void setCibilScore(Integer cibilScore) {
		this.cibilScore = cibilScore;
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

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRequestReceivedTime() {
		return requestReceivedTime;
	}

	public void setRequestReceivedTime(String requestReceivedTime) {
		this.requestReceivedTime = requestReceivedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEnquiryStatus() {
		return enquiryStatus;
	}

	public void setEnquiryStatus(String enquiryStatus) {
		this.enquiryStatus = enquiryStatus;
	}

	public String getCibilReportPath() {
		return cibilReportPath;
	}

	public void setCibilReportPath(String cibilReportPath) {
		this.cibilReportPath = cibilReportPath;
	}

	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public String getBureauString() {
		return bureauString;
	}

	public void setBureauString(String bureauString) {
		this.bureauString = bureauString;
	}

	public String getFinalResponse() {
		return finalResponse;
	}

	public void setFinalResponse(String finalResponse) {
		this.finalResponse = finalResponse;
	}

	public String getFinalResponseReceived() {
		return finalResponseReceived;
	}

	public void setFinalResponseReceived(String finalResponseReceived) {
		this.finalResponseReceived = finalResponseReceived;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getRankScore() {
		return rankScore;
	}

	public void setRankScore(String rankScore) {
		this.rankScore = rankScore;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getPdfStream() {
		return pdfStream;
	}

	public void setPdfStream(String pdfStream) {
		this.pdfStream = pdfStream;
	}

	public String getInitiationDate() {
		return initiationDate;
	}

	public void setInitiationDate(String initiationDate) {
		this.initiationDate = initiationDate;
	}

	@Override
	public String toString() {
		return "CustomerCibilDetail [cibilId=" + cibilId + ", trackingId="
				+ trackingId + ", bureau=" + bureau + ", product=" + product
				+ ", initiationDate=" + initiationDate + ", cibilScore="
				+ cibilScore + ", match=" + match + ", acknowledgementId="
				+ acknowledgementId + ", applicationId=" + applicationId
				+ ", responseType=" + responseType + ", custId=" + custId
				+ ", requestReceivedTime=" + requestReceivedTime + ", status="
				+ status + ", enquiryStatus=" + enquiryStatus
				+ ", cibilReportPath=" + cibilReportPath + ", makerDate="
				+ makerDate + ", bureauString=" + bureauString
				+ ", finalResponse=" + finalResponse
				+ ", finalResponseReceived=" + finalResponseReceived
				+ ", documentId=" + documentId + ", rankScore=" + rankScore
				+ ", rankName=" + rankName + ", pdfStream=" + pdfStream
				+ ", noEnquiriesWithInABFL=" + noEnquiriesWithInABFL
				+ ", noEnquiriesOtherThenABFL=" + noEnquiriesOtherThenABFL
				+ ", totalAccounts=" + totalAccounts + ", totalOverdueAccount="
				+ totalOverdueAccount + ", sanctionAmount=" + sanctionAmount
				+ ", overdueBalance=" + overdueBalance + ", currentBalance="
				+ currentBalance + ", noOfEnqueries=" + noOfEnqueries + "]";
	}
	
}