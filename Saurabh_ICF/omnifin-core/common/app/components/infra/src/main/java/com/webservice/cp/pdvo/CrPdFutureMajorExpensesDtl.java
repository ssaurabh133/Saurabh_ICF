package com.webservice.cp.pdvo;

import com.ibm.icu.math.BigDecimal;


public class CrPdFutureMajorExpensesDtl {
	
	private Integer id;
	
	private Integer pdId;
	
	private String futureExpensesDetail;
	
	
	private String futureExpensesIncurred	;
	
	
	private String futureYearOfExpense;
	
	
	private BigDecimal futureArrangementOfFund;
	
	
	private String futureCreditRemarks;
	
	
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

	

	

	public String getFutureExpensesDetail() {
		return futureExpensesDetail;
	}

	public void setFutureExpensesDetail(String futureExpensesDetail) {
		this.futureExpensesDetail = futureExpensesDetail;
	}

	public String getFutureExpensesIncurred() {
		return futureExpensesIncurred;
	}

	public void setFutureExpensesIncurred(String futureExpensesIncurred) {
		this.futureExpensesIncurred = futureExpensesIncurred;
	}

	public String getFutureYearOfExpense() {
		return futureYearOfExpense;
	}

	public void setFutureYearOfExpense(String futureYearOfExpense) {
		this.futureYearOfExpense = futureYearOfExpense;
	}

	public BigDecimal getFutureArrangementOfFund() {
		return futureArrangementOfFund;
	}

	public void setFutureArrangementOfFund(BigDecimal futureArrangementOfFund) {
		this.futureArrangementOfFund = futureArrangementOfFund;
	}

	public String getFutureCreditRemarks() {
		return futureCreditRemarks;
	}

	public void setFutureCreditRemarks(String futureCreditRemarks) {
		this.futureCreditRemarks = futureCreditRemarks;
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
		return "CrPdFutureMajorExpensesDtl [id=" + id + ", pdId=" + pdId
				+ ", futureExpensesDetail=" + futureExpensesDetail
				+ ", futureExpensesIncurred=" + futureExpensesIncurred
				+ ", futureYearOfExpense=" + futureYearOfExpense
				+ ", futureArrangementOfFund=" + futureArrangementOfFund
				+ ", futureCreditRemarks=" + futureCreditRemarks
				+ ", makerDate=" + makerDate + ", makerId=" + makerId + "]";
	}

	
	
	
}
