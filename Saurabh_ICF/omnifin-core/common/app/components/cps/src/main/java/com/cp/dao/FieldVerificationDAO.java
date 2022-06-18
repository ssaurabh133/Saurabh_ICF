package com.cp.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cp.vo.FieldVerificationVo;

public interface FieldVerificationDAO {
	String IDENTITY="FV"; 
	ArrayList<FieldVerificationVo> initiatedVerificationDealDetail(FieldVerificationVo vo);
	
	ArrayList<FieldVerificationVo> searchFieldDetail(FieldVerificationVo vo,String dealId);
	ArrayList<FieldVerificationVo> searchFieldVerInitiation(FieldVerificationVo vo,String dealId);
	ArrayList<FieldVerificationVo> searchCollDetail(FieldVerificationVo vo,String dealId);
	ArrayList<FieldVerificationVo> searchTradeDetail(FieldVerificationVo vo,String dealId);
	boolean checkDealID(Object ob);
	ArrayList<FieldVerificationVo> searchDealForCapture(FieldVerificationVo vo);
	ArrayList<FieldVerificationVo> searchDealForComCapture(FieldVerificationVo vo,HttpServletRequest request);
	int insertFieldVerCapture(Object ob);
	boolean forwardRec(String dealId,String varID,String userId,String businessDate);
	ArrayList checkCustomerId(String dealId);
	public boolean updateFieldVerCapture(Object ob);
	ArrayList<FieldVerificationVo> getCustomerDetail(String dealId,String makerId);
	//ArrayList getCustomerComDetail(String dealId); 
    ArrayList getDetail(String customer_id,String deal_id,String varSub);
	ArrayList<FieldVerificationVo> getDefaultCaptureData(FieldVerificationVo vo);
	ArrayList<FieldVerificationVo> getDefaultCompletionData(FieldVerificationVo vo);
	public boolean modifyFieldRemarks(Object ob, String dealId);
	ArrayList getAddressForField(String customer_id);
//	ArrayList searchDealDetailforBuyerSupp(FieldVerificationVo vo,
//			HttpServletRequest request);
//	ArrayList searchDealDetailforBuyerSuppAuthor(FieldVerificationVo vo,
//			HttpServletRequest request);
	ArrayList getGridListForAddress(String dealId, String customar_id);
	ArrayList getAddressDetail(String customer_id,String deal_id,String addressType);
	boolean deleteAddressFieldRecord(String dealId, String customerId,String verificationId, String addressTypeList);
	ArrayList getTradeHeader(String dealId);
	ArrayList getComCustomerDetail(String dealId,String recStats);
	
	ArrayList getVerificationList(String dealId);

	boolean insertFieldVerInitiation(FieldVerificationVo vo);

	ArrayList showVerificationList(String dealId);

	ArrayList getVerifList(String dealId, String entityId, String verificationId);

	ArrayList getQuestList(String dealId, String entityId,String verificationId);

	ArrayList getAddressWithDeal(String entityId);

	boolean insertQuestionDetails(FieldVerificationVo vo);

	ArrayList getCommonListList(String dealId, String entityId,
			String verificationId);

	boolean updateQuestionDetails(FieldVerificationVo vo);

	ArrayList getCompletionList(String dealId,String functionId);

	boolean completionAuthorVerf(FieldVerificationVo vo);

	ArrayList getViewCommonListList(String dealId, String fieldVerificationId);

	ArrayList getViewQuestList(String dealId, String fieldVerificationId);

	ArrayList getVerifMethodListList();

	boolean authorAllWavedVerifInit(FieldVerificationVo vo);

	ArrayList initiatedVerificationLoanDetail(FieldVerificationVo vo);

	ArrayList getVerificationListAtCM(String loanId);

	ArrayList showVerificationListAtCM(String loanId);

	boolean insertFieldVerInitiationAtCM(FieldVerificationVo vo);

	boolean authorAllWavedVerifInitAtCM(FieldVerificationVo vo);

	ArrayList getDefaultCaptureDataAtCM(FieldVerificationVo vo);

	ArrayList getVerifListAtCM(String loanId, String entityId,String verificationId);

	ArrayList getCommonListListAtCM(String loanId, String entityId,String verificationId);

	ArrayList getQuestListAtCM(String loanId, String entityId,String verificationId);

	boolean insertQuestionDetailsAtCM(FieldVerificationVo vo);

	int insertFieldVerCaptureAtCM(Object vo);

	boolean updateQuestionDetailsAtCM(FieldVerificationVo vo);

	boolean modifyFieldRemarksAtCM(Object vo, String loanId);

	ArrayList searchDealForCaptureAtCM(FieldVerificationVo vo);

	ArrayList getDefaultCompletionDataAtCM(FieldVerificationVo vo);

	ArrayList searchVerificationCompletionAtCM(FieldVerificationVo vo);

	ArrayList getCompletionListAtCM(String loanId);

	ArrayList getViewCommonListListAtCM(String loanId,String fieldVerificationUniqueId);

	boolean completionAuthorVerfAtCM(FieldVerificationVo vo);

	boolean getDealStatus(String dealId);	
	
	ArrayList<Object> fetchChildDocs(String txnDocId, String dealId,String txnType);

	ArrayList verificationCompletionDealDetail(FieldVerificationVo vo);

	boolean insertVerCompletionModule(FieldVerificationVo vo);

	ArrayList showVerificationListCompletionModule(String dealId);

	boolean verifCompletionModule(FieldVerificationVo vo);
	ArrayList getBalanceDay(String productId,String business,String schemeId);
	//amandeep changes for RCU
		ArrayList getReceivedDocs(String dealId,String bpType);
		ArrayList getVerifMethodListRCU();
		boolean updateRCUDocs(String hidRcuStatusStringValue,String hidRcuCommentStringValue,String hidDOCIDString,String hidRcuChildIDStringValue , FieldVerificationVo vo);
		ArrayList getReceivedDocsSaved(String dealId,String bpType);
		ArrayList getReceivedDocsAtLoan(String loanId,String bpType);
		ArrayList getReceivedDocsSavedAtLoan(String loanId,String bpType);
		//amandeep changes end for RCU
	}
