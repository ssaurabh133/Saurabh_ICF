package com.caps.actionForm;

import org.apache.struts.action.ActionForm;



public class PaymentDetailsForm extends ActionForm{

	private static final long serialVersionUID = 1L;
	private String paymentDate;
	private String paymentAmount;
	private String paymentMode;
	private int currentPageLink;
	private int TotalRecordSize;

	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return TotalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		TotalRecordSize = totalRecordSize;
	}
}
