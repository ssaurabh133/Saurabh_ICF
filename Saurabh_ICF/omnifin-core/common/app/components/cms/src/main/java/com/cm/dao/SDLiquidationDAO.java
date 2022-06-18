package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.DisbursalAuthorVO;
import com.cm.vo.DisbursalMakerVO;
import com.cm.vo.LiquidationAuthorVO;
import com.cm.vo.LiquidationMakerVO;
import com.cm.vo.LiquidationSearchVO;

public interface SDLiquidationDAO {
	
	String IDENTITY="SDLD";
	ArrayList<LiquidationSearchVO> searchLiquidationData(LiquidationSearchVO vo,String type);
	boolean updateLiquidationData(LiquidationMakerVO vo, String type);
	boolean deleteLiquidationData(LiquidationMakerVO vo);
	String saveLiquidationData(LiquidationMakerVO vo);
	ArrayList<LiquidationMakerVO> selectLiquidationData(String loanId, String sdId, String sdLiquidationId, String type);
	String saveLiquidationAuthor(LiquidationAuthorVO vo);
	ArrayList<LiquidationMakerVO> getLoanForLiquidationValues(String lbxLoanNoHID);
	ArrayList<LiquidationMakerVO> getLiquidationValues(String lbxLoanNoHID,String lbxSdNoHid);
	ArrayList<LiquidationMakerVO> generateSDAccrualValues(String businessDate,int companyId, String userId, String loanId, String sdNo);
	String getMakerDate(String loanId);
	String earlyClosureFlag();
}
