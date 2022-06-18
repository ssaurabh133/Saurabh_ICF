package com.cm.vo;

public class ClosureVO {
	private String lbxLoanNoHID; // This is loan ID
	private String terminationId;
	private String closureType;
	private String requestNumber;
	private String initiationDate;
	private String loanAc;
	private String loanDate;
	private String customerName;
	private String product;
	private String scheme;
	private String frequency;
	private String originalTenure;
	private String remainingTenure;
	private String remainingInstallments;
	private String reasonForClosure;
	private String effectiveDate;
	private String maturityDate;
	private String balancePrincipal;
	private String otherDues;
	private String overdueInstallments;
	private String secureDeposit;
	private String secureDepositInterest;
	private String otherRefunds;
	private String secureDepositTDS;
	private String gapSDInterest;
	private String gapSDTDS;
	private String unBilledInstallments;
	private String interestTillDate;
	private String foreClosurePenalty;
	private String netReceivablePayable;
	private String totalRecevable;
	private String lppAmount;
	private String interstTillLP;
	private String earlyClosureWarn;
	private String procWarning;
	private String overduePrincipal;
	private String waiveOffAmount;
	private String makerId;
	//Added By Arun advanceEmiRefunds
	private String advanceEmiRefunds;
	private String totalPayable;
	//added by neeraj date,charges,amountDue,balsAmt,totalAmountDue,totalBalsAmt,totalWaiveOffAmount,totalAdviceAmount,totalAdjustedAmount,chargeCode,adviceAmt,adjustedAmt,adviceId
	private String date;
	private String charges;
	private String chargeCode;
	private String adviceAmt;
	private String adjustedAmt;
	private String amountDue;
	private String balsAmt;
	private String totalAmountDue;
	private String totalBalsAmt;
	private String totalWaiveOffAmount;
	private String totalAdviceAmount;
	private String totalAdjustedAmount;
	private String recordId;
	private String adviceId;
	private String errorFlag;
	private String errorMsg;
	private String realize;
	private String vClosureType;
	private String vClosureTypeDesc;
//	Amit Changes Starts
	private String recStatus;
//	Amit Changes Ends
	
	public String getvClosureType() {
		return vClosureType;
	}
	public void setvClosureType(String vClosureType) {
		this.vClosureType = vClosureType;
	}
	
	
	public String getvClosureTypeDesc() {
		return vClosureTypeDesc;
	}
	public void setvClosureTypeDesc(String vClosureTypeDesc) {
		this.vClosureTypeDesc = vClosureTypeDesc;
	}
	public String getRealize() {
		return realize;
	}
	public void setRealize(String realize) {
		this.realize = realize;
	}
	
	public String getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getTotalAdviceAmount() {
		return totalAdviceAmount;
	}
	public void setTotalAdviceAmount(String totalAdviceAmount) {
		this.totalAdviceAmount = totalAdviceAmount;
	}
	public String getTotalAdjustedAmount() {
		return totalAdjustedAmount;
	}
	public void setTotalAdjustedAmount(String totalAdjustedAmount) {
		this.totalAdjustedAmount = totalAdjustedAmount;
	}
	public String getAdviceId() {
		return adviceId;
	}
	public void setAdviceId(String adviceId) {
		this.adviceId = adviceId;
	}
	public String getChargeCode() {
		return chargeCode;
	}
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}
	public String getAdviceAmt() {
		return adviceAmt;
	}
	public void setAdviceAmt(String adviceAmt) {
		this.adviceAmt = adviceAmt;
	}
	public String getAdjustedAmt() {
		return adjustedAmt;
	}
	public void setAdjustedAmt(String adjustedAmt) {
		this.adjustedAmt = adjustedAmt;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getTotalPayable() {
		return totalPayable;
	}
	public void setTotalPayable(String totalPayable) {
		this.totalPayable = totalPayable;
	}
	public String getTotalAmountDue() {
		return totalAmountDue;
	}
	public void setTotalAmountDue(String totalAmountDue) {
		this.totalAmountDue = totalAmountDue;
	}
	public String getTotalBalsAmt() {
		return totalBalsAmt;
	}
	public void setTotalBalsAmt(String totalBalsAmt) {
		this.totalBalsAmt = totalBalsAmt;
	}
	public String getTotalWaiveOffAmount() {
		return totalWaiveOffAmount;
	}
	public void setTotalWaiveOffAmount(String totalWaiveOffAmount) {
		this.totalWaiveOffAmount = totalWaiveOffAmount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCharges() {
		return charges;
	}
	public void setCharges(String charges) {
		this.charges = charges;
	}
	public String getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(String amountDue) {
		this.amountDue = amountDue;
	}
	public String getBalsAmt() {
		return balsAmt;
	}
	public void setBalsAmt(String balsAmt) {
		this.balsAmt = balsAmt;
	}
	public String getAdvanceEmiRefunds() {
		return advanceEmiRefunds;
	}
	public void setAdvanceEmiRefunds(String advanceEmiRefunds) {
		this.advanceEmiRefunds = advanceEmiRefunds;
	}
	//Added By Arun
	public String getTotalRecevable() {
		return totalRecevable;
	}
	public void setTotalRecevable(String totalRecevable) {
		this.totalRecevable = totalRecevable;
	}
	private String makerDate;
	private String authorId;
	private String authorDate;
	private String authorRemarks;
	
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	public String getSecureDepositTDS() {
		return secureDepositTDS;
	}
	public void setSecureDepositTDS(String secureDepositTDS) {
		this.secureDepositTDS = secureDepositTDS;
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
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getInitiationDate() {
		return initiationDate;
	}
	public void setInitiationDate(String initiationDate) {
		this.initiationDate = initiationDate;
	}
	public String getLoanAc() {
		return loanAc;
	}
	public void setLoanAc(String loanAc) {
		this.loanAc = loanAc;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
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
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getOriginalTenure() {
		return originalTenure;
	}
	public void setOriginalTenure(String originalTenure) {
		this.originalTenure = originalTenure;
	}
	public String getRemainingTenure() {
		return remainingTenure;
	}
	public void setRemainingTenure(String remainingTenure) {
		this.remainingTenure = remainingTenure;
	}
	public String getReasonForClosure() {
		return reasonForClosure;
	}
	public void setReasonForClosure(String reasonForClosure) {
		this.reasonForClosure = reasonForClosure;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getBalancePrincipal() {
		return balancePrincipal;
	}
	public void setBalancePrincipal(String balancePrincipal) {
		this.balancePrincipal = balancePrincipal;
	}
	public String getOverdueInstallments() {
		return overdueInstallments;
	}
	public void setOverdueInstallments(String overdueInstallments) {
		this.overdueInstallments = overdueInstallments;
	}
	public String getSecureDeposit() {
		return secureDeposit;
	}
	public void setSecureDeposit(String secureDeposit) {
		this.secureDeposit = secureDeposit;
	}
	public String getSecureDepositInterest() {
		return secureDepositInterest;
	}
	public void setSecureDepositInterest(String secureDepositInterest) {
		this.secureDepositInterest = secureDepositInterest;
	}
	public String getInterestTillDate() {
		return interestTillDate;
	}
	public void setInterestTillDate(String interestTillDate) {
		this.interestTillDate = interestTillDate;
	}
	public String getForeClosurePenalty() {
		return foreClosurePenalty;
	}
	public void setForeClosurePenalty(String foreClosurePenalty) {
		this.foreClosurePenalty = foreClosurePenalty;
	}
	public String getNetReceivablePayable() {
		return netReceivablePayable;
	}
	public void setNetReceivablePayable(String netReceivablePayable) {
		this.netReceivablePayable = netReceivablePayable;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setClosureType(String closureType) {
		this.closureType = closureType;
	}
	public String getClosureType() {
		return closureType;
	}
	public void setUnBilledInstallments(String unBilledInstallments) {
		this.unBilledInstallments = unBilledInstallments;
	}
	public String getUnBilledInstallments() {
		return unBilledInstallments;
	}
	public void setOtherDues(String otherDues) {
		this.otherDues = otherDues;
	}
	public String getOtherDues() {
		return otherDues;
	}
	public void setOtherRefunds(String otherRefunds) {
		this.otherRefunds = otherRefunds;
	}
	public String getOtherRefunds() {
		return otherRefunds;
	}
	public void setGapSDInterest(String gapSDInterest) {
		this.gapSDInterest = gapSDInterest;
	}
	public String getGapSDInterest() {
		return gapSDInterest;
	}
	public void setGapSDTDS(String gapSDTDS) {
		this.gapSDTDS = gapSDTDS;
	}
	public String getGapSDTDS() {
		return gapSDTDS;
	}
	public void setTerminationId(String terminationId) {
		this.terminationId = terminationId;
	}
	public String getTerminationId() {
		return terminationId;
	}
	public String getLppAmount() {
		return lppAmount;
	}
	public void setLppAmount(String lppAmount) {
		this.lppAmount = lppAmount;
	}
	public String getInterstTillLP() {
		return interstTillLP;
	}
	public void setInterstTillLP(String interstTillLP) {
		this.interstTillLP = interstTillLP;
	}
	public void setEarlyClosureWarn(String earlyClosureWarn) {
		this.earlyClosureWarn = earlyClosureWarn;
	}
	public String getEarlyClosureWarn() {
		return earlyClosureWarn;
	}
	public void setProcWarning(String procWarning) {
		this.procWarning = procWarning;
	}
	public String getProcWarning() {
		return procWarning;
	}
	public void setRemainingInstallments(String remainingInstallments) {
		this.remainingInstallments = remainingInstallments;
	}
	public String getRemainingInstallments() {
		return remainingInstallments;
	}
	public void setOverduePrincipal(String overduePrincipal) {
		this.overduePrincipal = overduePrincipal;
	}
	public String getOverduePrincipal() {
		return overduePrincipal;
	}
	public void setWaiveOffAmount(String waiveOffAmount) {
		this.waiveOffAmount = waiveOffAmount;
	}
	public String getWaiveOffAmount() {
		return waiveOffAmount;
	}
//	Amit Changes Starts
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRecStatus() {
		return recStatus;
	}
//	Amit Changes Ends
}
