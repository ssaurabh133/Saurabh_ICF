//Author Name : Ankit Agarwal-->
//Date of Creation : -->
//Purpose  : VO for Reason Master-->
//Documentation : -->

package com.legal.vo;

import java.io.Serializable;

public class ReasonMasterVo implements Serializable{

	
	private String reasonId;
	private String reasonType;
	private String reasonDes;
	private String chargeFlag;
	//private String chargeAmount;
	private String recStatus;
	private String lbxReason;
	private String makerId;
	private String makerDate;
	private String authorId;
	private String authorDate;
	private String reasonSearchDes;
	private String reasonIdModify;
	private int currentPageLink;
	private int totalRecordSize;
	private String reasonShortcode;
	private String notice;// added by Rohit for legal module
	private String caseType;// added by Rohit for legal module
	private String lbxNoticeCode;// added by Rohit for legal module
	private String lbxCaseTypeCode;// added by Rohit for legal module
	
	
	public String getReasonShortcode() {
		return reasonShortcode;
	}
	public void setReasonShortcode(String reasonShortcode) {
		this.reasonShortcode = reasonShortcode;
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
	public String getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(String authorDate) {
		this.authorDate = authorDate;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}
	public String getReasonType() {
		return reasonType;
	}
	public void setReasonDes(String reasonDes) {
		this.reasonDes = reasonDes;
	}
	public String getReasonDes() {
		return reasonDes;
	}
	public void setChargeFlag(String chargeFlag) {
		this.chargeFlag = chargeFlag;
	}
	public String getChargeFlag() {
		return chargeFlag;
	}
	
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setLbxReason(String lbxReason) {
		this.lbxReason = lbxReason;
	}
	public String getLbxReason() {
		return lbxReason;
	}
	public void setReasonSearchDes(String reasonSearchDes) {
		this.reasonSearchDes = reasonSearchDes;
	}
	public String getReasonSearchDes() {
		return reasonSearchDes;
	}
	public void setReasonIdModify(String reasonIdModify) {
		this.reasonIdModify = reasonIdModify;
	}
	public String getReasonIdModify() {
		return reasonIdModify;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getLbxNoticeCode() {
		return lbxNoticeCode;
	}
	public void setLbxNoticeCode(String lbxNoticeCode) {
		this.lbxNoticeCode = lbxNoticeCode;
	}
	public String getLbxCaseTypeCode() {
		return lbxCaseTypeCode;
	}
	public void setLbxCaseTypeCode(String lbxCaseTypeCode) {
		this.lbxCaseTypeCode = lbxCaseTypeCode;
	}



}