package com.legal.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.legal.dao.LegalMasterDAO;
import com.legal.vo.AdvocateMasterVo;
import com.legal.vo.CaseTypeMasterVo;
import com.legal.vo.CourtNameMasterVo;
import com.legal.vo.CourtTypeMasterVo;
import com.legal.vo.LawFirmMasterVo;
import com.legal.vo.NoticeTypeMasterVo;
import com.legal.vo.POAMasterVo;
import com.legal.vo.StageTypeMasterVo;



public class MSSQLLegalMasterDaoImpl implements LegalMasterDAO{

	private static final Logger logger = Logger.getLogger(MSSQLLegalMasterDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dbo=resource.getString("lbl.dbPrefix");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	

	
	public ArrayList searchCaseTypeMasterData(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String caseTypeCode = null;
		String caseTypeDesc = null;
		ArrayList searchlist = new ArrayList();
		CaseTypeMasterVo CaseTypeMasterVo = (CaseTypeMasterVo) ob;
		ArrayList<CaseTypeMasterVo> detailList = new ArrayList<CaseTypeMasterVo>();
		try {

			logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
			
			caseTypeCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(CaseTypeMasterVo.getCaseTypeCode())).trim());
			caseTypeDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(CaseTypeMasterVo.getCaseTypeDesc())).trim());
           	StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();
			bufInsSql.append("SELECT CASE_TYPE_CODE,CASE_TYPE_DESC,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM LEG_CASE_TYPE_DEF_M ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEG_CASE_TYPE_DEF_M ");
			bufInsSql.append(" WHERE 5=5 ");
			bufInsSqlTempCount.append(" WHERE 5=5 ");

			 if (!caseTypeCode.equals("")) {
				bufInsSql.append(" AND CASE_TYPE_CODE = '" + caseTypeCode + "' ");
				bufInsSqlTempCount.append(" AND CASE_TYPE_CODE = '" + caseTypeCode + "' ");
			}
			 if (!caseTypeDesc.equals("")) {
				bufInsSql.append(" AND CASE_TYPE_DESC like '%" + caseTypeDesc + "%' ");
				bufInsSqlTempCount.append(" AND CASE_TYPE_DESC like '%" + caseTypeDesc + "%' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			

				logger.info("current PAge Link no .................... "+CaseTypeMasterVo.getCurrentPageLink());
				if(CaseTypeMasterVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (CaseTypeMasterVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				bufInsSql.append(" ORDER BY CASE_TYPE_CODE OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							

			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
			
			logger.info("searchCaseTypeMasterData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					CaseTypeMasterVo deptMVO = new CaseTypeMasterVo();
					deptMVO.setCaseTypeCode("<a href=caseTypeMaster.do?method=openEditCaseTypeMaster&caseTypeCode="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					//deptMVO.setCaseTypeCode(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setCaseTypeDesc(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		/*if(searchlist.size()==0)
		{
			CaseTypeMasterVo deptMVO = new CaseTypeMasterVo();
			deptMVO.setTotalRecordSize(count);
			detailList.add(deptMVO);
			request.setAttribute("flag","yes");
			logger.info("Detail List when searchList is null: "+detailList.size());
		}*/
       finally{
	searchlist.clear();
	searchlist = null;
	
         }
		return detailList;
	}
	
	public boolean insertCaseTypeMaster(Object ob) {
		CaseTypeMasterVo vo = (CaseTypeMasterVo) ob;
		boolean status = false;
		
		logger.info("In insertCaseTypeMaster.........inside ejb server file...........Dao Impl"
						+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

		String query = "select CASE_TYPE_CODE from LEG_CASE_TYPE_DEF_M where CASE_TYPE_CODE='"
				+ StringEscapeUtils.escapeSql(vo.getCaseTypeCode().trim()) + "'";
		logger.info("In insertCaseTypeMaster.......inside ejb server file..........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (vo.getRecStatus() != null
						&& vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert Case Type master");
				StringBuilder bufInsSql = new StringBuilder();
				bufInsSql.append("insert into LEG_CASE_TYPE_DEF_M(CASE_TYPE_CODE,CASE_TYPE_DESC,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //caseTypeCode
				bufInsSql.append(" ?,"); //caseTypeDesc
				bufInsSql.append(" ?,"); //rec_status
				bufInsSql.append(" ?,"); //makerId
				//bufInsSql.append(dbo);
				//bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				//bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			
				if (CommonFunction.checkNull(vo.getCaseTypeCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCaseTypeCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getCaseTypeDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCaseTypeDesc().toUpperCase().trim());
				

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
				
				/*
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
				
				*/

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertCaseTypeMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveCountryData......................"+ status);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			qryList.clear();
			qryList = null;
			
		         }
		return status;

	}
	
	public ArrayList editCaseTypeData(Object ob) {

		ArrayList searchlist = new ArrayList();
		CaseTypeMasterVo vo = (CaseTypeMasterVo)ob;
		ArrayList<CaseTypeMasterVo> caseTypeList = new ArrayList<CaseTypeMasterVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getCaseTypeCode());

		try {
			

			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append(" select  CASE_TYPE_CODE,CASE_TYPE_DESC,REC_STATUS  from LEG_CASE_TYPE_DEF_M ");
			
			bufInsSql.append("  WHERE CASE_TYPE_CODE='"+StringEscapeUtils.escapeSql(vo.getCaseTypeCode())+"'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN editCaseTypeData() search query1 ### "+ bufInsSql.toString());
			logger.info("editCaseTypeData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("editCaseTypeData " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					CaseTypeMasterVo caseTypeDataVo = new CaseTypeMasterVo();
					
					caseTypeDataVo.setCaseTypeCode(CommonFunction.checkNull(data.get(0)).toString());
					caseTypeDataVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(1)).toString());					
					caseTypeDataVo.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
					caseTypeList.add(caseTypeDataVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			searchlist.clear();
			searchlist = null;
			
		         }
		return caseTypeList;
}
	
	
	
	
	public boolean updateCaseTypeData(Object ob) {
		CaseTypeMasterVo vo = (CaseTypeMasterVo) ob;
		String caseTypeCode = (String) vo.getCaseTypeCode();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		

		boolean status = false;
		String stat = "";

		try {
			
			if (vo.getRecStatus() != null
					&& vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}
			StringBuilder bufInsSql = new StringBuilder();
			logger.info("In updateCountryData..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE LEG_CASE_TYPE_DEF_M SET CASE_TYPE_DESC=?,REC_STATUS=?");
			bufInsSql.append(" ,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where CASE_TYPE_CODE=?");

			if (CommonFunction.checkNull(vo.getCaseTypeDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getCaseTypeDesc().toUpperCase().trim());


			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			//---------------------------------- 

			if (CommonFunction.checkNull(vo.getCaseTypeCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getCaseTypeCode());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			
			logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
			updatelist.clear();
			updatelist = null;
			
		         }
		return status;
	}
	
	
	
	//-------Law Firm Master block start ------
	
	
	
	
public ArrayList searchLawFirmMasterData(Object ob) {
		
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String lawFirmCode = null;
		String lawFirmDesc = null;
		
		
		
		ArrayList searchlist = new ArrayList();
		LawFirmMasterVo lawFirmVo = (LawFirmMasterVo) ob;
		ArrayList<LawFirmMasterVo> detailList = new ArrayList<LawFirmMasterVo>();
		try {

			logger.info("In searchLawFirmMasterData()..............inside ejb server file.......................Dao Impl");
			
			lawFirmCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(lawFirmVo.getLawFirmCode())).trim());
			lawFirmDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(lawFirmVo.getLawFirmDesc())).trim());
			
           	StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();
			bufInsSql.append("SELECT LAWFIRM_CODE,LAWFIRM_DESC,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM LEG_LAWFIRM_DEF_M ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEG_LAWFIRM_DEF_M ");
			bufInsSql.append(" WHERE 5=5 ");
			bufInsSqlTempCount.append(" WHERE 5=5 ");
			
			if (!lawFirmCode.equals("")) {
				bufInsSql.append(" AND LAWFIRM_CODE = '" + lawFirmCode + "' ");
				bufInsSqlTempCount.append(" AND LAWFIRM_CODE = '" + lawFirmCode + "' ");
			}
			 if (!lawFirmDesc.equals("")) {
				bufInsSql.append(" AND LAWFIRM_DESC like '%" + lawFirmDesc + "%' ");
				bufInsSqlTempCount.append(" AND LAWFIRM_DESC like '%" + lawFirmDesc + "%' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((caseTypeCode.trim()==null && caseTypeDesc.trim()==null) || (caseTypeCode.trim().equalsIgnoreCase("") && caseTypeDesc.trim().equalsIgnoreCase("")) || LawFirmMasterVo.getCurrentPageLink()>1)
//			{
			
				logger.info("current PAge Link no .................... "+lawFirmVo.getCurrentPageLink());
				if(lawFirmVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (lawFirmVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				bufInsSql.append(" ORDER BY LAWFIRM_CODE OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							
//			}
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchLawFirmMasterData() search query1 ### "+ bufInsSql.toString());
			
			logger.info("searchLawFirmMasterData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					LawFirmMasterVo deptMVO = new LawFirmMasterVo();
					deptMVO.setLawFirmCode("<a href=lawFirmMaster.do?method=openEditLawFirmMaster&lawFirmCode="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					//deptMVO.setCaseTypeCode(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setLawFirmDesc(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	
	public boolean insertLawFirmMaster(Object ob,String [] branchName) {
		LawFirmMasterVo vo = (LawFirmMasterVo) ob;
		boolean status = false;
		
		logger.info("In insertLawFirmMaster.........inside ejb server file...........Dao Impl"
						+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

		String query = "select LAWFIRM_CODE from LEG_LAWFIRM_DEF_M where LAWFIRM_CODE='"
				+ StringEscapeUtils.escapeSql(vo.getLawFirmCode().trim()) + "'";
		logger.info("In insertLawFirmMaster.......inside ejb server file..........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (vo.getRecStatus() != null
						&& vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert Law Firm master");
				StringBuilder bufInsSql = new StringBuilder();
				bufInsSql.append("insert into LEG_LAWFIRM_DEF_M(LAWFIRM_CODE,LAWFIRM_DESC,ADDRESS1,ADDRESS2,COUNTRY,STATE,CITY,ZIPCODE,PHONE1,PHONE2,MOBILE,EMAIL,PAN,BANK_ACCNO,DATE_OF_APPOINTMENT,CONSTITUION_OF_FIRM,SAP_CODE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //LAWFIRM_CODE
				bufInsSql.append(" ?,"); //LAWFIRM_DESC
				bufInsSql.append(" ?,"); //ADDRESS1
				bufInsSql.append(" ?,"); //ADDRESS2
				bufInsSql.append(" ?,"); //COUNTRY
				bufInsSql.append(" ?,"); //STATE
				bufInsSql.append(" ?,"); //CITY
				bufInsSql.append(" ?,"); //ZIPCODE
				bufInsSql.append(" ?,"); //PHONE1
				bufInsSql.append(" ?,"); //PHONE2
				bufInsSql.append(" ?,"); //MOBILE
				bufInsSql.append(" ?,"); //EMAIL
				bufInsSql.append(" ?,"); //PAN
				bufInsSql.append(" ?,"); //BANK_ACCNO
				bufInsSql.append(dbo);//DATE_OF_APPOINTMENT
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,"); //CONSTITUION_OF_FIRM
				bufInsSql.append(" ?,"); //SAP_CODE
				bufInsSql.append(" ?,"); //rec_status
				bufInsSql.append(" ?,"); //makerId
				bufInsSql.append(dbo);//MAKER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHER_ID
				bufInsSql.append(dbo);//AUTHER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			
				if (CommonFunction.checkNull(vo.getLawFirmCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLawFirmCode().toUpperCase().trim());
				
								
				if (CommonFunction.checkNull(vo.getLawFirmDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLawFirmDesc().toUpperCase().trim());
				
				
				if (CommonFunction.checkNull(vo.getAddress1()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAddress1().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getAddress2()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAddress2().toUpperCase().trim());
				
				
				if (CommonFunction.checkNull(vo.getTxtCountryCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTxtCountryCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getTxtStateCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTxtStateCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getTxtDistCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTxtDistCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getZipCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getZipCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getPhone1()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPhone1().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getPhone2()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPhone2().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getMobileNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMobileNo().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getEmailId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEmailId().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getPanNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPanNo().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getBankAccNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getBankAccNo().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getDateOfAppointment()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDateOfAppointment().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getConstitutionOfFirm()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getConstitutionOfFirm().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getSapCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSapCode().toUpperCase().trim());
				
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
				
				/*
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
				
				*/

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertLawFirmMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				
				
				
				for (int i = 0; i < branchName.length; i++) {
					
					insertPrepStmtObject = null;
					bufInsSql = null;
					
					bufInsSql = new StringBuilder();
					insertPrepStmtObject = new PrepStmtObject();
					
					bufInsSql.append("INSERT INTO LEG_LAWFIRM_BRANCH_MX(LAWFIRM_CODE,LAWFIRM_DESC,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");//LAWFIRM_CODE
					bufInsSql.append(" ?,");//LAWFIRM_DESC
					bufInsSql.append(" ?,");//BRANCH_ID
					bufInsSql.append(" ?,");//REC_STATUS
					bufInsSql.append(" ?,"); //makerId
					bufInsSql.append(dbo);//MAKER_DATE
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					bufInsSql.append(" ?,");//AUTHER_ID
					bufInsSql.append(dbo);//AUTHER_DATE
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					if (CommonFunction.checkNull(vo.getLawFirmCode()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLawFirmCode().toUpperCase());
					
					if (CommonFunction.checkNull(vo.getLawFirmDesc()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLawFirmDesc().toUpperCase());
					

					if (CommonFunction.checkNull(branchName[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(branchName[i]);

					/*if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);
					*/
					
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
					logger.info("IN insertLawFirmMaster() insert query1 ### "
							+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
				
				
				
				
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertLawFirmMaster......................"+ status);
			}

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally
		{
			
			qryList.clear();
			qryList=null;
			
		}
		return status;

	}
	
	public ArrayList editLawFirmData(Object ob) {

		ArrayList searchlist = new ArrayList();
		LawFirmMasterVo vo = (LawFirmMasterVo)ob;
		ArrayList<LawFirmMasterVo> lawFirmList = new ArrayList<LawFirmMasterVo>();
		logger.info("editLawFirmData in searchBenchMarkRatioEdit &***************************** = "+vo.getLawFirmCode());

		try {
			

			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append(" SELECT  LAWFIRM_CODE,LAWFIRM_DESC,ADDRESS1,ADDRESS2," );
			bufInsSql.append(" COUNTRY,(SELECT COUNTRY_DESC FROM COM_COUNTRY_M WHERE COUNTRY_ID = LLFDM.COUNTRY) COUNTRY_DESC," );
			bufInsSql.append(" STATE,(SELECT STATE_DESC FROM COM_STATE_M WHERE STATE_ID = LLFDM.STATE) STATE_DESC,");
			bufInsSql.append(" CITY,(SELECT DISTRICT_DESC FROM COM_DISTRICT_M WHERE DISTRICT_ID = LLFDM.CITY) CITY_DESC,");
			bufInsSql.append(" ZIPCODE,PHONE1,PHONE2,MOBILE,EMAIL,PAN,BANK_ACCNO,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(DATE_OF_APPOINTMENT,'"+ dateFormat +"'), ");
			bufInsSql.append(" CONSTITUION_OF_FIRM,SAP_CODE,REC_STATUS from LEG_LAWFIRM_DEF_M LLFDM ");
			
			
			
			bufInsSql.append("  WHERE LAWFIRM_CODE='"+StringEscapeUtils.escapeSql(vo.getLawFirmCode())+"'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN editLawFirmData() search query1 ### "+ bufInsSql.toString());
			logger.info("editLawFirmData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("editLawFirmData " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					LawFirmMasterVo lawFirmVo = new LawFirmMasterVo();
					
					lawFirmVo.setLawFirmCode(CommonFunction.checkNull(data.get(0)).toString());
					lawFirmVo.setLawFirmDesc(CommonFunction.checkNull(data.get(1)).toString());					
					lawFirmVo.setAddress1(CommonFunction.checkNull(data.get(2)).toString());
					lawFirmVo.setAddress2(CommonFunction.checkNull(data.get(3)).toString());
					lawFirmVo.setTxtCountryCode(CommonFunction.checkNull(data.get(4)).toString());
					lawFirmVo.setCountry(CommonFunction.checkNull(data.get(5)).toString());
					lawFirmVo.setTxtStateCode(CommonFunction.checkNull(data.get(6)).toString());
					lawFirmVo.setState(CommonFunction.checkNull(data.get(7)).toString());
					lawFirmVo.setTxtDistCode(CommonFunction.checkNull(data.get(8)).toString());
					lawFirmVo.setDist(CommonFunction.checkNull(data.get(9)).toString());
					lawFirmVo.setZipCode(CommonFunction.checkNull(data.get(10)).toString());
					lawFirmVo.setPhone1(CommonFunction.checkNull(data.get(11)).toString());
					lawFirmVo.setPhone2(CommonFunction.checkNull(data.get(12)).toString());
					lawFirmVo.setMobileNo(CommonFunction.checkNull(data.get(13)).toString());
					lawFirmVo.setEmailId(CommonFunction.checkNull(data.get(14)).toString());
					lawFirmVo.setPanNo(CommonFunction.checkNull(data.get(15)).toString());
					lawFirmVo.setBankAccNo(CommonFunction.checkNull(data.get(16)).toString());
					lawFirmVo.setDateOfAppointment(CommonFunction.checkNull(data.get(17)).toString());
					lawFirmVo.setConstitutionOfFirm(CommonFunction.checkNull(data.get(18)).toString());
					lawFirmVo.setSapCode(CommonFunction.checkNull(data.get(19)).toString());
					lawFirmVo.setRecStatus(CommonFunction.checkNull(data.get(20)).toString());
					
					lawFirmList.add(lawFirmVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lawFirmList;
	}
	
	
	public boolean updateLawFirmData(Object ob,String [] branchName) {
		
		LawFirmMasterVo vo = (LawFirmMasterVo) ob;
		String lawFirmCode = (String) vo.getLawFirmCode();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		
		boolean status = false;
		String stat = "";

		try {
			
			if (vo.getRecStatus() != null
					&& vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}
			StringBuilder bufInsSql = new StringBuilder();
			logger.info("In updateLawFirmData..........inside ejb server file............Dao Impl");
			
			bufInsSql.append(" UPDATE LEG_LAWFIRM_DEF_M SET LAWFIRM_DESC=? ");
			
			bufInsSql.append(" ,ADDRESS1 = ?,ADDRESS2 = ? ,COUNTRY = ?,STATE = ? ,CITY = ? ,ZIPCODE = ? ,PHONE1 = ?,PHONE2 = ? ");
			bufInsSql.append(" ,MOBILE = ? ,EMAIL = ?,PAN = ?,BANK_ACCNO = ?, CONSTITUION_OF_FIRM = ?,SAP_CODE = ?,REC_STATUS =? " );
			
			
			bufInsSql.append(" ,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" ,AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" ,DATE_OF_APPOINTMENT=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where LAWFIRM_CODE=?");
			

			if (CommonFunction.checkNull(vo.getLawFirmDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLawFirmDesc().toUpperCase().trim());
			
			
			
			if (CommonFunction.checkNull(vo.getAddress1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAddress1().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getAddress2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAddress2().toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(vo.getTxtCountryCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtCountryCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getTxtStateCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtStateCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getTxtDistCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtDistCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getZipCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getZipCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getPhone1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPhone1().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getPhone2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPhone2().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getMobileNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMobileNo().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getEmailId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getEmailId().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getPanNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPanNo().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getBankAccNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getBankAccNo().toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(vo.getConstitutionOfFirm()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getConstitutionOfFirm().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getSapCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getSapCode().toUpperCase().trim());


			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			
			//----------------------------------
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
			
			//---------------------------------- 
			
			if (CommonFunction.checkNull(vo.getDateOfAppointment()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDateOfAppointment().toUpperCase().trim());
			

			if (CommonFunction.checkNull(vo.getLawFirmCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLawFirmCode());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			logger.info("## In updateLawFirmData() : update query (case tye master) "+insertPrepStmtObject.printQuery());

			updatelist.add(insertPrepStmtObject);
			logger.info("In updateLawFirmData getListOfValues" + bufInsSql.toString());
			
			
			
			insertPrepStmtObject = null;
			bufInsSql = null;
			
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			
			bufInsSql.append(" DELETE FROM LEG_LAWFIRM_BRANCH_MX WHERE LAWFIRM_CODE='"+vo.getLawFirmCode()+"'");
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			updatelist.add(insertPrepStmtObject);
			
			
			for (int i = 0; i < branchName.length; i++) {
				
				insertPrepStmtObject = null;
				bufInsSql = null;
				
				bufInsSql = new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				
				bufInsSql.append("INSERT INTO LEG_LAWFIRM_BRANCH_MX(LAWFIRM_CODE,LAWFIRM_DESC,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//LAWFIRM_CODE
				bufInsSql.append(" ?,");//LAWFIRM_DESC
				bufInsSql.append(" ?,");//BRANCH_ID
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,"); //makerId
				bufInsSql.append(dbo);//MAKER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHER_ID
				bufInsSql.append(dbo);//AUTHER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				if (CommonFunction.checkNull(vo.getLawFirmCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLawFirmCode().toUpperCase());
				
				if (CommonFunction.checkNull(vo.getLawFirmDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLawFirmDesc().toUpperCase());
				

				if (CommonFunction.checkNull(branchName[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchName[i]);

				/*if (CommonFunction.checkNull(userMasterVo.getAllselection()[0]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(userMasterVo.getAllselection()[0]);
				*/
				
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
				logger.info("IN updateLawFirmData() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				updatelist.add(insertPrepStmtObject);
			}
			
			
			
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally
		{
			
			updatelist.clear();
			updatelist=null;
			
		}
		return status;
	}
	
	
	public ArrayList<LawFirmMasterVo> getBranchesForEdit(String lawFirmCode) {

		ArrayList searchlist = new ArrayList();
		
		ArrayList<LawFirmMasterVo> branchList = new ArrayList<LawFirmMasterVo>();

		try {
			logger.info("In getBranchesForEdit().....................................Dao Impl");

			StringBuilder bufInsSql = new StringBuilder();
			
			bufInsSql.append(" SELECT BRANCH_ID,BRANCH_DESC FROM com_branch_m WHERE BRANCH_ID IN (SELECT BRANCH_ID FROM LEG_LAWFIRM_BRANCH_MX WHERE LAWFIRM_CODE = '"+lawFirmCode+"') " );
					

			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN getBranchesForEdit() search query1 ### "+ bufInsSql.toString());
			logger.info("getBranchesForEdit " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("branchDescList " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					LawFirmMasterVo lawFirmVo = new LawFirmMasterVo();
					lawFirmVo.setBranchId(CommonFunction.checkNull(data.get(0)).toString());
					lawFirmVo.setBranchDesc(CommonFunction.checkNull(data.get(1)).toString());
					branchList.add(lawFirmVo);

				}

			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			searchlist.clear();
			searchlist=null;
			
		}

		return branchList;
	}
	
	
	
//-------Advocate Master block start ------
	
	
	
public ArrayList searchAdvocateMasterData(Object ob) {
		
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String advocateCode = null;
		String advocateDesc = null;
		
		
		
		ArrayList searchlist = new ArrayList();
		AdvocateMasterVo vo = (AdvocateMasterVo) ob;
		ArrayList<AdvocateMasterVo> detailList = new ArrayList<AdvocateMasterVo>();
		try {

			logger.info("In searchAdvocateMasterData()..............inside ejb server file.......................Dao Impl");
			
			advocateCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxUserId())).trim());
			advocateDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAdvocateCode())).trim());
			
           	StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();
			bufInsSql.append("SELECT ADVOCATE_CODE,ADVOCATE_DESC,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM LEG_ADVOCATE_DEF_M ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEG_ADVOCATE_DEF_M ");
			bufInsSql.append(" WHERE 5=5 ");
			bufInsSqlTempCount.append(" WHERE 5=5 ");
			
			if (!advocateCode.equals("")) {
				bufInsSql.append(" AND ADVOCATE_CODE = '" + advocateCode + "' ");
				bufInsSqlTempCount.append(" AND ADVOCATE_CODE = '" + advocateCode + "' ");
			}
			if (!advocateDesc.equals("")) {
				bufInsSql.append(" AND ADVOCATE_DESC like '%" + advocateDesc + "%' ");
				bufInsSqlTempCount.append(" AND ADVOCATE_DESC like '%" + advocateDesc + "%' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((caseTypeCode.trim()==null && caseTypeDesc.trim()==null) || (caseTypeCode.trim().equalsIgnoreCase("") && caseTypeDesc.trim().equalsIgnoreCase("")) || LawFirmMasterVo.getCurrentPageLink()>1)
//			{
			
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				bufInsSql.append(" ORDER BY ADVOCATE_CODE OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							
//			}
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchAdvocateMasterData() search query1 ### "+ bufInsSql.toString());
			
			logger.info("searchAdvocateMasterData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					AdvocateMasterVo admVo = new AdvocateMasterVo();
					admVo.setLbxUserId("<a href=advocateMaster.do?method=openEditAdvocateMaster&advocateCode="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					//deptMVO.setCaseTypeCode(CommonFunction.checkNull(data.get(0)).toString());
					admVo.setAdvocateCode(CommonFunction.checkNull(data.get(1)).toString());
					admVo.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
					admVo.setTotalRecordSize(count);
					logger.info("admVo.setAdvocateCode::::"+admVo.getLbxUserId());
					logger.info("admVo.setAdvocateCode::::"+admVo.getAdvocateCode());
					logger.info("admVo.setAdvocateCode::::"+admVo.getRecStatus());
					detailList.add(admVo);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	
	public boolean insertAdvocateMaster(Object ob,String [] branchName,String [] caseTypeCode) {
		AdvocateMasterVo vo = (AdvocateMasterVo) ob;
		boolean status = false;
		
		
		logger.info("In insertAdvocateMaster.........inside ejb server file...........Dao Impl"
						+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String retainerFlag = "N";
		
		//String advocateTypeFlag = "";
		//advocateTypeFlag = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAdvocateTypeFlag())).trim());

		String query = "select ADVOCATE_CODE from LEG_ADVOCATE_DEF_M where ADVOCATE_CODE='"
				+ StringEscapeUtils.escapeSql(vo.getAdvocateCode().trim()) + "'";
		logger.info("In insertAdvocateMaster.......inside ejb server file..........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		
		logger.info("## In insertAdvocateMaster() : st : ==>> "+st); 
		
		
		try {

			if (!st) {
				if (vo.getRecStatus() != null && vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				
				logger.info("## In insertAdvocateMaster() : st1 : ==>> "+st); 
				
				if (vo.getRegularRetainerFlag() != null
						&& vo.getRegularRetainerFlag().equals("on")) {
					retainerFlag = "Y";
				} else {
					retainerFlag = "N";
				}	
					logger.info("## In insertAdvocateMaster() : st2 : ==>> "+st); 	
				

			
				StringBuilder bufInsSql = new StringBuilder();
				bufInsSql.append("insert into LEG_ADVOCATE_DEF_M(ADVOCATE_CODE,ADVOCATE_DESC,ADVOCATE_TYPE_FLAG,LAW_FIRM_CODE,ADDRESS1,ADDRESS2,COUNTRY,STATE,CITY,ZIPCODE,PHONE1,PHONE2,MOBILE,EMAIL,PAN,BANK_ACCNO,DATE_OF_APPOINTMENT,MEMBERSHIP_LICNO,TENURE_OF_RETAINERSHIP,REGULAR_RETAINER_FLAG,RETAINERSHIP_VALID_UP_TO,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //ADVOCATE_CODE
				bufInsSql.append(" ?,"); //ADVOCATE_DESC
				bufInsSql.append(" ?,"); //ADVOCATE_TYPE_FLAG
				bufInsSql.append(" ?,"); //LAW_FIRM_CODE
				bufInsSql.append(" ?,"); //ADDRESS1
				bufInsSql.append(" ?,"); //ADDRESS2
				bufInsSql.append(" ?,"); //COUNTRY
				bufInsSql.append(" ?,"); //STATE
				bufInsSql.append(" ?,"); //CITY
				bufInsSql.append(" ?,"); //ZIPCODE
				bufInsSql.append(" ?,"); //PHONE1
				bufInsSql.append(" ?,"); //PHONE2
				bufInsSql.append(" ?,"); //MOBILE
				bufInsSql.append(" ?,"); //EMAIL
				bufInsSql.append(" ?,"); //PAN
				bufInsSql.append(" ?,"); //BANK_ACCNO
				bufInsSql.append(dbo);//DATE_OF_APPOINTMENT
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,"); //MEMBERSHIP_LICNO
				bufInsSql.append(" ?,"); //TENURE_OF_RETAINERSHIP
				bufInsSql.append(" ?,"); //REGULAR_RETAINER_FLAG
				bufInsSql.append(dbo);//RETAINERSHIP_VALID_UP_TO
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
				bufInsSql.append(" ?,"); //rec_status
				bufInsSql.append(" ?,"); //makerId
				bufInsSql.append(dbo);//MAKER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHER_ID
				bufInsSql.append(dbo);//AUTHER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			
				if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase().trim());
				
								
				if (CommonFunction.checkNull(vo.getAdvocateCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAdvocateCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getAdvocateTypeFlag()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAdvocateTypeFlag().toUpperCase().trim());
				
			
				
				if (CommonFunction.checkNull(vo.getLbxlawFirmCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxlawFirmCode().toUpperCase().trim());
				
				
				
				if (CommonFunction.checkNull(vo.getAddress1()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAddress1().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getAddress2()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAddress2().toUpperCase().trim());
				
				
				if (CommonFunction.checkNull(vo.getTxtCountryCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTxtCountryCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getTxtStateCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTxtStateCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getTxtDistCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTxtDistCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getZipCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getZipCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getPhone1()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPhone1().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getPhone2()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPhone2().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getMobileNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMobileNo().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getEmailId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEmailId().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getPanNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPanNo().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getBankAccNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getBankAccNo().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getDateOfAppointment()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDateOfAppointment().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getMembershipLicNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMembershipLicNo().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getSapCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSapCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(retainerFlag).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(retainerFlag);
				
				
				if (CommonFunction.checkNull(vo.getRetainershipValidUpto()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRetainershipValidUpto().toUpperCase().trim());
				
				

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
				logger.info("IN insertAdvocateMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
			
				
				
				for (int i = 0; i < branchName.length; i++) {
					
					insertPrepStmtObject = null;
					bufInsSql = null;
					
					bufInsSql = new StringBuilder();
					insertPrepStmtObject = new PrepStmtObject();
					
					bufInsSql.append("INSERT INTO LEG_ADVOCATE_BRANCH_MX(ADVOCATE_CODE,ADVOCATE_DESC,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");//ADVOCATE_CODE
					bufInsSql.append(" ?,");//ADVOCATE_DESC
					bufInsSql.append(" ?,");//BRANCH_ID
					bufInsSql.append(" ?,");//REC_STATUS
					bufInsSql.append(" ?,"); //makerId
					bufInsSql.append(dbo);//MAKER_DATE
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					bufInsSql.append(" ?,");//AUTHER_ID
					bufInsSql.append(dbo);//AUTHER_DATE
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase());
					
					if (CommonFunction.checkNull(vo.getAdvocateCode()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getAdvocateCode().toUpperCase());
					

					if (CommonFunction.checkNull(branchName[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(branchName[i]);
				
					
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
					logger.info("IN LEG_ADVOCATE_BRANCH_MX: insert query1 ### "
							+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
				
				
           for (int i = 0; i < caseTypeCode.length; i++) {
					
					insertPrepStmtObject = null;
					bufInsSql = null;
					
					bufInsSql = new StringBuilder();
					insertPrepStmtObject = new PrepStmtObject();
					
					bufInsSql.append("INSERT INTO LEG_ADVOCATE_CASE_TYPE_MX(ADVOCATE_CODE,CASE_TYPE_CODE,ADVOCATE_DESC,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");//ADVOCATE_CODE
					bufInsSql.append(" ?,");//CASE_TYPE_CODE
					bufInsSql.append(" ?,");//,ADVOCATE_DESC
					bufInsSql.append(" ?,");//REC_STATUS
					bufInsSql.append(" ?,"); //makerId
					bufInsSql.append(dbo);//MAKER_DATE
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					bufInsSql.append(" ?,");//AUTHER_ID
					bufInsSql.append(dbo);//AUTHER_DATE
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase());
					
					if (CommonFunction.checkNull(caseTypeCode[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(caseTypeCode[i]);
					
					if (CommonFunction.checkNull(vo.getAdvocateCode()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getAdvocateCode().toUpperCase());
					
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
					logger.info("IN LEG_ADVOCATE_CASE_TYPE_MX() insert query1 ### "
							+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
				
				
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertAdvocateMaster......................"+ status);
				
				}//end of if(!st)
			
			
		}//end of try
		
		catch (Exception e) {
			e.printStackTrace();
		}

		
		finally
		{
			
			qryList.clear();
			qryList=null;
			
		}
		return status;

	}
	
	public ArrayList editAdvocateData(Object ob) {

		ArrayList searchlist = new ArrayList();
		AdvocateMasterVo vo = (AdvocateMasterVo)ob;
		ArrayList<AdvocateMasterVo> advocateList = new ArrayList<AdvocateMasterVo>();
		logger.info("editAdvocateData in searchBenchMarkRatioEdit &***************************** = "+vo.getAdvocateCode());

		try {
			

			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append(" SELECT  (SELECT USER_NAME  FROM SEC_USER_M WHERE USER_ID=ADVOCATE_CODE)ADCODE,ADVOCATE_DESC,ADVOCATE_TYPE_FLAG,LAW_FIRM_CODE,ADDRESS1,ADDRESS2," );
			bufInsSql.append(" COUNTRY,(SELECT COUNTRY_DESC FROM COM_COUNTRY_M WHERE COUNTRY_ID = LADM.COUNTRY) COUNTRY_DESC," );
			bufInsSql.append(" STATE,(SELECT STATE_DESC FROM COM_STATE_M WHERE STATE_ID = LADM.STATE) STATE_DESC,");
			bufInsSql.append(" CITY,(SELECT DISTRICT_DESC FROM COM_DISTRICT_M WHERE DISTRICT_ID = LADM.CITY) CITY_DESC,");
			bufInsSql.append(" ZIPCODE,PHONE1,PHONE2,MOBILE,EMAIL,PAN,BANK_ACCNO,");
			bufInsSql.append(dbo);
			bufInsSql.append(" DATE_FORMAT(DATE_OF_APPOINTMENT,'"+ dateFormat +"'), ");
			bufInsSql.append(" MEMBERSHIP_LICNO,TENURE_OF_RETAINERSHIP,REGULAR_RETAINER_FLAG," );
			bufInsSql.append(dbo);
			bufInsSql.append(" DATE_FORMAT(RETAINERSHIP_VALID_UP_TO,'"+ dateFormat +"'), ");
			bufInsSql.append(" REC_STATUS,ADVOCATE_CODE from LEG_ADVOCATE_DEF_M LADM ");
			bufInsSql.append("  WHERE ADVOCATE_CODE='"+StringEscapeUtils.escapeSql(vo.getLbxUserId())+"'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN editAdvocateData() search query1 ### "+ bufInsSql.toString());
			logger.info("editAdvocateData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("editAdvocateData " + searchlist.get(i).toString());
				
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					AdvocateMasterVo advocateVo = new AdvocateMasterVo();
					
					advocateVo.setLbxUserId(CommonFunction.checkNull(data.get(0)).toString());
					advocateVo.setAdvocateCode(CommonFunction.checkNull(data.get(1)).toString());
					advocateVo.setAdvocateTypeFlag(CommonFunction.checkNull(data.get(2)).toString());	
					advocateVo.setLawFirmCode(CommonFunction.checkNull(data.get(3)).toString());
					advocateVo.setAddress1(CommonFunction.checkNull(data.get(4)).toString());
					advocateVo.setAddress2(CommonFunction.checkNull(data.get(5)).toString());
					advocateVo.setTxtCountryCode(CommonFunction.checkNull(data.get(6)).toString());
					advocateVo.setCountry(CommonFunction.checkNull(data.get(7)).toString());
					advocateVo.setTxtStateCode(CommonFunction.checkNull(data.get(8)).toString());
					advocateVo.setState(CommonFunction.checkNull(data.get(9)).toString());
					advocateVo.setTxtDistCode(CommonFunction.checkNull(data.get(10)).toString());
					advocateVo.setDist(CommonFunction.checkNull(data.get(11)).toString());
					advocateVo.setZipCode(CommonFunction.checkNull(data.get(12)).toString());
					advocateVo.setPhone1(CommonFunction.checkNull(data.get(13)).toString());
					advocateVo.setPhone2(CommonFunction.checkNull(data.get(14)).toString());
					advocateVo.setMobileNo(CommonFunction.checkNull(data.get(15)).toString());
					advocateVo.setEmailId(CommonFunction.checkNull(data.get(16)).toString());
					advocateVo.setPanNo(CommonFunction.checkNull(data.get(17)).toString());
					advocateVo.setBankAccNo(CommonFunction.checkNull(data.get(18)).toString());
					advocateVo.setDateOfAppointment(CommonFunction.checkNull(data.get(19)).toString());
					advocateVo.setMembershipLicNo(CommonFunction.checkNull(data.get(20)).toString());
					advocateVo.setSapCode(CommonFunction.checkNull(data.get(21)).toString());
					advocateVo.setRegularRetainerFlag(CommonFunction.checkNull(data.get(22)).toString());
					advocateVo.setRetainershipValidUpto(CommonFunction.checkNull(data.get(23)).toString());
					advocateVo.setRecStatus(CommonFunction.checkNull(data.get(24)).toString());
					advocateVo.setLbxUserId(CommonFunction.checkNull(data.get(25)).toString());
					
					advocateList.add(advocateVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally
		{
			
			searchlist.clear();
			searchlist=null;
			
		}
		return advocateList;
	}
	
	
	public boolean updateAdvocateData(Object ob,String [] branchName,String [] caseTypeCode) {
		
		AdvocateMasterVo vo = (AdvocateMasterVo) ob;
		//String advocateCode = (String) vo.getAdvocateCode();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		
		boolean status = false;
		String stat = "";
		//stat = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRecStatus())).trim());
		
         String retainerFlag = "";
		
		//String advocateTypeFlag ="";
		//advocateTypeFlag = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAdvocateTypeFlag())).trim());
		try {
			
			if (vo.getRecStatus() != null
					&& vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}
			if (vo.getRegularRetainerFlag() != null
					&& vo.getRegularRetainerFlag().equals("on")) {
				retainerFlag = "Y";
			} else {
				retainerFlag = "N";
			}	
			
			StringBuilder bufInsSql = new StringBuilder();
			logger.info("In updateAdvocateData..........inside ejb server file............Dao Impl");
			
			bufInsSql.append(" UPDATE LEG_ADVOCATE_DEF_M SET ADVOCATE_DESC=? ");
			
			bufInsSql.append("  ,ADVOCATE_TYPE_FLAG = ?, LAW_FIRM_CODE = ? ,ADDRESS1 = ?,ADDRESS2 = ? ,COUNTRY = ?,STATE = ? ,CITY = ? ,ZIPCODE = ? ,PHONE1 = ?,PHONE2 = ? ");
			bufInsSql.append(" ,MOBILE = ? ,EMAIL = ?,PAN = ?,BANK_ACCNO = ?, MEMBERSHIP_LICNO = ?,TENURE_OF_RETAINERSHIP = ?,REGULAR_RETAINER_FLAG = ?, REC_STATUS =? " );
			
			
			bufInsSql.append(" ,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" ,AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" ,DATE_OF_APPOINTMENT=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" ,RETAINERSHIP_VALID_UP_TO=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where ADVOCATE_CODE=?");
			

			if (CommonFunction.checkNull(vo.getAdvocateCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAdvocateCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getAdvocateTypeFlag()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAdvocateTypeFlag().toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(vo.getLawFirmCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLawFirmCode().toUpperCase().trim());
			
			
			
			if (CommonFunction.checkNull(vo.getAddress1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAddress1().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getAddress2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAddress2().toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(vo.getTxtCountryCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtCountryCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getTxtStateCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtStateCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getTxtDistCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTxtDistCode()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getZipCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getZipCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getPhone1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPhone1().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getPhone2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPhone2().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getMobileNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMobileNo().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getEmailId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getEmailId().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getPanNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPanNo().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getBankAccNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getBankAccNo().toUpperCase().trim());
			
			
			
			if (CommonFunction.checkNull(vo.getMembershipLicNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMembershipLicNo().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getSapCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getSapCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(retainerFlag).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(retainerFlag);


			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			
			//----------------------------------
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
			
			//---------------------------------- 
			
			if (CommonFunction.checkNull(vo.getDateOfAppointment()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDateOfAppointment().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getRetainershipValidUpto()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRetainershipValidUpto().toUpperCase().trim());
			

			if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxUserId());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			logger.info("## In updateAdvocateData() : update query (case tye master) "+insertPrepStmtObject.printQuery());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			
			
			
			insertPrepStmtObject = null;
			bufInsSql = null;
			
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			
			bufInsSql.append(" DELETE FROM LEG_ADVOCATE_BRANCH_MX WHERE ADVOCATE_CODE='"+vo.getLbxUserId()+"'");
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			logger.info("## In updateAdvocateData() : Delete query for LEG_ADVOCATE_BRANCH_MX : ==>> "+insertPrepStmtObject.printQuery());
			
			updatelist.add(insertPrepStmtObject);
			
			
			for (int i = 0; i < branchName.length; i++) {
				
				insertPrepStmtObject = null;
				bufInsSql = null;
				
				bufInsSql = new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				
				bufInsSql.append("INSERT INTO LEG_ADVOCATE_BRANCH_MX(ADVOCATE_CODE,ADVOCATE_DESC,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//ADVOCATE_CODE
				bufInsSql.append(" ?,");//ADVOCATE_DESC
				bufInsSql.append(" ?,");//BRANCH_ID
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,"); //makerId
				bufInsSql.append(dbo);//MAKER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHER_ID
				bufInsSql.append(dbo);//AUTHER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase());
				
				if (CommonFunction.checkNull(vo.getAdvocateCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAdvocateCode().toUpperCase());
				if (CommonFunction.checkNull(branchName[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(branchName[i]);
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
				logger.info("IN updateAdvocateData() insert query1 ### "+ insertPrepStmtObject.printQuery());
				updatelist.add(insertPrepStmtObject);
			}
			
			
			insertPrepStmtObject = null;
			bufInsSql = null;
			
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			
			bufInsSql.append(" DELETE FROM LEG_ADVOCATE_CASE_TYPE_MX WHERE ADVOCATE_CODE='"+vo.getLbxUserId()+"'");
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			logger.info("## In updateAdvocateData() : Delete query for LEG_ADVOCATE_CASE_TYPE_MX : ==>> "+insertPrepStmtObject.printQuery());
			
			updatelist.add(insertPrepStmtObject);
			
			
			for (int i = 0; i < caseTypeCode.length; i++) {
				
				insertPrepStmtObject = null;
				bufInsSql = null;
				
				bufInsSql = new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				
				bufInsSql.append("INSERT INTO LEG_ADVOCATE_CASE_TYPE_MX(ADVOCATE_CODE,CASE_TYPE_CODE,ADVOCATE_DESC,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//ADVOCATE_CODE
				bufInsSql.append(" ?,");//CASE_TYPE_CODE
				bufInsSql.append(" ?,");//,ADVOCATE_DESC
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,"); //makerId
				bufInsSql.append(dbo);//MAKER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHER_ID
				bufInsSql.append(dbo);//AUTHER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase());
				
				if (CommonFunction.checkNull(caseTypeCode[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(caseTypeCode[i]);
				
				if (CommonFunction.checkNull(vo.getAdvocateCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAdvocateCode().toUpperCase());
				
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
				logger.info("IN updateAdvocateData() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				updatelist.add(insertPrepStmtObject);
			}
			
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

			
			
		
			} catch (Exception e) {
			e.printStackTrace();
		}
			
			finally
			{
				
				updatelist.clear();
				updatelist=null;
			}
		return status;
	}
	
	
	public ArrayList<AdvocateMasterVo> getBranchesForAdvocateEdit(String lbxUserId) {

		ArrayList searchlist = new ArrayList();
		
		ArrayList<AdvocateMasterVo> branchList = new ArrayList<AdvocateMasterVo>();

		try {
			logger.info("In getBranchesForAdvocateEdit().....................................Dao Impl");

			StringBuilder bufInsSql = new StringBuilder();
			
			bufInsSql.append(" SELECT BRANCH_ID,BRANCH_DESC FROM com_branch_m WHERE BRANCH_ID IN (SELECT BRANCH_ID FROM LEG_ADVOCATE_BRANCH_MX WHERE ADVOCATE_CODE = '"+lbxUserId+"') " );
					

			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN getBranchesForAdvocateEdit() search query1 ### "+ bufInsSql.toString());
			logger.info("getBranchesForAdvocateEdit " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("branchDescList " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					AdvocateMasterVo advocateVo = new AdvocateMasterVo();
					advocateVo.setBranchId(CommonFunction.checkNull(data.get(0)).toString());
					advocateVo.setBranchDesc(CommonFunction.checkNull(data.get(1)).toString());
					branchList.add(advocateVo);

				}

			}

			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			searchlist.clear();
			searchlist=null;
		}
		return branchList;
	}
	public ArrayList<AdvocateMasterVo> getcaseTypeForAdvocateEdit(String lbxUserId) {

		ArrayList searchlist = new ArrayList();
		
		ArrayList<AdvocateMasterVo> caseTypeList = new ArrayList<AdvocateMasterVo>();

		try {
			logger.info("In getcaseTypeForAdvocateEdit().....................................Dao Impl");

			StringBuilder bufInsSql = new StringBuilder();
			
			bufInsSql.append(" SELECT CASE_TYPE_CODE,CASE_TYPE_DESC FROM LEG_CASE_TYPE_DEF_M WHERE CASE_TYPE_CODE IN (SELECT CASE_TYPE_CODE FROM LEG_ADVOCATE_CASE_TYPE_MX WHERE ADVOCATE_CODE = '"+lbxUserId+"') " );
					

			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN getcaseTypeForAdvocateEdit() search query1 ### "+ bufInsSql.toString());
			logger.info("getcaseTypeForAdvocateEdit " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("branchDescList " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					AdvocateMasterVo advocateVo = new AdvocateMasterVo();
					advocateVo.setCaseTypeCode(CommonFunction.checkNull(data.get(0)).toString());
					advocateVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(1)).toString());
					
					caseTypeList.add(advocateVo);

				}

			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			searchlist.clear();
			searchlist=null;
			
		}
		return caseTypeList;
	}


	//Richa---------->courtType starts
		
	public ArrayList searchCourtTypeMasterData(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String courtTypeCode = null;
		String courtTypeDesc = null;
		ArrayList searchlist = new ArrayList();
		CourtTypeMasterVo CourtTypeMasterVo = (CourtTypeMasterVo) ob;
		ArrayList<CourtTypeMasterVo> detailList = new ArrayList<CourtTypeMasterVo>();
		try {

			logger.info("In searchCourtTypeMasterData()..............inside ejb server file.......................Dao Impl");
			
			courtTypeCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(CourtTypeMasterVo.getCourtTypeCode())).trim());
			courtTypeDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(CourtTypeMasterVo.getCourtTypeDesc())).trim());
           	StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT COURT_TYPE_CODE,COURT_TYPE_DESC,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM LEG_COURT_TYPE_DEF_M ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEG_COURT_TYPE_DEF_M ");
			bufInsSql.append(" WHERE 5=5 ");
			bufInsSqlTempCount.append(" WHERE 5=5 ");
			
			 if (!courtTypeCode.equals("")) {
				bufInsSql.append(" AND COURT_TYPE_CODE = '" + courtTypeCode + "' ");
				bufInsSqlTempCount.append(" AND COURT_TYPE_CODE = '" + courtTypeCode + "' ");
			}
			 if (!courtTypeDesc.equals("")) {
				bufInsSql.append(" AND COURT_TYPE_DESC like '%" + courtTypeDesc + "%' ");
				bufInsSqlTempCount.append(" AND COURT_TYPE_DESC like '%" + courtTypeDesc + "%' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((courtTypeCode.trim()==null && courtTypeDesc.trim()==null) || (courtTypeCode.trim().equalsIgnoreCase("") && courtTypeDesc.trim().equalsIgnoreCase("")) || CourtTypeMasterVo.getCurrentPageLink()>1)
//			{
			
				logger.info("current PAge Link no .................... "+CourtTypeMasterVo.getCurrentPageLink());
				if(CourtTypeMasterVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (CourtTypeMasterVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				bufInsSql.append(" ORDER BY COURT_TYPE_CODE OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							
//			}
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchCourtTypeMasterData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchCourtTypeMasterData.....................................Dao Impl");
			logger.info("searchCourtTypeMasterData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCourtTypeMasterDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					CourtTypeMasterVo deptMVO = new CourtTypeMasterVo();
					deptMVO.setCourtTypeCode("<a href=courtTypeMaster.do?method=openEditCourtTypeMaster&courtTypeCode="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					//deptMVO.setCourtTypeCode(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setCourtTypeDesc(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		/*if(searchlist.size()==0)
		{
			CourtTypeMasterVo deptMVO = new CourtTypeMasterVo();
			deptMVO.setTotalRecordSize(count);
			detailList.add(deptMVO);
			request.setAttribute("flag","yes");
			logger.info("Detail List when searchList is null: "+detailList.size());
		}*/

		return detailList;
	}
	
	
	public boolean insertCourtTypeMaster(Object ob) {
		CourtTypeMasterVo vo = (CourtTypeMasterVo) ob;
		boolean status = false;
		
		logger.info("In insertCourtTypeMaster.........inside ejb server file...........Dao Impl"
						+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

		String query = "select COURT_TYPE_CODE from LEG_COURT_TYPE_DEF_M where COURT_TYPE_CODE='"
				+ StringEscapeUtils.escapeSql(vo.getCourtTypeCode().trim()) + "'";
		logger.info("In insertCourtTypeMaster.......inside ejb server file..........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (vo.getRecStatus() != null
						&& vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert Court Type master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into LEG_COURT_TYPE_DEF_M(COURT_TYPE_CODE,COURT_TYPE_DESC,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //caseTypeCode
				bufInsSql.append(" ?,"); //caseTypeDesc
				bufInsSql.append(" ?,"); //rec_status
				bufInsSql.append(" ?,"); //makerId
				//bufInsSql.append(dbo);
				//bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				//bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			
				if (CommonFunction.checkNull(vo.getCourtTypeCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCourtTypeCode()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getCourtTypeDesc())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCourtTypeDesc()
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
				
				/*
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
				
				*/

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertCourtTypeMaster() insert query1 ### "
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
	
	public ArrayList editCourtTypeData(Object ob) {

		ArrayList searchlist = new ArrayList();
		CourtTypeMasterVo vo = (CourtTypeMasterVo)ob;
		ArrayList<CourtTypeMasterVo> courtTypeList = new ArrayList<CourtTypeMasterVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getCourtTypeCode());

		try {
			

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" select  COURT_TYPE_CODE,COURT_TYPE_DESC,REC_STATUS  from LEG_COURT_TYPE_DEF_M ");
			
			bufInsSql.append("  WHERE COURT_TYPE_CODE='"+StringEscapeUtils.escapeSql(vo.getCourtTypeCode())+"'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN editCourtTypeData() search query1 ### "+ bufInsSql.toString());
			logger.info("editCourtTypeData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("editCourtTypeData " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					CourtTypeMasterVo courtTypeDataVo = new CourtTypeMasterVo();
					
					courtTypeDataVo.setCourtTypeCode(CommonFunction.checkNull(data.get(0)).toString());
					courtTypeDataVo.setCourtTypeDesc(CommonFunction.checkNull(data.get(1)).toString());					
					courtTypeDataVo.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
					courtTypeList.add(courtTypeDataVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return courtTypeList;
	}

	
		
		public boolean updateCourtTypeData(Object ob) {
			CourtTypeMasterVo vo = (CourtTypeMasterVo) ob;
			String CourtTypeCode = (String) vo.getCourtTypeCode();
			logger.info("vo.getRecStatus():-" + vo.getRecStatus());
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			ArrayList updatelist = new ArrayList();
			boolean status = false;
			String stat = "";

			try {
				if (vo.getRecStatus() != null
						&& vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";
				}
				StringBuffer bufInsSql = new StringBuffer();
				logger.info("In updateCourtTypeData..........inside ejb server file............Dao Impl");
				bufInsSql.append(" UPDATE LEG_COURT_TYPE_DEF_M set COURT_TYPE_DESC=?,");
				bufInsSql.append(" REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as varchar(30))),0,9) ");
				bufInsSql.append(" where COURT_TYPE_CODE=?");

				if (CommonFunction.checkNull(vo.getCourtTypeDesc()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCourtTypeDesc().toUpperCase()
							.trim());

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

				if (CommonFunction.checkNull(vo.getCourtTypeCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCourtTypeCode());

				insertPrepStmtObject.setSql(bufInsSql.toString());

				updatelist.add(insertPrepStmtObject);
				logger.info("In getListOfValues" + bufInsSql.toString());
				logger.info("## In updateCourtTypeData() : update Court Type master : ==>> "+insertPrepStmtObject.printQuery());
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return status;
		}
	//	Richa---------->courtType ends
		
		//Richa---------->courtName starts
		
		public ArrayList searchCourtNameMasterData(Object ob) {
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			String courtNameCode = null;
			String courtNameDesc = null;
			String courtType = null;
			String branch = null;
			ArrayList searchlist = new ArrayList();
			CourtNameMasterVo courtNameMasterVo = (CourtNameMasterVo) ob;
			ArrayList<CourtNameMasterVo> detailList = new ArrayList<CourtNameMasterVo>();
			try {

				logger.info("In searchCourtNameMasterData()..............inside ejb server file.......................Dao Impl");
				
				courtNameCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(courtNameMasterVo.getCourtNameCode())).trim());
				courtNameDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(courtNameMasterVo.getCourtNameDesc())).trim());
				courtType = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(courtNameMasterVo.getLbxCourtTypeCode())).trim());
				branch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(courtNameMasterVo.getLbxBranchId())).trim());
				StringBuffer bufInsSql = new StringBuffer();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
				bufInsSql.append("SELECT COURT_NAME_CODE,COURT_NAME_DESC,(SELECT COURT_TYPE_DESC FROM LEG_COURT_TYPE_DEF_M WHERE COURT_TYPE_CODE = LCNDM.COURT_TYPE_CODE) COURTDESC,");
				bufInsSql.append("(SELECT BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID = LCNDM.BRANCH_ID) BRANCH,");
				bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
				bufInsSql.append(" FROM LEG_COURT_NAME_DEF_M LCNDM ");
				bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEG_COURT_NAME_DEF_M ");
				bufInsSql.append(" WHERE 5=5 ");
				bufInsSqlTempCount.append(" WHERE 5=5 ");
				
				 if (!courtNameCode.equals("")) {
					bufInsSql.append(" AND COURT_NAME_CODE = '" + courtNameCode + "' ");
					bufInsSqlTempCount.append(" AND COURT_NAME_CODE = '" + courtNameCode + "' ");
				}
				 if (!courtNameDesc.equals("")) {
					bufInsSql.append(" AND COURT_NAME_DESC like '%" + courtNameDesc + "%' ");
					bufInsSqlTempCount.append(" AND COURT_NAME_DESC like '%" + courtNameDesc + "%' ");
				}
				 if (!courtType.equals("")) {
					bufInsSql.append(" AND COURT_TYPE_CODE = '" + courtType + "' ");
					bufInsSqlTempCount.append(" AND COURT_TYPE_CODE = '" + courtType + "' ");
				}
				 if (!branch.equals("")) {
					bufInsSql.append(" AND BRANCH_ID = '" + branch + "' ");
					bufInsSqlTempCount.append(" AND BRANCH_ID ='" + branch+ "' ");
				}
				logger.info("search Query...." + bufInsSql);
				logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	            
				count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
				
//				if((courtNameCode.trim()==null && courtNameDesc.trim()==null) || (courtNameCode.trim().equalsIgnoreCase("") && courtNameDesc.trim().equalsIgnoreCase("")) || CourtNameeMasterVo.getCurrentPageLink()>1)
//				{
				
					logger.info("current PAge Link no .................... "+courtNameMasterVo.getCurrentPageLink());
					if(courtNameMasterVo.getCurrentPageLink()>1)
					{
						startRecordIndex = (courtNameMasterVo.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						logger.info("startRecordIndex .................... "+startRecordIndex);
						logger.info("endRecordIndex .................... "+endRecordIndex);
					}
					//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
					bufInsSql.append(" ORDER BY COURT_NAME_CODE OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
								
//				}
				logger.info("query : "+bufInsSql);

				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

				logger.info("IN searchCourtNameMasterData() search query1 ### "+ bufInsSql.toString());
				logger.info("In searchCourtNameMasterData.....................................Dao Impl");
				logger.info("searchCourtNameMasterData " + searchlist.size());

				for (int i = 0; i < searchlist.size(); i++) {
					// logger.info("searchCourtNameMasterDataList "+searchlist.get(i).toString());

					ArrayList data = (ArrayList) searchlist.get(i);

					if (data.size() > 0) {
						CourtNameMasterVo deptMVO = new CourtNameMasterVo();
						deptMVO.setCourtNameCode("<a href=courtNameMaster.do?method=openEditCourtNameMaster&courtNameCode="
										+ CommonFunction.checkNull(data.get(0)).toString()
										+ ">"
										+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
						//deptMVO.setCourtNameCode(CommonFunction.checkNull(data.get(0)).toString());
						deptMVO.setCourtNameDesc(CommonFunction.checkNull(data.get(1)).toString());
						deptMVO.setCourtType(CommonFunction.checkNull(data.get(2)).toString());
						deptMVO.setBranch(CommonFunction.checkNull(data.get(3)).toString());
						deptMVO.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
						deptMVO.setTotalRecordSize(count);
						detailList.add(deptMVO);
					}

				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			/*if(searchlist.size()==0)
			{
				CourtNameMasterVo deptMVO = new CourtNameMasterVo();
				deptMVO.setTotalRecordSize(count);
				detailList.add(deptMVO);
				request.setAttribute("flag","yes");
				logger.info("Detail List when searchList is null: "+detailList.size());
			}*/

			return detailList;
		}
		
		
		public boolean insertCourtNameMaster(Object ob) {
			CourtNameMasterVo vo = (CourtNameMasterVo) ob;
			boolean status = false;
			
			logger.info("In insertCourtNameMaster.........inside ejb server file...........Dao Impl"
							+ vo.getRecStatus());
			
			logger.info("## In insertCourtNameMaster() : branch : ==>> " +vo.getLbxBranchId());
			logger.info("## In insertCourtNameMaster() : court name : ==>> " +vo.getCourtNameCode());
			logger.info("## In insertCourtNameMaster() : court name desc : ==>> " +vo.getCourtNameDesc());
			logger.info("## In insertCourtNameMaster() : court type : ==>> " +vo.getLbxCourtTypeCode());
			logger.info("## In insertCourtNameMaster() : recstatus : ==>> " +vo.getRecStatus());
			
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			String stat = "X";

			String query = "select COURT_NAME_CODE from LEG_COURT_NAME_DEF_M where COURT_NAME_CODE='"
					+ StringEscapeUtils.escapeSql(vo.getCourtNameCode().trim()) + "'";
			logger.info("In insertCourtNameMaster.......inside ejb server file..........Dao Impl"
							+ query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			try {

				if (!st) {
					if (vo.getRecStatus() != null
							&& vo.getRecStatus().equals("on")) {
						stat = "A";
					} else {
						stat = "X";

					}

					logger.info("In insert Court Name master");
					StringBuffer bufInsSql = new StringBuffer();
					bufInsSql.append("insert into LEG_COURT_NAME_DEF_M(COURT_NAME_CODE,COURT_NAME_DESC,COURT_TYPE_CODE,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,"); //courtNameCode
					bufInsSql.append(" ?,"); //courtNameDesc
					bufInsSql.append(" ?,"); //courtTypeCode
					bufInsSql.append(" ?,"); //branch id
					bufInsSql.append(" ?,"); //rec_status
					bufInsSql.append(" ?,"); //makerId
					//bufInsSql.append(dbo);
					//bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					//bufInsSql.append(" ?,");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				
					if (CommonFunction.checkNull(vo.getCourtNameCode())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getCourtNameCode()
								.toUpperCase().trim());
					
					
					if (CommonFunction.checkNull(vo.getCourtNameDesc())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getCourtNameDesc()
								.toUpperCase().trim());
					
					
				 if (CommonFunction.checkNull(vo.getLbxCourtTypeCode())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxCourtTypeCode()
								.toUpperCase().trim());
				 
					if (CommonFunction.checkNull(vo.getLbxBranchId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxBranchId()
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
					
					/*
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
					
					*/

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertCourtNameMaster() insert query1 ### "
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
		
		public ArrayList editCourtNameData(Object ob) {

			ArrayList searchlist = new ArrayList();
			CourtNameMasterVo vo = (CourtNameMasterVo)ob;
			ArrayList<CourtNameMasterVo> courtNameList = new ArrayList<CourtNameMasterVo>();
			logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getCourtNameCode());

			try {
				

				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("SELECT COURT_NAME_CODE,COURT_NAME_DESC,(SELECT COURT_TYPE_DESC FROM LEG_COURT_TYPE_DEF_M WHERE COURT_TYPE_CODE = LCNDM.COURT_TYPE_CODE) COURTDESC, COURT_TYPE_CODE,");
				bufInsSql.append("(SELECT BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID = LCNDM.BRANCH_ID) BRANCH,BRANCH_ID,REC_STATUS");
				//bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
				bufInsSql.append(" FROM LEG_COURT_NAME_DEF_M LCNDM ");
				//bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEG_COURT_NAME_DEF_M ");
				
				bufInsSql.append("  WHERE COURT_NAME_CODE='"+StringEscapeUtils.escapeSql(vo.getCourtNameCode())+"'");
				logger.info("search Query...." + bufInsSql);

				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
				logger.info("IN editCourtNameData() search query1 ### "+ bufInsSql.toString());
				logger.info("editCourtNameData " + searchlist.size());
				for (int i = 0; i < searchlist.size(); i++) {
					logger.info("editCourtNameData " + searchlist.get(i).toString());
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						CourtNameMasterVo courtNameDataVo = new CourtNameMasterVo();
						
						courtNameDataVo.setCourtNameCode(CommonFunction.checkNull(data.get(0)).toString());
						courtNameDataVo.setCourtNameDesc(CommonFunction.checkNull(data.get(1)).toString());					
						courtNameDataVo.setCourtType(CommonFunction.checkNull(data.get(2)).toString());
						courtNameDataVo.setLbxCourtTypeCode(CommonFunction.checkNull(data.get(3)).toString());
						courtNameDataVo.setBranch(CommonFunction.checkNull(data.get(4)).toString());
						courtNameDataVo.setLbxBranchId(CommonFunction.checkNull(data.get(5)).toString());
						courtNameDataVo.setRecStatus(CommonFunction.checkNull(data.get(6)).toString());
						courtNameList.add(courtNameDataVo);

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return courtNameList;
		}
			
			public boolean updateCourtNameData(Object ob) {
				CourtNameMasterVo vo = (CourtNameMasterVo) ob;
				String CourtNameCode = (String) vo.getCourtNameCode();
				logger.info("vo.getRecStatus():-" + vo.getRecStatus());
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				ArrayList updatelist = new ArrayList();
				boolean status = false;
				String stat = "";

				try {
					if (vo.getRecStatus() != null
							&& vo.getRecStatus().equals("on")) {
						stat = "A";
					} else {
						stat = "X";
					}
					StringBuffer bufInsSql = new StringBuffer();
					logger.info("In updateCourtNameData..........inside ejb server file............Dao Impl");
					bufInsSql.append(" UPDATE LEG_COURT_NAME_DEF_M set COURT_NAME_DESC=?,COURT_TYPE_CODE=?,BRANCH_ID=?,");
					bufInsSql.append(" REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as varchar(30))),0,9) ");
					bufInsSql.append(" where COURT_NAME_CODE=?");

					if (CommonFunction.checkNull(vo.getCourtNameDesc()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getCourtNameDesc().toUpperCase().trim());
					if (CommonFunction.checkNull(vo.getLbxCourtTypeCode()).equalsIgnoreCase(""))
				        insertPrepStmtObject.addNull();
					
			         else
				       insertPrepStmtObject.addString(vo.getLbxCourtTypeCode().toUpperCase().trim());
					if (CommonFunction.checkNull(vo.getLbxBranchId()).equalsIgnoreCase(""))
				       insertPrepStmtObject.addNull();
			        else
				       insertPrepStmtObject.addString(vo.getLbxBranchId().toUpperCase().trim());

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
					//----------------------------------

					if (CommonFunction.checkNull(vo.getCourtNameCode())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getCourtNameCode());

					insertPrepStmtObject.setSql(bufInsSql.toString());

					updatelist.add(insertPrepStmtObject);
					logger.info("In getListOfValues" + bufInsSql.toString());
					logger.info("## In updateCourtNameData() : update Court Name master : ==>> "+insertPrepStmtObject.printQuery());
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return status;
			}
			
			
			//Richa---------->POA Master starts
			public ArrayList searchPOAMasterData(Object ob) {
				int count=0;
				int startRecordIndex=0;
				int endRecordIndex = no;
				String poaCode = null;
				String poaDesc = null;
				String branch = null;
				String courtNameCode = null;
				ArrayList searchlist = new ArrayList();
				POAMasterVo poaMasterVo = (POAMasterVo) ob;
				ArrayList<POAMasterVo> detailList = new ArrayList<POAMasterVo>();
				try {

					logger.info("In searchPOAMasterData()..............inside ejb server file.......................Dao Impl");
					
					poaCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(poaMasterVo.getLbxUserId())).trim());
					
					branch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(poaMasterVo.getLbxBranchId())).trim());
					courtNameCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(poaMasterVo.getLbxCourtNameCode())).trim());
		           
					StringBuilder bufInsSql = new StringBuilder();
					StringBuilder bufInsSqlTempCount = new StringBuilder();
					
					bufInsSql.append(" select distinct a.POA_CODE,b.user_name,e.COURT_NAME_DESC,d.BRANCH_DESC,");
					bufInsSql.append(" case when a.REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS");
					bufInsSql.append(" from LEG_POA_DEF_M a");
					bufInsSql.append(" inner join SEC_USER_M b on b.user_id=a.POA_CODE");
					bufInsSql.append(" inner join LEG_POA_COURT_NAME_MX c on c.POA_CODE=a.POA_CODE");
					bufInsSql.append(" inner join COM_BRANCH_M d on d.BRANCH_ID=a.BRANCH_ID");
					bufInsSql.append(" inner join LEG_COURT_NAME_DEF_M e on e.COURT_NAME_CODE=c.COURT_NAME_CODE");
					
					
					
					bufInsSqlTempCount.append(" select count(1)");
					bufInsSqlTempCount.append(" from LEG_POA_DEF_M a");
					bufInsSqlTempCount.append(" inner join SEC_USER_M b on b.user_id=a.POA_CODE");
					bufInsSqlTempCount.append(" inner join LEG_POA_COURT_NAME_MX c on c.POA_CODE=a.POA_CODE");
					bufInsSqlTempCount.append(" inner join COM_BRANCH_M d on d.BRANCH_ID=a.BRANCH_ID");
					
					bufInsSql.append(" WHERE 5=5 ");
					bufInsSqlTempCount.append(" WHERE 5=5 ");
					logger.info(" In searchPOAMasterData():::  ---->>>> ");
				
				   if (!poaCode.equals("")) {
						bufInsSql.append(" AND a.POA_CODE = '" + poaCode + "' ");
						bufInsSqlTempCount.append(" AND a.POA_CODE = '" + poaCode + "' ");
					}
				   if (!branch.equals("")) {
						bufInsSql.append(" AND a.BRANCH_ID like '%" + branch + "%' ");
						bufInsSqlTempCount.append(" AND a.BRANCH_ID like '%" + branch + "%' ");
					}
				   if (!courtNameCode.equals("")) {
						bufInsSql.append(" AND c.COURT_NAME_CODE like '%" + courtNameCode + "%' ");
						bufInsSqlTempCount.append(" AND c.COURT_NAME_CODE like '%" + courtNameCode + "%' ");
					}
				logger.info("search Query...." + bufInsSql);
					logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
		            
					count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
					

					
						logger.info("current PAge Link no .................... "+poaMasterVo.getCurrentPageLink());
						if(poaMasterVo.getCurrentPageLink()>1)
						{
							startRecordIndex = (poaMasterVo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							logger.info("startRecordIndex .................... "+startRecordIndex);
							logger.info("endRecordIndex .................... "+endRecordIndex);
						}
						
						bufInsSql.append(" ORDER BY POA_CODE OFFSET ");
						bufInsSql.append(startRecordIndex);
						bufInsSql.append(" ROWS FETCH next ");
						bufInsSql.append(endRecordIndex);
						bufInsSql.append(" ROWS ONLY ");
									

					logger.info("query : "+bufInsSql);

					searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

					logger.info("IN searchPOAMasterData() search query1 ### "+ bufInsSql.toString());
					logger.info("In searchPOAMasterData.....................................Dao Impl");
					logger.info("searchPOAMasterData " + searchlist.size());

					for (int i = 0; i < searchlist.size(); i++) {
						

						ArrayList data = (ArrayList) searchlist.get(i);

						if (data.size() > 0) {
							POAMasterVo deptMVO = new POAMasterVo();
							deptMVO.setPoaCode("<a href=poaMaster.do?method=openEditPOAMaster&poaCode="
											+ CommonFunction.checkNull(data.get(0)).toString()
											+ ">"
											+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
							deptMVO.setPoaDesc(CommonFunction.checkNull(data.get(1)).toString());
							deptMVO.setCourtName(CommonFunction.checkNull(data.get(2)).toString());
							deptMVO.setBranch(CommonFunction.checkNull(data.get(3)).toString());
							deptMVO.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
							deptMVO.setTotalRecordSize(count);
							detailList.add(deptMVO);
						}

					}
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			
			finally
			{
				
				searchlist.clear();
				searchlist=null;
			}

				return detailList;
			}
			
			public boolean insertPOAMaster(Object ob, String [] courtName) {
				POAMasterVo vo = (POAMasterVo) ob;
				boolean status = false;
				
				logger.info("In insertPOAMaster.........inside ejb server file...........Dao Impl"+ vo.getRecStatus());
				ArrayList qryList = new ArrayList();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				String stat = "X";
				logger.info("Before select query : " + stat); 
				logger.info("Before select vo.getPoa() : " + vo.getPoa());
				String query = "select POA_CODE from LEG_POA_DEF_M where POA_CODE='"+CommonFunction.checkNull(vo.getPoa()) + "'";
				logger.info("query query:::::"+query);
				boolean st = ConnectionDAOforEJB.checkStatus(query);
				
				try {

					if (!st) {
						if (vo.getRecStatus() != null
								&& vo.getRecStatus().equals("on")) {
							stat = "A";
						} else {
							stat = "X";

						}
						logger.info("vo.getLbxBranchId()ssssssssss"+vo.getLbxBranchId());
						logger.info("In insert POA master");
						StringBuilder bufInsSql = new StringBuilder();
						bufInsSql.append("insert into LEG_POA_DEF_M(POA_CODE,BRANCH_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,"); //POA code
						bufInsSql.append(" ?,"); //branch
						bufInsSql.append(" ?,"); //rec_status
						bufInsSql.append(" ?,"); //makerId
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					
						if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase().trim());
						
						if (CommonFunction.checkNull(vo.getLbxBranchId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getLbxBranchId()
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
						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN insertPOAMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
						for (int i = 0; i < courtName.length; i++) {
							
							insertPrepStmtObject = null;
							bufInsSql = null;
							
							bufInsSql = new StringBuilder();
							insertPrepStmtObject = new PrepStmtObject();
							
							bufInsSql.append("INSERT INTO LEG_POA_COURT_NAME_MX(POA_CODE,COURT_NAME_CODE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?,");//POA_CODE
							bufInsSql.append(" ?,");//COURT_NAME_CODE
							bufInsSql.append(" ?,");//REC_STATUS
							bufInsSql.append(" ?,"); //makerId
							bufInsSql.append(dbo);//MAKER_DATE
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
							bufInsSql.append(" ?,");//AUTHER_ID
							bufInsSql.append(dbo);//AUTHER_DATE
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							
							if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase());
							
							
							logger.info("insertPOAMaster:::::::::::: "+ courtName[i]);

							if (CommonFunction.checkNull(courtName[i])
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(courtName[i]);

							
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
							logger.info("IN insertPOAMaster() insert query1 ### "
									+ insertPrepStmtObject.printQuery());
							qryList.add(insertPrepStmtObject);
						}
						status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
						logger.info("In insertPOAMaster......................"
								+ status);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				finally
				{
					
					qryList.clear();
					qryList=null;
				}

				return status;

			}
			
			public ArrayList openEditPOAData(Object ob) {

				ArrayList searchlist = new ArrayList();
				POAMasterVo vo = (POAMasterVo)ob;
				ArrayList<POAMasterVo> poaList = new ArrayList<POAMasterVo>();
				logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getPoaCode());

				try {
					StringBuilder bufInsSql = new StringBuilder();
					bufInsSql.append(" select  a.POA_CODE,d.USER_NAME,a.BRANCH_ID,c.BRANCH_DESC,a.REC_STATUS from LEG_POA_DEF_M  a ");
					bufInsSql.append(" inner join LEG_POA_COURT_NAME_MX b on a.POA_CODE=b.POA_CODE ");
					bufInsSql.append(" inner join COM_BRANCH_M c on a.BRANCH_ID=c.BRANCH_ID ");
					bufInsSql.append(" inner join sec_user_m d on d.USER_ID=a.POA_CODE ");
					
					bufInsSql.append("  WHERE a.POA_CODE='"+StringEscapeUtils.escapeSql(vo.getPoaCode())+"'");
					logger.info("search Query...." + bufInsSql);

					searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
					logger.info("IN editPOAData() search query1 ### "+ bufInsSql.toString());
					logger.info("editPOAData " + searchlist.size());
					for (int i = 0; i < searchlist.size(); i++) {
						logger.info("editPOAData " + searchlist.get(i).toString());
						ArrayList data = (ArrayList) searchlist.get(i);
						if (data.size() > 0) {
							POAMasterVo poaDataVo = new POAMasterVo();
							
							poaDataVo.setLbxUserId(CommonFunction.checkNull(data.get(0)).toString());
							poaDataVo.setUserId(CommonFunction.checkNull(data.get(1)).toString());	
							poaDataVo.setLbxBranchId(CommonFunction.checkNull(data.get(2)).toString());
							poaDataVo.setBranch(CommonFunction.checkNull(data.get(3)).toString());
							poaDataVo.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
							poaList.add(poaDataVo);

						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				finally
				{
					
					searchlist.clear();
					searchlist=null;
				}

				return poaList;
			}
				
				public boolean updatePOAData(Object ob, String[] courtName) {
					POAMasterVo vo = (POAMasterVo) ob;
					String POACode = (String) vo.getPoaCode();
					logger.info("vo.getRecStatus():-" + vo.getRecStatus());
					PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
					ArrayList updatelist = new ArrayList();
					boolean status = false;
					String stat = null;

					try {
						if (vo.getRecStatus() != null
								&& vo.getRecStatus().equals("on")) {
							stat = "A";
						} else {
							stat = "X";
						}
						StringBuilder bufInsSql = new StringBuilder();
						logger.info("In updatePOAData..........inside ejb server file............Dao Impl");
						bufInsSql.append(" UPDATE LEG_POA_DEF_M set BRANCH_ID=?,");
						bufInsSql.append(" REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as varchar(30))),0,9) ");
						bufInsSql.append(" where POA_CODE=? ");

						if (CommonFunction.checkNull(vo.getLbxBranchId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getLbxBranchId().toUpperCase().trim());

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
						//----------------------------------

						if (CommonFunction.checkNull(vo.getLbxUserId())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getLbxUserId());

						insertPrepStmtObject.setSql(bufInsSql.toString());

						updatelist.add(insertPrepStmtObject);
						logger.info("In getListOfValues" + bufInsSql.toString());
						logger.info("## In updatePOAData() : update POA master : ==>> "+insertPrepStmtObject.printQuery());
						
						
						//
						updatelist.add(insertPrepStmtObject);
						logger.info("In getListOfValues" + bufInsSql.toString());
						
						
						
						insertPrepStmtObject = null;
						bufInsSql = null;
						
						bufInsSql = new StringBuilder();
						insertPrepStmtObject = new PrepStmtObject();
						
						bufInsSql.append(" DELETE FROM LEG_POA_COURT_NAME_MX WHERE POA_CODE='"+vo.getLbxUserId()+"'");
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						
						logger.info("## In updateAdvocateData() : Delete query for LEG_ADVOCATE_BRANCH_MX : ==>> "+insertPrepStmtObject.printQuery());
						
						updatelist.add(insertPrepStmtObject);
						
						
						for (int i = 0; i < courtName.length; i++) {
							
							insertPrepStmtObject = null;
							bufInsSql = null;
							
							bufInsSql = new StringBuilder();
							insertPrepStmtObject = new PrepStmtObject();
							
							bufInsSql.append("INSERT INTO LEG_POA_COURT_NAME_MX(POA_CODE,COURT_NAME_CODE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?,");//POA_CODE
							bufInsSql.append(" ?,");//COURT_NAME_CODE
							bufInsSql.append(" ?,");//REC_STATUS
							bufInsSql.append(" ?,"); //makerId
							bufInsSql.append(dbo);//MAKER_DATE
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
							bufInsSql.append(" ?,");//AUTHER_ID
							bufInsSql.append(dbo);//AUTHER_DATE
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							
							if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase());
							logger.info("value of court name::::::"+ courtName[i]);
							

							if (CommonFunction.checkNull(courtName[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(courtName[i]);

							
							
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
							logger.info("IN updatePOAmaster() insert query1 ### "
									+ insertPrepStmtObject.printQuery());
							updatelist.add(insertPrepStmtObject);
						}
						

			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

					
						} catch (Exception e) {
						e.printStackTrace();
					}
						
						
						finally
						{
							
							updatelist.clear();
							updatelist=null;
						}	
					return status;
				}
						
						
				public ArrayList<POAMasterVo> getcourtNameForPOaEdit(String poaCode) {
 
					ArrayList searchlist = new ArrayList();
					
					ArrayList<POAMasterVo> courtList = new ArrayList<POAMasterVo>();

					try {
						logger
								.info("In searchUserBranchEdit().....................................Dao Impl");

						StringBuilder bufInsSql = new StringBuilder();
						
						bufInsSql.append(" SELECT COURT_NAME_CODE,COURT_NAME_DESC FROM LEG_COURT_NAME_DEF_M WHERE COURT_NAME_CODE IN (SELECT COURT_NAME_CODE FROM LEG_POA_COURT_NAME_MX WHERE POA_CODE = '"+poaCode+"') " );
								

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
								POAMasterVo poaVo = new POAMasterVo();
								poaVo.setCourtName(CommonFunction.checkNull(data.get(0)).toString());
								poaVo.setCourtNameDesc(CommonFunction.checkNull(data.get(1)).toString());
								
								courtList.add(poaVo);

							}

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						
						searchlist.clear();
						searchlist=null;
					}
					return courtList;
				}
				
				
				
				
				// nazia work space starts---

				
				
				// Notice type master starts
				public ArrayList searchNoticeTypeMasterData(Object ob) {
					int count=0;
					int startRecordIndex=0;
					int endRecordIndex = no;
					String noticeTypeCode =null;
					String noticeTypeDesc = null;
					ArrayList searchlist = new ArrayList();
					NoticeTypeMasterVo NoticeTypeMasterVo = (NoticeTypeMasterVo) ob;
					ArrayList<NoticeTypeMasterVo> detailList = new ArrayList<NoticeTypeMasterVo>();
					try {

						logger.info("In searchNoticeTypeMasterData()..............inside ejb server file.......................Dao Impl");
						
						noticeTypeCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(NoticeTypeMasterVo.getNoticeTypeCode())).trim());
						noticeTypeDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(NoticeTypeMasterVo.getNoticeTypeDesc())).trim());
			           	StringBuilder bufInsSql = new StringBuilder();
						StringBuilder bufInsSqlTempCount = new StringBuilder();
						bufInsSql.append("SELECT NOTICE_TYPE_CODE,NOTICE_TYPE_DESC,");
						bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
						bufInsSql.append(" FROM LEG_NOTICE_TYPE_DEF_M ");
						bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEG_NOTICE_TYPE_DEF_M ");
						bufInsSql.append(" WHERE 5=5 ");
						bufInsSqlTempCount.append(" WHERE 5=5 ");
						
						if (!noticeTypeCode.equals("")) {
							bufInsSql.append(" AND NOTICE_TYPE_CODE = '" + noticeTypeCode + "' ");
							bufInsSqlTempCount.append(" AND NOTICE_TYPE_CODE = '" + noticeTypeCode + "' ");
						}
						if (!noticeTypeDesc.equals("")) {
							bufInsSql.append(" AND NOTICE_TYPE_DESC like '%" + noticeTypeDesc + "%' ");
							bufInsSqlTempCount.append(" AND NOTICE_TYPE_DESC like '%" + noticeTypeDesc + "%' ");
						}
						logger.info("search Query...." + bufInsSql);
						logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			            
						count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
						
//						if((noticeTypeCode.trim()==null && noticeTypeDesc.trim()==null) || (noticeTypeCode.trim().equalsIgnoreCase("") && noticeTypeDesc.trim().equalsIgnoreCase("")) || NoticeTypeMasterVo.getCurrentPageLink()>1)
//						{
						
							logger.info("current PAge Link no .................... "+NoticeTypeMasterVo.getCurrentPageLink());
							if(NoticeTypeMasterVo.getCurrentPageLink()>1)
							{
								startRecordIndex = (NoticeTypeMasterVo.getCurrentPageLink()-1)*no;
								endRecordIndex = no;
								logger.info("startRecordIndex .................... "+startRecordIndex);
								logger.info("endRecordIndex .................... "+endRecordIndex);
							}
							//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
							bufInsSql.append(" ORDER BY NOTICE_TYPE_CODE OFFSET ");
							bufInsSql.append(startRecordIndex);
							bufInsSql.append(" ROWS FETCH next ");
							bufInsSql.append(endRecordIndex);
							bufInsSql.append(" ROWS ONLY ");
										
//						}
						logger.info("query : "+bufInsSql);

						searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

						logger.info("IN searchNoticeTypeMasterData() search query1 ### "+ bufInsSql.toString());
						logger.info("In searchNoticeTypeMasterData.....................................Dao Impl");
						logger.info("searchNoticeTypeMasterData " + searchlist.size());

						for (int i = 0; i < searchlist.size(); i++) {
							// logger.info("searchNoticeTypeMasterDataList "+searchlist.get(i).toString());

							ArrayList data = (ArrayList) searchlist.get(i);

							if (data.size() > 0) {
								NoticeTypeMasterVo deptMVO = new NoticeTypeMasterVo();
								deptMVO.setNoticeTypeCode("<a href=noticeTypeMaster.do?method=openEditNoticeTypeMaster&noticeTypeCode="
												+ CommonFunction.checkNull(data.get(0)).toString()
												+ ">"
												+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
								//deptMVO.setNoticeTypeCode(CommonFunction.checkNull(data.get(0)).toString());
								deptMVO.setNoticeTypeDesc(CommonFunction.checkNull(data.get(1)).toString());
								deptMVO.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
								deptMVO.setTotalRecordSize(count);
								detailList.add(deptMVO);
							}

						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}
					/*if(searchlist.size()==0)
					{
						NoticeTypeMasterVo deptMVO = new NoticeTypeMasterVo();
						deptMVO.setTotalRecordSize(count);
						detailList.add(deptMVO);
						request.setAttribute("flag","yes");
						logger.info("Detail List when searchList is null: "+detailList.size());
					}*/
					finally{
						searchlist.clear();
						searchlist = null;
						
					         }
					return detailList;
				}
				
				
				public boolean insertNoticeTypeMaster(Object ob) {
					NoticeTypeMasterVo vo = (NoticeTypeMasterVo) ob;
					boolean status = false;
					
					logger.info("In insertNoticeTypeMaster.........inside ejb server file...........Dao Impl"
									+ vo.getRecStatus());
					ArrayList qryList = new ArrayList();
					PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
					String stat = "X";

					String query = "select NOTICE_TYPE_CODE from LEG_NOTICE_TYPE_DEF_M where NOTICE_TYPE_CODE='"
							+ StringEscapeUtils.escapeSql(vo.getNoticeTypeCode().trim()) + "'";
					logger.info("In insertNoticeTypeMaster.......inside ejb server file..........Dao Impl"
									+ query);
					boolean st = ConnectionDAOforEJB.checkStatus(query);
					try {

						if (!st) {
							if (vo.getRecStatus() != null
									&& vo.getRecStatus().equals("on")) {
								stat = "A";
							} else {
								stat = "X";

							}

							logger.info("In insert Notice Type master");
							StringBuilder bufInsSql = new StringBuilder();
							bufInsSql.append("insert into LEG_NOTICE_TYPE_DEF_M(NOTICE_TYPE_CODE,NOTICE_TYPE_DESC,REC_STATUS,MAKER_ID,MAKER_DATE)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?,"); //noticeTypeCode
							bufInsSql.append(" ?,"); //noticeTypeDesc
							bufInsSql.append(" ?,"); //rec_status
							bufInsSql.append(" ?,"); //makerId
							//bufInsSql.append(dbo);
							//bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
							//bufInsSql.append(" ?,");
							bufInsSql.append(dbo);
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
						
						
							if (CommonFunction.checkNull(vo.getNoticeTypeCode())
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getNoticeTypeCode()
										.toUpperCase().trim());
							
							if (CommonFunction.checkNull(vo.getNoticeTypeDesc())
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getNoticeTypeDesc()
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
							
							/*
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
							
							*/

							insertPrepStmtObject.setSql(bufInsSql.toString());
							logger.info("IN insertNoticeTypeMaster() insert query1 ### "
									+ insertPrepStmtObject.printQuery());
							qryList.add(insertPrepStmtObject);
							status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
							logger.info("In saveCountryData......................"
									+ status);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					finally{
						qryList.clear();
						qryList = null;
						
					         }
					return status;

				}
				public ArrayList editNoticeTypeData(Object ob) {

					ArrayList searchlist = new ArrayList();
					NoticeTypeMasterVo vo = (NoticeTypeMasterVo)ob;
					ArrayList<NoticeTypeMasterVo> noticeTypeList = new ArrayList<NoticeTypeMasterVo>();
					logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getNoticeTypeCode());

					try {
						

						StringBuilder bufInsSql = new StringBuilder();
						bufInsSql.append(" select  NOTICE_TYPE_CODE,NOTICE_TYPE_DESC,REC_STATUS from LEG_NOTICE_TYPE_DEF_M ");
						
						bufInsSql.append("  WHERE NOTICE_TYPE_CODE='"+StringEscapeUtils.escapeSql(vo.getNoticeTypeCode())+"'");
						logger.info("search Query...." + bufInsSql);

						searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
						logger.info("IN editNoticeTypeData() search query1 ### "+ bufInsSql.toString());
						logger.info("editNoticeTypeData " + searchlist.size());
						for (int i = 0; i < searchlist.size(); i++) {
							logger.info("editNoticeTypeData " + searchlist.get(i).toString());
							ArrayList data = (ArrayList) searchlist.get(i);
							if (data.size() > 0) {
								NoticeTypeMasterVo noticeTypeMasterVo = new NoticeTypeMasterVo();
								
								noticeTypeMasterVo.setNoticeTypeCode(CommonFunction.checkNull(data.get(0)).toString());
								noticeTypeMasterVo.setNoticeTypeDesc(CommonFunction.checkNull(data.get(1)).toString());
								
								noticeTypeMasterVo.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
								
								noticeTypeList.add(noticeTypeMasterVo);

							}

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					finally{
						searchlist.clear();
						searchlist = null;
						
					         }
					return noticeTypeList;
				}
				
				public boolean updateNoticeTypeData(Object ob) {
					NoticeTypeMasterVo vo = (NoticeTypeMasterVo) ob;
					String NoticeTypeCode = (String) vo.getNoticeTypeCode();
					logger.info("vo.getRecStatus():-" + vo.getRecStatus());
					PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
					ArrayList updatelist = new ArrayList();
					boolean status = false;
					String stat = "";

					try {
						if (vo.getRecStatus() != null
								&& vo.getRecStatus().equals("on")) {
							stat = "A";
						} else {
							stat = "X";
						}
						StringBuilder bufInsSql = new StringBuilder();
						logger.info("In updateCourtTypeData..........inside ejb server file............Dao Impl");
						bufInsSql.append(" UPDATE LEG_NOTICE_TYPE_DEF_M set NOTICE_TYPE_DESC=?,");
						bufInsSql.append(" REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as varchar(30))),0,9) ");
						bufInsSql.append(" where NOTICE_TYPE_CODE=?");

						if (CommonFunction.checkNull(vo.getNoticeTypeDesc()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getNoticeTypeDesc().toUpperCase().trim());

						if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(stat);

						//----------------------------------
						if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerId());
						if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerDate());
						//----------------------------------

						if (CommonFunction.checkNull(vo.getNoticeTypeCode()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getNoticeTypeCode());

						insertPrepStmtObject.setSql(bufInsSql.toString());

						updatelist.add(insertPrepStmtObject);
						logger.info("In getListOfValues" + bufInsSql.toString());
						logger.info("## In updateNoticeTypeData() : update Notice Type master : ==>> "+insertPrepStmtObject.printQuery());
						status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

						
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally{
						updatelist.clear();
						updatelist = null;
						
					         }
					return status;
				}
				// notice type master ends ------
				
				//stage master starts ---
				
				public ArrayList searchStageTypeMasterData(Object ob) {
					int count=0;
					int startRecordIndex=0;
					int endRecordIndex = no;
					String stageCode = null;
					String stageTypeDesc = null;
					String sequenceNumber = null;
					String caseType = null;
					String stageProduct= null;
					ArrayList searchlist = new ArrayList();
					StageTypeMasterVo stageTypeMasterVo = (StageTypeMasterVo) ob;
					ArrayList<StageTypeMasterVo> detailList = new ArrayList<StageTypeMasterVo>();
					try {

						logger.info("In searchstageTypeMasterData() ");
						
						stageCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(stageTypeMasterVo.getStageCode())).trim());
						stageTypeDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(stageTypeMasterVo.getStageTypeDesc())).trim());
						sequenceNumber = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(stageTypeMasterVo.getSequenceNumber())).trim());
						caseType = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(stageTypeMasterVo.getLbxCaseTypeCode())).trim());
						stageProduct = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(stageTypeMasterVo.getLbxProductSearchID())).trim());
			           	StringBuilder bufInsSql = new StringBuilder();
						StringBuilder bufInsSqlTempCount = new StringBuilder();
						bufInsSql.append("SELECT distinct a.STAGE_CODE,a.STAGE_DESC,SEQUENCE_NO,c.CASE_TYPE_DESC,case when CLOSURE_STAGE_FLAG='Y' then 'Yes' else 'No' end as CLOSURE_STAGE_FLAG,");
						bufInsSql.append("case when a.REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
						bufInsSql.append(" FROM LEG_STAGE_DEF_M a ");
						bufInsSql.append(" left join LEG_STAGE_PRODUCT_MX b on b.STAGE_CODE=a.STAGE_CODE");
						bufInsSql.append(" left join LEG_CASE_TYPE_DEF_M c on c.CASE_TYPE_CODE=a.CASE_TYPE_CODE");

						bufInsSqlTempCount.append(" select count(distinct a.STAGE_CODE)");
						bufInsSqlTempCount.append(" from LEG_STAGE_DEF_M a");
						bufInsSqlTempCount.append(" left join LEG_STAGE_PRODUCT_MX b on b.STAGE_CODE=a.STAGE_CODE");
						bufInsSql.append(" WHERE 5=5 ");
						bufInsSqlTempCount.append(" WHERE 5=5 ");
							
						
						if (!caseType.equals("")) {
								bufInsSql.append(" AND a.CASE_TYPE_CODE = '" + caseType +  "' ");
								bufInsSqlTempCount.append(" AND a.CASE_TYPE_CODE = '" + caseType +  "' ");
							}
							
							 if (!stageProduct.equals("")) {
								bufInsSql.append(" AND b.PRODUCT_ID='"+ stageProduct + "' ");
								bufInsSqlTempCount.append(" AND b.PRODUCT_ID='"+ stageProduct + "' ");
							}
						count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
						logger.info("current PAge Link no .................... "+stageTypeMasterVo.getCurrentPageLink());
						
			            
						count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
						
							
							if(stageTypeMasterVo.getCurrentPageLink()>1)
							{
								startRecordIndex = (stageTypeMasterVo.getCurrentPageLink()-1)*no;
								endRecordIndex = no;
								logger.info("startRecordIndex .................... "+startRecordIndex);
								logger.info("endRecordIndex .................... "+endRecordIndex);
							}
							
							bufInsSql.append(" ORDER BY a.STAGE_CODE OFFSET ");
							bufInsSql.append(startRecordIndex);
							bufInsSql.append(" ROWS FETCH next ");
							bufInsSql.append(endRecordIndex);
							bufInsSql.append(" ROWS ONLY ");
										

							logger.info("## In searchStageTypeMasterData(): select query for LEG_STAGE_DEF_M: ==>" +bufInsSql.toString());


						searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
						int size=0;
						if(searchlist!=null)
							size=searchlist.size();
						
						logger.info("searchStageTypeMasterData " + size);

						for (int i = 0; i < size; i++) {
							
							ArrayList data = (ArrayList) searchlist.get(i);

							if (data.size() > 0) {
								StageTypeMasterVo deptMVO = new StageTypeMasterVo();
								deptMVO.setStageCode("<a href=stageTypeMaster.do?method=openEditStageTypeMaster&stageCode="
												+ CommonFunction.checkNull(data.get(0)).toString()
												+ ">"
												+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
								
								deptMVO.setStageTypeDesc(CommonFunction.checkNull(data.get(1)).toString());
								
								deptMVO.setSequenceNumber(CommonFunction.checkNull(data.get(2)).toString());
								deptMVO.setCaseType(CommonFunction.checkNull(data.get(3)).toString());
								deptMVO.setClosureStage(CommonFunction.checkNull(data.get(4)).toString());
								deptMVO.setRecStatus(CommonFunction.checkNull(data.get(5)).toString());
								
								deptMVO.setTotalRecordSize(count);
								detailList.add(deptMVO);
							}

						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					finally
					{
						
						searchlist.clear();
						searchlist=null;
					}

					return detailList;
				}
				
				
				public boolean insertStageTypeMaster(Object ob,String [] productIdList,String [] paymentStageFlagList,String [] repetitiveFlagList,String productIds) {
					StageTypeMasterVo vo = (StageTypeMasterVo) ob;
					boolean status = false;
					
					logger.info("In insertStageTypeMaster.........inside ejb server file...........Dao Impl"
									+ vo.getRecStatus());
					ArrayList qryList = new ArrayList();
					PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
					String stat = "X";
					String closureStage = "N";

					String query = "select STAGE_CODE from LEG_STAGE_DEF_M  where STAGE_CODE='"
							+ StringEscapeUtils.escapeSql(vo.getStageCode().trim()) + "'";
					
					
					logger.info("## insertStageTypeMaster() : query for duplicate stage : ==>> "+ query);
					
					
					boolean st = ConnectionDAOforEJB.checkStatus(query);
					
					
					logger.info("## insertStageTypeMaster() : st : ==>> "+ st);
					
					
					try {
						logger.info("In insertStageTypeMaster.......inside ejb server file..........Dao Impl");
						if (!st) {
							if (vo.getRecStatus() != null
									&& vo.getRecStatus().equals("on")) {
								stat = "A";
							} else {
								stat = "X";
							}
								
							if (vo.getClosureStage() != null
									&& vo.getClosureStage().equals("on")) {
								closureStage = "Y";
							} else {
								closureStage = "N";

							}
							
							String query1 = "select CASE_TYPE_CODE from LEG_STAGE_DEF_M  where CASE_TYPE_CODE='"+ StringEscapeUtils.escapeSql(vo.getLbxCaseTypeCode().trim()) + "' AND SEQUENCE_NO='"+StringEscapeUtils.escapeSql(vo.getSequenceNumber().trim())+"' ";
							
							logger.info("## insertStageTypeMaster() : query for duplicate of case type and sequence : ==>> "+ query1);
							
							boolean comb = ConnectionDAOforEJB.checkStatus(query1);
							
							logger.info("## insertStageTypeMaster() : comb : ==>> "+ comb);
							
						
							if (!comb){ 
							

							logger.info("In insert Stage Type master");
							StringBuilder bufInsSql = new StringBuilder();
							bufInsSql.append("insert into LEG_STAGE_DEF_M (STAGE_CODE,STAGE_DESC,SEQUENCE_NO,CASE_TYPE_CODE,REC_STATUS,CLOSURE_STAGE_FLAG,MAKER_ID,MAKER_DATE)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?,"); //STAGE_CODE
							bufInsSql.append(" ?,"); //STAGE_DESC
							bufInsSql.append(" ?,"); //SEQUENCE_NO
							bufInsSql.append(" ?,"); //CASE_TYPE_CODE
							bufInsSql.append(" ?,"); //REC_STATUS
							bufInsSql.append(" ?,"); //CLOSURE_STAGE_FLAG
			                bufInsSql.append(" ?,"); //makerId
							
							bufInsSql.append(dbo);
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
						
						
							if (CommonFunction.checkNull(vo.getStageCode()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getStageCode().toUpperCase().trim());
							
							if (CommonFunction.checkNull(vo.getStageTypeDesc()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getStageTypeDesc().toUpperCase().trim());
							
							if (CommonFunction.checkNull(vo.getSequenceNumber()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getSequenceNumber().toUpperCase().trim());
							
							if (CommonFunction.checkNull(vo.getLbxCaseTypeCode()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getLbxCaseTypeCode().toUpperCase().trim());
							
							
							if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(stat);
							
							if (CommonFunction.checkNull(closureStage).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(closureStage
										.toUpperCase().trim());
							

							if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getMakerId());
							
							
							if (CommonFunction.checkNull(vo.getMakerDate())
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getMakerDate());
							
							

							insertPrepStmtObject.setSql(bufInsSql.toString());
							logger.info("IN insertStageTypeMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
							qryList.add(insertPrepStmtObject);
							
							logger.info("## In insertStageTypeMaster() : productIds ==>> "+productIds);
							logger.info("## In insertStageTypeMaster() : CommonFunction.checkNull(productIds) ==>> "+CommonFunction.checkNull(productIds));
							logger.info("## In insertStageTypeMaster() : productIdList ==>> "+productIdList);
							
							int length=0;
							if(((productIdList!=null) && (!CommonFunction.checkNull(productIds).equalsIgnoreCase(""))));
							length= productIdList.length;
							
							
							
							logger.info("IN insertStageTypeMaster(): length1: "+length);
							
							if(CommonFunction.checkNull(productIds).equals(""))
							{
								length = 0;
							}
							
							logger.info("IN insertStageTypeMaster(): length2: "+length);
								
							for (int i = 0; i < length; i++) {
								
								insertPrepStmtObject = null;
								bufInsSql = null;
								
								bufInsSql = new StringBuilder();
								insertPrepStmtObject = new PrepStmtObject();
								
								bufInsSql.append("INSERT INTO LEG_STAGE_PRODUCT_MX(STAGE_CODE,STAGE_DESC,PRODUCT_ID,PAYMENT_STAGE_FLAG,REPETITIVE_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
								bufInsSql.append(" values ( ");
								bufInsSql.append(" ?,");//STAGE_CODE
								bufInsSql.append(" ?,");//STAGE_DESC
								bufInsSql.append(" ?,");//PRODUCT_ID
								bufInsSql.append(" ?,");//PAYMENT_STAGE_FLAG
								bufInsSql.append(" ?,");//REPETITIVE_FLAG
								bufInsSql.append(" ?,");//REC_STATUS
								bufInsSql.append(" ?,"); //makerId
								bufInsSql.append(dbo);//MAKER_DATE
								bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
								bufInsSql.append(" ?,");//AUTHER_ID
								bufInsSql.append(dbo);//AUTHER_DATE
								bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
								
								if (CommonFunction.checkNull(vo.getStageCode())
										.equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getStageCode()
											.toUpperCase());
								
								if (CommonFunction.checkNull(vo.getStageTypeDesc())
										.equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getStageTypeDesc()
											.toUpperCase());
								

								if (CommonFunction.checkNull(productIdList[i])
										.equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(productIdList[i]);
								
								if (CommonFunction.checkNull(paymentStageFlagList[i])
										.equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(paymentStageFlagList[i]);
								
								if (CommonFunction.checkNull(repetitiveFlagList[i])
										.equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(repetitiveFlagList[i]);

								
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
								logger.info("IN insertStageTypeMaster() insert query1 ### "
										+ insertPrepStmtObject.printQuery());
								qryList.add(insertPrepStmtObject);
							}
							
							
							
							status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
							logger.info("In insertStageTypeMaster......................"
									+ status);
							}
						}

						
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						
						qryList.clear();
						qryList=null;
					}

					return status;
					

					
				}
				
				public ArrayList editStageTypeData(Object ob) {

					ArrayList searchlist = new ArrayList();
					StageTypeMasterVo vo = (StageTypeMasterVo)ob;
					ArrayList<StageTypeMasterVo> stageTypeList = new ArrayList<StageTypeMasterVo>();
					logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getStageCode());

					try {
						logger.info("## In editStageTypeData(): ");

						StringBuilder bufInsSql = new StringBuilder();
						bufInsSql.append(" select a.STAGE_CODE,a.STAGE_DESC,a.SEQUENCE_NO,a.CASE_TYPE_CODE, ");
						bufInsSql.append(" a.REC_STATUS,a.CLOSURE_STAGE_FLAG,b.CASE_TYPE_DESC  from LEG_STAGE_DEF_M a ");
						bufInsSql.append(" inner join LEG_CASE_TYPE_DEF_M b on a.CASE_TYPE_CODE=b.CASE_TYPE_CODE ");
						
						bufInsSql.append("  WHERE STAGE_CODE='"+StringEscapeUtils.escapeSql(vo.getStageCode())+"'");
						logger.info("## In editStageTypeData(): search Query...." + bufInsSql);

						searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
						int size=0;
						if(searchlist!=null)
							size=searchlist.size();
						logger.info("IN editStageTypeData() search query1 ### "+ bufInsSql.toString());
						logger.info("editStageTypeData " + size);
						for (int i = 0; i < size; i++) {
							logger.info("editStageTypeData " + searchlist.get(i).toString());
							ArrayList data = (ArrayList) searchlist.get(i);
							if (data.size() > 0) {
								StageTypeMasterVo stageTypeMasterVo = new StageTypeMasterVo();
								
								stageTypeMasterVo.setStageCode(CommonFunction.checkNull(data.get(0)).toString());
								stageTypeMasterVo.setStageTypeDesc(CommonFunction.checkNull(data.get(1)).toString());
								stageTypeMasterVo.setSequenceNumber(CommonFunction.checkNull(data.get(2)).toString());
								stageTypeMasterVo.setLbxCaseTypeCode(CommonFunction.checkNull(data.get(3)).toString());
								stageTypeMasterVo.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
								stageTypeMasterVo.setClosureStage(CommonFunction.checkNull(data.get(5)).toString());
								stageTypeMasterVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(6)).toString());
								stageTypeList.add(stageTypeMasterVo);

							}

						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						
						searchlist.clear();
						searchlist=null;
					}

					return stageTypeList;
				}
				
				public boolean updateStageTypeData(Object ob,String [] productIdList,String [] paymentStageFlagList,String [] repetitiveFlagList,String productIds) {
					StageTypeMasterVo vo = (StageTypeMasterVo) ob;
					String StageCode = (String) vo.getStageCode();
					logger.info("vo.getRecStatus():-" + vo.getRecStatus());
					PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
					ArrayList updatelist = new ArrayList();
					boolean status = false;
					String stat = "";
			        String ClosureStat="";
					try {
						logger.info("## In updateStageTypeData(): ");
						if (vo.getRecStatus() != null
								&& vo.getRecStatus().equals("on")) {
							stat = "A";
						} else {
							stat = "X";
						}
						if (vo.getClosureStage() != null
								&& vo.getClosureStage().equals("on")) {
							ClosureStat = "Y";
						} else {
							ClosureStat = "N";
						}
						
						
						String query1 = "select CASE_TYPE_CODE from LEG_STAGE_DEF_M  where CASE_TYPE_CODE='"+ StringEscapeUtils.escapeSql(vo.getLbxCaseTypeCode().trim()) + "' AND SEQUENCE_NO='"+StringEscapeUtils.escapeSql(vo.getSequenceNumber().trim())+"' AND STAGE_CODE<>'"+StringEscapeUtils.escapeSql(vo.getStageCode().trim())+"' ";
						
						logger.info("## insertStageTypeMaster() : query for duplicate of case type and sequence : ==>> "+ query1);
						
						boolean comb = ConnectionDAOforEJB.checkStatus(query1);
						
						logger.info("## insertStageTypeMaster() : comb : ==>> "+ comb);
						
						if (!comb){ 
						
						StringBuilder bufInsSql = new StringBuilder();
						logger.info("In updateCourtTypeData..........inside ejb server file............Dao Impl");
						bufInsSql.append(" UPDATE LEG_STAGE_DEF_M set STAGE_DESC=?,SEQUENCE_NO=?,CASE_TYPE_CODE=?,");
						bufInsSql.append(" REC_STATUS=?,CLOSURE_STAGE_FLAG=?,MAKER_ID=?,MAKER_DATE=");
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as varchar(30))),0,9) ");
						bufInsSql.append(" where STAGE_CODE=?");

						if (CommonFunction.checkNull(vo.getStageTypeDesc()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getStageTypeDesc().toUpperCase()
									.trim());
						if (CommonFunction.checkNull(vo.getSequenceNumber()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSequenceNumber().toUpperCase()
							.trim());
						if (CommonFunction.checkNull(vo.getLbxCaseTypeCode()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxCaseTypeCode().toUpperCase()
							.trim());


						if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(stat);
						
						if (CommonFunction.checkNull(ClosureStat).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(ClosureStat);
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

						if (CommonFunction.checkNull(vo.getStageCode())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getStageCode());

						insertPrepStmtObject.setSql(bufInsSql.toString());

						updatelist.add(insertPrepStmtObject);
						logger.info("In getListOfValues" + bufInsSql.toString());
						logger.info("## In updateStageTypeData() : updateStageTypeData: ==>> "+insertPrepStmtObject.printQuery());
						
						
						insertPrepStmtObject = null;
						bufInsSql = null;
						
						bufInsSql = new StringBuilder();
						insertPrepStmtObject = new PrepStmtObject();
						
						bufInsSql.append(" DELETE FROM LEG_STAGE_PRODUCT_MX WHERE STAGE_CODE='"+vo.getStageCode()+"'");
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						
						updatelist.add(insertPrepStmtObject);
						
						int length=0;
						if(((productIdList!=null) && (!CommonFunction.checkNull(productIds).equalsIgnoreCase(""))));
						length= productIdList.length;
						
						
						
						logger.info("IN insertStageTypeMaster(): length1: "+length);
						
						if(CommonFunction.checkNull(productIds).equals(""))
						{
							length = 0;
						}
						
						logger.info("IN insertStageTypeMaster(): length2: "+length);

						for (int i = 0; i < length; i++) {
							
							insertPrepStmtObject = null;
							bufInsSql = null;
							
							bufInsSql = new StringBuilder();
							insertPrepStmtObject = new PrepStmtObject();
							
							bufInsSql.append("INSERT INTO LEG_STAGE_PRODUCT_MX(STAGE_CODE,STAGE_DESC,PRODUCT_ID,PAYMENT_STAGE_FLAG,REPETITIVE_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
							bufInsSql.append(" values ( ");
							bufInsSql.append(" ?,");//STAGE_CODE
							bufInsSql.append(" ?,");//STAGE_DESC
							bufInsSql.append(" ?,");//PRODUCT_ID
							bufInsSql.append(" ?,");//PAYMENT_STAGE_FLAG
							bufInsSql.append(" ?,");//REPETITIVE_FLAG
							bufInsSql.append(" ?,");//REC_STATUS
							bufInsSql.append(" ?,"); //makerId
							bufInsSql.append(dbo);//MAKER_DATE
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
							bufInsSql.append(" ?,");//AUTHER_ID
							bufInsSql.append(dbo);//AUTHER_DATE
							bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
							
							if (CommonFunction.checkNull(vo.getStageCode())
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getStageCode()
										.toUpperCase());
							
							if (CommonFunction.checkNull(vo.getStageTypeDesc())
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getStageTypeDesc()
										.toUpperCase());
							

							if (CommonFunction.checkNull(productIdList[i])
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(productIdList[i]);
							
							if (CommonFunction.checkNull(paymentStageFlagList[i])
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(paymentStageFlagList[i]);
							
							if (CommonFunction.checkNull(repetitiveFlagList[i])
									.equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(repetitiveFlagList[i]);

							
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
							logger.info("IN insertUserMaster() insert query1 ### "
									+ insertPrepStmtObject.printQuery());
							updatelist.add(insertPrepStmtObject);
						}
						
						status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
						
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					finally
					{
						
						updatelist.clear();
						updatelist=null;
					}
					return status;
				}
				
				public ArrayList getProduct(Object ob) {

					ArrayList list = new ArrayList();
					StageTypeMasterVo vo = (StageTypeMasterVo)ob;
					ArrayList<StageTypeMasterVo> productList = new ArrayList<StageTypeMasterVo>();
					logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getStageCode());

					try {
						logger.info("## In getProduct():  ");
						

						StringBuilder bufInsSql = new StringBuilder();
						bufInsSql.append(" SELECT PRODUCT_ID,PRODUCT_DESC FROM CR_PRODUCT_M WHERE REC_STATUS='A' ");
						
						
						logger.info("IN getProduct() search query1 ### "+ bufInsSql.toString());
						list = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
						 int size=0;
						 if(list!=null)
							 size=list.size();
						logger.info("getProduct " + size);
						for (int i = 0; i < size; i++) {
							logger.info("getProduct " + list.get(i).toString());
							ArrayList data = (ArrayList) list.get(i);
							if (data.size() > 0) {
								StageTypeMasterVo stageTypeMasterVo = new StageTypeMasterVo();
								
								stageTypeMasterVo.setProductCode(CommonFunction.checkNull(data.get(0)).toString());
								stageTypeMasterVo.setProductDesc(CommonFunction.checkNull(data.get(1)).toString());
			
								productList.add(stageTypeMasterVo);

							}

					}

					
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						
						list.clear();
						list=null;
					}
					
					return productList;
				}
				
				public ArrayList getProductOnEdit(Object ob) {

					ArrayList list = new ArrayList();
					StageTypeMasterVo vo = (StageTypeMasterVo)ob;
					ArrayList<StageTypeMasterVo> productList = new ArrayList<StageTypeMasterVo>();
					logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getStageCode());

					try {
						logger.info("## In getProductOnEdit():");

						StringBuilder bufInsSql = new StringBuilder();
						bufInsSql.append(" SELECT CPM.PRODUCT_ID,PRODUCT_DESC,LSPM.PRODUCT_ID,PAYMENT_STAGE_FLAG,REPETITIVE_FLAG FROM CR_PRODUCT_M CPM ");
						bufInsSql.append(" LEFT JOIN LEG_STAGE_PRODUCT_MX LSPM ON (LSPM.PRODUCT_ID=CPM.PRODUCT_ID AND STAGE_CODE = '"+vo.getStageCode()+"') ");
						bufInsSql.append(" WHERE CPM.REC_STATUS='A' ");
						
						
						logger.info("IN getProduct() search query1 ### "+ bufInsSql.toString());

						list = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
						
						int size=0;
						if(list!=null)
							size= list.size();
						logger.info("getProduct " +size);
						for (int i = 0; i < size; i++) {
							//logger.info("getProduct " + list.get(i).toString());
							ArrayList data = (ArrayList) list.get(i);
							if (data.size() > 0) {
								StageTypeMasterVo stageTypeMasterVo = new StageTypeMasterVo();
								
								stageTypeMasterVo.setProductCode(CommonFunction.checkNull(data.get(0)).toString());
								stageTypeMasterVo.setProductDesc(CommonFunction.checkNull(data.get(1)).toString());
								stageTypeMasterVo.setProduct(CommonFunction.checkNull(data.get(2)).toString());
								stageTypeMasterVo.setPaymentStage(CommonFunction.checkNull(data.get(3)).toString());
								stageTypeMasterVo.setRepetitive(CommonFunction.checkNull(data.get(4)).toString());
			
								productList.add(stageTypeMasterVo);

							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						
						list.clear();
						list=null;
					}
					return productList;
				}
				
				
				
				
				
				
				// stage mster ends ---

				
					
			//// nazia work space ends---
		
			public boolean checkExistingPAN(Object ob,String insertUpdateFlag){
				LawFirmMasterVo vo = (LawFirmMasterVo) ob;
				String query;
				if(CommonFunction.checkNull(insertUpdateFlag).equalsIgnoreCase("Y"))
					query = "select LAWFIRM_CODE from LEG_LAWFIRM_DEF_M where PAN='"+ (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPanNo())).trim()) + "'";
				else
					query = "select LAWFIRM_CODE from LEG_LAWFIRM_DEF_M where LAWFIRM_CODE<>'"+ (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLawFirmCode())).trim()) + "' and PAN='"+ (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPanNo())).trim()) + "'";
				
				logger.info("checkExistingPAN query : "	+ query);
				boolean st = ConnectionDAOforEJB.checkStatus(query);
				return st;
			}	
			
			public boolean checkExistingPANAdvocate(Object ob,String insertUpdateFlag){
				AdvocateMasterVo vo = (AdvocateMasterVo) ob;
				String query;
				if(CommonFunction.checkNull(insertUpdateFlag).equalsIgnoreCase("Y"))
					query = "select ADVOCATE_CODE from LEG_ADVOCATE_DEF_M where PAN='"+ (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPanNo())).trim()) + "'";
				else
					query = "select ADVOCATE_CODE from LEG_ADVOCATE_DEF_M where ADVOCATE_CODE<>'"+ (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAdvocateCode())).trim()) + "' and PAN='"+ (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPanNo())).trim()) + "'";
				
				logger.info("checkExistingPAN Advocate query : "	+ query);
				boolean st = ConnectionDAOforEJB.checkStatus(query);
				return st;
			}	
}

