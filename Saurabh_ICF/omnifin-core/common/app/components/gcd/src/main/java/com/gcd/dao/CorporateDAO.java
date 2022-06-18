package com.gcd.dao;

import com.VO.CorporateBusinessActivityVO;
import com.VO.CreditRatingVo;
import com.VO.CustomerSaveVo;
import com.VO.IndustryVO;
import com.VO.InstitutionNameVo;
import com.VO.StakeHolderVo;
import com.gcd.VO.ShowCustomerDetailVo;

import java.util.ArrayList;

public abstract interface CorporateDAO
{
  public static final String IDENTITY = "CORPORATED";

  public abstract ArrayList<Object> getCustomerCategoryList();

  public abstract ArrayList<Object> getContitutionList();

  public abstract ArrayList<Object> getEduDetail();

  public abstract ArrayList<Object> getRegistrationTypeList();

  public abstract ArrayList<Object> getAddressList();

  public abstract ArrayList<Object> getCountryList();

  public abstract ArrayList<Object> getDetailAddressList(String paramString);

  public abstract ArrayList<Object> getCountryDetail(String paramString);

  public abstract ArrayList<Object> getStates(String paramString);

  public abstract ArrayList<Object> getCities(String paramString);

  public abstract ArrayList<Object> getCityDetail(String paramString1, String paramString2);

  public abstract ArrayList<Object> getBusinessSegmentList();

  public abstract ArrayList<Object> getNatureOfBusinessList();

  public abstract boolean saveCustomerAddress(Object paramObject);

  public abstract boolean saveCustomerReference(Object paramObject);

  public abstract boolean saveStakeHolder(Object paramObject, int paramInt, String paramString);

  public abstract int updateStakeHolder(Object paramObject, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract boolean saveCreditRating(Object paramObject, int paramInt, String paramString);

  public abstract int updateCreditRating(Object paramObject, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract int saveCorporateDetails(Object paramObject, String paramString1, String paramString2, String paramString3);

  public abstract boolean saveCorporateUpdate(Object paramObject, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract int updateCustomerAddress(Object paramObject, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract int updateIndReference(Object paramObject, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract int deleteStakeHolderDetails(String paramString1, String paramString2, String paramString3, Object paramObject, String paramString4, String paramString5, String paramString6);

  public abstract int deleteCustomerAddress(String paramString1, String paramString2, String paramString3, Object paramObject, String paramString4, String paramString5, String paramString6);

  public abstract int deleteCustomerReference(String paramString1, String paramString2, String paramString3, Object paramObject, String paramString4, String paramString5, String paramString6);

  public abstract ArrayList<Object> getPositionList();

  public abstract ArrayList<Object> getHolderTypeList();

  public abstract ArrayList<Object> getSalutation();

  public abstract ArrayList<Object> getIndustryList();

  public abstract ArrayList<Object> getSubIndustryList(String paramString);

  public abstract ArrayList<IndustryVO> getSubIndustryUpdateList(String paramString);

  public abstract ArrayList<Object> getCustomerDetailList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt);

  public abstract ArrayList<Object> getIndividualDetails(String paramString1, Object paramObject, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract ArrayList<Object> getAdressAll(String paramString1, Object paramObject, String paramString2, String paramString3);

  public abstract ArrayList<Object> getReferenceAll(String paramString1, Object paramObject, String paramString2, String paramString3);

  public abstract ArrayList<Object> getCorporateDetailAll(int paramInt, Object paramObject, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract ArrayList<Object> getStakeDetails(int paramInt, Object paramObject, String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract ArrayList<Object> getRatingDetails(int paramInt, Object paramObject, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract ArrayList<Object> getStakeDetailsAll(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract ArrayList<Object> getAddressDetails(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract ArrayList<Object> getRefrenceDetails(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract ArrayList<Object> getRelationDeatil();

  public abstract ArrayList<InstitutionNameVo> getInstitutionName();

  public abstract int deleteSelectedCreditRating(String paramString1, String paramString2, String paramString3, Object paramObject, String paramString4, String paramString5, String paramString6);

  public abstract ArrayList<Object> getcreditRatingDetails(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract ArrayList<CustomerSaveVo> getCustomerAddressDetail(String paramString1, Object paramObject, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract ArrayList<CustomerSaveVo> getIndReferenceDetail(String paramString1, Object paramObject, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract ArrayList<StakeHolderVo> getStackHolderDetail(String paramString1, Object paramObject, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract ArrayList<CreditRatingVo> getCreditRatingDetail(String paramString1, Object paramObject, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract int setApproveStatus(int paramInt, String paramString1, String paramString2, String paramString3);

  public abstract boolean checkGoForApproval(String paramString);

  public abstract String insertAllIntoTempFromMainTable(String paramString1, String paramString2);

  public abstract ArrayList<Object> getRoleList(String paramString);

  public abstract ArrayList<Object> getRoleListCorp(String paramString);

  public abstract boolean deleteroleList(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString);

  //public abstract String moveFromGCD(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8);
  public abstract String moveFromGCD(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8);
  public abstract String approve(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract String checkCustomerStatus(int paramInt, String paramString);

  public abstract ArrayList<Object> getIndividualContitutionList();

  public abstract boolean deleteCustomerDocs(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString);

  public abstract ArrayList<CustomerSaveVo> defaultcountry();

  public abstract ArrayList<ShowCustomerDetailVo> getDefaultIndividualList(int paramInt);

  public abstract ArrayList<ShowCustomerDetailVo> getDefaultCorporateList(int paramInt);

  public abstract ArrayList<ShowCustomerDetailVo> getDefaultUpdateCustomerMakerList(int paramInt);

  public abstract ArrayList<ShowCustomerDetailVo> getDefaultUpdateCustomerAuthorList(int paramInt, String paramString);

  public abstract ArrayList<Object> getCastCategoryList();

  public abstract ArrayList<Object> getGenderList();

  public abstract ArrayList<CustomerSaveVo> copyAddress(String paramString1, String paramString2);

  public abstract boolean saveCustomerProfile(Object paramObject, String paramString1, String paramString2);

  public abstract ArrayList<Object> getProfileDetails(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract ArrayList<Object> getProfileAll(String paramString1, Object paramObject, String paramString2, String paramString3);

  public abstract ArrayList<CorporateBusinessActivityVO> getBusinessActivity(int paramInt, Object paramObject, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract boolean saveBusinessActivity(CorporateBusinessActivityVO paramCorporateBusinessActivityVO, int paramInt, String paramString);

  public abstract int updateBusinessActivity(CorporateBusinessActivityVO paramCorporateBusinessActivityVO, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract ArrayList<Object> getBusinessDescription(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract boolean saveBusinessDescription(Object paramObject, String paramString1, String paramString2);

  public abstract String checkCustomerVerifInit(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString);

  public abstract boolean deleteVerificationInitCustomer(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString);

  public abstract ArrayList<Object> getCustomerRoleList(String paramString1, String paramString2);

  //public abstract String moveFromGCDATCM(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9);
  public abstract String moveFromGCDATCM(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9);
  public abstract boolean deleteCustomerRoleAtCM(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString1, String paramString2);

  public abstract boolean saveCustomerFromDeal(String paramString1, String paramString2);

  public abstract boolean deleteCustomerDocsAtCM(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString1, String paramString2);

  public abstract boolean checkApplExistance(String[] paramArrayOfString, String paramString);

  public abstract boolean updateCustomerPRSDocsAtCM(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString);

  public abstract ArrayList<CustomerSaveVo> getCustBankDetails(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract boolean saveCustBankDetails(CustomerSaveVo paramCustomerSaveVo, int paramInt, String paramString);

  public abstract int updateCustBankDetails(CustomerSaveVo paramCustomerSaveVo, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract ArrayList copyAddressAtCM(String paramString);

  public abstract boolean getFirstNameStatus(CustomerSaveVo paramCustomerSaveVo);

  public abstract String getPanCondition();

  public abstract String checkCustomerVerifInitAtCM(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString);

  public abstract boolean deleteVerificationInitCustomerAtCM(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString);

  public abstract String getEmailMandatoryFlag();

  public abstract String getMobileNoStatus(String paramString1, String paramString2, String paramString3,String paramString4);

  public abstract ArrayList<Object> getSarvSurkshaDetails(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract ArrayList<Object> getSarvSurakshaAll(String paramString1, Object paramObject, String paramString2, String paramString3);

  public abstract int deletesarvaSuraksha(String paramString1, String paramString2, String paramString3, Object paramObject, String paramString4, String paramString5, String paramString6);

  public abstract boolean savesavaSuraksha(Object paramObject);

  public abstract int updateSarvaSuraksha(Object paramObject, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract ArrayList<CustomerSaveVo> getSarvaSurakshaDetail(String paramString1, Object paramObject, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract boolean getExistingIMD(String paramString, String[] paramArrayOfString);

  public abstract boolean saveCustomerAddress1(Object paramObject);

  public abstract int saveCorporateDetails1(Object paramObject, String paramString1, String paramString2, int paramInt);

  public abstract boolean saveCorporateUpdate1(Object paramObject, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  public ArrayList getriskCategoryList();
  public abstract ArrayList<Object> getRelationShipFlagCorporate();
  public abstract ArrayList<Object> getRelationShipFlagIndividual();

public abstract ArrayList<Object> getCustomerDetails(String addId,
		String customerId,String tableName);

public abstract boolean saveRelationShipDetails(String addressId,String customerId,
		String relationship, String relationAddressId, String relationCustomerId,String tableName);

public abstract String getValidation(String paramString1, Object paramObject, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
}