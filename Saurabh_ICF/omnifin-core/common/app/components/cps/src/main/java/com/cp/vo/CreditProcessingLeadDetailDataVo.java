package com.cp.vo;

import java.io.Serializable;

public class CreditProcessingLeadDetailDataVo
  implements Serializable
{
  private String indconstitution;
  private String corconstitution;
  private String contitution;
  private String list;
  private String registrationNo;
  private String leadId;
  private String groupName;
  private String leadGenerator;
  private String leadGenerator2;
  private String relationship;
  private String servicingRm;
  private String customerName;
  private String contactPerson;
  private String personDesignation;
  private String address1;
  private String address2;
  private String address3;
  private String country;
  private String state;
  private String dist;
  private String pincode;
  private String phoneOff;
  private String phoneRes;
  private String mobile;
  private String email;
  private String altEmail;
  private String product;
  private String scheme;
  private String loanAmount;
  private String loanTenure;
  private String loanPurpose;
  private String leadallocation;
  private String applicationdate;
  private String status;
  private String description;
  private String rmName;
  private String rmCode;
  private String rmHead;
  private String vendorName;
  private String vendorCode;
  private String vendorHead;
  private String branchName;
  private String branchHead;
  private String branchCode;
  private String contitutionCode;
  private String constitution;
  private String remarks;
  private String decision;
  private String businessSegmentCode;
  private String businessSegmentDesc;
  private String contactnorm;
  private String leadgenzonerm;
  private String leadgencityrm;
  private String contactnovendor;
  private String leadgenzonevendor;
  private String leadgencityvendor;
  private String contactnobranch;
  private String leadgenzonebranch;
  private String leadgencitybranch;
  private String lbxRegionID;
  private String lbxvendorCode;
  private String contactno1;
  private String contactno2;
  private String leadgenzone1;
  private String leadgencity1;
  private String lbxRegionID1;
  private String lbxvendorCode1;
  private String leadgenzone2;
  private String leadgencity2;
  private String lbxRegionID2;
  private String lbxvendorCode2;
  private String rmName1;
  private String rmCode1;
  private String rmHead1;
  private String vendorName1;
  private String vendorCode1;
  private String vendorHead1;
  private String branchName1;
  private String branchHead1;
  private String branchCode1;
  private String productType;
  private String currentStage;
  private String hGroupId;
  private String businessSegment;
  private String txtCountryCode;
  private String txtStateCode;
  private String txtDistCode;
  private String lbxProductID;
  private String schemeId;
  private String allocatedRm;
  private String allocatedBranch;
  private String registration_no;
  private String firstName;
  private String lastName;
  private String custDOB;
  private String lbxLeadBranchDetail;
  private String lbxUserId;
  private String branchDet;
  private String rmAllo;
  private String lbxBranchId;
  private String makerId;
  private String authorId;
  private String relationshipSince;
  private String landmark;
  private String noOfYears;
  private String hIndustry;
  private String lbxSubIndustry;
  private String industry;
  private String subIndustry;
  private String lbxIndustry;
  private String expectedLoginDate;
  private String expectedDisbursalDate;
  private String leadGenerator1;
  private String txnId;
  private String defaultcountryid;
  private String defaultcountryname;
  private String customerType;
  private String addresstypeid;
  private String addresstypename;
  private String addresstype;
  private String custPan;
  private String groupDesc;
  private String groupType;
  private String lbxbranchHead1;
  private String sourceList;
  private String sourceListHidden;
  private String groupName1;
  private String loanType;
  private String getLoanType;
  private String leadGeneratorSelect;
  private String otherDetails;
  private String lbxBranchIdOther;
  private String branchHead1Other;
  private String contactnobranchOther;
  private String leadgencitybranchOther;
  private String branchName1Other;
  private String branchCode1Other;
  private String custPanInd;
  private String salary;
  private String escalationFlag;
  private String noMonths;
  private String owner;
  private String fatherName;
  private String passport;
  private String dlNumber;
  private String voterId;
  private String tahsil;
  private String eduDetail;
  private String eduDetailInd;
  private String reasonId;
  private String dateOfIncorporation;
  private String uidNo;
  private String vatNo;
  private String tinNo;
  private String refNo;
  private String customerId;
  private String txnType;
  private String addString;
  private String partner;
  private String dealNo;
  private String dealStatus;
  private String dedupeRemarks;
  private String rule;
  private String ruleWeightage;
  private int dynamicWeightage;
  private String txnTahsilHID;
  private String middleName;
  private String lbxCustomerId;
  private int lbxOtherDetails;
  private String lbxother;
  private String assetdescOther;
  private String eduDetailCode;
  private String eduDetailDesc;
  private String lbxareaCodeVal;
  private String tahsilDesc;
  private String sectorType;
  private String source;
  private String dupDealId;
  private String areaCodename;
  private String turnOver;
  	private String coApptxnTahsilHID;
	private String gaurtxnTahsilHID;
	
	//harsh
	private String gaurcorconstitution;
	private String gaurindconstitution;
	private String gaurcustDOB;
	private String gaurhGroupId;
	private String gaurgroupName;
	private String gaurgroupName1;
	private String gaurconstitution;
	private String gaurregistrationNo;
	private String gaurcustPan;
	private String gaurbusinessSegment;
	private String gaurfirstName;
	private String gaurmiddleName;
	private String gaurlastName;
	private String gaurincorporationDate;	
	private String gaurrelationship;
	private String gaurcustomerName;
	private String gauraddress1;
	private String gauraddress2;
	private String gauraddress3;
	private String gaurcountry;
	private String gaurstate;
	private String gaurdist;
	private String gaurpincode;
	private String gaurphoneOff;
	private String gaurphoneRes;
	private String gaurmobile;
	private String gauremail;	
	private String gaurcustomerType;
	private String gaurgroupType;
	private String gaurtxtCountryCode;
	private String gaurtxtStateCode;
	private String gaurtxtDistCode;
	private String gaurlandmark;
	private String gaurhIndustry;
	private String gaurlbxSubIndustry;
	private String gaursubIndustry;
	private String gaurindustry;
	private String gaurlbxIndustry;	
	private String gaurfatherName;
	private String gaurpassport;
	private String gaurdlNumber;
	private String gaurvoterId;
	private String gaurtahsil;
	private String gauraddresstype;
	private String gaurturnover;	
	private String gaurcustPanInd;
	private String coAppcorconstitution;
	private String coAppindconstitution;
	private String coAppcustDOB;
	private String coApphGroupId;
	private String coAppgroupName;
	private String coAppgroupName1;
	private String coAppconstitution;
	private String coAppregistrationNo;
	private String coAppcustPan;
	private String coAppbusinessSegment;
	private String coAppfirstName;
	private String coAppmiddleName;
	private String coApplastName;
	private String coAppincorporationDate;	
	private String coApprelationship;
	private String coAppcustomerName;
	private String coAppaddress1;
	private String coAppaddress2;
	private String coAppaddress3;
	private String coAppcountry;
	private String coAppstate;
	private String coAppdist;
	private String coApppincode;
	private String coAppphoneOff;
	private String coAppphoneRes;
	private String coAppmobile;
	private String coAppemail;	
	private String coAppcustomerType;
	private String coAppgroupType;
	private String coApptxtCountryCode;
	private String coApptxtStateCode;
	private String coApptxtDistCode;
	private String coApplandmark;
	private String coApphIndustry;
	private String coApplbxSubIndustry;
	private String coAppsubIndustry;
	private String coAppindustry;
	private String coApplbxIndustry;	
	private String coAppfatherName;
	private String coApppassport;
	private String coAppdlNumber;
	private String coAppvoterId;
	private String coApptahsil;
	private String coAppaddresstype;
	private String coAppturnover;	
	private String coAppcustPanInd;
	private String gauraadhaar;
	private String gaurgenderIndividual;
	private String coAppaadhaar;
	private String coAppgenderIndividual;
	private String genderIndividual;
	private String aadhaar;
	private String coAppcontitutionCode;
	private String coAppgroupDesc;
	private String coApptahsilDesc;
	private String gaurcontitutionCode;
	private String gaurgroupDesc;
	private String gaurtahsilDesc;
	private String gaurhgroupId;
	private String coApplbxCustomerId;
	private String gaurlbxCustomerId;
	private String coAppStatus1;
	private String gaurStatus1;
	//pooja changes for Application Form No
		private String applicationFormNoRm;
		private String applicationFormNoVendor;
		private String applicationFormNoBranch;
		private String applicationFormNoOther;
		//pooja changes for Application Form No
  private String floorNo;
  private String plotNo;
	private String custType;
	private String coAppcustCode;
	private String gaurcustCode;
	private String custId;
	private String updateCustId;
	
	private String relationshipManager;
	private String lbxRelationship;
	private String generatedUser;
	private String lbxUserSearchId;
	
  public String getEduDetailInd()
  {
    return this.eduDetailInd;
  }
  public void setEduDetailInd(String eduDetailInd) {
    this.eduDetailInd = eduDetailInd;
  }

  public String getEduDetail()
  {
    return this.eduDetail;
  }
  public void setEduDetail(String eduDetail) {
    this.eduDetail = eduDetail;
  }
  public String getEduDetailCode() {
    return this.eduDetailCode;
  }
  public void setEduDetailCode(String eduDetailCode) {
    this.eduDetailCode = eduDetailCode;
  }
  public String getEduDetailDesc() {
    return this.eduDetailDesc;
  }
  public void setEduDetailDesc(String eduDetailDesc) {
    this.eduDetailDesc = eduDetailDesc;
  }
  public String getTahsilDesc() {
    return this.tahsilDesc;
  }
  public void setTahsilDesc(String tahsilDesc) {
    this.tahsilDesc = tahsilDesc;
  }
  public String getLbxareaCodeVal() {
    return this.lbxareaCodeVal;
  }
  public void setLbxareaCodeVal(String lbxareaCodeVal) {
    this.lbxareaCodeVal = lbxareaCodeVal;
  }

  public String getAreaCodename()
  {
    return this.areaCodename;
  }
  public void setAreaCodename(String areaCodename) {
    this.areaCodename = areaCodename;
  }

  public String getFatherName() {
    return this.fatherName;
  }
  public String getSourceList() {
    return this.sourceList;
  }
  public void setSourceList(String sourceList) {
    this.sourceList = sourceList;
  }
  public void setFatherName(String fatherName) {
    this.fatherName = fatherName;
  }
  public String getPassport() {
    return this.passport;
  }
  public void setPassport(String passport) {
    this.passport = passport;
  }
  public String getDlNumber() {
    return this.dlNumber;
  }
  public void setDlNumber(String dlNumber) {
    this.dlNumber = dlNumber;
  }
  public String getVoterId() {
    return this.voterId;
  }
  public void setVoterId(String voterId) {
    this.voterId = voterId;
  }

  public String getTahsil() {
    return this.tahsil;
  }
  public void setTahsil(String tahsil) {
    this.tahsil = tahsil;
  }
  public String getOwner() {
    return this.owner;
  }
  public void setOwner(String owner) {
    this.owner = owner;
  }
  public String getNoMonths() {
    return this.noMonths;
  }
  public void setNoMonths(String noMonths) {
    this.noMonths = noMonths;
  }
  public String getLoanType() {
    return this.loanType;
  }
  public void setLoanType(String loanType) {
    this.loanType = loanType;
  }
  public String getGetLoanType() {
    return this.getLoanType;
  }
  public void setGetLoanType(String getLoanType) {
    this.getLoanType = getLoanType;
  }
  public String getOtherDetails() {
    return this.otherDetails;
  }
  public void setOtherDetails(String otherDetails) {
    this.otherDetails = otherDetails;
  }
  public String getGroupName1() {
    return this.groupName1;
  }
  public void setGroupName1(String groupName1) {
    this.groupName1 = groupName1;
  }
  public String getGroupDesc() {
    return this.groupDesc;
  }
  public void setGroupDesc(String groupDesc) {
    this.groupDesc = groupDesc;
  }
  public String getGroupType() {
    return this.groupType;
  }
  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }
  public String getIndconstitution() {
    return this.indconstitution;
  }
  public void setIndconstitution(String indconstitution) {
    this.indconstitution = indconstitution;
  }
  public String getCorconstitution() {
    return this.corconstitution;
  }
  public void setCorconstitution(String corconstitution) {
    this.corconstitution = corconstitution;
  }
  public String getContitution() {
    return this.contitution;
  }
  public void setContitution(String contitution) {
    this.contitution = contitution;
  }
  public String getBusinessSegment() {
    return this.businessSegment;
  }
  public void setBusinessSegment(String businessSegment) {
    this.businessSegment = businessSegment;
  }
  public String getGroupName() {
    return this.groupName;
  }
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getRegistrationNo() {
    return this.registrationNo;
  }
  public void setRegistrationNo(String registrationNo) {
    this.registrationNo = registrationNo;
  }

  public String getCustPan()
  {
    return this.custPan;
  }
  public void setCustPan(String custPan) {
    this.custPan = custPan;
  }
  public String gethGroupId() {
    return this.hGroupId;
  }
  public void sethGroupId(String hGroupId) {
    this.hGroupId = hGroupId;
  }
  public String getRegistration_no() {
    return this.registration_no;
  }
  public void setRegistration_no(String registrationNo) {
    this.registration_no = registrationNo;
  }
  public String getFirstName() {
    return this.firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return this.lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getCustDOB() {
    return this.custDOB;
  }
  public void setCustDOB(String custDOB) {
    this.custDOB = custDOB;
  }
  public String getContitutionCode() {
    return this.contitutionCode;
  }
  public void setContitutionCode(String contitutionCode) {
    this.contitutionCode = contitutionCode;
  }
  public String getConstitution() {
    return this.constitution;
  }
  public void setConstitution(String constitution) {
    this.constitution = constitution;
  }
  public String getBusinessSegmentCode() {
    return this.businessSegmentCode;
  }
  public void setBusinessSegmentCode(String businessSegmentCode) {
    this.businessSegmentCode = businessSegmentCode;
  }
  public String getBusinessSegmentDesc() {
    return this.businessSegmentDesc;
  }
  public void setBusinessSegmentDesc(String businessSegmentDesc) {
    this.businessSegmentDesc = businessSegmentDesc;
  }
  public String getAddresstype() {
    return this.addresstype;
  }
  public void setAddresstype(String addresstype) {
    this.addresstype = addresstype;
  }

  public String getAddresstypeid() {
    return this.addresstypeid;
  }
  public void setAddresstypeid(String addresstypeid) {
    this.addresstypeid = addresstypeid;
  }
  public String getTurnOver() {
    return this.turnOver;
  }
  public void setTurnOver(String turnOver) {
    this.turnOver = turnOver;
  }
  public String getAddresstypename() {
    return this.addresstypename;
  }
  public void setAddresstypename(String addresstypename) {
    this.addresstypename = addresstypename;
  }
  public String getCustomerType() {
    return this.customerType;
  }
  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }
  public String getTxnId() {
    return this.txnId;
  }
  public void setTxnId(String txnId) {
    this.txnId = txnId;
  }
  public String getAuthorId() {
    return this.authorId;
  }
  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }
  public String getLeadGenerator1() {
    return this.leadGenerator1;
  }
  public void setLeadGenerator1(String leadGenerator1) {
    this.leadGenerator1 = leadGenerator1;
  }
  public String getCurrentStage() {
    return this.currentStage;
  }
  public void setCurrentStage(String currentStage) {
    this.currentStage = currentStage;
  }
  public String getProductType() {
    return this.productType;
  }
  public void setProductType(String productType) {
    this.productType = productType;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getExpectedLoginDate() {
    return this.expectedLoginDate;
  }
  public void setExpectedLoginDate(String expectedLoginDate) {
    this.expectedLoginDate = expectedLoginDate;
  }
  public String getExpectedDisbursalDate() {
    return this.expectedDisbursalDate;
  }
  public void setExpectedDisbursalDate(String expectedDisbursalDate) {
    this.expectedDisbursalDate = expectedDisbursalDate;
  }
  public String getLbxIndustry() {
    return this.lbxIndustry;
  }
  public void setLbxIndustry(String lbxIndustry) {
    this.lbxIndustry = lbxIndustry;
  }
  public String getIndustry() {
    return this.industry;
  }
  public void setIndustry(String industry) {
    this.industry = industry;
  }
  public String getSubIndustry() {
    return this.subIndustry;
  }
  public void setSubIndustry(String subIndustry) {
    this.subIndustry = subIndustry;
  }
  public String getRelationshipSince() {
    return this.relationshipSince;
  }
  public void setRelationshipSince(String relationshipSince) {
    this.relationshipSince = relationshipSince;
  }
  public String getLandmark() {
    return this.landmark;
  }
  public void setLandmark(String landmark) {
    this.landmark = landmark;
  }
  public String getNoOfYears() {
    return this.noOfYears;
  }
  public void setNoOfYears(String noOfYears) {
    this.noOfYears = noOfYears;
  }
  public String gethIndustry() {
    return this.hIndustry;
  }
  public void sethIndustry(String hIndustry) {
    this.hIndustry = hIndustry;
  }
  public String getLbxSubIndustry() {
    return this.lbxSubIndustry;
  }
  public void setLbxSubIndustry(String lbxSubIndustry) {
    this.lbxSubIndustry = lbxSubIndustry;
  }

  public String getAddress3() {
    return this.address3;
  }
  public void setAddress3(String address3) {
    this.address3 = address3;
  }
  public String getMakerId() {
    return this.makerId;
  }
  public void setMakerId(String makerId) {
    this.makerId = makerId;
  }
  public String getLeadGenerator2() {
    return this.leadGenerator2;
  }
  public void setLeadGenerator2(String leadGenerator2) {
    this.leadGenerator2 = leadGenerator2;
  }
  public String getLbxBranchId() {
    return this.lbxBranchId;
  }
  public void setLbxBranchId(String lbxBranchId) {
    this.lbxBranchId = lbxBranchId;
  }
  public String getBranchName1() {
    return this.branchName1;
  }
  public void setBranchName1(String branchName1) {
    this.branchName1 = branchName1;
  }
  public String getBranchCode1() {
    return this.branchCode1;
  }
  public void setBranchCode1(String branchCode1) {
    this.branchCode1 = branchCode1;
  }
  public String getContactno1() {
    return this.contactno1;
  }
  public void setContactno1(String contactno1) {
    this.contactno1 = contactno1;
  }
  public String getContactno2() {
    return this.contactno2;
  }
  public void setContactno2(String contactno2) {
    this.contactno2 = contactno2;
  }
  public String getLeadgenzone1() {
    return this.leadgenzone1;
  }
  public void setLeadgenzone1(String leadgenzone1) {
    this.leadgenzone1 = leadgenzone1;
  }
  public String getLeadgencity1() {
    return this.leadgencity1;
  }
  public void setLeadgencity1(String leadgencity1) {
    this.leadgencity1 = leadgencity1;
  }
  public String getLbxRegionID1() {
    return this.lbxRegionID1;
  }
  public void setLbxRegionID1(String lbxRegionID1) {
    this.lbxRegionID1 = lbxRegionID1;
  }
  public String getLbxvendorCode1() {
    return this.lbxvendorCode1;
  }
  public void setLbxvendorCode1(String lbxvendorCode1) {
    this.lbxvendorCode1 = lbxvendorCode1;
  }
  public String getLeadgenzone2() {
    return this.leadgenzone2;
  }
  public void setLeadgenzone2(String leadgenzone2) {
    this.leadgenzone2 = leadgenzone2;
  }
  public String getLeadgencity2() {
    return this.leadgencity2;
  }
  public void setLeadgencity2(String leadgencity2) {
    this.leadgencity2 = leadgencity2;
  }
  public String getLbxRegionID2() {
    return this.lbxRegionID2;
  }
  public void setLbxRegionID2(String lbxRegionID2) {
    this.lbxRegionID2 = lbxRegionID2;
  }
  public String getLbxvendorCode2() {
    return this.lbxvendorCode2;
  }
  public void setLbxvendorCode2(String lbxvendorCode2) {
    this.lbxvendorCode2 = lbxvendorCode2;
  }
  public String getBranchDet() {
    return this.branchDet;
  }
  public void setBranchDet(String branchDet) {
    this.branchDet = branchDet;
  }
  public String getRmAllo() {
    return this.rmAllo;
  }
  public void setRmAllo(String rmAllo) {
    this.rmAllo = rmAllo;
  }
  public String getLbxLeadBranchDetail() {
    return this.lbxLeadBranchDetail;
  }
  public void setLbxLeadBranchDetail(String lbxLeadBranchDetail) {
    this.lbxLeadBranchDetail = lbxLeadBranchDetail;
  }
  public String getLbxUserId() {
    return this.lbxUserId;
  }
  public void setLbxUserId(String lbxUserId) {
    this.lbxUserId = lbxUserId;
  }
  public String getAllocatedRm() {
    return this.allocatedRm;
  }
  public void setAllocatedRm(String allocatedRm) {
    this.allocatedRm = allocatedRm;
  }
  public String getAllocatedBranch() {
    return this.allocatedBranch;
  }
  public void setAllocatedBranch(String allocatedBranch) {
    this.allocatedBranch = allocatedBranch;
  }
  public String getLbxvendorCode() {
    return this.lbxvendorCode;
  }
  public void setLbxvendorCode(String lbxvendorCode) {
    this.lbxvendorCode = lbxvendorCode;
  }
  public void setLeadId(String leadId) {
    this.leadId = leadId;
  }
  public String getLeadGenerator() {
    return this.leadGenerator;
  }
  public void setLeadGenerator(String leadGenerator) {
    this.leadGenerator = leadGenerator;
  }
  public String getRelationship() {
    return this.relationship;
  }
  public void setRelationship(String relationship) {
    this.relationship = relationship;
  }
  public String getServicingRm() {
    return this.servicingRm;
  }
  public void setServicingRm(String servicingRm) {
    this.servicingRm = servicingRm;
  }
  public String getCustomerName() {
    return this.customerName;
  }
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }
  public String getContactPerson() {
    return this.contactPerson;
  }
  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }
  public String getPersonDesignation() {
    return this.personDesignation;
  }
  public void setPersonDesignation(String personDesignation) {
    this.personDesignation = personDesignation;
  }
  public String getAddress1() {
    return this.address1;
  }
  public void setAddress1(String address1) {
    this.address1 = address1;
  }
  public String getAddress2() {
    return this.address2;
  }
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  public String getCountry() {
    return this.country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getPincode() {
    return this.pincode;
  }
  public void setPincode(String pincode) {
    this.pincode = pincode;
  }
  public String getPhoneOff() {
    return this.phoneOff;
  }
  public void setPhoneOff(String phoneOff) {
    this.phoneOff = phoneOff;
  }
  public String getPhoneRes() {
    return this.phoneRes;
  }
  public void setPhoneRes(String phoneRes) {
    this.phoneRes = phoneRes;
  }
  public String getMobile() {
    return this.mobile;
  }
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getAltEmail() {
    return this.altEmail;
  }
  public void setAltEmail(String altEmail) {
    this.altEmail = altEmail;
  }
  public String getProduct() {
    return this.product;
  }
  public void setProduct(String product) {
    this.product = product;
  }
  public String getScheme() {
    return this.scheme;
  }
  public void setScheme(String scheme) {
    this.scheme = scheme;
  }
  public String getLoanAmount() {
    return this.loanAmount;
  }
  public void setLoanAmount(String loanAmount) {
    this.loanAmount = loanAmount;
  }
  public String getLoanTenure() {
    return this.loanTenure;
  }
  public void setLoanTenure(String loanTenure) {
    this.loanTenure = loanTenure;
  }
  public String getLoanPurpose() {
    return this.loanPurpose;
  }
  public void setLoanPurpose(String loanPurpose) {
    this.loanPurpose = loanPurpose;
  }
  public void setLeadallocation(String leadallocation) {
    this.leadallocation = leadallocation;
  }
  public String getLeadallocation() {
    return this.leadallocation;
  }
  public void setList(String list) {
    this.list = list;
  }
  public String getList() {
    return this.list;
  }
  public void setApplicationdate(String applicationdate) {
    this.applicationdate = applicationdate;
  }
  public String getApplicationdate() {
    return this.applicationdate;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getStatus() {
    return this.status;
  }
  public void setRmCode(String rmCode) {
    this.rmCode = rmCode;
  }
  public String getRmCode() {
    return this.rmCode;
  }
  public void setRmHead(String rmHead) {
    this.rmHead = rmHead;
  }
  public String getRmHead() {
    return this.rmHead;
  }
  public void setRmName(String rmName) {
    this.rmName = rmName;
  }
  public String getRmName() {
    return this.rmName;
  }
  public String getVendorName() {
    return this.vendorName;
  }
  public void setVendorName(String vendorName) {
    this.vendorName = vendorName;
  }
  public String getVendorCode() {
    return this.vendorCode;
  }
  public void setVendorCode(String vendorCode) {
    this.vendorCode = vendorCode;
  }
  public String getVendorHead() {
    return this.vendorHead;
  }
  public void setVendorHead(String vendorHead) {
    this.vendorHead = vendorHead;
  }
  public void setDist(String dist) {
    this.dist = dist;
  }
  public String getDist() {
    return this.dist;
  }

  public String getTxtCountryCode() {
    return this.txtCountryCode;
  }
  public void setTxtCountryCode(String txtCountryCode) {
    this.txtCountryCode = txtCountryCode;
  }
  public String getTxtStateCode() {
    return this.txtStateCode;
  }
  public void setTxtStateCode(String txtStateCode) {
    this.txtStateCode = txtStateCode;
  }
  public String getTxtDistCode() {
    return this.txtDistCode;
  }
  public void setTxtDistCode(String txtDistCode) {
    this.txtDistCode = txtDistCode;
  }
  public String getLbxProductID() {
    return this.lbxProductID;
  }
  public void setLbxProductID(String lbxProductID) {
    this.lbxProductID = lbxProductID;
  }
  public String getSchemeId() {
    return this.schemeId;
  }
  public void setSchemeId(String schemeId) {
    this.schemeId = schemeId;
  }
  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
  public String getRemarks() {
    return this.remarks;
  }
  public void setDecision(String decision) {
    this.decision = decision;
  }
  public String getDecision() {
    return this.decision;
  }
  public String getBranchName() {
    return this.branchName;
  }
  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }
  public String getBranchHead() {
    return this.branchHead;
  }
  public void setBranchHead(String branchHead) {
    this.branchHead = branchHead;
  }
  public String getBranchCode() {
    return this.branchCode;
  }
  public void setBranchCode(String branchCode) {
    this.branchCode = branchCode;
  }
  public String getLeadId() {
    return this.leadId;
  }
  public void setLbxRegionID(String lbxRegionID) {
    this.lbxRegionID = lbxRegionID;
  }
  public String getLbxRegionID() {
    return this.lbxRegionID;
  }
  public String getContactnorm() {
    return this.contactnorm;
  }
  public void setContactnorm(String contactnorm) {
    this.contactnorm = contactnorm;
  }
  public String getLeadgenzonerm() {
    return this.leadgenzonerm;
  }
  public void setLeadgenzonerm(String leadgenzonerm) {
    this.leadgenzonerm = leadgenzonerm;
  }
  public String getLeadgencityrm() {
    return this.leadgencityrm;
  }
  public void setLeadgencityrm(String leadgencityrm) {
    this.leadgencityrm = leadgencityrm;
  }
  public String getContactnovendor() {
    return this.contactnovendor;
  }
  public void setContactnovendor(String contactnovendor) {
    this.contactnovendor = contactnovendor;
  }
  public String getLeadgenzonevendor() {
    return this.leadgenzonevendor;
  }
  public void setLeadgenzonevendor(String leadgenzonevendor) {
    this.leadgenzonevendor = leadgenzonevendor;
  }
  public String getLeadgencityvendor() {
    return this.leadgencityvendor;
  }
  public void setLeadgencityvendor(String leadgencityvendor) {
    this.leadgencityvendor = leadgencityvendor;
  }
  public String getContactnobranch() {
    return this.contactnobranch;
  }
  public void setContactnobranch(String contactnobranch) {
    this.contactnobranch = contactnobranch;
  }
  public String getLeadgenzonebranch() {
    return this.leadgenzonebranch;
  }
  public void setLeadgenzonebranch(String leadgenzonebranch) {
    this.leadgenzonebranch = leadgenzonebranch;
  }
  public String getLeadgencitybranch() {
    return this.leadgencitybranch;
  }
  public void setLeadgencitybranch(String leadgencitybranch) {
    this.leadgencitybranch = leadgencitybranch;
  }
  public String getRmName1() {
    return this.rmName1;
  }
  public void setRmName1(String rmName1) {
    this.rmName1 = rmName1;
  }
  public String getRmCode1() {
    return this.rmCode1;
  }
  public void setRmCode1(String rmCode1) {
    this.rmCode1 = rmCode1;
  }
  public String getRmHead1() {
    return this.rmHead1;
  }
  public void setRmHead1(String rmHead1) {
    this.rmHead1 = rmHead1;
  }
  public String getVendorName1() {
    return this.vendorName1;
  }
  public void setVendorName1(String vendorName1) {
    this.vendorName1 = vendorName1;
  }
  public String getVendorCode1() {
    return this.vendorCode1;
  }
  public void setVendorCode1(String vendorCode1) {
    this.vendorCode1 = vendorCode1;
  }
  public String getVendorHead1() {
    return this.vendorHead1;
  }
  public void setVendorHead1(String vendorHead1) {
    this.vendorHead1 = vendorHead1;
  }
  public String getBranchHead1() {
    return this.branchHead1;
  }
  public void setBranchHead1(String branchHead1) {
    this.branchHead1 = branchHead1;
  }
  public String getDefaultcountryid() {
    return this.defaultcountryid;
  }
  public void setDefaultcountryid(String defaultcountryid) {
    this.defaultcountryid = defaultcountryid;
  }
  public String getDefaultcountryname() {
    return this.defaultcountryname;
  }
  public void setDefaultcountryname(String defaultcountryname) {
    this.defaultcountryname = defaultcountryname;
  }
  public void setLbxbranchHead1(String lbxbranchHead1) {
    this.lbxbranchHead1 = lbxbranchHead1;
  }
  public String getLbxbranchHead1() {
    return this.lbxbranchHead1;
  }
  public void setSalary(String salary) {
    this.salary = salary;
  }
  public String getSalary() {
    return this.salary;
  }
  public void setLeadGeneratorSelect(String leadGeneratorSelect) {
    this.leadGeneratorSelect = leadGeneratorSelect;
  }
  public String getLeadGeneratorSelect() {
    return this.leadGeneratorSelect;
  }
  public String getLbxBranchIdOther() {
    return this.lbxBranchIdOther;
  }
  public void setLbxBranchIdOther(String lbxBranchIdOther) {
    this.lbxBranchIdOther = lbxBranchIdOther;
  }
  public String getBranchHead1Other() {
    return this.branchHead1Other;
  }
  public void setBranchHead1Other(String branchHead1Other) {
    this.branchHead1Other = branchHead1Other;
  }
  public String getContactnobranchOther() {
    return this.contactnobranchOther;
  }
  public void setContactnobranchOther(String contactnobranchOther) {
    this.contactnobranchOther = contactnobranchOther;
  }
  public String getLeadgencitybranchOther() {
    return this.leadgencitybranchOther;
  }
  public void setLeadgencitybranchOther(String leadgencitybranchOther) {
    this.leadgencitybranchOther = leadgencitybranchOther;
  }
  public void setBranchName1Other(String branchName1Other) {
    this.branchName1Other = branchName1Other;
  }
  public String getBranchName1Other() {
    return this.branchName1Other;
  }
  public String getSourceListHidden() {
    return this.sourceListHidden;
  }
  public void setSourceListHidden(String sourceListHidden) {
    this.sourceListHidden = sourceListHidden;
  }
  public void setEscalationFlag(String escalationFlag) {
    this.escalationFlag = escalationFlag;
  }
  public String getEscalationFlag() {
    return this.escalationFlag;
  }
  public void setCustPanInd(String custPanInd) {
    this.custPanInd = custPanInd;
  }
  public String getCustPanInd() {
    return this.custPanInd;
  }
  public void setBranchCode1Other(String branchCode1Other) {
    this.branchCode1Other = branchCode1Other;
  }
  public String getBranchCode1Other() {
    return this.branchCode1Other;
  }
  public void setReasonId(String reasonId) {
    this.reasonId = reasonId;
  }
  public String getReasonId() {
    return this.reasonId;
  }
  public void setSectorType(String sectorType) {
    this.sectorType = sectorType;
  }
  public String getSectorType() {
    return this.sectorType;
  }
  public void setSource(String source) {
    this.source = source;
  }
  public String getSource() {
    return this.source;
  }
  public String getDateOfIncorporation() {
    return this.dateOfIncorporation;
  }
  public void setDateOfIncorporation(String dateOfIncorporation) {
    this.dateOfIncorporation = dateOfIncorporation;
  }
  public String getUidNo() {
    return this.uidNo;
  }
  public void setUidNo(String uidNo) {
    this.uidNo = uidNo;
  }
  public String getVatNo() {
    return this.vatNo;
  }
  public void setVatNo(String vatNo) {
    this.vatNo = vatNo;
  }
  public String getTinNo() {
    return this.tinNo;
  }
  public void setTinNo(String tinNo) {
    this.tinNo = tinNo;
  }
  public String getRefNo() {
    return this.refNo;
  }
  public void setRefNo(String refNo) {
    this.refNo = refNo;
  }
  public String getCustomerId() {
    return this.customerId;
  }
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }
  public String getTxnType() {
    return this.txnType;
  }
  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }
  public String getAddString() {
    return this.addString;
  }
  public void setAddString(String addString) {
    this.addString = addString;
  }
  public String getPartner() {
    return this.partner;
  }
  public void setPartner(String partner) {
    this.partner = partner;
  }
  public String getDealNo() {
    return this.dealNo;
  }
  public void setDealNo(String dealNo) {
    this.dealNo = dealNo;
  }
  public String getDealStatus() {
    return this.dealStatus;
  }
  public void setDealStatus(String dealStatus) {
    this.dealStatus = dealStatus;
  }
  public String getRule() {
    return this.rule;
  }
  public void setRule(String rule) {
    this.rule = rule;
  }
  public String getDedupeRemarks() {
    return this.dedupeRemarks;
  }
  public void setDedupeRemarks(String dedupeRemarks) {
    this.dedupeRemarks = dedupeRemarks;
  }
  public String getRuleWeightage() {
    return this.ruleWeightage;
  }
  public void setRuleWeightage(String ruleWeightage) {
    this.ruleWeightage = ruleWeightage;
  }
  public int getDynamicWeightage() {
    return this.dynamicWeightage;
  }
  public void setDynamicWeightage(int dynamicWeightage) {
    this.dynamicWeightage = dynamicWeightage;
  }
  public String getTxnTahsilHID() {
    return this.txnTahsilHID;
  }
  public void setTxnTahsilHID(String txnTahsilHID) {
    this.txnTahsilHID = txnTahsilHID;
  }
  public String getMiddleName() {
    return this.middleName;
  }
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }
  public String getLbxCustomerId() {
    return this.lbxCustomerId;
  }
  public void setLbxCustomerId(String lbxCustomerId) {
    this.lbxCustomerId = lbxCustomerId;
  }
  public int getLbxOtherDetails() {
    return this.lbxOtherDetails;
  }
  public void setLbxOtherDetails(int lbxOtherDetails) {
    this.lbxOtherDetails = lbxOtherDetails;
  }
  public String getLbxother() {
    return this.lbxother;
  }
  public void setLbxother(String lbxother) {
    this.lbxother = lbxother;
  }
  public String getAssetdescOther() {
    return this.assetdescOther;
  }
  public void setAssetdescOther(String assetdescOther) {
    this.assetdescOther = assetdescOther;
  }
  public String getDupDealId() {
    return this.dupDealId;
  }
  public void setDupDealId(String dupDealId) {
    this.dupDealId = dupDealId;
  }
  	public String getGenderIndividual() {
		return genderIndividual;
	}
	public void setGenderIndividual(String genderIndividual) {
		this.genderIndividual = genderIndividual;
	}
	public String getAadhaar() {
		return aadhaar;
	}
	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}
	public String getGaurcorconstitution() {
		return gaurcorconstitution;
	}
	public void setGaurcorconstitution(String gaurcorconstitution) {
		this.gaurcorconstitution = gaurcorconstitution;
	}
	public String getGaurindconstitution() {
		return gaurindconstitution;
	}
	public void setGaurindconstitution(String gaurindconstitution) {
		this.gaurindconstitution = gaurindconstitution;
	}
	public String getGaurcustDOB() {
		return gaurcustDOB;
	}
	public void setGaurcustDOB(String gaurcustDOB) {
		this.gaurcustDOB = gaurcustDOB;
	}
	public String getGaurhGroupId() {
		return gaurhGroupId;
	}
	public void setGaurhGroupId(String gaurhGroupId) {
		this.gaurhGroupId = gaurhGroupId;
	}
	public String getGaurgroupName() {
		return gaurgroupName;
	}
	public void setGaurgroupName(String gaurgroupName) {
		this.gaurgroupName = gaurgroupName;
	}
	public String getGaurgroupName1() {
		return gaurgroupName1;
	}
	public void setGaurgroupName1(String gaurgroupName1) {
		this.gaurgroupName1 = gaurgroupName1;
	}
	public String getGaurconstitution() {
		return gaurconstitution;
	}
	public void setGaurconstitution(String gaurconstitution) {
		this.gaurconstitution = gaurconstitution;
	}
	public String getGaurregistrationNo() {
		return gaurregistrationNo;
	}
	public void setGaurregistrationNo(String gaurregistrationNo) {
		this.gaurregistrationNo = gaurregistrationNo;
	}
	public String getGaurcustPan() {
		return gaurcustPan;
	}
	public void setGaurcustPan(String gaurcustPan) {
		this.gaurcustPan = gaurcustPan;
	}
	public String getGaurbusinessSegment() {
		return gaurbusinessSegment;
	}
	public void setGaurbusinessSegment(String gaurbusinessSegment) {
		this.gaurbusinessSegment = gaurbusinessSegment;
	}
	public String getGaurfirstName() {
		return gaurfirstName;
	}
	public void setGaurfirstName(String gaurfirstName) {
		this.gaurfirstName = gaurfirstName;
	}
	public String getGaurlastName() {
		return gaurlastName;
	}
	public void setGaurlastName(String gaurlastName) {
		this.gaurlastName = gaurlastName;
	}
	public String getGaurincorporationDate() {
		return gaurincorporationDate;
	}
	public void setGaurincorporationDate(String gaurincorporationDate) {
		this.gaurincorporationDate = gaurincorporationDate;
	}
	public String getGaurrelationship() {
		return gaurrelationship;
	}
	public void setGaurrelationship(String gaurrelationship) {
		this.gaurrelationship = gaurrelationship;
	}
	public String getGaurcustomerName() {
		return gaurcustomerName;
	}
	public void setGaurcustomerName(String gaurcustomerName) {
		this.gaurcustomerName = gaurcustomerName;
	}
	public String getGauraddress1() {
		return gauraddress1;
	}
	public void setGauraddress1(String gauraddress1) {
		this.gauraddress1 = gauraddress1;
	}
	public String getGauraddress2() {
		return gauraddress2;
	}
	public void setGauraddress2(String gauraddress2) {
		this.gauraddress2 = gauraddress2;
	}
	public String getGauraddress3() {
		return gauraddress3;
	}
	public void setGauraddress3(String gauraddress3) {
		this.gauraddress3 = gauraddress3;
	}
	public String getGaurcountry() {
		return gaurcountry;
	}
	public void setGaurcountry(String gaurcountry) {
		this.gaurcountry = gaurcountry;
	}
	public String getGaurstate() {
		return gaurstate;
	}
	public void setGaurstate(String gaurstate) {
		this.gaurstate = gaurstate;
	}
	public String getGaurdist() {
		return gaurdist;
	}
	public void setGaurdist(String gaurdist) {
		this.gaurdist = gaurdist;
	}
	public String getGaurpincode() {
		return gaurpincode;
	}
	public void setGaurpincode(String gaurpincode) {
		this.gaurpincode = gaurpincode;
	}
	public String getGaurphoneOff() {
		return gaurphoneOff;
	}
	public void setGaurphoneOff(String gaurphoneOff) {
		this.gaurphoneOff = gaurphoneOff;
	}
	public String getGaurphoneRes() {
		return gaurphoneRes;
	}
	public void setGaurphoneRes(String gaurphoneRes) {
		this.gaurphoneRes = gaurphoneRes;
	}
	public String getGaurmobile() {
		return gaurmobile;
	}
	public void setGaurmobile(String gaurmobile) {
		this.gaurmobile = gaurmobile;
	}
	public String getGauremail() {
		return gauremail;
	}
	public void setGauremail(String gauremail) {
		this.gauremail = gauremail;
	}
	public String getGaurcustomerType() {
		return gaurcustomerType;
	}
	public void setGaurcustomerType(String gaurcustomerType) {
		this.gaurcustomerType = gaurcustomerType;
	}
	public String getGaurgroupType() {
		return gaurgroupType;
	}
	public void setGaurgroupType(String gaurgroupType) {
		this.gaurgroupType = gaurgroupType;
	}
	public String getGaurtxtCountryCode() {
		return gaurtxtCountryCode;
	}
	public void setGaurtxtCountryCode(String gaurtxtCountryCode) {
		this.gaurtxtCountryCode = gaurtxtCountryCode;
	}
	public String getGaurtxtStateCode() {
		return gaurtxtStateCode;
	}
	public void setGaurtxtStateCode(String gaurtxtStateCode) {
		this.gaurtxtStateCode = gaurtxtStateCode;
	}
	public String getGaurtxtDistCode() {
		return gaurtxtDistCode;
	}
	public void setGaurtxtDistCode(String gaurtxtDistCode) {
		this.gaurtxtDistCode = gaurtxtDistCode;
	}
	public String getGaurlandmark() {
		return gaurlandmark;
	}
	public void setGaurlandmark(String gaurlandmark) {
		this.gaurlandmark = gaurlandmark;
	}
	public String getGaurhIndustry() {
		return gaurhIndustry;
	}
	public void setGaurhIndustry(String gaurhIndustry) {
		this.gaurhIndustry = gaurhIndustry;
	}
	public String getGaurlbxSubIndustry() {
		return gaurlbxSubIndustry;
	}
	public void setGaurlbxSubIndustry(String gaurlbxSubIndustry) {
		this.gaurlbxSubIndustry = gaurlbxSubIndustry;
	}
	public String getGaursubIndustry() {
		return gaursubIndustry;
	}
	public void setGaursubIndustry(String gaursubIndustry) {
		this.gaursubIndustry = gaursubIndustry;
	}
	public String getGaurindustry() {
		return gaurindustry;
	}
	public void setGaurindustry(String gaurindustry) {
		this.gaurindustry = gaurindustry;
	}
	public String getGaurlbxIndustry() {
		return gaurlbxIndustry;
	}
	public void setGaurlbxIndustry(String gaurlbxIndustry) {
		this.gaurlbxIndustry = gaurlbxIndustry;
	}
	public String getGaurfatherName() {
		return gaurfatherName;
	}
	public void setGaurfatherName(String gaurfatherName) {
		this.gaurfatherName = gaurfatherName;
	}
	public String getGaurpassport() {
		return gaurpassport;
	}
	public void setGaurpassport(String gaurpassport) {
		this.gaurpassport = gaurpassport;
	}
	public String getGaurdlNumber() {
		return gaurdlNumber;
	}
	public void setGaurdlNumber(String gaurdlNumber) {
		this.gaurdlNumber = gaurdlNumber;
	}
	public String getGaurvoterId() {
		return gaurvoterId;
	}
	public void setGaurvoterId(String gaurvoterId) {
		this.gaurvoterId = gaurvoterId;
	}
	public String getGaurtahsil() {
		return gaurtahsil;
	}
	public void setGaurtahsil(String gaurtahsil) {
		this.gaurtahsil = gaurtahsil;
	}
	public String getGauraddresstype() {
		return gauraddresstype;
	}
	public void setGauraddresstype(String gauraddresstype) {
		this.gauraddresstype = gauraddresstype;
	}
	public String getGaurturnover() {
		return gaurturnover;
	}
	public void setGaurturnover(String gaurturnover) {
		this.gaurturnover = gaurturnover;
	}
	public String getGaurcustPanInd() {
		return gaurcustPanInd;
	}
	public void setGaurcustPanInd(String gaurcustPanInd) {
		this.gaurcustPanInd = gaurcustPanInd;
	}
	public String getCoAppcorconstitution() {
		return coAppcorconstitution;
	}
	public void setCoAppcorconstitution(String coAppcorconstitution) {
		this.coAppcorconstitution = coAppcorconstitution;
	}
	public String getCoAppindconstitution() {
		return coAppindconstitution;
	}
	public void setCoAppindconstitution(String coAppindconstitution) {
		this.coAppindconstitution = coAppindconstitution;
	}
	public String getCoAppcustDOB() {
		return coAppcustDOB;
	}
	public void setCoAppcustDOB(String coAppcustDOB) {
		this.coAppcustDOB = coAppcustDOB;
	}
	public String getCoApphGroupId() {
		return coApphGroupId;
	}
	public void setCoApphGroupId(String coApphGroupId) {
		this.coApphGroupId = coApphGroupId;
	}
	public String getCoAppgroupName() {
		return coAppgroupName;
	}
	public void setCoAppgroupName(String coAppgroupName) {
		this.coAppgroupName = coAppgroupName;
	}
	public String getCoAppgroupName1() {
		return coAppgroupName1;
	}
	public void setCoAppgroupName1(String coAppgroupName1) {
		this.coAppgroupName1 = coAppgroupName1;
	}
	public String getCoAppconstitution() {
		return coAppconstitution;
	}
	public void setCoAppconstitution(String coAppconstitution) {
		this.coAppconstitution = coAppconstitution;
	}
	public String getCoAppregistrationNo() {
		return coAppregistrationNo;
	}
	public void setCoAppregistrationNo(String coAppregistrationNo) {
		this.coAppregistrationNo = coAppregistrationNo;
	}
	public String getCoAppcustPan() {
		return coAppcustPan;
	}
	public void setCoAppcustPan(String coAppcustPan) {
		this.coAppcustPan = coAppcustPan;
	}
	public String getCoAppbusinessSegment() {
		return coAppbusinessSegment;
	}
	public void setCoAppbusinessSegment(String coAppbusinessSegment) {
		this.coAppbusinessSegment = coAppbusinessSegment;
	}
	public String getCoAppfirstName() {
		return coAppfirstName;
	}
	public void setCoAppfirstName(String coAppfirstName) {
		this.coAppfirstName = coAppfirstName;
	}
	public String getCoApplastName() {
		return coApplastName;
	}
	public void setCoApplastName(String coApplastName) {
		this.coApplastName = coApplastName;
	}
	public String getCoAppincorporationDate() {
		return coAppincorporationDate;
	}
	public void setCoAppincorporationDate(String coAppincorporationDate) {
		this.coAppincorporationDate = coAppincorporationDate;
	}
	public String getCoApprelationship() {
		return coApprelationship;
	}
	public void setCoApprelationship(String coApprelationship) {
		this.coApprelationship = coApprelationship;
	}
	public String getCoAppcustomerName() {
		return coAppcustomerName;
	}
	public void setCoAppcustomerName(String coAppcustomerName) {
		this.coAppcustomerName = coAppcustomerName;
	}
	public String getCoAppaddress1() {
		return coAppaddress1;
	}
	public void setCoAppaddress1(String coAppaddress1) {
		this.coAppaddress1 = coAppaddress1;
	}
	public String getCoAppaddress2() {
		return coAppaddress2;
	}
	public void setCoAppaddress2(String coAppaddress2) {
		this.coAppaddress2 = coAppaddress2;
	}
	public String getCoAppaddress3() {
		return coAppaddress3;
	}
	public void setCoAppaddress3(String coAppaddress3) {
		this.coAppaddress3 = coAppaddress3;
	}
	public String getCoAppcountry() {
		return coAppcountry;
	}
	public void setCoAppcountry(String coAppcountry) {
		this.coAppcountry = coAppcountry;
	}
	public String getCoAppstate() {
		return coAppstate;
	}
	public void setCoAppstate(String coAppstate) {
		this.coAppstate = coAppstate;
	}
	public String getCoAppdist() {
		return coAppdist;
	}
	public void setCoAppdist(String coAppdist) {
		this.coAppdist = coAppdist;
	}
	public String getCoApppincode() {
		return coApppincode;
	}
	public void setCoApppincode(String coApppincode) {
		this.coApppincode = coApppincode;
	}
	public String getCoAppphoneOff() {
		return coAppphoneOff;
	}
	public void setCoAppphoneOff(String coAppphoneOff) {
		this.coAppphoneOff = coAppphoneOff;
	}
	public String getCoAppphoneRes() {
		return coAppphoneRes;
	}
	public void setCoAppphoneRes(String coAppphoneRes) {
		this.coAppphoneRes = coAppphoneRes;
	}
	public String getCoAppmobile() {
		return coAppmobile;
	}
	public void setCoAppmobile(String coAppmobile) {
		this.coAppmobile = coAppmobile;
	}
	public String getCoAppemail() {
		return coAppemail;
	}
	public void setCoAppemail(String coAppemail) {
		this.coAppemail = coAppemail;
	}
	public String getCoAppcustomerType() {
		return coAppcustomerType;
	}
	public void setCoAppcustomerType(String coAppcustomerType) {
		this.coAppcustomerType = coAppcustomerType;
	}
	public String getCoAppgroupType() {
		return coAppgroupType;
	}
	public void setCoAppgroupType(String coAppgroupType) {
		this.coAppgroupType = coAppgroupType;
	}
	public String getCoApptxtCountryCode() {
		return coApptxtCountryCode;
	}
	public void setCoApptxtCountryCode(String coApptxtCountryCode) {
		this.coApptxtCountryCode = coApptxtCountryCode;
	}
	public String getCoApptxtStateCode() {
		return coApptxtStateCode;
	}
	public void setCoApptxtStateCode(String coApptxtStateCode) {
		this.coApptxtStateCode = coApptxtStateCode;
	}
	public String getCoApptxtDistCode() {
		return coApptxtDistCode;
	}
	public void setCoApptxtDistCode(String coApptxtDistCode) {
		this.coApptxtDistCode = coApptxtDistCode;
	}
	public String getCoApplandmark() {
		return coApplandmark;
	}
	public void setCoApplandmark(String coApplandmark) {
		this.coApplandmark = coApplandmark;
	}
	public String getCoApphIndustry() {
		return coApphIndustry;
	}
	public void setCoApphIndustry(String coApphIndustry) {
		this.coApphIndustry = coApphIndustry;
	}
	public String getCoApplbxSubIndustry() {
		return coApplbxSubIndustry;
	}
	public void setCoApplbxSubIndustry(String coApplbxSubIndustry) {
		this.coApplbxSubIndustry = coApplbxSubIndustry;
	}
	public String getCoAppsubIndustry() {
		return coAppsubIndustry;
	}
	public void setCoAppsubIndustry(String coAppsubIndustry) {
		this.coAppsubIndustry = coAppsubIndustry;
	}
	public String getCoAppindustry() {
		return coAppindustry;
	}
	public void setCoAppindustry(String coAppindustry) {
		this.coAppindustry = coAppindustry;
	}
	public String getCoApplbxIndustry() {
		return coApplbxIndustry;
	}
	public void setCoApplbxIndustry(String coApplbxIndustry) {
		this.coApplbxIndustry = coApplbxIndustry;
	}
	public String getCoAppfatherName() {
		return coAppfatherName;
	}
	public void setCoAppfatherName(String coAppfatherName) {
		this.coAppfatherName = coAppfatherName;
	}
	public String getCoApppassport() {
		return coApppassport;
	}
	public void setCoApppassport(String coApppassport) {
		this.coApppassport = coApppassport;
	}
	public String getCoAppdlNumber() {
		return coAppdlNumber;
	}
	public void setCoAppdlNumber(String coAppdlNumber) {
		this.coAppdlNumber = coAppdlNumber;
	}
	public String getCoAppvoterId() {
		return coAppvoterId;
	}
	public void setCoAppvoterId(String coAppvoterId) {
		this.coAppvoterId = coAppvoterId;
	}
	public String getCoApptahsil() {
		return coApptahsil;
	}
	public void setCoApptahsil(String coApptahsil) {
		this.coApptahsil = coApptahsil;
	}
	public String getCoAppaddresstype() {
		return coAppaddresstype;
	}
	public void setCoAppaddresstype(String coAppaddresstype) {
		this.coAppaddresstype = coAppaddresstype;
	}
	public String getCoAppturnover() {
		return coAppturnover;
	}
	public void setCoAppturnover(String coAppturnover) {
		this.coAppturnover = coAppturnover;
	}
	public String getCoAppcustPanInd() {
		return coAppcustPanInd;
	}
	public void setCoAppcustPanInd(String coAppcustPanInd) {
		this.coAppcustPanInd = coAppcustPanInd;
	}
	public String getGauraadhaar() {
		return gauraadhaar;
	}
	public void setGauraadhaar(String gauraadhaar) {
		this.gauraadhaar = gauraadhaar;
	}
	public String getGaurgenderIndividual() {
		return gaurgenderIndividual;
	}
	public void setGaurgenderIndividual(String gaurgenderIndividual) {
		this.gaurgenderIndividual = gaurgenderIndividual;
	}
	public String getCoAppaadhaar() {
		return coAppaadhaar;
	}
	public void setCoAppaadhaar(String coAppaadhaar) {
		this.coAppaadhaar = coAppaadhaar;
	}
	public String getCoAppgenderIndividual() {
		return coAppgenderIndividual;
	}
	public void setCoAppgenderIndividual(String coAppgenderIndividual) {
		this.coAppgenderIndividual = coAppgenderIndividual;
	}
	public String getCoAppcontitutionCode() {
		return coAppcontitutionCode;
	}
	public void setCoAppcontitutionCode(String coAppcontitutionCode) {
		this.coAppcontitutionCode = coAppcontitutionCode;
	}
	public String getCoApptahsilDesc() {
		return coApptahsilDesc;
	}
	public void setCoApptahsilDesc(String coApptahsilDesc) {
		this.coApptahsilDesc = coApptahsilDesc;
	}
	public String getCoAppgroupDesc() {
		return coAppgroupDesc;
	}
	public void setCoAppgroupDesc(String coAppgroupDesc) {
		this.coAppgroupDesc = coAppgroupDesc;
	}
	public String getGaurcontitutionCode() {
		return gaurcontitutionCode;
	}
	public void setGaurcontitutionCode(String gaurcontitutionCode) {
		this.gaurcontitutionCode = gaurcontitutionCode;
	}
	public String getGaurgroupDesc() {
		return gaurgroupDesc;
	}
	public void setGaurgroupDesc(String gaurgroupDesc) {
		this.gaurgroupDesc = gaurgroupDesc;
	}
	public String getGaurtahsilDesc() {
		return gaurtahsilDesc;
	}
	public void setGaurtahsilDesc(String gaurtahsilDesc) {
		this.gaurtahsilDesc = gaurtahsilDesc;
	}
	public String getGaurhgroupId() {
		return gaurhgroupId;
	}
	public void setGaurhgroupId(String gaurhgroupId) {
		this.gaurhgroupId = gaurhgroupId;
	}
	public String getCoApplbxCustomerId() {
		return coApplbxCustomerId;
	}
	public void setCoApplbxCustomerId(String coApplbxCustomerId) {
		this.coApplbxCustomerId = coApplbxCustomerId;
	}
	public String getGaurlbxCustomerId() {
		return gaurlbxCustomerId;
	}
	public void setGaurlbxCustomerId(String gaurlbxCustomerId) {
		this.gaurlbxCustomerId = gaurlbxCustomerId;
	}
	public String getCoAppStatus1() {
		return coAppStatus1;
	}
	public void setCoAppStatus1(String coAppStatus1) {
		this.coAppStatus1 = coAppStatus1;
	}
	public String getGaurStatus1() {
		return gaurStatus1;
	}
	public void setGaurStatus1(String gaurStatus1) {
		this.gaurStatus1 = gaurStatus1;
	}
	 
    public String getCoApptxnTahsilHID() {
		return coApptxnTahsilHID;
	}
	public void setCoApptxnTahsilHID(String coApptxnTahsilHID) {
		this.coApptxnTahsilHID = coApptxnTahsilHID;
	}
	public String getGaurtxnTahsilHID() {
		return gaurtxnTahsilHID;
	}
	public void setGaurtxnTahsilHID(String gaurtxnTahsilHID) {
		this.gaurtxnTahsilHID = gaurtxnTahsilHID;
	}
	//pooja changes starts for middle name and application form no
		public String getGaurmiddleName() {
			return gaurmiddleName;
		}
		public void setGaurmiddleName(String gaurmiddleName) {
			this.gaurmiddleName = gaurmiddleName;
		}
		public String getCoAppmiddleName() {
			return coAppmiddleName;
		}
		public void setCoAppmiddleName(String coAppmiddleName) {
			this.coAppmiddleName = coAppmiddleName;
		}
		public String getApplicationFormNoRm() {
			return applicationFormNoRm;
		}
		public void setApplicationFormNoRm(String applicationFormNoRm) {
			this.applicationFormNoRm = applicationFormNoRm;
		}
		public String getApplicationFormNoVendor() {
			return applicationFormNoVendor;
		}
		public void setApplicationFormNoVendor(String applicationFormNoVendor) {
			this.applicationFormNoVendor = applicationFormNoVendor;
		}
		public String getApplicationFormNoBranch() {
			return applicationFormNoBranch;
		}
		public void setApplicationFormNoBranch(String applicationFormNoBranch) {
			this.applicationFormNoBranch = applicationFormNoBranch;
		}
		public String getApplicationFormNoOther() {
			return applicationFormNoOther;
		}
		public void setApplicationFormNoOther(String applicationFormNoOther) {
			this.applicationFormNoOther = applicationFormNoOther;
		}
		//pooja changes starts for middle name and application form no
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
public String getCustType() {
	return custType;
}
public void setCustType(String custType) {
	this.custType = custType;
}
public String getCoAppcustCode() {
	return coAppcustCode;
}
public void setCoAppcustCode(String coAppcustCode) {
	this.coAppcustCode = coAppcustCode;
}
public String getGaurcustCode() {
	return gaurcustCode;
}
public void setGaurcustCode(String gaurcustCode) {
	this.gaurcustCode = gaurcustCode;
}
public String getCustId() {
	return custId;
}
public void setCustId(String custId) {
	this.custId = custId;
}
public String getUpdateCustId() {
	return updateCustId;
}
public void setUpdateCustId(String updateCustId) {
	this.updateCustId = updateCustId;
}
public String getRelationshipManager() {
	return relationshipManager;
}
public void setRelationshipManager(String relationshipManager) {
	this.relationshipManager = relationshipManager;
}
public String getLbxRelationship() {
	return lbxRelationship;
}
public void setLbxRelationship(String lbxRelationship) {
	this.lbxRelationship = lbxRelationship;
}
public String getGeneratedUser() {
	return generatedUser;
}
public void setGeneratedUser(String generatedUser) {
	this.generatedUser = generatedUser;
}
public String getLbxUserSearchId() {
	return lbxUserSearchId;
}
public void setLbxUserSearchId(String lbxUserSearchId) {
	this.lbxUserSearchId = lbxUserSearchId;
}
  
}