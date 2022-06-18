package com.masters.dao;


import java.util.ArrayList;

import com.masters.vo.UserProductAccessVo;


public interface UserProductAccessDAO
{
	String IDENTITY="USERPAD";
	boolean saveUserProductAccess(UserProductAccessVo vo,String [] productId);
	public ArrayList getRecordAtSearch(int currentPageLink) ;
	ArrayList<UserProductAccessVo> getRecordForUpdate(String userId);
	ArrayList searchUserProductRecord(UserProductAccessVo vo);
	ArrayList<UserProductAccessVo> getProductRecordForUpdate(String userId);
}