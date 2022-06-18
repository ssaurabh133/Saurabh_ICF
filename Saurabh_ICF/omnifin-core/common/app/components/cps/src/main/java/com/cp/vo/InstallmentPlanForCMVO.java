package com.cp.vo;

import java.io.Serializable;

public class InstallmentPlanForCMVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String installmentType;
	private String totalInstallment;
	private String toInstallment;
	private String fromInstallment;
	private String recoveryPercen;
	private String loanId;
	private String toInstall[];
	private String fromInstall[];
	private String recoveryPer[];
	private String principalAmount[];
	private String installmentAmount[];
	private String dealId;
	private String txnType;
	private String prinAm;
	private String instalAm;
	private String rateType;
	
	private String loanAmount;
	private String recoveryType;
	private String reschId;
	private String makerId;
	private String makerDate;
	private String disbursalId;
	private String dueDate[];
	private String dueDatee;
	private String repayeffdate;
	private String maxDate;
	
	
	
	
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getRecoveryType() {
		return recoveryType;
	}
	public void setRecoveryType(String recoveryType) {
		this.recoveryType = recoveryType;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getPrinAm() {
		return prinAm;
	}
	public void setPrinAm(String prinAm) {
		this.prinAm = prinAm;
	}
	public String getInstalAm() {
		return instalAm;
	}
	public void setInstalAm(String instalAm) {
		this.instalAm = instalAm;
	}
	public String[] getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(String[] principalAmount) {
		this.principalAmount = principalAmount;
	}
	public String[] getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(String[] installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getInstallmentType() {
		return installmentType;
	}
	public void setInstallmentType(String installmentType) {
		this.installmentType = installmentType;
	}
	public String getTotalInstallment() {
		return totalInstallment;
	}
	public void setTotalInstallment(String totalInstallment) {
		this.totalInstallment = totalInstallment;
	}
	public String getToInstallment() {
		return toInstallment;
	}
	public void setToInstallment(String toInstallment) {
		this.toInstallment = toInstallment;
	}
	public String getFromInstallment() {
		return fromInstallment;
	}
	public void setFromInstallment(String fromInstallment) {
		this.fromInstallment = fromInstallment;
	}
	public String getRecoveryPercen() {
		return recoveryPercen;
	}
	public void setRecoveryPercen(String recoveryPercen) {
		this.recoveryPercen = recoveryPercen;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String[] getToInstall() {
		return toInstall;
	}
	public void setToInstall(String[] toInstall) {
		this.toInstall = toInstall;
	}
	public String[] getFromInstall() {
		return fromInstall;
	}
	public void setFromInstall(String[] fromInstall) {
		this.fromInstall = fromInstall;
	}
	public String[] getRecoveryPer() {
		return recoveryPer;
	}
	public void setRecoveryPer(String[] recoveryPer) {
		this.recoveryPer = recoveryPer;
	}
	public void setReschId(String reschId) {
		this.reschId = reschId;
	}
	public String getReschId() {
		return reschId;
	}
	public void setDisbursalId(String disbursalId) {
		this.disbursalId = disbursalId;
	}
	public String getDisbursalId() {
		return disbursalId;
	}
		public String getRepayeffdate() {
		return repayeffdate;
	}
	public void setRepayeffdate(String repayeffdate) {
		this.repayeffdate = repayeffdate;
	}
	public String[] getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate[]) {
		this.dueDate = dueDate;
	}
	public String getDueDatee() {
		return dueDatee;
	}
	public void setDueDatee(String string) {
		this.dueDatee = string;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

}
