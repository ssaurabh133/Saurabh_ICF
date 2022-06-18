package com.cm.dao;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.cm.vo.PaymentMakerForCMVO;


public interface PaymentDAO {
    String IDENTITY="PAYMENTD";
	ArrayList<PaymentMakerForCMVO> getchequeDetail(String bankAccountId,String accountType);
	/*ArrayList<PaymentMakerForCMVO>*/Map paymentMakerGrid(PaymentMakerForCMVO vo);
	ArrayList<PaymentMakerForCMVO> paymentAuthorGrid(PaymentMakerForCMVO vo);
	ArrayList<PaymentMakerForCMVO> searchPaymentData(PaymentMakerForCMVO paymentMakerForCMVO);
	ArrayList<PaymentMakerForCMVO> getbussinessPartner();
	ArrayList<PaymentMakerForCMVO> saveddatatoApprove(PaymentMakerForCMVO paymentMakerForCMVO);
	boolean closeViewPayable(PaymentMakerForCMVO paymentMakerForCMVO);
	ArrayList<PaymentMakerForCMVO>onAllocatedPayable(PaymentMakerForCMVO paymentMakerForCMVO,int loanId,String bpType,int instrumentId);
	public String getpmntId();	
	ArrayList<PaymentMakerForCMVO> onViewPayable(PaymentMakerForCMVO paymentMakerForCMVO,int loanId,String bpType,int bpId);
	String updateFlagByPaymentAuthor(PaymentMakerForCMVO paymentMakerForCMVO);
	ArrayList<PaymentMakerForCMVO> getViewPayable(int loanId,String bpType,int bpId, String uId,String amount,int instrumentId,String tdsAmount);
	int existData(Object ob);
	ArrayList saveEnteredData(Object ob);
	
	public String getinstrumentId();	
    String checkOnPaymentForward(PaymentMakerForCMVO paymentMakerForCMVO);
    int existInsNoinPay(PaymentMakerForCMVO vo);
	String saveAllForwardedData(PaymentMakerForCMVO paymentMakerForCMVO,String amount,String tdsAmount);
	boolean updateOnSave(PaymentMakerForCMVO paymentVO);
		
	boolean deletePayment(String instrumentId);
	String saveAllForwardedDataWithoutCheck(PaymentMakerForCMVO paymentMakerForCMVO, String amount,String tdsAmount);
	String allocatePayableCheckFlag();
	String payableAmountCheck(Object loanId);
	String checkPaymentStatusFromInventory(Object ob);
	String cashPaymentLimit();
	//Manish
	ArrayList getDataFromInstrumentPayment(String instrumentId,String loanID);
	//Code End Of Manish
	String getLoanRecStatusPayment(String loanID);
	String getCheckDateCountPayment(String instrumentId);
	String getParamValuePayment();
}
