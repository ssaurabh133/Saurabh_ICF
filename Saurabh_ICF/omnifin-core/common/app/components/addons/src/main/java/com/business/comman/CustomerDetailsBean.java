package com.business.comman;

import java.util.ArrayList;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import com.commonFunction.dao.commonDao;
import com.connect.DaoImplInstanceFactory;


@Stateless
public class CustomerDetailsBean implements CustomerDetailsBeanRemote {

	private static final Logger logger = Logger.getLogger(CustomerDetailsBeanRemote.class.getClass());
	commonDao comDao=(commonDao)DaoImplInstanceFactory.getDaoImplInstance(commonDao.IDENTITY);
	//comDao comDao = new commonFunctionDaoImpl();
	
	@Override
	public ArrayList<Object> getCastCategoryList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCastCategoryList()");
		ArrayList getCastCategoryList = comDao.getCastCategoryList();
		return getCastCategoryList;
	}

	@Override
	public ArrayList<Object> getCustomerCategoryList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCustomerCategoryList()");
		ArrayList getCustomerCategoryList = comDao.getCustomerCategoryList();
		return getCustomerCategoryList;
	}

	@Override
	public ArrayList<Object> getIndividualContitutionList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getIndividualContitutionList()");
		ArrayList getIndividualContitutionList = comDao.getIndividualContitutionList();
		return getIndividualContitutionList;
	}

	@Override
	public ArrayList<Object> getIndividualDetails(String code,
			Object pageStatus, String updateInMaker, String statusCase,
			String updateFlag, String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getIndividualDetails(......)");
		ArrayList getIndividualDetails = comDao.getIndividualDetails(code,
				pageStatus, updateInMaker, statusCase,
				updateFlag, pageStatuss);
		return getIndividualDetails;
	}

	@Override
	public ArrayList<Object> getAddressDetails(String code, String statusCase,
			String updateInMaker, String pageStatuss, String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getAddressDetails()");
		ArrayList getAddressDetails = comDao.getAddressDetails(code,statusCase,updateInMaker,pageStatuss,updateFlag);
		return getAddressDetails;
	}

	@Override
	public String insertAllIntoTempFromMainTable(String cId, String cusType) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................insertAllIntoTempFromMainTable()");
		String insertAllIntoTempFromMainTable = comDao.insertAllIntoTempFromMainTable(cId,cusType);
		return insertAllIntoTempFromMainTable;
	}

	@Override
	public int saveCorporateDetails(Object corporateDetailVo, String st,
			String dealId) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................saveCorporateDetails()");
		int saveCorporateDetails = comDao.saveCorporateDetails(corporateDetailVo,st,dealId);
		return saveCorporateDetails;
	}

	@Override
	public boolean saveCorporateUpdate(Object corporateDetailVo, int id,
			String recStatus, String statusCase, String updateFlag,
			String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................saveCorporateUpdate()");
		boolean saveCorporateUpdate = comDao.saveCorporateUpdate(corporateDetailVo,id,recStatus, statusCase,
											updateFlag,	pageStatuss);
		return saveCorporateUpdate;
	}

	
	
	@Override
	public String getPanNoRecord(String tableName, int panNo) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getPanNoRecord(..)");
		String getPanNoRecord = comDao.getPanNoRecord(tableName,panNo);
		return getPanNoRecord;
	}

	@Override
	public String getCustomerRecord(int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCustomerRecord(int id)");
		String getCustomerRecord = comDao.getCustomerRecord(id);
		return getCustomerRecord;
	}

	@Override
	public String getExistingCustomerRecord(int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getExistingCustomerRecord(int id)");
		String getExistingCustomerRecord = comDao.getExistingCustomerRecord(id);
		return getExistingCustomerRecord;
	}

	
	@Override
	public String getCustomerCount(String tableName, String panNo, int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCustomerCount(...)");
		String getCustomerCount = comDao.getCustomerCount(tableName,panNo,id);
		return getCustomerCount;
	}
	
	@Override
	public String getCustomerCount(String tableName, String panNo) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCustomerCount(..)");
		String getCustomerCount = comDao.getCustomerCount(tableName,panNo);
		return getCustomerCount;
	}

	
	@Override
	public ArrayList<Object> getAdressAll(String code, Object pageStatus,
			String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getAdressAll(String code, Object pageStatus,String updateFlag)");
		ArrayList getAdressAll = comDao.getAdressAll(code,pageStatus,updateFlag);
		return getAdressAll;
	}
	
	@Override
	public ArrayList defaultcountry() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................defaultcountry()");
		ArrayList defaultcountry = comDao.defaultcountry();
		return defaultcountry;
	}

	
	@Override
	public ArrayList<Object> getAddressList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getAddressList()");
		ArrayList getAddressList = comDao.getAddressList();
		return getAddressList;
	}

	
	@Override
	public ArrayList<Object> getCountryList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCountryList()");
		ArrayList getCountryList = comDao.getCountryList();
		return getCountryList;
	}

	
	@Override
	public ArrayList getCustomerAddressDetail(String addId, Object pageStatus,
			String statusCase, String checkFlag, String updateFlag,
			String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCustomerAddressDetail(......)");
		ArrayList getCustomerAddress = comDao.getCustomerAddressDetail(addId, pageStatus,statusCase,checkFlag,updateFlag,pageStatuss);
		return getCustomerAddress;
	}

	@Override
	public int updateCustomerAddress(Object corporateDetailVo, int id,
			int addId, String recStatus, String statusCase, String updateFlag,
			String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................updateCustomerAddress(.......)");
		int updateCustomerAddress = comDao.updateCustomerAddress( corporateDetailVo, id, addId, recStatus, statusCase, updateFlag, pageStatuss);
		return updateCustomerAddress;
	}

	@Override
	public String getAddIdCommAdd(String tableName, int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getAddIdCommAdd(String tableName, int id)");
		String getAddIdCommAdd = comDao.getAddIdCommAdd(tableName, id);
		return getAddIdCommAdd;
	}

	@Override
	public String getCountCommAdd(String tableName, int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCountCommAdd(String tableName, int id)");
		String getCountCommAdd = comDao.getCountCommAdd(tableName,id);
		return getCountCommAdd;
	}

	@Override
	public ArrayList<Object> getReferenceAll(String code, Object pageStatus, String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getReferenceAll(...)");
		ArrayList getReferenceAll = comDao.getReferenceAll(code, pageStatus, updateFlag);
		return getReferenceAll;
	}

	@Override
	public ArrayList<Object> getRefrenceDetails(String code, String statusCase, String updateInMaker, String pageStatuss, String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getRefrenceDetails(.....)");
		ArrayList getRefrenceDetails = comDao.getRefrenceDetails(code, statusCase, updateInMaker, pageStatuss, updateFlag);
		return getRefrenceDetails;
	}

	@Override
	public ArrayList getIndReferenceDetail(String addId, Object pageStatus,
			String statusCase, String checkFlag, String updateFlag,
			String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getIndReferenceDetail(.....)");
		ArrayList getIndReferenceDetail = comDao.getIndReferenceDetail(addId,pageStatus,statusCase,checkFlag,updateFlag,pageStatuss);
		return getIndReferenceDetail;
	}

	
	@Override
	public ArrayList<Object> getBusinessSegmentList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getBusinessSegmentList(.....)");
		ArrayList getBusinessSegmentList = comDao.getBusinessSegmentList();
		return getBusinessSegmentList;
	}

	
	@Override
	public ArrayList<Object> getContitutionList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getContitutionList(.....)");
		ArrayList getContitutionList = comDao.getContitutionList();
		return getContitutionList;
	}

	
	@Override
	public ArrayList<Object> getCorporateDetailAll(int code, Object pageStatus,
			String statusCase, String updateInMaker, String updateFlag,
			String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCorporateDetailAll(.....)");
		ArrayList getCorporateDetailAll = comDao.getCorporateDetailAll(code,pageStatus,statusCase,updateInMaker,updateFlag,
				pageStatuss);
		return getCorporateDetailAll;
	}

	
	@Override
	public ArrayList<Object> getIndustryList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getIndustryList()");
		ArrayList getIndustryList = comDao.getIndustryList();
		return getIndustryList;
	}

	
	@Override
	public ArrayList<Object> getRegistrationTypeList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getRegistrationTypeList()");
		ArrayList getRegistrationTypeList = comDao.getRegistrationTypeList();
		return getRegistrationTypeList;
	}

	
	@Override
	public String getRegNoCheck(String tableName, String regNo, int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getRegNoCheck(String tableName, String regNo, int id)");
		String getRegNoCheck = comDao.getRegNoCheck(tableName,regNo,id);
		return getRegNoCheck;
	}

	
	@Override
	public String getVatRegNo(String tableName, String regNo, int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getVatRegNo(String tableName, String regNo, int id)");
		String getVatRegNo = comDao.getVatRegNo(tableName,regNo,id);
		return getVatRegNo;
	}
	
	@Override
	public String getRegNoCheck(String tableName, String regNo) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getRegNoCheck(String tableName, String regNo)");
		String getRegNoCheck = comDao.getRegNoCheck(tableName,regNo);
		return getRegNoCheck;
	}

	
	@Override
	public String getVatRegNo(String tableName, String regNo) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getVatRegNo(String tableName, String regNo)");
		String getVatRegNo = comDao.getVatRegNo(tableName,regNo);
		return getVatRegNo;
	}

	@Override
	public ArrayList<Object> getRoleList(String dealId) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getRoleList(String dealId)");
		ArrayList getRoleList = comDao.getRoleList(dealId);
		return getRoleList;
	}


	
	@Override
	public ArrayList<Object> getHolderTypeList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getHolderTypeList()");
		ArrayList getHolderTypeList = comDao.getHolderTypeList();
		return getHolderTypeList;
	}

	@Override
	public ArrayList<Object> getPositionList() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getPositionList()");
		ArrayList getPositionList = comDao.getPositionList();
		return getPositionList;
	}

	@Override
	public ArrayList<Object> getSalutation() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getSalutation()");
		ArrayList getSalutation = comDao.getSalutation();
		return getSalutation;
	}

	@Override
	public ArrayList<Object> getStakeDetails(int code, Object pageStatus,
			String statusCase, String updateFlag, String updateInMaker) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getStakeDetails(.....)");
		ArrayList getStakeDetails = comDao.getStakeDetails(code,pageStatus,statusCase,updateFlag,updateInMaker);
		return getStakeDetails;
	}

	@Override
	public ArrayList<Object> getStakeDetailsAll(int id, String statusCase,
			String updateInMaker, String pageStatuss, String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getStakeDetailsAll(.....)");
		ArrayList getStakeDetailsAll = comDao.getStakeDetailsAll(id,statusCase,updateInMaker,pageStatuss,updateFlag);
		return getStakeDetailsAll;
	}

	
	
	@Override
	public String getDOBfromDCM(int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getDOBfromDCM(int id)");
		String getRoleList = comDao.getDOBfromDCM(id);
		return getRoleList;
	}

	@Override
	public String getDOBfromGcdCM(int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getDOBfromGcdCM(int id)");
		String getDOBfromGcdCM = comDao.getDOBfromGcdCM(id);
		return getDOBfromGcdCM;
	}

	@Override
	public String getStackHolderDin(String tableName, String dinNo) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getStackHolderDin(String tableName, String dinNo)");
		String getStackHolderDin = comDao.getStackHolderDin(tableName, dinNo);
		return getStackHolderDin;
	}

	@Override
	public String getStackHolderID(String tableName, String id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getStackHolderID(String tableName, String id)");
		String getStackHolderID = comDao.getStackHolderID(tableName, id);
		return getStackHolderID;
	}

	
	
	@Override
	public String getCustomerWithDoj(String tableName,String getDoj, int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCustomerWithDoj(String tableName, String id)");
		String getCustomerWithDoj = comDao.getCustomerWithDoj(tableName,getDoj, id);
		return getCustomerWithDoj;
	}

	@Override
	public boolean saveStakeHolder(Object ob, int id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................saveStakeHolder(Object ob, int id)");
		boolean saveStakeHolder = comDao.saveStakeHolder(ob, id);
		return saveStakeHolder;
	}

	@Override
	public ArrayList getStackHolderDetail(String stackHolderId,
			Object pageStatus, String statusCase, String checkFlag,
			String updateFlag, String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getStackHolderDetail(......)");
		ArrayList saveStakeHolder = comDao.getStackHolderDetail(stackHolderId,pageStatus,statusCase,checkFlag,updateFlag, pageStatuss);
		return saveStakeHolder;
	}

	@Override
	public String getCustomerFromGCD(String id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCustomerFromGCD(String id)");
		String getCustomerFromGCD = comDao.getCustomerFromGCD(id);
		return getCustomerFromGCD;
	}

	@Override
	public int updateStakeHolder(Object ob, int id, int stackId,
			String recStatus, String statusCase, String updateFlag,
			String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................updateStakeHolder(......)");
		int updateStakeHolder = comDao.updateStakeHolder(ob, id, stackId,recStatus, statusCase, updateFlag, pageStatuss);
		return updateStakeHolder;
	}

	@Override
	public ArrayList getInstitutionName() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getInstitutionName()");
		ArrayList getInstitutionName = comDao.getInstitutionName();
		return getInstitutionName;
	}

	@Override
	public ArrayList<Object> getRatingDetails(int code, Object pageStatus,
			String statusCase, String updateFlag, String updateInMaker) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getRatingDetails(......)");
		ArrayList updateStakeHolder = comDao.getRatingDetails(code, pageStatus,statusCase, updateFlag, updateInMaker);
		return updateStakeHolder;
	}

	@Override
	public ArrayList<Object> getcreditRatingDetails(int cid, String statusCase,
			String updateInMaker, String pageStatuss, String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getcreditRatingDetails(......)");
		ArrayList getcreditRatingDetails = comDao.getcreditRatingDetails(cid,statusCase,updateInMaker,pageStatuss,updateFlag);
		return getcreditRatingDetails;
	}

	@Override
	public boolean saveCreditRating(Object ob, int id) {
		logger.info("Inside CustomerDetailsBeanRemote ............................... saveCreditRating(Object ob, int id)");
		boolean saveCreditRating = comDao. saveCreditRating(ob,id);
		return saveCreditRating;
	}

	@Override
	public String getCustomerFromCRM(String id) {
		logger.info("Inside CustomerDetailsBeanRemote ............................... getCustomerFromCRM(String id)");
		String getCustomerFromCRM = comDao.getCustomerFromCRM(id);
		return getCustomerFromCRM;
	}

	@Override
	public ArrayList getCreditRatingDetail(String creditRatingId,
			Object pageStatus, String statusCase, String checkFlag,
			String updateFlag, String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCreditRatingDetail(......)");
		ArrayList getCreditRatingDetail = comDao.getCreditRatingDetail(creditRatingId,pageStatus,statusCase,checkFlag,
				updateFlag,pageStatuss);
		return getCreditRatingDetail;
	}

	@Override
	public int updateCreditRating(Object ob, int id, int crId,
			String recStatus, String statusCase, String updateFlag,
			String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................updateCreditRating(......)");
		int updateCreditRating = comDao.updateCreditRating(ob,id,crId,recStatus,statusCase,updateFlag,pageStatuss);
		return updateCreditRating;
	}

	
	@Override
	public boolean saveCustomerReference(Object ob) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................saveCustomerReference(Object ob)");
		boolean saveCustomerReference = comDao.saveCustomerReference(ob);
		return saveCustomerReference;
	}

	@Override
	public String getCustomerRecord(String id) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCustomerRecord(String id)");
		String getCustomerRecord = comDao.getCustomerRecord(id);
		return getCustomerRecord;
	}

	@Override
	public int updateIndReference(Object corporateDetailVo, int id, int refId,
			String recStatus, String statusCase, String updateFlag,
			String pageStatuss) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................updateIndReference(......)");
		int getCustomerRecord = comDao.updateIndReference(corporateDetailVo,id,refId,recStatus,statusCase,updateFlag,pageStatuss);
		return getCustomerRecord;
	}

	@Override
	public boolean saveCustomerAddress(Object ob) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................saveCustomerAddress(Object ob)");
		boolean saveCustomerAddress = comDao.saveCustomerAddress(ob);
		return saveCustomerAddress;
	}

	@Override
	public String getCommAddress(String tableName, String bpId) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getCommAddress(String tableName, String bpId)");
		String getCommAddress = comDao.getCommAddress(tableName, bpId);
		return getCommAddress;
	}

	@Override
	public int deleteCustomerAddress(String addrId, String updateInMaker,
			String statusCase, Object pageStatus, String pageStatuss,
			String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................deleteCustomerAddress(......)");
		int deleteCustomerAddress = comDao.deleteCustomerAddress(addrId,updateInMaker,statusCase,pageStatus,pageStatuss,updateFlag);
		return deleteCustomerAddress;
	}

	@Override
	public int deleteCustomerReference(String addrId, String updateInMaker,
			String statusCase, Object pageStatus, String pageStatuss,
			String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................deleteCustomerReference(......)");
		int deleteCustomerReference = comDao.deleteCustomerReference(addrId,updateInMaker,statusCase,pageStatus,pageStatuss,updateFlag);
		return deleteCustomerReference;
	}

	
	
	@Override
	public int deleteStakeHolderDetails(String holderid, String updateInMaker,
			String statusCase, Object pageStatus, String pageStatuss,
			String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................deleteStakeHolderDetails(......)");
		int deleteStakeHolderDetails = comDao.deleteStakeHolderDetails(holderid,updateInMaker,statusCase,pageStatus,pageStatuss,updateFlag);
		return deleteStakeHolderDetails;
	}

	@Override
	public int deleteSelectedCreditRating(String addrId, String updateInMaker,
			String statusCase, Object pageStatus, String pageStatuss,
			String updateFlag) {
		logger.info("Inside CustomerDetailsBeanRemote ...............................deleteSelectedCreditRating(......)");
		int deleteSelectedCreditRating = comDao.deleteSelectedCreditRating(addrId,updateInMaker,statusCase,pageStatus,pageStatuss,updateFlag);
		return deleteSelectedCreditRating;
	}

	@Override
	public String getParamValForInd() {
		logger.info("Inside CustomerDetailsBeanRemote ...............................getParamValForInd()");
		String getParamValForInd = comDao.getParamValForInd();
		return getParamValForInd;
	}




}
