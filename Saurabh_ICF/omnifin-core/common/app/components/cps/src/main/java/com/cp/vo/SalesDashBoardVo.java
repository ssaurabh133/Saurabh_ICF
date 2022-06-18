package com.cp.vo;

public class SalesDashBoardVo {
	private String id;
	private String dealNo;
	private String formNo;
	private String customerName;
	private String dealIntitionDate;
	
	private String roName;
	private String rmName;
	private String branchId;
	private String branchName;
	private String daysPending;
	private String makerId;
	private String makerName;
	private String status;
	private String source;
	private String loginUserId;
	private String operationDate;
	private String dealId;
	
	private String queryDtl;
	private String queryDate;
	private String queryMakerId;
	private String userId;
	private String department;
	private String reason;
	
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getQueryDtl() {
		return queryDtl;
	}
	public void setQueryDtl(String queryDtl) {
		this.queryDtl = queryDtl;
	}
	public String getQueryDate() {
		return queryDate;
	}
	@Override
	public String toString() {
		return "SalesDashBoardVo [id=" + id + ", dealNo=" + dealNo
				+ ", formNo=" + formNo + ", customerName=" + customerName
				+ ", dealIntitionDate=" + dealIntitionDate + ", roName="
				+ roName + ", rmName=" + rmName + ", branchId=" + branchId
				+ ", branchName=" + branchName + ", daysPending=" + daysPending
				+ ", makerId=" + makerId + ", makerName=" + makerName
				+ ", status=" + status + ", source=" + source
				+ ", loginUserId=" + loginUserId + ", operationDate="
				+ operationDate + ", dealId=" + dealId + ", queryDtl="
				+ queryDtl + ", queryDate=" + queryDate + ", queryMakerId="
				+ queryMakerId + ", userId=" + userId + ", department="
				+ department + ", reason=" + reason + "]";
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public String getQueryMakerId() {
		return queryMakerId;
	}
	public void setQueryMakerId(String queryMakerId) {
		this.queryMakerId = queryMakerId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getFormNo() {
		return formNo;
	}
	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getDealIntitionDate() {
		return dealIntitionDate;
	}
	public void setDealIntitionDate(String dealIntitionDate) {
		this.dealIntitionDate = dealIntitionDate;
	}
	public String getRoName() {
		return roName;
	}
	public void setRoName(String roName) {
		this.roName = roName;
	}
	public String getRmName() {
		return rmName;
	}
	public void setRmName(String rmName) {
		this.rmName = rmName;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getDaysPending() {
		return daysPending;
	}
	public void setDaysPending(String daysPending) {
		this.daysPending = daysPending;
	}
	public String getMakerName() {
		return makerName;
	}
	public void setMakerName(String makerName) {
		this.makerName = makerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}	 

}
