package com.caps.daoImplMSSQL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;

import com.VO.CustomerSaveVo;
import com.caps.VO.ActionCodeMasterVo;
import com.caps.VO.AllocationMasterVo;
import com.caps.VO.CRFollowUpTrailsDtlVO;
import com.caps.VO.ClassificationProcessVo;
import com.caps.VO.CollCommonVO;
import com.caps.VO.CollCustomerAddressVo;
import com.caps.VO.CollectionSummaryVO;
import com.caps.VO.ContactRecordingFollowUpVO;
import com.caps.VO.ContactRecordingSearchVO;
import com.caps.VO.EscalationMatrixVo;
import com.caps.VO.PaymentDetailsVo;
import com.caps.VO.ForeClosureVo;
import com.caps.VO.QueueCodeMasterVo;
import com.caps.VO.ReallocationGridVo;
import com.caps.VO.ReallocationMasterVo;
import com.caps.VO.ViewpayableRecievableVo;
import com.caps.dao.CollDAO;
import com.cm.vo.ClosureVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.utils.Mail;


public class MSSQLCollDAOImpl implements CollDAO{
	
	private static final Logger logger = Logger.getLogger(MSSQLCollDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");

	
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	//Anil Code Starts Here
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

			bufInsSql.append("SELECT ACTION_CODE,ACTION_DESC,case a.REC_STATUS when 'A' then 'Active' when 'X' then 'Inactive' end as Status FROM COLL_ACTION_CODE_M ");
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
			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

	       /*if ((codeId.trim() == null && codeDesc.trim() == null) || (codeId.trim().equalsIgnoreCase("") && codeDesc.trim().equalsIgnoreCase(""))
					|| actionCodeMasterVo.getCurrentPageLink() > 1) {*/

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
			
			//}
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

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
		boolean st = ConnectionDAO.checkStatus(query);
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
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				
				
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
				
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
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
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			
			
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
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
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

	
	
	//Code For (Collection) Queue Code Master 
	
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

		
			bufInsSql.append("SELECT QUEUE_CODE,QUEUE_DESC,case a.REC_STATUS when 'A' then 'Active' when 'X' then 'Inactive' end as Status ,PRIORITY " );
			
			bufInsSql.append("from coll_queue_def_m ");
			
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
        count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		
		/*if((queueSearchCode.trim()==null && queueSearchDesc.trim()==null) || (queueSearchCode.trim().equalsIgnoreCase("") && queueSearchDesc.trim().equalsIgnoreCase("")) || VO.getCurrentPageLink()>1)
		{*/
		
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
			
			

		//}
		logger.info("query : "+bufInsSql);
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		
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
		
		bufInsSql.append(" SELECT QUEUE_CODE,QUEUE_DESC,case a.REC_STATUS when 'A' then 'Active' when 'X' then 'Inactive' end as Status , "); 
		bufInsSql.append(" DPD_GRT,DPD_LES,POS_GRT,POS_LES,CUSTOMER_CATEGORY,");
		bufInsSql.append("CUSTOMER_TYPE,PRIORITY,ACTION_DAYS,(select b.BRANCH_DESC from  com_branch_m b where b.BRANCH_ID = a.BRANCH_ID) as Branch,");
		bufInsSql.append("(select c.PRODUCT_DESC from  cr_product_m c where c.PRODUCT_ID = a.PRODUCT_ID) as Product,");
		bufInsSql.append("(select d.SCHEME_DESC from  cr_scheme_m d where d.SCHEME_ID = a.SCHEME_ID) as Scheme,BRANCH_ID,PRODUCT_ID,SCHEME_ID FROM coll_queue_def_m a ");
	
		 if (!queue.equals("")) {
				bufInsSql.append(" where QUEUE_CODE = '" + queue + "' ");
			
			}

		logger.info("search Query...." + bufInsSql.toString());
		logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
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

	
	
	public ArrayList<QueueCodeMasterVo> NPAStageList()
	{ 
			ArrayList<QueueCodeMasterVo> npaStageList =new  ArrayList<QueueCodeMasterVo>();
			
			ArrayList searchList=new ArrayList();
			ArrayList StageList=new ArrayList();
			QueueCodeMasterVo VO=null;
			try{
	              String query="select  NPA_STAGE from cr_npa_m where REC_STATUS='A'";
	              logger.info("In npastage......Dao Impl"+query);
					
	              searchList=ConnectionDAO.sqlSelect(query);
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
				  
				  searchList=ConnectionDAO.sqlSelect(query);
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

	
	public ArrayList<QueueCodeMasterVo> ProductList()
	{
		
		  ArrayList<QueueCodeMasterVo> productList=new  ArrayList<QueueCodeMasterVo>();
		  
		  ArrayList searchList=new ArrayList();
		  ArrayList ProdList=new ArrayList();
		  QueueCodeMasterVo VO=null;
			try{
	            String query="select PRODUCT_ID,PRODUCT_DESC from cr_product_m where REC_STATUS='A' ";		
				logger.info("In ProductList......Dao Impl:::"+query);
				
				searchList=ConnectionDAO.sqlSelect(query);
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
			count=ConnectionDAO.singleReturn(query);
			logger.info("In checkQueueCodeMaster........count " + count);
			
	        if (count.equalsIgnoreCase("0")) {
			query1 = "SELECT count(1) FROM coll_queue_def_m where PRIORITY='"+queueCodeMasterVo.getPriority() +"' and REC_STATUS='A' ";
			count1=ConnectionDAO.singleReturn(query1);
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
			count1=ConnectionDAO.singleReturn(query1);
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
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				
				bufInsSql.append(" ?, ");//BRANCH_ID
				bufInsSql.append(" ?, ");//PRODUCT_ID
				bufInsSql.append(" ? ,");//SCHEME_ID
				bufInsSql.append(" ? )");//QUEUE_QUERY
				
				
				queueQuery.append("SELECT LOAN_ID FROM  coll_case_dtl CCD left join  " +
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
				queueQuery.append(" AND CCD.LOAN_OVERDUE_PRINCIPAL >='"+queueCodeMasterVo.getPosGreater()+"' ");
				}
				if (CommonFunction.checkNull(queueCodeMasterVo.getPosLess()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else{
			   insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(queueCodeMasterVo.getPosLess().toUpperCase().trim())).toString()); 
				queueQuery.append(" AND CCD.LOAN_OVERDUE_PRINCIPAL <='"+queueCodeMasterVo.getPosLess()+"' ");
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
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
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
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			
			bufInsSql.append("BRANCH_ID=?,PRODUCT_ID=?,SCHEME_ID=?,QUEUE_QUERY=? ");
			bufInsSql.append(" where QUEUE_CODE='" + queueCode + "'");

			queueQuery.append("SELECT LOAN_ID FROM  coll_case_dtl CCD left join  " +
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
			
				queueQuery.append(" AND CCD.LOAN_OVERDUE_PRINCIPAL >='"+queueCodeMasterVo.getPosGreater()+"' ");
			}
			if (CommonFunction.checkNull(queueCodeMasterVo.getPosLess()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else{
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(queueCodeMasterVo.getPosLess().toUpperCase().trim())).toString());
			queueQuery.append(" AND CCD.LOAN_OVERDUE_PRINCIPAL <='"+queueCodeMasterVo.getPosLess()+"' ");
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

			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
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
   
	
	public String queueActionDate(String curreentdate) {

		String count = "";
		StringBuilder query = new StringBuilder();
		try {
			query.append("select count(1) from coll_case_dtl CCD join coll_queue_def_m CQDM ON CCD.QUEUE_CODE=CQDM.QUEUE_CODE where CQDM.REC_STATUS='A' AND ");
			query.append(dbo);
			query.append("STR_TO_DATE('"+curreentdate+"','"+dateFormat+"') > ");
			query.append(dbo);
			query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				
			
			logger.info("In queueactiondate......Dao Impl:::::::::" + query.toString());
			count = ConnectionDAO.singleReturn(query.toString());
			logger.info("In queueactiondate......Dao Impl::::::" + count);

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query=null;
		}
		return count;
	}
	//Anil Code Ends Here

	//KANIKA CODE FOR ALLOCATION
	
	//Ritu Code Start
	public ArrayList<AllocationMasterVo> searchAllocationData(Object ob,HttpServletRequest request) {
		String queueCode = "";
		String queueDesc = "";
		String allocationId = "";
		String query1="";
		Connection con=null;
		ResultSet rs2 = null;
		Statement stmt = null;
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
			
			bufInsSql.append("SELECT QUEUE_CODE,(SELECT A.QUEUE_DESC FROM coll_queue_def_m A WHERE A.QUEUE_CODE=B.QUEUE_CODE ) AS QUEUE_DESC,");
			bufInsSql.append("USER_ID,(SELECT A.USER_NAME FROM SEC_USER_M A WHERE A.USER_ID=B.USER_ID),ALLOCATION_PERCENTAGE " );
			bufInsSql.append("FROM coll_alloc_queue_map B");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM coll_alloc_queue_map ");
			
			if (!(queueCode.equalsIgnoreCase(""))) {
				bufInsSql.append(" WHERE QUEUE_CODE='" + queueCode + "'");
				bufInsSqlTempCount.append(" WHERE QUEUE_CODE='" + queueCode + "'");
			}
			
			logger.info("search Query...." + bufInsSql);
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			//if(queueCode.trim()==null  || queueCode.trim().equalsIgnoreCase("") || vo.getCurrentPageLink()>1)
			//{
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			
			 
			bufInsSql.append(" ORDER BY QUEUE_CODE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search searchAllocationData query for SQL SERVER : " + bufInsSql.toString());

			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
			//}
			logger.info("query : "+bufInsSql);
						
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			logger.info("searchAgencyData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					AllocationMasterVo mVo = new AllocationMasterVo();

					mVo.setQueueIdModify("<a href=allocationmasteradd.do?method=openEditAllocation&queueId="+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0))
											.toString() + "</a>");

					// mVo.setAgencyCode("<a href="#" onclick="modifyDetails(CommonFunction.checkNull(data.get(0)).toString());" ></a>");
					//mVo.setAllocationId(CommonFunction.checkNull(data.get(0)).toString());
					mVo.setQueueId(CommonFunction.checkNull(data.get(0)).toString());
					mVo.setQueuedesc(CommonFunction.checkNull(data.get(1)).toString());
					mVo.setLbxUserSearchId(CommonFunction.checkNull(data.get(2)).toString());
					mVo.setUser(CommonFunction.checkNull(data.get(3)).toString());
					mVo.setPercent(CommonFunction.checkNull(data.get(4)).toString());
					detailList.add(mVo);
					mVo.setTotalRecordSize(count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(searchlist.size()==0)
		{
			AllocationMasterVo mVo = new AllocationMasterVo();
			mVo.setTotalRecordSize(count);
			detailList.add(mVo);
			request.setAttribute("flag","yes");
			logger.info("Detail List when searchList is null: "+detailList.size());
		}
		return detailList;
	}
	//Ritu Code End
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
		     mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
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
		StringBuilder str= new StringBuilder();
		int count=0;
		String percent1="";
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

			qry = ("SELECT count(1) FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+queueId+ "'");
			
			st = ConnectionDAO.singleReturn(qry);
			if(!st.equalsIgnoreCase("")){

			str.append("select sum(");
		 	str.append("ISNULL(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+queueId+"'");
			percent1=ConnectionDAO.singleReturn(str.toString());
			logger.info("percent1");
			if(percent1.equalsIgnoreCase(""))
			{
				percent1="0";
			}
			
			}else{
				percent1="0";
			}
			
			bufInsSql.append(" Select QUEUE_CODE,(Select QUEUE_DESC from coll_queue_def_m CQDM where CQDM.QUEUE_CODE=CAQM.QUEUE_CODE) AS QUEUE_DESC,CAQM.USER_ID,(Select USER_NAME from sec_user_m SEUM where CAQM.USER_ID=SEUM.USER_ID) AS USER_NAME,");
			bufInsSql.append(" CAQM.ALLOCATION_PERCENTAGE from coll_alloc_queue_map CAQM where QUEUE_CODE='"+queueId+"'");
			//bufInsSql.append(" as Status,CAQM.USER_ID FROM coll_alloc_queue_map CAQM JOIN sec_user_m SCM ON SCM.USER_ID=CAQM.USER_ID"); 

			
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
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
					vo.setUserId(CommonFunction.checkNull(data.get(2)).toString());
                    vo.setLbxUserSearchId(CommonFunction.checkNull(data.get(3)).toString());
					if(!CommonFunction.checkNull(data.get(4)).equalsIgnoreCase(""))
    	    		{
    	    		Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
    	    		vo.setAllocationpercentage(myFormatter.format(percentage));
    	    		}
					else{
						vo.setAllocationpercentage(CommonFunction.checkNull(data.get(4)).trim());	
					}
					//vo.setQueueStatus(CommonFunction.checkNull(data.get(4)).toString());
					vo.setTotalRecordSize(count);
					vo.setFlagval("");
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
	
			bufInsSql.append(" SELECT CAQM.ALLOC_ID,CAQM.USER_ID,SCM.USER_NAME,CAQM.QUEUE_CODE,CAQM.ALLOCATION_PERCENTAGE, case CAQM.REC_STATUS when 'A' then 'Active' when 'X' then 'Inactive' end as Status");
			bufInsSql.append(" FROM coll_alloc_queue_map CAQM JOIN sec_user_m SCM ON SCM.USER_ID=CAQM.USER_ID WHERE CAQM.USER_ID ='"+ userId + "'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
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
		String st ="";
		String query1 ="";
		boolean status1=false;
		ArrayList qrydelete=new ArrayList ();
		StringBuffer bufInsSql =null;
		PrepStmtObject insertPrepStmtObject = null;	
		boolean st1=false;
		String stat = "A";

		 insertPrepStmtObject = new PrepStmtObject();
		logger.info("In saveQueueAllocation...Dao Impl.");
		
		try{
		
			qry = ("SELECT count(1) FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "'");
			
			st = ConnectionDAO.singleReturn(qry);
		
			
			if(!(st.equalsIgnoreCase("0")))
			{
			logger.info("delete query");
			query1 = "DELETE FROM coll_alloc_queue_map WHERE QUEUE_CODE='"+collVO.getLbxQueuesearchId()+ "'";
			insertPrepStmtObject.setSql(query1);
			qryList.add(insertPrepStmtObject);
			
			//st1 = ConnectionDAO.sqlInsUpdDelete(qryList);
		
			}
			
				
				for (int i = 0; i < total; i++) {
//					String str="select sum(ISNULL(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+collVO.getLbxQueuesearchId()+"'";
//					String percent1=ConnectionDAO.singleReturn(str);
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
					logger.info(" for insert");
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
						 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						 bufInsSql.append(" ?,");
						 bufInsSql.append(dbo);
						 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
											
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

							
						insertPrepStmtObject.setSql(bufInsSql.toString());
						qryList.add(insertPrepStmtObject);

				}
				//}		
				if(flag.equalsIgnoreCase("F")){
					flag="PERCENT";
				}else{
			    logger.info("In saveQueueAllocation............query.........."+qryList.toString());
			    status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    if(status1){
			    	flag="SAVE";
			    }
	              logger.info("In saveQueueAllocation......................"+flag);
	               
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

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
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
		StringBuilder query= new StringBuilder();
		String percentage="";
		try{
			int length = checkbox.length;
		for(int i=0;i<length;i++){
			query.append("select sum(");
		 	query.append("ISNULL(ALLOCATION_PERCENTAGE,0)) as percentage from coll_alloc_queue_map where  QUEUE_CODE ='"+checkbox[i]+"'");
			logger.info("query"+query.toString());
			percentage=ConnectionDAO.singleReturn(query.toString());
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
		StringBuilder str= new StringBuilder();
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
							percent1=ConnectionDAO.singleReturn(str.toString());
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
								 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
								 bufInsSql.append(" ?,");
								 bufInsSql.append(dbo);
								 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
									
									
																
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
					    status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
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
	//END OF ALLOCATION
	//KANIKA CODE FOR REALLOCATION

	
	public ArrayList<ReallocationMasterVo> npastage(){

	  ArrayList<ReallocationMasterVo> npaStageList =new  ArrayList<ReallocationMasterVo>();
	  
		ArrayList mainList=new ArrayList();
		ArrayList subList=new ArrayList();
		String query="";
		ReallocationMasterVo reallocVo=null;
		try{
			 
              query="select  NPA_STAGE from cr_npa_m where REC_STATUS='A'";
              logger.info("In npastage......Dao Impl"+query);
              mainList = ConnectionDAO.sqlSelect(query);
              int size = mainList.size();
              for(int i=0;i<size;i++){
               
                subList=(ArrayList) mainList.get(i);

               if(subList.size()>0){
					reallocVo=new ReallocationMasterVo();
				
					reallocVo.setNpastageid(subList.get(0).toString());
					reallocVo.setNpastageval(subList.get(0).toString());
					npaStageList.add(reallocVo);
				}
		}
				  logger.info("In npastage......Dao Impl"+npaStageList.size());
			  
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			mainList=null;
			subList=null;
			query=null;
			reallocVo=null;
		}
		return npaStageList;
}

	
	public ArrayList<ReallocationMasterVo> custcategory(){

	int count=0;
	ArrayList mainList=new ArrayList();
	ArrayList subList=new ArrayList();
	String query="";
	ReallocationMasterVo reallocVo=null;
	
	 ArrayList<ReallocationMasterVo> custcatList=new  ArrayList<ReallocationMasterVo>();
		try{	
              query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='CUST_CATEGORY' and rec_status ='A'";              		
			  logger.info("In custcategory......Dao Impl"+query);
			    mainList = ConnectionDAO.sqlSelect(query);
			    int size = mainList.size();
			  for(int i=0;i<size;i++){
			   subList=(ArrayList) mainList.get(i);
			   if(subList.size()>0){
					reallocVo=new ReallocationMasterVo();
					reallocVo.setCstcat(subList.get(0).toString());
					reallocVo.setCstcatval(subList.get(1).toString());
					custcatList.add(reallocVo);
				}
			  }
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			mainList=null;
			subList=null;
			query=null;
			reallocVo=null;
		}
		return custcatList;
}
	
	public ArrayList<ReallocationMasterVo>  product(){

	 ArrayList<ReallocationMasterVo> productList=new  ArrayList<ReallocationMasterVo>();
	 
	 ArrayList mainList=new ArrayList();
		ArrayList subList=new ArrayList();
		 String query="";
		 ReallocationMasterVo reallocVo=null;
		try{
              query="select PRODUCT_ID,PRODUCT_DESC from cr_product_m where REC_STATUS='A'";	
			  logger.info("In product......Dao Impl"+query);
			  mainList = ConnectionDAO.sqlSelect(query);
			  for(int i=0;i<mainList.size();i++){
				  subList=(ArrayList) mainList.get(i);
				  if(subList.size()>0){
					reallocVo=new ReallocationMasterVo();
				
					reallocVo.setProductid(subList.get(0).toString());
					reallocVo.setProductval(subList.get(1).toString());
					productList.add(reallocVo);
				}
			  }
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			 mainList=null;
				subList=null;
				 query=null;
				 reallocVo=null;
		}
		return productList;
}

	public ArrayList <ReallocationGridVo> reallocationGrid(ReallocationMasterVo vo)
{
    logger.info("In reallocationGrid....");   
String loanNo="";
String customerName="";
String product="";
String queue="";
String pos1="";
String pos2="";
String dpd1="";
String dpd2="";
String user="";
String loginuser="";
String npastage="";
String customertype="";
String customercategory="";
String assigndatefrom="";
String assigndateto="";
String balanceprincipal="";
StringBuilder bufInsSql =null;
StringBuilder bufInsSqlTempCount=null;
int startRecordIndex=0;
int endRecordIndex = no;
int count=0;
ArrayList searchlist=new ArrayList();
ArrayList countlist=new ArrayList();
StringBuilder lppChargeQuery= new StringBuilder();
StringBuilder cbcquery= new StringBuilder();
StringBuilder bccQuery=new StringBuilder();
String LPP="";
String CBC="";
String otherCharge="";
String branch="";
String dueDate="";
ArrayList data=new ArrayList();
ReallocationGridVo rVO = null;

ArrayList<ReallocationGridVo> detailListGrid=new 	ArrayList<ReallocationGridVo>();

	  try{
	
      logger.info("In reallocationGrid....................");
      boolean appendSQL=false;
      bufInsSql =	new StringBuilder();
      bufInsSqlTempCount =	new StringBuilder();
      if(!CommonFunction.checkNull(vo.getDpd1()).equalsIgnoreCase(""))
   	    {
      dpd1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd1()).trim())).toString();
      logger.info("In reallocationGrid......dpd1  "+dpd1);         
   	    }
      if(!CommonFunction.checkNull(vo.getDpd2()).equalsIgnoreCase(""))
 	    {
    	  dpd2= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd2()).trim())).toString();
    logger.info("In reallocationGrid......dpd2  "+dpd2);         
 	    }
      if(!CommonFunction.checkNull(vo.getPos1()).equalsIgnoreCase(""))
 	    {
    	  pos1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos1()).trim())).toString();
    logger.info("In reallocationGrid......pos1  "+pos1);         
 	    }
      if(!CommonFunction.checkNull(vo.getPos2()).equalsIgnoreCase(""))
 	    {
    	  pos2= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos2()).trim())).toString();
    logger.info("In reallocationGrid......pos2  "+pos2);         
 	    }
      if(!CommonFunction.checkNull(vo.getBalanceprincipal()).equalsIgnoreCase(""))
	    {
    	  balanceprincipal= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBalanceprincipal()).trim())).toString();
  logger.info("In reallocationGrid......balanceprincipal  "+balanceprincipal);         
	    }

      loanNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanno()).trim());
      customerName=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim());
      product=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getProduct()).trim());
      loginuser=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim());
      user=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxUserSearchId()).trim());
      npastage=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNpastage()).trim());
      customertype=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustype()).trim());
      customercategory=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustcategory()).trim());
      logger.info("vo.getCustcategory()"+vo.getCustcategory());         
     
      queue=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxQueuesearchId()).trim());
      assigndateto=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAssignto()).trim());
      assigndatefrom=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAssignfrom()).trim());

       lppChargeQuery.append("Select  (");
       lppChargeQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
	   lppChargeQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
	   lppChargeQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) LPP");
       lppChargeQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+loanNo+"' AND ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109'");
       cbcquery.append("Select  (");
	   cbcquery.append("ISNULL(ADVICE_AMOUNT,0)-");
	   cbcquery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		cbcquery.append("ISNULL(AMOUNT_IN_PROCESS,0)) CBC");
       cbcquery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+loanNo+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110'");
       bccQuery.append("Select  (");
		bccQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
		bccQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		bccQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) OTHER_CHARGE");
       bccQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+loanNo+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' ");
	   bccQuery.append(" AND CHARGE_CODE_ID NOT IN ('109','110','7')");
		LPP=CommonFunction.checkNull(ConnectionDAO.singleReturn(lppChargeQuery.toString()));
		CBC=CommonFunction.checkNull(ConnectionDAO.singleReturn(cbcquery.toString()));
		otherCharge=CommonFunction.checkNull(ConnectionDAO.singleReturn(bccQuery.toString()));
		if(LPP.equalsIgnoreCase("")){
		LPP="0";	
		}
		if(CBC.equalsIgnoreCase("")){
		CBC="0";		
		}
		if(otherCharge.equalsIgnoreCase("")){
		otherCharge="0";
		}
	     String sumCharges=CommonFunction.checkNull(Double.parseDouble(CBC)+Double.parseDouble(LPP)+Double.parseDouble(otherCharge));
	     logger.info("LPP:---"+LPP)  ;
	     logger.info("CBC:---"+CBC)  ;
	     logger.info("otherCharge:---"+otherCharge)  ;
	     logger.info("sumCharges:---"+sumCharges)  ;
	  
	     bufInsSql.append("Select CLD.LOAN_NO,CLD.LOAN_DPD,gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,");
		 bufInsSql.append("ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+");
		 bufInsSql.append("ISNULL('"+sumCharges+"',0) as activePayment ,");
		 bufInsSql.append(" CCD.QUEUE_CODE,(select USER_NAME from sec_user_m where USER_ID= CCD.USER_ID)as users,");
		 bufInsSql.append(dbo);
		 bufInsSql.append("DATE_FORMAT(ALLOCATION_DATE,'"+ dateFormat + "') as ALLOCATION_DATE,");
		 bufInsSql.append(dbo);
		 bufInsSql.append("DATE_FORMAT(QUEUE_DATE,'"+ dateFormat + "')as QUEUE_DATE ,CLD.LOAN_ID " );
		 bufInsSql.append("from cr_loan_dtl CLD left join coll_case_dtl CCD ");
		 bufInsSql.append(" ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
		 bufInsSql.append(" where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' and CCD.USER_ID in(select USER_ID from user_group_reporting where PARENT_USER_ID='"+loginuser+"' and USER_ID<>'"+loginuser+"') ");
		 
		
		bufInsSqlTempCount.append("Select count(1) from (Select CLD.LOAN_NO,CLD.LOAN_DPD,gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,");
		bufInsSqlTempCount.append("ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+");
		bufInsSqlTempCount.append("ISNULL('"+sumCharges+"',0) as activePayment ,");
		bufInsSqlTempCount.append("CCD.QUEUE_CODE,(select USER_NAME from sec_user_m where USER_ID= CCD.USER_ID)as users,");
		bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("DATE_FORMAT(ALLOCATION_DATE,'"+ dateFormat + "') AS ALLOCATION_DATE,");
		bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("DATE_FORMAT(QUEUE_DATE,'"+ dateFormat + "')AS QUEUE_DATE  ,CLD.LOAN_ID from cr_loan_dtl CLD left join coll_case_dtl CCD ");
		bufInsSqlTempCount.append(" ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
		bufInsSqlTempCount.append(" where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' and CCD.USER_ID in(select USER_ID from user_group_reporting where PARENT_USER_ID='"+loginuser+"' and USER_ID<>'"+loginuser+"') ");
		
		
		
		if((!(loanNo.trim().equalsIgnoreCase("")))) {
	         bufInsSql.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
	         bufInsSqlTempCount.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
	    	 }
	 	if((!(assigndatefrom.trim().equalsIgnoreCase("")))) {
	 	  bufInsSql.append(" AND ");
	 	  bufInsSql.append(dbo);
	 	  bufInsSql.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+assigndatefrom+"' ");
	 	  bufInsSqlTempCount.append(" AND ");
	 	  bufInsSqlTempCount.append(dbo);
	 	  bufInsSqlTempCount.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+assigndatefrom+"' ");
	      }
	 	if((!(assigndateto.trim().equalsIgnoreCase("")))) {
	   	   bufInsSql.append(" AND ");
	   	   bufInsSql.append(dbo);
	   	   bufInsSql.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+assigndateto+"' ");
	       bufInsSqlTempCount.append(" AND ");
	       bufInsSqlTempCount.append(dbo);
	       bufInsSqlTempCount.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+assigndateto+"' ");
	     }
	    if((!(customerName.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND gcm.CUSTOMER_NAME like '%"+customerName+"%'  ");
	    	  bufInsSqlTempCount.append(" AND gcm.CUSTOMER_NAME like '%"+customerName+"%'  ");
	      }
		if((!(product.trim().equalsIgnoreCase("")))) {
  	  bufInsSql.append(" AND CLD.LOAN_PRODUCT='"+product+"' ");
  	  bufInsSqlTempCount.append(" AND CLD.LOAN_PRODUCT='"+product+"' ");
        }
		if((!(dpd2.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_DPD >= '"+dpd2+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_DPD >= '"+dpd2+"' ");
	      }
		if((!(dpd1.trim().equalsIgnoreCase("")))) {
  	  bufInsSql.append(" AND CLD.LOAN_DPD <= '"+dpd1+"' ");
  	  bufInsSqlTempCount.append(" AND CLD.LOAN_DPD <= '"+dpd1+"' ");
        }
			
		if((!(user.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CCD.USER_ID ='"+user+"' ");
	    	  bufInsSqlTempCount.append(" AND CCD.USER_ID ='"+user+"' ");
	      }
		if((!(pos2.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '"+pos2+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '"+pos2+"' ");
	      }
		if((!(pos1.trim().equalsIgnoreCase("")))) {
  	  bufInsSql.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+pos1+"' ");
  	  bufInsSqlTempCount.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+pos1+"' ");
        }
		
		if((!(customertype.trim().equalsIgnoreCase("")))) {
  	  bufInsSql.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
  	  bufInsSqlTempCount.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
       }
		if((!(customercategory.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND gcm.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
	    	  bufInsSqlTempCount.append(" AND gcm.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
	      }
		if((!(balanceprincipal.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
	       }
		if((!(queue.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
	    	  bufInsSqlTempCount.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
	      }
			bufInsSqlTempCount.append(" ) as t ");
			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
		  
			/*if(( loanNo.trim()=="" && customerName.trim()==""&& 
					product.trim()=="" && queue.trim()==""&& 
					 user.trim()=="" && npastage.trim()==""&& 
					 customertype.trim()=="" && customercategory.trim()==""&& 
					 balanceprincipal.trim()=="" &&  assigndateto.trim()==""&& 
					 loanNo.trim()=="" && customerName.trim()==""&& 
					 assigndatefrom.trim()=="" ) 
					 || (loanNo.trim().equalsIgnoreCase("") && customerName.trim().equalsIgnoreCase("")||
							 product.trim().equalsIgnoreCase("") && queue.trim().equalsIgnoreCase("")||
							 user.trim().equalsIgnoreCase("") && npastage.trim().equalsIgnoreCase("")||
							 customertype.trim().equalsIgnoreCase("") && customercategory.trim().equalsIgnoreCase("")||
							 balanceprincipal.trim().equalsIgnoreCase("") &&  assigndateto.trim().equalsIgnoreCase("")||
							 assigndatefrom.trim().equalsIgnoreCase("") ) 
					 || vo.getCurrentPageLink()>1)
			{*/
		
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				
					if(vo.getCurrentPageLink()>1)
					{
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
					}
				
					
					bufInsSql.append(" ORDER BY CLD.LOAN_NO OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
					logger.info("Search reallocationGrid query for SQL SERVER : " + bufInsSql.toString());

					//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
					
					
					
			//	}
					
					logger.info("query : "+bufInsSql);

					searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		 	     logger.info("count---RECORDS----------->"+count);
			     logger.info("search Query...." + bufInsSql);
		int size = searchlist.size();
	  
      logger.info("reallocationGrid search Data size is...."+size);
      logger.info("reallocationGrid    ........"+bufInsSql.toString());
      for(int i=0;i<size;i++){
      logger.info("reallocationGrid search List "+searchlist.get(i).toString());
      data=(ArrayList)searchlist.get(i);

      if(data.size()>0){
    	  rVO = new ReallocationGridVo();
   
         
    	  rVO.setCheckBoxDis("<input type="+"checkbox"+" name=checkId id=checkId  value="+CommonFunction.checkNull(data.get(0).toString()).trim()+" />");
    	  rVO.setGloanno("<a href=#" +CommonFunction.checkNull(data.get(0).toString()).trim()+" onclick="+"openRecordingTrailCaseHistory('"+CommonFunction.checkNull(data.get(9).toString()).trim()+"')"+" >"+CommonFunction.checkNull(data.get(0)).toString().trim()+"</a>");
    	//openRecordingTrailCaseHistory(loanNo)
    	  if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1))).equalsIgnoreCase("")){
				
				String gdpd = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1)));
				  rVO.setGdpd(gdpd);
				}else{

				Number gdpd =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1))).trim());
												
				  rVO.setGdpd(myFormatter.format(gdpd));
				}
    	  rVO.setGcustomername(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2)).trim()));
    		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).equalsIgnoreCase("")){
				
				String gpos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3)));
				rVO.setGpos(gpos);
				}else{

				Number gpos =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
												
				rVO.setGpos(myFormatter.format(gpos));
				}
//    		 if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).equalsIgnoreCase("")){
// 				
//       		  String gtos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4)));
//       		  rVO.setGtos(myFormatter.format(gtos));
//   			}else{
//
//   				Number gtos =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
//   				rVO.setGtos(myFormatter.format(gtos));
//   			
//   				}
    	  rVO.setGqueue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5)).trim()));
    	  rVO.setGassignto(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(6)).trim()));
    	  rVO.setGallocationdate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(7)).trim()));
    	  rVO.setGqueuedate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(8)).trim()));
    	 
    		rVO.setTotalRecordSize(count);
    	  
    	  detailListGrid.add(rVO);
  	}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		loanNo= null;
		customerName= null;
		product= null;
		queue= null;
		pos1= null;
		pos2= null;
		dpd1= null;
		dpd2= null;
		user= null;
		loginuser= null;
		npastage= null;
		customertype= null;
		customercategory= null;
		assigndatefrom= null;
		assigndateto= null;
		balanceprincipal= null;
		bufInsSql =null;
		bufInsSqlTempCount=null;
		searchlist= null;
		countlist= null;
		lppChargeQuery= null;
		cbcquery= null;
		bccQuery= null;
		LPP= null;
		CBC= null;
		otherCharge= null;
		data= null;
		rVO = null;
	}
	return detailListGrid;
}


	public boolean updatereallocation(ReallocationGridVo vo,String[] checkbox) {
ArrayList dataList = new ArrayList();
StringBuilder bufInsSql = null;
PrepStmtObject insertPrepStmtObject =  null;
boolean status = false;
try {
	int length = checkbox.length;
	for (int i = 0; i < length; i++) {
		bufInsSql = new StringBuilder();
		insertPrepStmtObject = new PrepStmtObject();
    bufInsSql.append("update coll_case_dtl set USER_ID=?,REALLOCATION_REMARKS=?,REALLOCATION_FLAG='Y',ALLOCATION_DATE=");
    bufInsSql.append(dbo);
	bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
	
    bufInsSql.append(" WHERE LOAN_NO='"+CommonFunction.checkNull(checkbox[i])+"'");
	
	if (CommonFunction.checkNull(vo.getLbxHierarchyUserId()).equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxHierarchyUserId()).trim()));
	if (CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRemarks()).trim()).toString());


	if (CommonFunction.checkNull(vo.getAllocationdate()).equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(vo.getAllocationdate());

	logger.info("IN updatereallocation query1 ### "+ bufInsSql.toString());

	insertPrepStmtObject.setSql(bufInsSql.toString());
	logger.info("IN updatereallocation query1 ### "+ insertPrepStmtObject.toString());
	dataList.add(insertPrepStmtObject);
	}
	status = ConnectionDAO.sqlInsUpdDeletePrepStmt(dataList);
} catch (Exception e) {
	e.printStackTrace();
}
finally
{
	dataList =  null;
	bufInsSql = null;
	insertPrepStmtObject =  null;
}
return status;
}
//END OF 




//Anil Yadav Code Start Here


public ArrayList<EscalationMatrixVo> escNPAstage()
{
	  ArrayList<EscalationMatrixVo> npaStageList =new  ArrayList<EscalationMatrixVo>();
	  
	  ArrayList searchlist = new ArrayList();
	  ArrayList StageList = new ArrayList();
	  EscalationMatrixVo escalationVo = null;
		try{
			
            String query="select  NPA_STAGE from cr_npa_m where REC_STATUS='A'";
            logger.info("In npastage......Dao Impl"+query);
              
          	searchlist = ConnectionDAO.sqlSelect(query);
          	int size = searchlist.size();
            logger.info("searchActionCodeData " + size);

			for (int i = 0; i < size; i++) {
				 StageList = (ArrayList) searchlist.get(i);
				if (StageList.size() > 0) {
					escalationVo = new EscalationMatrixVo();
					escalationVo.setNpastageid(CommonFunction.checkNull(StageList.get(0)).toString());
					escalationVo.setNpastageval(CommonFunction.checkNull(StageList.get(0)).toString());
					npaStageList.add(escalationVo);            
				}
			}          
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			searchlist = null;
			  StageList = null;
			  escalationVo = null;
		}
		return npaStageList;
}



  public ArrayList<EscalationMatrixVo> escCustcategory()
   {
	ArrayList<EscalationMatrixVo> custcatList=new  ArrayList<EscalationMatrixVo>();
	
	ArrayList searchlist = new ArrayList();
	ArrayList CustomerList = new ArrayList();
	String query="";
	EscalationMatrixVo escalationVo = null;
	try{
		
        query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='CUST_CATEGORY' and rec_status ='A'";
       logger.info("In custcategory......Dao Impl"+query);
         
     	searchlist = ConnectionDAO.sqlSelect(query);
     	int size = searchlist.size();
     	logger.info("searchActionCodeData " + size);

		for (int i = 0; i < size; i++) {
			 CustomerList = (ArrayList) searchlist.get(i);
			if (CustomerList.size() > 0) {
				escalationVo = new EscalationMatrixVo();
				escalationVo.setCstcat(CommonFunction.checkNull(CustomerList.get(0)).toString());
				escalationVo.setCstcatval(CommonFunction.checkNull(CustomerList.get(1)).toString());
				custcatList.add(escalationVo);            
			}
		}
	}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			searchlist = null;
			CustomerList = null;
			query= null;
			escalationVo = null;
		}
		return custcatList;
}
  
  
public ArrayList<EscalationMatrixVo>  escProduct()
{
	 ArrayList<EscalationMatrixVo> productList=new  ArrayList<EscalationMatrixVo>();
	 
	 ArrayList mainList=new ArrayList();
		ArrayList subList=new ArrayList();
		String query="";
		 EscalationMatrixVo escalationVo=null;
		try{
		
           query="select PRODUCT_ID,PRODUCT_DESC from cr_product_m where REC_STATUS='A'";
           		
			  logger.info("In product......Dao Impl"+query);
			  mainList = ConnectionDAO.sqlSelect(query);
			  int size = mainList.size();
			  for(int i=0;i<size;i++){
				
				  subList=(ArrayList) mainList.get(i);
				  if(subList.size()>0){
					  escalationVo=new EscalationMatrixVo();
				
					  escalationVo.setProductid(subList.get(0).toString());
					  escalationVo.setProductval(subList.get(1).toString());
					 productList.add(escalationVo);
				}
			  } 
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			 mainList=null;
				subList=null;
				query=null;
				 escalationVo=null;
		}
		return productList;
}





public ArrayList<EscalationMatrixVo> escalationGrid(EscalationMatrixVo vo) {
	logger.info("In escalationGrid....");
	String loanNo = "";
	String customerName = "";
	String product = "";
	String queue = "";
	String pos1 = "";
	String pos2 = "";
	String dpd1 = "";
	String dpd2 = "";
	String user = "";
	String tos1 = "";
	String tos2 = "";
	String actiondays = "";
	String npastage = "";
	String customertype = "";
	String customercategory = "";
	String queueDateFrom = "";
	String queueDateTo = "";
	String balanceprincipal = "";
	String curreentdate="";
	StringBuilder bufInsSql = null;
	StringBuilder bufInsSqlTempCount = null;
	int startRecordIndex = 0;
	int endRecordIndex = no;
	int count = 0;
	StringBuilder lppChargeQuery= new StringBuilder();
	StringBuilder cbcquery= new StringBuilder();
	StringBuilder bccQuery=new StringBuilder();
	String LPP="";
	String CBC="";
	String otherCharge="";
	ArrayList data = new ArrayList();
	EscalationMatrixVo escVO = null;
	ArrayList searchlist = new ArrayList();
	ArrayList countlist = new ArrayList();
	
	ArrayList<EscalationMatrixVo> detailListGrid = new ArrayList<EscalationMatrixVo>();
	try {

		logger.info("In escalationGrid....................");
		boolean appendSQL = false;
		bufInsSql = new StringBuilder();
		bufInsSqlTempCount = new StringBuilder();
		
		if (!CommonFunction.checkNull(vo.getDpd1()).equalsIgnoreCase("")) {
			dpd1 = myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd1()).trim())).toString();
//			dpd1 =CommonFunction.checkNull(vo.getDpd1()).trim();
			logger.info("In escalationGrid......dpd1  " + dpd1);
		}
		if (!CommonFunction.checkNull(vo.getDpd2()).equalsIgnoreCase("")) {
			dpd2 = myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd2()).trim())).toString();
//			dpd2 = CommonFunction.checkNull(vo.getDpd2()).trim();
			logger.info("In escalationGrid......dpd2  " + dpd2);
		}
		if (!CommonFunction.checkNull(vo.getPos1()).equalsIgnoreCase("")) {
			pos1 = myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos1()).trim())).toString();
			logger.info("In escalationGrid......pos1  " + pos1);
		}
		if (!CommonFunction.checkNull(vo.getPos2()).equalsIgnoreCase("")) {
			pos2 = myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos2()).trim())).toString();
			logger.info("In escalationGrid......pos2  " + pos2);
		}

		loanNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanno()).trim());
		customerName = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim());
		product = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getProduct()).trim());
		curreentdate = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCurreentdate()).trim());
		user = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserID()).trim());
		//npastage = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNpastage()).trim());
		customertype = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustype()).trim());
		customercategory = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustcategory()).trim());
		actiondays = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getActionNotTaken()).trim());
		balanceprincipal = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBalanceprincipal()).trim());
		queue = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxQueuesearchId()).trim());
		queueDateTo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getQueueDateTo()).trim());
		queueDateFrom = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getQueueDateFrom()).trim());

		logger.info("In escalationGrid......loanNo  " + loanNo);
		logger.info("In escalationGrid......customerName  " + customerName);

		lppChargeQuery.append("Select  (");
		lppChargeQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
		lppChargeQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		lppChargeQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) LPP");
		lppChargeQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+loanNo+"' AND ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109'");
		cbcquery.append("Select  (");
		cbcquery.append("ISNULL(ADVICE_AMOUNT,0)-");
		cbcquery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		cbcquery.append("ISNULL(AMOUNT_IN_PROCESS,0)) CBC");
		cbcquery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+loanNo+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110'");
		bccQuery.append("Select  (");
		bccQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
		bccQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		bccQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) OTHER_CHARGE");
		bccQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+loanNo+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' ");
		bccQuery.append(" AND CHARGE_CODE_ID NOT IN ('109','110','7')");
		LPP=CommonFunction.checkNull(ConnectionDAO.singleReturn(lppChargeQuery.toString()));
		CBC=CommonFunction.checkNull(ConnectionDAO.singleReturn(cbcquery.toString()));
		otherCharge=CommonFunction.checkNull(ConnectionDAO.singleReturn(bccQuery.toString()));
		if(LPP.equalsIgnoreCase("")){
		LPP="0";	
		}
		if(CBC.equalsIgnoreCase("")){
		CBC="0";		
		}
		if(otherCharge.equalsIgnoreCase("")){
		otherCharge="0";
		}
	     String sumCharges=CommonFunction.checkNull(Double.parseDouble(CBC)+Double.parseDouble(LPP)+Double.parseDouble(otherCharge));
	     logger.info("LPP:---"+LPP)  ;
	     logger.info("CBC:---"+CBC)  ;
	     logger.info("otherCharge:---"+otherCharge)  ;
	     logger.info("sumCharges:---"+sumCharges)  ;
	     

		bufInsSql.append("select  CLD.LOAN_ID,CLD.LOAN_NO,CLD.LOAN_DPD, gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,");
		bufInsSql.append("ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+");
		bufInsSql.append("ISNULL('"+sumCharges+"',0) as activePayment ,CCD.QUEUE_CODE,CCD.USER_ID,");
	    bufInsSql.append(dbo);
		bufInsSql.append("DATEDIFF(");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE('"+curreentdate+"','"+dateFormat+"'),CCD.ALLOCATION_DATE) AS ActionNotTaken,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(CCD.QUEUE_DATE,'"+dateFormat+"') As QueueAssignDate"
						+ " from cr_loan_dtl CLD left join coll_case_dtl CCD ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID "
						+ " join coll_queue_def_m CQDM ON CCD.QUEUE_CODE=CQDM.QUEUE_CODE where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG='Y' AND CCD.ESCALATION_FLAG='Y'  AND" +
						  " CCD.USER_ID IN (select USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+vo.getMakerId()+"' )    ");

		bufInsSqlTempCount.append("select count(1)from (select  CLD.LOAN_NO,CLD.LOAN_DPD, gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,");
		bufInsSqlTempCount.append("ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+");
		bufInsSqlTempCount.append("ISNULL('"+sumCharges+"',0) as activePayment ,CCD.QUEUE_CODE,CCD.USER_ID,");
		bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("DATEDIFF(");
		bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("STR_TO_DATE('"+curreentdate+"','"+dateFormat+"'),CCD.ALLOCATION_DATE) AS ActionNotTaken,");
		bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("DATE_FORMAT(CCD.QUEUE_DATE,'"+dateFormat+"') As QueueAssignDate"
						+ " from cr_loan_dtl CLD left join coll_case_dtl CCD ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID "
						+ " join coll_queue_def_m CQDM ON CCD.QUEUE_CODE=CQDM.QUEUE_CODE where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG='Y' AND CCD.ESCALATION_FLAG='Y'  AND" +
						  " CCD.USER_ID IN (select USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+vo.getMakerId()+"')    ");
		
		if ((!(loanNo.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND CLD.LOAN_ID='" + loanNo + "'");
			bufInsSqlTempCount.append(" AND CLD.LOAN_ID='" + loanNo + "'");

		}

		if ((!(queueDateFrom.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND ");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(CCD.QUEUE_DATE,'" + dateFormat + "') >= '" + queueDateFrom + "' ");
			bufInsSqlTempCount.append(" AND ");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("DATE_FORMAT(CCD.QUEUE_DATE,'"+ dateFormat + "') >= '" + queueDateFrom + "' ");
		}
		if ((!(queueDateTo.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND ");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(CCD.QUEUE_DATE,'"+ dateFormat + "') <= '" + queueDateTo + "' ");
			bufInsSqlTempCount.append(" AND ");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("DATE_FORMAT(CCD.QUEUE_DATE,'"+ dateFormat + "') <= '" + queueDateTo + "' ");
		}
		if ((!(customerName.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND gcm.CUSTOMER_NAME like '%"+ customerName + "%'  ");
			bufInsSqlTempCount.append(" AND gcm.CUSTOMER_NAME like '%"+ customerName + "%'  ");
		}

		if ((!(product.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND CLD.LOAN_PRODUCT='" + product + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_PRODUCT='" + product+ "' ");
		}
		if ((!(dpd2.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND CLD.LOAN_DPD >= '" + dpd2 + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_DPD >= '" + dpd2+ "' ");
		}
		if ((!(dpd1.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND CLD.LOAN_DPD <= '" + dpd1 + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_DPD <= '" + dpd1+ "' ");
		}

		/*if ((!(user.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND CCD.USER_ID ='" + user + "'");
			bufInsSqlTempCount.append(" AND CCD.USER_ID ='" + user + "' ");
		}*/
		if ((!(pos2.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '" + pos2+ "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '" + pos2+ "' ");
		}
		if ((!(pos1.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+ pos1 + "' ");
			bufInsSqlTempCount.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '" + pos1+ "' ");
		}

		if ((!(customertype.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+ customertype + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"	+ customertype + "' ");
		}
		if ((!(customercategory.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND gcm.CUSTOMER_CATEGORY  ='"+ customercategory + "' ");
			bufInsSqlTempCount.append(" AND gcm.CUSTOMER_CATEGORY  ='"	+ customercategory + "' ");
		}
		if ((!(balanceprincipal.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+ balanceprincipal + "' ");
			bufInsSqlTempCount.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"	+ balanceprincipal + "' ");
		}

		if ((!(queue.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND CCD.QUEUE_CODE  ='" + queue + "' ");
			bufInsSqlTempCount.append(" AND CCD.QUEUE_CODE  ='" + queue	+ "' ");
		}

		if ((!(actiondays.trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND ");
		 	  bufInsSql.append(dbo);
		 	  bufInsSql.append("DATEDIFF(");
		 	  bufInsSql.append(dbo);
		 	  bufInsSql.append("STR_TO_DATE('"+curreentdate+"','"+dateFormat+"'),CCD.ALLOCATION_DATE)  ='" + actiondays + "' ");
			bufInsSqlTempCount.append(" AND ");
		 	  bufInsSqlTempCount.append(dbo);
		 	  bufInsSqlTempCount.append("DATEDIFF(");
		 	  bufInsSqlTempCount.append(dbo);
		 	  bufInsSqlTempCount.append("STR_TO_DATE('"+curreentdate+"','"+dateFormat+"'),CCD.ALLOCATION_DATE)  ='" + actiondays+ "' ");
		}
		//bufInsSql.append(" ORDER BY CLD.LOAN_NO");
		bufInsSqlTempCount.append(") as t ");
		count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

		logger.info("search Query:::::::::::::::::::::::" + bufInsSql);
		logger.info("bufInsSqlTempCount **************************** : "	+ bufInsSqlTempCount.toString());

		if ((loanNo.trim() == "" && customerName.trim() == ""
				&& product.trim() == "" && queue.trim() == ""
				&& user.trim() == "" 
				&& customertype.trim() == ""
				&& customercategory.trim() == ""
				&& balanceprincipal.trim() == ""
				&& queueDateTo.trim() == "" && loanNo.trim() == ""
				&& customerName.trim() == "" && queueDateFrom.trim() == "")
				|| (loanNo.trim().equalsIgnoreCase("") && customerName.trim().equalsIgnoreCase("")
				|| product.trim().equalsIgnoreCase("") && queue.trim().equalsIgnoreCase("")
				|| user.trim().equalsIgnoreCase("") 
				|| customertype.trim().equalsIgnoreCase("") && customercategory.trim().equalsIgnoreCase("")
				|| balanceprincipal.trim().equalsIgnoreCase("") && queueDateTo.trim().equalsIgnoreCase("") 
				|| queueDateFrom.trim().equalsIgnoreCase(""))
				|| vo.getCurrentPageLink() > 1) {

			logger.info("current PAge Link no .................... "	+ vo.getCurrentPageLink());

			if (vo.getCurrentPageLink() > 1) {
				startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "	+ startRecordIndex);
				logger.info("endRecordIndex .................... "	+ endRecordIndex);
			}
			bufInsSql.append(" ORDER BY CLD.LOAN_NO OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");

		//bufInsSql.append(" limit " + startRecordIndex + ","	+ endRecordIndex);
		}
		logger.info("query : " + bufInsSql);

		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

		logger.info("count---RECORDS----------->" + count);
		int size = searchlist.size();
		logger.info("escalationGrid search Data size is...."+ size);
		logger.info("escalationGrid    ........" + bufInsSql.toString());
		for (int i = 0; i < size; i++) {
			data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				escVO = new EscalationMatrixVo();

				 escVO.setCheckBoxDis("<input type="	+ "checkbox"+ " name=checkId id=checkId  value="+ CommonFunction.checkNull(data.get(0).toString()).trim() + " />");
				 escVO.setGloanno("<a href=#"+ CommonFunction.checkNull(data.get(0).toString()).trim()+ " onclick="+"openRecordingTrailCaseHistory('"+CommonFunction.checkNull(data.get(0).toString()).trim()+"')"+" > "+ CommonFunction.checkNull(data.get(1)).toString().trim() + "</a>");
				if (StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2)))	.equalsIgnoreCase("")) 
				{
					String gdpd = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2)));
					escVO.setGdpd(gdpd);
				} 
				else 
				{
					Number gdpd = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2))).trim());
					escVO.setGdpd(myFormatter.format(gdpd));
				}
				escVO.setGcustomername(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3)).trim()));
				if (StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).equalsIgnoreCase("")) 
				{
					String gpos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4)));
					escVO.setGpos(gpos);
				} 
				else {
					Number gpos = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
					escVO.setGpos(myFormatter.format(gpos));
				    }
				if (StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).equalsIgnoreCase("")) 
				{     
					String gtos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5)));
					escVO.setGtos(myFormatter.format(gtos));
				}
             else
				{
					Number gtos = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
					escVO.setGtos(myFormatter.format(gtos));

				}
				escVO.setGqueue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(6)).trim()));
				escVO.setGassignto(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(7)).trim()));
				escVO.setGactionottaken(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(8)).trim()));
			    escVO.setgQueuedate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(9)).trim()));
			    
				//escVO.setgAllocationdate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(8)).trim()));

				escVO.setTotalRecordSize(count);

				detailListGrid.add(escVO);
			}

		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		loanNo = null;
		customerName = null;
		product= null;
		queue = null;
		pos1 = null;
		pos2 = null;
		dpd1 = null;
		dpd2 = null;
		user = null;
		tos1 = null;
		tos2 = null;
		actiondays = null;
		npastage = null;
		customertype = null;
		customercategory = null;
		queueDateFrom = null;
		queueDateTo = null;
		 balanceprincipal = null;
		curreentdate= null;
		bufInsSql = null;
		bufInsSqlTempCount = null;
		lppChargeQuery= null;
		cbcquery= null;
		bccQuery= null;
		LPP= null;
		CBC= null;
		otherCharge= null;
		data = null;
		escVO = null;
		searchlist = null;
		countlist = null;
	}
	
	return detailListGrid;
}

public boolean updateEscalationMatrix(EscalationMatrixVo VO, String[] checkbox) {
	
	logger.info("In updateEscalationMatrix........................");

	StringBuilder bufInsSql = null;
	PrepStmtObject insertPrepStmtObject = null;
	boolean status = false;
	ArrayList dataList = new ArrayList();
	try {
		int length = checkbox.length;
		for (int i = 0; i < length; i++) {
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql.append("update coll_case_dtl set USER_ID=?,ESCALATION_REMARK=?,ESCALATION_FLAG='N' "
							+ " WHERE LOAN_ID='"+ CommonFunction.checkNull(checkbox[i]) + "'");

			if (CommonFunction.checkNull(VO.getUserID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(VO.getUserID()).trim()));

			if (CommonFunction.checkNull(VO.getRemarks()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(VO.getRemarks()).trim()).toString());
			/*---------------------------------------------------------
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(VO.getUserID())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(VO.getUserID()).trim());
			if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(VO.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(VO.getMakerDate()).trim());
			
			//,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)
			//---------------------------------------------------------*/

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN updatereallocation query1:::::"+insertPrepStmtObject.printQuery());

			dataList.add(insertPrepStmtObject);
		}
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(dataList);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		bufInsSql = null;
		insertPrepStmtObject = null;
		dataList = null;
	}
	return status;


}



//Anil Yadav Code End Here

//Arun Code Starts Here

public ArrayList<ContactRecordingSearchVO> contactRecordingSearchList(ContactRecordingSearchVO vo){
	
	String loanNo="";
	String customerName="";
	String product="";
	String queue="";
	String actionDue="";
	String bDate="";
	String pos1="";
	String pos2="";
	String dpd1="";
	String dpd2="";
	String user="";
	String tos1="";
	String tos2="";
	String npastage="";
	String customertype="";
	String customercategory="";
	String assigndatefrom="";
	String assigndateto="";
	String balanceprincipal="";
	StringBuilder bufInsSql =null;
	StringBuilder bufInsSqlTempCount=null;
	StringBuilder lppChargeQuery= new StringBuilder();
	StringBuilder cbcquery= new StringBuilder();
	StringBuilder bccQuery=new StringBuilder();
	String LPP="";
	String CBC="";
	String otherCharge="";
	String branch="";
	String dueDate="";
	ArrayList data = new ArrayList();
	int startRecordIndex=0;
	int endRecordIndex = no;
	int count=0;
	ArrayList searchlist = new ArrayList();
	ContactRecordingSearchVO conteactRecVO = null;
	
	ArrayList<ContactRecordingSearchVO> detailListGrid= new ArrayList<ContactRecordingSearchVO>();
	

	try {
		logger.info("In contactRecordingSearchList().....................................Dao Impl");

		 bufInsSql = new StringBuilder();
		 bufInsSqlTempCount = new StringBuilder();
		
		 if(!CommonFunction.checkNull(vo.getDpd1()).equalsIgnoreCase(""))
	   	    {
	      dpd1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd1()).trim())).toString();
	      logger.info("In contactRecordingSearchList......dpd1  "+dpd1);         
	   	    }
	      if(!CommonFunction.checkNull(vo.getDpd2()).equalsIgnoreCase(""))
	 	    {
	    	  dpd2= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd2()).trim())).toString();
	       logger.info("In contactRecordingSearchList......dpd2  "+dpd2);         
	 	    }
	      if(!CommonFunction.checkNull(vo.getPos1()).equalsIgnoreCase(""))
	 	    {
	    	  pos1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos1()).trim())).toString();
	         logger.info("In contactRecordingSearchList......pos1  "+pos1);         
	 	    }
	      if(!CommonFunction.checkNull(vo.getPos2()).equalsIgnoreCase(""))
	 	    {
	    	  pos2= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos2()).trim())).toString();
	        logger.info("In contactRecordingSearchList......pos2  "+pos2);         
	 	    }
	      if(!CommonFunction.checkNull(vo.getBalanceprincipal()).equalsIgnoreCase(""))
	 	    {
	    	  balanceprincipal= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBalanceprincipal()).trim())).toString();
	       logger.info("In contactRecordingSearchList......balanceprincipal  "+balanceprincipal);         
	 	    }else{
	 	      balanceprincipal=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBalanceprincipal()).trim());
	 	    }
	     
	      loanNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanno()).trim());
	      customerName=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim());
	      product=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getProduct()).trim());
	    
	      user=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxUserSearchId()).trim());
	      npastage=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNpastage()).trim());
	      customertype=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustype()).trim());
	      customercategory=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustcategory()).trim());
	      logger.info("vo.getCustcategory()"+vo.getCustcategory());         
	     
	      queue=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxQueuesearchId()).trim());
	      assigndateto=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAssignto()).trim());
	      assigndatefrom=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAssignfrom()).trim());
	      actionDue=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getActionDue()).trim());
	      bDate=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessdate()).trim());
	      branch=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchId()).trim());
	      dueDate=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDueDate()).trim());
	      logger.info("branch:::"+vo.getLbxBranchId());
	      logger.info("dueDate:::"+vo.getDueDate());
		//Select CLD.LOAN_NO,CLD.LOAN_ID,cdcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,CCD.QUEUE_CODE,CCD.USER_ID,ESCALATION_FLAG From cr_loan_dtl CLD left join coll_case_dtl CCD ON CLD.LOAN_NO=CCD.LOAN_NO join cr_deal_customer_m cdcm on cdcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' AND CCD.USER_ID='ANIL' or ESCALATION_FLAG='Y'
	  	lppChargeQuery.append("Select  (");
		lppChargeQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
		lppChargeQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		lppChargeQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) LPP");
	  	lppChargeQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+vo.getLoanId()+"' AND ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109'");
		cbcquery.append("Select  (");
		cbcquery.append("ISNULL(ADVICE_AMOUNT,0)-");
		cbcquery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		cbcquery.append("ISNULL(AMOUNT_IN_PROCESS,0)) CBC");
		cbcquery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+vo.getLoanId()+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110'");
		bccQuery.append("Select  (");
		bccQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
		bccQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		bccQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) OTHER_CHARGE");
		bccQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+vo.getLoanId()+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' ");
		bccQuery.append(" AND CHARGE_CODE_ID NOT IN ('109','110','7')");
		LPP=CommonFunction.checkNull(ConnectionDAO.singleReturn(lppChargeQuery.toString()));
		CBC=CommonFunction.checkNull(ConnectionDAO.singleReturn(cbcquery.toString()));
		otherCharge=CommonFunction.checkNull(ConnectionDAO.singleReturn(bccQuery.toString()));
		if(LPP.equalsIgnoreCase("")){
		LPP="0";	
		}
		if(CBC.equalsIgnoreCase("")){
		CBC="0";		
		}
		if(otherCharge.equalsIgnoreCase("")){
		otherCharge="0";
		}
	     String sumCharges=CommonFunction.checkNull(Double.parseDouble(CBC)+Double.parseDouble(LPP)+Double.parseDouble(otherCharge));
	     logger.info("LPP:---"+LPP)  ;
	     logger.info("CBC:---"+CBC)  ;
	     logger.info("otherCharge:---"+otherCharge)  ;
	     logger.info("sumCharges:---"+sumCharges)  ;
	    
	     bufInsSql.append("Select CLD.LOAN_NO,CLD.LOAN_ID,gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_AMOUNT,");
			bufInsSql.append("ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+");
			bufInsSql.append("ISNULL('"+sumCharges+"',0) as activePayment ,");
			bufInsSql.append(" CCD.QUEUE_CODE,CCD.USER_ID,iif(CCD.LAST_ACTION_CODE is not null ,'YES','NO') " );
			 
			 bufInsSql.append("From cr_loan_dtl CLD left join coll_case_dtl CCD ");
			 bufInsSql.append(" ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
			 bufInsSql.append(" where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' AND ((CCD.USER_ID='"+vo.getMakerId()+"'AND ESCALATION_FLAG='N')  ");
			 bufInsSql.append(" OR (CCD.LOAN_ID IN (SELECT CCD.LOAN_ID FROM COLL_CASE_DTL CCD WHERE");
			 bufInsSql.append(" CCD.USER_ID IN (select USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+vo.getMakerId()+"') AND ESCALATION_FLAG='Y')))");
			 		
			bufInsSqlTempCount.append("Select count(1) from (Select CLD.LOAN_NO,CLD.LOAN_ID,gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_AMOUNT,");
			bufInsSqlTempCount.append(" ");
			bufInsSqlTempCount.append("ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+");
			bufInsSqlTempCount.append("ISNULL('"+sumCharges+"',0) as activePayment ,");
			bufInsSqlTempCount.append(" CCD.QUEUE_CODE,CCD.USER_ID,iif(CCD.LAST_ACTION_CODE is not null ,'YES','NO')as actionCode From cr_loan_dtl CLD left join coll_case_dtl CCD ");
			bufInsSqlTempCount.append(" ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
			bufInsSqlTempCount.append(" where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' AND ((CCD.USER_ID='"+vo.getMakerId()+"' AND ESCALATION_FLAG='N') ");
			bufInsSqlTempCount.append("  OR (CCD.LOAN_ID IN (SELECT CCD.LOAN_ID FROM COLL_CASE_DTL CCD WHERE");
			bufInsSqlTempCount.append(" CCD.USER_ID IN (select USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+vo.getMakerId()+"') AND ESCALATION_FLAG='Y')))");
					
			if((!(loanNo.trim().equalsIgnoreCase("")))) {
		         bufInsSql.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
		         bufInsSqlTempCount.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
		    	 }
		 	if((!(assigndatefrom.trim().equalsIgnoreCase("")))) {
		 	  bufInsSql.append(" AND ");
			  bufInsSql.append(dbo);
			  bufInsSql.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+assigndatefrom+"' ");
		 	  bufInsSqlTempCount.append(" AND ");
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+assigndatefrom+"' ");
		      }
		 	if((!(assigndateto.trim().equalsIgnoreCase("")))) {
		   	   bufInsSql.append(" AND ");
				bufInsSql.append(dbo);
				bufInsSql.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+assigndateto+"' ");
		     	bufInsSqlTempCount.append(" AND ");
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+assigndateto+"' ");
		     }
		    if((!(customerName.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND gcm.CUSTOMER_NAME like '%"+customerName+"%'  ");
		    	  bufInsSqlTempCount.append(" AND gcm.CUSTOMER_NAME like '%"+customerName+"%'  ");
		      }
			if((!(product.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_PRODUCT='"+product+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_PRODUCT='"+product+"' ");
	          }
			if((!(dpd2.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CLD.LOAN_DPD >= '"+dpd2+"' ");
		    	  bufInsSqlTempCount.append(" AND CLD.LOAN_DPD >= '"+dpd2+"' ");
		      }
			if((!(dpd1.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_DPD <= '"+dpd1+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_DPD <= '"+dpd1+"' ");
	          }
				
			if((!(user.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CCD.USER_ID ='"+user+"' ");
		    	  bufInsSqlTempCount.append(" AND CCD.USER_ID ='"+user+"' ");
		      }
			if((!(pos2.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '"+pos2+"' ");
		    	  bufInsSqlTempCount.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '"+pos2+"' ");
		      }
			if((!(pos1.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+pos1+"' ");
	    	  bufInsSqlTempCount.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+pos1+"' ");
	          }
			/*if((!(tos2.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0) >= '"+tos2+"' ");
		    	  bufInsSqlTempCount.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0)   >= '"+tos2+"' ");
		      }
			if((!(tos1.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0) <= '"+tos1+"' ");
		    	  bufInsSqlTempCount.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0) <= '"+tos1+"' ");
		      }
			if((!(npastage.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.NPA_FLAG  ='"+npastage+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.NPA_FLAG  ='"+npastage+"' ");
	          }*/
			if((!(customertype.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
	         }
			if((!(customercategory.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND gcm.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
		    	  bufInsSqlTempCount.append(" AND gcm.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
		      }
			if((!(balanceprincipal.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
		    	  bufInsSqlTempCount.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
		       }
			if((!(queue.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
		    	  bufInsSqlTempCount.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
		      }
			logger.info("actionDue::::::::"+actionDue);
			if(((actionDue.trim().equalsIgnoreCase("DT")))) {
		    	  bufInsSql.append(" AND CCD.NEXT_ACTION_DATE = ");
		    	  bufInsSql.append(dbo);
		    	  bufInsSql.append("STR_TO_DATE('"+bDate+"','"+dateFormat+"') ");
		    	  bufInsSqlTempCount.append(" AND CCD.NEXT_ACTION_DATE  = ");
		    	  bufInsSqlTempCount.append(dbo);
		    	  bufInsSqlTempCount.append("STR_TO_DATE('"+bDate+"','"+dateFormat+"') ");
		      }
			if(((actionDue.trim().equalsIgnoreCase("DL")))) {
		    	  bufInsSql.append(" AND CCD.NEXT_ACTION_DATE > ");
		    	  bufInsSql.append(dbo);
		    	  bufInsSql.append("STR_TO_DATE('"+bDate+"','"+dateFormat+"') ");
		    	  bufInsSqlTempCount.append(" AND CCD.NEXT_ACTION_DATE  > ");
		    	  bufInsSqlTempCount.append(dbo);
		    	  bufInsSqlTempCount.append("STR_TO_DATE('"+bDate+"','"+dateFormat+"') ");
		      }
			if(((actionDue.trim().equalsIgnoreCase("NA")))) {
		    	  bufInsSql.append(" AND (CCD.LAST_ACTION_CODE  ='' or CCD.LAST_ACTION_CODE is null) ");
		    	  bufInsSqlTempCount.append(" AND (CCD.LAST_ACTION_CODE  ='' or CCD.LAST_ACTION_CODE is null) ");
		      }
			
			if((!(branch.trim().equalsIgnoreCase("")))) {
		         bufInsSql.append(" AND CCD.LOAN_BRANCH='"+branch+"' ");
		         bufInsSqlTempCount.append(" AND CCD.LOAN_BRANCH='"+branch+"' ");
		    	 }
			if((!(dueDate.trim().equalsIgnoreCase("")))) {
		         bufInsSql.append(" AND CCD.NEXT_ACTION_DATE='"+dueDate+"' ");
		         bufInsSqlTempCount.append(" AND CCD.NEXT_ACTION_DATE='"+dueDate+"' ");
		    	 }
			
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
		
			bufInsSqlTempCount.append(" ) as t ");
			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
		  
			/*if(( loanNo.trim()=="" && customerName.trim()==""&& 
					product.trim()=="" && queue.trim()==""&& 
					 user.trim()=="" && npastage.trim()==""&& 
					 customertype.trim()=="" && customercategory.trim()==""&& 
					 balanceprincipal.trim()=="" &&  assigndateto.trim()==""&& 
					 loanNo.trim()=="" && customerName.trim()==""&& 
					 assigndatefrom.trim()=="" ) 
					 || (loanNo.trim().equalsIgnoreCase("") && customerName.trim().equalsIgnoreCase("")||
							 product.trim().equalsIgnoreCase("") && queue.trim().equalsIgnoreCase("")||
							 user.trim().equalsIgnoreCase("") && npastage.trim().equalsIgnoreCase("")||
							 customertype.trim().equalsIgnoreCase("") && customercategory.trim().equalsIgnoreCase("")||
							 balanceprincipal.trim().equalsIgnoreCase("") &&  assigndateto.trim().equalsIgnoreCase("")||
							 assigndatefrom.trim().equalsIgnoreCase("") ) 
					 || vo.getCurrentPageLink()>1)
			{*/
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			
				if(vo.getCurrentPageLink()>1)
				{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				}
			
				
				bufInsSql.append(" ORDER BY CLD.LOAN_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				logger.info("Search contactRecordingSearchList query for SQL SERVER : " + bufInsSql.toString());

				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
				
			
			//}
			
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
 	     logger.info("count---RECORDS----------->"+count);
	     logger.info("search Query...." + bufInsSql);
	     int size = searchlist.size();
		logger.info("searchLIst size:----" + size);
		for (int i = 0; i < size; i++) {
		/*	logger.info("searchQueueEdit "
					+ searchlist.get(i).toString());*/
			data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				conteactRecVO  = new ContactRecordingSearchVO();
				   
		     //    logger.info("Exclation Flag:000---"+CommonFunction.checkNull(data.get(7)));
				if(CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("Y")){
					conteactRecVO.setLoanno("<a href=#" +CommonFunction.checkNull(data.get(1).toString()).trim()+" onclick="+"openContactRecordingTrail('"+CommonFunction.checkNull(data.get(1).toString()).trim()+"','"+CommonFunction.checkNull(data.get(7))+"')"+" style=color:red>"+CommonFunction.checkNull(data.get(0)).toString().trim()+"</a>");	
				}else{
					conteactRecVO.setLoanno("<a href=#" +CommonFunction.checkNull(data.get(1).toString()).trim()+" onclick="+"openContactRecordingTrail('"+CommonFunction.checkNull(data.get(1).toString()).trim()+"','"+CommonFunction.checkNull(data.get(7))+"')"+">"+CommonFunction.checkNull(data.get(0)).toString().trim()+"</a>");
				}
				conteactRecVO.setCustomername(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2)).trim()));
		    	  
		    	
		    		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).equalsIgnoreCase("")){
						
						String gpos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3)));
						conteactRecVO.setPos1(gpos);
						}else{

						Number gpos =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
														
						conteactRecVO.setPos1(myFormatter.format(gpos));
						}
		    		 if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).equalsIgnoreCase("")){
		 				
		       		  String gtos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4)));
		       		conteactRecVO.setTos1(myFormatter.format(gtos));
		   			}else{

		   				Number gtos =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
		   				conteactRecVO.setTos1(myFormatter.format(gtos));
		   			
		   				}
		    		 conteactRecVO.setQueue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5)).trim()));
		    		 conteactRecVO.setAssignto(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(6)).trim()));
		    		 conteactRecVO.setExclationFlag(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(7)).trim()));
		    	  conteactRecVO.setTotalRecordSize(count);
		    	  detailListGrid.add(conteactRecVO);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		loanNo= null;
		customerName= null;
		product= null;
		queue= null;
		pos1= null;
		pos2= null;
		dpd1= null;
		dpd2= null;
		user= null;
		tos1= null;
		tos2= null;
		npastage= null;
		customertype= null;
		customercategory= null;
		assigndatefrom= null;
		assigndateto= null;
		balanceprincipal= null;
		bufInsSql =null;
		bufInsSqlTempCount=null;
		lppChargeQuery= null;
		cbcquery= null;
		bccQuery= null;
		LPP= null;
		CBC= null;
		otherCharge= null;
		data = null;
		searchlist = null;
		conteactRecVO = null;
	}
	return detailListGrid;	
	
}


public  ArrayList <ActionCodeMasterVo>  actionCodeList(){
	ArrayList <ActionCodeMasterVo> actionCodeList=new ArrayList <ActionCodeMasterVo>();
	
	 ArrayList mainList=new ArrayList();
	 ArrayList subList=new ArrayList();
	 String query="";
	 ActionCodeMasterVo actionCodeVo=null;
	try{
		
        query="select ACTION_CODE,ACTION_DESC from coll_action_code_m where REC_STATUS='A' and ACTION_CODE <> 'SR' ";
        		
		  logger.info("In actionCodeList......Dao Impl"+query);
		  mainList = ConnectionDAO.sqlSelect(query);
		  int size = mainList.size();
		  for(int i=0;i<size;i++){
			  subList=(ArrayList) mainList.get(i);
			  if(subList.size()>0){
				  actionCodeVo=new ActionCodeMasterVo();
				  actionCodeVo.setCodeId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0)).trim()));
				  actionCodeVo.setCodeDesc(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1)).trim()));
				
				actionCodeList.add(actionCodeVo);
			}
		  }
			  logger.info("In product......Dao Impl"+actionCodeList.size());
		  
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		mainList=null;
		 subList=null;
		 query=null;
		 actionCodeVo=null;
	}
	return actionCodeList;
}
//Customer info list with allocation info 


public ArrayList<ContactRecordingFollowUpVO> customerInfoList(ContactRecordingFollowUpVO vo){
	ArrayList<ContactRecordingFollowUpVO> customerInfoList= new ArrayList<ContactRecordingFollowUpVO>();
	
	 ArrayList mainList=new ArrayList();
		ArrayList subList=new ArrayList();
		StringBuilder query = new StringBuilder();
		ContactRecordingFollowUpVO ContactRecordingVO=null;
	try{
		
     query.append("Select CLD.LOAN_NO,CLD.LOAN_ID,CLD.LOAN_CUSTOMER_ID,gcm.CUSTOMER_NAME,");
	query.append(" (Select PRODUCT_DESC from cr_product_m CPM where CPM.PRODUCT_ID=CLD.LOAN_PRODUCT) LOAN_PRODUCT,");
	query.append(" (Select SCHEME_DESC from cr_scheme_m CSM where CSM.SCHEME_ID=CLD.LOAN_SCHEME) LOAN_SCHEME,");
	query.append(" (Select BRANCH_DESC from com_branch_m CBM where CBM.BRANCH_ID=CLD.LOAN_BRANCH) LOAN_BRANCH,");
	query.append(dbo);
	query.append("DATE_FORMAT(gcm.CUSTOMER_DOB,'"+dateFormat+"')as CUSTOMER_DOB ,");
	query.append(" (SELECT QUEUE_DESC from coll_queue_def_m cqdm where  cqdm.QUEUE_CODE= CCD.QUEUE_CODE) QUEUE_CODE,");
	query.append(dbo);
	query.append("DATE_FORMAT(ALLOCATION_DATE,'"+dateFormat+"')as ALLOCATION_DATE,");
	query.append(dbo);
	query.append("DATE_FORMAT(QUEUE_DATE,'"+dateFormat+"')as QUEUE_DATE,");
	query.append("(Select USER_NAME from sec_user_m seum where seum.USER_ID=CCD.USER_ID) USER_ID,gcm.CUSTMER_PAN,gcm.CUSTOMER_VAT_NO");
	query.append(" ,FATHER_HUSBAND_NAME From cr_loan_dtl CLD  join coll_case_dtl CCD");
	query.append(" ON CLD.LOAN_ID=CCD.LOAN_ID join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
	query.append(" where CLD.REC_STATUS='A' and CLD.LOAN_ID='"+vo.getLoanId()+"' AND DELINQUENCY_FLAG = 'Y'");
     		
		  logger.info("In customerInfoList......Dao Impl"+query);
		  mainList = ConnectionDAO.sqlSelect(query.toString());
		  int size = mainList.size();
		  for(int i=0;i<size;i++){
			  subList=(ArrayList) mainList.get(i);
			  if(subList.size()>0){
				  ContactRecordingVO=new ContactRecordingFollowUpVO();
				  ContactRecordingVO.setLoanNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0)).trim()));
				  ContactRecordingVO.setLoanId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1)).trim()));
				  ContactRecordingVO.setCustomerId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(2)).trim()));
				  ContactRecordingVO.setCustomerName(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(3)).trim()));
				  ContactRecordingVO.setProduct(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(4)).trim()));
				  ContactRecordingVO.setScheme(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(5)).trim()));
				  ContactRecordingVO.setBranch(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(6)).trim()));
				  ContactRecordingVO.setCustomerDOB(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(7)).trim()));
				  ContactRecordingVO.setQueue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(8)).trim()));
				  ContactRecordingVO.setAllocationDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(9)).trim()));
				  ContactRecordingVO.setQueueDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(10)).trim()));
				  ContactRecordingVO.setUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(11)).trim()));
				  ContactRecordingVO.setPanNO(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(12)).trim()));
				  ContactRecordingVO.setsTaxNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(13)).trim()));
				  ContactRecordingVO.setFatherHusbandName(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(14)).trim()));
				  customerInfoList.add(ContactRecordingVO);
			}
		  }
			  logger.info("In customerInfoList......Dao Impl"+customerInfoList.size());
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		mainList=null;
		subList=null;
		query =null;
		ContactRecordingVO=null;
	}
	return customerInfoList;
}


public ArrayList<ContactRecordingFollowUpVO> customerContactInfoList(ContactRecordingFollowUpVO vo){
	ArrayList<ContactRecordingFollowUpVO> customerInfoList= new ArrayList<ContactRecordingFollowUpVO>();
	
	 ArrayList mainList=new ArrayList();
		ArrayList subList=new ArrayList();
		StringBuilder query= new StringBuilder();
		ContactRecordingFollowUpVO ContactRecordingVO=null;
	try{
		
     query.append("Select cam.ADDRESS_LINE1,cam.ADDRESS_LINE2,cam.ADDRESS_LINE3,");
     query.append(" (Select DISTRICT_DESC from com_district_m cdm where cdm.DISTRICT_ID= cam.DISTRICT) DISTRICT,");
	 query.append(" (Select STATE_DESC from com_state_m csm where  csm.STATE_ID=cam.STATE) STATE,");
	 query.append(" cam.PRIMARY_PHONE,gcm.CUSTOMER_EMAIL,cam.ALTERNATE_PHONE ");
	 query.append("  From cr_loan_dtl CLD  join coll_case_dtl CCD ");
	 query.append(" ON CLD.LOAN_ID=CCD.LOAN_ID join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
	 query.append(" join com_address_m cam on cam.BPID=CLD.LOAN_CUSTOMER_ID ");
	 query.append(" where CLD.REC_STATUS='A'  AND DELINQUENCY_FLAG = 'Y' AND CLD.LOAN_ID='"+vo.getLoanId()+"' ");
     		
		  logger.info("In customerContactInfoList......Dao Impl"+query);
		  mainList = ConnectionDAO.sqlSelect(query.toString());
		  int size = mainList.size();
		  for(int i=0;i<size;i++){
			  subList=(ArrayList) mainList.get(i);
			  if(subList.size()>0){
				  ContactRecordingVO=new ContactRecordingFollowUpVO();
				  String address=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0)).trim())+ " " +StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1)).trim())+ " "+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(2)).trim());
				  ContactRecordingVO.setAddressDtl(address);
				  ContactRecordingVO.setCity(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(3)).trim()));
				  ContactRecordingVO.setState(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(4)).trim()));
				  ContactRecordingVO.setPrimaryPhone(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(5)).trim()));
				  ContactRecordingVO.setEmail(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(6)).trim()));
				  ContactRecordingVO.setSecondryPhone(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(7)).trim()));
				  customerInfoList.add(ContactRecordingVO);
			}
		  }
			  logger.info("In customerContactInfoList......Dao Impl"+customerInfoList.size());
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		mainList=null;
		subList=null;
		query=null;
		ContactRecordingVO=null;
	}
	return customerInfoList;
}


public ArrayList<ContactRecordingFollowUpVO> loanInfoList(ContactRecordingFollowUpVO vo){
	ArrayList<ContactRecordingFollowUpVO> allocationInfoList= new ArrayList<ContactRecordingFollowUpVO>();
	
	 ArrayList mainList=new ArrayList();
	 ArrayList subList=new ArrayList();
	 StringBuilder lppChargeQuery= new StringBuilder();
	 StringBuilder cbcquery= new StringBuilder();
	 StringBuilder bccQuery= new StringBuilder();
	 String LPP="";
	 String CBC="";
	 String otherCharge="";
	 StringBuilder query = new StringBuilder();
	 String DpdStringval="";
	 String DpdStringquery="";
	 String dpdString="";
	 ContactRecordingFollowUpVO ContactRecordingVO=null;
	 String loanAmount="";
	 String emiAmount="";
	 String loanAmountOverDue="";
	 String principalOutStanding="";
	 String interestOverDue="";
	 String principalOverDue="";
	 String frequency="";
	 String assetCost="";
	 String totalAmountDueFormatted="";
		 
	try{
		lppChargeQuery.append("Select  (");
		lppChargeQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
		lppChargeQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		lppChargeQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) LPP");
		lppChargeQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+vo.getLoanId()+"' AND ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109'");
		cbcquery.append("Select  (");
		cbcquery.append("ISNULL(ADVICE_AMOUNT,0)-");
		cbcquery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		cbcquery.append("ISNULL(AMOUNT_IN_PROCESS,0)) CBC");
		cbcquery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+vo.getLoanId()+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110'");
		bccQuery.append("Select  (");
		bccQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
		bccQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		bccQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) OTHER_CHARGE");
		bccQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+vo.getLoanId()+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' ");
		bccQuery.append(" AND CHARGE_CODE_ID NOT IN ('109','110','7')");
		LPP=CommonFunction.checkNull(ConnectionDAO.singleReturn(lppChargeQuery.toString()));
		CBC=CommonFunction.checkNull(ConnectionDAO.singleReturn(cbcquery.toString()));
		otherCharge=CommonFunction.checkNull(ConnectionDAO.singleReturn(bccQuery.toString()));
		if(LPP.equalsIgnoreCase("")){
			LPP="0";	
		}
		if(CBC.equalsIgnoreCase("")){
			CBC="0";		
				}
		if(otherCharge.equalsIgnoreCase("")){
			otherCharge="0";
		}
		
        query.append("Select CLD.LOAN_ID,CLD.LOAN_LOAN_AMOUNT,");
        query.append(dbo);
        query.append("DATE_FORMAT(CLD.LOAN_DISBURSAL_DATE,'"+dateFormat+"') as LOAN_DISBURSAL_DATE,CLD.LOAN_EMI_AMOUNT,CLD.LOAN_NO_OF_INSTALLMENT,");
        query.append(" CLD.LOAN_BILLED_INSTL_NUM,CLD.LOAN_OVERDUE_AMOUNT,CLD.LOAN_TENURE,CLD.LOAN_DPD,(");
        query.append("ISNULL(CLD.LOAN_OVERDUE_PRINCIPAL,0)+");
        query.append("ISNULL(CLD.LOAN_BALANCE_PRINCIPAL,0)) PRINCIPAL_OUTSTANDING,(");
        query.append("ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)-");
        query.append("ISNULL(CLD.LOAN_OVERDUE_PRINCIPAL,0)) INTEREST_OVERDUE,");
        query.append(" CLD.LOAN_OVERDUE_PRINCIPAL,CLD.LOAN_REPAYMENT_FREQ,");
        query.append(dbo);
        query.append("DATE_FORMAT(CLD.LOAN_REPAY_EFF_DATE,'"+dateFormat+"') as LOAN_REPAY_EFF_DATE,");
        query.append(dbo);
        query.append("DATE_FORMAT(CLD.LOAN_MATURITY_DATE,'"+dateFormat+"') as LOAN_MATURITY_DATE,");
        query.append(" CLD.LOAN_FINAL_RATE,CLD.LOAN_ASSET_COST,CLD.LOAN_DUE_DAY,CLD.NPA_FLAG,LOAN_DPD_STRING,LOAN_FLAT_RATE,LOAN_EFF_RATE,");
        query.append(dbo);
        query.append("DATE_FORMAT(CDD.DEAL_DATE,'"+dateFormat+"')as DEAL_DATE,");
   		query.append(dbo);
    	query.append("DATE_FORMAT(CDLD.DEAL_SANCTION_DATE,'"+dateFormat+"') as DEAL_SANCTION_DATE,(CLD.LOAN_BILLED_INSTL_NUM - CLD.LOAN_OVERDUE_INSTL_NUM) NO_OF_EMI_PAID,LOAN_REPAYMENT_MODE,LOAN_RATE_TYPE,(Select user_name from sec_user_m where user_id=DEAL_RM) RM,(Select user_name from sec_user_m where user_id=(Select USER_REPORTING_TO from sec_user_m where user_id=DEAL_RM)) Super_rm,DATE_FORMAT(CID.RECEIVED_DATE,'%d-%m-%Y'), CID.INSTRUMENT_AMOUNT,CLD.LOAN_OVERDUE_AMOUNT ");
        query.append(dbo);
        query.append("DATE_FORMAT(CID.RECEIVED_DATE,'"+dateFormat+"'), CID.INSTRUMENT_AMOUNT,CLD.LOAN_OVERDUE_AMOUNT");
        query.append(" FROM cr_loan_dtl CLD  left join cr_deal_dtl CDD on(CDD.DEAL_ID=CLD.LOAN_DEAL_ID)left join cr_deal_loan_dtl CDLD on(CDLD.DEAL_ID=CDD.DEAL_ID)");
        query.append("LEFT join CR_INSTRUMENT_DTL CID on CID.TXNID=CLD.LOAN_ID AND INSTRUMENT_TYPE='R'  and TXN_TYPE='LIM'and txnid='"+vo.getLoanId()+"'");
        query.append("and CID.INSTRUMENT_ID=( select max(INSTRUMENT_ID) from CR_INSTRUMENT_DTL where INSTRUMENT_TYPE='R' and CID.REC_STATUS in ('D','R') and TXN_TYPE='LIM'AND TXNID='"+vo.getLoanId()+"')");
        query.append("where CLD.LOAN_ID='"+vo.getLoanId()+"'");
       
		  logger.info("In customerContactInfoList......Dao Impl   :  "+query);
		  mainList = ConnectionDAO.sqlSelect(query.toString());
		  int size = mainList.size();
		  for(int i=0;i<size;i++){
			  subList=(ArrayList) mainList.get(i);
			  if(subList.size()>0){
				DpdStringval= CommonFunction.checkNull(subList.get(19)).trim();
				  logger.info("DpdStringval"+DpdStringval);
				  DpdStringquery="SELECT RIGHT('"+DpdStringval+"',24)";  
				  dpdString=CommonFunction.checkNull(ConnectionDAO.singleReturn(DpdStringquery));
				  logger.info("dpdString"+dpdString);
				  ContactRecordingVO=new ContactRecordingFollowUpVO();
				  loanAmount=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1)).trim());
				  
			    if(loanAmount.equalsIgnoreCase("")){
				     ContactRecordingVO.setLoanAmount(loanAmount);
				 }else{
					  ContactRecordingVO.setLoanAmount(myFormatter.format(myFormatter.parse(loanAmount)).toString());
				 }
				  ContactRecordingVO.setDisbersalDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(2)).trim()));
				  
				 emiAmount=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(3)).trim());
				  
				    if(emiAmount.equalsIgnoreCase("")){
					     ContactRecordingVO.setEmiAmount(emiAmount);
					 }else{
						 
						  ContactRecordingVO.setEmiAmount(myFormatter.format(myFormatter.parse(emiAmount)).toString());
					 }
				  ContactRecordingVO.setTotalNoOfEMI(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(4)).trim()));
				  ContactRecordingVO.setNoOfEMIDue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(5)).trim()));
				  loanAmountOverDue=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(6)).trim());
				  if(loanAmountOverDue.equalsIgnoreCase("")){
					  loanAmountOverDue="0";  
				  }
				  ContactRecordingVO.setTenure(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(7)).trim()));
				  ContactRecordingVO.setDpd(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(8)).trim()));
				  
				  //ContactRecordingVO.setPrincipalOut(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(9)).trim()));
				  principalOutStanding=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(9)).trim());
				  if(principalOutStanding.equalsIgnoreCase("")){
					     ContactRecordingVO.setPrincipalOut(principalOutStanding);
					 }else{
						 Number POS=myFormatter.parse(principalOutStanding);
						  ContactRecordingVO.setPrincipalOut(myFormatter.format(POS));
					 }
				 // ContactRecordingVO.setInterestOverDue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(10)).trim()));
				  interestOverDue=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(10)).trim());
				  if(interestOverDue.equalsIgnoreCase("")){
					     ContactRecordingVO.setInterestOverDue(interestOverDue);
					 }else{
						  ContactRecordingVO.setInterestOverDue(myFormatter.format(myFormatter.parse(interestOverDue)).toString());
					 }
				 // ContactRecordingVO.setPrincipalOverDue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(11)).trim()));
				  principalOverDue=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(11)).trim());
				  if(principalOverDue.equalsIgnoreCase("")){
					     ContactRecordingVO.setPrincipalOverDue(principalOverDue);
					 }else{
						  ContactRecordingVO.setPrincipalOverDue(myFormatter.format(myFormatter.parse(principalOverDue)).toString());
					 }
				  frequency=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(12)).trim());
				 if(frequency.equalsIgnoreCase("M")){
					 frequency="Monthly"; 
				 }else if(frequency.equalsIgnoreCase("Q")){
					 frequency="Quarterly"; 
				 }else if(frequency.equalsIgnoreCase("Y")){
					 frequency="Yearly"; 
				 }
				  ContactRecordingVO.setFrequency(frequency);
				  ContactRecordingVO.setEmiStartDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(13)).trim()));
				  ContactRecordingVO.setEmiEndDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(14)).trim()));
				 // ContactRecordingVO.setInterestRate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(15)).trim()));
				  String interestRate=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(15)).trim());
				  if(interestRate.equalsIgnoreCase("")){
					     ContactRecordingVO.setInterestRate(interestRate);
					 }else{
						  ContactRecordingVO.setInterestRate(myFormatter.format(myFormatter.parse(interestRate)).toString());
					 }
//				  String interestType=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(16)).trim());
//				  if(interestType.equalsIgnoreCase("F")){
//					  interestType="Float";
//				  }else if(interestType.equalsIgnoreCase("E")){
//					  interestType="Effective";
//				  }
//				  ContactRecordingVO.setInterestType(interestType);
				  //ContactRecordingVO.setAssetCost(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(17)).trim()));
				  assetCost=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(16)).trim());
				  if(assetCost.equalsIgnoreCase("")){
					     ContactRecordingVO.setAssetCost(assetCost);
					 }else{
						  ContactRecordingVO.setAssetCost(myFormatter.format(myFormatter.parse(assetCost)).toString());
					 }
				  ContactRecordingVO.setBillingCycle(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(17)).trim()));
				  ContactRecordingVO.setNpaStatus(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(18)).trim()));
				  ContactRecordingVO.setDpdstring(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(dpdString)));
				  
				  Double totalAmountDue=Double.parseDouble(LPP)+Double.parseDouble(CBC)+Double.parseDouble(otherCharge)+Double.parseDouble(loanAmountOverDue);
				  logger.info("totalAmountDue:-----:"+totalAmountDue);
				    if(LPP.equalsIgnoreCase("")){
					     ContactRecordingVO.setLppCharges(LPP);
					 }else{
						  ContactRecordingVO.setLppCharges(myFormatter.format(myFormatter.parse(LPP)).toString());
					 }
				if(CBC.equalsIgnoreCase("")){
					     ContactRecordingVO.setCbcChages(CBC);
					 }else{
						  ContactRecordingVO.setCbcChages(myFormatter.format(myFormatter.parse(CBC)).toString());
					 }
				 if(otherCharge.equalsIgnoreCase("")){
					     ContactRecordingVO.setOtherCharge(otherCharge);
					 }else{
						  ContactRecordingVO.setOtherCharge(myFormatter.format(myFormatter.parse(otherCharge)).toString());
					 }
				 
				  totalAmountDueFormatted=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(totalAmountDue).trim());
				  if(totalAmountDueFormatted.equalsIgnoreCase("")){
					     ContactRecordingVO.setTotalAmountDue(totalAmountDueFormatted);
					 }else{
						  ContactRecordingVO.setTotalAmountDue(myFormatter.format(myFormatter.parse(totalAmountDueFormatted)).toString());
					 }
				  if(CommonFunction.checkNull(subList.get(20)).equalsIgnoreCase("")){
					  ContactRecordingVO.setFlatRate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(20)).trim()));  
				  }else{
					  ContactRecordingVO.setFlatRate(myFormatter.format(myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(20)).trim()))));	  
				  }
				  if(CommonFunction.checkNull(subList.get(21)).equalsIgnoreCase("")){
					  ContactRecordingVO.setEffRate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(21)).trim()));  
				  }else{
					  ContactRecordingVO.setEffRate(myFormatter.format(myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(21)).trim()))));
				  }
//				  if(CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("")){
//					  ContactRecordingVO.setIrr1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(23)).trim()));  
//				  }else{
//					  ContactRecordingVO.setIrr1(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(23)).trim()));  
//				  }
//				  
//				  if(CommonFunction.checkNull(subList.get(24)).equalsIgnoreCase("")){
//					  ContactRecordingVO.setIrr2(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(24)).trim()));  
//				  }else{
//					  ContactRecordingVO.setIrr2(myFormatter.format(myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(24)).trim()))));  
//				  }
				  
				  ContactRecordingVO.setAppDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(22)).trim()));
				  ContactRecordingVO.setSecDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(23)).trim()));
				  ContactRecordingVO.setNoOfEmiPaid(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(24)).trim()));
				  String repaymentMode=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(25)).trim());
				  if(repaymentMode.equalsIgnoreCase("P")){
					  repaymentMode="PDC"; 
				  }else if(repaymentMode.equalsIgnoreCase("O")){
					  repaymentMode="OTC";
				  }
				  else if(repaymentMode.equalsIgnoreCase("D")){
					  repaymentMode="DIRECT DEBIT";
				  }
				  else if(repaymentMode.equalsIgnoreCase("E")){
					  repaymentMode="ECS";
				  }
				  ContactRecordingVO.setRepaymentMode(repaymentMode);
				  String interestType=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(26)).trim());
				  if(interestType.equalsIgnoreCase("F")){
				  interestType="Float";
				  }else if(interestType.equalsIgnoreCase("E")){
				  interestType="Effective";
				  }
//			
				  ContactRecordingVO.setInterestType(interestType);
				  ContactRecordingVO.setRm(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(27)).trim()));
				  ContactRecordingVO.setRmSup(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(28)).trim()));
				  ContactRecordingVO.setReceiptDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(29)).trim()));
				  ContactRecordingVO.setReceiptAmount(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(30)).trim()));
				  ContactRecordingVO.setOverdueEMI(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(31)).trim()));
				  allocationInfoList.add(ContactRecordingVO);
			}
		  }
			  logger.info("In customerContactInfoList......Dao Impl"+allocationInfoList.size());
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		 mainList=null;
		 subList=null;
		 lppChargeQuery=null;
		 cbcquery=null;
		 bccQuery=null;
		 LPP=null;
		 CBC=null;
		 otherCharge=null;
		 query =null;
		 DpdStringval=null;
		 DpdStringquery=null;
		 dpdString=null;
		 ContactRecordingVO=null;
		 loanAmount=null;
		 emiAmount=null;
		 loanAmountOverDue=null;
		 principalOutStanding=null;
		 interestOverDue=null;
		 principalOverDue=null;
		 frequency=null;
		 assetCost=null;
		 totalAmountDueFormatted=null;
	}
	return allocationInfoList;
}


public boolean saveFollowUpTrail(ContactRecordingFollowUpVO followUpVO){

	ArrayList qryList =  new ArrayList();
	boolean status=false;
	ArrayList qrydelete=new ArrayList ();
	StringBuilder bufInsSql =null;
	PrepStmtObject insertPrepStmtObject = null;	
	boolean st1=false;
	String stat = "X";
	ArrayList mainList=new ArrayList();
	ArrayList subList=new ArrayList();
	String oldActionCode=null;
	String esclationCount=null;
	String query="";
	int exclationCountInt=0;
	try{
		logger.info("In saveFollowUpTrail...Dao Impl.");
		 bufInsSql =	new StringBuilder();
		 insertPrepStmtObject = new PrepStmtObject();
		 bufInsSql.append("insert into coll_follow_up_trails (LOAN_ID,ACTION_CODE,REMARKS,MEMO," +
		 		          "ACTION_AMOUNT,ACTION_DATE,NEXT_ACTION_DATE,NEXT_ACTION_TIME,ACTION_BY," +
		 		          "CONTACT_PERSON,CONTACT_MODE,CONTACT_PLACE,PAY_MODE,RECIPT_NO,REC_STATUS," +
		 		          "ACTION_TIME_TAKEN,INSTRUMENT_NO,INSTRUMENT_DATE,CONTACT_BY," +
		 		          "MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
		 bufInsSql.append(" values ( ");
		 bufInsSql.append(" ?," ); //LOAN_ID
		 bufInsSql.append(" ?," ); //ACTION_CODE
		 bufInsSql.append(" ?," ); //REMARKS
		 bufInsSql.append(" ?," ); //MEMO
		 bufInsSql.append(" ?," ); //ACTION_AMOUNT
		 bufInsSql.append(dbo);
		 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		 bufInsSql.append(dbo);
		 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			
		 bufInsSql.append(" ?," ); //NEXT_ACTION_TIME
		 bufInsSql.append(" ?,");  //ACTION_BY
		 bufInsSql.append(" ?," );  //CONTACT_PERSON
		 bufInsSql.append(" ?," );  //CONTACT_MODE
		 bufInsSql.append(" ?," );  //CONTACT_PLACE
		 bufInsSql.append(" ?," );  //PAY_MODE
		 bufInsSql.append(" ?," );  //RECIPT_NO
		 bufInsSql.append(" 'A'," ); //REC_STATUS
		 bufInsSql.append(" ?," );  //ACTION_TIME_TAKEN
		 bufInsSql.append(" ?," );  //INSTRUMENT_NO
		 bufInsSql.append(dbo);
		 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			
		 bufInsSql.append(" ?," ); //CONTACT_BY
		 bufInsSql.append(" ?," ); //MAKER_ID
		 bufInsSql.append(dbo);
		 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			
		 bufInsSql.append(" ?," ); //AUTHOR_ID
		 bufInsSql.append(dbo);
		 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
		  String amount=CommonFunction.checkNull(followUpVO.getActionAmount());
	      if(!amount.equalsIgnoreCase("")){
	    	  amount=myFormatter.parse(StringEscapeUtils.escapeHtml(amount).trim()).toString();  
	      }
		
		 if(CommonFunction.checkNull(followUpVO.getLoanId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getLoanId()).trim()));
		 
		 if(CommonFunction.checkNull(followUpVO.getActionCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(followUpVO.getActionCode());
		 
		 if (CommonFunction.checkNull(followUpVO.getRemarks()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(followUpVO.getRemarks());
			
		 if(CommonFunction.checkNull(followUpVO.getCustomerMemoLine()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getCustomerMemoLine().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getActionAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(amount);
		
		 if(CommonFunction.checkNull(followUpVO.getActionDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getActionDate().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getNxtActionDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getNxtActionDate().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getNxtActionTime()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getNxtActionTime().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getMakerId().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getPersonContacted()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getPersonContacted().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getContactMode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getContactMode().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getPlaceContacted()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getPlaceContacted().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getPaymentMode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getPaymentMode().trim()));
		
		 if (CommonFunction.checkNull(followUpVO.getReceiptNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getReceiptNo().trim()));
			 
		 if(CommonFunction.checkNull(followUpVO.getTotalTimeTaken()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		 else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getTotalTimeTaken().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getInstrumentNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		 else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getInstrumentNo().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getInstrumentDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		 else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getInstrumentDate().trim()));
		
		 if(CommonFunction.checkNull(followUpVO.getLbxUserSearchId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
		 else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(followUpVO.getLbxUserSearchId().trim()));
				
		 if (CommonFunction.checkNull(followUpVO.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(followUpVO.getMakerId());

		 if (CommonFunction.checkNull(followUpVO.getBusinessDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(followUpVO.getBusinessDate());
			
			if (CommonFunction.checkNull(followUpVO.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(followUpVO.getMakerId());

			if (CommonFunction.checkNull(followUpVO.getBusinessDate()).equalsIgnoreCase(""))insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(followUpVO.getBusinessDate());

						
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
		    query="Select LAST_ACTION_CODE,ESCLATION_COUNT from coll_case_dtl where  LOAN_ID='"+CommonFunction.checkNull(followUpVO.getLoanId())+"'";
		    mainList=ConnectionDAO.sqlSelect(query);
		    Iterator< ArrayList> itr=mainList.iterator();
		    while (itr.hasNext()){
		    	subList=itr.next();
		    	if(subList.size()>0){
		    		oldActionCode=CommonFunction.checkNull(subList.get(0));
		    		esclationCount=CommonFunction.checkNull(subList.get(1));
		    	}
		    }
		    if(esclationCount.equalsIgnoreCase("")){
		    	esclationCount="0";
		    	}
		    exclationCountInt=Integer.parseInt(esclationCount);
		    if(oldActionCode.equalsIgnoreCase(CommonFunction.checkNull(followUpVO.getActionCode()))
		    	&& oldActionCode.equalsIgnoreCase("PTP") 
		    	&& CommonFunction.checkNull(followUpVO.getActionCode()).equalsIgnoreCase("PTP")){
		    	exclationCountInt=exclationCountInt+1;
		    }else{
		    	exclationCountInt=1;
		    }
		    bufInsSql =	new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			String exclationFleg=CommonFunction.checkNull(followUpVO.getExclationFlag());
			if(exclationFleg.equalsIgnoreCase("Y")){
				 bufInsSql.append("update coll_case_dtl set ESCALATION_FLAG='N',USER_ID=?,ALLOCATION_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
				
				 bufInsSql.append("LAST_ACTION_CODE=?,LAST_ACTION_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				 bufInsSql.append("NEXT_ACTION_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");	
					
				 bufInsSql.append("ESCLATION_COUNT=? where LOAN_ID=? " ); //LOAN_ID
				
				 if(CommonFunction.checkNull(followUpVO.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getMakerId()).trim()));
				
				 if(CommonFunction.checkNull(followUpVO.getBusinessDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getBusinessDate()).trim()));
				
				 if(CommonFunction.checkNull(followUpVO.getActionCode()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getActionCode()).trim()));
				
				 if(CommonFunction.checkNull(followUpVO.getActionDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getActionDate()).trim()));
			
				 if(CommonFunction.checkNull(followUpVO.getNxtActionDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getNxtActionDate()).trim()));
			
				 
				 if(CommonFunction.checkNull(exclationCountInt).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(exclationCountInt).trim()));
				   
				 if(CommonFunction.checkNull(followUpVO.getLoanId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getLoanId()).trim()));
				    
				 insertPrepStmtObject.setSql(bufInsSql.toString());
				 qryList.add(insertPrepStmtObject);
			}else{
				 bufInsSql.append("update coll_case_dtl set LAST_ACTION_CODE=?,LAST_ACTION_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				 bufInsSql.append("NEXT_ACTION_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");	
						
					
				 bufInsSql.append("ESCLATION_COUNT=? where LOAN_ID=? " ); //LOAN_ID	

				 if(CommonFunction.checkNull(followUpVO.getActionCode()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getActionCode()).trim()));
				
				 if(CommonFunction.checkNull(followUpVO.getActionDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getActionDate()).trim()));
			
				 if(CommonFunction.checkNull(followUpVO.getNxtActionDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getNxtActionDate()).trim()));
			
				 
				 if(CommonFunction.checkNull(exclationCountInt).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(exclationCountInt).trim()));
				  
				 if(CommonFunction.checkNull(followUpVO.getLoanId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getLoanId()).trim()));
				 
				 insertPrepStmtObject.setSql(bufInsSql.toString());
				 qryList.add(insertPrepStmtObject);
			}
			
			if(CommonFunction.checkNull(followUpVO.getActionCode()).equalsIgnoreCase("EC")){
				bufInsSql =	new StringBuilder();
				 insertPrepStmtObject = new PrepStmtObject();
				 
				 bufInsSql.append("UPDATE coll_case_dtl SET ESCALATION_FLAG='Y' , ESCLATION_DATE=");
		 	     bufInsSql.append(dbo);
		 	     bufInsSql.append("STR_TO_DATE(?, '"+dateFormatWithTime+"') ,ESCALATION_REMARK=? WHERE LOAN_ID=?");
				 
				 if(CommonFunction.checkNull(followUpVO.getBusinessDate()).equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
				 else
				 insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getBusinessDate()).trim()));
				 
				 if(CommonFunction.checkNull(followUpVO.getRemarks()).equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
				 else
				 insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getRemarks()).trim()));
					 
				 
				if(CommonFunction.checkNull(followUpVO.getLoanId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getLoanId()).trim()));
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
				
			}
			 
		    logger.info("In saveFollowUpTrail............query.........."+qryList.toString());
		    status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		    
            logger.info("In saveFollowUpTrail......................"+status);
            if(status){
            	if(CommonFunction.checkNull(followUpVO.getActionCode()).equalsIgnoreCase("EC")){
            		String queryReportingTo="Select a.USER_REPORTING_TO from sec_user_m a where a.USER_ID=(Select b.USER_ID from coll_case_dtl b where b.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getLoanId()).trim())+"')";
            		String queryUserEmail="select c.USER_EMAIL  from sec_user_m c where user_id=(Select a.USER_REPORTING_TO from sec_user_m a where a.USER_ID=(Select b.USER_ID from coll_case_dtl b where b.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getLoanId()).trim())+"')) ";
            		String querySystemEmail="Select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EMAIL_USERNAME'";
            		String querySystemPass="Select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EMAIL_PASSWORD'";
            		String querySystemSmtpHost="Select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='SMTP_HOST'";
            		String querySystemSmtpPort="Select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='SMTP_PORT'";
            	
            	String reportingTo=ConnectionDAO.singleReturn(queryReportingTo);
            	String userEmail=ConnectionDAO.singleReturn(queryUserEmail);
            	String systemEmail=ConnectionDAO.singleReturn(querySystemEmail);
            	String systemPass=ConnectionDAO.singleReturn(querySystemPass);
            	String smtpHost=ConnectionDAO.singleReturn(querySystemSmtpHost);
            	String smtpPort=ConnectionDAO.singleReturn(querySystemSmtpPort);
            	String loanNo=ConnectionDAO.singleReturn("Select LOAN_NO from coll_case_dtl where LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(followUpVO.getLoanId()).trim())+"'");
            	
            	logger.info("smtpPort:-------"+smtpPort);
            	logger.info("smtpHost:-------"+smtpHost);
            	logger.info("systemPass:-------"+systemPass);
            	logger.info("systemEmail:-------"+systemEmail);
            	logger.info(" reportingTo userEmail:-------"+userEmail);
            	logger.info("reportingTo:-------"+reportingTo);
            	logger.info("smtpPort:-------"+loanNo);
            	boolean sendFlag=Mail.sendMail(systemEmail,systemPass,smtpHost,smtpPort, "true",userEmail,loanNo+" escalated for action","Dear Sir/Madam,\n \nLoan No:- "+loanNo+" has been escalated in your tray.\nPlease take some appropriate action.\n \nThanks");
            	logger.info("After MAil sendFlag:------------"+sendFlag);
            	queryReportingTo=null;
            	queryUserEmail=null;
            	querySystemPass=null;
            	querySystemSmtpHost=null;
            	querySystemSmtpPort=null;
            	userEmail=null;
            	systemEmail=null;
            	systemPass=null;
            	smtpHost=null;
            	smtpPort=null;
            	loanNo=null;
            	}	
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
		mainList=null;
		subList=null;
		oldActionCode=null;
		esclationCount=null;
		query=null;
	}
	
	return status;
}


	public boolean saveCustomerAddress(CollCustomerAddressVo vo) {
		
	String stat = "X";
	boolean status=false;
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	StringBuilder bufInsSql =	new StringBuilder();
	try{
		if (vo.getCommunicationAddress()!= null && vo.getCommunicationAddress().equals("on")) {
			stat = "A";
		} else {
			stat = "X";
	
		}
		logger.info("saveCustomerAddress()......");
		logger.info("vo.getAddr_type()"+vo.getAddr_type());
		logger.info("getAddr1()"+vo.getAddr1());
		logger.info("getAlternatePhoneNo()"+vo.getAlternatePhoneNo());
		logger.info("pincode()"+vo.getPincode());
		
		logger.info("getPrimaryPhoneNo()"+vo.getPrimaryPhoneNo());
				
			
		bufInsSql.append("insert into coll_address_dtl(BPID,ADDRESS_TYPE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,LANDMARK,NO_OF_YEARS,COMMUNICATION_ADDRESS,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
		bufInsSql.append(" values ( ");
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" ?," );
		bufInsSql.append(" 'A'," );
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		
		
		bufInsSql.append(" ?," );
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
		
		
		if(CommonFunction.checkNull(vo.getBpid()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBpid()).trim());
		
		if(CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getAddr_type()).trim());
		if(CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getAddr1()).trim());
		
		if(CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getAddr2()).trim());
		
		if(CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getAddr3()).trim());
		
		if(CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
		    insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTxtCountryCode()).trim());
		
		if(CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTxtStateCode()).trim());
		
		if(CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTxtDistCode()).trim());
		
		if(CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getPincode()).trim());
		
		if(CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getPrimaryPhoneNo()).trim());
		
		if(CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getAlternatePhoneNo()).trim());
		
		if(CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTollfreeNo()).trim());
		
		if(CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getFaxNo()).trim());
		
		if(CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLandMark()).trim());
		
		if(CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getNoYears()).trim());
						
		if(CommonFunction.checkNull(stat).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(stat).trim());
		
		if(CommonFunction.checkNull(vo.getMakerid()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMakerid()).trim());
		if(CommonFunction.checkNull(vo.getMakerdate()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMakerdate()).trim());
		if(CommonFunction.checkNull(vo.getAuthorid()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getAuthorid()).trim());
		if(CommonFunction.checkNull(vo.getAuthordate()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getAuthordate()).trim());
			
		insertPrepStmtObject.setSql(bufInsSql.toString());
		qryList.add(insertPrepStmtObject);
		status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In saveCustomer......................"+status);
			 	
	}catch(Exception e){
			e.printStackTrace();
		}
	finally
	{
		stat =	null;
		qryList =	null;
		insertPrepStmtObject =	null;
		bufInsSql =	null;
	}
	return status;		
}


 public ArrayList<ContactRecordingFollowUpVO> followUpGridList(ContactRecordingFollowUpVO vo){
	 ArrayList<ContactRecordingFollowUpVO> followUpGridList= new ArrayList<ContactRecordingFollowUpVO>();
	 ArrayList mainList=new ArrayList();
	 ArrayList subList=new ArrayList();
	 StringBuilder query= new StringBuilder();
	 ContactRecordingFollowUpVO ContactRecordingVO=null;
	try{
		
     query.append("Select ");
     query.append(dbo);
     query.append("DATE_FORMAT(ACTION_DATE,'"+dateFormat+"') as ACTION_DATE,");
     query.append(" (Select ACTION_DESC from coll_action_code_m CACM where CFUT.ACTION_CODE=CACM.ACTION_CODE) ACTION_CODE,CONTACT_MODE,CONTACT_PLACE,");
     query.append(dbo);
     query.append("DATE_FORMAT(NEXT_ACTION_DATE,'"+dateFormat+"') as NEXT_ACTION_DATE,NEXT_ACTION_TIME,REMARKS,ACTION_AMOUNT from ");
     query.append("  coll_follow_up_trails CFUT where LOAN_ID='"+vo.getLoanId()+"'   order by FOLLOW_UP_ID desc OFFSET 0 ROWS FETCH next 3 ROWS ONLY");
  
     		
		  logger.info("In followUpGridList......Dao Impl"+query);
		  mainList = ConnectionDAO.sqlSelect(query.toString());
		  int size = mainList.size();
		  for(int i=0;i<size;i++){
			  subList=(ArrayList) mainList.get(i);
			  if(subList.size()>0){
				  ContactRecordingVO=new ContactRecordingFollowUpVO();
				
				  ContactRecordingVO.setActionDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0)).trim()));
				  ContactRecordingVO.setActionCode(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1)).trim()));
				  ContactRecordingVO.setContactMode(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(2)).trim()));
				  ContactRecordingVO.setPlaceContacted(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(3)).trim()));
				  ContactRecordingVO.setNxtActionDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(4)).trim()));
				  ContactRecordingVO.setNxtActionTime(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(5)).trim()));
				  ContactRecordingVO.setRemarks(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(6)).trim()));
                  String actionAmount=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(7)).trim());
				  if(actionAmount.equalsIgnoreCase("")){
					  ContactRecordingVO.setActionAmount(actionAmount);  
				  }else{
					  ContactRecordingVO.setActionAmount(myFormatter.parse(actionAmount).toString());  
				  }
				  followUpGridList.add(ContactRecordingVO);
			}
		  }
			  logger.info("In followUpGridList......Dao Impl"+followUpGridList.size());
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		mainList=null;
		 subList=null;
		 query=null;
		 ContactRecordingVO=null;
	}
	return followUpGridList; 
 }



public ArrayList<CollCustomerAddressVo> addresstype() throws Exception
{  
  ArrayList searchlist = new ArrayList();
  ArrayList addressList = new ArrayList();
  ArrayList list =  new ArrayList();
  CollCustomerAddressVo vo = null;
	try{
		
        String query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='ADDRESS_TYPE' and rec_status ='A'";
        logger.info("In addresstype()......Dao Impl"+query);
          
      	searchlist = ConnectionDAO.sqlSelect(query);
      	int size = searchlist.size();
        logger.info("addresstype " + size);

		for (int i = 0; i < size; i++) {
			list = (ArrayList) searchlist.get(i);
			if (list.size() > 0) {
				vo = new CollCustomerAddressVo();
				vo.setAdd_val(CommonFunction.checkNull(list.get(0)).toString());
				vo.setAdd_desc(CommonFunction.checkNull(list.get(1)).toString());
				addressList.add(vo);            
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		searchlist = null;
		  list = null;
		  vo = null;
	}
	return addressList;
}

//Arun Code Ends Here=======


//Anil Code For Contact Detail Start Here

public ArrayList<CollCustomerAddressVo> searchClassificationData(String loanId){
	
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	ArrayList searchlist = new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
	ArrayList data = new ArrayList();
	CollCustomerAddressVo Vo =null;
	
	ArrayList<CollCustomerAddressVo> detailList = new ArrayList<CollCustomerAddressVo>();
	try {
		logger.info("In searchClassificationData.....................................Dao Impl");

		bufInsSql.append(" select 'Collection',ADDRESS_TYPE,");
		bufInsSql.append(" (Select DISTRICT_DESC from com_district_m where DISTRICT_ID= DISTRICT) DISTRICT," );
		bufInsSql.append(" (Select STATE_DESC from com_state_m where STATE_ID= STATE) STATE," );
		bufInsSql.append(" (Select COUNTRY_DESC from com_country_m where COUNTRY_ID= COUNTRY) COUNTRY," );
		bufInsSql.append(" PRIMARY_PHONE,FAX,PINCODE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3 from coll_address_dtl coll ");
		bufInsSql.append(" join cr_loan_dtl crlt on   crlt.LOAN_ID=coll.BPID where coll.BPID='"+loanId+"' ");
		bufInsSql.append(" UNION select 'Host',ADDRESS_TYPE," );
		bufInsSql.append(" (Select DISTRICT_DESC from com_district_m where DISTRICT_ID= DISTRICT) DISTRICT," );
		bufInsSql.append(" (Select STATE_DESC from com_state_m where STATE_ID= STATE) STATE," );
		bufInsSql.append(" (Select COUNTRY_DESC from com_country_m where COUNTRY_ID= COUNTRY) COUNTRY," );
		bufInsSql.append(" PRIMARY_PHONE,FAX,PINCODE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3 from com_address_m com ");
		bufInsSql.append(" join cr_loan_dtl crlt on  crlt.LOAN_CUSTOMER_ID=com.BPID ");
		bufInsSql.append(" where crlt.LOAN_ID='"+loanId+"' ");

		bufInsSqlTempCount.append("select count(1) from (select 'Collection'AS COLLECTIONS,ADDRESS_TYPE,DISTRICT,STATE,COUNTRY,PRIMARY_PHONE,FAX,PINCODE,ADDRESS_LINE1 from coll_address_dtl coll "); 
		bufInsSqlTempCount.append("join cr_loan_dtl crlt on   crlt.LOAN_CUSTOMER_ID=coll.BPID where coll.BPID='"+loanId+"' ");
		bufInsSqlTempCount.append("UNION select 'Host',ADDRESS_TYPE,DISTRICT,STATE,COUNTRY,PRIMARY_PHONE,FAX,PINCODE,ADDRESS_LINE1 from com_address_m com ");
		bufInsSqlTempCount.append("join cr_loan_dtl crlt on  crlt.LOAN_CUSTOMER_ID=com.BPID ");
		bufInsSqlTempCount.append("where crlt.LOAN_ID='"+loanId+"') AS COLLS ");

		
		logger.info("search Query :::::::::::::::::::::::::::"+ bufInsSql);
		logger.info("bufInsSqlTempCount :::::::::::::::::::::"+bufInsSqlTempCount.toString());
        count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
        logger.info("count:::::::::::::::::::::::::::::::::::::::::::"+count);
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		
		logger.info("IN searchClassificationData() search query1 ::::: "+ bufInsSql.toString());
		int size = searchlist.size();
		logger.info("searchClassificationData " + size);
		
		for (int i = 0; i < size; i++) {
			data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				Vo = new CollCustomerAddressVo();
				Vo.setFromTable(CommonFunction.checkNull(data.get(0)));
                Vo.setAddr_type(CommonFunction.checkNull(data.get(1)));
				Vo.setTxtDistCode(CommonFunction.checkNull(data.get(2)));
				Vo.setTxtStateCode(CommonFunction.checkNull(data.get(3)));
				Vo.setTxtCountryCode(CommonFunction.checkNull(data.get(4)));
				Vo.setPrimaryPhoneNo(CommonFunction.checkNull(data.get(5)));
				Vo.setFaxNo(CommonFunction.checkNull(data.get(6)));
				Vo.setPincode(CommonFunction.checkNull(data.get(7)));
				Vo.setAddr1(CommonFunction.checkNull(data.get(8))+" "+CommonFunction.checkNull(data.get(9))+"  "+CommonFunction.checkNull(data.get(10)));
			    Vo.setTotalRecordSize(count);
				detailList.add(Vo);
			}

		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		searchlist =null;
		bufInsSql =null;
		bufInsSqlTempCount =null;
		data =null;
		Vo =null;
	}
	return detailList;

}
//Anil Code Ends Here
/*Supervisor Review Code Start here */
public ArrayList<ContactRecordingSearchVO> supervisorReviewSearchList(ContactRecordingSearchVO vo){
	
	String loanNo="";
	String customerName="";
	String product="";
	String queue="";
	String pos1="";
	String pos2="";
	String dpd1="";
	String dpd2="";
	String user="";
	String tos1="";
	String tos2="";
	String npastage="";
	String customertype="";
	String customercategory="";
	String assigndatefrom="";
	String assigndateto="";
	String balanceprincipal="";
	StringBuilder bufInsSql =null;
	StringBuilder bufInsSqlTempCount=null;
	StringBuilder lppChargeQuery= new StringBuilder();
	StringBuilder cbcquery= new StringBuilder();
	StringBuilder bccQuery= new StringBuilder();
	String LPP="";
	String CBC="";
	String otherCharge="";
	ArrayList data = new ArrayList();
	int startRecordIndex=0;
	int endRecordIndex = no;
	int count=0;
	ArrayList searchlist = new ArrayList();
	ContactRecordingSearchVO conteactRecVO = null;
	
	ArrayList<ContactRecordingSearchVO> detailListGrid= new ArrayList<ContactRecordingSearchVO>();
	
	try {
		logger.info("In contactRecordingSearchList().....................................Dao Impl");

		 bufInsSql = new StringBuilder();
		 bufInsSqlTempCount = new StringBuilder();
		
		 if(!CommonFunction.checkNull(vo.getDpd1()).equalsIgnoreCase(""))
	   	    {
	      dpd1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd1()).trim())).toString();
	      logger.info("In contactRecordingSearchList......dpd1  "+dpd1);         
	   	    }
	      if(!CommonFunction.checkNull(vo.getDpd2()).equalsIgnoreCase(""))
	 	    {
	    	  dpd2= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd2()).trim())).toString();
	       logger.info("In contactRecordingSearchList......dpd2  "+dpd2);         
	 	    }
	      if(!CommonFunction.checkNull(vo.getPos1()).equalsIgnoreCase(""))
	 	    {
	    	  pos1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos1()).trim())).toString();
	         logger.info("In contactRecordingSearchList......pos1  "+pos1);         
	 	    }
	      if(!CommonFunction.checkNull(vo.getPos2()).equalsIgnoreCase(""))
	 	    {
	    	  pos2= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos2()).trim())).toString();
	        logger.info("In contactRecordingSearchList......pos2  "+pos2);         
	 	    }
	      if(!CommonFunction.checkNull(vo.getBalanceprincipal()).equalsIgnoreCase(""))
	 	    {
	    	  balanceprincipal= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBalanceprincipal()).trim())).toString();
	       logger.info("In contactRecordingSearchList......balanceprincipal  "+balanceprincipal);         
	 	    }else{
	 	      balanceprincipal=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBalanceprincipal()).trim());
	 	    }
	     
	      loanNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanno()).trim());
	      customerName=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim());
	      product=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getProduct()).trim());
	    
	      user=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxUserSearchId()).trim());
	      npastage=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNpastage()).trim());
	      customertype=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustype()).trim());
	      customercategory=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustcategory()).trim());
	      logger.info("vo.getCustcategory()"+vo.getCustcategory());         
	     
	      queue=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxQueuesearchId()).trim());
	      assigndateto=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAssignto()).trim());
	      assigndatefrom=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAssignfrom()).trim());
		 
		//Select CLD.LOAN_NO,CLD.LOAN_ID,cdcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,CCD.QUEUE_CODE,CCD.USER_ID,ESCALATION_FLAG From cr_loan_dtl CLD left join coll_case_dtl CCD ON CLD.LOAN_NO=CCD.LOAN_NO join cr_deal_customer_m cdcm on cdcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' AND CCD.USER_ID='ANIL' or ESCALATION_FLAG='Y'
		  	lppChargeQuery.append("Select  (");
		  	lppChargeQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
		  	lppChargeQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		  	lppChargeQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) LPP");
		  	lppChargeQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+vo.getLoanId()+"' AND ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109'");
			cbcquery.append("Select  (");
			cbcquery.append("ISNULL(ADVICE_AMOUNT,0)-");
			cbcquery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
			cbcquery.append("ISNULL(AMOUNT_IN_PROCESS,0)) CBC");
			cbcquery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+vo.getLoanId()+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110'");
			bccQuery.append("Select  (");
			bccQuery.append("ISNULL(ADVICE_AMOUNT,0)-");
			bccQuery.append("ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
			bccQuery.append("ISNULL(AMOUNT_IN_PROCESS,0)) OTHER_CHARGE");
			bccQuery.append(" FROM cr_txnadvice_dtl WHERE LOAN_ID='"+vo.getLoanId()+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' ");
			bccQuery.append(" AND CHARGE_CODE_ID NOT IN ('109','110','7')");
			LPP=CommonFunction.checkNull(ConnectionDAO.singleReturn(lppChargeQuery.toString()));
			CBC=CommonFunction.checkNull(ConnectionDAO.singleReturn(cbcquery.toString()));
			otherCharge=CommonFunction.checkNull(ConnectionDAO.singleReturn(bccQuery.toString()));
			if(LPP.equalsIgnoreCase("")){
			LPP="0";	
			}
			if(CBC.equalsIgnoreCase("")){
			CBC="0";		
			}
			if(otherCharge.equalsIgnoreCase("")){
			otherCharge="0";
			}
		     String sumCharges=CommonFunction.checkNull(Double.parseDouble(CBC)+Double.parseDouble(LPP)+Double.parseDouble(otherCharge));
		     logger.info("LPP:---"+LPP)  ;
		     logger.info("CBC:---"+CBC)  ;
		     logger.info("otherCharge:---"+otherCharge)  ;
		     logger.info("sumCharges:---"+sumCharges)  ;
		   
		     bufInsSql.append("Select CLD.LOAN_NO,CLD.LOAN_ID,gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,");
		 	 bufInsSql.append("ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+");
		 	 bufInsSql.append("ISNULL('"+sumCharges+"',0) as activePayment ,");
			 bufInsSql.append(" CCD.QUEUE_CODE,CCD.USER_ID,ESCALATION_FLAG " );
					 
			 bufInsSql.append("From cr_loan_dtl CLD left join coll_case_dtl CCD ");
			 bufInsSql.append(" ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
			 bufInsSql.append(" where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' AND CCD.USER_ID IN (select USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+vo.getMakerId()+"' )  ");
			 
			
			bufInsSqlTempCount.append("Select count(1) from (Select CLD.LOAN_NO,CLD.LOAN_ID,gcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,");
		 	bufInsSqlTempCount.append("ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)+");
		 	bufInsSqlTempCount.append("ISNULL('"+sumCharges+"',0) as activePayment ,");
			bufInsSqlTempCount.append(" CCD.QUEUE_CODE,CCD.USER_ID,ESCALATION_FLAG From cr_loan_dtl CLD left join coll_case_dtl CCD ");
			bufInsSqlTempCount.append(" ON CLD.LOAN_NO=CCD.LOAN_NO join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
			bufInsSqlTempCount.append(" where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' AND CCD.USER_ID IN (select USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+vo.getMakerId()+"' )  ");
			
			
			
			if((!(loanNo.trim().equalsIgnoreCase("")))) {
		         bufInsSql.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
		         bufInsSqlTempCount.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
		    	 }
		 	if((!(assigndatefrom.trim().equalsIgnoreCase("")))) {
		 	  bufInsSql.append(" AND ");
		 	  bufInsSql.append(dbo);
		 	  bufInsSql.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+assigndatefrom+"' ");
		 	bufInsSqlTempCount.append(" AND ");
		 	  bufInsSqlTempCount.append(dbo);
		 	  bufInsSqlTempCount.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+assigndatefrom+"' ");
		      }
		 	if((!(assigndateto.trim().equalsIgnoreCase("")))) {
		   	   bufInsSql.append(" AND ");
		 	  bufInsSql.append(dbo);
		 	  bufInsSql.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+assigndateto+"' ");
		     	bufInsSqlTempCount.append(" AND ");
		 	  bufInsSqlTempCount.append(dbo);
		 	  bufInsSqlTempCount.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+assigndateto+"' ");
		     }
		    if((!(customerName.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND gcm.CUSTOMER_NAME like '%"+customerName+"%'  ");
		    	  bufInsSqlTempCount.append(" AND gcm.CUSTOMER_NAME like '%"+customerName+"%'  ");
		      }
			if((!(product.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_PRODUCT='"+product+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_PRODUCT='"+product+"' ");
	          }
			if((!(dpd2.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CLD.LOAN_DPD >= '"+dpd2+"' ");
		    	  bufInsSqlTempCount.append(" AND CLD.LOAN_DPD >= '"+dpd2+"' ");
		      }
			if((!(dpd1.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_DPD <= '"+dpd1+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_DPD <= '"+dpd1+"' ");
	          }
				
			if((!(user.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CCD.USER_ID ='"+user+"' ");
		    	  bufInsSqlTempCount.append(" AND CCD.USER_ID ='"+user+"' ");
		      }
			if((!(pos2.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '"+pos2+"' ");
		    	  bufInsSqlTempCount.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '"+pos2+"' ");
		      }
			if((!(pos1.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+pos1+"' ");
	    	  bufInsSqlTempCount.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+pos1+"' ");
	          }
			/*if((!(tos2.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0) >= '"+tos2+"' ");
		    	  bufInsSqlTempCount.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0)   >= '"+tos2+"' ");
		      }
			if((!(tos1.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0) <= '"+tos1+"' ");
		    	  bufInsSqlTempCount.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0) <= '"+tos1+"' ");
		      }
			if((!(npastage.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.NPA_FLAG  ='"+npastage+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.NPA_FLAG  ='"+npastage+"' ");
	          }*/
			if((!(customertype.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
	         }
			if((!(customercategory.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND gcm.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
		    	  bufInsSqlTempCount.append(" AND gcm.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
		      }
			if((!(balanceprincipal.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
		    	  bufInsSqlTempCount.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
		       }
			if((!(queue.trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
		    	  bufInsSqlTempCount.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
		      }
			
				bufInsSqlTempCount.append(" ) as t ");
			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
		  
			/*if(( loanNo.trim()=="" && customerName.trim()==""&& 
					product.trim()=="" && queue.trim()==""&& 
					 user.trim()=="" && npastage.trim()==""&& 
					 customertype.trim()=="" && customercategory.trim()==""&& 
					 balanceprincipal.trim()=="" &&  assigndateto.trim()==""&& 
					 loanNo.trim()=="" && customerName.trim()==""&& 
					 assigndatefrom.trim()=="" ) 
					 || (loanNo.trim().equalsIgnoreCase("") && customerName.trim().equalsIgnoreCase("")||
							 product.trim().equalsIgnoreCase("") && queue.trim().equalsIgnoreCase("")||
							 user.trim().equalsIgnoreCase("") && npastage.trim().equalsIgnoreCase("")||
							 customertype.trim().equalsIgnoreCase("") && customercategory.trim().equalsIgnoreCase("")||
							 balanceprincipal.trim().equalsIgnoreCase("") &&  assigndateto.trim().equalsIgnoreCase("")||
							 assigndatefrom.trim().equalsIgnoreCase("") ) 
					 || vo.getCurrentPageLink()>1)
			{*/
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			
				if(vo.getCurrentPageLink()>1)
				{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				}
			
			
				
				bufInsSql.append(" ORDER BY CLD.LOAN_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				logger.info("Search supervisorReviewSearchList query for SQL SERVER : " + bufInsSql.toString());

				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
		
			//}
			
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
 	     logger.info("count---RECORDS----------->"+count);
	     logger.info("search Query...." + bufInsSql);
	     int size = searchlist.size();
		logger.info("searchLIst size:----" + size);
		for (int i = 0; i < size; i++) {
			logger.info("searchQueueEdit "+ searchlist.get(i).toString());
			data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				conteactRecVO  = new ContactRecordingSearchVO();
				   
		         logger.info("Exclation Flag:000---"+CommonFunction.checkNull(data.get(7)));
				
					conteactRecVO.setLoanno("<a href=#" +CommonFunction.checkNull(data.get(1).toString()).trim()+" onclick="+"openRecordingTrailFromReview('"+CommonFunction.checkNull(data.get(1).toString()).trim()+"','"+CommonFunction.checkNull(data.get(7))+"')"+">"+CommonFunction.checkNull(data.get(0)).toString().trim()+"</a>");
			
				conteactRecVO.setCustomername(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2)).trim()));
		    	  
		    	
		    		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).equalsIgnoreCase("")){
						
						//String gpos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3)));
						conteactRecVO.setPos1(CommonFunction.checkNull(data.get(3)));
						}else{

						//Number gpos =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
														
						conteactRecVO.setPos1(myFormatter.format(myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim())));
						}
		    		 if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).equalsIgnoreCase("")){
		 				
		       		 // String gtos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4)));
		       		conteactRecVO.setTos1(myFormatter.format(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4)))));
		   			}else{

		   				//Number gtos =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
		   				conteactRecVO.setTos1(myFormatter.format(myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim())));
		   			
		   				}
		    		 conteactRecVO.setQueue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5)).trim()));
		    		 conteactRecVO.setAssignto(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(6)).trim()));
		    		 conteactRecVO.setExclationFlag(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(7)).trim()));
		    	  conteactRecVO.setTotalRecordSize(count);
		    	  detailListGrid.add(conteactRecVO);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		loanNo= null;
		customerName= null;
		product= null;
		queue= null;
		pos1= null;
		pos2= null;
		dpd1= null;
		dpd2= null;
		 user= null;
		tos1= null;
		tos2= null;
		npastage= null;
		customertype= null;
		customercategory= null;
		assigndatefrom= null;
		assigndateto= null;
		balanceprincipal= null;
		bufInsSql =null;
		bufInsSqlTempCount=null;
		lppChargeQuery= null;
		cbcquery= null;
		bccQuery= null;
		LPP= null;
		CBC= null;
		otherCharge= null;
		data = null;
		searchlist = null;
		conteactRecVO = null;
	}
	return detailListGrid;
}
/*Supervisor Review Code Ends here */

//Anil Code For Classification Process Start Here
public ArrayList<PaymentDetailsVo> paymentDetails(String loanId){
	
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	ArrayList searchlist = new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
	ArrayList data = new ArrayList();
	PaymentDetailsVo Vo = null;
	
	ArrayList<PaymentDetailsVo> detailList = new ArrayList<PaymentDetailsVo>();
	try {
		logger.info("In paymentDetails.....................................Dao Impl");
		bufInsSql.append("select ");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(pmnt.PMNT_DATE,'"+dateFormat+"') as PAYMENT_DATE," );
		bufInsSql.append("pmnt.PMNT_AMOUNT,CASE INSTRUMENT_MODE WHEN 'Q' THEN 'CHEQUE' WHEN 'C' " );
		bufInsSql.append("THEN 'CASH' WHEN 'D' THEN 'DD' WHEN 'N' THEN 'NEFT' WHEN 'R' THEN 'RTGS'" );
		bufInsSql.append("WHEN 'S' THEN 'SUSPENSE' WHEN 'B' THEN 'DIRECT DEBIT' WHEN 'E' THEN 'ECS' ");
		bufInsSql.append("WHEN 'O' THEN 'OTHER' END PAYMENT_MODE from cr_pmnt_dtl pmnt join cr_txnadvice_dtl txn ");
		bufInsSql.append(" ON pmnt.TXNADVICEID=txn.TXNADVICE_ID   JOIN cr_instrument_dtl CID ON  CID.INSTRUMENT_ID=pmnt.INSTRUMENT_ID WHERE txn.LOAN_ID='"+loanId+"' and  pmnt.rec_status <> 'X' and txn.advice_type='R' order by pmnt.PMNT_DATE desc ");
		  
		bufInsSqlTempCount.append("select Count(1) from (" );
		bufInsSqlTempCount.append("select ");
		bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("DATE_FORMAT(pmnt.PMNT_DATE,'"+dateFormat+"') as PAYMENT_DATE," );
		bufInsSqlTempCount.append("pmnt.PMNT_AMOUNT,CASE INSTRUMENT_MODE WHEN 'Q' THEN 'CHEQUE' WHEN 'C' " );
		bufInsSqlTempCount.append("THEN 'CASH' WHEN 'D' THEN 'DD' WHEN 'N' THEN 'NEFT' WHEN 'R' THEN 'RTGS'" );
		bufInsSqlTempCount.append("WHEN 'S' THEN 'SUSPENSE' WHEN 'B' THEN 'DIRECT DEBIT' WHEN 'E' THEN 'ECS' ");
		bufInsSqlTempCount.append("WHEN 'O' THEN 'OTHER' END PAYMENT_MODE from cr_pmnt_dtl pmnt join cr_txnadvice_dtl txn ");
		bufInsSqlTempCount.append(" ON pmnt.TXNADVICEID=txn.TXNADVICE_ID   JOIN cr_instrument_dtl CID ON  CID.INSTRUMENT_ID=pmnt.INSTRUMENT_ID WHERE txn.LOAN_ID='"+loanId+"' and  pmnt.rec_status <> 'X' and txn.advice_type='R'");
		bufInsSqlTempCount.append(	") A ");
		
		logger.info("search Query...." + bufInsSql);
		logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
        count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
        logger.info("count:::::::::::::::::::::::::::::::::::::::::::"+count);
		logger.info("query::::"+bufInsSql);
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		
		logger.info("IN paymentDetails():::::::::::::: "+ bufInsSql.toString());
		int size = searchlist.size();
		logger.info("paymentDetails " + size);
		
		for (int i = 0; i < size; i++) {
			data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				Vo = new PaymentDetailsVo();
				Vo.setPaymentDate(CommonFunction.checkNull(data.get(0)));
                Vo.setPaymentAmount(CommonFunction.checkNull(data.get(1)));
                Vo.setPaymentMode(CommonFunction.checkNull(data.get(2)));
			    Vo.setTotalRecordSize(count);
				detailList.add(Vo);
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
		data = null;
		Vo = null;
	}
	return detailList;

}




//Kanika Follow up trails details popup data
public ArrayList<CRFollowUpTrailsDtlVO> followUpTrailsDtlData(CRFollowUpTrailsDtlVO vo)  throws Exception{
	
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = 12;
	StringBuilder bufInsSql = new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
	ArrayList searchlist = new ArrayList();
	ArrayList data = new ArrayList();
	CRFollowUpTrailsDtlVO Vo =null;
	
	ArrayList<CRFollowUpTrailsDtlVO> detailList = new ArrayList<CRFollowUpTrailsDtlVO>();
	try {
		logger.info("In followUpTrailsDtlData().....................................Dao Impl");
		bufInsSql.append("Select ");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(ACTION_DATE,'"+dateFormat+"') as ACTION_DATE,(Select ACTION_DESC from coll_action_code_m CACM where CFUT.ACTION_CODE=CACM.ACTION_CODE) ACTION_CODE,");
		bufInsSql.append("CONTACT_MODE,CONTACT_PLACE,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(NEXT_ACTION_DATE,'"+dateFormat+"') as NEXT_ACTION_DATE,NEXT_ACTION_TIME,ACTION_AMOUNT,ACTION_TIME_TAKEN,CONTACT_BY,CONTACT_PERSON,REMARKS from "); 
		bufInsSql.append(" coll_follow_up_trails CFUT where  LOAN_ID= '"+vo.getLoanId()+"'  order by ACTION_DATE desc");
	
        bufInsSqlTempCount.append("SELECT count(1) from coll_follow_up_trails where  LOAN_ID= '"+vo.getLoanId()+"'");
        logger.info("search Query...." + bufInsSql);
		logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
        count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		
		logger.info("IN followUpTrailsDtlData() search query1 ### "+ bufInsSql.toString());
		logger.info("followUpTrailsDtlData-------count " + count);
	
	logger.info("query : "+bufInsSql);
	searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
	int size = searchlist.size();
		for (int i = 0; i < size; i++) {
			logger.info("followUpTrailsDtlData "+ searchlist.get(i).toString());
			data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				Vo = new CRFollowUpTrailsDtlVO();

				Vo.setCheckBoxDis("<input type="+"checkbox"+" name=checkId id=checkId  value="+ CommonFunction.checkNull(data.get(0).toString()).trim() + " />");
				Vo.setActionDate(CommonFunction.checkNull(data.get(0)));
				Vo.setActionCode(CommonFunction.checkNull(data.get(1)));
				Vo.setContactMode(CommonFunction.checkNull(data.get(2)));
				Vo.setContactPlace(CommonFunction.checkNull(data.get(3)));
				Vo.setNxtActionDate(CommonFunction.checkNull(data.get(4)));
				Vo.setNxtActionTime(CommonFunction.checkNull(data.get(5)));
				Vo.setActionAmt(CommonFunction.checkNull(data.get(6)));
				Vo.setActionTime(CommonFunction.checkNull(data.get(7)));
				Vo.setContactBy(CommonFunction.checkNull(data.get(8)));
				Vo.setContactTo(CommonFunction.checkNull(data.get(9)));
				Vo.setRemarks(CommonFunction.checkNull(data.get(10)));
			    Vo.setTotalRecordSize(count);
				detailList.add(Vo);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		bufInsSql =null;
		bufInsSqlTempCount =null;
		searchlist =null;
		data =null;
		Vo =null;
	}
	return detailList;


}

//end of Follow up trails
//Fore Closure Data
public ArrayList<ForeClosureVo> foreClosureData(String companyId, String loanId, String effectiveDate, String closureType)
{
	logger.info("In foreClosureData.............Dao Impl");

	logger.info("Effective Date: " + effectiveDate);
	//CallableStatement cst = null;
	ForeClosureVo closureVo = new ForeClosureVo();
	ArrayList<ForeClosureVo> duesRefundsList = new ArrayList<ForeClosureVo>();
	StringBuilder query = new StringBuilder();
	String repayEffDateStr = "";
	String queryScheme ="";
	BigDecimal balancePrincipal =null;
	BigDecimal overduePrincipal =null;
	BigDecimal overdueInstallments =null;
	BigDecimal interestTillDate =null;
	BigDecimal unbilledInstallments =null;
	BigDecimal foreClosurePenalty =null;
	BigDecimal otherDues=null;
	BigDecimal otherRefunds =null;
	BigDecimal lpInterest =null;
	BigDecimal lppAmt =null;
	BigDecimal netRecPay =null;
	//Connection con = ConnectionDAO.getConnection();
	ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		String bprincipal="";
		String ovprincipal="";
    	String ovinstallments="";  
    	String ubinstallments="";
 		String otherdues="";
     	String gapintrst="";  
     	String repaypenalty="";
		String lpintrst="";
    	String oldamt="";  
       	String otherpayable="";  
     	String securitydeposit="";
		String securitydepositintrst="";
    	String securitydeposittds=""; 
       	String securitydepositgapintrst="";  
     	String securitydepositgaptds="";
	
	try {
		logger.info("In EARLY_CLOSURE_DETAIL Procedure");
		//cst = con.prepareCall("call EARLY_CLOSURE_DETAIL(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		 in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(companyId).trim()));//I_COMPANY_ID
		 in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()));//I_LOAN_ID
		 	String aDate=StringEscapeUtils.escapeSql(CommonFunction.checkNull(effectiveDate));
		 	String date=aDate.substring(6)+"-"+aDate.substring(3,5)+"-"+aDate.substring(0,2);
		    in.add(date);//I_EFFECTIVE_DATE
		    out.add( bprincipal);//O_BALANCE_PRINCIPAL
		    out.add( ovprincipal);//O_OVERDUE_PRINCIPAL
		    out.add(ovinstallments);//O_OVERDUE_INSTALLMENTS
		    out.add(ubinstallments);//O_UNBILLED_INSTALLMENTS
		    out.add( otherdues);//O_OTHER_DUES
		    out.add(gapintrst);//O_GAP_INTEREST
		    out.add(repaypenalty);//O_PREPAY_PENALTY
			out.add(lpintrst);//LP Interest or O_LOCKIN_PERIOD_INTEREST
			out.add(oldamt);//OD Amount or LPP Amount or O_OD_AMOUNT
			out.add( otherpayable);//O_OTHER_PAYABLES
			out.add(securitydeposit);//O_SECURE_DEPOSIT
			out.add(securitydepositintrst);//O_SECURE_DEPOSIT_INTEREST
			out.add( securitydeposittds);//O_SECURE_DEPOSIT_TDS
			out.add(securitydepositgapintrst);//O_SECURE_DEPOSIT_GAP_INTEREST
			out.add(securitydepositgaptds);//O_SECURE_DEPOSIT_GAP_TDS
		    out.add(s1);//O_ERRORFLAG
		    out.add(s2);//O_ERRORMSG

		
		Number balPrincipal=0.0;
		Number overduePrinci=0.0;
		Number overdueInst=0.0;
		Number unbilledInst=0.0;
		Number othDues=0.0;
		Number intTillDate=0.0;
		Number foreClosurePennalty=0.0;
		Number othRef=0.0;
		Number secDeposit=0.0;
		Number secDepoInt=0.0;
		Number secDepTDS=0.0;
		Number gapSDInt=0.0;
		Number gapSDtds=0.0;
		Number interestTillLP=0.0;
		Number lppAmount=0.0;
		outMessages=(ArrayList) ConnectionDAO.callSP("EARLY_CLOSURE_DETAIL",in,out);
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(4)).trim()).equalsIgnoreCase(""))
		{
			closureVo.setBalancePrincipal("0");
		}
		else
		{
			balPrincipal = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(4)).trim()));
			closureVo.setBalancePrincipal(myFormatter.format(balPrincipal));
		}
		
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(5)).trim()).equalsIgnoreCase(""))
		{
			closureVo.setOverduePrincipal("0");
		}
		else
		{
			overduePrinci = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(5)).trim()));
			closureVo.setOverduePrincipal(myFormatter.format(overduePrinci));
		}
		
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(6)).trim()).equalsIgnoreCase(""))
			closureVo.setOverdueInstallments("0");
		else
		{
			overdueInst = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(6)).trim()));
			closureVo.setOverdueInstallments(myFormatter.format(overdueInst));
		}
		
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(7)).trim()).equalsIgnoreCase(""))
			closureVo.setUnBilledInstallments("0");
		else
		{
			unbilledInst = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(7)).trim()));
			closureVo.setUnBilledInstallments(myFormatter.format(unbilledInst));
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(8)).trim()).equalsIgnoreCase(""))
			closureVo.setOtherDues("0");
		else
		{
			othDues = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(8)).trim()));
			closureVo.setOtherDues(myFormatter.format(othDues));
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(9)).trim()).equalsIgnoreCase(""))
			closureVo.setInterestTillDate("0");
		else
		{
			intTillDate = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(9)).trim()));
			closureVo.setInterestTillDate(myFormatter.format(intTillDate));
		}
		if(closureType.equalsIgnoreCase("T"))
		{
			if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(10)).trim()).equalsIgnoreCase(""))
				closureVo.setForeClosurePenalty("0");
			else
			{
				foreClosurePennalty = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(10)).trim()));
				closureVo.setForeClosurePenalty(myFormatter.format(foreClosurePennalty));
			}
		}
		if(closureType.equalsIgnoreCase("C"))
		{
			closureVo.setForeClosurePenalty("0");
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(11)).trim()).equalsIgnoreCase(""))
			closureVo.setInterstTillLP("0");
		else
		{
			interestTillLP = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(11)).trim()));
			closureVo.setInterstTillLP(myFormatter.format(interestTillLP));
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(12)).trim()).equalsIgnoreCase(""))
			closureVo.setLppAmount("0");
		else
		{
			lppAmount = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(12)).trim()));
			closureVo.setLppAmount(myFormatter.format(lppAmount));
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(13)).trim()).equalsIgnoreCase(""))
			closureVo.setOtherRefunds("0");
		else
		{
			othRef = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(13)).trim()));
			closureVo.setOtherRefunds(myFormatter.format(othRef));
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(14)).trim()).equalsIgnoreCase(""))
			closureVo.setSecureDeposit("0");
		else
		{
			secDeposit = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(14)).trim()));
			closureVo.setSecureDeposit(myFormatter.format(secDeposit));
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(15)).trim()).equalsIgnoreCase(""))
			closureVo.setSecureDepositInterest("0");
		else
		{
			secDepoInt = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(15)).trim()));
			closureVo.setSecureDepositInterest(myFormatter.format(secDepoInt));
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(16)).trim()).equalsIgnoreCase(""))
			closureVo.setSecureDepositTDS("0");
		else
		{
			secDepTDS = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(16)).trim()));
			closureVo.setSecureDepositTDS(myFormatter.format(secDepTDS));
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(17)).trim()).equalsIgnoreCase(""))
			closureVo.setGapSDInterest("0");
		else
		{
			gapSDInt = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(17)).trim()));
			closureVo.setGapSDInterest(myFormatter.format(gapSDInt));
		}
		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(18)).trim()).equalsIgnoreCase(""))
			closureVo.setGapSDTDS("0");
		else
		{
			gapSDtds = myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(18)).trim()));
			closureVo.setGapSDTDS(myFormatter.format(gapSDtds));
		}
		
		balancePrincipal = new BigDecimal(balPrincipal.toString());
		overduePrincipal = new BigDecimal(overduePrinci.toString());
		overdueInstallments = new BigDecimal(overdueInst.toString());
		interestTillDate = new BigDecimal(intTillDate.toString());
		unbilledInstallments = new BigDecimal(unbilledInst.toString());
		foreClosurePenalty = new BigDecimal(foreClosurePennalty.toString());
		otherDues = new BigDecimal(othDues.toString());
		otherRefunds = new BigDecimal(othRef.toString());
		lpInterest = new BigDecimal(interestTillLP.toString());
		lppAmt = new BigDecimal(lppAmount.toString());
		netRecPay = balancePrincipal.add(overdueInstallments.add(interestTillDate.add(unbilledInstallments.add(foreClosurePenalty.add(otherDues.add(lpInterest.add(lppAmt))))))).subtract(otherRefunds) ;
		closureVo.setNetReceivablePayable(myFormatter.format(myFormatter.parse(netRecPay.toString())));
		
		logger.info("Values from Procedure Set in VO");
		s1 = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(19)).trim());
		s2 = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(outMessages.get(20)).trim());
		logger.info("s1: " + s1);
		logger.info("s2: " + s2);
		logger.info("Size of List: " + duesRefundsList.size());
		
		query.append("select ");
		query.append(dbo);
		query.append("DATE_FORMAT(d.LOAN_REPAY_EFF_DATE,'"+dateFormat+"') as LOAN_REPAY_EFF_DATE from cr_loan_dtl d where d.LOAN_ID="+StringEscapeUtils.escapeSql(loanId));
		repayEffDateStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
		
		queryScheme = "select s.TERMINATION_LOCKIN_PERIOD from cr_loan_dtl d,cr_scheme_m s where d.LOAN_SCHEME=s.SCHEME_ID and d.loan_id="+StringEscapeUtils.escapeSql(loanId);
		int lockinPeriodStr = Integer.parseInt(CommonFunction.checkNull(ConnectionDAO.singleReturn(queryScheme)));
		
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateForDisbursal);
		Calendar cal = Calendar.getInstance();
		try {
			  Date repayEffDate = dateFormat1.parse(repayEffDateStr);
			  logger.info("repayEffDate: "+repayEffDate);
			  cal.setTime(repayEffDate);
			  cal.add(Calendar.MONTH, lockinPeriodStr);
			  Date repayEffDateNew = cal.getTime();
			  logger.info("repayEffDateNew: "+repayEffDateNew);
			  Date businessDate = dateFormat1.parse(effectiveDate);
			  logger.info("businessDate: "+businessDate);
			  if(repayEffDateNew.after(businessDate) || repayEffDateNew.equals(businessDate))
			  {
				  closureVo.setEarlyClosureWarn("T");
				  logger.info("Lockin period Valid");
			  }
			  else
			  {
				  closureVo.setEarlyClosureWarn("F");
				  logger.info("Lockin period Invalid");
			  }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  if(s1.equalsIgnoreCase("E"))
			  closureVo.setProcWarning(s2);
		  duesRefundsList.add(closureVo);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		//cst = null;
		closureVo =null;
		query =null;
		repayEffDateStr =null;
		queryScheme =null;
		balancePrincipal =null;
		overduePrincipal =null;
		overdueInstallments =null;
		interestTillDate =null;
		unbilledInstallments =null;
		foreClosurePenalty =null;
		otherDues=null;
		otherRefunds =null;
		lpInterest =null;
		lppAmt =null;
		netRecPay =null;
		//con =null;
	}
	return duesRefundsList;
}
//end of Fore Closure

public ArrayList<PaymentDetailsVo> bounceDetails(String loanId){
	
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	ArrayList searchlist = new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
	ArrayList data = new ArrayList();
	PaymentDetailsVo Vo = null;
	
	ArrayList<PaymentDetailsVo> detailList = new ArrayList<PaymentDetailsVo>();
	try {
		logger.info("In bounceDetails.....................................Dao Impl");

//		bufInsSql.append("select DATE_FORMAT(pmnt.PMNT_DATE,'"+dateFormat+"') as PAYMENT_DATE," );
//		bufInsSql.append("pmnt.PMNT_AMOUNT,CASE INSTRUMENT_MODE WHEN 'Q' THEN 'CHEQUE' WHEN 'C' " );
//		bufInsSql.append("THEN 'CASH' WHEN 'D' THEN 'DD' WHEN 'N' THEN 'NEFT' WHEN 'R' THEN 'RTGS'" );
//		bufInsSql.append("WHEN 'S' THEN 'SUSPENSE' WHEN 'B' THEN 'DIRECT DEBIT' WHEN 'E' THEN 'ECS' ");
//		bufInsSql.append("WHEN 'O' THEN 'OTHER' END PAYMENT_MODE from cr_pmnt_dtl pmnt join cr_txnadvice_dtl txn ");
//		bufInsSql.append(" ON pmnt.TXNADVICEID=txn.TXNADVICE_ID   JOIN cr_instrument_dtl CID ON  TXNID=txn.TXNADVICE_ID WHERE txn.LOAN_ID='"+loanId+"' ");
//			  
//		bufInsSqlTempCount.append("select Count(1) from (" );
//		bufInsSqlTempCount.append("select DATE_FORMAT(pmnt.PMNT_DATE,'"+dateFormat+"') as PAYMENT_DATE," );
//		bufInsSqlTempCount.append("pmnt.PMNT_AMOUNT,CASE INSTRUMENT_MODE WHEN 'Q' THEN 'CHEQUE' WHEN 'C' " );
//		bufInsSqlTempCount.append("THEN 'CASH' WHEN 'D' THEN 'DD' WHEN 'N' THEN 'NEFT' WHEN 'R' THEN 'RTGS'" );
//		bufInsSqlTempCount.append("WHEN 'S' THEN 'SUSPENSE' WHEN 'B' THEN 'DIRECT DEBIT' WHEN 'E' THEN 'ECS' ");
//		bufInsSqlTempCount.append("WHEN 'O' THEN 'OTHER' END PAYMENT_MODE from cr_pmnt_dtl pmnt join cr_txnadvice_dtl txn ");
//		bufInsSqlTempCount.append(" ON pmnt.TXNADVICEID=txn.TXNADVICE_ID   JOIN cr_instrument_dtl CID ON  TXNID=txn.TXNADVICE_ID WHERE txn.LOAN_ID='"+loanId+"' ");
//		bufInsSqlTempCount.append(	") A ");

		bufInsSql.append("select ");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(INSTRUMENT_DATE,'"+dateFormat+"') as INSTRUMENT_DATE,INSTRUMENT_AMOUNT,CASE REC_STATUS WHEN 'B' THEN 'BOUNCE' END AS STATUS from  cr_instrument_dtl  where TXNID='"+loanId+"' "); 
		bufInsSql.append("and REC_STATUS='B'");
		bufInsSqlTempCount.append("select Count(1) from (" );
		bufInsSqlTempCount.append("select ");
		bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("DATE_FORMAT(INSTRUMENT_DATE,'"+dateFormat+"') as INSTRUMENT_DATE,INSTRUMENT_AMOUNT,CASE REC_STATUS WHEN 'B' THEN 'BOUNCE' END AS STATUS from  cr_instrument_dtl  where TXNID='"+loanId+"' "); 
		bufInsSqlTempCount.append("and REC_STATUS='B'");
		bufInsSqlTempCount.append(	") A ");
		logger.info("search Query...." + bufInsSql);
		logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
        count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
        logger.info("count:::::::::::::::::::::::::::::::::::::::::::"+count);
		logger.info("query : "+bufInsSql);
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size = searchlist.size();
		logger.info("IN bounceDetails():::::::::::::: "+ bufInsSql.toString());
		logger.info("bounceDetails " + size);
		
		for (int i = 0; i < size; i++) {
			data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				Vo = new PaymentDetailsVo();
				Vo.setPaymentDate(CommonFunction.checkNull(data.get(0)));
                Vo.setPaymentAmount(CommonFunction.checkNull(data.get(1)));
                Vo.setPaymentMode(CommonFunction.checkNull(data.get(2)));
			    Vo.setTotalRecordSize(count);
				detailList.add(Vo);
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
		data = null;
		Vo = null;
	}
	return detailList;

}
//KANIKA CODE FOR VIEW PAYABLE ANAD RECIEVABLE

public ArrayList<ViewpayableRecievableVo> viewPayableReceivable(ViewpayableRecievableVo viewPayableRecievable,String loanId){

		ArrayList<ViewpayableRecievableVo> viewPayableReceivableList=new ArrayList<ViewpayableRecievableVo>();
		
		ArrayList mainList=new ArrayList ();
		ArrayList subList =new ArrayList();
		//CallableStatement cst=null;
		//Connection con=ConnectionDAO.getConnection();
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		StringBuilder bufInsSql = new StringBuilder();
		ViewpayableRecievableVo vO = null;
		try{
			
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;		
			logger.info(" In viewPayableReceivable....");	
			
			bufInsSql.append("SELECT ");
		 	bufInsSql.append(dbo);
		 	bufInsSql.append("DATE_FORMAT(ADVICE_DATE,'"+dateFormat+"')  AS ADVICE_DATE, (SELECT CHARGE_DESC FROM com_charge_code_m WHERE CHARGE_CODE=CHARGE_CODE_ID) cHARGE ,");
			bufInsSql.append("(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)) BALANCE_AMOUNT,CASE WHEN ADVICE_TYPE='P'THEN 'PAYABLE' ELSE 'RECEIVABLE' END AS ADVICE_TYPE "); 
			bufInsSql.append("FROM cr_txnadvice_dtl WHERE LOAN_ID='"+loanId+"'  AND (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0 AND  REC_STATUS in('A','F') ORDER BY ADVICE_DATE ASC");
			bufInsSqlTempCount.append("select Count(1) from (" );
			bufInsSqlTempCount.append("SELECT ");
		 	bufInsSqlTempCount.append(dbo);
		 	bufInsSqlTempCount.append("DATE_FORMAT(ADVICE_DATE,'"+dateFormat+"') AS ADVICE_DATE, (SELECT CHARGE_DESC FROM com_charge_code_m WHERE CHARGE_CODE=CHARGE_CODE_ID) cHARGE ,");
			bufInsSqlTempCount.append("(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)) BALANCE_AMOUNT,CASE WHEN ADVICE_TYPE='P' THEN 'PAYABLE' ELSE 'RECEIVABLE' END AS ADVICE_TYPE ");
			bufInsSqlTempCount.append("FROM cr_txnadvice_dtl WHERE LOAN_ID='"+loanId+"'   AND (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0 AND  REC_STATUS in('A','F') ");
			bufInsSqlTempCount.append(	") A ");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	        count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			mainList=ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = mainList.size();
			for(int i=0;i<size;i++)
			{
				subList= (ArrayList)mainList.get(i);
				if(subList.size()>0){
					vO = new ViewpayableRecievableVo();
					vO.setViewPRDate(CommonFunction.checkNull(subList.get(0)).trim());
					vO.setCharge(CommonFunction.checkNull(subList.get(1)).trim());
					vO.setBalamt(CommonFunction.checkNull(subList.get(2)).trim());
					vO.setAdvicetype(CommonFunction.checkNull(subList.get(3)).trim());
					vO.setTotalRecordSize(count);
					viewPayableReceivableList.add(vO);
				}
			}
				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					mainList= null;
					subList = null;
					//cst=null;
					//con= null;
					bufInsSqlTempCount = null;
					bufInsSql = null;
					vO = null;
				}
				return viewPayableReceivableList;
}
	
//Anil Code Ends Here
public ArrayList<ClassificationProcessVo> getClassificationData(String businessDate){
	ArrayList<ClassificationProcessVo> classificationProcessData=new ArrayList<ClassificationProcessVo>();
	
	ArrayList searchlist = new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();
	ArrayList data = new ArrayList();
	ClassificationProcessVo VO=null;
	try{
		
		bufInsSql.append("SELECT A.PROCESS_NAME,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(B.START_TIME,'"+dateFormatWithTime+"') as START_TIME,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT (B.END_TIME,'"+dateFormatWithTime+"') as END_TIME,B.REC_STATUS FROM coll_classification_process_m A");
		bufInsSql.append(" LEFT OUTER JOIN coll_classification_process_LOG  B ON A.PROCESS_NAME=B.PROCESS_NAME AND PROCESS_DATE=");
		bufInsSql.append(dbo);
		bufInsSql.append("STR_TO_DATE('"+businessDate+"','"+dateFormatWithTime+"') AND");
		bufInsSql.append(" B.CLASS_PROCESS_ID IN  (SELECT MAX(CLASS_PROCESS_ID) FROM coll_classification_process_LOG");
		bufInsSql.append("  GROUP BY PROCESS_NAME)");
		bufInsSql.append(" where a.REC_STATUS='A'  order by A.SEQ_NO ");
		
//		bufInsSql.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.REC_STATUS FROM coll_classification_process_m A");
//		bufInsSql.append(" LEFT OUTER JOIN coll_classification_process_LOG  B ON A.PROCESS_NAME=B.PROCESS_NAME AND PROCESS_DATE='"+businessDate+"' AND");
//		bufInsSql.append(" B.CLASS_PROCESS_ID IN  (SELECT MAX(CLASS_PROCESS_ID) FROM coll_classification_process_LOG");
//		bufInsSql.append("  GROUP BY PROCESS_NAME)");
//		bufInsSql.append(" where a.REC_STATUS='A'  order by A.SEQ_NO ");
		
		//AND PROCESS_DATE=STR_TO_DATE('"+businessDate+"','"+dateFormatWithTime+"')
		logger.info("IN getClassificationData():::::::::::::: "+ bufInsSql.toString());
		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		 logger.info("IN getClassificationData():::::::::::::before size Arun: ");
		 int size = searchlist.size();
		 logger.info("IN getClassificationData():::::::::::::size: "+ size);
		logger.info("getClassificationData " + size);
		
		for (int i = 0; i < size; i++) {
			data = (ArrayList) searchlist.get(i);
			logger.info("IN getClassificationData():::::::::::::before size in if data is:--: "+data);
			if (data.size() > 0) {
				VO=new ClassificationProcessVo();	
				VO.setProcessName(CommonFunction.checkNull(data.get(0)));
				VO.setLastStartTime(CommonFunction.checkNull(data.get(1)));
				VO.setLastEndStartTime(CommonFunction.checkNull(data.get(2)));
				
				if(CommonFunction.checkNull(data.get(3)).equalsIgnoreCase("")){
					VO.setStatus("NI");	
				}else{
				VO.setStatus(CommonFunction.checkNull(data.get(3)));
				}
				//VO.setProcmessage(CommonFunction.checkNull(message));
			    classificationProcessData.add(VO);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		searchlist =null;
		bufInsSql =null;
		data =null;
		VO=null;
	}
	return classificationProcessData;
}


public ArrayList<ClassificationProcessVo> startClasificationProcess(String businessDate,String maker_id){
	ArrayList<ClassificationProcessVo> classificationProcessData=new ArrayList<ClassificationProcessVo>();
		logger.info("In.  startClasificationProcess()...DAIOIMPL.....");
		ArrayList list = new ArrayList();
		ArrayList list1 = new ArrayList();
		ArrayList queryList = new ArrayList();
		 int statusProc=0;
			ArrayList<Object> in =new ArrayList<Object>();
 			ArrayList<Object> out =new ArrayList<Object>();
 			ArrayList outMessages = new ArrayList();
 			String s1="";
 			String s2="";
		//CallableStatement cst=null;
		//Connection con=ConnectionDAO.getConnection();
		try
		{	
			//con.setAutoCommit(false);
			//,,
//			cst=con.prepareCall("call coll_classificationProcess(STR_TO_DATE(?,'"+dateFormatWithTime+"'),?,?,?)");
			
			 String date=businessDate.substring(6)+"-"+businessDate.substring(3,5)+"-"+businessDate.substring(0,2);
			 in.add(date);		
			 in.add(maker_id);		
			out.add(s1);
			out.add(s2);
				//statusProc=cst.executeUpdate();
			outMessages=(ArrayList) ConnectionDAO.callSP("coll_classificationProcess",in,out);
    	    s1=CommonFunction.checkNull(outMessages.get(0));
    	    s2=CommonFunction.checkNull(outMessages.get(1));
		
				logger.info(" coll_classificationProcess flag: "+  s1);
				logger.info(" coll_classificationProcess message: "+s2);
				
				classificationProcessData=getClassificationData(businessDate);
				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		
		list =null;
		list1 =null;
		queryList =null;
		//cst=null;
		//con=null;
	}
	

	return classificationProcessData;
}

public ArrayList<CollCommonVO> getBankInfo(String loanId){
	ArrayList<CollCommonVO> bankInfoList=new ArrayList<CollCommonVO>();
	
	ArrayList mainList=new ArrayList ();
	ArrayList subList =new ArrayList();
	logger.info(" In getBankInfo....");	
	StringBuilder bufInsSqlTempCount = new StringBuilder();	
	StringBuilder bufInsSql = new StringBuilder();
	CollCommonVO vo =null;
	
	try{
		bufInsSql.append(" Select DISTINCT PDC_ISSUEING_BANK_ID,(Select  BANK_NAME from  com_bank_m where BANK_ID= PDC_ISSUEING_BANK_ID) Bank,PDC_ISSUEING_BRANCH_ID,");
		bufInsSql.append("(Select BANK_BRANCH_NAME from com_bankbranch_m where BANK_BRANCH_ID=PDC_ISSUEING_BRANCH_ID ) Branch_Name,  "); 
		bufInsSql.append(" PDC_ISSUING_MICR_CODE,PDC_ISSUING_IFSC_CODE,PDC_ISSUEING_BANK_ACCOUNT from cr_pdc_instrument_dtl where PDC_LOAN_ID='"+loanId+"'  ");
	
		logger.info("Bank Info  Query...." + bufInsSql);
		
      int count=1;
		mainList=ConnectionDAO.sqlSelect(bufInsSql.toString());
      	int size = mainList.size();
		for(int i=0;i<size;i++)
		{
			subList= (ArrayList)mainList.get(i);
			if(subList.size()>0){
				vo = new CollCommonVO();
				//vO.setViewPRDate(CommonFunction.checkNull(subList.get(0)).trim());
				//vO.setCharge(CommonFunction.checkNull(subList.get(1)).trim());
				//vO.setBalamt(CommonFunction.checkNull(subList.get(2)).trim());
				//vO.setAdvicetype(CommonFunction.checkNull(subList.get(3)).trim());
				//vO.setTotalRecordSize(count);
				vo.setCount(count);
				vo.setBankId(CommonFunction.checkNull(subList.get(0)).trim());
				vo.setBankName(CommonFunction.checkNull(subList.get(1)).trim());
				vo.setBranchId(CommonFunction.checkNull(subList.get(2)).trim());
				vo.setBranchName(CommonFunction.checkNull(subList.get(3)).trim());
				vo.setMicr(CommonFunction.checkNull(subList.get(4)).trim());
				vo.setIfsc(CommonFunction.checkNull(subList.get(5)).trim());
				vo.setAccountNo(CommonFunction.checkNull(subList.get(6)).trim());
				bankInfoList.add(vo);
				count++;
			}
		}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				mainList=null;
				subList =null;
				bufInsSqlTempCount =null;
				bufInsSql =null;
				vo =null;
			}

			return bankInfoList;
}
//
//ArrayList<CollCommonVO> getGuarantorInfo(String loanId)
public ArrayList<CollCustomerAddressVo> getGuarantorInfo(String loanId){
	ArrayList<CollCustomerAddressVo> guarantorInfo=new ArrayList<CollCustomerAddressVo>();
	
	ArrayList mainList=new ArrayList ();
	ArrayList subList =new ArrayList();
	logger.info(" In getGuarantorInfo....");	
	StringBuilder bufInsSqlTempCount = new StringBuilder();	
	StringBuilder bufInsSql = new StringBuilder();
	CollCustomerAddressVo vo = null;
	
	try{
		bufInsSql.append("Select CDCM.CUSTOMER_NAME,CDAM.ADDRESS_LINE1,CDAM.ADDRESS_LINE2,CDAM.ADDRESS_LINE3,");
		bufInsSql.append("(Select  DISTRICT_DESC from com_district_m CDM where  CDM.DISTRICT_ID=CDAM.DISTRICT) District,"); 
		bufInsSql.append("(Select STATE_DESC from com_state_m CSM where   CSM.STATE_ID=CDAM.STATE) State,");
		bufInsSql.append(" (Select COUNTRY_DESC from com_country_m CCM where CCM.COUNTRY_ID=CDAM.COUNTRY) Country, ");
		bufInsSql.append(" CDAM.PINCODE,CDAM.PRIMARY_PHONE,CDAM.FAX from cr_loan_dtl CLD join "); 
		bufInsSql.append(" cr_deal_customer_role CDCR on CLD.LOAN_DEAL_ID=CDCR.DEAL_ID join ");
		bufInsSql.append(" gcd_customer_m CDCM on CDCR.GCD_ID=CDCM.CUSTOMER_ID join ");
		bufInsSql.append(" com_address_m CDAM on CDCM.CUSTOMER_ID=CDAM.BPID "); 
		bufInsSql.append("  where CLD.LOAN_ID='"+loanId+"'and CDCR.DEAL_CUSTOMER_ROLE_TYPE='GUARANTOR'");
	
		logger.info("getGuarantorInfo  Query...." + bufInsSql);
		
      int count=1;
      mainList=ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size = mainList.size();
		for(int i=0;i<size;i++)
		{
			subList= (ArrayList)mainList.get(i);
			if(subList.size()>0){
				vo = new CollCustomerAddressVo();
				vo.setCount(count);
				vo.setGuarantorName(CommonFunction.checkNull(subList.get(0)).trim());
				vo.setAdd_desc(CommonFunction.checkNull(subList.get(1)).trim() +" "+ CommonFunction.checkNull(subList.get(2)).trim() +" "+ CommonFunction.checkNull(subList.get(3)).trim());
				vo.setTxtDistCode(CommonFunction.checkNull(subList.get(4)).trim());
				vo.setTxtStateCode(CommonFunction.checkNull(subList.get(5)).trim());
				vo.setTxtCountryCode(CommonFunction.checkNull(subList.get(6)).trim());
				vo.setPincode(CommonFunction.checkNull(subList.get(7)).trim());
				vo.setPrimaryPhoneNo(CommonFunction.checkNull(subList.get(8)).trim());
				vo.setFaxNo(CommonFunction.checkNull(subList.get(9)).trim());
				guarantorInfo.add(vo);
				count++;
			}
		}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				mainList= null;
				subList = null;
				bufInsSqlTempCount = null;
				bufInsSql = null;
				vo = null;
			}

			return guarantorInfo;
}


public ArrayList<CollCustomerAddressVo> getSuplierInfo(String loanId){
	ArrayList<CollCustomerAddressVo> suplierInfo=new ArrayList<CollCustomerAddressVo>();
	
	ArrayList mainList=new ArrayList ();
	ArrayList subList =new ArrayList();
	logger.info(" In getSuplierInfo....");	
	StringBuilder bufInsSqlTempCount = new StringBuilder();	
	StringBuilder bufInsSql = new StringBuilder();
	CollCustomerAddressVo vo = null;
	
	try{
		
		bufInsSql.append("Select DEAL_BUYER_SUPPLIER_NAME,DEAL_BUYER_SUPPLIER_ADDRESS,");
		bufInsSql.append(" (Select  DISTRICT_DESC from com_district_m CDM where  CDM.DISTRICT_ID=DEAL_BUYER_SUPPLIER_DISTRICT) District ,"); 
		bufInsSql.append(" (Select STATE_DESC from com_state_m CSM where   CSM.STATE_ID=DEAL_BUYER_SUPPLIER_STATE) State ,");
		bufInsSql.append(" (Select COUNTRY_DESC from com_country_m CCM where CCM.COUNTRY_ID=DEAL_BUYER_SUPPLIER_COUNTRY) Country , ");
		bufInsSql.append(" DEAL_BUYER_SUPPLIER_PINCODE,DEAL_BUYER_SUPPLIER_MOBILE,DEAL_BUYER_SUPPLIER_EMAIL "); 
		bufInsSql.append(" from cr_loan_dtl CLD join cr_deal_buyer_supplier_dtl CDBSD on  ");
		bufInsSql.append(" CLD.LOAN_DEAL_ID=CDBSD.DEAL_ID where CLD.LOAN_ID='"+loanId+"' and DEAL_BUYER_SUPPLIER_CONTACT='B' ");

	
		logger.info("getSuplierInfo Query...." + bufInsSql);
		
      int count=1;
		mainList=ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size = mainList.size();
		for(int i=0;i<size;i++)
		{
			subList= (ArrayList)mainList.get(i);
			if(subList.size()>0){
				vo = new CollCustomerAddressVo();
				vo.setCount(count);
				vo.setGuarantorName(CommonFunction.checkNull(subList.get(0)).trim());
				vo.setAdd_desc(CommonFunction.checkNull(subList.get(1)).trim());
				vo.setTxtDistCode(CommonFunction.checkNull(subList.get(2)).trim());
				vo.setTxtStateCode(CommonFunction.checkNull(subList.get(3)).trim());
				vo.setTxtCountryCode(CommonFunction.checkNull(subList.get(4)).trim());
				vo.setPincode(CommonFunction.checkNull(subList.get(5)).trim());
				vo.setMobile(CommonFunction.checkNull(subList.get(6)).trim());
				vo.setEmail(CommonFunction.checkNull(subList.get(7)).trim());
				suplierInfo.add(vo);
				count++;
			}
		}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				mainList= null;
				subList = null;
				bufInsSqlTempCount = null;
				bufInsSql = null;
				vo = null;
			}

			return suplierInfo;
}
public ArrayList<ContactRecordingFollowUpVO> assetInfoList(ContactRecordingFollowUpVO vo){
	ArrayList<ContactRecordingFollowUpVO> assetInfoList= new ArrayList<ContactRecordingFollowUpVO>();
	
	 ArrayList mainList=new ArrayList();
		ArrayList subList=new ArrayList();
		StringBuilder query = null;
		ContactRecordingFollowUpVO ContactRecordingVO=null;
	try{
		query = new StringBuilder();
    query.append("Select A.ASSET_TYPE,A.ASSET_COLLATERAL_DESC,A.ASSET_COLLATERAL_CLASS,A.ASSET_COLLATERAL_VALUE");
	query.append(" from cr_asset_collateral_m A,cr_loan_collateral_m B where B.ASSETID=A.ASSET_ID AND B.REC_STATUS='A'");
	query.append(" AND B.LOAN_ID='"+vo.getLoanId()+"'");
    		
		  logger.info("In assetInfoList......Dao Impl"+query);
		  mainList = ConnectionDAO.sqlSelect(query.toString());
		  int size = mainList.size();
		  for(int i=0;i<size;i++){
			  subList=(ArrayList) mainList.get(i);
			  if(subList.size()>0){
				  ContactRecordingVO=new ContactRecordingFollowUpVO();
				  ContactRecordingVO.setAssetType(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0)).trim()));
				  ContactRecordingVO.setAssetDesc(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1)).trim()));
				  ContactRecordingVO.setAssetClass(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(2)).trim()));
				  ContactRecordingVO.setAssetValue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(3)).trim()));
				  assetInfoList.add(ContactRecordingVO);
			}
		  }
			  logger.info("In assetInfoList......Dao Impl"+assetInfoList.size());
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		mainList=null;
		subList=null;
		query =null;
		ContactRecordingVO=null;
	}
	return assetInfoList;
}

public void esclatedCaseSendMailToUserAuto(){
	ArrayList<ContactRecordingFollowUpVO> assetInfoList= new ArrayList<ContactRecordingFollowUpVO>();
	
	 ArrayList mainList=new ArrayList();
		ArrayList subList=new ArrayList();
		StringBuilder query = null;
		
	try{
		query = new StringBuilder();
		
		String queryBusinessDate="Select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BUSINESS_DATE'";
		String querySystemEmail="Select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EMAIL_USERNAME'";
		String querySystemPass="Select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EMAIL_PASSWORD'";
		String querySystemSmtpHost="Select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='SMTP_HOST'";
		String querySystemSmtpPort="Select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='SMTP_PORT'";
	
	
	String userEmail=null;
	String loanNo=null;
	String systemEmail=ConnectionDAO.singleReturn(querySystemEmail);
	String systemPass=ConnectionDAO.singleReturn(querySystemPass);
	String smtpHost=ConnectionDAO.singleReturn(querySystemSmtpHost);
	String smtpPort=ConnectionDAO.singleReturn(querySystemSmtpPort);
	String businessDate=ConnectionDAO.singleReturn(queryBusinessDate);
	
	logger.info("esclatedCaseSendMailToUserAuto smtpPort:-------"+smtpPort);
	logger.info("esclatedCaseSendMailToUserAuto smtpHost:-------"+smtpHost);
	logger.info("esclatedCaseSendMailToUserAuto systemPass:-------"+systemPass);
	logger.info("esclatedCaseSendMailToUserAuto systemEmail:-------"+systemEmail);
	logger.info("esclatedCaseSendMailToUserAuto businessDate:-------"+businessDate);
	
   query.append("Select d.USER_EMAIL,c.LOAN_NO,c.LOAN_ID,c.user_reporting_to from sec_user_m d,");
   query.append(" (Select a.LOAN_ID,A.LOAN_NO,a.USER_ID,b.user_reporting_to from sec_user_m b,");
   query.append(" (Select LOAN_ID,LOAN_NO,USER_ID from coll_case_dtl where ESCALATION_FLAG='Y' ");
   query.append(" and ESCLATION_DATE = '"+businessDate+"') a ");
   query.append("  where a.USER_ID=b.USER_ID) c  where   c.user_reporting_to=d.user_id ");

   		
		  logger.info("In esclatedCaseSendMailToUserAuto......Dao Impl"+query);
		  mainList = ConnectionDAO.sqlSelect(query.toString());
		  int size = mainList.size();
		  for(int i=0;i<size;i++){
			  subList=(ArrayList) mainList.get(i);
			  if(subList.size()>0){
			userEmail=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0)).trim());
			loanNo=StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1)).trim());
			//Mail.sendMail(systemEmail,systemPass,smtpHost,smtpPort, "true",userEmail," Escalated for action","Dear Sir/Madam,\n \nLoan No:- has been escalated in your tray.\nPlease take some appropriate action.\n \nThanks");
			Mail.sendMail(systemEmail,systemPass,smtpHost,smtpPort, "true",userEmail,loanNo+" escalated for action","Dear Sir/Madam,\n \nLoan No:- "+loanNo+" has been escalated in your tray.\nPlease take some appropriate action.\n \nThanks");	
			}
		  }
		
      	querySystemPass=null;
      	querySystemSmtpHost=null;
      	querySystemSmtpPort=null;
      	userEmail=null;
      	systemEmail=null;
      	systemPass=null;
      	smtpHost=null;
      	smtpPort=null;
      	
			  logger.info("In assetInfoList......Dao Impl"+assetInfoList.size());
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		mainList=null;
		subList=null;
		query =null;
				
	}
}
public ArrayList<CollCustomerAddressVo> defaultcountry() {
	ArrayList list = new ArrayList();
	try {
		logger.info("In defaultcountry()..........................DAOImpl");
		StringBuilder query=new StringBuilder();
		
		 query.append("SELECT PARAMETER_VALUE,PARAMETER_DESC FROM parameter_mst WHERE PARAMETER_KEY='DEFAULT_COUNTRY'");
		logger.info("In defaultcountry...............query...........DAOImpl"+ query);
		CollCustomerAddressVo vo = null;
		ArrayList country = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		
		logger.info("defaultcountry() " + country.size());
		for (int i = 0; i < country.size(); i++) {
			logger.info("defaultcountry()...Outer FOR loop "+ CommonFunction.checkNull(country.get(i)).toString());
			ArrayList data = (ArrayList) country.get(i);
			if (data.size() > 0) {
				vo = new CollCustomerAddressVo();
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
	public ArrayList<CustomerSaveVo> getRefrencesInfo(String loanId){
		ArrayList<CustomerSaveVo> list=new ArrayList<CustomerSaveVo>();
		StringBuilder query=new StringBuilder();
		 try
		 {
			 logger.info(" in getRefrencesInfo  : : : : : : :  " );
			 query.append("Select  concat(F_NAME,' ',");
		 	 query.append("ISNULL(M_NAME,''),' ',L_NAME)NAME,RELATIONSHIP,KNOWING_SINCE,MOBILE_NUMBER,LANDLINE_NUMBER ");
			 query.append(" from cr_loan_dtl CLD join  com_reference_m CFM on CLD.LOAN_CUSTOMER_ID=CFM.BPID ");
			 query.append(" WHERE LOAN_ID='"+loanId+"' and CFM.REC_STATUS='A'");
			    logger.info("IN getRefrencesInfo query : : : : : : :  "+query);
				ArrayList addressDetails = ConnectionDAO.sqlSelect(query.toString());
				logger.info("iN getRefrencesInfo "+addressDetails.size());
				for(int i=0;i<addressDetails.size();i++){
					//logger.info("getRefrencesInfo...FOR loop "+CommonFunction.checkNull(addressDetails.get(i)).toString());
					ArrayList data=(ArrayList)addressDetails.get(i);
					if(data.size()>0)
					{
						CustomerSaveVo custVO=new CustomerSaveVo();
						
						custVO.setRefName((CommonFunction.checkNull(data.get(0)).toString()));
						custVO.setRelationshipS((CommonFunction.checkNull(data.get(1)).toString()));
						custVO.setKnowingSince((CommonFunction.checkNull(data.get(2)).toString()));
						custVO.setPrimaryRefMbNo((CommonFunction.checkNull(data.get(3)).toString()));
						custVO.setAlternateRefPhNo((CommonFunction.checkNull(data.get(4)).toString()));
						list.add(custVO);
					}
				}
			 }catch(Exception e)
			{e.printStackTrace();}	
			 return list;
	}
	public ArrayList<CollCustomerAddressVo> getCoApplicantInfo(String loanId){
		ArrayList<CollCustomerAddressVo> coApplicantInfo=new ArrayList<CollCustomerAddressVo>();
		
		ArrayList mainList=new ArrayList ();
		ArrayList subList =new ArrayList();
		logger.info(" In getCoApplicantInfo....");	
		StringBuilder bufInsSqlTempCount = new StringBuilder();	
		StringBuilder bufInsSql = new StringBuilder();
		CollCustomerAddressVo vo = null;
		
		try{
			bufInsSql.append("Select CDCM.CUSTOMER_NAME,CDAM.ADDRESS_LINE1,CDAM.ADDRESS_LINE2,CDAM.ADDRESS_LINE3,");
			bufInsSql.append("(Select  DISTRICT_DESC from com_district_m CDM where  CDM.DISTRICT_ID=CDAM.DISTRICT) District,"); 
			bufInsSql.append("(Select STATE_DESC from com_state_m CSM where   CSM.STATE_ID=CDAM.STATE) State,");
			bufInsSql.append(" (Select COUNTRY_DESC from com_country_m CCM where CCM.COUNTRY_ID=CDAM.COUNTRY) Country, ");
			bufInsSql.append(" CDAM.PINCODE,CDAM.PRIMARY_PHONE,CDAM.FAX from cr_loan_dtl CLD join "); 
			bufInsSql.append(" cr_deal_customer_role CDCR on CLD.LOAN_DEAL_ID=CDCR.DEAL_ID join ");
			bufInsSql.append(" gcd_customer_m CDCM on CDCR.GCD_ID=CDCM.CUSTOMER_ID join ");
			bufInsSql.append(" com_address_m CDAM on CDCM.CUSTOMER_ID=CDAM.BPID "); 
			bufInsSql.append("  where CLD.LOAN_ID='"+loanId+"'and CDCR.DEAL_CUSTOMER_ROLE_TYPE='COAPPL'");
		
			logger.info("getCoApplicantInfo  Query...." + bufInsSql);
			
	      int count=1;
	      mainList=ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = mainList.size();
			for(int i=0;i<size;i++)
			{
				subList= (ArrayList)mainList.get(i);
				if(subList.size()>0){
					vo = new CollCustomerAddressVo();
					vo.setCount(count);
					vo.setGuarantorName(CommonFunction.checkNull(subList.get(0)).trim());
					vo.setAdd_desc(CommonFunction.checkNull(subList.get(1)).trim() +" "+ CommonFunction.checkNull(subList.get(2)).trim() +" "+ CommonFunction.checkNull(subList.get(3)).trim());
					vo.setTxtDistCode(CommonFunction.checkNull(subList.get(4)).trim());
					vo.setTxtStateCode(CommonFunction.checkNull(subList.get(5)).trim());
					vo.setTxtCountryCode(CommonFunction.checkNull(subList.get(6)).trim());
					vo.setPincode(CommonFunction.checkNull(subList.get(7)).trim());
					vo.setPrimaryPhoneNo(CommonFunction.checkNull(subList.get(8)).trim());
					vo.setFaxNo(CommonFunction.checkNull(subList.get(9)).trim());
					coApplicantInfo.add(vo);
					count++;
				}
			}
				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					mainList= null;
					subList = null;
					bufInsSqlTempCount = null;
					bufInsSql = null;
					vo = null;
				}

				return coApplicantInfo;
	}
	/*Code By Arun on 25/06/2012 Starts Here*/
	public CollectionSummaryVO getCollectionSummary(CollectionSummaryVO collectionSummaryVO){
		
		StringBuilder qryTotalAllocated=new StringBuilder();
		StringBuilder qryfollowUpToday=new StringBuilder();
		StringBuilder qryEscalatedCase=new StringBuilder();
		StringBuilder qryEscalatedReceive=new StringBuilder();
		StringBuilder qrytotalPtpToday=new StringBuilder();
		CollectionSummaryVO collectionSummary =new CollectionSummaryVO();
		try{
			qryTotalAllocated.append("select count(1) from coll_case_dtl where user_id='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"' AND DELINQUENCY_FLAG='Y'");
			
			qryfollowUpToday.append("select count(1) from coll_case_dtl a  join coll_follow_up_trails b  on a.LOAN_ID=b.LOAN_ID " );
			qryfollowUpToday.append(" where a.user_id='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"'  AND DELINQUENCY_FLAG='Y' AND " );
			qryfollowUpToday.append(dbo);
			qryfollowUpToday.append(" DATE_FORMAT(b.NEXT_ACTION_DATE,'"+dateFormat+"')='"+collectionSummaryVO.getBusinessDate()+"' ");
		 	//qryfollowUpToday.append(dbo);
		 	//qryfollowUpToday.append("str_to_date('"+collectionSummaryVO.getBusinessDate()+"','"+dateFormat+"');");
			
			qryEscalatedCase.append("select count(1) from coll_case_dtl a  join coll_follow_up_trails b  on a.LOAN_ID=b.LOAN_ID " );
			qryEscalatedCase.append(" where user_id='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"' AND DELINQUENCY_FLAG='Y'AND b.action_code='EC' AND ");
			qryEscalatedCase.append(dbo);
			qryEscalatedCase.append(" DATE_FORMAT(a.ESCLATION_DATE,'"+dateFormat+"')='"+collectionSummaryVO.getBusinessDate()+"' ");
			//qryEscalatedCase.append(dbo);
		 	//qryEscalatedCase.append("str_to_date('"+collectionSummaryVO.getBusinessDate()+"','"+dateFormat+"');");
			
			qryEscalatedReceive.append("select count(1) from coll_case_dtl a where user_id in (select USER_ID from sec_user_m WHERE USER_REPORTING_TO ='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"' ) " );
			qryEscalatedReceive.append(" and ESCALATION_FLAG='Y'AND DELINQUENCY_FLAG='Y' AND ");
			qryEscalatedReceive.append(dbo);
			qryEscalatedReceive.append(" DATE_FORMAT(ESCLATION_DATE,'"+dateFormat+"')='"+collectionSummaryVO.getBusinessDate()+"' ");
			
			//qryEscalatedReceive.append(dbo);
		 	//qryEscalatedReceive.append("str_to_date('"+collectionSummaryVO.getBusinessDate()+"','"+dateFormat+"');");
			
			qrytotalPtpToday.append("select count(1) from coll_case_dtl a  join coll_follow_up_trails b  on a.LOAN_ID=b.LOAN_ID " );
			qrytotalPtpToday.append(" where user_id='"+CommonFunction.checkNull(collectionSummaryVO.getUserId())+"' AND DELINQUENCY_FLAG='Y' AND b.action_code='PTP' and ");
			qrytotalPtpToday.append(dbo);
			qrytotalPtpToday.append(" DATE_FORMAT(b.ACTION_DATE,'"+dateFormat+"')='"+collectionSummaryVO.getBusinessDate()+"' ");
			
			//qrytotalPtpToday.append(dbo);
		 	//qrytotalPtpToday.append("str_to_date('"+collectionSummaryVO.getBusinessDate()+"','"+dateFormat+"');");
		
			String totalAllocated=ConnectionDAO.singleReturn(qryTotalAllocated.toString());
			String followUpToday=ConnectionDAO.singleReturn(qryfollowUpToday.toString());
			String escalatedCase=ConnectionDAO.singleReturn(qryEscalatedCase.toString());
			String escalatedReceive=ConnectionDAO.singleReturn(qryEscalatedReceive.toString());
			
			String totalPtpToday=ConnectionDAO.singleReturn(qrytotalPtpToday.toString());
			
			collectionSummary.setTotalAllocatedCase(totalAllocated);
			collectionSummary.setFollowupDueToday(followUpToday);
			collectionSummary.setEscalatedCases(escalatedCase);
			collectionSummary.setEscalatedRecive(escalatedReceive);
			collectionSummary.setTotalPTPDoneToday(totalPtpToday);
		}catch(Exception e){
			e.printStackTrace();
		}
		return collectionSummary;
	}
	/*Code By Arun on 25/06/2012 Ends Here*/
	/*KANIKA CODE FRO VEHICLE*/
	public ArrayList<ContactRecordingFollowUpVO> vehicleInfoList(ContactRecordingFollowUpVO vo){
		ArrayList<ContactRecordingFollowUpVO> vehicleInfoList= new ArrayList<ContactRecordingFollowUpVO>();
		
		 ArrayList mainList=new ArrayList();
			ArrayList subList=new ArrayList();
			StringBuilder query = null;
			ContactRecordingFollowUpVO ContactRecordingVO=null;
		try{
			query = new StringBuilder();
	    query.append("select ASSET_COLLATERAL_DESC,VEHICLE_REGISTRATION_NO,VEHICLE_CHASIS_NUMBER,VEHICLE_MAKE,VEHICLE_MODEL,VEHICLE_REGISTRATION_DATE,VEHICLE_ENGINE_NO" +
	    		" FROM cr_asset_collateral_m where ASSET_COLLATERAL_CLASS='VEHICLE' AND ASSET_ID IN(SELECT ASSETID FROM cr_loan_collateral_m  " +
	    		"WHERE LOAN_ID='"+vo.getLoanId()+"' )");
		
	    		
			  logger.info("In vehicleInfoList......Dao Impl"+query);
			  mainList = ConnectionDAO.sqlSelect(query.toString());
			  int size = mainList.size();
			  for(int i=0;i<size;i++){
				  subList=(ArrayList) mainList.get(i);
				  if(subList.size()>0){
					  ContactRecordingVO=new ContactRecordingFollowUpVO();
					 
					  ContactRecordingVO.setAssetDesc(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(0)).trim()));
					  ContactRecordingVO.setVehicleRegisNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(1)).trim()));
					  ContactRecordingVO.setVehicleChasisNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(2)).trim()));
					  ContactRecordingVO.setVehicleMake(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(3)).trim()));
					  ContactRecordingVO.setVehicleModel(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(4)).trim()));
					  ContactRecordingVO.setVehicleRegistrationDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(5)).trim()));
					  ContactRecordingVO.setVehicleEngineNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subList.get(6)).trim()));
					  vehicleInfoList.add(ContactRecordingVO);
				}
			  }
				  logger.info("In assetInfoList......Dao Impl"+vehicleInfoList.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			mainList=null;
			subList=null;
			query =null;
			ContactRecordingVO=null;
		}
		return vehicleInfoList;
	}
	
public String updateGetAutoRefreshFlag(String refreshFlag) {
		
		
		String status="N";

		ArrayList qryList = new ArrayList();
		try
		{
			String updateQuery="UPDATE PARAMETER_MST SET PARAMETER_VALUE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(refreshFlag))+"' WHERE PARAMETER_KEY = 'COLL_BOD_REFRESH_FLAG' ";
			logger.info("updateQuery: "+updateQuery);
			qryList.add(updateQuery);
			ConnectionDAO.sqlInsUpdDelete(qryList);
			String selectQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'COLL_BOD_REFRESH_FLAG'";
			logger.info("selectQuery: "+selectQuery);
			status=ConnectionDAO.singleReturn(selectQuery);
			qryList.clear();
			qryList=null;
			updateQuery=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}


/*END OF KANIKA CODE*/

/* Nazia workspace for adhoc Contact Recording */

public ArrayList<ContactRecordingSearchVO> adhocContactRecordingSearchList(ContactRecordingSearchVO vo){
	
	String loanNo="";
	String customerName="";
	String product="";
	String queue="";
	String actionDue="";
	String bDate="";
	String pos1="";
	String pos2="";
	String dpd1="";
	String dpd2="";
	String user="";
	String tos1="";
	String tos2="";
	String npastage="";
	String customertype="";
	String customercategory="";
	String assigndatefrom="";
	String assigndateto="";
	String balanceprincipal="";
	StringBuilder bufInsSql =null;
	StringBuilder bufInsSqlTempCount=null;
	StringBuilder lppChargeQuery= new StringBuilder();
	StringBuilder cbcquery= new StringBuilder();
	StringBuilder bccQuery=new StringBuilder();
	String LPP="";
	String CBC="";
	String otherCharge="";
	ArrayList data = new ArrayList();
	int startRecordIndex=0;
	int endRecordIndex = no;
	int count=0;
	ArrayList searchlist = new ArrayList();
	ContactRecordingSearchVO conteactRecVO = null;
	
	ArrayList<ContactRecordingSearchVO> detailListGrid= new ArrayList<ContactRecordingSearchVO>();
	

	try {
		logger.info("In adhocContactRecordingSearchList().....................................Dao Impl");

		 bufInsSql = new StringBuilder();
		 bufInsSqlTempCount = new StringBuilder();
		
		 if(!CommonFunction.checkNull(vo.getDpd1()).equalsIgnoreCase(""))
	   	    {
	      dpd1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd1()).trim())).toString();
	      logger.info("In adhocContactRecordingSearchList......dpd1  "+dpd1);         
	   	    }
	      if(!CommonFunction.checkNull(vo.getDpd2()).equalsIgnoreCase(""))
	 	    {
	    	  dpd2= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDpd2()).trim())).toString();
	       logger.info("In adhocContactRecordingSearchList......dpd2  "+dpd2);         
	 	    }
	      if(!CommonFunction.checkNull(vo.getPos1()).equalsIgnoreCase(""))
	 	    {
	    	  pos1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos1()).trim())).toString();
	         logger.info("In adhocContactRecordingSearchList......pos1  "+pos1);         
	 	    }
	      if(!CommonFunction.checkNull(vo.getPos2()).equalsIgnoreCase(""))
	 	    {
	    	  pos2= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPos2()).trim())).toString();
	        logger.info("In adhocContactRecordingSearchList......pos2  "+pos2);         
	 	    }
	      if(!CommonFunction.checkNull(vo.getBalanceprincipal()).equalsIgnoreCase(""))
	 	    {
	    	  balanceprincipal= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBalanceprincipal()).trim())).toString();
	       logger.info("In adhocContactRecordingSearchList......balanceprincipal  "+balanceprincipal);         
	 	    }else{
	 	      balanceprincipal=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBalanceprincipal()).trim());
	 	    }
	     
	      loanNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanno()).trim());
	      customerName=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim());
	      product=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getProduct()).trim());
	    
	      user=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxUserSearchId()).trim());
	      npastage=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getNpastage()).trim());
	      customertype=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustype()).trim());
	      customercategory=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustcategory()).trim());
	      logger.info("vo.getCustcategory()"+vo.getCustcategory());         
	     
	      queue=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxQueuesearchId()).trim());
	      assigndateto=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAssignto()).trim());
	      assigndatefrom=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAssignfrom()).trim());
	      actionDue=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getActionDue()).trim());
	      bDate=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessdate()).trim());
		 
		//Select CLD.LOAN_NO,CLD.LOAN_ID,cdcm.CUSTOMER_NAME,CLD.LOAN_OVERDUE_PRINCIPAL,CCD.QUEUE_CODE,CCD.USER_ID,ESCALATION_FLAG From cr_loan_dtl CLD left join coll_case_dtl CCD ON CLD.LOAN_NO=CCD.LOAN_NO join cr_deal_customer_m cdcm on cdcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' AND CCD.USER_ID='ANIL' or ESCALATION_FLAG='Y'
	  	lppChargeQuery.append("Select  (");
	  	lppChargeQuery.append(" CAST(ISNULL(ADVICE_AMOUNT,0)-");
		lppChargeQuery.append(" ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		lppChargeQuery.append(" ISNULL(AMOUNT_IN_PROCESS,0) as varchar(50))) LPP");
	  	lppChargeQuery.append(" FROM cr_txnadvice_dtl WHERE cast(LOAN_ID as varchar(50))='"+vo.getLoanId()+"' AND ADVICE_TYPE='R'AND REC_STATUS='A'AND CHARGE_CODE_ID='109'");
		cbcquery.append(" Select  (");
		cbcquery.append(" cast(ISNULL(ADVICE_AMOUNT,0)-");
		cbcquery.append(" ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		cbcquery.append(" ISNULL(AMOUNT_IN_PROCESS,0)as varchar(50))) CBC");
		cbcquery.append(" FROM cr_txnadvice_dtl WHERE cast(LOAN_ID as varchar(50))='"+vo.getLoanId()+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' AND CHARGE_CODE_ID='110'");
		bccQuery.append("Select  (");
		bccQuery.append(" cast(ISNULL(ADVICE_AMOUNT,0)-");
		bccQuery.append(" ISNULL(TXN_ADJUSTED_AMOUNT,0)-");
		bccQuery.append(" ISNULL(AMOUNT_IN_PROCESS,0)as varchar(50))) OTHER_CHARGE");
		bccQuery.append(" FROM cr_txnadvice_dtl WHERE cast(LOAN_ID as varchar(50))='"+vo.getLoanId()+"' AND ADVICE_TYPE='R' AND REC_STATUS='A' ");
		bccQuery.append(" AND CHARGE_CODE_ID NOT IN ('109','110','7')");
		LPP=CommonFunction.checkNull(ConnectionDAO.singleReturn(lppChargeQuery.toString()));
		CBC=CommonFunction.checkNull(ConnectionDAO.singleReturn(cbcquery.toString()));
		otherCharge=CommonFunction.checkNull(ConnectionDAO.singleReturn(bccQuery.toString()));
		if(LPP.equalsIgnoreCase("")){
		LPP="0";	
		}
		if(CBC.equalsIgnoreCase("")){
		CBC="0";		
		}
		if(otherCharge.equalsIgnoreCase("")){
		otherCharge="0";
		}
	     String sumCharges=CommonFunction.checkNull(Double.parseDouble(CBC)+Double.parseDouble(LPP)+Double.parseDouble(otherCharge));
	     logger.info("LPP:---"+LPP)  ;
	     logger.info("CBC:---"+CBC)  ;
	     logger.info("otherCharge:---"+otherCharge)  ;
	     logger.info("sumCharges:---"+sumCharges)  ;

		 bufInsSql.append(" Select cast(CLD.LOAN_NO as varchar(50)),CLD.LOAN_ID,gcm.CUSTOMER_NAME,cast(CLD.LOAN_OVERDUE_PRINCIPAL as varchar(50)),");
		 bufInsSql.append(" cast(ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)as varchar(20))+ cast(ISNULL('"+sumCharges+"',0) as varchar(50)) as activePayment ,");
		 bufInsSql.append(" CCD.QUEUE_CODE,CCD.USER_ID,ESCALATION_FLAG From cr_loan_dtl CLD inner join coll_case_dtl CCD");
		 bufInsSql.append(" ON CLD.LOAN_NO=CCD.LOAN_NO inner join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
		 bufInsSql.append(" where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' ");
		 bufInsSql.append(" AND CCD.LOAN_BRANCH IN (SELECT BRANCH_ID FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"+vo.getMakerId()+"')");
		 
		 bufInsSqlTempCount.append(" Select count(1) from (Select cast(CLD.LOAN_NO as varchar(50)),CLD.LOAN_ID,gcm.CUSTOMER_NAME,cast(CLD.LOAN_OVERDUE_PRINCIPAL as varchar(50)),");
		 bufInsSqlTempCount.append(" cast(ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0)as varchar(20))+ cast(ISNULL('"+sumCharges+"',0) as varchar(50)) as activePayment ,");
		 bufInsSqlTempCount.append(" CCD.QUEUE_CODE,CCD.USER_ID,ESCALATION_FLAG From cr_loan_dtl CLD inner join coll_case_dtl CCD");
		 bufInsSqlTempCount.append(" ON CLD.LOAN_NO=CCD.LOAN_NO inner join gcd_customer_m gcm on gcm.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID");
		 bufInsSqlTempCount.append(" where CLD.REC_STATUS='A' AND DELINQUENCY_FLAG = 'Y' ");
		 bufInsSqlTempCount.append(" AND CCD.LOAN_BRANCH IN (SELECT BRANCH_ID FROM SEC_USER_BRANCH_DTL WHERE USER_ID='"+vo.getMakerId()+"')");
		 
		 		
		if((!(loanNo.trim().equalsIgnoreCase("")))) {
	         bufInsSql.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
	         bufInsSqlTempCount.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
	    	 }
	 	if((!(assigndatefrom.trim().equalsIgnoreCase("")))) {
	 	  bufInsSql.append(" AND ");
		  bufInsSql.append(dbo);
		  bufInsSql.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+assigndatefrom+"' ");
	 	  bufInsSqlTempCount.append(" AND ");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') >= '"+assigndatefrom+"' ");
	      }
	 	if((!(assigndateto.trim().equalsIgnoreCase("")))) {
	   	   bufInsSql.append(" AND ");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+assigndateto+"' ");
	     	bufInsSqlTempCount.append(" AND ");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("DATE_FORMAT(CCD.ALLOCATION_DATE,'"+dateFormat+"') <= '"+assigndateto+"' ");
	     }
	    if((!(customerName.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND gcm.CUSTOMER_NAME like '%"+customerName+"%'  ");
	    	  bufInsSqlTempCount.append(" AND gcm.CUSTOMER_NAME like '%"+customerName+"%'  ");
	      }
		if((!(product.trim().equalsIgnoreCase("")))) {
    	  bufInsSql.append(" AND CLD.LOAN_PRODUCT='"+product+"' ");
    	  bufInsSqlTempCount.append(" AND CLD.LOAN_PRODUCT='"+product+"' ");
          }
		if((!(dpd2.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_DPD >= '"+dpd2+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_DPD >= '"+dpd2+"' ");
	      }
		if((!(dpd1.trim().equalsIgnoreCase("")))) {
    	  bufInsSql.append(" AND CLD.LOAN_DPD <= '"+dpd1+"' ");
    	  bufInsSqlTempCount.append(" AND CLD.LOAN_DPD <= '"+dpd1+"' ");
          }
			
		if((!(user.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CCD.USER_ID ='"+user+"' ");
	    	  bufInsSqlTempCount.append(" AND CCD.USER_ID ='"+user+"' ");
	      }
		if((!(pos2.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '"+pos2+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_OVERDUE_PRINCIPAL  >= '"+pos2+"' ");
	      }
		if((!(pos1.trim().equalsIgnoreCase("")))) {
    	  bufInsSql.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+pos1+"' ");
    	  bufInsSqlTempCount.append(" AND  CLD.LOAN_OVERDUE_PRINCIPAL  <= '"+pos1+"' ");
          }
		/*if((!(tos2.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0) >= '"+tos2+"' ");
	    	  bufInsSqlTempCount.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0)   >= '"+tos2+"' ");
	      }
		if((!(tos1.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0) <= '"+tos1+"' ");
	    	  bufInsSqlTempCount.append(" AND ISNULL(ISNULL(CTD.AMOUNT_IN_PROCESS,0) + ISNULL(CTD.TXN_ADJUSTED_AMOUNT,0) + ISNULL(CLD.LOAN_OVERDUE_AMOUNT,0) ,0) <= '"+tos1+"' ");
	      }
		if((!(npastage.trim().equalsIgnoreCase("")))) {
    	  bufInsSql.append(" AND CLD.NPA_FLAG  ='"+npastage+"' ");
    	  bufInsSqlTempCount.append(" AND CLD.NPA_FLAG  ='"+npastage+"' ");
          }*/
		if((!(customertype.trim().equalsIgnoreCase("")))) {
    	  bufInsSql.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
    	  bufInsSqlTempCount.append(" AND CLD.LOAN_CUSTOMER_TYPE  ='"+customertype+"' ");
         }
		if((!(customercategory.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND gcm.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
	    	  bufInsSqlTempCount.append(" AND gcm.CUSTOMER_CATEGORY  ='"+customercategory+"' ");
	      }
		if((!(balanceprincipal.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_BALANCE_PRINCIPAL  ='"+balanceprincipal+"' ");
	       }
		if((!(queue.trim().equalsIgnoreCase("")))) {
	    	  bufInsSql.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
	    	  bufInsSqlTempCount.append(" AND CCD.QUEUE_CODE  ='"+queue+"' ");
	      }
		logger.info("actionDue::::::::"+actionDue);
		if(((actionDue.trim().equalsIgnoreCase("DT")))) {
	    	  bufInsSql.append(" AND CCD.NEXT_ACTION_DATE = ");
	    	  bufInsSql.append(dbo);
	    	  bufInsSql.append("STR_TO_DATE('"+bDate+"','"+dateFormat+"') ");
	    	  bufInsSqlTempCount.append(" AND CCD.NEXT_ACTION_DATE  = ");
	    	  bufInsSqlTempCount.append(dbo);
	    	  bufInsSqlTempCount.append("STR_TO_DATE('"+bDate+"','"+dateFormat+"') ");
	      }
		if(((actionDue.trim().equalsIgnoreCase("DL")))) {
	    	  bufInsSql.append(" AND CCD.NEXT_ACTION_DATE > ");
	    	  bufInsSql.append(dbo);
	    	  bufInsSql.append("STR_TO_DATE('"+bDate+"','"+dateFormat+"') ");
	    	  bufInsSqlTempCount.append(" AND CCD.NEXT_ACTION_DATE  > ");
	    	  bufInsSqlTempCount.append(dbo);
	    	  bufInsSqlTempCount.append("STR_TO_DATE('"+bDate+"','"+dateFormat+"') ");
	      }
		if(((actionDue.trim().equalsIgnoreCase("NA")))) {
	    	  bufInsSql.append(" AND (CCD.LAST_ACTION_CODE  ='' or CCD.LAST_ACTION_CODE is null) ");
	    	  bufInsSqlTempCount.append(" AND (CCD.LAST_ACTION_CODE  ='' or CCD.LAST_ACTION_CODE is null) ");
	      }
		
					//bufInsSqlTempCount.append(" ) as t ");
		    String countCheck=ConnectionDAO.singleReturn(bufInsSqlTempCount.toString());
		
		    if(countCheck!=null && countCheck.length()>0)
			count = Integer.parseInt(countCheck);

			logger.info("search Query adhocContactRecordingSearchList...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
		  
			/*if(( loanNo.trim()=="" && customerName.trim()==""&& 
					product.trim()=="" && queue.trim()==""&& 
					 user.trim()=="" && npastage.trim()==""&& 
					 customertype.trim()=="" && customercategory.trim()==""&& 
					 balanceprincipal.trim()=="" &&  assigndateto.trim()==""&& 
					 loanNo.trim()=="" && customerName.trim()==""&& 
					 assigndatefrom.trim()=="" ) 
					 || (loanNo.trim().equalsIgnoreCase("") && customerName.trim().equalsIgnoreCase("")||
							 product.trim().equalsIgnoreCase("") && queue.trim().equalsIgnoreCase("")||
							 user.trim().equalsIgnoreCase("") && npastage.trim().equalsIgnoreCase("")||
							 customertype.trim().equalsIgnoreCase("") && customercategory.trim().equalsIgnoreCase("")||
							 balanceprincipal.trim().equalsIgnoreCase("") &&  assigndateto.trim().equalsIgnoreCase("")||
							 assigndatefrom.trim().equalsIgnoreCase("") ) 
					 || vo.getCurrentPageLink()>1)
			{*/
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			
				if(vo.getCurrentPageLink()>1)
				{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
				}
			
				
				bufInsSql.append(" ORDER BY CLD.LOAN_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				logger.info("Search contactRecordingSearchList query for SQL SERVER : " + bufInsSql.toString());

				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
				
			
			//}
			
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
 	     logger.info("count---RECORDS----------->"+count);
	     logger.info("search Query...." + bufInsSql);
	     int size = searchlist.size();
		logger.info("searchLIst size:----" + size);
		for (int i = 0; i < size; i++) {
		/*	logger.info("searchQueueEdit "
					+ searchlist.get(i).toString());*/
			data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				conteactRecVO  = new ContactRecordingSearchVO();
				   
		     //    logger.info("Exclation Flag:000---"+CommonFunction.checkNull(data.get(7)));
				if(CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("Y")){
					conteactRecVO.setLoanno("<a href=#" +CommonFunction.checkNull(data.get(1).toString()).trim()+" onclick="+"openAdhocContactRecordingTrail('"+CommonFunction.checkNull(data.get(1).toString()).trim()+"','"+CommonFunction.checkNull(data.get(7))+"')"+" style=color:red>"+CommonFunction.checkNull(data.get(0)).toString().trim()+"</a>");	
				}else{
					conteactRecVO.setLoanno("<a href=#" +CommonFunction.checkNull(data.get(1).toString()).trim()+" onclick="+"openAdhocContactRecordingTrail('"+CommonFunction.checkNull(data.get(1).toString()).trim()+"','"+CommonFunction.checkNull(data.get(7))+"')"+">"+CommonFunction.checkNull(data.get(0)).toString().trim()+"</a>");
				}
				conteactRecVO.setCustomername(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2)).trim()));
		    	  
		    	
		    		if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).equalsIgnoreCase("")){
						
						String gpos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3)));
						conteactRecVO.setPos1(gpos);
						}else{

						Number gpos =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
														
						conteactRecVO.setPos1(myFormatter.format(gpos));
						}
		    		 if(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).equalsIgnoreCase("")){
		 				
		       		  String gtos = StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4)));
		       		conteactRecVO.setTos1(myFormatter.format(gtos));
		   			}else{

		   				Number gtos =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
		   				conteactRecVO.setTos1(myFormatter.format(gtos));
		   			
		   				}
		    		 conteactRecVO.setQueue(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5)).trim()));
		    		 conteactRecVO.setAssignto(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(6)).trim()));
		    		 conteactRecVO.setExclationFlag(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(7)).trim()));
		    	  conteactRecVO.setTotalRecordSize(count);
		    	  detailListGrid.add(conteactRecVO);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		loanNo= null;
		customerName= null;
		product= null;
		queue= null;
		pos1= null;
		pos2= null;
		dpd1= null;
		dpd2= null;
		user= null;
		tos1= null;
		tos2= null;
		npastage= null;
		customertype= null;
		customercategory= null;
		assigndatefrom= null;
		assigndateto= null;
		balanceprincipal= null;
		bufInsSql =null;
		bufInsSqlTempCount=null;
		lppChargeQuery= null;
		cbcquery= null;
		bccQuery= null;
		LPP= null;
		CBC= null;
		otherCharge= null;
		data = null;
		searchlist = null;
		conteactRecVO = null;
	}
	return detailListGrid;	
	
}
/*  Nazia work space ends for adhoc Contact Recording*/

//Changes Start for Resolution Detail
@Override
public ArrayList<CRFollowUpTrailsDtlVO> getActionResolutionDtl(
		CRFollowUpTrailsDtlVO vo) {
	// TODO Auto-generated method stub
	return null;
}



@Override
public String SaveActionResolutionDtl(CRFollowUpTrailsDtlVO vo) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String  getActionDesc(String actionId){

	
	return null;
}
@Override
public ArrayList<CRFollowUpTrailsDtlVO> getResolutionFollowUpType(){
	int count=0;
	ArrayList mainList=new ArrayList();
	ArrayList subList=new ArrayList();
	String query="";
	CRFollowUpTrailsDtlVO resolVo=null;
	
	 ArrayList<CRFollowUpTrailsDtlVO> resolTypeList=new  ArrayList<CRFollowUpTrailsDtlVO>();
		try{	
              query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='COLL_RESOLUTION_TYPE' and rec_status ='A'";              		
			  logger.info("In getResolutionFollowUpType......Dao Impl:::"+query);
			    mainList = ConnectionDAO.sqlSelect(query);
			    int size = mainList.size();
			  for(int i=0;i<size;i++){
			   subList=(ArrayList) mainList.get(i);
			   if(subList.size()>0){
				   resolVo=new CRFollowUpTrailsDtlVO();
				   resolVo.setResolutionType(subList.get(0).toString());
				   resolVo.setResolTypeval(subList.get(1).toString());
				   resolTypeList.add(resolVo);
				}
			  }
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			mainList=null;
			subList=null;
			query=null;
			resolVo=null;
		}
		return resolTypeList;
}

@Override
public ArrayList<CRFollowUpTrailsDtlVO> getResolutionFollowUpStatus(){
	int count=0;
	ArrayList mainList=new ArrayList();
	ArrayList subList=new ArrayList();
	String query="";
	CRFollowUpTrailsDtlVO resolVo=null;
	
	 ArrayList<CRFollowUpTrailsDtlVO> resolStausList=new  ArrayList<CRFollowUpTrailsDtlVO>();
		try{	
              query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='COLL_RESOLUTION_STATUS' and rec_status ='A'";              		
			  logger.info("In getResolutionFollowUpStatus......Dao Impl:::"+query);
			    mainList = ConnectionDAO.sqlSelect(query);
			    int size = mainList.size();
			  for(int i=0;i<size;i++){
			   subList=(ArrayList) mainList.get(i);
			   if(subList.size()>0){
				   resolVo=new CRFollowUpTrailsDtlVO();
				   resolVo.setResolutionStatus(subList.get(0).toString());
				   resolVo.setResolStatusval(subList.get(1).toString());
				   resolStausList.add(resolVo);
				}
			  }
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			mainList=null;
			subList=null;
			query=null;
			resolVo=null;
		}
		return resolStausList;
}
//Changes End for Resolution Detail



@Override
public String saveQueueAllocation(AllocationMasterVo collVo,
		String[] percentage, int total, String[] queueuser) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}



@Override
public ArrayList<ClosureVO> getDuesRefundsList(String companyId, String loanId,
		String effectiveDate, String closureType, String source) {
	// TODO Auto-generated method stub
	return null;
}



@Override
public ArrayList<Object> getContactListApplicant() {
	// TODO Auto-generated method stub
	return null;
}

}