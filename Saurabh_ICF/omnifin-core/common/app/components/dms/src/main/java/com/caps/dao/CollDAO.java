package com.caps.dao;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.VO.CustomerSaveVo;
import com.caps.VO.ActionCodeMasterVo;
import com.caps.VO.AllocationMasterVo;
import com.caps.VO.CRFollowUpTrailsDtlVO;
import com.caps.VO.ClassificationProcessVo;
import com.caps.VO.CollCommonVO;
import com.caps.VO.CollCustomerAddressVo;
import com.caps.VO.ContactRecordingFollowUpVO;
import com.caps.VO.EscalationMatrixVo;
import com.caps.VO.ForeClosureVo;
import com.caps.VO.PaymentDetailsVo;
import com.caps.VO.QueueCodeMasterVo;
import com.caps.VO.ReallocationGridVo;
import com.caps.VO.ViewpayableRecievableVo;
import com.caps.VO.CollectionSummaryVO;
import com.caps.VO.ContactRecordingSearchVO;
import com.caps.VO.ReallocationMasterVo;
import com.cm.vo.ClosureVO;



public interface CollDAO {
	
	String IDENTITY="COLLD";
	//Code Start By Anil Yadav For Action Code Master
	ArrayList <ActionCodeMasterVo> searchActionCodeData(ActionCodeMasterVo VO);
	boolean insertActionCodeMaster(ActionCodeMasterVo VO);
	boolean updateActionCodeData(ActionCodeMasterVo VO);

	//Code Start By Anil Yadav For Queue Code Master
	ArrayList <QueueCodeMasterVo> searchQueueCodeData(QueueCodeMasterVo VO);
	ArrayList<QueueCodeMasterVo> NPAStageList();
	ArrayList<QueueCodeMasterVo> CustCategoryList();
	ArrayList<QueueCodeMasterVo> ProductList();
	String checkQueueCodeMaster(QueueCodeMasterVo VO);
	boolean insertQueueCodeMaster(QueueCodeMasterVo VO);
	boolean updateQueueCodeData(QueueCodeMasterVo VO);
	ArrayList <QueueCodeMasterVo> editQueueCodeData(String queue);
	//Anil Code Ends Here
	
	//Code Start By Anil Yadav For Escalation Matrix
    ArrayList<EscalationMatrixVo> escNPAstage();
    ArrayList<EscalationMatrixVo> escCustcategory();
    ArrayList<EscalationMatrixVo> escProduct();
    ArrayList<EscalationMatrixVo> escalationGrid(EscalationMatrixVo escalationVo);  
    boolean updateEscalationMatrix(EscalationMatrixVo VO, String[] checkbox);
    String queueActionDate(String queueadate);
       
    //Anil Code Ends Here

      //KANIKA CODE FOR ALLOCATION

      ArrayList<AllocationMasterVo> getQueueList();
      String saveQueueAllocation(AllocationMasterVo collVo, String[] percentage,int total,String[] queueuser) throws SQLException;
      ArrayList<AllocationMasterVo> searchQueueAllocationData(AllocationMasterVo allocationVo);
      ArrayList<AllocationMasterVo> searchQueueAllocationEdit(String userId);
      ArrayList<AllocationMasterVo> searchQueueEdit(String userId);
      String modifyqueueAllocation(AllocationMasterVo collVo, String[] checkbox,String[] percentagebox,int total) throws SQLException;
  	String calcpercentage(String[] checkbox) throws SQLException;
  	
  	  ArrayList <AllocationMasterVo> searchAllocationData(Object ob,HttpServletRequest request);
     //END OF KANIKA FOR ALLOCATION
     //CODE FOR REALLOCATION

     ArrayList<ReallocationMasterVo> custcategory();
     ArrayList<ReallocationMasterVo> npastage();
     ArrayList<ReallocationMasterVo> product();
     ArrayList<ReallocationGridVo> reallocationGrid(ReallocationMasterVo reallocVo);
     boolean updatereallocation(ReallocationGridVo reallocVo, String[] checkbox);
	 

     //END OF KANIKA CODE OF REALLOCATION
                        
     //Contact Recording Code Start Here (Arun)
     ArrayList<ContactRecordingSearchVO> contactRecordingSearchList(ContactRecordingSearchVO contactRecordingSearchVO);
     ArrayList<ContactRecordingSearchVO> supervisorReviewSearchList(ContactRecordingSearchVO contactRecordingSearchVO);
     ArrayList <ActionCodeMasterVo>  actionCodeList();
     ArrayList<ContactRecordingFollowUpVO> customerInfoList(ContactRecordingFollowUpVO vo);
     ArrayList<ContactRecordingFollowUpVO> customerContactInfoList(ContactRecordingFollowUpVO vo);
     ArrayList<ContactRecordingFollowUpVO> loanInfoList(ContactRecordingFollowUpVO vo);
     boolean saveFollowUpTrail(ContactRecordingFollowUpVO vo);
     ArrayList<ContactRecordingFollowUpVO> followUpGridList(ContactRecordingFollowUpVO vo);
     ArrayList<ContactRecordingSearchVO> adhocContactRecordingSearchList(ContactRecordingSearchVO contactRecordingSearchVO);// by nazia
     //Contact Recording Code Start Here (Arun)
     
     //Anil code start here for 
     ArrayList <CollCustomerAddressVo> searchClassificationData(String loanId);
	boolean saveCustomerAddress(CollCustomerAddressVo vo);
	ArrayList<CollCustomerAddressVo>  addresstype() throws Exception;

	ArrayList<PaymentDetailsVo> paymentDetails(String loanId);
	ArrayList<PaymentDetailsVo> bounceDetails(String loanId);

	public ArrayList<CRFollowUpTrailsDtlVO> followUpTrailsDtlData(CRFollowUpTrailsDtlVO vo) throws Exception;
	

	public ArrayList<ForeClosureVo> foreClosureData(String companyId, String loanId, String effectiveDate, String closureType);
	
	ArrayList<ClassificationProcessVo> getClassificationData(String businessDate);
	ArrayList<ClassificationProcessVo> startClasificationProcess(String businessDate,String maker_id);
	String checkPriority(QueueCodeMasterVo queueCodeMasterVo);
	ArrayList<ViewpayableRecievableVo> viewPayableReceivable(ViewpayableRecievableVo vo, String loanId);


	ArrayList<CollCommonVO> getBankInfo(String loanId);
	ArrayList<CollCustomerAddressVo> getGuarantorInfo(String loanId);
	ArrayList<CollCustomerAddressVo> getSuplierInfo(String loanId);
	 ArrayList<ContactRecordingFollowUpVO> assetInfoList(ContactRecordingFollowUpVO vo);

	 void esclatedCaseSendMailToUserAuto();
	ArrayList<CollCustomerAddressVo> defaultcountry();
	/*Code By Arun on 21/06/2012 Starts Here*/
	ArrayList<CustomerSaveVo> getRefrencesInfo(String loanId);
	ArrayList<CollCustomerAddressVo> getCoApplicantInfo(String loanId);
	/*Code By Arun on 21/06/2012 Ends Here*/
	/*Code By Arun on 25/06/2012 Starts Here*/
	CollectionSummaryVO getCollectionSummary(CollectionSummaryVO collectionSummaryVO);
	/*Code By Arun on 25/06/2012 Ends Here*/
	Object vehicleInfoList(ContactRecordingFollowUpVO contactRecordingVO);
	String updateGetAutoRefreshFlag(String refreshFlag);
	ArrayList<CRFollowUpTrailsDtlVO> getActionResolutionDtl(CRFollowUpTrailsDtlVO vo);
	String SaveActionResolutionDtl(CRFollowUpTrailsDtlVO vo);
	String getActionDesc(String actionId);
	ArrayList<CRFollowUpTrailsDtlVO> getResolutionFollowUpType();
	ArrayList<CRFollowUpTrailsDtlVO> getResolutionFollowUpStatus();
	ArrayList<ClosureVO> getDuesRefundsList(String companyId, String loanId,
			String effectiveDate, String closureType, String source);
	public ArrayList<Object> getContactListApplicant();
	
}
