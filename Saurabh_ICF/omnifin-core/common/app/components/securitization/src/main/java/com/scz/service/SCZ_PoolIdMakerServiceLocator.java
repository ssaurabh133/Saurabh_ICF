package com.scz.service;

import java.util.ArrayList;

import com.scz.vo.PoolCreationForCMVO;

public interface SCZ_PoolIdMakerServiceLocator {
	String IDENTITY="DOWNLOAD";
	ArrayList generatePoolReport(PoolCreationForCMVO poolvo,String Userid);

	String getCutoffDate();
}
