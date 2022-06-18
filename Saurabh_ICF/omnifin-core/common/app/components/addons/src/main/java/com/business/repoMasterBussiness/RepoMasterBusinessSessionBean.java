package com.business.repoMasterBussiness;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

import com.connect.DaoImplInstanceFactory;
import com.repo.dao.RepoMasterDAO;
import com.repo.vo.RepoAssetChecklistVo;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RepoMasterBusinessSessionBean implements RepoMasterBusinessSessionBeanRemote{

	private static final Logger logger = Logger.getLogger(RepoMasterBusinessSessionBean.class.getName());
	 @Resource
	  private UserTransaction userTransaction;
	 RepoMasterDAO newObj=(RepoMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(RepoMasterDAO.IDENTITY); 
	
	//Richa Work space start
	//Stockyard Master start
	 public ArrayList searchStockyardMasterData(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............searchCaseTypeMasterData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.searchStockyardMasterData(ob);
			return list;
		}
		
		public boolean insertStockyardMaster(Object ob){
			logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.insertStockyardMaster(ob);
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
		
		public ArrayList editStockyardData(Object ob) {
			logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.editStockyardData(ob);
			return list;
		}
		
		public boolean updateStockyardData(Object ob){
			logger.info("..In LegalMasterBusinessSessionBean..............updateCountryData");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.updateStockyardData(ob);
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
		//Stockyard Master end
		//Richa Work space end
		
		
		//Nazia workspace start
		
		public ArrayList searchRepoAsset(Object ob) {
			logger.info("..In CommonMasterBussinessSessionBean..............searchRepoAsset");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.searchRepoAsset(ob);
			return list;
		}
		
		public boolean insertRepoAsset(Object obj,String [] checkList){
			logger.info("..In CommonMasterBussinessSessionBean..............insertRepoAsset");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.insertRepoAsset(obj,checkList);
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
		
		public ArrayList<RepoAssetChecklistVo> openEditRepoAsset(Object ob) {
			logger.info("..In CommonMasterBussinessSessionBean..............openEditRepoAsset");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList<RepoAssetChecklistVo> list= newObj.openEditRepoAsset(ob);
			return list;
		}
		
		public ArrayList getChecklistOnEdit(Object ob) {
			logger.info("..In CommonMasterBussinessSessionBean..............openEditRepoAsset");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.getChecklistOnEdit(ob);
			return list;
		}
		
		public boolean updateRepoAsset(Object ob,String [] checkList){
			logger.info("..In CommonMasterBussinessSessionBean..............updateRepoAsset");
			boolean st =false;
			try
			{
				userTransaction.begin();
				logger.info("Implementation class: "+newObj.getClass());
				st = newObj.updateRepoAsset(ob,checkList);
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
		
		public ArrayList getAssetClass() {
			logger.info("..In CommonMasterBussinessSessionBean..............searchRepoAsset");
			logger.info("Implementation class: "+newObj.getClass());
			ArrayList list= newObj.getAssetClass();
			return list;
		}
		
		//Nazia workspace end
}
