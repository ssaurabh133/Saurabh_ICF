package com.legal.dao;

import java.util.ArrayList;

import com.legal.vo.AdvocateMasterVo;
import com.legal.vo.CaseTypeMasterVo;
import com.legal.vo.LawFirmMasterVo;
import com.legal.vo.POAMasterVo;
import com.masters.vo.MakeModelmasterVO;
import com.masters.vo.UserMasterVo;

public interface LegalMasterDAO {

	/*
	 * Code For Activity Master Starts Here By Arun 
	 * */
	String IDENTITY="LEGALMASTERDAO";
	
	//vinod work space start
	
	 ArrayList searchCaseTypeMasterData(Object ob) ;
	 boolean insertCaseTypeMaster(Object ob);
	 ArrayList editCaseTypeData(Object ob) ;
	 boolean updateCaseTypeData(Object ob);
	
	
	 ArrayList searchLawFirmMasterData(Object ob) ;
	 boolean insertLawFirmMaster(Object ob,String [] branchName);
	 ArrayList editLawFirmData(Object ob) ;
	 boolean updateLawFirmData(Object ob,String [] branchName);
	 ArrayList <LawFirmMasterVo> getBranchesForEdit(String lawFirmCode);
	
	 ArrayList searchAdvocateMasterData(Object ob) ;
	 boolean insertAdvocateMaster(Object ob,String [] branchName,String [] caseTypeCode);
	 ArrayList editAdvocateData(Object ob) ;
	 boolean updateAdvocateData(Object ob,String [] branchName,String [] caseTypeCode);
	 ArrayList <AdvocateMasterVo> getBranchesForAdvocateEdit(String advocateCode);
	 ArrayList <AdvocateMasterVo> getcaseTypeForAdvocateEdit(String advocateCode);
	
	//vinod work space end
	
	//Richa work space start
	
	 ArrayList searchCourtTypeMasterData(Object ob) ;
	 boolean insertCourtTypeMaster(Object ob);
	 ArrayList editCourtTypeData(Object ob) ;
	 boolean updateCourtTypeData(Object ob) ;
	
	 ArrayList searchCourtNameMasterData(Object ob) ;
	 boolean insertCourtNameMaster(Object ob);
	 ArrayList editCourtNameData(Object ob) ;
	 boolean updateCourtNameData(Object ob) ;
	
     ArrayList searchPOAMasterData(Object ob) ;
	 boolean insertPOAMaster(Object ob, String [] courtName);
	 ArrayList openEditPOAData(Object ob) ;
	 boolean updatePOAData(Object ob, String[] courtName) ;
	 ArrayList<POAMasterVo> getcourtNameForPOaEdit(String poaCode);
	
	//Richa work space end
	 
	 
    // Nazia work space start
  
	ArrayList  searchNoticeTypeMasterData(Object ob);
	boolean insertNoticeTypeMaster (Object ob);
	ArrayList  editNoticeTypeData(Object ob);
	boolean updateNoticeTypeData(Object ob);
	
	
	ArrayList  searchStageTypeMasterData(Object ob);
	boolean insertStageTypeMaster (Object ob,String [] productIdList,String [] paymentStageFlagList,String [] repetitiveFlagList,String productIds);
	ArrayList  editStageTypeData(Object ob);
	boolean updateStageTypeData(Object ob,String [] productIdList,String [] paymentStageFlagList,String [] repetitiveFlagList,String productIds);
	ArrayList  getProduct(Object ob);
	ArrayList  getProductOnEdit(Object ob);
  
 //Nazia work space end
	
	boolean checkExistingPAN(Object ob, String insertUpdateFlag);
	boolean checkExistingPANAdvocate(Object ob,String insertUpdateFlag);

}
