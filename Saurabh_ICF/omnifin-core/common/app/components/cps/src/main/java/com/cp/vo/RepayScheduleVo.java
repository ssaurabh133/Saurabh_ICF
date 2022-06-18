package com.cp.vo;

public class RepayScheduleVo {
	
	private String instNo;
	private String dueDate;
	private String instAmount;
	private String principle;
	private String instCom;
	private String excess;
	private String advFlag;
	private String prinOS;
	private String loanRateMethod;
	private String finalRate;
	private String effectiveRate;
	private String mktIRR1;
	private String mktIRR2;
	private String bussIrr;
	private String dealIRR2;
	private int companyId;
	private String authorId;
	private String authorDate;
	private String instAmountRec;
	private String billFlag;
	private String loanId;
	private String effectiveDate;
	private String deferalMonth;
	private String prepayAmount;
	private String statusFlag;
	private String repayReshId;
	private String reshType;
	private String procval;
	private String procvalstatus;
	private String overDueDay;
	// add by saorabh
	private String upfrontRoundingAmount;
	private String vatAmount;
	private String ServiceAmount;
	//change by sachin
	private String otherCharges;
	private String txtNpv;
	
	// add by Abhishek
/*	private String upfrontRoundingAmount; //Abhishek
	private String vatAmount;
	private String ServiceAmount;*/
	
	public String getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(String otherCharges) {
		this.otherCharges = otherCharges;
	}
	//end by sachin
	public String getProcval() {
		return procval;
	}
	public void setProcval(String procval) {
		this.procval = procval;
	}
	
	public String getProcvalstatus() {
		return procvalstatus;
	}
	public void setProcvalstatus(String procvalstatus) {
		this.procvalstatus = procvalstatus;
	}
	public String getInstAmountRec() {
		return instAmountRec;
	}
	public void setInstAmountRec(String instAmountRec) {
		this.instAmountRec = instAmountRec;
	}
	public String getBillFlag() {
		return billFlag;
	}
	public void setBillFlag(String billFlag) {
		this.billFlag = billFlag;
	}
	public String getBussIrr() {
		return bussIrr;
	}
	public void setBussIrr(String bussIrr) {
		this.bussIrr = bussIrr;
	}
	public String getInstNo() {
		return instNo;
	}
	public void setInstNo(String instNo) {
		this.instNo = instNo;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getInstAmount() {
		return instAmount;
	}
	public void setInstAmount(String instAmount) {
		this.instAmount = instAmount;
	}
	public String getPrinciple() {
		return principle;
	}
	public void setPrinciple(String principle) {
		this.principle = principle;
	}
	public String getInstCom() {
		return instCom;
	}
	public void setInstCom(String instCom) {
		this.instCom = instCom;
	}
	public String getExcess() {
		return excess;
	}
	public void setExcess(String excess) {
		this.excess = excess;
	}
	public String getAdvFlag() {
		return advFlag;
	}
	public void setAdvFlag(String advFlag) {
		this.advFlag = advFlag;
	}
	public String getPrinOS() {
		return prinOS;
	}
	public void setPrinOS(String prinOS) {
		this.prinOS = prinOS;
	}
	public String getLoanRateMethod() {
		return loanRateMethod;
	}
	public void setLoanRateMethod(String loanRateMethod) {
		this.loanRateMethod = loanRateMethod;
	}
	public String getFinalRate() {
		return finalRate;
	}
	public void setFinalRate(String finalRate) {
		this.finalRate = finalRate;
	}
	public String getEffectiveRate() {
		return effectiveRate;
	}
	public void setEffectiveRate(String effectiveRate) {
		this.effectiveRate = effectiveRate;
	}
	public String getMktIRR1() {
		return mktIRR1;
	}
	public void setMktIRR1(String mktIRR1) {
		this.mktIRR1 = mktIRR1;
	}
	public String getMktIRR2() {
		return mktIRR2;
	}
	public void setMktIRR2(String mktIRR2) {
		this.mktIRR2 = mktIRR2;
	}
	public void setDealIRR2(String dealIRR2) {
		this.dealIRR2 = dealIRR2;
	}
	public String getDealIRR2() {
		return dealIRR2;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getCompanyId() {
		return companyId;
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
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanId() {
		return loanId;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setDeferalMonth(String deferalMonth) {
		this.deferalMonth = deferalMonth;
	}
	public String getDeferalMonth() {
		return deferalMonth;
	}
	public void setPrepayAmount(String prepayAmount) {
		this.prepayAmount = prepayAmount;
	}
	public String getPrepayAmount() {
		return prepayAmount;
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	public String getStatusFlag() {
		return statusFlag;
	}
	public void setRepayReshId(String repayReshId) {
		this.repayReshId = repayReshId;
	}
	public String getRepayReshId() {
		return repayReshId;
	}
	public void setReshType(String reshType) {
		this.reshType = reshType;
	}
	public String getReshType() {
		return reshType;
	}
	public void setOverDueDay(String overDueDay) {
		this.overDueDay = overDueDay;
	}
	public String getOverDueDay() {
		return overDueDay;
	}

	public String getUpfrontRoundingAmount() {
		return upfrontRoundingAmount;
	}
/*	public void setUpfrontRoundingAmount(String upfrontRoundingAmount) {
		this.upfrontRoundingAmount = upfrontRoundingAmount;
	}
	public String getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(String vatAmount) {
		this.vatAmount = vatAmount;
	}
	public String getServiceAmount() {
		return ServiceAmount;
	}
	public void setServiceAmount(String serviceAmount) {
		ServiceAmount = serviceAmount;
	}*/

	public String getTxtNpv() {
		return txtNpv;
	}
	public void setTxtNpv(String txtNpv) {
		this.txtNpv = txtNpv;
	}
	public void setUpfrontRoundingAmount(String upfrontRoundingAmount) {
		this.upfrontRoundingAmount = upfrontRoundingAmount;
	}
	public String getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(String vatAmount) {
		this.vatAmount = vatAmount;
	}
	public String getServiceAmount() {
		return ServiceAmount;
	}
	public void setServiceAmount(String serviceAmount) {
		ServiceAmount = serviceAmount;
	}

}
