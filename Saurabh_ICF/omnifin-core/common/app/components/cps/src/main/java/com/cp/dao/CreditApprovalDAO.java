package com.cp.dao;
import java.util.ArrayList;





import com.cp.vo.CodeDescVo;
/*import javax.servlet.http.HttpServletRequest;

import com.cp.vo.DisbursalSearchVO;
import com.cp.vo.InstallmentPlanForCMVO;
import com.cp.vo.ObligationVo;
import com.cp.vo.OtherChargesPlanVo;
import com.cp.vo.SecurityDepositVO;
import com.cp.vo.ApplicantTypeVO;
import com.cp.vo.BuyerSupplierOtherVo;
import com.cp.vo.BuyerVo;
import com.cp.vo.ChargeVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CollateralVo;*/
import com.cp.vo.CommonDealVo;
import com.cp.vo.CreditApprovalVo;


public interface CreditApprovalDAO {
	
	String IDENTITY = "CAD";

	ArrayList<CommonDealVo> getCreditApprovalDetails(CommonDealVo vo);
	/*ArrayList<CommonDealVo> fetchCreditDetail(CommonDealVo vo);*/
	boolean getCreditApprovalUpdate(String caseId,CreditApprovalVo vo);
	ArrayList<CodeDescVo> getCaseMovementList(String caseId);
}
