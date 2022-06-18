package com.cp.daoImplMSSQL;

import java.util.ArrayList;

import com.cp.dao.FinancialReportDao;
import com.cp.vo.FinancialReportVo;

public class MSSQLFinancialReportDaoImpl implements FinancialReportDao{

	@Override
	public boolean saveReportLink(FinancialReportVo vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList getReportLink(String caseId, String makerId,
			String reportType,String refId,String customerName, String stageId, boolean isCreditApproval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getReportList(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkFundFlowForwardStatus(String caseId,String stageId) {
		// TODO Auto-generated method stub
		return false;
	}

}
