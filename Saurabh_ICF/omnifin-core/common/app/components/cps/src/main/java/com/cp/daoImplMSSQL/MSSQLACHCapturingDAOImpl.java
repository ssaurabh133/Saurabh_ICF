package com.cp.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.cm.vo.DedupeVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.ACHCapturingDAO;
import com.cp.dao.DedupeDAO;
import com.cp.vo.ACHCapturingVo;

import org.apache.log4j.Logger;


public  class MSSQLACHCapturingDAOImpl implements ACHCapturingDAO 
{
	public ArrayList getToDebitList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getDebitTypeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getFixedMaximumAmountList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getFrequencyList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getACHStatusList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList fetchACHCustomerDetails(String dealId,String functionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveNewACHRecordList(ACHCapturingVo achCapturingVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList fetchSavedRecordList(ACHCapturingVo achCapturingVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveNewACHTrackingRecordList(ACHCapturingVo achCapturingVo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList fetchAchRecordList(String achCaptringId,String functionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateACHCapturingData(ACHCapturingVo achCapturingVo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList fetchSavedACHTrackingRecordList(String achCapturingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteACHDataList(String achCapturingId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList getACHReceivedStatusList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkSendtovender(ACHCapturingVo achCapturingVo) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean updateACHCapturingDataforExist(ACHCapturingVo achCapturingVo) {
		//dummy method
		return false;
	}
}