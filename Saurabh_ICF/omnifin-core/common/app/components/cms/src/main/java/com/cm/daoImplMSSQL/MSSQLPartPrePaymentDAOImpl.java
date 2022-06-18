package com.cm.daoImplMSSQL;

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

import com.cm.dao.PartPrePaymentDAO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.cm.vo.PartPrePaymentAuthorVO;
import com.cm.vo.PartPrePaymentMakerVO;
import com.cm.vo.PartPrePaymentSearchVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.CodeDescVo;
import com.cp.vo.RepayScheduleVo;

public class MSSQLPartPrePaymentDAOImpl implements PartPrePaymentDAO 
{
	private static final Logger logger = Logger.getLogger(MSSQLPartPrePaymentDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	
	public ArrayList<PartPrePaymentSearchVO> searchPartPrePaymentData(PartPrePaymentSearchVO vo,String type)
	{
		logger.info("In searchPartPrePaymentData method of PartPrePaymentDAOImpl");
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
	              logger.info("In searchPartPrePaymentData().....................................Dao Impl");
	              
	              loanId.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID()).trim()));
	              customerName.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()));
	              boolean appendSQL=false;
	              StringBuffer bufInsSql =	new StringBuffer();
	              StringBuffer bufInsSqlTempCount = new StringBuffer();
	             
	              bufInsSql.append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, c.product_desc, d.scheme_desc,");
	              bufInsSql.append(dbo);
	              bufInsSql.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"') as loan_approval_date, a.loan_id, e.resch_id, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=E.MAKER_ID) MAKER_ID  ");
	              bufInsSql.append(" from cr_loan_dtl a,gcd_customer_m b," +
					 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='P' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
	              bufInsSqlTempCount.append("select count(1)"+
	     			 " from cr_loan_dtl a,gcd_customer_m b," +
	     			 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
	     			 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='P' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
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
	            logger.info("In searchPartPrePaymentData() ## tmp ## "+tmp);
	            logger.info("In searchPartPrePaymentData() ## tmp1 ## "+tmp1);
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
	//				if((loanId==null && customerName==null) || ((loanId.toString()).equalsIgnoreCase("") && customerName.toString().equalsIgnoreCase("")) 
	//						  || vo.getCurrentPageLink()>1)
	//				{
						logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						  if(vo.getCurrentPageLink()>1)
						  {
							  startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							  endRecordIndex = no;
							  logger.info("startRecordIndex .................... "+startRecordIndex);
							  logger.info("endRecordIndex .................... "+endRecordIndex);
						  }
						  
						  bufInsSql.append(" ORDER BY a.loan_no OFFSET ");
							bufInsSql.append(startRecordIndex);
							bufInsSql.append(" ROWS FETCH next ");
							bufInsSql.append(endRecordIndex);
							bufInsSql.append(" ROWS ONLY ");
						//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
						
	//				}
				  logger.info("search Query...else-------."+bufInsSql);
				  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				}
	          logger.info("searchPartPrePaymentData: "+searchlist.size());
	          for(int i=0;i<searchlist.size();i++){
	          logger.info("searchPartPrePaymentData: "+searchlist.get(i).toString());
	          ArrayList data=(ArrayList)searchlist.get(i);
	          if(data.size()>0){
	        	  PartPrePaymentSearchVO vo1= new PartPrePaymentSearchVO();
	        	  if(type.equalsIgnoreCase("P"))
	        	  {
	        		  vo1.setLoanNo("<a href=partPrePaymentSearch.do?method=showPartPrePaymentDataMaker&loanId="
	  					+ (CommonFunction.checkNull(data.get(6)).trim())
	  					+ "&reschId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
	  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
	        	  }
	        	  if(type.equalsIgnoreCase("F"))
	        	  {
	        		  vo1.setLoanNo("<a href=partPrePaymentSearch.do?method=showPartPrePaymentDataAuthor&loanId="
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
	public String partPrePaymentRealizeFlag() 
	{
		String flag="";
		StringBuilder query = new StringBuilder();
		query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='REALIZE_FLAG'");
		logger.info("Query for getting partPrePaymentRealizeFlag from parameter_mst  : "+query.toString());
		try
		{
			flag = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public ArrayList<PartPrePaymentMakerVO> selectPartPrePaymentData(String loanId,String reschId, String recStatus) 
	{
		logger.info("In selectPartPrePaymentData method of PartPrePaymentDAOImpl");
		ArrayList partPrePaymentData = new ArrayList();
		PartPrePaymentMakerVO vo = null;
		try{

			  ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();
			  StringBuilder query=new StringBuilder();
		 query.append("select a.loan_id,a.loan_no, b.customer_name, c.product_desc, d.scheme_desc,");
		 query.append("(select ISNULL(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+loanId+"' and rec_status='A'),"+
			  	 " a.loan_balance_principal,");
		 query.append(dbo);
		 query.append("DATE_FORMAT((select min(instl_date) from cr_repaysch_dtl where loan_id='"+loanId+"'" +
			  	 " and bill_flag='N' and instl_date>(select parameter_value from parameter_mst where parameter_key='BUSINESS_DATE')),'"+dateFormat+"')," +
			  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+loanId+"' and RESCH_TYPE='P' and rec_status='A'),");
		 query.append(dbo);
		 query.append("DATE_FORMAT((select max(RESCH_EFF_DATE) from cr_resch_dtl where loan_id='"+loanId+"' and RESCH_TYPE='P' and rec_status='A'),'"+dateFormat+"')," +
			  	 " a.loan_repayment_type, e.REQUEST_NO, ");
		 query.append(dbo);
		 query.append("DATE_FORMAT(e.RESCH_EFF_DATE,'"+dateFormat+"'), e.PREPAYMENT_AMOUNT, e.RESCH_CHARGES, "+
			  	 " e.PRE_PAYMENT_PARAMETER, e.RESCH_REASON, e.remarks, e.DEFERAL_FROM_INSTL,");
		 query.append("(Select (sum(ISNULL(txn.ADVICE_AMOUNT,0))-(sum(ISNULL(txn.TXN_ADJUSTED_AMOUNT,0))+sum(");
		 query.append("ISNULL(txn.AMOUNT_IN_PROCESS,0)))) d from cr_txnadvice_dtl txn where LOAN_ID='"+loanId+"' and ADVICE_TYPE='P')amoumt "+
				 " from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, cr_resch_dtl e" +
				 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
				 " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+""+
				 " and e.resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId).trim())+" ");

			 logger.info("In selectPartPrePaymentData : "+query.toString());
			 mainlist=ConnectionDAO.sqlSelect(query.toString());
			 
			 query=null;
			 
			 String cal = ConnectionDAO.singleReturn("Select ((sum(txn.ADVICE_AMOUNT)-sum(txn.TXN_ADJUSTED_AMOUNT)-sum(txn.AMOUNT_IN_PROCESS))-(Select (sum(txn.ADVICE_AMOUNT)-sum(txn.TXN_ADJUSTED_AMOUNT)-sum(txn.AMOUNT_IN_PROCESS)) d from  cr_txnadvice_dtl txn where LOAN_ID='"+loanId+"' and ADVICE_TYPE='R' ) )d from  cr_txnadvice_dtl txn where LOAN_ID='"+loanId+"' and ADVICE_TYPE='P' and charge_code_id=10");
			 
				StringBuilder realize = new StringBuilder();
				realize.append("select * from cr_instrument_dtl where TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' and isnull(TXN_TYPE,'')='LIM' and rec_status ='D'");
				String real = ConnectionDAO.singleReturn(realize.toString());
				logger.info("real status:::::::: "+ real);
				
			 logger.info("In selectPartPrePaymentData.....mainlist size: "+mainlist.size());
			if(mainlist.size()!=0)
			{
				
			 for(int i=0;i<mainlist.size();i++){

				subList=(ArrayList)mainlist.get(i);
				if(subList.size()>0){
					vo = new PartPrePaymentMakerVO();
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
					
					vo.setNextDueDate((CommonFunction.checkNull(subList.get(7)).trim()));
					if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
						vo.setPartPaymentSinceDisbursal("0");
					else
						vo.setPartPaymentSinceDisbursal((CommonFunction.checkNull(subList.get(8)).trim()));
					
					vo.setLastPartPaymentDate((CommonFunction.checkNull(subList.get(9)).trim()));
					vo.setInstallmentType((CommonFunction.checkNull(subList.get(10)).trim()));
					if((CommonFunction.checkNull(subList.get(11)).trim()).equalsIgnoreCase("0"))
						vo.setReqRefNo("");
					else
						vo.setReqRefNo((CommonFunction.checkNull(subList.get(11)).trim()));
					vo.setPartPaymentDate((CommonFunction.checkNull(subList.get(12)).trim()));
					
					if((CommonFunction.checkNull(subList.get(13))).trim().equalsIgnoreCase(""))
						vo.setPartPaymentAmount("0");
					else
					{
						Number partPaymentAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(13))).trim());
						vo.setPartPaymentAmount(myFormatter.format(partPaymentAmount));
					}
					if((CommonFunction.checkNull(subList.get(14))).trim().equalsIgnoreCase(""))
						vo.setReschCharges("0");
					else
					{
						Number partPaymentCharges = myFormatter.parse((CommonFunction.checkNull(subList.get(14))).trim());
						vo.setReschCharges(myFormatter.format(partPaymentCharges));
					}
					
					vo.setPartPaymentParameter((CommonFunction.checkNull(subList.get(15))).trim());
					vo.setReschReason((CommonFunction.checkNull(subList.get(16)).trim()));
					vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(17)).trim()));
					vo.setLbxInstlNo((CommonFunction.checkNull(subList.get(18))));
					vo.setAmount((CommonFunction.checkNull(subList.get(19))));
					vo.setPartPrePaymentCal(cal);
					vo.setRealize(real);
					partPrePaymentData.add(vo);
				}
			  }
			}
			
			cal=null;
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return partPrePaymentData;
	}
	public String savePartPrePaymentData(PartPrePaymentMakerVO vo)
	{
		logger.info("In savePartPrePaymentData method of PartPrePaymentDAOImpl");
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
				String PartPaymentAmount = vo.getPartPaymentAmount();
				 if(CommonFunction.checkNull(PartPaymentAmount).equalsIgnoreCase(""))
			        {
					 PartPaymentAmount="0";
			        }
			
				 String ReschCharges = vo.getReschCharges();
				 if(CommonFunction.checkNull(ReschCharges).equalsIgnoreCase(""))
			        {
					 ReschCharges="0";
			        }
			  in.add(0);
			  in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
			  in.add("P");

	        if((CommonFunction.checkNull(vo.getReqRefNo()).trim()).equalsIgnoreCase(""))
	        	reqNum="0";
	        else
	        	reqNum=(CommonFunction.checkNull(vo.getReqRefNo()).trim());

	        	in.add(reqNum);
	        
	        	String date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getPartPaymentDate()).trim());
	      
	 	    if(date1 != null)
	 	    	in.add(date1);
	        String date2=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getPartPaymentDate()).trim());

	 	    if(date2 != null)
	 	    	in.add(date2);

	 	    in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxInstlNo())));
	        in.add(myFormatter.parse(CommonFunction.checkNull(PartPaymentAmount).trim()).doubleValue());
	        in.add(myFormatter.parse(CommonFunction.checkNull(ReschCharges).trim()).doubleValue());
	        in.add(CommonFunction.checkNull(vo.getPartPaymentParameter()).trim());
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
		   
		    outMessages=(ArrayList) ConnectionDAO.callSP("Part_PrePayment_save",in,out);
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
	public String updatePartPrePaymentData(PartPrePaymentMakerVO vo, String type)
	{
		logger.info("In updatePartPrePaymentData method of PartPrePaymentDAOImpl");
//		String reschId="";
//	    String reqNum="";
//	    String s1= "";
//	    String s2 ="";
//	    String s3="";
		
		String reschId="";
	    StringBuilder reqNum=new StringBuilder();
	    StringBuilder s1=new StringBuilder();
	    StringBuilder s2 =new StringBuilder();
	    StringBuilder s3=new StringBuilder();
	    
	    
	    ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		try
		{

//			con.setAutoCommit(false);
//		cst=con.prepareCall("call Part_PrePayment_save(?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?)");
			 in.add(Integer.parseInt(CommonFunction.checkNull(vo.getReschId())));
			 in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
			 in.add("P");

	        if((CommonFunction.checkNull(vo.getReqRefNo()).trim()).equalsIgnoreCase(""))
	        	reqNum.append("0");
	        else
	        	reqNum.append((CommonFunction.checkNull(vo.getReqRefNo()).trim()));

	        in.add(reqNum);
	        
	        String date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getPartPaymentDate()).trim());
	 	    if(date1 != null)
	 	    	in.add(date1);

	        String date2=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getPartPaymentDate()).trim());
	 	    if(date2 != null)
	 	    	in.add(date2);
//	        in.add((CommonFunction.checkNull(vo.getPartPaymentDate()).trim()));
//	        in.add((CommonFunction.checkNull(vo.getPartPaymentDate()).trim()));
	        in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxInstlNo())));
	        in.add(myFormatter.parse(CommonFunction.checkNull(vo.getPartPaymentAmount()).trim()).doubleValue());
	        in.add(myFormatter.parse(CommonFunction.checkNull(vo.getReschCharges()).trim()).doubleValue());
	        in.add(CommonFunction.checkNull(vo.getPartPaymentParameter()).trim());
	        in.add((CommonFunction.checkNull(vo.getReschReason()).trim()));
	        in.add(type);
	        in.add((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim())));
	        String date=CommonFunction.changeFormat((CommonFunction.checkNull(vo.getMakerDate()).trim()));
		    if(date != null)
		    	in.add(date);
//	        in.add((CommonFunction.checkNull(vo.getMakerDate()).trim()));
		    out.add(s1);
		    out.add(s2);
		    out.add(s3);
		    
		    outMessages=(ArrayList) ConnectionDAO.callSP("Part_PrePayment_save",in,out);
		    
		    s1.append(CommonFunction.checkNull(outMessages.get(0)));
		    s2.append(CommonFunction.checkNull(outMessages.get(1)));
		    s3.append(CommonFunction.checkNull(outMessages.get(2)));

		    logger.info("s1: "+s1);
	        logger.info("s2: "+s2);
	        logger.info("s3: "+s3);
	        if((s2.toString()).equalsIgnoreCase("S"))
	        {
	        	reschId=CommonFunction.checkNull(s1.toString());
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
	public ArrayList<InstallmentPlanForCMVO> getInstallType(String loanId) 
	{
		logger.info("In getInstallType method of PartPrePaymentDAOImpl");
        ArrayList<InstallmentPlanForCMVO> list=new ArrayList<InstallmentPlanForCMVO>();
        ArrayList mainList=new ArrayList ();
			ArrayList subList =new ArrayList();
    		
    		try{
    			StringBuilder query=new StringBuilder();
             	query.append(" select distinct FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,LOAN_INSTALLMENT_TYPE,LOAN_NO_OF_INSTALLMENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,LOAN_RATE_TYPE,LOAN_LOAN_AMOUNT,RECOVERY_TYPE "+
    				"  from cr_loan_dtl L left JOIN cr_installment_plan D on D.LOAN_ID=L.LOAN_ID "+               			
    				" where L.LOAN_ID="+loanId);
    			
    		logger.info("getInstallType Queryl: "+query.toString());
    		
    		mainList=ConnectionDAO.sqlSelect(query.toString());
    		
			for(int i=0;i<mainList.size();i++)
			{
				subList= (ArrayList)mainList.get(i);
				if(subList.size()>0){
					InstallmentPlanForCMVO ipVo= new InstallmentPlanForCMVO();
    				ipVo = new InstallmentPlanForCMVO();  
    				if(CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("E")||CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("P"))
    				{
    					ipVo.setFromInstallment("1");
    					ipVo.setToInstallment((CommonFunction.checkNull(subList.get(4))).trim());
    					 if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
        				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
 	 			   
        				     logger.info("RecoveryPercen: "+RecoveryPercen);			
 	 			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
        				      }else{
        				     ipVo.setRecoveryPercen("0");
        				          }
    				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
     			  	ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
     			
     			      //ipVo.setRecoveryType("P");
    				}
    				else{
    				    ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
    				    ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
    				    if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
    				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
	 			   
    				     logger.info("RecoveryPercen: "+RecoveryPercen);			
	 			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
    				      }else{
    				     ipVo.setRecoveryPercen("0");
    				          }
    				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
    				    ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
    				 
         				
    				    }
    				ipVo.setRateType((CommonFunction.checkNull(subList.get(7))).trim());
   				
    				  if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
     				     Number princam = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
 	 			   
     				     logger.info("princ amount: "+princam);			
 	 			         ipVo.setPrinAm(myFormatter.format(princam));
     				      }else{
     				     ipVo.setPrinAm("0.00");
     				          }
      				if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
     				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
 	 			   
     				     logger.info("instamount: "+instam);			
 	 			         ipVo.setInstalAm(myFormatter.format(instam));
     				      }else{
     				     ipVo.setInstalAm("0.00");
     				          }
   				
   				if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
     				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
 	 			   
     				     logger.info("setLoanAmount: "+instam);			
 	 			         ipVo.setLoanAmount(myFormatter.format(instam));
     				      }else{
     				     ipVo.setLoanAmount("0.00");
     				          }
   				ipVo.setRecoveryType((CommonFunction.checkNull(subList.get(9))).trim());
    				   list.add(ipVo);
    			     }
    		  }
    		query=null;
    		
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return list;
    	}
	public ArrayList<PaymentMakerForCMVO> viewReceivableForPartPrePayment(int loanId)
	{
		logger.info("In viewReceivableForPartPrePayment method of PartPrePaymentDAOImpl");
 		ArrayList<PaymentMakerForCMVO> viewReceivabList=new ArrayList<PaymentMakerForCMVO>(); 		
 		try{
 			ArrayList mainList=new ArrayList ();
 			ArrayList subList =new ArrayList();	 					
 			logger.info(" In viewReceivableForPartPrePayment....");	
 			StringBuilder query=new StringBuilder();
 		 query.append("SELECT ");
 		query.append(dbo);
 		query.append("DATE_FORMAT(ADVICE_DATE,'"+dateFormat+"'),(Select CHARGE_DESC From com_charge_code_m "+
	 		" Where CHARGE_CODE=CHARGE_CODE_ID) CHARGE,ORG_ADVICE_AMOUNT,WAIVE_OFF_AMOUNT,TDS_AMOUNT," +
	 		" TXN_ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,"+
	        " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,ADVICE_AMOUNT,G.DESCRIPTION"+
	 		" FROM cr_txnadvice_dtl A inner join generic_master G on A.BP_TYPE=G.VALUE AND G.GENERIC_KEY='BPTYPE' WHERE A.REC_STATUS in('A','F') " +
	 		" AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0 "+
	        " AND ADVICE_TYPE='R' AND LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'" +
	       	" AND BP_TYPE in(select bp_type from business_partner_view where LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"') " +
	       	" ORDER BY ADVICE_DATE ASC");

 			
 			logger.info("In viewReceivableForPartPrePayment: "+query.toString());	
 			
 			mainList=ConnectionDAO.sqlSelect(query.toString());
 			for(int i=0;i<mainList.size();i++)
 			{
 				subList= (ArrayList)mainList.get(i);
 				if(subList.size()>0){
 					PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
 					paymentVO.setPaymentDate((CommonFunction.checkNull(subList.get(0)).trim()));
	 				paymentVO.setChargeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
	 				if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
	 					paymentVO.setOriginalAmount("0");
	 				else
	 				{
	 					Number orgAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
	 					paymentVO.setOriginalAmount(myFormatter.format(orgAmt));
	 				}
	 				
	 				if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
	 					paymentVO.setWaiveOffAmount("0");
	 				else
	 				{
	 					Number waivedOffAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(3)).trim()));
	 					paymentVO.setWaiveOffAmount(myFormatter.format(waivedOffAmt));
	 				}
	 				
	 				if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
	 					paymentVO.setTdsadviseAmount("0");
	 				else
	 				{
	 					Number tdsAdviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(4)).trim()));
	 					paymentVO.setTdsadviseAmount(myFormatter.format(tdsAdviceAmt));
	 				}
	 				
	 				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
	 					paymentVO.setAdjustedAmount("0");
	 				else
	 				{
	 					Number adjustedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(5)).trim()));
	 					paymentVO.setAdjustedAmount(myFormatter.format(adjustedAmt));
	 				}
	 				
	 				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
	 					paymentVO.setAmountInProcess("0");
	 				else
	 				{
	 					Number amtInProc = myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim()));
	 					paymentVO.setAmountInProcess(myFormatter.format(amtInProc));
	 				}
	 				
	 				if((CommonFunction.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
	 					paymentVO.setBalanceAmount("0");
	 				else
	 				{
	 					Number balAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim()));
	 					paymentVO.setBalanceAmount(myFormatter.format(balAmt));
	 				}
	 				
	 				if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
	 					paymentVO.setAdviceAmount("0");
	 				else
	 				{
	 					Number adviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(8)).trim()));
	 					paymentVO.setAdviceAmount(myFormatter.format(adviceAmt));
	 				}
	 				paymentVO.setBusinessPartnerType((CommonFunction.checkNull(subList.get(9)).trim()));
	 				viewReceivabList.add(paymentVO);
 				}
 			}
 				query=null;
 				
 		}catch(Exception e){
 					e.printStackTrace();
 				}


 				return viewReceivabList;
 			}
	public ArrayList<PartPrePaymentMakerVO> retrievePartPrePaymentValues(String lbxLoanNoHID)
	{
		logger.info("In retrievePartPrePaymentValues.....DAOImpl");
		ArrayList<PartPrePaymentMakerVO> partPrePaymetlist = new ArrayList();
		PartPrePaymentMakerVO vo = null;
		StringBuilder queryCheck=new StringBuilder();
		 queryCheck.append("Select count(resch_id) from cr_resch_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'" +
		" and rec_status in ('P','F') and resch_type='P'");
		String tempCount = ConnectionDAO.singleReturn(queryCheck.toString());
		int count = Integer.parseInt(tempCount);
		
		queryCheck= null;
		
		ArrayList mainlist=new ArrayList();
		ArrayList subList=new ArrayList();
		StringBuilder query = new StringBuilder();
		if(count==0)
		{
			try{
				  query.append("select a.loan_id,a.loan_no, b.customer_name, c.product_desc,  d.scheme_desc,");
				  query.append("(select ISNULL(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+lbxLoanNoHID+"' and rec_status='A')," +
				  	 " a.loan_balance_principal, ");
				  query.append(dbo);
				  query.append("DATE_FORMAT((select min(instl_date) from cr_repaysch_dtl where loan_id='"+lbxLoanNoHID+"' and" +
				  	 " bill_flag='N' and instl_date>(select parameter_value from parameter_mst where parameter_key='BUSINESS_DATE')),'"+dateFormat+"')," +
				  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and RESCH_TYPE='P' and rec_status='A'),");
				  query.append("ISNULL(");
				  query.append(dbo);
				  query.append("DATE_FORMAT((select max(RESCH_EFF_DATE) from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and RESCH_TYPE='P' and rec_status='A'),'"+dateFormat+"'),'')," +
				  	 " a.loan_repayment_type," +
				  	 " (select min(instl_no) from cr_repaysch_dtl where loan_id='"+lbxLoanNoHID+"' and bill_flag='N' "+
				  	 " and instl_date>(select parameter_value from parameter_mst where parameter_key='BUSINESS_DATE')) as instl_no," +
					 " (Select (sum(ISNULL(txn.ADVICE_AMOUNT,0))-(sum(ISNULL(txn.TXN_ADJUSTED_AMOUNT,0))+sum(");
				  query.append("ISNULL(txn.AMOUNT_IN_PROCESS,0)))) d from cr_txnadvice_dtl txn where LOAN_ID='"+lbxLoanNoHID+"' and ADVICE_TYPE='P')amoumt from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d " +
					 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and a.loan_id='"+lbxLoanNoHID+"'");
		
				 logger.info("In retrievePartPrePaymentValues : "+query.toString());
				 mainlist=ConnectionDAO.sqlSelect(query.toString());
				 
				 query = null;

				 
				 StringBuilder cal=new StringBuilder();
				  cal.append(ConnectionDAO.singleReturn("Select ((sum(txn.ADVICE_AMOUNT)-sum(txn.TXN_ADJUSTED_AMOUNT)-sum(txn.AMOUNT_IN_PROCESS))-(Select (sum(txn.ADVICE_AMOUNT)-sum(txn.TXN_ADJUSTED_AMOUNT)-sum(txn.AMOUNT_IN_PROCESS)) d from  cr_txnadvice_dtl txn where LOAN_ID='"+lbxLoanNoHID+"' and ADVICE_TYPE='R' ) )d from  cr_txnadvice_dtl txn where LOAN_ID='"+lbxLoanNoHID+"' and ADVICE_TYPE='P' and charge_code_id=10 "));
				 int size = mainlist.size();
				 logger.info("In retrievePartPrePaymentValues.....mainlist size: "+size);
				 logger.info("cal:............................. "+cal);
				if(size!=0)
				{
					
				 for(int i=0;i<size;i++){
		
					subList=(ArrayList)mainlist.get(i);
					if(subList.size()>0){
						vo = new PartPrePaymentMakerVO();
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
						
						vo.setNextDueDate((CommonFunction.checkNull(subList.get(7)).trim()));
						
						if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
							vo.setPartPaymentSinceDisbursal("0");
						else
							vo.setPartPaymentSinceDisbursal((CommonFunction.checkNull(subList.get(8)).trim()));
						vo.setLastPartPaymentDate((CommonFunction.checkNull(subList.get(9)).trim()));
						vo.setInstallmentType((CommonFunction.checkNull(subList.get(10)).trim()));
						if(CommonFunction.checkNull(subList.get(11)).trim().equalsIgnoreCase(""))
						{
							vo.setLbxInstlNo("0");
						}
						else
						{
							vo.setLbxInstlNo((CommonFunction.checkNull(subList.get(11)).trim()));
						}
						vo.setAmount((CommonFunction.checkNull(subList.get(12)).trim()));
						//vo.setLbxInstlNo((CommonFunction.checkNull(subList.get(11)).trim()));
						vo.setPartPrePaymentCal(cal.toString());
						partPrePaymetlist.add(vo);
					}
					cal=null;
				  }
				 
				}
			}catch(Exception e){
			e.printStackTrace();
			}
			finally
			{
				
				tempCount = null;
							}
		}
		else
			logger.info("Size of partPrePaymetlist: "+partPrePaymetlist.size());
		return partPrePaymetlist;
		
	}
	public String savePartPrePaymentAuthor(PartPrePaymentAuthorVO vo)
	{
		logger.info("Inside savePartPrePaymentAuthor.......CMDaoImpl");
	String status="";
		
	
		
//		CallableStatement cst=null;
//	    Connection con=ConnectionDAO.getConnection();
	    ArrayList qryList = new ArrayList();
	    ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
//		String s1="";
//		String s2="";
//		String s3="";
//		String s4="";
//		String s5="";
//		String s6="";
		StringBuilder s1=new StringBuilder();
		StringBuilder s2=new StringBuilder();
		StringBuilder s3=new StringBuilder();
		StringBuilder s4=new StringBuilder();
		StringBuilder s5=new StringBuilder();
		StringBuilder s6=new StringBuilder();
		
		try
		{

//			con.setAutoCommit(false);
//	        cst=con.prepareCall("call Part_Prepayment_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?)");
			   in.add(Integer.parseInt(CommonFunction.checkNull(vo.getCompanyId()).trim()));
			   in.add(Integer.parseInt(CommonFunction.checkNull(vo.getReschId()).trim()));
	        String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim());
		    if(date != null)
		    	in.add(date);
//	        cst.setString(3, CommonFunction.checkNull(vo.getAuthorDate()).trim());
		    in.add(CommonFunction.checkNull(vo.getAuthorId()).trim());
		    in.add(CommonFunction.checkNull(vo.getDecision()).trim());
		    in.add(CommonFunction.checkNull(vo.getComments()).trim());
		    out.add(s1);
		    out.add(s2);
		    out.add(s3);
		    out.add(s4);
		    out.add(s5);
		    out.add(s6);
		    outMessages=(ArrayList) ConnectionDAO.callSP("Part_Prepayment_Author",in,out);
		    s3.append(CommonFunction.checkNull(outMessages.get(0)));
		    s4.append(CommonFunction.checkNull(outMessages.get(1)));
		    s5.append(CommonFunction.checkNull(outMessages.get(2)));
		    s6.append(CommonFunction.checkNull(outMessages.get(3)));
		    s1.append(CommonFunction.checkNull(outMessages.get(4)));
		    s2.append(CommonFunction.checkNull(outMessages.get(5)));
		  

	        logger.info("s1: "+s1);
	        logger.info("s2: "+s2);
	        if((s1.toString()).equalsIgnoreCase("S"))
	        {
//				con.commit();
	        	status=s1.toString();
	        }
	        else if((s1.toString()).equalsIgnoreCase("E"))
	        {
//	        	con.rollback();
	        	status = s2.toString();
	        }
	        
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
	
	public String getMakerDate(String reschId) 
	{
		String makeDate="";
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append(dbo);
		query.append("date_format(MAKER_DATE,'%d-%m-%Y') from cr_resch_dtl where resch_id="+reschId.trim());
		logger.info("Query for getting Maker Date of repricing  :  "+query.toString());
		try
		{
			makeDate = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return makeDate;
	}
	
	public ArrayList<RepayScheduleVo> getNewRepaySchedulePartPayment(RepayScheduleVo vo, String loanId, String reschId) 
	{
	      logger.info("In getNewRepaySchedulePartPayment DAOImpl");
	      String status="";
	      CallableStatement cst=null;
	    Connection con=ConnectionDAO.getConnection();
	      ArrayList<RepayScheduleVo> list = new ArrayList<RepayScheduleVo>();
	      ArrayList mainlist = new ArrayList();
	      ArrayList subList = new ArrayList();
	      
//	    String chkQuery = "select count(INSTALLMENT_PLAN_ID) from cr_resch_installment_plan where " +
//	    " resch_id="+(CommonFunction.checkNull(reschId))+" and" +
//	    " loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+"";
//	    String chkStatusStr = ConnectionDAO.singleReturn(chkQuery);
//	    int chkStatus = Integer.parseInt(chkStatusStr);
//	    logger.info("chkStatus: "+chkStatus);
//	    if(chkStatus>0)
//	    {
	            try
	            {
	            		            	
	              con.setAutoCommit(false);
	              cst=con.prepareCall("{call Part_Prepayment_Author(?,?,?,?,?,?,?,?,?,?,?,?)}");
	              cst.setInt(1, Integer.parseInt((CommonFunction.checkNull(vo.getCompanyId()).trim())));
	              cst.setInt(2, Integer.parseInt((CommonFunction.checkNull(reschId).trim())));
	              cst.setString(3, (CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim())));
	              cst.setString(4, (CommonFunction.checkNull(vo.getAuthorId()).trim()));
	              cst.setString(5, "T");
	              cst.setString(6, "");
	              cst.registerOutParameter(7, Types.CHAR );
	              cst.registerOutParameter(8, Types.CHAR );
	              cst.registerOutParameter(9, Types.CHAR );
	              cst.registerOutParameter(10, Types.CHAR );
	              cst.registerOutParameter(11, Types.CHAR );
	              cst.registerOutParameter(12, Types.CHAR );
	              cst.execute();
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
	                  
	                        String bussIrrQ="select deal_irr2 from cr_deal_loan_dtl where DEAL_ID=(select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId+")";
	                        logger.info("Query in getRepaySchFieldsDetail--DEAL_IRR2---" + bussIrrQ);
	                        String bussIrr = ConnectionDAO.singleReturn(bussIrrQ);
	                        
	                        StringBuilder query=new StringBuilder();
	                        query.append("select R_Seq_No,");
	                        query.append(dbo);
	                        query.append("DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
	                        " CASE WHEN R_ADV_FLAG='Y' THEN 'YES' ELSE 'NO' END,R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" AND MAKER_ID='"+CommonFunction.checkNull(vo.getAuthorId()).trim()+"' order by R_Seq_No");
	                        
	                        logger.info("Query in getNewRepayScheduleDeferral-----" + query.toString());
	                        int j=1;
	                        Statement st = con.createStatement();
	                        ResultSet rs = st.executeQuery(query.toString());
	      
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

	public boolean deletePartPaymentData(String loanId, String reschId) 
	{
		logger.info("in deletePartPaymentData()");
		ArrayList qryList=new ArrayList();
		boolean status=false;
		StringBuilder query=new StringBuilder();
		query.append("delete from cr_resch_dtl where RESCH_ID="+reschId.trim()+" and LOAN_ID="+loanId.trim());
		PrepStmtObject inPrepStmtObject = new PrepStmtObject();
		inPrepStmtObject.setSql(query.toString());
		logger.info("in deleteRericingData()  Query  :  "+inPrepStmtObject.printQuery());
		qryList.add(inPrepStmtObject);
		try
		{
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		}
		catch (Exception e)
		{e.printStackTrace();}
		return status;
	}
	@Override
	public String getRecStatusForDisbursal(String lbxLoanNoHID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<CodeDescVo> getGenericMasterInfo(String genericKey) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
