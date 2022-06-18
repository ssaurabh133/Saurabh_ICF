package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.DumpDownLoadVO;

public interface DumpDownLoadDAO 
{
	String IDENTITY="DUMP";
	ArrayList<DumpDownLoadVO> getGeneratFieldInformation(int recordID);

	ArrayList<Object> reportGenerator(DumpDownLoadVO vo);

	ArrayList getReportList(String query);
	//sidharth
	ArrayList<Object> reportAdHoc(DumpDownLoadVO vo);
	void saveFunctionLogData(String userId,String moduleId,String functionId,String accessDate,String ipAddress,String sessionNo,String reportName,String reportParam);

	//Virender Start
	ArrayList fetchDealID(String hunterCustTypeVar);
	ArrayList HunterCustCSV(String dealId);
	ArrayList CorpHunterCustCSV(String dealId);
	ArrayList fetchCorpHunterData(String dealId,String custId,int j,String userId);
}
