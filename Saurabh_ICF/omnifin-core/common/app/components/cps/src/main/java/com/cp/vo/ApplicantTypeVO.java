package com.cp.vo;

import java.io.Serializable;

public class ApplicantTypeVO implements Serializable
{

	private String applicant_code;
	private String applicant_desc;
	
	public String getApplicant_code() {
		return applicant_code;
	}
	public void setApplicant_code(String applicantCode) {
		applicant_code = applicantCode;
	}
	public String getApplicant_desc() {
		return applicant_desc;
	}
	public void setApplicant_desc(String applicantDesc) {
		applicant_desc = applicantDesc;
	}
	
	
	
}
