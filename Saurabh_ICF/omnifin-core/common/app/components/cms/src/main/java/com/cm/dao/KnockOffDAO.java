package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.KnockOffAuthorVO;
import com.cm.vo.KnockOffMakerVO;
import com.cm.vo.KnockOffSearchVO;

public interface KnockOffDAO {
	String IDENTITY="KNOCKOFFD";
	ArrayList<KnockOffSearchVO> searchKnockOffData(KnockOffSearchVO vo,String type);
	ArrayList<KnockOffMakerVO> getKnockOffData(String loanId,String type);
	ArrayList<KnockOffMakerVO> getKnockOffCancellationData(String loanId,String type,String canFlage);	
	ArrayList<Object> getKnockOffDetailsDataMaker(String loanId, String adviceType, String string);
	ArrayList<Object> getTotalReceivableR(String loanNo, String type);
	ArrayList<Object> getTotalReceivableP(String loanNo, String type);
	 public String saveKnockOffDetails(Object ob,String sum,String sum1,String[] RknockOffAmount,String[] PKnockOffAmount,String loanIdNo,String[]ROriginalAmount,String[] RBalancelAmount,String[] POriginalAmount,String[] PBalancelAmount,String[] txnAdviceIdR, String[] txnAdviceIdP,String[] ProcessAmountR,String[] ProcessAmountP,String status,String[] KnockOffId_R, String[] KnockOffId_P);
	 String saveKnockOffAuthor(KnockOffAuthorVO vo);
	 
	 ArrayList<Object> getKnockOffDetailsDataNew(String loanNo, String bpId, String adviceType,String bDate);
	boolean saveKnockOffCancelData(String knockOffId, String makerRemarks,String userId,String makerDate,String status);
	ArrayList<KnockOffMakerVO> searchKOCData(String knockOffId,int currentPageLink,String canFlag,String userId); 
	 
	boolean deleteKnockOFF(String knockOffId);
	boolean deleteKnockOffCancellation(String knockOffID);
}
