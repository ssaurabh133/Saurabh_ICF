package com.scz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scz.vo.PoolCreationForCMVO;
import com.scz.vo.PoolIdMakerVO;

public interface PoolCreationDAO 
{
	String IDENTITY="SCZ_POOLCD";
	StringBuffer getUploadPoolData(String userId);
	ArrayList<PoolCreationForCMVO>addscreenForEditPoolData(PoolCreationForCMVO poolvo,int poolID);
	boolean  removePoolData(PoolCreationForCMVO poolvo);
	boolean  addPoolData(PoolCreationForCMVO poolvo);
    boolean isExistInPool(PoolCreationForCMVO poolvo);
    ArrayList getUploadPoolSummary(String userId,HttpServletRequest request);
	boolean insertDataforPoolId(PoolIdMakerVO poolIdMakerVO);
	boolean csvReadforpoolId(HttpServletRequest request,HttpServletResponse response, String string, String poolID) throws SQLException;
}
