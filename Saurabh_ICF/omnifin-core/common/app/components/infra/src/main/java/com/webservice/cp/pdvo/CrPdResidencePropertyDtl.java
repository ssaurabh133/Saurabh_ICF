package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdResidencePropertyDtl {

	private Integer id;

	private Integer pdId;

	private String ownerName;

	private Integer propertySize;

	private BigDecimal marketValue;

	private String occupancyStatus;

	private BigDecimal agreementValue;

	private String contributionDetails;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getPropertySize() {
		return propertySize;
	}

	public void setPropertySize(Integer propertySize) {
		this.propertySize = propertySize;
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}

	public String getOccupancyStatus() {
		return occupancyStatus;
	}

	public void setOccupancyStatus(String occupancyStatus) {
		this.occupancyStatus = occupancyStatus;
	}

	public BigDecimal getAgreementValue() {
		return agreementValue;
	}

	public void setAgreementValue(BigDecimal agreementValue) {
		this.agreementValue = agreementValue;
	}

	public String getContributionDetails() {
		return contributionDetails;
	}

	public void setContributionDetails(String contributionDetails) {
		this.contributionDetails = contributionDetails;
	}

	@Override
	public String toString() {
		return "CrPdResidencePropertyDtl [id=" + id + ", pdId=" + pdId
				+ ", ownerName=" + ownerName + ", propertySize=" + propertySize
				+ ", marketValue=" + marketValue + ", occupancyStatus="
				+ occupancyStatus + ", agreementValue=" + agreementValue
				+ ", contributionDetails=" + contributionDetails + "]";
	}
}
