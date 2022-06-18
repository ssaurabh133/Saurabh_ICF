package com.repo.dao;

import java.util.ArrayList;


public interface RepoMasterDAO {

	
	String IDENTITY="REPOMASTERDAO";
	
	//Richa work space start
	//stockyard master start
	
	 ArrayList searchStockyardMasterData(Object ob) ;
	 boolean insertStockyardMaster(Object ob);
	 ArrayList editStockyardData(Object ob) ;
	 boolean updateStockyardData(Object ob);	
	
	 //stockyard master end
	 //Richa work space end
	 
	 //Nazia workspce start
	 
	ArrayList  searchRepoAsset(Object ob);
	boolean insertRepoAsset (Object ob,String [] checkList);
	ArrayList  openEditRepoAsset(Object ob);
	ArrayList  getChecklistOnEdit(Object ob);
	boolean updateRepoAsset(Object ob,String [] checkList);
	ArrayList  getAssetClass();
	 
	 //Nazia workspace end
	
	

}
