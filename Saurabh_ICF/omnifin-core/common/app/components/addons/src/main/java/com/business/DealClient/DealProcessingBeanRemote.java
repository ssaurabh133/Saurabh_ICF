package com.business.DealClient;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.cp.vo.CodeDescVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.LinkCustomerVo;

@Remote
public interface DealProcessingBeanRemote {
	
	String REMOTE_IDENTITY="DEALPROCESSINGBUSSINESSMASTERREMOTE";

	ArrayList<Object> fetchDealDetail(CommonDealVo vo);
	ArrayList getDealHeader(String id);
	ArrayList<Object> getsourceTypeList();
	ArrayList<Object> getDealCatList();
	
	
	String checkStage(String dealId);
	boolean stageForward(String dealId);
	String getCollateralDetails(String dealId);
	String getRefreshFlag(String dealId);
	
	public String moveFromGCD(String customerId,String applType,String dealId,String tableStatus,String userId,String bDate);
	public String approve(String code, String cusType, String mvmtBy,String pageInfo, String dealId);
	
	public boolean deleteCustomerDocs(String[] roleId, String[] applType,String dealId);
	public boolean deleteroleList(String[] roleId,String[] applType,String dealId);
	
	ArrayList<Object> searchApplicant(LinkCustomerVo lo);
    public ArrayList getApplicantList();
    
    
    public String getDealLoanId(String dealId);
    public String getDealId(String dealId);
    public ArrayList getProductSchemeQuery(String dealId);
    ArrayList getProductSchemeDetailsFromLead(String product, String scheme,String loanTenure, String loanAmount, String loanPurpose,String bdate);
    
    public ArrayList getLoanTypeList();
    ArrayList getBaseRateList(String businessdate);
    ArrayList getSectorList();
    ArrayList getCycleDateList();
    ArrayList getProductTypeList();
    ArrayList getLoanDetailList(String dealId);
    ArrayList getAllLoanDetails(String scheme);
    
    boolean deleteLoanDetails(String[] id);
    boolean deleteChargeInstallmentRepay(String dealId, String[] dealLoanId);
    boolean deleteApplDocs(String dealId, String[] dealLoanId);
    
    ArrayList getSchemeList(String product);
    
    boolean saveLoanDetails(Object ob);
    
    
    String callProcedure(String DC,String dealId);
    ArrayList getchargesDetail(String dealCap,String dealId);
    String callRefreshChargesDetailPro(String string, String dealId);
	 ArrayList refreshchargesDetail(String dealCap,String dealId);
}
