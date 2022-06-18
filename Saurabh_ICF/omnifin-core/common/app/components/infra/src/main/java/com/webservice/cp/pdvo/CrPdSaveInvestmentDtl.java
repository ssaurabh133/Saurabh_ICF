package com.webservice.cp.pdvo;

import java.math.BigDecimal;
public class CrPdSaveInvestmentDtl {
	private Integer id;
	private Integer pdId;
	private String description;
	private String saveInvestmentValue;
	private String yearOfInvestment;
	private String ownership;
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

	public String getSaveInvestmentValue() {
		return saveInvestmentValue;
	}

	public void setSaveInvestmentValue(String saveInvestmentValue) {
		this.saveInvestmentValue = saveInvestmentValue;
	}

	public String getYearOfInvestment() {
		return yearOfInvestment;
	}

	public void setYearOfInvestment(String yearOfInvestment) {
		this.yearOfInvestment = yearOfInvestment;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
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
		return "CrPdSaveInvestmentDtl [id=" + id + ", pdId=" + pdId
				+ ", description=" + description + ", saveInvestmentValue="
				+ saveInvestmentValue + ", yearOfInvestment="
				+ yearOfInvestment + ", ownership=" + ownership
				+ ", makerDate=" + makerDate + ", makerId=" + makerId + "]";
	}
	
}
