package com.cp.financialDao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cp.vo.CommonDealVo;
import com.cp.vo.FinancialAnalysisParamVo;
import com.cp.vo.FinancialAnalysisVo;

public interface FinancialDAO 
{
	String IDENTITY="FID";
	public ArrayList<CommonDealVo> financialSearchGetDetail(CommonDealVo vo);
	public String saveBalanceSheet(Object ob); 
	public boolean saveFinancialRatioAnalysisDetail(Object ob); 
	public ArrayList getParamDetailDetails(String sourceType);
	public ArrayList getRatioParamDetails(String sourceType);
	public ArrayList<CommonDealVo> financialGetDetailBehind(CommonDealVo vo,HttpServletRequest request);
	
	public boolean financialAnalysisForward(String dealId);
	public boolean financialRatioAnalysisUpdate(String dealId);
	public ArrayList getdealAllParamDeatils(CommonDealVo vo);
	public String saveProfitandBalance(FinancialAnalysisVo vo);
	public ArrayList getParamMinusDetails(String string);
	public String saveBalanceSheetWithMinus(FinancialAnalysisParamVo vo);
	public String saveProfitandBalanceWithMinus(FinancialAnalysisParamVo fvo);
	public ArrayList<FinancialAnalysisVo> getdealAllMinusParamDeatils(
			CommonDealVo vo);
	//method added by sachin
	public boolean insertCorRatioData(ArrayList ratioAnalysisList,String dealId, String userID, String businessDate, String[] year);
	public boolean insertIndRatioData(ArrayList ratioAnalysisList,String dealId, String userID, String businessDate);
	//ADDED BY ARUN
	public ArrayList<FinancialAnalysisVo> getIndRAtioDataList(FinancialAnalysisVo vo);
	//Code by Arun For Default obligation AND TOTAL dr cr
	public CommonDealVo getPremValueForOtherPerm(CommonDealVo vo);
	//Code by Arun For Default obligation AND TOTAL dr cr
	
	//CODE MERGED BY SANJOG
	int getYearForFinancialAnalysis();
	public FinancialAnalysisVo getParamCode(String pcode,FinancialAnalysisVo vo);
	public FinancialAnalysisParamVo getNegativeParamCode(String pcode,FinancialAnalysisParamVo vo);
	
	public FinancialAnalysisVo getBalanceSheetParamCode(String pcode,FinancialAnalysisVo vo);
	public FinancialAnalysisParamVo getBalanceSheetNegativeParamCode(String pcode,FinancialAnalysisParamVo vo);
	public ArrayList getUploadedData(String dealId, String userId, String sourceType);
//	start by sachin
	public String benchBranchRatioList(String ratioCode,String dealId);
	public ArrayList getdealAllParamDeatilsForRatio(String dealId);
//end by sachin	
	public ArrayList getdealAllParamDeatils(String sourceType, String caseId,
			String customerId, String recStatus,int cy,int py1,int py2);
	
	
}
