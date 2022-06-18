package com.business.ejbClient;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

import com.connect.DaoImplInstanceFactory;
import com.masters.dao.MasterDAO;
import com.masters.vo.AutoAllocationDefinitionVo;
import com.masters.vo.GcdGroupMasterVo;
import com.masters.vo.NPAMasterVo;
import com.masters.vo.MobileUserMappingVo;
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CreditManagementBussinessSessionBean implements
		CreditManagementBussinessSessionBeanRemote {

	private static final Logger logger = Logger.getLogger(CreditManagementBussinessSessionBean.class.getName());
	 @Resource
	  private UserTransaction userTransaction;
	 MasterDAO newObj=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY); 
		//MasterDAO newObj = new MasterDAOImpl();
	
	//---------------------NPA Stage Master-----------by Nishant Rai----
	public ArrayList<NPAMasterVo> searchNPAStageData(Object ob){
		logger.info("CreditManagementBussinessSessionBean...........searchNPAStageData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<NPAMasterVo> list = newObj.searchNPAStageData(ob);
		return list;
	}
	public ArrayList<NPAMasterVo> getProductId(){
	//	logger.info("CreditManagementBussinessSessionBean...........searchNPAStageData");
		//logger.info("Implementation class: "+newObj.getClass());
		//ArrayList<NPAMasterVo> list = newObj.getProductId();
		return newObj.getProductId();
	}
	
	
	public String getProduct(String str)
	{
		
		return newObj.getProduct(str);
	}
	
	public String insertNPAStageMaster(Object ob){
		logger.info("CreditManagementBussinessSessionBean...........insertNPAStageMaster");
		String st ="";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertNPAStageMaster(ob);
			if(st.equalsIgnoreCase("SAVE"))
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
	public ArrayList<NPAMasterVo> getNPAStageData(){
		logger.info("CreditManagementBussinessSessionBean...........getNPAStageData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<NPAMasterVo> list = newObj.getNPAStageData();
		return list;
	}
	public String updateNPAStageData(Object ob){
		logger.info("CreditManagementBussinessSessionBean...........updateNPAStageData");
		String st ="";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateNPAStageData(ob);
			if(st.equalsIgnoreCase("UPDATE"))
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
	
	//---------------------GCD Group Master-----------by Nishant Rai----
	public String insertGroupCodeMaster(GcdGroupMasterVo gcdGroupMasterVo){
		logger.info("CreditManagementBussinessSessionBean...........insertGroupCodeMaster");
		String st ="";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertGroupCodeMaster(gcdGroupMasterVo);
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
	public ArrayList <GcdGroupMasterVo>  modifyGcdGroupDetailsDao(Object ob){
		logger.info("CreditManagementBussinessSessionBean........... modifyGcdGroupDetailsDao");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<GcdGroupMasterVo> list = newObj.modifyGcdGroupDetailsDao(ob);
		return list;
	}
	public String saveModifyGcdGroupDao(Object ob){
		logger.info("CreditManagementBussinessSessionBean...........saveModifyGcdGroupDao");
		String st = "";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.saveModifyGcdGroupDao(ob);
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
	public ArrayList <GcdGroupMasterVo> searchGcdGroupData(Object ob){
		logger.info("CreditManagementBussinessSessionBean...........searchGcdGroupData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<GcdGroupMasterVo> list = newObj.searchGcdGroupData(ob);
		return list;
	}
	//ravi start
	public String saveAutoAllocationDetailBean(Object ob){
		logger.info("CreditManagementBussinessSessionBean...........saveAutoAllocationDetailBean");
		String st ="";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.saveAutoAllocationDefDetail(ob);
			if(st.equalsIgnoreCase("SAVE"))
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
	
	public ArrayList<AutoAllocationDefinitionVo> getAutoAllocationDefDataBean(Object ob){
		logger.info("CreditManagementBussinessSessionBean...........getAutoAllocationDefDataBean");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AutoAllocationDefinitionVo> list = newObj.getAutoAllocationDefData(ob);
		return list;
	}
	
	public ArrayList<AutoAllocationDefinitionVo> searchAutoAllocationBean(Object ob){
		logger.info("CreditManagementBussinessSessionBean...........searchAutoAllocationBean");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AutoAllocationDefinitionVo> list = newObj.searchAutoAllocationDefData(ob);
		return list;
	}
	
	public String updateAutoAllocationDetailBean(Object ob){
		logger.info("CreditManagementBussinessSessionBean...........updateAutoAllocationDetailBean");
		String st ="";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateAutoAllocationDefDetail(ob);
			if(st.equalsIgnoreCase("UPDATE"))
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
	public String insertMobileUserMaster(MobileUserMappingVo mobileUserMappingVo, String bDate)
	   {
	     logger.info("CreditManagementBussinessSessionBean...........insertMobileUserMaster");
	     String st = "";
	     try
	     {
	       this.userTransaction.begin();
	       logger.info("Implementation class: " + this.newObj.getClass());
	       st = this.newObj.insertMobileUserMaster(mobileUserMappingVo, bDate);
	 
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
	 
	   public ArrayList<MobileUserMappingVo> searchMobileUserData(Object ob)
	   {
	     logger.info("CreditManagementBussinessSessionBean...........searchMobileUserData");
	     logger.info("Implementation class: " + this.newObj.getClass());
	     ArrayList list = this.newObj.searchMobileUserData(ob);
	     return list;
	   }
	 
	   public ArrayList<MobileUserMappingVo> modifyMobileUSerDetailsDao(Object ob) {
	     logger.info("CreditManagementBussinessSessionBean...........modifyMobileUSerDetailsDao");
	     logger.info("Implementation class: " + this.newObj.getClass());
	     ArrayList list = this.newObj.modifyMobileUSerDetailsDao(ob);
	     return list;
	   }
	 
	   public String saveModifyMobileMasterDao(Object ob, String s)
	   {
	     logger.info("CreditManagementBussinessSessionBean...........saveModifyMobileMasterDao");
	     String st = "";
	     try
	     {
	       this.userTransaction.begin();
	       logger.info("Implementation class: " + this.newObj.getClass());
	       st = this.newObj.saveModifyMobileMasterDao(ob, s);
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
	 
	   public String getMobileNoMasterDao(Object ob)
	   {
	     logger.info("CreditManagementBussinessSessionBean...........saveModifyMobileMasterDao");
	     String st = "";
	     try
	     {
	       this.userTransaction.begin();
	       logger.info("Implementation class: " + this.newObj.getClass());
	       st = this.newObj.getMobileNoMasterDao(ob);
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
