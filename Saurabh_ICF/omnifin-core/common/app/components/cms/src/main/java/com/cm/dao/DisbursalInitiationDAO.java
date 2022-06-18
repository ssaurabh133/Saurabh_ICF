package com.cm.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cm.vo.*;
import com.cp.vo.OtherChargesPlanVo;

public interface DisbursalInitiationDAO {   
    String IDENTITY="DISB";
	ArrayList<DisbursalSearchVO> searchCMGrid(DisbursalSearchVO vo);
	ArrayList<DisbursalMakerVO> getLoanDueDay(String lbxLoanNoHID);
	ArrayList<PostDisbursalDocVO> getDocumentStage();
	ArrayList<DisbursalMakerVO> getCycleDateList();	
	ArrayList<DisbursalSearchVO> searchDisbursalData(DisbursalSearchVO vo, String type,HttpServletRequest request);
	String saveDisbursalAuthor(DisbursalAuthorVO vo);
	
	ArrayList<DisbursalMakerVO> selectDisbursalData(String loanId, String disbursalNo,String businessDate,String bp_type);
	
	String disbursalFeasibility(DisbursalMakerVO vo);
	String getDisbursalAmountCheck(DisbursalMakerVO vo);
	int saveDisbursalData(DisbursalMakerVO vo, String disbursalFlag);
	boolean updateDisbursalData(DisbursalMakerVO vo, String recStatus, String disbursalFlag);
	ArrayList<DisbursalMakerVO> getDisbursalSchedule(String loanId);
	ArrayList<DisbursalMakerVO> getDisbursalData(String loanId);
	ArrayList<DisbursalMakerVO> getDisbursalValues(int loanId, String userId, String businessDate);
	ArrayList<PaymentMakerForCMVO>viewReceivableForDisbursal(int loanId);
	ArrayList getFromLoanDtl(String loanId);
	ArrayList getRepaySched(String loanId);
	String generateRepySchedule(String loanId,String makerId);
	ArrayList<PostDisbursalDocVO> searchPostDisbursalDocs(PostDisbursalDocVO vo,String type);
	boolean collectDocuments(String txnType,String dealId,String stage);
	ArrayList<DisbursalMakerVO> calculateEMI(String disbursalAmount,String disbursalDate,String loan_id,String bDate,String userId, String repayEffDate, String disbursalStatus);
	String loandisbursalid();
	boolean deletedisbursal(String loandisbursalid);
	String getProposedDisbursalCheck();
		
	ArrayList<DisbursalMakerVO> getShortpayOnDisbursalTo(int loanId, String userId, String businessDate,String disbursalTo);
	
	// Changes By Amit Starts
	String getDisbAuthorParameter(DisbursalAuthorVO vo);
	// Changes By Amit Ends
	String checkSpecialConditionAvail(DisbursalMakerVO vo, String disbId, String recStatus);
	/*Arun Change for disbursal with payment Starts here*/
	String saveDisbursalDataWithPayment(DisbursalMakerVO vo);
	ArrayList getDisbursalDataWithPayment(DisbursalMakerVO vo);
	String updateDisbursalDataWithPayment(DisbursalMakerVO vo,String recStatus);
	String saveDisbursalPaymentAddDetails(DisbursalMakerVO vo);
	String updateDisbursalPaymentAddDetails(DisbursalMakerVO vo);
	ArrayList selectAddDetailsForUpdate(DisbursalMakerVO vo);
	ArrayList selectAddDetailsList(DisbursalMakerVO vo,String from);
	ArrayList getTotalPayableReceiable(String loanid,String customerType);
	ArrayList getTotalShortPay(String loanid,String customerType);
	String deleteAddDetails(DisbursalMakerVO vo,String checkedStr);
	ArrayList getLoanAndDisburdesAmount(DisbursalMakerVO vo);
	String forwardDisbursal(DisbursalMakerVO vo,String loanDisbursalId[]);
	String generateRepyScheduleDisbursalPayment(String loanId,String makerId,String finalDisbursalFlag,String txnType);
	/*Arun Change for disbursal with payment Ends here */
	String getrevolvingFlag(String loanId);
	String getBalancePrinc(String loanId);
	String getForwardedAmt(String loanId);
	String getAmountInProcessLoan(int lbxLoanNoHID, String userId, String bDate);
	String getRecStatusForPartPayment(int lbxLoanNoHID, String userId, String bDate);// Add by saorabh
	String deleteNewAddDetails(DisbursalMakerVO vo,String checkedStr);
	String getRecoveryType(String loanid);
	String getDisbursalFlag(String loanid,String txnType);
	String getRepaymentType(String loanid);
	ArrayList getFromLoanDtlDisbursalPayment(String loanId);
	ArrayList getRepaySchedDisbursalPayment(String loanId,String makerId);
	boolean saveOtherChargesPlanAtDisbursal(OtherChargesPlanVo ipvo);
	ArrayList getOtherPeriodicalChargeDetailAtDisbursal(String id);
	String newInstallmentPlanStatus(DisbursalMakerVO vo);
	boolean paymentAmountAtDisbursal(DisbursalMakerVO vo);
	String getInstallmentType(String loanId);
	String getDateForDisbursalCheck( DisbursalMakerVO vo); //Add by saorabh
	String validationOnLeadPartner(String loanId);
	ArrayList selectOtherLoanDetails(int lbxLoanNoHID);
	String getEditDueDateStatus(String lbxLoanNoHID);
	String getRepyEffDateOfLoan(String lbxLoanNoHID);
	//String getEditDueDateStatus(String lbxLoanNoHID);
	String getRepayEffDate(String lbxLoanNoHID);
	String getLoanTenure(String lbxLoanNoHID);//added by Brijesh Pathak
	// String getLoanTenure(String lbxLoanNoHID);//added by Brijesh Pathak
	// String getRepayEffDate(String lbxLoanNoHID);
 
	
	//ArrayList checkSblGblLimit(String lbxLoanNoHID);//added by indrajeet
 
}
