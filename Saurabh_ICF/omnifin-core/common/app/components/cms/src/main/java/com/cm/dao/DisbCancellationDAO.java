package com.cm.dao;

import java.util.ArrayList; 

import com.cm.vo.AssetVerificationVO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.DisbCancellationVO;

public interface DisbCancellationDAO { 
	String IDENTITY="DISBCD"; 
	ArrayList<DisbCancellationVO> searchDisbCancellationData(Object ob, String status);
	ArrayList<DisbCancellationVO> getCancellationValues(int lbxLoanNoHID);
	ArrayList<DisbCancellationVO> getLoanDetails(int loanId);
	ArrayList<DisbCancellationVO> getDisbursalDetails(int loanId);
	boolean saveCancellationData(DisbCancellationVO vo);
	boolean forwardCancellationData(DisbCancellationVO vo);
	ArrayList<DisbCancellationVO> getDisbursalDetailsInAuthor(int loanId);
	String saveCancelLtionAuthor(Object ob);
	ArrayList<DisbCancellationVO> searchCancellationData(Object ob, String status);
	ArrayList<DisbCancellationVO> getAdviceDetails(DisbCancellationVO vo);
	ArrayList<DisbCancellationVO> getAdviceDetailsOnAuthor(DisbCancellationVO vo);
	ArrayList<DisbCancellationVO> getBillFlagDetails(DisbCancellationVO vo);
	ArrayList<DisbCancellationVO> getTAFlagDetails(DisbCancellationVO vo);
	ArrayList<DisbCancellationVO> getDisbursalFlag(DisbCancellationVO vo);
	ArrayList<DisbCancellationVO> getTAFlagDetailsAuthor(DisbCancellationVO vo);
	ArrayList<DisbCancellationVO> getAdviceTAFlag(DisbCancellationVO vo);
	ArrayList<DisbCancellationVO> getAdviceTAFlagOnAuthor(DisbCancellationVO vo);
}
