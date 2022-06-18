package com.cp.dao;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cm.vo.DisbursalSearchVO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.cp.vo.OtherChargesPlanVo;
import com.cm.vo.SecurityDepositVO;
import com.cp.vo.ApplicantTypeVO;
import com.cp.vo.BuyerSupplierOtherVo;
import com.cp.vo.BuyerVo;
import com.cp.vo.ChargeVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CollateralVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.CommonPageSecVo;
import com.cp.vo.ConsumerVo;
import com.cp.vo.CpInsuranceVo;
import com.cp.vo.CreditProcessingCustomerEntryVo;
import com.cp.vo.CreditProcessingNotepadVo;
import com.cp.vo.DealDisbursalVo;
import com.cp.vo.ExistingAssetVo;
import com.cp.vo.FacilityDetailsVo;
import com.cp.vo.FieldVerificationVo;
import com.cp.vo.LeaddetailDealVo;
import com.cp.vo.LoanedProductVo;
import com.cp.vo.RelationalManagerVo;
import com.cp.vo.LinkCustomerVo;
import com.cp.vo.ReprocessingDealVo;
import com.cp.vo.ScoringVO;
import com.cp.vo.SpecialConditionVo;
import com.cp.vo.UnderwriterApprovalVo;
import com.cp.vo.Viability;
import com.masters.vo.GcdGroupMasterVo;
import com.cp.vo.TermSheetVo;
import com.cp.vo.DealMovementVo;
import com.cp.vo.FacilityDetailsVo;


public interface UnderwritterDao {
	



	
	 String IDENTITY="UWA"; 		// changed by asesh
	 String getMaxPolicyApproval(String dealId);
	 public boolean checkFinalAmount(UnderwriterApprovalVo docVo,String userId) ;
	 String getUserPolicyApproval(String user1);
	
}

