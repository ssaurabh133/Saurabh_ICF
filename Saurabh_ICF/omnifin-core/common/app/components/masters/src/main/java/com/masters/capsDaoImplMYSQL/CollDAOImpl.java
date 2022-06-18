package com.masters.capsDaoImplMYSQL;

// change start  by shivam singhal
import java.sql.Connection;
import java.sql.PreparedStatement;
//change end  by shivam singhal
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.masters.capsDao.CollDAO;
import com.masters.capsVO.ActionCodeMasterVo;
import com.masters.capsVO.QueueCodeMasterVo;
import com.masters.capsVO.AllocationMasterVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;

public class CollDAOImpl implements CollDAO{
	
	private static final Logger logger = Logger.getLogger(CollDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
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

			bufInsSql.append("SELECT ACTION_CODE,ACTION_DESC,if(REC_STATUS='A','Active','Inactive')as Status FROM COLL_ACTION_CODE_M ");
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
			bufInsSql.append(" ORDER BY ACTION_CODE");
		
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

	       if ((codeId.trim() == null && codeDesc.trim() == null) || (codeId.trim().equalsIgnoreCase("") && codeDesc.trim().equalsIgnoreCase(""))
					|| actionCodeMasterVo.getCurrentPageLink() > 1) {

				logger.info("current PAge Link no .................... "+ actionCodeMasterVo.getCurrentPageLink());
				if (actionCodeMasterVo.getCurrentPageLink() > 1) {
					startRecordIndex = (actionCodeMasterVo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
				}

				bufInsSql.append(" limit " + startRecordIndex + ","+ endRecordIndex);
			
			}
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
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");//MAKER_DATE
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");//AUTHOR_DATE
				
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
			bufInsSql.append("UPDATE COLL_ACTION_CODE_M SET ACTION_DESC=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where ACTION_CODE=?");

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

			

				bufInsSql.append("SELECT QUEUE_CODE,QUEUE_DESC,if(REC_STATUS='A','Active','Inactive')as Status ,PRIORITY from coll_queue_def_m ");
				
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
					

			bufInsSql.append(" ORDER BY QUEUE_CODE");
			
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	        count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			if((queueSearchCode.trim()==null && queueSearchDesc.trim()==null) || (queueSearchCode.trim().equalsIgnoreCase("") && queueSearchDesc.trim().equalsIgnoreCase("")) || VO.getCurrentPageLink()>1)
			{
			
			logger.info("current PAge Link no .................... "+VO.getCurrentPageLink());
			if(VO.getCurrentPageLink()>1)
			{
				startRecordIndex = (VO.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);

			}
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
			
			bufInsSql.append(" SELECT QUEUE_CODE,QUEUE_DESC,if(REC_STATUS='A','Active','Inactive')as Status , "); 
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
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");//MAKER_DATE
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");//AUTHOR_DATE
				bufInsSql.append(" ?, ");//BRANCH_ID
				bufInsSql.append(" ?, ");//PRODUCT_ID
				bufInsSql.append(" ? ,");//SCHEME_ID
				bufInsSql.append(" ? )");//QUEUE_QUERY
				
				//Change for coll_case_dtl => cr_loan_dtl   @ SUR 30/08/12
				queueQuery.append("SELECT LOAN_ID FROM cr_loan_dtl CCD where 1=1 ");
				
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
				String queryLess=queueCodeMasterVo.getDpdLess();
				if(StringUtils.isBlank(queryLess)){
					queryLess="0";
				}
				queryLess=queryLess.trim();
				insertPrepStmtObject.addString(queryLess);
				if(Integer.parseInt(queryLess)==0){
					queueQuery.append(" AND CCD.LOAN_DPD=0");
				}else{
					queueQuery.append(" AND CCD.LOAN_DPD <='"+queryLess+"' ");
				}
				
				String queryGreater=queueCodeMasterVo.getDpdGreater();
				if(StringUtils.isBlank(queryGreater)){
					queryGreater="0";
				}
				queryGreater=queryGreater.trim();
				insertPrepStmtObject.addString(queryGreater);
				if(Integer.parseInt(queryGreater)>0){
					queueQuery.append(" AND CCD.LOAN_DPD >='"+queryGreater+"' ");
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

			bufInsSql.append(" UPDATE coll_queue_def_m set QUEUE_DESC=?,QUEUE_DESC_L=?,DPD_LES=?,DPD_GRT=?,POS_GRT=?,POS_LES=?,");
			bufInsSql.append(" CUSTOMER_CATEGORY=?,CUSTOMER_TYPE=?,PRIORITY=?,ACTION_DAYS=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)," +
							 "AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),BRANCH_ID=?,PRODUCT_ID=?,SCHEME_ID=?,QUEUE_QUERY=? ");
			bufInsSql.append(" where QUEUE_CODE='" + queueCode + "'");
			
			//Change for coll_case_dtl => cr_loan_dtl  By Arun 
			queueQuery.append("SELECT LOAN_ID FROM cr_loan_dtl CCD where 1=1 ");
			
			
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
			String queryLess=queueCodeMasterVo.getDpdLess();
			if(StringUtils.isBlank(queryLess)){
				queryLess="0";
			}
			queryLess=queryLess.trim();
			insertPrepStmtObject.addString(queryLess);
			if(Integer.parseInt(queryLess)==0){
				queueQuery.append(" AND CCD.LOAN_DPD=0");
			}else{
				queueQuery.append(" AND CCD.LOAN_DPD <='"+queryLess+"' ");
			}

			String queryGreater=queueCodeMasterVo.getDpdGreater();
			if(StringUtils.isBlank(queryGreater)){
				queryGreater="0";
			}
			queryGreater=queryGreater.trim();
			insertPrepStmtObject.addString(queryGreater);
			if(Integer.parseInt(queryGreater)>0){
				queueQuery.append(" AND CCD.LOAN_DPD >='"+queryGreater+"' ");
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
		String qry ="";
		String st = "";
		String str="";
		int count=0;
		String percent1="";
		String branchId="";
		ArrayList searchlist = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		ArrayList data = new ArrayList();
		AllocationMasterVo vo = null;

		ArrayList<AllocationMasterVo> detailList = new ArrayList<AllocationMasterVo>();
		try {

			String allocationId=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getAllocId())).trim());
			logger.info("allocationId.........."+allocationId);
			queueId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getLbxQueuesearchId())).trim());
			logger.info("queueId"+queueId);
			branchId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getLbxBranchIds())).trim());
			String areaCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getLbxareaCodeVal())).trim());
			qry = ("SELECT count(1) FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+queueId+ "'  AND  IFNULL(BRANCH_ID,'')='"+branchId+"' ");
//			if(!areaCode.equalsIgnoreCase(""))
//			{
//				qry=qry+" AND IFNULL(AREA_CODE,'')='"+areaCode+"' ";
//			}
			
			st = ConnectionDAOforEJB.singleReturn(qry);
			
			if(!st.equalsIgnoreCase("")){

			str="select sum(ifnull(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+queueId+"' AND  IFNULL(BRANCH_ID,'')='"+branchId+"' " ;
//            if(!areaCode.equalsIgnoreCase(""))
//            {
//				qry=qry+" AND IFNULL(AREA_CODE,'')='"+areaCode+"' ";
//			}
			
			percent1=ConnectionDAOforEJB.singleReturn(str);
			logger.info("percent1");
			if(percent1.equalsIgnoreCase(""))
			{
				percent1="0";
			}
			
			}else{
				percent1="0";
			}
			
			bufInsSql.append("  Select   CAQM.QUEUE_CODE,cqdm.QUEUE_DESC,CAQM.USER_ID,(Select USER_NAME from sec_user_m SEUM where CAQM.USER_ID=SEUM.USER_ID) AS USER_NAME, CAQM.ALLOCATION_PERCENTAGE,CAQM.ALLOC_ID,caqm.REC_STATUS,a.AREA_CODE_NAME,caqm.AREA_CODE,ALLOCATION_TYPE,");
			bufInsSql.append("  replace(GROUP_CONCAT(CAQBM.branch_id),',','|') from coll_alloc_queue_map CAQM left join coll_alloc_queue_branch_map  CAQBM on CAQBM.ALLOC_ID=CAQM.alloc_id join coll_queue_def_m CQDM on CAQM.QUEUE_CODE=CQDM.QUEUE_CODE " );
			bufInsSql.append("  left join com_areacode_m A on  A.AREA_CODE=CAQM.AREA_CODE where caqm.QUEUE_CODE='"+queueId+"' and CAQM.alloc_id='"+allocationId+"'" );
			//bufInsSql.append("AND IFNULL(BRANCH_ID,'')='"+branchId+"' " );

//			if(!areaCode.equalsIgnoreCase(""))
//			{
//				bufInsSql.append(" AND IFNULL(caqm.AREA_CODE,'')='"+areaCode+"' ");
//			}
//			else{
//				bufInsSql.append(" AND IFNULL(caqm.AREA_CODE,'')='' ");
//			}
			
			//bufInsSql.append(" as Status,CAQM.USER_ID FROM coll_alloc_queue_map CAQM JOIN sec_user_m SCM ON SCM.USER_ID=CAQM.USER_ID"); 
			bufInsSql.append(" group by  CAQM.USER_ID " );
			
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
                    if(CommonFunction.checkNull(data.get(9)).equalsIgnoreCase("OTHER") || CommonFunction.checkNull(data.get(9)).equalsIgnoreCase(""))
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
					
					//vo.setLbxBranchIds(CommonFunction.checkNull(data.get(5)).trim());	
				//	vo.setBranchId(CommonFunction.checkNull(data.get(6)).trim());	
					//vo.setQueueStatus(CommonFunction.checkNull(data.get(4)).toString());
					vo.setTotalRecordSize(count);
					vo.setFlagval("");
					vo.setAllocId(CommonFunction.checkNull(data.get(5)).trim());
					vo.setRecStatus(CommonFunction.checkNull(data.get(6)).trim());
					//Surendra Code Start here...........
					vo.setAreaCode(CommonFunction.checkNull(data.get(7)).trim());
					vo.setLbxareaCodeVal(CommonFunction.checkNull(data.get(8)).trim());
					logger.info("data.get(10)..................."+data.get(8));
					vo.setAllocationType(CommonFunction.checkNull(data.get(9)).trim());
					vo.setLbxBranchIds(CommonFunction.checkNull(data.get(10)).trim());

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
	
			bufInsSql.append(" SELECT CAQM.ALLOC_ID,CAQM.USER_ID,SCM.USER_NAME,CAQM.QUEUE_CODE,CAQM.ALLOCATION_PERCENTAGE, if(CAQM.REC_STATUS='A','Active','Inactive')");
			bufInsSql.append("	as Status FROM coll_alloc_queue_map CAQM JOIN sec_user_m SCM ON SCM.USER_ID=CAQM.USER_ID WHERE CAQM.USER_ID ='"+ userId + "'");
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

public String saveQueueAllocation(AllocationMasterVo collVO,String[] percentage,int total,String[] queueUser,String[] branchList) throws SQLException {
		
		String flag="NOTSAVE";
		ArrayList qryList =  new ArrayList();
		String qry ="";
		String qry1 ="";
		ArrayList st =new ArrayList ();
		ArrayList st2 =new ArrayList ();
		String query1 ="";
		String branch="";
		//Get Group_id
		String groupIdQry="SELECT function_sequence('COLL_GROUP_ID')";
		String groupId=ConnectionDAO.singleReturn(groupIdQry);
		if(StringUtils.isBlank(groupId) || groupId==null){
			groupId="0";
		}
		groupId=groupId.trim();
		if(CommonFunction.checkNull(branchList).length()==0)
		{

			String q1="SELECT GROUP_CONCAT(BRANCH_ID) FROM COM_BRANCH_M WHERE REC_STATUS='A' ";
			branch=ConnectionDAO.singleReturn(q1);
			branchList=branch.split(",");
		
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
		boolean status2=false;
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
		String allocId=collVO.getAllocId();
		
		qry1 = "	SELECT b.branch_id FROM coll_alloc_queue_map a join coll_alloc_queue_branch_map b  on a.alloc_Id=b.alloc_Id and b.REC_STATUS='A' and a.Rec_status='A' WHERE QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "' and b.branch_id in ("+branch+") ";
//		if(!areaCode.equalsIgnoreCase("")){
//			qry1=qry1+ "AND IFNULL(AREA_CODE,'')='"+areaCode+"'";
//		}
//		else {
//			qry1=qry1+ "AND IFNULL(AREA_CODE,'')='"+areaCode+"'";
//		}
		logger.info("qry1------------------------------------------------]>"+qry1);
		st2 = ConnectionDAOforEJB.sqlSelect(qry1);
	logger.info("st::::::::::::::::::::::::::::::::::::::::::::::::>"+st2);
	if(st2.size()>0)
	{
		//richa 
		String activeRetrurn="";
		String branchId="";
		String que=collVO.getLbxQueuesearchId();
		for (int i=0;i<branchList.length;i++){
		
			querySql =new StringBuffer();
			querySql=querySql.append("Select if(a.rec_status='A', 'Active' ,'Inactive') rec_status from coll_alloc_queue_map a join coll_alloc_queue_branch_map b  on a.alloc_Id=b.alloc_Id and b.rec_status='A' where QUEUE_CODE='"+que+"' AND IFNULL(b.BRANCH_ID,'')='"+branchList[i]+"'");
//			if(!areaCode.equalsIgnoreCase("")){
//			querySql.append(" AND IFNULL(AREA_CODE,'')='"+areaCode+"' ");		
//		}
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
       
        }else{
	
			if(!CommonFunction.checkNull(collVO.getAllocId()).equalsIgnoreCase("")){
				insertPrepStmtObject1 = new PrepStmtObject();
				String insertCollQueueBranchDataQry="INSERT INTO COLL_ALLOC_QUEUE_BRANCH_MAP_HISTROY(ALLOC_ID,BRANCH_ID,REC_STATUS) "
				+ " SELECT MAP.ALLOC_ID,MAP.BRANCH_ID,MAP.REC_STATUS"
				+ " FROM COLL_ALLOC_QUEUE_BRANCH_MAP MAP"
				+ " WHERE MAP.ALLOC_ID='"+collVO.getAllocId()+"'";
				insertPrepStmtObject1.setSql(insertCollQueueBranchDataQry);
				ArrayList qryInsList =  new ArrayList();
				qryInsList.add(insertPrepStmtObject1);
				boolean insertStatus=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryInsList);
				logger.info("Insert Collection Queue branch data in histroy table(COLL_ALLOC_QUEUE_BRANCH_MAP_HISTROY) status "+insertStatus);
				//qry = "	update coll_alloc_queue_branch_map set rec_status='X' WHERE ALLOC_ID='"+collVO.getAllocId()+ "' and BRANCH_ID in ("+branch+") ";
				qry = "DELETE FROM coll_alloc_queue_branch_map WHERE ALLOC_ID='"+collVO.getAllocId()+ "'";
				logger.info("qry------------------------------------------------]>"+qry);
				st.add(qry);
				boolean ststus = ConnectionDAOforEJB.sqlInsUpdDelete(st);
				logger.info("st::::::::::::::::::::::::::::::::::::::::::::::::>"+ststus);
			
			}
					for (int i = 0; i < total; i++) {
 
							if (collVO.getRecStatus() != null
							&& collVO.getRecStatus().equalsIgnoreCase("on")) {
						stat = "A";
					} else {
						stat = "X";

					}
					
						bufInsSql =	new StringBuffer();
						 insertPrepStmtObject = new PrepStmtObject();
						 qryList=new ArrayList();
						 bufInsSql.append("insert into coll_alloc_queue_map(user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,maker_id,maker_date,author_id,author_date,AREA_CODE,ALLOCATION_TYPE,GROUP_ID)");
						 bufInsSql.append(" values ( ");
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
						 bufInsSql.append(" ?,");
						 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");
						 bufInsSql.append(" ?, " );
						 bufInsSql.append(" ?," );
						 bufInsSql.append(" ?)" );//GROUP_ID
					
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

							insertPrepStmtObject.addString(groupId);
							
						insertPrepStmtObject.setSql(bufInsSql.toString());
						qryList.add(insertPrepStmtObject);
						
						logger.info("In saveQueueAllocation............query.........."+insertPrepStmtObject.printQuery());
						    status1=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
						    qryList=null;
						    
						    String query="select max(ALLOC_ID) from  coll_alloc_queue_map for update; ";
						    String newallocId=ConnectionDAO.singleReturn(query);
						    if(CommonFunction.checkNull(branchList).length()!=0){
								for (int j = 0; j < branchList.length; j++) {
						    StringBuffer  newbufInsSql =	new StringBuffer();
						    PrepStmtObject newinsertPrepStmtObject = new PrepStmtObject();
						    ArrayList newqryList=new ArrayList();
						    newbufInsSql.append("insert into coll_alloc_queue_branch_map(ALLOC_ID,BRANCH_ID,REC_STATUS) ");
						    newbufInsSql.append(" values ( ");
						    newbufInsSql.append(" ?," );
						    newbufInsSql.append(" ?," );
						    newbufInsSql.append(" ?) " );
							 
							 if(CommonFunction.checkNull(newallocId).equalsIgnoreCase(""))
								 newinsertPrepStmtObject.addNull();
							else
								newinsertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(newallocId).trim()));
							 
							 if(CommonFunction.checkNull(branchList[j]).equalsIgnoreCase(""))
								 newinsertPrepStmtObject.addNull();
							else
								newinsertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(branchList[j]).trim()));
							
							 if(CommonFunction.checkNull(stat).equalsIgnoreCase(""))
									newinsertPrepStmtObject.addNull();
								else
									newinsertPrepStmtObject.addString(stat);
							 
							 newinsertPrepStmtObject.setSql(newbufInsSql.toString());
							 newqryList.add(newinsertPrepStmtObject);
								
								logger.info("In saveQueueAllocation............query.........."+newinsertPrepStmtObject.printQuery());
								    status2=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(newqryList);
						}
						    }
						    if(status1){
						    	flag="SAVE";
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
		String query="";
		String percentage="";
		try{
			int length = checkbox.length;
		for(int i=0;i<length;i++){
			query="select sum(ifnull(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+checkbox[i]+"'";
			logger.info("query"+query);
			percentage=ConnectionDAOforEJB.singleReturn(query);
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
		String str="";
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
							str="select sum(ifnull(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+checkbox[i]+"' and  USER_ID <> '"+collVO.getLbxUserSearchId()+"'";
							percent1=ConnectionDAOforEJB.singleReturn(str);
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
									bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
									bufInsSql.append(" ?,");
									bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");
							
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
		String branchId="";
		
		ArrayList searchlist = new ArrayList();
		AllocationMasterVo vo = (AllocationMasterVo) ob;
		ArrayList<AllocationMasterVo> detailList = new ArrayList<AllocationMasterVo>();
		try {

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			queueCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxQueuesearchId())).trim());
			branchId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchId())).trim());
			
			
			
			logger.info("In searchAllocationData().....................................Dao Impl");
			bufInsSql.append(""
			+" SELECT IFNULL(B.GROUP_ID,0)GROUP_ID,MIN(B.QUEUE_CODE)QUEUE_CODE,MIN(QUEUE.QUEUE_DESC)QUEUE_DESC,GROUP_CONCAT(DISTINCT CONCAT(USR.USER_NAME,' (',ALLOCATION_PERCENTAGE,')') SEPARATOR ' | ')USER_NAME,"
			+" MIN(IF(B.REC_STATUS='A','ACTIVE','INACTIVE')) REC_STATUS,GROUP_CONCAT(DISTINCT CONCAT(BR.BRANCH_DESC,'(',BR.BRANCH_ID,')') SEPARATOR ' | ')BRANCH_DESC,"
			+" MIN(B.ALLOC_ID)ALLOC_ID"
			+" FROM COLL_ALLOC_QUEUE_MAP B  "
			+" JOIN COLL_QUEUE_DEF_M QUEUE ON(B.QUEUE_CODE=QUEUE.QUEUE_CODE) "
			+" JOIN SEC_USER_M USR ON(USR.USER_ID=B.USER_ID) "
			+" JOIN COLL_ALLOC_QUEUE_BRANCH_MAP BRCH ON(B.ALLOC_ID=BRCH.ALLOC_ID) "
			+" JOIN COM_BRANCH_M BR ON(BR.BRANCH_ID=BRCH.BRANCH_ID)");
			if (StringUtils.isNotBlank(branchId)){
				bufInsSql.append(" JOIN (SELECT DISTINCT ALLOC_ID FROM COLL_ALLOC_QUEUE_BRANCH_MAP WHERE BRANCH_ID='" +branchId+ "') FLT ON(FLT.ALLOC_ID=B.ALLOC_ID)");
			}
			bufInsSql.append(" WHERE 1=1 ");
		
			if (!(queueCode.equalsIgnoreCase(""))) {
				bufInsSql.append(" AND B.QUEUE_CODE='" + queueCode + "'");
			}
			if (!CommonFunction.checkNull(vo.getLbxUserSearchId()).equalsIgnoreCase("")) {
				bufInsSql.append(" AND B.USER_ID='" + vo.getLbxUserSearchId() + "'");
			}
			
			bufInsSql.append(" GROUP BY B.GROUP_ID ");
		
			bufInsSqlTempCount.append(" SELECT COUNT(1) FROM ( "+bufInsSql.toString()+" )as b");
			
			logger.info("searchAllocationData Count Query : "+bufInsSqlTempCount.toString());
			
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			if(queueCode.trim()==null  || queueCode.trim().equalsIgnoreCase("") || vo.getCurrentPageLink()>1){
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1){
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
				}
				bufInsSql.append(" ORDER BY B.GROUP_ID ");
				bufInsSql.append(" LIMIT "+startRecordIndex+","+endRecordIndex);
			}
			logger.info("searchAllocationData Main Query : "+bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("searchAllocationData Data Count " + searchlist.size());
			for (int i=0;i<searchlist.size();i++){
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					AllocationMasterVo mVo = new AllocationMasterVo();
					queueCode=CommonFunction.checkNull(data.get(1)).toString();
					queueDesc=CommonFunction.checkNull(data.get(2)).toString();
					mVo.setAlloctionGroupId(CommonFunction.checkNull(data.get(0)).toString());
					mVo.setQueueIdModify("<a href=allocationmasteradd.do?method=openEditAllocation&queueId="+queueCode+"&groupId="+CommonFunction.checkNull(data.get(0)).toString() +"&allocId="+CommonFunction.checkNull(data.get(6)).toString()+">"+queueDesc+" ("+queueCode+") </a>");
					mVo.setQueueId(queueCode);
					mVo.setQueuedesc(queueDesc);
					mVo.setUser(CommonFunction.checkNull(data.get(3)).toString());
					mVo.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
					mVo.setShowbranchName(CommonFunction.checkNull(data.get(5)).toString());
					detailList.add(mVo);
					mVo.setTotalRecordSize(count);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return detailList;
	}

	public ArrayList getresultForBranchAllcation(String allocId) {
		 
		ArrayList searchlist = new ArrayList();
		
		ArrayList<AllocationMasterVo> resultList = new ArrayList<AllocationMasterVo>();

		try {
			logger.info("In getresultForBranchAllcation().....................................Dao Impl....."+allocId);

			StringBuilder bufInsSql = new StringBuilder();
			AllocationMasterVo vo = new AllocationMasterVo();
			
			bufInsSql.append(" select distinct a.branch_id,a.branch_desc  from com_branch_m a join coll_alloc_queue_branch_map b  on  a.branch_id=b.branch_id where  alloc_Id ='"+ allocId+ "'  " );
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

public String modifyQueueAllocationDtl(AllocationMasterVo collVO,String[] percentage,int total,String[] queueUser,String[] branchList) throws SQLException {
		
		String flag="NOTSAVE";
		PrepStmtObject insertPrepStmtObject1=null;
		PrepStmtObject insertPrepStmtObject2=null;
		PrepStmtObject insertPrepStmtObject3=null;
		PrepStmtObject insertPrepStmtObject4=null;
		PrepStmtObject insertPrepStmtObject=null;
		StringBuilder insertCollQueueBranchDataQry=null;
		ArrayList qryInsList=null;
		StringBuffer bufInsSql =null;
		StringBuffer newbufInsSql =	null;
		ArrayList qryList=null;
		PrepStmtObject newinsertPrepStmtObject=null;
		ArrayList newqryList=null;
		ArrayList st2=null;
		
		boolean insertStatus=false;
		
		String recStatus=collVO.getRecStatus();
		if(StringUtils.isBlank(recStatus)){
			recStatus="X";
		}
		recStatus=recStatus.trim();
		try{
			if(StringUtils.equalsIgnoreCase(recStatus,"X")){
				insertPrepStmtObject = new PrepStmtObject();
				qryList=new ArrayList();
				String queryUpdateStatus="UPDATE COLL_ALLOC_QUEUE_MAP SET REC_STATUS='X' WHERE GROUP_ID='"+collVO.getAlloctionGroupId()+"'";
				logger.info("update queryUpdateStatus :"+queryUpdateStatus);
				
				insertPrepStmtObject.setSql(queryUpdateStatus);
				qryList.add(insertPrepStmtObject);
				boolean updateStatus=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				if(updateStatus){
					flag="SAVE";
				}
			}else{
				String selectedBranch="";
				if(branchList!=null){
					for(int i=0;i<branchList.length;i++){
						if(StringUtils.isBlank(selectedBranch)){
							selectedBranch=branchList[i];
						}else{
							selectedBranch=selectedBranch+","+branchList[i];
						}
					}
				}
				
				String branchDesc="";
				String mappedData="0";
				String qry1=""+
				" SELECT GROUP_CONCAT(DISTINCT CONCAT(BR.BRANCH_DESC,' (',BRCH.BRANCH_ID,')')) BRANCH_DESC,COUNT(1)CT"+
				" FROM COLL_ALLOC_QUEUE_MAP MAP "+
				" JOIN COLL_ALLOC_QUEUE_BRANCH_MAP BRCH ON(MAP.ALLOC_ID=BRCH.ALLOC_ID AND MAP.REC_STATUS='A')"+
				" JOIN COM_BRANCH_M BR ON(BR.BRANCH_ID=BRCH.BRANCH_ID) "+
				" WHERE MAP.QUEUE_CODE='"+collVO.getLbxQueuesearchId()+"' AND MAP.GROUP_ID!="+collVO.getAlloctionGroupId()+" AND BR.BRANCH_ID IN("+selectedBranch+") "+
				" GROUP BY MAP.QUEUE_CODE";
				logger.info("In modifyQueueAllocationDtl Check Duplicate record Query : "+qry1);
				st2 = ConnectionDAOforEJB.sqlSelect(qry1);
				if(st2!=null && st2.size()>0){
					ArrayList inr=(ArrayList)st2.get(0);
					if(inr!=null && inr.size()>0){
						branchDesc=(String)inr.get(0);
						mappedData=(String)inr.get(1);
					}
				}
				if(StringUtils.isBlank(mappedData)){
					mappedData="0";
				}
				mappedData=mappedData.trim();
				if(Integer.parseInt(mappedData)==0){
//					String groupIdQry="SELECT function_sequence('COLL_GROUP_ID')";
//					String groupId=ConnectionDAO.singleReturn(groupIdQry);
//					if(StringUtils.isBlank(groupId) || groupId==null){
//						groupId="0";
//					}
//					groupId=groupId.trim();
					
					//take backup of existing data
					insertPrepStmtObject1 = new PrepStmtObject();
					qryInsList=new ArrayList();
					insertCollQueueBranchDataQry=new StringBuilder();
					insertCollQueueBranchDataQry.append(" INSERT INTO COLL_ALLOC_QUEUE_BRANCH_MAP_HISTROY(ALLOC_ID,BRANCH_ID,REC_STATUS)"+
							" SELECT BRCH.ALLOC_ID,BRCH.BRANCH_ID,BRCH.REC_STATUS"+
							" FROM COLL_ALLOC_QUEUE_MAP MAP "+
							" JOIN COLL_ALLOC_QUEUE_BRANCH_MAP BRCH ON(MAP.ALLOC_ID=BRCH.ALLOC_ID) "+
							" WHERE MAP.GROUP_ID="+collVO.getAlloctionGroupId());
					insertPrepStmtObject1.setSql(insertCollQueueBranchDataQry.toString());
					qryInsList.add(insertPrepStmtObject1);
					
					insertPrepStmtObject2 = new PrepStmtObject();
					String deleteQry1="DELETE FROM COLL_ALLOC_QUEUE_BRANCH_MAP "
							+ " WHERE ALLOC_ID IN(SELECT ALLOC_ID FROM COLL_ALLOC_QUEUE_MAP WHERE GROUP_ID='"+collVO.getAlloctionGroupId()+"')";
					insertPrepStmtObject2.setSql(deleteQry1);
					qryInsList.add(insertPrepStmtObject2);
					
					insertPrepStmtObject3 = new PrepStmtObject();
					String mapHistoryQry="INSERT INTO COLL_ALLOC_QUEUE_MAP_HISTORY(USER_ID,QUEUE_CODE,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,MAKER_ID,"+
					" MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,REASON,MVMT_BY,MVMT_DATE,AREA_CODE,ALLOCATION_TYPE,GROUP_ID)"+
					" SELECT USER_ID,QUEUE_CODE,REC_STATUS,ALLOCATION_PERCENTAGE,BRANCH_ID,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,'UPDATED','"+collVO.getMakerId()+"',"+
					" DATE_ADD(STR_TO_DATE('"+collVO.getMakerDate()+"', '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), "+
					" AREA_CODE,ALLOCATION_TYPE,GROUP_ID "+
					" FROM  COLL_ALLOC_QUEUE_MAP "+
					" WHERE GROUP_ID='"+collVO.getAlloctionGroupId()+"'";
					insertPrepStmtObject3.setSql(mapHistoryQry);
					qryInsList.add(insertPrepStmtObject3);
	
					insertPrepStmtObject4 = new PrepStmtObject();
					String deleteMapQry="DELETE FROM COLL_ALLOC_QUEUE_MAP WHERE GROUP_ID='"+collVO.getAlloctionGroupId()+"'";
					insertPrepStmtObject4.setSql(deleteMapQry);
					qryInsList.add(insertPrepStmtObject4);
					
					insertStatus=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryInsList);
					logger.info("In modifyQueueAllocationDtl Take Backup Status : "+insertStatus);
					
					//create mapping record
					bufInsSql=new StringBuffer();
					bufInsSql.append("insert into coll_alloc_queue_map(user_id,queue_code,REC_STATUS,ALLOCATION_PERCENTAGE,maker_id,maker_date,author_id,author_date,AREA_CODE,ALLOCATION_TYPE,GROUP_ID)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
					bufInsSql.append(" ?,");
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");
					bufInsSql.append(" ?, " );
					bufInsSql.append(" ?, " );
					bufInsSql.append(" ?)" );//GROUP_ID
					
					newbufInsSql=new StringBuffer();
					newbufInsSql.append("insert into coll_alloc_queue_branch_map(ALLOC_ID,BRANCH_ID,REC_STATUS) ");
					newbufInsSql.append(" values ( ");
					newbufInsSql.append(" ?," );
					newbufInsSql.append(" ?," );
					newbufInsSql.append(" ?) " );
					
					for(int i = 0; i < total; i++){
						insertStatus=false;
						insertPrepStmtObject=new PrepStmtObject();
						qryList=new ArrayList();
						
						if(CommonFunction.checkNull(queueUser[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(queueUser[i]).trim()));
					
						if(CommonFunction.checkNull(collVO.getLbxQueuesearchId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getLbxQueuesearchId());
					
						insertPrepStmtObject.addString("A");
				
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
	
						if(CommonFunction.checkNull(collVO.getLbxareaCodeVal()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getLbxareaCodeVal());
						
						if(CommonFunction.checkNull(collVO.getAllocationType()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(collVO.getAllocationType());
						
						insertPrepStmtObject.addString(collVO.getAlloctionGroupId());
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						qryList.add(insertPrepStmtObject);
					
						logger.info("In modifyQueueAllocationDtl Map Insert Query : "+insertPrepStmtObject.printQuery());
						
						insertStatus=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
						qryList=null;
					
						if(CommonFunction.checkNull(branchList).length()!=0 && insertStatus){
							String query="select max(ALLOC_ID) from  coll_alloc_queue_map for update; ";
							String newallocId=ConnectionDAO.singleReturn(query);
							
							for (int j = 0; j < branchList.length; j++) {
								
								newinsertPrepStmtObject = new PrepStmtObject();
								newqryList=new ArrayList();
	
								if(CommonFunction.checkNull(newallocId).equalsIgnoreCase(""))
									 newinsertPrepStmtObject.addNull();
								else
									newinsertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(newallocId).trim()));
								
								if(CommonFunction.checkNull(branchList[j]).equalsIgnoreCase(""))
									 newinsertPrepStmtObject.addNull();
								else
									newinsertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(branchList[j]).trim()));
								
								newinsertPrepStmtObject.addString("A");
	
								newinsertPrepStmtObject.setSql(newbufInsSql.toString());
								newqryList.add(newinsertPrepStmtObject);
							
								logger.info("In modifyQueueAllocationDtl Branch Map Insert Query : "+newinsertPrepStmtObject.printQuery());
								boolean status3=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(newqryList);
							}
						}
					}
					if(insertStatus){
						flag="SAVE";
					}
				}else{
					//error block
					flag="Data already exists with Queue Code:-"+collVO.getLbxQueuesearchId()+" and Branch:-"+branchDesc+"";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			insertPrepStmtObject1=null;
			insertPrepStmtObject2=null;
			insertPrepStmtObject3=null;
			insertPrepStmtObject4=null;
			insertPrepStmtObject=null;
			qryInsList=null;
			bufInsSql =null;
			newbufInsSql=null;
			qryList=null;
			newinsertPrepStmtObject=null;
			newqryList=null;
			st2=null;
		}
		return flag;
	}



	@Override
	public ArrayList<AllocationMasterVo> allocatedCollQueueData(AllocationMasterVo allocationVo) {
		ArrayList<AllocationMasterVo> detailList = new ArrayList<AllocationMasterVo>();
		StringBuilder bufInsSql=new StringBuilder();
		AllocationMasterVo vo = null;
		try {
			String groupId=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getGroupId())).trim());
			String allocationId=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getAllocId())).trim());
			logger.info("allocationId.........."+allocationId);
			String queueId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(allocationVo.getLbxQueuesearchId())).trim());
			logger.info("queueId"+queueId);
			bufInsSql.append(" SELECT CAQM.USER_ID,SEC.USER_NAME,CAQM.ALLOCATION_PERCENTAGE,CAQM.ALLOC_ID,CQDM.QUEUE_DESC,A.AREA_CODE_NAME,CAQM.ALLOCATION_TYPE,"
					+ "CASE SEC.REC_STATUS WHEN 'A' THEN 'Active' ELSE 'Inactive' END USER_STATUS");
			bufInsSql.append(" FROM  COLL_ALLOC_QUEUE_MAP CAQM");
			//bufInsSql.append(" JOIN SEC_USER_M SEC ON (CAQM.USER_ID=SEC.USER_ID AND SEC.REC_STATUS='A')");
			bufInsSql.append(" JOIN SEC_USER_M SEC ON (CAQM.USER_ID=SEC.USER_ID)");
			bufInsSql.append(" JOIN COLL_QUEUE_DEF_M CQDM ON CAQM.QUEUE_CODE=CQDM.QUEUE_CODE");
			bufInsSql.append(" LEFT JOIN COM_AREACODE_M A ON  A.AREA_CODE=CAQM.AREA_CODE");
			bufInsSql.append(" WHERE CAQM.QUEUE_CODE='"+queueId+"' AND CAQM.GROUP_ID='"+groupId+"'");
			bufInsSql.append(" GROUP BY  CAQM.USER_ID");
			logger.info("query : "+bufInsSql);
			ArrayList searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
			logger.info("allocatedCollQueueData " +size);
			if(searchlist.size()>0){
				for (int i = 0; i < size; i++) {
					logger.info("allocatedCollQueueData " + searchlist.get(i).toString());
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0){
						vo = new AllocationMasterVo();
						vo.setUserId(CommonFunction.checkNull(data.get(0)).toString());
						vo.setLbxUserSearchId(CommonFunction.checkNull(data.get(1)).toString());
						if(!CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("")){
							Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2))).trim());
							vo.setAllocationpercentage(myFormatter.format(percentage));
						}
						else{
							vo.setAllocationpercentage(CommonFunction.checkNull(data.get(2)).trim());	
						}
						vo.setAllocId(CommonFunction.checkNull(data.get(3)).trim());
						vo.setAllocationId(CommonFunction.checkNull(data.get(3)).trim());
						vo.setUserStatus(CommonFunction.checkNull(data.get(7)).trim());
						detailList.add(vo);
					}
				}
			}else{
				vo = new AllocationMasterVo();
				vo.setFlagval("Noval");
				logger.info("queueId : "+queueId);
				vo.setLbxQueuesearchId(CommonFunction.checkNull(queueId));
				logger.info("Queuedesc : "+allocationVo.getQueueIdSearch());
				vo.setQueuedesc(CommonFunction.checkNull(allocationVo.getQueueIdSearch()));
				detailList.add(vo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
			return detailList;
		}
}
