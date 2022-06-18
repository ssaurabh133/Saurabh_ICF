//<!--Author Name :- Mradul Agarwal-->
//<!--Date of Creation : 02_March_2013-->
//<!--Purpose :-  Stationary Addition Screen-->

package com.cm.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cm.dao.StationaryMasterDAO;
import com.cm.actionform.StationaryMasterForm;
import com.connect.DaoImplInstanceFactory;

public class StationaryAdditionMasterBusiness {

	
private static final Logger logger=Logger.getLogger(StationaryAdditionMasterBusiness.class.getName());
	
	StationaryMasterDAO stationaryMasterDao=(StationaryMasterDAO)
	DaoImplInstanceFactory.getDaoImplInstance(StationaryMasterDAO.IDENTITY);
	StationaryIssuanceMasterBusiness stationaryIssuanceMasterBusiness = new StationaryIssuanceMasterBusiness();

	public List<StationaryMasterForm>getBankStatement(StationaryMasterForm stationaryForm) {
		logger.info("in .....................  -->> StationaryAdditonMasterBusiness");
		List<StationaryMasterForm> bankList = new ArrayList<StationaryMasterForm>();
		bankList=stationaryMasterDao.getBankStatement(stationaryForm);
		return bankList;
	}

	public boolean getsaveBankData(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> StationaryAdditonMasterBusiness");
		boolean saveData = false;
		boolean deleteData = false;
		saveData=stationaryMasterDao.getsaveBankData(stationaryMasterForm);
		logger.info("saveData status: "+saveData);
		if(saveData)
		{
			 String[] bookNo =stationaryMasterForm.getBookNo();
			 for(int i=0;i<bookNo.length;i++){
				stationaryMasterForm.setDesc(stationaryMasterForm.getCheckType());
				stationaryMasterForm.setBookIssue(bookNo[i]);
				stationaryMasterForm.setStatus1("NA");
				saveData=stationaryIssuanceMasterBusiness.getsaveIssue(stationaryMasterForm);
			 }
			 if(!saveData)
			 {
				 deleteData=stationaryMasterDao.deleteBankData(stationaryMasterForm);
				 logger.info("deleteData status : "+deleteData);
			 }
		}
	    logger.info("status : "+saveData);
		return saveData;
	}

	public List<StationaryMasterForm> getList(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> StationaryAdditonMasterBusiness");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryMasterDao.getList(stationaryMasterForm);
	   
		return list;
	}

	public List<StationaryMasterForm> getEditStatioanry(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> StationaryAdditonMasterBusiness");
		List<StationaryMasterForm> dataList = new ArrayList<StationaryMasterForm>();
		dataList=stationaryMasterDao.getdatalist(stationaryMasterForm);
		return dataList;
	}

	public int checkBookNo(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> StationaryAdditonMasterBusiness");
		int bID=stationaryMasterDao.checkBookNo(stationaryMasterForm);
		return bID;
	
	}
	public List<StationaryMasterForm> getSearch(StationaryMasterForm stationaryMasterForm) {
		logger.info("in .....................  -->> StationaryAdditonMasterBusiness");
		List<StationaryMasterForm> list = new ArrayList<StationaryMasterForm>();
		list=stationaryMasterDao.getSearch(stationaryMasterForm);
		return list;
	}

	public int checkDublicateReceiptNo(StationaryMasterForm stationaryMasterForm) {
		
		logger.info("in .....................  -->> checkDublicateReceiptNo");
		int bID=stationaryMasterDao.checkDublicateReceiptNo(stationaryMasterForm);
		return bID;
	}
	
}
