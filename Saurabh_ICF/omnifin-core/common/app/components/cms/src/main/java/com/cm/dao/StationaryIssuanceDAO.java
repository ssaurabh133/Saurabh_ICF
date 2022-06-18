package com.cm.dao;



import java.util.ArrayList;
import java.util.List;

import com.cm.actionform.StationaryMasterForm;



public interface StationaryIssuanceDAO 
{

	String IDENTITY = "SMI";

	boolean getsaveIssue(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getSearch(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getEditData(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getSrchData(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getHoAllocationSearch(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getHoAllocationEdit(StationaryMasterForm stationaryMasterForm);

	boolean saveForwardCancelReceipt(StationaryMasterForm stationaryMasterForm);

	String checkReceiptNoUsedCanceled(StationaryMasterForm stationaryMasterForm);

	String prevCheckReceiptNoUsedCanceled(StationaryMasterForm stationaryMasterForm);

	boolean saveForwardUsedReceipt(StationaryMasterForm stationaryMasterForm);

	String checkReceiptSelf(StationaryMasterForm stationaryMasterForm);

	ArrayList getBookTypeGeneric();

	List<StationaryMasterForm> getAcknowledgement(StationaryMasterForm stationaryMasterForm);

	boolean getSaveAcknow(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getSrchAcknow(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getSrchHo(StationaryMasterForm stationaryMasterForm);

	boolean getSaveAcknowHo(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getSrchAcknowHo(	StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getSearchHoStationaryAcknow(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> SrchAcknowStationaryHo(StationaryMasterForm stationaryMasterForm);

	boolean SaveStationaryAcknowHo(StationaryMasterForm stationaryMasterForm);	
	List<StationaryMasterForm> getStationaryBranchChangeSearch(StationaryMasterForm stationaryMasterForm);

	List<StationaryMasterForm> getstatnarybranchgchangeEdit(StationaryMasterForm stationaryMasterForm);
	boolean saveStationarybranchChange(StationaryMasterForm stationaryMasterForm);
	
	
}
