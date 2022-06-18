package com.cp.daoImplMSSQL;

import java.util.ArrayList;

import com.cp.dao.EligibilityCalculationProcessDao;
import com.cp.vo.CommonDealVo;
import com.cp.vo.PushDataInTargetSheetVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class MSSQLEligibilityCalculationProcessDaoImpl implements EligibilityCalculationProcessDao{

	@Override
	public ArrayList getCustomerDemographicsData(String dealId,
			String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getSystemInputData$CustDetailsIndv (UnderwritingDocUploadVo vo,String qry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getSystemInputData$CustDetailsCorp(
			UnderwritingDocUploadVo vo, String qry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getSystemInputData$CustDemographic(
			UnderwritingDocUploadVo vo, String qry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getSystemInputData$Obligation(UnderwritingDocUploadVo vo,
			String qry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDataInExcelErrorLog(UnderwritingDocUploadVo vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList getSystemInputData$BS(UnderwritingDocUploadVo vo,
			String qry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getSystemInputData$BALS(UnderwritingDocUploadVo vo,String sourceType) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public ArrayList getSystemInputData$PL(UnderwritingDocUploadVo vo) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public ArrayList getCoapplCustomerListWithSequence(
			UnderwritingDocUploadVo vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getGaurantorCustomerListWithSequence(
			UnderwritingDocUploadVo vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getDataEntryForBankingReport(UnderwritingDocUploadVo vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PushDataInTargetSheetVo> getTargetFieldValueData(String caseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getDataForBankingInput(UnderwritingDocUploadVo vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getSystemInputData$EcalManual(UnderwritingDocUploadVo vo) {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
    public boolean insertSchemeWiseEligibilityAmount(ArrayList list)
    {
    	return false;
    }

	@Override
	public String getBTOValue(UnderwritingDocUploadVo vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getSystemInputData$LoanSheetObligation(
			UnderwritingDocUploadVo vo, String qry) {
		// TODO Auto-generated method stub
		return null;
	}

}
