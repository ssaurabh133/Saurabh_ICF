package com.webservice.cp.pdvo;


public class CalculateAllEligibleSchemeRequest {

	private UserCredentials userCredentials;
	private EligibleNHBscheme eligibleNHBscheme;
	
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}
	public EligibleNHBscheme getEligibleNHBscheme() {
		return eligibleNHBscheme;
	}
	public void setEligibleNHBscheme(EligibleNHBscheme eligibleNHBscheme) {
		this.eligibleNHBscheme = eligibleNHBscheme;
	}
	@Override
	public String toString() {
		return "CalculateAllEligibleSchemeRequest [userCredentials="
				+ userCredentials + ", eligibleNHBscheme=" + eligibleNHBscheme
				+ "]";
	}
	
}
