package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdResidenceVehicleFamilyDtl {

	private Integer id;

	private Integer pdId;

	private String vehicleType;

	private String vehicleOwner;

	private String vehicleRegistrationNo;

	private String purchasedFinanceCash;

	private String fiName;

	private BigDecimal marketValue;

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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleOwner() {
		return vehicleOwner;
	}

	public void setVehicleOwner(String vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}

	public String getVehicleRegistrationNo() {
		return vehicleRegistrationNo;
	}

	public void setVehicleRegistrationNo(String vehicleRegistrationNo) {
		this.vehicleRegistrationNo = vehicleRegistrationNo;
	}

	public String getPurchasedFinanceCash() {
		return purchasedFinanceCash;
	}

	public void setPurchasedFinanceCash(String purchasedFinanceCash) {
		this.purchasedFinanceCash = purchasedFinanceCash;
	}

	public String getFiName() {
		return fiName;
	}

	public void setFiName(String fiName) {
		this.fiName = fiName;
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
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
		return "CrPdResidenceVehicleFamilyDtl [id=" + id + ", pdId=" + pdId
				+ ", vehicleType=" + vehicleType + ", vehicleOwner="
				+ vehicleOwner + ", vehicleRegistrationNo="
				+ vehicleRegistrationNo + ", purchasedFinanceCash="
				+ purchasedFinanceCash + ", fiName=" + fiName
				+ ", marketValue=" + marketValue + ", makerDate=" + makerDate
				+ ", makerId=" + makerId + "]";
	}
}
