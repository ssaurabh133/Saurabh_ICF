//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.masters.vo;

import java.io.Serializable;

public class DocChildMasterVo implements Serializable{
	
	private String docChildID;
	private String docDes;
	private String docChildDes;
	private String docId;
	private String status;
	private String makerId;
	private String makerDate;
	private String lbxDocId;
	private String docChildSearchDes;
	private String docChildIDModify;
	private int currentPageLink;
	private int totalRecordSize;

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
	public String getDocChildID() {
		return docChildID;
	}
	public void setDocChildID(String docChildID) {
		this.docChildID = docChildID;
	}
	public String getDocDes() {
		return docDes;
	}
	public void setDocDes(String docDes) {
		this.docDes = docDes;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setDocChildDes(String docChildDes) {
		this.docChildDes = docChildDes;
	}
	public String getDocChildDes() {
		return docChildDes;
	}
	public void setLbxDocId(String lbxDocId) {
		this.lbxDocId = lbxDocId;
	}
	public String getLbxDocId() {
		return lbxDocId;
	}
	public void setDocChildSearchDes(String docChildSearchDes) {
		this.docChildSearchDes = docChildSearchDes;
	}
	public String getDocChildSearchDes() {
		return docChildSearchDes;
	}
	public void setDocChildIDModify(String docChildIDModify) {
		this.docChildIDModify = docChildIDModify;
	}
	public String getDocChildIDModify() {
		return docChildIDModify;
	}

	
	
}
