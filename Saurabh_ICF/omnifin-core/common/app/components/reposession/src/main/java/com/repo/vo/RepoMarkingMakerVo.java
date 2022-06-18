//Author Name : Richa-->
//Date of Creation : 1 May 2013-->
//Purpose  : VO for repo marking-->
//Documentation : -->

package com.repo.vo;

import java.io.Serializable;

public class RepoMarkingMakerVo implements Serializable{
	
	private String loanNo;
	private String product;
	private String scheme;
	private String branch;
	private String lbxLoanId;
	private String balancePrincipal;
	private String overduePrincipal;
	private String overdueAmount;
	private String dpd;
	private String lbxReasonId;
	private String reasonDesc;
	private String remarks;
	private String updateForwardFlag;
	private String loanId;
	private String comments;
	private String decision;
	private String lbxScheme;
	private String lbxProduct;
	private String lbxBranch;
	private String lbxAgencyId;
	private String lbxProductSearchID;
	private String repoProduct;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	
	
	
	public String getRepoProduct() {
		return repoProduct;
	}
	public void setRepoProduct(String repoProduct) {
		this.repoProduct = repoProduct;
	}
	public String getLbxProductSearchID() {
		return lbxProductSearchID;
	}
	public void setLbxProductSearchID(String lbxProductSearchID) {
		this.lbxProductSearchID = lbxProductSearchID;
	}
	public String getLbxAgencyId() {
		return lbxAgencyId;
	}
	public void setLbxAgencyId(String lbxAgencyId) {
		this.lbxAgencyId = lbxAgencyId;
	}
	public String getLbxScheme() {
		return lbxScheme;
	}
	public void setLbxScheme(String lbxScheme) {
		this.lbxScheme = lbxScheme;
	}
	public String getLbxProduct() {
		return lbxProduct;
	}
	public void setLbxProduct(String lbxProduct) {
		this.lbxProduct = lbxProduct;
	}
	public String getLbxBranch() {
		return lbxBranch;
	}
	public void setLbxBranch(String lbxBranch) {
		this.lbxBranch = lbxBranch;
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
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	private String agency;
	
	public String getLbxLoanId() {
		return lbxLoanId;
	}
	public void setLbxLoanId(String lbxLoanId) {
		this.lbxLoanId = lbxLoanId;
	}
	private String loanAmount;
	
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getBalancePrincipal() {
		return balancePrincipal;
	}
	public void setBalancePrincipal(String balancePrincipal) {
		this.balancePrincipal = balancePrincipal;
	}
	public String getOverduePrincipal() {
		return overduePrincipal;
	}
	public void setOverduePrincipal(String overduePrincipal) {
		this.overduePrincipal = overduePrincipal;
	}
	public String getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	public String getDpd() {
		return dpd;
	}
	public void setDpd(String dpd) {
		this.dpd = dpd;
	}
	public String getLbxReasonId() {
		return lbxReasonId;
	}
	public void setLbxReasonId(String lbxReasonId) {
		this.lbxReasonId = lbxReasonId;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getUpdateForwardFlag() {
		return updateForwardFlag;
	}
	public void setUpdateForwardFlag(String updateForwardFlag) {
		this.updateForwardFlag = updateForwardFlag;
	}
	
	

}
