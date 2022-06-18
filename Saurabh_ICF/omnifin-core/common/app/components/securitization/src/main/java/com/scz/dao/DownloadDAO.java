package com.scz.dao;

import java.util.ArrayList;

import com.scz.vo.PoolCreationForCMVO;

public interface DownloadDAO {
	
	String IDENTITY="SCZ_DOWNLOAD";
	ArrayList generatePoolReport(PoolCreationForCMVO poolvo,String Userid);

	String getCutoffDate();
	String getCutOffDate(String date);
}
