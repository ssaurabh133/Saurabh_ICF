package com.cm.vo;

import java.io.Serializable;

public class SecurityDepositVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String securityAmount;
	private String interestType;
	private String interestRate;
	private String compoundFrequency;
	private String tenure;
	private String relatedInterest;
	private String txnType;
	private String loanId;
	private String userId;
	private String bussinessDate;
	private String txnId;
	private String dealId;
	private String sdAdjust;
	
	
	
	
	public String getSdAdjust() {
		return sdAdjust;
	}
	public void setSdAdjust(String sdAdjust) {
		this.sdAdjust = sdAdjust;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBussinessDate() {
		return bussinessDate;
	}
	public void setBussinessDate(String bussinessDate) {
		this.bussinessDate = bussinessDate;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getTenure() {
		return tenure;
	}
	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
	public String getRelatedInterest() {
		return relatedInterest;
	}
	public void setRelatedInterest(String relatedInterest) {
		this.relatedInterest = relatedInterest;
	}
	public String getSecurityAmount() {
		return securityAmount;
	}
	public void setSecurityAmount(String securityAmount) {
		this.securityAmount = securityAmount;
	}
	public String getInterestType() {
		return interestType;
	}
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getCompoundFrequency() {
		return compoundFrequency;
	}
	public void setCompoundFrequency(String compoundFrequency) {
		this.compoundFrequency = compoundFrequency;
	}
	
	}
