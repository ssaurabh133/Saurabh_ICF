package com.business.Security;

import java.util.ArrayList;




import javax.ejb.Remote;

import com.masters.vo.ChangePasswordMasterVo;
import com.masters.vo.ReportFunctionAccessVo;
import com.masters.vo.RoleAccessMasterVo;
import com.masters.vo.RoleMasterVo;
import com.masters.vo.UserAccessMasterVo;
import com.masters.vo.UserMasterVo;
import com.masters.vo.DumpFunctionAccessVo;

@Remote
public interface MasterBussinessSessionBeanRemote {
	
	String REMOTE_IDENTITY="MASTERBUSSINESSMASTERREMOTE";

	//--------------Role Module Master-----------------By Nishant Rai
	//boolean inseDumpFunctionAccessVortRoleMaster(Object ob);
	boolean updateRoleData(Object ob);
	ArrayList <RoleMasterVo> searchRoleData(Object Ob);
	boolean insertRoleMaster(Object ob);
	//--------------Role Access Master-----------------By Nishant Rai
	boolean insertRoleAccessMaster(Object ob,String[] checkbox);
	ArrayList<RoleAccessMasterVo> searchRoleAccessData(Object Ob);
	
	//--------------User Master-----------------By Nishant Rai
	String insertUserMaster(Object ob,String[] branchName,String[] levelName);
	boolean checkEmpIDInUserMaster(Object ob);
	boolean checkEmpIDInEditUserMaster(Object ob);
	boolean checkReportingToUserStatus(Object ob);
	boolean updateUserData(Object ob,String[] branchName,String[] levelName);
	ArrayList <UserMasterVo> searchUserData(Object Ob);
	String updateUserPassword(Object ob);
	boolean insertUserBranch(Object ob,String[] branchName);
	ArrayList <UserMasterVo> searchUserBranchEdit(String userSearchId);
	boolean updateUserData1(Object ob,String[] branchName);
	ArrayList<UserMasterVo> searchUserLevelEdit(String userSearchId);
	boolean insertUserLevel(UserMasterVo userMasterVo, String[] levelName);
	int counthieirarchyusers(UserMasterVo userMasterVo);
	boolean updateUserLevel(UserMasterVo userMasterVo, String[] levelName);
	
	//--------------User Role Module Mapping Master-----------------By Nishant Rai
	boolean insertUserAccessMaster(Object ob);
	boolean updateUserAccessData(Object ob);
	ArrayList <UserAccessMasterVo> searchUserAccessData(Object Ob);
	
	//--------------Report Function Access Master-----------------By Nishant Rai
	ArrayList<ReportFunctionAccessVo>getReportName(Object ob); 
	boolean deleteReportFunction(String moduleId,String roleId);
	boolean insertReport(String moduleId, String roleId, String[] checkedList,String[] unCheckedList);
	
	//--------------Change Password-----------------By Nishant Rai
	boolean updateChangePassword(Object ob);
    int countChangePassword(Object ob);
    String getlogintimeid(String userName);
    int countQuestion(Object ob);
    String getbeforemakerdate();
    boolean updateloginChangePassword(ChangePasswordMasterVo changePasswordMasterVo);
   //.............dump access master................by awadhesh vishwakarma 
     ArrayList<DumpFunctionAccessVo> getDumpReportName(Object paramObject);
     public abstract boolean deleteDumpFunction(String paramString1, String paramString2);
      public abstract boolean insertDump(String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2);
 
	//.............dump access master................by awadhesh vishwakarma   ...code end  
    
    
}
