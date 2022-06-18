package com.webservice.cp.pdvo;

public class InsuranceResponseWrapper {

	private Integer basePremium;
	private Integer premInclTax;
	private String errorText;
	public Integer getBasePremium() {
		return basePremium;
	}
	public void setBasePremium(Integer basePremium) {
		this.basePremium = basePremium;
	}
	public Integer getPremInclTax() {
		return premInclTax;
	}
	public void setPremInclTax(Integer premInclTax) {
		this.premInclTax = premInclTax;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	@Override
	public String toString() {
		return "InsuranceResponseWrapper [basePremium=" + basePremium
				+ ", premInclTax=" + premInclTax + ", errorText=" + errorText
				+ "]";
	}
}
