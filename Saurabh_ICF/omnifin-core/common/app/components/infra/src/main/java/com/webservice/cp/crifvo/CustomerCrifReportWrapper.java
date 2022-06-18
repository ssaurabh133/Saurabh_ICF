package com.webservice.cp.crifvo;

import com.webservice.cp.pdvo.*;
import com.webservice.cp.crifvo.CrifVO;
public class CustomerCrifReportWrapper {

	private UserCredentials userCredentials;
	private CrifVO crifVo;
	private CustomerCrifRequest crifRequest;

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}

	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}

	public CustomerCrifRequest getCrifRequest() {
		return crifRequest;
	}

	public void setCrifRequest(CustomerCrifRequest crifRequest) {
		this.crifRequest = crifRequest;
	}

	public CrifVO getCrifVo() {
		return crifVo;
	}

	public void setCrifVo(CrifVO crifVo) {
		this.crifVo = crifVo;
	}

	@Override
	public String toString() {
		return "CustomerCrifReportWrapper [userCredentials=" + userCredentials
				+ ", crifVo=" + crifVo + ", crifRequest=" + crifRequest + "]";
	}

	
	
	
}
