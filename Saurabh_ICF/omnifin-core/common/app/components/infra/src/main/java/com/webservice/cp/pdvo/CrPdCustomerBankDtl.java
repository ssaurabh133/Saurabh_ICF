package com.webservice.cp.pdvo;


public class CrPdCustomerBankDtl {
	
	private Integer pdCustomerBankId;
	
	private Integer pdCustomerId;
	
	private String holderName;
	private Integer bankId;
	private String bankName;
	
	private Integer accountSince;
	
	private String accountType;
	
	private String accountNo;
	
	private String saveAsDraft;
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;

	public Integer getPdCustomerBankId() {
		return pdCustomerBankId;
	}

	public void setPdCustomerBankId(Integer pdCustomerBankId) {
		this.pdCustomerBankId = pdCustomerBankId;
	}

	public Integer getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(Integer pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getAccountSince() {
		return accountSince;
	}

	public void setAccountSince(Integer accountSince) {
		this.accountSince = accountSince;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
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
		return "CrPdCustomerBankDtl [pdCustomerBankId=" + pdCustomerBankId
				+ ", pdCustomerId=" + pdCustomerId + ", holderName="
				+ holderName + ", bankId=" + bankId + ", bankName=" + bankName
				+ ", accountSince=" + accountSince + ", accountType="
				+ accountType + ", accountNo=" + accountNo + ", saveAsDraft="
				+ saveAsDraft + ", makerId=" + makerId + ", makerDate="
				+ makerDate + ", operationDate=" + operationDate
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", locationAddress=" + locationAddress + "]";
	}

}
