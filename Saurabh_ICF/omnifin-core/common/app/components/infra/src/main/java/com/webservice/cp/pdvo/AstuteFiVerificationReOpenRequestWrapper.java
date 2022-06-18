package com.webservice.cp.pdvo;

import java.util.List;

//import org.codehaus.jackson.annotate.JsonProperty;

public class AstuteFiVerificationReOpenRequestWrapper {
	List<AstuteFiVerificationReOpenRequest>fiVerificationList;
	public List<AstuteFiVerificationReOpenRequest> getFiVerificationList() {
		return fiVerificationList;
	}
	public void setFiVerificationList(
			List<AstuteFiVerificationReOpenRequest> fiVerificationList) {
		this.fiVerificationList = fiVerificationList;
	}
	@Override
	public String toString() {
		return "AstuteFiVerificationReOpenRequestWrapper [fiVerificationList="
				+ fiVerificationList + "]";
	}
}
