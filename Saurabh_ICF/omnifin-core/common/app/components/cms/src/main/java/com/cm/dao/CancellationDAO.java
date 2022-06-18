package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.AssetVerificationVO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureSearchVO;

public interface CancellationDAO {
	//change by sachin
	 String IDENTITY="CANCELD";
	//end by sachin
	ArrayList<ClosureSearchVO> searchClosureData(Object ob, String status, String type);
	boolean checkCancellationPaymentReceipt(String lbxLoanNoHID);
	String insertCancellationData(CancellationVO ob);
	ArrayList<CancellationVO> selectCancellationData(String loanId,String terminationId);
	boolean updateCancellationData(CancellationVO ob,String type);
	boolean deleteCancellationDetails(CancellationVO ob);
	ArrayList<CancellationVO> getCancellationValues(int lbxLoanNoHID,String businessDate);
	String getMakerDate(String loanId);
	String earlyClosureFlag();
}
