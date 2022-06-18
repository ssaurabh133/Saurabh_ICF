package com.cp.vo;

import org.apache.struts.upload.FormFile;

public class PartnerCaptureVO {
	
private String bpType;
private String bpName;
private String bpDesc;
private String makerId;
private String makerDate;
private String defaultBranch;
private int currentPageLink;
private int totalRecordSize;
private String addr_type;
private String addId;
private String addr1;
private String addr3;
private String addr2;
private String floorNo;
private String plotNo;
private String pincode;
private String tahsil;
private String txnTahsilHID;
private String txtDistCode;
private String dist;
private String state;
private String txtStateCode;
private String country;
private String txtCountryCode;
private String primaryPhoneNo;
private String lbxPincode;
private String tahsil4;
private String tahsilDesc;
private String alternatePhoneNo;
private String tollfreeNo;
private String faxNo;
private String landMark;
private String noYears;
private String noMonths;
private String distanceBranch;
private String communicationAddress;
private String addDetails;
private String customerProfile;
private String customerProfileOld;
private String customerName;
private String bpId;
private  String businessDesc;
private  String brands;
private  String productServices;
private  String noOfEmployees;
private  String auditors;
private  String certifications;
private  String awards;
private  String assocoationMembershipName;
private  String bankCode;
private  String lbxBankID;
private  String bankBranchName;
private  String lbxBranchID;
private  String ifscCode;
private  String micrCode;
private  String accountNo;
private String custBankID;
private String sex;
private String holderType;
private String dob;
private String mgmtPAN;
private String addId1;
private String dinNo;
private String primaryPhone;
private String primaryEmail;
private String website;
private String doj;
private String holderName;
private String holdingPerc;
private String totalExp;
private String addId2;
private String idNo;
private String alternatePhone;
private String position;
private String alternateEmail;
private String holderid;
private String[] documentId;
private String[] childId;
private String[] applicantName;
private String[] documentName;
private String[] status;
private String[] statusPrev;
private String[] recievedDate;
private String[] deferedDate;
private String[] expiryDate;
private String[] remarks;
private String docId;
private String docDesc;
private String recieveDate;
private String deferDate;
private String expirDate;
private String mandatory;
private String original;
private String applName;
private String userId;
private String bussinessDate;
private String docStatus;
private String docStatusPrev;
private String remark;
private String expirFlag;
private String type;
private String realDocId;
private String docChildFlag;
private String docChildId;
private String entityId;
// Changes by Amit
private String[] chk;
private String[] originalOrCopy;
private String[] mandatoryOrNonMandatory;
private String[] additionalDocStatus;
private String[] additionalReceivedDate;
private String[] additionalDeferredDate;
private String[] additionalExpiryDate;
private String[] additionalRemarks;
private String[] docNameAdditional;
private String txnType;
private String txnId;
private String StageId;
private String docType;
private String orgOrCopy;
private String mandateOrNonMandate;
private String addnDocStatus;
private String addnReceivedDate;
private String addnDeferredDate;
private String addnExpiryDate;
private String addnRemarks;
private String docNameAddn;
private String recStatus;
private String assetCollateralDesc;
private String daysExp;
private String corporateCategory;
private String constitution;
private String registrationNo;
private String pan;
private String industry;
private String lbxIndustry;
private String subIndustry;
private String lbxSubIndustry;
private String businessActivityId;
private String id;
private String name;
private String customerId;
private String groupType;
private String groupName;
private String hGroupId;
private String groupNameText;
private String incorporationDate;
private String vatRegNo;
private String businessSegment;
private String natureOfBus;
private String institutionEmail;
private String rpStageFlag;
private String reversingStage;
private String decision;
private String comments;
private String reasonDesc;
private String lbxReason;
private String agencyType;
private String agencyId;
private String contactPerson;
private String branchDesc;
private String allselection;
private String selection; 
private String lbxBranchIds;
private String branchId;
private String selectionAccess;
private String lastCustomerProfile;

private String firstAppDate;
private String agExpiryDate;
private String agrementDate; 
private String agencyRefinance;
private String branchSelection;
private String companyId;
private String allWaived;
private String fieldResults;
private String fieldRemarks;
private String verificationStatus;
private String verification;
private String seqNo;
private String stage;
private String bpReceived;
private String bpForwarded;
private String action;
private String escrow;
private String cibilScore;
private String cibilReamrks;
private String statusCase;
private String docDescription;
private String fileName;
private FormFile docFile;
private String docPath;
private String escrowType;
private String flatSno;
private String blockCompPerc;
private String authorRemarks;

private String documentDesc;
//changes by himanshu
private String documentStatus;
private String checkedvalue;

private String functionId;
private String autoDocId;
private String[] vDocType;//By Virender
private String vDocumentType;//By Virender

private String dmsId;

public String getCheckedValue() {
	return checkedvalue;
}
public void setCheckedValue(String checkedvalue) {
	this.checkedvalue = checkedvalue;
}
public String getDocumentStatus() {
	return documentStatus;
}
public void setDocumentStatus(String documentStatus) {
	this.documentStatus = documentStatus;
}
public String getDocumentDesc() {
	return documentDesc;
}
public void setDocumentDesc(String documentDesc) {
	this.documentDesc = documentDesc;
}
public String getDocDescription() {
	return docDescription;
}
public void setDocDescription(String docDescription) {
	this.docDescription = docDescription;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public FormFile getDocFile() {
	return docFile;
}
public void setDocFile(FormFile docFile) {
	this.docFile = docFile;
}
public String getDocPath() {
	return docPath;
}
public void setDocPath(String docPath) {
	this.docPath = docPath;
}
public String getCustomerId() {
	return customerId;
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
public String getCibilScore() {
	return cibilScore;
}
public void setCibilScore(String cibilScore) {
	this.cibilScore = cibilScore;
}
public String getCibilReamrks() {
	return cibilReamrks;
}
public void setCibilReamrks(String cibilReamrks) {
	this.cibilReamrks = cibilReamrks;
}
public String getSeqNo() {
	return seqNo;
}
public void setSeqNo(String seqNo) {
	this.seqNo = seqNo;
}
public String getStage() {
	return stage;
}
public void setStage(String stage) {
	this.stage = stage;
}
public String getBpReceived() {
	return bpReceived;
}
public void setBpReceived(String bpReceived) {
	this.bpReceived = bpReceived;
}
public String getBpForwarded() {
	return bpForwarded;
}
public void setBpForwarded(String bpForwarded) {
	this.bpForwarded = bpForwarded;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}
public String getBpForwardedUser() {
	return bpForwardedUser;
}
public void setBpForwardedUser(String bpForwardedUser) {
	this.bpForwardedUser = bpForwardedUser;
}
private String bpForwardedUser;

public String getAllWaived() {
	return allWaived;
}
public void setAllWaived(String allWaived) {
	this.allWaived = allWaived;
}
public String getFieldResults() {
	return fieldResults;
}
public void setFieldResults(String fieldResults) {
	this.fieldResults = fieldResults;
}
public String getFieldRemarks() {
	return fieldRemarks;
}
public void setFieldRemarks(String fieldRemarks) {
	this.fieldRemarks = fieldRemarks;
}
public String getVerificationStatus() {
	return verificationStatus;
}
public void setVerificationStatus(String verificationStatus) {
	this.verificationStatus = verificationStatus;
}
public String getFirstAppDate() {
	return firstAppDate;
}
public void setFirstAppDate(String firstAppDate) {
	this.firstAppDate = firstAppDate;
}
public String getAgExpiryDate() {
	return agExpiryDate;
}
public void setAgExpiryDate(String agExpiryDate) {
	this.agExpiryDate = agExpiryDate;
}

public String getAgencyType() {
	return agencyType;
}
public void setAgencyType(String agencyType) {
	this.agencyType = agencyType;
}
public String getAgencyId() {
	return agencyId;
}
public void setAgencyId(String agencyId) {
	this.agencyId = agencyId;
}
public String getReasonDesc() {
	return reasonDesc;
}
public void setReasonDesc(String reasonDesc) {
	this.reasonDesc = reasonDesc;
}
public String getLbxReason() {
	return lbxReason;
}
public void setLbxReason(String lbxReason) {
	this.lbxReason = lbxReason;
}
public String getRpStageFlag() {
	return rpStageFlag;
}
public void setRpStageFlag(String rpStageFlag) {
	this.rpStageFlag = rpStageFlag;
}
public String getReversingStage() {
	return reversingStage;
}
public void setReversingStage(String reversingStage) {
	this.reversingStage = reversingStage;
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
public String getInstitutionEmail() {
	return institutionEmail;
}
public void setInstitutionEmail(String institutionEmail) {
	this.institutionEmail = institutionEmail;
}
public String getWebAddress() {
	return webAddress;
}
public void setWebAddress(String webAddress) {
	this.webAddress = webAddress;
}
public String getYearOfEstb() {
	return yearOfEstb;
}
public void setYearOfEstb(String yearOfEstb) {
	this.yearOfEstb = yearOfEstb;
}
public String getSalesTax() {
	return salesTax;
}
public void setSalesTax(String salesTax) {
	this.salesTax = salesTax;
}
private String webAddress;
private String yearOfEstb;
private String salesTax;


public String getGroupType() {
	return groupType;
}
public void setGroupType(String groupType) {
	this.groupType = groupType;
}
public String getGroupName() {
	return groupName;
}
public void setGroupName(String groupName) {
	this.groupName = groupName;
}
public String gethGroupId() {
	return hGroupId;
}
public void sethGroupId(String hGroupId) {
	this.hGroupId = hGroupId;
}
public String getGroupNameText() {
	return groupNameText;
}
public void setGroupNameText(String groupNameText) {
	this.groupNameText = groupNameText;
}
public String getIncorporationDate() {
	return incorporationDate;
}
public void setIncorporationDate(String incorporationDate) {
	this.incorporationDate = incorporationDate;
}
public String getVatRegNo() {
	return vatRegNo;
}
public void setVatRegNo(String vatRegNo) {
	this.vatRegNo = vatRegNo;
}
public String getBusinessSegment() {
	return businessSegment;
}
public void setBusinessSegment(String businessSegment) {
	this.businessSegment = businessSegment;
}
public String getNatureOfBus() {
	return natureOfBus;
}
public void setNatureOfBus(String natureOfBus) {
	this.natureOfBus = natureOfBus;
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
public String getCorporateCategory() {
	return corporateCategory;
}
public void setCorporateCategory(String corporateCategory) {
	this.corporateCategory = corporateCategory;
}
public String getConstitution() {
	return constitution;
}
public void setConstitution(String constitution) {
	this.constitution = constitution;
}
public String getRegistrationNo() {
	return registrationNo;
}
public void setRegistrationNo(String registrationNo) {
	this.registrationNo = registrationNo;
}
public String getPan() {
	return pan;
}
public void setPan(String pan) {
	this.pan = pan;
}
public String getIndustry() {
	return industry;
}
public void setIndustry(String industry) {
	this.industry = industry;
}
public String getLbxIndustry() {
	return lbxIndustry;
}
public void setLbxIndustry(String lbxIndustry) {
	this.lbxIndustry = lbxIndustry;
}
public String getSubIndustry() {
	return subIndustry;
}
public void setSubIndustry(String subIndustry) {
	this.subIndustry = subIndustry;
}
public String getLbxSubIndustry() {
	return lbxSubIndustry;
}
public void setLbxSubIndustry(String lbxSubIndustry) {
	this.lbxSubIndustry = lbxSubIndustry;
}
public String[] getDocumentId() {
	return documentId;
}
public void setDocumentId(String[] documentId) {
	this.documentId = documentId;
}
public String[] getChildId() {
	return childId;
}
public void setChildId(String[] childId) {
	this.childId = childId;
}
public String[] getApplicantName() {
	return applicantName;
}
public void setApplicantName(String[] applicantName) {
	this.applicantName = applicantName;
}
public String[] getDocumentName() {
	return documentName;
}
public void setDocumentName(String[] documentName) {
	this.documentName = documentName;
}
public String[] getStatus() {
	return status;
}
public void setStatus(String[] status) {
	this.status = status;
}
public String[] getStatusPrev() {
	return statusPrev;
}
public void setStatusPrev(String[] statusPrev) {
	this.statusPrev = statusPrev;
}
public String[] getRecievedDate() {
	return recievedDate;
}
public void setRecievedDate(String[] recievedDate) {
	this.recievedDate = recievedDate;
}
public String[] getDeferedDate() {
	return deferedDate;
}
public void setDeferedDate(String[] deferedDate) {
	this.deferedDate = deferedDate;
}
public String[] getExpiryDate() {
	return expiryDate;
}
public void setExpiryDate(String[] expiryDate) {
	this.expiryDate = expiryDate;
}
public String[] getRemarks() {
	return remarks;
}
public void setRemarks(String[] remarks) {
	this.remarks = remarks;
}
public String getDocId() {
	return docId;
}
public void setDocId(String docId) {
	this.docId = docId;
}
public String getDocDesc() {
	return docDesc;
}
public void setDocDesc(String docDesc) {
	this.docDesc = docDesc;
}
public String getRecieveDate() {
	return recieveDate;
}
public void setRecieveDate(String recieveDate) {
	this.recieveDate = recieveDate;
}
public String getDeferDate() {
	return deferDate;
}
public void setDeferDate(String deferDate) {
	this.deferDate = deferDate;
}
public String getExpirDate() {
	return expirDate;
}
public void setExpirDate(String expirDate) {
	this.expirDate = expirDate;
}
public String getMandatory() {
	return mandatory;
}
public void setMandatory(String mandatory) {
	this.mandatory = mandatory;
}
public String getOriginal() {
	return original;
}
public void setOriginal(String original) {
	this.original = original;
}
public String getApplName() {
	return applName;
}
public void setApplName(String applName) {
	this.applName = applName;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getBussinessDate() {
	return bussinessDate;
}
public void setBussinessDate(String bussinessDate) {
	this.bussinessDate = bussinessDate;
}
public String getDocStatus() {
	return docStatus;
}
public void setDocStatus(String docStatus) {
	this.docStatus = docStatus;
}
public String getDocStatusPrev() {
	return docStatusPrev;
}
public void setDocStatusPrev(String docStatusPrev) {
	this.docStatusPrev = docStatusPrev;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public String getExpirFlag() {
	return expirFlag;
}
public void setExpirFlag(String expirFlag) {
	this.expirFlag = expirFlag;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getRealDocId() {
	return realDocId;
}
public void setRealDocId(String realDocId) {
	this.realDocId = realDocId;
}
public String getDocChildFlag() {
	return docChildFlag;
}
public void setDocChildFlag(String docChildFlag) {
	this.docChildFlag = docChildFlag;
}
public String getDocChildId() {
	return docChildId;
}
public void setDocChildId(String docChildId) {
	this.docChildId = docChildId;
}
public String getEntityId() {
	return entityId;
}
public void setEntityId(String entityId) {
	this.entityId = entityId;
}
public String[] getChk() {
	return chk;
}
public void setChk(String[] chk) {
	this.chk = chk;
}
public String[] getOriginalOrCopy() {
	return originalOrCopy;
}
public void setOriginalOrCopy(String[] originalOrCopy) {
	this.originalOrCopy = originalOrCopy;
}
public String[] getMandatoryOrNonMandatory() {
	return mandatoryOrNonMandatory;
}
public void setMandatoryOrNonMandatory(String[] mandatoryOrNonMandatory) {
	this.mandatoryOrNonMandatory = mandatoryOrNonMandatory;
}
public String[] getAdditionalDocStatus() {
	return additionalDocStatus;
}
public void setAdditionalDocStatus(String[] additionalDocStatus) {
	this.additionalDocStatus = additionalDocStatus;
}
public String[] getAdditionalReceivedDate() {
	return additionalReceivedDate;
}
public void setAdditionalReceivedDate(String[] additionalReceivedDate) {
	this.additionalReceivedDate = additionalReceivedDate;
}
public String[] getAdditionalDeferredDate() {
	return additionalDeferredDate;
}
public void setAdditionalDeferredDate(String[] additionalDeferredDate) {
	this.additionalDeferredDate = additionalDeferredDate;
}
public String[] getAdditionalExpiryDate() {
	return additionalExpiryDate;
}
public void setAdditionalExpiryDate(String[] additionalExpiryDate) {
	this.additionalExpiryDate = additionalExpiryDate;
}
public String[] getAdditionalRemarks() {
	return additionalRemarks;
}
public void setAdditionalRemarks(String[] additionalRemarks) {
	this.additionalRemarks = additionalRemarks;
}
public String[] getDocNameAdditional() {
	return docNameAdditional;
}
public void setDocNameAdditional(String[] docNameAdditional) {
	this.docNameAdditional = docNameAdditional;
}
public String getTxnType() {
	return txnType;
}
public void setTxnType(String txnType) {
	this.txnType = txnType;
}
public String getTxnId() {
	return txnId;
}
public void setTxnId(String txnId) {
	this.txnId = txnId;
}
public String getStageId() {
	return StageId;
}
public void setStageId(String stageId) {
	StageId = stageId;
}
public String getDocType() {
	return docType;
}
public void setDocType(String docType) {
	this.docType = docType;
}
public String getOrgOrCopy() {
	return orgOrCopy;
}
public void setOrgOrCopy(String orgOrCopy) {
	this.orgOrCopy = orgOrCopy;
}
public String getMandateOrNonMandate() {
	return mandateOrNonMandate;
}
public void setMandateOrNonMandate(String mandateOrNonMandate) {
	this.mandateOrNonMandate = mandateOrNonMandate;
}
public String getAddnDocStatus() {
	return addnDocStatus;
}
public void setAddnDocStatus(String addnDocStatus) {
	this.addnDocStatus = addnDocStatus;
}
public String getAddnReceivedDate() {
	return addnReceivedDate;
}
public void setAddnReceivedDate(String addnReceivedDate) {
	this.addnReceivedDate = addnReceivedDate;
}
public String getAddnDeferredDate() {
	return addnDeferredDate;
}
public void setAddnDeferredDate(String addnDeferredDate) {
	this.addnDeferredDate = addnDeferredDate;
}
public String getAddnExpiryDate() {
	return addnExpiryDate;
}
public void setAddnExpiryDate(String addnExpiryDate) {
	this.addnExpiryDate = addnExpiryDate;
}
public String getAddnRemarks() {
	return addnRemarks;
}
public void setAddnRemarks(String addnRemarks) {
	this.addnRemarks = addnRemarks;
}
public String getDocNameAddn() {
	return docNameAddn;
}
public void setDocNameAddn(String docNameAddn) {
	this.docNameAddn = docNameAddn;
}
public String getRecStatus() {
	return recStatus;
}
public void setRecStatus(String recStatus) {
	this.recStatus = recStatus;
}
public String getAssetCollateralDesc() {
	return assetCollateralDesc;
}
public void setAssetCollateralDesc(String assetCollateralDesc) {
	this.assetCollateralDesc = assetCollateralDesc;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getHolderType() {
	return holderType;
}
public void setHolderType(String holderType) {
	this.holderType = holderType;
}
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}
public String getMgmtPAN() {
	return mgmtPAN;
}
public void setMgmtPAN(String mgmtPAN) {
	this.mgmtPAN = mgmtPAN;
}
public String getAddId1() {
	return addId1;
}
public void setAddId1(String addId1) {
	this.addId1 = addId1;
}
public String getDinNo() {
	return dinNo;
}
public void setDinNo(String dinNo) {
	this.dinNo = dinNo;
}
public String getPrimaryPhone() {
	return primaryPhone;
}
public void setPrimaryPhone(String primaryPhone) {
	this.primaryPhone = primaryPhone;
}
public String getPrimaryEmail() {
	return primaryEmail;
}
public void setPrimaryEmail(String primaryEmail) {
	this.primaryEmail = primaryEmail;
}
public String getWebsite() {
	return website;
}
public void setWebsite(String website) {
	this.website = website;
}
public String getDoj() {
	return doj;
}
public void setDoj(String doj) {
	this.doj = doj;
}
public String getHolderName() {
	return holderName;
}
public void setHolderName(String holderName) {
	this.holderName = holderName;
}
public String getHoldingPerc() {
	return holdingPerc;
}
public void setHoldingPerc(String holdingPerc) {
	this.holdingPerc = holdingPerc;
}
public String getTotalExp() {
	return totalExp;
}
public void setTotalExp(String totalExp) {
	this.totalExp = totalExp;
}
public String getAddId2() {
	return addId2;
}
public void setAddId2(String addId2) {
	this.addId2 = addId2;
}
public String getIdNo() {
	return idNo;
}
public void setIdNo(String idNo) {
	this.idNo = idNo;
}
public String getAlternatePhone() {
	return alternatePhone;
}
public void setAlternatePhone(String alternatePhone) {
	this.alternatePhone = alternatePhone;
}
public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
}
public String getCompute() {
	return compute;
}
public void setCompute(String compute) {
	this.compute = compute;
}
private String compute;

public String getBankCode() {
	return bankCode;
}
public void setBankCode(String bankCode) {
	this.bankCode = bankCode;
}
public String getLbxBankID() {
	return lbxBankID;
}
public void setLbxBankID(String lbxBankID) {
	this.lbxBankID = lbxBankID;
}
public String getBankBranchName() {
	return bankBranchName;
}
public void setBankBranchName(String bankBranchName) {
	this.bankBranchName = bankBranchName;
}
public String getLbxBranchID() {
	return lbxBranchID;
}
public void setLbxBranchID(String lbxBranchID) {
	this.lbxBranchID = lbxBranchID;
}
public String getIfscCode() {
	return ifscCode;
}
public void setIfscCode(String ifscCode) {
	this.ifscCode = ifscCode;
}
public String getMicrCode() {
	return micrCode;
}
public void setMicrCode(String micrCode) {
	this.micrCode = micrCode;
}
public String getAccountNo() {
	return accountNo;
}
public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
}
public String getBrands() {
	return brands;
}
public void setBrands(String brands) {
	this.brands = brands;
}
public String getProductServices() {
	return productServices;
}
public void setProductServices(String productServices) {
	this.productServices = productServices;
}
public String getNoOfEmployees() {
	return noOfEmployees;
}
public void setNoOfEmployees(String noOfEmployees) {
	this.noOfEmployees = noOfEmployees;
}
public String getAuditors() {
	return auditors;
}
public void setAuditors(String auditors) {
	this.auditors = auditors;
}
public String getCertifications() {
	return certifications;
}
public void setCertifications(String certifications) {
	this.certifications = certifications;
}
public String getAwards() {
	return awards;
}
public void setAwards(String awards) {
	this.awards = awards;
}
public String getAssocoationMembershipName() {
	return assocoationMembershipName;
}
public void setAssocoationMembershipName(String assocoationMembershipName) {
	this.assocoationMembershipName = assocoationMembershipName;
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
public String getBpType() {
	return bpType;
}
public void setBpType(String bpType) {
	this.bpType = bpType;
}
public String getBpName() {
	return bpName;
}
public void setBpName(String bpName) {
	this.bpName = bpName;
}
public String getBpDesc() {
	return bpDesc;
}
public void setBpDesc(String bpDesc) {
	this.bpDesc = bpDesc;
}
public String getDefaultBranch() {
	return defaultBranch;
}
public void setDefaultBranch(String defaultBranch) {
	this.defaultBranch = defaultBranch;
}
public String getAddDetails() {
	return addDetails;
}
public void setAddDetails(String addDetails) {
	this.addDetails = addDetails;
}
public String getCustomerProfile() {
	return customerProfile;
}
public void setCustomerProfile(String customerProfile) {
	this.customerProfile = customerProfile;
}
public String getCustomerProfileOld() {
	return customerProfileOld;
}
public void setCustomerProfileOld(String customerProfileOld) {
	this.customerProfileOld = customerProfileOld;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public String getBpId() {
	return bpId;
}
public void setBpId(String bpId) {
	this.bpId = bpId;
}
public String getBusinessDesc() {
	return businessDesc;
}
public void setBusinessDesc(String businessDesc) {
	this.businessDesc = businessDesc;
}
public String getAddr_type() {
	return addr_type;
}
public void setAddr_type(String addr_type) {
	this.addr_type = addr_type;
}
public String getAddId() {
	return addId;
}
public void setAddId(String addId) {
	this.addId = addId;
}
public String getAddr1() {
	return addr1;
}
public void setAddr1(String addr1) {
	this.addr1 = addr1;
}
public String getAddr3() {
	return addr3;
}
public void setAddr3(String addr3) {
	this.addr3 = addr3;
}
public String getAddr2() {
	return addr2;
}
public void setAddr2(String addr2) {
	this.addr2 = addr2;
}
public String getFloorNo() {
	return floorNo;
}
public void setFloorNo(String floorNo) {
	this.floorNo = floorNo;
}
public String getPlotNo() {
	return plotNo;
}
public void setPlotNo(String plotNo) {
	this.plotNo = plotNo;
}
public String getPincode() {
	return pincode;
}
public void setPincode(String pincode) {
	this.pincode = pincode;
}
public String getTahsil() {
	return tahsil;
}
public void setTahsil(String tahsil) {
	this.tahsil = tahsil;
}
public String getTxnTahsilHID() {
	return txnTahsilHID;
}
public void setTxnTahsilHID(String txnTahsilHID) {
	this.txnTahsilHID = txnTahsilHID;
}
public String getTxtDistCode() {
	return txtDistCode;
}
public void setTxtDistCode(String txtDistCode) {
	this.txtDistCode = txtDistCode;
}
public String getDist() {
	return dist;
}
public void setDist(String dist) {
	this.dist = dist;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getTxtStateCode() {
	return txtStateCode;
}
public void setTxtStateCode(String txtStateCode) {
	this.txtStateCode = txtStateCode;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getTxtCountryCode() {
	return txtCountryCode;
}
public void setTxtCountryCode(String txtCountryCode) {
	this.txtCountryCode = txtCountryCode;
}
public String getPrimaryPhoneNo() {
	return primaryPhoneNo;
}
public void setPrimaryPhoneNo(String primaryPhoneNo) {
	this.primaryPhoneNo = primaryPhoneNo;
}
public String getLbxPincode() {
	return lbxPincode;
}
public void setLbxPincode(String lbxPincode) {
	this.lbxPincode = lbxPincode;
}
public String getTahsil4() {
	return tahsil4;
}
public void setTahsil4(String tahsil4) {
	this.tahsil4 = tahsil4;
}
public String getTahsilDesc() {
	return tahsilDesc;
}
public void setTahsilDesc(String tahsilDesc) {
	this.tahsilDesc = tahsilDesc;
}
public String getAlternatePhoneNo() {
	return alternatePhoneNo;
}
public void setAlternatePhoneNo(String alternatePhoneNo) {
	this.alternatePhoneNo = alternatePhoneNo;
}
public String getTollfreeNo() {
	return tollfreeNo;
}
public void setTollfreeNo(String tollfreeNo) {
	this.tollfreeNo = tollfreeNo;
}
public String getFaxNo() {
	return faxNo;
}
public void setFaxNo(String faxNo) {
	this.faxNo = faxNo;
}
public String getLandMark() {
	return landMark;
}
public void setLandMark(String landMark) {
	this.landMark = landMark;
}
public String getNoYears() {
	return noYears;
}
public void setNoYears(String noYears) {
	this.noYears = noYears;
}
public String getNoMonths() {
	return noMonths;
}
public void setNoMonths(String noMonths) {
	this.noMonths = noMonths;
}
public String getDistanceBranch() {
	return distanceBranch;
}
public void setDistanceBranch(String distanceBranch) {
	this.distanceBranch = distanceBranch;
}
public String getCommunicationAddress() {
	return communicationAddress;
}
public void setCommunicationAddress(String communicationAddress) {
	this.communicationAddress = communicationAddress;
}
public String getCustBankID() {
	return custBankID;
}
public void setCustBankID(String custBankID) {
	this.custBankID = custBankID;
}
public String getAlternateEmail() {
	return alternateEmail;
}
public void setAlternateEmail(String alternateEmail) {
	this.alternateEmail = alternateEmail;
}
public String getHolderid() {
	return holderid;
}
public void setHolderid(String holderid) {
	this.holderid = holderid;
}
public String getDaysExp() {
	return daysExp;
}
public void setDaysExp(String daysExp) {
	this.daysExp = daysExp;
}
public String getBusinessActivityId() {
	return businessActivityId;
}
public void setBusinessActivityId(String businessActivityId) {
	this.businessActivityId = businessActivityId;
}
public String getContactPerson() {
	return contactPerson;
}
public void setContactPerson(String contactPerson) {
	this.contactPerson = contactPerson;
}
public String getBranchDesc() {
	return branchDesc;
}
public void setBranchDesc(String branchDesc) {
	this.branchDesc = branchDesc;
}
public String getAllselection() {
	return allselection;
}
public void setAllselection(String allselection) {
	this.allselection = allselection;
}
public String getSelection() {
	return selection;
}
public void setSelection(String selection) {
	this.selection = selection;
}
public String getLbxBranchIds() {
	return lbxBranchIds;
}
public void setLbxBranchIds(String lbxBranchIds) {
	this.lbxBranchIds = lbxBranchIds;
}
public String getBranchId() {
	return branchId;
}
public void setBranchId(String branchId) {
	this.branchId = branchId;
}
public String getSelectionAccess() {
	return selectionAccess;
}
public void setSelectionAccess(String selectionAccess) {
	this.selectionAccess = selectionAccess;
}
public String getLastCustomerProfile() {
	return lastCustomerProfile;
}
public void setLastCustomerProfile(String lastCustomerProfile) {
	this.lastCustomerProfile = lastCustomerProfile;
}
public String getAgrementDate() {
	return agrementDate;
}
public void setAgrementDate(String agrementDate) {
	this.agrementDate = agrementDate;
}
public String getAgencyRefinance() {
	return agencyRefinance;
}
public void setAgencyRefinance(String agencyRefinance) {
	this.agencyRefinance = agencyRefinance;
}
public String getBranchSelection() {
	return branchSelection;
}
public void setBranchSelection(String branchSelection) {
	this.branchSelection = branchSelection;
}
public String getCompanyId() {
	return companyId;
}
public void setCompanyId(String companyId) {
	this.companyId = companyId;
}
public String getVerification() {
	return verification;
}
public void setVerification(String verification) {
	this.verification = verification;
}
public String getEscrow() {
	return escrow;
}
public void setEscrow(String escrow) {
	this.escrow = escrow;
}
public String getStatusCase() {
	return statusCase;
}
public void setStatusCase(String statusCase) {
	this.statusCase = statusCase;
}
public String getEscrowType() {
	return escrowType;
}
public void setEscrowType(String escrowType) {
	this.escrowType = escrowType;
}
public String getFlatSno() {
	return flatSno;
}
public void setFlatSno(String flatSno) {
	this.flatSno = flatSno;
}
public String getBlockCompPerc() {
	return blockCompPerc;
}
public void setBlockCompPerc(String blockCompPerc) {
	this.blockCompPerc = blockCompPerc;
}
public String getAuthorRemarks() {
	return authorRemarks;
}
public void setAuthorRemarks(String authorRemarks) {
	this.authorRemarks = authorRemarks;
}
public String getFunctionId() {
	return functionId;
}
public void setFunctionId(String functionId) {
	this.functionId = functionId;
}
public String getAutoDocId() {
	return autoDocId;
}
public void setAutoDocId(String autoDocId) {
	this.autoDocId = autoDocId;
}
public String[] getvDocType() {
	return vDocType;
}
public void setvDocType(String[] vDocType) {
	this.vDocType = vDocType;
}
public String getvDocumentType() {
	return vDocumentType;
}
public void setvDocumentType(String vDocumentType) {
	this.vDocumentType = vDocumentType;
}
public String getDmsId() {
	return dmsId;
}
public void setDmsId(String dmsId) {
	this.dmsId = dmsId;
}

}
