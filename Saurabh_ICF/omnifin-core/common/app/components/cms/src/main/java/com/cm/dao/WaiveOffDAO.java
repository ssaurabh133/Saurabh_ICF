package com.cm.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cm.vo.PaymentMakerForCMVO;

public interface WaiveOffDAO {
	
	String IDENTITY="WAIVEOFFD";
	ArrayList saveWaiveOffMaker(Object ob, HttpServletRequest request);

	boolean saveAndForwordWaiveOffMaker(Object ob);

	ArrayList searchwaiveOff(Object ob);

	ArrayList getWaiveOffDetailScheme(String str, String str1, String str2,String str3);

	ArrayList selectWaiveOffData(String loanId, String bp_id,String waveOffId);

	ArrayList searchWaiveOffAuthor(Object ob, HttpServletRequest request);

	ArrayList selectWaiveOffAuthorData(String loanId, String bp_id,String waveOffId);

	String saveWaiveOffAuthor(Object ob);
	
//	boolean rejectWaiveOffAuthor(Object ob);
	ArrayList<PaymentMakerForCMVO>viewPayableForWaiveOff(int loanId,String bpType,int bpId);
	ArrayList<PaymentMakerForCMVO>viewReceivableForWaiveOff(int loanId,String bpType,int bpId);

	boolean deleteWaiveoff(String waveOffId);
}
