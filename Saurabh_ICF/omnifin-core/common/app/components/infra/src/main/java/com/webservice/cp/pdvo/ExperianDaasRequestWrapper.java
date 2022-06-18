package com.webservice.cp.pdvo;


public class ExperianDaasRequestWrapper {
	
	private UserCredentials userCredentials;
	private Integer dealId;
	private String requestType;
	
	
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public Integer getDealId() {
		return dealId;
	}
	public void setDealId(Integer dealId) {
		this.dealId = dealId;
	}
	
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}

	@Override
	public String toString() {
		return "ExperianScoreCardRequestWrapper [userCredentials="
				+ userCredentials + ", dealId=" + dealId + "]";
	}
}
	