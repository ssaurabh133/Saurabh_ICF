package com.cp.vo;

import java.io.Serializable;

public class LeadCaptureVo implements Serializable{
	private String leadno;
	private String hiddenleadno;
	private String gendate;
	private String leadGenerator;
	private String applicationdate;
	private String scheme;
	private String customername;
	private String product;
	private String rmname;
	private String branch;
	private String vendor;
	private String lbxLeadBranchDetail;
	private String lbxProductID;
	private String schemeId;
	private String lbxBranchId;
	private String status;
	private int totalRecordSize;
	private int CurrentPageLink;
	private String sourceType;
	private String sourceName;
	private String rmCode;
	private String sourceCode;
	private String name;
	private String id;
	private String leadGenerator1;
	private String userId;
	private String lbxUserId;
	private String reportingToUserId;
	private String userName;
	private String branchId;
	private String customertype;
	private String dealRm;
	private String dealRmName;
	private String loanType;
	private String getLoanType;
	private String otherDetails;
	//saurabh
	private String source;
	private String lovSourceDes;
	private String sourcedesc;
	//kanika code
	private String followupDate;
	private String sourceTypeDesc;
	
	
	
	public String getSourceTypeDesc() {
		return sourceTypeDesc;
	}
	public void setSourceTypeDesc(String sourceTypeDesc) {
		this.sourceTypeDesc = sourceTypeDesc;
	}
	private String lbxareaCodeVal;
   
	public String getLbxareaCodeVal() {
		return lbxareaCodeVal;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLovSourceDes() {
		return lovSourceDes;
	}
	public void setLovSourceDes(String lovSourceDes) {
		this.lovSourceDes = lovSourceDes;
	}
	public String getSourcedesc() {
		return sourcedesc;
	}
	public void setSourcedesc(String sourcedesc) {
		this.sourcedesc = sourcedesc;
	}
	public void setLbxareaCodeVal(String lbxareaCodeVal) {
		this.lbxareaCodeVal = lbxareaCodeVal;
	}
private String areaCodename;
	
	public String getAreaCodename() {
		return areaCodename;
	}
	public void setAreaCodename(String areaCodename) {
		this.areaCodename = areaCodename;
	}
	//kanika end
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getGetLoanType() {
		return getLoanType;
	}
	public void setGetLoanType(String getLoanType) {
		this.getLoanType = getLoanType;
	}
	public String getOtherDetails() {
		return otherDetails;
	}
	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}
	public String getDealRmName() {
		return dealRmName;
	}
	public void setDealRmName(String dealRmName) {
		this.dealRmName = dealRmName;
	}
	public String getDealRm() {
		return dealRm;
	}
	public void setDealRm(String dealRm) {
		this.dealRm = dealRm;
	}
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReportingToUserId() {
		return reportingToUserId;
	}
	public void setReportingToUserId(String reportingToUserId) {
		this.reportingToUserId = reportingToUserId;
	}
	public String getLeadGenerator1() {
		return leadGenerator1;
	}
	public void setLeadGenerator1(String leadGenerator1) {
		this.leadGenerator1 = leadGenerator1;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getRmCode() {
		return rmCode;
	}
	public void setRmCode(String rmCode) {
		this.rmCode = rmCode;
	}
	public String getHiddenleadno() {
		return hiddenleadno;
	}
	public void setHiddenleadno(String hiddenleadno) {
		this.hiddenleadno = hiddenleadno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCurrentPageLink() {
		return CurrentPageLink;
	}
	public void setCurrentPageLink(int currentPageLink) {
		CurrentPageLink = currentPageLink;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String getLbxBranchId() {
		return lbxBranchId;
	}
	public void setLbxBranchId(String lbxBranchId) {
		this.lbxBranchId = lbxBranchId;
	}
	public String getLeadno() {
		return leadno;
	}
	public void setLeadno(String leadno) {
		this.leadno = leadno;
	}
	
	public String getGendate() {
		return gendate;
	}
	public void setGendate(String gendate) {
		this.gendate = gendate;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getProduct() {
		return product;
	}
    public void setProduct(String product) {
		this.product = product;
	}
	public String getRmname() {
		return rmname;
	}
    public void setRmname(String rmname) {
		this.rmname = rmname;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getApplicationdate() {
		return applicationdate;
	}
	public void setApplicationdate(String applicationdate) {
		this.applicationdate = applicationdate;
	}
	public void setLeadGenerator(String leadGenerator) {
		this.leadGenerator = leadGenerator;
	}
	public String getLeadGenerator() {
		return leadGenerator;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getScheme() {
		return scheme;
	}
	public void setLbxLeadBranchDetail(String lbxLeadBranchDetail) {
		this.lbxLeadBranchDetail = lbxLeadBranchDetail;
	}
	public String getLbxLeadBranchDetail() {
		return lbxLeadBranchDetail;
	}
	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}
	public String getLbxProductID() {
		return lbxProductID;
	}
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	public String getSchemeId() {
		return schemeId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}
	public String getLbxUserId() {
		return lbxUserId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setFollowupDate(String followupDate) {
		this.followupDate = followupDate;
	}
	public String getFollowupDate() {
		return followupDate;
	}
				
}
