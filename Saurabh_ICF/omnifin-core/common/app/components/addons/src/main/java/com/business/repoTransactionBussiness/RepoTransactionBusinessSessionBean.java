package com.business.repoTransactionBussiness;


import java.util.ArrayList;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

import com.connect.DaoImplInstanceFactory;
import com.repo.dao.RepoTransactionDAO;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RepoTransactionBusinessSessionBean implements RepoTransactionBusinessSessionBeanRemote{

	private static final Logger logger = Logger.getLogger(RepoTransactionBusinessSessionBean.class.getName());
	 @Resource
	  private UserTransaction userTransaction;
	 RepoTransactionDAO newObj=(RepoTransactionDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoTransactionDAO.IDENTITY); 
	 
	 //Vinod workspace start
	
	 public ArrayList searchRepoDetailbyAgency(Object ob) {
			logger.info("## In searchRepoDetailbyAgency() : ");
			logger.info("## In searchRepoDetailbyAgency() : newObj.getClass() : ==>> "+newObj.getClass());
			ArrayList list= newObj.searchRepoDetailbyAgency(ob);
			return list;
	}
	 
	 public ArrayList getRepoDetailbyAgencyForEdit(Object ob) {
			logger.info("## In getRepoDetailbyAgencyForEdit() : ");
			logger.info("## In getRepoDetailbyAgencyForEdit() : newObj.getClass() : ==>> "+newObj.getClass());
			ArrayList list= newObj.getRepoDetailbyAgencyForEdit(ob);
			return list;
	}
	 
	 public ArrayList getCheckListDetail(Object ob) {
			logger.info("## In getCheckListDetail() : ");
			logger.info("## In getCheckListDetail() : newObj.getClass() : ==>> "+newObj.getClass());
			ArrayList list= newObj.getCheckListDetail(ob);
			return list;
	}
	 
	 public boolean saveRepoDetailsByAgency(Object ob,String [] checkList,String [] statusList,String [] remarkList){
		 logger.info("## In saveRepoDetailsByAgency() : ");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("## In saveRepoDetailsByAgency () : Implementation class: "+newObj.getClass());
				st = newObj.saveRepoDetailsByAgency(ob,checkList,statusList,remarkList);
				if(st)
				{
					logger.info("## In saveRepoDetailsByAgency () : before commit");
					userTransaction.commit();
				}
				else
				{
									
					logger.info("## In saveRepoDetailsByAgency () : before rollback");
					userTransaction.rollback();
				}
			}
			catch(Exception e)
			{
				logger.info("## In saveRepoDetailsByAgency () : Exception: "+e);
			}
			return st;
	}

	//Vinod workspace end
	 
	 //Richa workspace start
	 
	 public ArrayList searchRepoDetailByStockyard(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.searchRepoDetailByStockyard(ob);
			return list;
		}

		public ArrayList openEditRepoDetailByStockyard(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.openEditRepoDetailByStockyard(ob);
			return list;
		}

		public ArrayList getCheckListForStockyard(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.getCheckListForStockyard(ob);
			return list;
		}

		public boolean insertRepoDetailByStockyard(Object ob,String [] checkList,String [] checkListStatusList,String [] checkListRemarksList){
			logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.insertRepoDetailByStockyard( ob, checkList, checkListStatusList, checkListRemarksList);
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
		
		public ArrayList searchRepoMarkingMaker(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.searchRepoMarkingMaker(ob);
			return list;
		}

		public ArrayList openEditRepoMarkingMaker(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.openEditRepoMarkingMaker(ob);
			return list;
		}

		public boolean insertRepoMarkingMaker(Object ob){
			logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.insertRepoMarkingMaker(ob);
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

		public boolean updateRepoMarkingMaker(Object ob){
			logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.updateRepoMarkingMaker(ob);
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

		public ArrayList getDetailofLoanForRepoMarkingMaker(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList subloanList= newObj.getDetailofLoanForRepoMarkingMaker(ob);
			return subloanList;
		}
		
		public ArrayList searchRepoMakingAuthor(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.searchRepoMarkingAuthor(ob);
			return list;
		}
		public ArrayList openEditRepoMarkingAuthorCase(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.openEditRepoMarkingAuthorCase(ob);
			return list;
		}

		public boolean insertRepoMarkingAuthorCase(Object ob){
			logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.insertRepoMarkingAuthorCase(ob);
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
	 
	 //Richa workspace end
		
	//Nazia workspace start
		
		public ArrayList searchRepoConfirmation(Object ob) {
			logger.info("..In RepoTransactionBusinessSessionBean..............searchRepoConfirmation");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.searchRepoConfirmation(ob);
			return list;
		}
	
	 public ArrayList openEditRepoConfirmation(Object ob) {
			logger.info("..In RepoTransactionBusinessSessionBean..............openEditRepoConfirmation");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.openEditRepoConfirmation(ob);
			return list;
		}
	 
	 public ArrayList getCheckListOfAgency(Object ob) {
			logger.info("## In getCheckListForStockyard() : ");
			logger.info("## In getCheckListForStockyard() : Implementation class: "+newObj.getClass());
			ArrayList list= newObj.getCheckListOfAgency(ob);
			return list;
		}

	 
	 public ArrayList getCheckListOfStockyard(Object ob) {
			logger.info("## In getCheckListForStockyard() : ");
			logger.info("## In getCheckListForStockyard() : Implementation class: "+newObj.getClass());
			ArrayList list= newObj.getCheckListOfStockyard(ob);
			return list;
		}
	 
	 public boolean saveRepoConfirmationDetail(Object ob){
			logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.saveRepoConfirmationDetail(ob);
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
		
		//Nazia workspace end
	
	
}
