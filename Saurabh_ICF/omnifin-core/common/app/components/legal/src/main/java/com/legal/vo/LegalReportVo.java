//Author Name : Vinod Kumar Gupta-->
//Date of Creation : 11-04-2013-->
//Purpose  : vo for Legal report-->
//Documentation : -->

package com.legal.vo;

import java.io.Serializable;

public class LegalReportVo implements Serializable{
	

	
	private String loanNo;
	private String caseTypeDesc;
	private String advocateDesc;
	private String lbxLoanId;
	private String lbxCaseType;
	private String lbxAdvocateId;
	private String lbxCaseTypeCode;
	private String lbxUserId;
	
	
	public String getLbxCaseTypeCode() {
		return lbxCaseTypeCode;
	}
	public void setLbxCaseTypeCode(String lbxCaseTypeCode) {
		this.lbxCaseTypeCode = lbxCaseTypeCode;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getCaseTypeDesc() {
		return caseTypeDesc;
	}
	public void setCaseTypeDesc(String caseTypeDesc) {
		this.caseTypeDesc = caseTypeDesc;
	}
	public String getAdvocateDesc() {
		return advocateDesc;
	}
	public void setAdvocateDesc(String advocateDesc) {
		this.advocateDesc = advocateDesc;
	}
	public String getLbxLoanId() {
		return lbxLoanId;
	}
	public void setLbxLoanId(String lbxLoanId) {
		this.lbxLoanId = lbxLoanId;
	}
	public String getLbxCaseType() {
		return lbxCaseType;
	}
	public void setLbxCaseType(String lbxCaseType) {
		this.lbxCaseType = lbxCaseType;
	}
	public String getLbxAdvocateId() {
		return lbxAdvocateId;
	}
	public void setLbxAdvocateId(String lbxAdvocateId) {
		this.lbxAdvocateId = lbxAdvocateId;
	}
	
	
}
