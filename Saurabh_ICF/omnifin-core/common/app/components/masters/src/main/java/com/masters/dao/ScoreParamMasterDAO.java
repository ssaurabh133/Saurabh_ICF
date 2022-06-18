package com.masters.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.masters.vo.scoreCardMasterVo;
import com.masters.vo.scoreMasterVo;


public interface ScoreParamMasterDAO {

	
	// Scoring masters by Sanjog
	String IDENTITY="SPM";
	boolean insertScoreMaster(Object ob);
	ArrayList<scoreMasterVo> searchScoreMaster(Object ob,HttpServletRequest request);
	boolean updateScoreMaster(Object ob);
	ArrayList<scoreMasterVo> searchScoreMasterForModify(Object ob,String scoringCode);
	ArrayList<scoreCardMasterVo> searchCardScoreMaster(Object ob,HttpServletRequest request);
	boolean insertCardScoreMaster(Object ob);
	boolean updateCardScoreMaster(Object ob);



	// Masters ended by Sanjog
	
}

