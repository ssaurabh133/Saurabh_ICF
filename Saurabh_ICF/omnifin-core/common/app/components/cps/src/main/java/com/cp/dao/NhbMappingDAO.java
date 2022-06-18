package com.cp.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cp.vo.NhbMappingVo;

public interface NhbMappingDAO {
	 String IDENTITY="NHBMAPPING"; 	
	 public ArrayList<NhbMappingVo> getSearchNhbMapping(NhbMappingVo vo,HttpServletRequest request);
	 public boolean insertNhbCategory(NhbMappingVo vo,String [] arrNhbCategoryId);
	 public ArrayList<NhbMappingVo> getNhbMapping(String dealId);
	 public ArrayList<Object> getNhbDescription(String nhbId);
}
