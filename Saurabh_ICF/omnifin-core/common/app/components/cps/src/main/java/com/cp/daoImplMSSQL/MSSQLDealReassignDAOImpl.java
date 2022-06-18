package com.cp.daoImplMSSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import jxl.common.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.cp.dao.DealReassignDAO;
import com.cp.vo.DealReassignVo;
import com.cp.vo.ReprocessingDealVo;
public class MSSQLDealReassignDAOImpl implements DealReassignDAO {

	private static final Logger logger=Logger.getLogger(MSSQLDealReassignDAOImpl.class.getClass());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	
	public ArrayList<DealReassignVo> getDealDetails(DealReassignVo dealReassignVo){
		
		logger.info("in getDealDetails method of DealReassignDAOImpl:::::::::::::");
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList<DealReassignVo> loanDetail = new ArrayList<DealReassignVo>();
		StringBuilder query = new StringBuilder();
		StringBuilder queryTemp = new StringBuilder();
		int count=0;
		
		try {
			query.append("SELECT  A.DEAL_ID,DEAL_NO,CUSTOMER_NAME,USER_NAME,G.STAGE_DESC ");
			query.append("FROM cr_deal_movement_dtl A ");
			query.append(" INNER JOIN ( SELECT DEAL_ID,MIN(DEAL_MOVEMENT_ID) MVNT_ID FROM cr_deal_movement_dtl WHERE REC_STATUS='A' AND  DEAL_FORWARDED='00-00-0000 00:00' AND IFNULL(DEAL_FORWARD_USER,'')='' GROUP BY DEAL_ID ");
			query.append("	 ) B ON A.DEAL_ID=B.DEAL_ID  AND B.MVNT_ID=A.DEAL_MOVEMENT_ID ");
			query.append(" INNER JOIN CR_DEAL_DTL C ON A.DEAL_ID=C.DEAL_ID ");
			query.append(" INNER JOIN CR_DEAL_CUSTOMER_M  D ON C.DEAL_CUSTOMER_ID=D.CUSTOMER_ID " );
			query.append(" LEFT JOIN (SELECT DISTINCT STAGE_ID  FROM cr_stage_workflow_m) F ON A.DEAL_STAGE_ID=F.STAGE_ID " );
			query.append(" LEFT JOIN CR_STAGE_M G ON F.STAGE_ID=G.STAGE_ID " );
			query.append(" LEFT JOIN SEC_USER_M M ON A.DEAL_RECEIVED_USER=M.USER_ID " );
			
			
			
			queryTemp.append("SELECT  COUNT(1) ");
			queryTemp.append("FROM cr_deal_movement_dtl A ");
			queryTemp.append(" INNER JOIN ( SELECT DEAL_ID,MIN(DEAL_MOVEMENT_ID) MVNT_ID  FROM cr_deal_movement_dtl WHERE REC_STATUS='A' AND  DEAL_FORWARDED='00-00-0000 00:00' AND IFNULL(DEAL_FORWARD_USER,'')='' GROUP BY DEAL_ID ");
			queryTemp.append("	 ) B ON A.DEAL_ID=B.DEAL_ID  AND B.MVNT_ID=A.DEAL_MOVEMENT_ID ");
			queryTemp.append(" INNER JOIN CR_DEAL_DTL C ON A.DEAL_ID=C.DEAL_ID ");
			queryTemp.append(" INNER JOIN CR_DEAL_CUSTOMER_M  D ON C.DEAL_CUSTOMER_ID=D.CUSTOMER_ID " );
			queryTemp.append(" LEFT JOIN (SELECT DISTINCT STAGE_ID  FROM cr_stage_workflow_m) F ON A.DEAL_STAGE_ID=F.STAGE_ID " );
			queryTemp.append(" LEFT JOIN CR_STAGE_M G ON F.STAGE_ID=G.STAGE_ID " );
			queryTemp.append(" LEFT JOIN SEC_USER_M M ON A.DEAL_RECEIVED_USER=M.USER_ID " );
			
			
			if(!(CommonFunction.checkNull(dealReassignVo.getReportingToUserId()).equalsIgnoreCase("")) && CommonFunction.checkNull(dealReassignVo.getLbxDealNo()).equalsIgnoreCase("") ) 
			{
				
				query.append(" INNER JOIN 	(	SELECT DISTINCT DEAL_ID		FROM cr_deal_movement_dtl	WHERE REC_STATUS='A'  ");
				query.append("	AND IFNULL(DEAL_RECEIVED_USER,'')='"+CommonFunction.checkNull(dealReassignVo.getReportingToUserId())+"' ");
				query.append(" ) E ON C.DEAL_ID=E.DEAL_ID  ");
				
				queryTemp.append(" INNER JOIN 	(	SELECT DISTINCT DEAL_ID		FROM cr_deal_movement_dtl	WHERE REC_STATUS='A'  ");
				queryTemp.append("	AND IFNULL(DEAL_RECEIVED_USER,'')='"+CommonFunction.checkNull(dealReassignVo.getReportingToUserId())+"' ");
				queryTemp.append(" ) E ON C.DEAL_ID=E.DEAL_ID  ");
				
			}
			
															
			if(!(CommonFunction.checkNull(dealReassignVo.getLbxDealNo()).equalsIgnoreCase("")) && CommonFunction.checkNull(dealReassignVo.getReportingToUserId()).equalsIgnoreCase("")) 
			{
				
				query.append(" INNER JOIN 	(	SELECT DISTINCT DEAL_ID		FROM cr_deal_movement_dtl	WHERE REC_STATUS='A'  ");
				query.append("	AND DEAL_ID='"+CommonFunction.checkNull(dealReassignVo.getLbxDealNo())+"' ");  
				query.append(" ) E ON C.DEAL_ID=E.DEAL_ID  ");
				
				queryTemp.append(" INNER JOIN 	(	SELECT DISTINCT DEAL_ID		FROM cr_deal_movement_dtl	WHERE REC_STATUS='A'  ");
				queryTemp.append("	 AND DEAL_ID='"+CommonFunction.checkNull(dealReassignVo.getLbxDealNo())+"' ");
				queryTemp.append(" ) E ON C.DEAL_ID=E.DEAL_ID  ");
					
			}
			
			if(!(CommonFunction.checkNull(dealReassignVo.getLbxDealNo()).equalsIgnoreCase("")) && !(CommonFunction.checkNull(dealReassignVo.getReportingToUserId()).equalsIgnoreCase(""))) 
			{
				
				query.append(" INNER JOIN 	(	SELECT DISTINCT DEAL_ID		FROM cr_deal_movement_dtl	WHERE REC_STATUS='A'  ");
				query.append("	AND DEAL_ID='"+CommonFunction.checkNull(dealReassignVo.getLbxDealNo())+"' ");  
				query.append("	AND IFNULL(DEAL_RECEIVED_USER,'')='"+CommonFunction.checkNull(dealReassignVo.getReportingToUserId())+"' ");
				query.append(" ) E ON C.DEAL_ID=E.DEAL_ID  ");
				
				queryTemp.append(" INNER JOIN 	(	SELECT DISTINCT DEAL_ID		FROM cr_deal_movement_dtl	WHERE REC_STATUS='A'  ");
				queryTemp.append("	 AND DEAL_ID='"+CommonFunction.checkNull(dealReassignVo.getLbxDealNo())+"' ");
				queryTemp.append("	AND IFNULL(DEAL_RECEIVED_USER,'')='"+CommonFunction.checkNull(dealReassignVo.getReportingToUserId())+"' ");
				queryTemp.append(" ) E ON C.DEAL_ID=E.DEAL_ID  ");
					
			}
												


			if(!(CommonFunction.checkNull(dealReassignVo.getLbxBranchId()).equalsIgnoreCase(""))) 
			{
				query.append(" WHERE  DEAL_BRANCH='"+CommonFunction.checkNull(dealReassignVo.getLbxBranchId())+"' ");
				queryTemp.append(" WHERE  DEAL_BRANCH='"+CommonFunction.checkNull(dealReassignVo.getLbxBranchId())+"' ");
				
			}
			
			if(!(CommonFunction.checkNull(dealReassignVo.getReportingToUserId()).equalsIgnoreCase("")) && CommonFunction.checkNull(dealReassignVo.getLbxBranchId()).equalsIgnoreCase("")) 
			{
				
				query.append(" WHERE A.DEAL_RECEIVED_USER='"+CommonFunction.checkNull(dealReassignVo.getReportingToUserId())+"' ");				
				queryTemp.append(" WHERE A.DEAL_RECEIVED_USER='"+CommonFunction.checkNull(dealReassignVo.getReportingToUserId())+"' ");

				
			}
			
			if(!(CommonFunction.checkNull(dealReassignVo.getReportingToUserId()).equalsIgnoreCase("")) && !(CommonFunction.checkNull(dealReassignVo.getLbxBranchId()).equalsIgnoreCase(""))) 
			{
				
				query.append(" AND A.DEAL_RECEIVED_USER='"+CommonFunction.checkNull(dealReassignVo.getReportingToUserId())+"' ");				
				queryTemp.append(" AND  A.DEAL_RECEIVED_USER='"+CommonFunction.checkNull(dealReassignVo.getReportingToUserId())+"' ");

				
			}

			logger.info("query for deal search ............."+query.toString());
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			
			logger.info("query for link ............."+queryTemp.toString());
			String dataCount=ConnectionDAOforEJB.singleReturn(queryTemp.toString());
			if(!(CommonFunction.checkNull(dataCount).equalsIgnoreCase("0")))
				count=Integer.parseInt(dataCount);
			
	
			
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					dealReassignVo=new DealReassignVo();
					dealReassignVo.setCheckBoxLink("<input type="+"checkbox"+" name=checkId id=checkId"+i+"  checked=checked value="+CommonFunction.checkNull(subList.get(0)).trim()+" />");
					dealReassignVo.setDealNumber(CommonFunction.checkNull(subList.get(1)).trim());
					dealReassignVo.setCustomername(CommonFunction.checkNull(subList.get(2)).trim());
					dealReassignVo.setMakerName(CommonFunction.checkNull(subList.get(3)).trim());				
					dealReassignVo.setCurrentDealStage(CommonFunction.checkNull(subList.get(4)).trim());
					dealReassignVo.setTotalRecordSize(count);
					
					loanDetail.add(dealReassignVo);
					dealReassignVo=null;
				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query=null;	
			queryTemp=null;
			mainlist.clear();
			mainlist=null;
			subList.clear();
			subList=null;
			
		}		
		
		return loanDetail;		
	}
	public boolean saveDealDetails(DealReassignVo dealReassignVo){
		

		  logger.info("in saveDealDetails method of DealReassignDAOImpl:::::::::::::::: ");
		  
		  PrepStmtObject insertPrepStmtObject =  new PrepStmtObject();
		  StringBuffer bufInsSql = new StringBuffer();
		  ArrayList queryList=new ArrayList();
		  StringBuffer sbList=new StringBuffer();
		  boolean status=false;
		  String[] alldEALiD=dealReassignVo.getDealIds();
		  int totalDeal=alldEALiD.length;
		  String decision="success";
		
		  for(int i=0;i<totalDeal;i++){
			  sbList.append("'"+alldEALiD[i]+"'");
			  sbList.append(",");
		  }
		  sbList.deleteCharAt(sbList.length()-1);
	
		  try{
			 bufInsSql.append("UPDATE CR_DEAL_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 1 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;		     
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
			  
			 bufInsSql.append("UPDATE CR_DEAL_CUSTOMER_ROLE SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 2 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
			  
			 bufInsSql.append("UPDATE CR_DEAL_COLLATERAL_M SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 3 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_DEAL_LOAN_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 4 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_DEAL_TXNCHARGES_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 5 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_DOCUMENT_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE TXN_TYPE='DC' AND TXNID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 6 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		  
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_DEAL_BUYER_SUPPLIER_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 7 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_FINANCIAL_DATA_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 8 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_POLICY_DECISION SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 9 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE cr_manual_deviation_dtl SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 10 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE cr_quality_check_dtl SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE TXN_TYPE='DC' AND TXN_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 11 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_COLLATETRAL_CHECK_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 12 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_TRADE_CHECK_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 13 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_OBLIGATION_ANALYSIS_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 14 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_SALES_ANALYSIS_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 15 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_BANK_ANALYSIS_DTL SET MAKER_ID=IF(MAKER_ID='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',MAKER_ID)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 16 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
			 insertPrepStmtObject =  new PrepStmtObject();
			 bufInsSql = new StringBuffer();
		     
			 bufInsSql.append("UPDATE CR_DEAL_MOVEMENT_DTL SET DEAL_RECEIVED_USER=IF(DEAL_RECEIVED_USER='"+dealReassignVo.getReportingToUserId()+"','"+dealReassignVo.getReportingToUserIdGrid()+"',DEAL_RECEIVED_USER)  WHERE DEAL_ID IN ("+sbList.toString()+") ");
		     insertPrepStmtObject.setSql(bufInsSql.toString());
		     logger.info("query 17 for UPDATE is ::::"+insertPrepStmtObject.printQuery());
		     queryList.add(insertPrepStmtObject);
		     insertPrepStmtObject=null;
		     bufInsSql=null;
		     
		     status=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);	     
		     logger.info("query for deal reassign is ::::"+status);  
		  }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		finally{
			queryList.clear();
			queryList=null;
				}
		  return status;

	}
	@Override
	public boolean UpdateInsertDealMovemenDtl(ReprocessingDealVo reprocessingDealVo, String companyId,String businessDate, String userId) 
		{
			boolean updstatus = false;
			boolean status = false;
			ArrayList qryUpdList = new ArrayList();
			ArrayList qryInsList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			PrepStmtObject insertPrepStmtObject1=null;
			StringBuilder query= new StringBuilder();
			StringBuilder insQuery= new StringBuilder();
			query.append("update CR_DEAL_MOVEMENT_DTL set rec_status='X' where DEAL_ID=?");
			if ((CommonFunction.checkNull(reprocessingDealVo.getLbxDealNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((reprocessingDealVo.getLbxDealNo()).trim());
			insertPrepStmtObject.setSql(query.toString());
			logger.info("IN UpdateDealMovemenDtl() UPDATE query1 ### "+ insertPrepStmtObject.printQuery());
			qryUpdList.add(insertPrepStmtObject);
			query = null;
			insertPrepStmtObject = null;
			try {
				updstatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryUpdList);
				logger.info("In UpdateDealMovemenDtl.........update status: "+ updstatus);
				if(updstatus)
				{
					insertPrepStmtObject1 = new PrepStmtObject();
					insQuery.append("INSERT INTO CR_DEAL_MOVEMENT_DTL(DEAL_ID, DEAL_STAGE_ID, DEAL_ACTION,	DEAL_RECEIVED, DEAL_FORWARDED,"+
								"DEAL_RECEIVED_USER, DEAL_FORWARD_USER,REC_STATUS)");
					insQuery.append("values(");
					insQuery.append("?,");
					insQuery.append("'DC',");
					insQuery.append("'I',");
					insQuery.append("dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
					insQuery.append(" ?, " );
					insQuery.append("?,");
					insQuery.append("'',");
					insQuery.append("'A' ");
					insQuery.append(")");
					if ((CommonFunction.checkNull(reprocessingDealVo.getLbxDealNo())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject1.addNull();
					else
						insertPrepStmtObject1.addString((reprocessingDealVo.getLbxDealNo()).trim());
					if ((CommonFunction.checkNull(businessDate)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject1.addNull();
					else
						insertPrepStmtObject1.addString((businessDate).trim());
					insertPrepStmtObject1.addNull();
					if ((CommonFunction.checkNull(userId)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject1.addNull();
					else
						insertPrepStmtObject1.addString((userId).trim());
					insertPrepStmtObject1.setSql(insQuery.toString());
					logger.info("IN UpdateDealMovemenDtl() Insert insQuery ### "+ insertPrepStmtObject1.printQuery());
					qryInsList.add(insertPrepStmtObject1);
					insQuery = null;
					insertPrepStmtObject1 = null;
					try
					{
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryInsList);
						logger.info("In UpdateDealMovemenDtl.........Insert status: "+status);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return status;
		}
}
