package com.masters.dao;

import java.util.ArrayList;



import com.masters.vo.DumpFunctionAccessVo;


public interface DumpAccessDAO {
	
	String IDENTITY="DUMPFUNCTIONACCESSD";
	  ArrayList<DumpFunctionAccessVo>getDumpName(Object ob); 
 
	boolean deleteDumpFunction(String moduleId,String roleId);

	boolean insertDump(String moduleId, String roleId, String[] checkedList,String[] unCheckedList);
	}

