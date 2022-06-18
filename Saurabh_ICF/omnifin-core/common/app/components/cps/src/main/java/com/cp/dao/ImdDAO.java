package com.cp.dao;

import java.util.ArrayList;

import com.cm.vo.PaymentMakerForCMVO;
import com.cp.vo.ImdMakerVO;

public interface ImdDAO {

	
	String IDENTITY="IMD";
	ArrayList<ImdMakerVO> imdMakerGrid(ImdMakerVO vo);
	ArrayList<ImdMakerVO> imdDetailsGrid(ImdMakerVO vo,String dealId);
	ArrayList<ImdMakerVO> imdAuthorGrid(ImdMakerVO vo);
	boolean saveImdData(Object ob);
	String existReceiptData(Object ob);
	String existReceiptForNR(Object ob);
	//change by sachin
	String checkReciptStatus(Object ob);
	String checkReciptUpdateStatus(Object ob);
	//end by sachin
	ArrayList<ImdMakerVO> getImdList(ImdMakerVO vo);
	boolean deleteReceipt(String instrumentId);
	String getTotalRec(String loanId, String bPType);
	String receiptNoCheckFlag();
	ArrayList receiptPurposeList();
	public String getinstrumentId();
	String saveForwardReceiptData(ImdMakerVO receiptVO,float amount);
	boolean saveForwardUpdateOnReceipt(ImdMakerVO receiptVO);
	ArrayList<ImdMakerVO> receiptdatatoApprove(ImdMakerVO vo);
	String onSaveofImdAuthor(ImdMakerVO receiptVO);
	ArrayList getchargesDetail(String dealCap,String dealId);
	ArrayList getAlocChargesDetail(String instrument,String dealId);
	boolean  saveViewReceivable(ImdMakerVO receiptVO); 	  
    boolean updateOnReceiptSave(ImdMakerVO receiptVO);
    String checkFesiblityOnForward(ImdMakerVO receiptVO);
	int existInsNo(ImdMakerVO receiptVO);
	int existInsNForNR(ImdMakerVO receiptVO);
	ArrayList<ImdMakerVO> getchequeDetailR(String bankAccount,String accountType);
	ArrayList<ImdMakerVO>onAllocatedReceivable(ImdMakerVO receiptVO,int loanId,String bpType,int instrumentId);
	String checkReciptStatusFromInventory(ImdMakerVO vo);
	ArrayList<ImdMakerVO> getpreDealImdList(ImdMakerVO vo);
	
}
