package com.gcd.VO;

public class IndividualDetailVo {

	private String individualName;
	private String individualCode;
	private String individualCategory;
	private String dob;
	private String pan;
	private String email;
	private String referredBy;
	private String blackListed;
	private String reasonForBlackListed;
	private String constitution;
	
	
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	public String getIndividualName() {
		return individualName;
	}
	public void setIndividualName(String individualName) {
		this.individualName = individualName;
	}
	public String getIndividualCode() {
		return individualCode;
	}
	public void setIndividualCode(String individualCode) {
		this.individualCode = individualCode;
	}
	public String getIndividualCategory() {
		return individualCategory;
	}
	public void setIndividualCategory(String individualCategory) {
		this.individualCategory = individualCategory;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReferredBy() {
		return referredBy;
	}
	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}
	public String getBlackListed() {
		return blackListed;
	}
	public void setBlackListed(String blackListed) {
		this.blackListed = blackListed;
	}
	public String getReasonForBlackListed() {
		return reasonForBlackListed;
	}
	public void setReasonForBlackListed(String reasonForBlackListed) {
		this.reasonForBlackListed = reasonForBlackListed;
	}
	
	
}
