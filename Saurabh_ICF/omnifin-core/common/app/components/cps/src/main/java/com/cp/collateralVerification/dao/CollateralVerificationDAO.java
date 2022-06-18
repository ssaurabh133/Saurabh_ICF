package com.cp.collateralVerification.dao;

import java.util.ArrayList;

import com.cp.collateralVerification.vo.CollateralCapturingVO;
import com.cp.vo.CommonDealVo;

public interface CollateralVerificationDAO {

	ArrayList<CommonDealVo> searchCollateralVerifiationCapturingData(CommonDealVo vo);
	ArrayList<CollateralCapturingVO> getCollateralCapturingData(String dealId, String recStatus,String userId);
	boolean saveMachineVerificationDetails(CollateralCapturingVO vo);
	boolean savePropertyVerificationDetails(CollateralCapturingVO vo);
	boolean saveVehicleVerificationDetails(CollateralCapturingVO vo);
	boolean saveStockVerificationDetails(CollateralCapturingVO vo);
	boolean saveOtherVerificationDetails(CollateralCapturingVO vo);
	ArrayList<CollateralCapturingVO> getCollateralCapturingMachineData(String dealId, String verificationId, String collateralId, String recStatus);
	ArrayList<CollateralCapturingVO> getCollateralCapturingPropertyData(String dealId, String verificationId, String collateralId, String recStatus);
	ArrayList<CollateralCapturingVO> getCollateralCapturingVehicleData(String dealId, String verificationId, String collateralId, String recStatus);
	ArrayList<CollateralCapturingVO> getCollateralCapturingStockData(String dealId, String verificationId, String collateralId, String recStatus);
	ArrayList<CollateralCapturingVO> getCollateralCapturingOtherData(String dealId, String verificationId, String collateralId, String recStatus);
	ArrayList<CollateralCapturingVO> getCollateralsCapturedData(String dealId,String userId);
	boolean forwardCollateralCapturingData(String dealId, String verificationId);
	
	ArrayList<CommonDealVo> searchCollateralVerifiationCompletionData(CommonDealVo vo);
	ArrayList<CommonDealVo>	initialCollateralVerifiationCapturingData(CommonDealVo vo);
	ArrayList<CommonDealVo>	initialCollateralVerifiationCompletionData(CommonDealVo vo);
	ArrayList getTradeHeader(String dealId);
	boolean saveCollateralCompletionDecision(CollateralCapturingVO vo);
	
}
