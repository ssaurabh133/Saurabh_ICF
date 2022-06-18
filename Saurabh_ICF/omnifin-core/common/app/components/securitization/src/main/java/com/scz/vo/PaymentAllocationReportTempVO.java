package com.scz.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PaymentAllocationReportTempVO {

	private int id;
	private int pool_id;
	private int loan_no;
	private Date month;
	private BigDecimal openingPOS;
	private BigDecimal openingFutureFlow;
	private BigDecimal openingOverdue;
	private BigDecimal openingOverduePos;
	private BigDecimal openingOverdueInt;
	private BigDecimal acctualEmi;
	private BigDecimal pos;
	private BigDecimal totalIntBank;
	private BigDecimal totalIntAu;
	private BigDecimal collOverdue;
	private BigDecimal collOverduePos;
	private BigDecimal collOverdueInt;
	private BigDecimal currBilling;
	
	private BigDecimal currBillingPos;
	private BigDecimal currBillingInt;
	private BigDecimal excessAmt;
	private BigDecimal totalCloasingOverdue;
	private BigDecimal totalCloasingFuturePos;
	private BigDecimal totalCloasingFutureInt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPool_id() {
		return pool_id;
	}
	public void setPool_id(int poolId) {
		pool_id = poolId;
	}
	public int getLoan_no() {
		return loan_no;
	}
	public void setLoan_no(int loanNo) {
		loan_no = loanNo;
	}
	public Date getMonth() {
		return month;
	}
	public void setMonth(Date month) {
		this.month = month;
	}
	public BigDecimal getOpeningPOS() {
		return openingPOS;
	}
	public void setOpeningPOS(BigDecimal openingPOS) {
		this.openingPOS = openingPOS;
	}
	public BigDecimal getOpeningFutureFlow() {
		return openingFutureFlow;
	}
	public void setOpeningFutureFlow(BigDecimal openingFutureFlow) {
		this.openingFutureFlow = openingFutureFlow;
	}
	public BigDecimal getOpeningOverdue() {
		return openingOverdue;
	}
	public void setOpeningOverdue(BigDecimal openingOverdue) {
		this.openingOverdue = openingOverdue;
	}
	public BigDecimal getOpeningOverduePos() {
		return openingOverduePos;
	}
	public void setOpeningOverduePos(BigDecimal openingOverduePos) {
		this.openingOverduePos = openingOverduePos;
	}
	public BigDecimal getOpeningOverdueInt() {
		return openingOverdueInt;
	}
	public void setOpeningOverdueInt(BigDecimal openingOverdueInt) {
		this.openingOverdueInt = openingOverdueInt;
	}
	public BigDecimal getAcctualEmi() {
		return acctualEmi;
	}
	public void setAcctualEmi(BigDecimal acctualEmi) {
		this.acctualEmi = acctualEmi;
	}
	public BigDecimal getPos() {
		return pos;
	}
	public void setPos(BigDecimal pos) {
		this.pos = pos;
	}
	public BigDecimal getTotalIntBank() {
		return totalIntBank;
	}
	public void setTotalIntBank(BigDecimal totalIntBank) {
		this.totalIntBank = totalIntBank;
	}
	public BigDecimal getTotalIntAu() {
		return totalIntAu;
	}
	public void setTotalIntAu(BigDecimal totalIntAu) {
		this.totalIntAu = totalIntAu;
	}
	public BigDecimal getCollOverdue() {
		return collOverdue;
	}
	public void setCollOverdue(BigDecimal collOverdue) {
		this.collOverdue = collOverdue;
	}
	public BigDecimal getCollOverduePos() {
		return collOverduePos;
	}
	public void setCollOverduePos(BigDecimal collOverduePos) {
		this.collOverduePos = collOverduePos;
	}
	public BigDecimal getCollOverdueInt() {
		return collOverdueInt;
	}
	public void setCollOverdueInt(BigDecimal collOverdueInt) {
		this.collOverdueInt = collOverdueInt;
	}
	public BigDecimal getCurrBilling() {
		return currBilling;
	}
	public void setCurrBilling(BigDecimal currBilling) {
		this.currBilling = currBilling;
	}
	public BigDecimal getCurrBillingPos() {
		return currBillingPos;
	}
	public void setCurrBillingPos(BigDecimal currBillingPos) {
		this.currBillingPos = currBillingPos;
	}
	public BigDecimal getCurrBillingInt() {
		return currBillingInt;
	}
	public void setCurrBillingInt(BigDecimal currBillingInt) {
		this.currBillingInt = currBillingInt;
	}
	public BigDecimal getExcessAmt() {
		return excessAmt;
	}
	public void setExcessAmt(BigDecimal excessAmt) {
		this.excessAmt = excessAmt;
	}
	public BigDecimal getTotalCloasingOverdue() {
		return totalCloasingOverdue;
	}
	public void setTotalCloasingOverdue(BigDecimal totalCloasingOverdue) {
		this.totalCloasingOverdue = totalCloasingOverdue;
	}
	public BigDecimal getTotalCloasingFuturePos() {
		return totalCloasingFuturePos;
	}
	public void setTotalCloasingFuturePos(BigDecimal totalCloasingFuturePos) {
		this.totalCloasingFuturePos = totalCloasingFuturePos;
	}
	public BigDecimal getTotalCloasingFutureInt() {
		return totalCloasingFutureInt;
	}
	public void setTotalCloasingFutureInt(BigDecimal totalCloasingFutureInt) {
		this.totalCloasingFutureInt = totalCloasingFutureInt;
	}
	
	
}
