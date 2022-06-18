package com.cm.vo;

public class ClosureAuthorVO {
	private String loanId;
	private String decision;
	private String comments;
	private String authorId;
	private String authorDate;
	private String closureStatus;
	private int companyId;
	
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setClosureStatus(String closureStatus) {
		this.closureStatus = closureStatus;
	}
	public String getClosureStatus() {
		return closureStatus;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getCompanyId() {
		return companyId;
	}

}
