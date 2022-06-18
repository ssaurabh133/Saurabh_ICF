package com.masters.dao;

import java.util.ArrayList;

import com.masters.vo.ParameterScoreDefVO;


public interface masterParameterDAO 
{
	String IDENTITY = "MPD";
	boolean saveParameterDescription(ParameterScoreDefVO vo);
	ArrayList getParameterDef(String sid,String pcode);
	ArrayList getParameterDefOnLoad(ParameterScoreDefVO vo);
	ArrayList getParameterScoreDef(String sid);
	ArrayList getParameterEdit(String sid,String pcode);
	int updateParameterList(ParameterScoreDefVO vo);
	
	
}
