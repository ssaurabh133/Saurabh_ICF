package com.connect;


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;



public class CommonFunctionForEJB {
	
	private static final Logger logger = Logger.getLogger(CommonFunctionForEJB.class.getName());
	static ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	static String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");

	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");

		
	String singleRowNumber=resource.getString("lbl.singleRowNumber");
	String doubleRowNumber=resource.getString("lbl.doubleRowNumber");
	String between=resource.getString("lbl.between");
	

	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	public static String checkNull(Object requestString) {
		if (requestString != null) {
			return requestString.toString();
		} else {
			requestString=null;
			return "";
		}
	}
//neeraj space start
	public static boolean check(String date)
	{
		boolean status=false;
		if(date != null && date.trim().length()==10)
		{
			int i=0;
			for(i=0;i<10;i++)
			{
				if(i==2 || i==5)
				{
					if((int)date.charAt(i) != 45)
						return status;
				}
				else
				{
					if((int)date.charAt(i)<48 || (int)date.charAt(i)>57)
						return status;
					if((int)date.charAt(0)==48 && (int)date.charAt(1)==48)
						return status;
					if((int)date.charAt(3)==48 && (int)date.charAt(4)==48)
						return status;
					if((int)date.charAt(6)==48 && (int)date.charAt(7)==48 && (int)date.charAt(8)==48 && (int)date.charAt(9)==48)
						return status;
				}
			}
			if(i==10)
				status=true;
		}
		date=null;
		return status;		
	}
	public static boolean check2(String date)
	{
		boolean status=false;
		if(date != null && date.trim().length()==10)
		{
			int i=0;
			for(i=0;i<10;i++)
			{
				if(i==4 || i==7)
				{
					if((int)date.charAt(i) != 45)
						return status;
				}
				else
				{
					if((int)date.charAt(i)<48 || (int)date.charAt(i)>57)
						return status;
					if((int)date.charAt(8)==48 && (int)date.charAt(9)==48)
						return status;
					if((int)date.charAt(5)==48 && (int)date.charAt(6)==48)
						return status;
					if((int)date.charAt(0)==48 && (int)date.charAt(1)==48 && (int)date.charAt(2)==48 && (int)date.charAt(3)==48)
						return status;
				}
			}
			if(i==10)
				status=true;
		}
		date=null;
		return status;		
	}
	public static String changeFormat(String strDate)
	{
		String date="";
		boolean status=false;
		status=check(strDate);
		if(status)
			date=strDate.substring(6)+"-"+strDate.substring(3,5)+"-"+strDate.substring(0,2);
		
		strDate=null;
		return date;
	}
	public static String changeFormatJSP(String strDate)
	{
		String date="";
		boolean status=false;
		status=check2(strDate);
		if(status)
			date=strDate.substring(8)+"-"+strDate.substring(5,7)+"-"+strDate.substring(0,4);
		
		strDate=null;
		return date;
	}
//neeraj space end

	public static String dateFormatConvert(String strDate) throws ParseException
    {
    	SimpleDateFormat sdf=new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");
		SimpleDateFormat sdf1=new SimpleDateFormat("MM/dd/yyyy");
		String conDate=sdf1.format(sdf.parse(strDate).getTime());
		strDate=null;
		sdf=null;
		sdf1=null;
		return conDate;
    }
	public static String editableFlag() {
		String editableEuery="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EDITABLE_FLAG'";
		String flag=ConnectionDAO.singleReturn(editableEuery);
		editableEuery=null;
		return flag;
	}
	public static boolean insertsecurity(String loanId) {
	
		boolean status = false;
		
		ArrayList qryList = new ArrayList();
		ArrayList subList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuffer bufInsSql=null;
		String chk=null;
		String query=null;
		String dealId=null;
		StringBuilder bufInsQuery=null;
		String sdQuery=null;
		ArrayList mainlist=null;
		 ArrayList dataSd=null;
		 String freq=null;
		 String rate=null;
		 String tenure=null;
		try
		{
			 query="select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId;
			
			logger.info("LOAN_DEAL_ID from cr_loan_dtl query "+query);
			
			 dealId = ConnectionDAO.singleReturn(query);
			
			
			//query="select DEAL_CHARGE_FINAL_AMOUNT from  cr_loan_dtl L,cr_txncharges_dtl C where L.LOAN_ID="+loanId+" and L.LOAN_ID=C.TXN_ID and c.DEAL_CHARGE_CODE=103 and TXN_TYPE='LIM'";
			// asesh space
			bufInsQuery=new StringBuilder();
			bufInsQuery.append("select ");
			String dbType=resource.getString("lbl.dbType");
			String dbo=resource.getString("lbl.dbPrefix");
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				bufInsQuery.append("TOP 1 ");
			}
			bufInsQuery.append("DEAL_CHARGE_FINAL_AMOUNT from  cr_loan_dtl L,cr_txncharges_dtl C where L.LOAN_ID="+loanId+" and L.LOAN_ID=C.TXN_ID and c.DEAL_CHARGE_CODE=103 and TXN_TYPE='LIM' ");
			if(!dbType.equalsIgnoreCase("MSSQL") && dbo.equalsIgnoreCase(""))
			{
				bufInsQuery.append("LIMIT 1");
			}
			logger.info("SD Amount from cr_txncharges_dtl query "+bufInsQuery.toString());
			String sdAmount = ConnectionDAO.singleReturn(bufInsQuery.toString());
			//logger.info("SD Amount from cr_txncharges_dtl amount "+sdAmount);
			 sdQuery="select SD_COMPOUNDING_FREQ,SD_INTEREST_RATE,SD_INTEREST_TYPE,SD_TENURE,SD_INTEREST,REC_STATUS,MAKER_ID,MAKER_DATE,SD_ADJUSTMENT" +
					" from cr_deal_sd_m where DEAL_ID='"+dealId+"'";
			logger.info("LOAN_DEAL_ID from cr_deal_sd_m query "+sdQuery);
			mainlist=ConnectionDAO.sqlSelect(sdQuery);
		//	logger.info("LOAN_DEAL_ID from cr_deal_sd_m query "+mainlist);
			
		if(mainlist!=null && mainlist.size()>0)
   		 {
   			 double totalInt=0;
   			 int freqMonth=0;
   			 int reqFreq=0;
   			  dataSd=(ArrayList)mainlist.get(0);
   			  freq=CommonFunctionForEJB.checkNull(dataSd.get(0)).trim();
   			  rate=CommonFunctionForEJB.checkNull(dataSd.get(1)).trim();
   			  tenure=CommonFunctionForEJB.checkNull(dataSd.get(3)).trim();
   			 //String sdAmount=CommonFunction.checkNull(dataSd.get(4)).trim();
   			// logger.info("Fetch Record: SD_COMPOUNDING_FREQ: "+freq+" SD_INTEREST_RATE: "+rate+" SD_INTEREST_TYPE: "+dataSd.get(2)+" SD_TENURE: "+tenure+" SD_AMOUNT: "+!sdAmount.equalsIgnoreCase(""));
   			 if(CommonFunctionForEJB.checkNull(dataSd.get(2)).equalsIgnoreCase("S"))
   			 {
   				 //logger.info("sdAmount: "+sdAmount);
   				 if(!sdAmount.equalsIgnoreCase("") && !tenure.equalsIgnoreCase("") && !rate.equalsIgnoreCase(""))
   				 totalInt=(Double.parseDouble(sdAmount)*Double.parseDouble(rate)*Double.parseDouble(tenure))/(12*100);
   				// logger.info("totalInt: "+totalInt);
   				
   			 }
   			 if(CommonFunctionForEJB.checkNull(dataSd.get(2)).equalsIgnoreCase("C"))
   			 {
   				 //logger.info("In Coupound part: "+dataSd.get(2)+" freq: "+freq); 
   				 if(freq.equalsIgnoreCase("M"))
   					{
   						freqMonth=1;
   					}
   					else if(freq.equalsIgnoreCase("B"))
   					{
   						freqMonth=2;
   					}
   					else if(freq.equalsIgnoreCase("Q"))
   					{
   						freqMonth=3;
   					}
   					else if(freq.equalsIgnoreCase("H"))
   					{
   						freqMonth=6;
   					}
   					else if(freq.equalsIgnoreCase("Y"))
   					{
   						freqMonth=12;
   					}
   					if(freqMonth!=0)
   					{
   						reqFreq=(int)12/freqMonth;
   					}
   					
   					//logger.info("sdAmount: "+sdAmount+" tenure: "+tenure+"rate: "+rate+" reqFreq "+reqFreq);
   					if(!sdAmount.equalsIgnoreCase("") && !tenure.equalsIgnoreCase("") && !rate.equalsIgnoreCase(""))
   					totalInt=(Double.parseDouble(sdAmount) * Math.pow(1+(Double.parseDouble(rate)/(reqFreq*100)),(Double.parseDouble(tenure)*reqFreq/12))) - Double.parseDouble(sdAmount);
   				
   					
			
   			 }
   		 
			
			 query = "select count(TXN_ID) from cr_sd_m where TXN_ID="+loanId;
			 chk = ConnectionDAO.singleReturn(query);
			
			
				if(Integer.parseInt(chk) <= 0)
				{
					
					
					 bufInsSql = new StringBuffer();
				bufInsSql.append("insert into cr_sd_m(TXN_TYPE,TXN_ID,SD_AMOUNT,SD_INTEREST_TYPE,SD_INTEREST_RATE,SD_COMPOUNDING_FREQ," +
						"SD_TENURE,SD_INTEREST,SD_ADJUSTMENT,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values (");
				bufInsSql.append(" ?,"); // TXN_TYPE
				bufInsSql.append(" ?,"); // TXN_ID
				bufInsSql.append(" ?,"); //SD_AMOUNT
				bufInsSql.append(" ?,"); // SD_INTEREST_TYPE
				bufInsSql.append(" ?,"); //SD_INTEREST_RATE
				bufInsSql.append(" ?,"); // SD_COMPOUNDING_FREQ
				bufInsSql.append(" ?,"); // SD_TENURE
				bufInsSql.append(" ?,"); // SD_INTEREST
				bufInsSql.append(" ?,");//SD_ADJUSTMENT
				bufInsSql.append(" ?,"); // REC_STATUS
				bufInsSql.append(" ?,"); // MAKER_ID
				bufInsSql.append(" ? )"); // maker_date
				insertPrepStmtObject.addString("LIM");
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(loanId).trim()));
				if (CommonFunctionForEJB.checkNull(sdAmount).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(sdAmount).trim()));
				//insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(sdAmount).trim()));
				int size=mainlist.size();
				for (int i = 0; i < size; i++) {

					subList = (ArrayList) mainlist.get(i);
					
					if (subList.size() > 0) 
					{ 
						
				if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(2)).trim()));
				
				if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(1)).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(1)).trim()));
				if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(0)).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(0)).trim()));
				if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(3)).trim()));
				if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(totalInt).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(totalInt).trim()));
				if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(8)).trim()));
				
				if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(5)).trim()));
				
				if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(6)).trim()));
				if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else  
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(7)).trim()));
				
					}
				}
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("status......................"+ status);
				}
				
   		 }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e=null;
		}
		finally
		{
			loanId=null;
			insertPrepStmtObject=null;
			qryList.clear();
			qryList=null;
			bufInsSql=null;
			chk=null;
			query=null;
			dealId=null;
			bufInsQuery=null;
			sdQuery=null;
			mainlist=null;
			dataSd=null;
			freq=null;
 			rate=null;
 			tenure=null;
		}
		
		return status;
	}

	public static boolean insertInstallment(int loanId) {
		
		boolean status = false;
		ArrayList qryList = new ArrayList();
		ArrayList subList = null;
		String query=null;
		StringBuffer bufInsSql = null;
		PrepStmtObject insertPrepStmtObject =null;
		String dealId=null;
		String sdQuery=null;
		ArrayList mainlist=null;
		try
		{
			 query="select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId;
			
			logger.info("LOAN_DEAL_ID from cr_loan_dtl query "+query);
			
			 dealId = ConnectionDAO.singleReturn(query);
			
			//logger.info("LOAN_DEAL_ID(dealId) from cr_loan_dtl query "+dealId);
			//query="select DEAL_CHARGE_FINAL_AMOUNT from  cr_loan_dtl L,cr_txncharges_dtl C where L.LOAN_ID="+loanId+" and L.LOAN_ID=C.TXN_ID and c.DEAL_CHARGE_CODE=103 and TXN_TYPE='LIM'";
			
			 sdQuery="select SEQ_NO,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE,RECOVERY_TYPE" +
			" from cr_deal_installment_plan where DEAL_ID='"+dealId+"'";
			
			logger.info(" from cr_deal_installment_plan query "+sdQuery);
			 mainlist=ConnectionDAO.sqlSelect(sdQuery);
			logger.info("LOAN_DEAL_ID from cr_deal_sd_m query "+mainlist);
			//query = "select count(LOAN_ID) from cr_installment_plan where LOAN_ID="+loanId;
			//String chk = ConnectionDAO.singleReturn(query);
			query = "delete from cr_installment_plan where LOAN_ID="+loanId;
			ArrayList delQ=new ArrayList();
			delQ.add(query);
			boolean st = ConnectionDAO.sqlInsUpdDelete(delQ);
			logger.info("delete installment plan: "+query+" status: "+st);
			
			//if(Integer.parseInt(chk) <= 0)
			//{
				
				if(mainlist!=null)
				{
						int max=mainlist.size();
					
					
					for (int i = 0; i<max ; i++) {
						
						 bufInsSql = new StringBuffer();
						 insertPrepStmtObject = new PrepStmtObject();
					bufInsSql.append("insert into cr_installment_plan(LOAN_ID,SEQ_NO,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT," +
							"INSTALLMENT_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE,RECOVERY_TYPE)");
					bufInsSql.append(" values (");
					bufInsSql.append(" ?,"); // LOAN_ID
					bufInsSql.append(" ?,"); // SEQ_NO
					bufInsSql.append(" ?,"); //FROM_INSTL_NO
					bufInsSql.append(" ?,"); // TO_INSTL_NO
					bufInsSql.append(" ?,"); //RECOVERY_PERCENT
					bufInsSql.append(" ?,"); // PRINCIPAL_AMOUNT
					bufInsSql.append(" ?,"); // INSTALLMENT_AMOUNT
					bufInsSql.append(" ?,"); // REC_STATUS
					bufInsSql.append(" ?,"); // MAKER_ID
					bufInsSql.append(" ?,"); // maker_date
					bufInsSql.append(" ? )");//RECOVERY_TYPE
				
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(loanId).trim()));
					
						subList = (ArrayList) mainlist.get(i);
						//logger.info("In getloanListInLoan..." + subList.size());
						if (subList.size() > 0) 
						{ 
							
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(0)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(0)).trim()));
					
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(1)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(1)).trim()));
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(2)).trim()));
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(3)).trim()));
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(4)).trim()));
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(5)).trim()));
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(6)).trim()));
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(7)).trim()));
					
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(8)).trim()));
					
					//logger.info("Recovery Type: "+CommonFunction.checkNull(subList.get(9)).trim());
					
					if (StringEscapeUtils.escapeHtml(CommonFunctionForEJB.checkNull(subList.get(9)).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else  
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunctionForEJB.checkNull(subList.get(9)).trim()));
					
					
						}
						
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("insert query1 ### "+ insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
					}
				
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("status......................"+ status);
			}	

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			query=null;
			qryList.clear();
			qryList=null;
			insertPrepStmtObject=null;
			bufInsSql=null;
			dealId=null;
			sdQuery=null;
			mainlist=null;
			subList=null;
		}
	
		
		return status;
	}
	
	public static String stageMovement(String companyId,String txnType,String action, String dealId,String stage,String bDate,String makerId) {
		CallableStatement cst=null;
		String s1=null;
		String s2=null;
		String  status=null;
		String  productType=null;
		Connection con=ConnectionDAOforEJB.getConnection();
		String dbType=resource.getString("lbl.dbType");
		String dbo=resource.getString("lbl.dbPrefix");
		String chekQ=null;
		String checkCount=null;
		StringBuilder bufSql=null;
		StringBuilder pTypeQ=null;
		try {
			//con.setAutoCommit(false);
			if(action.equalsIgnoreCase("I"))
			{
				productType="SME";
			}
			else
			{
				 pTypeQ=new StringBuilder();
				pTypeQ.append("select ");
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					pTypeQ.append("TOP 1 ");
				}
				pTypeQ.append("DEAL_PRODUCT_CATEGORY from cr_deal_loan_dtl where DEAL_ID="+dealId.trim()+" ");
				if(!dbType.equalsIgnoreCase("MSSQL") && dbo.equalsIgnoreCase(""))
				{
					pTypeQ.append("LIMIT 1");
				}
				//logger.info("pTypeQ "+pTypeQ.toString());
				productType=ConnectionDAOforEJB.singleReturn(pTypeQ.toString()) ;
			}
			//logger.info("In stageMovement proc( Stage_Movement )");
			logger.info("In stageMovement: action(Insert/Forward) "+action+" dealId: "+dealId+" stage: "+stage+" maker Id: "+makerId+" bDate: "+bDate+" txnType: "+txnType+"companyId: "+companyId+"productType: "+productType);
			// asesh space
			 bufSql=new StringBuilder();
			
			bufSql.append("{call Stage_Movement(?,?,?,?,?,?,?,?,?,?)}");
			cst=con.prepareCall(bufSql.toString());
			cst.setString(1, companyId);
			cst.setString(2, txnType);
			cst.setString(3, dealId);
			cst.setString(4, productType);
			cst.setString(5, action);
			cst.setString(6, stage);
			cst.setString(7, changeFormat(bDate));
			cst.setString(8, makerId);
			cst.registerOutParameter(9, Types.CHAR);
			cst.registerOutParameter(10, Types.CHAR);
			//logger.info("s1: "+cst.toString());
			cst.execute();
			s1= cst.getString(9);
			s2= cst.getString(10);
			if(s1!=null && s1.equalsIgnoreCase("S"))
			{
				status=s1;
				//con.commit();
				 chekQ="select count(POLICY_DECISION_ID) from cr_policy_decision 	where DEAL_ID ='"+dealId+"' and RULE_ACTION = 'S'";
				 checkCount=ConnectionDAOforEJB.singleReturn(chekQ);
				if(!CommonFunctionForEJB.checkNull(checkCount).equalsIgnoreCase("0"))
				{
					s1="E";
					status="SOME RULES ARE FAILED, PLEASE CHECK";
				}
			//	logger.info("checkCount:  -------------------"+checkCount);
				//logger.info("s1: "+s1);
				//logger.info("Procedure Error Message----"+s2);
			}
			else
			{
				status=s2;
			//	con.rollback();
				//logger.info("s1: "+s1);
				//logger.info("Procedure Error Message----"+s2);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			e=null;
		}
		finally
		{
			
			try {
				cst.close();
				con.close();
				cst=null;
				con=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e=null;
			}
			companyId=null;
			txnType=null;
			action=null;
			dealId=null;
			stage=null;
			bDate=null;
			makerId=null;
			s1=null;
			s2=null;
			chekQ=null;
			checkCount=null;
			bufSql=null;
			pTypeQ=null;
			productType=null;
			
		}
		return status;
	}
	
	 public static boolean isParsableToInt(String i)
	   {
	   try
	   {
	   Integer.parseInt(i);
	   i=null;
	   return true;
	   }
	   catch(NumberFormatException nfe)
	   {
		   nfe=null;
	   return false;
	   }
	   }
	 
	 public static Object removePadding(Object number){
		 
		     Object minVal=number;
		     if(!checkNull(number).equalsIgnoreCase(""))
		     {
	
			     String num=number.toString();
			     
			     minVal= Integer.parseInt(num);
			    // logger.info("minVal 122: "+minVal);
		     }
		     //logger.info("minVal: "+minVal);
		     number=null;
		     return minVal;
			}
	 
	 public static String loanStageMovement(String loanId,String txnType,String bDate,String makerId) {
			CallableStatement cst=null;
			String s1=null;
			String s2=null;
			String  status=null;
			String chekQ=null;
			String checkCount=null;
			StringBuilder bufSql=null;
			String dbType=resource.getString("lbl.dbType");
			//String dbo=resource.getString("lbl.dbPrefix");
			Connection con=ConnectionDAO.getConnection();
		
			try {
				con.setAutoCommit(false);
				
				logger.info("In loanStageMovement: action(Insert/Forward) loanId: "+loanId+" txnType: "+txnType+" maker Id: "+makerId+" bDate: "+bDate);
				 bufSql=new StringBuilder();
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufSql.append("{call Loan_Stage_Movement(?,?,?,?,?,?)}");
				}
				else
				{
					bufSql.append("{call Loan_Stage_Movement(?,?,STR_TO_DATE(?,'"+dateFormatWithTime+"'),?,?,?)}");
				}
				
				cst=con.prepareCall(bufSql.toString());
				cst.setString(1, loanId);
				cst.setString(2, txnType);
				cst.setString(3, bDate);
				cst.setString(4, makerId);
				cst.registerOutParameter(5, Types.CHAR);
				cst.registerOutParameter(6, Types.CHAR);
				//logger.info("s1: "+cst.toString());
				cst.execute();
				s1= cst.getString(5);
				s2= cst.getString(6);
				if(s1!=null && s1.equalsIgnoreCase("S"))
				{
					status=s1;
					con.commit();
					 chekQ="select count(LOAN_DEVIATION_ID) from cr_loan_deviation_dtl 	where LOAN_ID ='"+loanId+"' and RULE_ACTION = 'S'";
					 checkCount=ConnectionDAO.singleReturn(chekQ);
					if(!CommonFunctionForEJB.checkNull(checkCount).equalsIgnoreCase("0"))
					{
						s1="E";
						status="SOME RULES ARE FAILED, PLEASE CHECK";
					}
					//logger.info("checkCount:  -------------------"+checkCount);
					//logger.info("s1: "+s1);
					//logger.info("Procedure Error Message----"+s2);
				}
				else
				{
					status=s2;
					con.rollback();
					//logger.info("s1: "+s1);
					//logger.info("Procedure Error Message----"+s2);
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
				e=null;
			}
			finally
			{
				
				try {
					cst.close();
					con.close();
					cst=null;
					con=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					e=null;
				}
				s1=null;
				s2=null;
				chekQ=null;
				checkCount=null;
				dbType=null;
				bufSql=null;
				loanId=null;
				bDate=null;
				makerId=null;
				txnType=null;
				
			}
			return status;
		}
	 
		public static String editableDisbursalScheduleFlag() {
			String q="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='DISBURSAL_SCHEDULE_EDITABLE'";
			String flag=ConnectionDAO.singleReturn(q);
			q=null;
			return flag;
		}
		
		public static String rowNumber(String unq1) {
			
			String rowNum="";
			if(!checkNull(new CommonFunctionForEJB().singleRowNumber).equalsIgnoreCase(""))
			{
				rowNum=new CommonFunctionForEJB().singleRowNumber.replace("UNQ1", unq1);
			}
			unq1=null;
			return rowNum;
		}
		
		public static java.sql.Date changeStringToDate(String stringDate) {
			 ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
			   String dateFormatStr = resource.getString("lbl.dateForDisbursal");
			   
			Date date = null;
			java.sql.Date convertDate = null;
			try {
				date = new SimpleDateFormat(dateFormatStr).parse(stringDate);
				convertDate = new java.sql.Date(date.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertDate;
		}

	public static BigDecimal decimalNumberConvert(String decimalNumber) {
				
		    if(CommonFunctionForEJB.checkNull(decimalNumber).equalsIgnoreCase(""))
		    {
		    	decimalNumber="0";
		    }
			decimalNumber=decimalNumber.replace(",","");
	        BigDecimal decimalNum= new BigDecimal(decimalNumber);
	        decimalNumber=null;
			return decimalNum;
		}
     
}
