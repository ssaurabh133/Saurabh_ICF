// Rule masters by Sanjog

package com.masters.dao;

import java.util.ArrayList;

import com.masters.vo.pcdMasterVo;



public interface PCDMasterDAO {

	String IDENTITY = "PDM";
	ArrayList <pcdMasterVo>searchScreenPolicyListData(Object ob);
	boolean insertPolicyCheckListMaster(String[] ruleCodeList,String[] ruleDescList,String[] actionList,String[] appLevelList,pcdMasterVo vo);
	boolean updatePolicyCheckListMaster(String[] pcdCheckId,String[] ruleCodeList,String[] ruleDescList,String[] actionList,String[] appLevelList,pcdMasterVo vo);
	ArrayList <pcdMasterVo>searchPolicyListData(Object ob);
	ArrayList <pcdMasterVo>searchPolicyListDataForEditting(Object ob,String product,String scheme, String stageId);

}

