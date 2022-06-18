package com.masters.dao;

import java.util.ArrayList;

import com.masters.vo.ScoringMasterVO;

public interface ScoringMasterDAO {
	String IDENTITY="SCORINGMD";
	public ArrayList<ScoringMasterVO> getScoringMasterList(ScoringMasterVO vo);
	public String saveScoringMaster(ScoringMasterVO vo);
	public String updateScoringMaster(ScoringMasterVO vo);
	public ArrayList<ScoringMasterVO> editScoringMasterHeader(ScoringMasterVO vo);
	public ArrayList<ScoringMasterVO> editScoringMasterdtl(ScoringMasterVO vo);
	public ArrayList<ScoringMasterVO> editScoringParamValueDtl(ScoringMasterVO vo);
	public String saveScoringMasterParamValue(ScoringMasterVO vo);
}
