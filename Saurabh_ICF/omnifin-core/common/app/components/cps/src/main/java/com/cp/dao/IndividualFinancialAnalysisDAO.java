package com.cp.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cp.vo.CommonDealVo;
import com.cp.vo.FinancialAnalysisParamVo;

public interface IndividualFinancialAnalysisDAO {
	String IDENTITY="IFA"; 
	public ArrayList<CommonDealVo> individualFinancialSearchGetDetail(CommonDealVo vo);
	public ArrayList<CommonDealVo> individualFinancialGetDetailBehind(CommonDealVo vo,HttpServletRequest request);
	public ArrayList getParamDetailDetails(String sourceType);
	
	public String saveIndividualFinancialIncome(Object ob); 
	public ArrayList getdealAllParamDeatils(CommonDealVo vo);
	public String saveIndividualObligation(Object ob); 
	
	ArrayList getObligationDetails(String obligationId,String dealId,String recStatus);
	public String updateIndividualObligation(Object ob); 
	public int deleteIndividualObligationDetails(String salesId);
	public boolean individualFinancialAnalysisForward(String dealId);
	public boolean individualFinancialAnalysisUpdate(String dealId);
	public String saveMinusIndividualFinancialIncome(
			FinancialAnalysisParamVo fvo);
	public boolean individualRatioAnalysisUpdate(String dealId);
	/*Code By arun for Individual Bank Acount Analysis*/
	public int saveBankAccountAnalysis(Object ob); 
	public boolean updateBankAccountAnalysis(Object ob); 
	ArrayList getBankAccountAnalysisDetails(String bankAcctAnsId,String dealId,String recStatus);
	public int deleteBankAnalysis(String fundId);
	/*Code By arun for Individual Bank Acount Analysis Ends Here*/
	/*CODE For financial Income*/
	 public ArrayList getIncomeSourceTypeList();
	 public ArrayList getCustomerTypeList(String dealId);
	 public ArrayList getCustomerName(String dealId,String customerType);
	 public ArrayList getIncomeDetailList(Object ob);
	 public ArrayList getIncomeDetaisForEdit(Object ob); 
	 public String updateIndividualFinancialIncome(Object ob); 
	/*CODE For financial Income*/
	 public ArrayList getObligationTypeList();
	 /*Code for Obligation*/
	 /**/
	 public String deleteRatioAnalysis(String dealId);
	 /**/
	 public ArrayList getTypeOfLoan();
	 public String updateStatus(String dealId);
	public boolean getCheckBankFinancialStatus(String caseId);
	ArrayList getGenericMasterInfoList(String genericKey);
	ArrayList getGenericMasterInfoList(String genericKey, String parentValue);
	
}
