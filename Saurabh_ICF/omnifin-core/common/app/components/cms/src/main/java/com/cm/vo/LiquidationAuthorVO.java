package com.cm.vo;

public class LiquidationAuthorVO {
	private String loanId;
	private String sdId;
	private String sdLiquidationId;
	private int companyId;
	private String decision;
	private String comments;
	private String authorId;
	private String autrhorDate;
	
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAutrhorDate() {
		return autrhorDate;
	}
	public void setAutrhorDate(String autrhorDate) {
		this.autrhorDate = autrhorDate;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getSdId() {
		return sdId;
	}
	public void setSdId(String sdId) {
		this.sdId = sdId;
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
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setSdLiquidationId(String sdLiquidationId) {
		this.sdLiquidationId = sdLiquidationId;
	}
	public String getSdLiquidationId() {
		return sdLiquidationId;
	}

}
