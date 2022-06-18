/*
Author Name :- Vishal Singh
Date of Creation :26-03-2012
Purpose :-  Repay Schedule Maker/Author
*/

package com.cm.vo;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

public class UpdateRepayscheduleSearchVO {

	
	private String loanNo;
	private String lbxLoanNoHID;
	private String customerName;
	private String loanAmount;
	private String product;
	private String scheme;
	private String loanApprovalDate;
	private String branchId;
	private String userId;
	private String stage;
	private String reschId;
	private String reportingToUserId;
	private int currentPageLink;
	private int totalRecordSize;
	private String lbxUserId;
	private String repaymentType;
	
	
	private String disbursedAmount;
	
	private String outstandingLoanAmount;
	private String requestRefNo;
	private String curentDueDate;
	private String emi;
	
	private String dueDay;
	
	private String authorRemarks;
	private String currerntDueDay;
	private String reschDate;
	private String nextDueDate;
	private String reschCharges;
	private String resStatus;
	
	private String makerRemarks;
	private String makerDate;
	private String makerId;
	
	
	private String deferralFromInstallment;
	private String lbxInstlNo;
	private String repayEffDate;
	private String loanFrequency;

	
	
	public String getLoanFrequency() {
		return loanFrequency;
	}
	public void setLoanFrequency(String loanFrequency) {
		this.loanFrequency = loanFrequency;
	}
	private String reschType;
	
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getReportingToUserId() {
		return reportingToUserId;
	}
	public void setReportingToUserId(String reportingToUserId) {
		this.reportingToUserId = reportingToUserId;
	}
	
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
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
	public String getLoanApprovalDate() {
		return loanApprovalDate;
	}
	public void setLoanApprovalDate(String loanApprovalDate) {
		this.loanApprovalDate = loanApprovalDate;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public void setReschId(String reschId) {
		this.reschId = reschId;
	}
	public String getReschId() {
		return reschId;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	
	public String getDisbursedAmount() {
		return disbursedAmount;
	}
	public void setDisbursedAmount(String disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}
	
	public String getOutstandingLoanAmount() {
		return outstandingLoanAmount;
	}
	public void setOutstandingLoanAmount(String outstandingLoanAmount) {
		this.outstandingLoanAmount = outstandingLoanAmount;
	}
	
	
	public String getCurentDueDate() {
		return curentDueDate;
	}
	
	public void setCurentDueDate(String curentDueDate) {
		this.curentDueDate = curentDueDate;
	}
		
	public String getRequestRefNo() {
		return requestRefNo;
	}	
	
	public void setRequestRefNo(String requestRefNo) {
		this.requestRefNo = requestRefNo;
	}
	
	public String getEmi() {
		return emi;
	}	
	
	public void setEmi(String emi) {
		this.emi = emi;
	}
	
	public String getDueDay() {
		return dueDay;
	}	
	
	public void setDueDay(String dueDay) {
		this.dueDay = dueDay;
	}
	
	public String getCurrentDueDay() {
		return currerntDueDay;
	}	
	
	public void setCurrentDueDay(String currerntDueDay) {
		this.currerntDueDay = currerntDueDay;
	}
	
	public String getNextDueDate() {
		return nextDueDate;
	}	
	
	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	
	
	public String getAuthorRemarks() {
		return authorRemarks;
	}	
	
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	
	public String getreschDate() {
		return reschDate;
	}	
	
	public void setreschDate(String reschDate) {
		this.reschDate = reschDate;
	}
	
	public String getreschCharges() {
		return reschCharges;
	}	
	
	public void setreschCharges(String reschCharges) {
		this.reschCharges = reschCharges;
	}
	
	
	public String getresStatus() {
		return resStatus;
	}	
	
	public void setresStatus(String resStatus) {
		this.resStatus = resStatus;
	}
	
	public String getmakerRemarks() {
		return makerRemarks;
	}	
	
	public void setmakerRemarks(String makerRemarks) {
		this.makerRemarks = makerRemarks;
	}
	
	public String getmakerDate() {
		return makerDate;
	}	
	
	public void setmakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	
	public String getMakerId() {
		return makerId;
	}	
	
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	
	public String getreschType() {
		return reschType;
	}	
	
	public void setreschType(String reschType) {
		this.reschType = reschType;
	}
	
	public void reset(ActionMapping mapping,HttpServletRequest request){
		this.lbxLoanNoHID = "";
		
		
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setDeferralFromInstallment(String deferralFromInstallment) {
		this.deferralFromInstallment = deferralFromInstallment;
	}
	public String getDeferralFromInstallment() {
		return deferralFromInstallment;
	}
	public void setLbxInstlNo(String lbxInstlNo) {
		this.lbxInstlNo = lbxInstlNo;
	}
	public String getLbxInstlNo() {
		return lbxInstlNo;
	}
	public void setRepayEffDate(String repayEffDate) {
		this.repayEffDate = repayEffDate;
	}
	public String getRepayEffDate() {
		return repayEffDate;
	}
	
}
