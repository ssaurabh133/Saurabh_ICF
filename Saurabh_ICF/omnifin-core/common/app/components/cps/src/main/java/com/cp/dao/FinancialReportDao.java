package com.cp.dao;

import java.util.ArrayList;

import com.cp.vo.FinancialReportVo;

public interface FinancialReportDao {
	String IDENTITY="FRD";
boolean saveReportLink(FinancialReportVo vo);
ArrayList getReportLink(String caseId,String makerId,String reportType,String refId,String customerName, String stageId, boolean isCreditApproval);
ArrayList getReportList(String key);
boolean checkFundFlowForwardStatus(String caseId,String stageId);
}
