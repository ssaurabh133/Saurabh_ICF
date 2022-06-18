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

import com.cm.dao.DeferralDAO;
import com.cm.vo.DeferralMakerVo;
import com.cm.vo.PartPrePaymentAuthorVO;
import com.cm.vo.PartPrePaymentSearchVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.RepayScheduleVo;

public class MSSQLDeferralDAOImpl implements DeferralDAO 
{
	private static final Logger logger = Logger.getLogger(MSSQLDeferralDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	//String selectFrom = resource.getString("lbl.selectFrom");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");

	public ArrayList<PartPrePaymentSearchVO> searchDeferralData(PartPrePaymentSearchVO vo,String type)
	{
		logger.info("In searchDeferralData() of DeferralDAOImpl");
//		String loanId="";
//		String customerName="";
//		String userName="";
		
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
				userName=null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
	       try{
	              logger.info("In searchDeferralData().....................................Dao Impl");
	              
	              loanId.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID()).trim()));
	              customerName.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()));
	              
	              boolean appendSQL=false;
	              
	              StringBuffer bufInsSql =	new StringBuffer();
	              StringBuffer bufInsSqlTempCount = new StringBuffer();
	             
	              bufInsSql.append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, c.product_desc, d.scheme_desc,");
	              bufInsSql.append(dbo);
	              bufInsSql.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"') as loan_approval_date, a.loan_id, e.resch_id, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=E.MAKER_ID) MAKER_ID ");
	               bufInsSql.append(" from cr_loan_dtl a,gcd_customer_m b," +
					 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='D' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
	              bufInsSqlTempCount.append("select count(1)"+
	     			 " from cr_loan_dtl a,gcd_customer_m b," +
	     			 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
	     			 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='D' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
				  if(vo.getStage()!=null && !vo.getStage().equalsIgnoreCase("F"))
					{
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
					if((!((loanId.toString()).equalsIgnoreCase(""))) || (!((customerName.toString()).equalsIgnoreCase(""))) || (!(vo.getLbxUserId().equalsIgnoreCase("")))){
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
				  if(!(vo.getLbxUserId().equalsIgnoreCase(""))&& vo.getStage().equalsIgnoreCase("F"))
					{
					  bufInsSql.append("  E.MAKER_ID!='"+vo.getLbxUserId()+"' AND");
					  bufInsSqlTempCount.append("  E.MAKER_ID!='"+vo.getLbxUserId()+"' AND");
					}
				}
				
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL)
				{	
					String tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
	            logger.info("In searchDeferralData() ## tmp ## "+tmp);
	            logger.info("In searchDeferralData() ## tmp1 ## "+tmp1);
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
						  bufInsSql.append(" ORDER BY a.loan_id OFFSET ");
				            bufInsSql.append(startRecordIndex);
				            bufInsSql.append(" ROWS FETCH next ");
				            bufInsSql.append(endRecordIndex);
				            bufInsSql.append(" ROWS ONLY ");
						  //bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
						
					}
				  logger.info("search Query...else-------."+bufInsSql);
				  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
//				}
	          logger.info("searchDeferralData: "+searchlist.size());
	          for(int i=0;i<searchlist.size();i++){
	          logger.info("searchDeferralData: "+searchlist.get(i).toString());
	          ArrayList data=(ArrayList)searchlist.get(i);
	          if(data.size()>0){
	        	  PartPrePaymentSearchVO vo1= new PartPrePaymentSearchVO();
	        	  if(type.equalsIgnoreCase("P"))
	        	  {
	        		  vo1.setLoanNo("<a href=deferralSearch.do?method=showDeferralDataMaker&loanId="
	  					+ (CommonFunction.checkNull(data.get(6)).trim())
	  					+ "&reschId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
	  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
	        	  }
	        	  if(type.equalsIgnoreCase("F"))
	        	  {
	        		  vo1.setLoanNo("<a href=deferralSearch.do?method=showDeferralDataAuthor&loanId="
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
			loanId=null;
			customerName=null;
						
		}

		return detailList;
	}
	public ArrayList<DeferralMakerVo> selectDeferralData(String loanId,String reschId, String recStatus) 
	{
		logger.info("In selectDeferralData() of DeferralDAOImpl");
		
		ArrayList<DeferralMakerVo> deferralData = new ArrayList();
		DeferralMakerVo vo = null;
		try{

			  ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();
			  StringBuilder query =new StringBuilder();
			  query.append("select a.loan_id,a.loan_no, b.customer_name, c.product_desc, d.scheme_desc,(select ");
			  query.append("ISNULL(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+loanId+"' and rec_status='A'),"+
			  	 " a.loan_balance_principal,"+
			  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+loanId+"' and RESCH_TYPE='D' and rec_status='A')," );
			  query.append(dbo);
			  query.append("DATE_FORMAT((select max(RESCH_EFF_DATE) from cr_resch_dtl where loan_id='"+loanId+"' and RESCH_TYPE='D' and rec_status='A'),'"+dateFormat+"')," +
			  	 " e.instl_deferred, e.deferal_from_instl,");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.RESCH_EFF_DATE,'"+dateFormat+"'),e.REQUEST_NO,"+
			  	 " a.LOAN_RATE_TYPE, a.LOAN_BASE_RATE_TYPE, a.LOAN_BASE_RATE, a.LOAN_REPAYMENT_FREQ,"+
			  	 " a.LOAN_INSTALLMENT_TYPE, a.LOAN_EMI_AMOUNT, a.LOAN_TENURE,");
			  query.append(dbo);
			  query.append("date_format(a.LOAN_MATURITY_DATE,'"+dateFormat+"'),");
			  query.append(dbo);
			  query.append("DATE_FORMAT((select min(instl_date) from cr_repaysch_dtl where loan_id='"+loanId+"' and bill_flag='N'),'"+dateFormat+"')," +
			  	 " e.gap_interest, e.gap_interest_method, e.resch_charges, "+
			  	 " e.RESCH_REASON, e.remarks "+
				 " from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, cr_resch_dtl e" +
				 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
				 " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+""+
				 " and e.resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId).trim())+" ");

			 logger.info("In selectDeferralData : "+query.toString());
			 
			 mainlist=ConnectionDAO.sqlSelect(query.toString());
			 logger.info("In selectDeferralData.....mainlist size: "+mainlist.size());
			 
			 query=null;
			
			 if(mainlist.size()!=0)
			{
				
			 for(int i=0;i<mainlist.size();i++){

				subList=(ArrayList)mainlist.get(i);
				if(subList.size()>0){
					vo = new DeferralMakerVo();
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
					
					if((CommonFunction.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
						vo.setDeferralsSinceDsibursal("0");
					else
						vo.setDeferralsSinceDsibursal((CommonFunction.checkNull(subList.get(7)).trim()));
					
					vo.setLastDeferralDate((CommonFunction.checkNull(subList.get(8)).trim()));
					vo.setDeferredInstallmentNo((CommonFunction.checkNull(subList.get(9)).trim()));
					vo.setDeferralFromInstallment((CommonFunction.checkNull(subList.get(10)).trim()));
					vo.setReschDate((CommonFunction.checkNull(subList.get(11)).trim()));
					
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("0"))
						vo.setReqRefNo("");
					else
						vo.setReqRefNo((CommonFunction.checkNull(subList.get(12)).trim()));
					
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("E"))
						vo.setInterestRateType("Equated");
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("F"))
						vo.setInterestRateType("Flat");
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("P"))
						vo.setInterestRateType("PTPM");
					
					vo.setBaseRateType((CommonFunction.checkNull(subList.get(14)).trim()));
					
					if((CommonFunction.checkNull(subList.get(15))).trim().equalsIgnoreCase(""))
						vo.setBaseRate("0");
					else
					{
						Number baseRate = myFormatter.parse((CommonFunction.checkNull(subList.get(15))).trim());
						vo.setBaseRate(myFormatter.format(baseRate));
					}
					
					if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("M"))
						vo.setInstallmentFrequency("Monthly");
					if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("B"))
						vo.setInstallmentFrequency("Bimonthly");
					if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("Q"))
						vo.setInstallmentFrequency("Quarterly");
					if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("H"))
						vo.setInstallmentFrequency("Half Yearly");
					if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("Y"))
						vo.setInstallmentFrequency("Yearly");
					
					if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("E"))
						vo.setInstallmentType("Eq. Installment");
					if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("G"))
						vo.setInstallmentType("Gr. Installment");
					if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("P"))
						vo.setInstallmentType("Eq. Principal");
					if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("Q"))
						vo.setInstallmentType("Gr. Principal1");
					if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("S"))
						vo.setInstallmentType("Gr. Principal2");
					
					if((CommonFunction.checkNull(subList.get(18))).trim().equalsIgnoreCase(""))
						vo.setEmi("0");
					else
					{
						Number emi = myFormatter.parse((CommonFunction.checkNull(subList.get(18))).trim());
						vo.setEmi(myFormatter.format(emi));
					}
					
					vo.setTenure((CommonFunction.checkNull(subList.get(19)).trim()));
					vo.setMaturityDate((CommonFunction.checkNull(subList.get(20)).trim()));
					vo.setNextDueDate((CommonFunction.checkNull(subList.get(21)).trim()));
					
					if((CommonFunction.checkNull(subList.get(22))).trim().equalsIgnoreCase(""))
						vo.setInterestForGapPeriod("0");
					else
					{
						Number interestForGapPeriod = myFormatter.parse((CommonFunction.checkNull(subList.get(22))).trim());
						vo.setInterestForGapPeriod(myFormatter.format(interestForGapPeriod));
					}
					
					vo.setInterestChargingMethod((CommonFunction.checkNull(subList.get(23)).trim()));
					
					if((CommonFunction.checkNull(subList.get(24))).trim().equalsIgnoreCase(""))
						vo.setReschCharges("0");
					else
					{
						Number partPaymentCharges = myFormatter.parse((CommonFunction.checkNull(subList.get(24))).trim());
						vo.setReschCharges(myFormatter.format(partPaymentCharges));
					}
					
					vo.setDeferralReason((CommonFunction.checkNull(subList.get(25)).trim()));
					vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(26)).trim()));
					deferralData.add(vo);
				}
			  }
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return deferralData;
	}
	public String checkDeferralSaveFeasibility(DeferralMakerVo vo)
	{
		logger.info("In checkDeferralSaveFeasibility() of DeferralDAOImpl");
		/*String retStr="";*/
		String retStr="";
		
		StringBuilder query1=new StringBuilder();
		
		 query1.append("select iif(s.MAXIMUM_DEFR_MONTHS_ALLOWED< "+StringEscapeUtils.escapeSql(vo.getDeferredInstallmentNo())+", " +
					" 'no', 'yes') as maximum_defr_month_allowed from cr_scheme_m s, cr_loan_dtl ld" +
					" left join cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='D'" +
					" where ld.loan_scheme=s.scheme_id " +
					" and ld.loan_id="+StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID())+" ");
			 
		 
		String maxDefrMnthAllwd = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
		
		StringBuilder query2=new StringBuilder();
		 query2.append("select iif(s.MAXIMUM_DEFR_MONTHS_TOTAL< (select isnull(sum(INSTL_DEFERRED),0) from" +
					" cr_resch_dtl where rec_status='A' and resch_type='D' and loan_id="+StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID())+")," +
					" 'no', 'yes') as maximum_defr_month_total from cr_scheme_m s, cr_loan_dtl ld" +
					" left join cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='D'" +
					" where ld.loan_scheme=s.scheme_id " +
					" and ld.loan_id="+StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID())+" ");
			
		
		String maxDefrMnthTotal = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
		
		if(maxDefrMnthAllwd.equalsIgnoreCase("no"))
		{
			retStr=("maxDefrMnthAllwd");
			logger.info("max Deferral limit Allowed for a Month exceeds");
		}
		if(maxDefrMnthTotal.equalsIgnoreCase("no"))
		{
			retStr=("maxDefrMnthTotal");
			logger.info("total max Deferral limit Allowed for loan exceeds");
		}
		query1=null;
		query2=null;
		return retStr;
		
	}
	
	public String saveDeferralData(DeferralMakerVo vo)
	{
		logger.info("In checkDeferralSaveFeasibility() of DeferralDAOImpl");
		/*String reschId="";
	    String reqNum="";*/
		
		String reschId="";
		StringBuilder reqNum=new StringBuilder();


	    
	    
	    ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		
		StringBuilder s1=new StringBuilder();
		StringBuilder s2=new StringBuilder();
		StringBuilder s3=new StringBuilder();
		/*String s1="";
		String s2="";
		String s3="";*/
		
		try
		{

//			con.setAutoCommit(false);
//			cst=con.prepareCall("call Deferral_save(?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?)");
			 in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
			 in.add("D");

	        if((CommonFunction.checkNull(vo.getReqRefNo()).trim()).equalsIgnoreCase(""))
	        	reqNum.append("0");
	        else
	        	reqNum.append((CommonFunction.checkNull(vo.getReqRefNo()).trim()));

	        in.add(reqNum);
	        
	        String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getReschDate()).trim());
	 	    if(date != null)
	 	    	in.add(date);
	 	   String date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getReschDate()).trim());
		    if(date1 != null)
		    	in.add(date1);
//	        in.add((CommonFunction.checkNull(vo.getReschDate()).trim()));
//	        in.add((CommonFunction.checkNull(vo.getReschDate()).trim()));
	        in.add(Integer.parseInt(CommonFunction.checkNull(vo.getDeferredInstallmentNo()).trim()));
	        in.add(Integer.parseInt(CommonFunction.checkNull(vo.getDeferralFromInstallment()).trim()));
	        in.add(0.0);
	        in.add(CommonFunction.checkNull(vo.getInterestChargingMethod()).trim());
	        if(vo.getReschCharges().trim().equals(""))
	        	in.add(0.00);
	        else
	        	in.add(myFormatter.parse(CommonFunction.checkNull(vo.getReschCharges()).trim()).doubleValue());
	        
	        in.add((CommonFunction.checkNull(vo.getDeferralReason()).trim()));
	        in.add("P");
	        in.add((CommonFunction.checkNull(vo.getMakerId()).trim()));
	        String date2=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
	 	    if(date2 != null)
	 	    	in.add(date2);
//	        in.add((CommonFunction.checkNull(vo.getMakerDate()).trim()));
	 	    out.add(s3);
	 	    out.add(s1);
		    out.add(s2);
		    
		    outMessages=(ArrayList) ConnectionDAO.callSP("Deferral_save",in,out);
		    s1.append(CommonFunction.checkNull(outMessages.get(1)));
		    s2.append(CommonFunction.checkNull(outMessages.get(2)));
		    s3.append(CommonFunction.checkNull(outMessages.get(0)));

	        logger.info("s1: "+s1);
	        logger.info("s2: "+s2);
	        reschId = s3.toString();
//	        if(s1.equalsIgnoreCase("S"))
//	        {
//				con.commit();
//	        }
//	        else if(s1.equalsIgnoreCase("E"))
//	        {
//	        	con.rollback();
//	        }
		}
		catch (Exception e) {
		
			e.printStackTrace();
		}finally
		{
			reqNum=null;
			s1=null;
			s2=null;
			s3=null;
		}
		

		return reschId;
	}
	public boolean updateDeferralData(DeferralMakerVo vo, String type)
	{
		logger.info("In updateDeferralData() of DeferralDAOImpl");
		PrepStmtObject insPrepStmtObject = new PrepStmtObject();
		ArrayList qryList = new ArrayList();
		boolean status = false;
		
		try {
			StringBuilder query=new StringBuilder();	
			query.append("update cr_resch_dtl set RESCH_EFF_DATE=");
			query.append(dbo);
			query.append("STR_TO_DATE(?,'"+dateFormat+"'), DEFERAL_FROM_INSTL=?," +
					" INSTL_DEFERRED=?, GAP_INTEREST=?, GAP_INTEREST_METHOD=?, RESCH_CHARGES=?, " +
					" RESCH_REASON=?, REC_STATUS=?, MAKER_ID=?, MAKER_DATE=");
				query.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			   query.append("where loan_id=? and RESCH_ID=? ");
			
			if((CommonFunction.checkNull(vo.getReschDate())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getReschDate()).trim()));
			
			if((CommonFunction.checkNull(vo.getDeferralFromInstallment())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getDeferralFromInstallment()).trim()));
			
			if((CommonFunction.checkNull(vo.getDeferredInstallmentNo())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getDeferredInstallmentNo()).trim()));
			
			insPrepStmtObject.addFloat(0.0);
			
			if((CommonFunction.checkNull(vo.getInterestChargingMethod())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getInterestChargingMethod()).trim()));
			
			if((CommonFunction.checkNull(vo.getReschCharges())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getReschCharges()).trim())).toString());
			
			if((CommonFunction.checkNull(vo.getDeferralReason())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getDeferralReason()).trim()));
			
			if((CommonFunction.checkNull(type)).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(type).trim()));
			
			if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
			
			if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
			
			if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
			
			if((CommonFunction.checkNull(vo.getReschId())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getReschId()).trim()));
			
			insPrepStmtObject.setSql(query.toString());
			logger.info("Update Query: "+insPrepStmtObject.printQuery());
			qryList.add(insPrepStmtObject);
			
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
			query=null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public String saveDeferralAuthor(PartPrePaymentAuthorVO vo)
	{
		logger.info("Inside saveDeferralAuthor.......CMDaoImpl");
		/*String status="";*/
		
		String status="";
		
//		CallableStatement cst=null;
//	    Connection con=ConnectionDAO.getConnection();
		
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		/*String s1="";
		String s2="";
		String s3="";
		String s4="";
		String s5="";
		String s6="";*/
		
		StringBuilder s1=new StringBuilder();
		StringBuilder s2=new StringBuilder();
		StringBuilder s3=new StringBuilder();
		StringBuilder s4=new StringBuilder();
		StringBuilder s5=new StringBuilder();
		StringBuilder s6=new StringBuilder();
		try
		{

//			con.setAutoCommit(false);
//	        cst=con.prepareCall("call Deferral_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?)");
			in.add(Integer.parseInt(CommonFunction.checkNull(vo.getCompanyId()).trim()));
			in.add(Integer.parseInt(CommonFunction.checkNull(vo.getReschId()).trim()));
			 String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim());
	  	    if(date != null)
	  	    	in.add(date);
//			in.add(CommonFunction.checkNull(vo.getAuthorDate()).trim());
			in.add(CommonFunction.checkNull(vo.getAuthorId()).trim());
			in.add(CommonFunction.checkNull(vo.getDecision()).trim());
			in.add(CommonFunction.checkNull(vo.getComments()).trim());
	        out.add(s1);
		    out.add(s2);
		    out.add(s3);
		    out.add(s4);
		    out.add(s5);
		    out.add(s6);
//	        cst.executeUpdate();
		    outMessages=(ArrayList) ConnectionDAO.callSP("Deferral_Author",in,out);
		    
		    s3.append(CommonFunction.checkNull(outMessages.get(0)));
		    s4.append(CommonFunction.checkNull(outMessages.get(1)));
		    s5.append(CommonFunction.checkNull(outMessages.get(2)));
		    s6.append(CommonFunction.checkNull(outMessages.get(3)));
		    s1.append(CommonFunction.checkNull(outMessages.get(4)));
		    s2.append(CommonFunction.checkNull(outMessages.get(5)));
		    
//	        String s1= cst.getString(11);
//	        String s2 = cst.getString(12);

	        logger.info("s1: "+s1);
	        logger.info("s2: "+s2);
	        if(s1.toString().equalsIgnoreCase("S"))
	        {
//				con.commit();
	        	status=s1.toString();
	        }
	        else if(s1.toString().equalsIgnoreCase("E"))
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
	
	public ArrayList<RepayScheduleVo> getNewRepayScheduleDeferral(RepayScheduleVo vo, String loanId, String reschId) 
	{
	      logger.info("In getNewRepayScheduleDeferral DAOImpl");
	      String status="";
	      CallableStatement cst=null;
	    Connection con=ConnectionDAO.getConnection();
	      ArrayList<RepayScheduleVo> list = new ArrayList<RepayScheduleVo>();
	      ArrayList mainlist = new ArrayList();
	      ArrayList subList = new ArrayList();

	      try
	      {
	        logger.info("Input Parameter of Deferral_Author: Company Id: "+vo.getCompanyId()+" reschId: "+reschId+" BDate: "+vo.getAuthorDate()+" User ID: "+vo.getAuthorId());
	        con.setAutoCommit(false);
	        StringBuilder queryForProc=new StringBuilder();
	        queryForProc.append("{call Deferral_Author(?,?,?,?,?,?,?,?,?,?,?,?)}");
	        cst=con.prepareCall(queryForProc.toString());
	       // cst=con.prepareCall("call Deferral_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?)");
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
	                  "CASE WHEN R_ADV_FLAG='Y' THEN 'YES' ELSE 'NO' END AS R_ADV_FLAG,R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" AND MAKER_ID='"+CommonFunction.checkNull(vo.getAuthorId()).trim()+"' ");
	                  logger.info("Query in getNewRepayScheduleDeferral-----" + query);
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
	      return list;
	}
	public boolean deleteDeferralData(String loanId, String reschId) 
	{
		logger.info("in deleteDeferralData()");
		ArrayList qryList=new ArrayList();
		boolean status=false;
		StringBuilder query=new StringBuilder();
		query.append("delete from cr_resch_dtl where RESCH_ID="+reschId.trim()+" and LOAN_ID="+loanId.trim());
		PrepStmtObject inPrepStmtObject = new PrepStmtObject();
		inPrepStmtObject.setSql(query.toString());
		logger.info("in deleteDeferralData()  Query  :  "+inPrepStmtObject.printQuery());
		qryList.add(inPrepStmtObject);
		try
		{
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		}
		catch (Exception e)
		{e.printStackTrace();}
		return status;
	}

	
	
	
}
