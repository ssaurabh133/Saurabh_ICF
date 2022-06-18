package com.cp.dao;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import com.cp.vo.TradeCheckInitVo;

public interface TradeCheckDAO {

	String IDENTITY="TRADEDAO";
	ArrayList<TradeCheckInitVo> searchTradeCheckCapData(TradeCheckInitVo vo,HttpServletRequest request);
	ArrayList<TradeCheckInitVo> searchTradeCheckCapDataAuto(TradeCheckInitVo vo,String recStatus, HttpServletRequest request);
	ArrayList<TradeCheckInitVo> searchTradeCheckComData(TradeCheckInitVo vo,HttpServletRequest request);
	ArrayList getTradeHeader(String dealId);

	ArrayList getList(String dealId,String userId);
	ArrayList getTradeList(String dealId,String userId, String recStatus);
	
	ArrayList searchBuyerSupplierDetials(TradeCheckInitVo vo,String dealId, String verId,String entityId);
	boolean insertTradeBuyer(Object ob);
	boolean modifyTradeBuyer(Object ob, String tradeCheckId);
	
	boolean modifyTradecheck(Object ob, String dealId,String verificationID);
	boolean modifyTradeRemark(Object ob, String dealId);
	public ArrayList searchVerId(TradeCheckInitVo vo,String userId, String dealId);
	
}

