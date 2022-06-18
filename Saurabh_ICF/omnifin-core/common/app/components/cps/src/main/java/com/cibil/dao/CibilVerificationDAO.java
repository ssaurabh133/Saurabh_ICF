package com.cibil.dao;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import com.cibil.vo.CibilVerificationVO;
import com.cm.vo.DisbursalSearchVO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.communication.engn.vo.EmailVO;
import com.cp.vo.OtherChargesPlanVo;
import com.cm.vo.SecurityDepositVO;
import com.cp.vo.ApplicantTypeVO;
import com.cp.vo.BuyerSupplierOtherVo;
import com.cp.vo.BuyerVo;
import com.cp.vo.ChargeVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.CommonPageSecVo;
import com.cp.vo.ConsumerVo;
import com.cp.vo.CreditProcessingCustomerEntryVo;
import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.cp.vo.CreditProcessingNotepadVo;
import com.cp.vo.DealDisbursalVo;
import com.cp.vo.ExistingAssetVo;
import com.cp.vo.FieldVerificationVo;
import com.cp.vo.FleetDetailsVo;
import com.cp.vo.LeaddetailDealVo;
import com.cp.vo.LimitEnhancementVo;
import com.cp.vo.LoanedProductVo;
import com.cp.vo.QueryProcessingVo;
import com.cp.vo.RelationalManagerVo;
import com.cp.vo.LinkCustomerVo;
import com.cp.vo.ScoringVO;
import com.cp.vo.SpecialConditionVo;
import com.cp.vo.Viability;
import com.masters.vo.GcdGroupMasterVo;
import com.cp.vo.TermSheetVo;
import com.cibil.vo.CrifVO;


public interface CibilVerificationDAO 
{
	String IDENTITY="CIBILVER";

	ArrayList getCVGridList(CibilVerificationVO vo);
	ArrayList getCblViewGridList(CibilVerificationVO vo);
	ArrayList getCblViewCustomerGridList(CibilVerificationVO vo);
	ArrayList SaveRecord(String dealID, int customerId, String businessDate);

	boolean updateResString(String cibilId, String resString); 
	String GenerateView(String cibilId);
	CibilVerificationVO getIPconfig();	
	String getCibilId(String dealId, String customerID);
	String getCibilResponse(String cibilId);
	String getCibilRepotFlag();
	String getCibilProvider();
	//ArrayList getRoleList(String leadId);
	//pooja code for crif
	//String saveCrifDetail(String dealID,String customerId,CrifVO CrifVO);
	//String getCrifRequestXML(String dealID, String customerId,String businessDate);
    public String sendCrifRequest(CrifVO CrifVO) throws Exception;
	String getCRIFUserId();
	String getCRIFPassword();
	ArrayList getpreDealGridList(CibilVerificationVO vo);
	ArrayList SaveRecordPreDeal(String leadId, int customerId,
			String businessDate);
	boolean updatePreDealResString(String cibilId, String resString);
	ArrayList getRoleList(String leadId);
	
	
}

