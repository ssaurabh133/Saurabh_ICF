package com.scz.service;

import java.util.HashMap;

import com.scz.dao.DownloadDAO;
import com.scz.daoImplMYSQL.DownloadDAOImpl;
//import com.scz.daoHibImpl.SCZ_KnockOffHibImplService;
//import com.scz.daoHibImpl.SCZ_ManualAdviceHibImplService;
//import com.scz.daoHibImpl.SCZ_WaiveOffHibImplService;

public class SCZ_ServiceLocator {
	String database = "hibernate";
	static SCZ_ServiceLocator seLocator = null;
	HashMap cache = new HashMap();
	SCZ_ServiceLocator()
	{}
	
	public DownloadDAO getPoolDownload()
	{
		DownloadDAO PoolDownloadService = null;
		if(cache.get("PoolDownloadService")==null)
		{
			PoolDownloadService = new DownloadDAOImpl() ;
			cache.put("PoolDownloadService",PoolDownloadService);
			
		}
		return (DownloadDAO)cache.get("PoolDownloadService");
	}
	
	/*public SCZ_KnockOffService getKnockOffService()
	{
		SCZ_KnockOffService KnockOffService = null;
		if(cache.get("KnockOffService")==null)
		{
			KnockOffService = new SCZ_KnockOffHibImplService() ;
			cache.put("KnockOffService",KnockOffService);
			
		}
		return (SCZ_KnockOffService)cache.get("KnockOffService");
	}*/
	
	/*public SCZ_WaiveOffService getWaiveOffService()
	{
		SCZ_WaiveOffService WaiveOffService = null;
		if(cache.get("WaiveOffService")==null)
		{
			WaiveOffService = new SCZ_WaiveOffHibImplService() ;
			cache.put("WaiveOffService",WaiveOffService);
			
		}
		return (SCZ_WaiveOffService)cache.get("WaiveOffService");
	}
*/	
	/*public SCZ_ManualAdviceService getManualAdviceService()
	{
		SCZ_ManualAdviceService adviceService = null;
		if(cache.get("ManualAdviceService")==null)
		{
			adviceService = new SCZ_ManualAdviceHibImplService() ;
			cache.put("ManualAdviceService",adviceService);
			
		}
		return (SCZ_ManualAdviceService)cache.get("ManualAdviceService");
	}*/

	public static SCZ_ServiceLocator getInstance() {
		if (seLocator == null) {
			seLocator = new SCZ_ServiceLocator();
			return seLocator;
		}
		return seLocator;
	}
	
}
