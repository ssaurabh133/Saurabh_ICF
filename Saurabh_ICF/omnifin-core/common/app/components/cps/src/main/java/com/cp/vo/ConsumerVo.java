package com.cp.vo;

import org.apache.struts.upload.FormFile;

public class ConsumerVo {
	private String dealId;
	private String consumername;
	private String cibilCodes;
	private String totalAc;
	private String totalOverdueac;
	private String dateandtime;
	private String higherSanctionAmount;
	private String currentBalance;
	private String overDue;
	private String noofEnquiry;
	private String leadDate;
	private String leadTime;
	private String decison;
	private String comment;
	private String civilId;
	private FormFile cibilReportFileOne;
	private FormFile cibilReportFileTwo;
	private FormFile cibilReportFileThree;
	private String fileName;
	private String docPath;
	private String userName;
	private String makerDate;
	private String companyId;
	
	
	 private String Source;
	  private String InitatedDate;
	  public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	public String getInitatedDate() {
		return InitatedDate;
	}
	public void setInitatedDate(String initatedDate) {
		InitatedDate = initatedDate;
	}
	public String getCibilScore() {
		return CibilScore;
	}
	public void setCibilScore(String cibilScore) {
		CibilScore = cibilScore;
	}
	public String getEnquiryStatus() {
		return EnquiryStatus;
	}
	public void setEnquiryStatus(String enquiryStatus) {
		EnquiryStatus = enquiryStatus;
	}
	public String getInitatedBy() {
		return InitatedBy;
	}
	public void setInitatedBy(String initatedBy) {
		InitatedBy = initatedBy;
	}
	public String getDmsDocURL() {
		return DmsDocURL;
	}
	public void setDmsDocURL(String dmsDocURL) {
		DmsDocURL = dmsDocURL;
	}
	public String getDmsDocId() {
		return DmsDocId;
	}
	public void setDmsDocId(String dmsDocId) {
		DmsDocId = dmsDocId;
	}
	public String getSofcellCibilId() {
		return SofcellCibilId;
	}
	public void setSofcellCibilId(String sofcellCibilId) {
		SofcellCibilId = sofcellCibilId;
	}
	public String getDocDescription() {
		return DocDescription;
	}
	public void setDocDescription(String docDescription) {
		DocDescription = docDescription;
	}
	public String getDmsDocUrl() {
		return DmsDocUrl;
	}
	public void setDmsDocUrl(String dmsDocUrl) {
		DmsDocUrl = dmsDocUrl;
	}
	public String getDocumentId() {
		return DocumentId;
	}
	public void setDocumentId(String documentId) {
		DocumentId = documentId;
	}
	public String getLbxDocId() {
		return LbxDocId;
	}
	public void setLbxDocId(String lbxDocId) {
		LbxDocId = lbxDocId;
	}
	private String CibilScore;
	  private String EnquiryStatus;
	  private String InitatedBy;
	  private String DmsDocURL;
	  private String DmsDocId;
	  private String SofcellCibilId;
	  private String DocDescription;
	  private String DmsDocUrl;
	  private String DocumentId;
	  private String LbxDocId;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setConsumername(String consumername) {
		this.consumername = consumername;
	}
	public String getConsumername() {
		return consumername;
	}

	public String getCibilCodes() {
		return cibilCodes;
	}
	public void setCibilCodes(String cibilCodes) {
		this.cibilCodes = cibilCodes;
	}
	public String getTotalAc() {
		return totalAc;
	}
	public void setTotalAc(String totalAc) {
		this.totalAc = totalAc;
	}
	public String getTotalOverdueac() {
		return totalOverdueac;
	}
	public void setTotalOverdueac(String totalOverdueac) {
		this.totalOverdueac = totalOverdueac;
	}
	public String getHigherSanctionAmount() {
		return higherSanctionAmount;
	}
	public void setHigherSanctionAmount(String higherSanctionAmount) {
		this.higherSanctionAmount = higherSanctionAmount;
	}
	public String getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}
	public String getOverDue() {
		return overDue;
	}
	public void setOverDue(String overDue) {
		this.overDue = overDue;
	}
	public String getNoofEnquiry() {
		return noofEnquiry;
	}
	public void setNoofEnquiry(String noofEnquiry) {
		this.noofEnquiry = noofEnquiry;
	}
	public void setDecison(String decison) {
		this.decison = decison;
	}
	public String getDecison() {
		return decison;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComment() {
		return comment;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getDealId() {
		return dealId;
	}
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}
	public String getLeadTime() {
		return leadTime;
	}
	public void setLeadDate(String leadDate) {
		this.leadDate = leadDate;
	}
	public String getLeadDate() {
		return leadDate;
	}
	public void setDateandtime(String dateandtime) {
		this.dateandtime = dateandtime;
	}
	public String getDateandtime() {
		return dateandtime;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public String getCivilId() {
		return civilId;
	}
	public void setCibilReportFileThree(FormFile cibilReportFileThree) {
		this.cibilReportFileThree = cibilReportFileThree;
	}
	public FormFile getCibilReportFileThree() {
		return cibilReportFileThree;
	}
	public void setCibilReportFileTwo(FormFile cibilReportFileTwo) {
		this.cibilReportFileTwo = cibilReportFileTwo;
	}
	public FormFile getCibilReportFileTwo() {
		return cibilReportFileTwo;
	}
	public void setCibilReportFileOne(FormFile cibilReportFileOne) {
		this.cibilReportFileOne = cibilReportFileOne;
	}
	public FormFile getCibilReportFileOne() {
		return cibilReportFileOne;
	}
	
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public String getDocPath() {
		return docPath;
	}
	
	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}
	public String getMakerDate() {
		return makerDate;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
	
	

}
