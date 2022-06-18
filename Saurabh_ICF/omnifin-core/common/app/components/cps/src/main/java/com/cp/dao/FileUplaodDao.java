package com.cp.dao;

import java.util.ArrayList;
import java.util.Map;

import com.cp.vo.CibilObligationVo;
import com.cp.vo.CommonXlsVo;
import com.cp.vo.UnderwritingDocUploadVo;

public interface FileUplaodDao {
	String IDENTITY="FUD"; 
public boolean saveBalanceSheet(Object ob , ArrayList<CommonXlsVo> list);
public boolean saveProfitAndLossSheet(Object ob , ArrayList<CommonXlsVo> list);
public boolean saveBankingDetails(Object ob , Map<String, Map<String, String>> map);
public boolean updateWorkFlowRecords(Object ob,String queueName,String bgStatus, String stageId);
public ArrayList getBankingDetailsData(UnderwritingDocUploadVo uwDocVo);
boolean updateBankingDetailsTemp(ArrayList list);
boolean saveBankingDetailsInMain(ArrayList list);
boolean checkBankDataisValidation(UnderwritingDocUploadVo uwDocVo);
boolean saveErrorRecords(int row, int col,UnderwritingDocUploadVo uwDocVo);
boolean deleteBankDetailsData(String refId,String makerId, String Doc);
boolean deleteFinancialDetailsData(String caseId,String sourceType,String makerId, String Doc);
boolean getVerificationAccountNumberUpload(String dealId,String customerId);
boolean saveRecordCibilObligation(ArrayList<CibilObligationVo> list);
boolean deleteCibilObligationRecord(String caseId, String documentId);
boolean deleteAccountLoanNumberRecord(String caseId) ;
public boolean saveBankingDetailsPdf(Object ob, ArrayList headerDetails,ArrayList valueDetails);
public boolean saveBankingDetailsDataForPDF(ArrayList list, Map<String, String> map,Object ob);

}
