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
public class ReassignCaseVo implements Serializable{
	
	private String loanNo;
	private String customerName;
	private String caseType;
	private String caseFileDate;
	private String dateOfhearing;
	private String caseNo;
	private String advocateName;
	private String stage;
	private String section;
	private String courtName;
	private String poa;
	private String recStatus;
	private String makerId;
	private String makerDate;
	private String legalId;
	private String loanNoLink;
	private int currentPageLink;
	private int totalRecordSize;
	private String listsize;
	private String Decision;
	private String remarks;
	private String initMakerRemarks;
	private String reasonDesc;
	private String initMakerImemo;
	private String caseTypeDesc;
	private String principalOutstanding;
	private String otherCharges;
	private String totalOutstanding;
	private String dpd;
	private String scheme;
	private String product;
	private String branch;
	private String NoticeDesc;
	private String LbxNoticeCode;
	private String LbxReasonId;
	private String loanId;
	private String setLbxReasonId;
	private String NoticeAmount;
	private String ModeOfNotice;
	private String lbxCaseTypeCode;
	private String lbxUserId;
	private String setNoticeAmount;
	private String setModeOfNotice;
	private String DateOfConciliation;
	private String TimeOfConciliation;
	private String VenueOfConciliation;
	private String NameOfConciliator;
	private String DesignationOfConciliator;
	private String MakerRemarks;
	private String saveForwardFlag;
	private String lbxLoanId;
	private String lbxcustomerId;
	   private String customerId;
	   private String custRole;
	
	
	
	
	public String getLbxLoanId() {
		return lbxLoanId;
	}
	public void setLbxLoanId(String lbxLoanId) {
		this.lbxLoanId = lbxLoanId;
	}
	public String getSaveForwardFlag() {
		return saveForwardFlag;
	}
	public void setSaveForwardFlag(String saveForwardFlag) {
		this.saveForwardFlag = saveForwardFlag;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getLbxCaseTypeCode() {
		return lbxCaseTypeCode;
	}
	public void setLbxCaseTypeCode(String lbxCaseTypeCode) {
		this.lbxCaseTypeCode = lbxCaseTypeCode;
	}
	public String getModeOfNotice() {
		return ModeOfNotice;
	}
	public void setModeOfNotice(String modeOfNotice) {
		ModeOfNotice = modeOfNotice;
	}
	
	public String getNoticeAmount() {
		return NoticeAmount;
	}
	public void setNoticeAmount(String string) {
		NoticeAmount = string;
	}
	public String getNoticeDesc() {
		
		return NoticeDesc;
	}
	public void setNoticeDesc(String noticeDesc) {
		NoticeDesc = noticeDesc;
	}
	public String getLbxNoticeCode() {
		return LbxNoticeCode;
	}
	public void setLbxNoticeCode(String lbxNoticeCode) {
		LbxNoticeCode = lbxNoticeCode;
	}
	public String getLbxReasonId() {
		return LbxReasonId;
	}
	public void setLbxReasonId(String lbxReasonId) {
		LbxReasonId = lbxReasonId;
	}
	public String getSetLbxReasonId() {
		return setLbxReasonId;
	}
	public void setSetLbxReasonId(String setLbxReasonId) {
		this.setLbxReasonId = setLbxReasonId;
	}
	public String getSetNoticeAmount() {
		return setNoticeAmount;
	}
	public void setSetNoticeAmount(String setNoticeAmount) {
		this.setNoticeAmount = setNoticeAmount;
	}
	public String getSetModeOfNotice() {
		return setModeOfNotice;
	}
	public void setSetModeOfNotice(String setModeOfNotice) {
		this.setModeOfNotice = setModeOfNotice;
	}
	public String getDateOfConciliation() {
		return DateOfConciliation;
	}
	public void setDateOfConciliation(String dateOfConciliation) {
		DateOfConciliation = dateOfConciliation;
	}
	public String getTimeOfConciliation() {
		return TimeOfConciliation;
	}
	public void setTimeOfConciliation(String timeOfConciliation) {
		TimeOfConciliation = timeOfConciliation;
	}
	public String getVenueOfConciliation() {
		return VenueOfConciliation;
	}
	public void setVenueOfConciliation(String venueOfConciliation) {
		VenueOfConciliation = venueOfConciliation;
	}
	public String getNameOfConciliator() {
		return NameOfConciliator;
	}
	public void setNameOfConciliator(String nameOfConciliator) {
		NameOfConciliator = nameOfConciliator;
	}
	public String getDesignationOfConciliator() {
		return DesignationOfConciliator;
	}
	public void setDesignationOfConciliator(String designationOfConciliator) {
		DesignationOfConciliator = designationOfConciliator;
	}
	public String getMakerRemarks() {
		return MakerRemarks;
	}
	public void setMakerRemarks(String makerRemarks) {
		MakerRemarks = makerRemarks;
	}
	
	
	
	public String getInitMakerRemarks() {
		return initMakerRemarks;
	}
	public void setInitMakerRemarks(String initMakerRemarks) {
		this.initMakerRemarks = initMakerRemarks;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public String getInitMakerImemo() {
		return initMakerImemo;
	}
	public void setInitMakerImemo(String initMakerImemo) {
		this.initMakerImemo = initMakerImemo;
	}
	public String getCaseTypeDesc() {
		return caseTypeDesc;
	}
	public void setCaseTypeDesc(String caseTypeDesc) {
		this.caseTypeDesc = caseTypeDesc;
	}
	public String getPrincipalOutstanding() {
		return principalOutstanding;
	}
	public void setPrincipalOutstanding(String principalOutstanding) {
		this.principalOutstanding = principalOutstanding;
	}
	public String getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(String otherCharges) {
		this.otherCharges = otherCharges;
	}
	public String getTotalOutstanding() {
		return totalOutstanding;
	}
	public void setTotalOutstanding(String totalOutstanding) {
		this.totalOutstanding = totalOutstanding;
	}
	public String getDpd() {
		return dpd;
	}
	public void setDpd(String dpd) {
		this.dpd = dpd;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDecision() {
		return Decision;
	}
	public void setDecision(String decision) {
		this.Decision = decision;
	}
	private String updateForwardFlag;
	
	public String getUpdateForwardFlag() {
		return updateForwardFlag;
	}
	public void setUpdateForwardFlag(String updateForwardFlag) {
		this.updateForwardFlag = updateForwardFlag;
	}
	public String getListsize() {
		return listsize;
	}
	public void setListsize(String listsize) {
		this.listsize = listsize;
	}
	public String getLegalId() {
		return legalId;
	}
	public void setLegalId(String legalId) {
		this.legalId = legalId;
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
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getCaseFileDate() {
		return caseFileDate;
	}
	public void setCaseFileDate(String caseFileDate) {
		this.caseFileDate = caseFileDate;
	}
	public String getDateOfhearing() {
		return dateOfhearing;
	}
	public void setDateOfhearing(String dateOfhearing) {
		this.dateOfhearing = dateOfhearing;
	}
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public String getAdvocateName() {
		return advocateName;
	}
	public void setAdvocateName(String advocateName) {
		this.advocateName = advocateName;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getCourtName() {
		return courtName;
	}
	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public void setLoanNoLink(String loanNoLink) {
		this.loanNoLink = loanNoLink;
	}
	public String getLoanNoLink() {
		return loanNoLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}
	public int getCurrentPageLink() {
		return currentPageLink;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setPoa(String poa) {
		this.poa = poa;
	}
	public String getPoa() {
		return poa;
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
		// TODO Auto-generated method stub
		
	}

