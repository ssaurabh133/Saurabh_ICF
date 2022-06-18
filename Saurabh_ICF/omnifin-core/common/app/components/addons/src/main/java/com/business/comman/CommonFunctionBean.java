package com.business.comman;

import java.util.ArrayList;

import javax.ejb.Stateless;

@Stateless
public class CommonFunctionBean implements CommonFunctionBeanRemote {

	@Override
	public String stageMovement(String companyId, String txnType,
			String action, String dealId, String stage, String bDate,
			String makerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
