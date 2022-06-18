package com.caps.daoImplMSSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import com.caps.dao.CollectionDumpDAO;
import com.cm.actionform.ReportsForm;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;



public class MSSQLCollectionDumpDAOImpl implements CollectionDumpDAO{
	
	private static final Logger logger = Logger.getLogger(MSSQLCollectionDumpDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	public ArrayList<ReportsForm> insertDump(String makerID,String businessDate,String fromDate,String toDate){
		
		logger.info("in method of insert dump of CollectionDumpDAOImpl ");		
	
		//logger.info("in insetDump method of reportsDaoImpl:: businessDate::::::::::::::::::>>>>>>"+businessDate);
		
		
		     ArrayList arrList = new ArrayList();
		     ArrayList mainList = new ArrayList();
		     ArrayList subList = new ArrayList();
		
		ArrayList outMessages = new ArrayList();
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		StringBuilder s1=new StringBuilder();
		StringBuilder s2=new StringBuilder();
	
		 logger.info(" Before Procedure Call (COLL_DUMP_REPORT_PROC)"); 
		// logger.info("value of fromDate:::::::::::::>>>>>"+fromDate);
		// logger.info("value of toDate:::::::::::::>>>>>"+toDate);
		//String date1=CommonFunction.changeFormat(CommonFunction.checkNull(fromDate));
		
		
		in.add(makerID);		
		String date1=fromDate.substring(6)+"-"+fromDate.substring(3,5)+"-"+fromDate.substring(0,2);
		String date2=toDate.substring(6)+"-"+toDate.substring(3,5)+"-"+toDate.substring(0,2);
		String date3=businessDate.substring(6)+"-"+businessDate.substring(3,5)+"-"+businessDate.substring(0,2);
		in.add(date1);
  	    in.add(date2);
  	    in.add(date3);
  	    
  	    
  	   // logger.info("value of to date:::::::::::::>>>>>"+date1);
  	   // logger.info("value of to date2:::::::::::::>>>>>"+date2);
  	    //logger.info("value of to date3:::::::::::::>>>>>"+date3);
  	    
	     out.add(s1);
		 out.add(s2);
	
try{
		 outMessages=(ArrayList) ConnectionDAO.callSP("COLL_DUMP_REPORT_PROC",in,out);;
		
		s1.append(CommonFunction.checkNull(outMessages.get(0)));
		s2.append(CommonFunction.checkNull(outMessages.get(1)));
		
		 logger.info("s1::::::::***************==>>"+s1);
		 logger.info("s2::::::::***************==>>"+s2);
	
		if(s1.toString().equalsIgnoreCase("S"))
		{
			logger.info("After proc call..commit message."+s2);
			   StringBuilder sbAppendToSQLCount=new StringBuilder();
               StringBuilder bufInsSql =    new StringBuilder();

                 
	
	  bufInsSql.append("SELECT REGION,BRANCH,PRODUCT,SCHEME,ASSET_DETAILS,LOAN_NO,APPLICATION_NO,DEAL_CATEGORY," +
	                   "MONTH_END_QUEUE_STATE,PREVIOUS_QUEUE,LAST_MONTH_END_DPD,CURRENT_QUEUE,CURRENT_DPD,DEL_STRING," +
	  		"CURRENT_QUEUE_STATE,LAST_ACTION_TAKEN,CUSTOMER_NAME,INSTRUMENT_TYPE,DUE_DAY,EMI_AMOUNT,OVERDUE_EMI_AMT," +
	  		"NINETY_MIN,OD_POS,COLL_AMOUNT,LAST_COLL_DATE,MODE_OF_COLL,BKT_MGR,TEAM_LEAD,FOS,RESIDENCE_ADDRESS," +
	  		"PINCODE,TELE1,TELE2,MOBILE,DEALING_EXECUTIVE,VENDOR_DEALER_NAME,FINAL_DISBURSAL_DATE,CURRENT_EMI_NO," +
	  		"TENURE,LPP,CBC,NO_OF_CHQ_BOUNCE,LOAN_AMOUNT,CUSTOMER_CATEGORY,FATHER_NAME,GAURANTOR_NAME,GAURANTOR_FATHER_NAME," +
	  		"GAURANTOR_ADDRES,GAURANTOR_PINCODE,GAURANTOR_TEL1,GAURANTOR_TEL2,GAURANTOR_MOBILE,TOTAL_OVERDUE_AMT," +
	  		"PENDING_DISBURSAL_AMT,COLLETRAL_MONEY,POS,NET_POS,DISBURSAL_STATUS,LOAN_STATUS,LOAN_MATURITY_DATE,MAKER_ID," +
	  		"MAKER_DATE from coll_temp_dump_rpt_tbl	 where CURRENT_DPD>0 AND maker_id='"+makerID+"'");
	 
	  sbAppendToSQLCount.append("select count(1) from coll_temp_dump_rpt_tbl where maker_id='"+makerID+"'");
			
	  logger.info("Query-----for select data is "+bufInsSql.toString());		
	  logger.info("Query-----for  count is"+sbAppendToSQLCount.toString());
	  
	   mainList = ConnectionDAO.sqlSelect(bufInsSql.toString());
       
       bufInsSql=null;
       sbAppendToSQLCount=null;
			
//			con.commit();
//			con.close();
		}else{
			logger.info("After proc call..rollback.error message."+s2);
//			con.rollback();
//			con.close();
		}   
		
		
		}
		catch(Exception ex){
			ex.printStackTrace();
			 //logger.error(ex.getMessage());
		
		}
	finally 
	{  
	s1=null;
	s2=null;
	
	}
 return mainList;
	}
}
