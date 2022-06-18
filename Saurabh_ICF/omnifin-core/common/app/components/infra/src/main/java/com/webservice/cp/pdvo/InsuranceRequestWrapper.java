package com.webservice.cp.pdvo;

public class InsuranceRequestWrapper {

	private UserCredentials userCredentials;
	private InsuranceMphDetail mphDetail;
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}
	public InsuranceMphDetail getMphDetail() {
		return mphDetail;
	}
	public void setMphDetail(InsuranceMphDetail mphDetail) {
		this.mphDetail = mphDetail;
	}
	@Override
	public String toString() {
		return "InsuranceRequestWrapper [userCredentials=" + userCredentials
				+ ", mphDetail=" + mphDetail + "]";
	}
}
