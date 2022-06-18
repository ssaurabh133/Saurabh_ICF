package com.cp.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;






import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.cp.vo.dedupeReferalVo;

public interface DedupeReferalDao {
	
	public static String IDENTITY="DD"; 
	
	
	public void proForDedupeDecision(String str,String txnID);
	public void saveDedupeCustomerDetail(String str,String txnId,CreditProcessingLeadDetailDataVo vo); 
	ArrayList<Object> dedupeValues(dedupeReferalVo vo);
	public String insertDedupeCustomerData(dedupeReferalVo vo, String cusId1,String dealId,String txn_type);
	public String getSavedDecisionData(String dealId,String txnType,String cusId);
	public String getDecisonType(String lbxDealNo, String txnType,String cusId) ;
	public ArrayList getDedupeCustomerData1(dedupeReferalVo vo);
	public ArrayList excelExport(dedupeReferalVo vo);
	public int checkDedupeCustomerData(String leadId);
	public boolean getDealRejection(dedupeReferalVo vo,String dealId);
	public ArrayList<Object> searchCustomerList(dedupeReferalVo vo);
}
