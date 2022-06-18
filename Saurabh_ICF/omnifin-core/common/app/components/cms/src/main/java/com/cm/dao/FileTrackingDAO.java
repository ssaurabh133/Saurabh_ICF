package com.cm.dao;

import java.util.ArrayList;

public interface FileTrackingDAO {
	String IDENTITY = "FILETRACKING";

	ArrayList getFileTrackingData(Object ob);

	boolean insertFileTracking(Object ob);

	boolean updateFileTracking(Object ob);

	ArrayList searchfileTracking(Object ob);
	
	ArrayList getDealNumberValues(String lbxLoanNo);
	
	ArrayList checkFileStatus(String lbxLoanNo);
	
	ArrayList getFileTrackingDataForView(Object ob);

	boolean getFileTrackingSequenceStatus(String trackingId,String fileTrackStatus);
	ArrayList searchBulkfileTracking(Object ob);
	boolean insertBulkFileTracking(Object ob,String[] loanIds,String[] receivedDates,String[] remarks);
	boolean insertfileTrackingOps(Object ob,String[] loanIds,String[] receivedDates,String[] remarks);
	boolean insertfileTrackingtostore(Object ob,String[] loanIds,String[] receivedDates,String[] remarks);
	
	ArrayList searchfileTrackingOps(Object ob);
	ArrayList searchfileTrackingtostore(Object ob);
	 ArrayList getresultForOPS(String trackingId);
	
}
