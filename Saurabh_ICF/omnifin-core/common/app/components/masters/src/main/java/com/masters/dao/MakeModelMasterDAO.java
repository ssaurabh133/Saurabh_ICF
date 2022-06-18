package com.masters.dao;

import java.util.ArrayList;

import com.masters.vo.MakeModelmasterVO;




public interface MakeModelMasterDAO 
{
	String IDENTITY="MAKEMM";
	boolean saveMakeModelRecord(MakeModelmasterVO vo,String state);
// changed by abhimanyu on 20/07/2012
	boolean checkRecord(MakeModelmasterVO vo);
	boolean checkRecordforUpdate(MakeModelmasterVO vo);
    int getMakeModelID(MakeModelmasterVO vo) ;
// changed by abhimanyu on 20/07/2012

	ArrayList<MakeModelmasterVO> getMakeModelrecords(int currentPageLink);

	ArrayList<MakeModelmasterVO> searchMakeModelRecords(MakeModelmasterVO vo);

	ArrayList<MakeModelmasterVO> getParticularRecord(String makeModelId);

	String updateMakeModelRecord(MakeModelmasterVO vo,String[] stateId);
}