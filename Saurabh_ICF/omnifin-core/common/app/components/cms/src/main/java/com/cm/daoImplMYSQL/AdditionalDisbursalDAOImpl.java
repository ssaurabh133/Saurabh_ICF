package com.cm.daoImplMYSQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.AdditionalDisbursalDAO;
import com.cm.vo.AdditionalDisbursalProcessVO;
import com.cm.vo.AdditionalDisbursalSearchVO;


import com.cm.vo.LoanInitAuthorVo;


import com.cm.vo.PartPrePaymentSearchVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.RepayScheduleVo;
import com.cp.vo.CodeDescVo;
public class AdditionalDisbursalDAOImpl implements AdditionalDisbursalDAO
{

	private static final Logger logger = Logger.getLogger(AdditionalDisbursalDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList searchAdditionalDisbMakerData(AdditionalDisbursalSearchVO vo,
			String type) {
		logger.info("In searchAdditionalDisbMakerData method of AdditionalDisbursalDAOImpl");
//		String loanId="";
//		String customerName="";
//		String userName="";
//		
		StringBuilder loanId=new StringBuilder();
		StringBuilder customerName=new StringBuilder();
		StringBuilder userName=new StringBuilder();
		
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist=new ArrayList();
		ArrayList detailList=new ArrayList();
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
		try{
			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
			userName.append(ConnectionDAO.singleReturn(userNameQ));
			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			userNameQ=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	       try{
	              logger.info("In searchAdditionalDisbMakerData().....................................Dao Impl");
	              
	              loanId.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID()).trim()));
	              customerName.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()));
	              boolean appendSQL=false;
	              StringBuffer bufInsSql =	new StringBuffer();
	              StringBuffer bufInsSqlTempCount = new StringBuffer();
	              
	              bufInsSql.append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, c.product_desc, d.scheme_desc,"+
					 " DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), a.loan_id, e.resch_id, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=E.MAKER_ID) MAKER_ID from cr_loan_dtl a,gcd_customer_m b," +
					 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='A' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
	              bufInsSqlTempCount.append("select count(1)"+
	     			 " from cr_loan_dtl a,gcd_customer_m b," +
	     			 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
	     			 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='A' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
				  if(vo.getStage()!=null && !vo.getStage().equalsIgnoreCase("F"))
					{
					  
					  logger.info("ritu"+vo.getReportingToUserId());
					  bufInsSql.append(" AND E.MAKER_ID='"+vo.getReportingToUserId()+"' ");
					  bufInsSqlTempCount.append(" AND E.MAKER_ID='"+vo.getReportingToUserId()+"' ");
					}
				  if(vo.getStage()!=null && vo.getStage().equalsIgnoreCase("F"))
					{
					  bufInsSql.append(" AND E.MAKER_ID!='"+vo.getUserId()+"' ");
					  bufInsSqlTempCount.append(" AND E.MAKER_ID!='"+vo.getUserId()+"' ");
					}
					if((!((loanId.toString()).equalsIgnoreCase("")))&&(!((customerName.toString()).equalsIgnoreCase(""))))
					{
			    	  bufInsSql.append(" AND a.Loan_Id ='"+loanId+"' AND b.customer_name like'%"+customerName+"%'");
			    	  bufInsSqlTempCount.append(" AND a.Loan_Id ='"+loanId+"' AND b.customer_name like'%"+customerName+"%'");
					}
					if((!((loanId.toString()).equalsIgnoreCase(""))) || (!((customerName.toString()).equalsIgnoreCase(""))) || (!(vo.getLbxUserId().equalsIgnoreCase(""))) ){
						appendSQL=true;
					}
					
					if(appendSQL){
						bufInsSql.append(" AND");
						bufInsSqlTempCount.append(" AND");

	        	 if((!((loanId.toString()).equalsIgnoreCase("")))) {
	    	         bufInsSql.append(" a.Loan_Id ='"+loanId+"' AND");
	    	         bufInsSqlTempCount.append(" a.Loan_Id ='"+loanId+"' AND");
	    	    	 appendSQL=true;
	    	    	  
	    	      }
				if((!((customerName.toString()).equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append(" b.customer_name like'%"+customerName+"%' AND");
	    	    	  bufInsSqlTempCount.append(" b.customer_name like'%"+customerName+"%' AND");
	    	    	  appendSQL=true;
	    	      }
				if((!vo.getLbxUserId().equalsIgnoreCase("")) && vo.getStage().equalsIgnoreCase("F")) {
					logger.info("kk");
	   	         bufInsSql.append(" E.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
	   	         bufInsSqlTempCount.append(" E.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
	   	    	 appendSQL=true;
	   	    	  
	   	      }
				}
				
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){					
					String tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
	            logger.info("In searchAdditionalDisbMakerData() ## tmp ## "+tmp);
	            logger.info("In searchAdditionalDisbMakerData() ## tmp1 ## "+tmp1);
	            logger.info("In appendSQL true----  in check index Of tmp"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
	            logger.info("In appendSQL true----  in check index Of tmp1"+tmp1.lastIndexOf("AND") +"------"+(tmp1.length()-3));
	            if(tmp.lastIndexOf("AND") == (tmp.length()-3) && tmp1.lastIndexOf("AND") == (tmp1.length()-3))
	            {
	            logger.info("In appendSQL true----  in check index Of");
	            tmp = (tmp).substring(0,(tmp.length()-4)).trim();
	            tmp1 = (tmp1).substring(0,(tmp1.length()-4)).trim();
	            logger.info("search Query...tmp. "+tmp);
	            searchlist = ConnectionDAO.sqlSelect(tmp);
	            count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	             }
	            else
	            {
	            	  logger.info("search Query...tmp."+tmp);
	                  searchlist = ConnectionDAO.sqlSelect(tmp); 
	                  count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	              }
	             
	              
				}else{
					count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
					if((loanId==null && customerName==null) || ((loanId.toString()).equalsIgnoreCase("") && customerName.toString().equalsIgnoreCase("")) 
							  || vo.getCurrentPageLink()>1)
					{
						logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						  if(vo.getCurrentPageLink()>1)
						  {
							  startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							  endRecordIndex = no;
							  logger.info("startRecordIndex .................... "+startRecordIndex);
							  logger.info("endRecordIndex .................... "+endRecordIndex);
						  }
						  bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
					}
				  logger.info("search Query...else-------."+bufInsSql);
				  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				}
	          logger.info("searchPartPrePaymentData: "+searchlist.size());
	          for(int i=0;i<searchlist.size();i++){
	          logger.info("searchPartPrePaymentData: "+searchlist.get(i).toString());
	          ArrayList data=(ArrayList)searchlist.get(i);
	          if(data.size()>0){
	        	  AdditionalDisbursalSearchVO vo1= new AdditionalDisbursalSearchVO();
	        	  if(type.equalsIgnoreCase("P"))
	        	  {
	        		  vo1.setLoanNo("<a href=additionalDisbursalMaker.do?method=showAdditionalDisbDataMaker&loanId="
	  					+ (CommonFunction.checkNull(data.get(6)).trim())
	  					+ "&reschId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
	  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
	        	  }
	        	/*  if(type.equalsIgnoreCase("F"))
	        	  {
	        		  vo1.setLoanNo("<a href=partPrePaymentSearch.do?method=showPartPrePaymentDataAuthor&loanId="
	  					+ (CommonFunction.checkNull(data.get(6)).trim())
	  					+ "&reschId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
	  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
	        	  }*/
	        	  vo1.setCustomerName((CommonFunction.checkNull(data.get(1)).trim()));
	        	  if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase(""))
	        		  vo1.setLoanAmount("0");
	        	  else
	        	  {
	        		  Number loanAmt = myFormatter.parse((CommonFunction.checkNull(data.get(2)).trim()));
	        		  vo1.setLoanAmount(myFormatter.format(loanAmt));
	        	  }
	        	  vo1.setProduct((CommonFunction.checkNull(data.get(3)).trim()));
	        	  vo1.setScheme((CommonFunction.checkNull(data.get(4)).trim()));
	        	  vo1.setLoanApprovalDate((CommonFunction.checkNull(data.get(5)).trim()));
	        	  vo1.setLbxLoanNoHID((CommonFunction.checkNull(data.get(6)).trim()));
	        	  vo1.setReschId((CommonFunction.checkNull(data.get(7)).trim()));
	        	  vo1.setTotalRecordSize(count);
	        	  vo1.setReportingToUserId((CommonFunction.checkNull(data.get(8)).trim()));
	        	  detailList.add(vo1);
	        }

		}
	          bufInsSql=null;
	          bufInsSqlTempCount=null;
	          
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			customerName=null;
			userName=null;
			loanId=null;
		}

		return detailList;
	}

	public ArrayList searchAdditionalDisbAuthorData(AdditionalDisbursalSearchVO vo,
			String type) {


		logger.info("In searchAdditionalDisbAuthorData method of AdditionalDisbursalDAOImpl");
//		String loanId="";
//		String customerName="";
//		String userName="";
//		
		StringBuilder loanId=new StringBuilder();
		StringBuilder customerName=new StringBuilder();
		StringBuilder userName=new StringBuilder();
		
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist=new ArrayList();
		ArrayList<PartPrePaymentSearchVO> detailList=new ArrayList();
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
		try{
			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
			userName.append(ConnectionDAO.singleReturn(userNameQ));
			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			userNameQ=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	       try{
	              logger.info("In searchAdditionalDisbAuthorData().....................................Dao Impl");
	              
	              loanId.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID()).trim()));
	              customerName.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()));
	              boolean appendSQL=false;
	              StringBuffer bufInsSql =	new StringBuffer();
	              StringBuffer bufInsSqlTempCount = new StringBuffer();
	              
	              bufInsSql.append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, c.product_desc, d.scheme_desc,"+
					 " DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), a.loan_id, e.resch_id, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=E.MAKER_ID) MAKER_ID from cr_loan_dtl a,gcd_customer_m b," +
					 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='A' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
	              bufInsSqlTempCount.append("select count(1)"+
	     			 " from cr_loan_dtl a,gcd_customer_m b," +
	     			 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
	     			 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='A' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
				  if(vo.getStage()!=null && !vo.getStage().equalsIgnoreCase("F"))
					{
					  
					  logger.info("ritu"+vo.getReportingToUserId());
					  bufInsSql.append(" AND E.MAKER_ID='"+vo.getReportingToUserId()+"' ");
					  bufInsSqlTempCount.append(" AND E.MAKER_ID='"+vo.getReportingToUserId()+"' ");
					}
				  if(vo.getStage()!=null && vo.getStage().equalsIgnoreCase("F"))
					{
					  bufInsSql.append(" AND E.MAKER_ID!='"+vo.getUserId()+"' ");
					  bufInsSqlTempCount.append(" AND E.MAKER_ID!='"+vo.getUserId()+"' ");
					}
					if((!((loanId.toString()).equalsIgnoreCase("")))&&(!((customerName.toString()).equalsIgnoreCase(""))))
					{
			    	  bufInsSql.append(" AND a.Loan_Id ='"+loanId+"' AND b.customer_name like'%"+customerName+"%'");
			    	  bufInsSqlTempCount.append(" AND a.Loan_Id ='"+loanId+"' AND b.customer_name like'%"+customerName+"%'");
					}
					if((!((loanId.toString()).equalsIgnoreCase(""))) || (!((customerName.toString()).equalsIgnoreCase(""))) || (!(vo.getLbxUserId().equalsIgnoreCase(""))) ){
						appendSQL=true;
					}
					
					if(appendSQL){
						bufInsSql.append(" AND");
						bufInsSqlTempCount.append(" AND");

	        	 if((!((loanId.toString()).equalsIgnoreCase("")))) {
	    	         bufInsSql.append(" a.Loan_Id ='"+loanId+"' AND");
	    	         bufInsSqlTempCount.append(" a.Loan_Id ='"+loanId+"' AND");
	    	    	 appendSQL=true;
	    	    	  
	    	      }
				if((!((customerName.toString()).equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append(" b.customer_name like'%"+customerName+"%' AND");
	    	    	  bufInsSqlTempCount.append(" b.customer_name like'%"+customerName+"%' AND");
	    	    	  appendSQL=true;
	    	      }
				if((!vo.getLbxUserId().equalsIgnoreCase("")) && vo.getStage().equalsIgnoreCase("F")) {
					logger.info("kk");
	   	         bufInsSql.append(" E.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
	   	         bufInsSqlTempCount.append(" E.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
	   	    	 appendSQL=true;
	   	    	  
	   	      }
				}
				
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){					
					String tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
	            logger.info("In searchAdditionalDisbMakerData() ## tmp ## "+tmp);
	            logger.info("In searchAdditionalDisbMakerData() ## tmp1 ## "+tmp1);
	            logger.info("In appendSQL true----  in check index Of tmp"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
	            logger.info("In appendSQL true----  in check index Of tmp1"+tmp1.lastIndexOf("AND") +"------"+(tmp1.length()-3));
	            if(tmp.lastIndexOf("AND") == (tmp.length()-3) && tmp1.lastIndexOf("AND") == (tmp1.length()-3))
	            {
	            logger.info("In appendSQL true----  in check index Of");
	            tmp = (tmp).substring(0,(tmp.length()-4)).trim();
	            tmp1 = (tmp1).substring(0,(tmp1.length()-4)).trim();
	            logger.info("search Query...tmp. "+tmp);
	            searchlist = ConnectionDAO.sqlSelect(tmp);
	            count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	             }
	            else
	            {
	            	  logger.info("search Query...tmp."+tmp);
	                  searchlist = ConnectionDAO.sqlSelect(tmp); 
	                  count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	              }
	             
	              
				}else{
					count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
					if((loanId==null && customerName==null) || ((loanId.toString()).equalsIgnoreCase("") && customerName.toString().equalsIgnoreCase("")) 
							  || vo.getCurrentPageLink()>1)
					{
						logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						  if(vo.getCurrentPageLink()>1)
						  {
							  startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							  endRecordIndex = no;
							  logger.info("startRecordIndex .................... "+startRecordIndex);
							  logger.info("endRecordIndex .................... "+endRecordIndex);
						  }
						  bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
					}
				  logger.info("search Query...else-------."+bufInsSql);
				  
				  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				}
	          logger.info("searchAdditionalDisbAuthorData: "+searchlist.size());
	          for(int i=0;i<searchlist.size();i++){
	          logger.info("searchAdditionalDisbAuthorData: "+searchlist.get(i).toString());
	          ArrayList data=(ArrayList)searchlist.get(i);
	          if(data.size()>0){
	        	  PartPrePaymentSearchVO vo1= new PartPrePaymentSearchVO();
	        	
	        	 
	        		  vo1.setLoanNo("<a href=additionalDisbursalAuthor.do?method=showAdditionalDisbDataAuthor&loanId="
	  					+ (CommonFunction.checkNull(data.get(6)).trim())
	  					+ "&reschId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
	  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
	        	
	        	  vo1.setCustomerName((CommonFunction.checkNull(data.get(1)).trim()));
	        	  if((CommonFunction.checkNull(data.get(2)).trim()).equalsIgnoreCase(""))
	        		  vo1.setLoanAmount("0");
	        	  else
	        	  {
	        		  Number loanAmt = myFormatter.parse((CommonFunction.checkNull(data.get(2)).trim()));
	        		  vo1.setLoanAmount(myFormatter.format(loanAmt));
	        	  }
	        	  vo1.setProduct((CommonFunction.checkNull(data.get(3)).trim()));
	        	  vo1.setScheme((CommonFunction.checkNull(data.get(4)).trim()));
	        	  vo1.setLoanApprovalDate((CommonFunction.checkNull(data.get(5)).trim()));
	        	  vo1.setLbxLoanNoHID((CommonFunction.checkNull(data.get(6)).trim()));
	        	  vo1.setReschId((CommonFunction.checkNull(data.get(7)).trim()));
	        	  vo1.setTotalRecordSize(count);
	        	  vo1.setReportingToUserId((CommonFunction.checkNull(data.get(8)).trim()));
	        	  detailList.add(vo1);
	        }

		}
	          bufInsSql=null;
	          bufInsSqlTempCount=null;
	          
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			customerName=null;
			userName=null;
			loanId=null;
		}

		return detailList;
	}
	
	public ArrayList<AdditionalDisbursalProcessVO> retrieveAdditionDisbValues(String lbxLoanNoHID)
	{
		logger.info("In retrieveAdditionDisbValues.....DAOImpl");
		ArrayList<AdditionalDisbursalProcessVO> list = new ArrayList();
		AdditionalDisbursalProcessVO vo = null;
		StringBuilder queryCheck=new StringBuilder();
		queryCheck.append("Select count(resch_id) from cr_resch_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'" +
		" and rec_status in ('P','F') and resch_type='A'");
		String tempCount = ConnectionDAO.singleReturn(queryCheck.toString());
		int count = Integer.parseInt(tempCount);
		
		queryCheck= null;
		
		ArrayList mainlist=new ArrayList();
		ArrayList subList=new ArrayList();
		StringBuilder query = new StringBuilder();
		if(count==0)
		{
			try{
				  query.append("select dl.DEAL_SANCTION_AMOUNT,dl.DEAL_UTILIZED_AMOUNT,a.loan_id,a.loan_no, b.customer_name, c.product_desc,  d.scheme_desc, " +
				  	 " (select ifnull(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+lbxLoanNoHID+"' and rec_status='A') as disbursedAmount," +
				  	 " a.loan_balance_principal, " +
				  	 " DATE_FORMAT((select min(instl_date) from cr_repaysch_dtl where loan_id='"+lbxLoanNoHID+"' and" +
				  	 " bill_flag='N' and instl_date>(select parameter_value from parameter_mst where parameter_key='BUSINESS_DATE')),'"+dateFormat+"') as next_due_date," +
				  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and RESCH_TYPE='A' and rec_status='A') as loanIdCount_cr_resch_dtl," +
				  	 " a.loan_repayment_type," +
				  	 " (select min(instl_no) from cr_repaysch_dtl where loan_id='"+lbxLoanNoHID+"' and bill_flag='N' "+
				  	 " and instl_date>(select parameter_value from parameter_mst where parameter_key='BUSINESS_DATE')) as instl_no,a.LOAN_INSTALLMENT_TYPE" +
					 " from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d,cr_deal_loan_dtl dl" +
					 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and dl.DEAL_ID=a.LOAN_DEAL_ID and dl.DEAL_LOAN_ID=a.LOAN_DEAL_LOAN_ID and a.loan_id='"+lbxLoanNoHID+"'");
		
				 logger.info("In retrieveAdditionDisbValues : "+query.toString());
				 mainlist=ConnectionDAO.sqlSelect(query.toString());
				 
				 query = null;

				 
				 StringBuilder cal=new StringBuilder();
				  cal.append(ConnectionDAO.singleReturn("Select (sum(txn.ADVICE_AMOUNT)-sum(txn.TXN_ADJUSTED_AMOUNT)-sum(txn.AMOUNT_IN_PROCESS)) d from cr_txnadvice_dtl txn where LOAN_ID='"+lbxLoanNoHID+"' and ADVICE_TYPE='R'"));
				 int size = mainlist.size();
				 logger.info("In retrieveAdditionDisbValues.....mainlist size: "+size);
				 logger.info("cal:............................. "+cal.toString());
				if(size!=0)
				{
					
				 for(int i=0;i<size;i++){
		
					subList=(ArrayList)mainlist.get(i);
					if(subList.size()>0){
						vo = new AdditionalDisbursalProcessVO();
						
						if((CommonFunction.checkNull(subList.get(0))).trim().equalsIgnoreCase(""))
							vo.setDisbursedAmount("0");
						else
						{
							Number sancAm = myFormatter.parse((CommonFunction.checkNull(subList.get(0))).trim());
							vo.setSanctionedAmount(myFormatter.format(sancAm));
						}
						
						if((CommonFunction.checkNull(subList.get(1))).trim().equalsIgnoreCase(""))
							vo.setDisbursedAmount("0");
						else
						{
							Number utilizedAm = myFormatter.parse((CommonFunction.checkNull(subList.get(1))).trim());
							vo.setUtilizedAmount(myFormatter.format(utilizedAm));
						}
						vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(2)).trim()));
						vo.setLoanNo((CommonFunction.checkNull(subList.get(3)).trim()));
						vo.setCustomerName((CommonFunction.checkNull(subList.get(4)).trim()));
						vo.setProduct((CommonFunction.checkNull(subList.get(5)).trim()));
						vo.setScheme((CommonFunction.checkNull(subList.get(6)).trim()));
						
						if((CommonFunction.checkNull(subList.get(7))).trim().equalsIgnoreCase(""))
							vo.setDisbursedAmount("0");
						else
						{
							Number disbursedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());
							vo.setDisbursedAmount(myFormatter.format(disbursedAmt));
						}
						
						if((CommonFunction.checkNull(subList.get(8))).trim().equalsIgnoreCase(""))
							vo.setOutstandingLoanAmount("0");
						else
						{
							Number outstandingLoanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
							vo.setOutstandingLoanAmount(myFormatter.format(outstandingLoanAmt));
						}
						
						vo.setNextDueDate((CommonFunction.checkNull(subList.get(9)).trim()));
						//if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase(""))
							//vo.setPartPaymentSinceDisbursal("0");
						//else
						//	vo.setPartPaymentSinceDisbursal((CommonFunction.checkNull(subList.get(10)).trim()));
						//vo.setLastPartPaymentDate((CommonFunction.checkNull(subList.get(11)).trim()));
						vo.setInstallmentType((CommonFunction.checkNull(subList.get(11)).trim()));
						if(CommonFunction.checkNull(subList.get(12)).trim().equalsIgnoreCase(""))
						{
							vo.setLbxInstlNo("0");
						}
						else
						{
							vo.setLbxInstlNo((CommonFunction.checkNull(subList.get(12)).trim()));
						}
						//vo.setLbxInstlNo((CommonFunction.checkNull(subList.get(11)).trim()));
						//vo.setPartPrePaymentCal(cal.toString());
						vo.setInstType((CommonFunction.checkNull(subList.get(13)).trim()));
						list.add(vo);
					}
					cal=null;
				  }
				 
				}
			}catch(Exception e){
			e.printStackTrace();
			}
			finally
			{
				
				//tempCount = null;
			}
		}
		else
			logger.info("Size of retrieveAdditionDisbValues: "+list.size());
		return list;
		
	}

	public String saveAddDisbursalData(AdditionalDisbursalProcessVO vo) {
		logger.info("In saveAddDisbursalData method of ");
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
	    String reschId="";
	    String reqNum="";
//	    String s1= "";
//	    String s2 ="";
//	    String s3="";
	    StringBuilder s1=new StringBuilder();
	    StringBuilder s2 =new StringBuilder();
	    StringBuilder s3=new StringBuilder();
		try
		{
				String disbAmount = vo.getDisbursalAm();
				 if(CommonFunction.checkNull(disbAmount).equalsIgnoreCase(""))
			        {
					 disbAmount="0";
			        }
				 
				 if(CommonFunction.checkNull(vo.getReschId()).equalsIgnoreCase(""))
			        {
					  reschId="0";
			        }
				 else
				 {
					 reschId=vo.getReschId();
				 }
			
				 String ReschCharges = vo.getReschCharges();
				 if(CommonFunction.checkNull(ReschCharges).equalsIgnoreCase(""))
			        {
					 ReschCharges="0";
			        }
				 
			  logger.info("Before Additional_Disbursal_save"+reschId);
			  in.add(Integer.parseInt(reschId));
			  in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
			  in.add("A");

	        if((CommonFunction.checkNull(vo.getReqRefNo()).trim()).equalsIgnoreCase(""))
	        	reqNum="0";
	        else
	        	reqNum=(CommonFunction.checkNull(vo.getReqRefNo()).trim());

	        in.add(reqNum);
	        
	        String date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getDisbursalDate()).trim());

	 	    if(date1 != null)
	 	    	in.add(date1);
	        
	        String date2=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getNextDueDate()).trim());

	 	    if(date2 != null)
	 	    	in.add(date2);

	 	    in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxInstlNo())));
	 	  
	        in.add(myFormatter.parse(CommonFunction.checkNull(disbAmount).trim()).doubleValue());
	        in.add(myFormatter.parse(CommonFunction.checkNull(ReschCharges).trim()).doubleValue());
	        logger.info("Reschedule Parameter........................"+CommonFunction.checkNull(vo.getRescheduleParameter()).trim());
	        in.add(CommonFunction.checkNull(vo.getRescheduleParameter()).trim());
	        in.add((CommonFunction.checkNull(vo.getReschReason()).trim()));
	        in.add("P");
	        in.add((CommonFunction.checkNull(vo.getMakerId()).trim()));
//	        in.add((CommonFunction.checkNull(vo.getMakerDate()).trim()));
	        String date=CommonFunction.changeFormat((CommonFunction.checkNull(vo.getMakerDate()).trim()));
		    if(date != null)
		    in.add(date);
		    
	        out.add(s1);
		    out.add(s2);
		    out.add(s3);
		    outMessages=(ArrayList) ConnectionDAO.callSP("Additional_Disbursal_save",in,out);
		    s1.append(CommonFunction.checkNull(outMessages.get(0)));
		    s2.append(CommonFunction.checkNull(outMessages.get(1)));
		    s3.append(CommonFunction.checkNull(outMessages.get(2)));
		    
	        logger.info("s1: "+s1);
	        logger.info("s2: "+s2);
	        logger.info("s2: "+s3);
	        if((s2.toString()).equalsIgnoreCase("S"))
	        {
	        	reschId = CommonFunction.checkNull(s1);
//				con.commit();
	        }
	        else if((s2.toString()).equalsIgnoreCase("E"))
	        {
	        	reschId = s1.toString();
//	        	con.rollback();
	        	if(CommonFunction.checkNull(s3).equals("ADDITIONAL DISBURSAL SHOULD BE LESS THAN DEAL SANCTION AMOUNT")){
	        		reschId = s3.toString();
	        	}
	        }
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		finally
		{
			reqNum=null;
			s1=null;
			s2=null;
			s3=null;
			
		}
		
		return reschId;
	}

	public ArrayList<AdditionalDisbursalProcessVO> selectAdditionDisbursalData(String loanId,String reschId, String recStatus) 
		{
			 logger.info("In selectAdditionDisbursalData method of ");
			 ArrayList disbData = new ArrayList();
			  ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();
			  StringBuilder query=new StringBuilder();
			  String cal="";
			  AdditionalDisbursalProcessVO vo = null;
			try{

				
			 query.append("select dl.DEAL_SANCTION_AMOUNT,dl.DEAL_UTILIZED_AMOUNT,a.loan_id,a.loan_no, b.customer_name, c.product_desc, d.scheme_desc," +
				  	 " (select ifnull(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+loanId+"' and rec_status='A') as disbursedAmount,"+
				  	 " a.loan_balance_principal,"+
				  	 " DATE_FORMAT((select min(instl_date) from cr_repaysch_dtl where loan_id='"+loanId+"'" +
				  	 " and bill_flag='N' and instl_date>(select parameter_value from parameter_mst where parameter_key='BUSINESS_DATE')),'"+dateFormat+"') as Next_Due_Date," +
				  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+loanId+"' and RESCH_TYPE='A' and rec_status='A') as loanIdcount_cr_resch_dtl," +
				  	 " DATE_FORMAT((select max(RESCH_EFF_DATE) from cr_resch_dtl where loan_id='"+loanId+"' and RESCH_TYPE='A' and rec_status='A'),'"+dateFormat+"') as effectiveDate," +
				  	 " a.loan_repayment_type, e.REQUEST_NO, DATE_FORMAT(e.RESCH_DATE,'"+dateFormat+"'), e.PREPAYMENT_AMOUNT, e.RESCH_CHARGES, "+
				  	 " e.PRE_PAYMENT_PARAMETER, e.RESCH_REASON, e.remarks, e.DEFERAL_FROM_INSTL,a.LOAN_INSTALLMENT_TYPE   "+
					 " from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d,cr_deal_loan_dtl dl, cr_resch_dtl e" +
					 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and dl.DEAL_ID=a.LOAN_DEAL_ID and dl.DEAL_LOAN_ID=a.LOAN_DEAL_LOAN_ID and a.loan_id=e.loan_id and e.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+""+
					 " and e.resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId).trim())+" ");

				 logger.info("In selectAdditionDisbursalData : "+query.toString());
				 mainlist=ConnectionDAO.sqlSelect(query.toString());
				 
				 query=null;
				 
				 cal= ConnectionDAO.singleReturn("Select (sum(txn.ADVICE_AMOUNT)-sum(txn.TXN_ADJUSTED_AMOUNT)-sum(txn.AMOUNT_IN_PROCESS)) d from cr_txnadvice_dtl txn where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+" and ADVICE_TYPE='R'");
					
				 logger.info("In selectAdditionDisbursalData.....mainlist size: "+mainlist.size());
				if(mainlist.size()!=0)
				{
					
				 for(int i=0;i<mainlist.size();i++){

					subList=(ArrayList)mainlist.get(i);
					if(subList.size()>0){
                       vo = new AdditionalDisbursalProcessVO();
						
						if((CommonFunction.checkNull(subList.get(0))).trim().equalsIgnoreCase(""))
							vo.setDisbursedAmount("0");
						else
						{
							Number sancAm = myFormatter.parse((CommonFunction.checkNull(subList.get(0))).trim());
							vo.setSanctionedAmount(myFormatter.format(sancAm));
						}
						
						if((CommonFunction.checkNull(subList.get(1))).trim().equalsIgnoreCase(""))
							vo.setDisbursedAmount("0");
						else
						{
							Number utilizedAm = myFormatter.parse((CommonFunction.checkNull(subList.get(1))).trim());
							vo.setUtilizedAmount(myFormatter.format(utilizedAm));
						}
						vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(2)).trim()));
						vo.setLoanNo((CommonFunction.checkNull(subList.get(3)).trim()));
						vo.setCustomerName((CommonFunction.checkNull(subList.get(4)).trim()));
						vo.setProduct((CommonFunction.checkNull(subList.get(5)).trim()));
						vo.setScheme((CommonFunction.checkNull(subList.get(6)).trim()));
						if((CommonFunction.checkNull(subList.get(7))).trim().equalsIgnoreCase(""))
							vo.setDisbursedAmount("0");
						else
						{
							Number disbursedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());
							vo.setDisbursedAmount(myFormatter.format(disbursedAmt));
						}
						
						if((CommonFunction.checkNull(subList.get(8))).trim().equalsIgnoreCase(""))
							vo.setOutstandingLoanAmount("0");
						else
						{
							Number outstandingLoanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
							vo.setOutstandingLoanAmount(myFormatter.format(outstandingLoanAmt));
						}
						
						vo.setNextDueDate((CommonFunction.checkNull(subList.get(9)).trim()));
					/*	if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase(""))
							vo.setPartPaymentSinceDisbursal("0");
						else
							vo.setPartPaymentSinceDisbursal((CommonFunction.checkNull(subList.get(10)).trim()));
						
						vo.setLastPartPaymentDate((CommonFunction.checkNull(subList.get(11)).trim()));*/
						vo.setInstallmentType((CommonFunction.checkNull(subList.get(12)).trim()));
						if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("0"))
							vo.setReqRefNo("");
						else
							vo.setReqRefNo((CommonFunction.checkNull(subList.get(13)).trim()));
						vo.setDisbursalDate((CommonFunction.checkNull(subList.get(14)).trim()));
						
						if((CommonFunction.checkNull(subList.get(15))).trim().equalsIgnoreCase(""))
							vo.setDisbursalAm("0");
						else
						{
							Number partPaymentAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(15))).trim());
							vo.setDisbursalAm(myFormatter.format(partPaymentAmount));
						}
						if((CommonFunction.checkNull(subList.get(16))).trim().equalsIgnoreCase(""))
							vo.setReschCharges("0");
						else
						{
							Number partPaymentCharges = myFormatter.parse((CommonFunction.checkNull(subList.get(16))).trim());
							vo.setReschCharges(myFormatter.format(partPaymentCharges));
						}
						
						vo.setRescheduleParameter((CommonFunction.checkNull(subList.get(17))).trim());
						vo.setReschReason((CommonFunction.checkNull(subList.get(18)).trim()));
						vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(19)).trim()));
						vo.setLbxInstlNo((CommonFunction.checkNull(subList.get(20))));
						vo.setInstType((CommonFunction.checkNull(subList.get(21))));
						//vo.setNextDueDate((CommonFunction.checkNull(subList.get(21))));
						//vo.setPartPrePaymentCal(cal);
						disbData.add(vo);
					}
				  }
				}
				
			
				
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				 mainlist=null;
				 subList=null;
				 query=null;
				 cal=null;
			}

			return disbData;
		
	}

	public String forwardAddDisbursalData(AdditionalDisbursalProcessVO vo) {
		
			logger.info("In forwardAddDisbursalData.....loanId "+vo.getLbxLoanNoHID());
			ArrayList qryList=new ArrayList();
			boolean status=false;
			String fwdStatus="E";
			StringBuilder query1=new StringBuilder();
			query1.append("update cr_resch_dtl set REC_STATUS='F' where LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim()+"' AND RESCH_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReschId())).trim()+"'");
			logger.info("query1: "+query1.toString());
			qryList.add(query1);
	        try {
				status = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("In forwardAddDisbursalData......................status= "+status);
				if(status)
				{
					fwdStatus="S";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				query1=null;
			}
			return fwdStatus;
		}

	public String additionDisbursalAuthor(LoanInitAuthorVo vo) {
			
			logger.info("In additionDisbursalAuthor ..Additional_Disbursal_Author...... DAOImpl");
			logger.info("Decision: "+vo.getDecision()+"In additionDisbursalAuthor vo.getCompanyId(): "+((CommonFunction.checkNull(vo.getCompanyId()).trim()))+"vo.getLoanId(): "+((CommonFunction.checkNull(vo.getLoanId())).trim()));
			String status="S";
		
//			CallableStatement cst=null;
//			String s1="";
//			String s2="";
			StringBuilder s1=new StringBuilder();
			StringBuilder s2=new StringBuilder();
			StringBuilder s3=new StringBuilder();
			StringBuilder s4=new StringBuilder();
			StringBuilder s5=new StringBuilder();
			StringBuilder s6=new StringBuilder();
		    ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
//			Connection con=ConnectionDAO.getConnection();
			try {
//				con.setAutoCommit(false);
//				cst=con.prepareCall("call Loan_Authorization(?,?,STR_TO_DATE(?,'"+dateFormatWithTime+"'),?,?,?,?,?)");
				  in.add(CommonFunction.checkNull( vo.getCompanyId()).trim());
				  in.add(CommonFunction.checkNull(vo.getRschId()).trim());
				String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getBussinessDate()).trim());
			    if(date != null)
			    	in.add(date);
			  
//				cst.setString(3, ((CommonFunction.checkNull(vo.getBussinessDate()).trim())));
			    in.add(CommonFunction.checkNull(vo.getUserId()).trim());
			    in.add(CommonFunction.checkNull(vo.getDecision()).trim());
			    in.add(CommonFunction.checkNull(vo.getComments()).trim());
				out.add(s1);
			    out.add(s2);
			    out.add(s3);
			    out.add(s4);
			    out.add(s5);
			    out.add(s6);
//				cst.registerOutParameter(7, Types.CHAR);
//				cst.registerOutParameter(8, Types.CHAR);
//				cst.executeUpdate();
			    logger.info("Input Parameter: "+in.toString());
			    outMessages=(ArrayList) ConnectionDAO.callSP("Resch_Author",in,out);
			    s1.append(CommonFunction.checkNull(outMessages.get(0)));
			    s2.append(CommonFunction.checkNull(outMessages.get(1)));
			    s3.append(CommonFunction.checkNull(outMessages.get(2)));
			    s4.append(CommonFunction.checkNull(outMessages.get(3)));
			    s5.append(CommonFunction.checkNull(outMessages.get(4)));
			    s6.append(CommonFunction.checkNull(outMessages.get(5)));
//				s1= cst.getString(7);
//				s2 = cst.getString(8);
				if(s5!=null && s5.toString().equalsIgnoreCase("S"))
				{
					status=s5.toString();
				}
				else
				{
					status=s6.toString();
				}
				logger.info("status: "+status);
				
			}
	      catch (Exception e) {
				
				
				e.printStackTrace();
			}
			finally
			{
				s1=null;
				s2=null;
			}
			return status;
		 }
	
	public String getMakerDate(String reschId) 
	{
		String makeDate="";
		StringBuilder query = new StringBuilder();
		query.append("select date_format(MAKER_DATE,'%d-%m-%Y') from cr_resch_dtl where resch_id="+reschId.trim());
		logger.info("Query for getting Maker Date of repricing  :  "+query.toString());
		try
		{
			makeDate = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return makeDate;
	}

	public ArrayList<RepayScheduleVo> getNewRepayScheduleAdditionalDisbursal(
			RepayScheduleVo vo, String loanId, String reschId) {
		
		logger.info("in getNewRepayScheduleAdditionalDisbursal() method of CreditManagementDAOImpl");
		ArrayList mainlist=new ArrayList();
		ArrayList list=new ArrayList();
		ArrayList subList=null;
		StringBuilder bussIrrQ = new StringBuilder();
		StringBuilder bussIrr = new StringBuilder();
		StringBuilder query = new StringBuilder(); 
		try
		{
			bussIrrQ.append("select deal_irr2 from cr_deal_loan_dtl where DEAL_LOAN_ID=(select LOAN_DEAL_LOAN_ID from cr_loan_dtl where LOAN_ID="+loanId+")");
			logger.info("Query in getRepaySchFieldsDetail--DEAL_IRR2---" + bussIrrQ);
			bussIrr.append(ConnectionDAO.singleReturn(bussIrrQ.toString()));
			query.append("select R_Seq_No,DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
					" if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" order by R_DUE_DATE,R_ORG_SEQ_NO");
			logger.info("Query in getNewRepayScheduleDeferral-----" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			logger.info("mainlist size: "+mainlist.size());
			for (int i = 0; i < mainlist.size(); i++) 
			{
				subList = (ArrayList) mainlist.get(i);
				RepayScheduleVo repvo = new RepayScheduleVo();
				if (subList.size() > 0)
				{
					repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
					repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
					if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
			    	{
				    	Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
				    	repvo.setInstAmount(myFormatter.format(reconNum));
			    	}
					if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
			    	{
				    	Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
				    	repvo.setPrinciple(myFormatter.format(reconNum));
			    	}
					if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
			   		{
			    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
			    		repvo.setInstCom(myFormatter.format(reconNum));
			   		}
					if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
			   		{
			    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
			    		repvo.setExcess(myFormatter.format(reconNum));
			   		}
				}
				repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
				if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
				{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
					repvo.setPrinOS(myFormatter.format(reconNum));
				}
				repvo.setDealIRR2(bussIrr.toString());
				list.add(repvo);
			}		
		}
		catch (Exception e) 
		{e.printStackTrace();}
		finally
		{
			bussIrrQ = null;
			query = null;
			mainlist= null;
			bussIrr= null;
			
		}
		return list;
	}
	
	public ArrayList<RepayScheduleVo> getRepayScheduleAddDisbursal(RepayScheduleVo vo, String loanId, String reschId,String userId) 
	{
	      logger.info("In getRepayScheduleAddDisbursal DAOImpl");
	      String status="";
	      CallableStatement cst=null;
	    Connection con=ConnectionDAO.getConnection();
	      ArrayList<RepayScheduleVo> list = new ArrayList<RepayScheduleVo>();
	      ArrayList mainlist = new ArrayList();
	      ArrayList subList = new ArrayList();
	      
//	    String chkQuery = "select count(INSTALLMENT_PLAN_ID) from cr_resch_installment_plan where " +
//	    " resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId))+" and" +
//	    " loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+"";
//	    String chkStatusStr = ConnectionDAO.singleReturn(chkQuery);
//	    int chkStatus = Integer.parseInt(chkStatusStr);
	    logger.info("Input Parameter(Resch_Author): I_COMPANY_ID: "+vo.getCompanyId()+" I_RESCH_ID: "+reschId+" I_CURR_DATE: "+vo.getAuthorDate()+" I_USER_ID: "+vo.getAuthorId()+" I_STATUS: "+"T"+" I_REMARKS: ");
//	    if(chkStatus>0)
//	    {
	            try
	            {
	              con.setAutoCommit(false);
	              cst=con.prepareCall("call Resch_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?)");
	              cst.setInt(1, Integer.parseInt((CommonFunction.checkNull(vo.getCompanyId()).trim())));
	              cst.setInt(2, Integer.parseInt((CommonFunction.checkNull(reschId).trim())));
	              cst.setString(3, (CommonFunction.checkNull(vo.getAuthorDate()).trim()));
	              cst.setString(4, (CommonFunction.checkNull(vo.getAuthorId()).trim()));
	              cst.setString(5, "T");
	              cst.setString(6, "");
	              cst.registerOutParameter(7, Types.CHAR );
	              cst.registerOutParameter(8, Types.CHAR );
	              cst.registerOutParameter(9, Types.CHAR );
	              cst.registerOutParameter(10, Types.CHAR );
	              cst.registerOutParameter(11, Types.CHAR );
	              cst.registerOutParameter(12, Types.CHAR );
	              cst.executeUpdate();
	              
	              String s3= cst.getString(7);
	              String s4 = cst.getString(8);
	              String s5= cst.getString(9);
	              String s6 = cst.getString(10);
	              String s1= cst.getString(11);
	              String s2 = cst.getString(12);
	              
	              logger.info("s1: "+s1);
	              logger.info("s2: "+s2);
	              logger.info("s3: "+s3);
	              logger.info("s4: "+s4);
	              logger.info("s5: "+s5);
	              logger.info("s6: "+s6);
	              
	              if(s1.equalsIgnoreCase("S"))
	              {
	                  status=s1;
	                  
	                        String bussIrrQ="select deal_irr2 from cr_deal_loan_dtl where DEAL_LOAN_ID=(select LOAN_DEAL_LOAN_ID from cr_loan_dtl where LOAN_ID="+loanId+")";
	                        logger.info("Query in getRepayScheduleAddDisbursal--DEAL_IRR2---" + bussIrrQ);
	                        String bussIrr = ConnectionDAO.singleReturn(bussIrrQ);
	                        
	                        String query="select R_Seq_No,DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
	                        " if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" order by R_DUE_DATE,R_ORG_SEQ_NO";
	                        logger.info("Query in getRepayScheduleAddDisbursal-----" + query);
	                        int j=1;
	                        Statement st = con.createStatement();
	                        ResultSet rs = st.executeQuery(query);
	      
	                  //getting the no of fields selected
	                  int noOfFields = (rs.getMetaData()).getColumnCount();
	      
	                  //counter which will be incremented for the no of fields
	                  //check whether the records have been returned
	                  
	      
	                  while (rs.next()) {
	                      int counter = 1; //this will restart the count every time from 1
	      
	                      //change made ..arraylist to beinitialized within the rs.next()
	                      ArrayList records = new ArrayList();
	      
	                      while (counter <= noOfFields) {
	                          //adding the column values in the arraylist
	                          records.add(rs.getString(counter));
	                          counter++;
	                      }
	      
	                      //adding the arraylist to the vector
	                      mainlist.add(records);
	                      
	                  } //end of rs.next()
	                        logger.info("mainlist size: "+mainlist.size());
	                        
	                        for (int i = 0; i < mainlist.size(); i++) 
	                        {
	                              
	                              subList = (ArrayList) mainlist.get(i);
	                              
	                              RepayScheduleVo repvo = new RepayScheduleVo();
	                              if (subList.size() > 0)
	                              {
	                                    repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
	                                    repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
	                                    
	                                    if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
	                              {
	                                    Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
	                                    repvo.setInstAmount(myFormatter.format(reconNum));
	                              }
	                                    
	                                    if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
	                              {
	                                    Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
	                                    repvo.setPrinciple(myFormatter.format(reconNum));
	                              }
	                  
	                                    if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
	                              {
	                                    Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
	                                    repvo.setInstCom(myFormatter.format(reconNum));
	                              }
	                  
	                                    if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
	                              {
	                                    Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
	                                    repvo.setExcess(myFormatter.format(reconNum));
	                              }
	                              }
	                                    repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
	                                    if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
	                              {
	                                    Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
	                                    repvo.setPrinOS(myFormatter.format(reconNum));
	                              }
	                                    repvo.setDealIRR2(bussIrr);
	                                    
	                                    
	                                    if(!CommonFunction.checkNull(cst.getString(7)).trim().equalsIgnoreCase(""))
	                                   {
			                              Number reconNum =myFormatter.parse((CommonFunction.checkNull(cst.getString(7))).trim());  
			                              repvo.setFinalRate(myFormatter.format(reconNum));
	                                   }
	                                    else
	                                    {
	                                    	 repvo.setFinalRate("0.00");
	                                    }
	                                    
	                                    if(!CommonFunction.checkNull(cst.getString(8)).trim().equalsIgnoreCase(""))
				                        {
				                              Number reconNum =myFormatter.parse((CommonFunction.checkNull(cst.getString(8))).trim());  
				                              repvo.setEffectiveRate(myFormatter.format(reconNum));
				                        }
	                                    else
	                                    {
	                                    	 repvo.setEffectiveRate("0.00");
	                                    }
	                                    
	                                    if(!CommonFunction.checkNull(cst.getString(9)).trim().equalsIgnoreCase(""))
	                                    {
				                              Number reconNum =myFormatter.parse((CommonFunction.checkNull(cst.getString(9))).trim());  
				                              repvo.setMktIRR1(myFormatter.format(reconNum));
	                                    }
	                                    else
	                                    {
	                                    	 repvo.setMktIRR1("0.00");
	                                    }
	                                    
	                                    if(!CommonFunction.checkNull(cst.getString(10)).trim().equalsIgnoreCase(""))
	                                    {
			                              Number reconNum =myFormatter.parse((CommonFunction.checkNull(cst.getString(10))).trim());  
			                              repvo.setMktIRR2(myFormatter.format(reconNum));
	                                     }
	                                    else
	                                    {
	                                    	 repvo.setMktIRR2("0.00");
	                                    }
	                                    repvo.setProcval("");
	                                    repvo.setProcvalstatus("");   
	                              list.add(repvo);
	                        }
	                        con.commit();
	              }
	              else if(s1.equalsIgnoreCase("E"))
	              {
	                  con.rollback();
	                  status = s2;
	                  RepayScheduleVo repvo = new RepayScheduleVo();
	                  repvo.setProcval(status);
	                  repvo.setProcvalstatus(s1);
	                  list.add(repvo);
	              }
	              
	                  
	            } catch (Exception e) {
	                  e.printStackTrace();
	            }
	            finally
	            {
	                  try {
	                        con.close();
	                        cst.close();
	                  } catch (SQLException e) {
	                        e.printStackTrace();
	                  }
	                  
	            }
//	    }

	      return list;
	}

	public boolean deleteAdditionalDisbursalData(String loanId, String reschId) 
	{
		logger.info("in deleteAdditionalDisbursalData()");
		ArrayList qryList=new ArrayList();
		boolean status=false;
		StringBuilder query=new StringBuilder();
		query.append("delete from cr_resch_dtl where RESCH_ID="+reschId.trim()+" and LOAN_ID="+loanId.trim());
		PrepStmtObject inPrepStmtObject = new PrepStmtObject();
		inPrepStmtObject.setSql(query.toString());
		logger.info("in deleteAdditionalDisbursalData()  Query  :  "+inPrepStmtObject.printQuery());
		qryList.add(inPrepStmtObject);
		try
		{
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		}
		catch (Exception e)
		{e.printStackTrace();}
		return status;
	}

	//Add by ajay for additional disbursal option 
	public ArrayList<CodeDescVo> getGenericMasterInfo(String genericKey) {
		logger.info("in getGenericMasterInfo()");
		ArrayList list=new ArrayList();
		ArrayList<CodeDescVo> resultList = new ArrayList<CodeDescVo>();
		ArrayList sublist = new ArrayList();
		StringBuilder query=new StringBuilder();
		query.append("select value,description from generic_master where GENERIC_KEY ='"+genericKey+"' and rec_status='A' ");
		
		
		logger.info("getGenericMasterInfo  Query  :  "+query.toString());
		try
		{
			list = ConnectionDAO.sqlSelect(query.toString());
			int size = list.size();
			CodeDescVo codeDescVo = null;
			for (int i = 0; i <size; i++) {
				sublist = (ArrayList)list.get(i);
				if(sublist!=null && sublist.size()>0)
				{
					 codeDescVo =new CodeDescVo();
					codeDescVo.setId(CommonFunction.checkNull(sublist.get(0)));
					codeDescVo.setName(CommonFunction.checkNull(sublist.get(1)));
					
				}
				resultList.add(codeDescVo);
			}
			
		}
		catch (Exception e)
		{e.printStackTrace();}
		return resultList;
	}

	
	
	
	//End here by ajay

}
