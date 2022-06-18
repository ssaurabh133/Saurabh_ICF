package com.cp.vo;

public class CommonPageSecVo {
	private String[] dealId;
	private String[] dealLoanId;
	private String[] creditType;
	private String[] sancAmt;
	private String[] underTenure;
	private String[] emi;
	private String[] sancDate;
	private String useId;
	private String bussinessDate;
	private String[] busIrr;
	private String[] effectiveRate;
	private String[] grossLtv;
	private String[] foir;
	//ankita
	 private String[] lbxValue;
	 
	 
	public String[] getGrossLtv() {
		return grossLtv;
	}
	public void setGrossLtv(String[] grossLtv) {
		this.grossLtv = grossLtv;
	}
	public String[] getFoir() {
		return foir;
	}
	public void setFoir(String[] foir) {
		this.foir = foir;
	}
	
	
	private String minIRR;
	private String maxIRR;
	
	public String getMinIRR() {
		return minIRR;
	}
	public void setMinIRR(String minIRR) {
		this.minIRR = minIRR;
	}
	public String getMaxIRR() {
		return maxIRR;
	}
	public void setMaxIRR(String maxIRR) {
		this.maxIRR = maxIRR;
	}
	public String[] getEffectiveRate() {
		return effectiveRate;
	}
	public void setEffectiveRate(String[] effectiveRate) {
		this.effectiveRate = effectiveRate;
	}
	public String[] getBusIrr() {
		return busIrr;
	}
	public void setBusIrr(String[] busIrr) {
		this.busIrr = busIrr;
	}
	public String getUseId() {
		return useId;
	}
	public void setUseId(String useId) {
		this.useId = useId;
	}
	public String getBussinessDate() {
		return bussinessDate;
	}
	public void setBussinessDate(String bussinessDate) {
		this.bussinessDate = bussinessDate;
	}
	public String[] getSancDate() {
		return sancDate;
	}
	public void setSancDate(String[] sancDate) {
		this.sancDate = sancDate;
	}
	public void setDealId(String[] dealId) {
		this.dealId = dealId;
	}
	public String[] getDealId() {
		return dealId;
	}
	public void setCreditType(String[] creditType) {
		this.creditType = creditType;
	}
	public String[] getCreditType() {
		return creditType;
	}
	public void setSancAmt(String[] sancAmt) {
		this.sancAmt = sancAmt;
	}
	public String[] getSancAmt() {
		return sancAmt;
	}
	public void setUnderTenure(String[] underTenure) {
		this.underTenure = underTenure;
	}
	public String[] getUnderTenure() {
		return underTenure;
	}
	public void setEmi(String[] emi) {
		this.emi = emi;
	}
	public String[] getEmi() {
		return emi;
	}
	public void setDealLoanId(String[] dealLoanId) {
		this.dealLoanId = dealLoanId;
	}
	public String[] getDealLoanId() {
		return dealLoanId;
	}
	public String[] getLbxValue() {
		return lbxValue;
	}
	public void setLbxValue(String[] lbxValue) {
		this.lbxValue = lbxValue;
	}

}
