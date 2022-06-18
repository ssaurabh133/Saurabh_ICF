package com.payout.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.payout.dao.PayoutMasterDAO;
import com.payout.vo.ActivityMasterVO;
import com.payout.vo.BPMasterVO;
import com.payout.vo.PayReportVO;
import com.payout.vo.PaymentReceiptVO;
import com.payout.vo.ScheduleMasterVO;
import com.payout.vo.SchemeBpMapVO;
import com.payout.vo.SchemeVO;
import com.payout.vo.TaxMasterVO;

public class MSSQLPayoutMasterDaoImpl implements PayoutMasterDAO{

	private static final Logger logger = Logger.getLogger(MSSQLPayoutMasterDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dbo=resource.getString("lbl.dbPrefix");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	/*
	 * Code For Activity Master Starts Here By Arun 
	 * */
	public ArrayList<ActivityMasterVO> searchActivityCodeData(
			ActivityMasterVO vo) {
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
	ActivityMasterVO activityVo =null;
	
	ArrayList<ActivityMasterVO> detailList = new ArrayList<ActivityMasterVO>();
	try {
		logger.info("In PayoutMasterDaoImpl searchActivityCodeData()......");
		codeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchActivityCode())).trim());
		codeDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchActivityDesc())).trim());
		

		bufInsSql.append("SELECT a.ACTIVITY_CODE,a.ACTIVITY_DESC,a.MAPPING_SOURCE_ID,(SELECT SOURCE_NAME FROM PAY_SOURCE_M b WHERE a.MAPPING_SOURCE_ID=b.SOURCE_ID) SOURCE_NAME,MAPPING_CODE,");
		bufInsSql.append("CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS FROM pay_activity_m a where 'a'='a' ");
		bufInsSqlTempCount.append("SELECT COUNT(1) FROM pay_activity_m a where 'a'='a' ");
        if(!codeId.equalsIgnoreCase("")){
        	bufInsSql.append(" and a.ACTIVITY_CODE='"+codeId+"'"); 
        	bufInsSqlTempCount.append(" and a.ACTIVITY_CODE='"+codeId+"'");
         }
        if(!codeDesc.equalsIgnoreCase("")){
        	bufInsSql.append(" and a.ACTIVITY_DESC like '%"+codeDesc+"%'"); 
        	bufInsSqlTempCount.append(" and a.ACTIVITY_DESC like '%"+codeDesc+"%'");
        }
		
		//bufInsSql.append(" ORDER BY ACTIVITY_CODE");
	
		logger.info("search Query...." + bufInsSql);
		logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
		count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

       //if ((codeId.trim() == null && codeDesc.trim() == null) || (codeId.trim().equalsIgnoreCase("") && codeDesc.trim().equalsIgnoreCase(""))
		//		|| vo.getCurrentPageLink() > 1) {

			logger.info("current PAge Link no .................... "+ vo.getCurrentPageLink());
			if (vo.getCurrentPageLink() > 1) {
				startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+ startRecordIndex);
				logger.info("endRecordIndex .................... "+ endRecordIndex);
			}
            
			bufInsSql.append(" ORDER BY ACTIVITY_CODE OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			//bufInsSql.append(" limit " + startRecordIndex + ","+ endRecordIndex);
		
		//}
		logger.info("query : " + bufInsSql.toString());
		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		logger.info("In PayoutMasterDaoImpl searchActivityCodeData() search query1 ### "+ bufInsSql.toString());
		logger.info("searchActivityData " + searchlist.size());
		int size = searchlist.size();
		for (int i = 0; i < size; i++) {
			data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				activityVo=new ActivityMasterVO();
				activityVo.setActivityCodeModify("<a href='activityMasterDispatch.do?method=openEditActivity&activityCode="
						+ CommonFunction.checkNull(data.get(0)).toString()+ "' >"
						+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
				activityVo.setActivityCode(CommonFunction.checkNull(data.get(0)).toString());
				activityVo.setActivityDesc(CommonFunction.checkNull(data.get(1)).toString());
				activityVo.setSourceId(CommonFunction.checkNull(data.get(2)).toString());
				activityVo.setSourcedesc(CommonFunction.checkNull(data.get(3)).toString());
				activityVo.setMappingCode(CommonFunction.checkNull(data.get(4)).toString());
				activityVo.setRecStatus(CommonFunction.checkNull(data.get(5)).toString());
				activityVo.setTotalRecordSize(count);
				detailList.add(activityVo);
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
		
	}

	return detailList;
	}

	@Override
	public ArrayList<ActivityMasterVO> sourceList() {
		ArrayList<ActivityMasterVO> detailList = new ArrayList<ActivityMasterVO>();
		ArrayList searchList=new ArrayList();
		ArrayList StageList=new ArrayList();
		ActivityMasterVO vo=null;
		try{
			logger.info("In sourceList searchActivityCodeData()......");
			String query="Select SOURCE_ID,SOURCE_NAME from PAY_SOURCE_M where REC_STATUS='A' ";
            logger.info("In npastage......Dao Impl"+query);
				
            searchList=ConnectionDAOforEJB.sqlSelect(query);
            logger.info("query::::::::::::::::::"+query);
            int size = searchList.size();
            for(int i=0; i<size; i++){
          	  StageList=(ArrayList)searchList.get(i);
          	  if(StageList.size()>0){
          		vo=new ActivityMasterVO();
          		  
          		 vo.setSourceId(CommonFunction.checkNull(StageList.get(0)));
				 vo.setSourcedesc(CommonFunction.checkNull(StageList.get(1)));
				 detailList.add(vo); 
				
          	  }
            }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}

	@Override
	public String saveActivityCode(ActivityMasterVO vo) {
		boolean status = false;
		logger.info("In saveActivityCode..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
	 try {
			String query = "Select ACTIVITY_CODE from pay_activity_m where ACTIVITY_CODE='"+ StringEscapeUtils.escapeSql(vo.getActivityCode().trim()) + "'";
			logger.info("In saveActivityCode.....................................Dao Impl" + query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			query=null;
		if (!st) {
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
		
				bufInsSql.append("INSERT into pay_activity_m (ACTIVITY_CODE,ACTIVITY_DESC,MAPPING_SOURCE_ID,MAPPING_CODE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//ACTIVITY_CODE
				bufInsSql.append(" ?,");//ACTIVITY_DESC
				bufInsSql.append(" ?,");//MAPPING_SOURCE_ID
				bufInsSql.append(" ?,");//MAPPING_CODE
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//AUTHOR_DATE
				
				
				if (CommonFunction.checkNull(vo.getActivityCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getActivityCode().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getActivityDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getActivityDesc().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getSourceId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSourceId().trim());
                
				if (CommonFunction.checkNull(vo.getMappingCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMappingCode().trim());


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
				    
				logger.info("IN saveActivityCode() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveActivityCode......................"+ status);
				if(status){
					resultStr="Saved";
				}else{
					resultStr="notSaved";
				}
			} else {
				resultStr="Already";
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
			
		}
		return resultStr;
	}
	
	public String updateActivityCode(ActivityMasterVO vo) {
		boolean status = false;
		logger.info("In saveActivityCode..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
	 try {
			
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
		
				bufInsSql.append("Update pay_activity_m set ACTIVITY_DESC=?,MAPPING_SOURCE_ID=?,MAPPING_CODE=?,REC_STATUS=?, ");				
				bufInsSql.append(" MAKER_ID=?,MAKER_DATE= ");
				bufInsSql.append("CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append("AUTHOR_ID=?, AUTHOR_DATE=" );
				bufInsSql.append("CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				bufInsSql.append(" where ACTIVITY_CODE=?");
				
				
				if (CommonFunction.checkNull(vo.getActivityDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getActivityDesc().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getSourceId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSourceId().trim());
                
				if (CommonFunction.checkNull(vo.getMappingCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMappingCode().trim());


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
				
				if (CommonFunction.checkNull(vo.getActivityCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getActivityCode().toUpperCase().trim());

				 insertPrepStmtObject.setSql(bufInsSql.toString());
				    
				logger.info("IN saveActivityCode() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveActivityCode......................"+ status);
				if(status){
					resultStr="Saved";
				}else{
					resultStr="notSaved";
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
			
		}
		return resultStr;
	}
	public ArrayList<ActivityMasterVO> openEditActivity(ActivityMasterVO vo) {
	String codeId = "";

	ArrayList searchlist = new ArrayList();
	ArrayList data= new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();

	ActivityMasterVO activityVo =null;
	
	ArrayList<ActivityMasterVO> detailList = new ArrayList<ActivityMasterVO>();
	try {
		logger.info("In PayoutMasterDaoImpl openEditActivity()......");
		codeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getActivityCode())).trim());
		

		bufInsSql.append("SELECT a.ACTIVITY_CODE,a.ACTIVITY_DESC,a.MAPPING_SOURCE_ID,(SELECT SOURCE_NAME FROM PAY_SOURCE_M b WHERE a.MAPPING_SOURCE_ID=b.SOURCE_ID) SOURCE_NAME,MAPPING_CODE,");
		bufInsSql.append("CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS FROM pay_activity_m a where a.ACTIVITY_CODE='"+codeId+"' ");
		
	
	
		logger.info("query : " + bufInsSql);
		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		logger.info("In PayoutMasterDaoImpl openEditActivity() search query1 ### "+ bufInsSql.toString());
		logger.info("openEditActivity " + searchlist.size());
		int size = searchlist.size();
		for (int i = 0; i < size; i++) {
			data = (ArrayList) searchlist.get(i);
			if (data.size() > 0) {
				activityVo=new ActivityMasterVO();
				activityVo.setActivityCode(CommonFunction.checkNull(data.get(0)).toString());
				activityVo.setActivityDesc(CommonFunction.checkNull(data.get(1)).toString());
				activityVo.setSourceId(CommonFunction.checkNull(data.get(2)).toString());
				activityVo.setSourcedesc(CommonFunction.checkNull(data.get(3)).toString());
				activityVo.setMappingCode(CommonFunction.checkNull(data.get(4)).toString());
				activityVo.setRecStatus(CommonFunction.checkNull(data.get(5)).toString());
				
				detailList.add(activityVo);
			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		codeId = null;
		searchlist = null;
		data= null;
		bufInsSql = null;
		
		
	}

	return detailList;
	}
	/*
	 * Code For Activity Master Ends Here By Arun  
	 * */
	
	/*
	 * Code For Tax Master Starts Here By Arun  
	 * */
	
	public ArrayList<TaxMasterVO> openEditTax(TaxMasterVO vo) {
		String taxName = "";
		String activityCode = "";
		String taxPer="";
		String stateId="";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		TaxMasterVO taxMasterVO =null;
		
		ArrayList<TaxMasterVO> detailList = new ArrayList<TaxMasterVO>();
		try {
			logger.info("In PayoutMasterDaoImpl openEditTax()......");
			taxName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchTaxCode())).trim());
			activityCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchActivityCode())).trim());
			taxPer= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchTaxPer())).trim());
			stateId=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchStateId())).trim());
			
			bufInsSql.append("SELECT TAX_ID,TAX_DESC,A.ACTIVITY_CODE,(SELECT ACTIVITY_DESC FROM pay_activity_m B WHERE B.ACTIVITY_CODE=A.ACTIVITY_CODE) ACTIVITY_DESC,");
			bufInsSql.append("TAX_PERCENT,A.STATE_ID,(SELECT STATE_DESC FROM com_state_m C WHERE C.STATE_ID=A.STATE_ID) STATE, ");
			bufInsSql.append(" CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS FROM PAY_TAX_M A where TAX_ID='"+CommonFunction.checkNull(vo.getTaxId())+"' ");
			
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl openEditTax() search query1 ### "+ bufInsSql.toString());
			logger.info("searchActivityData " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					taxMasterVO=new TaxMasterVO();
					taxMasterVO.setTaxId(CommonFunction.checkNull(data.get(0)).toString());
					taxMasterVO.setTaxName(CommonFunction.checkNull(data.get(1)).toString());
					taxMasterVO.setActivityCode(CommonFunction.checkNull(data.get(2)).toString());
					taxMasterVO.setActivityDesc(CommonFunction.checkNull(data.get(3)).toString());
					if(CommonFunction.checkNull(data.get(4)).toString().equalsIgnoreCase("")){
						taxMasterVO.setTaxPer("0.0");	
					}else{
						Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
	    	    		taxMasterVO.setTaxPer(myFormatter.format(percentage));	
					}
					taxMasterVO.setStateId(CommonFunction.checkNull(data.get(5)).toString());
					taxMasterVO.setStateName(CommonFunction.checkNull(data.get(6)).toString());
					taxMasterVO.setRecStatus(CommonFunction.checkNull(data.get(7)).toString());
					detailList.add(taxMasterVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			taxPer = null;
			taxName = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}


	public String saveTaxMaster(TaxMasterVO vo) {
		boolean status = false;
		logger.info("In saveTaxMaster..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
		String num="";
	 try {
		
		 String query = "Select TAX_DESC from pay_tax_m where TAX_DESC='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getTaxName()))+"' and  isnull(ACTIVITY_CODE,'')='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxActivityCode()))+"' and isnull(STATE_ID,'') ='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getTxtStateCode().trim())) + "'";
			logger.info("In saveTaxMaster.....................................Dao Impl" + query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			query=null;
		  if (!st) {
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
		
				bufInsSql.append("INSERT INTO pay_tax_m (TAX_DESC,ACTIVITY_CODE,TAX_PERCENT,STATE_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//TAX_DESC
				bufInsSql.append(" ?,");//ACTIVITY_CODE
				bufInsSql.append(" ?,");//TAX_PERCENT
				bufInsSql.append(" ?,");//STATE_ID
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append("CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append("CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//AUTHOR_DATE
								
			
				if (CommonFunction.checkNull(vo.getTaxName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTaxName().trim());

				if (CommonFunction.checkNull(vo.getLbxActivityCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxActivityCode().trim());
				
				if (CommonFunction.checkNull(vo.getTaxPer()).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.00");
				else
					 num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getTaxPer().trim())).toString();
					insertPrepStmtObject.addString(num);
                
				if (CommonFunction.checkNull(vo.getTxtStateCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTxtStateCode().trim());


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
				    
				logger.info("IN saveTaxMaster() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveTaxMaster......................"+ status);
				if(status){
					resultStr="Saved";
				}else{
					resultStr="notSaved";
				}
			} else {
				resultStr="Already";
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
			
		}
		return resultStr;
	}

	
	public ArrayList<TaxMasterVO> searchTaxMasterData(TaxMasterVO vo) {
		String taxName = "";
		String activityCode = "";
		String taxPer="";
		String stateId="";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		TaxMasterVO taxMasterVO =null;
		
		ArrayList<TaxMasterVO> detailList = new ArrayList<TaxMasterVO>();
		try {
			logger.info("In PayoutMasterDaoImpl searchTaxMasterData()......");
			taxName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchTaxCode())).trim());
			activityCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchActivityCode())).trim());
			taxPer= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchTaxPer())).trim());
			stateId=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtStateCode())).trim());
			
			bufInsSql.append("SELECT TAX_ID,TAX_DESC,B.ACTIVITY_DESC,");
			bufInsSql.append("TAX_PERCENT,(SELECT STATE_DESC FROM com_state_m C WHERE C.STATE_ID=A.STATE_ID) STATE, ");
			bufInsSql.append(" CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS FROM PAY_TAX_M A join pay_activity_m B  on B.ACTIVITY_CODE=A.ACTIVITY_CODE where 'a'='a' ");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM PAY_TAX_M a join pay_activity_m B on B.ACTIVITY_CODE=A.ACTIVITY_CODE where 'a'='a' ");
			
			if(!taxName.equalsIgnoreCase("")){
	        	bufInsSql.append(" and A.TAX_DESC like '%"+taxName+"%'"); 
	        	bufInsSqlTempCount.append(" and A.TAX_DESC like '%"+taxName+"%'");
	        }
			if(!activityCode.equalsIgnoreCase("")){
	        	bufInsSql.append(" and B.ACTIVITY_DESC  like '%"+activityCode+"%' "); 
	        	bufInsSqlTempCount.append(" and B.ACTIVITY_DESC  like '%"+activityCode+"%' ");
	         }
			if(!taxPer.equalsIgnoreCase("")){
				String num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getSearchTaxPer().trim())).toString();
	        	bufInsSql.append(" and A.TAX_PERCENT='"+num+"'"); 
	        	bufInsSqlTempCount.append(" and A.TAX_PERCENT='"+num+"'");
	         }
			if(!stateId.equalsIgnoreCase("")){
	        	bufInsSql.append(" and A.STATE_ID='"+stateId+"'"); 
	        	bufInsSqlTempCount.append(" and A.STATE_ID='"+stateId+"'");
	         }
			
			//bufInsSql.append(" ORDER BY A.TAX_DESC");
		
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

	      /* if ((taxName.trim() == null && activityCode.trim() == null && taxPer==null && stateId==null) 
	    		|| (taxName.trim().equalsIgnoreCase("") && activityCode.trim().equalsIgnoreCase("") && taxPer.equalsIgnoreCase("")&& stateId.equalsIgnoreCase(""))
			    || vo.getCurrentPageLink() > 1) {*/

				logger.info("current PAge Link no .................... "+ vo.getCurrentPageLink());
				if (vo.getCurrentPageLink() > 1) {
					startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
				}
				bufInsSql.append(" ORDER BY A.TAX_DESC OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				//bufInsSql.append(" limit " + startRecordIndex + ","+ endRecordIndex);
			
			//}
			logger.info("query : " + bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl searchTaxMasterData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchTaxMasterData:- " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					taxMasterVO=new TaxMasterVO();
					taxMasterVO.setTaxId("<a href='taxMasterDispatch.do?method=openEditTax&taxId="
							+ CommonFunction.checkNull(data.get(0)).toString()+ "' >"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
					taxMasterVO.setTaxName(CommonFunction.checkNull(data.get(1)).toString());
					taxMasterVO.setActivityCode(CommonFunction.checkNull(data.get(2)).toString());
					taxMasterVO.setTaxPer(CommonFunction.checkNull(data.get(3)).toString());
					taxMasterVO.setStateName(CommonFunction.checkNull(data.get(4)).toString());
					taxMasterVO.setRecStatus(CommonFunction.checkNull(data.get(5)).toString());
					taxMasterVO.setTotalRecordSize(count);
					detailList.add(taxMasterVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			taxPer = null;
			taxName = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}

	
	public String updateTaxMaster(TaxMasterVO vo) {
		boolean status = false;
		logger.info("In updateTaxMaster..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
		String num="";
	 try {
			String query = "Select TAX_DESC from pay_tax_m where TAX_DESC='"+StringEscapeUtils.escapeSql(vo.getTaxName())+"' and  ACTIVITY_CODE='"+StringEscapeUtils.escapeSql(vo.getLbxActivityCode())+"' and STATE_ID ='"+ StringEscapeUtils.escapeSql(vo.getTxtStateCode().trim()) + "' and tax_id <>'"+StringEscapeUtils.escapeSql(vo.getTaxId().trim())+"'";
			logger.info("In updateTaxMaster.....................................Dao Impl" + query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			query=null;
		if (!st) {
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
		
				bufInsSql.append("update pay_tax_m set TAX_DESC=?,ACTIVITY_CODE=?,TAX_PERCENT=?,STATE_ID=?,REC_STATUS=?");				
				bufInsSql.append(",MAKER_ID=?,MAKER_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
				bufInsSql.append(" ,AUTHOR_ID=?,AUTHOR_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				bufInsSql.append("  where TAX_ID=?");
				
				
				if (CommonFunction.checkNull(vo.getTaxName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTaxName().trim());

				if (CommonFunction.checkNull(vo.getLbxActivityCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxActivityCode().trim());
				
				if (CommonFunction.checkNull(vo.getTaxPer()).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.00");
				else
					 num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getTaxPer().trim())).toString();
					insertPrepStmtObject.addString(num);
                
				if (CommonFunction.checkNull(vo.getTxtStateCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTxtStateCode().trim());


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
				
				insertPrepStmtObject.addString(vo.getTaxId());
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				    
				logger.info("IN updateTaxMaster() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In updateTaxMaster......................"+ status);
				if(status){
					resultStr="Saved";
				}else{
					resultStr="notSaved";
				}
			} else {
				resultStr="Already";
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
			
		}
		return resultStr;
	}
	/*
	 * Code For Tax Master Ends Here By Arun  
	 * */
	/*
	 * Code For BP Master Starts Here By Arun  
	 * */
	@Override
	public ArrayList<BPMasterVO> openEditBp(BPMasterVO vo) {

		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		BPMasterVO bpMasterVO =null;
		
		ArrayList<BPMasterVO> detailList = new ArrayList<BPMasterVO>();
		try {
			logger.info("In PayoutMasterDaoImpl openEditBp()......");
			
			bufInsSql.append("Select a.BP_ID,BP_NAME,BP_ADDRESS,");
			bufInsSql.append("a.BP_COUNTRY_ID,(Select COUNTRY_DESC from com_country_m where COUNTRY_ID= BP_COUNTRY_ID)COUNTRY,");
			bufInsSql.append("a.BP_STATE_ID,(Select STATE_DESC from com_state_m where STATE_ID=BP_STATE_ID) STATE, ");
			bufInsSql.append("BP_DISTRICT_ID,(Select DISTRICT_DESC from com_district_m where DISTRICT_ID=BP_DISTRICT_ID) CITY,");
			bufInsSql.append("BP_MOBILE,BP_PHONE,CP_NAME,CP_ADDRESS,");
			bufInsSql.append("a.CP_COUNTRY_ID,(Select COUNTRY_DESC from com_country_m where COUNTRY_ID= CP_COUNTRY_ID)CPCOUNTRY,");
			bufInsSql.append("a.CP_STATE_ID,(Select STATE_DESC from com_state_m where STATE_ID=CP_STATE_ID) CPSTATE, ");
			bufInsSql.append("a.CP_DISTRICT_ID,(Select DISTRICT_DESC from com_district_m where DISTRICT_ID=CP_DISTRICT_ID) CPCITY,");
			bufInsSql.append("CP_MOBILE,MAPPING_SOURCE_ID,MAPPING_CODE,");
			bufInsSql.append("CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS from pay_bp_m a ");
			bufInsSql.append(" where a.BP_ID='"+vo.getBpId()+"' ");

			
			
			logger.info("query : " + bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			

			logger.info("In PayoutMasterDaoImpl openEditBp() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditBp data :- " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					bpMasterVO=new BPMasterVO();
					bpMasterVO.setBpId(CommonFunction.checkNull(data.get(0)).toString());
					bpMasterVO.setBpName(CommonFunction.checkNull(data.get(1)).toString());
					bpMasterVO.setBpAdd(CommonFunction.checkNull(data.get(2)).toString() );
					bpMasterVO.setTxtCountryCode(CommonFunction.checkNull(data.get(3)).toString());
					bpMasterVO.setCountryDesc(CommonFunction.checkNull(data.get(4)).toString());
					bpMasterVO.setStateId(CommonFunction.checkNull(data.get(5)).toString());
					bpMasterVO.setTxtStateCode(CommonFunction.checkNull(data.get(6)).toString());
					bpMasterVO.setDistrictId(CommonFunction.checkNull(data.get(7)).toString());
					bpMasterVO.setTxtDistCode(CommonFunction.checkNull(data.get(8)).toString());
					bpMasterVO.setBpMobile(CommonFunction.checkNull(data.get(9)).toString());
					bpMasterVO.setBpPhone(CommonFunction.checkNull(data.get(10)).toString());
					bpMasterVO.setCpName(CommonFunction.checkNull(data.get(11)).toString());
					bpMasterVO.setCpAdd(CommonFunction.checkNull(data.get(12)).toString());
					bpMasterVO.setCpCountryCode(CommonFunction.checkNull(data.get(13)).toString());
					bpMasterVO.setCpCountryDesc(CommonFunction.checkNull(data.get(14)).toString());
					bpMasterVO.setCpStateCode(CommonFunction.checkNull(data.get(15)).toString());
					bpMasterVO.setCpStateDesc(CommonFunction.checkNull(data.get(16)).toString());
					bpMasterVO.setCpDistCode(CommonFunction.checkNull(data.get(17)).toString());
					bpMasterVO.setCpDistDesc(CommonFunction.checkNull(data.get(18)).toString());
					bpMasterVO.setCpMobile(CommonFunction.checkNull(data.get(19)).toString());
					bpMasterVO.setSourceId(CommonFunction.checkNull(data.get(20)).toString());
					bpMasterVO.setMappingCode(CommonFunction.checkNull(data.get(21)).toString());
					bpMasterVO.setRecStatus(CommonFunction.checkNull(data.get(22)).toString());
					detailList.add(bpMasterVO);
				}

			}
			/*bufInsSql=null;
			bufInsSql = new StringBuilder();
			bufInsSql.append("Select a.ACTIVITY_CODE,b.ACTIVITY_DESC from pay_bp_activity_map a join pay_activity_m b");
			bufInsSql.append(" on a.ACTIVITY_CODE=b.ACTIVITY_CODE where a.BP_ID='"+vo.getBpId()+"' ");
			searchlistActivity = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					
				}
				}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			searchlist = null;
			data= null;
			bufInsSql = null;
			
			
		}

		return detailList;
	}
	
	@Override
	public ArrayList<BPMasterVO> openEditBpActivity(BPMasterVO vo) {
		
	
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		BPMasterVO bpMasterVO =null;
	
		ArrayList<BPMasterVO> detailList = new ArrayList<BPMasterVO>();
		try {
			logger.info("In PayoutMasterDaoImpl openEditBp()......");
			
			bufInsSql.append("Select a.ACTIVITY_CODE,b.ACTIVITY_DESC from pay_bp_activity_map a join pay_activity_m b");
			bufInsSql.append(" on a.ACTIVITY_CODE=b.ACTIVITY_CODE where a.BP_ID='"+vo.getBpId()+"' ");
			logger.info("query openEditBpActivity: " + bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					bpMasterVO=new BPMasterVO();
					bpMasterVO.setLbxActivityCode(CommonFunction.checkNull(data.get(0)).toString());
					bpMasterVO.setActivityCode(CommonFunction.checkNull(data.get(1)).toString());
					
					detailList.add(bpMasterVO);
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			searchlist = null;
			data= null;
			bufInsSql = null;
			
			
		}

		return detailList;
	}
	
	
	@Override
	public String saveBpMaster(BPMasterVO vo) {
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		String status="";
		try{
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				status = "A";
			} else {
				status = "X";

				}
			logger.info("In PayoutMasterDaoImpl saveBpMaster()......");
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBpName())).trim());         // I_BP_NAME
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBpAdd())).trim());          // I_BP_ADDRESS
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtDistCode())).trim());    // I_BP_DISTRICT_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtStateCode())).trim());   // I_BP_STATE_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtCountryCode())).trim()); // I_BP_COUNTRY_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBpMobile())).trim());       // I_BP_MOBILE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBpPhone())).trim());        // I_BP_PHONE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpName())).trim());         // I_CP_NAME
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpAdd())).trim());          // I_CP_ADDRESS
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpDistCode())).trim());     // I_CP_DISTRICT_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpStateCode())).trim());    // I_CP_STATE_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpCountryCode())).trim());  // I_CP_COUNTRY_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpMobile())).trim());       // I_CP_MOBILE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSourceId())).trim());       // I_MAPPING_SOURCE_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMappingCode())).trim());      // I_MAPPING_CODE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(status)).trim());                 // I_REC_STATUS
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId())).trim());        // I_MAKER_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate())).trim()); // I_MAKER_DATE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxActivityCode()+"|")).trim()); // I_ACTIVITY_CODE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dateFormatWithTime)).trim()); // I_DATE_FORMAT
			in.add(""); // I_BP_ID
			in.add("I"); // I_INSERT_UPDATE_FLAG
			out.add(s1);
			out.add(s2);
	
			logger.info("PAY_BP_SAVE ("+in.toString()+","+out.toString()+")");
			outMessages=(ArrayList) ConnectionDAOforEJB.callSP("PAY_BP_SAVE",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
		}catch(Exception e){
			e.printStackTrace();
			}
		return s1;
	}


	public ArrayList<BPMasterVO> searchBpMasterData(BPMasterVO vo) {
		String bpName = "";
		String activityCode = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		BPMasterVO bpMasterVO =null;
		
		ArrayList<BPMasterVO> detailList = new ArrayList<BPMasterVO>();
		try {
			logger.info("In PayoutMasterDaoImpl searchBpMasterData()......");
			bpName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchBpName())).trim());
			activityCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchActivityCode())).trim());
			
			bufInsSql.append("Select a.BP_ID,BP_NAME,c.ACTIVITY_DESC,BP_ADDRESS,");
			bufInsSql.append("(Select DISTRICT_DESC from com_district_m where DISTRICT_ID=BP_DISTRICT_ID) CITY,");
			bufInsSql.append("(Select STATE_DESC from com_state_m where STATE_ID=BP_STATE_ID) STATE, ");
			bufInsSql.append("(Select COUNTRY_DESC from com_country_m where COUNTRY_ID= BP_COUNTRY_ID)COUNTRY,");
			bufInsSql.append("CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS from pay_bp_m a join pay_bp_activity_map b");
			bufInsSql.append(" on a.BP_ID=b.BP_ID join pay_activity_m c on b.ACTIVITY_CODE=c.ACTIVITY_CODE where  'a'='a'");
			
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM ( ");
			bufInsSqlTempCount.append("Select a.BP_ID,BP_NAME,c.ACTIVITY_DESC,BP_ADDRESS,");
			bufInsSqlTempCount.append("(Select DISTRICT_DESC from com_district_m where DISTRICT_ID=BP_DISTRICT_ID) CITY,");
			bufInsSqlTempCount.append("(Select STATE_DESC from com_state_m where STATE_ID=BP_STATE_ID) STATE, ");
			bufInsSqlTempCount.append("(Select COUNTRY_DESC from com_country_m where COUNTRY_ID= BP_COUNTRY_ID)COUNTRY,");
			bufInsSqlTempCount.append(" CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS from pay_bp_m a join pay_bp_activity_map b");
			bufInsSqlTempCount.append(" on a.BP_ID=b.BP_ID join pay_activity_m c on b.ACTIVITY_CODE=c.ACTIVITY_CODE where  'a'='a'");
			
			
			if(!bpName.equalsIgnoreCase("")){
	        	bufInsSql.append(" and a.BP_NAME like '%"+bpName+"%'"); 
	        	bufInsSqlTempCount.append(" and a.BP_NAME like '%"+bpName+"%'");
	        }
			if(!activityCode.equalsIgnoreCase("")){
	        	bufInsSql.append(" and c.ACTIVITY_DESC like '%"+activityCode+"%'"); 
	        	bufInsSqlTempCount.append(" and c.ACTIVITY_DESC like '%"+activityCode+"%'");
	         }
			
			
			//bufInsSql.append(" ORDER BY BP_NAME");
			bufInsSqlTempCount.append(" ) tab");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

	       /*if ((bpName.trim() == null && activityCode.trim() == null ) 
	    		|| (bpName.trim().equalsIgnoreCase("") && activityCode.trim().equalsIgnoreCase("") )
			    || vo.getCurrentPageLink() > 1) {*/

				logger.info("current PAge Link no .................... "+ vo.getCurrentPageLink());
				if (vo.getCurrentPageLink() > 1) {
					startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
				}

				bufInsSql.append(" ORDER BY BP_NAME OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				//bufInsSql.append(" limit " + startRecordIndex + ","+ endRecordIndex);
			
			//}
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl searchBpMasterData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchBpMasterData:- " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					bpMasterVO=new BPMasterVO();
					bpMasterVO.setBpId("<a href=bpMasterDispatch.do?method=openEditBp&bpId="
							+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
					bpMasterVO.setActivityCode(CommonFunction.checkNull(data.get(2)).toString());			
					bpMasterVO.setBpAdd(CommonFunction.checkNull(data.get(3)).toString() +","+CommonFunction.checkNull(data.get(4)).toString()+","+CommonFunction.checkNull(data.get(5)).toString()+","+CommonFunction.checkNull(data.get(6)).toString());
					bpMasterVO.setRecStatus(CommonFunction.checkNull(data.get(7)).toString());
					bpMasterVO.setTotalRecordSize(count);
					detailList.add(bpMasterVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			bpName = null;
			activityCode = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;

	}

	@Override
	public String updateBpMaster(BPMasterVO vo) {
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		String status="";
		try{
			 String query = "select BP_NAME from pay_bp_m where BP_NAME='"+CommonFunction.checkNull(vo.getBpName())+"' and MAPPING_SOURCE_ID='"+CommonFunction.checkNull(vo.getSourceId())+"' and  MAPPING_CODE='"+CommonFunction.checkNull(vo.getMappingCode())+"' and BP_ID <> '"+CommonFunction.checkNull(vo.getBpId())+"'";
			 logger.info("In updateBpMaster.....................................Dao Impl" + query);
				boolean st = ConnectionDAOforEJB.checkStatus(query);
				if(!st){
					
				
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				status = "A";
			} else {
				status = "X";

				}
			logger.info("In PayoutMasterDaoImpl updateBpMaster()......");
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBpName())).trim());         // I_BP_NAME
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBpAdd())).trim());          // I_BP_ADDRESS
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtDistCode())).trim());    // I_BP_DISTRICT_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtStateCode())).trim());   // I_BP_STATE_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTxtCountryCode())).trim()); // I_BP_COUNTRY_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBpMobile())).trim());       // I_BP_MOBILE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBpPhone())).trim());        // I_BP_PHONE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpName())).trim());         // I_CP_NAME
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpAdd())).trim());          // I_CP_ADDRESS
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpDistCode())).trim());     // I_CP_DISTRICT_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpStateCode())).trim());    // I_CP_STATE_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpCountryCode())).trim());  // I_CP_COUNTRY_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCpMobile())).trim());       // I_CP_MOBILE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSourceId())).trim());       // I_MAPPING_SOURCE_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMappingCode())).trim());      // I_MAPPING_CODE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(status)).trim());                 // I_REC_STATUS
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId())).trim());        // I_MAKER_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate())).trim()); // I_MAKER_DATE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxActivityCode()+"|")).trim()); // I_ACTIVITY_CODE
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dateFormatWithTime)).trim()); // I_DATE_FORMAT
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBpId())).trim()); // I_BP_ID
			in.add("U"); // I_INSERT_UPDATE_FLAG
			out.add(s1);
			out.add(s2);
	
			logger.info("PAY_BP_SAVE ("+in.toString()+","+out.toString()+")");
			outMessages=(ArrayList) ConnectionDAOforEJB.callSP("PAY_BP_SAVE",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
		}else{
			s1="Already";
			}
		}catch(Exception e){
			e.printStackTrace();
			}
		return s1;
	}
	/*
	 * Code For BP Master Ends Here By Arun  
	 * */
	
	/*
	 * Code For Scheme Master Starts Here By Arun  
	 * */



	@Override
	public String saveSchemeMaster(SchemeVO vo) {
		boolean status = false;
		logger.info("In saveSchemeMaster..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String taxApp = "X";
		String tdsApp = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
		String num="";
	 try {
		
		 String query = "Select  SCHEME_NAME from pay_scheme_m where SCHEME_NAME='"+CommonFunction.checkNull(vo.getSchemeName())+"'";
			logger.info("In saveSchemeMaster.....................................Dao Impl" + query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			
		    if (!st) {
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
			if (vo.getServiceTaxApp()!= null && vo.getServiceTaxApp().equals("on")) {
				taxApp = "A";
			} else {
				taxApp = "X";

				}
			if (vo.getTdsApp()!= null && vo.getTdsApp().equals("on")) {
				tdsApp = "A";
			} else {
				tdsApp = "X";

				}
		
				bufInsSql.append("insert into pay_scheme_m (SCHEME_NAME,NARRATION,EFFECTIVE_DATE,SCHEME_PARAMETER,SLAB_ON,SERVICE_TAX_APPLICABLE,TDS_APPLICABLE,TAT,CITY_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//SCHEME_NAME
				bufInsSql.append(" ?,");//NARRATION
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//EFFECTIVE_DATE
				bufInsSql.append(" ?,");//SCHEME_PARAMETER
				bufInsSql.append(" ?,");//SLAB_ON
				bufInsSql.append(" ?,");//SERVICE_TAX_APPLICABLE
				bufInsSql.append(" ?,");//TDS_APPLICABLE
				bufInsSql.append(" ?,");//TAT
				bufInsSql.append(" ?,");//CITY_ID
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//AUTHOR_DATE
				
				if (CommonFunction.checkNull(vo.getSchemeName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSchemeName());

				if (CommonFunction.checkNull(vo.getNarration()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getNarration());
				
				if (CommonFunction.checkNull(vo.getEffectiveDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEffectiveDate());
				
				if (CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSchemeParameter());
				
				if (CommonFunction.checkNull(vo.getSlabOn()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSlabOn());
				
				if (CommonFunction.checkNull(taxApp).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(taxApp);
				
				if (CommonFunction.checkNull(tdsApp).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(tdsApp);
				
				if (CommonFunction.checkNull(vo.getTat()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTat());
				
				if (CommonFunction.checkNull(vo.getCpDistCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCpDistCode());
			
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
				    
				logger.info("IN saveSchemeMaster() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				bufInsSql=null;
				bufInsSql=new StringBuilder();
				insertPrepStmtObject=null;
				insertPrepStmtObject=new PrepStmtObject();
				
				bufInsSql.append("insert into pay_scheme_dtl(SCHEME_NAME,COMMI_PER_CASE_R,COMMI_PER_CASE_P,FROM_CASE,TO_CASE,COMMI_PER_SLAB_R,COMMI_PER_SLAB_P,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append("values(");
				bufInsSql.append(" ?,");//SCHEME_NAME
				bufInsSql.append(" ?,");//COMMI_PER_CASE_R
				bufInsSql.append(" ?,");//COMMI_PER_CASE_P
				bufInsSql.append(" ?,");//FROM_CASE
				bufInsSql.append(" ?,");//TO_CASE
				bufInsSql.append(" ?,");//COMMI_PER_SLAB_R
				bufInsSql.append(" ?,");//COMMI_PER_SLAB_P
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//AUTHOR_DATE
				
				String[] fromCase=vo.getCaseFrom();
				String[] toCase=vo.getCaseTo();
				String[] fromCaseP=vo.getCaseFromP();
				String[] toCaseP=vo.getCaseToP();
				String[] fromCaseA=vo.getCaseFromA();
				String[] toCaseA=vo.getCaseToA();
				String[] commissionR=vo.getCommissionR();
				String[] commissionP=vo.getCommissionP();
				String[] commissionA=vo.getCommissionA();
				int size=0;
				logger.info("vo.getSchemeParameter():-------"+vo.getSchemeParameter());
				if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")){
					size=commissionR.length;
				}else if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SPW")){
					size=commissionP.length;
				}else if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
					size=commissionA.length;
				}else{
					size=0;
				}
				//logger.info("fromCase.length:-----------"+fromCase.length);
				//logger.info("toCase.length:-----------"+toCase.length);
				logger.info("commissionR.length:-----------"+commissionR.length);
				logger.info("commissionP.length:-----------"+commissionP.length);
				logger.info("commissionA.length:-----------"+commissionA.length);
				if(CommonFunction.checkNull(vo.getCommissionPerCaseR()).equalsIgnoreCase("")&& 
						CommonFunction.checkNull(vo.getCommissionPerCaseP()).equalsIgnoreCase("")){
				for(int i=0;i<size;i++){
					
					insertPrepStmtObject=null;
					insertPrepStmtObject=new PrepStmtObject();
					
					if (CommonFunction.checkNull(vo.getSchemeName()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getSchemeName());
					
					if (CommonFunction.checkNull(vo.getCommissionPerCaseR()).equalsIgnoreCase("")){
						insertPrepStmtObject.addNull();
					}else{
						String numCommissionPerCase=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionPerCaseR().trim())).toString();
					     insertPrepStmtObject.addString(numCommissionPerCase);
					}
					
					
					if (CommonFunction.checkNull(vo.getCommissionPerCaseP()).equalsIgnoreCase("")){
						insertPrepStmtObject.addNull();
					}else{
						String numCommissionPerCaseP=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionPerCaseR().trim())).toString();
					    insertPrepStmtObject.addString(numCommissionPerCaseP);
						
					}
					if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")){
						if (CommonFunction.checkNull(fromCase[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addString("0.0");
						}else{
							String fromCaseI=myFormatter.parse(StringEscapeUtils.escapeSql(fromCase[i].trim())).toString();
						    insertPrepStmtObject.addString(fromCaseI);
						 }
					}else if (CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
						if (CommonFunction.checkNull(fromCaseA[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addString("0.0");
						}else{
							String fromCaseI=myFormatter.parse(StringEscapeUtils.escapeSql(fromCaseA[i].trim())).toString();
						    insertPrepStmtObject.addString(fromCaseI);
						 }
					}else{
						if (CommonFunction.checkNull(fromCaseP[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String fromCasePI=myFormatter.parse(StringEscapeUtils.escapeSql(fromCaseP[i].trim())).toString();
						    insertPrepStmtObject.addString(fromCasePI);
							
						}
					}
					if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")){
						if (CommonFunction.checkNull(toCase[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String toCaseI=myFormatter.parse(StringEscapeUtils.escapeSql(toCase[i].trim())).toString();
						    insertPrepStmtObject.addString(toCaseI);
								
						}
					}else if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
						if (CommonFunction.checkNull(toCaseA[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String toCaseI=myFormatter.parse(StringEscapeUtils.escapeSql(toCaseA[i].trim())).toString();
						    insertPrepStmtObject.addString(toCaseI);
						}
					}else{
						if (CommonFunction.checkNull(toCaseP[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String toCasePI=myFormatter.parse(StringEscapeUtils.escapeSql(toCaseP[i].trim())).toString();
						    insertPrepStmtObject.addString(toCasePI);
							//insertPrepStmtObject.addString(toCaseP[i]);	
						}
						
					}
					
					
					if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")||CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
						if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
							if (CommonFunction.checkNull(commissionA[i]).equalsIgnoreCase("")){
								insertPrepStmtObject.addNull();
							}else{
								String commissionPI=myFormatter.parse(StringEscapeUtils.escapeSql(commissionA[i].trim())).toString();
							    insertPrepStmtObject.addString(commissionPI);
							
							}
						}else if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")){
							if (CommonFunction.checkNull(commissionR[i]).equalsIgnoreCase("")){
								insertPrepStmtObject.addNull();
							}else{
								String commissionRI=myFormatter.parse(StringEscapeUtils.escapeSql(commissionR[i].trim())).toString();
							    insertPrepStmtObject.addString(commissionRI);
							}
						}
					 }else{
						insertPrepStmtObject.addNull();	
					 }		
					
					if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SPW")){
						if (CommonFunction.checkNull(commissionP[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String commissionPI=myFormatter.parse(StringEscapeUtils.escapeSql(commissionP[i].trim())).toString();
						    insertPrepStmtObject.addString(commissionPI);
						
						}
					}else{
						insertPrepStmtObject.addNull();	
					}
					
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
					    
					logger.info("IN saveSchemeMaster() insert in pay_scheme_dtl for slab ### "	+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					
				}	
				}else{
					if (CommonFunction.checkNull(vo.getSchemeName()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getSchemeName());
					
					if (CommonFunction.checkNull(vo.getCommissionPerCaseR()).equalsIgnoreCase("")){
						insertPrepStmtObject.addString("0.00");
					}else{
						num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionPerCaseR().trim())).toString();
						insertPrepStmtObject.addString(num);
					}
					if (CommonFunction.checkNull(vo.getCommissionPerCaseP()).equalsIgnoreCase("")){
						insertPrepStmtObject.addString("0.00");
					}else{
						num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionPerCaseP().trim())).toString();
						insertPrepStmtObject.addString(num);
					}
					
					insertPrepStmtObject.addNull();
					insertPrepStmtObject.addNull();
					insertPrepStmtObject.addNull();
					insertPrepStmtObject.addNull();
					
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
					    
					logger.info("IN saveSchemeMaster() insert pay_scheme_dtl for Non Slab ### "	+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
				
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveSchemeMaster......................"+ status);
				
				if(status){
					resultStr="Saved";
				}else{
					resultStr="notSaved";
				}
			} else {
				resultStr="Already";
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
			
		}
		return resultStr;

	}

	@Override
	public ArrayList<SchemeVO> searchScemeMasterData(SchemeVO vo) {
		String schemeName = "";
		String activityCode = "";
		String narration = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		BPMasterVO bpMasterVO =null;
		SchemeVO schemeVO=null;
		ArrayList<SchemeVO> detailList = new ArrayList<SchemeVO>();
		try {
			logger.info("In PayoutMasterDaoImpl searchScemeMasterData()......");
			schemeName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchSchemeName())).trim());
			narration = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchNarration())).trim());
			
			bufInsSql.append("Select SCHEME_NAME,NARRATION," );
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(EFFECTIVE_DATE,'"+dateFormat+"'),");
			bufInsSql.append("CASE SCHEME_PARAMETER when 'CW' Then 'Case Wise' when 'PW' then 'Percent Wise' when 'SCW' then 'Slab Case Wise' when 'SPW' then 'Slab Percent Wise' when 'SFA' then 'Slab For Amount'  end schemeParameter,");
			bufInsSql.append("CASE WHEN REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS from pay_scheme_m  where 'a'='a'");
			
			bufInsSqlTempCount.append("Select count(1) from pay_scheme_m ");
			bufInsSqlTempCount.append(" where 'a'='a'");
			
			
			if(!schemeName.equalsIgnoreCase("")){
	        	bufInsSql.append(" and SCHEME_NAME like '%"+schemeName+"%'"); 
	        	bufInsSqlTempCount.append(" and SCHEME_NAME like '%"+schemeName+"%'");
	        }
			if(!narration.equalsIgnoreCase("")){
	        	bufInsSql.append(" and NARRATION like '%"+narration+"%'"); 
	        	bufInsSqlTempCount.append(" and NARRATION like '%"+narration+"%'");
	         }
			
			
			//bufInsSql.append(" ORDER BY SCHEME_NAME");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

	       /*if ((schemeName.trim() == null && activityCode.trim() == null ) 
	    		|| (schemeName.trim().equalsIgnoreCase("") && activityCode.trim().equalsIgnoreCase("") )
			    || vo.getCurrentPageLink() > 1) {*/

				logger.info("current PAge Link no .................... "+ vo.getCurrentPageLink());
				if (vo.getCurrentPageLink() > 1) {
					startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
				}


				           bufInsSql.append(" ORDER BY SCHEME_NAME OFFSET ");
							bufInsSql.append(startRecordIndex);
							bufInsSql.append(" ROWS FETCH next ");
							bufInsSql.append(endRecordIndex);
							bufInsSql.append(" ROWS ONLY ");
			
			//}
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl searchScemeMasterData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchScemeMasterData:- " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					bpMasterVO=new BPMasterVO();
					schemeVO=new SchemeVO();
					schemeVO.setSchemeName("<a href='schemeMasterDispatch.do?method=openEditScheme&schemeName="
							+ CommonFunction.checkNull(data.get(0)).toString()+ "'>"
							+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					schemeVO.setNarration(CommonFunction.checkNull(data.get(1)).toString());			
					schemeVO.setEffectiveDate(CommonFunction.checkNull(data.get(2)).toString());
					schemeVO.setSchemeParameter(CommonFunction.checkNull(data.get(3)).toString());
					schemeVO.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
					schemeVO.setTotalRecordSize(count);
					detailList.add(schemeVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			schemeName = null;
			activityCode = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}
	@Override
	public ArrayList<SchemeVO> openEditSchemeM(SchemeVO vo) {
		String schemeName = "";
		String activityCode = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		BPMasterVO bpMasterVO =null;
		SchemeVO schemeVO=null;
		ArrayList<SchemeVO> detailList = new ArrayList<SchemeVO>();
		try {
			logger.info("In PayoutMasterDaoImpl openEditSchemeM()......");
			
			bufInsSql.append("Select SCHEME_NAME,NARRATION," );
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(EFFECTIVE_DATE,'%d-%m-%Y'),SCHEME_PARAMETER,");
			bufInsSql.append(" SERVICE_TAX_APPLICABLE,TDS_APPLICABLE,TAT,CITY_ID,");
			bufInsSql.append(" (SELECT DISTRICT_DESC from com_district_m where DISTRICT_ID=CITY_ID) CITY_DESC,");
			bufInsSql.append(" CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS,SLAB_ON from pay_scheme_m a  where SCHEME_NAME ='"+vo.getSchemeName()+"'");	
			

			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl openEditSchemeM() query1 ### "+ bufInsSql.toString());
			logger.info("openEditSchemeM:- " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					bpMasterVO=new BPMasterVO();
					schemeVO=new SchemeVO();
					schemeVO.setSchemeName(CommonFunction.checkNull(data.get(0)).toString());
					schemeVO.setNarration(CommonFunction.checkNull(data.get(1)).toString());			
					schemeVO.setEffectiveDate(CommonFunction.checkNull(data.get(2)).toString());
					schemeVO.setSchemeParameter(CommonFunction.checkNull(data.get(3)).toString());
					schemeVO.setServiceTaxApp(CommonFunction.checkNull(data.get(4)).toString());
					schemeVO.setTdsApp(CommonFunction.checkNull(data.get(5)).toString());
					schemeVO.setTat(CommonFunction.checkNull(data.get(6)).toString());
					schemeVO.setDistrictId(CommonFunction.checkNull(data.get(7)).toString());
					schemeVO.setCpDistCode(CommonFunction.checkNull(data.get(8)).toString());
					schemeVO.setRecStatus(CommonFunction.checkNull(data.get(9)).toString());
					if(CommonFunction.checkNull(data.get(3)).equalsIgnoreCase("CW")||CommonFunction.checkNull(data.get(3)).equalsIgnoreCase("PW")){
						schemeVO.setSlabOn(null);
					}else{
					schemeVO.setSlabOn(CommonFunction.checkNull(data.get(10)).toString());
					}
					detailList.add(schemeVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			schemeName = null;
			activityCode = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}
		return detailList;
	}
	
	@Override
	public ArrayList<SchemeVO> openEditSchemeDtl(SchemeVO vo) {
		String schemeName = "";
		String activityCode = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		BPMasterVO bpMasterVO =null;
		SchemeVO schemeVO=null;
		ArrayList<SchemeVO> detailList = new ArrayList<SchemeVO>();
		try {
			logger.info("In PayoutMasterDaoImpl openEditSchemeDtl()......");
			
			bufInsSql.append("Select COMMI_PER_CASE_R,COMMI_PER_CASE_P,FROM_CASE,");
			bufInsSql.append(" TO_CASE,COMMI_PER_SLAB_R,COMMI_PER_SLAB_P  ");
			bufInsSql.append(" from pay_scheme_dtl where SCHEME_NAME='"+vo.getSchemeName()+"'");
				
			

			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl openEditSchemeDtl() query ### "+ bufInsSql.toString());
			logger.info("openEditSchemeDtl:- " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					bpMasterVO=new BPMasterVO();
					schemeVO=new SchemeVO();
					if(CommonFunction.checkNull(data.get(0)).toString().equalsIgnoreCase("")){
						schemeVO.setCommissionPerCaseR("0.0");	
					}else{
						Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(0))).trim());
						schemeVO.setCommissionPerCaseR(myFormatter.format(percentage));	
					}
					if(CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("")){
						schemeVO.setCommissionPerCaseP("0.0");	
					}else{
						Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1))).trim());
						schemeVO.setCommissionPerCaseP(myFormatter.format(percentage));	
					}
					if(CommonFunction.checkNull(data.get(2)).toString().equalsIgnoreCase("")){
						schemeVO.setCaseFromStr("0.0");	
					}else{
						Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2))).trim());
						schemeVO.setCaseFromStr(myFormatter.format(percentage));	
						//schemeVO.setCaseFromStr(CommonFunction.checkNull(data.get(2)).toString());
					}		
					if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("")){
						schemeVO.setCaseToStr("0.0");	
					}else{
						Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
						schemeVO.setCaseToStr(myFormatter.format(percentage));	
						//schemeVO.setCaseToStr(CommonFunction.checkNull(data.get(3)).toString());
					}
				
					
					if(CommonFunction.checkNull(data.get(4)).toString().equalsIgnoreCase("")){
						schemeVO.setCommissionRStr("0.0");	
					}else{
						Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
						schemeVO.setCommissionRStr(myFormatter.format(percentage));	
					}
					if(CommonFunction.checkNull(data.get(5)).toString().equalsIgnoreCase("")){
						schemeVO.setCommissionPStr("0.0");	
					}else{
						Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
						schemeVO.setCommissionPStr(myFormatter.format(percentage));	
					}
					
					detailList.add(schemeVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			schemeName = null;
			activityCode = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}
		return detailList;
	}
	
	
	@Override
	public String updateSchemeMaster(SchemeVO vo) {
		boolean status = false;
		logger.info("In updateSchemeMaster..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String taxApp = "X";
		String tdsApp = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
		String num="";
	 try {
				  
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
			if (vo.getServiceTaxApp()!= null && vo.getServiceTaxApp().equals("on")) {
				taxApp = "A";
			} else {
				taxApp = "X";

				}
			if (vo.getTdsApp()!= null && vo.getTdsApp().equals("on")) {
				tdsApp = "A";
			} else {
				tdsApp = "X";

				}
		
				bufInsSql.append("Update pay_scheme_m SET NARRATION=?,EFFECTIVE_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),SCHEME_PARAMETER=?,SLAB_ON=?,");				
				bufInsSql.append(" SERVICE_TAX_APPLICABLE=?,TDS_APPLICABLE=?,TAT=?,CITY_ID=?,REC_STATUS=?,");
				bufInsSql.append(" MAKER_ID=?,MAKER_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),AUTHOR_ID=?,AUTHOR_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");//SCHEME_NAME
				bufInsSql.append(" where SCHEME_NAME=?");
				

				if (CommonFunction.checkNull(vo.getNarration()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getNarration());
				
				if (CommonFunction.checkNull(vo.getEffectiveDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEffectiveDate());
				
				if (CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSchemeParameter());
				
				if (CommonFunction.checkNull(vo.getSlabOn()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSlabOn());
				
				
				if (CommonFunction.checkNull(taxApp).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(taxApp);
				
				if (CommonFunction.checkNull(tdsApp).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(tdsApp);
				
				if (CommonFunction.checkNull(vo.getTat()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTat());
				
				if (CommonFunction.checkNull(vo.getCpDistCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getCpDistCode());
			
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
				
				if (CommonFunction.checkNull(vo.getSchemeName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSchemeName());
				
				    insertPrepStmtObject.setSql(bufInsSql.toString());
				    
				logger.info("IN updateSchemeMaster() Update query1 ### "	+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				bufInsSql=null;
				bufInsSql=new StringBuilder();
				insertPrepStmtObject=null;
				insertPrepStmtObject=new PrepStmtObject();
				
				bufInsSql.append("DELETE from pay_scheme_dtl where SCHEME_NAME='"+vo.getSchemeName()+"'");
			    insertPrepStmtObject.setSql(bufInsSql.toString());
			    logger.info("IN updateSchemeMaster() Delete query1 ### "	+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				bufInsSql=null;
				bufInsSql=new StringBuilder();
				insertPrepStmtObject=null;
				insertPrepStmtObject=new PrepStmtObject();
				
				bufInsSql.append("insert into pay_scheme_dtl(SCHEME_NAME,COMMI_PER_CASE_R,COMMI_PER_CASE_P,FROM_CASE,TO_CASE,COMMI_PER_SLAB_R,COMMI_PER_SLAB_P,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append("values(");
				bufInsSql.append(" ?,");//SCHEME_NAME
				bufInsSql.append(" ?,");//COMMI_PER_CASE_R
				bufInsSql.append(" ?,");//COMMI_PER_CASE_P
				bufInsSql.append(" ?,");//FROM_CASE
				bufInsSql.append(" ?,");//TO_CASE
				bufInsSql.append(" ?,");//COMMI_PER_SLAB_R
				bufInsSql.append(" ?,");//COMMI_PER_SLAB_P
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )");//AUTHOR_DATE
				
				String[] fromCase=vo.getCaseFrom();
				String[] toCase=vo.getCaseTo();
				String[] fromCaseP=vo.getCaseFromP();
				String[] toCaseP=vo.getCaseToP();
				String[] fromCaseA=vo.getCaseFromA();
				String[] toCaseA=vo.getCaseToA();
				String[] commissionR=vo.getCommissionR();
				String[] commissionP=vo.getCommissionP();
				String[] commissionA=vo.getCommissionA();
				int size=0;
				logger.info("vo.getSchemeParameter():-------"+vo.getSchemeParameter());
				if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")){
					size=commissionR.length;
				}else if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SPW")){
					size=commissionP.length;
				}else if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
					size=commissionA.length;
				}else{
					size=0;
				}
				//logger.info("fromCase.length:-----------"+fromCase.length);
				//logger.info("toCase.length:-----------"+toCase.length);
				logger.info("commissionR.length:-----------"+commissionR.length);
				logger.info("commissionP.length:-----------"+commissionP.length);
				logger.info("commissionA.length:-----------"+commissionA.length);
				if(CommonFunction.checkNull(vo.getCommissionPerCaseR()).equalsIgnoreCase("")&& 
						CommonFunction.checkNull(vo.getCommissionPerCaseP()).equalsIgnoreCase("")){
				for(int i=0;i<size;i++){
					
					insertPrepStmtObject=null;
					insertPrepStmtObject=new PrepStmtObject();
					
					if (CommonFunction.checkNull(vo.getSchemeName()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getSchemeName());
					
					if (CommonFunction.checkNull(vo.getCommissionPerCaseR()).equalsIgnoreCase("")){
						insertPrepStmtObject.addNull();
					}else{
						String numCommissionPerCase=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionPerCaseR().trim())).toString();
					     insertPrepStmtObject.addString(numCommissionPerCase);
					}
					
					
					if (CommonFunction.checkNull(vo.getCommissionPerCaseP()).equalsIgnoreCase("")){
						insertPrepStmtObject.addNull();
					}else{
						String numCommissionPerCaseP=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionPerCaseR().trim())).toString();
					    insertPrepStmtObject.addString(numCommissionPerCaseP);
						
					}
					if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")){
						if (CommonFunction.checkNull(fromCase[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addString("0.0");
						}else{
							String fromCaseI=myFormatter.parse(StringEscapeUtils.escapeSql(fromCase[i].trim())).toString();
						    insertPrepStmtObject.addString(fromCaseI);
						 }
					}else if (CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
						if (CommonFunction.checkNull(fromCaseA[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addString("0.0");
						}else{
							String fromCaseI=myFormatter.parse(StringEscapeUtils.escapeSql(fromCaseA[i].trim())).toString();
						    insertPrepStmtObject.addString(fromCaseI);
						 }
					}else{
						if (CommonFunction.checkNull(fromCaseP[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String fromCasePI=myFormatter.parse(StringEscapeUtils.escapeSql(fromCaseP[i].trim())).toString();
						    insertPrepStmtObject.addString(fromCasePI);
							
						}
					}
					if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")){
						if (CommonFunction.checkNull(toCase[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String toCaseI=myFormatter.parse(StringEscapeUtils.escapeSql(toCase[i].trim())).toString();
						    insertPrepStmtObject.addString(toCaseI);
								
						}
					}else if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
						if (CommonFunction.checkNull(toCaseA[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String toCaseI=myFormatter.parse(StringEscapeUtils.escapeSql(toCaseA[i].trim())).toString();
						    insertPrepStmtObject.addString(toCaseI);
						}
					}else{
						if (CommonFunction.checkNull(toCaseP[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String toCasePI=myFormatter.parse(StringEscapeUtils.escapeSql(toCaseP[i].trim())).toString();
						    insertPrepStmtObject.addString(toCasePI);
							//insertPrepStmtObject.addString(toCaseP[i]);	
						}
						
					}
					
					
					if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")||CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
						if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SFA")){
							if (CommonFunction.checkNull(commissionA[i]).equalsIgnoreCase("")){
								insertPrepStmtObject.addNull();
							}else{
								String commissionPI=myFormatter.parse(StringEscapeUtils.escapeSql(commissionA[i].trim())).toString();
							    insertPrepStmtObject.addString(commissionPI);
							
							}
						}else if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SCW")){
							if (CommonFunction.checkNull(commissionR[i]).equalsIgnoreCase("")){
								insertPrepStmtObject.addNull();
							}else{
								String commissionRI=myFormatter.parse(StringEscapeUtils.escapeSql(commissionR[i].trim())).toString();
							    insertPrepStmtObject.addString(commissionRI);
							}
						}
					}else{
						insertPrepStmtObject.addNull();	
					}		
					
					if(CommonFunction.checkNull(vo.getSchemeParameter()).equalsIgnoreCase("SPW")){
						if (CommonFunction.checkNull(commissionP[i]).equalsIgnoreCase("")){
							insertPrepStmtObject.addNull();
						}else{
							String commissionPI=myFormatter.parse(StringEscapeUtils.escapeSql(commissionP[i].trim())).toString();
						    insertPrepStmtObject.addString(commissionPI);
						
						}
					}else{
						insertPrepStmtObject.addNull();	
					}
					
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
					    
					logger.info("IN updateSchemeMaster() insert in pay_scheme_dtl for slab ### "	+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					
				}	
				}else{
					if (CommonFunction.checkNull(vo.getSchemeName()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getSchemeName());
					
					if (CommonFunction.checkNull(vo.getCommissionPerCaseR()).equalsIgnoreCase("")){
						insertPrepStmtObject.addNull();
					}else{
						String commissionRI=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionPerCaseR().trim())).toString();
					    insertPrepStmtObject.addString(commissionRI);
						//insertPrepStmtObject.addString(vo.getCommissionPerCaseR());
					}
					
					if (CommonFunction.checkNull(vo.getCommissionPerCaseP()).equalsIgnoreCase("")){
						insertPrepStmtObject.addNull();
					}else{
						String commissionRI=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionPerCaseP().trim())).toString();
					    insertPrepStmtObject.addString(commissionRI);
						//insertPrepStmtObject.addString(vo.getCommissionPerCaseP());
						
					}
					insertPrepStmtObject.addNull();
					insertPrepStmtObject.addNull();
					insertPrepStmtObject.addNull();
					insertPrepStmtObject.addNull();
					
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
					    
					logger.info("IN updateSchemeMaster() insert pay_scheme_dtl for Non Slab ### "	+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
				
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In updateSchemeMaster......................"+ status);
				
				if(status){
					resultStr="Saved";
				}else{
					resultStr="notSaved";
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
			
		}
		return resultStr;


	}
	/*
	 * Code For Scheme Master Ends Here By Arun  
	 * */

	/*
	 * Code For Scheme BP Map Starts Here By Arun  
	 * */
	@Override
	public ArrayList<SchemeBpMapVO> searchScemeBpMapData(SchemeBpMapVO vo) {
		String bpName = "";
		String activityCode = "";
		String schemeName = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		
		SchemeBpMapVO schemeBpVO=null;
		ArrayList<SchemeBpMapVO> detailList = new ArrayList<SchemeBpMapVO>();
		try {
			logger.info("In PayoutMasterDaoImpl searchScemeBpMapData()......");
			bpName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchBpName())).trim());
			schemeName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchSchemeName())).trim());
			
			bufInsSql.append(" Select a.SCHEME_BP_MAP_ID,b.BP_NAME,a.SCHEME_NAME,d.NARRATION,c.ACTIVITY_DESC,CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS");
			bufInsSql.append(" from pay_scheme_bp_map a join pay_bp_m b on a.BP_ID=b.BP_ID");
			bufInsSql.append(" join pay_activity_m c on a.ACTIVITY_CODE=c.ACTIVITY_CODE join pay_scheme_m d on  a.SCHEME_NAME=d.SCHEME_NAME  where 'a'='a'");
			
			bufInsSqlTempCount.append("Select count(1) from  ( ");
			bufInsSqlTempCount.append("Select a.SCHEME_BP_MAP_ID,b.BP_NAME,c.ACTIVITY_DESC,a.SCHEME_NAME,d.NARRATION,a.REC_STATUS ");
			bufInsSqlTempCount.append(" from pay_scheme_bp_map a join pay_bp_m b on a.BP_ID=b.BP_ID");
			bufInsSqlTempCount.append(" join pay_activity_m c on a.ACTIVITY_CODE=c.ACTIVITY_CODE join pay_scheme_m d on  a.SCHEME_NAME=d.SCHEME_NAME  where 'a'='a'");
			
			if(!bpName.equalsIgnoreCase("")){
	        	bufInsSql.append(" and b.BP_NAME like '%"+bpName+"%'"); 
	        	bufInsSqlTempCount.append(" and b.BP_NAME like '%"+bpName+"%'");
	        }
			if(!schemeName.equalsIgnoreCase("")){
	        	bufInsSql.append(" and a.SCHEME_NAME like '%"+schemeName+"%'"); 
	        	bufInsSqlTempCount.append(" and a.SCHEME_NAME like '%"+schemeName+"%'");
	         }
			
			
			//bufInsSql.append(" ORDER BY b.BP_NAME");
			bufInsSqlTempCount.append(") Tab");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

	      /* if ((bpName.trim() == null && activityCode.trim() == null ) 
	    		|| (bpName.trim().equalsIgnoreCase("") && activityCode.trim().equalsIgnoreCase("") )
			    || vo.getCurrentPageLink() > 1) {*/

				logger.info("current PAge Link no .................... "+ vo.getCurrentPageLink());
				if (vo.getCurrentPageLink() > 1) {
					startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
				}

				    bufInsSql.append(" ORDER BY b.BP_NAME OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
			
		//	}
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl searchScemeBpMapData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchScemeBpMapData:- " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					schemeBpVO=new SchemeBpMapVO();
					
					schemeBpVO.setBpName("<a href=schemeBpMapDispatch.do?method=openEditSchemeBpMap&mapId="
							+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
							
					schemeBpVO.setSchemeName(CommonFunction.checkNull(data.get(2)).toString());
					schemeBpVO.setSchemeDesc(CommonFunction.checkNull(data.get(3)).toString());
					schemeBpVO.setActivityCode(CommonFunction.checkNull(data.get(4)).toString());	
					schemeBpVO.setRecStatus(CommonFunction.checkNull(data.get(5)).toString());
					schemeBpVO.setTotalRecordSize(count);
					detailList.add(schemeBpVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			bpName = null;
			activityCode = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}
	
	@Override
	public ArrayList<SchemeBpMapVO> openEditSchemeBPMap(SchemeBpMapVO vo) {
		String bpName = "";
		String activityCode = "";
		String narration = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		
		SchemeBpMapVO schemeBpVO=null;
		ArrayList<SchemeBpMapVO> detailList = new ArrayList<SchemeBpMapVO>();
		try {
			logger.info("In PayoutMasterDaoImpl openEditSchemeBPMap()......");
			bpName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchBpName())).trim());
			activityCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxActivityCode())).trim());
			
			bufInsSql.append(" Select a.SCHEME_BP_MAP_ID,a.BP_ID,b.BP_NAME,a.SCHEME_NAME,a.ACTIVITY_CODE,c.ACTIVITY_DESC,A.SPECIFIC_TIME,");
			bufInsSql.append(" A.TARGET,A.TARGET_COMMISION,A.PRIORITY,CASE WHEN a.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS,SPECIFIC_TARGET,SPECIFIC_TARGET_ON ");
			bufInsSql.append(" from pay_scheme_bp_map a join pay_bp_m b on a.BP_ID=b.BP_ID ");
			bufInsSql.append(" join pay_activity_m c on a.ACTIVITY_CODE=c.ACTIVITY_CODE join pay_scheme_m d on  a.SCHEME_NAME=d.SCHEME_NAME  ");
			bufInsSql.append(" WHERE A.SCHEME_BP_MAP_ID='"+CommonFunction.checkNull(vo.getMapId())+"'");
			
			
			logger.info("query :- " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl openEditSchemeBPMap()  query ### "+ bufInsSql.toString());
			logger.info("openEditSchemeBPMap searchlist.size():- " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					schemeBpVO=new SchemeBpMapVO();
					schemeBpVO.setMapId(CommonFunction.checkNull(data.get(0)).toString());
					schemeBpVO.setLbxBpId(CommonFunction.checkNull(data.get(1)).toString());
					schemeBpVO.setBpName(CommonFunction.checkNull(data.get(2)).toString());
					schemeBpVO.setSchemeName(CommonFunction.checkNull(data.get(3)).toString());
					schemeBpVO.setLbxActivityCode(CommonFunction.checkNull(data.get(4)).toString());
					schemeBpVO.setActivityCode(CommonFunction.checkNull(data.get(5)).toString());	
					schemeBpVO.setSpecificTime(CommonFunction.checkNull(data.get(6)).toString());
					schemeBpVO.setTargetForSt(CommonFunction.checkNull(data.get(7)).toString());
					
					if(CommonFunction.checkNull(data.get(8)).toString().equalsIgnoreCase("")){
						schemeBpVO.setCommissionForTarget("0.0");	
					}else{
						Number percentage =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(8))).trim());
						schemeBpVO.setCommissionForTarget(myFormatter.format(percentage));	
					}
					schemeBpVO.setPriority(CommonFunction.checkNull(data.get(9)).toString());
					schemeBpVO.setRecStatus(CommonFunction.checkNull(data.get(10)).toString());
					schemeBpVO.setSpecificTar(CommonFunction.checkNull(data.get(11)).toString());
					schemeBpVO.setSpecificTarOn(CommonFunction.checkNull(data.get(12)).toString());
					detailList.add(schemeBpVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			bpName = null;
			activityCode = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}

	@Override
	public String saveSchemeBPMap(SchemeBpMapVO vo) {
		boolean status = false;
		logger.info("In saveSchemeBPMap..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
		String num="";
		String specificTar="N";
	 try {
		  String priorityQry="Select PRIORITY from pay_scheme_bp_map where  BP_ID='"+CommonFunction.checkNull(vo.getLbxBpId())+"' and PRIORITY='"+vo.getPriority()+"'";
		   logger.info("In saveSchemeBPMap...................Dao Impl priorityQry :-" + priorityQry);
		   boolean stPriority = ConnectionDAOforEJB.checkStatus(priorityQry);
		   if(!stPriority){
			   		   
		   String query = "select SCHEME_BP_MAP_ID from pay_scheme_bp_map where BP_ID='"+CommonFunction.checkNull(vo.getLbxBpId())+"' and ACTIVITY_CODE='"+CommonFunction.checkNull(vo.getLbxActivityCode())+"' and SCHEME_NAME ='"+CommonFunction.checkNull(vo.getLbxSchemeName())+"'";
			logger.info("In saveSchemeBPMap.....................................Dao Impl" + query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			query=null;
		if (!st) {
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
			if (vo.getSpecificTar()!= null && vo.getSpecificTar().equals("on")) {
				specificTar = "Y";
			} else {
				specificTar = "N";

				}
		logger.info("specificTar:---"+specificTar);
		logger.info("vo.getSpecificTarOn():---"+vo.getSpecificTarOn());
		
				bufInsSql.append("insert into pay_scheme_bp_map ( BP_ID,ACTIVITY_CODE,SCHEME_NAME,SPECIFIC_TARGET,SPECIFIC_TIME,SPECIFIC_TARGET_ON,TARGET,TARGET_COMMISION,PRIORITY,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//BP_ID
				bufInsSql.append(" ?,");//ACTIVITY_CODE
				bufInsSql.append(" ?,");//SCHEME_NAME
				bufInsSql.append(" ?,");//SPECIFIC_TARGET
				bufInsSql.append(" ?,");//SPECIFIC_TIME
				bufInsSql.append(" ?,");//SPECIFIC_TARGET_ON
				bufInsSql.append(" ?,");//TARGET
				bufInsSql.append(" ?,");//TARGET_COMMISION 
				bufInsSql.append(" ?,");//PRIORITY
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )");//AUTHOR_DATE
				
				if (CommonFunction.checkNull(vo.getLbxBpId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBpId().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getLbxActivityCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxActivityCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getLbxSchemeName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxSchemeName().trim());
				
				if (CommonFunction.checkNull(specificTar).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(specificTar);
                
				if (CommonFunction.checkNull(vo.getSpecificTime()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSpecificTime().trim());
				
				if (CommonFunction.checkNull(vo.getSpecificTarOn()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSpecificTarOn().trim());

				if (CommonFunction.checkNull(vo.getTargetForSt()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTargetForSt().trim());

								
				if (CommonFunction.checkNull(vo.getCommissionForTarget()).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionForTarget().trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				if (CommonFunction.checkNull(vo.getPriority()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPriority().trim());


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
				    
				logger.info("IN saveSchemeBPMap() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveSchemeBPMap......................"+ status);
				if(status){
					resultStr="Saved";
				}else{
					resultStr="notSaved";
				}
			} else {
				resultStr="Already";
			}
		   }else{
			   resultStr="priority";
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
			
		}
		return resultStr;
	}
	
	@Override
	public String updateSchemeBPMap(SchemeBpMapVO vo) {
		boolean status = false;
		logger.info("In updateSchemeBPMap..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
		String num="";
		String specificTar="N";
	 try {
		  String priorityQry="Select PRIORITY from pay_scheme_bp_map where  BP_ID='"+CommonFunction.checkNull(vo.getLbxBpId())+"' and PRIORITY='"+vo.getPriority()+"' and SCHEME_BP_MAP_ID <> '"+vo.getMapId()+"'";
		   logger.info("In updateSchemeBPMap...................Dao Impl priorityQry :-" + priorityQry);
		   boolean stPriority = ConnectionDAOforEJB.checkStatus(priorityQry);
		   if(!stPriority){
			   		   
		   String query = "select SCHEME_BP_MAP_ID from pay_scheme_bp_map where BP_ID='"+CommonFunction.checkNull(vo.getLbxBpId())+"' and ACTIVITY_CODE='"+CommonFunction.checkNull(vo.getLbxActivityCode())+"' and SCHEME_NAME ='"+CommonFunction.checkNull(vo.getLbxSchemeName())+"' and SCHEME_BP_MAP_ID <> '"+vo.getMapId()+"'";
			logger.info("In updateSchemeBPMap.....................................Dao Impl" + query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			query=null;
		if (!st) {
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
			if (vo.getSpecificTar()!= null && vo.getSpecificTar().equals("on")) {
				specificTar = "Y";
			} else {
				specificTar = "N";

				}
				bufInsSql.append("Update pay_scheme_bp_map set BP_ID=?,ACTIVITY_CODE=?,SCHEME_NAME=?,SPECIFIC_TIME=?,TARGET=?,TARGET_COMMISION=?,");				
				bufInsSql.append(" PRIORITY=?,REC_STATUS=?,SPECIFIC_TARGET=?,SPECIFIC_TARGET_ON=?,MAKER_ID=?, ");
				bufInsSql.append(" MAKER_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" AUTHOR_ID=?,");
				bufInsSql.append(" AUTHOR_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				bufInsSql.append(" where SCHEME_BP_MAP_ID='"+vo.getMapId()+"'");
				
				if (CommonFunction.checkNull(vo.getLbxBpId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBpId().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getLbxActivityCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxActivityCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getLbxSchemeName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxSchemeName().trim());
                
				if (CommonFunction.checkNull(vo.getSpecificTime()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSpecificTime().trim());

				if (CommonFunction.checkNull(vo.getTargetForSt()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getTargetForSt().trim());

				if (CommonFunction.checkNull(vo.getCommissionForTarget()).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getCommissionForTarget().trim())).toString();
					insertPrepStmtObject.addString(num);
				}

				if (CommonFunction.checkNull(vo.getPriority()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPriority().trim());


				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(specificTar).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(specificTar);
				
				if (CommonFunction.checkNull(vo.getSpecificTarOn()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getSpecificTarOn().trim());
				
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
				    
				logger.info("IN updateSchemeBPMap() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In updateSchemeBPMap......................"+ status);
				if(status){
					resultStr="Saved";
				}else{
					resultStr="notSaved";
				}
			} else {
				resultStr="Already";
			}
		   }else{
			   resultStr="priority";
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
			
		}
		return resultStr;
	}
	/*
	 * Code For Scheme BP MAP Ends Here By Arun  
	 * */
	/*
	 * Code For Payment Receipt Starts Here By Arun  
	 * */
	@Override
	public ArrayList<PaymentReceiptVO> searchPaymentReceiptMaker(
			PaymentReceiptVO vo) {
		
		String bpName = "";
		String paymentType = "";
		String amount = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		PaymentReceiptVO paymentReceiptVo =null;
		
		ArrayList<PaymentReceiptVO> detailList = new ArrayList<PaymentReceiptVO>();
		try {
			logger.info("In PayoutMasterDaoImpl searchPaymentReceiptMaker()......");
			bpName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchBpName())).trim());
			paymentType = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchPaymentType())).trim());
			amount=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchPaymentType())).trim());

			bufInsSql.append("Select a.PAYMENT_ID,b.BP_NAME,case a.PAYMENT_TYPE when 'P' then 'Payment' when 'R' then 'Receipt' when 'W' then 'Weive Off' end PaymentType,");
			bufInsSql.append("case a.PAYMENT_MODE  When  'C'  Then  'Cash' When  'Q'  Then  'Cheque' When  'D'  Then  'DD' When  'N'  Then  'NEFT' When  'R'  Then  'RTGS' When  'S'  Then  'ADJUSTMENT' end PaymentMode,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(a.PAYMENT_DATE,'"+dateFormat+"'),a.AMOUNT,a.remark ");
			bufInsSql.append(" from pay_receipt_payment_dtl a  join  pay_bp_m b on a.BP_ID=b.BP_ID  where a.REC_STATUS ='P' and a.MAKER_ID='"+vo.getMakerId()+"'");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM (");
			bufInsSqlTempCount.append("Select a.PAYMENT_ID,b.BP_NAME,a.PAYMENT_TYPE,a.PAYMENT_MODE," );
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("DATE_FORMAT(a.PAYMENT_DATE,'"+dateFormat+"') AS PAYMENT_DATE,a.AMOUNT,a.remark");
			bufInsSqlTempCount.append(" from pay_receipt_payment_dtl a  join  pay_bp_m b on a.BP_ID=b.BP_ID  where a.REC_STATUS ='P' and a.MAKER_ID='"+vo.getMakerId()+"'");
	      
			if(!bpName.equalsIgnoreCase("")){
	        	bufInsSql.append(" and b.BP_NAME like '%"+bpName+"%'"); 
	        	bufInsSqlTempCount.append(" and b.BP_NAME like '%"+bpName+"%'");
	         }
	        if(!paymentType.equalsIgnoreCase("")){
	         	bufInsSql.append(" and a.PAYMENT_TYPE='"+paymentType+"'"); 
	        	bufInsSqlTempCount.append(" and a.PAYMENT_TYPE='"+paymentType+"'");
	        	
	        }
			
			//bufInsSql.append(" ORDER BY b.BP_NAME");
			bufInsSqlTempCount.append(")m ");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

	      /* if ((bpName.trim() == null && paymentType.trim() == null) || (bpName.trim().equalsIgnoreCase("") && paymentType.trim().equalsIgnoreCase(""))
					|| vo.getCurrentPageLink() > 1) {*/

				logger.info("current PAge Link no .................... "+ vo.getCurrentPageLink());
				if (vo.getCurrentPageLink() > 1) {
					startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
				}

				bufInsSql.append(" ORDER BY b.BP_NAME OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
			
			//}
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl searchPaymentReceiptMaker() search query1 ### "+ bufInsSql.toString());
			logger.info("searchActivityData " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					paymentReceiptVo=new PaymentReceiptVO();
					paymentReceiptVo.setPaymentId("<a href=paymentReceiptDispatch.do?method=openEditPaymentReceipt&paymentId="
							+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
					//paymentReceiptVo.setBpName(CommonFunction.checkNull(data.get(0)).toString());
					
					paymentReceiptVo.setPaymentType(CommonFunction.checkNull(data.get(2)).toString());
					paymentReceiptVo.setPaymentMode(CommonFunction.checkNull(data.get(3)).toString());
					paymentReceiptVo.setPaymentDate(CommonFunction.checkNull(data.get(4)).toString());
					paymentReceiptVo.setPaymentAmount(CommonFunction.checkNull(data.get(5)).toString());
					if(CommonFunction.checkNull(data.get(5)).toString().equalsIgnoreCase("")){
						paymentReceiptVo.setPaymentAmount("0.0");	
					}else{
						Number payAmount =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
						paymentReceiptVo.setPaymentAmount(myFormatter.format(payAmount));
					}
					paymentReceiptVo.setMakerRemark(CommonFunction.checkNull(data.get(6)).toString());
					paymentReceiptVo.setTotalRecordSize(count);
					detailList.add(paymentReceiptVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			bpName = null;
			paymentType = null;
			amount = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}


	@Override
	public String savePaymentReceiptMaker(PaymentReceiptVO vo) {
		boolean status = false;
		logger.info("In savePaymentReceiptMaker..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
		String num="";
	 try {
		 
			
		
				bufInsSql.append("insert into  pay_receipt_payment_dtl (BP_ID,PAYMENT_TYPE,PAYMENT_MODE,INSTRUMENT_NO,INSTRUMENT_DATE,PAYMENT_DATE,AMOUNT,REMARK,REC_STATUS,MAKER_ID,MAKER_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//BP_ID
				bufInsSql.append(" ?,");//PAYMENT_TYPE
				bufInsSql.append(" ?,");//PAYMENT_MODE
				bufInsSql.append(" ?,");//INSTRUMENT_NO 
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//INSTRUMENT_DATE
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//PAYMENT_DATE 
				bufInsSql.append(" ?,");//AMOUNT
				bufInsSql.append(" ?,");//Remark
				bufInsSql.append(" 'P',");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//MAKER_DATE

				
				if (CommonFunction.checkNull(vo.getLbxBpId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBpId().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getPaymentType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPaymentType().trim());
                
				if (CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPaymentMode().trim());
				
				
				if (CommonFunction.checkNull(vo.getInstrumentNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getInstrumentNo().trim());

				if (CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getInstrumentDate().trim());

												
				if (CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPaymentDate().trim());
				
				if (CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getPaymentAmount().trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				if (CommonFunction.checkNull(vo.getMakerRemark()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerRemark().trim());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				    
				logger.info("IN savePaymentReceiptMaker() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In savePaymentReceiptMaker......................"+ status);
				if(status){
					
					String priorityQry="Select MAX(PAYMENT_ID) from pay_receipt_payment_dtl";
					logger.info("In savePaymentReceiptMaker...................Dao Impl  :-" + priorityQry);
					resultStr = ConnectionDAOforEJB.singleReturn(priorityQry);
				}
				status=false;
				qryList=new ArrayList();
				String allocateStr=vo.getAllocateAmount();
				String tdsStr=vo.getTds();
				String totalAllocateStr=vo.getTotalAllocatedAmount();
				logger.info("allocateStr:-"+allocateStr);
				logger.info("tdsStr:-"+tdsStr);
				logger.info("totalAllocateStr:-"+totalAllocateStr);
				String allocateArr[]=allocateStr.split(":");
				String tdsArr[]=tdsStr.split(":");
				String totalAllocateArr[]=totalAllocateStr.split(":");
				
				bufInsSql=null;
				bufInsSql= new StringBuilder();
				
				bufInsSql.append("insert into pay_txn_pay_dtl (TRANSACTION_CASE_ID,PAYMENT_ID,TOTAL_ALLOCATED_AMOUNT,ALLOCATED_AMOUNT,TDS,REC_STATUS,MAKER_ID,MAKER_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//TRANSACTION_CASE_ID
				bufInsSql.append(" ?,");//PAYMENT_ID
				bufInsSql.append(" ?,");//TOTAL_ALLOCATED_AMOUNT
				bufInsSql.append(" ?,");//ALLOCATED_AMOUNT
				bufInsSql.append(" ?,");//TDS
				bufInsSql.append(" 'P',");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//MAKER_DATE

			for(int i=0;i<totalAllocateArr.length;i++){
				insertPrepStmtObject=null;
			    insertPrepStmtObject = new PrepStmtObject();
			    String allocateAm[]=allocateArr[i].split("@");
			    String tdsAm[]=tdsArr[i].split("@");
			    logger.info("totalAllocateArr[i]:--"+totalAllocateArr[i]);
			    String totalAllocateAm[]=totalAllocateArr[i].toString().split("@");
			    logger.info("totalAllocateArr[0]:--"+totalAllocateAm);
			    logger.info("totalAllocateArr[0]:--"+totalAllocateAm[0]);
			    logger.info("totalAllocateArr[1]:--"+allocateAm[1]);
			    
				if (CommonFunction.checkNull(allocateAm[0]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(allocateAm[0].trim());

				if (CommonFunction.checkNull(resultStr).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(resultStr.trim());
                
				if (CommonFunction.checkNull(totalAllocateAm[1]).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(totalAllocateAm[1].trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				if (CommonFunction.checkNull(allocateAm[1]).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(allocateAm[1].trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				if (CommonFunction.checkNull(tdsAm[1]).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(tdsAm[1].trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				    
				logger.info("IN saveAllocatePaymentReceipt() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);	
								
			}	
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			if(status){
				resultStr=resultStr;
			}else{
				resultStr="notSaved";
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
			
		}
		return resultStr;
	}

	@Override
	public ArrayList<PaymentReceiptVO> openEditPaymentReceiptMaker(
			PaymentReceiptVO vo) {
		String bpName = "";
		String paymentType = "";
		String amount = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList txnDtllist = new ArrayList();
		ArrayList data= new ArrayList();
		ArrayList dataNew= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		PaymentReceiptVO paymentReceiptVo =null;
		String allocateStr="";
		String tdsStr="";
		String totalAllocatedStr="";
		ArrayList<PaymentReceiptVO> detailList = new ArrayList<PaymentReceiptVO>();
		try {
			logger.info("In PayoutMasterDaoImpl openEditPaymentReceiptMaker()......");
			
			bufInsSql.append("Select a.PAYMENT_ID,a.BP_ID,b.BP_NAME,a.PAYMENT_TYPE,a.PAYMENT_MODE,");
			bufInsSql.append(dbo);
		    bufInsSql.append("DATE_FORMAT(a.PAYMENT_DATE,'"+dateFormat+"'),a.AMOUNT,a.INSTRUMENT_NO,");
		    bufInsSql.append(dbo);
		    bufInsSql.append("DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.REMARK");
			bufInsSql.append(" from pay_receipt_payment_dtl a  join  pay_bp_m b on a.BP_ID=b.BP_ID  where  ");
			bufInsSql.append("  PAYMENT_ID = '"+vo.getPaymentId()+"'"); 
	        
			
		
			logger.info("In PayoutMasterDaoImpl openEditPaymentReceiptMaker() edit query1 ### "+ bufInsSql.toString());
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
						
			logger.info("searchActivityData " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					paymentReceiptVo=new PaymentReceiptVO();
					paymentReceiptVo.setPaymentId(CommonFunction.checkNull(data.get(0)).toString());
					paymentReceiptVo.setLbxBpId(CommonFunction.checkNull(data.get(1)).toString());
					paymentReceiptVo.setBpName(CommonFunction.checkNull(data.get(2)).toString());
					paymentReceiptVo.setPaymentType(CommonFunction.checkNull(data.get(3)).toString());
					paymentReceiptVo.setPaymentMode(CommonFunction.checkNull(data.get(4)).toString());
					paymentReceiptVo.setPaymentDate(CommonFunction.checkNull(data.get(5)).toString());
								
					if(CommonFunction.checkNull(data.get(6)).toString().equalsIgnoreCase("")){
						paymentReceiptVo.setPaymentAmount("0.0");	
					}else{
						Number payAmount =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(6))).trim());
						paymentReceiptVo.setPaymentAmount(myFormatter.format(payAmount));
					}
					paymentReceiptVo.setInstrumentNo(CommonFunction.checkNull(data.get(7)).toString());
					paymentReceiptVo.setInstrumentDate(CommonFunction.checkNull(data.get(8)).toString());
					paymentReceiptVo.setMakerRemark(CommonFunction.checkNull(data.get(9)).toString());
					paymentReceiptVo.setTotalRecordSize(count);
					detailList.add(paymentReceiptVo);
				}

			}
			bufInsSql=null;
			bufInsSql = new StringBuilder();
			bufInsSql.append("Select TRANSACTION_CASE_ID,TOTAL_ALLOCATED_AMOUNT,ALLOCATED_AMOUNT,TDS ");
			bufInsSql.append(" from pay_txn_pay_dtl where PAYMENT_ID='"+vo.getPaymentId()+"'");
			
			logger.info("In PayoutMasterDaoImpl openEditPaymentReceiptMaker() edit query1 ### "+ bufInsSql.toString());
			txnDtllist= ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			size = txnDtllist.size();
			
			for (int i = 0; i < size; i++) {
				dataNew = (ArrayList) txnDtllist.get(i);
				if (dataNew.size() > 0) {
					String txnId=CommonFunction.checkNull(dataNew.get(0)).toString();
					String allocateAmount="";
					String tdsAmount="";
					String totalAllocatedAmount="";
					if(CommonFunction.checkNull(dataNew.get(1)).toString().equalsIgnoreCase("")){
						totalAllocatedAmount="0.0";	
					}else{
						Number tot =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(dataNew.get(1))).trim());
						totalAllocatedAmount=myFormatter.format(tot);
					}
					if(CommonFunction.checkNull(dataNew.get(2)).toString().equalsIgnoreCase("")){
						allocateAmount="0.0";	
					}else{
						Number tot =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(dataNew.get(2))).trim());
						allocateAmount=myFormatter.format(tot);
					}
					if(CommonFunction.checkNull(dataNew.get(3)).toString().equalsIgnoreCase("")){
						tdsAmount="0.0";	
					}else{
						Number tot =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(dataNew.get(3))).trim());
						tdsAmount=myFormatter.format(tot);
					}
					allocateStr=allocateStr+txnId+"@"+allocateAmount+":";
					tdsStr=tdsStr+txnId+"@"+tdsAmount+":";
					totalAllocatedStr=totalAllocatedStr+txnId+"@"+totalAllocatedAmount+":";
				}
			}
			paymentReceiptVo.setTotalAllocatedAmount(totalAllocatedStr);
			paymentReceiptVo.setTds(tdsStr);
			paymentReceiptVo.setAllocateAmount(allocateStr);
			
			String qry="Select Count(1) from pay_txn_pay_dtl where PAYMENT_ID = '"+vo.getPaymentId()+"'";
			String resStr=ConnectionDAOforEJB.singleReturn(qry);
			if(!CommonFunction.checkNull(resStr).equalsIgnoreCase("0")){
				paymentReceiptVo.setAllocateFlag("Y");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			bpName = null;
			paymentType = null;
			amount = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}
	@Override
	public String updatePaymentReceiptMaker(PaymentReceiptVO vo,String recStatus) {
		boolean status = false;
		logger.info("In updatePaymentReceiptMaker..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
		String num="";
	 try {
		 
			
		
				bufInsSql.append("update  pay_receipt_payment_dtl set BP_ID=?,PAYMENT_TYPE=?,PAYMENT_MODE=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),AMOUNT=?,REMARK=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");				
				bufInsSql.append(" where PAYMENT_ID=? ");
				

				
				if (CommonFunction.checkNull(vo.getLbxBpId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxBpId().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getPaymentType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPaymentType().trim());
                
				if (CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPaymentMode().trim());
				
				
				if (CommonFunction.checkNull(vo.getInstrumentNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getInstrumentNo().trim());

				if (CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getInstrumentDate().trim());

				if (CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPaymentDate().trim());
				
				if (CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getPaymentAmount().trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				
				if (CommonFunction.checkNull(vo.getMakerRemark()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerRemark());

				if (CommonFunction.checkNull(recStatus).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(recStatus);
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				if (CommonFunction.checkNull(vo.getPaymentId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPaymentId().toUpperCase().trim());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				    
				logger.info("IN updatePaymentReceiptMaker() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				//status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				//logger.info("In updatePaymentReceiptMaker......................"+ status);
				status=false;
				qryList=new ArrayList();
				String allocateStr=vo.getAllocateAmount();
				String tdsStr=vo.getTds();
				String totalAllocateStr=vo.getTotalAllocatedAmount();
				logger.info("allocateStr:-"+allocateStr);
				logger.info("tdsStr:-"+tdsStr);
				logger.info("totalAllocateStr:-"+totalAllocateStr);
				String allocateArr[]=allocateStr.split(":");
				String tdsArr[]=tdsStr.split(":");
				String totalAllocateArr[]=totalAllocateStr.split(":");
				
				bufInsSql=null;
				bufInsSql= new StringBuilder();
				
				bufInsSql.append("insert into pay_txn_pay_dtl (TRANSACTION_CASE_ID,PAYMENT_ID,TOTAL_ALLOCATED_AMOUNT,ALLOCATED_AMOUNT,TDS,REC_STATUS,MAKER_ID,MAKER_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//TRANSACTION_CASE_ID
				bufInsSql.append(" ?,");//PAYMENT_ID
				bufInsSql.append(" ?,");//TOTAL_ALLOCATED_AMOUNT
				bufInsSql.append(" ?,");//ALLOCATED_AMOUNT
				bufInsSql.append(" ?,");//TDS
				bufInsSql.append(" 'P',");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//MAKER_DATE

			for(int i=0;i<totalAllocateArr.length;i++){
				
			    String allocateAm[]=allocateArr[i].split("@");
			    String tdsAm[]=tdsArr[i].split("@");
			    logger.info("totalAllocateArr[i]:--"+totalAllocateArr[i]);
			    String totalAllocateAm[]=totalAllocateArr[i].toString().split("@");
			    logger.info("totalAllocateArr[0]:--"+totalAllocateAm);
			    logger.info("totalAllocateArr[0]:--"+totalAllocateAm[0]);
			    logger.info("totalAllocateArr[1]:--"+allocateAm[1]);
			    
			    String query = "select PAYMENT_ID from pay_txn_pay_dtl where PAYMENT_ID='"+CommonFunction.checkNull(vo.getPaymentId())+"'";
				   logger.info("In saveAllocatePaymentReceipt.....................................Dao Impl" + query);
				    boolean st = ConnectionDAOforEJB.checkStatus(query);
				    if(st){
				    	insertPrepStmtObject=null;
					    insertPrepStmtObject = new PrepStmtObject();
				    	String qry="delete from pay_txn_pay_dtl where PAYMENT_ID='"+CommonFunction.checkNull(vo.getPaymentId())+"'";
				    	insertPrepStmtObject.setSql(qry);
				    	qryList.add(insertPrepStmtObject);
				    }
				    insertPrepStmtObject=null;
				    insertPrepStmtObject = new PrepStmtObject();
				if (CommonFunction.checkNull(allocateAm[0]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(allocateAm[0].trim());

				if (CommonFunction.checkNull(vo.getPaymentId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPaymentId().trim());
                
				if (CommonFunction.checkNull(totalAllocateAm[1]).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(totalAllocateAm[1].trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				if (CommonFunction.checkNull(allocateAm[1]).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(allocateAm[1].trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				if (CommonFunction.checkNull(tdsAm[1]).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(tdsAm[1].trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				    
				logger.info("IN saveAllocatePaymentReceipt() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);	
								
			}	
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			
			if(status){
				resultStr="Saved";
			}else{
				resultStr="notSaved";
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
			
		}
		return resultStr;
	}
	
	public  ArrayList<PaymentReceiptVO> allocatePaymentReceiptBehindList(PaymentReceiptVO vo){
		
		String bpName = "";
		String paymentType = "";
		String amount = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		PaymentReceiptVO paymentReceiptVo =null;
		
		ArrayList<PaymentReceiptVO> detailList = new ArrayList<PaymentReceiptVO>();
		try {
			logger.info("In PayoutMasterDaoImpl allocatePaymentReceiptBehindList()......");
	
			/*bufInsSql.append("Select A.TRANSACTION_CASE_ID,BP_ID,(Select BP_NAME From PAY_BP_M B WHERE A.BP_ID=B.BP_ID) BP_NAME,LOAN_ID,LOAN_NO,ACTIVITY_CODE,");
			bufInsSql.append(" (Select ACTIVITY_DESC FROM PAY_ACTIVITY_M C WHERE A.ACTIVITY_CODE=C.ACTIVITY_CODE) ACTIVITY_DESC,");
			bufInsSql.append("  isnull(ORG_TOTAL_AMOUNT,0) ORG_TOTAL_AMOUNT,isnull(ORG_TOTAL_AMOUNT,0)-isnull(BALANCE_AMOUNT,0) BALANCE_AMOUNT ,"); 
			bufInsSql.append("C.AMOUNT,A.SCHEME_NAME,(Select NARRATION FROM PAY_SCHEME_M D WHERE SCHEME_NAME= A.SCHEME_NAME )SCHEME_DESC");
			bufInsSql.append("   from pay_transaction_case_dtl A left outer join  pay_txn_pay_dtl C on A.TRANSACTION_CASE_ID=C.TRANSACTION_CASE_ID  "); 
            if(!CommonFunction.checkNull(vo.getPaymentId()).equalsIgnoreCase("")){
            bufInsSql.append(" and C.PAYMENT_ID='"+vo.getPaymentId()+"'");
			}
			bufInsSql.append(" where BP_ID='"+vo.getLbxBpId()+"' and  (isnull(ORG_TOTAL_AMOUNT,0) -  isnull(TOTAL_RECIVED_AMOUNT,0)) > 0 and");
			bufInsSql.append("  A.TRANSACTION_CASE_ID not in ( select D.TRANSACTION_CASE_ID from  pay_txn_pay_dtl D where D.REC_STATUS in ('P','F') ");
			if(!CommonFunction.checkNull(vo.getPaymentId()).equalsIgnoreCase("")){
	            bufInsSql.append("  AND D.PAYMENT_ID!='"+vo.getPaymentId()+"'");
			}
			bufInsSql.append(" )");*/
			/* this is the query of allocation list gross wise ie BP wise*/
			bufInsSql.append("SELECT TXN_GROSS_ID,BP_ID,(Select BP_NAME From PAY_BP_M B WHERE A.BP_ID=B.BP_ID) BP_NAME,ACTIVITY_CODE,");
			bufInsSql.append(" (Select ACTIVITY_DESC FROM PAY_ACTIVITY_M C WHERE A.ACTIVITY_CODE=C.ACTIVITY_CODE) ACTIVITY_DESC,");
			bufInsSql.append(" ISNULL( GROSS_INSENTIVE_AMOUNT,0),ISNULL( GROSS_INSENTIVE_AMOUNT,0)-ISNULL( TOTAL_RECIVED_AMOUNT,0) BALANCE_AMOUNT,"); 
			bufInsSql.append(" C.TOTAL_ALLOCATED_AMOUNT,A.SCHEME_NAME,(Select NARRATION FROM PAY_SCHEME_M D WHERE SCHEME_NAME= A.SCHEME_NAME )SCHEME_DESC");
			bufInsSql.append("  from pay_txn_gross_dtl A left outer join  pay_txn_pay_dtl C on A.TXN_GROSS_ID=C.TRANSACTION_CASE_ID"); 
            if(!CommonFunction.checkNull(vo.getPaymentId()).equalsIgnoreCase("")){
            bufInsSql.append(" and C.PAYMENT_ID='"+vo.getPaymentId()+"'");
			}
			bufInsSql.append(" where BP_ID='"+vo.getLbxBpId()+"' and  (ISNULL(GROSS_INSENTIVE_AMOUNT,0) -  ISNULL(TOTAL_RECIVED_AMOUNT,0)) > 0");
			bufInsSql.append(" and  A.TXN_GROSS_ID not in ( select D.TRANSACTION_CASE_ID from  pay_txn_pay_dtl D where D.REC_STATUS in ('P','F')");
			if(!CommonFunction.checkNull(vo.getPaymentId()).equalsIgnoreCase("")){
	            bufInsSql.append("  AND D.PAYMENT_ID!='"+vo.getPaymentId()+"'");
			}
			bufInsSql.append(" )");
			//)
		
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl allocatePaymentReceiptBehindList() search query1 ### "+ bufInsSql.toString());
			logger.info("searchActivityData " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					paymentReceiptVo=new PaymentReceiptVO();
					paymentReceiptVo.setTxnCaseId(CommonFunction.checkNull(data.get(0)).toString());
					
					paymentReceiptVo.setBpId(CommonFunction.checkNull(data.get(1)).toString());
					paymentReceiptVo.setBpName(CommonFunction.checkNull(data.get(2)).toString());
					//paymentReceiptVo.setLoanId(CommonFunction.checkNull(data.get(3)).toString());
					//paymentReceiptVo.setLoanNo(CommonFunction.checkNull(data.get(4)).toString());
					paymentReceiptVo.setActivityCode(CommonFunction.checkNull(data.get(3)).toString());
					paymentReceiptVo.setActivityDesc(CommonFunction.checkNull(data.get(4)).toString());
					
					if(CommonFunction.checkNull(data.get(5)).toString().equalsIgnoreCase("")){
						paymentReceiptVo.setPaymentAmount("0.0");	
					}else{
						Number orgAmount =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
						paymentReceiptVo.setOrgAmount(myFormatter.format(orgAmount));
						
					}
					if(CommonFunction.checkNull(data.get(6)).toString().equalsIgnoreCase("")){
						paymentReceiptVo.setPaymentAmount("0.0");	
					}else{
						Number balAmount =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(6))).trim());
						paymentReceiptVo.setBalanceAmount(myFormatter.format(balAmount));					
					}
					if(CommonFunction.checkNull(data.get(7)).toString().equalsIgnoreCase("")){
						//paymentReceiptVo.setAllocatedAmount("");	
					}else{
						Number allAmount =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(7))).trim());
						paymentReceiptVo.setAllocatedAmount(myFormatter.format(allAmount));					
					}
					paymentReceiptVo.setSchemeName(CommonFunction.checkNull(data.get(8)).toString());
					paymentReceiptVo.setSchemeDesc(CommonFunction.checkNull(data.get(9)).toString());
					detailList.add(paymentReceiptVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			bpName = null;
			paymentType = null;
			amount = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}
	
	public String saveAllocatePaymentReceipt(PaymentReceiptVO vo,ArrayList<String> txnCaseId,ArrayList<String> allocateAmount){
		boolean status = false;
		logger.info("In saveAllocatePaymentReceipt..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTxn = new StringBuilder();
		String resultStr="";
		String num="";
	 try {
		 
		   String query = "select PAYMENT_ID from pay_txn_pay_dtl where PAYMENT_ID='"+CommonFunction.checkNull(vo.getPaymentId())+"'";
		   logger.info("In saveAllocatePaymentReceipt.....................................Dao Impl" + query);
		    boolean st = ConnectionDAOforEJB.checkStatus(query);
		    if(st){
		    	String qry="delete from pay_txn_pay_dtl where PAYMENT_ID='"+CommonFunction.checkNull(vo.getPaymentId())+"'";
		    	insertPrepStmtObject.setSql(qry);
		    	qryList.add(insertPrepStmtObject);
		    }
		    
		    int size=txnCaseId.size();
		     // bufInsSqlTxn.append("update pay_transaction_case_dtl  set TOTAL_RECIVED_AMOUNT=ISNULL(TOTAL_RECIVED_AMOUNT,0)+?,BALANCE_AMOUNT=ORG_TOTAL_AMOUNT-TOTAL_RECIVED_AMOUNT where ");
		    //  bufInsSqlTxn.append("TRANSACTION_CASE_ID=?");
		   
		    
				bufInsSql.append("insert into pay_txn_pay_dtl (TRANSACTION_CASE_ID,PAYMENT_ID,AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//TRANSACTION_CASE_ID
				bufInsSql.append(" ?,");//PAYMENT_ID
				bufInsSql.append(" ?,");//AMOUNT
				bufInsSql.append(" 'P',");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");//MAKER_DATE

			for(int i=0;i<size;i++){
				insertPrepStmtObject=null;
			    insertPrepStmtObject = new PrepStmtObject();
				if (CommonFunction.checkNull(txnCaseId.get(i)).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(txnCaseId.get(i).trim());

				if (CommonFunction.checkNull(vo.getPaymentId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPaymentId().trim());
                
				if (CommonFunction.checkNull(allocateAmount.get(i)).equalsIgnoreCase("")){
					insertPrepStmtObject.addString("0.00");
				}else{
					num=myFormatter.parse(StringEscapeUtils.escapeSql(allocateAmount.get(i).trim())).toString();
					insertPrepStmtObject.addString(num);
				}
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				    
				logger.info("IN saveAllocatePaymentReceipt() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);	
								
			}	
				
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveAllocatePaymentReceipt......................"+ status);
				if(status){
					resultStr="Saved";
					
				}else{
					resultStr="notSaved";
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
			
		}
		return resultStr;
	}
	
	public String forwardPaymentReceiptMaker(PaymentReceiptVO vo){
		boolean status = false;
		logger.info("In forwardPaymentReceiptMaker..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTxn = new StringBuilder();
		String resultStr="";
		String num="";
	 try {
		 
		 bufInsSql.append("update pay_txn_pay_dtl set REC_STATUS='F' WHERE PAYMENT_ID='"+vo.getPaymentId()+"'");
		 insertPrepStmtObject.setSql(bufInsSql.toString());
		 logger.info("IN forwardPaymentReceiptMaker() update query1 ### "	+ insertPrepStmtObject.printQuery());
		 qryList.add(insertPrepStmtObject);
		 
		 bufInsSql=null;
		 bufInsSql = new StringBuilder();
		 insertPrepStmtObject=null;
		 insertPrepStmtObject = new PrepStmtObject();
		 
		 bufInsSql.append("update pay_receipt_payment_dtl set REC_STATUS='F' WHERE PAYMENT_ID='"+vo.getPaymentId()+"'");
		 insertPrepStmtObject.setSql(bufInsSql.toString());
		 logger.info("IN forwardPaymentReceiptMaker() update query2 ### "	+ insertPrepStmtObject.printQuery());
		 qryList.add(insertPrepStmtObject);
		  				
				
		 status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		 logger.info("In saveAllocatePaymentReceipt......................"+ status);
		 if(status){
			resultStr="Saved";
			
		 }else{
			resultStr="notSaved";
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
			
		}
		return resultStr;	
	}
	public ArrayList<PaymentReceiptVO> searchPaymentReceiptAuthor(PaymentReceiptVO vo) {
		
		String bpName = "";
		String paymentType = "";
		String amount = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		PaymentReceiptVO paymentReceiptVo =null;
		
		ArrayList<PaymentReceiptVO> detailList = new ArrayList<PaymentReceiptVO>();
		try {
			logger.info("In PayoutMasterDaoImpl searchPaymentReceiptMaker()......");
			bpName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchBpName())).trim());
			paymentType = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchPaymentType())).trim());
			amount=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchPaymentType())).trim());

			bufInsSql.append("Select a.PAYMENT_ID,b.BP_NAME,case a.PAYMENT_TYPE when 'P' then 'Payment' when 'R' then 'Receipt' when 'W' then 'Weive Off' end PaymentType,");
			bufInsSql.append("case a.PAYMENT_MODE  When  'C'  Then  'Cash' When  'Q'  Then  'Cheque' When  'D'  Then  'DD' When  'N'  Then  'NEFT' When  'R'  Then  'RTGS' When  'S'  Then  'ADJUSTMENT' end PaymentMode,");
			bufInsSql.append(dbo);
			bufInsSql.append(" DATE_FORMAT(a.PAYMENT_DATE,'"+dateFormat+"'),a.AMOUNT,a.remark,(Select USER_NAME from sec_user_m where user_id=a.maker_id) maker_Id");
			bufInsSql.append(" from pay_receipt_payment_dtl a  join  pay_bp_m b on a.BP_ID=b.BP_ID  where a.REC_STATUS ='F' ");
			bufInsSql.append(" and a.maker_id != '"+vo.getMakerId()+"'" );
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM (");
			bufInsSqlTempCount.append("Select a.PAYMENT_ID,b.BP_NAME,a.PAYMENT_TYPE,a.PAYMENT_MODE," );
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("DATE_FORMAT(a.PAYMENT_DATE,'"+dateFormat+"') AS PAYMENT_DATE,a.AMOUNT,a.remark");
			bufInsSqlTempCount.append(" from pay_receipt_payment_dtl a  join  pay_bp_m b on a.BP_ID=b.BP_ID  where a.REC_STATUS ='F' ");
			bufInsSqlTempCount.append(" and a.maker_id != '"+vo.getMakerId()+"'" );
			
			if(!bpName.equalsIgnoreCase("")){
	        	bufInsSql.append(" and b.BP_NAME like '%"+bpName+"%'"); 
	        	bufInsSqlTempCount.append(" and b.BP_NAME like '%"+bpName+"%'");
	         }
	        if(!paymentType.equalsIgnoreCase("")){
	         	bufInsSql.append(" and a.PAYMENT_TYPE='"+paymentType+"'"); 
	        	bufInsSqlTempCount.append(" and a.PAYMENT_TYPE='"+paymentType+"'");
	        	
	        }
			
			//bufInsSql.append(" ORDER BY b.BP_NAME");
			bufInsSqlTempCount.append(")m ");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

	      /* if ((bpName.trim() == null && paymentType.trim() == null) || (bpName.trim().equalsIgnoreCase("") && paymentType.trim().equalsIgnoreCase(""))
					|| vo.getCurrentPageLink() > 1) {*/

				logger.info("current PAge Link no .................... "+ vo.getCurrentPageLink());
				if (vo.getCurrentPageLink() > 1) {
					startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
				}

				bufInsSql.append(" ORDER BY b.BP_NAME OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
			
			//}
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl searchPaymentReceiptMaker() search query1 ### "+ bufInsSql.toString());
			logger.info("searchActivityData " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					paymentReceiptVo=new PaymentReceiptVO();
					paymentReceiptVo.setPaymentId("<a href=paymentReceiptDispatch.do?method=openPaymentReceiptAuthor&paymentId="
							+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
					//paymentReceiptVo.setBpName(CommonFunction.checkNull(data.get(0)).toString());
					
					paymentReceiptVo.setPaymentType(CommonFunction.checkNull(data.get(2)).toString());
					paymentReceiptVo.setPaymentMode(CommonFunction.checkNull(data.get(3)).toString());
					paymentReceiptVo.setPaymentDate(CommonFunction.checkNull(data.get(4)).toString());
					paymentReceiptVo.setPaymentAmount(CommonFunction.checkNull(data.get(5)).toString());
					if(CommonFunction.checkNull(data.get(5)).toString().equalsIgnoreCase("")){
						paymentReceiptVo.setPaymentAmount("0.0");	
					}else{
						Number payAmount =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
						paymentReceiptVo.setPaymentAmount(myFormatter.format(payAmount));
					}
					paymentReceiptVo.setMakerRemark(CommonFunction.checkNull(data.get(6)).toString());
					paymentReceiptVo.setMakerId(CommonFunction.checkNull(data.get(7)).toString());
					paymentReceiptVo.setTotalRecordSize(count);
					detailList.add(paymentReceiptVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			bpName = null;
			paymentType = null;
			amount = null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}
	
	public String savePaymentReceiptAuthor(PaymentReceiptVO vo){
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		String status="";
		try{
			
			logger.info("In PayoutMasterDaoImpl savePaymentReceiptAuthor()......");
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPaymentId())).trim());         // I_PAYMENT_ID
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDecision())).trim());          // I_DECISION
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAuthorRemark())).trim());      // I_AUTHOR_REMARK
			in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId())).trim());            // I_AUTHOR_id
			String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
     	    if(date != null)
     	    in.add(date);                                                                                     // I_AUTHOR_DATE
			out.add(s1);
			out.add(s2);
	
			logger.info("Pay_Payment_Receipt_Author ("+in.toString()+","+out.toString()+")");
			outMessages=(ArrayList) ConnectionDAOforEJB.callSP("Pay_Payment_Receipt_Author",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
			logger.info("s1:-----------"+s1);
			logger.info("s2:-----------"+s2);
		}catch(Exception e){
			e.printStackTrace();
			}
		return s1;
		
	}
	public String getTdsRate(){
		String returnStr="";
		String qry="";
		try{
			qry="Select TAX_PERCENT from pay_tax_m where TAX_DESC='TDS' ";
			
			logger.info("## In getTdsRate() : qry :(select of pay_tax_m) : ==>> "+qry);
			
			returnStr=ConnectionDAOforEJB.singleReturn(qry);
			
			if(CommonFunction.checkNull(returnStr).equalsIgnoreCase(""))
				returnStr = "0";
			
			logger.info("## In getTdsRate() : returnStr : ==>> "+returnStr);
			
		}catch(Exception e){
			
			logger.info("## In getTdsRate() : inside catch : some problem is there #####");
			
			e.printStackTrace();
			
		}
		return returnStr;
	}
	
	/*
	 * Code For Payment Receipt Ends Here By Arun  
	 * */
	/*
	 * Code For Schedule Master Starts Here By Ritesh Srivastava 
	 * */
	@Override
	public ArrayList<ScheduleMasterVO> searchScheduleMasterData(
			ScheduleMasterVO vo) {
		

//		String taxName = "";
//		String activityCode = "";
//		String taxPer="";
//		String stateId="";
		String activityId="";
		String startDate="";
		String endDate="";
		String recStatus="";
		String startMonth="";
		String endMonth="";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		ScheduleMasterVO scheduleMasterVO =null;
		
		ArrayList<ScheduleMasterVO> detailList = new ArrayList<ScheduleMasterVO>();
		try {
			logger.info("In PayoutMasterDaoImpl searchScheduleMasterData()......");
			activityId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchActivityId())).trim());
			startDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchStartDate())).trim());
			endDate= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchEndDate())).trim());
			startMonth = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchStartMonth())).trim());
			endMonth= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchEndMonth())).trim());
			//recStatus=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRecStatus())).trim());
			
			bufInsSql.append("SELECT pam.ACTIVITY_DESC,pbsm.START_DAY,pbsm.END_DAY,CASE WHEN pbsm.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS,pbsm.BATCH_SCH_ID,pbsm.START_MONTH,pbsm.END_MONTH FROM pay_batch_sch_m pbsm inner join pay_activity_m pam on 'a'='a' and pam.ACTIVITY_CODE=pbsm.ACTIVITY_CODE");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM pay_batch_sch_m pbsm inner join pay_activity_m pam on 'a'='a' and pam.ACTIVITY_CODE=pbsm.ACTIVITY_CODE");
			
			if(!activityId.equalsIgnoreCase("")){
	        	bufInsSql.append(" and pam.ACTIVITY_DESC like '%"+activityId+"%'"); 
	        	bufInsSqlTempCount.append(" and pam.ACTIVITY_DESC like '%"+activityId+"%'");
	        }
			if(!startDate.equalsIgnoreCase("")){
	        	bufInsSql.append(" and pbsm.START_DAY='"+startDate+"'"); 
	        	bufInsSqlTempCount.append(" and pbsm.START_DAY='"+startDate+"'");
	         }
			if(!startMonth.equalsIgnoreCase("")){
	        	bufInsSql.append(" and pbsm.START_MONTH='"+startMonth+"'"); 
	        	bufInsSqlTempCount.append(" and pbsm.START_MONTH='"+startMonth+"'");
	         }
			if(!endDate.equalsIgnoreCase("")){
				bufInsSql.append(" and pbsm.END_DAY='"+endDate+"'"); 
	        	bufInsSqlTempCount.append(" and pbsm.END_DAY='"+endDate+"'");
	         }
			if(!endMonth.equalsIgnoreCase("")){
				bufInsSql.append(" and pbsm.END_MONTH='"+endMonth+"'"); 
	        	bufInsSqlTempCount.append(" and pbsm.END_MONTH='"+endMonth+"'");
	         }
			/*if(!recStatus.equalsIgnoreCase("")){
	        	bufInsSql.append(" and pbsm.REC_STATUS='"+recStatus+"'"); 
	        	bufInsSqlTempCount.append(" and REC_STATUS='"+recStatus+"'");
	         }*/
			
			//bufInsSql.append(" ORDER BY pbsm.ACTIVITY_CODE");
		
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			/* if ((activityId.trim() == null && startDate.trim() == null && endDate==null && recStatus==null && startMonth.trim() == null && endMonth.trim() == null) 
	    		|| (activityId.trim().equalsIgnoreCase("") && startDate.trim().equalsIgnoreCase("") && endDate.equalsIgnoreCase("")&& recStatus.equalsIgnoreCase("") && startMonth.trim().equalsIgnoreCase("") && endMonth.equalsIgnoreCase(""))
			    || vo.getCurrentPageLink() > 1) {*/

				logger.info("current PAge Link no .................... "+ vo.getCurrentPageLink());
				if (vo.getCurrentPageLink() > 1) {
					startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+ startRecordIndex);
					logger.info("endRecordIndex .................... "+ endRecordIndex);
				}
				bufInsSql.append(" ORDER BY pbsm.ACTIVITY_CODE OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
				//bufInsSql.append(" limit " + startRecordIndex + ","+ endRecordIndex);
			
			//}
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl searchScheduleMasterData() search query1 ### "+ bufInsSql.toString());
			logger.info("searchScheduleMasterData:- " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					scheduleMasterVO=new ScheduleMasterVO();
					scheduleMasterVO.setActivityId("<a href=scheduleMasterDispatch.do?method=openEditScheduleMaster&activityId="
							+ CommonFunction.checkNull(data.get(4)).toString()+">"
							+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					scheduleMasterVO.setStartDate(CommonFunction.checkNull(data.get(1)).toString());
					scheduleMasterVO.setEndDate(CommonFunction.checkNull(data.get(2)).toString());
					scheduleMasterVO.setRecStatus(CommonFunction.checkNull(data.get(3)).toString());
					scheduleMasterVO.setStartMonth(CommonFunction.checkNull(data.get(5)).toString());
					scheduleMasterVO.setEndMonth(CommonFunction.checkNull(data.get(6)).toString());
					scheduleMasterVO.setTotalRecordSize(count);
					detailList.add(scheduleMasterVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			activityId = null;
			startDate = null;
			endDate= null;
			recStatus=null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	
		
		
	}
	
	
	
	public String saveScheduleMaster(ScheduleMasterVO vo) {
		boolean status = false;
		logger.info("In saveScheduleMaster..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		String resultStr="";
		String num="";
		String s1="";
		String s2="";
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
	 try {
		
      //	 String query = "Select ACTIVITY_CODE from pay_batch_sch_m where  ACTIVITY_CODE=(select ACTIVITY_CODE from pay_activity_m where ACTIVITY_DESC='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getActivityId()))+"')";
		 String query = "Select ACTIVITY_CODE from pay_batch_sch_m where  ACTIVITY_CODE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxActivityCode()))+"'";
		 logger.info("In saveScheduleMaster.....................................Dao Impl " + query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			System.out.println("RITESH$$$$$$$$1 "+st);
			query=null;
			if (st){
				
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getStartDate())).trim());         // I_START_DAY
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getStartMonth())).trim());          // I_Start_Month
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEndDate())).trim());    // I_END_DAY
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEndMonth())).trim());   // I_End_Month
				in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxActivityCode())).trim()); // I_BP_COUNTRY_ID
				in.add(StringEscapeUtils.escapeSql(vo.getBatch_sch_id()).trim());
				logger.info("getBatch_sch_id $$$$$$$$$$$ "+vo.getBatch_sch_id().trim());
				out.add(s1);
				out.add(s2);
				logger.info("getLbxActivityCode $$$$ "+vo.getLbxActivityCode().trim());
				logger.info("pay_schedulemaster_validation ("+in.toString()+","+out.toString()+")");
				outMessages=(ArrayList) ConnectionDAOforEJB.callSP("pay_schedulemaster_validation",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				System.out.println("RITESH$$$$$$$$ "+s1+" "+s2);
				
			}
//			 if (!st) {
	  if(s1.equalsIgnoreCase("S")||!st){
		  logger.info("Ritesh$$$"+"inside if S= "+s2);
			if (vo.getRecStatus()!= null && vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";

				}
		
				bufInsSql.append("INSERT INTO pay_batch_sch_m (ACTIVITY_CODE,START_DAY,START_MONTH,END_DAY,END_MONTH,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//ACTIVITY_CODE
				bufInsSql.append(" ?,");//START_DAY
				bufInsSql.append(" ?,");//START_MONTH
				bufInsSql.append(" ?,");//END_DAY
				bufInsSql.append(" ?,");//END_MONTH
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
				bufInsSql.append(" ?,");//AUTHOR_ID
				bufInsSql.append(" CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )");//AUTHOR_DATE
				 

				if (CommonFunction.checkNull(vo.getLbxActivityCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxActivityCode().trim());
				
				if (CommonFunction.checkNull(vo.getStartDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.00");
				else
					// num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getStartDate().trim())).toString();
					insertPrepStmtObject.addString(vo.getStartDate().trim());
                
				if (CommonFunction.checkNull(vo.getStartMonth()).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.00");
				else
					// num=myFormatter.parse(StringEscapeUtils.escapeSql(vo.getStartDate().trim())).toString();
					insertPrepStmtObject.addString(vo.getStartMonth().trim());
				
				if (CommonFunction.checkNull(vo.getEndDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEndDate().trim());
				
				if (CommonFunction.checkNull(vo.getEndMonth()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getEndMonth().trim());

				
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
				    
				logger.info("IN saveScheduleMaster() insert query1 ### "	+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveScheduleMaster......................"+ status);
				if(status){
					resultStr="Saved";
				}else{
					resultStr="notSaved";
				}
			}
	  else if(s1.equalsIgnoreCase("E")){
		  logger.info("Ritesh$$$"+"inside if S= "+s1);
		  //resultStr=s2;
		   resultStr=s1+s2;
		  
	  }else {
				resultStr="Already";
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
			
		}
		return resultStr;
	}
	public ArrayList<ScheduleMasterVO> openEditScheduleMaster(
			ScheduleMasterVO scheduleMasterVO){

		String activityId = "";
		String startDate = "";
		String endDate="";
		String startMonth="";
		String endMonth="";
	
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList data= new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		ScheduleMasterVO masterVO =null;
		
		ArrayList<ScheduleMasterVO> detailList = new ArrayList<ScheduleMasterVO>();
		try {
			logger.info("In PayoutMasterDaoImpl openEditScheduleMaster()......");
			activityId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(scheduleMasterVO.getSearchActivityId())).trim());
			startDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(scheduleMasterVO.getSearchStartDate())).trim());
			endDate= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(scheduleMasterVO.getSearchEndDate())).trim());
			startMonth=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(scheduleMasterVO.getSearchStartMonth())).trim());
			endMonth= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(scheduleMasterVO.getSearchEndMonth())).trim());
			
			bufInsSql.append("SELECT pam.ACTIVITY_DESC,pbsm.START_DAY,pbsm.END_DAY,CASE WHEN pbsm.REC_STATUS='A' THEN 'Active' ELSE 'Inactive' END as REC_STATUS ,pbsm.BATCH_SCH_ID,pbsm.ACTIVITY_CODE ,pbsm.START_MONTH,pbsm.END_MONTH from pay_batch_sch_m pbsm inner join pay_activity_m pam");
			bufInsSql.append(" on pam.ACTIVITY_CODE=pbsm.ACTIVITY_CODE and pbsm.BATCH_SCH_ID='"+CommonFunction.checkNull(scheduleMasterVO.getActivityId())+"' ");
			
			logger.info("query : " + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("In PayoutMasterDaoImpl openEditScheduleMaster() search query1 ### "+ bufInsSql.toString());
			logger.info("searchActivityData " + searchlist.size());
			int size = searchlist.size();
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					masterVO=new ScheduleMasterVO();
					masterVO.setActivityId(CommonFunction.checkNull(data.get(0)).toString());
					masterVO.setStartDate(CommonFunction.checkNull(data.get(1)).toString());
					masterVO.setEndDate(CommonFunction.checkNull(data.get(2)).toString());
					masterVO.setRecStatus(CommonFunction.checkNull(data.get(3)).toString());
					masterVO.setBatch_sch_id(CommonFunction.checkNull(data.get(4)).toString());
					masterVO.setLbxActivityCode(CommonFunction.checkNull(data.get(5)).toString());
					masterVO.setStartMonth(CommonFunction.checkNull(data.get(6)).toString());
					masterVO.setEndMonth(CommonFunction.checkNull(data.get(7)).toString());
					detailList.add(masterVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			activityId = null;
			startDate = null;
			endDate=null;
			searchlist = null;
			data= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			
		}

		return detailList;
	}
	
	
	 public String updateScheduleMaster(ScheduleMasterVO masterVO){
		
		 boolean status = false;
			logger.info("In updateScheduleMaster..........");
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			String stat = "X";
			StringBuilder bufInsSql = new StringBuilder();
			String resultStr="";
			String num="";
			boolean st=false;
			String s1="";
			String s2="";
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			
			
		 try {
			 String query = "Select ACTIVITY_CODE from pay_batch_sch_m where  ACTIVITY_CODE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( masterVO.getLbxActivityCode()))+"'"+"and BATCH_SCH_ID<>'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( masterVO.getBatch_sch_id()))+"'";
				logger.info("In updateScheduleMaster.....................................Dao Impl" + query);
				 st = ConnectionDAOforEJB.checkStatus(query);
				query=null;
				logger.info("st value$$$$$$$"+st);
				if (st){
					logger.info("st value inside if $$$$$$$"+st);
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVO.getStartDate())).trim());         // I_START_DAY
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVO.getStartMonth())).trim());          // I_Start_Month
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVO.getEndDate())).trim());    // I_END_DAY
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVO.getEndMonth())).trim());   // I_End_Month
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVO.getLbxActivityCode())).trim()); // I_BP_COUNTRY_ID
					in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(masterVO.getBatch_sch_id())).trim()); // I_Batch_Schedule_Id
					out.add(s1);
					out.add(s2);
					logger.info("getLbxActivityCode $$$$ "+masterVO.getLbxActivityCode().trim());
					logger.info("pay_schedulemaster_validation ("+in.toString()+","+out.toString()+")");
					outMessages=(ArrayList) ConnectionDAOforEJB.callSP("pay_schedulemaster_validation",in,out);
					s1=CommonFunction.checkNull(outMessages.get(0));
					s2=CommonFunction.checkNull(outMessages.get(1));
					System.out.println("RITESH$$$$$$$$ "+s1+" "+s2);
					
				}
				logger.info("s1 value$$$$$$"+s1);
			if (s1.equalsIgnoreCase("S")||!st) {
				logger.info("s1 value$$$if$$$"+s1);
				
				if (masterVO.getRecStatus()!= null && masterVO.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

					}
			
					bufInsSql.append("update pay_batch_sch_m set ACTIVITY_CODE=? ,START_DAY=?,START_MONTH=?,END_DAY=?,END_MONTH=?,REC_STATUS=?");				
					bufInsSql.append(",MAKER_ID=?,MAKER_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
					bufInsSql.append(" ,AUTHOR_ID=?,AUTHOR_DATE=CONVERT(SMALLDATETIME,?,103) + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					bufInsSql.append("  where BATCH_SCH_ID=?");
					
			/*		
					if (CommonFunction.checkNull(masterVO.getActivityId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getActivityId().trim());
*/
					if (CommonFunction.checkNull(masterVO.getLbxActivityCode()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getLbxActivityCode().trim());
					
					if (CommonFunction.checkNull(masterVO.getStartDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getStartDate().trim());
	                
					if (CommonFunction.checkNull(masterVO.getStartMonth()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getStartMonth().trim());
					
					if (CommonFunction.checkNull(masterVO.getEndDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getEndDate().trim());
					
					if (CommonFunction.checkNull(masterVO.getEndMonth()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getEndMonth().trim());


					if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);

				
					if (CommonFunction.checkNull(masterVO.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getMakerId());

					if (CommonFunction.checkNull(masterVO.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getMakerDate());

					if (CommonFunction.checkNull(masterVO.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getMakerId());

					if (CommonFunction.checkNull(masterVO.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(masterVO.getMakerDate());
					
					if (CommonFunction.checkNull(masterVO.getBatch_sch_id()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(masterVO.getBatch_sch_id());
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					    
					logger.info("IN updateScheduleMaster() insert query1 ### "	+ insertPrepStmtObject.printQuery());
					
					qryList.add(insertPrepStmtObject);
					
					status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("In updateScheduleMaster......................"+ status);
					if(status){
						resultStr="Saved";
					}
					else{
						resultStr="notSaved";
					}
				} 
			else if(s1.equalsIgnoreCase("E")){
				logger.info("s1 value$$$else if $$$"+s1+" "+s2);
				  //resultStr=s2;
				   resultStr=s1+s2;
				  
			  }else {
					resultStr="Already";
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
				
			}
			return resultStr;
	 }
	/*
	 * Code For Schedule Master Ends Here By Ritesh Srivastava 
	 * */


	
	 /*
	  * Code for Report
	  * */
	 @Override
		public ArrayList getReportFormat() {
		 ArrayList list = new ArrayList();
		 PayReportVO reportformat = null;
			try {
				logger
						.info("In getReportFormat..........................DAOImpl");
				String query = "SELECT VALUE,DESCRIPTION FROM generic_master WHERE GENERIC_KEY='REPORT_FORMAT' and rec_status ='A' order by VALUE desc ";
		
				ArrayList formatlist = ConnectionDAOforEJB.sqlSelect(query.toString());
				logger.info("getReportFormat()----> " + formatlist.size());
				for (int i = 0; i < formatlist.size(); i++) {
					
					ArrayList data = (ArrayList) formatlist.get(i);
					if (data.size()>0) {
						
						reportformat = new PayReportVO();
						reportformat.setReportformatid(( data.get(0).toString()));
						reportformat.setReportformat((data.get(1).toString()));
						list.add(reportformat);
																
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
	 /*
	  * Code for Report
	  * */
}
