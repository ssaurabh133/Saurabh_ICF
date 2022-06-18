package com.cp.daoImplMSSQL;

import java.util.ArrayList;
import java.util.Map;

import com.cp.dao.FileUplaodDao;
import com.cp.vo.CibilObligationVo;
import com.cp.vo.CommonXlsVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class MSSQLFileUploadDaoImpl implements FileUplaodDao {

	@Override
	public boolean saveBalanceSheet(Object ob, ArrayList<CommonXlsVo> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveProfitAndLossSheet(Object ob, ArrayList<CommonXlsVo> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveBankingDetails(Object ob, Map<String, Map<String, String>> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateWorkFlowRecords(Object ob, String queueName,
			String bgStatus, String stageId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList getBankingDetailsData(UnderwritingDocUploadVo uwDocVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateBankingDetailsTemp(ArrayList list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveBankingDetailsInMain(ArrayList list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkBankDataisValidation(UnderwritingDocUploadVo uwDocVo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveErrorRecords(int row,int col,UnderwritingDocUploadVo uwDocVo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBankDetailsData(String refId, String makerId, String Doc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFinancialDetailsData(String caseId, String sourceType,
			String makerId, String Doc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getVerificationAccountNumberUpload(String dealId,
			String customerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveRecordCibilObligation(ArrayList<CibilObligationVo> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCibilObligationRecord(String caseId, String documentId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAccountLoanNumberRecord(String caseId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveBankingDetailsPdf(Object ob, ArrayList headerDetails,
			ArrayList valueDetails) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveBankingDetailsDataForPDF(ArrayList list,
			Map<String, String> map, Object ob) {
		// TODO Auto-generated method stub
		return false;
	}



}
