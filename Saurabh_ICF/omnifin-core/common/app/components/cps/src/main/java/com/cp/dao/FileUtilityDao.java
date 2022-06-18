package com.cp.dao;

import java.util.ArrayList;

public interface FileUtilityDao {
	String IDENTITY="FU"; 
String getDocPath(String docId);
String getCustomerRoleType(String caseId);
String getCustomerRoleType(String caseId,String customerId);
String getFileName(String docId);
String getCustomerID(String caseId);
String getNomenClatureFilePath();
String getNomenClatureFileForFormatHeading(String str);
String getCustomerNameByCustomerId(String customerId);
String getBankName(String bankId);
String getParameterMSTInfo(String key);
String getApplicationReferenceNo(String caseId);
String getProductId(String caseId);
String checkWorkFlowStatus(String CaseId,String stageId);
String getDocType(String documentId);
ArrayList getUploadErrorReasons(String caseId, String docId);
ArrayList getUploadErrorReasonsBS(String caseId, String docId);
ArrayList saveTargetValuesTmp(String caseId, String makerDate,String makerId);
ArrayList generateBankingProgram(String caseId, String makerDate,String makerId);
String getFinancialYearFromCustomerDemeographics(String caseId);
String getSchemeID(String caseId);
ArrayList getRTRObligationValue(String caseId,String sheetMame);
ArrayList getGenericMasterList(String genericKey);
String getProductCamTemplateId(String productId);
ArrayList getTopUpValue(String caseId,String sheetMame);
public String getGenericMasterDesc(String genericKey,String value);


}
