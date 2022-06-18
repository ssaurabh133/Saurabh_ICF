package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdProposedPropertyDtl {
	
	private Integer pdPropertyId;
	
	private Integer pdId;
	
	private String type;
	
	private String possession;
	
	private String ownershipType;
	
	private String ownershipName;
	
	private BigDecimal marketValue;
	
	private BigDecimal agreementValue;
	
	private BigDecimal cashContribution;
	
	private BigDecimal chequeContribution;
	
	private String endUse;
	
	private String saveAsDraft;
	
	private String makerId;
	
	private String makerDate;

	private String operationDate;
	
	private String longitude;
	
	private String latitude;
	
	private String locationAddress;
	
	private String otherPossession;

	public String getOtherPossession() {
		return otherPossession;
	}

	public void setOtherPossession(String otherPossession) {
		this.otherPossession = otherPossession;
	}

	public Integer getPdPropertyId() {
		return pdPropertyId;
	}

	public void setPdPropertyId(Integer pdPropertyId) {
		this.pdPropertyId = pdPropertyId;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPossession() {
		return possession;
	}

	public void setPossession(String possession) {
		this.possession = possession;
	}

	public String getOwnershipType() {
		return ownershipType;
	}

	public void setOwnershipType(String ownershipType) {
		this.ownershipType = ownershipType;
	}

	public String getOwnershipName() {
		return ownershipName;
	}

	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}

	public BigDecimal getAgreementValue() {
		return agreementValue;
	}

	public void setAgreementValue(BigDecimal agreementValue) {
		this.agreementValue = agreementValue;
	}

	public BigDecimal getCashContribution() {
		return cashContribution;
	}

	public void setCashContribution(BigDecimal cashContribution) {
		this.cashContribution = cashContribution;
	}

	public BigDecimal getChequeContribution() {
		return chequeContribution;
	}

	public void setChequeContribution(BigDecimal chequeContribution) {
		this.chequeContribution = chequeContribution;
	}

	public String getEndUse() {
		return endUse;
	}

	public void setEndUse(String endUse) {
		this.endUse = endUse;
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
		return "CrPdProposedPropertyDtl [pdPropertyId=" + pdPropertyId
				+ ", pdId=" + pdId + ", type=" + type + ", possession="
				+ possession + ", ownershipType=" + ownershipType
				+ ", ownershipName=" + ownershipName + ", marketValue="
				+ marketValue + ", agreementValue=" + agreementValue
				+ ", cashContribution=" + cashContribution
				+ ", chequeContribution=" + chequeContribution + ", endUse="
				+ endUse + ", saveAsDraft=" + saveAsDraft + ", makerId="
				+ makerId + ", makerDate=" + makerDate + ", operationDate="
				+ operationDate + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationAddress=" + locationAddress
				+ ", otherPossession=" + otherPossession + "]";
	}
}
