package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.CaseMarkingMakerVO;
import com.cm.vo.RepoBillingApprovalMakerVO;
import com.cm.vo.UpdateAssetVO;

public interface RepoBillingApprovalMakerDAO {
//change by sachin
String IDENTITY="REPOAPP";
//end by sachin	
ArrayList<RepoBillingApprovalMakerVO> searchRepoBillingApprovalMaker(RepoBillingApprovalMakerVO vo);
boolean saveRepoBillingApprovalDetails(Object ob);
boolean forwardRepoBillingApprovalMarkingDetails(Object ob);
public ArrayList<RepoBillingApprovalMakerVO> openEditRepoBillingMaker
(RepoBillingApprovalMakerVO vo);
boolean updateRepoBillingApprovalDetails(Object ob);

String deleteRepoBillingApproval(String repoBillingApprovalLoanId);
ArrayList<RepoBillingApprovalMakerVO> getRepoBillingApprovalValues(Object ob);

/*boolean insertCrCaseMarkingDetails(CaseMarkingMakerVO vo,String [] checkList,String [] checkList1,String [] checkList2,String [] checkList3,String [] checkList4,String [] checkList5);
boolean updateCrCaseMarkingDetails(CaseMarkingMakerVO vo,String [] checkList,String [] checkList1,String [] checkList2,String [] checkList3,String [] checkList4,String [] checkList5,String [] checkList6);

String checkLoanNo(CaseMarkingMakerVO vo);

ArrayList getCaseMarkingFlagList();
public ArrayList<CaseMarkingMakerVO> openEditCaseMarkingMaker(CaseMarkingMakerVO vo);
public ArrayList<Object> forwardCaseMarkingMaker(CaseMarkingMakerVO vo);

String deleteCaseMarking(String caseMarkingLoanId,String tableStatus);*/



}