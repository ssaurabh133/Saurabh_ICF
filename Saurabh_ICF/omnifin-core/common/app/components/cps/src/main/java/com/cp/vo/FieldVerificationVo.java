package com.cp.vo;

import org.apache.struts.upload.FormFile;

public class FieldVerificationVo {

	private String internalAppraiser;
	private String[] type;
	private String[] appraiser;
	private String externalAppraiser;
	private String lbxUseId;
	private String lbxetApprHID;
	private String dealId;
	private String dealNo;
	private String lbxDealNo;
	private String dealLink;
	private String customerName;
	private String applicationDate;
	private String product;
	private String lbxProductID;
	private String scheme;
	private String lbxscheme;
	private String checkbutton;
	private String customer_id;
	private String status;
	private String modfyDealNo;
	private String addType;
	private String addrType;
	private String dealDate;
	private String rmName;
	private String lbxUserId;
	private String lbxextApprHID;
	private String addressType;
	private String addressValue;
	private String appType;
	private String userName;
	private String companyId;
	private String reportingToUserId;
	private String verificationMappingId;
	private String verificationType;
	private String contactVerificationSubType;
	private String tecVerificationSubType;
	private String tradeVerificationSubType;
	private String incomeVerificationSubType;
	private String verificationSubType;
	
	private String entityType;
	private String entitySubType;

	private String appraiserType;
	private String verificationStatus;
	private String personToMeet;
	private String varificationType;
	private String relationWithApplicant;
	private String phone1;
	private String phone2;
	private String email;	
	private String officeAddress;
	private String officeFacilities;
	private String bussinessBoardScene;
	private String workEnvironment;
	private String bussinessActivity;
	private String officeInteriors;
	private String officeExteriors;
	private String cpvStatus;
	private String remarks;
	private String initiationDate;
	private String varificationSubType;
	private String varificationSubTypeTrade;
	private String varificationSubTypeCollateral;
	private String branchId;
	private int currentPageLink;
	private int totalRecordSize;
	private String makerId;
	private String makerDate;
	private String userId;
	private String actionValue;
	private String varId;
	private String[] externalAppraiserArr ;
	private String[] internalAppraiserArr ;
	private String decison;
	private String textarea;
	private String[] entityId;
	private String entityDesc;
	private String[] verificationAction;
	
	private String entId;
	
	private String[] verType ;
	private String[] verSubType ;
	private String[] entType ;
	private String[] entSubType ;
	private String[] entityDescription;
	private String[] externalAppUserId;
	private String[] internalAppUserId;
	private String questId;
	
	private String quest;
	private String[] questionId ;
	private String[] questionRemarks ;
	private String[] verificationMethod;
	
	private String[] questionSeqNo;
	private String questSeqNo;
	private String fieldVerificationUniqueId;
	private String questRemarks;
	private String questVerifMethod;
	private String questVerifUniqueId;
	private String[] questionVerificationUniqueId;
	private String addressTypeDesc;
	private String[] lbxAddressType;
	private String agencyName;
	private String verifQuestRequired;
	private String txnType;
	private String docDescription;
	private FormFile docFile;
	private String fileName;
	private String docPath;
	private String  verificationCapId;
	
	private String loanNo;
	private String  lbxLoanNoHID;
	private String entitySubTypeDesc;
	private String assetLocation;
	private String assetCondition;
	private String invoiceCollected;
	private String invoiceNumber;
	
	private String [] appraiserRemarksArray;
	private String [] appraiserDateArray;
	private String appraiserRemarks;
	private String appraiserDate;
	
	private String [] verfDecisionArray;
	private String verfDecision;
	private String[] hidRcuStatusString;
	private String[] hidRcuCommentString;
	private String[] hidDOCIDString;
	private String[] hidChildIDString;
	private String docEntityType;
	private String docStage;
	private String parentDocName;
	private String childDocName;
	private String RCUStatus;
	private String childDocID;
	private String parentDocID;
	private String RCURemarks;
	private String id;
	private String name;
	private String functionId;
	private String entityAddress;
	private String managementPhone;
	private String marketValue;
	private String govtValue;
	private String distressValue;
	private String referenceId;
	private String appraisalName;
	private String verificationMode;
	private String appraisalDate; 
	
	public String getEntityAddress() {
		return entityAddress;
	}
	public void setEntityAddress(String entityAddress) {
		this.entityAddress = entityAddress;
	}
	public String getManagementPhone() {
		return managementPhone;
	}
	public void setManagementPhone(String managementPhone) {
		this.managementPhone = managementPhone;
	}
	public String getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}
	public String getGovtValue() {
		return govtValue;
	}
	public void setGovtValue(String govtValue) {
		this.govtValue = govtValue;
	}
	public String getDistressValue() {
		return distressValue;
	}
	public void setDistressValue(String distressValue) {
		this.distressValue = distressValue;
	}
	public String[] getHidRcuStatusString() {
		return hidRcuStatusString;
	}
	public void setHidRcuStatusString(String[] hidRcuStatusString) {
		this.hidRcuStatusString = hidRcuStatusString;
	}
	public String[] getHidRcuCommentString() {
		return hidRcuCommentString;
	}
	public void setHidRcuCommentString(String[] hidRcuCommentString) {
		this.hidRcuCommentString = hidRcuCommentString;
	}
	public String[] getHidDOCIDString() {
		return hidDOCIDString;
	}
	public void setHidDOCIDString(String[] hidDOCIDString) {
		this.hidDOCIDString = hidDOCIDString;
	}
	public String[] getHidChildIDString() {
		return hidChildIDString;
	}
	public void setHidChildIDString(String[] hidChildIDString) {
		this.hidChildIDString = hidChildIDString;
	}
	public String getDocEntityType() {
		return docEntityType;
	}
	public void setDocEntityType(String docEntityType) {
		this.docEntityType = docEntityType;
	}
	public String getDocStage() {
		return docStage;
	}
	public void setDocStage(String docStage) {
		this.docStage = docStage;
	}
	public String getParentDocName() {
		return parentDocName;
	}
	public void setParentDocName(String parentDocName) {
		this.parentDocName = parentDocName;
	}
	public String getChildDocName() {
		return childDocName;
	}
	public void setChildDocName(String childDocName) {
		this.childDocName = childDocName;
	}
	public String getRCUStatus() {
		return RCUStatus;
	}
	public void setRCUStatus(String rCUStatus) {
		RCUStatus = rCUStatus;
	}
	public String getChildDocID() {
		return childDocID;
	}
	public void setChildDocID(String childDocID) {
		this.childDocID = childDocID;
	}
	public String getParentDocID() {
		return parentDocID;
	}
	public void setParentDocID(String parentDocID) {
		this.parentDocID = parentDocID;
	}
	public String getRCURemarks() {
		return RCURemarks;
	}
	public void setRCURemarks(String rCURemarks) {
		RCURemarks = rCURemarks;
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
	

	
	
	
	public String[] getVerfDecisionArray() {
		return verfDecisionArray;
	}
	public void setVerfDecisionArray(String[] verfDecisionArray) {
		this.verfDecisionArray = verfDecisionArray;
	}
	public String getVerfDecision() {
		return verfDecision;
	}
	public void setVerfDecision(String verfDecision) {
		this.verfDecision = verfDecision;
	}
	public String[] getAppraiserRemarksArray() {
		return appraiserRemarksArray;
	}
	public void setAppraiserRemarksArray(String[] appraiserRemarksArray) {
		this.appraiserRemarksArray = appraiserRemarksArray;
	}
	public String[] getAppraiserDateArray() {
		return appraiserDateArray;
	}
	public void setAppraiserDateArray(String[] appraiserDateArray) {
		this.appraiserDateArray = appraiserDateArray;
	}
	public String getAppraiserRemarks() {
		return appraiserRemarks;
	}
	public void setAppraiserRemarks(String appraiserRemarks) {
		this.appraiserRemarks = appraiserRemarks;
	}
	public String getAppraiserDate() {
		return appraiserDate;
	}
	public void setAppraiserDate(String appraiserDate) {
		this.appraiserDate = appraiserDate;
	}
	public String getAssetLocation() {
		return assetLocation;
	}
	public void setAssetLocation(String assetLocation) {
		this.assetLocation = assetLocation;
	}
	public String getAssetCondition() {
		return assetCondition;
	}
	public void setAssetCondition(String assetCondition) {
		this.assetCondition = assetCondition;
	}
	public String getInvoiceCollected() {
		return invoiceCollected;
	}
	public void setInvoiceCollected(String invoiceCollected) {
		this.invoiceCollected = invoiceCollected;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
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
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getDocDescription() {
		return docDescription;
	}
	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}
	
	public String getVerifQuestRequired() {
		return verifQuestRequired;
	}
	public void setVerifQuestRequired(String verifQuestRequired) {
		this.verifQuestRequired = verifQuestRequired;
	}
	public String[] getLbxAddressType() {
		return lbxAddressType;
	}
	public void setLbxAddressType(String[] lbxAddressType) {
		this.lbxAddressType = lbxAddressType;
	}
	public String getAddressTypeDesc() {
		return addressTypeDesc;
	}
	public void setAddressTypeDesc(String addressTypeDesc) {
		this.addressTypeDesc = addressTypeDesc;
	}
	public String getQuestVerifUniqueId() {
		return questVerifUniqueId;
	}
	public void setQuestVerifUniqueId(String questVerifUniqueId) {
		this.questVerifUniqueId = questVerifUniqueId;
	}
	public String[] getQuestionVerificationUniqueId() {
		return questionVerificationUniqueId;
	}
	public void setQuestionVerificationUniqueId(
			String[] questionVerificationUniqueId) {
		this.questionVerificationUniqueId = questionVerificationUniqueId;
	}
	public String getQuestRemarks() {
		return questRemarks;
	}
	public void setQuestRemarks(String questRemarks) {
		this.questRemarks = questRemarks;
	}
	public String getQuestVerifMethod() {
		return questVerifMethod;
	}
	public void setQuestVerifMethod(String questVerifMethod) {
		this.questVerifMethod = questVerifMethod;
	}
	public String getFieldVerificationUniqueId() {
		return fieldVerificationUniqueId;
	}
	public void setFieldVerificationUniqueId(String fieldVerificationUniqueId) {
		this.fieldVerificationUniqueId = fieldVerificationUniqueId;
	}
	public String getQuestSeqNo() {
		return questSeqNo;
	}
	public void setQuestSeqNo(String questSeqNo) {
		this.questSeqNo = questSeqNo;
	}
	public String[] getQuestionSeqNo() {
		return questionSeqNo;
	}
	public void setQuestionSeqNo(String[] questionSeqNo) {
		this.questionSeqNo = questionSeqNo;
	}
	public String[] getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String[] questionId) {
		this.questionId = questionId;
	}
	public String[] getQuestionRemarks() {
		return questionRemarks;
	}
	public void setQuestionRemarks(String[] questionRemarks) {
		this.questionRemarks = questionRemarks;
	}
	public String[] getVerificationMethod() {
		return verificationMethod;
	}
	public void setVerificationMethod(String[] verificationMethod) {
		this.verificationMethod = verificationMethod;
	}
	public String getCpvStatus() {
		return cpvStatus;
	}
	public void setCpvStatus(String cpvStatus) {
		this.cpvStatus = cpvStatus;
	}
	public String getQuest() {
		return quest;
	}
	public void setQuest(String quest) {
		this.quest = quest;
	}
	public String getQuestId() {
		return questId;
	}
	public void setQuestId(String questId) {
		this.questId = questId;
	}
	public String[] getExternalAppUserId() {
		return externalAppUserId;
	}
	public void setExternalAppUserId(String[] externalAppUserId) {
		this.externalAppUserId = externalAppUserId;
	}
	public String[] getInternalAppUserId() {
		return internalAppUserId;
	}
	public void setInternalAppUserId(String[] internalAppUserId) {
		this.internalAppUserId = internalAppUserId;
	}
	public String[] getEntityDescription() {
		return entityDescription;
	}
	public void setEntityDescription(String[] entityDescription) {
		this.entityDescription = entityDescription;
	}
	public String[] getAppraiser() {
		return appraiser;
	}
	public void setAppraiser(String[] appraiser) {
		this.appraiser = appraiser;
	}
	public String[] getVerType() {
		return verType;
	}
	public void setVerType(String[] verType) {
		this.verType = verType;
	}
	public String[] getVerSubType() {
		return verSubType;
	}
	public void setVerSubType(String[] verSubType) {
		this.verSubType = verSubType;
	}
	public String[] getEntType() {
		return entType;
	}
	public void setEntType(String[] entType) {
		this.entType = entType;
	}
	public String[] getEntSubType() {
		return entSubType;
	}
	public void setEntSubType(String[] entSubType) {
		this.entSubType = entSubType;
	}
	public String[] getEntityId() {
		return entityId;
	}
	public void setEntityId(String[] entityId) {
		this.entityId = entityId;
	}
	public String getEntId() {
		return entId;
	}
	public void setEntId(String entId) {
		this.entId = entId;
	}
	public String getEntityDesc() {
		return entityDesc;
	}
	public void setEntityDesc(String entityDesc) {
		this.entityDesc = entityDesc;
	}
	public String getActionValue() {
		return actionValue;
	}
	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}

	
	public String[] getVerificationAction() {
		return verificationAction;
	}
	public void setVerificationAction(String[] verificationAction) {
		this.verificationAction = verificationAction;
	}
	public String getVerificationMappingId() {
		return verificationMappingId;
	}
	public void setVerificationMappingId(String verificationMappingId) {
		this.verificationMappingId = verificationMappingId;
	}
	public String getVerificationType() {
		return verificationType;
	}
	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}
	public String getContactVerificationSubType() {
		return contactVerificationSubType;
	}
	public void setContactVerificationSubType(String contactVerificationSubType) {
		this.contactVerificationSubType = contactVerificationSubType;
	}
	public String getTecVerificationSubType() {
		return tecVerificationSubType;
	}
	public void setTecVerificationSubType(String tecVerificationSubType) {
		this.tecVerificationSubType = tecVerificationSubType;
	}
	public String getTradeVerificationSubType() {
		return tradeVerificationSubType;
	}
	public void setTradeVerificationSubType(String tradeVerificationSubType) {
		this.tradeVerificationSubType = tradeVerificationSubType;
	}
	public String getIncomeVerificationSubType() {
		return incomeVerificationSubType;
	}
	public void setIncomeVerificationSubType(String incomeVerificationSubType) {
		this.incomeVerificationSubType = incomeVerificationSubType;
	}
	public String getVerificationSubType() {
		return verificationSubType;
	}
	public void setVerificationSubType(String verificationSubType) {
		this.verificationSubType = verificationSubType;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntitySubType() {
		return entitySubType;
	}
	public void setEntitySubType(String entitySubType) {
		this.entitySubType = entitySubType;
	}
	public String getAppraiserType() {
		return appraiserType;
	}
	public void setAppraiserType(String appraiserType) {
		this.appraiserType = appraiserType;
	}
	public String getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public String getReportingToUserId() {
		return reportingToUserId;
	}
	public void setReportingToUserId(String reportingToUserId) {
		this.reportingToUserId = reportingToUserId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	private String applicantType;
    public String getAddressValue() {
		return addressValue;
	}
	public void setAddressValue(String addressValue) {
		this.addressValue = addressValue;
	}
	private String verificationId;
	public String getVerificationId() {
		return verificationId;
	}
	public void setVerificationId(String verificationId) {
		this.verificationId = verificationId;
	}
	
	
	public String[] getExternalAppraiserArr() {
		return externalAppraiserArr;
	}
	public void setExternalAppraiserArr(String[] externalAppraiserArr) {
		this.externalAppraiserArr = externalAppraiserArr;
	}
	public String[] getInternalAppraiserArr() {
		return internalAppraiserArr;
	}
	public void setInternalAppraiserArr(String[] internalAppraiserArr) {
		this.internalAppraiserArr = internalAppraiserArr;
	}


	public String getAppraisalName() {
		return appraisalName;
	}

	public void setAppraisalName(String appraisalName) {
		this.appraisalName = appraisalName;
	}

	public String getVerificationMode() {
		return verificationMode;
	}

	public void setVerificationMode(String verificationMode) {
		this.verificationMode = verificationMode;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getAppraisalDate() {
		return appraisalDate;
	}

	public void setAppraisalDate(String appraisalDate) {
		this.appraisalDate = appraisalDate;
	}

	public String getInternalAppraiser() {
		return internalAppraiser;
	}

	public void setInternalAppraiser(String internalAppraiser) {
		this.internalAppraiser = internalAppraiser;
	}

	public String[] getType() {
		return type;
	}

	public void setType(String[] type) {
		this.type = type;
	}



	public String getExternalAppraiser() {
		return externalAppraiser;
	}

	public void setExternalAppraiser(String externalAppraiser) {
		this.externalAppraiser = externalAppraiser;
	}

	public String getLbxUseId() {
		return lbxUseId;
	}

	public void setLbxUseId(String lbxUseId) {
		this.lbxUseId = lbxUseId;
	}

	public String getLbxetApprHID() {
		return lbxetApprHID;
	}

	public void setLbxetApprHID(String lbxetApprHID) {
		this.lbxetApprHID = lbxetApprHID;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	public String getLbxDealNo() {
		return lbxDealNo;
	}

	public void setLbxDealNo(String lbxDealNo) {
		this.lbxDealNo = lbxDealNo;
	}

	public String getDealLink() {
		return dealLink;
	}

	public void setDealLink(String dealLink) {
		this.dealLink = dealLink;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getLbxProductID() {
		return lbxProductID;
	}

	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getLbxscheme() {
		return lbxscheme;
	}

	public void setLbxscheme(String lbxscheme) {
		this.lbxscheme = lbxscheme;
	}

	public String getCheckbutton() {
		return checkbutton;
	}

	public void setCheckbutton(String checkbutton) {
		this.checkbutton = checkbutton;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customerId) {
		customer_id = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModfyDealNo() {
		return modfyDealNo;
	}

	public void setModfyDealNo(String modfyDealNo) {
		this.modfyDealNo = modfyDealNo;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public String getAddrType() {
		return addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public String getLbxUserId() {
		return lbxUserId;
	}

	public void setLbxUserId(String lbxUserId) {
		this.lbxUserId = lbxUserId;
	}

	public String getLbxextApprHID() {
		return lbxextApprHID;
	}

	public void setLbxextApprHID(String lbxextApprHID) {
		this.lbxextApprHID = lbxextApprHID;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
		
	public String getPersonToMeet() {
		return personToMeet;
	}

	public void setPersonToMeet(String personToMeet) {
		this.personToMeet = personToMeet;
	}

	public String getVarificationType() {
		return varificationType;
	}

	public void setVarificationType(String varificationType) {
		this.varificationType = varificationType;
	}

	public String getRelationWithApplicant() {
		return relationWithApplicant;
	}

	public void setRelationWithApplicant(String relationWithApplicant) {
		this.relationWithApplicant = relationWithApplicant;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getOfficeFacilities() {
		return officeFacilities;
	}

	public void setOfficeFacilities(String officeFacilities) {
		this.officeFacilities = officeFacilities;
	}

	public String getBussinessBoardScene() {
		return bussinessBoardScene;
	}

	public void setBussinessBoardScene(String bussinessBoardScene) {
		this.bussinessBoardScene = bussinessBoardScene;
	}

	public String getWorkEnvironment() {
		return workEnvironment;
	}

	public void setWorkEnvironment(String workEnvironment) {
		this.workEnvironment = workEnvironment;
	}

	public String getBussinessActivity() {
		return bussinessActivity;
	}

	public void setBussinessActivity(String bussinessActivity) {
		this.bussinessActivity = bussinessActivity;
	}

	public String getOfficeInteriors() {
		return officeInteriors;
	}

	public void setOfficeInteriors(String officeInteriors) {
		this.officeInteriors = officeInteriors;
	}

	public String getOfficeExteriors() {
		return officeExteriors;
	}

	public void setOfficeExteriors(String officeExteriors) {
		this.officeExteriors = officeExteriors;
	}

	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInitiationDate() {
		return initiationDate;
	}

	public void setInitiationDate(String initiationDate) {
		this.initiationDate = initiationDate;
	}

	public String getVarificationSubType() {
		return varificationSubType;
	}

	public void setVarificationSubType(String varificationSubType) {
		this.varificationSubType = varificationSubType;
	}

	public String getVarificationSubTypeTrade() {
		return varificationSubTypeTrade;
	}

	public void setVarificationSubTypeTrade(String varificationSubTypeTrade) {
		this.varificationSubTypeTrade = varificationSubTypeTrade;
	}

	public String getVarificationSubTypeCollateral() {
		return varificationSubTypeCollateral;
	}

	public void setVarificationSubTypeCollateral(
			String varificationSubTypeCollateral) {
		this.varificationSubTypeCollateral = varificationSubTypeCollateral;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

		
	public void setVarId(String varId) {
		this.varId = varId;
	}
	public String getVarId() {
		return varId;
	}
	
	public void setDecison(String decison) {
		this.decison = decison;
	}
	public String getDecison() {
		return decison;
	}
	public void setTextarea(String textarea) {
		this.textarea = textarea;
	}
	public String getTextarea() {
		return textarea;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setVerificationCapId(String verificationCapId) {
		this.verificationCapId = verificationCapId;
	}
	public String getVerificationCapId() {
		return verificationCapId;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLbxLoanNoHID(String lbxLoanNoHID) {
		this.lbxLoanNoHID = lbxLoanNoHID;
	}
	public String getLbxLoanNoHID() {
		return lbxLoanNoHID;
	}
	public void setEntitySubTypeDesc(String entitySubTypeDesc) {
		this.entitySubTypeDesc = entitySubTypeDesc;
	}
	public String getEntitySubTypeDesc() {
		return entitySubTypeDesc;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	
	
}
