package com.webservice.cp.pdvo;
public class PanRequestWrapper {

	private UserCredentials userCredentials;
	private OmnifinPANRequest panRequest;

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}

	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}

	public OmnifinPANRequest getPanRequest() {
		return panRequest;
	}

	public void setPanRequest(OmnifinPANRequest panRequest) {
		this.panRequest = panRequest;
	}

	@Override
	public String toString() {
		return "PanRequestWrapper [userCredentials=" + userCredentials
				+ ", panRequest=" + panRequest + "]";
	}

}
