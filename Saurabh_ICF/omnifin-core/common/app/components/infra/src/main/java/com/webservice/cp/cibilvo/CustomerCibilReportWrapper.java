package com.webservice.cp.cibilvo;

import com.webservice.cp.pdvo.*;

public class CustomerCibilReportWrapper {

	private UserCredentials userCredentials;
	
	private CustomerCibilRequest cibilRequest;

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}

	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}

	public CustomerCibilRequest getCibilRequest() {
		return cibilRequest;
	}

	public void setCibilRequest(CustomerCibilRequest cibilRequest) {
		this.cibilRequest = cibilRequest;
	}

	@Override
	public String toString() {
		return "CustomerCibilReportWrapper [userCredentials=" + userCredentials
				+ ", cibilRequest=" + cibilRequest + "]";
	}
	
}
