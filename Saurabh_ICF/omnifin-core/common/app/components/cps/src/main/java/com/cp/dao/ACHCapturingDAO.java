package com.cp.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cm.vo.DisbursalSearchVO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.cp.vo.OtherChargesPlanVo;
import com.cm.vo.SecurityDepositVO;
import com.cp.vo.ACHCapturingVo;
import com.cp.vo.ApplicantTypeVO;
import com.cp.vo.BuyerSupplierOtherVo;
import com.cp.vo.BuyerVo;
import com.cp.vo.ChargeVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CollateralVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.CommonPageSecVo;
import com.cp.vo.ConsumerVo;
import com.cp.vo.CreditProcessingCustomerEntryVo;
import com.cp.vo.CreditProcessingNotepadVo;
import com.cp.vo.DealDisbursalVo;
import com.cp.vo.ExistingAssetVo;
import com.cp.vo.FieldVerificationVo;
import com.cp.vo.LeaddetailDealVo;
import com.cp.vo.LoanedProductVo;
import com.cp.vo.RelationalManagerVo;
import com.cp.vo.LinkCustomerVo;
import com.cp.vo.ScoringVO;
import com.cp.vo.SpecialConditionVo;
import com.cp.vo.UnderwriterApprovalVo;
import com.cp.vo.Viability;
import com.masters.vo.GcdGroupMasterVo;
import com.cp.vo.TermSheetVo;


public interface ACHCapturingDAO {
	 String IDENTITY="ACHCAPDAO";
	 
	 public ArrayList getToDebitList();
	 public ArrayList getDebitTypeList();
	 public ArrayList getFixedMaximumAmountList();
	 public ArrayList getFrequencyList();
	 public ArrayList getACHStatusList();
	 public ArrayList getACHReceivedStatusList();
	 public ArrayList fetchACHCustomerDetails(String dealId,String functionId);
	 public int saveNewACHRecordList(ACHCapturingVo achCapturingVo);
	 public ArrayList fetchSavedRecordList(ACHCapturingVo achCapturingVo);
	 public boolean saveNewACHTrackingRecordList(ACHCapturingVo achCapturingVo);
	 public ArrayList fetchAchRecordList(String achCaptringId,String functionId);
	 public boolean updateACHCapturingData(ACHCapturingVo achCapturingVo);
	 public ArrayList fetchSavedACHTrackingRecordList(String achCapturingId);
	 public boolean deleteACHDataList(String achCapturingId);
	public boolean checkSendtovender(ACHCapturingVo achCapturingVo);
	 public boolean updateACHCapturingDataforExist(ACHCapturingVo achCapturingVo);
}
