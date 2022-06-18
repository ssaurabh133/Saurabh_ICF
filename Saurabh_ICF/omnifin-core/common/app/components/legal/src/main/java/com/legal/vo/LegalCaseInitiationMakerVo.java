//Author Name : Vinod Kumar Gupta-->
//Date of Creation : 01-04-2013 -->
//Purpose  : VO for Legal Case Initiation Maker-->
//Documentation : -->

package com.legal.vo;

import java.io.Serializable;

/*
 * @author A3S
 *
 */
public class LegalCaseInitiationMakerVo implements Serializable{
	
	private String initMakerImemo;
	private String initMakerRemarks;
	private String lbxReasonId;
	private String reasonDesc;
	private String lbxCaseTypeCode;
	private String caseTypeDesc;
	private String loanId;
	private String loanNo;
	private String lbxLoanId;
	private String customerName;
	private String branch;
	private String product;
	private String scheme;
	private String dpd;
	private String totalOutstanding;
	private String otherCharges;
	private String principalOutstanding;
	private String recStatus;
	private	String makerId;
	private String makerDate;
	private	String autherId;
	private String autherDate;
	private int currentPageLink;
	private int totalRecordSize;
	private String updateForwardFlag;
	private String comments; 
	private String advocateCode ;
	private String reject;
	private String legalId;
	private String lbxUserId;
	private String pos; 
	private String caseType ;
	private String iMemoBy;
	private String reason;
	private String remark;
	private String lbxBranchId;
	private String lbxProductSearchID;
	private String lbxscheme;
	private String totalOutsatanding;
	private String advocateDesc;
	   private String lbxPoa;
	   private String poaDesc;
	   private String CaseTypeCode;
	   private String emailRM;
	   private String phoneRm;
	   private String loanID;
	   private String loanNO;
	   private String custName;
	   private String lHEmail;
	   private String lHPhone;
	   private String bHEmail;
	   private String bHPhone;
	   private String lbxcustomerId;
	   private String customerId;
	   private String custRole;
	
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getAdvocateDesc() {
		return advocateDesc;
	}
	public void setAdvocateDesc(String advocateDesc) {
		this.advocateDesc = advocateDesc;
	}
	public String getTotalOutsatanding() {
		return totalOutsatanding;
	}
	public void setTotalOutsatanding(String totalOutsatanding) {
		this.totalOutsatanding = totalOutsatanding;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getiMemoBy() {
		return iMemoBy;
	}
	public void setiMemoBy(String iMemoBy) {
		this.iMemoBy = iMemoBy;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	public String getLbxProductSearchID() {
		return lbxProductSearchID;
	}
	public void setLbxProductSearchID(String lbxProductSearchID) {
		this.lbxProductSearchID = lbxProductSearchID;
	}
	public String getLbxscheme() {
		return lbxscheme;
	}
	public void setLbxscheme(String lbxscheme) {
		this.lbxscheme = lbxscheme;
	}
	public String getLegalId() {
		return legalId;
	}
	public void setLegalId(String legalId) {
		this.legalId = legalId;
	}
	public String getReject() {
		return reject;
	}
	public void setReject(String reject) {
		this.reject = reject;
	}
	public String getAdvocateCode() {
		return advocateCode;
	}
	public void setAdvocateCode(String advocateCode) {
		this.advocateCode = advocateCode;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getUpdateForwardFlag() {
		return updateForwardFlag;
	}
	public void setUpdateForwardFlag(String updateForwardFlag) {
		this.updateForwardFlag = updateForwardFlag;
	}
	public String getInitMakerImemo() {
		return initMakerImemo;
	}
	public void setInitMakerImemo(String initMakerImemo) {
		this.initMakerImemo = initMakerImemo;
	}
	public String getInitMakerRemarks() {
		return initMakerRemarks;
	}
	public void setInitMakerRemarks(String initMakerRemarks) {
		this.initMakerRemarks = initMakerRemarks;
	}
	public String getLbxReasonId() {
		return lbxReasonId;
	}
	public void setLbxReasonId(String lbxReasonId) {
		this.lbxReasonId = lbxReasonId;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public String getLbxCaseTypeCode() {
		return lbxCaseTypeCode;
	}
	public void setLbxCaseTypeCode(String lbxCaseTypeCode) {
		this.lbxCaseTypeCode = lbxCaseTypeCode;
	}
	public String getCaseTypeDesc() {
		return caseTypeDesc;
	}
	public void setCaseTypeDesc(String caseTypeDesc) {
		this.caseTypeDesc = caseTypeDesc;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLbxLoanId() {
		return lbxLoanId;
	}
	public void setLbxLoanId(String lbxLoanId) {
		this.lbxLoanId = lbxLoanId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanNo() {
		return loanNo;
	}

	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
	public String getDpd() {
		return dpd;
	}
	public void setDpd(String dpd) {
		this.dpd = dpd;
	}
	public String getTotalOutstanding() {
		return totalOutstanding;
	}
	public void setTotalOutstanding(String totalOutstanding) {
		this.totalOutstanding = totalOutstanding;
	}
	public String getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(String otherCharges) {
		this.otherCharges = otherCharges;
	}
	public String getPrincipalOutstanding() {
		return principalOutstanding;
	}
	public void setPrincipalOutstanding(String principalOutstanding) {
		this.principalOutstanding = principalOutstanding;
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
	public String getLbxPoa() {
		    return this.lbxPoa;
		   }
		   public void setLbxPoa(String lbxPoa) {
		    this.lbxPoa = lbxPoa;
		   }
		   public String getPoaDesc() {
		    return this.poaDesc;
		   }
		   public void setPoaDesc(String poaDesc) {
		    this.poaDesc = poaDesc;
		   }
		   public String getCaseTypeCode() {
		    return this.CaseTypeCode;
		   }
		   public void setCaseTypeCode(String caseTypeCode) {
		    this.CaseTypeCode = caseTypeCode;
		   }
		   public String getEmailRM() {
		    return this.emailRM;
		   }
		   public void setEmailRM(String emailRM) {
		    this.emailRM = emailRM;
		   }
		   public String getPhoneRm() {
		    return this.phoneRm;
		   }
		   public void setPhoneRm(String phoneRm) {
		    this.phoneRm = phoneRm;
		   }
		   public String getLoanID() {
		    return this.loanID;
		   }
		   public void setLoanID(String loanID) {
		    this.loanID = loanID;
		   }
		   public String getLoanNO() {
		    return this.loanNO;
		   }
		   public void setLoanNO(String loanNO) {
		    this.loanNO = loanNO;
		   }
		   public String getCustName() {
		    return this.custName;
		   }
		   public void setCustName(String custName) {
		    this.custName = custName;
		   }
		   public String getlHEmail() {
		    return this.lHEmail;
		   }
		   public void setlHEmail(String lHEmail) {
		    this.lHEmail = lHEmail;
		   }
		   public String getlHPhone() {
		    return this.lHPhone;
		   }
		   public void setlHPhone(String lHPhone) {
		    this.lHPhone = lHPhone;
		   }
		   public String getbHEmail() {
		    return this.bHEmail;
		   }
		   public void setbHEmail(String bHEmail) {
		    this.bHEmail = bHEmail;
		   }
		   public String getbHPhone() {
		    return this.bHPhone;
		   }
		   public void setbHPhone(String bHPhone) {
		    this.bHPhone = bHPhone;
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
