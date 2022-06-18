package com.masters.vo;

import java.io.Serializable;

import javax.management.loading.PrivateClassLoader;


public class ManufacturerSupplierMappingVO implements Serializable{
	private String manufacturerDesc;
	private String manufacturerId;
	private String lbxmachineManufact;
	private String supplierDesc;
	private String lbxSupplierId;
	private String supplierId;
	private String recStatus;
	private String makerId;
	private String makerDate;
	private String authorId;
	private String authorDate;
	private int totalRecordSize;
	private int currentPageLink;
	private String mappingId;
	
	
	
	
	public String getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
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
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	public String getMappingId() {
		return mappingId;
	}
	public void setManufacturerDesc(String manufacturerDesc) {
		this.manufacturerDesc = manufacturerDesc;
	}
	public String getManufacturerDesc() {
		return manufacturerDesc;
	}
	public void setLbxmachineManufact(String lbxmachineManufact) {
		this.lbxmachineManufact = lbxmachineManufact;
	}
	public String getLbxmachineManufact() {
		return lbxmachineManufact;
	}
	public void setLbxSupplierId(String lbxSupplierId) {
		this.lbxSupplierId = lbxSupplierId;
	}
	public String getLbxSupplierId() {
		return lbxSupplierId;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public String getAuthorDate() {
		return authorDate;
	}
	public void setSupplierDesc(String supplierDesc) {
		this.supplierDesc = supplierDesc;
	}
	public String getSupplierDesc() {
		return supplierDesc;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	
}
