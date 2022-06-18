package com.webservice.cp.pdvo;

import java.util.List;

public class AstuteFiVerificationResponseWrapper {

	List<AstuteFiVerificationResponse>CaseDetails;

	public List<AstuteFiVerificationResponse> getCaseDetails() {
		return CaseDetails;
	}

	public void setCaseDetails(List<AstuteFiVerificationResponse> caseDetails) {
		CaseDetails = caseDetails;
	}

	@Override
	public String toString() {
		return "AstuteFiVerificationResponseWrapper [CaseDetails="
				+ CaseDetails + ", getCaseDetails()=" + getCaseDetails()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	
}
