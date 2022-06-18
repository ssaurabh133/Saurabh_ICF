package com.masters.daoImplMSSQL;


import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.connect.md5;
import com.masters.dao.MasterDAO;
import com.masters.vo.ApprovalLevelDefVo;
import com.masters.vo.AutoAllocationDefinitionVo;
import com.masters.vo.BankAccountMasterVo;
import com.masters.vo.BankBranchMasterVo;
import com.masters.vo.BankMasterVo;
import com.masters.vo.BaseRateMasterVo;
import com.masters.vo.BenchmarkRatioVo;
import com.masters.vo.BranchMasterAreaCodeVo;
import com.masters.vo.BranchMasterVo;
import com.masters.vo.BusinessClosureVo;
import com.masters.vo.ChangePasswordMasterVo;
import com.masters.vo.ChargeCodeMasterVo;
import com.masters.vo.ChargeMasterVo;
import com.masters.vo.ConsortiumPartnerVo;
import com.masters.vo.CountryMasterVo;
import com.masters.vo.CrSchemeMasterVo;
import com.masters.vo.DealerMasterVo;
import com.masters.vo.DepartmentMasterVo;
import com.masters.vo.DistrictMasterVo;
import com.masters.vo.DocChildMasterVo;
import com.masters.vo.DocumentChecklistMasterVo;
import com.masters.vo.DocumentMasterVo;
import com.masters.vo.FinancialAnalysisVo;
import com.masters.vo.FinancialPramMasterVo;
import com.masters.vo.GcdGroupMasterVo;
import com.masters.vo.GenericMasterVo;
import com.masters.vo.IndustryMasterVo;
import com.masters.vo.MakeModelmasterVO;
import com.masters.vo.MasterVo;
import com.masters.vo.NPAMasterVo;
import com.masters.vo.ProductMasterVo;
import com.masters.vo.RateApprovalVo;
import com.masters.vo.ReasonMasterVo;
import com.masters.vo.RegionMasterVo;
import com.masters.vo.RoleAccessMasterVo;
import com.masters.vo.RoleMasterVo;
import com.masters.vo.SalesExecutiveMasterVo;
import com.masters.vo.ScoringBenchmarkMasterVo;
import com.masters.vo.StateMasterVo;
import com.masters.vo.SubDealerMasterVo;
import com.masters.vo.SubIndustryMasterVo;
import com.masters.vo.UnlockUserVo;
import com.masters.vo.UsedVehiclePricingVo;
import com.masters.vo.UserAccessMasterVo;
import com.masters.vo.UserBranchMasterVo;
import com.masters.vo.UserMasterVo;
import com.masters.vo.VerificationQuestionVo;
import com.masters.vo.pcdMasterVo;
import com.masters.vo.scoreCardMasterVo;
import com.masters.vo.MobileUserMappingVo;
import com.masters.vo.IrrCalculationMasterVo;
public class MSSQLMasterDAOImpl implements MasterDAO {
	private static final Logger logger = Logger.getLogger(MSSQLMasterDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	//String selectFrom = resource.getString("lbl.selectFrom");
	
	
	String dbo=resource.getString("lbl.dbPrefix");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList<BankBranchMasterVo> modifyBankDetailsDao(Object ob) {
		BankBranchMasterVo bankBranchMasterVo = (BankBranchMasterVo) ob;
		
		ArrayList searchlist = new ArrayList();
		String bankBranchID = "";
		ArrayList<BankBranchMasterVo> detailList = new ArrayList<BankBranchMasterVo>();
		try {
			logger.info("In modifyBankDetailsDao()....................");
			StringBuffer bufInsSql = new StringBuffer();
			bankBranchID = CommonFunction.checkNull(StringEscapeUtils.escapeSql(bankBranchMasterVo.getBankBranchId()));
			
			bufInsSql.append("SELECT BANK_BRANCH_CODE,BANK_BRANCH_NAME,BRANCH_MICR_CODE,BRANCH_IFCS_CODE,");
			bufInsSql.append(" BANK_ID,(SELECT E.BANK_NAME FROM com_bank_m  E WHERE  E.BANK_ID=D.BANK_ID ) AS BANK_DESC, ");
			bufInsSql.append(" CONTACT_PERSON,PERSON_DESIGNATION,ADDRESS_1,ADDRESS_2,ADDRESS_3,");
			bufInsSql.append(" CITY_ID,(SELECT A.DISTRICT_DESC FROM com_district_m A WHERE  A.DISTRICT_ID=D.CITY_ID ) AS CITY_DESC,");
			bufInsSql.append(" STATE_ID,(SELECT B.STATE_DESC FROM com_state_m B WHERE  B.STATE_ID=D.STATE_ID ) AS STATE_DESC,");
			bufInsSql.append(" COUNTRY_ID,(SELECT C.COUNTRY_DESC FROM com_country_m C WHERE  C.COUNTRY_ID=D.COUNTRY_ID ) AS COUNTRY_DESC,");
			bufInsSql.append(" ZIP_CODE,PHONE1,PHONE2,FAX,EMAIL_ID,case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS,ECS_Status FROM com_bankbranch_m D WHERE BANK_BRANCH_ID='"+ bankBranchID + "' ");

			logger.info("Query: "+bufInsSql.toString());
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("searchBankBranchDaoData size is...."
					+ searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchBankBranchDaoList "+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {

					bankBranchMasterVo.setBankBranchCodeModify("<a href=bankbranchMasterSearch.do?method=modifyDetails&bankbranchSearchId="
									+ CommonFunction.checkNull(data.get(0)).toString()+ ">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					bankBranchMasterVo.setBankBranchCode(CommonFunction.checkNull(data.get(0)).toString());
					bankBranchMasterVo.setBankBranchName(CommonFunction.checkNull(data.get(1)).toString());
					bankBranchMasterVo.setBranchMICRCode(CommonFunction.checkNull(data.get(2)).toString());
					bankBranchMasterVo.setBranchIFCSCode(CommonFunction.checkNull(data.get(3)).toString());

					bankBranchMasterVo.setLbxBankID(CommonFunction.checkNull(data.get(4)).toString());
					bankBranchMasterVo.setBank(CommonFunction.checkNull(data.get(5)).toString());

					bankBranchMasterVo.setContactPerson(CommonFunction.checkNull(data.get(6)).toString());
					bankBranchMasterVo.setPersonDesignation(CommonFunction.checkNull(data.get(7)).toString());
					bankBranchMasterVo.setAddress1(CommonFunction.checkNull(data.get(8)).toString());
					bankBranchMasterVo.setAddress2(CommonFunction.checkNull(data.get(9)).toString());
					bankBranchMasterVo.setAddress3(CommonFunction.checkNull(data.get(10)).toString());

					bankBranchMasterVo.setTxtDistCode(CommonFunction.checkNull(data.get(11)).toString());
					bankBranchMasterVo.setDist(CommonFunction.checkNull(data.get(12)).toString());

					bankBranchMasterVo.setTxtStateCode(CommonFunction.checkNull(data.get(13)).toString());
					bankBranchMasterVo.setState(CommonFunction.checkNull(data.get(14)).toString());

					bankBranchMasterVo.setTxtDistCode(CommonFunction.checkNull(data.get(15)).toString());
					bankBranchMasterVo.setCountry(CommonFunction.checkNull(data.get(16)).toString());

					bankBranchMasterVo.setZipCode(CommonFunction.checkNull(data.get(17)).toString());
					bankBranchMasterVo.setPhone1(CommonFunction.checkNull(data.get(18)).toString());
					bankBranchMasterVo.setPhone2(CommonFunction.checkNull(data.get(19)).toString());
					bankBranchMasterVo.setFax(CommonFunction.checkNull(data.get(20)).toString());
					bankBranchMasterVo.setEmail(CommonFunction.checkNull(data.get(21)).toString());
					bankBranchMasterVo.setBankBranchStatus(CommonFunction.checkNull(data.get(22)).toString());
					bankBranchMasterVo.setEcsStatus(CommonFunction.checkNull(data.get(23)).toString());
					bankBranchMasterVo.setBankBranchId(bankBranchID);
					detailList.add(bankBranchMasterVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	// code by Yogesh for BankBranchMaster

	public boolean insertBankBranchMaster(Object ob) {

		boolean status = false;
		BankBranchMasterVo bankBranchMasterVo = (BankBranchMasterVo) ob;
		String query = "SELECT BANK_BRANCH_CODE FROM com_bankbranch_m where  BANK_BRANCH_CODE='"
				+ StringEscapeUtils.escapeSql(bankBranchMasterVo.getBankBranchCode().trim()) + "' and  BANK_ID='"+ StringEscapeUtils.escapeSql(bankBranchMasterVo.getLbxBankID().trim())+"'";
		logger.info("in insert bank branch master dao Impl.." + query);
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		String stat = "X";
		String ecsStatus = "X";
		logger.info("in insert bank branch bankBranchMasterVo.getBankBranchStatus().."+ bankBranchMasterVo.getBankBranchStatus());
		logger.info("in bankBranchMasterVo.getEcsStatus().."
				+ bankBranchMasterVo.getEcsStatus());
		try {

			if (!st) {
				if (bankBranchMasterVo.getBankBranchStatus() != null
						&& bankBranchMasterVo.getBankBranchStatus()
								.equalsIgnoreCase("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
		if (bankBranchMasterVo.getEcsStatus() != null
						&& bankBranchMasterVo.getEcsStatus()
								.equalsIgnoreCase("on")) {
					ecsStatus = "A";
				} else {
					ecsStatus = "X";

				}
				logger.info("In insertBankBranchMaster()........");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO com_bankbranch_m(BANK_BRANCH_CODE,BANK_BRANCH_NAME,BRANCH_MICR_CODE,BRANCH_IFCS_CODE,BANK_ID, ");
				bufInsSql.append(" CONTACT_PERSON,PERSON_DESIGNATION,ADDRESS_1,ADDRESS_2,ADDRESS_3,CITY_ID,STATE_ID,COUNTRY_ID,ZIP_CODE,PHONE1,");
				bufInsSql.append(" PHONE2,FAX,EMAIL_ID,ECS_Status,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); // BANK_BRANCH_CODE
				bufInsSql.append(" ?,"); // BANK_BRANCH_NAME
				bufInsSql.append(" ?,"); // BRANCH_MICR_CODE
				bufInsSql.append(" ?,"); // BRANCH_IFCS_CODE
				bufInsSql.append(" ?,"); // BANK_ID
				bufInsSql.append(" ?,"); // CONTACT_PERSON
				bufInsSql.append(" ?,"); // PERSON_DESIGNATION
				bufInsSql.append(" ?,"); // ADDRESS_1
				bufInsSql.append(" ?,"); // ADDRESS_2
				bufInsSql.append(" ?,"); // ADDRESS_3
				bufInsSql.append(" ?,"); // CITY_ID
				bufInsSql.append(" ?,"); // STATE_ID
				bufInsSql.append(" ?,"); // COUNTRY_ID
				bufInsSql.append(" ?,"); // ZIP_CODE
				bufInsSql.append(" ?,"); // PHONE1
				bufInsSql.append(" ?,"); // PHONE2
				bufInsSql.append(" ?,"); // FAX
				bufInsSql.append(" ?,"); // EMAIL_ID
				bufInsSql.append(" ?,"); // ECS_Status
				bufInsSql.append(" ?,"); // STATUS
				bufInsSql.append(" ?,"); // MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");// MAKER_DATE
				bufInsSql.append(" ?,"); // AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))"); // AUTHOR_DATE
				
				
				if (CommonFunction.checkNull(
						bankBranchMasterVo.getBankBranchCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getBankBranchCode().trim());

				if (CommonFunction.checkNull(
						bankBranchMasterVo.getBankBranchName())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getBankBranchName().trim());

				if (CommonFunction.checkNull(
						bankBranchMasterVo.getBranchMICRCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getBranchMICRCode().trim());

				if (CommonFunction.checkNull(
						bankBranchMasterVo.getBranchIFCSCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getBranchIFCSCode().trim());

				if (CommonFunction.checkNull(bankBranchMasterVo.getLbxBankID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getLbxBankID().trim());

				if (CommonFunction.checkNull(
						bankBranchMasterVo.getContactPerson())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getContactPerson().trim());

				if (CommonFunction.checkNull(
						bankBranchMasterVo.getPersonDesignation())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getPersonDesignation().trim());

				if (CommonFunction.checkNull(bankBranchMasterVo.getAddress1())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getAddress1().trim());

				if (CommonFunction.checkNull(bankBranchMasterVo.getAddress2())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getAddress2().trim());

				if (CommonFunction.checkNull(bankBranchMasterVo.getAddress3())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getAddress3().trim());

				if (CommonFunction.checkNull(
						bankBranchMasterVo.getTxtDistCode()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getTxtDistCode().trim());
//logger.info("City: "+bankBranchMasterVo.getTxtDistCode());
//logger.info("state: "+bankBranchMasterVo.getTxtStateCode());
//logger.info("country: "+bankBranchMasterVo.getTxtCountryCode());
				if (CommonFunction.checkNull(
						bankBranchMasterVo.getTxtStateCode()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getTxtStateCode().trim());

				if (CommonFunction.checkNull(
						bankBranchMasterVo.getTxtCountryCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getTxtCountryCode().trim());

				if (CommonFunction.checkNull(bankBranchMasterVo.getZipCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getZipCode().trim());

				if (CommonFunction.checkNull(bankBranchMasterVo.getPhone1())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getPhone1().trim());

				if (CommonFunction.checkNull(bankBranchMasterVo.getPhone2())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getPhone2().trim());

				if (CommonFunction.checkNull(bankBranchMasterVo.getFax())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo.getFax()
							.trim());

				if (CommonFunction.checkNull(bankBranchMasterVo.getEmail())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getEmail().trim());

if (CommonFunction.checkNull(ecsStatus)
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(ecsStatus);
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(bankBranchMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getMakerId());

				if (CommonFunction.checkNull(bankBranchMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getMakerDate());

				if (CommonFunction.checkNull(bankBranchMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getMakerId());

				if (CommonFunction.checkNull(bankBranchMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankBranchMasterVo
							.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());

				logger.info("IN insertBankBranchMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertBankBranchMaster status is................."+ status);
				bufInsSql = null;
				insertPrepStmtObject = null;
				//manish changes for bank branch id
				if(status)
				{
					boolean status1 = false;
					ArrayList qryList1 = new ArrayList();
					insertPrepStmtObject = new PrepStmtObject();
				    String qry= " SELECT MAX(BANK_BRANCH_ID) FROM  com_bankbranch_m WITH (ROWLOCK)";
					String bank_branch_id=ConnectionDAO.singleReturn(qry);
					logger.info("bank_branch_id-----------"+bank_branch_id);
					String qryupdate=" UPDATE com_bankbranch_m SET BANK_BRANCH_CODE='"+bank_branch_id+"' WHERE BANK_BRANCH_ID='"+bank_branch_id+"'";
					
					insertPrepStmtObject.setSql(qryupdate.toString());
					logger.info("IN insertBankBranchMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
					qryList1.add(insertPrepStmtObject);
					status1 = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList1);
					logger.info("updateStatus for bank_branch_code>>-----------!!!"+qryupdate);
					qryList1.clear();
					qryList1=null;
					insertPrepStmtObject=null;
				}
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// code by yogesh for update ban branch details

	public boolean saveModifyBankDetailsDao(Object ob) {

		boolean status = false;
		BankBranchMasterVo bankBranchMasterVo = (BankBranchMasterVo) ob;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "";
		String ecsStatus = "";
		try {
			String bankBranchId = CommonFunction.checkNull(bankBranchMasterVo.getBankBranchId());
			String query = "SELECT BANK_BRANCH_CODE FROM com_bankbranch_m where  BANK_BRANCH_CODE='"
				+ StringEscapeUtils.escapeSql(bankBranchMasterVo.getBankBranchCode().trim()) + "' and  " +
						"BANK_ID='"+ StringEscapeUtils.escapeSql(bankBranchMasterVo.getLbxBankID().trim())+"' and BANK_BRANCH_ID <> '"+ bankBranchId + "'";
			logger.info("query------------>"+query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		if (!st) {
			if (bankBranchMasterVo.getBankBranchStatus() != null
					&& bankBranchMasterVo.getBankBranchStatus().equalsIgnoreCase("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
logger.info("in bankBranchMasterVo.getEcsStatus().."+ bankBranchMasterVo.getEcsStatus());
			if (bankBranchMasterVo.getEcsStatus() != null
					&& bankBranchMasterVo.getEcsStatus()
							.equalsIgnoreCase("on")) {
				ecsStatus = "A";
			} else {
				ecsStatus = "X";

			}

			logger.info("In saveModifyBankDetailsDao()........");
			StringBuffer bufInsSql = new StringBuffer();

			bufInsSql.append("UPDATE com_bankbranch_m SET BANK_BRANCH_NAME=?,BRANCH_MICR_CODE=?,BRANCH_IFCS_CODE=?,BANK_ID=?,CONTACT_PERSON=?,PERSON_DESIGNATION=?,ADDRESS_1=?,ADDRESS_2=?,ADDRESS_3=?,CITY_ID=?,STATE_ID=?,COUNTRY_ID=?,ZIP_CODE=?,PHONE1=?,");
			bufInsSql.append(" PHONE2=?,FAX=?,EMAIL_ID=?,ECS_Status=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" WHERE BANK_BRANCH_ID='"+ bankBranchId + "' ");

			if (CommonFunction.checkNull(bankBranchMasterVo.getBankBranchName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getBankBranchName().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getBranchMICRCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getBranchMICRCode().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getBranchIFCSCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getBranchIFCSCode().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getLbxBankID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getLbxBankID().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getContactPerson()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getContactPerson().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getPersonDesignation()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getPersonDesignation().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getAddress1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getAddress1().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getAddress2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getAddress2().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getAddress3()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getAddress3().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getTxtDistCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getTxtDistCode().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getTxtStateCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getTxtStateCode().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getTxtCountryCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getTxtCountryCode().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getZipCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getZipCode().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getPhone1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getPhone1().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getPhone2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getPhone2().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getFax()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getFax().trim());

			if (CommonFunction.checkNull(bankBranchMasterVo.getEmail()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(bankBranchMasterVo.getEmail().trim());

logger.info("ecsStatus.."+ ecsStatus);
			if (CommonFunction.checkNull(ecsStatus)
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ecsStatus);
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//---------------------------------------------------------
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankBranchMasterVo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(bankBranchMasterVo.getMakerId()).trim());
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankBranchMasterVo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(bankBranchMasterVo.getMakerDate()).trim());
			//---------------------------------------------------------

			insertPrepStmtObject.setSql(bufInsSql.toString());

			logger.info("IN insertBankBranchMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In insertBankBranchMaster status is................."+ status);
			bufInsSql = null;
			insertPrepStmtObject = null;

		}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// code for search bankBranchBranch
	public ArrayList searchBankBranchDao(Object ob) {
		ArrayList searchlist = new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String bankBranchCode = "";
		String bankBranchName = "";
		String branchMICRCode = "";
		String branchIFCSCode = "";
		BankBranchMasterVo bankBranchMasterVo = (BankBranchMasterVo) ob;
		ArrayList detailList = new ArrayList();
		try {
			logger.info("In searchBankBranchDao...............");
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bankBranchCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankBranchMasterVo.getBankBranchSearchCode())).trim());
			bankBranchName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankBranchMasterVo.getBankBranchSearchName())).trim());
			branchMICRCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankBranchMasterVo.getBranchMICRCode())).trim());
			branchIFCSCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankBranchMasterVo.getBranchIFCSCode())).trim());
				

			bufInsSql.append("SELECT BANK_BRANCH_ID,BANK_BRANCH_NAME,BRANCH_MICR_CODE,BRANCH_IFCS_CODE,CASE B.REC_STATUS WHEN 'A' THEN 'Active' ELSE 'Inactive' END AS REC_STATUS,BANK_BRANCH_ID,A.BANK_NAME ");
			bufInsSql.append(" FROM com_bankbranch_m B,COM_BANK_M A WHERE A.BANK_ID=B.BANK_ID  ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_bankbranch_m B,COM_BANK_M A WHERE A.BANK_ID=B.BANK_ID ");
			

			if (!bankBranchCode.equals("")){
				
				bufInsSql.append(" AND A.BANK_NAME LIKE '%" + bankBranchCode+ "%' ");
				bufInsSqlTempCount.append("  AND A.BANK_NAME LIKE '%" + bankBranchCode+ "%'");
			}
			if (!bankBranchName.equals("")){
				
				bufInsSql.append(" AND BANK_BRANCH_NAME like '%" + bankBranchName+ "%' ");
				bufInsSqlTempCount.append("  AND BANK_BRANCH_NAME like '%" + bankBranchName+ "%' ");
			}
			if (!branchMICRCode.equals("")){
				
				bufInsSql.append(" AND BRANCH_MICR_CODE LIKE '%" + branchMICRCode+ "%' ");
				bufInsSqlTempCount.append(" AND BRANCH_MICR_CODE LIKE '%" + branchMICRCode+ "%' ");
			}
			if (!branchIFCSCode.equals("")){
				
				bufInsSql.append(" AND BRANCH_IFCS_CODE like '%" + branchIFCSCode+ "%' ");
				bufInsSqlTempCount.append(" AND BRANCH_IFCS_CODE like '%" + branchIFCSCode+ "%' ");
			}

			logger.info("IN searchBankBranchDao() search query1 ### "+ bufInsSql.toString());
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			if((bankBranchCode.trim()==null && bankBranchName.trim()==null) || (bankBranchCode.trim().equalsIgnoreCase("") && bankBranchName.trim().equalsIgnoreCase("")) || bankBranchMasterVo.getCurrentPageLink()>1)
			{
			
			logger.info("current PAge Link no .................... "+bankBranchMasterVo.getCurrentPageLink());

			if(bankBranchMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (bankBranchMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY BANK_BRANCH_CODE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search searchBankBranchDaoDataList query for SQL SERVER : " + bufInsSql.toString());
	}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					BankBranchMasterVo bankVo = new BankBranchMasterVo();
			
					bankVo.setBankBranchCodeModify("<a href=bankbranchMasterSearch.do?method=modifyDetails&bankbranchSearchId="
							+ CommonFunction.checkNull(data.get(5)).toString()+ ">"
							+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					
					bankVo.setBankBranchCode(CommonFunction.checkNull(data.get(0)).toString());
					bankVo.setBankBranchName(CommonFunction.checkNull(data.get(1)).toString());
					bankVo.setBranchMICRCode(CommonFunction.checkNull(data.get(2)).toString());
					bankVo.setBranchIFCSCode(CommonFunction.checkNull(data.get(3)).toString());
					bankVo.setBankBranchStatus(CommonFunction.checkNull(data.get(4)).toString());
					bankVo.setBank(CommonFunction.checkNull(data.get(6)).toString());
					bankVo.setTotalRecordSize(count);
					detailList.add(bankVo);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			BankBranchMasterVo bankVo = new BankBranchMasterVo();
//			bankVo.setTotalRecordSize(count);
//			detailList.add(bankVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}

		return detailList;
	}

	// code by Yogesh ends here

	// code by Apoorva GCD Group Master

public String insertGroupCodeMaster(GcdGroupMasterVo gcdGroupMasterVo) {
		
		boolean status = false;
		logger.info("In insertGcdGroupMaster.........." + gcdGroupMasterVo.getGroupDescription());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String groupId ="";
		StringBuffer query = new StringBuffer();
		StringBuffer bufInsSql = new StringBuffer();
		try 
		{
			query.append("SELECT GROUP_DESC FROM gcd_group_m where  GROUP_DESC='"+ StringEscapeUtils.escapeSql(gcdGroupMasterVo.getGroupDescription().trim()) + "'");
			logger.info("In insertGcdGroupMaster.....................................Dao Impl  :  MSSQLMasterDAOImpl : "+ query);
			boolean st = ConnectionDAOforEJB.checkStatus(query.toString());
			logger.info("st -----" + st);
			if(st)
			{
				groupId="EX";	
			}
			if(!st)
			{
				if (gcdGroupMasterVo.getRecStatus() != null && gcdGroupMasterVo.getRecStatus().equalsIgnoreCase("on")) 
					stat = "A";
				 else 
					stat = "X";
				logger.info("In insert Gcd Group  master");
				bufInsSql.append("INSERT INTO gcd_group_m(GROUP_DESC,GROUP_EXPOSURE_LIMIT,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
				
				if (CommonFunction.checkNull(
						gcdGroupMasterVo.getGroupDescription())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(gcdGroupMasterVo
							.getGroupDescription().toUpperCase().trim());
				
				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(gcdGroupMasterVo.getGroupExposureLimit()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
					escapeSql(gcdGroupMasterVo.getGroupExposureLimit()).trim()).toString());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(gcdGroupMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(gcdGroupMasterVo
							.getMakerId());

				if (CommonFunction.checkNull(gcdGroupMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(gcdGroupMasterVo
							.getMakerDate());

				if (CommonFunction.checkNull(gcdGroupMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(gcdGroupMasterVo
							.getMakerId());

				if (CommonFunction.checkNull(gcdGroupMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(gcdGroupMasterVo
							.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertGroupCodeMaster() insert query1    :   "	+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertGroupCodeMaster status....................."+ status);
				if(status)
				{					
					String query1="select max(GROUP_ID) FROM gcd_group_m  WITH (ROWLOCK)  ";
					groupId = ConnectionDAOforEJB.singleReturn(query1);
					if(!groupId.equalsIgnoreCase("")){
						groupId = "S";	
					}
					query1=null;
				}
				else 
					groupId = "E";				
			} 
		} 
		catch (Exception e) 
		{
			groupId = "E";
			e.printStackTrace();			
		}
		return groupId;
	}


	public ArrayList<GcdGroupMasterVo> modifyGcdGroupDetailsDao(Object ob) {
		GcdGroupMasterVo gcdGroupMasterVo = (GcdGroupMasterVo) ob;
		ArrayList searchlist = new ArrayList();
		String groupId = "";
		ArrayList<GcdGroupMasterVo> detailList = new ArrayList<GcdGroupMasterVo>();
		try {
			logger.info("In searchBankBranchDao()....................");
			StringBuffer bufInsSql = new StringBuffer();
			groupId = CommonFunction.checkNull(StringEscapeUtils
					.escapeSql(gcdGroupMasterVo.getGcdgroupId()));
			bufInsSql
					.append(" SELECT GROUP_ID,GROUP_DESC,GROUP_EXPOSURE_LIMIT,case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM gcd_group_m WHERE GROUP_ID='" + groupId
					+ "' ORDER BY GROUP_ID");

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("searchGcdGroupDaoData size is...."
					+ searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchGcdGroupDaoList "
						+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {

					gcdGroupMasterVo
							.setGcdgroupIdModify("<a href=gcdGroupMasterSearch.do?method=modifyDetails&gcdgroupId="
									+ CommonFunction.checkNull(data.get(0))
											.toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0))
											.toString() + "</a>");

					gcdGroupMasterVo.setGcdgroupId(CommonFunction.checkNull(
							data.get(0)).toString());
					gcdGroupMasterVo.setGroupDescription(CommonFunction
							.checkNull(data.get(1)).toString());
			
					if(!CommonFunction.checkNull(data.get(2)).equalsIgnoreCase(""))	
				{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(2))).trim());
					gcdGroupMasterVo.setGroupExposureLimit(myFormatter.format(reconNum));
				}
					
					gcdGroupMasterVo.setRecStatus(CommonFunction.checkNull(
							data.get(3)).toString());
					detailList.add(gcdGroupMasterVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	public String saveModifyGcdGroupDao(Object ob) {
		String stat = "X";
		String result="";
		boolean status = false;
		GcdGroupMasterVo gcdGroupMasterVo = (GcdGroupMasterVo) ob;
		logger.info("gcdGroupMasterVo.getRecStatus()aaaaaaaaaaaaaaaaaaaaaa  saveModifyGcdGroupDao:-"
				+ gcdGroupMasterVo.getRecStatus());
		ArrayList qryList = new ArrayList();
		String groupId = CommonFunction.checkNull(gcdGroupMasterVo
				.getGcdgroupId());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder query = new StringBuilder();
		try {
		query.append("SELECT TOP 1 GROUP_DESC FROM gcd_group_m where  GROUP_DESC='"	+ StringEscapeUtils.escapeSql(gcdGroupMasterVo.getGroupDescription().trim()) + "' AND GROUP_ID<>"+groupId+"");
		
		logger.info("In saveModifyGcdGroupDao...................Dao Impl"+ query.toString());
		boolean st = ConnectionDAOforEJB.checkStatus(query.toString());
		logger.info("st -----" + st);
		if(st){
		result="EX";
		}
		if (!st) {
		if (gcdGroupMasterVo.getRecStatus() != null
				&& gcdGroupMasterVo.getRecStatus().equals("on")) {
			stat = "A";
		} else {
			stat = "X";
		}

		

			logger.info("In saveModifyGcdGroupDetailsDao()........");
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("UPDATE gcd_group_m SET GROUP_EXPOSURE_LIMIT=?,");
			bufInsSql.append("GROUP_DESC=?,");
			bufInsSql.append("GROUP_DESC_L=?,");
			bufInsSql.append("REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" WHERE GROUP_ID=? ");

			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(gcdGroupMasterVo.getGroupExposureLimit()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				escapeSql(gcdGroupMasterVo.getGroupExposureLimit()).trim()).toString());

			if (CommonFunction.checkNull(gcdGroupMasterVo.getGroupDescription()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(gcdGroupMasterVo.getGroupDescription());
			
			
			if (CommonFunction.checkNull(gcdGroupMasterVo.getGroupDescription()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(gcdGroupMasterVo.getGroupDescription());
			
			
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			//---------------
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getMakerId()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getMakerId()).trim()));
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getMakerDate()).trim()));
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getMakerId()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getMakerId()).trim()));
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getMakerDate()).trim()));
			
			//---------------
			
			if (CommonFunction.checkNull(groupId).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(groupId);

			insertPrepStmtObject.setSql(bufInsSql.toString());

			qryList.add(insertPrepStmtObject);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger
					.info("In saveModifyChargegcdGroupDao status is................."
							+ status);
			if(status){
				result="S";
			}
			else{
				result="E";
			}
			bufInsSql = null;
			insertPrepStmtObject = null;

		}
		}
		catch (Exception e) {
			result="E";
			e.printStackTrace();
		}
//	else
//	{
//		status=false;
//	}
		return result;
	}

	public ArrayList<GcdGroupMasterVo> searchGcdGroupData(Object ob) {
		ArrayList searchlist = new ArrayList();
		String groupDesc = "";
		String groupId = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		GcdGroupMasterVo gcdGroupMasterVo = (GcdGroupMasterVo) ob;
		ArrayList<GcdGroupMasterVo> detailList = new ArrayList<GcdGroupMasterVo>();
		try {
			logger.info("In searchGcdGroupDao...............");
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			groupDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getGroupSearchDescription())).trim());
			groupId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(gcdGroupMasterVo.getGcdgroupId())).trim());
			bufInsSql.append("SELECT GROUP_ID,GROUP_DESC,GROUP_EXPOSURE_LIMIT,case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM gcd_group_m ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM gcd_group_m ");
			
			if (!(groupId.equals("")) && !(groupDesc.equals(""))) {
				bufInsSql.append(" WHERE GROUP_ID = '" + groupId + "' AND GROUP_DESC like '%" + groupDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE GROUP_ID = '" + groupId + "' AND GROUP_DESC like '%" + groupDesc + "%' ");
			}
			else if (!groupId.equals("")) {
				bufInsSql.append(" WHERE GROUP_ID = '" + groupId + "' ");
				bufInsSqlTempCount.append(" WHERE GROUP_ID = '" + groupId + "' ");
			} 
			else if (!groupDesc.equals("")) {
				bufInsSql.append(" WHERE GROUP_DESC like '%" + groupDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE GROUP_DESC like '%" + groupDesc + "%' ");
			}
					
			logger.info("IN searchGcdGroupDao() search query1 ### "+ bufInsSql.toString());
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((groupId.trim()==null && groupDesc.trim()==null) || (groupId.trim().equalsIgnoreCase("") && groupDesc.trim().equalsIgnoreCase("")) || gcdGroupMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+gcdGroupMasterVo.getCurrentPageLink());
			if(gcdGroupMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (gcdGroupMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY GROUP_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
												
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("searchGcdGroupDao size is...." + searchlist.size());
			
			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					GcdGroupMasterVo gcdGroupMVo = new GcdGroupMasterVo();

					gcdGroupMVo.setGcdgroupIdModify("<a href=gcdGroupMasterSearch.do?method=modifyDetails&gcdgroupId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					gcdGroupMVo.setGcdgroupId(CommonFunction.checkNull(data.get(0)).toString());
					gcdGroupMVo.setGroupDescription(CommonFunction.checkNull(data.get(1)).toString());
				if(!CommonFunction.checkNull(data.get(2)).equalsIgnoreCase(""))
				{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(2))).trim());
					gcdGroupMVo.setGroupExposureLimit(myFormatter.format(reconNum));
				}
					
					gcdGroupMVo.setRecStatus(CommonFunction.checkNull(data.get(3)).toString());
					gcdGroupMVo.setTotalRecordSize(count);
					detailList.add(gcdGroupMVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

//		if(searchlist.size()==0)
//				{
//					GcdGroupMasterVo gcdGroupMVo = new GcdGroupMasterVo();
//					gcdGroupMVo.setTotalRecordSize(count);
//					detailList.add(gcdGroupMVo);
//					request.setAttribute("flag","yes");
//					logger.info("Detail List when searchList is null: "+detailList.size());
//				}

		return detailList;
	}

	// Query for bank master

	public String insertBankMaster(Object ob) {
		BankMasterVo vo = (BankMasterVo) ob;
		String result="";
		boolean status = false;
		StringBuffer query = new StringBuffer();
		logger.info("In insertBankMaster.....................................Dao Impl"+ vo.getBankStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		try {
		query.append("select BANK_CODE,BANK_NAME from com_bank_m where BANK_CODE='"+ StringEscapeUtils.escapeSql(vo.getBankCode().trim())+"'");
				//+ "' OR BANK_NAME='"
				//+ StringEscapeUtils.escapeSql(vo.getBankName().trim()) + "'";
		logger.info("In insertBankMaster.....................................Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query.toString());
		
			if(st)
			{
				result="EX";
			}

			if (!st) {
				if (vo.getBankStatus() != null
						&& vo.getBankStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert bank master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into com_bank_m(BANK_CODE,BANK_NAME,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				//bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				if (CommonFunction.checkNull(vo.getBankCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getBankCode()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getBankName())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getBankName()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertBankMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveBankMasterData......................"
						+ status);
				if(status){
					result="S";
				}
				else{
					result="E";
				}
			}

		} catch (Exception e) {
			result="E";
			e.printStackTrace();
		}

		return result;

	}

	public ArrayList<BankMasterVo> searchBankData(Object ob) {
		String bankCode = "";
		String bankName = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		BankMasterVo bankMasterVo = (BankMasterVo) ob;
		ArrayList<BankMasterVo> detailList = new ArrayList<BankMasterVo>();
		try {

			logger.info("In searchBankData().....................................Dao Impl");
			
			bankCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankMasterVo.getBankSearchCode())).trim());
			bankName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankMasterVo.getBankSearchName())).trim());
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select BANK_CODE,BANK_NAME,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status ");
			bufInsSql.append(" FROM com_bank_m ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_bank_m");
			
			if ((!(bankCode.equalsIgnoreCase(""))) && (!(bankName.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE BANK_NAME like '%" + bankName + "%' AND BANK_CODE='" + bankCode + "' ");
				bufInsSqlTempCount.append(" WHERE BANK_NAME like '%" + bankName + "%' AND BANK_CODE='" + bankCode + "' ");
			} 
			else if (!bankName.equals("")) {
				bufInsSql.append(" WHERE BANK_NAME LIKE '%" + bankName + "%' ");
				bufInsSqlTempCount.append(" WHERE BANK_NAME LIKE '%" + bankName + "%' ");
			}

			else if (!bankCode.equals("")) {
				bufInsSql.append(" WHERE BANK_CODE = '" + bankCode + "' ");
				bufInsSqlTempCount.append(" WHERE BANK_CODE = '" + bankCode + "' ");
			}
									
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
           
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
 //           if((bankCode.trim()==null && bankName.trim()==null) || (bankCode.trim().equalsIgnoreCase("") && bankName.trim().equalsIgnoreCase("")) || bankMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+bankMasterVo.getCurrentPageLink());
			if(bankMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (bankMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY BANK_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
//			}
			logger.info("query : "+bufInsSql);
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("searchBankData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					BankMasterVo bankMVO = new BankMasterVo();

					bankMVO.setBankCodeModify("<a href=bankMasterSearch.do?method=openEditBank&bankSearchCode="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					bankMVO.setBankCode(CommonFunction.checkNull(data.get(0)).toString());
					bankMVO.setBankName(CommonFunction.checkNull(data.get(1)).toString());
					bankMVO.setBankStatus(CommonFunction.checkNull(data.get(2)).toString());
					bankMVO.setTotalRecordSize(count);
					detailList.add(bankMVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			BankMasterVo bankMVO = new BankMasterVo();
//			bankMVO.setTotalRecordSize(count);
//			detailList.add(bankMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean updateBankData(Object ob) {
		BankMasterVo vo = (BankMasterVo) ob;
		String bankCode = (String) vo.getBankCode();
		logger.info("vo.getBankStatus():-" + vo.getBankStatus());
		ArrayList updatelist = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		boolean status = false;
		String stat = "";
		try {
			if (vo.getBankStatus() != null && vo.getBankStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateBankData.....................................Dao Impl");
			bufInsSql.append("UPDATE com_bank_m set BANK_NAME=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE= ");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where BANK_CODE=?");
			
			if (CommonFunction.checkNull(vo.getBankName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getBankName().toUpperCase()
						.trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//---------------------------------------------------------
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMakerId()).trim());
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
			//---------------------------------------------------------

			if (CommonFunction.checkNull(vo.getBankCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getBankCode().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			bufInsSql = null;
			insertPrepStmtObject = null;

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// code by Yogesh for ChargeCodeMasterInsert

	public String insertchagreCodeMaster(Object ob) {

		ChargeCodeMasterVo chargeCodeMasterVo = (ChargeCodeMasterVo) ob;
		boolean status = false;
		logger
				.info("In insertChargeCodeMaster.....................................Dao Impl"
						+ chargeCodeMasterVo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String stat1 = "N";
		String stat2 = "N";
		String result=null;
		String query = "select CHARGE_DESC from com_charge_code_m where CHARGE_DESC='"+ StringEscapeUtils.escapeSql(chargeCodeMasterVo.getChargeDescription().trim()) + "'";
		logger.info("In insert Charge Code Master.....................................Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);

		try {

			if (!st) {
				if (chargeCodeMasterVo.getRecStatus() != null
						&& chargeCodeMasterVo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				if (chargeCodeMasterVo.getManualAdviceFlag() != null
						&& chargeCodeMasterVo.getManualAdviceFlag()
								.equals("on")) {
					stat1 = "Y";
				} else {
					stat1 = "N";
				}

				if (chargeCodeMasterVo.getWaveOffFlag() != null
						&& chargeCodeMasterVo.getWaveOffFlag().equals("on")) {
					stat2 = "Y";
				} else {
					stat2 = "N";
				}

				logger.info("In insert Charge Code Master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("insert into com_charge_code_m(CHARGE_DESC,MANUAL_ADVICE_FLAG,WAIVE_OFF_FLAG,REC_STATUS,CHARGE_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,SYSTEM_DEFINED)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" 'N')");
				if (CommonFunction.checkNull(
						chargeCodeMasterVo.getChargeDescription())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeCodeMasterVo
							.getChargeDescription().toUpperCase().trim());

				if (CommonFunction.checkNull(stat1).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat1);

				if (CommonFunction.checkNull(stat2).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat2);

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(
						chargeCodeMasterVo.getChargeDescription())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeCodeMasterVo
							.getChargeDescription().toUpperCase().trim());

				if (CommonFunction.checkNull(chargeCodeMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeCodeMasterVo
							.getMakerId());

				if (CommonFunction.checkNull(chargeCodeMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeCodeMasterVo
							.getMakerDate());
				if (CommonFunction.checkNull(chargeCodeMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeCodeMasterVo
							.getMakerId());

				if (CommonFunction.checkNull(chargeCodeMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeCodeMasterVo
							.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertChargeCodeMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				if(status)
					result="datasaved";
					else
						result="datanotsaved";
					
			}else{
				result="dataexist";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("In saveChargeCodeData......................" + status);
		return result;

	}

	// code by yogesh for update charge code details

	public boolean saveModifyChargeCodeDetailsDao(Object ob) {

		ChargeCodeMasterVo chargeCodeMasterVo = (ChargeCodeMasterVo) ob;
		String chargeCode = (String) chargeCodeMasterVo.getChargeCode();
		logger.info("chargeCodeMasterVo.getChargeCode():-"
				+ chargeCodeMasterVo.getChargeCode());
		ArrayList updatelist = new ArrayList();
		// String
		// selquery="select CHARGE_CODE from com_charge_code_m where CHARGE_CODE='"+chargeCodeMasterVo.getChargeCode()+"'";
		// logger.info("In updateChargeCodeMaster.....................................Dao Impl"+selquery);
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		boolean status = false;
		String stat = "";
		String stat1 = "";
		String stat2 = "";

		try {

			if (chargeCodeMasterVo.getRecStatus() != null
					&& chargeCodeMasterVo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}

			if (chargeCodeMasterVo.getManualAdviceFlag() != null
					&& chargeCodeMasterVo.getManualAdviceFlag().equals("on")) {
				stat1 = "Y";
			} else {
				stat1 = "N";
			}

			if (chargeCodeMasterVo.getWaveOffFlag() != null
					&& chargeCodeMasterVo.getWaveOffFlag().equals("on")) {
				stat2 = "Y";
			} else {
				stat2 = "N";
			}
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In saveModifyChargeCodeDetailsDao.....................................Dao Impl");
			
			bufInsSql.append("UPDATE com_charge_code_m set CHARGE_DESC=?,MANUAL_ADVICE_FLAG=?,");
			bufInsSql.append("WAIVE_OFF_FLAG=?, REC_STATUS=?,CHARGE_DESC_L=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where CHARGE_CODE=?");

			if (CommonFunction.checkNull(
					chargeCodeMasterVo.getChargeDescription())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeCodeMasterVo
						.getChargeDescription().toUpperCase().trim());

			if (CommonFunction.checkNull(stat1).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat1);

			if (CommonFunction.checkNull(stat2).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat2);

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			if (CommonFunction.checkNull(
					chargeCodeMasterVo.getChargeDescription())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeCodeMasterVo
						.getChargeDescription().toUpperCase().trim());
			//-------------------------------------------------------
			if (CommonFunction.checkNull(chargeCodeMasterVo.getMakerId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeCodeMasterVo
						.getMakerId());
			//-------------------------------------------------------

			if (CommonFunction.checkNull(chargeCodeMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeCodeMasterVo
						.getMakerDate());

			if (CommonFunction.checkNull(chargeCodeMasterVo.getChargeCode())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeCodeMasterVo
						.getChargeCode().toUpperCase().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In saveModifyChargeCodeDetailsDao"
					+ bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public ArrayList<ChargeCodeMasterVo> searchChargeCodeDao(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		
		String chargeCode = "";
		String chargeCodeDesc = "";
		ArrayList searchlist = new ArrayList();
		ChargeCodeMasterVo chargeCodeMasterVo = (ChargeCodeMasterVo) ob;
		ArrayList<ChargeCodeMasterVo> detailList = new ArrayList<ChargeCodeMasterVo>();
		try {
			logger.info("In searchChargeCodeDao...............");
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			chargeCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeCodeMasterVo.getChargeCode())).trim());
			chargeCodeDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeCodeMasterVo.getChargeSearchDescription())).trim());
			
			bufInsSql.append("select CHARGE_CODE,CHARGE_DESC,case when MANUAL_ADVICE_FLAG='Y' then 'Yes' else 'No' end as ManualAdvice_Flag,case when WAIVE_OFF_FLAG='Y' then 'Yes' else 'No' end as WaveOff_Flag,case when REC_STATUS='A' then 'Active' else 'Inactive' end as status,case when SYSTEM_DEFINED='Y' then 'Yes' else 'No' end as SYSTEM_DEFINED");
			bufInsSql.append(" FROM com_charge_code_m  ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_charge_code_m");
		
			if (!chargeCode.equals("") && !chargeCodeDesc.equals("")) {
				bufInsSql.append(" where CHARGE_CODE = '" + chargeCode + "' AND CHARGE_DESC like '%" + chargeCodeDesc + "%'");
				bufInsSqlTempCount.append(" where CHARGE_CODE = '" + chargeCode + "' AND CHARGE_DESC like '%" + chargeCodeDesc + "%'");
			}

			else if (!chargeCode.equals("")) {
				bufInsSql.append(" where CHARGE_CODE = '" + chargeCode + "' ");
				bufInsSqlTempCount.append(" where CHARGE_CODE = '" + chargeCode + "' ");
			}

			else if (!chargeCodeDesc.equals("")) {
				bufInsSql.append(" where CHARGE_DESC like '%" + chargeCodeDesc + "%' ");
				bufInsSqlTempCount.append(" where CHARGE_DESC like '%" + chargeCodeDesc + "%' ");
			}
									
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((chargeCode.trim()==null && chargeCodeDesc.trim()==null) || (chargeCode.trim().equalsIgnoreCase("") && chargeCodeDesc.trim().equalsIgnoreCase("")) || chargeCodeMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+chargeCodeMasterVo.getCurrentPageLink());
			if(chargeCodeMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (chargeCodeMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY CHARGE_CODE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");						
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchChargeCodeDao() search query1 ### "+ bufInsSql.toString());
			logger.info("searchChargeCodeDao Data size is...."+ searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					ChargeCodeMasterVo chargeCodeVo = new ChargeCodeMasterVo();

					chargeCodeVo.setChargeCodeModify("<a href=chargeCodeMasterSearch.do?method=modifyDetails&chargeCode="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					chargeCodeVo.setChargeCode(CommonFunction.checkNull(data.get(0)).toString());
					chargeCodeVo.setChargeDescription(CommonFunction.checkNull(data.get(1)).toString());
					chargeCodeVo.setManualAdviceFlag(CommonFunction.checkNull(data.get(2)).toString());
					chargeCodeVo.setWaveOffFlag(CommonFunction.checkNull(data.get(3)).toString());
					chargeCodeVo.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
					chargeCodeVo.setSystemDefinedFlag(CommonFunction.checkNull(data.get(5)).toString());
					chargeCodeVo.setTotalRecordSize(count);
					detailList.add(chargeCodeVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			ChargeCodeMasterVo chargeCodeVo = new ChargeCodeMasterVo();
//			chargeCodeVo.setTotalRecordSize(count);
//			detailList.add(chargeCodeVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	// code by yogesh for chargeCodeSearch
	public ArrayList<ChargeCodeMasterVo> modifyChargeCodeDetailsDao(Object ob) {
		ChargeCodeMasterVo chargeCodeMasterVo = (ChargeCodeMasterVo) ob;
		ArrayList searchlist = new ArrayList();
		String chargeCode = "";
		ArrayList<ChargeCodeMasterVo> detailList = new ArrayList<ChargeCodeMasterVo>();
		try {
			logger.info("In searchBankBranchDao()....................");
			StringBuffer bufInsSql = new StringBuffer();
			chargeCode = CommonFunction.checkNull(chargeCodeMasterVo
					.getChargeCode());
			bufInsSql
					.append("SELECT CHARGE_CODE,CHARGE_DESC,MANUAL_ADVICE_FLAG,WAIVE_OFF_FLAG,REC_STATUS,SYSTEM_DEFINED ");
			bufInsSql.append(" FROM com_charge_code_m  WHERE CHARGE_CODE='"
					+ chargeCode + "' ORDER BY CHARGE_CODE ");

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("searchBankBranchDaoData size is...."
					+ searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
			
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					
					
					
					chargeCodeMasterVo.setChargeCodeModify("<a href=chargeCodeMasterSearch.do?method=modifyDetails&chargeCode="
									+ CommonFunction.checkNull(data.get(0))
											.toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0))
											.toString() + "</a>");

					chargeCodeMasterVo.setChargeCode(CommonFunction.checkNull(
							data.get(0)).toString());
					chargeCodeMasterVo.setChargeDescription(CommonFunction
							.checkNull(data.get(1)).toString());
					chargeCodeMasterVo.setManualAdviceFlag(CommonFunction
							.checkNull(data.get(2)).toString());
					chargeCodeMasterVo.setWaveOffFlag(CommonFunction.checkNull(
							data.get(3)).toString());
					chargeCodeMasterVo.setRecStatus(CommonFunction.checkNull(
							data.get(4)).toString());
					chargeCodeMasterVo.setSystemDefinedFlag(CommonFunction.checkNull(
							data.get(5)).toString());
					detailList.add(chargeCodeMasterVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	// Code For Dealer Master By Ankit
	public ArrayList searchUserEdit(String dealerId) {

		ArrayList searchlist = new ArrayList();
		DealerMasterVo vo = new DealerMasterVo();
		ArrayList<DealerMasterVo> levelDescList = new ArrayList<DealerMasterVo>();

		try {
			logger
					.info("In searchUserLevelEdit().....................................Dao Impl");

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("select b.user_id,a. user_name from cr_dsa_dealer_user_m b join sec_user_m a on  a.user_id=b.user_id where " +
					"b.DEALER_ID='"+StringEscapeUtils.escapeSql(dealerId)+"'");
			

			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN searchUserLevelEdit() search query1 ### "
					+ bufInsSql.toString());
			logger.info("searchUserLevelEdit " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger
						.info("branchDescList " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					DealerMasterVo dealerMasterVo = new DealerMasterVo();
					dealerMasterVo.setUserId(CommonFunction.checkNull(data.get(0)).toString());
					dealerMasterVo.setUserDesc(CommonFunction.checkNull(data.get(1)).toString());
					levelDescList.add(dealerMasterVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return levelDescList;
	}
	
	public String insertDealerMaster(Object ob,String userId) {
		DealerMasterVo vo = (DealerMasterVo) ob;
		String provcal="";
		boolean status = false;
		boolean st=true;
		int count=0;
		String	date=CommonFunction.changeFormat(vo.getMakerDate());
		logger.info("userId......"+userId);
		logger.info("In insertDealerMaster...........inside ejb server file...........Dao Impl"+ vo.getDealerStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String empnl = " ";
		String query = "select bp_type from cr_dsa_dealer_m where DEALER_DESC='"
				+ StringEscapeUtils.escapeSql(vo.getDealerDes().trim()) + "'";
		logger.info("query......"+query);
		try
		{
			ArrayList bpTypeList = ConnectionDAOforEJB.sqlSelect(query);
			logger.info("bpTypeList size: " + bpTypeList.size());
			for (int i = 0; i < bpTypeList.size(); i++) {
				logger.info("bpTypeList " + bpTypeList.get(i).toString());
				logger.info("vo.getLbxdealerType(): "+vo.getLbxdealerType());
				ArrayList data = (ArrayList) bpTypeList.get(i);
				if(data.contains(vo.getLbxdealerType()))
				{
					count++;
					logger.info("BP Type Match");
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		if(count==0)
			st=false;
		else
			st=true;
		
	/*	String userExist = "select user_id from cr_dsa_dealer_user_m where user_id='"+StringEscapeUtils.escapeSql(userId) +"'";
		logger.info("In insertDealerMaster..........inside ejb server file...........Dao Impl"
						+ userExist);
		boolean exist = ConnectionDAOforEJB.checkStatus(userExist);*/
		
		

		try {

			if (!st) {
				if (vo.getDealerStatus() != null
						&& vo.getDealerStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				//empannelled Status
				if (vo.getEmpanelStatus() != null&& vo.getEmpanelStatus().equals("on"))
				{
					empnl = "Y";
				} 
				else {
					empnl = "N";
				}
				logger.info("In insertDEALERMaster()........");
				logger.info("In DSA_DEALER_SAVE.....Procedure...");
				ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				String s1="";
				String s2="";
				
				
				try
				{ 
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDealerDes()).trim())); // DEALER_DESC
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxdealerType()).trim())); // BP_TYPE
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(stat).trim())); // REC_STATUS
					if(vo.getLbxBankID().equalsIgnoreCase("")){
					in.add(0);// BANK_ID
					}
					else{
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim()));
					}
					if(vo.getLbxBranchID().equalsIgnoreCase("")){
					in.add(0);// BANK_BRANCH_ID
					}
					else{
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchID()).trim()));// BANK_BRANCH_ID
                    }
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAccountNo()).trim()));// BANK_ACCOUNT
			//START BY SACHIN		
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddressDealer1()).trim()));// ADDRESS_LINE1
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddressDealer2()).trim()));// ADDRESS_LINE2
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddressDealer3()).trim()));// ADDRESS_LINE3
					if(vo.getTxtCountryCode().equalsIgnoreCase("")){
						in.add(0);// COUNTRY
					}else{
						in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtCountryCode()).trim()));// COUNTRY
					}
					if(vo.getTxtStateCode().equalsIgnoreCase("")){
						in.add(0);// STATE
					}else{
						in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtStateCode()).trim()));// STATE
					}
					if(vo.getTxtDistCode().equalsIgnoreCase("")){
						in.add(0);// DISTRICT
					}else{
						in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtDistCode()).trim()));// DISTRICT
					}
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPincode()).trim()));// PINCODE
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getContractPerson()).trim()));// CONTRACT_PERSON
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPhoneOff()).trim()));// MOBILE
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPhoneRes()).trim()));// LANDLINE
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmail()).trim()));// EMAIL
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRegistrationNo()).trim()));// REGISTRATION_NO
			//END BY SACHIN	
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(empnl).trim()));// EMPANELLED_STATUS
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));// MAKER_ID
					in.add(date);// MAKER_DATE
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));// AUTHOR_ID
					in.add(date);// AUTHOR_DATE
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(userId).trim()));// USER_ID
					
					
					out.add(s1);
					out.add(s2);
			
					logger.info("DSA_DEALER_SAVE("+in.toString()+","+out.toString()+")");
					outMessages=(ArrayList) ConnectionDAOforEJB.callSP("DSA_DEALER_SAVE",in,out);
					s1=CommonFunction.checkNull(outMessages.get(0));
					s2=CommonFunction.checkNull(outMessages.get(1));
				}
			     
				catch (Exception e) {
				e.printStackTrace();}			

				
	            if(s1.equalsIgnoreCase("S"))
	            {
	            	status=true;
	            	provcal=s1;
	            }
	            else
	            	status=false;
	            	provcal=s2;
				} else{
					provcal="dataExist";
				}
			}catch (Exception e) {
				e.printStackTrace();
				
			}
			
			return provcal;
		}

	public ArrayList searchDealerData(Object ob) {
		String dealerId = "";
		String dealerDesc = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String bankID="";
		String branchID="";

		ArrayList searchlist = new ArrayList();
		DealerMasterVo dealerMasterVo = (DealerMasterVo) ob;
		ArrayList<DealerMasterVo> detailList = new ArrayList<DealerMasterVo>();
		try {

			logger.info("In searchDealerData.............inside ejb server file...........Dao Impl");
			dealerId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealerMasterVo.getDealerId())).trim());
			dealerDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealerMasterVo.getDealerSearchDes())).trim());
			bankID=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealerMasterVo.getLbxBankID())).trim());
			branchID=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealerMasterVo.getLbxBranchID())).trim());	
			logger.info("bankID.."+bankID+"..branchID.."+branchID);
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select B.DEALER_ID,B.DEALER_DESC,B.BP_TYPE,");
			bufInsSql.append("(SELECT A.DESCRIPTION FROM generic_master A where GENERIC_KEY='BPTYPE' AND A.VALUE=B.BP_TYPE) sup,");
			bufInsSql.append("b.BANK_ID,b.BANK_BRANCH_ID,");
			bufInsSql.append("case when B.REC_STATUS='A' then 'Active' else 'Inactive' end as Status, ");
			//if(!bankID.equals("")&&!branchID.equals("")){
			bufInsSql.append("c.BANK_NAME ,d.BANK_BRANCH_NAME ,b.BANK_ACCOUNT,b.ADDRESS_LINE1,b.ADDRESS_LINE2,b.ADDRESS_LINE3,b.COUNTRY,(SELECT COUNTRY_DESC from com_country_m WHERE REC_STATUS='A' AND COUNTRY_ID=ISNULL(B.COUNTRY,0))AS COUNTRY_DESC,b.STATE,(SELECT STATE_DESC from com_state_m WHERE REC_STATUS='A' AND COUNTRY_ID=ISNULL(B.COUNTRY,0) AND STATE_ID=ISNULL(B.STATE,0))AS STATE_DESC,b.DISTRICT,(SELECT DISTRICT_DESC from com_district_m WHERE REC_STATUS='A' AND STATE_ID=ISNULL(B.STATE,0) AND DISTRICT_ID=ISNULL(B.DISTRICT,0))AS DISTRICT_DESC,b.PINCODE,b.CONTRACT_PERSON,b.MOBILE_NO,b.LANDLINE_NO,b.EMAIL,b.REGISTRATION_NO,EMPANELLED_STATUS ");
			if(CommonFunction.checkNull(dealerId).trim().equalsIgnoreCase("") && CommonFunction.checkNull(dealerDesc).trim().equalsIgnoreCase(""))
			{
			  bufInsSql.append(",DEALER_ID");
			}
			bufInsSql.append(" FROM cr_dsa_dealer_m b left join COM_BANK_M c on b.BANK_ID=c.BANK_ID ");						
			bufInsSql.append("left join COM_BANKBRANCH_M d on d.BANK_BRANCH_ID=b.BANK_BRANCH_ID ");
	
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_dsa_dealer_m B ");
		
			if (!dealerId.equals("") && !dealerDesc.equals("")) {
				bufInsSql.append(" WHERE DEALER_ID = '" + dealerId + "' AND DEALER_DESC like '%" + dealerDesc + "%'");
				bufInsSqlTempCount.append(" WHERE DEALER_ID = '" + dealerId + "' AND DEALER_DESC like '%" + dealerDesc + "%'");
			} 
			else if (!dealerId.equals("")) {
				bufInsSql.append(" WHERE DEALER_ID = '" + dealerId + "' ");
				bufInsSqlTempCount.append(" WHERE DEALER_ID = '" + dealerId + "' ");
			} 
			else if (!dealerDesc.equals("")) {
				bufInsSql.append(" WHERE DEALER_DESC like '%" + dealerDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE DEALER_DESC like '%" + dealerDesc + "%' ");
			}
					
			logger.info("search Query...." + bufInsSql);

			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
	//		if((dealerId.trim()==null && dealerDesc.trim()==null) || (dealerId.trim().equalsIgnoreCase("") && dealerDesc.trim().equalsIgnoreCase("")) || dealerMasterVo.getCurrentPageLink()>1)
	//		{
			
			logger.info("current PAge Link no .................... "+dealerMasterVo.getCurrentPageLink());
			if(dealerMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (dealerMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY B.DEALER_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
					
	//		}
			logger.info("query : "+bufInsSql);
		
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchDealerData() search query1 ### " + bufInsSql.toString());
			logger.info("In searchDealerData.....................................Dao Impl");
			logger.info("searchDealerData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					DealerMasterVo bankMVO = new DealerMasterVo();

					bankMVO.setDealerIdModify("<a href=dealerMasterSearch.do?method=openEditDealer&dealerId="
									+ CommonFunction.checkNull(data.get(0)).toString()									
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
//					if(!bankID.equals("")&&!branchID.equals("")){
						bankMVO.setDealerId(CommonFunction.checkNull(data.get(0)).toString());
						bankMVO.setDealerDes(CommonFunction.checkNull(data.get(1)).toString());
						bankMVO.setDealerType(CommonFunction.checkNull(data.get(2)).toString());
						bankMVO.setLbxdealerType(CommonFunction.checkNull(data.get(3)).toString());
						bankMVO.setLbxBankID(CommonFunction.checkNull(data.get(4)).toString());
						bankMVO.setLbxBranchID(CommonFunction.checkNull(data.get(5)).toString());			
						bankMVO.setDealerStatus(CommonFunction.checkNull(data.get(6)).toString());
						bankMVO.setBankCode(CommonFunction.checkNull(data.get(7)).toString());
						bankMVO.setBankBranchName(CommonFunction.checkNull(data.get(8)).toString());														
						bankMVO.setAccountNo(CommonFunction.checkNull(data.get(9)).toString());
						//SACHIN
						bankMVO.setAddressDealer1(CommonFunction.checkNull(data.get(10)).toString());
						bankMVO.setAddressDealer2(CommonFunction.checkNull(data.get(11)).toString());
						bankMVO.setAddressDealer3(CommonFunction.checkNull(data.get(12)).toString());
						bankMVO.setTxtCountryCode(CommonFunction.checkNull(data.get(13)).toString());
						bankMVO.setCountry(CommonFunction.checkNull(data.get(14)).toString());
						bankMVO.setTxtStateCode(CommonFunction.checkNull(data.get(15)).toString());
						bankMVO.setState(CommonFunction.checkNull(data.get(16)).toString());
						bankMVO.setTxtDistCode(CommonFunction.checkNull(data.get(17)).toString());
						bankMVO.setDist(CommonFunction.checkNull(data.get(18)).toString());
						bankMVO.setPincode(CommonFunction.checkNull(data.get(19)).toString());
						bankMVO.setContractPerson(CommonFunction.checkNull(data.get(20)).toString());
						bankMVO.setPhoneOff(CommonFunction.checkNull(data.get(21)).toString());
						bankMVO.setPhoneRes(CommonFunction.checkNull(data.get(22)).toString());
						bankMVO.setEmail(CommonFunction.checkNull(data.get(23)).toString());
						bankMVO.setRegistrationNo(CommonFunction.checkNull(data.get(24)).toString());
						bankMVO.setEmpanelStatus(CommonFunction.checkNull(data.get(25)).toString());
						//SACHIN
						
					bankMVO.setTotalRecordSize(count);
					detailList.add(bankMVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			DealerMasterVo bankMVO = new DealerMasterVo();
//			bankMVO.setTotalRecordSize(count);
//			detailList.add(bankMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}

		return detailList;
	}

	public String updateDealerData(Object ob,String[] userName) {
		
		DealerMasterVo vo = (DealerMasterVo) ob;
		String dealerId = (String) vo.getDealerId();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		logger.info("vo.getdealerStatus():-" + vo.getDealerStatus());
		ArrayList updatelist = new ArrayList();
		 String selquery="select BP_TYPE from cr_dsa_dealer_m where DEALER_ID<>'"+dealerId+"' AND DEALER_DESC='"+StringEscapeUtils.escapeSql(vo.getDealerDes())+"'";
		 logger.info("In updateDealerData.............inside ejb server file...........Dao Impl"+selquery);
		 boolean st = false;
		 int count=0;
		 String result="";
		 boolean status = false;
		String stat = "";
		
		try {
			ArrayList bpTypeList = ConnectionDAOforEJB.sqlSelect(selquery);
			logger.info("bpTypeList size: " + bpTypeList.size());
			for (int i = 0; i < bpTypeList.size(); i++) {
				logger.info("bpTypeList " + bpTypeList.get(i).toString());
				logger.info("vo.getLbxdealerType(): "+vo.getLbxdealerType());
				ArrayList data = (ArrayList) bpTypeList.get(i);
				if(data.contains(vo.getLbxdealerType()))
				{
					count++;
					logger.info("BP Type Match");
				}
			}
			if(count==0)
				st=false;
			else
				st=true;
			if (!st) {
			if (vo.getDealerStatus() != null
					&& vo.getDealerStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			//empannelled Status
			String empnl="";
			if (vo.getEmpanelStatus() != null && vo.getEmpanelStatus().equals("on")) 
				empnl = "Y";
			else 
				empnl = "N";
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateDealerData.............inside ejb server file...........Dao Impl");
			bufInsSql.append("UPDATE cr_dsa_dealer_m set DEALER_DESC=?, BP_TYPE=?,BANK_ID=?,BANK_BRANCH_ID=?,BANK_ACCOUNT=?,");
			bufInsSql.append("REC_STATUS=?, DEALER_DESC_L=?,MAKER_ID=?,MAKER_DATE= ");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(",ADDRESS_LINE1=?,ADDRESS_LINE2=?,ADDRESS_LINE3=?,COUNTRY=?,STATE=?,DISTRICT=?,PINCODE=?,CONTRACT_PERSON=?,MOBILE_NO=?,LANDLINE_NO=?,EMAIL=?,REGISTRATION_NO=?,EMPANELLED_STATUS=? ");
			bufInsSql.append(" where DEALER_ID=?");

			if (CommonFunction.checkNull(vo.getDealerDes())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDealerDes().toUpperCase()
						.trim());

			if (CommonFunction.checkNull(vo.getLbxdealerType())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxdealerType().trim());
//			Surendra Code..
			if (CommonFunction.checkNull(vo.getLbxBankID())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxBankID().trim());
			if (CommonFunction.checkNull(vo.getLbxBranchID())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxBranchID().trim());
			if (CommonFunction.checkNull(vo.getAccountNo())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAccountNo().trim());
			
//			Surendra Code End

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			if (CommonFunction.checkNull(vo.getDealerDes())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDealerDes().toUpperCase());
			//------------------------------------------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
		
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
		
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			//------------------------------------------------------------------
			//sachin
			if (CommonFunction.checkNull(vo.getAddressDealer1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAddressDealer1());
		
			if (CommonFunction.checkNull(vo.getAddressDealer2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAddressDealer2());
			
			if (CommonFunction.checkNull(vo.getAddressDealer3()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAddressDealer3());
		
			if (CommonFunction.checkNull(vo.getTxtCountryCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtCountryCode());
			
			if (CommonFunction.checkNull(vo.getTxtStateCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtStateCode());
		
			if (CommonFunction.checkNull(vo.getTxtDistCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtDistCode());
			
			if (CommonFunction.checkNull(vo.getPincode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPincode());
		
			if (CommonFunction.checkNull(vo.getContractPerson()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getContractPerson());
			
			if (CommonFunction.checkNull(vo.getPhoneOff()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPhoneOff());
		
			if (CommonFunction.checkNull(vo.getPhoneRes()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPhoneRes());
			
			if (CommonFunction.checkNull(vo.getEmail()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getEmail());
		
			if (CommonFunction.checkNull(vo.getRegistrationNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRegistrationNo());
			//sachin
			if (CommonFunction.checkNull(empnl).equalsIgnoreCase("")) // empannelled Status 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(empnl);
			if (CommonFunction.checkNull(vo.getDealerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDealerId().toUpperCase()
						.trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			
			
			//
			insertPrepStmtObject=null;
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql=null;
			bufInsSql = new StringBuffer();
			
			
			 
			String query = "DELETE FROM cr_dsa_dealer_user_m WHERE DEALER_ID='"+ dealerId + "'";
			logger.info("In insertUserBranchMaster.....................................Dao Impl"+ query);
			insertPrepStmtObject.setSql(query);
			updatelist.add(insertPrepStmtObject);
			//status = ConnectionDAOforEJB.sqlInsUpdDelete(updatelist);

			if (vo.getDealerStatus() != null
					&& vo.getDealerStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}

			if(userName!=null){
				if(userName.length>0){
			for (int i = 0; i < userName.length; i++) {
				logger.info("userName[i]"+userName[i]);
			
				insertPrepStmtObject=null;
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql=null;
				bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO cr_dsa_dealer_user_m(DEALER_ID,user_id,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
								
				if (CommonFunction.checkNull(vo.getDealerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDealerId()
							.toUpperCase());

				if (CommonFunction.checkNull(userName[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userName[i]);
				
			/*	if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);
				*/
				

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);
				//----------------------------------
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				//----------------------------------

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertUserMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				updatelist.add(insertPrepStmtObject);
			}
			//status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(insertList);
			}
			}
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			

			if(status){
				result="saved";
			}else{
				result="notsaved";
			}
			
}			
			else{
				result="dataExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// End Code of Dealer Master

	// Code For Department Master By Ankit

	public String insertDepartmentMaster(Object ob) {
		DepartmentMasterVo vo = (DepartmentMasterVo) ob;
		boolean status = false;
		String result="";
		logger.info("In insertDepartmentMaster............inside ejb server file...........Dao Impl"+ vo.getDepartmentStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "select DEPARTMENT_ID,DEPARTMENT_DESC from com_department_m where DEPARTMENT_ID='"
				+ StringEscapeUtils.escapeSql(vo.getDepartmentId().trim())
				+ "' OR DEPARTMENT_DESC='"
				+ StringEscapeUtils.escapeSql(vo.getDepartmentDes().trim())
				+ "'";
		logger
				.info("In insertDepartmentMaster...........inside ejb server file.............Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (vo.getDepartmentStatus() != null
						&& vo.getDepartmentStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert Department master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into com_department_m(DEPARTMENT_DESC,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
								
				if (CommonFunction.checkNull(vo.getDepartmentDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDepartmentDes()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertDepartmentMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				if(status){
					result="saved";
				}else{
					result="notsaved";
				}
				logger.info("In saveDepartmentData......................"
						+ status);
			}
			else{
				result="dataExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public ArrayList searchDepartmentData(Object ob) {
		String DepartmentId = "";
		String DepartmentDes = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		DepartmentMasterVo DepartmentMasterVo = (DepartmentMasterVo) ob;
		ArrayList<DepartmentMasterVo> detailList = new ArrayList<DepartmentMasterVo>();
		try {

			logger.info("In searchDepartmentData()...............inside ejb server file.............Dao Impl");
			DepartmentId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(DepartmentMasterVo.getDepartmentId())).trim());
			DepartmentDes = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(DepartmentMasterVo.getDepartmentSearchDes())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select Department_ID,Department_DESC, case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status ");
			bufInsSql.append(" FROM com_department_m ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_department_m ");
			
			if ((!(DepartmentId.equalsIgnoreCase(""))) && (!(DepartmentDes.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE Department_DESC like '%" + DepartmentDes + "%' AND DEPARTMENT_ID='" + DepartmentId + "'");
				bufInsSqlTempCount.append("WHERE Department_DESC like '%" + DepartmentDes + "%' AND DEPARTMENT_ID='" + DepartmentId + "'");
			}

			else if (!DepartmentDes.equals("")) {
				bufInsSql.append(" WHERE Department_DESC LIKE '%" + DepartmentDes + "%' ");
				bufInsSqlTempCount.append(" WHERE Department_DESC LIKE '%" + DepartmentDes + "%' ");
			}

			else if (!DepartmentId.equals("")) {
				bufInsSql.append(" WHERE DEPARTMENT_ID = '" + DepartmentId + "' ");
				bufInsSqlTempCount.append(" WHERE DEPARTMENT_ID = '" + DepartmentId + "' ");
			}
					
			logger.info("search Query...." + bufInsSql);

			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((DepartmentId.trim()==null && DepartmentDes.trim()==null) || (DepartmentId.trim().equalsIgnoreCase("") && DepartmentDes.trim().equalsIgnoreCase("")) || DepartmentMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+DepartmentMasterVo.getCurrentPageLink());
			if(DepartmentMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (DepartmentMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY Department_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchDepartmentData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchDepartmentData...........inside ejb server file............Dao Impl");
			logger.info("searchDepartmentData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					DepartmentMasterVo deptMVO = new DepartmentMasterVo();

					deptMVO.setDepartmentIdModify("<a href=departmentMasterSearch.do?method=openEditDepartment&DepartmentId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					deptMVO.setDepartmentId(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setDepartmentDes(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setDepartmentStatus(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			DepartmentMasterVo deptMVO = new DepartmentMasterVo();
//			deptMVO.setTotalRecordSize(count);
//			detailList.add(deptMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean updateDepartmentData(Object ob) {
		DepartmentMasterVo vo = (DepartmentMasterVo) ob;
		logger.info("vo.getDepartmentStatus():-" + vo.getDepartmentStatus());
		ArrayList updatelist = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		boolean status = false;
		String stat = "";
		try {

			if (vo.getDepartmentStatus() != null
					&& vo.getDepartmentStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}

			logger
					.info("In updateDepartmentData...........inside ejb server file........Dao Impl");

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" UPDATE com_department_m set Department_DESC=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where DEPARTMENT_ID=?");

			if (CommonFunction.checkNull(vo.getDepartmentDes())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDepartmentDes()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			//------------------------------------------------------------------
			if (CommonFunction.checkNull(vo.getDepartmentId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDepartmentId()
						.toUpperCase().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// End Code of Department Master

	// Code For Reason Master By Ankit

	public String insertReasonMaster(Object ob) {

		ReasonMasterVo vo = (ReasonMasterVo) ob;
		String result="";
		boolean status = false;
		logger.info("In insertReasonMaster..........inside ejb server file...........Dao Impl"+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		StringBuilder query = new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String stat1 = "N";
		
		try {
		 query.append("select REASON_TYPE from com_reason_m where REASON_TYPE='"+StringEscapeUtils.escapeSql(vo.getLbxReason().trim())+ "' AND ");
		 query.append("REASON_DESC='"+ StringEscapeUtils.escapeSql(vo.getReasonDes()) + "' ");
		logger.info("In insertReasonMaster..........inside ejb server file...........Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query.toString());
		if(st)
		{
		result="EX";	
		}
		if (!st) 
		{
				if (vo.getRecStatus() != null && vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				if (vo.getChargeFlag() != null
						&& vo.getChargeFlag().equals("on")) {
					stat1 = "Y";
				} else {
					stat1 = "N";
				}

				logger.info("In insert Reason master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into com_reason_m(REASON_TYPE,REASON_DESC,CHARGE_FLAG,REC_STATUS,REASON_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,REASON_SHORT_CODE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?)");
								
				logger.info("In insertReasonMaster...............adaf......................Dao Impl"+ vo.getLbxReason());

				if (CommonFunction.checkNull(vo.getLbxReason())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxReason()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getReasonDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getReasonDes()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat1).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat1);

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getReasonDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getReasonDes()
							.toUpperCase().trim());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				if (CommonFunction.checkNull(vo.getReasonShortcode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getReasonShortcode()
							.toUpperCase().trim());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertReasonMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveReasonData111......................"+ status);
				if(status)
				{
					result="S";
				}
				else
				{
					result="E";
				}
			}

		} catch (Exception e) {
			result="E";
			e.printStackTrace();
		}

		return result;

	}

	public ArrayList searchReasonData(Object ob) {
		String reasonId = "";
		String reasonDes = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ReasonMasterVo reasonMasterVo = (ReasonMasterVo) ob;
		ArrayList<ReasonMasterVo> detailList = new ArrayList<ReasonMasterVo>();
		try {
			logger.info("In searchReasonData()........inside ejb server file...........Dao Impl");
			reasonId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(reasonMasterVo.getReasonId())).trim());
			reasonDes = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(reasonMasterVo.getReasonSearchDes())).trim());
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			
			bufInsSql.append("SELECT REASON_ID,(SELECT A.DESCRIPTION FROM GENERIC_MASTER A WHERE GENERIC_KEY='REASON_TYPE' AND A.VALUE=B.REASON_TYPE)AS REASON_T,");
			bufInsSql.append(" REASON_DESC,case when CHARGE_FLAG='Y' then 'Yes' else 'No' end as CHARGE_FLAG, ");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ,REASON_TYPE,REASON_SHORT_CODE");
			bufInsSql.append(" from com_reason_m B ");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_reason_m B ");

			if ((!(reasonId.equalsIgnoreCase(""))) && (!(reasonDes.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE REASON_ID='" + reasonId + "' AND REASON_DESC like '%" + reasonDes + "%'");
				bufInsSqlTempCount.append(" WHERE REASON_ID='" + reasonId + "' AND REASON_DESC like '%" + reasonDes + "%'");
			} 
			else if (!reasonId.equals("")) {
				bufInsSql.append(" WHERE REASON_ID = '" + reasonId + "' ");
				bufInsSqlTempCount.append(" WHERE REASON_ID = '" + reasonId + "' ");
			} 
			else if (!reasonDes.equals("")) {
				bufInsSql.append(" WHERE REASON_DESC LIKE '%" + reasonDes + "%' ");
				bufInsSqlTempCount.append(" WHERE REASON_DESC LIKE '%" + reasonDes + "%' ");
			}
									
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((reasonId.trim()==null && reasonDes.trim()==null) || (reasonId.trim().equalsIgnoreCase("") && reasonDes.trim().equalsIgnoreCase("")) || reasonMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+reasonMasterVo.getCurrentPageLink());
			if(reasonMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (reasonMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY REASON_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
						
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchReasonData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchReasonData...........inside ejb server file..........Dao Impl");
			logger.info("searchReasonData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					ReasonMasterVo deptMVO = new ReasonMasterVo();

					deptMVO.setReasonIdModify("<a href=reasonMasterSearch.do?method=openEditReason&ReasonId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(6)).toString() + "</a>");

					deptMVO.setReasonId(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setLbxReason(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setReasonDes(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setChargeFlag(CommonFunction.checkNull(data.get(3)).toString());
					deptMVO.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
					deptMVO.setReasonType(CommonFunction.checkNull(data.get(5)).toString());
					deptMVO.setReasonShortcode(CommonFunction.checkNull(data.get(6)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			ReasonMasterVo deptMVO = new ReasonMasterVo();
//			deptMVO.setTotalRecordSize(count);
//			detailList.add(deptMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}

		return detailList;
	}

	public boolean updateReasonData(Object ob) {
		ReasonMasterVo vo = (ReasonMasterVo) ob;
		String reasonId = (String) vo.getReasonId();
		logger.info("vo.getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		boolean status = false;
		String stat = "";
		String stat1 = "";
		try {
			if (vo.getRecStatus() != null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}

			if (vo.getChargeFlag() != null && vo.getChargeFlag().equals("on")) {
				stat1 = "Y";
			} else {
				stat1 = "N";
			}

			logger
					.info("1.Reason type Id is..........." + vo.getLbxReason());
			logger
					.info("2.reason type is............." + vo.getReasonType());
			logger
					.info("In updateReasonData..........inside ejb server file...........Dao Impl");
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("UPDATE com_reason_m set REASON_TYPE=?,REASON_DESC=?,CHARGE_FLAG=?,REASON_SHORT_CODE=?,");
			bufInsSql.append(" REC_STATUS=?,MAKER_ID=?,MAKER_DATE= ");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where Reason_ID=?");

			if (CommonFunction.checkNull(vo.getLbxReason())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxReason().toUpperCase()
						.trim());

			if (CommonFunction.checkNull(vo.getReasonDes())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getReasonDes().toUpperCase()
						.trim());

			if (CommonFunction.checkNull(stat1).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat1);
			
			
			if (CommonFunction.checkNull(vo.getReasonShortcode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getReasonShortcode());



			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//-------------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			//=----------------------------------------------

			if (CommonFunction.checkNull(vo.getReasonId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getReasonId().toUpperCase()
						.trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// End Code of Reason Master

	// Code For Country Master By Ankit

	public boolean insertCountryMaster(Object ob) {
		CountryMasterVo vo = (CountryMasterVo) ob;
		boolean status = false;
		
		logger.info("In insertCountryMaster.........inside ejb server file...........Dao Impl"
						+ vo.getCountryStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

		String query = "select COUNTRY_DESC from com_country_m where COUNTRY_DESC='"
				+ StringEscapeUtils.escapeSql(vo.getCountryDes().trim()) + "'";
		logger.info("In insertCountryMaster.......inside ejb server file..........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (vo.getCountryStatus() != null
						&& vo.getCountryStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert Country master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into com_country_m(COUNTRY_DESC,CURRENCY_ID,REC_STATUS,COUNTRY_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			
				if (CommonFunction.checkNull(vo.getCountryDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCountryDes()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getcurrencyId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getcurrencyId()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getCountryDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCountryDes()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertCountryMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveCountryData......................"
						+ status);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public ArrayList searchCountryData(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String countryId = "";
		String countryDesc = "";
		ArrayList searchlist = new ArrayList();
		CountryMasterVo countryMasterVo = (CountryMasterVo) ob;
		ArrayList<CountryMasterVo> detailList = new ArrayList<CountryMasterVo>();
		try {

			logger.info("In searchCountryData()..............inside ejb server file.......................Dao Impl");
			
			countryId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(countryMasterVo.getCountryId())).trim());
			countryDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(countryMasterVo.getCountrySearchDes())).trim());
           	StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT COUNTRY_ID,COUNTRY_DESC,CURRENCY_ID,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM com_country_m ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_country_m ");

			if (!(countryId.equals("")) && !(countryDesc.equals(""))) {
				bufInsSql.append(" WHERE COUNTRY_ID = '" + countryId + "' AND COUNTRY_DESC like '%" + countryDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE COUNTRY_ID = '" + countryId + "' AND COUNTRY_DESC like '%" + countryDesc + "%' ");
			}

			else if (!countryId.equals("")) {
				bufInsSql.append(" WHERE COUNTRY_ID = '" + countryId + "' ");
				bufInsSqlTempCount.append(" WHERE COUNTRY_ID = '" + countryId + "' ");
			}
			else if (!countryDesc.equals("")) {
				bufInsSql.append(" WHERE COUNTRY_DESC like '%" + countryDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE COUNTRY_DESC like '%" + countryDesc + "%' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((countryId.trim()==null && countryDesc.trim()==null) || (countryId.trim().equalsIgnoreCase("") && countryDesc.trim().equalsIgnoreCase("")) || countryMasterVo.getCurrentPageLink()>1)
//			{
			
				logger.info("current PAge Link no .................... "+countryMasterVo.getCurrentPageLink());
				if(countryMasterVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (countryMasterVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				bufInsSql.append(" ORDER BY COUNTRY_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							
//			}
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchCountryData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchCountryData.....................................Dao Impl");
			logger.info("searchCountryData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCountryDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					CountryMasterVo deptMVO = new CountryMasterVo();
					deptMVO.setCountryIdModify("<a href=countryMaster.do?method=openEditCountry&CountryId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					deptMVO.setCountryId(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setCountryDes(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setcurrencyId(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setCountryStatus(CommonFunction.checkNull(data.get(3)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		/*if(searchlist.size()==0)
		{
			CountryMasterVo deptMVO = new CountryMasterVo();
			deptMVO.setTotalRecordSize(count);
			detailList.add(deptMVO);
			request.setAttribute("flag","yes");
			logger.info("Detail List when searchList is null: "+detailList.size());
		}*/

		return detailList;
	}

	public boolean updateCountryData(Object ob) {
		CountryMasterVo vo = (CountryMasterVo) ob;
		String countryId = (String) vo.getCountryId();
		logger.info("vo.getCountryStatus():-" + vo.getCountryStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		String selquery="select COUNTRY_ID,COUNTRY_DESC from com_country_m where COUNTRY_ID<>'"+vo.getCountryId()+"' AND COUNTRY_DESC='"+StringEscapeUtils.escapeSql(vo.getCountryDes())+"'";
		 logger.info("In updateCountryMaster.......inside ejb server file.......................Dao Impl"+selquery);
		 boolean st = ConnectionDAOforEJB.checkStatus(selquery);
		boolean status = false;
		String stat = "";

		try {
			if(!st){
			if (vo.getCountryStatus() != null
					&& vo.getCountryStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}
			StringBuffer bufInsSql = new StringBuffer();
			logger
					.info("In updateCountryData..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE com_country_m set COUNTRY_DESC=?,CURRENCY_ID=?,");
			bufInsSql.append(" REC_STATUS=?,COUNTRY_DESC_L=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where COUNTRY_ID=?");

			if (CommonFunction.checkNull(vo.getCountryDes()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getCountryDes().toUpperCase()
						.trim());

			if (CommonFunction.checkNull(vo.getcurrencyId()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getcurrencyId().toUpperCase()
						.trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			if (CommonFunction.checkNull(vo.getCountryDes()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getCountryDes().toUpperCase()
						.trim());
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			//----------------------------------

			if (CommonFunction.checkNull(vo.getCountryId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getCountryId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	// End Code of Country Master

	// Code For Region Master By Ankit

	public boolean insertRegionMaster(Object ob) {
		RegionMasterVo vo = (RegionMasterVo) ob;
		boolean status = false;
		logger
				.info("In insertCountryMaster............inside ejb server file.............Dao Impl"
						+ vo.getRegionStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "select REGION_ID,REGION_DESC from com_region_m where REGION_DESC='"
				+ StringEscapeUtils.escapeSql(vo.getRegionDes().trim()) + "'";
		logger
				.info("In insertCountryMaster.........inside ejb server file...............Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (vo.getRegionStatus() != null
						&& vo.getRegionStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert Region master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("insert into com_region_m(REGION_DESC,REC_STATUS,REGION_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
				if (CommonFunction.checkNull(vo.getRegionDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRegionDes()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getRegionDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRegionDes()
							.toUpperCase().trim());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertRegionMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveRegionData.............inside ejb server file........."
						+ status);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public ArrayList searchRegionData(Object ob) {
		String regionId = "";
		String regionDes = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		RegionMasterVo regionMasterVo = (RegionMasterVo) ob;
		ArrayList<RegionMasterVo> detailList = new ArrayList<RegionMasterVo>();
		try {

			logger.info("In searchRegionData()........inside ejb server file...........Dao Impl");
			
			regionId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(regionMasterVo.getRegionId())).trim());
			regionDes = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(regionMasterVo.getRegionSearchDes())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("SELECT REGION_ID,REGION_DESC,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM com_region_m ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_region_m ");
			
			if ((!(regionId.equalsIgnoreCase(""))) && (!(regionDes.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE REGION_DESC like '%" + regionDes + "%' AND REGION_ID='" + regionId + "'");
				bufInsSqlTempCount.append("WHERE REGION_DESC like '%" + regionDes + "%' AND REGION_ID='" + regionId + "'");
			}

			else if (!regionDes.equals("")) {
				bufInsSql.append(" WHERE REGION_DESC LIKE '%" + regionDes + "%' ");
				bufInsSqlTempCount.append(" WHERE REGION_DESC LIKE '%" + regionDes + "%' ");
			}

			else if (!regionId.equals("")) {
				bufInsSql.append(" WHERE REGION_ID = '" + regionId + "' ");
				bufInsSqlTempCount.append(" WHERE REGION_ID = '" + regionId + "' ");
			}
					
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((regionDes.trim()==null && regionId.trim()==null) || (regionDes.trim().equalsIgnoreCase("") && regionId.trim().equalsIgnoreCase("")) || regionMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+regionMasterVo.getCurrentPageLink());
			if(regionMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (regionMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY REGION_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");					
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchRegionData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchRegionData.....................................Dao Impl");
			logger.info("searchRegionData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					RegionMasterVo deptMVO = new RegionMasterVo();

					deptMVO.setRegionIdModify("<a href=regionMasterSearch.do?method=openEditRegion&RegionId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					deptMVO.setRegionId(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setRegionDes(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setRegionStatus(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			RegionMasterVo deptMVO = new RegionMasterVo();
//			deptMVO.setTotalRecordSize(count);
//			detailList.add(deptMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean updateRegionData(Object ob) {
		RegionMasterVo vo = (RegionMasterVo) ob;
		String regionId = (String) vo.getRegionId();
		logger.info("vo.getRegionStatus():-" + vo.getRegionStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();

		boolean status = false;
		String stat = "";
		String query = "select REGION_ID,REGION_DESC from com_region_m where REGION_ID <> '"+StringEscapeUtils.escapeSql(vo.getRegionId().trim())+"' AND REGION_DESC = '"+ StringEscapeUtils.escapeSql(vo.getRegionDes().trim()) + "'";
	    logger.info("In updateRegionData............inside ejb server file...............Dao Impl"+ query);
	    boolean st = ConnectionDAOforEJB.checkStatus(query);
		
	    try {
	    	if(!st){
			if (vo.getRegionStatus() != null
					&& vo.getRegionStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}
			StringBuffer bufInsSql = new StringBuffer();

			logger.info("In updateRegionData.....................................Dao Impl");
			bufInsSql.append("UPDATE com_region_m set Region_DESC=?,");
			bufInsSql.append(" REC_STATUS=?,REGION_DESC_L=?,MAKER_ID=?,MAKER_DATE= ");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where REGION_ID=?");

			if (CommonFunction.checkNull(vo.getRegionDes())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRegionDes().toUpperCase()
						.trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			if (CommonFunction.checkNull(vo.getRegionDes())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRegionDes().toUpperCase()
						.trim());
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			//----------------------------------

			if (CommonFunction.checkNull(vo.getRegionId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRegionId().toUpperCase()
						.trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} 
	    }catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	// End Code of Region Master

	// Code For District Master By Ankit

	public boolean insertDistrictMaster(Object ob) {
		DistrictMasterVo vo = (DistrictMasterVo) ob;
		boolean status = false;
		logger
				.info("In insertDistrictMaster.........inside ejb server file.............Dao Impl"
						+ vo.getDistrictStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

		String query = "select DISTRICT_DESC from com_district_m where DISTRICT_DESC='"
				+ StringEscapeUtils.escapeSql(vo.getDistrictDes().trim()) + "'";
		logger
				.info("In insertDistrictMaster.........inside ejb server file.............Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {
			if (!st) {
				if (vo.getDistrictStatus() != null
						&& vo.getDistrictStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";
				}

				logger.info("In insert District master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("insert into com_district_m(DISTRICT_DESC,STATE_ID,REC_STATUS,DISTRICT_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
								
				if (CommonFunction.checkNull(vo.getDistrictDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDistrictDes()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getLbxStateId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxStateId()
							.toUpperCase());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getDistrictDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDistrictDes()
							.toUpperCase().trim());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertDistrictMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveDistrictData......................"
						+ status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList searchDistrictData(Object ob) {
		String districtId = "";
		String districtDes = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		DistrictMasterVo districtMasterVo = (DistrictMasterVo) ob;
		ArrayList<DistrictMasterVo> detailList = new ArrayList<DistrictMasterVo>();
		try {
			logger.info("In searchDistrictData().........inside ejb server file..........Dao Impl");
			districtId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(districtMasterVo.getDistrictId())).trim());
			districtDes = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(districtMasterVo.getDistrictSearchDes())).trim());
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("SELECT DISTRICT_ID,DISTRICT_DESC,");
			bufInsSql.append(" STATE_ID,(SELECT A.STATE_DESC FROM com_state_m A WHERE STATE_ID=B.STATE_ID)AS State,");
			bufInsSql.append(" case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM com_district_m B ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_district_m B ");
			
			if ((!(districtId.equalsIgnoreCase(""))) && (!(districtDes.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE DISTRICT_DESC like '%" + districtDes + "%' AND DISTRICT_ID='" + districtId + "'");
				bufInsSqlTempCount.append(" WHERE DISTRICT_DESC like '%" + districtDes + "%' AND DISTRICT_ID='" + districtId + "'");
			}

			else if (!districtDes.equals("")) {
				bufInsSql.append(" WHERE DISTRICT_DESC LIKE '%" + districtDes + "%' ");
				bufInsSqlTempCount.append(" WHERE DISTRICT_DESC LIKE '%" + districtDes + "%' ");
			}

			else if (!districtId.equals("")) {
				bufInsSql.append(" WHERE DISTRICT_ID = '" + districtId + "' ");
				bufInsSqlTempCount.append(" WHERE DISTRICT_ID = '" + districtId + "' ");
				
			}
		
			logger.info("search Query...." + bufInsSql);
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((districtId.trim()==null && districtDes.trim()==null) || (districtId.trim().equalsIgnoreCase("") && districtDes.trim().equalsIgnoreCase("")) || districtMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+districtMasterVo.getCurrentPageLink());
			if(districtMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (districtMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY DISTRICT_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
						
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchDistrictData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchDistrictData.........inside ejb server file..........Dao Impl");
			logger.info("searchDistrictData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					DistrictMasterVo dMVO = new DistrictMasterVo();

					dMVO.setDistrictIdModify("<a href=districtMasterSearch.do?method=openEditDistrict&DistrictId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					dMVO.setDistrictId(CommonFunction.checkNull(data.get(0)).toString());
					dMVO.setDistrictDes(CommonFunction.checkNull(data.get(1)).toString());
					dMVO.setLbxStateId(CommonFunction.checkNull(data.get(2)).toString());
					dMVO.setStateId(CommonFunction.checkNull(data.get(3)).toString());
					dMVO.setDistrictStatus(CommonFunction.checkNull(data.get(4)).toString());
					dMVO.setTotalRecordSize(count);
					detailList.add(dMVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			DistrictMasterVo dMVO = new DistrictMasterVo();
//			dMVO.setTotalRecordSize(count);
//			detailList.add(dMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean updateDistrictData(Object ob) {
		DistrictMasterVo vo = (DistrictMasterVo) ob;
		String districtId = (String) vo.getDistrictId();
		logger.info("vo.getDistrictStatus():-" + vo.getDistrictStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		 String selquery="SELECT DISTRICT_ID,DISTRICT_DESC from com_district_m where DISTRICT_ID<>'"+vo.getDistrictId()+"' AND DISTRICT_DESC='"+StringEscapeUtils.escapeSql(vo.getDistrictDes())+"'";
		 logger.info("In updateDistrictMaster........inside ejb server file.............Dao Impl"+selquery);

		boolean status = false;
		String stat = "";
		boolean st = ConnectionDAOforEJB.checkStatus(selquery);

		try {
			if(!st){
			if (vo.getDistrictStatus() != null
					&& vo.getDistrictStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}

			logger
					.info("In updateDistrictData........inside ejb server file............Dao Impl");
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" UPDATE com_district_m set DISTRICT_DESC=?,STATE_ID=?, REC_STATUS=?,DISTRICT_DESC_L=?,MAKER_ID=?,MAKER_DATE= ");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where DISTRICT_ID=?");

			if (CommonFunction.checkNull(vo.getDistrictDes()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDistrictDes()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getLbxStateId()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject
						.addString(vo.getLbxStateId().toUpperCase());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			if (CommonFunction.checkNull(vo.getDistrictDes()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDistrictDes()
						.toUpperCase().trim());
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			//----------------------------------

			if (CommonFunction.checkNull(vo.getDistrictId()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDistrictId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	// End Code of District Master

	// Code For State Master By Ankit

	public boolean insertStateMaster(Object ob) {
		StateMasterVo vo = (StateMasterVo) ob;
		boolean status = false;
		logger
				.info("In insertStateMaster........inside ejb server file............Dao Impl"
						+ vo.getStateStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

		String query = "select STATE_DESC from com_state_m where STATE_DESC='"+ StringEscapeUtils.escapeSql(vo.getStateDes().trim()) + "'";
		logger
				.info("In insertStateMaster........inside ejb server file...........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {
			if (!st) {
				if (vo.getStateStatus() != null
						&& vo.getStateStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";
				}

				logger.info("In insert State master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("insert into com_state_m(STATE_DESC,COUNTRY_ID,REC_STATUS,STATE_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				if (CommonFunction.checkNull(vo.getStateDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getStateDes()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getTxtCountryCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTxtCountryCode()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getStateDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getStateDes()
							.toUpperCase().trim());
				insertPrepStmtObject.setSql(bufInsSql.toString());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				logger.info("IN insertStateMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveStateData......................"
						+ status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList searchStateData(Object ob) {
		String stateId = "";
		String stateDes = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		StateMasterVo stateMasterVo = (StateMasterVo) ob;
		ArrayList<StateMasterVo> detailList = new ArrayList<StateMasterVo>();
		try {
			logger.info("In searchStateData().......................inside ejb server file.......................Dao Impl");
			stateId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(stateMasterVo.getStateId())).trim());
			stateDes =(StringEscapeUtils.escapeSql(CommonFunction.checkNull(stateMasterVo.getStateSearchDes())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("SELECT STATE_ID,STATE_DESC,COUNTRY_ID,");
			bufInsSql.append("(SELECT A.COUNTRY_DESC FROM COM_COUNTRY_M A WHERE A.COUNTRY_ID=B.COUNTRY_ID)AS COUNTRY,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM COM_STATE_M B ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM COM_STATE_M B ");
			
			if ((!(stateId.equalsIgnoreCase(""))) && (!(stateDes.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE STATE_DESC like '%" + stateDes + "%' AND STATE_ID='" + stateId + "'");
				bufInsSqlTempCount.append("WHERE STATE_DESC like '%" + stateDes + "%' AND STATE_ID='" + stateId + "'");
			} 
			else if (!stateDes.equals("")) {
				bufInsSql.append(" WHERE STATE_DESC LIKE '%" + stateDes + "%' ");
				bufInsSqlTempCount.append(" WHERE STATE_DESC LIKE '%" + stateDes + "%' ");
			} 
			else if (!stateId.equals("")) {
				bufInsSql.append(" WHERE STATE_ID = '" + stateId + "' ");
				bufInsSqlTempCount.append(" WHERE STATE_ID = '" + stateId + "' ");
			}
							
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((stateId.trim()==null && stateDes.trim()==null) || (stateId.trim().equalsIgnoreCase("") && stateDes.trim().equalsIgnoreCase("")) || stateMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+stateMasterVo.getCurrentPageLink());
			if(stateMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (stateMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY STATE_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");						
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		
			logger.info("IN searchStateData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchStateData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					StateMasterVo deptMVO = new StateMasterVo();

					deptMVO.setStateIdModify("<a href=stateMasterSearch.do?method=openEditState&StateId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					deptMVO.setStateId(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setStateDes(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setTxtCountryCode(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setCountryId(CommonFunction.checkNull(data.get(3)).toString());
					deptMVO.setStateStatus(CommonFunction.checkNull(data.get(4)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			StateMasterVo deptMVO = new StateMasterVo();
//			deptMVO.setTotalRecordSize(count);
//			detailList.add(deptMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean updateStateData(Object ob) {
		StateMasterVo vo = (StateMasterVo) ob;
		String stateId = (String) vo.getStateId();
		logger.info("vo.getStateStatus():-" + vo.getStateStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		String selquery="select STATE_DESC from com_state_m where STATE_ID<>'"+vo.getStateId()+"' AND STATE_DESC='"+vo.getStateDes()+"'";
		logger.info("In updateStateMaster............inside ejb server file.......................Dao Impl"+selquery);

		boolean status = false;
		String stat = "";
		boolean st = ConnectionDAOforEJB.checkStatus(selquery);

		try {
			if(!st){
			if (vo.getStateStatus() != null && vo.getStateStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}

			logger
					.info("In updateStateData...........inside ejb server file...........................Dao Impl");
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" UPDATE com_state_m set STATE_DESC=?,COUNTRY_ID=?, REC_STATUS=?,STATE_DESC_L=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append("where STATE_ID=?");

			if (CommonFunction.checkNull(vo.getStateDes()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getStateDes().toUpperCase()
						.trim());

			if (CommonFunction.checkNull(vo.getTxtCountryCode())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtCountryCode()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			if (CommonFunction.checkNull(vo.getStateDes()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getStateDes().toUpperCase()
						.trim());
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			//----------------------------------
			insertPrepStmtObject.setSql(bufInsSql.toString());

			if (CommonFunction.checkNull(vo.getStateId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getStateId());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	// End Code of State Master

	// Code BY Ritu for agency master
	public String insertAgencyMaster(Object ob,String[] userMapping) {
		MasterVo vo = (MasterVo) ob;
		boolean status = false;
		StringBuffer query = new StringBuffer();
		String result="";
		logger.info("In insertAgencyMaster.............inside ejb server file............Dao Impl "	+ vo.getAgencyStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject insertPrepMappObject= null;
		StringBuffer bufInsSqlMapping=null;
		String stat = "X";
		String agencyType=CommonFunction.checkNull(vo.getAgencyType());
		try {
		query.append("select AGENCY_CODE,AGENCY_NAME from com_agency_m where  AGENCY_CODE='"+ StringEscapeUtils.escapeSql(vo.getAgencyCode().trim())+"'");
			//	+ "'OR AGENCY_NAME='"
				//+ StringEscapeUtils.escapeSql(vo.getAgencyDesc().trim()) + "'";
		logger.info("In insertAgencyMaster..............inside ejb server file............Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query.toString());
			if(st)
			{
				result="EX";	
			}

			if (!st) {
				if (vo.getAgencyStatus() != null
						&& vo.getAgencyStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert agency master");
				StringBuffer bufInsSql = new StringBuffer();
				
				if(agencyType.contains("EA"))
				{
					logger.info("In insert agency master Mapping");
				for (int i = 0; i < userMapping.length; i++) {
				bufInsSqlMapping= new StringBuffer();
				insertPrepMappObject=new PrepStmtObject();
				
				bufInsSqlMapping.append("Insert into com_agency_user_mapping(AGENCY_CODE,USER_ID,REC_STATUS)");
				bufInsSqlMapping.append(" values ( ");
				bufInsSqlMapping.append(" ?,");
				bufInsSqlMapping.append(" ?,");
				bufInsSqlMapping.append(" ?)");
				
				if (CommonFunction.checkNull(vo.getAgencyCode()).equalsIgnoreCase(""))
					insertPrepMappObject.addNull();
				else
					insertPrepMappObject.addString(vo.getAgencyCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(userMapping[i]).equalsIgnoreCase(""))
					insertPrepMappObject.addNull();
				else
					insertPrepMappObject.addString(userMapping[i].toUpperCase().trim());
				
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepMappObject.addNull();
				else
					insertPrepMappObject.addString(stat);
				
				insertPrepMappObject.setSql(bufInsSqlMapping.toString());
				qryList.add(insertPrepMappObject);
				}
				}
				bufInsSql.append("insert into com_agency_m(AGENCY_CODE,AGENCY_NAME,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,AGENCY_TYPE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ? )");
				if (CommonFunction.checkNull(vo.getAgencyCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAgencyCode().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getAgencyDesc())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAgencyDesc()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				if (CommonFunction.checkNull(vo.getAgencyType())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAgencyType());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN insertAgencyMaster() insert query1 ### " + insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveAgencyMasterData......................"
						+ status);
				if(status)
				{
					result="S";	
				}
				else
				{
					result="E";	
				}
			} 
		} catch (Exception e) {
			result="E";	
			e.printStackTrace();
		}

		return result;

	}

	public ArrayList searchAgencyData(Object ob) {
		String agencyCode = "";
		String agencyDesc = "";
		String query1="";
	
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		MasterVo masterVo = (MasterVo) ob;
		ArrayList<MasterVo> detailList = new ArrayList<MasterVo>();
		try {

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			agencyCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVo.getAgencySearchCode())).trim());
			agencyDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVo.getAgencySearchDesc())).trim());
			
			logger.info("In searchAgencyData()...........inside ejb server file............Dao Impl");
			
			bufInsSql.append("SELECT AGENCY_CODE,AGENCY_NAME,case when REC_STATUS='A' then 'Active' else 'Inactive' end as status,AGENCY_TYPE");
			bufInsSql.append(" FROM COM_AGENCY_M ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM COM_AGENCY_M ");
			
			if ((!(agencyCode.equalsIgnoreCase(""))) && (!(agencyDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE AGENCY_NAME like '%" + agencyDesc+ "%' AND AGENCY_CODE='" + agencyCode + "'");
				bufInsSqlTempCount.append("WHERE AGENCY_NAME like '%" + agencyDesc+ "%' AND AGENCY_CODE='" + agencyCode + "'");
			}

			else if (!agencyDesc.equals("")) {
				bufInsSql.append(" WHERE AGENCY_NAME LIKE '%" + agencyDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE AGENCY_NAME LIKE '%" + agencyDesc + "%' ");
			}

			else if (!agencyCode.equals("")) {
				bufInsSql.append(" WHERE AGENCY_CODE = '" + agencyCode + "' ");
				bufInsSqlTempCount.append(" WHERE AGENCY_CODE = '" + agencyCode + "' ");
			}
								
			logger.info("search Query...." + bufInsSql);
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((agencyCode.trim()==null && agencyDesc.trim()==null) || (agencyCode.trim().equalsIgnoreCase("") && agencyDesc.trim().equalsIgnoreCase("")) || masterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+masterVo.getCurrentPageLink());
			if(masterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (masterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//Nishant space starts
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			bufInsSql.append(" ORDER BY AGENCY_CODE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
//			}
			logger.info("query : "+bufInsSql);
						
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("searchAgencyData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					MasterVo mVo = new MasterVo();

					mVo.setAgencyCodeModify("<a href=agencyMasterSearch.do?method=openEditAgency&agencySearchCode="+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0))
											.toString() + "</a>");

					// mVo.setAgencyCode("<a href="#" onclick="modifyDetails(CommonFunction.checkNull(data.get(0)).toString());" ></a>");
					mVo.setAgencyCode(CommonFunction.checkNull(data.get(0)).toString());
					mVo.setAgencyDesc(CommonFunction.checkNull(data.get(1)).toString());
					mVo.setAgencyStatus(CommonFunction.checkNull(data.get(2)).toString());
					mVo.setAgencyType(CommonFunction.checkNull(data.get(3)).toString());
					detailList.add(mVo);
					mVo.setTotalRecordSize(count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			MasterVo mVo = new MasterVo();
//			mVo.setTotalRecordSize(count);
//			detailList.add(mVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}


	
	public ArrayList<UnlockUserVo> searchUnlockuser(UnlockUserVo vo) {
		String userCode = "";
		String userName = "";
		String query1="";
	
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		logger.info("In searchUnlockuser().....................................Dao Impl");
		ArrayList<UnlockUserVo> detailList = new ArrayList<UnlockUserVo>();
		try {

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			userCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserId())).trim());
			userName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserName())).trim());
			
			logger.info("userCode.->.......Dao Impl"+userCode);
			logger.info("userName-------------->Dao Impl"+userName);
			bufInsSql.append("select USER_ID,USER_NAME from sec_user_m  WHERE  ACCOUNT_STATUS ='L' AND REC_STATUS='A' ");
			//bufInsSqlTempCount.append("SELECT COUNT(1) FROM sec_user_m ");
			
			if ((!(userCode.equalsIgnoreCase(""))) && (!(userName.equalsIgnoreCase("")))) {
				bufInsSql.append("AND USER_NAME like '%" + userName+ "%' AND USER_ID='" + userCode + "'");
			//	bufInsSqlTempCount.append("WHERE USER_NAME like '%" + userName+ "%' AND USER_ID='" + userCode + "'");
			}

			else if (!userName.equals("")) {
				bufInsSql.append(" AND USER_NAME LIKE '%" + userName + "%' ");
			//	bufInsSqlTempCount.append(" WHERE USER_NAME LIKE '%" + userName + "%' ");
			}

			else if (!userCode.equals("")) {
				bufInsSql.append(" AND USER_ID = '" + userCode + "' ");
			//	bufInsSqlTempCount.append(" WHERE USER_ID = '" + userCode + "' ");
			}
		
			
			logger.info("search Query...." + bufInsSql);
			
			//logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
         //   count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((userCode.trim()==null && userName.trim()==null) || (userCode.trim().equalsIgnoreCase("") && userName.trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
//			{
//			
//			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
//			if(vo.getCurrentPageLink()>1)
//			{
//				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
//				endRecordIndex = no;
//				logger.info("startRecordIndex .................... "+startRecordIndex);
//				logger.info("endRecordIndex .................... "+endRecordIndex);
//			}
//			
//			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
//			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
//			}
			logger.info("query : "+bufInsSql);
						
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			logger.info("searchUnlockuser " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchUnlockuserliST "
						+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					UnlockUserVo mVo = new UnlockUserVo();

				
					mVo.setUserId(CommonFunction.checkNull(data.get(0)).toString());
					mVo.setUserName(CommonFunction.checkNull(data.get(1)).toString());
					
					detailList.add(mVo);
					mVo.setTotalRecordSize(count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return detailList;
	}
	public boolean updateUnlockuser(UnlockUserVo vo,String[] users) {
		
	
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject insertPrepMappObject= null;
		StringBuffer bufInsSqlMapping=null;
		ArrayList updatelist = new ArrayList();
		ArrayList deletList = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		boolean status = false;
		String stat = "";
		try {


			
			logger.info("users.length"+users.length);
			for(int i=0;i<users.length;i++){
				logger.info("users[i]"+users[i]);
				insertPrepStmtObject=null;
				bufInsSql=null;
				bufInsSql = new StringBuffer();
				insertPrepStmtObject = new PrepStmtObject();
			logger.info("In updateUnlockuser.....................................Dao Impl");
			bufInsSql.append("UPDATE sec_user_m SET ACCOUNT_STATUS='U' ,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE= ");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where USER_ID=?");
			//---------------
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
			//---------------
			
			if (CommonFunction.checkNull(users[i]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(users[i]);
			}
		
			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info(insertPrepStmtObject.printQuery());
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);

		}
		catch (Exception e) {
			e.printStackTrace();

		}finally{
			bufInsSql=null;
			insertPrepStmtObject=null;
		}
		return status;
	}

		public ArrayList searchAgencyDataMapping(Object ob){
		String agencyCode = "";
		
		ArrayList searchlist = new ArrayList();
		MasterVo masterVo = (MasterVo) ob;
		ArrayList<MasterVo> detailList = new ArrayList<MasterVo>();
		try {

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			agencyCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVo.getAgencySearchCode())).trim());
			logger.info("In searchAgencyDataMapping()....................."+agencyCode);		
			logger.info("In searchAgencyDataMapping()...........inside ejb server file............Dao Impl");
			bufInsSql.append("SELECT A.USER_NAME,B.USER_ID FROM sec_user_m A,com_agency_user_mapping B WHERE A.USER_ID=B.USER_ID AND AGENCY_CODE='" + agencyCode + "' ");
	
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("searchAgencyData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchAgencyDataMappingList "	+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					MasterVo mVo = new MasterVo();
					mVo.setUserName(CommonFunction.checkNull(data.get(0)).toString());
					mVo.setLbxUserIds(CommonFunction.checkNull(data.get(1)).toString());
					detailList.add(mVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return detailList;
	}

	
	
	public boolean updateAgencyData(Object ob,String[] userMapping) {
		MasterVo vo = (MasterVo) ob;
		String agencyCode = (String) vo.getAgencyCode();
		logger.info("vo.getAgencyStatus():-" + vo.getAgencyStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject insertPrepMappObject= null;
		StringBuffer bufInsSqlMapping=null;
		ArrayList updatelist = new ArrayList();
		ArrayList deletList = new ArrayList();
		String agencyType=CommonFunction.checkNull(vo.getAgencyType());
		boolean status = false;
		String stat = "";
		try {

			if (vo.getAgencyStatus() != null && vo.getAgencyStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
//			String query = "select AGENCY_CODE,AGENCY_NAME from com_agency_m where  AGENCY_CODE='"
//				+ StringEscapeUtils.escapeSql(vo.getAgencyCode().trim())
//				//+ "'OR AGENCY_NAME='"
//				+ StringEscapeUtils.escapeSql(vo.getAgencyDesc().trim()) + "'";
//		logger
//				.info("In insertAgencyMaster.....................................Dao Impl"
//						+ query);
//		boolean st = ConnectionDAOforEJB.checkStatus(query);
		//if (!st) {
			StringBuffer bufInsSql = new StringBuffer();
			
			
				String delQuery = "DELETE from com_agency_user_mapping where AGENCY_CODE='"+vo.getAgencyCode()+"' ";
				deletList.add(delQuery);
				status = ConnectionDAOforEJB.sqlInsUpdDelete(deletList);
				logger.info("In insert agency master Mapping");
				if(agencyType.contains("EA"))
				{
			for (int i = 0; i < userMapping.length; i++) {
			bufInsSqlMapping= new StringBuffer();
			insertPrepMappObject=new PrepStmtObject();
			
			bufInsSqlMapping.append("Insert into com_agency_user_mapping(AGENCY_CODE,USER_ID,REC_STATUS)");
			bufInsSqlMapping.append(" values ( ");
			bufInsSqlMapping.append(" ?,");
			bufInsSqlMapping.append(" ?,");
			bufInsSqlMapping.append(" ?)");
			
			if (CommonFunction.checkNull(vo.getAgencyCode()).equalsIgnoreCase(""))
				insertPrepMappObject.addNull();
			else
				insertPrepMappObject.addString(vo.getAgencyCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(userMapping[i]).equalsIgnoreCase(""))
				insertPrepMappObject.addNull();
			else
				insertPrepMappObject.addString(userMapping[i].toUpperCase().trim());
			
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepMappObject.addNull();
			else
				insertPrepMappObject.addString(stat);
			
			insertPrepMappObject.setSql(bufInsSqlMapping.toString());
			updatelist.add(insertPrepMappObject);
			}
			}

			logger.info("In updateAgencyData...........inside ejb server file............Dao Impl");
			bufInsSql.append("UPDATE COM_AGENCY_M SET AGENCY_NAME=?,REC_STATUS=?,AGENCY_TYPE=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where AGENCY_CODE=?");

			if (CommonFunction.checkNull(vo.getAgencyDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAgencyDesc());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			
			if (CommonFunction.checkNull(vo.getAgencyType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAgencyType().toUpperCase().trim());

			//---------------
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));

			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
		    //---------------
			
			if (CommonFunction.checkNull(vo.getAgencyCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAgencyCode().toUpperCase().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info(insertPrepStmtObject.printQuery());
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
		//}
//		else {
//			status = false;
//		}
		
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

	
	public ArrayList getAgency() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getAgency.............inside ejb server file............Dao Impl");
			String query = "select DESCRIPTION,VALUE FROM GENERIC_MASTER WHERE GENERIC_KEY='AGENCY_TYPE' and rec_status ='A'";
			MasterVo vO = null;
			ArrayList agency = ConnectionDAOforEJB.sqlSelect(query);
			logger.info("getAgency " + agency.size());
			for (int i = 0; i < agency.size(); i++) {
				logger.info("getAgency "
						+ CommonFunction.checkNull(agency.get(i)).toString());
				ArrayList data = (ArrayList) agency.get(i);
				for (int k = 0; k < data.size(); k++) {
					logger.info("getAgency "
							+ CommonFunction.checkNull(data.get(k)).toString());
					vO = new MasterVo();
					vO.setAgencyDescription(((String) data.get(0).toString()));
					vO.setAgencyValue(((String) data.get(1).toString()));
				}
				list.add(vO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// Code End BY Ritu for Agency Master


	// Code Start By Ritu For Branch Master

	public String insertBranchMaster(Object ob) {
	
		BranchMasterVo branchMastervo = (BranchMasterVo) ob;
		boolean status = false;
		logger.info("In insert branch master");
		logger.info("In insertBranchMaster.........."+ branchMastervo.getBranchStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String result="";
		String query = "SELECT BRANCH_DESC,BRANCH_ACCOUNT,BRANCH_SHORT_CODE FROM COM_BRANCH_M where  BRANCH_DESC='"+ StringEscapeUtils.escapeSql(branchMastervo.getBranchDesc().trim()) + "' OR BRANCH_SHORT_CODE='"+ StringEscapeUtils.escapeSql(branchMastervo.getBranchShortCode().trim()) + "' ";
		logger.info("In insertBranchMaster.....................................Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (branchMastervo.getBranchStatus() != null && branchMastervo.getBranchStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				
				insertPrepStmtObject = new PrepStmtObject();
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO COM_BRANCH_M(BRANCH_SHORT_CODE,BRANCH_DESC,BRANCH_ACCOUNT,COMPANY_ID,REGION_ID,REC_STATUS,BRANCH_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,COUNTRY_ID,STATE_ID,DISTRICT_ID)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//COUNTRY_ID
				bufInsSql.append(" ?,");//STATE_ID
		
				bufInsSql.append(" ?)");//DISTRICT_ID
				
				
				if (CommonFunction.checkNull(branchMastervo.getBranchShortCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getBranchShortCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(branchMastervo.getBranchDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getBranchDesc().toUpperCase().trim());

				if (CommonFunction.checkNull(branchMastervo.getBranchAccount()).equalsIgnoreCase(""))
					insertPrepStmtObject.addInt(0);
				else
					insertPrepStmtObject.addString(branchMastervo.getBranchAccount().trim());

				if (CommonFunction.checkNull(branchMastervo.getLbxCompanyID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getLbxCompanyID());

				if (CommonFunction.checkNull(branchMastervo.getLbxRegionID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getLbxRegionID());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(branchMastervo.getBranchDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getBranchDesc().trim());

				if (CommonFunction.checkNull(branchMastervo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getMakerId());

				if (CommonFunction.checkNull(branchMastervo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getMakerDate());

				if (CommonFunction.checkNull(branchMastervo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getMakerId());

				if (CommonFunction.checkNull(branchMastervo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getMakerDate());
				
				if (CommonFunction.checkNull(branchMastervo.getTxtCountryCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getTxtCountryCode());
				
				if (CommonFunction.checkNull(branchMastervo.getTxtStateCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getTxtStateCode());
				
				if (CommonFunction.checkNull(branchMastervo.getTxtDistCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchMastervo.getTxtDistCode());
				
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
				
				logger.info("IN insertBranchMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
			
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveBranchMasterData......................"+ status);
				if(status){
					String q1="select max(BRANCH_ID) from com_branch_m  WITH (ROWLOCK)  ";
					result=ConnectionDAOforEJB.singleReturn(q1);
				}
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList searchBranchData(Object ob) {
		String branchId = "";
		String branchDesc = "";
		
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		BranchMasterVo branchMasterVo = (BranchMasterVo) ob;
		ArrayList<BranchMasterVo> detailList = new ArrayList<BranchMasterVo>();
		try {
			logger.info("In searchBranchData()............inside ejb server file............Dao Impl");
			branchId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(branchMasterVo.getBranchId())).trim());
			branchDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(branchMasterVo.getBranchSearchDesc())).trim());
		
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("SELECT BRANCH_ID,BRANCH_DESC,BRANCH_ACCOUNT,");
			bufInsSql.append("COMPANY_ID,(SELECT A.COMPANY_DESC FROM COM_COMPANY_M A WHERE  A.COMPANY_ID=C.COMPANY_ID ) AS COMPANY_DESC,");
			bufInsSql.append("REGION_ID,(SELECT B.REGION_DESC FROM COM_REGION_M B WHERE B.REGION_ID=C.REGION_ID ) AS REGION_DESC,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,BRANCH_SHORT_CODE," );
			bufInsSql.append("COUNTRY_ID,(SELECT E.COUNTRY_DESC FROM com_country_m E WHERE  E.COUNTRY_ID=C.COUNTRY_ID ) AS COUNTRY_DESC," );
			bufInsSql.append("STATE_ID,(SELECT D.STATE_DESC FROM com_state_m D WHERE  D.STATE_ID=C.STATE_ID ) AS STATE_DESC,");
			bufInsSql.append("DISTRICT_ID,(SELECT E.DISTRICT_DESC FROM com_district_m E WHERE  E.DISTRICT_ID=C.DISTRICT_ID ) AS DISTRICT_DESC");
			bufInsSql.append(" FROM COM_BRANCH_M C ");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM COM_BRANCH_M C ");
			
			if ((!(branchId.equalsIgnoreCase(""))) && (!(branchDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE C.BRANCH_DESC like '%" + branchDesc + "%' AND C.BRANCH_ID='" + branchId + "'");
				bufInsSqlTempCount.append("WHERE C.BRANCH_DESC like '%" + branchDesc + "%' AND C.BRANCH_ID='" + branchId + "'");
			}

			else if (!branchId.equals("")) {
				bufInsSql.append(" WHERE C.BRANCH_ID = '" + branchId + "' ");
				bufInsSqlTempCount.append(" WHERE C.BRANCH_ID = '" + branchId + "' ");
			} 
			else if (!branchDesc.equals("")) {
				bufInsSql.append(" WHERE C.BRANCH_DESC LIKE '%" + branchDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE C.BRANCH_DESC LIKE '%" + branchDesc + "%' ");
			}
			
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((branchId.trim()==null && branchDesc.trim()==null) || (branchId.trim().equalsIgnoreCase("") && branchDesc.trim().equalsIgnoreCase("")) || branchMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+branchMasterVo.getCurrentPageLink());
			if(branchMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (branchMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY BRANCH_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
						
								
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchBranchData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchBranchData list size-------->" + searchlist.size());
			
			for (int i = 0; i < searchlist.size(); i++) {	
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {

					BranchMasterVo branchMVo = new BranchMasterVo();
					branchMVo.setBranchIdModify("<a href=branchMasterSearch.do?method=openEditBranch&branchId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					branchMVo.setBranchId(CommonFunction.checkNull(data.get(0)).toString());
					branchMVo.setBranchDesc(CommonFunction.checkNull(data.get(1)).toString());
					branchMVo.setBranchAccount(CommonFunction.checkNull(data.get(2)).toString());
					branchMVo.setCompanyId(CommonFunction.checkNull(data.get(3)).toString());
					branchMVo.setLbxCompanyID(CommonFunction.checkNull(data.get(4)).toString());
					branchMVo.setRegionId(CommonFunction.checkNull(data.get(5)).toString());
					branchMVo.setLbxRegionID(CommonFunction.checkNull(data.get(6)).toString());
					branchMVo.setBranchStatus(CommonFunction.checkNull(data.get(7)).toString());
					branchMVo.setBranchShortCode(CommonFunction.checkNull(data.get(8)).toString());
					
					branchMVo.setTxtCountryCode(CommonFunction.checkNull(data.get(9)).toString());
					branchMVo.setCountry(CommonFunction.checkNull(data.get(10)).toString());
					branchMVo.setTxtStateCode(CommonFunction.checkNull(data.get(11)).toString());
					branchMVo.setState(CommonFunction.checkNull(data.get(12)).toString());
					branchMVo.setTxtDistCode(CommonFunction.checkNull(data.get(13)).toString());
					branchMVo.setDistrict(CommonFunction.checkNull(data.get(14)).toString());
					
					detailList.add(branchMVo);
					branchMVo.setTotalRecordSize(count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			BranchMasterVo branchMVo = new BranchMasterVo();
//			branchMVo.setTotalRecordSize(count);
//			detailList.add(branchMVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean updateBranchData(Object ob) {
		BranchMasterVo branchMastervo = (BranchMasterVo) ob;
		String branchId = branchMastervo.getBranchId();
		String branchShortCode = branchMastervo.getBranchShortCode();
		
		logger.info("vo.getBranchStatus():-"	+ branchMastervo.getBranchStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		String selquery="SELECT BRANCH_ID FROM COM_BRANCH_M WHERE BRANCH_ID<>'"+branchId+"' AND BRANCH_SHORT_CODE='"+branchShortCode+"'";
		 logger.info("In updateBranchMaster...........inside ejb server file.............Dao Impl"+selquery);
		boolean st = ConnectionDAOforEJB.checkStatus(selquery);
		boolean status = false;
		String stat = "";
		try {
			if (!st) 
			{
				if (branchMastervo.getBranchStatus() != null && branchMastervo.getBranchStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateBranchData............inside ejb server file..............Dao Impl");
			bufInsSql.append("UPDATE COM_BRANCH_M SET BRANCH_SHORT_CODE=?,BRANCH_DESC=?,BRANCH_ACCOUNT=?,");
			bufInsSql.append("COMPANY_ID=?,REGION_ID=?,");
			bufInsSql.append("REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("COUNTRY_ID=?,STATE_ID=?,DISTRICT_ID=? where BRANCH_ID=?");

			if (CommonFunction.checkNull(branchMastervo.getBranchShortCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addString(branchMastervo.getBranchShortCode().trim());
			
			if (CommonFunction.checkNull(branchMastervo.getBranchDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addString(branchMastervo.getBranchDesc().trim());

			if (CommonFunction.checkNull(branchMastervo.getBranchAccount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addString(branchMastervo.getBranchAccount().trim());

			if (CommonFunction.checkNull(branchMastervo.getLbxCompanyID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(branchMastervo.getLbxCompanyID());

			if (CommonFunction.checkNull(branchMastervo.getLbxRegionID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(branchMastervo.getLbxRegionID());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------
			if (CommonFunction.checkNull(branchMastervo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(branchMastervo.getMakerId());
			if (CommonFunction.checkNull(branchMastervo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(branchMastervo.getMakerDate());
			
			if (CommonFunction.checkNull(branchMastervo.getTxtCountryCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addString(branchMastervo.getTxtCountryCode().trim());
			
			if (CommonFunction.checkNull(branchMastervo.getTxtStateCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addString(branchMastervo.getTxtStateCode().trim());
			
			if (CommonFunction.checkNull(branchMastervo.getTxtDistCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addInt(0);
			else
				insertPrepStmtObject.addString(branchMastervo.getTxtDistCode().trim());
			//----------------------------------

			if (CommonFunction.checkNull(branchId).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(branchId);

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	
	//Ritu
	public ArrayList defaultCountry() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In defaultCountry()...........inside ejb server file..........DAOImpl");
			StringBuilder query=new StringBuilder();
			
			 query.append("SELECT PARAMETER_VALUE,PARAMETER_DESC FROM parameter_mst WHERE PARAMETER_KEY='DEFAULT_COUNTRY'");
			logger.info("In defaultCountry...............query...........DAOImpl"+ query);
			BranchMasterVo vo = null;
			ArrayList country = ConnectionDAOforEJB.sqlSelect(query.toString());
			
			query=null;
			
			logger.info("defaultCountry() " + country.size());
			for (int i = 0; i < country.size(); i++) {
				logger.info("defaultCountry()...Outer FOR loop "+ CommonFunction.checkNull(country.get(i)).toString());
				ArrayList data = (ArrayList) country.get(i);
				if (data.size() > 0) {
					vo = new BranchMasterVo();
					vo.setDefaultcountryid((CommonFunction.checkNull(data.get(0))).trim());
					vo.setDefaultcountryname((CommonFunction.checkNull(data.get(1))).trim());
					list.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}


	// Code End By Ritu For Branch Master

	// Code BY Ritu for Industry Master

	public String insertIndustryMaster(Object ob) {
		IndustryMasterVo industryMasterVo = (IndustryMasterVo) ob;
		String result="";
		boolean status = false;
		logger.info("In insertIndustryMaster.........inside ejb server file...........Dao Impl"
				+ industryMasterVo.getIndustryStatus());
		ArrayList qryList = new ArrayList();
		StringBuffer query = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		try {
		query.append("SELECT INDUSTRY_DESC FROM COM_INDUSTRY_M where  INDUSTRY_DESC='"+ StringEscapeUtils.escapeSql(industryMasterVo.getIndustryDesc().trim()) + "'");
		logger.info("In insertIndustryMaster............inside ejb server file...........Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query.toString());
		logger.info("st -----" + st);
		if(st){
			result="EX";
		}
		if (!st) {
				if (industryMasterVo.getIndustryStatus() != null
						&& industryMasterVo.getIndustryStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert Industry master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO COM_INDUSTRY_M(INDUSTRY_DESC,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				if (CommonFunction
						.checkNull(industryMasterVo.getIndustryDesc())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(industryMasterVo
							.getIndustryDesc().toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(industryMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(industryMasterVo
							.getMakerId());

				if (CommonFunction.checkNull(industryMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(industryMasterVo
							.getMakerDate());

				if (CommonFunction.checkNull(industryMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(industryMasterVo
							.getMakerId());

				if (CommonFunction.checkNull(industryMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(industryMasterVo
							.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertIndustryMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveIndustryMasterData..........inside ejb server file...........Dao Impl"+ status);
				if(status){
					result="S";
				}
				else {
					result = "E";
				}
			} 
		} catch (Exception e) {
			result = "E";
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList searchIndustryData(Object ob) {
		String industryDesc = "";
		String industryId = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		IndustryMasterVo industryMasterVo = (IndustryMasterVo) ob;
		ArrayList<IndustryMasterVo> detailList = new ArrayList<IndustryMasterVo>();
		try {

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			industryId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(industryMasterVo.getIndustryId())).trim());
			industryDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(industryMasterVo.getIndustrySearchDesc())).trim());
			
			logger.info("In searchIndustryData()..........inside ejb server file...........Dao Impl");
			
			bufInsSql.append("SELECT INDUSTRY_ID,INDUSTRY_DESC,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status");
			bufInsSql.append(" FROM COM_INDUSTRY_M ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM COM_INDUSTRY_M ");
			
			if ((!(industryId.equalsIgnoreCase(""))) && (!(industryDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE INDUSTRY_DESC like '%" + industryDesc + "%' AND INDUSTRY_ID='" + industryId + "'");
				bufInsSqlTempCount.append("WHERE INDUSTRY_DESC like '%" + industryDesc + "%' AND INDUSTRY_ID='" + industryId + "'");
			}

			else if (!industryDesc.equals("")) {
				bufInsSql.append(" WHERE INDUSTRY_DESC LIKE '%" + industryDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE INDUSTRY_DESC LIKE '%" + industryDesc + "%' ");
			}

			else if (!industryId.equals("")) {
				bufInsSql.append(" WHERE INDUSTRY_ID = '" + industryId + "' ");
				bufInsSqlTempCount.append(" WHERE INDUSTRY_ID = '" + industryId + "' ");
			}
									
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((industryId.trim()==null && industryDesc.trim()==null) || (industryId.trim().equalsIgnoreCase("") && industryDesc.trim().equalsIgnoreCase("")) || industryMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+industryMasterVo.getCurrentPageLink());
			if(industryMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (industryMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY INDUSTRY_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
									
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("searchIndustryData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {

					IndustryMasterVo industryMVo = new IndustryMasterVo();

					industryMVo.setIndustryIdModify("<a href=industryMasterSearch.do?method=openEditIndustry&industryId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					industryMVo.setIndustryId(CommonFunction.checkNull(data.get(0)).toString());
					industryMVo.setIndustryDesc(CommonFunction.checkNull(data.get(1)).toString());
					industryMVo.setIndustryStatus(CommonFunction.checkNull(data.get(2)).toString());
					industryMVo.setTotalRecordSize(count);
					detailList.add(industryMVo);
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			IndustryMasterVo industryMVo = new IndustryMasterVo();
//			industryMVo.setTotalRecordSize(count);
//			detailList.add(industryMVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}

		return detailList;
	}

	public boolean updateIndustryData(Object ob) {
		IndustryMasterVo industryMasterVo = (IndustryMasterVo) ob;
		String industryDesc = (String) industryMasterVo.getIndustryDesc();
		logger.info("vo.getIndustryStatus():-"
				+ industryMasterVo.getIndustryStatus());
		ArrayList updatelist = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		boolean status = false;
		String stat = "";
		try {

			if (industryMasterVo.getIndustryStatus() != null
					&& industryMasterVo.getIndustryStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateIndustryData.............inside ejb server file...........Dao Impl");
			bufInsSql.append("UPDATE COM_INDUSTRY_M SET INDUSTRY_DESC=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where INDUSTRY_ID=?");
			if (CommonFunction.checkNull(industryMasterVo.getIndustryDesc())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(industryMasterVo
						.getIndustryDesc().toUpperCase().trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------
			if (CommonFunction.checkNull(industryMasterVo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(industryMasterVo.getMakerId());
			if (CommonFunction.checkNull(industryMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(industryMasterVo.getMakerDate());
			//----------------------------------

			if (CommonFunction.checkNull(industryMasterVo.getIndustryId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(industryMasterVo.getIndustryId()
						.toUpperCase().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

	// Code End BY Ritu for IndustryMaster

	// Code BY Ritu for SubIndustryMaster
	public String insertSubIndustryMaster(Object ob) {
		SubIndustryMasterVo subIndustryMasterVo = (SubIndustryMasterVo) ob;
		boolean status = false;
		StringBuffer query = new StringBuffer();
		String result="";
		logger.info("In insertSubIndustryMaster.........inside ejb server file...........Dao Impl"
				+ subIndustryMasterVo.getSubIndustryStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		try {
		query.append("SELECT SUB_INDUSTRY_DESC FROM COM_SUB_INDUSTRY_M where  SUB_INDUSTRY_DESC='"+ StringEscapeUtils.escapeSql(subIndustryMasterVo.getSubIndustryDesc().trim()) + "'");
		logger.info("In insertSubIndustryMaster..........inside ejb server file...........Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query.toString());
		logger.info("st -----" + st);
		if(st){
			result="EX";
		}

			if (!st) {
				if (subIndustryMasterVo.getSubIndustryStatus() != null
						&& subIndustryMasterVo.getSubIndustryStatus().equals(
								"on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert Sub Industry master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO COM_SUB_INDUSTRY_M(SUB_INDUSTRY_DESC,INDUSTRY_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				if (CommonFunction.checkNull(
						subIndustryMasterVo.getSubIndustryDesc())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(subIndustryMasterVo
							.getSubIndustryDesc().toUpperCase().trim());

				if (CommonFunction.checkNull(
						subIndustryMasterVo.getLbxIndustry()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(subIndustryMasterVo
							.getLbxIndustry());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(subIndustryMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(subIndustryMasterVo
							.getMakerId());

				if (CommonFunction
						.checkNull(subIndustryMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(subIndustryMasterVo
							.getMakerDate());

				if (CommonFunction.checkNull(subIndustryMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(subIndustryMasterVo
							.getMakerId());

				if (CommonFunction
						.checkNull(subIndustryMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(subIndustryMasterVo
							.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertSubIndustryMaster() insert query1 ### "
								+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveSubIndustryMasterData............inside ejb server file...........Dao Impl"+ status);
				if(status){
					result="S";
				}
				else{
					result="E";
				}
			} 
		} catch (Exception e) {
			result="E";
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList searchSubIndustryData(Object ob) {
		String subIndustryDesc = "";
		String subIndustryId = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		SubIndustryMasterVo subIndustryMasterVo = (SubIndustryMasterVo) ob;
		ArrayList<SubIndustryMasterVo> detailList = new ArrayList<SubIndustryMasterVo>();
		try {
			subIndustryId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(subIndustryMasterVo.getSubIndustryId())).trim());
			subIndustryDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(subIndustryMasterVo.getSubIndustrySearchDesc())).trim());
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			logger.info("In searchSubIndustryData().............inside ejb server file...........Dao Impl");
			
			bufInsSql.append("SELECT SUB_INDUSTRY_ID,SUB_INDUSTRY_DESC,");
			bufInsSql.append(" INDUSTRY_ID,(SELECT B.INDUSTRY_DESC FROM COM_INDUSTRY_M B WHERE B.INDUSTRY_ID=A.INDUSTRY_ID ) AS INDUSTRY_DESC,");
			bufInsSql.append(" case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status");
			bufInsSql.append(" FROM COM_SUB_INDUSTRY_M A ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM COM_SUB_INDUSTRY_M A ");
			
			if ((!(subIndustryId.equalsIgnoreCase(""))) && (!(subIndustryDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE SUB_INDUSTRY_DESC like '%" + subIndustryDesc + "%' AND SUB_INDUSTRY_ID='"+ subIndustryId + "'");
				bufInsSqlTempCount.append("WHERE SUB_INDUSTRY_DESC like '%" + subIndustryDesc + "%' AND SUB_INDUSTRY_ID='"+ subIndustryId + "'");
			}

			else if (!subIndustryDesc.equals("")) {
				bufInsSql.append(" WHERE A.SUB_INDUSTRY_DESC LIKE '%"+ subIndustryDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE A.SUB_INDUSTRY_DESC LIKE '%"+ subIndustryDesc + "%' ");
			}

			else if (!subIndustryId.equals("")) {
				bufInsSql.append(" WHERE A.SUB_INDUSTRY_ID = '" + subIndustryId+ "' ");
				bufInsSqlTempCount.append(" WHERE A.SUB_INDUSTRY_ID = '" + subIndustryId+ "' ");
			}
					
			logger.info("search query........" + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((subIndustryDesc.trim()==null && subIndustryId.trim()==null) || (subIndustryDesc.trim().equalsIgnoreCase("") && subIndustryId.trim().equalsIgnoreCase("")) || subIndustryMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+subIndustryMasterVo.getCurrentPageLink());
			if(subIndustryMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (subIndustryMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY SUB_INDUSTRY_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
							
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("searchSubIndustryData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					SubIndustryMasterVo subIndustryMVo = new SubIndustryMasterVo();

					subIndustryMVo.setSubIndustryIdModify("<a href=subIndustryMasterSearch.do?method=openEditSubIndustry&subIndustryId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					subIndustryMVo.setSubIndustryId(CommonFunction.checkNull(data.get(0)).toString());
					subIndustryMVo.setSubIndustryDesc(CommonFunction.checkNull(data.get(1)).toString());
					subIndustryMVo.setIndustryId(CommonFunction.checkNull(data.get(2)).toString());
					subIndustryMVo.setLbxIndustry(CommonFunction.checkNull(data.get(3)).toString());
					subIndustryMVo.setSubIndustryStatus(CommonFunction.checkNull(data.get(4)).toString());
					subIndustryMVo.setTotalRecordSize(count);
					detailList.add(subIndustryMVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			SubIndustryMasterVo subIndustryMVo = new SubIndustryMasterVo();
//			subIndustryMVo.setTotalRecordSize(count);
//			detailList.add(subIndustryMVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean updateSubIndustryData(Object ob) {
		SubIndustryMasterVo subIndustryMasterVo = (SubIndustryMasterVo) ob;
		String subIndustryId = (String) subIndustryMasterVo.getSubIndustryId();
		logger.info("vo.getSubIndustryId..:-" + subIndustryId);
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		// String
		// selquery="SELECT sub_industry_id FROM COM_SUB_INDUSTRY_M WHERE sub_industry_id='"+subIndustryMasterVo.getSubIndustryId()+"'";
		// logger.info("In updatESubIndustryMaster.....................................Dao Impl"+selquery);
		// boolean st=ConnectionDAOforEJB.checkStatus(selquery);
		boolean status = false;
		String stat = "";
		try {

			if (subIndustryMasterVo.getSubIndustryStatus() != null
					&& subIndustryMasterVo.getSubIndustryStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateSubIndustryData............inside ejb server file...........Dao Impl");
			bufInsSql.append("UPDATE COM_SUB_INDUSTRY_M SET SUB_INDUSTRY_DESC=?,INDUSTRY_ID=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where sub_industry_id=?");
			logger.info("In getListOfValues........." + bufInsSql.toString());

			if (CommonFunction.checkNull(
					subIndustryMasterVo.getSubIndustryDesc()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(subIndustryMasterVo
						.getSubIndustryDesc().toUpperCase().trim());

			if (CommonFunction.checkNull(subIndustryMasterVo.getLbxIndustry())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(subIndustryMasterVo
						.getLbxIndustry());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------
			if (CommonFunction.checkNull(subIndustryMasterVo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(subIndustryMasterVo.getMakerId());
			if (CommonFunction.checkNull(subIndustryMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(subIndustryMasterVo.getMakerDate());
			//----------------------------------

			if (CommonFunction
					.checkNull(subIndustryMasterVo.getSubIndustryId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(subIndustryMasterVo
						.getSubIndustryId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues........." + bufInsSql);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

	// Code End BY Ritu for SubIndustryMaster


	// Code By Ritu For Document Master
	public boolean insertDocumentMaster(Object ob) {
		DocumentMasterVo documentMastervo = (DocumentMasterVo) ob;
		boolean status = false;
		logger.info("In insertDocumentMaster.........."
				+ documentMastervo.getDocumentStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "SELECT DOC_DESC FROM CR_DOCUMENT_M where  DOC_DESC='"
				+ StringEscapeUtils.escapeSql(documentMastervo
						.getDocumentDesc().trim()) + "'";
		logger
				.info("In insertDocumentMaster.....................................Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (documentMastervo.getDocumentStatus() != null
						&& documentMastervo.getDocumentStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert document master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("INSERT INTO CR_DOCUMENT_M(DOC_DESC,REC_STATUS,DOC_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
								
				if (CommonFunction
						.checkNull(documentMastervo.getDocumentDesc())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(documentMastervo
							.getDocumentDesc().toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction
						.checkNull(documentMastervo.getDocumentDesc())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(documentMastervo
							.getDocumentDesc().toUpperCase().trim());

				if (CommonFunction.checkNull(documentMastervo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(documentMastervo
							.getMakerId());

				if (CommonFunction.checkNull(documentMastervo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(documentMastervo
							.getMakerDate());

				if (CommonFunction.checkNull(documentMastervo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(documentMastervo
							.getMakerId());

				if (CommonFunction.checkNull(documentMastervo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(documentMastervo
							.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertDocumentMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertDocumentMaster......................"
						+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public ArrayList<DocumentMasterVo> getDocumentData() {
		ArrayList list = new ArrayList();
		ArrayList<DocumentMasterVo> detailList = new ArrayList<DocumentMasterVo>();

		try {
			logger.info("In getDocumentData()..............Dao Impl");
			String query = ("SELECT DOC_ID,DOC_DESC,"
					+ " case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status FROM CR_DOCUMENT_M ORDER BY DOC_ID");
			list = ConnectionDAOforEJB.sqlSelect(query);
			logger.info("getDocumentData " + list.size());

			for (int i = 0; i < list.size(); i++) {
				logger.info("getDocumentDataList " + list.get(i).toString());

				ArrayList data = (ArrayList) list.get(i);

				if (data.size() > 0) {
					DocumentMasterVo documentMasterVo = new DocumentMasterVo();

					documentMasterVo
							.setDocumentIdModify("<a href=documentMasterSearch.do?method=openEditDocument&documentId="
									+ CommonFunction.checkNull(data.get(0))
											.toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0))
											.toString() + "</a>");

					documentMasterVo.setDocumentId(CommonFunction.checkNull(
							data.get(0)).toString());
					documentMasterVo.setDocumentDesc(CommonFunction.checkNull(
							data.get(1)).toString());
					documentMasterVo.setDocumentStatus(CommonFunction
							.checkNull(data.get(2)).toString());
					detailList.add(documentMasterVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}

	public ArrayList<DocumentMasterVo> searchDocumentData(Object ob) {
		String documentId = "";
		String documentDesc = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		DocumentMasterVo documentMasterVo = (DocumentMasterVo) ob;
		ArrayList<DocumentMasterVo> detailList = new ArrayList<DocumentMasterVo>();
		try {
			logger.info("In searchDocumentData().....................................Dao Impl");
			documentId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(documentMasterVo.getDocumentId())).trim());
			documentDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(documentMasterVo.getDocumentSearchDesc())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT DOC_ID,DOC_DESC,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status");
			bufInsSql.append(" FROM CR_DOCUMENT_M ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM CR_DOCUMENT_M ");
			
			if ((!(documentId.equalsIgnoreCase(""))) && (!(documentDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE DOC_DESC like '%" + documentDesc + "%' AND DOC_ID='" + documentId + "'");
				bufInsSqlTempCount.append("WHERE DOC_DESC like '%" + documentDesc + "%' AND DOC_ID='" + documentId + "'");
			}

			else if (!documentDesc.equals("")) {
				bufInsSql.append(" WHERE DOC_DESC LIKE '%" + documentDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE DOC_DESC LIKE '%" + documentDesc + "%' ");
			}

			else if (!documentId.equals("")) {
				bufInsSql.append(" WHERE DOC_ID = '" + documentId + "' ");
				bufInsSqlTempCount.append(" WHERE DOC_ID = '" + documentId + "' ");
			}
					
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((documentId.trim()==null && documentDesc.trim()==null) || (documentId.trim().equalsIgnoreCase("") && documentDesc.trim().equalsIgnoreCase("")) || documentMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+documentMasterVo.getCurrentPageLink());
			if(documentMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (documentMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY DOC_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchDocumentData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchDocumentData " + searchlist.size());
			
			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					DocumentMasterVo documentMVo = new DocumentMasterVo();

					documentMVo.setDocumentIdModify("<a href=documentMasterSearch.do?method=openEditDocument&documentId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					documentMVo.setDocumentId(CommonFunction.checkNull(data.get(0)).toString());
					documentMVo.setDocumentDesc(CommonFunction.checkNull(data.get(1)).toString());
					documentMVo.setDocumentStatus(CommonFunction.checkNull(data.get(2)).toString());
					documentMVo.setTotalRecordSize(count);
					detailList.add(documentMVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			DocumentMasterVo documentMVo = new DocumentMasterVo();
//			documentMVo.setTotalRecordSize(count);
//			detailList.add(documentMVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}


		return detailList;
	}

	public boolean updateDocumentData(Object ob) {
		DocumentMasterVo documentMasterVo = (DocumentMasterVo) ob;
		String documentDesc = documentMasterVo.getDocumentDesc();
		logger.info("vo.getDocumentStatus():-"
				+ documentMasterVo.getDocumentStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();

		boolean status = false;
		String stat = "";
		try {

			if (documentMasterVo.getDocumentStatus() != null
					&& documentMasterVo.getDocumentStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateDocumentData.....................................Dao Impl");
			bufInsSql.append("UPDATE CR_DOCUMENT_M SET DOC_DESC_L=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where DOC_ID=?");

			if (CommonFunction.checkNull(documentMasterVo.getDocumentDesc())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(documentMasterVo
						.getDocumentDesc().toUpperCase().trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------
			if (CommonFunction.checkNull(documentMasterVo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(documentMasterVo.getMakerId());
			if (CommonFunction.checkNull(documentMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(documentMasterVo.getMakerDate());
			//----------------------------------

			if (CommonFunction.checkNull(documentMasterVo.getDocumentId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(documentMasterVo.getDocumentId()
						.toUpperCase().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// Code End By Ritu For Document Master

	// Code Start By Ritu For Base Rate Master
	public boolean insertBaseRateMaster(Object ob) {
		BaseRateMasterVo baseRateMastervo = (BaseRateMasterVo) ob;
		boolean status = false;
		logger.info("In insertBaseRateMaster.........."
				+ baseRateMastervo.getBaseRateStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder query1 = new StringBuilder();
		query1.append("SELECT BASE_RATE_TYPE FROM CR_BASE_RATE_M WHERE BASE_RATE_TYPE='"+ StringEscapeUtils.escapeSql(baseRateMastervo.getBaseRateType().trim())+ "' AND EFFECTIVE_FROM = ");
		query1.append(dbo);
		query1.append("STR_TO_DATE('"+ baseRateMastervo.getEffectiveFromDate().trim()+ "','"	+ dateFormat + "') ");
		logger.info("In insertBaseRateMaster.....................................Dao Impl"+ query1.toString());
		boolean st1 = ConnectionDAOforEJB.checkStatus(query1.toString());

		try {

			if (!st1) {
				if (baseRateMastervo.getBaseRateStatus() != null
						&& baseRateMastervo.getBaseRateStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert Base Rate master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO CR_BASE_RATE_M(BASE_RATE_TYPE,EFFECTIVE_FROM,BASE_RATE,BASE_RATE_DESC,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'" + dateFormat + "'),");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							
				if (CommonFunction.checkNull(baseRateMastervo.getBaseRateType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(baseRateMastervo
							.getBaseRateType().toUpperCase().trim());

				if (CommonFunction.checkNull(baseRateMastervo.getEffectiveFromDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(baseRateMastervo
							.getEffectiveFromDate().trim());

				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(baseRateMastervo.getBaseRate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
							escapeSql(baseRateMastervo.getBaseRate()).trim()).toString());
				
				if (CommonFunction.checkNull(baseRateMastervo.getBaseRateDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(baseRateMastervo
							.getBaseRateDesc().toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(baseRateMastervo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(baseRateMastervo
							.getMakerId());

				if (CommonFunction.checkNull(baseRateMastervo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(baseRateMastervo
							.getMakerDate());

				if (CommonFunction.checkNull(baseRateMastervo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(baseRateMastervo
							.getMakerId());

				if (CommonFunction.checkNull(baseRateMastervo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(baseRateMastervo
							.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertBaseRateMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertBaseRateMaster......................"+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public ArrayList<BaseRateMasterVo> getBaseRateData() {
		ArrayList list = new ArrayList();
		ArrayList<BaseRateMasterVo> detailList = new ArrayList<BaseRateMasterVo>();

		try {
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In getBaseRateData()..............Dao Impl");
			bufInsSql.append("SELECT BASE_RATE_TYPE,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(EFFECTIVE_FROM,'"+ dateFormat +"'),BASE_RATE,BASE_RATE_DESC,");
			bufInsSql.append(" case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status FROM CR_BASE_RATE_M ORDER BY BASE_RATE_TYPE");
			list = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("getBaseRateData " + list.size());

			for (int i = 0; i < list.size(); i++) {
				logger.info("getBaseRateDataList " + list.get(i).toString());

				ArrayList data = (ArrayList) list.get(i);

				if (data.size() > 0) {
					BaseRateMasterVo baseRateMasterVo = new BaseRateMasterVo();

					baseRateMasterVo
							.setBaseRateTypeModify("<a href=baseRateMasterSearch.do?method=openEditBaseRate&baseRateTypeSearch="
									+ CommonFunction.checkNull(data.get(0))
											.toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0))
											.toString() + "</a>");

					baseRateMasterVo.setBaseRateType(CommonFunction.checkNull(
							data.get(0)).toString());
					baseRateMasterVo.setEffectiveFromDate(CommonFunction
							.checkNull(data.get(1)).toString());
					baseRateMasterVo.setBaseRate(CommonFunction.checkNull(
							data.get(2)).toString());

					baseRateMasterVo.setBaseRateDesc(CommonFunction.checkNull(
							data.get(3)).toString());
					baseRateMasterVo.setBaseRateStatus(CommonFunction
							.checkNull(data.get(4)).toString());
					detailList.add(baseRateMasterVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}

	public ArrayList<BaseRateMasterVo> searchBaseRateData(Object ob) {
		
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String baseRateType = "";
		String effectiveFrom = "";
		String baseRateDesc = "";
		ArrayList searchlist = new ArrayList();
		BaseRateMasterVo baseRateMasterVo = (BaseRateMasterVo) ob;
		ArrayList<BaseRateMasterVo> detailList = new ArrayList<BaseRateMasterVo>();
		try {
			logger.info("In searchBaseRateData().....................................Dao Impl");
			logger.info("Value: " + baseRateMasterVo.getBaseRateTypeSearch());
			
			baseRateType = ((CommonFunction.checkNull(baseRateMasterVo.getBaseRateTypeSearch())).trim());
			effectiveFrom = ((CommonFunction.checkNull(baseRateMasterVo.getEffectiveFromDateSearch())).trim());
			baseRateDesc = ((CommonFunction.checkNull(baseRateMasterVo.getBaseRateDescSearch())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT brm.BASE_RATE_TYPE,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(brm.EFFECTIVE_FROM,'"	+ dateFormat + "') as EFFECTIVE_FROM,brm.BASE_RATE,brm.BASE_RATE_DESC,");
			bufInsSql.append("case when brm.REC_STATUS='A' then 'Active' else 'Inactive' end as Status,gm.DESCRIPTION ");
			bufInsSql.append(" FROM CR_BASE_RATE_M brm ");
			bufInsSql.append("left outer join generic_master gm on gm.VALUE = brm.BASE_RATE_TYPE and  GENERIC_KEY='BASE_RATE_TYPE' and gm.REC_STATUS='A' ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM CR_BASE_RATE_M ");

			if ((!(baseRateType.equalsIgnoreCase(""))) && (!(effectiveFrom.equalsIgnoreCase(""))) && (!(baseRateDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE BASE_RATE_TYPE='" + baseRateType + "' AND EFFECTIVE_FROM= ");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('" + effectiveFrom	+ "','" + dateFormat + "') AND BASE_RATE_DESC like '%"+ baseRateDesc + "%'");
				
				bufInsSqlTempCount.append("WHERE BASE_RATE_TYPE='" + baseRateType + "' AND EFFECTIVE_FROM=" );
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("STR_TO_DATE('" + effectiveFrom	+ "','" + dateFormat + "') AND BASE_RATE_DESC like '%"+ baseRateDesc + "%'");
			}

			else if ((!(effectiveFrom.equalsIgnoreCase(""))) && (!(baseRateDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE EFFECTIVE_FROM=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('"+ effectiveFrom + "','" + dateFormat + "')  AND BASE_RATE_DESC like '%" + baseRateDesc + "%'");
				bufInsSqlTempCount.append("WHERE EFFECTIVE_FROM=");
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("STR_TO_DATE('"+ effectiveFrom + "','" + dateFormat + "')  AND BASE_RATE_DESC like '%" + baseRateDesc + "%'");
			}

			else if ((!(baseRateType.equalsIgnoreCase(""))) && (!(effectiveFrom.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE BASE_RATE_TYPE='" + baseRateType + "' AND EFFECTIVE_FROM=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('" + effectiveFrom	+ "','" + dateFormat + "') ");
				
				bufInsSqlTempCount.append("WHERE BASE_RATE_TYPE='" + baseRateType + "' AND EFFECTIVE_FROM=");
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("STR_TO_DATE('" + effectiveFrom+ "','" + dateFormat + "') ");
			}

			else if ((!(baseRateType.equalsIgnoreCase(""))) && (!(baseRateDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE BASE_RATE_TYPE='" + baseRateType + "' AND BASE_RATE_DESC like '%" + baseRateDesc + "%'");
				bufInsSqlTempCount.append("WHERE BASE_RATE_TYPE='" + baseRateType + "' AND BASE_RATE_DESC like '%" + baseRateDesc + "%'");
			}

			else if (!baseRateType.equals("")) {
				bufInsSql.append(" WHERE BASE_RATE_TYPE='" + baseRateType+ "' ");
				bufInsSqlTempCount.append(" WHERE BASE_RATE_TYPE='" + baseRateType+ "' ");
			}

			else if (!effectiveFrom.equals("")) {
				bufInsSql.append(" WHERE EFFECTIVE_FROM=" );
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('"+ effectiveFrom + "','" + dateFormat + "')  ");
				bufInsSqlTempCount.append(" WHERE EFFECTIVE_FROM=");
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("STR_TO_DATE('"+ effectiveFrom + "','" + dateFormat + "')  ");
			}

			else if (!baseRateDesc.equals("")) {
				bufInsSql.append(" WHERE BASE_RATE_DESC like '%" + baseRateDesc+ "%' ");
				bufInsSqlTempCount.append(" WHERE BASE_RATE_DESC like '%" + baseRateDesc+ "%' ");
			}
					
			logger.info("search Query...." + bufInsSql);

			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((baseRateType.trim()==null && effectiveFrom.trim()==null && baseRateDesc.trim()==null) || (baseRateType.trim().equalsIgnoreCase("") && effectiveFrom.trim().equalsIgnoreCase("") && baseRateDesc.trim().equalsIgnoreCase("")) || baseRateMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+baseRateMasterVo.getCurrentPageLink());
			if(baseRateMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (baseRateMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY BASE_RATE_TYPE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
									
//			}
			logger.info("query : "+bufInsSql);
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchBaseRateData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchBaseRateData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					BaseRateMasterVo baseRateMVo = new BaseRateMasterVo();

					baseRateMVo
							.setBaseRateTypeModify("<a href=baseRateMasterSearch.do?method=openEditBaseRate&baseRateTypeSearch="
									+ (CommonFunction.checkNull(data.get(0)).toString())
									+ "&effectiveFromDateSearch="+(CommonFunction.checkNull(data.get(1)).toString())+ ">"
									+ (CommonFunction.checkNull(data.get(5)).toString()) + "</a>");

					baseRateMVo.setBaseRateType((CommonFunction.checkNull(data.get(0)).toString()));
					baseRateMVo.setEffectiveFromDate((CommonFunction.checkNull(data.get(1)).toString()));

                if(!CommonFunction.checkNull(data.get(2)).equalsIgnoreCase(""))
                {
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(2))).trim());
					baseRateMVo.setBaseRate(myFormatter.format(reconNum));
                }
					baseRateMVo.setBaseRateDesc((CommonFunction.checkNull(data.get(3)).toString()));
					baseRateMVo.setBaseRateStatus((CommonFunction.checkNull(data.get(4)).toString()));
					baseRateMVo.setBaseRateTypeSearch((CommonFunction.checkNull(data.get(5)).toString()));
					detailList.add(baseRateMVo);
					baseRateMVo.setTotalRecordSize(count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		return detailList;
	}

	public boolean updateBaseRateData(Object ob) {
		BaseRateMasterVo baseRateMasterVo = (BaseRateMasterVo) ob;
		String baseRateType = (String) baseRateMasterVo.getBaseRateType();
		logger.info("vo.getBaseRateStatus():-"
				+ baseRateMasterVo.getBaseRateStatus());
		
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		// String
		// selquery="SELECT BASE_RATE_TYPE FROM CR_BASE_RATE_M WHERE BASE_RATE_TYPE='"+baseRateType+"'";
//		String selquery = "SELECT BASE_RATE_TYPE FROM CR_BASE_RATE_M WHERE BASE_RATE_TYPE='"
//				+ StringEscapeUtils.escapeSql(baseRateMasterVo
//						.getBaseRateType().trim())
//				+ "' AND EFFECTIVE_FROM >STR_TO_DATE('"
//				+ baseRateMasterVo.getEffectiveFromDate().trim()
//				+ "','"
//				+ dateFormat + "') ";
//		logger
//				.info("In updateBaseRateData.....................................Dao Impl"
//						+ selquery);

		boolean status = false;
		String stat = "";
		try {

			if (baseRateMasterVo.getBaseRateStatus() != null
					&& baseRateMasterVo.getBaseRateStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			StringBuffer bufInsSql = new StringBuffer();
			
			logger.info("In updateBaseRateData.....................................Dao Impl");
			bufInsSql.append("UPDATE CR_BASE_RATE_M SET BASE_RATE_DESC=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" WHERE BASE_RATE_TYPE=? and EFFECTIVE_FROM = ");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'" + dateFormat + "')");

//			if (CommonFunction.checkNull(
//					baseRateMasterVo.getEffectiveFromDate()).equalsIgnoreCase(
//					""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(baseRateMasterVo
//						.getEffectiveFromDate().trim());

			if (CommonFunction.checkNull(baseRateMasterVo.getBaseRateDesc())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(baseRateMasterVo
						.getBaseRateDesc().toUpperCase().trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------
			if (CommonFunction.checkNull(baseRateMasterVo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(baseRateMasterVo.getMakerId());
			if (CommonFunction.checkNull(baseRateMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(baseRateMasterVo.getMakerDate());
			//----------------------------------

			if (CommonFunction.checkNull(baseRateMasterVo.getBaseRateType())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(baseRateMasterVo
						.getBaseRateType().toUpperCase().trim());
			
			if (CommonFunction.checkNull(baseRateMasterVo.getEffectiveFromDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(baseRateMasterVo
						.getEffectiveFromDate().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			logger.info("In getListOfValues"+ insertPrepStmtObject.printQuery());
			
			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + updatelist.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// Code Ends By Ritu For Base Rate Master

	// Code Starts By Ritu For Charge Master

	public boolean insertChargeMaster(Object ob) {
		ChargeMasterVo chargeMasterVo = (ChargeMasterVo) ob;
		boolean status = false;
		
		logger.info("In insertChargeMaster.........."+ chargeMasterVo.getLbxCharge());
		
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String chargeStatus = "X";
		String tdsStatus = "N";
		String taxInclusiveStatus = "N";
		String taxApplicableStatus = "N";
		//Changes By Amit Starts
		String query ="";
		if(!CommonFunction.checkNull(chargeMasterVo.getLbxScheme()).equalsIgnoreCase(""))
		{
			query = "SELECT count(CHARGE_ID) FROM COM_CHARGES_M WHERE product_id='"+StringEscapeUtils.escapeSql(chargeMasterVo.getLbxProductID().trim()) + "' " +
				"and scheme_id='"+StringEscapeUtils.escapeSql(chargeMasterVo.getLbxScheme().trim()) +"' " +
				"and charge_code="+StringEscapeUtils.escapeSql(chargeMasterVo.getLbxCharge().trim()) +"  ";
		}else{
			query = "SELECT count(CHARGE_ID) FROM COM_CHARGES_M WHERE product_id='"+StringEscapeUtils.escapeSql(chargeMasterVo.getLbxProductID().trim()) + "' " +
			"and charge_code="+StringEscapeUtils.escapeSql(chargeMasterVo.getLbxCharge().trim()) +"  ";
		}
		logger.info("In insertChargeMaster.....................................Dao Impl"+ query);
		boolean st = false;
		String countStr = CommonFunction.checkNull(ConnectionDAOforEJB.singleReturn(query));
		int count = 0;
		if(!countStr.equalsIgnoreCase(""))
			count = Integer.parseInt(countStr);
		if(count>0)
			st=true;
		// Changes By Amit Ends
		try {

			if (!st) {
				if (chargeMasterVo.getChargeStatus() != null
						&& chargeMasterVo.getChargeStatus().equals("on")) {
					chargeStatus = "A";
				} else {
					chargeStatus = "X";

				}
				if (chargeMasterVo.getTdsStatus() != null
						&& chargeMasterVo.getTdsStatus().equals("on")) {
					tdsStatus = "Y";
				} else {
					tdsStatus = "N";

				}
				if (chargeMasterVo.getTaxInclusiveStatus() != null
						&& chargeMasterVo.getTaxInclusiveStatus().equals("on")) {
					taxInclusiveStatus = "Y";
				} else {
					taxInclusiveStatus = "N";

				}
				if (chargeMasterVo.getTaxStatus() != null
						&& chargeMasterVo.getTaxStatus().equals("on")) {
					taxApplicableStatus = "Y";
				} else {
					taxApplicableStatus = "N";

				}
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO COM_CHARGES_M(PRODUCT_ID,SCHEME_ID,DEF_STAGE_ID,APP_STAGE_ID,"
								+ "CHARGE_BP_TYPE,CHARGE_CODE,CHARGE_TYPE,CHARGE_METHOD,CHARGE_AMOUNT,CALCULATED_ON,"
								+ "TDS_APPLICABLE,TDS_RATE,TAX_APPLICABLE,TAX_INCLUSIVE,TAX_RATE1,TAX_RATE2,MIN_CHARGE_AMOUNT,REC_STATUS,MIN_CHARGE_METHOD,MIN_CHARGE_CALCULATED,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							
				if (CommonFunction.checkNull(chargeMasterVo.getLbxProductID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo
							.getLbxProductID());

				if (CommonFunction.checkNull(chargeMasterVo.getLbxScheme())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo
							.getLbxScheme());

				if (CommonFunction.checkNull(chargeMasterVo.getLbxStage())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo.getLbxStage()
							.toUpperCase());

				if (CommonFunction.checkNull(chargeMasterVo.getLbxAppStage())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo
							.getLbxAppStage().toUpperCase());

				if (CommonFunction.checkNull(
						chargeMasterVo.gethBuyerSupplierBPType())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo
							.gethBuyerSupplierBPType().toUpperCase());

				if (CommonFunction.checkNull(chargeMasterVo.getLbxCharge())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo
							.getLbxCharge().toUpperCase());

				if (CommonFunction.checkNull(chargeMasterVo.getChargeType())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo
							.getChargeType());

				if (CommonFunction.checkNull(chargeMasterVo.getChargeMethod()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo.getChargeMethod());
				
				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getChargeAmount()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
					escapeSql(chargeMasterVo.getChargeAmount()).trim()).toString());

				if (CommonFunction.checkNull(
						chargeMasterVo.getLbxCalculatedOn()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo
							.getLbxCalculatedOn());

				if (CommonFunction.checkNull(tdsStatus).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(tdsStatus);

				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getTdsRate()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addString("0.0");
				else
						insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
					escapeSql(chargeMasterVo.getTdsRate()).trim()).toString());

				if (CommonFunction.checkNull(taxApplicableStatus)
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(taxApplicableStatus);

				if (CommonFunction.checkNull(taxInclusiveStatus)
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(taxInclusiveStatus);

				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getTaxRate1()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
					escapeSql(chargeMasterVo.getTaxRate1()).trim()).toString());


				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getTaxRate2()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
					escapeSql(chargeMasterVo.getTaxRate2()).trim()).toString());


				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getMinChargeAmount()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
					escapeSql(chargeMasterVo.getMinChargeAmount()).trim()).toString());

				if (CommonFunction.checkNull(chargeStatus).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeStatus);

				if(CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getChargeMethod()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo.getChargeMethod());
				
				if (CommonFunction.checkNull(
						chargeMasterVo.getLbxCalculatedOn()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo.getLbxCalculatedOn());
				
				if (CommonFunction.checkNull(chargeMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo.getMakerId());

				if (CommonFunction.checkNull(chargeMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo
							.getMakerDate());

				if (CommonFunction.checkNull(chargeMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo.getMakerId());

				if (CommonFunction.checkNull(chargeMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(chargeMasterVo
							.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN insertBaseRateMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				
				logger.info("In insertBaseRateMaster......................"+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public ArrayList<ChargeMasterVo> getChargeData() {
		ArrayList list = new ArrayList();
		ArrayList<ChargeMasterVo> detailList = new ArrayList<ChargeMasterVo>();

		try {
			logger.info("In getChargeData()..............Dao Impl");
			String query = ("SELECT CHARGE_ID,(SELECT A.CHARGE_DESC FROM COM_CHARGE_CODE_M A WHERE A.CHARGE_CODE=V.CHARGE_CODE ) AS CHARGE_DESC,"
					+ "PRODUCT_ID,(SELECT B.PRODUCT_DESC FROM CR_PRODUCT_M B WHERE B.PRODUCT_ID=V.PRODUCT_ID ) AS PRODUCT_DESC,"
					+ "SCHEME_ID,(SELECT C.SCHEME_DESC FROM CR_SCHEME_M C WHERE C.SCHEME_ID=V.SCHEME_ID) AS SCHEME_DESC,"
					+ "DEF_STAGE_ID,(SELECT D.STAGE_DESC FROM CR_STAGE_M D WHERE D.STAGE_ID=V.APP_STAGE_ID ) AS DEF_STAGE_DESC,"
					+ "APP_STAGE_ID,(SELECT D.STAGE_DESC FROM CR_STAGE_M D WHERE D.STAGE_ID=V.APP_STAGE_ID ) AS APP_STAGE_ID,"
					+ "CHARGE_BP_TYPE,(SELECT E.DESCRIPTION FROM GENERIC_MASTER E WHERE E.VALUE=V.CHARGE_BP_TYPE  AND GENERIC_KEY='BPTYPE') AS CHARGE_BP_TYPE_DESC,"
					+ "CHARGE_CODE,CHARGE_TYPE,CHARGE_METHOD,CHARGE_AMOUNT,CALCULATED_ON,"
					+ "TDS_APPLICABLE,TDS_RATE,TAX_APPLICABLE,TAX_INCLUSIVE,TAX_RATE1,TAX_RATE2,MIN_CHARGE_AMOUNT,"
					+ " case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,MIN_CHARGE_METHOD,MIN_CHARGE_CALCULATED FROM COM_CHARGES_M V ORDER BY CHARGE_ID");
			list = ConnectionDAOforEJB.sqlSelect(query);
			logger.info("getChargeData " + list.size());

			for (int i = 0; i < list.size(); i++) {
				logger.info("getChargeDataList " + list.get(i).toString());

				ArrayList data = (ArrayList) list.get(i);

				if (data.size() > 0) {
					ChargeMasterVo chargeMVo = new ChargeMasterVo();

					chargeMVo
							.setLbxChargeModify("<a href=chargeMasterSearch.do?method=openEditCharge&chargeSearchId="
									+ CommonFunction.checkNull(data.get(0))
											.toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(1))
											.toString() + "</a>");

					chargeMVo.setLbxCharge(CommonFunction
							.checkNull(data.get(0)).toString());
					chargeMVo.setChargeId(CommonFunction.checkNull(data.get(1))
							.toString());
					chargeMVo.setLbxProductID(CommonFunction.checkNull(
							data.get(2)).toString());
					chargeMVo.setProductId(CommonFunction
							.checkNull(data.get(3)).toString());
					chargeMVo.setLbxScheme(CommonFunction
							.checkNull(data.get(4)).toString());
					chargeMVo.setSchemeId(CommonFunction.checkNull(data.get(5))
							.toString());
					chargeMVo.setLbxStage(CommonFunction.checkNull(data.get(6))
							.toString());
					chargeMVo.setStageId(CommonFunction.checkNull(data.get(7))
							.toString());
					chargeMVo.setLbxAppStage(CommonFunction.checkNull(
							data.get(8)).toString());
					chargeMVo.setAppStageId(CommonFunction.checkNull(
							data.get(9)).toString());
					chargeMVo.sethBuyerSupplierBPType(CommonFunction.checkNull(
							data.get(10)).toString());
					chargeMVo.setChargeBPType(CommonFunction.checkNull(
							data.get(11)).toString());
					chargeMVo.setChargeCode(CommonFunction.checkNull(
							data.get(12)).toString());
					chargeMVo.setChargeType(CommonFunction.checkNull(
							data.get(13)).toString());
					chargeMVo.setChargeMethod(CommonFunction.checkNull(
							data.get(14)).toString());
					chargeMVo.setChargeAmount(CommonFunction.checkNull(
							data.get(15)).toString());
					chargeMVo.setCalculatedOn(CommonFunction.checkNull(
							data.get(16)).toString());
					// chargeMVo.setChargePercentage(CommonFunction.checkNull(data.get(17)).toString());
					chargeMVo.setTdsStatus(CommonFunction.checkNull(
							data.get(17)).toString());
					chargeMVo.setTdsRate(CommonFunction.checkNull(data.get(18))
							.toString());
					chargeMVo.setTaxStatus(CommonFunction.checkNull(
							data.get(19)).toString());
					chargeMVo.setTaxInclusiveStatus(CommonFunction.checkNull(
							data.get(20)).toString());
					chargeMVo.setTaxRate1(CommonFunction
							.checkNull(data.get(21)).toString());
					chargeMVo.setTaxRate2(CommonFunction
							.checkNull(data.get(22)).toString());
					chargeMVo.setMinChargeAmount(CommonFunction.checkNull(
							data.get(23)).toString());
					chargeMVo.setChargeStatus(CommonFunction.checkNull(
							data.get(24)).toString());
					chargeMVo.setMinchargeMethod(CommonFunction.checkNull(
							data.get(25)).toString());
					chargeMVo.setMinCalculatedOn(CommonFunction.checkNull(
							data.get(26)).toString());
					
					detailList.add(chargeMVo);
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}

	public boolean updateChargeData(Object ob,String chargeId) {
		ChargeMasterVo chargeMasterVo = (ChargeMasterVo) ob;
		String lbxCharge = (String) chargeMasterVo.getLbxCharge();
		logger.info("vo.getChargeStatus():-"
				+ chargeMasterVo.getChargeStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		// String
		// selquery="SELECT CHARGE_ID FROM COM_CHARGES_M WHERE CHARGE_ID='"+lbxCharge+"'";
		// logger.info("In updateChargeData.....................................Dao Impl"+selquery);
		StringBuffer bufInsSql = new StringBuffer();
		boolean status = false;
		String chargeStatus = "X";
		String tdsStatus = "N";
		String taxInclusiveStatus = "N";
		String taxApplicableStatus = "N";
		try {
			if (chargeMasterVo.getChargeStatus() != null
					&& chargeMasterVo.getChargeStatus().equals("on")) {
				chargeStatus = "A";
			} else {
				chargeStatus = "X";

			}
			if (chargeMasterVo.getTdsStatus() != null
					&& chargeMasterVo.getTdsStatus().equals("on")) {
				tdsStatus = "Y";
			} else {
				tdsStatus = "N";

			}
			if (chargeMasterVo.getTaxInclusiveStatus() != null
					&& chargeMasterVo.getTaxInclusiveStatus().equals("on")) {
				taxInclusiveStatus = "Y";
			} else {
				taxInclusiveStatus = "N";

			}
			if (chargeMasterVo.getTaxStatus() != null
					&& chargeMasterVo.getTaxStatus().equals("on")) {
				taxApplicableStatus = "Y";
			} else {
				taxApplicableStatus = "N";

			}
			logger
					.info("In updateChargeData.....................................Dao Impl--tdsRate------"
							+ chargeMasterVo.getTdsRate());
			bufInsSql.append("UPDATE COM_CHARGES_M SET SCHEME_ID=?,DEF_STAGE_ID=?,APP_STAGE_ID=?,CHARGE_BP_TYPE=?,CHARGE_TYPE=?,CHARGE_METHOD=?,");
			bufInsSql.append("CHARGE_AMOUNT=?,CALCULATED_ON=?,TDS_APPLICABLE=?,TDS_RATE=?,TAX_APPLICABLE=?,TAX_INCLUSIVE=?,TAX_RATE1=?,TAX_RATE2=?,MIN_CHARGE_METHOD=?,MIN_CHARGE_AMOUNT=?,");
			bufInsSql.append("REC_STATUS=?,MIN_CHARGE_CALCULATED=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" WHERE CHARGE_ID=?");

			if (CommonFunction.checkNull(chargeMasterVo.getLbxScheme())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeMasterVo.getLbxScheme()
						.toUpperCase());

			if (CommonFunction.checkNull(chargeMasterVo.getLbxStage())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeMasterVo.getLbxStage()
						.toUpperCase());

			if (CommonFunction.checkNull(chargeMasterVo.getLbxAppStage())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeMasterVo.getLbxAppStage()
						.toUpperCase());
			logger.info("chargeMasterVo.getLbxAppStage()........"+chargeMasterVo.getLbxAppStage());

			if (CommonFunction.checkNull(
					chargeMasterVo.gethBuyerSupplierBPType()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeMasterVo
						.gethBuyerSupplierBPType().toUpperCase());

			if (CommonFunction.checkNull(chargeMasterVo.getChargeType())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeMasterVo.getChargeType());

			if (CommonFunction.checkNull(chargeMasterVo.getChargeMethod())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject
						.addString(chargeMasterVo.getChargeMethod());

			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getChargeAmount()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				escapeSql(chargeMasterVo.getChargeAmount()).trim()).toString());

			if (CommonFunction.checkNull(chargeMasterVo.getLbxCalculatedOn())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeMasterVo
						.getLbxCalculatedOn());

			if (CommonFunction.checkNull(tdsStatus).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(tdsStatus);

			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getTdsRate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0");
			else
					insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				escapeSql(chargeMasterVo.getTdsRate()).trim()).toString());

			if (CommonFunction.checkNull(taxApplicableStatus).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(taxApplicableStatus);

			if (CommonFunction.checkNull(taxInclusiveStatus).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(taxInclusiveStatus);

			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getTaxRate1()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				escapeSql(chargeMasterVo.getTaxRate1()).trim()).toString());

			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getTaxRate2()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				escapeSql(chargeMasterVo.getTaxRate2()).trim()).toString());

			if(CommonFunction.checkNull(chargeMasterVo.getChargeMethod()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeMasterVo.getChargeMethod());
			
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(chargeMasterVo.getMinChargeAmount()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				escapeSql(chargeMasterVo.getMinChargeAmount()).trim()).toString());

			if (CommonFunction.checkNull(chargeStatus).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeStatus);
			
			if (CommonFunction.checkNull(chargeMasterVo.getLbxCalculatedOn())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeMasterVo
						.getLbxCalculatedOn());

			//----------------------------------
						if (CommonFunction.checkNull(chargeMasterVo.getMakerId()).equalsIgnoreCase(
						""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(chargeMasterVo.getMakerId());
						if (CommonFunction.checkNull(chargeMasterVo.getMakerDate())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(chargeMasterVo.getMakerDate());
						//----------------------------------

			if (CommonFunction.checkNull(chargeId).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(chargeId);

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN insertBankBranchMaster() insert query1 ### "
					+ insertPrepStmtObject.printQuery());
			updatelist.add(insertPrepStmtObject);
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public ArrayList<ChargeMasterVo> searchChargeData(Object ob) {
		String productId = "";
		String schemeId = "";
		String chargeId = "";
		String chargeCode = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ChargeMasterVo chargeMasterVo = (ChargeMasterVo) ob;
		ArrayList<ChargeMasterVo> detailList = new ArrayList<ChargeMasterVo>();
		try {
			logger.info("In searchChargeData()............Inside ejb server file........Dao Impl");
			productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeMasterVo.getLbxProductSearchID())).trim());
			schemeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeMasterVo.getLbxSchemeSearch())).trim());
			chargeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeMasterVo.getLbxChargeSearch())).trim());
			String charge=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeMasterVo.getCharge())).trim());
			logger.info("In searchChargeData charge---- " + charge);
			logger.info("In searchChargeData chargeId---- " + chargeId);
			boolean appendSQL = false;
			StringBuffer sbAppendToSQLCount = new StringBuffer();
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT V.CHARGE_CODE,(SELECT A.CHARGE_DESC FROM COM_CHARGE_CODE_M A WHERE A.CHARGE_CODE=V.CHARGE_CODE ) AS CHARGE_DESC,"
							+ "PRODUCT_ID,(SELECT B.PRODUCT_DESC FROM CR_PRODUCT_M B WHERE B.PRODUCT_ID=V.PRODUCT_ID ) AS PRODUCT_DESC,"
							+ "SCHEME_ID,(SELECT C.SCHEME_DESC FROM CR_SCHEME_M C WHERE C.SCHEME_ID=V.SCHEME_ID) AS SCHEME_DESC,"
							+ "DEF_STAGE_ID,(SELECT D.STAGE_DESC FROM CR_STAGE_M D WHERE D.STAGE_ID=V.DEF_STAGE_ID ) AS DEF_STAGE_DESC,"
							+ "APP_STAGE_ID,(SELECT D.STAGE_DESC FROM CR_STAGE_M D WHERE D.STAGE_ID=V.APP_STAGE_ID ) AS APP_STAGE_DESC,"
							+ "CHARGE_BP_TYPE,(SELECT E.DESCRIPTION FROM GENERIC_MASTER E WHERE E.VALUE=V.CHARGE_BP_TYPE AND GENERIC_KEY='BPTYPE' ) AS CHARGE_BP_TYPE_DESC,"
							+ "V.CHARGE_CODE,CHARGE_TYPE,CHARGE_METHOD,CHARGE_AMOUNT,"
							+ "CALCULATED_ON,(SELECT F.CHARGE_DESC FROM COM_CHARGE_CODE_M F WHERE F.CHARGE_CODE=V.CALCULATED_ON) AS CALCULATED_ON_DESC,"
							+ "TDS_APPLICABLE,TDS_RATE,TAX_APPLICABLE,TAX_INCLUSIVE,TAX_RATE1,TAX_RATE2,MIN_CHARGE_AMOUNT,"
							+ "case when V.REC_STATUS='A' then 'Active' else 'Inactive' end as Status,V.CHARGE_ID,MIN_CHARGE_METHOD,MIN_CHARGE_CALCULATED,(SELECT F.CHARGE_DESC FROM COM_CHARGE_CODE_M F WHERE F.CHARGE_CODE=V.MIN_CHARGE_CALCULATED) AS MIN_CHARGE_CALCULATED_DESC");
			bufInsSql.append(" FROM COM_CHARGES_M V, COM_CHARGE_CODE_M R");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM COM_CHARGES_M V,COM_CHARGE_CODE_M R");
			
			bufInsSql.append(" WHERE V.CHARGE_CODE=R.CHARGE_CODE AND R.SYSTEM_DEFINED='N'");
			bufInsSqlTempCount.append(" WHERE V.CHARGE_CODE=R.CHARGE_CODE AND R.SYSTEM_DEFINED='N'");
			
			if ((!(productId.equalsIgnoreCase(""))) && (!(schemeId.equalsIgnoreCase(""))) && (!(chargeId.equalsIgnoreCase("")))) {
				bufInsSql.append("AND PRODUCT_ID='" + productId+ "' AND SCHEME_ID='" + schemeId
						+ "'  AND V.CHARGE_CODE ='" + chargeId + "'");
				
				bufInsSqlTempCount.append("AND PRODUCT_ID='" + productId+ "' AND SCHEME_ID='" + schemeId
						+ "'  AND V.CHARGE_CODE ='" + chargeId + "'");
			}
			if (!(productId.equalsIgnoreCase("")) || !(schemeId.equalsIgnoreCase("")) || !(chargeId.equalsIgnoreCase(""))) {
				appendSQL = true;
			}

			if (appendSQL) {
				bufInsSql.append(" AND ");
				bufInsSqlTempCount.append(" AND ");
			}

			if ((!(productId.equalsIgnoreCase("")))) {
				bufInsSql.append(" PRODUCT_ID='" + productId + "' AND");
				bufInsSqlTempCount.append(" PRODUCT_ID='" + productId + "' AND");
				appendSQL = true;

			}

			if ((!(schemeId.equalsIgnoreCase("")))) {
				bufInsSql.append(" SCHEME_ID='" + schemeId + "' AND");
				bufInsSqlTempCount.append(" SCHEME_ID='" + schemeId + "' AND");
				appendSQL = true;
			}

			if ((!(chargeId.equalsIgnoreCase("")))) {
				bufInsSql.append(" V.CHARGE_CODE='" + chargeId + "'");
				bufInsSqlTempCount.append(" V.CHARGE_CODE='" + chargeId + "'");
				appendSQL = true;
			}
			if ((!(charge.equalsIgnoreCase("")))) {
				bufInsSql.append(" AND CHARGE_ID='" + charge + "'");
				bufInsSqlTempCount.append(" AND CHARGE_ID='" + charge + "'");
				appendSQL = true;
			}
			//bufInsSql.append(" ORDER BY CHARGE_ID");
			logger.info("In appendSQL true---- " + appendSQL);
			
			if (appendSQL) {
				logger.info("In appendSQL true---- ");
				String tmp = bufInsSql.toString();
				String tmp1 = bufInsSqlTempCount.toString();
				
				logger.info("In setSearchCharge() ## tmp ## " + tmp);
				logger.info("In appendSQL true----  in check index Of" + tmp.lastIndexOf("AND") + "------"+ (tmp.length() - 3));
				
				if (tmp.lastIndexOf("AND") == (tmp.length() - 3) && tmp1.lastIndexOf("AND") == (tmp1.length() - 3)) {
					
					logger.info("In appendSQL true----  in check index Of");
					
					tmp = (tmp).substring(0, (tmp.length() - 4));
					tmp1 = (tmp1).substring(0,(tmp1.length()-4));
					logger.info("search Query...tmp." + tmp+" ORDER BY CHARGE_ID");
					
					searchlist = ConnectionDAOforEJB.sqlSelect(tmp+" ORDER BY CHARGE_ID");
					
					count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(tmp1.toString()));
				} 
				
                else {
					logger.info("search Query...tmp." + tmp+" ORDER BY CHARGE_ID");
					searchlist = ConnectionDAOforEJB.sqlSelect(tmp+" ORDER BY CHARGE_ID");
					
					count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
				}

			} else {
				logger.info("search Query...else-------." + bufInsSql);
				logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	            
				count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
				
//				if((productId.trim()==null && schemeId.trim()==null && chargeId.trim()==null) || (productId.trim().equalsIgnoreCase("") && schemeId.trim().equalsIgnoreCase("") && chargeId.trim().equalsIgnoreCase("")) || chargeMasterVo.getCurrentPageLink()>1)
//				{
				
				logger.info("current PAge Link no .................... "+chargeMasterVo.getCurrentPageLink());
				if(chargeMasterVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (chargeMasterVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				bufInsSql.append(" ORDER BY CHARGE_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
											
//				}
				logger.info("query : "+bufInsSql);
				
				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			}
			logger.info("searchChargeData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
			
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					ChargeMasterVo chargeMVo = new ChargeMasterVo();

					chargeMVo.setLbxChargeModify("<a href=chargeMasterSearch.do?method=openEditCharge&chargeSearchId="
									+ CommonFunction.checkNull(data.get(26)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");

					chargeMVo.setLbxCharge(CommonFunction.checkNull(data.get(0)).toString());
					chargeMVo.setChargeId(CommonFunction.checkNull(data.get(1)).toString());
					chargeMVo.setLbxProductID(CommonFunction.checkNull(data.get(2)).toString());
					chargeMVo.setProductId(CommonFunction.checkNull(data.get(3)).toString());
					chargeMVo.setLbxScheme(CommonFunction.checkNull(data.get(4)).toString());
					chargeMVo.setSchemeId(CommonFunction.checkNull(data.get(5)).toString());
					chargeMVo.setLbxStage(CommonFunction.checkNull(data.get(6)).toString());
					chargeMVo.setStageId(CommonFunction.checkNull(data.get(7)).toString());
					chargeMVo.setLbxAppStage(CommonFunction.checkNull(data.get(8)).toString());
					chargeMVo.setAppStageId(CommonFunction.checkNull(data.get(9)).toString());
					chargeMVo.setChargeBPType(CommonFunction.checkNull(data.get(10)).toString());
					chargeMVo.sethBuyerSupplierBPType(CommonFunction.checkNull(data.get(11)).toString());
					chargeMVo.setChargeCode(CommonFunction.checkNull(data.get(12)).toString());
					chargeMVo.setChargeType(CommonFunction.checkNull(data.get(13)).toString());
					chargeMVo.setChargeMethod(CommonFunction.checkNull(data.get(14)).toString());
				if(!CommonFunction.checkNull(data.get(15)).equalsIgnoreCase(""))	
				{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(15))).trim());
					chargeMVo.setChargeAmount(myFormatter.format(reconNum));
				}
//					chargeMVo.setChargeAmount(CommonFunction.checkNull(
//							data.get(15)).toString());
					chargeMVo.setLbxCalculatedOn(CommonFunction.checkNull(data.get(16)).toString());
					chargeMVo.setCalculatedOn(CommonFunction.checkNull(data.get(17)).toString());
					chargeMVo.setTdsStatus(CommonFunction.checkNull(data.get(18)).toString());
					if(!CommonFunction.checkNull(data.get(19)).equalsIgnoreCase(""))	
					{	
						Number reconNum1 =myFormatter.parse((CommonFunction.checkNull(data.get(19))).trim());
						chargeMVo.setTdsRate(myFormatter.format(reconNum1));
					}
//					chargeMVo.setTdsRate(CommonFunction.checkNull(data.get(19))
//							.toString());
					chargeMVo.setTaxStatus(CommonFunction.checkNull(data.get(20)).toString());
					chargeMVo.setTaxInclusiveStatus(CommonFunction.checkNull(data.get(21)).toString());
					if(!CommonFunction.checkNull(data.get(22)).equalsIgnoreCase(""))	
					{
						Number reconNum2 =myFormatter.parse((CommonFunction.checkNull(data.get(22))).trim());
						chargeMVo.setTaxRate1(myFormatter.format(reconNum2));
					}
//					chargeMVo.setTaxRate1(CommonFunction
//							.checkNull(data.get(22)).toString());
					if(!CommonFunction.checkNull(data.get(23)).equalsIgnoreCase(""))	
					{
					Number reconNum3 =myFormatter.parse((CommonFunction.checkNull(data.get(23))).trim());
					chargeMVo.setTaxRate2(myFormatter.format(reconNum3));
					}
//					chargeMVo.setTaxRate2(CommonFunction
//							.checkNull(data.get(23)).toString());
					if(!CommonFunction.checkNull(data.get(24)).equalsIgnoreCase(""))	
					{	
					Number reconNum4 =myFormatter.parse((CommonFunction.checkNull(data.get(24))).trim());
					chargeMVo.setMinChargeAmount(myFormatter.format(reconNum4));
					}
//					chargeMVo.setMinChargeAmount(CommonFunction.checkNull(
//							data.get(24)).toString());
					chargeMVo.setChargeStatus(CommonFunction.checkNull(data.get(25)).toString());
					chargeMVo.setCharge(CommonFunction.checkNull(data.get(26)).toString());
					chargeMVo.setMinchargeMethod(CommonFunction.checkNull(data.get(27)).toString());
					chargeMVo.setLbxminCalculatedOn(CommonFunction.checkNull(data.get(28)).toString());
					chargeMVo.setMinCalculatedOn(CommonFunction.checkNull(data.get(29)).toString());
					chargeMVo.setTotalRecordSize(count);
					detailList.add(chargeMVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			ChargeMasterVo chargeMVo = new ChargeMasterVo();
//			chargeMVo.setTotalRecordSize(count);
//			detailList.add(chargeMVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}
// Code Ends By Ritu For Charge Master

	// Code Start By Ritu For Role Master
	public boolean insertRoleMaster(Object ob) {
		RoleMasterVo roleMasterVo = (RoleMasterVo) ob;
		boolean status = false;
		logger.info("datebase type............"+dbType);
		logger.info("In insertRoleMaster.........."+ roleMasterVo.getRoleStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "SELECT ROLE_DESC FROM SEC_ROLE_M WHERE ROLE_DESC='"
				+ StringEscapeUtils.escapeSql(roleMasterVo.getRoleDesc().trim()) + "' AND MODULE_ID='"+StringEscapeUtils.escapeSql(roleMasterVo.getLbxModule().trim()) +"'";
		logger.info("In insertRoleMaster.....................................Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (roleMasterVo.getRoleStatus() != null
						&& roleMasterVo.getRoleStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert role master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("INSERT INTO SEC_ROLE_M(ROLE_DESC,MODULE_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				if (CommonFunction.checkNull(roleMasterVo.getRoleDesc())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(roleMasterVo.getRoleDesc()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(roleMasterVo.getLbxModule())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(roleMasterVo.getLbxModule());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(roleMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(roleMasterVo.getMakerId());

				if (CommonFunction.checkNull(roleMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(roleMasterVo.getMakerDate());
				if (CommonFunction.checkNull(roleMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(roleMasterVo.getMakerId());

				if (CommonFunction.checkNull(roleMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(roleMasterVo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertRoleMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertRoleMaster......................"	+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public ArrayList<RoleMasterVo> searchRoleData(Object ob) {
		String roleId = "";
		String roleDesc = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		RoleMasterVo roleMasterVo = (RoleMasterVo) ob;
		ArrayList<RoleMasterVo> detailList = new ArrayList<RoleMasterVo>();
		try {
			logger.info("In searchRoleData().....................................Dao Impl");
			roleId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleMasterVo.getRoleId())).trim());
			roleDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleMasterVo.getRoleSearchDesc())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT ROLE_ID,ROLE_DESC,");
			bufInsSql.append(" MODULE_ID,(SELECT A.MODULE_DESC FROM SEC_MODULE_M A WHERE A.MODULE_ID=V.MODULE_ID ) AS MODULE_DESC,");
			//bufInsSql.append(" if(REC_STATUS='A','Active','Inactive')AS STATUS ");
			bufInsSql.append(" case when REC_STATUS='A' then 'Active' else 'Inactive' end AS STATUS ");
			bufInsSql.append(" FROM SEC_ROLE_M V ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM SEC_ROLE_M V ");
			
			if ((!(roleId.equalsIgnoreCase(""))) && (!(roleDesc.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE ROLE_DESC like '%" + roleDesc + "%' AND ROLE_ID='" + roleId + "'");
				bufInsSqlTempCount.append(" WHERE ROLE_DESC like '%" + roleDesc + "%' AND ROLE_ID='" + roleId + "'");
			}

			else if (!roleDesc.equals("")) {
				bufInsSql.append(" WHERE ROLE_DESC LIKE '%" + roleDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE ROLE_DESC LIKE '%" + roleDesc + "%' ");
			}

			else if (!roleId.equals("")) {
				bufInsSql.append(" WHERE ROLE_ID = '" + roleId + "' ");
				bufInsSqlTempCount.append(" WHERE ROLE_ID = '" + roleId + "' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((roleId.trim()==null && roleDesc.trim()==null) || (roleId.trim().equalsIgnoreCase("") && roleDesc.trim().equalsIgnoreCase("")) || roleMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+roleMasterVo.getCurrentPageLink());
			if(roleMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (roleMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY ROLE_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchRoleData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchRoleData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					RoleMasterVo roleMVo = new RoleMasterVo();

					roleMVo.setRoleIdModify("<a href=roleMasterSearch.do?method=openEditRole&roleId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					roleMVo.setRoleId(CommonFunction.checkNull(data.get(0)).toString());
					roleMVo.setRoleDesc(CommonFunction.checkNull(data.get(1)).toString());
					roleMVo.setLbxModule(CommonFunction.checkNull(data.get(2)).toString());
					roleMVo.setModuleId(CommonFunction.checkNull(data.get(3)).toString());
					roleMVo.setRoleStatus(CommonFunction.checkNull(data.get(4)).toString());
					roleMVo.setTotalRecordSize(count);
					detailList.add(roleMVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			RoleMasterVo roleMVo = new RoleMasterVo();
//			roleMVo.setTotalRecordSize(count);
//			detailList.add(roleMVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean updateRoleData(Object ob) {
		RoleMasterVo roleMasterVo = (RoleMasterVo) ob;
		String roleDesc = (String) roleMasterVo.getRoleDesc().trim();
		logger.info("datebase Type................."+dbType);
		logger.info("vo.getRoleStatus():-" + roleMasterVo.getRoleStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();

		String query = "SELECT ROLE_DESC FROM SEC_ROLE_M WHERE ROLE_DESC='"
			+ StringEscapeUtils
					.escapeSql(roleMasterVo.getRoleDesc().trim()) + "' AND MODULE_ID='"+StringEscapeUtils.escapeSql(roleMasterVo.getLbxModule().trim()) +"' AND ROLE_ID!='"+roleMasterVo.getRoleId()+"'";
	    logger
			.info("In insertRoleMaster.....................................Dao Impl"
					+ query);
	    boolean st = ConnectionDAOforEJB.checkStatus(query);
	    
		boolean status = false;
		String stat = "";
		try {
			if (!st) {
			if (roleMasterVo.getRoleStatus() != null
					&& roleMasterVo.getRoleStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateRoleData.....................................Dao Impl");
			bufInsSql.append("UPDATE SEC_ROLE_M SET ROLE_DESC=?,MODULE_ID=?, REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" WHERE ROLE_ID=?");

			if (CommonFunction.checkNull(roleMasterVo.getRoleDesc())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(roleMasterVo.getRoleDesc()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(roleMasterVo.getLbxModule())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(roleMasterVo.getLbxModule());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			//----------------------------------
			if (CommonFunction.checkNull(roleMasterVo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(roleMasterVo.getMakerId());
			if (CommonFunction.checkNull(roleMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(roleMasterVo.getMakerDate());
			//----------------------------------

			if (CommonFunction.checkNull(roleMasterVo.getRoleId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(roleMasterVo.getRoleId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			
			if(stat.equalsIgnoreCase("X")){
				bufInsSql = new StringBuffer();
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql.append("UPDATE SEC_USER_ACCESS_M SET REC_STATUS='X' WHERE  ROLE_ID='"+roleMasterVo.getRoleId()+"'");
				logger.info("yudate query........"+bufInsSql.toString());
				insertPrepStmtObject.setSql(bufInsSql.toString());
				updatelist.add(insertPrepStmtObject);
			}
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			}else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// Code Start By Ritu For Role Access Master
	public ArrayList<RoleAccessMasterVo> searchRoleAccessData(Object ob) {
		String roleId = "";
		String moduleId = "";
		ArrayList searchlist = new ArrayList();
		RoleAccessMasterVo roleAccessMasterVo = (RoleAccessMasterVo) ob;
		ArrayList<RoleAccessMasterVo> detailList = new ArrayList<RoleAccessMasterVo>();

		try {
			logger.info("In searchRoleAccessData().....................................Dao Impl");
			
			roleId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleAccessMasterVo.getLbxRoleId())).trim());
			moduleId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleAccessMasterVo.getLbxModuleId())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" Select SFM.FUNCTION_ID,SFM.FUNCTION_DESC,SRAM.FUNCTION_ID,SFM.PARENT_FUNCTION_ID from sec_function_m SFM");
			bufInsSql.append(" LEFT JOIN  sec_role_access_m SRAM ON SFM.FUNCTION_ID=SRAM.FUNCTION_ID ");
			bufInsSql.append(" AND SRAM.ROLE_ID='" + roleId	+ "' AND SRAM.MODULE_ID='" + moduleId + "' ");
			bufInsSql.append(" WHERE SFM.MODULE_ID='" + moduleId + "' AND SFM.REC_STATUS='A' ORDER BY SFM.SEQUENCE_ID ");

			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN searchRoleAccessData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchRoleAccessData " + searchlist.size());
			
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchRoleAccessDataList "+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				
				if (data.size() > 0) {
					RoleAccessMasterVo roleAccessMVo = new RoleAccessMasterVo();
					roleAccessMVo.setFunName(CommonFunction.checkNull(data.get(0)).toString());
					roleAccessMVo.setFunDesc(CommonFunction.checkNull(data.get(1)).toString());
					roleAccessMVo.setFunNameforCheckBox(CommonFunction.checkNull(data.get(2)).toString());
					roleAccessMVo.setParentId(CommonFunction.checkNull(data.get(3)).toString());

					detailList.add(roleAccessMVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	public boolean insertRoleAccessMaster(Object ob, String[] checkbox) {

		ArrayList qryList = null;
		ArrayList insertList = null;
		StringBuffer bufInsSql = null;
		PrepStmtObject insertPrepStmtObject = null;
		boolean status = false;
		try {
			RoleAccessMasterVo roleAccessMasterVo = (RoleAccessMasterVo) ob;

			String roleId = roleAccessMasterVo.getLbxRoleId();
			String moduleId = roleAccessMasterVo.getLbxModuleId();
			logger.info("In insertRoleMaster.........."+ roleAccessMasterVo.getFunName());
			logger.info("In roleId.........."+ roleId);
			logger.info("In moduleId.........."+ moduleId);
			logger.info("datebase........."+dbType);

			qryList = new ArrayList();
			insertList = new ArrayList();

			String stat = "X";
			String query = "DELETE FROM SEC_ROLE_ACCESS_M WHERE ROLE_ID='"+ roleId + "' AND MODULE_ID='" + moduleId + "'";
			
			logger.info("In insertRoleAccessMaster....................Dao Impl"+ query);

			qryList.add(query);
			status = ConnectionDAOforEJB.sqlInsUpdDelete(qryList);

			if (status) {

				for (int i = 0; i < checkbox.length; i++) {

					logger.info("In insert role master");
					logger.info("In insert getLbxModuleId ... "+roleAccessMasterVo.getLbxModuleId());
					
					bufInsSql = new StringBuffer();
					insertPrepStmtObject = new PrepStmtObject();

					bufInsSql.append("INSERT INTO SEC_ROLE_ACCESS_M(ROLE_ID,MODULE_ID,");
					bufInsSql.append(" FUNCTION_ID,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					bufInsSql.append(" ?,");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					if (CommonFunction.checkNull(roleAccessMasterVo.getLbxRoleId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(roleAccessMasterVo.getLbxRoleId().toUpperCase());

					if (CommonFunction.checkNull(roleAccessMasterVo.getLbxModuleId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(roleAccessMasterVo.getLbxModuleId());

					if (CommonFunction.checkNull(checkbox[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkbox[i]);

					if (CommonFunction.checkNull(roleAccessMasterVo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(roleAccessMasterVo.getMakerId());

					if (CommonFunction.checkNull(
							roleAccessMasterVo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(roleAccessMasterVo
								.getMakerDate());
					if (CommonFunction.checkNull(
							roleAccessMasterVo.getMakerId()).equalsIgnoreCase(
							""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(roleAccessMasterVo
								.getMakerId());

					if (CommonFunction.checkNull(
							roleAccessMasterVo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(roleAccessMasterVo
								.getMakerDate());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					insertList.add(insertPrepStmtObject);
				}

				logger.info("IN insertRoleAccessMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());

				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(insertList);
				logger
						.info("In insertRoleAccessMaster......................"
								+ status);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// Code End By Ritu for Role Access Master
	
	// Code Start By Ritu for User Master
	
	public boolean checkEmpIDInEditUserMaster(Object ob) {
		boolean empSt = false;
		boolean status = true;
		try{
			UserMasterVo userMasterVo = (UserMasterVo) ob;
			
			logger.info("In checkEmpIDInEditUserMaster.........."+ userMasterVo.getUserStatus());
	
	        String empQuery = "SELECT USER_EMP_ID FROM SEC_USER_M WHERE USER_ID <> '"+userMasterVo.getUserId()+"' AND USER_EMP_ID='"+ StringEscapeUtils.escapeSql(userMasterVo.getEmpId().trim())+ "'";
			
			logger.info("In checkEmpIDInEditUserMaster.................Dao Impl"+ empQuery);
			
			empSt = ConnectionDAOforEJB.checkStatus(empQuery);
			
			if(empSt){
				status=false;
			}
			
		}catch(Exception e){
			logger.info("Exception in checkEmpIDInEditUserMaster() :: "+e.getMessage());
		}
		return status;
	}

	
	public boolean checkReportingToUserStatus(Object ob) {
		String statusCheck = "";
		boolean status = false;
		UserMasterVo userMasterVo = (UserMasterVo) ob;
		String reportingto=userMasterVo.getLbxReportingUser();
		try{
						
			logger.info("In checkReportingToUserStatus.....reportingto....."+ reportingto);
			String statusChkQry="SELECT rec_status FROM sec_user_m WHERE user_id='"+CommonFunction.checkNull(reportingto).trim()+"'" ;
			
			
			statusCheck = ConnectionDAOforEJB.singleReturn(statusChkQry);
			logger.info("In checkReportingToUserStatus.................Dao Impl"+ statusChkQry);
			logger.info("In checkReportingToUserStatus.......statusCheck..........Dao Impl"+ statusCheck);
			
			if(statusCheck.equalsIgnoreCase("A")){
				status=true;
			}
			
		}catch(Exception e){
			logger.info("Exception in checkEmpIDInEditUserMaster() :: "+e.getMessage());
		}
		return status;
	}
	
	public boolean checkEmpIDInUserMaster(Object ob) {
		boolean empSt = false;
		boolean status = true;
		try{
			UserMasterVo userMasterVo = (UserMasterVo) ob;
			
			logger.info("In checkEmpIDInUserMaster.........."+ userMasterVo.getUserStatus());
	
	        String empQuery = "SELECT USER_EMP_ID FROM SEC_USER_M WHERE USER_EMP_ID='"+ StringEscapeUtils.escapeSql(userMasterVo.getEmpId().trim())+ "'";
			
			logger.info("In checkEmpIDInUserMaster.................Dao Impl"+ empQuery);
			
			empSt = ConnectionDAOforEJB.checkStatus(empQuery);
			
			if(empSt){
				status=false;
			}
		}catch(Exception e){
			logger.info("Exception in checkEmpIDInUserMaster() :: "+e.getMessage());
		}
		return status;
	}

	public String insertUserMaster(Object ob,String[] branchName,String[] levelName) {
		UserMasterVo userMasterVo = (UserMasterVo) ob;
		boolean status = false;
		String result = "";
		logger.info("In insertUserMaster.........."	+ userMasterVo.getUserStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		final String charset = "0123456789abcdefghijklmnopqrstuvwxyz";
		String query = "SELECT USER_ID FROM SEC_USER_M WHERE USER_ID='"+ StringEscapeUtils.escapeSql(userMasterVo.getUserId().trim())+ "'";
		
		logger.info("In insertUserMaster.....................................Dao Impl"+ query);
		        
		String generatepass = null;
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		
		try {
			Random rand = new Random(System.currentTimeMillis());
			StringBuffer sb = new StringBuffer();
			int length = 8;
			for (int i = 0; i < length; i++) {
				int pos = rand.nextInt(charset.length());
				sb.append(charset.charAt(pos));
			}

			logger.info("password" + sb.toString());
			generatepass = sb.toString();
			userMasterVo.setPassword(generatepass);
			String pass = md5.en(sb.toString());

			if (!st) {
				if (userMasterVo.getUserStatus() != null
						&& userMasterVo.getUserStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert USER master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO SEC_USER_M(USER_ID,USER_NAME,USER_EMP_ID,USER_DEPARTMENT,USER_DEF_BRANCH,USER_DESIGNATION,");
				bufInsSql.append("USER_PHONE1,USER_PHONE2,USER_EMAIL,USER_PASSWORD,REC_STATUS,MAKER_ID,MAKER_DATE,LAST_PASSWORD_DATE,FORCED_PASSWORD_FLAG,PASS_COUNT,ACCOUNT_STATUS,USER_REPORTING_TO,BRANCH_ACCESS,VALIDITY_DATE,LEVEL_ACCESS)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(dbo);
				bufInsSql.append("sysdate() ,");
				bufInsSql.append(" 'Y',");
				bufInsSql.append(" '0',");
				bufInsSql.append(" 'U',");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),"); // VALIDITY_DATE
				bufInsSql.append(" ?)");
				if (CommonFunction.checkNull(userMasterVo.getUserId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getUserId()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(userMasterVo.getUserName())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getUserName()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(userMasterVo.getEmpId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getEmpId()
							.trim());

				if (CommonFunction.checkNull(userMasterVo.getLbxDepartmentId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo
							.getLbxDepartmentId().trim());

				if (CommonFunction.checkNull(userMasterVo.getLbxBranchId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo
							.getLbxBranchId().trim());

				if (CommonFunction
						.checkNull(userMasterVo.getLbxDesignationId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo
							.getLbxDesignationId());

				if (CommonFunction.checkNull(userMasterVo.getPhone1())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getPhone1()
							.trim());

				if (CommonFunction.checkNull(userMasterVo.getPhone2())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getPhone2()
							.trim());

				if (CommonFunction.checkNull(userMasterVo.getEmail())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getEmail()
							.trim());

				if (CommonFunction.checkNull(pass).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(pass);

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(userMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getMakerId());

				if (CommonFunction.checkNull(userMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getMakerDate());


				if (CommonFunction.checkNull(userMasterVo.getLbxReportingUser()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getLbxReportingUser());
				
				if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);
				
				if (CommonFunction.checkNull(userMasterVo.getValidityDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getValidityDate());
				
//				if (CommonFunction.checkNull(userMasterVo.getSelection()[0]).equalsIgnoreCase(""))
//					insertPrepStmtObject.addNull();
				logger.info("IN :::::::::::::"+userMasterVo.getSelection());
				logger.info("IN ::::::[]:::::::"+userMasterVo.getSelection().length);
				if (userMasterVo.getSelection().length==0)
				    insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(userMasterVo.getSelection()[0]);
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertUserMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertUserMaster......................"
						+ status);
			} 
			else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("In insertUserMaster......................"+ status);
		if(status)
			result = generatepass;
		return result;
	}

	public ArrayList<UserMasterVo> searchUserData(Object ob) {
		String userId = "";
		String userName = "";
		String branchId = "";
		String reportingUser="";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		UserMasterVo userMasterVo = (UserMasterVo) ob;
		ArrayList<UserMasterVo> detailList = new ArrayList<UserMasterVo>();
		try {
			logger.info("In searchUserData().....................................Dao Impl");
			userId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(userMasterVo.getUserSearchId())).trim());
			userName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(userMasterVo.getUserSearchName())).trim());
			branchId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(userMasterVo.getLbxBranchSearchId())).trim());
			reportingUser = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(userMasterVo.getLbxUserSearchId())).trim());
			logger.info("reportingUser..." + reportingUser);
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT USER_ID,USER_NAME,USER_EMP_ID,");
			bufInsSql.append("USER_DEPARTMENT,(SELECT C.DEPARTMENT_DESC FROM COM_DEPARTMENT_M C WHERE C.DEPARTMENT_ID=V.USER_DEPARTMENT ) AS DEPARTMENT_DESC,");
			bufInsSql.append(" USER_DEF_BRANCH,(SELECT A.BRANCH_DESC FROM COM_BRANCH_M A WHERE A.BRANCH_ID=V.USER_DEF_BRANCH ) AS BRANCH_DESC,");
			bufInsSql.append(" USER_DESIGNATION,(SELECT B.DESCRIPTION FROM GENERIC_MASTER B WHERE GENERIC_KEY='USER_DESIGNATION' AND B.VALUE=V.USER_DESIGNATION ) AS DESIGNATION_DESC,");
			bufInsSql.append(" USER_PHONE1,USER_PHONE2,USER_EMAIL,");
			//bufInsSql.append(" if(REC_STATUS='A','Active','Inactive')as Status,USER_REPORTING_TO,");
			bufInsSql.append(" case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,USER_REPORTING_TO,");
			bufInsSql.append("(select  USER_NAME from SEC_USER_M where USER_ID=V.USER_REPORTING_TO) AS REPORTINGNAME,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(VALIDITY_DATE,'"+dateFormat+"')");
			bufInsSql.append(" FROM SEC_USER_M V ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM SEC_USER_M V ");
			
			if ((!(userId.equalsIgnoreCase(""))) && (!(userName.equalsIgnoreCase(""))) && (!(branchId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_ID='" + userId + "' AND USER_DEF_BRANCH='" + branchId + "' AND USER_REPORTING_TO='"+reportingUser+"'");
				bufInsSqlTempCount.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_ID='" + userId + "' AND USER_DEF_BRANCH='" + branchId + "' AND USER_REPORTING_TO='"+reportingUser+"'");
			}
			else if ((!(userId.equalsIgnoreCase(""))) && (!(userName.equalsIgnoreCase(""))) && (!(branchId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_ID='" + userId + "' AND USER_DEF_BRANCH='" + branchId + "'");
				bufInsSqlTempCount.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_ID='" + userId + "' AND USER_DEF_BRANCH='" + branchId + "''");
			}
			else if ((!(userId.equalsIgnoreCase(""))) && (!(userName.equalsIgnoreCase(""))) && (!(reportingUser.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_ID='" + userId + "' AND USER_REPORTING_TO='"+reportingUser+"'");
				bufInsSqlTempCount.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_ID='" + userId + "' AND USER_REPORTING_TO='"+reportingUser+"'");
			}
			else if ((!(userName.equalsIgnoreCase(""))) && (!(reportingUser.equalsIgnoreCase(""))) && (!(branchId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_REPORTING_TO='" + reportingUser + "' AND USER_DEF_BRANCH='" + branchId + "'");
				bufInsSqlTempCount.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_REPORTING_TO='" + reportingUser + "' AND USER_DEF_BRANCH='" + branchId + "''");
			}
			else if ((!(branchId.equalsIgnoreCase(""))) && (!(userId.equalsIgnoreCase(""))) && (!(reportingUser.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_DEF_BRANCH = '" + branchId + "' AND USER_ID='" + userId + "' AND USER_REPORTING_TO='"+reportingUser+"'");
				bufInsSqlTempCount.append(" WHERE USER_DEF_BRANCH = '" + branchId + "' AND USER_ID='" + userId + "' AND USER_REPORTING_TO='"+reportingUser+"'");
			}
			else if ((!(userId.equalsIgnoreCase(""))) && (!(userName.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_ID='" + userId + "'");
				bufInsSqlTempCount.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_ID='" + userId + "'");
			}

			else if ((!(userName.equalsIgnoreCase(""))) && (!(branchId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_DEF_BRANCH='" + branchId + "'");
				bufInsSqlTempCount.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_DEF_BRANCH='" + branchId + "'");
			}
			else if ((!(userName.equalsIgnoreCase(""))) && (!(reportingUser.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_REPORTING_TO='" + reportingUser + "'");
				bufInsSqlTempCount.append(" WHERE USER_NAME like '%" + userName + "%' AND USER_REPORTING_TO='" + reportingUser + "'");
			}
			else if ((!(userId.equalsIgnoreCase(""))) && (!(branchId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_ID='" + userId + "' AND USER_DEF_BRANCH='" + branchId + "'");
				bufInsSqlTempCount.append(" WHERE USER_ID='" + userId + "' AND USER_DEF_BRANCH='" + branchId + "'");
			}
			else if ((!(userId.equalsIgnoreCase(""))) && (!(reportingUser.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_ID='" + userId + "' AND USER_REPORTING_TO='" + reportingUser + "'");
				bufInsSqlTempCount.append(" WHERE USER_ID='" + userId + "' AND USER_REPORTING_TO='" + reportingUser + "'");
			}
			else if ((!(reportingUser.equalsIgnoreCase(""))) && (!(branchId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_REPORTING_TO='" + reportingUser + "' AND USER_DEF_BRANCH='" + branchId + "'");
				bufInsSqlTempCount.append(" WHERE USER_REPORTING_TO='" + reportingUser + "' AND USER_DEF_BRANCH='" + branchId + "'");
			}
			else if (!userName.equals("")) {
				bufInsSql.append(" WHERE USER_NAME LIKE '%" + userName + "%' ");
				bufInsSqlTempCount.append(" WHERE USER_NAME LIKE '%" + userName + "%' ");
			}

			else if (!userId.equals("")) {
				bufInsSql.append(" WHERE USER_ID = '" + userId + "' ");
				bufInsSqlTempCount.append(" WHERE USER_ID = '" + userId + "' ");
			}

			else if (!branchId.equals("")) {
				bufInsSql.append(" WHERE USER_DEF_BRANCH = '" + branchId + "' ");
				bufInsSqlTempCount.append(" WHERE USER_DEF_BRANCH = '" + branchId + "' ");
			}
			else if (!reportingUser.equals("")) {
				bufInsSql.append(" WHERE USER_REPORTING_TO='" + reportingUser + "'");
				bufInsSqlTempCount.append(" WHERE USER_REPORTING_TO='" + reportingUser + "'");
			}
					
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((userName.trim()==null && userId.trim()==null && branchId.trim()==null) || (userName.trim().equalsIgnoreCase("") && userId.trim().equalsIgnoreCase("") && branchId.trim().equalsIgnoreCase("")) || userMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+userMasterVo.getCurrentPageLink());
			if(userMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (userMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY USER_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
		
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchUserData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchUserData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					UserMasterVo userMVo = new UserMasterVo();

					userMVo.setUserIdModify("<a href=userMasterSearch.do?method=openEditUser&userSearchId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					userMVo.setUserId(CommonFunction.checkNull(data.get(0)).toString());
					userMVo.setUserName(CommonFunction.checkNull(data.get(1)).toString());
					userMVo.setEmpId(CommonFunction.checkNull(data.get(2)).toString());
					userMVo.setLbxDepartmentId(CommonFunction.checkNull(data.get(3)).toString());
					userMVo.setUserDept(CommonFunction.checkNull(data.get(4)).toString());
					userMVo.setLbxBranchId(CommonFunction.checkNull(data.get(5)).toString());
					userMVo.setBranchId(CommonFunction.checkNull(data.get(6)).toString());
					userMVo.setLbxDesignationId(CommonFunction.checkNull(data.get(7)).toString());
					userMVo.setUserDesg(CommonFunction.checkNull(data.get(8)).toString());
					userMVo.setPhone1(CommonFunction.checkNull(data.get(9)).toString());
					userMVo.setPhone2(CommonFunction.checkNull(data.get(10)).toString());
					userMVo.setEmail(CommonFunction.checkNull(data.get(11)).toString());
					userMVo.setUserStatus(CommonFunction.checkNull(data.get(12)).toString());
					userMVo.setLbxReportingUser(CommonFunction.checkNull(data.get(13)).toString());
					userMVo.setReportingto(CommonFunction.checkNull(data.get(14)).toString());
					userMVo.setValidityDate(CommonFunction.checkNull(data.get(15)).toString());
				
					userMVo.setTotalRecordSize(count);
					detailList.add(userMVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			UserMasterVo userMVo = new UserMasterVo();
//			userMVo.setTotalRecordSize(count);
//			detailList.add(userMVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean updateUserData(Object ob) {

		logger.info("In updateUserData.........");
		UserMasterVo userMasterVo = (UserMasterVo) ob;
		String userId = (String) userMasterVo.getUserId();
		String reportingto=userMasterVo.getReportingto();
		PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
		logger.info("updateUserData vo.getUserStatus():-" + userMasterVo.getUserStatus());
		ArrayList detailList = new ArrayList();
		StringBuffer bufInsSql=null;
		String statusCheck=null;
		ArrayList data=new ArrayList();
		boolean status = false;
		String stat = "";
		ArrayList qryList = new ArrayList();
		try {
			if (userMasterVo.getUserStatus() != null
					&& userMasterVo.getUserStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}

			String Query="select USER_PASSWORD,USER_LAST_PASSWORD_1,USER_LAST_PASSWORD_2,USER_LAST_PASSWORD_3,USER_LAST_PASSWORD_4,USER_LAST_PASSWORD_5," +
					"LAST_PASSWORD_DATE,SECURITY_QUESTION1,SECURITY_ANSWER1,SECURITY_QUESTION2,SECURITY_ANSWER2,FORCED_PASSWORD_FLAG,ACCOUNT_STATUS," +
					"PASS_COUNT from sec_user_m where user_id='"+CommonFunction.checkNull(userId).trim()+"'" ;
			
			detailList = ConnectionDAOforEJB.sqlSelect(Query);
			logger.info("IN updateUserData() :::::::::::::::detailList::::::::::::::: "+ Query);
			
			for (int i = 0; i < detailList.size(); i++) {
				
				 data = (ArrayList) detailList.get(i);
			}

			bufInsSql = new StringBuffer();
			bufInsSql.append("UPDATE SEC_USER_M SET USER_NAME=?,USER_EMP_ID=?,USER_DEPARTMENT=?,USER_DEF_BRANCH=?,USER_DESIGNATION=?,USER_PHONE1=?,");
			bufInsSql.append("USER_PHONE2=?,USER_EMAIL=?,REC_STATUS=?,USER_REPORTING_TO=?,BRANCH_ACCESS=?,LEVEL_ACCESS=?,");
			bufInsSql.append("USER_PASSWORD=?,USER_LAST_PASSWORD_1=?,USER_LAST_PASSWORD_2=?,USER_LAST_PASSWORD_3=?,USER_LAST_PASSWORD_4=?,");
			bufInsSql.append("USER_LAST_PASSWORD_5=?,LAST_PASSWORD_DATE=?,SECURITY_QUESTION1=?,SECURITY_ANSWER1=?,SECURITY_QUESTION2=?,SECURITY_ANSWER2=?,FORCED_PASSWORD_FLAG=?,ACCOUNT_STATUS=?,PASS_COUNT=?,");
			bufInsSql.append("MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" VALIDITY_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" WHERE USER_ID=?");

			
			if (CommonFunction.checkNull(userMasterVo.getUserName()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getUserName().toUpperCase().trim());

			if (CommonFunction.checkNull(userMasterVo.getEmpId()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getEmpId().trim());

			if (CommonFunction.checkNull(userMasterVo.getLbxDepartmentId()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getLbxDepartmentId().trim());

			if (CommonFunction.checkNull(userMasterVo.getLbxBranchId()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getLbxBranchId().trim());

			if (CommonFunction.checkNull(userMasterVo.getLbxDesignationId()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getLbxDesignationId());

			if (CommonFunction.checkNull(userMasterVo.getPhone1()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getPhone1().trim());

			if (CommonFunction.checkNull(userMasterVo.getPhone2()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getPhone2().trim());

			if (CommonFunction.checkNull(userMasterVo.getEmail()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getEmail().trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(stat);
			
			if (CommonFunction.checkNull(userMasterVo.getLbxReportingUser()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getLbxReportingUser());
			
			if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString(userMasterVo.getAllselection()[0]);

			if (userMasterVo.getSelection().length==0)
				updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString(userMasterVo.getSelection()[0]);
						
			if (CommonFunction.checkNull(data.get(0)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(0)));
			
			if (CommonFunction.checkNull(data.get(1)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(1)));
			
			if (CommonFunction.checkNull(data.get(2)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(2)));
			
			if (CommonFunction.checkNull(data.get(3)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(3)));
			
			if (CommonFunction.checkNull(data.get(4)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(4)));

			if (CommonFunction.checkNull(data.get(5)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(5)));
			
			if (CommonFunction.checkNull(data.get(6)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(6)));
			
			if (CommonFunction.checkNull(data.get(7)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(7)));
			
			if (CommonFunction.checkNull(data.get(8)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(8)));
			
			if (CommonFunction.checkNull(data.get(9)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(9)));
			
			if (CommonFunction.checkNull(data.get(10)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(10)));
			
			if (CommonFunction.checkNull(data.get(11)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(11)));
			
			if (CommonFunction.checkNull(data.get(12)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(12)));
			
			if (CommonFunction.checkNull(data.get(13)).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(data.get(13)));
			
			if (CommonFunction.checkNull(userMasterVo.getMakerId()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getMakerId());

			if (CommonFunction.checkNull(userMasterVo.getMakerDate()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getMakerDate());
			
			if (CommonFunction.checkNull(userMasterVo.getValidityDate()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getValidityDate());
			
			if (CommonFunction.checkNull(userMasterVo.getUserId()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(userMasterVo.getUserId().toUpperCase().trim());
			
			
			updatePrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN updateUserData() update query1 ### "+ updatePrepStmtObject.printQuery());
			qryList.add(updatePrepStmtObject);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				
		
		}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		detailList=null;
		qryList=null;
		updatePrepStmtObject=null;
		bufInsSql=null;
		data=null;
	}
	
	logger.info("In updateUserData......................"	+ status);
	
	return status;
	}
	

	
	
	public int counthieirarchyusers(UserMasterVo vo) {
		int countUser=0;
		String countusers ="";
		try {
			logger.info("In counthieirarchyusers..........................MasterDAOImpl");
			String query = "SELECT count(1) FROM user_group_reporting WHERE REPORTING_TO='"+vo.getUserId()+"' AND REC_STATUS='A'";
			
			logger.info("In counthieirarchyusers...........query.............."+query);
			countusers = ConnectionDAOforEJB.singleReturn(query);
			countUser=Integer.parseInt(countusers);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return countUser;
	}
	
	
	 public String updateUserPassword(Object ob) {
		UserMasterVo userMasterVo = (UserMasterVo) ob;
		String userId = (String) userMasterVo.getUserId();
		ArrayList updatelist = new ArrayList();
		ArrayList updatePasslist = new ArrayList();
		String updPassword = null;
		boolean status = false;
		String result="";

		final String charset = "0123456789abcdefghijklmnopqrstuvwxyz";

		try {

			int length = 8;
			Random rand = new Random(System.currentTimeMillis());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length; i++) {
				int pos = rand.nextInt(charset.length());
				sb.append(charset.charAt(pos));
			}

			updPassword = sb.toString();
			logger.info("In updateUserPassword()updPassword:: "+ updPassword);

			String passQuery = "SELECT USER_PASSWORD,USER_LAST_PASSWORD_1,USER_LAST_PASSWORD_2,USER_LAST_PASSWORD_3,USER_LAST_PASSWORD_4,USER_LAST_PASSWORD_5 FROM SEC_USER_M WHERE USER_ID='"+ userId + "'";
			logger.info("passQuery  :  " + passQuery);
			updatePasslist = ConnectionDAOforEJB.sqlSelect(passQuery);
			logger.info("updateUserPass : " + updatePasslist.size());

			for (int i = 0; i < updatePasslist.size(); i++) 
			{
				ArrayList data = (ArrayList) updatePasslist.get(i);
				if (data.size() > 0) 
				{
					String userPass = (CommonFunction.checkNull(data.get(0)).toString());
					String userPass1 = (CommonFunction.checkNull(data.get(1)).toString());
					String userPass2 = (CommonFunction.checkNull(data.get(2)).toString());
					String userPass3 = (CommonFunction.checkNull(data.get(3)).toString());
					String userPass4 = (CommonFunction.checkNull(data.get(4)).toString());
					String userPass5 = (CommonFunction.checkNull(data.get(5)).toString());
					userMasterVo.setPassword(updPassword);
					String pass = md5.en(updPassword);
					logger.info("Incript Password   :  " + pass);
					StringBuilder query= new StringBuilder();
					query.append("UPDATE SEC_USER_M SET FORCED_PASSWORD_FLAG='Y',LAST_PASSWORD_DATE=");
					query.append(dbo);
					query.append("sysdate()");
					query.append(",USER_LAST_PASSWORD_1='"+userPass+"'");
					query.append(",USER_LAST_PASSWORD_2='"+userPass1+"'");
					query.append(",USER_LAST_PASSWORD_3='"+userPass2+"'");
					query.append(",USER_LAST_PASSWORD_4='"+userPass3+"'");
					query.append(",USER_LAST_PASSWORD_5='"+ userPass4+"'");
					query.append(",USER_PASSWORD='"+pass+"'");
					query.append(", ACCOUNT_STATUS='U' WHERE USER_ID='"+userId+"'");
//					String query = "UPDATE SEC_USER_M SET FORCED_PASSWORD_FLAG='Y',LAST_PASSWORD_DATE=sysdate()" +
//							",USER_LAST_PASSWORD_1='"
//							+ userPass
//							+ "',USER_LAST_PASSWORD_2='"
//							+ userPass1
//							+ "',USER_LAST_PASSWORD_3='"
//							+ userPass2
//							+ "',USER_LAST_PASSWORD_4='"
//							+ userPass3
//							+ "',USER_LAST_PASSWORD_5='"
//							+ userPass4
//							+ "',USER_PASSWORD='"
//							+ pass
//							+ "', ACCOUNT_STATUS='U' WHERE USER_ID='"
//							+ userId + "'";
					logger.info("Update password query  :  " + query.toString());
					updatelist.add(query.toString());
					status = ConnectionDAOforEJB.sqlInsUpdDelete(updatelist);
					logger.info("Update status  :  "+status);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(status)
			result=updPassword;
		return result;
	}

	public boolean insertUserBranch(Object ob, String[] branch) {
		UserMasterVo userMasterVo = (UserMasterVo) ob;
		boolean status = false;
		logger.info("In insertUserBranch.........."
				+ userMasterVo.getUserStatus());
		ArrayList qryList = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = null;
		String stat = "X";
		try{
		Connection con=ConnectionDAOforEJB.getConnection();
		try {
			String query = "SELECT USER_ID FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"
					+ userMasterVo.getUserId().trim() + "' ";
			logger
					.info("In insertUserMaster.....................................Dao Impl"
							+ query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);

			if (!st) {
				if (userMasterVo.getUserStatus() != null
						&& userMasterVo.getUserStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert User Master ");
				if(userMasterVo.getAllselection()[0].equalsIgnoreCase("S"))
				{
				for (int i = 0; i < branch.length; i++) {
					insertPrepStmtObject = new PrepStmtObject();
					
					bufInsSql.append("INSERT INTO SEC_USER_BRANCH_DTL(USER_ID,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					if (CommonFunction.checkNull(userMasterVo.getUserId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo.getUserId()
								.toUpperCase());

					if (CommonFunction.checkNull(branch[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(branch[i]);

					/*if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);
					*/
					
					if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);

					if (CommonFunction.checkNull(userMasterVo.getMakerId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo
								.getMakerId());

					if (CommonFunction.checkNull(userMasterVo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo
								.getMakerDate());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertUserMaster() insert query1 ### "
							+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
						

				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertUserMaster...S..................."	+ status);
				}
				else
				{
				String DeleteBranchQuery="delete from SEC_USER_BRANCH_DTL where USER_ID='"+userMasterVo.getUserId() +"'";
				logger.info("Delete Query for New User Against Branch Id:"+DeleteBranchQuery);
				
				Statement st1=con.createStatement();
				int a=st1.executeUpdate(DeleteBranchQuery)	;
				ArrayList updateList=new ArrayList();
				StringBuilder branchQuery = new StringBuilder();
				branchQuery.append("select BRANCH_ID from com_branch_m where ISNULL(REC_STATUS,'A')='A'");
				ArrayList <Object> branchId=ConnectionDAOforEJB.sqlSelect(branchQuery.toString());
				logger.info("Branch query : " + branchQuery.toString());
				if(branchId.size()>0)
				{
				
					for (int i = 0; i < branchId.size(); i++) {
						insertPrepStmtObject = new PrepStmtObject();
						 bufInsSql = new StringBuffer();
						bufInsSql.append("INSERT INTO SEC_USER_BRANCH_DTL(USER_ID,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
												
						if (CommonFunction.checkNull(userMasterVo.getUserId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userMasterVo.getUserId()
									.toUpperCase());

						if (CommonFunction.checkNull(((ArrayList<Object>) branchId.get(i)).get(0)).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(((ArrayList<Object>) branchId.get(i)).get(0).toString());
						
						/*
						if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);*/
						
						
						
						if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(stat);

						if (CommonFunction.checkNull(userMasterVo.getMakerId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userMasterVo
									.getMakerId());

						if (CommonFunction.checkNull(userMasterVo.getMakerDate())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userMasterVo
									.getMakerDate());

						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN insertUserMaster() insert query1 in all case ### "
								+ insertPrepStmtObject.printQuery());
						updateList.add(insertPrepStmtObject);
					}
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updateList);
					logger.info("In insertUserMaster......................"	+ status);
					
				}
				
				}
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	public boolean insertUserLevel(Object ob, String[] levelName) {
	
		boolean status = false;
		UserMasterVo userMasterVo=(UserMasterVo)ob;
		logger.info("In insertUserLevel.........."+ userMasterVo.getUserStatus());
		ArrayList qryList = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = null;
		String stat = "X";
		try{
		Connection con=ConnectionDAOforEJB.getConnection();
		try {
			String query = "SELECT USER_ID FROM sec_user_level_dtl WHERE USER_ID='"+ userMasterVo.getUserId().trim() + "' ";
			logger.info("In insertUserLevel.....................................Dao Impl"+ query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);

			if (!st) {
				if (userMasterVo.getUserStatus() != null&& userMasterVo.getUserStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In 111111111111111111111----insertUserLevel ");
				if(userMasterVo.getSelection()[0].equalsIgnoreCase("S"))
				{
				for (int i = 0; i < levelName.length; i++) {
					insertPrepStmtObject = new PrepStmtObject();
					
					bufInsSql.append("INSERT INTO sec_user_level_dtl(USER_ID,LEVEL_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
					if (CommonFunction.checkNull(userMasterVo.getUserId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo.getUserId()
								.toUpperCase());

					if (CommonFunction.checkNull(levelName[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(levelName[i]);

					/*if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);
					*/
					
					if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);

					if (CommonFunction.checkNull(userMasterVo.getMakerId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo
								.getMakerId());

					if (CommonFunction.checkNull(userMasterVo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo
								.getMakerDate());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertUserLevel() insert query1 ### "
							+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
						

				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertUserLevel...S..................."	+ status);
				}
			
				else
			{
					logger.info("222222222222222222222222222222");
//			String DeleteBranchQuery="delete from sec_user_level_dtl where USER_ID='"+userMasterVo.getUserId() +"'";
//			logger.info("Delete Query for New User Against level Id:"+DeleteBranchQuery);
//				
//				Statement st1=con.createStatement();
//				int a=st1.executeUpdate(DeleteBranchQuery)	;
				ArrayList updateList=new ArrayList();
				
				ArrayList <Object> levelId=ConnectionDAOforEJB.sqlSelect("select VALUE from generic_master where GENERIC_KEY='LEVELS' and REC_STATUS='A'");
				if(levelId.size()>0)
				{
					for (int i = 0; i < levelId.size(); i++) {
						insertPrepStmtObject = new PrepStmtObject();
						 bufInsSql = new StringBuffer();
						bufInsSql.append("INSERT INTO sec_user_level_dtl(USER_ID,LEVEL_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
						
						if (CommonFunction.checkNull(userMasterVo.getUserId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userMasterVo.getUserId()
									.toUpperCase());

						if (CommonFunction.checkNull(((ArrayList<Object>) levelId.get(i)).get(0).toString()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(((ArrayList<Object>) levelId.get(i)).get(0).toString());

						
						if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(stat);

						if (CommonFunction.checkNull(userMasterVo.getMakerId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userMasterVo
									.getMakerId());

						if (CommonFunction.checkNull(userMasterVo.getMakerDate())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userMasterVo
									.getMakerDate());

						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN insertUserLevel() insert query1 in all case ### "
								+ insertPrepStmtObject.printQuery());
						updateList.add(insertPrepStmtObject);
					}
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updateList);
					logger.info("In insertUserLevel......................"	+ status);
					
				}
				
			}
		} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	
	public ArrayList<UserMasterVo> searchUserBranchEdit(String userId) {

		ArrayList searchlist = new ArrayList();
		UserMasterVo userMasterVo = new UserMasterVo();
		ArrayList<UserMasterVo> branchDescList = new ArrayList<UserMasterVo>();

		try {
			logger
					.info("In searchUserBranchEdit().....................................Dao Impl");

			StringBuffer bufInsSql = new StringBuffer();
			// bufInsSql.append("SELECT B.BRANCH_ID,B.BRANCH_DESC  FROM SEC_USER_BRANCH_DTL A, COM_BRANCH_M B WHERE A.BRANCH_ID=B.BRANCH_ID AND USER_ID='"+userId+"'");
			bufInsSql.append("select com_branch_m.BRANCH_ID ,com_branch_m.BRANCH_DESC,s.BRANCH_ACCESS " +
						" from com_branch_m inner join sec_user_branch_dtl SUBD on com_branch_m.BRANCH_ID = SUBD.BRANCH_ID " +
						" join sec_user_m S on s.user_id=SUBD.USER_ID WHERE SUBD.USER_ID='"+userId+"' ");
			
			
//			bufInsSql.append("select com_branch_m.BRANCH_ID ,com_branch_m.BRANCH_DESC , " +
//					" sec_user_branch_dtl.BRANCH_ACCESS from com_branch_m inner join" +
//					" sec_user_branch_dtl on com_branch_m.BRANCH_ID = sec_user_branch_dtl.BRANCH_ID WHERE USER_ID='"+userId+"'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN searchUserBranchEdit() search query1 ### "
					+ bufInsSql.toString());
			logger.info("searchUserBranchEdit " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger
						.info("branchDescList " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					UserMasterVo userMVo = new UserMasterVo();
					userMVo.setBranchId(CommonFunction.checkNull(data.get(0)).toString());
					userMVo.setBranchDesc(CommonFunction.checkNull(data.get(1)).toString());
					userMVo.setSelectionAccess(CommonFunction.checkNull(data.get(2)).toString());
					branchDescList.add(userMVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return branchDescList;
	}
	
	public ArrayList<UserMasterVo> searchUserLevelEdit(String userId) {

		ArrayList searchlist = new ArrayList();
		UserMasterVo userMasterVo = new UserMasterVo();
		ArrayList<UserMasterVo> levelDescList = new ArrayList<UserMasterVo>();

		try {
			logger
					.info("In searchUserLevelEdit().....................................Dao Impl");

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("select GM.VALUE,GM.DESCRIPTION,S.LEVEL_ACCESS from generic_master GM  inner join sec_user_level_dtl SULD on SULD.LEVEL_ID = GM.VALUE " +
					"join sec_user_m S on s.user_id=SULD.USER_ID WHERE GM.GENERIC_KEY='LEVELS' AND SULD.USER_ID='"+userId+"' ");
			

			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN searchUserLevelEdit() search query1 ### "
					+ bufInsSql.toString());
			logger.info("searchUserLevelEdit " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger
						.info("branchDescList " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					UserMasterVo userMVo = new UserMasterVo();
					userMVo.setLevelID(CommonFunction.checkNull(data.get(0)).toString());
					userMVo.setLevelDesc(CommonFunction.checkNull(data.get(1)).toString());
					userMVo.setLevelAccess(CommonFunction.checkNull(data.get(2)).toString());
					levelDescList.add(userMVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return levelDescList;
	}
	public boolean updateUserData1(Object ob, String[] branch) {
		
		logger.info("In updateUserData1 for inserting in SEC_USER_BRANCH_DTL ");
		UserMasterVo userMasterVo = (UserMasterVo) ob;
		String userId = userMasterVo.getUserId();
		
		ArrayList updatelist = new ArrayList();
		
		PrepStmtObject insertPrepStmtObject = null;
		// PrepStmtObject insertPrepreObj=new PrepStmtObject();
		boolean status = false;
		String stat = "";
		try {

//			qryList = new ArrayList();
//			insertList = new ArrayList();
//			// String stat="X";
//			String query = "DELETE FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"+ userId + "'";
//			logger.info("In insertUserBranchMaster.....................................Dao Impl"+ query);
//
//			qryList.add(query);
//			status = ConnectionDAOforEJB.sqlInsUpdDelete(qryList);

			if (userMasterVo.getUserStatus() != null
					&& userMasterVo.getUserStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
if(userMasterVo.getAllselection()[0].equalsIgnoreCase("S"))
{
			for (int i = 0; i < branch.length; i++) {

				insertPrepStmtObject = new PrepStmtObject();
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO SEC_USER_BRANCH_DTL(USER_ID,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				if (CommonFunction.checkNull(userMasterVo.getUserId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getUserId()
							.toUpperCase());

				if (CommonFunction.checkNull(branch[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branch[i]);
				
			/*	if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);
				*/
				

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);
				//----------------------------------
				if (CommonFunction.checkNull(userMasterVo.getMakerId()).equalsIgnoreCase(
				""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getMakerId());
				if (CommonFunction.checkNull(userMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getMakerDate());
				//----------------------------------

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertUserMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				updatelist.add(insertPrepStmtObject);
			}
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			logger.info("In insertUserMaster......................" + status);
}
else
{
	StringBuilder branchQuery = new StringBuilder();
	branchQuery.append("select BRANCH_ID from com_branch_m where ISNULL(REC_STATUS,'A')='A'");
	ArrayList <Object> branchId=ConnectionDAOforEJB.sqlSelect(branchQuery.toString());
	logger.info("Branch query in updateUserData1: " + branchQuery.toString());
	
	if(branchId.size()>0)
	{
		for (int i = 0; i < branchId.size(); i++) {

			insertPrepStmtObject = new PrepStmtObject();
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("INSERT INTO SEC_USER_BRANCH_DTL(USER_ID,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			if (CommonFunction.checkNull(userMasterVo.getUserId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userMasterVo.getUserId()
						.toUpperCase());
			
			if (CommonFunction.checkNull(((ArrayList<Object>) branchId.get(i)).get(0)).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(((ArrayList<Object>) branchId.get(i)).get(0).toString());
			
			
		/*	if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);*/
			

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			if (CommonFunction.checkNull(userMasterVo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userMasterVo.getMakerId());
			if (CommonFunction.checkNull(userMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userMasterVo.getMakerDate());
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN insertUserMaster() insert query1 ### "	+ insertPrepStmtObject.printQuery());
			updatelist.add(insertPrepStmtObject);
		}
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
		logger.info("In insertUserMaster......................" + status);
	}
	
	
}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

   public boolean updateUserLevel(Object ob, String[] level) {
	   
	   
	   logger.info("In  updateUserLevel..");
		
		UserMasterVo userMasterVo=(UserMasterVo)ob;
		String userId = userMasterVo.getUserId();
		
		ArrayList updatelist = new ArrayList();
		
		PrepStmtObject insertPrepStmtObject = null;
		// PrepStmtObject insertPrepreObj=new PrepStmtObject();
		boolean status = false;
		String stat = "";
		try {

//			qryList = new ArrayList();
//			insertList = new ArrayList();
//			// String stat="X";
//			String query = "DELETE FROM sec_user_level_dtl WHERE USER_ID='"	+ userId + "'";
//			logger.info("In updateUserLevel.....................................Dao Impl"+ query);
//
//			qryList.add(query);
//			status = ConnectionDAOforEJB.sqlInsUpdDelete(qryList);

			if (userMasterVo.getUserStatus() != null
					&& userMasterVo.getUserStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			if(userMasterVo.getSelection()[0].equalsIgnoreCase("S"))
			{
			for (int i = 0; i < level.length; i++) {
				insertPrepStmtObject = new PrepStmtObject();
				StringBuffer bufInsSql = new StringBuffer();
				
				bufInsSql.append("INSERT INTO sec_user_level_dtl(USER_ID,LEVEL_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				if (CommonFunction.checkNull(userMasterVo.getUserId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getUserId()
							.toUpperCase());

				if (CommonFunction.checkNull(level[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(level[i]);

				/*if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);
				*/
				
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(userMasterVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getMakerId());

				if (CommonFunction.checkNull(userMasterVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				//logger.info("IN updateUserLevel() insert query1 ### "+ insertPrepStmtObject.printQuery());
				updatelist.add(insertPrepStmtObject);
			
			}
					

			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			logger.info("In updateUserLevel...S..................."	+ status);
			}
			else
			{

	ArrayList <Object> levelId=ConnectionDAOforEJB.sqlSelect("select VALUE from generic_master where GENERIC_KEY='LEVELS' and REC_STATUS='A'");
	if(levelId.size()>0)
	{
		for (int i = 0; i < levelId.size(); i++) {
			insertPrepStmtObject = new PrepStmtObject();
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("INSERT INTO sec_user_level_dtl(USER_ID,LEVEL_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			if (CommonFunction.checkNull(userMasterVo.getUserId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userMasterVo.getUserId()
						.toUpperCase());

			if (CommonFunction.checkNull(((ArrayList<Object>) levelId.get(i)).get(0).toString()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(((ArrayList<Object>) levelId.get(i)).get(0).toString());

			
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			if (CommonFunction.checkNull(userMasterVo.getMakerId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userMasterVo
						.getMakerId());

			if (CommonFunction.checkNull(userMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userMasterVo
						.getMakerDate());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			//logger.info("IN updateUserLevel() insert query1 in all case ### "+ insertPrepStmtObject.printQuery());
			updatelist.add(insertPrepStmtObject);
		}
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
		logger.info("In updateUserLevel......................"	+ status);
		
	}
	
	
}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// Code End for User Master by Ritu

	// Code Start For User Branch Master by Ritu
	public boolean insertUserBranchMaster(Object ob, String[] branch) {
		UserBranchMasterVo userBranchMasterVo = (UserBranchMasterVo) ob;
		boolean status = false;
		logger.info("In insertUserBranchMaster.........."
				+ userBranchMasterVo.getUserBranchStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = null;
		String stat = "X";
		try {

			String query = "SELECT USER_ID FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"
					+ userBranchMasterVo.getLbxUserId().trim() + "' ";
			logger
					.info("In insertUserBranchMaster.....................................Dao Impl"
							+ query);
			boolean st = ConnectionDAO.checkStatus(query);

			if (!st) {
				if (userBranchMasterVo.getUserBranchStatus() != null
						&& userBranchMasterVo.getUserBranchStatus()
								.equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert User Branch Master ");

				for (int i = 0; i < branch.length; i++) {

					insertPrepStmtObject = new PrepStmtObject();
					StringBuffer bufInsSql = new StringBuffer();
					bufInsSql
							.append("INSERT INTO SEC_USER_BRANCH_DTL(USER_ID,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							
					if (CommonFunction.checkNull(
							userBranchMasterVo.getLbxUserId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userBranchMasterVo
								.getLbxUserId().toUpperCase());

					if (CommonFunction.checkNull(branch[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(branch[i]);

					if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);

					if (CommonFunction.checkNull(
							userBranchMasterVo.getMakerId()).equalsIgnoreCase(
							""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userBranchMasterVo
								.getMakerId());

					if (CommonFunction.checkNull(
							userBranchMasterVo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userBranchMasterVo
								.getMakerDate());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger
							.info("IN insertUserBranchMaster() insert query1 ### "
									+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger
						.info("In insertUserBranchMaster......................"
								+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public ArrayList<UserBranchMasterVo> getUserBranchData() {
		ArrayList list = new ArrayList();
		ArrayList<UserBranchMasterVo> detailList = new ArrayList<UserBranchMasterVo>();

		try {
			logger.info("In getUserBranchData()..............Dao Impl");
			String query = ("SELECT USER_ID,(SELECT A.USER_NAME FROM SEC_USER_M A WHERE A.USER_ID=V.USER_ID ) AS USRE_DESC,"
					+ "BRANCH_ID,(SELECT B.BRANCH_DESC FROM COM_BRANCH_M B WHERE BRANCH_ID=V.BRANCH_ID ) AS BRANCH_DESC,"
					+ " case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status FROM SEC_USER_BRANCH_DTL V ORDER BY USER_ID");
			list = ConnectionDAO.sqlSelect(query);
			logger.info("getUserBranchData " + list.size());

			for (int i = 0; i < list.size(); i++) {
				logger.info("getUserBranchDataList "
						+ list.get(i).toString());

				ArrayList data = (ArrayList) list.get(i);

				if (data.size() > 0) {
					UserBranchMasterVo userBranchMasterVo = new UserBranchMasterVo();
					userBranchMasterVo.setLbxUserId(CommonFunction.checkNull(
							data.get(0)).toString());
					userBranchMasterVo.setUserId(CommonFunction.checkNull(
							data.get(1)).toString());
					userBranchMasterVo.setLbxBranchId(CommonFunction.checkNull(
							data.get(2)).toString());
					userBranchMasterVo.setBranchId(CommonFunction.checkNull(
							data.get(3)).toString());
					userBranchMasterVo.setUserBranchStatus(CommonFunction
							.checkNull(data.get(4)).toString());
					detailList.add(userBranchMasterVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}

	public ArrayList<UserBranchMasterVo> searchUserBranchData(Object ob) {
		String userId = "";
		String branchId = "";
		ArrayList searchlist = new ArrayList();
		UserBranchMasterVo userBranchMasterVo = (UserBranchMasterVo) ob;
		ArrayList<UserBranchMasterVo> detailList = new ArrayList<UserBranchMasterVo>();
		try {
			logger
					.info("In searchUserBranchData().....................................Dao Impl");
			userId = (StringEscapeUtils
					.escapeSql(CommonFunction.checkNull(userBranchMasterVo.getLbxUserSearchId())).trim());
			branchId = (StringEscapeUtils
					.escapeSql(CommonFunction.checkNull(userBranchMasterVo.getLbxBranchSearchId())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql
					.append("SELECT USER_ID,(SELECT A.USER_NAME FROM SEC_USER_M A WHERE A.USER_ID=V.USER_ID ) AS USRE_DESC,"
							+ "BRANCH_ID,(SELECT B.BRANCH_DESC FROM COM_BRANCH_M B WHERE BRANCH_ID=V.BRANCH_ID ) AS BRANCH_DESC,"
							+ " case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status FROM SEC_USER_BRANCH_DTL V ");

			if ((!(userId.equalsIgnoreCase("")))
					&& (!(branchId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_ID='" + userId
						+ "' AND BRANCH_ID='" + branchId + "'");
			}

			if (!userId.equals("")) {
				bufInsSql.append(" WHERE USER_ID='" + userId + "' ");
			}

			if (!branchId.equals("")) {
				bufInsSql.append(" WHERE BRANCH_ID = '" + branchId + "' ");
			}
			bufInsSql.append(" ORDER BY USER_ID");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("IN searchUserBranchData() search query1 ### "
					+ bufInsSql.toString());
			logger.info("searchUserBranchData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchUserBranchDataList "
						+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					UserBranchMasterVo userBranchMVo = new UserBranchMasterVo();

					userBranchMVo
							.setUserIdModify("<a href=userBranchMasterSearch.do?method=openEditUserBranch&lbxUserSearchId="
									+ CommonFunction.checkNull(data.get(0))
											.toString()
									+ "&lbxBranchId="
									+ CommonFunction.checkNull(data.get(2))
											.toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(1))
											.toString() + "</a>");

					userBranchMVo.setLbxUserId(CommonFunction.checkNull(
							data.get(0)).toString());
					userBranchMVo.setUserId(CommonFunction.checkNull(
							data.get(1)).toString());
					userBranchMVo.setLbxBranchId(CommonFunction.checkNull(
							data.get(2)).toString());
					userBranchMVo.setBranchId(CommonFunction.checkNull(
							data.get(3)).toString());
					userBranchMVo.setUserBranchStatus(CommonFunction.checkNull(
							data.get(4)).toString());
					detailList.add(userBranchMVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	public ArrayList<UserBranchMasterVo> searchUserBranch() {
		ArrayList searchlist = new ArrayList();
		ArrayList<UserBranchMasterVo> branchlList = new ArrayList<UserBranchMasterVo>();

		try {
			logger
					.info("In searchUserBranch().....................................Dao Impl");

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("SELECT BRANCH_ID,BRANCH_DESC FROM COM_BRANCH_M");

			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("IN searchUserBranch() search query1 ### "
					+ bufInsSql.toString());
			logger.info("searchUserBranch " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchUserBranchList "
						+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					UserBranchMasterVo userBranchMVo = new UserBranchMasterVo();
					userBranchMVo.setBranchId(CommonFunction.checkNull(
							data.get(0)).toString());
					userBranchMVo.setBranchDesc(CommonFunction.checkNull(
							data.get(1)).toString());

					branchlList.add(userBranchMVo);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return branchlList;
	}

	public ArrayList<UserBranchMasterVo> searchUserBranchDescEdit(String userId) {

		ArrayList searchlist = new ArrayList();
		UserBranchMasterVo userBranchMasterVo = new UserBranchMasterVo();
		ArrayList<UserBranchMasterVo> branchDescListEdit = new ArrayList<UserBranchMasterVo>();

		try {
			logger
					.info("In searchUserBranch().....................................Dao Impl");

			StringBuffer bufInsSql = new StringBuffer();
			// bufInsSql.append("SELECT B.BRANCH_ID,B.BRANCH_DESC  FROM SEC_USER_BRANCH_DTL A, COM_BRANCH_M B WHERE A.BRANCH_ID=B.BRANCH_ID AND USER_ID='"+userId+"'");
			bufInsSql
					.append(" SELECT BRANCH_ID,BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID IN(SELECT BRANCH_ID FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"
							+ userId + "')");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("IN searchUserBranchDesc() search query1 ### "
					+ bufInsSql.toString());
			logger.info("searchUserBranchDesc " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchUserBranchDescList "
						+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					UserBranchMasterVo userBranchMVo = new UserBranchMasterVo();
					userBranchMVo.setBranchId(CommonFunction.checkNull(
							data.get(0)).toString());
					userBranchMVo.setBranchDesc(CommonFunction.checkNull(
							data.get(1)).toString());
					branchDescListEdit.add(userBranchMVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return branchDescListEdit;
	}

	public ArrayList<UserBranchMasterVo> searchUserBranchDesc(String userId) {

		ArrayList searchlist = new ArrayList();
		UserBranchMasterVo userBranchMasterVo = new UserBranchMasterVo();
		ArrayList<UserBranchMasterVo> branchDescList = new ArrayList<UserBranchMasterVo>();

		try {
			logger
					.info("In searchUserBranch().....................................Dao Impl");

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql
					.append("SELECT B.BRANCH_ID,B.BRANCH_DESC  FROM SEC_USER_BRANCH_DTL A, COM_BRANCH_M B WHERE A.BRANCH_ID=B.BRANCH_ID AND USER_ID='"
							+ userId + "'");
			// bufInsSql.append(" SELECT BRANCH_ID,BRANCH_DESC FROM COM_BRANCH_M AND BRANCH_ID NOT IN(SELECT BRANCH_ID FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"+userId+"')");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("IN searchUserBranchDesc() search query1 ### "
					+ bufInsSql.toString());
			logger.info("searchUserBranchDesc " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchUserBranchDescList "
						+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					UserBranchMasterVo userBranchMVo = new UserBranchMasterVo();
					userBranchMVo.setBranchId(CommonFunction.checkNull(
							data.get(0)).toString());
					userBranchMVo.setBranchDesc(CommonFunction.checkNull(
							data.get(1)).toString());
					branchDescList.add(userBranchMVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return branchDescList;
	}

	public boolean updateUserBranchData(Object ob, String[] branch) {
		UserBranchMasterVo userBranchMasterVo = (UserBranchMasterVo) ob;
		String userId = userBranchMasterVo.getLbxUserId();
		logger.info("user id " + userId);
		logger.info("vo.getUserBranchStatus():-"
				+ userBranchMasterVo.getUserBranchStatus());
		ArrayList updatelist = new ArrayList();
		ArrayList qryList = null;
		ArrayList insertList = null;
		PrepStmtObject insertPrepStmtObject = null;
		// PrepStmtObject insertPrepreObj=new PrepStmtObject();
		boolean status = false;
		String stat = "";
		try {

			qryList = new ArrayList();
			insertList = new ArrayList();
			// String stat="X";
			String query = "DELETE FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"
					+ userId + "'";
			logger
					.info("In insertUserBranchMaster.....................................Dao Impl"
							+ query);

			qryList.add(query);
			status = ConnectionDAO.sqlInsUpdDelete(qryList);

			if (userBranchMasterVo.getUserBranchStatus() != null
					&& userBranchMasterVo.getUserBranchStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}

			for (int i = 0; i < branch.length; i++) {

				insertPrepStmtObject = new PrepStmtObject();
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("INSERT INTO SEC_USER_BRANCH_DTL(USER_ID,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							
				if (CommonFunction.checkNull(userBranchMasterVo.getLbxUserId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userBranchMasterVo
							.getLbxUserId().toUpperCase());

				if (CommonFunction.checkNull(branch[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branch[i]);

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);
				//----------------------------------
				if (CommonFunction.checkNull(userBranchMasterVo.getMakerId()).equalsIgnoreCase(
				""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userBranchMasterVo.getMakerId());
				if (CommonFunction.checkNull(userBranchMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userBranchMasterVo.getMakerDate());
				//----------------------------------

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertUserBranchMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				insertList.add(insertPrepStmtObject);
			}
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(insertList);
			logger.info("In insertUserBranchMaster......................"
					+ status);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// Code End By Ritu For User Branch Master

	// Code Start For User Access Master by Ritu
	public boolean insertUserAccessMaster(Object ob) {
		UserAccessMasterVo userAccessMasterVo = (UserAccessMasterVo) ob;
		boolean status = false;
		logger.info("In insertUserAccessMaster.........."
				+ userAccessMasterVo.getUserAccessStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "SELECT USER_ID,MODULE_ID FROM SEC_USER_ACCESS_M WHERE USER_ID='"
				+ userAccessMasterVo.getLbxUserId().trim()
				+ "' AND MODULE_ID='"
				+ userAccessMasterVo.getLbxModule().trim() + "'";
		logger
				.info("In insertUserAccessMaster.....................................Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (userAccessMasterVo.getUserAccessStatus() != null
						&& userAccessMasterVo.getUserAccessStatus()
								.equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert User Access Master ");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO SEC_USER_ACCESS_M(USER_ID,ROLE_ID,MODULE_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							
				if (CommonFunction.checkNull(userAccessMasterVo.getLbxUserId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userAccessMasterVo.getLbxUserId().toUpperCase());

				if (CommonFunction.checkNull(userAccessMasterVo.getLbxRoleId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userAccessMasterVo.getLbxRoleId());

				if (CommonFunction.checkNull(userAccessMasterVo.getLbxModule()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userAccessMasterVo.getLbxModule().toUpperCase());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(userAccessMasterVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userAccessMasterVo.getMakerId());

				if (CommonFunction.checkNull(userAccessMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userAccessMasterVo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertUserAccessMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertUserAccessMaster......................"+ status);
			}

			
			else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public ArrayList<UserAccessMasterVo> getUserAccessData() {
		ArrayList list = new ArrayList();
		ArrayList<UserAccessMasterVo> detailList = new ArrayList<UserAccessMasterVo>();

		try {
			logger.info("In getUserAccessData()..............Dao Impl");
			String query = ("SELECT USERACCESS_ID,USER_ID,(SELECT A.USER_NAME FROM SEC_USER_M A WHERE A.USER_ID=V.USER_ID ) AS USRE_DESC,"
					+ "ROLE_ID,(SELECT B.ROLE_DESC FROM SEC_ROLE_M B WHERE ROLE_ID=V.ROLE_ID ) AS ROLE_DESC,"
					+ "MODULE_ID,(SELECT C.MODULE_DESC FROM SEC_MODULE_M C WHERE MODULE_ID=V.MODULE_ID ) AS MODULE_DESC,"
					+ " case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status FROM SEC_USER_ACCESS_M V ORDER BY USER_ID");
			list = ConnectionDAO.sqlSelect(query);
			logger.info("getUserAccessData " + list.size());

			for (int i = 0; i < list.size(); i++) {
				logger.info("getUserAccessDataList "
						+ list.get(i).toString());

				ArrayList data = (ArrayList) list.get(i);

				if (data.size() > 0) {
					UserAccessMasterVo userAccessMasterVo = new UserAccessMasterVo();
					userAccessMasterVo.setUserAccessId(CommonFunction
							.checkNull(data.get(0)).toString());
					userAccessMasterVo.setLbxUserId(CommonFunction.checkNull(
							data.get(1)).toString());
					userAccessMasterVo.setUserId(CommonFunction.checkNull(
							data.get(2)).toString());
					userAccessMasterVo.setLbxRoleId(CommonFunction.checkNull(
							data.get(3)).toString());
					userAccessMasterVo.setRoleId(CommonFunction.checkNull(
							data.get(4)).toString());
					userAccessMasterVo.setLbxModule(CommonFunction.checkNull(
							data.get(5)).toString());
					userAccessMasterVo.setModuleId(CommonFunction.checkNull(
							data.get(6)).toString());
					userAccessMasterVo.setUserAccessStatus(CommonFunction
							.checkNull(data.get(7)).toString());
					detailList.add(userAccessMasterVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}

	public ArrayList<UserAccessMasterVo> searchUserAccessData(Object ob) {
		String userId = "";
		String roleId = "";
		String moduleId = "";
		String userAccessId = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		UserAccessMasterVo userAccessMasterVo = (UserAccessMasterVo) ob;
		ArrayList<UserAccessMasterVo> detailList = new ArrayList<UserAccessMasterVo>();
		try {
			logger.info("In searchUserAccessData().....................................Dao Impl");
			userId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(userAccessMasterVo.getLbxUserSearchId())).trim());
			roleId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(userAccessMasterVo.getLbxRoleSearchId())).trim());
			moduleId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(userAccessMasterVo.getLbxModuleSearch())).trim());
			userAccessId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(userAccessMasterVo.getUserAccessId())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT USERACCESS_ID,USER_ID,(SELECT A.USER_NAME FROM SEC_USER_M A WHERE A.USER_ID=V.USER_ID ) AS USRE_DESC,");
			bufInsSql.append(" ROLE_ID,(SELECT B.ROLE_DESC FROM SEC_ROLE_M B WHERE ROLE_ID=V.ROLE_ID ) AS ROLE_DESC,");
			bufInsSql.append(" MODULE_ID,(SELECT C.MODULE_DESC FROM SEC_MODULE_M C WHERE MODULE_ID=V.MODULE_ID ) AS MODULE_DESC,");
			bufInsSql.append(" case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status ");
			bufInsSql.append(" FROM SEC_USER_ACCESS_M V ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM SEC_USER_ACCESS_M V ");

			if ((!(userId.equalsIgnoreCase(""))) && (!(moduleId.equalsIgnoreCase(""))) && (!(roleId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_ID='" + userId + "' AND MODULE_ID ='" + moduleId + "' AND ROLE_ID='" + roleId + "'");
				bufInsSqlTempCount.append(" WHERE USER_ID='" + userId + "' AND MODULE_ID ='" + moduleId + "' AND ROLE_ID='" + roleId + "'");
			}

			else if ((!(roleId.equalsIgnoreCase(""))) && (!(moduleId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE ROLE_ID='" + roleId + "' AND MODULE_ID='" + moduleId + "'");
				bufInsSqlTempCount.append(" WHERE ROLE_ID='" + roleId + "' AND MODULE_ID='" + moduleId + "'");
			}

			else if ((!(userId.equalsIgnoreCase(""))) && (!(roleId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_ID='" + userId + "' AND ROLE_ID='" + roleId + "' ");
				bufInsSqlTempCount.append(" WHERE USER_ID='" + userId + "' AND ROLE_ID='" + roleId + "' ");
			}

			else if ((!(userId.equalsIgnoreCase(""))) && (!(moduleId.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE USER_ID='" + userId + "' AND MODULE_ID='" + moduleId + "'");
				bufInsSqlTempCount.append(" WHERE USER_ID='" + userId + "' AND MODULE_ID='" + moduleId + "'");
			}

			else if (!userId.equals("")) {
				bufInsSql.append(" WHERE USER_ID='" + userId + "' ");
				bufInsSqlTempCount.append(" WHERE USER_ID='" + userId + "' ");
			}

			else if (!roleId.equals("")) {
				bufInsSql.append(" WHERE ROLE_ID='" + roleId + "'  ");
				bufInsSqlTempCount.append(" WHERE ROLE_ID='" + roleId + "'  ");
			}

			else if (!moduleId.equals("")) {
				bufInsSql.append(" WHERE MODULE_ID='" + moduleId + "' ");
				bufInsSqlTempCount.append(" WHERE MODULE_ID='" + moduleId + "' ");
			}

			else if (!userAccessId.equals("")) {
				bufInsSql.append(" WHERE USERACCESS_ID='" + userAccessId + "'  ");
				bufInsSqlTempCount.append(" WHERE USERACCESS_ID='" + userAccessId + "'  ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((userId.trim()==null && roleId.trim()==null && moduleId.trim()==null && userAccessId.trim()==null) || (userId.trim().equalsIgnoreCase("") && roleId.trim().equalsIgnoreCase("") && moduleId.trim().equalsIgnoreCase("") && userAccessId.trim().equalsIgnoreCase("")) || userAccessMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+userAccessMasterVo.getCurrentPageLink());
			if(userAccessMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (userAccessMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY USER_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
//			}
			logger.info("query : "+bufInsSql);
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchUserAccessData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchUserAccessData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					UserAccessMasterVo UserAccessMVo = new UserAccessMasterVo();

					UserAccessMVo.setUserAccessIdModify("<a href=userAccessMasterSearch.do?method=openEditUserAccess&userAccessId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					UserAccessMVo.setUserAccessId(CommonFunction.checkNull(data.get(0)).toString());
					UserAccessMVo.setLbxUserId(CommonFunction.checkNull(data.get(1)).toString());
					UserAccessMVo.setUserId(CommonFunction.checkNull(data.get(2)).toString());
					UserAccessMVo.setLbxRoleId(CommonFunction.checkNull(data.get(3)).toString());
					UserAccessMVo.setRoleId(CommonFunction.checkNull(data.get(4)).toString());
					UserAccessMVo.setLbxModule(CommonFunction.checkNull(data.get(5)).toString());
					UserAccessMVo.setModuleId(CommonFunction.checkNull(data.get(6)).toString());
					UserAccessMVo.setUserAccessStatus(CommonFunction.checkNull(data.get(7)).toString());
					UserAccessMVo.setTotalRecordSize(count);
					detailList.add(UserAccessMVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			UserAccessMasterVo UserAccessMVo = new UserAccessMasterVo();
//			UserAccessMVo.setTotalRecordSize(count);
//			detailList.add(UserAccessMVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}

		return detailList;
	}
                           
	public int countQuestion(Object ob){
		
				ChangePasswordMasterVo changePasswordMasterVo = (ChangePasswordMasterVo)ob;
				String userId=(String)changePasswordMasterVo.getLbxUserId();
				
				ArrayList updatelist=new ArrayList();
			
				int count=0;
		        
					try{
						
                          String query="SELECT count(1) FROM SEC_USER_M WHERE SECURITY_QUESTION1='"+changePasswordMasterVo.getQues1()+"' and SECURITY_QUESTION2='"+changePasswordMasterVo.getQues2()+"'" +
                          		"and SECURITY_QUESTION2='"+changePasswordMasterVo.getQues1()+"' and SECURITY_QUESTION2='"+changePasswordMasterVo.getQues1()+"' and USER_ID='"+userId+"'";   
						  logger.info("In countChangePassword......Dao Impl"+query);
						  String countStr = ConnectionDAOforEJB.singleReturn(query);
						  count = Integer.parseInt(countStr);
						 
						  
					}catch(Exception e){
						e.printStackTrace();
					}

					return count;
			}

							//Code End By Ritu For Change Password Master   

	public boolean updateUserAccessData(Object ob) {
		UserAccessMasterVo userAccessMasterVo = (UserAccessMasterVo) ob;
		String userId = (String) userAccessMasterVo.getLbxUserId();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		logger.info("vo.getUserAccessStatus():-"
				+ userAccessMasterVo.getUserAccessStatus());
		ArrayList updatelist = new ArrayList();
        String roleId=CommonFunction.checkNull(userAccessMasterVo.getLbxRoleId());
        String moduleId=CommonFunction.checkNull(userAccessMasterVo.getLbxModule());
		boolean status = false;
		String moduleStatus="A"; 
		String stat = "";
		try {
			if (userAccessMasterVo.getUserAccessStatus() != null
					&& userAccessMasterVo.getUserAccessStatus().equals("on")) {
				stat = "A";
				StringBuilder query=new StringBuilder();
				 query.append("SELECT ISNULL(rec_status,'X') FROM SEC_ROLE_M  WHERE ROLE_ID='"+roleId+"' AND MODULE_ID='"+moduleId+"' ");
				 logger.info("query.....IN updateUserAccessData"+query.toString());
				 moduleStatus=ConnectionDAOforEJB.singleReturn(query.toString());
				 logger.info("moduleStatus.......IN updateUserAccessData"+moduleStatus);
			} else {
				stat = "X";
			}
			
			
			
			if(CommonFunction.checkNull(moduleStatus).equalsIgnoreCase("A")){
			
			userAccessMasterVo.setModuleStatus("A");
			StringBuffer bufInsSql = new StringBuffer();
			
			logger.info("In updateUserAccessData...................Dao Impl roleId "+roleId+" moduleId "+moduleId+" userAccessID "+userAccessMasterVo.getUserAccessId());
			
			bufInsSql.append("UPDATE SEC_USER_ACCESS_M SET ROLE_ID=?,MODULE_ID=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append("WHERE USERACCESS_ID=?");

			if (CommonFunction.checkNull(userAccessMasterVo.getLbxRoleId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userAccessMasterVo
						.getLbxRoleId());

			if (CommonFunction.checkNull(userAccessMasterVo.getLbxModule())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userAccessMasterVo
						.getLbxModule().toUpperCase());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------
			if (CommonFunction.checkNull(userAccessMasterVo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userAccessMasterVo.getMakerId());
			if (CommonFunction.checkNull(userAccessMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userAccessMasterVo.getMakerDate());
			//----------------------------------

			if (CommonFunction.checkNull(userAccessMasterVo.getUserAccessId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userAccessMasterVo.getUserAccessId());

		/*	if (CommonFunction.checkNull(userAccessMasterVo.getLbxModule())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(userAccessMasterVo
						.getLbxModule().toUpperCase());
		 */
			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			
			logger.info("In updateUserAccessData " + bufInsSql.toString());
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			}else{
				userAccessMasterVo.setModuleStatus("X");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

                      
	// Code End By Ritu For User Access Master

	// Code For Change Password By Ritu

	public int countChangePassword(Object ob) {
		ChangePasswordMasterVo changePasswordMasterVo = (ChangePasswordMasterVo) ob;
		String userId = (String) changePasswordMasterVo.getLbxUserId();

		ArrayList updatelist = new ArrayList();

		int count = 0;

		try {

			String query = "SELECT count(USER_PASSWORD) FROM SEC_USER_M WHERE USER_PASSWORD='"
					+ md5.en(changePasswordMasterVo.getOldPassword())
					+ "' and USER_ID='" + userId + "'";
			logger.info("In countChangePassword......Dao Impl" + query);
			String countStr = ConnectionDAOforEJB.singleReturn(query);
			count = Integer.parseInt(countStr);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	public boolean updateChangePassword(Object ob) {
		ChangePasswordMasterVo changePasswordMasterVo = (ChangePasswordMasterVo) ob;
		String userId = (String) changePasswordMasterVo.getLbxUserId();

		ArrayList updatelist = new ArrayList();
		ArrayList updatePasslist = new ArrayList();
		boolean status = false;

		try {

			String query = "SELECT count(1) FROM SEC_USER_M WHERE USER_ID='"
					+ userId + "' AND (USER_PASSWORD='"
					+ md5.en(changePasswordMasterVo.getNewPassword())
					+ "' OR USER_LAST_PASSWORD_1='"
					+ md5.en(changePasswordMasterVo.getNewPassword())
					+ "' OR USER_LAST_PASSWORD_2='"
					+ md5.en(changePasswordMasterVo.getNewPassword())
					+ "'OR USER_LAST_PASSWORD_3='"
					+ md5.en(changePasswordMasterVo.getNewPassword())
					+ "' OR USER_LAST_PASSWORD_4='"
					+ md5.en(changePasswordMasterVo.getNewPassword())
					+ "' OR USER_LAST_PASSWORD_5='"
					+ md5.en(changePasswordMasterVo.getNewPassword()) + "')";
			logger.info("In updateUserAccessData......Dao Impl" + query);
			String countStr = ConnectionDAOforEJB.singleReturn(query);
			int count = Integer.parseInt(countStr);
			if (count == 0) {
				//											  
				String passQuery = "SELECT USER_PASSWORD,USER_LAST_PASSWORD_1,USER_LAST_PASSWORD_2,USER_LAST_PASSWORD_3,USER_LAST_PASSWORD_4,USER_LAST_PASSWORD_5 FROM SEC_USER_M WHERE USER_ID='"
						+ userId + "'";
				logger.info("passQuery" + passQuery);
				updatePasslist = ConnectionDAOforEJB.sqlSelect(passQuery);
				logger
						.info("IN updateUserPass() update Password query1 ### "
								+ passQuery);
				logger.info("updateUserPass " + updatePasslist.size());
				for (int i = 0; i < updatePasslist.size(); i++) {
					logger.info("updateUserPassList "
							+ updatePasslist.get(i).toString());
					ArrayList data = (ArrayList) updatePasslist.get(i);
					if (data.size() > 0) {
						String userPass = (CommonFunction
								.checkNull(data.get(0)).toString());
						String userPass1 = (CommonFunction.checkNull(data
								.get(1)).toString());
						String userPass2 = (CommonFunction.checkNull(data
								.get(2)).toString());
						String userPass3 = (CommonFunction.checkNull(data
								.get(3)).toString());
						String userPass4 = (CommonFunction.checkNull(data
								.get(4)).toString());
						String userPass5 = (CommonFunction.checkNull(data
								.get(5)).toString());

						// kanika's code
						// LAST_PASSWORD_DATE=STR_TO_DATE('"+changePasswordMasterVo.getPasswordDate()+"','%d-%m-%Y')
						// replace
						logger
								.info("In updateUserData.....................................Dao Impl");
						StringBuilder query1=new StringBuilder();
						query1.append("UPDATE SEC_USER_M SET FORCED_PASSWORD_FLAG='N',LAST_PASSWORD_DATE=");
						query1.append(dbo);
						query1.append("sysdate()");
						query1.append(",USER_LAST_PASSWORD_1='"+userPass+"'");
						query1.append(",USER_LAST_PASSWORD_2='"+userPass1+"'");
						query1.append(",USER_LAST_PASSWORD_3='"+userPass2+"'");
						query1.append(",USER_LAST_PASSWORD_4='"+userPass3+"'");
						query1.append(",USER_LAST_PASSWORD_5='"+ userPass4+"'");
						query1.append(",USER_PASSWORD='"+md5.en(changePasswordMasterVo.getNewPassword())+"'");
						query1.append(", ACCOUNT_STATUS='U' WHERE USER_ID='"+userId+"'");
//						String query1 = "UPDATE SEC_USER_M SET FORCED_PASSWORD_FLAG='N',LAST_PASSWORD_DATE=sysdate()" +
//								",USER_LAST_PASSWORD_1='"
//								+ userPass
//								+ "',USER_LAST_PASSWORD_2='"
//								+ userPass1
//								+ "',USER_LAST_PASSWORD_3='"
//								+ userPass2
//								+ "',USER_LAST_PASSWORD_4='"
//								+ userPass3
//								+ "',USER_LAST_PASSWORD_5='"
//								+ userPass4
//								+ "',USER_PASSWORD='"
//								+ md5.en(changePasswordMasterVo
//										.getNewPassword())
//								+ "', ACCOUNT_STATUS='U' WHERE USER_ID='" + userId + "'";
						updatelist.add(query1.toString());
						logger.info("In getListOfValues" + query1.toString());
						status = ConnectionDAOforEJB.sqlInsUpdDelete(updatelist);

					}
				}
			} else if (count >= 1) {
				status = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// Code End By Ritu For Change Password Master

// code for generic master for Modify - Apoorva
	
	public ArrayList modifyGenericMasterDetailsDao(Object ob) {
		GenericMasterVo genericMasterVo = (GenericMasterVo) ob;
		ArrayList searchlist = new ArrayList();
		String genericKey = "";
		String value = "";
		String parentValue = "";
		ArrayList<GenericMasterVo> detailList = new ArrayList<GenericMasterVo>();
		try {
			logger.info("In GenericMasterDetailsDao()..........inside ejb server file..........Dao Impl");
			StringBuffer bufInsSql = new StringBuffer();
			genericKey = CommonFunction.checkNull(StringEscapeUtils.escapeSql(genericMasterVo.getGenericSearchKey()));
			parentValue = CommonFunction.checkNull(StringEscapeUtils.escapeSql(genericMasterVo.getParentValue()));
			value = CommonFunction.checkNull(StringEscapeUtils.escapeSql(genericMasterVo.getGenericval()));
			
			logger.info("genericMasterVo.getGenericSearchKey()........"+genericMasterVo.getLbxGenericId());
			logger.info("genericKey........"+genericKey);
			logger.info("parentValue........"+parentValue);
			logger.info("value........"+value);
			
			bufInsSql.append("SELECT GENERIC_KEY,(SELECT B.KEY1_DESCRIPTION FROM generic_master_keys B WHERE  B.GENERIC_KEY=A.GENERIC_KEY  " );
			bufInsSql.append(" AND ISNULL(PARENT_KEY,'')='"+parentValue+"'");
			bufInsSql.append(") AS GENERIC_DESC,ISNULL(PARENT_VALUE,'') AS PARENT_VALUE ,VALUE,DESCRIPTION,REC_STATUS ");
			bufInsSql.append(" FROM generic_master A WHERE GENERIC_KEY='"+ genericKey + "'");
			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(parentValue)).trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" and PARENT_VALUE='" + parentValue + "' ");
			}
			bufInsSql.append(" and VALUE='"+value+"' ORDER BY GENERIC_KEY ");
			logger.info("select query is...." + bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("modifyGenericDaoData size is...."+ searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("modifyGenericDaoList "+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					genericMasterVo.setGenericKeyModify("<a href=genericMasterSearchDispatch.do?method=modifyDetails&genericSearchKey="+(CommonFunction.checkNull(data.get(0)).trim())+"&parentValue="+(CommonFunction.checkNull(data.get(2)).trim())+"&value="+(CommonFunction.checkNull(data.get(3)))+">"+CommonFunction.checkNull(data.get(0)).trim() + "</a>");
					genericMasterVo.setLbxGenericId(CommonFunction.checkNull(data.get(0)).toString());
					genericMasterVo.setGenericKey(CommonFunction.checkNull(data.get(1)).toString());
					genericMasterVo.setParentValue(CommonFunction.checkNull(data.get(2)).toString());
					genericMasterVo.setGenericval(CommonFunction.checkNull(data.get(3)).toString());
					genericMasterVo.setDescription(CommonFunction.checkNull(data.get(4)).toString());
					genericMasterVo.setStatus(CommonFunction.checkNull(data.get(5)).toString());
					detailList.add(genericMasterVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	// code for generic master for Save Modify - Apoorva
	public boolean saveModifyGenericMasterDetailsDao(Object ob) {
		String stat = "X";
		boolean status = false;
		GenericMasterVo genericMasterVo = (GenericMasterVo) ob;

		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		if (genericMasterVo.getStatus() != null
				&& genericMasterVo.getStatus().equals("on")) {
			stat = "A";
		} else {
			stat = "X";
		}

		try {

			logger.info("In saveModifyGenericMasterDetailsDao() method........inside ejb server file..........Dao Impl");
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" UPDATE generic_master SET DESCRIPTION=?, ");
			bufInsSql.append(" REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" WHERE GENERIC_KEY=? ");
//			bufInsSql.append(" AND PARENT_VALUE=? ");
			bufInsSql.append(" AND VALUE=? ");

			if (CommonFunction.checkNull(genericMasterVo.getDescription())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(genericMasterVo.getDescription()
						.trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------
			if (CommonFunction.checkNull(genericMasterVo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(genericMasterVo.getMakerId());
			if (CommonFunction.checkNull(genericMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(genericMasterVo.getMakerDate());
			//----------------------------------

			if (CommonFunction.checkNull(genericMasterVo.getLbxGenericId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(genericMasterVo.getLbxGenericId()
						.toUpperCase().trim());

//			if (CommonFunction.checkNull(genericMasterVo.getParentValue())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(genericMasterVo.getParentValue()
//						.trim());
			if (CommonFunction.checkNull(genericMasterVo.getGenericval())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(genericMasterVo.getGenericval()
						.toUpperCase());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			logger
					.info("IN saveModifyGenericMasterDetailsDao() insert query1 ### "
							+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger
					.info("In saveModifyGenericMasterDetailsDao status is................."
							+ status);
			bufInsSql = null;
			insertPrepStmtObject = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// code for generic master for Insert New - Apoorva
	public String insertGenericMaster(Object ob) {
		GenericMasterVo genericMasterVo = (GenericMasterVo) ob;
		boolean status = false;
		logger.info("In saveGenericMaster........inside ejb server file..........Dao Impl"
				+ genericMasterVo.getGenericKey());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuffer bufInsSql1 = new StringBuffer();
		bufInsSql1.append("Select GENERIC_KEY,PARENT_VALUE,VALUE from generic_master where GENERIC_KEY= '"+ StringEscapeUtils.escapeSql(genericMasterVo.getLbxGenericId().trim())+"' ");
				
		if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(genericMasterVo.getParentValue())).trim().equalsIgnoreCase("")))) {
			bufInsSql1.append(" and PARENT_VALUE='" + StringEscapeUtils.escapeSql(genericMasterVo.getParentValue().trim()) + "' ");
		}
		bufInsSql1.append("  AND VALUE='" +genericMasterVo.getGenericval().trim() + "'");
			
		boolean st = ConnectionDAOforEJB.checkStatus(bufInsSql1.toString());
		logger.info("st -----" + st);
		logger.info("query -----" + bufInsSql1.toString());
		String dataval="";
		try {

			if (!st) {
				if (genericMasterVo.getStatus() != null
						&& genericMasterVo.getStatus().equalsIgnoreCase("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert Generic  master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO generic_master(GENERIC_KEY,PARENT_VALUE,VALUE,DESCRIPTION,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
								
				if (CommonFunction.checkNull(genericMasterVo.getLbxGenericId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(genericMasterVo
							.getLbxGenericId().toUpperCase().trim());

				if (CommonFunction.checkNull(genericMasterVo.getParentValue())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(genericMasterVo
							.getParentValue().trim());
				if (CommonFunction.checkNull(genericMasterVo.getGenericval())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(genericMasterVo.getGenericval()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(genericMasterVo.getDescription())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(genericMasterVo
							.getDescription().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);
				if (CommonFunction.checkNull(genericMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject
							.addString(genericMasterVo.getMakerId());
				if (CommonFunction.checkNull(genericMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(genericMasterVo
							.getMakerDate());
				if (CommonFunction.checkNull(genericMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject
							.addString(genericMasterVo.getMakerId());
				if (CommonFunction.checkNull(genericMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(genericMasterVo
							.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN genericMasterMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In genericMasterMaster status....................."+ status);
				if(status)
				{
				dataval="datasave";	
				}else{
					dataval="notsave";
				}
			} else{
				dataval="dataexist";	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataval;
	}

	// code for generic master for Search - Apoorva
	public ArrayList searchGenericMasterDao(Object ob) {
		String genericKey = "";
		String description = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		GenericMasterVo genericMasterVo = (GenericMasterVo) ob;
		ArrayList<GenericMasterVo> detailList = new ArrayList<GenericMasterVo>();
		try {
			logger.info("In searchGenericMasterDao..........inside ejb server file..........Dao Impl");
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			genericKey = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(genericMasterVo.getGenericSearchKey())).trim());
			description = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(genericMasterVo.getSearchDescription())).trim());
			
			bufInsSql.append("select DISTINCT A.GENERIC_KEY, B.KEY1_DESCRIPTION ,PARENT_VALUE,VALUE,DESCRIPTION,iif(REC_STATUS='A','Active','Inactive')as status FROM generic_master A JOIN  generic_master_keys B on B.GENERIC_KEY=A.GENERIC_KEY ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM (select DISTINCT A.GENERIC_KEY, B.KEY1_DESCRIPTION ,PARENT_VALUE,VALUE,DESCRIPTION,iif(REC_STATUS='A','Active','Inactive')as status FROM generic_master A JOIN  generic_master_keys B on B.GENERIC_KEY=A.GENERIC_KEY ");
			
			if ((!(genericKey.equalsIgnoreCase(""))) && (!(description.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE DESCRIPTION like '%" + description + "%' AND A.GENERIC_KEY='" + genericKey + "'");
				bufInsSqlTempCount.append(" WHERE DESCRIPTION like '%" + description + "%' AND A.GENERIC_KEY like '%" + genericKey + "%' )AS bb");
			} 
			else if (!description.equals("")) {
				bufInsSql.append(" WHERE DESCRIPTION LIKE '%" + description + "%' ");
				bufInsSqlTempCount.append(" WHERE DESCRIPTION LIKE '%" + description + "%' )AS bb");
			} 
			 else if (!genericKey.equals("")) {
				bufInsSql.append(" WHERE A.GENERIC_KEY like '%" + genericKey + "%' ");
				bufInsSqlTempCount.append(" WHERE A.GENERIC_KEY like '%" + genericKey + "%' )AS bb ");
			}
			 else
			 {
				 bufInsSqlTempCount.append(")AS bb");
			 }
			//bufInsSql.append(" ORDER BY A.GENERIC_KEY");
			logger.info("IN searchGenericMasterDao() search query1 ### "+ bufInsSql.toString());
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((genericKey.trim()==null && description.trim()==null) || (genericKey.trim().equalsIgnoreCase("") && description.trim().equalsIgnoreCase("")) || genericMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+genericMasterVo.getCurrentPageLink());
			if(genericMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (genericMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY A.GENERIC_KEY OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
									
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					GenericMasterVo genericMVo = new GenericMasterVo();

					genericMVo.setGenericKeyModify("<a href=genericMasterSearchDispatch.do?method=modifyDetails&genericSearchKey="+(CommonFunction.checkNull(data.get(0)).trim())+"&parentValue="+(CommonFunction.checkNull(data.get(2)).trim())+"&value="+(CommonFunction.checkNull(data.get(3)))+">"+CommonFunction.checkNull(data.get(0)).trim() + "</a>");
					
					genericMVo.setLbxGenericId(CommonFunction.checkNull(data.get(0)).toString());                
					genericMVo.setGenericKey(CommonFunction.checkNull(data.get(1)).toString());
					genericMVo.setParentValue(CommonFunction.checkNull(data.get(2)).toString());
					genericMVo.setGenericval(CommonFunction.checkNull(data.get(3)).toString());
					genericMVo.setDescription(CommonFunction.checkNull(data.get(4)).toString());
					genericMVo.setStatus(CommonFunction.checkNull(data.get(5)).toString());
					genericMVo.setTotalRecordSize(count);
					detailList.add(genericMVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			GenericMasterVo genericMVo = new GenericMasterVo();
//			genericMVo.setTotalRecordSize(count);
//			detailList.add(genericMVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}

		return detailList;

	}

	// Code For Product Master By Ankit
	
	
	public ArrayList getProductCategory() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getProductCategory..........................DAOImpl");
			String query = "SELECT PRODUCT_CATEGORY,PRODUCT_CATEGORY_DESC FROM CR_PRODUCTCATEGORY_M WHERE REC_STATUS='A'";
			ProductMasterVo vo = null;
			logger.info("category query......"+query);
			ArrayList product = ConnectionDAOforEJB.sqlSelect(query);
			
			logger.info("get Product size" + product.size());
			
			for (int i = 0; i < product.size(); i++) {
				logger.info("getProductCategory "+ CommonFunction.checkNull(product.get(i)).toString());
				
				ArrayList data = (ArrayList) product.get(i);
				for (int k = 0; k < data.size(); k++) {
					logger.info("getproduct "+ CommonFunction.checkNull(data.get(k)).toString());
					
					vo = new ProductMasterVo();
					vo.setCategory(((String) data.get(0).toString()));
					vo.setCategoryDesc(((String) data.get(1).toString()));
				}
				list.add(vo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean insertProductMaster(Object ob) {
		ProductMasterVo vo = (ProductMasterVo) ob;
		boolean status = false;
		logger.info("In insertProductMaster.....................................Dao Impl"+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String stat1 = "I";
		String stat2 = "N";
		String stat3 = "A";
		String query = "select PRODUCT_ID from cr_product_m where PRODUCT_ID='"+ StringEscapeUtils.escapeSql(vo.getProductId().trim()) + "'";
		logger.info("In insertProductMaster.....................................Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {
			if (!st) {
				if (vo.getRecStatus() != null && vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";
				}

//				if (vo.getRepaymentType() != null && vo.getRepaymentType().equals("I")) {
//					stat1 = "I";
//				} else {
//					stat1 = "N";
//				}

				if (vo.getRevolvingFlag() != null && vo.getRevolvingFlag().equals("on")) {
					stat2 = "Y";
				} else {
					stat2 = "N";
				}

//				if (vo.getAssetFlag() != null && vo.getAssetFlag().equals("A")) {
//					stat3 = "A";
//				} else {
//					stat3 = "N";
//				}

				logger.info("In insert Product master........");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into cr_product_m(PRODUCT_ID,PRODUCT_DESC,PRODUCT_CATEGORY,REPAYMENT_TYPE,REVOLVING_FLAG,REC_STATUS,PRODUCT_DESC_L,ASSET_FLAG,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,DAYS_BASIS,DAYS_PER_YEAR,INSTL_ROUND_TYPE,INSTL_ROUNDING,INT_ROUND_TYPE,INT_ROUNDING,OPPORTUNITY_RATE,ONE_DEAL_ONE_LOAN)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?)");

				if (CommonFunction.checkNull(vo.getProductId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getProductId().toUpperCase().trim());
				
			if (CommonFunction.checkNull(vo.getProductDes()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getProductDes().toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getProductCategory()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getProductCategory().trim());

				if (CommonFunction.checkNull(vo.getRepaymentType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRepaymentType());

				if (CommonFunction.checkNull(stat2).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat2);

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getProductDes()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getProductDes().toUpperCase());

				if (CommonFunction.checkNull(vo.getAssetFlag()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAssetFlag());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
		
				if (CommonFunction.checkNull(vo.getDaysBasis()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDaysBasis());

				if (CommonFunction.checkNull(vo.getDaysPerYear()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDaysPerYear());

				if (CommonFunction.checkNull(vo.getInsRound()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getInsRound());

				if (CommonFunction.checkNull(vo.getInsRounding()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getInsRounding());

				if (CommonFunction.checkNull(vo.getIntRounType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getIntRounType());

				if (CommonFunction.checkNull(vo.getIntRounding()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getIntRounding());


				if (CommonFunction.checkNull(StringEscapeUtils.
						escapeSql(vo.getOpportunityRate()).trim()).equalsIgnoreCase(""))
					  insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
							escapeSql(vo.getOpportunityRate()).trim()).toString());
				
				if (CommonFunction.checkNull(vo.getOneDealOneLoan()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getOneDealOneLoan());

				insertPrepStmtObject.setSql(bufInsSql.toString());

				logger.info("IN insertProductMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveProductData......................"+ status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList<ProductMasterVo> searchProductData(Object ob) {
		String prodID = "";
		String proDesc = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ProductMasterVo productMasterVo = (ProductMasterVo) ob;
		ArrayList<ProductMasterVo> detailList = new ArrayList<ProductMasterVo>();
		try {
			logger.info("In searchProductData().....................................Dao Impl");
			prodID = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(productMasterVo.getProductSearchId())).trim());
			proDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(productMasterVo.getProductSearchDes())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT PRODUCT_ID,PRODUCT_DESC,PRODUCT_CATEGORY,");
			bufInsSql.append(" REPAYMENT_TYPE,case when REPAYMENT_TYPE='I' then 'INSTALLMENT BASED' else 'NON INSTALLMENT BASED' end as REPAYMENT ,");
			bufInsSql.append("case when REVOLVING_FLAG='Y' then 'Yes' else 'No' end as REVOLVING_FLAG,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS,");
			bufInsSql.append("ASSET_FLAG,case when ASSET_FLAG='A' then 'ASSET BASED' else 'NON-ASSET BASED' end as ASSET,");
			bufInsSql.append(" DAYS_BASIS,DAYS_PER_YEAR,INSTL_ROUND_TYPE,");
			bufInsSql.append(" INSTL_ROUNDING,INT_ROUND_TYPE,INT_ROUNDING,OPPORTUNITY_RATE,ONE_DEAL_ONE_LOAN");
			bufInsSql.append(" FROM cr_product_m ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_product_m ");
			
			if (!prodID.equals("") && !proDesc.equals("")) {
				bufInsSql.append(" WHERE PRODUCT_ID = '" + prodID+ "' AND PRODUCT_DESC like '%" + proDesc + "%'");
				bufInsSqlTempCount.append(" WHERE PRODUCT_ID = '" + prodID+ "' AND PRODUCT_DESC like '%" + proDesc + "%'");
			}

			else if (!prodID.equals("")) {
				bufInsSql.append(" WHERE PRODUCT_ID = '" + prodID + "' ");
				bufInsSqlTempCount.append(" WHERE PRODUCT_ID = '" + prodID + "' ");
			}

			else if (!proDesc.equals("")) {
				bufInsSql.append(" WHERE PRODUCT_DESC like '%" + proDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE PRODUCT_DESC like '%" + proDesc + "%' ");
			}
								
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((prodID.trim()==null && proDesc.trim()==null) || (prodID.trim().equalsIgnoreCase("") && proDesc.trim().equalsIgnoreCase("")) || productMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+productMasterVo.getCurrentPageLink());
			if(productMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (productMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY PRODUCT_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
								
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchProductData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchProductData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					ProductMasterVo deptMVO = new ProductMasterVo();
					deptMVO.setProductIdModify("<a href=productMasterSearch.do?method=openEditProduct&ProductSearchId="+ CommonFunction.checkNull(data.get(0)).toString()+ ">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					deptMVO.setProductId(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setProductDes(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setProductCategory(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setRepaymentType(CommonFunction.checkNull(data.get(3)).toString());
					deptMVO.setRepaymentTypeFlag(CommonFunction.checkNull(data.get(4)).toString());
					
					deptMVO.setRevolvingFlag(CommonFunction.checkNull(data.get(5)).toString());
					deptMVO.setRecStatus(CommonFunction.checkNull(data.get(6)).toString());
					deptMVO.setAssetFlag(CommonFunction.checkNull(data.get(7)).toString());
					deptMVO.setAsset(CommonFunction.checkNull(data.get(8)).toString());
					
					deptMVO.setDaysBasis(CommonFunction.checkNull(data.get(9)).toString());
					deptMVO.setDaysPerYear(CommonFunction.checkNull(data.get(10)).toString());
					deptMVO.setInsRound(CommonFunction.checkNull(data.get(11)).toString());
					deptMVO.setInsRounding(CommonFunction.checkNull(data.get(12)).toString());
					deptMVO.setIntRounType(CommonFunction.checkNull(data.get(13)).toString());
					deptMVO.setIntRounding(CommonFunction.checkNull(data.get(14)).toString());
					
				if(!CommonFunction.checkNull(data.get(15)).equalsIgnoreCase(""))
				{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(15))).trim());
					deptMVO.setOpportunityRate(myFormatter.format(reconNum));
				}
				deptMVO.setOneDealOneLoan(CommonFunction.checkNull(data.get(16)).toString());
				deptMVO.setTotalRecordSize(count);
				detailList.add(deptMVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			return detailList;
	}

	public boolean updateProductData(Object ob) {
		ProductMasterVo vo = (ProductMasterVo) ob;
		String productId = (String) vo.getProductId();
		logger.info("vo.getRecStatus():-" + vo.getRecStatus());
		ArrayList updatelist = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		// String
		// selquery="select PRODUCT_ID,PRODUCT_DESC from cr_product_m where PRODUCT_DESC='"+vo.getProductDes()+"'";
		// logger.info("In updateProductMaster.....................................Dao Impl"+selquery);

		boolean status = false;
		String stat = "X";
		String stat1 = "I";
		String stat2 = "N";
		String stat3 = "A";

		try {

			if (vo.getRecStatus() != null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}

//			if (vo.getRepaymentType() != null
//					&& vo.getRepaymentType().equals("I")) {
//				stat1 = "I";
//			} else {
//				stat1 = "N";
//			}

			if (vo.getRevolvingFlag() != null
					&& vo.getRevolvingFlag().equals("on")) {
				stat2 = "Y";
			} else {
				stat2 = "N";
			}

//			if (vo.getAssetFlag() != null && vo.getAssetFlag().equals("A")) {
//				stat3 = "A";
//			} else {
//				stat3 = "N";
//			}

			StringBuffer bufInsSql = new StringBuffer();
			logger
					.info("In updateProductData.....................................Dao Impl");
			bufInsSql
					.append("UPDATE cr_product_m set PRODUCT_DESC=?,PRODUCT_CATEGORY=?,REPAYMENT_TYPE=?,");
			bufInsSql.append("REVOLVING_FLAG=?,REC_STATUS=?,"
					+ "PRODUCT_DESC_L=?,ASSET_FLAG=?,");
			bufInsSql.append("DAYS_BASIS=?,DAYS_PER_YEAR=?,"
					+ "INSTL_ROUND_TYPE=?,INSTL_ROUNDING=?,");
			bufInsSql.append("INT_ROUND_TYPE=?,INT_ROUNDING=?,"
					+ "OPPORTUNITY_RATE=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" ONE_DEAL_ONE_LOAN=?  where PRODUCT_ID=?");

			if (CommonFunction.checkNull(vo.getProductDes()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getProductDes().toUpperCase()
						.trim());

			if (CommonFunction.checkNull(vo.getProductCategory())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getProductCategory()
						.trim());

			if (CommonFunction.checkNull(vo.getRepaymentType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRepaymentType());

			if (CommonFunction.checkNull(stat2).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat2);

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			if (CommonFunction.checkNull(vo.getProductDes()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject
						.addString(vo.getProductDes().toUpperCase());

			if (CommonFunction.checkNull(vo.getAssetFlag()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAssetFlag());

			if (CommonFunction.checkNull(vo.getDaysBasis())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDaysBasis());

			if (CommonFunction.checkNull(vo.getDaysPerYear()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDaysPerYear());

			if (CommonFunction.checkNull(vo.getInsRound()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getInsRound());

			if (CommonFunction.checkNull(vo.getInsRounding()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getInsRounding());

			if (CommonFunction.checkNull(vo.getIntRounType()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getIntRounType());

			if (CommonFunction.checkNull(vo.getIntRounding()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getIntRounding());

			if (CommonFunction.checkNull(vo.getOpportunityRate().trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(vo.getOpportunityRate().trim()).toString());
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			
			if (CommonFunction.checkNull(vo.getOneDealOneLoan())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getOneDealOneLoan());
			//----------------------------------

			if (CommonFunction.checkNull(vo.getProductId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getProductId().toUpperCase());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			logger.info("In insertPrepStmtObject.printQuery()..." + insertPrepStmtObject.printQuery());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	// End Code of Product Master

	// Code For Doc Child Master By Ankit

	public boolean insertDocChildMaster(Object ob) {
		DocChildMasterVo vo = (DocChildMasterVo) ob;
		boolean status = false;
		logger
				.info("In insertDocChildMaster............inside ejb server file...........Dao Impl"
						+ vo.getStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

		String query = "select DOC_CHILD_ID,DOC_DESC from cr_document_child_m where DOC_ID='"
				+ StringEscapeUtils.escapeSql(vo.getDocId().trim())
				+ "' AND DOC_DESC='"
				+ StringEscapeUtils.escapeSql(vo.getDocChildDes().trim()) + "'";
		logger
				.info("In insertDocChildMaster...........inside ejb server file...........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {
			if (!st) {
				if (vo.getStatus() != null && vo.getStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";
				}

				logger.info("In insert Doc Child master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("insert into cr_document_child_m(DOC_ID,DOC_DESC,REC_STATUS,DOC_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
								
				if (CommonFunction.checkNull(vo.getDocId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocId().toUpperCase()
							.trim());

				if (CommonFunction.checkNull(vo.getDocChildDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocChildDes()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getDocChildDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocChildDes()
							.toUpperCase().trim());
				insertPrepStmtObject.setSql(bufInsSql.toString());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				logger.info("IN insertDocChildMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveDocChldData............inside ejb server file...........Dao Impl"
						+ status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList<DocChildMasterVo> searchDocChildData(Object ob) {
		String docChildId = "";
		String docDes = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		DocChildMasterVo docChildMasterVo = (DocChildMasterVo) ob;
		ArrayList<DocChildMasterVo> detailList = new ArrayList<DocChildMasterVo>();
		try {
			logger.info("In searchDocChildData()...........inside ejb server file...........Dao Impl");
			docChildId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(docChildMasterVo.getDocChildID())).trim());
			docDes = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(docChildMasterVo.getDocChildSearchDes())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT DOC_CHILD_ID," + "DOC_ID,");
			bufInsSql.append("(SELECT A.DOC_DESC FROM CR_DOCUMENT_M A WHERE A.DOC_ID=V.DOC_ID ) AS DOCUMENT_DESC,DOC_DESC,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM cr_document_child_m V ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_document_child_m V ");
			
			if ((!(docChildId.equalsIgnoreCase(""))) && (!(docDes.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE DOC_DESC like '%" + docDes + "%' AND DOC_CHILD_ID='" + docChildId + "'");
				bufInsSqlTempCount.append("WHERE DOC_DESC like '%" + docDes + "%' AND DOC_CHILD_ID='" + docChildId + "'");
			} 
			else if (!docDes.equals("")) {
				bufInsSql.append(" WHERE DOC_DESC LIKE '%" + docDes + "%' ");
				bufInsSqlTempCount.append(" WHERE DOC_DESC LIKE '%" + docDes + "%' ");
			}
			else if (!docChildId.equals("")) {
				bufInsSql.append(" WHERE DOC_CHILD_ID = '" + docChildId + "' ");
				bufInsSqlTempCount.append(" WHERE DOC_CHILD_ID = '" + docChildId + "' ");
			}

			logger.info("search Query...." + bufInsSql);
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((docChildId.trim()==null && docDes.trim()==null) || (docChildId.trim().equalsIgnoreCase("") && docDes.trim().equalsIgnoreCase("")) || docChildMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+docChildMasterVo.getCurrentPageLink());
			if(docChildMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (docChildMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY DOC_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
								
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchDocChildData() search query1 ### "+ bufInsSql.toString());

			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					DocChildMasterVo docMVO = new DocChildMasterVo();

					docMVO.setDocChildIDModify("<a href=docChildMasterSearch.do?method=openEditDocChild&DocChildId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					docMVO.setDocChildID(CommonFunction.checkNull(data.get(0)).toString());
					docMVO.setDocId(CommonFunction.checkNull(data.get(1)).toString());
					docMVO.setDocDes(CommonFunction.checkNull(data.get(2)).toString());
					docMVO.setDocChildDes(CommonFunction.checkNull(data.get(3)).toString());
					docMVO.setStatus(CommonFunction.checkNull(data.get(4)).toString());
					docMVO.setTotalRecordSize(count);
					detailList.add(docMVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			DocChildMasterVo docMVO = new DocChildMasterVo();
//			docMVO.setTotalRecordSize(count);
//			detailList.add(docMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}


		return detailList;
	}

	public boolean updateDocChildData(Object ob) {
		DocChildMasterVo vo = (DocChildMasterVo) ob;
		String docChildId = (String) vo.getDocChildID();

		logger.info("vo.getDocChildStatus():-" + vo.getStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();

		String query = "select DOC_CHILD_ID,DOC_ID,DOC_DESC from cr_document_child_m where DOC_ID ='"
			+ StringEscapeUtils.escapeSql(vo.getDocId().trim())
			+ "' AND DOC_DESC ='"
			+ StringEscapeUtils.escapeSql(vo.getDocChildDes().trim()) + "' and DOC_CHILD_ID <> '"+StringEscapeUtils.escapeSql(vo.getDocChildID().trim())+"' ";
	logger.info("In updateDocChildMaster Dao Impl.. "+ query);
	boolean st = ConnectionDAOforEJB.checkStatus(query);
	
	vo.setDocChildID(StringEscapeUtils.escapeSql(vo.getDocChildID().trim()));
	
		boolean status = false;
		String stat = "";

		try {
			if (!st) {
			{
				if (vo.getStatus() != null && vo.getStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";
				}

				logger
						.info("In updateDocChildMaster.....................................Dao Impl");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append(" UPDATE cr_document_child_m set DOC_ID=?,"
						+ "DOC_DESC=?," + " REC_STATUS=?," + "DOC_DESC_L=?,MAKER_ID=?,MAKER_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				bufInsSql.append(" where DOC_CHILD_ID=?");

				if (CommonFunction.checkNull(vo.getDocId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocId().toUpperCase()
							.trim());

				if (CommonFunction.checkNull(vo.getDocChildDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocChildDes()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getDocChildDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocChildDes()
							.toUpperCase().trim());
				//----------------------------------
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
				""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				//----------------------------------
				insertPrepStmtObject.setSql(bufInsSql.toString());

				if (CommonFunction.checkNull(vo.getDocChildID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocChildID());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				updatelist.add(insertPrepStmtObject);
				logger.info("In getListOfValues" + bufInsSql);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}



	// Document Checklist Master

	public ArrayList getStage() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getStage..........................DAOImpl");
			String query = "select DEScription,value from generic_master where generic_key='DOC_STAGE' and rec_status ='A'";
			DocumentChecklistMasterVo stageVO = null;
			ArrayList product = ConnectionDAOforEJB.sqlSelect(query);
			logger.info("getStage " + product.size());
			for (int i = 0; i < product.size(); i++) {
				logger.info("getStage "+ CommonFunction.checkNull(product.get(i)).toString());
				ArrayList data = (ArrayList) product.get(i);
				for (int k = 0; k < data.size(); k++) {
					logger.info("getStage "+ CommonFunction.checkNull(data.get(k)).toString());
					stageVO = new DocumentChecklistMasterVo();
					stageVO.setStageDescription(((String) data.get(0).toString()));
					stageVO.setStageValue(((String) data.get(1).toString()));
				}
				list.add(stageVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList getEntity(String parentValue) {
		ArrayList list1 = new ArrayList();
		try {
			logger.info("In getEntity..........................DAOImpl");
			String query = "select DEScription,value from generic_master where generic_key='DOC_ENTITY_TYPE ' and rec_status ='A'";
			DocumentChecklistMasterVo stageVO = null;
			ArrayList product = ConnectionDAOforEJB.sqlSelect(query);
			logger.info("getEntity " + product.size());
			for (int i = 0; i < product.size(); i++) {
				logger.info("getEntity "
						+ CommonFunction.checkNull(product.get(i)).toString());
				ArrayList data = (ArrayList) product.get(i);
				for (int k = 0; k < data.size(); k++) {
				
					stageVO = new DocumentChecklistMasterVo();
					stageVO.setEntityDescription(((String) data.get(0).toString()));
					stageVO.setEntityValue(((String) data.get(1).toString()));
				}
				list1.add(stageVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
	}

	public ArrayList getConstitution() {
		ArrayList list2 = new ArrayList();
		try {
			logger
					.info("In getConstitution..........................DAOImpl");
			String query = "select DEScription,value from generic_master where generic_key='CUST_CONSTITUTION' and rec_status ='A'";
			DocumentChecklistMasterVo stageVO = null;
			ArrayList product = ConnectionDAOforEJB.sqlSelect(query);
			logger.info("getConstitution " + product.size());
			for (int i = 0; i < product.size(); i++) {
				
				ArrayList data = (ArrayList) product.get(i);
				for (int k = 0; k < data.size(); k++) {
					
					stageVO = new DocumentChecklistMasterVo();
					stageVO.setConsDescription(((String) data.get(0).toString()));
					stageVO.setConsValue(((String) data.get(1).toString()));
				}
				list2.add(stageVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list2;
	}


	public boolean insertDocCheckListMaster(String[] docIdList,
			String[] docMandatoryList, String[] docOriginalList,
			String[] docExpiryFlagList, String[] statusList,
			DocumentChecklistMasterVo vo) {

		ArrayList qryList = new ArrayList();
		boolean status = false;
		String duplicateCount= null;
		logger.info("In insertDocCheckListMaster.....................................Dao Impl"+ vo.getStatus());
		String schemeId=CommonFunction.checkNull(vo.getLbxSchemeID());
		String producTId=CommonFunction.checkNull(vo.getLbxProductID());
		String doCcons=CommonFunction.checkNull(vo.getDocConstitution());
		String assetColl=CommonFunction.checkNull(vo.getLbxAssetCollId());
		StringBuffer bufInsSql = null;
		PrepStmtObject insertPrepStmtObject = null;
		try {
			if(schemeId.equalsIgnoreCase(""))
				schemeId="0";
			
			if(!(producTId.equalsIgnoreCase("")))
			{
			StringBuilder countQry=new StringBuilder();
			countQry.append("SELECT COUNT(1) FROM cr_document_checklist_m WHERE DOC_PRODUCT_ID='"+CommonFunction.checkNull(vo.getLbxProductID())+"' AND DOC_SATGE='"+CommonFunction.checkNull(vo.getDocStage())+"' AND DOC_ENTITY_TYPE='"+CommonFunction.checkNull(vo.getDocEntity())+"' AND ");
			countQry.append("ISNULL(DOC_SCHEME_ID,0)='"+schemeId+"'");
//			String countQry = "SELECT COUNT(1) FROM cr_document_checklist_m WHERE DOC_PRODUCT_ID='"+CommonFunction.checkNull(vo.getLbxProductID())+"' AND DOC_SATGE='"+CommonFunction.checkNull(vo.getDocStage())+"' AND DOC_ENTITY_TYPE='"+CommonFunction.checkNull(vo.getDocEntity())+"' AND ISNULL(DOC_SCHEME_ID,0)='"+schemeId+"'";
			logger.info("... countQry ..."+countQry.toString());
			duplicateCount = ConnectionDAOforEJB.singleReturn(countQry.toString());
			logger.info("... duplicateCount ..."+duplicateCount);
			}
			if(!(doCcons.equalsIgnoreCase("")))
			{
			StringBuilder countQry=new StringBuilder();
			countQry.append("SELECT COUNT(1) FROM cr_document_checklist_m WHERE DOC_CONSTITUTION='"+CommonFunction.checkNull(vo.getDocConstitution())+"' AND DOC_SATGE='"+CommonFunction.checkNull(vo.getDocStage())+"' AND DOC_ENTITY_TYPE='"+CommonFunction.checkNull(vo.getDocEntity())+"' AND ");
			countQry.append("ISNULL(DOC_SCHEME_ID,0)='"+schemeId+"'");
//			String countQry = "SELECT COUNT(1) FROM cr_document_checklist_m WHERE DOC_CONSTITUTION='"+CommonFunction.checkNull(vo.getDocConstitution())+"' AND DOC_SATGE='"+CommonFunction.checkNull(vo.getDocStage())+"' AND DOC_ENTITY_TYPE='"+CommonFunction.checkNull(vo.getDocEntity())+"' AND ISNULL(DOC_SCHEME_ID,0)='"+schemeId+"'";
			logger.info("... countQry ..."+countQry.toString());
			duplicateCount = ConnectionDAOforEJB.singleReturn(countQry.toString());
			logger.info("... duplicateCount ..."+duplicateCount);
			}
			if(!(assetColl.equalsIgnoreCase("")))
			{
			StringBuilder countQry=new StringBuilder();
			countQry.append("SELECT COUNT(1) FROM cr_document_checklist_m WHERE DOC_ASSET_CLASS='"+CommonFunction.checkNull(vo.getLbxAssetCollId())+"' AND DOC_SATGE='"+CommonFunction.checkNull(vo.getDocStage())+"' AND DOC_ENTITY_TYPE='"+CommonFunction.checkNull(vo.getDocEntity())+"' AND ");
			countQry.append("ISNULL(DOC_SCHEME_ID,0)='"+schemeId+"'");
//			String countQry = "SELECT COUNT(1) FROM cr_document_checklist_m WHERE DOC_ASSET_CLASS='"+CommonFunction.checkNull(vo.getLbxAssetCollId())+"' AND DOC_SATGE='"+CommonFunction.checkNull(vo.getDocStage())+"' AND DOC_ENTITY_TYPE='"+CommonFunction.checkNull(vo.getDocEntity())+"' AND ISNULL(DOC_SCHEME_ID,0)='"+schemeId+"'";
			logger.info("... countQry ..."+countQry.toString());
			duplicateCount = ConnectionDAOforEJB.singleReturn(countQry.toString());
			logger.info("... duplicateCount ..."+duplicateCount);
			}
			
			
			if(duplicateCount.equalsIgnoreCase("0"))
			{
				
			for (int i = 0; i < docIdList.length; i++) {

				bufInsSql = new StringBuffer();
				insertPrepStmtObject = new PrepStmtObject();

			
				bufInsSql.append(" insert into cr_document_checklist_m(DOC_PRODUCT_ID,DOC_SCHEME_ID,DOC_SATGE,DOC_ENTITY_TYPE,DOC_CONSTITUTION,DOC_ASSET_CLASS,DOC_ID,DOC_MANDATORY,DOC_ORIGINAL,DOC_EXPIRY_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							
				if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxProductID().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getLbxSchemeID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxSchemeID().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getDocStage()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocStage().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getDocEntity())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocEntity()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getDocConstitution())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocConstitution()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getLbxAssetCollId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxAssetCollId()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(docIdList[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(docIdList[i]);
				logger.info(docIdList[i]);
				logger.info(docMandatoryList[i]);

				if (CommonFunction.checkNull(docMandatoryList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(docMandatoryList[i]);

				logger.info(docMandatoryList[i]);

				if (CommonFunction.checkNull(docOriginalList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(docOriginalList[i]);

				if (CommonFunction.checkNull(docExpiryFlagList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(docExpiryFlagList[i]);

				if (CommonFunction.checkNull(statusList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(statusList[i]);

				logger.info(statusList[i]);

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
			}

				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveAssetVerification......................"+ status);
				logger.info("insertPrepStmtObject...."+insertPrepStmtObject.printQuery());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public ArrayList<DocumentChecklistMasterVo> searchDocCheckListData(Object ob) {
		String productId = "";
		String Stage = "";
		String entityType = "";
		String schemeCode = "";
		String constitution = "";
		String assetCollClass = "";

		ArrayList searchlist = new ArrayList();
		DocumentChecklistMasterVo documentChecklistMasterVo = (DocumentChecklistMasterVo) ob;
		ArrayList<DocumentChecklistMasterVo> detailList = new ArrayList<DocumentChecklistMasterVo>();
		try {
			logger
					.info("In searchDocChildData().....................................Dao Impl");
			productId = StringEscapeUtils
			.escapeSql(CommonFunction.checkNull(documentChecklistMasterVo.getLbxProductID()).trim());
			
			Stage = StringEscapeUtils
			.escapeSql(CommonFunction.checkNull(documentChecklistMasterVo.getDocStage()).trim());
			
			entityType = StringEscapeUtils
			.escapeSql(CommonFunction.checkNull(documentChecklistMasterVo.getDocEntity()).trim());
			
			schemeCode = StringEscapeUtils
			.escapeSql(CommonFunction.checkNull(documentChecklistMasterVo.getLbxSchemeID()).trim());
			
			constitution = StringEscapeUtils
			.escapeSql(CommonFunction.checkNull(documentChecklistMasterVo.getDocConstitution()).trim());
			
			assetCollClass = StringEscapeUtils
			.escapeSql(CommonFunction.checkNull(documentChecklistMasterVo.getLbxAssetCollId()).trim());

			boolean appendSQL = false;
			if(schemeCode.equalsIgnoreCase(""))
				schemeCode="0";
			
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT DOC_CHECKLIST_ID,DOC_PRODUCT_ID,DOC_SCHEME_ID,DOC_SATGE,DOC_ENTITY_TYPE,DOC_CONSTITUTION,");
			bufInsSql.append(" (SELECT TOP 1 A.DESCRIPTION FROM generic_master A WHERE A.VALUE=V.DOC_ASSET_CLASS AND A.GENERIC_KEY in ('ASSET_CLASS' , 'COLLATERAL_CLASS')) AS DOC_ASSET_CLASS,DOC_ID,");
			bufInsSql.append(" (SELECT A.DOC_DESC FROM CR_DOCUMENT_M A WHERE A.DOC_ID=V.DOC_ID ) AS DOCUMENT_DESC,");
			bufInsSql.append(" DOC_MANDATORY,");
			bufInsSql.append(" DOC_ORIGINAL,");
			bufInsSql.append(" DOC_EXPIRY_FLAG,");
			bufInsSql.append(" REC_STATUS,(Select PRODUCT_DESC from cr_product_m where PRODUCT_ID=DOC_PRODUCT_ID) PRODUCT_DESC ");
			bufInsSql.append(",(Select SCHEME_DESC from cr_scheme_m where SCHEME_ID=DOC_SCHEME_ID) SCHEME_DESC,V.DOC_ASSET_CLASS");
			bufInsSql.append(" FROM cr_document_checklist_m V ");

			if ((!(productId.equalsIgnoreCase("")))
					&& (!(Stage.equalsIgnoreCase("")))
					&& (!(entityType.equalsIgnoreCase("")))) {

				bufInsSql.append(" WHERE DOC_PRODUCT_ID='" + productId + "' AND DOC_SATGE='" + Stage
						+ "' AND DOC_ENTITY_TYPE='" + entityType + "'");
			}

			if(productId.equalsIgnoreCase(""))
			{
			if ((!(schemeCode.equalsIgnoreCase("")))) {

				bufInsSql.append(" WHERE ISNULL(DOC_SCHEME_ID,0)='" + schemeCode + "'");
				appendSQL = true;

			}
			}
			else
			{
				bufInsSql.append(" AND ISNULL(DOC_SCHEME_ID,0)='" + schemeCode + "'");
				appendSQL = true;
			}
			if ((!(constitution.equalsIgnoreCase("")))) {

				bufInsSql.append(" AND DOC_CONSTITUTION='" + constitution + "' AND DOC_ENTITY_TYPE='" + entityType + "' AND DOC_SATGE='" + Stage
						+ "' ");
				appendSQL = true;
			}
			
			if ((!(assetCollClass.equalsIgnoreCase("")))) {

				bufInsSql.append(" AND DOC_ASSET_CLASS='" + assetCollClass + "' AND DOC_ENTITY_TYPE='" + entityType + "' AND DOC_SATGE='" + Stage
						+ "'");
				appendSQL = true;
			}


				logger.info("search Query..." + bufInsSql);
				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			bufInsSql.append(" ORDER BY DOC_CHECKLIST_ID");

			logger.info("search Query...." + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchDocChildData() search query1 ### "
					+ bufInsSql.toString());

			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					DocumentChecklistMasterVo docMVO = new DocumentChecklistMasterVo();
					
					
					docMVO.setDocCheckId(CommonFunction.checkNull(data.get(0)).toString());
					docMVO.setLbxProductID(CommonFunction.checkNull(data.get(1)).toString().trim());
					docMVO.setLbxSchemeID(CommonFunction.checkNull(data.get(2)).toString());
					docMVO.setDocStage(CommonFunction.checkNull(data.get(3)).toString());
					docMVO.setDocEntity(CommonFunction.checkNull(data.get(4)).toString());
					docMVO.setDocConstitution(CommonFunction.checkNull(data.get(5)).toString());
					docMVO.setAssetClass(CommonFunction.checkNull(data.get(6)).toString());

					docMVO.setDocId(CommonFunction.checkNull(data.get(7)).toString());
					docMVO.setDocDes(CommonFunction.checkNull(((String) data.get(8)).toString()));
					docMVO.setDocMandatory(CommonFunction.checkNull(data.get(9)).toString());
					docMVO.setDocOriginal(CommonFunction.checkNull(data.get(10)).toString());
					docMVO.setDocExpiryFlag(CommonFunction.checkNull(data.get(11)).toString());
					docMVO.setStatus(CommonFunction.checkNull(data.get(12)).toString());
					docMVO.setProductId(CommonFunction.checkNull(data.get(13)).toString());
					docMVO.setSchemeId(CommonFunction.checkNull(data.get(14)).toString());
					docMVO.setLbxAssetCollId(CommonFunction.checkNull(data.get(15)).toString());
					
					detailList.add(docMVO);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}
	
	public boolean updatedocCheckListData(String[] docIdList,
			String[] docCheckIdList, String[] docMandatoryList,
			String[] docOriginalList, String[] docExpiryFlagList,
			String[] statusList, DocumentChecklistMasterVo vo,String[] docCheckAllIdVal) {

		StringBuffer bufInsSql = null;
		ArrayList qryList = null;
		logger.info(" docCheckIdList value:------"+docCheckIdList.length);
		logger.info(" docIdList value:------"+docIdList.length);
		logger.info(" docCheckAllIdVal value:------"+docCheckAllIdVal.length);
		
		boolean status = false;
		logger.info("In updateDocCheckListMaster.....................................Dao Impl"
						+ vo.getStatus());

		qryList = new ArrayList();

		PrepStmtObject insertPrepStmtObject = null;
		try {
			for (int j = 0; j <= docCheckAllIdVal.length; j++) {
				
				//logger.info(" docIdList value:---inside if---"+docIdList[j]);
				if(j<docCheckAllIdVal.length){
				if(!CommonFunction.checkNull(docCheckAllIdVal[j]).equalsIgnoreCase("")){
					bufInsSql = new StringBuffer();
					insertPrepStmtObject = new PrepStmtObject();
					
					bufInsSql.append("DELETE FROM cr_document_checklist_m WHERE DOC_CHECKLIST_ID=?");
					insertPrepStmtObject.addString(docCheckAllIdVal[j]);
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);
					logger.info("delete query:-------"+insertPrepStmtObject.printQuery());
					//status = ConnectionDAO.sqlInsUpdDelete(qryList);
				}
				}
			}
				
			for (int i = 0; i < docIdList.length; i++) {
				
//				logger.info(" docIdList value:---inside if---"+docIdList[i]);
//				if(i<docCheckIdList.length){
//				if(!CommonFunction.checkNull(docCheckIdList[i]).equalsIgnoreCase("")){
//					bufInsSql = new StringBuffer();
//					insertPrepStmtObject = new PrepStmtObject();
//					
//					bufInsSql.append("DELETE FROM cr_document_checklist_m WHERE DOC_CHECKLIST_ID=?");
//					insertPrepStmtObject.addString(docCheckIdList[i]);
//					insertPrepStmtObject.setSql(bufInsSql.toString());
//					qryList.add(insertPrepStmtObject);
//					logger.info("delete query:-------"+insertPrepStmtObject.printQuery());
//					//status = ConnectionDAO.sqlInsUpdDelete(qryList);
//				}
//				}

				bufInsSql = new StringBuffer();
				insertPrepStmtObject = new PrepStmtObject();              
				

				
				logger.info("in dsfadsfadfa...." + status);

				bufInsSql.append("Insert into cr_document_checklist_m (DOC_PRODUCT_ID,DOC_SCHEME_ID,DOC_SATGE," );
				bufInsSql.append(" DOC_ENTITY_TYPE,DOC_CONSTITUTION,DOC_ASSET_CLASS,");
				bufInsSql.append(" DOC_ID,DOC_MANDATORY,DOC_ORIGINAL,DOC_EXPIRY_FLAG,REC_STATUS,");
				bufInsSql.append(" MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?, ");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append("?, ");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				// bufInsSql.append(" WHERE DOC_CHECKLIST_ID=? " );

				if (CommonFunction.checkNull(vo.getLbxProductID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxProductID()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getLbxSchemeID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxSchemeID()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getDocStage())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocStage()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getDocEntity())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocEntity()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getDocConstitution())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDocConstitution()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getLbxAssetCollId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxAssetCollId()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(docIdList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(docIdList[i]);
				logger.info("doc list" + docIdList[i]);

				if (CommonFunction.checkNull(docMandatoryList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(docMandatoryList[i]);

				logger.info(docMandatoryList[i]);

				if (CommonFunction.checkNull(docOriginalList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(docOriginalList[i]);

				if (CommonFunction.checkNull(docExpiryFlagList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(docExpiryFlagList[i]);

				if (CommonFunction.checkNull(statusList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(statusList[i]);

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				logger.info(statusList[i]);

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("insertPrepStmtObject query:----:---"+insertPrepStmtObject.printQuery());
				logger.info("qryList---------" + qryList);
				qryList.add(insertPrepStmtObject);
			}
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	// code for search searchScemeCodeDao
	public ArrayList<CrSchemeMasterVo> searchScemeCodeDao(Object ob) {
		ArrayList searchlist = new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String schemeId = "";
		String SchemeDesc = "";
		
		CrSchemeMasterVo crSchemeMasterVo = (CrSchemeMasterVo) ob;
		ArrayList<CrSchemeMasterVo> detailList = new ArrayList<CrSchemeMasterVo>();
		try {
			logger.info("In searchScemeCodeDao...............");
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			schemeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getSchemeId())).trim());
			SchemeDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getSchemeDesc())).trim());
			bufInsSql.append("select A.SCHEME_ID,CP.PRODUCT_DESC,A.SCHEME_DESC,A.MIN_AMT_FIN,");
			bufInsSql.append(" A.MAX_AMT_FIN,A.MIN_MARGIN_RATE,A.DEFAULT_MARGIN_RATE,case when A.REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS");
			bufInsSql.append(" from cr_scheme_m A join  cr_product_m CP on (CP.PRODUCT_ID=A.PRODUCT_ID AND CP.REC_STATUS='A')   ");

			bufInsSqlTempCount.append("SELECT COUNT(1)  from cr_scheme_m A join  cr_product_m CP on (CP.PRODUCT_ID=A.PRODUCT_ID AND CP.REC_STATUS='A')  ");
			
			if ((!(schemeId.equalsIgnoreCase(""))) && (!(SchemeDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE A.SCHEME_DESC like'%" + SchemeDesc + "%' AND A.SCHEME_ID='" + schemeId + "'");
				bufInsSqlTempCount.append("WHERE A.SCHEME_DESC like'%" + SchemeDesc + "%' AND A.SCHEME_ID='" + schemeId + "'");
			} 
			else if (!SchemeDesc.equals("")) {
				bufInsSql.append(" WHERE A.SCHEME_DESC LIKE'%" + SchemeDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE A.SCHEME_DESC LIKE'%" + SchemeDesc + "%' ");
			} 
			else if (!schemeId.equals("")) {
				bufInsSql.append(" WHERE A.SCHEME_ID = '" + schemeId + "' ");
				bufInsSqlTempCount.append(" WHERE A.SCHEME_ID = '" + schemeId + "' ");
			}
						
			logger.info("IN searchChargeCodeDao() search query1 ### "+ bufInsSql.toString());
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((schemeId.trim()==null && SchemeDesc.trim()==null) || (schemeId.trim().equalsIgnoreCase("") && SchemeDesc.trim().equalsIgnoreCase("")) || crSchemeMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+crSchemeMasterVo.getCurrentPageLink());
			if(crSchemeMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (crSchemeMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY SCHEME_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
								
//			}
			logger.info("query : "+bufInsSql);
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("searchChargeCodeDao Data size is...."+ searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					CrSchemeMasterVo crScheMVO = new CrSchemeMasterVo();
					crScheMVO.setSchemeIdModify("<a href=crSchemeMasterSearch.do?method=modifyDetails&schemeId="+ CommonFunction.checkNull(data.get(0)).toString()+ ">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					crScheMVO.setSchemeId(CommonFunction.checkNull(data.get(0)).toString());
					crScheMVO.setProductId(CommonFunction.checkNull(data.get(1)).toString());
					crScheMVO.setSchemeDesc(CommonFunction.checkNull(data.get(2)).toString());
					
					if(!(CommonFunction.checkNull(data.get(3))).trim().equalsIgnoreCase(""))
					{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(3))).trim());
					crScheMVO.setMinAmountFin(myFormatter.format(reconNum));
					}

					if(!(CommonFunction.checkNull(data.get(4))).trim().equalsIgnoreCase(""))
					{
					Number reconNum1 =myFormatter.parse((CommonFunction.checkNull(data.get(4))).trim());
					crScheMVO.setMaxAmountFin(myFormatter.format(reconNum1));
					}

					if(!(CommonFunction.checkNull(data.get(5))).trim().equalsIgnoreCase(""))
					{
					Number reconNum2 =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
					crScheMVO.setMinMarginRate(myFormatter.format(reconNum2));
					}

					if(!(CommonFunction.checkNull(data.get(6))).trim().equalsIgnoreCase(""))
					{
						Number reconNum3 =myFormatter.parse((CommonFunction.checkNull(data.get(6))).trim());
						crScheMVO.setDefaultMarginRate(myFormatter.format(reconNum3));
					}

					crScheMVO.setStatus(CommonFunction.checkNull(data.get(7)).toString());
					crScheMVO.setTotalRecordSize(count);
					detailList.add(crScheMVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
			return detailList;
	}

	// code by Yogesh for insertSchemeCodeMaster

	public String insertSchemeCodeMaster(Object ob,String ratMet,String rwEve) {
		  String procval="";
		boolean status = false;
		CrSchemeMasterVo crSchemeMasterVo = (CrSchemeMasterVo) ob;
		ArrayList qryList = new ArrayList();
		//ArrayList qryList2=new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();		
		//boolean status1 = false;
		String stat = "";
		String reschAllowed = "";
		String deferralAllowed = "";
		String prepayAllowed = "";
		String terminationAllowed = "";
		String additionalDisbAllowed="";
//		CallableStatement cst=null;
			
        Connection con=ConnectionDAOforEJB.getConnection();
//        try {
//			//con.setAutoCommit(false);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try {
			
			
			if (crSchemeMasterVo.getStatus() != null && crSchemeMasterVo.getStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}

			if (crSchemeMasterVo.getReschAllowed() != null && crSchemeMasterVo.getReschAllowed().equals("on")) {
				reschAllowed = "Y";
			} else {
				reschAllowed = "N";

			}

			if (crSchemeMasterVo.getDeferralAllowed() != null && crSchemeMasterVo.getDeferralAllowed().equals("on")) {
				deferralAllowed = "Y";
			} else {
				deferralAllowed = "N";

			}

			if (crSchemeMasterVo.getPrepayAllowed() != null	&& crSchemeMasterVo.getPrepayAllowed().equals("on")) {
				prepayAllowed = "Y";
			} else {
				prepayAllowed = "N";

			}

			if (crSchemeMasterVo.getTerminationAllowed() != null && crSchemeMasterVo.getTerminationAllowed().equals("on")) {
				terminationAllowed = "Y";
			} else {
				terminationAllowed = "N";

			}
			
			if (crSchemeMasterVo.getAdditionalDisbAllowed() != null && crSchemeMasterVo.getAdditionalDisbAllowed().equals("on")) {
				additionalDisbAllowed = "Y";
			} else {
				additionalDisbAllowed = "N";

			}

			String MinMarginRate = crSchemeMasterVo.getMinMarginRate();
			 if(MinMarginRate.equalsIgnoreCase(""))
		        {
				 MinMarginRate="0.00";
		        }
			 
			 String MaximumDefrMonthsAllowed = crSchemeMasterVo.getMaximumDefrMonthsAllowed();
			 if(CommonFunction.checkNull(MaximumDefrMonthsAllowed).equalsIgnoreCase(""))
		        {
				 MaximumDefrMonthsAllowed="0";
		        }

			 String MaximumDefrMonthsTotal = crSchemeMasterVo.getMaximumDefrMonthsTotal();
			 if(CommonFunction.checkNull(MaximumDefrMonthsTotal).equalsIgnoreCase(""))
		        {
				 MaximumDefrMonthsTotal="0";
		        }
			 String MinimumGapDefr = crSchemeMasterVo.getMinimumGapDefr();
			 if(CommonFunction.checkNull(MinimumGapDefr).equalsIgnoreCase(""))
		        {
				 MinimumGapDefr="0";
		        }
			 String NumberDefrAllowedYear = crSchemeMasterVo.getNumberDefrAllowedYear();
			 if(CommonFunction.checkNull(NumberDefrAllowedYear).equalsIgnoreCase(""))
		        {
				 NumberDefrAllowedYear="0";
		        }
			 String NumberDefrAllowedTotal = crSchemeMasterVo.getNumberDefrAllowedTotal();
			 if(CommonFunction.checkNull(NumberDefrAllowedTotal).equalsIgnoreCase(""))
		        {
				 NumberDefrAllowedTotal="0";
		        }
			 String PrepayLockinPeriod = crSchemeMasterVo.getPrepayLockinPeriod();
			 if(CommonFunction.checkNull(PrepayLockinPeriod).equalsIgnoreCase(""))
		        {
				 PrepayLockinPeriod="0";
		        }
			 String NumberPrepayAllowedYear = crSchemeMasterVo.getNumberPrepayAllowedYear();
			 if(CommonFunction.checkNull(NumberPrepayAllowedYear).equalsIgnoreCase(""))
		        {
				 NumberPrepayAllowedYear="0";
		        }
			 String NumberPrepayAllowedTotal = crSchemeMasterVo.getNumberPrepayAllowedTotal();
			 if(CommonFunction.checkNull(NumberPrepayAllowedTotal).equalsIgnoreCase(""))
		        {
				 NumberPrepayAllowedTotal="0";
		        }
			 String DefrLockinPeriod = crSchemeMasterVo.getDefrLockinPeriod();
			 if(CommonFunction.checkNull(DefrLockinPeriod).equalsIgnoreCase(""))
		        {
				 DefrLockinPeriod="0";
		        }

			 String MinimumGapResch = crSchemeMasterVo.getMinimumGapResch();
			 if(CommonFunction.checkNull(MinimumGapResch).equalsIgnoreCase(""))
		        {
				 MinimumGapResch="0";
		        }
			 String NumberReschAllowedTotal = crSchemeMasterVo.getNumberReschAllowedTotal();
			 if(CommonFunction.checkNull(NumberReschAllowedTotal).equalsIgnoreCase(""))
		        {
				 NumberReschAllowedTotal="0";
		        }
			 String NumberReschAllowedYear = crSchemeMasterVo.getNumberReschAllowedYear();
			 if(CommonFunction.checkNull(NumberReschAllowedYear).equalsIgnoreCase(""))
		        {
				 NumberReschAllowedYear="0";
		        }
			 String ReschLockinPeriod = crSchemeMasterVo.getReschLockinPeriod();
			 if(CommonFunction.checkNull(ReschLockinPeriod).equalsIgnoreCase(""))
		        {
				 ReschLockinPeriod="0";
		        }
			 String MinPeriodResch = crSchemeMasterVo.getMinPeriodResch();
			 if(CommonFunction.checkNull(MinPeriodResch).equalsIgnoreCase(""))
		        {
				 MinPeriodResch="0";
		        }
			 String DefTenure = crSchemeMasterVo.getDefTenure();
			 if(CommonFunction.checkNull(DefTenure).equalsIgnoreCase(""))
		        {
				 DefTenure="1";
		        }
			 String MaxTenure = crSchemeMasterVo.getMaxTenure();
			 if(CommonFunction.checkNull(MaxTenure).equalsIgnoreCase(""))
		        {
				 MaxTenure="1";
		        }
			 String MinimumGapPrepay = crSchemeMasterVo.getMinimumGapPrepay();
			 if(CommonFunction.checkNull(MinimumGapPrepay).equalsIgnoreCase(""))
		        {
				 MinimumGapPrepay="0";
		        }
			 String MinimumPrepayPercent = crSchemeMasterVo.getMinimumPrepayPercent();
			 if(CommonFunction.checkNull(MinimumPrepayPercent).equalsIgnoreCase(""))
		        {
				 MinimumPrepayPercent="0.00";
		        }
			 
			 if(CommonFunction.checkNull(DefTenure).equalsIgnoreCase(""))
		        {
				 DefTenure="1";
		        }
			 String MinTenure = crSchemeMasterVo.getMinTenure();
			 if(CommonFunction.checkNull(MinTenure).equalsIgnoreCase(""))
		        {
				 MinTenure="1";
		        }
			 String MaxIrr = crSchemeMasterVo.getMaxIrr();
			 if(CommonFunction.checkNull(MaxIrr).equalsIgnoreCase(""))
		        {
				 MaxIrr="0.00";
		        }
			 String MinIrr = crSchemeMasterVo.getMinIrr();
			 if(CommonFunction.checkNull(MinIrr).equalsIgnoreCase(""))
		        {
				 MinIrr="0.00";
		        }
			 String MaximumPrepayPercent = crSchemeMasterVo.getMaximumPrepayPercent();
			 if(CommonFunction.checkNull(MaximumPrepayPercent).equalsIgnoreCase(""))
		        {
				 MaximumPrepayPercent="0.00";
		        }
			 
			 String TerminationLockinPeriod = crSchemeMasterVo.getTerminationLockinPeriod();
			 if(CommonFunction.checkNull(TerminationLockinPeriod).equalsIgnoreCase(""))
		        {
				 TerminationLockinPeriod="0";
		        }
			 
			 String MinimumGapTermination = crSchemeMasterVo.getMinimumGapTermination();
			 if(CommonFunction.checkNull(MinimumGapTermination).equalsIgnoreCase(""))
		        {
				 MinimumGapTermination="0";
		        }
			 if(CommonFunction.checkNull(MaxIrr).equalsIgnoreCase(""))
		        {
				 MaxIrr="0.00";
		        }
			 String DefFlatRate = crSchemeMasterVo.getDefFlatRate();
			 if(CommonFunction.checkNull(DefFlatRate).equalsIgnoreCase(""))
		        {
				 DefFlatRate="0.00";
		        }
			 String MinEffRate = crSchemeMasterVo.getMinEffRate();
			 if(CommonFunction.checkNull(MinEffRate).equalsIgnoreCase(""))
		        {
				 MinEffRate="0.00";
		        }
			 
			 String MaxEffRate = crSchemeMasterVo.getMaxEffRate();
			 if(CommonFunction.checkNull(MaxEffRate).equalsIgnoreCase(""))
		        {
				 MaxEffRate="0.00";
		        }
			 
			 String DefEffRate = crSchemeMasterVo.getDefEffRate();
			 if(CommonFunction.checkNull(DefEffRate).equalsIgnoreCase(""))
		        {
				 DefEffRate="0.00";
		        }
			 if(CommonFunction.checkNull(MaxIrr).equalsIgnoreCase(""))
		        {
				 MaxIrr="0.00";
		        }
			 String MaxMarginRate = crSchemeMasterVo.getMaxMarginRate();
			 if(CommonFunction.checkNull(MaxMarginRate).equalsIgnoreCase(""))
		        {
				 MaxMarginRate="0.00";
		        }
			 String DefaultMarginRate = crSchemeMasterVo.getDefaultMarginRate();
			 if(CommonFunction.checkNull(DefaultMarginRate).equalsIgnoreCase(""))
		        {
				 DefaultMarginRate="0.00";
		        }
			 
			 String MinFlatRate = crSchemeMasterVo.getMinFlatRate();
			 if(CommonFunction.checkNull(MinFlatRate).equalsIgnoreCase(""))
		        {
				 MinFlatRate="0.00";
		        }
			 
			 String MaxFlatRate = crSchemeMasterVo.getMaxFlatRate();
			 if(CommonFunction.checkNull(MaxFlatRate).equalsIgnoreCase(""))
		        {
				 MaxFlatRate="0.00";
		        }
			 
			 String CustomerExposureLimit = crSchemeMasterVo.getCustomerExposureLimit();
			 if(CommonFunction.checkNull(CustomerExposureLimit).equalsIgnoreCase(""))
		        {
				 CustomerExposureLimit="0.00";
		        }
			 
			 String preEMI = crSchemeMasterVo.getPreEMI();
			 if(CommonFunction.checkNull(preEMI).equalsIgnoreCase(""))
		        {
				 preEMI="0.00";
		        }
			 
			 String fixPeriod = crSchemeMasterVo.getFixPriod();
			 if(CommonFunction.checkNull(fixPeriod).equalsIgnoreCase(""))
		        {
				 fixPeriod="0.00";
		        }
			 
			 String gapreviw = crSchemeMasterVo.getGapReview();
			 if(CommonFunction.checkNull(gapreviw).equalsIgnoreCase(""))
		        {
				 gapreviw="0.00";
		        }
			 if(CommonFunction.checkNull( crSchemeMasterVo.getMinAmountFin()).equalsIgnoreCase(""))
		        {
				 crSchemeMasterVo.setMinAmountFin("0.00");
		        }
			 if(CommonFunction.checkNull( crSchemeMasterVo.getMaxAmountFin()).equalsIgnoreCase(""))
		        {
				 crSchemeMasterVo.setMaxAmountFin("0.00");
		        }
			 
			 String MinGapPrepayTermination=crSchemeMasterVo.getMinimumGapBetPrepayAndTer();
			 if(CommonFunction.checkNull(MinGapPrepayTermination).equalsIgnoreCase(""))
		        {
				 MinGapPrepayTermination="0";
		        }
			 
			// Code by sanjog 13-03-2013
			 if(CommonFunction.checkNull( crSchemeMasterVo.getSchemeExposure()).equalsIgnoreCase(""))
		        {
				 crSchemeMasterVo.setSchemeExposure("0.00");
		        }
			// Code by sanjog 13-03-2013
			
			logger.info("In insertSchemeCodeMaster()........");
			logger.info("In Scheme_Account_save.....Procedure...");
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			String	date=CommonFunction.changeFormat(crSchemeMasterVo.getMakerDate());
			String expDate=CommonFunction.changeFormat(crSchemeMasterVo.getExpiryDate());
			logger.info("Expiry Date : " + expDate);
			try
			{
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getLbxProductID()).trim())); // PRODUCT_ID
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getSchemeDesc()).trim())); // SCHEME_DESC
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getSchemeDesc()).trim())); // SCHEME_DESC_L
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinAmountFin()).trim())).toString()); // MIN_AMT_FIN
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxAmountFin()).trim())).toString()); // MAX_AMT_FIN
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MinMarginRate).trim())).toString());// MIN_MARGIN_RATE
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MaxMarginRate).trim())).toString()); // MAX_MARGIN_RATE
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(DefaultMarginRate).trim())).toString());// DEFAULT_MARGIN_RATE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getRateType()).trim()));// RATE_TYPE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(ratMet).trim()));// RATE_METHOD
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getBaseRateType()).trim()));// BASE_RATE_TYPE
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MinFlatRate).trim())).toString());// MIN_FLAT_RATE
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MaxFlatRate).trim())).toString());// MAX_FLAT_RATE
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(DefFlatRate).trim())).toString());// DEF_FLAT_RATE
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MinEffRate).trim())).toString());// MIN_EFF_RATE
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MaxEffRate).trim())).toString());// MAX_EFF_RATE
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(DefEffRate).trim())).toString());// DEF_EFF_RATE
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MinIrr).trim())).toString());// MIN_IRR
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MaxIrr).trim())).toString());// MAX_IRR
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MinTenure).trim()).toString());// MIN_TENURE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MaxTenure).trim()).toString());// MAX_TENURE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(DefTenure).trim()).toString()); // DEF_TENURE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getRepaymentFreq()).trim()));// REPAYMENT_FREQ
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getInstallmentType()).trim()));// INSTALLMENT_TYPE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getRepaymentMode()).trim()));// REPAYMENT_MODE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getInstallmentMode()).trim()));// INSTALLMENT_MODE
				in.add(Integer.parseInt(MinPeriodResch));// MINIMUM_PERIOD_RESCH
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschAllowed)).trim());   // RESCH_ALLOWED 
				in.add(Integer.parseInt(ReschLockinPeriod));// RESCH_LOCKIN_PERIOD
				in.add(Integer.parseInt(NumberReschAllowedYear));// NUMBER_RESCH_ALLOWED_YEAR
				in.add(Integer.parseInt(NumberReschAllowedTotal));// NUMBER_RESCH_ALLOWED_TOTAL
				in.add(Integer.parseInt(MinimumGapResch));// MINIMUM_GAP_RESCH
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(deferralAllowed)).trim());// DEFERRAL_ALLOWED
				in.add(Integer.parseInt(DefrLockinPeriod));// DEFR_LOCKIN_PERIOD
				in.add(Integer.parseInt(MaximumDefrMonthsAllowed));// MAXIMUM_DEFR_MONTHS_ALLOWED
				in.add(Integer.parseInt(MaximumDefrMonthsTotal));// MAXIMUM_DEFR_MONTHS_TOTAL
				in.add(Integer.parseInt(MinimumGapDefr));// MINIMUM_GAP_DEFR
				in.add(Integer.parseInt(NumberDefrAllowedYear));// NUMBER_DEFR_ALLOWED_YEAR
				in.add(Integer.parseInt(NumberDefrAllowedTotal));// NUMBER_DEFR_ALLOWED_TOTAL
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(prepayAllowed)).trim());// PREPAY_ALLOWED
				in.add(Integer.parseInt(PrepayLockinPeriod));// PREPAY_LOCKIN_PERIOD
				in.add(Integer.parseInt(NumberPrepayAllowedYear));// NUMBER_PREPAY_ALLOWED_YEAR
				in.add(Integer.parseInt(NumberPrepayAllowedTotal));// NUMBER_PREPAY_ALLOWED_TOTAL
				in.add(Integer.parseInt(MinimumGapPrepay));// MINIMUM_GAP_PREPAY
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MinimumPrepayPercent).trim())).toString());// MINIMUM_PREPAY_PERCENT
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(MaximumPrepayPercent).trim())).toString());// MAXIMUM_PREPAY_PERCENT
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationAllowed)).trim());// TERMINATION_ALLOWED
				in.add(Integer.parseInt(TerminationLockinPeriod));// TERMINATION_LOCKIN_PERIOD
				in.add(Integer.parseInt(MinimumGapTermination));// MINIMUM_GAP_TERMINATION
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(CustomerExposureLimit).trim())).toString());// CustomerExposureLimit
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(preEMI).trim())).toString());// PRE_EMI_INTEREST_RATE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(stat)).trim());// REC_STATUS
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(fixPeriod).trim())).toString());// FLOATING_FIXED_PERIOD
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(rwEve).trim()));// FLOATING_REVIEW_EVENT
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(gapreviw).trim())).toString());// MIN_GAP_FLOATING
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getIncrese()).trim()));// FLOATING_TYPE_INCREASE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDecrese()).trim()));// FLOATING_TYPE_DECREASE
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(additionalDisbAllowed)).trim());// ADDITIONAL_DISBURSAL_ALLOWED
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMakerId()).trim())); //UserID
				in.add(date); //date
				in.add(Integer.parseInt(MinGapPrepayTermination));// MINIMUM_GAP_BETWEEN_PREPAY_AND_TERMINATION
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getValidityDays()).trim()));//Validity Days
				in.add(expDate);//Expiry Date
				in.add(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getSchemeExposure()).trim())));// Scheme Exposure Amount
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getLbxBranchIds()).trim())); //lbxBranchIds
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getAllselection()).trim())); //AllBranch
				out.add(s1);
				out.add(s2);
		
				logger.info("Scheme_Account_save ("+in.toString()+","+out.toString()+")");
				outMessages=(ArrayList) ConnectionDAOforEJB.callSP("Scheme_Account_save",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
			}
		     
			catch (Exception e) {
				e.printStackTrace();
			}			
            procval=s2;
            if(s1.equalsIgnoreCase("S"))
            {
            	status=true;
                procval=s1;
            }
            else
            {
            	status=false;
            	procval=s2;
            }
			} catch (Exception e) {
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			e.printStackTrace();
		}
			
		finally
		{
			
			try
			{
				//con.commit();
				con.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return procval;
	}
	

	// code by yogesh for modifySchemeDetailsDao
	public ArrayList<CrSchemeMasterVo> modifySchemeDetailsDao(Object ob) {
		CrSchemeMasterVo crSchemeMasterVo = (CrSchemeMasterVo) ob;
		ArrayList searchlist = new ArrayList();
		String schemeId = "";
		ArrayList<CrSchemeMasterVo> detailList = new ArrayList<CrSchemeMasterVo>();
		try {
			logger.info("In modifySchemeDetailsDao()....................");
			StringBuffer bufInsSql = new StringBuffer();
			schemeId = CommonFunction.checkNull(crSchemeMasterVo.getSchemeId());

			bufInsSql.append(" SELECT PRODUCT_ID,SCHEME_DESC,MIN_AMT_FIN,MAX_AMT_FIN,MIN_MARGIN_RATE,");
			bufInsSql.append(" DEFAULT_MARGIN_RATE,RATE_TYPE,RATE_METHOD,BASE_RATE_TYPE,MIN_FLAT_RATE,");
			bufInsSql.append(" MAX_FLAT_RATE,DEF_FLAT_RATE,MIN_EFF_RATE,MAX_EFF_RATE,DEF_EFF_RATE,");
			bufInsSql.append(" MIN_IRR,MAX_IRR,MIN_TENURE,MAX_TENURE,DEF_TENURE,REPAYMENT_FREQ,");
			bufInsSql.append(" INSTALLMENT_TYPE,REPAYMENT_MODE,INSTALLMENT_MODE,case when RESCH_ALLOWED='Y' then 'Active' else 'Inactive' end as RESCH_ALLOWED,");
			bufInsSql.append(" RESCH_LOCKIN_PERIOD,NUMBER_RESCH_ALLOWED_YEAR,NUMBER_RESCH_ALLOWED_TOTAL,");
			bufInsSql.append(" MINIMUM_GAP_RESCH,case when DEFERRAL_ALLOWED='Y' then 'Active' else 'Inactive' end as DEFERRAL_ALLOWED,DEFR_LOCKIN_PERIOD,MAXIMUM_DEFR_MONTHS_ALLOWED,");
			bufInsSql.append(" MAXIMUM_DEFR_MONTHS_TOTAL,MINIMUM_GAP_DEFR,");
			bufInsSql.append(" NUMBER_DEFR_ALLOWED_YEAR,NUMBER_DEFR_ALLOWED_TOTAL,case when PREPAY_ALLOWED='Y' then 'Active' else 'Inactive' end as PREPAY_ALLOWED,");
			bufInsSql.append(" NUMBER_PREPAY_ALLOWED_YEAR,MINIMUM_GAP_PREPAY,");
			bufInsSql.append(" MINIMUM_PREPAY_PERCENT,MAXIMUM_PREPAY_PERCENT,");
			bufInsSql.append(" case when TERMINATION_ALLOWED='Y' then 'Active' else 'Inactive' end as TERMINATION_ALLOWED,MINIMUM_GAP_TERMINATION,case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS, ");
			bufInsSql.append(" PREPAY_LOCKIN_PERIOD,NUMBER_PREPAY_ALLOWED_TOTAL,TERMINATION_LOCKIN_PERIOD,MAX_MARGIN_RATE,SCHEME_ID, ");
			bufInsSql.append(" (SELECT ASSET_FLAG FROM cr_product_m A where V.PRODUCT_ID=A.PRODUCT_ID)As Asset_flag,MINIMUM_PERIOD_RESCH,CUSTOMER_EXPOSURE_LIMIT,PRE_EMI_INTEREST_RATE, ");
			bufInsSql.append(" FLOATING_FIXED_PERIOD,FLOATING_REVIEW_EVENT,MIN_GAP_FLOATING,FLOATING_TYPE_INCREASE,FLOATING_TYPE_DECREASE, ");
			bufInsSql.append(" case when ADDITIONAL_DISBURSAL_ALLOWED='Y' then 'Active' else 'Inactive' end as ADDITIONAL_DISBURSAL_ALLOWED,(select A.PRODUCT_DESC from cr_product_m A where A.PRODUCT_ID=V.PRODUCT_ID) AS PRODUCT,MINIMUM_GAP_BT_PREPAY_TERM,Validity_Days,dbo.DATE_FORMAT(Expiry_Date,'%d-%m-%Y') ");
			bufInsSql.append(" ,EXPOSURE_AMOUNT , BRANCH_IDS,BRANCH_ACCESS  ");
			bufInsSql.append(" FROM cr_scheme_m V WHERE SCHEME_ID='" + schemeId+ "'");

			logger.info("qurey is ....." + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN modifySchemeDetailsDao() search query1 ### "+ bufInsSql.toString());
			logger.info(" modifySchemeDetailsDao size is...."+ searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("modifySchemeDetailsDao "+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					logger.info("data.get(0)---ProductID----------->:"+data.get(0).toString());
					crSchemeMasterVo.setLbxProductID(CommonFunction.checkNull(data.get(0)).toString());
					logger.info("getLbxProductID----------->:"+	crSchemeMasterVo.getLbxProductID());
					crSchemeMasterVo.setSchemeDesc(CommonFunction.checkNull(
							data.get(1)).toString());
					
					if(!(CommonFunction.checkNull(data.get(2))).trim().equalsIgnoreCase(""))
					{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(2))).trim());
					crSchemeMasterVo.setMinAmountFin(myFormatter.format(reconNum));
					}
//					if (CommonFunction.checkNull(data.get(2)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMinAmountFin("0.000");
//					else
//						crSchemeMasterVo.setMinAmountFin(myFormatter.format(Double.parseDouble(data.get(2).toString())));
					if(!(CommonFunction.checkNull(data.get(3))).trim().equalsIgnoreCase(""))
					{
					Number reconNum1 =myFormatter.parse((CommonFunction.checkNull(data.get(3))).trim());
					crSchemeMasterVo.setMaxAmountFin(myFormatter.format(reconNum1));
					}
//					if (CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMaxAmountFin("0.000");
//					else
//						crSchemeMasterVo.setMaxAmountFin(myFormatter.format(Double.parseDouble(data.get(3).toString())));
					if(!CommonFunction.checkNull(data.get(4)).equalsIgnoreCase(""))
					{
					Number reconNum2 =myFormatter.parse((CommonFunction.checkNull(data.get(4))).trim());
					crSchemeMasterVo.setMinMarginRate(myFormatter.format(reconNum2));
					}
//					if (CommonFunction.checkNull(data.get(4)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMinMarginRate(0.0);
//					else
//						crSchemeMasterVo.setMinMarginRate(Double.parseDouble(data.get(4).toString()));
					if(!(CommonFunction.checkNull(data.get(5))).trim().equalsIgnoreCase(""))
					{
					Number reconNum3 =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
					crSchemeMasterVo.setDefaultMarginRate(myFormatter.format(reconNum3));
					}
//					if (CommonFunction.checkNull(data.get(5)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setDefaultMarginRate("0.000");
//					else
//						crSchemeMasterVo.setDefaultMarginRate(myFormatter.format(Double.parseDouble(data.get(5).toString())));

					crSchemeMasterVo.setRateType(CommonFunction.checkNull(data.get(6)).toString());
					crSchemeMasterVo.setRateMethod(CommonFunction.checkNull(data.get(7)).toString());
					crSchemeMasterVo.setBaseRateType(CommonFunction.checkNull(data.get(8)).toString());
					if(!(CommonFunction.checkNull(data.get(9))).trim().equalsIgnoreCase(""))
					{
					Number reconNum4 =myFormatter.parse((CommonFunction.checkNull(data.get(9))).trim());
					crSchemeMasterVo.setMinFlatRate(myFormatter.format(reconNum4));
					}
//					if (CommonFunction.checkNull(data.get(9)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMinFlatRate("0.000");
//					else
//						crSchemeMasterVo.setMinFlatRate(myFormatter.format(Double.parseDouble(data.get(9).toString())));
					if(!(CommonFunction.checkNull(data.get(10))).trim().equalsIgnoreCase(""))
					{
					Number reconNum5 =myFormatter.parse((CommonFunction.checkNull(data.get(10))).trim());
					crSchemeMasterVo.setMaxFlatRate(myFormatter.format(reconNum5));
					}
//					if (CommonFunction.checkNull(data.get(10)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMaxFlatRate("0.000");
//					else
//						crSchemeMasterVo.setMaxFlatRate(myFormatter.format(Double.parseDouble(data.get(10).toString())));
					if(!(CommonFunction.checkNull(data.get(11))).trim().equalsIgnoreCase(""))
					{
					Number reconNum6 =myFormatter.parse((CommonFunction.checkNull(data.get(11))).trim());
					crSchemeMasterVo.setDefFlatRate(myFormatter.format(reconNum6));
					}
//					if (CommonFunction.checkNull(data.get(11)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setDefFlatRate("0.000");
//					else
//						crSchemeMasterVo.setDefFlatRate(myFormatter.format(Double.parseDouble(data.get(11).toString())));
					if(!(CommonFunction.checkNull(data.get(12))).trim().equalsIgnoreCase(""))
					{
					Number reconNum7 =myFormatter.parse((CommonFunction.checkNull(data.get(12))).trim());
					crSchemeMasterVo.setMinEffRate(myFormatter.format(reconNum7));
					}
//					if (CommonFunction.checkNull(data.get(12)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMinEffRate("0.000");
//					else
//						crSchemeMasterVo.setMinEffRate(myFormatter.format(Double.parseDouble(data.get(12).toString())));
					if(!(CommonFunction.checkNull(data.get(13))).trim().equalsIgnoreCase(""))
					{
					Number reconNum8 =myFormatter.parse((CommonFunction.checkNull(data.get(13))).trim());
					crSchemeMasterVo.setMaxEffRate(myFormatter.format(reconNum8));
					}
//					if (CommonFunction.checkNull(data.get(13)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMaxEffRate("0.000");
//					else
//						crSchemeMasterVo.setMaxEffRate(myFormatter.format(Double.parseDouble(data.get(13).toString())));
					if(!(CommonFunction.checkNull(data.get(14))).trim().equalsIgnoreCase(""))
					{
					Number reconNum9 =myFormatter.parse((CommonFunction.checkNull(data.get(14))).trim());
					crSchemeMasterVo.setDefEffRate(myFormatter.format(reconNum9));
					}
//					if (CommonFunction.checkNull(data.get(14)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setDefEffRate("0.000");
//					else
//						crSchemeMasterVo.setDefEffRate(myFormatter.format(Double.parseDouble(data.get(14).toString())));
					if(!(CommonFunction.checkNull(data.get(15))).trim().equalsIgnoreCase(""))
					{
					Number reconNum10 =myFormatter.parse((CommonFunction.checkNull(data.get(15))).trim());
					crSchemeMasterVo.setMinIrr(myFormatter.format(reconNum10));
					}
//					if (CommonFunction.checkNull(data.get(15)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMinIrr("0.000");
//					else
//						crSchemeMasterVo.setMinIrr(myFormatter.format(Double.parseDouble(data.get(15).toString())));
					if(!(CommonFunction.checkNull(data.get(16))).trim().equalsIgnoreCase(""))
					{
					Number reconNum11 =myFormatter.parse((CommonFunction.checkNull(data.get(16))).trim());
					crSchemeMasterVo.setMaxIrr(myFormatter.format(reconNum11));
					}
//					if (CommonFunction.checkNull(data.get(16)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMaxIrr("0.000");
//					else
//						crSchemeMasterVo.setMaxIrr(myFormatter.format(Double.parseDouble(data.get(16).toString())));
					if((CommonFunction.checkNull(data.get(17)).trim().equals(""))||(CommonFunction.checkNull(data.get(17)).trim().equals("0")))
					{
						crSchemeMasterVo.setMinTenure("1");
					}else{	
						crSchemeMasterVo.setMinTenure(CommonFunction.checkNull(data.get(17)).trim());
					}
					//crSchemeMasterVo.setMinTenure(CommonFunction.checkNull(data.get(17)).toString());
					if((CommonFunction.checkNull(data.get(18)).trim().equals(""))||(CommonFunction.checkNull(data.get(18)).trim().equals("0")))
					{
						crSchemeMasterVo.setMaxTenure("1");
					}else{
						crSchemeMasterVo.setMaxTenure(CommonFunction.checkNull(data.get(18)).trim());
					}
//					crSchemeMasterVo.setMaxTenure(CommonFunction.checkNull(
//							data.get(18)).toString());
					if((CommonFunction.checkNull(data.get(19)).trim().equals(""))||(CommonFunction.checkNull(data.get(19)).trim().equals("0")))
					{
						crSchemeMasterVo.setDefTenure("1");
					}else{
						crSchemeMasterVo.setDefTenure(CommonFunction.checkNull(data.get(19)).trim());
					}
//					crSchemeMasterVo.setDefTenure(CommonFunction.checkNull(
//							data.get(19)).toString());
					
					crSchemeMasterVo.setRepaymentFreq(CommonFunction.checkNull(data.get(20)).toString());
					crSchemeMasterVo.setInstallmentType(CommonFunction.checkNull(data.get(21)).toString());
					crSchemeMasterVo.setRepaymentMode(CommonFunction.checkNull(data.get(22)).toString());
					crSchemeMasterVo.setInstallmentMode(CommonFunction.checkNull(data.get(23)).toString());
					crSchemeMasterVo.setReschAllowed(CommonFunction.checkNull(data.get(24)).toString());
					crSchemeMasterVo.setReschLockinPeriod(CommonFunction.checkNull(data.get(25)).toString());
					crSchemeMasterVo.setNumberReschAllowedYear(CommonFunction.checkNull(data.get(26)).toString());
					crSchemeMasterVo.setNumberReschAllowedTotal(CommonFunction.checkNull(data.get(27)).toString());
					crSchemeMasterVo.setMinimumGapResch(CommonFunction.checkNull(data.get(28)).toString());
					crSchemeMasterVo.setDeferralAllowed(CommonFunction.checkNull(data.get(29)).toString());
					crSchemeMasterVo.setDefrLockinPeriod(CommonFunction.checkNull(data.get(30)).toString());
					crSchemeMasterVo.setMaximumDefrMonthsAllowed(CommonFunction.checkNull(data.get(31)).toString());
					crSchemeMasterVo.setMaximumDefrMonthsTotal(CommonFunction.checkNull(data.get(32)).toString());
					crSchemeMasterVo.setMinimumGapDefr(CommonFunction.checkNull(data.get(33)).toString());
					crSchemeMasterVo.setNumberDefrAllowedYear(CommonFunction.checkNull(data.get(34)).toString());
					crSchemeMasterVo.setNumberDefrAllowedTotal(CommonFunction.checkNull(data.get(35)).toString());
					crSchemeMasterVo.setPrepayAllowed(CommonFunction.checkNull(data.get(36)).toString());
					crSchemeMasterVo.setNumberPrepayAllowedYear(CommonFunction.checkNull(data.get(37)).toString());
					crSchemeMasterVo.setMinimumGapPrepay(CommonFunction.checkNull(data.get(38)).toString());
					
					if(!(CommonFunction.checkNull(data.get(39))).trim().equalsIgnoreCase(""))
					{
					Number reconNum15 =myFormatter.parse((CommonFunction.checkNull(data.get(39))).trim());
					crSchemeMasterVo.setMinimumPrepayPercent(myFormatter.format(reconNum15));
					}
//					if (CommonFunction.checkNull(data.get(39)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMinimumPrepayPercent("0.000");
//					else
//						crSchemeMasterVo.setMinimumPrepayPercent(myFormatter.format(Double.parseDouble(data.get(39).toString())));
					if(!(CommonFunction.checkNull(data.get(40))).trim().equalsIgnoreCase(""))
					{
					Number reconNum16 =myFormatter.parse((CommonFunction.checkNull(data.get(40))).trim());
					crSchemeMasterVo.setMaximumPrepayPercent(myFormatter.format(reconNum16));
					}
//					if (CommonFunction.checkNull(data.get(40)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMaximumPrepayPercent("0.000");
//					else
//						crSchemeMasterVo.setMaximumPrepayPercent(myFormatter.format(Double.parseDouble(data.get(40).toString())));

					crSchemeMasterVo.setTerminationAllowed(CommonFunction.checkNull(data.get(41)).toString());
					crSchemeMasterVo.setMinimumGapTermination(CommonFunction.checkNull(data.get(42)).toString());
					crSchemeMasterVo.setStatus(CommonFunction.checkNull(data.get(43)).toString());
					crSchemeMasterVo.setPrepayLockinPeriod(CommonFunction.checkNull(data.get(44)).toString());
					crSchemeMasterVo.setNumberPrepayAllowedTotal(CommonFunction.checkNull(data.get(45)).toString());
					crSchemeMasterVo.setTerminationLockinPeriod(CommonFunction.checkNull(data.get(46)).toString());
					if(!(CommonFunction.checkNull(data.get(47))).trim().equalsIgnoreCase(""))
					{
					Number reconNum17 =myFormatter.parse((CommonFunction.checkNull(data.get(47))).trim());
					crSchemeMasterVo.setMaxMarginRate(myFormatter.format(reconNum17));
					}
//					if (CommonFunction.checkNull(data.get(47)).toString().equalsIgnoreCase(""))crSchemeMasterVo.setMaxMarginRate("0.000");
//					else
//						crSchemeMasterVo.setMaxMarginRate(myFormatter.format(Double.parseDouble(data.get(47).toString())));
					
					crSchemeMasterVo.setSchemeId(CommonFunction.checkNull(data.get(48)).toString());
					crSchemeMasterVo.setLbxAssetFlag(CommonFunction.checkNull(data.get(49)).toString());
					crSchemeMasterVo.setMinPeriodResch(CommonFunction.checkNull(data.get(50)).toString());
					
					if(!(CommonFunction.checkNull(data.get(51))).trim().equalsIgnoreCase(""))
					{
					Number reconNum18 =myFormatter.parse((CommonFunction.checkNull(data.get(51))).trim());
					crSchemeMasterVo.setCustomerExposureLimit(myFormatter.format(reconNum18));
					}
					
					if(!(CommonFunction.checkNull(data.get(52))).trim().equalsIgnoreCase(""))
					{
					Number reconNum19 =myFormatter.parse((CommonFunction.checkNull(data.get(52))).trim());
					crSchemeMasterVo.setPreEMI(myFormatter.format(reconNum19));
					}
					
					
					if(!(CommonFunction.checkNull(data.get(53))).trim().equalsIgnoreCase(""))
					{
					Number reconNum20 =myFormatter.parse((CommonFunction.checkNull(data.get(53))).trim());
					crSchemeMasterVo.setFixPriod(myFormatter.format(reconNum20));
					}
					
					crSchemeMasterVo.setReviewEvnet(CommonFunction.checkNull(data.get(54)).toString());
					
					if(!(CommonFunction.checkNull(data.get(55))).trim().equalsIgnoreCase(""))
					{
					Number reconNum21 =myFormatter.parse((CommonFunction.checkNull(data.get(55))).trim());
					crSchemeMasterVo.setGapReview(myFormatter.format(reconNum21));
					}
					
					crSchemeMasterVo.setIncrese(CommonFunction.checkNull(data.get(56)).toString());
					crSchemeMasterVo.setDecrese(CommonFunction.checkNull(data.get(57)).toString());
					
					crSchemeMasterVo.setAdditionalDisbAllowed(CommonFunction.checkNull(data.get(58)).toString());
					crSchemeMasterVo.setProductId(CommonFunction.checkNull(data.get(59)).toString());
					crSchemeMasterVo.setMinimumGapBetPrepayAndTer(CommonFunction.checkNull(data.get(60)).toString());
					//crSchemeMasterVo.setPreEMI(CommonFunction.checkNull(data.get(52)).toString());
					crSchemeMasterVo.setValidityDays(CommonFunction.checkNull(data.get(61)).toString());
					crSchemeMasterVo.setExpiryDate(CommonFunction.checkNull(data.get(62)).toString());
					
					if(!(CommonFunction.checkNull(data.get(63))).trim().equalsIgnoreCase(""))
					{
						Number reconNum22 =myFormatter.parse((CommonFunction.checkNull(data.get(63))).trim());
						crSchemeMasterVo.setSchemeExposure(myFormatter.format(reconNum22));
					}
					crSchemeMasterVo.setLbxBranchIds((CommonFunction.checkNull(data.get(64))).trim());
					crSchemeMasterVo.setSelectionAccecc((CommonFunction.checkNull(data.get(65))).trim());
					detailList.add(crSchemeMasterVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	
	public boolean saveAccountDtl(String[] accountFlagList,String[] checkbox,Object ob) {
		CrSchemeMasterVo crSchemeVo = (CrSchemeMasterVo) ob;
		boolean status = false;
		boolean status1 = false;
		logger.info("accountFlagList: "+accountFlagList.length);
		
		ArrayList qryList = new ArrayList();
		ArrayList qryList1 = new ArrayList();
		PrepStmtObject insertPrepStmtObject = null;

		try {
			
			String qry = ("SELECT count(1) FROM tmp_scheme_account_dtl WHERE USER_ID='"+crSchemeVo.getMakerId()+ "'");
			logger.info("IN saveAccountDtl...DAOIMPl......."+qry);
			String st = ConnectionDAO.singleReturn(qry);
			//logger.info("IN saveAccountDtl...DAOIMPl..st....."+st);
			if(!(st.equalsIgnoreCase("0")))
			{
			logger.info("delete query");
			String query1 = "DELETE FROM tmp_scheme_account_dtl WHERE USER_ID='"+crSchemeVo.getMakerId()+ "'";
			qryList1.add(query1);
			status = ConnectionDAO.sqlInsUpdDelete(qryList1);
			logger.info("IN saveAccountDtl...DAOIMPl......DELETE QUERY......"+query1);
			}
			logger.info("IN saveAccountDtl...DAOIMPl..status....."+status);
			
				for (int i = 0; i < accountFlagList.length; i++) {
					
				insertPrepStmtObject = new PrepStmtObject();
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO tmp_scheme_account_dtl (STAGE_ID,ACCOUNT_FLAG,USER_ID)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ? )");

				
				if (CommonFunction.checkNull(checkbox[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(checkbox[i]);

				
				if (CommonFunction.checkNull(accountFlagList[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(accountFlagList[i]);
				
				if (CommonFunction.checkNull(crSchemeVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(crSchemeVo.getMakerId().toUpperCase().trim());
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);

			}

				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger
				.info("IN saveAccountDtl() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				logger.info("In saveAccountDtl......................"+ status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	
	public boolean modifyAccountDtl(String[] accountFlagList,String[] checkbox,Object ob) {
		CrSchemeMasterVo crSchemeVo = (CrSchemeMasterVo) ob;
		boolean status = false;
		boolean status1 = false;
		logger.info("accountFlagList: "+accountFlagList.length);
		String schemId = "";
		ArrayList qryList = new ArrayList();
		ArrayList  qryList1=new ArrayList();
		ArrayList  qryList2=new ArrayList();
		ArrayList  qryList3=new ArrayList();
		ArrayList qryList4 = new ArrayList();
		PrepStmtObject insertPrepStmtObject = null;
		PrepStmtObject insertPrepStmtObject1 = null;

		
		try {
				
//			String query=("SELECT COUNT(1) FROM tmp_scheme_account_dtl WHERE USER_ID='"+crSchemeVo.getMakerId()+ "'");
//			logger.info("select query is********************"+query);
//			
//			String st = ConnectionDAO.singleReturn(query);
//			logger.info("IN saveAccountDtl...DAOIMPl..st....."+st);
//			if(!(st.equalsIgnoreCase("0")))
//			{			
//			String query1=("DELETE FROM tmp_scheme_account_dtl WHERE USER_ID='"+crSchemeVo.getMakerId()+ "' ");
//			qryList2.add(query1);
//			status1 = ConnectionDAO.sqlInsUpdDelete(qryList2);
//			logger.info("staus for delete is ***************"+status1);
//			logger.info("query for delete is *************"+query1);
//			}
//				logger.info("In modifyAccountDtl.....schemeId...."+crSchemeVo.getSchemeId());
//				
//				
//				for (int i = 0; i < accountFlagList.length; i++) {
//					
//					insertPrepStmtObject1 = new PrepStmtObject();
//					StringBuffer bufInsSql1 = new StringBuffer();
//					bufInsSql1.append("INSERT INTO tmp_scheme_account_dtl (STAGE_ID,ACCOUNT_FLAG,USER_ID)");
//					bufInsSql1.append(" values ( ");
//					bufInsSql1.append(" ?,");
//					bufInsSql1.append(" ?,");
//					bufInsSql1.append(" ? )");
//
//					
//					if (CommonFunction.checkNull(checkbox[i]).equalsIgnoreCase(""))
//						insertPrepStmtObject1.addNull();
//					else
//						insertPrepStmtObject1.addString(checkbox[i]);
//					
//					// logger.info("value is ***"+checkbox[i]);
//
//					
//					if (CommonFunction.checkNull(accountFlagList[i]).equalsIgnoreCase(""))
//						insertPrepStmtObject1.addNull();
//					else
//						insertPrepStmtObject1.addString(accountFlagList[i]);
//					// logger.info("value is 111***"+accountFlagList[i]);
//					
//					if (CommonFunction.checkNull(crSchemeVo.getMakerId()).equalsIgnoreCase(""))
//						insertPrepStmtObject1.addNull();
//					else
//						insertPrepStmtObject1.addString(crSchemeVo.getMakerId().toUpperCase().trim());
//					
//					
//					
//					insertPrepStmtObject1.setSql(bufInsSql1.toString());
//					qryList.add(insertPrepStmtObject1);
//					//logger.info("query insert  is 111***"+bufInsSql1.toString());

//				}
				
				// status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				// logger.info("IN saveAccountDtl() insert query1 ### "+ insertPrepStmtObject1.printQuery());
				
				String query2=("SELECT COUNT(1) FROM cr_scheme_accounting_dtl WHERE SCHEME_ID='"+crSchemeVo.getSchemeId()+ "'");
				logger.info("select query is********************"+query2);
				
				String st1 = ConnectionDAO.singleReturn(query2);
				 int k = Integer.parseInt(st1);
				logger.info("IN saveAccountDtl...DAOIMPl..st....."+k);
				if(!(st1.equalsIgnoreCase("0")))
				{			
				String query3=("DELETE FROM cr_scheme_accounting_dtl WHERE SCHEME_ID='"+crSchemeVo.getSchemeId()+ "' ");
				qryList3.add(query3);
				status1 = ConnectionDAO.sqlInsUpdDelete(qryList3);
				logger.info("staus for delete is *account**************"+status1);
				logger.info("query for delete is *****account********"+query3);
				}
				logger.info("acount listttttttttt"+accountFlagList.length);
				for (int i = 0; i < accountFlagList.length; i++) {
					schemId = CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeVo.getSchemeId()));
					
					insertPrepStmtObject = new PrepStmtObject();
				StringBuffer bufInsSql5 = new StringBuffer();
				bufInsSql5.append(" INSERT INTO  cr_scheme_accounting_dtl (SCHEME_ID,STAGE_ID,ACCOUNTING_FLAG)");
				bufInsSql5.append(" values ( ");
				bufInsSql5.append(" ?,");
				bufInsSql5.append(" ?,");
				bufInsSql5.append(" ? )");

				
				
				if (CommonFunction.checkNull(schemId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(schemId);
				
				if (CommonFunction.checkNull(checkbox[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(checkbox[i]);					
				
				if (CommonFunction.checkNull(accountFlagList[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(accountFlagList[i]);			

				
				
				insertPrepStmtObject.setSql(bufInsSql5.toString());
				qryList4.add(insertPrepStmtObject);
				
				// logger.info("In saveAccountDtl......................"+bufInsSql5.toString());
			}

				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList4);
				logger.info("IN saveAccountDtl() insert query1 ### "+ insertPrepStmtObject.printQuery());
				logger.info("In saveAccountDtl......................"+ status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public ArrayList <CrSchemeMasterVo> stageDetailMapping(Object ob)
	{
		CrSchemeMasterVo crSchemeMasterVo= (CrSchemeMasterVo)ob;
		ArrayList searchlist=new ArrayList();
		ArrayList<CrSchemeMasterVo> detailList=new 	ArrayList<CrSchemeMasterVo>();
           try{
                  logger.info("In stageDetailMapping()....................");
                  StringBuffer bufInsSql =	new StringBuffer();
                  
                  String makerId=crSchemeMasterVo.getMakerId();
//                  bufInsSql.append("SELECT STAGE_ID,STAGE_DESC,if(ACCOUNTING_FLAG='Y','Yes','No') AS ACCOUNTING_FLAG ");
                  bufInsSql.append(" SELECT A.STAGE_ID,STAGE_DESC, ");
//                 bufInsSql.append(" IF(B.ACCOUNT_FLAG IS NOT NULL,if(B.ACCOUNT_FLAG='Y','Yes','No'),if(A.ACCOUNTING_FLAG='Y','Yes','No') ) AS ACCOUNTING_FLAG ");
                  bufInsSql.append(" case when B.ACCOUNT_FLAG='Y' then 'Yes' else 'No' end as ACCOUNTING_FLAG ");
                  bufInsSql.append(" FROM cr_stage_m a LEFT OUTER JOIN tmp_scheme_account_dtl b ON A.STAGE_ID = B.STAGE_ID AND USER_ID='"+makerId+"' WHERE A.ACCOUNTING_FLAG='Y'");
  			      
  			      logger.info("In stageDetailMapping() Query is:: "+bufInsSql.toString());
  			      
	              searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

                  logger.info("In accountDetailMappingDao()...................."+searchlist);

                  for(int i=0;i<searchlist.size();i++){
                  ArrayList data=(ArrayList)searchlist.get(i);

                  if(data.size()>0){
                	  CrSchemeMasterVo crAccountVo= new CrSchemeMasterVo();
                	  crAccountVo.setStageId(CommonFunction.checkNull(data.get(0)).toString());
                	  crAccountVo.setStageDes(CommonFunction.checkNull(data.get(1)).toString());
                	  crAccountVo.setAccountingFlag(CommonFunction.checkNull(data.get(2)).toString());
                	 

                  detailList.add(crAccountVo);
                }

			}

			}catch(Exception e){
				e.printStackTrace();
			}

			return detailList;
	}
	
	
	public ArrayList <CrSchemeMasterVo> accountDetailMapping(Object ob)
	{
		String schemeId = "";
		CrSchemeMasterVo crSchemeMasterVo= (CrSchemeMasterVo)ob;
		ArrayList searchlist=new ArrayList();
		ArrayList<CrSchemeMasterVo> detailList=new 	ArrayList<CrSchemeMasterVo>();
           try{
                  logger.info("In accountDetailMappingDao()....................");
      				schemeId = CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getSchemeId()));
                    logger.info("In accountDetailMappingDao()schemeId...................."+schemeId);

                  StringBuffer bufInsSql =	new StringBuffer();

                  bufInsSql.append("SELECT A.STAGE_ID,A.STAGE_DESC,case when V.ACCOUNTING_FLAG='Y' then 'Yes' else 'No' end as ACCOUNTING_FLAG FROM cr_scheme_accounting_dtl V right join cr_stage_m A on A.STAGE_ID=V.STAGE_ID and SCHEME_ID='"+schemeId+"' ");
                  bufInsSql.append(" WHERE a.ACCOUNTING_FLAG='Y' ");
  			     // bufInsSql.append(" WHERE SCHEME_ID='"+schemeId+"' ");
  			      logger.info("Query "+bufInsSql.toString());
	              searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
                  
                  logger.info("In accountDetailMappingDao()...................."+searchlist);

                  for(int i=0;i<searchlist.size();i++){
                  ArrayList data=(ArrayList)searchlist.get(i);

                  if(data.size()>0){
                	  CrSchemeMasterVo crAccountVo= new CrSchemeMasterVo();
                	  crAccountVo.setStageId(CommonFunction.checkNull(data.get(0)).toString());
                	  crAccountVo.setStageDes(CommonFunction.checkNull(data.get(1)).toString());
                	  crAccountVo.setAccountingFlag(CommonFunction.checkNull(data.get(2)).toString());

                  detailList.add(crAccountVo);
                }

			}

			}catch(Exception e){
				e.printStackTrace();
			}

			return detailList;
	}
	
		// code by yogesh for update Scheme code details

	public boolean saveModifySchemeDetailsDao(Object ob,String ratMet,String rwEve)
	{

		boolean status = false;
		CrSchemeMasterVo crSchemeMasterVo = (CrSchemeMasterVo) ob;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		String stat = "";
		String reschAllowed = "";
		String deferralAllowed = "";
		String prepayAllowed = "";
		String terminationAllowed = "";
		String additionalDisbAllowed="";
		String allBranchChecked=CommonFunction.checkNull(crSchemeMasterVo.getAllselection());

		try {

			if (crSchemeMasterVo.getStatus() != null
					&& crSchemeMasterVo.getStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}

			if (crSchemeMasterVo.getReschAllowed() != null
					&& crSchemeMasterVo.getReschAllowed().equals("on")) {
				reschAllowed = "Y";
			} else {
				reschAllowed = "N";

			}

			if (crSchemeMasterVo.getDeferralAllowed() != null
					&& crSchemeMasterVo.getDeferralAllowed().equals("on")) {
				deferralAllowed = "Y";
			} else {
				deferralAllowed = "N";

			}

			if (crSchemeMasterVo.getPrepayAllowed() != null
					&& crSchemeMasterVo.getPrepayAllowed().equals("on")) {
				prepayAllowed = "Y";
			} else {
				prepayAllowed = "N";

			}

			if (crSchemeMasterVo.getTerminationAllowed() != null
					&& crSchemeMasterVo.getTerminationAllowed().equals("on")) {
				terminationAllowed = "Y";
			} else {
				terminationAllowed = "N";

			}
			
			if (crSchemeMasterVo.getAdditionalDisbAllowed() != null
					&& crSchemeMasterVo.getAdditionalDisbAllowed().equals("on")) {
				additionalDisbAllowed = "Y";
			} else {
				additionalDisbAllowed = "N";

			}

			logger.info("In Update Scheme CodeMaster()........");
			String expDate=CommonFunction.changeFormat(crSchemeMasterVo.getExpiryDate());
			logger.info("Expiry Date : " + expDate);
			StringBuffer bufInsSql = new StringBuffer();

			bufInsSql.append(" UPDATE cr_scheme_m SET PRODUCT_ID=?,SCHEME_DESC=?,SCHEME_DESC_L=?,MIN_AMT_FIN=?,MAX_AMT_FIN=?,MIN_MARGIN_RATE=?, ");
			bufInsSql.append(" MAX_MARGIN_RATE=?,DEFAULT_MARGIN_RATE=?,RATE_TYPE=?,RATE_METHOD=?,BASE_RATE_TYPE=?,MIN_FLAT_RATE=?, ");
			bufInsSql.append(" MAX_FLAT_RATE=?,DEF_FLAT_RATE=?,MIN_EFF_RATE=?,MAX_EFF_RATE=?,DEF_EFF_RATE=?, ");
			bufInsSql.append(" MIN_IRR=?,MAX_IRR=?,MIN_TENURE=?,MAX_TENURE=?,DEF_TENURE=?,REPAYMENT_FREQ=?,");
			bufInsSql.append(" INSTALLMENT_TYPE=?,REPAYMENT_MODE=?,INSTALLMENT_MODE=?,MINIMUM_PERIOD_RESCH=?,RESCH_ALLOWED=?, ");
			bufInsSql.append(" RESCH_LOCKIN_PERIOD=?,NUMBER_RESCH_ALLOWED_YEAR=?,NUMBER_RESCH_ALLOWED_TOTAL=?, ");
			bufInsSql.append(" MINIMUM_GAP_RESCH=?,DEFERRAL_ALLOWED=?,DEFR_LOCKIN_PERIOD=?,MAXIMUM_DEFR_MONTHS_ALLOWED=?, ");
			bufInsSql.append(" MAXIMUM_DEFR_MONTHS_TOTAL=?,MINIMUM_GAP_DEFR=?, ");
			bufInsSql.append(" NUMBER_DEFR_ALLOWED_YEAR=?,NUMBER_DEFR_ALLOWED_TOTAL=?,PREPAY_ALLOWED=?, ");
			bufInsSql.append(" NUMBER_PREPAY_ALLOWED_YEAR=?,MINIMUM_GAP_PREPAY=?, ");
			bufInsSql.append(" MINIMUM_PREPAY_PERCENT=?,MAXIMUM_PREPAY_PERCENT=?,TERMINATION_ALLOWED=?, ");
			bufInsSql.append(" MINIMUM_GAP_TERMINATION=?,PREPAY_LOCKIN_PERIOD=?,NUMBER_PREPAY_ALLOWED_TOTAL=?,TERMINATION_LOCKIN_PERIOD=?,REC_STATUS=?,CUSTOMER_EXPOSURE_LIMIT=?,PRE_EMI_INTEREST_RATE=?,");
			bufInsSql.append(" FLOATING_FIXED_PERIOD=?,FLOATING_REVIEW_EVENT=?,MIN_GAP_FLOATING=?,FLOATING_TYPE_INCREASE=?,FLOATING_TYPE_DECREASE=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("ADDITIONAL_DISBURSAL_ALLOWED=?,MINIMUM_GAP_BT_PREPAY_TERM=?,Validity_Days=?,Expiry_Date=?,EXPOSURE_AMOUNT = ? ,BRANCH_IDS=?,BRANCH_ACCESS=? WHERE SCHEME_ID=? ");

			if (CommonFunction.checkNull(crSchemeMasterVo.getLbxProductID())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getLbxProductID().trim());

			if (CommonFunction.checkNull(crSchemeMasterVo.getSchemeDesc())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getSchemeDesc()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(crSchemeMasterVo.getSchemeDesc())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getSchemeDesc()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getMinAmountFin()).trim()).equalsIgnoreCase(""))
		          insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(crSchemeMasterVo.getMinAmountFin()).trim()).toString());
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getMaxAmountFin()).trim()).equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(crSchemeMasterVo.getMaxAmountFin()).trim()).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinMarginRate()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinMarginRate()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxMarginRate()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxMarginRate()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDefaultMarginRate()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDefaultMarginRate()).trim())).toString());

			if (CommonFunction.checkNull(crSchemeMasterVo.getRateType())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getRateType()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(ratMet).trim())
					.equalsIgnoreCase(""))
                             insertPrepStmtObject.addNull();
	        else
		             insertPrepStmtObject.addString(StringEscapeUtils.
		            		 escapeSql(CommonFunction.checkNull(ratMet).trim()).toString());
			
			if (CommonFunction.checkNull(crSchemeMasterVo.getBaseRateType())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getBaseRateType().toUpperCase().trim());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinFlatRate()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinFlatRate()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxFlatRate()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxFlatRate()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDefFlatRate()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDefFlatRate()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinEffRate()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinEffRate()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxEffRate()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxEffRate()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDefEffRate()).trim())
				.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDefEffRate()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinIrr()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinIrr()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxIrr()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxIrr()).trim())).toString());
			

			if ((StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinTenure()).trim())
					.equals(""))||(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinTenure()).trim())
							.equals("0")))
		          insertPrepStmtObject.addString("1");
	        else
		             insertPrepStmtObject.addString(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinTenure()).trim()).toString());
			

			if ((StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxTenure()).trim())
				.equals(""))||(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxTenure()).trim())
						.equals("0")))
		          insertPrepStmtObject.addString("1");
	        else
		             insertPrepStmtObject.addString(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaxTenure()).trim()).toString());
			

			if ((StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDefTenure()).trim())
				.equals(""))||(StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDefTenure()).trim())
						.equals("0")))
		          insertPrepStmtObject.addString("1");
	        else
		             insertPrepStmtObject.addString(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDefTenure()).trim()).toString());

			if (CommonFunction.checkNull(crSchemeMasterVo.getRepaymentFreq())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getRepaymentFreq().toUpperCase().trim());

			if (CommonFunction.checkNull(crSchemeMasterVo.getInstallmentType())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getInstallmentType().toUpperCase().trim());

			if (CommonFunction.checkNull(crSchemeMasterVo.getRepaymentMode())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getRepaymentMode().toUpperCase().trim());

			if (CommonFunction.checkNull(crSchemeMasterVo.getInstallmentMode())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getInstallmentMode().toUpperCase().trim());

			if (CommonFunction.checkNull(crSchemeMasterVo.getMinPeriodResch())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getMinPeriodResch().toUpperCase().trim());

			if (CommonFunction.checkNull(reschAllowed).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(reschAllowed);

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getReschLockinPeriod()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getReschLockinPeriod().toUpperCase().trim());

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getNumberReschAllowedYear())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getNumberReschAllowedYear().toUpperCase().trim());

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getNumberReschAllowedTotal())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getNumberReschAllowedTotal().toUpperCase().trim());

			if (CommonFunction.checkNull(crSchemeMasterVo.getMinimumGapResch())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getMinimumGapResch().toUpperCase().trim());

			if (CommonFunction.checkNull(deferralAllowed).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(deferralAllowed);

			if (CommonFunction
					.checkNull(crSchemeMasterVo.getDefrLockinPeriod())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getDefrLockinPeriod().toUpperCase().trim());

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getMaximumDefrMonthsAllowed())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getMaximumDefrMonthsAllowed().toUpperCase().trim());

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getMaximumDefrMonthsTotal())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getMaximumDefrMonthsTotal().toUpperCase().trim());

			if (CommonFunction.checkNull(crSchemeMasterVo.getMinimumGapDefr())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getMinimumGapDefr().toUpperCase().trim());

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getNumberDefrAllowedYear())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getNumberDefrAllowedYear().toUpperCase().trim());

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getNumberDefrAllowedTotal())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getNumberDefrAllowedTotal().toUpperCase().trim());

			if (CommonFunction.checkNull(prepayAllowed).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(prepayAllowed);

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getNumberPrepayAllowedYear())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getNumberPrepayAllowedYear().toUpperCase().trim());

			if (CommonFunction
					.checkNull(crSchemeMasterVo.getMinimumGapPrepay())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getMinimumGapPrepay().toUpperCase().trim());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinimumPrepayPercent()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMinimumPrepayPercent()).trim())).toString());
			

			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaximumPrepayPercent()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getMaximumPrepayPercent()).trim())).toString());

			if (CommonFunction.checkNull(terminationAllowed).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(terminationAllowed);

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getMinimumGapTermination())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getMinimumGapTermination().toUpperCase().trim());

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getPrepayLockinPeriod()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getPrepayLockinPeriod().toUpperCase().trim());

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getNumberPrepayAllowedTotal())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getNumberPrepayAllowedTotal().toUpperCase().trim());

			if (CommonFunction.checkNull(
					crSchemeMasterVo.getTerminationLockinPeriod())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo
						.getTerminationLockinPeriod().toUpperCase().trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getCustomerExposureLimit()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getCustomerExposureLimit()).trim())).toString());
			
			/*if (CommonFunction.checkNull(crSchemeMasterVo.getPreEMI()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getPreEMI().toUpperCase().trim());*/
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getPreEMI()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getPreEMI()).trim())).toString());
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getFixPriod()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getFixPriod()).trim())).toString());
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(rwEve).trim())
					.equalsIgnoreCase(""))
                             insertPrepStmtObject.addNull();
	        else
		             insertPrepStmtObject.addString(StringEscapeUtils.
		            		 escapeSql(CommonFunction.checkNull(rwEve).trim()).toString());
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getGapReview()).trim())
					.equalsIgnoreCase(""))
				 insertPrepStmtObject.addFloat(0.00);
	        else
		             insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getGapReview()).trim())).toString());
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getIncrese()).trim())
					.equalsIgnoreCase(""))
                             insertPrepStmtObject.addNull();
	        else
		             insertPrepStmtObject.addString(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getIncrese()).trim()).toString());
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getDecrese()).trim())
					.equalsIgnoreCase(""))
                             insertPrepStmtObject.addNull();
	        else
		             insertPrepStmtObject.addString(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getDecrese()).trim()).toString());
			//----------------------------------
			if (CommonFunction.checkNull(crSchemeMasterVo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getMakerId());
			if (CommonFunction.checkNull(crSchemeMasterVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getMakerDate());
			//----------------------------------
			
			//Ritu Start
			if (CommonFunction.checkNull(additionalDisbAllowed).equalsIgnoreCase(""))
                insertPrepStmtObject.addNull();
	        else
		        insertPrepStmtObject.addString(additionalDisbAllowed);
			
			//End
			if (CommonFunction.checkNull(crSchemeMasterVo.getMinimumGapBetPrepayAndTer()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getMinimumGapBetPrepayAndTer().toUpperCase().trim());
			
			if (CommonFunction.checkNull(crSchemeMasterVo.getValidityDays()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getValidityDays());
			
			if (CommonFunction.checkNull(expDate).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(expDate);
			
			// Scheme master added by sanjog
			
			if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(crSchemeMasterVo.getSchemeExposure()).trim())			.equalsIgnoreCase(""))
                             insertPrepStmtObject.addString("0.00");
	        else
		        insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.
				  escapeSql(CommonFunction.checkNull(crSchemeMasterVo.getSchemeExposure()).trim())).toString());
			// Scheme master added by sanjog
			// manish for branch Modification
			String branchIds="";
			if(CommonFunction.checkNull(allBranchChecked).trim().equalsIgnoreCase("S"))
			{
        		branchIds=CommonFunction.checkNull(crSchemeMasterVo.getLbxBranchIds()).trim();
			}
			else
			{
				String qr="SELECT STUFF((SELECT '|' + CAST(BRANCH_ID AS varchar(10)) FROM com_branch_m WHERE ISNULL(REC_STATUS,'A')='A' FOR XML PATH ('')), 1, 1, '')";
				branchIds=ConnectionDAO.singleReturn(qr);
			}
			insertPrepStmtObject.addString(CommonFunction.checkNull(branchIds).trim());
				
			logger.info("In Scheme Master Update>>>>>>> all selection flag......"+ crSchemeMasterVo.getAllselection());
			if (CommonFunction.checkNull(crSchemeMasterVo.getAllselection()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getAllselection());
				
			
			if (CommonFunction.checkNull(crSchemeMasterVo.getSchemeId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(crSchemeMasterVo.getSchemeId());
			
			

			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			
			String branchDelete="delete from cr_scheme_branch_mapping_dtl where scheme_id='"+CommonFunction.checkNull(crSchemeMasterVo.getSchemeId()).trim()+"' ";
			PrepStmtObject istmt = new PrepStmtObject();
        	istmt.setSql(branchDelete);	
        	qryList.add(istmt);	
        	if(CommonFunction.checkNull(allBranchChecked).trim().equalsIgnoreCase("S"))
        	{
	        	String arr[]=(CommonFunction.checkNull(crSchemeMasterVo.getLbxBranchIds()).trim()).split("\\|");
	            for(int i=0; i<arr.length;i++)
	            {
	            	PrepStmtObject insStmt = new PrepStmtObject();
	            	String qry="INSERT INTO cr_scheme_branch_mapping_dtl(SCHEME_ID,BRANCH_ID,REC_STATUS)" +
	            			"VALUES("+CommonFunction.checkNull(crSchemeMasterVo.getSchemeId()).trim()+","+arr[i]+",'A')";
	            	insStmt.setSql(qry);	
	            	qryList.add(insStmt);	
	            }  
        	}
        	else 
        	{
        	  PrepStmtObject insStmt = new PrepStmtObject();
        	  String qry= " INSERT INTO cr_scheme_branch_mapping_dtl(SCHEME_ID,BRANCH_ID,REC_STATUS) " +
        				  " SELECT "+CommonFunction.checkNull(crSchemeMasterVo.getSchemeId()).trim()+", "+
        				  " BRANCH_ID,'A' from com_branch_m where isnull(REC_STATUS,'A')='A'";
        	  logger.info("In getListOfValues" + qry.toString());
        	  insStmt.setSql(qry);	
          	  qryList.add(insStmt);
          	insStmt=null;
          	qry=null;
        	  
        	}
			
			
			logger.info("In getListOfValues" + bufInsSql);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("query is .fererwerewrewrf.." + qryList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			 stat = null;
			 reschAllowed = null;
			 deferralAllowed = null;
			 prepayAllowed = null;
			 terminationAllowed = null;
			 additionalDisbAllowed= null;
			 allBranchChecked= null;
		}

		return status;
	}

	// CODE FOR NPA STAGE MASTER BY RITU

	// kanika code getbeforemakerdate()
	public String getbeforemakerdate() {
		Connection con = null;

		CallableStatement cs = null;
		ResultSet rs = null;
		String logindate = null;

		logger.info("Enter for DB isUserExist action");
		try {
			con = ConnectionDAOforEJB.getConnection();
			if (con != null) {
				StringBuilder login = new StringBuilder();
				login.append("SELECT ");
				login.append(dbo);
				login.append("DATE_FORMAT(PARAMETER_VALUE,'"+dateFormat+"') FROM parameter_mst WHERE PARAMETER_KEY='BUSINESS_DATE'");
				logindate = ConnectionDAOforEJB.singleReturn(login.toString());
				logger.info("DB isUserExist query : " + login.toString());
			}

		} finally {
			ConnectionDAOforEJB.closeConnection(con, cs, rs);
		}

		return logindate;

	}

	// kanika's code getlogintimeid()

	public String getlogintimeid(String userName) {
		String userid = null;
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		logger.info("Enter for DB getlogintimeid action");
		try {
			con = ConnectionDAOforEJB.getConnection();
			if (con != null) {

				String sqluserid = "select USER_ID from sec_user_m where USER_ID='"
						+ userName + "'";
				userid = ConnectionDAOforEJB.singleReturn(sqluserid);

				logger.info("----------userid-------" + userid);

			}

		} finally {
			ConnectionDAOforEJB.closeConnection(con, cs, rs);
		}

		logger.info("----------userid-------" + userid);
		return userid;
	}

// CODE FOR NPA STAGE MASTER BY RITU

	public ArrayList<NPAMasterVo> getNPAStageData() {
		
		logger.info("In getNPAStageData.....................................Dao Impl");
		ArrayList list = null;
		ArrayList<NPAMasterVo> detailList = new ArrayList<NPAMasterVo>();
		StringBuffer bufInsSql = new StringBuffer();
		NPAMasterVo vo=null;
		ArrayList data=null;
		try {
			
			
			bufInsSql.append(" SELECT NPA_STAGE,SEQ_NO,NPA_CRITERIA_FLAG,NPA_CRITERIA_VALUE,MOVE_TO_NEXT,MOVE_TO_PREV,");
			bufInsSql.append(" case when BILLING_FLAG='Y' then 'Yes' else 'No' end as BILLING_FLAG,case when ACCRUAL_FLAG='Y' then 'Yes' else 'No' end as ACCRUAL_FLAG,");
			bufInsSql.append(" case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,iif(SD_ACCRUAL_FLAG='Y','Yes','No')as SD_ACCRUAL_FLAG FROM cr_npa_m ORDER by NPA_STAGE");
			list = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
            int size=list.size();
			for (int i = 0; i < size; i++) {
			
				 data = (ArrayList) list.get(i);

				if (data.size() > 0) {
					vo = new NPAMasterVo();
					vo.setNpaStage(CommonFunction.checkNull(data.get(0)).toString());
					vo.setSequenceNo(CommonFunction.checkNull(data.get(1)).toString());
					vo.setNpaCriteria(CommonFunction.checkNull(data.get(2)).toString());
					vo.setNpaCriteriaValue(CommonFunction.checkNull(data.get(3)).toString());
					vo.setMoveToNext(CommonFunction.checkNull(data.get(4)).toString());
					vo.setMoveToPrevious(CommonFunction.checkNull(data.get(5)).toString());
					vo.setBillingFlagStatus(CommonFunction.checkNull(data.get(6)).toString());
					vo.setAccrualFlagStatus(CommonFunction.checkNull(data.get(7)).toString());
					vo.setNpaStageStatus(CommonFunction.checkNull(data.get(8)).toString());
					vo.setSdAccrualFlag(CommonFunction.checkNull(data.get(9)).toString());
					detailList.add(vo);
				}
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			bufInsSql=null;
			list.clear();
			list=null;
			vo=null;
			data.clear();
			data=null;
		}
		return detailList;
	}

	public String insertNPAStageMaster(Object ob) 
	{
		logger.info("In insertNPAStageMaster() of MasterDAOImpl");
		String result=null;
		NPAMasterVo vo = (NPAMasterVo) ob;
		boolean status =false;
		StringBuffer query =new StringBuffer();
		ArrayList qryList=null;
		PrepStmtObject insertPrepStmtObject =null;
		StringBuffer bufInsSql = new StringBuffer();
		if(CommonFunction.checkNull(vo.getNpaStage()).trim().equalsIgnoreCase("REGULAR"))
			result="REGULAR";
		else
		{
			
			query.append("select NPA_STAGE,SEQ_NO from cr_npa_m where  NPA_STAGE='"+ StringEscapeUtils.escapeSql(vo.getNpaStage().trim())+ "' ");
			query.append("	or SEQ_NO='"+ StringEscapeUtils.escapeSql(vo.getSequenceNo().trim())+ "' ");
			 logger.info("In insertNPAStageMaster() of MasterDAOImpl Query for check Existing NPA_STAGE and SEQ_NO : "+ query.toString());
			    try
			    {
			    	status = ConnectionDAOforEJB.checkStatus(query.toString());
			    	query=null;
			    }
			    catch (Exception e) 
			    {
			    	e.printStackTrace();
			    	result="ERROR";
				}
			    query=null;
			    if(status)
			       	result="EXIST";
				else
			    {
			    	 qryList = new ArrayList();
					 insertPrepStmtObject = new PrepStmtObject();
					String stat = "X";
					String stat1 = "N";
					String stat2 = "N";
					String stat3 = "N";
					
					if (vo.getNpaStageStatus() != null 	&& vo.getNpaStageStatus().equals("on")) 
						stat = "A";					
					if (vo.getBillingFlagStatus() != null && vo.getBillingFlagStatus().equals("on")) 
						stat1 = "Y";					
					if (vo.getAccrualFlagStatus() != null && vo.getAccrualFlagStatus().equals("on")) 
						stat2 = "Y";
					if (vo.getSdAccrualFlag() != null && vo.getSdAccrualFlag().equals("on")) 
						stat3 = "Y";
					
					
				    bufInsSql = new StringBuffer();
					bufInsSql.append("insert into cr_npa_m(NPA_STAGE,SEQ_NO,NPA_CRITERIA_FLAG,NPA_CRITERIA_VALUE,MOVE_TO_NEXT,MOVE_TO_PREV,BILLING_FLAG,ACCRUAL_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,SD_ACCRUAL_FLAG )");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");// NPA STAGE
					bufInsSql.append(" ?,");// SEQUENCE NO
					bufInsSql.append(" ?,");// NPA CRITERIA
					bufInsSql.append(" ?,");// VALUE
					bufInsSql.append(" ?,");// MOVE TO NEXT
					bufInsSql.append(" ?,");// MOVE TO PREVIOUS
					bufInsSql.append(" ?,");// BILLING FLAG
					bufInsSql.append(" ?,");// ACCURAL FLAG
					bufInsSql.append(" ?,");// REC STATUS
					bufInsSql.append(" ?,");// MAKER ID
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					bufInsSql.append(" ?,");// AUTHOR ID
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					bufInsSql.append(" ?)");//SD_ACCRUAL_FLAG 
					
					if (CommonFunction.checkNull(vo.getNpaStage()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getNpaStage().toUpperCase().trim());

					if (CommonFunction.checkNull(vo.getSequenceNo()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getSequenceNo().toUpperCase().trim());

					if (CommonFunction.checkNull(vo.getNpaCriteria()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getNpaCriteria().trim());

					if (CommonFunction.checkNull(vo.getNpaCriteriaValue()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getNpaCriteriaValue().trim());

					if (CommonFunction.checkNull(vo.getMoveToNext()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMoveToNext().trim());

					if (CommonFunction.checkNull(vo.getMoveToPrevious()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMoveToPrevious().trim());

					if (CommonFunction.checkNull(stat1).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat1);

					if (CommonFunction.checkNull(stat2).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat2);

					if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);

					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());

					if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());

					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());

					if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());
					
					if (CommonFunction.checkNull(stat3).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat3);

					insertPrepStmtObject.setSql(bufInsSql.toString());
					
					logger.info("In insertNPAStageMaster() of MasterDAOImpl Query for insert record :  "+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					boolean flag = false;
					try
					{
						flag=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
						bufInsSql=null;
						insertPrepStmtObject=null;
					    if(flag)
					      	result="SAVE";
					}
					catch (Exception e) 
				    {
				    	e.printStackTrace();
				    	result="ERROR";
					}
					finally
					{
						insertPrepStmtObject=null;
						query=null;
						qryList.clear();
						qryList=null;
						vo=null;
						bufInsSql=null;
						stat = null;
						stat1 = null;
						stat2 = null;
						stat3=null;
						ob=null;
						
					}
						 	
			    }
		}
		return result;
	}


	public ArrayList<NPAMasterVo> searchNPAStageData(Object ob) {
		String npaSearchStage = "";
		String sequenceNoSearch = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList data=null;
		NPAMasterVo vo=null;
		StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		ArrayList searchlist = new ArrayList();
		NPAMasterVo npaMasterVo = (NPAMasterVo) ob;
		ArrayList<NPAMasterVo> detailList = new ArrayList<NPAMasterVo>();
		try {

			logger.info("In searchNPAStageData().....................................Dao Impl");
			
			npaSearchStage = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(npaMasterVo.getNpaSearchStage())).trim());
			sequenceNoSearch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(npaMasterVo.getSequenceNoSearch())).trim());

			bufInsSql.append(" SELECT NPA_STAGE,SEQ_NO,case when NPA_CRITERIA_FLAG='D' then 'DPD' else 'Installments' end as NPA_CRITERIA,");
			bufInsSql.append(" NPA_CRITERIA_VALUE,");
			bufInsSql.append(" case when MOVE_TO_NEXT='M' then 'Manual' else 'Auto' end as NEXT_STAGE,");
			bufInsSql.append(" case when MOVE_TO_PREV='M' then 'Manual' else 'Auto' end as PREVIOUS_STAGE,");
			bufInsSql.append(" case when BILLING_FLAG='Y' then 'Yes' else 'No' end as BILLING_FLAG,case when ACCRUAL_FLAG='Y' then 'Yes' else 'No' end as ACCRUAL_FLAG,");
			bufInsSql.append(" case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,NPA_CRITERIA_FLAG,MOVE_TO_NEXT,MOVE_TO_PREV,iif(SD_ACCRUAL_FLAG='Y','Yes','No')as SD_ACCRUAL_FLAG ");
			bufInsSql.append(" FROM cr_npa_m ");

			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_npa_m ");
			
			if ((!(npaSearchStage.equalsIgnoreCase(""))) && (!(sequenceNoSearch.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE NPA_STAGE ='" + npaSearchStage + "' AND SEQ_NO='" + sequenceNoSearch + "' ");
				bufInsSqlTempCount.append(" WHERE NPA_STAGE ='" + npaSearchStage + "' AND SEQ_NO='" + sequenceNoSearch + "' ");
			} 
			else if (!sequenceNoSearch.equals("")) {
				bufInsSql.append(" WHERE SEQ_NO='" + sequenceNoSearch + "' ");
				bufInsSqlTempCount.append(" WHERE SEQ_NO='" + sequenceNoSearch + "' ");
			}

			else if (!npaSearchStage.equals("")) {
				bufInsSql.append(" WHERE NPA_STAGE = '" + npaSearchStage + "' ");
				bufInsSqlTempCount.append(" WHERE NPA_STAGE = '" + npaSearchStage + "' ");
			}
					
			logger.info("search Query...." + bufInsSql);

			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			
			logger.info("current PAge Link no .................... "+npaMasterVo.getCurrentPageLink());
			if(npaMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (npaMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
			}
			bufInsSql.append(" ORDER BY NPA_STAGE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchNPAStageData() search query1 ### "+ bufInsSql.toString());

			int size=searchlist.size();
			for (int i = 0; i < size; i++) {


				 data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					 vo = new NPAMasterVo();

					vo.setNpaStageModify("<a href=npaStageMasterSearch.do?method=openEditNPAStage&npaSearchStage="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					vo.setNpaStage(CommonFunction.checkNull(data.get(0)).toString());
					vo.setSequenceNo(CommonFunction.checkNull(data.get(1)).toString());
					vo.setNpaCriteriaModify(CommonFunction.checkNull(data.get(2)).toString());
					vo.setNpaCriteriaValue(CommonFunction.checkNull(data.get(3)).toString());

					vo.setMoveToNextModify(CommonFunction.checkNull(data.get(4)).toString());
					vo.setMoveToPreviousModify(CommonFunction.checkNull(data.get(5)).toString());
					vo.setBillingFlagStatus(CommonFunction.checkNull(data.get(6)).toString());
					vo.setAccrualFlagStatus(CommonFunction.checkNull(data.get(7)).toString());
					vo.setNpaStageStatus(CommonFunction.checkNull(data.get(8)).toString());

					vo.setNpaCriteria(CommonFunction.checkNull(data.get(9)).toString());
					vo.setMoveToNext(CommonFunction.checkNull(data.get(10)).toString());
					vo.setMoveToPrevious(CommonFunction.checkNull(data.get(11)).toString());
					vo.setSdAccrualFlag(CommonFunction.checkNull(data.get(12)).toString());
					vo.setTotalRecordSize(count);
					detailList.add(vo);
				}

			}

			

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			bufInsSql=null;
			bufInsSqlTempCount=null;
			searchlist.clear();
			searchlist = null;
			npaMasterVo = null;
			ob=null;
			data.clear();
			data=null;
			vo=null;
			npaSearchStage = null;
			sequenceNoSearch=null;

		}


		return detailList;
	}

	
	//neeraj
	public String updateNPAStageData(Object ob) 
	{
		String result=null;
		logger.info("In updateNPAStageData() of MasterDAOImpl");
		NPAMasterVo vo = (NPAMasterVo) ob;
		StringBuffer query =new StringBuffer();
		ArrayList updatelist = null;	   
		PrepStmtObject insertPrepStmtObject = null;
		StringBuffer bufInsSql=null;
		boolean status =false;
	    query.append("select SEQ_NO from cr_npa_m where SEQ_NO ='"+ StringEscapeUtils.escapeSql(vo.getSequenceNo().trim())+ "'" );
		query.append(" AND NPA_STAGE <> '"+ StringEscapeUtils.escapeSql(vo.getNpaStage().trim()) + "'");
	    logger.info("In updateNPAStageData() of MasterDAOImpl Query for check Existing SEQ_NO : "+ query.toString());
	    try
	    {
	    	status = ConnectionDAOforEJB.checkStatus(query.toString());
	    	query=null;
	    }
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
	    	result="ERROR";
		}
	    if(status)
	       	result="EXIST";
		else
	    {
			 updatelist = new ArrayList();	   
			 insertPrepStmtObject = new PrepStmtObject();
			String stat = null;
			String stat1 = "N";
			String stat2 = null;
			String stat3 = "N";
			
			if (vo.getNpaStageStatus() != null && vo.getNpaStageStatus().equals("on")) 
					stat = "A";
			if (vo.getBillingFlagStatus() != null && vo.getBillingFlagStatus().equals("on")) 
					stat1 = "Y";
			if (vo.getAccrualFlagStatus() != null && vo.getAccrualFlagStatus().equals("on")) 
					stat2 = "Y";
			if (vo.getSdAccrualFlag() != null && vo.getSdAccrualFlag().equals("on")) 
				stat3 = "Y";
			
			
			 bufInsSql = new StringBuffer();
			
			bufInsSql.append("UPDATE cr_npa_m set SEQ_NO=?,NPA_CRITERIA_FLAG=?,NPA_CRITERIA_VALUE=?,MOVE_TO_NEXT=?,MOVE_TO_PREV=?,BILLING_FLAG=?,ACCRUAL_FLAG=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),SD_ACCRUAL_FLAG=?");
			bufInsSql.append(" where NPA_STAGE=?");

			if (CommonFunction.checkNull(vo.getSequenceNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getSequenceNo().toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getNpaCriteria()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getNpaCriteria().trim());

			if (CommonFunction.checkNull(vo.getNpaCriteriaValue()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getNpaCriteriaValue().trim());

			if (CommonFunction.checkNull(vo.getMoveToNext()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMoveToNext().trim());

			if (CommonFunction.checkNull(vo.getMoveToPrevious()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMoveToPrevious().trim());

			if (CommonFunction.checkNull(stat1).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat1);

			if (CommonFunction.checkNull(stat2).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat2);

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			
			if (CommonFunction.checkNull(stat3).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat3);
			
			if (CommonFunction.checkNull(vo.getNpaStage()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getNpaStage().toUpperCase().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);

			logger.info("In query" + insertPrepStmtObject.printQuery());
			
			boolean st=false;
			try
			{
				st = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
				if(st)
				result="UPDATE";
			}
			catch (Exception e) 
		    {
		    	e.printStackTrace();
		    	result="ERROR";
			}
			finally
			{
				bufInsSql = null;
				insertPrepStmtObject = null;
				 stat = null;
				 stat1 = null;
				 stat2 = null;
				 stat3 =null;
				 ob=null;
				 vo = null;
				 query =null;
				 updatelist.clear();
				 updatelist = null;
				 

			}
				
		}
		return result;
}

	// Code by Yogesh for Bank Account Master Grid Data

	public ArrayList<BankAccountMasterVo> searchBankAccountData(Object ob) {
		ArrayList searchlist = new ArrayList();
		String query1="";
		Connection con=null;
		ResultSet rs2 = null;
		Statement stmt = null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		String bankCode = "";
		String bankBranchName = "";
		String accountNo="";
		BankAccountMasterVo bankAccountMasterVo = (BankAccountMasterVo) ob;
		ArrayList<BankAccountMasterVo> detailList = new ArrayList<BankAccountMasterVo>();
		try {
			
			logger.info("In searchBankAccountData...............");
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bankCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankAccountMasterVo.getLbxBankSearchID())).trim());
			bankBranchName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankAccountMasterVo.getLbxBranchSearchID())).trim());
			accountNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankAccountMasterVo.getAccountNo())).trim());
			
			logger.info("bankAccountMasterVo.....   accountNo.." + accountNo);

			bufInsSql.append("SELECT BANK_ID,(SELECT A.BANK_NAME FROM COM_BANK_M A WHERE A.BANK_ID=V.BANK_ID ) AS BANK_NAME,");
			bufInsSql.append(" BANK_BRANCH_ID,(SELECT B.BANK_BRANCH_NAME FROM COM_BANKBRANCH_M B WHERE B.BANK_BRANCH_ID=V.BANK_BRANCH_ID ) AS BANK_BRANCH_NAME,");
			bufInsSql.append(" BANK_ACCOUNT,BRANCH_MICR_CODE,BRANCH_IFCS_CODE,GL_CODE,ACCOUNT_TYPE,case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS,V.CLIENT_CODE ");
			bufInsSql.append(" FROM COM_BANK_ACCOUNTS_M V");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM COM_BANK_ACCOUNTS_M");

			
			 if (!bankCode.equals("") && !bankBranchName.equals("") && !accountNo.equals("")) {
					bufInsSql.append(" WHERE BANK_ID = '" + bankCode + "' AND BANK_BRANCH_ID='" + bankBranchName + "'AND BANK_ACCOUNT='"+accountNo+"' ");
					bufInsSqlTempCount.append(" WHERE BANK_ID = '" + bankCode + "' AND BANK_BRANCH_ID='" + bankBranchName + "'AND BANK_ACCOUNT='"+accountNo+"' ");
				}
			
			else if (!bankCode.equals("") && !bankBranchName.equals("")) {
				bufInsSql.append(" WHERE BANK_ID = '" + bankCode + "' AND BANK_BRANCH_ID='" + bankBranchName + "' ");
				bufInsSqlTempCount.append(" WHERE BANK_ID = '" + bankCode + "' AND BANK_BRANCH_ID='" + bankBranchName + "' ");
			}

			else if (!bankCode.equals("")) {
				bufInsSql.append(" WHERE BANK_ID = '" + bankCode + "' ");
				bufInsSqlTempCount.append(" WHERE BANK_ID = '" + bankCode + "' ");
			} 
			else if (!bankBranchName.equals("")) {
				bufInsSql.append(" WHERE BANK_BRANCH_ID ='" + bankBranchName + "' ");
				bufInsSqlTempCount.append(" WHERE BANK_BRANCH_ID ='" + bankBranchName + "' ");
			}
									
			logger.info("IN searchBankAccountData() search query1 ### "+ bufInsSql.toString());
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((bankCode.trim()==null && bankBranchName.trim()==null && accountNo.trim()==null) || (bankCode.trim().equalsIgnoreCase("") && bankBranchName.trim().equalsIgnoreCase("") && accountNo.trim().equalsIgnoreCase("")) || bankAccountMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+bankAccountMasterVo.getCurrentPageLink());
			if(bankAccountMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (bankAccountMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY BANK_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
								
	//		}
			logger.info("query : "+bufInsSql);
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("searchBankAcccountGridData size is...."+ searchlist.size());
			
			for (int i = 0; i < searchlist.size(); i++) {
								
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					BankAccountMasterVo bankAccountVo = new BankAccountMasterVo();

					bankAccountVo.setBankCodeModify("<a href=bankAccountMasterSearch.do?method=openEditBankAccount&bankSearchCode="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ "&bankBranchSearchName="+ CommonFunction.checkNull(data.get(2))
									.toString()+ "&accountNo="+CommonFunction.checkNull(data.get(4))
									.toString()+ ">"
									+ CommonFunction.checkNull(data.get(1))
											.toString() + "</a>");

					bankAccountVo.setLbxBankID(CommonFunction.checkNull(data.get(0)).toString());
					bankAccountVo.setBankCode(CommonFunction.checkNull(data.get(1)).toString());

					bankAccountVo.setLbxBranchID(CommonFunction.checkNull(data.get(2)).toString());
					bankAccountVo.setBankBranchName(CommonFunction.checkNull(data.get(3)).toString());

					bankAccountVo.setAccountNo(CommonFunction.checkNull(data.get(4)).toString());
					bankAccountVo.setMicrCode(CommonFunction.checkNull(data.get(5)).toString());
					bankAccountVo.setIfscCode(CommonFunction.checkNull(data.get(6)).toString());
					bankAccountVo.setGlCode(CommonFunction.checkNull(data.get(7)).toString());
					bankAccountVo.setAccountType(CommonFunction.checkNull(data.get(8)).toString());
					bankAccountVo.setBankAccountStatus(CommonFunction.checkNull(data.get(9)).toString());
					bankAccountVo.setClientCode(CommonFunction.checkNull(data.get(10)).toString());
					detailList.add(bankAccountVo);
					bankAccountVo.setTotalRecordSize(count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			BankAccountMasterVo bankAccountVo = new BankAccountMasterVo();
//			bankAccountVo.setTotalRecordSize(count);
//			detailList.add(bankAccountVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	public boolean insertBankAccountMaster(Object ob) {
		BankAccountMasterVo bankAccountMasterVo = (BankAccountMasterVo) ob;
		boolean status = false;

		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "select BANK_ID,BANK_BRANCH_ID,BANK_ACCOUNT from com_bank_accounts_m where  BANK_ID='"
				+ StringEscapeUtils.escapeSql(bankAccountMasterVo
						.getLbxBankID().trim())
				+ "' AND BANK_BRANCH_ID='"
				+ StringEscapeUtils.escapeSql(bankAccountMasterVo
						.getLbxBranchID().trim())
				+ "' AND BANK_ACCOUNT='"
				+ StringEscapeUtils.escapeSql(bankAccountMasterVo
						.getAccountNo().trim()) + "'";
		logger
				.info("In insertBankAccountMaster.....................................Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (bankAccountMasterVo.getBankAccountStatus() != null
						&& bankAccountMasterVo.getBankAccountStatus().equals(
								"on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert BANK ACCOUNT master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into COM_BANK_ACCOUNTS_M(BANK_ID,BANK_BRANCH_ID,BANK_ACCOUNT,BRANCH_IFCS_CODE,BRANCH_MICR_CODE,GL_CODE,ACCOUNT_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,CLIENT_CODE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");// BANK_ID
				bufInsSql.append(" ?,");// BANK_BRANCH_ID
				bufInsSql.append(" ?,");// BANK_ACCOUNT
				bufInsSql.append(" ?,");// BRANCH_IFCS_CODE
				bufInsSql.append(" ?,");// BRANCH_MICR_CODE
				bufInsSql.append(" ?,");// GL_CODE
				bufInsSql.append(" ?,");// ACCOUNT_TYPE
				bufInsSql.append(" ?,");// REC_STATUS
				bufInsSql.append(" ?,");// MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");// AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?)");// CLIENT_CODE
				
				if (CommonFunction
						.checkNull(bankAccountMasterVo.getLbxBankID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getLbxBankID().trim());

				if (CommonFunction.checkNull(
						bankAccountMasterVo.getLbxBranchID()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getLbxBranchID().trim());

				if (CommonFunction
						.checkNull(bankAccountMasterVo.getAccountNo())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getAccountNo().trim());

				if (CommonFunction.checkNull(bankAccountMasterVo.getIfscCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getIfscCode().trim());
				
				if (CommonFunction.checkNull(bankAccountMasterVo.getMicrCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getMicrCode().trim());

				if (CommonFunction.checkNull(bankAccountMasterVo.getGlCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getGlCode().trim());

				if (CommonFunction.checkNull(bankAccountMasterVo.getAccountType())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getAccountType().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(bankAccountMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getMakerId().trim());

				if (CommonFunction
						.checkNull(bankAccountMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getMakerDate().trim());

				if (CommonFunction.checkNull(bankAccountMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getMakerId().trim());

				if (CommonFunction
						.checkNull(bankAccountMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getMakerDate().trim());
				
				//code added by neeraj tripathi
				if (CommonFunction.checkNull(bankAccountMasterVo.getClientCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo.getClientCode().trim());
				//tripathi's space end

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger
						.info("IN insertBankAccountMaster() insert query1 ### "
								+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger
						.info("In insertBankAccountMaster......................"
								+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public boolean updateBankAccountData(Object ob) {
		BankAccountMasterVo bankAccountMasterVo = (BankAccountMasterVo) ob;

		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		ArrayList updatelist = new ArrayList();
//		String query = "select BANK_ACCOUNT from com_bank_accounts_m where  BANK_ACCOUNT='"
//				+ StringEscapeUtils.escapeSql(bankAccountMasterVo
//						.getAccountNo().trim())
//				+ "' AND BANK_BRANCH_ID <> '"
//				+ StringEscapeUtils.escapeSql(bankAccountMasterVo
//						.getLbxBranchID().trim())
//				+ "' AND BANK_ID <> '"
//				+ StringEscapeUtils.escapeSql(bankAccountMasterVo
//						.getLbxBankID().trim()) + "'";
//		logger
//				.info("In updateBankAccountData.....................................Dao Impl"
//						+ query);
//		boolean st = ConnectionDAO.checkStatus(query);
		boolean status = false;
		String stat = "";
		try {
			//if (!st) {

				if (bankAccountMasterVo.getBankAccountStatus() != null
						&& bankAccountMasterVo.getBankAccountStatus().equals(
								"on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				StringBuffer bufInsSql = new StringBuffer();
				logger.info("In updateBankAccountData.....................................Dao Impl");
				bufInsSql.append("UPDATE COM_BANK_ACCOUNTS_M SET GL_CODE=?,ACCOUNT_TYPE=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append("CLIENT_CODE=?  WHERE BANK_ID=? AND BANK_BRANCH_ID=? AND BANK_ACCOUNT=?");

				if (CommonFunction.checkNull(bankAccountMasterVo.getGlCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo.getGlCode().trim());
				
				if (CommonFunction.checkNull(bankAccountMasterVo.getAccountType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo.getAccountType());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);
				//----------------------------------
				if (CommonFunction.checkNull(bankAccountMasterVo.getMakerId()).equalsIgnoreCase(
				""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo.getMakerId());
				if (CommonFunction.checkNull(bankAccountMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo.getMakerDate());
				//----------------------------------
				//code added by neeraj tripathi
				if (CommonFunction.checkNull(bankAccountMasterVo.getClientCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo.getClientCode().trim());
				//tripathi's space end
				if (CommonFunction
						.checkNull(bankAccountMasterVo.getLbxBankID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getLbxBankID().toUpperCase());
				
				if (CommonFunction
						.checkNull(bankAccountMasterVo.getLbxBranchID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getLbxBranchID().toUpperCase());
				
				if (CommonFunction
						.checkNull(bankAccountMasterVo.getAccountNo())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(bankAccountMasterVo
							.getAccountNo().toUpperCase());
					
				// logger.info("................"+bankAccountMasterVo.getLbxBankID());
				insertPrepStmtObject.setSql(bufInsSql.toString());
				updatelist.add(insertPrepStmtObject);

				logger.info("In getListOfValues" + bufInsSql.toString());
				logger.info("In getListOfValuesQuery"
						+ insertPrepStmtObject.printQuery());

				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

			//}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	// Code End BY Ritu for BANK ACCOUNT
	


//kanika code ofr login
	
	public boolean updateloginChangePassword(ChangePasswordMasterVo changePasswordMasterVo)
	{	 
		String userId=(String)changePasswordMasterVo.getLbxUserId();
		
		ArrayList updatelist=new ArrayList();
		ArrayList updatePasslist=new ArrayList(); 
		boolean status=false;
     
		try{
				
               String query="SELECT count(1) FROM SEC_USER_M WHERE USER_ID='"+userId+"' AND (USER_PASSWORD='"+md5.en(changePasswordMasterVo.getNewPassword())+
               "' OR USER_LAST_PASSWORD_1='"+md5.en(changePasswordMasterVo.getNewPassword())+"' OR USER_LAST_PASSWORD_2='"+md5.en(changePasswordMasterVo.getNewPassword())
               +"'OR USER_LAST_PASSWORD_3='"+md5.en(changePasswordMasterVo.getNewPassword())+"' OR USER_LAST_PASSWORD_4='"+md5.en(changePasswordMasterVo.getNewPassword())+"' OR USER_LAST_PASSWORD_5='"+md5.en(changePasswordMasterVo.getNewPassword())+"')";   
				  
               logger.info("In updateloginChangePassword .Dao Impl query is... "+query);
               
               
				  String countStr = ConnectionDAOforEJB.singleReturn(query);
				  int count = Integer.parseInt(countStr);
				  logger.info("In updateloginChangePassword......count... "+count);
				  
				  if(count<1)
				  {
//					  
					  String passQuery="SELECT USER_PASSWORD,USER_LAST_PASSWORD_1,USER_LAST_PASSWORD_2,USER_LAST_PASSWORD_3,USER_LAST_PASSWORD_4,USER_LAST_PASSWORD_5,SECURITY_QUESTION1,SECURITY_ANSWER1,SECURITY_QUESTION2,SECURITY_ANSWER2 FROM SEC_USER_M WHERE USER_ID='"+userId+"'";	  
	        	      logger.info("passQuery.. "+passQuery);
	        	      
	        	      updatePasslist = ConnectionDAOforEJB.sqlSelect(passQuery);
		              
	        	      logger.info("IN updateUserPass() update Password query1 ### "+passQuery);
		              logger.info("updateUserPass.. "+updatePasslist.size());
	                  
		              for(int i=0;i<updatePasslist.size();i++){
		            	  String ans1="";
		            	  String ans2="";
		              logger.info("updateUserPassList "+updatePasslist.get(i).toString());
	                  
		              ArrayList data=(ArrayList)updatePasslist.get(i);
	                  
	                  if(data.size()>0){
	                	  String userPass=(CommonFunction.checkNull(data.get(0)).toString());
	                	  String userPass1=(CommonFunction.checkNull(data.get(1)).toString());
	                	  String userPass2=(CommonFunction.checkNull(data.get(2)).toString());
	                	  String userPass3=(CommonFunction.checkNull(data.get(3)).toString()); 
	                	  String userPass4=(CommonFunction.checkNull(data.get(4)).toString());
	                	  String userPass5=(CommonFunction.checkNull(data.get(5)).toString());
	                	
	                	  ans1 =md5.en((CommonFunction.checkNull(changePasswordMasterVo.getAns1())).trim());
	                	  ans2 = md5.en((CommonFunction.checkNull(changePasswordMasterVo.getAns2())).trim());
	                 //kanika's code LAST_PASSWORD_DATE=STR_TO_DATE('"+changePasswordMasterVo.getPasswordDate()+"','%d-%m-%Y') replace
					  logger.info("In updateUserData.....................................Dao Impl"); 													
					  String query1="UPDATE SEC_USER_M SET FORCED_PASSWORD_FLAG='N',LAST_PASSWORD_DATE=dbo.sysdate(),USER_LAST_PASSWORD_1='"+userPass+"',USER_LAST_PASSWORD_2='"+userPass1+"',USER_LAST_PASSWORD_3='"+userPass2+"',USER_LAST_PASSWORD_4='"+userPass3+"',USER_LAST_PASSWORD_5='"+userPass4+"',USER_PASSWORD='"+md5.en(changePasswordMasterVo.getNewPassword())+"', ACCOUNT_STATUS='U', " +
					  		"SECURITY_QUESTION1='"+(CommonFunction.checkNull(changePasswordMasterVo.getQues1())).trim()+"',SECURITY_ANSWER1='"+ans1+"',SECURITY_QUESTION2='"+(CommonFunction.checkNull(changePasswordMasterVo.getQues2())).trim()+"',SECURITY_ANSWER2='"+ans2+"' WHERE USER_ID='"+(CommonFunction.checkNull(userId)).trim()+"'";
					  updatelist.add(query1);
					  logger.info("In getListOfValues"+query1);
					  status =ConnectionDAOforEJB.sqlInsUpdDelete(updatelist);  
					  
	                  } 
	                }  
				  }
				  else
				  {
					  status=false; 
				  }
	                  
			}catch(Exception e){
				e.printStackTrace();
			}

			return status;
	}

	public ArrayList showquestion(ChangePasswordMasterVo changePasswordMasterVo)
	{
	 
		String userId=(String)changePasswordMasterVo.getLbxUserId();
		logger.info("------userid--------"+userId);
		ArrayList queslist=new ArrayList();
		ArrayList questionlist=new ArrayList();
     boolean status=false;
     
			try{
				
               
					  String passQuery="SELECT SECURITY_QUESTION1,SECURITY_ANSWER1,SECURITY_QUESTION2,SECURITY_ANSWER2 FROM SEC_USER_M WHERE USER_ID='"+userId+"'";	  
	        	      logger.info("passQuery"+passQuery);
	        	      queslist = ConnectionDAO.sqlSelect(passQuery);
	        	      for(int i=0;i<queslist.size();i++){
			              logger.info("updateUserPassList "+queslist.get(i).toString());
			              ArrayList data=(ArrayList)queslist.get(i);
		                  if(data.size()>0){
		                	  changePasswordMasterVo.setQues1((CommonFunction.checkNull(data.get(0)).toString()));
		                	  changePasswordMasterVo.setAns1((CommonFunction.checkNull(data.get(1)).toString()));
		                	  changePasswordMasterVo.setQues2((CommonFunction.checkNull(data.get(2)).toString()));
		                	  changePasswordMasterVo.setAns2((CommonFunction.checkNull(data.get(3)).toString())); 
		                	  questionlist.add(changePasswordMasterVo); 
		                	  
		                  }
		                  }
	        	      }catch(Exception e){
				e.printStackTrace();
			}

			return questionlist;
	}


	
	//end of kanika's code	  
	
	
	//ravi, ratio definition code
	

	public ArrayList ratioDefinitionSearch(Object ob)
	{
		String ratioCode = "";
		String ratioName = "";
		String query1="";
		Connection con=null;
		ResultSet rs2 = null;
		Statement stmt = null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		MasterVo masterVo = (MasterVo) ob;
		ArrayList<MasterVo> detailList = new ArrayList<MasterVo>();
		try {

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			ratioCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVo.getRatioCode())).trim());
			ratioName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVo.getRatioName())).trim());
			
			logger.info("In ratioDefinitionSearch().............inside ejb server file...........Dao Impl");
			
			bufInsSql.append("SELECT RATIO_CODE,RATIO_NAME,RATIO_FORMULA,REC_STATUS,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status");
			bufInsSql.append(" FROM cr_ratios_m ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_ratios_m ");
			
			if ((!(ratioCode.equalsIgnoreCase(""))) && (!(ratioName.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE RATIO_NAME like '%" + ratioName+ "%' AND RATIO_CODE='" + ratioCode + "'");
				bufInsSqlTempCount.append("WHERE RATIO_NAME like '%" + ratioName+ "%' AND RATIO_CODE='" + ratioCode + "'");
			}

			else if (!ratioName.equals("")) {
				bufInsSql.append(" WHERE RATIO_NAME LIKE '%" + ratioName + "%' ");
				bufInsSqlTempCount.append(" WHERE RATIO_NAME LIKE '%" + ratioName + "%' ");
			}

			else if (!ratioCode.equals("")) {
				bufInsSql.append(" WHERE RATIO_CODE = '" + ratioCode + "' ");
				bufInsSqlTempCount.append(" WHERE RATIO_CODE = '" + ratioCode + "' ");
			}
							
			logger.info("search Query...." + bufInsSql);
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((ratioCode.trim()==null && ratioName.trim()==null) || (ratioCode.trim().equalsIgnoreCase("") && ratioName.trim().equalsIgnoreCase("")) || masterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+masterVo.getCurrentPageLink());
			if(masterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (masterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY RATIO_CODE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
					
			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
//			}
			logger.info("query : "+bufInsSql);
						
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("ratioDefinitionSearch " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					MasterVo mVo = new MasterVo();

//					mVo.setAgencyCodeModify("<a href=agencyMasterSearch.do?method=openEditAgency&agencySearchCode="+ CommonFunction.checkNull(data.get(0)).toString()
//									+ ">"
//									+ CommonFunction.checkNull(data.get(0))
//											.toString() + "</a>");

					mVo.setRatioCodeModify("<a href=addRatioDefinition.do?method=openEditRatioDefinition&ratioCode="+ CommonFunction.checkNull(data.get(0)).toString()+ ">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					
					// mVo.setAgencyCode("<a href="#" onclick="modifyDetails(CommonFunction.checkNull(data.get(0)).toString());" ></a>");
					mVo.setRatioCode(CommonFunction.checkNull(data.get(0)).toString());
					mVo.setRatioName(CommonFunction.checkNull(data.get(1)).toString());
					mVo.setExpression(CommonFunction.checkNull(data.get(2)).toString());
					mVo.setRatioStatus(CommonFunction.checkNull(data.get(3)).toString());
					mVo.setRatioStatusDesc(CommonFunction.checkNull(data.get(4)).toString());
					detailList.add(mVo);
					mVo.setTotalRecordSize(count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			MasterVo mVo = new MasterVo();
//			mVo.setTotalRecordSize(count);
//			detailList.add(mVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}
	
	//ravi , ratio definition start
	
	public boolean saveRatioDefinition(Object ob) {
		MasterVo vo = (MasterVo) ob;
		boolean status = false;

		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "select RATIO_CODE,RATIO_NAME from cr_ratios_m where RATIO_CODE='"
				+ StringEscapeUtils.escapeSql(vo.getRatioCode().trim())+"'";
		
		logger
				.info("In saveRatioDefinition...........inside ejb server file...........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (vo.getRatioStatus() != null
						&& vo.getRatioStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In saveRatioDefinition master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("insert into cr_ratios_m(RATIO_CODE,RATIO_NAME,RATIO_FORMULA,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
//				bufInsSql.append(" ? )");
				if (CommonFunction.checkNull(vo.getRatioCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRatioCode()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getRatioName())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRatioName()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getExpression())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getExpression()
							.toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveRatioDefinition() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveRatioDefinition..........inside ejb server file...........Dao Impl"
						+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}
	
	public boolean updateRatioDefinition(Object ob) {
		MasterVo vo = (MasterVo) ob;
		String agencyCode = (String) vo.getAgencyCode();
		logger.info("updateRatioDefinition:-" + vo.getRatioStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();

		boolean status = false;
		String stat = "";
		try {

			if (vo.getRatioStatus() != null && vo.getRatioStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateRatioDefinition.............inside ejb server file...........Dao Impl");
			bufInsSql.append("UPDATE cr_ratios_m SET RATIO_NAME=?,RATIO_FORMULA=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where RATIO_CODE=?");

			if (CommonFunction.checkNull(vo.getRatioName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRatioName());
			
			if (CommonFunction.checkNull(vo.getExpression()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getExpression());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			
			//----------------------------------
			
			if (CommonFunction.checkNull(vo.getRatioCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRatioCode());
			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info(insertPrepStmtObject.printQuery());
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
		//}
//		else {
//			status = false;
//		}
		
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

		//ravi, ratio definition end
	
	public ArrayList searchFinancialPramData(Object ob) {
		String pramCode = "";
		String pramName = "";
		String pramType = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		FinancialPramMasterVo fiancialPramVo = (FinancialPramMasterVo) ob;
		ArrayList<FinancialPramMasterVo> detailList = new ArrayList<FinancialPramMasterVo>();
		try {

			logger.info("In searchFinancialPramData...........inside ejb server file...........Dao Impl");
			
			pramCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(fiancialPramVo.getPramSearchCode())).trim());
			pramName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(fiancialPramVo.getPramSearchName())).trim());
			pramType=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(fiancialPramVo.getPramTypeSearch())).trim());
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select PARAM_CODE,PARAM_NAME,Case When SOURCE_TYPE='B' then 'BALANCE SHEET' When SOURCE_TYPE='P' then 'PROFIT AND LOSS' When SOURCE_TYPE='O' then 'OTHERS' When SOURCE_TYPE='I' then 'INDIVIDUAL ANALYSIS' End As Source_Type,SEQUENCE_NO,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,Case when SUB_TYPE ='A' then 'ASSET' When SUB_TYPE ='L' then 'LIABILITY' End as subType");
			bufInsSql.append(" FROM cr_financial_param where 'a'='a'");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_financial_param where 'a'='a' ");
			
			/*if ((!(pramCode.equalsIgnoreCase(""))) && (!(pramName.equalsIgnoreCase("")))&&(!(pramType.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE PARAM_NAME like '%" + pramName + "%' AND PARAM_CODE='" + pramCode + "' AND SOURCE_TYPE= '" + pramType + "'");
				bufInsSqlTempCount.append(" WHERE PARAM_NAME like '%" + pramName + "%' AND PARAM_CODE='" + pramCode + "' AND SOURCE_TYPE= '" + pramType + "' ");
			} */
			 if (!pramName.equals("")) {
				bufInsSql.append(" AND  PARAM_NAME LIKE '%" + pramName + "%' ");
				bufInsSqlTempCount.append(" AND PARAM_NAME LIKE '%" + pramName + "%' ");
			}

			 if (!pramCode.equals("")) {
				bufInsSql.append(" AND PARAM_CODE = '" + pramCode + "' ");
				bufInsSqlTempCount.append(" AND PARAM_CODE = '" + pramCode + "' ");
			} 
			 if(!pramType.equals("")){
				bufInsSql.append(" AND SOURCE_TYPE = '" + pramType + "' ");
				bufInsSqlTempCount.append(" AND SOURCE_TYPE = '" + pramType + "' ");
			}
									
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
           
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//            if((pramCode.trim()==null && pramName.trim()==null) || (pramCode.trim().equalsIgnoreCase("") && pramName.trim().equalsIgnoreCase("")) || fiancialPramVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+fiancialPramVo.getCurrentPageLink());
			if(fiancialPramVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (fiancialPramVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY PARAM_CODE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
		
//			}
			logger.info("query : "+bufInsSql);
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("searchFinancial Data " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					FinancialPramMasterVo finVo = new FinancialPramMasterVo();

					finVo.setPramCodeModify("<a href=finPramMasterSearch.do?method=openEditFinPram&pramSearchCode="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					finVo.setPramCode(CommonFunction.checkNull(data.get(0)).toString());
					finVo.setPramName(CommonFunction.checkNull(data.get(1)).toString());
					finVo.setPramType(CommonFunction.checkNull(data.get(2)).toString());
					finVo.setSequenceNo(CommonFunction.checkNull(data.get(3)).toString());
					finVo.setPramStatus(CommonFunction.checkNull(data.get(4)).toString());
					finVo.setSubType(CommonFunction.checkNull(data.get(5)).toString());
					finVo.setTotalRecordSize(count);
					detailList.add(finVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			FinancialPramMasterVo finMvo = new FinancialPramMasterVo();
//			finMvo.setTotalRecordSize(count);
//			detailList.add(finMvo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}
	
	
	public ArrayList searchFinPramData(Object ob) {
		String pramCode = "";
		String pramName = "";

		ArrayList searchlist = new ArrayList();
		FinancialPramMasterVo fiancialPramVo = (FinancialPramMasterVo) ob;
		ArrayList<FinancialPramMasterVo> detailList = new ArrayList<FinancialPramMasterVo>();
		try {

			logger.info("In searchFinancialData().........inside ejb server file...........Dao Impl");
			
			pramCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(fiancialPramVo.getPramSearchCode())).trim());
			//pramName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(fiancialPramVo.getPramSearchName())).trim());
			
			logger.info("pramCodeis.........@@@*****"+pramCode);
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			


			bufInsSql.append("select PARAM_CODE,PARAM_NAME,SOURCE_TYPE,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,");
			bufInsSql.append("case when NEGATIVE_ALLOWED='A' then 'Active' else 'Inactive' end as NEGATIVE_ALLOWED,AUTO_CALCULATED,SYSTEM_DEFINED,SEQUENCE_NO,SUB_TYPE,FINANCIAL_FORMULA" );
			bufInsSql.append(" FROM cr_financial_param WHERE PARAM_CODE = '" + pramCode + "'");

			

			bufInsSql.append(" ORDER by PARAM_CODE");
			
			
           
			logger.info("query : "+bufInsSql);
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("searchFinancial Data " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				//logger.info("searchFinancialDataList "+ searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					FinancialPramMasterVo finVo = new FinancialPramMasterVo();
					finVo.setPramCode(CommonFunction.checkNull(data.get(0)).toString());
					finVo.setPramName(CommonFunction.checkNull(data.get(1)).toString());
					finVo.setPramType(CommonFunction.checkNull(data.get(2)).toString());
					finVo.setPramStatus(CommonFunction.checkNull(data.get(3)).toString());
					finVo.setNegativeAllowed(CommonFunction.checkNull(data.get(4)).toString());
					finVo.setAutoCalculated(CommonFunction.checkNull(data.get(5)).toString());
					finVo.setSystemDefined(CommonFunction.checkNull(data.get(6)).toString());
					finVo.setSequenceNo(CommonFunction.checkNull(data.get(7)).toString());
                    finVo.setSubType(CommonFunction.checkNull(data.get(8)).toString());	
                    finVo.setFinExpression(CommonFunction.checkNull(data.get(9)).toString());                    
                    //finVo.setTotalRecordSize(count);

					detailList.add(finVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	
	
		
	public String insertFinancialMaster(Object ob) {
		FinancialPramMasterVo vo = (FinancialPramMasterVo) ob;
		boolean status = false;
		logger
				.info("In insertFinancialMaster...........inside ejb server file...........Dao Impl"
						+ vo.getPramStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String negativeAllowed = "X";
		String autoCalculated = "X";
		String result = "";
		String query = "select PARAM_CODE,PARAM_NAME from cr_financial_param where PARAM_CODE LIKE '%"
				+ StringEscapeUtils.escapeSql(vo.getPramCode().trim())+"%'";
				
		logger.info("In insertFinancialMaster...........inside ejb server file...........Dao Impl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		
		String querySeq="select PARAM_CODE,PARAM_NAME from cr_financial_param where SOURCE_TYPE='"
			+ StringEscapeUtils.escapeSql(vo.getPramType().trim())+"' and SEQUENCE_NO='"+StringEscapeUtils.escapeSql(vo.getSequenceNo().trim())+"'";
		logger.info("In insertFinancialMaster...........inside ejb server file...........Dao Impl For Sequence:-"+ querySeq);
		boolean stSeq = ConnectionDAOforEJB.checkStatus(querySeq);
		try {

			if (!st && !stSeq) {
				if (vo.getPramStatus() != null
						&& vo.getPramStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("getNegativeAllowed():-" + vo.getNegativeAllowed());
				if (vo.getNegativeAllowed() != null
						&& vo.getNegativeAllowed().equals("on")) {
					negativeAllowed = "A";
				} else {
					negativeAllowed = "X";

				}
				logger.info("getAutoCalculated():-" + vo.getAutoCalculated());
				if (vo.getAutoCalculated() != null
						&& vo.getAutoCalculated().equals("on")) {
					autoCalculated = "Y";
				} else {
					autoCalculated = "N";

				}
				logger.info("In insertFinancialMaster");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into cr_financial_param(PARAM_CODE,PARAM_NAME,SOURCE_TYPE,NEGATIVE_ALLOWED,AUTO_CALCULATED,SYSTEM_DEFINED,SEQUENCE_NO,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,SUB_TYPE,FINANCIAL_FORMULA)");

				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//PARAM_CODE
				bufInsSql.append(" ?,");//PARAM_NAME
				bufInsSql.append(" ?,");//SOURCE_TYPE
				bufInsSql.append(" ?,");//NEGATIVE_ALLOWED
				bufInsSql.append(" ?,");//AUTO_CALCULATED
				bufInsSql.append(" 'N',");//SYSTEM_DEFINED
				bufInsSql.append(" ?,");//SEQUENCE_NO
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//SUB_TYPE
				bufInsSql.append(" ? )");// FINANCIAL_FORMULA
				
				if (CommonFunction.checkNull(vo.getPramCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPramCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getPramName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPramName().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getPramType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPramType().toUpperCase().trim());
				
				if (CommonFunction.checkNull(negativeAllowed).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(negativeAllowed);
				
				if (CommonFunction.checkNull(autoCalculated).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(autoCalculated);
				
				if (CommonFunction.checkNull(vo.getSequenceNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSequenceNo());
				
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				if (CommonFunction.checkNull(vo.getSubType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSubType());
				if (CommonFunction.checkNull(vo.getFinExpression()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getFinExpression());


				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN insertFinancialMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveFinancailPrameterData......................"+ status);
				if(status){
					result="saved";
				}else{
					result="notSaved";
				}
			}
           if(st){
        	   result="already"; 
           }
           if(stSeq){
        	   result="alreadySeq"; 
           }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public String updateFinPramData(Object ob) {
		FinancialPramMasterVo vo = (FinancialPramMasterVo) ob;
		String pramCode = (String) vo.getPramCode();
		logger.info("vo.getPramStatus():-" + vo.getPramStatus());
		ArrayList updatelist = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		boolean status = false;
		String negativeAllowed = "X";
		String stat = "X";
		String autoCalculated = "X";
		String result="";
		
		String querySeq="select PARAM_CODE,PARAM_NAME from cr_financial_param where SOURCE_TYPE='"
			+ StringEscapeUtils.escapeSql(vo.getPramType().trim())+"' and SEQUENCE_NO='"+StringEscapeUtils.escapeSql(vo.getSequenceNo().trim())+"' AND PARAM_CODE <> '"+StringEscapeUtils.escapeSql(vo.getPramCode().trim())+"'";
		logger.info("In updateFinPramData...........inside ejb server file...........Dao Impl For Sequence:-"+ querySeq);
		boolean stSeq = ConnectionDAOforEJB.checkStatus(querySeq);
		
		try {
			if(!stSeq){
			if (vo.getPramStatus() != null && vo.getPramStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			logger.info("getNegativeAllowed():-" + vo.getNegativeAllowed());
			if (vo.getNegativeAllowed() != null
					&& vo.getNegativeAllowed().equals("on")) {
				negativeAllowed = "A";
			} else {
				negativeAllowed = "X";

			}
			logger.info("getAutoCalculated():-" + vo.getAutoCalculated());
			if (vo.getAutoCalculated() != null
					&& vo.getAutoCalculated().equals("on")) {
				autoCalculated = "Y";
			} else {
				autoCalculated = "N";

			}
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateFinPramData..........inside ejb server file...........Dao Impl");
			bufInsSql.append("UPDATE cr_financial_param set PARAM_NAME=?,SOURCE_TYPE=?,NEGATIVE_ALLOWED=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("AUTO_CALCULATED=?,SEQUENCE_NO=?,SUB_TYPE=?,FINANCIAL_FORMULA=? where PARAM_CODE=?");


			if (CommonFunction.checkNull(vo.getPramName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPramName().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getPramType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPramType().toUpperCase().trim());
			
			if (CommonFunction.checkNull(negativeAllowed).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(negativeAllowed);
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
			""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			
			//----------------------------------

			if (CommonFunction.checkNull(autoCalculated).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(autoCalculated);
			
			if (CommonFunction.checkNull(vo.getSequenceNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getSequenceNo());
			
              
			if (CommonFunction.checkNull(vo.getSubType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getSubType());
			if (CommonFunction.checkNull(vo.getFinExpression()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getFinExpression());
			
			if (CommonFunction.checkNull(vo.getPramCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPramCode().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info("In updateFinPramData UPDATE QRY:---" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			bufInsSql = null;
			insertPrepStmtObject = null;
			if(status){
				result="saved";
			}else{
				result="notSaved";
			}
		 }
		 if(stSeq){
        	   result="alreadySeq"; 
           }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}	

	
public ArrayList searchApprovalLevelDef(ApprovalLevelDefVo Vo,String mcFlag) {

	String productModify="";
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	String decideUpdate="";
	String tableName="";
	String flag="";
	ArrayList searchlist = new ArrayList();
	ArrayList<ApprovalLevelDefVo> detailList = new ArrayList<ApprovalLevelDefVo>();
	try {
		logger.info("In searchApprovalLevelDef.....................................Dao Impl"+mcFlag);
		logger.info("In searchApprovalLevelDef...................hhhhhhhh"+CommonFunction.checkNull(Vo.getSearchStatus()));
		
		if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y") && !((CommonFunction.checkNull(Vo.getSearchStatus())).equalsIgnoreCase("A"))){
			tableName="CR_APPROVAL_LEVEL_M_temp";
			flag="Y";
			decideUpdate="N";
		}
		else{
			tableName="cr_approval_level_m";
			flag="N";
			decideUpdate="Y";
		}
		logger.info("value flag::::::::::::"+flag);
		
		String productSearch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxProductID())).trim());
		logger.info("productSearch...." + productSearch);
		
		String findApprovalSearch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getFindApprovalSearch())).trim());
		logger.info("findApprovalSearch...." + findApprovalSearch);
		
		productModify = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getProductModify())).trim());

		StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		
		
		
		
//			bufInsSql.append("SELECT PRODUCT_ID,FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO,if(REC_STATUS='A','Active','Inactive')as Status  from cr_approval_level_m ");
		bufInsSql.append("SELECT APPROVAL_LEVEL_ID,(SELECT cr_p.PRODUCT_DESC FROM cr_product_m cr_p WHERE cr_p.PRODUCT_ID=cra.PRODUCT_ID) as Product_Id,(SELECT SCHEME_DESC FROM CR_SCHEME_M S WHERE S.SCHEME_ID=cra.SCHEME_ID) as Scheme , ");
		bufInsSql.append("FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status");
		bufInsSql.append(" FROM "+tableName+" cra where 'a'='a' ");
		
		
		bufInsSqlTempCount.append("SELECT count(1) ");
		//bufInsSqlTempCount.append("SELECT count(1) APPROVAL_LEVEL_ID,(SELECT cr_p.PRODUCT_DESC FROM cr_product_m cr_p WHERE cr_p.PRODUCT_ID=cra.PRODUCT_ID) as Product_Id,(SELECT SCHEME_DESC FROM CR_SCHEME_M S WHERE S.SCHEME_ID=cra.SCHEME_ID) as Scheme , ");
		//bufInsSqlTempCount.append("FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO,case when REC_STATUS='A' then 'Active' else 'Inactive' end as ");
		bufInsSqlTempCount.append(" FROM "+tableName+" cra where 'a'='a'  ");
		
		if(flag.equalsIgnoreCase("Y")){
			logger.info("saurabh singh:::::::::::::::::::with flag :"+mcFlag);
			bufInsSql.append(" AND MAKER_AUTHOR_STATUS <> 'F' ");
			bufInsSqlTempCount.append("  AND MAKER_AUTHOR_STATUS <> 'F' ");
		}
		
		if(!(CommonFunction.checkNull(Vo.getLbxProductID()).equalsIgnoreCase("")))
		{
			bufInsSql.append("and cra.PRODUCT_ID='"+(CommonFunction.checkNull(Vo.getLbxProductID())).trim()+"' ");
			bufInsSqlTempCount.append(" and cra.PRODUCT_ID='"+(CommonFunction.checkNull(Vo.getLbxProductID())).trim()+"' "); 
		}
		if(!(CommonFunction.checkNull(Vo.getLbxSchemeId()).equalsIgnoreCase("")))
		{
			bufInsSql.append(" and cra.SCHEME_ID='"+(CommonFunction.checkNull(Vo.getLbxSchemeId())).trim()+"'");
			bufInsSqlTempCount.append(" and cra.SCHEME_ID='"+(CommonFunction.checkNull(Vo.getLbxSchemeId())).trim()+"'"); 
		}
		
		if(!(CommonFunction.checkNull(Vo.getFindApprovalSearch()).equalsIgnoreCase("")))
		{
			bufInsSql.append(" and cra.FINAL_APPROVAL_LEVEL='"+(CommonFunction.checkNull(Vo.getFindApprovalSearch())).trim()+"'");
			bufInsSqlTempCount.append(" and cra.FINAL_APPROVAL_LEVEL='"+(CommonFunction.checkNull(Vo.getFindApprovalSearch())).trim()+"'"); 
		}


		//bufInsSqlTempCount.append(" group by APPROVAL_LEVEL_ID,Product_Id,FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO,cra.SCHEME_ID,REC_STATUS");	
		logger.info("search Query...." + bufInsSql.toString());
		logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
        count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
		
//		if((productSearch.trim()==null && findApprovalSearch.trim()==null) || (productSearch.trim().equalsIgnoreCase("") && findApprovalSearch.trim().equalsIgnoreCase("")) || Vo.getCurrentPageLink()>1)
//		{
		
			logger.info("current PAge Link no .................... "+Vo.getCurrentPageLink());
		if(Vo.getCurrentPageLink()>1)
		{
			startRecordIndex = (Vo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		bufInsSql.append(" ORDER BY APPROVAL_LEVEL_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
		//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
		
//		}
		logger.info("query ::::::::::::::::: "+bufInsSql);
		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		
		logger.info("searchApprovalLevelDef " + searchlist.size());
		
		for (int i = 0; i < searchlist.size(); i++) {

			ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				ApprovalLevelDefVo LevelDefVo = new ApprovalLevelDefVo();
				            
				LevelDefVo.setApprovalLevelID("<a href=approvalLevelDefSearchBehind.do?method=EditapprovalLevelDef&productModify="
								+ CommonFunction.checkNull(data.get(0)).toString() + "&makerFlag="+flag+"&decideUpdate="+decideUpdate+">"
								+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

				LevelDefVo.setProductSearch(CommonFunction.checkNull(data.get(1)));
				LevelDefVo.setLbxProductID(CommonFunction.checkNull(data.get(1)));
				LevelDefVo.setScheme(CommonFunction.checkNull(data.get(2)));
				LevelDefVo.setFindApprovalSearch(CommonFunction.checkNull(data.get(3)));
				LevelDefVo.setFindApprovalLevel(CommonFunction.checkNull(data.get(3)));
				if(!(CommonFunction.checkNull(data.get(4))).trim().equalsIgnoreCase(""))	
				{
					Number amount =myFormatter.parse((CommonFunction.checkNull(data.get(4))).trim());
					LevelDefVo.setAmountFromSearch(myFormatter.format(amount));
					LevelDefVo.setAmountFrom(myFormatter.format(amount));
				}
				if(!(CommonFunction.checkNull(data.get(5))).trim().equalsIgnoreCase(""))	
				{
					Number amount =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
					LevelDefVo.setAmountToSearch(myFormatter.format(amount));
					LevelDefVo.setAmountTo(myFormatter.format(amount));
				}
				LevelDefVo.setStatus(CommonFunction.checkNull(data.get(6)));
		
				LevelDefVo.setTotalRecordSize(count);
				detailList.add(LevelDefVo);
			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;

}

public ArrayList<MasterVo> ruleMasterSearch(Object ob) {
	String ruleCode = "";
	String ruleName = "";
	String query1="";
	Connection con=null;
	ResultSet rs2 = null;
	Statement stmt = null;
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;

	ArrayList searchlist = new ArrayList();
	MasterVo masterVo = (MasterVo) ob;
	ArrayList<MasterVo> detailList = new ArrayList<MasterVo>();
	try {

		StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		
		ruleCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVo.getRuleCode())).trim());
		ruleName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVo.getRuleName())).trim());
		
		logger.info("In ruleMasterSearch()............Inside ejb server file..........Dao Impl");
		bufInsSql.append("SELECT RULE_CODE,RULE_DESCRIPTION,RULE_EXPRESSION,RULE_TYPE,REC_STATUS,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,STAGE,SUB_RULE_TYPE");
		bufInsSql.append(" FROM cr_rule_m ");
		bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_rule_m ");
		
		if ((!(ruleCode.equalsIgnoreCase(""))) && (!(ruleName.equalsIgnoreCase("")))) {
			bufInsSql.append("WHERE RULE_DESCRIPTION like '%" + ruleName+ "%' AND RULE_CODE='" + ruleCode + "'");
			bufInsSqlTempCount.append("WHERE RULE_DESCRIPTION like '%" + ruleName+ "%' AND RULE_CODE='" + ruleCode + "'");
		}

		else if (!ruleName.equals("")) {
			bufInsSql.append(" WHERE RULE_DESCRIPTION LIKE '%" + ruleName + "%' ");
			bufInsSqlTempCount.append(" WHERE RULE_DESCRIPTION LIKE '%" + ruleName + "%' ");
		}

		else if (!ruleCode.equals("")) {
			bufInsSql.append(" WHERE RULE_CODE = '" + ruleCode + "' ");
			bufInsSqlTempCount.append(" WHERE RULE_CODE = '" + ruleCode + "' ");
		}
						
		logger.info("search Query...." + bufInsSql);
		
		logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
        count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
		
//		if((ruleCode.trim()==null && ruleName.trim()==null) || (ruleCode.trim().equalsIgnoreCase("") && ruleName.trim().equalsIgnoreCase("")) || masterVo.getCurrentPageLink()>1)
//		{
		
		logger.info("current PAge Link no .................... "+masterVo.getCurrentPageLink());
		if(masterVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (masterVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		bufInsSql.append(" ORDER BY RULE_CODE OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
		//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				
		//query = query +" limit "+startRecordIndex+","+endRecordIndex;
//		}
		logger.info("query : "+bufInsSql);
					
		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		
		logger.info("searchlist " + searchlist.size());
		for (int i = 0; i < searchlist.size(); i++) {
			
			ArrayList data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				MasterVo mVo = new MasterVo();

				mVo.setRuleCodeModify("<a href=addRule.do?method=openEditRuleMaster&ruleCode="+ CommonFunction.checkNull(data.get(0)).toString()+ ">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
				// mVo.setAgencyCode("<a href="#" onclick="modifyDetails(CommonFunction.checkNull(data.get(0)).toString());" ></a>");
				mVo.setRuleCode(CommonFunction.checkNull(data.get(0)).toString());
				mVo.setRuleName(CommonFunction.checkNull(data.get(1)).toString());
				mVo.setExpression(CommonFunction.checkNull(data.get(2)).toString());
				mVo.setRuleType(CommonFunction.checkNull(data.get(3)).toString());
				mVo.setRuleStatus(CommonFunction.checkNull(data.get(4)).toString());
				mVo.setRuleStatusDesc(CommonFunction.checkNull(data.get(5)).toString());
				mVo.setStageForRule(CommonFunction.checkNull(data.get(6)).toString());
				mVo.setSubRuleType(CommonFunction.checkNull(data.get(7)).toString());
				detailList.add(mVo);
				mVo.setTotalRecordSize(count);
			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}
//	if(searchlist.size()==0)
//	{
//		MasterVo mVo = new MasterVo();
//		mVo.setTotalRecordSize(count);
//		detailList.add(mVo);
//		request.setAttribute("flag","yes");
//		logger.info("Detail List when searchList is null: "+detailList.size());
//	}
	return detailList;

}
public boolean saveApprovalLevelDef(ApprovalLevelDefVo Vo,String mcFlag) {
    logger.info("In MasterDAOImpl........................saveApprovalLevelDef..........."+mcFlag);
	boolean status = false;
	String flag="";
	String tableName="";
	String columnAdd="";
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String stat = "X";
	if (Vo.getStatus() != null && Vo.getStatus().equals("on")) {
		stat = "A";
	} else {
		stat = "X";


	}
	if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y")){
		tableName="CR_APPROVAL_LEVEL_M_temp";
		columnAdd=",MAKER_AUTHOR_STATUS";
	}		
	else{
		tableName="cr_approval_level_m";
		columnAdd="";
	}
		

	try {

			StringBuffer bufInsSql = new StringBuffer();
		
			bufInsSql.append("INSERT INTO "+tableName+"(PRODUCT_ID,SCHEME_ID,FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO," +
					"MIN_APPROVAL_LEVEL1,MIN_APPROVAL_LEVEL2,MIN_APPROVAL_LEVEL3,MUST_APPROVE_USER11,MUST_APPROVE_USER12," +
					"MUST_APPROVE_USER13,MUST_APPROVE_USER21,MUST_APPROVE_USER22," +
					"MUST_APPROVE_USER23,MUST_APPROVE_USER31,MUST_APPROVE_USER32,MUST_APPROVE_USER33," +
					"REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE," +
					"MIN_APPROVAL_LEVEL4,MIN_APPROVAL_LEVEL5,MIN_APPROVAL_LEVEL6,MIN_APPROVAL_LEVEL7,MIN_APPROVAL_LEVEL8,MIN_APPROVAL_LEVEL9," +
					"MUST_APPROVE_USER41,MUST_APPROVE_USER42," +
					"MUST_APPROVE_USER43,MUST_APPROVE_USER51,MUST_APPROVE_USER52," +
					"MUST_APPROVE_USER53,MUST_APPROVE_USER61,MUST_APPROVE_USER62,MUST_APPROVE_USER63," +
					"MUST_APPROVE_USER71,MUST_APPROVE_USER72," +
					"MUST_APPROVE_USER73,MUST_APPROVE_USER81,MUST_APPROVE_USER82," +
					"MUST_APPROVE_USER83,MUST_APPROVE_USER91,MUST_APPROVE_USER92,MUST_APPROVE_USER93"+columnAdd+")");				
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");//PRODUCT_ID 
			bufInsSql.append(" ?,");//SCHEME_ID 
			bufInsSql.append(" ?,");//FINAL_APPROVAL_LEVEL 
			bufInsSql.append(" ?,");//AMOUNT_FROM 
			bufInsSql.append(" ?,");//AMOUNT_TO 
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL1
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL2
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL3 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER11 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER12 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER13 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER21
			bufInsSql.append(" ?,");//MUST_APPROVE_USER22 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER23 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER31 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER32 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER33 
			bufInsSql.append(" ?,");//REC_STATUS
			bufInsSql.append(" ?,");//MAKER_ID 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" ?,");//AUTHOR_ID 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL4
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL5
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL6
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL7
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL8
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL9
			bufInsSql.append(" ?,");//MUST_APPROVE_USER41 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER42 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER43 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER51
			bufInsSql.append(" ?,");//MUST_APPROVE_USER52 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER53 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER61 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER62 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER63
			bufInsSql.append(" ?,");//MUST_APPROVE_USER71 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER72 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER73 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER81
			bufInsSql.append(" ?,");//MUST_APPROVE_USER82 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER83 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER91 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER92 
			
			if(!(columnAdd.equalsIgnoreCase(""))){
				bufInsSql.append(" ?,");//MUST_APPROVE_USER93
				bufInsSql.append(" ?)");//MAKER_CHECKER_FLAG
			}
			else{
				bufInsSql.append(" ?)");//MUST_APPROVE_USER93
			}
									
	
			if (CommonFunction.checkNull(Vo.getLbxProductID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();

			else{
				insertPrepStmtObject.addString(Vo.getLbxProductID().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(Vo.getLbxSchemeId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();

			else{
				insertPrepStmtObject.addString(Vo.getLbxSchemeId().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(Vo.getFindApprovalLevel()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getFindApprovalLevel().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(Vo.getAmountFrom()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(Vo.getAmountFrom().toUpperCase().trim())).toString()); 
			}
			if (CommonFunction.checkNull(Vo.getAmountTo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(Vo.getAmountTo().toUpperCase().trim())).toString()); 
			}
			if (CommonFunction.checkNull(Vo.getLevel1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel1().trim());
			}
			if (CommonFunction.checkNull(Vo.getLevel2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel2().trim().toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLevel3()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
		   insertPrepStmtObject.addString((Vo.getLevel3().trim()).toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId11()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId11()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId12()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId12()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId13()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId13()).trim()));
			}
				
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId21()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId21()).trim()));
			}
		
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId22()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId22()).trim())); 
			}
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId23()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId23()).trim()));
		}
		
		
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId31()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId31()).trim()));
			}
		
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId32()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId32()).trim())); 
			}
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId33()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId33()).trim()));
		}
			
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(stat); 
			}
			
			if (CommonFunction.checkNull(Vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerId()); 
			}
			if (CommonFunction.checkNull(Vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerDate()); 
			}
			if (CommonFunction.checkNull(Vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerId()); 
			}
			if (CommonFunction.checkNull(Vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerDate()); 			   
			}
			
			//Changes made by sanjog
			if (CommonFunction.checkNull(Vo.getLevel4()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel4().trim());
			}
			if (CommonFunction.checkNull(Vo.getLevel5()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel5().trim().toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLevel6()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
		   insertPrepStmtObject.addString((Vo.getLevel6().trim()).toString()); 
			}
			if (CommonFunction.checkNull(Vo.getLevel7()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel7().trim());
			}
			if (CommonFunction.checkNull(Vo.getLevel8()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel8().trim().toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLevel9()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
		   insertPrepStmtObject.addString((Vo.getLevel9().trim()).toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId41()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId41()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId42()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId42()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId43()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId43()).trim()));
			}
				
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId51()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId51()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId52()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId52()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId53()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId53()).trim()));
			}
			
			
				if (CommonFunction.checkNull(Vo.getLbxUserSearchId61()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId61()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId62()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId62()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId63()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId63()).trim()));
			}
			
			
					if (CommonFunction.checkNull(Vo.getLbxUserSearchId71()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId71()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId72()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId72()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId73()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId73()).trim()));
			}
				
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId81()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId81()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId82()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId82()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId83()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId83()).trim()));
			}
			
			
				if (CommonFunction.checkNull(Vo.getLbxUserSearchId91()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId91()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId92()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId92()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId93()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId93()).trim()));
			}
	// saurabh changes 
			if(!(columnAdd.equalsIgnoreCase("")))
			   insertPrepStmtObject.addString("P");
			

	// Changes done by sanjog

				    insertPrepStmtObject.setSql(bufInsSql.toString()); 
			    
				    logger.info("IN saveApprovalLevelDef() insert query1:::::"	+ insertPrepStmtObject.printQuery());
			
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			if(status){
				flag="S";
			}
			else{
				flag="N";
			}
		
			logger.info("In saveApprovalLevelDef......................"+ status);
		} 
	catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}


public ArrayList<ApprovalLevelDefVo> editApprovalLevelDef(String productModify,String mcFlag){
	
	int count=0;
	String tableName="";

	ArrayList searchlist = new ArrayList();
	ArrayList<ApprovalLevelDefVo> detailList = new ArrayList<ApprovalLevelDefVo>();
	try {
		logger.info("In editApprovalLevelDef.....................................Dao Impl"+mcFlag);

		StringBuffer bufInsSql = new StringBuffer();
		//StringBuffer bufInsSqlTempCount = new StringBuffer();
		if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y"))
			tableName="cr_approval_level_m_temp";
		else
			tableName="cr_approval_level_m";
		
		logger.info("value of table is ::::::::::::::::"+tableName);
		logger.info("value of productModify is ::::::::::::::::"+productModify);
		
		
		
		
		bufInsSql.append("SELECT PRODUCT_ID,FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO,MIN_APPROVAL_LEVEL1,MIN_APPROVAL_LEVEL2,MIN_APPROVAL_LEVEL3,");
		bufInsSql.append("MUST_APPROVE_USER11,MUST_APPROVE_COND11,MUST_APPROVE_USER12,MUST_APPROVE_COND12,MUST_APPROVE_USER13,MUST_APPROVE_USER21,MUST_APPROVE_COND21,");
		bufInsSql.append("MUST_APPROVE_USER22,MUST_APPROVE_COND22,MUST_APPROVE_USER23,MUST_APPROVE_USER31,MUST_APPROVE_COND31,MUST_APPROVE_USER32,MUST_APPROVE_COND32,");
		bufInsSql.append("MUST_APPROVE_USER33,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER11) As user1,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER12) As user2,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER13) As user3,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER21) As user21,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER22) As user22,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER23) As user23,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER31) As user31,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER32) As user32,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER33) As user33, ");
		bufInsSql.append("(select P.PRODUCT_DESC from  cr_product_m p where P.PRODUCT_ID = a.PRODUCT_ID) As Product ");
		bufInsSql.append(" ,SCHEME_ID,(SELECT SCHEME_DESC FROM CR_SCHEME_M S WHERE S.SCHEME_ID=a.SCHEME_ID), ");

		//Code start by sanjog

		bufInsSql.append("MUST_APPROVE_USER41,MUST_APPROVE_USER42,MUST_APPROVE_USER43,MUST_APPROVE_USER51,");
		bufInsSql.append("MUST_APPROVE_USER52,MUST_APPROVE_USER53,MUST_APPROVE_USER61,MUST_APPROVE_USER62,");
		bufInsSql.append("MUST_APPROVE_USER63,");
		bufInsSql.append("MUST_APPROVE_USER71,MUST_APPROVE_USER72,MUST_APPROVE_USER73,MUST_APPROVE_USER81,");
		bufInsSql.append("MUST_APPROVE_USER82,MUST_APPROVE_USER83,MUST_APPROVE_USER91,MUST_APPROVE_USER92,");
		bufInsSql.append("MUST_APPROVE_USER93,");

		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER41) As user41,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER42) As user42,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER43) As user43,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER51) As user51,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER52) As user52,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER53) As user53,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER61) As user61,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER62) As user62,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER63) As user63, ");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER71) As user71,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER72) As user72,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER73) As user73,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER81) As user81,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER82) As user82,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER83) As user83,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER91) As user91,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER92) As user92,");
		bufInsSql.append("(select c.USER_NAME from  SEC_USER_M c where c.USER_ID = a.MUST_APPROVE_USER93) As user93, ");
		bufInsSql.append("MIN_APPROVAL_LEVEL4,MIN_APPROVAL_LEVEL5,MIN_APPROVAL_LEVEL6,MIN_APPROVAL_LEVEL7,MIN_APPROVAL_LEVEL8,MIN_APPROVAL_LEVEL9 ");

		//Code ended by sanjog
		bufInsSql.append("FROM "+tableName+" a ");
		
		 if (!CommonFunction.checkNull(productModify).equalsIgnoreCase("")) {
				bufInsSql.append(" where APPROVAL_LEVEL_ID = '" + productModify + "' ");
			
			}

		 logger.info("search Query...." + bufInsSql.toString());
		// logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	
		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		
		logger.info("IN editApprovalLevelDef() search query1::::::::::::"+ bufInsSql.toString());
		
		for (int i = 0; i < searchlist.size(); i++) {
			logger.info("editApprovalLevelDef "+ searchlist.get(i).toString());
			ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				ApprovalLevelDefVo LevelDefVo = new ApprovalLevelDefVo();

				LevelDefVo.setProductSearch(CommonFunction.checkNull(data.get(0)));
				LevelDefVo.setLbxProductID(CommonFunction.checkNull(data.get(0)));
				LevelDefVo.setFindApprovalSearch(CommonFunction.checkNull(data.get(1)));
				LevelDefVo.setFindApprovalLevel(CommonFunction.checkNull(data.get(1)));
				if(!(CommonFunction.checkNull(data.get(2))).trim().equalsIgnoreCase(""))	
				{
					Number amount =myFormatter.parse((CommonFunction.checkNull(data.get(2))).trim());
					LevelDefVo.setAmountFromSearch(myFormatter.format(amount));
					LevelDefVo.setAmountFrom(myFormatter.format(amount));
				}
				if(!(CommonFunction.checkNull(data.get(3))).trim().equalsIgnoreCase(""))	
				{
					Number amount =myFormatter.parse((CommonFunction.checkNull(data.get(3))).trim());
					LevelDefVo.setAmountToSearch(myFormatter.format(amount));
					LevelDefVo.setAmountTo(myFormatter.format(amount));
				}
				LevelDefVo.setLevel1(CommonFunction.checkNull(data.get(4)));
				LevelDefVo.setLevel2(CommonFunction.checkNull(data.get(5)));
				LevelDefVo.setLevel3(CommonFunction.checkNull(data.get(6)));
				LevelDefVo.setLbxUserSearchId11(CommonFunction.checkNull(data.get(7)));
				LevelDefVo.setCondition1(CommonFunction.checkNull(data.get(8)));
				LevelDefVo.setLbxUserSearchId12(CommonFunction.checkNull(data.get(9)));
				LevelDefVo.setCondition2(CommonFunction.checkNull(data.get(10)));
				LevelDefVo.setLbxUserSearchId13(CommonFunction.checkNull(data.get(11)));
				LevelDefVo.setLbxUserSearchId21(CommonFunction.checkNull(data.get(12)));
				LevelDefVo.setCondition21(CommonFunction.checkNull(data.get(13)));
				LevelDefVo.setLbxUserSearchId22(CommonFunction.checkNull(data.get(14)));
				LevelDefVo.setCondition22(CommonFunction.checkNull(data.get(15)));
				LevelDefVo.setLbxUserSearchId23(CommonFunction.checkNull(data.get(16)));
				LevelDefVo.setLbxUserSearchId31(CommonFunction.checkNull(data.get(17)));
				LevelDefVo.setCondition31(CommonFunction.checkNull(data.get(18)));
				LevelDefVo.setLbxUserSearchId32(CommonFunction.checkNull(data.get(19)));
				LevelDefVo.setCondition32(CommonFunction.checkNull(data.get(20)));
				LevelDefVo.setLbxUserSearchId33(CommonFunction.checkNull(data.get(21)));
				LevelDefVo.setStatus(CommonFunction.checkNull(data.get(22)));
				LevelDefVo.setUser11(CommonFunction.checkNull(data.get(23)));
				LevelDefVo.setUser12(CommonFunction.checkNull(data.get(24)));
				LevelDefVo.setUser13(CommonFunction.checkNull(data.get(25)));
				LevelDefVo.setUser21(CommonFunction.checkNull(data.get(26)));
				LevelDefVo.setUser22(CommonFunction.checkNull(data.get(27)));
				LevelDefVo.setUser23(CommonFunction.checkNull(data.get(28)));
				LevelDefVo.setUser31(CommonFunction.checkNull(data.get(29)));
				LevelDefVo.setUser32(CommonFunction.checkNull(data.get(30)));
				LevelDefVo.setUser33(CommonFunction.checkNull(data.get(31)));	
				LevelDefVo.setProduct(CommonFunction.checkNull(data.get(32)));
				LevelDefVo.setLbxSchemeId(CommonFunction.checkNull(data.get(33)));
				LevelDefVo.setScheme(CommonFunction.checkNull(data.get(34)));
				
				// Code started by Sanjog
				LevelDefVo.setLbxUserSearchId41(CommonFunction.checkNull(data.get(35)));
				LevelDefVo.setLbxUserSearchId42(CommonFunction.checkNull(data.get(36)));
				LevelDefVo.setLbxUserSearchId43(CommonFunction.checkNull(data.get(37)));
				LevelDefVo.setLbxUserSearchId51(CommonFunction.checkNull(data.get(38)));
				LevelDefVo.setLbxUserSearchId52(CommonFunction.checkNull(data.get(39)));
				LevelDefVo.setLbxUserSearchId53(CommonFunction.checkNull(data.get(40)));
				LevelDefVo.setLbxUserSearchId61(CommonFunction.checkNull(data.get(41)));
				LevelDefVo.setLbxUserSearchId62(CommonFunction.checkNull(data.get(42)));
				LevelDefVo.setLbxUserSearchId63(CommonFunction.checkNull(data.get(43)));
				LevelDefVo.setLbxUserSearchId71(CommonFunction.checkNull(data.get(44)));
				LevelDefVo.setLbxUserSearchId72(CommonFunction.checkNull(data.get(45)));
				LevelDefVo.setLbxUserSearchId73(CommonFunction.checkNull(data.get(46)));
				LevelDefVo.setLbxUserSearchId81(CommonFunction.checkNull(data.get(47)));
				LevelDefVo.setLbxUserSearchId82(CommonFunction.checkNull(data.get(48)));
				LevelDefVo.setLbxUserSearchId83(CommonFunction.checkNull(data.get(49)));
				LevelDefVo.setLbxUserSearchId91(CommonFunction.checkNull(data.get(50)));
				LevelDefVo.setLbxUserSearchId92(CommonFunction.checkNull(data.get(51)));
				LevelDefVo.setLbxUserSearchId93(CommonFunction.checkNull(data.get(52)));
				LevelDefVo.setUser41(CommonFunction.checkNull(data.get(53)));
				LevelDefVo.setUser42(CommonFunction.checkNull(data.get(54)));
				LevelDefVo.setUser43(CommonFunction.checkNull(data.get(55)));
				LevelDefVo.setUser51(CommonFunction.checkNull(data.get(56)));
				LevelDefVo.setUser52(CommonFunction.checkNull(data.get(57)));
				LevelDefVo.setUser53(CommonFunction.checkNull(data.get(58)));
				LevelDefVo.setUser61(CommonFunction.checkNull(data.get(59)));
				LevelDefVo.setUser62(CommonFunction.checkNull(data.get(60)));
				LevelDefVo.setUser63(CommonFunction.checkNull(data.get(61)));
				LevelDefVo.setUser71(CommonFunction.checkNull(data.get(62)));
				LevelDefVo.setUser72(CommonFunction.checkNull(data.get(63)));
				LevelDefVo.setUser73(CommonFunction.checkNull(data.get(64)));
				LevelDefVo.setUser81(CommonFunction.checkNull(data.get(65)));
				LevelDefVo.setUser82(CommonFunction.checkNull(data.get(66)));
				LevelDefVo.setUser83(CommonFunction.checkNull(data.get(67)));
				LevelDefVo.setUser91(CommonFunction.checkNull(data.get(68)));
				LevelDefVo.setUser92(CommonFunction.checkNull(data.get(69)));
				LevelDefVo.setUser93(CommonFunction.checkNull(data.get(70)));
				LevelDefVo.setLevel4(CommonFunction.checkNull(data.get(71)));
				LevelDefVo.setLevel5(CommonFunction.checkNull(data.get(72)));
				LevelDefVo.setLevel6(CommonFunction.checkNull(data.get(73)));
				LevelDefVo.setLevel7(CommonFunction.checkNull(data.get(74)));
				LevelDefVo.setLevel8(CommonFunction.checkNull(data.get(75)));
				LevelDefVo.setLevel9(CommonFunction.checkNull(data.get(76)));
				//Code ended by sanjog
				
				
				LevelDefVo.setTotalRecordSize(count);
				detailList.add(LevelDefVo);
			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;		
}


public String updateApprovalLevelDef(ApprovalLevelDefVo Vo, String productModify,String mcFlag) {

    logger.info("In MasterDAOImpl........................updateApprovalLevelDef...........");
	boolean status = false;
	String flag="";
	String tableName="";
	String query="";
	String queryDelete="";
	String exist="";
	ArrayList qryList = new ArrayList();
	ArrayList updatelist = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	StringBuffer bufInsSql = new StringBuffer();
	String stat = "X";
	String count1="";
	int num=0;
	
	if (Vo.getStatus() != null
			&& Vo.getStatus().equals("on")) {
		stat = "A";
	} else {
		stat = "X";

	}
	if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y"))
		tableName="cr_approval_level_m_temp";
	else
		tableName="cr_approval_level_m";
		

	try {
		if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y")){						
			
			if(CommonFunction.checkNull(Vo.getDecideUpdate()).equalsIgnoreCase("Y")){
				query = "SELECT  APPROVAL_LEVEL_ID FROM cr_approval_level_m_temp WHERE  APPROVAL_LEVEL_ID_MAIN='"+CommonFunction.checkNull(productModify)+"'";
				 exist= ConnectionDAOforEJB.singleReturn(query);
				 if(!CommonFunction.checkNull(exist).equalsIgnoreCase("")){
					 PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
					  queryDelete= "DELETE FROM cr_approval_level_m_temp WHERE APPROVAL_LEVEL_ID='"+ exist + "'";
						logger.info("In DELETE .....................................Dao Impl"+ queryDelete);
						insertPrepStmtObject1.setSql(queryDelete);
						updatelist.add(insertPrepStmtObject1);	
					boolean 	status1 = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);	
					 logger.info("status  main :::::::::::::"+status1);
				    insertPrepStmtObject1=null;
				    updatelist.clear();
				    updatelist=null;
				 }
				 logger.info("value  of  num :::::::::::::"+num);
				
			}					
			else{
				query = "SELECT  COUNT(1) FROM cr_approval_level_m_temp WHERE  APPROVAL_LEVEL_ID='"+CommonFunction.checkNull(productModify)+"'";
			logger.info("in  getApprovalId:::::::::::::query::::::: "+query);
			 exist= ConnectionDAOforEJB.singleReturn(query);
			 if(!CommonFunction.checkNull(exist).equalsIgnoreCase(""))
			  num=Integer.parseInt(exist);
			}
		}
		else{
			num=10;
		}
		 logger.info("in  getApprovalId:::::::::::::value ::::::: "+productModify);
		 logger.info("in  getApprovalId:::::::::num ::::value ::::::: "+num);
		
		if(num >0){
			
		
			bufInsSql.append("UPDATE "+tableName+" set PRODUCT_ID=?,SCHEME_ID=?,FINAL_APPROVAL_LEVEL=?, AMOUNT_FROM=?, AMOUNT_TO=?, " +
					"MIN_APPROVAL_LEVEL1=?, MIN_APPROVAL_LEVEL2=?, MIN_APPROVAL_LEVEL3=?, MUST_APPROVE_USER11=?, MUST_APPROVE_USER12=?, " +
					"MUST_APPROVE_USER13=?, MUST_APPROVE_USER21=?, MUST_APPROVE_USER22=?, " +
					"MUST_APPROVE_USER23=?, MUST_APPROVE_USER31=?, MUST_APPROVE_USER32=?,  MUST_APPROVE_USER33=?, ");
			bufInsSql.append("REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" AUTHOR_ID=?, AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
			bufInsSql.append("MIN_APPROVAL_LEVEL4=?, MIN_APPROVAL_LEVEL5=?, MIN_APPROVAL_LEVEL6=?,MIN_APPROVAL_LEVEL7=?, MIN_APPROVAL_LEVEL8=?, MIN_APPROVAL_LEVEL9=?, " +
					"MUST_APPROVE_USER41=?, MUST_APPROVE_USER42=?, MUST_APPROVE_USER43=?, " +
					"MUST_APPROVE_USER51=?, MUST_APPROVE_USER52=?, MUST_APPROVE_USER53=?, " +
					"MUST_APPROVE_USER61=?, MUST_APPROVE_USER62=?, MUST_APPROVE_USER63=?," +
					"MUST_APPROVE_USER71=?, MUST_APPROVE_USER72=?, MUST_APPROVE_USER73=?, " +
					"MUST_APPROVE_USER81=?, MUST_APPROVE_USER82=?, MUST_APPROVE_USER83=?, " +
					"MUST_APPROVE_USER91=?, MUST_APPROVE_USER92=?, MUST_APPROVE_USER93=? ");
			if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y"))
				bufInsSql.append(",MAKER_AUTHOR_STATUS=?");
			bufInsSql.append(" where APPROVAL_LEVEL_ID='" + productModify + "'");
			
			logger.info("productModify:::::::::::"+productModify);
			logger.info("Vo.getLbxProductID()::::"+Vo.getLbxProductID());
			logger.info("Vo.getFindApprovalLevel():::::::::"+Vo.getFindApprovalLevel());
			
			if (CommonFunction.checkNull(Vo.getLbxProductID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();

			else{
				insertPrepStmtObject.addString(Vo.getLbxProductID().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(Vo.getLbxSchemeId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();

			else{
				insertPrepStmtObject.addString(Vo.getLbxSchemeId().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(Vo.getFindApprovalLevel()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getFindApprovalLevel().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(Vo.getAmountFrom()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(Vo.getAmountFrom().toUpperCase().trim())).toString());
			}
			if (CommonFunction.checkNull(Vo.getAmountTo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(Vo.getAmountTo().toUpperCase().trim())).toString());
			}
			if (CommonFunction.checkNull(Vo.getLevel1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel1().trim());
			}
			if (CommonFunction.checkNull(Vo.getLevel2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel2().trim().toString()); 
			}
			if (CommonFunction.checkNull(Vo.getLevel3()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
		   insertPrepStmtObject.addString((Vo.getLevel3().trim()).toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId11()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId11()).trim()));
			}
		
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId12()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId12()).trim())); 
			}
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId13()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId13()).trim()));
		}
			
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId21()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
	else{
		insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId21()).trim()));
		}
	
	if (CommonFunction.checkNull(Vo.getLbxUserSearchId22()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
	else{
		insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId22()).trim())); 
		}
	if (CommonFunction.checkNull(Vo.getLbxUserSearchId23()).equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else{
		insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId23()).trim()));
	}
	
	
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId31()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
	else{
		insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId31()).trim()));
		}
	
	if (CommonFunction.checkNull(Vo.getLbxUserSearchId32()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
	else{
		insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId32()).trim())); 
		}
	if (CommonFunction.checkNull(Vo.getLbxUserSearchId33()).equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else{
		insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId33()).trim()));
	}
			
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(stat); 
			}
			if (CommonFunction.checkNull(Vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerId()); 
			}
			if (CommonFunction.checkNull(Vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerDate()); 
			}
			if (CommonFunction.checkNull(Vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerId()); 
			}
			if (CommonFunction.checkNull(Vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerDate()); 			   
			}
			
			//Code start by sanjog
			if (CommonFunction.checkNull(Vo.getLevel4()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel4().trim());
			}
			if (CommonFunction.checkNull(Vo.getLevel5()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel5().trim().toString()); 
			}
			if (CommonFunction.checkNull(Vo.getLevel6()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
		   insertPrepStmtObject.addString((Vo.getLevel6().trim()).toString()); 
			}
			if (CommonFunction.checkNull(Vo.getLevel7()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel7().trim());
			}
			if (CommonFunction.checkNull(Vo.getLevel8()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel8().trim().toString()); 
			}
			if (CommonFunction.checkNull(Vo.getLevel9()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
		   insertPrepStmtObject.addString((Vo.getLevel9().trim()).toString()); 
			}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId41()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId41()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId42()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId42()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId43()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId43()).trim()));
			}
				
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId51()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId51()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId52()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId52()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId53()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId53()).trim()));
			}
			
			
				if (CommonFunction.checkNull(Vo.getLbxUserSearchId61()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId61()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId62()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId62()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId63()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId63()).trim()));
			}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId71()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId71()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId72()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId72()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId73()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId73()).trim()));
			}
				
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId81()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId81()).trim()));
			}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId82()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId82()).trim())); 
			}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId83()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId83()).trim()));
			}
			
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId91()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId91()).trim()));
			}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId92()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId92()).trim())); 
			}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId93()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId93()).trim()));
			}
			
			//Code ended by sanjog
			// code by saurabh 
			if(CommonFunction.checkNull(mcFlag).equalsIgnoreCase("Y"))
				insertPrepStmtObject.addString("P");

				    insertPrepStmtObject.setSql(bufInsSql.toString()); 
			    
				    logger.info("IN updateApprovalLevelDef() insert query1:::::"	+ insertPrepStmtObject.printQuery());
			
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		}
		else{
			//saurabh changes starts
			
			//StringBuffer bufInsSql = new StringBuffer();
			String columnAdd=",MAKER_AUTHOR_STATUS";
			
			bufInsSql.append("INSERT INTO cr_approval_level_m_temp(PRODUCT_ID,SCHEME_ID,FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO," +
					"MIN_APPROVAL_LEVEL1,MIN_APPROVAL_LEVEL2,MIN_APPROVAL_LEVEL3,MUST_APPROVE_USER11,MUST_APPROVE_USER12," +
					"MUST_APPROVE_USER13,MUST_APPROVE_USER21,MUST_APPROVE_USER22," +
					"MUST_APPROVE_USER23,MUST_APPROVE_USER31,MUST_APPROVE_USER32,MUST_APPROVE_USER33," +
					"REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE," +
					"MIN_APPROVAL_LEVEL4,MIN_APPROVAL_LEVEL5,MIN_APPROVAL_LEVEL6,MIN_APPROVAL_LEVEL7,MIN_APPROVAL_LEVEL8,MIN_APPROVAL_LEVEL9," +
					"MUST_APPROVE_USER41,MUST_APPROVE_USER42," +
					"MUST_APPROVE_USER43,MUST_APPROVE_USER51,MUST_APPROVE_USER52," +
					"MUST_APPROVE_USER53,MUST_APPROVE_USER61,MUST_APPROVE_USER62,MUST_APPROVE_USER63," +
					"MUST_APPROVE_USER71,MUST_APPROVE_USER72," +
					"MUST_APPROVE_USER73,MUST_APPROVE_USER81,MUST_APPROVE_USER82," +
					"MUST_APPROVE_USER83,MUST_APPROVE_USER91,MUST_APPROVE_USER92,APPROVAL_LEVEL_ID_MAIN,MUST_APPROVE_USER93"+columnAdd+")");				
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");//PRODUCT_ID 
			bufInsSql.append(" ?,");//SCHEME_ID 
			bufInsSql.append(" ?,");//FINAL_APPROVAL_LEVEL 
			bufInsSql.append(" ?,");//AMOUNT_FROM 
			bufInsSql.append(" ?,");//AMOUNT_TO 
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL1
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL2
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL3 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER11 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER12 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER13 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER21
			bufInsSql.append(" ?,");//MUST_APPROVE_USER22 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER23 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER31 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER32 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER33 
			bufInsSql.append(" ?,");//REC_STATUS
			bufInsSql.append(" ?,");//MAKER_ID 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" ?,");//AUTHOR_ID 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL4
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL5
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL6
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL7
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL8
			bufInsSql.append(" ?,");//MIN_APPROVAL_LEVEL9
			bufInsSql.append(" ?,");//MUST_APPROVE_USER41 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER42 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER43 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER51
			bufInsSql.append(" ?,");//MUST_APPROVE_USER52 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER53 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER61 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER62 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER63
			bufInsSql.append(" ?,");//MUST_APPROVE_USER71 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER72 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER73 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER81
			bufInsSql.append(" ?,");//MUST_APPROVE_USER82 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER83 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER91 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER92
			bufInsSql.append(" ?,");//APPROVE_LEVEL_ID 
			bufInsSql.append(" ?,");//MUST_APPROVE_USER93
			bufInsSql.append(" ?)");//MAKER_CHECKER_FLAG

									
			logger.info("Vo.getLbxProductID():::::::::::::"+Vo.getLbxProductID());
			logger.info("Vo.getLbxUserSearchId1:::::::::::"+Vo.getLbxUserSearchId11());
			logger.info("Vo.getLbxUserSearchId2:::::::::::"+Vo.getLbxUserSearchId12());
			logger.info("Vo.getLbxUserSearchId3:::::::::::"+Vo.getLbxUserSearchId13());
			
			if (CommonFunction.checkNull(Vo.getLbxProductID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();

			else{
				insertPrepStmtObject.addString(Vo.getLbxProductID().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(Vo.getLbxSchemeId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();

			else{
				insertPrepStmtObject.addString(Vo.getLbxSchemeId().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(Vo.getFindApprovalLevel()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getFindApprovalLevel().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(Vo.getAmountFrom()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(Vo.getAmountFrom().toUpperCase().trim())).toString()); 
			}
			if (CommonFunction.checkNull(Vo.getAmountTo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(Vo.getAmountTo().toUpperCase().trim())).toString()); 
			}
			if (CommonFunction.checkNull(Vo.getLevel1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel1().trim());
			}
			if (CommonFunction.checkNull(Vo.getLevel2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel2().trim().toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLevel3()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
		   insertPrepStmtObject.addString((Vo.getLevel3().trim()).toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId11()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId11()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId12()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId12()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId13()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId13()).trim()));
			}
				
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId21()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId21()).trim()));
			}
		
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId22()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId22()).trim())); 
			}
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId23()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId23()).trim()));
		}
		
		
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId31()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId31()).trim()));
			}
		
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId32()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId32()).trim())); 
			}
		if (CommonFunction.checkNull(Vo.getLbxUserSearchId33()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else{
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId33()).trim()));
		}
			
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(stat); 
			}
			
			if (CommonFunction.checkNull(Vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerId()); 
			}
			if (CommonFunction.checkNull(Vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerDate()); 
			}
			if (CommonFunction.checkNull(Vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerId()); 
			}
			if (CommonFunction.checkNull(Vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(Vo.getMakerDate()); 			   
			}
			
			//Changes made by sanjog
			if (CommonFunction.checkNull(Vo.getLevel4()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel4().trim());
			}
			if (CommonFunction.checkNull(Vo.getLevel5()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel5().trim().toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLevel6()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
		   insertPrepStmtObject.addString((Vo.getLevel6().trim()).toString()); 
			}
			if (CommonFunction.checkNull(Vo.getLevel7()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel7().trim());
			}
			if (CommonFunction.checkNull(Vo.getLevel8()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
			insertPrepStmtObject.addString(Vo.getLevel8().trim().toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLevel9()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
		   insertPrepStmtObject.addString((Vo.getLevel9().trim()).toString()); 
			}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId41()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId41()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId42()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId42()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId43()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId43()).trim()));
			}
				
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId51()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId51()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId52()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId52()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId53()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId53()).trim()));
			}
			
			
				if (CommonFunction.checkNull(Vo.getLbxUserSearchId61()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId61()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId62()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId62()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId63()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId63()).trim()));
			}
			
			
					if (CommonFunction.checkNull(Vo.getLbxUserSearchId71()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId71()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId72()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId72()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId73()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId73()).trim()));
			}
				
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId81()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId81()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId82()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId82()).trim())); 
				}
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId83()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId83()).trim()));
			}
			
			
				if (CommonFunction.checkNull(Vo.getLbxUserSearchId91()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId91()).trim()));
				}
			
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId92()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId92()).trim())); 
				}
		//Saurabh changes starts		
			if (CommonFunction.checkNull(productModify).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(productModify));
			}
		//Saurabh Changes ends
			if (CommonFunction.checkNull(Vo.getLbxUserSearchId93()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxUserSearchId93()).trim()));
			}
	// saurabh changes 

			insertPrepStmtObject.addString("P");
			

	// Changes done by sanjog

				    insertPrepStmtObject.setSql(bufInsSql.toString()); 
			    
				    logger.info("IN saveApprovalLevelDef() insert query1:::::"	+ insertPrepStmtObject.printQuery());
			
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);			
			
		//saurabh changes ends 
		}
			if(status){
				flag="S";
			}
			else{
				flag="N";
			}
		
			logger.info("In updateApprovalLevelDef......................"+ flag);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		queryDelete=null;
		tableName=null;
		query=null;
		queryDelete=null;
		exist=null;
}

	return flag;
}


	public ArrayList getRoleType() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getRoleType().........Inside ejb server file.........DAOImpl");
			String query = "select VALUE,DESCRIPTION from generic_master WHERE GENERIC_KEY='RULE_TYPE' and REC_STATUS='A'";
			logger.info("query : " + query);
			MasterVo vO = null;
			ArrayList roleList = ConnectionDAOforEJB.sqlSelect(query);
			logger.info("roleList " + roleList.size());
			for (int i = 0; i < roleList.size(); i++) {
				logger.info("roleList "
						+ CommonFunction.checkNull(roleList.get(i)).toString());
				ArrayList data = (ArrayList) roleList.get(i);
				for (int k = 0; k < data.size(); k++) {
					logger.info("getRoleType "
							+ CommonFunction.checkNull(data.get(k)).toString());
					vO = new MasterVo();
					vO.setRuleValue(((String) data.get(0).toString()));
					vO.setRuleDescription(((String) data.get(1).toString()));
				}
				list.add(vO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList getSubRoleType() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getSubRoleType().........Inside ejb server file.........DAOImpl");
			String query = "select VALUE,DESCRIPTION from generic_master WHERE GENERIC_KEY='SUB_RULE_TYPE' and REC_STATUS='A'";
			logger.info("query : " + query);
			MasterVo vO = null;
			ArrayList subRoleList = ConnectionDAOforEJB.sqlSelect(query);
			logger.info("subRoleList " + subRoleList.size());
			for (int i = 0; i < subRoleList.size(); i++) {
				logger.info("roleList "
						+ CommonFunction.checkNull(subRoleList.get(i)).toString());
				ArrayList data = (ArrayList) subRoleList.get(i);
				for (int k = 0; k < data.size(); k++) {
					logger.info("getSubRoleType "
							+ CommonFunction.checkNull(data.get(k)).toString());
					vO = new MasterVo();
					vO.setSubRuleValue(((String) data.get(0).toString()));
					vO.setSubRuleDescription(((String) data.get(1).toString()));
				}
				list.add(vO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String saveRuleDetail(Object ob) {
		MasterVo vo = (MasterVo) ob;
		String status = "";
		int ct=0;
		
		Connection con=ConnectionDAOforEJB.getConnection();
		CallableStatement cst=null;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "SELECT COUNT(1) FROM cr_rule_m where RULE_CODE='"+ StringEscapeUtils.escapeSql(vo.getRuleCode().trim())+"'";
		
		logger.info("In saveRuleDetail.............Inside ejb server file.........Dao Impl"
						+ query);
		logger.info("value of stage::::::::::"+vo.getStageForRule());
		String st=ConnectionDAOforEJB.singleReturn(query);
		ct=Integer.parseInt(st);	
				
		if (ct==0)
			{
				try
				{
				if (vo.getRuleStatus() != null
						&& vo.getRuleStatus().equals("on"))
				{
					stat = "A";
				} else {
					stat = "X";
				}
				//con.setAutoCommit(false);
			
				
				logger.info("CR_Rule_Expression_Validation Parameters: "+vo.getRuleCode()+","+vo.getRuleName()+","+vo.getRuleType()+","+vo.getSubRuleType()+","+vo.getExpression()+","+vo.getRuleCodeModify()+","+stat+","+vo.getMakerId()+","+CommonFunction.changeFormat(vo.getMakerDate())+","+vo.getStageForRule()+","+vo.getSessionId());
				cst=con.prepareCall("{call CR_Rule_Expression_Validation(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cst.setString(1, (CommonFunction.checkNull(vo.getRuleCode())).trim());
				cst.setString(2, (CommonFunction.checkNull(vo.getRuleName())).trim());
				cst.setString(3, (CommonFunction.checkNull(vo.getRuleType())).trim());
				cst.setString(4, (CommonFunction.checkNull(vo.getSubRuleType())).trim());
				cst.setString(5, (CommonFunction.checkNull(vo.getExpression())).trim());
				cst.setString(6, (CommonFunction.checkNull(vo.getRuleCodeModify())).trim());
				cst.setString(7, (CommonFunction.checkNull(stat)).trim());
				cst.setString(8, (CommonFunction.checkNull(vo.getMakerId())).trim());
			    cst.setString(9, (CommonFunction.checkNull(CommonFunction.changeFormat(vo.getMakerDate()))).trim());
				cst.setString(10, (CommonFunction.checkNull(vo.getStageForRule())).trim());
				cst.setString(11, (CommonFunction.checkNull(vo.getSessionId())).trim());
				cst.registerOutParameter(12, Types.CHAR);
				cst.registerOutParameter(13, Types.CHAR);
				logger.info("cst &&&&&&&&&&&&&&&&& "+cst.toString());
				cst.execute();
				String s1= cst.getString(12);
				String s2 = cst.getString(13);
				logger.info("S1 : "+s1);
				logger.info("S2 "+s2);
				if(s1!=null && s1.equalsIgnoreCase("S"))
				{
					status=s1;
					//con.commit();
					logger.info("Procedure Error Message----"+s2);
				}
				else
				{
					status=s2;
					//con.rollback();
					
					logger.info("Procedure Error Message----"+s2);
				}
				logger.info("s1: "+s1);
				logger.info("s2: "+s2);
			
			
		} catch (Exception e) {
			try {
				//con.rollback();
				status="ERROR! IN PROCEDURE EXECUTION,PLEASE CONTACT ADMINISTRATOR....";
				cst.close();
			} catch (SQLException e1) {
				logger.info("Rollback "+e1);
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			try {
				//con.commit();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
		return status;
	}
	
	
	public String updateRuleDetail(Object ob) {
		MasterVo vo = (MasterVo) ob;
		logger.info("updateRuleDetail:-" + vo.getRuleStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();

		String status = "";
		String stat = "";
				
		Connection con=ConnectionDAOforEJB.getConnection();
		CallableStatement cst=null;
		ArrayList qryList = new ArrayList();
		String query = "select RULE_CODE,RULE_DESCRIPTION from cr_rule_m where RULE_CODE='"
				+ StringEscapeUtils.escapeSql(vo.getRuleCode().trim())+"'";
		
		logger.info("In updateRuleDetail............Inside ejb server file.........DAOImpl"+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
			
			
		try {
			if (vo.getRuleStatus() != null && vo.getRuleStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			logger.info("start ^^^^^^^^^^^^^^^^^^^^^^^^^^^^::::::::::::::::saurabh"+vo.getStageForRule());
				//con.setAutoCommit(false);
			    cst=con.prepareCall("{call CR_Rule_Expression_Validation(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				cst.setString(1, (CommonFunction.checkNull(vo.getRuleCode())).trim());
				cst.setString(2, (CommonFunction.checkNull(vo.getRuleName())).trim());
				cst.setString(3, (CommonFunction.checkNull(vo.getRuleType())).trim());
				cst.setString(4, (CommonFunction.checkNull(vo.getSubRuleType())).trim());
				cst.setString(5, (CommonFunction.checkNull(vo.getExpression())).trim());
				cst.setString(6, (CommonFunction.checkNull(vo.getRuleCodeModify())).trim());
				cst.setString(7, (CommonFunction.checkNull(stat)).trim());
				cst.setString(8, (CommonFunction.checkNull(vo.getMakerId())).trim());
		        cst.setString(9, (CommonFunction.checkNull(CommonFunction.changeFormat(vo.getMakerDate()))).trim());
				cst.setString(10, (CommonFunction.checkNull(vo.getStageForRule())).trim());
				cst.setString(11, (CommonFunction.checkNull(vo.getSessionId())).trim());
				cst.registerOutParameter(12, Types.CHAR);
				cst.registerOutParameter(13, Types.CHAR);
				logger.info("cst &&&&&&&&&&&&&&&&&&&&&&&&&& "+cst.toString());

				cst.executeUpdate();
				String s1= cst.getString(12);
				String s2 = cst.getString(13);
				if(s1!=null && s1.equalsIgnoreCase("S"))
				{
					status=s1;
					//con.commit();
					logger.info("Procedure Error Message----"+s2);
				}
				else
				{
					status=s2;
					//con.rollback();
					
					logger.info("Procedure Error Message----"+s2);
				}
				logger.info("s1: "+s1);
				logger.info("s2: "+s2);
			
		} catch (Exception e) {
			try {
				//con.rollback();
				cst.close();
			} catch (SQLException e1) {
				logger.info("Rollback "+e1);
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			try {
				//con.commit();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}
	


	public boolean checkExpression(Object ob) {
		MasterVo vo = (MasterVo) ob;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();

		boolean status = false;
		String expr = "";
		String param = "";
		try {

			String query = "select PARAM_CODE from cr_financial_param where REC_STATUS='A'";
			logger.info("query : " + query);
			MasterVo vO = null;
			ArrayList paramCodeList = ConnectionDAOforEJB.sqlSelect(query);
			expr = vo.getExpression();
			logger.info("paramCodeList " + paramCodeList.size());
			for (int i = 0; i < paramCodeList.size(); i++) {
				
				ArrayList data = (ArrayList) paramCodeList.get(i);
				for (int k = 0; k < data.size(); k++) 
				{
					param = (String) data.get(0).toString();
					logger.info("param ******************* "+param);
					logger.info("expr.indexOf(param) ******************* "+expr.indexOf(param));
					if(expr.indexOf(param) >=0)
					{
						
						expr = expr.replaceAll(param, "1");
						logger.info("expr in if******************* "+expr);
						//ratioExpr = vo.getRatioExpr().replaceFirst(paramCodeInExpr, vo1.getFirstYear());
					}
				}
				
			}
			logger.info("expression .................................. "+expr);
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			
			logger.info("CommonFunction.checkNull(engine.eval(expr):::::::::::: "+CommonFunction.checkNull(engine.eval(expr)+""));
			if(!CommonFunction.checkNull(engine.eval(expr)+"").equalsIgnoreCase(""))
			{
				status=true;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		logger.info("status .................................. "+status);
		return status;
	}
	
	public ArrayList getParamDetailDetails(String sourceType) {
		ArrayList<Object> list=new ArrayList<Object>();
//		String query="";
		StringBuilder query=new StringBuilder();
		try{
			
		if(sourceType.equalsIgnoreCase(""))
		{
			query.append("SELECT PARAM_CODE,PARAM_NAME FROM cr_financial_param WHERE REC_STATUS='A'");
		}
		else
		{
			query.append("SELECT PARAM_CODE,PARAM_NAME FROM cr_financial_param WHERE REC_STATUS='A' and SOURCE_TYPE='"+sourceType+"'");
		}
		logger.info("query : Nishant2"+query);
		ArrayList paramDetail = ConnectionDAOforEJB.sqlSelect(query.toString());
		
		query=null;
		
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				FinancialAnalysisVo vo = new FinancialAnalysisVo();
				vo.setParameCode((CommonFunction.checkNull(subParamDetail.get(0))).trim());
				//vo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subParamDetail.get(1))).trim());
			
                list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}

	public boolean insertCardScoreMaster1(Object ob) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<scoreCardMasterVo> searchCardScoreDetails(Object ob,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<pcdMasterVo> searchPolicyListData(Object ob) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getRecordStatus(ApprovalLevelDefVo vo) 
	{
		boolean status=false;
		int count=0;		
		String productId=vo.getLbxProductID().trim();
		int finalApprovalLevel=Integer.parseInt(vo.getFindApprovalLevel().trim());
		String query =" select count(1) from cr_approval_level_m where PRODUCT_ID ='"+productId+"' and  FINAL_APPROVAL_LEVEL="+finalApprovalLevel;
		logger.info("query : "+query);		
		try
		{
			ArrayList header = ConnectionDAO.sqlSelect(query);
			ArrayList subList=(ArrayList)header.get(0);
			String num=(String)subList.get(0);
			count=Integer.parseInt(num);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(count>0)
			status=true;
		return status;
	}
	
	public ArrayList getRuleMasterParam(String sourceType) {
		ArrayList<Object> list=new ArrayList<Object>();
		logger.info("In getRuleMasterParam............Inside ejb server file......Dao Impl");
		String query="";
		try{
			
			if(sourceType.equalsIgnoreCase(""))
			{
				query="select RULE_PARAM_CODE , RULE_PARAM_NAME from cr_rule_param_m where REC_STATUS = 'A'";
			}
			else
			{
				query="select RULE_PARAM_CODE , RULE_PARAM_NAME from cr_rule_param_m where REC_STATUS = 'A' and SOURCE_DATA_TYPE='"+sourceType+"'";
			}
		logger.info("query : "+query);
		ArrayList paramDetail = ConnectionDAOforEJB.sqlSelect(query);
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				FinancialAnalysisVo vo = new FinancialAnalysisVo();
				vo.setParameCode((CommonFunction.checkNull(subParamDetail.get(0))).trim());
				//vo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subParamDetail.get(1))).trim());
			
                list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	public ArrayList defaultCountry1() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In defaultcountry1()..........inside ejb server file.........DAOImpl");
			StringBuilder query=new StringBuilder();
			
			 query.append("SELECT PARAMETER_VALUE,PARAMETER_DESC FROM parameter_mst WHERE PARAMETER_KEY='DEFAULT_COUNTRY'");
			logger.info("In defaultcountry...............query...........DAOImpl"+ query);
			BankBranchMasterVo vo = null;
			ArrayList country = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			
			logger.info("defaultcountry() " + country.size());
			for (int i = 0; i < country.size(); i++) {
				logger.info("defaultcountry()...Outer FOR loop "+ CommonFunction.checkNull(country.get(i)).toString());
				ArrayList data = (ArrayList) country.get(i);
				if (data.size() > 0) {
					vo = new BankBranchMasterVo();
					vo.setDefaultcountryid((CommonFunction.checkNull(data.get(0))).trim());
					vo.setDefaultcountryname((CommonFunction.checkNull(data.get(1))).trim());
					list.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public String checkgroupName(String group){
		String groupName="";
		try{
		String query1= "SELECT count(1) FROM gcd_group_m where GROUP_DESC='"+ StringEscapeUtils.escapeSql(group) + "'";
		groupName = ConnectionDAOforEJB.singleReturn(query1);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("checkgroupName------------->" +groupName);
		return groupName;

	}
	

	public ArrayList getAreaCode() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getAreaCode()...............DAOImpl");
			StringBuilder query=new StringBuilder();
			
			 query.append("select PARAMETER_KEY,PARAMETER_VALUE from com_areacode_m");
			logger.info("In getAreaCode...............query...........DAOImpl"+ query);
			BranchMasterVo vo = null;
			ArrayList country = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			
			logger.info("getAreaCode() " + country.size());
			for (int i = 0; i < country.size(); i++) {
				logger.info("getAreaCode()...Outer FOR loop "+ CommonFunction.checkNull(country.get(i)).toString());
				ArrayList data = (ArrayList) country.get(i);
				if (data.size() > 0) {
					vo = new BranchMasterVo();
					vo.setAreaCode((CommonFunction.checkNull(data.get(0))).trim());
					vo.setAreaDesc((CommonFunction.checkNull(data.get(1))).trim());
					list.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public boolean insertBranchAreaCode(BranchMasterVo branchMasterVo, String[] areaCode) {
		
		boolean status = false;
		logger.info("In insertBranchAreaCode.........."+ branchMasterVo.getBranchStatus());
		ArrayList qryList = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = null;
		String stat = "X";
		String branchId =CommonFunction.checkNull(branchMasterVo.getBranchId().trim());
		try{
		Connection con=ConnectionDAOforEJB.getConnection();
		try {
			  
			String query = "SELECT count(1) FROM com_areacode_branch_mapping WHERE BRANCH_ID='"+branchId + "' ";
			logger.info("In insertBranchAreaCode.....................................Dao Impl"+ query);
			String valcount =CommonFunction.checkNull(ConnectionDAOforEJB.singleReturn(query));
			logger.info("valcount------->"+valcount);
			if(!CommonFunction.checkNull(valcount).equalsIgnoreCase("")){
			int count=Integer.parseInt(valcount);
			if(count>0){
					insertPrepStmtObject = new PrepStmtObject();
					String strquery = "DELETE FROM com_areacode_branch_mapping WHERE BRANCH_ID='"+ branchId +"'";
					insertPrepStmtObject.setSql(strquery);
					qryList.add(insertPrepStmtObject);
				}
			}
	
					if (branchMasterVo.getBranchStatus() != null && branchMasterVo.getBranchStatus().equals("on")) {
						stat = "A";
					} else {
						stat = "X";
	
					}
					if(areaCode!=null){
						if(areaCode.length>0){
					
					for (int i = 0; i < areaCode.length; i++) {
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql = new StringBuffer();
					bufInsSql.append("INSERT INTO com_areacode_branch_mapping(BRANCH_ID,AREA_CODE,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					if (CommonFunction.checkNull(branchMasterVo.getBranchId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(branchMasterVo.getBranchId()
								.toUpperCase());

					if (CommonFunction.checkNull(areaCode[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(areaCode[i]);
					
					if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);

					if (CommonFunction.checkNull(branchMasterVo.getMakerId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(branchMasterVo.getMakerId());

					if (CommonFunction.checkNull(branchMasterVo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(branchMasterVo.getMakerDate());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertBranchAreaCode() insert query1 ### "
							+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
						
				}
			}
if(qryList.size()>0){
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
}
				logger.info("In insertBranchAreaCode...S..................."	+ status);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	

	public ArrayList<BranchMasterAreaCodeVo> searchAreaCodeBranchEdit(String branchId) {

		ArrayList searchlist = new ArrayList();
		UserMasterVo userMasterVo = new UserMasterVo();
		ArrayList<BranchMasterAreaCodeVo> areaCodeList = new ArrayList<BranchMasterAreaCodeVo>();

		try {
			logger.info("In searchAreaCodeBranchEdit().....................................Dao Impl");

			StringBuffer bufInsSql = new StringBuffer();
			// bufInsSql.append("SELECT B.BRANCH_ID,B.BRANCH_DESC  FROM SEC_USER_BRANCH_DTL A, COM_BRANCH_M B WHERE A.BRANCH_ID=B.BRANCH_ID AND USER_ID='"+userId+"'");
			bufInsSql.append("select A.AREA_CODE ,A.AREA_CODE_NAME,B.BRANCH_ID from com_areacode_m A  INNER JOIN com_areacode_branch_mapping B on" +
					" A.AREA_CODE=B.AREA_CODE WHERE B.BRANCH_ID= '"+branchId+"' ");
			
			
//			bufInsSql.append("select com_branch_m.BRANCH_ID ,com_branch_m.BRANCH_DESC , " +
//					" sec_user_branch_dtl.BRANCH_ACCESS from com_branch_m inner join" +
//					" sec_user_branch_dtl on com_branch_m.BRANCH_ID = sec_user_branch_dtl.BRANCH_ID WHERE USER_ID='"+userId+"'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN searchAreaCodeBranchEdit() search query1 ### "
					+ bufInsSql.toString());
			logger.info("searchAreaCodeBranchEdit " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("AreaCodeList----------->" + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					BranchMasterAreaCodeVo vo = new BranchMasterAreaCodeVo();
					vo.setAreaCode(CommonFunction.checkNull(data.get(0)).toString());
					vo.setAreaDesc(CommonFunction.checkNull(data.get(1)).toString());
					vo.setBranchId(CommonFunction.checkNull(data.get(2)).toString());
					
					areaCodeList.add(vo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return areaCodeList;
	}

	public ArrayList searchVerificationQuestionData(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String verificationSubType = "";
		String verificationType = "";
		ArrayList searchlist = new ArrayList();
		VerificationQuestionVo verificationVo = (VerificationQuestionVo) ob;
		ArrayList detailList = new ArrayList();
		try {

			logger.info("In searchVerificationQuestionData()..............inside ejb server file.......................Dao Impl");
			
			verificationSubType = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(verificationVo.getVerificationSubType())).trim());
			verificationType = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(verificationVo.getVerificationType())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT DISTINCT VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS,question_id ");
			bufInsSql.append(" FROM cr_deal_verification_question_m ");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_deal_verification_question_m ");

			if (!(verificationSubType.equals("")) && !(verificationType.equals(""))) {
				bufInsSql.append(" WHERE VERIFICATION_SUB_TYPE like '%" + verificationSubType + "%' AND VERIFICATION_TYPE like '%" + verificationType + "%' ");
				bufInsSqlTempCount.append(" WHERE VERIFICATION_SUB_TYPE like '%" + verificationSubType + "%' AND VERIFICATION_TYPE like '%" + verificationType + "%' ");
			}

			else if (!verificationSubType.equals("")) {
				bufInsSql.append(" WHERE VERIFICATION_SUB_TYPE like '%" + verificationSubType + "%' ");
				bufInsSqlTempCount.append(" WHERE VERIFICATION_SUB_TYPE like '%" + verificationSubType + "%' ");
			}
			else if (!verificationType.equals("")) {
				bufInsSql.append(" WHERE VERIFICATION_TYPE like '%" + verificationType + "%' ");
				bufInsSqlTempCount.append(" WHERE VERIFICATION_TYPE like '%" + verificationType + "%' ");
			}
						
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((verificationSubType.trim().equalsIgnoreCase("") && verificationType.trim().equalsIgnoreCase("")) || verificationVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+verificationVo.getCurrentPageLink());
			if(verificationVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (verificationVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY VERIFICATION_TYPE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
									
	//		}
			logger.info("IN searchVerificationMappingData() search query1 ### "+ bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		
			logger.info("searchVerificationMappingData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
			
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					VerificationQuestionVo deptMVO = new VerificationQuestionVo();
					logger.info("data.get(1)  : "+data.get(1));
					logger.info("data.get(2)  : "+data.get(2));
					
					//String verificationType =CommonFunction.checkNull(data.get(1)).toString().substring(0,CommonFunction.checkNull(data.get(1)).toString().indexOf(' '));
					//String verificationSubType =CommonFunction.checkNull(data.get(2)).toString().substring(0,CommonFunction.checkNull(data.get(2)).toString().indexOf(' '));
					
					deptMVO.setVerificationModifyLink("<a href=verificationQuestProcessingMaster.do?method=openEditFromSearchWithGridVerificationQuest&verificationType="
							+ URLEncoder.encode(CommonFunction.checkNull(data.get(0)),"UTF-8")+"&verificationSubType="+URLEncoder.encode(CommonFunction.checkNull(data.get(1)),"UTF-8")+"&questionId="+URLEncoder.encode(CommonFunction.checkNull(data.get(3)),"UTF-8")
							+ ">"
							+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					
									
					deptMVO.setVerificationType(CommonFunction.checkNull(data.get(0)));
					deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(1)));
					deptMVO.setQuestStatus(CommonFunction.checkNull(data.get(2)));
					deptMVO.setVerificationQuestId(CommonFunction.checkNull(data.get(3)));
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	//vinod
	public ArrayList getGridInEditVerificationQuest(Object ob)
	{

		ArrayList searchlist = new ArrayList();
		ArrayList detailList = new ArrayList();
		try {

			logger.info("## In getGridInEditVerificationQuest()..............inside ejb server file.......................Dao Impl");
			
			
			VerificationQuestionVo verificationVo = (VerificationQuestionVo) ob;
			
					
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("SELECT QUESTION_ID,VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,");
			bufInsSql.append("QUESTION,case when VERIFICATION_REQD='Y' then 'YES' else 'NO' end as VERIFICATION_REQD,case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS,QUESTION_SEQ_NO,ENTITY_TYPE,ENTITY_SUB_TYPE, ");
			bufInsSql.append(" (select DESCRIPTION from generic_master where GENERIC_KEY='ADDRESS_TYPE' and rec_status ='A' and VALUE=address_type )address_type");
	
			bufInsSql.append(" FROM cr_deal_verification_question_m ");
			
			bufInsSql.append(" WHERE VERIFICATION_TYPE = '" + verificationVo.getVerificationType() + "' AND VERIFICATION_SUB_TYPE = '" + verificationVo.getVerificationSubType() + "' ");
				
			logger.info("## In getGridInEditVerificationQuest() : "+ bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		
			logger.info("## In getGridInEditVerificationQuest() :searchlist.size() " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				
						
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					VerificationQuestionVo deptMVO = new VerificationQuestionVo();
					deptMVO.setVerificationModifyLink("<a href=verificationQuestProcessingMaster.do?method=openEditVerificationQuest&verificationQuestId="
									+ CommonFunction.checkNull(data.get(0))
									+ ">"
									+ CommonFunction.checkNull(data.get(0)) + "</a>");
			
					deptMVO.setVerificationType(CommonFunction.checkNull(data.get(1)));
					deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(2)));
					deptMVO.setVerificationQuest(CommonFunction.checkNull(data.get(3)));
					deptMVO.setVerificationRequired(CommonFunction.checkNull(data.get(4)));
					deptMVO.setQuestStatus(CommonFunction.checkNull(data.get(5)));
					deptMVO.setqSequenceNo(CommonFunction.checkNull(data.get(6)));
					 if (CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("PRAPPL"))
						 deptMVO.setEntityType("APPLICANT");
				     else if (CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("COAPPL"))
				    	  deptMVO.setEntityType("COAPPLICANT");
				     else
				    	 deptMVO.setEntityType(CommonFunction.checkNull(data.get(7)));
					deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(8)));
					deptMVO.setAddrType(CommonFunction.checkNull(data.get(9)));
					detailList.add(deptMVO);
				}

			}
			
			logger.info("## In getGridInEditVerificationQuest() : end of method");
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	
		
	}
	
	public boolean insertVerificationQuestMaster(Object ob) {
		VerificationQuestionVo vo = (VerificationQuestionVo) ob;
		boolean status = false;
		
		logger.info("In insertVerificationQuestMaster.........inside ejb server file...........Dao Impl"
						+ vo.getQuestStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

	
		try {

				if (vo.getQuestStatus() != null && vo.getQuestStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert insertVerificationQuestMaster");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into cr_deal_verification_question_m(VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,QUESTION,VERIFICATION_REQD,QUESTION_SEQ_NO,ENTITY_TYPE,ENTITY_SUB_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,address_type,PRODUCT_TYPE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //VERIFICATION_TYPE
				bufInsSql.append(" ?,"); //VERIFICATION_SUB_TYPE
				bufInsSql.append(" ?,"); //QUESTION
				bufInsSql.append(" ?,"); //VERIFICATION_REQD
				bufInsSql.append(" ?,"); //QUESTION_SEQ_NO		
				bufInsSql.append(" ?,"); //ENTITY_TYPE
				bufInsSql.append(" ?,"); //ENTITY_SUB_TYPE
				bufInsSql.append(" ?,"); //REC_STATUS
				bufInsSql.append(" ?,"); //MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,"); //AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ? ,"); //Address_Type
				bufInsSql.append(" ? )"); //product_Type
				if (CommonFunction.checkNull(vo.getVerificationType())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getVerificationType()
							.toUpperCase().trim());

				
					if (CommonFunction.checkNull(vo.getVerificationSubType())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getVerificationSubType()
								.toUpperCase().trim());
				
				
				
				if (CommonFunction.checkNull(vo.getVerificationQuest())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getVerificationQuest()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getVerificationRequired())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getVerificationRequired()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getqSequenceNo())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getqSequenceNo()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getEntityType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else if (CommonFunction.checkNull(vo.getEntityType()).equalsIgnoreCase("APPLICANT"))
					     insertPrepStmtObject.addString("PRAPPL");
				else if (CommonFunction.checkNull(vo.getEntityType()).equalsIgnoreCase("COAPPLICANT"))
				        insertPrepStmtObject.addString("COAPPL");
				else
					    insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getEntityType()));
				
				
				if (CommonFunction.checkNull(vo.getEntitySubType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEntitySubType().toUpperCase().trim());
				
				
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

			
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				if (CommonFunction.checkNull(vo.getAddrType())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAddrType()
							.toUpperCase().trim());
							
							if (CommonFunction.checkNull(vo.getProductType())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getProductType()
							.toUpperCase().trim());

				logger.info("IN insertVerificationMappingMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertVerificationMappingMaster......................"
						+ status);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public boolean updateVerificationQuestMaster(Object ob) {
		VerificationQuestionVo vo = (VerificationQuestionVo) ob;
		
		logger.info("getVerificationQuestId():-" + vo.getVerificationQuestId());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		
		boolean status = false;
		String stat = "";

		try {
			
			
			logger.info("## In updateVerificationQuestMaster : varification sub type"+vo.getVerificationSubType());
			logger.info("## In updateVerificationQuestMaster : varification type"+vo.getVerificationType());
			if (vo.getQuestStatus() != null 	&& vo.getQuestStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateVerificationMappingMaster..........inside ejb server file............Dao Impl ");
			bufInsSql.append(" UPDATE cr_deal_verification_question_m set QUESTION=?,VERIFICATION_REQD=?,");
			bufInsSql.append(" REC_STATUS=?,QUESTION_SEQ_NO=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(",address_type=? ,product_type=? where QUESTION_ID=?");

			if (CommonFunction.checkNull(vo.getVerificationQuest())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getVerificationQuest()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getVerificationRequired())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getVerificationRequired()
						.toUpperCase().trim());
			

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			
			if (CommonFunction.checkNull(vo.getqSequenceNo())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getqSequenceNo()
						.toUpperCase().trim());
			
		
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			if (CommonFunction.checkNull(vo.getAddrType())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAddrType());
				
				if (CommonFunction.checkNull(vo.getProductType())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getProductType());
			
			
			if (CommonFunction.checkNull(vo.getVerificationQuestId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getVerificationQuestId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In update query: " + bufInsSql.toString());
			logger.info("## In updateVerificationQuestMaster +++ : varification sub type"+vo.getVerificationSubType());
			
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public ArrayList getVerificationQuestData(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String countryId = "";
		String countryDesc = "";
		ArrayList searchlist = new ArrayList();
		String verificationQuestId = (String) ob;
		ArrayList detailList = new ArrayList();
		try {

			logger.info("In getVerificationQuestData()..............inside ejb server file.......................Dao Impl");
			
			
		String productID="";
			StringBuffer bufInsSql = new StringBuffer();
			 String branchMappedToUserQuery="select stuff((select '|' + cast (product_id as varchar(10)) from cr_verification_question_product_mapping_dtl where question_id='"+verificationQuestId+"' FOR XML PATH ('')), 1, 1, '')";
			   productID=ConnectionDAOforEJB.singleReturn(branchMappedToUserQuery);
					
			bufInsSql.append("SELECT distinct a.QUESTION_ID,a.VERIFICATION_TYPE,a.VERIFICATION_SUB_TYPE,a.QUESTION,a.VERIFICATION_REQD,");
			bufInsSql.append("case when a.REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS,a.QUESTION_SEQ_NO,a.ENTITY_TYPE,a.ENTITY_SUB_TYPE ");
		    bufInsSql.append(",a.address_type,a.product_type,replace('"+productID+"',',','')");
		    bufInsSql.append(" FROM cr_deal_verification_question_m a left join cr_verification_question_product_mapping_dtl b on b.question_id=a.question_id where a.QUESTION_ID='"+verificationQuestId+"' ORDER BY QUESTION_SEQ_NO");
			

		
			logger.info("getVerificationQuestData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
			
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					VerificationQuestionVo deptMVO = new VerificationQuestionVo();
					
					deptMVO.setVerificationQuestId(CommonFunction.checkNull(data.get(0)));
					deptMVO.setVerificationType(CommonFunction.checkNull(data.get(1)));
					
					deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(2)));
					
				
					deptMVO.setVerificationQuest(CommonFunction.checkNull(data.get(3)));
					deptMVO.setVerificationRequired(CommonFunction.checkNull(data.get(4)));
					deptMVO.setQuestStatus(CommonFunction.checkNull(data.get(5)));
					deptMVO.setqSequenceNo(CommonFunction.checkNull(data.get(6)));
					 if (CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("PRAPPL"))
						 deptMVO.setEntityType("APPLICANT");
				     else if (CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("COAPPL"))
				    	  deptMVO.setEntityType("COAPPLICANT");
				     else
				    	 deptMVO.setEntityType(CommonFunction.checkNull(data.get(7)));
					deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(8)));
					deptMVO.setAddrType(CommonFunction.checkNull(data.get(9)));
					deptMVO.setProductType(CommonFunction.checkNull(data.get(10)));
					deptMVO.setLbxproduct(CommonFunction.checkNull(data.get(11)));
					detailList.add(deptMVO);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	@Override
	public int countVerificationCombination(Object ob) {
		
		int status=0;
		VerificationQuestionVo vo=(VerificationQuestionVo)ob;
		
		StringBuilder checkQuery=new StringBuilder("select count(1) from cr_deal_verification_question_m where VERIFICATION_TYPE='"+vo.getVerificationType()+"' and VERIFICATION_SUB_TYPE='"+vo.getVerificationSubType()+"' and QUESTION_SEQ_NO='"+vo.getqSequenceNo()+"'");
		if(!CommonFunction.checkNull(vo.getVerificationQuestId()).equalsIgnoreCase(""))
		{
			checkQuery.append("AND QUESTION_ID<>'"+CommonFunction.checkNull(vo.getVerificationQuestId())+"'");
		}
		logger.info("checkQuery: "+checkQuery.toString());
		String count =ConnectionDAOforEJB.singleReturn(checkQuery.toString());
		if(!CommonFunction.checkNull(count).equalsIgnoreCase(""))
		{
			status=Integer.parseInt(count);
		}

		return status;
	}

	@Override
	public String insertSubDealerMaster(Object ob) {
		SubDealerMasterVo vo = (SubDealerMasterVo) ob;
		boolean status = false;
        String result=null;
        
		logger.info("In insertSubDealerMaster.........inside ejb server file...........Dao Impl getSubDealerStatus"+ vo.getSubDealerStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String sqlquery1="select sub_dealer_code from com_sub_dealer_m where sub_dealer_code='"+vo.getSubDealerCode()+"' ";
		logger.info("In insertSubDealerMaster...."+sqlquery1);
		boolean subDealerExist = ConnectionDAOforEJB.checkStatus(sqlquery1);
		logger.info("In insertSubDealerMaster...subDealerExist..."+subDealerExist);
		if(subDealerExist==true){
			result="dataExist";
		}
		else{
		try {

				if (vo.getSubDealerStatus() != null && vo.getSubDealerStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert insertSubDealerMaster");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into com_sub_dealer_m (SUB_DEALER_CODE ,SUB_DEALER_DESC,SUB_DEALER_BANK_AC_No,DEALER_ID,DEALER_DESC,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //SUB_DEALER_CODE
				bufInsSql.append(" ?,"); //SUB_DEALER_DESC				
				bufInsSql.append(" ?,"); //SUB_DEALER_BANK_AC_No
				bufInsSql.append(" ?,"); //DEALER_ID	
				bufInsSql.append(" ?,"); //DEALER_DESC	
				bufInsSql.append(" ?,"); //REC_STATUS				
				bufInsSql.append(" ?,"); //MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,"); //AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				

				if (CommonFunction.checkNull(vo.getSubDealerCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSubDealerCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getSubDealerDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSubDealerDes().toUpperCase().trim());
				
					if (CommonFunction.checkNull(vo.getSubDealerBankAC())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getSubDealerBankAC()
								.toUpperCase().trim());
				
					if (CommonFunction.checkNull(vo.getDealerID())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getDealerID()
								.toUpperCase().trim());
					if (CommonFunction.checkNull(vo.getDealerDes())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getDealerDes()
								.toUpperCase().trim());
					
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

			
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertSubDealerMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);				
				logger.info("In insertSubDealerMaster......................"
						+ status);
				if(status)
					result="datasaved";
					else
						result="datanotsaved";

		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return result;

		}

	public ArrayList searchSubDealerData(Object ob) {
		String subDealerSearchCode = "";
		String subDealerSearchDesc = "";
		String dealerSearchCode = "";
		String dealerSearchDes = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		SubDealerMasterVo subDealerMasterVo = (SubDealerMasterVo) ob;
		ArrayList<SubDealerMasterVo> detailList = new ArrayList<SubDealerMasterVo>();
		try {

			logger.info("In searchDealerData.............inside ejb server file...........Dao Impl");
			subDealerSearchCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(subDealerMasterVo.getSubDealerSearchCode())).trim());
			subDealerSearchDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(subDealerMasterVo.getSubDealerSearchDes())).trim());
			//dealerSearchCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(subDealerMasterVo.getDealerSearchID())).trim());
			dealerSearchDes = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(subDealerMasterVo.getDealerSearchDesc())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("select SUB_DEALER_CODE,SUB_DEALER_DESC,");			
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status,SUB_DEALER_ID,DEALER_ID,DEALER_DESC");
			bufInsSql.append(" FROM com_sub_dealer_m B");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM com_sub_dealer_m B ");
			
			if (!subDealerSearchCode.equals("") && !subDealerSearchDesc.equals("")&& !dealerSearchDes.equals("")) {
				bufInsSql.append(" WHERE SUB_DEALER_CODE like '%" + subDealerSearchCode + "%' AND SUB_DEALER_DESC like '%" + subDealerSearchDesc + "%' AND DEALER_DESC like '%" + dealerSearchDes + "%'");
				bufInsSqlTempCount.append(" WHERE SUB_DEALER_CODE like '%" + subDealerSearchCode + "%' AND SUB_DEALER_DESC like '%" + subDealerSearchDesc + "%' AND DEALER_DESC like '%" + dealerSearchDes + "%'");
			} 
			else if (!subDealerSearchCode.equals("") && !subDealerSearchDesc.equals("")) {
				bufInsSql.append(" WHERE SUB_DEALER_CODE like '%" + subDealerSearchCode + "%' AND SUB_DEALER_DESC like '%" + subDealerSearchDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE SUB_DEALER_CODE like '%" + subDealerSearchCode + "%' AND SUB_DEALER_DESC like '%" + subDealerSearchDesc + "%' ");
			}
			else if (!subDealerSearchCode.equals("") && !dealerSearchDes.equals("")) {
				bufInsSql.append(" WHERE SUB_DEALER_CODE like '%" + subDealerSearchCode + "%'  AND DEALER_DESC like '%" + dealerSearchDes + "%'");
				bufInsSqlTempCount.append(" WHERE SUB_DEALER_CODE like '%" + subDealerSearchCode + "%'  AND DEALER_DESC like '%" + dealerSearchDes + "%'");
			}
			else if (!subDealerSearchDesc.equals("")&& !dealerSearchDes.equals("")) {
				bufInsSql.append(" WHERE SUB_DEALER_DESC like '%" + subDealerSearchDesc + "%' AND DEALER_DESC like '%" + dealerSearchDes + "%'");
				bufInsSqlTempCount.append(" WHERE SUB_DEALER_DESC like '%" + subDealerSearchDesc + "%' AND DEALER_DESC like '%" + dealerSearchDes + "%'");
			}
			else if (!subDealerSearchCode.equals("")) {
				bufInsSql.append(" WHERE SUB_DEALER_CODE like '%" + subDealerSearchCode + "%' ");
				bufInsSqlTempCount.append(" WHERE SUB_DEALER_CODE like '%" + subDealerSearchCode + "%' ");
			} 
			else if (!subDealerSearchDesc.equals("")) {
				bufInsSql.append(" WHERE SUB_DEALER_DESC like '%" + subDealerSearchDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE SUB_DEALER_DESC like '%" + subDealerSearchDesc + "%' ");
			}
//			else if (!dealerSearchCode.equals("")) {
//				bufInsSql.append(" WHERE DEALER_ID like '%" + dealerSearchCode + "%' ");
//				bufInsSqlTempCount.append(" WHERE DEALER_ID like '%" + dealerSearchCode + "%' ");
//			} 
			else if (!dealerSearchDes.equals("")) {
				bufInsSql.append(" WHERE DEALER_DESC like '%" + dealerSearchDes + "%' ");
				bufInsSqlTempCount.append(" WHERE DEALER_DESC like '%" + dealerSearchDes + "%' ");
			}
						
			logger.info("search Query...." + bufInsSql);

			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((subDealerSearchCode.trim()==null && subDealerSearchDesc.trim()==null) || (subDealerSearchCode.trim().equalsIgnoreCase("") && subDealerSearchDesc.trim().equalsIgnoreCase("")) || subDealerMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+subDealerMasterVo.getCurrentPageLink());
			if(subDealerMasterVo.getCurrentPageLink()> 1)
			{
				startRecordIndex = (subDealerMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY SUB_DEALER_CODE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				
//			}
			logger.info("query : "+bufInsSql);
		
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchSubDealerData() search query1 ### " + bufInsSql.toString());
			logger.info("In searchSubDealerData.....................................Dao Impl");
			logger.info("searchSubDealerData "+searchlist);
			logger.info("searchlist.size()  "+searchlist.size());
			//logger.info("searchSubDealerData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					SubDealerMasterVo searchDealerMasterVO = new SubDealerMasterVo();

					searchDealerMasterVO.setSubDealerIdModify("<a href=subDealerMasterSearch.do?method=openEditSubDealer&subDealerCode="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+"&subDealerID="+ CommonFunction.checkNull(data.get(3)).toString() +  ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					searchDealerMasterVO.setSubDealerCode(CommonFunction.checkNull(data.get(0)).toString());
					searchDealerMasterVO.setSubDealerDes(CommonFunction.checkNull(data.get(1)).toString());		
					searchDealerMasterVO.setSubDealerStatus(CommonFunction.checkNull(data.get(2)).toString());
					searchDealerMasterVO.setDealerID(CommonFunction.checkNull(data.get(4)).toString());
					searchDealerMasterVO.setDealerDes(CommonFunction.checkNull(data.get(5)).toString());
					searchDealerMasterVO.setTotalRecordSize(count);
					detailList.add(searchDealerMasterVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("detailList size : "+detailList);
		return detailList;
	}

	public String updateSubDealerData(Object ob) {
		
		SubDealerMasterVo vo = (SubDealerMasterVo) ob;
		String subDealerCode = (String) vo.getSubDealerCode();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		logger.info("subDealerCode():-" +subDealerCode);
		logger.info("DealerID():-" +vo.getDealerID());
		ArrayList updatelist = new ArrayList();	
		 String result="";
		 boolean status = false;
		String stat = "";
		// and DEALER_ID='"+vo.getDealerID()+"'
		String sqlquery1="select sub_dealer_code from com_sub_dealer_m where sub_dealer_code='"+vo.getSubDealerCode()+"'and SUB_DEALER_ID<>'"+vo.getSubDealerID()+"' ";
		logger.info("In updateSubDealerData...."+sqlquery1);
		boolean subDealerExist = ConnectionDAOforEJB.checkStatus(sqlquery1);
		logger.info("In updateSubDealerData...subDealerExist..."+subDealerExist);
		if(subDealerExist==true){
			result="dataExist";
		}
		else{
		try {
			if (!subDealerExist) {
			if (vo.getSubDealerStatus() != null
					&& vo.getSubDealerStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			StringBuffer bufInsSql = new StringBuffer();				
		
				insertPrepStmtObject=null;
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql=null;
				bufInsSql = new StringBuffer();
				//----------------------------------
				bufInsSql.append("UPDATE com_sub_dealer_m set SUB_DEALER_CODE=?,SUB_DEALER_DESC=?, SUB_DEALER_BANK_AC_No=?,");
				bufInsSql.append(" DEALER_ID=?,DEALER_DESC=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				bufInsSql.append(" where SUB_DEALER_ID=?");

				if (CommonFunction.checkNull(vo.getSubDealerCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSubDealerCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getSubDealerDes())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSubDealerDes().toUpperCase().trim());
				
					if (CommonFunction.checkNull(vo.getSubDealerBankAC())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getSubDealerBankAC()
								.toUpperCase().trim());
				
					if (CommonFunction.checkNull(vo.getDealerID())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getDealerID()
								.toUpperCase().trim());
					if (CommonFunction.checkNull(vo.getDealerDes())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getDealerDes()
								.toUpperCase().trim());
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

			
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());				
				if (CommonFunction.checkNull(vo.getSubDealerID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSubDealerID());

				//----------------------------------

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertUserMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				updatelist.add(insertPrepStmtObject);
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			

			if(status){
				result="saved";
			}else{
				result="notsaved";
			}
			
        }						
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return result;
	}
	public ArrayList searchSubDealerUserEdit(String subDealerCode) {

		ArrayList searchlist = new ArrayList();
		SubDealerMasterVo vo = new SubDealerMasterVo();
		ArrayList<SubDealerMasterVo> levelDescList = new ArrayList<SubDealerMasterVo>();

		try {
			logger.info("In searchSubDealerUserEdit().....................................Dao Impl");

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("select SUB_DEALER_CODE,SUB_DEALER_DESC,SUB_DEALER_BANK_AC_No,REC_STATUS,DEALER_DESC,DEALER_ID,SUB_DEALER_ID from  com_sub_dealer_m where " +
					"SUB_DEALER_ID='"+StringEscapeUtils.escapeSql(subDealerCode)+"'");
			

			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN searchSubDealerUserEdit() search query1 ### "+ bufInsSql.toString());
			
			
			logger.info("searchSubDealerUserEdit " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchSubDealerUserEdit " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					SubDealerMasterVo subDealerMasterVo = new SubDealerMasterVo();
					subDealerMasterVo.setSubDealerCode(CommonFunction.checkNull(data.get(0)).toString());
					logger.info("searchSubDealerUserEdit "+CommonFunction.checkNull(data.get(0)).toString());
					subDealerMasterVo.setSubDealerDes(CommonFunction.checkNull(data.get(1)).toString());
					subDealerMasterVo.setSubDealerBankAC(CommonFunction.checkNull(data.get(2)).toString());
					subDealerMasterVo.setSubDealerStatus(CommonFunction.checkNull(data.get(3)).toString());
					subDealerMasterVo.setDealerDes(CommonFunction.checkNull(data.get(4)).toString());
					subDealerMasterVo.setDealerID(CommonFunction.checkNull(data.get(5)).toString());
					subDealerMasterVo.setSubDealerID(CommonFunction.checkNull(data.get(6)).toString());
					levelDescList.add(subDealerMasterVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return levelDescList;
	}
	
	public String saveAutoAllocationDefDetail(Object ob) 
	{
		logger.info("In saveAutoAllocationDefDetail() of MasterDAOImpl");
		String result="";
		AutoAllocationDefinitionVo vo = (AutoAllocationDefinitionVo) ob;
		
			boolean status =false;
				String query = "SELECT NPA_STAGE FROM com_auto_allocation_def_m WHERE NPA_STAGE='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxNPAStageId()).trim())+ "' AND REPAY_TYPE='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRepayType()).trim())+ "'";
			    logger.info("In saveAutoAllocationDefDetail() of MasterDAOImpl Query for check Existing NPA_STAGE AND REPAY TYPE : "+ query);
			    try
			    {
			    	status = ConnectionDAOforEJB.checkStatus(query);
			    }
			    catch (Exception e) 
			    {
			    	e.printStackTrace();
			    	result="ERROR";
				}
			    if(status)
			       	result="EXIST";
				else
			    {
					ArrayList qryList=new ArrayList();
					try 
					{
						
						 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
						 StringBuffer bufInsSql =	new StringBuffer();
//						 int cnt =0;
//						 if(CommonFunction.checkNull(vo.getRepayType()).trim().equalsIgnoreCase("I"))
//						 {
//							 cnt=3;
//						 }
//						 else
//						 {
//							 cnt=2;
//						 }
						 for(int k=0;k<3;k++)  
						 {
							 bufInsSql = new StringBuffer();
							 insertPrepStmtObject = new PrepStmtObject();
						  
							 insertPrepStmtObject = new PrepStmtObject();
							 bufInsSql.append("insert into com_auto_allocation_def_m (NPA_STAGE,ALLOCATION_TYPE,REPAY_TYPE,COMPONENT,PRIORITY,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE) values(?,?,?,?,?,?,?,");
							 bufInsSql.append(dbo);
							 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
							 bufInsSql.append("?,");
							 bufInsSql.append(dbo);
							 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
															 
							 if(CommonFunction.checkNull(vo.getLbxNPAStageId()).trim().equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString((vo.getLbxNPAStageId()).trim());   // npa stage
							 
							 if(CommonFunction.checkNull(vo.getType()).trim().equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString((vo.getType()).trim());   //allocation type
							 
							 if(CommonFunction.checkNull(vo.getRepayType()).trim().equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString((vo.getRepayType()).trim());   //repay type
							
							 if(CommonFunction.checkNull(vo.getRepayType()).trim().equalsIgnoreCase("I"))
							 {
								if(k==0)
								{
									insertPrepStmtObject.addString("I"); //component
									
									if(CommonFunction.checkNull(vo.getInstallmentCharges()).trim().equalsIgnoreCase(""))
										 insertPrepStmtObject.addNull();
									 else
										 insertPrepStmtObject.addString((vo.getInstallmentCharges()).trim()); //PrincipalValue
								}
								else if(k==1)
								{
									insertPrepStmtObject.addString("P"); //component
									
									if(CommonFunction.checkNull(vo.getPreEmiCharges()).trim().equalsIgnoreCase(""))
										 insertPrepStmtObject.addNull();
									 else
										 insertPrepStmtObject.addString((vo.getPreEmiCharges()).trim()); // InterestValue
								}
								else if(k==2)
								{
									insertPrepStmtObject.addString("O"); //component
									
									if(CommonFunction.checkNull(vo.getOtherCharges()).trim().equalsIgnoreCase(""))
										 insertPrepStmtObject.addNull();
									 else
										 insertPrepStmtObject.addString((vo.getOtherCharges()).trim()); //ChargeValue
								}
						 	}
							 else  if(CommonFunction.checkNull(vo.getRepayType()).trim().equalsIgnoreCase("N"))
							 {
								 if(k==0)
									{
										insertPrepStmtObject.addString("Pl"); //component
										
										if(CommonFunction.checkNull(vo.getPrincipalCharges()).trim().equalsIgnoreCase(""))
											 insertPrepStmtObject.addNull();
										 else
											 insertPrepStmtObject.addString((vo.getPrincipalCharges()).trim()); //PrincipalValue
									}
									else if(k==1)
									{
										insertPrepStmtObject.addString("In"); //component
										
										if(CommonFunction.checkNull(vo.getInterestCharges()).trim().equalsIgnoreCase(""))
											 insertPrepStmtObject.addNull();
										 else
											 insertPrepStmtObject.addString((vo.getInterestCharges()).trim()); // InterestValue
									}
									else if(k==2)
									{
										insertPrepStmtObject.addString("ONon"); //component
										
										if(CommonFunction.checkNull(vo.getOtherChargesNon()).trim().equalsIgnoreCase(""))
											 insertPrepStmtObject.addNull();
										 else
											 insertPrepStmtObject.addString((vo.getOtherChargesNon()).trim()); //ChargeValue
									}
							 }
							if (vo.getAllocationStatus() != null && vo.getAllocationStatus().equalsIgnoreCase("on")) 
							{
								insertPrepStmtObject.addString("A");// REC_STATUS   
							} 
							else
							{
								insertPrepStmtObject.addString("X");// REC_STATUS   
							}
							
					    	if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					    		insertPrepStmtObject.addNull();
					    	else
					    		insertPrepStmtObject.addString((vo.getMakerId()).trim());
					    	if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					    		insertPrepStmtObject.addNull();
					    	else
					    		insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
					    	
					    	if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					    		insertPrepStmtObject.addNull();
					    	else
					    		insertPrepStmtObject.addString((vo.getMakerId()).trim());
					    	if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					    		insertPrepStmtObject.addNull();
					    	else
					    		insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
						  
						 	insertPrepStmtObject.setSql(bufInsSql.toString());
							logger.info("IN saveAutoAllocationDefDetail() insert query1 ### here --- "+insertPrepStmtObject.printQuery());
							qryList.add(insertPrepStmtObject);
						}
												
						status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
						if(status)
						{
							result="SAVE";
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						qryList = null;
					}
						 	
			    }
		
		logger.info("In insertNPAStageMaster() of MasterDAOImpl return result String  :  "+result);
		return result;
	}
	
	public ArrayList<AutoAllocationDefinitionVo> getAutoAllocationDefData(Object ob)
	{
		ArrayList list = new ArrayList();
		AutoAllocationDefinitionVo vo1 = (AutoAllocationDefinitionVo) ob;
		ArrayList<AutoAllocationDefinitionVo> detailList = new ArrayList<AutoAllocationDefinitionVo>();

		try {
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In getAutoAllocationDefData.......111111111111111..............Dao Impl");
			bufInsSql.append(" SELECT NPA_STAGE,ALLOCATION_TYPE,PRIORITY,REC_STATUS,MAKER_ID,MAKER_DATE,ID,COMPONENT,REPAY_TYPE FROM com_auto_allocation_def_m WHERE NPA_STAGE='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo1.getNpaStage()).trim())+ "' AND REPAY_TYPE='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo1.getRepayType()).trim())+ "'");
			logger.info("query ::::::::::::::: "+bufInsSql.toString());
			list = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("list.size " + list.size());
			AutoAllocationDefinitionVo vo = new AutoAllocationDefinitionVo();
			for (int i = 0; i < list.size(); i++) 
			{
				logger.info("getAutoAllocationDefData List " + list.get(i).toString());

				ArrayList data = (ArrayList) list.get(i);
				
				if (data.size() > 0)
				{
					vo.setNpaStage(CommonFunction.checkNull(data.get(0)).toString());
					vo.setLbxNPAStageId(CommonFunction.checkNull(data.get(0)).toString());
					vo.setType(CommonFunction.checkNull(data.get(1)).toString());
					vo.setRepayType(CommonFunction.checkNull(data.get(8)).toString());
					if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("I"))
					{
						if(i==0)
						{
							vo.setInstallmentCharges(CommonFunction.checkNull(data.get(2)).toString());
							vo.setInstallmentChargesID(CommonFunction.checkNull(data.get(6)).toString());
						}
						else
							if(i==1)
							{
								vo.setPreEmiCharges(CommonFunction.checkNull(data.get(2)).toString());
								vo.setPreEmiChargesID(CommonFunction.checkNull(data.get(6)).toString());
							}
							else 
								if(i==2)
								{
									vo.setOtherCharges(CommonFunction.checkNull(data.get(2)).toString());
									vo.setOtherChargesID(CommonFunction.checkNull(data.get(6)).toString());
								}
					}
					else if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("N"))
					{
						if(i==0)
						{
							vo.setPrincipalCharges(CommonFunction.checkNull(data.get(2)).toString());
							vo.setPrincipalChargesID(CommonFunction.checkNull(data.get(6)).toString());
						}
						else
							if(i==1)
							{
								vo.setInterestCharges(CommonFunction.checkNull(data.get(2)).toString());
								vo.setInterestChargesID(CommonFunction.checkNull(data.get(6)).toString());
							}
							else 
								if(i==2)
								{
									vo.setOtherChargesNon(CommonFunction.checkNull(data.get(2)).toString());
									vo.setOtherChargesNonID(CommonFunction.checkNull(data.get(6)).toString());
								}
					}
					if ((CommonFunction.checkNull(data.get(3)).equalsIgnoreCase("A")))
					{
						logger.info("allocation status dao impl:  "+CommonFunction.checkNull(data.get(3)));
						vo.setAllocationStatus("Active");
					} 
					
					vo.setType(CommonFunction.checkNull(data.get(1)).toString());
					if(i==0)
					detailList.add(vo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}
	
	
	public ArrayList<AutoAllocationDefinitionVo> searchAutoAllocationDefData(Object ob) 
	{
		String npaSearchStage = "";
		String type = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		AutoAllocationDefinitionVo vo = (AutoAllocationDefinitionVo) ob;
		ArrayList<AutoAllocationDefinitionVo> detailList = new ArrayList<AutoAllocationDefinitionVo>();
		try {

			logger.info("In searchAutoAllocationDefData().....................................Dao Impl");
			logger.info("vo.getLbxNPAStageId() "+vo.getLbxNPAStageId());
			logger.info("vo.getType() "+vo.getType());
			npaSearchStage = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxNPAStageId())).trim());
			type = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getType())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT NPA_STAGE,ALLOCATION_TYPE,COMPONENT,PRIORITY,REC_STATUS,MAKER_ID,REPAY_TYPE");
			bufInsSql.append(" FROM com_auto_allocation_def_m ");

			bufInsSqlTempCount.append("SELECT COUNT(1) from (SELECT NPA_STAGE,ALLOCATION_TYPE,COMPONENT,PRIORITY,REC_STATUS,MAKER_ID,REPAY_TYPE FROM com_auto_allocation_def_m ");
			
			if ((!(npaSearchStage.equalsIgnoreCase(""))) && (!(type.equalsIgnoreCase("")))) {
				bufInsSql.append(" WHERE NPA_STAGE ='" + npaSearchStage + "' AND ALLOCATION_TYPE='" + type + "' ");
				bufInsSqlTempCount.append(" WHERE NPA_STAGE ='" + npaSearchStage + "' AND ALLOCATION_TYPE='" + type + "' ");
			} 
			else if (!type.equals("")) {
				bufInsSql.append(" WHERE ALLOCATION_TYPE='" + type + "' ");
				bufInsSqlTempCount.append(" WHERE ALLOCATION_TYPE='" + type + "' ");
			}

			else if (!npaSearchStage.equals("")) {
				bufInsSql.append(" WHERE NPA_STAGE = '" + npaSearchStage + "' ");
				bufInsSqlTempCount.append(" WHERE NPA_STAGE = '" + npaSearchStage + "' ");
			}
						
			bufInsSql.append(" group by NPA_STAGE,ALLOCATION_TYPE,COMPONENT,PRIORITY,REC_STATUS,MAKER_ID,REPAY_TYPE ");
			bufInsSqlTempCount.append(" group by NPA_STAGE,ALLOCATION_TYPE,COMPONENT,PRIORITY,REC_STATUS,MAKER_ID,REPAY_TYPE ) temp");
									
			logger.info("search Query...." + bufInsSql);

			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			String tempCount = ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString());
			if(tempCount!=null)
			{
	            count =Integer.parseInt(tempCount);
			}
			else
			{
				count=0;
			}
//			if((type.trim()==null && npaSearchStage.trim()==null) || (type.trim().equalsIgnoreCase("") && npaSearchStage.trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY NPA_STAGE,REPAY_TYPE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
									
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchAutoAllocationDefData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchAutoAllocationDefData.....................................Dao Impl");
			logger.info("searchAutoAllocationDefData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchAutoAllocationDefData "+ searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					AutoAllocationDefinitionVo vo1 = new AutoAllocationDefinitionVo();

					vo1.setAutoAllocationDefModify("<a href=autoAllocationDefinitionAction.do?method=openEditAutoAllocationDef&npaStage="+ CommonFunction.checkNull(data.get(0)).toString()+ "&repayType="+ CommonFunction.checkNull(data.get(6)).toString()+">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					vo1.setNpaStage(CommonFunction.checkNull(data.get(0)).toString());
					
					if(CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("D"))
					{
						vo1.setType("Date First");
					}
					else
					{
						vo1.setType("Charge First");
					}
				
					if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("A"))
					{
						vo1.setAllocationStatus("Active");
					}
					else
					{
						vo1.setAllocationStatus("InActive");
					}
					vo1.setMakerId(CommonFunction.checkNull(data.get(5)).toString());
					if(CommonFunction.checkNull(data.get(6)).equalsIgnoreCase("I"))
					{
						vo1.setRepayType("Installment");
					}
					else
					{
						vo1.setRepayType("Non-Installment");
					}
					
					vo1.setTotalRecordSize(count);
					detailList.add(vo1);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	
	public String updateAutoAllocationDefDetail(Object ob) 
	{
		String result="";
		logger.info("In updateAutoAllocationDefDetail() of MasterDAOImpl");
		AutoAllocationDefinitionVo vo = (AutoAllocationDefinitionVo) ob;
		ArrayList qryList=new ArrayList();
		try 
		{
			
			 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			 StringBuffer bufInsSql =	new StringBuffer();
//			 int cnt=0;
//			 if(CommonFunction.checkNull(vo.getRepayTypeHid()).trim().equalsIgnoreCase("I"))
//			 {
//				 cnt=3;
//			 }
//			 else
//			 {
//				 cnt=2;
//			 }
			 
			 for(int i=0;i<3;i++)  
			 {
				
				 bufInsSql = new StringBuffer();
				 insertPrepStmtObject = new PrepStmtObject();
				 bufInsSql.append("UPDATE com_auto_allocation_def_m SET ALLOCATION_TYPE=?,COMPONENT=?,PRIORITY=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				 bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				 bufInsSql.append(" where NPA_STAGE=? AND ID=?");
				 
				 if(CommonFunction.checkNull(vo.getType()).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString((vo.getType()).trim());   // type
				 if(CommonFunction.checkNull(vo.getRepayTypeHid()).trim().equalsIgnoreCase("I"))
				 {
					if(i==0)
					{
						insertPrepStmtObject.addString("I"); //component
						
						if(CommonFunction.checkNull(vo.getInstallmentCharges()).trim().equalsIgnoreCase(""))
							 insertPrepStmtObject.addNull();
						 else
							 insertPrepStmtObject.addString((vo.getInstallmentCharges()).trim()); //PrincipalValue
					}
					else if(i==1)
					{
						insertPrepStmtObject.addString("P"); //component
						
						if(CommonFunction.checkNull(vo.getPreEmiCharges()).trim().equalsIgnoreCase(""))
							 insertPrepStmtObject.addNull();
						 else
							 insertPrepStmtObject.addString((vo.getPreEmiCharges()).trim()); // InterestValue
					}
					else if(i==2)
					{
						insertPrepStmtObject.addString("O"); //component
						
						if(CommonFunction.checkNull(vo.getOtherCharges()).trim().equalsIgnoreCase(""))
							 insertPrepStmtObject.addNull();
						 else
							 insertPrepStmtObject.addString((vo.getOtherCharges()).trim()); //ChargeValue
					}
			 	}
				 else  if(CommonFunction.checkNull(vo.getRepayTypeHid()).trim().equalsIgnoreCase("N"))
				 {
					 if(i==0)
						{
							insertPrepStmtObject.addString("Pl"); //component
							
							if(CommonFunction.checkNull(vo.getPrincipalCharges()).trim().equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString((vo.getPrincipalCharges()).trim()); //PrincipalValue
						}
						else if(i==1)
						{
							insertPrepStmtObject.addString("In"); //component
							
							if(CommonFunction.checkNull(vo.getInterestCharges()).trim().equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString((vo.getInterestCharges()).trim()); // InterestValue
						}
						else if(i==2)
						{
							insertPrepStmtObject.addString("ONon"); //component
							logger.info("getOtherChargesNon :::::::::::::::::: "+vo.getOtherChargesNon());
							if(CommonFunction.checkNull(vo.getOtherChargesNon()).trim().equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString((vo.getOtherChargesNon()).trim()); // InterestValue
						}
				 }
				
				if (vo.getAllocationStatus() != null && vo.getAllocationStatus().equalsIgnoreCase("on")) 
				{
					insertPrepStmtObject.addString("A");// REC_STATUS   
				} 
				else
				{
					insertPrepStmtObject.addString("X");// REC_STATUS   
				}
				
		    	if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString((vo.getMakerId()).trim());
		    	if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
		    	
		    	if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString((vo.getMakerId()).trim());
		    	if ((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
		    	
		    	if ((CommonFunction.checkNull(vo.getLbxNPAStageId()).trim()).equalsIgnoreCase(""))
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getLbxNPAStageId()).trim());
			  
		    	
		    	if(CommonFunction.checkNull(vo.getRepayTypeHid()).trim().equalsIgnoreCase("I"))
				 {
		    		if(i==0)
			    	{
			    		if(CommonFunction.checkNull(vo.getInstallmentChargesID()).trim().equalsIgnoreCase(""))
							 insertPrepStmtObject.addNull();
						 else
							 insertPrepStmtObject.addString((vo.getInstallmentChargesID()).trim()); //InstallmentChargeID id
			    	}
					else if(i==1)
					{
						if(CommonFunction.checkNull(vo.getPreEmiChargesID()).trim().equalsIgnoreCase(""))
							 insertPrepStmtObject.addNull();
						 else
							 insertPrepStmtObject.addString((vo.getPreEmiChargesID()).trim()); // PreEmiChargesID id
					}
					else if(i==2)
					{
						if(CommonFunction.checkNull(vo.getOtherChargesID()).trim().equalsIgnoreCase(""))
							 insertPrepStmtObject.addNull();
						 else
							 insertPrepStmtObject.addString((vo.getOtherChargesID()).trim()); //OtherChargesID id
					}
		    		
			 	}
				 else  if(CommonFunction.checkNull(vo.getRepayTypeHid()).trim().equalsIgnoreCase("N"))
				 {
					 if(i==0)
						{
							if(CommonFunction.checkNull(vo.getPrincipalChargesID()).trim().equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString((vo.getPrincipalChargesID()).trim()); //PrincipalChargesID
						}
						else if(i==1)
						{
							if(CommonFunction.checkNull(vo.getInterestChargesID()).trim().equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString((vo.getInterestChargesID()).trim()); // InterestChargesID
						}
						else if(i==2)
						{
							logger.info("getOtherChargesNonID :::::::::::::::::: "+vo.getOtherChargesNonID());
							if(CommonFunction.checkNull(vo.getOtherChargesNonID()).trim().equalsIgnoreCase(""))
								 insertPrepStmtObject.addNull();
							 else
								 insertPrepStmtObject.addString((vo.getOtherChargesNonID()).trim()); // InterestChargesID
						}
				 }
		    	
			 	insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN updateAutoAllocationDefDetail() update query1 ### here --- "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				bufInsSql=null;
				insertPrepStmtObject=null;
				
			}
			boolean status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Update status  :  "+status);	
			if(status)
			{
				result="UPDATE";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			qryList = null;
		}
			
		return result;
}	

	
	public ArrayList getRatioList() {
		ArrayList<Object> list=new ArrayList<Object>();
//		String query="";
		StringBuilder query=new StringBuilder();
		try{
			
		query.append("select RATIO_CODE,RATIO_NAME from cr_ratios_m where REC_STATUS='A'");
		
		logger.info("query : "+query);
		ArrayList paramDetail = ConnectionDAOforEJB.sqlSelect(query.toString());
		
		query=null;
		
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				MasterVo vo = new MasterVo();
				vo.setRatioCode((CommonFunction.checkNull(subParamDetail.get(0))).trim());
				vo.setRatioName((CommonFunction.checkNull(subParamDetail.get(1))).trim());
				 list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}

	@Override
	public String getRatioFarmula(String ratioCode) {
		String result="";
		StringBuilder query=new StringBuilder();
		try{
			query.append("select  RATIO_FORMULA from cr_ratios_m where RATIO_CODE='"+ratioCode+"'");
			logger.info("query : "+query);
			result= ConnectionDAOforEJB.singleReturn(query.toString());
			query=null;
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
	}
	public ArrayList getbaseRateTypeList() {
		
		
		ArrayList<BaseRateMasterVo> list=new ArrayList<BaseRateMasterVo>();
		ArrayList baseRateTypeList = null;
		ArrayList subList = null;
		try
		{
			StringBuilder query =	new StringBuilder();
		    query.append("SELECT VALUE,DESCRIPTION FROM generic_master where GENERIC_KEY='BASE_RATE_TYPE' and REC_STATUS='A' order by DESCRIPTION  ");
		    logger.info("In getClearingType() Query  :  "+query.toString());
		    baseRateTypeList = ConnectionDAO.sqlSelect(query.toString());
		    query=null;
		    for(int i=0;i<baseRateTypeList.size();i++)
		    {
		    	subList=(ArrayList)baseRateTypeList.get(i);
		    	BaseRateMasterVo av=new BaseRateMasterVo();
		    	av.setbRTypeCode((CommonFunction.checkNull(subList.get(0))).trim());
		    	av.setbRTypeDesc((CommonFunction.checkNull(subList.get(1))).trim());
		    	list.add(av);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
// Anil Code Start Here
	
	public ArrayList getRepaymentModeList() {
	ArrayList list = new ArrayList();
	try {
		logger.info("In getRepaymentModeList.............inside ejb server file............Dao Impl");
		String query = "SELECT DESCRIPTION,VALUE FROM GENERIC_MASTER WHERE GENERIC_KEY='REPAYMENT_MODE' and rec_status ='A' ";
		CrSchemeMasterVo vO = null;
		ArrayList repaymentMode = ConnectionDAOforEJB.sqlSelect(query);
		logger.info("getRepaymentModeList..........." + repaymentMode.size());
		for (int i = 0; i < repaymentMode.size(); i++) {
			logger.info("getRepaymentModeList.........."+ CommonFunction.checkNull(repaymentMode.get(i)).toString());
			ArrayList data = (ArrayList) repaymentMode.get(i);
			for (int k = 0; k < data.size(); k++) {
				logger.info("getRepaymentModeList............. "+ CommonFunction.checkNull(data.get(k)).toString());
				vO = new CrSchemeMasterVo();
				vO.setRepaymentLabel(((String) data.get(0).toString()));
				vO.setRepaymentMode(((String) data.get(1).toString()));
			}
			list.add(vO);
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	return list;
}
	// Anil Code End Here

	/* Code start by asesh For Benchmark Ratio Master  Here*/

	public String saveBenchmarkRatioMaster(Object ob) {
		BenchmarkRatioVo vo = (BenchmarkRatioVo) ob;
		boolean status = false;
        String result=null;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String sqlquery1="select BENCHMARK_RATIO_CODE, BENCHMARK_INDUSTRY_ID, EFFECTIVE_DATE , BENCHMARK_RATIO from cr_benchmark_ratio_m where BENCHMARK_RATIO_CODE='"+ StringEscapeUtils.escapeSql(vo.getLbxRatio().trim()) +"' and BENCHMARK_INDUSTRY_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxIndustry().trim()) +"' and BENCHMARK_RATIO='"+ StringEscapeUtils.escapeSql(vo.getBenchmarkRatio().trim()) +"' and CONVERT(DATE, EFFECTIVE_DATE)='"+CommonFunction.changeFormat(vo.getEffectiveDate())+"'";
		logger.info("In select benchMarkRatioExist query *********************************************************...."+sqlquery1);
		boolean benchMarkRatioExist = ConnectionDAOforEJB.checkStatus(sqlquery1);
		if(benchMarkRatioExist==true){
			result="dataExist";
		}
		else{
		try {

				if (vo.getRecStatus() != null && vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert saveBenchmarkRatioMaster");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into cr_benchmark_ratio_m (BENCHMARK_RATIO_CODE ,BENCHMARK_INDUSTRY_ID,BENCHMARK_RATIO,EFFECTIVE_DATE,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //BENCHMARK_RATIO_CODE
				bufInsSql.append(" ?,"); //BENCHMARK_INDUSTRY_ID				
				bufInsSql.append(" ?,"); //BENCHMARK_RATIO	
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//EFFECTIVE_DATE
				bufInsSql.append(" ?,"); //REC_STATUS	
				bufInsSql.append(" ?,");// MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//AUTHOR_DATE

				if (CommonFunction.checkNull(vo.getLbxRatio())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxRatio().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getLbxIndustry())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxIndustry().toUpperCase().trim());
				
					if (CommonFunction.checkNull(vo.getBenchmarkRatio())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getBenchmarkRatio().toUpperCase().trim());
					if (CommonFunction.checkNull(vo.getEffectiveDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getEffectiveDate());
					
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

			
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveBenchMarkRatio() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);				
				logger.info("In saveBenchMarkRatio......................****************************** before return result *********** "
						+ status);
				if(status)
					result="datasaved";
					else
						result="datanotsaved";

		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return result;

		
	}
	

	
	@Override
	public ArrayList<BenchmarkRatioVo> getBenchmarkRatioMasterList(Object ob) {
		ArrayList searchlist = new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String searchRatioId = "";
		String searchIndustryId = "";
		BenchmarkRatioVo vo = (BenchmarkRatioVo) ob;
		ArrayList<BenchmarkRatioVo>  detailList = new ArrayList<BenchmarkRatioVo> ();
		try {
			logger.info("In getBenchmarkRatioMasterList...............");
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			searchRatioId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxRatioSearch())).trim());
			searchIndustryId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxIndustrySearch())).trim());

			bufInsSql.append("select BENCHMARK_ID,RATIO_NAME,CIM.INDUSTRY_DESC,case when CBRM.REC_STATUS='A' then 'Active' else 'Inactive' end AS REC FROM cr_benchmark_ratio_m CBRM ");
			bufInsSql.append("JOIN cr_ratios_m CRM ON CBRM. BENCHMARK_RATIO_CODE=CRM. RATIO_CODE");
			bufInsSql.append(" JOIN com_industry_m CIM ON CBRM.BENCHMARK_INDUSTRY_ID= CIM.INDUSTRY_ID ");

			bufInsSqlTempCount.append("SELECT COUNT(1) from cr_benchmark_ratio_m");
			
			if (!searchRatioId.equals("") && !searchIndustryId.equals("") ) {
				
				bufInsSql.append(" where BENCHMARK_RATIO_CODE = '" + searchRatioId+ "' and  BENCHMARK_INDUSTRY_ID = '" + searchIndustryId+ "'");
				bufInsSqlTempCount.append(" where BENCHMARK_RATIO_CODE = '" + searchRatioId+ "' and BENCHMARK_INDUSTRY_ID = '" + searchIndustryId+ "'");
			}
			else if (!searchRatioId.equals("") ) {
				
				bufInsSql.append(" where BENCHMARK_RATIO_CODE = '" + searchRatioId+ "'  ");
				bufInsSqlTempCount.append(" where BENCHMARK_RATIO_CODE = '" + searchRatioId+ "' ");
			}
			
			else if (!searchIndustryId.equals("") ) {
				
				bufInsSql.append(" where BENCHMARK_INDUSTRY_ID = '" + searchIndustryId+ "'  ");
				bufInsSqlTempCount.append(" where BENCHMARK_INDUSTRY_ID = '" + searchIndustryId+ "' ");
			}

			logger.info("IN getBenchmarkRatioMasterList() search query1 ### "+ bufInsSql.toString());
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" ORDER BY CBRM.BENCHMARK_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					BenchmarkRatioVo benchmarkRatioVo = new BenchmarkRatioVo();
					benchmarkRatioVo.setRatioCodeModify("<a href=benchMarkRatioAdd.do?method=openEditBenchMarkRatio&benchmarkRatioSeq="
							+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
							+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					benchmarkRatioVo.setBenchmarkRatioCode(CommonFunction.checkNull(data.get(1)).toString());
					benchmarkRatioVo.setBenchmarkIndustryId(CommonFunction.checkNull(data.get(2)).toString());
					benchmarkRatioVo.setRecStatus(CommonFunction.checkNull(data.get(3)).toString());
					benchmarkRatioVo.setTotalRecordSize(count);
					detailList.add(benchmarkRatioVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	

@Override
public String updateBenchMarkRatioData(Object ob) {
	
	BenchmarkRatioVo vo = (BenchmarkRatioVo) ob;
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	logger.info("DealerID():-" +vo.getBenchmarkRatioSeq());
	ArrayList updatelist = new ArrayList();	
	 String result="";
	 boolean status = false;
	String stat = "";
	String sqlquery1="select BENCHMARK_RATIO_CODE, BENCHMARK_INDUSTRY_ID, EFFECTIVE_DATE , BENCHMARK_RATIO from cr_benchmark_ratio_m where BENCHMARK_RATIO_CODE='"+ StringEscapeUtils.escapeSql(vo.getLbxRatio().trim()) +"' and BENCHMARK_INDUSTRY_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxIndustry().trim()) +"' and BENCHMARK_RATIO='"+ StringEscapeUtils.escapeSql(vo.getBenchmarkRatio().trim()) +"' and CONVERT(DATE, EFFECTIVE_DATE)='"+CommonFunction.changeFormat(vo.getEffectiveDate())+"' and  BENCHMARK_ID!="+vo.getBenchmarkRatioSeq();
	boolean benchMarkExist = ConnectionDAOforEJB.checkStatus(sqlquery1);
	if(benchMarkExist==true){
		result="dataExist";
	}
	else{
	try {
		if (!benchMarkExist) {
		if (vo.getRatioCodeModify()!= null && vo.getRecStatus().equals("on")) {
			stat = "A";
		} else {
			stat = "X";

		}
		StringBuffer bufInsSql = new StringBuffer();				
	
			insertPrepStmtObject=null;
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql=null;
			bufInsSql = new StringBuffer();
			bufInsSql.append("UPDATE cr_benchmark_ratio_m set BENCHMARK_RATIO_CODE=?, BENCHMARK_INDUSTRY_ID=?,");
			bufInsSql.append(" BENCHMARK_RATIO=?,EFFECTIVE_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(",REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where  BENCHMARK_ID=?");

			if (CommonFunction.checkNull(vo.getLbxRatio())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxRatio().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getLbxIndustry())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxIndustry().toUpperCase().trim());
			
				if (CommonFunction.checkNull(vo.getBenchmarkRatio())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getBenchmarkRatio()
							.toUpperCase().trim());
			
				if (CommonFunction.checkNull(vo.getEffectiveDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEffectiveDate());
				
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

		
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
					""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			logger.info("BENCHMARK_ID : " + vo.getBenchmarkRatioSeq());
			if (CommonFunction.checkNull(vo.getBenchmarkRatioSeq()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getBenchmarkRatioSeq());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN updateBenchMarkRatioData() update query1 ### "
					+ insertPrepStmtObject.printQuery());
			updatelist.add(insertPrepStmtObject);
		
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
		

		if(status){
			result="saved";
		}else{
			result="notsaved";
		}
		
    }						
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	return result;
}

public ArrayList searchBenchMarkRatioEdit(Object ob) {

	ArrayList searchlist = new ArrayList();
	BenchmarkRatioVo vo = (BenchmarkRatioVo)ob;
	ArrayList<BenchmarkRatioVo> ratioList = new ArrayList<BenchmarkRatioVo>();
	logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getBenchmarkRatioSeq());

	try {
		logger.info("In searchBenchMarkRatioEdit().....................................Dao Impl");
		

		StringBuffer bufInsSql = new StringBuffer();
		bufInsSql.append(" select  BENCHMARK_RATIO_CODE,RATIO_NAME,BENCHMARK_INDUSTRY_ID,INDUSTRY_DESC,BENCHMARK_RATIO,dbo.DATE_FORMAT(EFFECTIVE_DATE,'"+dateFormat+"'),cbrm.REC_STATUS  from cr_benchmark_ratio_m cbrm");
		bufInsSql.append(" join cr_ratios_m crm on cbrm.BENCHMARK_RATIO_CODE= crm. RATIO_CODE ");
		bufInsSql.append(" join com_industry_m cim on cbrm .BENCHMARK_INDUSTRY_ID= cim. INDUSTRY_ID WHERE" + 
				" BENCHMARK_ID='"+StringEscapeUtils.escapeSql(vo.getRatioCodeModify())+"'");
		logger.info("search Query...." + bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		logger.info("IN searchBenchMarkRatioEdit() search query1 ### "+ bufInsSql.toString());
		logger.info("searchBenchMarkRatioEdit " + searchlist.size());
		for (int i = 0; i < searchlist.size(); i++) {
			logger.info("searchBenchMarkRatioEdit " + searchlist.get(i).toString());
			ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				BenchmarkRatioVo ratioVo = new BenchmarkRatioVo();
				
				ratioVo.setLbxRatio(CommonFunction.checkNull(data.get(0)).toString());
				ratioVo.setBenchmarkRatioCode(CommonFunction.checkNull(data.get(1)).toString());					
				ratioVo.setLbxIndustry(CommonFunction.checkNull(data.get(2)).toString());
				ratioVo.setBenchmarkIndustryId(CommonFunction.checkNull(data.get(3)).toString());
				ratioVo.setBenchmarkRatio(CommonFunction.checkNull(data.get(4)).toString());
				ratioVo.setEffectiveDate(CommonFunction.checkNull(data.get(5)).toString());
				ratioVo.setRecStatus(CommonFunction.checkNull(data.get(6)).toString());
				ratioList.add(ratioVo);

			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return ratioList;
}
/* Code start by asesh End Here*/

	//code added by manish
		public ArrayList getAddressList() {
			logger.info("In MSSQLMasterDAOImpl's method getAddressList");		
			ArrayList<Object> list=new ArrayList<Object>();
			try
			{		
				String query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='ADDRESS_TYPE' and rec_status ='A'";
				logger.info("getAddressList"+query);
				VerificationQuestionVo vo = null;
				ArrayList source = ConnectionDAOforEJB.sqlSelect(query);
				logger.info("getAddressList"+source.size());
				for(int i=0;i<source.size();i++)
				{			
					ArrayList subAddress=(ArrayList)source.get(i);
					if(subAddress.size()>0)
					{
						logger.info("getAddressList"+subAddress.size());
						vo = new VerificationQuestionVo();
						vo.setAddressId((CommonFunction.checkNull(subAddress.get(0)).toString()));
						vo.setAddressDesc((CommonFunction.checkNull(subAddress.get(1)).toString()));
						list.add(vo);
					}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return list;
	}
		
		@Override
		public boolean finCheckExpression(Object ob) {
			// TODO Auto-generated method stub
			FinancialPramMasterVo vo = (FinancialPramMasterVo) ob;
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			ArrayList updatelist = new ArrayList();

			boolean status = false;
			String expr = "";
			String param = "";
			try {

				String query = "select PARAM_CODE from cr_financial_param where REC_STATUS='A'";
				logger.info("query : " + query);
				MasterVo vO = null;
				ArrayList paramCodeList = ConnectionDAOforEJB.sqlSelect(query);
				expr = vo.getFinExpression();
				logger.info("paramCodeList " + paramCodeList.size());
				for (int i = 0; i < paramCodeList.size(); i++) {
					
					ArrayList data = (ArrayList) paramCodeList.get(i);
					for (int k = 0; k < data.size(); k++) 
					{
						param = (String) data.get(0).toString();
						logger.info("param ******************* "+param);
						logger.info("expr.indexOf(param) ******************* "+expr.indexOf(param));
						if(expr.indexOf(param) >=0)
						{
							
							expr = expr.replaceAll(param, "1");
							logger.info("expr in if******************* "+expr);
							//ratioExpr = vo.getRatioExpr().replaceFirst(paramCodeInExpr, vo1.getFirstYear());
						}
					}
					
				}
				logger.info("expression .................................. "+expr);
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				
				logger.info("CommonFunction.checkNull(engine.eval(expr):::::::::::: "+CommonFunction.checkNull(engine.eval(expr)+""));
				if(!CommonFunction.checkNull(engine.eval(expr)+"").equalsIgnoreCase(""))
				{
					status=true;
				}
				
			}
			catch (Exception e) {
				e.printStackTrace();

			}
			logger.info("status .................................. "+status);
			return status;

		}
// asesh Space Start here
		@Override
		public ArrayList<BusinessClosureVo> getBusinessMonthList(Object ob) {
			ArrayList searchlist = new ArrayList();
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			String businessMonthSearch = "";
			String businessYearSearch = "";
			BusinessClosureVo vo = (BusinessClosureVo) ob;
			ArrayList<BusinessClosureVo>  detailList = new ArrayList<BusinessClosureVo> ();
			try {
				logger.info("In getBusinessMonthList...............");
				StringBuffer bufInsSql = new StringBuffer();
				StringBuffer lastRecord = new StringBuffer();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
				
				businessMonthSearch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessMonthSearch())).trim());
				businessYearSearch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessYearSearch())).trim());
				bufInsSql.append("select BUSINESS_MONTH,case when BUSINESS_MONTH='1' then 'January' when BUSINESS_MONTH='2' then 'February' when BUSINESS_MONTH='3' then 'March' when BUSINESS_MONTH='4' then 'April' when BUSINESS_MONTH='5' then 'May' when BUSINESS_MONTH='6' then 'June' when BUSINESS_MONTH='7' then 'July' when BUSINESS_MONTH='8' then 'August' when BUSINESS_MONTH='9' then 'September' when BUSINESS_MONTH='10' then 'October' when BUSINESS_MONTH='11' then 'November' when BUSINESS_MONTH='12' then 'December' end as BUSINESS_MONTH,BUSINESS_YEAR,dbo.DATE_FORMAT(START_DATE,'%d-%m-%Y'),dbo.DATE_FORMAT(END_DATE,'%d-%m-%Y'),case when REC_STATUS='A' then 'Active' else 'Inactive' end AS REC,business_id FROM cr_business_month_closur_m ");
				
				lastRecord.append("select max(business_id)business_id from cr_business_month_closur_m  WITH (ROWLOCK) ");
				bufInsSqlTempCount.append("SELECT COUNT(1) from cr_business_month_closur_m");
				
				if (!businessMonthSearch.equals("") && !businessYearSearch.equals("") ) {
					
					bufInsSql.append(" where BUSINESS_MONTH = '" + businessMonthSearch+ "' and  BUSINESS_YEAR = '" + businessYearSearch+ "'");
					//lastRecord.append(" where BUSINESS_MONTH = '" + businessMonthSearch+ "' and  BUSINESS_YEAR = '" + businessYearSearch+ "'");
					bufInsSqlTempCount.append(" where BUSINESS_MONTH = '" + businessMonthSearch+ "' and BUSINESS_YEAR = '" + businessYearSearch+ "'");
				}
				else if (!businessMonthSearch.equals("") ) {
					
					bufInsSql.append(" where BUSINESS_MONTH = '" + businessMonthSearch+ "'  ");
					//lastRecord.append(" where BUSINESS_MONTH = '" + businessMonthSearch+ "'  ");
					bufInsSqlTempCount.append(" where BUSINESS_MONTH = '" + businessMonthSearch+ "' ");
				}
				
				else if (!businessYearSearch.equals("") ) {
					
					bufInsSql.append(" where BUSINESS_YEAR = '" + businessYearSearch+ "'  ");
					//lastRecord.append(" where BUSINESS_YEAR = '" + businessYearSearch+ "'  ");
					bufInsSqlTempCount.append(" where BUSINESS_YEAR = '" + businessYearSearch+ "' ");
				}
		        //bufInsSql.append(" order by BUSINESS_ID");

				logger.info("IN getBusinessMonthList() search query1 ### "+ bufInsSql.toString());
				
				logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
				logger.info("maxBusinessId Query---- : "+lastRecord.toString());
				String businessId =ConnectionDAOforEJB.singleReturn(lastRecord.toString());
		        count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
				
//		if(businessMonthSearch.trim()==null  || businessMonthSearch.trim().equalsIgnoreCase("")|| businessYearSearch.trim()==null  || businessYearSearch.trim().equalsIgnoreCase("") || vo.getCurrentPageLink()>1)
//		{
				
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				bufInsSql.append(" ORDER BY BUSINESS_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				//bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
//			}
				logger.info("query : "+bufInsSql);
				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
				logger.info("searchlist size ---------------------"+searchlist.size());
				
				for (int i = 0; i < searchlist.size(); i++) {
					ArrayList data = (ArrayList) searchlist.get(i);

					if (data.size() > 0) 
					{
						BusinessClosureVo businessclosureVo = new BusinessClosureVo();
						if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase(businessId))
						{
								businessclosureVo.setBusinessMonth("<a href=businessMonthAdd.do?method=openEditBusinessClosure&businessMonth="
								+ CommonFunction.checkNull(data.get(0)).toString()+ "&businessYear="
								+ CommonFunction.checkNull(data.get(2)).toString()+">"
								+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
								businessclosureVo.setBusinessYear(CommonFunction.checkNull(data.get(2)).toString());
								businessclosureVo.setStartDate(CommonFunction.checkNull(data.get(3)).toString());
								businessclosureVo.setEndDate(CommonFunction.checkNull(data.get(4)).toString());
								businessclosureVo.setRecStatus(CommonFunction.checkNull(data.get(5)).toString());
								businessclosureVo.setTotalRecordSize(count);
								detailList.add(businessclosureVo);
						}
						else
						{
						businessclosureVo.setBusinessMonth(CommonFunction.checkNull(data.get(1)).toString());						
						businessclosureVo.setBusinessYear(CommonFunction.checkNull(data.get(2)).toString());
						businessclosureVo.setStartDate(CommonFunction.checkNull(data.get(3)).toString());
						businessclosureVo.setEndDate(CommonFunction.checkNull(data.get(4)).toString());
						businessclosureVo.setRecStatus(CommonFunction.checkNull(data.get(5)).toString());
						businessclosureVo.setTotalRecordSize(count);
						detailList.add(businessclosureVo);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return detailList;
		}

		public ArrayList searchBusinessMonthClosureEdit(Object ob) {

			ArrayList searchlist = new ArrayList();
			BusinessClosureVo vo = (BusinessClosureVo)ob;
			ArrayList<BusinessClosureVo> ratioList = new ArrayList<BusinessClosureVo>();
			logger.info("getBusinessMonth------------------------"+vo.getBusinessMonth());
			logger.info("getBusinessMonth------------------------"+vo.getBusinessMonth());
			try {
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append(" select  case when BUSINESS_MONTH='1' then 'January' when BUSINESS_MONTH='2' then 'February' when BUSINESS_MONTH='3' then 'March' when BUSINESS_MONTH='4' then 'April' when BUSINESS_MONTH='5' then 'May' when BUSINESS_MONTH='6' then 'June' when BUSINESS_MONTH='7' then 'July' when BUSINESS_MONTH='8' then 'August' when BUSINESS_MONTH='9' then 'September' when BUSINESS_MONTH='10' then 'October' when BUSINESS_MONTH='11' then 'November' when BUSINESS_MONTH='12' then 'December' end as BUSINESS_MONTH,BUSINESS_YEAR,dbo.DATE_FORMAT(START_DATE,'"+dateFormat+"'),dbo.DATE_FORMAT(END_DATE,'"+dateFormat+"'),REC_STATUS  from cr_business_month_closur_m ");
				bufInsSql.append(" WHERE" + 
						" BUSINESS_MONTH='"+StringEscapeUtils.escapeSql(vo.getBusinessMonth())+"' and BUSINESS_YEAR='"+StringEscapeUtils.escapeSql(vo.getBusinessYear())+"'");
				logger.info("search Query...." + bufInsSql);

				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
				logger.info("IN searchBusinessMonthClosureEdit() search query1 ### "+ bufInsSql.toString());
				logger.info("searchBusinessMonthClosureEdit " + searchlist.size());
				for (int i = 0; i < searchlist.size(); i++) {
					logger.info("searchBusinessMonthClosureEdit " + searchlist.get(i).toString());
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						BusinessClosureVo ratioVo = new BusinessClosureVo();
						
						ratioVo.setBusinessMonthss(CommonFunction.checkNull(data.get(0)).toString());
						ratioVo.setBusinessYear(CommonFunction.checkNull(data.get(1)).toString());					
						ratioVo.setStartDate(CommonFunction.checkNull(data.get(2)).toString());
						ratioVo.setEndDate(CommonFunction.checkNull(data.get(3)).toString());
						ratioVo.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
						ratioList.add(ratioVo);

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return ratioList;
		}

		public String updateBusinessMonthClosureData(Object ob) {
			
			BusinessClosureVo vo = (BusinessClosureVo) ob;
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			ArrayList updatelist = new ArrayList();	
			 String result="";
			 boolean status = false;
			String stat = "";
			try {
				if (true) {
				if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				StringBuffer bufInsSql = new StringBuffer();				
			
					insertPrepStmtObject=null;
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql=null;
					bufInsSql = new StringBuffer();
					bufInsSql.append("UPDATE cr_business_month_closur_m set START_DATE=");
					bufInsSql.append(dbo);
		            bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					bufInsSql.append(", END_DATE=");
					bufInsSql.append(dbo);
		            bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					bufInsSql.append(", REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
					bufInsSql.append(dbo);
		            bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					bufInsSql.append("where BUSINESS_MONTH='"+ StringEscapeUtils.escapeSql(vo.getBusinessMonth().trim()) +"' and BUSINESS_YEAR='"+ StringEscapeUtils.escapeSql(vo.getBusinessYear().trim()) +"'");
					if (CommonFunction.checkNull(vo.getStartDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getStartDate());
					
					if (CommonFunction.checkNull(vo.getEndDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getEndDate());
					
					if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);
					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());
					if (CommonFunction.checkNull(vo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());
					logger.info("BUSINESS_MONTH : " + vo.getBusinessMonth());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN updateBusinessMonthClosureData() update query1 ### "
							+ insertPrepStmtObject.printQuery());
					updatelist.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
				

				if(status){
					result="saved";
				}else{
					result="notsaved";
				}
				
		    }						
			} catch (Exception e) {
				e.printStackTrace();
			}
//			}
			return result;
		}

		@Override
		public String saveBusinessMonthClosureDetails(Object ob) {
			BusinessClosureVo vo = (BusinessClosureVo)ob;
			boolean status = false;
		    String result=null;
		    
			logger.info("In saveBusinessMonthClosureDetails.........inside ejb server file...........Dao Impl recStatus"+ vo.getRecStatus());
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			String stat = "X";
			String sqlquery1="select START_DATE,END_DATE from cr_business_month_closur_m where BUSINESS_MONTH='"+ StringEscapeUtils.escapeSql(vo.getBusinessMonthss().trim())+"' and BUSINESS_YEAR='"+ StringEscapeUtils.escapeSql(vo.getBusinessYear().trim())+"'";
			logger.info("In select saveBusinessMonthClosureDetails query ..."+sqlquery1);
			boolean businessMonthClosureExist = ConnectionDAO.checkStatus(sqlquery1);
			logger.info("In saveBusinessMonthClosureDetails.."+businessMonthClosureExist);
			logger.info("In getBusinessMonthss.--------------------------------------."+vo.getBusinessMonthss());
			if(businessMonthClosureExist==true){
				result="dataExist";
			}
			else{
			try {

					if (vo.getRecStatus() != null && vo.getRecStatus().equals("on")) {
						stat = "A";
					} else {
						stat = "X";

					}

					logger.info("In insert saveBusinessMonthClosureDetails");
					StringBuffer bufInsSql = new StringBuffer();
					bufInsSql.append("insert into cr_business_month_closur_m (BUSINESS_MONTH,BUSINESS_YEAR,START_DATE,END_DATE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,"); //BUSINESS_MONTH
					bufInsSql.append(" ?,"); //BUSINESS_YEAR				
					bufInsSql.append(dbo);
		            bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");//START_DATE
		            bufInsSql.append(dbo);
		            bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");//END_DATE
					bufInsSql.append(" ?,"); //REC_STATUS	
					bufInsSql.append(" ?,");// MAKER_ID
					bufInsSql.append(dbo);
		            bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");//MAKER_DATE
					bufInsSql.append(" ?,");// AUTHOR_ID
					bufInsSql.append(dbo);
		            bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)) ");//AUTHOR_DATE
					if (CommonFunction.checkNull(vo.getBusinessMonthss())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getBusinessMonthss().toUpperCase().trim());
					
					if (CommonFunction.checkNull(vo.getBusinessYear())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getBusinessYear().toUpperCase().trim());
					
						if (CommonFunction.checkNull(vo.getStartDate())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getStartDate());
						if (CommonFunction.checkNull(vo.getEndDate())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getEndDate());
						if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(stat);
						
						if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
							""))
						insertPrepStmtObject.addNull();
						else
						insertPrepStmtObject.addString(vo.getMakerId());
						if (CommonFunction.checkNull(vo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());
					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(vo.getMakerId());
					if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(vo.getMakerDate());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN saveBusinessMonthClosureDetails() insert query1 ### "+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);				
					if(status)
						result="datasaved";
						else
							result="datanotsaved";

			} catch (Exception e) {
				e.printStackTrace();
			}
			}
			return result;

			
		}
		public String getStartDate(String businessMonthss,String businessYear) 
		{
			logger.info("In getStartDate");		
			String businessMonths="";
			String year="";
			String result="";
			int count=0;
			try
			{
				String cntQuery="";
				StringBuilder dateQuery=new StringBuilder();
				businessMonths=businessMonthss;
				logger.info("businessMonths-------getStartDate daoimpl-----------"+businessMonths);
				if(businessMonths.equalsIgnoreCase("1"))
				{
					cntQuery="select count(1) from cr_business_month_closur_m WHERE BUSINESS_MONTH='12' AND BUSINESS_YEAR="+businessYear+"-1";
					logger.info("cntQuery- january--"+cntQuery);
					count=Integer.parseInt(ConnectionDAOforEJB.singleReturn(cntQuery));
					logger.info("count----january--"+count);
					if(count>0)
					{
						dateQuery.append("select ");
						dateQuery.append("dbo.DATE_FORMAT(DATEADD(DAY, 1, END_DATE),'%d-%m-%Y') as START_DATE");
						dateQuery.append(" from cr_business_month_closur_m where BUSINESS_MONTH='12' and BUSINESS_YEAR="+businessYear+"-1");
						logger.info("dateQuery---"+dateQuery.toString());
						result=ConnectionDAOforEJB.singleReturn(dateQuery.toString());
					}
					else
					{
						result="";
					}
				}
				else
				{
					cntQuery="select count(1) from cr_business_month_closur_m WHERE BUSINESS_MONTH="+businessMonths+"-1 AND BUSINESS_YEAR="+businessYear+"";
					logger.info("cntQuery---"+cntQuery);
					count=Integer.parseInt(ConnectionDAOforEJB.singleReturn(cntQuery));
					logger.info("count-------"+count);
					if(count>0)
					{
						dateQuery.append("select dbo.DATE_FORMAT(DATEADD(DAY, 1, END_DATE),'%d-%m-%Y') as START_DATE from cr_business_month_closur_m where BUSINESS_MONTH="+businessMonths+"-1 and BUSINESS_YEAR="+businessYear+"");
						logger.info("dateQuery---"+dateQuery.toString());
						result=ConnectionDAOforEJB.singleReturn(dateQuery.toString());
						
					}
					else
					{
						result="";
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}
		
		// Asesh space End Here
	
	//SANJOG CODE START FOR USER APPROVAL
	public int getApprovalfromPmst() {
		ArrayList list = new ArrayList();
		int count = 0;
		try {
			logger.info("In getApprovalfromPmst.............inside ejb server file............Dao Impl");
			String query = "select PARAMETER_VALUE from parameter_mst where parameter_key = 'MASTER_APPROVAL_LEVEL'";
			ApprovalLevelDefVo vO = null;
			ArrayList getApprovalfromPmst = ConnectionDAOforEJB.sqlSelect(query);
			String countStr = ConnectionDAOforEJB.singleReturn(query);
			if(!(CommonFunction.checkNull(countStr).equalsIgnoreCase("")))
				count = Integer.parseInt(countStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int getApprovalLevelfromPmst() {
		ArrayList list = new ArrayList();
		int count = 0;
		try {
			logger.info("In getApprovalLevelfromPmst.............inside ejb server file............Dao Impl");
			String query = "select PARAMETER_VALUE from parameter_mst where parameter_key = 'MASTER_APPROVAL_LEVEL'";
			ApprovalLevelDefVo vO = null;
			ArrayList getApprovalLevelfromPmst = ConnectionDAOforEJB.sqlSelect(query);
			String countStr = ConnectionDAOforEJB.singleReturn(query);
			if(!(CommonFunction.checkNull(countStr).equalsIgnoreCase("")))
				count = Integer.parseInt(countStr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean ifscBankBranchMaster(Object ob) {
		BankBranchMasterVo bankBranchMasterVo = (BankBranchMasterVo) ob;
		boolean result = false;
		try {
			logger.info("In ifscBankBranchMaster.............inside MSSQLmasterDao Impl");
			String query = "select count(1) from com_bankbranch_m where BANK_BRANCH_CODE not in('"+StringEscapeUtils.escapeSql(bankBranchMasterVo.getBankBranchCode().trim())+"') and BRANCH_IFCS_CODE='"+StringEscapeUtils.escapeSql(bankBranchMasterVo.getBranchIFCSCode().trim())+"'";
			logger.info("In ifscBankBranchMaster.............ifscBankBranchMaster...query"+query);
			String countStr = ConnectionDAOforEJB.singleReturn(query);
			if(!countStr.equalsIgnoreCase("0")){
				result=true;
			}
			logger.info("result"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//Saurabh changes starts
	public String getMakerCheckerFlag(){
		logger.info("in  getMakerCheckerFlag:::::::::::::: ");
		String makerCheckerFlag="";
		try{
		String query="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='MASTER_MAKER_AUTHOR'";
		logger.info("Query for maker checker flag is::::::"+query);
		
		 makerCheckerFlag=ConnectionDAOforEJB.singleReturn(query);
		
		logger.info("flak ki value hai::::::::"+makerCheckerFlag);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return makerCheckerFlag;
	}
	// saurabh changes ends
	// saurabh changes starts
	public String getApprovalId(){
		logger.info("in  getApprovalId:::::::::::::: ");
		String productModify="";
		String query="";
		try {			
			 query = "select MAX(APPROVAL_LEVEL_ID) from cr_approval_level_m_TEMP ";
			logger.info("in  getApprovalId:::::::::::::query::::::: "+query);
			 productModify = ConnectionDAOforEJB.singleReturn(query);	
			 logger.info("in  getApprovalId:::::::::::::value ::::::: "+productModify);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			query=null;
		}
		return productModify;
	}
	public String forwardApprovalLevel(String approvalId){
		logger.info("In forwardApprovalLevel::::::::::::::::"+approvalId);
		StringBuffer query=new StringBuffer();
		boolean status=false;
		ArrayList queryList=new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String queryResult="";
		
		try{
			query.append(" UPDATE CR_APPROVAL_LEVEL_M_temp SET MAKER_AUTHOR_STATUS='F' WHERE APPROVAL_LEVEL_ID=?");
			logger.info("query:::::::::::::::::::::is::::"+query.toString());
			
			if (CommonFunction.checkNull(approvalId).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(approvalId);
			
			
			insertPrepStmtObject.setSql(query.toString());
			logger.info("IN forwardApprovalLevel() insert query1 ### "+ insertPrepStmtObject.printQuery());
			queryList.add(insertPrepStmtObject);
			status=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(queryList);
			logger.info("update  :::::::::::::::::::"+status);
			if (status)
				queryResult="S";
			logger.info("update  :::::::::::::::::::"+queryResult);
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			query=null;
			queryList=null;
			insertPrepStmtObject=null;
		}
		return queryResult;
	}
	public ArrayList searchApprovalLevelDefAuthor(ApprovalLevelDefVo Vo){

		String productModify="";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList<ApprovalLevelDefVo> detailList = new ArrayList<ApprovalLevelDefVo>();
		try {
			logger.info("In searchApprovalLevelDef.....................................Dao Impl");
			
			String productSearch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxProductID())).trim());
			logger.info("productSearch...." + productSearch);
			
			String findApprovalSearch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getFindApprovalSearch())).trim());
			logger.info("findApprovalSearch...." + findApprovalSearch);
			
			productModify = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getProductModify())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			
			
			
//				bufInsSql.append("SELECT PRODUCT_ID,FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO,if(REC_STATUS='A','Active','Inactive')as Status  from cr_approval_level_m ");
			bufInsSql.append("SELECT APPROVAL_LEVEL_ID,(SELECT cr_p.PRODUCT_DESC FROM cr_product_m cr_p WHERE cr_p.PRODUCT_ID=cra.PRODUCT_ID) as Product_Id,(SELECT SCHEME_DESC FROM CR_SCHEME_M S WHERE S.SCHEME_ID=cra.SCHEME_ID) as Scheme , ");
			bufInsSql.append("FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status");
			bufInsSql.append(" FROM CR_APPROVAL_LEVEL_M_temp cra where 'a'='a' AND MAKER_AUTHOR_STATUS='F' ");
			
			
			bufInsSqlTempCount.append("SELECT count(1) ");
			//bufInsSqlTempCount.append("SELECT count(1) APPROVAL_LEVEL_ID,(SELECT cr_p.PRODUCT_DESC FROM cr_product_m cr_p WHERE cr_p.PRODUCT_ID=cra.PRODUCT_ID) as Product_Id,(SELECT SCHEME_DESC FROM CR_SCHEME_M S WHERE S.SCHEME_ID=cra.SCHEME_ID) as Scheme , ");
			//bufInsSqlTempCount.append("FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO,case when REC_STATUS='A' then 'Active' else 'Inactive' end as ");
			bufInsSqlTempCount.append(" FROM CR_APPROVAL_LEVEL_M_temp cra where 'a'='a' AND MAKER_AUTHOR_STATUS='F' ");
			
			if(!(CommonFunction.checkNull(Vo.getLbxProductID()).equalsIgnoreCase("")))
			{
				bufInsSql.append("and cra.PRODUCT_ID='"+(CommonFunction.checkNull(Vo.getLbxProductID())).trim()+"' ");
				bufInsSqlTempCount.append(" and cra.PRODUCT_ID='"+(CommonFunction.checkNull(Vo.getLbxProductID())).trim()+"' "); 
			}
			if(!(CommonFunction.checkNull(Vo.getLbxSchemeId()).equalsIgnoreCase("")))
			{
				bufInsSql.append(" and cra.SCHEME_ID='"+(CommonFunction.checkNull(Vo.getLbxSchemeId())).trim()+"'");
				bufInsSqlTempCount.append(" and cra.SCHEME_ID='"+(CommonFunction.checkNull(Vo.getLbxSchemeId())).trim()+"'"); 
			}
			
			if(!(CommonFunction.checkNull(Vo.getFindApprovalSearch()).equalsIgnoreCase("")))
			{
				bufInsSql.append(" and cra.FINAL_APPROVAL_LEVEL='"+(CommonFunction.checkNull(Vo.getFindApprovalSearch())).trim()+"'");
				bufInsSqlTempCount.append(" and cra.FINAL_APPROVAL_LEVEL='"+(CommonFunction.checkNull(Vo.getFindApprovalSearch())).trim()+"'"); 
			}


			//bufInsSqlTempCount.append(" group by APPROVAL_LEVEL_ID,Product_Id,FINAL_APPROVAL_LEVEL,AMOUNT_FROM,AMOUNT_TO,cra.SCHEME_ID,REC_STATUS");	
			logger.info("search Query...." + bufInsSql.toString());
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	        count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((productSearch.trim()==null && findApprovalSearch.trim()==null) || (productSearch.trim().equalsIgnoreCase("") && findApprovalSearch.trim().equalsIgnoreCase("")) || Vo.getCurrentPageLink()>1)
//			{
			
				logger.info("current PAge Link no .................... "+Vo.getCurrentPageLink());
			if(Vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (Vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY APPROVAL_LEVEL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			
//			}
			logger.info("query :::::::::::::::::saurabh "+bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("searchApprovalLevelDef " + searchlist.size());
			
			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					ApprovalLevelDefVo LevelDefVo = new ApprovalLevelDefVo();
					            
					LevelDefVo.setApprovalLevelID("<a href=UWApprovalLevelDefAuthorApprove.do?method=EditapprovalLevelDefAuthor&productModify="
									+ CommonFunction.checkNull(data.get(0)).toString() + ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					LevelDefVo.setProductSearch(CommonFunction.checkNull(data.get(1)));
					LevelDefVo.setLbxProductID(CommonFunction.checkNull(data.get(1)));
					LevelDefVo.setScheme(CommonFunction.checkNull(data.get(2)));
					LevelDefVo.setFindApprovalSearch(CommonFunction.checkNull(data.get(3)));
					LevelDefVo.setFindApprovalLevel(CommonFunction.checkNull(data.get(3)));
					if(!(CommonFunction.checkNull(data.get(4))).trim().equalsIgnoreCase(""))	
					{
						Number amount =myFormatter.parse((CommonFunction.checkNull(data.get(4))).trim());
						LevelDefVo.setAmountFromSearch(myFormatter.format(amount));
						LevelDefVo.setAmountFrom(myFormatter.format(amount));
					}
					if(!(CommonFunction.checkNull(data.get(5))).trim().equalsIgnoreCase(""))	
					{
						Number amount =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
						LevelDefVo.setAmountToSearch(myFormatter.format(amount));
						LevelDefVo.setAmountTo(myFormatter.format(amount));
					}
					LevelDefVo.setStatus(CommonFunction.checkNull(data.get(6)));
			
					LevelDefVo.setTotalRecordSize(count);
					detailList.add(LevelDefVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;


	}
	
	public String saveUWApproval(ApprovalLevelDefVo Vo,String productModify){
		logger.info("in saveUWApproval method::::::::::::::::::::::::::"+productModify);
			

		String provcal="";
		boolean status = false;
			
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

     			ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				String s1="";
				String s2="";	
								
				try
				{ 
					
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(productModify).trim())); 
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getDecision()).trim())); 
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getComments()).trim())); 					
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getMakerId()).trim()));
					
					logger.info("saurabh singh:::::::::::::::::"+productModify);
					logger.info("saurabh singh:::::::::::::::::"+Vo.getDecision());
					logger.info("saurabh singh:::::::::::::::::"+Vo.getComments());
					logger.info("saurabh singh:::::::::::::::::"+Vo.getMakerId());
					
					String date=CommonFunction.changeFormat(CommonFunction.checkNull(Vo.getMakerDate()).trim());
					logger.info("date is::::::::::::::::"+date);
					in.add(date);					
									
					out.add(s1);
					out.add(s2);			
					logger.info("UW_APPROVAL_AUTHOR("+in.toString()+","+out.toString()+")");
					outMessages=(ArrayList) ConnectionDAOforEJB.callSP("UW_APPROVAL_AUTHOR",in,out);
					s1=CommonFunction.checkNull(outMessages.get(0));
					s2=CommonFunction.checkNull(outMessages.get(1));
					logger.info("value of s1::::::::"+s1);
					logger.info("value of s1::::::::"+s2);
					
		            if(s1.equalsIgnoreCase("S"))
		            {
		            	status=true;
		            	provcal=s1;
		            }
		            else{
		            	status=false;
		            	provcal=s2;
		            }
		            
				}
			     
				catch (Exception e) {
						e.printStackTrace();				
				    }			
				finally{
					s1=null;
					s2=null;
					in.clear();
					in=null;
					out.clear();
					out=null;
					outMessages.clear();
					outMessages=null;					
				}					
			return provcal;		
	}
	// saurabh changes ends
	//mradul
	

	public ArrayList getVehicleSegment(){
			ArrayList list = new ArrayList();
			try {
				logger.info("In getVehicleSegment..........................DAOImpl");
				String query = "SELECT VALUE,DESCRIPTION FROM generic_master WHERE REC_STATUS='A' and generic_key='VEHICLE_SEGMENT'";
				MakeModelmasterVO vo = null;
				logger.info("vehicle category query......"+query);
				ArrayList vehicle = ConnectionDAOforEJB.sqlSelect(query);
				
				logger.info("get vehicle size" + vehicle.size());
				
				for (int i = 0; i < vehicle.size(); i++) {
					logger.info("vehicle "+ CommonFunction.checkNull(vehicle.get(i)).toString());
					
					ArrayList data = (ArrayList) vehicle.get(i);
					for (int k = 0; k < data.size(); k++) {
						logger.info("vehicle:::::::: "+ CommonFunction.checkNull(data.get(k)).toString());
						
						vo = new MakeModelmasterVO();
						vo.setVehicleValue((String) data.get(0).toString());
						vo.setVehicleDesc((String) data.get(1).toString());
					}
					list.add(vo);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;

	}

	public boolean saveScoringBenchmarkMaster(ScoringBenchmarkMasterVo vo) {
		
		boolean status = false;
        String result="";
    	String[] NumericValueFrom=vo.getNumericValueFrom();
		String[] NumericValueTo=vo.getNumericValueTo();
		String[] CharacterValue=vo.getCharacterValue();
		String[] Rating=vo.getRating();
		logger.info("NumericValueFrom::::"+ NumericValueFrom);
		logger.info("NumericValueTo::::"+ NumericValueTo);
		logger.info("CharacterValue::::"+ CharacterValue);
		logger.info("Rating::::"+ Rating);
	
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String recStatus = "A";
		String dataType=vo.getDataType();
		logger.info("dataType::::"+ dataType);
		try 
		{				
			logger.info("In insert saveScoringBenchmarkMaster");
			StringBuffer bufInsSql = new StringBuffer();
			ArrayList qryList1 = new ArrayList();
			bufInsSql.append("delete from CR_SCORECARD_VALUES where 'a'='a' ");
			if(!(CommonFunction.checkNull(vo.getLbxIndustryID()).equalsIgnoreCase("")))
			{
				bufInsSql.append(" and VALUE_CHAIN_ID='"+(CommonFunction.checkNull(vo.getLbxIndustryID())).trim()+"' ");
		   }
			if(!(CommonFunction.checkNull(vo.getLbxScorecardID()).equalsIgnoreCase("")))
			{
				bufInsSql.append(" and SCORING_PARAM_CODE='"+(CommonFunction.checkNull(vo.getLbxScorecardID())).trim()+"' ");
			}
			logger.info("Scorelist Details query......"+bufInsSql);
			qryList1.add(bufInsSql);
			status = ConnectionDAOforEJB.sqlInsUpdDelete(qryList1);
			for(int i=0;i<Rating.length && status ;i++)
			{				
				insertPrepStmtObject=null;
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql=null;
				bufInsSql = new StringBuffer();
				bufInsSql.append("insert into CR_SCORECARD_VALUES (VALUE_CHAIN_ID,SCORING_PARAM_CODE,WEIGHTAGE,");
				if(CommonFunction.checkNull(dataType).equalsIgnoreCase("N"))
				{
					bufInsSql.append("NUMERIC_VALUE_FROM,NUMERIC_VALUE_TO,");
				}else{
					bufInsSql.append("CHARACTER_VALUE,");
				}
				bufInsSql.append("RATING,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,?,?,");
				if(CommonFunction.checkNull(dataType).equalsIgnoreCase("N"))
				{
					bufInsSql.append("?,?,"); 
				}else{
					bufInsSql.append("?,");
				}
				bufInsSql.append("?,?,");
				bufInsSql.append(" ?,"); // MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");// MAKER_DATE
				
				if (CommonFunction.checkNull(vo.getLbxIndustryID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxIndustryID().trim());
				
				if (CommonFunction.checkNull(vo.getLbxScorecardID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxScorecardID().trim());
				
				if (CommonFunction.checkNull(vo.getWeightage())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getWeightage().trim());
				if(CommonFunction.checkNull(dataType).equalsIgnoreCase("N"))
				{
				
				if (CommonFunction.checkNull(NumericValueFrom[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(NumericValueFrom[i]);
					
				if (CommonFunction.checkNull(NumericValueTo[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(NumericValueTo[i]);
				}else{
				if (CommonFunction.checkNull(CharacterValue[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CharacterValue[i]);
				}
				if (CommonFunction.checkNull(Rating[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(Rating[i]);
				if (CommonFunction.checkNull(recStatus)
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(recStatus);
				if (CommonFunction.checkNull(vo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveBenchMarkMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);				
			}
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);	
			logger.info("IN status insert  "+ status);
			if(status)
				result="datasaved";
				else
				result="datanotsaved";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
		}	
	public ArrayList getScorelistDetails(ScoringBenchmarkMasterVo vo ) {
		ArrayList list = new ArrayList();
		try {
			
			String dataType=vo.getDataType();
			String ScorecardID=vo.getLbxScorecardID();
			String IndustryID=vo.getLbxIndustryID();
			String Weightage=vo.getWeightage();
			logger.info("dataType::::"+ dataType);
			logger.info("ScorecardID::::"+ ScorecardID);
			logger.info("IndustryID::::"+ IndustryID);
			logger.info("Weightage::::"+ Weightage);
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			if(CommonFunction.checkNull(dataType).equalsIgnoreCase("N"))
			{
				bufInsSql.append("select NUMERIC_VALUE_FROM,NUMERIC_VALUE_TO,RATING from CR_SCORECARD_VALUES where 'a'='a' ");
				bufInsSqlTempCount.append("select count(1) from CR_SCORECARD_VALUES where 'a'='a' ");
				
				if(!(CommonFunction.checkNull(vo.getLbxIndustryID()).equalsIgnoreCase("")))
				{
					bufInsSql.append(" and VALUE_CHAIN_ID='"+(CommonFunction.checkNull(vo.getLbxIndustryID())).trim()+"' ");
					bufInsSqlTempCount.append(" and VALUE_CHAIN_ID='"+(CommonFunction.checkNull(vo.getLbxIndustryID())).trim()+"' "); 
				}
				if(!(CommonFunction.checkNull(vo.getLbxScorecardID()).equalsIgnoreCase("")))
				{
					bufInsSql.append(" and SCORING_PARAM_CODE='"+(CommonFunction.checkNull(vo.getLbxScorecardID())).trim()+"' ");
					bufInsSqlTempCount.append(" and SCORING_PARAM_CODE='"+(CommonFunction.checkNull(vo.getLbxScorecardID())).trim()+"' "); 
				}
				if(!(CommonFunction.checkNull(vo.getWeightage()).equalsIgnoreCase("")))
				{
					bufInsSql.append(" and WEIGHTAGE='"+(CommonFunction.checkNull(vo.getWeightage())).trim()+"' ");
					bufInsSqlTempCount.append(" and WEIGHTAGE='"+(CommonFunction.checkNull(vo.getWeightage())).trim()+"' "); 
				}
				logger.info("Scorelist Details query......"+bufInsSql);
				logger.info("Scorelist Details count query......"+bufInsSqlTempCount);
				ArrayList ScoreList = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
				
				logger.info("get ScoreList size" + ScoreList.size());
			
				for (int i = 0; i < ScoreList.size(); i++) {
				logger.info("ScoreList "+ CommonFunction.checkNull(ScoreList.get(i)).toString());
				
					ArrayList data = (ArrayList) ScoreList.get(i);
					if (data.size() > 0) 
					{
						ScoringBenchmarkMasterVo vo1=new ScoringBenchmarkMasterVo();			
						vo1.setNumericValueFrom1(CommonFunction.checkNull(data.get(0)));
						vo1.setNumericValueTo1(CommonFunction.checkNull(data.get(1)));
						vo1.setRating1(CommonFunction.checkNull(data.get(2)));
						list.add(vo1);
				}
				}

			}else{
				
				bufInsSql.append("select CHARACTER_VALUE,RATING from CR_SCORECARD_VALUES where 'a'='a' ");
				bufInsSqlTempCount.append("select count(1) from CR_SCORECARD_VALUES where 'a'='a' ");
	
				if(!(CommonFunction.checkNull(vo.getLbxIndustryID()).equalsIgnoreCase("")))
				{
					bufInsSql.append(" and VALUE_CHAIN_ID='"+(CommonFunction.checkNull(vo.getLbxIndustryID())).trim()+"' ");
					bufInsSqlTempCount.append(" and VALUE_CHAIN_ID='"+(CommonFunction.checkNull(vo.getLbxIndustryID())).trim()+"' "); 
				}
				if(!(CommonFunction.checkNull(vo.getLbxScorecardID()).equalsIgnoreCase("")))
				{
					bufInsSql.append(" and SCORING_PARAM_CODE='"+(CommonFunction.checkNull(vo.getLbxScorecardID())).trim()+"' ");
					bufInsSqlTempCount.append(" and SCORING_PARAM_CODE='"+(CommonFunction.checkNull(vo.getLbxScorecardID())).trim()+"' "); 
				}
				if(!(CommonFunction.checkNull(vo.getWeightage()).equalsIgnoreCase("")))
				{
					bufInsSql.append(" and WEIGHTAGE='"+(CommonFunction.checkNull(vo.getWeightage())).trim()+"' ");
					bufInsSqlTempCount.append(" and WEIGHTAGE='"+(CommonFunction.checkNull(vo.getWeightage())).trim()+"' "); 
				}
				logger.info("Scorelist Details query......"+bufInsSql);
				logger.info("Scorelist Details count query......"+bufInsSqlTempCount);
				ArrayList ScoreList = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("get ScoreList size" + ScoreList.size());
			
			for (int i = 0; i < ScoreList.size(); i++) 
			{
				ArrayList data = (ArrayList) ScoreList.get(i);
				if (data.size() > 0) 
				{
					ScoringBenchmarkMasterVo vo1=new ScoringBenchmarkMasterVo();		
					vo1.setCharacterValue1(CommonFunction.checkNull(data.get(0).toString()));
					vo1.setRating1(CommonFunction.checkNull(data.get(1).toString()));
					list.add(vo1);
				}
				
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		}
	
	//Manish Rate Approval Maker work space start
	
	public ArrayList rateApprovalMakerSearch(Object ob) {
		String product = "";
		String scheme = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		RateApprovalVo rateApprovalVo = (RateApprovalVo) ob;
		ArrayList<RateApprovalVo> detailList = new ArrayList<RateApprovalVo>();
		try {

			logger.info("In searchDealerData.............inside ejb server file...........Dao Impl"+rateApprovalVo.getSearchByStatus());
			product = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(rateApprovalVo.getLbxProductID())).trim());
			scheme = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(rateApprovalVo.getLbxScheme())).trim());
			

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT RATE_APPROVAL_ID,CRM.PRODUCT_ID,CRM.SCHEME_ID,CRM.RACK_RATE,CRM.RACK_PROCESSING_FEE,CPM.PRODUCT_DESC,CSM.SCHEME_DESC ");
			bufInsSql.append("FROM CR_RATE_MATRIX_M CRM JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = CRM.PRODUCT_ID) ");
			bufInsSql.append("JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = CRM.SCHEME_ID) WHERE CRM.REC_STATUS='"+CommonFunction.checkNull(rateApprovalVo.getSearchByStatus())+"'");			
			bufInsSqlTempCount.append(" SELECT COUNT(1) FROM CR_RATE_MATRIX_M CRM JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = CRM.PRODUCT_ID) " );
			bufInsSqlTempCount.append(" JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = CRM.SCHEME_ID) WHERE CRM.REC_STATUS='"+CommonFunction.checkNull(rateApprovalVo.getSearchByStatus())+"' ");
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(product)).trim().equalsIgnoreCase("")))) {
			 bufInsSql.append(" AND CRM.PRODUCT_ID='"+product+"' ");
			 bufInsSqlTempCount.append(" AND CRM.PRODUCT_ID='"+product+"' "); 
			     }
				 
				
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(scheme)).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND CRM.SCHEME_ID='"+scheme+"' ");
				bufInsSqlTempCount.append(" AND CRM.SCHEME_ID='"+scheme+"'");

			}
			
						
			logger.info("search Query...." + bufInsSql);

			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			
			logger.info("current PAge Link no .................... "+rateApprovalVo.getCurrentPageLink());
			if(rateApprovalVo.getCurrentPageLink()> 1)
			{
				startRecordIndex = (rateApprovalVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY RATE_APPROVAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			
			logger.info("query : "+bufInsSql);
		
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchSubDealerData() search query1 ### " + bufInsSql.toString());
			logger.info("In searchSubDealerData.....................................Dao Impl");
			logger.info("searchSubDealerData "+searchlist);
			logger.info("searchlist.size()  "+searchlist.size());
		

			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					RateApprovalVo searchDataVo = new RateApprovalVo();

					searchDataVo.setRateApprovalId("<a href=rateMatrixMakerDispatchAction.do?method=rateApprovalMakerOpenEdit&product="
									+ CommonFunction.checkNull(data.get(1)).toString() +"&scheme="+ CommonFunction.checkNull(data.get(2)).toString()+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					searchDataVo.setRackRate(CommonFunction.checkNull(data.get(3)).toString());
					searchDataVo.setRackProcessingFee(CommonFunction.checkNull(data.get(4)).toString());
					searchDataVo.setProduct(CommonFunction.checkNull(data.get(5)).toString());
					searchDataVo.setScheme(CommonFunction.checkNull(data.get(6)).toString());
					searchDataVo.setTotalRecordSize(count);
					detailList.add(searchDataVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("detailList size : "+detailList);
		return detailList;
	}


	public boolean saveRateApprovalMakerData(Object ob) {
		RateApprovalVo vo = (RateApprovalVo) ob;
		boolean status = false;
		
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		String stat = "X";

		String query = "SELECT PRODUCT_ID FROM CR_RATE_MATRIX_M WHERE PRODUCT_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxProductID().trim()) + "'" + "AND SCHEME_ID='" + StringEscapeUtils.escapeSql(vo.getLbxScheme().trim()) + "'";
		logger.info("In saveRateApprovalMakerData.................Dao Impl" + query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		
		logger.info("## In saveRateApprovalMakerData() : st : ==>> " + st);
		
		try {
			
			
			
				logger.info("## In saveRateApprovalMakerData() : .........");
				
				if(!st){
				
				StringBuilder bufInsSql = new StringBuilder();
		//		bufInsSql.append("INSERT INTO CR_RATE_MATRIX_M (PRODUCT_ID,SCHEME_ID,CREDIT_INSURANCE_COVER,TYPE_OF_COVERAGE,DATE_OF_BIRTH,SUM_ASSURED_LOAN_AMT,CREDIT_INSURANCE_TENOR,CSLI_PREMIUM_AMOUNT,GENERAL_INSURANCE,CSGI_PREMIUM_AMOUNT,OTHER_INSURANCE_SOLD,PRODUCT_SOLD,CSOI_PREMIUM_AMOUNT,TRANSACTION_FEE,REC_STATUS,MAKER_ID,MAKER_DATE,RACK_RATE,RACK_PROCESSING_FEE) ");
				bufInsSql.append("INSERT INTO CR_RATE_MATRIX_M (PRODUCT_ID,SCHEME_ID,RACK_RATE,RACK_PROCESSING_FEE,REC_STATUS,MAKER_ID,MAKER_DATE) ");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //PRODUCT_ID 1
				bufInsSql.append(" ?,"); //SCHEME_ID 2
				bufInsSql.append(" ?,"); // RACK_RATE 3,
				bufInsSql.append(" ?,"); //RACK_PROCESSING_FEE 4
				bufInsSql.append(" ?,"); //REC_STATUS, 5
				bufInsSql.append(" ?,"); //MAKER_ID, 6
				bufInsSql.append(dbo);   //7
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))"); //MAKER_DATE,
				
				//1
				if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxProductID().toUpperCase().trim());
				//2
				
				if (CommonFunction.checkNull(vo.getLbxScheme()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxScheme().toUpperCase().trim());
				
				//3
				if((CommonFunction.checkNull(vo.getRackRate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					try {
						insertPrepStmtObject.addString(myFormatter.parse(vo.getRackRate()).toString());
					} catch (ParseException e1) {
						
						e1.printStackTrace();
					}
				//4
					if((CommonFunction.checkNull(vo.getRackProcessingFee())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						try {
							insertPrepStmtObject.addString(myFormatter.parse(vo.getRackProcessingFee()).toString());
						} catch (ParseException e1) {
							
							e1.printStackTrace();
						}
				
				//5
				if (CommonFunction.checkNull(vo.getSaveForwardFlag()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				
				else
					insertPrepStmtObject.addString(vo.getSaveForwardFlag());
				
				//6
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId().toUpperCase().trim());
				//7
				
				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate().toUpperCase().trim());
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN rateApprovalMakerMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
	
		
				
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In saveRateApprovalData......................"+ status);
	}//end of if	

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			qryList.clear();
			qryList = null;
			
		         }
		return status;

	}
	
	public ArrayList openEditRateApprovalMakerData(Object ob) {

		ArrayList searchlist = new ArrayList();
		RateApprovalVo vo = (RateApprovalVo)ob;
		ArrayList<RateApprovalVo> rateList = new ArrayList<RateApprovalVo>();
		

		try {
			

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT CRM.PRODUCT_ID,CRM.SCHEME_ID,CPM.PRODUCT_DESC,CSM.SCHEME_DESC," );
			bufInsSql.append(" CRM.RACK_RATE,CRM.RACK_PROCESSING_FEE FROM CR_RATE_MATRIX_M CRM  ");
			bufInsSql.append(" JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = CRM.PRODUCT_ID) ");
			bufInsSql.append(" JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = CRM.SCHEME_ID) ");
			bufInsSql.append(" WHERE CRM.REC_STATUS <> 'X' AND CRM.PRODUCT_ID = '"+StringEscapeUtils.escapeSql(vo.getLbxProductID())+"' AND CRM.SCHEME_ID ='"+StringEscapeUtils.escapeSql(vo.getLbxScheme())+"'");
			
			logger.info("## In openEditRateApprovalMakerData() : Select query of CR_RATE_MATRIX_M : ==>> " + bufInsSql.toString());

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			int listSize = searchlist.size();
			
			logger.info("## In openEditRateApprovalMakerData() : listSize : ==>> " + listSize);
			
			for (int i = 0; i < listSize; i++) {
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					
					RateApprovalVo rateVo = new RateApprovalVo();
					
					rateVo.setLbxProductID(CommonFunction.checkNull(data.get(0)).toString());
					rateVo.setLbxScheme(CommonFunction.checkNull(data.get(1)).toString());
					rateVo.setProduct(CommonFunction.checkNull(data.get(2)).toString());
					rateVo.setScheme(CommonFunction.checkNull(data.get(3)).toString());	
					
					
					if(CommonFunction.checkNull(data.get(4)).trim().equalsIgnoreCase(""))
					{
						rateVo.setRackRate("");
					}
					else
					{
						rateVo.setRackRate(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(4)))));
					}
					
					
					if(CommonFunction.checkNull(data.get(5)).trim().equalsIgnoreCase(""))
					{
						rateVo.setRackProcessingFee("");
					}
					else
					{
						rateVo.setRackProcessingFee(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(5)))));
					}
					rateList.add(rateVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rateList;
}
	
	public boolean updateRateApprovalMakerData(Object ob) {
		
		RateApprovalVo vo = (RateApprovalVo) ob;
		
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		

		boolean status = false;
		String saveForwardFlag = CommonFunction.checkNull(vo.getSaveForwardFlag());
		
		logger.info("## IN updateRateApprovalMakerData(): saveForwardFlag :==>> "+saveForwardFlag); 

		try {
			
			
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateCountryData..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE CR_RATE_MATRIX_M SET RACK_RATE=?,RACK_PROCESSING_FEE=?, ");
			bufInsSql.append(" REC_STATUS = ?, ");
			bufInsSql.append(" MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where PRODUCT_ID=? AND SCHEME_ID=?");
			
			if (CommonFunction.checkNull(vo.getRackRate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(vo.getRackRate()).toString());
			
			
			if (CommonFunction.checkNull(vo.getRackProcessingFee())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(vo.getRackProcessingFee()).toString());
			
			if (CommonFunction.checkNull(vo.getSaveForwardFlag())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getSaveForwardFlag()
						.toUpperCase().trim());

			
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			
			
			//---------------------------------- 

			if (CommonFunction.checkNull(vo.getLbxProductID())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxProductID());
			
			if (CommonFunction.checkNull(vo.getLbxScheme())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxScheme());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			
			
			logger.info("## In updateRateApprovalMakerData() : update query (CR_RATE_MATRIX_M) : ==>> "+insertPrepStmtObject.printQuery());
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			
		

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	//Manish work space end 
	
	// Manish Rate Approval Checker 
	public ArrayList rateApprovalCheckerSearch(Object ob) {
		String product = "";
		String scheme = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		RateApprovalVo rateApprovalVo = (RateApprovalVo) ob;
		ArrayList<RateApprovalVo> detailList = new ArrayList<RateApprovalVo>();
		try {

			logger.info("In searchDealerData.............inside ejb server file...........Dao Impl");
			product = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(rateApprovalVo.getLbxProductID())).trim());
			scheme = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(rateApprovalVo.getLbxScheme())).trim());
			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT RATE_APPROVAL_ID,CRM.PRODUCT_ID,CRM.SCHEME_ID,CRM.RACK_RATE,CRM.RACK_PROCESSING_FEE,CPM.PRODUCT_DESC,CSM.SCHEME_DESC ");
			bufInsSql.append("FROM CR_RATE_MATRIX_M CRM JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = CRM.PRODUCT_ID) ");
			bufInsSql.append("JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = CRM.SCHEME_ID) WHERE CRM.REC_STATUS='F' ");
			bufInsSql.append(" AND CRM.MAKER_ID <> '"+CommonFunction.checkNull(rateApprovalVo.getMakerId())+"'");
			bufInsSqlTempCount.append(" SELECT COUNT(1) FROM CR_RATE_MATRIX_M CRM JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = CRM.PRODUCT_ID) " );
			bufInsSqlTempCount.append(" JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = CRM.SCHEME_ID) WHERE CRM.REC_STATUS='F' ");
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(product)).trim().equalsIgnoreCase("")))) {
			 bufInsSql.append(" AND CPM.product_id='"+product+"' ");
			 bufInsSqlTempCount.append(" AND CPM.product_id='"+product+"' "); 
			     }
				 
				
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(scheme)).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND CRM.SCHEME_ID='"+scheme+"' ");
				bufInsSqlTempCount.append(" AND CRM.SCHEME_ID='"+scheme+"'");

			}
			
						
			logger.info("search Query...." + bufInsSql);

			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((subDealerSearchCode.trim()==null && subDealerSearchDesc.trim()==null) || (subDealerSearchCode.trim().equalsIgnoreCase("") && subDealerSearchDesc.trim().equalsIgnoreCase("")) || subDealerMasterVo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+rateApprovalVo.getCurrentPageLink());
			if(rateApprovalVo.getCurrentPageLink()> 1)
			{
				startRecordIndex = (rateApprovalVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY RATE_APPROVAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				
//			}
			logger.info("query : "+bufInsSql);
		
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchSubDealerData() search query1 ### " + bufInsSql.toString());
			logger.info("In searchSubDealerData.....................................Dao Impl");
			logger.info("searchSubDealerData "+searchlist);
			logger.info("searchlist.size()  "+searchlist.size());
		

			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					RateApprovalVo searchDataVo = new RateApprovalVo();

					searchDataVo.setRateApprovalId("<a href=rateMatrixCheckerDispatchAction.do?method=rateApprovalCheckerOpen&product="
									+ CommonFunction.checkNull(data.get(1)).toString() +"&scheme="+ CommonFunction.checkNull(data.get(2)).toString()+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					searchDataVo.setRackRate(CommonFunction.checkNull(data.get(3)).toString());
					searchDataVo.setRackProcessingFee(CommonFunction.checkNull(data.get(4)).toString());
					searchDataVo.setProduct(CommonFunction.checkNull(data.get(5)).toString());
					searchDataVo.setScheme(CommonFunction.checkNull(data.get(6)).toString());
					searchDataVo.setTotalRecordSize(count);
					detailList.add(searchDataVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("detailList size : "+detailList);
		return detailList;
	}

	public ArrayList openRateApprovalCheckerData(Object ob) {

		ArrayList searchlist = new ArrayList();
		RateApprovalVo vo = (RateApprovalVo)ob;
		ArrayList<RateApprovalVo> rateList = new ArrayList<RateApprovalVo>();
		

		try {
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT CRM.PRODUCT_ID,CRM.SCHEME_ID,CPM.PRODUCT_DESC,CSM.SCHEME_DESC," );
			bufInsSql.append(" RACK_RATE, RACK_PROCESSING_FEE FROM CR_RATE_MATRIX_M CRM  ");
			bufInsSql.append(" JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = CRM.PRODUCT_ID) ");
			bufInsSql.append(" JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = CRM.SCHEME_ID) ");
			bufInsSql.append(" WHERE CRM.REC_STATUS = 'F' AND CRM.PRODUCT_ID = '"+StringEscapeUtils.escapeSql(vo.getLbxProductID())+"' AND CRM.SCHEME_ID ='"+StringEscapeUtils.escapeSql(vo.getLbxScheme())+"'");
			
			logger.info("## In openRateApprovalCheckerData() : Select query of CR_RATE_MATRIX_M : ==>> " + bufInsSql.toString());

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			int listSize = searchlist.size();
			
			logger.info("## In openRateApprovalCheckerData() : listSize : ==>> " + listSize);
			
			for (int i = 0; i < listSize; i++) {
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					
					RateApprovalVo rateVo = new RateApprovalVo();
					
					rateVo.setLbxProductID(CommonFunction.checkNull(data.get(0)).toString());
					rateVo.setLbxScheme(CommonFunction.checkNull(data.get(1)).toString());
					rateVo.setProduct(CommonFunction.checkNull(data.get(2)).toString());
					rateVo.setScheme(CommonFunction.checkNull(data.get(3)).toString());	
					if(CommonFunction.checkNull(data.get(4)).trim().equalsIgnoreCase(""))
					{
						rateVo.setRackRate("");
					}
					else
					{
						rateVo.setRackRate(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(4)))));
					}
					
					
					if(CommonFunction.checkNull(data.get(5)).trim().equalsIgnoreCase(""))
					{
						rateVo.setRackProcessingFee("");
					}
					else
					{
						rateVo.setRackProcessingFee(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(5)))));
					}
					
						
					rateList.add(rateVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rateList;
}
	
	public boolean saveRateApprovalChecker(Object ob) {
		RateApprovalVo vo = (RateApprovalVo) ob;
		//String  = (String) vo.getLoanId();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		

		boolean status = false;
		String saveForwardFlag = CommonFunction.checkNull(vo.getSaveForwardFlag());
		
		logger.info("## IN saveRateApprovalChecker(): saveForwardFlag :==>> "+saveForwardFlag); 
		

		try {
			
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateCountryData..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE CR_RATE_MATRIX_M SET AUTHOR_REMARKS=?, " );
			bufInsSql.append(" AUTHOR_ID=?, ");
			bufInsSql.append("AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
			bufInsSql.append(" REC_STATUS=? ");
			bufInsSql.append(" where PRODUCT_ID=? AND SCHEME_ID=?");
			
			if (CommonFunction.checkNull(vo.getComments())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getComments()
						.toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(vo.getMakerId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId()
						.toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate()
						.toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(vo.getDecision())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDecision()); 
					
			if (CommonFunction.checkNull(vo.getLbxProductID())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxProductID()); 
			
			if (CommonFunction.checkNull(vo.getLbxScheme())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxScheme()); 

			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			
			logger.info("## saveRateApprovalChecker : update query (cr_rate_matrix_m) "+insertPrepStmtObject.printQuery());
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	// Manish for scheme Master
	public ArrayList<CrSchemeMasterVo> getBranchIdsDao(String schemeId) 
	{
		logger.info("In getBranchIdsDao..........................DAOImpl");
		ArrayList list = new ArrayList();
		CrSchemeMasterVo vo=null;
		ArrayList data=null;
		try 
		{
				String query = " SELECT A.BRANCH_ID,B.BRANCH_DESC " +
						" FROM cr_scheme_branch_mapping_dtl A " +
						" JOIN com_branch_m B ON A.BRANCH_ID=B.BRANCH_ID " +
						" WHERE A.REC_STATUS='A' AND B.REC_STATUS='A' AND SCHEME_ID='"+CommonFunction.checkNull(schemeId).trim()+"' ";
				logger.info("getBranchIdsDao......"+query);
				ArrayList branchList = ConnectionDAOforEJB.sqlSelect(query);
				for (int i = 0; i < branchList.size(); i++) 
				{									
					data = (ArrayList) branchList.get(i);
					vo = new CrSchemeMasterVo();
					vo.setBranchId(CommonFunction.checkNull(data.get(0)).toString());
					vo.setBranchDesc(CommonFunction.checkNull(data.get(1)).toString());
					list.add(vo);
				}
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		finally
		{
			vo=null;
			data=null;			
		}
		return list;

	}
	

	@Override
	public boolean deleteBranchData(Object ob) {
		
		boolean status=false;
		UserMasterVo userMasterVo = (UserMasterVo) ob;
		String userId = (String) userMasterVo.getUserId();
		PrepStmtObject deletePrepStmtObject = new PrepStmtObject();
		StringBuilder bufInsSql = new StringBuilder();
		ArrayList deleteList=new ArrayList();
		try
		{
		
		
		bufInsSql.append(" DELETE FROM SEC_USER_BRANCH_DTL WHERE USER_ID=? ") ;
		
		if (CommonFunction.checkNull(userId).trim().equalsIgnoreCase(""))
			deletePrepStmtObject.addNull();
		else
			deletePrepStmtObject.addString(CommonFunction.checkNull(userId).trim());

		deletePrepStmtObject.setSql(bufInsSql.toString());
		
		logger.info("IN deleteBranchData() delete query1 ### "+ deletePrepStmtObject.printQuery());
		
		deleteList.add(deletePrepStmtObject);
	

    	 status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(deleteList);

		logger.info("In deleteBranchData() DeleteQuery.................MasterDaoImpl::"+ status);
		}
		catch(Exception e)
		{
			logger.info("In deleteBranchData() DeleteQuery..........Exception.......MasterDaoImpl::"+e);
		}
		finally
		{
			deleteList=null;
			deletePrepStmtObject=null;
			bufInsSql=null;
			userMasterVo=null;
			ob=null;
			userId=null;
		}
		return status;
	}
	@Override
	public boolean deleteLevelData(Object ob) {
		
		boolean status=false;
		UserMasterVo userMasterVo = (UserMasterVo) ob;
		String userId = (String) userMasterVo.getUserId();
		PrepStmtObject deletePrepStmtObject = new PrepStmtObject();
		StringBuilder bufInsSql = new StringBuilder();
		ArrayList deleteList=new ArrayList();
		try
		{
				
		bufInsSql.append(" DELETE FROM sec_user_level_dtl WHERE USER_ID=? ") ;
		
		if (CommonFunction.checkNull(userId).trim().equalsIgnoreCase(""))
			deletePrepStmtObject.addNull();
		else
			deletePrepStmtObject.addString(CommonFunction.checkNull(userId).trim());

		deletePrepStmtObject.setSql(bufInsSql.toString());
		
		logger.info("IN deleteLevelData() delete query1 ### "+ deletePrepStmtObject.printQuery());
		
		deleteList.add(deletePrepStmtObject);
	

    	 status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(deleteList);

		logger.info("In deleteLevelData() DeleteQuery.................MasterDaoImpl::"+ status);
		}
		catch(Exception e)
		{
			logger.info("In deleteLevelData() DeleteQuery..........Exception.......MasterDaoImpl::"+e);
		}
		finally
		{
			deleteList=null;
			deletePrepStmtObject=null;
			bufInsSql=null;
			userMasterVo=null;
			ob=null;
			userId=null;
		}
		return status;
	}
	
	//sachin balyan
	public String saveSalesExecutive(Object ob) {
		SalesExecutiveMasterVo vo = (SalesExecutiveMasterVo)ob;
		boolean status = false;
	    String result=null;
	    logger.info("In saveSalesExecutive.........inside ejb server file...........Dao Impl recStatus"+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

		
		try {

				if (vo.getRecStatus() != null && vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert saveBusinessMonthClosureDetails");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into com_bp_employee_m (EMPLOYEE_NAME,EMPLOYEE_TYPE,BUSSINESS_PARTNER_ID,BANK_ACCOUNT_NO,BANK_ID,BANK_BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //EMPLOYEE_NAME
				bufInsSql.append(" ?,"); //EMPLOYEE_TYPE
				bufInsSql.append(" ?,"); //BUSSINESS_PARTNER_ID
				bufInsSql.append(" ?,");//BANK_ACCOUNT_NO
				bufInsSql.append(" ?,");//BANK_ID
				bufInsSql.append(" ?,");//BANK_BRANCH_ID
				bufInsSql.append(" ?,"); //REC_STATUS	
			    bufInsSql.append(" ?,");// MAKER_ID
			    bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");//MAKER_DATE

				 bufInsSql.append(" ?,");// AUTHOR_ID
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ) ");//Author_DATE

				
				if (CommonFunction.checkNull(vo.getEmployeeName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEmployeeName());
				
				if (CommonFunction.checkNull(vo.getEmployeeType())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEmployeeType());
				
				
				if (CommonFunction.checkNull(vo.getLbxBusinessPartnerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBusinessPartnerId());
				
				if (CommonFunction.checkNull(vo.getBankAccountNo())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getBankAccountNo());
				
				if (CommonFunction.checkNull(vo.getLbxBankID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBankID());
				
				if (CommonFunction.checkNull(vo.getLbxBranchID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBranchID());
				
					if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);
					
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(vo.getMakerId());
				
				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				   insertPrepStmtObject.addNull();
				else
				   insertPrepStmtObject.addString(vo.getMakerId());
				
				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				   insertPrepStmtObject.addNull();
				else
				  insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveSalesExecutive() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);				
				if(status)
					result="datasaved";
					else
					result="datanotsaved";

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;

		
	}
	
	
	public String updateSalesExecutive(Object ob) {
		SalesExecutiveMasterVo vo = (SalesExecutiveMasterVo) ob;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();	
		//request.setAttribute("businessMonth",businessMonth);
		 String result="";
		 boolean status = false;
		String stat = "";
		try {
			if (true) {
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			StringBuffer bufInsSql = new StringBuffer();				
		
				insertPrepStmtObject=null;
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql=null;
				bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE com_bp_employee_m set EMPLOYEE_NAME=?,EMPLOYEE_TYPE=?,BUSSINESS_PARTNER_ID=?,BANK_ACCOUNT_NO=?,BANK_ID=?,BANK_BRANCH_ID=?,");
				bufInsSql.append(" REC_STATUS=?,MAKER_ID=?,MAKER_DATE=" );
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
				bufInsSql.append(" AUTHOR_ID=?,AUTHOR_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)  ");
				
				if (CommonFunction.checkNull(vo.getEmployeeName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEmployeeName());
				
				if (CommonFunction.checkNull(vo.getEmployeeType())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEmployeeType());
				
				
				if (CommonFunction.checkNull(vo.getLbxBusinessPartnerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBusinessPartnerId());
				if (CommonFunction.checkNull(vo.getBankAccountNo())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getBankAccountNo());
				
				if (CommonFunction.checkNull(vo.getLbxBankID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBankID());
				
				if (CommonFunction.checkNull(vo.getLbxBranchID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBranchID());
				
			
			    	if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);
					
					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(vo.getMakerId());
					if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
			
			    if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(	""))
				    insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(vo.getMakerId());
			  
				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
			

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN updateSalesExecutive() update query1 ### "
						+ insertPrepStmtObject.printQuery());
				updatelist.add(insertPrepStmtObject);
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);	

			if(status){
				result="saved";
			}else{
				result="notsaved";
			}
			
	    }						
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	

	public ArrayList<SalesExecutiveMasterVo> getSalesExecutiveList(Object ob) {
		ArrayList searchlist = new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
	
		SalesExecutiveMasterVo vo = (SalesExecutiveMasterVo) ob;
		ArrayList<SalesExecutiveMasterVo>  detailList = new ArrayList<SalesExecutiveMasterVo> ();
		SalesExecutiveMasterVo salesExecutiveMasterVo=null;
		
		try {
			logger.info("In getSalesExecutiveList...............");
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer lastRecord = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();

			bufInsSql.append(" SELECT BPE.BP_EMP_ID, BPE.EMPLOYEE_NAME,BPE.EMPLOYEE_TYPE,BPE.BUSSINESS_PARTNER_ID,DSA.DEALER_DESC,BPE.BANK_ACCOUNT_NO,BPE.BANK_ID,B.BANK_NAME,BPE.BANK_BRANCH_ID,BR.BANK_BRANCH_NAME,BR.BRANCH_IFCS_CODE,BR.BRANCH_MICR_CODE,case when BPE.REC_STATUS='A' then 'Active' else 'Inactive' end AS REC_STATUS ");
			bufInsSql.append(" FROM COM_BP_EMPLOYEE_M BPE ");
			bufInsSql.append(" INNER JOIN COM_BANK_M B ON B.BANK_ID=BPE.BANK_ID ");
			bufInsSql.append(" INNER JOIN COM_BANKBRANCH_M BR ON BR.BANK_BRANCH_ID=BPE.BANK_BRANCH_ID ");
			
			bufInsSql.append(" INNER JOIN CR_DSA_DEALER_M DSA ON DSA.DEALER_ID=BPE.BUSSINESS_PARTNER_ID WHERE 'a'='a' ");
			
			bufInsSqlTempCount.append(" SELECT COUNT(1)  ");
			bufInsSqlTempCount.append(" FROM COM_BP_EMPLOYEE_M BPE ");
			bufInsSqlTempCount.append(" INNER JOIN COM_BANK_M B ON B.BANK_ID=BPE.BANK_ID ");
			bufInsSqlTempCount.append(" INNER JOIN COM_BANKBRANCH_M BR ON BR.BANK_BRANCH_ID=BPE.BANK_BRANCH_ID ");
			bufInsSqlTempCount.append(" INNER JOIN CR_DSA_DEALER_M DSA ON DSA.DEALER_ID=BPE.BUSSINESS_PARTNER_ID WHERE 'a'='a' ");
		
			if (!CommonFunction.checkNull(vo.getEmployeeName()).equals("")  ) {
				
				bufInsSql.append(" and BPE.EMPLOYEE_NAME = '" + CommonFunction.checkNull(vo.getEmployeeName())+ "' ");
				bufInsSqlTempCount.append(" and BPE.EMPLOYEE_NAME = '" +CommonFunction.checkNull(vo.getEmployeeName())+ "' ");
			}
			if (!CommonFunction.checkNull(vo.getEmployeeType()).equals("") ) {
				
				bufInsSql.append(" and BPE.EMPLOYEE_TYPE = '" + CommonFunction.checkNull(vo.getEmployeeType())+ "'  ");
				bufInsSqlTempCount.append(" and BPE.EMPLOYEE_TYPE = '" + CommonFunction.checkNull(vo.getEmployeeType())+ "' ");
		     }
			if (!CommonFunction.checkNull(vo.getBankAccountNo()).equals("") ) {
							
							bufInsSql.append(" and BPE.BANK_ACCOUNT_NO = '" + CommonFunction.checkNull(vo.getBankAccountNo())+ "'  ");
							bufInsSqlTempCount.append(" and BPE.BANK_ACCOUNT_NO = '" + CommonFunction.checkNull(vo.getBankAccountNo())+ "' ");
					     }
			if (!CommonFunction.checkNull(vo.getLbxBusinessPartnerId()).equals("") ) {
				
				bufInsSql.append(" and BPE.BUSSINESS_PARTNER_ID = '" + CommonFunction.checkNull(vo.getLbxBusinessPartnerId())+ "'  ");
				bufInsSqlTempCount.append(" and BPE.BUSSINESS_PARTNER_ID = '" + CommonFunction.checkNull(vo.getLbxBusinessPartnerId())+ "' ");
			 }
	

			logger.info("IN getSalesExecutiveList() search query1 ### "+ bufInsSql.toString());
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
		
	        count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)* no;
				endRecordIndex = no;
			
			}
			
			bufInsSql.append(" ORDER BY BPE.BP_EMP_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
	
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			//searchlist=ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					
					        salesExecutiveMasterVo = new SalesExecutiveMasterVo();
					        
							salesExecutiveMasterVo.setBpEmpUniqueId("<a href=salesExecutiveMasterAdd.do?method=openEditSalesExecutive&bpEmpUniqueId="
							+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
							+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");//BPE.BP_EMP_ID
							
							salesExecutiveMasterVo.setEmployeeName(CommonFunction.checkNull(data.get(1)).toString());//BPE.EMPLOYEE_NAME
							salesExecutiveMasterVo.setEmployeeType(CommonFunction.checkNull(data.get(2)).toString());//BPE.EMPLOYEE_TYPE
						    salesExecutiveMasterVo.setBusinessPartnerName(CommonFunction.checkNull(data.get(4)).toString());
						    
						    salesExecutiveMasterVo.setBankAccountNo(CommonFunction.checkNull(data.get(5)).toString());
						    salesExecutiveMasterVo.setBank(CommonFunction.checkNull(data.get(7)).toString());
						    salesExecutiveMasterVo.setBranch(CommonFunction.checkNull(data.get(9)).toString());
						    salesExecutiveMasterVo.setIfscCode(CommonFunction.checkNull(data.get(10)).toString());
						    salesExecutiveMasterVo.setMicrCode(CommonFunction.checkNull(data.get(11)).toString());
						    salesExecutiveMasterVo.setRecStatus(CommonFunction.checkNull(data.get(12)).toString());
					        					
							salesExecutiveMasterVo.setTotalRecordSize(count);
							detailList.add(salesExecutiveMasterVo);
				
					}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			salesExecutiveMasterVo=null;
		}

		return detailList;
	}

public ArrayList salesExecutiveEdit(Object ob) {
	
	    logger.info(" In salesExecutiveEdit ");

		ArrayList searchlist = new ArrayList();
		SalesExecutiveMasterVo vo = (SalesExecutiveMasterVo)ob;
		ArrayList<SalesExecutiveMasterVo> ratioList = new ArrayList<SalesExecutiveMasterVo>();
		SalesExecutiveMasterVo salesVo=null;
		try {
		StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT BPE.EMPLOYEE_NAME,BPE.EMPLOYEE_TYPE,BPE.BUSSINESS_PARTNER_ID,DSA.DEALER_DESC,BPE.BANK_ACCOUNT_NO,BPE.BANK_ID,B.BANK_NAME,BPE.BANK_BRANCH_ID,BR.BANK_BRANCH_NAME,BR.BRANCH_IFCS_CODE,BR.BRANCH_MICR_CODE,BPE.REC_STATUS  ");
			bufInsSql.append(" FROM COM_BP_EMPLOYEE_M BPE ");
			bufInsSql.append(" INNER JOIN COM_BANK_M B ON B.BANK_ID=BPE.BANK_ID ");
			bufInsSql.append(" INNER JOIN COM_BANKBRANCH_M BR ON BR.BANK_BRANCH_ID=BPE.BANK_BRANCH_ID ");
			
			bufInsSql.append(" INNER JOIN CR_DSA_DEALER_M DSA ON DSA.DEALER_ID=BPE.BUSSINESS_PARTNER_ID ");
			bufInsSql.append(" WHERE BP_EMP_ID='"+CommonFunction.checkNull(vo.getBpEmpUniqueId())+"'");
		    logger.info("salesExecutiveEdit  Query...." + bufInsSql.toString());

		    searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
            int size=searchlist.size();
			for (int i = 0; i < size; i++) {
	
				ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				
				salesVo = new SalesExecutiveMasterVo();
							
				salesVo.setEmployeeName(CommonFunction.checkNull(data.get(0)).toString());
				salesVo.setEmployeeType(CommonFunction.checkNull(data.get(1)).toString());
				salesVo.setLbxBusinessPartnerId(CommonFunction.checkNull(data.get(2)).toString());
				salesVo.setBusinessPartnerName(CommonFunction.checkNull(data.get(3)).toString());
				salesVo.setBankAccountNo(CommonFunction.checkNull(data.get(4)).toString());
				salesVo.setLbxBankID(CommonFunction.checkNull(data.get(5)).toString());
				salesVo.setBank(CommonFunction.checkNull(data.get(6)).toString());
				salesVo.setLbxBranchID(CommonFunction.checkNull(data.get(7)).toString());
				salesVo.setBranch(CommonFunction.checkNull(data.get(8)).toString());
				salesVo.setIfscCode(CommonFunction.checkNull(data.get(9)).toString());
				salesVo.setMicrCode(CommonFunction.checkNull(data.get(10)).toString());
				salesVo.setRecStatus(CommonFunction.checkNull(data.get(11)).toString());
				ratioList.add(salesVo);
				salesVo=null;

			}
			data=null;

		}
			searchlist=null;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ratioList;
		
 	}	




public ArrayList<Object> getEmployeeTypeList() {
	
	ArrayList list=new ArrayList();
	SalesExecutiveMasterVo av=null;
	try{
		StringBuffer query=new StringBuffer();
	 query.append("SELECT VALUE,DESCRIPTION FROM generic_master  "
        +"where GENERIC_KEY='EMPLOYEE_TYPE' and REC_STATUS='A'");
	logger.info("In getEmployeeTypeList: "+query.toString());
	
	ArrayList employeeType = ConnectionDAO.sqlSelect(query.toString());
	query=null;
	for(int i=0;i<employeeType.size();i++){
		ArrayList sublist = (ArrayList) employeeType.get(i);
		if(sublist.size()>0){
			av=new SalesExecutiveMasterVo();
		    av.setId((CommonFunction.checkNull(sublist.get(0))).trim());
			av.setName((CommonFunction.checkNull(sublist.get(1))).trim());
			list.add(av);
			av=null;
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return list;
}

	//started by richa
	public ArrayList getProductList(String questionId) {
		 
		ArrayList searchlist = new ArrayList();
		
		ArrayList<VerificationQuestionVo> resultList = new ArrayList<VerificationQuestionVo>();

		try {
			logger.info("In getProductList().....................................Dao Impl....."+questionId);

			StringBuilder bufInsSql = new StringBuilder();
			VerificationQuestionVo vo = null;
			
			bufInsSql.append(" select  a.product_id,b.product_desc  from cr_verification_question_product_mapping_dtl a  join cr_product_m b  on( (CHARINDEX(concat('|',a.product_id,'|'),concat('|',b.product_id,'|'))>0))  where question_id ='"+ questionId+ "' " );
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN getProductList() search query1 ### "+ bufInsSql.toString());
			int size=searchlist.size();
			for (int i = 0; i <size ; i++) {
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					vo = new VerificationQuestionVo();
					vo.setProductId(CommonFunction.checkNull(data.get(0)));
					vo.setProductDesc(CommonFunction.checkNull(data.get(1)));

					
					resultList.add(vo);
					vo=null;
				}
				data=null;
			}
			bufInsSql=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			searchlist.clear();
			searchlist=null;
		}
		return resultList;
	}




public boolean insertQuestionProduct(Object ob, String[] productId,String check) {
	VerificationQuestionVo vo = (VerificationQuestionVo) ob;
	logger.info("getVerificationQuestId()11111:-" + vo.getVerificationQuestId());
	boolean status = false;
	ArrayList qryList = new ArrayList();
	StringBuffer bufInsSql = new StringBuffer();
	String questionID="";
	PrepStmtObject insertPrepStmtObject = null;
	try{
	Connection con=ConnectionDAOforEJB.getConnection();
	Statement st1=con.createStatement();
	if(check=="S"){
	String sqlquery1="select max(question_id) from cr_deal_verification_question_m ";

	 questionID=ConnectionDAOforEJB.singleReturn(sqlquery1);
	 String DeleteBranchQuery="delete from cr_verification_question_product_mapping_dtl where question_id='"+questionID +"'";
		logger.info("Delete Query for New User Against Branch Id:"+DeleteBranchQuery);
		 st1=con.createStatement();
			int a=st1.executeUpdate(DeleteBranchQuery)	; 
	}
	else
	{
		String DeleteBranchQuery="delete from cr_verification_question_product_mapping_dtl where question_id='"+vo.getVerificationQuestId() +"'";
		logger.info("Delete Query for New User Against Branch Id:"+DeleteBranchQuery);
		 st1=con.createStatement();
			int a=st1.executeUpdate(DeleteBranchQuery)	; 
	}
	try {
			
			if(vo.getProductType().equalsIgnoreCase("S"))
			{
			
				for (int i = 0; i < productId.length; i++) {
					logger.info("In productId###r@@@@ "+productId);
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO cr_verification_question_product_mapping_dtl(question_id,product_id)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?)");
				
				if(check=="S"){
					if (CommonFunction.checkNull(questionID)
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(questionID
								.toUpperCase().trim());
					}
				else{
					if (CommonFunction.checkNull(vo.getVerificationQuestId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getVerificationQuestId());
					
							
				}
				if (CommonFunction.checkNull(productId[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(productId[i]);
				
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertQuestionProduct() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("status@@@@@ ### "
						+ status);
			}
					}
										

			else if(vo.getProductType().equalsIgnoreCase("A"))
			{
			
			ArrayList updateList=new ArrayList();
			ArrayList <Object> productID=ConnectionDAOforEJB.sqlSelect("select product_id from cr_product_m where ifnull(REC_STATUS,'A')='A'");
			if(productID.size()>0)
			{
			
				for (int i = 0; i < productID.size(); i++) {
					insertPrepStmtObject = new PrepStmtObject();
					 bufInsSql = new StringBuffer();
						bufInsSql.append("INSERT INTO cr_verification_question_product_mapping_dtl(question_id,product_id)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?)");
					
						if(check=="S"){
							if (CommonFunction.checkNull(questionID)
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(questionID);
							}
						else{
						if (CommonFunction.checkNull(vo.getVerificationQuestId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getVerificationQuestId()
									.toUpperCase().trim());
						}

						if (CommonFunction.checkNull(((ArrayList<Object>) productID.get(i)).get(0)).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(((ArrayList<Object>) productID.get(i)).get(0).toString());
						
						

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertQuestionProduct in all case ### "
							+ insertPrepStmtObject.printQuery());
					updateList.add(insertPrepStmtObject);
				}
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updateList);
				logger.info("In insertUserMaster......................"	+ status);
				
			}
			
			}
			else
			{
				status=true;
			}
		 
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		st1.close();
		con.close();
	}
	}catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}

@Override
public ArrayList searchConsortiumData(Object ob) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean insertConsortiumPartner(Object ob) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean updateConsortiumData(Object ob) {
	// TODO Auto-generated method stub
	return false;
}


@Override
public ArrayList searchGoldOrnamentData(Object ob) {
	// TODO Auto-generated method stub
	return null;
}



@Override

public boolean updateGoldOrnamentData(Object ob) {
	// TODO Auto-generated method stub
	return false;
}

@Override

public ArrayList getGoldOrnamentType() {
	// TODO Auto-generated method stub

	return null;
}

@Override
public ArrayList getGoldOrnamentStandard() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList getGoldOrnamentCategory() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String insertGoldOrnament(Object ob) {
	// TODO Auto-generated method stub
	return null;
}
public  String insertMobileUserMaster(MobileUserMappingVo paramMobileUserMappingVo, String paramString) {
	// TODO Auto-generated method stub
	return null;
}

public  String saveModifyMobileMasterDao(Object paramObject, String paramString) {
	// TODO Auto-generated method stub
	return null;
}
public  ArrayList<MobileUserMappingVo> modifyMobileUSerDetailsDao(Object paramObject) {
	// TODO Auto-generated method stub
	return null;
}

public  ArrayList<MobileUserMappingVo> searchMobileUserData(Object paramObject) {
	// TODO Auto-generated method stub
	return null;
}

public  String getMobileNoMasterDao(Object paramObject) {
	// TODO Auto-generated method stub
	return null;
}

public  String insertIrrMaster(IrrCalculationMasterVo paramIrrCalculationMasterVo) {
	// TODO Auto-generated method stub
	return null;
}

public  ArrayList searchIrrCalData(Object paramObject) {
	// TODO Auto-generated method stub
	return null;
}

public  ArrayList irrModifyChargeCodeDetailsDao(Object paramObject) {
	// TODO Auto-generated method stub
	return null;
}

public  ArrayList getIrrChargeDetail(Object paramObject) {
	// TODO Auto-generated method stub
	return null;
}

public  String updateIrrMaster(IrrCalculationMasterVo paramIrrCalculationMasterVo) {
	// TODO Auto-generated method stub
	return null;
}



@Override
public ArrayList<UsedVehiclePricingVo> getUsedVehiclePricingData(
		Object ob) {
	// TODO Auto-generated method stub
	return null;
}


@Override
public boolean updateVehiclePricingdata(Object ob) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public String getNoOfYearAtUsedVehicle() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList<UsedVehiclePricingVo> selectUsedVehiclePricing(
		String makeModelId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList<UsedVehiclePricingVo> searchUsedVehiclePricing(
		Object ob) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean insertUsedVehiclePricing(UsedVehiclePricingVo usedVehiclePricingVo) {
	// TODO Auto-generated method stub
	return false;
}

public ArrayList<NPAMasterVo> getProductId() {
    return null;
}
public String getProduct(String str) {
    return null;
  }
  public boolean updateSBLData(Object ob) {
	//dummy method
  return false;
}
  public ArrayList <ProductMasterVo> searchSblData(Object ob) {
	    return null;
	}
  
  public boolean insertSblMaster (Object ob) {
		//dummy method
	  return false;
	}
  public boolean deleteUser(String userId) {
		return false;
	}
  
}
				
						