package com.cp.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;


import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.ImdDAO;
import com.cp.vo.ChargeVo;
import com.cp.vo.ImdMakerVO;
import com.logger.LoggerMsg;

public class MSSQLImdDAOImpl implements ImdDAO{
	private static final Logger logger = Logger.getLogger(MSSQLImdDAOImpl.class.getName());
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

	//code by manisha for Receipt Maker search grid data
	
	public ArrayList <ImdMakerVO> imdMakerGrid(ImdMakerVO vo)
	{
		logger.info("In imdMakerGrid() of ReceiptDAOImpl");
		StringBuilder dealNo = new StringBuilder();
		StringBuilder customerName = new StringBuilder();
		StringBuilder businessPartnerType = new StringBuilder();
		StringBuilder businessPartnerID = new StringBuilder();
		StringBuilder receiptAmount = new StringBuilder();
		StringBuilder instrumentNumber = new StringBuilder();
		StringBuilder receiptMode = new StringBuilder();
		
//	  String loanNo="";
//	  String customerName="";
//	  String businessPartnerType="";
//	  String businessPartnerID="";
//	  String receiptAmount="";
//	  String instrumentNumber="";
//	  String receiptMode="";
//	  String userName="";

	  int count=0;
	  int startRecordIndex=0;
	  int endRecordIndex = no;
	  ArrayList<ImdMakerVO> receiptMakerSearchGrid=new 	ArrayList<ImdMakerVO>();
	  
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
//		try{
//			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
//			userName=ConnectionDAO.singleReturn(userNameQ);
//			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		  try{
			  ArrayList searchlist=new ArrayList();
	        logger.info("In imdMakerGrid....................");
	        StringBuffer bufInsSql =	new StringBuffer();
	        StringBuffer bufInsSqlTempCount = new StringBuffer();
	        boolean appendSQL=false;
	       
	        dealNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()));
	        logger.info("In imdMakerGrid......dealNo:::::::  "+dealNo);
	        customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
	        businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
	        businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
	   	    if(!CommonFunction.checkNull(vo.getReceiptAmount()).equalsIgnoreCase(""))
	   	    {
	        receiptAmount.append(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
	        logger.info("In imdMakerGrid......receiptAmount  "+receiptAmount);
	   	    }
		    instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
		    receiptMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptMode()).trim()));
		    logger.info("In imdMakerGrid......receiptMode  "+receiptMode);  
		   
		    
	        bufInsSql.append("SELECT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME ,CID.INSTRUMENT_AMOUNT, ");
	        bufInsSql.append("CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID " );
   
		    bufInsSql.append(" FROM CR_DEAL_CUSTOMER_M GCM ,CR_DEAL_DTL CDL, CR_INSTRUMENT_DTL CID, BUSINESS_PARTNER_VIEW BPV,");
		    bufInsSql.append(" GENERIC_MASTER GM WHERE CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID  AND BPV.BP_ID=CID.BPID AND  BPV.DEAL_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE ");
		    bufInsSql.append("AND CDL.DEAL_ID=CID.TXNID    AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='P' and INSTRUMENT_TYPE='R' " );
		    bufInsSql.append(" AND CID.MAKER_ID='"+vo.getReportingToUserId()+"' AND CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' AND CID.TXN_TYPE='DC' ");
		   
		    bufInsSqlTempCount.append(" SELECT  count(1)  FROM CR_DEAL_CUSTOMER_M GCM ,CR_DEAL_DTL CDL, CR_INSTRUMENT_DTL CID, BUSINESS_PARTNER_VIEW BPV,");
		    bufInsSqlTempCount.append(" GENERIC_MASTER GM WHERE CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID  AND BPV.BP_ID=CID.BPID AND  BPV.DEAL_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE ");
		    bufInsSqlTempCount.append("AND CDL.DEAL_ID=CID.TXNID    AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='P' and INSTRUMENT_TYPE='R' " );
		    bufInsSqlTempCount.append(" AND CID.MAKER_ID='"+vo.getReportingToUserId()+"' AND CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' AND CID.TXN_TYPE='DC' ");
		    
			  
	    	 if((!((dealNo.toString()).trim().equalsIgnoreCase("")))) {
	    		 bufInsSql.append(" AND");
		         bufInsSql.append(" CDL.DEAL_ID='"+dealNo+"' ");
		         bufInsSqlTempCount.append(" AND");
		         bufInsSqlTempCount.append(" CDL.DEAL_ID='"+dealNo+"' ");
		         appendSQL=true;
	    	  
	      }
	    			 
	    			if((!((customerName.toString()).trim().equalsIgnoreCase("")))) {
	    				 bufInsSql.append(" AND");
	    	    	  bufInsSql.append(" GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' ");
	    	    	  bufInsSqlTempCount.append(" AND");
	    	    	  bufInsSqlTempCount.append(" GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' ");
	    	    	  appendSQL=true;
	    	      }
	    			
	    			if((!((businessPartnerType.toString()).trim().equalsIgnoreCase("")))) {
	    				 bufInsSql.append(" AND");
	    	    	  bufInsSql.append(" CID.BPTYPE='"+(businessPartnerType.toString()).trim()+"' ");
	    	    	  bufInsSqlTempCount.append(" AND");
	    	    	  bufInsSqlTempCount.append(" CID.BPTYPE='"+(businessPartnerType.toString()).trim()+"' ");
	    	    	  appendSQL=true;
	    	      }
	    			
	    			if((!((businessPartnerID.toString()).trim().equalsIgnoreCase("")))) {
	    				 bufInsSql.append(" AND");
	    	    	  bufInsSql.append(" CID.BPID='"+businessPartnerID+"' ");
	    	    	  bufInsSqlTempCount.append(" AND");
	    	    	  bufInsSqlTempCount.append(" CID.BPID='"+businessPartnerID+"' ");
	    	    	  appendSQL=true;
	    	      }
	    			
	    			if((!((receiptAmount.toString()).trim().equalsIgnoreCase("")))) {
	    			  bufInsSql.append(" AND");
	    	    	  bufInsSql.append(" CID.INSTRUMENT_AMOUNT='"+receiptAmount+"' ");
	    	    	  bufInsSqlTempCount.append(" AND");
	    	    	  bufInsSqlTempCount.append(" CID.INSTRUMENT_AMOUNT='"+receiptAmount+"' ");
	    	    	  appendSQL=true;
	    	      }
	    			
	    			if((!((instrumentNumber.toString()).trim().equalsIgnoreCase("")))) {
	    			  bufInsSql.append(" AND");
	    	    	  bufInsSql.append(" CID.INSTRUMENT_NO='"+instrumentNumber+"' ");
	    	    	  bufInsSqlTempCount.append(" AND");
	    	    	  bufInsSqlTempCount.append(" CID.INSTRUMENT_NO='"+instrumentNumber+"' ");
	    	    	  appendSQL=true;
	    	      }

					if((!((receiptMode.toString()).trim().equalsIgnoreCase("")))) {
						  bufInsSql.append(" AND");
				    	  bufInsSql.append(" CID.INSTRUMENT_MODE='"+receiptMode+"'");
				    	  bufInsSqlTempCount.append(" AND");
				    	  bufInsSqlTempCount.append(" CID.INSTRUMENT_MODE='"+receiptMode+"'");
				    	  appendSQL=true;
				      }
					 
					 // if(((loanNo.toString()).trim()==null && (customerName.toString()).trim()==null && businessPartnerType.toString()==null && (businessPartnerID.toString()).trim()==null && (receiptAmount.toString()).trim()==null && (instrumentNumber.toString()).trim()==null && (receiptMode.toString()).trim()==null) || ((loanNo.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") && (businessPartnerType.toString()).trim().equalsIgnoreCase("") && (businessPartnerID.toString()).trim().equalsIgnoreCase("") && (receiptAmount.toString()).trim().equalsIgnoreCase("") && (instrumentNumber.toString()).trim().equalsIgnoreCase("")&& (receiptMode.toString()).trim().equalsIgnoreCase(""))|| vo.getCurrentPageLink()>1)

						//{
						
						LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						if(vo.getCurrentPageLink()>1)
						{
							startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							logger.info("startRecordIndex .................... "+startRecordIndex);
							logger.info("endRecordIndex .................... "+endRecordIndex);
						}
						bufInsSql.append(" ORDER BY CDL.DEAL_ID OFFSET ");
						bufInsSql.append(startRecordIndex);
						bufInsSql.append(" ROWS FETCH next ");
						bufInsSql.append(endRecordIndex);
						bufInsSql.append(" ROWS ONLY ");
						
				//		bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
						
						//}
					
					
					  
					  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
					  count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				
	    	  		
	            logger.info("imdMakerGrid search Data size is...."+searchlist.size());
	            logger.info("imdMakerGrid    ........main query"+bufInsSql.toString());
	            logger.info("imdMakerGrid    .......count query."+bufInsSqlTempCount.toString());
	            for(int i=0;i<searchlist.size();i++){
	            logger.info("imdMakerGrid search List "+searchlist.get(i).toString());
	            ArrayList data=(ArrayList)searchlist.get(i);

	        if(data.size()>0){
	        	ImdMakerVO receiptvo = new ImdMakerVO();
	        	receiptvo.setModifyNo("<a href=imdMakerViewAction.do?method=savedDataOfReceipt&stage=DC&lbxDealNo="
						+ CommonFunction.checkNull(data.get(0)).toString()
						+ "&instrumentID="+CommonFunction.checkNull(data.get(10)).toString()+">"
						+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
			receiptvo.setLbxDealNo((CommonFunction.checkNull(data.get(0)).trim()));
			receiptvo.setDealNo((CommonFunction.checkNull(data.get(1)).trim()));
			logger.info("setCustomerName:-------------"+data.get(2));
			receiptvo.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
			receiptvo.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
			receiptvo.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
			receiptvo.setLbxBPNID(CommonFunction.checkNull(data.get(5)).toString());
			logger.info("setLbxBPNID:-------------"+data.get(5));
			receiptvo.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
			logger.info("setCustomerName:-------------"+data.get(6));
			Number ReceiptAmount=0;
			if(!CommonFunction.checkNull(data.get(7)).equals(""))
			{
				ReceiptAmount = myFormatter.parse(CommonFunction.checkNull(data.get(7)).trim());
			}
			
			logger.info("ReceiptAmount: "+ReceiptAmount);			
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

		      }

		}catch(Exception e){
			e.printStackTrace();
				}
		finally
		{
			dealNo = null ;
			customerName = null;
			businessPartnerType = null;
			businessPartnerID = null;
			receiptAmount = null;
			instrumentNumber = null;
			receiptMode = null;
		}
		return  receiptMakerSearchGrid;	
	}
	
	//code by manisha for receipt Author search grid data


	public ArrayList <ImdMakerVO> imdAuthorGrid(ImdMakerVO vo)
	{
		logger.info("In imdAuthorGrid() of ImdDAOImpl");
		StringBuilder dealNo = new StringBuilder();
		StringBuilder businessPartnerType = new StringBuilder();
		StringBuilder businessPartnerID = new StringBuilder();
		StringBuilder receiptAmount = new StringBuilder();
		StringBuilder instrumentNumber = new StringBuilder();
		StringBuilder customerName = new StringBuilder();
		StringBuilder receiptMode = new StringBuilder();
		

	    int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		
	    ArrayList<ImdMakerVO> authordetailList=new 	ArrayList<ImdMakerVO>();
	    
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());

		  try{
			  ArrayList searchlist=new ArrayList();
	          logger.info("In imdAuthorGrid....................");
	          StringBuffer bufInsSql =	new StringBuffer();
	          StringBuffer bufInsSqlTempCount = new StringBuffer();
	          boolean appendSQL=false;		      
	          dealNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()));
	          logger.info("In imdAuthorGrid......dealNo:::::::  "+dealNo);
	          customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
	          businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
	          businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
	          if(!CommonFunction.checkNull(vo.getReceiptAmount()).equalsIgnoreCase(""))
		   	    {
	          receiptAmount.append(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
		      logger.info("In imdAuthorGrid......imdAmount  "+receiptAmount);         
		   	    }
	          instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
	          receiptMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptMode()).trim()));
			  logger.info("In imdAuthorGrid......imdMode  "+receiptMode);  
			 
	         
	          bufInsSql.append("SELECT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME ,CID.INSTRUMENT_AMOUNT, ");
	          bufInsSql.append("CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID ");
	          bufInsSql.append("FROM CR_DEAL_CUSTOMER_M GCM ,CR_DEAL_DTL CDL, CR_INSTRUMENT_DTL CID, BUSINESS_PARTNER_VIEW BPV, GENERIC_MASTER GM ");
	          bufInsSql.append("WHERE CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID AND BPV.BP_ID=CID.BPID AND  BPV.DEAL_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE ");
	          bufInsSql.append("AND CDL.DEAL_ID=CID.TXNID    AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='F' and INSTRUMENT_TYPE='R' AND CID.TXN_TYPE='DC'");

	          bufInsSqlTempCount.append("SELECT DISTINCT COUNT(1)FROM( SELECT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME , ");
	          bufInsSqlTempCount.append("CID.INSTRUMENT_AMOUNT,CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID ");
	          bufInsSqlTempCount.append("FROM CR_DEAL_CUSTOMER_M GCM ,CR_DEAL_DTL CDL, CR_INSTRUMENT_DTL CID, BUSINESS_PARTNER_VIEW BPV, GENERIC_MASTER GM ");
	          bufInsSqlTempCount.append("WHERE CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID AND BPV.BP_ID=CID.BPID AND  BPV.DEAL_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE ");
	          bufInsSqlTempCount.append("AND CDL.DEAL_ID=CID.TXNID    AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='F' and INSTRUMENT_TYPE='R' AND CID.TXN_TYPE='DC'");

	           if((((dealNo.toString()).trim().equalsIgnoreCase(""))) || (((customerName.toString()).trim().equalsIgnoreCase(""))) || (((businessPartnerType.toString()).trim().equalsIgnoreCase(""))) || (((businessPartnerID.toString()).trim().equalsIgnoreCase(""))) || (((receiptAmount.toString()).trim().equalsIgnoreCase(""))) || (((instrumentNumber.toString()).trim().equalsIgnoreCase("")))|| (((receiptMode.toString()).trim().equalsIgnoreCase("")))|| ((vo.getLbxUserId().equalsIgnoreCase("")))){
	  	  				appendSQL=true;
	  	  			}
	  	  		  
	           if(appendSQL){
					logger.info("In Where Clause");
					bufInsSql.append(" AND CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' AND CID.MAKER_ID!='"+vo.getUserId()+"'");
					bufInsSqlTempCount.append("  AND CID.DEFAULT_BRANCH='"+vo.getBranchId()+"' AND CID.MAKER_ID!='"+vo.getUserId()+"'");
				}
	           
	  	  		  if((!((dealNo.toString()).trim().equalsIgnoreCase("")))) {
	  	  	         bufInsSql.append("AND CDL.DEAL_ID='"+dealNo+"' ");
	  	  	         bufInsSqlTempCount.append("AND CDL.DEAL_ID='"+dealNo+"' ");
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
					bufInsSqlTempCount.append(" )AS B");

	  	  			logger.info("In appendSQL true---- "+appendSQL);
	  	  			
	  	  		
	  	  		 count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

				 // if(((loanNo.toString()).trim()==null && (customerName.toString()).trim()==null && businessPartnerType.toString()==null && (businessPartnerID.toString()).trim()==null && (receiptAmount.toString()).trim()==null && (instrumentNumber.toString()).trim()==null && (receiptMode.toString()).trim()==null) || ((loanNo.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") && (businessPartnerType.toString()).trim().equalsIgnoreCase("") && (businessPartnerID.toString()).trim().equalsIgnoreCase("") && (receiptAmount.toString()).trim().equalsIgnoreCase("") && (instrumentNumber.toString()).trim().equalsIgnoreCase("")&& (receiptMode.toString()).trim().equalsIgnoreCase(""))|| vo.getCurrentPageLink()>1)

					//{
					
					LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
					if(vo.getCurrentPageLink()>1)
					{
						startRecordIndex = (vo.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
						LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
					}
					bufInsSql.append(" ORDER BY CDL.DEAL_ID OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
			//		bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
					
		  			
					
					//}
				  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
					  	  	  		
	  	          logger.info("imdAuthorGrid search Data size is...."+searchlist.size());
	  	          logger.info("imdAuthorGrid    ........"+bufInsSql.toString());
	  	          
	  	          for(int i=0;i<searchlist.size();i++){
	  	          logger.info("imdAuthorGrid search List "+searchlist.get(i).toString());
	  	          ArrayList data=(ArrayList)searchlist.get(i);
	  	          
	  	          if(data.size()>0){
	  	        	ImdMakerVO receiptVO = new ImdMakerVO();
	        	  receiptVO.setModifyNo("<a href=imdAuthorProcessAction.do?method=getReceipttoApprove&lbxLoanNoHID="
							+ CommonFunction.checkNull(data.get(0)).toString()
							+ "&instrumentID="+CommonFunction.checkNull(data.get(10)).toString()
							+ "&lbxBPType="+CommonFunction.checkNull(data.get(3)).toString()+">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");

	        	
	        	  receiptVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
	        	  receiptVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
	        	  logger.info("imdAuthorGrid-------------"+data.get(2));
	        	  receiptVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
	        	  receiptVO.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
	        	  receiptVO.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
	        	  receiptVO.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
	        	  receiptVO.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
	        	  Number ReceiptAmount = 0;
	        	  if(!CommonFunction.checkNull(data.get(7)).equals(""))
	        	  {
	        		  ReceiptAmount = myFormatter.parse((CommonFunction.checkNull(data.get(7))).trim());
	        	  }
				  logger.info("ReceiptAmount: "+ReceiptAmount);			
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
		      }

		}catch(Exception e){
			e.printStackTrace();
				}
		finally
		{
			dealNo = null;
			businessPartnerType = null;
			businessPartnerID = null;
			receiptAmount = null;
			instrumentNumber = null;
			customerName = null;
			receiptMode = null;
		}
		return  authordetailList;	
	}
	
	 public ArrayList <ImdMakerVO> getImdList(ImdMakerVO vo)
		{
		 logger.info("In getImdList() of ImdDAOImpl");

			
		ArrayList<ImdMakerVO> savedReceipt=new ArrayList<ImdMakerVO>();
		try{
			  ArrayList searchlist=new ArrayList();
		  logger.info("In savedReceipt....................");
		  StringBuffer bufInsSql =	new StringBuffer();


		  bufInsSql.append(" SELECT DISTINCT CDL.DEAL_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " );		  
		  bufInsSql.append("   GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME , " );	
		  bufInsSql.append("  CID.INSTRUMENT_MODE,");
		  bufInsSql.append(dbo);
		  bufInsSql.append("date_format(CID.RECEIVED_DATE,'"+dateFormat+"') AS RECEIVED_DATE,CID.INSTRUMENT_AMOUNT,");
		  bufInsSql.append(dbo);
		  bufInsSql.append("date_format(CID.INSTRUMENT_DATE,'"+dateFormat+"') AS INSTRUMENT_DATE,CID.INSTRUMENT_NO," );
		  bufInsSql.append(" CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" );
		  bufInsSql.append("  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME," );
		  bufInsSql.append(" CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.REMARKS,CID.INSTRUMENT_ID,CID.TDS_AMOUNT,CID.RECIPT_NO,CID.MAKER_REMARKS,CDL.REC_STATUS,CID.RECEIPT_PURPOSE,TRANSACTION_REF,CDL.DEAL_BRANCH,CID.MANUAL_AUTO_FLAG" );
		  bufInsSql.append("  from cr_instrument_dtl CID,cr_deal_customer_m GCM ," );
		  bufInsSql.append(" cr_deal_dtl CDL, business_partner_view BPV,generic_master  GM" );
		  bufInsSql.append(" where CID.TXNID=CDL.DEAL_ID" );
		  bufInsSql.append("  AND BPV.BP_ID=CID.BPID AND BPV.DEAL_ID=CID.TXNID AND CID.BPTYPE=BPV.BP_TYPE" );
		  bufInsSql.append("  AND GM.VALUE=CID.BPTYPE and CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID" );
		  bufInsSql.append("  AND GM.GENERIC_KEY='BPTYPE' " );
		  bufInsSql.append("  and INSTRUMENT_TYPE='R'AND CID.REC_STATUS='P'" );
		  bufInsSql.append(" and isnull(CID.TXN_TYPE,'')='DC' AND CID.TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim())+"' ");
		  bufInsSql.append(" AND CID.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' ");
	
			  logger.info("In savedReceipt......... query111..........."+bufInsSql.toString());
		       searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		  logger.info("savedReceipt search Data size is...."+searchlist.size());

		  for(int i=0;i<searchlist.size();i++){
		  logger.info("savedReceipt search List "+searchlist.get(i).toString());
		  ArrayList data=(ArrayList)searchlist.get(i);

		  if(data.size()>0){
			  ImdMakerVO receiptvo = new ImdMakerVO();			
			 receiptvo.setLbxDealNo ((CommonFunction.checkNull(data.get(0)).trim()));
			 receiptvo.setDealNo((CommonFunction.checkNull(data.get(1)).trim()));
			  logger.info(" savedReceipt setLbxDealNo:-------------"+receiptvo.getLbxDealNo());
			 receiptvo.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
			  logger.info(" savedReceipt setCustomerName:-------------"+data.get(2));
			 receiptvo.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
			 receiptvo.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
			 logger.info("setBusinessPartnerType: "+data.get(4));
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
				 ReceiptAmount = myFormatter.parse((CommonFunction.checkNull(data.get(9))).trim());
			}
			 logger.info("ReceiptAmount: "+ReceiptAmount);			
			 receiptvo.setReceiptAmount(myFormatter.format(ReceiptAmount));			
			 receiptvo.setInstrumentDate((CommonFunction.checkNull(data.get(10)).trim()));
			 receiptvo.setInstrumentNumber((CommonFunction.checkNull(data.get(11)).trim()));
			 receiptvo.setBankAccount((CommonFunction.checkNull(data.get(12)).trim()));
			 receiptvo.setLbxBankID((CommonFunction.checkNull(data.get(13)).trim()));
			 logger.info(" savedReceipt setLbxBankID:-------------"+data.get(13));
			 receiptvo.setBank((CommonFunction.checkNull(data.get(14)).trim()));
			 receiptvo.setLbxBranchID((CommonFunction.checkNull(data.get(15)).trim()));
			 logger.info(" savedReceipt setLbxBranchID:-------------"+data.get(15));
			 receiptvo.setBranch((CommonFunction.checkNull(data.get(16)).trim()));
			 receiptvo.setMicr((CommonFunction.checkNull(data.get(17)).trim()));
			 receiptvo.setIfsCode((CommonFunction.checkNull(data.get(18)).trim()));
			 receiptvo.setAuthorRemarks((CommonFunction.checkNull(data.get(19)).trim()));
			 receiptvo.setInstrumentID((CommonFunction.checkNull(data.get(20)).trim()));
			 logger.info(" savedReceipt setInstrumentID:-------------"+data.get(20));
			 Number TdsAmount = 0;
			 if(!CommonFunction.checkNull(data.get(21)).equalsIgnoreCase(""))
	    	  {
				 TdsAmount = myFormatter.parse((CommonFunction.checkNull(data.get(21))).trim());
			  logger.info("TdsAmount: "+TdsAmount);    	 
			  
	    	  }
			 receiptvo.setTdsAmount(myFormatter.format(TdsAmount));
			 receiptvo.setReceiptNo((CommonFunction.checkNull(data.get(22)).trim()));
			 receiptvo.setRemarks((CommonFunction.checkNull(data.get(23)).trim()));
			 receiptvo.setLoanRecStatus((CommonFunction.checkNull(data.get(24)).trim()));
			 receiptvo.setPurpose((CommonFunction.checkNull(data.get(25)).trim()));
			 logger.info("setPurpose::::::::::::"+receiptvo.getPurpose()); 
			 receiptvo.setTransactionRefNo((CommonFunction.checkNull(data.get(26)).trim()));
			 receiptvo.setLoanBranch((CommonFunction.checkNull(data.get(27)).trim()));
			 receiptvo.setStatusReceipt((CommonFunction.checkNull(data.get(28)).trim()));
			 savedReceipt.add(receiptvo);	
		   }

		  }

		}catch(Exception e){
			e.printStackTrace();
				}
		return  savedReceipt;	
		}
		public ArrayList <ImdMakerVO> receiptdatatoApprove(ImdMakerVO vo)
		{
			 logger.info("In receiptdatatoApprove() of ReceiptDAOImpl");
			
		  ArrayList<ImdMakerVO> receiptApproveList=new ArrayList<ImdMakerVO>();
		  try{
			  ArrayList searchlist=new ArrayList();
		    logger.info("In searchAuthorData....................");
		    StringBuffer bufInsSql =	new StringBuffer();
		    
		    
		    bufInsSql.append(" SELECT DISTINCT CDL.DEAl_ID,CDL.DEAL_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " );		  
		    bufInsSql.append("   GM.DESCRIPTION,CID.BPID ,CDL.REC_STATUS, " );	
		    bufInsSql.append("  CID.INSTRUMENT_MODE,");
		    bufInsSql.append(dbo);
		    bufInsSql.append("date_format(CID.RECEIVED_DATE,'"+dateFormat+"') AS RECEIVED_DATE,CID.INSTRUMENT_AMOUNT,");
		    bufInsSql.append(dbo);
		    bufInsSql.append("date_format(CID.INSTRUMENT_DATE,'"+dateFormat+"') AS INSTRUMENT_DATE,CID.INSTRUMENT_NO," );
		    bufInsSql.append(" CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" );
		    bufInsSql.append("  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME," );
		    bufInsSql.append(" CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.REMARKS,CID.INSTRUMENT_ID,CID.TDS_AMOUNT,CID.RECIPT_NO,CID.MAKER_REMARKS,CDL.REC_STATUS,(select DESCRIPTION from generic_master where value=CID.RECEIPT_PURPOSE AND GENERIC_KEY='RECEIPT_PURPOSE' ) as RECEIPT_PURPOSE,CID.TRANSACTION_REF,CDL.DEAL_BRANCH,CID.MANUAL_AUTO_FLAG" );
		    bufInsSql.append("  from cr_instrument_dtl CID,CR_DEAL_CUSTOMER_M GCM ," );
		    bufInsSql.append(" cr_deal_dtl CDL,generic_master  GM" );
		    bufInsSql.append(" where CID.TXNID=CDL.DEAL_ID" );
		    bufInsSql.append("  AND GM.VALUE=CID.BPTYPE and CDL.DEAL_CUSTOMER_ID=GCM.CUSTOMER_ID ");
		    bufInsSql.append("  AND GM.GENERIC_KEY='BPTYPE' " );
		    bufInsSql.append("  and INSTRUMENT_TYPE='R'AND CID.REC_STATUS='F'" );
		    bufInsSql.append(" and ISNULL(CID.TXN_TYPE,'')='DC' AND CID.TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim())+"' ");
		    bufInsSql.append(" AND CID.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' ");
		
			  		
		   
			  logger.info("In savedReceipt......... query..........."+bufInsSql.toString());
		       searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		  logger.info("savedReceipt search Data size is...."+searchlist.size());

		  for(int i=0;i<searchlist.size();i++){
		  logger.info("savedReceipt search List "+searchlist.get(i).toString());
		  ArrayList data=(ArrayList)searchlist.get(i);

		  if(data.size()>0){
			  ImdMakerVO receiptvo = new ImdMakerVO();
			 receiptvo.setLbxDealNo((CommonFunction.checkNull(data.get(0)).trim()));
			 receiptvo.setDealNo((CommonFunction.checkNull(data.get(1)).trim()));
			 receiptvo.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
			  logger.info(" savedReceipt setCustomerName:-------------"+data.get(2));
			 receiptvo.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
			 receiptvo.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
			 receiptvo.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
			 //receiptvo.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
			 receiptvo.setReceiptMode((CommonFunction.checkNull(data.get(7)).trim()));
			 receiptvo.setReceiptDate((CommonFunction.checkNull(data.get(8)).trim()));
			 Number ReceiptAmount=0;
			 if(!CommonFunction.checkNull(data.get(9)).equals(""))
			{
			 ReceiptAmount = myFormatter.parse((CommonFunction.checkNull(data.get(9))).trim());
			}
			 logger.info("ReceiptAmount: "+ReceiptAmount);			
			 receiptvo.setReceiptAmount(myFormatter.format(ReceiptAmount));
			 receiptvo.setInstrumentDate((CommonFunction.checkNull(data.get(10)).trim()));
			 receiptvo.setInstrumentNumber((CommonFunction.checkNull(data.get(11)).trim()));
			 receiptvo.setBankAccount((CommonFunction.checkNull(data.get(12)).trim()));
			 receiptvo.setLbxBankID((CommonFunction.checkNull(data.get(13)).trim()));
			 logger.info(" savedReceipt setLbxBankID:-------------"+data.get(13));
			 receiptvo.setBank((CommonFunction.checkNull(data.get(14)).trim()));
			 receiptvo.setLbxBranchID((CommonFunction.checkNull(data.get(15)).trim()));
			 logger.info(" savedReceipt setLbxBranchID:-------------"+data.get(15));
			 receiptvo.setBranch((CommonFunction.checkNull(data.get(16)).trim()));
			 receiptvo.setMicr((CommonFunction.checkNull(data.get(17)).trim()));
			 receiptvo.setIfsCode((CommonFunction.checkNull(data.get(18)).trim()));
			 receiptvo.setAuthorRemarks((CommonFunction.checkNull(data.get(19)).trim()));
			 receiptvo.setInstrumentID((CommonFunction.checkNull(data.get(20)).trim()));
			
			 logger.info(" savedReceipt setInstrumentID:-------------"+data.get(20));
			 Number TdsAmount = 0;
			 if(!CommonFunction.checkNull(data.get(21)).equalsIgnoreCase(""))
	    	  {
				 TdsAmount = myFormatter.parse((CommonFunction.checkNull(data.get(21))).trim());
				 logger.info("TdsAmount: "+TdsAmount);			
				
	    	  }
			 receiptvo.setTdsAmount(myFormatter.format(TdsAmount));	
			 receiptvo.setReceiptNo((CommonFunction.checkNull(data.get(22)).trim()));
			 receiptvo.setRemarks((CommonFunction.checkNull(data.get(23)).trim()));

			 receiptvo.setLoanRecStatus((CommonFunction.checkNull(data.get(24)).trim()));
			 receiptvo.setPurpose((CommonFunction.checkNull(data.get(25)).trim()));
			 receiptvo.setTransactionRefNo((CommonFunction.checkNull(data.get(26)).trim()));
			 receiptvo.setLoanBranch((CommonFunction.checkNull(data.get(27)).trim()));
			 receiptvo.setStatusReceipt((CommonFunction.checkNull(data.get(28)).trim()));
			 receiptApproveList.add(receiptvo);	
		     }

		    }

		}catch(Exception e){
			e.printStackTrace();
				}
		return  receiptApproveList;	
		}
		 	
		 public boolean saveViewReceivable(ImdMakerVO receiptVO) {
				
			 boolean status=false;
			 StringBuilder bptype = new StringBuilder();
			 StringBuilder loanId = new StringBuilder();
			 StringBuilder deleteCount = new StringBuilder();
			 StringBuilder data = new StringBuilder();
			 
			try{
				ArrayList queryList=new ArrayList ();
				ArrayList subList =new ArrayList();
				ArrayList subList1 =new ArrayList();
				ArrayList list =new ArrayList();
				ArrayList dataList =new ArrayList();
				PrepStmtObject insertPrepStmtObject=null;
				StringBuffer bufInsSql = null;

					String allotedAmount[]=receiptVO.getAllocatedArry();
					String adviseId[]=receiptVO.getTxnAdvicedIDArry();//chargeCode_ID
					String chargeCode[]=receiptVO.getChargeCodeIDArry();
	                String tdsAllotedAmount[]=receiptVO.getTdsAllocatedArry();	  
			
	               	               
	              /*  String deleteTmpPayRecQuery="SELECT COUNT(1) FROM tmp_adv_payrec WHERE loan_id='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim())+ "' and maker_id='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getMakerId()).trim())+ "'";
	                logger.info("In saveViewReceivable..deleteTmpPayRecQuery. "+deleteTmpPayRecQuery);
					String deleteTmpPayRecCount=ConnectionDAO.singleReturn(deleteTmpPayRecQuery);
					logger.info("In saveViewReceivable... deleteTmpPayRecCount"+deleteTmpPayRecCount);
					 
					  if(!(deleteTmpPayRecCount).equalsIgnoreCase("0")){
						  
		                insertPrepStmtObject =  new PrepStmtObject();
		                bufInsSql = new StringBuffer();
		                bufInsSql.append(" DELETE FROM tmp_adv_payrec WHERE loan_id='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim())+ "' and maker_id='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getMakerId()).trim())+ "'");
		                logger.info("### bufInsSql delete TMPADVPAYREC Query #### "+bufInsSql.toString());
		                insertPrepStmtObject.setSql(bufInsSql.toString());
		                queryList.add(insertPrepStmtObject);  
		                
		            }*/
			// Prashant start here 		  
				  String deletePmntDtlQuery="SELECT COUNT(1) FROM cr_imd_dtl WHERE INSTRUMENT_ID='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+ "'";
				  logger.info("In saveViewReceivable..deletePmntDtlQuery. "+deletePmntDtlQuery);
				  String deletePmntDtlCount=ConnectionDAO.singleReturn(deletePmntDtlQuery);
				  logger.info("In saveViewReceivable... deletePmntDtlCount"+deletePmntDtlCount);
				 
				  if(!(deletePmntDtlCount).equalsIgnoreCase("0")){
					  
	                insertPrepStmtObject =  new PrepStmtObject();
	                bufInsSql = new StringBuffer();
	                bufInsSql.append(" DELETE FROM cr_imd_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+ "'");
	                logger.info("### bufInsSql delete IMD Query #### "+bufInsSql.toString());
	                insertPrepStmtObject.setSql(bufInsSql.toString());
	                queryList.add(insertPrepStmtObject);  
	                
	            }
			
				  //saurabh starts
				 status=true;
				 //saurabh ends	 
				// Prashant End here
				  
	            /*  data.append(" Select BP_TYPE,Loan_Id From cr_txnadvice_dtl WHERE TXNADVICE_ID='"+adviseId[0]+"'");
	              logger.info("### data qry is.. #### "+data);
	              dataList=ConnectionDAO.sqlSelect(data.toString());
	      		if(dataList.size()>0)
	      		{
	      			subList1= (ArrayList)dataList.get(0);
	      			if(subList1.size()>0){
	      				
	      				bptype.append((CommonFunction.checkNull(subList1.get(0)).trim()));
	      				loanId.append((CommonFunction.checkNull(subList1.get(1)).trim()));
	      				
	      			}
	      		}
	      		 logger.info("### bptype is.. #### "+bptype);
	      		 logger.info("### loanId is.. #### "+loanId);
	      		logger.info("### adviseId is.. #### "+adviseId.length);*/
	           
			 for(int i=0;i<adviseId.length;i++)
			 {
			   
			   insertPrepStmtObject=null;
	           bufInsSql=null;
	           insertPrepStmtObject =  new PrepStmtObject();
	           bufInsSql = new StringBuffer();
	           String alloc=CommonFunction.checkNull(allotedAmount[i].trim());

	           if(alloc.equalsIgnoreCase("")){
	        	   alloc="0"; 
	           }
	            double allocatedAmnt=0;
	            allocatedAmnt=myFormatter.parse(alloc).doubleValue();
	            logger.info("### In double adviseId #### "+adviseId);
	            logger.info("### In double allocatedAmnt #### "+allocatedAmnt);
	            
	              if(allocatedAmnt>0)
	              {
                        status=false; 
	                      
				  bufInsSql.append("insert into cr_imd_dtl( IMD_DATE,DEAL_ID,DEAL_CHARGE_DTL_ID,DEAL_CHARGE_CODE,IMD_AMOUNT,INSTRUMENT_ID,REC_STATUS,MAKER_ID,TDS_AMOUNT ,MAKER_DATE)");
				  bufInsSql.append(" values ( ");
				  bufInsSql.append(" (select ");
				
				  bufInsSql.append("TOP 1 ");
				 
				  bufInsSql.append("RECEIVED_DATE from cr_instrument_dtl where INSTRUMENT_ID=? ");
				 
				  bufInsSql.append("),");
			      bufInsSql.append(" ?," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(" 'P'," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(" ?," );
				  bufInsSql.append(dbo);
				  bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					
					

				logger.info("In........saveViewReceivable"+bufInsSql.toString());
				if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));	
				if((CommonFunction.checkNull(receiptVO.getLbxDealNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim()));					
				if((CommonFunction.checkNull(adviseId[i])).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(adviseId[i]).trim()));					
				if((CommonFunction.checkNull(chargeCode[i])).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(chargeCode[i]).trim()));					
				if((CommonFunction.checkNull(allotedAmount[i])).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((allotedAmount[i]).trim()).toString());					
				if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));			
				if((CommonFunction.checkNull(receiptVO.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getMakerId()).trim()));
				
				insertPrepStmtObject.addString("0.00");
			
				if((CommonFunction.checkNull(receiptVO.getBusinessDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getBusinessDate()).trim()));
				
			  insertPrepStmtObject.setSql(bufInsSql.toString());
			  queryList.add(insertPrepStmtObject);
				
				  logger.info("In insert of  cr_imd_dtl"+insertPrepStmtObject.printQuery());
			 
				  
				  insertPrepStmtObject =  new PrepStmtObject();
		           bufInsSql = new StringBuffer();
		         
     /*
         
		    bufInsSql.append(" Update tmp_adv_payrec Set PMNT_AMOUNT=?,TDS_ALLOCATED_AMOUNT=? WHERE  LOAN_ID=? And ADVICE_TYPE='R' And TXNADVICE_ID=? And BP_TYPE=?  And MAKER_ID=?");
				    if((CommonFunction.checkNull(allocatedAmnt)).trim().equalsIgnoreCase(""))
				    	insertPrepStmtObject.addString("0");
					else
						insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(allocatedAmnt).trim())).toString());
				    
				    if((CommonFunction.checkNull(tdsAllotedAmount[i])).equalsIgnoreCase(""))
						insertPrepStmtObject.addString("0");
					else
						insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(tdsAllotedAmount[i]).trim())).toString());
				
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
			}
			 if(queryList.size()>0){
				 status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
			 }
			logger.info("In saveViewReceivable,,,,,"+status);
			
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
				}

			return status;

	}
	
		 public  String existReceiptData(Object ob){
		  	  String result="";
		  	ImdMakerVO vo =  (ImdMakerVO)ob;
		  	StringBuilder existCount = new StringBuilder();
		  	   try{
		  		   existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim())+"'" +
		  	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim())+"'and cid.ISSUEING_BRANCH_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchID()).trim())+"' " +
		  	  		" and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim())+"'" +
		  	  		" and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim())+"' and INSTRUMENT_TYPE='R' AND REC_STATUS<>'X'");
		  
		  	 result=ConnectionDAO.singleReturn(existCount.toString());
		  		logger.info("IN existReceiptData  ### "+existCount.toString());
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
		  	  String result="";
		  	ImdMakerVO vo =  (ImdMakerVO)ob;
		  	StringBuilder existCount = new StringBuilder();
		  	   try{
		  		   existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim())+"'" +
		  	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim())+"' " +
		  	  		" and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim())+"'" +
		  	  		" and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim())+"' and INSTRUMENT_TYPE='R' AND REC_STATUS<>'X'");
		  
		  	 result=ConnectionDAO.singleReturn(existCount.toString());
		  		logger.info("IN existReceiptForNR Query--------- ### "+existCount.toString());
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
		
		 public boolean saveImdData(Object ob) {
			 ImdMakerVO vo =  (ImdMakerVO)ob;
				ArrayList<ImdMakerVO> getDataList=new ArrayList<ImdMakerVO>();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
				boolean status=false;	
				 logger.info("In saveReceiptData DAOImpl......................");
				 ArrayList qryList = new ArrayList();
				
					try{
						
						StringBuffer query =	new StringBuffer();
			        	query.append(" SELECT COUNT(1) FROM BUSINESS_PARTNER_VIEW WHERE BP_TYPE='CS' AND LOAN_ID='0' AND DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim())+"'");
					  	logger.info("Check Deal Existence in  BUSINESS_PARTNER_VIEW  query### "+query);
					    String DealExistence=CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
					    logger.info("In saveReceiptData DAOImpl DealExistence....."+DealExistence);
					    
					    if(DealExistence.equalsIgnoreCase("0")){
					    	
						    StringBuffer bufInsSql1 =	new StringBuffer();
						    insertPrepStmtObject=new PrepStmtObject();
						    bufInsSql1.append("INSERT INTO BUSINESS_PARTNER_VIEW (DEAL_ID,LOAN_ID,BP_TYPE,BP_ID,BP_NAME)");
						    bufInsSql1.append(" values ( ");
						    bufInsSql1.append(" ?," );
						    bufInsSql1.append(" '0'," );
						    bufInsSql1.append(" 'CS'," );
						    bufInsSql1.append(" ?," );
						    bufInsSql1.append(" ? )" );								
							
							if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxDealNo()).trim()));
							if((CommonFunction.checkNull(vo.getLbxBPNID())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
							
							if((CommonFunction.checkNull(vo.getCustomerName())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCustomerName()).trim()));
						    
							insertPrepStmtObject.setSql(bufInsSql1.toString());
							logger.info("IN SaveDatafor() insert BUSINESS_PARTNER_VIEW query ### "+insertPrepStmtObject.printQuery());
							qryList.add(insertPrepStmtObject);
					      }
					    StringBuffer bufInsSql =	new StringBuffer();
					    insertPrepStmtObject=new PrepStmtObject();
						bufInsSql.append("insert into cr_instrument_dtl (TXNID,TXN_TYPE,BPTYPE,BPID,INSTRUMENT_MODE,RECEIVED_DATE," +
								" INSTRUMENT_AMOUNT,INSTRUMENT_DATE,INSTRUMENT_NO,ISSUEING_BANK_ACCOUNT,ISSUEING_BANK_ID,ISSUEING_BRANCH_ID," +
								" ISSUING_MICR_CODE,ISSUING_IFSC_CODE,MAKER_REMARKS,TDS_AMOUNT,RECIPT_NO,REC_STATUS,INSTRUMENT_TYPE,PDC_FLAG,DEFAULT_BRANCH,RECEIPT_PURPOSE,TRANSACTION_REF,MAKER_ID,MAKER_DATE,MANUAL_AUTO_FLAG)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?," );
						bufInsSql.append(" 'DC'," );
						bufInsSql.append(" 'CS'," );
//						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(" ?," );
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );
						bufInsSql.append(" ?," );
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );
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
						bufInsSql.append(dbo);
						bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
						bufInsSql.append(" ? )" );
						
						
						logger.info("In........saveImdData"+bufInsSql.toString());
						if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxDealNo()).trim()));
//						if((CommonFunction.checkNull(vo.getLbxBPType())).trim().equalsIgnoreCase(""))
//							insertPrepStmtObject.addNull();
//						else
//							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxBPType()).trim()));
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
							insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
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
							insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getTdsAmount()).trim())).toString());
						
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
						if((CommonFunction.checkNull(vo.getStatusReceipt())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStatusReceipt()).trim()));
						
						
					
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN SaveDatafor() insert cr_instrument_dtl query1 ### "+insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
					
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				    logger.info("In saveReceiptData. cr_instrument_dtl ....................."+status);

				}
					catch(Exception e){
						e.printStackTrace();
					}
					
					return status;
				}
			
		
		 public boolean saveForwardUpdateOnReceipt(ImdMakerVO receiptVO){
				
			 ArrayList<ImdMakerVO> getDataList=new ArrayList<ImdMakerVO>();
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();	
				 StringBuffer bufInsSql =	new StringBuffer();
				boolean status=false;
				logger.info("In updateOnSave,,,,,");
				 ArrayList queryList=new ArrayList();
				try{
			
				 bufInsSql.append("update cr_instrument_dtl set ");
				 bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),");
				 bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
				 bufInsSql.append(" REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?,MAKER_ID=?,MAKER_DATE=");
				 bufInsSql.append(dbo);
			     bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					
				
				 bufInsSql.append("REC_STATUS='F',MANUAL_AUTO_FLAG=? where TXNID=?  ");
				 bufInsSql.append(" AND INSTRUMENT_ID=?");
					
				logger.info("In......saveForwardUpdateOnReceipt"+bufInsSql.toString());
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
						updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim())).toString());
				
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
						updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(receiptVO.getTdsAmount()).trim())).toString());
				 
				 if((CommonFunction.checkNull(receiptVO.getReceiptNo())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptNo()).trim()));


					//---------------------------------------------------------
					if ((CommonFunction.checkNull(receiptVO.getMakerId())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((receiptVO.getMakerId()).trim());
					if ((CommonFunction.checkNull(receiptVO.getBusinessDate()).trim()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getBusinessDate()).trim());
					
					 if((CommonFunction.checkNull(receiptVO.getStatusReceipt())).trim().equalsIgnoreCase(""))
						 updatePrepStmtObject.addNull();
						else
							updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getStatusReceipt()).trim()));
					//---------------------------------------------------------
				
				 if((CommonFunction.checkNull(receiptVO.getLbxDealNo())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim()));
				 
				 
				 if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
				 
				 updatePrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN saveForwardReceiptData cr_instrument_dtl query1 ### "+updatePrepStmtObject.printQuery());
					queryList.add(updatePrepStmtObject);
				
			
					logger.info("In saveAll update cr_instrument_dtl"+bufInsSql.toString());
					
				
					//mradul starts
					PrepStmtObject updatePrepStmtObject1 = new PrepStmtObject();	
	 			  	StringBuffer bufInsSql1 =	new StringBuffer();
	 			  	StringBuilder inventoryFlagInMst = new StringBuilder();
	 			  	StringBuilder existInStationary = new StringBuilder();
	 			    String inventoryFlag="";
	 			    String checkAllBranch="";
	 			    String checkUserAvailable="";
	 			    StringBuilder allBranch = new StringBuilder();
	 			  	StringBuilder checkUser = new StringBuilder();
					String resultStationary="";
					boolean updateStatus=false;
					String IssuingUser=receiptVO.getMakerId();
					logger.info("IssuingUser:::"+IssuingUser);
					logger.info("receipt default branch"+receiptVO.getDefaultBranch());		
					 
	 			  	inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='INVENTORY_FLAG'");
			  		logger.info("IN checkReciptStatus inventoryFlag ### "+inventoryFlagInMst.toString());
			  		inventoryFlag=ConnectionDAO.singleReturn(inventoryFlagInMst.toString());
		  		
		  	if(inventoryFlag.equalsIgnoreCase("Y") && !CommonFunction.checkNull(receiptVO.getReceiptNo()).equalsIgnoreCase(""))
		  	    {
		  		allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl INNER JOIN CR_STATIONARY_DTL ON CR_STATIONARY_DTL.BOOK_NO=CR_STATIONARY_STATUS_DTL.BOOK_NO WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"' AND STATUS='A' AND ISNULL(CR_STATIONARY_DTL.RETURN_TO_HO_FLAG,'')<>'R' ");
			  		logger.info("IN checkAllBranch ### "+allBranch.toString());
			  		checkAllBranch=ConnectionDAO.singleReturn(allBranch.toString());
		  		
		  	if(!(checkAllBranch.equalsIgnoreCase("0")))
		  		{
		  		 if(CommonFunction.checkNull(receiptVO.getRemarks()).equalsIgnoreCase(""))
		  		   {
		  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='USED BY IMD MAKER',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE="+dbo+"STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
		  		   }
		  		   else
		  		   {
		  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='"+receiptVO.getRemarks()+"',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE="+dbo+"STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
		  		   }
			  		logger.info("IN updateStationary  ### "+bufInsSql1.toString() + receiptVO.getReceiptNo());
			  	    updatePrepStmtObject1.setSql(bufInsSql1.toString());
			  		queryList.add(updatePrepStmtObject1);
			  		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
		  	}else{
			  		checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"' AND isnull(ISSUING_USER,'')<>''");
			  		logger.info("IN checkInventory inventoryFlag ### "+checkUser.toString());
			  		checkUserAvailable=ConnectionDAO.singleReturn(checkUser.toString());	
		  	if(!(checkUserAvailable.equalsIgnoreCase("0")))
				{
		  			existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'  AND isnull(ISSUING_USER,'"+IssuingUser+"')='"+IssuingUser+"' ");
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
		  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='USED BY IMD MAKER',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE="+dbo+"STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
		  		   }
		  		   else
		  		   {
		  			 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U',REMARKS='"+receiptVO.getRemarks()+"',USED_BY ='"+receiptVO.getMakerId()+"',USED_DATE="+dbo+"STR_TO_DATE('"+receiptVO.getBusinessDate()+"', '"+dateFormatWithTime+"')  where RECEPT_CHEQUE_NO='"+receiptVO.getReceiptNo()+"'");
		  		   }
			  		logger.info("IN updateStationary  ### "+bufInsSql1.toString() + receiptVO.getReceiptNo());
			  	    updatePrepStmtObject1.setSql(bufInsSql1.toString());
			  		queryList.add(updatePrepStmtObject1);
			  		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);				  		 
			}else{
			     	status=false;
			  	}
					logger.info("In saveForwardUpdateOnReceipt....................."+status);
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
			}catch(Exception e){
					e.printStackTrace();
			}
			return status;
	 	}
		 public boolean updateOnReceiptSave(ImdMakerVO receiptVO){
				
				
				ArrayList<ImdMakerVO> getDataList=new ArrayList<ImdMakerVO>();
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();	
				 StringBuffer bufInsSql =	new StringBuffer();
				boolean status=false;
				logger.info("In updateOnSave,,,,,");
				 ArrayList queryList=new ArrayList();
				try{

					
					 bufInsSql.append(" update cr_instrument_dtl set ");
					 bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=");
					 bufInsSql.append(dbo);
					 bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=");
					 bufInsSql.append(dbo);
					 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),");
					 bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,");
					 bufInsSql.append("  ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
					 bufInsSql.append("  MAKER_REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?,DEFAULT_BRANCH=?,RECEIPT_PURPOSE=?,TRANSACTION_REF=?,MAKER_ID=?,MAKER_DATE=");
					 bufInsSql.append(dbo);
					 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),MANUAL_AUTO_FLAG=?");
					
					
					 bufInsSql.append("  where TXNID=? and TXN_TYPE='DC' and INSTRUMENT_ID=?");
			
				logger.info("In...........updateOnReceiptSave"+bufInsSql.toString());
				 	 
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
						updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim())).toString());
				 
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

//					if((CommonFunction.checkNull(receiptVO.getLbxBPType())).trim().equalsIgnoreCase(""))
//						updatePrepStmtObject.addNull();
//					else
//						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBPType()).trim()));
//					if((CommonFunction.checkNull(receiptVO.getLbxBPNID())).trim().equalsIgnoreCase(""))
//						updatePrepStmtObject.addNull();
//					else
//						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxBPNID()).trim()));
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
						updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(receiptVO.getTdsAmount()).trim())).toString());
				 
				 if((CommonFunction.checkNull(receiptVO.getReceiptNo())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getReceiptNo()).trim()));

				//---------------------------------------------------------
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
					if ((CommonFunction.checkNull(receiptVO.getStatusReceipt()).trim()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(CommonFunction.checkNull(receiptVO.getStatusReceipt()).trim());
					//---------------------------------------------------------
				
				 if((CommonFunction.checkNull(receiptVO.getLbxDealNo())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim()));
				 
							 
				 if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
					 updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
				 
				 updatePrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN SaveDatafor() insert cr_instrument_dtl query1 ### "+updatePrepStmtObject.printQuery());
					queryList.add(updatePrepStmtObject);
				
					status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
					logger.info("In updateOnSave....................."+status);
				
				}catch(Exception e){
					e.printStackTrace();
				}

				return status;
			}

		 public String  checkFesiblityOnForward(ImdMakerVO receiptVO) {
				// ReceiptMakerVO vo= new ReceiptMakerVO();
//			  	  String query="";
//			  	  String query1="";
			  	  String status="";
			  	StringBuilder query = new StringBuilder();
			  	StringBuilder query1 = new StringBuilder();
			  	ArrayList<ImdMakerVO> List=new ArrayList ();
		         String receiptAmount=  CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim(); 
		         logger.info("In receiptAuthorGrid......receiptAmount  "+receiptAmount);
		         double receiptAmnt=0.0;
		         double tdsAmnt=0.0;
		         try{ 
		        	 if(receiptAmount.equalsIgnoreCase("")){
		        	 receiptAmount="0";  
		         }else{
		        	 receiptAmount= myFormatter.parse(CommonFunction.checkNull(receiptVO.getReceiptAmount())).toString();
		         }
		         receiptAmnt=Double.parseDouble(receiptAmount);
		         String tdsAmount=  CommonFunction.checkNull(receiptVO.getTdsAmount()).trim(); 
		         logger.info("In receiptAuthorGrid......tdsAmount  "+tdsAmount);
		         if(tdsAmount.equalsIgnoreCase("")){
		        	 
		        	 tdsAmount="0";  
		         }
		         else{
		        	 tdsAmount= myFormatter.parse(CommonFunction.checkNull(receiptVO.getTdsAmount())).toString();
		         }
		         tdsAmnt=Double.parseDouble(tdsAmount);
		         double Amount=(receiptAmnt + tdsAmnt);
		         logger.info("In receiptAuthorGrid......Amount  "+Amount);
		      
		        	 query.append(" SELECT ");
		        	 query.append("ISNULL(SUM(IMD_AMOUNT),0) FROM cr_imd_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+"'");
			  	  logger.info("IN checkAllocation  ### "+query.toString());
			     
			      String AllocatedAmount=CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
			      double VAllocatedAmount=0.0;
			      if(AllocatedAmount.equalsIgnoreCase("")){
			    	  AllocatedAmount="0";
			      }
			    	  VAllocatedAmount=Double.parseDouble(CommonFunction.checkNull(AllocatedAmount));

			  	  logger.info("VAllocatedAmount----"+VAllocatedAmount);
			  	  
			  	 query1.append(" SELECT ");
			  	 query1.append("ISNULL(SUM(TDS_AMOUNT),0) FROM cr_imd_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getInstrumentID()).trim())+"'");
			   		logger.info("IN checkAllocation 1  ### "+query1);
			  		String TdsAllocatedAmount =ConnectionDAO.singleReturn(query1.toString());  		 
			  		
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
		         }
			  	   catch(Exception e){
							e.printStackTrace();
						}
			  	   finally
			  	   {
			  		 query = null;
			  		 query1 = null;
			  	   }

						return status ;
		}
		 public  int existInsNo(ImdMakerVO vo){
		  	  int result=0;
		  	StringBuilder existCount = new StringBuilder();
		  	StringBuilder res = new StringBuilder();
		  
		  	   try{
		  		   existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim())+"'" +
		  	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim())+"'and cid.ISSUEING_BRANCH_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchID()).trim())+"'" +
		  	  	    " and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim())+"'" +
		  	  	    " and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim())+"' " +
		  	  	    " and INSTRUMENT_ID <>'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' and INSTRUMENT_TYPE='R'  AND REC_STATUS<>'X'");
		  	  
		  	//String res="";
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
		  		 existCount = null;
		  		 res = null;
		  	   }

					return result;
				}
		 public  int existInsNForNR(ImdMakerVO vo){
		  	  int result=0;
		  	StringBuilder existCount = new StringBuilder();
		  	StringBuilder res = new StringBuilder();
		  
		  	   try{
		  		   existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim())+"'" +
		  	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim())+"'" +
		  	  	    " and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim())+"'" +
		  	  	    " and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim())+"' " +
		  	  	    " and INSTRUMENT_ID <>'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' and INSTRUMENT_TYPE='R' AND REC_STATUS<>'X'");
		  	  
		  	//String res="";
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
		  		 existCount = null;
		  		 res = null;
		  	   }

					return result;
				}
		
		 public String saveForwardReceiptData(ImdMakerVO receiptVO,float amount){
			 
				
				ArrayList<ImdMakerVO> getDataList=new ArrayList<ImdMakerVO>();
				PrepStmtObject updatePrepStmtObject = new PrepStmtObject();	
				 StringBuffer bufInsSql =	new StringBuffer();
				boolean status=false;
				logger.info("In saveForwardReceiptData,,,,,");
				 ArrayList queryList=new ArrayList();
				 ArrayList arrList=new ArrayList();
				 String procval="";	
//					CallableStatement cst=null;
//					Connection con=ConnectionDAO.getConnection();  
				    ArrayList<Object> in =new ArrayList<Object>();
					ArrayList<Object> out =new ArrayList<Object>();
					ArrayList outMessages = new ArrayList();
					StringBuilder s1 = new StringBuilder();
					StringBuilder s2 = new StringBuilder();
					StringBuilder getProcessammountQuery = new StringBuilder();
					StringBuilder getAllotedAmtQuery = new StringBuilder();
//					String s1="";
//					String s2="";
				try{
					ArrayList txnAdviceId=new ArrayList();
					ArrayList amtInProcess=new ArrayList();
					ArrayList amtAllocated=new ArrayList();
				
//			         con.setAutoCommit(false);
					int statusProc=0;
					logger.info("BeforeProc: Imd_Maker_Validation_M ");
//					cst=con.prepareCall("call Receipt_Maker_Validation_M(?,?,?,?)");
					in.add(amount);
					in.add(((CommonFunction.checkNull(receiptVO.getInstrumentID().trim()))));			
//					cst.registerOutParameter(3, Types.CHAR);
//					cst.registerOutParameter(4, Types.CHAR);
					out.add(s1.toString());
				    out.add(s2.toString());
					logger.info("amount...."+amount);
					logger.info("in saveForwardReceiptData..getBusinessPartnerType........"+receiptVO.getLbxBPType());
					logger.info("in saveForwardReceiptData..getInstrumentID()........"+receiptVO.getInstrumentID());
					
//					statusProc=cst.executeUpdate();
//					String s1 = CommonFunction.checkNull(cst.getString(3));
//					String s2 = CommonFunction.checkNull(cst.getString(4));

				    outMessages=(ArrayList) ConnectionDAO.callSP("Imd_Maker_Validation_M",in,out);
				     s1.append(CommonFunction.checkNull(outMessages.get(0)));
					 s2.append(CommonFunction.checkNull(outMessages.get(1)));
					logger.info("Imd_Maker_Validation_M s1: "+s1);
					logger.info("Imd_Maker_Validation_M s2: "+s2);
					logger.info("Imd_Maker_Validation_M Status for Proc: "+statusProc);
				
					procval=CommonFunction.checkNull(s2.toString());
				/*	if((s1.toString()).equalsIgnoreCase("S"))
					{
						logger.info("After proc call..commit.error message."+s2);
//						con.commit();
//						con.close();
					}else{
						logger.info("After proc call..rollback.error message."+s2);
//						con.rollback();
//						con.close();
					}*/
if(!(s1.toString()).equalsIgnoreCase("E")){	
	if(!(s2.toString()).equalsIgnoreCase("F")){
						logger.info("After Proc inside If ");
						

						  
			 bufInsSql.append("update cr_instrument_dtl set ");
			 bufInsSql.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=");
			 bufInsSql.append(dbo);
			 bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=");
			 bufInsSql.append(dbo);
			 bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),");
			 bufInsSql.append(" ISSUEING_BANK_ACCOUNT=?,ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?,");
			 bufInsSql.append(" REMARKS=?,TDS_AMOUNT=?,RECIPT_NO=?, REC_STATUS='F',MAKER_ID=?,MAKER_DATE=");
			 bufInsSql.append(dbo);
			 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				
				
			 bufInsSql.append(" where TXNID=? AND TXN_TYPE='DC' ");
			 bufInsSql.append(" AND INSTRUMENT_ID=?");
				
			logger.info("In..............saveForwardReceiptData"+bufInsSql.toString());
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
					updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(receiptVO.getReceiptAmount()).trim())).toString());
			
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
					updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(receiptVO.getTdsAmount()).trim())).toString());
			 
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
			
			 if((CommonFunction.checkNull(receiptVO.getLbxDealNo())).trim().equalsIgnoreCase(""))
				 updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getLbxDealNo()).trim()));
			 
					 
			 if((CommonFunction.checkNull(receiptVO.getInstrumentID())).trim().equalsIgnoreCase(""))
				 updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString((CommonFunction.checkNull(receiptVO.getInstrumentID()).trim()));
			 
			 updatePrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveForwardReceiptData cr_instrument_dtl query1 ### "+updatePrepStmtObject.printQuery());
				queryList.add(updatePrepStmtObject);
			
			 
			// Prashant Start Here 
				
				//if(!CommonFunction.checkNull(receiptVO.getInstrumentID()).equalsIgnoreCase(""))
				//{
					StringBuilder getPmntIdsQuery=new StringBuilder();
					getPmntIdsQuery.append("select IMD_ID from cr_imd_dtl where ");
					getPmntIdsQuery.append("ISNULL(IMD_AMOUNT,0.00)=0.00 ");
					logger.info("In getImdIdsQuery---"+getPmntIdsQuery.toString());
					ArrayList getImdIds = ConnectionDAO.sqlSelect(getPmntIdsQuery.toString());	
					logger.info("In getPmntIds  size---"+getImdIds);
					if(getImdIds.size()>0)
					{
						  for(int j=0;j<getImdIds.size();j++){
							  
							  ArrayList subgetPmntIdsList=(ArrayList)getImdIds.get(j);
							  if(subgetPmntIdsList.size()>0){
								   String pmntId=subgetPmntIdsList.get(0).toString();
								   logger.info("In ImdIds  size---"+pmntId);
									updatePrepStmtObject = new PrepStmtObject();	
									bufInsSql =	new StringBuffer();
									bufInsSql.append(" Delete from cr_imd_dtl ");
									bufInsSql.append(" where IMD_ID=?");
									
									logger.info("In............saveForwardReceiptData.....delete query"+bufInsSql.toString());
									  if(CommonFunction.checkNull(pmntId).trim().equalsIgnoreCase(""))
											 updatePrepStmtObject.addNull();
											else
												updatePrepStmtObject.addString((CommonFunction.checkNull(pmntId).trim()));
									  
									
									 updatePrepStmtObject.setSql(bufInsSql.toString());
									logger.info("IN at saveForwardReceiptData() delete cr_imd_dtl query1 ### "+updatePrepStmtObject.printQuery());
									queryList.add(updatePrepStmtObject);
								   
							  }
						  }
						
					}
					
				//}
				// Prashant End Here 
				updatePrepStmtObject = new PrepStmtObject();	
				bufInsSql =	new StringBuffer();
				
				
				 bufInsSql.append(" update cr_imd_dtl set REC_STATUS='F',MAKER_ID=?,MAKER_DATE=");
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					
					
				 bufInsSql.append(" where   INSTRUMENT_ID=?");
				 
				 logger.info("In...saveForwardReceiptData..update..query"+bufInsSql.toString());
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
				logger.info("IN saveForwardReceiptData cr_imd_dtl query1 ### "+updatePrepStmtObject.printQuery());
				queryList.add(updatePrepStmtObject);
			


				
//				getProcessammountQuery.append("Select TXNADVICE_ID,AMOUNT_IN_PROCESS from tmp_adv_payrec " );
//				getProcessammountQuery.append(" WHERE ");
//				getProcessammountQuery.append("ISNULL(PMNT_AMOUNT,0)>0 AND LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim())+"' AND ADVICE_TYPE='R' " );
//				getProcessammountQuery.append(" and BP_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxBPType()).trim())+"' and " );
//				getProcessammountQuery.append(" maker_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getMakerId()).trim())+"' and PMNT_AMOUNT>0 order by TXNADVICE_ID ");
//			  logger.info("In getProcessammountQuery---"+getProcessammountQuery.toString());
//					
//			  ArrayList processAmmountList = ConnectionDAO.sqlSelect(getProcessammountQuery.toString());		
//			  
//			  getAllotedAmtQuery.append("Select  ");
//			  getAllotedAmtQuery.append("ISNULL(PMNT_AMOUNT,0 )from cr_pmnt_dtl where INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(receiptVO.getInstrumentID())+"' AND TXNADVICEID in " );
//			  getAllotedAmtQuery.append("(Select TXNADVICE_ID from tmp_adv_payrec " );
//			  getAllotedAmtQuery.append(" WHERE  ");
//			  getAllotedAmtQuery.append("ISNULL(PMNT_AMOUNT,0)>0 AND LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxLoanNoHID()).trim())+"' AND ADVICE_TYPE='R' " );
//			  getAllotedAmtQuery.append(" and BP_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getLbxBPType()).trim())+"' and " );
//			  getAllotedAmtQuery.append(" maker_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(receiptVO.getMakerId()).trim())+"') order by TXNADVICEID ");
//			  logger.info("In getAllotedAmtQuery---"+getAllotedAmtQuery.toString());
//			  ArrayList allotedAmmountList = ConnectionDAO.sqlSelect(getAllotedAmtQuery.toString());	
//			  
//			  logger.info("In allotedAmmountList  size---"+allotedAmmountList.size());
//			  logger.info("In processAmmountList  size---"+processAmmountList.size());
			  
			// Prashant Start Here 
//			  for(int j=0;j<processAmmountList.size();j++){
//				  
//				  ArrayList subprocessAmmountList=(ArrayList)processAmmountList.get(j);
//				
//			  
//				   if(subprocessAmmountList.size()>0){
//					   
//					   txnAdviceId.add(subprocessAmmountList.get(0));
//					   amtInProcess.add(subprocessAmmountList.get(1));
//					
//				   } 
//				   
//			  }
//			  
//       for(int j=0;j<allotedAmmountList.size();j++){
//				  
//				  ArrayList suballotedAmmountList=(ArrayList)allotedAmmountList.get(j);
//			  
//				   if(suballotedAmmountList.size()>0){
//					  
//					   amtAllocated.add(suballotedAmmountList.get(0));   
//				   } 
//				   
//			  }
    // Prashant END Here 
//			  for(int i=0;i<amtAllocated.size();i++){
//				  updatePrepStmtObject = new PrepStmtObject();	
//					bufInsSql =	new StringBuffer();
//					StringBuilder txnAdviceID = new StringBuilder();
//					txnAdviceID.append(CommonFunction.checkNull(txnAdviceId.get(i)));
//				 double sum=Double.parseDouble((String)amtInProcess.get(i))+Double.parseDouble((String)amtAllocated.get(i));
//				  logger.info("sum of Ammount in process and ammount allocated----- "+sum);
//				 
//				  bufInsSql.append(" Update cr_txnadvice_dtl set AMOUNT_IN_PROCESS=?" );
//				  bufInsSql.append(" where TXNADVICE_ID=?");
//	
//				  logger.info("In...saveForwardReceiptData..update query1"+bufInsSql.toString());
//				  if((CommonFunction.checkNull(sum)).trim().equalsIgnoreCase(""))
//						 updatePrepStmtObject.addString("0");
//						else
//							updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(sum).trim())).toString());
//				  if((CommonFunction.checkNull(txnAdviceID.toString())).trim().equalsIgnoreCase(""))
//						 updatePrepStmtObject.addNull();
//						else
//							updatePrepStmtObject.addString((CommonFunction.checkNull(txnAdviceID.toString()).trim()));
//				  
//				
//				 updatePrepStmtObject.setSql(bufInsSql.toString());
//				logger.info("IN SaveDatafor() Update cr_txnadvice_dtl query1 ### "+updatePrepStmtObject.printQuery());
//				queryList.add(updatePrepStmtObject);
//				//logger.info("Update Query----- "+updateQuery);
//				  logger.info("count i"+i);
//				 // queryList.add(updateQuery);
//				  txnAdviceID = null;
//			  }
			  logger.info("Update Query----- "+queryList);
			  
			  status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
				logger.info("In saveForwardReceiptData,,,,,"+status);
				
}}//end of if	
					if(status){
						procval="queryexecuted";
						}
				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					s1 = null;
					s2 = null;
					getProcessammountQuery = null;
					getAllotedAmtQuery = null;
				}

				
				return procval;
			}
		 
		 public ArrayList<ImdMakerVO>onAllocatedReceivable(ImdMakerVO receiptVO,int loanId,String bpType,int instrumentId)
			{
		     ArrayList<ImdMakerVO> allocatedList=new ArrayList<ImdMakerVO>();
				//CallableStatement cst=null;
		 		//Connection con=ConnectionDAO.getConnection();
		     StringBuilder query = new StringBuilder();
		 				
			 	try{
			 		ArrayList mainList=new ArrayList ();
			 		ArrayList subList =new ArrayList();
			 
			 		logger.info(" In onAllocatedReceivable....");	
			 	
			 		query.append("SELECT  distinct ");
			 		query.append(dbo);
			 		query.append("DATE_FORMAT(ADVICE_DATE,'"+dateFormat+"') AS ADVICE_DATE," );
			 		query.append(" (Select CHARGE_DESC From com_charge_code_m Where CHARGE_CODE=CTD.CHARGE_CODE_ID) CHARGE, ADVICE_AMOUNT,  ");
			 		query.append(" (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,   ");
			 		query.append(" AMOUNT_IN_PROCESS,CTD.TDS_AMOUNT, PMNT_AMOUNT,CPD.TDS_AMOUNT AS TDS_ALLOCATED_AMOUNT ");
			 		query.append(" from cr_txnadvice_dtl CTD,cr_pmnt_dtl CPD  where CPD.TXNADVICEID=CTD.TXNADVICE_ID ");
			 		query.append(" AND ADVICE_TYPE='R' ");
			 		query.append("  AND LOAN_ID='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+ "'" );
			 		query.append(" AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "' " );
			 		query.append(" AND CPD.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentId).trim()) + "'" );
			 		query.append("  Order By CTD.ADVICE_DATE ASC ");

			 		
			 		logger.info("In onAllocatedReceivable"+query.toString());	
			 		
			 		mainList=ConnectionDAO.sqlSelect(query.toString());
			 		for(int i=0;i<mainList.size();i++)
			 		{
			 			subList= (ArrayList)mainList.get(i);
			 			if(subList.size()>0){
			 				
			 				ImdMakerVO VO = new ImdMakerVO();
			 				VO.setReceiptDate((CommonFunction.checkNull(subList.get(0)).trim()));
			 				logger.info("setPaymentDate: "+subList.get(0));		
			 				VO.setChargeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
			 				Number OriginalAmount =0;
			 				if(!CommonFunction.checkNull(subList.get(2)).equals(""))
			 				{
			 					OriginalAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
			 				}
			 			    logger.info("OriginalAmount: "+OriginalAmount);			
			 		    	VO.setOriginalAmount(myFormatter.format(OriginalAmount));
			 		    	Number BalanceAmount =0;
			 		    	if(!CommonFunction.checkNull(subList.get(3)).equals(""))
			 				{
			 		    		BalanceAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
			 				}
			 				logger.info("BalanceAmount: "+BalanceAmount);			
			 		    	VO.setBalanceAmount(myFormatter.format(BalanceAmount));
			 		    	Number AmountInProcess =0;
			 		    	if(!CommonFunction.checkNull(subList.get(4)).equals(""))
			 				{
			 		    		AmountInProcess = myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());
			 				}
				 			logger.info("AmountInProcess: "+AmountInProcess);			
				 		    VO.setAmountInProcess(myFormatter.format(AmountInProcess));
				 		   Number TdsadviseAmount = 0;
				 		    if(!CommonFunction.checkNull(subList.get(5)).equals(""))
			 				{
				 		    	TdsadviseAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
			 				}
			 			    logger.info("TdsadviseAmount: "+TdsadviseAmount);			
			 		    	VO.setTdsadviseAmount(myFormatter.format(TdsadviseAmount));	
			 		    	Number AllotedAmount = 0;
			 		    	if(!CommonFunction.checkNull(subList.get(6)).equals(""))
			 				{
				 		    	AllotedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
			 				}
				 			logger.info("AllotedAmount: "+AllotedAmount);			 		    	
			 			    VO.setAllotedAmount(myFormatter.format(AllotedAmount));
			 			    if(!CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase("")){
			 				Number TdsAllocatedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());
				 			logger.info("TdsAllocatedAmount: "+TdsAllocatedAmount);			 		    	
			 				VO.setTdsAllocatedAmount(myFormatter.format(TdsAllocatedAmount));
			 			    }
			 			    else {
			 			    	VO.setTdsAllocatedAmount("0");
			 			    }
			 			    	
			 				allocatedList.add(VO);
			 			}
			 		}
			 			}catch(Exception e){
			 				e.printStackTrace();
			 			}
			 			finally
			 			{
			 				query = null;
			 			}


			 			return allocatedList;
			 		}	
		 public String onSaveofImdAuthor(ImdMakerVO receiptVO) {
				boolean status=false;
				String procval="";
			    ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				StringBuilder s1 = new StringBuilder();
				StringBuilder s2 = new StringBuilder();
//				 String s1="";
//				 String s2="";
//				Connection con=ConnectionDAO.getConnection();
//				CallableStatement cst=null;
				try{
				 
				ArrayList queryList=new ArrayList();
				
				int statusProc=0;
			
//				con.setAutoCommit(false);
				logger.info(" In pay_approveReject_Rec onSaveofReceiptAuthor BeforeProc: ");
				logger.info("In pay_approveReject_Rec onSaveofReceiptAuthor getInstrumentID()"+receiptVO.getInstrumentID());
				logger.info("In onSaveofReceiptAuthor getDecision"+receiptVO.getDecision());
				logger.info("In onSaveofReceiptAuthor getComments()"+receiptVO.getComments());
//		          cst=con.prepareCall("call pay_approveReject_Rec(?,?,?,?,?,?)");
				
				in.add(receiptVO.getInstrumentID());		
				in.add(receiptVO.getDecision());
				in.add(receiptVO.getComments());
				in.add("R");
				in.add(((CommonFunction.checkNull(receiptVO.getUserId().trim()))));
				String date=CommonFunction.changeFormat(receiptVO.getBusinessDate());
				in.add(date);
				out.add(s1.toString());
			    out.add(s2.toString());
//				cst.registerOutParameter(5, Types.CHAR);
//				cst.registerOutParameter(6, Types.CHAR);
//				statusProc=cst.executeUpdate();
//				String s1 = CommonFunction.checkNull(cst.getString(5));
//				String s2 = CommonFunction.checkNull(cst.getString(6));
				  outMessages=(ArrayList) ConnectionDAO.callSP("IMD_ApproveReject_Rec",in,out);
				    s1.append(CommonFunction.checkNull(outMessages.get(0)));
				    s2.append(CommonFunction.checkNull(outMessages.get(1)));
				logger.info("s1: "+s1);
				logger.info("s2: "+s2);
				logger.info("pay_approveReject_Rec Status for Proc: "+statusProc);
				logger.info("pay_approveReject_Rec After Proc: ");
				procval=(s2.toString());
				if(!(s1.toString()).equalsIgnoreCase("S")){
					logger.info("After Proc inside If "); 
					status=false;
				}else
					status=true;  
					 if((s1.toString()).equalsIgnoreCase("S"))  
					 {
							logger.info("After proc call..commit.error message."+s2);
//							con.commit();
//							con.close();
					}else{
							logger.info("After proc call..rollback.error message."+s2);
//							con.rollback();
//							con.close();
					}
				
				/*String query="update cr_instrument_dtl set REC_STATUS='"+receiptVO.getDecision()+"'" +
				" ,REMARKS='"+receiptVO.getComments()+"' where INSTRUMENT_ID='"+receiptVO.getInstrumentID()+"' and REC_STATUS ='F'";	

				queryList.add(query);
				logger.info("In updateFlagByPaymentAuthor"+query);
				
				 String	query1="update cr_pmnt_dtl set REC_STATUS='"+receiptVO.getDecision()+"' where  INSTRUMENT_ID='"+receiptVO.getInstrumentID()+"'  and REC_STATUS ='F'";
					
				
				queryList.add(query1);
				logger.info("In updateFlagByPaymentAuthor"+query1);	
				
				status =ConnectionDAO.sqlInsUpdDelete(queryList);*/
				logger.info("In onSaveofReceiptAuthor,,,,,"+status);

				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					s1 = null;
					s2 = null;
				}

				
				return procval;
				}

			public ArrayList<ImdMakerVO> getchequeDetailR(String bankAccount,String accountType) {
				
				
				ArrayList<ImdMakerVO> getChequeList=new ArrayList<ImdMakerVO>();
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
					for(int i=0;i<mainList.size();i++)
					{
						subList= (ArrayList)mainList.get(i);
						if(subList.size()>0){
							ImdMakerVO receiptVo = new ImdMakerVO();
							 receiptVo.setBankAccount((CommonFunction.checkNull(subList.get(0)).trim()));
							 receiptVo.setLbxBankID((CommonFunction.checkNull(subList.get(1)).trim()));
							 receiptVo.setBank((CommonFunction.checkNull(subList.get(2)).trim()));
							 receiptVo.setLbxBranchID((CommonFunction.checkNull(subList.get(3)).trim()));
							 receiptVo.setBranch((CommonFunction.checkNull(subList.get(4)).trim()));
							 receiptVo.setMicr((CommonFunction.checkNull( subList.get(5)).trim()));
							 receiptVo.setIfsCode((CommonFunction.checkNull( subList.get(6)).trim()));
							getChequeList.add(receiptVo);
						}
					}
						}catch(Exception e){
							e.printStackTrace();
						}
						finally
						{
							query = null;
						}


						return getChequeList;
					}
		
//Ritu
	
			public boolean deleteReceipt(String id) {
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
				
				query1.append ("delete from cr_pmnt_dtl where instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
				list1.add(query1.toString());
				logger.info("delete()     query1------------------" + query1.toString());
				status1 =ConnectionDAO.sqlInsUpdDelete(list1);
				status =ConnectionDAO.sqlInsUpdDelete(list);
				}
			  catch (Exception e) {
					e.printStackTrace();
				}
			  return status;

			}

			public String getTotalRec(String loanId, String bPType) 
			{
				logger.info("In getTotalRec().");
				String amount="0.00";
				StringBuilder query = new StringBuilder();				
				try
				{
//					query.append(" SELECT sum(ADVICE_AMOUNT-TXN_ADJUSTED_AMOUNT-AMOUNT_IN_PROCESS) as BALANCE_AMOUNT FROM cr_txnadvice_dtl A   WHERE A.REC_STATUS IN ('A', 'F') and A.ADVICE_TYPE='R' AND LOAN_ID='"+loanId.trim()+"' AND BP_TYPE='"+bPType.trim()+"'");
//					logger.info("In getTotalRec() query  :  "+query.toString());						
//					amount=ConnectionDAO.singleReturn(query.toString());
//					logger.info("In recAmount  :  "+amount);
//					if(CommonFunction.checkNull(amount).trim().equalsIgnoreCase(""))
//						amount="0.00";
//					Number totalRecAmt = myFormatter.parse((CommonFunction.checkNull(amount)).trim());
//					amount=myFormatter.format(totalRecAmt);
//					logger.info("In recAmount after formating  :  "+amount);
				}
				catch(Exception e)
				{e.printStackTrace();}
				finally
				{
					query = null;
				}
				return amount;
			}
			
			public String receiptNoCheckFlag() 
			{
				String flag="";
				StringBuilder query = new StringBuilder();
				query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='RECEIPT_NO_CHECK_FLAG'");
				logger.info("Query for getting receiptNoCheckFlag from parameter_mst  : "+query.toString());
				try
				{
					flag = ConnectionDAO.singleReturn(query.toString());
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
				arrList = ConnectionDAO.sqlSelect(query.toString());
				for(int i=0;i<arrList.size();i++){

					subList=(ArrayList)arrList.get(i);
					logger.info("In receiptPurposeList..."+subList.size());

						if(subList.size()>0){
			
								ImdMakerVO receiptVo = new ImdMakerVO();
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
					subList = null;
					arrList = null;
				}
				
					
				return mainList;
			}
//change by sachin
			@Override
			public String checkReciptStatus(Object ob) {
				
				 String result="";
				 ImdMakerVO vo =  (ImdMakerVO)ob;
				  	StringBuilder existCount = new StringBuilder();
				  	   try{
				  		   existCount.append("SELECT COUNT(1)  from cr_instrument_dtl where ");
				  		   existCount.append("ISNULL(REC_STATUS,'')<>'X' AND RECIPT_NO<>'' AND RECIPT_NO='"+vo.getReceiptNo()+"' ");
				  
				  	 result=ConnectionDAO.singleReturn(existCount.toString());
				  		logger.info("IN checkReceiptstatus  ### "+existCount.toString());
				    }
				  	   catch(Exception e){
								e.printStackTrace();
							}
				  	   finally
				  	   {
				  		 existCount = null;
				  	   }

							return result;

				// TODO Auto-generated method stub
				
			}
//end by sachin

			@Override
			public String checkReciptUpdateStatus(Object ob) {
				// TODO Auto-generated method stub
				String result="";
				ImdMakerVO vo =  (ImdMakerVO)ob;
			  	StringBuilder existCount = new StringBuilder();
			  	   try{
			  		   existCount.append("SELECT COUNT(1)  from cr_instrument_dtl where ");
			  		   existCount.append("ISNULL(REC_STATUS,'')<>'X' AND RECIPT_NO='"+vo.getReceiptNo()+"' AND INSTRUMENT_ID<>'"+vo.getInstrumentID()+"' ");
			  
			  	 result=ConnectionDAO.singleReturn(existCount.toString());
			  		logger.info("IN checkReciptUpdateStatus  ### "+existCount.toString());
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
			
			public String getinstrumentId(){
				String instId="";
				StringBuilder instrumentId = new StringBuilder();
				try{
					instrumentId.append("SELECT max(INSTRUMENT_ID) from cr_instrument_dtl  WITH (ROWLOCK) ");
					
					instId=ConnectionDAO.singleReturn(instrumentId.toString());
			  		logger.info("IN paymentId ### "+instrumentId);
			    }
			  	   catch(Exception e){
							e.printStackTrace();
						}
			  	   finally
			  	   {
			  		 instrumentId = null;
			  	   }

						return instId;
					}
			
			public ArrayList getchargesDetail(String dealCap,String dealId) 
			{
				ArrayList list =new ArrayList();	
				try
				{			
					ChargeVo vo =null;
					StringBuilder query=new StringBuilder();
					 query.append("select t.DEAL_CHARGE_DTL_ID,DEAL_CHARGE_TYPE,t.DEAL_CHARGE_CODE,CHARGE_DESC," );
					 query.append(" GM.DESCRIPTION,v.DEALER_DESC," );
					 query.append(" DEAL_CHARGE_CALCULATED_AMOUNT,DEAL_CHARGE_FINAL_AMOUNT,d.CUSTOMER_NAME,p.DEAL_LOAN_AMOUNT,(");
					 query.append("ISNULL(P.DEAL_ASSET_COST,0)-");
					 query.append("ISNULL(P.DEAL_LOAN_AMOUNT,0)) AS 'MARGIN AMOUNT'," );
					 query.append(" DEAL_CHARGE_CALCULATED_ON,");
					 query.append("case when DEAL_CHARGE_METHOD='P' then 'PERCENTAGE' else 'FLAT' end as DEAL_CHARGE_METHOD,");
					 query.append("case when DEAL_CHARGE_TAX_INCLUSIVE='N' then 'NO' else 'YES' end as DEAL_CHARGE_TAX_INCLUSIVE,");
					 query.append(" DEAL_CHARGE_TAX_RATE1,DEAL_CHARGE_TAX_RATE2,DEAL_CHARGE_BP_TYPE,");
					 query.append("case when DEAL_MIN_CHARGE_METHOD='P' then 'PERCENTAGE' else 'FLAT' end as DEAL_MIN_CHARGE_METHOD,");
					 query.append("DEAL_CHARGE_MIN_CHARGE_AMOUNT," );
					 query.append(" DEAL_CHARGE_TAX_APPLICABLE,DEAL_CHARGE_TDS_APPLICABLE,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_TAX_AMOUNT2,DEAL_CHARGE_MIN_CHARGE_AMOUNT,DEAL_CHARGE_TDS_RATE,DEAL_CHARGE_TDS_AMOUNT,DEAL_CHARGE_NET_AMOUNT,DEAL_CHARGE_APPLICATION_STAGE " );
					 query.append(" from cr_deal_txncharges_dtl t " );
					 query.append(" left join com_charge_code_m c on t.DEAL_CHARGE_CODE=c.CHARGE_CODE " );
					 query.append(" left join cr_dsa_dealer_m v on v.DEALER_ID=t.DEAL_CHARGE_BP_ID and v.BP_TYPE=t.DEAL_CHARGE_BP_TYPE and v.REC_STATUS='A' " );
					 query.append(" left join cr_deal_customer_m d on d.CUSTOMER_ID=t.DEAL_CHARGE_BP_ID ");
					 query.append(" left join cr_deal_loan_dtl p on p.DEAL_ID=t.DEAL_ID  ");
					 query.append(" left join generic_master GM on GM.VALUE=DEAL_CHARGE_BP_TYPE and GM.GENERIC_KEY='BPTYPE' ");
					 query.append(" where  t.DEAL_CHARGE_APPLICATION_STAGE='LIM' AND t.DEAL_CHARGE_TYPE='R' AND t.DEAL_CHARGE_BP_TYPE='CS'");
					 query.append(" AND DEAL_CHARGE_NET_AMOUNT >0 AND t.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
					logger.info("getchargesDetail in deal  query "+query.toString());
					ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
					logger.info("getchargesDetail in deal  size "+charges.size());
					
					query=null;
					
					for(int i=0;i<charges.size();i++){
					//logger.info("showUnderwritingQueryData...FOR loop "+CommonFunction.checkNull(product.get(i)).toString());
					ArrayList subcharges=(ArrayList)charges.get(i);
					if(subcharges.size()>0)	
					{
						vo = new ChargeVo();
						vo.setChargeId((CommonFunction.checkNull(subcharges.get(0))).trim());
						if((CommonFunction.checkNull(subcharges.get(1)).trim()).equals("R"))
						{
							vo.setChargeType("Receivable");
						}
						else if((CommonFunction.checkNull(subcharges.get(1)).trim()).equals("P"))
						{
							vo.setChargeType("Payable");
						}
						vo.setChargeCode((CommonFunction.checkNull(subcharges.get(2))).trim());
						vo.setChargeDesc((CommonFunction.checkNull(subcharges.get(3))).trim());
						vo.setChargeBPType((CommonFunction.checkNull(subcharges.get(4)).trim()));
						if((CommonFunction.checkNull(subcharges.get(16)).trim()).equals("CS"))
						{						
							vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(8))).trim());
						}
						else
						{
							vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(5))).trim());
						}
						if(!CommonFunction.checkNull(subcharges.get(6)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(6))).trim());
			   	    		vo.setChargeCal(myFormatter.format(reconNum));
			    		}
						if(!CommonFunction.checkNull(subcharges.get(7)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(7))).trim());
			   	    		vo.setChargeFinal(myFormatter.format(reconNum));
			    		}
					
						vo.setLoanAmount((CommonFunction.checkNull(subcharges.get(9))).trim());
						vo.setMarginAmount((CommonFunction.checkNull(subcharges.get(10))).trim());
						vo.setChargeCalculatedOn((CommonFunction.checkNull(subcharges.get(11))).trim());
						vo.setChargeMethod((CommonFunction.checkNull(subcharges.get(12))).trim());
						vo.setTaxsInclusive((CommonFunction.checkNull(subcharges.get(13))).trim());
						if(!CommonFunction.checkNull(subcharges.get(14)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(14))).trim());
			   	    		vo.setTaxtRat1(myFormatter.format(reconNum));
			    		}
						if(!CommonFunction.checkNull(subcharges.get(15)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(15))).trim());
			   	    		logger.info("setTaxtRat2: "+reconNum);
			   	    		vo.setTaxtRat2(myFormatter.format(reconNum));
			    		}
						vo.setMinChargeMethod((CommonFunction.checkNull(subcharges.get(17))).trim());
						vo.setMinChargeCalculatedOn((CommonFunction.checkNull(subcharges.get(18))).trim());
						vo.setDealChargeTaxApp((CommonFunction.checkNull(subcharges.get(19))).trim());
						vo.setDealChargeTdsApp((CommonFunction.checkNull(subcharges.get(20))).trim());
						if(!CommonFunction.checkNull(subcharges.get(21)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(21))).trim());
			   	    		vo.setDealChargeTaxAmount1(myFormatter.format(reconNum));
			    		}
						if(!CommonFunction.checkNull(subcharges.get(22)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(22))).trim());
			   	    		//logger.info("setTaxtRat2: "+reconNum);
			   	    		vo.setDealChargeTaxAmount2(myFormatter.format(reconNum));
			    		}
						if(!CommonFunction.checkNull(subcharges.get(23)).equalsIgnoreCase(""))
						{
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(23))).trim());
				    		vo.setDealChargeMinChargeAmount(myFormatter.format(reconNum));
			    	    }
						if(!CommonFunction.checkNull(subcharges.get(24)).equalsIgnoreCase(""))
			    	    {
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(24))).trim());
				    		//logger.info("setTaxtRat2: "+reconNum);
				    		vo.setDealChargeTdsRate(myFormatter.format(reconNum));
			    	    }
						if(!CommonFunction.checkNull(subcharges.get(25)).equalsIgnoreCase(""))
			    	    {
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(25))).trim());
				    		vo.setDealChargeTdsAmount(myFormatter.format(reconNum));
			    	    }
						if(!CommonFunction.checkNull(subcharges.get(26)).equalsIgnoreCase(""))
			    	    {
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(26))).trim());
				    		//logger.info("setTaxtRat2: "+reconNum);
				    		vo.setDealChargeNetAmount(myFormatter.format(reconNum));
			    	    }
						vo.setApplStage(CommonFunction.checkNull(subcharges.get(27)));

						list.add(vo);
					 }
					}
				}
				catch (Exception e) 
				{e.printStackTrace();}
				return list;
			}
			
			public ArrayList getAlocChargesDetail(String instrument,String dealId) 
			{
				ArrayList list =new ArrayList();
				String instID="";
				instID=StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrument)).trim();
				logger.info("instID instID instID ::::::::::"+instID);
				try
				{			
					ChargeVo vo =null;
					StringBuilder query=new StringBuilder();
					 query.append("select t.DEAL_CHARGE_DTL_ID,DEAL_CHARGE_TYPE,t.DEAL_CHARGE_CODE,CHARGE_DESC," );
					 query.append(" GM.DESCRIPTION,v.DEALER_DESC," );
					 query.append(" DEAL_CHARGE_CALCULATED_AMOUNT,DEAL_CHARGE_FINAL_AMOUNT,d.CUSTOMER_NAME,p.DEAL_LOAN_AMOUNT,(");
					 query.append("ISNULL(P.DEAL_ASSET_COST,0)-");
					 query.append("ISNULL(P.DEAL_LOAN_AMOUNT,0)) AS 'MARGIN AMOUNT'," );
					 query.append(" DEAL_CHARGE_CALCULATED_ON,");
					 query.append("case when DEAL_CHARGE_METHOD='P' then 'PERCENTAGE' else 'FLAT' end as DEAL_CHARGE_METHOD,");
					 query.append("case when DEAL_CHARGE_TAX_INCLUSIVE='N' then 'NO' else 'YES' end as DEAL_CHARGE_TAX_INCLUSIVE,");
					 query.append(" DEAL_CHARGE_TAX_RATE1,DEAL_CHARGE_TAX_RATE2,DEAL_CHARGE_BP_TYPE,");
					 query.append("case when DEAL_MIN_CHARGE_METHOD='P' then 'PERCENTAGE' else 'FLAT' end as DEAL_MIN_CHARGE_METHOD,");
					 query.append("DEAL_CHARGE_MIN_CHARGE_AMOUNT," );
					 query.append(" DEAL_CHARGE_TAX_APPLICABLE,DEAL_CHARGE_TDS_APPLICABLE,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_TAX_AMOUNT2,DEAL_CHARGE_MIN_CHARGE_AMOUNT,DEAL_CHARGE_TDS_RATE,DEAL_CHARGE_TDS_AMOUNT,DEAL_CHARGE_NET_AMOUNT,DEAL_CHARGE_APPLICATION_STAGE, " );
					 query.append(" (DEAL_CHARGE_FINAL_AMOUNT-ISNULL(imd.imd_amount,0))balanceAmount, ISNULL(currentImd.imd_amount,0)imd_amount");
					 query.append(" from cr_deal_txncharges_dtl t " );
					 query.append(" left join ( select deal_id,deal_charge_code,sum(imd_amount) imd_amount from cr_imd_dtl where instrument_id !='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrument)).trim()+"' and rec_status <>'X' ");
					 query.append(" group by deal_id,deal_charge_code	)imd on(imd.deal_id= t.DEAL_ID and t.deal_charge_code=imd.deal_charge_code) ");
					 query.append(" left join	( select deal_id,deal_charge_code,sum(imd_amount) imd_amount from cr_imd_dtl where instrument_id ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrument)).trim()+"' and rec_status <>'X' ");
					 query.append(" group by deal_id,deal_charge_code	)currentImd on(currentImd.deal_id= t.DEAL_ID and t.deal_charge_code=currentImd.deal_charge_code) ");
					 query.append(" left join com_charge_code_m c on t.DEAL_CHARGE_CODE=c.CHARGE_CODE " );
					 query.append(" left join cr_dsa_dealer_m v on v.DEALER_ID=t.DEAL_CHARGE_BP_ID and v.BP_TYPE=t.DEAL_CHARGE_BP_TYPE and v.REC_STATUS='A' " );
					 query.append(" left join cr_deal_customer_m d on d.CUSTOMER_ID=t.DEAL_CHARGE_BP_ID ");
					 query.append(" left join cr_deal_loan_dtl p on p.DEAL_ID=t.DEAL_ID  ");
					 query.append(" left join generic_master GM on GM.VALUE=DEAL_CHARGE_BP_TYPE and GM.GENERIC_KEY='BPTYPE' ");
					 query.append(" where  t.DEAL_CHARGE_APPLICATION_STAGE='LIM' AND t.DEAL_CHARGE_TYPE='R' AND t.DEAL_CHARGE_BP_TYPE='CS'");
					 query.append(" AND DEAL_CHARGE_NET_AMOUNT >0 AND t.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
					logger.info("getchargesDetail in deal  query "+query.toString());
					ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
					logger.info("getchargesDetail in deal  size "+charges.size());
					
					query=null;
					
					for(int i=0;i<charges.size();i++){
					//logger.info("showUnderwritingQueryData...FOR loop "+CommonFunction.checkNull(product.get(i)).toString());
					ArrayList subcharges=(ArrayList)charges.get(i);
					if(subcharges.size()>0)	
					{
						vo = new ChargeVo();
						vo.setChargeId((CommonFunction.checkNull(subcharges.get(0))).trim());
						if((CommonFunction.checkNull(subcharges.get(1)).trim()).equals("R"))
						{
							vo.setChargeType("Receivable");
						}
						else if((CommonFunction.checkNull(subcharges.get(1)).trim()).equals("P"))
						{
							vo.setChargeType("Payable");
						}
						vo.setChargeCode((CommonFunction.checkNull(subcharges.get(2))).trim());
						vo.setChargeDesc((CommonFunction.checkNull(subcharges.get(3))).trim());
						vo.setChargeBPType((CommonFunction.checkNull(subcharges.get(4)).trim()));
						if((CommonFunction.checkNull(subcharges.get(16)).trim()).equals("CS"))
						{						
							vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(8))).trim());
						}
						else
						{
							vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(5))).trim());
						}
						if(!CommonFunction.checkNull(subcharges.get(6)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(6))).trim());
			   	    		vo.setChargeCal(myFormatter.format(reconNum));
			    		}
						if(!CommonFunction.checkNull(subcharges.get(7)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(7))).trim());
			   	    		vo.setChargeFinal(myFormatter.format(reconNum));
			    		}
					
						vo.setLoanAmount((CommonFunction.checkNull(subcharges.get(9))).trim());
						vo.setMarginAmount((CommonFunction.checkNull(subcharges.get(10))).trim());
						vo.setChargeCalculatedOn((CommonFunction.checkNull(subcharges.get(11))).trim());
						vo.setChargeMethod((CommonFunction.checkNull(subcharges.get(12))).trim());
						vo.setTaxsInclusive((CommonFunction.checkNull(subcharges.get(13))).trim());
						if(!CommonFunction.checkNull(subcharges.get(14)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(14))).trim());
			   	    		vo.setTaxtRat1(myFormatter.format(reconNum));
			    		}
						if(!CommonFunction.checkNull(subcharges.get(15)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(15))).trim());
			   	    		logger.info("setTaxtRat2: "+reconNum);
			   	    		vo.setTaxtRat2(myFormatter.format(reconNum));
			    		}
						vo.setMinChargeMethod((CommonFunction.checkNull(subcharges.get(17))).trim());
						vo.setMinChargeCalculatedOn((CommonFunction.checkNull(subcharges.get(18))).trim());
						vo.setDealChargeTaxApp((CommonFunction.checkNull(subcharges.get(19))).trim());
						vo.setDealChargeTdsApp((CommonFunction.checkNull(subcharges.get(20))).trim());
						if(!CommonFunction.checkNull(subcharges.get(21)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(21))).trim());
			   	    		vo.setDealChargeTaxAmount1(myFormatter.format(reconNum));
			    		}
						if(!CommonFunction.checkNull(subcharges.get(22)).equalsIgnoreCase(""))
			    		{
			   	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(22))).trim());
			   	    		//logger.info("setTaxtRat2: "+reconNum);
			   	    		vo.setDealChargeTaxAmount2(myFormatter.format(reconNum));
			    		}
						if(!CommonFunction.checkNull(subcharges.get(23)).equalsIgnoreCase(""))
						{
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(23))).trim());
				    		vo.setDealChargeMinChargeAmount(myFormatter.format(reconNum));
			    	    }
						if(!CommonFunction.checkNull(subcharges.get(24)).equalsIgnoreCase(""))
			    	    {
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(24))).trim());
				    		//logger.info("setTaxtRat2: "+reconNum);
				    		vo.setDealChargeTdsRate(myFormatter.format(reconNum));
			    	    }
						if(!CommonFunction.checkNull(subcharges.get(25)).equalsIgnoreCase(""))
			    	    {
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(25))).trim());
				    		vo.setDealChargeTdsAmount(myFormatter.format(reconNum));
			    	    }
						if(!CommonFunction.checkNull(subcharges.get(26)).equalsIgnoreCase(""))
			    	    {
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(26))).trim());
				    		//logger.info("setTaxtRat2: "+reconNum);
				    		vo.setDealChargeNetAmount(myFormatter.format(reconNum));
			    	    }
						vo.setApplStage(CommonFunction.checkNull(subcharges.get(27)));
						if(!CommonFunction.checkNull(subcharges.get(28)).equalsIgnoreCase(""))
			    	    {
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(28))).trim());
				    		//logger.info("setTaxtRat2: "+reconNum);
				    		vo.setBalanceAmount(myFormatter.format(reconNum));
			    	    }
						if(!CommonFunction.checkNull(subcharges.get(29)).equalsIgnoreCase(""))
			    	    {
				    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(29))).trim());
				    		//logger.info("setTaxtRat2: "+reconNum);
				    		vo.setImdAllocatedAmount(myFormatter.format(reconNum));
			    	    }
						list.add(vo);
					 }
					}
				}
				catch (Exception e) 
				{e.printStackTrace();}
				return list;
			}
			//change by Mradul
			public String checkReciptStatusFromInventory(ImdMakerVO vo) {
				
				String result="1";
			 	String inventoryFlag="";	
			 	String bookNoNonMandatory="";
			 	String bookNoValidate="";
			 	String bookNoValidate2="";
				String checkAllBranch="";
			 	StringBuilder existCount = new StringBuilder();
		  		StringBuilder inventoryFlagInMst = new StringBuilder();
		  		StringBuilder existInStationary = new StringBuilder();
		  		StringBuilder allBranch = new StringBuilder();
		  		StringBuilder checkUser = new StringBuilder();
		  		StringBuilder nonMandatory= new StringBuilder();
		  		StringBuilder bookNoValidatation= new StringBuilder();
		  		StringBuilder bookNoValidation2= new StringBuilder();
			  	String checkUserAvailable="";
		  		String IssuingUser=vo.getMakerId();
		  		String receiptNo=vo.getReceiptNo();
		  		int receiptChequeNo=0;
		  		if(!CommonFunction.checkNull(receiptNo).equalsIgnoreCase(""))
		  		{
		  		  receiptChequeNo=(Integer.parseInt(receiptNo))-1;
		  		}
		 try{
			 		nonMandatory.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOOK_NO_NON_MANDATORY'");
			 		bookNoNonMandatory=ConnectionDAO.singleReturn(nonMandatory.toString());
		  		
				  	inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='INVENTORY_FLAG'");
			  		logger.info("IN checkPaymentStatusFromInventory inventoryFlag ### "+inventoryFlagInMst.toString());
			  		inventoryFlag=ConnectionDAO.singleReturn(inventoryFlagInMst.toString());
  		
		  		if(inventoryFlag.equalsIgnoreCase("Y") && !CommonFunction.checkNull(vo.getReceiptNo()).equalsIgnoreCase(""))
		  	    {
		  			allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl INNER JOIN CR_STATIONARY_DTL ON CR_STATIONARY_DTL.BOOK_NO=CR_STATIONARY_STATUS_DTL.BOOK_NO WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='"+vo.getReceiptNo()+"' AND STATUS='A' AND ISNULL(CR_STATIONARY_DTL.RETURN_TO_HO_FLAG,'')<>'R' ");
			  		logger.info("IN checkAllBranch ### "+allBranch.toString());
			  		checkAllBranch=ConnectionDAO.singleReturn(allBranch.toString());
			  		
		  	if(checkAllBranch.equalsIgnoreCase("0"))
		  		{
		  		if(bookNoNonMandatory.equalsIgnoreCase("N"))
	  			{	
			  		checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+vo.getReceiptNo()+"' AND ISNULL(ISSUING_USER,'')<>''");
			  		logger.info("IN checkInventory inventoryFlag ### "+checkUser.toString());
			  		checkUserAvailable=ConnectionDAO.singleReturn(checkUser.toString());	
	  			}else{
  					checkUserAvailable="1";
	  			}
  		if(!(checkUserAvailable.equalsIgnoreCase("0")))
  				{
		  			existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+vo.getReceiptNo()+"'  AND ISNULL(ISSUING_USER,'"+IssuingUser+"')='"+IssuingUser+"' ");
			  		logger.info("IN checkUserAvailability  in existInStationary ### "+existInStationary.toString());
			  		result=ConnectionDAO.singleReturn(existInStationary.toString());
  			}else{
		    	 	existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+vo.getReceiptNo()+"'  AND ISSUING_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDefaultBranch()).trim())+"'");
			  		logger.info("IN checkReciptStatusFromInventory  in existInStationary ### "+existInStationary.toString());
			  		result=ConnectionDAO.singleReturn(existInStationary.toString());
  					}
		  		}
			  	else{
			  		result="1";
			  	}
		  	if(bookNoNonMandatory.equalsIgnoreCase("Y"))
	  		{
	  			bookNoValidatation.append(" SELECT BOOK_NO FROM CR_STATIONARY_STATUS_DTL WHERE RECEPT_CHEQUE_NO='"+vo.getReceiptNo()+"' AND STATUS='A'");
	  			bookNoValidate=ConnectionDAO.singleReturn(bookNoValidatation.toString());
	  			bookNoValidation2.append(" SELECT ISNULL(STATUS,'') FROM CR_STATIONARY_STATUS_DTL WHERE RECEPT_CHEQUE_NO="+receiptChequeNo+" AND BOOK_NO='"+bookNoValidate+"'");
	  			bookNoValidate2=ConnectionDAO.singleReturn(bookNoValidation2.toString());
	  		}
	  	 if(CommonFunction.checkNull(bookNoValidate2).equalsIgnoreCase("A"))
			{
	  			result="2";
				}
			 }
		  }
		catch(Exception e){
			e.printStackTrace();
		     }
  	   finally{
  		 existCount = null;
  		inventoryFlagInMst=null;
  		existInStationary=null;
  	         }
		return result;
   }
//end by Mradul

			//changes by richa
			public ArrayList <ImdMakerVO> imdDetailsGrid(ImdMakerVO vo,String dealId)
			{

			logger.info("In imdMakerGrid() of ImdDAOImpl");
			StringBuilder customerName = new StringBuilder();
			StringBuilder businessPartnerType = new StringBuilder();
			StringBuilder businessPartnerID = new StringBuilder();
			StringBuilder receiptAmount = new StringBuilder();
			StringBuilder instrumentNumber = new StringBuilder();
			StringBuilder receiptMode = new StringBuilder();
			
//		  String loanNo="";
//		  String customerName="";
//		  String businessPartnerType="";
//		  String businessPartnerID="";
//		  String receiptAmount="";
//		  String instrumentNumber="";
//		  String receiptMode="";
//		  String userName="";

		  int count=0;
		  int startRecordIndex=0;
		  int endRecordIndex = no;
		  ArrayList<ImdMakerVO> receiptMakerSearchGrid=new 	ArrayList<ImdMakerVO>();
		  
			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
//			try{
//				String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
//				userName=ConnectionDAO.singleReturn(userNameQ);
//				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
			  try{
				  ArrayList searchlist=new ArrayList();
		        logger.info("In imdMakerGrid....................");
		        StringBuffer bufInsSql =	new StringBuffer();
		        StringBuffer bufInsSqlTempCount = new StringBuffer();
		        boolean appendSQL=false;
		       
		        logger.info("In imdMakerGrid......dealNo:::::::  "+dealId);
		        customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
		        businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
		        businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
		   	    if(!CommonFunction.checkNull(vo.getReceiptAmount()).equalsIgnoreCase(""))
		   	    {
		        receiptAmount.append(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptAmount()).trim())).toString());
		        logger.info("In imdMakerGrid......receiptAmount  "+receiptAmount);
		   	    }
			    instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
			    receiptMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReceiptMode()).trim()));
			    logger.info("In imdMakerGrid......receiptMode  "+receiptMode);  
			      
			    bufInsSql.append(" select b.deal_charge_code,C.CHARGE_DESC,b.imd_date,b.imd_amount,a.maker_id," );
			    bufInsSql.append(dbo);
			    bufInsSql.append("date_format(a.maker_date,'"+dateFormat+"') AS MAKER_DATE,a.author_id,");
			    bufInsSql.append(dbo);
			    bufInsSql.append("date_format(a.author_date,'"+dateFormat+"') AS AUTHOR_DATE,a.instrument_mode,a.instrument_amount,a.REC_STATUS,B.REC_STATUS,a.instrument_id,b.instrument_id,");
			    bufInsSql.append(dbo);
			    bufInsSql.append("date_format(a.instrument_date,'"+dateFormat+"') AS INSTRUMENT_DATE,");
			    bufInsSql.append(dbo);
			    bufInsSql.append("date_format(a.received_date,'"+dateFormat+"') AS RECEIVED_DATE ,a.recipt_no ");
			    bufInsSql.append(" from cr_instrument_dtl a left join cr_imd_dtl b on a.txnid=b.deal_id and a.INSTRUMENT_ID=b.INSTRUMENT_ID ");
			    bufInsSql.append(" left JOIN com_charge_code_m C ON C.CHARGE_CODE=B.DEAL_CHARGE_CODE where a.txn_type='DC' and a.txnid='"+dealId+"' ");
	       
		        bufInsSqlTempCount.append(" SELECT  count(1) ");
		        bufInsSqlTempCount.append(" from cr_instrument_dtl a left join cr_imd_dtl b on a.txnid=b.deal_id  ");
		        bufInsSqlTempCount.append(" where a.txn_type='DC' and a.txnid='"+dealId+"' ");
		    			    			 
		    	
		    		    				 
						 
						
							
							LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						
						/*	  startRecordIndex=0;
							  endRecordIndex = no;
							  bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
						 */
			    logger.info("imdMakerGrid    ........main query"+bufInsSql.toString());
						  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
						  count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
					
		    	  		
		            logger.info("imdMakerGrid search Data size is...."+searchlist.size());
		           logger.info("imdMakerGrid    .......count query."+bufInsSqlTempCount.toString());
		            for(int i=0;i<searchlist.size();i++){
		            logger.info("ImdMakerGrid search List "+searchlist.get(i).toString());
		            ArrayList data=(ArrayList)searchlist.get(i);

		        if(data.size()>0){
		        	ImdMakerVO receiptvo = new ImdMakerVO();
		        	receiptvo.setChargeCode((CommonFunction.checkNull(data.get(0)).trim()));
		        	if(CommonFunction.checkNull(data.get(12)).equalsIgnoreCase(CommonFunction.checkNull(data.get(13))))
		        	{
		        		receiptvo.setChargeDesc((CommonFunction.checkNull(data.get(1)).trim()));
		        	}
		        	else 
		        	{
		        		receiptvo.setChargeDesc("NOT ALLOCATED");
		        	}
		        		
		        	if(CommonFunction.checkNull(data.get(12)).equalsIgnoreCase(CommonFunction.checkNull(data.get(13))))
		        	{
		        		receiptvo.setImdDate((CommonFunction.checkNull(data.get(2)).trim()));
		        	}
		        	else if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("C"))
		        	{
		        		 receiptvo.setImdDate(CommonFunction.checkNull(data.get(15)).trim());
		        	}
		        	else 
		        	{
		        		 receiptvo.setImdDate(CommonFunction.checkNull(data.get(14)).trim());
		        	}
				
				if(CommonFunction.checkNull(data.get(12)).equalsIgnoreCase(CommonFunction.checkNull(data.get(13))))
	        	{
					receiptvo.setImdAmount((CommonFunction.checkNull(data.get(3)).trim()));
	        	}
	        	else 
	        	{
	        		receiptvo.setImdAmount(CommonFunction.checkNull(data.get(9)).trim());
	        	}
				receiptvo.setMakerId((CommonFunction.checkNull(data.get(4)).trim()));
				receiptvo.setMakerDate((CommonFunction.checkNull(data.get(5)).trim()));
				receiptvo.setAuthorId((CommonFunction.checkNull(data.get(6)).trim()));
				receiptvo.setAuthorDate(CommonFunction.checkNull(data.get(7)).toString());
				receiptvo.setInstrumentMode((CommonFunction.checkNull(data.get(8)).trim()));
				 if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("C")){ 	    		
					 receiptvo.setInstrumentMode("Cash");
		    	      	  } 
		    	  else if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("Q")){
		    		  receiptvo.setInstrumentMode("Cheque");
		    	  }
		    	  else if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("D")){
		    		  receiptvo.setInstrumentMode("DD");
		    	  }
		    	  else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("N")){
		    		  receiptvo.setInstrumentMode("NEFT");
		    	  }
		    	  else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("R")){
		    		  receiptvo.setInstrumentMode("RTGS");
		    	  }
		    	  else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("S")){
		    		  receiptvo.setInstrumentMode("Adjustment");
		    	  }
		    	  else if (CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase("DIR")){
		    		  receiptvo.setInstrumentMode("Direct Debit");
		    	  }
				receiptvo.setInstrumentAmount(CommonFunction.checkNull(data.get(9)).trim());
				receiptvo.setStatus(CommonFunction.checkNull(data.get(10)).trim());
				 if(CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("P")){ 	    		
					 receiptvo.setStatus("Pending");
		    	      	  } 
		    	  else if(CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("B")){
		    		  receiptvo.setStatus("Bounced");
		    	  }
		    	  else if(CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("D")){
		    		  receiptvo.setStatus("Deposited");
		    	  }
		    	  else if(CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("R")){
		    		  receiptvo.setStatus("Realized");
		    	  }
		    	  else if(CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("X")){
		    		  receiptvo.setStatus("Cancelled");
		    	  }
		    	  else if(CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase("A")){
		    		  receiptvo.setStatus("Approved");
		    	  }
				 
				 if(CommonFunction.checkNull(data.get(12)).equalsIgnoreCase(CommonFunction.checkNull(data.get(13))))
		        	{
					 receiptvo.setImdStatus(CommonFunction.checkNull(data.get(11)).trim());
					 if(CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("P")){ 	    		
						 receiptvo.setImdStatus("Pending");
			    	      	  } 
			    	  else if(CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("F")){
			    		  receiptvo.setImdStatus("Forwarded");
			    	  }
			    	  else if(CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase("A")){
			    		  receiptvo.setImdStatus("Approved");
			    	  }
		        	}
		        	else 
		        	{
		        		receiptvo.setImdStatus("NA");
		        	}
				
				 receiptvo.setInstrumentID(CommonFunction.checkNull(data.get(12)).trim());
				 receiptvo.setInstrument(CommonFunction.checkNull(data.get(13)).trim());
				 receiptvo.setInstrumentDate(CommonFunction.checkNull(data.get(14)).trim());
				 receiptvo.setReceivedDate(CommonFunction.checkNull(data.get(15)).trim());
				 receiptvo.setReceiptNo(CommonFunction.checkNull(data.get(16)).trim());
				receiptMakerSearchGrid.add(receiptvo);
		      	
		         }

			      }
							}
			catch(Exception e){
				e.printStackTrace();
					}
			finally
			{
				dealId = null ;
				customerName = null;
				businessPartnerType = null;
				businessPartnerID = null;
				receiptAmount = null;
				instrumentNumber = null;
				receiptMode = null;
			}
			return  receiptMakerSearchGrid;	
		
}

			@Override
			public ArrayList<ImdMakerVO> getpreDealImdList(ImdMakerVO vo) {
				// TODO Auto-generated method stub
				return null;
			}


}
