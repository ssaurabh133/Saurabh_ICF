package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.CaseMarkingAuthorVO;
import com.cm.vo.CaseMarkingMakerVO;

public interface CaseMarkingAuthorDAO {
//change by sachin
String IDENTITY="CASEMAUTHORD";
//end by sachin	
ArrayList<CaseMarkingAuthorVO> searchCaseMarkingAuthor(
		CaseMarkingAuthorVO caseMarkingAuthorVO);

public ArrayList<CaseMarkingAuthorVO> openEditCaseMarkingAuthor(CaseMarkingAuthorVO vo);
public ArrayList<Object> saveCaseMarkingCheckerDetails(CaseMarkingAuthorVO vo);


}