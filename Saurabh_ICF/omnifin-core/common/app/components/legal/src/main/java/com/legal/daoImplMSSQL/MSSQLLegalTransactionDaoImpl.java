package com.legal.daoImplMSSQL;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;



import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.legal.dao.LegalTransactionDAO;
import com.legal.vo.LegalCaseFileVo;
import com.legal.vo.LegalCaseInitiationMakerVo;
import com.legal.vo.LegalCourtProcessingVo;
import com.legal.vo.LegalNoticeInitiationVo;
import com.legal.vo.ReassignCaseVo;


public class MSSQLLegalTransactionDaoImpl implements LegalTransactionDAO{

	private static final Logger logger = Logger.getLogger(MSSQLLegalTransactionDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dbo=resource.getString("lbl.dbPrefix");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
	public ArrayList searchLegalNoticeInitiationMakerData(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String loanNo = "";
		String noticeCode = "";
		ArrayList searchlist = new ArrayList();
		LegalNoticeInitiationVo legalNoticeVo = (LegalNoticeInitiationVo) ob;
		ArrayList<LegalNoticeInitiationVo> detailList = new ArrayList<LegalNoticeInitiationVo>();
		try {

			logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
			
			loanNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(legalNoticeVo.getLbxLoanId())).trim());
			noticeCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(legalNoticeVo.getLbxNoticeCode())).trim());
           	StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT NOTICE_ID,(SELECT DISTINCT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID=LND.LOAN_NO) AS LAN,(SELECT DISTINCT NOTICE_TYPE_DESC FROM LEG_NOTICE_TYPE_DEF_M WHERE NOTICE_TYPE_CODE = LND.NOTICE_CODE) AS NOTICE,");
			bufInsSql.append("case REC_STATUS when 'A' then 'Approved' when 'F' then 'Forwarded' when 'X' then 'Reject' else 'Pending' end as REC_STATUS ");
			bufInsSql.append(" FROM LEGAL_NOTICE_DTL LND WHERE REC_STATUS = 'P' ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEGAL_NOTICE_DTL WHERE REC_STATUS = 'P'  ");
			


			if (!loanNo.equals("")) {
				bufInsSql.append(" AND LOAN_NO = '" + loanNo + "' ");
				bufInsSqlTempCount.append(" AND LOAN_NO = '" + loanNo + "' ");
			}
			 if (!noticeCode.equals("")) {
				bufInsSql.append(" AND NOTICE_CODE = '" + noticeCode + "' ");
				bufInsSqlTempCount.append(" AND NOTICE_CODE = '" + noticeCode + "' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

			
				logger.info("current PAge Link no .................... "+legalNoticeVo.getCurrentPageLink());
				if(legalNoticeVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (legalNoticeVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				bufInsSql.append(" ORDER BY NOTICE_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							

			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchCaseTypeMasterData.....................................Dao Impl");
			logger.info("searchCaseTypeMasterData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					LegalNoticeInitiationVo noticeVo = new LegalNoticeInitiationVo();
					noticeVo.setNoticeId("<a href=legalNoticeInitiationMaker.do?method=openEditLegalNoticeInitiationMaker&noticeId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					//deptMVO.setloanNo(CommonFunction.checkNull(data.get(0)).toString());
					noticeVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
					noticeVo.setNoticeDesc(CommonFunction.checkNull(data.get(2)).toString());
					noticeVo.setRecStatus(CommonFunction.checkNull(data.get(3)).toString());
					noticeVo.setTotalRecordSize(count);
					detailList.add(noticeVo);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	
	public boolean insertLegalNoticeInitiationMakerData(Object ob) {
		LegalNoticeInitiationVo vo = (LegalNoticeInitiationVo) ob;
		boolean status = false;
		
		logger.info("In insertCaseTypeMaster.........inside ejb server file...........Dao Impl"
						+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		String notice = CommonFunction.checkNull(vo.getLbxNoticeCode());
		
		String updateForwardFlag = CommonFunction.checkNull(vo.getUpdateForwardFlag());
		
		boolean st = true;
		
		if(notice.equalsIgnoreCase("NOTICE138"))
		{
			st = false;
			
			String query = "SELECT LOAN_NO FROM LEGAL_NOTICE_DTL WHERE NOTICE_CODE = 'LRN' AND LOAN_NO = '"+ StringEscapeUtils.escapeSql(vo.getLbxLoanId().trim()) + "' AND REC_STATUS = 'D'";
			
			logger.info("## In insertLegalNoticeInitiationMakerData: lrn notice checking query"+ query);
			
			st = ConnectionDAOforEJB.checkStatus(query);
		}

	
		try {

			if (st) {

				logger.info("In insert Case Type master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into LEGAL_NOTICE_DTL(LOAN_NO,NOTICE_CODE,REASON_CODE,NOTICE_AMOUNT,MODE_OF_NOTICE,TIME_OF_CONCILIATION,VENUE_OF_CONCILIATION,NAME_OF_CONCILIATOR,DESIGNATION_OF_CONCILIATOR,MAKER_REMARKS,REC_STATUS,DATE_OF_CONCILIATION,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //LOAN_NO
				bufInsSql.append(" ?,"); //NOTICE_CODE
				bufInsSql.append(" ?,"); //REASON_CODE
				bufInsSql.append(" ?,"); //NOTICE_AMOUNT
				bufInsSql.append(" ?,"); //MODE_OF_NOTICE
				bufInsSql.append(" ?,"); //TIME_OF_CONCILIATION
				bufInsSql.append(" ?,"); //VENUE_OF_CONCILIATION
				bufInsSql.append(" ?,"); //NAME_OF_CONCILIATOR
				bufInsSql.append(" ?,"); //DESIGNATION_OF_CONCILIATOR
				bufInsSql.append(" ?,"); //MAKER_REMARKS
				bufInsSql.append(" ?,"); //REC_STATUS
				bufInsSql.append(dbo);//DATE_OF_CONCILIATION
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(dbo);//MAKER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			
				if (CommonFunction.checkNull(vo.getLbxLoanId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxLoanId()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getLbxNoticeCode())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxNoticeCode()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getLbxReasonId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxReasonId()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getNoticeAmount())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse(vo.getNoticeAmount()).toString());
				
				if (CommonFunction.checkNull(vo.getModeOfNotice())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getModeOfNotice()
							.toUpperCase().trim());
				
			
				if (CommonFunction.checkNull(vo.getTimeOfConciliation())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTimeOfConciliation()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getVenueOfConciliation())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getVenueOfConciliation()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getNameOfConciliator())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getNameOfConciliator()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getDesignationOfConciliator())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDesignationOfConciliator()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getMakerRemarks())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerRemarks()
							.toUpperCase().trim());
				
				if(updateForwardFlag.equalsIgnoreCase("S"))
				{
					insertPrepStmtObject.addString("P");
				}
				else if(updateForwardFlag.equalsIgnoreCase("F"))
				{
					insertPrepStmtObject.addString("F");
				}
					
				if (CommonFunction.checkNull(vo.getDateOfConciliation())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDateOfConciliation()
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
				
			

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertCaseTypeMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveCountryData......................"
						+ status);
			}
			else
			{
				logger.info("## In insertLegalNoticeInitiationMakerData() : Before Initiating NOTICE138, LRN notice must be sent for same Loan No ");
				vo.setNoticeMessage("Before Initiating NOTICE138, LRN notice must be sent for same Loan No.");
				status = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}
	
	public ArrayList editLegalNoticeInitiationMakerData(Object ob) {

		ArrayList searchlist = new ArrayList();
		LegalNoticeInitiationVo vo = (LegalNoticeInitiationVo)ob;
		ArrayList<LegalNoticeInitiationVo> caseTypeList = new ArrayList<LegalNoticeInitiationVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getNoticeId());

		try {
			

			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT NOTICE_ID,LOAN_NO,(SELECT DISTINCT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LND.LOAN_NO),NOTICE_CODE,(SELECT DISTINCT NOTICE_TYPE_DESC FROM LEG_NOTICE_TYPE_DEF_M WHERE NOTICE_TYPE_CODE = LND.NOTICE_CODE)," );
			bufInsSql.append(" REASON_CODE,(SELECT DISTINCT REASON_DESC FROM COM_REASON_M WHERE REASON_ID = LND.REASON_CODE),NOTICE_AMOUNT,MODE_OF_NOTICE, ");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(DATE_OF_CONCILIATION,'"+ dateFormat +"'), ");
			bufInsSql.append(" TIME_OF_CONCILIATION,VENUE_OF_CONCILIATION, ");
			bufInsSql.append(" NAME_OF_CONCILIATOR,DESIGNATION_OF_CONCILIATOR,MAKER_REMARKS ");
			bufInsSql.append(" FROM LEGAL_NOTICE_DTL LND WHERE NOTICE_ID='"+StringEscapeUtils.escapeSql(vo.getNoticeId())+"'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN editCaseTypeData() search query1 ### "+ bufInsSql.toString());
			logger.info("editCaseTypeData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("editCaseTypeData " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					LegalNoticeInitiationVo caseTypeDataVo = new LegalNoticeInitiationVo();
					
					caseTypeDataVo.setNoticeId(CommonFunction.checkNull(data.get(0)).toString());
					caseTypeDataVo.setLbxLoanId(CommonFunction.checkNull(data.get(1)).toString());
					caseTypeDataVo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
					caseTypeDataVo.setLbxNoticeCode(CommonFunction.checkNull(data.get(3)).toString());	
					caseTypeDataVo.setNoticeDesc(CommonFunction.checkNull(data.get(4)).toString());	
					caseTypeDataVo.setLbxReasonId(CommonFunction.checkNull(data.get(5)).toString());
					caseTypeDataVo.setReasonDesc(CommonFunction.checkNull(data.get(6)).toString());
					if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase(""))
					{
						caseTypeDataVo.setNoticeAmount("0");
					}
					else
					{
						caseTypeDataVo.setNoticeAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(7)))));
					}
					
					
					caseTypeDataVo.setModeOfNotice(CommonFunction.checkNull(data.get(8)).toString());
					caseTypeDataVo.setDateOfConciliation(CommonFunction.checkNull(data.get(9)).toString());
					caseTypeDataVo.setTimeOfConciliation(CommonFunction.checkNull(data.get(10)).toString());
					caseTypeDataVo.setVenueOfConciliation(CommonFunction.checkNull(data.get(11)).toString());
					caseTypeDataVo.setNameOfConciliator(CommonFunction.checkNull(data.get(12)).toString());
					caseTypeDataVo.setDesignationOfConciliator(CommonFunction.checkNull(data.get(13)).toString());
					caseTypeDataVo.setMakerRemarks(CommonFunction.checkNull(data.get(14)).toString());
					caseTypeList.add(caseTypeDataVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return caseTypeList;
}
	
	
	
	
	public boolean updateLegalNoticeInitiationMakerData(Object ob) {
		LegalNoticeInitiationVo vo = (LegalNoticeInitiationVo) ob;
		String noticeId = (String) vo.getNoticeId();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		

		boolean status = false;
		String updateForwardFlag = CommonFunction.checkNull(vo.getUpdateForwardFlag());
		
		logger.info("## IN updateLegalNoticeInitiationMakerData(): updateForwardFlag :==>> "+updateForwardFlag); 
		
	
		
	
		

		try {
			
			
			
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateCountryData..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE LEGAL_NOTICE_DTL SET LOAN_NO=?,NOTICE_CODE=?,REASON_CODE=?,NOTICE_AMOUNT=?,");
			bufInsSql.append(" MODE_OF_NOTICE=?,TIME_OF_CONCILIATION=?,VENUE_OF_CONCILIATION=?,");
			bufInsSql.append(" NAME_OF_CONCILIATOR=?,DESIGNATION_OF_CONCILIATOR=?,MAKER_REMARKS=?,REC_STATUS=?, ");
			bufInsSql.append("DATE_OF_CONCILIATION=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
			bufInsSql.append(" MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where NOTICE_ID=?");

			if (CommonFunction.checkNull(vo.getLbxLoanId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxLoanId()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getLbxNoticeCode())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxNoticeCode()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getLbxReasonId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxReasonId()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getNoticeAmount())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(vo.getNoticeAmount()).toString());
			
			if (CommonFunction.checkNull(vo.getModeOfNotice())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getModeOfNotice()
						.toUpperCase().trim());
			
			
			
			if (CommonFunction.checkNull(vo.getTimeOfConciliation())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getTimeOfConciliation()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getVenueOfConciliation())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getVenueOfConciliation()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getNameOfConciliator())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getNameOfConciliator()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getDesignationOfConciliator())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDesignationOfConciliator()
						.toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getMakerRemarks())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerRemarks()
						.toUpperCase().trim());
			
		if(updateForwardFlag.equalsIgnoreCase("U"))
		{
			insertPrepStmtObject.addString("P");
		}
		else if(updateForwardFlag.equalsIgnoreCase("F"))
		{
			insertPrepStmtObject.addString("F");
		}
			
			
			if (CommonFunction.checkNull(vo.getDateOfConciliation())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDateOfConciliation()
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

			if (CommonFunction.checkNull(vo.getNoticeId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getNoticeId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			
			logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			
		

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public ArrayList searchLegalNoticeInitiationCheckerData(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String loanNo = null;
		String noticeCode = null;
		ArrayList searchlist = new ArrayList();
		LegalNoticeInitiationVo legalNoticeVo = (LegalNoticeInitiationVo) ob;
		ArrayList<LegalNoticeInitiationVo> detailList = new ArrayList<LegalNoticeInitiationVo>();
		try {

			logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
			
			loanNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(legalNoticeVo.getLbxLoanId())).trim());
			noticeCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(legalNoticeVo.getLbxNoticeCode())).trim());
           	StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append(" SELECT NOTICE_ID,(SELECT DISTINCT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID=LND.LOAN_NO) LOAN_NO, ");
			bufInsSql.append(" (SELECT DISTINCT NOTICE_TYPE_DESC FROM LEG_NOTICE_TYPE_DEF_M WHERE NOTICE_TYPE_CODE = LND.NOTICE_CODE) NOTICE, ");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(MAKER_DATE,'"+ dateFormat +"'), ");
			bufInsSql.append(" (SELECT DISTINCT USER_NAME FROM SEC_USER_M WHERE USER_ID = LND.MAKER_ID) NOTICE_INITIATED_BY, ");
			bufInsSql.append(" (SELECT REASON_DESC FROM COM_REASON_M WHERE REASON_ID = LND.REASON_CODE) AS REASON,MAKER_REMARKS,MODE_OF_NOTICE FROM LEGAL_NOTICE_DTL LND ");
			bufInsSql.append(" WHERE REC_STATUS='F' ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEGAL_NOTICE_DTL WHERE REC_STATUS='F' ");
			
			bufInsSql.append(" AND MAKER_ID <> '" + legalNoticeVo.getMakerId() + "' ");
			bufInsSqlTempCount.append(" AND MAKER_ID <> '" + legalNoticeVo.getMakerId() + "' ");

	 if (!loanNo.equals("")) {
				bufInsSql.append(" AND LOAN_NO = '" + loanNo + "' ");
				bufInsSqlTempCount.append(" AND LOAN_NO = '" + loanNo + "' ");
			}
		 if (!noticeCode.equals("")) {
				bufInsSql.append(" AND NOTICE_CODE = '" + noticeCode + "' ");
				bufInsSqlTempCount.append(" AND NOTICE_CODE = '" + noticeCode + "' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((loanNo.trim()==null && noticeCode.trim()==null) || (loanNo.trim().equalsIgnoreCase("") && noticeCode.trim().equalsIgnoreCase("")) || LegalNoticeInitiationVo.getCurrentPageLink()>1)
//			{
			
				logger.info("current PAge Link no .................... "+legalNoticeVo.getCurrentPageLink());
				if(legalNoticeVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (legalNoticeVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				bufInsSql.append(" ORDER BY NOTICE_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							
//			}
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchCaseTypeMasterData.....................................Dao Impl");
			logger.info("searchCaseTypeMasterData " + searchlist.size());
			
			int searchListSize = 0;
			
			if(searchlist!=null)
			searchListSize = searchlist.size();  
			
			
			for (int i = 0; i < searchListSize; i++) {
				// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					LegalNoticeInitiationVo noticeVo = new LegalNoticeInitiationVo();
					noticeVo.setNoticeIdlink("<a href=noticeCheckerDispatchAction.do?method=openEditNoticeChecker&noticeId="
							+ CommonFunction.checkNull(data.get(0)).toString()
							+ ">"
							+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					noticeVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
					noticeVo.setNoticeDesc(CommonFunction.checkNull(data.get(2)).toString());
					noticeVo.setMakerDate(CommonFunction.checkNull(data.get(3)).toString());
					noticeVo.setMakerId(CommonFunction.checkNull(data.get(4)).toString());
					noticeVo.setReasonDesc(CommonFunction.checkNull(data.get(5)).toString());
					noticeVo.setMakerRemarks(CommonFunction.checkNull(data.get(6)).toString());
					noticeVo.setModeOfNotice(CommonFunction.checkNull(data.get(7)).toString());
					
					noticeVo.setTotalRecordSize(count);
					noticeVo.setListSize(Integer.toString(searchListSize));
					detailList.add(noticeVo);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		/*if(searchlist.size()==0)
		{
			LegalNoticeInitiationVo deptMVO = new LegalNoticeInitiationVo();
			deptMVO.setTotalRecordSize(count);
			detailList.add(deptMVO);
			request.setAttribute("flag","yes");
			logger.info("Detail List when searchList is null: "+detailList.size());
		}*/

		return detailList;
	}
	
	
	
	public boolean insertLegalNoticeInitiationChecker(Object ob) {
		
		logger.info("## in insertLegalNoticeInitiationChecker()");
		
		LegalNoticeInitiationVo vo = (LegalNoticeInitiationVo) ob;
		String noticeId = (String) vo.getNoticeId();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		

		boolean status = false;
		String updateForwardFlag = CommonFunction.checkNull(vo.getUpdateForwardFlag());
		logger.info("## IN updateLegalNoticeInitiationMakerData(): updateForwardFlag :==>> "+updateForwardFlag); 
		
		logger.info("## IN insertLegalNoticeInitiationChecker(): noticeId :==>> "+noticeId); 
		

		try {
			
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateCountryData..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE LEGAL_NOTICE_DTL SET AUTHER_REMARKS=?,REC_STATUS=?,");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where NOTICE_ID=?");

			if (CommonFunction.checkNull(vo.getComments()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getComments().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDecision().toUpperCase().trim());
			
             if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
		

			if (CommonFunction.checkNull(noticeId).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(noticeId);

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			
			logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public ArrayList editLegalNoticeInitiationCheckerData(Object ob) {
		
		
		ArrayList<LegalNoticeInitiationVo> caseTypeList = new ArrayList<LegalNoticeInitiationVo>();
		
		LegalNoticeInitiationVo vo = (LegalNoticeInitiationVo)ob;
		
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getNoticeId());

		try {
			
			ArrayList searchlist = new ArrayList();
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT NOTICE_ID,LOAN_NO,(SELECT DISTINCT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LND.LOAN_NO),NOTICE_CODE,(SELECT DISTINCT NOTICE_TYPE_DESC FROM LEG_NOTICE_TYPE_DEF_M WHERE NOTICE_TYPE_CODE = LND.NOTICE_CODE)," );
			bufInsSql.append(" REASON_CODE,(SELECT DISTINCT REASON_DESC FROM COM_REASON_M WHERE REASON_ID = LND.REASON_CODE),NOTICE_AMOUNT,MODE_OF_NOTICE, ");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(DATE_OF_CONCILIATION,'"+ dateFormat +"'), ");
			bufInsSql.append(" TIME_OF_CONCILIATION,VENUE_OF_CONCILIATION, ");
			bufInsSql.append(" NAME_OF_CONCILIATOR,DESIGNATION_OF_CONCILIATOR,MAKER_REMARKS ");
			bufInsSql.append(" FROM LEGAL_NOTICE_DTL LND WHERE NOTICE_ID='"+StringEscapeUtils.escapeSql(vo.getNoticeId())+"'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN editLegalNoticeInitiationCheckerData() search query1 ### "+ bufInsSql.toString());
			logger.info("editLegalNoticeInitiationCheckerData " + searchlist.size());
			
		
			
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("editLegalNoticeInitiationCheckerData " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					LegalNoticeInitiationVo caseTypeDataVo = new LegalNoticeInitiationVo();
					
					caseTypeDataVo.setNoticeId(CommonFunction.checkNull(data.get(0)).toString());
					caseTypeDataVo.setLbxLoanId(CommonFunction.checkNull(data.get(1)).toString());
					caseTypeDataVo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
					caseTypeDataVo.setLbxNoticeCode(CommonFunction.checkNull(data.get(3)).toString());	
					caseTypeDataVo.setNoticeDesc(CommonFunction.checkNull(data.get(4)).toString());	
					caseTypeDataVo.setLbxReasonId(CommonFunction.checkNull(data.get(5)).toString());
					caseTypeDataVo.setReasonDesc(CommonFunction.checkNull(data.get(6)).toString());
					caseTypeDataVo.setNoticeAmount(CommonFunction.checkNull(data.get(7)).toString());
					caseTypeDataVo.setModeOfNotice(CommonFunction.checkNull(data.get(8)).toString());
					caseTypeDataVo.setDateOfConciliation(CommonFunction.checkNull(data.get(9)).toString());
					caseTypeDataVo.setTimeOfConciliation(CommonFunction.checkNull(data.get(10)).toString());
					caseTypeDataVo.setVenueOfConciliation(CommonFunction.checkNull(data.get(11)).toString());
					caseTypeDataVo.setNameOfConciliator(CommonFunction.checkNull(data.get(12)).toString());
					caseTypeDataVo.setDesignationOfConciliator(CommonFunction.checkNull(data.get(13)).toString());
					caseTypeList.add(caseTypeDataVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return caseTypeList;
}
	
	
	
	
	 public ArrayList searchLegalDeclineDispatchNoticeData(Object ob) {
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			String loanNo = null;
			String noticeCode = null;
			ArrayList searchlist = new ArrayList();
			LegalNoticeInitiationVo legalNoticeVo = (LegalNoticeInitiationVo) ob;
			ArrayList<LegalNoticeInitiationVo> detailList = new ArrayList<LegalNoticeInitiationVo>();
			try {

				logger.info("In searchLegalDeclineDispatchNoticeData()..............inside ejb server file.......................Dao Impl");
				
				loanNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(legalNoticeVo.getLbxLoanId())).trim());
				noticeCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(legalNoticeVo.getLbxNoticeCode())).trim());
	           	StringBuffer bufInsSql = new StringBuffer();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
				bufInsSql.append(" SELECT NOTICE_ID,(SELECT DISTINCT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID=LND.LOAN_NO) LOAN_NO, ");
				bufInsSql.append(" (SELECT DISTINCT NOTICE_TYPE_DESC FROM LEG_NOTICE_TYPE_DEF_M WHERE NOTICE_TYPE_CODE = LND.NOTICE_CODE) NOTICE, ");
				bufInsSql.append(dbo);
				bufInsSql.append("DATE_FORMAT(MAKER_DATE,'"+ dateFormat +"'), ");
				bufInsSql.append(" (SELECT DISTINCT USER_NAME FROM SEC_USER_M WHERE USER_ID = LND.MAKER_ID) NOTICE_INITIATED_BY, ");
				bufInsSql.append(dbo);
				bufInsSql.append("DATE_FORMAT(AUTHOR_DATE,'"+ dateFormat +"'), ");
				bufInsSql.append(" (SELECT DISTINCT USER_NAME FROM SEC_USER_M WHERE USER_ID = LND.AUTHOR_ID) NOTICE_APPROVED_BY, ");
				bufInsSql.append(" (SELECT REASON_DESC FROM COM_REASON_M WHERE REASON_ID = LND.REASON_CODE) AS REASON,MAKER_REMARKS,AUTHER_REMARKS,MODE_OF_NOTICE FROM LEGAL_NOTICE_DTL LND ");
				bufInsSql.append(" WHERE REC_STATUS='A' ");
				bufInsSqlTempCount.append("SELECT COUNT(1) FROM LEGAL_NOTICE_DTL WHERE REC_STATUS='A' ");
				
				//bufInsSql.append(" AND MAKER_ID <> '" + legalNoticeVo.getMakerId() + "' ");
				//bufInsSqlTempCount.append(" AND MAKER_ID <> '" + legalNoticeVo.getMakerId() + "' ");
				
				//bufInsSql.append(" AND AUTHOR_ID <> '" + legalNoticeVo.getMakerId() + "' ");
				//bufInsSqlTempCount.append(" AND AUTHOR_ID <> '" + legalNoticeVo.getMakerId() + "' ");

			 if (!loanNo.equals("")) {
					bufInsSql.append(" AND LOAN_NO = '" + loanNo + "' ");
					bufInsSqlTempCount.append(" AND LOAN_NO = '" + loanNo + "' ");
				}
				 if (!noticeCode.equals("")) {
					bufInsSql.append(" AND NOTICE_CODE = '" + noticeCode + "' ");
					bufInsSqlTempCount.append(" AND NOTICE_CODE = '" + noticeCode + "' ");
				}
				logger.info("search Query...." + bufInsSql);
				logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	            
				count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
				

				
					logger.info("current PAge Link no .................... "+legalNoticeVo.getCurrentPageLink());
					if(legalNoticeVo.getCurrentPageLink()>1)
					{
						startRecordIndex = (legalNoticeVo.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						logger.info("startRecordIndex .................... "+startRecordIndex);
						logger.info("endRecordIndex .................... "+endRecordIndex);
					}
					//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
					bufInsSql.append(" ORDER BY NOTICE_ID OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
								
//				}
				logger.info("query : "+bufInsSql);

				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

				logger.info("IN searchLegalDeclineDispatchNoticeData() search query1 ### "+ bufInsSql.toString());
				logger.info("In searchLegalDeclineDispatchNoticeData.....................................Dao Impl");
				logger.info("searchLegalDeclineDispatchNoticeData " + searchlist.size());
				
				int searchListSize = 0;
				
				if(searchlist!=null)
				searchListSize = searchlist.size();  
				
				
				for (int i = 0; i < searchListSize; i++) {
					

					ArrayList data = (ArrayList) searchlist.get(i);

					if (data.size() > 0) {
						LegalNoticeInitiationVo noticeVo = new LegalNoticeInitiationVo();
						
						noticeVo.setNoticeIdlink("<a href=legalDeclineDispatchNoticeAction.do?method=openEditLegalDeclineDispatchNotice&noticeId=" 
								+CommonFunction.checkNull(data.get(0)).toString()+">"
								+CommonFunction.checkNull(data.get(0)).toString()+"</a> ");
						noticeVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
						noticeVo.setNoticeDesc(CommonFunction.checkNull(data.get(2)).toString());
						noticeVo.setMakerDate(CommonFunction.checkNull(data.get(3)).toString());
						noticeVo.setMakerId(CommonFunction.checkNull(data.get(4)).toString());
						noticeVo.setAutherDate(CommonFunction.checkNull(data.get(5)).toString());
						noticeVo.setAutherId(CommonFunction.checkNull(data.get(6)).toString());
						noticeVo.setReasonDesc(CommonFunction.checkNull(data.get(7)).toString());
						noticeVo.setMakerRemarks(CommonFunction.checkNull(data.get(8)).toString());
						noticeVo.setAuthorRemarks(CommonFunction.checkNull(data.get(9)).toString());
						noticeVo.setModeOfNotice(CommonFunction.checkNull(data.get(10)).toString());
						noticeVo.setTotalRecordSize(count);
						noticeVo.setListSize(Integer.toString(searchListSize));
						detailList.add(noticeVo);
						
					}

				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return detailList;
	 		}
	        
	        
	        public boolean insertLegalDeclineDispatchNotice(Object ob) {
	    		
	    		logger.info("## in insertLegalDeclineDispatchNotice()");
	    		
	    		LegalNoticeInitiationVo vo = (LegalNoticeInitiationVo) ob;
	    		String noticeLegalDeclineID = (String) vo.getNoticeLegalDeclineID();
	    		logger.info("getRecStatus():-" + vo.getRecStatus());
	    		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	    		ArrayList updatelist = new ArrayList();
	    		

	    		boolean status = false;
	    		String updateForwardFlag = CommonFunction.checkNull(vo.getUpdateForwardFlag());
	    		logger.info("## IN insertLegalDeclineDispatchNotice(): updateForwardFlag :==>> "+updateForwardFlag); 
	    		
	    		logger.info("## IN insertLegalDeclineDispatchNotice(): noticeId :==>> "+noticeLegalDeclineID); 
	    		

	    		try {
	    			
	    			StringBuffer bufInsSql = new StringBuffer();
	    			logger.info("In insertLegalDeclineDispatchNotice..........inside ejb server file............Dao Impl");
	    			bufInsSql.append(" UPDATE LEGAL_NOTICE_DTL SET DECLINE_REMARKS=?,DECLINE_REASON=?,REC_STATUS=?,");
	    			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
	    			bufInsSql.append(dbo);
	    			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
	    			bufInsSql.append(" where NOTICE_ID=?");

	    			if (CommonFunction.checkNull(vo.getComments()).equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString(vo.getComments().toUpperCase().trim());
	    			
	    			if (CommonFunction.checkNull(vo.getLbxReasonId()).equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString(vo.getLbxReasonId().toUpperCase().trim());
	    			
	    			if (CommonFunction.checkNull(vo.getCheckerDecision()).equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString(vo.getCheckerDecision().toUpperCase().trim());
	    			
	                 if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString(vo.getMakerId());
	    			
	    			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString(vo.getMakerDate());
	    		

	    			if (CommonFunction.checkNull(noticeLegalDeclineID).equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString(noticeLegalDeclineID);

	    			insertPrepStmtObject.setSql(bufInsSql.toString());

	    			updatelist.add(insertPrepStmtObject);
	    			logger.info("In getListOfValues" + bufInsSql.toString());
	    			
	    			logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
	    			
	    			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

	    		
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		return status;
	    	}
	        
	        
	        

		public ArrayList editLegalDeclineDispatchNotice(Object ob) {

			ArrayList searchlist = new ArrayList();
			LegalNoticeInitiationVo vo = (LegalNoticeInitiationVo)ob;
			ArrayList<LegalNoticeInitiationVo> caseTypeList = new ArrayList<LegalNoticeInitiationVo>();
			logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getNoticeId());

			try {
				

				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append(" SELECT NOTICE_ID,LOAN_NO,(SELECT DISTINCT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LND.LOAN_NO),NOTICE_CODE,(SELECT DISTINCT NOTICE_TYPE_DESC FROM LEG_NOTICE_TYPE_DEF_M WHERE NOTICE_TYPE_CODE = LND.NOTICE_CODE)," );
				bufInsSql.append(" REASON_CODE,(SELECT DISTINCT REASON_DESC FROM COM_REASON_M WHERE REASON_ID = LND.REASON_CODE),NOTICE_AMOUNT,MODE_OF_NOTICE, ");
				bufInsSql.append(dbo);
				bufInsSql.append("DATE_FORMAT(DATE_OF_CONCILIATION,'"+ dateFormat +"'), ");
				bufInsSql.append(" TIME_OF_CONCILIATION,VENUE_OF_CONCILIATION, ");
				bufInsSql.append(" NAME_OF_CONCILIATOR,DESIGNATION_OF_CONCILIATOR,MAKER_REMARKS ");
				bufInsSql.append(" FROM LEGAL_NOTICE_DTL LND WHERE NOTICE_ID='"+StringEscapeUtils.escapeSql(vo.getNoticeId())+"' ");
				logger.info("search Query...." + bufInsSql);

				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
				logger.info("IN editLegalDeclineDispatchNotice() search query1 ### "+ bufInsSql.toString());
				logger.info("editLegalDeclineDispatchNotice " + searchlist.size());
				for (int i = 0; i < searchlist.size(); i++) {
					logger.info("editCaseTypeData " + searchlist.get(i).toString());
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						LegalNoticeInitiationVo caseTypeDataVo = new LegalNoticeInitiationVo();
						
						caseTypeDataVo.setNoticeId(CommonFunction.checkNull(data.get(0)).toString());
						caseTypeDataVo.setLbxLoanId(CommonFunction.checkNull(data.get(1)).toString());
						caseTypeDataVo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
						caseTypeDataVo.setLbxNoticeCode(CommonFunction.checkNull(data.get(3)).toString());	
						caseTypeDataVo.setNoticeDesc(CommonFunction.checkNull(data.get(4)).toString());	
						caseTypeDataVo.setLbxReasonId(CommonFunction.checkNull(data.get(5)).toString());
						caseTypeDataVo.setReasonDesc(CommonFunction.checkNull(data.get(6)).toString());
						caseTypeDataVo.setNoticeAmount(CommonFunction.checkNull(data.get(7)).toString());
						caseTypeDataVo.setModeOfNotice(CommonFunction.checkNull(data.get(8)).toString());
						caseTypeDataVo.setDateOfConciliation(CommonFunction.checkNull(data.get(9)).toString());
						caseTypeDataVo.setTimeOfConciliation(CommonFunction.checkNull(data.get(10)).toString());
						caseTypeDataVo.setVenueOfConciliation(CommonFunction.checkNull(data.get(11)).toString());
						caseTypeDataVo.setNameOfConciliator(CommonFunction.checkNull(data.get(12)).toString());
						caseTypeDataVo.setDesignationOfConciliator(CommonFunction.checkNull(data.get(13)).toString());
						caseTypeDataVo.setMakerRemarks(CommonFunction.checkNull(data.get(14)).toString());
						caseTypeList.add(caseTypeDataVo);

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return caseTypeList;
		}


	

		public ArrayList searchLegalCaseInitiationMaker(Object ob) {
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			String loanNo = "";

			ArrayList searchlist = new ArrayList();
			LegalCaseInitiationMakerVo vo = (LegalCaseInitiationMakerVo) ob;
			ArrayList<LegalCaseInitiationMakerVo> detailList = new ArrayList<LegalCaseInitiationMakerVo>();
			try {

				logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
				
				loanNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanId())).trim());
			
		       	StringBuffer bufInsSql = new StringBuffer();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
				bufInsSql.append(" SELECT  LOAN_ID,LOAN_NO,(SELECT DISTINCT CUSTOMER_NAME FROM GCD_CUSTOMER_M WHERE CUSTOMER_ID = CLD.LOAN_CUSTOMER_ID) CUST_NAME,");
				bufInsSql.append(" (SELECT DISTINCT BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID = CLD.LOAN_BRANCH) BRANCH,(SELECT DISTINCT PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = CLD.LOAN_PRODUCT) PRODUCT, ");
				bufInsSql.append(" (SELECT DISTINCT SCHEME_DESC FROM CR_SCHEME_M WHERE SCHEME_ID = CLD.LOAN_SCHEME) SCHEME ");
				bufInsSql.append(" FROM CR_LOAN_DTL CLD ");
				bufInsSql.append(" WHERE REC_STATUS = 'A' AND EXISTS (SELECT 1 FROM LEGAL_NOTICE_DTL LND WHERE REC_STATUS='D' AND CLD.LOAN_ID = LND.LOAN_NO) ");
				bufInsSql.append(" AND NOT EXISTS (SELECT 1 FROM LEGAL_CASE_DTL LCD WHERE LCD.LOAN_ID = CLD.LOAN_ID AND REC_STATUS IN ('I','A')) ");
				bufInsSqlTempCount.append(" SELECT COUNT(1) FROM CR_LOAN_DTL CLD WHERE REC_STATUS = 'A' AND EXISTS (SELECT 1 FROM LEGAL_NOTICE_DTL LND WHERE REC_STATUS='D' AND CLD.LOAN_ID = LND.LOAN_NO) AND NOT EXISTS (SELECT 1 FROM LEGAL_CASE_DTL LCD WHERE LCD.LOAN_ID = CLD.LOAN_ID AND REC_STATUS IN ('I','A')) ");

				if (!loanNo.equals("")) {
					bufInsSql.append(" AND CLD.LOAN_ID = '" + loanNo + "' ");
					bufInsSqlTempCount.append(" AND CLD.LOAN_ID = '" + loanNo + "' ");
				}
				logger.info("search Query....loanNo:::" + loanNo);
				logger.info("search Query...." + bufInsSql);
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
					
					bufInsSql.append(" ORDER BY LOAN_ID OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
								

				logger.info("query : "+bufInsSql);

				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

				logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
				logger.info("In searchCaseTypeMasterData.....................................Dao Impl");
				logger.info("searchCaseTypeMasterData " + searchlist.size());

				for (int i = 0; i < searchlist.size(); i++) {
					// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

					ArrayList data = (ArrayList) searchlist.get(i);

					if (data.size() > 0) {
						LegalCaseInitiationMakerVo caseInitVo = new LegalCaseInitiationMakerVo();
						caseInitVo.setLoanNo("<a href=legalCaseInitiationMakerDispatch.do?method=openLegalCaseInitiationMaker&loanId="
										+ CommonFunction.checkNull(data.get(0)).toString()
										+ ">"
										+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
						caseInitVo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
						caseInitVo.setBranch(CommonFunction.checkNull(data.get(3)).toString());
						caseInitVo.setProduct(CommonFunction.checkNull(data.get(4)).toString());
						caseInitVo.setScheme(CommonFunction.checkNull(data.get(5)).toString());
						caseInitVo.setTotalRecordSize(count);
						detailList.add(caseInitVo);
					}

				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			/*if(searchlist.size()==0)
			{
				LegalNoticeInitiationVo deptMVO = new LegalNoticeInitiationVo();
				deptMVO.setTotalRecordSize(count);
				detailList.add(deptMVO);
				request.setAttribute("flag","yes");
				logger.info("Detail List when searchList is null: "+detailList.size());
			}*/

			return detailList;
		}

		public ArrayList openEditLegalCaseInitiationMaker(Object ob) {

			ArrayList searchlist = new ArrayList();
			LegalCaseInitiationMakerVo vo = (LegalCaseInitiationMakerVo)ob;
			ArrayList<LegalCaseInitiationMakerVo> caseInitMakerList = new ArrayList<LegalCaseInitiationMakerVo>();
			logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanId());

			try {
				

				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append(" SELECT  LOAN_ID,LOAN_NO,(SELECT DISTINCT CUSTOMER_NAME FROM GCD_CUSTOMER_M WHERE CUSTOMER_ID = CLD.LOAN_CUSTOMER_ID) CUST_NAME, " );
				bufInsSql.append(" (SELECT DISTINCT BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID = CLD.LOAN_BRANCH) BRANCH,(SELECT DISTINCT PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = CLD.LOAN_PRODUCT) PRODUCT, ");
				bufInsSql.append(" (SELECT DISTINCT SCHEME_DESC FROM CR_SCHEME_M WHERE SCHEME_ID = CLD.LOAN_SCHEME) SCHEME,LOAN_DPD,(ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+ISNULL(CLD.LOAN_BALANCE_PRINCIPAL,0)) TOS, ");
				bufInsSql.append(" (select DISTINCT sum(ISNULL(cr_txnadvice_dtl.advice_amount,0))- sum(ISNULL(txn_adjusted_amount,0))- sum(ISNULL(amount_in_process,0)) ");
				bufInsSql.append(" from cr_txnadvice_dtl where advice_type = 'R' and CHARGE_CODE_ID <> 7 and CHARGE_CODE_ID <> 27 AND LOAN_ID = CLD.LOAN_ID) CHARGES,");

				bufInsSql.append(" (ISNULL(CLD.LOAN_BALANCE_PRINCIPAL,0)+ISNULL(CLD.LOAN_OVERDUE_PRINCIPAL,0)) POS ");
				bufInsSql.append(" FROM CR_LOAN_DTL CLD WHERE REC_STATUS = 'A' AND LOAN_ID='"+StringEscapeUtils.escapeSql(vo.getLoanId())+"' ");
			
				logger.info("search Query...." + bufInsSql);

				searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
				logger.info("IN editCaseTypeData() search query1 ### "+ bufInsSql.toString());
				logger.info("editCaseTypeData " + searchlist.size());
				for (int i = 0; i < searchlist.size(); i++) {
					logger.info("editCaseTypeData " + searchlist.get(i).toString());
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						LegalCaseInitiationMakerVo caseInitMakerVo = new LegalCaseInitiationMakerVo();
						
						caseInitMakerVo.setLoanId(CommonFunction.checkNull(data.get(0)).toString());
						caseInitMakerVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
						caseInitMakerVo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
						caseInitMakerVo.setBranch(CommonFunction.checkNull(data.get(3)).toString());	
						caseInitMakerVo.setProduct(CommonFunction.checkNull(data.get(4)).toString());	
						caseInitMakerVo.setScheme(CommonFunction.checkNull(data.get(5)).toString());
						caseInitMakerVo.setDpd(CommonFunction.checkNull(data.get(6)).toString());
						//caseInitMakerVo.setTotalOutstanding(CommonFunction.checkNull(data.get(7)).toString());
						if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase(""))
						{
							caseInitMakerVo.setTotalOutstanding("");
						}
						else
						{
							caseInitMakerVo.setTotalOutstanding(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(7)))));
						}
						//caseInitMakerVo.setOtherCharges(CommonFunction.checkNull(data.get(8)).toString());
						if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase(""))
						{
							caseInitMakerVo.setOtherCharges("");
						}
						else
						{
							caseInitMakerVo.setOtherCharges(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(8)))));
						}
						//caseInitMakerVo.setPrincipalOutstanding(CommonFunction.checkNull(data.get(9)).toString());
						if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase(""))
						{
							caseInitMakerVo.setPrincipalOutstanding("");
						}
						else
						{
							caseInitMakerVo.setPrincipalOutstanding(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(9)))));
						}
						caseInitMakerList.add(caseInitMakerVo);

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return caseInitMakerList;
		}

		public boolean insertLegalCaseInitiationMaker(Object ob) {
			LegalCaseInitiationMakerVo vo = (LegalCaseInitiationMakerVo) ob;
			boolean status = false;
			
			
			logger.info("In insertCaseTypeMaster.........inside ejb server file...........Dao Impl"
							+ vo.getRecStatus());
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

			String query = "select LOAN_ID from LEGAL_CASE_DTL where REC_STATUS IN ('I','A') AND LOAN_ID='"+ StringEscapeUtils.escapeSql(vo.getLoanId().trim()) + "' AND CASE_TYPE = '"+StringEscapeUtils.escapeSql(vo.getLbxCaseTypeCode().trim()) +"' ";
			logger.info("In insertCaseTypeMaster.......inside ejb server file..........Dao Impl"
							+ query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			
			
			
		
			
			
		
			
			try {

				if (!st) {
					
					

					logger.info("In insert Case Type master");
					StringBuffer bufInsSql = new StringBuffer();
					bufInsSql.append(" INSERT INTO LEGAL_CASE_DTL(LOAN_ID,CUSTOMER_ID, BRANCH_ID,PRODUCT_ID,SCHEME_ID,DPD, " );
					bufInsSql.append(" TOTAL_OUTSTANDING, " ); 
					bufInsSql.append(" CHARGES, " );
					bufInsSql.append(" PRINCIPAL_OUTSTANDING, " );
					bufInsSql.append(" CASE_TYPE,INITIATION_MAKER_IMEMO, ");
					bufInsSql.append(" INITIATION_MAKER_REASON,INITIATION_MAKER_REMARKS, ");
					bufInsSql.append(" CASE_INIT_MAKER_ID,CASE_INIT_MAKER_DATE, " );
					bufInsSql.append(" REC_STATUS) " );
					bufInsSql.append(" SELECT LOAN_ID,LOAN_CUSTOMER_ID,LOAN_BRANCH,LOAN_PRODUCT,LOAN_SCHEME,LOAN_DPD, ");
					bufInsSql.append(" (ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+ISNULL(CLD.LOAN_BALANCE_PRINCIPAL,0)) TOS, " );
					//bufInsSql.append(" (SELECT SUM(OTHER_CHARGES) FROM CR_REPAYSCH_DTL WHERE LOAN_ID = CLD.LOAN_ID) CHARGES, ");
					bufInsSql.append(" (select DISTINCT sum(ISNULL(cr_txnadvice_dtl.advice_amount,0))- sum(ISNULL(txn_adjusted_amount,0))- sum(ISNULL(amount_in_process,0))  ");
					bufInsSql.append(" from cr_txnadvice_dtl where advice_type = 'R' and CHARGE_CODE_ID <> 7 and CHARGE_CODE_ID <> 27 AND LOAN_ID = CLD.LOAN_ID) CHARGES,");

					
					bufInsSql.append(" (ISNULL(CLD.LOAN_BALANCE_PRINCIPAL,0)+ISNULL(CLD.LOAN_OVERDUE_PRINCIPAL,0)) POS, ");
					bufInsSql.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxCaseTypeCode()))+"','"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitMakerImemo()))+"', ");
					bufInsSql.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxReasonId()))+"','"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitMakerRemarks()))+"', ");
					bufInsSql.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()))+"', ");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()))+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					bufInsSql.append("'I'");
					bufInsSql.append(" FROM CR_LOAN_DTL CLD WHERE LOAN_ID = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanId()))+"' ");
					

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertCaseTypeMaster() insert query1 ### "
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
		
		public boolean checkingLRNStatus(Object ob) {
			
			LegalCaseInitiationMakerVo vo = (LegalCaseInitiationMakerVo) ob;
				
			boolean forLRN = false;
			String query1 = "SELECT LOAN_NO FROM LEGAL_NOTICE_DTL WHERE NOTICE_CODE = 'LRN' AND LOAN_NO = '"+StringEscapeUtils.escapeSql(vo.getLoanId().trim()) +"' AND REC_STATUS = 'D'";
					 
			logger.info("In insertCaseTypeMaster.......inside ejb server file..........Dao Impl"+ query1);
			forLRN = ConnectionDAOforEJB.checkStatus(query1);
			
			
			return forLRN;

		}
		
		public boolean checkingNotice138Status(Object ob) {
			
			LegalCaseInitiationMakerVo vo = (LegalCaseInitiationMakerVo) ob;
			
			boolean forNotiec138 = false;
			
			String query1 = "SELECT LOAN_NO FROM LEGAL_NOTICE_DTL WHERE NOTICE_CODE = 'NOTICE138' AND LOAN_NO = '"+StringEscapeUtils.escapeSql(vo.getLoanId().trim()) +"' AND REC_STATUS = 'D'";
					 
			logger.info("In insertCaseTypeMaster.......inside ejb server file..........Dao Impl"+ query1);
			forNotiec138 = ConnectionDAOforEJB.checkStatus(query1);
			
			
			return forNotiec138;

		}

public ArrayList searchLegalCaseFileMaker(Object ob) {
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	String loanId = null;

	ArrayList searchlist = new ArrayList();
	LegalCaseFileVo vo = (LegalCaseFileVo) ob;
	ArrayList<LegalCaseFileVo> detailList = new ArrayList<LegalCaseFileVo>();
	try {

		logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
		
		loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanId())).trim());
	
       	StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		bufInsSql.append(" SELECT LEGAL_ID,(SELECT TOP 1 LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LCD.LOAN_ID) LOAN_NO, ");
		bufInsSql.append(" (SELECT TOP 1 CASE_TYPE_DESC FROM LEG_CASE_TYPE_DEF_M WHERE CASE_TYPE_CODE = LCD.CASE_TYPE ) CASE_TYPE, ");
		bufInsSql.append(" (SELECT TOP 1 PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = LCD.PRODUCT_ID) PRODUCT, ");
		bufInsSql.append(" (SELECT TOP 1 BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID  = LCD.BRANCH_ID) BRANCH  ");
		bufInsSql.append(" FROM LEGAL_CASE_DTL LCD WHERE REC_STATUS IN ('A','CFM') AND ADVOCATE_ID = '"+StringEscapeUtils.escapeSql(vo.getMakerId())+"' ");
		
		bufInsSqlTempCount.append(" SELECT COUNT(1) FROM LEGAL_CASE_DTL LCD WHERE REC_STATUS IN ('A','CFM') AND ADVOCATE_ID = '"+StringEscapeUtils.escapeSql(vo.getMakerId())+"' ");

		if (!loanId.equals("")) {
			bufInsSql.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
		}
		
		logger.info("search Query...." + bufInsSql);
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
			
			bufInsSql.append(" ORDER BY LEGAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
						

		logger.info("query : "+bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
		logger.info("In searchCaseTypeMasterData.....................................Dao Impl");
		logger.info("searchCaseTypeMasterData " + searchlist.size());

		for (int i = 0; i < searchlist.size(); i++) {
			// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

			ArrayList data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				LegalCaseFileVo caseFileVo = new LegalCaseFileVo();
				caseFileVo.setLegalId("<a href=legalCaseFileMakerDispatch.do?method=openLegalCaseFileMaker&legalId="
								+ CommonFunction.checkNull(data.get(0)).toString()
								+ ">"
								+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
				caseFileVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
				caseFileVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(2)).toString());
				caseFileVo.setProduct(CommonFunction.checkNull(data.get(3)).toString());
				caseFileVo.setBranch(CommonFunction.checkNull(data.get(4)).toString());
				caseFileVo.setTotalRecordSize(count);
				detailList.add(caseFileVo);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	/*if(searchlist.size()==0)
	{
		LegalNoticeInitiationVo deptMVO = new LegalNoticeInitiationVo();
		deptMVO.setTotalRecordSize(count);
		detailList.add(deptMVO);
		request.setAttribute("flag","yes");
		logger.info("Detail List when searchList is null: "+detailList.size());
	}*/

	return detailList;
}

public ArrayList openEditLegalCaseFileMaker(Object ob) {

	ArrayList searchlist = new ArrayList();
	LegalCaseFileVo vo = (LegalCaseFileVo)ob;
	ArrayList<LegalCaseFileVo> caseFileMakerList = new ArrayList<LegalCaseFileVo>();
	logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanId());

	try {

		StringBuffer bufInsSql = new StringBuffer();
		bufInsSql.append(" SELECT LEGAL_ID,(SELECT TOP 1 LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LCD.LOAN_ID) LOAN_NO, " );
		bufInsSql.append(" (SELECT TOP 1 CUSTOMER_NAME FROM GCD_CUSTOMER_M WHERE CUSTOMER_ID = LCD.CUSTOMER_ID) CUSTOMER,   ");
		bufInsSql.append(" (SELECT TOP 1 PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = LCD.PRODUCT_ID) PRODUCT, ");
		bufInsSql.append(" (SELECT TOP 1 SCHEME_DESC FROM CR_SCHEME_M WHERE SCHEME_ID = LCD.SCHEME_ID) SCHEME, ");
		bufInsSql.append(" (SELECT TOP 1 CASE_TYPE_DESC FROM LEG_CASE_TYPE_DEF_M WHERE CASE_TYPE_CODE = LCD.CASE_TYPE) CASE_TYPE, ");
		bufInsSql.append(" (SELECT TOP 1 USER_NAME FROM SEC_USER_M WHERE USER_ID = LCD.ADVOCATE_ID) ADVOCATE, ");
		bufInsSql.append(" CASE_NO,COURT_FEE,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(CASE_FILE_DATE,'"+ dateFormat +"'), ");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(DATE_OF_HEARING,'"+ dateFormat +"'), ");
		bufInsSql.append(" (SELECT TOP 1 STAGE_DESC FROM LEG_STAGE_DEF_M WHERE STAGE_CODE = LCD.STAGE) STAGE_DESC, ");
		bufInsSql.append(" STAGE AS STAGE_CODE,SECTION, ");
		bufInsSql.append(" (SELECT TOP 1 COURT_TYPE_DESC FROM LEG_COURT_TYPE_DEF_M WHERE COURT_TYPE_CODE = LCD.COURT_TYPE) AS COURT_TYPE_DESC, ");
		bufInsSql.append(" COURT_TYPE,(SELECT TOP 1 COURT_NAME_DESC FROM LEG_COURT_NAME_DEF_M WHERE COURT_NAME_CODE = LCD.COURT_NAME) COURT_NAME_DESC, ");
		bufInsSql.append(" COURT_NAME,JUDGE_NAME, ");
		bufInsSql.append(" (SELECT TOP 1 USER_NAME FROM SEC_USER_M WHERE USER_ID = LCD.POA) POA_DESC, ");
		bufInsSql.append(" POA, ");
		bufInsSql.append(" RECOVERY_AMOUNT,CASE_FILE_MAKER_REMARKS ");
		bufInsSql.append(" FROM LEGAL_CASE_DTL LCD WHERE LEGAL_ID='"+StringEscapeUtils.escapeSql(vo.getLegalId())+"' ");
	
		logger.info("search Query...." + bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		logger.info("IN editCaseTypeData() search query1 ### "+ bufInsSql.toString());
		logger.info("editCaseTypeData " + searchlist.size());
		for (int i = 0; i < searchlist.size(); i++) {
			logger.info("editCaseTypeData " + searchlist.get(i).toString());
			ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				LegalCaseFileVo caseFileMakerVo = new LegalCaseFileVo();
				
				caseFileMakerVo.setLegalId(CommonFunction.checkNull(data.get(0)).toString());
				caseFileMakerVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
				caseFileMakerVo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
				caseFileMakerVo.setProduct(CommonFunction.checkNull(data.get(3)).toString());	
				caseFileMakerVo.setScheme(CommonFunction.checkNull(data.get(4)).toString());
				caseFileMakerVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(5)).toString());
				caseFileMakerVo.setAdvocate(CommonFunction.checkNull(data.get(6)).toString());
				caseFileMakerVo.setCaseNo(CommonFunction.checkNull(data.get(7)).toString());
				if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase(""))
				{
					caseFileMakerVo.setCourtFee("0");
				}
				else
				{
					
					caseFileMakerVo.setCourtFee(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(8)))));
				}
				//caseFileMakerVo.setCourtFee(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(8)))));
				caseFileMakerVo.setCaseFileDate(CommonFunction.checkNull(data.get(9)).toString());
				caseFileMakerVo.setDateOfHearing(CommonFunction.checkNull(data.get(10)).toString());	
				caseFileMakerVo.setStageDesc(CommonFunction.checkNull(data.get(11)).toString());
				caseFileMakerVo.setLbxStage(CommonFunction.checkNull(data.get(12)).toString());
				caseFileMakerVo.setSection(CommonFunction.checkNull(data.get(13)).toString());
				caseFileMakerVo.setCourtTypeDesc(CommonFunction.checkNull(data.get(14)).toString());
				caseFileMakerVo.setLbxCourtType(CommonFunction.checkNull(data.get(15)).toString());
				caseFileMakerVo.setCourtNameDesc(CommonFunction.checkNull(data.get(16)).toString());
				caseFileMakerVo.setLbxCourtName(CommonFunction.checkNull(data.get(17)).toString());	
				caseFileMakerVo.setJudgeName(CommonFunction.checkNull(data.get(18)).toString());
				caseFileMakerVo.setPoaDesc(CommonFunction.checkNull(data.get(19)).toString());
				caseFileMakerVo.setLbxPoa(CommonFunction.checkNull(data.get(20)).toString());
				if(CommonFunction.checkNull(data.get(21)).trim().equalsIgnoreCase(""))
				{
					caseFileMakerVo.setRecoveryAmount("0");
				}
				else
				{
					
					caseFileMakerVo.setRecoveryAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(21)))));
				}
				
				//caseFileMakerVo.setRecoveryAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(21)))));
				caseFileMakerVo.setFileMakerRemarks(CommonFunction.checkNull(data.get(22)).toString());
				
				caseFileMakerList.add(caseFileMakerVo);

			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return caseFileMakerList;
}

public boolean saveLegalCaseFileMaker(Object ob) {
	LegalCaseFileVo vo = (LegalCaseFileVo) ob;
	String legalId = (String) vo.getLoanId();
	logger.info("getRecStatus():-" + vo.getRecStatus());
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	ArrayList updatelist = new ArrayList();
	

	boolean status = false;
	String saveForwardFlag = CommonFunction.checkNull(vo.getSaveForwardFlag());
	
	logger.info("## IN updateLegalNoticeInitiationMakerData(): saveForwardFlag :==>> "+saveForwardFlag); 
	

	try {
		
		StringBuffer bufInsSql = new StringBuffer();
		logger
				.info("In updateCountryData..........inside ejb server file............Dao Impl");
		bufInsSql.append(" UPDATE LEGAL_CASE_DTL SET CASE_NO=?,COURT_FEE=?, " );
		bufInsSql.append(" CASE_FILE_DATE= " );
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
		bufInsSql.append(" DATE_OF_HEARING= " );
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
		bufInsSql.append(" STAGE=?,SECTION=?,COURT_TYPE=?,");
		bufInsSql.append(" COURT_NAME=?,JUDGE_NAME=?,POA=?,RECOVERY_AMOUNT=?, ");
		bufInsSql.append(" CASE_FILE_MAKER_REMARKS=?,CASE_FILE_MAKER_ID=?, ");
		bufInsSql.append("CASE_FILE_MAKER_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
		bufInsSql.append(" REC_STATUS=?");
		bufInsSql.append(" where LEGAL_ID=?");

		if (CommonFunction.checkNull(vo.getCaseNo())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getCaseNo()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getCourtFee())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse(vo.getCourtFee()).toString());
		
		if (CommonFunction.checkNull(vo.getCaseFileDate())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getCaseFileDate()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getDateOfHearing())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getDateOfHearing()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getLbxStage())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLbxStage()
					.toUpperCase().trim());
		
		
		
		if (CommonFunction.checkNull(vo.getSection())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getSection()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getLbxCourtType())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLbxCourtType()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getLbxCourtName())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLbxCourtName()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getJudgeName())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getJudgeName()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getLbxPoa())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLbxPoa()
					.toUpperCase().trim());
		

		
		
		if (CommonFunction.checkNull(vo.getRecoveryAmount())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse(vo.getRecoveryAmount()).toString());
		
		if (CommonFunction.checkNull(vo.getFileMakerRemarks())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getFileMakerRemarks()
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
		
		
		if(saveForwardFlag.equalsIgnoreCase("S"))
		{
			insertPrepStmtObject.addString("CFM");
		}
		else if(saveForwardFlag.equalsIgnoreCase("F"))
		{
			insertPrepStmtObject.addString("CFF");
		}
		
		//---------------------------------- 

		if (CommonFunction.checkNull(vo.getLegalId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLegalId());

		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		logger.info("In getListOfValues" + bufInsSql.toString());
		
		logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
		
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}


public ArrayList searchLegalCaseFileAuthor(Object ob) {
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	String loanId = null;

	ArrayList searchlist = new ArrayList();
	LegalCaseFileVo vo = (LegalCaseFileVo) ob;
	ArrayList<LegalCaseFileVo> detailList = new ArrayList<LegalCaseFileVo>();
	try {

		logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
		
		loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanId())).trim());
	
       	StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		bufInsSql.append(" SELECT LEGAL_ID,(SELECT TOP 1 LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LCD.LOAN_ID) LOAN_NO, ");
		bufInsSql.append(" (SELECT TOP 1 CASE_TYPE_DESC FROM LEG_CASE_TYPE_DEF_M WHERE CASE_TYPE_CODE = LCD.CASE_TYPE ) CASE_TYPE, ");
		bufInsSql.append(" (SELECT TOP 1 PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = LCD.PRODUCT_ID) PRODUCT, ");
		bufInsSql.append(" (SELECT TOP 1 BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID  = LCD.BRANCH_ID) BRANCH  ");
		bufInsSql.append(" FROM LEGAL_CASE_DTL LCD WHERE REC_STATUS = 'CFF' ");
		
		bufInsSqlTempCount.append(" SELECT COUNT(1) FROM LEGAL_CASE_DTL LCD WHERE REC_STATUS = 'CFF' ");
		
		bufInsSql.append(" AND LCD.CASE_FILE_MAKER_ID <> '" + vo.getMakerId() + "' ");
		bufInsSqlTempCount.append(" AND LCD.CASE_FILE_MAKER_ID <> '" + vo.getMakerId() + "' ");
		
		

		if (!loanId.equals("")) {
			bufInsSql.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
		}
		
		logger.info("search Query...." + bufInsSql);
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
			
			bufInsSql.append(" ORDER BY LEGAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
						

		logger.info("query : "+bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
		logger.info("In searchCaseTypeMasterData.....................................Dao Impl");
		logger.info("searchCaseTypeMasterData " + searchlist.size());

		for (int i = 0; i < searchlist.size(); i++) {
			// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

			ArrayList data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				LegalCaseFileVo caseFileVo = new LegalCaseFileVo();
				caseFileVo.setLegalId("<a href=legalCaseFileAuthorDispatch.do?method=openLegalCaseFileMakerAuthor&legalId="
								+ CommonFunction.checkNull(data.get(0)).toString()
								+ ">"
								+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
				caseFileVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
				caseFileVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(2)).toString());
				caseFileVo.setProduct(CommonFunction.checkNull(data.get(3)).toString());
				caseFileVo.setBranch(CommonFunction.checkNull(data.get(4)).toString());
				caseFileVo.setTotalRecordSize(count);
				detailList.add(caseFileVo);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	/*if(searchlist.size()==0)
	{
		LegalNoticeInitiationVo deptMVO = new LegalNoticeInitiationVo();
		deptMVO.setTotalRecordSize(count);
		detailList.add(deptMVO);
		request.setAttribute("flag","yes");
		logger.info("Detail List when searchList is null: "+detailList.size());
	}*/

	return detailList;
}

public ArrayList getLegalCaseFileMakerDataForAuthorView(Object ob) {

	ArrayList searchlist = new ArrayList();
	LegalCaseFileVo vo = (LegalCaseFileVo)ob;
	ArrayList<LegalCaseFileVo> caseFileMakerList = new ArrayList<LegalCaseFileVo>();
	logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanId());

	try {
		

		StringBuffer bufInsSql = new StringBuffer();
		
		bufInsSql.append(" SELECT LEGAL_ID,(SELECT TOP 1 LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LCD.LOAN_ID) LOAN_NO, " );
		bufInsSql.append(" (SELECT TOP 1 CUSTOMER_NAME FROM GCD_CUSTOMER_M WHERE CUSTOMER_ID = LCD.CUSTOMER_ID) CUSTOMER,   ");
		bufInsSql.append(" (SELECT TOP 1 PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = LCD.PRODUCT_ID) PRODUCT, ");
		bufInsSql.append(" (SELECT TOP 1 SCHEME_DESC FROM CR_SCHEME_M WHERE SCHEME_ID = LCD.SCHEME_ID) SCHEME, ");
		bufInsSql.append(" (SELECT TOP 1 CASE_TYPE_DESC FROM LEG_CASE_TYPE_DEF_M WHERE CASE_TYPE_CODE = LCD.CASE_TYPE) CASE_TYPE, ");
		bufInsSql.append(" (SELECT TOP 1 USER_NAME FROM SEC_USER_M WHERE USER_ID = LCD.ADVOCATE_ID) ADVOCATE, ");
		bufInsSql.append(" CASE_NO,COURT_FEE,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(CASE_FILE_DATE,'"+ dateFormat +"'), ");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(DATE_OF_HEARING,'"+ dateFormat +"'), ");
		bufInsSql.append(" (SELECT TOP 1 STAGE_DESC FROM LEG_STAGE_DEF_M WHERE STAGE_CODE = LCD.STAGE) STAGE_DESC, ");
		bufInsSql.append(" STAGE AS STAGE_CODE,SECTION, ");
		bufInsSql.append(" (SELECT TOP 1 COURT_TYPE_DESC FROM LEG_COURT_TYPE_DEF_M WHERE COURT_TYPE_CODE = LCD.COURT_TYPE) AS COURT_TYPE_DESC, ");
		bufInsSql.append(" COURT_TYPE,(SELECT TOP 1 COURT_NAME_DESC FROM LEG_COURT_NAME_DEF_M WHERE COURT_NAME_CODE = LCD.COURT_NAME) COURT_NAME_DESC, ");
		bufInsSql.append(" COURT_NAME,JUDGE_NAME, ");
		bufInsSql.append(" (SELECT TOP 1 USER_NAME FROM SEC_USER_M WHERE USER_ID = LCD.POA) POA_DESC, ");
		bufInsSql.append(" POA, ");
		bufInsSql.append(" RECOVERY_AMOUNT,CASE_FILE_MAKER_REMARKS ");
		bufInsSql.append(" FROM LEGAL_CASE_DTL LCD WHERE LEGAL_ID='"+StringEscapeUtils.escapeSql(vo.getLegalId())+"' ");
		
		
	
		logger.info("search Query...." + bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		logger.info("IN editCaseTypeData() search query1 ### "+ bufInsSql.toString());
		logger.info("editCaseTypeData " + searchlist.size());
		for (int i = 0; i < searchlist.size(); i++) {
			logger.info("editCaseTypeData " + searchlist.get(i).toString());
			ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				LegalCaseFileVo caseFileMakerVo = new LegalCaseFileVo();
				
				caseFileMakerVo.setLegalId(CommonFunction.checkNull(data.get(0)).toString());
				caseFileMakerVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
				caseFileMakerVo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
				caseFileMakerVo.setProduct(CommonFunction.checkNull(data.get(3)).toString());	
				caseFileMakerVo.setScheme(CommonFunction.checkNull(data.get(4)).toString());
				caseFileMakerVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(5)).toString());
				caseFileMakerVo.setAdvocate(CommonFunction.checkNull(data.get(6)).toString());
				caseFileMakerVo.setCaseNo(CommonFunction.checkNull(data.get(7)).toString());
				caseFileMakerVo.setCourtFee(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(8)))));
				caseFileMakerVo.setCaseFileDate(CommonFunction.checkNull(data.get(9)).toString());
				caseFileMakerVo.setDateOfHearing(CommonFunction.checkNull(data.get(10)).toString());	
				caseFileMakerVo.setStageDesc(CommonFunction.checkNull(data.get(11)).toString());
				caseFileMakerVo.setLbxStage(CommonFunction.checkNull(data.get(12)).toString());
				caseFileMakerVo.setSection(CommonFunction.checkNull(data.get(13)).toString());
				caseFileMakerVo.setCourtTypeDesc(CommonFunction.checkNull(data.get(14)).toString());
				caseFileMakerVo.setLbxCourtType(CommonFunction.checkNull(data.get(15)).toString());
				caseFileMakerVo.setCourtNameDesc(CommonFunction.checkNull(data.get(16)).toString());
				caseFileMakerVo.setLbxCourtName(CommonFunction.checkNull(data.get(17)).toString());	
				caseFileMakerVo.setJudgeName(CommonFunction.checkNull(data.get(18)).toString());
				caseFileMakerVo.setPoaDesc(CommonFunction.checkNull(data.get(19)).toString());
				caseFileMakerVo.setLbxPoa(CommonFunction.checkNull(data.get(20)).toString());
				caseFileMakerVo.setRecoveryAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(21)))));
				caseFileMakerVo.setFileMakerRemarks(CommonFunction.checkNull(data.get(22)).toString());
				caseFileMakerList.add(caseFileMakerVo);

			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return caseFileMakerList;
}

public boolean saveLegalCaseFileAuthor(Object ob) {
	LegalCaseFileVo vo = (LegalCaseFileVo) ob;
	String legalId = (String) vo.getLoanId();
	logger.info("getRecStatus():-" + vo.getRecStatus());
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	ArrayList updatelist = new ArrayList();
	

	boolean status = false;
	String saveForwardFlag = CommonFunction.checkNull(vo.getSaveForwardFlag());
	
	logger.info("## IN updateLegalNoticeInitiationMakerData(): saveForwardFlag :==>> "+saveForwardFlag); 
	

	try {
		
		StringBuffer bufInsSql = new StringBuffer();
		logger
				.info("In updateCountryData..........inside ejb server file............Dao Impl");
		bufInsSql.append(" UPDATE LEGAL_CASE_DTL SET CASE_FILE_AUTHOR_REMARKS=?, " );
		bufInsSql.append(" CASE_FILE_AUTHOR_ID=?, ");
		bufInsSql.append("CASE_FILE_AUTHOR_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
		bufInsSql.append(" REC_STATUS=?");
		bufInsSql.append(" where LEGAL_ID=?");

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
			insertPrepStmtObject.addString(vo.getDecision()
					.toUpperCase().trim());
		
				
		//---------------------------------- 

		if (CommonFunction.checkNull(vo.getLegalId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLegalId());

		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		logger.info("In getListOfValues" + bufInsSql.toString());
		
		logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
		
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}

public ArrayList searchLegalCourtProcessingMaker(Object ob) {
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	String loanId = null;

	ArrayList searchlist = new ArrayList();
	LegalCourtProcessingVo vo = (LegalCourtProcessingVo) ob;
	ArrayList<LegalCourtProcessingVo> detailList = new ArrayList<LegalCourtProcessingVo>();
	try {

		logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
		
		loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanId())).trim());
	
       	StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		bufInsSql.append(" SELECT LEGAL_ID,CASE_TYPE,(SELECT TOP 1 LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LCD.LOAN_ID) LOAN_NO, ");
		bufInsSql.append(" (SELECT TOP 1 CASE_TYPE_DESC FROM LEG_CASE_TYPE_DEF_M WHERE CASE_TYPE_CODE = LCD.CASE_TYPE ) CASE_TYPE, ");
		bufInsSql.append(" (SELECT TOP 1 PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = LCD.PRODUCT_ID) PRODUCT, ");
		bufInsSql.append(" (SELECT TOP 1 BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID  = LCD.BRANCH_ID) BRANCH  ");
		bufInsSql.append(" FROM LEGAL_CASE_DTL LCD WHERE REC_STATUS IN ('CFA','CPM','CPA') AND ADVOCATE_ID ='"+StringEscapeUtils.escapeSql(vo.getMakerId())+"' ");
		
		bufInsSqlTempCount.append(" SELECT COUNT(1) FROM LEGAL_CASE_DTL LCD WHERE REC_STATUS IN ('CFA','CPM','CPA') AND ADVOCATE_ID ='"+StringEscapeUtils.escapeSql(vo.getMakerId())+"' ");

		if (!loanId.equals("")) {
			bufInsSql.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
		}
		
		logger.info("search Query...." + bufInsSql);
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
			
			bufInsSql.append(" ORDER BY LEGAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
						

		logger.info("query : "+bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
		logger.info("In searchCaseTypeMasterData.....................................Dao Impl");
		logger.info("searchCaseTypeMasterData " + searchlist.size());

		for (int i = 0; i < searchlist.size(); i++) {
			// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

			ArrayList data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				LegalCourtProcessingVo caseFileVo = new LegalCourtProcessingVo();
				caseFileVo.setLegalId("<a href=legalCourtProcessingMakerDispatch.do?method=openLegalCourtProcessingMaker&legalId="
								+ CommonFunction.checkNull(data.get(0)).toString()+"&caseType="+CommonFunction.checkNull(data.get(1)).toString()
								+ ">"
								+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
				caseFileVo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
				caseFileVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(3)).toString());
				caseFileVo.setProduct(CommonFunction.checkNull(data.get(4)).toString());
				caseFileVo.setBranch(CommonFunction.checkNull(data.get(5)).toString());
				caseFileVo.setTotalRecordSize(count);
				detailList.add(caseFileVo);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	/*if(searchlist.size()==0)
	{
		LegalNoticeInitiationVo deptMVO = new LegalNoticeInitiationVo();
		deptMVO.setTotalRecordSize(count);
		detailList.add(deptMVO);
		request.setAttribute("flag","yes");
		logger.info("Detail List when searchList is null: "+detailList.size());
	}*/

	return detailList;
}

public ArrayList openEditLegalCourtProcessingMaker(Object ob) {

	ArrayList searchlist = new ArrayList();
	LegalCourtProcessingVo vo = (LegalCourtProcessingVo)ob;
	ArrayList<LegalCourtProcessingVo> caseFileMakerList = new ArrayList<LegalCourtProcessingVo>();
	logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanId());

	try {
		

		StringBuffer bufInsSql = new StringBuffer();
		bufInsSql.append(" SELECT LEGAL_ID,(SELECT TOP 1 LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LCD.LOAN_ID) LOAN_NO, " );
		bufInsSql.append(" (SELECT TOP 1 CUSTOMER_NAME FROM GCD_CUSTOMER_M WHERE CUSTOMER_ID = LCD.CUSTOMER_ID) CUSTOMER,   ");
		bufInsSql.append(" (SELECT TOP 1 PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = LCD.PRODUCT_ID) PRODUCT, ");
		bufInsSql.append(" (SELECT TOP 1 SCHEME_DESC FROM CR_SCHEME_M WHERE SCHEME_ID = LCD.SCHEME_ID) SCHEME, ");
		bufInsSql.append(" (SELECT TOP 1 CASE_TYPE_DESC FROM LEG_CASE_TYPE_DEF_M WHERE CASE_TYPE_CODE = LCD.CASE_TYPE) CASE_TYPE, ");
		bufInsSql.append(" (SELECT TOP 1 USER_NAME FROM SEC_USER_M WHERE USER_ID = LCD.ADVOCATE_ID) ADVOCATE, ");
		bufInsSql.append(" CASE_NO,COURT_FEE,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(CASE_FILE_DATE,'"+ dateFormat +"'), ");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(DATE_OF_HEARING,'"+ dateFormat +"'), ");
		bufInsSql.append(" (SELECT TOP 1 STAGE_DESC FROM LEG_STAGE_DEF_M WHERE STAGE_CODE = LCD.STAGE) STAGE_DESC, ");
		bufInsSql.append(" STAGE AS STAGE_CODE,SECTION, ");
		bufInsSql.append(" (SELECT TOP 1 COURT_TYPE_DESC FROM LEG_COURT_TYPE_DEF_M WHERE COURT_TYPE_CODE = LCD.COURT_TYPE) AS COURT_TYPE_DESC, ");
		bufInsSql.append(" COURT_TYPE,(SELECT TOP 1 COURT_NAME_DESC FROM LEG_COURT_NAME_DEF_M WHERE COURT_NAME_CODE = LCD.COURT_NAME) COURT_NAME_DESC, ");
		bufInsSql.append(" COURT_NAME,JUDGE_NAME, ");
		bufInsSql.append(" (SELECT TOP 1 USER_NAME FROM SEC_USER_M WHERE USER_ID = LCD.POA) POA_DESC, ");
		bufInsSql.append(" POA, ");
		bufInsSql.append(" RECOVERY_AMOUNT,CASE_FILE_MAKER_REMARKS,LCD.ADVOCATE_ID,LCD.CASE_TYPE,LCD.LOAN_ID ");
		bufInsSql.append(" FROM LEGAL_CASE_DTL LCD WHERE LEGAL_ID='"+StringEscapeUtils.escapeSql(vo.getLegalId())+"' ");
	
		logger.info("search Query...." + bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		logger.info("IN editCaseTypeData() search query1 ### "+ bufInsSql.toString());
		logger.info("editCaseTypeData " + searchlist.size());
		for (int i = 0; i < searchlist.size(); i++) {
			logger.info("editCaseTypeData " + searchlist.get(i).toString());
			ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				LegalCourtProcessingVo caseFileMakerVo = new LegalCourtProcessingVo();
				
				caseFileMakerVo.setLegalId(CommonFunction.checkNull(data.get(0)).toString());
				caseFileMakerVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
				caseFileMakerVo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
				caseFileMakerVo.setProduct(CommonFunction.checkNull(data.get(3)).toString());	
				caseFileMakerVo.setScheme(CommonFunction.checkNull(data.get(4)).toString());
				caseFileMakerVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(5)).toString());
				caseFileMakerVo.setAdvocateDesc(CommonFunction.checkNull(data.get(6)).toString());
				caseFileMakerVo.setCaseNo(CommonFunction.checkNull(data.get(7)).toString());
				caseFileMakerVo.setCourtFee(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(8)))));
				caseFileMakerVo.setCaseFileDate(CommonFunction.checkNull(data.get(9)).toString());
				caseFileMakerVo.setDateOfHearing(CommonFunction.checkNull(data.get(10)).toString());	
				caseFileMakerVo.setStageDesc(CommonFunction.checkNull(data.get(11)).toString());
				caseFileMakerVo.setLbxStage(CommonFunction.checkNull(data.get(12)).toString());
				caseFileMakerVo.setSection(CommonFunction.checkNull(data.get(13)).toString());
				caseFileMakerVo.setCourtTypeDesc(CommonFunction.checkNull(data.get(14)).toString());
				caseFileMakerVo.setLbxCourtType(CommonFunction.checkNull(data.get(15)).toString());
				caseFileMakerVo.setCourtNameDesc(CommonFunction.checkNull(data.get(16)).toString());
				caseFileMakerVo.setLbxCourtName(CommonFunction.checkNull(data.get(17)).toString());	
				caseFileMakerVo.setJudgeName(CommonFunction.checkNull(data.get(18)).toString());
				caseFileMakerVo.setPoaDesc(CommonFunction.checkNull(data.get(19)).toString());
				caseFileMakerVo.setLbxPoa(CommonFunction.checkNull(data.get(20)).toString());
				caseFileMakerVo.setRecoveryAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(21)))));
				caseFileMakerVo.setFileMakerRemarks(CommonFunction.checkNull(data.get(22)).toString());
				caseFileMakerVo.setAdvocate(CommonFunction.checkNull(data.get(23)).toString());
				caseFileMakerVo.setLbxCaseTypeCode(CommonFunction.checkNull(data.get(24)).toString());
				caseFileMakerVo.setLoanId(CommonFunction.checkNull(data.get(25)).toString());
				
			
				caseFileMakerList.add(caseFileMakerVo);

			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return caseFileMakerList;
}

public ArrayList getStageData(Object ob) {

	ArrayList searchlist = new ArrayList();
	LegalCourtProcessingVo vo = (LegalCourtProcessingVo)ob;
	ArrayList<LegalCourtProcessingVo> caseStageList = new ArrayList<LegalCourtProcessingVo>();
	logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanId());

	try {
		

		StringBuffer bufInsSql = new StringBuffer();
		bufInsSql.append(" SELECT LSDM.STAGE_CODE,LSDM.STAGE_DESC,LCSD.CASE_NO,LCSD.COURT_PROCESSING_MAKER_REMARKS, " );
		bufInsSql.append(dbo);
		bufInsSql.append(" DATE_FORMAT(LCSD.HEARING_DATE,'"+ dateFormat +"'), ");
		bufInsSql.append(" (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID = LCSD.POA) POA_DESC,LCSD.POA, ");
		bufInsSql.append(" ISNULL((SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID =  LCSD.ADVOCATE AND LCSD.LEGAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLegalId()))+"'),(SELECT  USER_NAME FROM SEC_USER_M,LEGAL_CASE_DTL LCD WHERE USER_ID = LCD.ADVOCATE_ID AND LCD.LEGAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLegalId()))+"'))  ADVOCATE, ");
		bufInsSql.append(dbo);
		bufInsSql.append(" DATE_FORMAT(LCSD.COURT_PROCESSING_MAKER_DATE,'"+ dateFormat +"'), ");
		bufInsSql.append(" LSDM.CLOSURE_STAGE_FLAG,ISNULL(LCSD.APPROVAL_FLAG,'N'), ");
		bufInsSql.append(" LCSD.ADVOCATE,LCSD.COURT_PROCESSING_AUTHOR_ID,LCSD.COURT_PROCESSING_AUTHOR_DATE ");
		bufInsSql.append(" FROM LEG_STAGE_DEF_M LSDM ");
		bufInsSql.append(" LEFT JOIN LEGAL_CASE_STAGE_DTL LCSD ON (LCSD.STAGE = LSDM.STAGE_CODE AND LCSD.LEGAL_ID = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLegalId()))+"') ");
		bufInsSql.append(" WHERE LSDM.CASE_TYPE_CODE = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxCaseTypeCode()))+"' AND LSDM.REC_STATUS = 'A' ");
		bufInsSql.append(" ORDER BY LSDM.SEQUENCE_NO,LCSD.HEARING_DATE ");

	
		logger.info("search Query...." + bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		logger.info("IN editCaseTypeData() search query1 ### "+ bufInsSql.toString());
		logger.info("editCaseTypeData " + searchlist.size());
		for (int i = 0; i < searchlist.size(); i++) {
			logger.info("editCaseTypeData " + searchlist.get(i).toString());
			ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				LegalCourtProcessingVo caseStageVo = new LegalCourtProcessingVo();
				
				caseStageVo.setLbxStage(CommonFunction.checkNull(data.get(0)).toString());
				caseStageVo.setStageDesc(CommonFunction.checkNull(data.get(1)).toString());
				caseStageVo.setCaseNo(CommonFunction.checkNull(data.get(2)).toString());
				caseStageVo.setMakerRemarks(CommonFunction.checkNull(data.get(3)).toString());	
				caseStageVo.setDateOfHearing(CommonFunction.checkNull(data.get(4)).toString());
				caseStageVo.setPoaDesc(CommonFunction.checkNull(data.get(5)).toString());
				caseStageVo.setLbxPoa(CommonFunction.checkNull(data.get(6)).toString());
				caseStageVo.setAdvocateDesc(CommonFunction.checkNull(data.get(7)).toString());
				caseStageVo.setMakerDate(CommonFunction.checkNull(data.get(8)).toString());
				caseStageVo.setClosureFlag(CommonFunction.checkNull(data.get(9)).toString());
				caseStageVo.setApprovalFlag(CommonFunction.checkNull(data.get(10)).toString());
				caseStageVo.setAdvocate(CommonFunction.checkNull(data.get(11)).toString());
				caseStageVo.setAutherId(CommonFunction.checkNull(data.get(12)).toString());
				caseStageVo.setAutherDate(CommonFunction.checkNull(data.get(13)).toString());
				caseStageList.add(caseStageVo);

			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return caseStageList;
}

public boolean insertCourtProcessingMakerDetails(Object ob,String [] stageList,String [] caseNoList,String [] remarkList,String [] hearingDateList,String [] poaList,String [] approvalFlagList,String [] makerDateList) {
	LegalCourtProcessingVo vo = (LegalCourtProcessingVo) ob;
	boolean status = false;
	ArrayList qryList = new ArrayList();
	StringBuilder bufInsSql = null;
	PrepStmtObject insertPrepStmtObject = null;

	try {
		
		   bufInsSql=new StringBuilder();
           insertPrepStmtObject =  new PrepStmtObject();
           bufInsSql.append("DELETE FROM LEGAL_CASE_STAGE_DTL WHERE LEGAL_ID='"+vo.getLegalId()+"'");
           logger.info("bufInsUpdSql DELETE Query:---" + bufInsSql.toString());
           insertPrepStmtObject.setSql(bufInsSql.toString());
           qryList.add(insertPrepStmtObject);
           logger.info("## In insertCourtProcessingMakerDetails () : makerDateList : ==>> "+makerDateList);
           logger.info("## In insertCourtProcessingMakerDetails () : makerDateList.length : ==>> "+makerDateList.length);
           bufInsSql = null;
           insertPrepStmtObject = null;
           
			
			for (int i = 0; i < caseNoList.length; i++) {
				
				bufInsSql = new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				
				bufInsSql.append("INSERT INTO LEGAL_CASE_STAGE_DTL (LEGAL_ID,LOAN_ID,STAGE,CASE_NO,COURT_PROCESSING_MAKER_REMARKS,HEARING_DATE,POA,ADVOCATE,APPROVAL_FLAG,COURT_PROCESSING_MAKER_ID,COURT_PROCESSING_MAKER_DATE) ");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//LEGAL_ID
				bufInsSql.append(" ?,");//LOAN_ID
				bufInsSql.append(" ?,");//STAGE
				bufInsSql.append(" ?,");//CASE_NO
				bufInsSql.append(" ?,");//COURT_PROCESSING_MAKER_REMARKS
				bufInsSql.append(dbo);//HEARING_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//POA
				bufInsSql.append(" ?,"); //ADVOCATE
				bufInsSql.append(" ?,"); //APPROVAL_FLAG
				bufInsSql.append(" ?,");//COURT_PROCESSING_MAKER_ID
				bufInsSql.append(dbo);//COURT_PROCESSING_MAKER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				if (CommonFunction.checkNull(vo.getLegalId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLegalId()
							.toUpperCase());
				
				if (CommonFunction.checkNull(vo.getLoanId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLoanId()
							.toUpperCase());

				if (CommonFunction.checkNull(stageList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stageList[i]);
				
				if (CommonFunction.checkNull(caseNoList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(caseNoList[i]);
				
				if (CommonFunction.checkNull(remarkList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(remarkList[i]);
					
				if (CommonFunction.checkNull(hearingDateList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(hearingDateList[i]);
				
				if (CommonFunction.checkNull(poaList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(poaList[i]);
				
				if (CommonFunction.checkNull(vo.getAdvocate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAdvocate()
							.toUpperCase());
				
				if (CommonFunction.checkNull(approvalFlagList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(approvalFlagList[i]);
				
				
				if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
				""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
			
				if (CommonFunction.checkNull(makerDateList[i]).toString().equalsIgnoreCase("B"))//B means blank
					insertPrepStmtObject.addString(vo.getMakerDate());
				else
					insertPrepStmtObject.addString(makerDateList[i]);
				

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertUserMaster() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
			}
			
			bufInsSql = null;
	        insertPrepStmtObject = null;
	        
	        bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			
			bufInsSql.append(" UPDATE LEGAL_CASE_DTL SET REC_STATUS=?, " );
			bufInsSql.append(" COURT_PROCESSING_MAKER_ID=?, ");
			bufInsSql.append("COURT_PROCESSING_MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where LEGAL_ID=?");
			
			if(CommonFunction.checkNull(vo.getSaveForwardFlag()).equalsIgnoreCase("S"))
				insertPrepStmtObject.addString("CPM");
			else
				insertPrepStmtObject.addString("CPF");	
			
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
			
					
			//---------------------------------- 

			if (CommonFunction.checkNull(vo.getLegalId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLegalId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			qryList.add(insertPrepStmtObject);
			
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveCountryData......................"
					+ status);
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
	

	
}

public boolean addRowofCourtProcessingMaker(Object ob) {
	LegalCourtProcessingVo vo = (LegalCourtProcessingVo) ob;
	String legalId = (String) vo.getLoanId();
	logger.info("getRecStatus():-" + vo.getRecStatus());
	
	ArrayList qryList = new ArrayList();
	

	boolean status = false;
	
	
	
	

	try {
		
		StringBuilder bufInsSql = new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		bufInsSql.append("INSERT INTO LEGAL_CASE_STAGE_DTL (LEGAL_ID,LOAN_ID,STAGE,CASE_NO,COURT_PROCESSING_MAKER_REMARKS,HEARING_DATE,POA,ADVOCATE,COURT_PROCESSING_MAKER_ID,COURT_PROCESSING_MAKER_DATE) ");
		bufInsSql.append(" values ( ");
		bufInsSql.append(" ?,");//LEGAL_ID
		bufInsSql.append(" ?,");//LOAN_ID
		bufInsSql.append(" ?,");//STAGE
		bufInsSql.append(" ?,");//CASE_NO
		bufInsSql.append(" ?,");//COURT_PROCESSING_MAKER_REMARKS
		bufInsSql.append(dbo);//HEARING_DATE
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		bufInsSql.append(" ?,");//POA
		bufInsSql.append(" ?,"); //ADVOCATE
		bufInsSql.append(" ?,");//COURT_PROCESSING_MAKER_ID
		bufInsSql.append(dbo);//COURT_PROCESSING_MAKER_DATE
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
		
		if (CommonFunction.checkNull(vo.getLegalId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLegalId()
					.toUpperCase());
		
		if (CommonFunction.checkNull(vo.getLoanId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLoanId()
					.toUpperCase());

		if (CommonFunction.checkNull(vo.getLbxStage())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLbxStage());
		
		if (CommonFunction.checkNull(vo.getCaseNo())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getCaseNo());
		
		if (CommonFunction.checkNull(vo.getCourtProcessingMakerRemarks())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getCourtProcessingMakerRemarks());
			
		if (CommonFunction.checkNull(vo.getDateOfHearing())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getDateOfHearing());
		
		if (CommonFunction.checkNull(vo.getLbxPoa())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLbxPoa());
		
		
		if (CommonFunction.checkNull(vo.getAdvocate())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getAdvocate()
					.toUpperCase());
		
		
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
		qryList.add(insertPrepStmtObject);
		logger.info("In getListOfValues" + bufInsSql.toString());
		
		logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
		
		bufInsSql = null;
        insertPrepStmtObject = null;
        
        bufInsSql = new StringBuilder();
		insertPrepStmtObject = new PrepStmtObject();
		
		bufInsSql.append(" UPDATE LEGAL_CASE_DTL SET REC_STATUS=?, " );
		bufInsSql.append(" COURT_PROCESSING_MAKER_ID=?, ");
		bufInsSql.append("COURT_PROCESSING_MAKER_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
		bufInsSql.append(" where LEGAL_ID=?");
		
		if(vo.getMakerAuthorFlag().equalsIgnoreCase("A"))
		{
			insertPrepStmtObject.addString("CPF");
		}
		else
		{
			insertPrepStmtObject.addString("CPM");
		}
	
		
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
		
				
		//---------------------------------- 

		if (CommonFunction.checkNull(vo.getLegalId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLegalId());

		insertPrepStmtObject.setSql(bufInsSql.toString());
		
		logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());

		qryList.add(insertPrepStmtObject);
		
		
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);

	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}

public ArrayList searchLegalCourtProcessingAuthor(Object ob) {
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	String loanId = null;

	ArrayList searchlist = new ArrayList();
	LegalCourtProcessingVo vo = (LegalCourtProcessingVo) ob;
	ArrayList<LegalCourtProcessingVo> detailList = new ArrayList<LegalCourtProcessingVo>();
	try {

		logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
		
		loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanId())).trim());
	
       	StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		bufInsSql.append(" SELECT LEGAL_ID,CASE_TYPE,(SELECT TOP 1 LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LCD.LOAN_ID) LOAN_NO, ");
		bufInsSql.append(" (SELECT TOP 1 CASE_TYPE_DESC FROM LEG_CASE_TYPE_DEF_M WHERE CASE_TYPE_CODE = LCD.CASE_TYPE ) CASE_TYPE, ");
		bufInsSql.append(" (SELECT TOP 1 PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = LCD.PRODUCT_ID) PRODUCT, ");
		bufInsSql.append(" (SELECT TOP 1 BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID  = LCD.BRANCH_ID) BRANCH  ");
		bufInsSql.append(" FROM LEGAL_CASE_DTL LCD WHERE REC_STATUS IN ('CPF') ");
		
		bufInsSqlTempCount.append(" SELECT COUNT(1) FROM LEGAL_CASE_DTL LCD WHERE REC_STATUS = 'CPF' ");
		
		bufInsSql.append(" AND LCD.ADVOCATE_ID <> '" + vo.getMakerId() + "' ");
		bufInsSqlTempCount.append(" AND LCD.ADVOCATE_ID <> '" + vo.getMakerId() + "' ");

		if (!loanId.equals("")) {
			bufInsSql.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
		}
		
		logger.info("search Query...." + bufInsSql);
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
			
			bufInsSql.append(" ORDER BY LEGAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
						

		logger.info("query : "+bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
		logger.info("In searchCaseTypeMasterData.....................................Dao Impl");
		logger.info("searchCaseTypeMasterData " + searchlist.size());

		for (int i = 0; i < searchlist.size(); i++) {
			// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

			ArrayList data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				LegalCourtProcessingVo caseFileVo = new LegalCourtProcessingVo();
				caseFileVo.setLegalId("<a href=legalCourtProcessingAuthorDispatch.do?method=openLegalCourtProcessingAuthor&legalId="
								+ CommonFunction.checkNull(data.get(0)).toString()+"&caseType="+CommonFunction.checkNull(data.get(1)).toString()
								+ ">"
								+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
				caseFileVo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
				caseFileVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(3)).toString());
				caseFileVo.setProduct(CommonFunction.checkNull(data.get(4)).toString());
				caseFileVo.setBranch(CommonFunction.checkNull(data.get(5)).toString());
				caseFileVo.setTotalRecordSize(count);
				detailList.add(caseFileVo);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	/*if(searchlist.size()==0)
	{
		LegalNoticeInitiationVo deptMVO = new LegalNoticeInitiationVo();
		deptMVO.setTotalRecordSize(count);
		detailList.add(deptMVO);
		request.setAttribute("flag","yes");
		logger.info("Detail List when searchList is null: "+detailList.size());
	}*/

	return detailList;
}

public boolean saveCourtProcessingAuthorData(Object ob) {
	LegalCourtProcessingVo vo = (LegalCourtProcessingVo) ob;
	String legalId = (String) vo.getLegalId();
	logger.info("legalId:-" + legalId);
	
	ArrayList updatelist = new ArrayList();
	
	String approvalFlag = null;
	

	boolean status = false;
	String saveForwardFlag = CommonFunction.checkNull(vo.getSaveForwardFlag());
	
	logger.info("## IN updateLegalNoticeInitiationMakerData(): saveForwardFlag :==>> "+saveForwardFlag); 
	

	try {
		
		if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("CPA"))
			approvalFlag ="Y";
		else
			approvalFlag ="N";
		
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		logger.info("In updateCountryData..........inside ejb server file............Dao Impl");
		
		bufInsSql.append(" UPDATE LEGAL_CASE_STAGE_DTL SET APPROVAL_FLAG=?, COURT_PROCESSING_AUTHOR_REMARKS=?, " );
		bufInsSql.append(" COURT_PROCESSING_AUTHOR_ID=?, ");
		bufInsSql.append("COURT_PROCESSING_AUTHOR_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
		bufInsSql.append(" WHERE LEGAL_ID=? AND ISNULL(APPROVAL_FLAG,'N') <> 'Y'");
		
		insertPrepStmtObject.addString(CommonFunction.checkNull(approvalFlag).toUpperCase().trim());
		
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
		
				
		//---------------------------------- 

		if (CommonFunction.checkNull(vo.getLegalId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLegalId());

		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		logger.info("In getListOfValues" + bufInsSql.toString());
		
		logger.info("## In saveCourtProcessingAuthorData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
		
		String closedStageQuery = "SELECT B.CLOSURE_STAGE_FLAG FROM LEGAL_CASE_STAGE_DTL A , LEG_STAGE_DEF_M B, LEGAL_CASE_DTL C WHERE A.STAGE=B.STAGE_CODE AND B.REC_STATUS='A' AND A.LEGAL_ID='"+vo.getLegalId()+"' AND C.LEGAL_ID='"+vo.getLegalId()+"' AND B.CLOSURE_STAGE_FLAG ='Y' AND ISNULL(C.REOPEN_DATE,(A.COURT_PROCESSING_MAKER_DATE-1)) < A.COURT_PROCESSING_MAKER_DATE ";
		
		String closedStageFlag = CommonFunction.checkNull(ConnectionDAOforEJB.singleReturn(closedStageQuery));
		
		logger.info("## In saveCourtProcessingAuthorData() : closedStageFlag : ==>> "+closedStageFlag);
		
		String withdrawnFlag=null;
		String recStatus = null;
		
		if(closedStageFlag.equalsIgnoreCase("Y"))
		{
			withdrawnFlag = "Y";
			recStatus = "C";
		}
		else
		{
			withdrawnFlag = "N";
		    recStatus = vo.getDecision();
		}
		
		logger.info("## In saveCourtProcessingAuthorData() : withdrawnFlag : ==>> "+withdrawnFlag);
		
		logger.info("## In saveCourtProcessingAuthorData() : recStatus : ==>> "+recStatus);
		
		bufInsSql = null;
		insertPrepStmtObject = null;
		
		insertPrepStmtObject = new PrepStmtObject();
		bufInsSql = new StringBuffer();
		
		bufInsSql.append(" MERGE INTO LEGAL_CASE_DTL A USING (SELECT TOP 1 LEGAL_ID,POA,HEARING_DATE,STAGE,CASE_NO FROM LEGAL_CASE_STAGE_DTL WHERE LEGAL_ID=? ORDER BY COURT_PROCESSING_MAKER_DATE,CASE_STAGE_ID DESC ) B " );
		bufInsSql.append(" ON (A.LEGAL_ID=B.LEGAL_ID) WHEN MATCHED ");
		bufInsSql.append(" THEN UPDATE SET A.POA=B.POA,A.DATE_OF_HEARING=B.HEARING_DATE,A.STAGE=B.STAGE,A.CASE_NO=B.CASE_NO, ");
		bufInsSql.append(" A.WITHDRAWAL_FLAG=?,REC_STATUS=?,COURT_PROCESSING_AUTHOR_REMARKS=?,COURT_PROCESSING_AUTHOR_ID=?, ");
		bufInsSql.append("COURT_PROCESSING_AUTHOR_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9); ");
		
		if (CommonFunction.checkNull(vo.getLegalId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLegalId());
		
		if (CommonFunction.checkNull(withdrawnFlag)
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(withdrawnFlag);
		
		if (CommonFunction.checkNull(recStatus)
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(recStatus);
		

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
		

		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		
		logger.info("In getListOfValues" + bufInsSql.toString());
		
		logger.info("## In saveCourtProcessingAuthorData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
		
		if(closedStageFlag.equalsIgnoreCase("Y")){
		
		bufInsSql = null;
		insertPrepStmtObject = null;
		
		insertPrepStmtObject = new PrepStmtObject();
		bufInsSql = new StringBuffer();
		
		bufInsSql.append(" UPDATE CR_LOAN_DTL SET LEGAL_FLAG=?,LEGAL_REMARKS = ?, " );
		bufInsSql.append("LEGAL_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
		bufInsSql.append(" where LOAN_ID = (SELECT LOAN_ID FROM LEGAL_CASE_DTL WHERE LEGAL_ID = ?) ");

		
			insertPrepStmtObject.addString("Y");
		
		
		if (CommonFunction.checkNull(vo.getComments())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getComments()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getMakerDate())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerDate()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getLegalId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLegalId());

		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		
		
		logger.info("## In saveCourtProcessingAuthorData() : update query (FOR CR_LOAN_DTL) : ==>> "+insertPrepStmtObject.printQuery());
		
		
		}
		
		
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}

public ArrayList searchLegalReopenClosedCase(Object ob) {
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	String loanId = null;

	ArrayList searchlist = new ArrayList();
	LegalCourtProcessingVo vo = (LegalCourtProcessingVo) ob;
	ArrayList<LegalCourtProcessingVo> detailList = new ArrayList<LegalCourtProcessingVo>();
	try {

		logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
		
		loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanId())).trim());
	
       	StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		bufInsSql.append(" SELECT LEGAL_ID,CASE_TYPE,(SELECT TOP 1 LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LCD.LOAN_ID) LOAN_NO, ");
		bufInsSql.append(" (SELECT TOP 1 CASE_TYPE_DESC FROM LEG_CASE_TYPE_DEF_M WHERE CASE_TYPE_CODE = LCD.CASE_TYPE ) CASE_TYPE, ");
		bufInsSql.append(" (SELECT TOP 1 PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = LCD.PRODUCT_ID) PRODUCT, ");
		bufInsSql.append(" (SELECT TOP 1 BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID  = LCD.BRANCH_ID) BRANCH  ");
		bufInsSql.append(" FROM LEGAL_CASE_DTL LCD WHERE WITHDRAWAL_FLAG = 'Y' ");
		
		bufInsSqlTempCount.append(" SELECT COUNT(1) FROM LEGAL_CASE_DTL LCD WHERE WITHDRAWAL_FLAG = 'Y' ");

		if (!loanId.equals("")) {
			bufInsSql.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_ID = '" + loanId + "' ");
		}
		
		logger.info("search Query...." + bufInsSql);
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
			
			bufInsSql.append(" ORDER BY LEGAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
						

		logger.info("query : "+bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
		logger.info("In searchCaseTypeMasterData.....................................Dao Impl");
		logger.info("searchCaseTypeMasterData " + searchlist.size());

		for (int i = 0; i < searchlist.size(); i++) {
			// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

			ArrayList data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				LegalCourtProcessingVo caseFileVo = new LegalCourtProcessingVo();
				caseFileVo.setLegalId("<a href=legalReopenClosedCaseDispatchAction.do?method=openEditReopenClosedCase&legalId="
								+ CommonFunction.checkNull(data.get(0)).toString()+"&caseType="+CommonFunction.checkNull(data.get(1)).toString()
								+ ">"
								+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
				caseFileVo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
				caseFileVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(3)).toString());
				caseFileVo.setProduct(CommonFunction.checkNull(data.get(4)).toString());
				caseFileVo.setBranch(CommonFunction.checkNull(data.get(5)).toString());
				caseFileVo.setTotalRecordSize(count);
				detailList.add(caseFileVo);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	/*if(searchlist.size()==0)
	{
		LegalNoticeInitiationVo deptMVO = new LegalNoticeInitiationVo();
		deptMVO.setTotalRecordSize(count);
		detailList.add(deptMVO);
		request.setAttribute("flag","yes");
		logger.info("Detail List when searchList is null: "+detailList.size());
	}*/

	return detailList;
}

public boolean saveLegalReopenClosedCaseData(Object ob) {
	LegalCourtProcessingVo vo = (LegalCourtProcessingVo) ob;
	String legalId = (String) vo.getLegalId();
	logger.info("legalId:-" + legalId);
	
	ArrayList updatelist = new ArrayList();
	
	String approvalFlag = null;
	

	boolean status = false;
	String saveForwardFlag = CommonFunction.checkNull(vo.getSaveForwardFlag());
	
	logger.info("## IN saveLegalReopenClosedCaseData(): saveForwardFlag :==>> "+saveForwardFlag); 
	

	try {
		
		
		StringBuffer bufInsSql = new StringBuffer();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		logger.info("In updateCountryData..........inside ejb server file............Dao Impl");
		
		bufInsSql.append(" UPDATE LEGAL_CASE_DTL SET WITHDRAWAL_FLAG=?,REC_STATUS=?, REOPEN_CASE_REMARKS=?, " );
		bufInsSql.append(" REOPEN_BY=?, ");
		bufInsSql.append("REOPEN_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
		bufInsSql.append(" WHERE LEGAL_ID=? ");
		
		insertPrepStmtObject.addString("N");
		
		insertPrepStmtObject.addString("CPF");
		
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
		
				
		//---------------------------------- 

		if (CommonFunction.checkNull(vo.getLegalId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLegalId());

		insertPrepStmtObject.setSql(bufInsSql.toString());
		
		logger.info("## In saveLegalReopenClosedCaseData() : update query ( for LEGAL_CASE_DTL) : ==>> "+insertPrepStmtObject.printQuery());

		updatelist.add(insertPrepStmtObject);
		

		
		bufInsSql = null;
		insertPrepStmtObject = null;
		
		insertPrepStmtObject = new PrepStmtObject();
		bufInsSql = new StringBuffer();
		
		bufInsSql.append(" UPDATE CR_LOAN_DTL SET LEGAL_FLAG=?,LEGAL_REMARKS = ?, " );
		bufInsSql.append("LEGAL_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
		bufInsSql.append(" where LOAN_ID = (SELECT LOAN_ID FROM LEGAL_CASE_DTL WHERE LEGAL_ID = ?) ");

		
			insertPrepStmtObject.addString("N");
		
		
		if (CommonFunction.checkNull(vo.getComments())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getComments()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getMakerDate())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerDate()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getLegalId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLegalId());

		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		
		
		logger.info("## In saveLegalReopenClosedCaseData() : update query (FOR CR_LOAN_DTL) : ==>> "+insertPrepStmtObject.printQuery());
		
		
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}

public ArrayList searchAssignRejectCaseData(Object ob) {
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	String legalId = "";
	String loanNo = "";
	String product = "";
	String scheme = "";
	String caseType = "";
	String branch = "";
	ArrayList searchlist = new ArrayList();
	LegalCaseInitiationMakerVo vo = (LegalCaseInitiationMakerVo) ob;
	ArrayList<LegalCaseInitiationMakerVo> detailList = new ArrayList<LegalCaseInitiationMakerVo>();
	try {

		logger.info("In searchCaseTypeMasterData()..............inside ejb server file.......................Dao Impl");
		
		legalId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLegalId())).trim());
		branch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchId())).trim());
		loanNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanId())).trim());
		product = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductSearchID())).trim());
		scheme = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
		caseType = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxCaseTypeCode())).trim());
		
		
		
       	StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		bufInsSql.append(" SELECT LCD.LEGAL_ID,CLT.LOAN_NO,GCM.CUSTOMER_NAME,CBM.BRANCH_DESC,CPM.PRODUCT_DESC, CSM.SCHEME_DESC,DPD, TOTAL_OUTSTANDING,CHARGES,PRINCIPAL_OUTSTANDING, CASE_TYPE, INITIATION_MAKER_IMEMO, CRM.REASON_DESC, INITIATION_MAKER_REMARKS FROM LEGAL_CASE_DTL LCD ");
		bufInsSql.append(" INNER JOIN CR_LOAN_DTL CLT ON CLT.LOAN_ID=LCD.LOAN_ID ");
		bufInsSql.append(" INNER JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=LCD.CUSTOMER_ID ");
		bufInsSql.append(" INNER JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=LCD.BRANCH_ID ");
		bufInsSql.append(" INNER JOIN CR_SCHEME_M CSM ON CSM.SCHEME_ID=LCD.SCHEME_ID  ");
		bufInsSql.append("INNER JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID=LCD.PRODUCT_ID  ");
		bufInsSql.append("INNER JOIN COM_REASON_M CRM ON CRM.REASON_ID=LCD.INITIATION_MAKER_REASON  ");
			
				
		bufInsSql.append(" WHERE LCD.REC_STATUS = 'I'"); 
		bufInsSqlTempCount.append(" SELECT COUNT(1) FROM LEGAL_CASE_DTL LCD INNER JOIN CR_LOAN_DTL CLT ON CLT.LOAN_ID=LCD.LOAN_ID INNER JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=LCD.CUSTOMER_ID INNER JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=LCD.BRANCH_ID WHERE LCD.REC_STATUS = 'I' ");

		if (!loanNo.equals("")) {
			bufInsSql.append(" AND LCD.LOAN_ID = '" + loanNo + "' ");
			bufInsSqlTempCount.append(" AND LCD.LOAN_ID = '" + loanNo + "' ");
		}
		
		if (!branch.equals("")) {
			bufInsSql.append(" AND CBM.BRANCH_ID = '" + branch + "' ");
			bufInsSqlTempCount.append(" AND CBM.BRANCH_ID = '" + branch + "' ");
		}
		
		if (!legalId.equals("")) {
			bufInsSql.append(" AND LCD.LEGAL_ID = '" + legalId + "' ");
			bufInsSqlTempCount.append(" AND LCD.LEGAL_ID = '" + legalId + "' ");
		}
		
		if (!caseType.equals("")) {
			bufInsSql.append(" AND CASE_TYPE = '" + caseType + "' ");
			bufInsSqlTempCount.append(" AND CASE_TYPE = '" + caseType + "' ");
		}
		
		if (!product.equals("")) {
			bufInsSql.append(" AND CPM.PRODUCT_ID = '" + product + "' ");
			bufInsSqlTempCount.append(" AND LCD.PRODUCT_ID = '" + product + "' ");
		}
		
		if (!scheme.equals("")) {
			bufInsSql.append(" AND CSM.SCHEME_ID = '" + scheme + "' ");
			bufInsSqlTempCount.append(" AND LCD.SCHEME_ID = '" + scheme + "' ");
		}
		
		
		
		logger.info("search Query...." + bufInsSql);
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
			
			bufInsSql.append(" ORDER BY LEGAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
						

		logger.info("query : "+bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		logger.info("IN searchCaseTypeMasterData() search query1 ### "+ bufInsSql.toString());
		logger.info("In searchCaseTypeMasterData.....................................Dao Impl");
		logger.info("searchCaseTypeMasterData " + searchlist.size());

		for (int i = 0; i < searchlist.size(); i++) {
			// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

			ArrayList data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				LegalCaseInitiationMakerVo caseInitVo = new LegalCaseInitiationMakerVo();
				
				caseInitVo.setLegalId("<a href=assignRejectCaseDispatchAction.do?method=openEditAssignRejectCase&legalId="
						+ CommonFunction.checkNull(data.get(0)).toString()
						+ ">"
						+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
				
				caseInitVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
				caseInitVo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
				caseInitVo.setBranch(CommonFunction.checkNull(data.get(3)).toString());
				caseInitVo.setProduct(CommonFunction.checkNull(data.get(4)).toString());
				caseInitVo.setScheme(CommonFunction.checkNull(data.get(5)).toString());
				
				
				caseInitVo.setDpd(CommonFunction.checkNull(data.get(6)).toString());
				caseInitVo.setTotalOutsatanding(CommonFunction.checkNull(data.get(7)).toString());
				caseInitVo.setOtherCharges(CommonFunction.checkNull(data.get(8)).toString());
				caseInitVo.setPos(CommonFunction.checkNull(data.get(9)).toString());
				caseInitVo.setCaseType(CommonFunction.checkNull(data.get(10)).toString());
				caseInitVo.setiMemoBy(CommonFunction.checkNull(data.get(11)).toString());
				caseInitVo.setReason(CommonFunction.checkNull(data.get(12)).toString());
				caseInitVo.setRemark(CommonFunction.checkNull(data.get(13)).toString());
				caseInitVo.setTotalRecordSize(count);
				detailList.add(caseInitVo);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	/*if(searchlist.size()==0)
	{
		LegalNoticeInitiationVo deptMVO = new LegalNoticeInitiationVo();
		deptMVO.setTotalRecordSize(count);
		detailList.add(deptMVO);
		request.setAttribute("flag","yes");
		logger.info("Detail List when searchList is null: "+detailList.size());
	}*/

	return detailList;
}


public boolean insertAssignRejectCase(Object ob) {
	
	logger.info("## in insertAssignRejectCase()");
	
	LegalCaseInitiationMakerVo vo = (LegalCaseInitiationMakerVo) ob;
	String legalId = (String) vo.getLegalId();
	logger.info("getRecStatus():-" + vo.getRecStatus());
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	ArrayList updatelist = new ArrayList();
	

	boolean status = false;
	String updateForwardFlag = CommonFunction.checkNull(vo.getUpdateForwardFlag());
	logger.info("## IN insertAssignRejectCase(): updateForwardFlag :==>> "+updateForwardFlag); 
	
	logger.info("## IN insertAssignRejectCase(): noticeId :==>> "+legalId); 
	

	try {
		
		String rejectFlag = "";

		
			
			if (vo.getReject() != null
					&& vo.getReject().equals("R")) {
				rejectFlag = "R";
			} else {
				rejectFlag = "A";
			}
		
		StringBuffer bufInsSql = new StringBuffer();
		logger.info("In updateCountryData..........inside ejb server file............Dao Impl");
		bufInsSql.append(" UPDATE LEGAL_CASE_DTL SET ASSIGN_REJECT_IMEMO=?,ASSIGN_REJECT_REASON=?,ASSIGN_REJECT_REMARKS=?,ADVOCATE_ID=?,REC_STATUS=?,");
		bufInsSql.append("ASSIGN_REJECT_BY=?,ASSIGN_REJECT_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
		bufInsSql.append(" where LEGAL_ID=?");

		if (CommonFunction.checkNull(vo.getInitMakerImemo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getInitMakerImemo().toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getLbxReasonId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLbxReasonId().toUpperCase().trim());
		
		
		if (CommonFunction.checkNull(vo.getComments()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getComments().toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase().trim());
		
		
		if (CommonFunction.checkNull(rejectFlag).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(rejectFlag);
		
		
         if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerId());
		
		if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerDate());
	

		if (CommonFunction.checkNull(legalId).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(legalId);

		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		logger.info("In getListOfValues" + bufInsSql.toString());
		
		logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
		
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}
public ArrayList openEditAssignRejectCaseData(Object ob) {
	
	
	ArrayList<LegalCaseInitiationMakerVo> caseTypeList = new ArrayList<LegalCaseInitiationMakerVo>();
	
	LegalCaseInitiationMakerVo vo = (LegalCaseInitiationMakerVo)ob;
	
	logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanNo());

	try {
		
		ArrayList searchlist = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		bufInsSql.append(" SELECT LCD.LEGAL_ID,CLT.LOAN_NO,GCM.CUSTOMER_NAME,CBM.BRANCH_DESC,CPM.PRODUCT_DESC, CSM.SCHEME_DESC,DPD, TOTAL_OUTSTANDING,CHARGES,PRINCIPAL_OUTSTANDING, CASE_TYPE, INITIATION_MAKER_IMEMO, CRM.REASON_DESC,INITIATION_MAKER_REMARKS FROM LEGAL_CASE_DTL LCD ");
		bufInsSql.append(" INNER JOIN CR_LOAN_DTL CLT ON CLT.LOAN_ID=LCD.LOAN_ID ");
		bufInsSql.append(" INNER JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=LCD.CUSTOMER_ID ");
		bufInsSql.append(" INNER JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=LCD.BRANCH_ID ");
		bufInsSql.append(" INNER JOIN CR_SCHEME_M CSM ON CSM.SCHEME_ID=LCD.SCHEME_ID  ");
		bufInsSql.append("INNER JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID=LCD.PRODUCT_ID  ");
		bufInsSql.append("INNER JOIN COM_REASON_M CRM ON CRM.REASON_ID=LCD.INITIATION_MAKER_REASON  ");
			
		bufInsSql.append(" WHERE LEGAL_ID='"+StringEscapeUtils.escapeSql(vo.getLegalId())+"' ");		
		//bufInsSql.append(" WHERE LCD.REC_STATUS = 'I'"); 
		//bufInsSqlTempCount.append(" SELECT COUNT(1) FROM LEGAL_CASE_DTL LCD INNER JOIN CR_LOAN_DTL CLT ON CLT.LOAN_ID=LCD.LOAN_ID INNER JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=LCD.CUSTOMER_ID INNER JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=LCD.BRANCH_ID WHERE LCD.REC_STATUS = 'I' ");
		
		
	
		logger.info("search Query...." + bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		logger.info("IN editCaseTypeData() search query1 ### "+ bufInsSql.toString());
		logger.info("editCaseTypeData " + searchlist.size());
		for (int i = 0; i < searchlist.size(); i++) {
			logger.info("editCaseTypeData " + searchlist.get(i).toString());
			ArrayList data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				LegalCaseInitiationMakerVo caseInitMakerVo = new LegalCaseInitiationMakerVo();
				
				caseInitMakerVo.setLegalId(CommonFunction.checkNull(data.get(0)).toString());
				caseInitMakerVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
				caseInitMakerVo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
				caseInitMakerVo.setBranch(CommonFunction.checkNull(data.get(3)).toString());
				caseInitMakerVo.setProduct(CommonFunction.checkNull(data.get(4)).toString());
				caseInitMakerVo.setScheme(CommonFunction.checkNull(data.get(5)).toString());
				
				
				caseInitMakerVo.setDpd(CommonFunction.checkNull(data.get(6)).toString());
				caseInitMakerVo.setTotalOutsatanding(CommonFunction.checkNull(data.get(7)).toString());
				caseInitMakerVo.setOtherCharges(CommonFunction.checkNull(data.get(8)).toString());
				caseInitMakerVo.setPos(CommonFunction.checkNull(data.get(9)).toString());
				caseInitMakerVo.setCaseType(CommonFunction.checkNull(data.get(10)).toString());
				caseInitMakerVo.setiMemoBy(CommonFunction.checkNull(data.get(11)).toString());
				caseInitMakerVo.setReason(CommonFunction.checkNull(data.get(12)).toString());
				caseInitMakerVo.setRemark(CommonFunction.checkNull(data.get(13)).toString());
				
				caseTypeList.add(caseInitMakerVo);

			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	return caseTypeList;
}

public ArrayList searchReassignCaseData(Object ob) {
	{
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String loanId = null;
		String legalId = null;

		ArrayList searchlist = new ArrayList();
		ReassignCaseVo vo = (ReassignCaseVo) ob;
		ArrayList<ReassignCaseVo> detailList = new ArrayList<ReassignCaseVo>();
		try {

			logger.info("In searchReassignCaseData()..............inside ejb server file.......................Dao Impl");
			
			loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanId())).trim());
			legalId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLegalId())).trim());
			
		
	       	StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append(" SELECT LEGAL_ID,(SELECT LOAN_NO FROM CR_LOAN_DTL WHERE LOAN_ID = LCD.LOAN_ID) LOAN_NO,");
			bufInsSql.append(" (SELECT DISTINCT CUSTOMER_NAME FROM GCD_CUSTOMER_M WHERE CUSTOMER_ID = LCD.CUSTOMER_ID) CUST_NAME,"); 
			bufInsSql.append(" CASE_TYPE , ");
			
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(CASE_FILE_DATE,'"+ dateFormat +"') as CASE_FILE_DATE, ");
			
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(DATE_OF_HEARING,'"+ dateFormat +"') as HEARING_DATE, ");
			
			bufInsSql.append(" CASE_NO, ");
			bufInsSql.append(" (SELECT DISTINCT ADVOCATE_DESC FROM LEG_ADVOCATE_DEF_M WHERE ADVOCATE_CODE = LCD.ADVOCATE_ID) ADVOCATE_NAME,"); 
			
			bufInsSql.append("STAGE,SECTION,COURT_NAME,poa");
			bufInsSql.append(" from LEGAL_CASE_DTL LCD");
		
			
			bufInsSqlTempCount.append(" select COUNT(1)");
			bufInsSqlTempCount.append(" from LEGAL_CASE_DTL LCD");
			bufInsSqlTempCount.append(" inner join CR_LOAN_DTL CLD on LCD.LOAN_ID=CLD.LOAN_ID");
			bufInsSqlTempCount.append(" WHERE CLD.REC_STATUS = 'A' AND EXISTS ");
			bufInsSqlTempCount.append(" (SELECT 1 FROM LEGAL_NOTICE_DTL LND WHERE REC_STATUS='D' AND CLD.LOAN_ID = LND.LOAN_NO)"); 

			
			bufInsSql.append(" WHERE 5=5 ");
			
			
			if (!loanId.equals("")) {
				bufInsSql.append(" AND LCD.LOAN_ID = '" + loanId + "' ");
				bufInsSqlTempCount.append(" AND LCD.LOAN_ID = '" + loanId + "' ");
			}
			 if (!legalId.equals("")) {
				bufInsSql.append(" AND LCD.LEGAL_ID like '%" + legalId + "%' ");
				bufInsSqlTempCount.append(" AND LCD.LEGAL_ID like '%" + legalId + "%' ");
			}
			
			
			logger.info("search Query...." + bufInsSql);
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
				
				bufInsSql.append(" ORDER BY LCD.LOAN_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							

			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchReassignCaseData() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchReassignCaseData.....................................Dao Impl");
			logger.info("searchReassignCaseData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					ReassignCaseVo caseInitVo = new ReassignCaseVo();
					caseInitVo.setLegalId("<a href=reassignCaseAction.do?method=editReassignCase&legalId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					caseInitVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
					caseInitVo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
					caseInitVo.setCaseType(CommonFunction.checkNull(data.get(3)).toString());
					caseInitVo.setCaseFileDate(CommonFunction.checkNull(data.get(4)).toString());
					caseInitVo.setDateOfhearing(CommonFunction.checkNull(data.get(5)).toString());
					caseInitVo.setCaseNo(CommonFunction.checkNull(data.get(6)).toString());
					caseInitVo.setAdvocateName(CommonFunction.checkNull(data.get(7)).toString());
					caseInitVo.setStage(CommonFunction.checkNull(data.get(8)).toString());
					caseInitVo.setSection(CommonFunction.checkNull(data.get(9)).toString());
					caseInitVo.setCourtName(CommonFunction.checkNull(data.get(10)).toString());
					caseInitVo.setPoa(CommonFunction.checkNull(data.get(11)).toString());
					
					
					caseInitVo.setTotalRecordSize(count);
					detailList.add(caseInitVo);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return detailList;}
	}
    
    public boolean insertReassignCase(Object ob) {
		
		logger.info("## in insertReassignCase()");
		
		ReassignCaseVo vo = (ReassignCaseVo) ob;
		//String noticeLegalDeclineID = (String) vo.getNoticeLegalDeclineID();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		

		boolean status = false;
		String updateForwardFlag = CommonFunction.checkNull(vo.getUpdateForwardFlag());
		logger.info("## IN insertReassignCase(): updateForwardFlag :==>> "+updateForwardFlag); 
		
		//logger.info("## IN insertReassignCase(): noticeId :==>> "+noticeLegalDeclineID); 
		

		try {
			
			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In insertLegalDeclineDispatchNotice..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE LEGAL_NOTICE_DTL SET DECLINE_REMARKS=?,DECLINE_REASON=?,REC_STATUS=?,");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where NOTICE_ID=?");

			if (CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRemarks().toUpperCase().trim());
			
			if (CommonFunction.checkNull(vo.getLbxReasonId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxReasonId().toUpperCase().trim());
			
			if  (CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDecision().toUpperCase().trim());
			
             if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
		

//			if (CommonFunction.checkNull(noticeLegalDeclineID).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(noticeLegalDeclineID);

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			
			logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
    
    
    

    public ArrayList editReassignCase(Object ob) {
    	
    	
    	ArrayList<ReassignCaseVo> caseTypeList = new ArrayList<ReassignCaseVo>();
    	
    	ReassignCaseVo vo = (ReassignCaseVo)ob;
    	
    	logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanNo());

    	try {
    		
    		ArrayList searchlist = new ArrayList();
    		StringBuffer bufInsSql = new StringBuffer();
    		StringBuffer bufInsSqlTempCount = new StringBuffer();
    		bufInsSql.append(" SELECT LCD.LEGAL_ID,CLT.LOAN_NO,GCM.CUSTOMER_NAME,CBM.BRANCH_DESC,CPM.PRODUCT_DESC, CSM.SCHEME_DESC,DPD, TOTAL_OUTSTANDING,CHARGES,PRINCIPAL_OUTSTANDING, CASE_TYPE, INITIATION_MAKER_IMEMO, CRM.REASON_DESC,INITIATION_MAKER_REMARKS FROM LEGAL_CASE_DTL LCD ");
    		bufInsSql.append(" INNER JOIN CR_LOAN_DTL CLT ON CLT.LOAN_ID=LCD.LOAN_ID ");
    		bufInsSql.append(" INNER JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=LCD.CUSTOMER_ID ");
    		bufInsSql.append(" INNER JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=LCD.BRANCH_ID ");
    		bufInsSql.append(" INNER JOIN CR_SCHEME_M CSM ON CSM.SCHEME_ID=LCD.SCHEME_ID  ");
    		bufInsSql.append("INNER JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID=LCD.PRODUCT_ID  ");
    		bufInsSql.append("INNER JOIN COM_REASON_M CRM ON CRM.REASON_ID=LCD.INITIATION_MAKER_REASON  ");
    			
    		bufInsSql.append(" WHERE LEGAL_ID='"+StringEscapeUtils.escapeSql(vo.getLegalId())+"' ");		
    		//bufInsSql.append(" WHERE LCD.REC_STATUS = 'I'"); 
    		//bufInsSqlTempCount.append(" SELECT COUNT(1) FROM LEGAL_CASE_DTL LCD INNER JOIN CR_LOAN_DTL CLT ON CLT.LOAN_ID=LCD.LOAN_ID INNER JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID=LCD.CUSTOMER_ID INNER JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID=LCD.BRANCH_ID WHERE LCD.REC_STATUS = 'I' ");
    		
    		
    	
    		logger.info("search Query...." + bufInsSql);

    		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
    		logger.info("IN editCaseTypeData() search query1 ### "+ bufInsSql.toString());
    		logger.info("editCaseTypeData " + searchlist.size());
    		for (int i = 0; i < searchlist.size(); i++) {
    			logger.info("editCaseTypeData " + searchlist.get(i).toString());
    			ArrayList data = (ArrayList) searchlist.get(i);
    			if (data.size() > 0) {
    				ReassignCaseVo caseInitMakerVo = new ReassignCaseVo();
    				
    				caseInitMakerVo.setLegalId(CommonFunction.checkNull(data.get(0)).toString());
    				caseInitMakerVo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
    				caseInitMakerVo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
    				caseInitMakerVo.setBranch(CommonFunction.checkNull(data.get(3)).toString());
    				caseInitMakerVo.setProduct(CommonFunction.checkNull(data.get(4)).toString());
    				caseInitMakerVo.setScheme(CommonFunction.checkNull(data.get(5)).toString());
    				
    				
    				caseInitMakerVo.setDpd(CommonFunction.checkNull(data.get(6)).toString());
    				caseInitMakerVo.setTotalOutstanding(CommonFunction.checkNull(data.get(7)).toString());
    				caseInitMakerVo.setOtherCharges(CommonFunction.checkNull(data.get(8)).toString());
    				caseInitMakerVo.setPrincipalOutstanding(CommonFunction.checkNull(data.get(9)).toString());
    				caseInitMakerVo.setCaseTypeDesc(CommonFunction.checkNull(data.get(10)).toString());
    				caseInitMakerVo.setInitMakerImemo(CommonFunction.checkNull(data.get(11)).toString());
    				caseInitMakerVo.setReasonDesc(CommonFunction.checkNull(data.get(12)).toString());
    				caseInitMakerVo.setInitMakerRemarks(CommonFunction.checkNull(data.get(13)).toString());
    				
    				caseTypeList.add(caseInitMakerVo);

    			}

    		}

    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	return caseTypeList;
    }

    
    public boolean saveReassignCase(Object ob) {
    	ReassignCaseVo vo = (ReassignCaseVo) ob;
    	String legalId = (String) vo.getLegalId();
    	logger.info("getRecStatus():-" + vo.getRecStatus());
    	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    	ArrayList updatelist = new ArrayList();
    	boolean status = false;
    	String saveForwardFlag = CommonFunction.checkNull(vo.getSaveForwardFlag());
    	
    	logger.info("## IN saveReassignCase(): saveForwardFlag :==>> "+saveForwardFlag); 
    	

    	try {
    		
    		StringBuffer bufInsSql = new StringBuffer();
    		logger
    				.info("In saveReassignCase..........inside ejb server file............Dao Impl");
    		bufInsSql.append(" UPDATE LEGAL_CASE_DTL SET ADVOCATE_ID=?,REASSIGN_REMARKS=?, " );
    		bufInsSql.append("REASSIGN_BY=?,REASSIGN_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
    		bufInsSql.append(" where LEGAL_ID=?");

    		if (CommonFunction.checkNull(vo.getLbxUserId())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getLbxUserId()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getRemarks())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getRemarks()
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
    		
    		if (CommonFunction.checkNull(vo.getLegalId())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getLegalId());

    		insertPrepStmtObject.setSql(bufInsSql.toString());

    		updatelist.add(insertPrepStmtObject);
    		logger.info("In getListOfValues" + bufInsSql.toString());
    		
    		logger.info("## In saveReassignCase() : update query (Reassign Case) "+insertPrepStmtObject.printQuery());
    		
    		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return status;
    }

    public  String getNoticeId()
    {
    	return null;
    }

    public  String insertReasonMaster(Object paramObject)
    {
    	return null;
    }

    public  ArrayList searchReasonData(Object paramObject)
    {
    	return null;
    }

    public  boolean updateReasonData(Object paramObject)
    {
    	return false;
    }

	
		
}
