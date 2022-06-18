package com.cp.vo;

import java.io.Serializable;

public class ChargeVo implements Serializable{
	
	private String[] chargeIdDtl;
	private String[] finalAmount;
	private String[] dealChargeTaxAmountInConfig1;
	private String[] dealChargeTaxAmountInConfig2;
	private String[] dealChargeTdsAmountInConfig;
	private String[] dealChargeNetAmountInConfig;
	private String chargeId;
	private String chargeType;
	private String chargeCode;
	private String chargeDesc;
	private String chargeBPType;
	private String chargeBPId;
	private String chargeFinal;
	private String userId;
	private String bussinessDate;
	private String loanId;
	private String dealId;
	private String chargeCal;
	private String loanAmount;
	private String marginAmount;
	private String chargeCalculatedOn;
	private String taxsInclusive;
	private String taxtRat1;
	private String taxtRat2;
	private String chargeMethod;
	private String chargeAmount[];
	private String minChargeMethod;
	private String minChargeCalculatedOn;
	private String dealChargeTaxApp;
	private String dealChargeTdsApp;
	private String dealChargeTaxAmount1;
	private String dealChargeTaxAmount2;
	private String dealChargeMinChargeAmount;
	private String dealChargeTdsRate;
	private String dealChargeTdsAmount;
	private String dealChargeNetAmount;
	private String minChargeAmount;
	private String applStage;
	private String intrsttype;
	private String intrstrate;
	private String totalintrst;
	private String cfrequency;
	private String imdAllocatedAmount;
	private String balanceAmount;
	private String customerCharge;
	private String customerFinalCharge;
	
	public String getCustomerFinalCharge() {
		return customerFinalCharge;
	}
	public void setCustomerFinalCharge(String customerFinalCharge) {
		this.customerFinalCharge = customerFinalCharge;
	}
	public String getCustomerCharge() {
		return customerCharge;
	}
	public void setCustomerCharge(String customerCharge) {
		this.customerCharge = customerCharge;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getImdAllocatedAmount() {
		return imdAllocatedAmount;
	}
	public void setImdAllocatedAmount(String imdAllocatedAmount) {
		this.imdAllocatedAmount = imdAllocatedAmount;
	}
	public String getIntrsttype() {
		return intrsttype;
	}
	public void setIntrsttype(String intrsttype) {
		this.intrsttype = intrsttype;
	}
	public String getIntrstrate() {
		return intrstrate;
	}
	public void setIntrstrate(String intrstrate) {
		this.intrstrate = intrstrate;
	}
	public String getTotalintrst() {
		return totalintrst;
	}
	public void setTotalintrst(String totalintrst) {
		this.totalintrst = totalintrst;
	}
	public String getCfrequency() {
		return cfrequency;
	}
	public void setCfrequency(String cfrequency) {
		this.cfrequency = cfrequency;
	}
	
	public String getApplStage() {
		return applStage;
	}
	public void setApplStage(String applStage) {
		this.applStage = applStage;
	}
	public String getMinChargeAmount() {
		return minChargeAmount;
	}
	public void setMinChargeAmount(String minChargeAmount) {
		this.minChargeAmount = minChargeAmount;
	}
	public String[] getDealChargeTaxAmountInConfig1() {
		return dealChargeTaxAmountInConfig1;
	}
	public void setDealChargeTaxAmountInConfig1(
			String[] dealChargeTaxAmountInConfig1) {
		this.dealChargeTaxAmountInConfig1 = dealChargeTaxAmountInConfig1;
	}
	public String[] getDealChargeTaxAmountInConfig2() {
		return dealChargeTaxAmountInConfig2;
	}
	public void setDealChargeTaxAmountInConfig2(
			String[] dealChargeTaxAmountInConfig2) {
		this.dealChargeTaxAmountInConfig2 = dealChargeTaxAmountInConfig2;
	}
	public String[] getDealChargeTdsAmountInConfig() {
		return dealChargeTdsAmountInConfig;
	}
	public void setDealChargeTdsAmountInConfig(String[] dealChargeTdsAmountInConfig) {
		this.dealChargeTdsAmountInConfig = dealChargeTdsAmountInConfig;
	}
	public String[] getDealChargeNetAmountInConfig() {
		return dealChargeNetAmountInConfig;
	}
	public void setDealChargeNetAmountInConfig(String[] dealChargeNetAmountInConfig) {
		this.dealChargeNetAmountInConfig = dealChargeNetAmountInConfig;
	}
	public String getDealChargeTaxApp() {
		return dealChargeTaxApp;
	}
	public void setDealChargeTaxApp(String dealChargeTaxApp) {
		this.dealChargeTaxApp = dealChargeTaxApp;
	}
	public String getDealChargeTdsApp() {
		return dealChargeTdsApp;
	}
	public void setDealChargeTdsApp(String dealChargeTdsApp) {
		this.dealChargeTdsApp = dealChargeTdsApp;
	}
	public String getDealChargeTaxAmount1() {
		return dealChargeTaxAmount1;
	}
	public void setDealChargeTaxAmount1(String dealChargeTaxAmount1) {
		this.dealChargeTaxAmount1 = dealChargeTaxAmount1;
	}
	public String getDealChargeTaxAmount2() {
		return dealChargeTaxAmount2;
	}
	public void setDealChargeTaxAmount2(String dealChargeTaxAmount2) {
		this.dealChargeTaxAmount2 = dealChargeTaxAmount2;
	}
	public String getDealChargeMinChargeAmount() {
		return dealChargeMinChargeAmount;
	}
	public void setDealChargeMinChargeAmount(String dealChargeMinChargeAmount) {
		this.dealChargeMinChargeAmount = dealChargeMinChargeAmount;
	}
	public String getDealChargeTdsRate() {
		return dealChargeTdsRate;
	}
	public void setDealChargeTdsRate(String dealChargeTdsRate) {
		this.dealChargeTdsRate = dealChargeTdsRate;
	}
	public String getDealChargeTdsAmount() {
		return dealChargeTdsAmount;
	}
	public void setDealChargeTdsAmount(String dealChargeTdsAmount) {
		this.dealChargeTdsAmount = dealChargeTdsAmount;
	}
	public String getDealChargeNetAmount() {
		return dealChargeNetAmount;
	}
	public void setDealChargeNetAmount(String dealChargeNetAmount) {
		this.dealChargeNetAmount = dealChargeNetAmount;
	}
	public String getMinChargeMethod() {
		return minChargeMethod;
	}
	public void setMinChargeMethod(String minChargeMethod) {
		this.minChargeMethod = minChargeMethod;
	}
	public String getMinChargeCalculatedOn() {
		return minChargeCalculatedOn;
	}
	public void setMinChargeCalculatedOn(String minChargeCalculatedOn) {
		this.minChargeCalculatedOn = minChargeCalculatedOn;
	}
	public String[] getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(String[] chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getChargeMethod() {
		return chargeMethod;
	}
	public void setChargeMethod(String chargeMethod) {
		this.chargeMethod = chargeMethod;
	}
	public String getTaxsInclusive() {
		return taxsInclusive;
	}
	public void setTaxsInclusive(String taxsInclusive) {
		this.taxsInclusive = taxsInclusive;
	}
	public String getTaxtRat1() {
		return taxtRat1;
	}
	public void setTaxtRat1(String taxtRat1) {
		this.taxtRat1 = taxtRat1;
	}
	public String getTaxtRat2() {
		return taxtRat2;
	}
	public void setTaxtRat2(String taxtRat2) {
		this.taxtRat2 = taxtRat2;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getMarginAmount() {
		return marginAmount;
	}
	public void setMarginAmount(String marginAmount) {
		this.marginAmount = marginAmount;
	}
	public String getChargeCalculatedOn() {
		return chargeCalculatedOn;
	}
	public void setChargeCalculatedOn(String chargeCalculatedOn) {
		this.chargeCalculatedOn = chargeCalculatedOn;
	}
	public String getChargeCal() {
		return chargeCal;
	}
	public void setChargeCal(String chargeCal) {
		this.chargeCal = chargeCal;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String[] getChargeIdDtl() {
		return chargeIdDtl;
	}
	public void setChargeIdDtl(String[] chargeIdDtl) {
		this.chargeIdDtl = chargeIdDtl;
	}
	public String[] getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(String[] finalAmount) {
		this.finalAmount = finalAmount;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getChargeCode() {
		return chargeCode;
	}
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}
	public String getChargeDesc() {
		return chargeDesc;
	}
	public void setChargeDesc(String chargeDesc) {
		this.chargeDesc = chargeDesc;
	}
	public String getChargeBPType() {
		return chargeBPType;
	}
	public void setChargeBPType(String chargeBPType) {
		this.chargeBPType = chargeBPType;
	}
	public String getChargeBPId() {
		return chargeBPId;
	}
	public void setChargeBPId(String chargeBPId) {
		this.chargeBPId = chargeBPId;
	}
	public String getChargeFinal() {
		return chargeFinal;
	}
	public void setChargeFinal(String chargeFinal) {
		this.chargeFinal = chargeFinal;
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
	
   
	
}
