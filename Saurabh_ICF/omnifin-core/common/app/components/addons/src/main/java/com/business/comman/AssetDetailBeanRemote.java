package com.business.comman;

import java.util.ArrayList;

import javax.ejb.Remote;

@Remote
public interface AssetDetailBeanRemote {
	
	ArrayList getAssetLoanDetailAmount(String dealId);

}
