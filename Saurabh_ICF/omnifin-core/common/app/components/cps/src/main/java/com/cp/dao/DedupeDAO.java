package com.cp.dao;

import java.util.ArrayList;

import com.cm.vo.DedupeVO;

public interface DedupeDAO 
{
	String IDENTITY="DEDUPE"; 
	ArrayList getDupRecord(DedupeVO vo);

	ArrayList getDefltCustDtl(String customerID);

	boolean replaceCustomerID(DedupeVO vo);
}
