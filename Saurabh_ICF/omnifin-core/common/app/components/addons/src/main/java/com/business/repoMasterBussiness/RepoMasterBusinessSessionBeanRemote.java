package com.business.repoMasterBussiness;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.repo.vo.RepoAssetChecklistVo;


@Remote
public interface RepoMasterBusinessSessionBeanRemote {
	
	String REMOTE_IDENTITY="REPOMASTERBUSINESSSESSIONBEANREMOTE";
	
	
	public ArrayList searchStockyardMasterData(Object ob) ;
	public boolean insertStockyardMaster(Object ob);
	public ArrayList editStockyardData(Object ob) ;
	public boolean updateStockyardData(Object ob);	
	
	public ArrayList searchRepoAsset(Object ob) ;
	public boolean insertRepoAsset(Object ob,String [] checkList);
	public ArrayList<RepoAssetChecklistVo> openEditRepoAsset(Object ob) ;
	public boolean updateRepoAsset(Object ob,String [] checkList);
	public ArrayList getChecklistOnEdit(Object ob) ;
	public ArrayList getAssetClass() ;


}//end of class

