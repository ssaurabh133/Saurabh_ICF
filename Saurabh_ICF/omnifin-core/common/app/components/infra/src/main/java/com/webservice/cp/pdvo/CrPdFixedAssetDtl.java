package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdFixedAssetDtl {

	private Integer id;

	private Integer pdId;

	private String description;

	private String address;

	private BigDecimal assetvalue;

	private String ownership;

	private String occupancy;

	private String fundedby;

	private String collateralproperty;

	private String makerDate;

	private String makerId;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAssetvalue() {
		return assetvalue;
	}

	public void setAssetvalue(BigDecimal assetvalue) {
		this.assetvalue = assetvalue;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public String getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}

	public String getFundedby() {
		return fundedby;
	}

	public void setFundedby(String fundedby) {
		this.fundedby = fundedby;
	}

	public String getCollateralproperty() {
		return collateralproperty;
	}

	public void setCollateralproperty(String collateralproperty) {
		this.collateralproperty = collateralproperty;
	}

	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	@Override
	public String toString() {
		return "CrPdFixedAssetDtl [id=" + id + ", pdId=" + pdId
				+ ", description=" + description + ", address=" + address
				+ ", assetvalue=" + assetvalue + ", ownership=" + ownership
				+ ", occupancy=" + occupancy + ", fundedby=" + fundedby
				+ ", collateralproperty=" + collateralproperty + ", makerDate="
				+ makerDate + ", makerId=" + makerId + "]";
	}
}
