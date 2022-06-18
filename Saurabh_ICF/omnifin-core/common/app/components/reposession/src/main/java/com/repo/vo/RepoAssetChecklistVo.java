//Author Name : Nazia-->
//Date of Creation : 13 Mar 2013-->
//Purpose  : VO for notice Master-->
//Documentation : -->

package com.repo.vo;

import java.io.Serializable;

public class RepoAssetChecklistVo implements Serializable{

	private String lbxProductSearchID;
	private String assetClass;
	private String docCheckIdForDel;
	private String docId;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String status;
	private String docCheckId;
	private String product;
	private String checklist;
	private String repoProduct;
	private String assetClassId;
	
	
	
	
	
	

	public String getAssetClassId() {
		return assetClassId;
	}
	public void setAssetClassId(String assetClassId) {
		this.assetClassId = assetClassId;
	}
	public String getRepoProduct() {
		return repoProduct;
	}
	public void setRepoProduct(String repoProduct) {
		this.repoProduct = repoProduct;
	}
	public String getChecklist() {
		return checklist;
	}
	public void setChecklist(String checklist) {
		this.checklist = checklist;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getDocCheckId() {
		return docCheckId;
	}
	public void setDocCheckId(String docCheckId) {
		this.docCheckId = docCheckId;
	}
	public String getLbxProductSearchID() {
		return lbxProductSearchID;
	}
	public void setLbxProductSearchID(String lbxProductSearchID) {
		this.lbxProductSearchID = lbxProductSearchID;
	}
	public String getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}
	public String getDocCheckIdForDel() {
		return docCheckIdForDel;
	}
	public void setDocCheckIdForDel(String docCheckIdForDel) {
		this.docCheckIdForDel = docCheckIdForDel;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
	
	
	