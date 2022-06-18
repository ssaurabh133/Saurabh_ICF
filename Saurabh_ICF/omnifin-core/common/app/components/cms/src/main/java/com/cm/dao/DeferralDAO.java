package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.DeferralMakerVo;
import com.cm.vo.PartPrePaymentAuthorVO;
import com.cm.vo.PartPrePaymentSearchVO;
import com.cp.vo.RepayScheduleVo;

public interface DeferralDAO 
{
	String IDENTITY="DEFERAL";
	ArrayList<PartPrePaymentSearchVO> searchDeferralData(PartPrePaymentSearchVO vo, String type);
	ArrayList<DeferralMakerVo> selectDeferralData(String lbxLoanNoHID,String reschId, String recStatus);
	String checkDeferralSaveFeasibility(DeferralMakerVo vo);
	String saveDeferralData(DeferralMakerVo vo);
	boolean updateDeferralData(DeferralMakerVo vo, String type);
	String saveDeferralAuthor(PartPrePaymentAuthorVO vo);
	String getMakerDate(String reschId);
	ArrayList<RepayScheduleVo> getNewRepayScheduleDeferral(RepayScheduleVo vo,
			String loanId, String reschId);
	boolean deleteDeferralData(String loanId, String reschId);
	
	
}
