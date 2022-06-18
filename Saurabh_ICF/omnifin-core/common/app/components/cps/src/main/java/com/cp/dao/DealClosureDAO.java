package com.cp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.ClosureVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cp.vo.DealCancellationVo;
import com.cp.vo.RepayScheduleVo;

public interface DealClosureDAO 
{
	String IDENTITY="DCL"; 
	ArrayList fetchDealCanData(String dealId);
	boolean insertDealCancellation(Object ob);
	ArrayList<DealCancellationVo> searchDealCancellation(DealCancellationVo vo,String recStatus, HttpServletRequest request);
	ArrayList dealCancellationValues(String dealId,String Status);
	public boolean modifySaveDeal(Object ob);
	public boolean modifyDealForward(Object ob);
	public String modifyDealForAuthor(Object ob,String dealID);
}
