package com.scz.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class MonthlyCollectionReoportVO {

	private int id;
	private int loanId;
	private BigDecimal openingOverdue;
	private BigDecimal billingForMonth;
	private BigDecimal cloasingOverdue;
	private BigDecimal collectionForMonth;
	private int poolId;
	private Date monthOfReport;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public BigDecimal getOpeningOverdue() {
		return openingOverdue;
	}
	public void setOpeningOverdue(BigDecimal openingOverdue) {
		this.openingOverdue = openingOverdue;
	}
	public BigDecimal getBillingForMonth() {
		return billingForMonth;
	}
	public void setBillingForMonth(BigDecimal billingForMonth) {
		this.billingForMonth = billingForMonth;
	}
	public BigDecimal getCloasingOverdue() {
		return cloasingOverdue;
	}
	public void setCloasingOverdue(BigDecimal cloasingOverdue) {
		this.cloasingOverdue = cloasingOverdue;
	}
	public BigDecimal getCollectionForMonth() {
		return collectionForMonth;
	}
	public void setCollectionForMonth(BigDecimal collectionForMonth) {
		this.collectionForMonth = collectionForMonth;
	}
	public int getPoolId() {
		return poolId;
	}
	public void setPoolId(int poolId) {
		this.poolId = poolId;
	}
	public Date getMonthOfReport() {
		return monthOfReport;
	}
	public void setMonthOfReport(Date monthOfReport) {
		this.monthOfReport = monthOfReport;
	}
	
	
}
