package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.AdditionalDisbursalProcessVO;
import com.cm.vo.AdditionalDisbursalSearchVO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.cm.vo.LoanInitAuthorVo;
import com.cm.vo.PartPrePaymentAuthorVO;
import com.cm.vo.PartPrePaymentMakerVO;
import com.cm.vo.PartPrePaymentSearchVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cp.vo.RepayScheduleVo;
import com.cp.vo.RepayScheduleVo;

public interface AdditionalDisbursalDAO 
{
	//change by sachin
	 String IDENTITY="ADDITIONALD"; 
	//end by sachin
	ArrayList searchAdditionalDisbMakerData(AdditionalDisbursalSearchVO vo,
			String type);

	ArrayList searchAdditionalDisbAuthorData(AdditionalDisbursalSearchVO vo,
			String type);

	ArrayList<AdditionalDisbursalProcessVO> retrieveAdditionDisbValues(
			String lbxLoanNoHID);

	String saveAddDisbursalData(AdditionalDisbursalProcessVO vo);

	ArrayList<AdditionalDisbursalProcessVO> selectAdditionDisbursalData(String lbxLoanNoHID, String reschId,
			String string);


	String forwardAddDisbursalData(AdditionalDisbursalProcessVO vo);

	String additionDisbursalAuthor(LoanInitAuthorVo vo);

	String getMakerDate(String reschId);
	
	ArrayList<RepayScheduleVo> getNewRepayScheduleAdditionalDisbursal(
			RepayScheduleVo vo, String loanId, String reschId);

	ArrayList<RepayScheduleVo> getRepayScheduleAddDisbursal(RepayScheduleVo vo,
			String loanId, String reschId,String userId);

	boolean deleteAdditionalDisbursalData(String loanId, String reschId);

	ArrayList getGenericMasterInfo(String string); 
	
	
	
	
	
	
		
	
}
