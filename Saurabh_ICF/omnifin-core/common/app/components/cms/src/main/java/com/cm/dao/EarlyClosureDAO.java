package com.cm.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.ClosureVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cp.vo.RepayScheduleVo;

public interface EarlyClosureDAO {
	String IDENTITY="EARLY";
	ArrayList<ClosureVO> getClosureValues(int lbxLoanNoHID, String businessDate);
	ArrayList<ClosureSearchVO> searchClosureData(Object ob, String status, String type);
	ArrayList<ClosureVO> selectClosureData(String loanId,String terminationId);
	String insertClosureData(ClosureVO closureVo);
	String checkClosureAmtInProces(String lbxLoanNoHID);
	boolean insertUpdateClosureData(Object ob, String type);
	boolean deleteClosureData(Object ob);
	ArrayList<CancellationVO> selectCancellationData(String loanId,String terminationId);
	ArrayList<PaymentMakerForCMVO>viewPayableForEarlyClousre(int loanId);
	ArrayList<PaymentMakerForCMVO>viewReceivableForEarlyClousre(int loanId);
	public ArrayList<RepayScheduleVo> getRepayScheduleDisbursal(String loanId);
	ArrayList<RepayScheduleVo> getRepaySchFieldsDetail(String loanId);
	ArrayList<ClosureVO> getDuesRefundsList(String companyId,String loanId, String effectiveDate, String closureType, String source) throws SQLException;
	String saveClosureAuthor(Object ob);
	String getMakerDate(String loanId);
	String earlyClosureFlag();
	String earlyClosureRealizeFlag();
    //declaration added by neeraj tripathi
	ArrayList getWaiveOffList(ArrayList<String> inList);
	ArrayList getTotalList(String terminationId);
	ArrayList saveWaiveOffData(String terminationId,String chargeList, String waiveAmtList,String balAmtList,String dateList, String loanId, String userId,String businessDate,String approvedBY);
    //tripathi's space end
	//Surendra Code goes here..
	String getApprovedBY(String loanId,String terminationId);
	String netReceivablePayableFlag();
	String getLbxapprovedBy(String loanId, String terminationId);
	public ArrayList<Object> getClosureTypeList();//Virender

}
