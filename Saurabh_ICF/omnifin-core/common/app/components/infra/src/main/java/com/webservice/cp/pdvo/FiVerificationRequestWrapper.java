package com.webservice.cp.pdvo;

public class FiVerificationRequestWrapper {
	private UserCredentials userCredentials;
	private AstuteFiVerificationRequestWrapper verification;
	private AstuteFiVerificationReOpenRequestWrapper verificationReOpen;
	
	private String workingUserId;
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	public AstuteFiVerificationRequestWrapper getVerification() {
		return verification;
	}
	public void setVerification(AstuteFiVerificationRequestWrapper verification) {
		this.verification = verification;
	}
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}
	public String getWorkingUserId() {
		return workingUserId;
	}
	public void setWorkingUserId(String workingUserId) {
		this.workingUserId = workingUserId;
	}
	public AstuteFiVerificationReOpenRequestWrapper getVerificationReOpen() {
		return verificationReOpen;
	}
	public void setVerificationReOpen(
			AstuteFiVerificationReOpenRequestWrapper verificationReOpen) {
		this.verificationReOpen = verificationReOpen;
	}
	@Override
	public String toString() {
		return "FiVerificationRequestWrapper [userCredentials="
				+ userCredentials + ", verification=" + verification
				+ ", verificationReOpen=" + verificationReOpen
				+ ", workingUserId=" + workingUserId + "]";
	}
}
