package com.business.DealClient;

import java.util.ArrayList;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dealDao.DealProcessingDao;
import com.cp.dealDaoImplMYSQL.DealProcessingDaoImpl;
import com.cp.vo.CommonDealVo;
import com.cp.vo.LinkCustomerVo;

@Stateless
public class DealProcessingBean implements DealProcessingBeanRemote {
	private static final Logger logger = Logger.getLogger(DealProcessingBeanRemote.class.getName());
	DealProcessingDao DPDao=(DealProcessingDao)DaoImplInstanceFactory.getDaoImplInstance(DealProcessingDao.IDENTITY);
     
	
//	DealProcessingDao DPDao = new DealProcessingDaoImpl();
	@Override
	public ArrayList<Object> fetchDealDetail(CommonDealVo vo) {
		logger.info("Inside DealProcessingBeanRemote ...............................fetchDealDetail(CommonDealVo vo)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList dealDetails = DPDao.fetchDealDetail(vo);
		return dealDetails;
	}
	
	@Override
	public ArrayList<Object> getDealCatList() {
		logger.info("Inside DealProcessingBeanRemote ...............................getDealCatList()");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList dealCategory = DPDao.getDealCatList();
		return dealCategory;
	}
	@Override
	public ArrayList getDealHeader(String id) {
		logger.info("Inside DealProcessingBeanRemote ...............................getDealHeader(String id)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList dealHeader = DPDao.getDealHeader(id);
		return dealHeader;
	}
	@Override
	public ArrayList<Object> getsourceTypeList() {
		logger.info("Inside DealProcessingBeanRemote ...............................getsourceTypeList()");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList sourceTypeList = DPDao.getsourceTypeList();
		return sourceTypeList;
	}

	@Override
	public String checkStage(String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................checkStage(String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		String checkStage = DPDao.checkStage(dealId);
		return checkStage;
	}

	@Override
	public boolean stageForward(String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................stageForward(String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		boolean stageForward = DPDao.stageForward(dealId);
		return stageForward;
	}

	@Override
	public String getCollateralDetails(String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................getCollateralDetails(String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		String getCollateralDetails = DPDao.getCollateralDetails(dealId);
		return getCollateralDetails;
	}

	@Override
	public String getRefreshFlag(String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................getRefreshFlag(String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		String getRefreshFlag = DPDao.getRefreshFlag(dealId);
		return getRefreshFlag;
	}

	@Override
	public String approve(String code, String cusType, String mvmtBy,
			String pageInfo, String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................approve(.....)");
		logger.info("Implementation class: "+DPDao.getClass());
		String approve = DPDao.approve(code,cusType,mvmtBy,pageInfo,dealId);
		return approve;
	}

	@Override
	public String moveFromGCD(String customerId, String applType,
			String dealId, String tableStatus, String userId, String bDate) {
		logger.info("Inside DealProcessingBeanRemote ...............................moveFromGCD(......)");
		logger.info("Implementation class: "+DPDao.getClass());
		String moveFromGCD = DPDao.moveFromGCD(customerId,applType,dealId,tableStatus,userId,bDate);
		return moveFromGCD;
	}

	
	@Override
	public boolean deleteCustomerDocs(String[] roleId, String[] applType,
			String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................deleteCustomerDocs(...)");
		logger.info("Implementation class: "+DPDao.getClass());
		boolean deleteCustomerDocs = DPDao.deleteCustomerDocs(roleId,applType,dealId);
		return deleteCustomerDocs;
	}

	
	@Override
	public boolean deleteroleList(String[] roleId, String[] applType,
			String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................deleteroleList(......)");
		logger.info("Implementation class: "+DPDao.getClass());
		boolean deleteroleList = DPDao.deleteroleList(roleId,applType,dealId);
		return deleteroleList;
	}

	@Override
	public ArrayList<Object> searchApplicant(LinkCustomerVo lo) {
		logger.info("Inside DealProcessingBeanRemote ...............................searchApplicant(LinkCustomerVo lo)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList searchApplicant = DPDao.searchApplicant(lo);
		return searchApplicant;
	}

	@Override
	public ArrayList getApplicantList() {
		logger.info("Inside DealProcessingBeanRemote ...............................getApplicantList() ");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getApplicantList = DPDao.getApplicantList() ;
		return getApplicantList;
	}

	@Override
	public ArrayList getAllLoanDetails(String scheme) {
		logger.info("Inside DealProcessingBeanRemote ...............................getAllLoanDetails(String scheme)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getAllLoanDetails = DPDao.getAllLoanDetails(scheme);
		return getAllLoanDetails;
	}

	@Override
	public ArrayList getBaseRateList(String businessdate) {
		logger.info("Inside DealProcessingBeanRemote ...............................getBaseRateList(String businessdate)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getBaseRateList = DPDao.getBaseRateList(businessdate);
		return getBaseRateList;
	}

	@Override
	public ArrayList getCycleDateList() {
		logger.info("Inside DealProcessingBeanRemote ...............................getCycleDateList() ");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getCycleDateList = DPDao.getCycleDateList() ;
		return getCycleDateList;
	}

	@Override
	public String getDealId(String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................getDealId(String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		String getDealId = DPDao.getDealId(dealId);
		return getDealId;
	}

	@Override
	public String getDealLoanId(String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................getDealLoanId(String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		String getDealLoanId = DPDao.getDealLoanId(dealId);
		return getDealLoanId;
	}

	@Override
	public ArrayList getLoanDetailList(String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................getLoanDetailList(dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getLoanDetailList = DPDao.getLoanDetailList(dealId);
		return getLoanDetailList;
	}

	@Override
	public ArrayList getLoanTypeList() {
		logger.info("Inside DealProcessingBeanRemote ...............................getLoanTypeList()");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getLoanTypeList = DPDao.getLoanTypeList();
		return getLoanTypeList;
	}

	@Override
	public ArrayList getProductSchemeDetailsFromLead(String product,
			String scheme, String loanTenure, String loanAmount,
			String loanPurpose, String bdate) {
		logger.info("Inside DealProcessingBeanRemote ...............................getProductSchemeDetailsFromLead(......)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getProductSchemeDetailsFromLead = DPDao.getProductSchemeDetailsFromLead(product,scheme,loanTenure,loanAmount,
				loanPurpose,bdate);
		return getProductSchemeDetailsFromLead;
	}

	@Override
	public ArrayList getProductSchemeQuery(String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................getProductSchemeQuery(String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getProductSchemeQuery = DPDao.getProductSchemeQuery(dealId);
		return getProductSchemeQuery;
	}

	@Override
	public ArrayList getProductTypeList() {
		logger.info("Inside DealProcessingBeanRemote ...............................getProductTypeList()");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getProductTypeList = DPDao.getProductTypeList();
		return getProductTypeList;
	}

	@Override
	public ArrayList getSectorList() {
		logger.info("Inside DealProcessingBeanRemote ...............................getSectorList()");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getSectorList = DPDao.getSectorList();
		return getSectorList;
	}

	@Override
	public boolean deleteApplDocs(String dealId, String[] dealLoanId) {
		logger.info("Inside DealProcessingBeanRemote ...............................deleteApplDocs(String dealId, String[] dealLoanId)");
		logger.info("Implementation class: "+DPDao.getClass());
		boolean deleteApplDocs = DPDao.deleteApplDocs(dealId,dealLoanId);
		return deleteApplDocs;
	}

	
	@Override
	public boolean deleteChargeInstallmentRepay(String dealId,
			String[] dealLoanId) {
		logger.info("Inside DealProcessingBeanRemote ...............................deleteChargeInstallmentRepay(String dealId,String[] dealLoanId)");
		logger.info("Implementation class: "+DPDao.getClass());
		boolean deleteChargeInstallmentRepay = DPDao.deleteChargeInstallmentRepay(dealId,dealLoanId);
		return deleteChargeInstallmentRepay;
	}

	
	@Override
	public boolean deleteLoanDetails(String[] id) {
		logger.info("Inside DealProcessingBeanRemote ...............................deleteLoanDetails(String[] id)");
		logger.info("Implementation class: "+DPDao.getClass());
		boolean deleteLoanDetails = DPDao.deleteLoanDetails(id);
		return deleteLoanDetails;
	}

	
	
	@Override
	public ArrayList getSchemeList(String product) {
		logger.info("Inside DealProcessingBeanRemote ...............................getSchemeList(String product)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getSchemeList = DPDao.getSchemeList(product);
		return getSchemeList;
	}

	
	@Override
	public boolean saveLoanDetails(Object ob) {
		logger.info("Inside DealProcessingBeanRemote ...............................saveLoanDetails(Object ob)");
		logger.info("Implementation class: "+DPDao.getClass());
		boolean saveLoanDetails = DPDao.saveLoanDetails(ob);
		return saveLoanDetails;
	}

	@Override
	public String callProcedure(String DC, String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................callProcedure(String DC, String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		String callProcedure = DPDao.callProcedure(DC,dealId);
		return callProcedure;
	}

	@Override
	public ArrayList getchargesDetail(String dealCap, String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................getchargesDetail(String dealCap, String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList getchargesDetail = DPDao.getchargesDetail(dealCap,dealId);
		return getchargesDetail;
	}

	@Override
	public String callRefreshChargesDetailPro(String string, String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................callRefreshChargesDetailPro(String string, String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		String callRefreshChargesDetailPro = DPDao.callRefreshChargesDetailPro(string,dealId);
		return callRefreshChargesDetailPro;
	}

	@Override
	public ArrayList refreshchargesDetail(String dealCap, String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................refreshchargesDetail(String dealCap, String dealId)");
		logger.info("Implementation class: "+DPDao.getClass());
		ArrayList refreshchargesDetail = DPDao.refreshchargesDetail(dealCap,dealId);
		return refreshchargesDetail;
	}

}
