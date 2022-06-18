package com.scz.delegate;

import java.util.ArrayList;

import com.scz.dao.DownloadDAO;
import com.scz.vo.PoolCreationForCMVO;
import com.scz.service.SCZ_ServiceLocator;

public class SCZ_PoolDownloadDelegate {
	static DownloadDAO service = SCZ_ServiceLocator.getInstance().getPoolDownload();
	
	public static ArrayList generatePoolReport(PoolCreationForCMVO poolvo,String Userid)
	{
		ArrayList list = service.generatePoolReport(poolvo, Userid);
		return list;
	}
}
