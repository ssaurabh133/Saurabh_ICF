package com.business.legalTransactionBussiness;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
import com.connect.DaoImplInstanceFactory;
import com.legal.dao.LegalTransactionDAO;
import com.masters.dao.MasterDAO;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LegalTransactionBusinessSessionBean implements LegalTransactionBusinessSessionBeanRemote{

	private static final Logger logger = Logger.getLogger(LegalTransactionBusinessSessionBean.class.getName());
	 @Resource
	  private UserTransaction userTransaction;
	 LegalTransactionDAO newObj=(LegalTransactionDAO)DaoImplInstanceFactory.getDaoImplInstance(LegalTransactionDAO.IDENTITY); 
	
	
//--------------Legal Notice Initiation Maker-----------------By Vinod Kumar Gupta

	
	public ArrayList searchLegalNoticeInitiationMakerData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLegalNoticeInitiationMakerData(ob);
		return list;
	}
	
	public boolean insertLegalNoticeInitiationMakerData(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertLegalNoticeInitiationMakerData(ob);
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
	
	public ArrayList editLegalNoticeInitiationMakerData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editLegalNoticeInitiationMakerData(ob);
		return list;
	}
	
	public boolean updateLegalNoticeInitiationMakerData(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............updateCountryData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateLegalNoticeInitiationMakerData(ob);
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


	
	//------end of Notice Initiation Maker block------
	
	public ArrayList searchLegalNoticeInitiationCheckerData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLegalNoticeInitiationCheckerData(ob);
		return list;
	}
	
	public boolean insertLegalNoticeInitiationChecker(Object ob){
		logger.info("..In insertLegalNoticeInitiationChecker () :");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertLegalNoticeInitiationChecker(ob);
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
	


public ArrayList editLegalNoticeInitiationCheckerData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editLegalNoticeInitiationCheckerData(ob);
		return list;
	}
	
	public ArrayList searchLegalDeclineDispatchNoticeData(Object ob) {
		logger.info("..In LegalTransactionBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLegalDeclineDispatchNoticeData(ob);
		return list;
	}
	
	public boolean insertLegalDeclineDispatchNotice(Object ob){
		logger.info("..In LegaltransactionBusinessSessionBean..............insert");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertLegalDeclineDispatchNotice(ob);
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
	
	public ArrayList openEditLegalDeclineDispatchNotice(Object ob) {
		logger.info("..In LegalTransactionBusinessSessionBean..............editLegalDeclineDispatchNotice");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editLegalDeclineDispatchNotice(ob);
		return list;
	}
	
	
	
	
	public ArrayList searchLegalCaseInitiationMaker(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLegalCaseInitiationMaker(ob);
		return list;
	}
	
	public ArrayList openEditLegalCaseInitiationMaker(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.openEditLegalCaseInitiationMaker(ob);
		return list;
	}
	
	public boolean insertLegalCaseInitiationMaker(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertLegalCaseInitiationMaker(ob);
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
	
	public boolean checkingLRNStatus(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.checkingLRNStatus(ob);
			
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return st;
	}
	
	public boolean checkingNotice138Status(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.checkingNotice138Status(ob);
			
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return st;
	}
	
	public ArrayList searchLegalCaseFileMaker(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLegalCaseFileMaker(ob);
		return list;
	}
	
	public ArrayList openEditLegalCaseFileMaker(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.openEditLegalCaseFileMaker(ob);
		return list;
	}
	
	public boolean saveLegalCaseFileMaker(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.saveLegalCaseFileMaker(ob);
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
	
	
	public ArrayList searchLegalCaseFileAuthor(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLegalCaseFileAuthor(ob);
		return list;
	}
	
	public ArrayList getLegalCaseFileMakerDataForAuthorView(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.getLegalCaseFileMakerDataForAuthorView(ob);
		return list;
	}
	
	public boolean saveLegalCaseFileAuthor(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.saveLegalCaseFileAuthor(ob);
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
	
	
	public ArrayList searchLegalCourtProcessingMaker(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLegalCourtProcessingMaker(ob);
		return list;
	}
	
	public ArrayList openEditLegalCourtProcessingMaker(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.openEditLegalCourtProcessingMaker(ob);
		return list;
	}
	
	public ArrayList getStageData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.getStageData(ob);
		return list;
	}
	
	public boolean insertCourtProcessingMakerDetails(Object ob,String [] stageList,String [] caseNoList,String [] remarkList,String [] hearingDateList,String [] poaList,String [] approvalFlagList,String [] makerDateList){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertCourtProcessingMakerDetails(ob,stageList,caseNoList,remarkList,hearingDateList,poaList,approvalFlagList,makerDateList);
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
	
	public boolean addRowofCourtProcessingMaker(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.addRowofCourtProcessingMaker(ob);
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
	
	public ArrayList searchLegalCourtProcessingAuthor(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLegalCourtProcessingAuthor(ob);
		return list;
	}
	
	public boolean saveCourtProcessingAuthorData(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.saveCourtProcessingAuthorData(ob);
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
	
	public ArrayList searchLegalReopenClosedCase(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLegalReopenClosedCase(ob);
		return list;
	}
	
	public boolean saveLegalReopenClosedCaseData(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.saveLegalReopenClosedCaseData(ob);
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
	
	public ArrayList searchAssignRejectCaseData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchAssignRejectCaseData(ob);
		return list;
	}
	
	
	
	public boolean insertAssignRejectCase(Object ob) {
		
	
		logger.info("..In insertAssignRejectCase () :");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertAssignRejectCase(ob);
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

	public ArrayList openEditAssignRejectCaseData(Object ob) {
		
	
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.openEditAssignRejectCaseData(ob);
		return list;
	}
	
	public ArrayList searchReassignCaseData(Object ob) {
		logger.info("..In LegalTransactionBusinessSessionBean..............searchReassignCaseData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchReassignCaseData(ob);
		return list;
	}

	public boolean insertReassignCase(Object ob){
		logger.info("..In LegaltransactionBusinessSessionBean..............insert");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertReassignCase(ob);
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

	public ArrayList editReassignCase(Object ob) {
		logger.info("..In LegalTransactionBusinessSessionBean..............Reassign case");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editReassignCase(ob);
		return list;
	}

	public boolean saveReassignCase(Object ob){
		logger.info("..In LegalTransactionBusinessSessionBean..............onSavereassignCase");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.saveReassignCase(ob);
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
	 
	   public String getNoticeId() {
	     logger.info("..In LegalTransactionBusinessSessionBean..............Reassign case");
	     logger.info("Implementation class: " + this.newObj.getClass());
	     String getNoticeId = this.newObj.getNoticeId();
	     return getNoticeId;
	   }
	 
	   public ArrayList searchReasonData(Object ob) {
	     logger.info("..In LegalTransactionBusinessSessionBean..............searchReasonData");
	    logger.info("Implementation class: " + this.newObj.getClass());
	     ArrayList list = this.newObj.searchReasonData(ob);
	    return list;
	   }
	   public boolean updateReasonData(Object ob) {
	     logger.info("..In LegalTransactionBusinessSessionBean..............updateReasonData");
	     boolean st = false;
	    try
	     {
	     this.userTransaction.begin();
	    logger.info("Implementation class: " + this.newObj.getClass());
	      st = this.newObj.updateReasonData(ob);
	     if (!st)
	      {
        logger.info("before rollback");
	        this.userTransaction.rollback();
	       }
	       else
	       {
	        logger.info("before commit");
	        this.userTransaction.commit();
	       }
	     }
	     catch (Exception e)
	     {
	      logger.info("Exception: " + e);
	     }
	    return st;
	   }
	   public String insertReasonMaster(Object ob) {
	    logger.info("..In LegalTransactionBusinessSessionBean..............insertReasonMaster");
	    String st = "";
	     try
	     {
	      this.userTransaction.begin();
	      logger.info("Implementation class: " + this.newObj.getClass());
	      st = this.newObj.insertReasonMaster(ob);
	      if (!st.equalsIgnoreCase(""))
	       {
	        logger.info("before commit");
	        this.userTransaction.commit();
	       }
	       else
	       {
	        logger.info("before rollback");
	        this.userTransaction.rollback();
	       }
	     }
	     catch (Exception e)
	     {
	      logger.info("Exception: " + e);
	     }
	    return st;
	   }
	
}
