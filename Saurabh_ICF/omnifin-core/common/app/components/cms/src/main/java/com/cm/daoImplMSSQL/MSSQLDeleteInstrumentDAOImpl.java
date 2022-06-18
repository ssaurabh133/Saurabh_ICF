package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.DeleteInstrumentDAO;
import com.cm.vo.InstructionCapMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class MSSQLDeleteInstrumentDAOImpl implements DeleteInstrumentDAO {
	private static final Logger logger = Logger.getLogger(MSSQLDeleteInstrumentDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	public ArrayList<InstructionCapMakerVO> searchDeleteMaker(
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
			
//			String lbxLoanNoHID="";
//	        String loanAccNo="";
//	        String instrumentType="";
//	        String bank="";
//	        String branch="";
//	        String lbxBankID="";
//	        String lbxBranchID="";
//	        String product="";
//	        String lbxProductID="";
//	        String customerName="";
//	        String userName="";
	        int count=0;
	    	int startRecordIndex=0;
	    	int endRecordIndex = no;

	        ArrayList<InstructionCapMakerVO> arrList = new ArrayList<InstructionCapMakerVO>();
	        ArrayList mainList = new ArrayList();
	        ArrayList subList = new ArrayList();
//	    	logger.info("here userid+++++++++++searchDeleteMaker()---------->"+instructionCapMakerVO.getReportingToUserId());
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
	                  logger.info("In searchDeleteMaker().....................................Dao Impl");
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
	                  			                                    
//	                  bufInsSql.append( " select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc,d.SCHEME_DESC, "+ 
//	                          " DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"'),f.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID from cr_loan_dtl a, cr_pdc_instrument_dtl c, "+
//	                          "  cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and "+
//	                          " e.branch_id=a.loan_branch and a.LOAN_REPAYMENT_TYPE='I' and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_SCHEME = d.SCHEME_ID and c.PDC_STATUS in ('A','H','L')  "+
//	                          " and  c.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F')) AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");
//
//	                  sbAppendToSQLCount.append( "select distinct count(1) from( select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc,d.SCHEME_DESC, "+ 
//	                          " DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"'),f.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID from cr_loan_dtl a, cr_pdc_instrument_dtl c, "+
//	                          "  cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and "+
//	                          " e.branch_id=a.loan_branch and a.LOAN_REPAYMENT_TYPE='I' and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_SCHEME = d.SCHEME_ID and c.PDC_STATUS in ('A','H','L')   "+
//	                          " and  c.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F')) AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");

	                  //sidharth comment
	                 /* bufInsSql.append("select c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc,d.SCHEME_DESC,");
	                  bufInsSql.append(dbo);
	                  bufInsSql.append("DATE_FORMAT(a.LOAN_APPROVAL_DATE,'%d-%m-%Y') as LOAN_APPROVAL_DATE,f.CUSTOMER_NAME ," );
	                  bufInsSql.append("(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID ");
	                  bufInsSql.append(" from cr_loan_dtl a " );
	                  bufInsSql.append(" join (select a.pdc_loan_id,a.MAKER_ID from cr_pdc_instrument_dtl a join(select max(pdc_instrument_id)id,pdc_loan_id from cr_pdc_instrument_dtl where PDC_STATUS in ('A','H','L')  and  pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))group by PDC_LOAN_ID ) b on(a.pdc_instrument_id=b.id))c on(a.LOAN_ID=c.PDC_LOAN_ID) " );
	                  bufInsSql.append(" join cr_product_m b on(a.LOAN_PRODUCT = b.PRODUCT_ID) join cr_scheme_m d on(a.LOAN_SCHEME = d.SCHEME_ID)" );
	                  bufInsSql.append(" join com_branch_m e on(e.branch_id=a.loan_branch ) join gcd_customer_m f  on(f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID)" );
	                  bufInsSql.append(" where a.LOAN_REPAYMENT_TYPE='I' AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");*/
	                  // SIDHARTH change
	                  bufInsSql.append("select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc,d.SCHEME_DESC," );
	                  bufInsSql.append(dbo) ;
	                  bufInsSql.append("DATE_FORMAT(a.LOAN_APPROVAL_DATE,'%d-%m-%Y'),f.CUSTOMER_NAME from  cr_loan_dtl a ");
	                  bufInsSql.append("  join cr_pdc_instrument_dtl C ON(a.LOAN_ID=c.PDC_LOAN_ID AND C.PDC_STATUS in ('A','H','L')) ");
	                  bufInsSql.append("   LEFT JOIN cr_pdc_hold_reason_dtl HL ON(C.pdc_instrument_id=HL.pdc_instrument_id )     " );
					  bufInsSql.append(" AND ISNULL(HL.main_status,'A')<>'F'  join cr_product_m b on(a.LOAN_PRODUCT = b.PRODUCT_ID)  " );
					  bufInsSql.append("  join cr_scheme_m d on(a.LOAN_SCHEME = d.SCHEME_ID) ");
					  bufInsSql.append(" join com_branch_m e on(e.branch_id=a.loan_branch )   " );
					  bufInsSql.append(" join gcd_customer_m f  on(f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID) " );
					  bufInsSql.append(" where  A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");
	                  // SIDHARTH change ends
	                  sbAppendToSQLCount.append("select distinct count(1) from(select c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc,d.SCHEME_DESC,");
	                  sbAppendToSQLCount.append(dbo);
	                  sbAppendToSQLCount.append("DATE_FORMAT(a.LOAN_APPROVAL_DATE,'%d-%m-%Y')LOAN_APPROVAL_DATE,f.CUSTOMER_NAME ," +
		                  		"(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID from  cr_loan_dtl a " +
		                  		" join (select a.pdc_loan_id,a.MAKER_ID from cr_pdc_instrument_dtl a join(select max(pdc_instrument_id)id,pdc_loan_id from cr_pdc_instrument_dtl where PDC_STATUS in ('A','H','L')  and  pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))group by PDC_LOAN_ID ) b on(a.pdc_instrument_id=b.id))c on(a.LOAN_ID=c.PDC_LOAN_ID) " +
		                  		" join cr_product_m b on(a.LOAN_PRODUCT = b.PRODUCT_ID) join cr_scheme_m d on(a.LOAN_SCHEME = d.SCHEME_ID)" +
		                  		" join com_branch_m e on(e.branch_id=a.loan_branch ) join gcd_customer_m f  on(f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID)" +
		                  		" where  A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");
	                //Nishant space end

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

		                  //bufInsSql.append("order by a.LOAN_APPROVAL_DATE");
		                  sbAppendToSQLCount.append(")as b");
	                  
	                  count =Integer.parseInt(ConnectionDAO.singleReturn(sbAppendToSQLCount.toString()));
		      			logger.info("sbAppendToSQLCount : "+sbAppendToSQLCount.toString());

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
		    			bufInsSql.append(" ORDER BY c.pdc_loan_id OFFSET ");
		                bufInsSql.append(startRecordIndex);
		                bufInsSql.append(" ROWS FETCH next ");
		                bufInsSql.append(endRecordIndex);
		                bufInsSql.append(" ROWS ONLY ");
	    				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
		    			
		    			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
//		    			}
		    			logger.info("In searchDeleteMaker() of DeleteInstrumentDAOImpl query : "+bufInsSql);
		      			
		    			  mainList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	          for(int i=0;i<mainList.size();i++){
		     	subList=(ArrayList)mainList.get(i);
			  if(subList.size()>0){
						InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
						loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
						loanvo.setLoanAccNo("<a href=\"#\" onclick=\"editDeleteInstrumentMaker('"+((CommonFunction.checkNull(subList.get(0)).trim()))+"');\">" +
								 ((CommonFunction.checkNull(subList.get(1)).trim()))+"</a>");

						loanvo.setProduct((CommonFunction.checkNull(subList.get(2)).trim()));
						
						Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
						
						loanvo.setLoanAmount(myFormatter.format(loanAmount));
					//	loanvo.setLoanAmount((CommonFunction.checkNull(subList.get(3)).trim()));
						loanvo.setScheme((CommonFunction.checkNull(subList.get(4)).trim()));
						loanvo.setBranch((CommonFunction.checkNull(subList.get(5)).trim()));
						loanvo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(6)).trim()));
						loanvo.setCustomerName((CommonFunction.checkNull(subList.get(7)).trim()));
						//loanvo.setUserName((CommonFunction.checkNull(subList.get(8)).trim()));  -- SIDHARTH
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
	        	   lbxProductID = null;
	        	   customerName = null; 
	           }

		return arrList;
	}
	public ArrayList<InstructionCapMakerVO> searchDeleteAuthor(
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
		StringBuilder lbxProductID = new StringBuilder();
		StringBuilder customerName = new StringBuilder();
		
		logger.info("In searchDeleteAuthor");
//		String lbxLoanNoHID="";
//	     String loanAccNo="";
//	     String instrumentType="";
//	     String bank="";
//	     String branch="";
//	     String lbxBankID="";
//	     String lbxBranchID="";
//	     String product="";
//	     String instrumentNo="";
//	     String status="";
//	     String lbxProductID="";
//	        String customerName="";
//	        String userName="";
	    	int count=0;
	    	int startRecordIndex=0;
	    	int endRecordIndex = no;


	     ArrayList<InstructionCapMakerVO> arrList = new ArrayList<InstructionCapMakerVO>();
	     ArrayList mainList = new ArrayList();
	     ArrayList subList = new ArrayList();
	 	
	     logger.info("here userid+++++++searchDeleteAuthor()---------------> "+instructionCapMakerVO.getReportingToUserId());
//		try{
//			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+instructionCapMakerVO.getReportingToUserId()+"'";
//			userName=ConnectionDAO.singleReturn(userNameQ);
//			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
	     
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
	               lbxProductID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getLbxProductID()).trim()));
	               customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getCustomerName()).trim()));
	               boolean appendSQL=false;

	               StringBuffer sbAppendToSQLCount=new StringBuffer();
	               StringBuffer bufInsSql =    new StringBuffer();
	               bufInsSql.append( "select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc,d.SCHEME_DESC, ");
	               bufInsSql.append(dbo);
	               bufInsSql.append("DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"') as LOAN_APPROVAL_DATE,f.CUSTOMER_NAME, (SELECT USER_NAME ");
	               bufInsSql.append("FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID ");
	               bufInsSql.append("from cr_loan_dtl a, cr_pdc_instrument_dtl c, "+
	                          "  cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and "+
	                          " e.branch_id=a.loan_branch  and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_SCHEME = d.SCHEME_ID and c.PDC_STATUS in ('A','H','L')  "+
	                          " and c.PDC_INSTRUMENT_ID in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F') and hold_reason_status in ('X')  AND MAKER_ID!='"+instructionCapMakerVO.getUserID()+"') AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");

	               sbAppendToSQLCount.append( "select distinct count(1) from (select distinct c.pdc_loan_id,a.loan_no,b.PRODUCT_DESC,a.LOAN_LOAN_AMOUNT,e.branch_desc,d.SCHEME_DESC,");
	               sbAppendToSQLCount.append(dbo);
	               sbAppendToSQLCount.append("DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"')LOAN_APPROVAL_DATE,f.CUSTOMER_NAME, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=c.MAKER_ID) USERID from cr_loan_dtl a, cr_pdc_instrument_dtl c, "+
	                          "  cr_product_m b,cr_scheme_m d,com_branch_m e,gcd_customer_m f where a.LOAN_ID=c.PDC_LOAN_ID and f.CUSTOMER_ID = a.LOAN_CUSTOMER_ID and "+
	                          " e.branch_id=a.loan_branch  and a.LOAN_PRODUCT = b.PRODUCT_ID and a.LOAN_SCHEME = d.SCHEME_ID and c.PDC_STATUS in ('A','H','L')  "+
	                          " and c.PDC_INSTRUMENT_ID in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F') and hold_reason_status in ('X')  AND MAKER_ID!='"+instructionCapMakerVO.getUserID()+"') AND A.LOAN_BRANCH='"+instructionCapMakerVO.getBranchId()+"'");


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
//	                  if((!(instructionCapMakerVO.getLbxUserId().equalsIgnoreCase("")))) {
//	                		bufInsSql.append(" AND C.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"' ");	
//	                		sbAppendToSQLCount.append(" AND C.MAKER_ID='"+StringEscapeUtils.escapeSql(instructionCapMakerVO.getLbxUserId()).trim()+"'");
//	          			appendSQL = true;
//	          	      }
	                  //bufInsSql.append(" order by  a.LOAN_APPROVAL_DATE");
	                  sbAppendToSQLCount.append(" )as b");
	               logger.info("Query-----"+bufInsSql.toString());
	               
	               count =Integer.parseInt(ConnectionDAO.singleReturn(sbAppendToSQLCount.toString()));
	      			logger.info("sbAppendToSQLCount : "+sbAppendToSQLCount.toString());

//	      			if(((lbxLoanNoHID.toString()).trim()==null && (lbxBankID.toString()).trim()==null && (instrumentType.toString()).trim()==null && (lbxBranchID.toString()).trim()==null  && (lbxProductID.toString()).trim()==null && (customerName.toString()).trim()==null) || ((lbxLoanNoHID.toString()).trim().equalsIgnoreCase("") && (lbxBankID.toString()).trim().equalsIgnoreCase("") && (instrumentType.toString()).trim().equalsIgnoreCase("") && (lbxBranchID.toString()).trim().equalsIgnoreCase("") && (lbxProductID.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("")) || instructionCapMakerVO.getCurrentPageLink()>1)
//	    			{
	    			
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
	    			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
	    			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
//	    			}
	    			logger.info("query : "+bufInsSql);
	      			
	    			  mainList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	               	for(int i=0;i<mainList.size();i++){
	               		
	               			subList=(ArrayList)mainList.get(i);
	               			
	               			if(subList.size()>0){
	               				
										InstructionCapMakerVO loanvo=new InstructionCapMakerVO();
										loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
										loanvo.setLoanAccNo("<a href=\"#\" onclick=\"editDeleteInstrumentAuthor('"+((CommonFunction.checkNull(subList.get(0)).trim()))+"');\">" +
												 ((CommonFunction.checkNull(subList.get(1)).trim()))+"</a>");
										loanvo.setProduct((CommonFunction.checkNull(subList.get(2)).trim()));
										
										Number loanAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
										
										loanvo.setLoanAmount(myFormatter.format(loanAmount));
									//	loanvo.setLoanAmount((CommonFunction.checkNull(subList.get(3)).trim()));
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
	        	lbxProductID = null;
	        	customerName = null;
	        	instrumentNo = null;
	        	status = null;
	        }

		return arrList;


	}	
	
	public ArrayList<InstructionCapMakerVO> getValuesforDeleteInstrument(int id) {
		ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
		StringBuilder query = new StringBuilder();
		logger.info("In getValuesforDeleteInstrument: ");
		try{

				  ArrayList mainlist=new ArrayList();
				  ArrayList subList=new ArrayList();
				  query.append("select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID, a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO, a.PDC_INSTRUMENT_AMOUNT,");
				  query.append(dbo);
				  query.append("DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),a.pdc_status,a.pdc_instrument_id,a.PDC_INSTL_NO, a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE, a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER,");
				  query.append(dbo);
				  query.append("DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y') from cr_pdc_instrument_dtl a LEFT OUTER JOIN com_bank_m b ON(a.PDC_ISSUEING_BANK_ID = b.BANK_ID )LEFT OUTER JOIN com_bankbranch_m c ON(a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID )LEFT OUTER JOIN cr_loan_dtl d ON(a.pdc_loan_id = d.loan_id )LEFT OUTER JOIN cr_repaysch_dtl cr ON(A.PDC_LOAN_ID=CR.LOAN_ID AND A.PDC_INSTL_NO=CR.INSTL_NO )where a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and a.pdc_status in ('A','H')  and a.PRESENTATION_DATE is null and a.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))   order by a.PDC_INSTRUMENT_DATE");
				  
//				  String query="select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID," +
//			  		" a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO," +
//			  		" a.PDC_INSTRUMENT_AMOUNT,DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'"+dateFormat+"'),a.pdc_status,a.pdc_instrument_id,a.PDC_INSTL_NO," +
//					" a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE," +
//				    " a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER" +
//			  		" from cr_pdc_instrument_dtl a," +
//			  		" com_bank_m b,com_bankbranch_m c,cr_loan_dtl d where a.PDC_ISSUEING_BANK_ID = b.BANK_ID and a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID  and" +
//			  		" a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and a.pdc_loan_id = d.loan_id and a.pdc_status in ('A','H')" +
//			  		"  and a.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))  " +
//			  		" order by a.PDC_INSTRUMENT_DATE";
				  
			

				 logger.info(" getValuesforDeleteInstrument query "+query);
				 mainlist=ConnectionDAO.sqlSelect(query.toString());
				 logger.info("In getValuesforDeleteInstrument list,,,,,"+mainlist.size());
		 
				 		for(int i=0;i<mainlist.size();i++){
				 			
								subList=(ArrayList)mainlist.get(i);
								logger.info("In getValuesforDeleteInstrument sublist..."+subList.size());
			
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
		                                   	instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(22)).trim()));
											
											
											
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
	 
	
	public ArrayList<InstructionCapMakerVO> searchIndiDeleteInstrument(int id,InstructionCapMakerVO vo) {
			
			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
			StringBuilder instrumentType = new StringBuilder();
			StringBuilder instrumentNo = new StringBuilder();
			StringBuilder date = new StringBuilder();
			logger.info("In searchIndiDeleteInstrument: ");
//			String instrumentType="";
//	        String instrumentNo="";
//	        String date="";
	        ArrayList mainlist=new ArrayList();
			ArrayList subList=new ArrayList();
			  
			try{
					
	                    instrumentType.append((CommonFunction.checkNull(vo.getInstrumentType()).trim()));
	                    instrumentNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNo()).trim()));
	                    date.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDate()).trim()));
       	            StringBuffer sbAppendToSQLCount=new StringBuffer();
       	            StringBuffer bufInsSql =    new StringBuffer();

	                    bufInsSql.append( "select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID," +
	    		  		" a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO," +
	    		  		" a.PDC_INSTRUMENT_AMOUNT,");
	                    bufInsSql.append(dbo);
	                    bufInsSql.append("DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'"+dateFormat+"'),a.pdc_status,a.pdc_instrument_id,a.pdc_instl_no," +
	    		  		" a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE," +
				  		" a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER" +
	    		  		" from cr_pdc_instrument_dtl a,com_bank_m b,com_bankbranch_m c,cr_loan_dtl d where " +
	    		  		" a.PDC_ISSUEING_BANK_ID = b.BANK_ID and a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID  and" +
	    		  		" a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' and a.pdc_loan_id = d.loan_id and a.pdc_status in ('A','H')" +
	    		  		" and a.pdc_instrument_id not in (select pdc_instrument_id from cr_pdc_hold_reason_dtl where main_status in ('F'))");

	           	              if((!((instrumentType.toString()).equalsIgnoreCase("")))) {
	           	                  bufInsSql.append(" AND a.PDC_INSTRUMENT_MODE='"+instrumentType+"'");

	           	               }

	           	               if((!((instrumentNo.toString()).equalsIgnoreCase("")))) {
	           	                   bufInsSql.append("  AND a.pdc_instrument_no='"+instrumentNo+"'");

	           	               }
	           	              if((!((date.toString()).equalsIgnoreCase("")))) {
	           	                   bufInsSql.append("  AND a.PDC_INSTRUMENT_DATE=");
	           	                   bufInsSql.append(dbo);
	           	                   bufInsSql.append("STR_TO_DATE('"+date+"','%d-%m-%Y')");

	           	               }

//	           	               if((!(date.equalsIgnoreCase("")))) {
//	           	                   bufInsSql.append("  AND a.PDC_INSTRUMENT_TYPE='"+date+"'");
//	           	                   appendSQL=true;
//	           	               }
//
	           	           bufInsSql.append(" order by a.PDC_INSTRUMENT_DATE");
	           	            logger.info("Query-----"+bufInsSql.toString());
	          	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());


	          	            	for(int i=0;i<mainlist.size();i++){

										subList=(ArrayList)mainlist.get(i);
										logger.info("In searchIndiDeleteInstrument..."+subList.size());
										
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
												instructionCapMakerVO.setDate((CommonFunction.checkNull(subList.get(11)).trim()));
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
	
												
												instrumentList.add(instructionCapMakerVO);
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
							 bufInsSql.append(dbo);
						 	bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
		
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
	 
	public ArrayList<InstructionCapMakerVO> getValuesforDeleteInstrumentAuthor(int id) {
			
			ArrayList<InstructionCapMakerVO> instrumentList = new ArrayList<InstructionCapMakerVO>();
			logger.info("In getValuesforDeleteInstrumentAuthor: ");
			StringBuilder query = new StringBuilder();
			
			try{

			  ArrayList mainlist=new ArrayList();
			  ArrayList subList=new ArrayList();
		      query.append("select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID, a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO, a.PDC_INSTRUMENT_AMOUNT,");
		      query.append(dbo);
		      query.append("DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'%d-%m-%Y'),a.pdc_status,a.pdc_instrument_id, e.hold_reason,e.from_status,e.to_status,a.PDC_INSTL_NO, a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE, a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER,");
		      query.append(dbo);
		      query.append("DATE_FORMAT(CR.INSTL_DATE,'%d-%m-%Y')  from cr_pdc_instrument_dtl a LEFT OUTER JOIN com_bank_m b ON(a.PDC_ISSUEING_BANK_ID = b.BANK_ID )LEFT OUTER JOIN com_bankbranch_m c ON(a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID )LEFT OUTER JOIN cr_loan_dtl d ON(a.pdc_loan_id = d.loan_id )LEFT OUTER JOIN cr_repaysch_dtl cr ON(A.PDC_LOAN_ID=CR.LOAN_ID AND A.PDC_INSTL_NO=CR.INSTL_NO )left outer join cr_pdc_hold_reason_dtl e on(a.pdc_instrument_id = e.pdc_instrument_id )where  e.main_status='F' and e.hold_reason_status='X' and a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'  and a.pdc_status in ('A','H')  order by a.PDC_INSTRUMENT_DATE");
			  
			  
//			  String query="select a.PDC_LOAN_ID,a.PDC_INSTRUMENT_MODE,a.PDC_INSTRUMENT_NO,b.bank_name,a.PDC_ISSUEING_BANK_ID," +
//			  		" a.PDC_ISSUEING_BRANCH_ID,c.bank_branch_name,a.PDC_ISSUING_MICR_CODE,a.PDC_LOCATION,a.PDC_BIN_NO," +
//			  		" a.PDC_INSTRUMENT_AMOUNT,DATE_FORMAT(a.PDC_INSTRUMENT_DATE,'"+dateFormat+"'),a.pdc_status,a.pdc_instrument_id," +
//			  		" e.hold_reason,e.from_status,e.to_status,a.PDC_INSTL_NO," +
//					" a.PDC_ISSUING_IFSC_CODE,a.PDC_ISSUEING_BANK_ACCOUNT,a.PDC_PURPOSE,a.ECS_TRANSACTION_CODE," +
//					" a.ECUSTOMER_ACCOUNT_TYPE,a.SPONSOR_BANKBRANCH_CODE,a.UTILITY_NUMBER" +
//			  		" from cr_pdc_instrument_dtl a,com_bank_m b,com_bankbranch_m c,cr_loan_dtl d," +
//			  		" cr_pdc_hold_reason_dtl e where " +
//			  		" a.PDC_ISSUEING_BANK_ID = b.BANK_ID and a.PDC_ISSUEING_BRANCH_ID = c.bank_BRANCH_ID and a.pdc_loan_id = d.loan_id " +
//			  		" and a.pdc_instrument_id = e.pdc_instrument_id " +
//			  		" and e.main_status='F' and e.hold_reason_status='X' and" +
//			  		" a.pdc_loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'  and a.pdc_status in ('A','H')  order by a.PDC_INSTRUMENT_DATE";

			 logger.info("In getValuesforDeleteInstrumentAuthor "+query);
			 mainlist=ConnectionDAO.sqlSelect(query.toString());
			 logger.info("In getValuesforDeleteInstrumentAuthor,,,,,"+mainlist.size());
			
			 		for(int i=0;i<mainlist.size();i++){

							subList=(ArrayList)mainlist.get(i);
							logger.info("In getValuesforDeleteInstrumentAuthor..."+subList.size());
				
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
	
	public boolean updateCommentNDecisionforDeleteIns(
				InstructionCapMakerVO instructionCapMakerVO, String[] instrumentID) {
			
//			 hese values cant be null
			
			    logger.info("In updateCommentNDecisionforDeleteIns");
			   
				boolean status=false;
				boolean status1=false;
				boolean status2=false;
				try{

						ArrayList queryList=new ArrayList();
						ArrayList queryList1=new ArrayList();
						ArrayList deleteList= new ArrayList();
	
						logger.info("In updateCommentNDecisionforDeleteIns :::: "+instructionCapMakerVO.getDecision());

						if((instructionCapMakerVO.getDecision()).equalsIgnoreCase("X"))
						{
							
							for(int i=0;i<instrumentID.length;i++)
							{
								
								StringBuilder query = new StringBuilder();
								
								query.append("update cr_pdc_instrument_dtl set pdc_status=(select top 1 to_status from cr_pdc_hold_reason_dtl where main_status='F' and  pdc_instrument_id='"+instrumentID[i]+"' )," +
								" pdc_remarks='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getComments()).trim())+"',author_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim())+"',author_date=");
								query.append(dbo);
								query.append("STR_TO_DATE('"+instructionCapMakerVO.getMakerDate()+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
								
								    query.append(" where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID[i]).trim())+"' ");
								logger.info("In updateCommentNDecisionforDeleteIns x decision,,,,,"+query);
								queryList.add(query.toString());
												
								query = null;
								
								StringBuilder query1 = new StringBuilder();
								int count = Integer.parseInt(ConnectionDAO.singleReturn("select count(1) from  cr_pdc_instrument_temp_dtl where pdc_instrument_id='"+instrumentID[i]+"'"));
								if(count>0)
								{
									query1.append("update cr_pdc_instrument_temp_dtl set pdc_status='X' where pdc_instrument_id='"+instrumentID[i]+"'");
									 logger.info("in update cr_pdc_instrument_temp_dtl "+query1);
									queryList1.add(query1.toString());
									
									query1 = null;
								}
							}


				status =ConnectionDAO.sqlInsUpdDelete(queryList);
				logger.info("In updateCommentNDecisionforDeleteIns,,,,,"+status);
				status2 = ConnectionDAO.sqlInsUpdDelete(queryList1);
				logger.info("In updateCommentNDecisionforDeleteIns status2,,,,,"+status2);

	            if(status){
	            	
		            	logger.info("In status");
		            	for(int i=0;i<instrumentID.length;i++){
		            		
		            	StringBuilder updateQuery = new StringBuilder();
		            	
		            	updateQuery.append("update cr_pdc_hold_reason_dtl set main_status='A' where pdc_instrument_id='"+instrumentID[i]+"'");
		            	logger.info("in delete "+updateQuery);
		            	deleteList.add(updateQuery.toString());
		            	
		            	updateQuery = null;

	            	}
	            status1 =ConnectionDAO.sqlInsUpdDelete(deleteList);
	            }
				}else
				{
					for(int i=0;i<instrumentID.length;i++){

						StringBuilder query = new StringBuilder();
						
						query.append("update cr_pdc_instrument_dtl set pdc_status=(select top 1 from_status from cr_pdc_hold_reason_dtl where main_status = 'F' and pdc_instrument_id='"+instrumentID[i]+"' )," +
							     " pdc_remarks='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getComments()).trim())+"',author_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instructionCapMakerVO.getMakerID()).trim())+"',author_date=");
						query.append(dbo);
						query.append("STR_TO_DATE('"+instructionCapMakerVO.getMakerDate()+"','"+dateFormatWithTime+"') where pdc_instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentID[i]).trim())+"' ");
							 logger.info("in update else decision "+query);
							queryList.add(query.toString());
							
							query = null;
							
					}

				status =ConnectionDAO.sqlInsUpdDelete(queryList);
				
				logger.info("In updateCommentNDecisionforDeleteIns else decision,,,,,"+status);
				

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
	
}
