package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.PaymentMakerForCMVO;
import com.cm.vo.ReceiptMakerVO;

public interface ReceiptDAO {
	
	String IDENTITY="RECEIPTD";
	ArrayList<ReceiptMakerVO> receiptMakerGrid(ReceiptMakerVO vo);
	ArrayList<ReceiptMakerVO> receiptAuthorGrid(ReceiptMakerVO vo);
	ArrayList<ReceiptMakerVO> getReceiptList(ReceiptMakerVO vo);
	ArrayList<ReceiptMakerVO> receiptdatatoApprove(ReceiptMakerVO vo);
	ArrayList<ReceiptMakerVO> getViewReceivable(int loanId,String bpType,int bpId, String uId,String amt,int instrumentId,String tdsAmount,String receiptamount);
	ArrayList<PaymentMakerForCMVO> onViewReceivable(PaymentMakerForCMVO paymentMakerForCMVO,int loanId,String bpType,int bpId);
	boolean  saveViewReceivable(ReceiptMakerVO receiptVO);
	String existReceiptData(Object ob);
	String existReceiptForNR(Object ob);
	boolean saveReceiptData(Object ob);
	
    String saveForwardUpdateOnReceipt(ReceiptMakerVO receiptVO);
    boolean updateOnReceiptSave(ReceiptMakerVO receiptVO);
    String checkFesiblityOnForward(ReceiptMakerVO receiptVO);
	int existInsNo(ReceiptMakerVO receiptVO);
	int existInsNForNR(ReceiptMakerVO receiptVO);
	String saveForwardReceiptData(ReceiptMakerVO receiptVO,float amount);
	ArrayList<ReceiptMakerVO> getchequeDetailR(String bankAccount,String accountType);
	ArrayList<ReceiptMakerVO>onAllocatedReceivable(ReceiptMakerVO receiptVO,int loanId,String bpType,int instrumentId);
	String onSaveofReceiptAuthor(ReceiptMakerVO receiptVO);
	boolean deleteReceipt(String instrumentId);
	String getTotalRec(String loanId, String bPType);
	String receiptNoCheckFlag();
	ArrayList receiptPurposeList();
	//change by sachin
	String checkReciptStatus(Object ob);
	//String checkReciptUpdateStatus(Object ob);
	//end by sachin
	String checkReciptStatusFromInventory(Object ob);
	ArrayList getreceiptAmountData(ReceiptMakerVO receiptVO);
	
	ArrayList getBranchIdMicr(String depositBankAccount,String depositBankID,String depositIfscCode,String receiptMode);
	String updateDepositChequeFromReceipt(ReceiptMakerVO receiptVO);
	ArrayList getchargesDetailOnReceipt(ReceiptMakerVO vo);
	boolean saveAllocationReceipt(ReceiptMakerVO vo,String recStatus);
	ArrayList getOtherAddChargesDetailOnReceipt(ReceiptMakerVO receiptVO);
	String saveAuthorAllocationReceipt(ReceiptMakerVO receiptVO);
	String cashDepositFlag();
	String nonCashDepositFlag();
	ArrayList getDefaultBusinessPartnerTypeReceipt(String lbxLoanNoHID,String bpType);
	String getAllocationGridReceipt();
	ArrayList getshowTotalOnReceipt(ReceiptMakerVO receiptVO);
	String getTDSreceiptStatus();	
	String getAllocationChargeCode(String loanId);
	ArrayList getCashDepositAccount();
	String getsaveForwardReceipt();
	ArrayList getchargesDetailBeforeSave(ReceiptMakerVO obj);
	ArrayList saveOrUpdateOrForwardOperations(String compId,String loanId,String instrumentID, String makerId,String bDate,String recStatus,String directForward);
	String getRepoFlag(String lbxLoanNoHID);
	String getInstallmentTypeReciept(String loanid);
	String getChargeReceipt();
	String getEntryGrossAllocationReceipt(String instrumentID);
	ArrayList getDataFromInstrumentList(String instrmtID, String txnID);
	
	}
