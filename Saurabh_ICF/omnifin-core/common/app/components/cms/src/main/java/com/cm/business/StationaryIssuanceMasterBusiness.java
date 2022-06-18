//<!--Author Name :- Mradul Agarwal-->
//<!--Date of Creation : 02_March_2013-->
//<!--Purpose :-  Stationary Addition Screen-->

package com.cm.business;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cm.dao.StationaryIssuanceDAO;
import com.cm.actionform.StationaryMasterForm;

import com.connect.DaoImplInstanceFactory;

public class StationaryIssuanceMasterBusiness {

	
private static final Logger logger=Logger.getLogger(StationaryIssuanceMasterBusiness.class.getName());
	
	StationaryIssuanceDAO stationaryIssuanceDAO=(StationaryIssuanceDAO)
	DaoImplInstanceFactory.getDaoImplInstance(StationaryIssuanceDAO.IDENTITY);

	public boolean getsaveIssue(StationaryMasterForm stationaryMasterForm) {
		boolean saveData = false;
		saveData=stationaryIssuanceDAO.getsaveIssue(stationaryMasterForm);		
		logger.info("status : "+saveData);
		return saveData;
		
	}

	public List<StationaryMasterForm> getSearch(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> getSearch");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryIssuanceDAO.getSearch(stationaryMasterForm);
	   
		return list;
	}

	public List<StationaryMasterForm> getEdit(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> getEdit");
		List<StationaryMasterForm> editList = new ArrayList<StationaryMasterForm>();
		editList=stationaryIssuanceDAO.getEditData(stationaryMasterForm);
		return editList;
	}

	public List<StationaryMasterForm> getSrchData(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> getSrchData");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryIssuanceDAO.getSrchData(stationaryMasterForm);
	   
		return list;
	}

	public List<StationaryMasterForm> getHoAllocationSearch(StationaryMasterForm stationaryMasterForm) {
		
		logger.info("in .....................  -->> getHoAllocationSearch");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryIssuanceDAO.getHoAllocationSearch(stationaryMasterForm);
	   
		return list;
	}

	public List<StationaryMasterForm> getHoAllocationEdit(StationaryMasterForm stationaryMasterForm) {
		
		logger.info("in .....................  -->> getHoAllocationEdit");
		List<StationaryMasterForm> editList = new ArrayList<StationaryMasterForm>();
		editList=stationaryIssuanceDAO.getHoAllocationEdit(stationaryMasterForm);
		return editList;
	}

	public boolean saveForwardCancelReceipt(StationaryMasterForm stationaryMasterForm) {
		
		boolean status = false;
		status=stationaryIssuanceDAO.saveForwardCancelReceipt(stationaryMasterForm);		
		logger.info("status : "+status);
		return status;
	}

	public String checkReceiptNoUsedCanceled(StationaryMasterForm stationaryMasterForm) {
		
		 
		String status=stationaryIssuanceDAO.checkReceiptNoUsedCanceled(stationaryMasterForm);		
		logger.info("status : "+status);
		return status;
	}

	public String prevCheckReceiptNoUsedCanceled(StationaryMasterForm stationaryMasterForm) {
		
		String status=stationaryIssuanceDAO.prevCheckReceiptNoUsedCanceled(stationaryMasterForm);		
		logger.info("status : "+status);
		return status;
	}

	public boolean saveForwardUsedReceipt(StationaryMasterForm stationaryMasterForm) {
		
		boolean status = false;
		status=stationaryIssuanceDAO.saveForwardUsedReceipt(stationaryMasterForm);		
		logger.info("status : "+status);
		return status;
	}

	public String checkReceiptSelf(StationaryMasterForm stationaryMasterForm) {
		
		String status=stationaryIssuanceDAO.checkReceiptSelf(stationaryMasterForm);		
		logger.info("status : "+status);
		return status;
	}

	public ArrayList getBookTypeGeneric() {
		
		logger.info("in .....................  -->> getBookTypeGeneric");
		ArrayList bookList=stationaryIssuanceDAO.getBookTypeGeneric();
		return bookList;
	}

	public List<StationaryMasterForm> getSearchAcknowledgement(StationaryMasterForm stationaryMasterForm) {
			logger.info("in .....................  -->> getSearchAcknowledgement");
			List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
			list=stationaryIssuanceDAO.getAcknowledgement(stationaryMasterForm);		   
			return list;
		}

	public boolean getSaveAcknow(StationaryMasterForm stationaryMasterForm) {
		logger.info("in ... -->> get Save Acknow");
		boolean status = false;
		status=stationaryIssuanceDAO.getSaveAcknow(stationaryMasterForm);		
		return status;
	}

	public Object getSrchAcknow(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> getSrchAcknow");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryIssuanceDAO.getSrchAcknow(stationaryMasterForm);	   
		return list;
	}

	public Object getSearchHo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> getSearchHo");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryIssuanceDAO.getSrchHo(stationaryMasterForm);	   
		return list;
	}

	public boolean getSaveAcknowHo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in ... -->> getSaveAcknowHo");
		boolean status = false;
		status=stationaryIssuanceDAO.getSaveAcknowHo(stationaryMasterForm);		
		return status;	
	}

	public Object getSrchAcknowHo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> getSrchAcknowHo");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryIssuanceDAO.getSrchAcknowHo(stationaryMasterForm);	   
		return list;
	}

	public Object getSearchHoStationaryAcknow(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> getSearchHoStationaryAcknow");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryIssuanceDAO.getSearchHoStationaryAcknow(stationaryMasterForm);	   
		return list;
	}

	public Object SrchAcknowStationaryHo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> SrchAcknowStationaryHo");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryIssuanceDAO.SrchAcknowStationaryHo(stationaryMasterForm);	   
		return list;
	}

	public boolean SaveStationaryAcknowHo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in ... -->> SaveStationaryAcknowHo");
		boolean status = false;
		status=stationaryIssuanceDAO.SaveStationaryAcknowHo(stationaryMasterForm);		
		return status;	
	}
	public List<StationaryMasterForm> getStationaryBranchChangeSearch(StationaryMasterForm stationaryMasterForm) {
		
		logger.info("in .....................  -->> getHoAllocationSearch");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryIssuanceDAO.getStationaryBranchChangeSearch(stationaryMasterForm);
	   
		return list;
	
	
	}
	
public List<StationaryMasterForm> getstationarybranchgchangeEdit(StationaryMasterForm stationaryMasterForm) {
		
		logger.info("in .....................  -->> getHoAllocationEdit");
		List<StationaryMasterForm> editList = new ArrayList<StationaryMasterForm>();
		editList=stationaryIssuanceDAO.getstatnarybranchgchangeEdit(stationaryMasterForm);
		return editList;
	}


public boolean saveStationarybranchChange(StationaryMasterForm stationaryMasterForm) {
	
	boolean status = false;
	status=stationaryIssuanceDAO.saveStationarybranchChange(stationaryMasterForm);		
	logger.info("status : "+status);
	return status;
}
}
