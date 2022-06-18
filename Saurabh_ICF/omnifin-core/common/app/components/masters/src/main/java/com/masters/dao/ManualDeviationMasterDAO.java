package com.masters.dao;

import java.util.ArrayList;

import com.masters.vo.MakeModelmasterVO;
import com.masters.vo.ManulaDeviationVO;

public interface ManualDeviationMasterDAO 
{

	String IDENTITY = "MDM";
	boolean saveManualDeviation(ManulaDeviationVO vo);
	ArrayList<ManulaDeviationVO> getManualDeviationRecords(int linksize);
	ArrayList<ManulaDeviationVO> searchManualDeviationList(ManulaDeviationVO vo);
	ArrayList<ManulaDeviationVO> getSingleRecord(String manualid);
	
}
