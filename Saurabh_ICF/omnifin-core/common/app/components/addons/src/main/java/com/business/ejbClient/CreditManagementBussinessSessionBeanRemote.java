package com.business.ejbClient;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.masters.vo.AutoAllocationDefinitionVo;
import com.masters.vo.GcdGroupMasterVo;
import com.masters.vo.NPAMasterVo;
import com.masters.vo.MobileUserMappingVo;

@Remote
public interface CreditManagementBussinessSessionBeanRemote {
	
	
	String REMOTE_IDENTITY="CREDITMANAGEMENTBUSSINESSMASTERREMOTE";

	//---------------------NPA Stage Master-----------by Nishant Rai----
	public ArrayList<NPAMasterVo> searchNPAStageData(Object ob);
	public String insertNPAStageMaster(Object ob);
	public ArrayList<NPAMasterVo> getNPAStageData();
	public String updateNPAStageData(Object ob);
	public ArrayList<NPAMasterVo> getProductId();
	public String getProduct(String str);
	//---------------------GCD Group Master-----------by Nishant Rai----
	public String insertGroupCodeMaster(GcdGroupMasterVo gcdGroupMasterVo);
	ArrayList <GcdGroupMasterVo>  modifyGcdGroupDetailsDao(Object ob);
	public String saveModifyGcdGroupDao(Object ob);
	ArrayList <GcdGroupMasterVo> searchGcdGroupData(Object ob);
	
	
	//ravi
	public String saveAutoAllocationDetailBean(Object ob);
	public ArrayList<AutoAllocationDefinitionVo> getAutoAllocationDefDataBean(Object ob);
	public ArrayList<AutoAllocationDefinitionVo> searchAutoAllocationBean(Object ob);
	public String updateAutoAllocationDetailBean(Object ob);
	  public abstract String insertMobileUserMaster(MobileUserMappingVo paramMobileUserMappingVo, String paramString);

	  public abstract ArrayList<MobileUserMappingVo> searchMobileUserData(Object paramObject);

	  public abstract ArrayList<MobileUserMappingVo> modifyMobileUSerDetailsDao(Object paramObject);

	  public abstract String saveModifyMobileMasterDao(Object paramObject, String paramString);

	  public abstract String getMobileNoMasterDao(Object paramObject);
}
