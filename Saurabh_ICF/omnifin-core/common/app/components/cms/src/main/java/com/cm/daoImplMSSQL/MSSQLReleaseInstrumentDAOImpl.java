package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;


import com.cm.dao.ReleaseInstrumentDAO;
import com.cm.vo.InstructionCapMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class MSSQLReleaseInstrumentDAOImpl implements ReleaseInstrumentDAO {
	private static final Logger logger = Logger.getLogger(MSSQLReleaseInstrumentDAOImpl.class.getName());

	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	//change by sachin
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	//end by sachin
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	 public ArrayList<InstructionCapMakerVO> searchReleaseInstrumentMaker(
				InstructionCapMakerVO instructionCapMakerVO) {
			
		 		StringBuilder lbxLoanNoHID = new StringBuilder(); 
		 		StringBuilder loanAccNo = new StringBuilder(); 
		 		StringBuilder instrumentType = new StringBuilder(); 
		 		StringBuilder bank = new StringBuilder(); 
		 		StringBuilder branch = new StringBuilder(); 
		 		StringBuilder lbxBankID = new StringBuilder(); 
		 		StringBuilder lbxBranchID = new StringBuilder(); 
		 		StringBuilder product = new StringBuilder(); 
		 		StringBuilder lbxProductID = new StringBuilder(); 
		 		StringBuilder customerName = new StringBuilder(); 
		 		
//		 		String lbxLoanNoHID="";
//		        String loanAccNo="";
//		        String instrumentType="";
//		        String bank="";
//		        String branch="";
//		        String lbxBankID="";
//		        String lbxBranchID="";
//		        String product="";
//		        String lbxProductID="";
//		        String customerName="";
//		        String userName="";
		    	int count=0;
		    	int startRecordIndex=0;
		    	int endRecordIndex = no;

		        ArrayList<InstructionCapMakerVO> arrList = new ArrayList<InstructionCapMakerVO>();
		        ArrayList mainList = new ArrayList();
		        ArrayList subList = new ArrayList();
		        
//		    	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+instructionCapMakerVO.getReportingToUserId());
//				try{
//					String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+instructionCapMakerVO.getReportingToUserId()+"'";
//					userName=ConnectionDAO.singleReturn(userNameQ);
//					logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//				}
//				catch(Exception e)
//				{
//					e.printStackTrace();
//				}
//		        
		           try{
		        	   
		                  logger.info("In searchHoldInstrumentMaker().....................................Dao Impl");
		                  lbxLoanNoHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim()));
		                  loanAccNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLoanAccNo()).trim()));
		                  instrumentType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim()));
		                  bank.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBank()).trim()));
		                  branch.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBranch()).trim()));
		                  lbxBankID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()));
		                  lbxBranchID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()));
		                  product.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getProduct()).trim()));
		                  lbxProductID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim()));
		                  customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getCustomerName()).trim()));
		                  logger.info("customerName"+customerName);
		                  
		                  boolean appendSQL=false;

		                  StringBuffer sbAppendToSQLCount=new StringBuffer();
		                  StringBuffer bufInsSql =    new StringBuffer();
		                  
		                  
   
   	 		  
    bufInsSql.append( " select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc,d.SCHEME_DESC, ");
    bufInsSql.append(dbo);
    bufInsSql.append("DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"')as LOAN_APPROVAL_DATE,f.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID  ");
   
    bufInsSql.append("from cr_loan_dtl a, cr_pdc_instrument_dtl c, ");
    bufInsSql.append("  cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and ");
    bufInsSql.append(" e.branch_id=a.loan_branch  and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_SCHEME = d.SCHEME_ID and c.PDC_STATUS in ('A','H')  ");
    bufInsSql.append("  and c.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F')) AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"' ");

    sbAppendToSQLCount.append( "select count(1) from(select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc,d.SCHEME_DESC, ");
    sbAppendToSQLCount.append(dbo);
    sbAppendToSQLCount.append("DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"') as LOAN_APPROVAL_DATE,f.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID from cr_loan_dtl a, cr_pdc_instrument_dtl c, ");
    sbAppendToSQLCount.append("  cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and ");
    sbAppendToSQLCount.append(" e.branch_id=a.loan_branch  and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_SCHEME = d.SCHEME_ID and c.PDC_STATUS in ('A','H')  ");
    sbAppendToSQLCount.append("  and c.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F')) AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"' ");


		                 if((!((lbxLoanNoHID.toString()).equalsIgnoreCase("")))) {
		                     bufInsSql.append(" AND C.PDC_LOAN_ID='"+lbxLoanNoHID+"'");
		                     sbAppendToSQLCount.append(" AND C.PDC_LOAN_ID='"+lbxLoanNoHID+"'");
		                     appendSQL=true;

		                  }

		                  if((!((lbxBankID.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND C.pdc_issueing_bank_id='"+lbxBankID+"'");
		                      sbAppendToSQLCount.append("  AND C.pdc_issueing_bank_id='"+lbxBankID+"'");
		                      appendSQL=true;
		                  }

		                  if((!((instrumentType.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND C.PDC_INSTRUMENT_MODE='"+instrumentType+"'");
		                      sbAppendToSQLCount.append("  AND C.PDC_INSTRUMENT_MODE='"+instrumentType+"'");
		                      appendSQL=true;
		                  }

		                  if((!((lbxBranchID.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND C.pdc_issueing_branch_id='"+lbxBranchID+"'");
		                      sbAppendToSQLCount.append("  AND C.pdc_issueing_branch_id='"+lbxBranchID+"'");
		                      appendSQL=true;
		                  }
		                  if((!((lbxProductID.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND A.LOAN_PRODUCT ='"+lbxProductID+"'");
		                      sbAppendToSQLCount.append("  AND A.LOAN_PRODUCT ='"+lbxProductID+"'");
		                      appendSQL=true;
		                  }

		                  if((!((customerName.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND f.CUSTOMER_NAME like '%"+customerName+"%'");
		                      sbAppendToSQLCount.append("  AND f.CUSTOMER_NAME like '%"+customerName+"%'");
		                      appendSQL=true;
		                  }
//		                  if((!(instructionCapMakerVO.getLbxUserId().equalsIgnoreCase("")))) {
//		                		bufInsSql.append(" AND A.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"' ");	
//		                		sbAppendToSQLCount.append(" AND A.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"'");
//		          			appendSQL = true;
//		          	      }
		                  
		          //        bufInsSql.append("  order by a.LOAN_APPROVAL_DATE");
		                  sbAppendToSQLCount.append(" )as b");
		                  
		                  count =Integer.parseInt(ConnectionDAO.singleReturn(sbAppendToSQLCount.toString()));
			      			logger.info("sbAppendToSQLCount : "+sbAppendToSQLCount.toString());

			      			//if(((lbxLoanNoHID.toString()).trim()==null && (lbxBankID.toString()).trim()==null && (instrumentType.toString()).trim()==null && (lbxBranchID.toString()).trim()==null  && (lbxProductID.toString()).trim()==null && (customerName.toString()).trim()==null) || ((lbxLoanNoHID.toString()).trim().equalsIgnoreCase("") && (lbxBankID.toString()).trim().equalsIgnoreCase("") && (instrumentType.toString()).trim().equalsIgnoreCase("") && (lbxBranchID.toString()).trim().equalsIgnoreCase("") && (lbxProductID.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("")) || instructionCapMakerVO.getCurrentPageLink()>1)
			    			//{
			    			
			    			logger.info("current PAge Link no .................... "+instructionCapMakerVO.getCurrentPageLink());
			    			if(instructionCapMakerVO.getCurrentPageLink()>1)
			    			{
			    				startRecordIndex = (instructionCapMakerVO.getCurrentPageLink()-1)*no;
			    				endRecordIndex = no;
			    				logger.info("startRecordIndex .................... "+startRecordIndex);
			    				logger.info("endRecordIndex .................... "+endRecordIndex);
			    			}
			    			bufInsSql.append(" ORDER BY c.pdc_loan_id OFFSET ");
							bufInsSql.append(startRecordIndex);
							bufInsSql.append(" ROWS FETCH next ");
							bufInsSql.append(endRecordIndex);
							bufInsSql.append(" ROWS ONLY ");
			 				//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			 				
			 	  			
			    			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
			    			//}
			    			logger.info("In searchReleaseInstrumentMaker() of ReleaseInstrumentDAOImpl  query : "+bufInsSql.toString());

		                  mainList = ConnectionDAO.sqlSelect(bufInsSql.toString());

		          for(int i=0;i<mainList.size();i++){
			     	subList=(ArrayList)mainList.get(i);
				  if(subList.size()>0){
					  
					  InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
						loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
						loanvo.setLoanAccNo("<a href=\"#\" onclick=\"editReleaseInstrumentMaker('"+((CommonFunction.checkNull(subList.get(0)).trim()))+"');\">" +
								 ((CommonFunction.checkNull(subList.get(1)).trim()))+"</a>");

						loanvo.setProduct((CommonFunction.checkNull(subList.get(2)).trim()));
						
						Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
						
						loanvo.setLoanAmount(myFormatter.format(loanAmount));
						
						
						//loanvo.setLoanAmount((CommonFunction.checkNull(subList.get(3)).trim()));
						loanvo.setBranch((CommonFunction.checkNull(subList.get(4)).trim()));
						loanvo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
						loanvo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(6)).trim()));
						loanvo.setCustomerName((CommonFunction.checkNull(subList.get(7)).trim()));
						loanvo.setUserName((CommonFunction.checkNull(subList.get(8)).trim()));
						loanvo.setTotalRecordSize(count);
						//loanvo.setReportingToUserId(userName);
						arrList.add(loanvo);
				  		}
		          }

		           }catch(Exception e){
		        	   e.printStackTrace();
		           }
		           finally
		           {
		        	   lbxLoanNoHID = null;
		        	   loanAccNo = null;
		        	   instrumentType = null;
		        	   bank = null;
		        	   branch = null;
		        	   lbxBankID = null;
		        	   lbxBranchID = null;
		        	   product = null;
		        	   lbxProductID = null;
		        	   customerName = null; 
		           }

			return arrList;
		}
	 
	 
	 public ArrayList<InstructionCapMakerVO> searchReleaseInstrumentAuthor(
				InstructionCapMakerVO instructionCapMakerVO) {
		 	
		 	StringBuilder lbxLoanNoHID = new StringBuilder();
		 	StringBuilder loanAccNo = new StringBuilder();
		 	StringBuilder instrumentType = new StringBuilder();
		 	StringBuilder bank = new StringBuilder();
		 	StringBuilder branch = new StringBuilder();
		 	StringBuilder lbxBankID = new StringBuilder();
		 	StringBuilder lbxBranchID = new StringBuilder();
		 	StringBuilder product = new StringBuilder();
		 	StringBuilder instrumentNo = new StringBuilder();
		 	StringBuilder status = new StringBuilder();
		 	StringBuilder customerName = new StringBuilder();
		 	StringBuilder lbxProductID = new StringBuilder();

		 	logger.info("In searchReleaseInstrumentAuthor");
//			String lbxLoanNoHID="";
//		     String loanAccNo="";
//		     String instrumentType="";
//		     String bank="";
//		     String branch="";
//		     String lbxBankID="";
//		     String lbxBranchID="";
//		     String product="";
//		     String instrumentNo="";
//		     String status="";
//		     String customerName="";
//		     String lbxProductID="";
//		     String userName="";
		     int count=0;
		     int startRecordIndex=0;
		    int endRecordIndex = no;

		     ArrayList<InstructionCapMakerVO> arrList = new ArrayList<InstructionCapMakerVO>();
		     ArrayList mainList = new ArrayList();
		     ArrayList subList = new ArrayList();
//		 	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+instructionCapMakerVO.getReportingToUserId());
//			try{
//				String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+instructionCapMakerVO.getReportingToUserId()+"'";
//				userName=ConnectionDAO.singleReturn(userNameQ);
//				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
		     
		        try{
		               
		               lbxLoanNoHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim()));
		               loanAccNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLoanAccNo()).trim()));
		               instrumentType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim()));
		               bank.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBank()).trim()));
		               branch.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBranch()).trim()));
		               lbxBankID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()));
		               lbxBranchID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()));
		               product.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getProduct()).trim()));
		               instrumentNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentNo()).trim()));
		               status.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getStatus()).trim()));
		               customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getCustomerName()).trim()));
		               lbxProductID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim()));
		           
		               boolean appendSQL=false;

		               StringBuffer sbAppendToSQLCount=new StringBuffer();
		               StringBuffer bufInsSql =    new StringBuffer();
		               
		               
		               bufInsSql.append("select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,d.SCHEME_DESC,e.branch_desc," );
		               bufInsSql.append(dbo);
		               bufInsSql.append("DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"') as LOAN_APPROVAL_DATE,f.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID  ");
		               
		               bufInsSql.append("from cr_loan_dtl a, cr_pdc_instrument_dtl c, ");
		               bufInsSql.append(" cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and ");
		               bufInsSql.append(" e.branch_id=a.loan_branch and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_SCHEME = d.SCHEME_ID ");
		               bufInsSql.append(" and c.PDC_INSTRUMENT_ID in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F') and hold_reason_status in ('L') AND MAKER_ID!='"+instructionCapMakerVO.getUserID()+"') AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"' ");

		               sbAppendToSQLCount.append("select distinct count(1) from (select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,d.SCHEME_DESC,e.branch_desc," );
		               sbAppendToSQLCount.append(dbo);
		               sbAppendToSQLCount.append("DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"') as LOAN_APPROVAL_DATE,f.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID from cr_loan_dtl a, cr_pdc_instrument_dtl c, ");
		               sbAppendToSQLCount.append(" cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and ");
		               sbAppendToSQLCount.append(" e.branch_id=a.loan_branch  and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_SCHEME = d.SCHEME_ID ");
		               sbAppendToSQLCount.append(" and c.PDC_INSTRUMENT_ID in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F') and hold_reason_status in ('L') AND MAKER_ID!='"+instructionCapMakerVO.getUserID()+"') AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"' ");

		               			if((!((lbxLoanNoHID.toString()).equalsIgnoreCase("")))) {
		        		                     bufInsSql.append(" AND C.PDC_LOAN_ID='"+lbxLoanNoHID+"'");
		        		                     sbAppendToSQLCount.append(" AND C.PDC_LOAN_ID='"+lbxLoanNoHID+"'");
		        		                     appendSQL=true;

		        		                  }

		        		                  if((!((lbxBankID.toString()).equalsIgnoreCase("")))) {
		        		                      bufInsSql.append("  AND C.pdc_issueing_bank_id='"+lbxBankID+"'");
		        		                      sbAppendToSQLCount.append("  AND C.pdc_issueing_bank_id='"+lbxBankID+"'");
		        		                      appendSQL=true;
		        		                  }

		        		                  if((!((instrumentType.toString()).equalsIgnoreCase("")))) {
		        		                      bufInsSql.append("  AND C.PDC_INSTRUMENT_MODE='"+instrumentType+"'");
		        		                      sbAppendToSQLCount.append("  AND C.PDC_INSTRUMENT_MODE='"+instrumentType+"'");
		        		                      appendSQL=true;
		        		                  }

		        		                  if((!((lbxBranchID.toString()).equalsIgnoreCase("")))) {
		        		                      bufInsSql.append("  AND C.pdc_issueing_branch_id='"+lbxBranchID+"'");
		        		                      sbAppendToSQLCount.append("  AND C.pdc_issueing_branch_id='"+lbxBranchID+"'");
		        		                      appendSQL=true;
		        		                  }
		        		                  if((!((lbxProductID.toString()).equalsIgnoreCase("")))) {
		        		                      bufInsSql.append("  AND A.LOAN_PRODUCT ='"+lbxProductID+"'");
		        		                      sbAppendToSQLCount.append("  AND A.LOAN_PRODUCT ='"+lbxProductID+"'");
		        		                      appendSQL=true;
		        		                  }

		        		                  if((!((customerName.toString()).equalsIgnoreCase("")))) {
		        		                      bufInsSql.append("  AND f.CUSTOMER_NAME like '%"+customerName+"%'");
		        		                      sbAppendToSQLCount.append("  AND f.CUSTOMER_NAME like '%"+customerName+"%'");
		        		                      appendSQL=true;
		        		                  }
		        		           		               
		        		               //   bufInsSql.append("  order by a.LOAN_APPROVAL_DATE");
		        		                  sbAppendToSQLCount.append(" )as b");
		        		                  
		        		                  logger.info("Query-----"+bufInsSql.toString());
		        		                  count =Integer.parseInt(ConnectionDAO.singleReturn(sbAppendToSQLCount.toString()));
		        			      			logger.info("sbAppendToSQLCount : "+sbAppendToSQLCount.toString());

		        			      			//if(((lbxLoanNoHID.toString()).trim()==null && (lbxBankID.toString()).trim()==null && (instrumentType.toString()).trim()==null && (lbxBranchID.toString()).trim()==null  && (lbxProductID.toString()).trim()==null && (customerName.toString()).trim()==null) || ((lbxLoanNoHID.toString()).trim().equalsIgnoreCase("") && (lbxBankID.toString()).trim().equalsIgnoreCase("") && (instrumentType.toString()).trim().equalsIgnoreCase("") && (lbxBranchID.toString()).trim().equalsIgnoreCase("") && (lbxProductID.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("")) || instructionCapMakerVO.getCurrentPageLink()>1)
		        			    			//{
		        			    			
		        			    			logger.info("current PAge Link no .................... "+instructionCapMakerVO.getCurrentPageLink());
		        			    			if(instructionCapMakerVO.getCurrentPageLink()>1)
		        			    			{
		        			    				startRecordIndex = (instructionCapMakerVO.getCurrentPageLink()-1)*no;
		        			    				endRecordIndex = no;
		        			    				logger.info("startRecordIndex .................... "+startRecordIndex);
		        			    				logger.info("endRecordIndex .................... "+endRecordIndex);
		        			    			}
		        			    			bufInsSql.append(" ORDER BY c.pdc_loan_id OFFSET ");
		        							bufInsSql.append(startRecordIndex);
		        							bufInsSql.append(" ROWS FETCH next ");
		        							bufInsSql.append(endRecordIndex);
		        							bufInsSql.append(" ROWS ONLY ");
		        			    			
		        			 			//		bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
		        			 				
		        			    			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
		        			    			//}
		        			    			logger.info("query : "+bufInsSql.toString());

		        		                  mainList = ConnectionDAO.sqlSelect(bufInsSql.toString());

		               	for(int i=0;i<mainList.size();i++){
		               		
		               		subList=(ArrayList)mainList.get(i);
		               		
		               			if(subList.size()>0){
		               				
										InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
										loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
										loanvo.setLoanAccNo("<a href=\"#\" onclick=\"editReleaseInstrumentAuthor('"+((CommonFunction.checkNull(subList.get(0)).trim()))+"');\">" +
												 ((CommonFunction.checkNull(subList.get(1)).trim()))+"</a>");

										loanvo.setProduct((CommonFunction.checkNull(subList.get(2)).trim()));
										
										Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
										
										loanvo.setLoanAmount(myFormatter.format(loanAmount));
										
										//loanvo.setLoanAmount((CommonFunction.checkNull(subList.get(3)).trim()));
										loanvo.setScheme((CommonFunction.checkNull(subList.get(4)).trim()));
										loanvo.setBranch((CommonFunction.checkNull(subList.get(5)).trim()));
										loanvo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(6)).trim()));
										loanvo.setCustomerName((CommonFunction.checkNull(subList.get(7)).trim()));
										loanvo.setUserName((CommonFunction.checkNull(subList.get(8)).trim()));
										loanvo.setTotalRecordSize(count);
										arrList.add(loanvo);
		               			}
		               	}

		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		        finally
		        {
		        	lbxLoanNoHID = null;
		        	loanAccNo = null;
		        	instrumentType = null;
		        	bank = null;
		        	branch = null;
		        	lbxBankID = null;
		        	lbxBranchID = null;
		        	product = null;
		        	instrumentNo = null;
		        	status = null;
		        	customerName = null;
		        	lbxProductID = null;
		        }
			return arrList;
		}
	
	 public ArrayList<InstructionCapMakerVO> getValuesforReleaseInstrument(int id) {
			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
			StringBuilder query = new StringBuilder();
			logger.info("In getValuesforHoldInstrument: ");
			try{

					  ArrayList mainlist=new ArrayList();
					  ArrayList subList=new ArrayList();
					  query.append("select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID, a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO, a.PDC_INSTRUMENT_AMOUNT,");
					  query.append(dbo);
					  query.append("DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'"+dateFormat+"') as PDC_INSTRUMENT_DATE,a.pdc_status,a.pdc_instrument_id,a.PDC_INSTL_NO,  a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE, a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER,a.HOLD_REMARKS,");
					  query.append(dbo);
					  query.append("DATE_FORMAT(CR.INSTL_DATE,'"+dateFormat+"') as INSTL_DATE  from cr_pdc_instrument_dtl a LEFT OUTER JOIN com_bank_m b ON(a.PDC_ISSUEING_BANK_ID = b.BANK_ID )LEFT OUTER JOIN com_bankbranch_m c ON(a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID )LEFT OUTER JOIN cr_loan_dtl d ON(a.pdc_loan_id = d.loan_id )LEFT OUTER JOIN cr_repaysch_dtl cr ON(A.PDC_LOAN_ID=CR.LOAN_ID AND A.PDC_INSTL_NO=CR.INSTL_NO ) where a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and a.pdc_status in ('A','H') and a.PRESENTATION_DATE is null  and a.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))   order by a.PDC_INSTRUMENT_DATE");
//					  
//					  String query="select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID," +
//					  		" a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO," +
//					  		" a.PDC_INSTRUMENT_AMOUNT,DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'"+dateFormat+"'),a.pdc_status,a.pdc_instrument_id,a.PDC_INSTL_NO, " +
//					  		" a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE," +
//					  		" a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER,a.HOLD_REMARKS" +
//					  		" from cr_pdc_instrument_dtl a," +
//					  		" com_bank_m b,com_bankbranch_m c,cr_loan_dtl d where a.PDC_ISSUEING_BANK_ID = b.BANK_ID and a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID  and" +
//					  		" a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and a.pdc_loan_id = d.loan_id and a.pdc_status in ('A','H')" +
//					  		" and a.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))  " +
//					  		" order by a.PDC_INSTRUMENT_DATE";
					  
			
					 logger.info(" getValuesforHoldInstrument query "+query.toString());
					 mainlist=ConnectionDAO.sqlSelect(query.toString());
					 logger.info("In getValuesforHoldInstrument list,,,,,"+mainlist.size());
			 
					 		for(int i=0;i<mainlist.size();i++){
					 			
									subList=(ArrayList)mainlist.get(i);
									logger.info("In getValuesforHoldInstrument sublist..."+subList.size());
				
									if(subList.size()>0){
										
					InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
					instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(0)).trim()));
					instructionCapMakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(1)).trim()));
					instructionCapMakerVO.setInstrumentNo((CommonFunction.checkNull(subList.get(2)).trim()));
					instructionCapMakerVO.setBank((CommonFunction.checkNull(subList.get(3)).trim()));
					instructionCapMakerVO.setLbxBankID((CommonFunction.checkNull(subList.get(4)).trim()));
					instructionCapMakerVO.setLbxBranchID((CommonFunction.checkNull(subList.get(5)).trim()));
					instructionCapMakerVO.setBranch((CommonFunction.checkNull(subList.get(6)).trim()));
					instructionCapMakerVO.setMicr((CommonFunction.checkNull(subList.get(7)).trim()));
					instructionCapMakerVO.setLocation((CommonFunction.checkNull(subList.get(8)).trim()));			
					instructionCapMakerVO.setBinNo((CommonFunction.checkNull(subList.get(9)).trim()));					
					Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());
					instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
					//instructionCapMakerVO.setInstrumentAmount((CommonFunction.checkNull(subList.get(10)).trim()));
					instructionCapMakerVO.setPdcDate((CommonFunction.checkNull(subList.get(11)).trim()));
					instructionCapMakerVO.setStatus((CommonFunction.checkNull(subList.get(12)).trim()));
					instructionCapMakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(13)).trim()));
					instructionCapMakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(14)).trim()));					
					instructionCapMakerVO.setIfscCode((CommonFunction.checkNull(subList.get(15)).trim()));
					instructionCapMakerVO.setBankAccount((CommonFunction.checkNull(subList.get(16)).trim()));
					instructionCapMakerVO.setPurpose((CommonFunction.checkNull(subList.get(17)).trim()));
					instructionCapMakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(18)).trim()));
					instructionCapMakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(19)).trim()));
					instructionCapMakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(20)).trim()));
					instructionCapMakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(21)).trim()));
					instructionCapMakerVO.setHoldRemarks((CommonFunction.checkNull(subList.get(22)).trim()));
					instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(23)).trim()));
					instrumentList.add(instructionCapMakerVO);
					
					
									}
					 		}
				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					query = null;
				}

			return instrumentList;
		}
		public ArrayList<InstructionCapMakerVO> getValuesforIndiReleaseInstrument(
				int id, InstructionCapMakerVO instructionCapMakerVO) {
			
			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
			StringBuilder instrumentType = new StringBuilder();
			StringBuilder instrumentNo = new StringBuilder();
			StringBuilder date = new StringBuilder();
			
			logger.info("In getValuesforIndiReleaseInstrument: ");
//			String instrumentType="";
//	        String instrumentNo="";
//	        String date="";
	        ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();
			try{



	                    logger.info("In searchChargeData().....................................Dao Impl");
	                    instrumentType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim()));
	                    instrumentNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentNo()).trim()));
	                    logger.info("instrumentNo"+instrumentNo);
	                    date.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getDate()).trim()));

	           	               StringBuffer sbAppendToSQLCount=new StringBuffer();
	           	               StringBuffer bufInsSql =    new StringBuffer();

	           	               
         
	           	               
	                    bufInsSql.append( "select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID," );
	                    bufInsSql.append(" a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO," );
	                    bufInsSql.append(" a.PDC_INSTRUMENT_AMOUNT,");
	                    bufInsSql.append(dbo);
	                    bufInsSql.append("DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'"+dateFormat+"') as PDC_INSTRUMENT_DATE,a.pdc_status,a.pdc_instrument_id,a.PDC_INSTL_NO," );
	                    bufInsSql.append(" a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE," );
	                    bufInsSql.append(" a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER" );				  		
	                    bufInsSql.append(" from cr_pdc_instrument_dtl a,com_bank_m b,com_bankbranch_m c,cr_loan_dtl d where " );
	                    bufInsSql.append(" a.PDC_ISSUEING_BANK_ID = b.BANK_ID and a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID  and" );
	                    bufInsSql.append(" a.pdc_loan_id='"+id+"' and a.pdc_loan_id = d.loan_id and a.pdc_status in ('A','H')" );
	                    bufInsSql.append(" and a.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))");
	                    
	                    
	            

	           	              if((!((instrumentType.toString()).equalsIgnoreCase("")))) {
	           	                  bufInsSql.append(" AND a.PDC_INSTRUMENT_MODE='"+instrumentType+"'");

	           	               }

	           	               if((!((instrumentNo.toString()).equalsIgnoreCase("")))) {
	           	                   bufInsSql.append("  AND a.pdc_instrument_no='"+instrumentNo+"'");

	           	               }
	           	              if((!((date.toString()).equalsIgnoreCase("")))) {
	           	                   bufInsSql.append("  AND a.PDC_INSTRUMENT_DATE=");
	           	                   bufInsSql.append(dbo);
	           	                   bufInsSql.append("STR_TO_DATE('"+date+"','"+dateFormatWithTime+"')");

	           	               }
	           	              
	           	           bufInsSql.append(" order by a.PDC_INSTRUMENT_DATE ");
//	           	               if((!(date.equalsIgnoreCase("")))) {
//	           	                   bufInsSql.append("  AND a.PDC_INSTRUMENT_TYPE='"+date+"'");
//	           	                   appendSQL=true;
//	           	               }
//

	           	               logger.info("Query-----"+bufInsSql.toString());
	          	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


			for(int i=0;i<mainlist.size();i++){

				subList=(ArrayList)mainlist.get(i);
				logger.info("In getValuesforHoldInstrument..."+subList.size());
				
				if(subList.size()>0){
					
						InstructionCapMakerVO instructionCapmakerVO=new InstructionCapMakerVO();
						instructionCapmakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(0)).trim()));
						instructionCapmakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(1)).trim()));
						instructionCapmakerVO.setInstrumentNo((CommonFunction.checkNull(subList.get(2)).trim()));
						instructionCapmakerVO.setBank((CommonFunction.checkNull(subList.get(3)).trim()));
						instructionCapmakerVO.setLbxBankID((CommonFunction.checkNull(subList.get(4)).trim()));
						instructionCapmakerVO.setLbxBranchID((CommonFunction.checkNull(subList.get(5)).trim()));
						instructionCapmakerVO.setBranch((CommonFunction.checkNull(subList.get(6)).trim()));
						instructionCapmakerVO.setMicr((CommonFunction.checkNull(subList.get(7)).trim()));
						instructionCapmakerVO.setLocation((CommonFunction.checkNull(subList.get(8)).trim()));
						instructionCapmakerVO.setBinNo((CommonFunction.checkNull(subList.get(9)).trim()));
						
						
						Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());
						instructionCapmakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
						//instructionCapmakerVO.setInstrumentAmount((CommonFunction.checkNull(subList.get(10)).trim()));
						instructionCapmakerVO.setDate((CommonFunction.checkNull(subList.get(11)).trim()));
						instructionCapmakerVO.setStatus((CommonFunction.checkNull(subList.get(12)).trim()));
						instructionCapmakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(13)).trim()));
						instructionCapmakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(14)).trim()));
						
						instructionCapmakerVO.setIfscCode((CommonFunction.checkNull(subList.get(15)).trim()));
						instructionCapmakerVO.setBankAccount((CommonFunction.checkNull(subList.get(16)).trim()));
						instructionCapmakerVO.setPurpose((CommonFunction.checkNull(subList.get(17)).trim()));
						instructionCapmakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(18)).trim()));
						instructionCapmakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(19)).trim()));
						instructionCapmakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(20)).trim()));
						instructionCapmakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(21)).trim()));



						
						
						
						
						instrumentList.add(instructionCapmakerVO);
				}
		}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				instrumentType = null;
				instrumentNo = null;
				date = null;
			}

			return instrumentList;
		}
		public boolean updateIndiReleaseInstrument(String[] checkedholdList,
				String[] checkedStatusList, String[] instrumentidList,
				String[] newStatusList,
				InstructionCapMakerVO instructionCapMakerVO) {
			
			ArrayList qryList =  new ArrayList();
			boolean status=false;
			String holdRE="";
			StringBuffer bufInsSql =null;
			PrepStmtObject insertPrepStmtObject = null;	
			logger.info("In updateIndiReleaseInstrument.");
			try{
				for(int i=0;i<checkedholdList.length;i++)
				{
					holdRE=checkedholdList[i];
					
					if(checkedholdList[i].equalsIgnoreCase("test")){
						holdRE="";
					}
					
					bufInsSql =	new StringBuffer();
					 insertPrepStmtObject = new PrepStmtObject();	
					 
					 bufInsSql.append("insert into cr_pdc_hold_reason_dtl(pdc_instrument_id,to_status,from_status," +
					 		" hold_reason,hold_reason_status,main_status,maker_id,maker_date)");
					 
							 bufInsSql.append(" values ( ");
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" 'L'," );
							 bufInsSql.append(" 'F'," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(dbo);
							 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )");
								
								
							
							 logger.info("In.............updateIndiReleaseInstrument"+bufInsSql.toString());
							 if(CommonFunction.checkNull(instrumentidList[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(instrumentidList[i]).trim()));
							 
								if(CommonFunction.checkNull(newStatusList[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(newStatusList[i]).trim()));
								
								if(CommonFunction.checkNull(checkedStatusList[i]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(checkedStatusList[i]).trim()));
								
								if(CommonFunction.checkNull(holdRE).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(holdRE).trim()));
								
								if(CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(instructionCapMakerVO.getMakerID().trim())));
								
								if(CommonFunction.checkNull(instructionCapMakerVO.getMakerDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(instructionCapMakerVO.getMakerDate().trim())));
								
								insertPrepStmtObject.setSql(bufInsSql.toString());
								qryList.add(insertPrepStmtObject);

					}
			              status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		                logger.info("In Applicant......................"+status);

			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				bufInsSql=null;
			}
			return status;
		
		}
		public ArrayList<InstructionCapMakerVO> getValuesforReleaseInstrumentAuthor(int id) {
			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
			StringBuilder query = new StringBuilder();
			logger.info("In getValuesforReleaseInstrumentAuthor: ");
			try{

			  ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();
			  query.append("select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID, a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO, a.PDC_INSTRUMENT_AMOUNT,");
			  query.append(dbo);
			  query.append("DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'"+dateFormat+"') as PDC_INSTRUMENT_DATE,a.pdc_status,a.pdc_instrument_id, e.hold_reason,e.from_status,e.to_status,a.PDC_INSTL_NO, a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE, a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER,");
			  query.append(dbo);
			  query.append("DATE_FORMAT(CR.INSTL_DATE,'"+dateFormat+"') as INSTL_DATE  from cr_pdc_instrument_dtl a LEFT OUTER JOIN com_bank_m b ON(a.PDC_ISSUEING_BANK_ID = b.BANK_ID )LEFT OUTER JOIN com_bankbranch_m c ON(a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID )LEFT OUTER JOIN cr_loan_dtl d ON(a.pdc_loan_id = d.loan_id )LEFT OUTER JOIN cr_repaysch_dtl cr ON(A.PDC_LOAN_ID=CR.LOAN_ID AND A.PDC_INSTL_NO=CR.INSTL_NO )left outer join cr_pdc_hold_reason_dtl e on(a.pdc_instrument_id = e.pdc_instrument_id )where e.main_status='F' and e.hold_reason_status='L'  and a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'  and a.pdc_status in ('A','H')   order by a.PDC_INSTRUMENT_DATE ");
			 logger.info("In getValuesforReleaseInstrumentAuthor "+query.toString());
			 mainlist=ConnectionDAO.sqlSelect(query.toString());
			 logger.info("In getValuesforReleaseInstrumentAuthor,,,,,"+mainlist.size());
			 
			 			for(int i=0;i<mainlist.size();i++){

							subList=(ArrayList)mainlist.get(i);
							logger.info("In getValuesforHoldInstrumentAuthor..."+subList.size());
				
								if(subList.size()>0){
					
										InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
										instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(0)).trim()));
										instructionCapMakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(1)).trim()));
										instructionCapMakerVO.setInstrumentNo((CommonFunction.checkNull(subList.get(2)).trim()));
										instructionCapMakerVO.setBank((CommonFunction.checkNull(subList.get(3)).trim()));
										instructionCapMakerVO.setLbxBankID((CommonFunction.checkNull(subList.get(4)).trim()));
										instructionCapMakerVO.setLbxBranchID((CommonFunction.checkNull(subList.get(5)).trim()));
										instructionCapMakerVO.setBranch((CommonFunction.checkNull(subList.get(6)).trim()));
										instructionCapMakerVO.setMicr((CommonFunction.checkNull(subList.get(7)).trim()));
										instructionCapMakerVO.setLocation((CommonFunction.checkNull(subList.get(8)).trim()));
										instructionCapMakerVO.setBinNo((CommonFunction.checkNull(subList.get(9)).trim()));
										
										Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());
										instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
										//instructionCapMakerVO.setInstrumentAmount((CommonFunction.checkNull(subList.get(10)).trim()));
										instructionCapMakerVO.setPdcDate((CommonFunction.checkNull(subList.get(11)).trim()));
										instructionCapMakerVO.setStatus((CommonFunction.checkNull(subList.get(12)).trim()));
										instructionCapMakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(13)).trim()));
										instructionCapMakerVO.setHoldReason((CommonFunction.checkNull(subList.get(14)).trim()));
										instructionCapMakerVO.setFromStatus((CommonFunction.checkNull(subList.get(15)).trim()));
										instructionCapMakerVO.setToStatus((CommonFunction.checkNull(subList.get(16)).trim()));
										instructionCapMakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(17)).trim()));
										
										instructionCapMakerVO.setIfscCode((CommonFunction.checkNull(subList.get(18)).trim()));
										instructionCapMakerVO.setBankAccount((CommonFunction.checkNull(subList.get(19)).trim()));
										instructionCapMakerVO.setPurpose((CommonFunction.checkNull(subList.get(20)).trim()));
										instructionCapMakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(21)).trim()));
										instructionCapMakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(22)).trim()));
										instructionCapMakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(23)).trim()));
		                                instructionCapMakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(24)).trim()));
		                                instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(25)).trim()));

										
										instrumentList.add(instructionCapMakerVO);
								}
			 			}
					}catch(Exception e){
						e.printStackTrace();
					}
					finally
					{
						query = null;
					}

			return instrumentList;
		}
public boolean updateCommentNDecisionforReleaseIns(InstructionCapMakerVO instructionCapMakerVO, String[] instrumentID) {
			
			//These values cant be null...
						
			           logger.info("In updateCommentNDecisionforReleaseIns");
						boolean status=false;
						try{

								ArrayList queryList=new ArrayList();
								ArrayList deleteList= new ArrayList();
								logger.info("In updateCommentNDecisionforReleaseIns"+instructionCapMakerVO.getDecision());

				if((instructionCapMakerVO.getDecision()).equalsIgnoreCase("L")){

									for(int i=0;i<instrumentID.length;i++){
										
										StringBuilder query = new StringBuilder();

										query.append("update cr_pdc_instrument_dtl set pdc_status=(select to_status from cr_pdc_hold_reason_dtl where main_status = 'F' and pdc_instrument_id='"+instrumentID[i]+"'),");
										query.append(" pdc_remarks='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getComments()).trim())+"',author_id='"+instructionCapMakerVO.getMakerID()+"',author_date=");
										query.append(dbo);
										query.append("STR_TO_DATE('"+instructionCapMakerVO.getMakerDate()+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
										
										
										query.append(" where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID[i]).trim())+"' ");
										logger.info("In updateCommentNDecisionforHoldIns,,,,,"+query.toString());
										queryList.add(query.toString());
										
										query = null;
									}

						         status =ConnectionDAO.sqlInsUpdDelete(queryList);
						
									if(status){
						            	logger.info("In status");
						            	for(int i=0;i<instrumentID.length;i++){
						            		
						            	StringBuilder deleteQuery = new StringBuilder();
						            	
						            	deleteQuery.append("update cr_pdc_hold_reason_dtl set main_status='A' where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID[i]).trim())+"'");
						            	logger.info("in delete "+deleteQuery.toString());
						            	deleteList.add(deleteQuery.toString());
						            	
						            	deleteQuery = null;
				
						            	}
						            status =ConnectionDAO.sqlInsUpdDelete(deleteList);
						            }
									logger.info("In updateCommentNDecisionforReleaseIns,,,,,"+status);


				}else{
							for(int i=0;i<instrumentID.length;i++){

								StringBuilder query = new StringBuilder();
								
								query.append("update cr_pdc_instrument_dtl set pdc_status=(select from_status from cr_pdc_hold_reason_dtl where main_status = 'F' and pdc_instrument_id='"+instrumentID[i]+"')," );
								query.append(" pdc_remarks='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getComments()).trim())+"',author_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim())+"',author_date=");
								query.append(dbo);
								query.append("STR_TO_DATE('"+instructionCapMakerVO.getMakerDate()+"','"+dateFormatWithTime+"') where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID[i]).trim())+"' ");
								 logger.info("in delete "+query.toString());
								queryList.add(query.toString());
								
								query = null;
								}

								status =ConnectionDAO.sqlInsUpdDelete(queryList);
								if(status){
					            	logger.info("In status");
					            	for(int i=0;i<instrumentID.length;i++){
					            		
					            	StringBuilder deleteQuery = new StringBuilder();
					            	
					            	deleteQuery.append("update cr_pdc_hold_reason_dtl set main_status='A' where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID[i]).trim())+"'");
					            	 logger.info("in delete "+deleteQuery.toString());
					            	deleteList.add(deleteQuery.toString());
					            	
					            	deleteQuery = null;

					            	}
					            status =ConnectionDAO.sqlInsUpdDelete(deleteList);
					            }
								logger.info("In updateCommentNDecisionforHoldIns,,,,,"+status);

					}
						}catch(Exception e){
							e.printStackTrace();
						}

						return status;
				}
		

}
