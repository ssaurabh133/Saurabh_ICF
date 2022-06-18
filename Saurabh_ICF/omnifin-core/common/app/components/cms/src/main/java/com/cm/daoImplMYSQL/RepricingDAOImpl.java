package com.cm.daoImplMYSQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.RepricingDAO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.cm.vo.PartPrePaymentAuthorVO;
import com.cm.vo.PartPrePaymentSearchVO;
import com.cm.vo.RepricingMakerVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.CodeDescVo;
import com.cp.vo.RepayScheduleVo;
import com.logger.LoggerMsg;

public class RepricingDAOImpl implements RepricingDAO 
{
	private static final Logger logger = Logger.getLogger(RepricingDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	public String repricingMakerFeasibility(String lbxLoanNoHID, String effBusinessDate)
	{
		String retStr ="";
		
		StringBuilder loanId = new StringBuilder();
		loanId.append(StringEscapeUtils.escapeSql(lbxLoanNoHID));
		
		StringBuilder query1=new StringBuilder();
		query1.append("select if (DATE_ADD(ld.loan_disbursal_date,INTERVAL s.resch_lockin_period MONTH)" +
				" >= STR_TO_DATE('"+StringEscapeUtils.escapeSql(effBusinessDate)+"','%d-%m-%Y') , 'yes', 'no') as lockin_period_valid" +
				" from cr_scheme_m s, cr_loan_dtl ld left join cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='R'" +
				" where ld.loan_scheme=s.scheme_id and ld.loan_id="+loanId+" ");
		
		logger.info("In repricingMakerFeasibility() query is (query1)::- "+query1.toString());
		
		String lockinPeriodValid = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
		
		StringBuilder query2=new StringBuilder();
		 query2.append( "select if(s.number_resch_allowed_total> (select count(loan_id) from cr_resch_dtl where" +
				        " loan_id="+loanId+" and resch_type='R' and rec_status='A'), 'yes', 'no') as" +
				        " total_repricing_allowed_loan from cr_scheme_m s, cr_loan_dtl ld" +
				        " left join cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='R'" +
				        " where ld.loan_scheme=s.scheme_id" +
			     	    " and ld.loan_id="+loanId+" "
			     	  );
		 
		 logger.info("In repricingMakerFeasibility() query is (query2)::- "+query2.toString());
		 
		String totalRepricingAllowed = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
		
		StringBuilder query3=new StringBuilder();
		query3.append( "select case  when (select ifnull(max(RESCH_DATE),0) from cr_resch_dtl where" +
				" loan_id="+loanId+" and RESCH_TYPE='R' and rec_status='A')=0 then 'yes'" +
				" when (select ifnull(max(RESCH_DATE),0) from cr_resch_dtl where loan_id="+loanId+"" +
				" and RESCH_TYPE='R' and rec_status='A')<> 0 then if(DATE_ADD((select max(RESCH_DATE) from cr_resch_dtl" +
				" where loan_id="+loanId+" and RESCH_TYPE='R' and rec_status='A'),INTERVAL s.MINIMUM_GAP_RESCH MONTH) <=" +
				" STR_TO_DATE('"+StringEscapeUtils.escapeSql(effBusinessDate)+"','%d-%m-%Y'), 'yes', 'no')" +
//				" (select max(RESCH_EFF_DATE) from cr_resch_dtl where loan_id="+loanId+" and RESCH_TYPE='R' and rec_status='A'), 'yes', 'no')" +
				" end as min_gap_succ_repricing_valid from cr_scheme_m s, cr_loan_dtl ld" +
				" left join cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='R'" +
				" where ld.loan_scheme=s.scheme_id and ld.loan_id="+loanId+" ");
		
		logger.info("In repricingMakerFeasibility() query is (query3)::- "+query3.toString());
		
		String minGapSuccRepricingValid = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
		
		StringBuilder query4=new StringBuilder();
		query4.append("select extract(year from str_to_date('"+StringEscapeUtils.escapeSql(effBusinessDate)+"','"+dateFormat+"'))");
		
		logger.info("In repricingMakerFeasibility() query is (query4)::- "+query4.toString());
		
		String businessYearStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query4.toString()));
		
		int businessYear = 0;
		if(!businessYearStr.equalsIgnoreCase(""))
			businessYear = Integer.parseInt(businessYearStr);
		
		StringBuilder query5=new StringBuilder();
		
	 query5.append("select extract(year from resch_eff_date) from cr_resch_dtl where resch_type='R' and rec_status='A' and loan_id="+loanId);
	 logger.info("In repricingMakerFeasibility() query is (query5)::- "+query5.toString());
		
		int count=0;
		try
		{
			ArrayList yearList = ConnectionDAO.sqlSelect(query5.toString());
			for(int i=0;i<yearList.size();i++){
				logger.info("yearList: "+yearList.get(i).toString());
		        ArrayList data=(ArrayList)yearList.get(i);
		        Iterator itr = data.iterator();
		        while(itr.hasNext())
		        {
		        	if(itr.next().toString().equalsIgnoreCase(businessYearStr))
		        		count++;
		        }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
//		retStr=null;
		
		query1 =  null;
		query2 =  null;
		query4=null;
		query3=null;
		query5=null;
		
		}
			
		
		logger.info("Count Of Years: "+count);
		

		String query6="select s.NUMBER_RESCH_ALLOWED_YEAR from cr_scheme_m s, cr_loan_dtl ld" +
				" left join cr_resch_dtl r on ld.loan_id=r.loan_id and r.resch_type='R' and r.REC_STATUS='A' where" +
				" ld.loan_scheme=s.scheme_id and ld.loan_id="+loanId+" ";
		
		logger.info("In repricingMakerFeasibility() query is (query6)::- "+query6.toString());
		
		String numRepricingAllowedYrStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query6));
		
		
		int numRepricingAllowedYr = 0;
		if(!numRepricingAllowedYrStr.equalsIgnoreCase(""))
			numRepricingAllowedYr = Integer.parseInt(numRepricingAllowedYrStr);
		
		if(numRepricingAllowedYr<=count && numRepricingAllowedYr>0)
		{
			retStr=("repricingAllowedYear");
			logger.info("Total Repricing Allowed in a single year Over");
		}
		
		if(minGapSuccRepricingValid.equalsIgnoreCase("no"))
		{
			retStr=("minGapSuccRepricingValid");
			logger.info("Minimum Gap between Successive Repricing Valid");
		}
		
		if(totalRepricingAllowed.equalsIgnoreCase("no"))
		{
			retStr="totalRepricingAllowedExceeds";
			logger.info("totalRepricingAllowedExceeds");
		}
		
		if(lockinPeriodValid.equalsIgnoreCase("yes"))
		{
			retStr="lockinPeriod";
			logger.info("Lockin period Valid");
		}
		
	query6=null;
	loanId=null;
		return retStr;
	}

	
	
	
	public ArrayList<RepricingMakerVo> retriveRepricingValues(String lbxLoanNoHID)
	{
		logger.info("In retriveRepricingValues.....DAOImpl");
		ArrayList<RepricingMakerVo> rePricingList = new ArrayList();
		
		RepricingMakerVo vo = null;
		
		StringBuilder queryCheck=new StringBuilder();
		
	 queryCheck.append("Select count(resch_id) from cr_resch_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'" +
		" and rec_status in ('P','F') and resch_type='R'");
	 
	 StringBuilder tempCount=new StringBuilder();
		//String tempCount = ConnectionDAO.singleReturn(queryCheck.toString());
	 
	 tempCount.append(ConnectionDAO.singleReturn(queryCheck.toString()));
	 
		int count = Integer.parseInt(tempCount.toString());
		 StringBuilder query=new StringBuilder();
		if(count==0)
		{
			try{
		
				  ArrayList mainlist=new ArrayList();
				  ArrayList subList=new ArrayList();
				  
				 
				  query.append("select a.loan_id,a.loan_no, b.customer_name, c.product_desc,  d.scheme_desc, " +
				  	 " (select ifnull(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+lbxLoanNoHID+"' and rec_status='A')," +
				  	 " a.loan_balance_principal, " +
				  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and RESCH_TYPE='R' and rec_status='A')," +
				  	 " DATE_FORMAT((select max(RESCH_EFF_DATE) from cr_resch_dtl where loan_id='"+lbxLoanNoHID+"' and RESCH_TYPE='R' and rec_status='A'),'"+dateFormat+"')," +
				  	 " a.loan_rate_method, a.LOAN_RATE_TYPE, a.LOAN_BASE_RATE_TYPE, a.LOAN_BASE_RATE, a.LOAN_MARKUP, a.loan_final_rate, a.LOAN_EFF_RATE, a.LOAN_REPAYMENT_FREQ,"+
				  	 " a.LOAN_INSTALLMENT_TYPE, a.LOAN_EMI_AMOUNT, a.LOAN_TENURE, a.LOAN_NO_OF_INSTALLMENT, " +
				  	 " DATE_FORMAT((select min(instl_date) from cr_repaysch_dtl where loan_id='"+lbxLoanNoHID+"' and instl_date>(select parameter_value from parameter_mst where parameter_key='BUSINESS_DATE')),'"+dateFormat+"')," +
				  	 " date_format(a.LOAN_MATURITY_DATE,'"+dateFormat+"') , a.LOAN_FLAT_RATE,date_format((select parameter_value from parameter_mst where parameter_key='BUSINESS_DATE'),'"+dateFormat+"')"+
					 " from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d" +
					 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					 " and d.scheme_id = a.loan_scheme and a.loan_id='"+lbxLoanNoHID+"'");
		
				 logger.info("In retriveRepricingValues : "+query.toString());
				 
				 mainlist=ConnectionDAO.sqlSelect(query.toString());
				 logger.info("In retriveRepricingValues.....mainlist size: "+mainlist.size());
				if(mainlist.size()!=0)
				{
					
				 for(int i=0;i<mainlist.size();i++){
		
					subList=(ArrayList)mainlist.get(i);
					logger.info("In retriveRepricingValues......sublist size: "+subList.size());
					if(subList.size()>0){
						vo = new RepricingMakerVo();
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
						
						vo.setRePricingSinceDsibursal((CommonFunction.checkNull(subList.get(7)).trim()));
						vo.setLastRePricingDate((CommonFunction.checkNull(subList.get(8)).trim()));
						
						if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase("F"))
							vo.setInterestRateMethod("Fixed");
						if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase("L"))
							vo.setInterestRateMethod("Floating");
						vo.setInterestRateMethodHid((CommonFunction.checkNull(subList.get(9)).trim()));
						
						if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("E"))
							vo.setInterestRateType("Effective");
						if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("F"))
							vo.setInterestRateType("Flat");
						vo.setInterestRateTypeHid((CommonFunction.checkNull(subList.get(10)).trim()));
						
						vo.setBaseRateType((CommonFunction.checkNull(subList.get(11)).trim()));
						
						if((CommonFunction.checkNull(subList.get(12))).trim().equalsIgnoreCase(""))
							vo.setBaseRate("0");
						else
						{
							Number baseRate = myFormatter.parse((CommonFunction.checkNull(subList.get(12))).trim());
							vo.setBaseRate(myFormatter.format(baseRate));
						}
						
						if((CommonFunction.checkNull(subList.get(13))).trim().equalsIgnoreCase(""))
							vo.setMarkup("0");
						else
						{
							Number markup = myFormatter.parse((CommonFunction.checkNull(subList.get(13))).trim());
							vo.setMarkup(myFormatter.format(markup));
						}
						
						if((CommonFunction.checkNull(subList.get(14))).trim().equalsIgnoreCase(""))
							vo.setFinalRate("0");
						else
						{
							Number finalRate = myFormatter.parse((CommonFunction.checkNull(subList.get(14))).trim());
							vo.setFinalRate(myFormatter.format(finalRate));
						}
						
						if((CommonFunction.checkNull(subList.get(15))).trim().equalsIgnoreCase(""))
							vo.setEffectiveRate("0");
						else
						{
							Number effRate = myFormatter.parse((CommonFunction.checkNull(subList.get(15))).trim());
							vo.setEffectiveRate(myFormatter.format(effRate));
						}
						
						if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("M"))
							vo.setInstallmentFrequencyOld("Monthly");
						if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("B"))
							vo.setInstallmentFrequencyOld("Bimonthly");
						if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("Q"))
							vo.setInstallmentFrequencyOld("Quarterly");
						if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("H"))
							vo.setInstallmentFrequencyOld("Half Yearly");
						if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("Y"))
							vo.setInstallmentFrequencyOld("Yearly");
						vo.setInstallmentFrequency((CommonFunction.checkNull(subList.get(16))));
						vo.setInstallmentFrequencyOldHid((CommonFunction.checkNull(subList.get(16))));
						
						if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("E"))
							vo.setInstallmentType("Eq. Installment");
						if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("G"))
							vo.setInstallmentType("Gr. Installment");
						if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("P"))
							vo.setInstallmentType("Eq. Principal");
						if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("Q"))
							vo.setInstallmentType("Gr. Principal1");
						if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase("S"))
							vo.setInstallmentType("SEPARATE PRINCIPAL & INTEREST");
						vo.setInstallmentTypeHid((CommonFunction.checkNull(subList.get(17)).trim()));
						
						if((CommonFunction.checkNull(subList.get(18))).trim().equalsIgnoreCase(""))
							vo.setEmi("0");
						else
						{
							Number emi = myFormatter.parse((CommonFunction.checkNull(subList.get(18))).trim());
							vo.setEmi(myFormatter.format(emi));
						}
						
						vo.setTenure((CommonFunction.checkNull(subList.get(19)).trim()));
						vo.setNoOfInstl((CommonFunction.checkNull(subList.get(20)).trim()));
						vo.setNextDueDate((CommonFunction.checkNull(subList.get(21)).trim()));
						vo.setMaturityDate((CommonFunction.checkNull(subList.get(22)).trim()));
						
						if((CommonFunction.checkNull(subList.get(23))).trim().equalsIgnoreCase(""))
							vo.setFlatRate("0");
						else
						{
							Number flatRate = myFormatter.parse((CommonFunction.checkNull(subList.get(23))).trim());
							vo.setFlatRate(myFormatter.format(flatRate));
						}
						
						rePricingList.add(vo);
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
			logger.info("Size of rePricingList: "+rePricingList.size());
		return rePricingList;
	}

	public ArrayList<PartPrePaymentSearchVO> searchRepricing(PartPrePaymentSearchVO vo,String type)
	{
		logger.info("In searchRepricing() method of RepricingDAOImpl");
		/*String loanId="";
		String customerName="";
		String userName="";*/
		
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
			logger.info("In searchRepricing().....................................Dao Impl");
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
					 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='R' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
	        bufInsSqlTempCount.append("select count(1)"+
	     			 " from cr_loan_dtl a,gcd_customer_m b," +
	     			 " cr_product_m c, cr_scheme_m d, cr_resch_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
	     			 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.resch_type='R' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
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
				if((!(vo.getLbxUserId().equalsIgnoreCase("")))&& vo.getStage().equalsIgnoreCase("F") ) {
					 bufInsSql.append(" E.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
	    	         bufInsSqlTempCount.append(" E.MAKER_ID ='"+vo.getLbxUserId()+"' AND");
	  	    	  appendSQL=true;
	  	      }
				}
				
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){					
					String tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
	            logger.info("In searchRepricing() ## tmp ## "+tmp);
	            logger.info("In searchRepricing() ## tmp1 ## "+tmp1);
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
	          logger.info("searchRepricing: "+searchlist.size());
	          for(int i=0;i<searchlist.size();i++){
	          logger.info("searchRepricing: "+searchlist.get(i).toString());
	          ArrayList data=(ArrayList)searchlist.get(i);
	          if(data.size()>0){
	        	  PartPrePaymentSearchVO vo1= new PartPrePaymentSearchVO();
	        	  if(type.equalsIgnoreCase("P"))
	        	  {
	        		  vo1.setLoanNo("<a href=rePricingSearch.do?method=showRepricingDataMaker&loanId="
	  					+ (CommonFunction.checkNull(data.get(6)).trim())
	  					+ "&reschId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
	  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
	        	  }
	        	  if(type.equalsIgnoreCase("F"))
	        	  {
	        		  vo1.setLoanNo("<a href=rePricingSearch.do?method=showRepricingDataAuthor&loanId="
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
	
    public ArrayList<RepricingMakerVo> selectRericingData(String loanId,String reschId, String recStatus)
  {
	logger.info("In selectRericingData() method of RepricingDAOImpl");
	ArrayList<RepricingMakerVo> rePricingData = new ArrayList<RepricingMakerVo>();
	RepricingMakerVo vo = null;
	 StringBuilder query=new StringBuilder();
	try{
		  ArrayList mainlist=new ArrayList();
		  ArrayList subList=new ArrayList();
		  
		 
		   query.append("select a.loan_id,a.loan_no, b.customer_name, c.product_desc, d.scheme_desc," +
		  	 " (select ifnull(sum(disbursal_amount),0) from cr_loan_disbursal_dtl where loan_id='"+loanId+"' and rec_status='A'),"+
		  	 " a.loan_balance_principal,"+
		  	 " (select count(loan_id) from cr_resch_dtl where loan_id='"+loanId+"' and RESCH_TYPE='R' and rec_status='A')," +
		  	 " DATE_FORMAT((select max(RESCH_EFF_DATE) from cr_resch_dtl where loan_id='"+loanId+"' and RESCH_TYPE='R' and rec_status='A'),'"+dateFormat+"')," +
		  	 " e.deferal_from_instl,DATE_FORMAT(e.RESCH_EFF_DATE,'"+dateFormat+"'),e.PRE_PAYMENT_PARAMETER,e.REQUEST_NO,"+
		  	 " e.OLD_INT_RATE_METHOD,e.NEW_INT_RATE_METHOD,e.OLD_INT_RATE_TYPE,e.NEW_INT_RATE_TYPE," +
		  	 " e.OLD_BASE_RATE_TYPE,e.NEW_BASE_RATE_TYPE,e.OLD_BASE_RATE,e.NEW_BASE_RATE," +
		  	 " e.OLD_MARKUP,e.NEW_MARKUP,e.OLD_RATE,e.NEW_RATE,"+
		  	 " e.OLD_EFF_RATE,e.OLD_FREQUENCY,e.NEW_FREQUENCY,e.OLD_INSTALLMENT_TYPE,e.NEW_INSTALLMENT_TYPE,"+
		  	 " a.LOAN_EMI_AMOUNT,e.OLD_TENURE,e.NEW_TENURE,e.OLD_INSTL_NO,e.NEW_INSTL_NO," +
		  	 " date_format(e.OLD_DUE_DATE,'"+dateFormat+"')," +
		  	 " date_format(e.OLD_MATURITY_DATE,'"+dateFormat+"'),"+
		  	 " date_format(e.NEW_MATURITY_DATE,'"+dateFormat+"'),"+
		  	 " e.GAP_INTEREST,e.GAP_INTEREST_METHOD,e.RESCH_CHARGES,e.RESCH_REASON,e.remarks,a.LOAN_FLAT_RATE,DATE_FORMAT(e.RESCH_DATE,'"+dateFormat+"'),"+
//		  	 "IFNULL(DATE_FORMAT((select max(instl_date) from cr_repaysch_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' and bill_flag='Y' ),'"+dateFormat+"'),A.LOAN_REPAY_EFF_DATE)"
			 " IFNULL(DATE_FORMAT((select max(instl_date) from cr_repaysch_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' and bill_flag='Y' ),'"+dateFormat+"'),A.LOAN_REPAY_EFF_DATE) from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, cr_resch_dtl e" +
			 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
			 " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+""+
			 " and e.resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId).trim())+" ");
			 logger.info("In selectRericingData : "+query.toString());
			 
		 mainlist=ConnectionDAO.sqlSelect(query.toString());
		 
		 query=null;
		 
		 logger.info("In selectRericingData.....mainlist size: "+mainlist.size());
		 if(mainlist.size()!=0)
		 {
			 for(int i=0;i<mainlist.size();i++)
			 {
				subList=(ArrayList)mainlist.get(i);
				if(subList.size()>0)
				{
					vo = new RepricingMakerVo();
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
						vo.setRePricingSinceDsibursal("0");
					else
						vo.setRePricingSinceDsibursal((CommonFunction.checkNull(subList.get(7)).trim()));
					
					vo.setLastRePricingDate((CommonFunction.checkNull(subList.get(8)).trim()));
					vo.setRePricingFromInstallment((CommonFunction.checkNull(subList.get(9)).trim()));
					vo.setReschDate((CommonFunction.checkNull(subList.get(10)).trim()));
					vo.setRePricingCondition((CommonFunction.checkNull(subList.get(11)).trim()));
					vo.setReqRefNo((CommonFunction.checkNull(subList.get(12)).trim()));
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("F"))
						vo.setInterestRateMethodOld("Fixed");
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase("L"))
						vo.setInterestRateMethodOld("Floating");
					vo.setInterestRateMethodOldHid((CommonFunction.checkNull(subList.get(13)).trim()));
					
					if((CommonFunction.checkNull(subList.get(14)).trim()).equalsIgnoreCase("F"))
						vo.setInterestRateMethod("Fixed");
					if((CommonFunction.checkNull(subList.get(14)).trim()).equalsIgnoreCase("L"))
						vo.setInterestRateMethod("Floating");
					vo.setInterestRateMethodHid((CommonFunction.checkNull(subList.get(14)).trim()));
					
					if((CommonFunction.checkNull(subList.get(15)).trim()).equalsIgnoreCase("E"))
						vo.setInterestRateTypeOld("Effective");
					if((CommonFunction.checkNull(subList.get(15)).trim()).equalsIgnoreCase("F"))
						vo.setInterestRateTypeOld("Flat");
					vo.setInterestRateTypeOldHid((CommonFunction.checkNull(subList.get(15)).trim()));
					
					if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("E"))
						vo.setInterestRateType("Effective");
					if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase("F"))
						vo.setInterestRateType("Flat");
					vo.setInterestRateTypeHid((CommonFunction.checkNull(subList.get(16)).trim()));
					
					vo.setBaseRateTypeOld((CommonFunction.checkNull(subList.get(17)).trim()));
					vo.setBaseRateType((CommonFunction.checkNull(subList.get(18)).trim()));
					
					if((CommonFunction.checkNull(subList.get(19))).trim().equalsIgnoreCase(""))
						vo.setBaseRateOld("0");
					else
					{
						Number baseRateOld = myFormatter.parse((CommonFunction.checkNull(subList.get(19))).trim());
						vo.setBaseRateOld(myFormatter.format(baseRateOld));
					}
					
					if((CommonFunction.checkNull(subList.get(20))).trim().equalsIgnoreCase(""))
						vo.setBaseRate("0");
					else
					{
						Number baseRate = myFormatter.parse((CommonFunction.checkNull(subList.get(20))).trim());
						vo.setBaseRate(myFormatter.format(baseRate));
					}
					
					if((CommonFunction.checkNull(subList.get(21))).trim().equalsIgnoreCase(""))
						vo.setMarkupOld("0");
					else
					{
						Number markupold = myFormatter.parse((CommonFunction.checkNull(subList.get(21))).trim());
						vo.setMarkupOld(myFormatter.format(markupold));
					}
					
					if((CommonFunction.checkNull(subList.get(22))).trim().equalsIgnoreCase(""))
						vo.setMarkup("0");
					else
					{
						Number markup = myFormatter.parse((CommonFunction.checkNull(subList.get(22))).trim());
						vo.setMarkup(myFormatter.format(markup));
					}
					
					if((CommonFunction.checkNull(subList.get(23))).trim().equalsIgnoreCase(""))
						vo.setFinalRateOld("0");
					else
					{
						Number finalRateOld = myFormatter.parse((CommonFunction.checkNull(subList.get(23))).trim());
						vo.setFinalRateOld(myFormatter.format(finalRateOld));
					}
					
					if((CommonFunction.checkNull(subList.get(24))).trim().equalsIgnoreCase(""))
						vo.setFinalRate("0");
					else
					{
						Number finalRate = myFormatter.parse((CommonFunction.checkNull(subList.get(24))).trim());
						vo.setFinalRate(myFormatter.format(finalRate));
					}
					
					if((CommonFunction.checkNull(subList.get(25))).trim().equalsIgnoreCase(""))
						vo.setEffectiveRate("0");
					else
					{
						Number effRate = myFormatter.parse((CommonFunction.checkNull(subList.get(25))).trim());
						vo.setEffectiveRate(myFormatter.format(effRate));
					}					
					if((CommonFunction.checkNull(subList.get(26)).trim()).equalsIgnoreCase("M"))
						vo.setInstallmentFrequencyOld("Monthly");
					if((CommonFunction.checkNull(subList.get(26)).trim()).equalsIgnoreCase("B"))
						vo.setInstallmentFrequencyOld("Bimonthly");
					if((CommonFunction.checkNull(subList.get(26)).trim()).equalsIgnoreCase("Q"))
						vo.setInstallmentFrequencyOld("Quarterly");
					if((CommonFunction.checkNull(subList.get(26)).trim()).equalsIgnoreCase("H"))
						vo.setInstallmentFrequencyOld("Half Yearly");
					if((CommonFunction.checkNull(subList.get(26)).trim()).equalsIgnoreCase("Y"))
						vo.setInstallmentFrequencyOld("Yearly");
					vo.setInstallmentFrequencyOldHid((CommonFunction.checkNull(subList.get(26))));					
					if((CommonFunction.checkNull(subList.get(27)).trim()).equalsIgnoreCase("M"))
						vo.setInstallmentFrequency("Monthly");
					if((CommonFunction.checkNull(subList.get(27)).trim()).equalsIgnoreCase("B"))
						vo.setInstallmentFrequency("Bimonthly");
					if((CommonFunction.checkNull(subList.get(27)).trim()).equalsIgnoreCase("Q"))
						vo.setInstallmentFrequency("Quarterly");
					if((CommonFunction.checkNull(subList.get(27)).trim()).equalsIgnoreCase("H"))
						vo.setInstallmentFrequency("Half Yearly");
					if((CommonFunction.checkNull(subList.get(27)).trim()).equalsIgnoreCase("Y"))
						vo.setInstallmentFrequency("Yearly");
					vo.setInstallmentFrequencyHid((CommonFunction.checkNull(subList.get(27))));
					
					if((CommonFunction.checkNull(subList.get(28)).trim()).equalsIgnoreCase("E"))
						vo.setInstallmentTypeOld("Eq. Installment");
					if((CommonFunction.checkNull(subList.get(28)).trim()).equalsIgnoreCase("G"))
						vo.setInstallmentTypeOld("Gr. Installment");
					if((CommonFunction.checkNull(subList.get(28)).trim()).equalsIgnoreCase("P"))
						vo.setInstallmentTypeOld("Eq. Principal");
					if((CommonFunction.checkNull(subList.get(28)).trim()).equalsIgnoreCase("Q"))
						vo.setInstallmentTypeOld("Gr. Principal1");
					if((CommonFunction.checkNull(subList.get(28)).trim()).equalsIgnoreCase("S"))
						vo.setInstallmentTypeOld("SEPARATE PRINCIPAL & INTEREST");
					vo.setInstallmentTypeOldHid((CommonFunction.checkNull(subList.get(28)).trim()));
					
					if((CommonFunction.checkNull(subList.get(29)).trim()).equalsIgnoreCase("E"))
						vo.setInstallmentType("Eq. Installment");
					if((CommonFunction.checkNull(subList.get(29)).trim()).equalsIgnoreCase("G"))
						vo.setInstallmentType("Gr. Installment");
					if((CommonFunction.checkNull(subList.get(29)).trim()).equalsIgnoreCase("P"))
						vo.setInstallmentType("Eq. Principal");
					if((CommonFunction.checkNull(subList.get(29)).trim()).equalsIgnoreCase("Q"))
						vo.setInstallmentType("Gr. Principal1");
					if((CommonFunction.checkNull(subList.get(29)).trim()).equalsIgnoreCase("S"))
						vo.setInstallmentType("SEPARATE PRINCIPAL & INTEREST");
					vo.setInstallmentTypeHid((CommonFunction.checkNull(subList.get(29)).trim()));
					
					if((CommonFunction.checkNull(subList.get(30))).trim().equalsIgnoreCase(""))
						vo.setEmi("0");
					else
					{
						Number emi = myFormatter.parse((CommonFunction.checkNull(subList.get(30))).trim());
						vo.setEmi(myFormatter.format(emi));
					}
					
					vo.setTenureOld((CommonFunction.checkNull(subList.get(31)).trim()));
					vo.setTenure((CommonFunction.checkNull(subList.get(32)).trim()));
					vo.setNoOfInstlOld((CommonFunction.checkNull(subList.get(33)).trim()));
					vo.setNoOfInstl((CommonFunction.checkNull(subList.get(34)).trim()));
					vo.setNextDueDate((CommonFunction.checkNull(subList.get(35)).trim()));
					vo.setMaturityDateOld((CommonFunction.checkNull(subList.get(36)).trim()));
					vo.setMaturityDate((CommonFunction.checkNull(subList.get(37)).trim()));
					
					if((CommonFunction.checkNull(subList.get(38))).trim().equalsIgnoreCase(""))
						vo.setInterestForGapPeriod("0");
					else
					{
						Number interestForGapPeriod = myFormatter.parse((CommonFunction.checkNull(subList.get(38))).trim());
						vo.setInterestForGapPeriod(myFormatter.format(interestForGapPeriod));
					}
					
					vo.setInterestChargingMethod((CommonFunction.checkNull(subList.get(39)).trim()));
					
					if((CommonFunction.checkNull(subList.get(40))).trim().equalsIgnoreCase(""))
						vo.setReschCharges("0");
					else
					{
						Number partPaymentCharges = myFormatter.parse((CommonFunction.checkNull(subList.get(40))).trim());
						vo.setReschCharges(myFormatter.format(partPaymentCharges));
					}
					
					vo.setRePricingReason((CommonFunction.checkNull(subList.get(41)).trim()));
					vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(42)).trim()));
					
					if((CommonFunction.checkNull(subList.get(43))).trim().equalsIgnoreCase(""))
						vo.setFlatRate("0");
					else
					{
						Number flatRate = myFormatter.parse((CommonFunction.checkNull(subList.get(43))).trim());
						vo.setFlatRate(myFormatter.format(flatRate));
					}
					vo.setRepricingdate((CommonFunction.checkNull(subList.get(44)).trim()));
					vo.setMaxInstallmentDate((CommonFunction.checkNull(subList.get(45)).trim()));
					
					rePricingData.add(vo);
				}
			  }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
//		finally
//		{
//		
//		}

		return rePricingData;
	}
  
public ArrayList<CodeDescVo> getBaseRateList(String businessdate) 
{
	logger.info("In getBaseRateList() method of RepricingDAOImpl");
	ArrayList<CodeDescVo> list=new ArrayList<CodeDescVo>();
	
	try
	{
		StringBuilder query=new StringBuilder();
		 query.append("SELECT A.BASE_RATE_TYPE FROM cr_base_rate_m A WHERE A.REC_STATUS='A' and A.EFFECTIVE_FROM =" +
			" (SELECT MAX(B.EFFECTIVE_FROM) FROM cr_base_rate_m B WHERE B.REC_STATUS='A' and " +
			"A.BASE_RATE_TYPE= B.BASE_RATE_TYPE AND B.EFFECTIVE_FROM <= STR_TO_DATE('"+businessdate+"','"+dateFormat+"'))");
		logger.info("in getBaseRateList  query : "+query.toString());
		
		CodeDescVo branchVo=null;
		ArrayList baseRate = ConnectionDAO.sqlSelect(query.toString());
		logger.info("query : "+query);
		query=null;
		for(int i=0;i<baseRate.size();i++)
		{
			logger.info("getProductTypeList"+baseRate.get(i).toString());
			ArrayList baseRate1=(ArrayList)baseRate.get(i);
			if(baseRate1.size()>0)
			{
			    branchVo = new CodeDescVo();
			    
				branchVo.setId((CommonFunction.checkNull(baseRate1.get(0))).trim());
				LoggerMsg.info("id.................................. "+(CommonFunction.checkNull(baseRate1.get(0))).trim());
				list.add(branchVo);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}

	return list;
}

public ArrayList<RepayScheduleVo> getRepayScheduleDisbursal(String loanId) 
{
	logger.info("In getRepayScheduleDisbursal() method of RepricingDAOImpl");	
	ArrayList<RepayScheduleVo> list = new ArrayList<RepayScheduleVo>();
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder query= new StringBuilder();
	RepayScheduleVo repvo = null;
	try {

		query.append("select INSTL_NO,DATE_FORMAT(INSTL_DATE,'"+dateFormat+"'),INSTL_AMOUNT,PRIN_COMP,INT_COMP,EXCESS_INT," +
				" if(ADV_FLAG='Y','YES','NO'),PRIN_OS from cr_repaysch_dtl where LOAN_ID="+loanId);
		
		logger.info("Query in getRepaySched-----" + query);
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		query= null;
		int size = mainlist.size();
		
		for (int i = 0; i <size; i++) {
			subList = (ArrayList) mainlist.get(i);
			repvo = new RepayScheduleVo();
			if (subList.size() > 0) {

				repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
				repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
				
				if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
    	    		repvo.setInstAmount(myFormatter.format(reconNum));
	    		}
				
				//repvo.setInstAmount((CommonFunction.checkNull(subList.get(2)).trim()));
				if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
    	    		repvo.setPrinciple(myFormatter.format(reconNum));
	    		}
				//repvo.setPrinciple((CommonFunction.checkNull(subList.get(3)).trim()));
				if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
    	    		repvo.setInstCom(myFormatter.format(reconNum));
	    		}
				//repvo.setInstCom((CommonFunction.checkNull(subList.get(4)).trim()));
				if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
    	    		repvo.setExcess(myFormatter.format(reconNum));
	    		}
				//repvo.setExcess((CommonFunction.checkNull(subList.get(5)).trim()));
				repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
				if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
    	    		repvo.setPrinOS(myFormatter.format(reconNum));
	    		}
				//repvo.setPrinOS((CommonFunction.checkNull(subList.get(7)).trim()));
				
			}
			list.add(repvo);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
			
	}
	return list;
}

public String saveRepricingData(RepricingMakerVo vo)
{
	logger.info("In saveRepricingData() method of RepricingDAOImpl");	
	/*String reschId="";
    String reqNum="";*/
	String reschId ="";
	StringBuilder reqNum =new StringBuilder();
	
    ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	/*String s1="";
	String s2="";
	String s3="";*/
	StringBuilder s1 =new StringBuilder();
	StringBuilder s2 =new StringBuilder();
	StringBuilder s3 =new StringBuilder();
	
	try
	{

		if(CommonFunction.checkNull(vo.getBaseRateOld()).equalsIgnoreCase(""))
		{
			vo.setBaseRateOld("0");
		}
		if(CommonFunction.checkNull(vo.getBaseRate()).equalsIgnoreCase(""))
		{
			vo.setBaseRate("0");
		}
		if(CommonFunction.checkNull(vo.getMarkupOld()).equalsIgnoreCase(""))
		{
			vo.setMarkupOld("0");
		}
		if(CommonFunction.checkNull(vo.getMarkup()).equalsIgnoreCase(""))
		{
			vo.setMarkup("0");
		}
		if(CommonFunction.checkNull(vo.getFinalRateOld()).equalsIgnoreCase(""))
		{
			vo.setFinalRateOld("0");
		}
		if(CommonFunction.checkNull(vo.getFinalRate()).equalsIgnoreCase(""))
		{
			vo.setFinalRate("0");
		}
		if(CommonFunction.checkNull(vo.getEffectiveRate()).equalsIgnoreCase(""))
		{
			vo.setEffectiveRate("0");
		}
		if(CommonFunction.checkNull(vo.getEffectiveRate()).equalsIgnoreCase(""))
		{
			vo.setEffectiveRate("0");
		}
//		con.setAutoCommit(false);
//		cst=con.prepareCall("call Repricing_Save(?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),STR_TO_DATE(?,'"+dateFormat+"'),STR_TO_DATE(?,'"+dateFormat+"'),STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?)");
		in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));//1
        in.add("R");//2

        if((CommonFunction.checkNull(vo.getReqRefNo()).trim()).equalsIgnoreCase(""))
        	reqNum.append("0");
        else
        	reqNum.append((CommonFunction.checkNull(vo.getReqRefNo()).trim()));

        in.add(reqNum);//3
        String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getRepricingDate()).trim());
        if(date.trim().equalsIgnoreCase(""))
	    	date="0000-00-00";
	    	in.add(date);//4
//        cst.setString(4, CommonFunction.checkNull(vo.getMakerDate()).trim());
	    String date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getReschDate()).trim());
	    if(date1 != null)
	    	in.add(date1);//5
	    in.add(CommonFunction.checkNull(vo.getInterestRateMethodOld()).trim());//6
	    in.add(CommonFunction.checkNull(vo.getInterestRateMethod()).trim());//7
	    in.add(CommonFunction.checkNull(vo.getInterestRateTypeOld()).trim());//8
	    in.add(CommonFunction.checkNull(vo.getInterestRateType()).trim());//9
	    in.add(CommonFunction.checkNull(vo.getBaseRateTypeOld()).trim());//10
	    in.add(CommonFunction.checkNull(vo.getBaseRateType()).trim());//11
	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getBaseRateOld()).trim()).doubleValue());//12
	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getBaseRate()).trim()).doubleValue());//13
	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getMarkupOld()).trim()).doubleValue());//14
	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getMarkup()).trim()).doubleValue());//15
	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getFinalRateOld()).trim()).doubleValue());//16
	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getFinalRate()).trim()).doubleValue());//17
	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getEffectiveRate()).trim()).doubleValue());//18
	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getEffectiveRate()).trim()).doubleValue());//19
	    in.add(CommonFunction.checkNull(vo.getInstallmentFrequencyOld()).trim());//20
	    if((CommonFunction.checkNull(vo.getRePricingCondition()).trim()).equalsIgnoreCase("T"))
	    {
	    	 in.add(CommonFunction.checkNull(vo.getInstallmentFrequencyOld()).trim());
	    }
	    else 
	    {
	    in.add(CommonFunction.checkNull(vo.getInstallmentFrequency()).trim());//21
	    }
	    in.add(CommonFunction.checkNull(vo.getInstallmentTypeOld()).trim());//22
	    if((CommonFunction.checkNull(vo.getRePricingCondition()).trim()).equalsIgnoreCase("T"))
	    {
	    	 in.add(CommonFunction.checkNull(vo.getInstallmentTypeOld()).trim());
	    }
	    else 
	    {
	    in.add(CommonFunction.checkNull(vo.getInstallmentType()).trim());//23
	    }
	    in.add(Integer.parseInt(CommonFunction.checkNull(vo.getTenureOld()).trim()));//24
	    in.add(Integer.parseInt(CommonFunction.checkNull(vo.getTenure()).trim()));//25
	    in.add(Integer.parseInt(CommonFunction.checkNull(vo.getNoOfInstlOld()).trim()));//26
	    in.add(Integer.parseInt(CommonFunction.checkNull(vo.getNoOfInstl()).trim()));//27
	    
	   String date2=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getNextDueDate()).trim());
	   if(date2.trim().equalsIgnoreCase(""))
	    	date2="0000-00-00";
	    	in.add(date2);//28
	    String date3=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getNextDueDate()).trim());
	    if(date3.trim().equalsIgnoreCase(""))
	    	date3="0000-00-00";
	    	in.add(date3);//29
	    String date6=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMaturityDateOld()).trim());
	    if(date6.trim().equalsIgnoreCase(""))
	    	date6="0000-00-00";
	    	in.add(date6);//30
//	    in.add(CommonFunction.checkNull(vo.getMaturityDateOld()).trim());
	    String date4=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMaturityDate()).trim());
	    if(date4.trim().equalsIgnoreCase(""))
	    	date4="0000-00-00";
	    	in.add(date4);//31
	    in.add(Integer.parseInt(CommonFunction.checkNull(vo.getRePricingFromInstallment()).trim()));//32
	    in.add(CommonFunction.checkNull(vo.getRePricingCondition()).trim());//33
//       in.add( myFormatter.parse(CommonFunction.checkNull(vo.getInterestForGapPeriod()).trim())).doubleValue());

	    in.add(0.0);//34
	    in.add((CommonFunction.checkNull(vo.getInterestChargingMethod()).trim()));//35
	    in.add(myFormatter.parse(CommonFunction.checkNull(vo.getReschCharges()).trim()).doubleValue());//36
	    in.add((CommonFunction.checkNull(vo.getRePricingReason()).trim()));//37
	    in.add("P");//38
	    in.add((CommonFunction.checkNull(vo.getMakerId()).trim()));//39
	    String date5=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
	    if(date5.trim().equalsIgnoreCase(""))
	    	date5="0000-00-00";
	    	in.add(date5);//40
	    out.add(s3);
	    out.add(s1);
	    out.add(s2);
	    
	    outMessages=(ArrayList) ConnectionDAO.callSP("Repricing_Save",in,out);
	    s1.append(CommonFunction.checkNull(outMessages.get(1)));
	    s2.append(CommonFunction.checkNull(outMessages.get(2)));
	    s3.append(CommonFunction.checkNull(outMessages.get(0)));

        logger.info("s1: "+s1);
        logger.info("s2: "+s2);
      //  reschId=( s3.toString());
        if((s1.toString()).equalsIgnoreCase("S"))
        {
        	reschId = CommonFunction.checkNull(s3);
//			con.commit();
        }
        else if((s1.toString()).equalsIgnoreCase("E"))
        {
        	reschId = s2.toString();
//        	con.rollback();
        }
//        if(s1.equalsIgnoreCase("S"))
//        {
//			con.commit();
//        }
//        else if(s1.equalsIgnoreCase("E"))
//        {
//        	con.rollback();
//        }
	}
	catch (Exception e) {
//		try {
//			con.rollback();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		e.printStackTrace();
	}
//	finally
//	{
//		try {
//			con.close();
//			cst.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}	
//	}
	
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
	logger.info("In getInstallType() method of RepricingDAOImpl");	
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
		query=null;
		
		
		
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
		
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			
		}
		return list;
	
	}
public boolean updateRepricingData(RepricingMakerVo vo, String type)
{
	logger.info("In updateRepricingData() method of RepricingDAOImpl");	  	
	PrepStmtObject insPrepStmtObject = new PrepStmtObject();
	ArrayList qryList = new ArrayList();
	boolean status = false;
	boolean status2 = true;
	StringBuilder query=new StringBuilder();
	try {
		
		query.append("update cr_resch_dtl set RESCH_EFF_DATE=STR_TO_DATE(?,'"+dateFormat+"'), DEFERAL_FROM_INSTL=?," +
				" PRE_PAYMENT_PARAMETER=?, NEW_INT_RATE_METHOD=?, NEW_INT_RATE_TYPE=?, NEW_BASE_RATE_TYPE=?," +
				" NEW_BASE_RATE=?, NEW_MARKUP=?, NEW_RATE=?, NEW_FREQUENCY=?, NEW_INSTALLMENT_TYPE=?, NEW_TENURE=?," +
				" NEW_INSTL_NO=?, NEW_MATURITY_DATE=STR_TO_DATE(?,'"+dateFormat+"'), GAP_INTEREST=?," +
				" GAP_INTEREST_METHOD=?, RESCH_CHARGES=?, RESCH_REASON=?, REC_STATUS=?, MAKER_ID=?," +
				" MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),RESCH_DATE=STR_TO_DATE(?,'"+dateFormat+"') where loan_id=? and RESCH_ID=? ");
		
		if((CommonFunction.checkNull(vo.getReschDate())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getReschDate()).trim()));
		
		if((CommonFunction.checkNull(vo.getRePricingFromInstallment())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getRePricingFromInstallment()).trim()));
		
		if((CommonFunction.checkNull(vo.getRePricingCondition())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getRePricingCondition()).trim()));
		
		if((CommonFunction.checkNull(vo.getInterestRateMethod())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getInterestRateMethod()).trim()));
		
		if((CommonFunction.checkNull(vo.getInterestRateType())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getInterestRateType()).trim()));
		
		if((CommonFunction.checkNull(vo.getBaseRateType())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getBaseRateType()).trim()));
		
		if((CommonFunction.checkNull(vo.getBaseRate())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getBaseRate()).trim())).toString());
		
		if((CommonFunction.checkNull(vo.getMarkup())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getMarkup()).trim())).toString());
		
		if((CommonFunction.checkNull(vo.getFinalRate())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getFinalRate()).trim())).toString());
		//start by raj 
		if((CommonFunction.checkNull(vo.getRePricingCondition()).trim()).equalsIgnoreCase("T"))
		{
			if((CommonFunction.checkNull(vo.getInstallmentFrequencyOld())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstallmentFrequencyOld()).trim()));
		}
		else{
		//end by raj 
		if((CommonFunction.checkNull(vo.getInstallmentFrequency())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstallmentFrequency()).trim()));
		}
		//start by raj 
		if((CommonFunction.checkNull(vo.getRePricingCondition()).trim()).equalsIgnoreCase("T"))
		{
			if((CommonFunction.checkNull(vo.getInstallmentTypeOld())).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstallmentTypeOld()).trim()));
			
		}
		else {
		//end by raj 
		if((CommonFunction.checkNull(vo.getInstallmentType())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstallmentType()).trim()));
		}
		
		if((CommonFunction.checkNull(vo.getTenure())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getTenure()).trim()));
		
		if((CommonFunction.checkNull(vo.getNoOfInstl())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getNoOfInstl()).trim()));
		
		if((CommonFunction.checkNull(vo.getMaturityDate())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getMaturityDate()).trim()));
		
//		if((CommonFunction.checkNull(vo.getInterestForGapPeriod())).trim().equalsIgnoreCase(""))
//			insPrepStmtObject.addNull();
//		else
//			insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getInterestForGapPeriod()).trim())).toString());
		insPrepStmtObject.addFloat(0.0);
		
		if((CommonFunction.checkNull(vo.getInterestChargingMethod())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getInterestChargingMethod()).trim()));
		
		if((CommonFunction.checkNull(vo.getReschCharges())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getReschCharges()).trim())).toString());
		
		if((CommonFunction.checkNull(vo.getRePricingReason())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getRePricingReason()).trim()));
		
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
		
		if((CommonFunction.checkNull(vo.getRepricingDate())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getRepricingDate()).trim()));
		
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
		
		if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRePricingCondition())).trim().equalsIgnoreCase("T"))
		{
			StringBuilder chkQuery=new StringBuilder();
			 chkQuery.append("select count(INSTALLMENT_PLAN_ID) from cr_resch_installment_plan where " +
			" resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReschId()))+" and" +
			" loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"");
			
		//	String chkStatusStr = ConnectionDAO.singleReturn(chkQuery.toString());
			
			//int chkStatus = Integer.parseInt(chkStatusStr);
			 
			int chkStatus = Integer.parseInt(ConnectionDAO.singleReturn(chkQuery.toString()));
			logger.info("chkStatus: "+chkStatus);
			if(chkStatus>0)
			{
				StringBuilder query2=new StringBuilder();
				query2.append("delete from cr_resch_installment_plan where " +
				" resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReschId()))+" and" +
				" loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"");
				
				PrepStmtObject inPrepStmtObject = new PrepStmtObject();
				inPrepStmtObject.setSql(query2.toString());
				
				logger.info("Update query2: "+inPrepStmtObject.printQuery());
				qryList.add(inPrepStmtObject);
				status2 = true;
				query2 = null;
			}
			chkQuery=null;
		}
	
		if(type.equalsIgnoreCase("F") && StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRePricingCondition())).trim().equalsIgnoreCase("I"))
		{	
			StringBuilder chkQuery=new StringBuilder();
		    chkQuery.append("select count(INSTALLMENT_PLAN_ID) from cr_resch_installment_plan where " +
			" resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReschId()))+" and" +
			" loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"");
			
			
			//String chkStatusStr = ConnectionDAO.singleReturn(chkQuery.toString());
			//int chkStatus = Integer.parseInt(chkStatusStr);
			int chkStatus = Integer.parseInt(ConnectionDAO.singleReturn(chkQuery.toString()));
			
			if(chkStatus>0)
			{
				StringBuilder query2=new StringBuilder();
				 query2.append("update cr_resch_installment_plan set rec_status='F' where " +
					" resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReschId()))+" and" +
					" loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))+"");
				PrepStmtObject inPrepStmtObject = new PrepStmtObject();
				inPrepStmtObject.setSql(query2.toString());
				logger.info("Update query2: "+inPrepStmtObject.printQuery());
				qryList.add(inPrepStmtObject);
				status2 = true;
				query2 = null;
			}
			else
			{	status2 = false;}
			chkQuery=null;
			
		}
		if(status2)
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		query=null;
		
	}
	return status;
}
public String saveRepricingAuthor(PartPrePaymentAuthorVO vo)
{
	logger.info("Inside saveRepricingAuthor.......CMDaoImpl");
	String status="";
	
//	CallableStatement cst=null;
//    Connection con=ConnectionDAO.getConnection();
    ArrayList qryList = new ArrayList();
    /*String s1="";
	String s2="";
	String s3="";	 
    String s4="";
	String s5="";
	String s6="";	*/ 
    
    StringBuilder s1=new StringBuilder();
    StringBuilder s2=new StringBuilder();
    StringBuilder s3=new StringBuilder();
    StringBuilder s4=new StringBuilder();
    StringBuilder s5=new StringBuilder();
    StringBuilder s6=new StringBuilder();
    
    ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	try
	{
//		con.setAutoCommit(false);
//        cst=con.prepareCall("call Resch_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?)");
		in.add(Integer.parseInt((CommonFunction.checkNull(vo.getCompanyId()).trim())));
		in.add(Integer.parseInt((CommonFunction.checkNull(vo.getReschId()).trim())));
		 String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim());
		    if(date != null)
		    	in.add(date);
//		in.add((CommonFunction.checkNull(vo.getAuthorDate()).trim()));
		in.add((CommonFunction.checkNull(vo.getAuthorId()).trim()));
		in.add((CommonFunction.checkNull(vo.getDecision()).trim()));
		in.add((CommonFunction.checkNull(vo.getComments()).trim()));
//        cst.registerOutParameter(7, Types.CHAR );
//        cst.registerOutParameter(8, Types.CHAR );
//        cst.registerOutParameter(9, Types.CHAR );
//        cst.registerOutParameter(10, Types.CHAR );
//        cst.registerOutParameter(11, Types.CHAR );
//        cst.registerOutParameter(12, Types.CHAR );
        out.add(s1);
	    out.add(s2);
	    out.add(s3);
	    out.add(s4);
	    out.add(s5);
	    out.add(s6);
//        cst.executeUpdate();
	    outMessages=(ArrayList) ConnectionDAO.callSP("Resch_Author",in,out);
	    s3.append(CommonFunction.checkNull(outMessages.get(0)));
	    s4.append(CommonFunction.checkNull(outMessages.get(1)));
	    s5.append(CommonFunction.checkNull(outMessages.get(2)));
	    s6.append(CommonFunction.checkNull(outMessages.get(3)));
	    s1.append(CommonFunction.checkNull(outMessages.get(4)));
	    s2.append(CommonFunction.checkNull(outMessages.get(5)));
//        String s1= cst.getString(11);
//        String s2 = cst.getString(12);
        logger.info("s1: "+s1);
        logger.info("s2: "+s2);
        if(s1.toString().equalsIgnoreCase("S"))
        {
//			con.commit();
        	status=s1.toString();
        }
        else if(s1.toString().equalsIgnoreCase("E"))
        {
//        	con.rollback();
        	status = s2.toString();
        }
	}
	catch (Exception e) {
		try {
//			con.rollback();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
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

public boolean checkOldNewTenureAreEqual(String loanId,String reschId,String reschType){
	boolean oldNewTenureEqlFlag=false;
	StringBuilder query = new StringBuilder();
	ArrayList list=new ArrayList();
	ArrayList sublist=new ArrayList();
//	String oldTenure=null;
//	String newTenure=null;
	
	String oldTenure=null;
	String newTenure=null;
	
 try{
		query.append("Select D.TO_INSTL_NO,R.NEW_INSTL_NO from cr_resch_dtl R ,cr_resch_installment_plan D");
		query.append(" where  R.LOAN_ID=D.LOAN_ID and R.LOAN_ID="+CommonFunction.checkNull(loanId)+" and  R.resch_id="+CommonFunction.checkNull(reschId)+" and R.RESCH_TYPE='"+reschType+"'");
		logger.info("checkOldNewTenureAreEqual query:-"+query);
		list=ConnectionDAO.sqlSelect(query.toString());
		int size=list.size();
		if(size>0){
			sublist=(ArrayList)list.get(0);
			oldTenure=CommonFunction.checkNull(sublist.get(0));
			newTenure=CommonFunction.checkNull(sublist.get(1));
			if(oldTenure.equalsIgnoreCase(newTenure)){
				oldNewTenureEqlFlag=true;	
			}
			
		}
		
	
		
}catch (Exception e) {
	e.printStackTrace();
}
finally
{
	list = null;
	sublist=null;
	query = null;
	oldTenure=null;
	newTenure=null;
}
return oldNewTenureEqlFlag;	
}

public ArrayList<RepayScheduleVo> getNewRepayScheduleRepricing(RepayScheduleVo vo, String loanId, String reschId) 
{
      logger.info("In getNewRepayScheduleRepricing DAOImpl");
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
                        logger.info("Query in getRepaySchFieldsDetail--DEAL_IRR2---" + bussIrrQ);
                        String bussIrr = ConnectionDAO.singleReturn(bussIrrQ);
                        
                        String query="select R_Seq_No,DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
                        " if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" order by R_DUE_DATE,R_ORG_SEQ_NO";
                        logger.info("Query in getNewRepayScheduleRepricing-----" + query);
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




public boolean deleteRericingData(String reschId, String loanId) 
{
	logger.info("in deleteRericingData()");
	ArrayList qryList=new ArrayList();
	boolean status=false;
	StringBuilder query1=new StringBuilder();
	StringBuilder query2=new StringBuilder();
	query1.append("delete from cr_resch_dtl where RESCH_ID="+reschId.trim()+" and LOAN_ID="+loanId.trim());
	query2.append("delete from cr_resch_installment_plan where RESCH_ID="+reschId.trim()+" and LOAN_ID="+loanId.trim());
	PrepStmtObject inPrepStmtObject1 = new PrepStmtObject();
	PrepStmtObject inPrepStmtObject2 = new PrepStmtObject();
	inPrepStmtObject1.setSql(query1.toString());
	inPrepStmtObject2.setSql(query2.toString());
	logger.info("in deleteRericingData()  Query  :  "+inPrepStmtObject1.printQuery());
	logger.info("in deleteRericingData()  Query  :  "+inPrepStmtObject2.printQuery());
	qryList.add(inPrepStmtObject1);
	qryList.add(inPrepStmtObject2);
	try
	{
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	}
	catch (Exception e)
	{e.printStackTrace();}
	return status;
}


}
