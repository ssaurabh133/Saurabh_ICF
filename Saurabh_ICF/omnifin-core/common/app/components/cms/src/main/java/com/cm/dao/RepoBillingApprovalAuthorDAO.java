package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.CaseMarkingAuthorVO;
import com.cm.vo.RepoBillingApprovalMakerVO;

public interface RepoBillingApprovalAuthorDAO {
//change by sachin
String IDENTITY="REPOAUTHOR";
//end by sachin	
ArrayList<RepoBillingApprovalMakerVO> searchRepoBillingApprovalAuthor(
		RepoBillingApprovalMakerVO vo);

public ArrayList<RepoBillingApprovalMakerVO> openEditRepoBillingApprovalAuthor(RepoBillingApprovalMakerVO vo);
boolean saveRepoBillingApprovalChecker(Object ob);

}