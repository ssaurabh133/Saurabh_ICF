//Author Name : Ankit Agarwal-->
//Date of Creation : 21 May 2011-->
//Purpose  : VO for Country Master-->
//Documentation : -->

package com.legal.vo;

import java.io.Serializable;

/**
 * @author A3S
 *
 */
public class LegalNoticeInitiationVo implements Serializable{
	
	private String declineDispatchDecision;
	private String textareaDeclineDispatchRemarks;
	private String lovDeclineDispatchReason;
	private String checkerDecision;
	private String listSize;
	private String noticeIdChk;
	private String noticeIdlink;
	private String noticeIdCheckerRemarks;
	private String updateForwardFlag;
	private String lbxLoanId;
	private String lbxReasonId;
	private String lbxNoticeCode;
	private String reasonDesc;
	private String noticeDesc;
	private String noticeId;
	private String noticeCode;
	private String loanNo;
	private String reasonCode;
	private String makerRemarks;
	private String autherRemarks;
	private String noticeAmount;
	private String modeOfNotice;
	private String dateOfConciliation;
	private String timeOfConciliation;
	private String venueOfConciliation;
	private String nameOfConciliator;
	private String designationOfConciliator;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private	String autherId;
	private String autherDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String lbxLoanNoHID;
	private String decision;
	private String comments;
	private String noticeInitiationDate;
	private String noticeInitiatedBy;
	private String noticeApprovedDate;
	private String noticeApprovedBy;
	private String initiateReason;
	private String initiateRemark;
	private String declineRemark;
	private String authorRemarks;
	private String noticeLegalDeclineID;
	private String noticeMessage;

	private String lbxcustomerId;
	private String customerId;
	private String custRole;
	
	
	
	public String getNoticeMessage() {
		return noticeMessage;
	}
	public void setNoticeMessage(String noticeMessage) {
		this.noticeMessage = noticeMessage;
	}
	public String getDeclineRemark() {
		return declineRemark;
	}
	public void setDeclineRemark(String declineRemark) {
		this.declineRemark = declineRemark;
	}
	public String getNoticeLegalDeclineID() {
		return noticeLegalDeclineID;
	}
	public void setNoticeLegalDeclineID(String noticeLegalDeclineID) {
		this.noticeLegalDeclineID = noticeLegalDeclineID;
	}	
	public String getNoticeInitiationDate() {
		return noticeInitiationDate;
	}
	public void setNoticeInitiationDate(String noticeInitiationDate) {
		this.noticeInitiationDate = noticeInitiationDate;
	}
	public String getNoticeInitiatedBy() {
		return noticeInitiatedBy;
	}
	public void setNoticeInitiatedBy(String noticeInitiatedBy) {
		this.noticeInitiatedBy = noticeInitiatedBy;
	}
	public String getNoticeApprovedDate() {
		return noticeApprovedDate;
	}
	public void setNoticeApprovedDate(String noticeApprovedDate) {
		this.noticeApprovedDate = noticeApprovedDate;
	}
	public String getNoticeApprovedBy() {
		return noticeApprovedBy;
	}
	public void setNoticeApprovedBy(String noticeApprovedBy) {
		this.noticeApprovedBy = noticeApprovedBy;
	}
	public String getInitiateReason() {
		return initiateReason;
	}
	public void setInitiateReason(String initiateReason) {
		this.initiateReason = initiateReason;
	}
	public String getInitiateRemark() {
		return initiateRemark;
	}
	public void setInitiateRemark(String initiateRemark) {
		this.initiateRemark = initiateRemark;
	}
	public String getAuthorRemarks() {
		return authorRemarks;
	}
	public void setAuthorRemarks(String authorRemarks) {
		this.authorRemarks = authorRemarks;
	}
	
	 public String getDecision() {
			return decision;
		}
		public void setDecision(String decision) {
			this.decision = decision;
	}
	
	public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public String getLbxLoanNoHID() {
			return lbxLoanNoHID;
		}
		public void setLbxLoanNoHID(String lbxLoanNoHID) {
			this.lbxLoanNoHID = lbxLoanNoHID;
	}
	
	
	public String getDeclineDispatchDecision() {
		return declineDispatchDecision;
	}
	public void setDeclineDispatchDecision(String declineDispatchDecision) {
		this.declineDispatchDecision = declineDispatchDecision;
	}
	public String getTextareaDeclineDispatchRemarks() {
		return textareaDeclineDispatchRemarks;
	}
	public void setTextareaDeclineDispatchRemarks(
			String textareaDeclineDispatchRemarks) {
		this.textareaDeclineDispatchRemarks = textareaDeclineDispatchRemarks;
	}
	public String getLovDeclineDispatchReason() {
		return lovDeclineDispatchReason;
	}
	public void setLovDeclineDispatchReason(String lovDeclineDispatchReason) {
		this.lovDeclineDispatchReason = lovDeclineDispatchReason;
	}
	public String getCheckerDecision() {
		return checkerDecision;
	}
	public void setCheckerDecision(String checkerDecision) {
		this.checkerDecision = checkerDecision;
	}
	public String getListSize() {
		return listSize;
	}
	public void setListSize(String listSize) {
		this.listSize = listSize;
	}
	public String getNoticeIdChk() {
		return noticeIdChk;
	}
	public void setNoticeIdChk(String noticeIdChk) {
		this.noticeIdChk = noticeIdChk;
	}
	public String getNoticeIdlink() {
		return noticeIdlink;
	}
	public void setNoticeIdlink(String noticeIdlink) {
		this.noticeIdlink = noticeIdlink;
	}
	public String getNoticeIdCheckerRemarks() {
		return noticeIdCheckerRemarks;
	}
	public void setNoticeIdCheckerRemarks(String noticeIdCheckerRemarks) {
		this.noticeIdCheckerRemarks = noticeIdCheckerRemarks;
	}
	public String getUpdateForwardFlag() {
		return updateForwardFlag;
	}
	public void setUpdateForwardFlag(String updateForwardFlag) {
		this.updateForwardFlag = updateForwardFlag;
	}
	public String getLbxLoanId() {
		return lbxLoanId;
	}
	public void setLbxLoanId(String lbxLoanId) {
		this.lbxLoanId = lbxLoanId;
	}
	public String getLbxReasonId() {
		return lbxReasonId;
	}
	public void setLbxReasonId(String lbxReasonId) {
		this.lbxReasonId = lbxReasonId;
	}
	public String getLbxNoticeCode() {
		return lbxNoticeCode;
	}
	public void setLbxNoticeCode(String lbxNoticeCode) {
		this.lbxNoticeCode = lbxNoticeCode;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public String getNoticeDesc() {
		return noticeDesc;
	}
	public void setNoticeDesc(String noticeDesc) {
		this.noticeDesc = noticeDesc;
	}
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeCode() {
		return noticeCode;
	}
	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getMakerRemarks() {
		return makerRemarks;
	}
	public void setMakerRemarks(String makerRemarks) {
		this.makerRemarks = makerRemarks;
	}
	public String getAutherRemarks() {
		return autherRemarks;
	}
	public void setAutherRemarks(String autherRemarks) {
		this.autherRemarks = autherRemarks;
	}
	public String getNoticeAmount() {
		return noticeAmount;
	}
	public void setNoticeAmount(String noticeAmount) {
		this.noticeAmount = noticeAmount;
	}
	public String getModeOfNotice() {
		return modeOfNotice;
	}
	public void setModeOfNotice(String modeOfNotice) {
		this.modeOfNotice = modeOfNotice;
	}
	public String getDateOfConciliation() {
		return dateOfConciliation;
	}
	public void setDateOfConciliation(String dateOfConciliation) {
		this.dateOfConciliation = dateOfConciliation;
	}
	public String getTimeOfConciliation() {
		return timeOfConciliation;
	}
	public void setTimeOfConciliation(String timeOfConciliation) {
		this.timeOfConciliation = timeOfConciliation;
	}
	public String getVenueOfConciliation() {
		return venueOfConciliation;
	}
	public void setVenueOfConciliation(String venueOfConciliation) {
		this.venueOfConciliation = venueOfConciliation;
	}
	public String getNameOfConciliator() {
		return nameOfConciliator;
	}
	public void setNameOfConciliator(String nameOfConciliator) {
		this.nameOfConciliator = nameOfConciliator;
	}
	public String getDesignationOfConciliator() {
		return designationOfConciliator;
	}
	public void setDesignationOfConciliator(String designationOfConciliator) {
		this.designationOfConciliator = designationOfConciliator;
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
	public String getAutherId() {
		return autherId;
	}
	public void setAutherId(String autherId) {
		this.autherId = autherId;
	}
	public String getAutherDate() {
		return autherDate;
	}
	public void setAutherDate(String autherDate) {
		this.autherDate = autherDate;
	}
	
	public String getLbxcustomerId() {
		return this.lbxcustomerId;
		}
		   public void setLbxcustomerId(String lbxcustomerId) {
		     this.lbxcustomerId = lbxcustomerId;
		   }
		   public String getCustomerId() {
		     return this.customerId;
		   }
		   public void setCustomerId(String customerId) {
		     this.customerId = customerId;
		   }
		   public String getCustRole() {
		     return this.custRole;
		   }
		   public void setCustRole(String custRole) {
		     this.custRole = custRole;
		   }
	
	
	
	
	
	
}
