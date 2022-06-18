//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class IrrCalculationMasterVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String makerId;
	private String makerDate;
	private String product;
	private String scheme;
	private String lbxProductID;
	private String lbxscheme;
	private String irrType;
	private String irrChargeCode;
	private Integer totalRecordSize;
	private Integer currentPageLink;
	private String irrChargeCodeModify;
	private String irrID;
	private String lbxIrrType;	
	private String lbxIrrChargeCode;	
	private String chargeCode;
	private String chargeDesc;
	
	
	public String getChargeCode() {
		return chargeCode;
	}
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}
	public String getChargeDesc() {
		return chargeDesc;
	}
	public void setChargeDesc(String chargeDesc) {
		this.chargeDesc = chargeDesc;
	}
	public String getLbxIrrChargeCode() {
		return lbxIrrChargeCode;
	}
	public void setLbxIrrChargeCode(String lbxIrrChargeCode) {
		this.lbxIrrChargeCode = lbxIrrChargeCode;
	}
	public String getLbxIrrType() {
		return lbxIrrType;
	}
	public void setLbxIrrType(String lbxIrrType) {
		this.lbxIrrType = lbxIrrType;
	}
	public String getIrrID() {
		return irrID;
	}
	public void setIrrID(String irrID) {
		this.irrID = irrID;
	}
	public String getIrrChargeCodeModify() {
		return irrChargeCodeModify;
	}
	public void setIrrChargeCodeModify(String irrChargeCodeModify) {
		this.irrChargeCodeModify = irrChargeCodeModify;
	}
	public Integer getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(Integer currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public Integer getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(Integer totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
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
	public String getLbxProductID() {
		return lbxProductID;
	}
	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}
	public String getLbxscheme() {
		return lbxscheme;
	}
	public void setLbxscheme(String lbxscheme) {
		this.lbxscheme = lbxscheme;
	}
	public String getIrrType() {
		return irrType;
	}
	public String getIrrChargeCode() {
		return irrChargeCode;
	}
	public void setIrrChargeCode(String irrChargeCode) {
		this.irrChargeCode = irrChargeCode;
	}
	public void setIrrType(String irrType) {
		this.irrType = irrType;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	}
