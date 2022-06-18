package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.cm.dao.HandSightingDAO;
import com.cm.vo.HandSightingVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
//import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class MSSQLHandSightingDAOImpl implements HandSightingDAO {

	private static final Logger logger = Logger.getLogger(MSSQLHandSightingDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");

	public boolean insertFileTracking(Object ob) {
		HandSightingVO fileTrackVo = (HandSightingVO) ob;
		boolean status = false;
		boolean statusUp = false;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder bufInsSql = new StringBuilder();
		ArrayList qryList = new ArrayList();
		ArrayList qryList1 = new ArrayList();
		try {

			bufInsSql.append("insert into CR_HAND_SIGHTING_DTL (LOAN_ID,LOAN_NO,DEAL_ID,RECEIVED_DATE,HAND_SIGHTING_STATUS,REMARKS,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,"); // LOAN_ID
			bufInsSql.append(" ?,"); // LOAN_NO
			bufInsSql.append(" ?,"); // DEAL_ID
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat + "'),"); // RECEIVED_DATE
			bufInsSql.append(" ?,"); // HAND_SIGHTING_STATUS
			bufInsSql.append(" ?,"); // REMARKS
			bufInsSql.append(" ?,"); // MAKER_ID
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");  // MAKER_DATE
			if (CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getLbxLoanNoHID())
						.trim());

			if (CommonFunction.checkNull(fileTrackVo.getLoanNo())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject
						.addString((fileTrackVo.getLoanNo()).trim());
			
			if (CommonFunction.checkNull(fileTrackVo.getLbxDealNo())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject
						.addString((fileTrackVo.getLbxDealNo()).trim());

			if (CommonFunction
					.checkNull(fileTrackVo.getFileTrackReceivedDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo
						.getFileTrackReceivedDate()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getFileTrackStatus())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo
						.getFileTrackStatus()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getRemarks())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getRemarks())
						.trim());

			if (CommonFunction.checkNull(fileTrackVo.getMakerId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerId())
						.trim());

			if (CommonFunction.checkNull(fileTrackVo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerDate())
						.trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			logger.info("IN  insertFileTracking ### "
					+ insertPrepStmtObject.printQuery());
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In insertFileTracking......................" + status);
			if(status){
				StringBuilder queryUpdate = new StringBuilder();
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
					queryUpdate.append("update cr_loan_dtl set HAND_SIGHTING_RECEIVED_DATE=");
					queryUpdate.append(dbo);
					queryUpdate.append("STR_TO_DATE(?,'"+dateFormat+ "'),HAND_SIGHTING_STATUS=?  where LOAN_ID=? ");

					if (CommonFunction
							.checkNull(fileTrackVo.getFileTrackReceivedDate())
							.equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo
								.getFileTrackReceivedDate()).trim());

					if (CommonFunction.checkNull(fileTrackVo.getFileTrackStatus())
							.equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo
								.getFileTrackStatus()).trim());

					if ((CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID()))
							.trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo.getLbxLoanNoHID())
								.trim());

					updatePrepStmtObject.setSql(queryUpdate.toString());
					qryList1.add(updatePrepStmtObject);
					logger.info("IN  updateFileTracking ### "
							+ updatePrepStmtObject.printQuery());
					statusUp = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
					logger.info("In updateFileTracking......................" + statusUp);
		    }
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

	public boolean updateFileTracking(Object ob) {
		HandSightingVO fileTrackVo = (HandSightingVO) ob;
		boolean status = false;
		boolean statusUp = false;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder queryUpdate = new StringBuilder();
		ArrayList qryList = new ArrayList();
		ArrayList qryList1 = new ArrayList();
		try {

			queryUpdate.append("update CR_HAND_SIGHTING_DTL set RECEIVED_DATE=");
			queryUpdate.append(dbo);
			queryUpdate.append("STR_TO_DATE(?,'"+dateFormat+ "'),HAND_SIGHTING_STATUS=?,REMARKS=?  where LOAN_ID=? ");

			if (CommonFunction.checkNull(fileTrackVo.getFileTrackReceivedDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getFileTrackReceivedDate()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getFileTrackStatus()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getRemarks()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getRemarks()).trim());

			if ((CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getLbxLoanNoHID()).trim());

			insertPrepStmtObject.setSql(queryUpdate.toString());
			qryList.add(insertPrepStmtObject);
			logger.info("IN  updateFileTracking ### "+ insertPrepStmtObject.printQuery());
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In updateFileTracking......................" + status);
			if(status){

				StringBuilder queryUpdateLoan = new StringBuilder();
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
				queryUpdateLoan.append("update cr_loan_dtl set HAND_SIGHTING_RECEIVED_DATE=");
				queryUpdateLoan.append(dbo);
				queryUpdateLoan.append("STR_TO_DATE(?,'"+dateFormat+ "'),HAND_SIGHTING_STATUS=?  where LOAN_ID=? ");

					if (CommonFunction
							.checkNull(fileTrackVo.getFileTrackReceivedDate())
							.equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo
								.getFileTrackReceivedDate()).trim());

					if (CommonFunction.checkNull(fileTrackVo.getFileTrackStatus())
							.equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo
								.getFileTrackStatus()).trim());

					if ((CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID()))
							.trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo.getLbxLoanNoHID())
								.trim());

					updatePrepStmtObject.setSql(queryUpdateLoan.toString());
					qryList1.add(updatePrepStmtObject);
					logger.info("IN  updateFileTracking ### "
							+ updatePrepStmtObject.printQuery());
					statusUp = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
					logger.info("In updateFileTracking......................" + statusUp);
		    
			}
		}

		catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

	public ArrayList getFileTrackingData(Object ob) {
		HandSightingVO fileTrackVo = (HandSightingVO) ob;
		ArrayList<HandSightingVO> assetList = new ArrayList<HandSightingVO>();
		logger.info("In getFileTrackingData...Dao Impl.");

		try {
			ArrayList searchlist = new ArrayList();
			ArrayList searchRemlist = new ArrayList();
			ArrayList data = new ArrayList();
			ArrayList remarks = new ArrayList();
			logger.info("In getFileTrackingData.................."
					+ fileTrackVo);
			StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlRem = new StringBuilder();

			bufInsSql.append("select loan_no,loan_id,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(received_date,'"+dateFormat+"'),HAND_SIGHTING_STATUS,remarks,DEAL_ID,s.USER_NAME  from CR_HAND_SIGHTING_DTL t join sec_user_m s on t.maker_id=s.USER_ID where t.loan_id='"
							+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID()).trim())+ "' AND HAND_SIGHTING_STATUS NOT IN('C') ");

			logger.info("In getFileTrackingData......... query..........."
					+ bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("getFileTrackingData size is...." + searchlist.size());
			
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("getFileTrackingData List "
						+ searchlist.get(i).toString());
				data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					HandSightingVO HandSightingVO = new HandSightingVO();

					HandSightingVO.setLoanNo((CommonFunction.checkNull(data
							.get(0)).trim()));
					HandSightingVO.setLbxLoanNoHID((CommonFunction
							.checkNull(data.get(1)).trim()));
					HandSightingVO.setFileTrackReceivedDate((CommonFunction
							.checkNull(data.get(2)).trim()));
					HandSightingVO.setFileTrackStatus((CommonFunction
							.checkNull(data.get(3)).trim()));
					HandSightingVO.setRemarks((CommonFunction.checkNull(data
							.get(4)).trim()));
					HandSightingVO.setLbxDealNo((CommonFunction.checkNull(data
							.get(5)).trim()));
					HandSightingVO.setFileTrackingUser((CommonFunction.checkNull(data
							.get(6)).trim()));
					assetList.add(HandSightingVO);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assetList;
	}

	public ArrayList searchfileTracking(Object ob) {

		HandSightingVO fileTrackVo = (HandSightingVO) ob;
		HandSightingVO HandSightingVO = new HandSightingVO();
		ArrayList<HandSightingVO> fileTrackList = new ArrayList<HandSightingVO>();
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		logger.info("In searchfileTracking...Dao Impl.");
		String loanNoSearch = (StringEscapeUtils.escapeSql(CommonFunction
				.checkNull(fileTrackVo.getLoanNoSearch())).trim());
		String trackingId = (StringEscapeUtils.escapeSql(CommonFunction
				.checkNull(fileTrackVo.getTrackingId())).trim());
		String lbxLoanNoHIDSearch = (StringEscapeUtils.escapeSql(CommonFunction
				.checkNull(fileTrackVo.getLbxLoanNoHIDSearch())).trim());

		try {
			ArrayList searchlist = new ArrayList();
			ArrayList data = new ArrayList();
			logger.info("In searchfileTracking.................."
					+ fileTrackVo);
			StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();

			bufInsSql
					.append("SELECT TRACKING_ID,LOAN_ID,LOAN_NO,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(RECEIVED_DATE,'"+dateFormat+"'),case HAND_SIGHTING_STATUS when 'R' THEN 'RECEIVED' when 'H' THEN 'HOLD' when 'C' THEN 'CLEAR' when 'P' THEN 'PENDING' end as HAND_SIGHTING_STATUS,REMARKS,DEAL_ID,s.USER_NAME FROM CR_HAND_SIGHTING_DTL t join sec_user_m s on t.maker_id=s.USER_ID where t.HAND_SIGHTING_STATUS NOT IN('C') ");
			bufInsSqlTempCount
					.append("SELECT COUNT(1) FROM CR_HAND_SIGHTING_DTL where HAND_SIGHTING_STATUS NOT IN('C') ");

           if (!CommonFunction.checkNull(
					fileTrackVo.getLbxLoanNoHIDSearch()).equalsIgnoreCase("")) {
				bufInsSql.append(" and loan_id='"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(
								fileTrackVo.getLbxLoanNoHIDSearch()).trim())
						+ "' ");
				bufInsSqlTempCount.append(" and loan_id='"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(
								fileTrackVo.getLbxLoanNoHIDSearch()).trim())
						+ "' ");

			} 

			count = Integer.parseInt(ConnectionDAO
					.singleReturn(bufInsSqlTempCount.toString()));

			if ((lbxLoanNoHIDSearch.trim() == null && trackingId.trim() == null)
					|| (lbxLoanNoHIDSearch.trim().equalsIgnoreCase("") && trackingId
							.trim().equalsIgnoreCase(""))
					|| fileTrackVo.getCurrentPageLink() > 1) {

				logger.info("current PAge Link no .................... "
						+ fileTrackVo.getCurrentPageLink());
				if (fileTrackVo.getCurrentPageLink() > 1) {
					startRecordIndex = (fileTrackVo.getCurrentPageLink() - 1)
							* no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "
							+ startRecordIndex);
					logger.info("endRecordIndex .................... "
							+ endRecordIndex);
				}

				//bufInsSql.append(" limit " + startRecordIndex + ","	+ endRecordIndex);
				bufInsSql.append(" ORDER BY LOAN_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				logger.info("Search searchBankBranchDaoDataList query for SQL SERVER : " + bufInsSql.toString());
			}

			logger.info("In searchfileTracking......... query..........."
					+ bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

			logger.info("bufInsSqlTempCount **************************** : "
					+ bufInsSqlTempCount.toString());

			logger.info("searchfileTracking size is...." + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchfileTracking List "
						+ searchlist.get(i).toString());
				data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					HandSightingVO = new HandSightingVO();

					HandSightingVO
							.setLoanNoSearch("<a href=handSightingDispatchAction.do?method=getFileTrackingData&LbxLoanNoHID="
									+ CommonFunction.checkNull(data.get(1))
											.toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(2))
											.toString() + "</a>");

					HandSightingVO.setTrackingId((CommonFunction.checkNull(data
							.get(0)).trim()));
					HandSightingVO.setLbxLoanNoHID((CommonFunction
							.checkNull(data.get(1)).trim()));
					HandSightingVO.setLoanNo((CommonFunction.checkNull(data
							.get(2)).trim()));
					HandSightingVO.setFileTrackReceivedDate((CommonFunction
							.checkNull(data.get(3)).trim()));
					HandSightingVO.setFileTrackStatus((CommonFunction
							.checkNull(data.get(4)).trim()));
					HandSightingVO.setRemarks((CommonFunction.checkNull(data
							.get(5)).trim()));
					HandSightingVO.setLbxDealNo((CommonFunction.checkNull(data
							.get(6)).trim()));
					HandSightingVO.setFileTrackingUser((CommonFunction.checkNull(data
							.get(7)).trim()));
					HandSightingVO.setTotalRecordSize(count);
					fileTrackList.add(HandSightingVO);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileTrackList;

	}

	public ArrayList getDealNumberValues(String lbxLoanNo) {
		ArrayList getDealId = new ArrayList();
		logger.info("In getDealNumberValues ...lbxLoanNo........" + lbxLoanNo);
		ArrayList mainlist = new ArrayList();
		ArrayList mainlist1 = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		StringBuilder queryCheck = new StringBuilder();
		HandSightingVO loanvo = null;
		try {
			queryCheck.append("select loan_id from CR_HAND_SIGHTING_DTL where loan_id='"+lbxLoanNo+"'");
			mainlist1 = ConnectionDAO.sqlSelect(queryCheck.toString());
			logger.info("queryCheck............................"+queryCheck.toString());
			logger.info("status............................"+mainlist1.size());
			
			if(mainlist1.size()==0){
			query.append("select LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+lbxLoanNo+"'");
			logger.info("In getTAFlagDetailsAuthor : query" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			query = null;
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new HandSightingVO();	
					loanvo.setLbxDealNo(CommonFunction.checkNull(subList.get(0)).trim());
					getDealId.add(loanvo);
				                         }
			                               }
			
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
			loanvo = null;			
			
		}
		return getDealId;
	}
	
	public ArrayList checkFileStatus(String lbxLoanNo) {
		logger.info("In checkFileStatus ...lbxLoanNo........" + lbxLoanNo);
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList getFileStatus = new ArrayList();
		StringBuilder queryCheck = new StringBuilder();
		HandSightingVO loanvo = null;
		try {
			queryCheck.append("select HAND_SIGHTING_STATUS from CR_HAND_SIGHTING_DTL where loan_id='"+lbxLoanNo+"'");
			mainlist = ConnectionDAO.sqlSelect(queryCheck.toString());
			logger.info("checkFileStatus............................"+queryCheck.toString());
			
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new HandSightingVO();	
					loanvo.setFileTrackStatus(CommonFunction.checkNull(subList.get(0)).trim());
					getFileStatus.add(loanvo);
				                         }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			queryCheck=null;	
		}
		return getFileStatus;
	}


	public ArrayList getFileTrackingDataForView(Object ob) {

		HandSightingVO fileTrackVo = (HandSightingVO) ob;
		HandSightingVO HandSightingVO = new HandSightingVO();
		ArrayList<HandSightingVO> fileTrackList = new ArrayList<HandSightingVO>();
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		logger.info("In getFileTrackingDataForView...Dao Impl.");
		String loanNoSearch = (StringEscapeUtils.escapeSql(CommonFunction
				.checkNull(fileTrackVo.getLoanNoSearch())).trim());
		String trackingId = (StringEscapeUtils.escapeSql(CommonFunction
				.checkNull(fileTrackVo.getTrackingId())).trim());
		String lbxLoanNoHIDSearch = (StringEscapeUtils.escapeSql(CommonFunction
				.checkNull(fileTrackVo.getLbxLoanNoHIDSearch())).trim());

		try {
			ArrayList searchlist = new ArrayList();
			ArrayList data = new ArrayList();
			logger.info("In getFileTrackingDataForView.................."
					+ fileTrackVo);
			StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();

			bufInsSql
					.append("SELECT TRACKING_ID,LOAN_ID,LOAN_NO,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(RECEIVED_DATE,'"+dateFormat+"'),HAND_SIGHTING_STATUS,REMARKS,DEAL_ID,s.USER_NAME FROM CR_HAND_SIGHTING_DTL t join sec_user_m s on t.maker_id=s.USER_ID where loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).trim())+ "' ");
			bufInsSqlTempCount
					.append("SELECT COUNT(1) FROM CR_HAND_SIGHTING_DTL where  loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).trim())+ "' ");

			count = Integer.parseInt(ConnectionDAO
					.singleReturn(bufInsSqlTempCount.toString()));

			if ((lbxLoanNoHIDSearch.trim() == null && trackingId.trim() == null)
					|| (lbxLoanNoHIDSearch.trim().equalsIgnoreCase("") && trackingId
							.trim().equalsIgnoreCase(""))
					|| fileTrackVo.getCurrentPageLink() > 1) {

				logger.info("current PAge Link no .................... "
						+ fileTrackVo.getCurrentPageLink());
				if (fileTrackVo.getCurrentPageLink() > 1) {
					startRecordIndex = (fileTrackVo.getCurrentPageLink() - 1)
							* no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "
							+ startRecordIndex);
					logger.info("endRecordIndex .................... "
							+ endRecordIndex);
				}

				bufInsSql.append(" limit " + startRecordIndex + ","
						+ endRecordIndex);

			}

			logger.info("In getFileTrackingDataForView......... query..........."
					+ bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

			logger.info("bufInsSqlTempCount **************************** : "
					+ bufInsSqlTempCount.toString());

			logger.info("getFileTrackingDataForView size is...." + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("getFileTrackingDataForView List "
						+ searchlist.get(i).toString());
				data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					HandSightingVO = new HandSightingVO();

					HandSightingVO
							.setLoanNoSearch("<a href=handSightingDispatchAction.do?method=searchFileTrackingDataForView&viewMode=viewMode&lbxLoanNoHIDSearch="
									+ CommonFunction.checkNull(data.get(1))
											.toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(2))
											.toString() + "</a>");

					HandSightingVO.setTrackingId((CommonFunction.checkNull(data
							.get(0)).trim()));
					HandSightingVO.setLbxLoanNoHID((CommonFunction
							.checkNull(data.get(1)).trim()));
					HandSightingVO.setLoanNo((CommonFunction.checkNull(data
							.get(2)).trim()));
					HandSightingVO.setFileTrackReceivedDate((CommonFunction
							.checkNull(data.get(3)).trim()));
					HandSightingVO.setFileTrackStatus((CommonFunction
							.checkNull(data.get(4)).trim()));
					if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("H")){
						HandSightingVO.setTrackStatus("HOLD");
					}else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("P")){
						HandSightingVO.setTrackStatus("PENDING");
					}else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("R")){
						HandSightingVO.setTrackStatus("RECEIVED");
					}else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("C")){
						HandSightingVO.setTrackStatus("CLEAR");
					}
					HandSightingVO.setRemarks((CommonFunction.checkNull(data
							.get(5)).trim()));
					HandSightingVO.setLbxDealNo((CommonFunction.checkNull(data
							.get(6)).trim()));
					HandSightingVO.setFileTrackingUser((CommonFunction.checkNull(data
							.get(7)).trim()));
					HandSightingVO.setTotalRecordSize(count);
					fileTrackList.add(HandSightingVO);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileTrackList;

	}
	
}
