package com.a3s.adapter.util;

public class AdapterLock {

	private static AdapterLock adapterLock = null;
	
	private AdapterLock() {
		
	}
	
	public synchronized static AdapterLock getInstance() {
		if(adapterLock==null)
			adapterLock = new AdapterLock();
		return adapterLock;
	}
	
}
