package com.business.comman;

import javax.ejb.Remote;

import com.commonFunction.vo.RefreshFlagVo;


@Remote
public interface RefreshFlagValueRemote {
	
	String REMOTE_IDENTITY="REFRESHFLAGVALUEBUSSINESSMASTERREMOTE";
	
	public boolean insertRefreshFlag(RefreshFlagVo vo);
	
	public boolean updateRefreshFlag(RefreshFlagVo vo);

}
