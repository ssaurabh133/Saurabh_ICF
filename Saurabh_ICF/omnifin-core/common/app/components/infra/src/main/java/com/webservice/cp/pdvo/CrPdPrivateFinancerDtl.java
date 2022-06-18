package com.webservice.cp.pdvo;
import java.math.BigDecimal;

public class CrPdPrivateFinancerDtl {
	private Integer id;
	private Integer pdId;
	private String takenFrom;
	private String typeOfFinancer;
	private BigDecimal amountTaken;
	private BigDecimal monthlyRepaymentAmount;
	private String expectedClosureDate;
	private String remark;
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

	public String getTakenFrom() {
		return takenFrom;
	}

	public void setTakenFrom(String takenFrom) {
		this.takenFrom = takenFrom;
	}

	public String getTypeOfFinancer() {
		return typeOfFinancer;
	}

	public void setTypeOfFinancer(String typeOfFinancer) {
		this.typeOfFinancer = typeOfFinancer;
	}

	public BigDecimal getAmountTaken() {
		return amountTaken;
	}

	public void setAmountTaken(BigDecimal amountTaken) {
		this.amountTaken = amountTaken;
	}

	public BigDecimal getMonthlyRepaymentAmount() {
		return monthlyRepaymentAmount;
	}

	public void setMonthlyRepaymentAmount(BigDecimal monthlyRepaymentAmount) {
		this.monthlyRepaymentAmount = monthlyRepaymentAmount;
	}

	public String getExpectedClosureDate() {
		return expectedClosureDate;
	}

	public void setExpectedClosureDate(String expectedClosureDate) {
		this.expectedClosureDate = expectedClosureDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return "CrPdPrivateFinancerDtl [id=" + id + ", pdId=" + pdId
				+ ", takenFrom=" + takenFrom + ", typeOfFinancer="
				+ typeOfFinancer + ", amountTaken=" + amountTaken
				+ ", monthlyRepaymentAmount=" + monthlyRepaymentAmount
				+ ", expectedClosureDate=" + expectedClosureDate + ", remark="
				+ remark + ", makerDate=" + makerDate + ", makerId=" + makerId
				+ "]";
	}

}