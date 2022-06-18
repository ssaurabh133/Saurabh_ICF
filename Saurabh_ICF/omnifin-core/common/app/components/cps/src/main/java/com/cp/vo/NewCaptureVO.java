package com.cp.vo;

public class NewCaptureVO 
{
	private String customer_id;
	private String Customer_name;
	private String applicant_type;
	public String getVerificationId() {
		return verificationId;
	}
	public void setVerificationId(String verificationId) {
		this.verificationId = verificationId;
	}
	private String verificationSubType;
	private String status;
	private String verificationId;
	public String getVerificationSubType() {
		return verificationSubType;
	}
	public void setVerificationSubType(String verificationSubType) {
		this.verificationSubType = verificationSubType;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customerId) {
		customer_id = customerId;
	}
	public String getCustomer_name() {
		return Customer_name;
	}
	public void setCustomer_name(String customerName) {
		Customer_name = customerName;
	}
	public String getApplicant_type() {
		return applicant_type;
	}
	public void setApplicant_type(String applicantType) {
		applicant_type = applicantType;
	}
	

}
