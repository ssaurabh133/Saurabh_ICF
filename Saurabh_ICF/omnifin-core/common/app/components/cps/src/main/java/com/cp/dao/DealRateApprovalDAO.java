package com.cp.dao;

import java.util.ArrayList;

import com.cp.vo.DealRateApprovalVO;

public interface DealRateApprovalDAO 
{
	String IDENTITY="DRA";

	ArrayList fetchDealRateApprovalMakerDetail(DealRateApprovalVO vo);

	ArrayList fetchRecordDetail(DealRateApprovalVO vo);
	
	boolean insertRecordDetail(DealRateApprovalVO vo);
	
	ArrayList fetchDealRateApprovalAuthorDetail(DealRateApprovalVO vo);
	
	public boolean saveDealRateAuthorDetail(DealRateApprovalVO vo);
	
	ArrayList getRateApprovalAuthorDetail(String dealId);
	
	ArrayList<Object> getCollateralDetailsAllForRateApproval(String dealId,String assetOrCollateralResult);
	
	public String isItMachineOrProperty(String dealId);

}
