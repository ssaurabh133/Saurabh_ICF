package com.cp.fundFlowDao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.Request;

import com.cm.vo.ManualAdviceCreationVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.CommonPageVo;
import com.cp.vo.FundFlowDownloadUploadVo;//Add BY Abhishek 

public interface FundFlowAnalysisDAO 
{
	String IDENTITY="FFA";
	public ArrayList<CommonDealVo> fundFlowSearchGetDetail(CommonDealVo vo);
	public ArrayList<CommonDealVo> fundFlowSearchGetDetailBehind(CommonDealVo vo,HttpServletRequest request);
	
	public int saveBankAccountAnalysis(Object ob); 
	public boolean updateBankAccountAnalysis(Object ob); 
	ArrayList getBankAccountAnalysisDetails(String bankAcctAnsId,String dealId,String recStatus);
	public int deleteBankAnalysis(String fundId);
	ArrayList<Object> getYears(String businessDate);
	
	public boolean saveSalesAnalysis(Object ob); 
	ArrayList getSalesAnalysisDetails(String salesAnsId,String dealId,String recStatus, String year);
	public String deleteSalesAnalysis(String salesId,String businessDate,String userId);
	public String updateSalesAnalysis(Object ob); 
	
	public String saveObligation(Object ob); 
	ArrayList getObligationDetails(String obligationId,String dealId,String recStatus);
	public int deleteObligationDetails(String salesId);
	public String updateObligation(Object ob); 
	
	boolean fundFlowForward(String dealId);
	public boolean fundFlowAuthor(Object ob);
	public ArrayList getSalesyear(String string, String dealId, String recStatus);
	public ArrayList getBankAnalysisDetails(String bankAcctAnsId,String dealId,String recStatus,String bptype);
// Abhishek Start
	public String InsertBankAccountDtl(ArrayList<FundFlowDownloadUploadVo> list);
	public ArrayList getAccountStatmentData();
	public String getBalanceAsOnDateData(String caseId,String balanceAsOnDate,String custRef,String customerId,String documentId);
	public ArrayList getBankStatementDtl(String dealId) ;
	
	//added by Pranaya
	public ArrayList getBankStatementData(String deal_no);
	public String InsertUpdateFundFlowBankDtl(FundFlowDownloadUploadVo vo);
	//end by Pranaya
}
