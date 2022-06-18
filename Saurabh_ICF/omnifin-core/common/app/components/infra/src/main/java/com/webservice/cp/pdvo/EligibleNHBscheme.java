package com.webservice.cp.pdvo;

import java.math.BigDecimal;
import java.util.List;


public class EligibleNHBscheme {
	
	private String product;
	private Integer scheme;
	private String existingPuccaHouse;
	private Integer branchId;
	private String branchDesc;
	private BigDecimal loanAmount;
	private BigDecimal propertyValue;
	private String apfNonApf;
	private String requestFlag;
	private BigDecimal propertyArea;
	private BigDecimal houseHoldMonthlyIncome;
	private List<NHBownershipDtl>  nhbOwnershipDtlList;
	
	
	public BigDecimal getHouseHoldMonthlyIncome() {
		return houseHoldMonthlyIncome;
	}
	public void setHouseHoldMonthlyIncome(BigDecimal houseHoldMonthlyIncome) {
		this.houseHoldMonthlyIncome = houseHoldMonthlyIncome;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Integer getScheme() {
		return scheme;
	}
	public void setScheme(Integer scheme) {
		this.scheme = scheme;
	}
	public String getExistingPuccaHouse() {
		return existingPuccaHouse;
	}
	public void setExistingPuccaHouse(String existingPuccaHouse) {
		this.existingPuccaHouse = existingPuccaHouse;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public String getBranchDesc() {
		return branchDesc;
	}
	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public BigDecimal getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(BigDecimal propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getApfNonApf() {
		return apfNonApf;
	}
	public void setApfNonApf(String apfNonApf) {
		this.apfNonApf = apfNonApf;
	}
	public List<NHBownershipDtl> getNhbOwnershipDtlList() {
		return nhbOwnershipDtlList;
	}
	public void setNhbOwnershipDtlList(List<NHBownershipDtl> nhbOwnershipDtlList) {
		this.nhbOwnershipDtlList = nhbOwnershipDtlList;
	}
	public String getRequestFlag() {
		return requestFlag;
	}
	public void setRequestFlag(String requestFlag) {
		this.requestFlag = requestFlag;
	}
	
	public BigDecimal getPropertyArea() {
		return propertyArea;
	}
	public void setPropertyArea(BigDecimal propertyArea) {
		this.propertyArea = propertyArea;
	}
	@Override
	public String toString() {
		return "EligibleNHBscheme [product=" + product + ", scheme=" + scheme
				+ ", existingPuccaHouse=" + existingPuccaHouse + ", branchId="
				+ branchId + ", branchDesc=" + branchDesc + ", loanAmount="
				+ loanAmount + ", propertyValue=" + propertyValue
				+ ", apfNonApf=" + apfNonApf + ", requestFlag=" + requestFlag
				+ ", propertyArea=" + propertyArea
				+ ", houseHoldMonthlyIncome=" + houseHoldMonthlyIncome
				+ ", nhbOwnershipDtlList=" + nhbOwnershipDtlList + "]";
	}
}
