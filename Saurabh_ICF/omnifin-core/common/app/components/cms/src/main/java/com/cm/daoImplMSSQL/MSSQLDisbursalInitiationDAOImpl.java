package com.cm.daoImplMSSQL;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.DisbursalInitiationDAO;
import com.cm.vo.*;	
import com.cm.vo.DisbursalMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.OtherChargesPlanVo;
import com.cp.vo.RepayScheduleVo;

import java.text.SimpleDateFormat;
import java.sql.SQLException;

public class MSSQLDisbursalInitiationDAOImpl implements DisbursalInitiationDAO {
	
	private static final Logger logger = Logger.getLogger(MSSQLDisbursalInitiationDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");


	public ArrayList<DisbursalMakerVO> getCycleDateList(){
		ArrayList list=new ArrayList();
		String query = "";
		DisbursalMakerVO vo=null;
		ArrayList product = null;
		ArrayList data= null;
		try{
			logger.info("In getCycleDateList..........................DAOImpl");
			query = "select gm.value, gm.description from generic_master gm,generic_master_keys gmk where gm.generic_key='CYCLE_DATE'and gmk.generic_key='CYCLE_DATE' and rec_status ='A'";
			
			product = ConnectionDAO.sqlSelect(query);
			int size = product.size();
			logger.info("getCycleDateList "+size);
			for(int i=0;i<size;i++){
				logger.info("getNoteCode "+CommonFunction.checkNull(product.get(i)).toString());
				data=(ArrayList)product.get(i);
				if(data.size()>0){
					vo = new DisbursalMakerVO();
					vo.setCycleDateValue((CommonFunction.checkNull(data.get(0))).trim());
					vo.setCycleDateDesc((CommonFunction.checkNull(data.get(1))).trim());
					list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query =null;
				vo=null;
				product = null;
				data= null;
			}
		return list;
	}
	
	public ArrayList<DisbursalSearchVO> searchDisbursalData(DisbursalSearchVO vo, String type,HttpServletRequest request) 
	{
		String loanId="";
		String loanNo="";
		String customerName="";
		String loanAmtStr="";
		String loanApprovalDate="";
		String product="";
		String productId="";
		String scheme="";
		String schemeId="";
		String userId="";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist=new ArrayList();
		StringBuilder bufInsSql =	new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		ArrayList data=new ArrayList();          	  
      	DisbursalSearchVO vo1= null;
      	String userName="";
      	
      	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
      	try{
      	String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
      	userName=ConnectionDAO.singleReturn(userNameQ);
      	logger.info("userNameQ: "+userNameQ+" userName: "+userName);
      	}catch(Exception e){
			e.printStackTrace();
		}
        
		ArrayList<DisbursalSearchVO> detailList=new ArrayList();
	       try{
	              logger.info("In searchDisbursalData().....................................Dao Impl");
	              
	              loanId= StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim());
	              loanNo=  StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanNo()).trim());
	              customerName=  StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim());
	              
	              if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanAmt()).trim()).equalsIgnoreCase(""))
	              {
	            	  loanAmtStr="";
	              }
	              else
	              {	
	            	  loanAmtStr=  myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanAmt()).trim())).toString();
	              }
	              loanApprovalDate=  StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanApprovalDate()).trim());
	              product=  StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getProduct()).trim());
	              productId=  StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim());
	              scheme=  StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getScheme()).trim());
	              schemeId =  StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim());
	              boolean appendSQL=false;
                  //code added by neeraj
	              String forwarded=request.getParameter("forwarded");
	              request.setAttribute("forwardedLoanId","");
	              if(CommonFunction.checkNull(forwarded).trim().equalsIgnoreCase("Y"))
	              {
	            	  request.setAttribute("forwardedLoanId",loanId);
	            	  loanId="";
	            	  customerName="";
	            	  loanAmtStr="";
	            	  loanApprovalDate="";
	            	  productId="";
	            	  schemeId="";
	            	  
	              }
	              //neeraj space end
	              /*
	               * 
					  bufInsSql.append("select distinct a.loan_no, e.disbursal_no, b.customer_name, a.loan_loan_amount, DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),c.product_desc, d.scheme_desc,");
					  bufInsSql.append("a.loan_id,e.LOAN_DISBURSAL_ID,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=e.MAKER_ID) MAKER_ID");
					  if(type.equalsIgnoreCase("F")){
					  bufInsSql.append(" ,DISBURSAL_BATCH_ID ");
					   }
					  bufInsSql.append(" from cr_loan_dtl a,gcd_customer_m b," );
					  bufInsSql.append(" cr_product_m c, cr_scheme_m d, "+table+" e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product");
					  bufInsSql.append(" and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
					  */
		              String table="";
		              if(type.equalsIgnoreCase("P")){
		            	  table="cr_loan_disbursal_dtl_temp";
		              }else{
		            	  table="cr_loan_disbursal_dtl";
		              }
		            //Nishant Space starts
					   String branch="";
					   if(vo.getAllBranches().equalsIgnoreCase("on"))
					   {
						   String branchMappedToUserQuery="SELECT STUFF((SELECT ',' + CAST(BRANCH_ID as varchar(10)) FROM sec_user_branch_dtl WHERE USER_ID='"+CommonFunction.checkNull(vo.getReportingToUserId()).trim()+"' AND REC_STATUS='A' FOR XML PATH ('')), 1, 1, '')";
						   branch=ConnectionDAO.singleReturn(branchMappedToUserQuery);
						   logger.info("branchMappedToUserQuery: "+branchMappedToUserQuery+"      branches maped to user:   "+branch);
					   }
					   else if(!(vo.getLbxBranchId().equalsIgnoreCase("")))
					   {
						   branch=vo.getLbxBranchId();
					   }
					   else
					   {
						   branch=vo.getBranchId();
					   }
					//Nishant Space End
					  bufInsSql.append("select distinct a.loan_no, e.disbursal_no, b.customer_name, a.loan_loan_amount, ");
					  bufInsSql.append(dbo);
					  bufInsSql.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"') as loan_approval_date,c.product_desc, d.scheme_desc,"+
							  			"a.loan_id,e.LOAN_DISBURSAL_ID,(SELECT USER_NAME ");
					  bufInsSql.append("FROM SEC_USER_M WHERE USER_ID=e.MAKER_ID) MAKER_ID ");
					  if(type.equalsIgnoreCase("F")){
						  bufInsSql.append(" ,DISBURSAL_BATCH_ID ");
						   }
					  bufInsSql.append("from cr_loan_dtl a,gcd_customer_m b," +
							  			" cr_product_m c, cr_scheme_m d, "+table+"  e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
							  			" and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and a.REC_STATUS='A' and e.loan_id=a.loan_id AND A.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")  ");
					  
					  bufInsSqlTempCount.append("select distinct COUNT(1) FROM  cr_loan_dtl a,gcd_customer_m b," +
					  			" cr_product_m c, cr_scheme_m d, "+table+"  e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
					  			" and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and a.REC_STATUS='A' and e.loan_id=a.loan_id AND A.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")  ");

					  if(vo.getStage()!=null && !vo.getStage().equalsIgnoreCase("F"))
						{
						  bufInsSql.append(" AND e.MAKER_ID='"+vo.getReportingToUserId()+"'");
						  bufInsSqlTempCount.append(" AND e.MAKER_ID='"+vo.getReportingToUserId()+"'");
						}
					  else if(vo.getStage()!=null && vo.getStage().equalsIgnoreCase("F"))
						{
						  bufInsSql.append(" AND e.MAKER_ID!='"+vo.getUserId()+"'");
						  bufInsSqlTempCount.append(" AND e.MAKER_ID!='"+vo.getUserId()+"'");
						}
	            
					if((!(loanId.equalsIgnoreCase("")))&&(!(customerName.equalsIgnoreCase("")))&&(!(loanAmtStr.equalsIgnoreCase("")))&&(!(loanApprovalDate.equalsIgnoreCase("")))&&(!(productId.equalsIgnoreCase("")))&&(!(schemeId.equalsIgnoreCase(""))))
					{
	    	    	  bufInsSql.append(" AND a.Loan_Id in (select loan_id from "+table+" where loan_id='"+loanId+"' and rec_status='"+type+"') AND b.customer_name like'%"+customerName+"%' AND a.loan_loan_amount='"+loanAmtStr+"' AND ");
	    	    	  bufInsSql.append(dbo);
	    	    	  bufInsSql.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"')='"+loanApprovalDate+"' AND c.product_id='"+productId+"' AND d.scheme_id ='"+schemeId+"'");
	    	    	  bufInsSqlTempCount.append(" AND a.Loan_Id in (select loan_id from "+table+" where loan_id='"+loanId+"' and rec_status='"+type+"') AND b.customer_name like'%"+customerName+"%' AND a.loan_loan_amount='"+loanAmtStr+"' AND  ");
	    	    	  bufInsSqlTempCount.append(dbo);
	    	    	  bufInsSqlTempCount.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"')='"+loanApprovalDate+"' AND c.product_id='"+productId+"' AND d.scheme_id ='"+schemeId+"'");
					}
					if(!(loanId.equalsIgnoreCase("")) || !(customerName.equalsIgnoreCase("")) || !(loanAmtStr.equalsIgnoreCase("")) || !(loanApprovalDate.equalsIgnoreCase("")) || !(productId.equalsIgnoreCase(""))|| !(schemeId.equalsIgnoreCase(""))|| !(vo.getLbxUserId().equalsIgnoreCase(""))){
						appendSQL=true;
					}
					
					if(appendSQL){
						bufInsSql.append(" AND ");
						bufInsSqlTempCount.append(" AND ");
	              }

	        	 if((!(loanId.equalsIgnoreCase("")))) {
	    	         bufInsSql.append(" a.Loan_Id in (select loan_id from "+table+" where loan_id='"+loanId+"' and rec_status='"+type+"') AND ");
	    	         bufInsSqlTempCount.append(" a.Loan_Id in (select loan_id from "+table+" where loan_id='"+loanId+"' and rec_status='"+type+"') AND ");
	    	         appendSQL=true;
	    	    	  
	    	      }
				if((!(customerName.equalsIgnoreCase("")))) {					
	    	    	  bufInsSql.append(" b.customer_name like'%"+customerName+"%' AND ");
	    	    	  bufInsSqlTempCount.append(" b.customer_name like'%"+customerName+"%' AND ");
	    	    	  appendSQL=true;
	    	      }
				if((!(loanAmtStr.equalsIgnoreCase("")))) {					
	    	    	  bufInsSql.append(" a.loan_loan_amount='"+loanAmtStr+"' AND ");
	    	    	  bufInsSqlTempCount.append(" a.loan_loan_amount='"+loanAmtStr+"' AND ");
	    	    	  appendSQL=true;
	    	      }
				if((!(loanApprovalDate.equalsIgnoreCase("")))) {					
					  bufInsSql.append(dbo);
	    	    	  bufInsSql.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"')='"+loanApprovalDate+"' AND ");
	    	    	  bufInsSqlTempCount.append(dbo);
	    	    	  bufInsSqlTempCount.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"')='"+loanApprovalDate+"' AND ");
	    	    	  appendSQL=true;
	    	      }
				if((!(product.equalsIgnoreCase("")))) {					
	    	    	  bufInsSql.append(" c.product_id='"+productId+"' AND ");
	    	    	  bufInsSqlTempCount.append(" c.product_id='"+productId+"' AND ");
	    	    	  appendSQL=true;
	    	      }
				if((!(scheme.equalsIgnoreCase("")))) {
					  bufInsSql.append(" d.scheme_id ='"+schemeId+"' AND ");
					  bufInsSqlTempCount.append(" d.scheme_id ='"+schemeId+"' AND ");
		    	      appendSQL=true;
		    	  }
				
				if((!(vo.getLbxUserId().equalsIgnoreCase("")))&& vo.getStage().equalsIgnoreCase("F")) {
					bufInsSql.append("  e.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"' ");	
					bufInsSqlTempCount.append("  e.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"'");
					appendSQL = true;
		    	      
		    	  }
				
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){					
					String  tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
                logger.info("In searchDisbursalData() ## tmp ## "+tmp);
                logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-4));
                if(tmp.lastIndexOf("AND") == (tmp.length()-4) || tmp1.lastIndexOf("AND") == (tmp1.length()-4))
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
                 
				}else {
			    	  
					logger.info("search Query...else-------." + bufInsSql);
					logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
		            
					count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
					
					
					//if((loanId.trim()==null && customerName.trim()==null && loanAmtStr.trim()==null && loanApprovalDate.trim()==null && productId.trim()==null && schemeId.trim()==null) || (loanId.trim().equalsIgnoreCase("") && customerName.trim().equalsIgnoreCase("") && loanAmtStr.trim().equalsIgnoreCase("") && loanApprovalDate.trim().equalsIgnoreCase("") && productId.trim().equalsIgnoreCase("") && schemeId.trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
					//{
					
						logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
					if(vo.getCurrentPageLink()>1)
					{
						startRecordIndex = (vo.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						logger.info("startRecordIndex .................... "+startRecordIndex);
						logger.info("endRecordIndex .................... "+endRecordIndex);
					}
					/*else{
						//bufInsSql.append(" GROUP BY a.loan_no ");
						//bufInsSqlTempCount.append(" GROUP BY a.loan_no ");
					}*/
					bufInsSql.append(" ORDER BY a.loan_no OFFSET ");
		            bufInsSql.append(startRecordIndex);
		            bufInsSql.append(" ROWS FETCH next ");
		            bufInsSql.append(endRecordIndex);
		            bufInsSql.append(" ROWS ONLY ");
					//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
					//}
					logger.info("query : "+bufInsSql.toString());
					
					searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				}

				int size = searchlist.size();
              logger.info("searchDisbursalData: "+size);
              for(int i=0;i<size;i++){
              logger.info("searchDisbursalDataList: "+searchlist.get(i).toString());
              data=(ArrayList)searchlist.get(i);
              if(data.size()>0){
            	  
            	  vo1= new DisbursalSearchVO();
            	  if(type.equalsIgnoreCase("P"))
            	  {
            	  vo1.setModifyNo("<a href=disbursalSearch.do?method=openDisbursalValues&loanId="
      					+ (CommonFunction.checkNull(data.get(7)).trim())+ "&disbursalNo=" 
      					+ (CommonFunction.checkNull(data.get(1)).trim())
      					+ "&loanDisbursalId="+(CommonFunction.checkNull(data.get(8)).trim())+">"
      					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
            	  }
            	  if(type.equalsIgnoreCase("F"))
            	  {
            	   	  vo1.setModifyNo("<a href=disbursalSearch.do?method=openDisbursalValuesAuthor&loanId="
        					+ (CommonFunction.checkNull(data.get(7)).trim())+ "&disbursalNo=" 
        					+ (CommonFunction.checkNull(data.get(1)).trim())
        					+ "&loanDisbursalId="+(CommonFunction.checkNull(data.get(8)).trim())+"&batchId="+(CommonFunction.checkNull(data.get(10)).trim())+">"
        					+(CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
              	  }
            	  vo1.setLoanNo((CommonFunction.checkNull(data.get(0)).trim()));
            	  vo1.setDisbursalNo((CommonFunction.checkNull(data.get(1)).trim()));
            	  vo1.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
            	  Number loanAmt = myFormatter.parse((CommonFunction.checkNull(data.get(3)).trim()));
            	  vo1.setLoanAmt(myFormatter.format(loanAmt));
            	  vo1.setLoanApprovalDate((CommonFunction.checkNull(data.get(4)).trim()));
            	  vo1.setProduct((CommonFunction.checkNull(data.get(5)).trim()));
            	  vo1.setScheme((CommonFunction.checkNull(data.get(6)).trim()));
            	  vo1.setLbxLoanNoHID((CommonFunction.checkNull(data.get(7)).trim()));
            	  vo1.setLoanDisbursalId((CommonFunction.checkNull(data.get(8)).trim()));
            	  vo1.setReportingToUserId((CommonFunction.checkNull(data.get(9)).trim()));
            	  vo1.setTotalRecordSize(count);
            	  detailList.add(vo1);
            }

		}

		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			loanId= null;
			loanNo= null;
			customerName= null;
			loanAmtStr= null;
			loanApprovalDate= null;
			product= null;
			productId= null;
			scheme= null;
			schemeId= null;
			searchlist= null;
			bufInsSql = null;
			bufInsSqlTempCount = null;
			data= null;  	  
          	vo1= null;
		}
		return detailList;
}
	
	
	public String saveDisbursalAuthor(DisbursalAuthorVO vo) 
	{
		logger.info("In saveDisbursalAuthor ........ DAOImpl");
		String status="";
		logger.info("In saveDisbursalAuthor ........Decision: "+vo.getDecision());
		String query1 = "select disbursal_flag from cr_loan_disbursal_dtl where loan_id='"+vo.getLoanId()+"'" +
				" and disbursal_no='"+vo.getDisbursalNo()+"'";
		logger.info("In saveDisbursalAuthor...........select query: "+query1);
		String flag = ConnectionDAO.singleReturn(query1);
		logger.info("Disbursal Flag from select query= "+flag);
		
		logger.info("In saveDisbursalAuthor ........ DAOImpl "+vo.getLoanDisbursalId());
		logger.info("vo.getCompanyId(): "+vo.getCompanyId()+"vo.getLoanId(): "+vo.getLoanId());
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		try {
			in.add(Integer.parseInt(CommonFunction.checkNull(vo.getCompanyId()).trim()));
			in.add(Integer.parseInt(CommonFunction.checkNull (vo.getLoanId()).trim()));
			in.add(Integer.parseInt(CommonFunction.checkNull (vo.getDisbursalBatchId()).trim()));
			String date=CommonFunction.changeFormat(CommonFunction.checkNull (vo.getAuthorDate()).trim());
			if(date != null)
				in.add(date);
			in.add(CommonFunction.checkNull (vo.getAuthorId()).trim());
			//in.add(CommonFunction.checkNull (flag).trim());
			in.add(CommonFunction.checkNull (vo.getDecision()).trim());
			in.add(CommonFunction.checkNull (vo.getComments()).trim());
					
			out.add(s1);
        	out.add(s2);

        	logger.info("In Disbursal_Authorization Procedure  ");
        	logger.info("Input :   "+in.toString());
        	logger.info("Disbursal_Authorization Procedure  "+in+""+out);
			outMessages=(ArrayList) ConnectionDAO.callSP("Disbursal_Authorization",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
        	s2=CommonFunction.checkNull(outMessages.get(1));
        	    
			if(s1!=null && s1.equalsIgnoreCase("S"))
			{
				status=s1;	
			}
			else if(s1!=null && s1.equalsIgnoreCase("E"))
			{
				status=s2;	
			}
			logger.info("status: "+s1);
			logger.info("s2: "+s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query1 = null;
			flag=null;
		}
		return status;
	}

	
	public ArrayList<DisbursalMakerVO> selectDisbursalData(String loanId, String disbursalNo,String businessDate,String bp_type) {
		logger.info("In selectDisbursalData.....DAOImpl");
		ArrayList<DisbursalMakerVO> disbursalData = new ArrayList<DisbursalMakerVO>();
		DisbursalMakerVO vo = null;
		ArrayList mainlist=new ArrayList();
		ArrayList subList=new ArrayList();
		StringBuilder query1= new StringBuilder();
		String proposedShortPayAmtStr= "";
		StringBuilder query2= new StringBuilder();
		String disbursedAmountStr="";
		StringBuilder query3= new StringBuilder();
		String shortAmountPaidStr= "";
		StringBuilder query = new StringBuilder();
		StringBuilder query5= new StringBuilder();
		StringBuilder query7= new StringBuilder();
		String maxDisbursalDate="";
		StringBuilder query6= new StringBuilder();
		String query8="";
		//logger.info("In selectDisbursalData Loan Id : "+loanId);
		try{
			  
			  query1.append("select SUM(ISNULL(ADVICE_AMOUNT,0) - ISNULL(TXN_ADJUSTED_AMOUNT,0) - ISNULL(AMOUNT_IN_PROCESS,0))" +
				" FROM CR_TXNADVICE_DTL WHERE LOAN_ID = '"+loanId+"' AND BP_TYPE ='"+bp_type+"' AND ADVICE_TYPE = 'R' AND REC_STATUS = 'A'AND ADVICE_DATE<=" );
			  query1.append(dbo);
			  query1.append("STR_TO_DATE('"+businessDate+"', '"+dateFormat+"') ");
			  logger.info("In selectDisbursalData : "+query1);
			  proposedShortPayAmtStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
			  logger.info("In selectDisbursalData.....proposedShortPayAmt: "+proposedShortPayAmtStr);
		
			  query2.append("select SUM(ISNULL(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+loanId+"' AND REC_STATUS='A'");
			  //logger.info("In getDisbursalValues : "+query2);
			  disbursedAmountStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
			  logger.info("In selectDisbursalData.....disbursedAmount: "+disbursedAmountStr);

			  query3.append("select SUM(ISNULL(SHORT_AMOUNT_ADJUSTED,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+loanId+"' AND REC_STATUS='A'");
			  //logger.info("In getDisbursalValues : "+query3);
			  shortAmountPaidStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
			  logger.info("In selectDisbursalData.....shortAmountPaid: "+shortAmountPaidStr);

				String maxExpectedPayDate="";
				query6.append("select ");
				query6.append(dbo);
				query6.append("DATE_FORMAT(max(PAYMENT_DATE),'"+dateFormat+"') from cr_loan_disbursal_dtl where loan_id='"+loanId+"' and REC_STATUS='A'");
				logger.info("In selectDisbursalData : "+query6);
				
				maxExpectedPayDate=ConnectionDAO.singleReturn(query6.toString());
				logger.info("In selectDisbursalData maxExpectedPayDate : "+maxExpectedPayDate);
				
				//Added by Arun to make disbursal date editable starts here
				StringBuilder queryMaxDisb=new StringBuilder();
				queryMaxDisb.append("select ");
				queryMaxDisb.append(dbo);
				queryMaxDisb.append("DATE_FORMAT(max(DISBURSAL_DATE) ,'"+dateFormat+"')from cr_loan_disbursal_dtl where LOAN_ID='"+loanId+"' and REC_STATUS='A'");
				logger.info("query8:----------"+queryMaxDisb.toString());
				maxDisbursalDate=CommonFunction.checkNull(ConnectionDAO.singleReturn(queryMaxDisb.toString()));
				logger.info("In selectDisbursalData maxDisbursalDate : "+maxDisbursalDate);
				//Added by Arun to make disbursal date editable ends here
			  
			   query.append("select a.loan_no, b.customer_name, a.loan_loan_amount, ");
			  query.append(dbo);
			  query.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), c.product_desc, d.scheme_desc,a.loan_id, e.disbursal_description, ");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.disbursal_date,'"+dateFormat+"'),e.short_amount_adjusted, e.disbursal_amount, e.disbursal_no, e.disbursal_flag,");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.REPAY_EFF_DATE,'"+dateFormat+"'),e.remarks,e.loan_due_day,a.loan_repayment_type," +
				 " (select count(PDC_LOAN_ID) from cr_pdc_instrument_dtl where  PDC_STATUS not in('X','R') and PDC_PURPOSE='INSTALLMENT' and PDC_LOAN_ID='"+loanId+"'),");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.NEXT_DUE_DATE ,'%d-%m-%Y'),e.CUR_MONTH_EMI,e.NEXT_MONTH_EMI,e.LOAN_DISBURSAL_ID,e.DISBURSAL_TO_ID,e.DISBURSAL_TO, ");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.PAYMENT_DATE,'"+dateFormat+"'),e.TA_LOAN_ID, " );
			  query.append(dbo);
			  query.append(	"DATE_FORMAT(e.PENAL_INT_CALC_DATE ,'%d-%m-%Y')" +
				 " from cr_loan_dtl a,gcd_customer_m b," +
				 " cr_product_m c, cr_scheme_m d, cr_loan_disbursal_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
				 " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.loan_id='"+loanId+"' and e.disbursal_no='"+disbursalNo+"'");


			  
			 logger.info("In selectDisbursalData ::::::::::::: "+query);
			 mainlist=ConnectionDAO.sqlSelect(query.toString());
			 int size = mainlist.size();
			 logger.info("In selectDisbursalData.....mainlist size: "+size);
			if(size!=0)
			{
				
			 for(int i=0;i<size;i++){

				subList=(ArrayList)mainlist.get(i);
				logger.info("In selectDisbursalData......sublist size: "+subList.size());
				if(subList.size()>0){
					vo = new DisbursalMakerVO();
					vo.setLoanNo((CommonFunction.checkNull(subList.get(0)).trim()));
					vo.setCustomerName((CommonFunction.checkNull(subList.get(1)).trim()));
					
					if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
					{
						vo.setLoanAmt("0.00");
					}
					else
					{
						Number loanAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
						vo.setLoanAmt(myFormatter.format(loanAmount));
					}
					
					vo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(3)).trim()));
					vo.setProduct((CommonFunction.checkNull(subList.get(4)).trim()));
					vo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
					vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(6)).trim()));
					vo.setDisbursalDescription((CommonFunction.checkNull(subList.get(7)).trim()));
					vo.setDisbursalDate((CommonFunction.checkNull(subList.get(8)).trim()));
					
					if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase(""))
					{
						vo.setShortPayAmount("0");
					}
					else
					{
						Number shortPayAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(9)).trim()));
						vo.setShortPayAmount(myFormatter.format(shortPayAmount));
					}
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase(""))
					{
						vo.setDisbursalAmount("0");
					}
					else
					{
						Number disbursalAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim()));
						vo.setDisbursalAmount(myFormatter.format(disbursalAmount));
					}
					
					vo.setDisbursalNo((CommonFunction.checkNull(subList.get(11)).trim()));
					vo.setFinalDisbursal((CommonFunction.checkNull(subList.get(12)).trim()));
					
					if((CommonFunction.checkNull(proposedShortPayAmtStr).trim()).equalsIgnoreCase(""))
					{
						vo.setProposedShortPayAmount("0");
					}
					else
					{
						Number proposedShortPayAmt = myFormatter.parse((CommonFunction.checkNull(proposedShortPayAmtStr).trim()));
						vo.setProposedShortPayAmount(myFormatter.format(proposedShortPayAmt));
					}
					
					if((CommonFunction.checkNull(disbursedAmountStr).trim()).equalsIgnoreCase(""))
					{
						vo.setDisbursedAmount("0");
					}
					else
					{
						Number disbursedAmount = myFormatter.parse((CommonFunction.checkNull(disbursedAmountStr).trim()));
						vo.setDisbursedAmount(myFormatter.format(disbursedAmount));
					}
					
					if((CommonFunction.checkNull(shortAmountPaidStr).trim()).equalsIgnoreCase(""))
					{
						vo.setAdjustedShortPayAmount("0");
					}
					else
					{
						Number shortAmountPaid = myFormatter.parse((CommonFunction.checkNull(shortAmountPaidStr).trim()));
						vo.setAdjustedShortPayAmount(myFormatter.format(shortAmountPaid));
					}
					
					vo.setRepayEffDate((CommonFunction.checkNull(subList.get(13)).trim()));
					vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(14)).trim()));
					vo.setCycleDateValue((CommonFunction.checkNull(subList.get(15)).trim()));
					vo.setRepayMode((CommonFunction.checkNull(subList.get(16)).trim()));
					if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase(""))
						vo.setPdcDepositCount("0");
					else
						vo.setPdcDepositCount((CommonFunction.checkNull(subList.get(17)).trim()));
					
					vo.setNextDueDate((CommonFunction.checkNull(subList.get(18)).trim()));
					
					if((CommonFunction.checkNull(subList.get(19)).trim()).equalsIgnoreCase(""))
						vo.setCurrentMonthEMI("");
					else
					{
						Number amount = myFormatter.parse((CommonFunction.checkNull(subList.get(19)).trim()));
						vo.setCurrentMonthEMI(myFormatter.format(amount));
					}
					if((CommonFunction.checkNull(subList.get(20)).trim()).equalsIgnoreCase(""))
						vo.setPreEMINextMonth("");
					else
					{
						Number amount = myFormatter.parse((CommonFunction.checkNull(subList.get(20)).trim()));
						vo.setPreEMINextMonth(myFormatter.format(amount));
					}
					vo.setLoanDisbursalId(CommonFunction.checkNull(subList.get(21)).trim());
					
					//vo.setDisbursalToId((CommonFunction.checkNull(subList.get(22)).trim()));
					vo.setDisbursalTo((CommonFunction.checkNull(subList.get(23)).trim()));
					
					vo.setLbxBusinessPartnearHID(CommonFunction.checkNull(subList.get(22)).trim());
					vo.setExpectedPaymentDate(CommonFunction.checkNull(subList.get(24)).trim());
					//added by arun
					vo.setMaxDisbursalDate(maxDisbursalDate);
					//added by arun
					vo.setPenalIntCalcDate(CommonFunction.checkNull(subList.get(26)).trim());
					query5.append("SELECT distinct v.BP_NAME from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='"+loanId+"' and v.BP_ID='"+CommonFunction.checkNull(subList.get(22)).trim()+"' and v.BP_TYPE='"+CommonFunction.checkNull(subList.get(23)).trim()+"'");

					logger.info("In disbursal supplier desc..... : "+query1);
						String	  supplierName=CommonFunction.checkNull(ConnectionDAO.singleReturn(query5.toString()));
							  logger.info("In selectDisbursalData.....proposedShortPayAmt: "+proposedShortPayAmtStr);					
					vo.setSupplierDesc(supplierName);
					logger.info("getDisbursalTo in DAO Impl................:------"+vo.getDisbursalTo());
					
					if(CommonFunction.checkNull(subList.get(25)).trim()!=null && !CommonFunction.checkNull(subList.get(25)).trim().equalsIgnoreCase(""))
					{
						query7.append("SELECT C.LOAN_ID,C.LOAN_NO,G.CUSTOMER_NAME FROM CR_LOAN_DTL C,GCD_CUSTOMER_M G WHERE C.LOAN_CUSTOMER_ID=G.CUSTOMER_ID AND C.LOAN_ID="+CommonFunction.checkNull(subList.get(25)).trim()+" AND REC_STATUS='A'");
						logger.info("In TA Loan details ::::::::::::: "+query7.toString());
						
						ArrayList taLoanList=ConnectionDAO.sqlSelect(query7.toString());
						 int size1 = taLoanList.size();
						 logger.info("In selectDisbursalData.....taLoanList size: "+size);
						if(size1>0)
						{			
							 for(int k=0;k<size1;k++)
							 {
								ArrayList subtaLoanList=(ArrayList)taLoanList.get(k);
								logger.info("In selectDisbursalData......taLoanList size: "+subtaLoanList.size());
								if(subtaLoanList.size()>0)
								{
									vo.setLbxTaLoanNoHID((CommonFunction.checkNull(subtaLoanList.get(0)).trim()));
									vo.setTaLoanNo((CommonFunction.checkNull(subtaLoanList.get(1)).trim()));
									vo.setTaCustomerName((CommonFunction.checkNull(subtaLoanList.get(2)).trim()));
								}
							 }
						}
					}
					vo.setMaxExpectedPayDate(CommonFunction.checkNull(maxExpectedPayDate));
					disbursalData.add(vo);		
				}
			  }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			vo = null;
			mainlist= null;
			subList= null;
			query1= null;
			proposedShortPayAmtStr= null;
			query2= null;
			disbursedAmountStr= null;
			query3= null;
			shortAmountPaidStr= null;
			query = null;
		}
		return disbursalData;
}
	
	
	public String disbursalFeasibility(DisbursalMakerVO vo) {
		logger.info("Inside disbursalFeasibility........DAOImpl");
		String status="";
		
		StringBuilder query=new StringBuilder();
		String disbursedAmountStr="";
		BigDecimal disbursedAmount = null;
		StringBuilder query1=new StringBuilder();
		String proposedShortPayAmt="";
		BigDecimal proposedShortPayAmount = null;
		StringBuilder query2=new StringBuilder();
		String shortAmountPaidStr="";
		BigDecimal shortAmountPaid = null;
		String query4="";
		String loanAmtStr="";
		String query5 = "";
		String maxDisbursalNo="";
		StringBuilder query6 = new StringBuilder();
		String disbursalDate ="";
		String query7 = "";
		String repayType = "";
		BigDecimal loanAmount = null;
		BigDecimal disbursalAmount= null;
		BigDecimal shortPayAmount=null;
		BigDecimal balanceAmountBig=null;
		BigDecimal netAmountBig=null;
		BigDecimal blsPrin=null;
		BigDecimal frdAmt=null;
		try
		{
			  query.append("select SUM(ISNULL(disbursal_amount,0)) as disbursal_amt FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()))+"' AND REC_STATUS='A'");
		      logger.info("In getDisbursalValues ::::::::::::::"+query.toString());
			  disbursedAmountStr=ConnectionDAO.singleReturn(query.toString());
			  if(disbursedAmountStr.equals(""))
			  {
				  disbursedAmountStr="0";
			  }
			  logger.info("In disbursedAmountStr:--->"+disbursedAmountStr);
			  disbursedAmount = new BigDecimal(disbursedAmountStr);
			  logger.info("In disbursedAmount:---->"+disbursedAmount);
			  logger.info(" vo.getDisbursalTo():---->"+vo.getDisbursalTo());
			  
			  query1.append("select SUM(ISNULL(ADVICE_AMOUNT,0) - ISNULL(TXN_ADJUSTED_AMOUNT,0) - ISNULL(AMOUNT_IN_PROCESS,0))" +
				" FROM CR_TXNADVICE_DTL WHERE LOAN_ID = '"+((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()))+"' AND BP_TYPE = '"+vo.getDisbursalTo()+"' AND ADVICE_TYPE = 'R' AND REC_STATUS = 'A'");
			  logger.info("In selectDisbursalData ::::::::::::::::::::::::::;"+query1.toString());
			  proposedShortPayAmt=CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
			  if(proposedShortPayAmt.equals(""))
			  {
				  proposedShortPayAmt = "0";
			  }
			  logger.info("In selectDisbursalData.....proposedShortPayAmt: "+proposedShortPayAmt);
			  proposedShortPayAmount = new BigDecimal(proposedShortPayAmt);

			  query2.append("select SUM(ISNULL(SHORT_AMOUNT_ADJUSTED,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()))+"' AND REC_STATUS='A'");
			  logger.info("In getDisbursalValues ::::::::::::::::::::::;"+query2.toString());
			  shortAmountPaidStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
			  if(shortAmountPaidStr.equals(""))
			  {
				  shortAmountPaidStr = "0";
			  }
			  logger.info("In getDisbursalValues.....shortAmountPaid: "+shortAmountPaidStr);
			  shortAmountPaid = new BigDecimal(shortAmountPaidStr);
			  
			  query4= "select loan_loan_amount FROM CR_LOAN_DTL WHERE LOAN_ID = '"+((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()))+"'";
			  logger.info("In getDisbursalValues : "+query4);
			  loanAmtStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query4));
			  if(loanAmtStr.equals(""))
			  {
				  loanAmtStr = "0";
			  }
			  logger.info("In getDisbursalValues.....loanAmt: "+loanAmtStr);
			  
			  query5 = "select (max(a.disbursal_no)-1) from cr_loan_disbursal_dtl a, cr_loan_dtl b where b.disbursal_status='P' and b.loan_id=a.loan_id and a.loan_id='"+((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()))+"'";
			  maxDisbursalNo= CommonFunction.checkNull(ConnectionDAO.singleReturn(query5));
			  logger.info("MaxDisbursal No.: "+maxDisbursalNo);
			  
			  query6.append("select ");
			  query6.append(dbo);
			  query6.append("Date_format(disbursal_date,'"+dateFormat+"') from cr_loan_disbursal_dtl where disbursal_no='"+maxDisbursalNo+"' and loan_id='"+((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()))+"' ");
			  disbursalDate = CommonFunction.checkNull(ConnectionDAO.singleReturn(query6.toString()));
			  
			  query7 = "select loan_repayment_type from cr_loan_dtl where loan_id='"+((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()))+"'";
			  repayType = CommonFunction.checkNull(ConnectionDAO.singleReturn(query7));
			  logger.info("Repayment Type: "+repayType);
			  
			  loanAmount = new BigDecimal(loanAmtStr);
			  String balanceAmount="";
			  if(CommonFunction.checkNull(vo.getPaymentFlag()).equalsIgnoreCase("T")){
				  String query8="select isnull(ADVICE_AMOUNT,0)-isnull(TXN_ADJUSTED_AMOUNT,0)-isnull(AMOUNT_IN_PROCESS,0) from cr_txnadvice_dtl t where t.loan_id='"+vo.getLbxTaLoanNoHID()+"' and t.CHARGE_CODE_ID=8  group by t.loan_id";
				  balanceAmount= CommonFunction.checkNull(ConnectionDAO.singleReturn(query8));
			  }
			 if(!balanceAmount.equalsIgnoreCase("")){
				  balanceAmountBig = new BigDecimal(myFormatter.parse((CommonFunction.checkNull(balanceAmount).trim())).toString());
			 }
			 if(!(CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase(""))){
				  netAmountBig = new BigDecimal(myFormatter.parse((CommonFunction.checkNull(vo.getNetAmount()).trim())).toString());
			 }
			  try
			  {
				     if(!(CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))){
					  disbursalAmount = new BigDecimal(myFormatter.parse((CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString());
					  }
					  if(!CommonFunction.checkNull(vo.getShortPayAmount()).equalsIgnoreCase("")){
					  shortPayAmount = new BigDecimal(myFormatter.parse((CommonFunction.checkNull(vo.getShortPayAmount()).trim())).toString());
					  }  
			   }catch(Exception e)
			  {
				  e.printStackTrace();
			  }
			  if(repayType.equalsIgnoreCase("I"))
			  {

				  String revolvingFlag=CommonFunction.checkNull(vo.getRevolvingFlag()).trim();
				  if(CommonFunction.checkNull(revolvingFlag).trim().equalsIgnoreCase(""))
					  revolvingFlag="N";
				  String balancePrinc=CommonFunction.checkNull(vo.getBalancePrinc()).trim();
				  if(CommonFunction.checkNull(balancePrinc).trim().equalsIgnoreCase(""))
					  balancePrinc="0";
				  blsPrin = new BigDecimal(myFormatter.parse((CommonFunction.checkNull(balancePrinc).trim())).toString());
				  String forwardedAmt=CommonFunction.checkNull(vo.getForwardedAmt()).trim();
				  if(CommonFunction.checkNull(forwardedAmt).trim().equalsIgnoreCase(""))
					  forwardedAmt="0";
				  frdAmt = new BigDecimal(myFormatter.parse((CommonFunction.checkNull(forwardedAmt).trim())).toString());
				  if(CommonFunction.checkNull(revolvingFlag).trim().equalsIgnoreCase("N"))
				  {
					  if(disbursedAmount!=null&&(disbursedAmount.add(disbursalAmount)).compareTo(loanAmount)>0)
					  	  status="DisbAmt";
					  else if(disbursedAmount!=null&&(disbursedAmount.add(disbursalAmount)).compareTo(loanAmount)<0)
					 	  status="LoanAmt";
					  else if(disbursedAmount!=null&&(disbursedAmount.add(disbursalAmount)).compareTo(loanAmount)==0)
					  	  status="eqDisbAmt";			  
				  }
				  
				  if(CommonFunction.checkNull(revolvingFlag).trim().equalsIgnoreCase("Y"))
				  {
					  if(disbursalAmount.compareTo(((loanAmount.subtract(blsPrin)).subtract(frdAmt)))>0)
					  	  status="DisbAmtRV";
					  else
						  status="LoanAmt";  
				  }				  				
				  if(shortPayAmount!=null&&(shortPayAmount).compareTo(proposedShortPayAmount)>0)
				  {
					  status= "ShrtPayAmt";
				  }
			  }
			  else if(repayType.equalsIgnoreCase("N"))
			  {
				  logger.info("Checking Disbursal Feasibility for Non Installment based loan.");
				  if(shortPayAmount!=null){
					  if((shortPayAmount).compareTo(proposedShortPayAmount)>0)
					  {
						  status= "ShrtPayAmt";
					  }
				      }else{
						  status="LoanAmt";
				      }
				  
				  }
			 /* if(CommonFunction.checkNull(vo.getPaymentFlag()).equalsIgnoreCase("T")){
	            	 if(balanceAmountBig!=null && netAmountBig!=null){
	            		 if(netAmountBig.compareTo(balanceAmountBig)==1){
	            		 status= "balAmount";
	            		 }
	            	 }
	             }*/
//			  SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateForDisbursal);
//			  if(disbursedAmount.compareTo(new BigDecimal("0.0000"))>0)
//			  {
//				  try {
//					  Date preDisbDate = dateFormat1.parse(disbursalDate); 
//					  Date actDisbDate = dateFormat1.parse(vo.getDisbursalDate());
//					  if(preDisbDate.after(actDisbDate) || preDisbDate.equals(actDisbDate))
//					  {
//						  status="preDisbDate";
//					  }
//				  }catch(Exception e){
//					  e.printStackTrace();
//				  }
//			  }
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			query=null;
			disbursedAmountStr=null;
			disbursedAmount = null;
			query1=null;
			proposedShortPayAmt=null;
			proposedShortPayAmount = null;
			query2=null;
			shortAmountPaidStr=null;
			shortAmountPaid =null;
			query4=null;
			loanAmtStr=null;
			query5 =null;
			maxDisbursalNo=null;
			query6 =null;
			disbursalDate =null;
			query7 =null;
			repayType =null;
			loanAmount = null;
			disbursalAmount= null;
			shortPayAmount=null;
		}
		return status;
	}
	
	
	public String getDisbursalAmountCheck(DisbursalMakerVO vo) {
	logger.info("Inside getDisbursalAmountCheck.....DAOImpl");
	String status = "";
	
	StringBuilder query3 = new StringBuilder();
	String proposedDisbDateStr = "";
	StringBuilder query = new StringBuilder();
	SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateForDisbursal);
	StringBuilder query1 = new StringBuilder();
	String proposedAmtStr = "";
	BigDecimal proposedDisbursalAmt = null;
	StringBuilder query2 = new StringBuilder();
	String disbursedAmtStr = "";
	BigDecimal disbursedAmt = null;
	BigDecimal loanAmt = null;
	String balanceAmtStr = "";
	BigDecimal balanceAmt = null;
	
	try
	{
		if(CommonFunction.checkNull(vo.getProposedDisbursalFlag()).equalsIgnoreCase("Y")){
		String revolvingFlagQuery="  select P.REVOLVING_FLAG from cr_loan_dtl L "+
		                           " inner join cr_product_m P on P.PRODUCT_ID=L.LOAN_PRODUCT and P.REC_STATUS='A' "+
		                           " where L.LOAN_ID="+vo.getLbxLoanNoHID()+" and L.REC_STATUS='A'";

        String revolvingFlagStatus=ConnectionDAO.singleReturn(revolvingFlagQuery);
       logger.info("revolvingFlagQuery: "+revolvingFlagQuery+" revolvingFlag: "+revolvingFlagStatus);

		if (vo.getRepayMode().equalsIgnoreCase("I"))
		{
			query3.append("select max(");
			query3.append(dbo);
			query3.append("Date_format(PROPOSED_DISBURSAL_DATE,'"+dateFormat+"')) from cr_loan_disbursalsch_dtl "
					+ "where loan_id='"+ vo.getLbxLoanNoHID()+ "' " +
					"and PROPOSED_DISBURSAL_DATE <=");
			query3.append(dbo);
			query3.append("STR_TO_DATE('"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalDate()))+"','"+dateFormat+"')");
			proposedDisbDateStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
			logger.info("proposedDisbDateStr: "+proposedDisbDateStr);
			if(proposedDisbDateStr.equalsIgnoreCase(""))
			{
				query.append("select ");
				query.append(dbo);
				query.append("Date_format(proposed_disbursal_date,'"+dateFormat+"') from cr_loan_disbursalsch_dtl "
					+ "where loan_id='"+ vo.getLbxLoanNoHID()+ "' and disbursal_no='1'");
				logger.info("query::::::::::::::::::::::::"+query.toString());
				proposedDisbDateStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
				logger.info("proposedDisbDateStr: "+proposedDisbDateStr);
			}
			
			try 
			{
				Date proposedDisbDate = dateFormat1.parse(proposedDisbDateStr);
				
				Date actDisbDate=null;
				if(!CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
				{
					
					actDisbDate = dateFormat1.parse(vo.getDisbursalDate());
					
				}
				
				if (proposedDisbDate.after(actDisbDate))
				{
					status = "preProposedDisbDate";
					logger.info("status: "+status);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (status.equalsIgnoreCase(""))
			{
				query=new StringBuilder();
				query.append("select count(loan_id) from cr_loan_disbursalsch_dtl where loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())) + "'");
				logger.info("query::::::::::::::::::::::::::::::::::::::::"+query.toString());
				int count = Integer.parseInt(ConnectionDAO.singleReturn(query.toString()));
				if (count > 1)
				{
					query1.append("select ISNULL(sum(PROPOSED_DISBURSAL_AMOUNT),0) from cr_loan_disbursalsch_dtl where PROPOSED_DISBURSAL_DATE <= ");
					query1.append(dbo);
					query1.append("STR_TO_DATE('"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalDate()))+ "','"+ dateFormat+ "') "
							+ " and loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())) + "'");
					proposedAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
					logger.info(proposedAmtStr);
					proposedDisbursalAmt = new BigDecimal(proposedAmtStr);
					logger.info("proposedDisbursalAmt: "+ proposedDisbursalAmt);
					query2.append("select ISNULL(sum(DISBURSAL_AMOUNT),0) from cr_loan_disbursal_dtl where DISBURSAL_DATE <= ");
					query2.append(dbo);
					query2.append("STR_TO_DATE('"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalDate()))+ "','"+ dateFormat+ "') "
							+ " and loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()))
							+ "' and rec_status='A'");
					disbursedAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
					logger.info(disbursedAmtStr);
					disbursedAmt = new BigDecimal(disbursedAmtStr);
					logger.info("disbursed Amt: " + disbursedAmt);
					logger.info("disbursal Amt: " + vo.getDisbursalAmount());
					int res = 0;
					try
					{
						res = (proposedDisbursalAmt.subtract(disbursedAmt)).compareTo((new BigDecimal(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString())));
						logger.info("res: " + res);
						if (res < 0) 
						{
							status = "disbursalAmount";
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		/*Start by Prashant Here           */
		if (vo.getRepayMode().equalsIgnoreCase("N") && revolvingFlagStatus.equalsIgnoreCase("Y")) 
		{

			query1.append("select loan_loan_amount from cr_loan_dtl "
					+ "where loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())) + "'");
			String loanAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
			loanAmt = new BigDecimal(loanAmtStr);
			logger.info("loanAmt: " + loanAmt);
			query2.append("select loan_balance_principal from cr_loan_dtl "
					+ "where loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())) + "'");
			balanceAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
			balanceAmt = new BigDecimal(balanceAmtStr);
			logger.info("balanceAmt: " + balanceAmt);
			logger.info("disbursal Amt: " + vo.getDisbursalAmount());
			int res = 0;
			try {
				res = (loanAmt.subtract(balanceAmt)).compareTo((new BigDecimal(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString())));
				logger.info("res: " + res);
				if (res < 0)
				{
					status = "disbursalAmtForNonInst";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (vo.getRepayMode().equalsIgnoreCase("N")){
			
			query1.append("select loan_loan_amount from cr_loan_dtl "
					+ "where loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())) + "'");
			String loanAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
			loanAmt = new BigDecimal(loanAmtStr);
			logger.info("loanAmt: " + loanAmt);
			query2.append("select SUM(ISNULL(disbursal_amount,0)) from CR_LOAN_DISBURSAL_DTL "
					+ "where LOAN_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())) + "'");
			
			logger.info("query2:::::::::::::::::::::::" + query2.toString());
			balanceAmtStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
			
			balanceAmt = new BigDecimal(balanceAmtStr);
			logger.info("balanceAmt: " + balanceAmt);
			
			String totDisAmt="";
			
			totDisAmt = CommonFunction.checkNull(vo.getDisbursalAmount()).trim();
		
			logger.info("Total disbursed amount:111 " + totDisAmt);
			logger.info("Total disbursed amount:222 " + new BigDecimal(totDisAmt));
			logger.info("Total disbursed amount: " + vo.getDisbursalAmount());
			int res = 0;
			try {
				res = (loanAmt.subtract(balanceAmt)).compareTo((new BigDecimal(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString())));
				logger.info("res: " + res);
				if (res < 0)
				{
					status = "disbursalAmtForNonInst";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		/*end by Prashant Here           */
	}
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		query3 = null;
		proposedDisbDateStr = null;
		query = null;
		dateFormat1 = null;
		query1 = null;
		proposedAmtStr = null;
		proposedDisbursalAmt = null;
		query2 = null;
		disbursedAmtStr = null;
		disbursedAmt = null;
		loanAmt = null;
		balanceAmtStr = null;
		balanceAmt = null;
	}
	return status;
}
	

	public int saveDisbursalData(DisbursalMakerVO vo, String disbursalFlag ) {
		boolean status=false;
		logger.info("In saveDisbursalData.....................................Dao Impl....111");
		String loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
		String loanDueDay="";
		String loanRepayEff="";
		String nextDueDate="";
		ArrayList qryList = new ArrayList();
		ArrayList loanData = new ArrayList();
		ArrayList subList= new ArrayList();
		ArrayList qryListUpdate = new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
		StringBuilder query2 = new StringBuilder();
		StringBuilder query3 = new StringBuilder();
		String bpId="";
		 int maxId=0;
		try
		{
			if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS"))
			{
				query3.append("SELECT distinct v.BP_ID from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='"+loanId+"' and v.BP_TYPE='"+CommonFunction.checkNull(vo.getDisbursalTo()).trim()+"'");
			
			logger.info("In disbursal bpid..... : "+query3);
			bpId=CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
					  					
			vo.setLbxBusinessPartnearHID(bpId);
			}
			
			logger.info("In insert disbursal Data part"  +vo.getNextDueDate());
			if(vo.getNextDueDate() != null && vo.getNextDueDate().equals("undefined"))
				vo.setNextDueDate("");
			//change by sachin
			loanData = ConnectionDAO.sqlSelect("SELECT LOAN_DUE_DAY,LOAN_REPAY_EFF_DATE,NEXT_DUE_DATE FROM CR_LOAN_DTL WHERE LOAN_ID="+(vo.getLbxLoanNoHID()).trim());  
			logger.info("loanData..."+loanData);
			
			 int size = loanData.size();
			 logger.info("In selectLoandetail.....mainlist size: "+size);
			 if(size!=0)
			 {			 	
			   for(int i=0;i<size;i++)
			   {
				subList=(ArrayList)loanData.get(i);
				logger.info("In selectDisbursalData......sublist size: "+subList.size());
				if(subList.size()>0)
				{
					 loanDueDay=CommonFunction.checkNull(subList.get(0)).trim();
					 loanRepayEff=CommonFunction.checkNull(subList.get(1)).trim();
					 nextDueDate=CommonFunction.checkNull(subList.get(2)).trim();	
				}
			 }
			}
			 //end by sachin
			
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql.append("insert into cr_loan_disbursal_dtl(loan_id, disbursal_no, disbursal_description,");
			bufInsSql.append("disbursal_date,disbursal_amount,loan_due_day,NEXT_DUE_DATE,short_amount_adjusted,disbursal_flag,");
			bufInsSql.append("rec_status,maker_id,maker_date,CUR_MONTH_EMI,NEXT_MONTH_EMI,REPAY_EFF_DATE,DISBURSAL_TO_ID,DISBURSAL_TO,PAYMENT_DATE,TA_LOAN_ID,old_repay_eff_Date,old_LOAN_DUE_DAY,old_NEXT_DUE_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,"); //loan id
			bufInsSql.append(" ?,"); //disbursal_no
			bufInsSql.append(" ?,"); //disbursal_description
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),"); //disbursal_date
			bufInsSql.append(" ?,"); //disbursal_amount
			bufInsSql.append(" ?,"); //loan_due_day
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),");
			bufInsSql.append(" ?,"); //short_amount_adjusted
			bufInsSql.append(" ?,"); //disbursal_flag
			bufInsSql.append(" 'P',"); //rec_status
			bufInsSql.append(" ?,"); //maker_id
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" ?,"); //CUR_MONTH_EMI
			bufInsSql.append(" ? ,"); //NEXT_MONTH_EMI
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //REPAY_EFF_DATE
			bufInsSql.append(" ?,"); //DISBURSAL_TO_ID
			bufInsSql.append(" ?,");//DISBURSAL_TO
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),"); //PAYMENT_DATE
			bufInsSql.append(" ? ,");//TA_LOAN_ID
			bufInsSql.append(" ? ,"); //old_repay_eff_Date
			bufInsSql.append(" ? ,"); //old_LOAN_DUE_DAY
			bufInsSql.append(" ? )");//old_NEXT_DUE_DATE

			
			if(CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxLoanNoHID()).trim());
			
			if(CommonFunction.checkNull(vo.getDisbursalNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDisbursalNo()).trim());
			
			if(CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDisbursalDescription()).trim());
			
			if(CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else  
				insertPrepStmtObject.addString((vo.getDisbursalDate()).trim());
			
			if(CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString());
			
			if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCycleDate()).trim()));
			
			if(CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getNextDueDate()).trim());
			
			if(CommonFunction.checkNull(vo.getShortPayAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getShortPayAmount()).trim())).toString());
			
			if(CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((disbursalFlag).trim());
			
			//insertPrepStmtObject.addString("P");
			
			if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId()).trim());
			
			if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerDate()).trim());
			
			if(CommonFunction.checkNull(vo.getCurrentMonthEMI()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getCurrentMonthEMI()).trim())).toString());
			
			if(CommonFunction.checkNull(vo.getPreEMINextMonth()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getPreEMINextMonth()).trim())).toString());
			
			if(CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase("F"))
				insertPrepStmtObject.addString((vo.getRepayEffDate()).trim());
			else if(CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase("P") && CommonFunction.checkNull(vo.getRepayMode()).equalsIgnoreCase("N"))
			{
				insertPrepStmtObject.addString((vo.getRepayEffDate()).trim());
			}
			else
				insertPrepStmtObject.addNull();
			
			if(CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxBusinessPartnearHID()).trim());
			
			if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDisbursalTo()).trim());
			
			if(CommonFunction.checkNull(vo.getExpectedPaymentDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getExpectedPaymentDate()).trim());
			
			if(CommonFunction.checkNull(vo.getLbxTaLoanNoHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxTaLoanNoHID()).trim());
			
			if(CommonFunction.checkNull(loanRepayEff).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((loanRepayEff).trim());
			
			if(CommonFunction.checkNull(loanDueDay).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((loanDueDay).trim());
			
			if(CommonFunction.checkNull(nextDueDate).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((nextDueDate).trim());
	
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveDisbursalData() insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			
			/*if(vo.getRepayMode().equalsIgnoreCase("I"))
			{
				updatePrepStmtObject = new PrepStmtObject();
			    query2.append("update cr_loan_dtl set LOAN_REPAY_EFF_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"'),"+
					" LOAN_DUE_DAY=?,NEXT_DUE_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"') where loan_id=?");
			    if(CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase(""))
			    	updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString((vo.getRepayEffDate()).trim());
			    
			    if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase(""))
			    	updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString((vo.getCycleDate()).trim());
			    
			    if(CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase(""))
			    	updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString((vo.getNextDueDate()).trim());
			    
			    updatePrepStmtObject.addString(loanId);
			   
			    updatePrepStmtObject.setSql(query2.toString());
			    
				logger.info("IN saveDisbursalData() update cr_loan_dtl query1 ### "+updatePrepStmtObject.printQuery());
				qryList.add(updatePrepStmtObject);
				logger.info("In saveDisbursalData ....cr_loan_dtl.... update query: "+query2);
			}
			
			if(vo.getRepayMode().equalsIgnoreCase("N") && vo.getDisbursalNo().equalsIgnoreCase("1"))
			{
			    query2.append("update cr_loan_dtl set LOAN_REPAY_EFF_DATE=STR_TO_DATE(?,'"+dateFormat+"')"+
						" where loan_id=?");
			    if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			    	updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString((vo.getMakerDate()).trim());
			    
			    updatePrepStmtObject.addString(loanId);
			   
			    updatePrepStmtObject.setSql(query2.toString());
			    
				logger.info("IN saveDisbursalData() update cr_loan_dtl query1 ### "+updatePrepStmtObject.printQuery());
				qryList.add(updatePrepStmtObject);
				logger.info("In saveDisbursalData ....cr_loan_dtl.... update query: "+query2);
			}*/
		    
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveDisbursalData.........update status: "+status);
			
			//change by sachin
			updatePrepStmtObject = new PrepStmtObject();
			logger.info("CommonFunction.checkNull(vo.getNextDueDate()): "+CommonFunction.checkNull(vo.getNextDueDate()));
		    query2.append("update cr_loan_dtl set ");
		    
		    if(CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
		    	query2.append("LOAN_REPAY_EFF_DATE=LOAN_REPAY_EFF_DATE,");
		    }
		    if(!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
		    	query2.append("LOAN_REPAY_EFF_DATE=");
		    	query2.append(dbo);
		    	query2.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),");
		    }
		    if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
		    	query2.append(" LOAN_DUE_DAY=LOAN_DUE_DAY,");
		    }
		    if(!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
		    	query2.append(" LOAN_DUE_DAY=?,");
		    }
		    if(CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
		    	query2.append("NEXT_DUE_DATE=NEXT_DUE_DATE");
		    }
		    if(!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
		    	query2.append("NEXT_DUE_DATE=");
		    	query2.append(dbo);
		    	query2.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')");
		    }
		    query2.append(" where loan_id=? ");
		    
		    if(!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
		    	updatePrepStmtObject.addString((vo.getRepayEffDate()).trim());
		    }
		    
		    if(!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
		    	updatePrepStmtObject.addString((vo.getCycleDate()).trim());
		    }
		    
		    if(!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
		    	updatePrepStmtObject.addString((vo.getNextDueDate()).trim());
		    }

		    updatePrepStmtObject.addString(loanId);
		    logger.info("IN saveDisbursalData() update cr_loan_dtl query3 ### sachinnn"+updatePrepStmtObject.printQuery());
		   
		    updatePrepStmtObject.setSql(query2.toString());
		    
			logger.info("IN saveDisbursalData() update cr_loan_dtl query3 ### "+updatePrepStmtObject.printQuery());
			qryListUpdate.add(updatePrepStmtObject);
			logger.info("In saveDisbursalData ....cr_loan_dtl.... update query: "+query2);
		
	    
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
			logger.info("In saveDisbursalData.........update3 status: "+status);
			
			//end by sachin
			 String maxIdQuery="Select distinct max(LOAN_DISBURSAL_ID) from cr_loan_disbursal_dtl";
			    
			 
			   String id=ConnectionDAO.singleReturn(maxIdQuery);
			   maxId=Integer.parseInt(id.toString());
			   logger.info("maxId : "+maxId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			loanId = null;
			qryList = null;
			bufInsSql = null;
			insertPrepStmtObject = null;
			updatePrepStmtObject = null;
			query2 = null;
			qryListUpdate=null;
		}
		return maxId;
	}
	
	
	public boolean updateDisbursalData(DisbursalMakerVO vo, String recStatus, String disbursalFlag) {
		boolean status=false;
		logger.info("In updateDisbursalData.....................................Dao Impl....111");
		String loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
		ArrayList qryList = new ArrayList();
		ArrayList qryListUpdate  = new ArrayList();
		StringBuilder query = new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
		StringBuilder query2 = new StringBuilder();
		StringBuilder query3 = new StringBuilder();
		String bpId="";
		
		
		try
		{
			if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS"))
			{
				query3.append("SELECT distinct v.BP_ID from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='"+loanId+"' and v.BP_TYPE='"+CommonFunction.checkNull(vo.getDisbursalTo()).trim()+"'");
			
			logger.info("In disbursal bpid..... : "+query3);
			bpId=CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
					  					
			vo.setLbxBusinessPartnearHID(bpId);
			}
			
		query.append("update cr_loan_disbursal_dtl set disbursal_date=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),");
		query.append(" short_amount_adjusted=?, disbursal_amount=?, loan_due_day=?,NEXT_DUE_DATE=");
		query.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		query.append(" disbursal_description=?, rec_status=?, disbursal_flag=?,");
		query.append(" maker_id=?, maker_date=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		query.append("CUR_MONTH_EMI=?,NEXT_MONTH_EMI=?,REPAY_EFF_DATE=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		query.append("DISBURSAL_TO_ID=?,DISBURSAL_TO=?,PAYMENT_DATE=");
		query.append(dbo);
		query.append("STR_TO_DATE(?, '"+dateFormat+"'),TA_LOAN_ID=? where loan_id=? and disbursal_no=?");
		
		String date=vo.getNextDueDate();
		String amount=vo.getPreEMINextMonth();
		if(CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalDate()).trim());
		
		if(CommonFunction.checkNull(vo.getShortPayAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getShortPayAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getCycleDate()).trim());
		
		if(CommonFunction.checkNull(disbursalFlag).trim().equalsIgnoreCase("P"))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((date).trim());
		
		if(CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalDescription()).trim());
		
		if(CommonFunction.checkNull(recStatus).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((recStatus).trim());
		
		if(CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((disbursalFlag).trim());
		
		if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerId()).trim());
		
		if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerDate()).trim());
		
		if(CommonFunction.checkNull(vo.getCurrentMonthEMI()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getCurrentMonthEMI()).trim())).toString());
		
		if(CommonFunction.checkNull(amount).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(amount).trim())).toString());	
		
		if(CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase("F"))
		{
			insertPrepStmtObject.addString((vo.getRepayEffDate()).trim());
			
		}
		else if(CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase("P") && CommonFunction.checkNull(vo.getRepayMode()).equalsIgnoreCase("N"))
		{
			insertPrepStmtObject.addString((vo.getRepayEffDate()).trim());
		}
		else
			insertPrepStmtObject.addNull();

		if(CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxBusinessPartnearHID()).trim());
		
		if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalTo()).trim());
		
		if(CommonFunction.checkNull(vo.getExpectedPaymentDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getExpectedPaymentDate()).trim());
		
		if(CommonFunction.checkNull(vo.getLbxTaLoanNoHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxTaLoanNoHID()).trim());
		
		if(CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxLoanNoHID()).trim());
		
		if(CommonFunction.checkNull(vo.getDisbursalNo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalNo()).trim());
		
		
		
		insertPrepStmtObject.setSql(query.toString());
		logger.info("IN updateDisbursalData() update query1   :   "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		
	/*	if(vo.getRepayMode().equalsIgnoreCase("I") && disbursalFlag.equalsIgnoreCase("F"))
		{
		    query2.append("update cr_loan_dtl set LOAN_REPAY_EFF_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"'),"+
				" LOAN_DUE_DAY=? where loan_id=?");
		    if(CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase(""))
		    	updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((vo.getRepayEffDate()).trim());
		    
		    if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase(""))
		    	updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((vo.getCycleDate()).trim());
		    
		    updatePrepStmtObject.addString(loanId);
		   
		    updatePrepStmtObject.setSql(query2.toString());
		    
			logger.info("IN updateDisbursalData() update cr_loan_dtl query1 ### "+updatePrepStmtObject.printQuery());
			qryList.add(updatePrepStmtObject);
			logger.info("In updateDisbursalData ....cr_loan_dtl.... update query: "+query2);
		} */
		logger.info("In updateDisbursalData.........update status: "+status);
		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In updateDisbursalData.........update status: "+status);
		
		//sachin
	    query2.append("update cr_loan_dtl set ");
	    
	    if(CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
	    	query2.append("LOAN_REPAY_EFF_DATE=LOAN_REPAY_EFF_DATE,");
	    }
	    if(!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
	    	query2.append("LOAN_REPAY_EFF_DATE=");
	    	query2.append(dbo);
	    	query2.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),");
	    }
	    if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
	    	query2.append(" LOAN_DUE_DAY=LOAN_DUE_DAY,");
	    }
	    if(!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
	    	query2.append(" LOAN_DUE_DAY=?,");
	    }
	    if(CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
	    	query2.append("NEXT_DUE_DATE=NEXT_DUE_DATE");
	    }
	    if(!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
	    	query2.append("NEXT_DUE_DATE=");
	    	query2.append(dbo);
	    	query2.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')");
	    }
	    query2.append(" where loan_id=? ");
	    
	    if(!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
	    	updatePrepStmtObject.addString((vo.getRepayEffDate()).trim());
	    }
	    
	    if(!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
	    	updatePrepStmtObject.addString((vo.getCycleDate()).trim());
	    }
	    
	    if(!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
	    	updatePrepStmtObject.addString((vo.getNextDueDate()).trim());
	    }

	    updatePrepStmtObject.addString(loanId);
	    logger.info("IN saveDisbursalData() update cr_loan_dtl query3 ### "+updatePrepStmtObject.printQuery());
	   
	    updatePrepStmtObject.setSql(query2.toString());
	    
		logger.info("IN saveDisbursalData() update cr_loan_dtl query3 ### "+updatePrepStmtObject.printQuery());
		qryListUpdate.add(updatePrepStmtObject);
		logger.info("In saveDisbursalData ....cr_loan_dtl.... update query: "+query2);
	
    
	status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
	logger.info("In saveDisbursalData.........update3 status: "+status);
		
		//sachin

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			loanId = null;
			qryList = null;
			query = null;
			insertPrepStmtObject = null;
			updatePrepStmtObject = null;
			query2 = null;
			qryListUpdate=null;
		}
		return status;
	}

	public ArrayList<DisbursalMakerVO> getDisbursalSchedule(String loanId) {
		logger.info("Inside getDisbursalSchedule.........DAOImpl");
		ArrayList<DisbursalMakerVO> disbursalData = new ArrayList<DisbursalMakerVO>();
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		DisbursalMakerVO vo = null;
		StringBuilder query=new StringBuilder();
		query.append("select a.loan_no, e.disbursal_no, e.disbursal_description, ");
		query.append(dbo);
		query.append("DATE_FORMAT(e.proposed_disbursal_date,'"+dateFormat+"')," +
		 " e.proposed_disbursal_amount, e.disbursal_flag,case when e.PAYMENT_TYPE='D' then 'Deferral ' when e.PAYMENT_TYPE='P' then 'Payment ' end AS PAYMENT_TYPE,e.FAVOURING,case when e.INSTRUMENT_MODE='C' then 'Cash' when e.INSTRUMENT_MODE='Q' then 'Cheque' when e.INSTRUMENT_MODE='D' then 'DD' when e.INSTRUMENT_MODE='N' then 'NEFT' when e.INSTRUMENT_MODE='R' then 'RTGS' when e.INSTRUMENT_MODE='S' then 'ADJUSTMENT' end as INSTRUMENT_MODE,e.BANK_ID,c.BANK_NAME from cr_loan_dtl a " +
		 " left join cr_loan_disbursalsch_dtl e on a.loan_id=e.loan_id left join com_bank_m c on e.BANK_ID=c.BANK_ID where e.loan_id='"+loanId+"'" );
		try
		{
		logger.info("In getDisbursalSchedule::::::::::::::::::"+query.toString());
		mainlist=ConnectionDAO.sqlSelect(query.toString());
		int size = mainlist.size();
		logger.info("In getDisbursalSchedule.....mainlist size: "+size);
		for(int i=0;i<size;i++){

			subList=(ArrayList)mainlist.get(i);
			if(subList.size()>0){
				vo = new DisbursalMakerVO();
				vo.setLoanNo((CommonFunction.checkNull(subList.get(0)).trim()));
				vo.setDisbursalNo((CommonFunction.checkNull(subList.get(1)).trim()));
				vo.setDisbursalDescription((CommonFunction.checkNull(subList.get(2)).trim()));
				vo.setProposedDisbursalDate((CommonFunction.checkNull(subList.get(3)).trim()));
				if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
				{
					vo.setProposedDisbursalAmount("0");
				}
				else
				{
					Number proposedDisbursalAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(4)).trim()));
					vo.setProposedDisbursalAmount(myFormatter.format(proposedDisbursalAmount));
				}
				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("N"))
					vo.setFinalDisbursal("Not Disbursed Yet");
				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("P"))
					vo.setFinalDisbursal("Partial");
				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("F"))
					vo.setFinalDisbursal("Final");
				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
					vo.setFinalDisbursal("");
				//Nishant space starts
				vo.setPaymentType((CommonFunction.checkNull(subList.get(6))).trim());//PAYMENT_TYPE
				vo.setFavouring((CommonFunction.checkNull(subList.get(7))).trim());//FAVOURING
				vo.setInstrumentMode((CommonFunction.checkNull(subList.get(8))).trim());//INSTRUMENT_MODE
				if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("Q")){ 	    		
				  vo.setLbxInstrumentMode("B");
				} 		  
				else if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("D")){ 	    		
				  vo.setLbxInstrumentMode("B");
				} 
				else if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("N")){ 	    		
				  vo.setLbxInstrumentMode("B");
				} 
				else if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("R")){ 	    		
				  vo.setLbxInstrumentMode("B");
				} 
				else if(CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase("S")){ 	    		
				  vo.setLbxInstrumentMode("S");
				} 
				else{
				  vo.setLbxInstrumentMode((CommonFunction.checkNull(subList.get(8))).trim());	
				}
				vo.setLbxbankAccountID((CommonFunction.checkNull(subList.get(9))).trim());//BANK_ID
				vo.setBankAccount((CommonFunction.checkNull(subList.get(10))).trim());//BANK_NAME 
				//Nishant space ends
				disbursalData.add(vo);
			}	
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
			vo = null;
			query= null;
		}
		return disbursalData;
	}
	
	
	public ArrayList<DisbursalMakerVO> getDisbursalData(String loanId) {
		logger.info("Inside getDisbursalData.........DAOImpl");
		ArrayList<DisbursalMakerVO> disbursalData = new ArrayList<DisbursalMakerVO>();
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		DisbursalMakerVO vo = null;
		StringBuilder query=new StringBuilder();
		query.append("select a.loan_no, e.disbursal_no, e.disbursal_description, ");
		query.append(dbo);
		query.append("DATE_FORMAT(e.disbursal_date,'"+dateFormat+"')," +
		 " e.disbursal_amount, e.disbursal_flag from cr_loan_dtl a," +
		 " cr_loan_disbursal_dtl e where a.loan_id=e.loan_id and e.loan_id='"+loanId+"'");
		try
		{
		logger.info("In getDisbursalData ::::::::::::::::::::::;"+query.toString());
		mainlist=ConnectionDAO.sqlSelect(query.toString());
		int size = mainlist.size();
		logger.info("In getDisbursalData.....mainlist size: "+size);
		for(int i=0;i<size;i++){

			subList=(ArrayList)mainlist.get(i);
			if(subList.size()>0){
				vo = new DisbursalMakerVO();
				vo.setLoanNo((CommonFunction.checkNull(subList.get(0)).trim()));
				vo.setDisbursalNo((CommonFunction.checkNull(subList.get(1)).trim()));
				vo.setDisbursalDescription((CommonFunction.checkNull(subList.get(2)).trim()));
				vo.setDisbursalDate((CommonFunction.checkNull(subList.get(3)).trim()));
				if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
				{
					vo.setDisbursalAmount("0");
				}
				else
				{
					Number disbursalAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(4)).trim()));
					vo.setDisbursalAmount(myFormatter.format(disbursalAmount));
				}
				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("P"))
					vo.setFinalDisbursal("Partial");
				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("F"))
					vo.setFinalDisbursal("Final");
				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
					vo.setFinalDisbursal("Not Disbursed Yet");
				disbursalData.add(vo);
			}	
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
			vo = null;
			query= null;
		}
		return disbursalData;
	}
	//neeraj
	public ArrayList<DisbursalMakerVO> calculateEMI(String disbursalAmount,String disbursalDate,String loan_id,String curDate,String userId,String repayEffDate, String disbursalStatus)
	{
		logger.info("In getDisbursalValues..................DAOImpl");
		ArrayList<DisbursalMakerVO> EMIList = new ArrayList();
		DisbursalMakerVO vo=new DisbursalMakerVO();		
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String CurrentMonthEMI="";
		String NextMonthEMI="";
		String s1="";
		String s2="";		
		
		try
		{
			BigDecimal dbAmount=new BigDecimal(myFormatter.parse((CommonFunction.checkNull(disbursalAmount).trim())).toString());
			int lonID=Integer.parseInt(loan_id.trim());
			in.add(dbAmount);
			String disDate=CommonFunction.changeFormat(disbursalDate.trim());
			if(disDate.equalsIgnoreCase(""))
			{
				disDate="";
			}
			in.add(disDate);
			in.add(lonID);
			String cDate=CommonFunction.changeFormat(curDate.trim());
			if(cDate.equalsIgnoreCase(""))
			{
				cDate="";
			}
			in.add(cDate);
			String repDate=CommonFunction.changeFormat(repayEffDate.trim());
			if(repDate.equalsIgnoreCase(""))
			{
				repDate="";
			}
			in.add(repDate);
			in.add(userId.trim());
			in.add(disbursalStatus);			
			out.add(CurrentMonthEMI);
			out.add(NextMonthEMI);
			out.add(s1);
			out.add(s2);		
			logger.info("Pre_EMI_Calc_At_Disbursal ("+in+","+out);
			outMessages=(ArrayList) ConnectionDAO.callSP("Pre_EMI_Calc_At_Disbursal",in,out);
			CurrentMonthEMI=CommonFunction.checkNull(outMessages.get(0));
			NextMonthEMI=CommonFunction.checkNull(outMessages.get(1));
			s1=CommonFunction.checkNull(outMessages.get(2));
			s2=CommonFunction.checkNull(outMessages.get(3));			
			logger.info("CurrentMonthEMI  : "+CurrentMonthEMI);
			logger.info("NextMonthEMI     : "+NextMonthEMI);
	        logger.info("s1  : "+s1);
	        logger.info("s2  : "+s2);		        
	        if(s1.trim().equals("S"))
	        {
	        	if(CommonFunction.checkNull(CurrentMonthEMI).equals(""))
	        		vo.setCurrentMonthEMI("0.00");
	        	else
	        	{
	        		Number curAmount = myFormatter.parse(CurrentMonthEMI);
	        		vo.setCurrentMonthEMI(myFormatter.format(curAmount));			
	        	}
	        	if(CommonFunction.checkNull(NextMonthEMI).equals(""))
	        		vo.setPreEMINextMonth("0.00");
	        	else
	        	{
	        		Number nextAmount = myFormatter.parse(NextMonthEMI);
	        		vo.setPreEMINextMonth(myFormatter.format(nextAmount));			
	        	}
	        }
	        else
	        {
	        	vo.setCurrentMonthEMI("0.00");
	        	vo.setPreEMINextMonth("0.00");
	        }
		}
		catch(Exception e)
		{e.printStackTrace();}			
		EMIList.add(vo);
		return EMIList;
	}
	
	public ArrayList<DisbursalMakerVO> getDisbursalValues(int lbxLoanNoHID, String userId, String businessDate) {
		logger.info("In getDisbursalValues..................DAOImpl");
			ArrayList<DisbursalMakerVO> disbursalList = new ArrayList();
			int count = 0;			
			String countQueryPF = "Select count(loan_id) from cr_loan_disbursal_dtl where loan_id='"+lbxLoanNoHID+"' and rec_status in ('P','F')";
			//logger.info("In getDisbursalValues...........count query: "+countQuery);
			String tempCountPF = ConnectionDAO.singleReturn(countQueryPF);
			int countPF = Integer.parseInt(tempCountPF);
			logger.info("count from select query= "+countPF);
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			ArrayList mainlist=new ArrayList();
			ArrayList subList=new ArrayList();
			StringBuilder query1=new StringBuilder();
			StringBuilder query2=new StringBuilder();
			StringBuilder query3=new StringBuilder();
			String query4="";
			StringBuilder query5=new StringBuilder();
			//String query6="";
			String maxDisbursalNoStr="";
			String maxExpectedPayDate="";
			String maxDisbursalDate="";
			StringBuilder query= new StringBuilder();
			StringBuilder query6= new StringBuilder();
			StringBuilder query7= new StringBuilder();
			DisbursalMakerVO vo=null;
			try
			{
				if(countPF==0)
				{
					try
					{
						query1.append("select SUM(ISNULL(ADVICE_AMOUNT,0) - ISNULL(TXN_ADJUSTED_AMOUNT,0) - ISNULL(AMOUNT_IN_PROCESS,0))");
						query1.append(" FROM CR_TXNADVICE_DTL WHERE LOAN_ID = '"+lbxLoanNoHID+"' AND BP_TYPE = 'CS' AND ADVICE_TYPE = 'R' AND REC_STATUS = 'A' AND ADVICE_DATE<=");
						query1.append(dbo);
						query1.append("STR_TO_DATE('"+businessDate+"', '"+dateFormat+"')");
						
						logger.info("In getDisbursalValues :::::::::::::::::::::::::::"+query1.toString());
						String proposedShortPayAmtStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
						logger.info("In getDisbursalValues.....proposedShortPayAmt: "+proposedShortPayAmtStr);
				
						query2.append("select SUM(ISNULL(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+lbxLoanNoHID+"' AND REC_STATUS='A'");
						logger.info("In getDisbursalValues ::::::::::::::::::::::::::"+query2.toString());
						String disbursedAmountStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
						//logger.info("In getDisbursalValues : "+query2);
						logger.info("In getDisbursalValues.....disbursedAmount: "+disbursedAmountStr);
		
						query3.append("select SUM(ISNULL(SHORT_AMOUNT_ADJUSTED,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+lbxLoanNoHID+"' AND REC_STATUS='A'");

						String shortAmountPaidStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
						logger.info("In getDisbursalValues.....shortAmountPaid: "+shortAmountPaidStr);
					  
						query4 = "select max(disbursal_no) from cr_loan_disbursal_dtl where loan_id='"+lbxLoanNoHID+"'";
						//logger.info("In getDisbursalValues : "+query4);
						maxDisbursalNoStr=ConnectionDAO.singleReturn(query4);
						logger.info("In getDisbursalValues.....maxDisbursalNo: "+maxDisbursalNoStr);
					
						query5.append("select ");
						query5.append(dbo);
						query5.append("DATE_FORMAT(max(PAYMENT_DATE),'"+dateFormat+"') from cr_loan_disbursal_dtl where loan_id='"+lbxLoanNoHID+"' and REC_STATUS='A'");
						logger.info("In getDisbursalValues :::::::::::::::::::::::::::::::::"+query5.toString());
						
						maxExpectedPayDate=ConnectionDAO.singleReturn(query5.toString());
						logger.info("In getDisbursalValues maxExpectedPayDate : "+maxExpectedPayDate);
                         //Added by Arun to make disbursal date editable starts here
						query6.append("select ");
						query6.append(dbo);
						query6.append("DATE_FORMAT(max(DISBURSAL_DATE) ,'"+dateFormat+"')from cr_loan_disbursal_dtl where LOAN_ID='"+lbxLoanNoHID+"' and REC_STATUS='A'");
						maxDisbursalDate=CommonFunction.checkNull(ConnectionDAO.singleReturn(query6.toString()));
						logger.info("In getDisbursalValues maxDisbursalDate : "+maxDisbursalDate);
						//Added by Arun to make disbursal date editable ends here
						// Start by KK
						query7.append("SELECT SUM(ISNULL(DISBURSAL_AMOUNT,0)) FROM CR_LOAN_DISBURSAL_DTL_TEMP WHERE LOAN_ID = '"+lbxLoanNoHID+"' AND REC_STATUS='P'");
						logger.info("In getDisbursalValues In Tem Table ::::::::::::::::::::::::::"+query7.toString());
						String disbursedAmountStrTemp=CommonFunction.checkNull(ConnectionDAO.singleReturn(query7.toString()));
						logger.info("In getDisbursalValues.....disbursedAmountStrTemp: "+disbursedAmountStrTemp);
						//End by KK
						int maxDisbursalNo=0;
						if(CommonFunction.checkNull(maxDisbursalNoStr).equalsIgnoreCase("") || maxDisbursalNoStr==null)
							maxDisbursalNo=1;
						else
							maxDisbursalNo = Integer.parseInt(CommonFunction.checkNull(maxDisbursalNoStr))+1;
						logger.info("Disbursal number: "+maxDisbursalNo);
						
						String maxDisbursalNoStrNew=null;
						String queryMaxDis=null;
						queryMaxDis = "select max(disbursal_no) from cr_loan_disbursal_dtl_temp where loan_id='"+lbxLoanNoHID+"'";
						//logger.info("In getDisbursalValues : "+query4);
						maxDisbursalNoStrNew=ConnectionDAO.singleReturn(queryMaxDis);
						logger.info("In getDisbursalValues.....maxDisbursalNo: "+maxDisbursalNoStrNew);
						if(!CommonFunction.checkNull(maxDisbursalNoStrNew).equalsIgnoreCase("")){
							maxDisbursalNo = Integer.parseInt(CommonFunction.checkNull(maxDisbursalNoStrNew))+1;	
						}
						
						query.append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, ");
						query.append(dbo);
						query.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), c.product_desc, d.scheme_desc");
						query.append(",a.loan_id,a.loan_due_day,a.loan_repayment_type,");
						query.append(" (select count(PDC_LOAN_ID) from cr_pdc_instrument_dtl where  PDC_STATUS='A' and PDC_PURPOSE='INSTALLMENT' and PDC_LOAN_ID='"+lbxLoanNoHID+"')" );
						query.append(" ,(select REVOLVING_FLAG from cr_product_m where product_id=a.LOAN_PRODUCT)REVOLVING_FLAG,LOAN_BALANCE_PRINCIPAL" );
						query.append(" ,isnull((select sum(disbursal_amount)disbursal_amount from cr_loan_disbursal_dtl where rec_status='F' and loan_id='"+CommonFunction.checkNull(lbxLoanNoHID)+"' group by loan_id),0.00)disbursal_amount " );
						query.append(" from cr_loan_dtl a,gcd_customer_m b,");
						query.append(" cr_product_m c, cr_scheme_m d where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product");
						query.append(" and d.scheme_id = a.loan_scheme and a.loan_id='"+lbxLoanNoHID+"' and a.rec_status='A'");
	
						logger.info("In getDisbursalValues By using Ajax:::::::::::: "+query);
						mainlist=ConnectionDAO.sqlSelect(query.toString());
						int size = mainlist.size();
						logger.info("In getDisbursalValues.....mainlist size: "+size);
						for(int i=0;i<size;i++)
						{
							subList=(ArrayList)mainlist.get(i);
							if(subList.size()>0)
							{
								vo=new DisbursalMakerVO();
								vo.setLoanNo((CommonFunction.checkNull(subList.get(0)).trim()));
								vo.setCustomerName((CommonFunction.checkNull(subList.get(1)).trim()));
							
								if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
								{
									vo.setLoanAmt("0");
								}
								else
								{
									Number loanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
									vo.setLoanAmt(myFormatter.format(loanAmt));
								}
	
								vo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(3)).trim()));
								vo.setProduct((CommonFunction.checkNull(subList.get(4)).trim()));
								vo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
								vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(6)).trim()));
								vo.setCycleDateValue((CommonFunction.checkNull(subList.get(7)).trim()));
								vo.setRepayMode((CommonFunction.checkNull(subList.get(8)).trim()));
								if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase(""))
									vo.setPdcDepositCount("0");
								else
									vo.setPdcDepositCount((CommonFunction.checkNull(subList.get(9)).trim()));
							
								
								
								if((CommonFunction.checkNull(proposedShortPayAmtStr).trim()).equalsIgnoreCase(""))
								{
									vo.setProposedShortPayAmount("0");
								}
								else
								{
									Number proposedShortPayAmt = myFormatter.parse((CommonFunction.checkNull(proposedShortPayAmtStr).trim()));
									vo.setProposedShortPayAmount(myFormatter.format(proposedShortPayAmt));
								}
							
								if((CommonFunction.checkNull(disbursedAmountStr).trim()).equalsIgnoreCase(""))
								{
									vo.setDisbursedAmount("0");
								}
								else
								{
									Number disbursedAmount = myFormatter.parse((CommonFunction.checkNull(disbursedAmountStr).trim()));
									vo.setDisbursedAmount(myFormatter.format(disbursedAmount));
								}
							
								if((CommonFunction.checkNull(shortAmountPaidStr).trim()).equalsIgnoreCase(""))
								{
									vo.setAdjustedShortPayAmount("0");
								}
								else
								{
									Number shortAmountPaid = myFormatter.parse((CommonFunction.checkNull(shortAmountPaidStr).trim()));
									vo.setAdjustedShortPayAmount(myFormatter.format(shortAmountPaid));
								}
								
								if((CommonFunction.checkNull(disbursedAmountStrTemp).trim()).equalsIgnoreCase(""))
								{
									vo.setDisbursedAmountTemp("0");
								}
								else
								{
									Number disbursedAmountTemp = myFormatter.parse((CommonFunction.checkNull(disbursedAmountStrTemp).trim()));
									vo.setDisbursedAmountTemp(myFormatter.format(disbursedAmountTemp));
								}
							
								vo.setDisbursalNo((CommonFunction.checkNull(maxDisbursalNo).trim()));
								
								vo.setMaxExpectedPayDate(CommonFunction.checkNull(maxExpectedPayDate).trim()) ;
								//Added by Arun Starts here
								vo.setDisbursalDate(maxDisbursalDate);
								//Added by Arun ends here
								vo.setRevolvingFlag(CommonFunction.checkNull(subList.get(10)).trim());
								vo.setBalancePrinc(CommonFunction.checkNull(subList.get(11)).trim());
								vo.setForwardedAmt(CommonFunction.checkNull(subList.get(12)).trim());
								disbursalList.add(vo);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				else if(count>0)
				{
					logger.info("Disbursal List Size: "+disbursalList.size());
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				countQueryPF =null;
				tempCountPF =null;
				insertPrepStmtObject =null;
				mainlist=null;
				subList=null;
				query1=null;
				query2=null;
				query3=null;
				query4=null;
				query7=null;
				maxDisbursalNoStr=null;
				query=null;
				vo=null;
			}
			return disbursalList;
		}
	
	
	public ArrayList<PaymentMakerForCMVO> viewReceivableForDisbursal(int loanId){

 		ArrayList<PaymentMakerForCMVO> viewReceivabList=new ArrayList<PaymentMakerForCMVO>();
 		
 		ArrayList mainList=new ArrayList ();
			ArrayList subList =new ArrayList();
			StringBuilder query= new StringBuilder();
			PaymentMakerForCMVO paymentVO = null;
 		try{
 			logger.info(" In viewReceivableForDisbursal....");	
 			query.append("SELECT ");
 			query.append(dbo);
 			query.append("DATE_FORMAT(ADVICE_DATE,'"+dateFormat+"'),(Select CHARGE_DESC From com_charge_code_m "+
	 		" Where CHARGE_CODE=CHARGE_CODE_ID) CHARGE,ORG_ADVICE_AMOUNT,WAIVE_OFF_AMOUNT,TDS_AMOUNT," +
	 		" TXN_ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,"+
	        " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,ADVICE_AMOUNT,ADVICE_TYPE,BP_TYPE"+
	 		" FROM cr_txnadvice_dtl  WHERE REC_STATUS in('A','F') " +
	 		// " AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0 "+
	        " AND ADVICE_TYPE in ('R','P') AND LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'" +
	       	" AND BP_TYPE in(select bp_type from business_partner_view where LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"') " +
	       	" ORDER BY ADVICE_DATE ASC");

 			
 			logger.info("In viewReceivableForDisbursal: "+query);	
 			
 			mainList=ConnectionDAO.sqlSelect(query.toString());
 			int size = mainList.size();
 			for(int i=0;i<size;i++)
 			{
 				subList= (ArrayList)mainList.get(i);
 				if(subList.size()>0){
 					paymentVO = new PaymentMakerForCMVO();
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
	 				if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase("P")){
	 					paymentVO.setPaymentMode("Payable");
	 				}else{
	 					paymentVO.setPaymentMode("Receivable");
	 				}
	 				if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("CS")){
	 					paymentVO.setBusinessPartnerType("CUSTOMER");
	 				}else if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("MF")){
	 					paymentVO.setBusinessPartnerType("MANUFACTURER ");
	 				}else if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("SU")){
	 					paymentVO.setBusinessPartnerType("SUPPLIER");
	 				}else if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("OTH")){
	 					paymentVO.setBusinessPartnerType("OTHER");
	 				}
	 				viewReceivabList.add(paymentVO);
 				}
 			}
 				}catch(Exception e){
 					e.printStackTrace();
 				}
 				finally
 				{
 					mainList= null;
 		 			subList = null;
 		 			query= null;
 		 			paymentVO = null;
 				}
 				return viewReceivabList;
 			}


	public ArrayList getFromLoanDtl(String loanId) {

		ArrayList list = new ArrayList();

		logger.info("In getFromLoanDtl: ");
		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query= new StringBuilder();
		StringBuilder bussIrrQ=new StringBuilder();
		RepayScheduleVo repvo = null;
		try {

			query.append("select LOAN_RATE_METHOD,LOAN_FLAT_RATE,LOAN_EFF_RATE,LOAN_IRR1,LOAN_IRR2,UPFRONT_ROUNDING_AMOUNT ");
			query.append(" from cr_loan_dtl where LOAN_ID="+loanId+"");
			logger.info("Query in getFromLoanDtl-----" + query.toString());
			bussIrrQ.append("select DEAL_BUSINESS_IRR from cr_deal_loan_dtl where DEAL_ID=(select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId+")");
			logger.info("Query in getFromLoanDtl--DEAL_BUSINESS_IRR---" + bussIrrQ.toString());
			String bussIrr=ConnectionDAO.singleReturn(bussIrrQ.toString());
			logger.info("Query in getFromLoanDtl--bussIrr---" + bussIrr);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				repvo = new RepayScheduleVo();
				if (subList.size()> 0) {
					
					repvo.setLoanRateMethod((CommonFunction.checkNull(subList.get(0)).trim()));
					if(!CommonFunction.checkNull(subList.get(1)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(1))).trim());  
	    	    		repvo.setFinalRate(myFormatter.format(reconNum));
    	    		}
					//repvo.setFinalRate((CommonFunction.checkNull(subList.get(1)).trim()));
					//logger.info("Final Rate: "+CommonFunction.checkNull(subList.get(1)));
					if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
	    	    		repvo.setEffectiveRate(myFormatter.format(reconNum));
    	    		}
					//repvo.setEffectiveRate((CommonFunction.checkNull(subList.get(2)).trim()));
					if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
	    	    		repvo.setMktIRR1(myFormatter.format(reconNum));
    	    		}
					//repvo.setMktIRR1((CommonFunction.checkNull(subList.get(3)).trim()));
					if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
	    	    		repvo.setMktIRR2(myFormatter.format(reconNum));
    	    		}
					// add by saorabh
					if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number UPFRONT_ROUNDING_AMOUNT =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
	    	    		repvo.setUpfrontRoundingAmount(myFormatter.format(UPFRONT_ROUNDING_AMOUNT));
    	    		}
					else
					repvo.setUpfrontRoundingAmount("0.00");
					// end by saorabh
					//repvo.setMktIRR2((CommonFunction.checkNull(subList.get(4)).trim()));
					if((CommonFunction.checkNull(subList.get(0)).trim()).equalsIgnoreCase("E"))
                    {
						repvo.setFinalRate("");
                    }
					repvo.setBussIrr(bussIrr);
				
				}
				list.add(repvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			mainlist = null;
			subList = null;
			query= null;
			bussIrrQ= null;
			repvo = null;
		}
		return list;
	}
	
	

	public ArrayList getRepaySched(String loanId) {
		
		ArrayList list = new ArrayList();
		logger.info("In getRepaySched: ");
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query= new StringBuilder();
		RepayScheduleVo repvo = null;
		try {

			query.append("select INSTL_NO,");
			query.append(dbo);
			query.append("DATE_FORMAT(INSTL_DATE,'"+dateFormat+"'),INSTL_AMOUNT,PRIN_COMP,INT_COMP,EXCESS_INT,");
			query.append(" case when ADV_FLAG='Y' then 'YES' else 'NO' end as ADV_FLAG,PRIN_OS,OTHER_CHARGES from cr_repaysch_dtl where LOAN_ID="+loanId+"");
			
			logger.info("Query in getRepaySched-----" + query.toString());
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			int size = mainlist.size();
			for (int i = 0; i < size; i++) {
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
					if(!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());  
	    	    		repvo.setOtherCharges(myFormatter.format(reconNum));
    	    		}
					else
					{
						repvo.setOtherCharges("0.00");
					}
				}
				list.add(repvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
			query= null;
			repvo = null;
		}
		return list;
	}


	public String generateRepySchedule(String loanId,String makerId) {
			boolean status=false;
			//CallableStatement cst=null;
			//Connection con=ConnectionDAO.getConnection();
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			String procval="";
			try {
//				con.setAutoCommit(false);
				logger.info("In Generate_Repayment_schedule Procedure: ");
				in.add("LIM");
	     	    in.add(loanId);
	            in.add(makerId);
	     	    out.add(s1);
	     	    out.add(s2);
	     	//    out.add(s3);
	     	    outMessages=(ArrayList) ConnectionDAO.callSP("Generate_Repayment_schedule",in,out);
	     	    s1=CommonFunction.checkNull(outMessages.get(0));
	     	    s2=CommonFunction.checkNull(outMessages.get(1));
	     
				//cst=con.prepareCall("call Generate_Repayment_schedule(?,?,?,?)");
//				cst.setString(1, "LIM");
//				cst.setString(2, loanId);
//				cst.registerOutParameter(3, Types.CHAR);
//				cst.registerOutParameter(4, Types.CHAR);
//				cst.executeUpdate();
//				
//				String s1= cst.getString(3);
//				String s2 = cst.getString(4);
				
				if(s1!=null && s1.equalsIgnoreCase("S"))
				{
					status=true;
					procval=s1;
				}else{
					procval=s2;
				}
				logger.info("status: "+status);
				logger.info("s2: "+s2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return procval;
		}

	public ArrayList<DisbursalMakerVO> getLoanDueDay(String lbxLoanNoHID)
	{
		ArrayList<DisbursalMakerVO> list=new ArrayList<DisbursalMakerVO>();
		
		String query ="";
		DisbursalMakerVO vo=null;
		ArrayList product = null;
		ArrayList data= null;
		try{
			logger.info("In getLoanDueDay..........................DAOImpl");
			query = "select loan_due_day from cr_loan_dtl where loan_id='"+lbxLoanNoHID+"'";
			
			product = ConnectionDAO.sqlSelect(query);
			int size = product.size();
			logger.info("getCycleDateList "+size);
			for(int i=0;i<size;i++){
				logger.info("getNoteCode "+CommonFunction.checkNull(product.get(i)).toString());
				data=(ArrayList)product.get(i);
				if(data.size()>0){
					vo = new DisbursalMakerVO();
					vo.setCycleDateValue((CommonFunction.checkNull(data.get(0))).trim());
					list.add(vo);
				}
			}

			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query =null;
				vo=null;
				product = null;
				data= null;
			}
		return list;
	}
	public ArrayList<PostDisbursalDocVO> getDocumentStage() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getStage..........................DAOImpl");
			String query = "select Description,value from generic_master where generic_key='DOC_STAGE' and rec_status ='A'";
			PostDisbursalDocVO vo = null;
			ArrayList product = ConnectionDAO.sqlSelect(query);
			logger.info("getStage " + product.size());
			for (int i = 0; i < product.size(); i++) {
				logger.info("getStage "+ CommonFunction.checkNull(product.get(i)).toString());
				ArrayList data = (ArrayList) product.get(i);
				for (int k = 0; k < data.size(); k++) {
					logger.info("getStage "+ CommonFunction.checkNull(data.get(k)).toString());
					vo = new PostDisbursalDocVO();
					vo.setStageDescription(((String) data.get(0).toString()));
					vo.setStageValue(((String) data.get(1).toString()));
				}
				list.add(vo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<PostDisbursalDocVO> searchPostDisbursalDocs(
			PostDisbursalDocVO vo,String type) {
		logger.info("In searchPostDisbursalDocs():::::");
		String dealId = null;
		String loanId = null;
		String customerName = null;
		String docStage = null;
		String searchStatus=null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		ArrayList searchlist = new ArrayList();
		ArrayList<PostDisbursalDocVO> detailList = new ArrayList();
		StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		String newType=null;
		try {

			loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
			dealId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()));
			customerName = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()));
			docStage = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDocStage()));
			searchStatus=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchStatusPDD()));
			if(docStage.equalsIgnoreCase("POD") && CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
			{
				logger.info("Running Proc for POD Documents.");
				collectDocuments("LIM",loanId,"POD");
			}
			if(type.equalsIgnoreCase("P") && CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P"))
			{
				newType="P";
			}
			else if(type.equalsIgnoreCase("F"))
			{
				newType="F";
			}
			else
			{
				newType="A";
			}

			if (!loanId.equalsIgnoreCase("")) { 
				
				bufInsSql.append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, " );
				bufInsSql.append(dbo);
				bufInsSql.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),c.product_desc, d.scheme_desc,a.loan_id ");
				bufInsSql.append(" from cr_loan_dtl a,gcd_customer_m b," +
								" cr_product_m c, cr_scheme_m d,");
				if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P") || CommonFunction.checkNull(searchStatus).equalsIgnoreCase("F"))
				{
				bufInsSql.append(" cr_document_dtl_temp e " );
				bufInsSql.append(" where b.customer_id = a.loan_customer_id  and c.product_id = a.loan_product" +
						" and d.scheme_id = a.loan_scheme and e.rec_status = '"+newType+"'" +
						" and e.stage_id='"+docStage+"'" +
						" and a.loan_id=e.txnid");
				}
				else
				{
				bufInsSql.append(" cr_document_dtl e " );
				bufInsSql.append(" where b.customer_id = a.loan_customer_id  and c.product_id = a.loan_product" +
						" and d.scheme_id = a.loan_scheme " +
						" and e.stage_id='"+docStage+"'" +
						" and a.loan_id=e.txnid");
				}
				

				if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A") )
				{
				bufInsSql.append(" and e.txnid not in(select txnid from cr_document_dtl_temp  ");
				bufInsSql.append(" where  rec_status in('P','F') "); 
				bufInsSql.append(" and stage_id='"+docStage+"'" );
				bufInsSql.append(" and txnid="+loanId+")");	
				}
				
				
				bufInsSqlTempCount.append("select count(1) from" +
						" (select distinct a.loan_no, b.customer_name, a.loan_loan_amount, " );
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"')loan_approvaldate,c.product_desc, " +
						" d.scheme_desc,a.loan_id from cr_loan_dtl a,gcd_customer_m b," +
						" cr_product_m c, cr_scheme_m d, ");
				if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P") || CommonFunction.checkNull(searchStatus).equalsIgnoreCase("F"))
				{
				bufInsSqlTempCount.append("cr_document_dtl_temp e " );
				bufInsSqlTempCount.append(" where b.customer_id = a.loan_customer_id  and c.product_id = a.loan_product" +
						" and d.scheme_id = a.loan_scheme and e.rec_status = '"+newType+"'" +
						" and e.stage_id='"+docStage+"'" +
						" and a.loan_id=e.txnid");
				}
				else
				{
				bufInsSqlTempCount.append("cr_document_dtl e " );
				bufInsSqlTempCount.append(" where b.customer_id = a.loan_customer_id  and c.product_id = a.loan_product" +
						" and d.scheme_id = a.loan_scheme " +
						" and e.stage_id='"+docStage+"'" +
						" and a.loan_id=e.txnid");
				}

				if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
				{
					bufInsSqlTempCount.append(" and e.txnid not in(select txnid from cr_document_dtl_temp  ");
					bufInsSqlTempCount.append(" where  rec_status in('P','F') "); 
					bufInsSqlTempCount.append(" and stage_id='"+docStage+"'" );
					bufInsSqlTempCount.append(" and txnid="+loanId+")");	
				}
			}
			if (!dealId.equalsIgnoreCase("")) {
				bufInsSql.append("select distinct a.deal_no, b.customer_name, c.product_desc, d.scheme_desc,"
								+ "a.deal_id ");
				bufInsSql.append(" from cr_deal_dtl a,cr_deal_loan_dtl f, cr_deal_customer_m b,"
								+ " cr_product_m c, cr_scheme_m d, ");
				if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P") || CommonFunction.checkNull(searchStatus).equalsIgnoreCase("F"))
				{
				bufInsSql.append(" cr_document_dtl_temp e " );
				bufInsSql.append(" where a.deal_id=f.deal_id and b.customer_id = a.deal_customer_id and c.product_id = f.deal_product"
						+ " and d.scheme_id = f.deal_scheme and e.rec_status = '"+newType+"'"
						+ " and e.stage_id='"+docStage+"'"
						+ " and a.deal_id=e.txnid");
				}
				else
				{
				bufInsSql.append(" cr_document_dtl e " );
				bufInsSql.append(" where a.deal_id=f.deal_id and b.customer_id = a.deal_customer_id and c.product_id = f.deal_product"
						+ " and d.scheme_id = f.deal_scheme "
						+ " and e.stage_id='"+docStage+"'"
						+ " and a.deal_id=e.txnid");
				}

				if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
				{
				bufInsSql.append(" and e.txnid not in(select txnid from cr_document_dtl_temp  ");
				bufInsSql.append(" where rec_status in('P','F') "); 
				bufInsSql.append(" and stage_id='"+docStage+"'" );
				bufInsSql.append(" and txnid="+dealId+")");	
				}
				bufInsSqlTempCount.append("select count(1) from "
						+ " (select distinct a.deal_no, b.customer_name, c.product_desc, d.scheme_desc,"
						+ " a.deal_id from cr_deal_dtl a,cr_deal_loan_dtl f, cr_deal_customer_m b,"
						+ " cr_product_m c, cr_scheme_m d, ");
				if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("P") || CommonFunction.checkNull(searchStatus).equalsIgnoreCase("F"))
				{
				bufInsSqlTempCount.append("cr_document_dtl_temp e " );
				bufInsSqlTempCount.append( " where a.deal_id=f.deal_id and b.customer_id = a.deal_customer_id and c.product_id = f.deal_product"
						+ " and d.scheme_id = f.deal_scheme and e.rec_status = '"+newType+"'"
						+ " and e.stage_id='"+docStage+"'"
						+ " and a.deal_id=e.txnid");
				}
				else
				{
				bufInsSqlTempCount.append("cr_document_dtl e " );
				bufInsSqlTempCount.append( " where a.deal_id=f.deal_id and b.customer_id = a.deal_customer_id and c.product_id = f.deal_product"
						+ " and d.scheme_id = f.deal_scheme "
						+ " and e.stage_id='"+docStage+"'"
						+ " and a.deal_id=e.txnid");
				}

				if(CommonFunction.checkNull(searchStatus).equalsIgnoreCase("A"))
				{
					bufInsSqlTempCount.append(" and e.txnid not in(select txnid from cr_document_dtl_temp  ");
					bufInsSqlTempCount.append(" where rec_status in('P','F') "); 
					bufInsSqlTempCount.append(" and stage_id='"+docStage+"'" );
					bufInsSqlTempCount.append(" and txnid="+dealId+")");	
				}
			}
			boolean appendSQL = false;

			if ((loanId.equalsIgnoreCase("")) && (customerName.equalsIgnoreCase("")) && (dealId.equalsIgnoreCase("")) && (docStage.equalsIgnoreCase(""))) {
				bufInsSqlTempCount.append(") as b"); }
			
			
			if (!(loanId.equalsIgnoreCase("")) && !(customerName.equalsIgnoreCase("")) && !(dealId.equalsIgnoreCase("")) && !(docStage.equalsIgnoreCase(""))) {
				bufInsSql.append(" and a.deal_id='"+dealId+"' AND a.Loan_Id ='"+loanId+"' AND b.Customer_Name like'%" + customerName+ "%'");
				bufInsSqlTempCount.append(" and a.deal_id='"+dealId+"' AND a.Loan_Id ='"+loanId+"' AND b.Customer_Name like'%" + customerName+ "%')as b");
			}
			
			
			if ((loanId.equalsIgnoreCase("")) || (customerName.equalsIgnoreCase("")) || (dealId.equalsIgnoreCase("")) || (docStage.equalsIgnoreCase(""))) {
				appendSQL = true;
			}
			
			if ((!(dealId.equalsIgnoreCase("")))) {
				bufInsSql.append(" AND a.deal_id='"+dealId+"' ");
				bufInsSqlTempCount.append(" AND a.deal_id='"+dealId+"' ");
				appendSQL = true;

			}

			if ((!(loanId.equalsIgnoreCase("")))) {
				bufInsSql.append(" AND a.Loan_Id ='"+loanId+"' ");
				bufInsSqlTempCount.append(" AND a.Loan_Id ='"+loanId+"' ");
				appendSQL = true;

			}
			if ((!(customerName.equalsIgnoreCase("")))) {
				bufInsSql.append(" AND b.Customer_Name like'%" + customerName+ "%' ");
				bufInsSqlTempCount.append(" AND b.Customer_Name like'%" + customerName+ "%' ");
				appendSQL = true;
			}

			if (!(loanId.equalsIgnoreCase("")) || !(customerName.equalsIgnoreCase("")) || !(dealId.equalsIgnoreCase("")) || !(docStage.equalsIgnoreCase(""))) {
				bufInsSqlTempCount.append(") as b");
				appendSQL=true;
			}
			
				count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				logger.info("  searchPostDisbursalDocs() bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
				
				if((dealId==null && loanId==null && customerName==null && docStage==null) 
						|| (dealId.equalsIgnoreCase("") && loanId.equalsIgnoreCase("") && customerName.equalsIgnoreCase("")
								&& docStage.equalsIgnoreCase(""))|| vo.getCurrentPageLink()>1)
				{
					  logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
					  if(vo.getCurrentPageLink()>1)
					  {
						  startRecordIndex = (vo.getCurrentPageLink()-1)*no;
						  endRecordIndex = no;
							logger.info("startRecordIndex .................... "+startRecordIndex);
							logger.info("endRecordIndex .................... "+endRecordIndex);
					  }
					  	bufInsSql.append(" ORDER BY a.deal_id OFFSET ");
			            bufInsSql.append(startRecordIndex);
			            bufInsSql.append(" ROWS FETCH next ");
			            bufInsSql.append(endRecordIndex);
			            bufInsSql.append(" ROWS ONLY ");		
				  }
				logger.info("search Query...else-------." + bufInsSql);
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

			int size=searchlist.size();
			for (int i = 0; i < size; i++) {
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					if (!loanId.equalsIgnoreCase("")) 
					{
					PostDisbursalDocVO vo1 = new PostDisbursalDocVO();
					if(newType.equalsIgnoreCase("A") || newType.equalsIgnoreCase("P"))
		        	  {
		        		  vo1.setLoanNo("<a href=postDisbursalDocSearch.do?method=openPostDisbursalDocMaker&loanId="
		  					+ (CommonFunction.checkNull(data.get(6)).trim())
		  					+ "&stage="+(CommonFunction.checkNull(vo.getDocStage()).trim())+">"
		  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
		        	  }
		        	  if(newType.equalsIgnoreCase("F"))
		        	  {
		        		  vo1.setLoanNo("<a href=postDisbursalDocSearch.do?method=openPostDisbursalDocAuthor&loanId="
		  					+ (CommonFunction.checkNull(data.get(6)).trim())
		  					+ "&stage="+(CommonFunction.checkNull(vo.getDocStage()).trim())+">"
		  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
		        	  }
					vo1.setCustomerName((CommonFunction.checkNull(data.get(1)).trim()));
					vo1.setLoanAmt((CommonFunction.checkNull(data.get(2)).trim()));
					vo1.setLoanApprovalDate((CommonFunction.checkNull(data.get(3)).trim()));
					vo1.setProduct((CommonFunction.checkNull(data.get(4)).trim()));
					vo1.setScheme((CommonFunction.checkNull(data.get(5)).trim()));
					vo1.setLbxLoanNoHID((CommonFunction.checkNull(data.get(6)).trim()));
					vo1.setDocStage((vo.getDocStage()));
					vo1.setTotalRecordSize(count);
					detailList.add(vo1);
					}
					if (!dealId.equalsIgnoreCase("")) 
					{
					PostDisbursalDocVO vo1 = new PostDisbursalDocVO();
					if(newType.equalsIgnoreCase("A") || newType.equalsIgnoreCase("P"))
		        	  {
		        		  vo1.setDealNo("<a href=postDisbursalDocSearch.do?method=openPostDisbursalDocMaker&dealId="
		  					+ (CommonFunction.checkNull(data.get(4)).trim())
		  					+ "&stage="+(CommonFunction.checkNull(vo.getDocStage()).trim())+">"
		  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
		        	  }
		        	  if(newType.equalsIgnoreCase("F"))
		        	  {
		        		  vo1.setDealNo("<a href=postDisbursalDocSearch.do?method=openPostDisbursalDocAuthor&dealId="
		  					+ (CommonFunction.checkNull(data.get(4)).trim())
		  					+ "&stage="+(CommonFunction.checkNull(vo.getDocStage()).trim())+">"
		  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
		        	  }
					vo1.setCustomerName((CommonFunction.checkNull(data.get(1)).trim()));
					vo1.setProduct((CommonFunction.checkNull(data.get(2)).toString()));
					vo1.setScheme((CommonFunction.checkNull(data.get(3)).trim()));
					vo1.setLbxDealNo((CommonFunction.checkNull(data.get(4)).trim()));
					vo1.setDocStage((vo.getDocStage()));
					vo1.setTotalRecordSize(count);
					detailList.add(vo1);
					}
				}
				data=null;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			vo=null;
			dealId=null;
			loanId=null;
			customerName = null;
			docStage = null;
			searchStatus=null;
			type=null;
			bufInsSql=null;
			bufInsSqlTempCount=null;
			newType=null;
			searchlist.clear();
			searchlist=null;
			
		}

		return detailList;
	}

	public boolean collectDocuments(String txnType, String dealId, String stage) {
//		CallableStatement cst=null;
		boolean status=false;
//		Connection con=ConnectionDAO.getConnection();
  	    ArrayList<Object> in =new ArrayList<Object>();
   		ArrayList<Object> out =new ArrayList<Object>();
   		ArrayList outMessages = new ArrayList();
   		 String s1="";
   		String s2="";
		try {
			logger.info("In collectDocuments proc( Insert_Document_Checklist )");
			logger.info("collectDocuments txnType: "+txnType+" dealId: "+dealId+" stage: "+stage);
//			con.setAutoCommit(false);
//			cst=con.prepareCall("call Insert_Document_Checklist(?,?,?,?,?)");
			in.add(txnType);
			in.add(dealId);
			in.add(stage);
			in.add("NE");
//			cst.registerOutParameter(4, Types.CHAR);
//			cst.registerOutParameter(5, Types.CHAR);
//			cst.executeUpdate();
//			String s1= cst.getString(4);
//			String s2 = cst.getString(5);
			out.add(s1);
			 out.add(s2);

			outMessages=(ArrayList) ConnectionDAO.callSP("Insert_Document_Checklist",in,out);
			 s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
			if(s1!=null && s1.equalsIgnoreCase("S"))
			{
				status=true;
//				con.commit();
			}
			logger.info("s1: "+s1);
			logger.info("s2: "+s2);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return status;
	}	
	 
	
	public ArrayList <DisbursalSearchVO> searchCMGrid(DisbursalSearchVO vo)
      {
      	String loanNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanNo()).trim());
      	String lbxLoanNoHID=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim());
      	String dealNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDealNo()).trim());
      	String lbxDealNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim());
      	String customerName=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim());
      	String panNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPanNo()).trim());
      	String appFormNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAppFormNo()).trim());
      	String mbNumber=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMbNumber()).trim());
      	String drivingLic=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDrivingLic()).trim());
      	String voterId=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVoterId()).trim());
      	String vehicleNo=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVehicleNo()).trim());
      	ArrayList<DisbursalSearchVO> detailListGrid=new ArrayList<DisbursalSearchVO>();
      	StringBuilder bufInsSql =	new StringBuilder();
      	DisbursalSearchVO disVO=null;
      	ArrayList subList=null;
         try
         {
         	  ArrayList searchlist=new ArrayList();
              boolean appendSQL=false;                  
               logger.info("In searchCMGrid......loanNo=  "+loanNo+" And Loan Id="+lbxLoanNoHID);               	     
//            	  bufInsSql.append(" select cr_deal_dtl.deal_id,cr_deal_dtl.DEAL_NO, cr_loan_dtl.LOAN_ID,cr_loan_dtl.LOAN_NO,cr_deal_customer_m.CUSTOMER_NAME, cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,if(cr_loan_dtl.LOAN_ID is null,if(cr_deal_dtl.REC_STATUS='P','Pending',if(cr_deal_dtl.REC_STATUS='A','Authorised',if(cr_deal_dtl.REC_STATUS='X','Rejected','Pending'))),if(cr_loan_dtl.REC_STATUS='P','Pending',if(cr_loan_dtl.REC_STATUS='C','Closed',if(cr_loan_dtl.REC_STATUS='L','Cancelled',if(cr_loan_dtl.REC_STATUS='A',if(cr_loan_dtl.DISBURSAL_STATUS='P','Partially Disperse',if(cr_loan_dtl.DISBURSAL_STATUS='F','Fully Disburse','Authorised')),'Forwarded')))))as currentStatus,cr_termination_dtl.TERMINATION_ID,cr_deal_dtl.DEAL_APPLICATION_FORM_NO,cr_deal_customer_m.CUSTMER_PAN from cr_deal_dtl left outer join cr_deal_loan_dtl on(cr_deal_dtl.DEAL_ID=cr_deal_loan_dtl.DEAL_ID) left outer join cr_loan_dtl on(cr_loan_dtl.LOAN_DEAL_ID=cr_deal_dtl.DEAL_ID) left outer join cr_termination_dtl on(cr_termination_dtl.LOAN_ID=cr_loan_dtl.LOAN_ID)  left outer join cr_deal_customer_m on (cr_deal_dtl.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID) left outer join cr_product_m on(cr_deal_loan_dtl.DEAL_PRODUCT=cr_product_m.PRODUCT_ID) left outer join cr_scheme_m on (cr_deal_loan_dtl.DEAL_SCHEME=cr_scheme_m.SCHEME_ID) left outer join com_branch_m on (cr_deal_dtl.DEAL_BRANCH=com_branch_m.BRANCH_ID) where true  ");
//            	      if(dealNo.length()!=0)
//            	       	 bufInsSql.append(" and cr_deal_dtl.deal_id='"+lbxDealNo+"'");
//            	      if(loanNo.length()!=0)
//            	    	  bufInsSql.append(" and cr_loan_dtl.LOAN_ID='"+lbxLoanNoHID+"'");
//            	      if(customerName.length()!=0)
//            	    	  bufInsSql.append(" and cr_deal_customer_m.CUSTOMER_NAME LIKE '%"+customerName+"%'"); 
//            	      if(appFormNo.length()!=0)
//            	    	  bufInsSql.append(" and cr_deal_dtl.DEAL_APPLICATION_FORM_NO='"+appFormNo+"'");
//            	      if(panNo.length()!=0)
//            	    	  bufInsSql.append(" and cr_deal_customer_m.CUSTMER_PAN='"+panNo+"'");

                bufInsSql.append(" Select DISTINCT CLD.LOAN_DEAL_ID,CDD.DEAL_NO, CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME, CPM.PRODUCT_DESC,CSM.SCHEME_DESC, ");
				//bufInsSql.append(" if(CLD.REC_STATUS='P','Pending' ,if(CLD.REC_STATUS='A','Active', if(CLD.REC_STATUS='L','Cancelled', if(CLD.REC_STATUS='X','Rejected',     if(CLD.REC_STATUS='C','Closed', 'NA'))))) AS REC_STATUS , ");//
                bufInsSql.append("Case when CLD.REC_STATUS='P' then 'Pending' When CLD.REC_STATUS='A' then 'Active' When CLD.REC_STATUS='L' then 'Cancelled' When CLD.REC_STATUS='X' then 'Rejected' When CLD.REC_STATUS='C' then 'Closed'  else 'NA'  End as REC_STATUS,"); 
				//bufInsSql.append(" if(CLD.DISBURSAL_STATUS='N','Not Disbursed', if(CLD.DISBURSAL_STATUS='P','Partially Disbursed',if(CLD.DISBURSAL_STATUS='F','Fully Disbursed','NA'   ))) AS DISBURSAL_STATUS , ");//
                bufInsSql.append("Case when CLD.DISBURSAL_STATUS='N' then 'Not Disbursed' When CLD.DISBURSAL_STATUS='P' then 'Partially Disburse' When CLD.DISBURSAL_STATUS='F' then 'Fully Disbursed' else 'NA'  End as DISBURSAL_STATUS, ");
				bufInsSql.append(" CLD.NPA_FLAG  ,CTD.TERMINATION_ID,CLD.LOAN_REFERENCE_NO,GCM.CUSTMER_PAN,CBM.BRANCH_DESC,");
				bufInsSql.append("ISNULL(maker.USER_NAME,'') maker,");
				bufInsSql.append("ISNULL(author.USER_NAME,'') author ,GCM.FATHER_HUSBAND_NAME, ");//
				bufInsSql.append(dbo);
				bufInsSql.append(" DATE_FORMAT(GCM.CUSTOMER_DOB,'"+dateFormat+"'),");
				bufInsSql.append(" CONCAT(ISNULL(CAM.ADDRESS_LINE1,''),'  ',ISNULL(CAM.ADDRESS_LINE2,''),'  ',ISNULL(CAM.ADDRESS_LINE3,'')) as address ");
				bufInsSql.append(" ,concat(case isnull(LEGAL_FLAG,'NA') when 'X' then 'No' when 'A' then 'Yes' else 'NA' end,'|',case isnull(REPO_FLAG,'NA') when 'X' then 'No' when 'A' then 'Yes' else 'NA' end)legalRepoFlag ");//
				bufInsSql.append(" from cr_loan_dtl CLD ");//
				bufInsSql.append(" INNER join cr_deal_dtl as CDD on(CLD.LOAN_DEAL_ID=CDD.DEAL_ID) ");//
				bufInsSql.append(" INNER join gcd_customer_m as GCM on  (GCM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID) ");//
				bufInsSql.append("INNER join   com_address_m as CAM on (CAM.BPID=GCM.CUSTOMER_ID AND CAM.COMMUNICATION_ADDRESS='Y')");
				bufInsSql.append(" INNER  join cr_product_m as CPM on(CLD.LOAN_PRODUCT=CPM.PRODUCT_ID) ");//
				bufInsSql.append(" INNER  join cr_scheme_m as CSM on (CLD.LOAN_SCHEME=CSM.SCHEME_ID) ");//
				if(!vehicleNo.equalsIgnoreCase("")){
					bufInsSql.append("INNER join cr_loan_collateral_m as CLCM on(CLCM.LOAN_ID=CLD.LOAN_ID) ");
					bufInsSql.append("INNER join  cr_asset_collateral_m  as CACM on (CACM.ASSET_ID=CLCM.ASSETID and CACM.ASSET_TYPE='ASSET' and CACM.ASSET_COLLATERAL_CLASS='VEHICLE')");
				}
				bufInsSql.append(" left outer join com_branch_m as CBM on (CLD.LOAN_BRANCH=CBM.BRANCH_ID) ");//
				bufInsSql.append(" left outer join sec_user_m as maker on (CLD.MAKER_ID=maker.USER_ID) ");//
				bufInsSql.append(" left outer join sec_user_m as author on (CLD.AUTHOR_ID=author.USER_ID) ");//
				
				
				bufInsSql.append(" left outer join cr_termination_dtl as CTD on(CTD.LOAN_ID=CLD.LOAN_ID AND CTD.rec_status='A') ");//
        		
        		bufInsSql.append(" where 'a'='a' ");//
        		
               if(!dealNo.equalsIgnoreCase(""))
            	  bufInsSql.append(" and CLD.LOAN_DEAL_ID='"+lbxDealNo+"'");
      	       if(!loanNo.equalsIgnoreCase(""))
      	    	  bufInsSql.append(" and CLD.LOAN_ID='"+lbxLoanNoHID+"'");
      	       if((!customerName.equalsIgnoreCase("")) && (dealNo.equalsIgnoreCase("") && loanNo.equalsIgnoreCase("")))
      	    	  bufInsSql.append(" and GCM.CUSTOMER_NAME LIKE '%"+customerName+"%'"); 
      	       if(appFormNo.length()!=0)
      	    	  bufInsSql.append(" and CLD.LOAN_REFERENCE_NO='"+appFormNo+"'");
      	       if(panNo.length()!=0)
      	    	  bufInsSql.append(" and GCM.CUSTMER_PAN='"+panNo+"'");
      	       
      	      if(!mbNumber.equalsIgnoreCase(""))
            	  bufInsSql.append(" and  CAM.PRIMARY_PHONE LIKE '%"+mbNumber+"%'");
              if(!drivingLic.equalsIgnoreCase(""))
            	  bufInsSql.append(" and  GCM.DRIVING_LICENSE LIKE '%"+drivingLic+"%'");
              if(!voterId.equalsIgnoreCase(""))
            	  bufInsSql.append(" and GCM.VOTER_ID LIKE '%"+voterId+"%'");
              if(!vehicleNo.equalsIgnoreCase(""))
            	  bufInsSql.append(" and CACM.VEHICLE_REGISTRATION_NO LIKE '%"+vehicleNo+"%'");

            		  logger.info("query :::::::::::::::::::::: "+bufInsSql);
         		
            		  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
            		  int size=searchlist.size();            		
            	      for(int i=0;i<size;i++)
            	      {
            	    	  subList=(ArrayList)searchlist.get(i);
            	    	  if(subList.size()>0)
            	    	  {
            	    		  disVO = new DisbursalSearchVO();
            	    		  disVO.setLbxDealNo((CommonFunction.checkNull(subList.get(0)).trim()));
            	    		  disVO.setDealNo((CommonFunction.checkNull(subList.get(1)).trim()));
            	    		  disVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(2)).trim()));
            	    		  disVO.setLoanNo((CommonFunction.checkNull(subList.get(3)).trim()));
            	    		  disVO.setCustomerName((CommonFunction.checkNull(subList.get(4)).trim()));
            	    		  disVO.setProduct((CommonFunction.checkNull(subList.get(5)).trim()));
            	    		  disVO.setScheme((CommonFunction.checkNull(subList.get(6)).trim()));
            	    		  disVO.setLoanStatus((CommonFunction.checkNull(subList.get(7)).trim()));
            	    		  disVO.setDisbursalStatus((CommonFunction.checkNull(subList.get(8)).trim()));
            	    		  disVO.setNpaStatus((CommonFunction.checkNull(subList.get(9)).trim()));
            	    		  disVO.setTerminationId((CommonFunction.checkNull(subList.get(10)).trim()));
            	    		  disVO.setAppFormNo((CommonFunction.checkNull(subList.get(11)).trim()));
            	    		  disVO.setPanNo((CommonFunction.checkNull(subList.get(12)).trim())); 
            	    		  disVO.setLoanBranch((CommonFunction.checkNull(subList.get(13)).trim()));
            	    		  disVO.setMaker((CommonFunction.checkNull(subList.get(14)).trim()));
            	    		  disVO.setAuthor((CommonFunction.checkNull(subList.get(15)).trim()));
            	    		  disVO.setFatherHusband((CommonFunction.checkNull(subList.get(16)).trim()));
            	    		  disVO.setDob((CommonFunction.checkNull(subList.get(17)).trim()));
            	    		  disVO.setAddr((CommonFunction.checkNull(subList.get(18)).trim()));
            	    		  disVO.setLegalRepoFlag((CommonFunction.checkNull(subList.get(19)).trim()));
            	    		  detailListGrid.add(disVO);
            	    	  }               	    	  
            	      }
           }
           catch(Exception e)
           {e.printStackTrace();}
           finally
           {
         	bufInsSql=null;
            	disVO=null;
            	subList=null;
           }
           return  detailListGrid;	
      }
	public String loandisbursalid() {
		logger.info("Inside getDisbursalSchedule.........DAOImpl");
		
		String loandisbursalid="";
		StringBuilder query=new StringBuilder();
		query.append("SELECT ");
		if(dbType.equalsIgnoreCase("MSSQL"))
		{
			query.append("TOP 1 ");
		}
		query.append("LOAN_DISBURSAL_ID FROM cr_loan_disbursal_dtl   ORDER BY LOAN_DISBURSAL_ID DESC ");
		if(!dbType.equalsIgnoreCase("MSSQL") && dbo.equalsIgnoreCase(""))
		{
			query.append("LIMIT 1");
		}
		try
		{
		logger.info("In getDisbursalSchedule :::::::::::::::::::::"+query.toString());
		loandisbursalid=ConnectionDAO.singleReturn(query.toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("In loandisbursalid : "+loandisbursalid);
		return loandisbursalid;
	}
	public boolean deletedisbursal(String disbursalloanid) {
		logger.info("Inside deletedisbursal().........DAOImpl");
		ArrayList list=new ArrayList();
		boolean status=false;
		StringBuilder  bufInsSql=null;
		StringBuilder bufInsSqlTemp=null;
		
		try
		{
			bufInsSql=new StringBuilder();
			bufInsSqlTemp=new StringBuilder();
			bufInsSql.append("delete  from cr_loan_disbursal_dtl  where LOAN_DISBURSAL_ID = '"+CommonFunction.checkNull(disbursalloanid).trim()+"'");
			bufInsSqlTemp.append("delete  from cr_loan_special_condition_dtl  where LOAN_DISBURSAL_ID = '"+CommonFunction.checkNull(disbursalloanid).trim()+"'");
			list.add(bufInsSqlTemp);
			list.add(bufInsSql);
		logger.info("In deletedisbursal() :bufInsSql "+bufInsSql.toString()+" bufInsSqlTemp: "+bufInsSqlTemp.toString());
		status=ConnectionDAO.sqlInsUpdDelete(list);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return status;
	}
	public String getProposedDisbursalCheck() 
	{
		logger.info("In getEditableFlag()");
		String ediFlag="";
		try
		{
			StringBuilder query=new StringBuilder();
			query.append("select parameter_value from parameter_mst where parameter_key='PROPOSED_DISBURSAL_CHECK'" );
			logger.info("In getEditableFlag() query  :  "+query.toString());
			ediFlag = ConnectionDAO.singleReturn(query.toString());
			logger.info("In getEditableFlag() ediFlag  :  "+ediFlag);
		}
		catch(Exception e)
		{e.printStackTrace();}
		return ediFlag;
	}
	
	
	public ArrayList<DisbursalMakerVO> getShortpayOnDisbursalTo(int lbxLoanNoHID, String userId, String businessDate,String disbursalTo) {
		logger.info("In getShortpayOnDisbursalTo..................DAOImpl");
			ArrayList<DisbursalMakerVO> disbursalList = new ArrayList();
		
			ArrayList mainlist=new ArrayList();
			ArrayList subList=new ArrayList();
			StringBuilder query1=new StringBuilder();
			StringBuilder query2=new StringBuilder();
			String shortpayValue="";
			String shortpay="";
			String shortAmountPaidStr="";
			DisbursalMakerVO vo=new DisbursalMakerVO();
			
			try
			{
							
						query1.append("select SUM(ISNULL(ADVICE_AMOUNT,0) - ISNULL(TXN_ADJUSTED_AMOUNT,0) - ISNULL(AMOUNT_IN_PROCESS,0))" +
							" FROM CR_TXNADVICE_DTL WHERE LOAN_ID = '"+lbxLoanNoHID+"' AND BP_TYPE = '"+disbursalTo+"' AND ADVICE_TYPE = 'R' AND REC_STATUS = 'A' AND ADVICE_DATE<=");
						query1.append(dbo);
						query1.append("STR_TO_DATE('"+businessDate+"', '"+dateFormat+"')");
						logger.info("In getShortpayOnDisbursalTo :::::::::::::::::::::::::::;"+query1.toString());
						shortpayValue=CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
						logger.info("In getShortpayOnDisbursalTo.proposedShortPayAmtStr....shortpayValue: "+shortpayValue);
						logger.info("shortpayValue proposedShortPayAmtStr:::::::::::::::::::::::::: "+shortpayValue);
						if(shortpayValue.equalsIgnoreCase(""))
						{
							shortpay="0.00";
						}
						else
						{
							Number shortval= myFormatter.parse(CommonFunction.checkNull(shortpayValue).trim());
							shortpay=myFormatter.format(shortval);
							
						}
						vo.setProposedShortPayAmount(shortpay);
						logger.info("shortpay (TOTAL RECEIVABLE):::::::::::::::::::::::::: "+shortpay);
						
						
						query2.append("select SUM(ISNULL(TXN_ADJUSTED_AMOUNT,0)) FROM cr_txnadvice_dtl WHERE LOAN_ID = '"+lbxLoanNoHID+"' AND REC_STATUS='A' AND BP_TYPE = '"+disbursalTo+"'");
						logger.info("In getDisbursalValues ::::::::::::::::"+query2.toString());
						shortAmountPaidStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
						logger.info("In getDisbursalValues.....shortAmountPaid: "+shortAmountPaidStr);
						if(shortAmountPaidStr.equalsIgnoreCase(""))
						{
							vo.setAdjustedShortPayAmount("0.00");
						}
						else
						{
							Number adjustshortAmt= myFormatter.parse(CommonFunction.checkNull(shortAmountPaidStr).trim());
							vo.setAdjustedShortPayAmount(myFormatter.format(adjustshortAmt).toString());
						}
						
						mainlist.add(vo);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
		
				query1=null;
			
			}
			return mainlist;
		}
	// Changes By Amit Starts
	public String getDisbAuthorParameter(DisbursalAuthorVO vo)
	{
		String query = "select parameter_value from parameter_mst where parameter_key='DISBURSAL_AUTHOR_NEXT_BUSINESS_DATE'";
		String parameterValue = CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
		StringBuilder query1=new StringBuilder();
		query1.append("select count(1) from cr_loan_disbursal_dtl where MAKER_DATE>=");
		query1.append(dbo);
		query1.append("str_to_date('"+vo.getAuthorDate()+"','%d-%m-%Y') and loan_disbursal_id='"+vo.getLoanDisbursalId()+"'");
		logger.info("query1::::::::::::::::::::::::::::::::::::::::::::::"+query1.toString());
		String res = CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
		String retStr = "";
		if(parameterValue.equalsIgnoreCase("Y") && res.equalsIgnoreCase("0"))
			retStr = "DisbursalAllowedWithAlert";
		else if(parameterValue.equalsIgnoreCase("Y") && res.equalsIgnoreCase("1"))
			retStr="";
		else if(parameterValue.equalsIgnoreCase("N") && res.equalsIgnoreCase("0"))
			retStr="DisbursalNotAllowed";
		else if(parameterValue.equalsIgnoreCase("N") && res.equalsIgnoreCase("1"))
			retStr="";
		return retStr;
	}
	// Changes By Amit Ends

	public String checkSpecialConditionAvail(DisbursalMakerVO vo, String disbId, String recStatus) {
		
		String status="S";
		
		 String specialRequiredQuery="SELECT PARAMETER_VALUE  FROM parameter_mst where PARAMETER_KEY='SPECIAL_CONDITION_REQUIRED'";
		 String specialRequired=ConnectionDAO.singleReturn(specialRequiredQuery);
		 logger.info("specialRequiredQuery: "+specialRequiredQuery+" specialRequired: "+specialRequired);
		 
		if(recStatus.equalsIgnoreCase("F") && CommonFunction.checkNull(specialRequired).equalsIgnoreCase("Y"))
		{
			String specialConditionAtDealQuery="SELECT COUNT(1) FROM cr_deal_special_condition_m WHERE DEAL_ID=(SELECT LOAN_DEAL_ID FROM CR_LOAN_DTL WHERE LOAN_ID='"+vo.getLbxLoanNoHID()+"' )";
			logger.info("specialConditionAtDealQuery: "+specialConditionAtDealQuery);
			String specialConditionAtDealCount=ConnectionDAO.singleReturn(specialConditionAtDealQuery);
			logger.info("specialConditionAtDealCount: "+specialConditionAtDealCount);
			if(!CommonFunction.checkNull(specialConditionAtDealCount).equalsIgnoreCase("0"))
			{
				String specialConditionAtDisbursalQuery="SELECT COUNT(1) FROM cr_loan_special_condition_dtl WHERE LOAN_ID='"+vo.getLbxLoanNoHID()+"'";
				logger.info("specialConditionAtDisbursalQuery: "+specialConditionAtDisbursalQuery);
				String specialConditionAtDisbursalCount=ConnectionDAO.singleReturn(specialConditionAtDisbursalQuery);
				logger.info("specialConditionAtDisbursalCount: "+specialConditionAtDisbursalCount);
				if(CommonFunction.checkNull(specialConditionAtDisbursalCount).equalsIgnoreCase("0"))
				{
					status="E";
				}else
				{
					status="S";
				}
				
			}
			logger.info("checkSpecialConditionAvail status: "+status);
		}
		return status;
	}
	
	
	
	
	/*Arun Change for disbursal with payment Starts here*/
	public String saveDisbursalDataWithPayment(DisbursalMakerVO vo){
		boolean status=false;
		logger.info("In saveDisbursalDataWithPayment.....................................Dao Impl...");
		String loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
		String oldLoanDueDay="";
		String oldLoanRepayEff="";
		String oldNextDueDate="";
		ArrayList qryList = new ArrayList();
		ArrayList loanData = new ArrayList();
		ArrayList subList= new ArrayList();
		ArrayList qryListUpdate = new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
		StringBuilder query2 = new StringBuilder();
		StringBuilder query3 = new StringBuilder();
		String bpId="";
		 int maxId=0;
		String disbursalFlag="";
		try
		{
			if(vo.getFinalDisbursal()!=null&&vo.getFinalDisbursal().equalsIgnoreCase("on")){
				disbursalFlag="F";	
			}else{
				disbursalFlag="P";	
			}
			if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS"))
			{
				query3.append("SELECT distinct v.BP_ID from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='"+loanId+"' and v.BP_TYPE='"+CommonFunction.checkNull(vo.getDisbursalTo()).trim()+"'");
			
			logger.info("In saveDisbursalDataWithPayment bpid..... : "+query3);
			bpId=CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
					  					
			vo.setLbxBusinessPartnearHID(bpId);
			}
			
			logger.info("In saveDisbursalDataWithPayment insert disbursal Data part"  +vo.getNextDueDate());
			if(vo.getNextDueDate() != null && vo.getNextDueDate().equals("undefined"))
				vo.setNextDueDate("");
			//change by sachin
			loanData = ConnectionDAO.sqlSelect("SELECT LOAN_DUE_DAY,LOAN_REPAY_EFF_DATE,dbo.DATE_FORMAT(NEXT_DUE_DATE,'"+dateFormat+"') FROM CR_LOAN_DTL WHERE LOAN_ID="+(vo.getLbxLoanNoHID()).trim());  
			logger.info("In saveDisbursalDataWithPayment loanData..."+loanData);
			
			 int size = loanData.size();
			 logger.info("In saveDisbursalDataWithPayment selectLoandetail.....mainlist size: "+size);
			 if(size!=0)
			 {			 	
			   for(int i=0;i<size;i++)
			   {
				subList=(ArrayList)loanData.get(i);
				logger.info("In selectDisbursalData......sublist size: "+subList.size());
				if(subList.size()>0)
				{
					oldLoanRepayEff=CommonFunction.checkNull(subList.get(0)).trim();
					oldLoanRepayEff=CommonFunction.checkNull(subList.get(1)).trim();
					oldNextDueDate=CommonFunction.checkNull(subList.get(2)).trim();	
				}
			 }
			}
			logger.info(" vo.getRepayEffDate():---------------"+ vo.getRepayEffDate()); 
			
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql.append("insert into cr_loan_disbursal_dtl_temp (loan_id, disbursal_no, disbursal_description,");
			bufInsSql.append("disbursal_date,disbursal_amount,loan_due_day,NEXT_DUE_DATE,disbursal_flag,");
			bufInsSql.append("rec_status,maker_id,maker_date,CUR_MONTH_EMI,NEXT_MONTH_EMI,REPAY_EFF_DATE,");
			bufInsSql.append("old_repay_eff_Date,old_LOAN_DUE_DAY,old_NEXT_DUE_DATE,PENAL_INT_CALC_DATE,");
			bufInsSql.append("DISBURSAL_TO,DISBURSAL_TO_ID,DISBURSAL_TO_NAME,NET_AMOUNT,ADJUST_TOTAL_PAYABLE,");
			bufInsSql.append("PAYMENT_FLAG,TA_LOAN_ID,TA_PARTY_NAME,PMNT_MODE,PMNT_DATE,");
			bufInsSql.append("INSTRUMENT_NO,INSTRUMENT_DATE,BANK_ACCOUNT,BANK_ID,BRANCH_ID,MICR_CODE,IFSC_CODE,");
			bufInsSql.append("PMNT_AMOUNT,TDS_AMOUNT,PMNT_REMARK,SHORT_AMOUNT_ADJUSTED,PAY_TO,PAYEE_NAME,DEFAULT_BRANCH)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,"); //loan id
			bufInsSql.append(" ?,"); //disbursal_no
			bufInsSql.append(" ?,"); //disbursal_description
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),"); //disbursal_date
			bufInsSql.append(" ?,"); //disbursal_amount
			bufInsSql.append(" ?,"); //loan_due_day
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),");//NEXT_DUE_DATE
			bufInsSql.append(" ?,"); //disbursal_flag
			bufInsSql.append(" 'P',"); //rec_status
			bufInsSql.append(" ?,"); //maker_id
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),"); //maker_date
			bufInsSql.append(" ?,"); //CUR_MONTH_EMI
			bufInsSql.append(" ? ,"); //NEXT_MONTH_EMI
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //REPAY_EFF_DATE
			bufInsSql.append(" ? ,"); //old_repay_eff_Date
			bufInsSql.append(" ? ,"); //old_LOAN_DUE_DAY
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"'),");//old_NEXT_DUE_DATE
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'), ");//PENAL_INT_CALC_DATE
			bufInsSql.append(" ? ,");// DISBURSAL_TO
			bufInsSql.append(" ? ,"); //DISBURSAL_TO_ID
			bufInsSql.append(" ? ,"); //DISBURSAL_TO_NAME
			bufInsSql.append(" ? ,"); //NET_AMOUNT
			bufInsSql.append(" ? ,"); //ADJUST_TOTAL_PAYABLE
			bufInsSql.append(" ? ,"); //PAYMENT_FLAG
			bufInsSql.append(" ? ,"); //TA_LOAN_ID
			bufInsSql.append(" ? ,"); //TA_PARTY_NAME
			bufInsSql.append(" ? ,"); //PMNT_MODE
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"') ,"); //PMNT_DATE
			bufInsSql.append(" ? ,"); //INSTRUMENT_NO
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"') ,"); //INSTRUMENT_DATE
			bufInsSql.append(" ? ,"); //BANK_ACCOUNT
			bufInsSql.append(" ? ,"); //BANK_ID
			bufInsSql.append(" ? ,"); //BRANCH_ID
			bufInsSql.append(" ? ,"); //MICR_CODE
			bufInsSql.append(" ? ,"); //IFSC_CODE
			bufInsSql.append(" ? ,");//PMNT_AMOUNT
			bufInsSql.append(" ? ,");//TDS_AMOUNT
			bufInsSql.append(" ? ,");//PMNT_REMARK
			bufInsSql.append(" ? ,");//SHORT_AMOUNT_ADJUSTED
			bufInsSql.append(" ? ,");//PAY_TO
			bufInsSql.append(" ? ,");//PAYEE_NAME
			bufInsSql.append(" ? )"); //DEFAULT_BRANCH
			
			if(CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxLoanNoHID()).trim());
			
			if(CommonFunction.checkNull(vo.getDisbursalNo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDisbursalNo()).trim());
			
			if(CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDisbursalDescription()).trim());
			
			if(CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else  
				insertPrepStmtObject.addString((vo.getDisbursalDate()).trim());
			
			if(CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString());
			
			if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCycleDate()).trim()));
			
			if(CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getNextDueDate()).trim());
			
		
			if(CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((disbursalFlag).trim());
			
			if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId()).trim());
			
			if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerDate()).trim());
			
			if(CommonFunction.checkNull(vo.getCurrentMonthEMI()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getCurrentMonthEMI()).trim())).toString());
			
			if(CommonFunction.checkNull(vo.getPreEMINextMonth()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getPreEMINextMonth()).trim())).toString());
			
			if(CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getRepayEffDate()).trim());
			
			if(CommonFunction.checkNull(oldLoanRepayEff).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((oldLoanRepayEff).trim());
			
			if(CommonFunction.checkNull(oldLoanDueDay).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((oldLoanDueDay).trim());
			
			if(CommonFunction.checkNull(oldNextDueDate).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((oldNextDueDate).trim());
			
	       if(CommonFunction.checkNull(vo.getRepayMode()).equalsIgnoreCase("N")){
	    	   if(CommonFunction.checkNull(vo.getPenalIntCalcDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else	
					insertPrepStmtObject.addString((vo.getPenalIntCalcDate()).trim());  
	       }else{
	    	   insertPrepStmtObject.addNull(); 
	       }
	       
	       if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getDisbursalTo()).trim());
	       
	       if(CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getLbxBusinessPartnearHID()).trim());
	       
	       if(CommonFunction.checkNull(vo.getBusinessPartnerTypeDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getBusinessPartnerTypeDesc()).trim());
	       
	       if(CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getNetAmount()).trim())).toString());
					       
	       if(CommonFunction.checkNull(vo.getAdjustTotalPayable()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getAdjustTotalPayable()).trim())).toString());
	       
	       if(CommonFunction.checkNull(vo.getPaymentFlag()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getPaymentFlag()).trim());
	       
	       if(CommonFunction.checkNull(vo.getLbxTaLoanNoHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getLbxTaLoanNoHID()).trim());
	       
	       if(CommonFunction.checkNull(vo.getTaCustomerName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getTaCustomerName()).trim());
	       
	       if(CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getPaymentMode()).trim());
	       
	       		
	       if(CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getPaymentDate()).trim());
	       
	       if(CommonFunction.checkNull(vo.getInstrumentNumber()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getInstrumentNumber()).trim());
	       
	       if(CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getInstrumentDate()).trim());
	       
	       if(CommonFunction.checkNull(vo.getBankAccount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getBankAccount()).trim());
	       
	       if(CommonFunction.checkNull(vo.getLbxBankID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getLbxBankID()).trim());
	       
	       if(CommonFunction.checkNull(vo.getLbxBranchID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getLbxBranchID()).trim());
	       
	       if(CommonFunction.checkNull(vo.getMicr()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getMicr()).trim());
	       
	       if(CommonFunction.checkNull(vo.getIfsCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getIfsCode()).trim());
	       
	       if(CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getPaymentAmount()).trim())).toString());
	       
	       if(CommonFunction.checkNull(vo.getTdsAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTdsAmount()).trim())).toString());
				 	       
	       if(CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getRemarks()).trim());
	       
	       if(CommonFunction.checkNull(vo.getAdjustTotalReceivable()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getAdjustTotalReceivable()).trim())).toString());
	       
	       if(CommonFunction.checkNull(vo.getPayTo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getPayTo()).trim());
	       
	       if(CommonFunction.checkNull(vo.getPayeeName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getPayeeName()).trim());
	       
	       if(CommonFunction.checkNull(vo.getPaymentFlag()).equalsIgnoreCase("P"))
	       {
		       if(CommonFunction.checkNull(vo.getDefaultBranch()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else	
					insertPrepStmtObject.addString((vo.getDefaultBranch()).trim());
	       }
	       else
	    	   insertPrepStmtObject.addNull();
	       
	       	logger.info("PaymentFlag:::::::::: "+vo.getDefaultBranch());
	       	logger.info("DefaultBranch:::::::::: "+vo.getDefaultBranch());
			
	       
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveDisbursalDataWithPayment() insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			
					    
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveDisbursalDataWithPayment.........update status: "+status);
			
			
			/*updatePrepStmtObject = new PrepStmtObject();
			logger.info("CommonFunction.checkNull(vo.getNextDueDate()): "+CommonFunction.checkNull(vo.getNextDueDate()));
		    query2.append("update cr_loan_dtl set ");
		    
		    if(CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
		    	query2.append("LOAN_REPAY_EFF_DATE=LOAN_REPAY_EFF_DATE,");
		    }
		    if(!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
		    	query2.append("LOAN_REPAY_EFF_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"'),");
		    }
		    if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
		    	query2.append(" LOAN_DUE_DAY=LOAN_DUE_DAY,");
		    }
		    if(!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
		    	query2.append(" LOAN_DUE_DAY=?,");
		    }
		    if(CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
		    	query2.append("NEXT_DUE_DATE=NEXT_DUE_DATE");
		    }
		    if(!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
		    	query2.append("NEXT_DUE_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"')");
		    }
		    query2.append(" where loan_id=? ");
		    
		    if(!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
		    	updatePrepStmtObject.addString((vo.getRepayEffDate()).trim());
		    }
		    
		    if(!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
		    	updatePrepStmtObject.addString((vo.getCycleDate()).trim());
		    }
		    
		    if(!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
		    	updatePrepStmtObject.addString((vo.getNextDueDate()).trim());
		    }

		    updatePrepStmtObject.addString(loanId);
		    logger.info("IN saveDisbursalDataWithPayment() update cr_loan_dtl query3 ### "+updatePrepStmtObject.printQuery());
		   
		    updatePrepStmtObject.setSql(query2.toString());
		    
			logger.info("IN saveDisbursalDataWithPayment() update cr_loan_dtl query3 ### "+updatePrepStmtObject.printQuery());
			qryListUpdate.add(updatePrepStmtObject);
			logger.info("In saveDisbursalDataWithPayment ....cr_loan_dtl.... update query: "+query2);
		
	    
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
			logger.info("In saveDisbursalDataWithPayment.........update3 status: "+status);*/
			
			 String maxIdQuery="Select distinct max(LOAN_DISBURSAL_ID) from cr_loan_disbursal_dtl_temp";
			  String id=ConnectionDAO.singleReturn(maxIdQuery);
			   maxId=Integer.parseInt(id.toString());
			   logger.info("maxId : "+maxId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			loanId = null;
			qryList = null;
			bufInsSql = null;
			insertPrepStmtObject = null;
			updatePrepStmtObject = null;
			query2 = null;
			qryListUpdate=null;
		}
		return maxId+"";
	}
	public ArrayList getDisbursalDataWithPayment(DisbursalMakerVO vo){
		logger.info("In selectDisbursalData.....DAOImpl");
		ArrayList<DisbursalMakerVO> disbursalData = new ArrayList<DisbursalMakerVO>();
		DisbursalMakerVO disVo = null;
		ArrayList mainlist=new ArrayList();
		ArrayList subList=new ArrayList();
		StringBuilder query1= new StringBuilder();
		String proposedShortPayAmtStr= "";
		StringBuilder query2= new StringBuilder();
		String disbursedAmountStr="";
		StringBuilder query3= new StringBuilder();
		String shortAmountPaidStr= "";
		StringBuilder query = new StringBuilder();
		StringBuilder query5= new StringBuilder();
		StringBuilder query7= new StringBuilder();
		String maxDisbursalDate="";
		StringBuilder query6 = new StringBuilder();
		StringBuilder query8 = new StringBuilder();
		//logger.info("In selectDisbursalData Loan Id : "+loanId);
		try{
			  
			/*  query1.append("select SUM(isnull(ADVICE_AMOUNT,0) - isnull(TXN_ADJUSTED_AMOUNT,0) - isnull(AMOUNT_IN_PROCESS,0))" +
				" FROM CR_TXNADVICE_DTL WHERE LOAN_ID = '"+vo.getLbxLoanNoHID()+"' AND BP_TYPE = '"+vo.getBusinessPartnerTypeDesc()+"' AND ADVICE_TYPE = 'R' AND REC_STATUS = 'A'AND ADVICE_DATE<=STR_TO_DATE('"+vo.getMakerDate()+"', '"+dateFormat+"') ");
			  logger.info("In selectDisbursalData : "+query1);
			  proposedShortPayAmtStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
			  logger.info("In selectDisbursalData.....proposedShortPayAmt: "+proposedShortPayAmtStr);*/
		
			  query2.append("select SUM(isnull(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+vo.getLbxLoanNoHID()+"' AND REC_STATUS='A'");
			  //logger.info("In getDisbursalValues : "+query2);
			  disbursedAmountStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
			  logger.info("In selectDisbursalData.....disbursedAmount: "+disbursedAmountStr);

			  query3.append("select SUM(isnull(SHORT_AMOUNT_ADJUSTED,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+vo.getLbxLoanNoHID()+"' AND REC_STATUS='A'");
			  //logger.info("In getDisbursalValues : "+query3);
			  shortAmountPaidStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
			  logger.info("In selectDisbursalData.....shortAmountPaid: "+shortAmountPaidStr);

				String maxExpectedPayDate="";
				
				query6.append("select ");
				query6.append(dbo);
				query6.append("DATE_FORMAT(max(PAYMENT_DATE),'"+dateFormat+"') from cr_loan_disbursal_dtl where loan_id='"+vo.getLbxLoanNoHID()+"' and REC_STATUS='A'");
				logger.info("In selectDisbursalData : "+query6.toString());
				
				maxExpectedPayDate=ConnectionDAO.singleReturn(query6.toString());
				logger.info("In selectDisbursalData maxExpectedPayDate : "+maxExpectedPayDate);
				
				//Added by Arun to make disbursal date editable starts here
				query8.append("select ");
				query8.append(dbo);
				query8.append("DATE_FORMAT(max(DISBURSAL_DATE) ,'"+dateFormat+"')from cr_loan_disbursal_dtl where LOAN_ID='"+vo.getLbxLoanNoHID()+"' and REC_STATUS='A'");
				logger.info("query8:----------"+query8.toString());
				maxDisbursalDate=CommonFunction.checkNull(ConnectionDAO.singleReturn(query8.toString()));
				logger.info("In selectDisbursalData maxDisbursalDate : "+maxDisbursalDate);
				//Added by Arun to make disbursal date editable ends here
			  
			  query.append("select a.loan_no, b.customer_name, a.loan_loan_amount, ");
			  query.append(dbo);
			  query.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), c.product_desc, d.scheme_desc,"+
				 " a.loan_id, e.disbursal_description, ");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.disbursal_date,'"+dateFormat+"')," +
				 " e.short_amount_adjusted, e.disbursal_amount, e.disbursal_no, e.disbursal_flag,");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.REPAY_EFF_DATE,'"+dateFormat+"'),e.remarks," +
				 " e.loan_due_day,a.loan_repayment_type," +
				 " (select count(PDC_LOAN_ID) from cr_pdc_instrument_dtl where  PDC_STATUS not in('X','R') and PDC_PURPOSE='INSTALLMENT' and PDC_LOAN_ID='"+vo.getLbxLoanNoHID()+"'),");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.NEXT_DUE_DATE ,'%d-%m-%Y'),e.CUR_MONTH_EMI,e.NEXT_MONTH_EMI,e.LOAN_DISBURSAL_ID," +
				 " e.DISBURSAL_TO_ID,e.DISBURSAL_TO, ");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.PAYMENT_DATE,'"+dateFormat+"'),e.TA_LOAN_ID, ");
			  query.append(dbo);
			  query.append("DATE_FORMAT(e.PENAL_INT_CALC_DATE ,'"+dateFormat+"')" +
				 "  from cr_loan_dtl a,gcd_customer_m b," +
				 " cr_product_m c, cr_scheme_m d, cr_loan_disbursal_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
				 " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.loan_id='"+vo.getLbxLoanNoHID()+"' and e.disbursal_no='"+vo.getDisbursalNo()+"'");

			  
			 logger.info("In selectDisbursalData ::::::::::::: "+query);
			 mainlist=ConnectionDAO.sqlSelect(query.toString());
			 int size = mainlist.size();
			 logger.info("In selectDisbursalData.....mainlist size: "+size);
			if(size!=0)
			{
				
			 for(int i=0;i<size;i++){

				subList=(ArrayList)mainlist.get(i);
				logger.info("In selectDisbursalData......sublist size: "+subList.size());
				if(subList.size()>0){
					disVo = new DisbursalMakerVO();
					disVo.setLoanNo((CommonFunction.checkNull(subList.get(0)).trim()));
					disVo.setCustomerName((CommonFunction.checkNull(subList.get(1)).trim()));
					
					if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
					{
						disVo.setLoanAmt("0.00");
					}
					else
					{
						Number loanAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
						disVo.setLoanAmt(myFormatter.format(loanAmount));
					}
					
					disVo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(3)).trim()));
					disVo.setProduct((CommonFunction.checkNull(subList.get(4)).trim()));
					disVo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
					disVo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(6)).trim()));
					disVo.setDisbursalDescription((CommonFunction.checkNull(subList.get(7)).trim()));
					disVo.setDisbursalDate((CommonFunction.checkNull(subList.get(8)).trim()));
					
					if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase(""))
					{
						disVo.setShortPayAmount("0");
					}
					else
					{
						Number shortPayAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(9)).trim()));
						disVo.setShortPayAmount(myFormatter.format(shortPayAmount));
					}
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase(""))
					{
						disVo.setDisbursalAmount("0");
					}
					else
					{
						Number disbursalAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim()));
						disVo.setDisbursalAmount(myFormatter.format(disbursalAmount));
					}
					
					disVo.setDisbursalNo((CommonFunction.checkNull(subList.get(11)).trim()));
					disVo.setFinalDisbursal((CommonFunction.checkNull(subList.get(12)).trim()));
					
					if((CommonFunction.checkNull(proposedShortPayAmtStr).trim()).equalsIgnoreCase(""))
					{
						disVo.setProposedShortPayAmount("0");
					}
					else
					{
						Number proposedShortPayAmt = myFormatter.parse((CommonFunction.checkNull(proposedShortPayAmtStr).trim()));
						disVo.setProposedShortPayAmount(myFormatter.format(proposedShortPayAmt));
					}
					
					if((CommonFunction.checkNull(disbursedAmountStr).trim()).equalsIgnoreCase(""))
					{
						disVo.setDisbursedAmount("0");
					}
					else
					{
						Number disbursedAmount = myFormatter.parse((CommonFunction.checkNull(disbursedAmountStr).trim()));
						disVo.setDisbursedAmount(myFormatter.format(disbursedAmount));
					}
					
					if((CommonFunction.checkNull(shortAmountPaidStr).trim()).equalsIgnoreCase(""))
					{
						disVo.setAdjustedShortPayAmount("0");
					}
					else
					{
						Number shortAmountPaid = myFormatter.parse((CommonFunction.checkNull(shortAmountPaidStr).trim()));
						disVo.setAdjustedShortPayAmount(myFormatter.format(shortAmountPaid));
					}
					
					disVo.setRepayEffDate((CommonFunction.checkNull(subList.get(13)).trim()));
					vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(14)).trim()));
					disVo.setCycleDateValue((CommonFunction.checkNull(subList.get(15)).trim()));
					disVo.setCycleDate((CommonFunction.checkNull(subList.get(15)).trim()));
					disVo.setRepayMode((CommonFunction.checkNull(subList.get(16)).trim()));
					if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase(""))
						disVo.setPdcDepositCount("0");
					else
						disVo.setPdcDepositCount((CommonFunction.checkNull(subList.get(17)).trim()));
					
					disVo.setNextDueDate((CommonFunction.checkNull(subList.get(18)).trim()));
					
					if((CommonFunction.checkNull(subList.get(19)).trim()).equalsIgnoreCase(""))
						disVo.setCurrentMonthEMI("");
					else
					{
						Number amount = myFormatter.parse((CommonFunction.checkNull(subList.get(19)).trim()));
						disVo.setCurrentMonthEMI(myFormatter.format(amount));
					}
					if((CommonFunction.checkNull(subList.get(20)).trim()).equalsIgnoreCase(""))
						disVo.setPreEMINextMonth("");
					else
					{
						Number amount = myFormatter.parse((CommonFunction.checkNull(subList.get(20)).trim()));
						disVo.setPreEMINextMonth(myFormatter.format(amount));
					}
					disVo.setLoanDisbursalId(CommonFunction.checkNull(subList.get(21)).trim());
					
					//disVo.setDisbursalToId((CommonFunction.checkNull(subList.get(22)).trim()));
					disVo.setDisbursalTo((CommonFunction.checkNull(subList.get(23)).trim()));
					
					disVo.setLbxBusinessPartnearHID(CommonFunction.checkNull(subList.get(22)).trim());
					disVo.setExpectedPaymentDate(CommonFunction.checkNull(subList.get(24)).trim());
					//added by arun
					disVo.setMaxDisbursalDate(maxDisbursalDate);
					//added by arun
					disVo.setMaxExpectedPayDate(CommonFunction.checkNull(maxExpectedPayDate));
					disVo.setPenalIntCalcDate(CommonFunction.checkNull(subList.get(26)).trim());
					disbursalData.add(disVo);		
				}
			  }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			vo = null;
			mainlist= null;
			subList= null;
			query1= null;
			proposedShortPayAmtStr= null;
			query2= null;
			disbursedAmountStr= null;
			query3= null;
			shortAmountPaidStr= null;
			query = null;
		}
		return disbursalData;
	}
	
	public String updateDisbursalDataWithPayment(DisbursalMakerVO vo,String forStatus) {
		boolean status=false;
		logger.info("In updateDisbursalDataWithPayment.....................................Dao Impl....111");
		String loanId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()));
		ArrayList qryList = new ArrayList();
		ArrayList qryListUpdate  = new ArrayList();
		StringBuilder query = new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
		StringBuilder query2 = new StringBuilder();
		StringBuilder query3 = new StringBuilder();
		String bpId="";
		String result="";
		String disbursalFlag="";
		String recStatus=forStatus;
		try
		{
			if(!CommonFunction.checkNull(recStatus).equalsIgnoreCase("F")){
				recStatus="P";
			}
			logger.info("recStatus-------------------"+forStatus);
			
			if(vo.getFinalDisbursal()!=null&&vo.getFinalDisbursal().equalsIgnoreCase("on")){
				disbursalFlag="F";	
			}else{
				disbursalFlag="P";	
			}
			
			if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS"))
			{
				query3.append("SELECT distinct v.BP_ID from business_partner_view v,generic_master g where g.GENERIC_KEY='BPTYPE' and g.VALUE=v.BP_TYPE and g.REC_STATUS='A' AND v.LOAN_ID='"+vo.getLbxLoanNoHID()+"' and v.BP_TYPE='"+CommonFunction.checkNull(vo.getDisbursalTo()).trim()+"'");
			
			logger.info("In disbursal bpid..... : "+query3);
			bpId=CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
					  					
			vo.setLbxBusinessPartnearHID(bpId);
			}
			logger.info(" vo.getRepayEffDate():---------------"+ vo.getRepayEffDate());	
		query.append("update cr_loan_disbursal_dtl_temp set disbursal_description=?,disbursal_date=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'),");
		query.append(" disbursal_amount=?, loan_due_day=?,NEXT_DUE_DATE=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'), rec_status=?, disbursal_flag=?,");
		query.append(" maker_id=?, maker_date=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		query.append(" CUR_MONTH_EMI=?,NEXT_MONTH_EMI=?,REPAY_EFF_DATE=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'),PENAL_INT_CALC_DATE=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'),");
		query.append("DISBURSAL_TO=?,DISBURSAL_TO_ID=?,DISBURSAL_TO_NAME=?,NET_AMOUNT=?,ADJUST_TOTAL_PAYABLE=?,");
		query.append("PAYMENT_FLAG=?,TA_LOAN_ID=?,TA_PARTY_NAME=?,PMNT_MODE=?,PMNT_DATE=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'),");
		query.append("INSTRUMENT_NO=?,INSTRUMENT_DATE=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'),BANK_ACCOUNT=?,BANK_ID=?,BRANCH_ID=?,MICR_CODE=?,IFSC_CODE=?,");
		query.append("PMNT_AMOUNT=?,TDS_AMOUNT=?,PMNT_REMARK=?,SHORT_AMOUNT_ADJUSTED=?,PAY_TO=?,PAYEE_NAME=? ");
		
		query.append(" where LOAN_DISBURSAL_ID=?");
		
		String date=vo.getNextDueDate();
		String amount=vo.getPreEMINextMonth();
		
		if(CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalDescription()).trim());
		
		if(CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalDate()).trim());
		
		if(CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getCycleDate()).trim());
			
		
		if(CommonFunction.checkNull(disbursalFlag).trim().equalsIgnoreCase("P"))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((date).trim());//next_due_date
		
	/*	if(CommonFunction.checkNull(vo.getDisbursalDescription()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalDescription()).trim());  */
		
		if(CommonFunction.checkNull(recStatus).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((recStatus).trim());
		
		if(CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((disbursalFlag).trim());
		
		if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerId()).trim());
		
		if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerDate()).trim());
		
		if(CommonFunction.checkNull(vo.getCurrentMonthEMI()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getCurrentMonthEMI()).trim())).toString());
		
		if(CommonFunction.checkNull(amount).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(amount).trim())).toString());	
		
		if(CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else	
			insertPrepStmtObject.addString((vo.getRepayEffDate()).trim());
		
		 if(CommonFunction.checkNull(vo.getRepayMode()).equalsIgnoreCase("N")){
	    	   if(CommonFunction.checkNull(vo.getPenalIntCalcDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else	
					insertPrepStmtObject.addString((vo.getPenalIntCalcDate()).trim());  
	       }else{
	    	   insertPrepStmtObject.addNull(); 
	       }
		 if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getDisbursalTo()).trim());
	       
	       if(CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getLbxBusinessPartnearHID()).trim());
	       
	       if(CommonFunction.checkNull(vo.getBusinessPartnerTypeDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getBusinessPartnerTypeDesc()).trim());
	       
	       if(CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getNetAmount()).trim())).toString());
					       
	       if(CommonFunction.checkNull(vo.getAdjustTotalPayable()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getAdjustTotalPayable()).trim())).toString());
	       
	       if(CommonFunction.checkNull(vo.getPaymentFlag()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getPaymentFlag()).trim());
	       
	       if(CommonFunction.checkNull(vo.getLbxTaLoanNoHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getLbxTaLoanNoHID()).trim());
	       
	       if(CommonFunction.checkNull(vo.getTaCustomerName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getTaCustomerName()).trim());
	       
	       if(CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getPaymentMode()).trim());
	       
	       		
	       if(CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getPaymentDate()).trim());
	       
	       if(CommonFunction.checkNull(vo.getInstrumentNumber()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getInstrumentNumber()).trim());
	       
	       if(CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getInstrumentDate()).trim());
	       
	       if(CommonFunction.checkNull(vo.getBankAccount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getBankAccount()).trim());
	       
	       if(CommonFunction.checkNull(vo.getLbxBankID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getLbxBankID()).trim());
	       
	       if(CommonFunction.checkNull(vo.getLbxBranchID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getLbxBranchID()).trim());
	       
	       if(CommonFunction.checkNull(vo.getMicr()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getMicr()).trim());
	       
	       if(CommonFunction.checkNull(vo.getIfsCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getIfsCode()).trim());
	       
	       if(CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getPaymentAmount()).trim())).toString());
	       
	       if(CommonFunction.checkNull(vo.getTdsAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTdsAmount()).trim())).toString());
				 	       
	       if(CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else	
				insertPrepStmtObject.addString((vo.getRemarks()).trim());
	       
	       if(CommonFunction.checkNull(vo.getAdjustTotalReceivable()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getAdjustTotalReceivable()).trim())).toString());
			
		 
		
		if(CommonFunction.checkNull(vo.getPayTo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else	
			insertPrepStmtObject.addString((vo.getPayTo()).trim());
       
		if(CommonFunction.checkNull(vo.getPayeeName()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else	
			insertPrepStmtObject.addString((vo.getPayeeName()).trim());
		
		if(CommonFunction.checkNull(vo.getLoanDisbursalId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLoanDisbursalId()).trim());
		
	    insertPrepStmtObject.setSql(query.toString());
		logger.info("IN updateDisbursalDataWithPayment() update query1   :   "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		
	
		logger.info("In updateDisbursalDataWithPayment.........update status: "+status);
		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In updateDisbursalDataWithPayment.........update status: "+status);
		
		
	    query2.append("update cr_loan_dtl set ");
	    
	    if(CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
	    	query2.append("LOAN_REPAY_EFF_DATE=LOAN_REPAY_EFF_DATE,");
	    }
	    if(!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
	    	query2.append("LOAN_REPAY_EFF_DATE=");
	    	query2.append(dbo);
	    	query2.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),");
	    }
	    if(CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
	    	query2.append(" LOAN_DUE_DAY=LOAN_DUE_DAY,");
	    }
	    if(!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
	    	query2.append(" LOAN_DUE_DAY=?,");
	    }
	    if(CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
	    	query2.append("NEXT_DUE_DATE=NEXT_DUE_DATE");
	    }
	    if(!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
	    	query2.append("NEXT_DUE_DATE=");
	    	query2.append(dbo);
	    	query2.append("STR_TO_DATE(?,'"+dateFormatWithTime+"')");
	    }
	    query2.append(" where loan_id=? ");
	    
	    if(!CommonFunction.checkNull(vo.getRepayEffDate()).equalsIgnoreCase("")){
	    	updatePrepStmtObject.addString((vo.getRepayEffDate()).trim());
	    }
	    
	    if(!CommonFunction.checkNull(vo.getCycleDate()).equalsIgnoreCase("")){
	    	updatePrepStmtObject.addString((vo.getCycleDate()).trim());
	    }
	    
	    if(!CommonFunction.checkNull(vo.getNextDueDate()).equalsIgnoreCase("")){
	    	updatePrepStmtObject.addString((vo.getNextDueDate()).trim());
	    }

	    updatePrepStmtObject.addString(vo.getLbxLoanNoHID());
	    logger.info("IN updateDisbursalDataWithPayment() update cr_loan_dtl query3 ### "+updatePrepStmtObject.printQuery());
	   
	    updatePrepStmtObject.setSql(query2.toString());
	    
		logger.info("IN updateDisbursalDataWithPayment() update cr_loan_dtl query3 ### "+updatePrepStmtObject.printQuery());
		qryListUpdate.add(updatePrepStmtObject);
		logger.info("In updateDisbursalDataWithPayment ....cr_loan_dtl.... update query: "+query2);
	
    if(CommonFunction.checkNull(forStatus).equalsIgnoreCase("F")){
    	StringBuilder queryUpdate=new StringBuilder();
    	queryUpdate.append("upadte cr_loan_disbursal_add_dtl set REC_STATUS='F' where LOAN_DISBURSAL_ID='"+vo.getLbxLoanNoHID()+"'");
    	updatePrepStmtObject =null;
    	updatePrepStmtObject=new PrepStmtObject();
    	updatePrepStmtObject.setSql(queryUpdate.toString());
    	logger.info("queryUpdate:--cr_loan_disbursal_add_dtl--in "+queryUpdate);
    	qryListUpdate.add(queryUpdate.toString());
    }
   
	status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUpdate);
	logger.info("In updateDisbursalDataWithPayment.........update3 status: "+status);
		if(status){
			result="saved";
		}else{
			result="notsaved";	
		}
	

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			loanId = null;
			qryList = null;
			query = null;
			insertPrepStmtObject = null;
			updatePrepStmtObject = null;
			query2 = null;
			qryListUpdate=null;
		}
		return result;
	}
	

	@Override
	public String saveDisbursalPaymentAddDetails(DisbursalMakerVO vo) {
		String paymentFlag="";
		String taFlag="";
		boolean status=false;
		String resultStr="";
		ArrayList qryList = new ArrayList();
		ArrayList qryListUpdate  = new ArrayList();
		StringBuilder query = new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try{
			logger.info("In saveDisbursalPaymentAddDetails:---");
			if(vo.getPaymentFlag()!=null&&vo.getPaymentFlag().equalsIgnoreCase("on")){
				paymentFlag="Y";	
			}else{
				paymentFlag="N";	
			}	
			if(vo.getTaFlag()!=null&&vo.getTaFlag().equalsIgnoreCase("on")){
				taFlag="Y";	
			}else{
				taFlag="N";	
			}	
		if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS")){
			String selQry="select LOAN_CUSTOMER_ID from cr_loan_dtl where loan_id=(select Loan_id from cr_loan_disbursal_dtl where loan_disbursal_id='"+vo.getLoanDisbursalId()+"')";
			String cusId=ConnectionDAO.singleReturn(selQry);
			logger.info("cusId from vo:----"+vo.getLbxBusinessPartnearHID());
			logger.info("cusId:----"+cusId);
			vo.setLbxBusinessPartnearHID(cusId);
			}
			logger.info("cusId from vo after Select:----"+vo.getLbxBusinessPartnearHID());
		query.append("insert into   CR_LOAN_DISBURSAL_ADD_DTL(LOAN_DISBURSAL_ID,");
		query.append("LOAN_ID,DISBURSAL_NO,DISBURSAL_TO,DISBURSAL_TO_ID,DISBURSAL_TO_NAME,");
		query.append("DISBURSAL_AMOUNT,NET_AMOUNT,TOTAL_PAYABLE,");
		query.append("ADJUST_TOTAL_PAYABLE,TOTAL_RECEIVABLE,ADJUST_TOTAL_RECEIVABLE,");
		query.append("PAYMENT_FLAG,TA_FLAG,TA_LOAN_ID,TA_PARTY_NAME,");
		query.append("PMNT_MODE,PMNT_DATE,INSTRUMENT_NO,INSTRUMENT_DATE,BANK_ACCOUNT,");
		query.append("BANK_ID,BRANCH_ID,MICR_CODE,IFSC_CODE,PMNT_AMOUNT,");
		query.append("TDS_AMOUNT,PMNT_REMARK,REC_STATUS,MAKER_ID,MAKER_DATE)");
		query.append("values(?,");//LOAN_DISBURSAL_ID
		query.append("?,");//LOAN_ID
		query.append("?,");//DISBURSAL_NO
		query.append("?,");//DISBURSAL_TO
		query.append("?,");//DISBURSAL_TO_ID
		query.append("?,");//DISBURSAL_TO_NAME
		query.append("?,");//DISBURSAL_AMOUNT
		query.append("?,");//NET_AMOUNT
		query.append("?,");//TOTAL_PAYABLE
		query.append("?,");//ADJUST_TOTAL_PAYABLE
		query.append("?,");//TOTAL_RECEIVABLE
		query.append("?,");//ADJUST_TOTAL_RECEIVABLE
		query.append("?,");//PAYMENT_FLAG
		query.append("?,");//TA_FLAG
		query.append("?,");//TA_LOAN_ID
		query.append("?,");//TA_PARTY_NAME
		query.append("?,");//PMNT_MODE
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'),");//PMNT_DATE
		query.append("?,");//INSTRUMENT_NO
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'),");//INSTRUMENT_DATE
		query.append("?,");//BANK_ACCOUNT
		query.append("?,");//BANK_ID
		query.append("?,");//BRANCH_ID
		query.append("?,");//MICR_CODE
		query.append("?,");//IFSC_CODE
		query.append("?,");//PMNT_AMOUNT
		query.append("?,");//TDS_AMOUNT
		query.append("?,");//PMNT_REMARK
		query.append(" 'P',"); //REC_STATUS
		query.append(" ?,"); //MAKER_ID
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))"); //maker_date
		
		if(CommonFunction.checkNull(vo.getLoanDisbursalId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLoanDisbursalId()).trim());
		
		if(CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxLoanNoHID()).trim());
		
		if(CommonFunction.checkNull(vo.getDisbursalNo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalNo()).trim());
		
		if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalTo()).trim());
		
		if(CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else  
			insertPrepStmtObject.addString((vo.getLbxBusinessPartnearHID()).trim());
		
		if(CommonFunction.checkNull(vo.getBusinessPartnerTypeDesc()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else  
			insertPrepStmtObject.addString((vo.getBusinessPartnerTypeDesc()).trim());
		
		if(CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getNetAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getTotalPayable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTotalPayable()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getAdjustTotalPayable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getAdjustTotalPayable()).trim())).toString());
			
		if(CommonFunction.checkNull(vo.getTotalReceivable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTotalReceivable()).trim())).toString());
			
		if(CommonFunction.checkNull(vo.getAdjustTotalReceivable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getAdjustTotalReceivable()).trim())).toString());
			
		if(CommonFunction.checkNull(paymentFlag).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(paymentFlag).trim()));
		
		if(CommonFunction.checkNull(taFlag).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(taFlag).trim()));
				
		if(CommonFunction.checkNull(vo.getTaLoanNo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTaLoanNo()).trim()));
		
		if(CommonFunction.checkNull(vo.getTaCustomerName()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTaCustomerName()).trim()));
	
		if(CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPaymentMode()).trim()));
	
		if(CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPaymentDate()).trim()));
	
		if(CommonFunction.checkNull(vo.getInstrumentNumber()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
	
		if(CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstrumentDate()).trim()));
	
		if(CommonFunction.checkNull(vo.getBankAccount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getBankAccount()).trim());
		
		if(CommonFunction.checkNull(vo.getLbxBankID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxBankID()).trim());
		
		if(CommonFunction.checkNull(vo.getLbxBranchID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxBranchID()).trim());
		
		if(CommonFunction.checkNull(vo.getMicr()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMicr()).trim());
		
		if(CommonFunction.checkNull(vo.getIfsCode()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getIfsCode()).trim());
		
		if(CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getPaymentAmount()).trim())).toString());
	
		if(CommonFunction.checkNull(vo.getTdsAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTdsAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getRemarks().trim());
		
		if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerId()).trim());
		
		if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerDate()).trim());
		
		insertPrepStmtObject.setSql(query.toString());
		
		logger.info("IN saveDisbursalDataAddDetails() insert query### "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		if(status){
			resultStr="saved";
		}else{
			resultStr="notsaved";
		}
		logger.info("In saveDisbursalPaymentAddDetails    status:---"+status);
		}catch(Exception e){
		e.printStackTrace();	
		}finally{
			
		}
		return resultStr;
	}
	public String updateDisbursalPaymentAddDetails(DisbursalMakerVO vo) {
		String paymentFlag="";
		String taFlag="";
		boolean status=false;
		String resultStr="";
		ArrayList qryList = new ArrayList();
		ArrayList qryListUpdate  = new ArrayList();
		StringBuilder query = new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try{
			logger.info("In updateDisbursalPaymentAddDetails.....");
			if(vo.getPaymentFlag()!=null&&vo.getPaymentFlag().equalsIgnoreCase("on")){
				paymentFlag="Y";	
			}else{
				paymentFlag="N";	
			}	
			if(vo.getTaFlag()!=null&&vo.getTaFlag().equalsIgnoreCase("on")){
				taFlag="Y";	
			}else{
				taFlag="N";	
			}
			if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase("CS")){
			String selQry="select LOAN_CUSTOMER_ID from cr_loan_dtl where loan_id=(select Loan_id from cr_loan_disbursal_dtl where loan_disbursal_id='"+vo.getLoanDisbursalId()+"')";
			String cusId=ConnectionDAO.singleReturn(selQry);
			logger.info("cusId from vo:----"+vo.getLbxBusinessPartnearHID());
			logger.info("cusId:----"+cusId);
			vo.setLbxBusinessPartnearHID(cusId);
			}
			logger.info("cusId from vo after Select:----"+vo.getLbxBusinessPartnearHID());
		query.append("Update CR_LOAN_DISBURSAL_ADD_DTL set ");//LOAN_DISBURSAL_ID,
		query.append("DISBURSAL_TO=?,DISBURSAL_TO_ID=?,DISBURSAL_TO_NAME=?,");
		query.append("DISBURSAL_AMOUNT=?,NET_AMOUNT=?,TOTAL_PAYABLE=?,");
		query.append("ADJUST_TOTAL_PAYABLE=?,TOTAL_RECEIVABLE=?,ADJUST_TOTAL_RECEIVABLE=?,");
		query.append("PAYMENT_FLAG=?,TA_FLAG=?,TA_LOAN_ID=?,TA_PARTY_NAME=?,");
		query.append("PMNT_MODE=?,PMNT_DATE=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'),INSTRUMENT_NO=?,INSTRUMENT_DATE=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormat+"'),BANK_ACCOUNT=?,");
		query.append("BANK_ID=?,BRANCH_ID=?,MICR_CODE=?,IFSC_CODE=?,PMNT_AMOUNT=?,");
		query.append("TDS_AMOUNT=?,REC_STATUS='P',PMNT_REMARK=?,MAKER_ID=?,MAKER_DATE= ");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
		query.append(" where LOAN_DISBURSAL_ADD_ID=?");
		//LOAN_ID
		
	
		
		if(CommonFunction.checkNull(vo.getDisbursalTo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getDisbursalTo()).trim());
		
		if(CommonFunction.checkNull(vo.getLbxBusinessPartnearHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else  
			insertPrepStmtObject.addString((vo.getLbxBusinessPartnearHID()).trim());
		
		if(CommonFunction.checkNull(vo.getBusinessPartnerTypeDesc()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else  
			insertPrepStmtObject.addString((vo.getBusinessPartnerTypeDesc()).trim());
		
		if(CommonFunction.checkNull(vo.getDisbursalAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getDisbursalAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getNetAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getNetAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getTotalPayable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTotalPayable()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getAdjustTotalPayable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getAdjustTotalPayable()).trim())).toString());
			
		if(CommonFunction.checkNull(vo.getTotalReceivable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTotalReceivable()).trim())).toString());
			
		if(CommonFunction.checkNull(vo.getAdjustTotalReceivable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getAdjustTotalReceivable()).trim())).toString());
			
		if(CommonFunction.checkNull(paymentFlag).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(paymentFlag).trim()));
		
		if(CommonFunction.checkNull(taFlag).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(taFlag).trim()));
				
		if(CommonFunction.checkNull(vo.getTaLoanNo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTaLoanNo()).trim()));
		
		if(CommonFunction.checkNull(vo.getTaCustomerName()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTaCustomerName()).trim()));
	
		if(CommonFunction.checkNull(vo.getPaymentMode()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPaymentMode()).trim()));
	
		if(CommonFunction.checkNull(vo.getPaymentDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPaymentDate()).trim()));
	
		if(CommonFunction.checkNull(vo.getInstrumentNumber()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
	
		if(CommonFunction.checkNull(vo.getInstrumentDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstrumentDate()).trim()));
	
		if(CommonFunction.checkNull(vo.getBankAccount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getBankAccount()).trim());
		
		if(CommonFunction.checkNull(vo.getLbxBankID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxBankID()).trim());
		
		if(CommonFunction.checkNull(vo.getLbxBranchID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLbxBranchID()).trim());
		
		if(CommonFunction.checkNull(vo.getMicr()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMicr()).trim());
		
		if(CommonFunction.checkNull(vo.getIfsCode()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getIfsCode()).trim());
		
		if(CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getPaymentAmount()).trim())).toString());
	
		if(CommonFunction.checkNull(vo.getTdsAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTdsAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getRemarks()).trim());
		
		if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerId()).trim());
		
		if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getMakerDate()).trim());
		
		if(CommonFunction.checkNull(vo.getLoanDisbursalAddId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((vo.getLoanDisbursalAddId()).trim());
		
		insertPrepStmtObject.setSql(query.toString());
		
		logger.info("IN updateDisbursalPaymentAddDetails() update query### "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		if(status){
			resultStr="saved";
		}else{
			resultStr="notsaved";
		}
		
		}catch(Exception e){
		e.printStackTrace();	
		}finally{
			
		}
		return resultStr;
	}
    public ArrayList selectAddDetailsForUpdate(DisbursalMakerVO vo){
    	ArrayList mainList   = new ArrayList();
		ArrayList subList    = new ArrayList();
		StringBuilder query  = new StringBuilder();
		StringBuilder query2 = new StringBuilder();
		ArrayList returnList = new ArrayList();
		String disbursedAmountStr="";
    	try{
    		
    		 query2.append("select SUM(isnull(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+vo.getLbxLoanNoHID()+"' AND REC_STATUS='A'");
			  //logger.info("In getDisbursalValues : "+query2);
			  disbursedAmountStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
			  logger.info("In selectAddDetailsForUpdate.....disbursedAmount: "+disbursedAmountStr);
			  String table="";
    		if(CommonFunction.checkNull(vo.getFrom()).equalsIgnoreCase("A")){
    			table="cr_loan_disbursal_dtl";
    		}else{
    			table="cr_loan_disbursal_dtl_temp";
    		}
    		query.append("SELECT a.loan_no, b.customer_name, a.loan_loan_amount, ");
    		query.append(dbo);
    		query.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), c.product_desc, d.scheme_desc,");
    		query.append(" a.LOAN_ID,e.disbursal_no, e.disbursal_description,");
    		query.append(dbo);
    		query.append("DATE_FORMAT(e.disbursal_date,'"+dateFormat+"'),e.disbursal_amount,e.loan_due_day,");
    		query.append(dbo);
    		query.append("DATE_FORMAT(e.NEXT_DUE_DATE,'"+dateFormat+"'),e.disbursal_flag,");
    		query.append(" e.CUR_MONTH_EMI,e.NEXT_MONTH_EMI,");
    		query.append(dbo);
    		query.append("DATE_FORMAT(e.REPAY_EFF_DATE,'"+dateFormat+"'),");
    		query.append(dbo);
			query.append("DATE_FORMAT(e.PENAL_INT_CALC_DATE,'"+dateFormat+"'),");
			query.append(" e.DISBURSAL_TO,e.DISBURSAL_TO_ID,e.DISBURSAL_TO_NAME,e.NET_AMOUNT,e.ADJUST_TOTAL_PAYABLE,e.SHORT_AMOUNT_ADJUSTED,");
			query.append(" e.PAYMENT_FLAG,e.TA_LOAN_ID,(select loan_no from cr_loan_dtl cld where cld.loan_id=e.TA_LOAN_ID) TA_LOAN_NO,");
			query.append(" e.TA_PARTY_NAME,e.PMNT_MODE,");
			query.append(dbo);
			query.append("DATE_FORMAT(e.PMNT_DATE,'"+dateFormat+"'),");
			query.append(" e.INSTRUMENT_NO,");
			query.append(dbo);
			query.append("DATE_FORMAT(e.INSTRUMENT_DATE,'"+dateFormat+"'),e.BANK_ACCOUNT,e.BANK_ACCOUNT as Bank_acc,");
			//query.append("(Select BANK_ACCOUNT from com_bank_accounts_m acc where acc.BANK_ACCOUNT_ID=e.BANK_ACCOUNT) Bank_account,");
			query.append("e.BANK_ID,(Select BANK_NAME from com_bank_m bank where bank.BANK_ID=e.BANK_ID) Bank_Name,");
			query.append("e.BRANCH_ID,(Select BANK_BRANCH_NAME from com_bankbranch_m br where br.BANK_BRANCH_ID=e.BRANCH_ID) Branch_Name,");
			query.append(" e.MICR_CODE,e.IFSC_CODE,e.PMNT_AMOUNT,e.TDS_AMOUNT,e.PMNT_REMARK,a.loan_repayment_type,");
			query.append(" (select count(PDC_LOAN_ID) from cr_pdc_instrument_dtl where  PDC_STATUS='A' and PDC_PURPOSE='INSTALLMENT' and PDC_LOAN_ID='"+vo.getLbxLoanNoHID()+"'),e.LOAN_DISBURSAL_ID,e.PAY_TO,e.PAYEE_NAME,e.REMARKS,");
			query.append("(SELECT ISNULL(SUM(ISNULL(DISBURSAL_AMOUNT,0)),0)FROM CR_LOAN_DISBURSAL_DTL_TEMP WHERE LOAN_ID ='"+vo.getLbxLoanNoHID()+"' AND e.LOAN_DISBURSAL_ID !='"+vo.getLoanDisbursalId()+"' AND REC_STATUS='P')as disbursedAmountTemp, ");
			query.append("  (select VALUE from generic_master where  GENERIC_KEY='BPTYPE'  and DESCRIPTION=e.PAY_TO)   from  cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, "+table+" e");
			query.append(" where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.LOAN_DISBURSAL_ID='"+vo.getLoanDisbursalId()+"'");
    		logger.info("in selectAddDetailsForUpdate Select query :--------"+query.toString());
    		mainList=ConnectionDAO.sqlSelect(query.toString());
    		int size=mainList.size();
    		if(size!=0){
				for(int i=0;i<size;i++){

				subList=(ArrayList)mainList.get(i);
				int subSize=subList.size();
				if(subSize>0){
					DisbursalMakerVO disVo=new DisbursalMakerVO();
					disVo.setLoanNo(CommonFunction.checkNull(subList.get(0)));
					disVo.setCustomerName(CommonFunction.checkNull(subList.get(1)));
					if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase("")){
						disVo.setLoanAmt("0.00");
					}else{
						Number loanAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
						disVo.setLoanAmt(myFormatter.format(loanAmount));
					}
					
					disVo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(3)).trim()));
					disVo.setProduct((CommonFunction.checkNull(subList.get(4)).trim()));
					disVo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
					disVo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(6)).trim()));
					disVo.setDisbursalNo((CommonFunction.checkNull(subList.get(7)).trim()));
					disVo.setDisbursalDescription((CommonFunction.checkNull(subList.get(8)).trim()));
					disVo.setDisbursalDate((CommonFunction.checkNull(subList.get(9)).trim()));
					logger.info("setDisbursalAmount:======"+subList.get(10));
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase(""))
					{
						disVo.setDisbursalAmount("0.0");
					}
					else
					{
						Number disbursalAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim()));
						disVo.setDisbursalAmount(myFormatter.format(disbursalAmount));
					}
					
					disVo.setCycleDateValue((CommonFunction.checkNull(subList.get(11)).trim()));
					disVo.setNextDueDate((CommonFunction.checkNull(subList.get(12)).trim()));
					disVo.setFinalDisbursal((CommonFunction.checkNull(subList.get(13)).trim()));
					logger.info("setFinalDisbursal:======"+subList.get(13));
					if((CommonFunction.checkNull(subList.get(14)).trim()).equalsIgnoreCase(""))
						disVo.setCurrentMonthEMI("");
					else{
						Number amount = myFormatter.parse((CommonFunction.checkNull(subList.get(14)).trim()));
						disVo.setCurrentMonthEMI(myFormatter.format(amount));
					}
					if((CommonFunction.checkNull(subList.get(15)).trim()).equalsIgnoreCase(""))
						disVo.setPreEMINextMonth("");
					else{
						Number amount = myFormatter.parse((CommonFunction.checkNull(subList.get(15)).trim()));
						disVo.setPreEMINextMonth(myFormatter.format(amount));
					}
					disVo.setRepayEffDate(CommonFunction.checkNull(subList.get(16)));
					disVo.setPenalIntCalcDate(CommonFunction.checkNull(subList.get(17)));
					disVo.setDisbursalTo((CommonFunction.checkNull(subList.get(18)).trim()));
					disVo.setLbxBusinessPartnearHID(CommonFunction.checkNull(subList.get(19)).trim());
					disVo.setBusinessPartnerTypeDesc(CommonFunction.checkNull(subList.get(20)).trim());
					if((CommonFunction.checkNull(subList.get(21)).trim()).equalsIgnoreCase("")){
						disVo.setNetAmount("0.00");
					}else{
						Number netAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(21)).trim()));
						disVo.setNetAmount(myFormatter.format(netAmount));
					}
					if((CommonFunction.checkNull(subList.get(22)).trim()).equalsIgnoreCase("")){
						disVo.setAdjustTotalPayable("0.00");
					}else{
						Number netAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(22)).trim()));
						disVo.setAdjustTotalPayable(myFormatter.format(netAmount));
					}
					if((CommonFunction.checkNull(subList.get(23)).trim()).equalsIgnoreCase("")){
						disVo.setAdjustTotalReceivable("0.00");
					}else{
						Number netAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(23)).trim()));
						disVo.setAdjustTotalReceivable(myFormatter.format(netAmount));
					}
					disVo.setPaymentFlag(CommonFunction.checkNull(subList.get(24)));
					disVo.setLbxTaLoanNoHID(CommonFunction.checkNull(subList.get(25)));
					disVo.setTaLoanNo(CommonFunction.checkNull(subList.get(26)));
					disVo.setTaCustomerName(CommonFunction.checkNull(subList.get(27)));
					disVo.setPaymentMode(CommonFunction.checkNull(subList.get(28)));
					disVo.setPaymentDate(CommonFunction.checkNull(subList.get(29)));
					disVo.setInstrumentNumber(CommonFunction.checkNull(subList.get(30)));
					disVo.setInstrumentDate(CommonFunction.checkNull(subList.get(31)));
					disVo.setLbxbankAccountID(CommonFunction.checkNull(subList.get(32)));
					disVo.setBankAccount(CommonFunction.checkNull(subList.get(33)));
					disVo.setLbxBankID(CommonFunction.checkNull(subList.get(34)));
					disVo.setBank(CommonFunction.checkNull(subList.get(35)));
					disVo.setLbxBranchID(CommonFunction.checkNull(subList.get(36)));
					disVo.setBranch(CommonFunction.checkNull(subList.get(37)));
					disVo.setMicr(CommonFunction.checkNull(subList.get(38)));
					disVo.setIfsCode(CommonFunction.checkNull(subList.get(39)));
					if((CommonFunction.checkNull(subList.get(40)).trim()).equalsIgnoreCase(""))
					{
						disVo.setPaymentAmount("0.0");
					}
					else
					{
						Number payAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(40)).trim()));
						disVo.setPaymentAmount(myFormatter.format(payAmount));
					}
					
					if((CommonFunction.checkNull(subList.get(41)).trim()).equalsIgnoreCase(""))
					{
						disVo.setTdsAmount("0.0");
					}
					else
					{
						Number tdsAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(41)).trim()));
						disVo.setTdsAmount(myFormatter.format(tdsAmount));
					}
					
					disVo.setRemarks((CommonFunction.checkNull(subList.get(42)).trim()));
					disVo.setRepayMode((CommonFunction.checkNull(subList.get(43)).trim()));
					logger.info("setRepayMode:======"+subList.get(43));
					disVo.setPdcDepositCount((CommonFunction.checkNull(subList.get(44)).trim()));
					disVo.setLoanDisbursalId((CommonFunction.checkNull(subList.get(45)).trim()));
					ArrayList recList=getTotalPayableReceiable(vo.getLbxLoanNoHID(),disVo.getDisbursalTo());
					disVo.setTotalPayable(recList.get(0).toString());
					disVo.setTotalReceivable(recList.get(1).toString());
					disVo.setTotalReceivableCustomer(recList.get(2).toString());
					if(CommonFunction.checkNull(disbursedAmountStr).equalsIgnoreCase(""))
					{
						disVo.setDisbursedAmount("0.0");
					}
					else
					{
						Number disb = myFormatter.parse((CommonFunction.checkNull(disbursedAmountStr).trim()));
						disVo.setDisbursedAmount(myFormatter.format(disb));
					}
					
					disVo.setPayTo((CommonFunction.checkNull(subList.get(46)).trim()));
					disVo.setPayeeName((CommonFunction.checkNull(subList.get(47)).trim()));
					disVo.setAuthorRemarks((CommonFunction.checkNull(subList.get(48)).trim()));
					disVo.setDisbursedAmountTemp((CommonFunction.checkNull(subList.get(49)).trim()));
					disVo.setLbxpayTo((CommonFunction.checkNull(subList.get(50)).trim()));
					returnList.add(disVo);	
				 }
				}
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return returnList;
    }
    
    public ArrayList selectAddDetailsList(DisbursalMakerVO vo,String from){
    	ArrayList mainList = new ArrayList();
		ArrayList subList  = new ArrayList();
		StringBuilder query = new StringBuilder();
		ArrayList returnList  = new ArrayList();
		String recStatus="";
    	try{
    		/*query.append("SELECT LOAN_DISBURSAL_ADD_ID,LOAN_ID,DISBURSAL_NO,(select DESCRIPTION from generic_master where  GENERIC_KEY='BPTYPE'  and value=DISBURSAL_TO),");
    		query.append("DISBURSAL_TO_NAME,NET_AMOUNT,if(PAYMENT_FLAG='Y','YES','NO'),if(TA_FLAG='Y','YES','NO'),LOAN_DISBURSAL_ID,DISBURSAL_AMOUNT");
    		query.append(" from cr_loan_disbursal_add_dtl a where  ");
    		query.append("  a.LOAN_DISBURSAL_ID='"+vo.getLoanDisbursalId()+"'");*/
    		String table="";
    		if(!CommonFunction.checkNull(from).equalsIgnoreCase("A")){
    			recStatus="P";
    			table="cr_loan_disbursal_dtl_temp";
    		}else{
    			recStatus="F";
    			table="cr_loan_disbursal_dtl";
    		}
    		query.append("Select LOAN_DISBURSAL_ID,a.LOAN_ID,b.LOAN_NO,DISBURSAL_NO,DISBURSAL_DESCRIPTION,");
    		query.append(dbo);
    		query.append("DATE_FORMAT(DISBURSAL_DATE ,'"+dateFormat+"') as DISBURSAL_DATE,");
    		query.append("DISBURSAL_AMOUNT ,DISBURSAL_FLAG,(select DESCRIPTION from generic_master where  GENERIC_KEY='BPTYPE'  and value=DISBURSAL_TO)");
    		query.append("from "+table+" a join cr_loan_dtl b on a.loan_id=b.loan_id ");
    		query.append(" where a.loan_id='"+vo.getLbxLoanNoHID()+"' and a.REC_STATUS='"+recStatus+"' ");
    		if(recStatus.equalsIgnoreCase("F")){
    			query.append("and DISBURSAL_BATCH_ID='"+vo.getDisbursalBatchId()+"'");	
    		}
    		
    		logger.info("in selectAddDetailsList Select query :--------"+query.toString());
    		mainList=ConnectionDAO.sqlSelect(query.toString());
    		int size=mainList.size();
    		if(size!=0){
				for(int i=0;i<size;i++){

				subList=(ArrayList)mainList.get(i);
				int subSize=subList.size();
				if(subSize>0){
					DisbursalMakerVO disVo=new DisbursalMakerVO();
					disVo.setLoanDisbursalId(CommonFunction.checkNull(subList.get(0)));
					if(!CommonFunction.checkNull(from).equalsIgnoreCase("A")){
						disVo.setLoanNo("<a href=# onclick=openEditDisbursalDetails('"+CommonFunction.checkNull(subList.get(0))+"','"+CommonFunction.checkNull(subList.get(1))+"','"+CommonFunction.checkNull(subList.get(3))+"','M') >"
			  					+ (CommonFunction.checkNull(subList.get(2)).trim()) + "</a>");	
					}else{
						disVo.setLoanNo("<a href=# onclick=openEditDisbursalDetails('"+CommonFunction.checkNull(subList.get(0))+"','"+CommonFunction.checkNull(subList.get(1))+"','"+CommonFunction.checkNull(subList.get(3))+"','A') >"
			  					+ (CommonFunction.checkNull(subList.get(2)).trim()) + "</a>");	
					}
					
					disVo.setLbxLoanNoHID(CommonFunction.checkNull(subList.get(1)));
                    disVo.setDisbursalNo((CommonFunction.checkNull(subList.get(3)).trim()));
                  // 
                   disVo.setDisbursalDescription(CommonFunction.checkNull(subList.get(4)).trim());
                   disVo.setDisbursalDate(CommonFunction.checkNull(subList.get(5)).trim());
					if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
					{
						disVo.setDisbursalAmount("0.00");
					}
					else
					{
						Number netAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim()));
						disVo.setDisbursalAmount(myFormatter.format(netAmount));
					}
					disVo.setFinalDisbursal(CommonFunction.checkNull(subList.get(7)).trim());
					 disVo.setDisbursalTo((CommonFunction.checkNull(subList.get(8)).trim()));
					returnList.add(disVo);	
				 }
				}
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return returnList;
    }
    
   public ArrayList getTotalPayableReceiable(String loanid,String customerType){
	  StringBuilder qryPayable=new StringBuilder();
	  StringBuilder qryReceiable=new StringBuilder();
	  StringBuilder qryReceiableCustomer=new StringBuilder();
	  ArrayList returnList=new ArrayList();
	  String totalpayable="";
	  String totalReceiable="";
	  String totalReceiveable="";
	  String flagVal="";
	  String totalReceiableAmount="";
	  String query ="";
	  String totalReceivedAmount="";
	  
	   try{
		   qryPayable.append("SELECT sum((ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))) totalBalance FROM cr_txnadvice_dtl "); 
		   qryPayable.append(" WHERE REC_STATUS in('A','F')  AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0  AND");
		   qryPayable.append(" ADVICE_TYPE='P' AND LOAN_ID='"+loanid+"' AND BP_TYPE ='"+customerType+"'");
		   logger.info(" Query String of total payable:----"+qryPayable.toString());
		   totalpayable=CommonFunction.checkNull(ConnectionDAO.singleReturn(qryPayable.toString()));
		   if(totalpayable.equalsIgnoreCase("")){
			   returnList.add("0.0");
		   }else{
			   Number payAmount = myFormatter.parse((CommonFunction.checkNull(totalpayable).trim()));
				returnList.add(myFormatter.format(payAmount)); 
		   }
		   
		   qryReceiable.append("SELECT sum((ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))) totalBalance FROM cr_txnadvice_dtl "); 
		   qryReceiable.append(" WHERE REC_STATUS in('A','F')  AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0  AND");
		   qryReceiable.append(" ADVICE_TYPE='R' AND LOAN_ID='"+loanid+"' AND ISNULL(CHARGE_CODE_ID,0)<>8 AND BP_TYPE ='"+customerType+"'");
		   logger.info(" query String of total qryReceiable:----"+qryReceiable.toString());
		   totalReceiable=ConnectionDAO.singleReturn(qryReceiable.toString());
		   if(totalReceiable.equalsIgnoreCase("")){
			   returnList.add("0.0");
		   }else{
			   Number recAmount = myFormatter.parse((CommonFunction.checkNull(totalReceiable).trim()));
				returnList.add(myFormatter.format(recAmount)); 
		   }
		   // Change start by Prashant
		   totalReceiveable="Select parameter_value from parameter_mst where parameter_key='TOTAL_RECEIVABLE_CURRENT_SHORT_PAY'";
		   logger.info("short Receivable status :----"+totalReceiveable);
		   flagVal=CommonFunction.checkNull(ConnectionDAO.singleReturn(totalReceiveable));
		   if(CommonFunction.checkNull(flagVal).equalsIgnoreCase("Y") )
		   {
			   qryReceiableCustomer.append("SELECT sum((ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))) totalBalance FROM cr_txnadvice_dtl "); 
			   qryReceiableCustomer.append(" WHERE REC_STATUS in('A','F')  AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0  AND");
			   qryReceiableCustomer.append(" ADVICE_TYPE='R' AND LOAN_ID='"+loanid+"' AND BP_TYPE ='CS' ");
			   logger.info(" query String of total qryReceiableCustomer:----"+qryReceiableCustomer.toString());
			   totalReceiableAmount=ConnectionDAO.singleReturn(qryReceiableCustomer.toString());
			   if(totalReceiableAmount.equalsIgnoreCase("")){
				   returnList.add("0.0");
			   }else{
				   query = "select SUM(SHORT_AMOUNT_ADJUSTED) from cr_loan_disbursal_dtl_temp where loan_id='"+loanid+"' and disbursal_to='CS'";
				   logger.info("query for totalReceiableAmount received : " + query);
				   totalReceivedAmount=ConnectionDAO.singleReturn(query);
				   if(!CommonFunction.checkNull(totalReceivedAmount).equalsIgnoreCase(""))
				   {
					   totalReceiableAmount=Double.toString((Double.parseDouble(totalReceiableAmount)-Double.parseDouble(totalReceivedAmount)));
				   }
				    Number recAmount = myFormatter.parse((CommonFunction.checkNull(totalReceiableAmount).trim()));
					returnList.add(myFormatter.format(recAmount)); 
			   }
		   }
		   else
		   {
			   returnList.add("0.0");
		   }
		   // Change end by Prashant
	   }catch(Exception e){
		   
	   }
	   finally
	   {
		   qryPayable=null;
		   qryReceiable=null;
		   qryReceiableCustomer=null;
		   loanid=null;
		   customerType=null;
		   totalpayable=null;
		   totalReceiable=null;
	       totalReceiveable=null;
		   flagVal=null;
		   totalReceiableAmount=null;
		   query =null;
		   totalReceivedAmount=null;
	   }
	   return returnList;
   }
   
   public ArrayList getTotalShortPay(String loanid,String customerType){
		  StringBuilder qryReceiable=new StringBuilder();
		  ArrayList returnList=new ArrayList();
		   try{			   
			   qryReceiable.append(" select SHORT_AMOUNT_ADJUSTED from cr_loan_disbursal_dtl_temp AND LOAN_ID='"+loanid+"' AND DISBURSAL_TO ='"+customerType+"'");
			   logger.info(" query String of total getTotalShortPay:----"+qryReceiable.toString());
			   String totalReceiable=ConnectionDAO.singleReturn(qryReceiable.toString());
			   if(totalReceiable.equalsIgnoreCase("")){
				   returnList.add("0.0");
			   }else{
				   Number recAmount = myFormatter.parse((CommonFunction.checkNull(totalReceiable).trim()));
					returnList.add(myFormatter.format(recAmount)); 
			   }
		   }catch(Exception e){
			   
		   }
		   return returnList;
	   }
   
   @Override
   public String deleteAddDetails(DisbursalMakerVO vo,String checkedStr) {
	   String resultStr="";
	 
		ArrayList qryList = new ArrayList();
		StringBuilder query = new StringBuilder();
		PrepStmtObject deletePrepStmtObject = new PrepStmtObject();
		try{
			String addId[]=vo.getAddId();
			logger.info("addId:----------"+addId);
			String addIdStr="";
			/*
			for(int i=0;i<addId.length;i++){
				addIdStr=addIdStr+addId[i]+",";
			}*/
			if(!CommonFunction.checkNull(checkedStr).equalsIgnoreCase("")){
			addIdStr=checkedStr.substring(0,checkedStr.length()-1);
			logger.info("addIdStr:----------"+addIdStr);
			String querySel="Select count(1) from cr_loan_disbursal_dtl_temp where  LOAN_DISBURSAL_ID in ("+addIdStr+")";
			String deleteFlag=ConnectionDAO.singleReturn(querySel);
			if(!CommonFunction.checkNull(deleteFlag).equalsIgnoreCase("0")){
				query.append(" Delete  from cr_loan_disbursal_dtl_temp where  LOAN_DISBURSAL_ID in ("+addIdStr+")");
				deletePrepStmtObject.setSql(query.toString());
				qryList.add(deletePrepStmtObject);
				boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("status:----------"+status);
				if(status){
					resultStr="deleted";
				}else{
					resultStr="notdeleted";
				}
			 }
			}
			
		}catch(Exception e){
		e.printStackTrace();	
		}
   	return resultStr;
   }
   
   public ArrayList getLoanAndDisburdesAmount(DisbursalMakerVO vo){
	   StringBuilder  query1=new StringBuilder();
	   StringBuilder  query2=new StringBuilder();
	   String disbursedAmountStr="";
	   String loanAmount="";
	   ArrayList returnList=new ArrayList();
	   try{
		    query1.append("select SUM(isnull(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+vo.getLbxLoanNoHID()+"' AND REC_STATUS='A'");
		    logger.info("In getDisbursalValues : "+query1);
			disbursedAmountStr=CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
			logger.info("In selectDisbursalData.....disbursedAmount: "+disbursedAmountStr);  
			query2.append("Select loan_loan_amount FROM CR_LOAN_DTL WHERE LOAN_ID = '"+vo.getLbxLoanNoHID()+"' AND REC_STATUS='A'");
			logger.info("In getDisbursalValues : "+query2);
			loanAmount=CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
			DisbursalMakerVO nvo=new DisbursalMakerVO();
			nvo.setDisbursedAmount(disbursedAmountStr);
			nvo.setLoanAmt(loanAmount);
			returnList.add(nvo);
	   }catch(Exception e){
		   
	   }
	   return returnList;
   }
   public String forwardDisbursal(DisbursalMakerVO vo,String loanDisbursalId[]){
	String resultStr="";
	StringBuilder bufInsSql=new StringBuilder();
	String loanAddIdstr="";
	ArrayList qryList = new ArrayList();
	boolean status=false;
	boolean updateStatus=false;
	
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	   try{
		   String batchQuery="Select VALUE from generate_sequence_tbl  where SEQ_KEY='DISBURSAL_BATCH_ID' ";
		   String batchValueStr=ConnectionDAO.singleReturn(batchQuery);
		   int batchValue=Integer.parseInt(batchValueStr);
		   batchValue++;
		   logger.info("batchValue:--------------------"+batchValue);
		   
		  for(int i=0;i<loanDisbursalId.length;i++){
			  loanAddIdstr=loanAddIdstr+loanDisbursalId[i]+",";
		   }
		  
		  	String DISBURSAL_NO_MAX="0";
			String query=" select MAX(DISBURSAL_NO) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID='"+vo.getLbxLoanNoHID()+"' ";
			logger.info("IN forwardDisbursal() MAX(DISBURSAL_NO)query### "+query);
			DISBURSAL_NO_MAX=ConnectionDAO.singleReturn(query);	
			if(CommonFunction.checkNull(DISBURSAL_NO_MAX).trim().equalsIgnoreCase(""))
				DISBURSAL_NO_MAX="0";
		  
			for(int i=0;i<loanDisbursalId.length;i++)
			{
				int DISBURSAL_NO=Integer.parseInt(DISBURSAL_NO_MAX)+i+1;
				insertPrepStmtObject=null;
				StringBuilder bufInsSql1=new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql1.append("UPDATE CR_LOAN_DISBURSAL_DTL_TEMP SET DISBURSAL_NO='"+DISBURSAL_NO+"'");
				bufInsSql1.append(" WHERE LOAN_DISBURSAL_ID='"+loanDisbursalId[i]+"'");
				insertPrepStmtObject.setSql(bufInsSql1.toString());
				logger.info("IN forwardDisbursal() update DISBURSAL_NO query### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				updateStatus =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			}			
			bufInsSql=null;
			insertPrepStmtObject=null;
			bufInsSql=new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			
		  loanAddIdstr=	loanAddIdstr.substring(0, loanAddIdstr.length()-1);   
		    bufInsSql.append("Insert into cr_loan_disbursal_dtl(loan_id, disbursal_no, disbursal_description,");
			bufInsSql.append("disbursal_date,disbursal_amount,loan_due_day,NEXT_DUE_DATE,disbursal_flag,");
			bufInsSql.append("rec_status,maker_id,maker_date,CUR_MONTH_EMI,NEXT_MONTH_EMI,REPAY_EFF_DATE,");
			bufInsSql.append("old_repay_eff_Date,old_LOAN_DUE_DAY,old_NEXT_DUE_DATE,PENAL_INT_CALC_DATE,");
			bufInsSql.append("DISBURSAL_TO,DISBURSAL_TO_ID,DISBURSAL_TO_NAME,NET_AMOUNT,ADJUST_TOTAL_PAYABLE,");
			bufInsSql.append("PAYMENT_FLAG,TA_LOAN_ID,TA_PARTY_NAME,PMNT_MODE,PMNT_DATE,");
			bufInsSql.append("INSTRUMENT_NO,INSTRUMENT_DATE,BANK_ACCOUNT,BANK_ID,BRANCH_ID,MICR_CODE,IFSC_CODE,");
			bufInsSql.append("PMNT_AMOUNT,TDS_AMOUNT,PMNT_REMARK,SHORT_AMOUNT_ADJUSTED,DISBURSAL_BATCH_ID,PAY_TO,PAYEE_NAME,DEFAULT_BRANCH)");
			bufInsSql.append(" Select loan_id, disbursal_no, disbursal_description,");
			bufInsSql.append("disbursal_date,disbursal_amount,loan_due_day,NEXT_DUE_DATE,disbursal_flag,");
			bufInsSql.append("'F','"+vo.getMakerId()+"',");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE('"+vo.getMakerDate()+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("CUR_MONTH_EMI,NEXT_MONTH_EMI,REPAY_EFF_DATE,");
			bufInsSql.append("old_repay_eff_Date,old_LOAN_DUE_DAY,old_NEXT_DUE_DATE,PENAL_INT_CALC_DATE,");
			bufInsSql.append("DISBURSAL_TO,DISBURSAL_TO_ID,DISBURSAL_TO_NAME,NET_AMOUNT,ADJUST_TOTAL_PAYABLE,");
			bufInsSql.append("PAYMENT_FLAG,TA_LOAN_ID,TA_PARTY_NAME,PMNT_MODE,PMNT_DATE,");
			bufInsSql.append("INSTRUMENT_NO,INSTRUMENT_DATE,BANK_ACCOUNT,BANK_ID,BRANCH_ID,MICR_CODE,IFSC_CODE,");
			bufInsSql.append("PMNT_AMOUNT,TDS_AMOUNT,PMNT_REMARK,SHORT_AMOUNT_ADJUSTED,'"+batchValue+"',PAY_TO,PAYEE_NAME,DEFAULT_BRANCH FROM cr_loan_disbursal_dtl_temp ");
			bufInsSql.append(" where LOAN_DISBURSAL_ID in ("+loanAddIdstr+")");
			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			logger.info("IN forwardDisbursal() Insert query### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			bufInsSql=null;
			insertPrepStmtObject=null;
			 bufInsSql=new StringBuilder();
			 insertPrepStmtObject = new PrepStmtObject();
			 
			bufInsSql.append("DELETE from cr_loan_disbursal_dtl_temp");
			bufInsSql.append(" where LOAN_DISBURSAL_ID in ("+loanAddIdstr+")");
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN forwardDisbursal() Delete query### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			
			 bufInsSql=null;
			 insertPrepStmtObject=null;
			 bufInsSql=new StringBuilder();
			 insertPrepStmtObject = new PrepStmtObject();
			 bufInsSql.append("UPDATE generate_sequence_tbl set VALUE='"+batchValue+"' where SEQ_KEY='DISBURSAL_BATCH_ID' ");
			 insertPrepStmtObject.setSql(bufInsSql.toString());
			 logger.info("IN forwardDisbursal() Update Sequence query### "+insertPrepStmtObject.printQuery()); 
			 qryList.add(insertPrepStmtObject);
			 status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			if(status){
				resultStr="saved";	
				//Nishant space starts
				String disbIdQuery = "SELECT MAX(LOAN_DISBURSAL_ID) FROM CR_LOAN_DISBURSAL_DTL";
				String disbId = ConnectionDAO.singleReturn(disbIdQuery);
				bufInsSql=null;
				insertPrepStmtObject=null;
				qryList.clear();
				bufInsSql=new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql.append("UPDATE CR_RESCH_INSTALLMENT_PLAN SET LOAN_DISBURSAL_ID='"+disbId+"' WHERE LOAN_DISBURSAL_ID_TEMP in ("+loanAddIdstr+") AND LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN forwardDisbursal() Update CR_RESCH_INSTALLMENT_PLAN query### "+insertPrepStmtObject.printQuery()); 
				qryList.add(insertPrepStmtObject);
				
				bufInsSql=null;
				insertPrepStmtObject=null;
				bufInsSql=new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql.append("UPDATE CR_LOAN_CHARGE_PLAN_DTL SET LOAN_DISBURSAL_ID='"+disbId+"' WHERE LOAN_DISBURSAL_ID_TEMP in ("+loanAddIdstr+") AND LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN forwardDisbursal() Update CR_LOAN_CHARGE_PLAN_DTL query### "+insertPrepStmtObject.printQuery()); 
				qryList.add(insertPrepStmtObject);
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				//Nishant space ends
			}else{
				resultStr="notsaved";	
			}
			
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	   return resultStr;
   }
   public String generateRepyScheduleDisbursalPayment(String loanId,String makerId,String finalDisbursalFlag,String txnType) {
		boolean status=false;
		//CallableStatement cst=null;
		//Connection con=ConnectionDAO.getConnection();
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		String installCount="";
		String flatRate="";
		String effRate="";
		String mktIrr1="";
		String mktIrr2="";
		String procval="";
		try {
//			con.setAutoCommit(false);
			logger.info("In Generate_Repayment_schedule Procedure: ");
			in.add(txnType);
   	    in.add(loanId);
   	    in.add(finalDisbursalFlag);
   	    in.add(makerId);
   	    out.add(installCount);
   	    out.add(flatRate);
   	    out.add(effRate);
   	    out.add(mktIrr1);
   	    out.add(mktIrr2);
   	    out.add(s1);
   	    out.add(s2);
   	    outMessages=(ArrayList) ConnectionDAO.callSP("Generate_Repayment_schedule_Resch",in,out);
   	    s1=CommonFunction.checkNull(outMessages.get(5));
   	    s2=CommonFunction.checkNull(outMessages.get(6));

			
			if(s1!=null && s1.equalsIgnoreCase("S"))
			{
				status=true;
				procval=s1;
			}else{
				procval=s2;
			}
			logger.info("status: "+status);
			logger.info("s2: "+s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return procval;
	}

	/*Arun Change for disbursal with payment Ends here */
 
   //Method added by neeraj
   public String getrevolvingFlag(String loanId) 
   {
   	String revolvingFlag="N";
   	String query=" select REVOLVING_FLAG from cr_loan_dtl a join cr_product_m b on(a.LOAN_PRODUCT=b.PRODUCT_ID) " +
   			" where loan_id='"+CommonFunction.checkNull(loanId).trim()+"'";
   	revolvingFlag=ConnectionDAO.singleReturn(query);
   	if(CommonFunction.checkNull(revolvingFlag).trim().equalsIgnoreCase(""))
   		revolvingFlag="N";
   	return revolvingFlag;
   }
   public String getBalancePrinc(String loanId) 
   {
   	String balancePrinc="0";
   	String query=" select LOAN_BALANCE_PRINCIPAL from cr_loan_dtl where loan_id='"+CommonFunction.checkNull(loanId).trim()+"'";
   	balancePrinc=ConnectionDAO.singleReturn(query);	
   	if(CommonFunction.checkNull(balancePrinc).trim().equalsIgnoreCase(""))
   		balancePrinc="0.00";
   	return balancePrinc;
   }

   public String getForwardedAmt(String loanId) 
   {
   	String forwardedAmt="0";
   	String query=" select sum(disbursal_amount)disbursal_amount from cr_loan_disbursal_dtl where rec_status='F' and loan_id='"+CommonFunction.checkNull(loanId).trim()+"'";
   	forwardedAmt=ConnectionDAO.singleReturn(query);	
   	if(CommonFunction.checkNull(forwardedAmt).trim().equalsIgnoreCase(""))
   		forwardedAmt="0.00";
   	return forwardedAmt;
   }
   
   public String getAmountInProcessLoan(int lbxLoanNoHID, String userId,
			String bDate) 
	{
		   String amountInProcess="0.00";
		   String totalReceiveable="Select parameter_value from parameter_mst where parameter_key='TOTAL_RECEIVABLE_CURRENT_SHORT_PAY'";
		   logger.info("short Receivable status :----"+totalReceiveable);
		   String flagVal=CommonFunction.checkNull(ConnectionDAO.singleReturn(totalReceiveable));
		   if(CommonFunction.checkNull(flagVal).equalsIgnoreCase("Y") )
		   {
			   StringBuilder amountInProcessQuery= new StringBuilder();
			   amountInProcessQuery.append("SELECT ISNULL(sum(AMOUNT_IN_PROCESS),0) totalBalance FROM cr_txnadvice_dtl "); 
			   amountInProcessQuery.append(" WHERE ADVICE_TYPE='R' AND LOAN_ID='"+lbxLoanNoHID+"' AND BP_TYPE ='CS' ");
			  
			  logger.info("amountInProcessQuery: "+amountInProcessQuery.toString());
			   amountInProcess=ConnectionDAO.singleReturn(amountInProcessQuery.toString());
		   }
		
		if(CommonFunction.checkNull(amountInProcess).trim().equalsIgnoreCase(""))
			amountInProcess="0.00";
		return amountInProcess;

	}
   

   public String deleteNewAddDetails(DisbursalMakerVO vo,String checkedStr) {
   	   String resultStr="";
   	 
   		ArrayList qryList = new ArrayList();
   		StringBuilder query = new StringBuilder();
   		PrepStmtObject deletePrepStmtObject = new PrepStmtObject();
   		try{
   			String addId[]=vo.getAddId();
   			logger.info("addId:----------"+addId);
   			String addIdStr="";
   			/*
   			for(int i=0;i<addId.length;i++){
   				addIdStr=addIdStr+addId[i]+",";
   			}*/
   			if(!CommonFunction.checkNull(checkedStr).equalsIgnoreCase("")){
   			addIdStr=checkedStr.substring(0,checkedStr.length()-1);
   			logger.info("addIdStr:----------"+addIdStr);
   			String querySel="Select count(1) from cr_resch_installment_plan where  LOAN_DISBURSAL_ID in ("+addIdStr+")";
   			String deleteFlag=ConnectionDAO.singleReturn(querySel);
   			if(!CommonFunction.checkNull(deleteFlag).equalsIgnoreCase("0")){
   				query.append(" Delete  from cr_resch_installment_plan where  LOAN_DISBURSAL_ID in ("+addIdStr+")");
   				logger.info("Delete query : " + query.toString());
   				deletePrepStmtObject.setSql(query.toString());
   				qryList.add(deletePrepStmtObject);
   				boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
   				logger.info("status:----------"+status);
   				if(status){
   					resultStr="deleted";
   				}else{
   					resultStr="notdeleted";
   				}
   			 }
   			}
   			
   		}catch(Exception e){
   		e.printStackTrace();	
   		}
   	return resultStr;
   }


   public String getRecoveryType(String loanid){
   	  String recoveryTypeQuery=null;
   	  String recoveryType=null;
   	   try{			   
   		   recoveryTypeQuery = " select RECOVERY_TYPE from cr_installment_plan WHERE LOAN_ID='"+loanid+"'";
   		   logger.info(" query String of getRecoveryType:----"+recoveryTypeQuery);
   		   recoveryType=ConnectionDAO.singleReturn(recoveryTypeQuery);
   		   logger.info("Recovery Type : " + recoveryType);
   	   }catch(Exception e){
   		   e.printStackTrace();
   	   }
   	   finally{
   		   recoveryTypeQuery=null;
   	   }
   	   return recoveryType;
    }
   public String getDisbursalFlag(String loanid,String txnType){
   	  String disbursalFlagQuery=null;
   	  String disbursalFlag=null;
   	  String table = null;
   	   try{			 
   		   if(CommonFunction.checkNull(txnType).equalsIgnoreCase("DIM"))
   			   table = "CR_LOAN_DISBURSAL_DTL_TEMP";
   		   else
   			   table = "CR_LOAN_DISBURSAL_DTL";
   		   disbursalFlagQuery = "SELECT DISBURSAL_FLAG FROM "+table+" WHERE DISBURSAL_FLAG='F' AND LOAN_ID='"+loanid+"'";
   		   logger.info(" query String of getDisbursalFlag:----"+disbursalFlagQuery);
   		   disbursalFlag=ConnectionDAO.singleReturn(disbursalFlagQuery);
   		   if(CommonFunction.checkNull(disbursalFlag).equalsIgnoreCase(""))
   			   disbursalFlag="P";
   		   logger.info("Recovery Type : " + disbursalFlag);
   	   }catch(Exception e){
   		   e.printStackTrace();
   	   }
   	   finally{
   		   disbursalFlagQuery=null;
   		   table=null;
   	   }
   	   return disbursalFlag;
   }
   public String getRepaymentType(String loanid){
   	  String repayTypeQuery=null;
   	  String repayType=null;
   	   try{			   
   		   repayTypeQuery = "SELECT LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='"+loanid+"'";
   		   logger.info(" query String of getDisbursalFlag:----"+repayTypeQuery);
   		   repayType=ConnectionDAO.singleReturn(repayTypeQuery);
   	   }catch(Exception e){
   		   e.printStackTrace();
   	   }
   	   finally{
   		   repayTypeQuery=null;
   	   }
   	   return repayType;
   }

   public ArrayList getFromLoanDtlDisbursalPayment(String loanId) {

   	ArrayList list = new ArrayList();

   	logger.info("In getFromLoanDtlDisbursalPayment: ");
   	
   	ArrayList mainlist = new ArrayList();
   	ArrayList subList = new ArrayList();
   	StringBuilder query= new StringBuilder();
   	StringBuilder bussIrrQ=new StringBuilder();
   	RepayScheduleVo repvo = null;
   	try {

   		query.append("select LOAN_RATE_METHOD,LOAN_FLAT_RATE,LOAN_EFF_RATE,LOAN_IRR1,LOAN_IRR2, UPFRONT_ROUNDING_AMOUNT ");
   		query.append(" from cr_loan_dtl where LOAN_ID="+loanId+"");
   		logger.info("Query in getFromLoanDtl-----" + query.toString());
   		bussIrrQ.append("select DEAL_BUSINESS_IRR from cr_deal_loan_dtl where DEAL_ID=(select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId+")");
   		logger.info("Query in getFromLoanDtlDisbursalPayment--DEAL_BUSINESS_IRR---" + bussIrrQ.toString());
   		String bussIrr=ConnectionDAO.singleReturn(bussIrrQ.toString());
   		logger.info("Query in getFromLoanDtlDisbursalPayment--bussIrr---" + bussIrr);
   		mainlist = ConnectionDAO.sqlSelect(query.toString());
   		int size = mainlist.size();
   		for (int i = 0; i < size; i++) {
   			subList = (ArrayList) mainlist.get(i);
   			repvo = new RepayScheduleVo();
   			if (subList.size()> 0) {
   				
   				repvo.setLoanRateMethod((CommonFunction.checkNull(subList.get(0)).trim()));
   				if(!CommonFunction.checkNull(subList.get(1)).trim().equalsIgnoreCase(""))
   	    		{
       	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(1))).trim());  
       	    		repvo.setFinalRate(myFormatter.format(reconNum));
   	    		}
   				//repvo.setFinalRate((CommonFunction.checkNull(subList.get(1)).trim()));
   				//logger.info("Final Rate: "+CommonFunction.checkNull(subList.get(1)));
   				if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
   	    		{
       	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
       	    		repvo.setEffectiveRate(myFormatter.format(reconNum));
   	    		}
   				//repvo.setEffectiveRate((CommonFunction.checkNull(subList.get(2)).trim()));
   				if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
   	    		{
       	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
       	    		repvo.setMktIRR1(myFormatter.format(reconNum));
   	    		}
   				//repvo.setMktIRR1((CommonFunction.checkNull(subList.get(3)).trim()));
   				if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
   	    		{
       	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
       	    		repvo.setMktIRR2(myFormatter.format(reconNum));
   	    		}
   			// add by saorabh
				if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number UPFRONT_ROUNDING_AMOUNT =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
    	    		repvo.setUpfrontRoundingAmount(myFormatter.format(UPFRONT_ROUNDING_AMOUNT));
	    		}
					else
					repvo.setUpfrontRoundingAmount("0.00");
				// end by saorabh
   				//repvo.setMktIRR2((CommonFunction.checkNull(subList.get(4)).trim()));
   				if((CommonFunction.checkNull(subList.get(0)).trim()).equalsIgnoreCase("E"))
                   {
   					repvo.setFinalRate("");
                   }
   				repvo.setBussIrr(bussIrr);
   			
   			}
   			list.add(repvo);
   		}
   	} catch (Exception e) {
   		e.printStackTrace();
   	}
   	finally{
   		mainlist = null;
   		subList = null;
   		query= null;
   		bussIrrQ= null;
   		repvo = null;
   	}
   	return list;
   }



   public ArrayList getRepaySchedDisbursalPayment(String loanId,String makerId) {
   	
   	ArrayList list = new ArrayList();
   	logger.info("In getRepaySchedDisbursalPayment: ");
   	ArrayList mainlist = new ArrayList();
   	ArrayList subList = new ArrayList();
   	StringBuilder query= new StringBuilder();
   	RepayScheduleVo repvo = null;
   	try {

   		  query.append("select R_Seq_No,dbo.DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
             " IIF(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS,R_OTHER_CHARGES from Repay_Temp where R_LOAN_ID="+loanId+" AND MAKER_ID='"+makerId+"' order by R_Seq_No");
      
   		logger.info("Query in getRepaySchedDisbursalPayment-----" + query.toString());
   		mainlist = ConnectionDAO.sqlSelect(query.toString());
   		int size = mainlist.size();
   		for (int i = 0; i < size; i++) {
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
   				if(!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
   	    		{
       	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());  
       	    		repvo.setOtherCharges(myFormatter.format(reconNum));
   	    		}
   				else
   				{
   					repvo.setOtherCharges("0.00");
   				}
   			}
   			list.add(repvo);
   		}
   	} catch (Exception e) {
   		e.printStackTrace();
   	}
   	finally
   	{
   		mainlist = null;
   		subList = null;
   		query= null;
   		repvo = null;
   	}
   	return list;
   }
   public boolean saveOtherChargesPlanAtDisbursal(OtherChargesPlanVo ipvo)
   {
   	    logger.info("In saveOtherChargesPlanAtDisbursal");
   		String FromInstallment[] = ipvo.getFromInstall();
   		logger.info("In FromInstallment.................."+ipvo.getFromInstall());
   		logger.info("In FromInstallment....."+FromInstallment.length);
   		String ToInstallment[] = ipvo.getToInstall();
   		logger.info("In ToInstallment.................."+ToInstallment.length);
   		String type[] = ipvo.getType();
   		logger.info("In type.................."+type.length);
   		String InstallmentType= ipvo.getInstallmentType();
   		logger.info("In saveInstallPlan.................."+ipvo.getInstallmentType());
   		String TotalInstallment=ipvo.getTotalInstallment();
   		logger.info("In saveInstallPlan.................."+ipvo.getTotalInstallment());
   		String toInstallment = ipvo.getToInstallment();	
   		logger.info("In toInstallment.................."+toInstallment);
   		String amount[] = ipvo.getAmount();
   		logger.info("In amount.................."+amount.length);
   		String chargeCode[] = ipvo.getChargehiddenFld();
   		String loanId = ipvo.getLoanId();
   		logger.info("loanId........."+loanId);
   			
   		ArrayList qryList=new ArrayList();
   		boolean status=false;
   		
   		try {
   			 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
   			 StringBuffer bufInsSql =	new StringBuffer();
   			 StringBuilder checkQ=new StringBuilder();
   			    checkQ.append("select count(*) from cr_loan_charge_plan_dtl where LOAN_ID="+(CommonFunction.checkNull(ipvo.getLoanId()).trim())+"  AND ISNULL(LOAN_DISBURSAL_ID_TEMP,'')<>''");
   			   String count=ConnectionDAO.singleReturn(checkQ.toString());
   			   
   			   checkQ=null;
   			   
   			   if(Integer.parseInt(count)>0)
   			   { 
   			/*	StringBuilder hstQuery = new StringBuilder();
				   hstQuery.append("INSERT INTO CR_LOAN_CHARGE_PLAN_DTL_HST (LOAN_ID, LOAN_DISBURSAL_ID_TEMP, LOAN_DISBURSAL_ID, FROM_INSTL_NO, TO_INSTL_NO,CHARGE_TYPE,AMOUNT,CHARGE_CODE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				   hstQuery.append("(SELECT LOAN_ID, LOAN_DISBURSAL_ID_TEMP, LOAN_DISBURSAL_ID, FROM_INSTL_NO, TO_INSTL_NO,CHARGE_TYPE,AMOUNT,CHARGE_CODE,REC_STATUS,");                 
				   hstQuery.append("MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE FROM  CR_LOAN_CHARGE_PLAN_DTL	WHERE LOAN_ID='"+(CommonFunction.checkNull(ipvo.getLoanId()).trim())+ "')");
				   logger.info("Insert query in CR_LOAN_CHARGE_PLAN_DTL_HST :  " + hstQuery.toString());
				   
				   insertPrepStmtObject = new PrepStmtObject();
				   insertPrepStmtObject.setSql(hstQuery.toString());
				   qryList.add(insertPrepStmtObject);
				   hstQuery=null;
				   insertPrepStmtObject=null;
				   
				  insertPrepStmtObject = new PrepStmtObject(); */
				  StringBuilder qry=new StringBuilder();
				  qry.append("DELETE FROM cr_loan_charge_plan_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(ipvo.getLoanId()).trim())+ "'  AND ISNULL(LOAN_DISBURSAL_ID_TEMP,'')<>''");
				  logger.info("DELETE query from cr_loan_charge_plan_dtl : " + qry.toString());
			     insertPrepStmtObject.setSql(qry.toString());
				 qryList.add(insertPrepStmtObject);
   				// ConnectionDAO.sqlInsUpdDelete(qryList);
   				  
   				 qry=null;
   				}
   		
   			 for(int k=0;k<FromInstallment.length;k++)  
   			{
   				bufInsSql =	new StringBuffer();
   		     insertPrepStmtObject = new PrepStmtObject();
   			  
   			
   			  insertPrepStmtObject = new PrepStmtObject();
   				bufInsSql.append("insert into cr_loan_charge_plan_dtl (LOAN_ID,LOAN_DISBURSAL_ID_TEMP,FROM_INSTL_NO,TO_INSTL_NO,CHARGE_TYPE,AMOUNT,CHARGE_CODE,REC_STATUS,MAKER_ID,MAKER_DATE) values(?,?,?,?,?,?,?,?,?,dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
   								
   				if(CommonFunction.checkNull(ipvo.getLoanId()).trim().equalsIgnoreCase(""))
   					insertPrepStmtObject.addNull();
   				else
   					insertPrepStmtObject.addString(ipvo.getLoanId().trim());   // loan Id
   				
   				if(CommonFunction.checkNull(ipvo.getDisbursalId()).trim().equalsIgnoreCase(""))
   					insertPrepStmtObject.addNull();
   				else
   					insertPrepStmtObject.addString(ipvo.getDisbursalId().trim());   // LOAN_DISBURSAL_ID_TEMP
   				
   				if(CommonFunction.checkNull(FromInstallment[k]).trim().equalsIgnoreCase(""))
   					insertPrepStmtObject.addNull();
   				else
   					insertPrepStmtObject.addString(FromInstallment[k].trim()); //From Installment
   				
   		    	if(CommonFunction.checkNull(ToInstallment[k]).trim().equalsIgnoreCase(""))
   					insertPrepStmtObject.addNull();
   				else
   				insertPrepStmtObject.addString(ToInstallment[k].trim());// To Installment 
   		    	
//   		    	if(CommonFunction.checkNull(type[k]).trim().equalsIgnoreCase(""))
//   					insertPrepStmtObject.addNull();
//   				else
//   				insertPrepStmtObject.addString(type[k].trim());// charge type
   		    	
   		    	insertPrepStmtObject.addString("F");// charge type
   		    	 
   		    	 if((CommonFunction.checkNull(amount[k])).trim().equalsIgnoreCase(""))
   		        		insertPrepStmtObject.addString("0.00");
   				else
   				 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(amount[k]).trim())).toString());// PRINCIPAL_AMOUNT   	
   		    	
   		    	if((CommonFunction.checkNull(chargeCode[k])).trim().equalsIgnoreCase(""))
   					insertPrepStmtObject.addNull();
   				else
   					insertPrepStmtObject.addString(chargeCode[k].trim()); //Charge Id 
   		    	 
   		    	insertPrepStmtObject.addString("P");// REC_STATUS   	   	
   								
   		    	//insertPrepStmtObject.addString(""+(k+1));// To Installment 
   		    			    	
   		    	if (CommonFunction.checkNull(ipvo.getMakerId()).equalsIgnoreCase(""))
   					insertPrepStmtObject.addNull();
   				else
   					insertPrepStmtObject.addString((CommonFunction.checkNull(ipvo.getMakerId()).trim()));
   				
   				if (CommonFunction.checkNull(ipvo.getMakerDate()).equalsIgnoreCase(""))
   					insertPrepStmtObject.addNull();
   				else
   					insertPrepStmtObject.addString((CommonFunction.checkNull(ipvo.getMakerDate()).trim()));
   		    	//============================================================
   		    	
   			  
   			 	insertPrepStmtObject.setSql(bufInsSql.toString());
   				 logger.info("IN saveOtherChargesPlanAtDisbursal() LOAN insert query1 ### DAO IMPL "+insertPrepStmtObject.printQuery());
   				qryList.add(insertPrepStmtObject);
   				bufInsSql=null;
   			  
   			}
   						
   				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
   			} catch (Exception e) {
   				e.printStackTrace();
   			}
   	
   	     logger.info("In saveOtherChargesPlanAtDisbursal......................"+status);
   	return status;

   }
   public ArrayList getOtherPeriodicalChargeDetailAtDisbursal(String id)
   {
   	 ArrayList<OtherChargesPlanVo> list=new ArrayList<OtherChargesPlanVo>();
       ArrayList mainList=new ArrayList ();
   		ArrayList subList =new ArrayList();
   		String count=null;
		StringBuilder chkQuery=new StringBuilder();
   		try{
   			StringBuilder query=new StringBuilder();
			chkQuery.append("SELECT COUNT(LOAN_ID) FROM CR_LOAN_CHARGE_PLAN_DTL WHERE LOAN_ID="+id+" AND ISNULL(LOAN_DISBURSAL_ID_TEMP,'')<>''");
			count = ConnectionDAO.singleReturn(chkQuery.toString());	
			chkQuery=null;
			if(Integer.parseInt(count)>0)
			{
				query.append("select distinct D.FROM_INSTL_NO,D.TO_INSTL_NO,D.CHARGE_TYPE,D.AMOUNT ,C.CHARGE_CODE,C.CHARGE_DESC,L.LOAN_INSTALLMENT_TYPE,L.LOAN_NO_OF_INSTALLMENT,L.LOAN_RATE_TYPE,L.LOAN_LOAN_AMOUNT   "+
						"  from cr_loan_dtl L left JOIN cr_loan_charge_plan_dtl D on D.LOAN_ID=L.LOAN_ID ,com_charge_code_m C "+               			
						" where L.LOAN_ID="+id+" AND C.CHARGE_CODE=D.CHARGE_CODE AND ISNULL(LOAN_DISBURSAL_ID_TEMP,'')<>''");
			}
			else
			{
			    query.append("select distinct D.FROM_INSTL_NO,D.TO_INSTL_NO,D.CHARGE_TYPE,D.AMOUNT ,C.CHARGE_CODE,C.CHARGE_DESC,L.LOAN_INSTALLMENT_TYPE,L.LOAN_NO_OF_INSTALLMENT,L.LOAN_RATE_TYPE,L.LOAN_LOAN_AMOUNT   "+
				"  from cr_loan_dtl L left JOIN cr_loan_charge_plan_dtl D on D.LOAN_ID=L.LOAN_ID ,com_charge_code_m C "+               			
				" where L.LOAN_ID="+id+" AND C.CHARGE_CODE=D.CHARGE_CODE");
			}
   			
   		 logger.info("getOtherPeriodicalChargeDetailAtDisbursal Queryl: "+query);
   		
   		mainList=ConnectionDAO.sqlSelect(query.toString());
   		
   		query=null;
   		
   		for(int i=0;i<mainList.size();i++)
   		{
   			subList= (ArrayList)mainList.get(i);
   			if(subList.size()>0){
   				OtherChargesPlanVo ipVo= null;
   				ipVo = new OtherChargesPlanVo();  

   				    ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
   				    ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
   				   // ipVo.setChargeType((CommonFunction.checkNull(subList.get(2))).trim());
   				    
   				    ipVo.setChargeType("FLAT");
   				    if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase(""))
       	    		{
   	    	    		Number reconNum=0.0000;
   						try {
   							reconNum = myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
   						} catch (ParseException e) {
   							// TODO Auto-generated catch block
   							e.printStackTrace();
   						}
   						ipVo.setChargeAmount((myFormatter.format(reconNum)));
       	    		}
       	    		else
       	    		{
       	    			ipVo.setChargeAmount("0.00");	
       	    		}
   				    
   				    ipVo.setChargeCode((CommonFunction.checkNull(subList.get(4))).trim());
   				    ipVo.setChargeDesc((CommonFunction.checkNull(subList.get(5))).trim());
   				    
   				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(6))).trim());
   				    ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(7))).trim());
     				
   				    //}
   				
   				ipVo.setRateType((CommonFunction.checkNull(subList.get(8))).trim());
   				
   				if(!CommonFunction.checkNull(subList.get(9)).equalsIgnoreCase(""))
   				{
   				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(9))).trim());
    			   
   				     logger.info("setLoanAmount: "+instam);			
    			         ipVo.setLoanAmount((myFormatter.format(instam)));
   				}
   				else
   				{
   				     ipVo.setLoanAmount("0.00");
   				}
   				
   				   list.add(ipVo);
   			     }
   		  }
   		}catch(Exception e){
   			e.printStackTrace();
   		}
   		return list;
   }
   public String newInstallmentPlanStatus(DisbursalMakerVO vo){
		  String newInstPlanQuery=null;
		  String newInstPlan=null;
		  String result=null;
		   try{			   
			   newInstPlanQuery = "SELECT INSTALLMENT_PLAN_ID FROM CR_RESCH_INSTALLMENT_PLAN WHERE LOAN_ID='"+vo.getLbxLoanNoHID()+"'";
			   logger.info(" query String of getRecoveryType:----"+newInstPlanQuery);
			   newInstPlan=ConnectionDAO.singleReturn(newInstPlanQuery);
			   if(CommonFunction.checkNull(newInstPlan).equalsIgnoreCase(""))
				   result="Y";
			   else
				   result="N";
			   logger.info("newInstPlan result : " + result);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		   finally{
			   newInstPlanQuery=null;
			   newInstPlan=null;
		   }
		   return result;
	}
  
   public boolean paymentAmountAtDisbursal(DisbursalMakerVO vo) 
   {
   	boolean status=false;
   	String paymentAmount=null;
   	String TotalPaidAmount=null;
   	String cashPaymentLimit=null;
   	
   	try
   	{
   		paymentAmount=(CommonFunction.checkNull(vo.getPaymentAmount())).replace(",", "");
   		TotalPaidAmount=ConnectionDAO.singleReturn("SELECT ISNULL(SUM(PMNT_AMOUNT),0) AS PAYMENT_AMOUNT FROM CR_LOAN_DISBURSAL_DTL_TEMP WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"'");
   		cashPaymentLimit=ConnectionDAO.singleReturn("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CASH_PAYMENT_LIMIT'");
   		if(paymentAmount.equalsIgnoreCase(""))
   			paymentAmount="0.00";
   		BigDecimal pa = new BigDecimal(paymentAmount);
   		BigDecimal tpa = new BigDecimal(TotalPaidAmount);
   		BigDecimal cpl = new BigDecimal(cashPaymentLimit);
   		BigDecimal total = pa.add(tpa);
   		int res;
   		res = total.compareTo(cpl);
   		if(res == 1)
   			status = false;
   		else
   			status = true;
   		
   	} catch (Exception e) {
   		e.printStackTrace();
   	}finally{
   		paymentAmount=null;
   		TotalPaidAmount=null;
   		cashPaymentLimit=null;
   	}
   	return status;
   }
   public String getInstallmentType(String loanid){
		  String instlTypeQuery=null;
		  String instlType=null;
		   try{			   
			   instlTypeQuery = "SELECT LOAN_INSTALLMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='"+loanid+"'";
			   logger.info(" query String of getInstallmentType:----"+instlTypeQuery);
			   instlType=ConnectionDAO.singleReturn(instlTypeQuery);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		   finally{
			   instlTypeQuery=null;
		   }
		   return instlType;
	}
   
   @Override
   public String validationOnLeadPartner(String loanId) {
   	StringBuilder queryResult = new StringBuilder();
   	 StringBuilder queryResult1 = new StringBuilder();
   	 StringBuilder queryResult2 = new StringBuilder();
   	String finalResult="ALLRIGHT";
   	int result=0;
    try{  
   	 queryResult.append(" SELECT COUNT(1) FROM cr_business_partner_dtl WHERE LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"  AND IFNULL(SERVICE_PARTNER_FLAG,'N')='Y' AND IFNULL(SELF_FLAG,'N')='Y'  ");
   	 result=Integer.parseInt(ConnectionDAO.singleReturn(queryResult.toString()));
   	 if(result>0){
   		 queryResult2.append(" select COUNT(1) from consortium_repayment_dtl a ");
   		queryResult2.append(" join cr_business_partner_dtl b on a.loan_id=b.LOAN_ID and ifnull(b.LEAD_PARTNER_FLAG,'N')='Y' AND A.PARTNER_ID=B.partner_ID ");
   		queryResult2.append(" where loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+" and A.REFPAY_EFF_RATE IS NOT NULL ");
   		 result=Integer.parseInt(ConnectionDAO.singleReturn(queryResult2.toString())); 
   		 if(result==0){
   			 finalResult="CAPTURELEAD"; 
   		 }
   		 else{
   			 finalResult="ALLRIGHT"; 
   		 }
   	 }
   	 else{
   		 finalResult="ALLRIGHT";
   	 }
   	 if(CommonFunction.checkNull(finalResult).trim().equalsIgnoreCase("ALLRIGHT")){

   		 queryResult1.append(" SELECT COUNT(1) FROM cr_business_partner_dtl A WHERE IFNULL(A.LEAD_PARTNER_FLAG,'N')='Y' AND A.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+" AND IFNULL(ROUND(A.LOAN_AMOUNT,2),0)= ");
   		 queryResult1.append(" (SELECT IFNULL(ROUND(SUM(B.PRIN_COMP),2),0) FROM consortium_repayment_dtl B WHERE B.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+" AND B.PARTNER_ID=A.PARTNER_ID AND REFPAY_EFF_RATE IS NOT NULL )  ");
   		 result=Integer.parseInt(ConnectionDAO.singleReturn(queryResult1.toString()));
   		 if(result==0){
   			 finalResult="AMTNOTMATCH";
   		 }
   		 else{
   			 finalResult="ALLRIGHT";
   		 }
   	 }

   }catch (Exception e) {
   	e.printStackTrace();
   	e=null;
   }
   finally
   {
   	loanId=null;
   	queryResult=null;
   	queryResult1=null;
   }
   return finalResult;
   } 
   public ArrayList selectOtherLoanDetails(int lbxLoanNoHID)
   {
   	return null;
   }
@Override
public String getRecStatusForPartPayment(int lbxLoanNoHID, String userId,
		String bDate) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getDateForDisbursalCheck(DisbursalMakerVO vo) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public String getEditDueDateStatus(String lbxLoanNoHID)

{
	return null;
	
	
}

@Override
public String getRepyEffDateOfLoan(String lbxLoanNoHID)

{
	return null;
	
	
}
@Override
public String getRepayEffDate(String lbxLoanNoHID)

{

	
	return null;
	
	
}

//start:added by indrajeet
/*@Override
public ArrayList checkSblGblLimit(String lbxLoanNoHID) {
return null;
}*/
//end:added by indrajeet

//start here | Brijesh Pathak
public String getLoanTenure(String lbxLoanNoHID)
	
	{
		logger.info("---------------In getLoanTenure()-----------");
		String query = "select loan_tenure from cr_loan_dtl where loan_id='"+lbxLoanNoHID+"'";
		logger.info("String Query Loan Tenure: "+query );
		String str = "";
		
			str = ConnectionDAO.singleReturn(query);
		
		
		return str;
		
		
	}
//end here | Brijesh Pathak



}

