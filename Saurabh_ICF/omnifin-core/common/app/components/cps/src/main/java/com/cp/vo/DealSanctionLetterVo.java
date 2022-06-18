package com.cp.vo;

import org.apache.struts.upload.FormFile;

public class DealSanctionLetterVo {
	
	  private String dealNo;
	  private String customerName;
	  private String dealId;
	  private String lbxLoanNoHID;
	  private String lbxDealNoHID;
	  
	  private String loanNo;
	  private String InsurancePremium;
	  private String rois;
	  private String processingFee;
	  private String marginMoneyAmount;
	  private String marginMoneyRemarks;
	  private String userId;
	  private String makerDate;
		private String branchId;
		  private String loanId;
		
		
	  public String getLbxDealNoHID() {
			return lbxDealNoHID;
		}
		public void setLbxDealNoHID(String lbxDealNoHID) {
			this.lbxDealNoHID = lbxDealNoHID;
		}
	public String getLoanId() {
			return loanId;
		}
		public void setLoanId(String loanId) {
			this.loanId = loanId;
		}
	public String getMakerDate() {
			return makerDate;
		}
		public void setMakerDate(String makerDate) {
			this.makerDate = makerDate;
		}
	public String getDealNo() {
			return dealNo;
		}
		public void setDealNo(String dealNo) {
			this.dealNo = dealNo;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getDealId() {
			return dealId;
		}
		public void setDealId(String dealId) {
			this.dealId = dealId;
		}
		public String getLbxLoanNoHID() {
			return lbxLoanNoHID;
		}
		public void setLbxLoanNoHID(String lbxLoanNoHID) {
			this.lbxLoanNoHID = lbxLoanNoHID;
		}
		public String getLoanNo() {
			return loanNo;
		}
		public void setLoanNo(String loanNo) {
			this.loanNo = loanNo;
		}
		public String getInsurancePremium() {
			return InsurancePremium;
		}
		public void setInsurancePremium(String insurancePremium) {
			InsurancePremium = insurancePremium;
		}
		public String getRois() {
			return rois;
		}
		public void setRois(String rois) {
			this.rois = rois;
		}
		public String getProcessingFee() {
			return processingFee;
		}
		public void setProcessingFee(String processingFee) {
			this.processingFee = processingFee;
		}
		public String getMarginMoneyAmount() {
			return marginMoneyAmount;
		}
		public void setMarginMoneyAmount(String marginMoneyAmount) {
			this.marginMoneyAmount = marginMoneyAmount;
		}
		public String getMarginMoneyRemarks() {
			return marginMoneyRemarks;
		}
		public void setMarginMoneyRemarks(String marginMoneyRemarks) {
			this.marginMoneyRemarks = marginMoneyRemarks;
		}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	
	
	

}
