package com.communication.engn.vo;

public class EmailVO {

	private String to;
	private String From;
	private String Bcc;
	private String customerEmail;
	private String customerName;
	private String subject;
	private String port;
	
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEmailSub() {
		return emailSub;
	}
	public void setEmailSub(String emailSub) {
		this.emailSub = emailSub;
	}
	private String varOneEvent;
	private String filePath;
	private String smtpHost;
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	private String smtpMailAddress;
	private String smtpMailPassword;
	private String[] emailList;
	private String emailSubjectTxt;
	private String emailFromAddress;
	private String eventName;
	private String emailSub;
	private String eventNo;
	private String massage;
	private int recordId;
	
	
	public String getMassage() {
		return massage;
	}
	public void setMassage(String massage) {
		this.massage = massage;
	}
	public String getEmailSubjectTxt() {
		return emailSubjectTxt;
	}
	public String getEventNo() {
		return eventNo;
	}
	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}
	public void setEmailSubjectTxt(String emailSubjectTxt) {
		this.emailSubjectTxt = emailSubjectTxt;
	}
	public String getEmailFromAddress() {
		return emailFromAddress;
	}
	public void setEmailFromAddress(String emailFromAddress) {
		this.emailFromAddress = emailFromAddress;
	}
	public String[] getEmailList() {
		return emailList;
	}
	public void setEmailList(String[] emailList) {
		this.emailList = emailList;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getSmtpMailAddress() {
		return smtpMailAddress;
	}
	public void setSmtpMailAddress(String smtpMailAddress) {
		this.smtpMailAddress = smtpMailAddress;
	}
	public String getSmtpMailPassword() {
		return smtpMailPassword;
	}
	public void setSmtpMailPassword(String smtpMailPassword) {
		this.smtpMailPassword = smtpMailPassword;
	}
	public String getVarOneEvent() {
		return varOneEvent;
	}
	public void setVarOneEvent(String varOneEvent) {
		this.varOneEvent = varOneEvent;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getVarOne() {
		return varOneEvent;
	}
	public void setVarOne(String varOneEvent) {
		this.varOneEvent = varOneEvent;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return From;
	}
	public void setFrom(String from) {
		From = from;
	}
	public String getBcc() {
		return Bcc;
	}
	public void setBcc(String bcc) {
		Bcc = bcc;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	
}
