package com.webservice.cp.pdvo;

import com.ibm.icu.math.BigDecimal;


public class CrPdPreviousMajorExpensesDtl {
	
	private Integer id;
	
	
	private Integer pdId;
	
	private String previousExpensesDetail;
	
	
	private String previousExpensesIncurred	;
	
	
	private String previousYearOfExpense;
	
	
	private BigDecimal previousArrangementOfFund;
	
	
	private String previousCreditRemarks;
	
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

	

	public String getPreviousExpensesDetail() {
		return previousExpensesDetail;
	}

	public void setPreviousExpensesDetail(String previousExpensesDetail) {
		this.previousExpensesDetail = previousExpensesDetail;
	}

	public String getPreviousExpensesIncurred() {
		return previousExpensesIncurred;
	}

	public void setPreviousExpensesIncurred(String previousExpensesIncurred) {
		this.previousExpensesIncurred = previousExpensesIncurred;
	}

	public String getPreviousYearOfExpense() {
		return previousYearOfExpense;
	}

	public void setPreviousYearOfExpense(String previousYearOfExpense) {
		this.previousYearOfExpense = previousYearOfExpense;
	}

	public BigDecimal getPreviousArrangementOfFund() {
		return previousArrangementOfFund;
	}

	public void setPreviousArrangementOfFund(BigDecimal previousArrangementOfFund) {
		this.previousArrangementOfFund = previousArrangementOfFund;
	}

	public String getPreviousCreditRemarks() {
		return previousCreditRemarks;
	}

	public void setPreviousCreditRemarks(String previousCreditRemarks) {
		this.previousCreditRemarks = previousCreditRemarks;
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
		return "CrPdPreviousMajorExpensesDtl [id=" + id + ", pdId=" + pdId
				+ ", previousExpensesDetail=" + previousExpensesDetail
				+ ", previousExpensesIncurred=" + previousExpensesIncurred
				+ ", previousYearOfExpense=" + previousYearOfExpense
				+ ", previousArrangementOfFund=" + previousArrangementOfFund
				+ ", previousCreditRemarks=" + previousCreditRemarks
				+ ", makerDate=" + makerDate + ", makerId=" + makerId + "]";
	}

	
	
	
}
