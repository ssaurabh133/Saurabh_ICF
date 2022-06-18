package com.cm.dao;

import java.util.List;

import com.cm.actionform.StationaryMasterForm;

public interface StationaryMasterDAO 
{

	String IDENTITY = "SMA";
	List<StationaryMasterForm> getBankStatement(StationaryMasterForm stationaryForm);	
	boolean getsaveBankData(StationaryMasterForm stationaryMasterForm);
	boolean deleteBankData(StationaryMasterForm stationaryMasterForm);
	List<StationaryMasterForm> getList(StationaryMasterForm stationaryMasterForm);
	List<StationaryMasterForm> getdatalist(StationaryMasterForm stationaryMasterForm);
	int checkBookNo(StationaryMasterForm stationaryMasterForm);
	List<StationaryMasterForm> getSearch(StationaryMasterForm stationaryMasterForm);
	int checkDublicateReceiptNo(StationaryMasterForm stationaryMasterForm);
	
	

}
