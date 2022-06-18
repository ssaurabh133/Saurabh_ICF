package com.business.comman;

import javax.ejb.Remote;

@Remote
public interface CommonFunctionBeanRemote {
	
	String REMOTE_IDENTITY="COMMONFUNCTIONBUSSINESSMASTERREMOTE";
	
	public String stageMovement(String companyId,String txnType,String action, String dealId,String stage,String bDate,String makerId);

}
