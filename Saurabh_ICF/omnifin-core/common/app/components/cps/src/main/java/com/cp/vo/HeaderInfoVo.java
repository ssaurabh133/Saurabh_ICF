package com.cp.vo;

import java.io.Serializable;

public class HeaderInfoVo implements Serializable{

	private String dealId;
	private String dealNo;
	private String dealCustomerName;
	private String dealDate;
	private String dealProduct;
	private String dealProductCat;
	private String dealScheme;
	private String dealCurrentStage;
	
	private String loanId;
	private String loanNo;
	private String dealLoanNo;
	private String loanCustomerName;
	private String loanDate;
	private String loanProduct;
	private String loanProductCat;
	private String loanScheme;
	private String dealCustomerId;
	private String loanAmount;
	
	private String recStatus;
	private String schemeMinAmt;
	private String schemeMaxAmt;
	
	
	
	
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getSchemeMinAmt() {
		return schemeMinAmt;
	}
	public void setSchemeMinAmt(String schemeMinAmt) {
		this.schemeMinAmt = schemeMinAmt;
	}
	public String getSchemeMaxAmt() {
		return schemeMaxAmt;
	}
	public void setSchemeMaxAmt(String schemeMaxAmt) {
		this.schemeMaxAmt = schemeMaxAmt;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getDealLoanNo() {
		return dealLoanNo;
	}
	public void setDealLoanNo(String dealLoanNo) {
		this.dealLoanNo = dealLoanNo;
	}
	public String getLoanCustomerName() {
		return loanCustomerName;
	}
	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public String getLoanProductCat() {
		return loanProductCat;
	}
	public void setLoanProductCat(String loanProductCat) {
		this.loanProductCat = loanProductCat;
	}
	public String getLoanScheme() {
		return loanScheme;
	}
	public void setLoanScheme(String loanScheme) {
		this.loanScheme = loanScheme;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getDealCustomerName() {
		return dealCustomerName;
	}
	public void setDealCustomerName(String dealCustomerName) {
		this.dealCustomerName = dealCustomerName;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getDealProduct() {
		return dealProduct;
	}
	public void setDealProduct(String dealProduct) {
		this.dealProduct = dealProduct;
	}
	public String getDealScheme() {
		return dealScheme;
	}
	public void setDealScheme(String dealScheme) {
		this.dealScheme = dealScheme;
	}
	public String getDealCurrentStage() {
		return dealCurrentStage;
	}
	public void setDealCurrentStage(String dealCurrentStage) {
		this.dealCurrentStage = dealCurrentStage;
	}
	public String getDealProductCat() {
		return dealProductCat;
	}
	public void setDealProductCat(String dealProductCat) {
		this.dealProductCat = dealProductCat;
	}
	public String getDealCustomerId() {
		return dealCustomerId;
	}
	public void setDealCustomerId(String dealCustomerId) {
		this.dealCustomerId = dealCustomerId;
	}
	
	
}
