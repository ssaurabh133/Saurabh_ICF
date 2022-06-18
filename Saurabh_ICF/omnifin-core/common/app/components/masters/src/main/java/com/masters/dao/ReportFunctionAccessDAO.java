package com.masters.dao;

import java.util.ArrayList;

import com.masters.vo.ReportFunctionAccessVo;


public interface ReportFunctionAccessDAO {
	
	String IDENTITY="REPORTFUNCTIONACCESSD";
	ArrayList<ReportFunctionAccessVo>getReportName(Object ob); 
 
	boolean deleteReportFunction(String moduleId,String roleId);

	boolean insertReport(String moduleId, String roleId, String[] checkedList,String[] unCheckedList);
	}
