package com.webservice.cp.pdvo;

import java.math.BigDecimal;

public class CrPdFixedLiquidAssetDtl{

	private Integer id;

	private Integer pdId;

	private BigDecimal cashBankLiquidValue;

	private BigDecimal cashHandLiquidValue;

	private String cashBankOwnership;

	private String cashHandOwnership;

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

	public BigDecimal getCashBankLiquidValue() {
		return cashBankLiquidValue;
	}

	public void setCashBankLiquidValue(BigDecimal cashBankLiquidValue) {
		this.cashBankLiquidValue = cashBankLiquidValue;
	}

	public BigDecimal getCashHandLiquidValue() {
		return cashHandLiquidValue;
	}

	public void setCashHandLiquidValue(BigDecimal cashHandLiquidValue) {
		this.cashHandLiquidValue = cashHandLiquidValue;
	}

	public String getCashBankOwnership() {
		return cashBankOwnership;
	}

	public void setCashBankOwnership(String cashBankOwnership) {
		this.cashBankOwnership = cashBankOwnership;
	}

	public String getCashHandOwnership() {
		return cashHandOwnership;
	}

	public void setCashHandOwnership(String cashHandOwnership) {
		this.cashHandOwnership = cashHandOwnership;
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
		return "CrPdFixedLiquidAssetDtl [id=" + id + ", pdId=" + pdId
				+ ", cashBankLiquidValue=" + cashBankLiquidValue
				+ ", cashHandLiquidValue=" + cashHandLiquidValue
				+ ", cashBankOwnership=" + cashBankOwnership
				+ ", cashHandOwnership=" + cashHandOwnership + ", makerDate="
				+ makerDate + ", makerId=" + makerId + "]";
	}
}
