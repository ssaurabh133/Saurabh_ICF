package com.business.PayoutCilent;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.payout.vo.ActivityMasterVO;
import com.payout.vo.BPMasterVO;
import com.payout.vo.PaymentReceiptVO;
import com.payout.vo.ScheduleMasterVO;
import com.payout.vo.SchemeBpMapVO;
import com.payout.vo.SchemeVO;
import com.payout.vo.TaxMasterVO;

@Remote
public interface PayoutBusinessMasterSessionBeanRemote {
	
	String REMOTE_IDENTITY="PAYOUTBUSSINESSMASTERREMOTE";
	
	/*
	 * Code For Activity Master Starts Here By Arun 
	 * */
	ArrayList<ActivityMasterVO> searchActivityCodeData(ActivityMasterVO vo);
	ArrayList<ActivityMasterVO> sourceList();
	String saveActivityCode(ActivityMasterVO vo);
	String updateActivityCode(ActivityMasterVO vo);
	ArrayList<ActivityMasterVO> openEditActivity(ActivityMasterVO vo);
	/*
	 * Code For Activity Master Ends Here By Arun 
	 * */
	
	/*
	 * Code For Tax Master Starts Here By Arun 
	 * */
	ArrayList<TaxMasterVO> searchTaxMasterData(TaxMasterVO vo);
	String saveTaxMaster(TaxMasterVO vo);
	ArrayList<TaxMasterVO> openEditTax(TaxMasterVO vo);
	String updateTaxMaster(TaxMasterVO vo);
		
	/*
	 * Code For Tax Master Ends Here By Arun 
	 * */
	
	/*
	 * Code For BP Master Starts Here By Arun 
	 * */
	ArrayList<BPMasterVO> searchBpMasterData(BPMasterVO vo);
	String saveBpMaster(BPMasterVO vo);
	ArrayList<BPMasterVO> openEditBp(BPMasterVO vo);
	String updateBpMaster(BPMasterVO vo);
	ArrayList<BPMasterVO> openEditBpActivity(BPMasterVO vo);
	/*
	 * Code For BP Master Ends Here By Arun 
	 * */
	/*
	 * Code For Scheme Master Starts Here By Arun 
	 * */
	ArrayList<SchemeVO> searchScemeMasterData(SchemeVO vo);
	String saveSchemeMaster(SchemeVO vo);
	ArrayList<SchemeVO> openEditSchemeM(SchemeVO vo);
	ArrayList<SchemeVO> openEditSchemeDtl(SchemeVO vo);
	String updateSchemeMaster(SchemeVO vo);
	//ArrayList<BPMasterVO> openEditBpActivity(SchemeVO vo);
	/*
	 * Code For Scheme Master Ends Here By Arun 
	 * */
	/*
	 * Code For Scheme Bp Map Starts Here By Arun 
	 * */
	ArrayList<SchemeBpMapVO> searchScemeBpMapData(SchemeBpMapVO vo);
	String saveSchemeBPMap(SchemeBpMapVO vo);
	ArrayList<SchemeBpMapVO> openEditSchemeBPMap(SchemeBpMapVO vo);
	String updateSchemeBPMap(SchemeBpMapVO vo);
	
	/*
	 * Code For Scheme Bp Map Ends Here By Arun 
	 * */
	
	/*
	 * Code For Payment receipt  Starts Here By Arun 
	 * */
	
	ArrayList<PaymentReceiptVO> searchPaymentReceiptMaker(PaymentReceiptVO vo);
	String savePaymentReceiptMaker(PaymentReceiptVO vo);
	ArrayList<PaymentReceiptVO> openEditPaymentReceiptMaker(PaymentReceiptVO vo);
	String updatePaymentReceiptMaker(PaymentReceiptVO vo,String recStatus);
	ArrayList<PaymentReceiptVO> allocatePaymentReceiptBehindList(PaymentReceiptVO vo);
	String saveAllocatePaymentReceipt(PaymentReceiptVO vo,ArrayList<String> txnCaseId,ArrayList<String> allocateAmount);
	String forwardPaymentReceiptMaker(PaymentReceiptVO vo);
	ArrayList<PaymentReceiptVO> searchPaymentReceiptAuthor(PaymentReceiptVO vo);
	String savePaymentReceiptAuthor(PaymentReceiptVO vo);
	String getTdsRate();
	/*
	 * Code For  Payment receipt   Starts Here By Arun 
	 * */
	
	/* Code For Schedule Master Starts Here By Ritesh Srivastava 
	 * */
	ArrayList<ScheduleMasterVO> searchScheduleMasterData(ScheduleMasterVO vo);
	String saveScheduleMaster(ScheduleMasterVO scheduleMasterVO);
	
	ArrayList<ScheduleMasterVO> openEditScheduleMaster(
			ScheduleMasterVO scheduleMasterVO);
	
	String updateScheduleMaster(ScheduleMasterVO masterVO);
	/*
	 * Code For Schedule Master Ends Here By Ritesh Srivastava 
	 * */
	 /*
	  * Code for Report
	  * */
	  ArrayList getReportFormat();
	 /*
	  * Code for Report
	  * */
}
