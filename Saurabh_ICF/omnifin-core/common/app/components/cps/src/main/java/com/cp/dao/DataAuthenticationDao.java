package com.cp.dao;

import java.util.ArrayList;
import java.util.List;

import com.cp.vo.FinancialAnalysisVo;
import com.cp.vo.QuesBankVo;

public interface DataAuthenticationDao {
	public String identity="DAD";
	
	ArrayList<QuesBankVo> getQuestionForAuthentication(String caseId , String stageId, String docType,String businessDate,String documentId);
	boolean insertDataInTable(String caseId , String docType,String businessDate,String makerId,String docId, List<QuesBankVo> list);
	boolean saveDataForAuthor(String caseId , String docType,String businessDate,String makerId,String docId, List<QuesBankVo> list);
	
	
	int getYearForFinancialAnalysis();
	ArrayList getUploadedData(String dealId, String userId, String sourceType);
	ArrayList getParamDetailDetails(String sourceType);
	ArrayList getParamMinusDetails(String sourceType);
	String saveBalanceSheet(Object ob , String caseId, String stageId , String docType , String docId , String businessDate , String makerId , int year);
	FinancialAnalysisVo getParamCode(String string, FinancialAnalysisVo vo);
	boolean checkQuestionBankStatus(String caseId , String stageId, String docType,String businessDate,String documentId);
	boolean statusForQuestionAnswer(String caseId , String stageId, String docType,String businessDate,String documentId);
	ArrayList getQuestionAnswerList(String caseId , String stageId, String docType,String businessDate,String documentId);
	ArrayList dataValidationProcess(String caseId , String businessDate, String docId);
	 
}
