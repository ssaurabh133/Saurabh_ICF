package com.cp.dao;

import java.util.ArrayList;

import com.cp.vo.CommonDealVo;
import com.cp.vo.PushDataInTargetSheetVo;
import com.cp.vo.UnderwritingDocUploadVo;
import com.cp.vo.FundFlowDownloadUploadVo;
public interface EligibilityCalculationProcessDao {
	public String identity="ECP";
public ArrayList getCustomerDemographicsData(String dealId,String customerId);
public ArrayList getSystemInputData$CustDetailsIndv(UnderwritingDocUploadVo vo,String qry);
public ArrayList getSystemInputData$CustDetailsCorp(UnderwritingDocUploadVo vo,String qry);
public ArrayList getSystemInputData$CustDemographic(UnderwritingDocUploadVo vo,String qry);

public ArrayList getSystemInputData$Obligation(UnderwritingDocUploadVo vo,String qry);
public ArrayList getSystemInputData$BS(UnderwritingDocUploadVo vo,String qry);
public ArrayList getSystemInputData$BALS(UnderwritingDocUploadVo vo,String sourceType);
/*public ArrayList getSystemInputData$PL(UnderwritingDocUploadVo vo);*/
public ArrayList getSystemInputData$EcalManual(UnderwritingDocUploadVo vo);
public void saveDataInExcelErrorLog(UnderwritingDocUploadVo vo);
public ArrayList getCoapplCustomerListWithSequence(UnderwritingDocUploadVo vo);
public ArrayList getGaurantorCustomerListWithSequence(UnderwritingDocUploadVo vo);
public ArrayList getDataEntryForBankingReport(UnderwritingDocUploadVo vo);
public ArrayList<PushDataInTargetSheetVo> getTargetFieldValueData(String caseId);
public ArrayList getDataForBankingInput(UnderwritingDocUploadVo vo);
public boolean insertSchemeWiseEligibilityAmount(ArrayList<FundFlowDownloadUploadVo> list);
public String getBTOValue(UnderwritingDocUploadVo vo);	
public ArrayList getSystemInputData$LoanSheetObligation(UnderwritingDocUploadVo vo,String qry);




}
