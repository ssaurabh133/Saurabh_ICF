package com.commonFunction.dao;

import java.util.ArrayList;

public interface commonDao {
	

	String IDENTITY="COMMONFUNCTIOND"; 
	ArrayList getAssetLoanDetailAmount(String dealId);
	
	//--------------------------Customer Detail in CP and Master---------------------
	
	public ArrayList<Object> getCastCategoryList();
	public ArrayList<Object> getCustomerCategoryList();
	public ArrayList<Object> getIndividualContitutionList();
	public ArrayList<Object> getIndividualDetails(String code,Object pageStatus,String updateInMaker,String statusCase,String updateFlag,String pageStatuss);
	
	
	public String insertAllIntoTempFromMainTable(String cId, String cusType);
	public boolean saveCorporateUpdate(Object corporateDetailVo,int id, String recStatus,String statusCase,String updateFlag,String pageStatuss);
	public int saveCorporateDetails(Object corporateDetailVo,String st,String dealId);
	public ArrayList<Object> getAddressDetails(String code, String statusCase,String updateInMaker,String pageStatuss,String updateFlag);

	public String getPanNoRecord(String tableName, int panNo);
	public String getCustomerRecord(int id);
	public String getCustomerRecord(String id);
	public String getExistingCustomerRecord(int id);
	public String getCustomerCount(String tableName, String panNo, int id);
	public String getCustomerCount(String tableName, String panNo);
	
	public ArrayList<Object> getAdressAll(String code, Object pageStatus,String updateFlag);
	
	public ArrayList defaultcountry();
	public ArrayList<Object> getAddressList();
	public ArrayList<Object> getCountryList();
	
	public ArrayList getCustomerAddressDetail(String addId,Object pageStatus, String statusCase,String checkFlag,String updateFlag,String pageStatuss);
	public int updateCustomerAddress(Object corporateDetailVo,int id,int addId, String recStatus,String statusCase,String updateFlag,String pageStatuss);
	
	public String getCountCommAdd(String tableName,int id);
	public String getAddIdCommAdd(String tableName,int id);
	
	public ArrayList<Object> getReferenceAll(String code,Object pageStatus,String updateFlag);
	public ArrayList<Object> getRefrenceDetails(String code, String statusCase,String updateInMaker,String pageStatuss,String updateFlag);
	public ArrayList getIndReferenceDetail(String addId,Object pageStatus, String statusCase,String checkFlag,String updateFlag,String pageStatuss);


	public ArrayList<Object> getContitutionList();
	public ArrayList<Object> getRegistrationTypeList();
	public ArrayList<Object> getIndustryList();
	public ArrayList<Object> getBusinessSegmentList();
	public ArrayList<Object> getCorporateDetailAll(int code,Object pageStatus, String statusCase,String updateInMaker,String updateFlag,String pageStatuss);
	public String getVatRegNo(String tableName, String regNo, int id);
	public String getRegNoCheck(String tableName, String regNo, int id);
	public String getVatRegNo(String tableName, String regNo);
	public String getRegNoCheck(String tableName, String regNo);
	
	ArrayList<Object> getRoleList(String dealId);
	
	
	public ArrayList<Object> getStakeDetails(int code,Object pageStatus, String statusCase,String updateFlag,String updateInMaker);
	public ArrayList<Object> getHolderTypeList();
	public ArrayList<Object> getSalutation();
	public ArrayList<Object> getPositionList();
	public ArrayList<Object> getStakeDetailsAll(int id, String statusCase,String updateInMaker,String pageStatuss,String updateFlag);
	
	
	public String getDOBfromDCM(int id);
	public String getDOBfromGcdCM(int id);
	
	
	public String getStackHolderDin(String tableName,String dinNo);
	public String getStackHolderID(String tableName,String id);
	public String getCustomerWithDoj(String tableName,String getDoj,int id);
	
	public boolean saveStakeHolder(Object ob,int id);
	public ArrayList getStackHolderDetail(String stackHolderId,Object pageStatus, String statusCase,String checkFlag,String updateFlag,String pageStatuss);
	
	public String getCustomerFromGCD(String id);
	public int updateStakeHolder(Object ob,int id,int stackId, String recStatus,String statusCase,String updateFlag,String pageStatuss);
	

	public ArrayList getInstitutionName();
	public ArrayList<Object> getcreditRatingDetails(int cid, String statusCase,String updateInMaker,String pageStatuss,String updateFlag);
	public ArrayList<Object> getRatingDetails(int code,Object pageStatus, String statusCase,String updateFlag,String updateInMaker);
	
	public boolean saveCreditRating(Object ob,int id);
	public String getCustomerFromCRM(String id);
	
	public ArrayList getCreditRatingDetail(String creditRatingId,Object pageStatus, String statusCase,String checkFlag,String updateFlag,String pageStatuss);
	
	public int updateCreditRating(Object ob,int id,int crId, String recStatus,String statusCase,String updateFlag,String pageStatuss);
	
	public boolean saveCustomerReference(Object ob);
	public int updateIndReference(Object corporateDetailVo,int id,int refId, String recStatus,String statusCase,String updateFlag,String pageStatuss);
	public boolean saveCustomerAddress(Object ob);

	public String getCommAddress(String tableName,String bpId);
	
	public int deleteCustomerAddress(String addr_id,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag);
	public int deleteCustomerReference(String addr_id,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag);
	
	public int deleteStakeHolderDetails(String holderid,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag);
	
	public int deleteSelectedCreditRating(String addr_id,String updateInMaker,String statusCase,Object pageStatus,String pageStatuss,String updateFlag);
	
	public String getParamValForInd();
	
}

