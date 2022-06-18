package com.business.CPClient;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;


import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.cp.leadDao.LeadDao;
import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.cp.vo.LeadCaptureVo;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LeadProcessing implements LeadProcessingRemote {

	private static final Logger logger = Logger.getLogger(LeadProcessing.class.getName());
	LeadDao leadObj=(LeadDao)DaoImplInstanceFactory.getDaoImplInstance(LeadDao.IDENTITY);
	
	
	// Injects UserTransaction
	  @Resource
	  private UserTransaction userTransaction;
	
	@Override
	public ArrayList<Object> getCommonLeadData(LeadCaptureVo ob, String tracking) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getCommonLeadData(LeadCaptureVo ob, String tracking)");
		ArrayList commonData = leadObj.getCommonLeadData(ob, tracking);
		return commonData;
	}

	@Override
	public ArrayList getSourceDetailList(String source) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getSourceDetailList(String source)");
		ArrayList commonData = leadObj.getSourceDetailList(source);
		return commonData;
	}

	@Override
	public ArrayList CustomerDetailsList(String customerId, String addressId, String bDate) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................CustomerDetailsList(String customerId, String addressId)");
		ArrayList customerDetails = leadObj.CustomerDetailsList(customerId,addressId,bDate);
		return customerDetails;
	}

	@Override
	public ArrayList addresstype() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................addresstype()");
		ArrayList addresstype = leadObj.addresstype();
		return addresstype;
	}

	@Override
	public ArrayList defaultcountry() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................defaultcountry()");
		ArrayList defaultcountry = leadObj.defaultcountry();
		return defaultcountry;
	}

	@Override
	public boolean deletelead(String leadId) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................deletelead(String leadId)");
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = leadObj.deletelead(leadId);;
			if(st)
			{
				logger.info("before commit");
				userTransaction.commit();
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return st;
		
	}

	@Override
	public ArrayList existingUser(CreditProcessingLeadDetailDataVo cpLeadDetailVo, String userId) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................existingUser(String userName)");
		ArrayList existingUser = leadObj.existingUser(cpLeadDetailVo,userId);
		return existingUser;
	}

	@Override
	public ArrayList<Object> getAllocationDetail(String leadId) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getAllocationDetail(String leadId)");
		ArrayList getAllocationDetail = leadObj.getAllocationDetail(leadId);
		return getAllocationDetail;
	}

	@Override
	public ArrayList<Object> getBusinessSegmentList() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getBusinessSegmentList()");
		ArrayList getBusinessSegmentList = leadObj.getBusinessSegmentList();
		return getBusinessSegmentList;
	}
	
	@Override
	public ArrayList<Object> getEduDetailList() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getEduDetailList()");
		ArrayList getEduDetailList = leadObj.getEduDetailList();
		return getEduDetailList;
	}

	@Override
	public ArrayList<Object> getCommonLeadDatafromjsp(LeadCaptureVo ob,String tracking) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getCommonLeadDatafromjsp(LeadCaptureVo ob,String tracking)");
		ArrayList getCommonLeadDatafromjsp = leadObj.getCommonLeadDatafromjsp(ob,tracking);
		return getCommonLeadDatafromjsp;
	}

	@Override
	public ArrayList<Object> getContitutionList() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getContitutionList()");
		ArrayList getContitutionList = leadObj.getContitutionList();
		return getContitutionList;
	}

	@Override
	public ArrayList<Object> getCorContitutionList() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getCorContitutionList()");
		ArrayList getCorContitutionList = leadObj.getCorContitutionList();
		return getCorContitutionList;
	}

	@Override
	public ArrayList<LeadCaptureVo> getData(Object ob) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getData(Object ob)");
		ArrayList getData = leadObj.getData(ob);
		return getData;
	}

	@Override
	public ArrayList<Object> getIndContitutionList() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getIndContitutionList()");
		ArrayList getIndContitutionList = leadObj.getIndContitutionList();
		return getIndContitutionList;
	}

	@Override
	public ArrayList getLeadCapturingDetailsList(String leadId) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getLeadCapturingDetailsList(String leadId)");
		ArrayList getLeadCapturingDetailsList = leadObj.getLeadCapturingDetailsList(leadId);
		return getLeadCapturingDetailsList;
	}

	@Override
	public ArrayList getLeadDetailList() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getLeadDetailList()");
		ArrayList getLeadDetailList = leadObj.getLeadDetailList();
		return getLeadDetailList;
	}

	@Override
	public ArrayList getLeadNoteCode() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getLeadNoteCode()");
		ArrayList getLeadNoteCode = leadObj.getLeadNoteCode();
		return getLeadNoteCode;
	}

	@Override
	public ArrayList getLeadNotepadData(String txnid, String txnType) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getLeadNotepadData(String txnid, String txnType)");
		ArrayList getLeadNotepadData = leadObj.getLeadNotepadData(txnid, txnType);
		return getLeadNotepadData;
	}

	@Override
	public ArrayList<Object> getMaxLeadId(String userName) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getMaxLeadId(String userName)");
		ArrayList getMaxLeadId = leadObj.getMaxLeadId(userName);
		return getMaxLeadId;
	}

	@Override
	public ArrayList<Object> getRmDetail(String leadId) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getRmDetail(String leadId)");
		ArrayList getRmDetail = leadObj.getRmDetail(leadId);
		return getRmDetail;
	}

	@Override
	public ArrayList<Object> getTrackingDetail(String leadId) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getTrackingDetail(String leadId)");
		ArrayList getTrackingDetail = leadObj.getTrackingDetail(leadId);
		return getTrackingDetail;
	}

	@Override
	public ArrayList<Object> getexistingData(LeadCaptureVo ob, String tracking) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getexistingData(LeadCaptureVo ob, String tracking)");
		ArrayList getexistingData = leadObj.getexistingData(ob, tracking);
		return getexistingData;
	}

	@Override
	public boolean saveAllocation(CreditProcessingLeadDetailDataVo leadIdVo) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................saveAllocation(CreditProcessingLeadDetailDataVo leadIdVo)");
		boolean saveAllocation = false;
	
		try
		{
			userTransaction.begin();
			saveAllocation = leadObj.saveAllocation(leadIdVo);
			if(saveAllocation)
			{
				logger.info("before commit");
				userTransaction.commit();
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return saveAllocation;
	}

	@Override
	public boolean saveLeadDetailData(Object vo, String param) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................saveLeadDetailData(Object vo, String param)");
		boolean saveLeadDetailData = false;
		try
		{
			userTransaction.begin();
			saveLeadDetailData = leadObj.saveLeadDetailData(vo, param);
			if(saveLeadDetailData)
			{
				logger.info("before commit");
				userTransaction.commit();
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		
		return saveLeadDetailData;
	}

	@Override
	public boolean saveLeadNotepadData(Object ob) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................saveLeadNotepadData(Object ob)");
	
		boolean saveLeadNotepadData = false;
		try
		{
			userTransaction.begin();
			saveLeadNotepadData = leadObj.saveLeadNotepadData(ob);
			if(saveLeadNotepadData)
			{
				logger.info("before commit");
				userTransaction.commit();
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		
	
		return saveLeadNotepadData;
	}

	@Override
	public String saveNewLead(Object ob) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................saveNewLead(Object ob)");
		String saveNewLead = "";
		
		try
		{
			userTransaction.begin();
			saveNewLead = leadObj.saveNewLead(ob);
			if(CommonFunction.checkNull(saveNewLead).equalsIgnoreCase("E"))
			{
				logger.info("before rollback");
				userTransaction.rollback();
				
			}
			else
			{
				logger.info("before commit");
				userTransaction.commit();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		
	
		
		return saveNewLead;
	}

	@Override
	public boolean saveTrackingDetails(CreditProcessingLeadDetailDataVo leadIdVo) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................saveTrackingDetails(CreditProcessingLeadDetailDataVo leadIdVo)");
		boolean saveTrackingDetails = false;
		
		try
		{
			userTransaction.begin();
			saveTrackingDetails = leadObj.saveTrackingDetails(leadIdVo);
			if(saveTrackingDetails)
			{
				logger.info("before commit");
				userTransaction.commit();
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return saveTrackingDetails;
	}

	
	
	
	
	
	@Override
	public ArrayList<Object> getLeadEntryList(String leadId) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getLeadEntryList(String leadId)");
		ArrayList leadEntry = leadObj.getLeadEntryList(leadId);
		return leadEntry;
	}

	@Override
	public int saveCPLeadEntry(Object vo) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................saveCPLeadEntry(Object vo)");
		int leadEntry = 0;
  	
		try
		{
			userTransaction.begin();
			leadEntry = leadObj.saveCPLeadEntry(vo);
			if(leadEntry!=0)
			{
				logger.info("before commit");
				userTransaction.commit();
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return leadEntry;
	}

	@Override
	public ArrayList searchCPGrid(LeadCaptureVo vo,String leadNo) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................searchCPGrid(LeadCaptureVo vo)");
		ArrayList searchCPGrid = leadObj.searchCPGrid(vo,leadNo);
		return searchCPGrid;
	}

	@Override
	public ArrayList<Object> getDecisionList(String leadId) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getDecisionList(String leadId)");
		ArrayList getDecisionList = leadObj.getDecisionList(leadId);
		return getDecisionList;
	}

	@Override
	public ArrayList getLoanTypeList() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getLoanTypeList()");
		ArrayList getLoanTypeList = leadObj.getLoanTypeList();
		return getLoanTypeList;
	}
	
	@Override
	public boolean updateLeadStatus(String leadId) {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................updateLeadStatus(String leadId)");
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = leadObj.updateLeadStatus(leadId);;
			if(st)
			{
				logger.info("before commit");
				userTransaction.commit();
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return st;
		
	}	
	
	public ArrayList getSectorList() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getLoanTypeList()");
		ArrayList getSectorList = leadObj.getSectorList();
		return getSectorList;
	}
	
	public String getEmailMandatoryFlag() {
		logger.info("Implementation class: "+leadObj.getClass());
		logger.info("LeadProcessingRemote Bean..................................getEmailMandatoryFlag()");
		String flag = leadObj.getEmailMandatoryFlag();
		return flag;
	}
	 public String getPincodeFlag() {
		    logger.info("Implementation class: " + this.leadObj.getClass());
		    logger.info("LeadProcessingRemote Bean..................................getEmailMandatoryFlag()");
		    String flag = this.leadObj.getPincodeFlag();
		    return flag;
		  }	
}
