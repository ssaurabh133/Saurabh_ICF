package com.business.legalMasterBussiness;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.legal.vo.AdvocateMasterVo;
import com.legal.vo.LawFirmMasterVo;
import com.legal.vo.POAMasterVo;


@Remote
public interface LegalMasterBusinessSessionBeanRemote {
	
	String REMOTE_IDENTITY="LEGALMASTERBUSSINESSMASTERREMOTE";
	
	
	//vinod work space start
	
	
	public ArrayList searchCaseTypeMasterData(Object ob) ;
	public boolean insertCaseTypeMaster(Object ob);
	public ArrayList editCaseTypeData(Object ob) ;
	public boolean updateCaseTypeData(Object ob);
	
	
	public ArrayList searchLawFirmMasterData(Object ob) ;
	public boolean insertLawFirmMaster(Object ob,String [] branchName);
	public ArrayList editLawFirmData(Object ob) ;
	public boolean updateLawFirmData(Object ob,String [] branchName);
	ArrayList <LawFirmMasterVo> getBranchesForEdit(String lawFirmCode);
	
	public ArrayList searchAdvocateMasterData(Object ob) ;
	public boolean insertAdvocateMaster(Object ob,String [] branchName,String [] caseTypeCode);
	public ArrayList editAdvocateData(Object ob) ;
	public boolean updateAdvocateData(Object ob,String [] branchName,String [] caseTypeCode);
	ArrayList <AdvocateMasterVo> getBranchesForAdvocateEdit(String advocateCode);
	ArrayList <AdvocateMasterVo> getcaseTypeForAdvocateEdit(String advocateCode);
	
	//vinod work space end
	
	//Richa work space start
	
	public ArrayList searchCourtTypeMasterData(Object ob) ;
	public boolean insertCourtTypeMaster(Object ob);
	public ArrayList editCourtTypeData(Object ob) ;
	public boolean updateCourtTypeData(Object ob) ;
	
	public ArrayList searchCourtNameMasterData(Object ob) ;
	public boolean insertCourtNameMaster(Object ob);
	public ArrayList editCourtNameData(Object ob) ;
	public boolean updateCourtNameData(Object ob) ;
	
	
	public ArrayList searchPOAMasterData(Object ob) ;
	public boolean insertPOAMaster(Object ob,String [] courtName);
	public ArrayList openEditPOAData(Object ob) ;
	public boolean updatePOAData(Object ob, String[] courtName) ;
	ArrayList <POAMasterVo> getcourtNameForPOaEdit(String poaCode);
	
	
	
	//Richa work space end
	
	
	//Nazia work space start
	
	
	public ArrayList searchNoticeTypeMasterData(Object ob) ;
	public boolean insertNoticeTypeMaster(Object ob);
	public ArrayList editNoticeTypeData(Object ob) ;
	public boolean updateNoticeTypeData(Object ob);
	
	
	
	public ArrayList searchStageTypeMasterData(Object ob) ;
	public boolean insertStageTypeMaster(Object ob,String [] productIdList,String [] paymentStageFlagList,String [] repetitiveFlagList,String productIds );
	public ArrayList editStageTypeData(Object ob) ;
	public boolean updateStageTypeData(Object ob,String [] productIdList,String [] paymentStageFlagList,String [] repetitiveFlagList,String productIds );
	public ArrayList getProduct(Object ob) ;
	public ArrayList getProductOnEdit(Object ob) ;
	
	
	
	//Nazia work space end
	boolean checkExistingPAN(Object ob,String insertUpdateFlag);
	boolean checkExistingPANAdvocate(Object ob,String insertUpdateFlag);

}//end of class
