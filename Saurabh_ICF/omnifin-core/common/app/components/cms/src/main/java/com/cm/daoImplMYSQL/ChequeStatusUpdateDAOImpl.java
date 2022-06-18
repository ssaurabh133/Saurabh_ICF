package com.cm.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cm.dao.ChequeStatusUpdateDAO;
import com.cm.vo.ChequeStatusVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

public class ChequeStatusUpdateDAOImpl implements ChequeStatusUpdateDAO {
	
	
	private static final Logger logger = Logger.getLogger(ChequeStatusUpdateDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList searchChequesByPayment(ChequeStatusVO chequeStatusVO) {
		
		ArrayList<ChequeStatusVO> chequeStatusList = new ArrayList<ChequeStatusVO>();
		logger.info("In searchChequesByPayment: method ");
		StringBuilder businessPartnerType = new StringBuilder();
		StringBuilder businessPartnerName = new StringBuilder();
		StringBuilder pdcFlag = new StringBuilder();
		StringBuilder customerName = new StringBuilder();
		StringBuilder paymentMode = new StringBuilder();
		StringBuilder lbxBPNID = new StringBuilder();
		StringBuilder chequeStatus = new StringBuilder();
		StringBuilder lbxBPTypeHID = new StringBuilder();
		StringBuilder loanAccNo = new StringBuilder();
		StringBuilder instrumentType = new StringBuilder();
		StringBuilder lbxLoanNoHID = new StringBuilder();
		StringBuilder instrumentNo = new StringBuilder();
		StringBuilder instrumentDate = new StringBuilder();
		StringBuilder txnType = new StringBuilder();
		StringBuilder lbxDealNo = new StringBuilder();
		StringBuilder receivedDate = new StringBuilder();
		StringBuilder lbxPayInSlipHID = new StringBuilder();
		String payInSlipId="0";
		String receiptSource="";
		String defaultBranch=""; 
		
//		String businessPartnerType="";
//        String businessPartnerName="";
//        String pdcFlag="";
//        String customerName="";
//        String paymentMode="";
//        String lbxBPNID="";
//        String chequeStatus="";
//        String lbxBPTypeHID="";
//        String loanAccNo="";
//        String instrumentType="";
//        String lbxLoanNoHID="";
//        String instrumentNo="";
//        String instrumentDate="";
		String branch = null;
		branch=StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getBranch()).trim());
        int count=0;
        int noOfRecords;
        	ArrayList mainlist=new ArrayList();
        	ArrayList subList=new ArrayList();
		try{

//Start Here By Prashant
					//String valDate="a.RECEIVED_DATE";
                    logger.info("In searchChequesByPayment().....................................Dao Impl getChequeStatus: "+chequeStatusVO.getChequeStatus());  
                    /*if(CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).trim().equalsIgnoreCase("A")||CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).trim().equalsIgnoreCase("C")||CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).trim().equalsIgnoreCase("D")||CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).trim().equalsIgnoreCase("R"))
                    {
                    	valDate="a.RECEIVED_DATE";
                    }*/
                   // logger.info("Value Date: "+valDate);
 //End Here By Prashant, and the valDate used by Prashant 
                    businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getBusinessPartnerType()).trim()));
                    businessPartnerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getBusinessPartnerDesc()).trim()));
                    pdcFlag.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getPdcFlag()).trim()));
                    paymentMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getPaymentMode()).trim()));
                    lbxBPNID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLbxBPNID()).trim()));
                    chequeStatus.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).trim()));
                    lbxBPTypeHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLbxBPTypeHID()).trim()));
                    noOfRecords=chequeStatusVO.getNoOfRecords();
                    loanAccNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLoanAccNo()).trim()));
                    instrumentNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getInstrumentNo()).trim()));
                    instrumentType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getInstrumentType()).trim()));
                    lbxLoanNoHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLbxLoanNoHID()).trim()));
                    customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getCustomerName()).trim()));
                    instrumentDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getInstrumentDate()).trim()));
                    txnType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getTxnType()).trim()));
                    lbxDealNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLbxDealNo()).trim()));
                    receivedDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getReceivedDate()).trim()));
                    lbxPayInSlipHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLbxPayInSlipHID()).trim()));
                    payInSlipId=StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getPayInSlipId()).trim());
                    receiptSource=StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getReceiptSource()).trim());
                    defaultBranch = CommonFunction.checkNull(chequeStatusVO.getLbxBranchId()).trim();
           	               StringBuffer sbAppendToSQLCount=new StringBuffer();
           	               StringBuffer bufInsSql =    new StringBuffer();
           	            StringBuffer bufInsSqlTempCount = new StringBuffer();
           	               
           	               if((instrumentType.toString()).equalsIgnoreCase("P")){
           	            	   																							//if(a.REC_STATUS in('C','D','R'),DATE_FORMAT(a.DEPOSITED_DATE,'%d-%m-%Y'),DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y')) as valuedate
           	            	   bufInsSql.append("select distinct a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
           	            			   " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
           	            			   " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
           	            			   " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no,a.INSTRUMENT_MODE,if(a.REC_STATUS in('C','D','R'),DATE_FORMAT((SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'),'%d-%m-%Y'),DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y')) as valuedate ,if(a.REC_STATUS in('C','D','R'),DATE_FORMAT(A.DEPOSITED_DATE,'%d-%m-%Y'),DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y')) as HIDDENdate,z.REC_STATUS,a.RECIPT_NO,DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y') as RECIPT_DATE,BRANCH_DESC ,u.REASON_REMARKS,BM.BANK_NAME,BRM.BANK_BRANCH_NAME,a.DEPOSIT_BANK_ACCOUNT"+
           	            			   " from cr_instrument_dtl a "+
           	            			   " left outer join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
           	            			   " left outer join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
           	            			   " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
           	            			   " left outer join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
           	            			   " left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID  and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
           	            			   " and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
           	            			   " left join com_branch_m cbm on cbm.BRANCH_ID=a.DEFAULT_BRANCH  left outer join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
           	            			   " inner join cr_loan_dtl z on a.TXNID = z.loan_id "+
           	            			   " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=IFNULL(a.DEFAULT_BRANCH,z.LOAN_BRANCH) "+
           	            			   " where  z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='LIM' and  A.instrument_type='P'  and ((a.rec_status='X' and a.rec_status not in ('P','F')) or (a.rec_status<>'X' and a.rec_status not in ('P','F','X'))) "+
           	            			   " and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') )"); 
           	            			  
           	            	   
                   /* bufInsSql.append( "select a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID," +
                    		" c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,a.BPTYPE,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,a.pdc_instrument_id," +
                    		" a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE,a.DEPOSIT_BANK_ACCOUNT " +
                    		" from cr_instrument_dtl a,com_bank_m b,com_bankbranch_m c,generic_master d,business_partner_view e  where" +
                    		"  a.ISSUEING_BANK_ID = b.BANK_ID and a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID " +
                    		" and instrument_type='P'  and a.bpid=e.bp_id and  a.bptype=d.value  and a.TXNID = e.LOAN_ID and a.bptype=e.bp_type and a.rec_status not in ('P','F') and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F'))");*/
                    bufInsSqlTempCount.append("select count(1) from( select distinct a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
           	            			   " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
           	            			   " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
           	            			   " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no,BRANCH_DESC,u.REASON_REMARKS " +
                    		 " from cr_instrument_dtl a "+
 	            			   " left outer join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
 	            			   " left outer join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
 	            			   " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
 	            			   " left outer join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
 	            			   " left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
 	            			   " and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
 	            			   " left join com_branch_m cbm on cbm.BRANCH_ID=a.DEFAULT_BRANCH left outer join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
 	            			   " inner join cr_loan_dtl z on a.TXNID = z.loan_id "+
 	            			   " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=IFNULL(a.DEFAULT_BRANCH,z.LOAN_BRANCH) "+
 	            			   " where  z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='LIM' and  A.instrument_type='P'  and ((a.rec_status='X' and a.rec_status not in ('P','F')) or (a.rec_status<>'X' and a.rec_status not in ('P','F','X'))) "+
 	            			   " and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') )");
           
           	               }else{
           	            	   
		           	         if((txnType.toString()).equalsIgnoreCase("LIM"))
		           	         {
		           	            		
		           	            	bufInsSql.append("select distinct a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
		           	            			   " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
		           	            			   " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
		           	            			   " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no,a.INSTRUMENT_MODE,if(a.REC_STATUS in('C','D','R'),DATE_FORMAT((SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'),'%d-%m-%Y'),DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y')) as valuedate ,if(a.REC_STATUS in('C','D','R'),DATE_FORMAT(A.DEPOSITED_DATE,'%d-%m-%Y'),DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y')) as HIDDENdate,z.REC_STATUS,a.RECIPT_NO,DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y') as RECIPT_DATE,BRANCH_DESC ,u.REASON_REMARKS,BM.BANK_NAME,BRM.BANK_BRANCH_NAME,a.DEPOSIT_BANK_ACCOUNT"+
		           	            			   " from cr_instrument_dtl a "+
		           	            			   " left outer join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
		           	            			   " left outer join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
		           	            			   " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
		           	            			   " left outer join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
		           	            			   " left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
		           	            			   " and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
		           	            			   " left join com_branch_m cbm on cbm.BRANCH_ID=a.DEFAULT_BRANCH left outer join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
		           	            			   " inner join cr_loan_dtl z on a.TXNID = z.loan_id "+
		           	            			   " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=IFNULL(a.DEFAULT_BRANCH,z.LOAN_BRANCH) "+
		           	            			   " where  z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='LIM' and  A.instrument_type='R'  and ((a.rec_status='X' and a.rec_status not in ('P','F')) or (a.rec_status<>'X' and a.rec_status not in ('P','F','X')))  "+
		           	            			   " and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");  
		           	            			 
		           	            	   
		           	            	   
		   	            	 /*bufInsSql.append( "select a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID," +
			                    		" c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,a.BPTYPE,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,a.pdc_instrument_id," +
			                    		" a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE,a.DEPOSIT_BANK_ACCOUNT  " +
			                    		" from cr_instrument_dtl a,com_bank_m b,com_bankbranch_m c,generic_master d,business_partner_view e  where" +
			                    		"  a.ISSUEING_BANK_ID = b.BANK_ID and a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID " +
			                    		" and instrument_type='R' and a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=d.value and a.bptype=e.bp_type and a.rec_status not in ('P','F') and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");  */ 
		           	            	bufInsSqlTempCount.append("select count(1) from ( select distinct a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
		           	            			   " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
		           	            			   " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
		           	            			   " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no,BRANCH_DESC ,u.REASON_REMARKS " +
		           	            			   " from cr_instrument_dtl a "+
		           	            			   " left outer join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
		           	            			   " left outer join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
		           	            			   " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
		           	            			   " left outer join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
		           	            			   " left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID  and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
		           	            			   " and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
		           	            			   " left join com_branch_m cbm on cbm.BRANCH_ID=a.DEFAULT_BRANCH left outer join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
		           	            			   " inner join cr_loan_dtl z on a.TXNID = z.loan_id "+
		           	            			   " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=IFNULL(a.DEFAULT_BRANCH,z.LOAN_BRANCH) "+
		           	            			   " where  z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='LIM' and  A.instrument_type='R'  and ((a.rec_status='X' and a.rec_status not in ('P','F')) or (a.rec_status<>'X' and a.rec_status not in ('P','F','X')))  "+
		   	            					   " and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");
		           	            	}
   	            	
			       	            if((txnType.toString()).equalsIgnoreCase("DC")) 
			       	            	{

	           	            		
		           	            	bufInsSql.append("select distinct a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
		           	            			   " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
		           	            			   " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
		           	            			   " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.deal_no,a.INSTRUMENT_MODE,if(a.REC_STATUS in('C','D','R'),DATE_FORMAT((SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'),'%d-%m-%Y'),DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y')) as valuedate ,if(a.REC_STATUS in('C','D','R'),DATE_FORMAT(A.DEPOSITED_DATE,'%d-%m-%Y'),DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y')) as HIDDENdate,z.REC_STATUS,a.RECIPT_NO,DATE_FORMAT(a.RECEIVED_DATE,'%d-%m-%Y') as RECIPT_DATE,BRANCH_DESC ,u.REASON_REMARKS,BM.BANK_NAME,BRM.BANK_BRANCH_NAME,a.DEPOSIT_BANK_ACCOUNT"+
		           	            			   " from cr_instrument_dtl a "+
		           	            			   " left outer join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
		           	            			   " left outer join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
		           	            			   " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
		           	            			   " left outer join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
		           	            			   " left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID  and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
		           	            			   " and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
		           	            			   " left join com_branch_m cbm on cbm.BRANCH_ID=a.DEFAULT_BRANCH left outer join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
		           	            			   " inner join cr_deal_dtl z on a.TXNID = z.deal_id "+
		           	            			   " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=IFNULL(a.DEFAULT_BRANCH,z.DEAL_BRANCH) "+
		           	            			   " where  z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='DC' and  A.instrument_type='R'  and ((a.rec_status='X' and a.rec_status not in ('P','F')) or (a.rec_status<>'X' and a.rec_status not in ('P','F','X')))  "+
		           	            			   " and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");  
		           	            			 
		           	            	   
		           	            	   
		   	            	 /*bufInsSql.append( "select a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID," +
			                    		" c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,a.BPTYPE,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,a.pdc_instrument_id," +
			                    		" a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE,a.DEPOSIT_BANK_ACCOUNT  " +
			                    		" from cr_instrument_dtl a,com_bank_m b,com_bankbranch_m c,generic_master d,business_partner_view e  where" +
			                    		"  a.ISSUEING_BANK_ID = b.BANK_ID and a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID " +
			                    		" and instrument_type='R' and a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=d.value and a.bptype=e.bp_type and a.rec_status not in ('P','F') and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");  */ 
		           	            	bufInsSqlTempCount.append("select count(1) from ( select distinct a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'"+dateFormat+"'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
		           	            			   " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
		           	            			   " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
		           	            			   " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.deal_no,BRANCH_DESC,u.REASON_REMARKS  " +
		           	            			   " from cr_instrument_dtl a "+
		           	            			   " left outer join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
		           	            			   " left outer join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
		           	            			   " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
		           	            			   " left outer join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
		           	            			   " left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID  and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
		           	            			   " and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
		           	            			   " left join com_branch_m cbm on cbm.BRANCH_ID=a.DEFAULT_BRANCH left outer join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
		           	            			   " inner join cr_deal_dtl z on a.TXNID = z.deal_id "+
		           	            			   " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=IFNULL(a.DEFAULT_BRANCH,z.DEAL_BRANCH) "+
		           	            			   " where  z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='DC' and  A.instrument_type='R'  and ((a.rec_status='X' and a.rec_status not in ('P','F')) or (a.rec_status<>'X' and a.rec_status not in ('P','F','X')))  "+
		   	            					   " and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");
		           	            				
			       	            	}
	            	 	   
           	               }
           	            if(StringUtils.isBlank(payInSlipId)){
       	            		payInSlipId="0";
       	            	}
       	            	payInSlipId=payInSlipId.trim();
       	            	if((StringUtils.equalsIgnoreCase(receiptSource, "MOB") || StringUtils.equalsIgnoreCase(receiptSource, "A")) && Integer.parseInt(payInSlipId)>0){
       	            		bufInsSql.append(" AND a.MOBILE_PAYINSLIP_ID='"+payInSlipId+"'");
       	            		bufInsSqlTempCount.append(" AND a.MOBILE_PAYINSLIP_ID='"+payInSlipId+"'");
       	            	}
           	            if(!((lbxLoanNoHID.toString()).equalsIgnoreCase(""))&& ((txnType.toString()).equalsIgnoreCase("LIM"))) {
           	                  bufInsSql.append(" AND a.TXNID='"+lbxLoanNoHID+"'");
           	               bufInsSqlTempCount.append(" AND a.TXNID='"+lbxLoanNoHID+"'");

           	               }
           	            if(!((lbxDealNo.toString()).equalsIgnoreCase("")) && ((txnType.toString()).equalsIgnoreCase("DC")) ) {
         	                  bufInsSql.append(" AND a.TXNID='"+lbxDealNo+"'");
         	               bufInsSqlTempCount.append(" AND a.TXNID='"+lbxDealNo+"'");

         	               }	
           	            
//           	         if((!(customerName.equalsIgnoreCase("")))) {
//       	                  bufInsSql.append(" AND a.TXNID='"+lbxLoanNoHID+"'");
//
//       	               }
           	              if((!((lbxBPTypeHID.toString()).equalsIgnoreCase("")))) {
           	                  bufInsSql.append(" AND a.BPTYPE='"+lbxBPTypeHID+"'");
           	               bufInsSqlTempCount.append(" AND a.BPTYPE='"+lbxBPTypeHID+"'");
           	               }

           	               if((!((lbxBPNID.toString()).equalsIgnoreCase("")))) {
           	                   bufInsSql.append("  AND a.BPID='"+lbxBPNID+"'");
           	                bufInsSqlTempCount.append("  AND a.BPID='"+lbxBPNID+"'");
           	               }
           	              if((!((pdcFlag.toString()).equalsIgnoreCase("")))) {
           	                   bufInsSql.append("  AND a.PDC_FLAG='"+pdcFlag+"'");
           	                bufInsSqlTempCount.append("  AND a.PDC_FLAG='"+pdcFlag+"'");
           	               }
           	             if((!((instrumentNo.toString()).equalsIgnoreCase("")))) {
           	                  bufInsSql.append(" AND a.INSTRUMENT_NO='"+instrumentNo+"'");
           	               bufInsSqlTempCount.append(" AND a.INSTRUMENT_NO='"+instrumentNo+"'");
           	               }
                           if((!((chequeStatus.toString()).equalsIgnoreCase("ALL")))){
                        	   if((!((chequeStatus.toString()).equalsIgnoreCase("")))) {
	           	                   bufInsSql.append("  AND a.REC_STATUS='"+chequeStatus+"'");
	           	                bufInsSqlTempCount.append("  AND a.REC_STATUS='"+chequeStatus+"'");
	           	               }
                           }
           	              if((!((paymentMode.toString()).equalsIgnoreCase("")))) {
           	                   bufInsSql.append("  AND a.INSTRUMENT_MODE='"+paymentMode+"'");
           	                bufInsSqlTempCount.append("  AND a.INSTRUMENT_MODE='"+paymentMode+"'");
           	               }
           	           if((!((instrumentDate.toString()).equalsIgnoreCase("")))) {
       	                   bufInsSql.append("  AND a.INSTRUMENT_DATE=STR_TO_DATE('"+StringEscapeUtils.escapeSql(instrumentDate.toString())+"','"+dateFormat+"')");
       	                bufInsSqlTempCount.append("  AND a.INSTRUMENT_DATE=STR_TO_DATE('"+StringEscapeUtils.escapeSql(instrumentDate.toString())+"','"+dateFormat+"')");
       	               }
           	           if((!((receivedDate.toString()).equalsIgnoreCase("")))) {
       	                   bufInsSql.append("  AND a.RECEIVED_DATE=STR_TO_DATE('"+StringEscapeUtils.escapeSql(receivedDate.toString())+"','"+dateFormat+"')");
       	                bufInsSqlTempCount.append("  AND a.RECEIVED_DATE=STR_TO_DATE('"+StringEscapeUtils.escapeSql(receivedDate.toString())+"','"+dateFormat+"')");
       	               }
              	        if((!((defaultBranch.toString()).equalsIgnoreCase("")))) {
       	                  bufInsSql.append(" AND DEFAULT_BRANCH='"+defaultBranch+"'");
       	               bufInsSqlTempCount.append(" AND DEFAULT_BRANCH='"+defaultBranch+"'");
              	        }
           	        bufInsSqlTempCount.append(" ) as b");  
           	           bufInsSql.append("  order by a.INSTRUMENT_DATE");
           	           
           	               logger.info("Query-----"+bufInsSql.toString());
          	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
          	           
          	          logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
                      count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
                      
                    int startRecordIndex=0;
                    int endRecordIndex=0;
          			logger.info("current PAge Link no .................... "+chequeStatusVO.getCurrentPageLink());
          			if(chequeStatusVO.getCurrentPageLink()>1)
          			{
          				startRecordIndex = (chequeStatusVO.getCurrentPageLink()-1) * noOfRecords;
          				endRecordIndex = noOfRecords;
          				logger.info("startRecordIndex .................... "+startRecordIndex);
          				logger.info("endRecordIndex .................... "+endRecordIndex);
          			}
          			
          			bufInsSql.append(" limit "+startRecordIndex+","+noOfRecords);
          			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
          			
          			logger.info("query : "+bufInsSql.toString());
                      logger.info("count"+count);
//        	if(mainlist.size()<=0){
//        		ChequeStatusVO chequeStatusVO1=new ChequeStatusVO();
//        		chequeStatusVO1.setTotalRecordSize(count);
//        		chequeStatusList.add(chequeStatusVO1);
//        		
//        		
//        	}
//        	else
//        	{
        if(mainlist.size()>0){              
		for(int i=0;i<mainlist.size();i++)
		{
			subList=(ArrayList)mainlist.get(i);
			if(subList.size()>0)
			{
				
				    ChequeStatusVO chequeStatusVO1=new ChequeStatusVO();
				    chequeStatusVO1.setCheckBox("<input type='checkbox' name='chk' id='chk' value='"+(CommonFunction.checkNull(subList.get(13)).trim())+"' />");
				    
				    chequeStatusVO1.setInstrumentNo((CommonFunction.checkNull(subList.get(0)).trim()));
				    chequeStatusVO1.setDate((CommonFunction.checkNull(subList.get(1)).trim()));
				    Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
				    chequeStatusVO1.setInstrumentAmount(myFormatter.format(instrumentAmount));
				    
					chequeStatusVO1.setBank((CommonFunction.checkNull(subList.get(5)).trim()));
					chequeStatusVO1.setBranch((CommonFunction.checkNull(subList.get(7)).trim()));
					chequeStatusVO1.setBankAccount((CommonFunction.checkNull(subList.get(9)).trim()));
					chequeStatusVO1.setStatus((CommonFunction.checkNull(subList.get(10)).trim()));
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("A")){
						chequeStatusVO1.setStatusName("Approved");
					}
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("C")){
						chequeStatusVO1.setStatusName("Sent To Customer");
					}
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("S")){
						chequeStatusVO1.setStatusName("Stop Payment");
					}
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("D")){
						chequeStatusVO1.setStatusName("Deposit");
					}
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("R")){
						chequeStatusVO1.setStatusName("Realized");
					}
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("B")){
						chequeStatusVO1.setStatusName("Bounce");
					}
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("X")){
						chequeStatusVO1.setStatusName("Cancel");
					}
					
					chequeStatusVO1.setBusinessPartnerType((CommonFunction.checkNull(subList.get(11)).trim()));
					chequeStatusVO1.setBusinessPartnerDesc((CommonFunction.checkNull(subList.get(12)).trim()));

					 Number tdsAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(14))).trim());
					  chequeStatusVO1.setTdsAmount(myFormatter.format(tdsAmount));
					
				
					chequeStatusVO1.setPdcInstrumentId((CommonFunction.checkNull(subList.get(15)).trim()));
					
					if(CommonFunction.checkNull(subList.get(16)).trim().equalsIgnoreCase("P")){
						chequeStatusVO1.setInstrumentType("Payable");
					}else if(CommonFunction.checkNull(subList.get(16)).trim().equalsIgnoreCase("R")){
						chequeStatusVO1.setInstrumentType("Receivables");
					}
					//chequeStatusVO1.setInstrumentType((CommonFunction.checkNull(subList.get(16)).trim()));
					
					chequeStatusVO1.setReasonLov("<input type='text' class='text' name='reason' id='reason"+i+"' value='"+(CommonFunction.checkNull(subList.get(23)).trim())+"' /> <input type='button' class='lovbutton' onclick=\"openLOVCommon(70,\'sourcingForm\',\'reason"+i+"\',\'\',\'\', \'\',\'\',\'\',\'lbxReasonHID"+i+"\')\"/> <input type='hidden' name='lbxReasonHID1' id='lbxReasonHID' value='"+(CommonFunction.checkNull(subList.get(22)).trim())+"'/>  <input type='hidden' name='lbxReasonHID' id='lbxReasonHID"+i+"'/> <input type='hidden' name='instrumentNo1' id='instrumentNo1' value='"+(CommonFunction.checkNull(subList.get(0)).trim())+"' /> <input type='hidden' name='date1' id='date1' value='"+(CommonFunction.checkNull(subList.get(1)).trim())+"' /> <input type='hidden' name='instrumentAmount1' id='instrumentAmount1' value='"+myFormatter.format(instrumentAmount)+"' />  <input type='hidden' name='businessPartnerDesc1' id='businessPartnerDesc1' value='"+(CommonFunction.checkNull(subList.get(12)).trim())+"' />  <input type='hidden' name='businessPartnerType1' id='businessPartnerType1' value='"+(CommonFunction.checkNull(subList.get(11)).trim())+"' /> <input type='hidden' name='bank1' id='bank1' value='"+(CommonFunction.checkNull(subList.get(5)).trim())+"' /> <input type='hidden' name='branch1' id='branch1' value='"+(CommonFunction.checkNull(subList.get(7)).trim())+"' /> <input type='hidden' name='status1' id='status1' value='"+(CommonFunction.checkNull(subList.get(10)).trim())+"' /> <input type='hidden' name='tdsAmount1' id='tdsAmount1' value='"+myFormatter.format(tdsAmount)+"' />  <input type='hidden' name='pdcInstrumentId' id='pdcInstrumentId' value='"+(CommonFunction.checkNull(subList.get(15)).trim())+"' /> <input type='hidden' name='lbxBPTypeHID1' id='lbxBPTypeHID1' value='"+(CommonFunction.checkNull(subList.get(3)).trim())+"' /> <input type='hidden' name='lbxBPNID1' id='lbxBPNID1' value='"+(CommonFunction.checkNull(subList.get(4)).trim())+"' /> <input type='hidden' name='lbxBankID1' id='lbxBankID1' value='"+(CommonFunction.checkNull(subList.get(6)).trim())+"' /> <input type='hidden' name='lbxBranchID1' id='lbxBranchID1' value='"+(CommonFunction.checkNull(subList.get(8)).trim())+"' />  <input type='hidden' name='depositBankId' id='depositBankId' value='"+(CommonFunction.checkNull(subList.get(17)).trim())+"' /> <input type='hidden' name='depositBranchId' id='depositBranchId' value='"+(CommonFunction.checkNull(subList.get(18)).trim())+"' /> <input type='hidden' name='depositMicrCode' id='depositMicrCode' value='"+(CommonFunction.checkNull(subList.get(19)).trim())+"' /> <input type='hidden' name='depositIfscCode' id='depositIfscCode' value='"+(CommonFunction.checkNull(subList.get(20)).trim())+"' /><input type='hidden' name='instrumentMode' id='instrumentMode' value='"+(CommonFunction.checkNull(subList.get(25)).trim())+"' />  <input type='hidden' name='depositBankAccount' id='depositBankAccount' value='"+(CommonFunction.checkNull(subList.get(21)).trim())+"' />");  
					
					chequeStatusVO1.setLoanAccNo((CommonFunction.checkNull(subList.get(24)).trim()));
					
					chequeStatusVO1.setValueDate("<input type='text' class='text6' name='valueDate' id='valueDate"+i+"' value='"+(CommonFunction.checkNull(subList.get(26)).trim())+"' />");
					
					chequeStatusVO1.setHideDate((CommonFunction.checkNull(subList.get(27)).trim()));
					chequeStatusVO1.setLoanRecStatus((CommonFunction.checkNull(subList.get(28)).trim()));
					chequeStatusVO1.setReciptNo((CommonFunction.checkNull(subList.get(29)).trim()));
					chequeStatusVO1.setReciptDate((CommonFunction.checkNull(subList.get(30)).trim()));
					chequeStatusVO1.setDefaultBranch((CommonFunction.checkNull(subList.get(31)).trim()));
					
					chequeStatusVO1.setReasonRemarks("<input type='text' class='text' name='reasonRemarks' id='reasonRemarks"+i+"' value='"+(CommonFunction.checkNull(subList.get(32)).trim())+"' />");
					
					chequeStatusVO1.setDepositBank((CommonFunction.checkNull(subList.get(33))).trim());
					chequeStatusVO1.setDepositBankBranch((CommonFunction.checkNull(subList.get(34))).trim());
					chequeStatusVO1.setDepositBankAccount((CommonFunction.checkNull(subList.get(35))).trim());
					
					//Hidden fields--
					//chequeStatusVO1.setReceivedDate("<input type='text' name='receivedDate"+i+"' id='receivedDate"+i+"' value='"+(CommonFunction.checkNull(subList.get(27)).trim())+"' />");
//					chequeStatusVO1.setHiddenDate("<input type='text' name='date1' id='date1' value='"+(CommonFunction.checkNull(subList.get(1)).trim())+"' />");
//					chequeStatusVO1.setHiddenInstrumentAmount("<input type='text' name='instrumentAmount1' id='instrumentAmount1' value='"+myFormatter.format(instrumentAmount)+"' />");
//					chequeStatusVO1.setHiddenBusParDesc("<input type='text' name='businessPartnerDesc1' id='businessPartnerDesc1' value='"+(CommonFunction.checkNull(subList.get(12)).trim())+"' />");
//					chequeStatusVO1.setHiddenBusParType("<input type='text' name='businessPartnerType1' id='businessPartnerType1' value='"+(CommonFunction.checkNull(subList.get(11)).trim())+"' />");
//					chequeStatusVO1.setHiddenBank("<input type='text' name='bank1' id='bank1' value='"+(CommonFunction.checkNull(subList.get(5)).trim())+"' />");
//					chequeStatusVO1.setHiddenBranch("<input type='text' name='branch1' id='branch1' value='"+(CommonFunction.checkNull(subList.get(7)).trim())+"' />");
//					chequeStatusVO1.setHiddenStatus("<input type='text' name='status1' id='status1' value='"+(CommonFunction.checkNull(subList.get(10)).trim())+"' />");
//					chequeStatusVO1.setHiddenTDS("<input type='text' name='tdsAmount1' id='tdsAmount1' value='"+myFormatter.format(tdsAmount)+"' />");
//					chequeStatusVO1.setHiddenPDCInstrumentID("<input type='text' name='pdcInstrumentId' id='pdcInstrumentId' value='"+(CommonFunction.checkNull(subList.get(15)).trim())+"' />");
//					chequeStatusVO1.setLbxBPTypeHID("<input type='text' name='lbxBPTypeHID1' id='lbxBPTypeHID1' value='"+(CommonFunction.checkNull(subList.get(3)).trim())+"' />");
//					chequeStatusVO1.setLbxBPNID("<input type='text' name='lbxBPNID1' id='lbxBPNID1' value='"+(CommonFunction.checkNull(subList.get(4)).trim())+"' />");
//					chequeStatusVO1.setLbxBankID("<input type='text' name='lbxBankID1' id='lbxBankID1' value='"+(CommonFunction.checkNull(subList.get(6)).trim())+"' />");
//					chequeStatusVO1.setLbxBranchID("<input type='text' name='lbxBranchID1' id='lbxBranchID1' value='"+(CommonFunction.checkNull(subList.get(8)).trim())+"' />");
//							
					
					chequeStatusVO1.setTotalRecordSize(count);
					chequeStatusVO1.setNoOfRecords(noOfRecords);
					chequeStatusList.add(chequeStatusVO1);
			}
	}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			businessPartnerType = null;
			businessPartnerName = null;
			pdcFlag = null;
			customerName = null;
			paymentMode = null;
			lbxBPNID = null;
			chequeStatus = null;
			lbxBPTypeHID = null;
			loanAccNo = null;
			instrumentType = null;
			lbxLoanNoHID = null;
			instrumentNo = null;
			instrumentDate = null;
		}

		return chequeStatusList;
	 
	}
	public ArrayList searchChequeStatusForLoanViewer(ChequeStatusVO chequeStatusVO,String loanId) 
	{
		ArrayList<ChequeStatusVO> chequeStatusList = new ArrayList<ChequeStatusVO>();
			logger.info("In searchChequeStatusForLoanViewer ");
			StringBuilder pdcFlag = new StringBuilder();
			StringBuilder customerName = new StringBuilder();
			StringBuilder paymentMode = new StringBuilder();
			StringBuilder chequeStatus = new StringBuilder();
			StringBuilder loanAccNo = new StringBuilder();
			StringBuilder instrumentType = new StringBuilder();
			StringBuilder lbxLoanNoHID = new StringBuilder();
			StringBuilder instrumentNo = new StringBuilder();
			StringBuilder receiptNo = new StringBuilder();

	        int count=0;
	        int noOfRecords;
	        ArrayList mainlist=new ArrayList();
			ArrayList subList=new ArrayList();
			String branch=null;
			branch=StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getBranch()).trim());
			try
			{
				pdcFlag.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getPdcFlag()).trim()));
	            paymentMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getPaymentMode()).trim()));
	            chequeStatus.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getChequeStatus()).trim()));
	            noOfRecords=chequeStatusVO.getNoOfRecords();
	            loanAccNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getLoanAccNo()).trim()));
	            instrumentNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getInstrumentNo()).trim()));
	            instrumentType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getInstrumentType()).trim()));
	            lbxLoanNoHID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()));
	            customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getCustomerName()).trim()));
	            receiptNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chequeStatusVO.getReciptNo()).trim()));
	   
	           	StringBuffer bufInsSql =    new StringBuffer();
	           	StringBuffer bufInsSqlTempCount = new StringBuffer();
	           	if((instrumentType.toString()).equalsIgnoreCase("P"))
	           	{
	           		bufInsSql.append("select distinct a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'%d-%m-%Y'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
	           	    			     " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
	           	            	     " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
	           	            		 " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no, "+
	           	            		 " DATE_FORMAT(VALUE_DATE,'%d-%m-%Y'),a.RECIPT_NO,concat((select USER_NAME from sec_user_m where user_id=u.AUTHOR_ID),'-', u.AUTHOR_ID) as AUTHOR_ID,BM.BANK_NAME,BRM.BANK_BRANCH_NAME,a.DEPOSIT_BANK_ACCOUNT "
	           	            		 +" ,case A.INSTRUMENT_MODE when 'Q' then 'Cheque' when 'C' then 'Cash' when 'D' then 'Draft' when 'R' then 'RTGS' when 'N' then 'NEFT' when 'B' then 'Direct Debit' when 'S' then 'Adjustment' when 'E' then 'ECS' else 'NA' end as MODE_DESC"
	           	            		 +" from cr_instrument_dtl a "+
	           	            		 " left outer join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
	           	            		 " left outer join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
	           	            		 " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
         	            			   " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=a.DEPOSIT_BRANCH_ID "+
	           	            		 	"  left outer join  business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
	           	            			"  left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
	           	            			" and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
	           	            			"  left outer join  com_reason_m r on  r.REASON_ID=u.REASON_ID "+
	           	            			" inner join cr_loan_dtl z on a.TXNID = z.loan_id "+
	           	            			" where    z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='LIM' and A.instrument_type ='P' and a.rec_status not in ('P','F') "+
	           	            			" and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') )"); 
	           	            			  
	           	     bufInsSqlTempCount.append("select count(1) from (select a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'%d-%m-%Y'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
	           	    			     " c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
	           	            	     " a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
	           	            		 " a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no, "+
	           	            		 " DATE_FORMAT(VALUE_DATE,'%d-%m-%Y'),a.RECIPT_NO,concat((select USER_NAME from sec_user_m where user_id=u.AUTHOR_ID),'-', u.AUTHOR_ID) as AUTHOR_ID from cr_instrument_dtl a "+
	           	            		 "  left outer join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
	           	            		 " left outer join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
	           	            		 " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
         	            			   " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=a.DEPOSIT_BRANCH_ID "+
            	            			"  left outer join  business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
	           	            			"  left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
	           	            			" and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
	           	            			" left outer join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
	           	            			" inner join cr_loan_dtl z on a.TXNID = z.loan_id "+
	           	            			" where  z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='LIM' and  A.instrument_type ='P' and a.rec_status not in ('P','F')  "+
	           	            			" and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') )");
	           
	           	}
	           	else
	           	{
	           	    bufInsSql.append("select distinct a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'%d-%m-%Y'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
	           	            		" c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
	           	            		" a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
	           	            		" a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no, "+
	           	            		" DATE_FORMAT(VALUE_DATE,'%d-%m-%Y'),a.RECIPT_NO,concat((select USER_NAME from sec_user_m where user_id=u.AUTHOR_ID),'-', u.AUTHOR_ID) as AUTHOR_ID,BM.BANK_NAME,BRM.BANK_BRANCH_NAME,a.DEPOSIT_BANK_ACCOUNT "
	           	            		+" ,case A.INSTRUMENT_MODE when 'Q' then 'Cheque' when 'C' then 'Cash' when 'D' then 'Draft' when 'R' then 'RTGS' when 'N' then 'NEFT' when 'B' then 'Direct Debit' when 'S' then 'Adjustment' when 'E' then 'ECS' else 'NA' end as MODE_DESC"
	           	            		+" from cr_instrument_dtl a "+
	           	            	    " left outer join com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
	           	            		" left outer join com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
	           	            	   " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
     	            			   " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=a.DEPOSIT_BRANCH_ID "+
	           	            		"  left outer join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
	           	            		"  left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
	           	            		" and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
	           	            		"  left outer join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
	           	            		" inner join cr_loan_dtl z on a.TXNID = z.loan_id "+
	           	            		" where z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='LIM' and   A.instrument_type ='R' and a.rec_status not in ('P','F') "+
	           	            		" and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') ) ");  
	           	            			 
	           	            	   
	           	            	   
	   	            	 
	   	            	bufInsSqlTempCount.append("select count(1) from (select distinct a.instrument_no,DATE_FORMAT(a.INSTRUMENT_DATE,'%d-%m-%Y'),a.INSTRUMENT_AMOUNT,a.BPID,a.BPTYPE,b.bank_name,a.ISSUEING_BANK_ID, "+
	           	            		" c.bank_branch_name,a.ISSUEING_BRANCH_ID,a.ISSUEING_BANK_ACCOUNT,a.REC_STATUS,(select description from  generic_master   where generic_key='BPTYPE' and value=a.BPTYPE)Description,e.bp_name,A.INSTRUMENT_ID,a.tds_amount,"+
	           	            		" a.pdc_instrument_id, a.instrument_type,a.DEPOSIT_BANK_ID,a.DEPOSIT_BRANCH_ID,a.DEPOSIT_MICR_CODE,a.DEPOSIT_IFSC_CODE, "+
	           	            		" a.DEPOSIT_BANK_ACCOUNT,u.REASON_ID,r.REASON_DESC,z.loan_no, "+
	           	            		" DATE_FORMAT(VALUE_DATE,'%d-%m-%Y'),a.RECIPT_NO,concat((select USER_NAME from sec_user_m where user_id=u.AUTHOR_ID),'-', u.AUTHOR_ID) as AUTHOR_ID from cr_instrument_dtl a "+
	           	            	    "  left outer join  com_bank_m b on a.ISSUEING_BANK_ID = b.BANK_ID "+
	           	            		"  left outer join  com_bankbranch_m c on a.ISSUEING_BRANCH_ID = c.bank_BRANCH_ID "+
	           	            	    " LEFT JOIN COM_BANK_M BM ON BM.BANK_ID=a.DEPOSIT_BANK_ID " +
     	            			    " LEFT JOIN COM_BANKBRANCH_M BRM ON BRM.BANK_BRANCH_ID=a.DEPOSIT_BRANCH_ID "+
	           	            		"  left outer join business_partner_view e on a.TXNID = e.LOAN_ID and a.bpid=e.bp_id and a.bptype=e.bp_type "+
	           	            		"  left outer join cr_instrument_update_dtl u on u.INSTRUMENT_ID=a.INSTRUMENT_ID and u.INSTRUMENT_TYPE=a.INSTRUMENT_TYPE and u.next_STATUS=a.REC_STATUS " +
	           	            		" and u.INSTRUMENT_BATCH_ID = (select max(k.INSTRUMENT_BATCH_ID) from cr_instrument_update_dtl k where k.INSTRUMENT_ID=a.INSTRUMENT_ID) "+
	           	            		"  left outer join com_reason_m r on  r.REASON_ID=u.REASON_ID "+
	           	            		" inner join cr_loan_dtl z on a.TXNID = z.loan_id "+
	           	            		" where  z.rec_status<>'L' and ifnull(a.TXN_TYPE,'')='LIM' and  A.instrument_type ='R' and a.rec_status not in ('P','F') "+
	           	            		" and a.INSTRUMENT_ID not in(select INSTRUMENT_ID from cr_instrument_update_dtl where REC_STATUS in ('P','F') )");   
		            	 	   
	           	               }
	           	               
	           	            if((!((lbxLoanNoHID.toString()).equalsIgnoreCase("")))) 
	           	            {
	           	            	bufInsSql.append(" AND a.TXNID='"+lbxLoanNoHID+"'");
	           	                bufInsSqlTempCount.append(" AND a.TXNID='"+lbxLoanNoHID+"'");
	           	            }	
	         
	           	              if((!((pdcFlag.toString()).equalsIgnoreCase("ALL")))) 
	           	              {
	           	                   bufInsSql.append("  AND a.PDC_FLAG='"+pdcFlag+"'");
	           	                   bufInsSqlTempCount.append("  AND a.PDC_FLAG='"+pdcFlag+"'");
	           	               }
	           	             if((!((instrumentNo.toString()).equalsIgnoreCase("")))) {
	           	                  bufInsSql.append(" AND a.INSTRUMENT_NO='"+instrumentNo+"'");
	           	               bufInsSqlTempCount.append(" AND a.INSTRUMENT_NO='"+instrumentNo+"'");
	           	               }
	                           if((!((chequeStatus.toString()).equalsIgnoreCase("ALL")))){
	                        	   if((!((chequeStatus.toString()).equalsIgnoreCase("")))) {
		           	                   bufInsSql.append("  AND a.REC_STATUS='"+chequeStatus+"'");
		           	                bufInsSqlTempCount.append("  AND a.REC_STATUS='"+chequeStatus+"'");
		           	               }
	                           }
	           	              if((!((paymentMode.toString()).equalsIgnoreCase("ALL")))) {
	           	                   bufInsSql.append("  AND a.INSTRUMENT_MODE='"+paymentMode+"'");
	           	                bufInsSqlTempCount.append("  AND a.INSTRUMENT_MODE='"+paymentMode+"'");
	           	               }
	           	           if((!((receiptNo.toString()).equalsIgnoreCase("")))) {
           	                   bufInsSql.append("  AND a.RECIPT_NO='"+receiptNo+"'");
           	                bufInsSqlTempCount.append("  AND a.RECIPT_NO='"+receiptNo+"'");
           	               }
	           	              
	           	           bufInsSql.append("  order by a.INSTRUMENT_DATE");
	           	           bufInsSqlTempCount.append(") as f");
	           	           logger.info("Query::::::::::::::::::::::"+bufInsSql.toString());
	          	            mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
	          	           
	          	          logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	                      count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
	                      
	                    int startRecordIndex=0;
	                    int endRecordIndex=0;
	          			if(chequeStatusVO.getCurrentPageLink()>1)
	          			{
	          				startRecordIndex = (chequeStatusVO.getCurrentPageLink()-1) * noOfRecords;
	          				endRecordIndex = noOfRecords;
	          				logger.info("startRecordIndex .................... "+startRecordIndex);
	          				logger.info("endRecordIndex .................... "+endRecordIndex);
	          			}
	          			
	          			bufInsSql.append(" limit "+startRecordIndex+","+noOfRecords);
	          			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
	          			
	          			logger.info("count"+count);
	        	if(mainlist.size()<=0){
	        		ChequeStatusVO chequeStatusVO1=new ChequeStatusVO();
	        		chequeStatusVO1.setTotalRecordSize(count);
	        		chequeStatusList.add(chequeStatusVO1);
	        		
	        		
	        	}
	        	else
	        	{
			for(int i=0;i<mainlist.size();i++){

				subList=(ArrayList)mainlist.get(i);
				logger.info("In searchChequeStatusForLoanViewer sublist..."+subList.size());
				
				if(subList.size()>0){
					
					    ChequeStatusVO chequeStatusVO1=new ChequeStatusVO();
					    chequeStatusVO1.setCheckBox("<input type='checkbox' name='chk' id='chk' value='"+(CommonFunction.checkNull(subList.get(13)).trim())+"' />");
					    
					    chequeStatusVO1.setInstrumentNo((CommonFunction.checkNull(subList.get(0)).trim()));
					    chequeStatusVO1.setDate((CommonFunction.checkNull(subList.get(1)).trim()));
					    Number instrumentAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
					    chequeStatusVO1.setInstrumentAmount(myFormatter.format(instrumentAmount));
					    
						chequeStatusVO1.setBank((CommonFunction.checkNull(subList.get(5)).trim()));
						chequeStatusVO1.setBranch((CommonFunction.checkNull(subList.get(7)).trim()));
						chequeStatusVO1.setBankAccount((CommonFunction.checkNull(subList.get(9)).trim()));
						chequeStatusVO1.setStatus((CommonFunction.checkNull(subList.get(10)).trim()));
						
						if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("A")){
							chequeStatusVO1.setStatusName("Approved");
						}
						
						if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("C")){
							chequeStatusVO1.setStatusName("Sent To Customer");
						}
						
						if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("S")){
							chequeStatusVO1.setStatusName("Stop Payment");
						}
						
						if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("D")){
							chequeStatusVO1.setStatusName("Deposit");
						}
						
						if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("R")){
							chequeStatusVO1.setStatusName("Realized");
						}
						
						if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("B")){
							chequeStatusVO1.setStatusName("Bounce");
						}
						
						if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase("X")){
							chequeStatusVO1.setStatusName("Cancel");
						}
						
						chequeStatusVO1.setBusinessPartnerType((CommonFunction.checkNull(subList.get(11)).trim()));
						chequeStatusVO1.setBusinessPartnerDesc((CommonFunction.checkNull(subList.get(12)).trim()));

						 Number tdsAmount =myFormatter.parse((CommonFunction.checkNull(subList.get(14))).trim());
						  chequeStatusVO1.setTdsAmount(myFormatter.format(tdsAmount));
						
					
						chequeStatusVO1.setPdcInstrumentId((CommonFunction.checkNull(subList.get(15)).trim()));
						
						if(CommonFunction.checkNull(subList.get(16)).trim().equalsIgnoreCase("P")){
							chequeStatusVO1.setInstrumentType("Payable by "+(CommonFunction.checkNull(subList.get(31))).trim());
						}else if(CommonFunction.checkNull(subList.get(16)).trim().equalsIgnoreCase("R")){
							chequeStatusVO1.setInstrumentType("Receivables by "+(CommonFunction.checkNull(subList.get(31))).trim());
						}
						//chequeStatusVO1.setInstrumentType((CommonFunction.checkNull(subList.get(16)).trim()));
						
						chequeStatusVO1.setReasonLov("<input type='text' class='text' name='reason' id='reason"+i+"' value='"+(CommonFunction.checkNull(subList.get(23)).trim())+"' /> <input type='button' class='lovbutton' onclick=\"openLOVCommon(70,\'sourcingForm\',\'reason"+i+"\',\'\',\'\', \'\',\'\',\'\',\'lbxReasonHID"+i+"\')\"/> <input type='hidden' name='lbxReasonHID1' id='lbxReasonHID' value='"+(CommonFunction.checkNull(subList.get(22)).trim())+"'/>  <input type='hidden' name='lbxReasonHID' id='lbxReasonHID"+i+"'/> <input type='hidden' name='instrumentNo1' id='instrumentNo1' value='"+(CommonFunction.checkNull(subList.get(0)).trim())+"' /> <input type='hidden' name='date1' id='date1' value='"+(CommonFunction.checkNull(subList.get(1)).trim())+"' /> <input type='hidden' name='instrumentAmount1' id='instrumentAmount1' value='"+myFormatter.format(instrumentAmount)+"' />  <input type='hidden' name='businessPartnerDesc1' id='businessPartnerDesc1' value='"+(CommonFunction.checkNull(subList.get(12)).trim())+"' />  <input type='hidden' name='businessPartnerType1' id='businessPartnerType1' value='"+(CommonFunction.checkNull(subList.get(11)).trim())+"' /> <input type='hidden' name='bank1' id='bank1' value='"+(CommonFunction.checkNull(subList.get(5)).trim())+"' /> <input type='hidden' name='branch1' id='branch1' value='"+(CommonFunction.checkNull(subList.get(7)).trim())+"' /> <input type='hidden' name='status1' id='status1' value='"+(CommonFunction.checkNull(subList.get(10)).trim())+"' /> <input type='hidden' name='tdsAmount1' id='tdsAmount1' value='"+myFormatter.format(tdsAmount)+"' />  <input type='hidden' name='pdcInstrumentId' id='pdcInstrumentId' value='"+(CommonFunction.checkNull(subList.get(15)).trim())+"' /> <input type='hidden' name='lbxBPTypeHID1' id='lbxBPTypeHID1' value='"+(CommonFunction.checkNull(subList.get(3)).trim())+"' /> <input type='hidden' name='lbxBPNID1' id='lbxBPNID1' value='"+(CommonFunction.checkNull(subList.get(4)).trim())+"' /> <input type='hidden' name='lbxBankID1' id='lbxBankID1' value='"+(CommonFunction.checkNull(subList.get(6)).trim())+"' /> <input type='hidden' name='lbxBranchID1' id='lbxBranchID1' value='"+(CommonFunction.checkNull(subList.get(8)).trim())+"' />  <input type='hidden' name='depositBankId' id='depositBankId' value='"+(CommonFunction.checkNull(subList.get(17)).trim())+"' /> <input type='hidden' name='depositBranchId' id='depositBranchId' value='"+(CommonFunction.checkNull(subList.get(18)).trim())+"' /> <input type='hidden' name='depositMicrCode' id='depositMicrCode' value='"+(CommonFunction.checkNull(subList.get(19)).trim())+"' /> <input type='hidden' name='depositIfscCode' id='depositIfscCode' value='"+(CommonFunction.checkNull(subList.get(20)).trim())+"' />  <input type='hidden' name='depositBankAccount' id='depositBankAccount' value='"+(CommonFunction.checkNull(subList.get(21)).trim())+"' />");  
		/*SACHIN*/				chequeStatusVO1.setReason((CommonFunction.checkNull(subList.get(23)).trim()));
						chequeStatusVO1.setLoanAccNo((CommonFunction.checkNull(subList.get(24)).trim()));
	/*SACHIN*/					chequeStatusVO1.setValueDate((CommonFunction.checkNull(subList.get(25)).trim()));
						chequeStatusVO1.setReciptNo((CommonFunction.checkNull(subList.get(26)).trim()));
						chequeStatusVO1.setAuthorId((CommonFunction.checkNull(subList.get(27)).trim()));
						
						chequeStatusVO1.setDepositBank((CommonFunction.checkNull(subList.get(28))).trim());
						chequeStatusVO1.setDepositBankBranch((CommonFunction.checkNull(subList.get(29))).trim());
						chequeStatusVO1.setDepositBankAccount((CommonFunction.checkNull(subList.get(30))).trim());
						chequeStatusVO1.setInstrumentModeDesc((CommonFunction.checkNull(subList.get(31))).trim());
						
						//Hidden fields--
//						chequeStatusVO1.setHiddenInstrumentNo("<input type='text' name='instrumentNo1' id='instrumentNo1' value='"+(CommonFunction.checkNull(subList.get(0)).trim())+"' />");
//						chequeStatusVO1.setHiddenDate("<input type='text' name='date1' id='date1' value='"+(CommonFunction.checkNull(subList.get(1)).trim())+"' />");
//						chequeStatusVO1.setHiddenInstrumentAmount("<input type='text' name='instrumentAmount1' id='instrumentAmount1' value='"+myFormatter.format(instrumentAmount)+"' />");
//						chequeStatusVO1.setHiddenBusParDesc("<input type='text' name='businessPartnerDesc1' id='businessPartnerDesc1' value='"+(CommonFunction.checkNull(subList.get(12)).trim())+"' />");
//						chequeStatusVO1.setHiddenBusParType("<input type='text' name='businessPartnerType1' id='businessPartnerType1' value='"+(CommonFunction.checkNull(subList.get(11)).trim())+"' />");
//						chequeStatusVO1.setHiddenBank("<input type='text' name='bank1' id='bank1' value='"+(CommonFunction.checkNull(subList.get(5)).trim())+"' />");
//						chequeStatusVO1.setHiddenBranch("<input type='text' name='branch1' id='branch1' value='"+(CommonFunction.checkNull(subList.get(7)).trim())+"' />");
//						chequeStatusVO1.setHiddenStatus("<input type='text' name='status1' id='status1' value='"+(CommonFunction.checkNull(subList.get(10)).trim())+"' />");
//						chequeStatusVO1.setHiddenTDS("<input type='text' name='tdsAmount1' id='tdsAmount1' value='"+myFormatter.format(tdsAmount)+"' />");
//						chequeStatusVO1.setHiddenPDCInstrumentID("<input type='text' name='pdcInstrumentId' id='pdcInstrumentId' value='"+(CommonFunction.checkNull(subList.get(15)).trim())+"' />");
//						chequeStatusVO1.setLbxBPTypeHID("<input type='text' name='lbxBPTypeHID1' id='lbxBPTypeHID1' value='"+(CommonFunction.checkNull(subList.get(3)).trim())+"' />");
//						chequeStatusVO1.setLbxBPNID("<input type='text' name='lbxBPNID1' id='lbxBPNID1' value='"+(CommonFunction.checkNull(subList.get(4)).trim())+"' />");
//						chequeStatusVO1.setLbxBankID("<input type='text' name='lbxBankID1' id='lbxBankID1' value='"+(CommonFunction.checkNull(subList.get(6)).trim())+"' />");
//						chequeStatusVO1.setLbxBranchID("<input type='text' name='lbxBranchID1' id='lbxBranchID1' value='"+(CommonFunction.checkNull(subList.get(8)).trim())+"' />");
//								
						
						chequeStatusVO1.setTotalRecordSize(count);
						chequeStatusVO1.setNoOfRecords(noOfRecords);
						chequeStatusList.add(chequeStatusVO1);
				}
		}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				pdcFlag= null;
				customerName= null;
				paymentMode= null;
				chequeStatus= null;
				loanAccNo= null;
				instrumentType= null;
				lbxLoanNoHID= null;
				instrumentNo= null;
			}

			return chequeStatusList;
	}


}
