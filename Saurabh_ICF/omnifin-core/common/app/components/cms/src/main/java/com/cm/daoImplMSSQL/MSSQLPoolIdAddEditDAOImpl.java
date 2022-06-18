package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.connect.PrepStmtObject;
import com.cm.dao.PoolIdAddEditDAO;
import com.cm.vo.PoolIdMakerVO;

public class MSSQLPoolIdAddEditDAOImpl implements PoolIdAddEditDAO{
	
	private static final Logger logger = Logger.getLogger(MSSQLPoolIdAddEditDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	public ArrayList<PoolIdMakerVO> searchPoolIdAddEditGrid(PoolIdMakerVO poolIdMakerVO)
	{
		 logger.info("In searchPoolIdAddEditGrid() method of PoolIdAddEditDAOImpl");
      	 String poolId="";
      	 String poolName="";
      	 String poolCreationDate="";
      	 String cutOffDate="";
      	 String instituteId="";
      	 int count=0;
	     int startRecordIndex=0;
	     int endRecordIndex = no; 
	     ArrayList<PoolIdMakerVO> getPoolIdAddEditList=new ArrayList<PoolIdMakerVO>();
	 	 try
	 	 {
	 		  ArrayList searchlist=new ArrayList();
	 		  logger.info("In searchPoolIdAddEditGrid....................");
	 		  boolean appendSQL=false;
	 		  StringBuffer bufInsSql =	new StringBuffer();
	 		  StringBuffer bufInsSqlTempCount =	new StringBuffer();
	 		  poolId=StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getPoolID()).trim());
	 		  logger.info("In searchPoolIdAddEditGrid......poolId  "+poolId);
	 		  poolName=StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getPoolName()).trim());
	 		  logger.info("In searchPoolIdAddEditGrid......poolName  "+poolName);
	 		  poolCreationDate=StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getPoolCreationDate()).trim());
	 		  logger.info("In searchPoolIdAddEditGrid......poolCreationDate  "+poolCreationDate);            	     
	 		  cutOffDate= StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getCutOffDate()).trim());
	 		  logger.info("In searchPoolIdAddEditGrid......cutOffDate  "+cutOffDate);
	 		  instituteId= StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolIdMakerVO.getInstituteID()).trim());
	 		  logger.info("In searchPoolIdAddEditGrid......instituteId  "+instituteId);
	 		  
	 		
	 		  bufInsSql.append(" select P.pool_id,P.pool_name,");
	 		  bufInsSql.append(dbo);
	 		  bufInsSql.append("DATE_FORMAT(P.pool_creation_date,'"+dateFormat+"') as pool_creation_date,");
	 		  bufInsSql.append(dbo);
	 		  bufInsSql.append("DATE_FORMAT(P.POOL_CUTOFF_DATE,'"+dateFormat+"') as POOL_CUTOFF_DATE,A.AGENCY_CODE,A.AGENCY_NAME  ");
	 		
	 		 bufInsSql.append(" from cr_securitization_pool_m P,com_agency_m A where P.POOL_INSTITUTION_ID=A.AGENCY_CODE " +
 		 		"  and P.rec_status='A' ");
 		      
	 		  bufInsSqlTempCount.append("  select  count(1) from cr_securitization_pool_m P,com_agency_m A where  "+
	 		  	"  P.POOL_INSTITUTION_ID=A.AGENCY_CODE   and P.rec_status='A' " );
	 		  
	 		  if(!poolId.trim().equalsIgnoreCase(""))
	 		  {
	 			  bufInsSql.append(" AND P.pool_id='"+poolId+"'");
	 			  bufInsSqlTempCount.append(" AND P.pool_id='"+poolId+"'");
	 		  }
	 		  if(!poolName.trim().equalsIgnoreCase(""))
	 		  {
	 			  bufInsSql.append(" AND P.pool_name LIKE '%"+poolName+"%'");
	 			  bufInsSqlTempCount.append(" AND P.pool_name LIKE '%"+poolName+"%'");
	 		  }
	 		  if(!poolCreationDate.trim().equalsIgnoreCase(""))
	 		  {
	 			  String date =CommonFunction.changeFormat(poolCreationDate);
	 			  bufInsSql.append(" AND P.pool_creation_date='"+date+"'");
	 			  bufInsSqlTempCount.append(" AND P.pool_creation_date='"+date+"'");
	 		  }
	 		  if(!cutOffDate.trim().equalsIgnoreCase(""))
	 		  {
	 			  String date =CommonFunction.changeFormat(cutOffDate);
	 			  bufInsSql.append(" AND P.POOL_CUTOFF_DATE='"+date+"'");
	 			  bufInsSqlTempCount.append(" AND P.POOL_CUTOFF_DATE='"+date+"'");
	 		  }
	 		  if(!instituteId.trim().equalsIgnoreCase(""))
	 		  {
	 			  bufInsSql.append(" AND A.AGENCY_NAME='"+instituteId+"'");
	 			  bufInsSqlTempCount.append(" AND A.AGENCY_NAME='"+instituteId+"'");
	 		  }
	 		  logger.info("Count Query :  "+bufInsSqlTempCount.toString());
	 		  count =Integer.parseInt(ConnectionUploadDAO.singleReturn(bufInsSqlTempCount.toString()));
	 		  startRecordIndex = (poolIdMakerVO.getCurrentPageLink()-1)*no;
	 		  endRecordIndex = no;
	 		 logger.info("startRecordIndex .................... "+startRecordIndex);
	 		logger.info("endRecordIndex .................... "+endRecordIndex);
	 		
	 		  	bufInsSql.append(" ORDER BY P.pool_id OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
			// bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			
	  			
	 		  logger.info("Search Query  :  "+bufInsSql.toString());
	 		  searchlist = ConnectionUploadDAO.sqlSelect(bufInsSql.toString());
	 		  
	 		  for(int i=0;i<searchlist.size();i++)
	 		  {
	 			   ArrayList data=(ArrayList)searchlist.get(i);
	 			   if(data.size()>0)
	 			   {
	 				   PoolIdMakerVO VO = new PoolIdMakerVO();
	 				   VO .setModifyNo("<a href=poolIdAddEditProcessAction.do?method=searchedPoolEditData&poolID="+ CommonFunction.checkNull(data.get(0)).toString()+">"+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");   
					   VO.setPoolID((CommonFunction.checkNull(data.get(0)).trim()));
					   VO.setPoolName((CommonFunction.checkNull(data.get(1)).trim()));
					   VO.setPoolCreationDate((CommonFunction.checkNull(data.get(2)).trim()));
					   VO.setCutOffDate((CommonFunction.checkNull(data.get(3)).trim()));                 	   
					   VO.setLbxinstituteID((CommonFunction.checkNull(data.get(4)).trim()));
					   VO.setInstituteID((CommonFunction.checkNull(data.get(5)).trim()));
					   VO.setTotalRecordSize(count);  
					   getPoolIdAddEditList.add(VO);	
	 			   }
		      }
		}catch(Exception e){
			e.printStackTrace();
				}
		return  getPoolIdAddEditList;	
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
	 					
	 		
	 				   StringBuilder query=new StringBuilder();		//String query asesh
				       query.append("select P.pool_id,P.pool_name,");
				       query.append(dbo);
				       query.append("DATE_FORMAT(P.POOL_CREATION_DATE,'"+dateFormat+"') as POOL_CREATION_DATE," +
				       		"");
				       query.append(dbo);
				       query.append("DATE_FORMAT(P.POOL_CUTOFF_DATE,'"+dateFormat+"') as POOL_CUTOFF_DATE,P.POOL_TYPE,A.AGENCY_CODE,A.AGENCY_NAME" +
				  		" from cr_securitization_pool_m P,com_agency_m A where P.POOL_INSTITUTION_ID=A.AGENCY_CODE  " +    
				  		" AND POOL_ID='"+(StringEscapeUtils.escapeSql(CommonFunction.checkNull(poolID).trim()))+"'" +
				  		" and P.rec_status='A' ");
				       


	 			logger.info("In savedPoolIdMakerData:-"+query);	
	 			
	 			mainList=ConnectionUploadDAO.sqlSelect(query.toString());
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
		StringBuilder query=new StringBuilder();		//String query asesh
		query.append("select POOL_ID,LOAN_ID, LOAN_PRODUCT,P.PRODUCT_DESC, LOAN_SCHEME,S.SCHEME_DESC," +
		 " CUSTOMER_NAME, LOAN_CUSTOMER_TYPE, LOAN_CUSTOMER_CATEGORY, LOAN_CUSTOMER_CONSTITUTION,"+
		 " LOAN_CUSTOMER_BUSINESS_SEGMENT, LOAN_INDUSTRY,I.INDUSTRY_DESC, LOAN_SUB_INDUSTRY,"+
		 " SI.SUB_INDUSTRY_DESC, ");
		query.append(dbo);
		query.append("DATE_FORMAT(LOAN_DISBURSAL_DATE,'"+dateFormat+"') as LOAN_DISBURSAL_DATE, ");
		query.append(dbo);
		query.append("DATE_FORMAT(LOAN_MATURITY_DATE,'"+dateFormat+"') as LOAN_MATURITY_DATE,"+
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
			" where pool_id ="+poolID+" and A.rec_status = 'A' ");
		
		logger.info("query :"+query);
		arrList = ConnectionUploadDAO.sqlSelect(query.toString());
		
		
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
	 public boolean insertLoanforPoolIdEdit(PoolIdMakerVO poolIdMakerVO, int companyId) 
	 {
		 logger.info("In insertLoanforPoolIdEdit() method of PoolIdAddEditDAOImpl");
 		 ArrayList qryList =  new ArrayList();
 		 boolean status=false;
 		 StringBuffer bufInsSql =new StringBuffer();
 		 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
 		 ArrayList<Object> in =new ArrayList<Object>();
 		 ArrayList<Object> out =new ArrayList<Object>();
 		 ArrayList outMessages = new ArrayList();
 		 String s1="";
 		 String s2="";
 		 try 
 	     { 
 	        int statusProc=0;                   	
       		in.add(companyId);
    		in.add( Integer.parseInt(poolIdMakerVO.getPoolID()));
    		in.add(Integer.parseInt(poolIdMakerVO.getLbxLoanNoHID()));
    		String date=CommonFunction.changeFormat(CommonFunction.checkNull( poolIdMakerVO.getMakerDate()).trim());
    	 	if(date != null)
    	 	in.add(date);
    	    in.add(poolIdMakerVO.getMakerID());
    		in.add("A");
    		in.add("D");
    		out.add(s1);
 	        out.add(s2);
 	        logger.info("Securitization_Pool_Authorization("+in.toString()+","+out.toString()+")");
 	        outMessages=(ArrayList) ConnectionUploadDAO.callSP("Securitization_Pool_Authorization",in,out);
 	        s1=CommonFunction.checkNull(outMessages.get(0));
 			s2=CommonFunction.checkNull(outMessages.get(1));
    		logger.info("s1: "+s1);
    		logger.info("s2: "+s2);
   			if(s1.equalsIgnoreCase("S"))
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
   					bufInsSql.append(" 'A'," ); 
   					bufInsSql.append(" ?," ); 
   					bufInsSql.append(dbo);
   					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)) " ); //maker_date
   					
 		
 						

 						 if(CommonFunction.checkNull(poolIdMakerVO.getPoolID()).trim().equalsIgnoreCase(""))
 								insertPrepStmtObject.addNull();
 							else
 								insertPrepStmtObject.addString(poolIdMakerVO.getPoolID().trim());
 						 
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
 				
    			}
 	                 logger.info("In insertLoanforPoolIdEdit."+status);
 	                 
 	   
         
 		}catch(Exception e){
 			e.printStackTrace();
 		}


 
 		
 		return status;
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
					  bufInsSql.append(" select  *,MAKER_ID USERID, ");
					  bufInsSql.append(dbo);
					  bufInsSql.append("SYSDATE() FROM cr_securitization_pool_dtl " );
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
