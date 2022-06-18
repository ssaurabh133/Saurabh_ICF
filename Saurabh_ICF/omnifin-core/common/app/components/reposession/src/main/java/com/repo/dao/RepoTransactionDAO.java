package com.repo.dao;

import java.util.ArrayList;



public interface RepoTransactionDAO {

	/*
	 * Code For Activity Master Starts Here By Arun 
	 * */
	String IDENTITY="REPOTRANSDAO";
	
	//Vinod WorkSpace Start
	
	public ArrayList searchRepoDetailbyAgency(Object ob) ;
	public ArrayList getRepoDetailbyAgencyForEdit(Object ob) ;
	public ArrayList getCheckListDetail(Object ob) ;
	public boolean saveRepoDetailsByAgency(Object ob,String [] checkList,String [] statusList,String [] remarkList);
	
	//Vinod Work space end
	
	
	//Richa Workspace start
	
	public ArrayList searchRepoDetailByStockyard(Object ob) ;
	public ArrayList openEditRepoDetailByStockyard(Object ob) ;
	public ArrayList getCheckListForStockyard(Object ob) ;
	public boolean insertRepoDetailByStockyard(Object ob,String [] checkList,String [] checkListStatusList,String [] checkListRemarksList);
	
	public ArrayList searchRepoMarkingMaker(Object ob) ;
	public ArrayList openEditRepoMarkingMaker(Object ob) ;
	public boolean insertRepoMarkingMaker(Object ob);
	public boolean updateRepoMarkingMaker(Object ob);
	public ArrayList getDetailofLoanForRepoMarkingMaker(Object ob) ;
	
	
	public ArrayList searchRepoMarkingAuthor(Object ob) ;
	public ArrayList openEditRepoMarkingAuthorCase(Object ob) ;
	public boolean insertRepoMarkingAuthorCase(Object ob);
	
	
	//Richa Workspace end
	
	//Nazia workspace start
	
	public ArrayList searchRepoConfirmation(Object ob) ;
	public ArrayList openEditRepoConfirmation(Object ob) ;
	public ArrayList getCheckListOfAgency(Object ob) ;
	public ArrayList getCheckListOfStockyard(Object ob) ;
	public boolean saveRepoConfirmationDetail(Object ob);
	
	//Nazia workspace end
	
	
	

}
