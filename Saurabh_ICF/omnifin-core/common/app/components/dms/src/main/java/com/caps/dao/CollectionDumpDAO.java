package com.caps.dao;

import java.util.ArrayList;

import com.cm.actionform.ReportsForm;

public interface CollectionDumpDAO {
	
	String IDENTITY="COLLDUMP";
	
	ArrayList<ReportsForm> insertDump(String makerID,String businessDate,String fromDate,String toDate);
	

}
