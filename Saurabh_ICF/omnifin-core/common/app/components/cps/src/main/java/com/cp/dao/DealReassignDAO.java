package com.cp.dao;

import java.util.ArrayList;

import com.cp.vo.DealReassignVo;
import com.cp.vo.ReprocessingDealVo;

public interface DealReassignDAO {

	String IDENTITY="DRD";
	ArrayList<DealReassignVo> getDealDetails(DealReassignVo dealReassignVo);
	boolean saveDealDetails(DealReassignVo linkLoanVo);
	boolean UpdateInsertDealMovemenDtl(ReprocessingDealVo reprocessingDealVo, String companyId, String businessDate, String userId);	
}
