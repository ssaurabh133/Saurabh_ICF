package com.webservice.cp.pdvo;

import java.math.BigDecimal;


public class CrPdCommitteeDtl {
	
	private Integer id;
	private Integer pdId;
	private String takenWith;
	private String typeOfCommittee;
	private BigDecimal monthlyDepositAmount;
	private String withdrawn;
	private BigDecimal maturityAmount;
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

	public String getTakenWith() {
		return takenWith;
	}

	public void setTakenWith(String takenWith) {
		this.takenWith = takenWith;
	}

	public String getTypeOfCommittee() {
		return typeOfCommittee;
	}

	public void setTypeOfCommittee(String typeOfCommittee) {
		this.typeOfCommittee = typeOfCommittee;
	}

	public BigDecimal getMonthlyDepositAmount() {
		return monthlyDepositAmount;
	}

	public void setMonthlyDepositAmount(BigDecimal monthlyDepositAmount) {
		this.monthlyDepositAmount = monthlyDepositAmount;
	}

	public String getWithdrawn() {
		return withdrawn;
	}

	public void setWithdrawn(String withdrawn) {
		this.withdrawn = withdrawn;
	}

	public BigDecimal getMaturityAmount() {
		return maturityAmount;
	}

	public void setMaturityAmount(BigDecimal maturityAmount) {
		this.maturityAmount = maturityAmount;
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
		return "CrPdCommitteeDtl [id=" + id + ", pdId=" + pdId + ", takenWith="
				+ takenWith + ", typeOfCommittee=" + typeOfCommittee
				+ ", monthlyDepositAmount=" + monthlyDepositAmount
				+ ", withdrawn=" + withdrawn + ", maturityAmount="
				+ maturityAmount + ", remark=" + remark + ", makerDate="
				+ makerDate + ", makerId=" + makerId + "]";
	}


}
