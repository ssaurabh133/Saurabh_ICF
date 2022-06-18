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
import com.masters.dao.HolidayMasterDAO;
import com.masters.dao.MasterDAO;
import com.masters.vo.BranchMasterAreaCodeVo;
import com.masters.vo.BranchMasterVo;
import com.masters.vo.HolidayMasterVo;
import com.masters.vo.SalesExecutiveMasterVo;
import com.masters.vo.IrrCalculationMasterVo;



@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CommonMasterBussinessSessionBean implements
		CommonMasterBussinessSessionBeanRemote {
	private static final Logger logger = Logger.getLogger(CommonMasterBussinessSessionBean.class.getName());
	MasterDAO newObj=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY); 
	//MasterDAO newObj = new MasterDAOImpl();	
	// Injects UserTransaction
	  @Resource
	  private UserTransaction userTransaction;
// Contry Master by Nishant Rai
public ArrayList searchCountryData(Object ob) {
		logger.info("..In CommonMasterBussinessSessionBean..............searchCountryData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchCountryData(ob);
		return list;
	}

public boolean insertCountryMaster(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............insertCountryMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertCountryMaster(ob);
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

public boolean updateCountryData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............updateCountryData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateCountryData(ob);
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

//State Master by Nishant Rai
public ArrayList searchStateData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............searchStateData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list = newObj.searchStateData(ob);
		return list;
}

public boolean updateStateData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............updateStateData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateStateData(ob);
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

public ArrayList defaultCountry1(){
		logger.info("..In CommonMasterBussinessSessionBean..............defaultCountry1()");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list = newObj.defaultCountry();
		return list;
}

public boolean insertStateMaster(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............insertStateMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertStateMaster(ob);
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

//Distric Master by Nishant Rai
public ArrayList searchDistrictData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............searchDistrictData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list = newObj.searchDistrictData(ob);
		return list;
}

public boolean updateDistrictData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............updateDistrictData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateDistrictData(ob);
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

public boolean insertDistrictMaster(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............insertDistrictMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertDistrictMaster(ob);
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

//Region Master by Nishant Rai
public ArrayList searchRegionData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............searchRegionData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list = newObj.searchRegionData(ob);
		return list;
}

public boolean updateRegionData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............updateRegionData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateRegionData(ob);
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

public boolean insertRegionMaster(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............insertRegionMaster");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertRegionMaster(ob);
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

//Branch Master by Nishant Rai
public ArrayList searchBranchData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchBranchData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchBranchData(ob);
	return list;
}

public boolean updateBranchData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateBranchData");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateBranchData(ob);
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

public ArrayList defaultCountry(){
	logger.info("..In CommonMasterBussinessSessionBean..............defaultCountry()");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.defaultCountry();
	return list;
}

public String  insertBranchMaster(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............insertBranchMaster");
	String st ="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertBranchMaster(ob);
		if(st.equalsIgnoreCase(""))
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

//Department Master by Nishant Rai
public ArrayList searchDepartmentData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchDepartmentData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchDepartmentData(ob);
	return list;
}

public boolean updateDepartmentData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateDepartmentData");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateDepartmentData(ob);
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

public String insertDepartmentMaster(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............insertDepartmentMaster");
	String st="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertDepartmentMaster(ob);
		if(!st.equalsIgnoreCase("saved"))
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

//Generic Master by Nishant Rai
public ArrayList searchGenericMasterDao(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchGenericMasterDao");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchGenericMasterDao(ob);
	return list;
}
public ArrayList modifyGenericMasterDetailsDao(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............modifyGenericMasterDetailsDao");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.modifyGenericMasterDetailsDao(ob);
	return list;
}
public boolean saveModifyGenericMasterDetailsDao(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............saveModifyGenericMasterDetailsDao");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.saveModifyGenericMasterDetailsDao(ob);
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
public String insertGenericMaster(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............saveModifyGenericMasterDetailsDao");
	String st="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertGenericMaster(ob);
		if(!st.equalsIgnoreCase("datasave"))
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

//Reason Master by Nishant Rai
public ArrayList searchReasonData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchReasonData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchReasonData(ob);
	return list;
}
public boolean updateReasonData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateReasonData");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateReasonData(ob);
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
public String insertReasonMaster(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............insertReasonMaster");
	String  st ="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertReasonMaster(ob);
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

//Agency Master by Nishant Rai
public ArrayList searchAgencyData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchAgencyData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchAgencyData(ob);
	return list;
}
public ArrayList searchAgencyDataMapping(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchAgencyDataMapping");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchAgencyDataMapping(ob);
	return list;
}
public ArrayList getAgency(){
	logger.info("..In CommonMasterBussinessSessionBean.............. getAgency()");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.getAgency();
	return list;
}
public boolean updateAgencyData(Object ob,String[] userMapping){
	logger.info("..In CommonMasterBussinessSessionBean..............updateAgencyData");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateAgencyData(ob, userMapping);
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
public String insertAgencyMaster(Object ob,String[] userMapping){
	logger.info("..In CommonMasterBussinessSessionBean..............insertAgencyMaster");
	String st ="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertAgencyMaster(ob, userMapping);
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

//DSA Dealer Master by Nishant Rai
public ArrayList searchUserEdit(String userSearchId){
	logger.info("..In CommonMasterBussinessSessionBean..............searchUserLevelEdit");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list= newObj.searchUserEdit(userSearchId);
	return list;
}

public ArrayList searchDealerData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchDealerData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchDealerData(ob);
	return list;
}
public String updateDealerData(Object ob,String[] userName){
	logger.info("..In CommonMasterBussinessSessionBean..............updateDealerData");
	String st="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateDealerData(ob,userName);
		if(!st.equalsIgnoreCase("saved"))
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
public String insertDealerMaster(Object ob,String userId){
	logger.info("..In CommonMasterBussinessSessionBean..............insertDealerMaster");
	String st = "";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertDealerMaster(ob,userId);
		if(st.equalsIgnoreCase("NONE"))
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

//Industry Master by Nishant Rai
public ArrayList searchIndustryData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchIndustryData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchIndustryData(ob);
	return list;
}
public boolean updateIndustryData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateIndustryData");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateIndustryData(ob);
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
public String insertIndustryMaster(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............insertIndustryMaster");
	String st ="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertIndustryMaster(ob);
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

//Sub Industry Master by Nishant Rai
public ArrayList searchSubIndustryData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchSubIndustryData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchSubIndustryData(ob);
	return list;
}
public String insertSubIndustryMaster(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............insertSubIndustryMaster");
	String st ="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertSubIndustryMaster(ob);
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
public boolean updateSubIndustryData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateSubIndustryData");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateSubIndustryData(ob);
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

//Ratio Definition Master by Nishant Rai
public ArrayList ratioDefinitionSearch(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............ratioDefinitionSearch");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.ratioDefinitionSearch(ob);
	return list;
}
public boolean checkExpression(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............checkExpression");
	logger.info("Implementation class: "+newObj.getClass());
	boolean st = newObj.checkExpression(ob);
	return st;
}
public boolean updateRatioDefinition(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateRatioDefinition");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateRatioDefinition(ob);
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
public boolean saveRatioDefinition(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............saveRatioDefinition");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.saveRatioDefinition(ob);
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
public ArrayList getParamDetailDetails(String sourceType){
	logger.info("..In CommonMasterBussinessSessionBean..............getParamDetailDetails");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.getParamDetailDetails(sourceType);
	return list;
}

//Financial Parameter Master by Nishant Rai
public ArrayList searchFinancialPramData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchFinancialPramData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchFinancialPramData(ob);
	return list;
}
public	ArrayList searchFinPramData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchFinPramData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchFinPramData(ob);
	return list;
}
public String insertFinancialMaster (Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............insertFinancialMaster");
	String st ="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = CommonFunction.checkNull(newObj.insertFinancialMaster(ob));
		if(st.equalsIgnoreCase("saved"))
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
public String updateFinPramData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateFinPramData");
	String st ="";
	try
	{
		userTransaction.begin();
		st = CommonFunction.checkNull(newObj.updateFinPramData(ob));
		logger.info("Implementation class: "+newObj.getClass());
		if(st.equalsIgnoreCase("saved"))
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
//Holiday Master by Nishant Rai
public boolean insertHolidayMaster(HolidayMasterVo vo){
	logger.info("..In CommonMasterBussinessSessionBean..............insertHolidayMaster");
	HolidayMasterDAO daoObj=(HolidayMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(HolidayMasterDAO.IDENTITY);
    logger.info("Implementation class: "+daoObj.getClass()); 
	//HolidayMasterDAO daoObj = new HolidayMasterDAOImpl();
	boolean st =false;
	try
	{
		userTransaction.begin();
		st = daoObj.insertHolidayMaster(vo);
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
public ArrayList searchHolidayData(HolidayMasterVo ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchHolidayData");
	HolidayMasterDAO daoObj=(HolidayMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(HolidayMasterDAO.IDENTITY);
    logger.info("Implementation class: "+daoObj.getClass()); 
	//HolidayMasterDAO daoObj = new HolidayMasterDAOImpl();
	ArrayList list = daoObj.searchHolidayData(ob);
	return list;
}
public boolean updateHolidyData(HolidayMasterVo ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateHolidyData");
	HolidayMasterDAO daoObj=(HolidayMasterDAO)DaoImplInstanceFactory.getDaoImplInstance(HolidayMasterDAO.IDENTITY);
    logger.info("Implementation class: "+daoObj.getClass()); 
	//HolidayMasterDAO daoObj = new HolidayMasterDAOImpl();
	boolean st =false;
	try
	{
		userTransaction.begin();
		st = daoObj.updateHolidyData(ob);
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

@Override
public String checkgroupName(String group) {
	logger.info("..In CommonMasterBussinessSessionBean..............checkgroupName(String group)");
	MasterDAO masterDao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY); 
	logger.info("Implementation class: "+masterDao.getClass());
	//MasterDAO masterDao = new MasterDAOImpl();
	String checkgroupName = masterDao.checkgroupName(group);
	return checkgroupName;
}

@Override
public ArrayList getAreaCode() {
	
	logger.info("..In CommonMasterBussinessSessionBean..............getAreaCode");
	MasterDAO masterDao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY); 
	logger.info("Implementation class: "+masterDao.getClass());
	//MasterDAO masterDao = new MasterDAOImpl();
	ArrayList list = masterDao.getAreaCode();
	return list;
}

public boolean insertBranchAreaCode(BranchMasterVo branchMasterVo,
		String[] areaCode){
	logger.info("..In CommonMasterBussinessSessionBean.............insertBranchAreaCode");
	MasterDAO masterDao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY); 
	logger.info("Implementation class: "+masterDao.getClass());
	//MasterDAO masterDao = new MasterDAOImpl();
	boolean st = false;
	try
	{
		userTransaction.begin();
		st = masterDao.insertBranchAreaCode(branchMasterVo,areaCode);;
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
public ArrayList<BranchMasterAreaCodeVo> searchAreaCodeBranchEdit(String userId) {

	logger.info("..In CommonMasterBussinessSessionBean.............searchAreaCodeBranchEdit");
	MasterDAO masterDao=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY); 
	logger.info("Implementation class: "+masterDao.getClass());
	//MasterDAO masterDao = new MasterDAOImpl();
	ArrayList<BranchMasterAreaCodeVo> list = masterDao. searchAreaCodeBranchEdit(userId);
	return list;
}

@Override
public String insertSubDealerMaster(Object ob) {
	logger.info("..In CommonMasterBussinessSessionBean..............insertSubDealerMaster");
	String st ="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertSubDealerMaster(ob);
		if(st.equalsIgnoreCase(""))
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

public ArrayList searchSubDealerUserEdit(String userSearchId){
	logger.info("..In CommonMasterBussinessSessionBean..............searchUserLevelEdit");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list= newObj.searchSubDealerUserEdit(userSearchId);
	return list;
}

public ArrayList searchSubDealerData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchDealerData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.searchSubDealerData(ob);
	return list;
}
public String updateSubDealerData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateDealerData");
	String st="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateSubDealerData(ob);
		if(!st.equalsIgnoreCase("saved"))
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
/*Code by arun For ratio Name List starts here*/
@Override
public ArrayList getRatioList() {
	logger.info("..In CommonMasterBussinessSessionBean..............getRatioList");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.getRatioList();
	return list;
}
@Override
public String getRatioFarmula(String ratioCode) {
	logger.info("..In CommonMasterBussinessSessionBean..............getRatioFarmula");
	logger.info("Implementation class: "+newObj.getClass());
	return newObj.getRatioFarmula(ratioCode);
}
/*Code by arun For ratio Name List starts here*/


/* code start by Asesh Kumar for benchmark ratio */
@Override
public String saveBenchmarkRatioMaster(Object ob) {
	logger.info("..In CommonMasterBussinessSessionBean..............insertSubDealerMaster");
	String st ="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.saveBenchmarkRatioMaster(ob);
		if(st.equalsIgnoreCase(""))
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


public String updateBenchMarkRatioData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateBenchMarkRatioData");
	String st="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateBenchMarkRatioData(ob);
		if(!st.equalsIgnoreCase("saved"))
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


public ArrayList getBenchmarkRatioMasterList(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............getBenchmarkRatioMasterList");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.getBenchmarkRatioMasterList(ob);
	return list;
}

public ArrayList searchBenchMarkRatioEdit(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchUserLevelEdit");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list= newObj.searchBenchMarkRatioEdit(ob);
	return list;
}
// code end by asesh 
public boolean finCheckExpression(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............checkExpression");
	logger.info("Implementation class: "+newObj.getClass());
	boolean st = newObj.finCheckExpression(ob);
	return st;
}

// Code added by Asesh Kumar
@Override
public String saveBusinessMonthClosureDetails(Object ob) {
	logger.info("..In CommonMasterBussinessSessionBean..............saveBusinessMonthClosureDetails");
	String st ="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.saveBusinessMonthClosureDetails(ob);
		if(st.equalsIgnoreCase(""))
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


public String updateBusinessMonthClosureData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............updateBusinessMonthClosureData");
	String st="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateBusinessMonthClosureData(ob);
		if(!st.equalsIgnoreCase("saved"))
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

public ArrayList getBusinessMonthList(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............getBusinessMonthList");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.getBusinessMonthList(ob);
	return list;
}
public ArrayList searchBusinessMonthClosureEdit(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchBusinessMonthClosureEdit");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list= newObj.searchBusinessMonthClosureEdit(ob);
	return list;
}
public String getStartDate(String businessMonthss,String businessYear){
	logger.info("..In CommonMasterBussinessSessionBean..............getStartDate");
	logger.info("Implementation class: "+newObj.getClass());
	String startDate = newObj.getStartDate(businessMonthss,businessYear);
	return startDate;
}

//Manish Work space start

public ArrayList rateApprovalMakerSearch(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............searchBusinessMonthClosureEdit");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list= newObj.rateApprovalMakerSearch(ob);
	return list;
}

public boolean saveRateApprovalMakerData(Object ob){
	logger.info("..In CommonMasterBusinessSessionBean..............insertrateApproval");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.saveRateApprovalMakerData(ob);
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

public ArrayList openEditRateApprovalMakerData(Object ob) {
	logger.info("..In LegalMasterBusinessSessionBean..............editCaseTypeData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list= newObj.openEditRateApprovalMakerData(ob);
	return list;
}

public boolean updateRateApprovalMakerData(Object ob){
	logger.info("..In LegalMasterBusinessSessionBean..............updateCountryData");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateRateApprovalMakerData(ob);
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
}//manish work space end for Maker


//manish Starts for Checker
public ArrayList rateApprovalCheckerSearch(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............Rate Approval checker");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list= newObj.rateApprovalCheckerSearch(ob);
	return list;
}
public ArrayList openRateApprovalCheckerData(Object ob) {
	logger.info("...In CommonMasterBussinessSessionBean..............openRateApprovalCheckerData");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list= newObj.openRateApprovalCheckerData(ob);
	return list;
}
public boolean saveRateApprovalChecker(Object ob){
	logger.info("..In LegalMasterBusinessSessionBean..............insertCaseTypeMaster");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.saveRateApprovalChecker(ob);
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
public String saveSalesExecutive(Object ob) {

		logger.info("..In CommonMasterBussinessSessionBean..............saveSalesExecutive");
		String st ="";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.saveSalesExecutive(ob);
			logger.info("st sachhhhhh : " + st);
			if(st.equalsIgnoreCase(""))
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

@Override
public String updateSalesExecutive(Object ob ) {
	logger.info("..In CommonMasterBussinessSessionBean..............updateBusinessMonthClosureData");
	String st="";
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.updateSalesExecutive(ob);
		if(!st.equalsIgnoreCase("saved"))
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
	
	// TODO Auto-generated method stub
	return st;
}


public ArrayList<SalesExecutiveMasterVo> salesExecutiveEdit(Object ob)
{
		logger.info("..In CommonMasterBussinessSessionBean..............searchBusinessMonthClosureEdit");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.salesExecutiveEdit(ob);
		return list;
}


public ArrayList getSalesExecutiveList(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............getBusinessMonthList");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.getSalesExecutiveList(ob);
	return list;
}


public ArrayList getEmployeeTypeList(){
	logger.info("..In CommonMasterBussinessSessionBean..............getEmployeeTypeList");
	logger.info("Implementation class: "+newObj.getClass());
	ArrayList list = newObj.getEmployeeTypeList();
	return list;
}

//Consortium Master by Parvez
public ArrayList searchConsortiumData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............searchConsortiumData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list = newObj.searchConsortiumData(ob);
		return list;
}

public boolean updateConsortiumData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............updateConsortiumData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateConsortiumData(ob);
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

public boolean insertConsortiumData(Object ob){
	logger.info("..In CommonMasterBussinessSessionBean..............insertConsortiumData");
	boolean st =false;
	try
	{
		userTransaction.begin();
		logger.info("Implementation class: "+newObj.getClass());
		st = newObj.insertConsortiumPartner(ob);
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



//gold Ornament Master by Parvez
public ArrayList searchGoldOrnamentData(Object ob){
		logger.info("..In CommonMasterBussinessSessionBean..............searchGoldOrnamentData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list = newObj.searchGoldOrnamentData(ob);
		return list;
}


/*      */   public String updateIrrCalMaster(IrrCalculationMasterVo vo)
/*      */   {
/* 1564 */     logger.info("..In CommonMasterBussinessSessionBean..............saveIrrCalMaster");
/* 1565 */     String st = "ER";
/*      */     try
/*      */     {
/* 1568 */       this.userTransaction.begin();
/* 1569 */       MasterDAO newObj = (MasterDAO)DaoImplInstanceFactory.getDaoImplInstance("MD");
/*      */ 
/* 1571 */       st = newObj.updateIrrMaster(vo);
/* 1572 */       if (CommonFunction.checkNull(st).trim().equalsIgnoreCase("S"))
/*      */       {
/* 1574 */         logger.info("before commit");
/* 1575 */         this.userTransaction.commit();
/*      */       }
/*      */       else
/*      */       {
/* 1579 */         logger.info("before rollback");
/* 1580 */         this.userTransaction.rollback();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1585 */       logger.info("Exception: " + e);
/*      */     }
/* 1587 */     return st;
/*      */   }
/*      */ 
/*      */   public ArrayList<IrrCalculationMasterVo> getIrrChargeDetail(Object ob)
/*      */   {
/* 1592 */     logger.info("..In getIrrChargeDetail() of CommonMasterBussinessSessionBean.");
/* 1593 */     MasterDAO newObj = (MasterDAO)DaoImplInstanceFactory.getDaoImplInstance("MD");
/* 1594 */     ArrayList list = newObj.getIrrChargeDetail(ob);
/* 1595 */     return list;
/*      */   }
/*      */ 
/*      */   public ArrayList<IrrCalculationMasterVo> irrModifyChargeCodeDetailsDao(Object ob)
/*      */   {
/* 1600 */     logger.info("..In CommonMasterBussinessSessionBean..............irrModifyChargeCodeDetailsDao");
/* 1601 */     MasterDAO newObj = (MasterDAO)DaoImplInstanceFactory.getDaoImplInstance("MD");
/* 1602 */     ArrayList list = newObj.irrModifyChargeCodeDetailsDao(ob);
/* 1603 */     return list;
/*      */   }
/*      */ 
/*      */   public ArrayList searchIrrCalculationData(IrrCalculationMasterVo vo)
/*      */   {
/* 1609 */     return null;
/*      */   }
/*      */ 
/*      */   public ArrayList<IrrCalculationMasterVo> searchIrrCalData(Object ob)
/*      */   {
/* 1614 */     logger.info("..In CommonMasterBussinessSessionBean..............searchIrrCalData()");
/* 1615 */     MasterDAO newObj = (MasterDAO)DaoImplInstanceFactory.getDaoImplInstance("MD");
/* 1616 */     ArrayList list = newObj.searchIrrCalData(ob);
/* 1617 */     return list;
/*      */   }
/*      */ 
/*      */   public String saveIrrCalMaster(IrrCalculationMasterVo vo)
/*      */   {
/* 1623 */     logger.info("..In CommonMasterBussinessSessionBean..............saveIrrCalMaster");
/* 1624 */     String st = "ER";
/*      */     try
/*      */     {
/* 1627 */       this.userTransaction.begin();
/* 1628 */       MasterDAO newObj = (MasterDAO)DaoImplInstanceFactory.getDaoImplInstance("MD");
/* 1629 */       st = newObj.insertIrrMaster(vo);
/* 1630 */       if (CommonFunction.checkNull(st).trim().equalsIgnoreCase("S"))
/*      */       {
/* 1632 */         logger.info("before commit");
/* 1633 */         this.userTransaction.commit();
/*      */       }
/*      */       else
/*      */       {
/* 1637 */         logger.info("before rollback");
/* 1638 */         this.userTransaction.rollback();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1643 */       logger.info("Exception: " + e);
/*      */     }
/* 1645 */     return st;
/*      */   }

}
