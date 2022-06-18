package com.cm.dao;

import java.util.ArrayList;

public interface HandSightingDAO {
	String IDENTITY = "HANDSIGHTING";

	ArrayList getFileTrackingData(Object ob);

	boolean insertFileTracking(Object ob);

	boolean updateFileTracking(Object ob);

	ArrayList searchfileTracking(Object ob);
	
	ArrayList getDealNumberValues(String lbxLoanNo);
	
	ArrayList checkFileStatus(String lbxLoanNo);
	
	ArrayList getFileTrackingDataForView(Object ob);

}
