package com.cp.dao;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import com.cp.vo.LimitEnhancementVo;;

public interface LimitEnhancementDAO {

	String IDENTITY="LED"; 
	ArrayList<LimitEnhancementVo> searchLimitMaker(LimitEnhancementVo vo,String recStatus, HttpServletRequest request);
	boolean insertLimitEnhancement(Object ob);
	ArrayList limitEnhancmentValues(String dealId,String loanId,String Status);
	boolean modifyLimitEnhancement(Object ob);
	boolean modifySaveLimit(Object ob);
	boolean modifyLimitForAuthor(Object ob,String DealID,String LoanID,String loanType);
	ArrayList<LimitEnhancementVo> getValueForLimitEnhancement(String lbxLoanID,String lbxDealNo);
	ArrayList<LimitEnhancementVo> getValueForLimitLoanEnhancement(String lbxLoanID,String lbxDealNo);
	String getCheckDisbursalLoan(String lbxLoanNo);
	String getCheckTerminationLoan(String lbxLoanNo);
}

