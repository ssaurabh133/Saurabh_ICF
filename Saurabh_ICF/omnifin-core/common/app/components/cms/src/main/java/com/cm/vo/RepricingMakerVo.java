package com.cm.vo;

public class RepricingMakerVo {
	private String loanNo;
	private String lbxLoanNoHID;
	private String customerName;
	private String product;
	private String scheme;
	private String disbursedAmount;
	private String outstandingLoanAmount;
	private String rePricingSinceDsibursal;
	private String lastRePricingDate;
	private String rePricingFromInstallment;
	private String reschDate;
	private String rePricingCondition;
	private String reqRefNo;
	private String interestForGapPeriod;
	private String interestChargingMethod;
	private String reschCharges;
	private String rePricingReason;
	
	private String interestRateMethod;
	private String interestRateMethodHid;
	private String interestRateType;
	private String interestRateTypeHid;
	private String baseRateType;
	private String baseRate;
	private String markup;
	private String finalRate;
	private String effectiveRate;
	private String installmentFrequency;
	private String installmentFrequencyHid;
	private String installmentType;
	private String installmentTypeHid;
	private String emi;
	private String tenure;
	private String noOfInstl;
	private String nextDueDate;
	private String maturityDate;
	
	private String interestRateMethodOld;
	private String interestRateMethodOldHid;
	private String interestRateTypeOld;
	private String interestRateTypeOldHid;
	private String baseRateTypeOld;
	private String baseRateOld;
	private String finalRateOld;
	private String markupOld;
	private String installmentFrequencyOld;
	private String installmentFrequencyOldHid;
	private String installmentTypeOld;
	private String installmentTypeOldHid;
	private String tenureOld;
	private String noOfInstlOld;
	private String maturityDateOld;
	
	
	private String makerId;
	private String makerDate;
	private String authorRemarks;
	private String reschId;
	private String flatRate;
	
	
	// Adde By Rahul papneja|09112017
	private String repricingDate;
	
	private String maxInstallmentDate;
	
	
	
	public String getMaxInstallmentDate()
	{
		return maxInstallmentDate;
	}
	public void setMaxInstallmentDate(String maxInstallmentDate)
	{
		this.maxInstallmentDate=maxInstallmentDate;	
	}
	
	public String getRepricingDate()
	{
		return repricingDate;
	}
	
	public void setRepricingdate(String repricingDate){
		this.repricingDate=repricingDate;
	}
	
	public String getFlatRate() {
		return flatRate;
	}
	public void setFlatRate(String flatRate) {
		this.flatRate = flatRate;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getDisbursedAmount() {
		return disbursedAmount;
	}
	public void setDisbursedAmount(String disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}
	public String getOutstandingLoanAmount() {
		return outstandingLoanAmount;
	}
	public void setOutstandingLoanAmount(String outstandingLoanAmount) {
		this.outstandingLoanAmount = outstandingLoanAmount;
	}
	public String getRePricingSinceDsibursal() {
		return rePricingSinceDsibursal;
	}
	public void setRePricingSinceDsibursal(String rePricingSinceDsibursal) {
		this.rePricingSinceDsibursal = rePricingSinceDsibursal;
	}
	public String getLastRePricingDate() {
		return lastRePricingDate;
	}
	public void setLastRePricingDate(String lastRePricingDate) {
		this.lastRePricingDate = lastRePricingDate;
	}
	public String getRePricingFromInstallment() {
		return rePricingFromInstallment;
	}
	public void setRePricingFromInstallment(String rePricingFromInstallment) {
		this.rePricingFromInstallment = rePricingFromInstallment;
	}
	public String getReschDate() {
		return reschDate;
	}
	public void setReschDate(String reschDate) {
		this.reschDate = reschDate;
	}
	public String getRePricingCondition() {
		return rePricingCondition;
	}
	public void setRePricingCondition(String rePricingCondition) {
		this.rePricingCondition = rePricingCondition;
	}
	public String getReqRefNo() {
		return reqRefNo;
	}
	public void setReqRefNo(String reqRefNo) {
		this.reqRefNo = reqRefNo;
	}
	public String getInterestForGapPeriod() {
		return interestForGapPeriod;
	}
	public void setInterestForGapPeriod(String interestForGapPeriod) {
		this.interestForGapPeriod = interestForGapPeriod;
	}
	public String getInterestChargingMethod() {
		return interestChargingMethod;
	}
	public void setInterestChargingMethod(String interestChargingMethod) {
		this.interestChargingMethod = interestChargingMethod;
	}
	public String getReschCharges() {
		return reschCharges;
	}
	public void setReschCharges(String reschCharges) {
		this.reschCharges = reschCharges;
	}
	public String getRePricingReason() {
		return rePricingReason;
	}
	public void setRePricingReason(String rePricingReason) {
		this.rePricingReason = rePricingReason;
	}
	public String getInterestRateType() {
		return interestRateType;
	}
	public void setInterestRateType(String interestRateType) {
		this.interestRateType = interestRateType;
	}
	public String getBaseRateType() {
		return baseRateType;
	}
	public void setBaseRateType(String baseRateType) {
		this.baseRateType = baseRateType;
	}
	public String getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}
	public void setMarkup(String markup) {
		this.markup = markup;
	}
	public String getMarkup() {
		return markup;
	}
	public void setEffectiveRate(String effectiveRate) {
		this.effectiveRate = effectiveRate;
	}
	public String getEffectiveRate() {
		return effectiveRate;
	}
	public String getInstallmentFrequency() {
		return installmentFrequency;
	}
	public void setInstallmentFrequency(String installmentFrequency) {
		this.installmentFrequency = installmentFrequency;
	}
	public String getInstallmentType() {
		return installmentType;
	}
	public void setInstallmentType(String installmentType) {
		this.installmentType = installmentType;
	}
	public String getEmi() {
		return emi;
	}
	public void setEmi(String emi) {
		this.emi = emi;
	}
	public String getTenure() {
		return tenure;
	}
	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
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
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	public String getReschId() {
		return reschId;
	}
	public void setReschId(String reschId) {
		this.reschId = reschId;
	}
	public void setInterestRateMethod(String interestRateMethod) {
		this.interestRateMethod = interestRateMethod;
	}
	public String getInterestRateMethod() {
		return interestRateMethod;
	}
	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	public String getNextDueDate() {
		return nextDueDate;
	}
	public void setFinalRate(String finalRate) {
		this.finalRate = finalRate;
	}
	public String getFinalRate() {
		return finalRate;
	}
	public void setMarkupOld(String markupOld) {
		this.markupOld = markupOld;
	}
	public String getMarkupOld() {
		return markupOld;
	}
	public void setInstallmentFrequencyOld(String installmentFrequencyOld) {
		this.installmentFrequencyOld = installmentFrequencyOld;
	}
	public String getInstallmentFrequencyOld() {
		return installmentFrequencyOld;
	}
	public void setTenureOld(String tenureOld) {
		this.tenureOld = tenureOld;
	}
	public String getTenureOld() {
		return tenureOld;
	}
	public void setInstallmentFrequencyOldHid(String installmentFrequencyOldHid) {
		this.installmentFrequencyOldHid = installmentFrequencyOldHid;
	}
	public String getInstallmentFrequencyOldHid() {
		return installmentFrequencyOldHid;
	}
	public void setInterestRateMethodHid(String interestRateMethodHid) {
		this.interestRateMethodHid = interestRateMethodHid;
	}
	public String getInterestRateMethodHid() {
		return interestRateMethodHid;
	}
	public void setInterestRateTypeHid(String interestRateTypeHid) {
		this.interestRateTypeHid = interestRateTypeHid;
	}
	public String getInterestRateTypeHid() {
		return interestRateTypeHid;
	}
	public void setInstallmentTypeHid(String installmentTypeHid) {
		this.installmentTypeHid = installmentTypeHid;
	}
	public String getInstallmentTypeHid() {
		return installmentTypeHid;
	}
	public void setInstallmentFrequencyHid(String installmentFrequencyHid) {
		this.installmentFrequencyHid = installmentFrequencyHid;
	}
	public String getInstallmentFrequencyHid() {
		return installmentFrequencyHid;
	}
	public void setNoOfInstl(String noOfInstl) {
		this.noOfInstl = noOfInstl;
	}
	public String getNoOfInstl() {
		return noOfInstl;
	}
	public void setNoOfInstlOld(String noOfInstlOld) {
		this.noOfInstlOld = noOfInstlOld;
	}
	public String getNoOfInstlOld() {
		return noOfInstlOld;
	}
	public void setInterestRateMethodOld(String interestRateMethodOld) {
		this.interestRateMethodOld = interestRateMethodOld;
	}
	public String getInterestRateMethodOld() {
		return interestRateMethodOld;
	}
	public void setInterestRateMethodOldHid(String interestRateMethodOldHid) {
		this.interestRateMethodOldHid = interestRateMethodOldHid;
	}
	public String getInterestRateMethodOldHid() {
		return interestRateMethodOldHid;
	}
	public void setInterestRateTypeOld(String interestRateTypeOld) {
		this.interestRateTypeOld = interestRateTypeOld;
	}
	public String getInterestRateTypeOld() {
		return interestRateTypeOld;
	}
	public void setInterestRateTypeOldHid(String interestRateTypeOldHid) {
		this.interestRateTypeOldHid = interestRateTypeOldHid;
	}
	public String getInterestRateTypeOldHid() {
		return interestRateTypeOldHid;
	}
	public void setBaseRateTypeOld(String baseRateTypeOld) {
		this.baseRateTypeOld = baseRateTypeOld;
	}
	public String getBaseRateTypeOld() {
		return baseRateTypeOld;
	}
	public void setBaseRateOld(String baseRateOld) {
		this.baseRateOld = baseRateOld;
	}
	public String getBaseRateOld() {
		return baseRateOld;
	}
	public void setFinalRateOld(String finalRateOld) {
		this.finalRateOld = finalRateOld;
	}
	public String getFinalRateOld() {
		return finalRateOld;
	}
	public void setInstallmentTypeOld(String installmentTypeOld) {
		this.installmentTypeOld = installmentTypeOld;
	}
	public String getInstallmentTypeOld() {
		return installmentTypeOld;
	}
	public void setInstallmentTypeOldHid(String installmentTypeOldHid) {
		this.installmentTypeOldHid = installmentTypeOldHid;
	}
	public String getInstallmentTypeOldHid() {
		return installmentTypeOldHid;
	}
	public void setMaturityDateOld(String maturityDateOld) {
		this.maturityDateOld = maturityDateOld;
	}
	public String getMaturityDateOld() {
		return maturityDateOld;
	}
}
