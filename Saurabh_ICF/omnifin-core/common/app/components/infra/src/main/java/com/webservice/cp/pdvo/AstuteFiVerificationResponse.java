package com.webservice.cp.pdvo;


public class AstuteFiVerificationResponse {
	

	String NotificationId;
	
	String Status;

	String Message;
	
	Integer CaseId;
	
	Integer StatusId;

	public String getNotificationId() {
		return NotificationId;
	}

	public void setNotificationId(String notificationId) {
		NotificationId = notificationId;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Integer getCaseId() {
		return CaseId;
	}

	public void setCaseId(Integer caseId) {
		CaseId = caseId;
	}

	public Integer getStatusId() {
		return StatusId;
	}

	public void setStatusId(Integer statusId) {
		StatusId = statusId;
	}

	@Override
	public String toString() {
		return "AstuteFiVerificationResponse [NotificationId=" + NotificationId
				+ ", Status=" + Status + ", Message=" + Message + ", CaseId="
				+ CaseId + ", StatusId=" + StatusId + "]";
	}
}
