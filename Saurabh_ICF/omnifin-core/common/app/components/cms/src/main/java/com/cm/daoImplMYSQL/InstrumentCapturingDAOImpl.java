package com.cm.daoImplMYSQL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.cm.dao.InstrumentCapturingDAO;
import com.cm.vo.InstructionCapMakerVO;
import com.cm.vo.PoolIdMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;


public class InstrumentCapturingDAOImpl implements InstrumentCapturingDAO{
	private static final Logger logger = Logger.getLogger(InstrumentCapturingDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
	public ArrayList<InstructionCapMakerVO> searchInstrument(InstructionCapMakerVO instructionCapMakerVO) throws SQLException {


	    	int count=0;
	    	int startRecordIndex=0;
	    	int endRecordIndex = no;
	        ArrayList<InstructionCapMakerVO> arrList = new ArrayList<InstructionCapMakerVO>();
	        ArrayList mainList = new ArrayList();
	        ArrayList subList = new ArrayList();
	        
	        StringBuilder lbxLoanNoHID = new StringBuilder(); 
	        StringBuilder loanAccNo = new StringBuilder(); 
	        StringBuilder instrumentType = new StringBuilder(); 
	        StringBuilder bank = new StringBuilder(); 
	        StringBuilder branch = new StringBuilder(); 
	        StringBuilder lbxBankID = new StringBuilder(); 
	        StringBuilder lbxBranchID = new StringBuilder(); 
	        StringBuilder product = new StringBuilder(); 
	        StringBuilder lbxProductID = new StringBuilder(); 
//	        StringBuilder customerName = new StringBuilder(); 

	    	logger.info("In searchInstrument().. getReportingToUserId ..+++ "+instructionCapMakerVO.getReportingToUserId());

	        
	           try{
	        	   
	                  lbxLoanNoHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim()));
	                  loanAccNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLoanAccNo()).trim()));
	                  instrumentType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim()));
	                  bank.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBank()).trim()));
	                  branch.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBranch()).trim()));
	                  lbxBankID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()));
	                  lbxBranchID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()));
	                  product.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getProduct()).trim()));
	                  lbxProductID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim()));
//	                  customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getCustomerName()).trim()));
//	                  logger.info("customerName"+customerName);
	                  boolean appendSQL=false;

	                  StringBuffer sbAppendToSQLCount=new StringBuffer();
	                  StringBuffer bufInsSql =    new StringBuffer();
					  
					  bufInsSql.append(" SELECT DISTINCT CPI.PDC_LOAN_ID,CLD.LOAN_NO,CPM.PRODUCT_DESC,CLD.LOAN_LOAN_AMOUNT,CBM.BRANCH_DESC, CSM.SCHEME_DESC,");
					  bufInsSql.append(" DATE_FORMAT(CLD.LOAN_APPROVAL_DATE,'"+dateFormat+"'),GCM.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CPI.MAKER_ID) MAKER_ID ");
					  if(CommonFunction.checkNull(lbxLoanNoHID.toString()).trim().equalsIgnoreCase(""))
			    	  {
						  bufInsSql.append(" FROM CR_LOAN_DTL CLD INNER JOIN CR_PDC_INSTRUMENT_DTL CPI ON CLD.LOAN_ID = CPI.PDC_LOAN_ID AND (CPI.PDC_STATUS ='P' AND CPI.MAKER_ID ='"+instructionCapMakerVO.getReportingToUserId()+"') ");
			    	  }else
			    	  {
			    		  bufInsSql.append(" FROM CR_LOAN_DTL CLD INNER JOIN CR_PDC_INSTRUMENT_DTL CPI ON CLD.LOAN_ID = CPI.PDC_LOAN_ID AND ((CPI.PDC_STATUS ='P' AND CPI.MAKER_ID ='"+instructionCapMakerVO.getReportingToUserId()+"') OR CPI.PDC_STATUS NOT IN ('P','F')) AND CLD.LOAN_REPAYMENT_TYPE='I'");
			    	  }
					  bufInsSql.append(" INNER JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID = CLD.LOAN_PRODUCT INNER JOIN CR_SCHEME_M CSM ON CSM.SCHEME_ID = CLD.LOAN_SCHEME ");
					  bufInsSql.append(" INNER JOIN COM_BRANCH_M CBM ON CBM.BRANCH_ID = CLD.LOAN_BRANCH INNER JOIN GCD_CUSTOMER_M GCM ON GCM.CUSTOMER_ID = CLD.LOAN_CUSTOMER_ID ");
					 // bufInsSql.append(" INNER JOIN (SELECT PDC_LOAN_ID , MAX(PDC_INSTRUMENT_ID) AS PID FROM CR_PDC_INSTRUMENT_DTL GROUP BY PDC_LOAN_ID) ABC ON CPI.PDC_LOAN_ID = ABC.PDC_LOAN_ID AND CPI.PDC_INSTRUMENT_ID = ABC.PID ");
					  bufInsSql.append(" WHERE   CLD.LOAN_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl WHERE USER_ID='"+instructionCapMakerVO.getReportingToUserId()+"' AND REC_STATUS='A')");

					  sbAppendToSQLCount.append(" SELECT COUNT(DISTINCT CPI.PDC_LOAN_ID) FROM CR_PDC_INSTRUMENT_DTL CPI "); 
					  if(CommonFunction.checkNull(lbxLoanNoHID.toString()).trim().equalsIgnoreCase(""))
			    	  {
					      sbAppendToSQLCount.append(" INNER JOIN CR_LOAN_DTL CLD on CLD.LOAN_ID = CPI.PDC_LOAN_ID  AND (CPI.PDC_STATUS ='P' AND CPI.MAKER_ID='"+instructionCapMakerVO.getReportingToUserId()+"') ");
			    	  }
					  else
					  {
						  sbAppendToSQLCount.append(" INNER JOIN CR_LOAN_DTL CLD on CLD.LOAN_ID = CPI.PDC_LOAN_ID  AND ((CPI.PDC_STATUS ='P' AND CPI.MAKER_ID='"+instructionCapMakerVO.getReportingToUserId()+"') OR CPI.PDC_STATUS NOT IN ('P','F')) ");
					  }
					 // sbAppendToSQLCount.append(" INNER JOIN (SELECT PDC_LOAN_ID , MAX(PDC_INSTRUMENT_ID) AS PID FROM CR_PDC_INSTRUMENT_DTL GROUP BY PDC_LOAN_ID) ABC ");
					 // sbAppendToSQLCount.append(" ON CPI.PDC_LOAN_ID = ABC.PDC_LOAN_ID AND CPI.PDC_INSTRUMENT_ID = ABC.PID ");
					  sbAppendToSQLCount.append(" WHERE   CLD.LOAN_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl WHERE USER_ID='"+instructionCapMakerVO.getReportingToUserId()+"' AND REC_STATUS='A')  "); 
	                  
//	                  bufInsSql.append("select distinct CPI.pdc_loan_id,CLD.loan_no,CPM.PRODUCT_DESC,CLD.LOAN_LOAN_AMOUNT,CBM.branch_desc, CSM.SCHEME_DESC,"+
//								"DATE_FORMAT(CLD.LOAN_APPROVAL_DATE,'"+dateFormat+"'),GCM.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=cpi.MAKER_ID) MAKER_ID from cr_loan_dtl CLD inner join cr_pdc_instrument_dtl CPI on cld.loan_id = cpi.PDC_LOAN_ID "+
//								"inner join cr_product_m CPM on cpm.PRODUCT_ID = cld.LOAN_PRODUCT inner join cr_scheme_m CSM on csm.SCHEME_ID = cld.LOAN_SCHEME "+
//								"inner join com_branch_m CBM on cbm.BRANCH_ID = cld.LOAN_BRANCH inner join gcd_customer_m GCM on gcm.customer_id = cld.LOAN_CUSTOMER_ID INNER JOIN (SELECT PDC_LOAN_ID , MAX(PDC_INSTRUMENT_ID) AS pid FROM cr_pdc_instrument_dtl GROUP BY PDC_LOAN_ID) ABC ON CPI.PDC_LOAN_ID = ABC.PDC_LOAN_ID AND CPI.PDC_INSTRUMENT_ID = ABC.pid "+
//								"where  CLD.LOAN_REPAYMENT_TYPE='I' AND NOT EXISTS (SELECT 1 FROM cr_pdc_instrument_dtl a WHERE a.pdc_loan_id = CLD.loan_id AND (pdc_status ='F' OR (a.pdc_status ='P' AND a.MAKER_ID!='"+instructionCapMakerVO.getReportingToUserId()+"'))) "+
//								"AND CLD.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");
//			                  
//			                 sbAppendToSQLCount.append(" select count(1) from (select distinct CPI.pdc_loan_id,CLD.loan_no,CPM.PRODUCT_DESC,CLD.LOAN_LOAN_AMOUNT,CBM.branch_desc, CSM.SCHEME_DESC,"+
//								"DATE_FORMAT(CLD.LOAN_APPROVAL_DATE,'"+dateFormat+"'),GCM.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=cpi.MAKER_ID) MAKER_ID from cr_loan_dtl CLD inner join cr_pdc_instrument_dtl CPI on cld.loan_id = cpi.PDC_LOAN_ID "+
//								"inner join cr_product_m CPM on cpm.PRODUCT_ID = cld.LOAN_PRODUCT inner join cr_scheme_m CSM on csm.SCHEME_ID = cld.LOAN_SCHEME "+
//								"inner join com_branch_m CBM on cbm.BRANCH_ID = cld.LOAN_BRANCH inner join gcd_customer_m GCM on gcm.customer_id = cld.LOAN_CUSTOMER_ID INNER JOIN (SELECT PDC_LOAN_ID , MAX(PDC_INSTRUMENT_ID) AS pid FROM cr_pdc_instrument_dtl GROUP BY PDC_LOAN_ID) ABC ON CPI.PDC_LOAN_ID = ABC.PDC_LOAN_ID AND CPI.PDC_INSTRUMENT_ID = ABC.pid "+
//								"where  CLD.LOAN_REPAYMENT_TYPE='I' AND NOT EXISTS (SELECT 1 FROM cr_pdc_instrument_dtl a WHERE a.pdc_loan_id = CLD.loan_id AND (pdc_status ='F' OR (a.pdc_status ='P' AND a.MAKER_ID!='"+instructionCapMakerVO.getReportingToUserId()+"'))) "+
//								" AND CLD.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");


	                 if((!((lbxLoanNoHID.toString()).equalsIgnoreCase("")))) {
	                     bufInsSql.append(" AND CPI.PDC_LOAN_ID='"+lbxLoanNoHID+"'");
	                     sbAppendToSQLCount.append(" AND CPI.PDC_LOAN_ID='"+lbxLoanNoHID+"'");
	                     appendSQL=true;

	                  }

	                  if((!((lbxBankID.toString()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  AND CPI.pdc_issueing_bank_id='"+lbxBankID+"'");
	                      sbAppendToSQLCount.append("  AND CPI.pdc_issueing_bank_id='"+lbxBankID+"'");
	                      appendSQL=true;
	                  }

	                  if((!((instrumentType.toString()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  AND CPI.PDC_INSTRUMENT_MODE='"+instrumentType+"'");
	                      sbAppendToSQLCount.append("  AND CPI.PDC_INSTRUMENT_MODE='"+instrumentType+"'");
	                      appendSQL=true;
	                  }

	                  if((!((lbxBranchID.toString()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  AND CPI.pdc_issueing_branch_id='"+lbxBranchID+"'");
	                      sbAppendToSQLCount.append("  AND CPI.pdc_issueing_branch_id='"+lbxBranchID+"'");
	                      appendSQL=true;
	                  }
	                  if((!((lbxProductID.toString()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  AND CLD.LOAN_PRODUCT ='"+lbxProductID+"'");
	                      sbAppendToSQLCount.append("  AND CLD.LOAN_PRODUCT ='"+lbxProductID+"'");
	                      appendSQL=true;
	                  }

//	                  if((!((customerName.toString()).equalsIgnoreCase("")))) {
//	                      bufInsSql.append("  AND GCM.CUSTOMER_NAME like '%"+customerName+"%'");
//	                      sbAppendToSQLCount.append("  AND GCM.CUSTOMER_NAME like '%"+customerName+"%'");
//	                      appendSQL=true;
//	                  }

	                  if((!(instructionCapMakerVO.getLbxUserId().equalsIgnoreCase("")))) {
	                	  bufInsSql.append(" AND CPI.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"' ");	
	                      sbAppendToSQLCount.append(" AND CPI.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"'");
	                      appendSQL = true;
	                  }

	                  
	                  bufInsSql.append(" group by  CPI.pdc_loan_id order by  CLD.LOAN_APPROVAL_DATE");
	                  //sbAppendToSQLCount.append(") as f");
//	                  sbAppendToSQLCount.append(" group by  CPI.pdc_loan_id order by CLD.LOAN_APPROVAL_DATE )AS B");
	                  logger.info("In searchInstrument()..count Query....  "+sbAppendToSQLCount.toString());
	                  
	                  count =Integer.parseInt(ConnectionDAO.singleReturn(sbAppendToSQLCount.toString()));
	      			  
	                  sbAppendToSQLCount = null;

	      			//if(((lbxLoanNoHID.toString()).trim()==null && (lbxBankID.toString()).trim()==null && (instrumentType.toString()).trim()==null && (lbxBranchID.toString()).trim()==null  && (lbxProductID.toString()).trim()==null && (customerName.toString()).trim()==null) || (lbxLoanNoHID.toString()).trim().equalsIgnoreCase("") && (lbxBankID.toString()).trim().equalsIgnoreCase("") && (instrumentType.toString()).trim().equalsIgnoreCase("") && (lbxBranchID.toString()).trim().equalsIgnoreCase("") && (lbxProductID.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") || instructionCapMakerVO.getCurrentPageLink()>1)
	    			//{
	    			
	    			logger.info("current PAge Link no .................... "+instructionCapMakerVO.getCurrentPageLink());
	    			if(instructionCapMakerVO.getCurrentPageLink()>1)
	    			{
	    				startRecordIndex = (instructionCapMakerVO.getCurrentPageLink()-1)*no;
	    				endRecordIndex = no;
	    				logger.info("startRecordIndex .................... "+startRecordIndex);
	    				logger.info("endRecordIndex .................... "+endRecordIndex);
	    			}
	    			
	    				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
	    			//}
	                  logger.info("In searchInstrument()..Main Query.... "+bufInsSql.toString());
	      			
	    			  mainList = ConnectionDAO.sqlSelect(bufInsSql.toString());
	    			  bufInsSql = null;
	      			
	      			int size=mainList.size();
	      			
	          for(int i=0;i<size;i++)
	          {
		     	subList=(ArrayList)mainList.get(i);
			  
		     	if(subList.size()>0)
		     		{					
				  		InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
						loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
						loanvo.setLoanAccNo("<a href=\"#\" onclick=\"editInstrument('"+((CommonFunction.checkNull(subList.get(0)).trim()))+"');\">" +
								 ((CommonFunction.checkNull(subList.get(1)).trim()))+"</a>");
						loanvo.setProduct((CommonFunction.checkNull(subList.get(2)).trim()));
						
						Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
						
						loanvo.setLoanAmount(myFormatter.format(loanAmount));
						
						loanvo.setBranch((CommonFunction.checkNull(subList.get(4)).trim()));
						loanvo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
						loanvo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(6)).trim()));
						loanvo.setCustomerName((CommonFunction.checkNull(subList.get(7)).trim()));
						loanvo.setReportingToUserId((CommonFunction.checkNull(subList.get(8)).trim()));
						loanvo.setTotalRecordSize(count);
						arrList.add(loanvo);
						loanvo=null;
			  		}
	          }
	          		subList.clear();
	          		mainList.clear();
	          		subList=null;
	          		mainList=null;
	          		
	           }catch(Exception e){
	        	   e.printStackTrace();
	           }
	           finally
	           {

	        	lbxLoanNoHID = null;
	   	        loanAccNo = null;
	   	        instrumentType = null;
	   	        bank  = null;
	   	       	branch = null;
	   	        lbxBankID = null;
	   	        lbxBranchID = null;
	   	        product = null;
	   	        lbxProductID = null;
//	   	        customerName = null;
	           }

		return arrList;
	}
	
	public ArrayList<InstructionCapMakerVO> searchInstrumentAuthor(
			InstructionCapMakerVO instructionCapMakerVO) {
			
			logger.info("In searchInstrumentAuthor ... ");
			
		    int count=0;
		    int startRecordIndex=0;
		    int endRecordIndex = no;
		        
		     ArrayList<InstructionCapMakerVO> arrList = new ArrayList<InstructionCapMakerVO>();
		     ArrayList mainList = new ArrayList();
		     ArrayList subList = new ArrayList();
		     StringBuilder lbxLoanNoHID = new StringBuilder(); 
		     StringBuilder loanAccNo = new StringBuilder(); 
		     StringBuilder instrumentType = new StringBuilder(); 
		     StringBuilder branch = new StringBuilder(); 
		     StringBuilder lbxBankID = new StringBuilder(); 
		     StringBuilder lbxBranchID = new StringBuilder(); 
		     StringBuilder product = new StringBuilder(); 
		     StringBuilder lbxProductID = new StringBuilder(); 
		     StringBuilder bank = new StringBuilder(); 
//		     StringBuilder customerName = new StringBuilder(); 
		     

			
		        try{
		              
		               lbxLoanNoHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim()));
		               loanAccNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLoanAccNo()).trim()));
		               instrumentType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim()));
		               bank.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBank()).trim()));
		               branch.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBranch()).trim()));
		               lbxBankID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()));
		               lbxBranchID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()));
		               product.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getProduct()).trim()));
		               lbxProductID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim()));
//		               customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getCustomerName()).trim()));

		               boolean appendSQL=false;

		               StringBuffer sbAppendToSQLCount=new StringBuffer();
		               StringBuffer bufInsSql =    new StringBuffer();
		               
		               
		               bufInsSql.append(" select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc," +
		                  		" d.SCHEME_DESC,DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"'),f.CUSTOMER_NAME , (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=C.MAKER_ID AND C.PDC_STATUS='F') MAKER_ID from cr_loan_dtl a, cr_pdc_instrument_dtl c," +
		                  		" cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and " +

		                  		" e.branch_id=a.loan_branch and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_SCHEME = d.SCHEME_ID and c.pdc_status='F' AND A.LOAN_BRANCH IN (SELECT BRANCH_ID FROM sec_user_branch_dtl WHERE USER_ID='"+instructionCapMakerVO.getUserID()+"' AND REC_STATUS='A') AND C.MAKER_ID!='"+instructionCapMakerVO.getUserID()+"' ");

//		               sbAppendToSQLCount.append(" select distinct count(1) from (select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc," +
//		                  		" d.SCHEME_DESC,DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"'),f.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=C.MAKER_ID AND C.PDC_STATUS='F') MAKER_ID from cr_loan_dtl a, cr_pdc_instrument_dtl c," +
//		                  		" cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and " +
//		                  		" e.branch_id=a.loan_branch and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_REPAYMENT_TYPE='I' and a.LOAN_SCHEME = d.SCHEME_ID and c.pdc_status='F' AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"' AND C.MAKER_ID!='"+instructionCapMakerVO.getUserID()+"'");

		               sbAppendToSQLCount.append(" SELECT COUNT(distinct c.PDC_LOAN_ID) FROM CR_PDC_INSTRUMENT_DTL C "); 
		               sbAppendToSQLCount.append(" INNER JOIN CR_LOAN_DTL A ON A.LOAN_ID = C.PDC_LOAN_ID  AND C.PDC_STATUS ='F' AND C.MAKER_ID !='"+instructionCapMakerVO.getUserID()+"'");
		               sbAppendToSQLCount.append(" WHERE A.LOAN_BRANCH IN (SELECT BRANCH_ID FROM sec_user_branch_dtl WHERE USER_ID='"+instructionCapMakerVO.getUserID()+"' AND REC_STATUS='A') "); 

		               
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

//		                  if((!((customerName.toString()).equalsIgnoreCase("")))) {
//		                      bufInsSql.append("  AND f.CUSTOMER_NAME like '%"+customerName+"%'");
//		                      appendSQL=true;
//		                  }
		                  if((!(instructionCapMakerVO.getLbxUserId().equalsIgnoreCase("")))) {
		                		bufInsSql.append(" AND C.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"' ");	
		                		sbAppendToSQLCount.append(" AND C.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"'");
		          			appendSQL = true;
		          	      }
		                  
		                  bufInsSql.append(" order by a.LOAN_APPROVAL_DATE");
//		                  sbAppendToSQLCount.append(" )as b ");

		               
		                  logger.info("In searchInstrumentAuthor()... COUNT QUERY ... "+sbAppendToSQLCount.toString());
		                  count =Integer.parseInt(ConnectionDAO.singleReturn(sbAppendToSQLCount.toString()));
		                  sbAppendToSQLCount=null; 

//		      			if(((lbxLoanNoHID.toString()).trim()==null && (lbxBankID.toString()).trim()==null && (instrumentType.toString()).trim()==null && (lbxBranchID.toString()).trim()==null  && (lbxProductID.toString()).trim()==null && (customerName.toString()).trim()==null) || ((lbxLoanNoHID.toString()).trim().equalsIgnoreCase("") && (lbxBankID.toString()).trim().equalsIgnoreCase("") && (instrumentType.toString()).trim().equalsIgnoreCase("") && (lbxBranchID.toString()).trim().equalsIgnoreCase("") && (lbxProductID.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("")) || instructionCapMakerVO.getCurrentPageLink()>1)
//		    			{
		    			
		    			logger.info("current PAge Link no .................... "+instructionCapMakerVO.getCurrentPageLink());
		    			if(instructionCapMakerVO.getCurrentPageLink()>1)
		    			{
		    				startRecordIndex = (instructionCapMakerVO.getCurrentPageLink()-1)*no;
		    				endRecordIndex = no;
		    				logger.info("startRecordIndex .................... "+startRecordIndex);
		    				logger.info("endRecordIndex .................... "+endRecordIndex);
		    			}
		    			
		    			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
//		    			}
		                  logger.info("In searchInstrumentAuthor()... Main QUERY ... "+bufInsSql.toString());

		                  mainList = ConnectionDAO.sqlSelect(bufInsSql.toString());
		                  bufInsSql=null;
		                  int size =mainList.size() ;

		       for(int i=0;i<size;i++)
		       {
		    	   
			     	 subList=(ArrayList)mainList.get(i);
			     	 
			    	if(subList.size()>0){
			    		
							InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
							loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
							loanvo.setLoanAccNo("<a href=\"#\" onclick=\"editInstrumentAuthor('"+((CommonFunction.checkNull(subList.get(0)).trim()))+"');\">" +
									((CommonFunction.checkNull(subList.get(1)).trim()))+"</a>");

							loanvo.setProduct((CommonFunction.checkNull(subList.get(2)).trim()));
							
							Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
							
							loanvo.setLoanAmount(myFormatter.format(loanAmount));
							
							//loanvo.setLoanAmount(CommonFunction.checkNull(subList.get(3)).trim());
							loanvo.setBranch((CommonFunction.checkNull(subList.get(4)).trim()));
							loanvo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
							loanvo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(6)).trim()));
							loanvo.setCustomerName((CommonFunction.checkNull(subList.get(7)).trim()));
							loanvo.setReportingToUserId((CommonFunction.checkNull(subList.get(8)).trim()));
							loanvo.setTotalRecordSize(count);
							arrList.add(loanvo);
							loanvo=null;
			    	}
		       }
		       subList.clear();
		       mainList.clear();
		       subList=null;
		       mainList=null;
		       
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		           finally
		           {
		        	   lbxLoanNoHID = null;
		        	   loanAccNo = null;
		        	   instrumentType = null;
		        	   branch  = null;
		        	   lbxBankID = null;
		        	   lbxBranchID = null;
		        	   product = null;
		        	   lbxProductID = null;
		        	   bank = null;
//		        	   customerName = null;
		        	   mainList = null;
		   	        	subList = null;
		           }

			return arrList;
		}
	public ArrayList instrumentPurposeList() {
		 
		ArrayList arrList = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList mainList = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		logger.info("In instrumentPurposeList () ...");
		try{
			
		query.append("select value,description from generic_master where generic_key = 'INSTRUMENT_PURPOSE' and rec_status ='A' ");
		logger.info("In instrumentPurposeList () ..query is .. "+query.toString());
		
		arrList = ConnectionDAO.sqlSelect(query.toString());
		int size=arrList.size();
		for(int i=0;i<size;i++){

			subList=(ArrayList)arrList.get(i);

				if(subList.size()>0)
				{	
						InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
						instructionCapMakerVO.setPdcPurposeValue((CommonFunction.checkNull(subList.get(0)).trim()));
						instructionCapMakerVO.setPdcPurposeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
						mainList.add(instructionCapMakerVO);
						instructionCapMakerVO = null;				
				}
			}
		arrList.clear();
		subList.clear();
		subList = null;
		arrList = null;
			
		}catch(Exception e){
			logger.debug("IOException In instrumentPurposeList() ==>> "+e.getMessage());
		}
		finally
		{
			query = null;
			subList = null;
			arrList = null;
		}
		
			
		return mainList;
	}
	public ArrayList instrumentecsTransactionCodeList() {
		 
		ArrayList arrList = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList mainList = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		logger.info("In instrumentecsTransactionCodeList...");
		try{
			
		query.append("select value,description from generic_master where generic_key = 'ECS_TRAN_CODE' and rec_status ='A' ");	
		arrList = ConnectionDAO.sqlSelect(query.toString());
		for(int i=0;i<arrList.size();i++){

			subList=(ArrayList)arrList.get(i);
			logger.info("In instrumentecsTransactionCodeList..."+subList.size());

				if(subList.size()>0){
	
						InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
						instructionCapMakerVO.setEcsTransactionCodeValue((CommonFunction.checkNull(subList.get(0)).trim()));
						instructionCapMakerVO.setEcsTransactionCodeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
						mainList.add(instructionCapMakerVO);
				
				}
				}
		
			
		}catch(Exception e){
			logger.debug("IOException In downLoadErrorLog() ==>> "+e.getMessage());
		}
		finally
		{
			query = null;
			subList = null;
			arrList = null;
		}
		
			
		return mainList;
	}
	
	public ArrayList instrumentspnserBnkBrncCodeList() {
		 
		ArrayList arrList = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList mainList = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		logger.info("In instrumentspnserBnkBrncCodeList...");
		try{
			
		query.append("select value,description from generic_master where generic_key = 'SPONSOR_BANK_CODE' and rec_status ='A' ");	
		arrList = ConnectionDAO.sqlSelect(query.toString());
		for(int i=0;i<arrList.size();i++){

			subList=(ArrayList)arrList.get(i);
			logger.info("In instrumentspnserBnkBrncCodeList..."+subList.size());

				if(subList.size()>0){
	
						InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
						instructionCapMakerVO.setSpnserBnkBrncCodeValue((CommonFunction.checkNull(subList.get(0)).trim()));
						instructionCapMakerVO.setSpnserBnkBrncCodeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
						mainList.add(instructionCapMakerVO);
				
				}
				}
		
			
		}catch(Exception e){
			logger.debug("IOException In downLoadErrorLog() ==>> "+e.getMessage());
		}
		finally
		{
			query = null;
			subList = null;
			arrList = null;
		}
			
		return mainList;
	}
	
	public ArrayList instrumentutilityNoList() {
		 
		ArrayList arrList = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList mainList = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		logger.info("In instrumentutilityNoList...");
		try{
			
		query.append("select value,description from generic_master where generic_key = 'UTILITY_NUMBER' and rec_status ='A' ");	
		arrList = ConnectionDAO.sqlSelect(query.toString());
		for(int i=0;i<arrList.size();i++){

			subList=(ArrayList)arrList.get(i);
			logger.info("In instrumentutilityNoList..."+subList.size());

				if(subList.size()>0){
	
						InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
						instructionCapMakerVO.setUtilityNoValue((CommonFunction.checkNull(subList.get(0)).trim()));
						instructionCapMakerVO.setUtilityNoDesc((CommonFunction.checkNull(subList.get(1)).trim()));
						mainList.add(instructionCapMakerVO);
				
				}
				}
		
			
		}catch(Exception e){
			logger.debug("IOException In downLoadErrorLog() ==>> "+e.getMessage());
		}
		finally
		{
			query = null;
			subList = null;
			arrList = null;
		}
			
		return mainList;
	}
	public String pdcPartialForward(){
		
		String 	pdcPartialForward="";
		StringBuilder query = new StringBuilder();
		try{
			
		query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'PDC_PARTIAL_FORWARD'");	
		logger.info("query------"+query);
		pdcPartialForward = ConnectionDAO.singleReturn(query.toString());
			
			
		}catch(Exception e){
		e.printStackTrace();
	}
		finally
		{
			query = null;
		}
		
			
		return pdcPartialForward;
	}
	
	public ArrayList<InstructionCapMakerVO> getValuesforUpdate(int loanID) {

		ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
		logger.info("In getValuesforUpdate: ");
		InstructionCapMakerVO instructionCapMakerVO=null;
		StringBuilder query = new StringBuilder(); 
			try{

			  ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();
			  
			  
			  
			  query.append("SELECT DATE_FORMAT(CP.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),CP.PDC_INSTRUMENT_NO,CR.INSTL_AMOUNT,CP.PDC_INSTRUMENT_AMOUNT,c.loan_id,c.loan_no, "+
	            " CP.pdc_instrument_id,CR.INSTL_No,CP.pdc_status,DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y'), B.BANK_NAME, d.BANK_BRANCH_NAME,"+ 
	            " CP.PDC_ISSUING_MICR_CODE, CP.PDC_ISSUING_IFSC_CODE, CP.PDC_ISSUEING_BANK_ACCOUNT, CP.PDC_PURPOSE,cp.pdc_instrument_mode," +
	            "  cp.ECS_TRANSACTION_CODE,cp.ECUSTOMER_ACCOUNT_TYPE,cp.SPONSOR_BANKBRANCH_CODE,cp.UTILITY_NUMBER,DATE_FORMAT(cp.MAKER_DATE,'%d-%m-%Y'),cp.MAKER_ID,CP.SUBMIT_BY,CP.NAME_OF_PDC_SUBMIT_BY,CP.PDC_LOCATION,Z.DESCRIPTION as PDC_CLEARING_TYPE,CP.MAKER_REMARKS,(IFNULL(cr.INSTL_AMOUNT,0)+IFNULL(cr.OTHER_CHARGES,0)) as TOTAL_EMI "+
	            " from cr_pdc_instrument_dtl CP left join cr_repaysch_dtl cr on CP.PDC_LOAN_ID=CR.LOAN_ID AND CP.PDC_INSTL_NO=CR.INSTL_NO"+
	            " left join cr_loan_dtl c on CP.PDC_LOAN_ID=c.LOAN_ID"+
	            " left join com_bank_m b on b.BANK_ID= cp.PDC_ISSUEING_BANK_ID "+
	            " left join com_bankbranch_m d  on d.BANK_BRANCH_ID=cp.PDC_ISSUEING_BRANCH_ID"+
	            " left join generic_master Z on Z.VALUE=CP.PDC_CLEARING_TYPE and GENERIC_KEY='CLEARING_TYPE' and Z.REC_STATUS='A' "+
	            " where  cp.pdc_status in ('P','A','H') and Cp.PDC_LOAN_ID = '"+loanID+"'"+
	            " order by CP.PDC_INSTRUMENT_DATE,cp.pdc_instrument_id");
			  
			  
			  
			  
//			  String query="SELECT DATE_FORMAT(CP.PDC_INSTRUMENT_DATE,'"+dateFormat+"'),CP.PDC_INSTRUMENT_NO,CR.INSTL_AMOUNT,CP.PDC_INSTRUMENT_AMOUNT,c.loan_id,c.loan_no," +
//		  		" CP.pdc_instrument_id,CR.INSTL_No,CP.pdc_status,DATE_FORMAT(CR.INSTL_DATE,'"+dateFormat+"')," +
//		  		" B.BANK_NAME, d.BANK_BRANCH_NAME, CP.PDC_ISSUING_MICR_CODE, CP.PDC_ISSUING_IFSC_CODE, CP.PDC_ISSUEING_BANK_ACCOUNT, CP.PDC_PURPOSE" +
//		  		" from cr_pdc_instrument_dtl CP, cr_repaysch_dtl CR ,cr_loan_dtl c,com_bank_m b, com_bankbranch_m d" +
//		  		" where CP.PDC_LOAN_ID=CR.LOAN_ID AND CP.PDC_INSTL_NO=CR.INSTL_NO" +
//		  		" AND CR.loan_id =c.loan_id and cp.pdc_status in ('P','A','H') " +
//		  		" and b.bank_code= cp.PDC_ISSUEING_BANK_ID and d.BANK_BRANCH_CODE=cp.PDC_ISSUEING_BRANCH_ID" +
//		  		" AND CR.LOAN_ID='"+loanID+"'" +
//		  		" order by CP.PDC_INSTRUMENT_DATE";

			  
			  
			logger.info("In insertListToGeneratePDC(getValuesforUpdate) QUERY  :  "+query);
			mainlist=ConnectionDAO.sqlSelect(query.toString());
			logger.info("In insertListToGeneratePDC LIST,,,,,"+mainlist.size());
			
				for(int i=0;i<mainlist.size();i++){

					subList=(ArrayList)mainlist.get(i);
					logger.info("In insertListToGeneratePDC sublist..."+subList.size());
					if(subList.size()>0){
							instructionCapMakerVO=new InstructionCapMakerVO();
							instructionCapMakerVO.setPdcDate((CommonFunction.checkNull(subList.get(0)).trim()));
							instructionCapMakerVO.setStartingChequeNo((CommonFunction.checkNull(subList.get(1)).trim()));							
							if((CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase(""))
							{								
								String installmentAmount = (CommonFunction.checkNull(subList.get(2)));
								instructionCapMakerVO.setInstallmentAmount(installmentAmount);
							}
							else
							{
								Number installmentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
								instructionCapMakerVO.setInstallmentAmount(myFormatter.format(installmentAmount));
							}
							Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
							instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
							instructionCapMakerVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(4)).trim()));
							instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(5)).trim()));
							instructionCapMakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(6)).trim()));
							instructionCapMakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(7)).trim()));
							instructionCapMakerVO.setPdcStatus((CommonFunction.checkNull(subList.get(8)).trim()));
							instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(9)).trim()));
							instructionCapMakerVO.setBank((CommonFunction.checkNull(subList.get(10)).trim()));
						    instructionCapMakerVO.setBranch(StringEscapeUtils.escapeHtml((CommonFunction.checkNull(subList.get(11)).trim())));
							instructionCapMakerVO.setMicr((CommonFunction.checkNull(subList.get(12)).trim()));
							instructionCapMakerVO.setIfscCode((CommonFunction.checkNull(subList.get(13)).trim()));
							instructionCapMakerVO.setBankAccount((CommonFunction.checkNull(subList.get(14)).trim()));
							instructionCapMakerVO.setPurpose((CommonFunction.checkNull(subList.get(15)).trim()));
							instructionCapMakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(16)).trim()));
							instructionCapMakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(17)).trim()));
							instructionCapMakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(18)).trim()));
							instructionCapMakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(19)).trim()));
							instructionCapMakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(20)).trim()));
							instructionCapMakerVO.setMakerDate((CommonFunction.checkNull(subList.get(21)).trim()));
							instructionCapMakerVO.setMaker((CommonFunction.checkNull(subList.get(22)).trim()));
							if(CommonFunction.checkNull(subList.get(23)).trim().equalsIgnoreCase("PRAPPL"))
								instructionCapMakerVO.setSubmitBy("APPLICANT");
							else if(CommonFunction.checkNull(subList.get(23)).trim().equalsIgnoreCase("COAPPL"))
								instructionCapMakerVO.setSubmitBy("CO APPLICANT");
							else 
								instructionCapMakerVO.setSubmitBy((CommonFunction.checkNull(subList.get(23)).trim()));
							instructionCapMakerVO.setPdcSubmitCustomerName((CommonFunction.checkNull(subList.get(24)).trim()));
							//Nishant starts
							instructionCapMakerVO.setLocation((CommonFunction.checkNull(subList.get(25)).trim()));
							instructionCapMakerVO.setClearingType((CommonFunction.checkNull(subList.get(26)).trim()));
							instructionCapMakerVO.setRemarks((CommonFunction.checkNull(subList.get(27)).trim()));
							Number totalEMI =myFormatter.parse((CommonFunction.checkNull(subList.get(28))).trim());
							instructionCapMakerVO.setTotalEMI(myFormatter.format(totalEMI));
							//Nishant Ends
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
	
	
	public String getInstallmentDiff(){
		
		String 	installmentDiff="";
		StringBuilder query = new StringBuilder();
		try{
			
		query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'INST_DIFF_COUNT'");	
		logger.info("query for installment difference------"+query);
		installmentDiff = ConnectionDAO.singleReturn(query.toString());
			
			
		}catch(Exception e){
		e.printStackTrace();
	}
		finally
		{
			query = null;
		}
		
			
		return installmentDiff;
	}

	public ArrayList instrumentcustomeracTypeList() {
		 
		ArrayList arrList = new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList mainList = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		logger.info("In instrumentcustomeracTypeList...");
		try{
			
		query.append("select value,description from generic_master where generic_key = 'CUST_ACC_TYPE' and rec_status ='A' ");	
		logger.info("Query for getting Customer A/C Type  :  "+query.toString());
		arrList = ConnectionDAO.sqlSelect(query.toString());
		for(int i=0;i<arrList.size();i++){

			subList=(ArrayList)arrList.get(i);
				if(subList.size()>0){
	
						InstructionCapMakerVO instructionCapMakerVO=new InstructionCapMakerVO();
						instructionCapMakerVO.setCustomeracTypeValue((CommonFunction.checkNull(subList.get(0)).trim()));
						instructionCapMakerVO.setCustomeracTypeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
						mainList.add(instructionCapMakerVO);
				
				}
				}
		
			
		}catch(Exception e){
			logger.debug("IOException In downLoadErrorLog() ==>> "+e.getMessage());
		}
		finally
		{
			query = null;
			subList = null;
			arrList = null;
		}
			
		return mainList;
	}
	public ArrayList insNonIns(int lbxLoanNoHID) {
		String flag="";
		ArrayList arrList=new ArrayList();
		ArrayList subList = new ArrayList();
		ArrayList getDataList =new ArrayList();
		StringBuilder query = new StringBuilder(); 
		int value=0;
		try{
			
			//query.append("select LOAN_INSTALLMENT_MODE,LOAN_ADVANCE_INSTL,LOAN_INSTALLMENT_TYPE from cr_loan_dtl where loan_id="+lbxLoanNoHID);
			query.append("select LOAN_INSTALLMENT_MODE,LOAN_REPAYMENT_TYPE,LOAN_ADVANCE_INSTL,LOAN_INSTALLMENT_TYPE,SUM(R.OTHER_CHARGES) TOTAL_CHARGE,(IFNULL(SUM(R.OTHER_CHARGES),0)+R.INSTL_AMOUNT) TOTAL_CHR_INST  FROM cr_loan_dtl L, cr_repaysch_dtl R WHERE R.LOAN_ID=L.LOAN_ID AND R.REC_TYPE NOT IN ('D','P') AND L.loan_id="+lbxLoanNoHID);
			arrList = ConnectionDAO.sqlSelect(query.toString());
			logger.info("In insNonIns..."+query);

			for(int i=0;i<arrList.size();i++){

				subList=(ArrayList)arrList.get(i);
				logger.info("In insNonIns..."+subList.size());

					if(subList.size()>0){

						    PoolIdMakerVO poolIdMakerVO1=new PoolIdMakerVO();
						    poolIdMakerVO1.setLoanInstallmentMode((CommonFunction.checkNull(subList.get(0)).trim()));
						    poolIdMakerVO1.setRepayment((CommonFunction.checkNull(subList.get(1)).trim()));
						    logger.info("In insNonIns...setRepayment" +poolIdMakerVO1.getRepayment());
						    
						    if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase("")){
						    	
						    	 poolIdMakerVO1.setLoanAdvanceInstallment("0");
						    	
						    }else{
						    
						    poolIdMakerVO1.setLoanAdvanceInstallment((CommonFunction.checkNull(subList.get(2)).trim()));
						    
						    }
						   
						    if(CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase("E"))
						    {
						    	poolIdMakerVO1.setInstallmentType("Eq. INSTALLMENT");
						    }
						    else if(CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase("G"))
						    {
						    	poolIdMakerVO1.setInstallmentType("Gr. INSTALLMENT");
						    }
						    else if(CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase("P"))
						    {
						    	poolIdMakerVO1.setInstallmentType("Eq. PRINCIPAL");
						    }
						    else if(CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase("Q"))
						    {
						    	poolIdMakerVO1.setInstallmentType("Gr. PRINCIPAL1");
						    }
						    else if(CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase("R"))
						    {
						    	poolIdMakerVO1.setInstallmentType("Gr. PRINCIPAL2");
						    }
						    
						    if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
							  {
								  poolIdMakerVO1.setOtherInstallmentCharges("0");
							  }
							  else
					    	    {
						    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
						    		poolIdMakerVO1.setOtherInstallmentCharges(myFormatter.format(reconNum));
					    	    }
						    
						    if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
							  {
								  poolIdMakerVO1.setTotalChargeInstallmentAmount("0");
							  }
							  else
					    	    {
						    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
						    		poolIdMakerVO1.setTotalChargeInstallmentAmount(myFormatter.format(reconNum));
					    	    }
						   						    
						    getDataList.add(poolIdMakerVO1);
					
					}
					}
			
				
			}catch(Exception e){
				logger.debug("IOException In getPoolData() ==>> "+e.getMessage());
			}
			finally
			{
				query = null;
				subList = null;
				arrList = null;
			}
				
			return getDataList;
	}
	public ArrayList<InstructionCapMakerVO> getLoanAccountList() {
		
		ArrayList<InstructionCapMakerVO> loanDetailList=new ArrayList<InstructionCapMakerVO>();
		StringBuilder query = new StringBuilder(); 
		logger.info("In getListOfValuesforInstrumentMaker: ");
		
		try{

				  ArrayList mainlist=new ArrayList ();
				  ArrayList subList=new ArrayList ();
				  query.append(" select loan_id,loan_no from cr_loan_dtl ");
		
				  mainlist=ConnectionDAO.sqlSelect(query.toString());
				  
				  	for(int i=0;i<mainlist.size();i++){
				  		
				  		subList=(ArrayList)mainlist.get(i);
				  		
				  			if(subList.size()>0){
				  				
										InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
										loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
										loanvo.setLoanAccNo((CommonFunction.checkNull(subList.get(1)).trim()));
										loanDetailList.add(loanvo);
				  				}
				  		}
				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					query = null;
				}

				return loanDetailList;

		}
	public boolean deleteInstrument(String id) {

		logger.info("In deleteInstrument()-------in dao impl ");
		StringBuilder query = new StringBuilder(); 
		StringBuilder query1 = new StringBuilder();
		StringBuilder deletequery = new StringBuilder();
		boolean status=false;
		try {
			ArrayList mainList = new ArrayList();
			ArrayList subList = new ArrayList();
			query.append("delete from cr_pdc_instrument_dtl where pdc_instrument_id ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
			mainList.add(query.toString());
			

		    status =ConnectionDAO.sqlInsUpdDelete(mainList);
		   query1.append(" select count(1) from cr_pdc_instrument_loan_dtl where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
		int count= Integer.parseInt(ConnectionDAO.singleReturn(query1.toString()));
		if(count>0){
			deletequery.append ("delete from cr_pdc_instrument_loan_dtl where pdc_instrument_id ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
			subList.add(deletequery.toString());
			logger.info(" delete instrument query"+deletequery.toString());
		  status =ConnectionDAO.sqlInsUpdDelete(subList);	
		}
		
		    
		} 
		catch (SQLException e) {
					e.printStackTrace();
				}
		finally
		{
			query = null;
			query1 = null;
			deletequery = null;
		}
				return status;
	}

	public ArrayList<InstructionCapMakerVO> getValuesforAuthorUpdate(int loanID) {

		ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
		StringBuilder query = new StringBuilder(); 
		logger.info("In getValuesforAuthorUpdate: ");
		
		try{
			InstructionCapMakerVO instructionCapMakerVO=null;
			  ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();

			  
			  
			  query.append("SELECT DATE_FORMAT(CP.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),CP.PDC_INSTRUMENT_NO,CR.INSTL_AMOUNT,CP.PDC_INSTRUMENT_AMOUNT,c.loan_id,c.loan_no, "+
	            " CP.pdc_instrument_id,CR.INSTL_No,CP.pdc_status,DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y'), B.BANK_NAME, d.BANK_BRANCH_NAME,"+ 
	            " CP.PDC_ISSUING_MICR_CODE, CP.PDC_ISSUING_IFSC_CODE, CP.PDC_ISSUEING_BANK_ACCOUNT, CP.PDC_PURPOSE,cp.pdc_instrument_mode, "+
	            "  cp.ECS_TRANSACTION_CODE,cp.ECUSTOMER_ACCOUNT_TYPE,cp.SPONSOR_BANKBRANCH_CODE,cp.UTILITY_NUMBER,CP.SUBMIT_BY,CP.NAME_OF_PDC_SUBMIT_BY,CP.PDC_LOCATION,Z.DESCRIPTION as PDC_CLEARING_TYPE,CP.MAKER_REMARKS,(IFNULL(cr.INSTL_AMOUNT,0)+IFNULL(cr.OTHER_CHARGES,0)) as TOTAL_EMI "+
	            " from cr_pdc_instrument_dtl CP left join cr_repaysch_dtl cr on CP.PDC_LOAN_ID=CR.LOAN_ID AND CP.PDC_INSTL_NO=CR.INSTL_NO"+
	            " left join cr_loan_dtl c on CP.PDC_LOAN_ID=c.LOAN_ID"+
	            " left join com_bank_m b on b.BANK_ID= cp.PDC_ISSUEING_BANK_ID "+
 				" left join generic_master Z on Z.VALUE=CP.PDC_CLEARING_TYPE and GENERIC_KEY='CLEARING_TYPE' and Z.REC_STATUS='A'"+
	            " left join com_bankbranch_m d  on d.BANK_BRANCH_ID=cp.PDC_ISSUEING_BRANCH_ID"+
	            " where  cp.pdc_status in ('F') and Cp.PDC_LOAN_ID = '"+loanID+"'"+
			    " order by CP.PDC_INSTRUMENT_DATE,cp.pdc_instrument_id");
			  
			  
			  
			  
			  
//			  String query="SELECT DATE_FORMAT(CP.PDC_INSTRUMENT_DATE,'"+dateFormat+"'),CP.PDC_INSTRUMENT_NO,CR.INSTL_AMOUNT,CP.PDC_INSTRUMENT_AMOUNT,c.loan_id,c.loan_no," +
//		  		" CP.pdc_instrument_id,CR.INSTL_No,CP.pdc_status,DATE_FORMAT(CR.INSTL_DATE,'"+dateFormat+"')," +
//		  		" B.BANK_NAME, d.BANK_BRANCH_NAME, CP.PDC_ISSUING_MICR_CODE, CP.PDC_ISSUING_IFSC_CODE, CP.PDC_ISSUEING_BANK_ACCOUNT, CP.PDC_PURPOSE" +
//		  		" from cr_pdc_instrument_dtl CP, cr_repaysch_dtl CR ,cr_loan_dtl c,com_bank_m b, com_bankbranch_m d" +
//		  		" where CP.PDC_LOAN_ID=CR.LOAN_ID AND CP.PDC_INSTL_NO=CR.INSTL_NO" +
//		  		" AND CR.loan_id =c.loan_id and cp.pdc_status in ('F') " +
//		  		" and b.bank_code= cp.PDC_ISSUEING_BANK_ID and d.BANK_BRANCH_CODE=cp.PDC_ISSUEING_BRANCH_ID" +
//		  		" AND CR.LOAN_ID='"+loanID+"'" +
//		  		" order by CP.PDC_INSTRUMENT_DATE";

			logger.info("In getValuesforAuthorUpdate QUERY"+query);
			mainlist=ConnectionDAO.sqlSelect(query.toString());
			logger.info("In getValuesforAuthorUpdate LIST,,,,,"+mainlist.size());
			
				for(int i=0;i<mainlist.size();i++){

					subList=(ArrayList)mainlist.get(i);
					logger.info("In getValuesforAuthorUpdate sublist..."+subList.size());
					if(subList.size()>0){
							instructionCapMakerVO=new InstructionCapMakerVO();
							instructionCapMakerVO.setPdcDate((CommonFunction.checkNull(subList.get(0)).trim()));
							instructionCapMakerVO.setStartingChequeNo((CommonFunction.checkNull(subList.get(1)).trim()));

							if((CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase("")){
								
								String installmentAmount = (CommonFunction.checkNull(subList.get(2)));
								instructionCapMakerVO.setInstallmentAmount(installmentAmount);
								}else{

								Number installmentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
																
								instructionCapMakerVO.setInstallmentAmount(myFormatter.format(installmentAmount));
								}
							//instructionCapMakerVO.setInstallmentAmount((CommonFunction.checkNull(subList.get(2)).trim()));
							Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
							instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
							
						//	instructionCapMakerVO.setInstrumentAmount((CommonFunction.checkNull(subList.get(3)).trim()));
							instructionCapMakerVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(4)).trim()));
							instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(5)).trim()));
							instructionCapMakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(6)).trim()));
							instructionCapMakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(7)).trim()));
							instructionCapMakerVO.setPdcStatus((CommonFunction.checkNull(subList.get(8)).trim()));
							instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(9)).trim()));
							
							instructionCapMakerVO.setBank((CommonFunction.checkNull(subList.get(10)).trim()));
							instructionCapMakerVO.setBranch(StringEscapeUtils.escapeHtml((CommonFunction.checkNull(subList.get(11)).trim())));
							instructionCapMakerVO.setMicr((CommonFunction.checkNull(subList.get(12)).trim()));
							instructionCapMakerVO.setIfscCode((CommonFunction.checkNull(subList.get(13)).trim()));
							instructionCapMakerVO.setBankAccount((CommonFunction.checkNull(subList.get(14)).trim()));
							instructionCapMakerVO.setPurpose((CommonFunction.checkNull(subList.get(15)).trim()));
							instructionCapMakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(16)).trim()));
							
							instructionCapMakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(17)).trim()));
							instructionCapMakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(18)).trim()));
							instructionCapMakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(19)).trim()));
							instructionCapMakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(20)).trim()));
							if(CommonFunction.checkNull(subList.get(21)).trim().equalsIgnoreCase("PRAPPL"))
								instructionCapMakerVO.setSubmitBy("APPLICANT");
							else if(CommonFunction.checkNull(subList.get(21)).trim().equalsIgnoreCase("COAPPL"))
								instructionCapMakerVO.setSubmitBy("CO APPLICANT");
							else 
								instructionCapMakerVO.setSubmitBy((CommonFunction.checkNull(subList.get(21)).trim()));
							instructionCapMakerVO.setPdcSubmitCustomerName((CommonFunction.checkNull(subList.get(22)).trim()));
							//Nishant starts
							instructionCapMakerVO.setLocation((CommonFunction.checkNull(subList.get(23)).trim()));
							instructionCapMakerVO.setClearingType((CommonFunction.checkNull(subList.get(24)).trim()));
							instructionCapMakerVO.setRemarks((CommonFunction.checkNull(subList.get(25)).trim()));
							Number totalEMI =myFormatter.parse((CommonFunction.checkNull(subList.get(26))).trim());
							instructionCapMakerVO.setTotalEMI(myFormatter.format(totalEMI));
							//Nishant Ends
							
							
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
	public boolean updateFlag(int id,InstructionCapMakerVO vo, String[] checkedIDArr, String[] checkedDateArr) {
		   boolean status=false;
		   ArrayList updateQuery =null;
		   int len=checkedIDArr.length; 
		   StringBuilder query = new StringBuilder();
			try{
				logger.info("In updateFlag(int id,InstructionCapMakerVO vo, String[] checkedIDArr, String[] checkedDateArr)  checkedDateArr "+checkedDateArr.length);
		if(checkedDateArr.length > 0)
		{					
				for(int i =0;i<len;i++)
				{	
					if(!StringEscapeUtils.escapeSql(CommonFunction.checkNull(checkedDateArr[i])).equalsIgnoreCase(""))
					{ 
						StringBuilder queryy = new StringBuilder();
						updateQuery = new ArrayList();
						queryy.append("update cr_pdc_instrument_dtl set PDC_INSTRUMENT_DATE=STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(checkedDateArr[i]))+"','"+dateFormatWithTime+"') "+
                                    " where PDC_INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(checkedIDArr[i]))+"'");

                    updateQuery.add(queryy.toString());	
                    logger.info("In updateFlag() query is.. "+queryy.toString());
                    queryy = null;
                	status =ConnectionDAO.sqlInsUpdDelete(updateQuery);
					}
				
					logger.info("In updateFlag(status) getListOfValuesupdate: "+status);
				}
		 }
				
				    ArrayList queryList=new ArrayList();
					query.append("update cr_pdc_instrument_dtl set pdc_status='F',maker_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerID()).trim())+"'" +
							" ,maker_date=DATE_ADD(STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim())+"','"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)  where pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and pdc_status='P'");
		
					queryList.add(query.toString());
					logger.info("In updateFlag(final): "+query);
					status =ConnectionDAO.sqlInsUpdDelete(queryList);
					logger.info("In updateFlag(status) getListOfValues: "+status);

			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query = null;
			}

			return status;
		}
	public boolean saveForLinkedLan(String[] loanIdList, String[] dateList,
			String[] installmentNoList,
			String[] installmentAmountMainList, String[] allotedAmountList,String instrumentID,
			InstructionCapMakerVO instructionCapMakerVO) {

		ArrayList qryList =  new ArrayList();
		boolean status=false;
		StringBuffer bufInsSql =null;
		PrepStmtObject insertPrepStmtObject = null;	
		logger.info("In saveForLinkedLan...Dao Impl.");
		try{
			for(int i=0;i<loanIdList.length;i++)
			{
			 bufInsSql =	new StringBuffer();
			 insertPrepStmtObject = new PrepStmtObject();	
			 
			 bufInsSql.append("insert into cr_pdc_instrument_loan_dtl(PDC_INSTRUMENT_ID,PDC_LOAN_ID,PDC_INSTL_NO,PDC_INSTL_DUE_DATE," +
			 		"PDC_INSTL_AMOUNT,PDC_ALLOC_AMOUNT,REC_STATUS)");
			 
					 bufInsSql.append(" values ( ");
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" ?," );
					 bufInsSql.append(" 'A' )" );

					 if(CommonFunction.checkNull(instrumentID).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(instrumentID).trim()));
					 
						if(CommonFunction.checkNull(loanIdList[i]).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(loanIdList[i]).trim()));
						
						if(CommonFunction.checkNull(installmentNoList[i]).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(installmentNoList[i]).trim()));
						
						if(CommonFunction.checkNull(dateList[i]).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(dateList[i]).trim()));
						
						if(CommonFunction.checkNull(installmentAmountMainList[i]).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(installmentAmountMainList[i]).trim())).toString());
						
						if(CommonFunction.checkNull(allotedAmountList[i]).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(allotedAmountList[i]).trim())).toString());
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						qryList.add(insertPrepStmtObject);
						 logger.info(insertPrepStmtObject.printQuery());
			}
			      logger.info(insertPrepStmtObject.printQuery());
	              status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	              logger.info("In saveForLinkedLan......................"+status);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return status;
	}
	
	  
	  
	  public boolean updateIndiDeleteInstrument(String[] checkedholdList, String[] checkedStatusList, String[] instrumentidList, String[] newStatusList, InstructionCapMakerVO instructionCapMakerVO) {

	        logger.info("In updateIndiDeleteInstrument...Dao Impl.");
			ArrayList qryList =  new ArrayList();
			boolean status=false;
			StringBuffer bufInsSql =null;
			PrepStmtObject insertPrepStmtObject = null;	
			
			try{
				for(int i=0;i<checkedholdList.length;i++)
				{
					String holdRE=checkedholdList[i];
					
					if(checkedholdList[i].equalsIgnoreCase("test")){
						holdRE="";
					}

					bufInsSql =	new StringBuffer();
					 insertPrepStmtObject = new PrepStmtObject();	
					 
					 bufInsSql.append("insert into cr_pdc_hold_reason_dtl(pdc_instrument_id,to_status,from_status,hold_reason,hold_reason_status,main_status,maker_id,maker_date)");
					 
							 bufInsSql.append(" values ( ");
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" 'X'," );
							 bufInsSql.append(" 'F'," );
							 bufInsSql.append(" ?," );
							 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" );
							
		
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
									insertPrepStmtObject.addString((CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim()));
								
								if(CommonFunction.checkNull(instructionCapMakerVO.getMakerDate()).trim().equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString((CommonFunction.checkNull(instructionCapMakerVO.getMakerDate()).trim()));
								
								insertPrepStmtObject.setSql(bufInsSql.toString());
								qryList.add(insertPrepStmtObject);

					}
			              status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					
					

			
		              logger.info("In updateIndiDeleteInstrument."+status);

			}catch(Exception e){
				e.printStackTrace();
			}
			return status;


		}
	
	  
		public boolean updateCommentNDecisionforHoldIns(InstructionCapMakerVO instructionCapMakerVO, String[] instrumentID) {
			 
		    logger.info("In updateCommentNDecisionforHoldIns");
			boolean status=false;
			try{

					ArrayList queryList=new ArrayList();
					ArrayList deleteList= new ArrayList();

					logger.info("In updateCommentNDecisionforHoldIns"+instructionCapMakerVO.getDecision());

					if((instructionCapMakerVO.getDecision()).equalsIgnoreCase("A")){

						for(int i=0;i<instrumentID.length;i++){

							StringBuilder query = new StringBuilder();
							
							query.append("update cr_pdc_instrument_dtl set pdc_status=(select to_status from cr_pdc_hold_reason_dtl where main_status='F' and  pdc_instrument_id='"+instrumentID[i]+"')," +
									" HOLD_REMARKS = (select hold_reason from cr_pdc_hold_reason_dtl where main_status='F' and  pdc_instrument_id='"+instrumentID[i]+"'),pdc_remarks='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getComments()).trim())+"',author_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim())+"',author_date=DATE_ADD(STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getMakerDate()).trim())+"','"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)  where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID[i]).trim())+"' ");
							logger.info("In updateCommentNDecisionforHoldIns,,,,,"+query);
							queryList.add(query.toString());
							
							query = null;
						}


			status =ConnectionDAO.sqlInsUpdDelete(queryList);
			logger.info("In updateCommentNDecisionforHoldIns,,,,,"+status);

            if(status){
            	
	            	logger.info("In status");
	            	for(int i=0;i<instrumentID.length;i++){
	            		
	            		StringBuilder updateQuery = new StringBuilder();
	            		
	            	updateQuery.append("update cr_pdc_hold_reason_dtl set main_status='A' where pdc_instrument_id='"+instrumentID[i]+"'");
	            	logger.info("in delete "+updateQuery);
	            	deleteList.add(updateQuery.toString());
	            	
	            	updateQuery = null;
            	}
            status =ConnectionDAO.sqlInsUpdDelete(deleteList);
            }
			}else{
					for(int i=0;i<instrumentID.length;i++){

						StringBuilder query = new StringBuilder();
						
						query.append("update cr_pdc_instrument_dtl set pdc_status=(select from_status from cr_pdc_hold_reason_dtl where main_status = 'F' and pdc_instrument_id='"+instrumentID[i]+"')," +
							     " pdc_remarks='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getComments()).trim())+"',author_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim())+"',author_date=STR_TO_DATE('"+instructionCapMakerVO.getMakerDate()+"','"+dateFormatWithTime+"') where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID[i]).trim())+"' ");
							 logger.info("in update "+query);
							queryList.add(query.toString());
							
							query = null;
					}

				status =ConnectionDAO.sqlInsUpdDelete(queryList);
				logger.info("In updateCommentNDecisionforHoldIns,,,,,"+status);

		            if(status){
		            	
		            	logger.info("In status");
		            	for(int i=0;i<instrumentID.length;i++){
		            		StringBuilder deleteQuery = new StringBuilder();
		            		
		            	deleteQuery.append("update cr_pdc_hold_reason_dtl set main_status='A' where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID[i]).trim())+"'");
		            	logger.info("in delete "+deleteQuery);
		            	deleteList.add(deleteQuery.toString());
		            	
		            	deleteQuery = null;
		            	
		            	}
		            status =ConnectionDAO.sqlInsUpdDelete(deleteList);
		            }
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
	}

		public boolean updateCommentNDecision(
			InstructionCapMakerVO instructionCapMakerVO,int loanID) {
			StringBuilder query = new StringBuilder(); 
			logger.info("In updateCommentNDecision");
		    boolean status=false;
		    
		    try{

					ArrayList queryList=new ArrayList();
					query.append("update cr_pdc_instrument_dtl set pdc_status='"+instructionCapMakerVO.getDecision()+"'" +
							" ,author_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getUserID()).trim())+"'," +
									" author_date=DATE_ADD(STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getMakerDate()).trim())+"','"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,pdc_remarks='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getComments()).trim())+"' where pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanID).trim())+"' and pdc_status ='F'");
			
					queryList.add(query.toString());
					logger.info(" updateCommentNDecision query"+query);
					status =ConnectionDAO.sqlInsUpdDelete(queryList);
					logger.info(" updateCommentNDecision status,,,,,"+status);

			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				query = null;
			}

			return status;
		}
		public ArrayList<InstructionCapMakerVO> insertListToGeneratePDC(InstructionCapMakerVO instructionCapMakerVO) 
		{
			logger.info("In insertListToGeneratePDC");
     	    ArrayList<InstructionCapMakerVO> instructionCapMaker=new ArrayList<InstructionCapMakerVO>();
			ArrayList queryList=new ArrayList();
			ArrayList mainlist=new ArrayList();
			ArrayList subList=new ArrayList();
			ArrayList dateList = new ArrayList();
			ArrayList<Object> in =new ArrayList<Object>();
	        ArrayList<Object> out =new ArrayList<Object>();
	        ArrayList outMessages = new ArrayList();
	        StringBuilder s1 = new StringBuilder(); 
	        StringBuilder s2 = new StringBuilder(); 
	   	    String bpId = null;
			InstructionCapMakerVO instructionCapMakerVO1 = null;
			StringBuilder query = new StringBuilder();
			try
			{
				String binNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBinNo().trim()));
				if(binNo.equalsIgnoreCase(""))
						binNo = "";
				if((StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType().trim()))).equalsIgnoreCase("Q") || (StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType().trim()))).equalsIgnoreCase("DIR"))
				{				
					String purpose = StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getPurpose().trim()));
					if(purpose.equalsIgnoreCase("GUAR"))
						purpose = "GUARANTEE";
					if(purpose.equalsIgnoreCase("INS"))
						purpose = "INSURANCE";
					if(purpose.equalsIgnoreCase("PDC"))					
						purpose = "INSTALLMENT";
					if(purpose.equalsIgnoreCase("SEC"))					
						purpose = "SECURITY";
					if(purpose.equalsIgnoreCase("WHLS"))					
						purpose = "WHOLE AMOUNT";
					StringBuilder ecsTransactionCode = new StringBuilder(); 
					StringBuilder customeracType = new StringBuilder();
					customeracType.append(CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim());
					logger.info("customeracType---------"+customeracType);
					logger.info("amandeep---------"+instructionCapMakerVO.getClearingType());
				 	int spnserBnkBrncCode=0;
				    int utilityNo=0;
				    in.add(((CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getStartingChequeNo()).trim())));
			        in.add(((CommonFunction.checkNull(purpose).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim())));
			        in.add((myFormatter.parse((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentAmount()).trim()))).toString());
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getClearingType()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim())));
			        in.add( binNo);
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getFromInstallment()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getToInstallment()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim())));
			        String date=(CommonFunction.changeFormat(instructionCapMakerVO.getMakerDate()).trim());
			        if(date != null)
			           in.add(date);
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getEndingChequeNo()).trim())));
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim())));
					in.add( ecsTransactionCode.toString());
			        in.add(((CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim())));
			        in.add(spnserBnkBrncCode );
			        in.add(utilityNo);
			        in.add(CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim());
			        in.add(CommonFunction.checkNull(instructionCapMakerVO.getSubmitBy()).trim());
			        String preEmidate=CommonFunction.changeFormat(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentPreEmiDate()));
			        logger.info("preEmidate: "+preEmidate);
			        in.add(preEmidate);
			        in.add(CommonFunction.checkNull(instructionCapMakerVO.getPdcSubmitCustomerName()).trim());
			        out.add(s1.toString());
				    out.add(s2.toString());
				    logger.info("Instrument_Capturing_Maker("+in.toString()+","+out.toString()+")");  
					outMessages=(ArrayList) ConnectionDAO.callSP("Instrument_Capturing_Maker",in,out);
					s1.append(CommonFunction.checkNull(outMessages.get(0)));
					s2.append(CommonFunction.checkNull(outMessages.get(1)));
		            logger.info("s1: "+s1);
		            logger.info("s2: "+s2);
		 	        String valproc="";
				 	valproc=(s2.toString());
				    query.append("SELECT DATE_FORMAT(CP.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),CP.PDC_INSTRUMENT_NO,CR.INSTL_AMOUNT,CP.PDC_INSTRUMENT_AMOUNT,c.loan_id,c.loan_no, "+
				            " CP.pdc_instrument_id,CR.INSTL_No,CP.pdc_status,DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y'), B.BANK_NAME, d.BANK_BRANCH_NAME,"+ 
				            " CP.PDC_ISSUING_MICR_CODE, CP.PDC_ISSUING_IFSC_CODE, CP.PDC_ISSUEING_BANK_ACCOUNT, CP.PDC_PURPOSE,CP.PDC_INSTRUMENT_MODE, "+
				            "  cp.ECS_TRANSACTION_CODE,cp.ECUSTOMER_ACCOUNT_TYPE,cp.SPONSOR_BANKBRANCH_CODE,cp.UTILITY_NUMBER,DATE_FORMAT(cp.MAKER_DATE,'%d-%m-%Y'),cp.MAKER_ID,CP.SUBMIT_BY,CP.NAME_OF_PDC_SUBMIT_BY,CP.PDC_LOCATION, Z.DESCRIPTION as PDC_CLEARING_TYPE,CP.MAKER_REMARKS,(IFNULL(cr.INSTL_AMOUNT,0)+IFNULL(cr.OTHER_CHARGES,0)) as TOTAL_EMI "+
				            " from cr_pdc_instrument_dtl CP left join cr_repaysch_dtl cr on CP.PDC_LOAN_ID=CR.LOAN_ID AND CP.PDC_INSTL_NO=CR.INSTL_NO"+
				            " left join cr_loan_dtl c on CP.PDC_LOAN_ID=c.LOAN_ID"+
				            " left join com_bank_m b on b.BANK_ID= cp.PDC_ISSUEING_BANK_ID "+
				            " left join com_bankbranch_m d  on d.BANK_BRANCH_ID=cp.PDC_ISSUEING_BRANCH_ID"+
				            " left join generic_master Z on Z.VALUE=CP.PDC_CLEARING_TYPE and GENERIC_KEY='CLEARING_TYPE' and Z.REC_STATUS='A' "+
				            " where  cp.pdc_status in ('P','A','H') and Cp.PDC_LOAN_ID = '"+((CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID().trim())))+"'"+
				            " order by CP.PDC_INSTRUMENT_DATE,cp.pdc_instrument_id");
				       	 
					logger.info("In insertListToGeneratePDC(insertListToGeneratePDC) QUERY2  :  "+query);
					mainlist=ConnectionDAO.sqlSelect(query.toString());
					logger.info("In insertListToGeneratePDC LIST,,,,,"+mainlist.size());
					if(mainlist.size()>0)
					{
						for(int i=0;i<mainlist.size();i++)
						{
							subList=(ArrayList)mainlist.get(i);
							logger.info("In insertListToGeneratePDC sublist..."+subList.size());
							if(subList.size()>0)
							{
								instructionCapMakerVO=new InstructionCapMakerVO();
								instructionCapMakerVO.setPdcDate((CommonFunction.checkNull(subList.get(0)).trim()));
								instructionCapMakerVO.setStartingChequeNo((CommonFunction.checkNull(subList.get(1)).trim()));
								if((CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase(""))
								{
									String installmentAmount = (CommonFunction.checkNull(subList.get(2)));
									instructionCapMakerVO.setInstallmentAmount(installmentAmount);
								}
								else
								{
									Number installmentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
     								instructionCapMakerVO.setInstallmentAmount(myFormatter.format(installmentAmount));
								}
								Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
								instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
								instructionCapMakerVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(4)).trim()));
								instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(5)).trim()));
								instructionCapMakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(6)).trim()));
								instructionCapMakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(7)).trim()));
								instructionCapMakerVO.setPdcStatus((CommonFunction.checkNull(subList.get(8)).trim()));
								instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(9)).trim()));
								instructionCapMakerVO.setBank((CommonFunction.checkNull(subList.get(10)).trim()));
								instructionCapMakerVO.setBranch((CommonFunction.checkNull(subList.get(11)).trim()));
								instructionCapMakerVO.setMicr((CommonFunction.checkNull(subList.get(12)).trim()));
								instructionCapMakerVO.setIfscCode((CommonFunction.checkNull(subList.get(13)).trim()));
								instructionCapMakerVO.setBankAccount((CommonFunction.checkNull(subList.get(14)).trim()));
								instructionCapMakerVO.setPurpose((CommonFunction.checkNull(subList.get(15)).trim()));
								instructionCapMakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(16)).trim()));
								instructionCapMakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(17)).trim()));
								instructionCapMakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(18)).trim()));
								instructionCapMakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(19)).trim()));
								instructionCapMakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(20)).trim()));
								instructionCapMakerVO.setMakerDate((CommonFunction.checkNull(subList.get(21)).trim()));
								instructionCapMakerVO.setMaker((CommonFunction.checkNull(subList.get(22)).trim()));
								if(CommonFunction.checkNull(subList.get(23)).trim().equalsIgnoreCase("PRAPPL"))
									instructionCapMakerVO.setSubmitBy("APPLICANT");
								else if(CommonFunction.checkNull(subList.get(23)).trim().equalsIgnoreCase("COAPPL"))
									instructionCapMakerVO.setSubmitBy("CO APPLICANT");
								else 
									instructionCapMakerVO.setSubmitBy((CommonFunction.checkNull(subList.get(23)).trim()));
								instructionCapMakerVO.setPdcSubmitCustomerName((CommonFunction.checkNull(subList.get(24)).trim()));
								//Nishant starts
								instructionCapMakerVO.setLocation((CommonFunction.checkNull(subList.get(25)).trim()));
								instructionCapMakerVO.setClearingType((CommonFunction.checkNull(subList.get(26)).trim()));
								instructionCapMakerVO.setRemarks((CommonFunction.checkNull(subList.get(27)).trim()));
								Number totalEMI =myFormatter.parse((CommonFunction.checkNull(subList.get(28))).trim());
								instructionCapMakerVO.setTotalEMI(myFormatter.format(totalEMI));
								//Nishant Ends
								instructionCapMakerVO.setProcvalue((CommonFunction.checkNull(valproc).trim()));
					            logger.info("getProcvalue---------->.."+CommonFunction.checkNull(instructionCapMakerVO.getProcvalue()));  
								instructionCapMaker.add(instructionCapMakerVO);
							}
						}
					}
					else
					{
						instructionCapMakerVO=new InstructionCapMakerVO();
						instructionCapMakerVO.setProcvalue((CommonFunction.checkNull(valproc).trim()));
						logger.info("getProcvalue---------->.."+CommonFunction.checkNull(instructionCapMakerVO.getProcvalue())); 
						instructionCapMaker.add(instructionCapMakerVO);	
					}
					ecsTransactionCode = null;
		     		customeracType = null;
				}
				else
				{
					String purpose = "INSTALLMENT" ;	
					String clearingtype="L";
					int startingChequeNo = 0;
					int endingChequeNo=0;
					logger.info("b4 proc Instrument_Capturing_Maker call..222..");  
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType()).trim())));
					in.add(startingChequeNo);
					in.add(purpose);
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim())));
					in.add((myFormatter.parse((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentAmount()).trim()))).toString());
					in.add(((CommonFunction.checkNull(clearingtype).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim())));
					in.add(binNo);
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getFromInstallment()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getToInstallment()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim())));
			        String date=CommonFunction.changeFormat(CommonFunction.checkNull(instructionCapMakerVO.getMakerDate()).trim());
				    if(date != null)
					   	in.add(date);
					in.add(endingChequeNo);
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getEcsTransactionCode()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim())));
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getSpnserBnkBrncCode()).trim())) );
					in.add(((CommonFunction.checkNull(instructionCapMakerVO.getUtilityNo()).trim())));
					in.add(CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim());
					in.add(CommonFunction.checkNull(instructionCapMakerVO.getSubmitBy()).trim());
					String preEmidate=CommonFunction.changeFormat(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentPreEmiDate()));
				    logger.info("preEmidate: "+preEmidate);
				    in.add(preEmidate);
				    in.add(CommonFunction.checkNull(instructionCapMakerVO.getPdcSubmitCustomerName()).trim());
			        out.add(s1.toString());
			        out.add(s2.toString());
			        logger.info("Instrument_Capturing_Maker("+in.toString()+","+out.toString()+")");  
			        outMessages=(ArrayList) ConnectionDAO.callSP("Instrument_Capturing_Maker",in,out);
				    s1.append(CommonFunction.checkNull(outMessages.get(0)));
					s2.append(CommonFunction.checkNull(outMessages.get(1)));
					logger.info("s1: "+s1);
					logger.info("s2: "+s2);
					String valproc="";
     		        valproc=(s2.toString());
				    if((s1.toString()).equalsIgnoreCase("S"))
				         	logger.info("Proc Exception----"+s1);
	                else
	                {
	                    valproc=(s2.toString());
					    logger.info("Proc Exception----"+s2);
					}
					logger.info("After proc call....");  
					          
					            
					query.append("SELECT DATE_FORMAT(CP.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),CP.PDC_INSTRUMENT_NO,CR.INSTL_AMOUNT,CP.PDC_INSTRUMENT_AMOUNT,c.loan_id,c.loan_no, "+
					            " CP.pdc_instrument_id,CR.INSTL_No,CP.pdc_status,DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y'), B.BANK_NAME, d.BANK_BRANCH_NAME,"+ 
					            " CP.PDC_ISSUING_MICR_CODE, CP.PDC_ISSUING_IFSC_CODE, CP.PDC_ISSUEING_BANK_ACCOUNT, CP.PDC_PURPOSE,cp.ECS_TRANSACTION_CODE," +
					            " cp.ECUSTOMER_ACCOUNT_TYPE,cp.SPONSOR_BANKBRANCH_CODE,cp.UTILITY_NUMBER,cp.pdc_instrument_mode,DATE_FORMAT(cp.MAKER_DATE,'%d-%m-%Y'),cp.MAKER_ID, "+				           
					            " CP.SUBMIT_BY,CP.NAME_OF_PDC_SUBMIT_BY,CP.PDC_LOCATION,Z.DESCRIPTION as PDC_CLEARING_TYPE,CP.MAKER_REMARKS,(IFNULL(cr.INSTL_AMOUNT,0)+IFNULL(cr.OTHER_CHARGES,0)) as TOTAL_EMI from cr_pdc_instrument_dtl CP left join cr_repaysch_dtl cr on CP.PDC_LOAN_ID=CR.LOAN_ID AND CP.PDC_INSTL_NO=CR.INSTL_NO"+
					            " left join cr_loan_dtl c on CP.PDC_LOAN_ID=c.LOAN_ID"+
					            " left join com_bank_m b on b.BANK_ID= cp.PDC_ISSUEING_BANK_ID "+
 								" left join generic_master Z on Z.VALUE=CP.PDC_CLEARING_TYPE and GENERIC_KEY='CLEARING_TYPE' and Z.REC_STATUS='A' "+
					            " left join com_bankbranch_m d  on d.BANK_BRANCH_ID=cp.PDC_ISSUEING_BRANCH_ID"+
					            " where  cp.pdc_status in ('P','A','H') and Cp.PDC_LOAN_ID = '"+((CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID().trim())))+"'"+
					            " order by CP.PDC_INSTRUMENT_DATE,cp.pdc_instrument_id"); 
					       	 
					logger.info("In insertListToGeneratePDC QUERY3  :  "+query);
								mainlist=ConnectionDAO.sqlSelect(query.toString());
								logger.info("In insertListToGeneratePDC LIST,,111,,,"+mainlist.size());
								if(mainlist.size()>0){
									for(int i=0;i<mainlist.size();i++){

										subList=(ArrayList)mainlist.get(i);
										logger.info("In insertListToGeneratePDC sublist.222.."+subList.size());
										if(subList.size()>0){
												instructionCapMakerVO=new InstructionCapMakerVO();
												instructionCapMakerVO.setPdcDate((CommonFunction.checkNull(subList.get(0)).trim()));
												instructionCapMakerVO.setStartingChequeNo((CommonFunction.checkNull(subList.get(1)).trim()));

												if((CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase("")){
													
													String installmentAmount = (CommonFunction.checkNull(subList.get(2)));
													instructionCapMakerVO.setInstallmentAmount(installmentAmount);
													}else{

													Number installmentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
																					
													instructionCapMakerVO.setInstallmentAmount(myFormatter.format(installmentAmount));
													}
												
												//instructionCapMakerVO.setInstallmentAmount((CommonFunction.checkNull(subList.get(2)).trim()));
												Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
												instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
												
											//	instructionCapMakerVO.setInstrumentAmount((CommonFunction.checkNull(subList.get(3)).trim()));
												instructionCapMakerVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(4)).trim()));
												instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(5)).trim()));
												instructionCapMakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(6)).trim()));
												instructionCapMakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(7)).trim()));
												instructionCapMakerVO.setPdcStatus((CommonFunction.checkNull(subList.get(8)).trim()));
												instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(9)).trim()));
												
												instructionCapMakerVO.setBank((CommonFunction.checkNull(subList.get(10)).trim()));
												instructionCapMakerVO.setBranch((CommonFunction.checkNull(subList.get(11)).trim()));
												instructionCapMakerVO.setMicr((CommonFunction.checkNull(subList.get(12)).trim()));
												instructionCapMakerVO.setIfscCode((CommonFunction.checkNull(subList.get(13)).trim()));
												instructionCapMakerVO.setBankAccount((CommonFunction.checkNull(subList.get(14)).trim()));
												instructionCapMakerVO.setPurpose((CommonFunction.checkNull(subList.get(15)).trim()));
												
												
												instructionCapMakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(16)).trim()));
												instructionCapMakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(17)).trim()));
												instructionCapMakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(18)).trim()));
												instructionCapMakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(19)).trim()));
												instructionCapMakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(20)).trim()));
												instructionCapMakerVO.setMakerDate((CommonFunction.checkNull(subList.get(21)).trim()));
												instructionCapMakerVO.setMaker((CommonFunction.checkNull(subList.get(22)).trim()));
												instructionCapMakerVO.setProcvalue((CommonFunction.checkNull(valproc).trim()));
												if(CommonFunction.checkNull(subList.get(23)).trim().equalsIgnoreCase("PRAPPL"))
													instructionCapMakerVO.setSubmitBy("APPLICANT");
												else if(CommonFunction.checkNull(subList.get(23)).trim().equalsIgnoreCase("COAPPL"))
													instructionCapMakerVO.setSubmitBy("CO APPLICANT");
												else 
													instructionCapMakerVO.setSubmitBy((CommonFunction.checkNull(subList.get(23)).trim()));
												instructionCapMakerVO.setPdcSubmitCustomerName((CommonFunction.checkNull(subList.get(24)).trim()));
												//Nishant starts
												instructionCapMakerVO.setLocation((CommonFunction.checkNull(subList.get(25)).trim()));
												instructionCapMakerVO.setClearingType((CommonFunction.checkNull(subList.get(26)).trim()));
												instructionCapMakerVO.setRemarks((CommonFunction.checkNull(subList.get(27)).trim()));
												Number totalEMI =myFormatter.parse((CommonFunction.checkNull(subList.get(28))).trim());
												instructionCapMakerVO.setTotalEMI(myFormatter.format(totalEMI));
												//Nishant Ends
									            logger.info("getProcvalue------2222---->.."+CommonFunction.checkNull(instructionCapMakerVO.getProcvalue())); 
												
									            
												instructionCapMaker.add(instructionCapMakerVO);

										}
									}}else{
										instructionCapMakerVO=new InstructionCapMakerVO();
										instructionCapMakerVO.setProcvalue((CommonFunction.checkNull(valproc).trim()));
							            logger.info("getProcvalue-----22---11-->.."+CommonFunction.checkNull(instructionCapMakerVO.getProcvalue())); 
										
							            
										instructionCapMaker.add(instructionCapMakerVO);	
									}
						
					}
						
					
						}catch (Exception e) {
							
							e.printStackTrace();
						}
						finally
						{
							query = null;
							s1 = null;
							s2 = null; 
						}
						
						
				return instructionCapMaker;
				
				
			}
		public boolean updateFlag(int id) {
			boolean status = false;
			ArrayList queryList = new ArrayList();
			StringBuilder query = new StringBuilder();
			try {

				query.append("update cr_pdc_instrument_dtl set pdc_status='F' where pdc_loan_id='"
						+ id + "'");

				queryList.add(query.toString());
				logger.info("In getListOfValues" + query);
				status = ConnectionDAO.sqlInsUpdDelete(queryList);
				logger.info("In getListOfValues,,,,," + status);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				queryList = null;
				query = null;
			}
			return status;

		}
		public ArrayList<InstructionCapMakerVO> getValuesforInstrumentforloanViewer(int id) 
		{
			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
			StringBuilder query = new StringBuilder(); 
			logger.info("In getValuesforInstrumentforloanViewer... ");
			try
			{
				ArrayList mainlist=new ArrayList();
				ArrayList subList=new ArrayList();
				query.append("select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,if(rcacds.bank_name<>'',rcacds.bank_name,b.bank_name)bank_name,a.PDC_ISSUEING_BANK_ID, a.PDC_ISSUEING_BRANCH_ID,if(abcef.bank_branch_name<>'',abcef.bank_branch_name,c.bank_branch_name)bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO, a.PDC_INSTRUMENT_AMOUNT,DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),a.pdc_status,a.pdc_instrument_id,a.PDC_INSTL_NO,  a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE, a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER,a.HOLD_REMARKS,DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y')");
				query.append(" ,CASE WHEN ciud.CURRENT_STATUS='A' THEN 'Approved' WHEN ciud.CURRENT_STATUS='C' THEN 'Sent To Customer' WHEN ciud.CURRENT_STATUS='S' THEN 'Stop Payment' WHEN ciud.CURRENT_STATUS='D' THEN 'Deposited' WHEN ciud.CURRENT_STATUS='R' THEN 'Realized' WHEN ciud.CURRENT_STATUS='B' THEN 'Bounced' WHEN ciud.CURRENT_STATUS='X' THEN 'Cancelled' ELSE 'NA' END AS CURRENT_STATUS ");
				query.append(" ,CASE WHEN a.PDC_STATUS='P' THEN 'Pending' WHEN a.PDC_STATUS='A' THEN 'Approved' WHEN a.PDC_STATUS='H' THEN 'Hold' WHEN a.PDC_STATUS='X' THEN 'Delete' WHEN a.PDC_STATUS='R' THEN 'Released' WHEN a.PDC_STATUS='B' THEN 'Bounced' WHEN a.PDC_STATUS='F' THEN 'Forwarded' WHEN a.PDC_STATUS='L' THEN 'Cancel' ELSE 'NA' END AS PDC_STATUS ");
				query.append(" ,ciud.REASON_REMARKS,a.PDC_LOCATION,Z.DESCRIPTION as PDC_CLEARING_TYPE  ");
				query.append(" ,(IFNULL(cr.INSTL_AMOUNT,0)+IFNULL(cr.OTHER_CHARGES,0)) as TOTAL_EMI,a.MAKER_REMARKS,su.USER_NAME as MAKER_NAME,DATE_FORMAT(a.MAKER_DATE,'%d-%m-%Y'),a.PDC_REMARKS AS AUTHOR_REMARKS,sua.USER_NAME as AUTHOR_NAME,DATE_FORMAT(a.AUTHOR_DATE,'%d-%m-%Y') ");
				query.append(" from cr_pdc_instrument_dtl a left join CR_INSTRUMENT_UPDATE_DTL ciud on (ciud.PDC_INSTRUMENT_ID=a.PDC_INSTRUMENT_ID) left outer join com_bank_m b on (a.PDC_ISSUEING_BANK_ID = b.BANK_ID)left outer join com_bankbranch_m c on(a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID)left outer join cr_loan_dtl d on(a.pdc_loan_id = d.loan_id)LEFT OUTER JOIN cr_repaysch_dtl cr ON(A.PDC_LOAN_ID=CR.LOAN_ID AND A.PDC_INSTL_NO=CR.INSTL_NO ) left join generic_master Z on Z.VALUE=a.PDC_CLEARING_TYPE and GENERIC_KEY='CLEARING_TYPE' and Z.REC_STATUS='A' left join sec_user_m su on su.USER_ID=a.MAKER_ID left join sec_user_m sua on sua.USER_ID=a.AUTHOR_ID left join cr_ach_capturing_dtl cacds on cacds.loan_id=d.loan_id left join com_bank_m rcacds on rcacds.BANK_ID = cacds.BANK_ID left join com_bankbranch_m abcef on abcef.bank_BRANCH_ID = cacds.BRANCH_ID where a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and a.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))   order by a.PDC_INSTRUMENT_DATE");
				
//				String query="select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID," +
//					  		" a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO," +
//					  		" a.PDC_INSTRUMENT_AMOUNT,DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'"+dateFormat+"'),a.pdc_status,a.pdc_instrument_id,a.PDC_INSTL_NO, " +
//					  		" a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE," +
//					  		" a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER,a.HOLD_REMARKS" +
//					  		" from cr_pdc_instrument_dtl a," +
//					  		" com_bank_m b,com_bankbranch_m c,cr_loan_dtl d where a.PDC_ISSUEING_BANK_ID = b.BANK_ID and a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID  and" +
//					  		" a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and a.pdc_loan_id = d.loan_id and a.pdc_status in ('A')" +
//					  		"  and a.pdc_purpose='INSTALLMENT' and a.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))  " +
//					  		" order by a.PDC_INSTRUMENT_DATE";
					  
				logger.info(" getValuesforInstrumentforloanViewer query "+query);
				mainlist=ConnectionDAO.sqlSelect(query.toString());
				logger.info("In getValuesforHoldInstrument list,,,,,"+mainlist.size());
				for(int i=0;i<mainlist.size();i++)
				{
					 subList=(ArrayList)mainlist.get(i);
					 logger.info("In getValuesforHoldInstrument sublist..."+subList.size());
					 if(subList.size()>0)
					 {
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
					     instructionCapMakerVO.setCurrentStatus((CommonFunction.checkNull(subList.get(24)).trim()));
					     instructionCapMakerVO.setPdcStatus((CommonFunction.checkNull(subList.get(25)).trim()));
					     instructionCapMakerVO.setReason((CommonFunction.checkNull(subList.get(26)).trim()));
					    //Nishant starts
						 instructionCapMakerVO.setLocation((CommonFunction.checkNull(subList.get(27)).trim()));
						 instructionCapMakerVO.setClearingType((CommonFunction.checkNull(subList.get(28)).trim()));
						 Number totalEMI =myFormatter.parse((CommonFunction.checkNull(subList.get(29))).trim());
						 instructionCapMakerVO.setTotalEMI(myFormatter.format(totalEMI));
						 instructionCapMakerVO.setRemarks((CommonFunction.checkNull(subList.get(30)).trim()));
						 instructionCapMakerVO.setMaker((CommonFunction.checkNull(subList.get(31)).trim()));
						 instructionCapMakerVO.setMakerDate((CommonFunction.checkNull(subList.get(32)).trim()));
						 instructionCapMakerVO.setAuthorRemarks((CommonFunction.checkNull(subList.get(33)).trim()));
						 instructionCapMakerVO.setAuthorName((CommonFunction.checkNull(subList.get(34)).trim()));
						 instructionCapMakerVO.setAuthorDate((CommonFunction.checkNull(subList.get(35)).trim()));
						//Nishant Ends
					     instrumentList.add(instructionCapMakerVO);
					}
			}
		}catch(Exception e)
		{e.printStackTrace();}
		finally
		{
			query = null;
		}
		return instrumentList;
	}
	
		
		//ravindra kumar

		public ArrayList<InstructionCapMakerVO> searchInstrumentToUpdate(InstructionCapMakerVO instructionCapMakerVO) throws SQLException {

		    	int count=0;
		    	int startRecordIndex=0;
		    	int endRecordIndex = no;
		        ArrayList<InstructionCapMakerVO> arrList = new ArrayList<InstructionCapMakerVO>();
		        ArrayList mainList = new ArrayList();
		        ArrayList subList = new ArrayList();
		        
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

		    	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+instructionCapMakerVO.getReportingToUserId());
//		    		try{
//		    			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+instructionCapMakerVO.getReportingToUserId()+"'";
//		    			userName=ConnectionDAO.singleReturn(userNameQ);
//		    			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//		    		}
//		    		catch(Exception e)
//		    		{
//		    			e.printStackTrace();
//		    		}
		        
		           try{
		        	   
		                  logger.info("In searchInstrumentToUpdate().....................................Dao Impl");
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

		                  bufInsSql.append("select distinct CPI.pdc_loan_id,CLD.loan_no,CPM.PRODUCT_DESC,CLD.LOAN_LOAN_AMOUNT,CBM.branch_desc, CSM.SCHEME_DESC,"+
									"DATE_FORMAT(CLD.LOAN_APPROVAL_DATE,'"+dateFormat+"'),GCM.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=cpi.MAKER_ID) MAKER_ID from cr_loan_dtl CLD inner join cr_pdc_instrument_temp_dtl CPI on cld.loan_id = cpi.PDC_LOAN_ID "+
									"inner join cr_product_m CPM on cpm.PRODUCT_ID = cld.LOAN_PRODUCT inner join cr_scheme_m CSM on csm.SCHEME_ID = cld.LOAN_SCHEME "+
									"inner join com_branch_m CBM on cbm.BRANCH_ID = cld.LOAN_BRANCH inner join gcd_customer_m GCM on gcm.customer_id = cld.LOAN_CUSTOMER_ID INNER JOIN (SELECT PDC_LOAN_ID , MAX(PDC_INSTRUMENT_ID) AS pid  FROM cr_pdc_instrument_temp_dtl where MAKER_ID='"+instructionCapMakerVO.getReportingToUserId()+"' GROUP BY PDC_LOAN_ID) ABC ON CPI.PDC_LOAN_ID = ABC.PDC_LOAN_ID AND CPI.PDC_INSTRUMENT_ID = ABC.pid "+
									"where  CLD.LOAN_REPAYMENT_TYPE='I' AND pdc_status ='P' AND CPI.MAKER_ID='"+instructionCapMakerVO.getReportingToUserId()+"' "+
									"AND CLD.LOAN_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl WHERE USER_ID='"+instructionCapMakerVO.getReportingToUserId()+"' AND REC_STATUS='A')");
				                  
//				                 sbAppendToSQLCount.append(" select count(1) from (select distinct CPI.pdc_loan_id,CLD.loan_no,CPM.PRODUCT_DESC,CLD.LOAN_LOAN_AMOUNT,CBM.branch_desc, CSM.SCHEME_DESC,"+
//									"DATE_FORMAT(CLD.LOAN_APPROVAL_DATE,'"+dateFormat+"'),GCM.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=cpi.MAKER_ID) MAKER_ID from cr_loan_dtl CLD inner join cr_pdc_instrument_temp_dtl CPI on cld.loan_id = cpi.PDC_LOAN_ID "+
//									"inner join cr_product_m CPM on cpm.PRODUCT_ID = cld.LOAN_PRODUCT inner join cr_scheme_m CSM on csm.SCHEME_ID = cld.LOAN_SCHEME "+
//									"inner join com_branch_m CBM on cbm.BRANCH_ID = cld.LOAN_BRANCH inner join gcd_customer_m GCM on gcm.customer_id = cld.LOAN_CUSTOMER_ID INNER JOIN (SELECT PDC_LOAN_ID , MAX(PDC_INSTRUMENT_ID) AS pid FROM cr_pdc_instrument_temp_dtl GROUP BY PDC_LOAN_ID) ABC ON CPI.PDC_LOAN_ID = ABC.PDC_LOAN_ID AND CPI.PDC_INSTRUMENT_ID = ABC.pid "+
//									"where  CLD.LOAN_REPAYMENT_TYPE='I' AND pdc_status ='P' AND CPI.MAKER_ID='"+instructionCapMakerVO.getReportingToUserId()+"' "+
//									" AND CLD.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");


		                 sbAppendToSQLCount.append(" SELECT distinct COUNT(1) FROM cr_loan_dtl CLD "); 
        				 sbAppendToSQLCount.append(" INNER JOIN cr_pdc_instrument_temp_dtl CPI on cld.loan_id = cpi.PDC_LOAN_ID ");
        				 if((!((customerName.toString()).equalsIgnoreCase("")))) {
        					 	sbAppendToSQLCount.append(" INNER JOIN gcd_customer_m GCM on gcm.customer_id = cld.LOAN_CUSTOMER_ID ");
        				 }
						 sbAppendToSQLCount.append(" INNER JOIN (SELECT PDC_LOAN_ID , MAX(PDC_INSTRUMENT_ID) AS pid  ");
						 sbAppendToSQLCount.append(" FROM cr_pdc_instrument_temp_dtl where MAKER_ID='"+instructionCapMakerVO.getReportingToUserId()+"' GROUP BY PDC_LOAN_ID) ABC ON CPI.PDC_LOAN_ID = ABC.PDC_LOAN_ID AND CPI.PDC_INSTRUMENT_ID = ABC.pid "); 
						 sbAppendToSQLCount.append(" WHERE  CLD.LOAN_REPAYMENT_TYPE='I' AND pdc_status ='P' AND CPI.MAKER_ID='"+instructionCapMakerVO.getReportingToUserId()+"' ");
						 sbAppendToSQLCount.append(" AND CLD.LOAN_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl WHERE USER_ID='"+instructionCapMakerVO.getReportingToUserId()+"' AND REC_STATUS='A') ");
				                 

		                 if((!((lbxLoanNoHID.toString()).equalsIgnoreCase("")))) {
		                     bufInsSql.append(" AND CPI.PDC_LOAN_ID='"+lbxLoanNoHID+"'");
		                     sbAppendToSQLCount.append(" AND CPI.PDC_LOAN_ID='"+lbxLoanNoHID+"'");
		                     appendSQL=true;

		                  }

		                  if((!((lbxBankID.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND CPI.pdc_issueing_bank_id='"+lbxBankID+"'");
		                      sbAppendToSQLCount.append("  AND CPI.pdc_issueing_bank_id='"+lbxBankID+"'");
		                      appendSQL=true;
		                  }

		                  if((!((instrumentType.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND CPI.PDC_INSTRUMENT_MODE='"+instrumentType+"'");
		                      sbAppendToSQLCount.append("  AND CPI.PDC_INSTRUMENT_MODE='"+instrumentType+"'");
		                      appendSQL=true;
		                  }

		                  if((!((lbxBranchID.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND CPI.pdc_issueing_branch_id='"+lbxBranchID+"'");
		                      sbAppendToSQLCount.append("  AND CPI.pdc_issueing_branch_id='"+lbxBranchID+"'");
		                      appendSQL=true;
		                  }
		                  if((!((lbxProductID.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND CLD.LOAN_PRODUCT ='"+lbxProductID+"'");
		                      sbAppendToSQLCount.append("  AND CLD.LOAN_PRODUCT ='"+lbxProductID+"'");
		                      appendSQL=true;
		                  }

		                  if((!((customerName.toString()).equalsIgnoreCase("")))) {
		                      bufInsSql.append("  AND GCM.CUSTOMER_NAME like '%"+customerName+"%'");
		                      sbAppendToSQLCount.append("  AND GCM.CUSTOMER_NAME like '%"+customerName+"%'");
		                      appendSQL=true;
		                  }

		                  if((!(instructionCapMakerVO.getLbxUserId().equalsIgnoreCase("")))) {
		                	  bufInsSql.append(" AND CPI.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"' ");	
		                      sbAppendToSQLCount.append(" AND CPI.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"'");
		                      appendSQL = true;
		                  }

		                  
		                  bufInsSql.append(" group by  CPI.pdc_loan_id order by  CLD.LOAN_APPROVAL_DATE");
		                  //sbAppendToSQLCount.append(") as f");
//		                  sbAppendToSQLCount.append(" group by  CPI.pdc_loan_id order by CLD.LOAN_APPROVAL_DATE )AS B");
		                  logger.info("IN searchInstrumentToUpdate() COUNT QUERY IS ----- "+sbAppendToSQLCount.toString());
		                  String countStr = ConnectionDAO.singleReturn(sbAppendToSQLCount.toString());
		                  
		                  if(countStr.equalsIgnoreCase("")){
		                	  countStr = "0";
		                  }
		                  count =Integer.parseInt(countStr);
		                  countStr =null;
		                  
//		      			if(((lbxLoanNoHID.toString()).trim()==null && (lbxBankID.toString()).trim()==null && (instrumentType.toString()).trim()==null && (lbxBranchID.toString()).trim()==null  && (lbxProductID.toString()).trim()==null && (customerName.toString()).trim()==null) || (lbxLoanNoHID.toString()).trim().equalsIgnoreCase("") && (lbxBankID.toString()).trim().equalsIgnoreCase("") && (instrumentType.toString()).trim().equalsIgnoreCase("") && (lbxBranchID.toString()).trim().equalsIgnoreCase("") && (lbxProductID.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") || instructionCapMakerVO.getCurrentPageLink()>1)
//		    			{
		    			
		    			logger.info("current PAge Link no .................... "+instructionCapMakerVO.getCurrentPageLink());
		    			if(instructionCapMakerVO.getCurrentPageLink()>1)
		    			{
		    				startRecordIndex = (instructionCapMakerVO.getCurrentPageLink()-1)*no;
		    				endRecordIndex = no;
		    				logger.info("startRecordIndex .................... "+startRecordIndex);
		    				logger.info("endRecordIndex .................... "+endRecordIndex);
		    			}
		    			
		    			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
		    			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
//		    			}
		    			logger.info("IN searchInstrumentToUpdate() MAIN QUERY IS ----- "+bufInsSql.toString());
		      			
		    			  mainList = ConnectionDAO.sqlSelect(bufInsSql.toString());
		      			
		      			
		          for(int i=0;i<mainList.size();i++){
			     	subList=(ArrayList)mainList.get(i);
				  if(subList.size()>0){
						
					  		InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
							loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
							loanvo.setLoanAccNo("<a href=\"#\" onclick=\"editInstrumentForUpdate('"+((CommonFunction.checkNull(subList.get(0)).trim()))+"');\">" +
									 ((CommonFunction.checkNull(subList.get(1)).trim()))+"</a>");
							loanvo.setProduct((CommonFunction.checkNull(subList.get(2)).trim()));
							
							Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
							
							loanvo.setLoanAmount(myFormatter.format(loanAmount));
							
							//loanvo.setLoanAmount((CommonFunction.checkNull(subList.get(3)).trim()));
							loanvo.setBranch((CommonFunction.checkNull(subList.get(4)).trim()));
							loanvo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
							loanvo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(6)).trim()));
							loanvo.setCustomerName((CommonFunction.checkNull(subList.get(7)).trim()));
							loanvo.setReportingToUserId((CommonFunction.checkNull(subList.get(8)).trim()));
							loanvo.setTotalRecordSize(count);
							arrList.add(loanvo);
							loanvo=null;
				  		}
		          }
//		      	if( mainList.size()==0)
//				{
//		      		InstructionCapMakerVO loanVo=new InstructionCapMakerVO();
//		      		loanVo.setTotalRecordSize(count);
//		      		arrList.add(loanVo);
//					//request.setAttribute("flag","yes");
//					//LoggerMsg.info("getTotalRecordSize : "+payMVo.getTotalRecordSize());
//				}

		          
		           }catch(Exception e){
		        	   e.printStackTrace();
		           }
		           finally
		           {

		        	lbxLoanNoHID = null;
		   	        loanAccNo = null;
		   	        instrumentType = null;
		   	        bank  = null;
		   	       	branch = null;
		   	        lbxBankID = null;
		   	        lbxBranchID = null;
		   	        product = null;
		   	        lbxProductID = null;
		   	        customerName = null;
		   	        subList=null;
		   	     	mainList=null;
		           }

			return arrList;
		}


		public ArrayList<InstructionCapMakerVO> OpenInstrumentForEdit(int loanID,String fromIns,String toIns,String InsMode) {

			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
			logger.info("In OpenInstrumentForEdit: ");
			InstructionCapMakerVO instructionCapMakerVO=null;
			//StringBuilder query = new StringBuilder(); 
			  StringBuffer bufInsSql = new StringBuffer();
				try{

				  ArrayList mainlist=new ArrayList();
				  ArrayList subList=new ArrayList();
				  		  
				  boolean appendSQL=false;
				     

                  bufInsSql.append("SELECT DATE_FORMAT(CP.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),CP.PDC_INSTRUMENT_NO,CR.INSTL_AMOUNT,CP.PDC_INSTRUMENT_AMOUNT,c.loan_id,c.loan_no, "+
		            " CP.pdc_instrument_id,CR.INSTL_No,CP.pdc_status,DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y'), B.BANK_NAME, d.BANK_BRANCH_NAME,"+ 
		            " CP.PDC_ISSUING_MICR_CODE, CP.PDC_ISSUING_IFSC_CODE, CP.PDC_ISSUEING_BANK_ACCOUNT, CP.PDC_PURPOSE,cp.pdc_instrument_mode," +
		            "  cp.ECS_TRANSACTION_CODE,cp.ECUSTOMER_ACCOUNT_TYPE,cp.SPONSOR_BANKBRANCH_CODE,cp.UTILITY_NUMBER,DATE_FORMAT(cp.MAKER_DATE,'%d-%m-%Y'),cp.MAKER_ID "+
		            " from cr_pdc_instrument_temp_dtl CP left join cr_repaysch_dtl cr on CP.PDC_LOAN_ID=CR.LOAN_ID AND CP.PDC_INSTL_NO=CR.INSTL_NO"+
		            " left join cr_loan_dtl c on CP.PDC_LOAN_ID=c.LOAN_ID"+
		            " left join com_bank_m b on b.BANK_ID= cp.PDC_ISSUEING_BANK_ID "+
		            " left join com_bankbranch_m d  on d.BANK_BRANCH_ID=cp.PDC_ISSUEING_BRANCH_ID"+
		            " where  cp.pdc_status ='P' and Cp.PDC_LOAN_ID = '"+loanID+"' and CP.PRESENTATION_DATE is NULL ");
		            	
		            //" order by CP.PDC_INSTRUMENT_DATE,cp.pdc_instrument_id");
				  
                  if((!(InsMode.toString().equalsIgnoreCase("")))) {
                	  bufInsSql.append(" AND CP.PDC_INSTRUMENT_MODE ='"+InsMode+"' ");	
                      appendSQL = true;
                  }
                  
                  if((!((fromIns.toString()).equalsIgnoreCase("")))) {
                      bufInsSql.append("  AND CP.PDC_INSTL_NO >='"+fromIns+"'");
                      appendSQL=true;
                  }

                  if((!(toIns.toString().equalsIgnoreCase("")))) {
                	  bufInsSql.append(" AND CP.PDC_INSTL_NO <='"+toIns+"' ");	
                      appendSQL = true;
                  }
                  
                  
                  bufInsSql.append(" order by CP.PDC_INSTRUMENT_DATE,cp.pdc_instrument_id");
			  
				logger.info("In insertListToGeneratePDC(OpenInstrumentForEdit) QUERY  :  "+bufInsSql);
				mainlist=ConnectionDAO.sqlSelect(bufInsSql.toString());
				logger.info("In OpenInstrumentForEdit LIST,,,,,"+mainlist.size());
				
					for(int i=0;i<mainlist.size();i++){

						subList=(ArrayList)mainlist.get(i);
						logger.info("In OpenInstrumentForEdit sublist..."+subList.size());
						if(subList.size()>0){
								instructionCapMakerVO=new InstructionCapMakerVO();
								instructionCapMakerVO.setPdcDate((CommonFunction.checkNull(subList.get(0)).trim()));
								instructionCapMakerVO.setStartingChequeNo((CommonFunction.checkNull(subList.get(1)).trim()));							
								if((CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase(""))
								{								
									String installmentAmount = (CommonFunction.checkNull(subList.get(2)));
									instructionCapMakerVO.setInstallmentAmount(installmentAmount);
								}
								else
								{
									Number installmentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
									instructionCapMakerVO.setInstallmentAmount(myFormatter.format(installmentAmount));
								}
								Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
								instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
								instructionCapMakerVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(4)).trim()));
								instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(5)).trim()));
								instructionCapMakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(6)).trim()));
								instructionCapMakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(7)).trim()));
								instructionCapMakerVO.setPdcStatus((CommonFunction.checkNull(subList.get(8)).trim()));
								instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(9)).trim()));
								instructionCapMakerVO.setBank((CommonFunction.checkNull(subList.get(10)).trim()));
								instructionCapMakerVO.setBranch((CommonFunction.checkNull(subList.get(11)).trim()));
								instructionCapMakerVO.setMicr((CommonFunction.checkNull(subList.get(12)).trim()));
								instructionCapMakerVO.setIfscCode((CommonFunction.checkNull(subList.get(13)).trim()));
								instructionCapMakerVO.setBankAccount((CommonFunction.checkNull(subList.get(14)).trim()));
								instructionCapMakerVO.setPurpose((CommonFunction.checkNull(subList.get(15)).trim()));
								instructionCapMakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(16)).trim()));
								instructionCapMakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(17)).trim()));
								instructionCapMakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(18)).trim()));
								instructionCapMakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(19)).trim()));
								instructionCapMakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(20)).trim()));
								instructionCapMakerVO.setMakerDate((CommonFunction.checkNull(subList.get(21)).trim()));
								instructionCapMakerVO.setMaker((CommonFunction.checkNull(subList.get(22)).trim()));
								instrumentList.add(instructionCapMakerVO);

						}
					}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				bufInsSql = null;
				
			}

			return instrumentList;

		}
		
		public boolean UpdateListToGeneratePDC(InstructionCapMakerVO instructionCapMakerVO) 
		{
			logger.info("In UpdateListToGeneratePDC");
			boolean status = false; 
			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
	   	    String bpId = null;
			InstructionCapMakerVO instructionCapMakerVO1 = null;
			StringBuffer bufInsSql = new StringBuffer();
			boolean appendSQL=false;
			try
			{
				String binNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBinNo().trim()));
				if(binNo.equalsIgnoreCase(""))
						binNo = "";
				if((StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType().trim()))).equalsIgnoreCase("Q") || (StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType().trim()))).equalsIgnoreCase("DIR"))
				{				
					String purpose = StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getPurpose().trim()));
					if(purpose.equalsIgnoreCase("GUAR"))
						purpose = "GUARANTEE";
					if(purpose.equalsIgnoreCase("INS"))
						purpose = "INSURANCE";
					if(purpose.equalsIgnoreCase("PDC"))					
						purpose = "INSTALLMENT";
					if(purpose.equalsIgnoreCase("SEC"))					
						purpose = "SECURITY";
					if(purpose.equalsIgnoreCase("WHLS"))					
						purpose = "WHOLE AMOUNT";
					StringBuilder ecsTransactionCode = new StringBuilder(); 
					StringBuilder customeracType = new StringBuilder();
					customeracType.append(CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim());
					logger.info("customeracType---------"+customeracType);
				 	int spnserBnkBrncCode=0;
				    int utilityNo=0;
				    
				    bufInsSql.append("update cr_pdc_instrument_dtl set");
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BANK_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BRANCH_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUING_MICR_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUING_IFSC_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BANK_ACCOUNT='"+CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getClearingType()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("   PDC_CLEARING_TYPE='"+CommonFunction.checkNull(instructionCapMakerVO.getClearingType()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_LOCATION='"+CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  ECUSTOMER_ACCOUNT_TYPE='"+CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim()+"',");
	                      appendSQL=true;
	                  }
				    			    
				    if((!(binNo.equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_BIN_NO='"+CommonFunction.checkNull(binNo).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				 	    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MICR='"+CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MAKER_REMARKS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim())+"',");
	                      appendSQL=true;
	                  }
				    
				   
				    if(CommonFunction.checkNull(bufInsSql).endsWith(","))
				    {
				    	bufInsSql.replace(bufInsSql.lastIndexOf(","),bufInsSql.lastIndexOf(",")+1 , "");
				    	logger.info("Having comma at the end--removed-------------Query-"+bufInsSql.toString());
				    }
				    
				    bufInsSql.append(" where  pdc_status ='A' and PDC_LOAN_ID = '"+CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim()+"' and PDC_INSTRUMENT_ID = '"+CommonFunction.checkNull(instructionCapMakerVO.getInstrumentID()).trim()+"' and PRESENTATION_DATE is NULL ");
				    
//				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getFromInstallment()).trim()).equalsIgnoreCase("")))) {
//	                      bufInsSql.append("  AND PDC_INSTL_NO>='"+CommonFunction.checkNull(instructionCapMakerVO.getFromInstallment()).trim()+"' ");
//	                      appendSQL=true;
//	                  } 
//				    
//				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getToInstallment()).trim()).equalsIgnoreCase("")))) {
//	                      bufInsSql.append("  AND PDC_INSTL_NO<='"+CommonFunction.checkNull(instructionCapMakerVO.getToInstallment()).trim()+"' ");
//	                      appendSQL=true;
//	                  } 
				    
				    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				    ArrayList qryList =  new ArrayList();
				    
				    insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN UpdateListToGeneratePDC() update query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					logger.info("In UpdateListToGeneratePDC ........ update query: "+bufInsSql);			
			
					
					try
					{
						status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						logger.info("In UpdateListToGeneratePDC.........update status: "+status);
					}
					catch(Exception e){
						e.printStackTrace();
					}	
				    
					ecsTransactionCode = null;
		     		customeracType = null;
				}
				else
				{
					String purpose = "INSTALLMENT" ;	
					String clearingtype="L";
					int startingChequeNo = 0;
					int endingChequeNo=0;
					
					
					bufInsSql.append("update cr_pdc_instrument_dtl set");
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BANK_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BRANCH_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUING_MICR_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUING_IFSC_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BANK_ACCOUNT='"+CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				   			    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_LOCATION='"+CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  ECUSTOMER_ACCOUNT_TYPE='"+CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getEcsTransactionCode()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  ECS_TRANSACTION_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getEcsTransactionCode()).trim()+"',");
	                      appendSQL=true;
	                  }
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getSpnserBnkBrncCode()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  SPONSOR_BANKBRANCH_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getSpnserBnkBrncCode()).trim()+"',");
	                      appendSQL=true;
	                  }
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getUtilityNo()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  UTILITY_NUMBER='"+CommonFunction.checkNull(instructionCapMakerVO.getUtilityNo()).trim()+"',");
	                      appendSQL=true;
	                  }
				    			    
				    if((!(binNo.equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_BIN_NO='"+CommonFunction.checkNull(binNo).trim()+"',");
	                      appendSQL=true;
	                  }
				 	    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MICR='"+CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MAKER_REMARKS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim())+"',");
	                      appendSQL=true;
	                  }
				    
				   
				    if(CommonFunction.checkNull(bufInsSql).endsWith(","))
				    {
				    	bufInsSql.replace(bufInsSql.lastIndexOf(","),bufInsSql.lastIndexOf(",")+1 , "");
				    	logger.info("Having comma at the end--removed-------------Query-"+bufInsSql.toString());
				    }
				    
				    bufInsSql.append(" where  pdc_status ='A' and PDC_LOAN_ID = '"+CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim()+"' and PDC_INSTRUMENT_ID = '"+CommonFunction.checkNull(instructionCapMakerVO.getInstrumentID()).trim()+"' and PRESENTATION_DATE is NULL ");
				    
				    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				    ArrayList qryList =  new ArrayList();
				    
				    insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN UpdateListToGeneratePDC() update query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					logger.info("In UpdateListToGeneratePDC ........ update query: "+bufInsSql);			
			
					
					try
					{
						status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						logger.info("In UpdateListToGeneratePDC.........update status: "+status);
					}
					catch(Exception e){
						e.printStackTrace();
					}	
				    
					}
						
					
						}catch (Exception e) {
							
							e.printStackTrace();
						}
						finally
						{
							bufInsSql = null;
						}
						
						
				return status;
				
				
			}
		
		public boolean insertInstrumentIntoTempForUpdate(InstructionCapMakerVO instructionCapMakerVO) 
		{
			ArrayList qryList =  new ArrayList();
			ArrayList delList = new ArrayList();
			boolean status=false;
			StringBuffer bufInsSql =null;
			StringBuffer bufInsSql1 =null;
			PrepStmtObject insertPrepStmtObject = null;	
			String instIds = CommonFunction.checkNull(instructionCapMakerVO.getInstrumentSelectedIds()).trim();
			logger.info("In insertInstrumentIntoTempForUpdate...Dao Impl.");
			logger.info("instructionCapMakerVO.getMakerId() 0000000000000 "+instructionCapMakerVO.getMaker());
			 String [] checkedDateArr=instructionCapMakerVO.getCheckedDateArr();
			 String [] instrumentId=instructionCapMakerVO.getInstrumentId();
			try{
				
				 bufInsSql1 =	new StringBuffer();
				 bufInsSql =	new StringBuffer();
				 insertPrepStmtObject = new PrepStmtObject();	
				 StringBuilder delQuery = new StringBuilder();
				
				 delQuery.append("delete from cr_pdc_instrument_temp_dtl WHERE PDC_LOAN_ID='"+instructionCapMakerVO.getLbxLoanNoHID()+"'");
				 delList.add(delQuery.toString());
				 boolean st = ConnectionDAO.sqlInsUpdDelete(delList);
				 logger.info("Delete query : " + delQuery.toString());
				 logger.info("Delete cr_pdc_instrument_temp_dtl table status : " + st);
				 ArrayList InstrumentCheck = null;
					InstrumentCheck = ConnectionDAO.sqlSelect("SELECT 1 FROM cr_pdc_instrument_temp_dtl WHERE PDC_LOAN_ID='"+instructionCapMakerVO.getLbxLoanNoHID()+"' AND PDC_STATUS='P' AND PDC_INSTRUMENT_ID in("+instIds+")");	
//					
						logger.info("InstrumentCheck **************** "+InstrumentCheck.size());
						if(InstrumentCheck.size()<= 0)
						{
							bufInsSql1.append("insert into cr_pdc_instrument_temp_dtl(PDC_INSTRUMENT_ID,PDC_LOAN_ID,PDC_INSTL_NO,PDC_BPTYPE,PDC_BPID,PDC_INSTRUMENT_MODE,PDC_INSTRUMENT_TYPE,PDC_PURPOSE,PDC_FLAG,PDC_INSTRUMENT_NO,PDC_ISSUEING_BANK_ID,PDC_ISSUEING_BRANCH_ID,PDC_ISSUING_MICR_CODE,PDC_ISSUING_IFSC_CODE,PDC_ISSUEING_BANK_ACCOUNT,PDC_INSTRUMENT_DATE,PDC_INSTRUMENT_AMOUNT,PDC_CLEARING_TYPE,PDC_BIN_NO,PDC_LOCATION,PDC_REMARKS,PDC_STATUS,ECS_TRANSACTION_CODE,ECUSTOMER_ACCOUNT_TYPE,SPONSOR_BANKBRANCH_CODE,UTILITY_NUMBER,TDS_AMOUNT,HOLD_REMARKS,PRESENTATION_DATE,MICR,SUBMIT_BY,NAME_OF_PDC_SUBMIT_BY,MAKER_REMARKS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
							bufInsSql1.append("(select PDC_INSTRUMENT_ID,PDC_LOAN_ID,PDC_INSTL_NO,PDC_BPTYPE,PDC_BPID,PDC_INSTRUMENT_MODE,PDC_INSTRUMENT_TYPE,PDC_PURPOSE,PDC_FLAG,PDC_INSTRUMENT_NO,PDC_ISSUEING_BANK_ID,PDC_ISSUEING_BRANCH_ID,PDC_ISSUING_MICR_CODE,PDC_ISSUING_IFSC_CODE,PDC_ISSUEING_BANK_ACCOUNT," );
							bufInsSql1.append(" PDC_INSTRUMENT_DATE, PDC_INSTRUMENT_AMOUNT,PDC_CLEARING_TYPE,PDC_BIN_NO,PDC_LOCATION,PDC_REMARKS,PDC_STATUS,ECS_TRANSACTION_CODE,ECUSTOMER_ACCOUNT_TYPE,SPONSOR_BANKBRANCH_CODE,UTILITY_NUMBER,TDS_AMOUNT,HOLD_REMARKS,PRESENTATION_DATE,MICR,SUBMIT_BY,NAME_OF_PDC_SUBMIT_BY,MAKER_REMARKS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE from cr_pdc_instrument_dtl where PDC_INSTRUMENT_ID in("+instIds+"))");
							insertPrepStmtObject.setSql(bufInsSql1.toString());
							qryList.add(insertPrepStmtObject);
						    status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		                    
						}

			}catch(Exception e){
				e.printStackTrace();
			}
			
			boolean appendSQL=false;
			try
			{
				 if(CommonFunction.checkNull(instructionCapMakerVO.getPurpose().trim()).equalsIgnoreCase("PRE EMI"))
				    {
					  if(checkedDateArr.length>0)
						{
						    qryList=new ArrayList();
						     for(int j=0;j<checkedDateArr.length;j++)
						     {	
						    	PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
						    	StringBuilder updateQuery =new StringBuilder();
						    	updateQuery.append("update cr_pdc_instrument_temp_dtl set PDC_INSTRUMENT_DATE=STR_TO_DATE(?,'"+dateFormat+"') WHERE PDC_INSTRUMENT_ID=? ");
						    	
						    	if((CommonFunction.checkNull(checkedDateArr[j])).trim().equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString((CommonFunction.checkNull(checkedDateArr[j]).trim()));
								
								if((CommonFunction.checkNull(instrumentId[j])).trim().equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString((CommonFunction.checkNull(instrumentId[j]).trim()));
							
								insertPrepStmtObject1.setSql(updateQuery.toString());
								logger.info("IN saveAllocationReceipt() insert cr_instrument_dtl query1 ### "+insertPrepStmtObject1.printQuery());
								qryList.add(insertPrepStmtObject1);
			
						     }
						     status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						}
				     }
				String binNo = StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getBinNo().trim()));
				if(binNo.equalsIgnoreCase(""))
						binNo = "";
				if((StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType().trim()))).equalsIgnoreCase("Q") || (StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getInstrumentType().trim()))).equalsIgnoreCase("DIR"))
				{				
					String purpose = StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getPurpose().trim()));
					if(purpose.equalsIgnoreCase("GUAR"))
						purpose = "GUARANTEE";
					if(purpose.equalsIgnoreCase("INS"))
						purpose = "INSURANCE";
					if(purpose.equalsIgnoreCase("PDC"))					
						purpose = "INSTALLMENT";
					if(purpose.equalsIgnoreCase("SEC"))					
						purpose = "SECURITY";
					if(purpose.equalsIgnoreCase("WHLS"))					
						purpose = "WHOLE AMOUNT";
					logger.info("purpose---------"+purpose);
					StringBuilder ecsTransactionCode = new StringBuilder(); 
					StringBuilder customeracType = new StringBuilder();
					customeracType.append(CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim());
					logger.info("customeracType---------"+customeracType);
				 	int spnserBnkBrncCode=0;
				    int utilityNo=0;
				    
				    bufInsSql.append("update cr_pdc_instrument_temp_dtl set");
				//    if((!purpose.equalsIgnoreCase("")) && purpose.equalsIgnoreCase("PRE EMI"))
				//    {
				    	if((!((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentAmount()).trim()).equalsIgnoreCase(""))))
				    	{
				    	      bufInsSql.append("  PDC_INSTRUMENT_AMOUNT="+myFormatter.parse((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentAmount()).trim())).toString()+",");
		                      appendSQL=true;
		                  }
			//	    }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BANK_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BRANCH_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUING_MICR_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUING_IFSC_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BANK_ACCOUNT='"+CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim()+"',");
	                      appendSQL=true;
	                  }
				   
				/*    if(!purpose.equalsIgnoreCase("") && purpose.equalsIgnoreCase("PRE EMI"))	
				    {
					    if((!((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentAmount()).trim()).equalsIgnoreCase("")))) 
					    {
					    	//Number instrumentAmt =myFormatter.parse((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentAmount())).trim());
		                      bufInsSql.append("  PDC_INSTRUMENT_AMOUNT="+myFormatter.parse((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentAmount())).trim())+",");
		                      appendSQL=true;
		                  }
				    } */
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getClearingType()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("   PDC_CLEARING_TYPE='"+CommonFunction.checkNull(instructionCapMakerVO.getClearingType()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_LOCATION='"+CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  ECUSTOMER_ACCOUNT_TYPE='"+CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim()+"',");
	                      appendSQL=true;
	                  }
				    			    
				    if((!(binNo.equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_BIN_NO='"+CommonFunction.checkNull(binNo).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				 	    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MICR='"+CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MAKER_REMARKS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim())+"',");
	                      appendSQL=true;
	                  }
				    
				    logger.info("instructionCapMakerVO.getMakerId() 111111111111111111 "+instructionCapMakerVO.getMaker());
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMaker()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MAKER_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getMaker()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    bufInsSql.append("  pdc_status='P' ");
				    
				    if(CommonFunction.checkNull(bufInsSql).endsWith(","))
				    {
				    	bufInsSql.replace(bufInsSql.lastIndexOf(","),bufInsSql.lastIndexOf(",")+1 , "");
				    	logger.info("Having comma at the end--removed-------------Query-"+bufInsSql.toString());
				    }
				    
				    bufInsSql.append(" where PDC_LOAN_ID = '"+CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim()+"' and PDC_INSTRUMENT_ID in ("+CommonFunction.checkNull(instIds).trim()+") and PRESENTATION_DATE is NULL ");

				    insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN UpdateListToGeneratePDC() update query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					logger.info("In UpdateListToGeneratePDC ........ update query: "+bufInsSql);			
			
					
					try
					{
						status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						logger.info("In UpdateListToGeneratePDC.........update status: "+status);
					}
					catch(Exception e){
						e.printStackTrace();
					}	
				    
					ecsTransactionCode = null;
		     		customeracType = null;
				}
				else
				{
					String purpose = "INSTALLMENT" ;	
					String clearingtype="L";
					int startingChequeNo = 0;
					int endingChequeNo=0;
					
					
					bufInsSql.append("update cr_pdc_instrument_temp_dtl set");
				    
					if((!((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentAmount()).trim()).equalsIgnoreCase(""))))
			    	{
			    	      bufInsSql.append("  PDC_INSTRUMENT_AMOUNT="+myFormatter.parse((CommonFunction.checkNull(instructionCapMakerVO.getInstrumentAmount()).trim())).toString()+",");
	                      appendSQL=true;
	                  }
					
					if((!((CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BANK_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getLbxBankID()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BRANCH_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getLbxBranchID()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUING_MICR_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getMicr()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUING_IFSC_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getIfscCode()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_ISSUEING_BANK_ACCOUNT='"+CommonFunction.checkNull(instructionCapMakerVO.getBankAccount()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				   			    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_LOCATION='"+CommonFunction.checkNull(instructionCapMakerVO.getLocation()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  ECUSTOMER_ACCOUNT_TYPE='"+CommonFunction.checkNull(instructionCapMakerVO.getCustomeracType()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getEcsTransactionCode()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  ECS_TRANSACTION_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getEcsTransactionCode()).trim()+"',");
	                      appendSQL=true;
	                  }
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getSpnserBnkBrncCode()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  SPONSOR_BANKBRANCH_CODE='"+CommonFunction.checkNull(instructionCapMakerVO.getSpnserBnkBrncCode()).trim()+"',");
	                      appendSQL=true;
	                  }
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getUtilityNo()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  UTILITY_NUMBER='"+CommonFunction.checkNull(instructionCapMakerVO.getUtilityNo()).trim()+"',");
	                      appendSQL=true;
	                  }
				    			    
				    if((!(binNo.equalsIgnoreCase("")))) {
	                      bufInsSql.append("  PDC_BIN_NO='"+CommonFunction.checkNull(binNo).trim()+"',");
	                      appendSQL=true;
	                  }
				 	    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MICR='"+CommonFunction.checkNull(instructionCapMakerVO.getMicrNonmicr()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MAKER_REMARKS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getRemarks()).trim())+"',");
	                      appendSQL=true;
	                  }
				    logger.info("instructionCapMakerVO.getMakerId() 222222222222222 "+instructionCapMakerVO.getMaker());
				    if((!((CommonFunction.checkNull(instructionCapMakerVO.getMaker()).trim()).equalsIgnoreCase("")))) {
	                      bufInsSql.append("  MAKER_ID='"+CommonFunction.checkNull(instructionCapMakerVO.getMaker()).trim()+"',");
	                      appendSQL=true;
	                  }
				    
				    bufInsSql.append("  pdc_status='P' ");
				    
				    if(CommonFunction.checkNull(bufInsSql).endsWith(","))
				    {
				    	bufInsSql.replace(bufInsSql.lastIndexOf(","),bufInsSql.lastIndexOf(",")+1 , "");
				    	logger.info("Having comma at the end--removed-------------Query-"+bufInsSql.toString());
				    }
				    
				    bufInsSql.append(" where  PDC_LOAN_ID = '"+CommonFunction.checkNull(instructionCapMakerVO.getLbxLoanNoHID()).trim()+"' and PDC_INSTRUMENT_ID in ("+CommonFunction.checkNull(instIds).trim()+")  and PRESENTATION_DATE is NULL ");
				    
				    
				    insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN UpdateListToGeneratePDC() update query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					logger.info("In UpdateListToGeneratePDC ........ update query: "+bufInsSql);			
			
					
					try
					{
						status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						logger.info("In UpdateListToGeneratePDC.........update status: "+status);
					}
					catch(Exception e){
						e.printStackTrace();
					}	
				    
					}
						
					
						}catch (Exception e) {
							
							e.printStackTrace();
						}
						finally
						{
							bufInsSql = null;
						}
						
			
			
			return status;
		}
		
		public boolean forwardInstruments(int id,InstructionCapMakerVO vo) {
			   boolean status=false;
			   StringBuilder query = new StringBuilder();
				try{
			
					
					    ArrayList queryList=new ArrayList();
						query.append("update cr_pdc_instrument_temp_dtl set pdc_status='F',maker_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerID()).trim())+"'" +
								" ,maker_date=DATE_ADD(STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim())+"','"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)  where pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and pdc_status!='X'");
			
						queryList.add(query.toString());
						logger.info("In forwardInstruments(final): "+query);
						status =ConnectionDAO.sqlInsUpdDelete(queryList);
						logger.info("In forwardInstruments(status) getListOfValues: "+status);

				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					query = null;
				}

				return status;
			}
		
		public boolean deleteInstrumentFromTemp(String id) {

			logger.info("In deleteInstrumentFromTemp()-------in dao impl ");
			StringBuilder query = new StringBuilder(); 
			StringBuilder query1 = new StringBuilder();
			StringBuilder deletequery = new StringBuilder();
			boolean status=false;
			try {
				ArrayList mainList = new ArrayList();
				ArrayList subList = new ArrayList();
				query.append("delete from cr_pdc_instrument_temp_dtl where pdc_instrument_id ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
				mainList.add(query.toString());
				

			    status =ConnectionDAO.sqlInsUpdDelete(mainList);
//			   query1.append(" select count(1) from cr_pdc_instrument_loan_dtl where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
//			int count= Integer.parseInt(ConnectionDAO.singleReturn(query1.toString()));
//			if(count>0){
//				deletequery.append ("delete from cr_pdc_instrument_loan_dtl where pdc_instrument_id ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
//				subList.add(deletequery.toString());
//				logger.info(" delete instrument query"+deletequery.toString());
//			  status =ConnectionDAO.sqlInsUpdDelete(subList);	
//			}
			
			    
			} 
			catch (SQLException e) {
						e.printStackTrace();
					}
			finally
			{
				query = null;
				query1 = null;
				deletequery = null;
			}
					return status;
		}
		

		public ArrayList<InstructionCapMakerVO> searchInstrumentForEdit(int loanID,String fromIns,String toIns,String InsMode,String readyToForard, String purpose)
		{

			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
			logger.info("In searchInstrumentForEdit: ");
			InstructionCapMakerVO instructionCapMakerVO=null;
			//StringBuilder query = new StringBuilder(); 
			  StringBuffer bufInsSql = new StringBuffer();
				try{

				  ArrayList mainlist=new ArrayList();
				  ArrayList subList=new ArrayList();
				  		  
				  boolean appendSQL=false;
				     String tableName="";
				     String pdcStatus="";
				     if(readyToForard.equalsIgnoreCase(""))
				     {
				    	 tableName="cr_pdc_instrument_dtl";
				    	 pdcStatus="A";
				     }
				     else
				     {
				    	 tableName="cr_pdc_instrument_temp_dtl";
				    	 pdcStatus="P";
				     }

                  bufInsSql.append("SELECT DATE_FORMAT(CP.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),CP.PDC_INSTRUMENT_NO,CR.INSTL_AMOUNT,CP.PDC_INSTRUMENT_AMOUNT,c.loan_id,c.loan_no, "+
		            " CP.pdc_instrument_id,CR.INSTL_No,CP.pdc_status,DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y'), B.BANK_NAME, d.BANK_BRANCH_NAME,"+ 
		            " CP.PDC_ISSUING_MICR_CODE, CP.PDC_ISSUING_IFSC_CODE, CP.PDC_ISSUEING_BANK_ACCOUNT, CP.PDC_PURPOSE,cp.pdc_instrument_mode," +
		            "  cp.ECS_TRANSACTION_CODE,cp.ECUSTOMER_ACCOUNT_TYPE,cp.SPONSOR_BANKBRANCH_CODE,cp.UTILITY_NUMBER,DATE_FORMAT(cp.MAKER_DATE,'%d-%m-%Y'),cp.MAKER_ID "+
		            " from "+tableName+" CP left join cr_repaysch_dtl cr on CP.PDC_LOAN_ID=CR.LOAN_ID AND CP.PDC_INSTL_NO=CR.INSTL_NO"+
		            " left join cr_loan_dtl c on CP.PDC_LOAN_ID=c.LOAN_ID"+
		            " left join com_bank_m b on b.BANK_ID= cp.PDC_ISSUEING_BANK_ID "+
		            " left join com_bankbranch_m d  on d.BANK_BRANCH_ID=cp.PDC_ISSUEING_BRANCH_ID"+
		            " where  cp.pdc_status ='"+pdcStatus+"' and Cp.PDC_LOAN_ID = '"+loanID+"' and CP.PRESENTATION_DATE is NULL ");
                  logger.info("purpose DAO:::::::::::::::::::::: "+purpose);
		            //" order by CP.PDC_INSTRUMENT_DATE,cp.pdc_instrument_id");
				  
                  if((!(InsMode.toString().equalsIgnoreCase("")))) {
                	  bufInsSql.append(" AND CP.PDC_INSTRUMENT_MODE ='"+InsMode+"' ");	
                      appendSQL = true;
                  }
                  
                  if((!((fromIns.toString()).equalsIgnoreCase("")))) {
                      bufInsSql.append("  AND CP.PDC_INSTL_NO >='"+fromIns+"'");
                      appendSQL=true;
                  }

                  if((!(toIns.toString().equalsIgnoreCase("")))) {
                	  bufInsSql.append(" AND CP.PDC_INSTL_NO <='"+toIns+"' ");	
                      appendSQL = true;
                  }
                  if((!(purpose.toString().equalsIgnoreCase("")))) {
                	  bufInsSql.append(" AND CP.PDC_PURPOSE ='"+purpose+"' ");	
                      appendSQL = true;
                  }
                  
                  bufInsSql.append(" order by CP.PDC_INSTRUMENT_DATE,cp.pdc_instrument_id");
			  
				logger.info("In searchInstrumentForEdit QUERY  :  "+bufInsSql);
				mainlist=ConnectionDAO.sqlSelect(bufInsSql.toString());
				logger.info("In searchInstrumentForEdit LIST,,,,,"+mainlist.size());
				
					for(int i=0;i<mainlist.size();i++){

						subList=(ArrayList)mainlist.get(i);
						logger.info("In searchInstrumentForEdit sublist..."+subList.size());
						if(subList.size()>0){
								instructionCapMakerVO=new InstructionCapMakerVO();
								instructionCapMakerVO.setPdcDate((CommonFunction.checkNull(subList.get(0)).trim()));
								instructionCapMakerVO.setStartingChequeNo((CommonFunction.checkNull(subList.get(1)).trim()));							
								if((CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase(""))
								{								
									String installmentAmount = (CommonFunction.checkNull(subList.get(2)));
									instructionCapMakerVO.setInstallmentAmount(installmentAmount);
								}
								else
								{
									Number installmentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
									instructionCapMakerVO.setInstallmentAmount(myFormatter.format(installmentAmount));
								}
								Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
								instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
								instructionCapMakerVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(4)).trim()));
								instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(5)).trim()));
								instructionCapMakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(6)).trim()));
								instructionCapMakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(7)).trim()));
								instructionCapMakerVO.setPdcStatus((CommonFunction.checkNull(subList.get(8)).trim()));
								instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(9)).trim()));
								instructionCapMakerVO.setBank((CommonFunction.checkNull(subList.get(10)).trim()));
								instructionCapMakerVO.setBranch((CommonFunction.checkNull(subList.get(11)).trim()));
								instructionCapMakerVO.setMicr((CommonFunction.checkNull(subList.get(12)).trim()));
								instructionCapMakerVO.setIfscCode((CommonFunction.checkNull(subList.get(13)).trim()));
								instructionCapMakerVO.setBankAccount((CommonFunction.checkNull(subList.get(14)).trim()));
								instructionCapMakerVO.setPurpose((CommonFunction.checkNull(subList.get(15)).trim()));
								instructionCapMakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(16)).trim()));
								instructionCapMakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(17)).trim()));
								instructionCapMakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(18)).trim()));
								instructionCapMakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(19)).trim()));
								instructionCapMakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(20)).trim()));
								instructionCapMakerVO.setMakerDate((CommonFunction.checkNull(subList.get(21)).trim()));
								instructionCapMakerVO.setMaker((CommonFunction.checkNull(subList.get(22)).trim()));
								
								instructionCapMakerVO.setCurrentPurpose(CommonFunction.checkNull(purpose));
								instrumentList.add(instructionCapMakerVO);

						}
					}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				bufInsSql = null;
				
			}

			return instrumentList;

		}
		

		public ArrayList<InstructionCapMakerVO> searchUpdateInstrumentAuthor(
				InstructionCapMakerVO instructionCapMakerVO) {
				
				 logger.info("In searchUpdateInstrumentAuthor");
				 //String lbxLoanNoHID="";
			     //String loanAccNo="";
			     //String instrumentType="";
			     //String bank="";
			     //String branch="";
			     //String lbxBankID="";
			     //String lbxBranchID="";
			     //String product="";
			     //String lbxProductID="";
			     //String customerName="";
			     //String userName="";
			    int count=0;
			    int startRecordIndex=0;
			    int endRecordIndex = no;
			        
			     ArrayList<InstructionCapMakerVO> arrList = new ArrayList<InstructionCapMakerVO>();
			     ArrayList mainList = new ArrayList();
			     ArrayList subList = new ArrayList();
			     StringBuilder lbxLoanNoHID = new StringBuilder(); 
			     StringBuilder loanAccNo = new StringBuilder(); 
			     StringBuilder instrumentType = new StringBuilder(); 
			     StringBuilder branch = new StringBuilder(); 
			     StringBuilder lbxBankID = new StringBuilder(); 
			     StringBuilder lbxBranchID = new StringBuilder(); 
			     StringBuilder product = new StringBuilder(); 
			     StringBuilder lbxProductID = new StringBuilder(); 
			     StringBuilder bank = new StringBuilder(); 
			     StringBuilder customerName = new StringBuilder(); 
			     
			 	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+instructionCapMakerVO.getReportingToUserId());
//				try{
//					String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+instructionCapMakerVO.getReportingToUserId()+"'";
//					userName=ConnectionDAO.singleReturn(userNameQ);
//					logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//				}
//				catch(Exception e)
//				{
//					e.printStackTrace();
//				}
				
			        try{
			              
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

			               boolean appendSQL=false;

			               StringBuffer sbAppendToSQLCount=new StringBuffer();
			               StringBuffer bufInsSql =    new StringBuffer();
			               
			               
			               bufInsSql.append(" select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc," +
			                  		" d.SCHEME_DESC,DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"'),f.CUSTOMER_NAME , (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=C.MAKER_ID AND C.PDC_STATUS='F') MAKER_ID from cr_loan_dtl a, cr_pdc_instrument_temp_dtl c," +
			                  		" cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and " +

			                  		" e.branch_id=a.loan_branch and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_REPAYMENT_TYPE='I' and a.LOAN_SCHEME = d.SCHEME_ID and c.pdc_status='F' AND A.LOAN_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl WHERE USER_ID='"+instructionCapMakerVO.getUserID()+"' AND REC_STATUS='A') AND C.MAKER_ID!='"+instructionCapMakerVO.getUserID()+"' ");

			               sbAppendToSQLCount.append(" select distinct count(1) from (select c.pdc_loan_id from cr_loan_dtl a, cr_pdc_instrument_temp_dtl c," +
			                  		" cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and " +
			                  		" e.branch_id=a.loan_branch and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_REPAYMENT_TYPE='I' and a.LOAN_SCHEME = d.SCHEME_ID and c.pdc_status='F' AND A.LOAN_BRANCH in (SELECT BRANCH_ID FROM sec_user_branch_dtl WHERE USER_ID='"+instructionCapMakerVO.getUserID()+"' AND REC_STATUS='A') AND C.MAKER_ID!='"+instructionCapMakerVO.getUserID()+"'");

			               
			               
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
			                  if((!(instructionCapMakerVO.getLbxUserId().equalsIgnoreCase("")))) {
			                		bufInsSql.append(" AND C.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"' ");	
			                		sbAppendToSQLCount.append(" AND C.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"'");
			          			appendSQL = true;
			          	      }
			                  
			                  bufInsSql.append(" order by a.LOAN_APPROVAL_DATE");
			                  sbAppendToSQLCount.append("  GROUP BY c.pdc_loan_id ,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc, d.SCHEME_DESC,a.LOAN_APPROVAL_DATE,f.CUSTOMER_NAME,C.MAKER_ID )as b ");

			               logger.info("Query-----"+bufInsSql.toString());
			               
			               
			               count =Integer.parseInt(ConnectionDAO.singleReturn(sbAppendToSQLCount.toString()));
			      			logger.info("sbAppendToSQLCount : "+sbAppendToSQLCount.toString());

			      			if(((lbxLoanNoHID.toString()).trim()==null && (lbxBankID.toString()).trim()==null && (instrumentType.toString()).trim()==null && (lbxBranchID.toString()).trim()==null  && (lbxProductID.toString()).trim()==null && (customerName.toString()).trim()==null) || ((lbxLoanNoHID.toString()).trim().equalsIgnoreCase("") && (lbxBankID.toString()).trim().equalsIgnoreCase("") && (instrumentType.toString()).trim().equalsIgnoreCase("") && (lbxBranchID.toString()).trim().equalsIgnoreCase("") && (lbxProductID.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("")) || instructionCapMakerVO.getCurrentPageLink()>1)
			    			{
			    			
			    			logger.info("current PAge Link no .................... "+instructionCapMakerVO.getCurrentPageLink());
			    			if(instructionCapMakerVO.getCurrentPageLink()>1)
			    			{
			    				startRecordIndex = (instructionCapMakerVO.getCurrentPageLink()-1)*no;
			    				endRecordIndex = no;
			    				logger.info("startRecordIndex .................... "+startRecordIndex);
			    				logger.info("endRecordIndex .................... "+endRecordIndex);
			    			}
			    			
			    			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			    			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
			    			}
			    			logger.info("query : "+bufInsSql);

			               mainList = ConnectionDAO.sqlSelect(bufInsSql.toString());

			       for(int i=0;i<mainList.size();i++){
			    	   
				     	 subList=(ArrayList)mainList.get(i);
				     	 
				    	if(subList.size()>0){
				    		
								InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
								loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
								loanvo.setLoanAccNo("<a href=\"#\" onclick=\"editUpdatedInstrumentAuthor('"+((CommonFunction.checkNull(subList.get(0)).trim()))+"');\">" +
										((CommonFunction.checkNull(subList.get(1)).trim()))+"</a>");

								loanvo.setProduct((CommonFunction.checkNull(subList.get(2)).trim()));
								
								Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
								
								loanvo.setLoanAmount(myFormatter.format(loanAmount));
								
								//loanvo.setLoanAmount(CommonFunction.checkNull(subList.get(3)).trim());
								loanvo.setBranch((CommonFunction.checkNull(subList.get(4)).trim()));
								loanvo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
								loanvo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(6)).trim()));
								loanvo.setCustomerName((CommonFunction.checkNull(subList.get(7)).trim()));
								loanvo.setReportingToUserId((CommonFunction.checkNull(subList.get(8)).trim()));
								loanvo.setTotalRecordSize(count);
								arrList.add(loanvo);
				    	}
			       }
//			   	if( mainList.size()==0)
//				{
//		      		InstructionCapMakerVO loanVo=new InstructionCapMakerVO();
//		      		loanVo.setTotalRecordSize(count);
//		      		arrList.add(loanVo);
//					//request.setAttribute("flag","yes");
//					//LoggerMsg.info("getTotalRecordSize : "+payMVo.getTotalRecordSize());
//				}
			        }catch(Exception e){
			        	e.printStackTrace();
			        }
			           finally
			           {
			        	   lbxLoanNoHID = null;
			        	   loanAccNo = null;
			        	   instrumentType = null;
			        	   branch  = null;
			        	   lbxBankID = null;
			        	   lbxBranchID = null;
			        	   product = null;
			        	   lbxProductID = null;
			        	   bank = null;
			        	   customerName = null;
			        	   mainList = null;
			   	        	subList = null;
			           }

				return arrList;
			}
		
		public ArrayList<InstructionCapMakerVO> getValueForUpdateAuthor(int loanID) {

			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
			StringBuilder query = new StringBuilder(); 
			logger.info("In getValuesforAuthorUpdate: ");
			
			try{
				InstructionCapMakerVO instructionCapMakerVO=null;
				  ArrayList mainlist=new ArrayList();
				  ArrayList subList=new ArrayList();
				  
				  query.append("SELECT DATE_FORMAT(CP.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),CP.PDC_INSTRUMENT_NO,CR.INSTL_AMOUNT,CP.PDC_INSTRUMENT_AMOUNT,c.loan_id,c.loan_no, "+
		            " CP.pdc_instrument_id,CR.INSTL_No,CP.pdc_status,DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y'), B.BANK_NAME, d.BANK_BRANCH_NAME,"+ 
		            " CP.PDC_ISSUING_MICR_CODE, CP.PDC_ISSUING_IFSC_CODE, CP.PDC_ISSUEING_BANK_ACCOUNT, CP.PDC_PURPOSE,cp.pdc_instrument_mode, "+
		            "  cp.ECS_TRANSACTION_CODE,cp.ECUSTOMER_ACCOUNT_TYPE,cp.SPONSOR_BANKBRANCH_CODE,cp.UTILITY_NUMBER,CP.PDC_LOCATION,Z.DESCRIPTION as PDC_CLEARING_TYPE,CP.MAKER_REMARKS,(IFNULL(cr.INSTL_AMOUNT,0)+IFNULL(cr.OTHER_CHARGES,0)) as TOTAL_EMI "+
		            " from cr_pdc_instrument_temp_dtl CP left join cr_repaysch_dtl cr on CP.PDC_LOAN_ID=CR.LOAN_ID AND CP.PDC_INSTL_NO=CR.INSTL_NO"+
		            " left join cr_loan_dtl c on CP.PDC_LOAN_ID=c.LOAN_ID"+
		            " left join com_bank_m b on b.BANK_ID= cp.PDC_ISSUEING_BANK_ID "+
					" left join generic_master Z on Z.VALUE=CP.PDC_CLEARING_TYPE and GENERIC_KEY='CLEARING_TYPE' and Z.REC_STATUS='A' "+
		            " left join com_bankbranch_m d  on d.BANK_BRANCH_ID=cp.PDC_ISSUEING_BRANCH_ID"+
		            " where  cp.pdc_status ='F' and Cp.PDC_LOAN_ID = '"+loanID+"'"+
				    " order by CP.PDC_INSTRUMENT_DATE,cp.pdc_instrument_id");
				  
				  logger.info("In getValuesforAuthorUpdate QUERY"+query);
				mainlist=ConnectionDAO.sqlSelect(query.toString());
				logger.info("In getValuesforAuthorUpdate LIST,,,,,"+mainlist.size());
				
					for(int i=0;i<mainlist.size();i++){

						subList=(ArrayList)mainlist.get(i);
						logger.info("In getValuesforAuthorUpdate sublist..."+subList.size());
						if(subList.size()>0){
								instructionCapMakerVO=new InstructionCapMakerVO();
								instructionCapMakerVO.setPdcDate((CommonFunction.checkNull(subList.get(0)).trim()));
								instructionCapMakerVO.setStartingChequeNo((CommonFunction.checkNull(subList.get(1)).trim()));

								if((CommonFunction.checkNull(subList.get(2))).equalsIgnoreCase("")){
									
									String installmentAmount = (CommonFunction.checkNull(subList.get(2)));
									instructionCapMakerVO.setInstallmentAmount(installmentAmount);
									}else{

									Number installmentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
																	
									instructionCapMakerVO.setInstallmentAmount(myFormatter.format(installmentAmount));
									}
								//instructionCapMakerVO.setInstallmentAmount((CommonFunction.checkNull(subList.get(2)).trim()));
								Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
								instructionCapMakerVO.setInstrumentAmount(myFormatter.format(instrumentAmount));
								
							//	instructionCapMakerVO.setInstrumentAmount((CommonFunction.checkNull(subList.get(3)).trim()));
								instructionCapMakerVO.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(4)).trim()));
								instructionCapMakerVO.setLoanAccNo((CommonFunction.checkNull(subList.get(5)).trim()));
								instructionCapMakerVO.setInstrumentID((CommonFunction.checkNull(subList.get(6)).trim()));
								instructionCapMakerVO.setInstallmentNo((CommonFunction.checkNull(subList.get(7)).trim()));
								instructionCapMakerVO.setPdcStatus((CommonFunction.checkNull(subList.get(8)).trim()));
								instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(9)).trim()));
								
								instructionCapMakerVO.setBank((CommonFunction.checkNull(subList.get(10)).trim()));
								instructionCapMakerVO.setBranch((CommonFunction.checkNull(subList.get(11)).trim()));
								instructionCapMakerVO.setMicr((CommonFunction.checkNull(subList.get(12)).trim()));
								instructionCapMakerVO.setIfscCode((CommonFunction.checkNull(subList.get(13)).trim()));
								instructionCapMakerVO.setBankAccount((CommonFunction.checkNull(subList.get(14)).trim()));
								instructionCapMakerVO.setPurpose((CommonFunction.checkNull(subList.get(15)).trim()));
								instructionCapMakerVO.setInstrumentType((CommonFunction.checkNull(subList.get(16)).trim()));
								
								instructionCapMakerVO.setEcsTransactionCode((CommonFunction.checkNull(subList.get(17)).trim()));
								instructionCapMakerVO.setCustomeracType((CommonFunction.checkNull(subList.get(18)).trim()));
								instructionCapMakerVO.setSpnserBnkBrncCode((CommonFunction.checkNull(subList.get(19)).trim()));
								instructionCapMakerVO.setUtilityNo((CommonFunction.checkNull(subList.get(20)).trim()));
								//Nishant starts
								instructionCapMakerVO.setLocation((CommonFunction.checkNull(subList.get(21)).trim()));
								instructionCapMakerVO.setClearingType((CommonFunction.checkNull(subList.get(22)).trim()));
								instructionCapMakerVO.setRemarks((CommonFunction.checkNull(subList.get(23)).trim()));
								Number totalEMI =myFormatter.parse((CommonFunction.checkNull(subList.get(24))).trim());
								instructionCapMakerVO.setTotalEMI(myFormatter.format(totalEMI));
								//Nishant Ends														
								
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
		
		public boolean authorUpdateInstruments(InstructionCapMakerVO instructionCapMakerVO,int loanID) {
				StringBuilder query = new StringBuilder(); 
				StringBuilder query1 = new StringBuilder();
				logger.info("In authorUpdateInstruments author decision : "+ instructionCapMakerVO.getDecision());
			    boolean status=false;
				StringBuffer bufInsSql = new StringBuffer();
				//Nishant starts
				ArrayList delList = new ArrayList();
				StringBuilder delQuery = new StringBuilder();
				delQuery.append("delete from cr_pdc_instrument_temp_dtl WHERE PDC_LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanID).trim())+"'");
				delList.add(delQuery.toString());
				logger.info("Delete query : " + delQuery.toString());
				//Nishant end
				boolean appendSQL=false;
			    try{
						query1.append("select PDC_INSTRUMENT_ID,PDC_LOAN_ID,PDC_INSTRUMENT_MODE,PDC_INSTRUMENT_TYPE," +
								"PDC_ISSUEING_BANK_ID,PDC_ISSUEING_BRANCH_ID,PDC_ISSUING_MICR_CODE,PDC_ISSUING_IFSC_CODE," +
								"PDC_ISSUEING_BANK_ACCOUNT,PDC_CLEARING_TYPE,PDC_BIN_NO,PDC_LOCATION,PDC_REMARKS," +
								"ECS_TRANSACTION_CODE,ECUSTOMER_ACCOUNT_TYPE,SPONSOR_BANKBRANCH_CODE,UTILITY_NUMBER," +
								"MICR,MAKER_REMARKS,PDC_INSTRUMENT_AMOUNT,PDC_PURPOSE,PDC_INSTRUMENT_DATE from cr_pdc_instrument_temp_dtl where PDC_LOAN_ID = '"+loanID+"' and PDC_STATUS='F'");
							    
							  
							  logger.info("In authorUpdateInstruments QUERY "+query1);
							  
							  ArrayList mainlist=new ArrayList();
							  ArrayList subList=new ArrayList();
							mainlist=ConnectionDAO.sqlSelect(query1.toString());
							logger.info("In authorUpdateInstruments LIST,,,,,"+mainlist.size());
							
							ArrayList queryList=new ArrayList();
							query.append("update cr_pdc_instrument_temp_dtl set pdc_status='"+instructionCapMakerVO.getDecision()+"'" +
									" ,author_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getUserID()).trim())+"'," +
											" author_date=DATE_ADD(STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getMakerDate()).trim())+"','"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,pdc_remarks='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getComments()).trim())+"' where pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanID).trim())+"' and pdc_status ='F'");
					
							queryList.add(query.toString());
							logger.info(" authorUpdateInstruments query"+query);
							status =ConnectionDAO.sqlInsUpdDelete(queryList);
							logger.info(" authorUpdateInstruments status,,,,,"+status);
							
							
							int i=0;
								if(i<mainlist.size())
								{

									subList=(ArrayList)mainlist.get(i);
									logger.info("In authorUpdateInstruments sublist..."+subList.size());
									if(subList.size()>0)
									{
			
						
								if((StringEscapeUtils.escapeSql((CommonFunction.checkNull(subList.get(2)).trim())).equalsIgnoreCase("Q")) || (StringEscapeUtils.escapeSql((CommonFunction.checkNull(subList.get(2)).trim())).equalsIgnoreCase("DIR")))
								{				
								
								    bufInsSql.append("update cr_pdc_instrument_dtl set");
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(4)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUEING_BANK_ID='"+CommonFunction.checkNull(subList.get(4)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(5)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUEING_BRANCH_ID='"+CommonFunction.checkNull(subList.get(5)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(6)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUING_MICR_CODE='"+CommonFunction.checkNull(subList.get(6)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(7)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUING_IFSC_CODE='"+CommonFunction.checkNull(subList.get(7)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(8)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUEING_BANK_ACCOUNT='"+CommonFunction.checkNull(subList.get(8)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(9)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("   PDC_CLEARING_TYPE='"+CommonFunction.checkNull(subList.get(9)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(11)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_LOCATION='"+CommonFunction.checkNull(subList.get(11)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(14)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  ECUSTOMER_ACCOUNT_TYPE='"+CommonFunction.checkNull(subList.get(14)).trim()+"',");
					                      appendSQL=true;
					                  }
								    			    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(10)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_BIN_NO='"+CommonFunction.checkNull(subList.get(10)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								 	    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(17)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  MICR='"+CommonFunction.checkNull(subList.get(17)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(18)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  MAKER_REMARKS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(subList.get(18)).trim())+"',");
					                      appendSQL=true;
					                  }
								    
							
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(19)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_INSTRUMENT_AMOUNT='"+CommonFunction.checkNull(subList.get(19)).trim()+"',");
					                      appendSQL=true;
					                  }
								   if((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(20)).trim())).equalsIgnoreCase("PRE EMI")) 
								   { 
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(21)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_INSTRUMENT_DATE='"+CommonFunction.checkNull(subList.get(21)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    }
								    if(CommonFunction.checkNull(bufInsSql).endsWith(","))
								    {
								    	bufInsSql.replace(bufInsSql.lastIndexOf(","),bufInsSql.lastIndexOf(",")+1 , "");
								    	logger.info("Having comma at the end--removed-------------Query-"+bufInsSql.toString());
								    }
								    
								    bufInsSql.append(" where  pdc_status ='A' and PDC_LOAN_ID = '"+loanID+"' and PDC_INSTRUMENT_ID IN (select PDC_INSTRUMENT_ID from cr_pdc_instrument_temp_dtl where PDC_LOAN_ID = '"+loanID+"' AND pdc_status='A') ");
								    
								    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
								    ArrayList qryList =  new ArrayList();
								    
								    insertPrepStmtObject.setSql(bufInsSql.toString());
									logger.info("IN authorUpdateInstruments() update query1 ### "+insertPrepStmtObject.printQuery());
									qryList.add(insertPrepStmtObject);
									logger.info("In authorUpdateInstruments ........ update query: "+bufInsSql);			
							
									
									try
									{
										status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
										logger.info("In authorUpdateInstruments.........update status: "+status);
										if(status)
										{
											logger.info("instructionCapMakerVO.getDecision() inside if" + instructionCapMakerVO.getDecision());
											if((instructionCapMakerVO.getDecision()).equalsIgnoreCase("A"))
											{
												logger.info("before delete");
												boolean st = ConnectionDAO.sqlInsUpdDelete(delList);
												logger.info("Delete status in author : " + st);
											}
										}
									}
									catch(Exception e){
										e.printStackTrace();
									}	
								}
								else
								{
									
									bufInsSql.append("update cr_pdc_instrument_dtl set");
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(4)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUEING_BANK_ID='"+CommonFunction.checkNull(subList.get(4)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(5)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUEING_BRANCH_ID='"+CommonFunction.checkNull(subList.get(5)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(6)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUING_MICR_CODE='"+CommonFunction.checkNull(subList.get(6)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(7)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUING_IFSC_CODE='"+CommonFunction.checkNull(subList.get(7)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(8)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_ISSUEING_BANK_ACCOUNT='"+CommonFunction.checkNull(subList.get(8)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(9)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("   PDC_CLEARING_TYPE='"+CommonFunction.checkNull(subList.get(9)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(11)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_LOCATION='"+CommonFunction.checkNull(subList.get(11)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(14)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  ECUSTOMER_ACCOUNT_TYPE='"+CommonFunction.checkNull(subList.get(14)).trim()+"',");
					                      appendSQL=true;
					                  }
								    			    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(13)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  ECS_TRANSACTION_CODE='"+CommonFunction.checkNull(subList.get(13)).trim()+"',");
					                      appendSQL=true;
					                  }
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(15)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  SPONSOR_BANKBRANCH_CODE='"+CommonFunction.checkNull(subList.get(15)).trim()+"',");
					                      appendSQL=true;
					                  }
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(16)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  UTILITY_NUMBER='"+CommonFunction.checkNull(subList.get(16)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(10)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_BIN_NO='"+CommonFunction.checkNull(subList.get(10)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								 	    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(17)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  MICR='"+CommonFunction.checkNull(subList.get(17)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(18)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  MAKER_REMARKS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(subList.get(18)).trim())+"',");
					                      appendSQL=true;
					                  }
									
								    if((!((CommonFunction.checkNull(CommonFunction.checkNull(subList.get(19)).trim())).equalsIgnoreCase("")))) {
					                      bufInsSql.append("  PDC_INSTRUMENT_AMOUNT='"+CommonFunction.checkNull(subList.get(19)).trim()+"',");
					                      appendSQL=true;
					                  }
								    
								    
								    if(CommonFunction.checkNull(bufInsSql).endsWith(","))
								    {
								    	bufInsSql.replace(bufInsSql.lastIndexOf(","),bufInsSql.lastIndexOf(",")+1 , "");
								    	logger.info("Having comma at the end--removed-------------Query-"+bufInsSql.toString());
								    }
								    
								    bufInsSql.append(" where  pdc_status ='A' and PDC_LOAN_ID = '"+loanID+"' and PDC_INSTRUMENT_ID IN (select PDC_INSTRUMENT_ID from cr_pdc_instrument_temp_dtl where PDC_LOAN_ID = '"+loanID+"') ");
								    
								    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
								    ArrayList qryList =  new ArrayList();
								    
								    insertPrepStmtObject.setSql(bufInsSql.toString());
									logger.info("IN authorUpdateInstruments() ecs update query1 ### "+insertPrepStmtObject.printQuery());
									qryList.add(insertPrepStmtObject);
									logger.info("In authorUpdateInstruments .ecs....... update query: "+bufInsSql);			
							
									
									try
									{
										status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
										logger.info("In authorUpdateInstruments.........update status123: "+status);
										if(status)
										{
											logger.info("instructionCapMakerVO.getDecision() inside if" + instructionCapMakerVO.getDecision());
											if((instructionCapMakerVO.getDecision()).equalsIgnoreCase("A"))
											{
												logger.info("before delete");
												boolean st = ConnectionDAO.sqlInsUpdDelete(delList);
												logger.info("Delete status in author : " + st);
											}
										}
									}
									catch(Exception e){
										e.printStackTrace();
									}	
							}
								
						  }
						}

				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					query = null;
				}

				return status;
			}
		//method added by neeraj tripathi
		public ArrayList<InstructionCapMakerVO> getClearingType()
		{
			logger.info("In getClearingType()");
			ArrayList<InstructionCapMakerVO> list=new ArrayList<InstructionCapMakerVO>();
			ArrayList clearingList = null;
			ArrayList subList = null;
			try
			{
				StringBuffer query =	new StringBuffer();
			    query.append("SELECT VALUE,DESCRIPTION FROM generic_master where GENERIC_KEY='CLEARING_TYPE' and REC_STATUS='A' order by DESCRIPTION  ");
			    logger.info("In getClearingType() Query  :  "+query.toString());
			    clearingList = ConnectionDAO.sqlSelect(query.toString());
			    query=null;
			    for(int i=0;i<clearingList.size();i++)
			    {
			    	subList=(ArrayList)clearingList.get(i);
			    	InstructionCapMakerVO av=new InstructionCapMakerVO();
			    	av.setClTypeCode((CommonFunction.checkNull(subList.get(0))).trim());
			    	av.setClTypeDesc((CommonFunction.checkNull(subList.get(1))).trim());
			    	list.add(av);
			    }
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}
		
		//method added by Anil Kumar
		public ArrayList getNameOfPDCGiven(int lbxLoanNoHID, String submitBy) {
			logger.info("In getNameOfPDCGiven()");
			ArrayList<InstructionCapMakerVO> list=new ArrayList<InstructionCapMakerVO>();
			ArrayList getNameList = null;
			ArrayList subList = null;
			try
			{
				StringBuffer query =	new StringBuffer();
			    query.append(" SELECT G.CUSTOMER_NAME FROM GCD_CUSTOMER_M G where G.customer_id IN (select distinct CD.GCD_ID from ");
			    query.append("CR_LOAN_CUSTOMER_ROLE CD where CD.LOAN_ID="+lbxLoanNoHID+"  ");
			    query.append("AND CD.LOAN_CUSTOMER_ROLE_TYPE='"+submitBy+"' );  ");
			    
			    logger.info("In getNameList() Query:::::::::::"+query.toString());
			    getNameList = ConnectionDAO.sqlSelect(query.toString());
			    query=null;
			    for(int i=0;i<getNameList.size();i++)
			    {
			    	subList=(ArrayList)getNameList.get(i);
			    	InstructionCapMakerVO instrucCapMakerVO=new InstructionCapMakerVO();
			    	instrucCapMakerVO.setPdcSubmitCustomerName((CommonFunction.checkNull(subList.get(0))).trim());
			    	list.add(instrucCapMakerVO);
			    }
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}

}