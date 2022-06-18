package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdResidenceHouseholdExpensesDtl {

	private Integer id;

	private Integer pdId;

	private BigDecimal foodGroceryExpenses;

	private BigDecimal landlineBill;

	private BigDecimal mobileBill;

	private BigDecimal electricityBill;

	private BigDecimal houseTax;

	private BigDecimal educationExpense;

	private BigDecimal medicalExpense;

	private BigDecimal conveyance;

	private BigDecimal runningEmiAmount;

	private BigDecimal monthlyInsurancePremium;

	private BigDecimal gold;

	private BigDecimal mutualChitFund;

	private String makerDate;

	private String makerId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public BigDecimal getFoodGroceryExpenses() {
		return foodGroceryExpenses;
	}

	public void setFoodGroceryExpenses(BigDecimal foodGroceryExpenses) {
		this.foodGroceryExpenses = foodGroceryExpenses;
	}

	public BigDecimal getLandlineBill() {
		return landlineBill;
	}

	public void setLandlineBill(BigDecimal landlineBill) {
		this.landlineBill = landlineBill;
	}

	public BigDecimal getMobileBill() {
		return mobileBill;
	}

	public void setMobileBill(BigDecimal mobileBill) {
		this.mobileBill = mobileBill;
	}

	public BigDecimal getElectricityBill() {
		return electricityBill;
	}

	public void setElectricityBill(BigDecimal electricityBill) {
		this.electricityBill = electricityBill;
	}

	public BigDecimal getHouseTax() {
		return houseTax;
	}

	public void setHouseTax(BigDecimal houseTax) {
		this.houseTax = houseTax;
	}

	public BigDecimal getEducationExpense() {
		return educationExpense;
	}

	public void setEducationExpense(BigDecimal educationExpense) {
		this.educationExpense = educationExpense;
	}

	public BigDecimal getMedicalExpense() {
		return medicalExpense;
	}

	public void setMedicalExpense(BigDecimal medicalExpense) {
		this.medicalExpense = medicalExpense;
	}

	public BigDecimal getConveyance() {
		return conveyance;
	}

	public void setConveyance(BigDecimal conveyance) {
		this.conveyance = conveyance;
	}

	public BigDecimal getRunningEmiAmount() {
		return runningEmiAmount;
	}

	public void setRunningEmiAmount(BigDecimal runningEmiAmount) {
		this.runningEmiAmount = runningEmiAmount;
	}

	public BigDecimal getMonthlyInsurancePremium() {
		return monthlyInsurancePremium;
	}

	public void setMonthlyInsurancePremium(BigDecimal monthlyInsurancePremium) {
		this.monthlyInsurancePremium = monthlyInsurancePremium;
	}

	public BigDecimal getGold() {
		return gold;
	}

	public void setGold(BigDecimal gold) {
		this.gold = gold;
	}

	public BigDecimal getMutualChitFund() {
		return mutualChitFund;
	}

	public void setMutualChitFund(BigDecimal mutualChitFund) {
		this.mutualChitFund = mutualChitFund;
	}

	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	@Override
	public String toString() {
		return "CrPdResidenceHouseholdExpensesDtl [id=" + id + ", pdId=" + pdId
				+ ", foodGroceryExpenses=" + foodGroceryExpenses
				+ ", landlineBill=" + landlineBill + ", mobileBill="
				+ mobileBill + ", electricityBill=" + electricityBill
				+ ", houseTax=" + houseTax + ", educationExpense="
				+ educationExpense + ", medicalExpense=" + medicalExpense
				+ ", conveyance=" + conveyance + ", runningEmiAmount="
				+ runningEmiAmount + ", monthlyInsurancePremium="
				+ monthlyInsurancePremium + ", gold=" + gold
				+ ", mutualChitFund=" + mutualChitFund + ", makerDate="
				+ makerDate + ", makerId=" + makerId + "]";
	}
}
