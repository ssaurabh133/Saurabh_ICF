package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.CaseMarkingMakerVO;

public interface CaseMarkingMakerDAO {
//change by sachin
String IDENTITY="CASEMMAKERD";
//end by sachin	
ArrayList<CaseMarkingMakerVO> searchCaseMarkingMaker(
		CaseMarkingMakerVO caseMarkingCheckerVO,String statusCase);



boolean insertCrCaseMarkingDetails(CaseMarkingMakerVO vo,String [] checkList,String [] checkList1,String [] checkList2,String [] checkList3,String [] checkList4,String [] checkList5);
boolean updateCrCaseMarkingDetails(CaseMarkingMakerVO vo,String [] checkList,String [] checkList1,String [] checkList2,String [] checkList3,String [] checkList4,String [] checkList5,String [] checkList6);

String checkLoanNo(CaseMarkingMakerVO vo);

ArrayList getCaseMarkingFlagList();
public ArrayList<CaseMarkingMakerVO> openEditCaseMarkingMaker(CaseMarkingMakerVO vo);
public ArrayList<Object> forwardCaseMarkingMaker(CaseMarkingMakerVO vo);

String deleteCaseMarking(String caseMarkingLoanId,String tableStatus);



}