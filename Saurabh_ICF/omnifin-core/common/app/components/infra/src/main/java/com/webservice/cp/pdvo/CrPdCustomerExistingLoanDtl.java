package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdCustomerExistingLoanDtl {
	
	private Integer pdCustomerExistingLoanId;
	
	private Integer pdCustomerId;
	
	private String nameOfBorrower;
	
	private String bankName;
	
	private String typeOfLoan;
	
	private BigDecimal loanAmount;
	
	private BigDecimal emi;
	
	private Integer tenure;
	
	private BigDecimal balanceAmount;
	
	private Integer balanceEmi;
	
	private String saveAsDraft;
	
	private String makerId;
	
	private String makerDate;
	
	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;

	public Integer getPdCustomerExistingLoanId() {
		return pdCustomerExistingLoanId;
	}

	public void setPdCustomerExistingLoanId(Integer pdCustomerExistingLoanId) {
		this.pdCustomerExistingLoanId = pdCustomerExistingLoanId;
	}

	public Integer getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(Integer pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public String getNameOfBorrower() {
		return nameOfBorrower;
	}

	public void setNameOfBorrower(String nameOfBorrower) {
		this.nameOfBorrower = nameOfBorrower;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTypeOfLoan() {
		return typeOfLoan;
	}

	public void setTypeOfLoan(String typeOfLoan) {
		this.typeOfLoan = typeOfLoan;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getEmi() {
		return emi;
	}

	public void setEmi(BigDecimal emi) {
		this.emi = emi;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Integer getBalanceEmi() {
		return balanceEmi;
	}

	public void setBalanceEmi(Integer balanceEmi) {
		this.balanceEmi = balanceEmi;
	}

	public String getSaveAsDraft() {
		return saveAsDraft;
	}

	public void setSaveAsDraft(String saveAsDraft) {
		this.saveAsDraft = saveAsDraft;
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

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	@Override
	public String toString() {
		return "CrPdCustomerExistingLoanDtl [pdCustomerExistingLoanId="
				+ pdCustomerExistingLoanId + ", pdCustomerId=" + pdCustomerId
				+ ", nameOfBorrower=" + nameOfBorrower + ", bankName="
				+ bankName + ", typeOfLoan=" + typeOfLoan + ", loanAmount="
				+ loanAmount + ", emi=" + emi + ", tenure=" + tenure
				+ ", balanceAmount=" + balanceAmount + ", balanceEmi="
				+ balanceEmi + ", saveAsDraft=" + saveAsDraft + ", makerId="
				+ makerId + ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress + "]";
	}
	
}
