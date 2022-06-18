package com.cp.vo;

import org.apache.struts.upload.FormFile;

public class FinancialAnalysisVo {
	private String id;
	private String dealNo;
	private String lbxDealNo;
	private String financialYear;
	private String sourceType;
	private String parameCode;
	private String negativeParamCode;
	private String negativeActive;
	private String paramName;
	private String paramValue;
	private String userId;
	private String businessDate;
	private String lbxparameCode;
	private String currBusinessDateYear;
	private String ratioExpr;
	private String financialId;
	private String[] financialIds;
	private String[] pCode;
	private String[] pName;
	private String[] year1;
	private String[] year2;
	private String[] year3;
	private String[] year4;
	private String[] year5;
	
	private String paramCodes;
	private String firstYear;
	private String secondYear;
	private String thirdYear;
	private String fourthYear;
	private String fifthYear;
	private String[] concernsAndChecksArrId;
	private String update;
	
	private String ratioFirstYear;
	private String ratioSecondYear;
	private String ratioThirdYear;
	private String ratioFourthYear;
	private String ratioFifthYear;
	private String ratioParamCode;
	
	private String[] month;
	private String[] year;
	
	private String monthValue;
	private String yearValue;
	private String ratioName;
	
	/*code for income new added field*/
	private String customerType;
	private String customerName;
	private String monthlyIncome;
	private String annuallyIncome;
	private String varificationMethod;
	private String remarks;
	private String emi;
	private String subType;
	private String subTypeDesc;
	private String autoCalculated;
	private String financialFormula;
	private String negativeAllow;
	private FormFile docFile;
	private String toBeConsidered;
	private String applicantIncome;
	private String applicantObligation;
	private String coAapplicantIncome;
	private String coApplicantObligation;
	private String loanRoi;
	private String constitution;
	private String customerTypeList;
	private String constName;
	private String constCode;
	private String customerConstitutionList;

	//Abhishek Start
	private String[] yearRemarks; // add by Abhishek 
	private String custId;
	/*private String caseId;
	private String entityCustomerId;
	private String custId;
	private String docEntity;
	private String targetSheetName;
	private String programScheme;
	private String queriesRequirementsId;
	private String customerTypeDesc;
	private String queryRequrementCode;
	private String queryRequrementValue;
	private String lbxDocDesc;
	private String[] queryRequrementCodes;	
	private String[] queryRequrementValues;	
	private String[] queryRequrementRemarks;
	private String concernsAndChecksId;
	private String concernsCheckCode;
	private String concernsValue;
	private String[] concernsCheckId;
	private String[] concernsCheckValue;
	private String[] concernsCheckRemarks;
	private String code;
	private String value;
	private String uploadPath;
	private String uploadType;
	private String mcaUploadId;
	private String fileName;
	private int totalRecordSize;
	private String[] customerNameArr;
	private String id;
	private String[] dobArr;
	private String[] ageArr;
	private String[] ageAtMaturityArr;
	private String[] incomeTakenArr;
	private String[] relationArr;
	private String[] propOwnerArr;
	private String[] CIBILcodesArr;
	private String[] addressArr;
	private String[] contactNoReceiptArr;
	private String dob;
	private String age;
	private String ageAtMaturity;
	private String incomeTaken;
	private String relation;
	private String propOwner;
	private String CIBILcodes;
	private String address;
	private String contactNoReceipt;
	private String shareholdersName; 
	private String directors;
	private String onLoanStructure;
	private String noOfShares;
	private String stakeInAgePer;
	private String[] shareholdersNameArr; 
	private String[] directorsArr;
	private String[] onLoanStructureArr;
	private String[] noOfSharesArr;
	private String[] pName;*/
	private String updateFlag;
	//Abhishek End

	private String bpType;
	public String getConstName() {
		return constName;
	}
	public void setConstName(String constName) {
		this.constName = constName;
	}
	public String getConstCode() {
		return constCode;
	}
	public void setConstCode(String constCode) {
		this.constCode = constCode;
	}
	public String getCustomerConstitutionList() {
		return customerConstitutionList;
	}
	public void setCustomerConstitutionList(String customerConstitutionList) {
		this.customerConstitutionList = customerConstitutionList;
	}
	
	
//	start by sachin add an column in ratio analysis
	private String benchBranchRatio;
	private String[] analysisYear;
	private String entityCustomerName;
	private String lbxCustomerType; 
	private String lbxCustomerRoleType;
	 private String entityCustomerType;
	 private String customerId;
	 private String chk[];
	 private String chkValue;
	 private String includeInRatio;
	private String caseId;
	
	private String queriesRequirementsId;
	private String applicationFormKyc ;
	private String banking ;
	private String cibil ;
	private String collateral ;
	private String financialsNotesSchedules ;
	private String loanSheetRepaymentSchedules; 
	private String loanStructure ;
	private String sanctionLetter ;
	private String taxReturns	;
	private String customerTypeDesc;
	private String concernsAndChecksId;
	private String eligibility;
	
	private String applicationFormKycRemarks ;
	private String bankingRemarks ;
	private String cibilRemarks ;
	private String collateralRemarks ;
	private String financialsNotesSchedulesRemarks ;
	private String loanSheetRepaymentSchedulesRemarks; 
	private String loanStructureRemarks ;
	private String sanctionLetterRemarks ;
	private String taxReturnsRemarks	;
	private String customerTypeDescRemarks;
	private String eligibilityRemarks;
	private String docEntity;
	private String entityCustomerId;
	private String targetSheetName;
	private String lbxConcernsParentId;
	private String lbxConcernsId;
	private String[] concernsCheckRemarks;
	private String[] concernsCheckId;	
	private String[] concernsCheckValue;	
	private String concernsCheckCode;
	private String concernsValue;	
	private String code;
	private String value;
	private String[] queryRequrementRemarks;
	private String[] queryRequrementCodes;	
	private String[] queryRequrementValues;	
	private String queryRequrementCode;
	private String queryRequrementValue;	
	private String[] queryRequrementArrId;
	private String lbxDocDesc;
	private String programScheme;
	private String uploadType;
	private String uploadPath;
	private String mcaUploadId;
	private String fileName;
	private int totalRecordSize;
	
	private String dob;
	private String age;
	private String ageAtMaturity;
	private String incomeTaken;
	private String relation;
	private String propOwner;
	private String CIBILcodes;
	private String address;
	private String contactNoReceipt;
	
	private String[] customerNameArr;
	private String[] dobArr;
	private String[] ageArr;
	private String[] ageAtMaturityArr;
	private String[] incomeTakenArr;
	private String[] relationArr;
	private String[] propOwnerArr;
	private String[] CIBILcodesArr;
	private String[] addressArr;
	private String[] contactNoReceiptArr;
	
	private String[] stakeInAgePerArr;
	private String stakeInAgePer;
	private String[] shareholdersNameArr; 
	private String shareholdersName; 
	private String[] directorsArr;
	private String directors;
	private String onLoanStructure;
	private String[] onLoanStructureArr;
	private String noOfShares;;
	private String[] noOfSharesArr;
	public String[] getChk() {
		return chk;
	}
	public void setChk(String[] chk) {
		this.chk = chk;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getAnalysisYear() {
		return analysisYear;
	}
	public void setAnalysisYear(String[] analysisYear) {
		this.analysisYear = analysisYear;
	}
	public String getBenchBranchRatio() {
		return benchBranchRatio;
	}
	public void setBenchBranchRatio(String benchBranchRatio) {
		this.benchBranchRatio = benchBranchRatio;
	}

//end by sachin	
	
	public FormFile getDocFile() {
		return docFile;
	}
	public void setDocFile(FormFile docFile) {
		this.docFile = docFile;
	}
	public String getNegativeAllow() {
		return negativeAllow;
	}
	public void setNegativeAllow(String negativeAllow) {
		this.negativeAllow = negativeAllow;
	}
	public String getEmi() {
		return emi;
	}
	public void setEmi(String emi) {
		this.emi = emi;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public String getAnnuallyIncome() {
		return annuallyIncome;
	}
	public void setAnnuallyIncome(String annuallyIncome) {
		this.annuallyIncome = annuallyIncome;
	}
	public String getVarificationMethod() {
		return varificationMethod;
	}
	public void setVarificationMethod(String varificationMethod) {
		this.varificationMethod = varificationMethod;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/*code for income new added field ends here*/
	public String getRatioName() {
		return ratioName;
	}
	public void setRatioName(String ratioName) {
		this.ratioName = ratioName;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getNegativeActive() {
		return negativeActive;
	}
	public void setNegativeActive(String negativeActive) {
		this.negativeActive = negativeActive;
	}
	public String getNegativeParamCode() {
		return negativeParamCode;
	}
	public void setNegativeParamCode(String negativeParamCode) {
		this.negativeParamCode = negativeParamCode;
	}
	public String getMonthValue() {
		return monthValue;
	}
	public void setMonthValue(String monthValue) {
		this.monthValue = monthValue;
	}
	public String getYearValue() {
		return yearValue;
	}
	public void setYearValue(String yearValue) {
		this.yearValue = yearValue;
	}
	public String[] getMonth() {
		return month;
	}
	public void setMonth(String[] month) {
		this.month = month;
	}
	public String[] getYear() {
		return year;
	}
	public void setYear(String[] year) {
		this.year = year;
	}
	public String getFinancialId() {
		return financialId;
	}
	public void setFinancialId(String financialId) {
		this.financialId = financialId;
	}
	public String[] getFinancialIds() {
		return financialIds;
	}
	public void setFinancialIds(String[] financialIds) {
		this.financialIds = financialIds;
	}
	public String getRatioParamCode() {
		return ratioParamCode;
	}
	public void setRatioParamCode(String ratioParamCode) {
		this.ratioParamCode = ratioParamCode;
	}
	public String getRatioFirstYear() {
		return ratioFirstYear;
	}
	public void setRatioFirstYear(String ratioFirstYear) {
		this.ratioFirstYear = ratioFirstYear;
	}
	public String getRatioSecondYear() {
		return ratioSecondYear;
	}
	public void setRatioSecondYear(String ratioSecondYear) {
		this.ratioSecondYear = ratioSecondYear;
	}
	public String getRatioThirdYear() {
		return ratioThirdYear;
	}
	public void setRatioThirdYear(String ratioThirdYear) {
		this.ratioThirdYear = ratioThirdYear;
	}
	public String getRatioFourthYear() {
		return ratioFourthYear;
	}
	public void setRatioFourthYear(String ratioFourthYear) {
		this.ratioFourthYear = ratioFourthYear;
	}
	public String getRatioFifthYear() {
		return ratioFifthYear;
	}
	public void setRatioFifthYear(String ratioFifthYear) {
		this.ratioFifthYear = ratioFifthYear;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getRatioExpr() {
		return ratioExpr;
	}
	public void setRatioExpr(String ratioExpr) {
		this.ratioExpr = ratioExpr;
	}
	public String getParamCodes() {
		return paramCodes;
	}
	public void setParamCodes(String paramCodes) {
		this.paramCodes = paramCodes;
	}
	public String getFirstYear() {
		return firstYear;
	}
	public void setFirstYear(String firstYear) {
		this.firstYear = firstYear;
	}
	public String getSecondYear() {
		return secondYear;
	}
	public void setSecondYear(String secondYear) {
		this.secondYear = secondYear;
	}
	public String getThirdYear() {
		return thirdYear;
	}
	public void setThirdYear(String thirdYear) {
		this.thirdYear = thirdYear;
	}
	public String getFourthYear() {
		return fourthYear;
	}
	public void setFourthYear(String fourthYear) {
		this.fourthYear = fourthYear;
	}
	public String getFifthYear() {
		return fifthYear;
	}
	public void setFifthYear(String fifthYear) {
		this.fifthYear = fifthYear;
	}
	public String getCurrBusinessDateYear() {
		return currBusinessDateYear;
	}
	public void setCurrBusinessDateYear(String currBusinessDateYear) {
		this.currBusinessDateYear = currBusinessDateYear;
	}
	public String[] getpCode() {
		return pCode;
	}
	public void setpCode(String[] pCode) {
		this.pCode = pCode;
	}
	public String[] getYear1() {
		return year1;
	}
	public void setYear1(String[] year1) {
		this.year1 = year1;
	}
	public String[] getYear2() {
		return year2;
	}
	public void setYear2(String[] year2) {
		this.year2 = year2;
	}
	public String[] getYear3() {
		return year3;
	}
	public void setYear3(String[] year3) {
		this.year3 = year3;
	}
	public String[] getYear4() {
		return year4;
	}
	public void setYear4(String[] year4) {
		this.year4 = year4;
	}
	public String[] getYear5() {
		return year5;
	}
	public void setYear5(String[] year5) {
		this.year5 = year5;
	}
	public String getLbxparameCode() {
		return lbxparameCode;
	}
	public void setLbxparameCode(String lbxparameCode) {
		this.lbxparameCode = lbxparameCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
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
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getParameCode() {
		return parameCode;
	}
	public void setParameCode(String parameCode) {
		this.parameCode = parameCode;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubTypeDesc(String subTypeDesc) {
		this.subTypeDesc = subTypeDesc;
	}
	public String getSubTypeDesc() {
		return subTypeDesc;
	}
	public void setAutoCalculated(String autoCalculated) {
		this.autoCalculated = autoCalculated;
	}
	public String getAutoCalculated() {
		return autoCalculated;
	}
	public void setFinancialFormula(String financialFormula) {
		this.financialFormula = financialFormula;
	}
	public String getFinancialFormula() {
		return financialFormula;
	}
	public String getEntityCustomerName() {
		return entityCustomerName;
	}
	public void setEntityCustomerName(String entityCustomerName) {
		this.entityCustomerName = entityCustomerName;
	}
	public String getLbxCustomerType() {
		return lbxCustomerType;
	}
	public void setLbxCustomerType(String lbxCustomerType) {
		this.lbxCustomerType = lbxCustomerType;
	}
	public String getLbxCustomerRoleType() {
		return lbxCustomerRoleType;
	}
	public void setLbxCustomerRoleType(String lbxCustomerRoleType) {
		this.lbxCustomerRoleType = lbxCustomerRoleType;
	}
	public String getEntityCustomerType() {
		return entityCustomerType;
	}
	public void setEntityCustomerType(String entityCustomerType) {
		this.entityCustomerType = entityCustomerType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getChkValue() {
		return chkValue;
	}
	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	public String getIncludeInRatio() {
		return includeInRatio;
	}
	public void setIncludeInRatio(String includeInRatio) {
		this.includeInRatio = includeInRatio;
	}
	/*public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}*/

	public String[] getYearRemarks() {
		return yearRemarks;
	}
	public void setYearRemarks(String[] yearRemarks) {
		this.yearRemarks = yearRemarks;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	/*public String getEntityCustomerId() {
		return entityCustomerId;
	}
	public void setEntityCustomerId(String entityCustomerId) {
		this.entityCustomerId = entityCustomerId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getDocEntity() {
		return docEntity;
	}
	public void setDocEntity(String docEntity) {
		this.docEntity = docEntity;
	}
	public String getTargetSheetName() {
		return targetSheetName;
	}
	public void setTargetSheetName(String targetSheetName) {
		this.targetSheetName = targetSheetName;
	}
	public String getProgramScheme() {
		return programScheme;
	}
	public void setProgramScheme(String programScheme) {
		this.programScheme = programScheme;
	}
	public String getQueriesRequirementsId() {
		return queriesRequirementsId;
	}
	public void setQueriesRequirementsId(String queriesRequirementsId) {
		this.queriesRequirementsId = queriesRequirementsId;
	}
	public String getCustomerTypeDesc() {
		return customerTypeDesc;
	}
	public void setCustomerTypeDesc(String customerTypeDesc) {
		this.customerTypeDesc = customerTypeDesc;
	}
	public String getQueryRequrementCode() {
		return queryRequrementCode;
	}
	public void setQueryRequrementCode(String queryRequrementCode) {
		this.queryRequrementCode = queryRequrementCode;
	}
	public String getQueryRequrementValue() {
		return queryRequrementValue;
	}
	public void setQueryRequrementValue(String queryRequrementValue) {
		this.queryRequrementValue = queryRequrementValue;
	}
	public String getLbxDocDesc() {
		return lbxDocDesc;
	}
	public void setLbxDocDesc(String lbxDocDesc) {
		this.lbxDocDesc = lbxDocDesc;
	}
	public String[] getQueryRequrementCodes() {
		return queryRequrementCodes;
	}
	public void setQueryRequrementCodes(String[] queryRequrementCodes) {
		this.queryRequrementCodes = queryRequrementCodes;
	}
	public String[] getQueryRequrementValues() {
		return queryRequrementValues;
	}
	public void setQueryRequrementValues(String[] queryRequrementValues) {
		this.queryRequrementValues = queryRequrementValues;
	}
	public String[] getQueryRequrementRemarks() {
		return queryRequrementRemarks;
	}
	public void setQueryRequrementRemarks(String[] queryRequrementRemarks) {
		this.queryRequrementRemarks = queryRequrementRemarks;
	}
	public String getConcernsAndChecksId() {
		return concernsAndChecksId;
	}
	public void setConcernsAndChecksId(String concernsAndChecksId) {
		this.concernsAndChecksId = concernsAndChecksId;
	}
	public String getConcernsCheckCode() {
		return concernsCheckCode;
	}
	public void setConcernsCheckCode(String concernsCheckCode) {
		this.concernsCheckCode = concernsCheckCode;
	}
	public String getConcernsValue() {
		return concernsValue;
	}
	public void setConcernsValue(String concernsValue) {
		this.concernsValue = concernsValue;
	}
	public String[] getConcernsCheckId() {
		return concernsCheckId;
	}
	public void setConcernsCheckId(String[] concernsCheckId) {
		this.concernsCheckId = concernsCheckId;
	}
	public String[] getConcernsCheckValue() {
		return concernsCheckValue;
	}
	public void setConcernsCheckValue(String[] concernsCheckValue) {
		this.concernsCheckValue = concernsCheckValue;
	}
	public String[] getConcernsCheckRemarks() {
		return concernsCheckRemarks;
	}
	public void setConcernsCheckRemarks(String[] concernsCheckRemarks) {
		this.concernsCheckRemarks = concernsCheckRemarks;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getMcaUploadId() {
		return mcaUploadId;
	}
	public void setMcaUploadId(String mcaUploadId) {
		this.mcaUploadId = mcaUploadId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	public String[] getCustomerNameArr() {
		return customerNameArr;
	}
	public void setCustomerNameArr(String[] customerNameArr) {
		this.customerNameArr = customerNameArr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getDobArr() {
		return dobArr;
	}
	public void setDobArr(String[] dobArr) {
		this.dobArr = dobArr;
	}
	public String[] getAgeArr() {
		return ageArr;
	}
	public void setAgeArr(String[] ageArr) {
		this.ageArr = ageArr;
	}
	public String[] getAgeAtMaturityArr() {
		return ageAtMaturityArr;
	}
	public void setAgeAtMaturityArr(String[] ageAtMaturityArr) {
		this.ageAtMaturityArr = ageAtMaturityArr;
	}
	public String[] getIncomeTakenArr() {
		return incomeTakenArr;
	}
	public void setIncomeTakenArr(String[] incomeTakenArr) {
		this.incomeTakenArr = incomeTakenArr;
	}
	public String[] getRelationArr() {
		return relationArr;
	}
	public void setRelationArr(String[] relationArr) {
		this.relationArr = relationArr;
	}
	public String[] getPropOwnerArr() {
		return propOwnerArr;
	}
	public void setPropOwnerArr(String[] propOwnerArr) {
		this.propOwnerArr = propOwnerArr;
	}
	public String[] getCIBILcodesArr() {
		return CIBILcodesArr;
	}
	public void setCIBILcodesArr(String[] cIBILcodesArr) {
		CIBILcodesArr = cIBILcodesArr;
	}
	public String[] getAddressArr() {
		return addressArr;
	}
	public void setAddressArr(String[] addressArr) {
		this.addressArr = addressArr;
	}
	public String[] getContactNoReceiptArr() {
		return contactNoReceiptArr;
	}
	public void setContactNoReceiptArr(String[] contactNoReceiptArr) {
		this.contactNoReceiptArr = contactNoReceiptArr;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAgeAtMaturity() {
		return ageAtMaturity;
	}
	public void setAgeAtMaturity(String ageAtMaturity) {
		this.ageAtMaturity = ageAtMaturity;
	}
	public String getIncomeTaken() {
		return incomeTaken;
	}
	public void setIncomeTaken(String incomeTaken) {
		this.incomeTaken = incomeTaken;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getPropOwner() {
		return propOwner;
	}
	public void setPropOwner(String propOwner) {
		this.propOwner = propOwner;
	}
	public String getCIBILcodes() {
		return CIBILcodes;
	}
	public void setCIBILcodes(String cIBILcodes) {
		CIBILcodes = cIBILcodes;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNoReceipt() {
		return contactNoReceipt;
	}
	public void setContactNoReceipt(String contactNoReceipt) {
		this.contactNoReceipt = contactNoReceipt;
	}
	public String getShareholdersName() {
		return shareholdersName;
	}
	public void setShareholdersName(String shareholdersName) {
		this.shareholdersName = shareholdersName;
	}
	public String getDirectors() {
		return directors;
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}
	public String getOnLoanStructure() {
		return onLoanStructure;
	}*//*
	public void setOnLoanStructure(String onLoanStructure) {
		this.onLoanStructure = onLoanStructure;
	}
	public String getNoOfShares() {
		return noOfShares;
	}
	public void setNoOfShares(String noOfShares) {
		this.noOfShares = noOfShares;
	}
	public String getStakeInAgePer() {
		return stakeInAgePer;
	}
	public void setStakeInAgePer(String stakeInAgePer) {
		this.stakeInAgePer = stakeInAgePer;
	}
	public String[] getShareholdersNameArr() {
		return shareholdersNameArr;
	}
	public void setShareholdersNameArr(String[] shareholdersNameArr) {
		this.shareholdersNameArr = shareholdersNameArr;
	}
	public String[] getDirectorsArr() {
		return directorsArr;
	}
	public void setDirectorsArr(String[] directorsArr) {
		this.directorsArr = directorsArr;
	}
	public String[] getOnLoanStructureArr() {
		return onLoanStructureArr;
	}
	public void setOnLoanStructureArr(String[] onLoanStructureArr) {
		this.onLoanStructureArr = onLoanStructureArr;
	}
	public String[] getNoOfSharesArr() {
		return noOfSharesArr;
	}
	public void setNoOfSharesArr(String[] noOfSharesArr) {
		this.noOfSharesArr = noOfSharesArr;
	}
	public String[] getpName() {
		return pName;
	}
	public void setpName(String[] pName) {
		this.pName = pName;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}*/

	public String getBpType() {
		return bpType;
	}
	/*public void setYearRemarks(String[] yearRemarks) {
		this.yearRemarks = yearRemarks;
	}*/
	public String[] getpName() {
		return pName;
	}
	public void setpName(String[] pName) {
		this.pName = pName;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	
	
	
	public String getToBeConsidered() {
		return toBeConsidered;
	}
	public void setToBeConsidered(String toBeConsidered) {
		this.toBeConsidered = toBeConsidered;
	}
	public String getApplicantIncome() {
		return applicantIncome;
	}
	public void setApplicantIncome(String applicantIncome) {
		this.applicantIncome = applicantIncome;
	}
	public String getApplicantObligation() {
		return applicantObligation;
	}
	public void setApplicantObligation(String applicantObligation) {
		this.applicantObligation = applicantObligation;
	}
	public String getCoAapplicantIncome() {
		return coAapplicantIncome;
	}
	public void setCoAapplicantIncome(String coAapplicantIncome) {
		this.coAapplicantIncome = coAapplicantIncome;
	}
	public String getCoApplicantObligation() {
		return coApplicantObligation;
	}
	public void setCoApplicantObligation(String coApplicantObligation) {
		this.coApplicantObligation = coApplicantObligation;
	}
	public String getLoanRoi() {
		return loanRoi;
	}
	public void setLoanRoi(String loanRoi) {
		this.loanRoi = loanRoi;
	}
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	public String getCustomerTypeList() {
		return customerTypeList;
	}
	public void setCustomerTypeList(String customerTypeList) {
		this.customerTypeList = customerTypeList;
	}
	public void setBpType(String bpType) {
		this.bpType = bpType;
	}
	public String getQueriesRequirementsId() {
		return queriesRequirementsId;
	}
	public void setQueriesRequirementsId(String queriesRequirementsId) {
		this.queriesRequirementsId = queriesRequirementsId;
	}
	public String getApplicationFormKyc() {
		return applicationFormKyc;
	}
	public void setApplicationFormKyc(String applicationFormKyc) {
		this.applicationFormKyc = applicationFormKyc;
	}
	public String getBanking() {
		return banking;
	}
	public void setBanking(String banking) {
		this.banking = banking;
	}
	public String getCibil() {
		return cibil;
	}
	public void setCibil(String cibil) {
		this.cibil = cibil;
	}
	public String getCollateral() {
		return collateral;
	}
	public void setCollateral(String collateral) {
		this.collateral = collateral;
	}
	public String getFinancialsNotesSchedules() {
		return financialsNotesSchedules;
	}
	public void setFinancialsNotesSchedules(String financialsNotesSchedules) {
		this.financialsNotesSchedules = financialsNotesSchedules;
	}
	public String getLoanSheetRepaymentSchedules() {
		return loanSheetRepaymentSchedules;
	}
	public void setLoanSheetRepaymentSchedules(String loanSheetRepaymentSchedules) {
		this.loanSheetRepaymentSchedules = loanSheetRepaymentSchedules;
	}
	public String getLoanStructure() {
		return loanStructure;
	}
	public void setLoanStructure(String loanStructure) {
		this.loanStructure = loanStructure;
	}
	public String getSanctionLetter() {
		return sanctionLetter;
	}
	public void setSanctionLetter(String sanctionLetter) {
		this.sanctionLetter = sanctionLetter;
	}
	public String getTaxReturns() {
		return taxReturns;
	}
	public void setTaxReturns(String taxReturns) {
		this.taxReturns = taxReturns;
	}
	
	public String getCustomerTypeDesc() {
		return customerTypeDesc;
	}
	public void setCustomerTypeDesc(String customerTypeDesc) {
		this.customerTypeDesc = customerTypeDesc;
	}
	
	public String getConcernsAndChecksId() {
		return concernsAndChecksId;
	}
	public void setConcernsAndChecksId(String concernsAndChecksId) {
		this.concernsAndChecksId = concernsAndChecksId;
	}
	
	public String getEligibility() {
		return eligibility;
	}
	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}
	
	
	
	public String getApplicationFormKycRemarks() {
		return applicationFormKycRemarks;
	}
	public void setApplicationFormKycRemarks(String applicationFormKycRemarks) {
		this.applicationFormKycRemarks = applicationFormKycRemarks;
	}
	public String getBankingRemarks() {
		return bankingRemarks;
	}
	public void setBankingRemarks(String bankingRemarks) {
		this.bankingRemarks = bankingRemarks;
	}
	public String getCibilRemarks() {
		return cibilRemarks;
	}
	public void setCibilRemarks(String cibilRemarks) {
		this.cibilRemarks = cibilRemarks;
	}
	public String getCollateralRemarks() {
		return collateralRemarks;
	}
	public void setCollateralRemarks(String collateralRemarks) {
		this.collateralRemarks = collateralRemarks;
	}
	public String getFinancialsNotesSchedulesRemarks() {
		return financialsNotesSchedulesRemarks;
	}
	public void setFinancialsNotesSchedulesRemarks(
			String financialsNotesSchedulesRemarks) {
		this.financialsNotesSchedulesRemarks = financialsNotesSchedulesRemarks;
	}
	public String getLoanSheetRepaymentSchedulesRemarks() {
		return loanSheetRepaymentSchedulesRemarks;
	}
	public void setLoanSheetRepaymentSchedulesRemarks(
			String loanSheetRepaymentSchedulesRemarks) {
		this.loanSheetRepaymentSchedulesRemarks = loanSheetRepaymentSchedulesRemarks;
	}
	public String getLoanStructureRemarks() {
		return loanStructureRemarks;
	}
	public void setLoanStructureRemarks(String loanStructureRemarks) {
		this.loanStructureRemarks = loanStructureRemarks;
	}
	public String getSanctionLetterRemarks() {
		return sanctionLetterRemarks;
	}
	public void setSanctionLetterRemarks(String sanctionLetterRemarks) {
		this.sanctionLetterRemarks = sanctionLetterRemarks;
	}
	public String getTaxReturnsRemarks() {
		return taxReturnsRemarks;
	}
	public void setTaxReturnsRemarks(String taxReturnsRemarks) {
		this.taxReturnsRemarks = taxReturnsRemarks;
	}
	public String getCustomerTypeDescRemarks() {
		return customerTypeDescRemarks;
	}
	public void setCustomerTypeDescRemarks(String customerTypeDescRemarks) {
		this.customerTypeDescRemarks = customerTypeDescRemarks;
	}
	public String getEligibilityRemarks() {
		return eligibilityRemarks;
	}
	public void setEligibilityRemarks(String eligibilityRemarks) {
		this.eligibilityRemarks = eligibilityRemarks;
	}
	
	public String getDocEntity() {
		return docEntity;
	}
	public void setDocEntity(String docEntity) {
		this.docEntity = docEntity;
	}
	public String getEntityCustomerId() {
		return entityCustomerId;
	}
	public void setEntityCustomerId(String entityCustomerId) {
		this.entityCustomerId = entityCustomerId;
	}
	
	public String getTargetSheetName() {
		return targetSheetName;
	}
	public void setTargetSheetName(String targetSheetName) {
		this.targetSheetName = targetSheetName;
	}
	
	
	public String getLbxConcernsParentId() {
		return lbxConcernsParentId;
	}
	public void setLbxConcernsParentId(String lbxConcernsParentId) {
		this.lbxConcernsParentId = lbxConcernsParentId;
	}
	public String getLbxConcernsId() {
		return lbxConcernsId;
	}
	public void setLbxConcernsId(String lbxConcernsId) {
		this.lbxConcernsId = lbxConcernsId;
	}
	

	public String[] getConcernsCheckRemarks() {
		return concernsCheckRemarks;
	}
	public void setConcernsCheckRemarks(String[] concernsCheckRemarks) {
		this.concernsCheckRemarks = concernsCheckRemarks;
	}
	public String[] getConcernsCheckId() {
		return concernsCheckId;
	}
	public void setConcernsCheckId(String[] concernsCheckId) {
		this.concernsCheckId = concernsCheckId;
	}
	public String[] getConcernsCheckValue() {
		return concernsCheckValue;
	}
	public void setConcernsCheckValue(String[] concernsCheckValue) {
		this.concernsCheckValue = concernsCheckValue;
	}
	
	public String getConcernsCheckCode() {
		return concernsCheckCode;
	}
	public void setConcernsCheckCode(String concernsCheckCode) {
		this.concernsCheckCode = concernsCheckCode;
	}
	public String getConcernsValue() {
		return concernsValue;
	}
	public void setConcernsValue(String concernsValue) {
		this.concernsValue = concernsValue;
	}
	
	public String[] getConcernsAndChecksArrId() {
		return concernsAndChecksArrId;
	}
	public void setConcernsAndChecksArrId(String[] concernsAndChecksArrId) {
		this.concernsAndChecksArrId = concernsAndChecksArrId;
	}
	
	public String[] getQueryRequrementRemarks() {
		return queryRequrementRemarks;
	}
	public void setQueryRequrementRemarks(String[] queryRequrementRemarks) {
		this.queryRequrementRemarks = queryRequrementRemarks;
	}
	public String[] getQueryRequrementCodes() {
		return queryRequrementCodes;
	}
	public void setQueryRequrementCodes(String[] queryRequrementCodes) {
		this.queryRequrementCodes = queryRequrementCodes;
	}
	public String[] getQueryRequrementValues() {
		return queryRequrementValues;
	}
	public void setQueryRequrementValues(String[] queryRequrementValues) {
		this.queryRequrementValues = queryRequrementValues;
	}
	public String getQueryRequrementCode() {
		return queryRequrementCode;
	}
	public void setQueryRequrementCode(String queryRequrementCode) {
		this.queryRequrementCode = queryRequrementCode;
	}
	public String getQueryRequrementValue() {
		return queryRequrementValue;
	}
	public void setQueryRequrementValue(String queryRequrementValue) {
		this.queryRequrementValue = queryRequrementValue;
	}
	
	public String[] getQueryRequrementArrId() {
		return queryRequrementArrId;
	}
	public void setQueryRequrementArrId(String[] queryRequrementArrId) {
		this.queryRequrementArrId = queryRequrementArrId;
	}
	
	public String getProgramScheme() {
		return programScheme;
	}
	public void setProgramScheme(String programScheme) {
		this.programScheme = programScheme;
	}
	
	public String getLbxDocDesc() {
		return lbxDocDesc;
	}
	public void setLbxDocDesc(String lbxDocDesc) {
		this.lbxDocDesc = lbxDocDesc;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
	public String getMcaUploadId() {
		return mcaUploadId;
	}
	public void setMcaUploadId(String mcaUploadId) {
		this.mcaUploadId = mcaUploadId;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

	public int getTotalRecordSize() {
		return totalRecordSize;
	}
	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}
	
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAgeAtMaturity() {
		return ageAtMaturity;
	}
	public void setAgeAtMaturity(String ageAtMaturity) {
		this.ageAtMaturity = ageAtMaturity;
	}
	public String getIncomeTaken() {
		return incomeTaken;
	}
	public void setIncomeTaken(String incomeTaken) {
		this.incomeTaken = incomeTaken;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getPropOwner() {
		return propOwner;
	}
	public void setPropOwner(String propOwner) {
		this.propOwner = propOwner;
	}
	public String getCIBILcodes() {
		return CIBILcodes;
	}
	public void setCIBILcodes(String cIBILcodes) {
		CIBILcodes = cIBILcodes;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNoReceipt() {
		return contactNoReceipt;
	}
	public void setContactNoReceipt(String contactNoReceipt) {
		this.contactNoReceipt = contactNoReceipt;
	}
	
	public String[] getCustomerNameArr() {
		return customerNameArr;
	}
	public void setCustomerNameArr(String[] customerNameArr) {
		this.customerNameArr = customerNameArr;
	}
	public String[] getDobArr() {
		return dobArr;
	}
	public void setDobArr(String[] dobArr) {
		this.dobArr = dobArr;
	}
	public String[] getAgeArr() {
		return ageArr;
	}
	public void setAgeArr(String[] ageArr) {
		this.ageArr = ageArr;
	}
	public String[] getAgeAtMaturityArr() {
		return ageAtMaturityArr;
	}
	public void setAgeAtMaturityArr(String[] ageAtMaturityArr) {
		this.ageAtMaturityArr = ageAtMaturityArr;
	}
	public String[] getIncomeTakenArr() {
		return incomeTakenArr;
	}
	public void setIncomeTakenArr(String[] incomeTakenArr) {
		this.incomeTakenArr = incomeTakenArr;
	}
	public String[] getRelationArr() {
		return relationArr;
	}
	public void setRelationArr(String[] relationArr) {
		this.relationArr = relationArr;
	}
	public String[] getPropOwnerArr() {
		return propOwnerArr;
	}
	public void setPropOwnerArr(String[] propOwnerArr) {
		this.propOwnerArr = propOwnerArr;
	}
	public String[] getCIBILcodesArr() {
		return CIBILcodesArr;
	}
	public void setCIBILcodesArr(String[] cIBILcodesArr) {
		CIBILcodesArr = cIBILcodesArr;
	}
	public String[] getAddressArr() {
		return addressArr;
	}
	public void setAddressArr(String[] addressArr) {
		this.addressArr = addressArr;
	}
	public String[] getContactNoReceiptArr() {
		return contactNoReceiptArr;
	}
	public void setContactNoReceiptArr(String[] contactNoReceiptArr) {
		this.contactNoReceiptArr = contactNoReceiptArr;
	}
	
	public String[] getStakeInAgePerArr() {
		return stakeInAgePerArr;
	}
	public void setStakeInAgePerArr(String[] stakeInAgePerArr) {
		this.stakeInAgePerArr = stakeInAgePerArr;
	}
	public String getStakeInAgePer() {
		return stakeInAgePer;
	}
	public void setStakeInAgePer(String stakeInAgePer) {
		this.stakeInAgePer = stakeInAgePer;
	}
	public String[] getShareholdersNameArr() {
		return shareholdersNameArr;
	}
	public void setShareholdersNameArr(String[] shareholdersNameArr) {
		this.shareholdersNameArr = shareholdersNameArr;
	}
	public String getShareholdersName() {
		return shareholdersName;
	}
	public void setShareholdersName(String shareholdersName) {
		this.shareholdersName = shareholdersName;
	}
	public String[] getDirectorsArr() {
		return directorsArr;
	}
	public void setDirectorsArr(String[] directorsArr) {
		this.directorsArr = directorsArr;
	}
	public String getDirectors() {
		return directors;
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}
	public String getOnLoanStructure() {
		return onLoanStructure;
	}
	public void setOnLoanStructure(String onLoanStructure) {
		this.onLoanStructure = onLoanStructure;
	}
	public String[] getOnLoanStructureArr() {
		return onLoanStructureArr;
	}
	public void setOnLoanStructureArr(String[] onLoanStructureArr) {
		this.onLoanStructureArr = onLoanStructureArr;
	}
	public String getNoOfShares() {
		return noOfShares;
	}
	public void setNoOfShares(String noOfShares) {
		this.noOfShares = noOfShares;
	}
	public String[] getNoOfSharesArr() {
		return noOfSharesArr;
	}
	public void setNoOfSharesArr(String[] noOfSharesArr) {
		this.noOfSharesArr = noOfSharesArr;
	}
	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (this == obj)
			flag= true;
        if (obj == null)
        	flag= false;
        if (getClass() != obj.getClass())
        	flag= false;
        FinancialAnalysisVo vo = (FinancialAnalysisVo)obj;
        if(this.paramName.equalsIgnoreCase(vo.getParamName()))
        	flag=true;
		return flag;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + ((paramName == null) ? 0 : paramName.hashCode());
        return result;
	}
}
