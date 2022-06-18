package com.cm.daoImplMYSQL;

/*
Author: Vishal SIngh 
Date:27-03-2012 
Purpose:Repay Schedule Maker/Author 

*/
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


import com.cm.dao.RepayScheduleDAO;
import com.cm.vo.LoanInitAuthorVo;
import com.cm.vo.UpdateRepayscheduleSearchVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.CodeDescVo;
import com.cp.vo.RepayScheduleVo;


public  class RepayScheduleDAOImpl implements RepayScheduleDAO 
{
	private static final Logger logger = Logger.getLogger(RepayScheduleDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
	

	public ArrayList<UpdateRepayscheduleSearchVO> searchRepaySchedule(UpdateRepayscheduleSearchVO vo,String type)
	{
		logger.info("In searchRepaySchedule() method of RepayScheduleDAOImpl");
	
		StringBuilder loanId=new StringBuilder();
		StringBuilder customerName=new StringBuilder();
		StringBuilder userName=new StringBuilder();
		
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist=new ArrayList();
		ArrayList<UpdateRepayscheduleSearchVO> detailList=new ArrayList();
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
		try{
			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
			//userName=ConnectionDAO.singleReturn(userNameQ);
			
			userName.append(ConnectionDAO.singleReturn(userNameQ));
			
			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			userNameQ=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			logger.info("In searchRepaySchedule.....................................Dao Impl stage "+vo.getStage()+" type: "+type);
	        /*loanId= CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID()).trim());
	        customerName=  CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim());*/
			
			loanId.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID()).trim()));
	        customerName.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()));
	        
	        boolean appendSQL=false;
	        StringBuffer bufInsSql =	new StringBuffer();
	        StringBuffer bufInsSqlTempCount = new StringBuffer();
	        bufInsSql.append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, c.product_desc, d.scheme_desc,"+
					 " DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), a.loan_id, e.resch_id,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=E.MAKER_ID) MAKER_ID from cr_loan_dtl a,gcd_customer_m b," +
					 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='U' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
	        bufInsSqlTempCount.append("select count(1)"+
	     			 " from cr_loan_dtl a,gcd_customer_m b," +
	     			 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
	     			 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='U' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
			if(type!=null && type.equalsIgnoreCase("P"))
			{
				  bufInsSql.append(" AND E.MAKER_ID='"+vo.getReportingToUserId()+"' ");
				  bufInsSqlTempCount.append(" AND E.MAKER_ID='"+vo.getReportingToUserId()+"' ");
			}
			if(type!=null && type.equalsIgnoreCase("F"))
			{
				  bufInsSql.append(" AND E.MAKER_ID!='"+vo.getUserId()+"' ");
				  bufInsSqlTempCount.append(" AND E.MAKER_ID!='"+vo.getUserId()+"' ");
			}
			if((!((loanId.toString()).equalsIgnoreCase("")))&&(!((customerName.toString()).equalsIgnoreCase(""))))
			{
			  	  bufInsSql.append(" AND a.Loan_Id ='"+loanId+"' AND b.customer_name like'%"+customerName+"%'");
			   	  bufInsSqlTempCount.append(" AND a.Loan_Id ='"+loanId+"' AND b.customer_name like'%"+customerName+"%'");
			}
			if((!((loanId.toString()).equalsIgnoreCase(""))) || (!((customerName.toString()).equalsIgnoreCase(""))) || (!(vo.getLbxUserId().equalsIgnoreCase(""))))
			{
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
				
				  if(type.equalsIgnoreCase("F"))
	        	  {
						if((!(vo.getLbxUserId().equalsIgnoreCase("")))&& vo.getStage().equalsIgnoreCase("F") ) {
							 bufInsSql.append(" E.MAKER_ID! ='"+vo.getLbxUserId()+"' AND");
			    	         bufInsSqlTempCount.append(" E.MAKER_ID! ='"+vo.getLbxUserId()+"' AND");
			  	    	  appendSQL=true;
			  	      }
	        	  }
				  else  if(type.equalsIgnoreCase("P"))
	        	  {
						if((!(vo.getLbxUserId().equalsIgnoreCase("")))&& vo.getStage().equalsIgnoreCase("F") ) {
							 bufInsSql.append(" E.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
			    	         bufInsSqlTempCount.append(" E.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
			  	    	  appendSQL=true;
			  	      }
	        	  }
				}
				
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){					
					String tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
	            logger.info("In searchRepayScheduling ## tmp ## "+tmp);
	            logger.info("In searchRepayScheduling() ## tmp1 ## "+tmp1);
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
	          logger.info("searchRepayScheduling: "+searchlist.size());
	          for(int i=0;i<searchlist.size();i++){
	          logger.info("searchRepayScheduling: "+searchlist.get(i).toString());
	          ArrayList data=(ArrayList)searchlist.get(i);
	          if(data.size()>0){
	        	  UpdateRepayscheduleSearchVO vo1= new UpdateRepayscheduleSearchVO();
	        	  if(type.equalsIgnoreCase("P"))
	        	  {
	        		  vo1.setLoanNo("<a href=updateRepaySchedule.do?method=editRepayScheduleData&loanId="
	  					+ (CommonFunction.checkNull(data.get(6)).trim())
	  					+ "&reschId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
	  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
	        	  }
	        	  if(type.equalsIgnoreCase("F"))
	        	  {
	        		  vo1.setLoanNo("<a href=updateRepaySchedule.do?method=editRepayScheduleData&loanId="
	  					+ (CommonFunction.checkNull(data.get(6)).trim())
	  					+ "&reschId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
	  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
	        	  }
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

		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{

			loanId=null;
			customerName=null;
			userName=null;
			}
		

		return detailList;
	}
	
	
	public ArrayList<UpdateRepayscheduleSearchVO> retriveRepayScheduleValues(String lbxLoanNoHID){
		logger.info("In UpdateRepayscheduleSearchVO.....DAOImpl********"+lbxLoanNoHID);
		ArrayList<UpdateRepayscheduleSearchVO> repayScheduleList = new ArrayList();
		
		UpdateRepayscheduleSearchVO vo = null;
		
		StringBuilder queryCheck=new StringBuilder();
		
	 queryCheck.append("Select count(resch_id) from cr_resch_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'" +
		" and rec_status in ('P','F') and resch_type='U'");
	 
	    StringBuilder tempCount=new StringBuilder();
		
	    logger.info(queryCheck.toString());
	 
	 tempCount.append(ConnectionDAO.singleReturn(queryCheck.toString()));
	 
		int count = Integer.parseInt(tempCount.toString());
		 StringBuilder query=new StringBuilder();
		if(count==0)
		{
			try{
		
				  ArrayList mainlist=new ArrayList();
				  ArrayList subList=new ArrayList();
				  
				 
				  query.append("select a.loan_id,a.loan_no, b.customer_name, c.product_desc,  d.scheme_desc, " +
						  	 " (select ifnull(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+lbxLoanNoHID+"' and rec_status='A') as disbursedAmount," +
						  	 " a.loan_balance_principal," +
						  	 " DATE_FORMAT((select min(instl_date) from cr_repaysch_dtl where loan_id='"+lbxLoanNoHID+"' and" +
						  	 " bill_flag='N' and instl_date>(select parameter_value from parameter_mst where parameter_key='BUSINESS_DATE')),'"+dateFormat+"') as next_due_date," +
						  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and RESCH_TYPE='U' and rec_status='A') as loanIdcountIncr_resch_dtl," +
						  	 " a.loan_repayment_type," +
						  	 " a.LOAN_EMI_AMOUNT,a.loan_due_day,date_format(A.LOAN_REPAY_EFF_DATE,'"+dateFormat+"'),A.LOAN_REPAYMENT_FREQ" +
							 " from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d,cr_deal_loan_dtl dl" +
							 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
							 " and d.scheme_id = a.loan_scheme and dl.DEAL_ID=a.LOAN_DEAL_ID and dl.DEAL_LOAN_ID=a.LOAN_DEAL_LOAN_ID  and a.loan_id='"+lbxLoanNoHID+"'");
				
				  
				//  query.append("SELECT a.loan_id,a.loan_no, b.customer_name,c.product_desc,  d.scheme_desc, ifnull(sum(f.disbursal_amount),0) as disbursal_amount ,a.loan_balance_principal, e.request_no,date_format(a.next_due_date,'"+dateFormat+"') as new_due_date,a.LOAN_EMI_AMOUNT,a.loan_remarks,a.loan_due_day,e.resch_id from cr_loan_dtl a left join gcd_customer_m b on b.customer_id = a.loan_customer_id left join cr_product_m c on c.product_id = a.loan_product left join  cr_scheme_m d on d.scheme_id = a.loan_scheme left join cr_resch_dtl e on a.loan_id=e.loan_id left join cr_loan_disbursal_dtl f on a.loan_id=f.loan_id where a.loan_id='"+lbxLoanNoHID+"'");
				 
				  		
				 logger.info("In retriveRepayScheduleValues : "+query.toString());
				 
				 mainlist=ConnectionDAO.sqlSelect(query.toString());
				 logger.info("In retriveRepayScheduleValues.....mainlist size: "+mainlist.size());
				if(mainlist.size()!=0)
				{
					
				 for(int i=0;i<mainlist.size();i++){
		
					subList=(ArrayList)mainlist.get(i);
					logger.info("In retriveRepayScheduleValues......sublist size: "+subList.size());
					if(subList.size()>0){
						vo = new UpdateRepayscheduleSearchVO();
						vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
						vo.setLoanNo((CommonFunction.checkNull(subList.get(1)).trim()));
						vo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
						vo.setProduct((CommonFunction.checkNull(subList.get(3)).trim()));
						vo.setScheme((CommonFunction.checkNull(subList.get(4)).trim()));
						
						if((CommonFunction.checkNull(subList.get(5))).trim().equalsIgnoreCase(""))
							vo.setDisbursedAmount("0");
						else
						{
							Number disbursedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
							vo.setDisbursedAmount(myFormatter.format(disbursedAmt));
						}
						
						if((CommonFunction.checkNull(subList.get(6))).trim().equalsIgnoreCase(""))
							vo.setOutstandingLoanAmount("0");
						else
						{
							Number outstandingLoanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
							vo.setOutstandingLoanAmount(myFormatter.format(outstandingLoanAmt));
						}
						
						//vo.setRequestRefNo((CommonFunction.checkNull(subList.get(7)).trim()));
						vo.setCurentDueDate((CommonFunction.checkNull(subList.get(7)).trim()));
						
						vo.setRepaymentType((CommonFunction.checkNull(subList.get(9)).trim()));
						
						if((CommonFunction.checkNull(subList.get(10))).trim().equalsIgnoreCase(""))
							vo.setEmi("0");
						else
						{
							Number emi = myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());
							vo.setEmi(myFormatter.format(emi));							
						}
						vo.setDueDay((CommonFunction.checkNull(subList.get(11)).trim()));	
						vo.setRepayEffDate((CommonFunction.checkNull(subList.get(12)).trim()));
						vo.setLoanFrequency((CommonFunction.checkNull(subList.get(13)).trim()));
						repayScheduleList.add(vo);
					}
				  }
				}
			}catch(Exception e)
			{
			e.printStackTrace();
			
			}
			finally
			{
			queryCheck=null;
			tempCount=null;
			query=null;
			}
		}
		
		else
			logger.info("Size of repayScheduleList: "+repayScheduleList.size());
		return repayScheduleList;
	}
	
	public ArrayList getCycleDateList() {
		ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT CAST(VALUE AS UNSIGNED INTEGER) id,CAST(VALUE AS UNSIGNED INTEGER) id1 FROM generic_master WHERE GENERIC_KEY = 'CYCLE_DATE' and rec_status='A' ORDER BY id");

		logger.info("getCycleDateList: "+query);
		CodeDescVo branchVo=null;
		ArrayList baseRate = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getCycleDateList"+baseRate.size());
		query=null;
		for(int i=0;i<baseRate.size();i++){

			logger.info("getCycleDateList"+baseRate.get(i).toString());
			ArrayList baseRate1=(ArrayList)baseRate.get(i);
			if(baseRate1.size()>0)
			{
			    branchVo = new CodeDescVo();
				branchVo.setId((CommonFunction.checkNull(baseRate1.get(0))).trim());
				branchVo.setName((CommonFunction.checkNull(baseRate1.get(1))).trim());
				list.add(branchVo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}

		return list;
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
	public String updateRepayScheduleData(UpdateRepayscheduleSearchVO ob) {
		
		logger.info("In saveAddDisbursalData method of ");
		
		UpdateRepayscheduleSearchVO vo = (UpdateRepayscheduleSearchVO)ob;
		
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
				
				 
				 if(CommonFunction.checkNull(vo.getReschId()).equalsIgnoreCase(""))
			        {
					  reschId="0";
			        }
				 else
				 {
					 reschId=vo.getReschId();
				 }
			
				 String ReschCharges = vo.getreschCharges();
				 if(CommonFunction.checkNull(ReschCharges).equalsIgnoreCase(""))
			        {
				    	 ReschCharges="0";
			        }
				 
			  logger.info("Before Repay_Schedule_save"+reschId);
			  in.add(Integer.parseInt(reschId));
			  in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
			  in.add("U");
	        if((CommonFunction.checkNull(vo.getRequestRefNo()).trim()).equalsIgnoreCase(""))
	        	reqNum="0";
	        else
	        	reqNum=(CommonFunction.checkNull(vo.getRequestRefNo()).trim());

	        in.add(reqNum);
	        
	      
	        
	        String reschDate=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getreschDate()).trim());
	        
	        if(reschDate!=null){
	        	in.add(reschDate);	
	        }
	        
	        String date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getCurentDueDate()).trim());

	 	    if(date1 != null)
	 	    	in.add(date1);
	        
	        String date2=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getNextDueDate()).trim());

	 	    if(date2 != null)
	 	    	in.add(date2);

	 	  
	 	  
	   
	        in.add(myFormatter.parse(CommonFunction.checkNull(ReschCharges).trim()).doubleValue());
	        
	        in.add(vo.getresStatus());
	        in.add((CommonFunction.checkNull(vo.getmakerRemarks()).trim()));
	        in.add((CommonFunction.checkNull(vo.getMakerId()).trim()));
//	        in.add((CommonFunction.checkNull(vo.getMakerDate()).trim()));
	        String date=CommonFunction.changeFormat((CommonFunction.checkNull(vo.getmakerDate()).trim()));
		    if(date != null)
		    in.add(date);
		    in.add(vo.getLbxInstlNo());
		    
	        out.add(s1);
		    out.add(s2);
		    out.add(s3);
		    outMessages=(ArrayList) ConnectionDAO.callSP("Repay_Schedule_save",in,out);
		    s1.append(CommonFunction.checkNull(outMessages.get(0)));
		    s2.append(CommonFunction.checkNull(outMessages.get(1)));
		    s3.append(CommonFunction.checkNull(outMessages.get(2)));
		    
	        logger.info("s1: "+s1);
	        logger.info("s2: "+s2);
	        logger.info("s3: "+s3);
	        if((s2.toString()).equalsIgnoreCase("S"))
	        {
	        	reschId = CommonFunction.checkNull(s1);
//				con.commit();
	        }
	        else if((s2.toString()).equalsIgnoreCase("E"))
	        {
	        	reschId = s1.toString();
//	        	con.rollback();
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
	
//	public boolean updateRepayScheduleData(UpdateRepayscheduleSearchVO ob){
//		
//		
//		boolean status = false;
//		ArrayList qryList = new ArrayList();
//		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
//		
//		
//		String query = "select LOAN_ID from cr_resch_dtl where LOAN_ID='"
//			+ StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID().toUpperCase().trim()) + "'";
//	   logger.info("In updateRepayScheduleData.....................................Dao "
//					+ query);
//	   try{
//	   boolean st = ConnectionDAO.checkStatus(query);
//	   StringBuffer bufInsSql = new StringBuffer();
//	   if(!st){
//		   
//		   logger.info("In insert Repay Schedule master");
//			
//			bufInsSql.append("insert into cr_resch_dtl(PREPAYMENT_AMOUNT,GAP_INTEREST,LOAN_ID,RESCH_TYPE,REQUEST_NO,RESCH_DATE,OLD_DUE_DATE,NEW_DUE_DATE,RESCH_CHARGES,REC_STATUS,REMARKS,MAKER_ID,MAKER_DATE)");
//			bufInsSql.append(" values ('0.0000','0.0000',");
//			bufInsSql.append(" ?,");
//			bufInsSql.append(" ?,");
//			bufInsSql.append(" ?,");
//			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
//			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
//			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
//			bufInsSql.append(" ?,");
//			bufInsSql.append(" ?,");
//			bufInsSql.append(" ?,");
//			bufInsSql.append(" ?,");
//			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
//			if (CommonFunction.checkNull(vo.getLbxLoanNoHID())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getLbxLoanNoHID()
//						.toUpperCase().trim());
//
//			if (CommonFunction.checkNull(vo.getreschType())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getreschType()
//						.toUpperCase().trim());
//			if (CommonFunction.checkNull(vo.getRequestRefNo())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getRequestRefNo()
//						.toUpperCase().trim());
//
//			if (CommonFunction.checkNull(vo.getreschDate())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getreschDate()
//						.toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getCurentDueDate())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getCurentDueDate()
//						.toUpperCase().trim());
//
//			if (CommonFunction.checkNull(vo.getNextDueDate())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getNextDueDate()
//						.toUpperCase().trim());
//			
//			
//			if (CommonFunction.checkNull(vo.getreschCharges())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getreschCharges()
//						.toUpperCase().trim());
//
//			if (CommonFunction.checkNull(vo.getresStatus())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getresStatus()
//						.toUpperCase().trim());
//			if (CommonFunction.checkNull(vo.getmakerRemarks())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getmakerRemarks()
//						.toUpperCase().trim());
//
//			if (CommonFunction.checkNull(vo.getMakerId())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getMakerId()
//						.toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getmakerDate())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getmakerDate()
//						.toUpperCase().trim());
//			
//			
//			insertPrepStmtObject.setSql(bufInsSql.toString());
//			logger.info("IN updateRepayScheduleData() update query1 ### "
//					+ insertPrepStmtObject.printQuery());
//			qryList.add(insertPrepStmtObject);
//			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
//			logger.info("In saveRepayScheduleData......................"
//					+ status);
//			
//		   
//	   }else{
//		   
//		   logger.info("In update Repay Schedule master");
//		   
//		   bufInsSql.append(" UPDATE cr_resch_dtl set OLD_DUE_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),NEW_DUE_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),RESCH_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),RESCH_CHARGES=?,REC_STATUS=?,REMARKS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where RESCH_ID=?");	
//		   if (CommonFunction.checkNull(vo.getCurentDueDate())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getCurentDueDate()
//						.toUpperCase().trim());
//
//			if (CommonFunction.checkNull(vo.getNextDueDate())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getNextDueDate()
//						.toUpperCase().trim());
//			
//			
//			if (CommonFunction.checkNull(vo.getreschDate())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getreschDate()
//						.toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getreschCharges())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getreschCharges()
//						.toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getresStatus())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getresStatus()
//						.toUpperCase().trim());
//			
//			
//			if (CommonFunction.checkNull(vo.getmakerRemarks())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getmakerRemarks()
//						.toUpperCase().trim());
//
//			if (CommonFunction.checkNull(vo.getMakerId())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getMakerId()
//						.toUpperCase().trim());
//			
//			if (CommonFunction.checkNull(vo.getmakerDate())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getmakerDate()
//						.toUpperCase().trim());
//			
//			
//			if (CommonFunction.checkNull(vo.getLbxLoanNoHID())
//					.equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getLbxLoanNoHID());
//			
//			
//			
//			insertPrepStmtObject.setSql(bufInsSql.toString());
//
//			qryList.add(insertPrepStmtObject);
//			logger.info("In UPDATE REPAYSCHEDULE QUERY " + bufInsSql.toString());
//			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
//
//		   
//		   
//		   // write update query
//	   }
//	   }catch(Exception e){
//		   e.printStackTrace();
//	   }
//	   
//	   return status;
//	}
		
	
	
	public ArrayList<UpdateRepayscheduleSearchVO> getRepayScheduleData(String reschId,String lbxLoanNoHID){
		ArrayList<UpdateRepayscheduleSearchVO> repayScheduleList = new ArrayList();
		ArrayList subList =new ArrayList();
		ArrayList mainlist=new ArrayList();
		
		UpdateRepayscheduleSearchVO vo;
		
		 StringBuilder query=new StringBuilder();
		
		 
		  query.append("select a.loan_id,a.loan_no, b.customer_name, c.product_desc,  d.scheme_desc, " +
				  	 " (select ifnull(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+lbxLoanNoHID+"' and rec_status='A') as disbursedAmount," +
				  	 " a.loan_balance_principal, " +
				  	 " DATE_FORMAT(e.OLD_DUE_DATE,'"+dateFormat+"') as next_due_date," +
				  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and RESCH_TYPE='U' and rec_status='A') as loanIdcountIncr_resch_dtl," +
				  	 " a.loan_repayment_type," +
				  	 " a.LOAN_EMI_AMOUNT,a.loan_due_day,e.request_no,e.resch_id,date_format(e.new_due_date,'"+dateFormat+"') as new_due_date,e.remarks,e.resch_charges,e.DEFERAL_FROM_INSTL,date_format(a.loan_repay_eff_date,'"+dateFormat+"'),a.LOAN_REPAYMENT_FREQ" +
					 " from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d,cr_deal_loan_dtl dl,cr_resch_dtl e" +
					 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and dl.DEAL_ID=a.LOAN_DEAL_ID  and dl.DEAL_LOAN_ID=a.LOAN_DEAL_LOAN_ID and a.loan_id='"+lbxLoanNoHID+"' and a.loan_id=e.loan_id and e.resch_id='"+reschId+"'");
		
		 
		 
		// query.append("SELECT a.loan_id,a.loan_no, b.customer_name,c.product_desc,  d.scheme_desc, ifnull(sum(f.disbursal_amount),0) as disbursal_amount ,a.loan_balance_principal, e.request_no,e.old_due_date,a.LOAN_EMI_AMOUNT,a.loan_remarks,a.loan_due_day,e.resch_id,date_format(e.new_due_date,'"+dateFormat+"') as new_due_date,e.remarks,e.resch_charges from cr_loan_dtl a left join gcd_customer_m b on b.customer_id = a.loan_customer_id left join cr_product_m c on c.product_id = a.loan_product left join  cr_scheme_m d on d.scheme_id = a.loan_scheme left join cr_resch_dtl e on a.loan_id=e.loan_id left join cr_loan_disbursal_dtl f on a.loan_id=f.loan_id where a.loan_id='"+lbxLoanNoHID+"' and e.resch_id='"+reschId+"'");
		 
		   try{
			   logger.info("In getRepayScheduleData : "+query.toString());
				 
				 mainlist=ConnectionDAO.sqlSelect(query.toString());
				 logger.info("In getRepayScheduleData.....mainlist size: "+mainlist.size());
				if(mainlist.size()!=0)
				{
					
				 for(int i=0;i<mainlist.size();i++){
		
					subList=(ArrayList)mainlist.get(i);
					logger.info("In getRepayScheduleData......sublist size: "+subList.size());
					if(subList.size()>0){
						vo = new UpdateRepayscheduleSearchVO();
						vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
						vo.setLoanNo((CommonFunction.checkNull(subList.get(1)).trim()));
						vo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
						vo.setProduct((CommonFunction.checkNull(subList.get(3)).trim()));
						vo.setScheme((CommonFunction.checkNull(subList.get(4)).trim()));
						
						if((CommonFunction.checkNull(subList.get(5))).trim().equalsIgnoreCase(""))
							vo.setDisbursedAmount("0");
						else
						{
							Number disbursedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
							vo.setDisbursedAmount(myFormatter.format(disbursedAmt));
						}
						
						if((CommonFunction.checkNull(subList.get(6))).trim().equalsIgnoreCase(""))
							vo.setOutstandingLoanAmount("0");
						else
						{
							Number outstandingLoanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
							vo.setOutstandingLoanAmount(myFormatter.format(outstandingLoanAmt));
						}
						
						//vo.setRequestRefNo((CommonFunction.checkNull(subList.get(7)).trim()));
						vo.setCurentDueDate((CommonFunction.checkNull(subList.get(7)).trim()));
						
						vo.setRepaymentType((CommonFunction.checkNull(subList.get(9)).trim()));
						
						if((CommonFunction.checkNull(subList.get(10))).trim().equalsIgnoreCase(""))
							vo.setEmi("0");
						else
						{
							Number emi = myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());
							vo.setEmi(myFormatter.format(emi));
						}
						
						
						vo.setCurrentDueDay((CommonFunction.checkNull(subList.get(11)).trim()));
						logger.info("setCurrentDueDay:     "+vo.getCurrentDueDay());
						vo.setRequestRefNo((CommonFunction.checkNull(subList.get(12)).trim()));
						vo.setReschId((CommonFunction.checkNull(subList.get(13)).trim()));
						vo.setNextDueDate((CommonFunction.checkNull(subList.get(14)).trim()));
						vo.setmakerRemarks((CommonFunction.checkNull(subList.get(15)).trim()));
						
						
						if((CommonFunction.checkNull(subList.get(16))).trim().equalsIgnoreCase(""))
							vo.setreschCharges("0");
						else
						{
							Number charges = myFormatter.parse((CommonFunction.checkNull(subList.get(16))).trim());
							vo.setreschCharges(myFormatter.format(charges));
						}
						//vo.setreschCharges((CommonFunction.checkNull(subList.get(16)).trim()));
						if(!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase(""))
						{
							String format=dateFormat.substring(2,3);
							logger.info("format:  "+format);
							vo.setDueDay(vo.getNextDueDate().substring(0,vo.getNextDueDate().indexOf(format)));
							String val=vo.getDueDay().substring(0,1);
							logger.info("val:  "+val);
							if(CommonFunction.checkNull(val).equalsIgnoreCase("0"))
							{
								vo.setDueDay(vo.getDueDay().substring(1));
							}
							logger.info("getDueDay:  "+vo.getDueDay());
						}
						
						vo.setLbxInstlNo((CommonFunction.checkNull(subList.get(17)).trim()));
						vo.setDeferralFromInstallment((CommonFunction.checkNull(subList.get(17)).trim()));
						vo.setRepayEffDate((CommonFunction.checkNull(subList.get(18)).trim()));
						vo.setLoanFrequency((CommonFunction.checkNull(subList.get(19)).trim()));
						repayScheduleList.add(vo);
					  }
				    }
				   }
						
					}catch(Exception e){
						e.printStackTrace();
					}		   
		 
		      return repayScheduleList;
	   }
	
	
public boolean updateAndFarwordRepayScheduleData(UpdateRepayscheduleSearchVO ob){
		
		UpdateRepayscheduleSearchVO vo = (UpdateRepayscheduleSearchVO)ob;
		boolean status = false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		 try{
			 
			   StringBuffer bufInsSql = new StringBuffer();
			   
			   bufInsSql.append(" UPDATE cr_resch_dtl set REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where RESCH_ID=?");
			   
			   if (CommonFunction.checkNull(vo.getresStatus())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getresStatus()
							.toUpperCase().trim());
			   
			   if (CommonFunction.checkNull(vo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getmakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getmakerDate()
							.toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getReschId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getReschId());
				
				insertPrepStmtObject.setSql(bufInsSql.toString());

				qryList.add(insertPrepStmtObject);
				logger.info("In UPDATE REPAYSCHEDULE farward QUERY " + bufInsSql.toString());
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return status;
		
}
	
public ArrayList<RepayScheduleVo> getNewRepayScheduleDueDate(RepayScheduleVo vo, String loanId, String reschId) 
{
      logger.info("In getNewRepayScheduleDueDate DAOImpl Resch_Author");
      String status="";
      CallableStatement cst=null;
    Connection con=ConnectionDAO.getConnection();
      ArrayList<RepayScheduleVo> list = new ArrayList<RepayScheduleVo>();
      ArrayList mainlist = new ArrayList();
      ArrayList subList = new ArrayList();
      
//    String chkQuery = "select count(INSTALLMENT_PLAN_ID) from cr_resch_installment_plan where " +
//    " resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId))+" and" +
//    " loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+"";
//    String chkStatusStr = ConnectionDAO.singleReturn(chkQuery);
//    int chkStatus = Integer.parseInt(chkStatusStr);
//    logger.info("chkStatus: "+chkStatus);
//    if(chkStatus>0)
//    {
            try
            {
            	logger.info("Company Id: "+vo.getCompanyId()+" reschId: "+reschId+" BDate: "+vo.getAuthorDate()+"User ID: "+vo.getAuthorId());
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
                        logger.info("Query in getNewRepayScheduleDueDate--DEAL_IRR2---" + bussIrrQ);
                        String bussIrr = ConnectionDAO.singleReturn(bussIrrQ);
                        
                        String query="select R_Seq_No,DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
                        " if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" order by R_DUE_DATE,R_ORG_SEQ_NO";
                        logger.info("Query in getNewRepayScheduleDueDate-----" + query);
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
//    }

      return list;
}

	
public String saveDueDateAuthor(LoanInitAuthorVo vo) {
	
	logger.info("In Resch_Author ........ DAOImpl");
	logger.info("Decision: "+vo.getDecision()+"In saveDueDateAuthor vo.getCompanyId(): "+((CommonFunction.checkNull(vo.getCompanyId()).trim()))+"vo.getLoanId(): "+((CommonFunction.checkNull(vo.getLoanId())).trim()));
	String status="";
//	CallableStatement cst=null;
//	String s1="";
//	String s2="";
	StringBuilder s1=new StringBuilder();
	StringBuilder s2=new StringBuilder();
	StringBuilder s3=new StringBuilder();
	StringBuilder s4=new StringBuilder();
	StringBuilder s5=new StringBuilder();
	StringBuilder s6=new StringBuilder();
    ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
//	Connection con=ConnectionDAO.getConnection();
	try {
//		con.setAutoCommit(false);
//		cst=con.prepareCall("call Loan_Authorization(?,?,STR_TO_DATE(?,'"+dateFormatWithTime+"'),?,?,?,?,?)");
		  in.add(CommonFunction.checkNull( vo.getCompanyId()).trim());
		  in.add(CommonFunction.checkNull(vo.getRschId()).trim());
		String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getBussinessDate()).trim());
	    if(date != null)
	    	in.add(date);
	  
//		cst.setString(3, ((CommonFunction.checkNull(vo.getBussinessDate()).trim())));
	    in.add(CommonFunction.checkNull(vo.getUserId()).trim());
	    in.add(CommonFunction.checkNull(vo.getDecision()).trim());
	    in.add(CommonFunction.checkNull(vo.getComments()).trim());
		out.add(s1);
	    out.add(s2);
	    out.add(s3);
	    out.add(s4);
	    out.add(s5);
	    out.add(s6);
//		cst.registerOutParameter(7, Types.CHAR);
//		cst.registerOutParameter(8, Types.CHAR);
//		cst.executeUpdate();
	    outMessages=(ArrayList) ConnectionDAO.callSP("Resch_Author",in,out);
	    s1.append(CommonFunction.checkNull(outMessages.get(0)));
	    s2.append(CommonFunction.checkNull(outMessages.get(1)));
	    s3.append(CommonFunction.checkNull(outMessages.get(2)));
	    s4.append(CommonFunction.checkNull(outMessages.get(3)));
	    s5.append(CommonFunction.checkNull(outMessages.get(4)));
	    s6.append(CommonFunction.checkNull(outMessages.get(5)));
//		s1= cst.getString(7);
//		s2 = cst.getString(8);
		if(s5!=null && s5.toString().equalsIgnoreCase("S"))
		{
			status=s5.toString();
		}
		else
		{
			status=s6.toString();
		}
		logger.info("s1: "+s1);
		logger.info("s2: "+s2);
		logger.info("s3: "+s3);
		logger.info("s4: "+s4);
		logger.info("status: "+s5);
		logger.info("s6: "+s6);
	}
  catch (Exception e) {
		
		
		e.printStackTrace();
	}
		
	finally
	{
		s1=null;
		s2=null;
		s3=null;
		s4=null;
		s5=null;
		s6=null;
	}
	return status;
 }


public boolean deleteDueDateData(String loanId, String reschId) 
{
	logger.info("in deleteDueDateData()");
	ArrayList qryList=new ArrayList();
	boolean status=false;
	StringBuilder query=new StringBuilder();
	query.append("delete from cr_resch_dtl where RESCH_ID="+reschId.trim()+" and LOAN_ID="+loanId.trim());
	PrepStmtObject inPrepStmtObject = new PrepStmtObject();
	inPrepStmtObject.setSql(query.toString());
	logger.info("in deleteDueDateData()  Query  :  "+inPrepStmtObject.printQuery());
	qryList.add(inPrepStmtObject);
	try
	{
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	}
	catch (Exception e)
	{e.printStackTrace();}
	return status;
}

public String getBillFlag(LoanInitAuthorVo vo)
{
	StringBuilder billFlagQuery = new StringBuilder();
	billFlagQuery.append("select a.BILL_FLAG from cr_repaysch_dtl a join CR_RESCH_DTL b on (a.loan_id=b.loan_id) and (b.loan_id='"+CommonFunction.checkNull(vo.getLoanId())+"' and b.RESCH_ID='"+CommonFunction.checkNull(vo.getRschId())+"') where a.INSTL_NO=b.DEFERAL_FROM_INSTL");
	logger.info("billFlagQuery : " + billFlagQuery);
	String result = ConnectionDAO.singleReturn(billFlagQuery.toString());
	return result;
}

		
}
	
	
	
	

	

	
  

