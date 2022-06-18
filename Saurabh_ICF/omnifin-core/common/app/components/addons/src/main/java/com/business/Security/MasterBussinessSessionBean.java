package com.business.Security;

import java.util.ArrayList;


import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import com.connect.DaoImplInstanceFactory;
import com.masters.dao.MasterDAO;
import com.masters.dao.ReportFunctionAccessDAO;
import com.masters.vo.ChangePasswordMasterVo;
import com.masters.vo.ReportFunctionAccessVo;
import com.masters.vo.RoleAccessMasterVo;
import com.masters.vo.RoleMasterVo;
import com.masters.vo.UserAccessMasterVo;
import com.masters.vo.UserMasterVo;
import com.masters.dao.DumpAccessDAO;
import com.masters.vo.DumpFunctionAccessVo;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class MasterBussinessSessionBean implements MasterBussinessSessionBeanRemote{

	private static final Logger logger = Logger.getLogger(MasterBussinessSessionBean.class.getName());
	 @Resource
	  private UserTransaction userTransaction;
	MasterDAO newObj=(MasterDAO)DaoImplInstanceFactory.getDaoImplInstance(MasterDAO.IDENTITY); 
	
	//MasterDAO newObj = new MasterDAOImpl();
	
	//--------------Role Module Master-----------------By Nishant Rai
	public boolean insertRoleMaster(Object ob){
		logger.info("..In MasterBussinessSessionBean..............insertRoleMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());	
			st = newObj.insertRoleMaster(ob);
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
	public boolean updateRoleData(Object ob){
		logger.info("..In MasterBussinessSessionBean..............updateRoleData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateRoleData(ob);
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
	public ArrayList<RoleMasterVo> searchRoleData(Object Ob){
		logger.info("..In MasterBussinessSessionBean..............searchRoleData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<RoleMasterVo> list= newObj.searchRoleData(Ob);
		return list;
	}
	
	//--------------Role Access Master-----------------By Nishant Rai
	public boolean insertRoleAccessMaster(Object ob,String[] checkbox){
		logger.info("..In MasterBussinessSessionBean..............insertRoleAccessMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertRoleAccessMaster(ob, checkbox);
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
	public ArrayList<RoleAccessMasterVo> searchRoleAccessData(Object Ob){
		logger.info("..In MasterBussinessSessionBean..............searchRoleAccessData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<RoleAccessMasterVo> list= newObj.searchRoleAccessData(Ob);
		return list;
	}
	
	//--------------User Master-----------------By Nishant Rai
	public String insertUserMaster(Object ob,String[] branchName,String[] levelName){
		logger.info("..In MasterBussinessSessionBean..............insertUserMaster");
		String st = "";
		boolean branchStatus = false;
		boolean levelStatus = false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertUserMaster(ob,branchName,levelName);
			branchStatus=newObj.insertUserBranch(ob,branchName);
			UserMasterVo userMasterVo=(UserMasterVo)ob;
			if(userMasterVo.getSelection().length>0)			
				levelStatus=newObj.insertUserLevel(ob,levelName);
			else
				levelStatus=true;
			if(!st.equalsIgnoreCase("") && levelStatus && branchStatus)
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
			logger.info("before rollback");
			try {
				userTransaction.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			logger.info("Exception: "+e);
		}
		return st;
}
	public boolean checkEmpIDInUserMaster(Object ob){
		logger.info("..In MasterBussinessSessionBean..............checkEmpIDInUserMaster");
		logger.info("Implementation class: "+newObj.getClass());
		boolean st = newObj.checkEmpIDInUserMaster(ob);
		return st;
}
	public boolean checkEmpIDInEditUserMaster(Object ob){
		logger.info("..In MasterBussinessSessionBean..............checkEmpIDInEditUserMaster");
		logger.info("Implementation class: "+newObj.getClass());
		boolean st = newObj.checkEmpIDInEditUserMaster(ob);
		return st;
}
	public boolean checkReportingToUserStatus(Object ob){
		logger.info("..In MasterBussinessSessionBean..............checkReportingToUserStatus");
		logger.info("Implementation class: "+newObj.getClass());
		boolean st = newObj.checkReportingToUserStatus(ob);
		return st;
}
	public boolean updateUserData(Object ob,String[] branchName,String[] levelName){
		logger.info("..In MasterBussinessSessionBean..............updateUserData");
		boolean st =false;
		boolean branchStatus=false;
		boolean levelStatus=false;
		boolean branchDeleteStatus=false;
		boolean levelDeleteStatus=false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			branchDeleteStatus = newObj.deleteBranchData(ob);
			branchStatus=newObj.updateUserData1(ob,branchName);
			levelDeleteStatus = newObj.deleteLevelData(ob);
			levelStatus=newObj.updateUserLevel(ob,levelName);
			st = newObj.updateUserData(ob);
			
			logger.info("Status of operation: "+branchDeleteStatus +" "+ st +" "+ levelStatus +" "+ branchStatus +" "+levelDeleteStatus);
			if(branchDeleteStatus && st && levelStatus && branchStatus && levelDeleteStatus)
			{
			
				logger.info("before commit");
				userTransaction.commit();
				st=true;
				
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
				st=false;
			}
		}
		catch(Exception e)
		{
			st=false;
			logger.info("before rollback");
			try {
				userTransaction.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			logger.info("Exception: "+e);
		}
		return st;
}
	public ArrayList<UserMasterVo> searchUserData(Object Ob){
		logger.info("..In MasterBussinessSessionBean..............searchUserData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<UserMasterVo> list= newObj.searchUserData(Ob);
		return list;
	}
	public String updateUserPassword(Object ob){
		logger.info("..In MasterBussinessSessionBean..............updateUserPassword");
		String st = "";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateUserPassword(ob);
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
	public boolean insertUserBranch(Object ob,String[] branchName){
		logger.info("..In MasterBussinessSessionBean..............insertUserBranch");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertUserBranch(ob, branchName);
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
	public ArrayList<UserMasterVo> searchUserBranchEdit(String userSearchId){
		logger.info("..In MasterBussinessSessionBean..............searchUserBranchEdit");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<UserMasterVo> list= newObj.searchUserBranchEdit(userSearchId);
		return list;
	}
	public boolean updateUserData1(Object ob,String[] branchName){
		logger.info("..In MasterBussinessSessionBean..............updateUserData1");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateUserData1(ob, branchName);
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
	public ArrayList<UserMasterVo> searchUserLevelEdit(String userSearchId){
		logger.info("..In MasterBussinessSessionBean..............searchUserLevelEdit");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<UserMasterVo> list= newObj.searchUserLevelEdit(userSearchId);
		return list;
	}
	public boolean insertUserLevel(UserMasterVo userMasterVo, String[] levelName){
		logger.info("..In MasterBussinessSessionBean..............insertUserLevel");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertUserLevel(userMasterVo, levelName);
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
	public int counthieirarchyusers(UserMasterVo userMasterVo){
		logger.info("..In MasterBussinessSessionBean..............counthieirarchyusers");
		logger.info("Implementation class: "+newObj.getClass());
		int cnt = newObj.counthieirarchyusers(userMasterVo);
		return cnt;
}
	public boolean updateUserLevel(UserMasterVo userMasterVo, String[] levelName){
		logger.info("..In MasterBussinessSessionBean..............updateUserLevel");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateUserLevel(userMasterVo, levelName);
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
	
	//--------------User Role Module Mapping Master-----------------By Nishant Rai
	public boolean insertUserAccessMaster(Object ob){
		logger.info("..In MasterBussinessSessionBean..............insertUserAccessMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.insertUserAccessMaster(ob);
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
	public boolean updateUserAccessData(Object ob){
		logger.info("..In MasterBussinessSessionBean..............updateUserAccessData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.updateUserAccessData(ob);
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
	public ArrayList <UserAccessMasterVo> searchUserAccessData(Object Ob){
		logger.info("..In MasterBussinessSessionBean..............searchUserAccessData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<UserAccessMasterVo> list= newObj.searchUserAccessData(Ob);
		return list;
	}
	
	//--------------Report Function Access Master-----------------By Nishant Rai
	public ArrayList<ReportFunctionAccessVo> getReportName(Object ob){
		logger.info("..In MasterBussinessSessionBean..............getReportName");
		ReportFunctionAccessDAO newObj=(ReportFunctionAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportFunctionAccessDAO.IDENTITY);
		logger.info("Implementation class........"+ReportFunctionAccessDAO.class);
		//ReportFunctionAccessDAO newObj = new ReportFunctionAccessDAOImpl();
		ArrayList<ReportFunctionAccessVo> list= newObj.getReportName(ob);
		return list;
	} 
	public boolean deleteReportFunction(String moduleId,String roleId){
		logger.info("..In MasterBussinessSessionBean..............deleteReportFunction");
		ReportFunctionAccessDAO newObj=(ReportFunctionAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportFunctionAccessDAO.IDENTITY);
		logger.info("Implementation class........"+ReportFunctionAccessDAO.class);
		//ReportFunctionAccessDAO newObj = new ReportFunctionAccessDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.deleteReportFunction(moduleId, roleId);
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
	public boolean insertReport(String moduleId, String roleId, String[] checkedList,String[] unCheckedList){
		logger.info("..In MasterBussinessSessionBean..............insertReport");
		ReportFunctionAccessDAO newObj=(ReportFunctionAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportFunctionAccessDAO.IDENTITY);
		logger.info("Implementation class........"+ReportFunctionAccessDAO.class);
		//ReportFunctionAccessDAO newObj = new ReportFunctionAccessDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.insertReport(moduleId, roleId, checkedList, unCheckedList);
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
	
	//--------------Change Password-----------------By Nishant Rai
	public boolean updateChangePassword(Object ob){
		logger.info("..In MasterBussinessSessionBean..............updateChangePassword");
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.updateChangePassword(ob);
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
    public int countChangePassword(Object ob){
		logger.info("..In MasterBussinessSessionBean..............countChangePassword");
		int st = newObj.countChangePassword(ob);
		return st;
}
    public String getlogintimeid(String userName){
		logger.info("..In MasterBussinessSessionBean..............getlogintimeid");
		String st = newObj.getlogintimeid(userName);
		return st;
}
    public int countQuestion(Object ob){
		logger.info("..In MasterBussinessSessionBean..............countQuestion");
		int st = newObj.countQuestion(ob);
		return st;
}
    public String getbeforemakerdate(){
		logger.info("..In MasterBussinessSessionBean..............getbeforemakerdate");
		String st = newObj.getbeforemakerdate();
		return st;
}
    public boolean updateloginChangePassword(ChangePasswordMasterVo changePasswordMasterVo){
		logger.info("..In MasterBussinessSessionBean..............updateloginChangePassword");
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.updateloginChangePassword(changePasswordMasterVo);
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
  //--------------Dump  Function Access Master-----------------by awadhesh
	public ArrayList<DumpFunctionAccessVo> getDumpReportName(Object ob){
		logger.info("..In MasterBussinessSessionBean..............getDumpReportName");
		DumpAccessDAO newObj=(DumpAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(DumpAccessDAO.IDENTITY);
		logger.info("Implementation class........"+ReportFunctionAccessDAO.class);
		//ReportFunctionAccessDAO newObj = new ReportFunctionAccessDAOImpl();
		ArrayList<DumpFunctionAccessVo> list= newObj.getDumpName(ob);
		return list;
	} 
	public boolean deleteDumpFunction(String moduleId,String roleId){
		logger.info("..In MasterBussinessSessionBean..............deleteDumpFunction");
		DumpAccessDAO newObj=(DumpAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(DumpAccessDAO.IDENTITY);
		logger.info("Implementation class........"+ReportFunctionAccessDAO.class);
		//ReportFunctionAccessDAO newObj = new ReportFunctionAccessDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.deleteDumpFunction(moduleId, roleId);
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
	public boolean insertDump(String moduleId, String roleId, String[] checkedList,String[] unCheckedList){
		logger.info("..In MasterBussinessSessionBean..............insertDump");
		DumpAccessDAO newObj=(DumpAccessDAO)DaoImplInstanceFactory.getDaoImplInstance(DumpAccessDAO.IDENTITY);
		logger.info("Implementation class........"+ReportFunctionAccessDAO.class);
		//ReportFunctionAccessDAO newObj = new ReportFunctionAccessDAOImpl();
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.insertDump(moduleId, roleId, checkedList, unCheckedList);
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
	//--------------Dump  Function Access Master-----------------by awadhesh ...code end
    
    
}
