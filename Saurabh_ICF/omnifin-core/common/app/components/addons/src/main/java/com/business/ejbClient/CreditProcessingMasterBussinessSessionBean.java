package com.business.ejbClient;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.masters.vo.ManufacturerSupplierMappingVO;
import com.masters.dao.MakeModelMasterDAO;
import com.masters.dao.ManualDeviationMasterDAO;
import com.masters.dao.ManufacturerSupplierMappingMasterDAO;
import com.masters.dao.MasterDAO;
import com.masters.dao.PCDMasterDAO;
import com.masters.dao.ScoringMasterDAO;
import com.masters.dao.UserApprovalMatrixDAO;
import com.masters.dao.UserProductAccessDAO;
import com.masters.dao.ruleMasterDAO;
import com.masters.vo.ApprovalLevelDefVo;
import com.masters.vo.ChargeCodeMasterVo;
import com.masters.vo.ChargeMasterVo;
import com.masters.vo.DocChildMasterVo;
import com.masters.vo.DocumentChecklistMasterVo;
import com.masters.vo.GoldOrnamentMasterVo;
import com.masters.vo.MakeModelmasterVO;
import com.masters.vo.ManufacturerSupplierMappingVO;
import com.masters.vo.ManulaDeviationVO;
import com.masters.vo.ScoringBenchmarkMasterVo;
import com.masters.vo.ScoringMasterVO;
import com.masters.vo.UsedVehiclePricingVo;
import com.masters.vo.UserApprovalMatrixVo;
import com.masters.vo.UserProductAccessVo;
import com.masters.vo.pcdMasterVo;
import com.masters.vo.ruleParamMasterVo;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CreditProcessingMasterBussinessSessionBean implements
		CreditProcessingMasterBussinessSessionBeanRemote {
	private static final Logger logger = Logger.getLogger(CreditProcessingMasterBussinessSessionBean.class.getName());
	// Injects UserTransaction
	  @Resource
	  private UserTransaction userTransaction;
	  MasterDAO newObj=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY); 
	  	  
	//MasterDAO newObj = new MasterDAOImpl();
	//-----------------------Product---------------------------------
	
	
	public ArrayList searchProductData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchProductData(Object ob)");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		ArrayList product = proSearch.searchProductData(ob);
		return product;
	}

	
	
	public ArrayList getProductCategory() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getProductCategory(");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		ArrayList getProduct = proSearch.getProductCategory();
		return getProduct;
	}

	

	public boolean insertProductMaster(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertProductMaster(Object ob)");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = proSearch.insertProductMaster(ob);
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

	

	public boolean updateProductData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateProductData(Object ob)");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = proSearch.updateProductData(ob);
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



	

	//-----------------------Scheme---------------------------------
	

	public String insertSchemeCodeMaster(Object ob, String ratMet, String rwEve) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertSchemeCodeMaster(Object ob, String ratMet, String rwEve)");
		MasterDAO schemeInsert=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+schemeInsert.getClass()); 
		//MasterDAO schemeInsert = new MasterDAOImpl();
		String st ="";
		try
		{
			userTransaction.begin();
			st = schemeInsert.insertSchemeCodeMaster(ob,ratMet,rwEve);
			if(st.equalsIgnoreCase("S"))
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



	public ArrayList modifySchemeDetailsDao(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........modifySchemeDetailsDao(Object ob)");
		MasterDAO schemeSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+schemeSearch.getClass()); 
		//MasterDAO schemeSearch = new MasterDAOImpl();
		ArrayList searchScheme = schemeSearch.modifySchemeDetailsDao(ob);
		return searchScheme;
	}
	public ArrayList getBranchIdsDao(String schemeId) 
	{
		logger.info("CreditProcessingMasterBussinessSessionBean...........getBranchIdsDao(String schemeId)");
		MasterDAO schemeSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+schemeSearch.getClass()); 
		ArrayList branchList = schemeSearch.getBranchIdsDao(schemeId);
		return branchList;
	}



	public boolean saveModifySchemeDetailsDao(Object ob, String ratMet,
			String rwEve) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveModifySchemeDetailsDao(Object ob, String ratMet,String rwEve)");
		MasterDAO schemeSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+schemeSearch.getClass()); 
		//MasterDAO schemeSearch = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = schemeSearch.saveModifySchemeDetailsDao(ob,ratMet,rwEve);
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



	public ArrayList searchScemeCodeDao(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchScemeCodeDao(Object ob)");
		MasterDAO schemeSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+schemeSearch.getClass()); 
		//MasterDAO schemeSearch = new MasterDAOImpl();
		ArrayList searchScheme = schemeSearch.searchScemeCodeDao(ob);
		return searchScheme;
	}

	
	//-----------------------Scheme---------------------------------

	
	public ArrayList getBaseRateData() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getBaseRateData(");
		MasterDAO baseRateSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+baseRateSearch.getClass()); 
		//MasterDAO baseRateSearch = new MasterDAOImpl();
		ArrayList BaseSearch = baseRateSearch.getBaseRateData();
		return BaseSearch;
	}



	public boolean insertBaseRateMaster(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertBaseRateMaster(Object ob)");
		MasterDAO baseRateInsert=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+baseRateInsert.getClass()); 
		//MasterDAO baseRateInsert = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = baseRateInsert.insertBaseRateMaster(ob);
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



	public ArrayList searchBaseRateData(Object Ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchBaseRateData(Object Ob)");
		MasterDAO baseSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+baseSearch.getClass()); 
		//MasterDAO baseSearch = new MasterDAOImpl();
		ArrayList searchBase = baseSearch.searchBaseRateData(Ob);
		return searchBase;
	}
	public ArrayList getbaseRateTypeList()
	{
		logger.info("CreditProcessingMasterBussinessSessionBean...........getbaseRateTypeList()");
		MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		// MasterDAO dao = new MasterDAOImpl();
		ArrayList list = dao.getbaseRateTypeList();
	    return list;
	}



	public boolean updateBaseRateData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateBaseRateData(Object ob)");
		MasterDAO updateBase=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+updateBase.getClass()); 
		//MasterDAO updateBase = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = updateBase.updateBaseRateData(ob);
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



	
	
	//---------------------Bank-------------------------------
	
	
	public String insertBankMaster(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertBankMaster(Object ob)");
		MasterDAO insertBank=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+insertBank.getClass()); 
		//MasterDAO insertBank = new MasterDAOImpl();
		String st ="";
		try
		{
			userTransaction.begin();
			st = insertBank.insertBankMaster(ob);
			if(!st.equalsIgnoreCase(""))
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



	

	public ArrayList searchBankData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchBankData(Object Ob)");
		MasterDAO searchBank=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+searchBank.getClass()); 
		//MasterDAO searchBank = new MasterDAOImpl();
		ArrayList bankData = searchBank.searchBankData(ob);
		return bankData;
	}



	

	public boolean updateBankData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateBankData(Object ob)");
		MasterDAO updateBank=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+updateBank.getClass()); 
		//MasterDAO updateBank = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = updateBank.updateBankData(ob);
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



	
	
	
	//---------------------Bank Branch-------------------------------
	
	
	
	public boolean insertBankBranchMaster(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertBankBranchMaster(Object ob)");
		MasterDAO BankBranch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+BankBranch.getClass()); 
		//MasterDAO BankBranch = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = BankBranch.insertBankBranchMaster(ob);
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



	public ArrayList modifyBankDetailsDao(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........modifyBankDetailsDao(Object ob)");
		MasterDAO BankDetails=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+BankDetails.getClass()); 
		//MasterDAO BankDetails = new MasterDAOImpl();
		ArrayList modifyBankDetails = BankDetails.modifyBankDetailsDao(ob);
		return modifyBankDetails;
	}



	public boolean saveModifyBankDetailsDao(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveModifyBankDetailsDao(Object ob)");
		MasterDAO modifyBankDetails=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+modifyBankDetails.getClass()); 
		//MasterDAO modifyBankDetails = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = modifyBankDetails.saveModifyBankDetailsDao(ob);
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



	public ArrayList searchBankBranchDao(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchBankBranchDao(Object ob)");
		MasterDAO bankBranchDao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+bankBranchDao.getClass()); 
		//MasterDAO bankBranchDao = new MasterDAOImpl();
		ArrayList searchBankBranch = bankBranchDao.searchBankBranchDao(ob);
		logger.info("CreditProcessingMasterBussinessSessionBean...list size "+searchBankBranch.size());
		return searchBankBranch;
	}



	public boolean insertBankAccountMaster(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertBankAccountMaster(Object ob)");
		MasterDAO bankAccount=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+bankAccount.getClass()); 
		//MasterDAO bankAccount = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = bankAccount.insertBankAccountMaster(ob);
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


	//---------------------Bank Account-------------------------------
	

	public ArrayList searchBankAccountData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchBankAccountData(Object ob)");
		MasterDAO bankAccount=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+bankAccount.getClass()); 
		//MasterDAO bankAccount = new MasterDAOImpl();
		ArrayList searchBankAccount = bankAccount.searchBankAccountData(ob);
		return searchBankAccount;
	}


	public boolean updateBankAccountData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateBankAccountData(Object ob)");
		MasterDAO bankAccount=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+bankAccount.getClass()); 
		//MasterDAO bankAccount = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = bankAccount.updateBankAccountData(ob);
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



	
	
	//---------------------Document Master-------------------------------
	
	
	public ArrayList getDocumentData() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getDocumentData(Object ob)");
		MasterDAO documentData=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+documentData.getClass()); 
		//MasterDAO documentData = new MasterDAOImpl();
		ArrayList getDocument = documentData.getDocumentData();
		return getDocument;
	}



	public boolean insertDocumentMaster(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertDocumentMaster(Object ob)");
		MasterDAO documentMaster=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+documentMaster.getClass()); 
		//MasterDAO documentMaster = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = documentMaster.insertDocumentMaster(ob);
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



	public ArrayList searchDocumentData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchDocumentData(Object ob)");
		MasterDAO searchDocument=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+searchDocument.getClass()); 
		//MasterDAO searchDocument = new MasterDAOImpl();
		ArrayList searchDocumentData = searchDocument.searchDocumentData(ob);
		return searchDocumentData;
	}



	public boolean updateDocumentData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateDocumentData(Object ob)");
		MasterDAO updateDocument=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+updateDocument.getClass()); 
		//MasterDAO updateDocument = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = updateDocument.updateDocumentData(ob);
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


	//---------------------Document checkList Master-------------------------------
	
	
	public ArrayList getConstitution() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getConstitution()");
		MasterDAO constitution=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+constitution.getClass()); 
		//MasterDAO constitution = new MasterDAOImpl();
		ArrayList getConstitution = constitution.getConstitution();
		return getConstitution;
	}



	public ArrayList getEntity(String parentValue) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getEntity()");
		MasterDAO entity=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+entity.getClass()); 
		//MasterDAO entity = new MasterDAOImpl();
		ArrayList getEntity = entity.getEntity(parentValue);
		return getEntity;
	}



	public ArrayList getStage() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getStage()");
		MasterDAO stage=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+stage.getClass()); 
		//MasterDAO stage = new MasterDAOImpl();
		ArrayList getStage = stage.getStage();
		return getStage;
	}



	public boolean insertDocCheckListMaster(String[] docIdList,
			String[] docMandatoryList, String[] docOriginalList,
			String[] docExpiryFlagList, String[] statusList,
			DocumentChecklistMasterVo vo) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertDocCheckListMaster");
		MasterDAO docCheckListMaster=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+docCheckListMaster.getClass()); 
		//MasterDAO docCheckListMaster = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = docCheckListMaster.insertDocCheckListMaster(docIdList,docMandatoryList, 
					docOriginalList, docExpiryFlagList, statusList, vo );
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



	public ArrayList searchDocCheckListData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchDocCheckListData(Object ob)");
		MasterDAO docCheckListData=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+docCheckListData.getClass()); 
		//MasterDAO docCheckListData = new MasterDAOImpl();
		ArrayList searchDocCheckList = docCheckListData.searchDocCheckListData(ob);
		return searchDocCheckList;
	}



	public boolean updatedocCheckListData(String[] docIdList,
			String[] docCheckIdList, String[] docMandatoryList,
			String[] docOriginalList, String[] docExpiryFlagList,
			String[] statusList, DocumentChecklistMasterVo vo, String[] docCheckAllIdVal) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........updatedocCheckListData");
		MasterDAO docCheckList=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+docCheckList.getClass()); 
		//MasterDAO docCheckList = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = docCheckList.updatedocCheckListData(docIdList,docCheckIdList, 
					docMandatoryList, docOriginalList, docExpiryFlagList, statusList, vo,docCheckAllIdVal );
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

	//---------------------Document child Master-----------by Nishant Rai----
	public ArrayList<DocChildMasterVo> searchDocChildData(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchDocCheckListData(Object ob)");
		logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList<DocChildMasterVo> list = newObj.searchDocChildData(ob);
		return list;
	}
	
	public boolean updateDocChildData(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateDocChildData(Object ob)");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass()); 
			st = newObj.updateDocChildData(ob);
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
	
	public boolean insertDocChildMaster(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertDocChildMaster(Object ob)");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass()); 
			st = newObj.insertDocChildMaster(ob);
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
	
	 //---------------------Charge Code Master-----------by Nishant Rai----
	public String insertchagreCodeMaster(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertchagreCodeMaster(Object ob)");
		String st = "";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass()); 
			st = newObj.insertchagreCodeMaster(ob);
			if(st.equalsIgnoreCase("datasaved"))
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
	public ArrayList <ChargeCodeMasterVo>  modifyChargeCodeDetailsDao(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........modifyChargeCodeDetailsDao(Object ob)");
		logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList<ChargeCodeMasterVo> list = newObj.modifyChargeCodeDetailsDao(ob);
		return list;
	}
	public boolean saveModifyChargeCodeDetailsDao(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveModifyChargeCodeDetailsDao(Object ob)");
		boolean st = false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass()); 
			st = newObj.saveModifyChargeCodeDetailsDao(ob);
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
	public ArrayList <ChargeCodeMasterVo>  searchChargeCodeDao(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchChargeCodeDao(Object ob)");
		logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList<ChargeCodeMasterVo> list = newObj.searchChargeCodeDao(ob);
		return list;
	}
	
	//---------------------Charges Definition Master-----------by Nishant Rai----
	public boolean insertChargeMaster(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertChargeMaster(Object ob)");
		boolean st = false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass()); 
			st = newObj.insertChargeMaster(ob);
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
	public ArrayList <ChargeMasterVo> getChargeData(){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getChargeData()");
		logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList<ChargeMasterVo> list = newObj.getChargeData();
		return list;
	}
	public boolean updateChargeData(Object ob,String chargeId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateChargeData(Object ob,String chargeId)");
		boolean st = false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass()); 
			st = newObj.updateChargeData(ob, chargeId);
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
	public ArrayList <ChargeMasterVo> searchChargeData(Object Ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchChargeData(Object ob)");
		logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList<ChargeMasterVo> list = newObj.searchChargeData(Ob);
		return list;
	}
	
	//---------------------Rule Master-----------by Nishant Rai----
	public ArrayList ruleMasterSearch(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........ruleMasterSearch(Object ob)");
		logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList list = newObj.ruleMasterSearch(ob);
		return list;
	}
	public ArrayList getRuleMasterParam(String sourceType){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getRuleMasterParam(String sourceType)");
		logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList list = newObj.getRuleMasterParam(sourceType);
		return list;
	}
	public String updateRuleDetail(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateRuleDetail(Object ob)");
		String st = "";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass()); 
			st = newObj.updateRuleDetail(ob);
			if(st.equalsIgnoreCase("S"))
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
	public String saveRuleDetail(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveRuleDetail(Object ob)");
		String st = "";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass()); 
			st = newObj.saveRuleDetail(ob);
			if(st.equalsIgnoreCase("S"))
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
	public ArrayList getRoleType(){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getRoleType()");
		logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList list = newObj.getRoleType();
		return list;
	}
	public ArrayList getSubRoleType(){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getSubRoleType()");
		ArrayList list = newObj.getSubRoleType();
		return list;
	}
	
	//---------------------Rule Parameter Master-----------by Nishant Rai----
	public ArrayList<ruleParamMasterVo> searchRuleMaster(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchRuleMaster");
		ruleMasterDAO newObj=(ruleMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ruleMasterDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//ruleMasterDAO newObj = new ruleMasterDAOImpl();
		ArrayList list = newObj.searchRuleMaster(ob);
		return list;
	}
	public boolean updateRuleMaster(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateRuleMaster");
		ruleMasterDAO newObj=(ruleMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ruleMasterDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//ruleMasterDAO newObj = new ruleMasterDAOImpl();
		boolean st = false;
		try
		{
			userTransaction.begin();
			st = newObj.updateRuleMaster(ob);
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
	public boolean insertRuleScoreMaster(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertRuleScoreMaster");
		ruleMasterDAO newObj=(ruleMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ruleMasterDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//ruleMasterDAO newObj = new ruleMasterDAOImpl();
		boolean st = false;
		try
		{
			userTransaction.begin();
			st = newObj.insertRuleScoreMaster(ob);
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
	
	//---------------------User Product Access Master-----------by Nishant Rai----
	// changed by asesh for mssql
	public boolean saveUserProductAccess(UserProductAccessVo vo,String [] productId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveUserProductAccess");
		UserProductAccessDAO newObj=(UserProductAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(UserProductAccessDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//UserProductAccessDAO newObj = new UserProductAccessDAOImpl();
		boolean st = false;
		try
		{
			userTransaction.begin();
			st = newObj.saveUserProductAccess(vo, productId);
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
	public ArrayList getRecordAtSearch(int currentPageLink){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getRecordAtSearch");
		UserProductAccessDAO newObj=(UserProductAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(UserProductAccessDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList list = newObj.getRecordAtSearch(currentPageLink);
		return list;
	}
	public ArrayList<UserProductAccessVo> getRecordForUpdate(String userId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getRecordForUpdate");
		UserProductAccessDAO newObj=(UserProductAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(UserProductAccessDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList<UserProductAccessVo> list = newObj.getRecordForUpdate(userId);
		return list;
	}
	public ArrayList searchUserProductRecord(UserProductAccessVo vo){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchUserProductRecord");
		UserProductAccessDAO newObj=(UserProductAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(UserProductAccessDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList list = newObj.searchUserProductRecord(vo);
		return list;
	}
	public ArrayList<UserProductAccessVo> getProductRecordForUpdate(String userId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getProductRecordForUpdate");
		UserProductAccessDAO newObj=(UserProductAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(UserProductAccessDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList<UserProductAccessVo> list = newObj.getProductRecordForUpdate(userId);
		return list;
	}
	
	//---------------------User Approval Matrix Master-----------by Nishant Rai----
	// changed by asesh for mssql
	public ArrayList<UserApprovalMatrixVo> getApprovedUser(int currentPageLink,String flag){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getApprovedUser");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//UserApprovalMatrixDAO newObj = new UserApprovalMatrixDAOIMPL();
		ArrayList<UserApprovalMatrixVo> list = newObj.getApprovedUser(currentPageLink,flag);
		return list;
	} 
	public boolean saveUserApprovalMatrix(UserApprovalMatrixVo vo,String flag){
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveUserApprovalMatrix");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass());
		boolean st = false;
		try
		{
			userTransaction.begin();
			st = newObj.saveUserApprovalMatrix(vo,flag);
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
	public ArrayList getDetail(String userId, String userRole,String flag){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getDetail");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass());
		ArrayList list = newObj.getDetail(userId, userRole,flag);
		return list;
	} 
	public boolean updateUserApprovedRecords(UserApprovalMatrixVo vo,String flag){
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateUserApprovedRecords");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass());
		boolean st = false;
		try
		{
			userTransaction.begin();
			st = newObj.updateUserApprovedRecords(vo,flag);
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
	public ArrayList<UserApprovalMatrixVo> getsearchApprovedUser(UserApprovalMatrixVo vo, String flag, String recStatus,String makerAuthorFlag){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getsearchApprovedUser");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass());
		ArrayList<UserApprovalMatrixVo> list = newObj.getsearchApprovedUser(vo,flag,recStatus,makerAuthorFlag);
		return list;
	} 
	public int checkUserId(String userId, String role){
		logger.info("CreditProcessingMasterBussinessSessionBean...........checkUserId");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass());
		int uId = newObj.checkUserId(userId, role);
		return uId;
	}
	public ArrayList<UserApprovalMatrixVo> getBranches(String userId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getBranches");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass());
		ArrayList<UserApprovalMatrixVo> list = newObj.getBranches(userId);
		return list;
	} 
	public ArrayList<UserApprovalMatrixVo> getProducts(String userId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getProducts");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass());
		ArrayList<UserApprovalMatrixVo> list = newObj.getProducts(userId);
		return list;
	} 
	//---------------------U/W Approval Level Definition Master-----------by Nishant Rai----
		public ArrayList searchApprovalLevelDef(ApprovalLevelDefVo Vo,String mcFlag){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchApprovalLevelDef");
		ArrayList list = newObj.searchApprovalLevelDef(Vo,mcFlag);
		return list;
	}
	public boolean saveApprovalLevelDef(ApprovalLevelDefVo Vo,String mcFlag){
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveApprovalLevelDef");
		boolean st = false;
		try
		{
			userTransaction.begin();
			st = newObj.saveApprovalLevelDef(Vo,mcFlag);
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
		public ArrayList<ApprovalLevelDefVo> editApprovalLevelDef(String productModify,String mcFlag){
		logger.info("CreditProcessingMasterBussinessSessionBean...........editApprovalLevelDef");
		ArrayList<ApprovalLevelDefVo> list = newObj.editApprovalLevelDef(productModify,mcFlag);
		return list;
	}
	
	// saurabh changes 
	public String saveUWApproval(ApprovalLevelDefVo Vo,String productModify){
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveUWApproval");
		String flag=newObj.saveUWApproval(Vo,productModify);
		return flag;
	}
	public String getApprovalId(){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getApprovalId");
		String id=newObj.getApprovalId();
		return id;
	}
	public String forwardApprovalLevel(String approvalId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getApprovalId");
		String id=newObj.forwardApprovalLevel(approvalId);
		return id;
		
	}
	public ArrayList searchApprovalLevelDefAuthor(ApprovalLevelDefVo Vo){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchApprovalLevelDefAuthor");
		ArrayList list = newObj.searchApprovalLevelDefAuthor(Vo);
		return list;
	}

	
	//SANJOG CODE START FOR USER APPROVAL
	public int getApprovalfromPmst(){
		logger.info("CreditProcessingMasterBussinessSessionBean...........editApprovalLevelDef");
		int list = newObj.getApprovalfromPmst();
		return list;
	}
	public int getApprovalLevelfromPmst(){
		logger.info("CreditProcessingMasterBussinessSessionBean......getApprovalLevelfromPmst.....");
		int list = newObj.getApprovalLevelfromPmst();
		return list;
	}
	
		public String updateApprovalLevelDef(ApprovalLevelDefVo Vo, String productModify,String mcFlag){
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateApprovalLevelDef");
		String st = "";
		try
		{
			userTransaction.begin();
			st = newObj.updateApprovalLevelDef(Vo, productModify,mcFlag);
			if(st.equalsIgnoreCase("S"))
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

	//---------------------Policy Check Definition Master-----------by Nishant Rai----
	public ArrayList<pcdMasterVo> searchScreenPolicyListData(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchScreenPolicyListData");
		PCDMasterDAO newObj=(PCDMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(PCDMasterDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//PCDMasterDAO newObj = new PCDMasterDAOImpl();
		ArrayList<pcdMasterVo> list = newObj.searchScreenPolicyListData(ob);
		return list;
	} 
	public boolean insertPolicyCheckListMaster(String[] ruleCodeList,String[] ruleDescList,String[] actionList,String[] appLevelList,pcdMasterVo vo){
		logger.info("CreditProcessingMasterBussinessSessionBean...........insertPolicyCheckListMaster");
		PCDMasterDAO newObj=(PCDMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(PCDMasterDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//PCDMasterDAO newObj = new PCDMasterDAOImpl();
		boolean pcm = false;
		try
		{
			userTransaction.begin();
			pcm = newObj.insertPolicyCheckListMaster(ruleCodeList, ruleDescList, actionList, appLevelList, vo);
			if(pcm)
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
		return pcm;
	} 
	public boolean updatePolicyCheckListMaster(String[] pcdCheckId,String[] ruleCodeList,String[] ruleDescList,String[] actionList,String[] appLevelList,pcdMasterVo vo){
		logger.info("CreditProcessingMasterBussinessSessionBean...........updatePolicyCheckListMaster");
		PCDMasterDAO newObj=(PCDMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(PCDMasterDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//PCDMasterDAO newObj = new PCDMasterDAOImpl();
		boolean pcm = false;
		try
		{
			userTransaction.begin();
			pcm = newObj.updatePolicyCheckListMaster(pcdCheckId, ruleCodeList, ruleDescList, actionList, appLevelList, vo);
			if(pcm)
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
		return pcm;
	} 
	public ArrayList<pcdMasterVo> searchPolicyListData(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchPolicyListData");
		PCDMasterDAO newObj=(PCDMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(PCDMasterDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//PCDMasterDAO newObj = new PCDMasterDAOImpl();
		ArrayList<pcdMasterVo> list = newObj.searchPolicyListData(ob);
		return list;
	} 
	public ArrayList<pcdMasterVo> searchPolicyListDataForEditting(Object ob,String product,String scheme, String stageId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchPolicyListDataForEditting");
		PCDMasterDAO newObj=(PCDMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(PCDMasterDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//PCDMasterDAO newObj = new PCDMasterDAOImpl();
		ArrayList<pcdMasterVo> list = newObj.searchPolicyListDataForEditting(ob, product, scheme, stageId);
		return list;
	} 
	//---------------------Make Model Master-----------by Nishant Rai----
	public boolean saveMakeModelRecord(MakeModelmasterVO vo,String state){
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveMakeModelRecord");
		MakeModelMasterDAO newObj=(MakeModelMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MakeModelMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+newObj.getClass()); 
		//MakeModelMasterDAO newObj = new MakeModelMasterDAOImpl();
		boolean st = false;
		try
		{
			userTransaction.begin();
			st = newObj.saveMakeModelRecord(vo,state);
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
	
//	changed by abhimanyu on 18/07/2012
	public boolean checkRecord(MakeModelmasterVO vo){
		logger.info("CreditProcessingMasterBussinessSessionBean...........checkRecord");
		MakeModelMasterDAO newObj=(MakeModelMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MakeModelMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+newObj.getClass()); 
		//MakeModelMasterDAO newObj = new MakeModelMasterDAOImpl();
		boolean st = newObj.checkRecord(vo);
		return st;
	} 
//	changed by abhimanyu on 18/07/2012
	public ArrayList<MakeModelmasterVO> getMakeModelrecords(int currentPageLink){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getMakeModelrecords");
		MakeModelMasterDAO newObj=(MakeModelMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MakeModelMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+newObj.getClass()); 
		//MakeModelMasterDAO newObj = new MakeModelMasterDAOImpl();
		ArrayList<MakeModelmasterVO> list = newObj.getMakeModelrecords(currentPageLink);
		return list;
	} 
	public ArrayList<MakeModelmasterVO> searchMakeModelRecords(MakeModelmasterVO vo){
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchMakeModelRecords");
		MakeModelMasterDAO newObj=(MakeModelMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MakeModelMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+newObj.getClass()); 
		//MakeModelMasterDAO newObj = new MakeModelMasterDAOImpl();
		ArrayList<MakeModelmasterVO> list = newObj.searchMakeModelRecords(vo);
		return list;
	} 
	public ArrayList<MakeModelmasterVO> getParticularRecord(String makeModelId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getParticularRecord");
		MakeModelMasterDAO newObj=(MakeModelMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MakeModelMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+newObj.getClass()); 
		//MakeModelMasterDAO newObj = new MakeModelMasterDAOImpl();
		ArrayList<MakeModelmasterVO> list = newObj.getParticularRecord(makeModelId);
		return list;
	} 
	public String  updateMakeModelRecord(MakeModelmasterVO vo,String[] stateId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateMakeModelRecord");
		MakeModelMasterDAO newObj=(MakeModelMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MakeModelMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+newObj.getClass()); 
		//MakeModelMasterDAO newObj = new MakeModelMasterDAOImpl();
		String st = "";
		try
		{
			userTransaction.begin();
			st = newObj.updateMakeModelRecord(vo,stateId);
			if(CommonFunction.checkNull(st).trim().equalsIgnoreCase("saved"))
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



	
	public boolean checkRecordforUpdate(MakeModelmasterVO vo) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........checkRecordforUpdate");
		MakeModelMasterDAO newObj=(MakeModelMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MakeModelMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+newObj.getClass()); 
		//MakeModelMasterDAO newObj = new MakeModelMasterDAOImpl();
		boolean st = newObj.checkRecordforUpdate(vo);
		return st;
	}



	
	public int getMakeModelID(MakeModelmasterVO vo) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getMakeModelID");
		MakeModelMasterDAO newObj=(MakeModelMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MakeModelMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+newObj.getClass()); 
		//MakeModelMasterDAO newObj = new MakeModelMasterDAOImpl();
		int st = newObj.getMakeModelID(vo);
		return st;
		
	} 
	
//manual deviation starts here
	
	public boolean saveManualDeviation(ManulaDeviationVO vo) {
		ManualDeviationMasterDAO service=(ManualDeviationMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualDeviationMasterDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//ManualDeviationMasterDAO service=new ManualDeviationMasterDAOIMPL();
		boolean status=false;
		status=service.saveManualDeviation(vo);
		return status;
	}


	
	public ArrayList<ManulaDeviationVO> getManualDeviationRecords(int linksize) {
		ManualDeviationMasterDAO service=(ManualDeviationMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualDeviationMasterDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//ManualDeviationMasterDAO service=new ManualDeviationMasterDAOIMPL();
		ArrayList<ManulaDeviationVO> list = new ArrayList<ManulaDeviationVO>();		
		list=service.getManualDeviationRecords(linksize);
		return list;
		
	}



	
	public ArrayList<ManulaDeviationVO> searchManualDeviationList(
			ManulaDeviationVO vo) {
		ManualDeviationMasterDAO service=(ManualDeviationMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualDeviationMasterDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//ManualDeviationMasterDAO service=new ManualDeviationMasterDAOIMPL();
		ArrayList<ManulaDeviationVO> list = new ArrayList<ManulaDeviationVO>();		
		list=service.searchManualDeviationList(vo);
		return list;
	}

	
	public ArrayList<ManulaDeviationVO> getSingleRecord(String manualid) {
		ManualDeviationMasterDAO service=(ManualDeviationMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ManualDeviationMasterDAO.IDENTITY);
		logger.info("Implementation class: "+service.getClass()); 
		//ManualDeviationMasterDAO service=new ManualDeviationMasterDAOIMPL();
		ArrayList<ManulaDeviationVO> list = new ArrayList<ManulaDeviationVO>();		
		list=service.getSingleRecord(manualid);
		return list;
	} 
	//manual deviation end here
	
	
	//Manufacturer Supplier Start Here
	
	public ArrayList<ManufacturerSupplierMappingVO> getManufacturerSupplierMappingRecords(ManufacturerSupplierMappingVO vo, int linksize) {
		ManufacturerSupplierMappingMasterDAO service=(ManufacturerSupplierMappingMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ManufacturerSupplierMappingMasterDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
		//ManufacturerSupplierMappingMasterDAO service=new ManufacturerSupplierMappingMasterDAOIMPL();
		ArrayList<ManufacturerSupplierMappingVO> list = new ArrayList<ManufacturerSupplierMappingVO>();		
		list=service.getManufacturerSupplierMappingRecords(vo,linksize);
		return list;
		
	}
	
	
	public boolean saveMfrSuppMappingRecord(ManufacturerSupplierMappingVO vo, String[] supplier) {
		ManufacturerSupplierMappingMasterDAO service=(ManufacturerSupplierMappingMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ManufacturerSupplierMappingMasterDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
		//ManufacturerSupplierMappingMasterDAO service=new ManufacturerSupplierMappingMasterDAOIMPL();
		boolean status=false;
		status=service.saveMfrSuppMappingRecord(vo,supplier);
		return status;
	}
	
	
	public ArrayList<ManufacturerSupplierMappingVO> searchManufacturerSupplierMappingList(ManufacturerSupplierMappingVO vo) {
		ManufacturerSupplierMappingMasterDAO service=(ManufacturerSupplierMappingMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ManufacturerSupplierMappingMasterDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
		//ManufacturerSupplierMappingMasterDAO service=new ManufacturerSupplierMappingMasterDAOIMPL();
		ArrayList<ManufacturerSupplierMappingVO> list = new ArrayList<ManufacturerSupplierMappingVO>();		
		list=service.searchManufacturerSupplierMappingList(vo);
		return list;
	}
	
	public ArrayList<ManufacturerSupplierMappingVO> searchSupplierDescEdit(String mappingId){
		ManufacturerSupplierMappingMasterDAO service=(ManufacturerSupplierMappingMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ManufacturerSupplierMappingMasterDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
		//ManufacturerSupplierMappingMasterDAO service=new ManufacturerSupplierMappingMasterDAOIMPL();
		ArrayList<ManufacturerSupplierMappingVO> list = new ArrayList<ManufacturerSupplierMappingVO>();				
		list= service.searchSupplierDescEdit(mappingId);
		return list;
	}


	
	public ArrayList searchVerificationQuestionData(Object ob) {
		
		logger.info("searchVerificationQuestionData..."+ob);
		ArrayList list= newObj.searchVerificationQuestionData(ob);
		return list;
	}
	
	
	public ArrayList getGridInEditVerificationQuest(Object ob) {
		
		logger.info("In getGridInEditVerificationQuest...  ob==>   "+ob);
		
		ArrayList list= newObj.getGridInEditVerificationQuest(ob);
		return list;
	}
	

	
	public boolean insertVerificationQuestMaster(Object ob, String[] productId,String check){
		
		logger.info("............insertVerificationQuestMaster");
		boolean st =false;
		boolean productStatus = false;
		try
		{
			userTransaction.begin();
			st = newObj.insertVerificationQuestMaster(ob);
			productStatus=newObj.insertQuestionProduct(ob,productId,check);
			if(st && productStatus)
			{
				logger.info("before commit");
				userTransaction.commit();
			}
			else
			{
				logger.info("st::::::"+st);
				logger.info("productStatus::::::"+productStatus);
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
	
	public boolean updateVerificationQuestMaster(Object ob, String[] productId,String check){
		
		logger.info("............updateVerificationQuestMaster");
		boolean st =false;
		boolean productStatus = false;
		String QuestionId="";
		try
		{
			userTransaction.begin();
			st = newObj.updateVerificationQuestMaster(ob);
			productStatus=newObj.insertQuestionProduct(ob,productId,check);
			if(st && productStatus)
			{
				logger.info("before commit");
				userTransaction.commit();
			}
			else
			{
				logger.info("st::::::"+st);
				logger.info("productStatus::::::"+productStatus);
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

	
	public boolean updateMfrSuppMappingRecord(ManufacturerSupplierMappingVO vo, String[] supplier) {
		ManufacturerSupplierMappingMasterDAO service=(ManufacturerSupplierMappingMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ManufacturerSupplierMappingMasterDAO.IDENTITY);
        logger.info("Implementation class: "+service.getClass());
		//ManufacturerSupplierMappingMasterDAO service=new ManufacturerSupplierMappingMasterDAOIMPL();
		boolean status=false;
		status=service.updateMfrSuppMappingRecord(vo,supplier);
		return status;
	}

	public ArrayList<Object> getProductList(String  questionId) 
	{
		ArrayList productList=new ArrayList();
		logger.info("CreditProcessingMasterBussinessSessionBean...........getProductList()");
		MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		productList = dao.getProductList(questionId);
		return productList;
	}


	
	public ArrayList getVerificationQuestData(Object ob) {
		
		logger.info("getVerificationMappingData..."+ob);
		ArrayList list= newObj.getVerificationQuestData(ob);
		return list;
	}



	
	public int countVerificationCombination(Object vo) {
		
		logger.info("CreditProcessingMasterBussinessSessionBean...........countVerificationCombination(Object ob)");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		int count = proSearch.countVerificationCombination(vo);
		return count;
	}


/* Code by Arun For Scoring Master Starts Here*/
/* changed by asesh for mssql */
	
	public ArrayList<ScoringMasterVO> editScoringMasterHeader(ScoringMasterVO vo) {
		ScoringMasterDAO scoringMasterDao=(ScoringMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoringMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+scoringMasterDao.getClass()); 				// changed by Asesh
		//ScoringMasterDAO scoringMasterDao=new ScoringMasterDAOImpl();
		ArrayList<ScoringMasterVO> list=scoringMasterDao.editScoringMasterHeader(vo);
		return list;
	}



	
	public ArrayList<ScoringMasterVO> editScoringMasterdtl(ScoringMasterVO vo) {
		ScoringMasterDAO scoringMasterDao=(ScoringMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoringMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+scoringMasterDao.getClass()); 
		ArrayList<ScoringMasterVO> list=scoringMasterDao.editScoringMasterdtl(vo);
		return list;
	}



	
	public ArrayList<ScoringMasterVO> getScoringMasterList(ScoringMasterVO vo) {
		ScoringMasterDAO scoringMasterDao=(ScoringMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoringMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+scoringMasterDao.getClass()); 
		ArrayList<ScoringMasterVO> list=scoringMasterDao.getScoringMasterList(vo);
		return list;
	}



	
	public String saveScoringMaster(ScoringMasterVO vo) {
		ScoringMasterDAO scoringMasterDao=(ScoringMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoringMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+scoringMasterDao.getClass()); 
		String result="";
		try
		{
			userTransaction.begin();
			result=scoringMasterDao.saveScoringMaster(vo);
			if(!CommonFunction.checkNull(result).equalsIgnoreCase("saved"))
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
		return result;
	}
	
	
	public String updateScoringMaster(ScoringMasterVO vo) {
		ScoringMasterDAO scoringMasterDao=(ScoringMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoringMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+scoringMasterDao.getClass()); 
		String result="";
		try
		{
			userTransaction.begin();
			logger.info("Save method is called...............");
			result=scoringMasterDao.updateScoringMaster(vo);
			logger.info("Save method is called..............."+result);
			if(!CommonFunction.checkNull(result).equalsIgnoreCase("saved"))
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
		return result;
	}
	
	public ArrayList<ScoringMasterVO> editScoringParamValueDtl(
			ScoringMasterVO vo) {
		ScoringMasterDAO scoringMasterDao=(ScoringMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoringMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+scoringMasterDao.getClass()); 
		ArrayList<ScoringMasterVO> list=scoringMasterDao.editScoringParamValueDtl(vo);
		return list;
	}



	
	public String saveScoringMasterParamValue(ScoringMasterVO vo) {
		ScoringMasterDAO scoringMasterDao=(ScoringMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(ScoringMasterDAO.IDENTITY);
	    logger.info("Implementation class: "+scoringMasterDao.getClass()); 
		String result="";
		try
		{
			userTransaction.begin();
			result=scoringMasterDao.saveScoringMasterParamValue(vo);
			if(!CommonFunction.checkNull(result).equalsIgnoreCase("saved"))
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
		return result;
	}
	/* Code by Arun For Scoring Master ends Here*/
	
	
	// Code Start by Anil
	
	public ArrayList getRepaymentModeList() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........modifySchemeDetailsDao(Object ob)");
		MasterDAO schemeSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+schemeSearch.getClass()); 
		//MasterDAO schemeSearch = new MasterDAOImpl();
		ArrayList repaymentModeList = schemeSearch.getRepaymentModeList();
		return repaymentModeList;
	}
	//code added by manish
	public ArrayList<Object> getAddressList() 
	{
		ArrayList addrList=new ArrayList();
		logger.info("CreditProcessingMasterBussinessSessionBean...........getAddressList()");
		MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		addrList = dao.getAddressList();
		return addrList;
	}
	
	// Code End by Anil

	//start by sachin for bank branch master
	public boolean ifscBankBranchMaster(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........ifscBankBranchMaster(Object ob)");
		MasterDAO BankBranch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+BankBranch.getClass()); 
		//MasterDAO BankBranch = new MasterDAOImpl();
		boolean st =false;
		
		st = BankBranch.ifscBankBranchMaster(ob);
		
		return st;
	}
	   // saurabh changes starts
	
	public String getMakerCheckerFlag(){	
		
		logger.info(" CreditProcessingMasterBussinessSessionBean........... in getMakerCheckerFlag() ");
		
		MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation Class:::::"+dao.getClass());
		
		String makerFlag="";
		makerFlag=dao.getMakerCheckerFlag();
		return makerFlag;
	 }

	
	public String makerAuthorFlag() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........makerAuthorFlag(Object ob)");
		UserApprovalMatrixDAO flag=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
		logger.info("Implementation class: "+flag.getClass()); 
		//MasterDAO schemeSearch = new MasterDAOImpl();
		String makerflag= flag.makerAuthorFlag();
		return makerflag;
	}

	public boolean forwardUserApprovedRecords(UserApprovalMatrixVo fieldVo) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........forwardUserApprovedRecords");
		UserApprovalMatrixDAO obj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
		logger.info("Implementation class: "+obj.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = obj.forwardUserApprovedRecords(fieldVo);
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

	public ArrayList<UserApprovalMatrixVo> getAuthorSearchUser(int currentPageLink,String userId,String userRole){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getAuthorSearchUser");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass()); 
		//UserApprovalMatrixDAO newObj = new UserApprovalMatrixDAOIMPL();
		ArrayList<UserApprovalMatrixVo> list = newObj.getAuthorSearchUser(currentPageLink,userId,userRole);
		return list;
	} 
	public ArrayList getAuthorDetail(String userId, String userRole){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getAuthorDetail");
		UserApprovalMatrixDAO newObj=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
        logger.info("Implementation class: "+newObj.getClass());
		ArrayList list = newObj.getAuthorDetail(userId, userRole);
		return list;
	} 
	public String saveUserApprovalAuthor(Object ob, String userId, String role) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveUserApprovalAuthor(Object ob, String ratMet, String rwEve)");
		UserApprovalMatrixDAO saveUserApproval=(UserApprovalMatrixDAO)DaoImplInstanceFactory.getDaoImplInstance(UserApprovalMatrixDAO.IDENTITY);
		logger.info("Implementation class: "+saveUserApproval.getClass()); 
		//MasterDAO schemeInsert = new MasterDAOImpl();
		String st ="";
		try
		{
			userTransaction.begin();
			st = saveUserApproval.saveUserApprovalAuthor(ob, userId, role);
			if(st.equalsIgnoreCase("S"))
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
	// mradul mchanges starts
	public ArrayList getVehicleSegment(){
		logger.info("CreditProcessingMasterBussinessSessionBean...........getVehicleSegment(");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		ArrayList getVehicle = proSearch.getVehicleSegment();
		return getVehicle;
	}
	
//mradul changes ends
	
	public boolean saveScoringDetailsMaster(ScoringBenchmarkMasterVo vo) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........saveScoringDetailsMaster");
		MasterDAO saveScoringDetailsMaster=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+saveScoringDetailsMaster.getClass()); 
	
		boolean st =false;
			st = saveScoringDetailsMaster.saveScoringBenchmarkMaster(vo);
		return st;
	}
	
	public ArrayList getsearchScoringDetails(ScoringBenchmarkMasterVo vo) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getsearchScoringDetails");
		MasterDAO getsearchScoringDetails=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+getsearchScoringDetails.getClass()); 
		ArrayList getScorelist = getsearchScoringDetails.getScorelistDetails(vo);
		return getScorelist;
	}



	@Override
	public ArrayList searchGoldOrnamentData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........searchData(Object ob)");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		ArrayList product = proSearch.searchGoldOrnamentData(ob);
		return product;
	}



	@Override
	public String insertGoldOrnamentMaster(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........insert Gold Ornament Master(Object ob)");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		String st="";
		try
		{
			userTransaction.begin();
			st = proSearch.insertGoldOrnament(ob);
			if(CommonFunction.checkNull(st).equalsIgnoreCase("S"))
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
	public boolean updateGoldOrnamentData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........updateGoldOrnamentData(Object ob)");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = proSearch.updateGoldOrnamentData(ob);
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

	public ArrayList getGoldOrnamentCategory() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getGoldOrnamentCategory(");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		ArrayList getgoldOrnament = proSearch.getGoldOrnamentCategory();
		return getgoldOrnament;
	}
	

	@Override
	public ArrayList getGoldOrnamentType() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getGoldOrnamentType(");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		ArrayList getgoldOrnament = proSearch.getGoldOrnamentType();
		return getgoldOrnament;
	}
	
	public ArrayList getGoldOrnamentStandard() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getGoldOrnamentStandard(");
		MasterDAO proSearch=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+proSearch.getClass()); 
		//MasterDAO proSearch = new MasterDAOImpl();
		ArrayList getgoldOrnament = proSearch.getGoldOrnamentStandard();
		return getgoldOrnament;
	}
//Richa changes start.
	
	public ArrayList getUsedVehiclePricingData(Object ob) {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getUsedVehiclePricingData(Object ob)");
		MasterDAO usedVehicleDao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+usedVehicleDao.getClass()); 
		ArrayList getUsedVehiclePricingData = usedVehicleDao.getUsedVehiclePricingData(ob);
		logger.info("CreditProcessingMasterBussinessSessionBean...list size "+getUsedVehiclePricingData.size());
		return getUsedVehiclePricingData;
	}
	public String getNoOfYearAtUsedVehicle() {
		logger.info("CreditProcessingMasterBussinessSessionBean...........getNoOfYearAtUsedVehicle()");
		MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
		logger.info("Implementation class: "+dao.getClass()); 
		String noOfYear = dao.getNoOfYearAtUsedVehicle();
		return noOfYear;
	}
	public boolean insertUsedVehiclePricing(UsedVehiclePricingVo usedVehiclePricingVo){
		logger.info("..In CreditProcessingMasterBussinessSessionBean.............insertUsedVehicleMakeSearch");
		MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY); 
		logger.info("Implementation class: "+dao.getClass());
		boolean st = false;
		try
		{
			userTransaction.begin();
			st = dao.insertUsedVehiclePricing(usedVehiclePricingVo);
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
	
	public ArrayList<UsedVehiclePricingVo> selectUsedVehiclePricing(String makeModelId){
		logger.info("CreditProcessingMasterBussinessSessionBean...........get vehicle record on the basis of MODEL ID.");
		MasterDAO dao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
	    logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList<UsedVehiclePricingVo> list = dao.selectUsedVehiclePricing(makeModelId);
		
		return list;
	} 
	
	public ArrayList<UsedVehiclePricingVo> searchUsedVehiclePricing(Object ob){
		logger.info("CreditProcessingMasterBussinessSessionBean...........get vehicle record on the basis of MAKE AND MODEL.");
		MasterDAO usedVehicleDao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY);
	    logger.info("Implementation class: "+usedVehicleDao.getClass()); 
		ArrayList list = usedVehicleDao.searchUsedVehiclePricing(ob);
		logger.info("CreditProcessingMasterBussinessSessionBean...list size "+list.size());
		return list;
	} 
	
	
	
	public boolean updateVehiclePricingdata(Object ob){
		logger.info("..In CreditProcessingMasterBussinessSessionBean..............updateBranchData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateVehiclePricingdata(ob);
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
	 public boolean insertSblMaster(Object ob)
	  {
	    logger.info("CreditProcessingMasterBussinessSessionBean...........insertSblMaster(Object ob)");
	    MasterDAO proSearch = (MasterDAO)DaoImplInstanceFactory.getDaoImplInstance("MD");
	    logger.info("Implementation class: " + proSearch.getClass());

	    boolean st = false;
	    try
	    {
	      this.userTransaction.begin();
	      st = proSearch.insertSblMaster(ob);
	      if (st)
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
	  public ArrayList searchSblData(Object ob) {
	    logger.info("CreditProcessingMasterBussinessSessionBean...........searchProductData(Object ob)");
	    MasterDAO proSearch = (MasterDAO)DaoImplInstanceFactory.getDaoImplInstance("MD");
	    logger.info("Implementation class: " + proSearch.getClass());

	    ArrayList product = proSearch.searchSblData(ob);
	    return product;
	  }	
	  public boolean updateSBLData(Object ob) {
		    logger.info("CreditProcessingMasterBussinessSessionBean...........updateSBLData(Object ob)");
		    MasterDAO proSearch = (MasterDAO)DaoImplInstanceFactory.getDaoImplInstance("MD");
		    logger.info("Implementation class: " + proSearch.getClass());
		    boolean st = false;
		    try
		    {
		      this.userTransaction.begin();
		      st = proSearch.updateSBLData(ob);
		      if (st)
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
	  
	//pooja code for SBL & GBL Master

}
