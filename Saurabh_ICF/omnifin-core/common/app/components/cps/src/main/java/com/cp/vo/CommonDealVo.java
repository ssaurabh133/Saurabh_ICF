package com.cp.vo;

import java.io.Serializable;

public class CommonDealVo
  implements Serializable
{
  private String dealNo;
  private String deal;
  private String lbxDealNo;
  private String applicationno;
  private String applicationdate;
  private String initiationDate;
  private String customername;
  private String productcategory;
  private String product;
  private String lbxProductID;
  private String scheme;
  private String lbxscheme;
  private String reportingToUserId;
  private String stage;
  private String branchId;
  private int currentPageLink;
  private int totalRecordSize;
  private String recStatus;
  private String loanAmount;
  private String tenure;
  private String fundFlowDealStatus;
  private String financialDealStatus;
  private String businessdate;
  private String userId;
  private String lbxUserId;
  private String userName;
  private String allBranches;
  private String dealStatus;
  private String lbxBranchId;
  private String selectiveBranch;
  private String docTypeId;
  private String docTypeDesc;
  private String functionId;
  private String leadno;
  private String lbxLeadId;
  private String custName;
  private String entityCustomerName;
  private String lbxCustomerType;
  private String entityCustomerType;
  private String lbxCustomerRoleType;
  private String customerId;
  private String financialyear;
  private String financialyearDesc;
  private String financialyearCode;
  private String docEntity;
  private String otherRemarks;
  private String lbxRemarksID;
  private String entityCustomerId;
  private String camPreparationMode;
  private String camPreparationModeCode;
  private String camPreparationModeDesc;
  private String makerId;
  private String rate;
  private String city;
  private String loanAmt;
  private String interest;
  private String lessee;
  private String applicationNo;
  private String propertyType;
  private String bpType;
  private String customerType;
  private String level;
  private String branchDesc;
  private String approvalBy;
  private String approvalDecision;
  private String approvalRemark;
  private String decision;
  private String chk;
  private String remarks;
  private String companyId;
  private String scoringParamName;
  private String applicationValue;
  private String scoringWeightage;
  private String score;
  private String weightage;
  private String obligationPram;
  private String totalDrPram;
  private String totalCrPram;
  private String qualityCheckStatus;
  private String sanctionValidTill;
  private String sourceType;
  private String status;

  public String getScoringParamName()
  {
    return this.scoringParamName;
  }

  public String getSanctionValidTill() {
    return this.sanctionValidTill;
  }

  public void setSanctionValidTill(String sanctionValidTill) {
    this.sanctionValidTill = sanctionValidTill;
  }

  public String getObligationPram() {
    return this.obligationPram;
  }

  public void setObligationPram(String obligationPram) {
    this.obligationPram = obligationPram;
  }

  public String getTotalDrPram() {
    return this.totalDrPram;
  }

  public void setTotalDrPram(String totalDrPram) {
    this.totalDrPram = totalDrPram;
  }

  public String getTotalCrPram() {
    return this.totalCrPram;
  }

  public void setTotalCrPram(String totalCrPram) {
    this.totalCrPram = totalCrPram;
  }

  public void setScoringParamName(String scoringParamName) {
    this.scoringParamName = scoringParamName;
  }

  public String getApplicationValue() {
    return this.applicationValue;
  }

  public void setApplicationValue(String applicationValue) {
    this.applicationValue = applicationValue;
  }

  public String getScoringWeightage()
  {
    return this.scoringWeightage;
  }

  public void setScoringWeightage(String scoringWeightage) {
    this.scoringWeightage = scoringWeightage;
  }

  public String getWeightage() {
    return this.weightage;
  }

  public void setWeightage(String weightage) {
    this.weightage = weightage;
  }

  public String getScore() {
    return this.score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getBranchDesc()
  {
    return this.branchDesc;
  }

  public void setBranchDesc(String branchDesc) {
    this.branchDesc = branchDesc;
  }

  public String getApprovalBy() {
    return this.approvalBy;
  }

  public void setApprovalBy(String approvalBy) {
    this.approvalBy = approvalBy;
  }

  public String getApprovalDecision() {
    return this.approvalDecision;
  }

  public void setApprovalDecision(String approvalDecision) {
    this.approvalDecision = approvalDecision;
  }

  public String getApprovalRemark() {
    return this.approvalRemark;
  }

  public void setApprovalRemark(String approvalRemark) {
    this.approvalRemark = approvalRemark;
  }

  public String getLevel() {
    return this.level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getCustomerType()
  {
    return this.customerType;
  }

  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }

  public String getReportingToUserId() {
    return this.reportingToUserId;
  }

  public void setReportingToUserId(String reportingToUserId) {
    this.reportingToUserId = reportingToUserId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getLbxUserId() {
    return this.lbxUserId;
  }

  public void setLbxUserId(String lbxUserId) {
    this.lbxUserId = lbxUserId;
  }

  public String getDeal() {
    return this.deal;
  }

  public void setDeal(String deal) {
    this.deal = deal;
  }

  public String getBusinessdate() {
    return this.businessdate;
  }

  public void setBusinessdate(String businessdate) {
    this.businessdate = businessdate;
  }

  public String getSourceType()
  {
    return this.sourceType;
  }
  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }
  public String getFinancialDealStatus() {
    return this.financialDealStatus;
  }
  public void setFinancialDealStatus(String financialDealStatus) {
    this.financialDealStatus = financialDealStatus;
  }
  public String getFundFlowDealStatus() {
    return this.fundFlowDealStatus;
  }
  public void setFundFlowDealStatus(String fundFlowDealStatus) {
    this.fundFlowDealStatus = fundFlowDealStatus;
  }
  public String getLoanAmount() {
    return this.loanAmount;
  }
  public void setLoanAmount(String loanAmount) {
    this.loanAmount = loanAmount;
  }
  public String getTenure() {
    return this.tenure;
  }
  public void setTenure(String tenure) {
    this.tenure = tenure;
  }
  public String getRecStatus() {
    return this.recStatus;
  }
  public void setRecStatus(String recStatus) {
    this.recStatus = recStatus;
  }
  public int getCurrentPageLink() {
    return this.currentPageLink;
  }
  public void setCurrentPageLink(int currentPageLink) {
    this.currentPageLink = currentPageLink;
  }
  public int getTotalRecordSize() {
    return this.totalRecordSize;
  }
  public void setTotalRecordSize(int totalRecordSize) {
    this.totalRecordSize = totalRecordSize;
  }
  public String getBranchId() {
    return this.branchId;
  }
  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }
  public String getDealNo() {
    return this.dealNo;
  }
  public void setDealNo(String dealNo) {
    this.dealNo = dealNo;
  }
  public String getLbxDealNo() {
    return this.lbxDealNo;
  }
  public void setLbxDealNo(String lbxDealNo) {
    this.lbxDealNo = lbxDealNo;
  }
  public String getApplicationno() {
    return this.applicationno;
  }
  public void setApplicationno(String applicationno) {
    this.applicationno = applicationno;
  }

  public String getCustomername() {
    return this.customername;
  }
  public void setCustomername(String customername) {
    this.customername = customername;
  }
  public String getProductcategory() {
    return this.productcategory;
  }
  public void setProductcategory(String productcategory) {
    this.productcategory = productcategory;
  }
  public String getProduct() {
    return this.product;
  }
  public void setProduct(String product) {
    this.product = product;
  }
  public String getLbxProductID() {
    return this.lbxProductID;
  }
  public void setLbxProductID(String lbxProductID) {
    this.lbxProductID = lbxProductID;
  }
  public String getScheme() {
    return this.scheme;
  }
  public void setScheme(String scheme) {
    this.scheme = scheme;
  }
  public String getLbxscheme() {
    return this.lbxscheme;
  }
  public void setLbxscheme(String lbxscheme) {
    this.lbxscheme = lbxscheme;
  }

  public String getStage() {
    return this.stage;
  }
  public void setStage(String stage) {
    this.stage = stage;
  }
  public void setInitiationDate(String initiationDate) {
    this.initiationDate = initiationDate;
  }
  public String getInitiationDate() {
    return this.initiationDate;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setAllBranches(String allBranches) {
    this.allBranches = allBranches;
  }

  public String getAllBranches() {
    return this.allBranches;
  }

  public void setDecision(String decision)
  {
    this.decision = decision;
  }
  public String getDecision() {
    return this.decision;
  }

  public void setChk(String chk) {
    this.chk = chk;
  }
  public String getChk() {
    return this.chk;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
  public String getRemarks() {
    return this.remarks;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }
  public String getCompanyId() {
    return this.companyId;
  }

  public void setQualityCheckStatus(String qualityCheckStatus)
  {
    this.qualityCheckStatus = qualityCheckStatus;
  }

  public String getQualityCheckStatus() {
    return this.qualityCheckStatus;
  }

  public String getDealStatus()
  {
    return this.dealStatus;
  }

  public void setDealStatus(String dealStatus) {
    this.dealStatus = dealStatus;
  }

  public String getLbxBranchId() {
    return this.lbxBranchId;
  }

  public void setLbxBranchId(String lbxBranchId) {
    this.lbxBranchId = lbxBranchId;
  }

  public String getSelectiveBranch() {
    return this.selectiveBranch;
  }

  public void setSelectiveBranch(String selectiveBranch) {
    this.selectiveBranch = selectiveBranch;
  }

  public String getDocTypeId() {
    return this.docTypeId;
  }

  public void setDocTypeId(String docTypeId) {
    this.docTypeId = docTypeId;
  }

  public String getDocTypeDesc() {
    return this.docTypeDesc;
  }

  public void setDocTypeDesc(String docTypeDesc) {
    this.docTypeDesc = docTypeDesc;
  }

  public String getMakerId() {
    return this.makerId;
  }

  public void setMakerId(String makerId) {
    this.makerId = makerId;
  }

  public String getRate() {
    return this.rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getLoanAmt() {
    return this.loanAmt;
  }

  public String getFinancialyear()
  {
    return this.financialyear;
  }

  public void setFinancialyear(String financialyear) {
    this.financialyear = financialyear;
  }

  public String getFinancialyearDesc() {
    return this.financialyearDesc;
  }

  public void setFinancialyearDesc(String financialyearDesc) {
    this.financialyearDesc = financialyearDesc;
  }

  public String getFinancialyearCode() {
    return this.financialyearCode;
  }

  public void setFinancialyearCode(String financialyearCode) {
    this.financialyearCode = financialyearCode;
  }

  public String getBpType()
  {
    return this.bpType;
  }
  public void setBpType(String bpType) {
    this.bpType = bpType;
  }

  public void setLoanAmt(String loanAmt) {
    this.loanAmt = loanAmt;
  }
  public String getCity() {
    return this.city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getInterest() {
    return this.interest;
  }
  public void setInterest(String interest) {
    this.interest = interest;
  }
  public String getApplicationdate() {
    return this.applicationdate;
  }
  public void setApplicationdate(String applicationdate) {
    this.applicationdate = applicationdate;
  }
  public String getPropertyType() {
    return this.propertyType;
  }
  public void setPropertyType(String propertyType) {
    this.propertyType = propertyType;
  }
  public String getLessee() {
    return this.lessee;
  }
  public void setLessee(String lessee) {
    this.lessee = lessee;
  }
  public String getApplicationNo() {
    return this.applicationNo;
  }
  public void setApplicationNo(String applicationNo) {
    this.applicationNo = applicationNo;
  }
  public String getDocEntity() {
    return this.docEntity;
  }
  public void setDocEntity(String docEntity) {
    this.docEntity = docEntity;
  }
  public String getOtherRemarks() {
    return this.otherRemarks;
  }
  public void setOtherRemarks(String otherRemarks) {
    this.otherRemarks = otherRemarks;
  }
  public String getLbxRemarksID() {
    return this.lbxRemarksID;
  }
  public void setLbxRemarksID(String lbxRemarksID) {
    this.lbxRemarksID = lbxRemarksID;
  }
  public String getEntityCustomerId() {
    return this.entityCustomerId;
  }
  public void setEntityCustomerId(String entityCustomerId) {
    this.entityCustomerId = entityCustomerId;
  }
  public String getCamPreparationModeCode() {
    return this.camPreparationModeCode;
  }
  public void setCamPreparationModeCode(String camPreparationModeCode) {
    this.camPreparationModeCode = camPreparationModeCode;
  }
  public String getCamPreparationModeDesc() {
    return this.camPreparationModeDesc;
  }
  public void setCamPreparationModeDesc(String camPreparationModeDesc) {
    this.camPreparationModeDesc = camPreparationModeDesc;
  }
  public String getCamPreparationMode() {
    return this.camPreparationMode;
  }
  public void setCamPreparationMode(String camPreparationMode) {
    this.camPreparationMode = camPreparationMode;
  }

  public String getEntityCustomerName()
  {
    return this.entityCustomerName;
  }

  public void setEntityCustomerName(String entityCustomerName) {
    this.entityCustomerName = entityCustomerName;
  }

  public String getLbxCustomerType() {
    return this.lbxCustomerType;
  }

  public void setLbxCustomerType(String lbxCustomerType) {
    this.lbxCustomerType = lbxCustomerType;
  }

  public String getLbxCustomerRoleType() {
    return this.lbxCustomerRoleType;
  }

  public void setLbxCustomerRoleType(String lbxCustomerRoleType) {
    this.lbxCustomerRoleType = lbxCustomerRoleType;
  }

  public String getEntityCustomerType() {
    return this.entityCustomerType;
  }

  public void setEntityCustomerType(String entityCustomerType) {
    this.entityCustomerType = entityCustomerType;
  }

  public String getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getFunctionId() {
    return this.functionId;
  }

  public void setFunctionId(String functionId) {
    this.functionId = functionId;
  }

  public String getLbxLeadId()
  {
    return this.lbxLeadId;
  }

  public void setLbxLeadId(String lbxLeadId) {
    this.lbxLeadId = lbxLeadId;
  }

  public String getCustName() {
    return this.custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
  }

  public String getLeadno() {
    return this.leadno;
  }

  public void setLeadno(String leadno) {
    this.leadno = leadno;
  }
  public String getStatus() {
	return status;
  }

  public void setStatus(String status) {
	this.status = status;
  }

}