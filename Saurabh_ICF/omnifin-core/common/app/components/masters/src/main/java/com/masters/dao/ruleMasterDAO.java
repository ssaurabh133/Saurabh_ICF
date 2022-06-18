// Rule masters by Sanjog

package com.masters.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.masters.vo.ruleParamMasterVo;

public interface ruleMasterDAO {

	String IDENTITY = "RULEMASTERD";
	ArrayList<ruleParamMasterVo> searchRuleMaster(Object ob);
	boolean updateRuleMaster(Object ob);
	boolean insertRuleScoreMaster(Object ob);

}

