//Author Name : Nazia-->
//Date of Creation : 13 Mar 2013-->
//Purpose  : VO for notice Master-->
//Documentation : -->

package com.legal.vo;

import java.io.Serializable;

public class StageTypeMasterVo implements Serializable{

	private String caseTypeDesc;
	private String stageCode;
	private String stageTypeDesc;
	private String recStatus;
	public String getCaseTypeDesc() {
		return caseTypeDesc;
	}
	public void setCaseTypeDesc(String caseTypeDesc) {
		this.caseTypeDesc = caseTypeDesc;
	}
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String caseType;
	private String stageProduct;
	private String sequenceNumber;
	private String status;
	private String closureStage;
	private String product;
	private String productCode;
	private String productDesc;
	private String lbxProductSearchID;
	private String lbxCaseTypeCode;
	private String tat;
	public String getLbxCaseTypeCode() {
		return lbxCaseTypeCode;
	}
	public void setLbxCaseTypeCode(String lbxCaseTypeCode) {
		this.lbxCaseTypeCode = lbxCaseTypeCode;
	}
	public String getLbxProductSearchID() {
		return lbxProductSearchID;
	}
	public void setLbxProductSearchID(String lbxProductSearchID) {
		this.lbxProductSearchID = lbxProductSearchID;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getPaymentStage() {
		return paymentStage;
	}
	public void setPaymentStage(String paymentStage) {
		this.paymentStage = paymentStage;
	}
	public String getRepetitive() {
		return repetitive;
	}
	public void setRepetitive(String repetitive) {
		this.repetitive = repetitive;
	}
	private String paymentStage ;
	private String repetitive;
	
	
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getClosureStage() {
		return closureStage;
	}
	public void setClosureStage(String closureStage) {
		this.closureStage = closureStage;
	}
	public String getStageProduct() {
		return stageProduct;
	}
	public void setStageProduct(String stageProduct) {
		this.stageProduct = stageProduct;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getStageCode() {
		return stageCode;
	}
	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}
	public String getStageTypeDesc() {
		return stageTypeDesc;
	}
	public void setStageTypeDesc(String stageTypeDesc) {
		this.stageTypeDesc = stageTypeDesc;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	 public String getTat() {
		      return this.tat;
		    }
		  public void setTat(String tat) {
		      this.tat = tat;
		  }
	
}
	

	