package com.VO;

import java.io.Serializable;

public class CorporateBusinessActivityVO implements Serializable {
	private String brands;
	private String productServices;
	private String noOfEmployees;
	private String auditors;
	private String certifications;
	private String awards;
	private String assocoationMembershipName;
	private String customerId;
	private String recStatus;
	private String makerId;
	private String makerDate;
	private String pageStat;
	private String updateFlag;
	private String statusCase;
	private String loanId;
	
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public String getProductServices() {
		return productServices;
	}
	public void setProductServices(String productServices) {
		this.productServices = productServices;
	}
	public String getNoOfEmployees() {
		return noOfEmployees;
	}
	public void setNoOfEmployees(String noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}
	public String getAuditors() {
		return auditors;
	}
	public void setAuditors(String auditors) {
		this.auditors = auditors;
	}
	public String getCertifications() {
		return certifications;
	}
	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
	}
	public String getAssocoationMembershipName() {
		return assocoationMembershipName;
	}
	public void setAssocoationMembershipName(String assocoationMembershipName) {
		this.assocoationMembershipName = assocoationMembershipName;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setPageStat(String pageStat) {
		this.pageStat = pageStat;
	}
	public String getPageStat() {
		return pageStat;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setStatusCase(String statusCase) {
		this.statusCase = statusCase;
	}
	public String getStatusCase() {
		return statusCase;
	}
}