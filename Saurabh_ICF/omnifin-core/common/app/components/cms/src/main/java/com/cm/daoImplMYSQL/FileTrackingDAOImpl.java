package com.cm.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.FileTrackingDAO;
import com.cm.vo.FileTrackingVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
//import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class FileTrackingDAOImpl implements FileTrackingDAO {

	private static final Logger logger = Logger.getLogger(FileTrackingDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateForDisbursal = resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	public boolean insertFileTracking(Object ob) {
		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		boolean status = false;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder bufInsSql = new StringBuilder();
		ArrayList qryList = new ArrayList();
		ArrayList qryList1 = new ArrayList();
		try {

			bufInsSql.append("insert into cr_file_track_dtl (LOAN_ID,DEAL_ID,RECEIVED_DATE,FILE_STATUS,REMARKS,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,"); // LOAN_ID
			bufInsSql.append(" ?,"); // DEAL_ID
			bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat + "'),"); // RECEIVED_DATE
			bufInsSql.append(" ?,"); // FILE_STATUS
			bufInsSql.append(" ?,"); // REMARKS
			bufInsSql.append(" ?,"); // MAKER_ID
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime+ "'),INTERVAL CURTIME() HOUR_SECOND))"); // MAKER_DATE

			if (CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getLbxLoanNoHID()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getLbxDealNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getLbxDealNo()).trim());

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

			if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			logger.info("IN  insertFileTracking cr_file_track_dtl ### "+ insertPrepStmtObject.printQuery());
			insertPrepStmtObject=null;
			bufInsSql=null;
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			qryList.clear();
			qryList=null;
			
			if(status){
				// Insert status into cr_file_track_status_dtl
		
				  StringBuilder query3=new StringBuilder();
				  query3.append("Select distinct max(TRACKING_ID) from cr_file_track_dtl for update");
				  String trackId=ConnectionDAO.singleReturn(query3.toString());
				  query3=null;
				insertPrepStmtObject= new PrepStmtObject();
				bufInsSql=new StringBuilder();
				bufInsSql.append("insert into cr_file_track_status_dtl (TRACKING_ID,FILE_TRACKING_STATUS,FILE_REMARKS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); // TRACKING_ID
				bufInsSql.append(" ?,"); // FILE_TRACKING_STATUS
				bufInsSql.append(" ?,"); //FILE_REMARKS
				bufInsSql.append(" ?,"); // MAKER_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
						+ "'),INTERVAL CURTIME() HOUR_SECOND))"); // MAKER_DATE

				if (CommonFunction.checkNull(trackId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(trackId).trim());

				if (CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getFileTrackStatus()).trim());
				
				if (CommonFunction.checkNull(fileTrackVo.getRemarks()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getRemarks()).trim());

				if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

				if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList1.add(insertPrepStmtObject);
				logger.info("IN  insert cr_file_track_status_dtl file status ### "+ insertPrepStmtObject.printQuery());
				insertPrepStmtObject=null;
				bufInsSql=null;
				//end insert status into cr_file_track_status_dtl
				
				StringBuilder queryUpdate = new StringBuilder();
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
					queryUpdate.append("update cr_loan_dtl set file_received_date=STR_TO_DATE(?,'"+dateFormat+ "'),file_status=?  where LOAN_ID=? ");

					if (CommonFunction.checkNull(fileTrackVo.getFileTrackReceivedDate()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo.getFileTrackReceivedDate()).trim());

					if (CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo.getFileTrackStatus()).trim());

					if ((CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo.getLbxLoanNoHID()).trim());

					updatePrepStmtObject.setSql(queryUpdate.toString());
					qryList1.add(updatePrepStmtObject);
					logger.info("IN  update cr_loan_dtl file_status and date ### " + updatePrepStmtObject.printQuery());
					updatePrepStmtObject=null;
					queryUpdate=null;
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
					qryList1.clear();
					qryList1=null;
					logger.info("IN  update cr_loan_dtl file_status and date ###.." + status);
					trackId=null;
		    }
			
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		finally
		{
			ob=null;
			fileTrackVo=null;
		}
		return status;
	}

	public boolean updateFileTracking(Object ob) {
		
		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		boolean status = false;
		boolean statusUp = false;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder queryUpdate = new StringBuilder();
		ArrayList qryList = new ArrayList();
		ArrayList qryList1 = new ArrayList();
		try {

			queryUpdate.append("update cr_file_track_dtl set RECEIVED_DATE=STR_TO_DATE(?,'"+dateFormat+ "'),FILE_STATUS=?,REMARKS=? ,INDEX_REF_NO=?,HOLD_REASON=?,MAKER_ID=?,MAKER_DATE=STR_TO_DATE(?,'"+dateFormat+ "') where LOAN_ID=? ");

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
			
			if (CommonFunction.checkNull(fileTrackVo.getIndexRefNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getIndexRefNo()).trim());
			
			if (CommonFunction.checkNull(fileTrackVo.getLbxReasonId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getLbxReasonId()).trim());


			if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());
			
			if ((CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getLbxLoanNoHID()).trim());

			insertPrepStmtObject.setSql(queryUpdate.toString());
			qryList.add(insertPrepStmtObject);
			logger.info("IN  updateFileTracking ### "+ insertPrepStmtObject.printQuery());
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			queryUpdate=null;
			insertPrepStmtObject=null;
			qryList.clear();
			qryList=null;
			
			if(status){
				
				// Insert status into cr_file_track_status_dtl
				/*PrepStmtObject deletePrepStmtObject= new PrepStmtObject();
				String deleteQuery="DELETE FROM cr_file_track_status_dtl WHERE TRACKING_ID=? ";
				if (CommonFunction.checkNull(fileTrackVo.getTrackingId())
						.equalsIgnoreCase(""))
					deletePrepStmtObject.addNull();
				else
					deletePrepStmtObject.addString(CommonFunction.checkNull(fileTrackVo.getTrackingId()).trim());
				
				deletePrepStmtObject.setSql(deleteQuery);
				qryList1.add(deletePrepStmtObject);
				logger.info("IN  delete cr_file_track_status_dtl file status ### "+ deletePrepStmtObject.printQuery());
				deletePrepStmtObject=null;
				deleteQuery=null;*/
				insertPrepStmtObject= new PrepStmtObject();
				StringBuilder bufInsSql=new StringBuilder();
				bufInsSql.append("insert into cr_file_track_status_dtl (TRACKING_ID,FILE_TRACKING_STATUS,FILE_REMARKS,MAKER_ID,MAKER_DATE,INDEX_REF_NO,HOLD_REASON)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); // TRACKING_ID
				bufInsSql.append(" ?,"); // FILE_TRACKING_STATUS
				bufInsSql.append(" ?,");//FILE_REMARKS
				bufInsSql.append(" ?,"); // MAKER_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
						+ "'),INTERVAL CURTIME() HOUR_SECOND),"); // MAKER_DATE
				bufInsSql.append(" ?,"); // INDEX_REF_NO
				bufInsSql.append(" ?)"); // HOLD_REASON

				if (CommonFunction.checkNull(fileTrackVo.getTrackingId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(fileTrackVo.getTrackingId()).trim());

				if (CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getFileTrackStatus()).trim());
				
				if (CommonFunction.checkNull(fileTrackVo.getRemarks()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getRemarks()).trim());
				
				if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

				if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());
				if (CommonFunction.checkNull(fileTrackVo.getIndexRefNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getIndexRefNo()).trim());
				
				if (CommonFunction.checkNull(fileTrackVo.getLbxReasonId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getLbxReasonId()).trim());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList1.add(insertPrepStmtObject);
				logger.info("IN  insert cr_file_track_status_dtl file status ### "+ insertPrepStmtObject.printQuery());
				insertPrepStmtObject=null;
				bufInsSql=null;
				//end insert status into cr_file_track_status_dtl
                
				StringBuilder queryUpdateLoan = new StringBuilder();
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
				queryUpdateLoan.append("update cr_loan_dtl set file_received_date=STR_TO_DATE(?,'"+dateFormat+ "'),file_status=?  where LOAN_ID=? ");

					if (CommonFunction.checkNull(fileTrackVo.getFileTrackReceivedDate()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo.getFileTrackReceivedDate()).trim());

					if (CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo.getFileTrackStatus()).trim());

					if ((CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((fileTrackVo.getLbxLoanNoHID()).trim());

					updatePrepStmtObject.setSql(queryUpdateLoan.toString());
					qryList1.add(updatePrepStmtObject);
					logger.info("IN  updateFileTracking ### "+ updatePrepStmtObject.printQuery());
					statusUp = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
					queryUpdateLoan=null;
					updatePrepStmtObject=null;
					qryList1.clear();
					qryList1=null;
					
					logger.info("In updateFileTracking......................" + statusUp);
		    
			}
		}

		catch (Exception e) {
			e.printStackTrace();

		}
		finally
		{
			ob=null;
			fileTrackVo=null;
		}
		return statusUp;
	}

	public ArrayList getFileTrackingData(Object ob) {
		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		ArrayList<FileTrackingVO> assetList = new ArrayList<FileTrackingVO>();
		logger.info("In getFileTrackingData...Dao Impl.");

		try {
			ArrayList searchlist = new ArrayList();
			/*ArrayList searchRemlist = new ArrayList();*/
			ArrayList data = new ArrayList();
			/*ArrayList remarks = new ArrayList();*/
			
			StringBuilder bufInsSql = new StringBuilder();
			/*StringBuilder bufInsSqlRem = new StringBuilder();*/

			bufInsSql.append("select d.loan_no,t.loan_id,DATE_FORMAT(received_date,'"+dateFormat+"'),t.file_status,remarks,DEAL_ID,s.USER_NAME,tracking_id,INDEX_REF_NO,hold_reason  from cr_file_track_dtl t join cr_loan_dtl d on d.loan_id=t.loan_id  join sec_user_m s on t.maker_id=s.USER_ID where t.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID()).trim())+ "' ");
			if(CommonFunction.checkNull(fileTrackVo.getTrackStatus()).equalsIgnoreCase("SO") && CommonFunction.checkNull(fileTrackVo.getStageStep()).equalsIgnoreCase("branch"))
			{
				bufInsSql.append(" AND t.FILE_STATUS = 'SO' ");
				
			}
			else if(CommonFunction.checkNull(fileTrackVo.getTrackStatus()).equalsIgnoreCase("SS,RS") && CommonFunction.checkNull(fileTrackVo.getStageStep()).equalsIgnoreCase("operation"))
			{
				bufInsSql.append(" AND t.FILE_STATUS NOT IN ('SS','RS') ");
				
			}
			else if(CommonFunction.checkNull(fileTrackVo.getTrackStatus()).equalsIgnoreCase("SS,RS") && CommonFunction.checkNull(fileTrackVo.getStageStep()).equalsIgnoreCase("store") )
			{
				bufInsSql.append(" AND t.FILE_STATUS IN ('SS','RS') ");
				
			}
			

			logger.info("In getFileTrackingData......... query..........."+ bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			int size= searchlist.size();
			for (int i = 0; i < size; i++) {
			
				data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					FileTrackingVO fileTrackingVO = new FileTrackingVO();

					fileTrackingVO.setLoanNo((CommonFunction.checkNull(data.get(0)).trim()));
					fileTrackingVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(1)).trim()));
					fileTrackingVO.setFileTrackReceivedDate((CommonFunction.checkNull(data.get(2)).trim()));
					fileTrackingVO.setFileTrackStatus((CommonFunction.checkNull(data.get(3)).trim()));
					fileTrackingVO.setRemarks((CommonFunction.checkNull(data.get(4)).trim()));
					fileTrackingVO.setLbxDealNo((CommonFunction.checkNull(data.get(5)).trim()));
					fileTrackingVO.setFileTrackingUser((CommonFunction.checkNull(data.get(6)).trim()));
					fileTrackingVO.setTrackingId((CommonFunction.checkNull(data.get(7)).trim()));
					fileTrackingVO.setIndexRefNo((CommonFunction.checkNull(data.get(8)).trim()));
					fileTrackingVO.setLbxReasonId((CommonFunction.checkNull(data.get(9)).trim()));
					assetList.add(fileTrackingVO);
					fileTrackingVO=null;
				}

			}
			searchlist.clear();
			searchlist=null;
			data.clear();
			data=null;
			bufInsSql=null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			fileTrackVo=null;
			ob=null;
		}
		return assetList;
	}

	public ArrayList searchfileTracking(Object ob) {

		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		FileTrackingVO fileTrackingVO = new FileTrackingVO();
		ArrayList<FileTrackingVO> fileTrackList = new ArrayList<FileTrackingVO>();
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		logger.info("In searchfileTracking...Dao Impl.");
		
		String trackingId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getTrackingId())).trim());
		String lbxLoanNoHIDSearch = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch())).trim());

		try {
			ArrayList searchlist = new ArrayList();
			ArrayList data = new ArrayList();
			logger.info("In searchfileTracking.................."+ fileTrackVo.getFileTrackStatus());
			StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();

			bufInsSql.append("SELECT TRACKING_ID,t.LOAN_ID,d.LOAN_NO,DATE_FORMAT(RECEIVED_DATE,'"+dateFormat+"'),case t.file_status when 'SO' THEN 'Send To OPS' when 'RO' THEN 'Received By OPS' when 'RU' THEN 'Received By User' when 'H' THEN 'Hold' when 'C' THEN 'Clear' when 'SS' THEN 'Send To Store' when 'RS' THEN 'Received By Store' end as file_status,REMARKS,DEAL_ID,s.USER_NAME,t.file_status FROM cr_file_track_dtl t join cr_loan_dtl d on d.loan_id=t.loan_id and d.LOAN_BRANCH IN (SELECT BRANCH_ID FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"+CommonFunction.checkNull(fileTrackVo.getUserId())+"') join sec_user_m s on t.maker_id=s.USER_ID where 1=1 " );
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_file_track_dtl t join cr_loan_dtl d on d.loan_id=t.loan_id and d.LOAN_BRANCH IN (SELECT BRANCH_ID FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"+CommonFunction.checkNull(fileTrackVo.getUserId())+"') where 1=1 " );
			if(CommonFunction.checkNull(fileTrackVo.getTrackStatus()).equalsIgnoreCase("SO") && CommonFunction.checkNull(fileTrackVo.getStageStep()).equalsIgnoreCase("branch"))
			{
				 if(CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).equalsIgnoreCase("") )
				  {
					bufInsSql.append(" AND t.FILE_STATUS = 'SO' ");
					bufInsSqlTempCount.append(" AND t.FILE_STATUS = 'SO' ");
				  }
				 else
				 {
					 bufInsSql.append(" AND t.FILE_STATUS = '"+CommonFunction.checkNull(fileTrackVo.getFileTrackStatus())+"' ");
					 bufInsSqlTempCount.append(" AND t.FILE_STATUS = '"+CommonFunction.checkNull(fileTrackVo.getFileTrackStatus())+"' "); 
				 }
				
			}
			else if(CommonFunction.checkNull(fileTrackVo.getTrackStatus()).equalsIgnoreCase("SS,RS") && CommonFunction.checkNull(fileTrackVo.getStageStep()).equalsIgnoreCase("operation"))
			{
				
				  if(CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).equalsIgnoreCase("") )
				  {
					  bufInsSql.append(" AND t.FILE_STATUS NOT IN ('SS','RS') ");
					  bufInsSqlTempCount.append(" AND t.FILE_STATUS NOT IN ('SS','RS') ");
						
				  }
				  else
				  {
					  bufInsSql.append(" AND t.FILE_STATUS = '"+CommonFunction.checkNull(fileTrackVo.getFileTrackStatus())+"' ");
					  bufInsSqlTempCount.append(" AND t.FILE_STATUS = '"+CommonFunction.checkNull(fileTrackVo.getFileTrackStatus())+"' ");
				  }

				
				
			}
			else if(CommonFunction.checkNull(fileTrackVo.getTrackStatus()).equalsIgnoreCase("SS,RS") && CommonFunction.checkNull(fileTrackVo.getStageStep()).equalsIgnoreCase("store") )
			{
				if(CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).equalsIgnoreCase("") )
				{
					bufInsSql.append(" AND t.FILE_STATUS IN ('SS') ");
					bufInsSqlTempCount.append(" AND t.FILE_STATUS IN ('SS') ");
				}
				else
				{
					bufInsSql.append(" AND t.FILE_STATUS = '"+CommonFunction.checkNull(fileTrackVo.getFileTrackStatus())+"' ");
					bufInsSqlTempCount.append(" AND t.FILE_STATUS = '"+CommonFunction.checkNull(fileTrackVo.getFileTrackStatus())+"' ");
				}
								
			}

           if (!CommonFunction.checkNull(
					fileTrackVo.getLbxLoanNoHIDSearch()).equalsIgnoreCase("")) {
				bufInsSql.append(" AND t.LOAN_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).trim())+ "' ");
				bufInsSqlTempCount.append("  AND t.LOAN_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).trim())+ "' ");
} 

			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

		
				if (fileTrackVo.getCurrentPageLink() > 1) {
					startRecordIndex = (fileTrackVo.getCurrentPageLink() - 1)* no;
					endRecordIndex = no;
				}

				bufInsSql.append(" limit " + startRecordIndex + ","+ endRecordIndex);

			

			logger.info("In searchfileTracking......... query..........."+ bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());

			int size=searchlist.size();
			for (int i = 0; i < size; i++) {
			
				data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					fileTrackingVO = new FileTrackingVO();

					fileTrackingVO.setLoanNoSearch("<a href=fileTrackingDispatchAction.do?method=getFileTrackingData&LbxLoanNoHID="+ CommonFunction.checkNull(data.get(1)).toString()+ "&stageStep="+CommonFunction.checkNull(fileTrackVo.getStageStep())+"&viewMode="+ CommonFunction.checkNull(data.get(8)).toString()+"&trackingId="+ CommonFunction.checkNull(data.get(0)).toString()+">"
									+ CommonFunction.checkNull(data.get(2))
											.toString() + "</a>");

					fileTrackingVO.setTrackingId((CommonFunction.checkNull(data.get(0)).trim()));
					fileTrackingVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(1)).trim()));
					fileTrackingVO.setLoanNo((CommonFunction.checkNull(data.get(2)).trim()));
					fileTrackingVO.setFileTrackReceivedDate((CommonFunction.checkNull(data.get(3)).trim()));
					fileTrackingVO.setFileTrackStatus((CommonFunction.checkNull(data.get(4)).trim()));
					fileTrackingVO.setRemarks((CommonFunction.checkNull(data.get(5)).trim()));
					fileTrackingVO.setLbxDealNo((CommonFunction.checkNull(data.get(6)).trim()));
					fileTrackingVO.setFileTrackingUser((CommonFunction.checkNull(data.get(7)).trim()));
					fileTrackingVO.setTotalRecordSize(count);
					fileTrackList.add(fileTrackingVO);
				}

			}
			searchlist.clear();
			searchlist=null;
			data.clear();
			data=null;
			bufInsSql=null;
			bufInsSqlTempCount=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			fileTrackVo=null;
			ob=null;
			fileTrackingVO=null;
			trackingId=null;
			lbxLoanNoHIDSearch=null;
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
		FileTrackingVO loanvo = null;
		try {
			queryCheck.append("select loan_id from cr_file_track_dtl where loan_id='"+lbxLoanNo+"'");
			mainlist1 = ConnectionDAO.sqlSelect(queryCheck.toString());
			logger.info("queryCheck............................"+queryCheck.toString());
			
			
			if(mainlist1.size()==0){
			query.append("select LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+lbxLoanNo+"'");
			logger.info("In getTAFlagDetailsAuthor : query" + query.toString());
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			query = null;
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new FileTrackingVO();	
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
			mainlist1.clear();
			mainlist1=null;
			subList = null;
			loanvo = null;	
			lbxLoanNo=null;
			queryCheck=null;
			
		}
		return getDealId;
	}
	
	public ArrayList checkFileStatus(String lbxLoanNo) {
		logger.info("In checkFileStatus ...lbxLoanNo........" + lbxLoanNo);
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList getFileStatus = new ArrayList();
		StringBuilder queryCheck = new StringBuilder();
		FileTrackingVO loanvo = null;
		try {
			queryCheck.append("select file_status from cr_file_track_dtl where loan_id='"+lbxLoanNo+"'");
			mainlist = ConnectionDAO.sqlSelect(queryCheck.toString());
			logger.info("checkFileStatus............................"+queryCheck.toString());
			
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new FileTrackingVO();	
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
			lbxLoanNo=null;
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			loanvo=null;
		}
		return getFileStatus;
	}


	public ArrayList getFileTrackingDataForView(Object ob) {

		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		FileTrackingVO fileTrackingVO = new FileTrackingVO();
		ArrayList<FileTrackingVO> fileTrackList = new ArrayList<FileTrackingVO>();
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		logger.info("In getFileTrackingDataForView...Dao Impl.");
	

		try {
			ArrayList searchlist = new ArrayList();
			ArrayList data = new ArrayList();
			
			StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();

			bufInsSql.append("SELECT TRACKING_ID,t.LOAN_ID,d.LOAN_NO,DATE_FORMAT(RECEIVED_DATE,'"+dateFormat+"'),t.file_status,REMARKS,DEAL_ID,s.USER_NAME FROM cr_file_track_dtl t join cr_loan_dtl d on d.loan_id=t.loan_id join sec_user_m s on t.maker_id=s.USER_ID where 1=1  ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_file_track_dtl t join cr_loan_dtl d on d.loan_id=t.loan_id where  1=1 ");
			if(!CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).equalsIgnoreCase(""))
			{
				bufInsSql.append(" AND t.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).trim())+ "' " );
				bufInsSqlTempCount.append(" AND t.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).trim())+ "' " );
			}
			if(!CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).equalsIgnoreCase("") )
			  {
				bufInsSql.append(" AND t.FILE_STATUS = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).trim())+ "' ");
				bufInsSqlTempCount.append(" AND t.FILE_STATUS = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getFileTrackStatus()).trim())+ "' ");
			  }
			

			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

		 
				if (fileTrackVo.getCurrentPageLink() > 1) {
					startRecordIndex = (fileTrackVo.getCurrentPageLink() - 1)* no;
					endRecordIndex = no;
				
				}
				bufInsSql.append(" limit " + startRecordIndex + ","+ endRecordIndex);

			logger.info("In getFileTrackingDataForView......... query..........."+ bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());

			int size=searchlist.size();
			for (int i = 0; i < size; i++) {
			
				data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					fileTrackingVO = new FileTrackingVO();

					fileTrackingVO.setLoanNoSearch("<a href=fileTrackingDispatchAction.do?method=searchFileTrackingDataForView&lbxLoanNoHIDSearch="
									+ CommonFunction.checkNull(data.get(1))
											.toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(2))
											.toString() + "</a>");

					fileTrackingVO.setTrackingId((CommonFunction.checkNull(data.get(0)).trim()));
					fileTrackingVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(1)).trim()));
					fileTrackingVO.setLoanNo((CommonFunction.checkNull(data.get(2)).trim()));
					fileTrackingVO.setFileTrackReceivedDate((CommonFunction.checkNull(data.get(3)).trim()));
					fileTrackingVO.setFileTrackStatus((CommonFunction.checkNull(data.get(4)).trim()));
					if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("SO")){
						fileTrackingVO.setTrackStatus("Send To OPS");
					}else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("RO")){
						fileTrackingVO.setTrackStatus("Received By OPS");
					}else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("RU")){
						fileTrackingVO.setTrackStatus("Received By User");
					}else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("H")){
						fileTrackingVO.setTrackStatus("Hold");
					}else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("C")){
						fileTrackingVO.setTrackStatus("Clear");
					}else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("SS")){
						fileTrackingVO.setTrackStatus("Send To Store");
					}else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("RS")){
						fileTrackingVO.setTrackStatus("Received By Store");
					}
					fileTrackingVO.setRemarks((CommonFunction.checkNull(data.get(5)).trim()));
					fileTrackingVO.setLbxDealNo((CommonFunction.checkNull(data.get(6)).trim()));
					fileTrackingVO.setFileTrackingUser((CommonFunction.checkNull(data.get(7)).trim()));
					fileTrackingVO.setTotalRecordSize(count);
					fileTrackList.add(fileTrackingVO);
				}

			}
			searchlist.clear();
			searchlist=null;
			data.clear();
			data=null;
			bufInsSql=null;
			bufInsSqlTempCount=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			fileTrackVo=null;
			ob=null;
			fileTrackingVO=null;
		}
		return fileTrackList;

	}

	@Override
	public boolean getFileTrackingSequenceStatus(String trackingId,	String fileTrackStatus) {
		
		
	        boolean status=false;	
		    String statusQuery="SELECT IFNULL(FILE_TRACKING_STATUS,'') FROM CR_FILE_TRACK_STATUS_DTL WHERE TRACKING_ID='"+CommonFunction.checkNull(trackingId)+"' AND  FILE_TRACKING_STATUS<>'SO' ";
	        logger.info("check Status Query CR_FILE_TRACK_STATUS_DTL : "+statusQuery);
	        try
	        {
	        	 ArrayList statusList=ConnectionDAO.sqlSelect(statusQuery);
	        	 ArrayList data=null;
	        	 if(statusList.size()==0 && CommonFunction.checkNull(fileTrackStatus).equalsIgnoreCase("RO"))
	        	 {
	        		 status=true;
	        	 }else
	        	 {int size=statusList.size();
	        		 for(int j=0;j<size;j++)
	        		 {
	        			 data = (ArrayList) statusList.get(j);
	        			 if (data.size() > 0) {
	        				 
	        				 String stat=CommonFunction.checkNull(data.get(0));
	        				 if(CommonFunction.checkNull(stat).equalsIgnoreCase("RO"))
	        				 {
	        					 if(CommonFunction.checkNull(fileTrackStatus).equalsIgnoreCase("RU"))
		        				 {
	        						 status=true;
	        						 break;
		        				 }
	        					
	        				 }
	        				 else if(CommonFunction.checkNull(stat).equalsIgnoreCase("RU"))
	        				 {
	        					 if(CommonFunction.checkNull(fileTrackStatus).equalsIgnoreCase("H")||CommonFunction.checkNull(fileTrackStatus).equalsIgnoreCase("C"))
		        				 {
	        						 status=true;
	        						 break;
		        				 }
	        					 
	        				 }
	        				 else if(CommonFunction.checkNull(stat).equalsIgnoreCase("C"))
	        				 {
	        					 if(CommonFunction.checkNull(fileTrackStatus).equalsIgnoreCase("SS"))
		        				 {
	        						 status=true;
	        						 break;
		        				 }
	        					 
	        				 }
	        				 
	        				 stat=null;
	        				 }
	        		 }
	        	 }
	        	 statusList.clear();
	        	 statusList=null;
	 	         data.clear();
	 	         data=null;
	 	     
	        }
	        
	        catch(Exception e)
	        {
	        	 logger.info("in catch block : "+e);
	        }
	        finally
	        {
	        	statusQuery=null;
	        	trackingId=null;
	        	fileTrackStatus=null;
	        }
	       
		return status;
	}

	//Manish Space Starts
	public ArrayList searchBulkfileTracking(Object ob) 
	{		
		logger.info("In searchBulkfileTracking...MSSQLDao Impl.");
		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		ArrayList<FileTrackingVO> fileTrackList = new ArrayList<FileTrackingVO>();
		StringBuilder bufInsSql = new StringBuilder();
		ArrayList searchlist = null;
		ArrayList data =null;
		try 
		{		
			bufInsSql.append(" SELECT CLD.LOAN_ID,CLD.LOAN_NO,G.CUSTOMER_NAME,CBM.BRANCH_DESC ");
			bufInsSql.append(" FROM cr_loan_dtl CLD ");
			bufInsSql.append(" JOIN GCD_CUSTOMER_M G ON CLD.LOAN_CUSTOMER_ID=G.CUSTOMER_ID AND IFNULL(CLD.FILE_STATUS,'')='' AND CLD.REC_STATUS not in ('X','L') ");
			bufInsSql.append(" JOIN com_branch_m CBM ON CBM.BRANCH_ID=CLD.LOAN_BRANCH  ");
			if(CommonFunction.checkNull(fileTrackVo.getAllBranches()).equalsIgnoreCase("on"))
				bufInsSql.append(" JOIN SEC_USER_BRANCH_DTL ub ON ub.BRANCH_ID=CLD.LOAN_BRANCH and USER_ID='"+CommonFunction.checkNull(fileTrackVo.getUserId())+"' ");
			bufInsSql.append(" WHERE  'a'='a' ");
			
			if(!CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHID()).equalsIgnoreCase(""))
				bufInsSql.append(" AND CLD.LOAN_ID='"+fileTrackVo.getLbxLoanNoHID()+"'");
			if(!CommonFunction.checkNull(fileTrackVo.getLbxBranchId()).equalsIgnoreCase(""))
				bufInsSql.append(" AND CLD.LOAN_BRANCH='"+fileTrackVo.getLbxBranchId()+"'");
			if(!CommonFunction.checkNull(fileTrackVo.getStartDate()).equalsIgnoreCase(""))
				bufInsSql.append(" AND date(LOAN_APPROVAL_DATE) >='"+CommonFunction.changeFormat(CommonFunction.checkNull(fileTrackVo.getStartDate()))+"'");
			if(!CommonFunction.checkNull(fileTrackVo.getEndDate()).equalsIgnoreCase(""))
				bufInsSql.append(" AND date(LOAN_APPROVAL_DATE) <='"+CommonFunction.changeFormat(CommonFunction.checkNull(fileTrackVo.getEndDate()))+"'");
			
			String str="SELECT PARAMETER_VALUE FROM parameter_mst  WHERE PARAMETER_KEY='SEND_OPS_SIZE'";
			String val=ConnectionDAO.singleReturn(str);
			if(CommonFunction.checkNull(val).trim().equalsIgnoreCase(""))
				val="100";				
			bufInsSql.append(" limit 0,"+CommonFunction.checkNull(val).trim()+"");
			logger.info("In searchBulkfileTracking select query..........."+ bufInsSql.toString());
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());			
			int size=searchlist.size();
			for (int i = 0; i < size; i++) 
			{
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0)
				{
					fileTrackVo = new FileTrackingVO();
					fileTrackVo.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
					fileTrackVo.setLoanNo((CommonFunction.checkNull(data.get(1)).trim()));
					fileTrackVo.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
					fileTrackVo.setBranch((CommonFunction.checkNull(data.get(3)).trim()));
					fileTrackList.add(fileTrackVo);
				}				
			}						
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		finally
		{
			fileTrackVo = null;
			bufInsSql=null;
			searchlist.clear();
			searchlist = null;
		}
		return fileTrackList;
	}
	
	public boolean insertBulkFileTracking(Object ob,String[] loanIds,String[] receivedDates,String[] remarks) {
		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		boolean status = false;
		boolean status1 = false;
		boolean tempStatus = false;
		String fileStatus="SO";
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder bufInsSql = new StringBuilder();
		
		
		try {
			
			logger.info("## In side the insertBulkFileTracking() : ");
			
			for(int i=0; i<loanIds.length; i++)
			{
				ArrayList qryList = new ArrayList();
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql = new StringBuilder();

			bufInsSql.append("insert into cr_file_track_dtl (LOAN_ID,RECEIVED_DATE,FILE_STATUS,REMARKS,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,"); // LOAN_ID
			bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat + "'),"); // RECEIVED_DATE
			bufInsSql.append(" ?,"); // FILE_STATUS
			bufInsSql.append(" ?,"); // REMARKS
			bufInsSql.append(" ?,"); // MAKER_ID
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime+ "'),INTERVAL CURTIME() HOUR_SECOND))"); // MAKER_DATE

			if (CommonFunction.checkNull(loanIds[i]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((loanIds[i]).trim());
			
			if (CommonFunction.checkNull(receivedDates[i]).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((receivedDates[i]).trim());

			
				insertPrepStmtObject.addString((fileStatus).trim());

			if (CommonFunction.checkNull((remarks[i]).trim()).equalsIgnoreCase("$"))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((remarks[i]).trim());

			if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("## In insertBulkFileTracking : insert query for cr_file_track_dtl : "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			
			insertPrepStmtObject=null;
			bufInsSql=null;
			
			
			
			status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("## In insertBulkFileTracking : status of cr_file_track_dtl : ==>> "+status1);
			
			qryList.clear();
			qryList=null;
			
			if(status1){
				// Insert status into cr_file_track_status_dtl
				
				qryList = new ArrayList();				
		//		for(int k=0; k<loanIds.length; k++)
		//		{
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql = new StringBuilder();
					
					StringBuilder querytrack=new StringBuilder();
					querytrack.append("Select distinct max(TRACKING_ID) from cr_file_track_dtl ");
					String trackId=ConnectionDAO.singleReturn(querytrack.toString());
					querytrack=null;
					
				bufInsSql.append("insert into cr_file_track_status_dtl (TRACKING_ID,FILE_TRACKING_STATUS,FILE_REMARKS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); // TRACKING_ID
				bufInsSql.append(" ?,"); // FILE_STATUS
				bufInsSql.append(" ?,"); // REMARKS
				bufInsSql.append(" ?,"); // MAKER_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime+ "'),INTERVAL CURTIME() HOUR_SECOND))"); // MAKER_DATE

				if (CommonFunction.checkNull(trackId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(trackId).trim());
				
				
					insertPrepStmtObject.addString((fileStatus).trim());

				if (CommonFunction.checkNull((remarks[i]).trim()).equalsIgnoreCase("$"))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((remarks[i]).trim());

				if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

				if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("## In insertBulkFileTracking : insert in cr_file_track_status_dtl  : ==>>  "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				insertPrepStmtObject=null;
				bufInsSql=null;
				
		//		}
				
				tempStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("## In insertBulkFileTracking : temp status ==>> "+tempStatus);
				
				qryList.clear();
				qryList=null;
				
				if(tempStatus)
				{
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql = new StringBuilder();
					qryList = new ArrayList();
					
								
				//	for(int j=0; j<loanIds.length; j++)
				//	{
				//		insertPrepStmtObject = new PrepStmtObject();
				//		bufInsSql = new StringBuilder();
			
					
					bufInsSql.append("update cr_loan_dtl set file_received_date=");
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+ "'),file_status=?  where LOAN_ID=? ");

						if (CommonFunction.checkNull(receivedDates[i]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((receivedDates[i]).trim());

						
							insertPrepStmtObject.addString((fileStatus).trim());

						if ((CommonFunction.checkNull(loanIds[i])).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((loanIds[i]).trim());

						insertPrepStmtObject.setSql(bufInsSql.toString());
						qryList.add(insertPrepStmtObject);
						logger.info("## In insertBulkFileTracking : Update for cr_loan_dtl  : ==>> " + insertPrepStmtObject.printQuery());
						insertPrepStmtObject=null;
						bufInsSql=null;
						
				//	}
					
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					qryList.clear();
					qryList=null;
					logger.info("## In insertBulkFileTracking : status  : ==>> "+ status);
					
					
				}//inner temp status
		
				  
		    }//outer status
		}//final loop
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		finally
		{
			fileTrackVo = null;
			insertPrepStmtObject= null;
			bufInsSql= null;
			
			
		}
		return status;
	}
//sachin balyan 

	
	public ArrayList searchfileTrackingOps(Object ob) 
	{
		logger.info("In searchfileTracking...Dao Impl.");
		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		FileTrackingVO fileTrackingVO = null;
		ArrayList<FileTrackingVO> fileTrackList = new ArrayList<FileTrackingVO>();		
		ArrayList searchlist = new ArrayList();
		ArrayList data = new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		try 
		{				
					bufInsSql.append(" select b.loan_id,b.loan_no,g.customer_name,s.branch_desc,b.file_status "  );
					bufInsSql.append(" from cr_loan_dtl b "  );
					bufInsSql.append(" join gcd_customer_m g on b.loan_customer_id=g.customer_id "  );
					bufInsSql.append(" join com_branch_m s on s.branch_id=b.loan_branch " );
					bufInsSql.append(" and  b.file_status='SO' and b.REC_STATUS<>'X' " );
		    if(CommonFunction.checkNull(fileTrackVo.getAllBranches()).equalsIgnoreCase("on"))
					bufInsSql.append(" JOIN SEC_USER_BRANCH_DTL ub ON ub.BRANCH_ID=b.LOAN_BRANCH and USER_ID='"+CommonFunction.checkNull(fileTrackVo.getUserId())+"' ");
		     
		  	    
		    if (!CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).equalsIgnoreCase("")) 
		    		bufInsSql.append(" AND b.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).trim())+ "' ");
		    if (!CommonFunction.checkNull(fileTrackVo.getLbxBranchId()).equalsIgnoreCase("")) 
		    		bufInsSql.append(" AND b.LOAN_BRANCH='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxBranchId()).trim())+"' ");
		    if(!CommonFunction.checkNull(fileTrackVo.getStartDate()).equalsIgnoreCase(""))
				bufInsSql.append(" AND date(LOAN_APPROVAL_DATE) >='"+CommonFunction.changeFormat(CommonFunction.checkNull(fileTrackVo.getStartDate()))+"'");
			if(!CommonFunction.checkNull(fileTrackVo.getEndDate()).equalsIgnoreCase(""))
				bufInsSql.append(" AND date(LOAN_APPROVAL_DATE) <='"+CommonFunction.changeFormat(CommonFunction.checkNull(fileTrackVo.getEndDate()))+"'");
		    	String str="SELECT PARAMETER_VALUE FROM parameter_mst  WHERE PARAMETER_KEY='SEND_OPS_SIZE'";
		    	String val=ConnectionDAO.singleReturn(str);
		    	str=null;
			if(CommonFunction.checkNull(val).trim().equalsIgnoreCase(""))
				val="100";			
			
            	bufInsSql.append(" limit " + 0 + ","+ CommonFunction.checkNull(val).trim());
            	val=null;
					logger.info("In searchfileTracking......... query..........."+ bufInsSql.toString());
					searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size=searchlist.size();
			for (int i = 0; i < size; i++) 
			{				
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) 
				{
					fileTrackingVO = new FileTrackingVO();
					fileTrackingVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
					fileTrackingVO.setLoanNo((CommonFunction.checkNull(data.get(1)).trim()));
					fileTrackingVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
					fileTrackingVO.setBranch((CommonFunction.checkNull(data.get(3)).trim()));
					fileTrackList.add(fileTrackingVO);
				}
			}				
		} catch (Exception e) 
					{e.printStackTrace();}
		finally
		{
					fileTrackVo =null;
					fileTrackingVO = null;
					searchlist.clear();
					searchlist=null;
					data.clear();
					data=null;
					bufInsSql=null;
					ob=null;
		}
		return fileTrackList;

		}

	public boolean insertfileTrackingOps(Object ob,String[] loanIds,String[] receivedDates,String[] remarks) {
		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		boolean status = false;
		boolean statusUp = false;
		PrepStmtObject insertPrepStmtObject =null;
		StringBuilder queryUpdate =null;
		ArrayList qryList = new ArrayList();
		ArrayList qryList1 = new ArrayList();
		StringBuilder bufInsSql=null;
		ArrayList subTrackingId=null;
		try {
			logger.info("loanIds.length"+loanIds.length);
			for(int i=0; i<loanIds.length; i++)
			{
					queryUpdate= new StringBuilder();
					insertPrepStmtObject= new PrepStmtObject();
					queryUpdate.append("update cr_file_track_dtl set RECEIVED_DATE=STR_TO_DATE(?,'"+dateFormat+ "'),FILE_STATUS='RO',REMARKS=?,MAKER_ID=?,MAKER_DATE=STR_TO_DATE(?,'"+dateFormat+ "')  where LOAN_ID=? ");

			if (CommonFunction.checkNull(receivedDates[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString((receivedDates[i]).trim());


			if (CommonFunction.checkNull((remarks[i]).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString((remarks[i]).trim());
			
			if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());

			if ((CommonFunction.checkNull(loanIds[i])).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString((loanIds[i]).trim());

					insertPrepStmtObject.setSql(queryUpdate.toString());
					qryList.add(insertPrepStmtObject);
					logger.info("IN  updateFileTracking ### "+ insertPrepStmtObject.printQuery());
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			if(status){
				
					StringBuilder trackingQuery= new StringBuilder();
					trackingQuery.append("SELECT TRACKING_ID FROM CR_FILE_TRACK_DTL WHERE LOAN_ID="+CommonFunction.checkNull(loanIds[i])+"");
					ArrayList trackingIdList=ConnectionDAO.sqlSelect(trackingQuery.toString());
					trackingQuery=null;
					int size=trackingIdList.size();
					for(int j=0;j<size; j++)
				{
					
					 subTrackingId=(ArrayList) trackingIdList.get(j);
					if(subTrackingId.size()>0)
					{
						insertPrepStmtObject= new PrepStmtObject();
					bufInsSql=new StringBuilder();
					bufInsSql.append("insert into cr_file_track_status_dtl (TRACKING_ID,FILE_TRACKING_STATUS,FILE_REMARKS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,"); // TRACKING_ID
					bufInsSql.append(" 'RO',"); // FILE_TRACKING_STATUS
					bufInsSql.append(" ?,");//FILE_REMARKS
					bufInsSql.append(" ?,"); // MAKER_ID
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
						+ "'),INTERVAL CURTIME() HOUR_SECOND))"); // MAKER_DATE
				

				if (CommonFunction.checkNull(subTrackingId.get(0)).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(subTrackingId.get(0)).trim());
				
				if (CommonFunction.checkNull((remarks[i]).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((remarks[i]).trim());
				
				if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

				if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());
				
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList1.add(insertPrepStmtObject);
					logger.info("IN  insert cr_file_track_status_dtl file status ### "+ insertPrepStmtObject.printQuery());
					statusUp = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
				
					}
				
				}
					StringBuilder queryUpdateLoan = new StringBuilder();
					PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
					qryList1=new ArrayList();
					queryUpdateLoan.append("update cr_loan_dtl set file_received_date=STR_TO_DATE(?,'"+dateFormat+ "'),file_status='RO'  where LOAN_ID=? ");

					if (CommonFunction.checkNull(receivedDates[i]).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((receivedDates[i]).trim());
					if ((CommonFunction.checkNull(loanIds[i])).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((loanIds[i]).trim());

					updatePrepStmtObject.setSql(queryUpdateLoan.toString());
					queryUpdateLoan=null;
					qryList1.add(updatePrepStmtObject);
					logger.info("IN  updateFileTracking ### "+ updatePrepStmtObject.printQuery());
					statusUp = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
					updatePrepStmtObject=null;
				    }
				   }
		      
		         }

		            catch (Exception e) {
			         e.printStackTrace();

		          }
		         finally
		      {
			     ob=null;
			     fileTrackVo=null;
			     queryUpdate=null;
			     insertPrepStmtObject=null;
			     qryList.clear();
			     qryList=null;
			     bufInsSql=null;
	             subTrackingId=null;
				 qryList1=null;
		      }
		         return statusUp;
	    }
	
	
	//sachin balyan work send to store
	

	public ArrayList searchfileTrackingtostore(Object ob) 
	{
		logger.info("In searchfileTracking...Dao Impl.");
		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		FileTrackingVO fileTrackingVO = null;
		ArrayList<FileTrackingVO> fileTrackList = new ArrayList<FileTrackingVO>();		
		ArrayList searchlist = new ArrayList();
		ArrayList data = new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		try 
		{				
					bufInsSql.append(" select b.loan_id,b.loan_no,g.customer_name,s.branch_desc,b.file_status "  );
					bufInsSql.append(" from cr_loan_dtl b "  );
					bufInsSql.append(" join gcd_customer_m g on b.loan_customer_id=g.customer_id "  );
					bufInsSql.append(" join com_branch_m s on s.branch_id=b.loan_branch " );
					bufInsSql.append(" and  b.file_status='C' and b.REC_STATUS<>'X' " );
		    if(CommonFunction.checkNull(fileTrackVo.getAllBranches()).equalsIgnoreCase("on"))
					bufInsSql.append(" JOIN SEC_USER_BRANCH_DTL ub ON ub.BRANCH_ID=b.LOAN_BRANCH and USER_ID='"+CommonFunction.checkNull(fileTrackVo.getUserId())+"' ");
		     
		  	    
		    if (!CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).equalsIgnoreCase("")) 
		    		bufInsSql.append(" AND b.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxLoanNoHIDSearch()).trim())+ "' ");
		    if (!CommonFunction.checkNull(fileTrackVo.getLbxBranchId()).equalsIgnoreCase("")) 
		    		bufInsSql.append(" AND b.LOAN_BRANCH='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(fileTrackVo.getLbxBranchId()).trim())+"' ");
		    if(!CommonFunction.checkNull(fileTrackVo.getStartDate()).equalsIgnoreCase(""))
				bufInsSql.append(" AND date(LOAN_APPROVAL_DATE) >='"+CommonFunction.changeFormat(CommonFunction.checkNull(fileTrackVo.getStartDate()))+"'");
			if(!CommonFunction.checkNull(fileTrackVo.getEndDate()).equalsIgnoreCase(""))
				bufInsSql.append(" AND date(LOAN_APPROVAL_DATE) <='"+CommonFunction.changeFormat(CommonFunction.checkNull(fileTrackVo.getEndDate()))+"'");
		    	String str="SELECT PARAMETER_VALUE FROM parameter_mst  WHERE PARAMETER_KEY='SEND_OPS_SIZE'";
		    	String val=ConnectionDAO.singleReturn(str);
		    	str=null;
			if(CommonFunction.checkNull(val).trim().equalsIgnoreCase(""))
				val="100";			
			
            	bufInsSql.append(" limit " + 0 + ","+ CommonFunction.checkNull(val).trim());
            	val=null;
					logger.info("In searchfileTracking......... query..........."+ bufInsSql.toString());
					searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size=searchlist.size();
			for (int i = 0; i < size; i++) 
			{				
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) 
				{
					fileTrackingVO = new FileTrackingVO();
					fileTrackingVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
					fileTrackingVO.setLoanNo((CommonFunction.checkNull(data.get(1)).trim()));
					fileTrackingVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
					fileTrackingVO.setBranch((CommonFunction.checkNull(data.get(3)).trim()));
					fileTrackList.add(fileTrackingVO);
				}
			}				
		} catch (Exception e) 
					{e.printStackTrace();}
		finally
		{
					fileTrackVo =null;
					fileTrackingVO = null;
					searchlist.clear();
					searchlist=null;
					data.clear();
					data=null;
					bufInsSql=null;
					ob=null;
		}
		return fileTrackList;

		}

	public boolean insertfileTrackingtostore(Object ob,String[] loanIds,String[] receivedDates,String[] remarks) {
		FileTrackingVO fileTrackVo = (FileTrackingVO) ob;
		boolean status = false;
		boolean statusUp = false;
		PrepStmtObject insertPrepStmtObject =null;
		StringBuilder queryUpdate =null;
		ArrayList qryList = new ArrayList();
		ArrayList qryList1 = new ArrayList();
		StringBuilder bufInsSql=null;
		ArrayList subTrackingId=null;
		try {
			logger.info("loanIds.length"+loanIds.length);
			for(int i=0; i<loanIds.length; i++)
			{
					queryUpdate= new StringBuilder();
					insertPrepStmtObject= new PrepStmtObject();
					queryUpdate.append("update cr_file_track_dtl set RECEIVED_DATE=STR_TO_DATE(?,'"+dateFormat+ "'),FILE_STATUS='SS',REMARKS=?,MAKER_ID=?,MAKER_DATE=STR_TO_DATE(?,'"+dateFormat+ "')  where LOAN_ID=? ");

			if (CommonFunction.checkNull(receivedDates[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString((receivedDates[i]).trim());


			if (CommonFunction.checkNull((remarks[i]).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString((remarks[i]).trim());
			
			if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

			if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());

			if ((CommonFunction.checkNull(loanIds[i])).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			else
					insertPrepStmtObject.addString((loanIds[i]).trim());

					insertPrepStmtObject.setSql(queryUpdate.toString());
					qryList.add(insertPrepStmtObject);
					logger.info("IN  updateFileTracking ### "+ insertPrepStmtObject.printQuery());
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			if(status){
				
					StringBuilder trackingQuery= new StringBuilder();
					trackingQuery.append("SELECT TRACKING_ID FROM CR_FILE_TRACK_DTL WHERE LOAN_ID="+CommonFunction.checkNull(loanIds[i])+"");
					ArrayList trackingIdList=ConnectionDAO.sqlSelect(trackingQuery.toString());
					trackingQuery=null;
					int size=trackingIdList.size();
					for(int j=0;j<size; j++)
				{
					
					 subTrackingId=(ArrayList) trackingIdList.get(j);
					if(subTrackingId.size()>0)
					{
						insertPrepStmtObject= new PrepStmtObject();
					bufInsSql=new StringBuilder();
					bufInsSql.append("insert into cr_file_track_status_dtl (TRACKING_ID,FILE_TRACKING_STATUS,FILE_REMARKS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,"); // TRACKING_ID
					bufInsSql.append(" 'SS',"); // FILE_TRACKING_STATUS
					bufInsSql.append(" ?,");//FILE_REMARKS
					bufInsSql.append(" ?,"); // MAKER_ID
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
						+ "'),INTERVAL CURTIME() HOUR_SECOND))"); // MAKER_DATE
				

				if (CommonFunction.checkNull(subTrackingId.get(0)).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(subTrackingId.get(0)).trim());
				
				if (CommonFunction.checkNull((remarks[i]).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((remarks[i]).trim());
				
				if (CommonFunction.checkNull(fileTrackVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerId()).trim());

				if (CommonFunction.checkNull(fileTrackVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fileTrackVo.getMakerDate()).trim());
				
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList1.add(insertPrepStmtObject);
					logger.info("IN  insert cr_file_track_status_dtl file status ### "+ insertPrepStmtObject.printQuery());
					statusUp = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
				
					}
				
				}
					StringBuilder queryUpdateLoan = new StringBuilder();
					PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
					qryList1=new ArrayList();
					queryUpdateLoan.append("update cr_loan_dtl set file_received_date=STR_TO_DATE(?,'"+dateFormat+ "'),file_status='SS'  where LOAN_ID=? ");

					if (CommonFunction.checkNull(receivedDates[i]).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((receivedDates[i]).trim());
					if ((CommonFunction.checkNull(loanIds[i])).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((loanIds[i]).trim());

					updatePrepStmtObject.setSql(queryUpdateLoan.toString());
					queryUpdateLoan=null;
					qryList1.add(updatePrepStmtObject);
					logger.info("IN  updateFileTracking ### "+ updatePrepStmtObject.printQuery());
					statusUp = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
					updatePrepStmtObject=null;
				    }
				   }
		      
		         }

		            catch (Exception e) {
			         e.printStackTrace();
		          }
		         finally
		      {
			     ob=null;
			     fileTrackVo=null;
			     queryUpdate=null;
			     insertPrepStmtObject=null;
			     qryList.clear();
			     qryList=null;
			     bufInsSql=null;
	             subTrackingId=null;
				 qryList1=null;
		      }
		         return statusUp;
	    }
	
	public ArrayList getresultForOPS(String trackingId) {
		 
		ArrayList searchlist = new ArrayList();
		
		ArrayList<FileTrackingVO> resultList = new ArrayList<FileTrackingVO>();

		try {
			logger.info("In getresultForOPS().....................................Dao Impl....."+trackingId);

			StringBuilder bufInsSql = new StringBuilder();
			FileTrackingVO vo = new FileTrackingVO();
			
			bufInsSql.append(" select  VALUE,DESCRIPTION  from generic_master a join cr_file_track_dtl b  on( (instr(concat('|',HOLD_REASON,'|'),concat('|',a.VALUE,'|'))>0)) where generic_key='FILE_HOLD_REASON' and tracking_id ='"+ trackingId+ "' " );
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("IN searchUserBranchEdit() search query1 ### "+ bufInsSql.toString());
			int size=searchlist.size();
			for (int i = 0; i <size ; i++) {
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					vo = new FileTrackingVO();
					vo.setLbxReasonId(CommonFunction.checkNull(data.get(0)).toString());
					vo.setReason(CommonFunction.checkNull(data.get(1)).toString());
					
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
}
	

