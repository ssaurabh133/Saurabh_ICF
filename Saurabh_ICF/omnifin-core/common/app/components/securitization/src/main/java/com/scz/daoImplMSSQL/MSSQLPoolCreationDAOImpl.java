package com.scz.daoImplMSSQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.scz.dao.PoolCreationDAO;
import com.scz.vo.PoolCreationForCMVO;
import com.scz.vo.PoolIdMakerVO;
import com.scz.vo.uploadVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.login.roleManager.UserObject;

public class MSSQLPoolCreationDAOImpl implements PoolCreationDAO 
{
	private static final Logger logger = Logger.getLogger(MSSQLPoolCreationDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	public StringBuffer getUploadPoolData(String makerId)
	{
		logger.info("In getUploadPoolData()in PoolCreationDAOImpl");
		Connection conn=null;
		Statement stmt =null;
		StringBuffer data= new StringBuffer();
		try
		{
		if(makerId!=null)
		{
		String query= "Select file_name from upload_SUMMARY where maker_id='"+makerId+"' and MAKER_DATE=CURRENT_DATE";
		logger.info("In getUploadPoolData()...............query...........DAOImpl "+query);
		conn= ConnectionDAO.getConnection();
		stmt= conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			data.append("~");
			data.append(rs.getString(1).toString());
	    }
				
		}
		else
		{
			logger.info("In.....getUploadPoolData()...DAOIMPL...MakerId..."+makerId);
			
		}
		conn.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//checking how many value have come from database..
		


		finally
				{
					try {
						conn.commit();
						conn.close();
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}

		logger.info(" ..getUploadData()..File Name..is...."+data);

		
		return data; 
		
		
	   }
	 public	 ArrayList<PoolCreationForCMVO>addscreenForEditPoolData(PoolCreationForCMVO poolvo,int poolID){
		 ArrayList<PoolCreationForCMVO> editPoolListGrid=new ArrayList<PoolCreationForCMVO>();
		//	CallableStatement cst=null;
 			//Connection con=ConnectionDAO.getConnection();
	 		try{
	 			ArrayList mainList=new ArrayList ();
	 			ArrayList subList =new ArrayList();
	     String query="select POOL_ID,LOAN_ID,G.CUSTOMER_NAME," +
				  		" LOAN_CUSTOMER_CATEGORY,LOAN_CUSTOMER_BUSINESS_SEGMENT,LOAN_CUSTOMER_CONSTITUTION," +
				  		" CI.INDUSTRY_DESC,LOAN_PRODUCT,LOAN_SCHEME,LOAN_DISBURSAL_DATE,LOAN_MATURITY_DATE,LOAN_AMOUNT," +
				  		" LOAN_INT_RATE,LOAN_BALANCE_INSTL_AMOUNT,LOAN_INSTL_NUM,(LOAN_INSTL_NUM-LOAN_BALANCE_INSTL_NUM)INSTL_RECEIVED," +
				  		" LOAN_OVERDUE_AMOUNT,LOAN_DPD,S.MAKER_ID from cr_securitization_pool_dtl S,gcd_customer_m G,com_industry_m CI " +
				  		" WHERE POOL_ID='"+(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getPoolID()).trim()))+"'" +
				  		" AND S.LOAN_CUSTOMER_ID=G.CUSTOMER_ID "+
				        " AND S.LOAN_INDUSTRY=CI.INDUSTRY_ID ";


	 			logger.info("In showPoolData:-"+query);	
	 			
	 			mainList=ConnectionDAO.sqlSelect(query);
	 			for(int i=0;i<mainList.size();i++)
	 			{
	 				subList= (ArrayList)mainList.get(i);
	 				if(subList.size()>0){
	 					logger.info("Inside loop in showPoolData:-");	
	 					PoolCreationForCMVO poolVO = new PoolCreationForCMVO();	
	 					poolVO.setPoolID((CommonFunction.checkNull(subList.get(0)).trim()));
	 					poolVO.setLoanAccountNumber((CommonFunction.checkNull(subList.get(1)).trim()));
	 					poolVO.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
	 					poolVO.setCustomerCategory((CommonFunction.checkNull(subList.get(3)).trim()));
	 					poolVO.setCustomerSegment((CommonFunction.checkNull(subList.get(4)).trim()));
	 					poolVO.setConstitution((CommonFunction.checkNull(subList.get(5)).trim()));
	 					poolVO.setIndustry((CommonFunction.checkNull(subList.get(6)).trim()));
	 					poolVO.setProduct((CommonFunction.checkNull(subList.get(7)).trim()));
	 					poolVO.setScheme((CommonFunction.checkNull(subList.get(8)).trim()));
	 					poolVO.setDisbursalDate1((CommonFunction.checkNull(subList.get(9)).trim()));
	 					poolVO.setMaturityDate1((CommonFunction.checkNull(subList.get(10)).trim()));
	 					poolVO.setLoanAmount1((CommonFunction.checkNull(subList.get(11)).trim()));		 					
	 					poolVO.setInterestRate1((CommonFunction.checkNull(subList.get(12)).trim()));
	 					poolVO.setInstallmentAmount1((CommonFunction.checkNull(subList.get(13)).trim()));
	 					poolVO.setTotalInstallments1((CommonFunction.checkNull(subList.get(14)).trim()));
	 					poolVO.setInstallmentReceived1((CommonFunction.checkNull(subList.get(15)).trim()));
	 					poolVO.setOverDueAmount1((CommonFunction.checkNull(subList.get(16)).trim()));
	 					poolVO.setDPD1((CommonFunction.checkNull(subList.get(17)).trim()));		 					
	 					poolVO.setMakerID((CommonFunction.checkNull(subList.get(18)).trim()));
	 					editPoolListGrid.add(poolVO);
	 				}
	 			}
	 				}catch(Exception e){
	 					e.printStackTrace();
	 				}


	 				return editPoolListGrid;
	 			}
	 public boolean removePoolData(PoolCreationForCMVO poolvo) {
		  logger.info("###  In removePoolData #### ");
		boolean status=false;
			try{
				ArrayList queryList=new ArrayList ();
				ArrayList subList =new ArrayList();
				ArrayList list =new ArrayList();
				PrepStmtObject insertPrepStmtObject=null;
				StringBuffer bufInsSql = null;
				 
				  String deleteCount = ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_securitization_pool_dtl WHERE LOAN_ID='" +((CommonFunction.checkNull(poolvo.getCheckId()).trim()))+ "'" +
				  		" AND POOL_ID='"+CommonFunction.checkNull(StringEscapeUtils.escapeSql(poolvo.getPoolID()).trim())+ "'");
				
				  logger.info("### In removePoolData  deleteCount #### "+deleteCount);
				  if(!deleteCount.equalsIgnoreCase("0")){
					 
					     logger.info("### In cr_securitization_pool_dtl_hst table =");
						 insertPrepStmtObject=null;
				         bufInsSql=null;
				         insertPrepStmtObject =  new PrepStmtObject();
				         bufInsSql = new StringBuffer();
				           
							
							  bufInsSql.append("insert into cr_securitization_pool_dtl_HST ");									
							  bufInsSql.append(" select  *,MAKER_ID USERID, ");
							  bufInsSql.append(dbo);
							  bufInsSql.append("SYSDATE() FROM cr_securitization_pool_dtl " );
							  bufInsSql.append(" WHERE POOL_ID='"+(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getPoolID()).trim()))+"'");
							  bufInsSql.append(" AND LOAN_ID='" +(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolvo.getCheckId()).trim()))+ "'");
							 

								
							  insertPrepStmtObject.setSql(bufInsSql.toString());
							  queryList.add(insertPrepStmtObject);
							
							  logger.info("In insert of  cr_securitization_pool_dtl_hst "+insertPrepStmtObject.printQuery());
					  
					  
					  
	                insertPrepStmtObject =  new PrepStmtObject();
	                bufInsSql = new StringBuffer();
	                bufInsSql.append(" DELETE FROM cr_securitization_pool_dtl WHERE LOAN_ID='"+CommonFunction.checkNull(StringEscapeUtils.escapeSql(poolvo.getCheckId()).trim())+ "'") ;
	                bufInsSql.append(" AND POOL_ID='"+CommonFunction.checkNull(StringEscapeUtils.escapeSql(poolvo.getPoolID()).trim())+ "'");
	                logger.info("### In removePoolData  bufInsSql #### "+bufInsSql.toString());
	                insertPrepStmtObject.setSql(bufInsSql.toString());
	                queryList.add(insertPrepStmtObject);  
	                
	            }
				  status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
					logger.info("In removePoolData,,,,,"+status);
					
					} 
						catch(Exception e){
						e.printStackTrace();
					}

					return status;

			}
	 public boolean addPoolData(PoolCreationForCMVO poolvo){		    		
 		ArrayList<PoolCreationForCMVO> getDataList=new ArrayList<PoolCreationForCMVO>();
 		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
 		boolean status=false;	
 		 logger.info("In addPoolData DAOImpl......................");
 		 ArrayList qryList = new ArrayList();
 		
 			try{

 			    StringBuffer bufInsSql =	new StringBuffer();
 				bufInsSql.append(" insert into cr_securitization_pool_dtl(POOL_ID,LOAN_ID,LOAN_PRODUCT,LOAN_SCHEME,LOAN_CUSTOMER_ID,");
 				bufInsSql.append(" LOAN_CUSTOMER_TYPE,LOAN_CUSTOMER_CATEGORY,LOAN_CUSTOMER_CONSTITUTION,LOAN_CUSTOMER_BUSINESS_SEGMENT,");
 				bufInsSql.append(" LOAN_INDUSTRY,LOAN_SUB_INDUSTRY,LOAN_DISBURSAL_DATE,LOAN_MATURITY_DATE,LOAN_TENURE,LOAN_BALANCE_TENURE,");
					bufInsSql.append(" LOAN_INSTL_NUM,LOAN_ADV_EMI_NUM,LOAN_INT_RATE,");
					bufInsSql.append(" LOAN_DISBURSAL_STATUS,LOAN_NPA_FLAG,LOAN_DPD,LOAN_DPD_STRING,LOAN_ASSET_COST,LOAN_AMOUNT,LOAN_EMI,LOAN_ADV_EMI_AMOUNT,");
  				bufInsSql.append(" LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,");
 				bufInsSql.append(" LOAN_RECEIVED_PRINCIPAL,LOAN_OVERDUE_INSTL_NUM,LOAN_OVERDUE_AMOUNT,LOAN_BALANCE_INSTL_NUM,LOAN_BALANCE_INSTL_AMOUNT ");
 				bufInsSql.append(" ,REC_STATUS,MAKER_ID,MAKER_DATE)");
 				
					bufInsSql.append(" SELECT distinct '"+((CommonFunction.checkNull(poolvo.getPoolID()).trim()))+"' POOL_ID,A.LOAN_ID,");
					bufInsSql.append(" LOAN_PRODUCT,LOAN_SCHEME,LOAN_CUSTOMER_ID,LOAN_CUSTOMER_TYPE,C.CUSTOMER_CATEGORY,C.CUSTOMER_CONSTITUTION,C.CUSTOMER_BUSINESS_SEGMENT,LOAN_INDUSTRY_ID,LOAN_SUB_INDUSTRY_ID,LOAN_DISBURSAL_DATE,LOAN_MATURITY_DATE,");
					bufInsSql.append(" LOAN_TENURE,(SELECT COUNT(1) FROM cr_repaysch_dtl WHERE LOAN_ID = A.LOAN_ID AND BILL_FLAG='N')AS REMAINING_TENURE,LOAN_NO_OF_INSTALLMENT,4,5," );
					bufInsSql.append("  DISBURSAL_STATUS,NPA_FLAG,LOAN_DPD,LOAN_DPD_STRING,LOAN_ASSET_COST,LOAN_LOAN_AMOUNT,LOAN_EMI_AMOUNT,6,");
					bufInsSql.append(" LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,LOAN_RECEIVED_PRINCIPAL,LOAN_OVERDUE_INSTL_NUM,LOAN_OVERDUE_AMOUNT,7,LOAN_BALANCE_INSTL_AMOUNT, ");
					bufInsSql.append(" 'A' REC_STATUS,'"+((CommonFunction.checkNull(poolvo.getMakerID()).trim()))+"' MAKER_ID,");
					bufInsSql.append(dbo);
					bufInsSql.append(" SYSDATE()");
					bufInsSql.append(" from cr_loan_dtl A,cr_repaysch_dtl R,gcd_customer_m C  ");
					bufInsSql.append(" WHERE R.LOAN_ID = A.LOAN_ID ");
					bufInsSql.append(" AND A.LOAN_CUSTOMER_ID = C.CUSTOMER_ID ");
					bufInsSql.append(" AND A.LOAN_ID ='"+((CommonFunction.checkNull(poolvo.getLoanAccountNumber()).trim()))+"'");
 			
 				insertPrepStmtObject.setSql(bufInsSql.toString());
 				
 				qryList.add(insertPrepStmtObject);
 				logger.info("IN addPoolData query1 ### "+insertPrepStmtObject.printQuery());
 			
 				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
 				logger.info("In addPoolData ....................."+status);
 		}
 			catch(Exception e){
 				e.printStackTrace();
 			}

 			return status;
 		}
	 public boolean isExistInPool(PoolCreationForCMVO poolvo){
		 boolean result=false;
	     String result1="";
	     String result2="";
		   try{
		  String existCount =("SELECT COUNT(1) FROM cr_loan_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(poolvo.getLoanAccountNumber()).trim())+"'" );
		  result1=ConnectionDAO.singleReturn(existCount);
		  logger.info("IN existData  ### "+existCount);
		  logger.info("IN existData  ### "+result1);
		  String exist1Count =("SELECT COUNT(1) FROM cr_securitization_pool_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(poolvo.getLoanAccountNumber()).trim())+"'" );
		  result2=ConnectionDAO.singleReturn(exist1Count);
			logger.info("IN existData  ### "+exist1Count);
			logger.info("IN existData  ### "+result2);
		 if (!result1.equalsIgnoreCase("0")&& result2.equalsIgnoreCase("0") ){
			 result=true;
		 }
		 else{
			 result=false;
		 }
	  }
		   catch(Exception e){
					e.printStackTrace();
				}

				return result;
				
			}
	 public ArrayList getUploadPoolSummary(String makerId, HttpServletRequest request)
		{
			HttpSession session=request.getSession();
			logger.info("in getUploadPoolSummary... DAOI ");
			ArrayList list= new ArrayList();
			//Connection conn1=null;
			//Statement stmt,stmt1 =null;
			StringBuffer data= new StringBuffer();
			try
			{
			if(makerId!=null)
			{
				// select batch_id ,maker_date,no_of_records_rejected,no_of_records_uploaded from upload_summary where maker_id=1 and batch_id=( select max(batch_id) from upload_summary)
			String query= "select batch_id ,maker_date,no_of_records_rejected,no_of_records_uploaded from upload_summary where maker_id= '"+makerId+"' and batch_id=( select max(batch_id) from upload_summary)";
			logger.info("In getUploadPoolSummary()...............query...........DAOImpl "+query);
			list=ConnectionDAO.sqlSelect(query);
			logger.info("In getUploadPoolSummary()...............list...........DAOImpl "+list);
		    String queryForTMP="Select count('total') from pool_creation_update_tmp where uploaded_flag='N' or uploaded_flag='Y' and maker_id='"+makerId+"'"; 
//			String queryForTMP="Select count('total') from pool_creation_update_tmp where uploaded_flag='N' and maker_id='"+makerId+"'"; 
			logger.info("In..getUploadPoolSummary()...Query"+queryForTMP);
			
			String total=ConnectionDAO.singleReturn(queryForTMP);		
			logger.info("in getUploadPoolSummary()..in daoi..total is."+total);
			session.setAttribute("TotalUploadandReject", total);
			logger.info("Total uploaded and rejected.."+total);
			}
			else
			{
				logger.info("In.....getUploadPoolSummary()...DAOIMPL...MakerId..."+makerId);
				
			}
		
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				
			}
			
			return list;
		}
	 public boolean insertDataforPoolId(PoolIdMakerVO poolIdMakerVO) {

		    logger.info("In insertDataforPoolId...Dao Impl.");
			ArrayList qryList =  new ArrayList();
			boolean status=false;
			StringBuffer bufInsSql =null;
			PrepStmtObject insertPrepStmtObject = null;	
			
			try{

					bufInsSql =	new StringBuffer();
					 insertPrepStmtObject = new PrepStmtObject();	
					 
					 bufInsSql.append("insert into cr_securitization_pool_m(POOL_ID,POOL_NAME,POOL_CREATION_DATE,POOL_CUTOFF_DATE,POOL_TYPE," +
					 		          " POOL_INSTITUTION_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
					 
							 bufInsSql.append(" values ( ");
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(dbo);
							 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),");
							 bufInsSql.append(dbo);
							 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),");
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" 'P'," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(dbo);
							 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )" );
								
								
							

							 if(CommonFunction.checkNull(poolIdMakerVO.getPoolID()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(String.valueOf(poolIdMakerVO.getPoolID()));
							 
								if(CommonFunction.checkNull(poolIdMakerVO.getPoolName()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getPoolName().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getPoolCreationDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(String.valueOf(poolIdMakerVO.getPoolCreationDate()));
								
								if(CommonFunction.checkNull(poolIdMakerVO.getCutOffDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(String.valueOf(poolIdMakerVO.getCutOffDate()));
								
								if(CommonFunction.checkNull(poolIdMakerVO.getPoolType()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getPoolType().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getLbxinstituteID()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getLbxinstituteID().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getMakerID()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(poolIdMakerVO.getMakerID().trim());
								
								if(CommonFunction.checkNull(poolIdMakerVO.getMakerDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(String.valueOf(poolIdMakerVO.getMakerDate()));
								
								insertPrepStmtObject.setSql(bufInsSql.toString());
								qryList.add(insertPrepStmtObject);

					
			              status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					
			
		              logger.info("In insertDataforPoolId."+status);

			}catch(Exception e){
				e.printStackTrace();
			}
			return status;


		}
		
	  public boolean csvReadforpoolId(HttpServletRequest request,HttpServletResponse response, String file,String poolID) throws SQLException 
	  {

	    logger.info("in csvReadforpoolId Read ==>> ");
//	        CallableStatement cst=null;
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
	   	    ArrayList<Object> in =new ArrayList<Object>();
      		ArrayList<Object> out =new ArrayList<Object>();
      		ArrayList outMessages = new ArrayList();
      		 String s1="";
      		String s2="";
//	        Connection utilConn =ConnectionDAO.getConnection();
	        try {
	          
	          PrepStmtObject insertPrepStmtObject=null;
	          session     = request.getSession(false);
	          UserObject userobj=(UserObject)session.getAttribute("userobject");
	  	     String userId=userobj.getUserId();
	  	     makerID=CommonFunction.checkNull(userId);
	  		 vo.setMakerId(""+userId);
	  		 vo.setMakerDate(userobj.getBusinessdate());
	          logger.info("File Name in Process.."+file);
	          if(!file.equals(""))
	          {
	          logger.info(" strFileName is ==>>"+file);
	          String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='POOL_CREATION_UPLOAD'";			
	 		 String strPath=ConnectionDAO.singleReturn(query);
	          strPath     = strPath + "/" + file;
	          logger.info("strPath---"+strPath);
	          objFile1    = new File(strPath);
	          logger.info(" strFile path... ==>>"+objFile1);
	          logger.info(" strFileName < 60 ==>> "+file!=null?file:file.length()+"("+file.length()+")");
	          ArrayList dataList=null;
	          try
	          {
	        	  //Deleting data..start....     
	             // String strDeleteQuery = "DELETE FROM cr_securitization_pool_dtl WHERE MAKER_ID = '"+vo.getMakerId()+"' ";
	             // alDeleteQuery.add(strDeleteQuery);
	            //  boolean status1=ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
	           //   logger.info(" Deleting query is..... ==>>"+status1);
//	             if(status1)
//	             {
//	             	logger.info("Row is deleted....");
//	             }
//	             else
//	             {
//	             	logger.info("Row is not deleted......");
//	             }
	              dataList=new ArrayList();
	              Workbook workbook = Workbook.getWorkbook(new File(strPath));
	              String sheetName[]=workbook.getSheetNames();
	              Sheet sheet;
	              Cell xlsCell;
	              Cell[] cell;
	          
	             
	          

	              for(int p=0; p<sheetName.length; p++)
	              {
	                  sheet = workbook.getSheet(p);
	                  for(int i=0; i<sheet.getRows(); i++)
	                  {
	                	  cell = sheet.getRow(i);
	                	  arr = new ArrayList();
	                      logger.info("cell.length"+cell.length);
	                      for(int j=0; j<cell.length; j++)
	                      {
	                    	
	                          xlsCell = sheet.getCell(j,i);
	                          
	                          arr.add(xlsCell.getContents().toString());
	                                                 
	                          
	                          //dataList.add(xlsCell.getContents().toString());
	                          System.out.println(xlsCell.getContents().toString());
	                      }  dataList.add(arr);	 
	                  }
	              }
	          }
	          catch(Exception exec)
	          {
	              System.out.print("Exception "+exec);
	          }

	       
	         //insert start.............

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
			bufInsSql.append(dbo );
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )" );
			
		
				//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" ); 
 
			logger.info("IN IF---try...checking...-row and col.>>>>>>"+row+".."+col);	
			logger.info("IN IF---try...checking...-row and col.>>>>>>"+dataList.size());	
			
          for(int i=1;i<dataList.size();i++)
          {		
       	  
       	   subList=(ArrayList)dataList.get(i);
				logger.info("In csvReadforpoolId insert into cr_securitization_pool_dtl:-"+subList.size());
	
				if(subList.size()>0){
				
			
       	 String LOAN_ID=CommonFunction.checkNull(subList.get(36));
       	 String LOAN_PRODUCT=CommonFunction.checkNull(subList.get(37));
            String LOAN_SCHEME=CommonFunction.checkNull(subList.get(38));
            String LOAN_CUSTOMER_ID=CommonFunction.checkNull(subList.get(39));
            String LOAN_CUSTOMER_TYPE=CommonFunction.checkNull(subList.get(40));
            String LOAN_CUSTOMER_CATEGORY=CommonFunction.checkNull(subList.get(41));
            String LOAN_CUSTOMER_CONSTITUTION=CommonFunction.checkNull(subList.get(42));
            String LOAN_CUSTOMER_BUSINESS_SEGMENT=CommonFunction.checkNull(subList.get(43));
            String LOAN_INDUSTRY=CommonFunction.checkNull(subList.get(44));
   
            String LOAN_SUB_INDUSTRY=CommonFunction.checkNull(subList.get(46));
            String LOAN_DISBURSAL_DATE=CommonFunction.checkNull(subList.get(21));
            String LOAN_MATURITY_DATE=CommonFunction.checkNull(subList.get(48));
            String LOAN_TENURE=CommonFunction.checkNull(subList.get(28));
            String LOAN_BALANCE_TENURE=CommonFunction.checkNull(subList.get(32));
            String LOAN_INSTL_NUM=CommonFunction.checkNull(subList.get(49));
            String LOAN_ADV_EMI_NUM=CommonFunction.checkNull(subList.get(50));
            String LOAN_INT_RATE=CommonFunction.checkNull(subList.get(51));
            String LOAN_DISBURSAL_STATUS=CommonFunction.checkNull(subList.get(52));
      
            String LOAN_NPA_FLAG=CommonFunction.checkNull(subList.get(53));
            String LOAN_DPD=CommonFunction.checkNull(subList.get(54));
            String LOAN_DPD_STRING=CommonFunction.checkNull(subList.get(55));
            String LOAN_ASSET_COST=CommonFunction.checkNull(subList.get(56));
            String LOAN_AMOUNT=CommonFunction.checkNull(subList.get(17));
            String LOAN_EMI=CommonFunction.checkNull(subList.get(33));
            String LOAN_ADV_EMI_AMOUNT=CommonFunction.checkNull(subList.get(57));
            String LOAN_BALANCE_PRINCIPAL=CommonFunction.checkNull(subList.get(58));
            String LOAN_OVERDUE_PRINCIPAL=CommonFunction.checkNull(subList.get(59));
       
            String LOAN_RECEIVED_PRINCIPAL=CommonFunction.checkNull(subList.get(60));
            String LOAN_OVERDUE_INSTL_NUM=CommonFunction.checkNull(subList.get(61));
            String LOAN_OVERDUE_AMOUNT=CommonFunction.checkNull(subList.get(62));
            String LOAN_BALANCE_INSTL_NUM=CommonFunction.checkNull(subList.get(63));
            String LOAN_BALANCE_INSTL_AMOUNT=CommonFunction.checkNull(subList.get(64));
            String REC_STATUS=CommonFunction.checkNull(subList.get(65));		          
            String makerid=vo.getMakerId();
            String makerdate=vo.getMakerDate();
            
            Integer seq=new Integer(i);
            
            
            insertPrepStmtObject = new PrepStmtObject();
            
            if(CommonFunction.checkNull(poolID).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(poolID);
				
				if(CommonFunction.checkNull(LOAN_ID).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_ID);
				
				if(CommonFunction.checkNull(LOAN_PRODUCT).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_PRODUCT);
				
				
				if(CommonFunction.checkNull(LOAN_SCHEME).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_SCHEME);
				
				if(CommonFunction.checkNull(LOAN_CUSTOMER_ID).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_CUSTOMER_ID);
				
				
				if(CommonFunction.checkNull(LOAN_CUSTOMER_TYPE).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_CUSTOMER_TYPE);
				
				if(CommonFunction.checkNull(LOAN_CUSTOMER_CATEGORY).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_CUSTOMER_CATEGORY);
				
				if(CommonFunction.checkNull(LOAN_CUSTOMER_CONSTITUTION).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_CUSTOMER_CONSTITUTION);
				
				 
				
				if(CommonFunction.checkNull(LOAN_CUSTOMER_BUSINESS_SEGMENT).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_CUSTOMER_BUSINESS_SEGMENT);
				
				if(CommonFunction.checkNull(LOAN_INDUSTRY).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_INDUSTRY);	
				
				if(CommonFunction.checkNull(LOAN_SUB_INDUSTRY).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_SUB_INDUSTRY);
				
				if(CommonFunction.checkNull(LOAN_DISBURSAL_DATE).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_DISBURSAL_DATE);
				
				if(CommonFunction.checkNull(LOAN_MATURITY_DATE).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_MATURITY_DATE);
				
				if(CommonFunction.checkNull(LOAN_TENURE).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_TENURE);	
				
				
				
				if(CommonFunction.checkNull(LOAN_BALANCE_TENURE).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_BALANCE_TENURE);
				
				if(CommonFunction.checkNull(LOAN_INSTL_NUM).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_INSTL_NUM);
				
				if(CommonFunction.checkNull(LOAN_ADV_EMI_NUM).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_ADV_EMI_NUM);
				
				if(CommonFunction.checkNull(LOAN_INT_RATE).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_INT_RATE);	
				
				
				if(CommonFunction.checkNull(LOAN_DISBURSAL_STATUS).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_DISBURSAL_STATUS);
				
				 
				
				if(CommonFunction.checkNull(LOAN_NPA_FLAG).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_NPA_FLAG);
				
				if(CommonFunction.checkNull(LOAN_DPD).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_DPD);	
				if(CommonFunction.checkNull(LOAN_DPD_STRING).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_DPD_STRING);
				
				if(CommonFunction.checkNull(LOAN_ASSET_COST).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_ASSET_COST);
				
				if(CommonFunction.checkNull(LOAN_AMOUNT).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_AMOUNT);
				
				if(CommonFunction.checkNull(LOAN_EMI).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_EMI);	
				
				if(CommonFunction.checkNull(LOAN_ADV_EMI_AMOUNT).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_ADV_EMI_AMOUNT);
				 
				
				if(CommonFunction.checkNull(LOAN_BALANCE_PRINCIPAL).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_BALANCE_PRINCIPAL);
				
				if(CommonFunction.checkNull(LOAN_OVERDUE_PRINCIPAL).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_OVERDUE_PRINCIPAL);
				if(CommonFunction.checkNull(LOAN_RECEIVED_PRINCIPAL).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_RECEIVED_PRINCIPAL);
				
				if(CommonFunction.checkNull(LOAN_OVERDUE_INSTL_NUM).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_OVERDUE_INSTL_NUM);	
				
				if(CommonFunction.checkNull(LOAN_OVERDUE_AMOUNT).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_OVERDUE_AMOUNT);
				 
				
				if(CommonFunction.checkNull(LOAN_BALANCE_INSTL_NUM).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_BALANCE_INSTL_NUM);
				
				if(CommonFunction.checkNull(LOAN_BALANCE_INSTL_AMOUNT).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(LOAN_BALANCE_INSTL_AMOUNT);	
//				if(CommonFunction.checkNull(REC_STATUS).equalsIgnoreCase(""))
//					insertPrepStmtObject.addNull();
//				else
//					insertPrepStmtObject.addString(REC_STATUS);	
				
				if(CommonFunction.checkNull(makerid).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(makerid);			
				
				if(CommonFunction.checkNull(makerdate).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(makerdate);	
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info(""+insertPrepStmtObject.printQuery());
				
				alFinalQry.add(insertPrepStmtObject);  
             	 				
			
				}
			
          }		
		          			 				
		          			 				
	        if(alFinalQry.size()>0)
	        {
	      	  status=ConnectionDAO.sqlInsUpdDeletePrepStmt(alFinalQry); 
	 	           
	      	  
	        }           	
          
//	             cst=utilConn.prepareCall("call Bulk_Upload_Instrument_Dtl(str_to_date(?,'%Y-%m-%d'),?,?,?,?)");
//	             Calendar ca=Calendar.getInstance();
//	         	SimpleDateFormat fmt= new SimpleDateFormat("yyyy-MM-dd ");
//	         	String ct=fmt.format(ca.getTime());           
//	             logger.info("Current time...is........."+ ct);
//	 			cst.setString(1,ct );
//	 			logger.info("file name........."+ file);
//	 			cst.setString(2, file);
//	 			logger.info("Maker id...is........."+ vo.getMakerId());
//	 			cst.setString(3, vo.getMakerId());
//	 			cst.registerOutParameter(4, Types.CHAR);
//	 			cst.registerOutParameter(5, Types.CHAR);
//	 			int st=cst.executeUpdate();
//	 			String s1 = cst.getString(4);
//	 			String s2 = cst.getString(5);
//	 			logger.info("Bulk_Upload_Instrument_Dtl OUT 1: "+s1);
//	 			logger.info("Bulk_Upload_Instrument_Dtl OUT 2: "+s2);
//	 			//int st=cst.executeUpdate();
//	 			if(s1.equals("S")&& s2.equals("NONE"))
//	 			{
//	 				status=true;
//	 			}
//	 			else
//	 			{
//	 				status=false;
//	 			}
	          
//	           logger.info(" After Procedure Call "+st);
	           
	           } /* End Inner Try */
	         catch(IOException ex) {
	      	   logger.error("IOException In Uploading File ==>>"+ ex.getMessage());
	           }
	          }
	          else
	          {
	         	 msg="NF";
	          }   
	          
	      }
	    catch(Exception e)
	    {
	 	 logger.error(e.getMessage());
	    }
	    finally 
	    {	
	      objFile1.delete();
	      request.setAttribute("msg", msg);
	    }
	    return status;
	 }

}
