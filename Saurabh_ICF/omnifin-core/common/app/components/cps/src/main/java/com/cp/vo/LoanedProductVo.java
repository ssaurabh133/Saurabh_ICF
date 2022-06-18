package com.cp.vo;

public class LoanedProductVo {
	

	private String  productType;
	private String  product;
	private String  scheme;
	private String  loanType;
	private String  requestedLoanAmount;
	private String  requestedLoanTenure;
	private String  loanProductSeq;
	private String  status;
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getRequestedLoanAmount() {
		return requestedLoanAmount;
	}
	public void setRequestedLoanAmount(String requestedLoanAmount) {
		this.requestedLoanAmount = requestedLoanAmount;
	}
	public String getRequestedLoanTenure() {
		return requestedLoanTenure;
	}
	public void setRequestedLoanTenure(String requestedLoanTenure) {
		this.requestedLoanTenure = requestedLoanTenure;
	}
	public String getLoanProductSeq() {
		return loanProductSeq;
	}
	public void setLoanProductSeq(String loanProductSeq) {
		this.loanProductSeq = loanProductSeq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
