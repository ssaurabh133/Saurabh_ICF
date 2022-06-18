package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.processVO;

public interface BeginOfDayProcessDAO {
	//CHANGE BY SACHIN
	String IDENTITY="BOD";
	//END BY SACHIN
	String getBodStatus();
	ArrayList showBodData(String busnissDate);
	ArrayList showBodProcessData(processVO vo);
	ArrayList showBodErrorData(String ProcessName);
	public String getBodProcessStatus();

	
	
	
}
