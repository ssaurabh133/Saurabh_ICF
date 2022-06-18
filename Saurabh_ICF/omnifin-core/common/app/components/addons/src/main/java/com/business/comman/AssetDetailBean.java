package com.business.comman;

import java.util.ArrayList;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import com.business.DealClient.DealProcessingBeanRemote;
import com.commonFunction.dao.commonDao;
import com.connect.DaoImplInstanceFactory;




@Stateless
public class AssetDetailBean implements AssetDetailBeanRemote {
	private static final Logger logger = Logger.getLogger(DealProcessingBeanRemote.class.getName());
	commonDao comDao=(commonDao)DaoImplInstanceFactory.getDaoImplInstance(commonDao.IDENTITY);
     
	//commonDao comDao = new commonFunctionDaoImpl();
	@Override
	public ArrayList getAssetLoanDetailAmount(String dealId) {
		logger.info("Inside DealProcessingBeanRemote ...............................getAssetLoanDetailAmount(String dealId)");
		logger.info("Implementation class: "+comDao.getClass());
		ArrayList getAssetDetail = comDao.getAssetLoanDetailAmount(dealId);
		return getAssetDetail;
	}

}
