package com.webservice.cp.pdvo;

//import org.codehaus.jackson.annotate.JsonProperty;


public class AstuteFiVerificationReOpenRequest {
//	@JsonProperty("NotificationId")
	String notificationId;
	
	//@JsonProperty("ReasonForReopenCase")
	String reasonForReopenCase;

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getReasonForReopenCase() {
		return reasonForReopenCase;
	}

	public void setReasonForReopenCase(String reasonForReopenCase) {
		this.reasonForReopenCase = reasonForReopenCase;
	}

	@Override
	public String toString() {
		return "AstuteFiVerificationReOpenRequest [notificationId="
				+ notificationId + ", reasonForReopenCase="
				+ reasonForReopenCase + "]";
	}
}
