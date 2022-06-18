package com.cm.vo;

public class DisbursalAuthorVO {
	private String loanId;
	private String disbursalNo;
	private String comments;
	private String decision;
	private String authorId;
	private String authorDate;
	private int companyId;
	private String loanDisbursalId;
//Added By arun
	private String disbursalBatchId;
	
	
	public String getLoanDisbursalId() {
		return loanDisbursalId;
	}
	public void setLoanDisbursalId(String loanDisbursalId) {
		this.loanDisbursalId = loanDisbursalId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getDisbursalNo() {
		return disbursalNo;
	}
	public void setDisbursalNo(String disbursalNo) {
		this.disbursalNo = disbursalNo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setDisbursalBatchId(String disbursalBatchId) {
		this.disbursalBatchId = disbursalBatchId;
	}
	public String getDisbursalBatchId() {
		return disbursalBatchId;
	}

}
