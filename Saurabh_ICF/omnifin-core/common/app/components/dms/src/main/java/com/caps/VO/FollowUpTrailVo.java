package com.caps.VO;

import org.apache.struts.action.ActionForm;

public class FollowUpTrailVo extends ActionForm {
	
	private String lbxLoanId;
	private String reportformat;
	private String loanno;
	private String lbxLoanno;
	private String user;
	private String lbxUserSearchId;
	private String fromdate;
	private String todate;
	private String producTCategory;
	private String loanClassification;
	private String branch;
	private String lbxBranchId;
	
	public String getProducTCategory() {
		return producTCategory;
	}
	public void setProducTCategory(String producTCategory) {
		this.producTCategory = producTCategory;
	}
	public String getLoanClassification() {
		return loanClassification;
	}
	public void setLoanClassification(String loanClassification) {
		this.loanClassification = loanClassification;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	
	public String getLbxLoanId() {
		return lbxLoanId;
	}
	public void setLbxLoanId(String lbxLoanId) {
		this.lbxLoanId = lbxLoanId;
	}
	public String getReportformat() {
		return reportformat;
	}
	public void setReportformat(String reportformat) {
		this.reportformat = reportformat;
	}
	public String getLoanno() {
		return loanno;
	}
	public void setLoanno(String loanno) {
		this.loanno = loanno;
	}
	public String getLbxLoanno() {
		return lbxLoanno;
	}
	public void setLbxLoanno(String lbxLoanno) {
		this.lbxLoanno = lbxLoanno;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getLbxUserSearchId() {
		return lbxUserSearchId;
	}
	public void setLbxUserSearchId(String lbxUserSearchId) {
		this.lbxUserSearchId = lbxUserSearchId;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	
	
	
}
	

