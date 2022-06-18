package com.business.legalMasterBussiness;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

import com.connect.DaoImplInstanceFactory;
import com.legal.dao.LegalMasterDAO;
import com.legal.vo.AdvocateMasterVo;
import com.legal.vo.LawFirmMasterVo;
import com.legal.vo.POAMasterVo;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LegalMasterBusinessSessionBean implements LegalMasterBusinessSessionBeanRemote{

	private static final Logger logger = Logger.getLogger(LegalMasterBusinessSessionBean.class.getName());
	 @Resource
	  private UserTransaction userTransaction;
	 LegalMasterDAO newObj=(LegalMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(LegalMasterDAO.IDENTITY); 
	
	
	
	//--------------Case Type Master-----------------By Vinod Kumar Gupta

	
	 public ArrayList searchCaseTypeMasterData(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.searchCaseTypeMasterData(ob);
			return list;
		}
		
		public boolean insertCaseTypeMaster(Object ob){
			logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.insertCaseTypeMaster(ob);
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
		
		public ArrayList editCaseTypeData(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.editCaseTypeData(ob);
			return list;
		}
		
		public boolean updateCaseTypeData(Object ob){
			logger.info("..In LegalMasterBusinessSessionBean..............updateCountryData");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.updateCaseTypeData(ob);
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
	
	//------end of Case Type block------
	
	// -------------  Law Firm Master ---- done by vinod kumar Gupta
	
	
	public ArrayList searchLawFirmMasterData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCountryData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchLawFirmMasterData(ob);
		return list;
	}
	
	public boolean insertLawFirmMaster(Object ob,String [] branchName){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertLawFirmMaster(ob,branchName);
			if(!st)
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
		return st;
	}
	
	public ArrayList editLawFirmData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editLawFirmData(ob);
		return list;
	}
	
	public boolean updateLawFirmData(Object ob,String [] branchName){
		logger.info("..In LegalMasterBusinessSessionBean..............updateCountryData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateLawFirmData(ob,branchName);
			if(!st)
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
		return st;
	}
	
	
	public ArrayList<LawFirmMasterVo> getBranchesForEdit(String lawFirmCode){
		logger.info("..In MasterBussinessSessionBean..............searchUserBranchEdit");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<LawFirmMasterVo> list= newObj.getBranchesForEdit(lawFirmCode);
		return list;
	}
	
// -------------  Advocate Master ---- done by vinod kumar Gupta
	
	
	public ArrayList searchAdvocateMasterData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCountryData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchAdvocateMasterData(ob);
		return list;
	}
	
	public boolean insertAdvocateMaster(Object ob,String [] branchName, String [] caseTypeCode){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertAdvocateMaster(ob,branchName,caseTypeCode);
			if(!st)
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
		return st;
	}
	
	public ArrayList editAdvocateData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editAdvocateData(ob);
		return list;
	}
	
	public boolean updateAdvocateData(Object ob,String [] branchName,String [] caseTypeCode){
		logger.info("..In LegalMasterBusinessSessionBean..............updateCountryData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateAdvocateData(ob,branchName,caseTypeCode);
			if(!st)
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
		return st;
	}
	
	
	public ArrayList<AdvocateMasterVo> getBranchesForAdvocateEdit(String advocateCode){
		logger.info("..In MasterBussinessSessionBean..............searchUserBranchEdit");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AdvocateMasterVo> list= newObj.getBranchesForAdvocateEdit(advocateCode);
		return list;
	}
	
	public ArrayList<AdvocateMasterVo> getcaseTypeForAdvocateEdit(String advocateCode){
		logger.info("..In MasterBussinessSessionBean..............searchUserBranchEdit");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AdvocateMasterVo> list= newObj.getcaseTypeForAdvocateEdit(advocateCode);
		return list;
	}
	
	//---Vinod work space end
	
	
	
	//----Richa work space end
	
	
//--------------Court Type Master----------------

	
	public ArrayList searchCourtTypeMasterData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchCourtTypeMasterData(ob);
		return list;
	}
	
	public boolean insertCourtTypeMaster(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertCourtTypeMaster(ob);
			if(!st)
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
		return st;
	}
	
	public ArrayList editCourtTypeData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editCourtTypeData(ob);
		return list;
	}
	
	public boolean updateCourtTypeData(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............updateCountryData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateCourtTypeData(ob);
			if(!st)
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
		return st;
	}
	
	//------end of court Type block------
	
	
//--------------Court Name Master----------------

	
	public ArrayList searchCourtNameMasterData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchCourtNameMasterData(ob);
		return list;
	}
	
	public boolean insertCourtNameMaster(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertCourtNameMaster(ob);
			if(!st)
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
		return st;
	}
	
	public ArrayList editCourtNameData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editCourtNameData(ob);
		return list;
	}
	
	public boolean updateCourtNameData(Object ob){
		logger.info("..In LegalMasterBusinessSessionBean..............updateCountryData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateCourtNameData(ob);
			if(!st)
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
		return st;
	}
	
	//------end of Court name block------
	
	
	
//--------------POA Master----------------

	
	public ArrayList searchPOAMasterData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchPOAMasterData(ob);
		return list;
	}
	
	public boolean insertPOAMaster(Object ob, String [] courtName){
		logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertPOAMaster(ob,courtName);
			if(!st)
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
		return st;
	}
	
	public ArrayList openEditPOAData(Object ob) {
		logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.openEditPOAData(ob);
		return list;
	}
	
	public boolean updatePOAData(Object ob, String[] courtName){
		logger.info("..In LegalMasterBusinessSessionBean..............updateCountryData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updatePOAData(ob,courtName);
			if(!st)
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
		return st;
	}
	public ArrayList<POAMasterVo> getcourtNameForPOaEdit(String poaCode){
		logger.info("..In MasterBussinessSessionBean..............searchUserBranchEdit");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<POAMasterVo> list= newObj.getcourtNameForPOaEdit(poaCode);
		return list;
	}
	
	
	//------end of Poa block------
	
	
//Nazia work space start
	
	
	// notice type master starts
	
	public ArrayList editNoticeTypeData(Object ob) {
		logger.info("..In CommonMasterBussinessSessionBean..............editNoticeTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editNoticeTypeData(ob);
		return list;
	}
	
	public ArrayList searchNoticeTypeMasterData(Object ob) {
		logger.info("..In CommonMasterBussinessSessionBean..............searchCountryData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchNoticeTypeMasterData(ob);
		return list;
	}
	
	public boolean insertNoticeTypeMaster(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............insertNoticeTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertNoticeTypeMaster(ob);
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
	
	public boolean updateNoticeTypeData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............updateCountryData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateNoticeTypeData(ob);
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
	
	// notice type master ends
	
	
	// stage type master starts
	
	public ArrayList editStageTypeData(Object ob) {
		logger.info("..In CommonMasterBussinessSessionBean..............editStageTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.editStageTypeData(ob);
		return list;
	}
	
	public ArrayList searchStageTypeMasterData(Object ob) {
		logger.info("..In CommonMasterBussinessSessionBean..............searchStageData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchStageTypeMasterData(ob);
		return list;
	}
	
	public boolean insertStageTypeMaster(Object ob,String [] productIdList,String [] paymentStageFlagList,String [] repetitiveFlagList,String productIds){
		logger.info("..In CommonMasterBussinessSessionBean..............insertStageTypeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertStageTypeMaster(ob,productIdList,paymentStageFlagList,repetitiveFlagList,productIds);
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
	
	public boolean updateStageTypeData(Object ob,String [] productIdList,String [] paymentStageFlagList,String [] repetitiveFlagList,String productIds){
		logger.info("..In CommonMasterBussinessSessionBean..............updateCountryData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateStageTypeData(ob,productIdList,paymentStageFlagList,repetitiveFlagList,productIds);
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
	
	public ArrayList getProduct(Object ob) {
		logger.info("..In CommonMasterBussinessSessionBean..............getProduct");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.getProduct(ob);
		return list;
	}
	
	public ArrayList getProductOnEdit(Object ob) {
		logger.info("..In CommonMasterBussinessSessionBean..............editStageTypeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.getProductOnEdit(ob);
		return list;
	}






	
	// stage type master ends
	
	
	
	// nazia work space ends
	
	public boolean checkExistingPAN(Object ob,String insertUpdateFlag){
		logger.info("..In CommonMasterBussinessSessionBean..............checkExistingPAN");
		boolean st = newObj.checkExistingPAN(ob,insertUpdateFlag);
		return st;
	}
	
	public boolean checkExistingPANAdvocate(Object ob,String insertUpdateFlag){
		logger.info("..In CommonMasterBussinessSessionBean..............checkExistingPANAdvocate");
		boolean st = newObj.checkExistingPANAdvocate(ob,insertUpdateFlag);
		return st;
	}
}
