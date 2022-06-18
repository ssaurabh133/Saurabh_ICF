package com.cm.daoImplMYSQL;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.connect.PrepStmtObject;
import com.login.roleManager.UserObject;
import com.cm.dao.PoolIDDAO;
import com.cm.vo.PoolIdMakerVO;

public class PoolIDDAOImpl implements PoolIDDAO{
	
	private static final Logger logger = Logger.getLogger(PoolIDDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	SimpleDateFormat fileNameFormat = new SimpleDateFormat ("yyyy-MM-dd'_'hh-mm-ss");
	 String dbType=resource.getString("lbl.dbType");
	public ArrayList<PoolIdMakerVO> searchPoolIdMakerGrid(PoolIdMakerVO poolIdMakerVO)
	{
		 logger.info("In searchPoolIdMakerGrid() method of PoolIDDAOImpl");
		 StringBuilder poolId=new StringBuilder();
		 StringBuilder poolName=new StringBuilder();
		 StringBuilder poolCreationDate=new StringBuilder();
		 StringBuilder cutOffDate=new StringBuilder();
		 StringBuilder instituteId=new StringBuilder();
		 StringBuilder reportingTo=new StringBuilder();
		 StringBuilder makerID=new StringBuilder();
		 
	   	 int count=0;
	     int startRecordIndex=0;
	     int endRecordIndex = no;	        
   	     ArrayList<PoolIdMakerVO> getPoolIdMakerList=new ArrayList<PoolIdMakerVO>();
	 	 try
	 	 {
	 		 ArrayList searchlist=new ArrayList();
	 		 logger.info("In searchPoolIdMakerGrid....................");
	 		 boolean appendSQL=false;
	 		 StringBuilder bufInsSql =	new StringBuilder();
	 		 StringBuilder bufInsSqlTempCount = new StringBuilder();
	 		 poolId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getPoolID()).trim()));
	 		 logger.info("In searchPoolIdMakerGrid......poolId  "+poolId);
	 		 poolName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getPoolName()).trim()));
	 		 logger.info("In searchPoolIdMakerGrid......poolName  "+poolName);
	 		 poolCreationDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getPoolCreationDate()).trim()));
	 		 logger.info("In searchPoolIdMakerGrid......poolCreationDate  "+poolCreationDate);            	     
	 		 cutOffDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getCutOffDate()).trim()));
	 		 logger.info("In searchPoolIdMakerGrid......cutOffDate  "+cutOffDate);
	 		 instituteId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getInstituteID()).trim()));
	 		 logger.info("In searchPoolIdMakerGrid......instituteId  "+instituteId);
	 		 reportingTo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getReportingToUserId()).trim()));
	 		 logger.info("In searchPoolIdMakerGrid......reportingTo  "+reportingTo);
	 		 makerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getMakerID()).trim()));
	 		 logger.info("In searchPoolIdMakerGrid......makerID  "+makerID);       
   	         bufInsSql.append(" select P.pool_id,P.pool_name,DATE_FORMAT(P.pool_creation_date,'%d-%m-%Y'),DATE_FORMAT(P.POOL_CUTOFF_DATE,'%d-%m-%Y'),A.AGENCY_CODE,A.AGENCY_NAME,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=p.maker_id) USERID  "+
  		 		            "  from cr_securitization_pool_m P,com_agency_m A where P.POOL_INSTITUTION_ID=A.AGENCY_CODE and P.rec_status='P' AND p.MAKER_ID='"+makerID+"' ");
   	         bufInsSqlTempCount.append("  select  count(1) from cr_securitization_pool_m P,com_agency_m A where  P.POOL_INSTITUTION_ID=A.AGENCY_CODE   and P.rec_status='P' AND p.MAKER_ID='"+makerID+"' " );
   	         if(!poolId.toString().trim().equalsIgnoreCase(""))
   	         {
   	    	   bufInsSql.append("AND P.pool_id='"+poolId+"'");
   	    	   bufInsSqlTempCount.append("AND P.pool_id='"+poolId+"'");
   	         }
   	         if(!poolName.toString().trim().equalsIgnoreCase(""))
	         {
	    	   bufInsSql.append("AND P.pool_name LIKE '%"+poolName+"%'");
	    	   bufInsSqlTempCount.append("AND P.pool_name LIKE '%"+poolName+"%'");
	         }
   	         if(!poolCreationDate.toString().trim().equalsIgnoreCase(""))
	         {
   	           String date =CommonFunction.changeFormat(poolCreationDate.toString());
	    	   bufInsSql.append(" AND P.pool_creation_date='"+date+"'");
	    	   bufInsSqlTempCount.append(" AND P.pool_creation_date='"+date+"'");
	         }
   	         if(!cutOffDate.toString().trim().equalsIgnoreCase(""))
	         {
   	           String date =CommonFunction.changeFormat(cutOffDate.toString());
	    	   bufInsSql.append("AND P.POOL_CUTOFF_DATE='"+date+"'");
	    	   bufInsSqlTempCount.append("AND P.POOL_CUTOFF_DATE='"+date+"'");
	         }
   	         if(!instituteId.toString().trim().equalsIgnoreCase(""))
	         {
	    	   bufInsSql.append(" AND A.AGENCY_NAME='"+instituteId+"'");
	    	   bufInsSqlTempCount.append(" AND A.AGENCY_NAME='"+instituteId+"'");
	         }
   	         if(!reportingTo.toString().trim().equalsIgnoreCase(""))
             {
  	           bufInsSql.append("AND P.MAKER_ID='"+reportingTo+"'");
  	           bufInsSqlTempCount.append("AND P.MAKER_ID='"+reportingTo+"'");
             }
   	         logger.info("Count Query : "+bufInsSqlTempCount.toString());
   	         count =Integer.parseInt(ConnectionUploadDAO.singleReturn(bufInsSqlTempCount.toString()));
   	         bufInsSqlTempCount=null;
   	         startRecordIndex = (poolIdMakerVO.getCurrentPageLink()-1)*no;
   	         endRecordIndex = no;
   	      logger.info("startRecordIndex .................... "+startRecordIndex);
   	   logger.info("endRecordIndex .................... "+endRecordIndex);
   	         bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
   	         logger.info("search  Query : "+bufInsSql.toString());
   	         searchlist = ConnectionUploadDAO.sqlSelect(bufInsSql.toString());
   	         logger.info("searchPoolIdMakerGrid Data size is...."+searchlist.size());
   	         logger.info("searchPoolIdMakerGrid    ........"+bufInsSql.toString());
   	         bufInsSql=null;
  	    	    
   	         for(int i=0;i<searchlist.size();i++)
   	         {
   	        	 ArrayList data=(ArrayList)searchlist.get(i);
   	        	 if(data.size()>0)
   	        	 {
   	        		 PoolIdMakerVO VO = new PoolIdMakerVO();
   	        		 VO .setModifyNo("<a href=poolIdMakerProcessAction.do?method=getPoolSearchedData&poolID="+ CommonFunction.checkNull(data.get(0)).toString()+">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
   	        		 VO.setPoolID((CommonFunction.checkNull(data.get(0)).trim()));
   	        		 VO.setPoolName((CommonFunction.checkNull(data.get(1)).trim()));
   	        		 VO.setPoolCreationDate((CommonFunction.checkNull(data.get(2)).trim()));
   	        		 VO.setCutOffDate((CommonFunction.checkNull(data.get(3)).trim()));                 	   
   	        		 VO.setLbxinstituteID((CommonFunction.checkNull(data.get(4)).trim()));
   	        		 VO.setInstituteID((CommonFunction.checkNull(data.get(5)).trim()));
   	        		 VO.setUserName((CommonFunction.checkNull(data.get(6)).trim()));     	   
   	        		 VO.setTotalRecordSize(count);
   	        		 getPoolIdMakerList.add(VO);	
   	        	 }
   	         }
   	     }
	 	 catch(Exception e)
	 	 {e.printStackTrace();}
 		 finally
 		 {
 			poolId=null;
 			poolName=null;
 			poolCreationDate=null;
 			cutOffDate=null;
 			instituteId=null;
 			reportingTo=null;
 		 }
 		 return  getPoolIdMakerList;			
	}
	
	public String getPoolNo() 
	{
		 logger.info("In getPoolNo() method of PoolIDDAOImpl");
		
		 StringBuilder query=new StringBuilder();
 query.append(" select max(pool_id) from cr_securitization_pool_m for update");
		 String generateId = ConnectionUploadDAO.singleReturn(query.toString());
		 logger.info("generateId---"+generateId);
		query=null;
		return generateId;
	
	}
	
	public ArrayList downLoadPoolErrorLog(HttpServletRequest request,HttpServletResponse response,String makerid){
		 logger.info("In downLoadPoolErrorLog() method of PoolIDDAOImpl");
		

		logger.info("in..downLoadPoolErrorLog(). in DAOIM.");
		boolean status=false;
	    HttpSession session = null;	
	    StringBuilder userID = new StringBuilder();
        StringBuilder txtReport = new StringBuilder();	       
        ArrayList dataList = null;
        ArrayList arrBatchID = null;
        ArrayList alSearchResults = null;
        ArrayList downloadResults = null;
        int count = 0;	
		ArrayList list= new ArrayList();
		try
		{
			 StringBuilder query=new StringBuilder();
		query.append("select LOAN_ID,LOAN_PRODUCT,LOAN_SCHEME,LOAN_CUSTOMER_ID,LOAN_CUSTOMER_TYPE,LOAN_CUSTOMER_CATEGORY," +
				" LOAN_CUSTOMER_CONSTITUTION,LOAN_CUSTOMER_BUSINESS_SEGMENT,LOAN_INDUSTRY,LOAN_SUB_INDUSTRY,LOAN_DISBURSAL_DATE," +
				" LOAN_MATURITY_DATE,LOAN_TENURE,LOAN_BALANCE_TENURE,LOAN_INSTL_NUM,LOAN_ADV_EMI_NUM,LOAN_INT_RATE," +
				" LOAN_DISBURSAL_STATUS,LOAN_NPA_FLAG,LOAN_DPD,LOAN_DPD_STRING,LOAN_ASSET_COST,LOAN_AMOUNT,LOAN_EMI,LOAN_ADV_EMI_AMOUNT" +
				" ,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,LOAN_RECEIVED_PRINCIPAL,LOAN_OVERDUE_INSTL_NUM,LOAN_OVERDUE_AMOUNT," +
				" LOAN_BALANCE_INSTL_NUM,LOAN_BALANCE_INSTL_AMOUNT,REC_STATUS,REJECT_REASON from pool_creation_update_tmp" +
				" WHERE MAKER_ID='"+makerid+"' and uploaded_flag='N'");

		logger.info("In getUploadError()...............query...........DAOImpl "+query);
				
		list=ConnectionUploadDAO.sqlSelect(query.toString());
	   
	          query=null;
	          }
	          catch (Exception e) 
	          {
	        	  logger.debug("IOException In downLoadErrorLog() ==>> "+e.getMessage());
	          }	
	          finally
	          {
	        	  userID=null;
	        	  txtReport=null;
	          }
	       
	          return list;
	
	}
	
	public ArrayList<PoolIdMakerVO> searchPoolIdAuthorGrid(PoolIdMakerVO poolIdMakerVO)
	{
		logger.info("In searchPoolIdAuthorGrid() method of PoolIDDAOImpl");
		StringBuilder poolId=new StringBuilder();
		StringBuilder poolName=new StringBuilder();
		StringBuilder poolCreationDate=new StringBuilder();
		StringBuilder cutOffDate=new StringBuilder();
		StringBuilder instituteId=new StringBuilder();
		String makerID=poolIdMakerVO.getMakerID();
  	    int count=0;
	    int startRecordIndex=0;
	    int endRecordIndex = no;
  	    ArrayList<PoolIdMakerVO> getPoolIdMakerList=new ArrayList<PoolIdMakerVO>();
	 	
	 	try
	 	{ 	
	 		  ArrayList searchlist=new ArrayList();
	 		  logger.info("In searchPoolIdAuthorGrid....................");
	 		  boolean appendSQL=false;
	 		  StringBuilder bufInsSql =	new StringBuilder();
	 		  StringBuilder bufInsSqlTempCount =new StringBuilder();
	 		  poolId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getPoolID()).trim()));
	 		  logger.info("In searchPoolIdAuthorGrid......poolId  "+poolId);
	 		  poolName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getPoolName()).trim()));
	 		  logger.info("In searchPoolIdAuthorGrid......poolName  "+poolName);
	 		  poolCreationDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getPoolCreationDate()).trim()));
	 		  logger.info("In searchPoolIdAuthorGrid......poolCreationDate  "+poolCreationDate);            	     
	 		  cutOffDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getCutOffDate()).trim()));
	 		  logger.info("In searchPoolIdAuthorGrid......cutOffDate  "+cutOffDate);
	 		  instituteId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getInstituteID()).trim()));
	 		  logger.info("In searchPoolIdAuthorGrid......instituteId  "+instituteId);	  
	 		  bufInsSql.append(" select P.pool_id,P.pool_name,DATE_FORMAT(P.pool_creation_date,'%d-%m-%Y'),DATE_FORMAT(P.POOL_CUTOFF_DATE,'%d-%m-%Y'),A.AGENCY_CODE,A.AGENCY_NAME "+
 		 		" , (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=P.MAKER_ID) MAKER_ID from cr_securitization_pool_m P,com_agency_m A where P.POOL_INSTITUTION_ID=A.AGENCY_CODE " +
 		 		"  and P.rec_status='F' and P.MAKER_ID!='"+makerID+"'  ");
 		  
  	         bufInsSqlTempCount.append("  select  count(1) from cr_securitization_pool_m P,com_agency_m A where P.POOL_INSTITUTION_ID=A.AGENCY_CODE and P.rec_status='F' and P.MAKER_ID!='"+makerID+"'  ");
 		        
             
  	         if(!poolId.toString().trim().equalsIgnoreCase(""))
  	         {
  	        	 bufInsSql.append(" AND P.pool_id='"+poolId+"'" );
  	        	 bufInsSqlTempCount.append("  AND P.pool_id='"+poolId+"'" );
  	         }
  	         if(!poolName.toString().trim().equalsIgnoreCase(""))
	         {
	        	 bufInsSql.append(" AND P.pool_name LIKE '%"+poolName+"%'" );
	        	 bufInsSqlTempCount.append("  AND P.pool_name LIKE '%"+poolName+"%'" );
	         }
  	         if(!poolCreationDate.toString().trim().equalsIgnoreCase(""))
	         {
  	        	 String date =CommonFunction.changeFormat(poolCreationDate.toString());
	        	 bufInsSql.append(" and P.pool_creation_date='"+date+"'" );
	        	 bufInsSqlTempCount.append(" and  P.pool_creation_date='"+date+"'" );
	         }
  	         if(!cutOffDate.toString().trim().equalsIgnoreCase(""))
	         {
	        	 String date =CommonFunction.changeFormat(cutOffDate.toString());
	        	 bufInsSql.append(" and P.POOL_CUTOFF_DATE='"+date+"'" );
	        	 bufInsSqlTempCount.append(" and  P.POOL_CUTOFF_DATE='"+date+"'" );
	         }
  	         if(!instituteId.toString().trim().equalsIgnoreCase(""))
  	         {
  	        	 bufInsSql.append(" AND A.AGENCY_NAME='"+instituteId+"'" );
  	        	 bufInsSqlTempCount.append("  AND A.AGENCY_NAME='"+instituteId+"'" );
  	         }
  	         logger.info("Count Query  : "+bufInsSqlTempCount.toString());
  	         count =Integer.parseInt(ConnectionUploadDAO.singleReturn(bufInsSqlTempCount.toString()));
  	         startRecordIndex = (poolIdMakerVO.getCurrentPageLink()-1)*no;
			 endRecordIndex = no;
			 logger.info("startRecordIndex---- "+startRecordIndex);
			 logger.info("endRecordIndex------ "+endRecordIndex);
			 bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			 logger.info("Select Query   :  "+bufInsSql.toString());
			 searchlist = ConnectionUploadDAO.sqlSelect(bufInsSql.toString());
			 logger.info("searchPoolIdAuthorGrid Data size is...."+searchlist.size());
	         logger.info("searchPoolIdAuthorGrid    ........:::::>>>>>>>>"+bufInsSql.toString());
	         bufInsSqlTempCount=null;
	         bufInsSql=null;
	    
	         for(int i=0;i<searchlist.size();i++)
	         {
	        	 ArrayList data=(ArrayList)searchlist.get(i);
                 if(data.size()>0)
                 {
                	 PoolIdMakerVO VO = new PoolIdMakerVO();
                	 VO .setModifyNo("<a href=poolIdAuthorProcessAction.do?method=getPoolSearched&poolID="+ CommonFunction.checkNull(data.get(0)).toString()+">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");  
					 VO.setPoolID((CommonFunction.checkNull(data.get(0)).trim()));
					 VO.setPoolName((CommonFunction.checkNull(data.get(1)).trim()));
					 VO.setPoolCreationDate((CommonFunction.checkNull(data.get(2)).trim()));
					 VO.setCutOffDate((CommonFunction.checkNull(data.get(3)).trim()));                 	   
					 VO.setLbxinstituteID((CommonFunction.checkNull(data.get(4)).trim()));
					 VO.setInstituteID((CommonFunction.checkNull(data.get(5)).trim()));
					 VO.setUserName((CommonFunction.checkNull(data.get(6)).trim()));  
					 VO.setTotalRecordSize(count);  
					 getPoolIdMakerList.add(VO);	
                 }
		      }
	    }
	 	catch(Exception e)
	 	{e.printStackTrace();}
		finally
		{ 
			poolId=null;
			poolName=null;
			poolCreationDate=null;
			cutOffDate=null;
			instituteId=null;
		}
		return  getPoolIdMakerList;			
	}
	
	
	public boolean forwardPoolIdMaker(String poolID) {
		logger.info("In forwardPoolIdMaker() method of PoolIDDAOImpl");

		
		boolean status=false;
		   ArrayList updateQuery =null;
			try{
						
					updateQuery = new ArrayList();
					  StringBuilder query=new StringBuilder();
	      query.append("update cr_securitization_pool_dtl set REC_STATUS='F' "+
	      " where POOL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolID))+"");

	             updateQuery.add(query);	
	             logger.info("In forward cr_securitization_pool_dtl:-"+query);
				   
	             query=null;
	             
					status =ConnectionUploadDAO.sqlInsUpdDelete(updateQuery);
					logger.info("In getListOfValuesupdate,,,,,"+status);
				 
				 if(status){
					 
					 updateQuery = new ArrayList();
				      String query1="update cr_securitization_pool_m set REC_STATUS='F' "+
				      " where POOL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolID))+"";

				             updateQuery.add(query1);	
				             logger.info("In forward cr_securitization_pool_m:-"+query1);
							   
				             query1=null;
								status =ConnectionUploadDAO.sqlInsUpdDelete(updateQuery);
								logger.info("In getListOfValuesupdate,,,,,"+status); 
				 }
        
			}catch(Exception e){
				e.printStackTrace();
			}

			return status;

	}
	
		 	 
	
	 public boolean deletePoolID(String[] loanID, String[] poolID){
		 
		 logger.info("In deletePoolID() method of PoolIDDAOImpl");
		 
			
			ArrayList qryList =  new ArrayList();
			boolean status =false;
			
			StringBuffer bufInsSql =null;
			PrepStmtObject insertPrepStmtObject = null;	
			

			
			logger.info("In deletePoolID...Dao Impl.");
			 
			  String qry=null;
			  String checkCount=null;
			try{
				
				for(int i =0;i<loanID.length;i++){
				 bufInsSql =	new StringBuffer();
				 insertPrepStmtObject = new PrepStmtObject();
				 qry="Select count(1) from cr_securitization_pool_dtl where  POOL_ID = '"+CommonFunction.checkNull(poolID[i]).trim()+"' and LOAN_ID = '"+CommonFunction.checkNull(loanID[i]).trim()+"'";
				 checkCount=ConnectionUploadDAO.singleReturn(qry);
				 if(!CommonFunction.checkNull(checkCount).equalsIgnoreCase("0")){
					 
				 
					 bufInsSql.append("delete from cr_securitization_pool_dtl where POOL_ID = ? and LOAN_ID = ?");

						 if(CommonFunction.checkNull(poolID[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(poolID[i]).trim()));
						 
						 if(CommonFunction.checkNull(loanID[i]).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(loanID[i]).trim()));
						 
							
							insertPrepStmtObject.setSql(bufInsSql.toString());
							qryList.add(insertPrepStmtObject);
				 }
			}
				    logger.info("delete:-"+insertPrepStmtObject.printQuery());
				     status=ConnectionUploadDAO.sqlInsUpdDeletePrepStmt(qryList);
			
			
				     bufInsSql=null;
		}catch(Exception e){
			logger.debug("IOException In getPoolData() ==>> "+e.getMessage());
		}
		
		return status;

	 }
	
	 public ArrayList getPoolData(String poolID,Object poolIdMakerVO) 
	 {
		 logger.info("In getPoolData() method of PoolIDDAOImpl");
		    int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			ArrayList arrList = new ArrayList();
			ArrayList subList = new ArrayList();
			ArrayList mainList = new ArrayList();
			PoolIdMakerVO poolIdMakerVOo= (PoolIdMakerVO)poolIdMakerVO;
			StringBuilder customerType=new StringBuilder();
			StringBuilder disbursalStatus=new StringBuilder();
			try
			{
				StringBuilder query=new StringBuilder();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
				query.append("select POOL_ID,LOAN_ID, LOAN_PRODUCT,P.PRODUCT_DESC, LOAN_SCHEME,S.SCHEME_DESC,cm.CUSTOMER_NAME, LOAN_CUSTOMER_TYPE, LOAN_CUSTOMER_CATEGORY," +
					" LOAN_CUSTOMER_CONSTITUTION,LOAN_CUSTOMER_BUSINESS_SEGMENT, LOAN_INDUSTRY,I.INDUSTRY_DESC, LOAN_SUB_INDUSTRY, SI.SUB_INDUSTRY_DESC," +
					" DATE_FORMAT(LOAN_DISBURSAL_DATE,'"+dateFormat+"'), DATE_FORMAT(LOAN_MATURITY_DATE,'"+dateFormat+"')," +
					" LOAN_TENURE,LOAN_BALANCE_TENURE, LOAN_INSTL_NUM,LOAN_ADV_EMI_NUM, LOAN_INT_RATE, LOAN_DISBURSAL_STATUS, LOAN_NPA_FLAG," +
					" LOAN_DPD, LOAN_DPD_STRING, LOAN_ASSET_COST, LOAN_AMOUNT,LOAN_EMI, LOAN_ADV_EMI_AMOUNT, LOAN_BALANCE_PRINCIPAL, LOAN_OVERDUE_PRINCIPAL," +
					" LOAN_RECEIVED_PRINCIPAL, LOAN_OVERDUE_INSTL_NUM, LOAN_OVERDUE_AMOUNT, LOAN_BALANCE_INSTL_NUM, LOAN_BALANCE_INSTL_AMOUNT " +
					" from cr_securitization_pool_dtl A " +
					"LEFT JOIN com_industry_m I ON(I.INDUSTRY_ID=LOAN_INDUSTRY) " +
					"LEFT JOIN com_sub_industry_m SI ON(SI.sub_industry_id=LOAN_SUB_INDUSTRY) " +
					"LEFT JOIN cr_product_m P ON(LOAN_PRODUCT=P.PRODUCT_ID) " +
					"LEFT JOIN cr_scheme_m S ON(LOAN_SCHEME=S.SCHEME_ID) " +
					"LEFT JOIN gcd_customer_m cm ON(cm.CUSTOMER_ID=A.LOAN_CUSTOMER_ID) " +
					" where pool_id ="+poolID+" and A.rec_status = 'P' ");
			
				bufInsSqlTempCount.append("SELECT COUNT(1) " +
						" from cr_securitization_pool_dtl A " +
						"LEFT JOIN com_industry_m I ON(I.INDUSTRY_ID=LOAN_INDUSTRY) " +
						"LEFT JOIN com_sub_industry_m SI ON(SI.sub_industry_id=LOAN_SUB_INDUSTRY) " +
						"LEFT JOIN cr_product_m P ON(LOAN_PRODUCT=P.PRODUCT_ID) " +
						"LEFT JOIN cr_scheme_m S ON(LOAN_SCHEME=S.SCHEME_ID) " +
						"LEFT JOIN gcd_customer_m cm ON(cm.CUSTOMER_ID=A.LOAN_CUSTOMER_ID) " +
						" where pool_id ="+poolID+" and A.rec_status = 'P' ");
				logger.info("In getPoolData() Count query   :  "+bufInsSqlTempCount);
				count =Integer.parseInt(ConnectionUploadDAO.singleReturn(bufInsSqlTempCount.toString()));
				logger.info("In getPoolData() Count   :  "+count);
				logger.info("In getPoolData() Pool Id   :  "+poolID);
						
				if(poolIdMakerVOo.getCurrentPageLink()>1)
				{
					startRecordIndex = (poolIdMakerVOo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex . "+startRecordIndex);
					logger.info("endRecordIndex . "+endRecordIndex);
				}
				query.append(" limit "+startRecordIndex+","+endRecordIndex);
				logger.info("in getPoolData()  Query   :   "+query.toString());			
				arrList = ConnectionUploadDAO.sqlSelect(query.toString());			
				query=null;
				int size=arrList.size();
				for(int i=0;i<size;i++)
				{
					disbursalStatus= new StringBuilder();
					customerType= new StringBuilder();
					subList=(ArrayList)arrList.get(i);
					if(subList.size()>0)
					{
						PoolIdMakerVO poolIdMakerVO1=new PoolIdMakerVO(); 
						poolIdMakerVO1.setCheckBoxDis("<input type="+"checkbox"+" name=checkId id=checkId"+i+"  value="+CommonFunction.checkNull(subList.get(1)).trim()+"  /> ");
						poolIdMakerVO1.setPoolID((CommonFunction.checkNull(subList.get(0)).trim()));
						poolIdMakerVO1.setLoanID((CommonFunction.checkNull(subList.get(1)).trim()));
						poolIdMakerVO1.setLoanProduct((CommonFunction.checkNull(subList.get(3)).trim()));
						poolIdMakerVO1.setLoanScheme((CommonFunction.checkNull(subList.get(5)).trim()));
						poolIdMakerVO1.setLoanCustomerID((CommonFunction.checkNull(subList.get(6)).trim()));
						if(CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase("I"))
						     customerType.append("INDIVIDUAL");
				  	    else if(CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase("C"))
				  	       	 customerType.append("CORPORATE");	  
				  	    poolIdMakerVO1.setLoanCustomerType((CommonFunction.checkNull(customerType)));
					    poolIdMakerVO1.setLoanCustomerCtegory((CommonFunction.checkNull(subList.get(8)).trim()));
					    poolIdMakerVO1.setLoanCustomerConstituion((CommonFunction.checkNull(subList.get(9)).trim()));
					    poolIdMakerVO1.setLoanCustomerBusinessSeg((CommonFunction.checkNull(subList.get(10)).trim()));
					    poolIdMakerVO1.setLoanIndustry((CommonFunction.checkNull(subList.get(12)).trim()));
					    poolIdMakerVO1.setLoanSubIndustry((CommonFunction.checkNull(subList.get(14)).trim()));
					    poolIdMakerVO1.setLoanDisbursalDate((CommonFunction.checkNull(subList.get(15)).trim()));
					    poolIdMakerVO1.setLoanMaturityDate((CommonFunction.checkNull(subList.get(16)).trim()));
					    poolIdMakerVO1.setLoanTenure((CommonFunction.checkNull(subList.get(17)).trim()));
					    poolIdMakerVO1.setLoanBalanceTenure((CommonFunction.checkNull(subList.get(18)).trim()));
					    poolIdMakerVO1.setLoanInstallmentNum((CommonFunction.checkNull(subList.get(19)).trim()));
					    poolIdMakerVO1.setLoanAdvEMINUm((CommonFunction.checkNull(subList.get(20)).trim()));
					    poolIdMakerVO1.setLoanInitRate((CommonFunction.checkNull(subList.get(21)).trim()));
					    if(CommonFunction.checkNull(subList.get(22)).trim().equalsIgnoreCase("N"))
		    	              disbursalStatus.append("NO DISBURSAL");
			    	    else if(CommonFunction.checkNull(subList.get(22)).trim().equalsIgnoreCase("P"))
					       	  disbursalStatus.append("PARTIAL");	  
			    	    else if(CommonFunction.checkNull(subList.get(22)).trim().equalsIgnoreCase("F"))
		 	               	 disbursalStatus.append("FULLY");	  
		    	    	poolIdMakerVO1.setLoanDisbursalStatus((CommonFunction.checkNull(disbursalStatus)));
					    poolIdMakerVO1.setLoanNPAFlag((CommonFunction.checkNull(subList.get(23)).trim()));
					    poolIdMakerVO1.setLoanDPD((CommonFunction.checkNull(subList.get(24)).trim()));
					    poolIdMakerVO1.setLoanDPDString((CommonFunction.checkNull(subList.get(25)).trim()));
					    poolIdMakerVO1.setLoanAssetCost((CommonFunction.checkNull(subList.get(26)).trim()));
					    poolIdMakerVO1.setLoanAmount((CommonFunction.checkNull(subList.get(27)).trim()));
					    poolIdMakerVO1.setLoanEMI((CommonFunction.checkNull(subList.get(28)).trim()));
					    poolIdMakerVO1.setLoanAdvEMIAmount((CommonFunction.checkNull(subList.get(29)).trim()));
					    poolIdMakerVO1.setLoanBalPrincipal((CommonFunction.checkNull(subList.get(30)).trim()));
					    poolIdMakerVO1.setLoanOverduePrincipal((CommonFunction.checkNull(subList.get(31)).trim()));
					    poolIdMakerVO1.setLoanReceivedPrincipal((CommonFunction.checkNull(subList.get(32)).trim()));
					    poolIdMakerVO1.setLoanOverdueInstNo((CommonFunction.checkNull(subList.get(33)).trim()));
					    poolIdMakerVO1.setLoanOverdueAmount((CommonFunction.checkNull(subList.get(34)).trim()));
					    poolIdMakerVO1.setLoanBalnceInstNo((CommonFunction.checkNull(subList.get(35)).trim()));
					    poolIdMakerVO1.setLoanBalInstlAmount((CommonFunction.checkNull(subList.get(36)).trim()));
					    poolIdMakerVO1.setTotalRecordSize(count);
					   	mainList.add(poolIdMakerVO1);
					}
					disbursalStatus=null;
					customerType=null;
				}			
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				customerType=null;
				disbursalStatus=null;
				
			}				
			return mainList;
	}
	 
	 public	 ArrayList<PoolIdMakerVO>savedPoolIdMakerData(PoolIdMakerVO poolIdMakerVO,String poolID){
		 logger.info("In savedPoolIdMakerData() method of PoolIDDAOImpl");

			 ArrayList<PoolIdMakerVO> savedPoolListGrid=new ArrayList<PoolIdMakerVO>();
			//CallableStatement cst=null;
			//Connection con=ConnectionUploadDAO.getConnection();
		 		try{
		 			ArrayList mainList=new ArrayList ();
		 			ArrayList subList =new ArrayList();
		 		
		 					
		 		StringBuilder query=new StringBuilder();
		 			 
					       query.append("select P.pool_id,P.pool_name,DATE_FORMAT(P.POOL_CREATION_DATE,'"+dateFormat+"'),DATE_FORMAT(P.POOL_CUTOFF_DATE,'"+dateFormat+"'),P.POOL_TYPE,A.AGENCY_CODE,A.AGENCY_NAME" +
					  		" from cr_securitization_pool_m P,com_agency_m A where P.POOL_INSTITUTION_ID=A.AGENCY_CODE  " +    
					  		" AND POOL_ID='"+(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolID).trim()))+"'" +
					  		" and P.rec_status='P' ");
					       


		 			logger.info("In savedPoolIdMakerData from cr_securitization_pool_m:-"+query);	
		 			
		 			mainList=ConnectionUploadDAO.sqlSelect(query.toString());
		 			query=null;	
		 			
		 			for(int i=0;i<mainList.size();i++)
		 			{
		 				subList= (ArrayList)mainList.get(i);
		 				if(subList.size()>0){
		 					logger.info("Inside loop in savedPoolIdMakerData:-");	
		 					PoolIdMakerVO VO = new PoolIdMakerVO();	
		 					VO.setPoolID((CommonFunction.checkNull(subList.get(0)).trim()));
	             	    	VO.setPoolName((CommonFunction.checkNull(subList.get(1)).trim()));          	             	    	
	             	    	VO.setPoolCreationDate((CommonFunction.checkNull(subList.get(2)).trim())); 
	                 	    logger.info(" savedPoolIdMakerData setPoolCreationDate:-------------"+subList.get(2));
	                 	    VO.setCutOffDate((CommonFunction.checkNull(subList.get(3)).trim())); 
	                 	    VO.setPoolType((CommonFunction.checkNull(subList.get(4)).trim()));
	                 	    VO.setLbxinstituteID((CommonFunction.checkNull(subList.get(5)).trim()));
	                 	   logger.info(" savedPoolIdMakerData setLbxinstituteID:-------------"+subList.get(5));
	                 	    VO.setInstituteID((CommonFunction.checkNull(subList.get(6)).trim()));
	                 	 savedPoolListGrid.add(VO);
		 				}
		 			}
		 			
		 		}catch(Exception e){
		 					e.printStackTrace();
		 				}


		 				return savedPoolListGrid;
		 			
	 }
	 
	 public boolean insertLoanforPoolId(PoolIdMakerVO poolIdMakerVO, String poolID){
		 
		 logger.info("In insertLoanforPoolId() method of PoolIDDAOImpl");
		 
			
			
			ArrayList qryList =  new ArrayList();
			boolean status=false;
			StringBuilder bufInsSql =new StringBuilder();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
			logger.info("In--insertLoanforPoolId");
			try 
		    { 
		    
		   	 
		bufInsSql.append("insert into cr_securitization_pool_dtl(POOL_ID,LOAN_ID,LOAN_PRODUCT,LOAN_SCHEME,LOAN_CUSTOMER_ID,LOAN_CUSTOMER_TYPE,LOAN_CUSTOMER_CATEGORY,");
		bufInsSql.append("LOAN_CUSTOMER_CONSTITUTION,LOAN_CUSTOMER_BUSINESS_SEGMENT,LOAN_INDUSTRY,LOAN_SUB_INDUSTRY,LOAN_DISBURSAL_DATE, ");	
		bufInsSql.append("LOAN_MATURITY_DATE,LOAN_TENURE,LOAN_BALANCE_TENURE,LOAN_INSTL_NUM,LOAN_ADV_EMI_NUM,LOAN_INT_RATE,");
		bufInsSql.append("LOAN_DISBURSAL_STATUS,LOAN_NPA_FLAG,LOAN_DPD,LOAN_DPD_STRING,LOAN_ASSET_COST,LOAN_AMOUNT,LOAN_EMI,");
		bufInsSql.append("LOAN_ADV_EMI_AMOUNT,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,LOAN_RECEIVED_PRINCIPAL,LOAN_OVERDUE_INSTL_NUM,");
		bufInsSql.append("LOAN_OVERDUE_AMOUNT,LOAN_BALANCE_INSTL_NUM,LOAN_BALANCE_INSTL_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)");


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
			bufInsSql.append(" ?," ); 
			bufInsSql.append(" 'P'," ); 
			bufInsSql.append(" ?," ); 
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" ); //maker_date

			
							

							 if(CommonFunction.checkNull(poolID).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolID.trim());
							 
								if(CommonFunction.checkNull(poolIdMakerVO.getLbxLoanNoHID()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLbxLoanNoHID().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanProduct()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanProduct().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanScheme()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanScheme().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanCustomerID()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanCustomerID().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanCustomerType()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanCustomerType().trim());
								
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanCustomerCtegory()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanCustomerCtegory().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanCustomerConstituion()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanCustomerConstituion().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanCustomerBusinessSeg()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanCustomerBusinessSeg().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanIndustry()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanIndustry().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanSubIndustry()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanSubIndustry().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanDisbursalDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanDisbursalDate().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanMaturityDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanMaturityDate().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanTenure()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanTenure().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanBalanceTenure()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanBalanceTenure().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanInstallmentNum()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanInstallmentNum().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanAdvEMINUm()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanAdvEMINUm().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanInitRate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanInitRate().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanDisbursalStatus()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanDisbursalStatus().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanNPAFlag()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanNPAFlag().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanDPD()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanDPD().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanDPDString()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanDPDString().trim());
								
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanAssetCost()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanAssetCost().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanAmount()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanAmount().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanEMI()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanEMI().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanAdvEMIAmount()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanAdvEMIAmount().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanBalPrincipal()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanBalPrincipal().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanOverduePrincipal()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanOverduePrincipal().trim());
								

								if(CommonFunction.checkNull(poolIdMakerVO.getLoanReceivedPrincipal()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanReceivedPrincipal().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanOverdueInstNo()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanOverdueInstNo().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanOverdueAmount()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanOverdueAmount().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanBalnceInstNo()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanBalnceInstNo().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLoanBalInstlAmount()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLoanBalInstlAmount().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getMakerID()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getMakerID().trim());
								
								
								if(CommonFunction.checkNull(poolIdMakerVO.getMakerDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getMakerDate().trim());
								
								
								insertPrepStmtObject.setSql(bufInsSql.toString());
								qryList.add(insertPrepStmtObject);
		                logger.info(""+insertPrepStmtObject.printQuery());
					
			              status=ConnectionUploadDAO.sqlInsUpdDeletePrepStmt(qryList);
			          	bufInsSql=null;
			
		                 logger.info("In insertDataforPoolId."+status);

			}catch(Exception e){
				e.printStackTrace();
			}
finally
{
	bufInsSql=null;
	
}
			
			return status;

	 }
//	 
//	 public boolean poolUpload(HttpServletRequest request, FormFile myFile) 
//	 {
//		logger.info("In poolUpload() method of PoolIDDAOImpl");
//		boolean status=false;
//		String fileName="";
//		String filePath="";
//		String message="";
//		try
//		{
//				String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='POOL_CREATION_UPLOAD'";
//				logger.info("Query for getting path Query  :  "+query);
//				String rpt=ConnectionUploadDAO.singleReturn(query);
//				File directory = new File(rpt+"/");
//				boolean isDirectory =false;
//				 isDirectory= directory.isDirectory();
//				logger.info("The name you have entered is a true or false  : "  +isDirectory);
//				if(isDirectory && myFile!=null )
//				{
//					fileName    = myFile.getFileName();
//					logger.info("myFile--:::::>>>>>> "+myFile);					
//					logger.info("fileName--:::::>>>>>> "+fileName);
//				}
//				logger.info("directory does not exist:::::>>>>>> "+isDirectory);
//				//Get the servers upload directory real path name
//		        filePath = directory.getAbsolutePath();
//		        logger.info("filePath--"+filePath);
//		        /* Save file on the server */
//		        if(!fileName.equals(""))
//		        {  
//		        	logger.info("Server path:" +filePath);
//		        	//Create file
//		        	File fileToCreate = new File(filePath, fileName);
//		        	int fileSize = myFile.getFileSize(); //1048576 bytes = 1 MB
//		        	logger.info("Size of uploaded file= "+fileSize);
//		        	if(fileSize<1048576*2)
//		        	{
//			        	FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
//		                fileOutStream.write(myFile.getFileData());
//		        		fileOutStream.flush();
//		        		fileOutStream.close();
//		        		message="O";
//		        		status=true;	        		
//		        	}
//		        	else
//		        	{
//		        		message="E";
//		        		//logger.info("File size exceeds the upper limit of 1 MB.");
//				       	status=false;
//		        	}
//		        }
//		        else
//		        {
//		        	message="S";
//		        	//logger.info("Please select a File.");
//		        	status=false;
//		        }
//		        
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			request.setAttribute("fileName", fileName);
//			request.setAttribute("filePath", filePath);
//			request.setAttribute("message",message);
//		
//		fileName=null;
//		filePath=null;
//		message=null;
//			
//			return status;		
//		}
	 public ArrayList retriveValueByLoanforPool(String lbxLoanNoHID)
	 {
		 logger.info("In retriveValueByLoanforPool() method of PoolIDDAOImpl");

			ArrayList getDataList=new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
			 logger.info("In addPoolData DAOImpl......................");
			 ArrayList arrList = new ArrayList();
			 ArrayList subList =new ArrayList();
			
				try{

					StringBuilder query=new StringBuilder();
					 query.append("SELECT distinct A.loan_id,A.LOAN_ID," +
							" LOAN_PRODUCT,LOAN_SCHEME,LOAN_CUSTOMER_ID,LOAN_CUSTOMER_TYPE,C.CUSTOMER_CATEGORY,C.CUSTOMER_CONSTITUTION," +
							" C.CUSTOMER_BUSINESS_SEGMENT,LOAN_INDUSTRY_ID," +
							" LOAN_SUB_INDUSTRY_ID,LOAN_DISBURSAL_DATE,LOAN_MATURITY_DATE," +					
							" LOAN_TENURE," +
							" (SELECT COUNT(1) FROM cr_repaysch_dtl WHERE LOAN_ID = A.LOAN_ID AND BILL_FLAG='N')AS REMAINING_TENURE," +
							" LOAN_NO_OF_INSTALLMENT,LOAN_ADVANCE_INSTL,LOAN_FINAL_RATE, DISBURSAL_STATUS,NPA_FLAG,LOAN_DPD,LOAN_DPD_STRING,LOAN_ASSET_COST,LOAN_LOAN_AMOUNT," +
							" LOAN_EMI_AMOUNT,(select SUM(INSTL_AMOUNT) from cr_repaysch_dtl A,cr_loan_dtl B WHERE A.LOAN_ID=1 AND A.LOAN_ID=B.LOAN_ID "+
		                    " AND (ADV_FLAG='Y' OR (INSTL_NO=1 AND B.LOAN_INSTALLMENT_MODE='A')))AS LoanAdvanceEMIAmount ,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL," +
		                    " LOAN_RECEIVED_PRINCIPAL,LOAN_OVERDUE_INSTL_NUM," +
							" LOAN_OVERDUE_AMOUNT,(LOAN_NO_OF_INSTALLMENT-LOAN_BILLED_INSTL_NUM) AS LOAN_BALANCE_INSTL_NO,LOAN_BALANCE_INSTL_AMOUNT" +
							" from cr_loan_dtl A,cr_repaysch_dtl R,gcd_customer_m C  " +
							" WHERE R.LOAN_ID = A.LOAN_ID " +
							" AND A.LOAN_CUSTOMER_ID = C.CUSTOMER_ID " +
							" AND A.LOAN_ID ="+lbxLoanNoHID+"");
				
					
					logger.info(query);
					arrList = ConnectionUploadDAO.sqlSelect(query.toString());
					
					query=null;
					
					for(int i=0;i<arrList.size();i++){

						subList=(ArrayList)arrList.get(i);
						logger.info("In getPoolData..."+subList.size());

							if(subList.size()>0){

								    PoolIdMakerVO poolIdMakerVO1=new PoolIdMakerVO();
								    poolIdMakerVO1.setLoanID((CommonFunction.checkNull(subList.get(1)).trim()));
								    poolIdMakerVO1.setLoanProduct((CommonFunction.checkNull(subList.get(2)).trim()));
								    poolIdMakerVO1.setLoanScheme((CommonFunction.checkNull(subList.get(3)).trim()));
								    poolIdMakerVO1.setLoanCustomerID((CommonFunction.checkNull(subList.get(4)).trim()));
								    poolIdMakerVO1.setLoanCustomerType((CommonFunction.checkNull(subList.get(5)).trim()));
								    poolIdMakerVO1.setLoanCustomerCtegory((CommonFunction.checkNull(subList.get(6)).trim()));
								    poolIdMakerVO1.setLoanCustomerConstituion((CommonFunction.checkNull(subList.get(7)).trim()));
								    poolIdMakerVO1.setLoanCustomerBusinessSeg((CommonFunction.checkNull(subList.get(8)).trim()));
								    poolIdMakerVO1.setLoanIndustry((CommonFunction.checkNull(subList.get(9)).trim()));
								    poolIdMakerVO1.setLoanSubIndustry((CommonFunction.checkNull(subList.get(10)).trim()));
								    poolIdMakerVO1.setLoanDisbursalDate((CommonFunction.checkNull(subList.get(11)).trim()));
								    poolIdMakerVO1.setLoanMaturityDate((CommonFunction.checkNull(subList.get(12)).trim()));
								    poolIdMakerVO1.setLoanTenure((CommonFunction.checkNull(subList.get(13)).trim()));
								    poolIdMakerVO1.setLoanBalanceTenure((CommonFunction.checkNull(subList.get(14)).trim()));
								    poolIdMakerVO1.setLoanInstallmentNum((CommonFunction.checkNull(subList.get(15)).trim()));
								    poolIdMakerVO1.setLoanAdvEMINUm((CommonFunction.checkNull(subList.get(16)).trim()));
								    poolIdMakerVO1.setLoanInitRate((CommonFunction.checkNull(subList.get(17)).trim()));
								    poolIdMakerVO1.setLoanDisbursalStatus((CommonFunction.checkNull(subList.get(18)).trim()));
								    poolIdMakerVO1.setLoanNPAFlag((CommonFunction.checkNull(subList.get(19)).trim()));
								    poolIdMakerVO1.setLoanDPD((CommonFunction.checkNull(subList.get(20)).trim()));
								    poolIdMakerVO1.setLoanDPDString((CommonFunction.checkNull(subList.get(21)).trim()));
								    poolIdMakerVO1.setLoanAssetCost((CommonFunction.checkNull(subList.get(22)).trim()));
								    poolIdMakerVO1.setLoanAmount((CommonFunction.checkNull(subList.get(23)).trim()));
								    poolIdMakerVO1.setLoanEMI((CommonFunction.checkNull(subList.get(24)).trim()));
								    poolIdMakerVO1.setLoanAdvEMIAmount((CommonFunction.checkNull(subList.get(25)).trim()));
								    poolIdMakerVO1.setLoanBalPrincipal((CommonFunction.checkNull(subList.get(26)).trim()));
								    poolIdMakerVO1.setLoanOverduePrincipal((CommonFunction.checkNull(subList.get(27)).trim()));
								    poolIdMakerVO1.setLoanReceivedPrincipal((CommonFunction.checkNull(subList.get(28)).trim()));
								    poolIdMakerVO1.setLoanOverdueInstNo((CommonFunction.checkNull(subList.get(29)).trim()));
								    poolIdMakerVO1.setLoanOverdueAmount((CommonFunction.checkNull(subList.get(30)).trim()));
								    poolIdMakerVO1.setLoanBalnceInstNo((CommonFunction.checkNull(subList.get(31)).trim()));
								    poolIdMakerVO1.setLoanBalInstlAmount((CommonFunction.checkNull(subList.get(32)).trim()));
								     
								    
								    getDataList.add(poolIdMakerVO1);
							
							}
							}
					
						
					}catch(Exception e){
						logger.debug("IOException In getPoolData() ==>> "+e.getMessage());
					}
					
						
					return getDataList;
		}	 
	 public int countPoolLine(PoolIdMakerVO poolIdMakerVO)
	 {
		 logger.info("In countPoolLine() method of PoolIDDAOImpl");
		 int rowTotalNum=0;
		 String fileName = "";
		 try
		 {
			 String rpt=ConnectionUploadDAO.singleReturn("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'");		
			 logger.info("File Name......... ==>>"+fileName);
			 logger.info("File Name. poolIdMakerVO.getFileName()........ ==>>"+poolIdMakerVO.getFileName());
			 logger.info("File directory. getFilePathWithName........ ==>>"+poolIdMakerVO.getFilePathWithName());
//			 File directory = new File(rpt+"/"+fileName);
			 File directory = new File(poolIdMakerVO.getFilePathWithName());
			 logger.info("File directory......... ==>>"+directory);
			 logger.info("directory.exists()  :  "+directory.exists());
			 if(directory.exists())
		     {
				 logger.info("in method of file....................."+directory.length());
				 if(directory.length()>0)
				 {
					 Workbook workbook = Workbook.getWorkbook(directory);			 
					 logger.info("in method of file.....................");
					 Sheet sheet;
					 sheet = workbook.getSheet(0);
					 rowTotalNum=sheet.getRows();
					 logger.info("Total Lines............. saurabh==>>"+rowTotalNum);
				 }
				 else
				 {
					 rowTotalNum=-1;
					 logger.info("singh singh +++++++++++............. saurabh==>>"+rowTotalNum);
				 }
		      }
		      else
		      {
		    	  rowTotalNum=-2;
		    	  logger.info("File not exitxt..");

		      }
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			  return rowTotalNum;
		}
/*	 
public boolean csvRead(HttpServletRequest request,HttpServletResponse response,PoolIdMakerVO poolIdMakerVO, String file,String poolID) throws SQLException 
{
	logger.info("In csvRead() method of PoolIDDAOImpl");
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	StringBuilder s1=new StringBuilder();
	StringBuilder s2=new StringBuilder();
	StringBuilder s3=new StringBuilder();
    
	HttpSession   session		  = null;
    String        makerID	 		= null;
    StringBuffer  sql         = null;
    ArrayList     alFinalQry  = new ArrayList(1);
    ArrayList     alDeleteQuery  = new ArrayList(1);
    int           rowTotalNum = 0;
    int           row         = 0;
    int           col         = 0;
    String[][]    rowVal      = new String[1000][40];
    String        line        = null;
    String        msg         = "";
    String errorMsgSpace      = null;
    String errorMsgHeader     = null;
    String excep              = "";     
    boolean singleObject;
    File          objFile1		= null;      
    uploadVO vo=new uploadVO();
    BufferedReader bufRdr;
    boolean status=false;
    int intRem=0;
    StringBuffer bufInsSql = new StringBuffer();
    String message="";
    ArrayList arr =null;
    ArrayList subList =new ArrayList();

     try 
     {
    	 PrepStmtObject insertPrepStmtObject=null;
    	 session     = request.getSession(false);
    	 UserObject userobj=(UserObject)session.getAttribute("userobject");
	     String userId=userobj.getUserId();
	     int companyId=userobj.getCompanyId();
	     makerID=CommonFunction.checkNull(userId);
		 vo.setMakerId(""+userId);
		 vo.setMakerDate(userobj.getBusinessdate());
		 logger.info("File Name in Process.."+file);
		 userId=null;
         logger.info("poolIdMakerVO.getPoolName()........."+ poolIdMakerVO.getPoolName());	
		 logger.info("getPoolCreationDate()........."+ poolIdMakerVO.getPoolCreationDate());	
		 logger.info("getCutOffDate()........."+ poolIdMakerVO.getCutOffDate());	
		 logger.info("getPoolType()........."+ poolIdMakerVO.getPoolType());	
		 logger.info("getLbxinstituteID()........."+ poolIdMakerVO.getLbxinstituteID());	
     
         if(!file.equals(""))
         {
        	 logger.info(" strFileName is ==>>"+file);
        	 String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='POOL_CREATION_UPLOAD'";			
        	 String strPath=ConnectionUploadDAO.singleReturn(query);
        	 strPath     = strPath + "/" + file;
        	 
        	 objFile1    = new File(strPath);
        	 logger.info(" strFile path... ==>>"+objFile1);
        	 logger.info(" strFileName < 60 ==>> "+file!=null?file:file.length()+"("+file.length()+")");
        	 ArrayList dataList=null;
        	 try
        	 {
        		//Deleting data..start....     
        		String strDeleteQuery = "DELETE FROM pool_creation_update_tmp WHERE MAKER_ID = '"+vo.getMakerId()+"' ";
        		alDeleteQuery.add(strDeleteQuery);
        		boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
        		logger.info(" Deleting query is..... ==>>"+status1);
        		if(status1)
        		 logger.info("Row is deleted....");
        		else
        			 logger.info("Row is not deleted......");
        		
        		dataList=new ArrayList();
        		Workbook workbook = Workbook.getWorkbook(new File(strPath));
        		//String sheetName[]=workbook.getSheetNames();
        		Sheet sheet;
        		Cell xlsCell;
        		Cell[] cell;
        	    int p=0;
                sheet = workbook.getSheet(p);
                int rows=sheet.getRows();
                logger.info("value of sheet rows   : "+rows);
                for(int i=0; i<rows; i++)
                {
             	  cell = sheet.getRow(i);
             	  arr = new ArrayList();
                  for(int j=0; j<cell.length; j++)
                  {
                 	xlsCell = sheet.getCell(j,i);
                    arr.add(xlsCell.getContents().toString());
                  }  
                  dataList.add(arr);	 
               }           
        	 }
        	 catch(Exception exec)
        	 {
        		 exec.fillInStackTrace();
        	 }    
        	 
        	 //insert start.............

        	 try 
        	 {      	 
        		String LOAN_ID="";
				String LOAN_PRODUCT="";
				String LOAN_SCHEME="";
				String LOAN_CUSTOMER_ID="";
				String LOAN_CUSTOMER_TYPE="";
				String LOAN_CUSTOMER_CATEGORY="";
				String LOAN_CUSTOMER_CONSTITUTION="";
				String LOAN_CUSTOMER_BUSINESS_SEGMENT="";
				String LOAN_INDUSTRY="";
				String LOAN_SUB_INDUSTRY="";
				String LOAN_MATURITY_DATE="";
				String LOAN_DISBURSAL_STATUS="";
				String LOAN_NPA_FLAG="";
				String LOAN_BRANCH="";
        		 
        		 bufInsSql.append("insert into pool_creation_update_tmp( " +
        		 		"SEQ_NO,POOL_ID,LOAN_ID,LOAN_PRODUCT,LOAN_SCHEME,LOAN_CUSTOMER_ID,LOAN_CUSTOMER_TYPE,LOAN_CUSTOMER_CATEGORY,LOAN_CUSTOMER_CONSTITUTION,LOAN_CUSTOMER_BUSINESS_SEGMENT," +
        		 		"LOAN_INDUSTRY,LOAN_SUB_INDUSTRY,LOAN_DISBURSAL_DATE,LOAN_MATURITY_DATE,LOAN_TENURE,LOAN_BALANCE_TENURE,LOAN_INSTL_NUM,LOAN_ADV_EMI_NUM,LOAN_INT_RATE,LOAN_DISBURSAL_STATUS," +
        		 		"LOAN_NPA_FLAG,LOAN_DPD,LOAN_DPD_STRING,LOAN_ASSET_COST,LOAN_AMOUNT,LOAN_EMI,LOAN_ADV_EMI_AMOUNT,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,LOAN_RECEIVED_PRINCIPAL," +
        		 		"LOAN_OVERDUE_INSTL_NUM,LOAN_OVERDUE_AMOUNT,LOAN_BALANCE_INSTL_NUM,LOAN_BALANCE_INSTL_AMOUNT,ADDRESS,NATURE_OF_ASSET,ASSET_DESCRIPTION,ASSET_MANUFACTURER,NEW_USED,MODE_OF_EMI," +
        		 		"NO_OF_PDC_OPTAINED,INSTALLMENT_PATTERN,APPLCT_FIN_DOCS,GUR_FIN_DOCS,SECTOR_TYPE,APPL_KYC_ADDR_PROOF,APPL_KYC_ID_PROOF,APPL_KYC_SIGN_PROOF,GUR_KYC_ADDR_PROOF,GUR_KYC_ID_PROOF," +
        		 		"GUR_KYC_SIGN_PROOF,FIRST_EMI_DATE,LAST_EMI_DATE,FIRST_EMI_MONTH,LAST_EMI_MONTH,SEASONING,EMI_AMT,LOAN_STATUS,LOAN_BRANCH,ADVANCE_EMI," +
        		 		"SD_AMOUNT,PENDING_SD_AMOUNT,UNDISBURSED,AGREED_VALUE,RECVD_INT_AMT_TILL_DATE,UPLOADED_FLAG,REJECT_REASON,REC_STATUS,MAKER_ID,MAKER_DATE) " +
        		 		"values(");
        		 bufInsSql.append(" ?," );//SEQ_NO
        		 bufInsSql.append(" ?," );//POOL_ID
        		 bufInsSql.append(" ?," );//LOAN_ID
        		 bufInsSql.append(" ?," );//LOAN_PRODUCT
        		 bufInsSql.append(" ?," );//LOAN_SCHEME
        		 bufInsSql.append(" ?," );//LOAN_CUSTOMER_ID
        		 bufInsSql.append(" ?," );//LOAN_CUSTOMER_TYPE
        		 bufInsSql.append(" ?," );//LOAN_CUSTOMER_CATEGORY
        		 bufInsSql.append(" ?," );//LOAN_CUSTOMER_CONSTITUTION        		
        		 bufInsSql.append(" ?," );//LOAN_CUSTOMER_BUSINESS_SEGMENT  
        		 
        		 bufInsSql.append(" ?," );//LOAN_INDUSTRY	
        		 bufInsSql.append(" ?," );//LOAN_SUB_INDUSTRY	
        		 bufInsSql.append(" ?," );//LOAN_DISBURSAL_DATE
        		 bufInsSql.append(" ?," );//LOAN_MATURITY_DATE
        		 bufInsSql.append(" ?," );//LOAN_TENURE        		 
        		 bufInsSql.append(" ?," );//LOAN_BALANCE_TENURE
        		 bufInsSql.append(" ?," );//LOAN_INSTL_NUM
        		 bufInsSql.append(" ?," );//LOAN_ADV_EMI_NUM
        		 bufInsSql.append(" ?," );//LOAN_INT_RATE
        		 bufInsSql.append(" ?," );//LOAN_DISBURSAL_STATUS        		 
        		 bufInsSql.append(" ?," );//LOAN_NPA_FLAG
        		 bufInsSql.append(" ?," );//LOAN_DPD        		 
        		 bufInsSql.append(" ?," );//LOAN_DPD_STRING
        		 bufInsSql.append(" ?," );//LOAN_ASSET_COST
        		 bufInsSql.append(" ?," );//LOAN_AMOUNT
        		 bufInsSql.append(" ?," );//LOAN_EMI
        		 bufInsSql.append(" ?," );//LOAN_ADV_EMI_AMOUNT
        		 bufInsSql.append(" ?," );//LOAN_BALANCE_PRINCIPAL        		 
        		 bufInsSql.append(" ?," );//LOAN_OVERDUE_PRINCIPAL        		 
        		 bufInsSql.append(" ?," );//LOAN_RECEIVED_PRINCIPAL        		 
        		 bufInsSql.append(" ?," );//LOAN_OVERDUE_INSTL_NUM
        		 bufInsSql.append(" ?," );//LOAN_OVERDUE_AMOUNT        		 
        		 bufInsSql.append(" ?," );//LOAN_BALANCE_INSTL_NUM
        		 bufInsSql.append(" ?," );//LOAN_BALANCE_INSTL_AMOUNT
        		 bufInsSql.append(" ?," );//ADDRESS
        		 bufInsSql.append(" ?," );//NATURE_OF_ASSET
        		 bufInsSql.append(" ?," );//ASSET_DESCRIPTION
        		 bufInsSql.append(" ?," );//ASSET_MANUFACTURER
        		 bufInsSql.append(" ?," );//NEW_USED
        		 bufInsSql.append(" ?," );//MODE_OF_EMI        		 
        		 bufInsSql.append(" ?," );//NO_OF_PDC_OPTAINED           		 
        		 bufInsSql.append(" ?," );//INSTALLMENT_PATTERN        		 
        		 bufInsSql.append(" ?," );//APPLCT_FIN_DOCS
        		 bufInsSql.append(" ?," );//GUR_FIN_DOCS
        		 bufInsSql.append(" ?," );//SECTOR_TYPE
        		 bufInsSql.append(" ?," );//APPL_KYC_ADDR_PROOF
        		 bufInsSql.append(" ?," );//APPL_KYC_ID_PROOF
        		 bufInsSql.append(" ?," );//APPL_KYC_SIGN_PROOF
        		 bufInsSql.append(" ?," );//GUR_KYC_ADDR_PROOF
        		 bufInsSql.append(" ?," );//GUR_KYC_ID_PROOF        		 
        		 bufInsSql.append(" ?," );//GUR_KYC_SIGN_PROOF        		 
        		 bufInsSql.append(" ?," );//FIRST_EMI_DATE        		 
        		 bufInsSql.append(" ?," );//LAST_EMI_DATE
        		 bufInsSql.append(" ?," );//FIRST_EMI_MONTH
        		 bufInsSql.append(" ?," );//LAST_EMI_MONTH
        		 bufInsSql.append(" ?," );//SEASONING
        		 bufInsSql.append(" ?," );//EMI_AMT
        		 bufInsSql.append(" ?," );//LOAN_STATUS
        		 bufInsSql.append(" ?," );//LOAN_BRANCH
        		 bufInsSql.append(" ?," );//ADVANCE_EMI        		 
        		 bufInsSql.append(" ?," );//SD_AMOUNT        		 
        		 bufInsSql.append(" ?," );//PENDING_SD_AMOUNT        		 
        		 bufInsSql.append(" ?," );//UNDISBURSED
        		 bufInsSql.append(" ?," );//AGREED_VALUE
        		 bufInsSql.append(" ?," );//RECVD_INT_AMT_TILL_DATE
        		 bufInsSql.append(" ?," );//UPLOADED_FLAG
        		 bufInsSql.append(" ?," );//REJECT_REASON        		 
        		 bufInsSql.append("'P',");//REC_STATUS
        		 bufInsSql.append(" ?," );//MAKER_ID
        		 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'))" );//maker_date

        		int arrSize=0;
				if(dataList!=null)
					arrSize=dataList.size();
				logger.info("array list size  :  "+arrSize);
				for(int i=1;i<arrSize;i++)
				{		
					subList=(ArrayList)dataList.get(i);
					if(subList.size()>0)
					{
						insertPrepStmtObject = new PrepStmtObject();
						
						LOAN_ID=CommonFunction.checkNull(subList.get(0));		
						ArrayList list =new ArrayList();
						String subQuery="select LOAN_PRODUCT,LOAN_SCHEME,LOAN_CUSTOMER_ID,LOAN_CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,LOAN_MATURITY_DATE,DISBURSAL_STATUS,NPA_FLAG,LOAN_BRANCH from cr_loan_dtl a join gcd_customer_m b on (a.LOAN_CUSTOMER_ID=b.CUSTOMER_ID) where a.loan_id="+LOAN_ID;
						logger.info("Query to get id of uploaded description : "+subQuery);
						list = ConnectionUploadDAO.sqlSelect(subQuery);
						ArrayList idList = new ArrayList();
						if(list.size()>0)
						{
							idList = (ArrayList) list.get(0);
							LOAN_PRODUCT=(String)idList.get(0);
							LOAN_SCHEME=(String)idList.get(1);
							LOAN_CUSTOMER_ID=(String)idList.get(2);
							LOAN_CUSTOMER_TYPE=(String)idList.get(3);
							LOAN_CUSTOMER_CONSTITUTION=(String)idList.get(4);
							LOAN_CUSTOMER_BUSINESS_SEGMENT=(String)idList.get(5);
							LOAN_INDUSTRY=(String)idList.get(6);
							LOAN_SUB_INDUSTRY=(String)idList.get(7);
							LOAN_MATURITY_DATE=(String)idList.get(8);
							LOAN_DISBURSAL_STATUS=(String)idList.get(9);
							LOAN_NPA_FLAG=(String)idList.get(10);
							LOAN_BRANCH=(String)idList.get(11);
						}
						list=null;
						idList=null;
						String LOAN_DISBURSAL_DATE=CommonFunction.changeFormat((String)subList.get(23));
						String LOAN_TENURE=CommonFunction.checkNull(subList.get(11));
						String LOAN_BALANCE_TENURE=CommonFunction.checkNull(subList.get(30));
						String LOAN_INSTL_NUM=CommonFunction.checkNull(subList.get(40));						
						String LOAN_ADV_EMI_NUM=CommonFunction.checkNull(subList.get(41));
						String LOAN_INT_RATE=CommonFunction.checkNull(subList.get(42));
						String LOAN_DPD=CommonFunction.checkNull(subList.get(46));
					    String LOAN_DPD_STRING=CommonFunction.checkNull(subList.get(47));					    
					    String LOAN_ASSET_COST="0";//asset cost not present
					    String LOAN_AMOUNT=CommonFunction.checkNull(subList.get(22));
					    String LOAN_EMI=CommonFunction.checkNull(subList.get(28));					    
					    String LOAN_ADV_EMI_AMOUNT=CommonFunction.checkNull(subList.get(48));
					    String LOAN_BALANCE_PRINCIPAL=CommonFunction.checkNull(subList.get(49));
					    String LOAN_OVERDUE_PRINCIPAL=CommonFunction.checkNull(subList.get(50));      
					    String LOAN_RECEIVED_PRINCIPAL=CommonFunction.checkNull(subList.get(51));   
					    String LOAN_OVERDUE_INSTL_NUM=CommonFunction.checkNull(subList.get(52));
					    String LOAN_OVERDUE_AMOUNT=CommonFunction.checkNull(subList.get(53));     
					    String LOAN_BALANCE_INSTL_NUM=CommonFunction.checkNull(subList.get(54));      
					    String LOAN_BALANCE_INSTL_AMOUNT=CommonFunction.checkNull(subList.get(55));  
					    
					    String ADDRESS=CommonFunction.checkNull(subList.get(4));  
					    String NATURE_OF_ASSET=CommonFunction.checkNull(subList.get(5)); 
					    String ASSET_DESCRIPTION=CommonFunction.checkNull(subList.get(6)); 					    
					    String ASSET_MANUFACTURER=CommonFunction.checkNull(subList.get(7));  
					    String NEW_USED=CommonFunction.checkNull(subList.get(8));  
					    String mode=CommonFunction.checkNull(subList.get(9)); 
					    String MODE_OF_EMI="";
					    if(CommonFunction.checkNull(mode).trim().equalsIgnoreCase("PDC"))
					    	MODE_OF_EMI = "P";
					    else if(CommonFunction.checkNull(mode).trim().equalsIgnoreCase("OTC"))
					    	MODE_OF_EMI = "O";
					    else if(CommonFunction.checkNull(mode).trim().equalsIgnoreCase("DIRECT DEBIT"))
					    	MODE_OF_EMI = "DIR";
					    else if(CommonFunction.checkNull(mode).trim().equalsIgnoreCase("ECS"))
					    	MODE_OF_EMI = "E";
					    else 
					    	MODE_OF_EMI = null;
				        					   					    
					    String NO_OF_PDC_OPTAINED=CommonFunction.checkNull(subList.get(10));  
					    
					    String pattern=CommonFunction.checkNull(subList.get(12)); 
					    String INSTALLMENT_PATTERN="";
					    if(CommonFunction.checkNull(pattern).trim().equalsIgnoreCase("EQUATED INSTALLMENT"))
					    	INSTALLMENT_PATTERN = "E";
					    else if(CommonFunction.checkNull(pattern).trim().equalsIgnoreCase("GRADED INSTALLMENT"))
					    	INSTALLMENT_PATTERN = "G";
					    else if(CommonFunction.checkNull(pattern).trim().equalsIgnoreCase("EQUATED PRINCIPAL"))
					    	INSTALLMENT_PATTERN = "P";
					    else if(CommonFunction.checkNull(pattern).trim().equalsIgnoreCase("GRADED PRINCIPAL1"))
					    	INSTALLMENT_PATTERN = "Q";
					    else if(CommonFunction.checkNull(pattern).trim().equalsIgnoreCase("GRADED PRINCIPAL2"))
					    	INSTALLMENT_PATTERN = "R";
					    else 
					    	INSTALLMENT_PATTERN = null;
					    
					    String APPLCT_FIN_DOCS=CommonFunction.checkNull(subList.get(13));  
					    String GUR_FIN_DOCS=CommonFunction.checkNull(subList.get(14));  
					    String SECTOR_TYPE=CommonFunction.checkNull(subList.get(15));  
					    String APPL_KYC_ADDR_PROOF=CommonFunction.checkNull(subList.get(16));  
					    String APPL_KYC_ID_PROOF=CommonFunction.checkNull(subList.get(17));  
					    String APPL_KYC_SIGN_PROOF=CommonFunction.checkNull(subList.get(18));  
					    String GUR_KYC_ADDR_PROOF=CommonFunction.checkNull(subList.get(19));  
					    String GUR_KYC_ID_PROOF=CommonFunction.checkNull(subList.get(20));  
					    String GUR_KYC_SIGN_PROOF=CommonFunction.checkNull(subList.get(21));  
					    String FIRST_EMI_DATE=CommonFunction.changeFormat((String)subList.get(24));  
					    String LAST_EMI_DATE=CommonFunction.changeFormat((String)subList.get(25));  
					    String FIRST_EMI_MONTH=CommonFunction.checkNull(subList.get(26));  
					    String LAST_EMI_MONTH=CommonFunction.checkNull(subList.get(27));  
					    String SEASONING=CommonFunction.checkNull(subList.get(29));  
					    String EMI_AMT=CommonFunction.checkNull(subList.get(31));  
					    //String LOAN_STATUS=CommonFunction.checkNull(subList.get(45));  
					    String LOAN_STATUS="A";  
					    String advanceEmi=CommonFunction.checkNull(subList.get(57));  
					    String ADVANCE_EMI="";
					    	
					    if(CommonFunction.checkNull(advanceEmi).trim().equalsIgnoreCase("Yes"))
					    	ADVANCE_EMI = "Y";
					    else if(CommonFunction.checkNull(advanceEmi).trim().equalsIgnoreCase("No"))
					    	ADVANCE_EMI = "N";
					    else
					    	ADVANCE_EMI=null;
					    
					    String SD_AMOUNT=CommonFunction.checkNull(subList.get(58));
					    String PENDING_SD_AMOUNT=CommonFunction.checkNull(subList.get(59));
					    String UNDISBURSED=CommonFunction.checkNull(subList.get(60));
					    String AGREED_VALUE=CommonFunction.checkNull(subList.get(61));
					    String RECVD_INT_AMT_TILL_DATE=CommonFunction.checkNull(subList.get(62));
					    String UPLOADED_FLAG=null;
					    String REJECT_REASON=null;
					      
					    String makerid=vo.getMakerId();
					    String makerdate=vo.getMakerDate(); 
						
						insertPrepStmtObject.addInt(i);//Seq No
						if(CommonFunction.checkNull(poolID).equalsIgnoreCase(""))//POOL_ID
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(poolID);
						
						if(CommonFunction.checkNull(LOAN_ID).equalsIgnoreCase(""))//LOAN_ID
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_ID);
						
						if(CommonFunction.checkNull(LOAN_PRODUCT).equalsIgnoreCase(""))//LOAN_PRODUCT
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_PRODUCT);
						
						if(CommonFunction.checkNull(LOAN_SCHEME).equalsIgnoreCase(""))//LOAN_SCHEME
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_SCHEME);
						
						if(CommonFunction.checkNull(LOAN_CUSTOMER_ID).equalsIgnoreCase(""))//LOAN_CUSTOMER_ID
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_CUSTOMER_ID);
						
						if(CommonFunction.checkNull(LOAN_CUSTOMER_TYPE).equalsIgnoreCase(""))//LOAN_CUSTOMER_TYPE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_CUSTOMER_TYPE);
						
						if(CommonFunction.checkNull(LOAN_CUSTOMER_CATEGORY).equalsIgnoreCase(""))//LOAN_CUSTOMER_CATEGORY
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_CUSTOMER_CATEGORY);
						
						if(CommonFunction.checkNull(LOAN_CUSTOMER_CONSTITUTION).equalsIgnoreCase(""))//LOAN_CUSTOMER_CONSTITUTION
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_CUSTOMER_CONSTITUTION);
						
						if(CommonFunction.checkNull(LOAN_CUSTOMER_BUSINESS_SEGMENT).equalsIgnoreCase(""))//LOAN_CUSTOMER_BUSINESS_SEGMENT
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_CUSTOMER_BUSINESS_SEGMENT);
						
						if(CommonFunction.checkNull(LOAN_INDUSTRY).equalsIgnoreCase(""))//LOAN_INDUSTRY
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_INDUSTRY);	
						
						if(CommonFunction.checkNull(LOAN_SUB_INDUSTRY).equalsIgnoreCase(""))//LOAN_SUB_INDUSTRY
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_SUB_INDUSTRY);
						
						if(CommonFunction.checkNull(LOAN_DISBURSAL_DATE).equalsIgnoreCase(""))//LOAN_DISBURSAL_DATE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_DISBURSAL_DATE);
						
						if(CommonFunction.checkNull(LOAN_MATURITY_DATE).equalsIgnoreCase(""))//LOAN_MATURITY_DATE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_MATURITY_DATE);
						
						if(CommonFunction.checkNull(LOAN_TENURE).equalsIgnoreCase(""))//LOAN_TENURE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_TENURE);	
						
						if(CommonFunction.checkNull(LOAN_BALANCE_TENURE).equalsIgnoreCase(""))//LOAN_BALANCE_TENURE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_BALANCE_TENURE);
						
						if(CommonFunction.checkNull(LOAN_INSTL_NUM).equalsIgnoreCase(""))//LOAN_INSTL_NUM
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_INSTL_NUM);
						
						if(CommonFunction.checkNull(LOAN_ADV_EMI_NUM).equalsIgnoreCase(""))//LOAN_ADV_EMI_NUM
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_ADV_EMI_NUM);
						
						if(CommonFunction.checkNull(LOAN_INT_RATE).equalsIgnoreCase(""))//LOAN_INT_RATE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_INT_RATE);	
						
						if(CommonFunction.checkNull(LOAN_DISBURSAL_STATUS).equalsIgnoreCase(""))//LOAN_DISBURSAL_STATUS
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_DISBURSAL_STATUS);
						
						if(CommonFunction.checkNull(LOAN_NPA_FLAG).equalsIgnoreCase(""))//LOAN_NPA_FLAG
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_NPA_FLAG);
						
						if(CommonFunction.checkNull(LOAN_DPD).equalsIgnoreCase(""))//LOAN_DPD
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_DPD);	
						if(CommonFunction.checkNull(LOAN_DPD_STRING).equalsIgnoreCase(""))//LOAN_DPD_STRING
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_DPD_STRING);
						
						if(CommonFunction.checkNull(LOAN_ASSET_COST).equalsIgnoreCase(""))//LOAN_ASSET_COST
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_ASSET_COST);
						
						if(CommonFunction.checkNull(LOAN_AMOUNT).equalsIgnoreCase(""))//LOAN_AMOUNT
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_AMOUNT);
						
						if(CommonFunction.checkNull(LOAN_EMI).equalsIgnoreCase(""))//LOAN_EMI
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_EMI);	
						
						if(CommonFunction.checkNull(LOAN_ADV_EMI_AMOUNT).equalsIgnoreCase(""))//LOAN_ADV_EMI_AMOUNT
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_ADV_EMI_AMOUNT);
						 
						if(CommonFunction.checkNull(LOAN_BALANCE_PRINCIPAL).equalsIgnoreCase(""))//LOAN_BALANCE_PRINCIPAL
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_BALANCE_PRINCIPAL);
						
						if(CommonFunction.checkNull(LOAN_OVERDUE_PRINCIPAL).equalsIgnoreCase(""))//LOAN_OVERDUE_PRINCIPAL
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_OVERDUE_PRINCIPAL);
						if(CommonFunction.checkNull(LOAN_RECEIVED_PRINCIPAL).equalsIgnoreCase(""))//LOAN_RECEIVED_PRINCIPAL
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_RECEIVED_PRINCIPAL);
						
						if(CommonFunction.checkNull(LOAN_OVERDUE_INSTL_NUM).equalsIgnoreCase(""))//LOAN_OVERDUE_INSTL_NUM
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_OVERDUE_INSTL_NUM);	
						
						if(CommonFunction.checkNull(LOAN_OVERDUE_AMOUNT).equalsIgnoreCase(""))//LOAN_OVERDUE_AMOUNT
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_OVERDUE_AMOUNT);
						 
						if(CommonFunction.checkNull(LOAN_BALANCE_INSTL_NUM).equalsIgnoreCase(""))//LOAN_BALANCE_INSTL_NUM
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_BALANCE_INSTL_NUM);
						
						if(CommonFunction.checkNull(LOAN_BALANCE_INSTL_AMOUNT).equalsIgnoreCase(""))//LOAN_BALANCE_INSTL_AMOUNT
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_BALANCE_INSTL_AMOUNT);	
					
						if(CommonFunction.checkNull(ADDRESS).equalsIgnoreCase(""))//ADDRESS
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(ADDRESS);
						
						if(CommonFunction.checkNull(NATURE_OF_ASSET).equalsIgnoreCase(""))//NATURE_OF_ASSET
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(NATURE_OF_ASSET);
						
						if(CommonFunction.checkNull(ASSET_DESCRIPTION).equalsIgnoreCase(""))//ASSET_DESCRIPTION
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(ASSET_DESCRIPTION);
						
						if(CommonFunction.checkNull(ASSET_MANUFACTURER).equalsIgnoreCase(""))//ASSET_MANUFACTURER
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(ASSET_MANUFACTURER);
						
						if(CommonFunction.checkNull(NEW_USED).equalsIgnoreCase(""))//NEW_USED
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(NEW_USED);
						
						if(CommonFunction.checkNull(MODE_OF_EMI).equalsIgnoreCase(""))//MODE_OF_EMI
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(MODE_OF_EMI);
						
						if(CommonFunction.checkNull(NO_OF_PDC_OPTAINED).equalsIgnoreCase(""))//NO_OF_PDC_OPTAINED
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(NO_OF_PDC_OPTAINED);
						
						if(CommonFunction.checkNull(INSTALLMENT_PATTERN).equalsIgnoreCase(""))//INSTALLMENT_PATTERN
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(INSTALLMENT_PATTERN);
						if(CommonFunction.checkNull(APPLCT_FIN_DOCS).equalsIgnoreCase(""))//APPLCT_FIN_DOCS
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(APPLCT_FIN_DOCS);
						if(CommonFunction.checkNull(GUR_FIN_DOCS).equalsIgnoreCase(""))//GUR_FIN_DOCS
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(GUR_FIN_DOCS);
						if(CommonFunction.checkNull(SECTOR_TYPE).equalsIgnoreCase(""))//SECTOR_TYPE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(SECTOR_TYPE);
						if(CommonFunction.checkNull(APPL_KYC_ADDR_PROOF).equalsIgnoreCase(""))//APPL_KYC_ADDR_PROOF
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(APPL_KYC_ADDR_PROOF);
						if(CommonFunction.checkNull(APPL_KYC_ID_PROOF).equalsIgnoreCase(""))//APPL_KYC_ID_PROOF
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(APPL_KYC_ID_PROOF);
						if(CommonFunction.checkNull(APPL_KYC_SIGN_PROOF).equalsIgnoreCase(""))//APPL_KYC_SIGN_PROOF
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(APPL_KYC_SIGN_PROOF);
						if(CommonFunction.checkNull(GUR_KYC_ADDR_PROOF).equalsIgnoreCase(""))//GUR_KYC_ADDR_PROOF
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(GUR_KYC_ADDR_PROOF);
						if(CommonFunction.checkNull(GUR_KYC_ID_PROOF).equalsIgnoreCase(""))//GUR_KYC_ID_PROOF
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(GUR_KYC_ID_PROOF);
						
						if(CommonFunction.checkNull(GUR_KYC_SIGN_PROOF).equalsIgnoreCase(""))//GUR_KYC_SIGN_PROOF
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(GUR_KYC_SIGN_PROOF);
						if(CommonFunction.checkNull(FIRST_EMI_DATE).equalsIgnoreCase(""))//FIRST_EMI_DATE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(FIRST_EMI_DATE);
						if(CommonFunction.checkNull(LAST_EMI_DATE).equalsIgnoreCase(""))//LAST_EMI_DATE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LAST_EMI_DATE);
						if(CommonFunction.checkNull(FIRST_EMI_MONTH).equalsIgnoreCase(""))//FIRST_EMI_MONTH
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(FIRST_EMI_MONTH);
						if(CommonFunction.checkNull(LAST_EMI_MONTH).equalsIgnoreCase(""))//LAST_EMI_MONTH
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LAST_EMI_MONTH);
						if(CommonFunction.checkNull(SEASONING).equalsIgnoreCase(""))//SEASONING
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(SEASONING);
						if(CommonFunction.checkNull(EMI_AMT).equalsIgnoreCase(""))//EMI_AMT
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(EMI_AMT);
						if(CommonFunction.checkNull(LOAN_STATUS).equalsIgnoreCase(""))//LOAN_STATUS
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_STATUS);
						if(CommonFunction.checkNull(LOAN_BRANCH).equalsIgnoreCase(""))//LOAN_BRANCH
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(LOAN_BRANCH);
						if(CommonFunction.checkNull(ADVANCE_EMI).equalsIgnoreCase(""))//ADVANCE_EMI
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(ADVANCE_EMI);
						
						if(CommonFunction.checkNull(SD_AMOUNT).equalsIgnoreCase(""))//SD_AMOUNT
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(SD_AMOUNT);
						if(CommonFunction.checkNull(PENDING_SD_AMOUNT).equalsIgnoreCase(""))//PENDING_SD_AMOUNT
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(PENDING_SD_AMOUNT);
						if(CommonFunction.checkNull(UNDISBURSED).equalsIgnoreCase(""))//UNDISBURSED
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(UNDISBURSED);
						if(CommonFunction.checkNull(AGREED_VALUE).equalsIgnoreCase(""))//AGREED_VALUE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(AGREED_VALUE);
						if(CommonFunction.checkNull(RECVD_INT_AMT_TILL_DATE).equalsIgnoreCase(""))//RECVD_INT_AMT_TILL_DATE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(RECVD_INT_AMT_TILL_DATE);
						if(CommonFunction.checkNull(UPLOADED_FLAG).equalsIgnoreCase(""))//UPLOADED_FLAG
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(UPLOADED_FLAG);
						if(CommonFunction.checkNull(REJECT_REASON).equalsIgnoreCase(""))//REJECT_REASON
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(REJECT_REASON);
						if(CommonFunction.checkNull(makerid).equalsIgnoreCase(""))//MAKER_ID
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(makerid);
												 
						if(CommonFunction.checkNull(makerdate).equalsIgnoreCase(""))//MAKER_DATE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(makerdate);
			
						
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						alFinalQry.add(insertPrepStmtObject); 
						//logger.info("Insert query :  "+insertPrepStmtObject.printQuery());   
					}//End of If		
				} // End of loop
				bufInsSql=null;
				if(alFinalQry.size()>0)
				{
					status=ConnectionUploadDAO.sqlInsUpdDeletePrepStmt(alFinalQry); 
				}
				logger.info("Insert status in temp table :  "+status);   
				in.add(""+companyId);
				in.add(poolID);
				in.add(poolIdMakerVO.getPoolName());
				String date=CommonFunction.changeFormat(CommonFunction.checkNull(poolIdMakerVO.getPoolCreationDate()).trim());
				in.add(date);
				String date1=CommonFunction.changeFormat(CommonFunction.checkNull(poolIdMakerVO.getCutOffDate()).trim());
  	        	in.add(date1);
  	        	in.add(poolIdMakerVO.getPoolType());
  	        	in.add(poolIdMakerVO.getLbxinstituteID());
  	        	String date2=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
  	        	in.add(date2);
  	        	in.add(file);
  	        	in.add(vo.getMakerId());
  	        	out.add(s1);
  	        	out.add(s2);
  	        	out.add(s3);
  	        	
				logger.info("Securitization_Pool_Upload("+in.toString()+","+out.toString()+")");
  	        	outMessages=(ArrayList) ConnectionUploadDAO.callSP("Securitization_Pool_Upload",in,out);			
  	        	s3.append(CommonFunction.checkNull(outMessages.get(0)));
  	        	s1.append(CommonFunction.checkNull(outMessages.get(1)));
  	        	s2.append(CommonFunction.checkNull(outMessages.get(2)));
  	        	logger.info("s1::::::::==>>"+s1);
  	        	logger.info("s2::::::::==>>"+s2);
  	        	logger.info("s3::::::::==>>"+s3);
  	        	if(s3.toString().equalsIgnoreCase("Y"))
  	        		status=true;
  	        	else
  	        		status=false;
                logger.info(" After Procedure Status "+status);
     
        	 } 
        	 catch(IOException ex) 
        	 {ex.printStackTrace();}
         }
         else
           	 msg="NF";
     }
     catch(Exception e)
     { e.printStackTrace();}
     finally 
     {
    	 objFile1.delete();
    	 request.setAttribute("msg", msg);
    	 s1=null;
    	 s2=null;
    	 s3=null;
     }
 return status;
}
*/
	 public	 ArrayList<PoolIdMakerVO>savedPoolIdAuthorData(PoolIdMakerVO poolIdMakerVO,String poolID)
	 {
		 logger.info("In savedPoolIdAuthorData() method of PoolIDDAOImpl");
  	   
		 ArrayList<PoolIdMakerVO> savedPoolListGrid=new ArrayList<PoolIdMakerVO>();
	 		
	 		try{
	 			ArrayList mainList=new ArrayList ();
	 			ArrayList subList =new ArrayList();
	 			//CallableStatement cst=null;
	 			//Connection con=ConnectionUploadDAO.getConnection();
	 					
	 		StringBuilder query=new StringBuilder();
	 			 
				       query.append("select P.pool_id,P.pool_name,DATE_FORMAT(P.POOL_CREATION_DATE,'"+dateFormat+"'),DATE_FORMAT(P.POOL_CUTOFF_DATE,'"+dateFormat+"'),P.POOL_TYPE,A.AGENCY_CODE,A.AGENCY_NAME" +
				  		" from cr_securitization_pool_m P,com_agency_m A where P.POOL_INSTITUTION_ID=A.AGENCY_CODE  " +    
				  		" AND POOL_ID='"+(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolID).trim()))+"'" +
				  		" and P.rec_status='F' ");
				       


	 			logger.info("In savedPoolIdAuthorData:-"+query);	
	 			
	 			mainList=ConnectionUploadDAO.sqlSelect(query.toString());
	 			
	 			query=null;
	 			
	 			for(int i=0;i<mainList.size();i++)
	 			{
	 				subList= (ArrayList)mainList.get(i);
	 				if(subList.size()>0){
	 					logger.info("Inside loop in savedPoolIdAuthorData:-");	
	 					PoolIdMakerVO VO = new PoolIdMakerVO();	
	 					VO.setPoolID((CommonFunction.checkNull(subList.get(0)).trim()));
             	    	VO.setPoolName((CommonFunction.checkNull(subList.get(1)).trim()));
             	    	VO.setPoolCreationDate((CommonFunction.checkNull(subList.get(2)).trim())); 
	                 	    logger.info(" savedPoolIdMakerData setPoolCreationDate:-------------"+subList.get(2));
	                 	    VO.setCutOffDate((CommonFunction.checkNull(subList.get(3)).trim())); 
	                 	    VO.setPoolType((CommonFunction.checkNull(subList.get(4)).trim()));
	                 	    VO.setLbxinstituteID((CommonFunction.checkNull(subList.get(5)).trim()));
	                 	   logger.info(" savedPoolIdMakerData setLbxinstituteID:-------------"+subList.get(5));
	                 	    VO.setInstituteID((CommonFunction.checkNull(subList.get(6)).trim()));
	                  savedPoolListGrid.add(VO);
	 				}
	 			}
	 			
	 				}catch(Exception e){
	 					e.printStackTrace();
	 				}


	 				return savedPoolListGrid;
	 			}
	 
	 public ArrayList getAuthorPoolData(PoolIdMakerVO poolIdMakerVO,String poolID) 
	 {
		 logger.info("In getAuthorPoolData() method of PoolIDDAOImpl");
		    int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
 		
 		ArrayList arrList = new ArrayList();
 		ArrayList subList = new ArrayList();
 		ArrayList mainList = new ArrayList();
 		PoolIdMakerVO poolIdMakerVOo= (PoolIdMakerVO)poolIdMakerVO;
 		logger.info("In getPoolData...");
 		logger.info("In getPoolData b4 query...");
 		StringBuilder customerType=new StringBuilder();
		StringBuilder disbursalStatus=new StringBuilder();
 		try{
 			StringBuilder query=new StringBuilder();
 			StringBuffer bufInsSqlTempCount = new StringBuffer();
 		 query.append("select POOL_ID,LOAN_ID, P.PRODUCT_DESC, S.SCHEME_DESC, cm.CUSTOMER_NAME, LOAN_CUSTOMER_TYPE, LOAN_CUSTOMER_CATEGORY," +
 				" LOAN_CUSTOMER_CONSTITUTION,LOAN_CUSTOMER_BUSINESS_SEGMENT, I.INDUSTRY_DESC, SI.SUB_INDUSTRY_DESC,DATE_FORMAT(LOAN_DISBURSAL_DATE,'"+dateFormat+"') ,DATE_FORMAT(LOAN_MATURITY_DATE,'"+dateFormat+"') ," +
 				" LOAN_TENURE,LOAN_BALANCE_TENURE, LOAN_INSTL_NUM,LOAN_ADV_EMI_NUM, LOAN_INT_RATE, LOAN_DISBURSAL_STATUS, LOAN_NPA_FLAG," +
 				" LOAN_DPD, LOAN_DPD_STRING, LOAN_ASSET_COST, LOAN_AMOUNT,LOAN_EMI, LOAN_ADV_EMI_AMOUNT, LOAN_BALANCE_PRINCIPAL, LOAN_OVERDUE_PRINCIPAL," +
 				" LOAN_RECEIVED_PRINCIPAL, LOAN_OVERDUE_INSTL_NUM, LOAN_OVERDUE_AMOUNT, LOAN_BALANCE_INSTL_NUM, LOAN_BALANCE_INSTL_AMOUNT " +
 				" from cr_securitization_pool_dtl A " +
				"LEFT JOIN com_industry_m I ON(I.INDUSTRY_ID=LOAN_INDUSTRY) " +
				"LEFT JOIN com_sub_industry_m SI ON(SI.sub_industry_id=LOAN_SUB_INDUSTRY) " +
				"LEFT JOIN cr_product_m P ON(LOAN_PRODUCT=P.PRODUCT_ID) " +
				"LEFT JOIN cr_scheme_m S ON(LOAN_SCHEME=S.SCHEME_ID) " +
				"LEFT JOIN gcd_customer_m cm ON(cm.CUSTOMER_ID=A.LOAN_CUSTOMER_ID) " +
				" where pool_id ="+poolID+" and A.rec_status = 'F' ");
 		 
 		 		
 		logger.info(query);
 		bufInsSqlTempCount.append("SELECT COUNT(1) " +
 				" from cr_securitization_pool_dtl A " +
				"LEFT JOIN com_industry_m I ON(I.INDUSTRY_ID=LOAN_INDUSTRY) " +
				"LEFT JOIN com_sub_industry_m SI ON(SI.sub_industry_id=LOAN_SUB_INDUSTRY) " +
				"LEFT JOIN cr_product_m P ON(LOAN_PRODUCT=P.PRODUCT_ID) " +
				"LEFT JOIN cr_scheme_m S ON(LOAN_SCHEME=S.SCHEME_ID) " +
				"LEFT JOIN gcd_customer_m cm ON(cm.CUSTOMER_ID=A.LOAN_CUSTOMER_ID) " +
				" where pool_id ="+poolID+" and A.rec_status = 'F' ");
		
		logger.info("query for count is ?>>>>>>>>>>>>>>>>>>>>>>"+bufInsSqlTempCount.toString());
		count =Integer.parseInt(ConnectionUploadDAO.singleReturn(bufInsSqlTempCount.toString()));
		logger.info("value of count in getb pool data:>>>>>>"+count);
		logger.info("value of page link is>>>>>>>>>>>>>>>>"+poolIdMakerVOo.getCurrentPageLink());
		if(poolIdMakerVOo.getCurrentPageLink()>1)
		{
		
		logger.info("current Page Link no .................... "+poolIdMakerVOo.getCurrentPageLink());
		if(poolIdMakerVOo.getCurrentPageLink()>1)
		{
			startRecordIndex = (poolIdMakerVOo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		query.append(" limit "+startRecordIndex+","+endRecordIndex);
		
		}
		else{
			
			startRecordIndex =0;
			endRecordIndex = no;
			query.append(" limit "+startRecordIndex+","+endRecordIndex);
		}
			
 	arrList = ConnectionUploadDAO.sqlSelect(query.toString());
 	
 	query=null;
 		
 		for(int i=0;i<arrList.size();i++){

 			subList=(ArrayList)arrList.get(i);
 			logger.info("In getPoolData..."+subList.size());
 			disbursalStatus= new StringBuilder();
			customerType= new StringBuilder();

 				if(subList.size()>0){

 					    PoolIdMakerVO poolIdMakerVO1=new PoolIdMakerVO();
 					    
 					    poolIdMakerVO1.setPoolID((CommonFunction.checkNull(subList.get(0)).trim()));
 					    poolIdMakerVO1.setLoanID((CommonFunction.checkNull(subList.get(1)).trim()));
 					    poolIdMakerVO1.setLoanProduct((CommonFunction.checkNull(subList.get(2)).trim()));
 					    poolIdMakerVO1.setLoanScheme((CommonFunction.checkNull(subList.get(3)).trim()));
 					    poolIdMakerVO1.setLoanCustomerID((CommonFunction.checkNull(subList.get(4)).trim()));
 					   logger.info("subList.get(7)   "+subList.get(5));
					    if(CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("I"))
				          {
					    customerType.append("INDIVIDUAL");
		  	      	  }
				         else if(CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase("C"))
		  	      	  {
				        	 customerType.append("CORPORATE");	  
		  	      	  }
 					    poolIdMakerVO1.setLoanCustomerType((CommonFunction.checkNull(customerType)));
 					    poolIdMakerVO1.setLoanCustomerCtegory((CommonFunction.checkNull(subList.get(6)).trim()));
 					    poolIdMakerVO1.setLoanCustomerConstituion((CommonFunction.checkNull(subList.get(7)).trim()));
 					    poolIdMakerVO1.setLoanCustomerBusinessSeg((CommonFunction.checkNull(subList.get(8)).trim()));
 					    poolIdMakerVO1.setLoanIndustry((CommonFunction.checkNull(subList.get(9)).trim()));
 					    poolIdMakerVO1.setLoanSubIndustry((CommonFunction.checkNull(subList.get(10)).trim()));
 					    poolIdMakerVO1.setLoanDisbursalDate((CommonFunction.checkNull(subList.get(11)).trim()));
 					    poolIdMakerVO1.setLoanMaturityDate((CommonFunction.checkNull(subList.get(12)).trim()));
 					    poolIdMakerVO1.setLoanTenure((CommonFunction.checkNull(subList.get(13)).trim()));
 					    poolIdMakerVO1.setLoanBalanceTenure((CommonFunction.checkNull(subList.get(14)).trim()));
 					    poolIdMakerVO1.setLoanInstallmentNum((CommonFunction.checkNull(subList.get(15)).trim()));
 					    poolIdMakerVO1.setLoanAdvEMINUm((CommonFunction.checkNull(subList.get(16)).trim()));
 					    poolIdMakerVO1.setLoanInitRate((CommonFunction.checkNull(subList.get(17)).trim()));
 					   if(CommonFunction.checkNull(subList.get(18)).trim().equalsIgnoreCase("N"))
		    	        {
		    		       disbursalStatus.append("NO DISBURSAL");
			    	      	  }
					    else if(CommonFunction.checkNull(subList.get(18)).trim().equalsIgnoreCase("P"))
					           {
			    	      	  
					    	disbursalStatus.append("PARTIAL");	  
			    	      	  }
			    	
		 	            else if(CommonFunction.checkNull(subList.get(18)).trim().equalsIgnoreCase("F"))
		 	             {
		    	    
		 	            	disbursalStatus.append("FULLY");	  
		    	    	  }
 					    poolIdMakerVO1.setLoanDisbursalStatus((CommonFunction.checkNull(disbursalStatus)));
 					    poolIdMakerVO1.setLoanNPAFlag((CommonFunction.checkNull(subList.get(19)).trim()));
 					    poolIdMakerVO1.setLoanDPD((CommonFunction.checkNull(subList.get(20)).trim()));
 					    poolIdMakerVO1.setLoanDPDString((CommonFunction.checkNull(subList.get(21)).trim()));
 					    poolIdMakerVO1.setLoanAssetCost((CommonFunction.checkNull(subList.get(22)).trim()));
 					    poolIdMakerVO1.setLoanAmount((CommonFunction.checkNull(subList.get(23)).trim()));
 					    poolIdMakerVO1.setLoanEMI((CommonFunction.checkNull(subList.get(24)).trim()));
 					    poolIdMakerVO1.setLoanAdvEMIAmount((CommonFunction.checkNull(subList.get(25)).trim()));
 					    poolIdMakerVO1.setLoanBalPrincipal((CommonFunction.checkNull(subList.get(26)).trim()));
 					    poolIdMakerVO1.setLoanOverduePrincipal((CommonFunction.checkNull(subList.get(27)).trim()));
 					    poolIdMakerVO1.setLoanReceivedPrincipal((CommonFunction.checkNull(subList.get(28)).trim()));
 					    poolIdMakerVO1.setLoanOverdueInstNo((CommonFunction.checkNull(subList.get(29)).trim()));
 					    poolIdMakerVO1.setLoanOverdueAmount((CommonFunction.checkNull(subList.get(30)).trim()));
 					    poolIdMakerVO1.setLoanBalnceInstNo((CommonFunction.checkNull(subList.get(31)).trim()));
 					    poolIdMakerVO1.setLoanBalInstlAmount((CommonFunction.checkNull(subList.get(32)).trim()));
 					   poolIdMakerVO1.setTotalRecordSize(count); 
 					    logger.info("count:----"+count);
 						mainList.add(poolIdMakerVO1);
 				
 				}
 				  disbursalStatus=null;
				  customerType=null;
 				}
 		
 			
 		}catch(Exception e){
 			logger.debug("IOException In getPoolData() ==>> "+e.getMessage());
 		}
 		
 			
 		return mainList;
 	}
	 
	 public String  onSaveOfPoolIdAuthor(PoolIdMakerVO poolIdMakerVO,int companyId)
	 {
		logger.info("In onSaveOfPoolIdAuthor() method of PoolIDDAOImpl"); 	    
 		ArrayList<Object> in =new ArrayList<Object>();
   		ArrayList<Object> out =new ArrayList<Object>();
   		ArrayList outMessages = new ArrayList();
   		StringBuilder s1=new StringBuilder();
   		StringBuilder s2=new StringBuilder();
 	    try
 	    {               		
    	    StringBuffer bufInsSql =	new StringBuffer();
    		ArrayList queryList=new ArrayList();                   		
    		PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
    		int statusProc=0;          	
    		in.add(companyId);
    		in.add(Integer.parseInt(poolIdMakerVO.getPoolID()));
    		in.add( 0);
    		String date=CommonFunction.changeFormat(CommonFunction.checkNull(poolIdMakerVO.getAuthorDate()).trim());
    		in.add(date);
    		in.add(poolIdMakerVO.getAuthorID());
    		in.add(poolIdMakerVO.getDecision());
    		in.add(poolIdMakerVO.getComments());
    		out.add(s1);
    		out.add(s2);
    		logger.info("Securitization_Pool_Authorization("+in.toString()+","+out.toString()+")");
    		outMessages=(ArrayList) ConnectionUploadDAO.callSP("Securitization_Pool_Authorization",in,out);
    		s1.append(CommonFunction.checkNull(outMessages.get(0)));
   			s2.append(CommonFunction.checkNull(outMessages.get(1)));
   			logger.info("s1: "+s1);
   			logger.info("s2: "+s2);
   		    if(s1.toString().equalsIgnoreCase("E"))
   		    	return s2.toString();
   			bufInsSql=null;
   			date=null;
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    		return "Some Error Occur During Procedure Execution,Please contact Administrator...";
    	}
    	finally
    	{
    		s1=null;
    		s2=null;
    	}
		return "S";
   		}	


	 public boolean docUploadForExcel(HttpServletRequest request, FormFile docFile) {
	 	boolean status=false;
	 	String fileName="";
	 	String filePath="";
	 	String message="";
	 	String filePathWithName="";
	 	String fileNameChange="";
	 	String user_Id="";
	 	
	 	HttpSession session=request.getSession(false);
	 	UserObject sessUser = (UserObject) session.getAttribute("userobject");
	 	
	 	try{
	 		if(sessUser!=null){
	 			user_Id = sessUser.getUserId();
	 		}
	 		String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='UPLOAD_PATH'";
	 		String rpt=ConnectionUploadDAO.singleReturn(query);
	 		query=null;
	 		File directory = new File(rpt+"/");
	 		boolean isDirectory = directory.isDirectory();
	 		
	 		Date dNow = new Date( );
	 		fileNameChange=fileNameFormat.format(dNow);
	 		if(isDirectory){
	 			fileName    = docFile.getFileName();
	 			logger.info("fileName is --->"+fileName);
	 			fileName=user_Id.concat("_"+fileNameChange.concat(fileName));
	 			logger.info("changed fileName is --->"+fileName);	
	 			
	 			filePath = directory.getAbsolutePath();
	 			filePathWithName=filePath.concat("\\").concat(fileName);
	 			
	 				if(!fileName.equals("")){  
	 						logger.info("Server path: filePath:==>>" +filePath);

	 						File fileToCreate = new File(filePath, fileName);
	 						int fileSize = docFile.getFileSize(); //26314400 bytes = 25 MB
	 						logger.info("docUpload :Size of file==>> "+fileSize);

	 							if(fileSize<26314400){
	 								FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
	 								fileOutStream.write(docFile.getFileData());
	 								fileOutStream.flush();
	 								fileOutStream.close();
	 								message="O";	 								
	 								status=true;	        		
	 							}else{
	 								message="E";
	 								logger.info("File size exceeds the upper limit of 25 MB.");
	 								status=false;
	 							}
	 				}else{
	 					message="S";
	 		        	status=false;
	 				}
	 		}else{
	 			message="E";
	 			logger.info("File Directory is empty");
	         	status=false;
	 		}
	 	}catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	logger.info("fileName "+fileName);
	 	logger.info("filePath "+filePath);
	 	logger.info("message "+message);
	 	logger.info("filePathWithName "+filePathWithName);
	 	logger.info("status "+status);
	 	
	 	request.setAttribute("fileName", fileName);
	 	request.setAttribute("filePath", filePath);
	 	request.setAttribute("message",message);
	 	request.setAttribute("filePathWithName", filePathWithName);
	 	fileName=null;
	 	filePath=null;
	 	message=null;
	 	filePathWithName=null;
	 	fileNameChange=null;
	 	user_Id=null;	 	
	 	
	 	return status;		
	 }

	 public boolean uploadCsv_Securitization(HttpServletRequest request,HttpServletResponse response,String strFileName,PoolIdMakerVO  poolIdMakerVO)
	 {
	 	   ArrayList alDeleteQuery  = new ArrayList(1);
	 	   boolean status=false;
	 	   try { 
	 		  String rpt1 = request.getContextPath();
			   	if(dbType.equalsIgnoreCase("MSSQL")){
			   		rpt1=rpt1+"/KTR_UPLOAD/MSSQL_KTR";
				}else{
					rpt1=rpt1+"/KTR_UPLOAD/MYSQL_KTR";
				}
	 			
	 		   	String strDeleteQuery = "DELETE FROM POOL_CREATION_UPDATE_TMP WHERE MAKER_ID = '"+CommonFunction.checkNull(poolIdMakerVO.getMakerID())+"' ";
	 		   	alDeleteQuery.add(strDeleteQuery);
	 		   	boolean status1=ConnectionUploadDAO.sqlInsUpdDelete(alDeleteQuery);
	 			   	
	 	     	  if(status1) {
	 	     		  logger.info("In uploadCsv_Securitization() Row is deleted....");
	 	     	  }else{
	 	     		  logger.info("In uploadCsv_Securitization() Row is not deleted......");
	 	     	  }
	 	     	
	 	     	  logger.info("In uploadCsv_Securitization() poolID.. "+poolIdMakerVO.getPoolID());
	 	     		 	     	  
	 	     	  KettleEnvironment.init(false); 
	 	     	  EnvUtil.environmentInit(); 
	 	     	  TransMeta transMeta = new TransMeta(rpt1.concat("\\PoolCreation.ktr")); 
	 	     	  Trans trans = new Trans(transMeta); 

	 	     	 // set parameter. same can be used inside steps in transformation 
	 		     	 trans.setParameterValue("filePathWithName",CommonFunction.checkNull(poolIdMakerVO.getFilePathWithName()));
	 		     	 trans.setParameterValue("poolID",poolIdMakerVO.getPoolID());
	 		     	 trans.setParameterValue("userID",CommonFunction.checkNull(poolIdMakerVO.getMakerID()));
	 	     	     
	 		     	 trans.execute(null); // You can pass arguments instead of null. 
	 	     	     trans.waitUntilFinished(); 
	 	     	 
	 	     	  if (trans.getErrors() > 0){ 
	 	     		  status=false;
	 	     		  throw new RuntimeException( "There were errors during transformation execution."+trans.getErrors() ); 
	 	     	  } else{
	 	     		 status=true;
	 	     	  }
	 	     	  
	 	     	  	transMeta.clearCaches();
	 		     	transMeta.clear();
	 		     	rpt1=null;
	 		     	trans.clearParameters();
	 		     	trans.cleanup();
	 		     	strDeleteQuery=null;
	 	     	
	    } 
	       catch (Exception e) {
	     	  logger.info("In uploadCsv_Securitization() Problem is ---->"+e.getMessage());
	 	}finally {
	 		
	 	} 

	 	return status;
	 }



	 public String saveSecuritization(HttpServletRequest request,PoolIdMakerVO poolIdMakerVO) {

		 logger.info("In saveSecuritization() ");
	 	   Connection con = ConnectionUploadDAO.getConnection();
		   ResultSet rs = null;
		   CallableStatement csmt = null;
		   
		   String flag=null;
		   String s2 =null;		   

	 	try { 	 
	 		
	 		csmt = (CallableStatement) con.prepareCall("{CALL SECURITIZATION_POOL_UPLOAD(?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),STR_TO_DATE(?,'"+dateFormat+"'),?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?)}");
	 		csmt.setString(1,"1");
	 		csmt.setString(2,CommonFunction.checkNull(poolIdMakerVO.getPoolID()));
	 		csmt.setString(3,CommonFunction.checkNull(poolIdMakerVO.getPoolName()));
	 		csmt.setString(4,CommonFunction.checkNull(poolIdMakerVO.getPoolCreationDate()));
	 		csmt.setString(5,CommonFunction.checkNull(poolIdMakerVO.getCutOffDate()));
	 		csmt.setString(6,CommonFunction.checkNull(poolIdMakerVO.getPoolType()));
	 		csmt.setString(7, CommonFunction.checkNull(poolIdMakerVO.getLbxinstituteID()));
	 		csmt.setString(8,CommonFunction.checkNull(poolIdMakerVO.getMakerDate()));
	 		csmt.setString(9,CommonFunction.checkNull(poolIdMakerVO.getFileName()));
	 		csmt.setString(10,CommonFunction.checkNull(poolIdMakerVO.getMakerID()));
	 		csmt.registerOutParameter(11,java.sql.Types.VARCHAR);
	 		csmt.registerOutParameter(12, java.sql.Types.VARCHAR);
	 		csmt.registerOutParameter(13, java.sql.Types.VARCHAR);
	 	
	 		rs = csmt.executeQuery();
	 		
	 		flag = csmt.getString(12);
	 		 s2 = csmt.getString(13);

	 		 logger.info("In saveSecuritization() flag:----"+flag);
	 		 logger.info("In saveSecuritization() s2:----"+s2);
	 		 
	 		 request.setAttribute("errorMsg", s2);		 

	 	} catch (SQLException exp) {

	 		exp.printStackTrace();

	 	} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
	 		   ConnectionUploadDAO.closeConnection(con,csmt,rs);
	 		   con=null;
	 		   s2 = null;	 			
	 	}
	 	return flag;
	 }

	
	 public	 ArrayList<PoolIdMakerVO>savedPoolIdEditData(PoolIdMakerVO poolIdMakerVO,String poolID)
		{
			logger.info("In savedPoolIdEditData() method of PoolIdAddEditDAOImpl");
			 ArrayList<PoolIdMakerVO> savedPoolListGrid=new ArrayList<PoolIdMakerVO>();
		 		
		 		try{
		 			ArrayList mainList=new ArrayList ();
		 			ArrayList subList =new ArrayList();
		 			//CallableStatement cst=null;
		 			//Connection con=ConnectionUploadDAO.getConnection();
		 					
		 		
		 			 
					       String query="select P.pool_id,P.pool_name,DATE_FORMAT(P.POOL_CREATION_DATE,'"+dateFormat+"')," +
					       		"DATE_FORMAT(P.POOL_CUTOFF_DATE,'"+dateFormat+"'),P.POOL_TYPE,A.AGENCY_CODE,A.AGENCY_NAME" +
					  		" from cr_securitization_pool_m P,com_agency_m A where P.POOL_INSTITUTION_ID=A.AGENCY_CODE  " +    
					  		" AND POOL_ID='"+(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolID).trim()))+"'" +
					  		" and P.rec_status='A' ";
					       


		 			logger.info("In savedPoolIdMakerData:-"+query);	
		 			
		 			mainList=ConnectionUploadDAO.sqlSelect(query);
		 			for(int i=0;i<mainList.size();i++)
		 			{
		 				subList= (ArrayList)mainList.get(i);
		 				if(subList.size()>0){
		 					logger.info("Inside loop in savedPoolIdMakerData:-");	
		 					PoolIdMakerVO VO = new PoolIdMakerVO();	
		 					VO.setPoolID((CommonFunction.checkNull(subList.get(0)).trim()));
	            	    	VO.setPoolName((CommonFunction.checkNull(subList.get(1)).trim()));          	             	    	
	            	    	VO.setPoolCreationDate((CommonFunction.checkNull(subList.get(2)).trim())); 
	                	    logger.info(" savedPoolIdMakerData setPoolCreationDate:-------------"+subList.get(2));
	                	    VO.setCutOffDate((CommonFunction.checkNull(subList.get(3)).trim())); 
	                	    VO.setPoolType((CommonFunction.checkNull(subList.get(4)).trim()));
	                	    VO.setLbxinstituteID((CommonFunction.checkNull(subList.get(5)).trim()));
	                	   logger.info(" savedPoolIdMakerData setLbxinstituteID:-------------"+subList.get(5));
	                	    VO.setInstituteID((CommonFunction.checkNull(subList.get(6)).trim()));
	                	 savedPoolListGrid.add(VO);
		 				}
		 			}
		 				}catch(Exception e){
		 					e.printStackTrace();
		 				}


		 				return savedPoolListGrid;
		 			}	
	 
	 public ArrayList getPoolAddEditData(String poolID) 
		{
			
			logger.info("In getPoolAddEditData() method of PoolIdAddEditDAOImpl");
			ArrayList arrList = new ArrayList();
			ArrayList subList = new ArrayList();
			ArrayList mainList = new ArrayList();
			logger.info("In getPoolAddEditData...");
			String customerType="";
			String disbursalStatus="";
			try{                			
			
			String query = "select POOL_ID,LOAN_ID, LOAN_PRODUCT,P.PRODUCT_DESC, LOAN_SCHEME,S.SCHEME_DESC," +
			 " CUSTOMER_NAME, LOAN_CUSTOMER_TYPE, LOAN_CUSTOMER_CATEGORY, LOAN_CUSTOMER_CONSTITUTION,"+
			 " LOAN_CUSTOMER_BUSINESS_SEGMENT, LOAN_INDUSTRY,I.INDUSTRY_DESC, LOAN_SUB_INDUSTRY,"+
			 " SI.SUB_INDUSTRY_DESC, DATE_FORMAT(LOAN_DISBURSAL_DATE,'%d-%m-%Y'), DATE_FORMAT(LOAN_MATURITY_DATE,'%d-%m-%Y'),"+
			 " LOAN_TENURE,LOAN_BALANCE_TENURE, LOAN_INSTL_NUM,LOAN_ADV_EMI_NUM, LOAN_INT_RATE, LOAN_DISBURSAL_STATUS,"+
			 " LOAN_NPA_FLAG, LOAN_DPD, LOAN_DPD_STRING, LOAN_ASSET_COST, LOAN_AMOUNT,LOAN_EMI, LOAN_ADV_EMI_AMOUNT,"+
			 " LOAN_BALANCE_PRINCIPAL, LOAN_OVERDUE_PRINCIPAL, LOAN_RECEIVED_PRINCIPAL, LOAN_OVERDUE_INSTL_NUM,"+
			 " LOAN_OVERDUE_AMOUNT, LOAN_BALANCE_INSTL_NUM, LOAN_BALANCE_INSTL_AMOUNT "+
			 " from cr_securitization_pool_dtl A " +
				"LEFT JOIN com_industry_m I ON(I.INDUSTRY_ID=LOAN_INDUSTRY) " +
				"LEFT JOIN com_sub_industry_m SI ON(SI.sub_industry_id=LOAN_SUB_INDUSTRY) " +
				"LEFT JOIN cr_product_m P ON(LOAN_PRODUCT=P.PRODUCT_ID) " +
				"LEFT JOIN cr_scheme_m S ON(LOAN_SCHEME=S.SCHEME_ID) " +
				"LEFT JOIN gcd_customer_m cm ON(cm.CUSTOMER_ID=A.LOAN_CUSTOMER_ID) " +
				" where pool_id ="+poolID+" and A.rec_status = 'A' ";
			
			logger.info(query);
			arrList = ConnectionUploadDAO.sqlSelect(query);
			
			
			for(int i=0;i<arrList.size();i++){

				subList=(ArrayList)arrList.get(i);
				logger.info("In getPoolAddEditData..."+subList.size());

					if(subList.size()>0){

						    PoolIdMakerVO poolIdMakerVO1=new PoolIdMakerVO();
						    
						    poolIdMakerVO1.setPoolID((CommonFunction.checkNull(subList.get(0)).trim()));
						    poolIdMakerVO1.setLoanID((CommonFunction.checkNull(subList.get(1)).trim()));
						    poolIdMakerVO1.setLoanProduct((CommonFunction.checkNull(subList.get(3)).trim()));
						    poolIdMakerVO1.setLoanScheme((CommonFunction.checkNull(subList.get(5)).trim()));
						    poolIdMakerVO1.setLoanCustomerID((CommonFunction.checkNull(subList.get(6)).trim()));
						    if(CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase("I"))
					          {
						    customerType ="INDIVIDUAL";
			  	      	  }
					         else if(CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase("C"))
			  	      	  {
					        	 customerType ="CORPORATE";	  
			  	      	  }
						    poolIdMakerVO1.setLoanCustomerType((CommonFunction.checkNull(customerType)));
						    poolIdMakerVO1.setLoanCustomerCtegory((CommonFunction.checkNull(subList.get(8)).trim()));
						    poolIdMakerVO1.setLoanCustomerConstituion((CommonFunction.checkNull(subList.get(9)).trim()));
						    poolIdMakerVO1.setLoanCustomerBusinessSeg((CommonFunction.checkNull(subList.get(10)).trim()));
						    poolIdMakerVO1.setLoanIndustry((CommonFunction.checkNull(subList.get(12)).trim()));
						    poolIdMakerVO1.setLoanSubIndustry((CommonFunction.checkNull(subList.get(14)).trim()));
						    poolIdMakerVO1.setLoanDisbursalDate((CommonFunction.checkNull(subList.get(15)).trim()));
						    poolIdMakerVO1.setLoanMaturityDate((CommonFunction.checkNull(subList.get(16)).trim()));
						    poolIdMakerVO1.setLoanTenure((CommonFunction.checkNull(subList.get(17)).trim()));
						    poolIdMakerVO1.setLoanBalanceTenure((CommonFunction.checkNull(subList.get(18)).trim()));
						    poolIdMakerVO1.setLoanInstallmentNum((CommonFunction.checkNull(subList.get(19)).trim()));
						    poolIdMakerVO1.setLoanAdvEMINUm((CommonFunction.checkNull(subList.get(20)).trim()));
						    poolIdMakerVO1.setLoanInitRate((CommonFunction.checkNull(subList.get(21)).trim()));
						    if(CommonFunction.checkNull(subList.get(22)).trim().equalsIgnoreCase("N"))
			    	        {
			    		       disbursalStatus ="NO DISBURSAL";
				    	      	  }
						    else if(CommonFunction.checkNull(subList.get(22)).trim().equalsIgnoreCase("P"))
						           {
				    	      	  
						    	disbursalStatus ="PARTIAL";	  
				    	      	  }
				    	
			 	            else if(CommonFunction.checkNull(subList.get(22)).trim().equalsIgnoreCase("F"))
			 	             {
			    	    
			 	            	disbursalStatus ="FULLY";	  
			    	    	  }
						    poolIdMakerVO1.setLoanDisbursalStatus((CommonFunction.checkNull(disbursalStatus)));
						    poolIdMakerVO1.setLoanNPAFlag((CommonFunction.checkNull(subList.get(23)).trim()));
						    poolIdMakerVO1.setLoanDPD((CommonFunction.checkNull(subList.get(24)).trim()));
						    poolIdMakerVO1.setLoanDPDString((CommonFunction.checkNull(subList.get(25)).trim()));
						    poolIdMakerVO1.setLoanAssetCost((CommonFunction.checkNull(subList.get(26)).trim()));
						    poolIdMakerVO1.setLoanAmount((CommonFunction.checkNull(subList.get(27)).trim()));
						    poolIdMakerVO1.setLoanEMI((CommonFunction.checkNull(subList.get(28)).trim()));
						    poolIdMakerVO1.setLoanAdvEMIAmount((CommonFunction.checkNull(subList.get(29)).trim()));
						    poolIdMakerVO1.setLoanBalPrincipal((CommonFunction.checkNull(subList.get(30)).trim()));
						    poolIdMakerVO1.setLoanOverduePrincipal((CommonFunction.checkNull(subList.get(31)).trim()));
						    poolIdMakerVO1.setLoanReceivedPrincipal((CommonFunction.checkNull(subList.get(32)).trim()));
						    poolIdMakerVO1.setLoanOverdueInstNo((CommonFunction.checkNull(subList.get(33)).trim()));
						    poolIdMakerVO1.setLoanOverdueAmount((CommonFunction.checkNull(subList.get(34)).trim()));
						    poolIdMakerVO1.setLoanBalnceInstNo((CommonFunction.checkNull(subList.get(35)).trim()));
						    poolIdMakerVO1.setLoanBalInstlAmount((CommonFunction.checkNull(subList.get(36)).trim()));
						    
						    
							mainList.add(poolIdMakerVO1);
					
					}
					}
			
				
			}catch(Exception e){
				logger.debug("IOException In getPoolAddEditData() ==>> "+e.getMessage());
			}
			
				
			return mainList;
		}
	 public boolean deletePoolIDEdit(PoolIdMakerVO poolIdMakerVO,String[] loanID, String[] poolID,int companyId)
	 {
		logger.info("In deletePoolIDEdit() method of PoolIdAddEditDAOImpl");
		ArrayList qryList =  new ArrayList();
	   	boolean status =false;
	   	ArrayList<Object> in =null;
	   	ArrayList<Object> out =null;
	   	ArrayList outMessages =null;
	   	StringBuffer bufInsSql =null;
	   	PrepStmtObject insertPrepStmtObject = null;	
	   	String s1="";
	   	String s2="";
	   	
		logger.info("In deletePoolIDEdit...Dao Impl.");
	   	try
	   	{
	   		for(int i =0;i<loanID.length;i++)
	   		{
	   			 bufInsSql =	new StringBuffer();
	   			 in=new ArrayList<Object>();
	   		     out =new ArrayList<Object>();
	   		     outMessages =new ArrayList();
	   			 insertPrepStmtObject = new PrepStmtObject();
             	 int  PoolId=Integer.parseInt(poolID[i]);
	             int  LoanId=Integer.parseInt(loanID[i]);
            	 in.add(companyId);
	             in.add(PoolId);
	             in.add(LoanId);
	             String date=CommonFunction.changeFormat(CommonFunction.checkNull(poolIdMakerVO.getMakerDate()).trim());
	             in.add(date);
	            in.add(poolIdMakerVO.getMakerID());
            	 in.add("A");                         	
            	 in.add("Delete");
	             out.add(s1);
	             out.add(s2);
	             logger.info("Securitization_Pool_Deletion("+in.toString()+","+out.toString()+")");
	             outMessages=(ArrayList) ConnectionUploadDAO.callSP("Securitization_Pool_Deletion",in,out);
	             s1=CommonFunction.checkNull(outMessages.get(0));
	             s2=CommonFunction.checkNull(outMessages.get(1));
	             logger.info("s1: "+s1);
	             logger.info("s2: "+s2);
	             
	   			 if(s1.equalsIgnoreCase("S"))
	   			 {
	             	  bufInsSql.append("insert into cr_securitization_pool_dtl_HST ");									
					  bufInsSql.append(" select  *,MAKER_ID USERID, SYSDATE() FROM cr_securitization_pool_dtl " );
					  bufInsSql.append(" WHERE POOL_ID='"+(StringEscapeUtils.escapeSql(CommonFunction.checkNull(PoolId)))+"'");
					  bufInsSql.append(" AND LOAN_ID='" +(StringEscapeUtils.escapeSql(CommonFunction.checkNull(LoanId)))+ "'");
					  insertPrepStmtObject.setSql(bufInsSql.toString());
					  qryList.add(insertPrepStmtObject);
				      logger.info("In insert of  cr_securitization_pool_dtl_hst "+insertPrepStmtObject.printQuery());
					  insertPrepStmtObject =  new PrepStmtObject();
		              bufInsSql = new StringBuffer();
		              bufInsSql.append("delete from cr_securitization_pool_dtl where POOL_ID = ? and LOAN_ID = ?");

	   				if(CommonFunction.checkNull(poolID[i]).equalsIgnoreCase(""))
	   					insertPrepStmtObject.addNull();
	   				else
	   					insertPrepStmtObject.addString((CommonFunction.checkNull(poolID[i]).trim()));
	   					 
	   				if(CommonFunction.checkNull(loanID[i]).equalsIgnoreCase(""))
	   					insertPrepStmtObject.addNull();
	   				else
	   					insertPrepStmtObject.addString((CommonFunction.checkNull(loanID[i]).trim()));
	   				insertPrepStmtObject.setSql(bufInsSql.toString());
	   				qryList.add(insertPrepStmtObject);
	   		    }
	   		}
	   		logger.info("deletePoolIDEdit:-"+insertPrepStmtObject.printQuery());
	   		status=ConnectionUploadDAO.sqlInsUpdDeletePrepStmt(qryList);	   		
	   	}
	   	catch(Exception e)
	   	{e.printStackTrace();}
	   	return status;		
	 }
}
