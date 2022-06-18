package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.InstallmentPlanForCMVO;
import com.cm.vo.PartPrePaymentAuthorVO;
import com.cm.vo.PartPrePaymentMakerVO;
import com.cm.vo.PartPrePaymentSearchVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cp.vo.CodeDescVo;
import com.cp.vo.RepayScheduleVo;

public interface PartPrePaymentDAO 
{
	String IDENTITY="PPD";
	ArrayList<PartPrePaymentSearchVO> searchPartPrePaymentData(PartPrePaymentSearchVO vo,String type);
	ArrayList<PartPrePaymentMakerVO> selectPartPrePaymentData(String lbxLoanNoHID, String reschId, String recStatus);
	String savePartPrePaymentData(PartPrePaymentMakerVO vo);
	String updatePartPrePaymentData(PartPrePaymentMakerVO vo, String type);
	ArrayList<InstallmentPlanForCMVO> getInstallType(String loanId);
	ArrayList<PaymentMakerForCMVO>viewReceivableForPartPrePayment(int loanId);
	ArrayList<PartPrePaymentMakerVO> retrievePartPrePaymentValues(String lbxLoanNoHID);
	String savePartPrePaymentAuthor(PartPrePaymentAuthorVO vo);
	String getMakerDate(String reschId);
	ArrayList<RepayScheduleVo> getNewRepaySchedulePartPayment(
			RepayScheduleVo vo, String loanId, String reschId);
	boolean deletePartPaymentData(String loanId, String reschId);
	String partPrePaymentRealizeFlag();
	String getRecStatusForDisbursal(String lbxLoanNoHID);//add by saourabh
	ArrayList<CodeDescVo>getGenericMasterInfo(String genericKey);
}
