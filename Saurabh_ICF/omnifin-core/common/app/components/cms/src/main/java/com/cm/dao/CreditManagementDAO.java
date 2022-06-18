package com.cm.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import com.cm.vo.*;
import com.cp.vo.ChargeVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.DocumentsVo;
import com.cp.vo.CollateralVo;
import com.cp.vo.RepayScheduleVo;

public interface CreditManagementDAO {   
	//change by sachin
	 String IDENTITY="CMD";
	//end by sachin
	
	ArrayList selectAsset(String loanId, String source);
	boolean rejectWaiveOffAuthor(Object ob);

	ArrayList<InstructionCapMakerVO> getretriveCutInsValues(int id);
	public String getTxnAdviceId();

	boolean deleteAsset(String[] assetId, String loanId);
    int checkAllocation(ReceiptMakerVO receiptVO);


	boolean deleteAssetInsuranceID(String[]LoanID,String[]AssetID);

     boolean updateInstallPlan(InstallmentPlanForCMVO ipvo);
    
	ArrayList<InstructionCapMakerVO> getListOfValuesforAuthor(int loanID);


	ArrayList<InstructionCapMakerVO> getListOfValuesforAuthor();
	ArrayList<InstructionCapMakerVO> getListOfValuesforInstrumentMaker();



	boolean updateIndiHoldInstrumentUnChecked(String[] uncheckedValueList,
			String[] uncheckedStatusList,
			InstructionCapMakerVO instructionCapMakerVO, String[] uncheckedholdList);

	boolean updateIndiReleaseInstrumentUnChecked(String[] uncheckedValueList,
			String[] uncheckedStatusList,
			InstructionCapMakerVO instructionCapMakerVO, String[] uncheckedholdList);

	


			
			ArrayList<InstructionCapMakerVO> getbussinessPartnerList();
			
			
			
			


			
			String updateDepositCheque(String[] tdsAmountListList,
					String[] instrumentIDList,
					String[] checkedinstrumentNoList,
					String[] checkeddateList,
					String[] checkedinstrumentAmountList,
					String[] checkedstatusList,
					String[] checkedlbxBPTypeHIDList,
					String[] checkedlbxBPNIDList,
					String[] checkedlbxBankIDList,
					String[] checkedlbxBranchIDList,
					ChequeStatusVO chequeStatusVO, String status,
					String[] checkedlbxReasonHIDList, String lbxBankID,
					String lbxBranchID, String micr, String ifscCode,
					String bankAccount, String[] pdcInstrumentIdList, String pdcFlag, String instrumentType,String[] checkedvalueDateList);
			ArrayList<AssetVerificationVO> getAssetList();



	//manas end
	//Amit
	
	


	String getGroupExposureLimitCheck(DisbursalMakerVO vo);
	String getCustomerExposureLimitCheck(DisbursalMakerVO vo);
	
	//ArrayList<Object> getBusinessPartnerTypeList();
	
   
	
   
	
	

	
	String checkDocMatrix(DocumentsVo vo);
	boolean saveDocumentPOD(Object vo);
	String savePODAuthor(PODAuthorVO vo);
	
	ArrayList<DocumentsVo> getApplicationDocuments(String entityType,String commonId,String stage, String txnType,String recStatus, String source);
	ArrayList<DocumentsVo> getAllApplicantDocs(String entityType,String commonId,String stage, String txnType,String recStatus);
	ArrayList<DocumentsVo> getAssetCollateralDocuments(String entityType,String commonId,String stage, String txnType,String recStatus);
	
	
	

	
	
	
	ArrayList<RepayScheduleVo> getNewRepaySchedulePartPayment(RepayScheduleVo vo, String loanId, String reschId);
	ArrayList generateReschCharges(String lbxLoanNoHID, double partPaymentAmt,String reschDate, int functionId, int companyId);
	
	String deferralMakerFeasibility(String lbxLoanNoHID, String businessDate);
	ArrayList<DeferralMakerVo> retriveDeferralValues(String lbxLoanNoHID);
	
	ArrayList<RepayScheduleVo> getNewRepayScheduleDeferral(RepayScheduleVo vo, String loanId, String reschId);
	
	


	
	
	ArrayList<InstallmentPlanForCMVO> viewNewInstallmentPlanPartPayment(String loanId, String reschId,String installNo);
	ArrayList<InstallmentPlanForCMVO> viewNewInstallmentPlanRepricing(String loanId, String reschId);
	boolean saveNewInstallPlan(InstallmentPlanForCMVO ipvo);
	//Amit end
	
	ArrayList getDealCharges(String loanId);
	ArrayList<Object> getAssetsAll();
	
	boolean generatePlaning(String loanId,String userId,String bgDate);
	
	ArrayList getUploadSummary(String makerId,HttpServletRequest request);	
	 boolean downLoadErrorLog(HttpServletRequest req,HttpServletResponse res,String makerid);

	boolean saveChargesInCm(ChargeVo vo);


	//RAVINDRA manual Advice Start
	
	public boolean rejectManualAdviceByAuthor(Object ob);
	
	
	
	//RAVINDRA manual Advice endo manualAdviceCreationVo);
	
	//..........Abhimanyu process.........
	
	
	boolean startEodBodProcess();
	
	boolean startBodProcess();
	
	
	
	ArrayList getNoOfDisbInloan(String loanId);
	

	

	ArrayList<InstructionCapMakerVO> insertListECS(
			InstructionCapMakerVO instructionCapMakerVO);





	

	






	
	String updateInstrumentDetailForPayment(String[] tdsAmountListList,
			String[] instrumentIDList, String[] checkedinstrumentNoList,
			String[] checkeddateList, String[] checkedinstrumentAmountList,
			String[] checkedstatusList, String[] checkedlbxBPTypeHIDList,
			String[] checkedlbxBPNIDList, String[] checkedlbxBankIDList,
			String[] checkedlbxBranchIDList, ChequeStatusVO chequeStatusVO,
			String status, String[] checkedlbxReasonHIDList,
			String[] pdcInstrumentIdList, String pdcFlag,
			String instrumentType, String[] depositBankIdList,
			String[] depositBranchIdList, String[] depositMicrCodeList,
			String[] depositIfscCodeList, String[] depositBankAccountList,String[] checkedvalueDateList,String[] checkedReasonRemarksList);


	boolean downLoadErrorLogReceipt(HttpServletRequest request,HttpServletResponse response, String userId);

	

	//String loanViewerClosureCheck(String loanIDMain);

	
	//	ArrayList chequeStatusbyloanViewer(String loanId, int currentPageLink);
	boolean updateLoginStatus(String userId);
    /*For asset/collateral*/
	/**/

	ArrayList<RepayScheduleVo> getOldRepayScheduleDisbursal(String loanId);
	boolean checkExistRecord(String[] instrumentIDList, String[] checkedstatusList, String status);
//Prashant
	String getProductTypeInCM(String loanId);
	
	//Changes By Amit Starts
	ArrayList getAdditionalDocs(String entityType, String commonId, String stage, String txnType, String recStatus);
	
	//Changes By Amit Ends
	
	//Nishant space starts
	String getLoanAmount(String loanId);
	//Nishant space end
	
	//start by sachin
	ArrayList<DocumentsVo> getApplicationDocumentsForDeal(String entityType,String commonId,String stage, String recStatus);
	ArrayList getAdditionalDocsForDeal(String entityType, String commonId, String stage, String recStatus);
	ArrayList<DocumentsVo> getAllApplicantDocsForDeal(String entityType,String commonId, String txnType,String recStatus);
	ArrayList<DocumentsVo> getAssetCollateralDocumentsForDeal(String entityType,String commonId, String txnType,String recStatus);
	String saveDocInTemp(Object vo);
	boolean forwardDocumentPOD(Object vo);
	//end by sachin
	String documentForwardOrNotAtDocumentCollection(String commonId,String stage);
	ArrayList<DocumentsVo> getDocumentsForAllApplicationDocAtDocCollection(String entityType,String commonId,String stage, String txnType,String recStatus, String source);
	String getDocFromTempOrNotAtDocCollection(String entityType,String commonId,String stage, String txnType);
	ArrayList getAdditionalDocsForAllDocTypeAtDocCollection(String entityType, String commonId, String stage, String txnType, String recStatus,String source);
	
	ArrayList<DocumentsVo> getAllApplicantDocsForTempAtDocCollection(String entityType,String commonId,String stage, String txnType,String recStatus,String source);
	ArrayList<DocumentsVo> getAssetCollateralDocumentsForTempAtDocCollection(String entityType,String commonId,String stage, String txnType,String recStatus,String source);

	//Manish
	boolean updateServiceBranch(LoanInitAuthorVo vo, String loanNo);
	ArrayList getServiceBranch(String loanId);

	boolean checkDocuments(String[] instrumentIDList,
			String[] checkedstatusList, String status);	
	String getEditDueDateStatus(String lbxLoanNoHID);
	 String getRepayEffDate(String lbxLoanNoHID, String reschID);
	 ArrayList selectIndustryDetails(String natureOfBus, String lbxIndustry);
		
		public ArrayList<LoanDetailForCMVO> selectSblGblWaiverMakerSearch(String loanId,String disbursalNo);//added by indrajeet
	    public boolean saveSblGblWaiver(LoanDetailForCMVO vo);//added by indrajeet
	    public ArrayList<LoanDetailForCMVO> searchSblAuthor(String makerId);//added by indrajeet
	    public boolean saveSblGblWaiverAuthor(LoanDetailForCMVO vo);//added by indrajeet
	    public ArrayList<LoanDetailForCMVO> searchSblAuthorValues(String loanNo,String disbursalId);//added by indrajeet
	    public ArrayList<LoanDetailForCMVO> searchSblGblAuthorValues(String loanId,String disbursalId);//added by indrajeet
}
