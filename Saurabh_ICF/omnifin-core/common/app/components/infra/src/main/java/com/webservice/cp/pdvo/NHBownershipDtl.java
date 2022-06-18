package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class NHBownershipDtl {
	
	private String name;
	private String gender;
	private BigDecimal income;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	@Override
	public String toString() {
		return "NHBownershipDtl [name=" + name + ", gender=" + gender
				+ ", income=" + income + "]";
	}
}
