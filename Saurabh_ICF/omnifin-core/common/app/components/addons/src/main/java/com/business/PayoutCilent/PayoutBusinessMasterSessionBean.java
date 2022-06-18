package com.business.PayoutCilent;

import java.util.ArrayList;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.payout.dao.PayoutMasterDAO;
import com.payout.vo.ActivityMasterVO;
import com.payout.vo.BPMasterVO;
import com.payout.vo.PaymentReceiptVO;
import com.payout.vo.ScheduleMasterVO;
import com.payout.vo.SchemeBpMapVO;
import com.payout.vo.SchemeVO;
import com.payout.vo.TaxMasterVO;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PayoutBusinessMasterSessionBean implements
		PayoutBusinessMasterSessionBeanRemote {
	private static final Logger logger = Logger.getLogger(PayoutBusinessMasterSessionBean.class.getName());
	PayoutMasterDAO payoutMaster=(PayoutMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(PayoutMasterDAO.IDENTITY); 
	//PayoutMasterDAO payoutMaster=new PayoutMasterDaoImpl();
	// Injects UserTransaction
	  @Resource
	 private UserTransaction userTransaction;
	/*
	 * Code For Activity Master Starts Here By Arun 
	 * */
	public ArrayList<ActivityMasterVO> searchActivityCodeData(
			ActivityMasterVO vo) {
			
		ArrayList<ActivityMasterVO> list=payoutMaster.searchActivityCodeData(vo);
		return list;
	}

	@Override
	public ArrayList<ActivityMasterVO> sourceList() {
		
		ArrayList<ActivityMasterVO> list=payoutMaster.sourceList();
		
		return list;
	}

	@Override
	public String saveActivityCode(ActivityMasterVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............saveActivityCode");
		String resultStr=null;	
        try{
        	userTransaction.begin();
        	 resultStr=payoutMaster.saveActivityCode(vo);
        	 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
        			logger.info("before rollback");
    				userTransaction.rollback();
    			}
    			else
    			{
    				logger.info("before commit");
    				userTransaction.commit();
    			}
        	
		}catch (Exception e) {
			logger.info("Exception: "+e);
		}
		return resultStr;
	}

	@Override
	public String updateActivityCode(ActivityMasterVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............updateActivityCode");
		String resultStr=null;	
		 try{
			 userTransaction.begin(); 
			 resultStr=payoutMaster.updateActivityCode(vo);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
     			logger.info("before rollback");
 				userTransaction.rollback();
 			}
 			else
 			{
 				logger.info("before commit");
 				userTransaction.commit();
 			}
		 }catch (Exception e) {
			 logger.info("Exception: "+e);
		}
		return resultStr;
	}

	@Override
	public ArrayList<ActivityMasterVO> openEditActivity(ActivityMasterVO vo) {
		
		ArrayList<ActivityMasterVO> list=payoutMaster.openEditActivity(vo);
		return list;
		
	}
	/*
	 * Code For Activity Master Ends Here By Arun 
	 * */

	/*
	 * Code For Tax Master Starts Here By Arun 
	 * */
	@Override
	public ArrayList<TaxMasterVO> openEditTax(TaxMasterVO vo) {
		
		ArrayList<TaxMasterVO> list=payoutMaster.openEditTax(vo);
		return list;
	}

	@Override
	public String saveTaxMaster(TaxMasterVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............saveTaxMaster");
		String resultStr=null;
		try{
			 userTransaction.begin(); 
			 resultStr= payoutMaster.saveTaxMaster(vo);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
	}

	@Override
	public ArrayList<TaxMasterVO> searchTaxMasterData(TaxMasterVO vo) {
		
		ArrayList<TaxMasterVO> list=payoutMaster.searchTaxMasterData(vo);
		return list;
	}

	@Override
	public String updateTaxMaster(TaxMasterVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............updateTaxMaster");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr= payoutMaster.updateTaxMaster(vo);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
	}
	/*
	 * Code For Tax Master Ends Here By Arun 
	 * */
	/*
	 * Code For BP Master Starts Here By Arun 
	 * */
	@Override
	public ArrayList<BPMasterVO> openEditBp(BPMasterVO vo) {
		ArrayList<BPMasterVO> list=payoutMaster.openEditBp(vo);
		return list;
	}

	@Override
	public String saveBpMaster(BPMasterVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............saveBpMaster");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr=  payoutMaster.saveBpMaster(vo);
			 if(!CommonFunction.checkNull(resultStr).equalsIgnoreCase("S")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
		}

	@Override
	public ArrayList<BPMasterVO> searchBpMasterData(BPMasterVO vo) {
		ArrayList<BPMasterVO> list=payoutMaster.searchBpMasterData(vo);
		return list;
	}

	@Override
	public String updateBpMaster(BPMasterVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............updateBpMaster");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr=  payoutMaster.updateBpMaster(vo);
			 if(!CommonFunction.checkNull(resultStr).equalsIgnoreCase("S")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
		
	}
	@Override
	public ArrayList<BPMasterVO> openEditBpActivity(BPMasterVO vo) {
		ArrayList<BPMasterVO> list=payoutMaster.openEditBpActivity(vo);
		return list;
	}
	/*
	 * Code For BP Master Ends Here By Arun 
	 * */
	
	/*
	 * Code For Scheme Master Starts Here By Arun 
	 * */
	

	@Override
	public String saveSchemeMaster(SchemeVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............saveSchemeMaster");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr=  payoutMaster.saveSchemeMaster(vo);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;

	}

	@Override
	public ArrayList<SchemeVO> searchScemeMasterData(SchemeVO vo) {
		ArrayList<SchemeVO> list=payoutMaster.searchScemeMasterData(vo);
		return list;
	}

	@Override
	public String updateSchemeMaster(SchemeVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............updateSchemeMaster");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr=  payoutMaster.updateSchemeMaster(vo);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
	
	}

	@Override
	public ArrayList<SchemeVO> openEditSchemeDtl(SchemeVO vo) {
		ArrayList<SchemeVO> list=payoutMaster.openEditSchemeDtl(vo);
		return list;
	}

	@Override
	public ArrayList<SchemeVO> openEditSchemeM(SchemeVO vo) {
		ArrayList<SchemeVO> list=payoutMaster.openEditSchemeM(vo);
		return list;
	}

	/*
	 * Code For Scheme Master Ends Here By Arun 
	 * */
	
	/*
	 * Code For Scheme Bp Map Ends Here By Arun 
	 * */
	@Override
	public ArrayList<SchemeBpMapVO> openEditSchemeBPMap(SchemeBpMapVO vo) {
		ArrayList<SchemeBpMapVO> list=payoutMaster.openEditSchemeBPMap(vo);
		return list;
	}

	@Override
	public String saveSchemeBPMap(SchemeBpMapVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............saveSchemeBPMap");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr=  payoutMaster.saveSchemeBPMap(vo);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
		
	}

	@Override
	public ArrayList<SchemeBpMapVO> searchScemeBpMapData(SchemeBpMapVO vo) {
		ArrayList<SchemeBpMapVO> list=payoutMaster.searchScemeBpMapData(vo);
		return list;
	}

	@Override
	public String updateSchemeBPMap(SchemeBpMapVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............updateSchemeBPMap");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr= payoutMaster.updateSchemeBPMap(vo);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
		
	}
	/*
	 * Code For Scheme Bp Map Ends Here By Arun 
	 * */
	/*
	 * Code For Payment Receipt Starts Here By Arun 
	 * */
	@Override
	public ArrayList<PaymentReceiptVO> searchPaymentReceiptMaker(
			PaymentReceiptVO vo) {
		ArrayList<PaymentReceiptVO> list=payoutMaster.searchPaymentReceiptMaker(vo);
		return list;
	}

	@Override
	public ArrayList<PaymentReceiptVO> openEditPaymentReceiptMaker(
			PaymentReceiptVO vo) {
		ArrayList<PaymentReceiptVO> list=payoutMaster.openEditPaymentReceiptMaker(vo);
		return list;
	}

	@Override
	public String savePaymentReceiptMaker(PaymentReceiptVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............savePaymentReceiptMaker");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr= payoutMaster.savePaymentReceiptMaker(vo);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
		}

	@Override
	public String updatePaymentReceiptMaker(PaymentReceiptVO vo,String recStatus) {
		logger.info("..In PayoutBusinessMasterSessionBean..............updatePaymentReceiptMaker");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr= payoutMaster.updatePaymentReceiptMaker(vo,recStatus);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
		}

	@Override
	public ArrayList<PaymentReceiptVO> allocatePaymentReceiptBehindList(
			PaymentReceiptVO vo) {
		ArrayList<PaymentReceiptVO> list=payoutMaster.allocatePaymentReceiptBehindList(vo);
		return list;
	}

	@Override
	public String saveAllocatePaymentReceipt(PaymentReceiptVO vo,
			ArrayList<String> txnCaseId, ArrayList<String> allocateAmount) {
		// TODO Auto-generated method stub
		return payoutMaster.saveAllocatePaymentReceipt(vo, txnCaseId, allocateAmount);
	}

	@Override
	public String forwardPaymentReceiptMaker(PaymentReceiptVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............forwardPaymentReceiptMaker");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr=  payoutMaster.forwardPaymentReceiptMaker(vo);
			 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;

	}

	@Override
	public ArrayList<PaymentReceiptVO> searchPaymentReceiptAuthor(
			PaymentReceiptVO vo) {
		ArrayList<PaymentReceiptVO> list=payoutMaster.searchPaymentReceiptAuthor(vo);
		return list;
	}

	@Override
	public String savePaymentReceiptAuthor(PaymentReceiptVO vo) {
		logger.info("..In PayoutBusinessMasterSessionBean..............savePaymentReceiptAuthor");
		String resultStr=null;	
		try{
			 userTransaction.begin(); 
			 resultStr= payoutMaster.savePaymentReceiptAuthor(vo);
			 logger.info("resultStr:---"+resultStr);
			 if(!CommonFunction.checkNull(resultStr).equalsIgnoreCase("S")){
	     			logger.info("before rollback");
	 				userTransaction.rollback();
	 			}
	 			else
	 			{
	 				logger.info("before commit");
	 				userTransaction.commit();
	 			}
			 }catch (Exception e) {
				 logger.info("Exception: "+e);
			}
		return resultStr;
		 
	}

	@Override
	public String getTdsRate() {
		
		return payoutMaster.getTdsRate();
	}

	/*
	 * Code For Payment Receipt Ends Here By Arun 
	 * */
	
	/*
	 * Code For Schedule Master starts Here By Ritesh Srivastava 
	 * */
	@Override
	public ArrayList<ScheduleMasterVO> searchScheduleMasterData(
			ScheduleMasterVO vo) {
		ArrayList<ScheduleMasterVO> list=payoutMaster.searchScheduleMasterData(vo);
		return list;
	}
	
public String saveScheduleMaster(ScheduleMasterVO vo) {
	logger.info("..In PayoutBusinessMasterSessionBean..............saveScheduleMaster");
	String resultStr=null;	
	try{
		 userTransaction.begin(); 
		 resultStr=  payoutMaster.saveScheduleMaster(vo);
		 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
     			logger.info("before rollback");
 				userTransaction.rollback();
 			}
 			else
 			{
 				logger.info("before commit");
 				userTransaction.commit();
 			}
		 }catch (Exception e) {
			 logger.info("Exception: "+e);
		}
	return resultStr;
	
	}
public ArrayList<ScheduleMasterVO> openEditScheduleMaster(
		ScheduleMasterVO scheduleMasterVO){
	return payoutMaster.openEditScheduleMaster(scheduleMasterVO);
}

public String updateScheduleMaster(ScheduleMasterVO masterVO){
	logger.info("..In PayoutBusinessMasterSessionBean..............updateScheduleMaster");
	String resultStr=null;	
	try{
		 userTransaction.begin(); 
		 resultStr=  payoutMaster.updateScheduleMaster(masterVO);
		 if(CommonFunction.checkNull(resultStr).equalsIgnoreCase("notSaved")){
     			logger.info("before rollback");
 				userTransaction.rollback();
 			}
 			else
 			{
 				logger.info("before commit");
 				userTransaction.commit();
 			}
		 }catch (Exception e) {
			 logger.info("Exception: "+e);
		}
	return resultStr;
	
}
	/*
	 * Code For Schedule Master Ends Here By Ritesh Srivastava 
	 * */
/*
 * Code for Report
 * */

@Override
public ArrayList getReportFormat() {

	return payoutMaster.getReportFormat();
}

/*
 * Code for Report
 * */
}
