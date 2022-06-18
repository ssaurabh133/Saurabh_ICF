package com.webservice.cp.pdvo;

public class InsuranceMphDetail {

	private String partnerName;
	private String doc;
	private String dob;
	private String gender;
	private String tenureInYears;
	private String tenureInMonths;
	private String premiumTerm;
	private String productName;
	private String sumAssured;
	private String typeOfCover;
	private String rateOfInterest;
	private String riderTpdFlag;
	private String riderAciFlag;
	private String riderAdbFlag;
	private String riderIllnessFlag;
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTenureInYears() {
		return tenureInYears;
	}
	public void setTenureInYears(String tenureInYears) {
		this.tenureInYears = tenureInYears;
	}
	public String getTenureInMonths() {
		return tenureInMonths;
	}
	public void setTenureInMonths(String tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}
	public String getPremiumTerm() {
		return premiumTerm;
	}
	public void setPremiumTerm(String premiumTerm) {
		this.premiumTerm = premiumTerm;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSumAssured() {
		return sumAssured;
	}
	public void setSumAssured(String sumAssured) {
		this.sumAssured = sumAssured;
	}
	public String getTypeOfCover() {
		return typeOfCover;
	}
	public void setTypeOfCover(String typeOfCover) {
		this.typeOfCover = typeOfCover;
	}
	public String getRateOfInterest() {
		return rateOfInterest;
	}
	public void setRateOfInterest(String rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}
	public String getRiderTpdFlag() {
		return riderTpdFlag;
	}
	public void setRiderTpdFlag(String riderTpdFlag) {
		this.riderTpdFlag = riderTpdFlag;
	}
	public String getRiderAciFlag() {
		return riderAciFlag;
	}
	public void setRiderAciFlag(String riderAciFlag) {
		this.riderAciFlag = riderAciFlag;
	}
	public String getRiderAdbFlag() {
		return riderAdbFlag;
	}
	public void setRiderAdbFlag(String riderAdbFlag) {
		this.riderAdbFlag = riderAdbFlag;
	}
	public String getRiderIllnessFlag() {
		return riderIllnessFlag;
	}
	public void setRiderIllnessFlag(String riderIllnessFlag) {
		this.riderIllnessFlag = riderIllnessFlag;
	}
	@Override
	public String toString() {
		return "InsuranceMphDetail [partnerName=" + partnerName + ", doc="
				+ doc + ", dob=" + dob + ", gender=" + gender
				+ ", tenureInYears=" + tenureInYears + ", tenureInMonths="
				+ tenureInMonths + ", premiumTerm=" + premiumTerm
				+ ", productName=" + productName + ", sumAssured=" + sumAssured
				+ ", typeOfCover=" + typeOfCover + ", rateOfInterest="
				+ rateOfInterest + ", riderTpdFlag=" + riderTpdFlag
				+ ", riderAciFlag=" + riderAciFlag + ", riderAdbFlag="
				+ riderAdbFlag + ", riderIllnessFlag=" + riderIllnessFlag + "]";
	}
}
