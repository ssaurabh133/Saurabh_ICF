package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.InstallmentPlanForCMVO;
import com.cm.vo.PartPrePaymentAuthorVO;
import com.cm.vo.PartPrePaymentSearchVO;
import com.cm.vo.RepricingMakerVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.RepayScheduleVo;

public interface RepricingDAO 
{
	String IDENTITY="REPRICINGD";
	ArrayList<PartPrePaymentSearchVO> searchRepricing(PartPrePaymentSearchVO vo, String type);
	ArrayList<RepricingMakerVo> selectRericingData(String lbxLoanNoHID,String reschId, String recStatus);
	
	ArrayList<RepayScheduleVo> getRepayScheduleDisbursal(String loanId);
	String saveRepricingData(RepricingMakerVo vo);
	ArrayList<InstallmentPlanForCMVO> getInstallType(String loanId);
	boolean updateRepricingData(RepricingMakerVo vo, String type);
	String repricingMakerFeasibility(String lbxLoanNoHID, String businessDate);
	ArrayList<CodeDescVo> getBaseRateList(String businessdate);
	ArrayList<RepricingMakerVo> retriveRepricingValues(String lbxLoanNoHID);
	String saveRepricingAuthor(PartPrePaymentAuthorVO vo);
	String getMakerDate(String reschId);
	ArrayList<RepayScheduleVo> getNewRepayScheduleRepricing(RepayScheduleVo vo,
			String loanId, String reschId);
	boolean checkOldNewTenureAreEqual(String loanId, String reschId,String reschType);
	boolean deleteRericingData(String reschId, String loanId);
	
}

