package com.masters.vo;

import java.io.Serializable;

public class CrSchemeMasterVo implements Serializable{
	
	private String productId;
	private String schemeDesc;
	private String minAmountFin;
	private String maxAmountFin;
	private String minMarginRate;
	private String defaultMarginRate;
	private String rateType;
	private String rateMethod;
	private String baseRateType;
	private String minFlatRate;
	private String maxFlatRate;
	private String defFlatRate;
	private String minEffRate;
	private String maxEffRate;
	private String defEffRate;
	private String minIrr;
	private String maxIrr;
	private String minTenure;
	private String maxTenure;
	private String defTenure;
	private String repaymentFreq;
	private String installmentType;
	private String repaymentMode;
	private String installmentMode;
	private String reschAllowed;
	private String reschLockinPeriod;
	private String numberReschAllowedYear;
	private String numberReschAllowedTotal;
	private String minimumGapResch;
	private String deferralAllowed;
	private String defrLockinPeriod;
	private String maximumDefrMonthsAllowed;
	private String maximumDefrMonthsTotal;
	private String minimumGapDefr;
	private String numberDefrAllowedYear;
	private String numberDefrAllowedTotal;
	private String prepayAllowed;
	private String numberPrepayAllowedYear;
	private String minimumGapPrepay;
	private String minimumPrepayPercent;
	private String maximumPrepayPercent;
	private String terminationAllowed;
	private String minimumGapTermination;
	private String status;
	private String maxMarginRate;
	
	private String prepayLockinPeriod;
	private String numberPrepayAllowedTotal;
	private String terminationLockinPeriod;
	private String schemeId;
	private String makerId;
	private String makerDate;
	private String lbxProductID;
	private String lbxAssetFlag;
	private String minPeriodResch;
	private String schemeIdModify;
	
	private String stageId;
	private String accountingFlag;
	private String chk;
	private String stageDes;
	private String customerExposureLimit;

	private int currentPageLink;
	private int totalRecordSize;
	private String preEMI;
	
	private String fixPriod;
	private String reviewEvnet;
	private String gapReview;
	private String increse;
	private String decrese;
	private String repaymentLabel;
	private String validityDays;
	private String expiryDate;
	
	private String additionalDisbAllowed;
	private String minimumGapBetPrepayAndTer;
	
	private String schemeExposure;
	private String lbxBranchIds;
	private String branchId;
	private String branchDesc;
	private String allselection;
	private String singleselection ;
	private String	selectionAccecc;
	
	
	
	
	
	public String getSchemeExposure() {
		return schemeExposure;
	}
	public void setSchemeExposure(String schemeExposure) {
		this.schemeExposure = schemeExposure;
	}
	public String getValidityDays() {
		return validityDays;
	}
	public void setValidityDays(String validityDays) {
		this.validityDays = validityDays;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getMinimumGapBetPrepayAndTer() {
		return minimumGapBetPrepayAndTer;
	}
	public void setMinimumGapBetPrepayAndTer(String minimumGapBetPrepayAndTer) {
		this.minimumGapBetPrepayAndTer = minimumGapBetPrepayAndTer;
	}
	public String getFixPriod() {
		return fixPriod;
	}
	public void setFixPriod(String fixPriod) {
		this.fixPriod = fixPriod;
	}
	public String getReviewEvnet() {
		return reviewEvnet;
	}
	public void setReviewEvnet(String reviewEvnet) {
		this.reviewEvnet = reviewEvnet;
	}
	public String getGapReview() {
		return gapReview;
	}
	public void setGapReview(String gapReview) {
		this.gapReview = gapReview;
	}
	public String getIncrese() {
		return increse;
	}
	public void setIncrese(String increse) {
		this.increse = increse;
	}
	public String getDecrese() {
		return decrese;
	}
	public void setDecrese(String decrese) {
		this.decrese = decrese;
	}
	
	public String getPreEMI() {
		return preEMI;
	}
	public void setPreEMI(String preEMI) {
		this.preEMI = preEMI;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}
	public String getAccountingFlag() {
		return accountingFlag;
	}
	public void setAccountingFlag(String accountingFlag) {
		this.accountingFlag = accountingFlag;
	}
	public String getSchemeIdModify() {
		return schemeIdModify;
	}
	public void setSchemeIdModify(String schemeIdModify) {
		this.schemeIdModify = schemeIdModify;
	}
	public String getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	public String getTerminationLockinPeriod() {
		return terminationLockinPeriod;
	}
	public void setTerminationLockinPeriod(String terminationLockinPeriod) {
		this.terminationLockinPeriod = terminationLockinPeriod;
	}
	public String getPrepayLockinPeriod() {
		return prepayLockinPeriod;
	}
	public void setPrepayLockinPeriod(String prepayLockinPeriod) {
		this.prepayLockinPeriod = prepayLockinPeriod;
	}
	public String getNumberPrepayAllowedTotal() {
		return numberPrepayAllowedTotal;
	}
	public void setNumberPrepayAllowedTotal(String numberPrepayAllowedTotal) {
		this.numberPrepayAllowedTotal = numberPrepayAllowedTotal;
	}
	public String getMaxMarginRate() {
		return maxMarginRate;
	}
	public void setMaxMarginRate(String maxMarginRate) {
		this.maxMarginRate = maxMarginRate;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSchemeDesc() {
		return schemeDesc;
	}
	public void setSchemeDesc(String schemeDesc) {
		this.schemeDesc = schemeDesc;
	}
	public String getMinAmountFin() {
		return minAmountFin;
	}
	public void setMinAmountFin(String minAmountFin) {
		this.minAmountFin = minAmountFin;
	}
	public String getMaxAmountFin() {
		return maxAmountFin;
	}
	public void setMaxAmountFin(String maxAmountFin) {
		this.maxAmountFin = maxAmountFin;
	}

	public String getMinMarginRate() {
		return minMarginRate;
	}
	public void setMinMarginRate(String minMarginRate) {
		this.minMarginRate = minMarginRate;
	}
	public String getDefaultMarginRate() {
		return defaultMarginRate;
	}
	public void setDefaultMarginRate(String defaultMarginRate) {
		this.defaultMarginRate = defaultMarginRate;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getRateMethod() {
		return rateMethod;
	}
	public void setRateMethod(String rateMethod) {
		this.rateMethod = rateMethod;
	}
	public String getBaseRateType() {
		return baseRateType;
	}
	public void setBaseRateType(String baseRateType) {
		this.baseRateType = baseRateType;
	}
	public String getMinFlatRate() {
		return minFlatRate;
	}
	public void setMinFlatRate(String minFlatRate) {
		this.minFlatRate = minFlatRate;
	}
	public String getMaxFlatRate() {
		return maxFlatRate;
	}
	public void setMaxFlatRate(String maxFlatRate) {
		this.maxFlatRate = maxFlatRate;
	}
	public String getDefFlatRate() {
		return defFlatRate;
	}
	public void setDefFlatRate(String defFlatRate) {
		this.defFlatRate = defFlatRate;
	}
	public String getMinEffRate() {
		return minEffRate;
	}
	public void setMinEffRate(String minEffRate) {
		this.minEffRate = minEffRate;
	}
	public String getMaxEffRate() {
		return maxEffRate;
	}
	public void setMaxEffRate(String maxEffRate) {
		this.maxEffRate = maxEffRate;
	}
	public String getDefEffRate() {
		return defEffRate;
	}
	public void setDefEffRate(String defEffRate) {
		this.defEffRate = defEffRate;
	}
	public String getMinIrr() {
		return minIrr;
	}
	public void setMinIrr(String minIrr) {
		this.minIrr = minIrr;
	}
	public String getMaxIrr() {
		return maxIrr;
	}
	public void setMaxIrr(String maxIrr) {
		this.maxIrr = maxIrr;
	}
	public String getMinTenure() {
		return minTenure;
	}
	public void setMinTenure(String minTenure) {
		this.minTenure = minTenure;
	}
	public String getMaxTenure() {
		return maxTenure;
	}
	public void setMaxTenure(String maxTenure) {
		this.maxTenure = maxTenure;
	}
	public String getDefTenure() {
		return defTenure;
	}
	public void setDefTenure(String defTenure) {
		this.defTenure = defTenure;
	}
	public String getRepaymentFreq() {
		return repaymentFreq;
	}
	public void setRepaymentFreq(String repaymentFreq) {
		this.repaymentFreq = repaymentFreq;
	}
	public String getInstallmentType() {
		return installmentType;
	}
	public void setInstallmentType(String installmentType) {
		this.installmentType = installmentType;
	}
	public String getRepaymentMode() {
		return repaymentMode;
	}
	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}
	public String getInstallmentMode() {
		return installmentMode;
	}
	public void setInstallmentMode(String installmentMode) {
		this.installmentMode = installmentMode;
	}
	public String getReschAllowed() {
		return reschAllowed;
	}
	public void setReschAllowed(String reschAllowed) {
		this.reschAllowed = reschAllowed;
	}
	public String getReschLockinPeriod() {
		return reschLockinPeriod;
	}
	public void setReschLockinPeriod(String reschLockinPeriod) {
		this.reschLockinPeriod = reschLockinPeriod;
	}
	public String getNumberReschAllowedYear() {
		return numberReschAllowedYear;
	}
	public void setNumberReschAllowedYear(String numberReschAllowedYear) {
		this.numberReschAllowedYear = numberReschAllowedYear;
	}
	public String getNumberReschAllowedTotal() {
		return numberReschAllowedTotal;
	}
	public void setNumberReschAllowedTotal(String numberReschAllowedTotal) {
		this.numberReschAllowedTotal = numberReschAllowedTotal;
	}
	public String getMinimumGapResch() {
		return minimumGapResch;
	}
	public void setMinimumGapResch(String minimumGapResch) {
		this.minimumGapResch = minimumGapResch;
	}
	public String getDeferralAllowed() {
		return deferralAllowed;
	}
	public void setDeferralAllowed(String deferralAllowed) {
		this.deferralAllowed = deferralAllowed;
	}
	public String getDefrLockinPeriod() {
		return defrLockinPeriod;
	}
	public void setDefrLockinPeriod(String defrLockinPeriod) {
		this.defrLockinPeriod = defrLockinPeriod;
	}
	public String getMaximumDefrMonthsAllowed() {
		return maximumDefrMonthsAllowed;
	}
	public void setMaximumDefrMonthsAllowed(String maximumDefrMonthsAllowed) {
		this.maximumDefrMonthsAllowed = maximumDefrMonthsAllowed;
	}
	public String getMaximumDefrMonthsTotal() {
		return maximumDefrMonthsTotal;
	}
	public void setMaximumDefrMonthsTotal(String maximumDefrMonthsTotal) {
		this.maximumDefrMonthsTotal = maximumDefrMonthsTotal;
	}
	public String getMinimumGapDefr() {
		return minimumGapDefr;
	}
	public void setMinimumGapDefr(String minimumGapDefr) {
		this.minimumGapDefr = minimumGapDefr;
	}
	public String getNumberDefrAllowedYear() {
		return numberDefrAllowedYear;
	}
	public void setNumberDefrAllowedYear(String numberDefrAllowedYear) {
		this.numberDefrAllowedYear = numberDefrAllowedYear;
	}
	public String getNumberDefrAllowedTotal() {
		return numberDefrAllowedTotal;
	}
	public void setNumberDefrAllowedTotal(String numberDefrAllowedTotal) {
		this.numberDefrAllowedTotal = numberDefrAllowedTotal;
	}
	public String getPrepayAllowed() {
		return prepayAllowed;
	}
	public void setPrepayAllowed(String prepayAllowed) {
		this.prepayAllowed = prepayAllowed;
	}
	public String getNumberPrepayAllowedYear() {
		return numberPrepayAllowedYear;
	}
	public void setNumberPrepayAllowedYear(String numberPrepayAllowedYear) {
		this.numberPrepayAllowedYear = numberPrepayAllowedYear;
	}
	public String getMinimumGapPrepay() {
		return minimumGapPrepay;
	}
	public void setMinimumGapPrepay(String minimumGapPrepay) {
		this.minimumGapPrepay = minimumGapPrepay;
	}
	public String getMinimumPrepayPercent() {
		return minimumPrepayPercent;
	}
	public void setMinimumPrepayPercent(String minimumPrepayPercent) {
		this.minimumPrepayPercent = minimumPrepayPercent;
	}
	public String getMaximumPrepayPercent() {
		return maximumPrepayPercent;
	}
	public void setMaximumPrepayPercent(String maximumPrepayPercent) {
		this.maximumPrepayPercent = maximumPrepayPercent;
	}
	public String getTerminationAllowed() {
		return terminationAllowed;
	}
	public void setTerminationAllowed(String terminationAllowed) {
		this.terminationAllowed = terminationAllowed;
	}
	public String getMinimumGapTermination() {
		return minimumGapTermination;
	}
	public void setMinimumGapTermination(String minimumGapTermination) {
		this.minimumGapTermination = minimumGapTermination;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}
	public String getLbxProductID() {
		return lbxProductID;
	}
	public void setLbxAssetFlag(String lbxAssetFlag) {
		this.lbxAssetFlag = lbxAssetFlag;
	}
	public String getLbxAssetFlag() {
		return lbxAssetFlag;
	}
	public void setMinPeriodResch(String minPeriodResch) {
		this.minPeriodResch = minPeriodResch;
	}
	public String getMinPeriodResch() {
		return minPeriodResch;
	}
	public void setChk(String chk) {
		this.chk = chk;
	}
	public String getChk() {
		return chk;
	}
	public void setStageDes(String stageDes) {
		this.stageDes = stageDes;
	}
	public String getStageDes() {
		return stageDes;
	}
	public void setCustomerExposureLimit(String customerExposureLimit) {
		this.customerExposureLimit = customerExposureLimit;
	}
	public String getCustomerExposureLimit() {
		return customerExposureLimit;
	}
	public void setAdditionalDisbAllowed(String additionalDisbAllowed) {
		this.additionalDisbAllowed = additionalDisbAllowed;
	}
	public String getAdditionalDisbAllowed() {
		return additionalDisbAllowed;
	}
	public void setRepaymentLabel(String repaymentLabel) {
		this.repaymentLabel = repaymentLabel;
	}
	public String getRepaymentLabel() {
		return repaymentLabel;
	}
	public void setLbxBranchIds(String lbxBranchIds) {
		this.lbxBranchIds = lbxBranchIds;
	}
	public String getLbxBranchIds() {
		return lbxBranchIds;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}
	public String getBranchDesc() {
		return branchDesc;
	}
	public void setAllselection(String allselection) {
		this.allselection = allselection;
	}
	public String getAllselection() {
		return allselection;
	}
	public void setSingleselection(String singleselection) {
		this.singleselection = singleselection;
	}
	public String getSingleselection() {
		return singleselection;
	}
	public void setSelectionAccecc(String selectionAccecc) {
		this.selectionAccecc = selectionAccecc;
	}
	public String getSelectionAccecc() {
		return selectionAccecc;
	}
		
	
}
