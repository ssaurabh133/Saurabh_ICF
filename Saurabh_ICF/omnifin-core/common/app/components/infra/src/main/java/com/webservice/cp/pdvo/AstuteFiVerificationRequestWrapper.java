package com.webservice.cp.pdvo;

import java.util.List;


public class AstuteFiVerificationRequestWrapper {

	List<AstuteFiVerificationRequest>fiVerificationList;

	public List<AstuteFiVerificationRequest> getFiVerificationList() {
		return fiVerificationList;
	}

	public void setFiVerificationList(
			List<AstuteFiVerificationRequest> fiVerificationList) {
		this.fiVerificationList = fiVerificationList;
	}

	@Override
	public String toString() {
		return "AstuteFiVerificationRequestWrapper [fiVerificationList="
				+ fiVerificationList + "]";
	}

}
