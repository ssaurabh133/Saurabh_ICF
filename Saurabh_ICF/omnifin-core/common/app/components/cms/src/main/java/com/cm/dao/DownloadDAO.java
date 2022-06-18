package com.cm.dao;

import java.util.ArrayList;

import com.cm.vo.PoolCreationForCMVO;

public interface DownloadDAO {
	
	String IDENTITY="DOWNLOAD";
	ArrayList generatePoolReport(PoolCreationForCMVO poolvo,String Userid);

	String getCutoffDate();
	
}
