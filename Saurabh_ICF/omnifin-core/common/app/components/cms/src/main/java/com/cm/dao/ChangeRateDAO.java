package com.cm.dao;

import java.util.ArrayList;
import com.cm.vo.ChangeRateVo;

public interface ChangeRateDAO {
//change by sachin
	 String IDENTITY="CHANGERD";	
//end by sachin	
	ArrayList<ChangeRateVo> nonEmiBasedMakerGrid(Object ob, String type);
	ArrayList<ChangeRateVo> getValueForChangeRate(String lbxLoanNo);
	boolean insertMakerData(Object ob);
	boolean updateMakerData(Object ob);
	boolean forwardNonEMILoanData(Object ob);
	ArrayList<ChangeRateVo> nonEmiBasedGrid(Object ob);
	boolean saveAuthorDecision(Object ob);
	boolean checkStatus(String lbxLoanNo);
	boolean checkLoanStatus(String lbxLoanNo);
	String getMakerDate(String loanId);
	
}
