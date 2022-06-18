package com.business.comman;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.commonFunction.dao.RefreshFlagValueInsert;
import com.commonFunction.vo.RefreshFlagVo;

@Stateless
public class RefreshFlagValue implements RefreshFlagValueRemote {
		
	public static final Logger logger = Logger.getLogger(RefreshFlagValueRemote.class.getClass());
	@Override
	public boolean insertRefreshFlag(RefreshFlagVo vo) {
		logger.info("Inside RefreshFlagValueRemote ...............................insertRefreshFlag(..)");
		boolean refreshFlagValueRemote = RefreshFlagValueInsert.insertRefreshFlag(vo);
		return refreshFlagValueRemote;
	}
	
	
	@Override
	public boolean updateRefreshFlag(RefreshFlagVo vo) {
		logger.info("Inside RefreshFlagValueRemote ...............................updateRefreshFlag(..)");
		boolean updateRefreshFlag = RefreshFlagValueInsert.updateRefreshFlag(vo);
		return updateRefreshFlag;
	}

}
