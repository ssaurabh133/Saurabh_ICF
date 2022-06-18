package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.ChequeStatusVO;

public interface ChequeStatusUpdateDAO {
	//change by sachin
	 String IDENTITY="CHEQUESUD";
	//end by sachin
	ArrayList searchChequesByPayment(ChequeStatusVO chequeStatusVO);
	ArrayList searchChequeStatusForLoanViewer(ChequeStatusVO chequeStatusVO, String loanId);
}
