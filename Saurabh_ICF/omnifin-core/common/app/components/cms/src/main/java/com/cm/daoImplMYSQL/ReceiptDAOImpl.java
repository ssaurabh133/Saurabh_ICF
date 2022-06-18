package com.cm.daoImplMYSQL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.ReceiptDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cm.vo.ReceiptMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.logger.LoggerMsg;

public class ReceiptDAOImpl implements ReceiptDAO{
	private static final Logger logger = Logger.getLogger(ReceiptDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));


	public ArrayList <ReceiptMakerVO> receiptMakerGrid(ReceiptMakerVO vo)
	{
		logger.info("In receiptMakerGrid() of ReceiptDAOImpl");
		StringBuilder loanNo = new StringBuilder();
		StringBuilder customerName = new StringBuilder();
		StringBuilder businessPartnerType = new StringBuilder();
		StringBuilder businessPartnerID = new StringBuilder();
		StringBuilder receiptAmount = new StringBuilder();
		StringBuilder instrumentNumber = new StringBuilder();
		StringBuilder receiptMode = new StringBuilder();
		
	  int count=0;
	  int startRecordIndex=0;
	  int endRecordIndex = no;
	  ArrayList<ReceiptMakerVO> receiptMakerSearchGrid=new 	ArrayList<ReceiptMakerVO>();
	  
	  try{
			ArrayList searchlist=null;
			ReceiptMakerVO receiptvo=null;
	        StringBuffer bufInsSql =	new StringBuffer();
	        StringBuffer bufInsSqlTempCount = new StringBuffer();
	        boolean appendSQL=false;
	       
	        loanNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
	        customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
	        businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
	        businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
	   	    if(!CommonFunction.checkNull(vo.getReceiptAmount()).equalsIgnoreCase(""))
	   	    {
	        receiptAmount.append(CommonFunction.decimalNumberConvert(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
	       
	   	    }
		    instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
		    receiptMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptMode()).trim()));
		
		      
	        bufInsSql.append(" SELECT  CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " +		  
			  		"   GM.DESCRIPTION,CID.BPID , " +		  	
			  		"   BPV.BP_NAME ,  CID.INSTRUMENT_AMOUNT,CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID  " +
			        "   from gcd_customer_m GCM ,cr_loan_dtl CLD, cr_instrument_dtl CID, business_partner_view BPV, generic_master  GM  " +
			        "   WHERE CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID  AND BPV.BP_ID=CID.BPID AND  BPV.LOAN_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE and CLD.LOAN_ID=CID.TXNID " +
			        "   AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='P' and INSTRUMENT_TYPE='R' and ifnull(CID.TXN_TYPE,'')='LIM' AND CID.MAKER_ID='"+vo.getReportingToUserId()+"' AND CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' ");
	       
//	        bufInsSqlTempCount.append(" SELECT  count(1)  from gcd_customer_m GCM ,cr_loan_dtl CLD, cr_instrument_dtl CID, business_partner_view BPV, generic_master  GM " +
//			  		" WHERE CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID  AND BPV.BP_ID=CID.BPID AND  BPV.LOAN_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE and CLD.LOAN_ID=CID.TXNID " +
//			  		" AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='P' and ifnull(CID.TXN_TYPE,'')='LIM' and INSTRUMENT_TYPE='R' AND " +
//			  		" CID.MAKER_ID='"+vo.getReportingToUserId()+"' AND CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' ");
//	        
			bufInsSqlTempCount.append(" SELECT COUNT(1) FROM CR_INSTRUMENT_DTL CID JOIN CR_LOAN_DTL CLD ON CID.TXNID=CLD.LOAN_ID AND ifnull(CID.TXN_TYPE,'')='LIM' "); 
			bufInsSqlTempCount.append(" AND INSTRUMENT_TYPE='R'  AND CID.REC_STATUS='P' AND BPID IS NOT NULL ");
			if((!((customerName.toString()).trim().equalsIgnoreCase("")))) {			
				bufInsSqlTempCount.append(" JOIN GCD_CUSTOMER_M GCM ON CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID ");
			}
			bufInsSqlTempCount.append(" WHERE  CID.MAKER_ID='"+vo.getReportingToUserId()+"' AND CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' ");


	        	 if((!((loanNo.toString()).trim().equalsIgnoreCase("")))) {
	    	         bufInsSql.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
	    	         bufInsSqlTempCount.append("  AND CLD.LOAN_ID='"+loanNo+"' ");
	    	         appendSQL=true;
	    	    	  
	    	      }
	    			 
	    			if((!((customerName.toString()).trim().equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append("  AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' ");
	    	    	  bufInsSqlTempCount.append("  AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' ");
	    	    	  appendSQL=true;
	    	      }
	    			
	    			if((!((businessPartnerType.toString()).trim().equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append("  AND CID.BPTYPE='"+(businessPartnerType.toString()).trim()+"' ");
	    	    	  bufInsSqlTempCount.append("  AND CID.BPTYPE='"+(businessPartnerType.toString()).trim()+"' ");
	    	    	  appendSQL=true;
	    	      }
	    			
	    			if((!((businessPartnerID.toString()).trim().equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append("  AND CID.BPID='"+businessPartnerID+"' ");
	    	    	  bufInsSqlTempCount.append("  AND CID.BPID='"+businessPartnerID+"' ");
	    	    	  appendSQL=true;
	    	      }
	    			
	    			if((!((receiptAmount.toString()).trim().equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append("  AND CID.INSTRUMENT_AMOUNT='"+receiptAmount+"' ");
	    	    	  bufInsSqlTempCount.append("  AND CID.INSTRUMENT_AMOUNT='"+receiptAmount+"' ");
	    	    	  appendSQL=true;
	    	      }
	    			
	    			if((!((instrumentNumber.toString()).trim().equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append("  AND CID.INSTRUMENT_NO='"+instrumentNumber+"' ");
	    	    	  bufInsSqlTempCount.append("  AND CID.INSTRUMENT_NO='"+instrumentNumber+"' ");
	    	    	  appendSQL=true;
	    	      }

					if((!((receiptMode.toString()).trim().equalsIgnoreCase("")))) {
				    	  bufInsSql.append("  AND CID.INSTRUMENT_MODE='"+receiptMode+"'");
				    	  bufInsSqlTempCount.append("  AND CID.INSTRUMENT_MODE='"+receiptMode+"'");
				    	  appendSQL=true;
				      }
	    				if(vo.getCurrentPageLink()>1)
						{
							startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							logger.info("startRecordIndex .................... "+startRecordIndex);
							logger.info("endRecordIndex .................... "+endRecordIndex);
						}
						
						
						bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
								
					  
					  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
					  count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
					  bufInsSql=null;
					  bufInsSqlTempCount=null;
	    	     int size=searchlist.size();
	            for(int i=0;i<size;i++){
	            	

	            ArrayList data=(ArrayList)searchlist.get(i);

	        if(data.size()>0){
	        	
	        	receiptvo = new ReceiptMakerVO();
	        	receiptvo.setModifyNo("<a href=receiptMakerViewAction.do?method=savedDataOfReceipt&lbxLoanNoHID="
							+ CommonFunction.checkNull(data.get(0)).toString()
							+ "&instrumentID="+CommonFunction.checkNull(data.get(10)).toString()+">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");

			receiptvo.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
			receiptvo.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
		
			receiptvo.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
			receiptvo.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
			receiptvo.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
			receiptvo.setLbxBPNID(CommonFunction.checkNull(data.get(5)).toString());
			
			receiptvo.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));

			Number ReceiptAmount=0;
			if(!CommonFunction.checkNull(data.get(7)).equals(""))
			{
				ReceiptAmount = CommonFunction.decimalNumberConvert(CommonFunction.checkNull(data.get(7)).trim());
			}
			
		
			receiptvo.setReceiptAmount(myFormatter.format(ReceiptAmount));

			receiptvo.setInstrumentNumber(CommonFunction.checkNull(data.get(8)).trim());
			receiptvo.setReceiptMode(CommonFunction.checkNull(data.get(9)).trim());
			 if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("C")){ 	    		
				 receiptvo.setReceiptMode("Cash");
	    	      	  } 
	    	  else if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("Q")){
	    		  receiptvo.setReceiptMode("Cheque");
	    	  }
	    	  else if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("D")){
	    		  receiptvo.setReceiptMode("DD");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("N")){
	    		  receiptvo.setReceiptMode("NEFT");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("R")){
	    		  receiptvo.setReceiptMode("RTGS");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("S")){
	    		  receiptvo.setReceiptMode("Adjustment");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("DIR")){
	    		  receiptvo.setReceiptMode("Direct Debit");
	    	  }
	        receiptvo.setInstrumentID(CommonFunction.checkNull(data.get(10)).trim());

	        receiptvo.setTotalRecordSize(count);
	        receiptvo.setReportingToUserId(CommonFunction.checkNull(data.get(11)).trim());
	        receiptMakerSearchGrid.add(receiptvo);
	      	
	         }
	        receiptvo=null;
	        data.clear();
	        data=null;
		      }
	            searchlist.clear();
	            searchlist=null;

		}catch(Exception e){
			e.printStackTrace();
				}
		finally
		{
			loanNo = null ;
			customerName = null;
			businessPartnerType = null;
			businessPartnerID = null;
			receiptAmount = null;
			instrumentNumber = null;
			receiptMode = null;
			vo=null;
		}
		return  receiptMakerSearchGrid;	
	}

	public ArrayList <ReceiptMakerVO> receiptAuthorGrid(ReceiptMakerVO vo)
	{
		logger.info("In receiptAuthorGrid() of ReceiptDAOImpl");
		StringBuilder loanNo = new StringBuilder();
		StringBuilder businessPartnerType = new StringBuilder();
		StringBuilder businessPartnerID = new StringBuilder();
		StringBuilder receiptAmount = new StringBuilder();
		StringBuilder instrumentNumber = new StringBuilder();
		StringBuilder customerName = new StringBuilder();
		StringBuilder receiptMode = new StringBuilder();
		ReceiptMakerVO receiptVO=null;
	    int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		
	    ArrayList<ReceiptMakerVO> authordetailList=new 	ArrayList<ReceiptMakerVO>();
	  
	    try{
			  ArrayList searchlist=new ArrayList();

	          StringBuffer bufInsSql =	new StringBuffer();
	          StringBuffer bufInsSqlTempCount = new StringBuffer();
	          boolean appendSQL=false;		      
	          loanNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
	    
	          customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
	          businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
	          businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
	          if(!CommonFunction.checkNull(vo.getReceiptAmount()).equalsIgnoreCase(""))
		   	    {
	          receiptAmount.append(CommonFunction.decimalNumberConvert(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
		       
		   	    }
	          instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
	          receiptMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptMode()).trim()));
			
			     
	         
	          bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE,GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME," +
	          		" CID.INSTRUMENT_AMOUNT,CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID  " +
	          		" from cr_loan_dtl CLD " +
	          		" JOIN cr_instrument_dtl CID ON(ifnull(CID.TXN_TYPE,'')='LIM' AND CLD.LOAN_ID=CID.TXNID  AND INSTRUMENT_TYPE='R' AND CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' AND CID.REC_STATUS='F' AND CID.MAKER_ID!='"+vo.getUserId()+"' )" +
	          		" JOIN gcd_customer_m GCM ON(CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID )" +
	          		" JOIN business_partner_view BPV ON( BPV.BP_ID=CID.BPID AND BPV.LOAN_ID=CID.TXNID AND BPV.BP_TYPE=cid.BPTYPE) " +
	          		" JOIN generic_master  GM ON(GM.VALUE=CID.BPTYPE  AND GM.GENERIC_KEY='BPTYPE')");

	          bufInsSqlTempCount.append(" SELECT COUNT(1) FROM CR_INSTRUMENT_DTL CID JOIN CR_LOAN_DTL CLD ON CID.TXNID=CLD.LOAN_ID AND ifnull(CID.TXN_TYPE,'')='LIM' "); 
				bufInsSqlTempCount.append(" AND INSTRUMENT_TYPE='R'  AND CID.REC_STATUS='F' AND CID.MAKER_ID!='"+vo.getUserId()+"'  AND BPID IS NOT NULL ");
				if((!((customerName.toString()).trim().equalsIgnoreCase("")))) {			
					bufInsSqlTempCount.append(" JOIN GCD_CUSTOMER_M GCM ON CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID ");
				}
				bufInsSqlTempCount.append(" WHERE CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' ");

				
//	          bufInsSqlTempCount.append(" SELECT COUNT(1) FROM (SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE,GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME," +
//	          		" CID.INSTRUMENT_AMOUNT,CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID  " +
//	          		" from cr_loan_dtl CLD " +
//	          		" JOIN cr_instrument_dtl CID ON(ifnull(CID.TXN_TYPE,'')='LIM' AND CLD.LOAN_ID=CID.TXNID  AND INSTRUMENT_TYPE='R' AND CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' AND CID.REC_STATUS='F' AND CID.MAKER_ID!='"+vo.getUserId()+"' )" +
//	          		" JOIN gcd_customer_m GCM ON(CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID )" +
//	          		" JOIN business_partner_view BPV ON( BPV.BP_ID=CID.BPID AND BPV.LOAN_ID=CID.TXNID AND BPV.BP_TYPE=cid.BPTYPE) " +
//	          		" JOIN generic_master  GM ON(GM.VALUE=CID.BPTYPE  AND GM.GENERIC_KEY='BPTYPE') ");

			
	           if((((loanNo.toString()).trim().equalsIgnoreCase(""))) || (((customerName.toString()).trim().equalsIgnoreCase(""))) || (((businessPartnerType.toString()).trim().equalsIgnoreCase(""))) || (((businessPartnerID.toString()).trim().equalsIgnoreCase(""))) || (((receiptAmount.toString()).trim().equalsIgnoreCase(""))) || (((instrumentNumber.toString()).trim().equalsIgnoreCase("")))|| (((receiptMode.toString()).trim().equalsIgnoreCase("")))|| ((vo.getLbxUserId().equalsIgnoreCase("")))){
	  	  				appendSQL=true;
	  	  			}
	  	  		  

	           
	  	  		  if((!((loanNo.toString()).trim().equalsIgnoreCase("")))) {
	  	  	         bufInsSql.append("AND CLD.LOAN_ID='"+loanNo+"' ");
	  	  	         bufInsSqlTempCount.append("AND CLD.LOAN_ID='"+loanNo+"' ");
	  	  	    	 appendSQL=true;
	  	  	    	  
	  	  	      }
	  	  			 
	  	  			if((!((customerName.toString()).trim().equalsIgnoreCase("")))) {
	  	  	    	  bufInsSql.append("AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' ");
	  	  	    	  bufInsSqlTempCount.append("AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' ");
	  	  	    	  appendSQL=true;
	  	  	      }
	  	  			
	  	  		if((!((businessPartnerType.toString()).trim().trim().equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append("AND CID.BPTYPE='"+(businessPartnerType.toString()).trim()+"' ");
	    	    	  bufInsSqlTempCount.append("AND CID.BPTYPE='"+(businessPartnerType.toString()).trim()+"' ");
	    	    	  appendSQL=true;
	  	  	      }
	  	  			
	  	  			if((!((businessPartnerID.toString()).trim().equalsIgnoreCase("")))) {
	  	  	    	  bufInsSql.append("AND CID.BPID='"+businessPartnerID+"' ");
	  	  	    	  bufInsSqlTempCount.append("AND CID.BPID='"+businessPartnerID+"' ");
	  	  	    	  appendSQL=true;
	  	  	      }
	  	  			
	  	  			if((!((receiptAmount.toString()).trim().equalsIgnoreCase("")))) {
	  	  	    	  bufInsSql.append("AND CID.INSTRUMENT_AMOUNT='"+receiptAmount+"' ");
	  	  	    	  bufInsSqlTempCount .append("AND CID.INSTRUMENT_AMOUNT='"+receiptAmount+"' ");
	  	  	    	  appendSQL=true;
	  	  	      }
	  	  			
	  	  			if((!((instrumentNumber.toString()).trim().equalsIgnoreCase("")))) {
	  	  	    	  bufInsSql.append("AND CID.INSTRUMENT_NO='"+instrumentNumber+"'");
	  	  	    	  bufInsSqlTempCount.append("AND CID.INSTRUMENT_NO='"+instrumentNumber+"'");
	  	  	    	  appendSQL=true;
	  	  	      }

					if((!((receiptMode.toString()).trim().equalsIgnoreCase("")))) {
				    	  bufInsSql.append("AND CID.INSTRUMENT_MODE='"+receiptMode+"'");
				    	  bufInsSqlTempCount.append("AND CID.INSTRUMENT_MODE='"+receiptMode+"'");
				    	  appendSQL=true;
				      }
					if((!(vo.getLbxUserId().equalsIgnoreCase("")))) {
				    	  bufInsSql.append(" AND CID.MAKER_ID='"+vo.getLbxUserId()+"'");
				    	  bufInsSqlTempCount.append("AND CID.MAKER_ID='"+vo.getLbxUserId()+"'");
				    	  appendSQL=true;
				      }
//					bufInsSqlTempCount.append(" )AS B");
					
	  			
	  	  		
	  	  		 count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

			
					
					LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
					if(vo.getCurrentPageLink()>1)
					{
						startRecordIndex = (vo.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
						LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
					}
					
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);

				    searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
					  	  	  		
	  	         int size= searchlist.size(); 	          
	  	          for(int i=0;i<size;i++){
	  	      
	  	          ArrayList data=(ArrayList)searchlist.get(i);
	  	          
	  	          if(data.size()>0){
	        	  receiptVO = new ReceiptMakerVO();
	        	  receiptVO.setModifyNo("<a href=receiptAuthorProcessAction.do?method=getReceipttoApprove&lbxLoanNoHID="
							+ CommonFunction.checkNull(data.get(0)).toString()
							+ "&instrumentID="+CommonFunction.checkNull(data.get(10)).toString()
							+ "&lbxBPType="+CommonFunction.checkNull(data.get(3)).toString()+">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");

	        	
	        	  receiptVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
	        	  receiptVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
	        	  receiptVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
	        	  receiptVO.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
	        	  receiptVO.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
	        	  receiptVO.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
	        	  receiptVO.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
	        	  Number ReceiptAmount = 0;
	        	  if(!CommonFunction.checkNull(data.get(7)).equals(""))
	        	  {
	        		  ReceiptAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(data.get(7))).trim());
	        	  }
				 	
				  receiptVO.setReceiptAmount(myFormatter.format(ReceiptAmount));

	        	  receiptVO.setInstrumentNumber(CommonFunction.checkNull(data.get(8)).trim());
	        	  receiptVO.setReceiptMode(CommonFunction.checkNull(data.get(9)).trim());
					 if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("C")){ 	    		
						 receiptVO.setReceiptMode("Cash");
			    	      	  } 
			    	  else if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("Q")){
			    		  receiptVO.setReceiptMode("Cheque");
			    	  }
			    	  else if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("D")){
			    		  receiptVO.setReceiptMode("DD");
			    	  }
			    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("N")){
			    		  receiptVO.setReceiptMode("NEFT");
			    	  }
			    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("R")){
			    		  receiptVO.setReceiptMode("RTGS");
			    	  }
			    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("S")){
			    		  receiptVO.setReceiptMode("Adjustment");
			    	  }
	        	  receiptVO.setInstrumentID(CommonFunction.checkNull(data.get(10)));

	        	  receiptVO.setTotalRecordSize(count);
	        	  receiptVO.setReportingToUserId(CommonFunction.checkNull(data.get(11)));
	        	  authordetailList.add(receiptVO);	
	           }
	  	        data.clear();
	  	        data=null;
	  	        receiptVO=null;
		      }
	  	      searchlist.clear();
	  	      searchlist=null;
	  	      bufInsSql=null;
	  	    bufInsSqlTempCount=null;
		}catch(Exception e){
			e.printStackTrace();
				}
		finally
		{
			loanNo = null;
			businessPartnerType = null;
			businessPartnerID = null;
			receiptAmount = null;
			instrumentNumber = null;
			customerName = null;
			receiptMode = null;
			vo=null;
			
		}
		return  authordetailList;	
	}
	
	 public ArrayList <ReceiptMakerVO> getReceiptList(ReceiptMakerVO vo)
		{
		 logger.info("In getReceiptList() of ReceiptDAOImpl");

		 ReceiptMakerVO receiptvo=null;
		ArrayList<ReceiptMakerVO> savedReceipt=new ArrayList<ReceiptMakerVO>();
		try{
			  ArrayList searchlist=new ArrayList();
		
		  StringBuffer bufInsSql =	new StringBuffer();
		  bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " +		  
			  		"   GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME , " +	
			  		"  CID.INSTRUMENT_MODE,date_format(CID.RECEIVED_DATE,'"+dateFormat+"'),CID.INSTRUMENT_AMOUNT,date_format(CID.INSTRUMENT_DATE,'"+dateFormat+"'),CID.INSTRUMENT_NO," +
			  		" CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" +
			  		"  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME," +
			  		" CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.REMARKS,CID.INSTRUMENT_ID,CID.TDS_AMOUNT,CID.RECIPT_NO,CID.RECEIPT_AMOUNT_NEW,CID.MAKER_REMARKS,CLD.REC_STATUS,CID.RECEIPT_PURPOSE,TRANSACTION_REF,CLD.LOAN_BRANCH, CID.DEPOSIT_BANK_ID,CID.DEPOSIT_BRANCH_ID,CID.DEPOSIT_MICR_CODE,CID.DEPOSIT_IFSC_CODE,CID.DEPOSIT_BANK_ACCOUNT,CLD.LOAN_REPAYMENT_TYPE,CID.RECEIVED_FROM,CID.CONTACT_NO,CID.MANUAL_AUTO_FLAG" +
			  		"  from cr_instrument_dtl CID,gcd_customer_m GCM ," +
			  		" cr_loan_dtl CLD, business_partner_view BPV,generic_master  GM" +
			  		" where CID.TXNID=CLD.LOAN_ID" +
			  		"  AND BPV.BP_ID=CID.BPID AND CID.BPTYPE=BPV.BP_TYPE" +
			        "  AND GM.VALUE=CID.BPTYPE and CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID" +
			        "  AND GM.GENERIC_KEY='BPTYPE' " +
			        "  and INSTRUMENT_TYPE='R'AND CID.REC_STATUS='P'" +
			  		" and ifnull(CID.TXN_TYPE,'')='LIM' AND CID.TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim())+"' "+
			        " AND CID.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' ");
		
			  logger.info("In getReceiptList  query..........."+bufInsSql.toString());
		       searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		       int size=searchlist.size();
		       for(int i=0;i<size;i++){

		  ArrayList data=(ArrayList)searchlist.get(i);

		  if(data.size()>0){
			 receiptvo = new ReceiptMakerVO();			
			 receiptvo.setLbxLoanNoHID ((CommonFunction.checkNull(data.get(0)).trim()));
			 receiptvo.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
			 receiptvo.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
			
			 receiptvo.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
			 receiptvo.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
			 
			 receiptvo.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
			 receiptvo.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
			 if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("Q")){ 	    		
				 receiptvo.setLbxreceiptMode("B");
	    	      	  } 		  
			  else if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("D")){ 	    		
				  receiptvo.setLbxreceiptMode("B");
	    	      	  } 
			  else if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("N")){ 	    		
				  receiptvo.setLbxreceiptMode("B");
	    	      	  } 
			  else if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("R")){ 	    		
				  receiptvo.setLbxreceiptMode("B");
	    	      	  } 
			  else if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("S")){ 	    		
				  receiptvo.setLbxreceiptMode("S");
	    	      	  } 
			 receiptvo.setReceiptMode((CommonFunction.checkNull(data.get(7)).trim()));
			 receiptvo.setReceiptDate((CommonFunction.checkNull(data.get(8)).trim()));
			 Number ReceiptAmount =0;
			 if(!CommonFunction.checkNull(data.get(9)).equals(""))
			{
				 ReceiptAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(data.get(9))).trim());
			}
			
			 receiptvo.setReceiptAmount(myFormatter.format(ReceiptAmount));			
			 receiptvo.setInstrumentDate((CommonFunction.checkNull(data.get(10)).trim()));
			 receiptvo.setInstrumentNumber((CommonFunction.checkNull(data.get(11)).trim()));
			 receiptvo.setBankAccount((CommonFunction.checkNull(data.get(12)).trim()));
			 receiptvo.setLbxBankID((CommonFunction.checkNull(data.get(13)).trim()));
			
			 receiptvo.setBank((CommonFunction.checkNull(data.get(14)).trim()));
			 receiptvo.setLbxBranchID((CommonFunction.checkNull(data.get(15)).trim()));
			
			 receiptvo.setBranch((CommonFunction.checkNull(data.get(16)).trim()));
			 receiptvo.setMicr((CommonFunction.checkNull(data.get(17)).trim()));
			 receiptvo.setIfsCode((CommonFunction.checkNull(data.get(18)).trim()));
			 receiptvo.setAuthorRemarks((CommonFunction.checkNull(data.get(19)).trim()));
			 receiptvo.setInstrumentID((CommonFunction.checkNull(data.get(20)).trim()));
			 
			 Number TdsAmount = 0;
			 if(!CommonFunction.checkNull(data.get(21)).equalsIgnoreCase(""))
	    	  {
				 TdsAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(data.get(21))).trim());
			 			  
	    	  }
			 receiptvo.setTdsAmount(myFormatter.format(TdsAmount));
			 receiptvo.setReceiptNo((CommonFunction.checkNull(data.get(22)).trim()));
			 receiptvo.setReceiptAmountNew((CommonFunction.checkNull(data.get(23)).trim()));
			 receiptvo.setRemarks((CommonFunction.checkNull(data.get(24)).trim()));
			 receiptvo.setLoanRecStatus((CommonFunction.checkNull(data.get(25)).trim()));
			 receiptvo.setPurpose((CommonFunction.checkNull(data.get(26)).trim()));
			 receiptvo.setTransactionRefNo((CommonFunction.checkNull(data.get(27)).trim()));
			 receiptvo.setLoanBranch((CommonFunction.checkNull(data.get(28)).trim()));
			 
			 receiptvo.setDepositBankID((CommonFunction.checkNull(data.get(29)).trim()));
			 receiptvo.setDepositBranchID((CommonFunction.checkNull(data.get(30)).trim()));
			
			 receiptvo.setDepositMicr((CommonFunction.checkNull(data.get(31)).trim()));
			 receiptvo.setDepositIfscCode((CommonFunction.checkNull(data.get(32)).trim()));
			
			 receiptvo.setDepositBankAccount((CommonFunction.checkNull(data.get(33)).trim()));
			 receiptvo.setLoanRepaymentType((CommonFunction.checkNull(data.get(34)).trim()));	
			 
			 receiptvo.setReceivedFrom((CommonFunction.checkNull(data.get(35)).trim()));
			 receiptvo.setContactNo((CommonFunction.checkNull(data.get(36)).trim()));	
			 
			 receiptvo.setStatusReceipt((CommonFunction.checkNull(data.get(37)).trim()));	
			 
			 savedReceipt.add(receiptvo);	
		   }
		  data.clear();
		  data=null;
		  receiptvo=null;

		  }
		  searchlist.clear();
		  searchlist=null;

		}catch(Exception e){
			e.printStackTrace();
				}
		finally
		{
			vo=null;
		}
		return  savedReceipt;	
		}
	 
	 public ArrayList <ReceiptMakerVO> receiptdatatoApprove(ReceiptMakerVO vo)
		{
			 logger.info("In receiptdatatoApprove() of ReceiptDAOImpl");
			
		  ArrayList<ReceiptMakerVO> receiptApproveList=new ArrayList<ReceiptMakerVO>();
		  try{
			  ArrayList searchlist=new ArrayList();
			  ReceiptMakerVO receiptvo=null;
		    StringBuffer bufInsSql =	new StringBuffer();
		    
		    

		    bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " +		  
			  		"   GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME , " +	
			  		"  CID.INSTRUMENT_MODE,date_format(CID.RECEIVED_DATE,'"+dateFormat+"'),CID.INSTRUMENT_AMOUNT,date_format(CID.INSTRUMENT_DATE,'"+dateFormat+"'),CID.INSTRUMENT_NO," +
			  		" CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" +
			  		"  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME," +
			  		" CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.REMARKS,CID.INSTRUMENT_ID,CID.TDS_AMOUNT,CID.RECIPT_NO,CID.MAKER_REMARKS,CLD.REC_STATUS,(select DESCRIPTION from generic_master where value=CID.RECEIPT_PURPOSE AND GENERIC_KEY='RECEIPT_PURPOSE' ) as RECEIPT_PURPOSE,CID.TRANSACTION_REF,CLD.LOAN_BRANCH, CID.DEPOSIT_BANK_ID,CID.DEPOSIT_BRANCH_ID,CID.DEPOSIT_MICR_CODE,CID.DEPOSIT_IFSC_CODE,CID.DEPOSIT_BANK_ACCOUNT,CID.RECEIVED_FROM,CID.CONTACT_NO,CID.MANUAL_AUTO_FLAG " +
			  		"  from cr_instrument_dtl CID,gcd_customer_m GCM ," +
			  		" cr_loan_dtl CLD, business_partner_view BPV,generic_master  GM" +
			  		" where CID.TXNID=CLD.LOAN_ID" +
			  		"  AND BPV.BP_ID=CID.BPID " +
			        "  AND GM.VALUE=CID.BPTYPE and CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID " +
			        "  AND GM.GENERIC_KEY='BPTYPE' " +
			        "  and INSTRUMENT_TYPE='R'AND CID.REC_STATUS='F'" +
			  		" and ifnull(CID.TXN_TYPE,'')='LIM' AND CID.TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim())+"' "+
			        " AND CID.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' ");
		
			  		
		   
			  logger.info("In receiptdatatoApprove. query..........."+bufInsSql.toString());
		       searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		       int size=searchlist.size();
		 for(int i=0;i<size;i++){
		
		  ArrayList data=(ArrayList)searchlist.get(i);

		  if(data.size()>0){
			 receiptvo = new ReceiptMakerVO();
			 receiptvo.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
			 receiptvo.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
			 receiptvo.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
			 receiptvo.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
			 receiptvo.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
			 receiptvo.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
			 receiptvo.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
			 receiptvo.setReceiptMode((CommonFunction.checkNull(data.get(7)).trim()));
			 receiptvo.setReceiptDate((CommonFunction.checkNull(data.get(8)).trim()));
			 Number ReceiptAmount=0;
			 if(!CommonFunction.checkNull(data.get(9)).equals(""))
			{
			 ReceiptAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(data.get(9))).trim());
			}
				
			 receiptvo.setReceiptAmount(myFormatter.format(ReceiptAmount));
			 receiptvo.setInstrumentDate((CommonFunction.checkNull(data.get(10)).trim()));
			 receiptvo.setInstrumentNumber((CommonFunction.checkNull(data.get(11)).trim()));
			 receiptvo.setBankAccount((CommonFunction.checkNull(data.get(12)).trim()));
			 receiptvo.setLbxBankID((CommonFunction.checkNull(data.get(13)).trim()));
			
			 receiptvo.setBank((CommonFunction.checkNull(data.get(14)).trim()));
			 receiptvo.setLbxBranchID((CommonFunction.checkNull(data.get(15)).trim()));
			
			 receiptvo.setBranch((CommonFunction.checkNull(data.get(16)).trim()));
			 receiptvo.setMicr((CommonFunction.checkNull(data.get(17)).trim()));
			 receiptvo.setIfsCode((CommonFunction.checkNull(data.get(18)).trim()));
			 receiptvo.setAuthorRemarks((CommonFunction.checkNull(data.get(19)).trim()));
			 receiptvo.setInstrumentID((CommonFunction.checkNull(data.get(20)).trim()));
			
			
			 Number TdsAmount = 0;
			 if(!CommonFunction.checkNull(data.get(21)).equalsIgnoreCase(""))
	    	  {
				 TdsAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(data.get(21))).trim());
				
				
	    	  }
			 receiptvo.setTdsAmount(myFormatter.format(TdsAmount));	
			 receiptvo.setReceiptNo((CommonFunction.checkNull(data.get(22)).trim()));
			 receiptvo.setRemarks((CommonFunction.checkNull(data.get(23)).trim()));

			 receiptvo.setLoanRecStatus((CommonFunction.checkNull(data.get(24)).trim()));
			 receiptvo.setPurpose((CommonFunction.checkNull(data.get(25)).trim()));
			 receiptvo.setTransactionRefNo((CommonFunction.checkNull(data.get(26)).trim()));
			 receiptvo.setLoanBranch((CommonFunction.checkNull(data.get(27)).trim()));
			 
			 receiptvo.setDepositBankID((CommonFunction.checkNull(data.get(28)).trim()));
			 receiptvo.setDepositBranchID((CommonFunction.checkNull(data.get(29)).trim()));
			
			 receiptvo.setDepositMicr((CommonFunction.checkNull(data.get(30)).trim()));
			 receiptvo.setDepositIfscCode((CommonFunction.checkNull(data.get(31)).trim()));
			 receiptvo.setDepositBankAccount((CommonFunction.checkNull(data.get(32)).trim()));
			 receiptvo.setReceivedFrom((CommonFunction.checkNull(data.get(33)).trim()));
			 receiptvo.setContactNo((CommonFunction.checkNull(data.get(34)).trim()));
			 receiptvo.setStatusReceipt((CommonFunction.checkNull(data.get(35)).trim()));
			
			 receiptApproveList.add(receiptvo);	
		     }
		  data.clear();
		  data=null;
		  receiptvo=null;

		    }
		  searchlist.clear();
		  searchlist=null;
		  bufInsSql=null;
		}catch(Exception e){
			e.printStackTrace();
				}
		
		finally{
			vo=null;
		}
		return  receiptApproveList;	
		}
		 public ArrayList<ReceiptMakerVO> getViewReceivable(int loanId,String bpType,int bpId,String uId,String amt,int instrumentId,String tdsAmount,String receiptamount) {
				

				ArrayList<ReceiptMakerVO> getReceivableList=new ArrayList<ReceiptMakerVO>();
				ReceiptMakerVO receiptVO=null;
			    ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				StringBuilder s1 = new StringBuilder();
				StringBuilder s2 = new StringBuilder();
				StringBuilder query = new StringBuilder();
				StringBuilder procval = new StringBuilder();

				try{
	
					ArrayList mainList=new ArrayList ();
					ArrayList subList =new ArrayList();					
					String totalAmount=CommonFunction.checkNull(amt);
					
					logger.info("In getViewReceivable Advice_Pay_Rec Procedure");

						
					    in.add("R");
						
						in.add(loanId);
						in.add(bpType);
						in.add( bpId);
						in.add( uId);
						in.add(totalAmount);
						in.add(instrumentId);
						in.add(tdsAmount);
						out.add(s1.toString());
					    out.add(s2.toString());
						outMessages=(ArrayList) ConnectionDAO.callSP("Advice_Pay_Rec",in,out);
						s1.append(CommonFunction.checkNull(outMessages.get(0)));
						s2.append(CommonFunction.checkNull(outMessages.get(1)));
						logger.info("s1: "+s1.toString());
						logger.info("s2: "+s2.toString());
						outMessages.clear();
						outMessages=null;
						in.clear();
						in=null;
						out.clear();
						out=null;
						procval=s2;
				
						query.append("SELECT DATE_FORMAT(A.ADVICE_DATE,'"+dateFormat+"'),A.CHARGE,A.ADVICE_AMOUNT,A.BALANCE_AMOUNT,"+
					      " IFNULL(A.PMNT_AMOUNT,0) PMNT_AMOUNT,A.AMOUNT_IN_PROCESS,A.TDS_AMOUNT ,A.TXNADVICE_ID, IFNULL(A.TDS_ALLOCATED_AMOUNT,0)TDS_ALLOCATED_AMOUNT"+
					      " FROM tmp_adv_payrec A LEFT OUTER JOIN cr_pmnt_dtl cpd  ON CPD.TXNADVICEID=A.TXNADVICE_ID AND CPD.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentId).trim())+"' "+
						  " WHERE  A.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' AND A.ADVICE_TYPE='R' "+
					      " and A.BP_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim())+"' and A.BP_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpId).trim())+"'" +
					      " and A.MAKER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(uId).trim())+"'"  +
					      //" Order By A.ADVICE_DATE, A.TXNADVICE_ID ASC; ");
						" Order By A.SEQ_NO ASC; ");

					
					
					logger.info("In getViewReceivable "+query.toString());	
					
					mainList=ConnectionDAO.sqlSelect(query.toString());
					query=null;
					int size=mainList.size();
					for(int i=0;i<size;i++)
					{
						subList= (ArrayList)mainList.get(i);
						if(subList.size()>0){
							receiptVO = new ReceiptMakerVO();
							receiptVO.setReceiptDate((CommonFunction.checkNull(subList.get(0)).trim()));
							receiptVO.setChargeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
							Number OriginalAmount=0;

							if(!CommonFunction.checkNull(subList.get(2)).equals(""))
							{
								OriginalAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(2))).trim());
							}
				 			
				 			receiptVO.setOriginalAmount(myFormatter.format(OriginalAmount));
			 		    	Number BalanceAmount =0;
			 		    	if(!CommonFunction.checkNull(subList.get(3)).equals(""))
							{
			 		    		BalanceAmount=CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(3))).trim());
							}
			 					
			 				receiptVO.setBalanceAmount(myFormatter.format(BalanceAmount));
			 				Number AllotedAmount=0;
			 				if(!CommonFunction.checkNull(subList.get(4)).equals(""))
							{
			 					AllotedAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(4))).trim());
							}
				 		
				 			receiptVO.setAllotedAmount(myFormatter.format(AllotedAmount));
				 			Number AmountInProcess = 0;
				 			if(!CommonFunction.checkNull(subList.get(5)).equals(""))
							{
				 				AmountInProcess = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(5))).trim());
							}
				 					
				 			receiptVO.setAmountInProcess(myFormatter.format(AmountInProcess));
				 			Number TdsadviseAmount = 0;
				 			if(!CommonFunction.checkNull(subList.get(6)).equals(""))
							{
				 				TdsadviseAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(6))).trim());
							}
				 			
				 			receiptVO.setTdsadviseAmount(myFormatter.format(TdsadviseAmount));	
				 			receiptVO.setTxnAdvicedID((CommonFunction.checkNull(subList.get(7)).trim()));
				 			Number TdsAllocatedAmount = 0;
				 			if(!CommonFunction.checkNull(subList.get(8)).equals(""))
							{
				 				TdsAllocatedAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(8))).trim());
							}
				 		
				 			receiptVO.setTdsAllocatedAmount(myFormatter.format(TdsAllocatedAmount));
				 			receiptVO.setProcVal((CommonFunction.checkNull(procval.toString()).trim()));
				 			
							getReceivableList.add(receiptVO);
						}
						subList.clear();
						subList=null;
						receiptVO=null;
					}
					mainList.clear();
					mainList=null;
						}catch(Exception e){
							e.printStackTrace();
						}
						finally
						{

							s1 = null;
							s2 = null;
							query = null;
							procval = null;
							bpType=null;
							uId=null;
							amt=null;
							tdsAmount=null;
							receiptamount=null;
							
						}

					
						return getReceivableList;
					}
		 public ArrayList<PaymentMakerForCMVO> onViewReceivable(PaymentMakerForCMVO paymentMakerForCMVO,int loanId,String bpType,int bpId){

		 		ArrayList<PaymentMakerForCMVO> viewReceivabList=new ArrayList<PaymentMakerForCMVO>();
		 		StringBuilder query = new StringBuilder();
		
		 		try{
		 			PaymentMakerForCMVO paymentVO=null;
		 			ArrayList mainList=new ArrayList ();
		 			ArrayList subList =new ArrayList();
		 		
		 					
		 			logger.info(" In onViewReceivable....");	
		 				
		 		
		 			query.append("SELECT DATE_FORMAT(A.ADVICE_DATE,'"+dateFormat+"'),(Select CHARGE_DESC From com_charge_code_m "+
			 		" Where CHARGE_CODE=a.CHARGE_CODE_ID) CHARGE,A.ORG_ADVICE_AMOUNT,A.WAIVE_OFF_AMOUNT,A.TDS_AMOUNT," +
			 		" A.TXN_ADJUSTED_AMOUNT,A.AMOUNT_IN_PROCESS,"+
			       " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,A.TXNADVICE_ID,A.ADVICE_AMOUNT,G.DESCRIPTION  "+
			 		"  FROM cr_txnadvice_dtl A inner join generic_master G on A.BP_TYPE=G.VALUE  AND G.GENERIC_KEY='BPTYPE' WHERE A.REC_STATUS IN ('A', 'F') and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0 AND  "+
			       "   A.ADVICE_TYPE='R' AND LOAN_ID='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+ "' " +
			       	"AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "'" +
			       	" and A.BP_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpId).trim())+"' " +
			       	" ORDER BY A.ADVICE_DATE ASC");

		 			
		 			logger.info("In onViewReceivable "+query.toString());	
		 			
		 			mainList=ConnectionDAO.sqlSelect(query.toString());
		 			query=null;
		 			int size=mainList.size();
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
			 					Number orgAmt = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(2)).trim()));
			 					paymentVO.setOriginalAmount(myFormatter.format(orgAmt));
			 				}
			 				
			 				if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
			 					paymentVO.setWaiveOffAmount("0");
			 				else
			 				{
			 					Number waivedOffAmt = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(3)).trim()));
			 					paymentVO.setWaiveOffAmount(myFormatter.format(waivedOffAmt));
			 				}
			 				
			 				if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
			 					paymentVO.setTdsadviseAmount("0");
			 				else
			 				{
			 					Number tdsAdviceAmt = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(4)).trim()));
			 					paymentVO.setTdsadviseAmount(myFormatter.format(tdsAdviceAmt));
			 				}
			 				
			 				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
			 					paymentVO.setAdjustedAmount("0");
			 				else
			 				{
			 					Number adjustedAmt = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(5)).trim()));
			 					paymentVO.setAdjustedAmount(myFormatter.format(adjustedAmt));
			 				}
			 				
			 				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
			 					paymentVO.setAmountInProcess("0");
			 				else
			 				{
			 					Number amtInProc = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(6)).trim()));
			 					paymentVO.setAmountInProcess(myFormatter.format(amtInProc));
			 				}
			 				
			 				if((CommonFunction.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
			 					paymentVO.setBalanceAmount("0");
			 				else
			 				{
			 					Number balAmt = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(7)).trim()));
			 					paymentVO.setBalanceAmount(myFormatter.format(balAmt));
			 				}
			 		    	
			 				paymentVO.setTxnAdvicedID((CommonFunction.checkNull(subList.get(8)).trim()));
			 				if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase(""))
			 					paymentVO.setAdviceAmount("0");
			 				else
			 				{
			 					Number adviceAmt = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(9)).trim()));
			 					paymentVO.setAdviceAmount(myFormatter.format(adviceAmt));
			 				}
			 				paymentVO.setBusinessPartnerType((CommonFunction.checkNull(subList.get(10)).trim()));	
			 				viewReceivabList.add(paymentVO);
		 				}
		 				subList.clear();
		 				subList=null;
		 				paymentVO=null;
		 			}
		 			mainList.clear();
		 			mainList=null;
		 				}catch(Exception e){
		 					e.printStackTrace();
		 				}
		 				finally
		 				{
		 					query = null;
		 					paymentMakerForCMVO=null;
		 					bpType=null;
		 				}


		 				return viewReceivabList;
		 			}
		 	
		 public boolean saveViewReceivable(ReceiptMakerVO receiptVO) {
				
			 boolean status=false;
			 StringBuilder bptype = new StringBuilder();
			 StringBuilder loanId = new StringBuilder();
			 StringBuilder deleteCount = new StringBuilder();
			 StringBuilder data = new StringBuilder();
			 
			try{
				ArrayList queryList=new ArrayList ();
				//ArrayList subList =new ArrayList();
				//ArrayList subList1 =new ArrayList();
				//ArrayList list =new ArrayList();
				//ArrayList dataList =new ArrayList();
				PrepStmtObject insertPrepStmtObject=null;
				StringBuffer bufInsSql = null;

					String allotedAmount[]=receiptVO.getAllocatedArry();
					String adviseId[]=receiptVO.getTxnAdvicedIDArry();
	                String tdsAllotedAmount[]=receiptVO.getTdsAllocatedArry();	  
			
	 
			// Prashant start here 		  
				  String deletePmntDtlQuery="SELECT COUNT(1) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+ "'";
				  logger.info("In saveViewReceivable..deletePmntDtlQuery. "+deletePmntDtlQuery);
				  String deletePmntDtlCount=ConnectionDAO.singleReturn(deletePmntDtlQuery);
				  logger.info("In saveViewReceivable... deletePmntDtlCount"+deletePmntDtlCount);
				 
				  if(!(deletePmntDtlCount).equalsIgnoreCase("0")){
					  
	                insertPrepStmtObject =  new PrepStmtObject();
	                bufInsSql = new StringBuffer();
	                bufInsSql.append(" DELETE FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+ "'");
	                logger.info("### bufInsSql delete PMNT Query #### "+bufInsSql.toString());
	                insertPrepStmtObject.setSql(bufInsSql.toString());
	                bufInsSql=null;
	                queryList.add(insertPrepStmtObject);  
	                insertPrepStmtObject=null;
	                
	            }
			
				 status=true;
	
	
	         int length=adviseId.length;  
			 for(int i=0;i<length;i++)
			 {
			   
			   insertPrepStmtObject=null;
	           bufInsSql=null;
	           insertPrepStmtObject =  new PrepStmtObject();
	           bufInsSql = new StringBuffer();
	           String alloc=CommonFunction.checkNull(allotedAmount[i]);

	           if(alloc.equalsIgnoreCase("")){
	        	   alloc="0"; 
	           }
	            double allocatedAmnt=0;
	            allocatedAmnt=CommonFunction.decimalNumberConvert(alloc).doubleValue();
	            
	            
	              if(allocatedAmnt>0)
	              {
                        status=false; 
	                      
				  bufInsSql.append("insert into cr_pmnt_dtl( PMNT_DATE,PMNT_AMOUNT,INSTRUMENT_ID,TXNADVICEID,REC_STATUS,MAKER_ID,TDS_AMOUNT ,MAKER_DATE)");
				  bufInsSql.append(" values ( ");
				  bufInsSql.append(" (select RECEIVED_DATE from cr_instrument_dtl where INSTRUMENT_ID=? limit 1)," );
			      bufInsSql.append(" ?," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(" 'P'," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))" );
				
				  if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
					if((CommonFunction.checkNull(allocatedAmnt)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addString("0.00");
					else 
						insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocatedAmnt).trim())).toString());
					if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
					if((CommonFunction.checkNull(adviseId[i])).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(adviseId[i]).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getMakerId()).trim()));
					
					if((CommonFunction.checkNull(tdsAllotedAmount[i])).equalsIgnoreCase(""))
						insertPrepStmtObject.addString("0.00");
					else
						insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(tdsAllotedAmount[i]).trim())).toString());
				
					if((CommonFunction.checkNull(receiptVO.getBusinessDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getBusinessDate()).trim()));
					
				  insertPrepStmtObject.setSql(bufInsSql.toString());
				  bufInsSql=null;
				  queryList.add(insertPrepStmtObject);
				  logger.info("In insert of  cr_pmnt_dtl"+insertPrepStmtObject.printQuery());
				  insertPrepStmtObject=null;
				  
				/*   insertPrepStmtObject =  new PrepStmtObject();
		           bufInsSql = new StringBuffer();

         
		    bufInsSql.append(" Update tmp_adv_payrec Set PMNT_AMOUNT=?,TDS_ALLOCATED_AMOUNT=? WHERE  LOAN_ID=? And ADVICE_TYPE='R' And TXNADVICE_ID=? And BP_TYPE=?  And MAKER_ID=?");
				    if((CommonFunction.checkNull(allocatedAmnt)).trim().equalsIgnoreCase(""))
				    	insertPrepStmtObject.addString("0");
					else
						insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocatedAmnt).trim())).toString());
				    
				    if((CommonFunction.checkNull(tdsAllotedAmount[i])).equalsIgnoreCase(""))
						insertPrepStmtObject.addString("0");
					else
						insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(tdsAllotedAmount[i]).trim())).toString());
				
				    if((CommonFunction.checkNull(loanId.toString())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(loanId.toString()).trim()));
				    
				    if((CommonFunction.checkNull(adviseId[i])).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(adviseId[i]).trim()));
				    
				    if((CommonFunction.checkNull(bptype.toString())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(bptype.toString()).trim()));
				    
				    
				    if((CommonFunction.checkNull(receiptVO.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getMakerId()).trim()));
				
				      insertPrepStmtObject.setSql(bufInsSql.toString());
					  queryList.add(insertPrepStmtObject);
					
					  logger.info("In Update of  tmp_adv_payrec "+insertPrepStmtObject.printQuery());
				*/
				}
	              alloc=null;
			}
			 if(queryList.size()>0){
				 status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
				 queryList.clear();
				 queryList=null;
			 }
			
			 adviseId=null;
			 allotedAmount=null;
			 tdsAllotedAmount=null;
			 deletePmntDtlQuery=null;
			 deletePmntDtlCount=null;
			
			} 
				catch(Exception e){
				e.printStackTrace();
			}
				finally
				{
					bptype = null;
					loanId   = null;
					deleteCount   = null;
					data = null;
					receiptVO=null;
					
					
				}

			return status;

	}
	
		 public  String existReceiptData(Object ob){
		  	  String result=null;
		  	ReceiptMakerVO vo =  (ReceiptMakerVO)ob;
		  	StringBuilder existCount = new StringBuilder();
		  	   try{
		  		   existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim())+"'" +
		  	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim())+"'and cid.ISSUEING_BRANCH_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchID()).trim())+"' " +
		  	  		" and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim())+"'" +
		  	  		" and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim())+"' and INSTRUMENT_TYPE='R' AND REC_STATUS<>'X'");
		  		 logger.info("IN existReceiptData  ### "+existCount.toString());
		  	 result=ConnectionDAO.singleReturn(existCount.toString());
		  		
		    }
		  	   catch(Exception e){
						e.printStackTrace();
					}
		  	   finally
		  	   {
		  		 existCount = null;
		  	   }

					return result;
				}
		 public  String existReceiptForNR(Object ob){
		  	  String result=null;
		  	ReceiptMakerVO vo =  (ReceiptMakerVO)ob;
		  	StringBuilder existCount = new StringBuilder();
		  	   try{
		  		   existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim())+"'" +
		  	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim())+"' " +
		  	  		" and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim())+"'" +
		  	  		" and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim())+"' and INSTRUMENT_TYPE='R' AND REC_STATUS<>'X'");
		  		 logger.info("IN existReceiptForNR Query--------- ### "+existCount.toString());
		  	 result=ConnectionDAO.singleReturn(existCount.toString());
		  		
		    }
		  	   catch(Exception e){
						e.printStackTrace();
					}
		  	   	finally
		  	   	{
		  	   		existCount = null;
		  	   		ob=null;
		  	   		vo=null;
		  	   		existCount=null;
		  	   	}

					return result;
				}
		
		 public boolean saveReceiptData(Object ob) 
		 {
			 
			 
				ReceiptMakerVO vo =  (ReceiptMakerVO)ob;
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
				boolean status=false;
				
				    String allocChargeCodeArray[]=vo.getAllocChargeCodeArray();
				    String allocAdviceAmountArray[]=vo.getAllocAdviceAmountArray();
				    String allocAdjustedAmountArray[]=vo.getAllocAdjustedAmountArray();
				    String allocAmountInProcessArray[]=vo.getAllocAmountInProcessArray();
				    String allocBalAmountArray[]=vo.getAllocBalAmountArray();
				    String allocAmountStringArray[] = vo.getAllocAmountStringArray();
				    String allocTdsAllocatedAmountStringArray[] = vo.getAllocTdsAllocatedAmountStringArray();
				    
				    String lbxAllocChargeStringArray[]=vo.getLbxAllocChargeStringArray();
				    String allocOtherBalAmountStringArray[] =vo.getAllocOtherBalAmountStringArray();
				    String allocOtherAmountStringArray[]=vo.getAllocOtherAmountStringArray();
				    String allocTdsOtherAllAmtStringArray[]=vo.getAllocTdsOtherAllAmtStringArray();
				    
				    ArrayList qryList = new ArrayList();
				    ArrayList deleteList = new ArrayList();
				    boolean delete=false;
				
					try
					{
						StringBuilder deleteQuery1=new StringBuilder();
						deleteQuery1.append ("delete from cr_instrument_temp where MAKER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim())+"' ");
						deleteList.add(deleteQuery1.toString());
						deleteQuery1=null;
						StringBuilder deleteQuery2=new StringBuilder();
						deleteQuery2.append ("delete from cr_gross_allocation_temp where MAKER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim())+"' ");
						deleteList.add(deleteQuery2.toString());
						delete=ConnectionDAO.sqlInsUpdDelete(deleteList);
						if(delete)
						{
							String ct1=ConnectionDAO.singleReturn("select count(1) from cr_instrument_temp where MAKER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim())+"'");
							String ct2=ConnectionDAO.singleReturn("select count(1) from cr_gross_allocation_temp where MAKER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim())+"' ");
							if(!CommonFunction.checkNull(ct1).trim().equalsIgnoreCase("0") || !CommonFunction.checkNull(ct2).trim().equalsIgnoreCase("0"))
								delete=false;
						}
						if(delete)
						{
					    StringBuffer bufInsSql =	new StringBuffer();
						bufInsSql.append("insert into cr_instrument_temp (TXNID,TXN_TYPE,BPTYPE,BPID,INSTRUMENT_MODE,RECEIVED_DATE," +
								" INSTRUMENT_AMOUNT,RECEIPT_AMOUNT_NEW,INSTRUMENT_DATE,INSTRUMENT_NO,ISSUEING_BANK_ACCOUNT,ISSUEING_BANK_ID,ISSUEING_BRANCH_ID," +
								" ISSUING_MICR_CODE,ISSUING_IFSC_CODE,MAKER_REMARKS,TDS_AMOUNT,RECIPT_NO,REC_STATUS,INSTRUMENT_TYPE,PDC_FLAG,DEFAULT_BRANCH,RECEIPT_PURPOSE,TRANSACTION_REF,MAKER_ID,MAKER_DATE, " +
								" DEPOSIT_BANK_ID,DEPOSIT_BRANCH_ID,DEPOSIT_MICR_CODE,DEPOSIT_IFSC_CODE,DEPOSIT_BANK_ACCOUNT,RECEIVED_FROM,CONTACT_NO,MANUAL_AUTO_FLAG ) ");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?," );
						bufInsSql.append(" 'LIM'," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" 'P'," );
						bufInsSql.append(" 'R'," );
						bufInsSql.append(" 'N'," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
					    bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );
					    bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?, " );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ? ) " );
						
						
						if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
						if((CommonFunction.checkNull(vo.getLbxBPType())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxBPType()).trim()));
						if((CommonFunction.checkNull(vo.getLbxBPNID())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
						if((CommonFunction.checkNull(vo.getReceiptMode())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getReceiptMode()).trim()));
						if((CommonFunction.checkNull(vo.getReceiptDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getReceiptDate()).trim()));
						if((CommonFunction.checkNull(vo.getReceiptAmount())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0");
						else
							insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
						if((CommonFunction.checkNull(vo.getReceiptAmountNew())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0");
						else
							insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(vo.getReceiptAmountNew()).trim())).toString());
						if((CommonFunction.checkNull(vo.getInstrumentDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else 
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstrumentDate()).trim()));
						if((CommonFunction.checkNull(vo.getInstrumentNumber())).trim().equalsIgnoreCase(""))
						    insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
						if((CommonFunction.checkNull(vo.getBankAccount())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getBankAccount()).trim()));
						if((CommonFunction.checkNull(vo.getLbxBankID())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxBankID()).trim()));
						if((CommonFunction.checkNull(vo.getLbxBranchID())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxBranchID()).trim()));
						if((CommonFunction.checkNull(vo.getMicr())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMicr()).trim()));
						
						if((CommonFunction.checkNull(vo.getIfsCode())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getIfsCode()).trim()));
						
						if((CommonFunction.checkNull(vo.getRemarks())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRemarks()).trim()));
						
						if((CommonFunction.checkNull(vo.getTdsAmount())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0.00");
						else
							insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(vo.getTdsAmount()).trim())).toString());
						
						if((CommonFunction.checkNull(vo.getReceiptNo())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getReceiptNo()).trim()));
						
						if((CommonFunction.checkNull(vo.getDefaultBranch())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDefaultBranch()).trim()));
						if((CommonFunction.checkNull(vo.getPurpose())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPurpose()).trim()));
						if((CommonFunction.checkNull(vo.getTransactionRefNo())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTransactionRefNo()).trim()));
						if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
						if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getBusinessDate()).trim()));
						
						
						if((CommonFunction.checkNull(vo.getDepositBankID())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDepositBankID()).trim()));
						
						if((CommonFunction.checkNull(vo.getDepositBranchID())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDepositBranchID()).trim()));
						
						if((CommonFunction.checkNull(vo.getDepositMicr())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDepositMicr()).trim()));
						
						if((CommonFunction.checkNull(vo.getDepositIfscCode())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDepositIfscCode()).trim()));
						
						if((CommonFunction.checkNull(vo.getDepositBankAccount())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDepositBankAccount()).trim()));
					
						if((CommonFunction.checkNull(vo.getReceivedFrom())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getReceivedFrom()).trim()));
						
						if((CommonFunction.checkNull(vo.getContactNo())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getContactNo()).trim()));
						if((CommonFunction.checkNull(vo.getStatusReceipt())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStatusReceipt()).trim()));
						insertPrepStmtObject.setSql(bufInsSql.toString());
						bufInsSql=null;
						//logger.info("IN saveReceiptData() insert cr_instrument_temp query1 ### "+insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
						insertPrepStmtObject=null;
						int length=allocChargeCodeArray.length;
						if(length>0)	
						{
						    for(int i=0;i<length;i++)
						    {
							
								    bufInsSql =	new StringBuffer();
								    insertPrepStmtObject = new PrepStmtObject();
								    bufInsSql.append("insert into cr_gross_allocation_temp (INSTRUMENT_ID,SEQ_NO,TXNTYPE,TXNID,CHARGE_TYPE,CHARGE_CODE,ADVICE_AMOUNT,ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,BALANNCE_AMOUNT,ALLOCATED_AMOUNT,ALLOCATED_TDS,OTHER_CHARGE_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE ) ");
									bufInsSql.append(" values ( ");
									bufInsSql.append(" ?," );//INSTRUMENT_ID
									bufInsSql.append(" ?," ); //SEQ_NO
									bufInsSql.append(" 'LIM'," ); //TXNTYPE
									bufInsSql.append(" ?," ); //TXNID
									bufInsSql.append(" ?," ); //CHARGE_TYPE
									bufInsSql.append(" ?," ); //CHARGE_CODE
									bufInsSql.append(" ?," ); //ADVICE_AMOUNT
									bufInsSql.append(" ?," ); //ADJUSTED_AMOUNT
									bufInsSql.append(" ?," ); //AMOUNT_IN_PROCESS
									bufInsSql.append(" ?," ); //BALANNCE_AMOUNT
									bufInsSql.append(" ?," ); //ALLOCATED_AMOUNT
									bufInsSql.append(" ?," ); //ALLOCATED_TDS
									bufInsSql.append(" ?," );//OTHER_CHARGE_FLAG
									bufInsSql.append(" ?," ); //REC_STATUS
									bufInsSql.append(" ?," ); //MAKER_ID
					
									bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " ); //MAKER_DATE
								    bufInsSql.append(" ?," ); //AUTHOR_ID
								
								    bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ) " ); //AUTHOR_DATE
									
									if((CommonFunction.checkNull(vo.getInstrumentID())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstrumentID()).trim()));
									
									insertPrepStmtObject.addInt(i+1);
									if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
									
									insertPrepStmtObject.addString("R");
									
									
									if(CommonFunction.checkNull(allocChargeCodeArray[i]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(allocChargeCodeArray[i]).trim());
								
									if(CommonFunction.checkNull(allocAdviceAmountArray[i]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addString("0.00");
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(allocAdviceAmountArray[i]).trim());
								
									
									if(CommonFunction.checkNull(allocAdjustedAmountArray[i]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addString("0.00");
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(allocAdjustedAmountArray[i]).trim());
								
									if(CommonFunction.checkNull(allocAmountInProcessArray[i]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addString("0.00");
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(allocAmountInProcessArray[i]).trim());
								
									if(CommonFunction.checkNull(allocBalAmountArray[i]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addString("0.00");
									else
										insertPrepStmtObject.addString(CommonFunction.checkNull(allocBalAmountArray[i]).trim());
								
									if(CommonFunction.checkNull(allocAmountStringArray[i]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addString("0.00");
									else
										insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocAmountStringArray[i]).trim())).toString());
							
									if(CommonFunction.checkNull(allocTdsAllocatedAmountStringArray[i]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addString("0.00");
									else
										insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocTdsAllocatedAmountStringArray[i]).trim())).toString());
									
									insertPrepStmtObject.addString("N");  
							
									if((CommonFunction.checkNull(vo.getRecStatus()).trim().equalsIgnoreCase("")))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRecStatus()).trim()));
									
											
									if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
									
									if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getBusinessDate()).trim()));
									
									if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
									
									if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
										insertPrepStmtObject.addNull();
									else
										insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getBusinessDate()).trim()));
									
									insertPrepStmtObject.setSql(bufInsSql.toString());
									//logger.info("IN saveAllocationReceipt() insert cr_instrument_dtl query1 ### "+insertPrepStmtObject.printQuery());
									qryList.add(insertPrepStmtObject);
							  
								
							    }
							}
							if(lbxAllocChargeStringArray.length>0)
							{
								//logger.info("First Other Charge Code: "+lbxAllocChargeStringArray[0]);
								
								int k=allocChargeCodeArray.length;
								
							     for(int j=0;j<lbxAllocChargeStringArray.length;j++)
							     {	
							    	 if(!CommonFunction.checkNull(lbxAllocChargeStringArray[j]).equalsIgnoreCase(""))
										{
									       StringBuffer bufInsSql1 =	new StringBuffer();
									       PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
									    bufInsSql1.append("insert into cr_gross_allocation_temp (INSTRUMENT_ID,SEQ_NO,TXNTYPE,TXNID,CHARGE_TYPE,CHARGE_CODE,ADVICE_AMOUNT,ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,BALANNCE_AMOUNT,ALLOCATED_AMOUNT,ALLOCATED_TDS,OTHER_CHARGE_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE ) ");
										bufInsSql1.append(" values ( ");
										bufInsSql1.append(" ?," );//INSTRUMENT_ID
										bufInsSql1.append(" ?," ); //SEQ_NO
										bufInsSql1.append(" 'LIM'," ); //TXNTYPE
										bufInsSql1.append(" ?," ); //TXNID
										bufInsSql1.append(" ?," ); //CHARGE_TYPE
										bufInsSql1.append(" ?," ); //CHARGE_CODE
										bufInsSql1.append(" ?," ); //ADVICE_AMOUNT
										bufInsSql1.append(" ?," ); //ADJUSTED_AMOUNT
										bufInsSql1.append(" ?," ); //AMOUNT_IN_PROCESS
										bufInsSql1.append(" ?," ); //BALANNCE_AMOUNT
										bufInsSql1.append(" ?," ); //ALLOCATED_AMOUNT
										bufInsSql1.append(" ?," ); //ALLOCATED_TDS
										bufInsSql1.append(" ?," );//OTHER_CHARGE_FLAG
										bufInsSql1.append(" ?," ); //REC_STATUS
										bufInsSql1.append(" ?," ); //MAKER_ID
						
										bufInsSql1.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " ); //MAKER_DATE
									    bufInsSql1.append(" ?," ); //AUTHOR_ID
									
									    bufInsSql1.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ) " ); //AUTHOR_DATE
										
										if((CommonFunction.checkNull(vo.getInstrumentID())).trim().equalsIgnoreCase(""))
											insertPrepStmtObject1.addNull();
										else
											insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getInstrumentID()).trim()));
										
									
										insertPrepStmtObject1.addInt(k+j+1);
											
										if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
											insertPrepStmtObject1.addNull();
										else
											insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
										
										insertPrepStmtObject1.addString("R");  
										
										if(lbxAllocChargeStringArray.length>0 && j<lbxAllocChargeStringArray.length)
										{
											if(CommonFunction.checkNull(lbxAllocChargeStringArray[j]).trim().equalsIgnoreCase(""))
												insertPrepStmtObject1.addNull();
											else
												insertPrepStmtObject1.addString(CommonFunction.checkNull(lbxAllocChargeStringArray[j]).trim().toString());
										}
										else
											insertPrepStmtObject1.addNull();
										
										insertPrepStmtObject1.addString("0.00");
										insertPrepStmtObject1.addString("0.00");
										insertPrepStmtObject1.addString("0.00");
										
										
											
											if(allocOtherBalAmountStringArray.length>0 && j<allocOtherBalAmountStringArray.length)
											{
												if(CommonFunction.checkNull(allocOtherBalAmountStringArray[j]).trim().equalsIgnoreCase(""))
													insertPrepStmtObject1.addString("0.00");
												else
													insertPrepStmtObject1.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocOtherBalAmountStringArray[j]).trim())).toString());
											}
											else
												insertPrepStmtObject1.addString("0.00");
																	
										
											if(allocOtherAmountStringArray.length>0 && j<allocOtherAmountStringArray.length)
											{
												if(CommonFunction.checkNull(allocOtherAmountStringArray[j]).trim().equalsIgnoreCase(""))
													insertPrepStmtObject1.addString("0.00");
												else
													insertPrepStmtObject1.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocOtherAmountStringArray[j]).trim())).toString());
											}
											else
												insertPrepStmtObject1.addString("0.00");
											
											if(allocTdsOtherAllAmtStringArray.length>0 && j<allocTdsOtherAllAmtStringArray.length)
											{
												if(CommonFunction.checkNull(allocTdsOtherAllAmtStringArray[j]).trim().equalsIgnoreCase(""))
													insertPrepStmtObject1.addString("0.00");
												else
													insertPrepStmtObject1.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocTdsOtherAllAmtStringArray[j]).trim())).toString());
											}
											else
												insertPrepStmtObject1.addString("0.00");
										
											insertPrepStmtObject1.addString("Y");  
								
											if((CommonFunction.checkNull(vo.getRecStatus()).trim().equalsIgnoreCase("")))
												insertPrepStmtObject1.addNull();
											else
												insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getRecStatus()).trim()));
										
												
										if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
											insertPrepStmtObject1.addNull();
										else
											insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
										
										if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
											insertPrepStmtObject1.addNull();
										else
											insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getBusinessDate()).trim()));
										
										if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
											insertPrepStmtObject1.addNull();
										else
											insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
										
										if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
											insertPrepStmtObject1.addNull();
										else
											insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getBusinessDate()).trim()));
									
										insertPrepStmtObject1.setSql(bufInsSql1.toString());
										//logger.info("IN saveAllocationReceipt() insert cr_instrument_dtl query1 ### "+insertPrepStmtObject1.printQuery());
										qryList.add(insertPrepStmtObject1);
										insertPrepStmtObject1=null;
										bufInsSql1=null;
							        }
								 
								 }
							  
						    }
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					qryList.clear();
					qryList=null;
					bufInsSql=null;
					insertPrepStmtObject=null;
						}//end of if(delete)
				 
				}
					catch(Exception e){
						status=false;
						e.printStackTrace();
					}
					finally{
						
						ob=null;
						vo=null;
						insertPrepStmtObject=null;
						 allocChargeCodeArray=null;			   
						 allocAdviceAmountArray	=null;			    
						 allocAdjustedAmountArray=null;				    
						 allocAmountInProcessArray=null;
						 allocBalAmountArray=null;
						 allocAmountStringArray =null;
						 allocTdsAllocatedAmountStringArray=null;
						 lbxAllocChargeStringArray=null;
						 allocOtherBalAmountStringArray=null;
						 allocOtherAmountStringArray=null;
						 allocTdsOtherAllAmtStringArray=null;
						 deleteList.clear();
						 deleteList=null;
						 
					}
					
					return status;
				}
			
		
		 public String saveForwardUpdateOnReceipt(ReceiptMakerVO receiptVO){
				
			
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();	
				 StringBuffer bufInsSql =	new StringBuffer();
				 StringBuilder s11 = new StringBuilder();
				boolean status=false;
				String procval=null;
				logger.info("In saveForwardUpdateOnReceipt(ReceiptMakerVO receiptVO)");
				 ArrayList queryList=new ArrayList();
				try{
			
					  String chargeFlagQuery=ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");
	            	  if(CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y"))
	            	  {
				            		ArrayList<Object> in1 =new ArrayList<Object>();
				  					ArrayList<Object> out1 =new ArrayList<Object>();
				  					ArrayList outMessages1 = new ArrayList();
				  					
				  					StringBuilder s22 = new StringBuilder();
				  					
				  					in1.add(((CommonFunction.checkNull(receiptVO.getInstrumentID().trim()))));
				  					in1.add("F");
				  					in1.add("");
				  					in1.add(CommonFunction.checkNull(receiptVO.getMakerId()).trim());
				  					String date=CommonFunction.changeFormat(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
				  			  	    if(date != null)
				  			  	    in1.add(date);
				  				
				  					out1.add(s11.toString());
				  				    out1.add(s22.toString());
				  					
				  				     outMessages1=(ArrayList) ConnectionDAO.callSP("GROSS_RECEIPT_ALLOCATION",in1,out1);
				  				     s11.append(CommonFunction.checkNull(outMessages1.get(0)));
				  					 s22.append(CommonFunction.checkNull(outMessages1.get(1)));
				  					 logger.info("GROSS_RECEIPT_ALLOCATION s11: "+s11.toString());
				  					 logger.info("GROSS_RECEIPT_ALLOCATION s22: "+s22.toString());
				  					 procval=CommonFunction.checkNull(s22.toString());
				  					if(CommonFunction.checkNull(s11).equalsIgnoreCase("S"))
				  					{
										procval="queryexecuted";
									
				  					PrepStmtObject updatePrepStmtObject1 = new PrepStmtObject();	
					 			  	StringBuffer bufInsSql1 =	new StringBuffer();
					 			  	StringBuilder inventoryFlagInMst = new StringBuilder();
					 			  	StringBuilder existInStationary = new StringBuilder();
					 			    String inventoryFlag=null;
					 			    String checkAllBranch=null;
					 			    String checkUserAvailable=null;
					 			    StringBuilder allBranch = new StringBuilder();
					 			  	StringBuilder checkUser = new StringBuilder();
									String resultStationary=null;
									boolean updateStatus=false;
									String IssuingUser=receiptVO.getMakerId();
										
									 
					 			  	inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='INVENTORY_FLAG'");
							  		logger.info("IN checkReciptStatus inventoryFlag ### "+inventoryFlagInMst.toString());
							  		inventoryFlag=ConnectionDAO.singleReturn(inventoryFlagInMst.toString());
						  		
						  	if(inventoryFlag.equalsIgnoreCase("Y") && !CommonFunction.checkNull(receiptVO.getReceiptNo()).equalsIgnoreCase(""))
						  	    {
							  		allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"' AND STATUS='A'");
							  		logger.info("IN checkAllBranch ### "+allBranch.toString());
							  		checkAllBranch=ConnectionDAO.singleReturn(allBranch.toString());
						  		
						  	if(!(checkAllBranch.equalsIgnoreCase("0")))
						  		{
							  		
							  		if(CommonFunction.checkNull(receiptVO.getRemarks()).equalsIgnoreCase(""))
							  		   {
							  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='USED BY IMD MAKER',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE=STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
							  		   }
							  		   else
							  		   {
							  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='"+receiptVO.getRemarks()+"',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE=STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
							  		   }
							  		
							  	    updatePrepStmtObject1.setSql(bufInsSql1.toString());
							  		queryList.add(updatePrepStmtObject1);
							  		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
						  	}else{
							  		checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"' AND IFNULL(ISSUING_USER,'')<>''");
							  		logger.info("IN checkInventory inventoryFlag ### "+checkUser.toString());
							  		checkUserAvailable=ConnectionDAO.singleReturn(checkUser.toString());	
						  	if(!(checkUserAvailable.equalsIgnoreCase("0")))
								{
						  			existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'  AND IFNULL(ISSUING_USER,'"+IssuingUser+"')='"+IssuingUser+"' ");
							  		logger.info("IN checkUserAvailability  in existInStationary ### "+existInStationary.toString());
							  		resultStationary=ConnectionDAO.singleReturn(existInStationary.toString());
						  	}else{
						    	 	existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'  AND ISSUING_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getDefaultBranch()).trim())+"'");
							  		logger.info("IN checkReciptStatus  in existInStationary ### "+existInStationary.toString());
							  		resultStationary=ConnectionDAO.singleReturn(existInStationary.toString());
						         }
					  	   if(!(resultStationary.equalsIgnoreCase("0")))
							  	{
						  		 if(CommonFunction.checkNull(receiptVO.getRemarks()).equalsIgnoreCase(""))
						  		   {
						  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='USED BY IMD MAKER',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE=STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
						  		   }
						  		   else
						  		   {
						  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='"+receiptVO.getRemarks()+"',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE=STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
						  		   }
							  		logger.info("IN updateStationary  ### "+bufInsSql1.toString() + receiptVO.getReceiptNo());
							  	    updatePrepStmtObject1.setSql(bufInsSql1.toString());
							  		queryList.add(updatePrepStmtObject1);
							  		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);				  		 
							}else{
							     	status=false;
							  	}
									
					  	         }   
						  	    }
						   else{
						  		 	status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
						  	 	}
				            
							  		 updatePrepStmtObject1=null;	
							   		 existInStationary=null;
							  		 bufInsSql1=null;	
									 queryList.clear();
									 queryList=null;
				  		}
				  					 in1=null;
						  				out1=null;
						  				outMessages1=null;
						  				s22=null;
						  				date=null;  
	            	  }
	            	  else
	            	  {
						 bufInsSql.append("update cr_instrument_dtl set ");
						 bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=STR_TO_DATE(?,'"+dateFormat+"'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'"+dateFormat+"'),");
						 bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
						 bufInsSql.append(" MAKER_REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),DEPOSIT_BANK_ID=?,DEPOSIT_BRANCH_ID=?,DEPOSIT_MICR_CODE=?,DEPOSIT_IFSC_CODE=?,DEPOSIT_BANK_ACCOUNT=? ,REC_STATUS='F',RECEIVED_FROM=?,CONTACT_NO=? where TXNID=?  ");
						 bufInsSql.append(" AND INSTRUMENT_ID=?");
					
				
				 if((CommonFunction.checkNull(receiptVO.getReceiptMode())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptMode()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getReceiptDate())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptDate()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getReceiptAmount())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addString("0");
					else
						updatePrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim())).toString());
				
				 if((CommonFunction.checkNull(receiptVO.getInstrumentNumber())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getInstrumentDate())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getBankAccount())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getBankAccount()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getLbxBankID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBankID()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getLbxBranchID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getMicr())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getMicr()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getIfsCode())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getIfsCode()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getRemarks())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getRemarks()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getTdsAmount())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addString("0.00");
					else
						updatePrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getTdsAmount()).trim())).toString());
				 
				 if((CommonFunction.checkNull(receiptVO.getReceiptNo())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptNo()).trim()));



					if ((CommonFunction.checkNull(receiptVO.getMakerId())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((receiptVO.getMakerId()).trim());
					if ((CommonFunction.checkNull(receiptVO.getBusinessDate()).trim()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
			
					if((CommonFunction.checkNull(receiptVO.getDepositBankID())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankID()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getDepositBranchID())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBranchID()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getDepositMicr())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositMicr()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getDepositIfscCode())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositIfscCode()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getDepositBankAccount())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankAccount()).trim()));
				
					if((CommonFunction.checkNull(receiptVO.getReceivedFrom())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceivedFrom()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getContactNo())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getContactNo()).trim()));
				
				
				 if((CommonFunction.checkNull(receiptVO.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim()));
				 
				 
				 if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
				   
				 updatePrepStmtObject.setSql(bufInsSql.toString());
				 bufInsSql=null;
					logger.info("IN saveForwardUpdateOnReceipt query1 ### "+updatePrepStmtObject.printQuery());
					queryList.add(updatePrepStmtObject);
					updatePrepStmtObject=null;
//mradul starts
						PrepStmtObject updatePrepStmtObject1 = new PrepStmtObject();	
		 			  	StringBuffer bufInsSql1 =	new StringBuffer();
		 			  	StringBuilder inventoryFlagInMst = new StringBuilder();
		 			  	StringBuilder existInStationary = new StringBuilder();
		 			    String inventoryFlag=null;
		 			    String checkAllBranch=null;
		 			    String checkUserAvailable=null;
		 			    StringBuilder allBranch = new StringBuilder();
		 			  	StringBuilder checkUser = new StringBuilder();
						String resultStationary=null;
						boolean updateStatus=false;
						String IssuingUser=receiptVO.getMakerId();
						
		 			  	inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='INVENTORY_FLAG'");
				  		logger.info("IN checkReciptStatus inventoryFlag ### "+inventoryFlagInMst.toString());
				  		inventoryFlag=ConnectionDAO.singleReturn(inventoryFlagInMst.toString());
			  		
			  	if(inventoryFlag.equalsIgnoreCase("Y") && !CommonFunction.checkNull(receiptVO.getReceiptNo()).equalsIgnoreCase(""))
			  	    {
				  		allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"' AND STATUS='A'");
				  		logger.info("IN checkAllBranch ### "+allBranch.toString());
				  		checkAllBranch=ConnectionDAO.singleReturn(allBranch.toString());
			  		
			  	if(!(checkAllBranch.equalsIgnoreCase("0")))
			  		{
				  		if(CommonFunction.checkNull(receiptVO.getRemarks()).equalsIgnoreCase(""))
				  		   {
				  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='USED BY IMD MAKER',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE=STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
				  		   }
				  		   else
				  		   {
				  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='"+receiptVO.getRemarks()+"',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE=STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
				  		   }
				  		logger.info("IN updateStationary  ### "+bufInsSql1.toString() + receiptVO.getReceiptNo());
				  	    updatePrepStmtObject1.setSql(bufInsSql1.toString());
				  		queryList.add(updatePrepStmtObject1);
				  		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
			  	}else{
				  		checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"' AND IFNULL(ISSUING_USER,'')<>''");
				  		logger.info("IN checkInventory inventoryFlag ### "+checkUser.toString());
				  		checkUserAvailable=ConnectionDAO.singleReturn(checkUser.toString());	
			  	if(!(checkUserAvailable.equalsIgnoreCase("0")))
					{
			  			existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'  AND IFNULL(ISSUING_USER,'"+IssuingUser+"')='"+IssuingUser+"' ");
				  		logger.info("IN checkUserAvailability  in existInStationary ### "+existInStationary.toString());
				  		resultStationary=ConnectionDAO.singleReturn(existInStationary.toString());
			  	}else{
			    	 	existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'  AND ISSUING_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getDefaultBranch()).trim())+"'");
				  		logger.info("IN checkReciptStatus  in existInStationary ### "+existInStationary.toString());
				  		resultStationary=ConnectionDAO.singleReturn(existInStationary.toString());
			         }
		  	   if(!(resultStationary.equalsIgnoreCase("0")))
				  	{
				  		 if(CommonFunction.checkNull(receiptVO.getRemarks()).equalsIgnoreCase(""))
				  		   {
				  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='USED BY IMD MAKER',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE=STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
				  		   }
				  		   else
				  		   {
				  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='"+receiptVO.getRemarks()+"',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE=STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
				  		   }
				  		logger.info("IN updateStationary  ### "+bufInsSql1.toString() + receiptVO.getReceiptNo());
				  	    updatePrepStmtObject1.setSql(bufInsSql1.toString());
				  		queryList.add(updatePrepStmtObject1);
				  		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);				  		 
				}else{
				     	status=false;
				  	}
						
		  	         }   
			  	    }
			   else{
			  		 	status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
			  	 	}
	            
				  		 updatePrepStmtObject1=null;	
				   		 existInStationary=null;
				  		 bufInsSql1=null;	
						 queryList.clear();
						 queryList=null;
						 inventoryFlagInMst=null;
			  				inventoryFlag=null;
			  				checkAllBranch=null;
			  				checkUserAvailable=null;
			  				allBranch=null;
			  				checkUser=null;
			  				resultStationary=null;
			  				IssuingUser=null;
	            }
	            	  chargeFlagQuery=null;
	  				
			}catch(Exception e){
						e.printStackTrace();
				}
			
			finally{
				receiptVO=null;
				updatePrepStmtObject=null;
				bufInsSql=null;
				s11=null;
				procval=null;
				
				

				
			}
			if(status||CommonFunction.checkNull(s11).equalsIgnoreCase("S"))
				{
					procval="queryexecuted";
				}
				return procval;
		 	}
  //mradul ends
		 public boolean updateOnReceiptSave(ReceiptMakerVO receiptVO){
				
				
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();	
				 StringBuffer bufInsSql =	new StringBuffer();
				boolean status=false;
				logger.info("updateOnReceiptSave(ReceiptMakerVO receiptVO) ");
				 ArrayList queryList=new ArrayList();
				try{

					
					 bufInsSql.append(" update cr_instrument_dtl set ");
					 bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=STR_TO_DATE(?,'"+dateFormat+"'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"'),");
					 bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,BPTYPE=?,BPID=?,");
					 bufInsSql.append("  ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
					 bufInsSql.append("  MAKER_REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?,DEFAULT_BRANCH=?,RECEIPT_PURPOSE=?,TRANSACTION_REF=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),DEPOSIT_BANK_ID=?,DEPOSIT_BRANCH_ID=?,DEPOSIT_MICR_CODE=?,DEPOSIT_IFSC_CODE=?,DEPOSIT_BANK_ACCOUNT=?,RECEIVED_FROM=?,CONTACT_NO=?  where TXNID=? and INSTRUMENT_ID=?");
			
				
				 	 
				 if((CommonFunction.checkNull(receiptVO.getReceiptMode())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptMode()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getReceiptDate())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptDate()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getReceiptAmount())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addString("0");
					else
						updatePrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim())).toString());
				 
				 if((CommonFunction.checkNull(receiptVO.getInstrumentNumber())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getInstrumentDate())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getBankAccount())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getBankAccount()).trim()));

					if((CommonFunction.checkNull(receiptVO.getLbxBPType())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBPType()).trim()));
					if((CommonFunction.checkNull(receiptVO.getLbxBPNID())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBPNID()).trim()));
				 if((CommonFunction.checkNull(receiptVO.getLbxBankID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBankID()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getLbxBranchID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getMicr())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getMicr()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getIfsCode())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getIfsCode()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getRemarks())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getRemarks()).trim()));
				 
				 if((CommonFunction.checkNull(receiptVO.getTdsAmount())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addString("0.00");
					else
						updatePrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getTdsAmount()).trim())).toString());
				 
				 if((CommonFunction.checkNull(receiptVO.getReceiptNo())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptNo()).trim()));

			
					if ((CommonFunction.checkNull(receiptVO.getDefaultBranch())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((receiptVO.getDefaultBranch()).trim());
					if ((CommonFunction.checkNull(receiptVO.getPurpose())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((receiptVO.getPurpose()).trim());
					if ((CommonFunction.checkNull(receiptVO.getTransactionRefNo())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((receiptVO.getTransactionRefNo()).trim());
					if ((CommonFunction.checkNull(receiptVO.getMakerId())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((receiptVO.getMakerId()).trim());
					if ((CommonFunction.checkNull(receiptVO.getBusinessDate()).trim()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
					if((CommonFunction.checkNull(receiptVO.getDepositBankID())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankID()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getDepositBranchID())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBranchID()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getDepositMicr())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositMicr()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getDepositIfscCode())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositIfscCode()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getDepositBankAccount())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankAccount()).trim()));
				
					if((CommonFunction.checkNull(receiptVO.getReceivedFrom())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceivedFrom()).trim()));
					
					if((CommonFunction.checkNull(receiptVO.getContactNo())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getContactNo()).trim()));
				
				
					
				 if((CommonFunction.checkNull(receiptVO.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim()));
				 
							 
				 if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
				 
				 updatePrepStmtObject.setSql(bufInsSql.toString());
				 bufInsSql=null;
					logger.info("IN updateOnReceiptSave() insert query1 ### "+updatePrepStmtObject.printQuery());
					queryList.add(updatePrepStmtObject);
					updatePrepStmtObject=null;
					status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
					queryList.clear();
					queryList=null;
					
				}catch(Exception e){
					e.printStackTrace();
				}
				finally{
					receiptVO=null;
				}

				return status;
			}

		 public String  checkFesiblityOnForward(ReceiptMakerVO receiptVO) {

			  	String status=null;
			  	StringBuilder query = new StringBuilder();
			
			  
		         String receiptAmount=  CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim(); 
		       
		         double receiptAmnt=0.0;
		         double tdsAmnt=0.0;
		         try{ 
		        	 if(receiptAmount.equalsIgnoreCase("")){
		        	 receiptAmount="0";  
		         }else{
		        	 receiptAmount= CommonFunction.decimalNumberConvert(CommonFunction.checkNull(receiptVO.getReceiptAmount())).toString();
		         }
		         receiptAmnt=Double.parseDouble(receiptAmount);
		         String tdsAmount=  CommonFunction.checkNull(receiptVO.getTdsAmount()).trim(); 
		        
		         if(tdsAmount.equalsIgnoreCase("")){
		        	 
		        	 tdsAmount="0";  
		         }
		         else{
		        	 tdsAmount= CommonFunction.decimalNumberConvert(CommonFunction.checkNull(receiptVO.getTdsAmount())).toString();
		         }
		         tdsAmnt=Double.parseDouble(tdsAmount);
		         double Amount=(receiptAmnt + tdsAmnt);
		         logger.info("In checkFesiblityOnForward......Amount  "+Amount);
		         String chargeFlagQuery=ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");
					if(CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y"))
					{
		               query.append(" SELECT ifnull(SUM(ALLOCATED_AMOUNT),0),ifnull(SUM(ALLOCATED_TDS),0) FROM CR_GROSS_ALLOCATION_DTL WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+"'");
					}
					else
					{
						query.append(" SELECT ifnull(SUM(PMNT_AMOUNT),0),ifnull(SUM(TDS_AMOUNT),0) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+"'");
					}
			  	  logger.info("IN checkAllocation  ### "+query.toString());
			      ArrayList arrAmt = ConnectionDAO.sqlSelect(query.toString());
			      String AllocatedAmount =null;
			      String TdsAllocatedAmount =null;
			      if(arrAmt.size()>0)
			      {
			    	  ArrayList subList= (ArrayList)arrAmt.get(0);
			    	  AllocatedAmount=CommonFunction.checkNull(((ArrayList)arrAmt.get(0)).get(0)).trim();
			    	  TdsAllocatedAmount=CommonFunction.checkNull(((ArrayList)arrAmt.get(0)).get(1)).trim();
			      }
			      
//			      String AllocatedAmount=CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
			      double VAllocatedAmount=0.0;
			      if(AllocatedAmount.equalsIgnoreCase("")){
			    	  AllocatedAmount="0";
			      }
			    	  VAllocatedAmount=Double.parseDouble(CommonFunction.checkNull(AllocatedAmount));

			  	  logger.info("VAllocatedAmount----"+VAllocatedAmount);
			  	  
//			  	 query1.append(" SELECT ifnull(SUM(TDS_AMOUNT),0) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+"'");
//			   		logger.info("IN checkAllocation 1  ### "+query1.toString());
//			  		String TdsAllocatedAmount =ConnectionDAO.singleReturn(query1.toString());  		 
			  		
			  		double VTdsAllocatedAmount=0.0;
				      if(TdsAllocatedAmount.equalsIgnoreCase("")){
				    	  TdsAllocatedAmount="0";
				      }
				      VTdsAllocatedAmount=Double.parseDouble(CommonFunction.checkNull(TdsAllocatedAmount));
			     	logger.info("VTdsAllocatedAmount----"+VTdsAllocatedAmount);
				 
			  		if( VAllocatedAmount == 0.0000 ){
			  			status ="DA";
					  }
			  		else  if((Amount>=VAllocatedAmount) && (tdsAmnt==VTdsAllocatedAmount)){
					  
					  status ="A";
				  }
				  else if((Amount < VAllocatedAmount) || (tdsAmnt != VTdsAllocatedAmount)){
					 
					  status ="NA";
			    }
			  		tdsAmount=null;
			  		chargeFlagQuery=null;
			  		AllocatedAmount=null;
			  		TdsAllocatedAmount=null;
		         }
			  	   catch(Exception e){
							e.printStackTrace();
						}
			  	   finally
			  	   {
			  		 query = null;
			  		receiptVO=null;
			  		receiptAmount=null;
			  		
			  		

			  	   }

						return status ;
		}
		 public  int existInsNo(ReceiptMakerVO vo){
		  	  int result=0;
		  	StringBuilder existCount = new StringBuilder();
		  	StringBuilder res = new StringBuilder();
		  
		  	   try{
		  		   existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim())+"'" +
		  	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim())+"'and cid.ISSUEING_BRANCH_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchID()).trim())+"'" +
		  	  	    " and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim())+"'" +
		  	  	    " and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim())+"' " +
		  	  	    " and INSTRUMENT_ID <>'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' and INSTRUMENT_TYPE='R'  AND REC_STATUS<>'X'");
		  	  
		  		 logger.info("IN existInsNo  ### "+existCount.toString());
		  	 res.append(ConnectionDAO.singleReturn(existCount.toString()));
		  	 
		  	if(!(CommonFunction.checkNull(res.toString())).equalsIgnoreCase("")){
		    	
		  		result=Integer.parseInt(res.toString());
			    }
		  		logger.info("IN existReceiptData  ### "+result);
		    }
		  	   catch(Exception e){
						e.printStackTrace();
					}
		  	   finally
		  	   {
		  		 vo=null;
		  		 existCount = null;
		  		 res = null;
		  	   }

					return result;
				}
		 public  int existInsNForNR(ReceiptMakerVO vo){
		  	  int result=0;
		  	StringBuilder existCount = new StringBuilder();
		  	StringBuilder res = new StringBuilder();
		  
		  	   try{
		  		   existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim())+"'" +
		  	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim())+"'" +
		  	  	    " and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim())+"'" +
		  	  	    " and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim())+"' " +
		  	  	    " and INSTRUMENT_ID <>'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' and INSTRUMENT_TYPE='R' AND REC_STATUS<>'X'");
		  	  
		  	 logger.info("IN existInsNForNR  ### "+existCount.toString());
		  	 res.append(ConnectionDAO.singleReturn(existCount.toString()));
		  	 
		  	if(!(CommonFunction.checkNull(res.toString())).equalsIgnoreCase("")){
		    	
		  		result=Integer.parseInt(res.toString());
			    }
		  		logger.info("IN existInsNForNR  ### "+result);
		    }
		  	   catch(Exception e){
						e.printStackTrace();
					}
		  	   finally
		  	   {
		  		 existCount = null;
		  		 res = null;
		  		vo=null;
		  	   }

					return result;
				}
		
		 public String saveForwardReceiptData(ReceiptMakerVO receiptVO,float amount){
			 
				
				//ArrayList<ReceiptMakerVO> getDataList=new ArrayList<ReceiptMakerVO>();
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();	
				 StringBuffer bufInsSql =	new StringBuffer();
				boolean status=false;
				StringBuilder s11 = new StringBuilder();
				logger.info("In saveForwardReceiptData(ReceiptMakerVO receiptVO,float amount) ");
				 ArrayList queryList=new ArrayList();
				 ArrayList arrList=new ArrayList();
				 String procval=null;	

				    ArrayList<Object> in =new ArrayList<Object>();
					ArrayList<Object> out =new ArrayList<Object>();
					ArrayList outMessages = new ArrayList();
					StringBuilder s1 = new StringBuilder();
					StringBuilder s2 = new StringBuilder();
					StringBuilder getProcessammountQuery = new StringBuilder();
					StringBuilder getAllotedAmtQuery = new StringBuilder();

				try{
					ArrayList txnAdviceId=new ArrayList();
					ArrayList amtInProcess=new ArrayList();
					ArrayList amtAllocated=new ArrayList();
				

				
					logger.info("BeforeProc: Receipt_Maker_Validation_M ");

					in.add(amount);
					in.add(((CommonFunction.checkNull(receiptVO.getInstrumentID().trim()))));			
					out.add(s1.toString());
				    out.add(s2.toString());
					
				     outMessages=(ArrayList) ConnectionDAO.callSP("Receipt_Maker_Validation_M",in,out);
				     s1.append(CommonFunction.checkNull(outMessages.get(0)));
					 s2.append(CommonFunction.checkNull(outMessages.get(1)));
					logger.info("Receipt_Maker_Validation_M s1: "+s1.toString());
					logger.info("Receipt_Maker_Validation_M s2: "+s2.toString());
					procval=CommonFunction.checkNull(s2.toString());

					
			
              if(!(s1.toString()).equalsIgnoreCase("E") && !(s2.toString()).equalsIgnoreCase("F")){	
            	  
            	  String chargeFlagQuery=ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");
            	  if(CommonFunction.checkNull(chargeFlagQuery).equalsIgnoreCase("Y"))
            	  {
            		ArrayList<Object> in1 =new ArrayList<Object>();
  					ArrayList<Object> out1 =new ArrayList<Object>();
  					ArrayList outMessages1 = new ArrayList();
  					
  					StringBuilder s22 = new StringBuilder();
  					
  					in1.add(((CommonFunction.checkNull(receiptVO.getInstrumentID().trim()))));
  					in1.add("F");
  					in1.add("");
  					in1.add(CommonFunction.checkNull(receiptVO.getMakerId()).trim());
  					String date=CommonFunction.changeFormat(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
  			  	    if(date != null)
  			  	    in1.add(date);
  				
  					out1.add(s11.toString());
  				    out1.add(s22.toString());
  					
  				     outMessages1=(ArrayList) ConnectionDAO.callSP("GROSS_RECEIPT_ALLOCATION",in1,out1);
  				     s11.append(CommonFunction.checkNull(outMessages1.get(0)));
  					 s22.append(CommonFunction.checkNull(outMessages1.get(1)));
  					 logger.info("GROSS_RECEIPT_ALLOCATION s11: "+s11.toString());
  					 logger.info("GROSS_RECEIPT_ALLOCATION s22: "+s22.toString());
  					 procval=CommonFunction.checkNull(s22.toString());
  					 
  					  
        			 bufInsSql.append("update cr_instrument_dtl set ");
        			 bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=STR_TO_DATE(?,'"+dateFormat+"'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'"+dateFormat+"'),");
        			 bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
        			 bufInsSql.append(" REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?, REC_STATUS='F',MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),DEPOSIT_BANK_ID=?,DEPOSIT_BRANCH_ID=?,DEPOSIT_MICR_CODE=?,DEPOSIT_IFSC_CODE=?,DEPOSIT_BANK_ACCOUNT=? ,RECEIVED_FROM=?,CONTACT_NO=? where TXNID=?  ");
        			 bufInsSql.append(" AND INSTRUMENT_ID=?");
        				
        			
        			 if((CommonFunction.checkNull(receiptVO.getReceiptMode())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptMode()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getReceiptDate())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptDate()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getReceiptAmount())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addString("0.00");
        				else
        					updatePrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim())).toString());
        			
        			 if((CommonFunction.checkNull(receiptVO.getInstrumentNumber())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getInstrumentDate())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getBankAccount())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getBankAccount()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getLbxBankID())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBankID()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getLbxBranchID())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getMicr())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getMicr()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getIfsCode())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getIfsCode()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getRemarks())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getRemarks()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getTdsAmount())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addString("0.00");
        				else
        					updatePrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getTdsAmount()).trim())).toString());
        			 
        			 if((CommonFunction.checkNull(receiptVO.getReceiptNo())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptNo()).trim()));
        			 
        			 
        			 if(CommonFunction.checkNull(receiptVO.getMakerId()).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        				updatePrepStmtObject.addString(receiptVO.getMakerId().trim());
        				
        			 if(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        				updatePrepStmtObject.addString(receiptVO.getBusinessDate().trim());
        			
        			 if((CommonFunction.checkNull(receiptVO.getDepositBankID())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankID()).trim()));
        				
        				if((CommonFunction.checkNull(receiptVO.getDepositBranchID())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBranchID()).trim()));
        				
        				if((CommonFunction.checkNull(receiptVO.getDepositMicr())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositMicr()).trim()));
        				
        				if((CommonFunction.checkNull(receiptVO.getDepositIfscCode())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositIfscCode()).trim()));
        				
        				if((CommonFunction.checkNull(receiptVO.getDepositBankAccount())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankAccount()).trim()));
        				if((CommonFunction.checkNull(receiptVO.getReceivedFrom())).trim().equalsIgnoreCase(""))
    						updatePrepStmtObject.addNull();
    					else
    						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceivedFrom()).trim()));
    					
    					if((CommonFunction.checkNull(receiptVO.getContactNo())).trim().equalsIgnoreCase(""))
    						updatePrepStmtObject.addNull();
    					else
    						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getContactNo()).trim()));
    				
    				
        			 
        			 if((CommonFunction.checkNull(receiptVO.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim()));
        			 
        					 
        			 if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
        			 
        			 updatePrepStmtObject.setSql(bufInsSql.toString());
        			 bufInsSql=null;
        				logger.info("IN saveForwardReceiptData cr_instrument_dtl query1 ### "+updatePrepStmtObject.printQuery());
        				queryList.add(updatePrepStmtObject);
        				updatePrepStmtObject=null;
        				
        			  status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
           			  queryList.clear();
           			  queryList=null;
           			  chargeFlagQuery=null;
           			  in1=null;
           			  out1=null;
           			  outMessages1=null;
           			  s22=null;
            	  }
            	  else
            	  {
         			 logger.info("saveForwardReceiptData(ReceiptMakerVO receiptVO,float amount) After Proc In If Block ");
						
                  					  
        			 bufInsSql.append("update cr_instrument_dtl set ");
        			 bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=STR_TO_DATE(?,'"+dateFormat+"'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=STR_TO_DATE(?,'"+dateFormat+"'),");
        			 bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
        			 bufInsSql.append(" REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?, REC_STATUS='F',MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),DEPOSIT_BANK_ID=?,DEPOSIT_BRANCH_ID=?,DEPOSIT_MICR_CODE=?,DEPOSIT_IFSC_CODE=?,DEPOSIT_BANK_ACCOUNT=?,RECEIVED_FROM=?,CONTACT_NO=? where TXNID=?  ");
        			 bufInsSql.append(" AND INSTRUMENT_ID=?");
        				
        			
        			 if((CommonFunction.checkNull(receiptVO.getReceiptMode())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptMode()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getReceiptDate())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptDate()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getReceiptAmount())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addString("0.00");
        				else
        					updatePrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim())).toString());
        			
        			 if((CommonFunction.checkNull(receiptVO.getInstrumentNumber())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getInstrumentDate())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getBankAccount())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getBankAccount()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getLbxBankID())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBankID()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getLbxBranchID())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBranchID()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getMicr())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getMicr()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getIfsCode())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getIfsCode()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getRemarks())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getRemarks()).trim()));
        			 
        			 if((CommonFunction.checkNull(receiptVO.getTdsAmount())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addString("0.00");
        				else
        					updatePrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getTdsAmount()).trim())).toString());
        			 
        			 if((CommonFunction.checkNull(receiptVO.getReceiptNo())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptNo()).trim()));
        			 
        			 
        			 if(CommonFunction.checkNull(receiptVO.getMakerId()).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        				updatePrepStmtObject.addString(receiptVO.getMakerId().trim());
        				
        			 if(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        				updatePrepStmtObject.addString(receiptVO.getBusinessDate().trim());
        			
        			 if((CommonFunction.checkNull(receiptVO.getDepositBankID())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankID()).trim()));
        				
        				if((CommonFunction.checkNull(receiptVO.getDepositBranchID())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBranchID()).trim()));
        				
        				if((CommonFunction.checkNull(receiptVO.getDepositMicr())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositMicr()).trim()));
        				
        				if((CommonFunction.checkNull(receiptVO.getDepositIfscCode())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositIfscCode()).trim()));
        				
        				if((CommonFunction.checkNull(receiptVO.getDepositBankAccount())).trim().equalsIgnoreCase(""))
        					updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankAccount()).trim()));
        			
        				if((CommonFunction.checkNull(receiptVO.getReceivedFrom())).trim().equalsIgnoreCase(""))
    						updatePrepStmtObject.addNull();
    					else
    						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceivedFrom()).trim()));
    					
    					if((CommonFunction.checkNull(receiptVO.getContactNo())).trim().equalsIgnoreCase(""))
    						updatePrepStmtObject.addNull();
    					else
    						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getContactNo()).trim()));
    				
        			 
        			 if((CommonFunction.checkNull(receiptVO.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim()));
        			 
        					 
        			 if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
        				 updatePrepStmtObject.addNull();
        				else
        					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
        			 
        			 updatePrepStmtObject.setSql(bufInsSql.toString());
        			 bufInsSql=null;
        				logger.info("IN saveForwardReceiptData cr_instrument_dtl query1 ### "+updatePrepStmtObject.printQuery());
        				queryList.add(updatePrepStmtObject);
        				updatePrepStmtObject=null;
        			 
        			// Prashant Start Here 
        				
        				//if(!CommonFunction.checkNull(receiptVO.getInstrumentID()).equalsIgnoreCase(""))
        				//{
        					String getPmntIdsQuery="select PMNT_ID from cr_pmnt_dtl where PMNT_AMOUNT=0.00 or PMNT_AMOUNT is NULL ";
        					logger.info("In getPmntIdsQuery---"+getPmntIdsQuery);
        					ArrayList getPmntIds = ConnectionDAO.sqlSelect(getPmntIdsQuery);
        					getPmntIdsQuery=null;
        					int size=getPmntIds.size();
        					if(size>0)
        					{
        						  for(int j=0;j<size;j++){
        							  
        							  ArrayList subgetPmntIdsList=(ArrayList)getPmntIds.get(j);
        							  if(subgetPmntIdsList.size()>0){
        								   String pmntId=subgetPmntIdsList.get(0).toString();
        								 
        									updatePrepStmtObject = new PrepStmtObject();	
        									bufInsSql =	new StringBuffer();
        									bufInsSql.append(" Delete from cr_pmnt_dtl ");
        									bufInsSql.append(" where PMNT_ID=?");
        									
        									
        									  if(CommonFunction.checkNull(pmntId).trim().equalsIgnoreCase(""))
        											 updatePrepStmtObject.addNull();
        											else
        												updatePrepStmtObject.addString((CommonFunction.checkNull(pmntId).trim()));
        									  
        									
        									 updatePrepStmtObject.setSql(bufInsSql.toString());
        									 bufInsSql=null;
        									logger.info("IN at saveForwardReceiptData() delete cr_pmnt_dtl query1 ### "+updatePrepStmtObject.printQuery());
        									queryList.add(updatePrepStmtObject);
        									updatePrepStmtObject=null;
        									pmntId=null; 
        							  }
        							  subgetPmntIdsList=null;
        							 
        							  
        						  }
        						  getPmntIds=null;
        					}
        					
        				//}
        				// Prashant End Here 
        				updatePrepStmtObject = new PrepStmtObject();	
        				bufInsSql =	new StringBuffer();
        				
        				
        				 bufInsSql.append(" update cr_pmnt_dtl set REC_STATUS='F',MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where   INSTRUMENT_ID=?");
        				 
        				 
        				 if(CommonFunction.checkNull(receiptVO.getMakerId()).trim().equalsIgnoreCase(""))
        					 updatePrepStmtObject.addNull();
        					else
        					updatePrepStmtObject.addString(receiptVO.getMakerId().trim());
        					
        				 if(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim().equalsIgnoreCase(""))
        					 updatePrepStmtObject.addNull();
        					else
        					updatePrepStmtObject.addString(receiptVO.getBusinessDate().trim());
        				 
        				 if(CommonFunction.checkNull(receiptVO.getInstrumentID()).equalsIgnoreCase(""))
        					 updatePrepStmtObject.addNull();
        					else
        					updatePrepStmtObject.addString((receiptVO.getInstrumentID()).trim());
        				
        				 updatePrepStmtObject.setSql(bufInsSql.toString());
        				 bufInsSql=null;
        				logger.info("IN saveForwardReceiptData cr_pmnt_dtl query1 ### "+updatePrepStmtObject.printQuery());
        				queryList.add(updatePrepStmtObject);
        				updatePrepStmtObject=null;


        				
        				getProcessammountQuery.append("Select TXNADVICE_ID,AMOUNT_IN_PROCESS from tmp_adv_payrec " +
        			  		                    " WHERE ifnull(PMNT_AMOUNT,0)>0 AND LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim())+"' AND ADVICE_TYPE='R' " +
        			  		                    " and BP_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxBPType()).trim())+"' and " +
        			  		                    " maker_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getMakerId()).trim())+"' and PMNT_AMOUNT>0 order by TXNADVICE_ID ");
        			  logger.info("In getProcessammountQuery---"+getProcessammountQuery.toString());
        					
        			  ArrayList processAmmountList = ConnectionDAO.sqlSelect(getProcessammountQuery.toString());		
        			  getProcessammountQuery=null;
        			  getAllotedAmtQuery.append("Select  IfNull(PMNT_AMOUNT,0 )from cr_pmnt_dtl where INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(receiptVO.getInstrumentID())+"' AND TXNADVICEID in " +
        			  	                       	"(Select TXNADVICE_ID from tmp_adv_payrec " +
        			  		                    " WHERE  ifnull(PMNT_AMOUNT,0)>0 AND LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim())+"' AND ADVICE_TYPE='R' " +
        			  		                    " and BP_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxBPType()).trim())+"' and " +
        			  		                    " maker_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getMakerId()).trim())+"') order by TXNADVICEID ");
        			  logger.info("In getAllotedAmtQuery---"+getAllotedAmtQuery.toString());
        			  ArrayList allotedAmmountList = ConnectionDAO.sqlSelect(getAllotedAmtQuery.toString());	
        			  getAllotedAmtQuery=null;
        			  int processAmmountListSize=processAmmountList.size();
        			// Prashant Start Here 
        			  for(int j=0;j<processAmmountListSize;j++){
        				  
        				  ArrayList subprocessAmmountList=(ArrayList)processAmmountList.get(j);
        				
        			  
        				   if(subprocessAmmountList.size()>0){
        					   txnAdviceId.add(subprocessAmmountList.get(0));
        					   amtInProcess.add(subprocessAmmountList.get(1));
        					
        				   } 
        				   subprocessAmmountList=null;
        			  }
        			  processAmmountList=null;
        			  int allotedAmmountListSize=allotedAmmountList.size();
               for(int j=0;j<allotedAmmountListSize;j++){
        				  
        				  ArrayList suballotedAmmountList=(ArrayList)allotedAmmountList.get(j);
        			  
        				   if(suballotedAmmountList.size()>0){
        					  
        					   amtAllocated.add(suballotedAmmountList.get(0));   
        				   } 
        				   suballotedAmmountList=null;
        			  }
               allotedAmmountList=null;
               int amtAllocatedSize=amtAllocated.size();
            // Prashant END Here 
        			  for(int i=0;i<amtAllocatedSize;i++){
        				  updatePrepStmtObject = new PrepStmtObject();	
        					bufInsSql =	new StringBuffer();
        					StringBuilder txnAdviceID = new StringBuilder();
        					txnAdviceID.append(CommonFunction.checkNull(txnAdviceId.get(i)));
        				 double sum=Double.parseDouble((String)amtInProcess.get(i))+Double.parseDouble((String)amtAllocated.get(i));
        				  logger.info("sum of Ammount in process and ammount allocated----- "+sum);
        				 
        				  bufInsSql.append(" Update cr_txnadvice_dtl set AMOUNT_IN_PROCESS=?" );
        				  bufInsSql.append(" where TXNADVICE_ID=?");
        				
        				  if((CommonFunction.checkNull(sum)).trim().equalsIgnoreCase(""))
        						 updatePrepStmtObject.addString("0");
        						else
        							updatePrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(sum).trim())).toString());
        				  if((CommonFunction.checkNull(txnAdviceID.toString())).trim().equalsIgnoreCase(""))
        						 updatePrepStmtObject.addNull();
        						else
        							updatePrepStmtObject.addString((CommonFunction.checkNull(txnAdviceID.toString()).trim()));
        				  
        				
        				 updatePrepStmtObject.setSql(bufInsSql.toString());
        				 bufInsSql=null;
        				logger.info("IN SaveDatafor() Update cr_txnadvice_dtl query1 ### "+updatePrepStmtObject.printQuery());
        				queryList.add(updatePrepStmtObject);
        				updatePrepStmtObject=null;
        				//logger.info("Update Query----- "+updateQuery);
        				  logger.info("count i"+i);
        				 // queryList.add(updateQuery);
        				  txnAdviceID = null;
        			  }
        			  		  
        			  status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
        			  queryList.clear();
        			  queryList=null;
        				logger.info("In saveForwardReceiptData,,,,,"+status);
            	  }
		

				
              }//end of if	
					if(status || CommonFunction.checkNull(s11).equalsIgnoreCase("S")){
						procval="queryexecuted";
						}
					txnAdviceId=null;
					amtInProcess=null;
					amtAllocated=null;
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					in=null;
					out=null;
					s1 = null;
					s2 = null;
					getProcessammountQuery = null;
					getAllotedAmtQuery = null;
					receiptVO=null;
					s11=null;
					arrList.clear();
					arrList=null;
					outMessages.clear();
					outMessages=null;
					
					
				}

				
				return procval;
			}
		 
		 public ArrayList<ReceiptMakerVO>onAllocatedReceivable(ReceiptMakerVO receiptVO,int loanId,String bpType,int instrumentId)
			{
		     ArrayList<ReceiptMakerVO> allocatedList=new ArrayList<ReceiptMakerVO>();
			  StringBuilder query = new StringBuilder();
		 				
			 	try{
			 		ArrayList mainList=new ArrayList ();
			 		ArrayList subList =new ArrayList();
			 
			 		logger.info(" In onAllocatedReceivable....");	
			 	
			 		query.append("SELECT  distinct DATE_FORMAT(ADVICE_DATE,'"+dateFormat+"')," +
			 				" (Select CHARGE_DESC From com_charge_code_m Where CHARGE_CODE=CTD.CHARGE_CODE_ID) CHARGE, ADVICE_AMOUNT,  "+
			 		" (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,   "+
			       " AMOUNT_IN_PROCESS,CTD.TDS_AMOUNT, PMNT_AMOUNT,CPD.TDS_AMOUNT AS TDS_ALLOCATED_AMOUNT "+
			       " from cr_txnadvice_dtl CTD,cr_pmnt_dtl CPD  where CPD.TXNADVICEID=CTD.TXNADVICE_ID "+
			       " AND ADVICE_TYPE='R' "+
			       "  AND LOAN_ID='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+ "'" +
			     	" AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "' " +
			      	" AND CPD.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentId).trim()) + "'" +
			      	"  Order By CTD.ADVICE_DATE ASC ");

			 		
			 		logger.info("In onAllocatedReceivable"+query.toString());	
			 		
			 		mainList=ConnectionDAO.sqlSelect(query.toString());
			 		
			 		int size=mainList.size();
			 		for(int i=0;i<size;i++)
			 		{
			 			subList= (ArrayList)mainList.get(i);
			 			if(subList.size()>0){
			 				
			 				ReceiptMakerVO VO = new ReceiptMakerVO();
			 				VO.setReceiptDate((CommonFunction.checkNull(subList.get(0)).trim()));
			 				VO.setChargeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
			 				Number OriginalAmount =0;
			 				if(!CommonFunction.checkNull(subList.get(2)).equals(""))
			 				{
			 					OriginalAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(2))).trim());
			 				}
			 			  
			 		    	VO.setOriginalAmount(myFormatter.format(OriginalAmount));
			 		    	Number BalanceAmount =0;
			 		    	if(!CommonFunction.checkNull(subList.get(3)).equals(""))
			 				{
			 		    		BalanceAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(3))).trim());
			 				}
			 						
			 		    	VO.setBalanceAmount(myFormatter.format(BalanceAmount));
			 		    	Number AmountInProcess =0;
			 		    	if(!CommonFunction.checkNull(subList.get(4)).equals(""))
			 				{
			 		    		AmountInProcess = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(4))).trim());
			 				}
				 				
				 		    VO.setAmountInProcess(myFormatter.format(AmountInProcess));
				 		   Number TdsadviseAmount = 0;
				 		    if(!CommonFunction.checkNull(subList.get(5)).equals(""))
			 				{
				 		    	TdsadviseAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(5))).trim());
			 				}
			 			  		
			 		    	VO.setTdsadviseAmount(myFormatter.format(TdsadviseAmount));	
			 		    	Number AllotedAmount = 0;
			 		    	if(!CommonFunction.checkNull(subList.get(6)).equals(""))
			 				{
				 		    	AllotedAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(6))).trim());
			 				}
				 					 		    	
			 			    VO.setAllotedAmount(myFormatter.format(AllotedAmount));
			 			    if(!CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("")){
			 				Number TdsAllocatedAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subList.get(7))).trim());
				 				    	
			 				VO.setTdsAllocatedAmount(myFormatter.format(TdsAllocatedAmount));
			 			    }
			 			    else {
			 			    	VO.setTdsAllocatedAmount("0");
			 			    }
			 			    	
			 				allocatedList.add(VO);
			 				VO=null;
			 			}
			 			subList.clear();
			 			subList=null;
			 			
			 			
			 		}
			 		mainList.clear();
			 		mainList=null;
			 			}catch(Exception e){
			 				e.printStackTrace();
			 			}
			 			finally
			 			{
			 				query = null;
			 				receiptVO=null;
			 				bpType=null;
			 				
			 			}


			 			return allocatedList;
			 		}	
		 public String onSaveofReceiptAuthor(ReceiptMakerVO receiptVO) {
			 
			 
				boolean status=false;
				String procval=null;
			    ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				StringBuilder s1 = new StringBuilder();
				StringBuilder s2 = new StringBuilder();
				try{
				 
				ArrayList queryList=new ArrayList();
				
				int statusProc=0;

				logger.info(" In pay_approveReject_Rec onSaveofReceiptAuthor BeforeProc: ");
							
				in.add(receiptVO.getInstrumentID());		
				in.add(receiptVO.getDecision());
				in.add(receiptVO.getComments());
				in.add("R");
				in.add(CommonFunction.checkNull(receiptVO.getMakerId()).trim());
				String date=CommonFunction.changeFormat(receiptVO.getBusinessDate());
				in.add(date);
				out.add(s1.toString());
			    out.add(s2.toString());

				    outMessages=(ArrayList) ConnectionDAO.callSP("pay_approveReject_Rec",in,out);
				    s1.append(CommonFunction.checkNull(outMessages.get(0)));
				    s2.append(CommonFunction.checkNull(outMessages.get(1)));
				    outMessages.clear();
				    outMessages=null;
				    in.clear();
				    in=null;
				    out.clear();
				    out=null;
				logger.info("s1: "+s1.toString());
				logger.info("s2: "+s2.toString());
				procval=(s2.toString());
				if(!(s1.toString()).equalsIgnoreCase("S")){
					logger.info("After Proc inside If "); 
					status=false;
				}else
					status=true;  
					 if((s1.toString()).equalsIgnoreCase("S"))  
					 {
							logger.info("After proc call..commit.error message."+s2);

					}else{
							logger.info("After proc call..rollback.error message."+s2);

					}
				
		
				logger.info("In onSaveofReceiptAuthor,,,,,"+status);

				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					in=null;
					out=null;
					s1 = null;
					s2 = null;
					receiptVO=null;
				}

				
				return procval;
				}
//start from here now
			public ArrayList<ReceiptMakerVO> getchequeDetailR(String bankAccount,String accountType) {
				
				
				ArrayList<ReceiptMakerVO> getChequeList=new ArrayList<ReceiptMakerVO>();
				StringBuilder query = new StringBuilder();
				
				try{
					ArrayList mainList=new ArrayList ();
					ArrayList subList =new ArrayList();
					
					
					
					
					query.append(" SELECT CBA.BANK_ACCOUNT,CBA.BANK_ID,CB.BANK_NAME,CBA.BANK_BRANCH_ID,CBB.BANK_BRANCH_NAME,"+
		           "  CBA.BRANCH_MICR_CODE,CBA.BRANCH_IFCS_CODE FROM com_bank_m CB,com_bankbranch_m CBB,com_bank_accounts_m CBA "+
		           " WHERE CB.BANK_ID=CBA.BANK_ID AND CBB.BANK_BRANCH_ID=CBA.BANK_BRANCH_ID AND CBA.REC_STATUS='A' AND CBA.BANK_ACCOUNT='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankAccount).trim())+"'"+
		           " AND ACCOUNT_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(accountType).trim())+"'");
					
					logger.info("In getListOfValues"+query.toString());	
					
					mainList=ConnectionDAO.sqlSelect(query.toString());
					
					int size=mainList.size();
					for(int i=0;i<size;i++)
					{
						subList= (ArrayList)mainList.get(i);
						if(subList.size()>0){
							 ReceiptMakerVO receiptVo = new ReceiptMakerVO();
							 receiptVo.setBankAccount((CommonFunction.checkNull(subList.get(0)).trim()));
							 receiptVo.setLbxBankID((CommonFunction.checkNull(subList.get(1)).trim()));
							 receiptVo.setBank((CommonFunction.checkNull(subList.get(2)).trim()));
							 receiptVo.setLbxBranchID((CommonFunction.checkNull(subList.get(3)).trim()));
							 receiptVo.setBranch((CommonFunction.checkNull(subList.get(4)).trim()));
							 receiptVo.setMicr((CommonFunction.checkNull( subList.get(5)).trim()));
							 receiptVo.setIfsCode((CommonFunction.checkNull( subList.get(6)).trim()));
							 getChequeList.add(receiptVo);
						}
						subList.clear();
						subList=null;
						
					}
					mainList.clear();
					mainList=null;
						}catch(Exception e){
							e.printStackTrace();
						}
						finally
						{
							query = null;
							bankAccount=null;
							accountType=null;
						}


						return getChequeList;
					}
		
//Ritu
	
			public boolean deleteReceipt(String id) {
				
				logger.info("In deleteReceipt(String id) ");
				boolean status=false;
				boolean status1=false;
				ArrayList list=new ArrayList();
				ArrayList list1=new ArrayList();
				StringBuilder query=new StringBuilder();
				StringBuilder query1=new StringBuilder();
				try{
				query.append ("delete from cr_instrument_dtl where instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' ");
				list.add(query.toString());
				logger.info("delete()     query------------------" + query.toString());
				query=null;
				query1.append ("delete from cr_pmnt_dtl where instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
				list1.add(query1.toString());
				logger.info("delete()     query1------------------" + query1.toString());
				query1=null;
				status1 =ConnectionDAO.sqlInsUpdDelete(list1);
				list1.clear();
				list1=null;
				status =ConnectionDAO.sqlInsUpdDelete(list);
				list.clear();
				list=null;
				}
			  catch (Exception e) {
					e.printStackTrace();
				}
			  finally{
				  id=null;
			  }
			  return status;

			}

			public String getTotalRec(String loanId, String bPType) 
			{
				logger.info("In getTotalRec(String loanId, String bPType) .");
				String amount="0.00";
				StringBuilder query = new StringBuilder();				
				try
				{
					query.append(" SELECT sum(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT-AMOUNT_IN_PROCESS) as BALANCE_AMOUNT FROM cr_txnadvice_dtl A   WHERE A.REC_STATUS IN ('A', 'F') and A.ADVICE_TYPE='R' AND LOAN_ID='"+loanId.trim()+"' AND BP_TYPE='"+bPType.trim()+"'");
					logger.info("In getTotalRec() query  :  "+query.toString());						
					amount=ConnectionDAO.singleReturn(query.toString());
					
					if(CommonFunction.checkNull(amount).trim().equalsIgnoreCase(""))
						amount="0.00";
					Number totalRecAmt = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(amount)).trim());
					amount=myFormatter.format(totalRecAmt);
					
				}
				catch(Exception e)
				{e.printStackTrace();}
				finally
				{
					query = null;
					loanId=null;
					bPType=null;
				}
				return amount;
			}
			
			public String receiptNoCheckFlag() 
			{
				String flag=null;
				StringBuilder query = new StringBuilder();
				query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='RECEIPT_NO_CHECK_FLAG'");
				logger.info("Query for getting receiptNoCheckFlag from parameter_mst  : "+query.toString());
				try
				{
					flag = ConnectionDAO.singleReturn(query.toString());
					query=null;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return flag;
			}
			public ArrayList receiptPurposeList() {
				 
				ArrayList arrList = new ArrayList();
				ArrayList subList = new ArrayList();
				ArrayList mainList = new ArrayList();
				StringBuilder query = new StringBuilder(); 
				logger.info("In receiptPurposeList...");
				try{
					
				query.append("select value,description from generic_master where generic_key = 'RECEIPT_PURPOSE' and rec_status ='A' ");
				logger.info("In receiptPurposeList..."+query.toString());
				arrList = ConnectionDAO.sqlSelect(query.toString());
				int size=arrList.size();
				for(int i=0;i<size;i++){

					subList=(ArrayList)arrList.get(i);
						if(subList.size()>0){
			
								ReceiptMakerVO receiptVo = new ReceiptMakerVO();
								receiptVo.setReceiptPurposeValue((CommonFunction.checkNull(subList.get(0)).trim()));
								receiptVo.setReceiptPurposeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
								mainList.add(receiptVo);
						
						    }
				  }
				
					
				}catch(Exception e){
					logger.debug("IOException In downLoadErrorLog() ==>> "+e.getMessage());
				}
				finally
				{
					query = null;
					subList.clear();
					subList = null;
					arrList.clear();
					arrList = null;
				}
				
					
				return mainList;
			}
//change by sachin
			@Override
	public String checkReciptStatus(Object ob) {
				
			    String result="0";
			    ReceiptMakerVO vo =  (ReceiptMakerVO)ob;
				ArrayList queryList=new ArrayList ();
				StringBuilder existCount = new StringBuilder();
 			    
			  	
		try{		 
			  	   if(CommonFunction.checkNull(vo.getInstrumentID()).equalsIgnoreCase(""))
			  	   {
			  		 existCount.append("SELECT COUNT(1) FROM CR_INSTRUMENT_DTL where IFNULL(REC_STATUS,'')<>'X' AND RECIPT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptNo())).trim()+"' and INSTRUMENT_ID IS NOT NULL ");
			  	   }
			  	   else
			  	   {
			  		 existCount.append("SELECT COUNT(1) FROM CR_INSTRUMENT_DTL where IFNULL(REC_STATUS,'')<>'X' AND RECIPT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptNo())).trim()+"' and INSTRUMENT_ID IS NOT NULL and INSTRUMENT_ID<>"+CommonFunction.checkNull(vo.getInstrumentID()).trim());
			  		   
			  	   }
			  	 logger.info("IN checkReciptStatus  ### "+existCount.toString());
		  		 result=ConnectionDAO.singleReturn(existCount.toString());
		  		 
				  		
		   }
	  	  catch(Exception e){
					e.printStackTrace();
			 }finally  {
	  		   existCount = null;
	  		 ob=null;
	  		vo=null;
	  	   }
		return result;				
       }
//end by sachin			
	//change by Mradul
			public String checkReciptStatusFromInventory(Object ob) {
				
					String result="1";
					String inventoryFlag="";
					String checkAllBranch="";
					ReceiptMakerVO vo =  (ReceiptMakerVO)ob;
				  	StringBuilder existCount = new StringBuilder();
				  	StringBuilder inventoryFlagInMst = new StringBuilder();
				  	StringBuilder existInStationary = new StringBuilder();
				  	StringBuilder allBranch = new StringBuilder();
				  	StringBuilder checkUser = new StringBuilder();
				  	String checkUserAvailable="";
				  	String IssuingUser=vo.getMakerId();
				  	   
		 try{    					
						  	inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='INVENTORY_FLAG'");
					  		logger.info("IN checkParameterFlagFromInventory inventoryFlag ### "+inventoryFlagInMst.toString());
					  		inventoryFlag=ConnectionDAO.singleReturn(inventoryFlagInMst.toString());				  		
				  	
				   if(inventoryFlag.equalsIgnoreCase("Y") && !CommonFunction.checkNull(vo.getReceiptNo()).equalsIgnoreCase(""))
				  	    {
					  		allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='"+vo.getReceiptNo()+"' AND STATUS='A'");
					  		logger.info("IN checkAllBranch ### "+allBranch.toString());
					  		checkAllBranch=ConnectionDAO.singleReturn(allBranch.toString());
				  		
				  	if(checkAllBranch.equalsIgnoreCase("0"))
				  		{
					  		checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+vo.getReceiptNo()+"' AND IFNULL(ISSUING_USER,'')<>''");
					  		logger.info("IN checkInventory inventoryFlag ### "+checkUser.toString());
					  		checkUserAvailable=ConnectionDAO.singleReturn(checkUser.toString());	

				  	if(!(checkUserAvailable.equalsIgnoreCase("0")))
						{
				  			existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+vo.getReceiptNo()+"'  AND IFNULL(ISSUING_USER,'"+IssuingUser+"')='"+IssuingUser+"' ");
					  		logger.info("IN checkUserAvailability  in existInStationary ### "+existInStationary.toString());
					  		result=ConnectionDAO.singleReturn(existInStationary.toString());
			  	  }else{
				    	 	existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+vo.getReceiptNo()+"'  AND ISSUING_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDefaultBranch()).trim())+"'");
					  		logger.info("IN checkReciptStatusFromInventory  in existInStationary ### "+existInStationary.toString());
					  		result=ConnectionDAO.singleReturn(existInStationary.toString());
				  		 }
			  	       }else{
				  		result="1";
				  }}}catch(Exception e){
							e.printStackTrace();
					}finally{
				  		 existCount = null;
				  		ob=null;
				  		vo=null;
				  		inventoryFlagInMst=null;
				  		existInStationary=null;
				  		allBranch=null;
				  		checkUser=null;
				  		checkUserAvailable=null;
				  		IssuingUser=null;
				    }return result;
				}
//end by Mradul
			
		/*	public String checkReciptUpdateStatus(Object ob) {
				String result="";
				ReceiptMakerVO vo =  (ReceiptMakerVO)ob;
			  	StringBuilder existCount = new StringBuilder();
			    //mradul starts
			  	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
			  	String receiptAmountFinal=CommonFunction.checkNull(vo.getReceiptAmountNew()).trim();
			  	String allocationAmount=CommonFunction.checkNull(vo.getReceiptAmount()).trim();
			  	String instrumentAmount="";
			    double receiptAmountNew=0.0;
			    double instrumentAmountOld=0.0;
			    double allocationAmountNew=0.0;
			    double totalAmount=0.0;
			    
			  	   try{
//			  		   existCount.append("SELECT COUNT(1) FROM CR_INSTRUMENT_DTL where IFNULL(REC_STATUS,'')<>'X' AND  RECEIPT_AMOUNT_NEW =(select SUM(INSTRUMENT_AMOUNT) from CR_INSTRUMENT_DTL where RECIPT_NO='"+vo.getReceiptNo()+"')  and  RECIPT_NO='"+vo.getReceiptNo()+"' AND INSTRUMENT_ID<>'"+vo.getInstrumentID()+"' ");
//			  		   logger.info("IN checkReciptUpdateStatus  ### "+existCount.toString());
//			  	       result=ConnectionDAO.singleReturn(existCount.toString());
//			
//			  	     if(result.equalsIgnoreCase("0"))
//			  			{
				  			String instrumentAmountSum=" SELECT sum(IFNULL(instrument_amount,0)) instrument_amount from cr_instrument_dtl where IFNULL(REC_STATUS,'')<>'X'  AND INSTRUMENT_ID<>'"+vo.getInstrumentID()+"'";
				  			logger.info("In checkReciptUpdateStatus() method query is.. "+instrumentAmountSum);
				  			instrumentAmount=ConnectionDAO.singleReturn(instrumentAmountSum);				  			
//			  			}	 	
					  				         
					  if(instrumentAmount.equalsIgnoreCase(""))
					 	{
						  instrumentAmount="0"; 
					 }
//					  else{ 						
//						 instrumentAmount= CommonFunction.decimalNumberConvert(CommonFunction.checkNull(instrumentAmount)).toString();	
//					 }
					   	instrumentAmountOld=Double.parseDouble(instrumentAmount);
						    	
				   	 if(allocationAmount.equalsIgnoreCase(""))
				   	 	{
				   		 	allocationAmount="0";  
				   	 }else{
				   		 	allocationAmount= CommonFunction.decimalNumberConvert(CommonFunction.checkNull(allocationAmount)).toString();
				   	 	}					    	   
						   	allocationAmountNew=Double.parseDouble(allocationAmount);					    	
						    	   
				   	 if(receiptAmountFinal.equalsIgnoreCase(""))
				   	 	{
				   		 	receiptAmountFinal="0";  
				   	 }else{
				   		 	receiptAmountFinal= CommonFunction.decimalNumberConvert(CommonFunction.checkNull(receiptAmountFinal)).toString();				       
				   	 	}
						   	receiptAmountNew=Double.parseDouble(receiptAmountFinal);						    	
						    	
						   	totalAmount=(instrumentAmountOld+allocationAmountNew);
					   		logger.info("IN receiptAmountFinal ### "+ receiptAmountNew);
					   		logger.info("instrumentAmountOld### "+ instrumentAmountOld);
					   		logger.info("allocationAmountNew### "+ allocationAmountNew);
					   		logger.info("IN totalAmount1 ### "+totalAmount);
						   		
					if(totalAmount <= receiptAmountNew)
				   	  {
					   		result="0";
				   	  }else
					   		result="1";				  	
			  	       
			    } catch(Exception e){
						e.printStackTrace();
				}finally{
			  		 existCount = null;
			    }return result;
			}
			*/
public ArrayList getreceiptAmountData(ReceiptMakerVO receiptVO) {
				
	          ArrayList result=new ArrayList();
	          ArrayList mainList=new ArrayList();
			  	StringBuilder existCount = new StringBuilder();
	 try{
		  		   existCount.append("select RECEIPT_AMOUNT_NEW,(RECEIPT_AMOUNT_NEW-sum(instrument_amount)) instrument_amount from cr_instrument_dtl A  WHERE RECIPT_NO='"+receiptVO.getReceiptNo()+"'");
		  		   logger.info("IN getreceiptAmountData  ### "+existCount.toString());
		  		   result = ConnectionDAO.sqlSelect(existCount.toString());
		  		   int size=result.size();
		  		   logger.info("savedReceipt search Data size is...."+ size);

		  	for(int i=0;i<size;i++){
				
				  ArrayList data=(ArrayList)result.get(i);
				  

			  if(data.size()>0){
		
					 Number ReceiptAmountNew = 0;
					 Number ReceiptAmount = 0;
					 if(!CommonFunction.checkNull(data.get(0)).equalsIgnoreCase(""))
			    	  {
						 ReceiptAmountNew = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(data.get(0))).trim());
					 			  
			    	  }
					 receiptVO.setReceiptAmountNew(myFormatter.format(ReceiptAmountNew));
					 if(!CommonFunction.checkNull(data.get(1)).equalsIgnoreCase(""))
			    	  {
						 ReceiptAmount = CommonFunction.decimalNumberConvert((CommonFunction.checkNull(data.get(1))).trim());
					 			  
			    	  }
					 receiptVO.setReceiptAmount(myFormatter.format(ReceiptAmount));
					 
					 mainList.add(receiptVO);	
			  		}
			  data.clear();
			  data=null;
			    }
		  } catch(Exception e){
							e.printStackTrace();
						}
			  	   finally
			  	   {
			  		 existCount = null;
			  		receiptVO=null;
			  		result.clear();
			  		result=null;
			  	   }
						return mainList;
			}
	
		
		public ArrayList getBranchIdMicr(String depositBankAccount,String depositBankID,String depositIfscCode,String receiptMode){
			
			 ArrayList result=new ArrayList();
	          ArrayList mainList=new ArrayList();
	          ReceiptMakerVO receiptVO = new ReceiptMakerVO();
			  	StringBuilder query = new StringBuilder();
			  	try{
			  		query.append("select BANK_BRANCH_ID,BRANCH_MICR_CODE from com_bank_accounts_m ");
			  		query.append("where REC_STATUS='A' AND ACCOUNT_TYPE='"+CommonFunction.checkNull(receiptMode)+"'");
			  		query.append(" and BANK_ID='"+CommonFunction.checkNull(depositBankID)+"' AND BANK_ACCOUNT='"+CommonFunction.checkNull(depositBankAccount)+"'");
			  		//query.append(" and BRANCH_IFCS_CODE='"+CommonFunction.checkNull(depositIfscCode)+"'");
		  		   logger.info("IN getBranchIdMicr  ### "+query.toString());
		  		   result = ConnectionDAO.sqlSelect(query.toString());
		  		   int size=result.size();
		  		   logger.info("getBranchIdMicr size...."+ size);

		  	for(int i=0;i<size;i++){
				
				  ArrayList data=(ArrayList)result.get(i);
				  

			  if(data.size()>0){
		
					 receiptVO.setDepositBranchID((CommonFunction.checkNull(data.get(0))).trim());
					 receiptVO.setDepositMicr((CommonFunction.checkNull(data.get(1))).trim());
					 mainList.add(receiptVO);	
			  		}
			  data.clear();
			  data=null;
			    }
		  } catch(Exception e){
							e.printStackTrace();
						}
			  	   finally
			  	   {
			  		 query = null;
			  		depositBankAccount=null;
			  		depositBankID=null;
			  		depositIfscCode=null;
			  		receiptMode=null;
			  		result.clear();
			  		result=null;
			  	   }
						return mainList;
		}

		@Override
		public String updateDepositChequeFromReceipt(ReceiptMakerVO receiptVO) {
			
			ArrayList<Object> in =new ArrayList<Object>();
       		ArrayList<Object> out =new ArrayList<Object>();
       		ArrayList outMessages = new ArrayList();
       		
       		String s1=null;
       		String s2=null;
       		String procval="";
			ArrayList qryList =  new ArrayList();
			boolean status1=false;
			StringBuffer bufInsSql =null;
			PrepStmtObject insertPrepStmtObject = null;	
			StringBuilder instrumentBatchId = new StringBuilder(); 
			StringBuilder instrumentBatchIdValue = new StringBuilder(); 
			//String instrumentBatchId="";
			//String instrumentBatchIdValue="";
			int updateBatchID;
			ArrayList updateBatchIDforQry= null;
			ArrayList updateBatchIDformainQry=null;
			StringBuilder updateBatchIDforQryString = new StringBuilder(); 
			StringBuilder updateMainQuery = new StringBuilder();
			//String updateBatchIDforQryString="";
			//String updateMainQuery="";
			String reasonID="";
			String pdcInstrum="";
//			CallableStatement cst=null;
			logger.info("In updateDepositChequeFromReceipt...Dao Impl.");
			 boolean procstatus = false;
			
			try{
				instrumentBatchId.append("select function_sequence('CHEQUE_STATUS')");
				instrumentBatchIdValue.append(ConnectionDAO.singleReturn(instrumentBatchId.toString()));		  
				updateBatchID = Integer.parseInt(instrumentBatchIdValue.toString());
						
				 bufInsSql =	new StringBuffer();
				 insertPrepStmtObject = new PrepStmtObject();
				//select starts
				 ArrayList result=new ArrayList();
		          ArrayList mainList=new ArrayList();
		         
				  	StringBuilder query = new StringBuilder();
				  	try{
				  		query.append("select INSTRUMENT_NO,INSTRUMENT_DATE,INSTRUMENT_AMOUNT,TDS_AMOUNT,DEPOSIT_BANK_ID,DEPOSIT_BRANCH_ID,DEPOSIT_MICR_CODE,DEPOSIT_IFSC_CODE,DEPOSIT_BANK_ACCOUNT,RECEIVED_DATE");
				  		query.append(" from cr_instrument_dtl where INSTRUMENT_ID='"+CommonFunction.checkNull(receiptVO.getInstrumentID())+"'");
				  	
			  		   logger.info("IN get instrument dtl  ### "+query.toString());
			  		   result = ConnectionDAO.sqlSelect(query.toString());
			  		   int size=result.size();
			  		   logger.info("cr_instrument_dtl size...."+ size);

			  	for(int i=0;i<size;i++){
					
					  ArrayList data=(ArrayList)result.get(i);
					  

				  if(data.size()>0){
						 receiptVO.setInstrumentNumber((CommonFunction.checkNull(data.get(0))).trim());//INSTRUMENT_NO,
						 receiptVO.setInstrumentDate((CommonFunction.checkNull(data.get(1))).trim());//INSTRUMENT_DATE,
						 receiptVO.setReceiptAmount((CommonFunction.checkNull(data.get(2))).trim());//INSTRUMENT_AMOUNT,
						 receiptVO.setTdsAmount((CommonFunction.checkNull(data.get(3))).trim());//TDS_AMOUNT,
						 receiptVO.setDepositBankID((CommonFunction.checkNull(data.get(4))).trim());//DEPOSIT_BANK_ID,
						 receiptVO.setDepositBranchID((CommonFunction.checkNull(data.get(5))).trim());//DEPOSIT_BRANCH_ID,
						 receiptVO.setDepositMicr((CommonFunction.checkNull(data.get(6))).trim());//DEPOSIT_MICR_CODE,
						 receiptVO.setDepositIfscCode((CommonFunction.checkNull(data.get(7))).trim());//DEPOSIT_IFSC_CODE,
						 receiptVO.setDepositBankAccount((CommonFunction.checkNull(data.get(8))).trim());//DEPOSIT_BANK_ACCOUNT,
						 receiptVO.setReceiptDate((CommonFunction.checkNull(data.get(9))).trim());//RECEIVED_DATE
						 //mainList.add(receiptVO);	
				  		}
				  data.clear();
				  data=null;
				    }
				 //select end
				  
				 
				 bufInsSql.append("insert into cr_instrument_update_dtl(INSTRUMENT_BATCH_ID,INSTRUMENT_ID,INSTRUMENT_NO,INSTRUMENT_DATE," +
				 		" INSTRUMENT_AMOUNT,TDS_AMOUNT,REASON_ID,DEPOSIT_BANK_ID,DEPOSIT_BRANCH_ID,DEPOSIT_MICR_CODE,DEPOSIT_IFSC_CODE," +
				 		"DEPOSIT_BANK_ACCOUNT," +
				 		" PDC_INSTRUMENT_ID,CURRENT_STATUS,NEXT_STATUS,REC_STATUS,PDC_FLAG,INSTRUMENT_TYPE,VALUE_DATE,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				 
						 bufInsSql.append(" values ( ");
						 bufInsSql.append(" ?," );//1
						 bufInsSql.append(" ?," );//2
						 bufInsSql.append(" ?," );//3
						 bufInsSql.append(" ?,");//4
						 bufInsSql.append(" ?," );//5
						 bufInsSql.append(" ?," );//6
						 bufInsSql.append(" ?," );//7
						 bufInsSql.append(" ?," );//8
						 bufInsSql.append(" ?," );//9
						 bufInsSql.append(" ?," );//10
						 bufInsSql.append(" ?," );//11
						 bufInsSql.append(" ?," );//12
						 bufInsSql.append(" ?," );//13
						 
						 bufInsSql.append(" ?," );//14
						 bufInsSql.append(" 'D'," );//15
			
						
						 bufInsSql.append(" 'A'," );//16
						 bufInsSql.append(" ?," );//17
						 bufInsSql.append(" ?," );//18
//Start by Prashant 	
						 bufInsSql.append(" ?,");//19
						 bufInsSql.append(" ?," );//20
						 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//21
						 bufInsSql.append(" ?," );//22
						 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" );//23
//End by Prashant 								 
	
						 if(CommonFunction.checkNull(updateBatchID).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addInt(updateBatchID);
					 
						if(CommonFunction.checkNull(receiptVO.getInstrumentID()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
						
						if(CommonFunction.checkNull(receiptVO.getInstrumentNumber()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentNumber()).trim()));
							
//						else
//							insertPrepStmtObject.addString((CommonFunction.checkNull(checkedinstrumentNoList[i]).trim()));
//						logger.info("checkeddateList()))))))"+checkeddateList.length);
						if(CommonFunction.checkNull(receiptVO.getInstrumentDate()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentDate()).trim()));
						
					
						if((CommonFunction.checkNull(receiptVO.getReceiptAmount())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0");
						else
							insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim())).toString());
						
						if((CommonFunction.checkNull(receiptVO.getTdsAmount())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0");
						else
							insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(receiptVO.getTdsAmount()).trim())).toString());
						
						if(CommonFunction.checkNull(reasonID).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(reasonID).trim()));
						
						
						
						if(CommonFunction.checkNull(receiptVO.getDepositBankID()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankID()).trim()));
						
						if(CommonFunction.checkNull(receiptVO.getDepositBranchID()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBranchID()).trim()));
						
						if(CommonFunction.checkNull(receiptVO.getDepositMicr()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositMicr()).trim()));
						
						
						
						if(CommonFunction.checkNull(receiptVO.getDepositIfscCode()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositIfscCode()).trim()));
						
						
						if(CommonFunction.checkNull(receiptVO.getDepositBankAccount()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getDepositBankAccount()).trim()));
						
							
						pdcInstrum = "";
		
						if(CommonFunction.checkNull(pdcInstrum).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(pdcInstrum).trim()));
						
						insertPrepStmtObject.addString((CommonFunction.checkNull("A").trim()));
						
						insertPrepStmtObject.addString((CommonFunction.checkNull("N").trim()));
						
		                 insertPrepStmtObject.addString((CommonFunction.checkNull("R").trim())); //instrumentType
//Start by Prashant 							
						if(CommonFunction.checkNull(receiptVO.getReceiptDate()).equalsIgnoreCase("")) //value date
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptDate()).trim()));
						
//End by Prashant 							
																			
						
						if(CommonFunction.checkNull(receiptVO.getMakerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getMakerId()).trim()));
						
						if(CommonFunction.checkNull(receiptVO.getBusinessDate()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getBusinessDate()).trim()));
					//Start By Prashant	
						if(CommonFunction.checkNull(receiptVO.getMakerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getMakerId()).trim()));
						
						if(CommonFunction.checkNull(receiptVO.getBusinessDate()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getBusinessDate()).trim()));
					 // End By Prashant
							
							insertPrepStmtObject.setSql(bufInsSql.toString());
							qryList.add(insertPrepStmtObject);
							
							
			
				logger.info("In printquery.................."+insertPrepStmtObject.printQuery());
				status1=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		        logger.info("In updateInstrumentDetail......................"+status1);
		        if(status1)
		        {
		        	try
		        	{
		        		in.add(receiptVO.getCompanyId());
		        		in.add(updateBatchID);
		        		String date=CommonFunction.changeFormat(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
		        		in.add(date);
		        		in.add((CommonFunction.checkNull(receiptVO.getMakerId())));
		        		in.add("SINGLE");
		        		out.add(s1);
		        		out.add(s2);
		        		logger.info("Update_Cheque_Status("+in.toString()+","+out.toString()+")"); 
		        		outMessages=(ArrayList) ConnectionDAO.callSP("Update_Cheque_Status",in,out);
		        		s1=CommonFunction.checkNull(outMessages.get(0));
		        		s2=CommonFunction.checkNull(outMessages.get(1));
		        		logger.info("Update_Cheque_Status s1: "+s1); 
		        		logger.info("Update_Cheque_Status s2: "+s2);
		        		logger.info("After proc call...."); 
		        		if(CommonFunction.checkNull(s1).equalsIgnoreCase("S"))
		        		{
		        			procval=s1;
		        		}
		        		else
		        		{
		        			procval=s2;
		        			logger.info("Proc Exception----"+s2);
		        			StringBuffer  updateQuery =	new StringBuffer();
		        			PrepStmtObject instObject = new PrepStmtObject();			 				  
		        			updateQuery.append( "UPDATE CR_INSTRUMENT_UPDATE_DTL  SET REC_STATUS = 'E',ERROR_MSG = '"+procval+"' WHERE INSTRUMENT_BATCH_ID = '"+updateBatchID+"'");
		        			instObject.setSql(updateQuery.toString());
		        			logger.info("Query when procedure return error   :   "+instObject.printQuery());
		        			ArrayList list =  new ArrayList();
		        			list.add(instObject);
		        			boolean flg=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);	
		        			if(flg)
		        				logger.info("Query when procedure return error   Status   :   "+flg);
		        		}
		        		
		        	}
		        	catch (Exception e) 
		        	{
						procval="Exception occur During Procedure call,Please contact administrator....";
	        			StringBuffer  updateQuery =	new StringBuffer();
	        			PrepStmtObject instObject = new PrepStmtObject();			 				  
	        			updateQuery.append( "UPDATE CR_INSTRUMENT_UPDATE_DTL  SET REC_STATUS = 'E',ERROR_MSG ='Exception occur During Procedure call' WHERE INSTRUMENT_BATCH_ID = '"+updateBatchID+"'");
	        			instObject.setSql(updateQuery.toString());
	        			logger.info("Query when procedure return exception   :   "+instObject.printQuery());
	        			ArrayList list =  new ArrayList();
	        			list.add(instObject);
	        			boolean flg=ConnectionDAO.sqlInsUpdDeletePrepStmt(list);	
	        			if(flg)
	        				logger.info("Query when procedure return error   Status   :   "+flg);
	        			updateQuery=null;
	        			instObject=null;
					}
		  	  	}
		        pdcInstrum=null;
			}catch (Exception e) {
				
				e.printStackTrace();
			}
			finally
			{
				in = null;
				out = null;
				outMessages = null;
				qryList = null;
				s1 = null;
				s2 = null;
				instrumentBatchId = null;
				instrumentBatchIdValue = null;
				updateBatchIDforQryString = null;
				updateMainQuery = null;
				receiptVO=null;
				bufInsSql=null;
				insertPrepStmtObject=null;
				result.clear();
				result=null;
				mainList.clear();
				mainList=null;
				query=null;
			}
	
			}catch(Exception e){
				e.printStackTrace();
			}
			return procval;
}
		public ArrayList getchargesDetailOnReceipt(ReceiptMakerVO obj) 
		{
			ArrayList list =new ArrayList();	
			try
			{			
				 ReceiptMakerVO vo =null;
				 StringBuilder query=new StringBuilder();
				 query.append(" SELECT B.CHARGE_DESC,A.CHARGE_CODE,A.ADVICE_AMOUNT,A.ADJUSTED_AMOUNT,A.AMOUNT_IN_PROCESS,A.BALANNCE_AMOUNT,A.ALLOCATED_AMOUNT,A.ALLOCATED_TDS" );
				 query.append(" FROM CR_GROSS_ALLOCATION_DTL A ");
				 query.append(" INNER JOIN COM_CHARGE_CODE_M B on A.CHARGE_CODE=B.CHARGE_CODE " );
				 query.append(" WHERE INSTRUMENT_ID='"+obj.getInstrumentID()+"' AND OTHER_CHARGE_FLAG='N' ");
				
				logger.info("getchargesDetailOnReceipt in receipt  query "+query.toString());
				ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getchargesDetailOnReceipt in receipt  size "+charges.size());
				
				query=null;
				int sizze=charges.size();
				for(int i=0;i<sizze;i++){
			
				ArrayList subcharges=(ArrayList)charges.get(i);
				if(subcharges.size()>0)	
				{
					vo = new ReceiptMakerVO();
					vo.setAllocChargeDesc((CommonFunction.checkNull(subcharges.get(0))).trim());
					vo.setAllocChargeCode((CommonFunction.checkNull(subcharges.get(1))).trim());
					vo.setAllocAdviceAmount((CommonFunction.checkNull(subcharges.get(2))).trim());
					vo.setAllocAdjustedAmount((CommonFunction.checkNull(subcharges.get(3))).trim());
					vo.setAllocAmountInProcess((CommonFunction.checkNull(subcharges.get(4))).trim());
					vo.setAllocBalAmount((CommonFunction.checkNull(subcharges.get(5))).trim());		
					vo.setAllocAmount((CommonFunction.checkNull(subcharges.get(6))).trim());
					vo.setAllocTdsAllocatedAmount((CommonFunction.checkNull(subcharges.get(7))).trim());
					list.add(vo);
				 }
				}
				vo=null;
				charges.clear();
				charges=null;
			}
			catch (Exception e) 
			{e.printStackTrace();}
			finally{
				obj=null;
				
			}
			return list;
		}
		@Override//tomorrow start from here
		
		public boolean saveAllocationReceipt(ReceiptMakerVO vo,String recStatus) {
			
			logger.info("saveAllocationReceipt in receipt  query instrument id "+vo.getInstrumentID()+" loan Id: "+vo.getLbxLoanNoHID()+" BP Type: "+vo.getLbxBPType());
		
			ArrayList list =new ArrayList();	
		    StringBuilder deleteQuery = new StringBuilder();
		    ArrayList deleteList =new ArrayList();
		    
		    String lbxAllocChargeStringArray[]=vo.getLbxAllocChargeStringArray();
		    String allocOtherBalAmountStringArray[] =vo.getAllocOtherBalAmountStringArray();
		    String allocOtherAmountStringArray[]=vo.getAllocOtherAmountStringArray();
		    String allocTdsOtherAllAmtStringArray[]=vo.getAllocTdsOtherAllAmtStringArray();
		    
		    String allocAmountStringArray[] = vo.getAllocAmountStringArray();
		    String allocTdsAllocatedAmountStringArray[] = vo.getAllocTdsAllocatedAmountStringArray();
		    boolean status=false;
			try
			{			
				StringBuilder query=new StringBuilder();
				deleteQuery.append ("delete from CR_GROSS_ALLOCATION_DTL where instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' ");
				deleteList.add(deleteQuery.toString());
				logger.info("delete()     query------------------" + deleteQuery.toString());
				ConnectionDAO.sqlInsUpdDelete(deleteList);
				String installmentTypeQuery="SELECT LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='"+vo.getLbxLoanNoHID()+"' ";
				String installmentType=ConnectionDAO.singleReturn(installmentTypeQuery);
				if(CommonFunction.checkNull(installmentType).equalsIgnoreCase("I") )
				{
					 if( vo.getLbxBPType().equalsIgnoreCase("CS"))
					 { 
						 query.append(" (SELECT  DISTINCT CHARGE_DESC,A.CHARGE_CODE,IFNULL(SUM(ADVICE_AMOUNT),0) AS ADVICE_AMOUNT,IFNULL(SUM(TXN_ADJUSTED_AMOUNT),0) AS TXN_ADJUSTED_AMOUNT, " ); 
						 query.append(" IFNULL(SUM(AMOUNT_IN_PROCESS),0) AS AMOUNT_IN_PROCESS,IFNULL(SUM(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0) as  BALANCE_AMOUNT " );
						 query.append(" FROM COM_CHARGE_CODE_M A ");
						 query.append("	INNER JOIN CR_RECEIPT_CHARGE_CODE_M ON CR_RECEIPT_CHARGE_CODE_M.CHARGE_CODE=A.CHARGE_CODE ");
						 
						 query.append("	LEFT JOIN CR_TXNADVICE_DTL B ON IF(A.CHARGE_CODE=7,B.TXN_TYPE='BIL' AND B.ADVICE_TYPE='R' ,A.CHARGE_CODE=B.CHARGE_CODE_ID AND B.ADVICE_TYPE='R' )  ");
						 query.append("	 AND B.BP_TYPE='CS' and  B.LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+CommonFunction.checkNull(vo.getBusinessDate())+"', '"+dateFormat+"')  ");
						 query.append("	 GROUP BY cr_receipt_charge_code_m.SEQ_NO,CHARGE_CODE,CHARGE_DESC)  ");
						 query.append(" UNION ALL  ");
						 query.append(" (select  distinct CHARGE_DESC,A.CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT, ");
						 query.append(" IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS, IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT ");
						 query.append(" from com_charge_code_m a  ");
						 query.append(" left join cr_receipt_charge_code_m on CR_RECEIPT_CHARGE_CODE_M.Charge_Code=A.CHARGE_CODE ");
						 query.append(" inner join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID AND B.BP_TYPE='CS' and  b.loan_id='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+CommonFunction.checkNull(vo.getBusinessDate())+"', '"+dateFormat+"') ");
						 query.append(" WHERE cr_receipt_charge_code_m.Charge_Code is null and (TXN_TYPE='BIL' and ADVICE_TYPE='R')=0   ");
						 query.append(" and B.ADVICE_TYPE='R' AND (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 ");
						 query.append(" GROUP BY A.CHARGE_CODE,CHARGE_DESC ) ");
						 
					 }
					 else
					 {
					
						 query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT from com_charge_code_m a " );
						 query.append(" inner join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID AND b.CHARGE_CODE_ID NOT IN (SELECT DISTINCT ifnull(CHARGE_CODE,'') FROM CR_LOAN_CHARGE_PLAN_DTL WHERE LOAN_ID='"+vo.getLbxLoanNoHID()+"'  AND REC_STATUS='A') AND B.BP_TYPE='"+vo.getLbxBPType()+"' and  b.loan_id='"+vo.getLbxLoanNoHID()+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+vo.getBusinessDate()+"', '"+dateFormat+"') " );
						 query.append(" where CHARGE_CODE not in (7,109,110,99,98,6) and B.advice_type='R' and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 ");
						 query.append(" group by CHARGE_CODE,CHARGE_DESC) ");
					
					 }
					
				}
				else
				{
					if( vo.getLbxBPType().equalsIgnoreCase("CS"))
					 {
						 query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT from com_charge_code_m a " );
						 query.append(" left join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID and B.advice_type='R' AND B.BP_TYPE='"+vo.getLbxBPType()+"' and  b.loan_id='"+vo.getLbxLoanNoHID()+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+vo.getBusinessDate()+"', '"+dateFormat+"')  " );
						 query.append(" where CHARGE_CODE IN (110,99,9) ");
						 query.append(" group by IF(CHARGE_CODE=7,1,IF(CHARGE_CODE=109,2,IF(CHARGE_CODE=110,3,IF(CHARGE_CODE=99,4,IF(CHARGE_CODE=98,5,6))))),CHARGE_CODE,CHARGE_DESC) ");
						 query.append(" UNION ALL ");
						 query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT from com_charge_code_m a " );
						 query.append(" inner join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID AND b.CHARGE_CODE_ID NOT IN (SELECT DISTINCT ifnull(CHARGE_CODE,'') FROM CR_LOAN_CHARGE_PLAN_DTL WHERE LOAN_ID='"+vo.getLbxLoanNoHID()+"'  AND REC_STATUS='A') AND B.BP_TYPE='"+vo.getLbxBPType()+"' and  b.loan_id='"+vo.getLbxLoanNoHID()+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+vo.getBusinessDate()+"', '"+dateFormat+"') " );
						 query.append(" where CHARGE_CODE not in (7,109,110,99,98,9,6)  and B.advice_type='R' and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 ");
						 query.append(" group by CHARGE_CODE,CHARGE_DESC) ");
					
					}
					else
					{
						 query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT from com_charge_code_m a " );
						 query.append(" inner join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID AND b.CHARGE_CODE_ID NOT IN (SELECT DISTINCT ifnull(CHARGE_CODE,'') FROM CR_LOAN_CHARGE_PLAN_DTL WHERE LOAN_ID='"+vo.getLbxLoanNoHID()+"'  AND REC_STATUS='A') AND B.BP_TYPE='"+vo.getLbxBPType()+"' and  b.loan_id='"+vo.getLbxLoanNoHID()+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+vo.getBusinessDate()+"', '"+dateFormat+"')  " );
						 query.append(" where CHARGE_CODE not in (7,109,110,99,98,9,6)  and B.advice_type='R' and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 ");
						 query.append(" group by CHARGE_CODE,CHARGE_DESC) ");
			
					}
				}
		
				logger.info("saveAllocationReceipt in receipt  query "+query.toString());
				ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
				
				ArrayList qryList = new ArrayList();
				
				int sizee=charges.size();
				for(int i=0;i<sizee;i++){
			
				ArrayList subcharges=(ArrayList)charges.get(i);
				if(subcharges.size()>0)	
				{
					
					 StringBuffer bufInsSql =	new StringBuffer();
					 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
					    bufInsSql.append("insert into CR_GROSS_ALLOCATION_DTL (INSTRUMENT_ID,SEQ_NO,TXNTYPE,TXNID,CHARGE_TYPE,CHARGE_CODE,ADVICE_AMOUNT,ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,BALANNCE_AMOUNT,ALLOCATED_AMOUNT,ALLOCATED_TDS,OTHER_CHARGE_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE ) ");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?," );//INSTRUMENT_ID
						bufInsSql.append(" ?," ); //SEQ_NO
						bufInsSql.append(" 'LIM'," ); //TXNTYPE
						bufInsSql.append(" ?," ); //TXNID
						bufInsSql.append(" ?," ); //CHARGE_TYPE
						bufInsSql.append(" ?," ); //CHARGE_CODE
						bufInsSql.append(" ?," ); //ADVICE_AMOUNT
						bufInsSql.append(" ?," ); //ADJUSTED_AMOUNT
						bufInsSql.append(" ?," ); //AMOUNT_IN_PROCESS
						bufInsSql.append(" ?," ); //BALANNCE_AMOUNT
						bufInsSql.append(" ?," ); //ALLOCATED_AMOUNT
						bufInsSql.append(" ?," ); //ALLOCATED_TDS
						bufInsSql.append(" ?," );//OTHER_CHARGE_FLAG
						bufInsSql.append(" ?," ); //REC_STATUS
						bufInsSql.append(" ?," ); //MAKER_ID
		
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " ); //MAKER_DATE
					    bufInsSql.append(" ?," ); //AUTHOR_ID
					
					    bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ) " ); //AUTHOR_DATE
						
						if((CommonFunction.checkNull(vo.getInstrumentID())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getInstrumentID()).trim()));
						
						insertPrepStmtObject.addInt(i+1);
						if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
						
						insertPrepStmtObject.addString("R");
						
						if(CommonFunction.checkNull(subcharges.get(1)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(subcharges.get(1)).trim()));
						
						if(CommonFunction.checkNull(subcharges.get(2)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0.00");
						else
							insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subcharges.get(2)).trim())).toString());
						
						if(CommonFunction.checkNull(subcharges.get(3)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0.00");
						else
							insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subcharges.get(3)).trim())).toString());
						
						if(CommonFunction.checkNull(subcharges.get(4)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0.00");
						else
							insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subcharges.get(4)).trim())).toString());
						
						
						if(CommonFunction.checkNull(subcharges.get(5)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0.00");
						else
							insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(subcharges.get(5)).trim())).toString());
						
						
						if(allocAmountStringArray.length>0 && i<allocAmountStringArray.length)
						{
							if(CommonFunction.checkNull(allocAmountStringArray[i]).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addString("0.00");
							else
								insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocAmountStringArray[i]).trim())).toString());
					
						}
						else
						{
							insertPrepStmtObject.addString("0.00");
						}
						
						
						if(allocTdsAllocatedAmountStringArray.length>0 && i<allocTdsAllocatedAmountStringArray.length)
						{
							if(CommonFunction.checkNull(allocTdsAllocatedAmountStringArray[i]).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addString("0.00");
							else
								insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocTdsAllocatedAmountStringArray[i]).trim())).toString());
					
						}
						else
						{
							insertPrepStmtObject.addString("0.00");
						}
						
						insertPrepStmtObject.addString("N");  
						//insertPrepStmtObject.addString("P");
						if((CommonFunction.checkNull(recStatus).trim().equalsIgnoreCase("")))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(recStatus).trim()));
						
								
						if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
						
						if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getBusinessDate()).trim()));
						
						if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
						
						if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getBusinessDate()).trim()));
						
					
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN saveAllocationReceipt() insert cr_instrument_dtl query1 ### "+insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
						insertPrepStmtObject=null;
						bufInsSql=null;
					
				    }
				}
				if(lbxAllocChargeStringArray.length>0)
				{
					logger.info("First Other Charge Code: "+lbxAllocChargeStringArray[0]);
					
					int k=charges.size();
					
				     for(int j=0;j<lbxAllocChargeStringArray.length;j++)
				     {	
				    	 if(!CommonFunction.checkNull(lbxAllocChargeStringArray[j]).equalsIgnoreCase(""))
							{
						       StringBuffer bufInsSql1 =	new StringBuffer();
						       PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
						    bufInsSql1.append("insert into CR_GROSS_ALLOCATION_DTL (INSTRUMENT_ID,SEQ_NO,TXNTYPE,TXNID,CHARGE_TYPE,CHARGE_CODE,ADVICE_AMOUNT,ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,BALANNCE_AMOUNT,ALLOCATED_AMOUNT,ALLOCATED_TDS,OTHER_CHARGE_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE ) ");
							bufInsSql1.append(" values ( ");
							bufInsSql1.append(" ?," );//INSTRUMENT_ID
							bufInsSql1.append(" ?," ); //SEQ_NO
							bufInsSql1.append(" 'LIM'," ); //TXNTYPE
							bufInsSql1.append(" ?," ); //TXNID
							bufInsSql1.append(" ?," ); //CHARGE_TYPE
							bufInsSql1.append(" ?," ); //CHARGE_CODE
							bufInsSql1.append(" ?," ); //ADVICE_AMOUNT
							bufInsSql1.append(" ?," ); //ADJUSTED_AMOUNT
							bufInsSql1.append(" ?," ); //AMOUNT_IN_PROCESS
							bufInsSql1.append(" ?," ); //BALANNCE_AMOUNT
							bufInsSql1.append(" ?," ); //ALLOCATED_AMOUNT
							bufInsSql1.append(" ?," ); //ALLOCATED_TDS
							bufInsSql1.append(" ?," );//OTHER_CHARGE_FLAG
							bufInsSql1.append(" ?," ); //REC_STATUS
							bufInsSql1.append(" ?," ); //MAKER_ID
			
							bufInsSql1.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " ); //MAKER_DATE
						    bufInsSql1.append(" ?," ); //AUTHOR_ID
						
						    bufInsSql1.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ) " ); //AUTHOR_DATE
							
							if((CommonFunction.checkNull(vo.getInstrumentID())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getInstrumentID()).trim()));
							
						
							insertPrepStmtObject1.addInt(k+j+1);
								
							if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
							
							insertPrepStmtObject1.addString("R");  
							
							if(lbxAllocChargeStringArray.length>0 && j<lbxAllocChargeStringArray.length)
							{
								if(CommonFunction.checkNull(lbxAllocChargeStringArray[j]).trim().equalsIgnoreCase(""))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString(CommonFunction.checkNull(lbxAllocChargeStringArray[j]).trim().toString());
							}
							else
								insertPrepStmtObject1.addNull();
							
							insertPrepStmtObject1.addString("0.00");
							insertPrepStmtObject1.addString("0.00");
							insertPrepStmtObject1.addString("0.00");
							
							
								
								if(allocOtherBalAmountStringArray.length>0 && j<allocOtherBalAmountStringArray.length)
								{
									if(CommonFunction.checkNull(allocOtherBalAmountStringArray[j]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject1.addString("0.00");
									else
										insertPrepStmtObject1.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocOtherBalAmountStringArray[j]).trim())).toString());
								}
								else
									insertPrepStmtObject1.addString("0.00");
														
							
								if(allocOtherAmountStringArray.length>0 && j<allocOtherAmountStringArray.length)
								{
									if(CommonFunction.checkNull(allocOtherAmountStringArray[j]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject1.addString("0.00");
									else
										insertPrepStmtObject1.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocOtherAmountStringArray[j]).trim())).toString());
								}
								else
									insertPrepStmtObject1.addString("0.00");
								
								if(allocTdsOtherAllAmtStringArray.length>0 && j<allocTdsOtherAllAmtStringArray.length)
								{
									if(CommonFunction.checkNull(allocTdsOtherAllAmtStringArray[j]).trim().equalsIgnoreCase(""))
										insertPrepStmtObject1.addString("0.00");
									else
										insertPrepStmtObject1.addString(CommonFunction.decimalNumberConvert((CommonFunction.checkNull(allocTdsOtherAllAmtStringArray[j]).trim())).toString());
								}
								else
									insertPrepStmtObject1.addString("0.00");
							
								insertPrepStmtObject1.addString("Y");  
								//insertPrepStmtObject1.addString("P");
								if((CommonFunction.checkNull(recStatus).trim().equalsIgnoreCase("")))
									insertPrepStmtObject1.addNull();
								else
									insertPrepStmtObject1.addString((CommonFunction.checkNull(recStatus).trim()));
							
									
							if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
							
							if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getBusinessDate()).trim()));
							
							if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
							
							if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((CommonFunction.checkNull(vo.getBusinessDate()).trim()));
						
							insertPrepStmtObject1.setSql(bufInsSql1.toString());
							logger.info("IN saveAllocationReceipt() insert cr_instrument_dtl query1 ### "+insertPrepStmtObject1.printQuery());
							qryList.add(insertPrepStmtObject1);
							bufInsSql1=null;
							insertPrepStmtObject1=null;
				        }
					 
					 }
				  
			    }
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				query=null;
				installmentTypeQuery=null;
				installmentType=null;
				charges=null;
				qryList=null;
			
			}
			catch (Exception e) 
			{e.printStackTrace();}
			
			finally{
				lbxAllocChargeStringArray=null;
				allocOtherBalAmountStringArray=null;
				allocOtherAmountStringArray=null;
				allocTdsOtherAllAmtStringArray=null;
				allocAmountStringArray=null;
				allocTdsAllocatedAmountStringArray=null;
				
				deleteQuery=null;
				vo=null;
				recStatus=null;
			}
		
			return status;
		}
		
		public ArrayList getOtherAddChargesDetailOnReceipt(ReceiptMakerVO obj) 
		{
			ArrayList list =new ArrayList();	
			try
			{			
				ReceiptMakerVO vo =null;
				 StringBuilder query=new StringBuilder();
				 query.append(" SELECT B.CHARGE_DESC,A.CHARGE_CODE,A.ADVICE_AMOUNT,A.ADJUSTED_AMOUNT,A.AMOUNT_IN_PROCESS,A.BALANNCE_AMOUNT,A.ALLOCATED_AMOUNT,A.ALLOCATED_TDS" );
				 query.append(" FROM CR_GROSS_ALLOCATION_DTL A ");
				 query.append(" INNER JOIN COM_CHARGE_CODE_M B on A.CHARGE_CODE=B.CHARGE_CODE " );
				 query.append(" WHERE INSTRUMENT_ID='"+CommonFunction.checkNull(obj.getInstrumentID())+"' AND OTHER_CHARGE_FLAG='Y' ");
				
				logger.info("getchargesDetailOnReceipt in receipt  query "+query.toString());
				ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getchargesDetailOnReceipt in receipt  size "+charges.size());
				
				query=null;
				
				for(int i=0;i<charges.size();i++){
			
				ArrayList subcharges=(ArrayList)charges.get(i);
				if(subcharges.size()>0)	
				{
					vo = new ReceiptMakerVO();
					vo.setOtherAddChargeDesc((CommonFunction.checkNull(subcharges.get(0))).trim());
					vo.setOtherAddChargeCode((CommonFunction.checkNull(subcharges.get(1))).trim());
//					vo.setOtherAddBalanceAmount((CommonFunction.checkNull(subcharges.get(2))).trim());
//					vo.setAllocAdjustedAmount((CommonFunction.checkNull(subcharges.get(3))).trim());
//					vo.setAllocAmountInProcess((CommonFunction.checkNull(subcharges.get(4))).trim());
					vo.setOtherAddBalanceAmount((CommonFunction.checkNull(subcharges.get(5))).trim());		
					vo.setOtherAddAllocateAmount((CommonFunction.checkNull(subcharges.get(6))).trim());
					vo.setOtherAddTDSAmount((CommonFunction.checkNull(subcharges.get(7))).trim());
					list.add(vo);
				 }
				subcharges=null;
				}
				
			}
			catch (Exception e) 
			{e.printStackTrace();}
			return list;
		}
		
		public String saveAuthorAllocationReceipt(ReceiptMakerVO receiptVO) {
			 
			String procval=null;
			 try{
			    boolean status=false;
			    ArrayList<Object> in1 =new ArrayList<Object>();
				ArrayList<Object> out1 =new ArrayList<Object>();
				ArrayList outMessages1 = new ArrayList();
				StringBuilder s11 = new StringBuilder();
				StringBuilder s22 = new StringBuilder();
				
				in1.add(((CommonFunction.checkNull(receiptVO.getInstrumentID().trim()))));	
				in1.add(((CommonFunction.checkNull(receiptVO.getDecision().trim()))));
				in1.add(CommonFunction.checkNull(receiptVO.getComments()).trim());
				in1.add(CommonFunction.checkNull(receiptVO.getMakerId()).trim());
				String date=CommonFunction.changeFormat(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
			  	    if(date != null)
			  	    in1.add(date);
				out1.add(s11.toString());
			    out1.add(s22.toString());
				
			     outMessages1=(ArrayList) ConnectionDAO.callSP("GROSS_RECEIPT_ALLOCATION",in1,out1);
			     s11.append(CommonFunction.checkNull(outMessages1.get(0)));
				 s22.append(CommonFunction.checkNull(outMessages1.get(1)));
				 logger.info("GROSS_RECEIPT_ALLOCATION s11: "+s11.toString());
				 logger.info("GROSS_RECEIPT_ALLOCATION s22: "+s22.toString());
				 procval=CommonFunction.checkNull(s22.toString());
				 in1=null;
				 out1=null;
				 outMessages1=null;
				 s11=null;
				 s22=null;
			 }
			 catch(Exception e)
			 {
				 logger.info("GROSS_RECEIPT_ALLOCATION s11: "+e);
			 }
			 finally{
				 
				 receiptVO=null;
				
			 }
			
			return procval;
		}

		@Override
		public String cashDepositFlag() {
			String flag=null;
			StringBuilder query = new StringBuilder();
			query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CASH_AUTO_DEPOSIT'");
			logger.info("Query for getting cashDepositFlag from parameter_mst  : "+query.toString());
			try
			{
				flag = ConnectionDAO.singleReturn(query.toString());
				query=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}

		

		@Override
		public String nonCashDepositFlag() {
			String flag=null;
			StringBuilder query = new StringBuilder();
			query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='NON_CASH_AUTO_DEPOSIT'");
			logger.info("Query for getting nonCashDepositFlag from parameter_mst  : "+query.toString());
			try
			{
				flag = ConnectionDAO.singleReturn(query.toString());
				query=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}

		@Override
		public ArrayList getDefaultBusinessPartnerTypeReceipt(String lbxLoanNoHID,String bpType) {
			
			ArrayList bpList=new ArrayList();
	 		StringBuilder query = new StringBuilder();
	
	 		try{
	 			ReceiptMakerVO vo=null;
	 			ArrayList mainList=new ArrayList ();
	 			ArrayList subList =new ArrayList();
	 					
	 			logger.info(" In getDefaultBusinessPartnerTypeReceipt....bpType : " + bpType);	
	 			String bpTypeCheckQuery = "select BPV.BP_ID from CR_LOAN_DTL C INNER JOIN business_partner_view BPV ON BPV.LOAN_ID=C.LOAN_ID and BPV.BP_TYPE='"+CommonFunction.checkNull(bpType)+"' where C.LOAN_ID='"+CommonFunction.checkNull(lbxLoanNoHID)+"'";
	 			logger.info("bpTypeCheckQuery : " + bpTypeCheckQuery);
	 			String bpTypeResult = ConnectionDAO.singleReturn(bpTypeCheckQuery);
	 			
	 			if(CommonFunction.checkNull(bpTypeResult).equalsIgnoreCase(""))
	 			{
	 				query.append("SELECT LOAN_CUSTOMER_ID,CUSTOMER_NAME,LOAN_REPAYMENT_TYPE,LOAN_NO,'','' FROM CR_LOAN_DTL C ");
		 			query.append("INNER JOIN GCD_CUSTOMER_M G ON C.LOAN_CUSTOMER_ID=G.CUSTOMER_ID ");
		 			query.append("WHERE C.LOAN_ID='"+CommonFunction.checkNull(lbxLoanNoHID)+"'" );
	 			}
	 			else
	 			{
		 			query.append("SELECT BPV.BP_ID,CUSTOMER_NAME,LOAN_REPAYMENT_TYPE,LOAN_NO,GM.DESCRIPTION,BPV.BP_NAME FROM CR_LOAN_DTL C ");
		 			query.append("INNER JOIN GCD_CUSTOMER_M G ON C.LOAN_CUSTOMER_ID=G.CUSTOMER_ID ");
		 			query.append(" INNER JOIN business_partner_view BPV ON BPV.LOAN_ID=C.LOAN_ID and BPV.BP_TYPE='"+CommonFunction.checkNull(bpType)+"' ");
		 			query.append(" INNER JOIN generic_master gm ON gm.VALUE=BPV.BP_TYPE AND GENERIC_KEY='BPTYPE' ");
		 			query.append("WHERE C.LOAN_ID='"+CommonFunction.checkNull(lbxLoanNoHID)+"'" );
	 			}

	 			
	 			logger.info("In getDefaultBusinessPartnerTypeReceipt "+query.toString());	
	 			
	 			mainList=ConnectionDAO.sqlSelect(query.toString());
	 			query=null;
	 			int size=mainList.size();
	 			for(int i=0;i<size;i++)
	 			{
	 				subList= (ArrayList)mainList.get(i);
	 				if(subList.size()>0){
	 					vo = new ReceiptMakerVO();
	 					vo.setLbxBPNID((CommonFunction.checkNull(subList.get(0)).trim()));
		 				vo.setCustomerName((CommonFunction.checkNull(subList.get(1)).trim()));	
		 				vo.setLoanRepaymentType((CommonFunction.checkNull(subList.get(2)).trim()));	
		 				vo.setLoanAccountNumber((CommonFunction.checkNull(subList.get(3)).trim()));		
		 				vo.setBusinessPartnerType((CommonFunction.checkNull(subList.get(4)).trim()));
		 				vo.setBusinessPartnerName((CommonFunction.checkNull(subList.get(5)).trim()));
		 				vo.setLbxBPType(CommonFunction.checkNull(bpType));
		 				bpList.add(vo);
	 				}
	 				subList.clear();
	 				subList=null;
	 				vo=null;
	 			}
	 			mainList.clear();
	 			mainList=null;
	 				}catch(Exception e){
	 					e.printStackTrace();
	 				}
	 				finally
	 				{
	 					query = null;
	 					lbxLoanNoHID=null;
	 					bpType=null;
	 				}


	 				return bpList;
		}

		public String getAllocationGridReceipt() {
			
			String flag=null;
			StringBuilder query = new StringBuilder();
			query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'");
			logger.info("Query for getting getAllocationGridReceipt from parameter_mst  : "+query.toString());
			try
			{
				flag = ConnectionDAO.singleReturn(query.toString());
				query=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}

		public ArrayList getshowTotalOnReceipt(ReceiptMakerVO obj) 
		{
			ArrayList list =new ArrayList();	
			try
			{			
				ReceiptMakerVO vo =null;
				 StringBuilder query=new StringBuilder();
				 query.append(" SELECT SUM(IFNULL(BALANNCE_AMOUNT,0)),SUM(IFNULL(ALLOCATED_AMOUNT,0)),SUM(IFNULL(ALLOCATED_TDS,0))" );
				 query.append(" FROM CR_GROSS_ALLOCATION_DTL A ");
				 query.append(" INNER JOIN COM_CHARGE_CODE_M B on A.CHARGE_CODE=B.CHARGE_CODE " );
				 query.append(" WHERE INSTRUMENT_ID='"+CommonFunction.checkNull(obj.getInstrumentID())+"' ");
				
				logger.info("getshowTotalOnReceipt in receipt  query "+query.toString());
				ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
			
				
				query=null;
				int size=charges.size();
				for(int i=0;i<size;i++){
			
				ArrayList subcharges=(ArrayList)charges.get(i);
				if(subcharges.size()>0)	
				{
					vo = new ReceiptMakerVO();
					vo.setShowTotalBalanceAmt((CommonFunction.checkNull(subcharges.get(0))).trim());
					vo.setShowTotalAllocatedAmount((CommonFunction.checkNull(subcharges.get(1))).trim());
					vo.setShowTotalTdsAmount((CommonFunction.checkNull(subcharges.get(2))).trim());		
					
					list.add(vo);
				 }
				}
				vo=null;
				charges.clear();
				charges=null;
			}
			catch (Exception e) 
			{e.printStackTrace();}
			finally{
				
				obj=null;
				
			}
			return list;
		}

		@Override
		public String getTDSreceiptStatus() {
			
			String flag=null;
			StringBuilder query = new StringBuilder();
			query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='TDS_RECEIPT_STATUS'");
			logger.info("Query for getting getTDSreceiptStatus from parameter_mst  : "+query.toString());
			try
			{
				flag = ConnectionDAO.singleReturn(query.toString());
				query=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}

		public String getAllocationChargeCode(String loanId) {
			
			String result=null;
			StringBuilder query = new StringBuilder();
			query.append("SELECT CONCAT(GROUP_CONCAT( DISTINCT CHARGE_CODE_ID SEPARATOR ','),',7,109,110,99,98,6')  FROM CR_TXNADVICE_DTL WHERE  ADVICE_TYPE='R' AND (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 AND  LOAN_ID='"+CommonFunction.checkNull(loanId)+"'");
			logger.info("Query for getting getAllocationChargeCode : "+query.toString());
			try
			{
				result = ConnectionDAO.singleReturn(query.toString());
				logger.info("getAllocationChargeCode result : " + result);
				if(CommonFunction.checkNull(result).equalsIgnoreCase("")){
					result="7,109,110,99,98,6";
					
				}
				query=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				loanId=null;
			}
			return result;
		}
		
		public ArrayList getCashDepositAccount(){
			
			 ArrayList result=new ArrayList();
	          ArrayList mainList=new ArrayList();
	          ReceiptMakerVO receiptVO = new ReceiptMakerVO();
			  	StringBuilder query = new StringBuilder();
			  	try{
			  		query.append("select a.BANK_ID,a.BANK_ACCOUNT ,a.BRANCH_IFCS_CODE ,a.BANK_BRANCH_ID,a.BRANCH_MICR_CODE from com_bank_accounts_m a  ");
			  		query.append(" left join com_bankbranch_m b on (a.BANK_BRANCH_ID = b.BANK_BRANCH_ID) ");
			  		query.append(" left join com_bank_m c on (c.BANK_ID=a.BANK_ID) ");
			  		query.append(" where a.REC_STATUS='A' AND a.ACCOUNT_TYPE='C' limit 1");
			  		
		  		   logger.info("IN getcashDepositAccount  ### "+query.toString());
		  		   result = ConnectionDAO.sqlSelect(query.toString());
		  		   int size=result.size();
		  		   

		  	for(int i=0;i<size;i++){
				
				  ArrayList data=(ArrayList)result.get(i);
				  

			  if(data.size()>0){
		
					 receiptVO.setDepositBankID((CommonFunction.checkNull(data.get(0))).trim());
					 receiptVO.setDepositBankAccount((CommonFunction.checkNull(data.get(1))).trim());
					 receiptVO.setDepositIfscCode((CommonFunction.checkNull(data.get(2))).trim());
					 receiptVO.setDepositBranchID((CommonFunction.checkNull(data.get(3))).trim());
					 receiptVO.setDepositMicr((CommonFunction.checkNull(data.get(4))).trim());
					 
					 mainList.add(receiptVO);	
			  		}
			  data.clear();
			  data=null;
			    }
		  } catch(Exception e){
							e.printStackTrace();
						}
			  	   finally
			  	   {
			  		 query = null;
			  		result.clear();
			  		result=null;
			  		receiptVO=null;
			  	   }
						return mainList;
		}
		
		public String getsaveForwardReceipt() {
			
			String flag=null;
			StringBuilder query = new StringBuilder();
			query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='SAVE_FORWARD_RECEIPT'");
			logger.info("Query for getting getsaveForwardReceipt from parameter_mst  : "+query.toString());
			try
			{
				flag = ConnectionDAO.singleReturn(query.toString());
				query=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}
		
		public ArrayList getchargesDetailBeforeSave(ReceiptMakerVO vo) 
		{
			ArrayList list =new ArrayList();	
			try
			{			
				//ReceiptMakerVO vo =null;
				 StringBuilder query=new StringBuilder();
				 String installmentTypeQuery="SELECT LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' ";
				 String installmentType=ConnectionDAO.singleReturn(installmentTypeQuery);
				 if(CommonFunction.checkNull(installmentType).equalsIgnoreCase("I") )
					{
						 if(CommonFunction.checkNull(vo.getLbxBPType()).equalsIgnoreCase("CS"))
						 {
							 
							 query.append(" (SELECT  DISTINCT CHARGE_DESC,A.CHARGE_CODE,IFNULL(SUM(ADVICE_AMOUNT),0) AS ADVICE_AMOUNT,IFNULL(SUM(TXN_ADJUSTED_AMOUNT),0) AS TXN_ADJUSTED_AMOUNT, " ); 
							 query.append(" IFNULL(SUM(AMOUNT_IN_PROCESS),0) AS AMOUNT_IN_PROCESS,IFNULL(SUM(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0) as  BALANCE_AMOUNT " );
							 query.append(" FROM COM_CHARGE_CODE_M A ");
							 query.append("	INNER JOIN CR_RECEIPT_CHARGE_CODE_M ON CR_RECEIPT_CHARGE_CODE_M.CHARGE_CODE=A.CHARGE_CODE ");
							 query.append("	LEFT JOIN CR_TXNADVICE_DTL B ON A.CHARGE_CODE=B.CHARGE_CODE_ID  AND B.ADVICE_TYPE='R' ");
							 query.append("	 AND B.BP_TYPE='CS' and  B.LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+CommonFunction.checkNull(vo.getBusinessDate())+"', '"+dateFormat+"')  ");
							 query.append("	 GROUP BY cr_receipt_charge_code_m.SEQ_NO,CHARGE_DESC,A.RECIEPT_CHARGE_CODE) ");
							 query.append(" UNION ALL  ");
							 query.append(" (select  distinct CHARGE_DESC,A.CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT, ");
							 query.append(" IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS, IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT ");
							 query.append(" from com_charge_code_m a  ");							
							 query.append(" inner join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID AND B.BP_TYPE='CS' and  b.loan_id='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' and b.charge_code_id not in (select charge_code from CR_RECEIPT_CHARGE_CODE_M)    AND B.ADVICE_DATE<=STR_TO_DATE('"+CommonFunction.checkNull(vo.getBusinessDate())+"', '"+dateFormat+"') ");
							 query.append(" WHERE   ");
							 query.append("  B.ADVICE_TYPE='R' AND (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 ");
							 query.append(" GROUP BY A.CHARGE_CODE,CHARGE_DESC ) ");

									  
						 }
					 
					 //manish
					 
					 
					 /*if (CommonFunction.checkNull(vo.getLbxBPType()).equalsIgnoreCase("CS"))
				        {
				          query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,");
				          query.append(new StringBuilder().append("(CASE charge_code WHEN 7 THEN IFNULL((SELECT sum(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT-AMOUNT_IN_PROCESS) FROM cr_txnadvice_dtl A WHERE A.REC_STATUS IN ('A', 'F') and A.ADVICE_TYPE='R' AND A.CHARGE_CODE_ID IN (8,9) AND LOAN_ID='").append(vo.getLbxLoanNoHID()).append("' AND BP_TYPE='CS' and txn_type='bil'),0) WHEN 8 THEN case IFNULL(@valuee:=(select  sum(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT-AMOUNT_IN_PROCESS)  from cr_txnadvice_dtl where  loan_id='").append(vo.getLbxLoanNoHID()).append("' and txn_type='MAA'  and CHARGE_CODE_ID=8 and REC_STATUS IN ('A', 'F')),0) when 0 THEN '0.0000'  else @valuee end  WHEN 9 THEN case IFNULL(@valuee:=(select sum(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT-AMOUNT_IN_PROCESS)  from cr_txnadvice_dtl where  loan_id='").append(vo.getLbxLoanNoHID()).append("' and txn_type='MAA' and CHARGE_CODE_ID=9 and  REC_STATUS IN ('A', 'F')),0) when 0 then '0.0000' else @valuee end   ELSE  IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))+ifnull(dueAmt.BALANCE_AMOUNT,0),0) END)BALANCE_AMOUNT from com_charge_code_m a ").toString());
				          query.append(new StringBuilder().append(" left join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID and B.advice_type='R' AND B.BP_TYPE='").append(vo.getLbxBPType()).append("' and  B.LOAN_ID='").append(vo.getLbxLoanNoHID()).append("' AND B.ADVICE_DATE<=STR_TO_DATE('").append(vo.getBusinessDate()).append("', '").append(this.dateFormat).append("')  ").toString());
				          query.append(new StringBuilder().append(" left join (  SELECT Q.LOAN_ID,IFNULL(SUM(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT  FROM CR_TXNADVICE_DTL Q \tjoin   ( \tselect distinct CHARGE_CODE,LOAN_ID \tfrom CR_LOAN_CHARGE_PLAN_DTL \tWHERE LOAN_ID='").append(CommonFunction.checkNull(vo.getLbxLoanNoHID())).append("' AND REC_STATUS='A' ").append(" ) ch on(ch.CHARGE_CODE=Q.CHARGE_CODE_ID and ch.loan_id=Q.LOAN_ID) ").append(" where Q.ADVICE_TYPE='R' AND Q.REC_STATUS IN ('A', 'F') AND Q.BP_TYPE='CS' ").append("    AND  Q.LOAN_ID='").append(CommonFunction.checkNull(vo.getLbxLoanNoHID())).append("' AND Q.ADVICE_DATE<=STR_TO_DATE('").append(CommonFunction.checkNull(vo.getBusinessDate())).append("', '").append(this.dateFormat).append("') ").append(" )dueAmt on(dueAmt.LOAN_ID=b.loan_id and a.CHARGE_CODE in (7,8,9))").toString());

				          query.append(" where CHARGE_CODE in (7,109,110,99,98,6,8,9)  ");
				          query.append(" group by IF(CHARGE_CODE in (7,8,9),1,IF(CHARGE_CODE=109,2,IF(CHARGE_CODE=110,3,IF(CHARGE_CODE=6,4,IF(CHARGE_CODE=99,5,IF(CHARGE_CODE=98,6,8)))))),CHARGE_CODE,CHARGE_DESC) ");
				          query.append(" UNION ALL ");
				          query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT from com_charge_code_m a ");
				          query.append(new StringBuilder().append(" inner join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID AND b.CHARGE_CODE_ID NOT IN (SELECT DISTINCT ifnull(CHARGE_CODE,'') FROM CR_LOAN_CHARGE_PLAN_DTL WHERE LOAN_ID='").append(CommonFunction.checkNull(vo.getLbxLoanNoHID())).append("'  AND REC_STATUS='A') AND B.BP_TYPE='").append(CommonFunction.checkNull(vo.getLbxBPType())).append("' and  b.loan_id='").append(CommonFunction.checkNull(vo.getLbxLoanNoHID())).append("' AND B.ADVICE_DATE<=STR_TO_DATE('").append(CommonFunction.checkNull(vo.getBusinessDate())).append("', '").append(this.dateFormat).append("')  ").toString());
				          query.append(" where CHARGE_CODE not in (7,8,9,109,110,99,98,6)  and B.advice_type='R' and b.rec_status='A' and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 ");
				          query.append(" group by CHARGE_CODE,CHARGE_DESC) ");
				        }*/
					 
					 
					 
					 //manish

						 else
						 {
						
							 query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT from com_charge_code_m a " );
							 query.append(" inner join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID AND b.CHARGE_CODE_ID NOT IN (SELECT DISTINCT ifnull(CHARGE_CODE,'') FROM CR_LOAN_CHARGE_PLAN_DTL WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"'  AND REC_STATUS='A') AND B.BP_TYPE='"+CommonFunction.checkNull(vo.getLbxBPType())+"' and  b.loan_id='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+CommonFunction.checkNull(vo.getBusinessDate())+"', '"+dateFormat+"') " );
							 query.append(" where CHARGE_CODE not in (7,109,110,99,98,6) and B.advice_type='R' and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 ");
							 query.append(" group by CHARGE_CODE,CHARGE_DESC) ");
						
						 }
						
					}
					else
					{
						if( vo.getLbxBPType().equalsIgnoreCase("CS"))
						 {
							 query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT from com_charge_code_m a " );
							 query.append(" left join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID and B.advice_type='R' AND B.BP_TYPE='"+vo.getLbxBPType()+"' and  b.loan_id='"+vo.getLbxLoanNoHID()+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+vo.getBusinessDate()+"', '"+dateFormat+"')  " );
							 query.append(" where CHARGE_CODE IN (110,99,9) ");
							 query.append(" group by IF(CHARGE_CODE=7,1,IF(CHARGE_CODE=109,2,IF(CHARGE_CODE=110,3,IF(CHARGE_CODE=99,4,IF(CHARGE_CODE=98,5,6))))),CHARGE_CODE,CHARGE_DESC) ");
							 query.append(" UNION ALL ");
							 query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT from com_charge_code_m a " );
							 query.append(" inner join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID AND b.CHARGE_CODE_ID NOT IN (SELECT DISTINCT ifnull(CHARGE_CODE,'') FROM CR_LOAN_CHARGE_PLAN_DTL WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"'  AND REC_STATUS='A') AND B.BP_TYPE='"+CommonFunction.checkNull(vo.getLbxBPType())+"' and  b.loan_id='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+CommonFunction.checkNull(vo.getBusinessDate())+"', '"+dateFormat+"') " );
							 query.append(" where CHARGE_CODE not in (7,109,110,99,98,9,6)  and B.advice_type='R' and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 ");
							 query.append(" group by CHARGE_CODE,CHARGE_DESC) ");
						
						}
						else
						{
							 query.append("(select  distinct CHARGE_DESC,CHARGE_CODE,IFNULL(sum(ADVICE_AMOUNT),0) as ADVICE_AMOUNT,IFNULL(sum(TXN_ADJUSTED_AMOUNT),0) as TXN_ADJUSTED_AMOUNT,IFNULL(sum(AMOUNT_IN_PROCESS),0) as AMOUNT_IN_PROCESS,IFNULL(sum(ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS)),0)BALANCE_AMOUNT from com_charge_code_m a " );
							 query.append(" inner join cr_txnadvice_dtl b on a.CHARGE_CODE=b.CHARGE_CODE_ID AND b.CHARGE_CODE_ID NOT IN (SELECT DISTINCT ifnull(CHARGE_CODE,'') FROM CR_LOAN_CHARGE_PLAN_DTL WHERE LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"'  AND REC_STATUS='A') AND B.BP_TYPE='"+CommonFunction.checkNull(vo.getLbxBPType())+"' and  b.loan_id='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' AND B.ADVICE_DATE<=STR_TO_DATE('"+CommonFunction.checkNull(vo.getBusinessDate())+"', '"+dateFormat+"')  " );
							 query.append(" where CHARGE_CODE not in (7,109,110,99,98,9,6)  and B.advice_type='R' and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 ");
							 query.append(" group by CHARGE_CODE,CHARGE_DESC) ");
				
						}
					}
				
				logger.info("getchargesDetailOnReceipt in receipt  query "+query.toString());
				ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
				
				query=null;
				int size=charges.size();
				ReceiptMakerVO vo1=null;
				for(int i=0;i<size;i++){
			
				ArrayList subcharges=(ArrayList)charges.get(i);
				if(subcharges.size()>0)	
				{
					vo1 = new ReceiptMakerVO();
					vo1.setAllocChargeDesc((CommonFunction.checkNull(subcharges.get(0))).trim());
					vo1.setAllocChargeCode((CommonFunction.checkNull(subcharges.get(1))).trim());
					vo1.setAllocAdviceAmount((CommonFunction.checkNull(subcharges.get(2))).trim());
					vo1.setAllocAdjustedAmount((CommonFunction.checkNull(subcharges.get(3))).trim());
					vo1.setAllocAmountInProcess((CommonFunction.checkNull(subcharges.get(4))).trim());
					vo1.setAllocBalAmount((CommonFunction.checkNull(subcharges.get(5))).trim());	
					String qry="SELECT ifnull(ALLOCATED_AMOUNT,0.00),ifnull(ALLOCATED_TDS,0.00)  FROM CR_GROSS_ALLOCATION_DTL WHERE CHARGE_CODE='"+(CommonFunction.checkNull(subcharges.get(1))).trim()+"' AND txnid='"+CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()+"'  and INSTRUMENT_ID='"+CommonFunction.checkNull(vo.getInstrumentID()).trim()+"' AND OTHER_CHARGE_FLAG='N' ";
					ArrayList list1 = ConnectionDAO.sqlSelect(qry);
					if(list1.size()>0)
					{
						ArrayList list2=(ArrayList)list1.get(0);
						if(list2.size()>0)	
						{
							vo1.setAllocAmount(CommonFunction.checkNull(list2.get(0)).trim());
							vo1.setAllocTdsAllocatedAmount(CommonFunction.checkNull(list2.get(1)).trim());
						}
						else
						{
							vo1.setAllocAmount("0.00");
							vo1.setAllocTdsAllocatedAmount("0.00");
						}
					}
					else
					{
						vo1.setAllocAmount("0.00");
						vo1.setAllocTdsAllocatedAmount("0.00");
					}
					list.add(vo1);
				 }
				subcharges.clear();
				subcharges=null;
				}
				installmentTypeQuery=null;
				installmentType=null;
				charges.clear();
				charges=null;
				
				
			}
			catch (Exception e) 
			{e.printStackTrace();}
			finally{
				vo=null;
			}
			return list;
		}
     
		@Override
public ArrayList saveOrUpdateOrForwardOperations(String compId,String loanId,String instrumentID, String makerId,String bDate,String recStatus,String directForward) {
			
			logger.info("saveOrUpdateOrForwardOperations......SAVE_UPDATE_FORWARD_RECEIPT........");
			ArrayList status=new ArrayList();
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			StringBuilder s1 = new StringBuilder();
			StringBuilder s2 = new StringBuilder();
			StringBuilder instrumentId = new StringBuilder();
			
			try
			{
			    in.add(compId);
			    if (CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
			    	loanId="0";
			    in.add(((CommonFunction.checkNull(loanId).trim())));
				if (CommonFunction.checkNull(instrumentID).equalsIgnoreCase(""))
				{
					instrumentID="0";
				}
				in.add(((CommonFunction.checkNull(instrumentID).trim())));
				in.add(makerId);
				String date=CommonFunction.changeFormat(CommonFunction.checkNull(bDate).trim());
		  	    if(date != null)
		  	    in.add(date);
				in.add(recStatus);
				in.add(directForward);
				out.add(instrumentId.toString());
				out.add(s1.toString());
			    out.add(s2.toString());
			   
				
			     outMessages=(ArrayList) ConnectionDAO.callSP("SAVE_UPDATE_FORWARD_RECEIPT",in,out);
			     instrumentId.append(CommonFunction.checkNull(outMessages.get(0)));
				 s1.append(CommonFunction.checkNull(outMessages.get(1)));
				 s2.append(CommonFunction.checkNull(outMessages.get(2)));
				 
				 if(CommonFunction.checkNull(s1).equalsIgnoreCase("S"))
				 {
					 status.add(0,CommonFunction.checkNull(s1.toString()));
					 status.add(1,CommonFunction.checkNull(instrumentId.toString()));
				 }
				 else
				 {
					 status.add(0,CommonFunction.checkNull(s2.toString()));
					 status.add(1,CommonFunction.checkNull(instrumentId.toString()));
				 }
				 logger.info("SAVE_UPDATE_FORWARD_RECEIPT instrumentId: "+CommonFunction.checkNull(instrumentId).toString()+" s1: "+CommonFunction.checkNull(s1).toString()+""+CommonFunction.checkNull(s2).toString());
					in=null;
					out=null;
					outMessages=null;
					s1=null;
					s2=null;
					instrumentId=null;
			}
			catch (Exception e)
			{
				 status.add("Some Error occure during processing...");
				 status.add(1,CommonFunction.checkNull(instrumentId.toString()));
				e.printStackTrace();
			}
			finally{
				compId=null;
				instrumentID=null;
				makerId=null;
				bDate=null;
				recStatus=null;
				directForward=null;
			}			
			return status;
		}

		@Override
		public String getRepoFlag(String lbxLoanNoHID) {
			
			
			String repoFlagQuery=" SELECT IFNULL(REPO_FLAG,'N') FROM CR_LOAN_DTL L "+
            " INNER JOIN CR_INSTRUMENT_DTL I ON L.LOAN_ID=I.TXNID AND TXN_TYPE='LIM' AND I.INSTRUMENT_TYPE='R' "+
            "  WHERE L.REC_STATUS='A' AND L.LOAN_ID='"+CommonFunction.checkNull(lbxLoanNoHID)+"' LIMIT 1 ";

			logger.info("repoFlagQuery: "+repoFlagQuery);
			String repoFlag=ConnectionDAO.singleReturn(repoFlagQuery);
			repoFlagQuery=null;
			lbxLoanNoHID=null;
			return repoFlag;
		
		}

		@Override
		public String getInstallmentTypeReciept(String loanid) {
			
			String installmentTypeQuery="SELECT LOAN_REPAYMENT_TYPE FROM CR_LOAN_DTL WHERE LOAN_ID='"+loanid+"' ";
			logger.info("query: "+installmentTypeQuery);
			String installmentType=ConnectionDAO.singleReturn(installmentTypeQuery);
			logger.info("installmentType: "+installmentType);
			installmentTypeQuery=null;
			return installmentType;
				
		}
		 @Override
			public String getChargeReceipt() {
				String chargeFlagQuery="SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='ALLOCATION_GRID_RECEIPT'";
				String chargeFlag = ConnectionDAO.singleReturn(chargeFlagQuery);
				chargeFlagQuery=null;
				return chargeFlag;
						
			}
		@Override
		public String getEntryGrossAllocationReceipt(String instrumentID) {
			 String entryGrossAllocationQuery="SELECT COUNT(1) FROM CR_GROSS_ALLOCATION_DTL WHERE INSTRUMENT_ID='"+instrumentID+"'";
			 String entryGrossAllocation=ConnectionDAO.singleReturn(entryGrossAllocationQuery);
			 logger.info("entryGrossAllocation: "+entryGrossAllocation+" entryGrossAllocationQuery: "+entryGrossAllocationQuery);
					
			 entryGrossAllocationQuery=null;
			 return entryGrossAllocation;
		}
		
		@Override
		public ArrayList getDataFromInstrumentList(String instrmtID,
				String txnID) {
			
			String dataFromInstrumentDtlQuery="select BPTYPE,BPID,INSTRUMENT_AMOUNT,TDS_AMOUNT from cr_instrument_dtl where INSTRUMENT_ID='"+instrmtID+"' AND TXN_TYPE='LIM' AND TXNID='"+txnID+"' limit 1";
	 	    logger.info("dataFromInstrumentDtlQuery: "+dataFromInstrumentDtlQuery);
	 	   ArrayList dataFromInstrumentList=null;
	 		try {
	 			dataFromInstrumentList = ConnectionDAO.sqlSelect(dataFromInstrumentDtlQuery);
	 		} catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	 		dataFromInstrumentDtlQuery=null;
	 		return dataFromInstrumentList;
		}
		//Manish Code Start on 15/04/2014

		
}
