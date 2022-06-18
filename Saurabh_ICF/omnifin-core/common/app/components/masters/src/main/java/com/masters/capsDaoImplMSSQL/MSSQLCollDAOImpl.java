package com.masters.capsDaoImplMSSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringEscapeUtils;

import com.masters.capsDao.CollDAO;
import com.masters.capsVO.ActionCodeMasterVo;
import com.masters.capsVO.QueueCodeMasterVo;
import com.masters.capsVO.AllocationMasterVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class MSSQLCollDAOImpl implements CollDAO{
	
	private static final Logger logger = Logger.getLogger(MSSQLCollDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList<ActionCodeMasterVo> searchActionCodeData(
			ActionCodeMasterVo actionCodeMasterVo) {
		String codeId = "";
		String codeDesc = "";
		String codeModifyId = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		ActionCodeMasterVo actionCodeVo =null;
		
		ArrayList<ActionCodeMasterVo> detailList = new ArrayList<ActionCodeMasterVo>();
		try {
			logger.info("In searchActionCodeData.....................................Dao Impl");
			codeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(actionCodeMasterVo.getCodeSearchId())).trim());
			codeDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(actionCodeMasterVo.getCodeSearchDesc())).trim());
			codeModifyId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(actionCodeMasterVo.getCodeIdModify())).trim());
			bufInsSql.append("SELECT ACTION_CODE,ACTION_DESC,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status ");
			bufInsSql.append(" FROM COLL_ACTION_CODE_M ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM COLL_ACTION_CODE_M ");

			if ((!(codeId.equalsIgnoreCase(""))) && (!(codeDesc.equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE ACTION_DESC like '%" + codeDesc	+ "%' AND ACTION_CODE='" + codeId + "'");
				bufInsSqlTempCount.append("WHERE ACTION_DESC like '%"+ codeDesc + "%' AND ACTION_CODE='" + codeId + "'");
			}

			else if (!codeDesc.equals("")) {
				bufInsSql.append(" WHERE ACTION_DESC LIKE '%" + codeDesc+ "%' ");
				bufInsSqlTempCount.append(" WHERE ACTION_DESC LIKE '%"+ codeDesc + "%' ");
			}

			else if (!codeId.equals("")) {
				bufInsSql.append(" WHERE ACTION_CODE = '" + codeId + "' ");
				bufInsSqlTempCount.append(" WHERE ACTION_CODE = '" + codeId+ "' ");
			} else if (!codeModifyId.equals("")) {
				bufInsSql.append(" WHERE ACTION_CODE = '" + codeModifyId + "' ");
				bufInsSqlTempCount.append(" WHERE ACTION_CODE = '"+ codeModifyId + "' ");
			}
			
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

	      // if ((codeId.trim() == null && codeDesc.trim() == null) || (codeId.trim().equalsIgnoreCase("") && codeDesc.trim().equalsIgnoreCase(""))
			//		|| actionCodeMasterVo.getCurrentPageLink() > 1) {

				logger.info("current PAge Link no .................... "+ actionCodeMasterVo.getCurrentPageLink());
				if (actionCodeMasterVo.getCurrentPageLink() > 1) {
					startRecordIndex = (actionCodeMasterVo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
				}
				bufInsSql.append(" ORDER BY ACTION_CODE OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				logger.info("Search searchActionCodeData query for SQL SERVER : " + bufInsSql.toString());
	
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));

			
		//	}
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchActionCodeData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchActionCodeData " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					actionCodeVo = new ActionCodeMasterVo();

					actionCodeVo	.setCodeIdModify("<a href=actionCodeMasterSearch.do?method=openEditActionCode&codeId="
									+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					actionCodeVo.setCodeSearchId(CommonFunction.checkNull(data.get(0)).toString());
					actionCodeVo.setCodeId(CommonFunction.checkNull(data.get(0)).toString());
					actionCodeVo.setCodeSearchDesc(CommonFunction.checkNull(data.get(1)).toString());
					actionCodeVo.setCodeDesc(CommonFunction.checkNull(data.get(1)).toString());
					actionCodeVo.setCodeStatus(CommonFunction.checkNull(data.get(2)).toString());
					actionCodeVo.setTotalRecordSize(count);
					detailList.add(actionCodeVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			codeId = null;
			codeDesc = null;
			codeModifyId = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			actionCodeVo =null;
		}

		return detailList;
	}
	


	public boolean insertActionCodeMaster(ActionCodeMasterVo actionCodeMasterVo) {
		boolean status = false;
		logger.info("In insertActionCodeMaster.........."+ actionCodeMasterVo.getCodeStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		//String query = "SELECT ACTION_DESC FROM CR_DOCUMENT_M where ACTION_DESC='"+ StringEscapeUtils.escapeSql(actionCodeMasterVo.getCodeDesc().trim()) + "'";
		String query = "SELECT ACTION_CODE FROM COLL_ACTION_CODE_M where ACTION_CODE='"+ StringEscapeUtils.escapeSql(actionCodeMasterVo.getCodeId().trim()) + "'";
		logger.info("In insertActionCodeMaster.....................................Dao Impl" + query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

		if (!st) {
			if (actionCodeMasterVo.getCodeStatus()!= null && actionCodeMasterVo.getCodeStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
				logger.info("In insert Action Code master");
				
				
				bufInsSql.append("INSERT INTO COLL_ACTION_CODE_M(ACTION_CODE,ACTION_DESC,REC_STATUS,ACTION_DESC_L,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//ACTION_CODE
				bufInsSql.append(" ?,");//ACTION_DESC
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//ACTION_DESC_L
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
								
				if (CommonFunction.checkNull(actionCodeMasterVo.getCodeId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(actionCodeMasterVo.getCodeId().toUpperCase().trim());

				if (CommonFunction.checkNull(actionCodeMasterVo.getCodeDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(actionCodeMasterVo.getCodeDesc().toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(actionCodeMasterVo.getCodeDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(actionCodeMasterVo.getCodeDesc().toUpperCase().trim());

				if (CommonFunction.checkNull(actionCodeMasterVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(actionCodeMasterVo.getMakerId());

				if (CommonFunction.checkNull(actionCodeMasterVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(actionCodeMasterVo.getMakerDate());

				if (CommonFunction.checkNull(actionCodeMasterVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(actionCodeMasterVo.getMakerId());

				if (CommonFunction.checkNull(actionCodeMasterVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(actionCodeMasterVo.getMakerDate());
				    insertPrepStmtObject.setSql(bufInsSql.toString());
				    
				logger.info("IN insertActionCodeMaster() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertActionCodeMaster......................"+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			qryList =null;
			insertPrepStmtObject =null;
			stat=null;
			bufInsSql =null;
			query =null;
		}
		return status;
	}
	
	

	public boolean updateActionCodeData(ActionCodeMasterVo actionCodeMasterVo) {
		
		logger.info("In updateActionCodeData");
		logger.info("vo.getDocumentStatus():-"+ actionCodeMasterVo.getCodeDesc());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		boolean status = false;
		String stat = "";
		try {

			if (actionCodeMasterVo.getCodeStatus() != null	&& actionCodeMasterVo.getCodeStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			
			logger.info("In updateActionCodeData.....................................Dao Impl");
			bufInsSql.append("UPDATE COLL_ACTION_CODE_M SET ACTION_DESC=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			bufInsSql.append(" where ACTION_CODE=?");

			if (CommonFunction.checkNull(actionCodeMasterVo.getCodeDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(actionCodeMasterVo.getCodeDesc().toUpperCase().trim());

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			//---------------------------------------
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(actionCodeMasterVo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(actionCodeMasterVo.getMakerId()).trim());
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(actionCodeMasterVo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(actionCodeMasterVo.getMakerDate()).trim());
		
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(actionCodeMasterVo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(actionCodeMasterVo.getMakerId()).trim());
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(actionCodeMasterVo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(actionCodeMasterVo.getMakerDate()).trim());
			//---------------------------------------
			if (CommonFunction.checkNull(actionCodeMasterVo.getCodeId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(actionCodeMasterVo.getCodeId().toUpperCase().trim());

			insertPrepStmtObject.setSql(bufInsSql.toString());
			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			logger.info("print query........"+insertPrepStmtObject.printQuery());
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			insertPrepStmtObject = null;
			updatelist = null;
			bufInsSql = null;
			stat = null;
		}
		return status;

	}

	public ArrayList<QueueCodeMasterVo> searchQueueCodeData(QueueCodeMasterVo VO) {
		String queueSearchCode = "";
		String queueSearchDesc = "";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data = new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		QueueCodeMasterVo queueCodeVo =null;
		
		ArrayList<QueueCodeMasterVo> detailList = new ArrayList<QueueCodeMasterVo>();
		try {
			logger.info("In searchQueueCodeData.....................................Dao Impl");
			queueSearchCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(VO.getQueueSearchCode())).trim());
			queueSearchDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(VO.getQueueSearchDesc())).trim());

			
				
				bufInsSql.append("SELECT QUEUE_CODE,QUEUE_DESC,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status ,PRIORITY");
				bufInsSql.append(" from coll_queue_def_m ");
				
	            bufInsSqlTempCount.append("SELECT count(1) from coll_queue_def_m  ");

				if ((!(queueSearchCode.equalsIgnoreCase(""))) && (!(queueSearchDesc.equalsIgnoreCase("")))) {
					bufInsSql.append("where  QUEUE_DESC like '%" + queueSearchDesc + "%' AND QUEUE_CODE='" + queueSearchCode + "'");
					bufInsSqlTempCount.append("where QUEUE_DESC like '%" + queueSearchDesc + "%' AND QUEUE_CODE='" + queueSearchCode + "'");
				}


				else if (!queueSearchCode.equals("")) {
					bufInsSql.append(" where QUEUE_CODE = '" + queueSearchCode + "' ");
					bufInsSqlTempCount.append(" where QUEUE_CODE = '" + queueSearchCode + "' ");
				}
					

				else if (!queueSearchDesc.equals("")) {
					bufInsSql.append(" where QUEUE_DESC like '%" + queueSearchDesc + "%' ");
					bufInsSqlTempCount.append(" where QUEUE_DESC like '%" + queueSearchDesc + "%' ");
				}	
			
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	        count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if((queueSearchCode.trim()==null && queueSearchDesc.trim()==null) || (queueSearchCode.trim().equalsIgnoreCase("") && queueSearchDesc.trim().equalsIgnoreCase("")) || VO.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+VO.getCurrentPageLink());
			if(VO.getCurrentPageLink()>1)
			{
				startRecordIndex = (VO.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}

			bufInsSql.append(" ORDER BY QUEUE_CODE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search searchQueueCodeData query for SQL SERVER : " + bufInsSql.toString());

			
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));	
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("IN searchQueueCodeData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchQueueCodeData " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {

				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					queueCodeVo = new QueueCodeMasterVo();
					            
					queueCodeVo.setQueueIdModify("<a href=queueCodeMasterBehind.do?method=openEditQueueCode&queueCode="
									+ CommonFunction.checkNull(data.get(0)).toString() + ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");

					queueCodeVo.setQueueSearchCode(CommonFunction.checkNull(data.get(0)));
					queueCodeVo.setQueueCode(CommonFunction.checkNull(data.get(0)));
					queueCodeVo.setQueueSearchDesc(CommonFunction.checkNull(data.get(1)));
					queueCodeVo.setQueueDesc(CommonFunction.checkNull(data.get(1)));
					queueCodeVo.setQueueStatus(CommonFunction.checkNull(data.get(2)));
					queueCodeVo.setPriority(CommonFunction.checkNull(data.get(3)));
			
					queueCodeVo.setTotalRecordSize(count);
					detailList.add(queueCodeVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			queueSearchCode = null;
			queueSearchDesc = null;
			searchlist = null;
			data = null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			queueCodeVo =null;
		}
		return detailList;
	}
	
	public ArrayList<QueueCodeMasterVo> NPAStageList()
	{ 
			ArrayList<QueueCodeMasterVo> npaStageList =new  ArrayList<QueueCodeMasterVo>();
			
			ArrayList searchList=new ArrayList();
			ArrayList StageList=new ArrayList();
			QueueCodeMasterVo VO=null;
			try{
	              String query="select  NPA_STAGE from cr_npa_m where REC_STATUS='A'";
	              logger.info("In npastage......Dao Impl"+query);
					
	              searchList=ConnectionDAOforEJB.sqlSelect(query);
	              logger.info("query::::::::::::::::::"+query);
	              int size = searchList.size();
	              for(int i=0; i<size; i++){
	            	  StageList=(ArrayList)searchList.get(i);
	            	  if(StageList.size()>0){
	            		  VO=new QueueCodeMasterVo();
	            		  
	            		  VO.setNpaStageId(CommonFunction.checkNull(StageList.get(0)));
						  VO.setNpaStageValue(CommonFunction.checkNull(StageList.get(0)));
						  npaStageList.add(VO); 
						  logger.info("npaStageList:::::::::::::"+npaStageList.size());
	            	  }
	              }
	            	  
	              }
					catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				 searchList=null;
				 StageList=null;
				 VO=null;
			}
			return npaStageList;
	}
	
	
	
	public ArrayList<QueueCodeMasterVo> CustCategoryList()
	{
		  ArrayList<QueueCodeMasterVo> custCategoryList=new  ArrayList<QueueCodeMasterVo>();
		  
		  ArrayList searchList=new ArrayList();
		  ArrayList CategoryList=new ArrayList();
		  QueueCodeMasterVo VO=null;
			try{
	              String query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='CUST_CATEGORY' and rec_status ='A'";	
	              logger.info("In CustCategoryList......Dao Impl"+query);
				  
				  searchList=ConnectionDAOforEJB.sqlSelect(query);
				  logger.info("query:::::::::::::::::::::::"+query);
				  int size = searchList.size();
				  logger.info("searchList:::::::::::::::::::::::"+size);
				  
				  for(int i=0 ; i<size; i++){
					CategoryList = (ArrayList) searchList.get(i);

					if(CategoryList.size()>0){
						VO=new QueueCodeMasterVo();	
						VO.setCustCategoryId(CommonFunction.checkNull(CategoryList.get(0)).toString());
						VO.setCustCategoryValue(CommonFunction.checkNull(CategoryList.get(1)).toString());
						custCategoryList.add(VO);
						
					}
					  
				  }				
				  
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				 searchList=null;
				 CategoryList=null;
				 VO=null;
			}
			return custCategoryList;
	}
	
	public ArrayList<QueueCodeMasterVo> editQueueCodeData(String queue) {
		int count=0;

		ArrayList searchlist = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		ArrayList data =new ArrayList();
		QueueCodeMasterVo queueCodeVo =null;
			
		ArrayList<QueueCodeMasterVo> detailList = new ArrayList<QueueCodeMasterVo>();
		try {
			logger.info("In editQueueCodeData.....................................Dao Impl");
			
			bufInsSql.append(" SELECT QUEUE_CODE,QUEUE_DESC,case when REC_STATUS='A' then 'Active' else 'Inactive' end as Status , "); 
			bufInsSql.append(" DPD_GRT,DPD_LES,POS_GRT,POS_LES,CUSTOMER_CATEGORY,");
			bufInsSql.append("CUSTOMER_TYPE,PRIORITY,ACTION_DAYS,(select b.BRANCH_DESC from  com_branch_m b where b.BRANCH_ID = a.BRANCH_ID) as Branch,");
			bufInsSql.append("(select c.PRODUCT_DESC from  cr_product_m c where c.PRODUCT_ID = a.PRODUCT_ID) as Product,");
			bufInsSql.append("(select d.SCHEME_DESC from  cr_scheme_m d where d.SCHEME_ID = a.SCHEME_ID) as Scheme,BRANCH_ID,PRODUCT_ID,SCHEME_ID FROM coll_queue_def_m a ");
		
			 if (!queue.equals("")) {
					bufInsSql.append(" where QUEUE_CODE = '" + queue + "' ");
				
				}

			logger.info("search Query...." + bufInsSql.toString());
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
		
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
			logger.info("IN searchQueueCodeData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchQueueCodeData::: " + size);
			
			for (int i = 0; i < size; i++) {
				logger.info("searchQueueCodeDataList "+ searchlist.get(i).toString());
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					queueCodeVo = new QueueCodeMasterVo();

					queueCodeVo.setQueueSearchCode(CommonFunction.checkNull(data.get(0)));
					queueCodeVo.setQueueCode(CommonFunction.checkNull(data.get(0)));
					queueCodeVo.setQueueSearchDesc(CommonFunction.checkNull(data.get(1)));
					queueCodeVo.setQueueDesc(CommonFunction.checkNull(data.get(1)));
					queueCodeVo.setQueueStatus(CommonFunction.checkNull(data.get(2)));
					queueCodeVo.setDpdGreater(CommonFunction.checkNull(data.get(3)));
					queueCodeVo.setDpdLess(CommonFunction.checkNull(data.get(4)));
					queueCodeVo.setPosGreater(CommonFunction.checkNull(data.get(5)));
					queueCodeVo.setPosLess(CommonFunction.checkNull(data.get(6)));
					queueCodeVo.setCustCategory(CommonFunction.checkNull(data.get(7)));
					queueCodeVo.setCustType(CommonFunction.checkNull(data.get(8)));
					queueCodeVo.setPriority(CommonFunction.checkNull(data.get(9)));
					queueCodeVo.setActionPeriod(CommonFunction.checkNull(data.get(10)));
					
					queueCodeVo.setBranch(CommonFunction.checkNull(data.get(11)));
					logger.info("queueCodeVo.setBranch::::::::::::::::"+queueCodeVo.getBranch());
					queueCodeVo.setProduct(CommonFunction.checkNull(data.get(12)));
					logger.info("queueCodeVo.setProduct:::::::::::::::"+queueCodeVo.getProduct());
					queueCodeVo.setScheme(CommonFunction.checkNull(data.get(13)));
					logger.info("queueCodeVo.setScheme:::::::::::::::::"+queueCodeVo.getScheme());
					queueCodeVo.setLbxBranchId(CommonFunction.checkNull(data.get(14)));
					logger.info("queueCodeVo.getLbxBranchId:::::::::::::::::"+queueCodeVo.getLbxBranchId());
					queueCodeVo.setLbxProductID(CommonFunction.checkNull(data.get(15)));
					logger.info("queueCodeVo.getLbxProductID:::::::::::::::::"+queueCodeVo.getLbxProductID());
					queueCodeVo.setLbxscheme(CommonFunction.checkNull(data.get(16)));
					logger.info("queueCodeVo.getLbxscheme:::::::::::::::::"+queueCodeVo.getLbxscheme());	

					queueCodeVo.setTotalRecordSize(count);
					detailList.add(queueCodeVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			searchlist = null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			data =null;
			queueCodeVo =null;
		}
		return detailList;
		
		}

	public boolean insertQueueCodeMaster(QueueCodeMasterVo queueCodeMasterVo){
		boolean status = false;
		logger.info("In insertQueueCodeMaster.........."+ queueCodeMasterVo.getQueueStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder queueQuery = new StringBuilder();
		
			try {
			if (queueCodeMasterVo.getQueueStatus()!= null && queueCodeMasterVo.getQueueStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
	
				
			
				bufInsSql.append("INSERT INTO coll_queue_def_m(QUEUE_CODE,QUEUE_DESC,QUEUE_DESC_L,DPD_LES,DPD_GRT,POS_GRT,POS_LES," +
						"CUSTOMER_CATEGORY,CUSTOMER_TYPE,PRIORITY,ACTION_DAYS,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,BRANCH_ID,PRODUCT_ID,SCHEME_ID,QUEUE_QUERY)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//QUEUE_CODE
				bufInsSql.append(" ?,");//QUEUE_DESC
				bufInsSql.append(" ?,");//QUEUE_DESC_L
				bufInsSql.append(" ?,");//DPD_GRT
				bufInsSql.append(" ?,");//DPD_LES
				bufInsSql.append(" ?,");//POS_GRT
				bufInsSql.append(" ?,");//POS_LES
				//bufInsSql.append(" ?,");//TOS_GRT
				//bufInsSql.append(" ?,");//TOS_LES
				//bufInsSql.append(" ?,");//NPA_STAGE
				bufInsSql.append(" ?,");//CUSTOMER_CATEGORY
				bufInsSql.append(" ?,");//CUSTOMER_TYPE
				bufInsSql.append(" ?,");//PRIORITY
				bufInsSql.append(" ?,");//ACTION_DAYS
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?, ");//BRANCH_ID
				bufInsSql.append(" ?, ");//PRODUCT_ID
				bufInsSql.append(" ? ,");//SCHEME_ID
				bufInsSql.append(" ? )");//QUEUE_QUERY
				
				//Change for coll_case_dtl => cr_loan_dtl   @ SUR 30/08/12
				queueQuery.append(" FROM  cr_loan_dtl CCD left join  " +
						          " gcd_customer_m GCM on GCM.CUSTOMER_ID=CCD.LOAN_CUSTOMER_ID " +
						          " where GCM.CUSTOMER_STATUS='A' ");
				
				if (CommonFunction.checkNull(queueCodeMasterVo.getQueueCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();

				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getQueueCode().toUpperCase().trim());
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getQueueDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getQueueDesc().toUpperCase().trim());
				   
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getQueueDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getQueueDesc().toUpperCase().trim());
//				queueQuery.append(" AND QUEUE_DESC_L="+queueCodeMasterVo.getQueueDesc()+" ");
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getDpdLess()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getDpdLess().trim()); 
				queueQuery.append(" AND CCD.LOAN_DPD <='"+queueCodeMasterVo.getDpdLess()+"' ");
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getDpdGreater()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getDpdGreater().trim());
				queueQuery.append(" AND CCD.LOAN_DPD >='"+queueCodeMasterVo.getDpdGreater()+"' ");
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getPosGreater()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(queueCodeMasterVo.getPosGreater().toUpperCase().trim())).toString()); 
				queueQuery.append(" AND CCD.LOAN_OVERDUE_PRINCIPAL >='"+myFormatter.parse(queueCodeMasterVo.getPosGreater())+"' ");
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getPosLess()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
			   insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(queueCodeMasterVo.getPosLess().toUpperCase().trim())).toString()); 
				queueQuery.append(" AND CCD.LOAN_OVERDUE_PRINCIPAL <='"+myFormatter.parse(queueCodeMasterVo.getPosLess())+"' ");
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getCustCategory()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getCustCategory().toUpperCase().trim()); 
					queueQuery.append(" AND GCM.CUSTOMER_CATEGORY='"+queueCodeMasterVo.getCustCategory()+"' ");
			}
				
				if (CommonFunction.checkNull(queueCodeMasterVo.getCustType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getCustType().toUpperCase().trim()); 
				queueQuery.append(" AND CCD.LOAN_CUSTOMER_TYPE='"+queueCodeMasterVo.getCustType()+"' ");
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getPriority()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					
					insertPrepStmtObject.addString(queueCodeMasterVo.getPriority()); 
					//queueQuery.append(" AND PRIORITY='"+queueCodeMasterVo.getPriority()+"' ");
				}
				
				if (CommonFunction.checkNull(queueCodeMasterVo.getActionPeriod()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getActionPeriod()); 
				//queueQuery.append(" AND ACTION_DAYS='"+queueCodeMasterVo.getActionPeriod()+"' ");
				}
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else{
				insertPrepStmtObject.addString(stat); 
				//queueQuery.append(" AND REC_STATUS='"+stat+"' ");
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getMakerId()); 
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getMakerDate()); 
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getMakerId()); 
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getMakerDate()); 			   
				}
				
				if (CommonFunction.checkNull(queueCodeMasterVo.getLbxBranchId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getLbxBranchId());	
					if(queueCodeMasterVo.getLbxBranchId().equalsIgnoreCase("0")){
						
					}
					else{
						queueQuery.append(" AND CCD.LOAN_BRANCH='"+queueCodeMasterVo.getLbxBranchId()+"' ");	
					}
				    
				}  
				if (CommonFunction.checkNull(queueCodeMasterVo.getLbxProductID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
					insertPrepStmtObject.addString(queueCodeMasterVo.getLbxProductID());
                       if(queueCodeMasterVo.getLbxProductID().equalsIgnoreCase("0"))
                       {
						
					   }
					else{
						  queueQuery.append(" AND CCD.LOAN_PRODUCT='"+queueCodeMasterVo.getLbxProductID()+"' ");
					}
				  
				} 
				    if (CommonFunction.checkNull(queueCodeMasterVo.getLbxscheme()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else{
						insertPrepStmtObject.addString(queueCodeMasterVo.getLbxscheme());
						 if(queueCodeMasterVo.getLbxscheme().equalsIgnoreCase("0"))
	                       {
							
						   }
						else{
							queueQuery.append(" AND CCD.LOAN_SCHEME='"+queueCodeMasterVo.getLbxscheme()+"' ");
						    }
					    
					} 
					    logger.info("queueQuery:::::::::::::::::::::::"+queueQuery.toString());
					    
					    insertPrepStmtObject.addString(queueQuery.toString());
					    insertPrepStmtObject.setSql(bufInsSql.toString()); 
				    
				logger.info("IN insertQueueCodeMaster() insert query1:::::"	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			qryList = null;
			insertPrepStmtObject = null;
			stat = null;
			bufInsSql = null;
			queueQuery = null;
		}
		logger.info("In insertActionCodeMaster..............."+ status);

		return status;
		
		
}
	
   
	public boolean updateQueueCodeData(QueueCodeMasterVo queueCodeMasterVo) {
		String queueCode = queueCodeMasterVo.getQueueCode();
		logger.info("getQueueCode():-" + queueCodeMasterVo.getQueueCode());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder queueQuery = new StringBuilder();

		boolean status = false;
		String stat = "";
		try {

			if (queueCodeMasterVo.getQueueStatus() != null
					&& queueCodeMasterVo.getQueueStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			
			logger.info("In updateQueueCodeData.....................................Dao Impl");

			bufInsSql.append(" UPDATE coll_queue_def_m set QUEUE_DESC=?,QUEUE_DESC_L=?,DPD_GRT=?,DPD_LES=?,POS_GRT=?,POS_LES=?,");
			bufInsSql.append(" CUSTOMER_CATEGORY=?,CUSTOMER_TYPE=?,PRIORITY=?,ACTION_DAYS=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("BRANCH_ID=?,PRODUCT_ID=?,SCHEME_ID=?,QUEUE_QUERY=? ");
			bufInsSql.append(" where QUEUE_CODE='" + queueCode + "'");
			
			//Change for coll_case_dtl => cr_loan_dtl  By Arun 
			queueQuery.append(" FROM  cr_loan_dtl CCD left join  " +
			          " gcd_customer_m GCM on GCM.CUSTOMER_ID=CCD.LOAN_CUSTOMER_ID " +
			          " where GCM.CUSTOMER_STATUS='A' ");
			
			if (CommonFunction.checkNull(queueCodeMasterVo.getQueueDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getQueueDesc().toUpperCase().trim());
				//queueQuery.append(" QUEUE_DESC='"+queueCodeMasterVo.getQueueDesc()+"' ");
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getQueueDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getQueueDesc().toUpperCase().trim());
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getDpdGreater()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getDpdGreater().trim());
				queueQuery.append(" AND CCD.LOAN_DPD >='"+queueCodeMasterVo.getDpdGreater()+"' ");
				
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getDpdLess()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			{
				insertPrepStmtObject.addString(queueCodeMasterVo.getDpdLess().trim());
				queueQuery.append(" AND CCD.LOAN_DPD <='"+queueCodeMasterVo.getDpdLess()+"' ");
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getPosGreater()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(queueCodeMasterVo.getPosGreater().toUpperCase().trim())).toString());
			
				queueQuery.append(" AND CCD.LOAN_OVERDUE_PRINCIPAL >='"+myFormatter.parse(queueCodeMasterVo.getPosGreater())+"' ");
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getPosLess()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(queueCodeMasterVo.getPosLess().toUpperCase().trim())).toString());
			queueQuery.append(" AND CCD.LOAN_OVERDUE_PRINCIPAL <='"+myFormatter.parse(queueCodeMasterVo.getPosLess())+"' ");
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getCustCategory()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getCustCategory().toUpperCase().trim());
                 queueQuery.append(" AND GCM.CUSTOMER_CATEGORY='"+queueCodeMasterVo.getCustCategory()+"' ");
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getCustType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getCustType().toUpperCase().trim());
			queueQuery.append(" AND CCD.LOAN_CUSTOMER_TYPE='"+queueCodeMasterVo.getCustType()+"' ");
		         }
			if (CommonFunction.checkNull(queueCodeMasterVo.getPriority()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getPriority());
			//queueQuery.append(" AND PRIORITY='"+queueCodeMasterVo.getPriority()+"' ");
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getActionPeriod()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getActionPeriod());
			//queueQuery.append(" AND ACTION_DAYS='"+queueCodeMasterVo.getActionPeriod()+"' ");
			}
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(stat);
			//queueQuery.append(" AND REC_STATUS='"+stat+"' ");
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getMakerId());
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(queueCodeMasterVo.getMakerDate());
			
			if (CommonFunction.checkNull(queueCodeMasterVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getMakerId());
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(queueCodeMasterVo.getMakerDate());
			
			if (CommonFunction.checkNull(queueCodeMasterVo.getLbxBranchId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getLbxBranchId());
				if(queueCodeMasterVo.getLbxBranchId().equalsIgnoreCase("0")){
					
				}
				else{
					 queueQuery.append(" AND CCD.LOAN_BRANCH='"+queueCodeMasterVo.getLbxBranchId()+"' ");
				}
	        
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getLbxProductID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(queueCodeMasterVo.getLbxProductID().toUpperCase().trim());		
			   if(queueCodeMasterVo.getLbxProductID().equalsIgnoreCase("0"))
			   {
			   }
				else{
					 queueQuery.append(" AND CCD.LOAN_PRODUCT='"+queueCodeMasterVo.getLbxProductID()+"' ");
				}
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getLbxscheme()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				 insertPrepStmtObject.addString(queueCodeMasterVo.getLbxscheme());
				 if(queueCodeMasterVo.getLbxscheme().equalsIgnoreCase("0"))
				 {
    			 }
				 else{
					  queueQuery.append(" AND CCD.LOAN_SCHEME='"+queueCodeMasterVo.getLbxscheme()+"' ");
					 }
			
			}
			
			logger.info("queueQuery:::::::::::::::::::::::"+queueQuery.toString());
		    
		    insertPrepStmtObject.addString(queueQuery.toString());
		    insertPrepStmtObject.setSql(bufInsSql.toString()); 

			
			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());

			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			logger.info("print query........"+ insertPrepStmtObject.printQuery());

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			queueCode = null;
			insertPrepStmtObject = null;
			updatelist = null;
			bufInsSql = null;
			queueQuery = null;
			stat = null;
		}
		logger.info("status........................"+ status);
		
		return status;

	}

	public ArrayList<QueueCodeMasterVo> ProductList()
	{
		
		  ArrayList<QueueCodeMasterVo> productList=new  ArrayList<QueueCodeMasterVo>();
		  
		  ArrayList searchList=new ArrayList();
		  ArrayList ProdList=new ArrayList();
		  QueueCodeMasterVo VO=null;
			try{
	            String query="select PRODUCT_ID,PRODUCT_DESC from cr_product_m where REC_STATUS='A' ";		
				logger.info("In ProductList......Dao Impl:::"+query);
				
				searchList=ConnectionDAOforEJB.sqlSelect(query);
				for (int i = 0; i < searchList.size(); i++) {
					ProdList = (ArrayList) searchList.get(i);
					if (ProdList.size() > 0) {
						VO=new QueueCodeMasterVo();
						VO.setProductListId(CommonFunction.checkNull(ProdList.get(0)).toString());
						VO.setProductListValue(CommonFunction.checkNull(ProdList.get(1)).toString());
						productList.add(VO);
					}
				}
			}
				  
			catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				searchList=null;
				  ProdList=null;
				  VO=null;
			}
			return productList;
	}

	public String checkQueueCodeMaster(QueueCodeMasterVo queueCodeMasterVo){
		boolean status = false;
		String flagg="";
		String count1="";
		String query="";
		String count="";
		String query1="";
		try
		{
			logger.info("In checkQueueCodeMaster.........."+ queueCodeMasterVo.getQueueStatus());
			
			query = "SELECT count(1) FROM coll_queue_def_m where QUEUE_CODE='"+ StringEscapeUtils.escapeSql(queueCodeMasterVo.getQueueCode().trim()) + "'  ";
			logger.info("In checkQueueCodeMaster.....................................Dao Impl" + query);
			count=ConnectionDAOforEJB.singleReturn(query);
			logger.info("In checkQueueCodeMaster........count " + count);
			
	        if (count.equalsIgnoreCase("0")) {
			query1 = "SELECT count(1) FROM coll_queue_def_m where PRIORITY='"+queueCodeMasterVo.getPriority() +"' and REC_STATUS='A' ";
			count1=ConnectionDAOforEJB.singleReturn(query1);
			logger.info("In checkQueueCodeMaster........count1 " + count1);
			}
	        
	        if (count.equalsIgnoreCase("0") && count1.equalsIgnoreCase("0")) {
	        	status=true;
	        	flagg="Pass";
	        }else if(!count.equalsIgnoreCase("0")){
	        	flagg="QueueExist";
	        }else if(!count1.equalsIgnoreCase("0")){
	        	flagg="PriorityExist";
	        }
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			count1=null;
			query=null;
			count=null;
			query1=null;
		}
		logger.info("In checkQueueCodeMaster........queueCodeMasterVo.getQueueStatus() " + queueCodeMasterVo.getQueueStatus());
		logger.info("In checkQueueCodeMaster........count1 " + status);
		
		return flagg;
	}
	
	public String checkPriority(QueueCodeMasterVo queueCodeMasterVo){
		String count1="";
		String flagg="";
		String query1 ="";
		try
		{
			query1 = "SELECT count(1) FROM coll_queue_def_m where PRIORITY='"+queueCodeMasterVo.getPriority() +"'  and QUEUE_CODE <> '"+queueCodeMasterVo.getQueueCode() +"' and REC_STATUS='A' ";
			count1=ConnectionDAOforEJB.singleReturn(query1);
			 if (count1.equalsIgnoreCase("0")) {
		        
		        	flagg="Pass";
		        }
			else if(!count1.equalsIgnoreCase("0")){
	        	flagg="PriorityExist";
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			count1=null;
			query1 =null;
		}
		 return flagg;
	}
	
	public ArrayList<AllocationMasterVo> getQueueList() {
		ArrayList<AllocationMasterVo> queueList = new ArrayList<AllocationMasterVo>();
		
        ArrayList mainlist=new ArrayList();
		ArrayList subList=new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		AllocationMasterVo  queueVo = null;

		try {
			logger.info("In getQueueList()....................");
			
			bufInsSql.append("SELECT CQDM.queue_code,CQDM.queue_desc,(SELECT SUM(caqm.ALLOCATION_PERCENTAGE) from coll_alloc_queue_map caqm WHERE  caqm.QUEUE_CODE=  CQDM.queue_code) AS TOTAL from coll_queue_def_m CQDM where rec_status='A'");
			logger.info("Query: "+bufInsSql.toString());
		     mainlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
		     int size = mainlist.size();
		 	for(int i=0;i<size;i++){

				subList=(ArrayList)mainlist.get(i);
			
				if (subList.size() > 0) {
					queueVo=new AllocationMasterVo();
					queueVo.setQueue(CommonFunction.checkNull(subList.get(0)).toString());
					queueVo.setQueuedesc(CommonFunction.checkNull(subList.get(1)).toString());
					queueVo.setShowtotalpercent(CommonFunction.checkNull(subList.get(2)).toString());
					queueList.add(queueVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist= null;
			subList= null;
			bufInsSql = null;
			queueVo = null;
		}
		return queueList;
	}

	public ArrayList<AllocationMasterVo> searchQueueAllocationData(AllocationMasterVo allocationVo){
		String queueId = "";
		StringBuilder qry = new StringBuilder();
		//String qry ="";
		String st = "";
		StringBuilder str = new StringBuilder();
		//String str="";
		int count=0;
		String percent1="";
		String branchId="";
		ArrayList searchlist = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		ArrayList data = new ArrayList();
		AllocationMasterVo vo = null;

		ArrayList<AllocationMasterVo> detailList = new ArrayList<AllocationMasterVo>();
		try {

			String allocationId=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getAllocationId())).trim());
			logger.info("allocationId.........."+allocationId);
			queueId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getLbxQueuesearchId())).trim());
			logger.info("queueId"+queueId);
			branchId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getLbxBranchIds())).trim());
			String areaCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getLbxareaCodeVal())).trim());
			qry.append("(SELECT count(1) FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+queueId+ "'  AND  ");
			qry.append("ISNULL(BRANCH_ID,'')='"+branchId+"')");
			if(!areaCode.equalsIgnoreCase(""))
			{
				qry.append(" AND ");
				qry.append("ISNULL(AREA_CODE,'')='"+areaCode+"' ");
			}
			
			st = ConnectionDAOforEJB.singleReturn(qry.toString());
			
			if(!st.equalsIgnoreCase("")){

			str.append("select sum(");
			str.append("ISNULL(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+queueId+"' AND  ");
			str.append("ISNULL(BRANCH_ID,'')='"+branchId+"' " );
            if(!areaCode.equalsIgnoreCase(""))
            {
				qry.append(" AND ");
				qry.append("ISNULL(AREA_CODE,'')='"+areaCode+"' ");
			}
			
			percent1=ConnectionDAOforEJB.singleReturn(str.toString());
			logger.info("percent1");
			if(percent1.equalsIgnoreCase(""))
			{
				percent1="0";
			}
			
			}else{
				percent1="0";
			}
			
			bufInsSql.append(" Select CAQM.QUEUE_CODE,(Select QUEUE_DESC from coll_queue_def_m CQDM where CQDM.QUEUE_CODE=CAQM.QUEUE_CODE) AS QUEUE_DESC,CAQM.USER_ID,(Select USER_NAME from sec_user_m SEUM where CAQM.USER_ID=SEUM.USER_ID) AS USER_NAME,");
			bufInsSql.append(" CAQM.ALLOCATION_PERCENTAGE,BRANCH_ID,(select BRANCH_DESC from com_branch_m  where BRANCH_ID = CAQM.BRANCH_ID),ALLOC_ID,REC_STATUS,(select AREA_CODE_NAME from com_areacode_m A where  A.AREA_CODE=CAQM.AREA_CODE ) AS AREA_CODE_NAME,AREA_CODE,ALLOCATION_TYPE from coll_alloc_queue_map CAQM where QUEUE_CODE='"+queueId+"' AND " );
			bufInsSql.append("ISNULL(BRANCH_ID,'')='"+branchId+"' " );
			if(!areaCode.equalsIgnoreCase(""))
			{
				bufInsSql.append(" AND ");
				bufInsSql.append("ISNULL(AREA_CODE,'')='"+areaCode+"' ");
			}
			else{
				bufInsSql.append(" AND ");
				bufInsSql.append("ISNULL(AREA_CODE,'')='' ");
			}
			
			//bufInsSql.append(" as Status,CAQM.USER_ID FROM coll_alloc_queue_map CAQM JOIN sec_user_m SCM ON SCM.USER_ID=CAQM.USER_ID"); 

			
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
			logger.info("searchQueueAllocationData " +size);
			if(searchlist.size()>0){
			for (int i = 0; i < size; i++) {
				logger.info("searchQueueAllocationDataList " + searchlist.get(i).toString());

				data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
				
					vo = new AllocationMasterVo();
					vo.setPercent(percent1);
					vo.setLbxQueuesearchId(CommonFunction.checkNull(data.get(0)).toString());
					vo.setQueuedesc(CommonFunction.checkNull(data.get(1)).toString());
                    vo.setLbxUserSearchId(CommonFunction.checkNull(data.get(3)).toString());
                    if(CommonFunction.checkNull(data.get(11)).equalsIgnoreCase("OTHER") || CommonFunction.checkNull(data.get(11)).equalsIgnoreCase(""))
                    {
                    	vo.setUserId(CommonFunction.checkNull(data.get(2)).toString());
						if(!CommonFunction.checkNull(data.get(4)).equalsIgnoreCase(""))
	    	    		{
	    	    		Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
	    	    		vo.setAllocationpercentage(myFormatter.format(percentage));
	    	    		}
						else{
							vo.setAllocationpercentage(CommonFunction.checkNull(data.get(4)).trim());	
						}
                    }
					
					vo.setLbxBranchIds(CommonFunction.checkNull(data.get(5)).trim());	
					vo.setBranchId(CommonFunction.checkNull(data.get(6)).trim());	
					//vo.setQueueStatus(CommonFunction.checkNull(data.get(4)).toString());
					vo.setTotalRecordSize(count);
					vo.setFlagval("");
					vo.setAllocationId(CommonFunction.checkNull(data.get(7)).trim());
					vo.setRecStatus(CommonFunction.checkNull(data.get(8)).trim());
					//Surendra Code Start here...........
					vo.setAreaCode(CommonFunction.checkNull(data.get(9)).trim());
					vo.setLbxareaCodeVal(CommonFunction.checkNull(data.get(10)).trim());
					logger.info("data.get(10)..................."+data.get(10));
					vo.setAllocationType(CommonFunction.checkNull(data.get(11)).trim());
					//Surendra Code End here...........
					detailList.add(vo);
				}

			}}else{
				vo = new AllocationMasterVo();
				vo.setFlagval("Noval");
				logger.info("queueId : "+queueId);
				vo.setLbxQueuesearchId(CommonFunction.checkNull(queueId));
				logger.info("Queuedesc : "+allocationVo.getQueueIdSearch());
				vo.setQueuedesc(CommonFunction.checkNull(allocationVo.getQueueIdSearch()));
				detailList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			queueId = null;
			qry = null;
			st = null;
			str= null;
			percent1= null;
			searchlist = null;
			bufInsSql = null;
			data = null;
			vo = null;
		}
		
		return detailList;
	
	}
		
	public ArrayList<AllocationMasterVo> searchQueueAllocationEdit(String userId) {

		ArrayList searchlist = new ArrayList();
		ArrayList data = new ArrayList();
		AllocationMasterVo allocVo = null;
		StringBuilder bufInsSql = new StringBuilder();
		
		ArrayList<AllocationMasterVo> queueListEdit = new ArrayList<AllocationMasterVo>();

		try {
			logger.info("In searchQueueAllocationEdit().....................................Dao Impl");
	
			bufInsSql.append(" SELECT CAQM.ALLOC_ID,CAQM.USER_ID,SCM.USER_NAME,CAQM.QUEUE_CODE,CAQM.ALLOCATION_PERCENTAGE, case when CAQM.REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS");
			bufInsSql.append(" FROM coll_alloc_queue_map CAQM JOIN sec_user_m SCM ON SCM.USER_ID=CAQM.USER_ID WHERE CAQM.USER_ID ='"+ userId + "'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN searchQueueAllocationEdit() search query1 ### "+ bufInsSql.toString());
			int size = searchlist.size();
			logger.info("searchQueueAllocationEdit " + size);
			for (int i = 0; i < size; i++) {
				logger.info("searchQueueAllocationEdit "+ searchlist.get(i).toString());
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					allocVo = new AllocationMasterVo();
					allocVo.setLbxUserSearchId(CommonFunction.checkNull(data.get(1)).toString());
					allocVo.setUserSearchId(CommonFunction.checkNull(data.get(2)).toString());
					allocVo.setQueuecheck(CommonFunction.checkNull(data.get(3)).toString());
					
    	    		if(!CommonFunction.checkNull(data.get(4)).equalsIgnoreCase(""))
    	    		{
    	    		Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
    	    		allocVo.setAllocationpercentage(myFormatter.format(percentage));
    	    		}
    	    		allocVo.setQueueStatus(CommonFunction.checkNull(data.get(5)).toString());

					queueListEdit.add(allocVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			searchlist = null;
			data= null;
			allocVo = null;
			bufInsSql = null;
		}

		return queueListEdit;
	}

public String saveQueueAllocation(AllocationMasterVo collVO,String[] percentage,int total,String[] queueUser) throws SQLException {
		
		String flag="NOTSAVE";
		ArrayList qryList =  new ArrayList();
		String qry ="";
		String st ="";
		String query1 ="";
		boolean status1=false;
		ArrayList qrydelete=new ArrayList ();
		StringBuffer bufInsSql =null;
		StringBuffer querySql =new StringBuffer();
		PrepStmtObject insertPrepStmtObject = null;	
		PrepStmtObject insertPrepStmtObject1 = null;	
		boolean st1=false;
		String stat = "A";
        String areaCode=CommonFunction.checkNull(collVO.getLbxareaCodeVal());
		 insertPrepStmtObject = new PrepStmtObject();
		logger.info("In saveQueueAllocation...Dao Impl.");
		
		try{
		/*String alloacArray[]=collVO.getAllocId();
		String allocForQry="";
		if(alloacArray!=null && alloacArray.length>0){
			for(int i=0;i<alloacArray.length;i++){
				allocForQry=allocForQry+alloacArray[i]+",";
			}
		}*/
		String allocId=collVO.getAllocId();
				qry = "	SELECT count(1) FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "' AND ISNULL(BRANCH_ID,'')='"+CommonFunction.checkNull(collVO.getLbxBranchIds())+"' ";
				
				if(!areaCode.equalsIgnoreCase("")){
					qry=qry+ "AND ISNULL(AREA_CODE,'')='"+areaCode+"'";
				}
				else {
					qry=qry+ "AND ISNULL(AREA_CODE,'')='"+areaCode+"'";
				}
				qry = qry+	"and ALLOC_ID ='"+allocId+"'" ;		

				//if(!allocForQry.equalsIgnoreCase("")){
				//	qry = qry+	"and ALLOC_ID not in("+allocForQry.substring(0, allocForQry.length()-1)+")" ;		
			
				//	}
			
				logger.info("qry------------------------------------------------]>"+qry);
	
			
			st = ConnectionDAOforEJB.singleReturn(qry);
			logger.info("st::::::::::::::::::::::::::::::::::::::::::::::::>"+st);
			
			if(st.equalsIgnoreCase("0"))
			{

				qry = "	SELECT count(1) FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "' and ISNULL(BRANCH_ID,'')='"+CommonFunction.checkNull(collVO.getLbxBranchIds())+"' ";
				
				if(!areaCode.equalsIgnoreCase("")){
					qry=qry+ "AND ISNULL(AREA_CODE,'')='"+areaCode+"'";
				}
				else {
					qry=qry+ "AND ISNULL(AREA_CODE,'')='"+areaCode+"'";
				}
				qry = qry+	"AND ALLOC_ID = '"+allocId+"'" ;		
		
				st = ConnectionDAOforEJB.singleReturn(qry);
				if(!st.equalsIgnoreCase("0"))
				{

				logger.info("before delete  insert");
				 bufInsSql =	new StringBuffer();
				 insertPrepStmtObject1 = new PrepStmtObject();
				 bufInsSql.append("insert into coll_alloc_queue_map_history(user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,maker_id,maker_date,author_id,author_date,REASON,MVMT_BY,MVMT_DATE,AREA_CODE,ALLOCATION_TYPE)");
				 bufInsSql.append("Select user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,maker_id,maker_date,author_id,author_date,'UPDATED',");
				 bufInsSql.append(" '"+collVO.getMakerId()+"',");
				 bufInsSql.append(dbo);
				 bufInsSql.append(" STR_TO_DATE('"+collVO.getMakerDate()+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				 bufInsSql.append(" ,AREA_CODE,ALLOCATION_TYPE from  coll_alloc_queue_map where QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "'");
				 if(!collVO.getLbxBranchIds().equalsIgnoreCase("")){
					 bufInsSql.append(" and BRANCH_ID='"+collVO.getLbxBranchIds()+"'");
					}
				bufInsSql.append(" and ALLOC_ID  ='"+allocId+"'") ;		
				
					
				 //@SURENDRA COde Starts here....
				 if(!collVO.getLbxareaCodeVal().equalsIgnoreCase("")){
					 bufInsSql.append(" and AREA_CODE='"+collVO.getLbxareaCodeVal()+"'");
					}
				insertPrepStmtObject1.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject1);

				
				query1 = "DELETE FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "' and ISNULL(BRANCH_ID,'')='"+CommonFunction.checkNull(collVO.getLbxBranchIds())+"' " ;
				query1 = query1+	"and ALLOC_ID  = '"+allocId+"'" ;
				
				insertPrepStmtObject.setSql(query1);
				qryList.add(insertPrepStmtObject);
			}
					
			
		    logger.info("In INSERT BEFORE DELETE.......query......."+qryList.toString());
		    //status1=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		   
           //   logger.info("In INSERT BEFORE DELETE.......query........"+status1);
          	//qryList =  new ArrayList();
			
			
			
			//st1 = ConnectionDAOforEJB.sqlInsUpdDelete(qryList);
		
			
			
				
				for (int i = 0; i < total; i++) {
//					String str="select sum(ISNULL(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+collVO.getLbxQueuesearchId()+"'";
//					String percent1=ConnectionDAOforEJB.singleReturn(str);
//					logger.info("percent1");
//					if(percent1.equalsIgnoreCase(""))
//					{
//						percent1="0";
//					}
//					logger.info("percentage[i]-->"+percentage[i]);
//					Double totalpercent=0.0;
//					for(int n = 0; n < total; n++) {
//						totalpercent=Double.parseDouble(percent1)+Double.parseDouble(percentage[i]);
//					}
//				logger.info("totalpercent-->"+totalpercent);
//				if(totalpercent > 100){
//					flag="F";
//					logger.info("flag=F for percent");
//				}else{
					if (collVO.getRecStatus() != null
							&& collVO.getRecStatus().equalsIgnoreCase("on")) {
						stat = "A";
					} else {
						stat = "X";

					}
					logger.info(" for insert _-----------Status:-"+stat);
					 bufInsSql =	new StringBuffer();
					 insertPrepStmtObject = new PrepStmtObject();
					 //@Surendra Added Code for Area-Code.
					 bufInsSql.append("insert into coll_alloc_queue_map(user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,maker_id,maker_date,author_id,author_date,AREA_CODE,ALLOCATION_TYPE)");
					 
						bufInsSql.append(" values ( ");
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(dbo);
						 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						 bufInsSql.append(" ?,");
						 bufInsSql.append(dbo);
						 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						 bufInsSql.append(" ?, " );
						 bufInsSql.append(" ?)" );
					
						 if(CommonFunction.checkNull(queueUser[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(queueUser[i]).trim()));
						 
						 if(CommonFunction.checkNull(collVO.getLbxQueuesearchId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getLbxQueuesearchId());
						 if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(stat);
					
						 if(CommonFunction.checkNull(percentage[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(percentage[i].trim())).toString());
						 if(CommonFunction.checkNull(collVO.getLbxBranchIds()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getLbxBranchIds());
							if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerId());

							if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerDate());
							if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerId());

							if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerDate());
							//@sur........
							 if(CommonFunction.checkNull(collVO.getLbxareaCodeVal()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getLbxareaCodeVal());
							
							 if(CommonFunction.checkNull(collVO.getAllocationType()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getAllocationType());
							
						insertPrepStmtObject.setSql(bufInsSql.toString());
						qryList.add(insertPrepStmtObject);

				}
				//}		
				
			    logger.info("In saveQueueAllocation............query.........."+qryList.toString());
			    status1=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			    if(status1){
			    	flag="SAVE";
			    }
	              logger.info("In saveQueueAllocation......................"+flag);
	               
			}else{
				String que=collVO.getLbxQueuesearchId();
				String branch=collVO.getLbxBranchIds();

				querySql=querySql.append("Select TOP 1 iif(rec_status='A', 'Active' ,'Inactive') rec_status from coll_alloc_queue_map where QUEUE_CODE='"+que+"' AND ISNULL(BRANCH_ID,'')='"+branch+"'");
				if(!areaCode.equalsIgnoreCase("")){
					querySql.append(" AND ISNULL(AREA_CODE,'')='"+areaCode+"' ");		
				}
					String query=querySql.toString();
				    logger.info("query:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+query);
				String activeRetrurn=ConnectionDAOforEJB.singleReturn(query);
                if(CommonFunction.checkNull(activeRetrurn).equalsIgnoreCase("")){

	            	flag="Data Not Saved";
	            	 logger.info(""+flag);
	            } else{
				flag="Data already exists with Queue Code:-"+que+" and Branch:-"+collVO.getBranchId()+"  with "+activeRetrurn +" Status!";
	            logger.info(""+flag);
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			qryList =null;
			qry =null;
			st =null;
			query1 =null;
			qrydelete=null;
			bufInsSql =null;
			insertPrepStmtObject = null;
			stat =null;
			querySql=null;
			areaCode=null;
			
		}
		
		return flag;
		
	 
	}
	
	
	public ArrayList<AllocationMasterVo> searchQueueEdit(String userId) {

		ArrayList searchlist = new ArrayList();
		AllocationMasterVo allocVo = null;
		StringBuilder bufInsSql = new StringBuilder();
		ArrayList data = new ArrayList();
		
		ArrayList<AllocationMasterVo> queueListEdit = new ArrayList<AllocationMasterVo>();

		try {
			logger.info("In searchQueueEdit().....................................Dao Impl");

			
	
			bufInsSql.append("Select  caqm.QUEUE_CODE  QUE1 , cqdm.QUEUE_CODE QUE2,cqdm.queue_desc,caqm.ALLOCATION_PERCENTAGE,"); 
			bufInsSql.append("(SELECT SUM(caqm.ALLOCATION_PERCENTAGE) from coll_alloc_queue_map  caqm where cqdm.QUEUE_CODE=caqm.QUEUE_CODE and user_id <> '"+ userId + "') as totalpercent");	
			bufInsSql.append(" from coll_alloc_queue_map caqm right join coll_queue_def_m cqdm on");
			bufInsSql.append(" caqm.QUEUE_CODE= cqdm.QUEUE_CODE and caqm.user_id = '"+ userId + "' and cqdm.rec_status='A'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN searchQueueEdit() search query1 ### "+ bufInsSql.toString());
			int size = searchlist.size();
			logger.info("searchQueueEdit " +size);
			for (int i = 0; i < size; i++) {
				logger.info("searchQueueEdit "+ searchlist.get(i).toString());
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					allocVo = new AllocationMasterVo();
					allocVo.setQueue(CommonFunction.checkNull(data.get(0)).toString());
					allocVo.setQueueId(CommonFunction.checkNull(data.get(1)).toString());
					allocVo.setQueuedesc(CommonFunction.checkNull(data.get(2)).toString());

					if(!CommonFunction.checkNull(data.get(3)).equalsIgnoreCase(""))
					{
				    	Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
				    	allocVo.setAllocationpercentage(myFormatter.format(percentage));
				    }
					if(!CommonFunction.checkNull(data.get(4)).equalsIgnoreCase(""))
    	    		{
						Number totalpercentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
						allocVo.setShowtotalpercent(myFormatter.format(totalpercentage));
    	    		}
					queueListEdit.add(allocVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			searchlist = null;
			allocVo = null;
			bufInsSql = null;
			data = null;
		}
		return queueListEdit;
	}

	public String calcpercentage(String[] checkbox) throws SQLException{
		StringBuilder query = new StringBuilder();
		String percentage="";
		try{
			int length = checkbox.length;
		for(int i=0;i<length;i++){
			query.append("select sum(");
			query.append("ISNULL(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+checkbox[i]+"'");
			logger.info("query"+query.toString());
			percentage=ConnectionDAOforEJB.singleReturn(query.toString());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			query = null;
		}
		return percentage;
	}
	
	
	public String modifyqueueAllocation(AllocationMasterVo collVO,String[] checkbox,String[] percentage,int total) {
	
		
		String flag="NOTSAVE";
		ArrayList qryList =  new ArrayList();
		boolean status1=false;
		ArrayList qrydelete=new ArrayList ();
		StringBuffer bufInsSql =null;
		PrepStmtObject insertPrepStmtObject = null;	
		boolean st1=false;
		String stat = "X";
		String query1 = "";
		StringBuilder str=new StringBuilder();
		String percent1="";
		
		insertPrepStmtObject = new PrepStmtObject();
	
		try {
		
					logger.info("In modifyqueueAllocation.....daoimpl()..");
				
					logger.info("delete query");
					query1 = "DELETE FROM coll_alloc_queue_map WHERE USER_ID='"+collVO.getLbxUserSearchId()+ "'";
					insertPrepStmtObject.setSql(query1);
					qryList.add(insertPrepStmtObject);
				
						if (collVO.getQueueStatus() != null && collVO.getQueueStatus().equals("on")) {
							stat = "A";
						} else {
							stat = "X";

						}
					
						for (int i = 0; i < total; i++) {
							str.append("select sum(");
							str.append("ISNULL(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+checkbox[i]+"' and  USER_ID <> '"+collVO.getLbxUserSearchId()+"'");
							percent1=ConnectionDAOforEJB.singleReturn(str.toString());
							if(percent1.equalsIgnoreCase(""))
							{
								percent1="0";
							}
						Double totalpercent=Double.parseDouble(percent1)+Double.parseDouble(percentage[i]);
						logger.info("totalpercent---------->"+totalpercent);
						if(totalpercent > 100){
							flag="F";
						}else{
							 bufInsSql =	new StringBuffer();
							 insertPrepStmtObject = new PrepStmtObject();
							 bufInsSql.append("insert into coll_alloc_queue_map(user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,maker_id,maker_date,author_id,author_date)");
							 
								 bufInsSql.append(" values ( ");
								 bufInsSql.append(" ?," );
								 bufInsSql.append(" ?," );
								 bufInsSql.append(" ?," );
								 bufInsSql.append(" ?," );
								 bufInsSql.append(" ?," );
								 bufInsSql.append(dbo);
								 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
									
								 bufInsSql.append(" ?,");
								 bufInsSql.append(dbo);
								 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
																
								 if(CommonFunction.checkNull(collVO.getLbxUserSearchId()).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(collVO.getLbxUserSearchId()).trim()));
								 
								 if(CommonFunction.checkNull(checkbox[i]).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(checkbox[i]);
								 if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(stat);
							
								 if(CommonFunction.checkNull(percentage[i]).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(percentage[i].trim())).toString());
									if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(collVO.getMakerId());

									if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(collVO.getMakerDate());
									if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(collVO.getMakerId());

									if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(collVO.getMakerDate());

									
								insertPrepStmtObject.setSql(bufInsSql.toString());
								qryList.add(insertPrepStmtObject);

						}
						}		
						if(flag.equalsIgnoreCase("F")){
							flag="PERCENT";
						}else{
					    logger.info("In modifyqueueAllocation............query.........."+qryList.toString());
					    status1=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
					    if(status1){
					    	flag="Modify";
					    }
			              logger.info("In modifyqueueAllocation......................"+flag);
			               
						}
				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					qryList =null;
					qrydelete=null;
					bufInsSql =null;
					insertPrepStmtObject = null;	
					stat =null;
					query1 =null;
					str=null;
					percent1=null;
				}
				return flag;	
	}

	public ArrayList<AllocationMasterVo> searchAllocationData(Object ob) {
		String queueCode = "";
		String queueDesc = "";
		String allocationId = "";
		String query1="";
		
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		AllocationMasterVo vo = (AllocationMasterVo) ob;
		ArrayList<AllocationMasterVo> detailList = new ArrayList<AllocationMasterVo>();
		try {

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			queueCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxQueuesearchId())).trim());
			queueDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getQueuedesc())).trim());
			allocationId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAllocationId())).trim());
			
			logger.info("In searchAllocationData().....................................Dao Impl");
			bufInsSql.append("SELECT b.QUEUE_CODE,(SELECT A.QUEUE_DESC FROM coll_queue_def_m A WHERE A.QUEUE_CODE=B.QUEUE_CODE ) AS QUEUE_DESC,");
			bufInsSql.append("b.USER_ID,(SELECT A.USER_NAME FROM SEC_USER_M A WHERE A.USER_ID=B.USER_ID),ALLOCATION_PERCENTAGE,b.BRANCH_ID,(select BRANCH_DESC from com_branch_m where BRANCH_ID = b.BRANCH_ID) branch ,case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS,B.AREA_CODE" );
			bufInsSql.append(" FROM coll_alloc_queue_map B WHERE 'a'='a' ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM (SELECT QUEUE_CODE,(SELECT A.QUEUE_DESC FROM coll_queue_def_m A WHERE A.QUEUE_CODE=B.QUEUE_CODE ) AS QUEUE_DESC,");
			bufInsSqlTempCount.append("USER_ID,(SELECT A.USER_NAME FROM SEC_USER_M A WHERE A.USER_ID=B.USER_ID)as username,ALLOCATION_PERCENTAGE FROM coll_alloc_queue_map B WHERE 'a'='a' " );
			
			if (!(queueCode.equalsIgnoreCase(""))) {
				bufInsSql.append(" AND b.QUEUE_CODE='" + queueCode + "'");
				bufInsSqlTempCount.append(" AND b.QUEUE_CODE='" + queueCode + "'");
			}
			if (!CommonFunction.checkNull(vo.getLbxUserSearchId()).equalsIgnoreCase("")) {
				bufInsSql.append(" AND B.USER_ID='" + vo.getLbxUserSearchId() + "'");
				bufInsSqlTempCount.append(" AND B.USER_ID='" + vo.getLbxUserSearchId() + "'");
			}
			if (!CommonFunction.checkNull(vo.getLbxBranchIds()).equalsIgnoreCase("")) {
				bufInsSql.append(" AND b.BRANCH_ID='" + vo.getLbxBranchIds() + "'");
				bufInsSqlTempCount.append(" AND b.BRANCH_ID='" + vo.getLbxBranchIds() + "'");
				}
			
			if (!CommonFunction.checkNull(vo.getLbxareaCodeVal()).equalsIgnoreCase("")) {
				bufInsSql.append(" AND b.AREA_CODE='" + vo.getLbxareaCodeVal() + "'");
				bufInsSqlTempCount.append(" AND b.AREA_CODE='" + vo.getLbxareaCodeVal() + "'");
			}
			
			/*if( (!CommonFunction.checkNull(vo.getLbxUserSearchId()).equalsIgnoreCase(""))&&(!(queueCode.equalsIgnoreCase(""))) ) {
				bufInsSql.append(" AND B.USER_ID='" + vo.getLbxUserSearchId() + "' AND b.QUEUE_CODE='" + queueCode + "'");
				bufInsSqlTempCount.append(" AND B.USER_ID='" + vo.getLbxUserSearchId() + "' AND b.QUEUE_CODE='" + queueCode + "'");
			}
			if ((!CommonFunction.checkNull(vo.getLbxBranchId()).equalsIgnoreCase(""))&&(!(queueCode.equalsIgnoreCase(""))) ) {
				bufInsSql.append(" WHERE b.BRANCH_ID='" + vo.getLbxBranchId() + "' AND b.QUEUE_CODE='" + queueCode + "'");
				bufInsSqlTempCount.append(" WHERE b.BRANCH_ID='" + vo.getLbxBranchId() + "' AND b.QUEUE_CODE='" + queueCode + "'");
			}
			if ((!CommonFunction.checkNull(vo.getLbxBranchId()).equalsIgnoreCase(""))&&(!CommonFunction.checkNull(vo.getLbxUserSearchId()).equalsIgnoreCase(""))) {
				bufInsSql.append(" WHERE b.BRANCH_ID='" + vo.getLbxBranchId() + "' AND B.USER_ID='" + vo.getLbxUserSearchId() + "'");
				bufInsSqlTempCount.append(" WHERE b.BRANCH_ID='" + vo.getLbxBranchId() + "' AND B.USER_ID='" + vo.getLbxUserSearchId() + "'");
			}
			if ((!CommonFunction.checkNull(vo.getLbxBranchId()).equalsIgnoreCase(""))&&(!(queueCode.equalsIgnoreCase(""))) &&(!CommonFunction.checkNull(vo.getLbxUserSearchId()).equalsIgnoreCase(""))) {
				bufInsSql.append(" WHERE b.BRANCH_ID='" + vo.getLbxBranchId() + "' AND b.QUEUE_CODE='" + queueCode + "' AND B.USER_ID='" + vo.getLbxUserSearchId() + "'");
				bufInsSqlTempCount.append(" WHERE b.BRANCH_ID='" + vo.getLbxBranchId() + "' AND b.QUEUE_CODE='" + queueCode + "' AND B.USER_ID='" + vo.getLbxUserSearchId() + "'");
			}*/
			bufInsSqlTempCount.append(" )as b");
			logger.info("search Query.............................................." + bufInsSql);
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
        	logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
//			if(queueCode.trim()==null  || queueCode.trim().equalsIgnoreCase("") || vo.getCurrentPageLink()>1)
//			{
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
		
			if(vo.getCurrentPageLink()>1)
			{
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			
				logger.info("no........... "+no);
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			//
			
				bufInsSql.append(" ORDER BY QUEUE_CODE,BRANCH_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				logger.info("Search searchAllocationData query for SQL SERVER : " + bufInsSql.toString());

				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
			
			logger.info("query : "+bufInsSql);
						
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			logger.info("searchAgencyData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					AllocationMasterVo mVo = new AllocationMasterVo();

					mVo.setQueueIdModify("<a href=allocationmasteradd.do?method=openEditAllocation&queueId="+CommonFunction.checkNull(data.get(0)).toString()+"&branchId="+CommonFunction.checkNull(data.get(5)).toString()+"&areaCode="+CommonFunction.checkNull(data.get(8)).toString() +">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					
					
					// mVo.setAgencyCode("<a href="#" onclick="modifyDetails(CommonFunction.checkNull(data.get(0)).toString());" ></a>");
					//mVo.setAllocationId(CommonFunction.checkNull(data.get(0)).toString());
					mVo.setQueueId(CommonFunction.checkNull(data.get(0)).toString());
					mVo.setQueuedesc(CommonFunction.checkNull(data.get(1)).toString());
					mVo.setLbxUserSearchId(CommonFunction.checkNull(data.get(2)).toString());
					if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase(""))
						mVo.setUser(CommonFunction.checkNull(data.get(2)).toString());
					else
						mVo.setUser(CommonFunction.checkNull(data.get(3)).toString());
					mVo.setPercent(CommonFunction.checkNull(data.get(4)).toString());
					mVo.setShowbranchId(CommonFunction.checkNull(data.get(5)).toString());
					mVo.setShowbranchName(CommonFunction.checkNull(data.get(6)).toString());
					mVo.setRecStatus(CommonFunction.checkNull(data.get(7)).toString());
					detailList.add(mVo);
					mVo.setTotalRecordSize(count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			AllocationMasterVo mVo = new AllocationMasterVo();
//			mVo.setTotalRecordSize(count);
//			detailList.add(mVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}



	public ArrayList getresultForBranchAllcation(String allocId) {
		 
		ArrayList searchlist = new ArrayList();
		
		ArrayList<AllocationMasterVo> resultList = new ArrayList<AllocationMasterVo>();

		try {
			logger.info("In getresultForBranchAllcation().....................................Dao Impl....."+allocId);

			StringBuilder bufInsSql = new StringBuilder();
			AllocationMasterVo vo = new AllocationMasterVo();
			
			bufInsSql.append(" select distinct a.branch_id,a.branch_desc  from com_branch_m a join coll_alloc_queue_map b  on( (instr(concat('|',b.Branch_id,'|'),concat('|',a.Branch_id,'|'))>0)) where  alloc_Id ='"+ allocId+ "' " );
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("IN searchUserBranchEdit() search query1 ### "+ bufInsSql.toString());
			int size=searchlist.size();
			for (int i = 0; i <size ; i++) {
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					vo = new AllocationMasterVo();
					vo.setLbxBranchIds(CommonFunction.checkNull(data.get(0)));
					vo.setBranchDesc(CommonFunction.checkNull(data.get(1)));
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


public String saveQueueAllocation(AllocationMasterVo collVO,String[] percentage,int total,String[] queueUser,String[] branchList) throws SQLException {
		
		String flag="NOTSAVE";
		ArrayList qryList =  new ArrayList();
		String qry ="";
		ArrayList st =new ArrayList ();
		String query1 ="";
		String branch="";
		if(CommonFunction.checkNull(branchList).length()==0)
		{
			branch="' '";
		}
		else
		{
		for(int i=0;i<branchList.length;i++)
		{
			logger.info("In saveQueueAllocation...Dao Impl."+branchList[i]);
			//branch=branch+","+branchList[i];
			//branch=branch.substring(0);
			branch=branch+branchList[i]+",";
			
		}
		branch=branch.replace("'","");
		branch = branch.substring(0,branch.length()-1);
		}
		boolean status1=false;
		ArrayList qrydelete=new ArrayList ();
		StringBuffer bufInsSql =null;
		StringBuffer querySql =new StringBuffer();
		PrepStmtObject insertPrepStmtObject = null;	
		PrepStmtObject insertPrepStmtObject1 = null;	
		boolean st1=false;
		String stat = "A";
        String areaCode=CommonFunction.checkNull(collVO.getLbxareaCodeVal());
		 insertPrepStmtObject = new PrepStmtObject();
		logger.info("In saveQueueAllocation...Dao Impl."+branchList);
		logger.info("In saveQueueAllocation...Dao Impl."+branch);
		try{
	/*	String alloacArray[]=collVO.getAllocId();
		String allocForQry="";
		if(alloacArray!=null && alloacArray.length>0){
			for(int i=0;i<alloacArray.length;i++){
				allocForQry=allocForQry+alloacArray[i]+",";
			}
		}*/
		String allocId=collVO.getAllocId();
		
				qry = "	SELECT branch_id FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "' and branch_id in ("+branch+") ";
				
				if(!areaCode.equalsIgnoreCase("")){
					qry=qry+ "AND IFNULL(AREA_CODE,'')='"+areaCode+"'";
				}
				else {
					qry=qry+ "AND IFNULL(AREA_CODE,'')='"+areaCode+"'";
				}
				/*if(!allocForQry.equalsIgnoreCase("")){
					qry = qry+	"and ALLOC_ID not in("+allocForQry.substring(0, allocForQry.length()-1)+")" ;		
			
					}*/
			
				logger.info("qry------------------------------------------------]>"+qry);
	
			
			st = ConnectionDAOforEJB.sqlSelect(qry);
			logger.info("st::::::::::::::::::::::::::::::::::::::::::::::::>"+st);
			
			if(st.size()>0)
			{
				//richa 
				String activeRetrurn="";
				String branchId="";
				String que=collVO.getLbxQueuesearchId();
				for (int i=0;i<branchList.length;i++){
				
					querySql =new StringBuffer();
					querySql=querySql.append("Select if(rec_status='A', 'Active' ,'Inactive') rec_status from coll_alloc_queue_map where QUEUE_CODE='"+que+"' AND IFNULL(BRANCH_ID,'')='"+branchList[i]+"'");
					if(!areaCode.equalsIgnoreCase("")){
					querySql.append(" AND IFNULL(AREA_CODE,'')='"+areaCode+"' ");		
				}
					querySql.append(" limit 1 ");		
				    String query=querySql.toString();
				    logger.info("query:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+query);
				 activeRetrurn=ConnectionDAOforEJB.singleReturn(query);
				 if(!CommonFunction.checkNull(activeRetrurn).equalsIgnoreCase("")){
					 branchId=branchId+branchList[i]+",";
				 }
				 
				}
                if(!CommonFunction.checkNull(branchId).equalsIgnoreCase("")){
				flag="Data already exists with Queue Code:-"+que+" and Branch:-"+branchId+"";
                }
	           
	            }
			else if (CommonFunction.checkNull(branchList).length()>0){
					qry = "	SELECT value FROM generate_sequence_tbl WHERE SEQ_KEY='alloc_id'";
					String seq = ConnectionDAOforEJB.singleReturn(qry);
					int seq1= Integer.parseInt(seq)+1;
					logger.info(" for insert _-----------Status:-"+seq);
					bufInsSql =	new StringBuffer();
					insertPrepStmtObject = new PrepStmtObject();
				 //@Surendra Added Code for Area-Code.
					bufInsSql.append("UPDATE generate_sequence_tbl SET VALUE='"+seq1 +"'  WHERE SEQ_KEY='alloc_id'");
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);
				
					for (int i = 0; i < total; i++) {
						for (int j = 0; j < branchList.length; j++) {
							if (collVO.getRecStatus() != null
							&& collVO.getRecStatus().equalsIgnoreCase("on")) {
						stat = "A";
					} else {
						stat = "X";

					}
					
						bufInsSql =	new StringBuffer();
						 insertPrepStmtObject = new PrepStmtObject();
						 bufInsSql.append("insert into coll_alloc_queue_map(alloc_id,user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,maker_id,maker_date,author_id,author_date,AREA_CODE,ALLOCATION_TYPE)");
					 
						 bufInsSql.append(" values ( ");
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
						 bufInsSql.append(" ?,");
						 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");
						 bufInsSql.append(" ?, " );
						 bufInsSql.append(" ?)" );
					
						 if(CommonFunction.checkNull(seq).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(seq);
						 if(CommonFunction.checkNull(queueUser[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(queueUser[i]).trim()));
						 
						 if(CommonFunction.checkNull(collVO.getLbxQueuesearchId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getLbxQueuesearchId());
						 if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(stat);
					
						 if(CommonFunction.checkNull(percentage[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(percentage[i].trim())).toString());
						 
						 if(CommonFunction.checkNull(branchList[j]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(branchList[j]).trim()));
						 
						if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerId());

							if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerDate());
							if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerId());

							if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerDate());
							//@sur........
							 if(CommonFunction.checkNull(collVO.getLbxareaCodeVal()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getLbxareaCodeVal());
							 
							if(CommonFunction.checkNull(collVO.getAllocationType()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getAllocationType());

							
						insertPrepStmtObject.setSql(bufInsSql.toString());
						qryList.add(insertPrepStmtObject);
					}
				}
				//}		
				
			    logger.info("In saveQueueAllocation............query.........."+qryList.toString());
			    status1=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			    if(status1){
			    	flag="SAVE";
			   
	              logger.info("In saveQueueAllocation......................"+flag);
	               
			}
			}
			else
			{
				qry = "	SELECT value FROM generate_sequence_tbl WHERE SEQ_KEY='alloc_id'";
				String seq = ConnectionDAOforEJB.singleReturn(qry);
				int seq1= Integer.parseInt(seq)+1;
				logger.info(" for insert _-----------Status:-"+seq);
				bufInsSql =	new StringBuffer();
				insertPrepStmtObject = new PrepStmtObject();
			 //@Surendra Added Code for Area-Code.
				bufInsSql.append("UPDATE generate_sequence_tbl SET VALUE='"+seq1 +"'  WHERE SEQ_KEY='alloc_id'");
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
				
				for (int i = 0; i < total; i++) {
//				String str="select sum(ifnull(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+collVO.getLbxQueuesearchId()+"'";
//				String percent1=ConnectionDAOforEJB.singleReturn(str);
//				logger.info("percent1");
//				if(percent1.equalsIgnoreCase(""))
//				{
//					percent1="0";
//				}
//				logger.info("percentage[i]-->"+percentage[i]);
//				Double totalpercent=0.0;
//				for(int n = 0; n < total; n++) {
//					totalpercent=Double.parseDouble(percent1)+Double.parseDouble(percentage[i]);
//				}
//			logger.info("totalpercent-->"+totalpercent);
//			if(totalpercent > 100){
//				flag="F";
//				logger.info("flag=F for percent");
//			}else{
				if (collVO.getRecStatus() != null
						&& collVO.getRecStatus().equalsIgnoreCase("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				
					bufInsSql =	new StringBuffer();
					 insertPrepStmtObject = new PrepStmtObject();
				 bufInsSql.append("insert into coll_alloc_queue_map(alloc_id,user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,maker_id,maker_date,author_id,author_date,AREA_CODE,ALLOCATION_TYPE)");
				 
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
						bufInsSql.append(" ?,");
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");
						 bufInsSql.append(" ?, " );
					bufInsSql.append(" ?)" );
				
					 if(CommonFunction.checkNull(seq).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(seq);
					 if(CommonFunction.checkNull(queueUser[i]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(queueUser[i]).trim()));
					 
					 if(CommonFunction.checkNull(collVO.getLbxQueuesearchId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(collVO.getLbxQueuesearchId());
					 if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(stat);
				
					 if(CommonFunction.checkNull(percentage[i]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(percentage[i].trim())).toString());
					 if(CommonFunction.checkNull(collVO.getLbxBranchIds()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(collVO.getLbxBranchIds());
						if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getMakerId());

						if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getMakerDate());
						if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getMakerId());

						if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getMakerDate());
						//@sur........
						 if(CommonFunction.checkNull(collVO.getLbxareaCodeVal()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getLbxareaCodeVal());
						 
						if(CommonFunction.checkNull(collVO.getAllocationType()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getAllocationType());

						
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);
				
			}
			//}		
			
		    logger.info("In saveQueueAllocation............query.........."+qryList.toString());
		    status1=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		    if(status1){
		    	flag="SAVE";
		   
              logger.info("In saveQueueAllocation......................"+flag);
               
		}
		}/*else{
				String que=collVO.getLbxQueuesearchId();
				String branch=collVO.getLbxBranchIds();

				querySql=querySql.append("Select if(rec_status='A', 'Active' ,'Inactive') rec_status from coll_alloc_queue_map where QUEUE_CODE='"+que+"' AND IFNULL(BRANCH_ID,'')='"+branch+"'");
				if(!areaCode.equalsIgnoreCase("")){
					querySql.append(" AND IFNULL(AREA_CODE,'')='"+areaCode+"' ");		
				}
					querySql.append(" limit 1 ");		
				    String query=querySql.toString();
				    logger.info("query:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+query);
				String activeRetrurn=ConnectionDAOforEJB.singleReturn(query);
                if(CommonFunction.checkNull(activeRetrurn).equalsIgnoreCase("")){

	            	flag="Data Not Saved";
	            	 logger.info(""+flag);
	            } else{
				flag="Data already exists with Queue Code:-"+que+" and Branch:-"+collVO.getBranchId()+"  with "+activeRetrurn +" Status!";
	            logger.info(""+flag);
			}
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			qryList =null;
			qry =null;
			st =null;
			query1 =null;
			qrydelete=null;
			bufInsSql =null;
			insertPrepStmtObject = null;
			stat =null;
			querySql=null;
			areaCode=null;
			
		}
		
		return flag;
		
	 
	}
	




	@Override
	public String modifyQueueAllocationDtl(AllocationMasterVo collVO,String[] percentage,int total,String[] queueUser,String[] branchList) throws SQLException {
		
		String flag="NOTSAVE";
		ArrayList qryList =  new ArrayList();
		String qry ="";
		ArrayList st =new ArrayList ();
		String query1 ="";
		String branch="";
		if(CommonFunction.checkNull(branchList).length()==0)
		{
			branch="''";
		}
		else
		{
		for(int i=0;i<branchList.length;i++)
		{
			logger.info("In saveQueueAllocation...Dao Impl."+branchList[i]);
			//branch=branch+","+branchList[i];
			//branch=branch.substring(0);
			branch=branch+branchList[i]+",";
			
		}
		branch=branch.replace("'","");
		branch = branch.substring(0,branch.length()-1);
		}
		boolean status1=false;
		ArrayList qrydelete=new ArrayList ();
		StringBuffer bufInsSql =null;
		StringBuffer querySql =new StringBuffer();
		PrepStmtObject insertPrepStmtObject = null;	
		PrepStmtObject insertPrepStmtObject1 = null;	
		boolean st1=false;
		String stat = "A";
        String areaCode=CommonFunction.checkNull(collVO.getLbxareaCodeVal());
		 insertPrepStmtObject = new PrepStmtObject();
		logger.info("In saveQueueAllocation...Dao Impl."+branchList);
		logger.info("In saveQueueAllocation...Dao Impl."+branch);
		try{
	/*	String alloacArray[]=collVO.getAllocId();
		String allocForQry="";
		if(alloacArray!=null && alloacArray.length>0){
			for(int i=0;i<alloacArray.length;i++){
				allocForQry=allocForQry+alloacArray[i]+",";
			}
		}*/
		String allocId=collVO.getAllocId();
		
				qry = "	SELECT branch_id FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "' and branch_id in ("+branch+") and alloc_id <> '"+allocId+"'";
				
				if(!areaCode.equalsIgnoreCase("")){
					qry=qry+ "AND IFNULL(AREA_CODE,'')='"+areaCode+"'";
				}
				else {
					qry=qry+ "AND IFNULL(AREA_CODE,'')='"+areaCode+"'";
				}
				/*if(!allocForQry.equalsIgnoreCase("")){
					qry = qry+	"and ALLOC_ID not in("+allocForQry.substring(0, allocForQry.length()-1)+")" ;		
			
					}*/
			
				logger.info("qry------------------------------------------------]>"+qry);
	
			
			st = ConnectionDAOforEJB.sqlSelect(qry);
			logger.info("st::::::::::::::::::::::::::::::::::::::::::::::::>"+st);
			
			if(st.size()>0)
			{
				//richa 
				String activeRetrurn="";
				String branchId="";
				String que=collVO.getLbxQueuesearchId();
				for (int i=0;i<branchList.length;i++){
				
					querySql =new StringBuffer();
					querySql=querySql.append("Select if(rec_status='A', 'Active' ,'Inactive') rec_status from coll_alloc_queue_map where QUEUE_CODE='"+que+"' AND IFNULL(BRANCH_ID,'')='"+branchList[i]+"' ");
					if(!areaCode.equalsIgnoreCase("")){
					querySql.append(" AND IFNULL(AREA_CODE,'')='"+areaCode+"' ");		
				}
					querySql.append(" limit 1 ");		
				    String query=querySql.toString();
				    logger.info("query:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+query);
				 activeRetrurn=ConnectionDAOforEJB.singleReturn(query);
				 if(!CommonFunction.checkNull(activeRetrurn).equalsIgnoreCase("")){
					 branchId=branchId+branchList[i]+",";
				 }
				 
				}
                if(!CommonFunction.checkNull(branchId).equalsIgnoreCase("")){
				flag="Data already exists with Queue Code:-"+que+" and Branch:-"+branchId+"";
                }
			}
	            else
	            {
	      
				//richa
				
				logger.info("before delete  insert");
				 bufInsSql =	new StringBuffer();
				 insertPrepStmtObject1 = new PrepStmtObject();
				 bufInsSql.append("insert into coll_alloc_queue_map_history(alloc_id,user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,maker_id,maker_date,author_id,author_date,REASON,MVMT_BY,MVMT_DATE,AREA_CODE,ALLOCATION_TYPE)");
				 bufInsSql.append("Select alloc_id,user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,maker_id,maker_date,author_id,author_date,'UPDATED',");
				 bufInsSql.append(" '"+collVO.getMakerId()+"',");
				 bufInsSql.append(" DATE_ADD(STR_TO_DATE('"+collVO.getMakerDate()+"', '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
				 bufInsSql.append(" ,AREA_CODE,ALLOCATION_TYPE from  coll_alloc_queue_map where ");
				 bufInsSql.append("  ALLOC_ID = '"+allocId+"'") ;		
				
				 insertPrepStmtObject1.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject1);

				
				query1 = "DELETE FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "' " ;
				query1 = query1+	"and ALLOC_ID = '"+allocId+"'" ;
				
				insertPrepStmtObject.setSql(query1);
				qryList.add(insertPrepStmtObject);
			
		    logger.info("In INSERT BEFORE DELETE.......query......."+qryList.toString());
		   
			 if (CommonFunction.checkNull(branchList).length()>0){
				for (int i = 0; i < total; i++) {
						for (int j = 0; j < branchList.length; j++) {
//				
					if (collVO.getRecStatus() != null
							&& collVO.getRecStatus().equalsIgnoreCase("on")) {
						stat = "A";
					} else {
						stat = "X";

					}
					
						bufInsSql =	new StringBuffer();
						 insertPrepStmtObject = new PrepStmtObject();
						 bufInsSql.append("insert into coll_alloc_queue_map(alloc_id,user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,maker_id,maker_date,author_id,author_date,AREA_CODE,ALLOCATION_TYPE)");
					 
						 bufInsSql.append(" values ( ");
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
						 bufInsSql.append(" ?,");
						 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");
						 bufInsSql.append(" ?, " );
						 bufInsSql.append(" ?)" );
					
						 if(CommonFunction.checkNull(allocId).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(allocId);
						 if(CommonFunction.checkNull(queueUser[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(queueUser[i]).trim()));
						 
						 if(CommonFunction.checkNull(collVO.getLbxQueuesearchId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getLbxQueuesearchId());
						 if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(stat);
					
						 if(CommonFunction.checkNull(percentage[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(percentage[i].trim())).toString());
						 
						 if(CommonFunction.checkNull(branchList[j]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(branchList[j]).trim()));
						 
						if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerId());

							if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerDate());
							if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerId());

							if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getMakerDate());
							//@sur........
							 if(CommonFunction.checkNull(collVO.getLbxareaCodeVal()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getLbxareaCodeVal());
							 
							if(CommonFunction.checkNull(collVO.getAllocationType()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(collVO.getAllocationType());

							
						insertPrepStmtObject.setSql(bufInsSql.toString());
						qryList.add(insertPrepStmtObject);
					}
				}
				//}		
				
			    logger.info("In saveQueueAllocation............query.........."+qryList.toString());
			    status1=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			    if(status1){
			    	flag="SAVE";
			   
	              logger.info("In saveQueueAllocation......................"+flag);
	               
			}
			}
			else
			{
				for (int i = 0; i < total; i++) {
//				
				if (collVO.getRecStatus() != null
						&& collVO.getRecStatus().equalsIgnoreCase("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				
					bufInsSql =	new StringBuffer();
					 insertPrepStmtObject = new PrepStmtObject();
				 bufInsSql.append("insert into coll_alloc_queue_map(alloc_id,user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,maker_id,maker_date,author_id,author_date,AREA_CODE,ALLOCATION_TYPE)");
				 
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
						bufInsSql.append(" ?,");
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");
						 bufInsSql.append(" ?, " );
					bufInsSql.append(" ?)" );
				
					 if(CommonFunction.checkNull(allocId).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(allocId);
					 if(CommonFunction.checkNull(queueUser[i]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(queueUser[i]).trim()));
					 
					 if(CommonFunction.checkNull(collVO.getLbxQueuesearchId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(collVO.getLbxQueuesearchId());
					 if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(stat);
				
					 if(CommonFunction.checkNull(percentage[i]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(percentage[i].trim())).toString());
					 if(CommonFunction.checkNull(collVO.getLbxBranchIds()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(collVO.getLbxBranchIds());
						if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getMakerId());

						if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getMakerDate());
						if (CommonFunction.checkNull(collVO.getMakerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getMakerId());

						if (CommonFunction.checkNull(collVO.getMakerDate()).equalsIgnoreCase(""))insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getMakerDate());
						//@sur........
						 if(CommonFunction.checkNull(collVO.getLbxareaCodeVal()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getLbxareaCodeVal());
						 
						if(CommonFunction.checkNull(collVO.getAllocationType()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getAllocationType());

						
					insertPrepStmtObject.setSql(bufInsSql.toString());
					qryList.add(insertPrepStmtObject);
				
			}
			//}		
			
		    logger.info("In saveQueueAllocation............query.........."+qryList.toString());
		    status1=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		    if(status1){
		    	flag="SAVE";
		   
              logger.info("In saveQueueAllocation......................"+flag);
               
		}
		}
	            }
			}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			qryList =null;
			qry =null;
			st =null;
			query1 =null;
			qrydelete=null;
			bufInsSql =null;
			insertPrepStmtObject = null;
			stat =null;
			querySql=null;
			areaCode=null;
			
		}
		
		return flag;
		
	 
	}



	@Override
	public ArrayList<AllocationMasterVo> allocatedCollQueueData(
			AllocationMasterVo allocationVo) {
		// TODO Auto-generated method stub
		return null;
	}


}
