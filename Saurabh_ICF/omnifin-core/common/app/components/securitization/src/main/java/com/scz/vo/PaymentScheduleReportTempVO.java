package com.scz.vo;
import java.math.BigDecimal;
import java.sql.Date;

public class PaymentScheduleReportTempVO {

	
	private int id;
	private int pool_id;
	private String accrual_Month;
	private Date instl_Date;
	private BigDecimal Total_Principle_Amort;
	private BigDecimal interest;
	private BigDecimal residual_Principle;
	private BigDecimal monthly_Cash_Flow;
	private BigDecimal total_EMI;
	private BigDecimal difference;
	
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
	public String getAccrual_Month() {
		return accrual_Month;
	}
	public void setAccrual_Month(String accrualMonth) {
		accrual_Month = accrualMonth;
	}
	
	public Date getInstl_Date() {
		return instl_Date;
	}
	public void setInstl_Date(Date instlDate) {
		instl_Date = instlDate;
	}
	
	public BigDecimal getTotal_Principle_Amort() {
		return Total_Principle_Amort;
	}
	public void setTotal_Principle_Amort(BigDecimal totalPrincipleAmort) {
		Total_Principle_Amort = totalPrincipleAmort;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public BigDecimal getResidual_Principle() {
		return residual_Principle;
	}
	public void setResidual_Principle(BigDecimal residualPrinciple) {
		residual_Principle = residualPrinciple;
	}
	public BigDecimal getMonthly_Cash_Flow() {
		return monthly_Cash_Flow;
	}
	public void setMonthly_Cash_Flow(BigDecimal monthlyCashFlow) {
		monthly_Cash_Flow = monthlyCashFlow;
	}
	public BigDecimal getTotal_EMI() {
		return total_EMI;
	}
	public void setTotal_EMI(BigDecimal totalEMI) {
		total_EMI = totalEMI;
	}
	public BigDecimal getDifference() {
		return difference;
	}
	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}
	
	
	
}
