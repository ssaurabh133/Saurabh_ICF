package com.cm.daoImplMSSQL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.PaymentDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cm.vo.ReceiptMakerVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.logger.LoggerMsg;

public class MSSQLPaymentDAOImpl implements PaymentDAO 
{
	private static final Logger logger = Logger.getLogger(MSSQLPaymentDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	//code by manisha for paymentMaker search grid data

	public /*ArrayList <PaymentMakerForCMVO>*/Map paymentMakerGrid(PaymentMakerForCMVO vo)
	 {
		StringBuilder loanNo = new StringBuilder();
		StringBuilder customerName = new StringBuilder();
		StringBuilder businessPartnerType = new StringBuilder();
		StringBuilder businessPartnerID = new StringBuilder();
		StringBuilder paymentAmount = new StringBuilder();
		StringBuilder instrumentNumber = new StringBuilder();
		StringBuilder instrumentId = new StringBuilder();
		StringBuilder paymentMode = new StringBuilder();
		 HashMap map = new HashMap();
		
//	String loanNo="";
//	String customerName="";
//	String businessPartnerType="";
//	String businessPartnerID="";
//	String paymentAmount="";
//	String instrumentNumber="";
//	String instrumentId="";
//	String paymentMode="";
//	String userName="";

	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;

		
	ArrayList<PaymentMakerForCMVO> detailListGrid=new 	ArrayList<PaymentMakerForCMVO>();
	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
//	try{
//		String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
//		userName=ConnectionDAO.singleReturn(userNameQ);
//		logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//	}
//	catch(Exception e)
//	{
//		e.printStackTrace();
//	}
		  try{
			  
	      ArrayList searchlist=new ArrayList();
	      logger.info("In paymentMakerGrid....................");
	      boolean appendSQL=false;
	      StringBuffer bufInsSql =	new StringBuffer();
	      StringBuffer bufInsSqlTempCount = new StringBuffer();
	      
	      loanNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
	    //  logger.info("In paymentMakerGrid......loanNo  "+loanNo);
	      customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
	      businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
	      businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
	      if(!CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
	   	    {
	      paymentAmount.append(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPaymentAmount()).trim())).toString());
      //    logger.info("In paymentMakerGrid......paymentAmount  "+paymentAmount);         
	   	    }
          instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
	      instrumentId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim()));
	   //   logger.info("In paymentMakerGrid......instrumentId  "+instrumentId);  
	      paymentMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPaymentMode()).trim()));
	  //    logger.info("In paymentMakerGrid......paymentMode  "+paymentMode);
	    	 
			  bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " +		  
		  		"   GM.DESCRIPTION,CID.BPID , " +		  	
		  		"   BPV.BP_NAME ,  CID.INSTRUMENT_AMOUNT,CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE, CID.INSTRUMENT_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID= CID.MAKER_ID) MAKER_ID  " +
		        "   ");
			 
			  bufInsSql.append("from gcd_customer_m GCM ,cr_loan_dtl CLD, cr_instrument_dtl CID, business_partner_view BPV, generic_master  GM  " +
		        "   WHERE CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID  AND BPV.BP_ID=CID.BPID AND  BPV.LOAN_ID=CID.TXNID and  BPV.BP_TYPE=cid.BPTYPE and CLD.LOAN_ID=CID.TXNID " +
		        "   AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='P' and INSTRUMENT_TYPE='P' and isnull(CID.TXN_TYPE,'')='LIM' AND CLD.LOAN_BRANCH='"+vo.getBranchId()+"' AND CID.MAKER_ID='"+vo.getReportingToUserId()+"'");
			  
			  bufInsSqlTempCount.append(" SELECT count(DISTINCT CLD.LOAN_ID)  from gcd_customer_m GCM ,cr_loan_dtl CLD, cr_instrument_dtl CID, business_partner_view BPV, generic_master  GM " +
			  		" WHERE CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID  AND BPV.BP_ID=CID.BPID AND  BPV.LOAN_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE and CLD.LOAN_ID=CID.TXNID " +
			  		" AND GM.VALUE=CID.BPTYPE AND GM.GENERIC_KEY='BPTYPE'and CID.REC_STATUS='P' and INSTRUMENT_TYPE='P' and isnull(CID.TXN_TYPE,'')='LIM' AND CLD.LOAN_BRANCH='"+vo.getBranchId()+"' AND " +
			  		" CID.MAKER_ID='"+vo.getReportingToUserId()+"'");
			  
			  if((!((loanNo.toString()).trim().equalsIgnoreCase("")))&&(!((customerName.toString()).trim().equalsIgnoreCase("")))&&(!((businessPartnerType.toString()).trim().equalsIgnoreCase("")))&&(!((businessPartnerID.toString()).trim().equalsIgnoreCase("")))&&(!((paymentAmount.toString()).trim().equalsIgnoreCase("")))&&(!((instrumentNumber.toString()).trim().equalsIgnoreCase("")))&&(!((paymentMode.toString()).trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CLD.LOAN_ID='"+loanNo+"' AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' AND CID.BPTYPE ='"+businessPartnerType+"'AND CID.BPID='"+businessPartnerID+"' AND CID.INSTRUMENT_AMOUNT='"+paymentAmount+"' AND CID.INSTRUMENT_NO ='"+instrumentNumber+"' AND CID.INSTRUMENT_MODE='"+paymentMode+"'");
		    	  bufInsSqlTempCount.append(" AND CLD.LOAN_ID='"+loanNo+"' AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' AND CID.BPTYPE ='"+businessPartnerType+"'AND CID.BPID='"+businessPartnerID+"' AND CID.INSTRUMENT_AMOUNT='"+paymentAmount+"' AND CID.INSTRUMENT_NO ='"+instrumentNumber+"' AND CID.INSTRUMENT_MODE='"+paymentMode+"'");
			  }
				if(!((loanNo.toString()).trim().equalsIgnoreCase("")) || !((customerName.toString()).trim().equalsIgnoreCase("")) || !((businessPartnerType.toString()).trim().equalsIgnoreCase("")) || !((businessPartnerID.toString()).trim().equalsIgnoreCase("")) || !((paymentAmount.toString()).trim().equalsIgnoreCase("")) || !((instrumentNumber.toString()).trim().equalsIgnoreCase("")) ||!((paymentMode.toString()).trim().equalsIgnoreCase(""))){
					appendSQL=true;
				}
				
				if(appendSQL){
					bufInsSql.append(" AND");
					bufInsSqlTempCount.append(" AND");
	       }

	  	 if((!((loanNo.toString()).trim().equalsIgnoreCase("")))) {
		         bufInsSql.append(" CLD.LOAN_ID='"+loanNo+"' AND");
		         bufInsSqlTempCount.append(" CLD.LOAN_ID='"+loanNo+"' AND");
		    	 appendSQL=true;
		    	  
		      }
				 
				if((!((customerName.toString()).trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' AND");
		    	  bufInsSqlTempCount.append(" GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' AND");
		    	  appendSQL=true;
		      }
				
				if((!((businessPartnerType.toString()).trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" CID.BPTYPE='"+businessPartnerType+"' AND");
		    	  bufInsSqlTempCount.append(" CID.BPTYPE='"+businessPartnerType+"' AND");
		    	  appendSQL=true;
		      }
				
				if((!((businessPartnerID.toString()).trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" CID.BPID='"+businessPartnerID+"' AND");
		    	  bufInsSqlTempCount.append(" CID.BPID='"+businessPartnerID+"' AND");
		    	  appendSQL=true;
		      }
				
				if((!((paymentAmount.toString()).trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" CID.INSTRUMENT_AMOUNT='"+paymentAmount+"' AND");
		    	  bufInsSqlTempCount.append(" CID.INSTRUMENT_AMOUNT='"+paymentAmount+"' AND");
		    	  appendSQL=true;
		      }
				
				if((!((instrumentNumber.toString()).trim().equalsIgnoreCase("")))) {

		    	  bufInsSql.append(" CID.INSTRUMENT_NO='"+instrumentNumber+"' AND");
		    	  bufInsSqlTempCount.append(" CID.INSTRUMENT_NO='"+instrumentNumber+"' AND");

		    	  appendSQL=true;
		      }
				if((!((paymentMode.toString()).trim().equalsIgnoreCase("")))) {
			    	  bufInsSql.append(" CID.INSTRUMENT_MODE='"+paymentMode+"'");
			    	  bufInsSqlTempCount.append(" CID.INSTRUMENT_MODE='"+paymentMode+"'");
			    	  appendSQL=true;
			      }
				
			//	logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){
			//		logger.info("In appendSQL true---- ");
					String  tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
	        //  logger.info("In setSearchCharge() ## tmp ## "+tmp);
	       //   logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
	          if(tmp.lastIndexOf("AND") == (tmp.length()-3)|| tmp1.lastIndexOf("AND") == (tmp1.length()-3)){
	        //  logger.info("In appendSQL true----  in check index Of");
	          tmp = (tmp).substring(0,(tmp.length()-4));
	          tmp1 = (tmp1).substring(0,(tmp1.length()-4));
	        //  logger.info("search Query...tmp."+tmp);
	          searchlist = ConnectionDAO.sqlSelect(tmp);
	          count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));

	           }else{
	        //  	  logger.info("search Query...tmp."+tmp);
	                searchlist = ConnectionDAO.sqlSelect(tmp); 
	                count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	            }
	           
	            
				}else{
			//	  logger.info("paymentMakerGrid Query...else-------."+bufInsSql);
			//	   logger.info("paymentMakerGrid    ........"+bufInsSql.toString());
				  count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				//  if(((loanNo.toString()).trim()==null && (customerName.toString()).trim()==null && businessPartnerType==null && (businessPartnerID.toString()).trim()==null && (paymentAmount.toString()).trim()==null && (instrumentNumber.toString()).trim()==null && (paymentMode.toString()).trim()==null) || ((loanNo.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") && (businessPartnerType.toString()).trim().equalsIgnoreCase("") && (businessPartnerID.toString()).trim().equalsIgnoreCase("") && (paymentAmount.toString()).trim().equalsIgnoreCase("") && (instrumentNumber.toString()).trim().equalsIgnoreCase("") && (paymentMode.toString()).trim().equalsIgnoreCase(""))||  vo.getCurrentPageLink()>1)

				//	{
					
					LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
					if(vo.getCurrentPageLink()>1)
					{
						startRecordIndex = (vo.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
				//		LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
				//		LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
					}
					
					bufInsSql.append(" ORDER BY CLD.LOAN_ID OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
					//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
					 
					
			//		}
				  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				}	
	    //  logger.info("paymentMaker search Data size is...."+searchlist.size());
	    //  logger.info("paymentMakerGrid    ........"+bufInsSql.toString());
	      for(int i=0;i<searchlist.size();i++){
	    //  logger.info("paymentMaker search List "+searchlist.get(i).toString());
	      ArrayList data=(ArrayList)searchlist.get(i);

	      if(data.size()>0){
	    	  PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
	    	  
	    	  paymentVO.setModifyNo("<a href=paymentCMProcessAction.do?method=openEditpaymentMaker&lbxLoanNoHID="
						+ CommonFunction.checkNull(data.get(0)).toString()
						+ "&instrumentID="+CommonFunction.checkNull(data.get(10)).toString()+">"
						+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");

	    	  
	    	  paymentVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
	    //	  logger.info("setLbxLoanNoHID:-------------"+data.get(0));
	    	  paymentVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
	    //	  logger.info("setCustomerName:-------------"+data.get(2));
	    	  paymentVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
	    	  paymentVO.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
	    	  paymentVO.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
	    //	  logger.info("setBusinessPartnerType:-------------"+data.get(4));
	    	  paymentVO.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
	    	  paymentVO.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
	    	  Number PaymentAmount = 0;
	    	  if(!CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("")){ 	
	    		  PaymentAmount = myFormatter.parse((CommonFunction.checkNull(data.get(7))).trim()); 
	    	  }
			
	    	  paymentVO.setPaymentAmount(PaymentAmount.toString());

	    	  paymentVO.setInstrumentNumber(CommonFunction.checkNull(data.get(8)).trim());
	    	  paymentVO.setPaymentMode(CommonFunction.checkNull(data.get(9)).trim());
	    	  if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("C")){ 	    		
	    		  paymentVO.setPaymentMode("Cash");
	    	      	  } 
	    	  else if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("Q")){
	    		  paymentVO.setPaymentMode("Cheque");
	    	  }
	    	  else if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("D")){
	    		  paymentVO.setPaymentMode("DD");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("N")){
	    		  paymentVO.setPaymentMode("NEFT");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("R")){
	    		  paymentVO.setPaymentMode("RTGS");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("S")){
	    		  paymentVO.setPaymentMode("Adjustment");
	    	  }
	    	  paymentVO.setInstrumentID(CommonFunction.checkNull(data.get(10)).trim());
	    	 

	    	  paymentVO.setTotalRecordSize(count);
	    	  paymentVO.setReportingToUserId(CommonFunction.checkNull(data.get(11)).trim());
	    	  detailListGrid.add(paymentVO);
	    	 
	       }

		      }
	      map.put("searchlist", searchlist);
	      map.put("detailListGrid", detailListGrid);
			/*if( searchlist.size()==0)
			{
				//PaymentMakerForCMVO payMVo = new PaymentMakerForCMVO();
				//payMVo.setTotalRecordSize(count);
				//detailListGrid.add(payMVo);
				//request.setAttribute("flag","yes");
				request.setAttribute("nullVal","yes");
				//LoggerMsg.info("getTotalRecordSize : "+payMVo.getTotalRecordSize());
			}*/

		}catch(Exception e){
			e.printStackTrace();
				}
		finally
		{
				loanNo = null;
				customerName = null;
				businessPartnerType = null;
				businessPartnerID = null;
				paymentAmount = null;
				instrumentNumber = null;
				instrumentId = null;
				paymentMode = null;
		}
		return  map/*detailListGrid*/;	
	}

	public ArrayList<PaymentMakerForCMVO>onAllocatedPayable(PaymentMakerForCMVO paymentMakerForCMVO,int loanId,String bpType,int instrumentId )
	{
     ArrayList<PaymentMakerForCMVO> allocatedList=new ArrayList<PaymentMakerForCMVO>();
     StringBuilder query = new StringBuilder();
	 	
	 	try{
	 		ArrayList mainList=new ArrayList ();
	 		ArrayList subList =new ArrayList();
//	 		CallableStatement cst=null;
//	 		Connection con=ConnectionDAO.getConnection();
	 
	 		logger.info(" In onAllocatedPayable....");	
	 			
	 	
	 		query.append("SELECT  distinct ");
	 		query.append(dbo);
	 		query.append("DATE_FORMAT(ADVICE_DATE,'"+dateFormat+"') AS ADVICE_DATE,(Select CHARGE_DESC From com_charge_code_m Where CHARGE_CODE=CTD.CHARGE_CODE_ID) CHARGE, ADVICE_AMOUNT,  "+
	 		" (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,  "+
	       " AMOUNT_IN_PROCESS, CTD.TDS_AMOUNT, PMNT_AMOUNT,CPD.TDS_AMOUNT AS TDS_ALLOCATED_AMOUNT "+
	       " from cr_txnadvice_dtl CTD,cr_pmnt_dtl CPD  where CPD.TXNADVICEID=CTD.TXNADVICE_ID "+
	       " AND ADVICE_TYPE='P' "+
	      "  AND LOAN_ID='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+ "'" +
	     	" AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "' " +
	      	" AND CPD.INSTRUMENT_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentId).trim()) + "' " +
	      	" Order By ADVICE_DATE ASC ");

	 		
	 		logger.info("In onAllocatedPayable"+query);	
	 		
	 		mainList=ConnectionDAO.sqlSelect(query.toString());
	 		for(int i=0;i<mainList.size();i++)
	 		{
	 			subList= (ArrayList)mainList.get(i);
	 			if(subList.size()>0){
	 				
	 				PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
	 				paymentVO.setPaymentDate((CommonFunction.checkNull(subList.get(0)).trim()));
	 				logger.info("setPaymentDate: "+subList.get(0));		
	 				paymentVO.setChargeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
	 				Number OriginalAmount =0;
	 				if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase(""))
	 		   	    {
	 					OriginalAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
	 		   	    }
	 			    logger.info("OriginalAmount: "+OriginalAmount);			
	 		    	paymentVO.setOriginalAmount(myFormatter.format(OriginalAmount));
	 		    	Number BalanceAmount = 0;
	 		    	if(!CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase(""))
	 		   	    {
	 		    		BalanceAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
	 		   	    }
	 				logger.info("BalanceAmount: "+BalanceAmount);			
	 		    	paymentVO.setBalanceAmount(myFormatter.format(BalanceAmount));
	 		    	Number AmountInProcess = 0;
	 		    	if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase(""))
	 		   	    {
	 		    		AmountInProcess = myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());
	 		   	    }
		 			logger.info("AmountInProcess: "+AmountInProcess);			
		 		    paymentVO.setAmountInProcess(myFormatter.format(AmountInProcess));
		 		   Number TdsadviseAmount = 0;
		 		  if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase(""))
	 		   	    {
		 			  TdsadviseAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
	 		   	    }
	 			    logger.info("TdsadviseAmount: "+TdsadviseAmount);			
	 		    	paymentVO.setTdsadviseAmount(myFormatter.format(TdsadviseAmount));		
	 		    	Number AllotedAmount =0;
	 		    	if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase(""))
	 		   	    {
		 			AllotedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
	 		   	    }
		 			logger.info("AllotedAmount: "+AllotedAmount);			 		    	
	 				paymentVO.setAllotedAmount(myFormatter.format(AllotedAmount));
	 				Number TdsAllocatedAmount =0;
	 				if(!CommonFunction.checkNull(subList.get(7)).equalsIgnoreCase(""))
	 		   	    {
	 					TdsAllocatedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());
	 		   	    }
		 			logger.info("TdsAllocatedAmount: "+TdsAllocatedAmount);			 		    	
	 				paymentVO.setTdsAllocatedAmount(myFormatter.format(TdsAllocatedAmount));
	 				
	 		    
	 				allocatedList.add(paymentVO);
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
	

//code by manisha for payment Author search grid data

public ArrayList <PaymentMakerForCMVO> paymentAuthorGrid(PaymentMakerForCMVO vo)
{
	StringBuilder loanNo = new StringBuilder();
	StringBuilder customerName = new StringBuilder();
	StringBuilder businessPartnerType = new StringBuilder();
	StringBuilder businessPartnerID = new StringBuilder();
	StringBuilder paymentAmount = new StringBuilder();
	StringBuilder instrumentNumber = new StringBuilder();
	StringBuilder paymentMode = new StringBuilder();
	
//	String loanNo="";
//    String customerName="";
//    String businessPartnerType="";
//    String businessPartnerID="";
//    String paymentAmount="";
//    String instrumentNumber="";
//    String paymentMode="";
//    String userName="";

    int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;

   ArrayList<PaymentMakerForCMVO> authordetailList=new 	ArrayList<PaymentMakerForCMVO>();

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
          logger.info("In paymentAuthorGrid....................");
          StringBuffer bufInsSql =	new StringBuffer();
          StringBuffer bufInsSqlTempCount = new StringBuffer();
          boolean appendSQL=false;
          loanNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
          logger.info("In paymentMakerGrid......loanNo  "+loanNo);
          customerName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()));
          businessPartnerType.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim()));
          businessPartnerID.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim()));
          if(!CommonFunction.checkNull(vo.getPaymentAmount()).equalsIgnoreCase(""))
	   	    {
          paymentAmount.append(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPaymentAmount()).trim())).toString());
          logger.info("In paymentAuthor......paymentAmount  "+paymentAmount);
	   	    }
          instrumentNumber.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim()));
          paymentMode.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPaymentMode()).trim()));
	      logger.info("In paymentMakerGrid......paymentMode  "+paymentMode);
	    	
  		  bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " +
  	  		"  GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME ,CID.INSTRUMENT_AMOUNT," + 		
  	  		"  CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID  " +
  	        "  ");
  		
  		  bufInsSql.append("from cr_instrument_dtl CID,cr_loan_dtl CLD,gcd_customer_m GCM ,business_partner_view BPV,generic_master  GM" +
  	        "  WHERE CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID" +
  	        "  and CLD.LOAN_ID=CID.TXNID" +
  	        "  AND BPV.BP_ID=CID.BPID AND  BPV.LOAN_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE "+
  	        " AND GM.VALUE=CID.BPTYPE "+
  	        " AND GM.GENERIC_KEY='BPTYPE'" +
  	        " and CID.REC_STATUS='F' and isnull(CID.TXN_TYPE,'')='LIM' and INSTRUMENT_TYPE='P' AND CID.MAKER_ID!='"+vo.getUserId()+"' AND CLD.LOAN_BRANCH='"+vo.getBranchId()+"' ");
  		  
  		bufInsSqlTempCount.append("select count(1) from(SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " +
		  	  		"  GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME ,CID.INSTRUMENT_AMOUNT," + 		
		  	  		"  CID.INSTRUMENT_NO,CID.INSTRUMENT_MODE,CID.INSTRUMENT_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CID.MAKER_ID) MAKER_ID " +
		  	        "  from cr_instrument_dtl CID,cr_loan_dtl CLD,gcd_customer_m GCM ,business_partner_view BPV,generic_master  GM" +
		  	        "  WHERE CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID" +
		  	        "  and CLD.LOAN_ID=CID.TXNID" +
		  	        "  AND BPV.BP_ID=CID.BPID AND  BPV.LOAN_ID=CID.TXNID and BPV.BP_TYPE=cid.BPTYPE "+
		  	        " AND GM.VALUE=CID.BPTYPE "+
		  	        " AND GM.GENERIC_KEY='BPTYPE'" +
		  	        " and CID.REC_STATUS='F' and isnull(CID.TXN_TYPE,'')='LIM' and INSTRUMENT_TYPE='P' AND CID.MAKER_ID!='"+vo.getUserId() +"' AND CLD.LOAN_BRANCH='"+vo.getBranchId()+"' ");
		
		  	  if(((loanNo.toString()).trim().equalsIgnoreCase(""))&&((customerName.toString()).trim().equalsIgnoreCase(""))&&((businessPartnerType.toString()).trim().equalsIgnoreCase(""))&&((businessPartnerID.toString()).trim().equalsIgnoreCase(""))&&((paymentAmount.toString()).trim().equalsIgnoreCase(""))&&((instrumentNumber.toString()).trim().equalsIgnoreCase(""))&&((paymentMode.toString()).trim().equalsIgnoreCase(""))&&(vo.getLbxUserId().equalsIgnoreCase(""))) {
	  	    	  bufInsSqlTempCount.append(" )as b");
	  		  }

  		  if((!((loanNo.toString()).trim().equalsIgnoreCase("")))&&(!((customerName.toString()).trim().equalsIgnoreCase("")))&&(!((businessPartnerType.toString()).trim().equalsIgnoreCase("")))&&(!((businessPartnerID.toString()).trim().equalsIgnoreCase("")))&&(!((paymentAmount.toString()).trim().equalsIgnoreCase("")))&&(!((instrumentNumber.toString()).trim().equalsIgnoreCase("")))&&(!((paymentMode.toString()).trim().equalsIgnoreCase("")))) {
  	    	  bufInsSql.append("AND CLD.LOAN_ID='"+loanNo+"' AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' AND CID.BPTYPE ='"+businessPartnerType+"'AND CID.BPID='"+businessPartnerID+"' AND CID.INSTRUMENT_AMOUNT='"+paymentAmount+"' AND CID.INSTRUMENT_NO ='"+instrumentNumber+"' AND CID.INSTRUMENT_MODE='"+paymentMode+"'");
  	    	  bufInsSqlTempCount.append(" AND CLD.LOAN_ID='"+loanNo+"' AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' AND CID.BPTYPE ='"+businessPartnerType+"'AND CID.BPID='"+businessPartnerID+"' AND CID.INSTRUMENT_AMOUNT='"+paymentAmount+"' AND CID.INSTRUMENT_NO ='"+instrumentNumber+"'AND CID.INSTRUMENT_MODE='"+paymentMode+"'");
  		  }
  		  
  		  if((((loanNo.toString()).trim().equalsIgnoreCase(""))) || (((customerName.toString()).trim().equalsIgnoreCase(""))) || (((businessPartnerType.toString()).trim().equalsIgnoreCase(""))) || (((businessPartnerID.toString()).trim().equalsIgnoreCase(""))) || (((paymentAmount.toString()).trim().equalsIgnoreCase(""))) || (((instrumentNumber.toString()).trim().equalsIgnoreCase(""))) || (((paymentMode.toString()).trim().equalsIgnoreCase("")))|| (vo.getLbxUserId().equalsIgnoreCase(""))){
  				appendSQL=true;
  			}
  			
  	   	 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull((loanNo.toString()).trim())).equalsIgnoreCase("")))) {
  	         bufInsSql.append(" AND CLD.LOAN_ID='"+loanNo+"' ");
  	         bufInsSqlTempCount.append("AND CLD.LOAN_ID='"+loanNo+"' ");
  	    	 appendSQL=true;
  	    	  
  	      }
  			 
  			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull((customerName.toString()).trim())).equalsIgnoreCase("")))) {
  	    	  bufInsSql.append(" AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' ");
  	    	  bufInsSqlTempCount.append(" AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%' ");
  	    	  appendSQL=true;
  	      }
  			
  			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull((businessPartnerType.toString()).trim())).equalsIgnoreCase("")))) {
  	    	  bufInsSql.append(" AND CID.BPTYPE='"+businessPartnerType+"' ");
  	    	  bufInsSqlTempCount.append(" AND CID.BPTYPE='"+businessPartnerType+"' ");
  	    	  appendSQL=true;
  	      }
  			
  			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull((businessPartnerID.toString()).trim())).equalsIgnoreCase("")))) {
  	    	  bufInsSql.append(" AND CID.BPID='"+businessPartnerID+"' ");
  	          bufInsSqlTempCount.append(" AND CID.BPID='"+businessPartnerID+"' ");
  	    	  appendSQL=true;
  	      }
  			
  			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull((paymentAmount.toString()).trim())).equalsIgnoreCase("")))) {
  	    	  bufInsSql.append(" AND CID.INSTRUMENT_AMOUNT='"+paymentAmount+"' ");
  	    	  bufInsSqlTempCount.append(" AND CID.INSTRUMENT_AMOUNT='"+paymentAmount+"' ");
  	    	  appendSQL=true;
  	      }
  			
  			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull((instrumentNumber.toString()).trim())).equalsIgnoreCase("")))) {
  	    	  bufInsSql.append(" AND CID.INSTRUMENT_NO='"+instrumentNumber+"'");
  	    	  bufInsSqlTempCount.append(" AND CID.INSTRUMENT_NO='"+instrumentNumber+"'");
  	    	  appendSQL=true;
  	      }
  			if((!((paymentMode.toString()).trim().equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CID.INSTRUMENT_MODE='"+paymentMode+"'");
		    	  bufInsSqlTempCount.append(" AND CID.INSTRUMENT_MODE='"+paymentMode+"'");
		    	  appendSQL=true;
		      }
  			if((!(CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase("")))) {
		    	  bufInsSql.append(" AND CID.MAKER_ID='"+vo.getLbxUserId()+"'");
		    	  bufInsSqlTempCount.append(" AND CID.MAKER_ID='"+vo.getLbxUserId()+"'");
		    	  appendSQL=true;
		      }
  			
  			
  			 if((!((loanNo.toString()).trim().equalsIgnoreCase("")))||(!((customerName.toString()).trim().equalsIgnoreCase("")))||(!((businessPartnerType.toString()).trim().equalsIgnoreCase("")))||(!((businessPartnerID.toString()).trim().equalsIgnoreCase("")))||(!((paymentAmount.toString()).trim().equalsIgnoreCase("")))||(!((instrumentNumber.toString()).trim().equalsIgnoreCase("")))||(!((paymentMode.toString()).trim().equalsIgnoreCase("")))||(!(vo.getLbxUserId().equalsIgnoreCase("")))) {
  				bufInsSqlTempCount.append(" )as b");
  			 }

  			
  			logger.info("In appendSQL true---- "+appendSQL);
  			
  	
            count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

        		  //if(((loanNo.toString()).trim()==null && (customerName.toString()).trim()==null && businessPartnerType.toString()==null && (businessPartnerID.toString()).trim()==null && (paymentAmount.toString()).trim()==null && (instrumentNumber.toString()).trim()==null && (paymentMode.toString()).trim()==null) || ((loanNo.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") && (businessPartnerType.toString()).trim().equalsIgnoreCase("") && (businessPartnerID.toString()).trim().equalsIgnoreCase("") && (paymentAmount.toString()).trim().equalsIgnoreCase("") && (instrumentNumber.toString()).trim().equalsIgnoreCase("")&& (paymentMode.toString()).trim().equalsIgnoreCase(""))|| vo.getCurrentPageLink()>1)

					//{
					
					LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
					if(vo.getCurrentPageLink()>1)
					{
						startRecordIndex = (vo.getCurrentPageLink()-1)*no;
						endRecordIndex = no;
						LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
						LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
					}
					
					bufInsSql.append(" ORDER BY CLD.LOAN_ID OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
					//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
					  
					
					//}
			
	  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

          logger.info("paymentAuthor search Data size is...."+searchlist.size());
          logger.info("paymentAuthor   search grid  ........"+bufInsSql.toString());
          for(int i=0;i<searchlist.size();i++){
          logger.info("paymentAuthor search List "+searchlist.get(i).toString());
          ArrayList data=(ArrayList)searchlist.get(i);

          if(data.size()>0){
        	  PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
        	  
        	  paymentVO.setModifyNo("<a href=paymentAuthorProcessAction.do?method=getDatatoApprove&lbxLoanNoHID="
						+ CommonFunction.checkNull(data.get(0)).toString()
						+ "&instrumentID="+CommonFunction.checkNull(data.get(10)).toString()
						+ "&lbxBPType="+CommonFunction.checkNull(data.get(3)).toString()+">"
						+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");

        	  
        	  paymentVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
        	  paymentVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
        	  logger.info("setCustomerName:paymentAuthorGrid-------------"+data.get(2));
        	  paymentVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
        	  paymentVO.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
        	  paymentVO.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
        	  paymentVO.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
        	  paymentVO.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
        	  Number PaymentAmount =0;
        	  if(!CommonFunction.checkNull(data.get(7)).equalsIgnoreCase(""))
		   	    {
        		  PaymentAmount = myFormatter.parse((CommonFunction.checkNull(data.get(7))).trim());
		   	    }
    		  logger.info("PaymentAmount: "+PaymentAmount);			
        	  paymentVO.setPaymentAmount(myFormatter.format(PaymentAmount));

        	  paymentVO.setInstrumentNumber(CommonFunction.checkNull(data.get(8)).trim());
        	  paymentVO.setPaymentMode(CommonFunction.checkNull(data.get(9)).trim());
        	  if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("C")){ 	    		
	    		  paymentVO.setPaymentMode("Cash");
	    	      	  } 
	    	  else if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("Q")){
	    		  paymentVO.setPaymentMode("Cheque");
	    	  }
	    	  else if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("D")){
	    		  paymentVO.setPaymentMode("DD");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("N")){
	    		  paymentVO.setPaymentMode("NEFT");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("R")){
	    		  paymentVO.setPaymentMode("RTGS");
	    	  }
	    	  else if (CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase("S")){
	    		  paymentVO.setPaymentMode("Adjustment");
	    	  }
        	  paymentVO.setInstrumentID(CommonFunction.checkNull(data.get(10)).trim());

        	  paymentVO.setTotalRecordSize(count);
        	  paymentVO.setReportingToUserId(CommonFunction.checkNull(data.get(11)).trim());
        	  authordetailList.add(paymentVO);	
           }
	      }

	}catch(Exception e){
		e.printStackTrace();
			}
	finally
	{
		loanNo = null;
		customerName = null;
		businessPartnerType = null;
		businessPartnerID = null;
		paymentAmount = null;
		instrumentNumber = null;
		paymentMode = null;
	}
	return  authordetailList;	
	}

public ArrayList <PaymentMakerForCMVO> searchPaymentData(PaymentMakerForCMVO paymentMakerForCMVO)
{

	
ArrayList<PaymentMakerForCMVO> payableList=new ArrayList<PaymentMakerForCMVO>();

	  ArrayList searchlist=new ArrayList();
logger.info("In searchPaymentData....................");
StringBuffer bufInsSql =	new StringBuffer();
try{
bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " +		  
  		"   GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME , CID.INSTRUMENT_MODE,");
bufInsSql.append(dbo);
bufInsSql.append("date_format(CID.RECEIVED_DATE,'"+dateFormat+"') AS RECEIVED_DATE,CID.INSTRUMENT_NO,");
bufInsSql.append(dbo);
bufInsSql.append("date_format(CID.INSTRUMENT_DATE,'"+dateFormat+"') AS INSTRUMENT_DATE," +
  		" CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" +
  		"  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME," +
  		" CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.INSTRUMENT_AMOUNT,CID.TDS_AMOUNT ,CID.REMARKS,CID.INSTRUMENT_ID,CID.MAKER_REMARKS,CID.PAY_TO,CID.PAYEE_NAME,CLD.REC_STATUS,CID.TA_Adjustment_FLAG,CID.TA_LOAN_ID,(SELECT LOAN_NO FROM CR_LOAN_DTL CLD JOIN CR_INSTRUMENT_DTL CID  ON CLD.LOAN_ID=CID.TA_LOAN_ID WHERE CID.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+"' ) AS TA_LOAN_NO,CID.TA_ACCOUNT_NAME" +
  		"  from cr_instrument_dtl CID,gcd_customer_m GCM ," +
  		" cr_loan_dtl CLD, business_partner_view BPV,generic_master  GM " +
  		" where CID.TXNID=CLD.LOAN_ID" +
  		"  AND BPV.BP_ID=CID.BPID AND CID.BPTYPE=BPV.BP_TYPE AND BPV.LOAN_ID=CID.TXNID" +  		
        "  AND GM.VALUE=CID.BPTYPE and CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID" +
        "  AND GM.GENERIC_KEY='BPTYPE' " +
        "  and INSTRUMENT_TYPE='P'AND CID.REC_STATUS='P'" +
  		" and isnull(CID.TXN_TYPE,'')='LIM' AND CID.TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim())+"' "+
        " AND CID.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+"' ");

	  logger.info("In searchPaymentData......... query..........."+bufInsSql.toString());
     searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
    logger.info("paymentMaker search Data size is...."+searchlist.size());

      for(int i=0;i<searchlist.size();i++){
logger.info("paymentMaker search List "+searchlist.get(i).toString());
ArrayList data=(ArrayList)searchlist.get(i);

if(data.size()>0){
	  PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
	  paymentVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
	  paymentVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
	  paymentVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
	  logger.info(" searchPaymentData setCustomerName:-------------"+data.get(2));
	  paymentVO.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
	  paymentVO.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
	  logger.info("setBusinessPartnerType:-------------"+data.get(4));
	  paymentVO.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
	  paymentVO.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
	  if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("Q")){ 	    		
		  paymentVO.setLbxpaymentMode("B");
	      	  } 		  
	  else if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("D")){ 	    		
		  paymentVO.setLbxpaymentMode("B");
	      	  } 
	  else if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("N")){ 	    		
		  paymentVO.setLbxpaymentMode("B");
	      	  } 
	  else if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("R")){ 	    		
		  paymentVO.setLbxpaymentMode("B");
	      	  } 
	  else if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase("S")){ 	    		
		  paymentVO.setLbxpaymentMode("S");
	      	  } 
	  paymentVO.setPaymentMode((CommonFunction.checkNull(data.get(7)).trim()));
	  paymentVO.setPaymentDate((CommonFunction.checkNull(data.get(8)).trim()));
	  paymentVO.setInstrumentNumber((CommonFunction.checkNull(data.get(9)).trim()));
	  paymentVO.setInstrumentDate((CommonFunction.checkNull(data.get(10)).trim()));
	  paymentVO.setBankAccount((CommonFunction.checkNull(data.get(11)).trim()));
	  logger.info(" searchPaymentData setBankAccount:-------------"+data.get(11));
	  paymentVO.setLbxBankID((CommonFunction.checkNull(data.get(12)).trim()));
	  paymentVO.setBank((CommonFunction.checkNull(data.get(13)).trim()));
	  paymentVO.setLbxBranchID((CommonFunction.checkNull(data.get(14)).trim()));
	  paymentVO.setBranch((CommonFunction.checkNull(data.get(15)).trim()));
	  paymentVO.setMicr((CommonFunction.checkNull(data.get(16)).trim()));
	  paymentVO.setIfsCode((CommonFunction.checkNull(data.get(17)).trim()));
	  Number PaymentAmount =0;
	  if(!CommonFunction.checkNull(data.get(18)).equalsIgnoreCase(""))
	  {
		  PaymentAmount = myFormatter.parse((CommonFunction.checkNull(data.get(18))).trim());
	  }
	  logger.info("PaymentAmount: "+PaymentAmount);			
	  paymentVO.setPaymentAmount(myFormatter.format(PaymentAmount));
	  Number TdsAmount = 0;
	  if(!CommonFunction.checkNull(data.get(19)).equalsIgnoreCase(""))
	  {
		  TdsAmount = myFormatter.parse((CommonFunction.checkNull(data.get(19))).trim());
	  logger.info("TdsAmount: "+TdsAmount);    	 
	 
	  }
	  paymentVO.setTdsAmount(myFormatter.format(TdsAmount));
	  paymentVO.setAuthorRemarks((CommonFunction.checkNull(data.get(20)).trim()));
	  paymentVO.setInstrumentID((CommonFunction.checkNull(data.get(21)).trim()));
	  paymentVO.setRemarks((CommonFunction.checkNull(data.get(22)).trim()));
	  paymentVO.setLbxpayTo((CommonFunction.checkNull(data.get(23)).trim()));
	  if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("CS"))
	  {
		  paymentVO.setPayTo("CUSTOMER");
	  }
	  else if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("MF"))
	  {
		  paymentVO.setPayTo("MANUFACTURER");
	  }
	  else if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("SU"))
	  {
		  paymentVO.setPayTo("SUPPLIER/DEALER/VENDOR ");
	  }
	  else if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("OTH"))
	  {
		  if((CommonFunction.checkNull(data.get(3)).trim()).equalsIgnoreCase("CS")){
			  paymentVO.setPayTo("OTHERS");
		  }
	  }
	  paymentVO.setPayeeName((CommonFunction.checkNull(data.get(24)).trim()));
	  paymentVO.setLoanRecStatus((CommonFunction.checkNull(data.get(25)).trim()));
	  paymentVO.setTaStatus((CommonFunction.checkNull(data.get(26)).trim()));
	  paymentVO.setLbxTaLoanNoHID((CommonFunction.checkNull(data.get(27)).trim()));
	  paymentVO.setTaLoanNo((CommonFunction.checkNull(data.get(28)).trim()));
	  paymentVO.setTaCustomerName((CommonFunction.checkNull(data.get(29)).trim()));
	  logger.info(" searchPaymentData setInstrumentID:-------------"+data.get(21));
	  
	 
	  
	 

	payableList.add(paymentVO);	
 }

}

}catch(Exception e){
	e.printStackTrace();
		}finally{
			bufInsSql=null;
		}

return  payableList;	
}

public ArrayList<PaymentMakerForCMVO> getbussinessPartner(){
	  
	  ArrayList<PaymentMakerForCMVO> getBPList=new ArrayList<PaymentMakerForCMVO>();
	  StringBuilder query = new StringBuilder();
	  try{
		  ArrayList mainList=new ArrayList ();
		  ArrayList subList =new ArrayList();
	 
		  query.append("select VALUE,DESCRIPTION from generic_master where" +
		  		" GENERIC_KEY='BPTYPE' and rec_status ='A' ");  
		  logger.info("In getBPList:------"+query);	
			
			mainList=ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<mainList.size();i++)
			{
				subList= (ArrayList)mainList.get(i);
				if(subList.size()>0){
					PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
					paymentVO.setBusinessPartnerType((CommonFunction.checkNull(subList.get(0)).trim()));
					paymentVO.setBusinessPartnerDesc((CommonFunction.checkNull(subList.get(1)).trim()));
					
					getBPList.add(paymentVO);
				}	
			}
			
				}catch(Exception e){
					e.printStackTrace();
	              } 
				finally
				{
					query = null;
				}
	return getBPList;			

}

public ArrayList <PaymentMakerForCMVO> saveddatatoApprove(PaymentMakerForCMVO paymentMakerForCMVO)
{

	
  ArrayList<PaymentMakerForCMVO> datatoapproveList=new ArrayList<PaymentMakerForCMVO>();
 
	  ArrayList searchlist=new ArrayList();
    logger.info("In searchAuthorData....................");
    StringBuffer bufInsSql =	new StringBuffer();
    try{
    /*
      bufInsSql.append("SELECT CLD.LOAN_ID,CLD.LOAN_NO,(SELECT CUSTOMER_NAME FROM gcd_customer_m WHERE  CLD.LOAN_CUSTOMER_ID=CUSTOMER_ID)CUSTOMER_Name," +
  		" CID.BPTYPE," +
  		" (select DESCRIPTION from generic_master where GENERIC_KEY='BPTYPE' and PARENT_VALUE='NULL' AND VALUE=CID.BPTYPE)bp_type_DESC," +
  		" CID.BPID," +
  		"  (SELECT BP_NAME FROM business_partner_view WHERE  BP_ID=CID.BPID and BP_TYPE=CID.BPTYPE)BPNAME," +
  		"  CID.INSTRUMENT_MODE,date_format(CID.RECEIVED_DATE,'%d-%m-%Y'),CID.INSTRUMENT_NO,date_format(CID.INSTRUMENT_DATE,'%d-%m-%Y'),CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" +
  		"  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME,CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.INSTRUMENT_AMOUNT,CID.TDS_AMOUNT ,CID.REMARKS,CID.INSTRUMENT_ID" +
  		"  from cr_instrument_dtl CID," +
  		" cr_loan_dtl CLD" +
  		" where CID.TXNID=CLD.LOAN_ID" +
  		" AND CID.REC_STATUS='P'" +
  		" AND CID.TXNID='"+paymentMakerForCMVO.getLbxLoanNoHID()+"' "+
        " AND CID.INSTRUMENT_ID='"+paymentMakerForCMVO.getInstrumentID()+"' ");
  
    */
    bufInsSql.append(" SELECT DISTINCT CLD.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME,CID.BPTYPE, " +		  
	  		"   GM.DESCRIPTION,CID.BPID ,BPV.BP_NAME,CID.INSTRUMENT_MODE,");
    bufInsSql.append(dbo);
    bufInsSql.append("date_format(CID.RECEIVED_DATE,'"+dateFormat+"') AS RECEIVED_DATE,CID.INSTRUMENT_NO,");
    bufInsSql.append(dbo);
    bufInsSql.append("date_format(CID.INSTRUMENT_DATE,'"+dateFormat+"') AS INSTRUMENT_DATE," +
	  		" CID.ISSUEING_BANK_ACCOUNT,CID.ISSUEING_BANK_ID,(SELECT BANK_NAME FROM com_bank_m where BANK_ID=CID.ISSUEING_BANK_ID)BANK_NAME,CID.ISSUEING_BRANCH_ID" +
	  		"  ,(SELECT BANK_BRANCH_NAME FROM com_bankbranch_m where BANK_BRANCH_ID=CID.ISSUEING_BRANCH_ID)BANK_NAME," +
	  		" CID.ISSUING_MICR_CODE,CID.ISSUING_IFSC_CODE,CID.INSTRUMENT_AMOUNT,CID.TDS_AMOUNT ,CID.REMARKS,CID.INSTRUMENT_ID,CID.MAKER_REMARKS,CID.PAY_TO,CID.PAYEE_NAME,CLD.REC_STATUS" +
	  		"  ,(SELECT DESCRIPTION FROM generic_master WHERE GENERIC_KEY='PAY_TO' AND VALUE=CID.PAY_TO) PAY_TO_DESC " +
	  		" ,CID.TA_Adjustment_FLAG,(SELECT LOAN_NO FROM CR_LOAN_DTL CLD JOIN CR_INSTRUMENT_DTL CID  ON CLD.LOAN_ID=CID.TA_LOAN_ID WHERE CID.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+"' ) AS TA_LOAN_NO "+
	  		" ,CID.TA_LOAN_ID,CID.TA_ACCOUNT_NAME "+
	  		"  from cr_instrument_dtl CID,gcd_customer_m GCM ," +
	  		" cr_loan_dtl CLD, business_partner_view BPV,generic_master  GM" +
	  		" where CID.TXNID=CLD.LOAN_ID" +
	  		"  AND BPV.BP_ID=CID.BPID AND BPV.BP_TYPE=CID.BPTYPE" +
	        "  AND GM.VALUE=CID.BPTYPE  and CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID" +
	        "  AND GM.GENERIC_KEY='BPTYPE' " +
	        "  and INSTRUMENT_TYPE='P'AND CID.REC_STATUS='F'" +
	  		" and isnull(CID.TXN_TYPE,'')='LIM' AND CID.TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim())+"' "+
	        " AND CID.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+"' ");
	  		
	  		     
	  logger.info("In searchAuthorData......... query..........."+bufInsSql.toString());
    searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
    logger.info("searchAuthorData search Data size is...."+searchlist.size());

    for(int i=0;i<searchlist.size();i++){
    logger.info("paymentMasearchAuthorDataker search List "+searchlist.get(i).toString());
    ArrayList data=(ArrayList)searchlist.get(i);

    if(data.size()>0){ 	  
	  PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
	  paymentVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(0)).trim()));
	  paymentVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
	  paymentVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
	  logger.info(" searchPaymentData setCustomerName:-------------"+data.get(2));
	  paymentVO.setLbxBPType((CommonFunction.checkNull(data.get(3)).trim()));
	  paymentVO.setBusinessPartnerType((CommonFunction.checkNull(data.get(4)).trim()));
	  paymentVO.setLbxBPNID((CommonFunction.checkNull(data.get(5)).trim()));
	  paymentVO.setBusinessPartnerName((CommonFunction.checkNull(data.get(6)).trim()));
	  paymentVO.setPaymentMode((CommonFunction.checkNull(data.get(7)).trim()));
	  paymentVO.setPaymentDate((CommonFunction.checkNull(data.get(8)).trim()));
	  paymentVO.setInstrumentNumber((CommonFunction.checkNull(data.get(9)).trim()));
	  paymentVO.setInstrumentDate((CommonFunction.checkNull(data.get(10)).trim()));
	  paymentVO.setBankAccount((CommonFunction.checkNull(data.get(11)).trim()));
	  logger.info(" searchPaymentData setBankAccount:-------------"+data.get(11));
	  paymentVO.setLbxBankID((CommonFunction.checkNull(data.get(12)).trim()));
	  paymentVO.setBank((CommonFunction.checkNull(data.get(13)).trim()));
	  paymentVO.setLbxBranchID((CommonFunction.checkNull(data.get(14)).trim()));
	  paymentVO.setBranch((CommonFunction.checkNull(data.get(15)).trim()));
	  paymentVO.setMicr((CommonFunction.checkNull(data.get(16)).trim()));
	  paymentVO.setIfsCode((CommonFunction.checkNull(data.get(17)).trim()));
	  Number PaymentAmount =0;
	  if(!CommonFunction.checkNull(data.get(18)).equalsIgnoreCase(""))
		  	PaymentAmount = myFormatter.parse((CommonFunction.checkNull(data.get(18))).trim());
	  
	  logger.info("PaymentAmount: "+PaymentAmount);			
	  paymentVO.setPaymentAmount(myFormatter.format(PaymentAmount));
	  Number TdsAmount = 0;
	  if(!CommonFunction.checkNull(data.get(19)).equalsIgnoreCase(""))
	  
		 TdsAmount = myFormatter.parse((CommonFunction.checkNull(data.get(19))).trim());
	  logger.info("TdsAmount: "+TdsAmount);			
	  paymentVO.setTdsAmount(myFormatter.format(TdsAmount));
	  
	  paymentVO.setAuthorRemarks((CommonFunction.checkNull(data.get(20)).trim()));
	  paymentVO.setInstrumentID((CommonFunction.checkNull(data.get(21)).trim()));
	  paymentVO.setRemarks((CommonFunction.checkNull(data.get(22)).trim()));
	  /*Commented By Arun For Pay To is not proper At Author
		  * 
		  *  if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("CS"))
		  {
			  paymentVO.setPayTo("CUSTOMER");
		  }
		  else if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("MF"))
		  {
			  paymentVO.setPayTo("MANUFACTURER");
		  }
		  else if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("SU"))
		  {
			  paymentVO.setPayTo("SUPPLIER");
		  }
		  else if((CommonFunction.checkNull(data.get(23)).trim()).equalsIgnoreCase("CS"))
		  {
			  paymentVO.setPayTo("OTHERS");
		  }*/
		  paymentVO.setPayeeName((CommonFunction.checkNull(data.get(24)).trim()));
		  paymentVO.setLoanRecStatus((CommonFunction.checkNull(data.get(25)).trim()));
		  //Added By Arun Starts Here
		  paymentVO.setPayTo((CommonFunction.checkNull(data.get(26)).trim()));
		  //Added By Arun Ends Here
		  //Mradul starts Here
		  paymentVO.setTaStatus((CommonFunction.checkNull(data.get(27)).trim()));
		  paymentVO.setTaLoanNo((CommonFunction.checkNull(data.get(28)).trim()));
		  paymentVO.setLbxTaLoanNoHID((CommonFunction.checkNull(data.get(29)).trim()));
		  paymentVO.setTaCustomerName((CommonFunction.checkNull(data.get(30)).trim()));
	  logger.info(" searchPaymentData setInstrumentID:-------------"+data.get(21));
  	 
	  
	  datatoapproveList.add(paymentVO);	
     }

    }

}catch(Exception e){
	e.printStackTrace();
		}finally{
			searchlist.clear();
			bufInsSql=null;
		}
return  datatoapproveList;	
}


public boolean closeViewPayable(PaymentMakerForCMVO paymentMakerForCMVO) {
	  logger.info("###  In closeViewPayable #### ");
	boolean status=false;
	StringBuilder bptype = new StringBuilder();
	StringBuilder loanId = new StringBuilder();
	StringBuilder deleteCount = new StringBuilder();
	StringBuilder data = new StringBuilder();
		try{
			ArrayList queryList=new ArrayList ();
			ArrayList subList =new ArrayList();
			ArrayList list =new ArrayList();
			ArrayList subList1 =new ArrayList();
			ArrayList dataList =new ArrayList();
			PrepStmtObject insertPrepStmtObject=null;
			StringBuffer bufInsSql = null;

//			String bptype="";
//			String loanId="";
			  String allotedAmount[]=paymentMakerForCMVO.getAllocatedArry();
		
			 
			  logger.info("### In closeViewPayable  allotedAmount size #### "+allotedAmount.length);
			
			  
			  String adviseId[]=paymentMakerForCMVO.getTxnAdvicedIDArry();
			  
			  logger.info("### In closeViewPayable  adviseId size #### "+adviseId.length);
			
			 
			  String tdsAllotedAmount[]=paymentMakerForCMVO.getTdsAllocatedArry();
			  
			  logger.info("### In closeViewPayable  tdsAllotedAmount size #### "+tdsAllotedAmount.length);
			
			 
			  deleteCount.append(ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='" +paymentMakerForCMVO.getInstrumentID()+ "'"));
			
			  logger.info("### In closeViewPayable  deleteCount #### "+deleteCount);
			  if(!(deleteCount.toString()).equalsIgnoreCase("0")){
				  
                insertPrepStmtObject =  new PrepStmtObject();
                bufInsSql = new StringBuffer();
                bufInsSql.append(" DELETE FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='"+(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+ "'");
                logger.info("### In closeViewPayable  bufInsSql #### "+bufInsSql.toString());
                insertPrepStmtObject.setSql(bufInsSql.toString());
                queryList.add(insertPrepStmtObject);  
                
            }
			/*  
			  data.append(" Select BP_TYPE,Loan_Id From cr_txnadvice_dtl WHERE TXNADVICE_ID='"+adviseId[0]+"'");
              logger.info("### data qry is.. #### "+data);
              dataList=ConnectionDAO.sqlSelect(data.toString());
      		if(dataList.size()>0)
      		{
      			subList1= (ArrayList)dataList.get(0);
      			if(subList1.size()>0){
      				
      				bptype.append(CommonFunction.checkNull(subList1.get(0)).trim());
      				loanId.append(CommonFunction.checkNull(subList1.get(1)).trim());
      				
      			}
      		}
      		 logger.info("### bptype is.. #### "+bptype);
      		 logger.info("### loanId is.. #### "+loanId);
           
          */
           
		 for(int i=0;i<allotedAmount.length;i++)
			{
			 insertPrepStmtObject=null;
           bufInsSql=null;
           insertPrepStmtObject =  new PrepStmtObject();
           bufInsSql = new StringBuffer();
           
           String alloc=CommonFunction.checkNull(allotedAmount[i]);
           if(alloc.equalsIgnoreCase("")){
        	   alloc="0"; 
           }
           double allocatedAmnt= myFormatter.parse(alloc).doubleValue();
            logger.info("### In double allotedAmount #### "+allocatedAmnt);
              if(allocatedAmnt>0)
              {

             
			  bufInsSql.append("insert into cr_pmnt_dtl( PMNT_DATE,PMNT_AMOUNT,TDS_AMOUNT,INSTRUMENT_ID,TXNADVICEID,REC_STATUS,MAKER_ID,MAKER_DATE )");
			  bufInsSql.append(" values ( ");
			  bufInsSql.append(" (select RECEIVED_DATE from cr_instrument_dtl where INSTRUMENT_ID=? )," );
		      bufInsSql.append(" ?," );
			  bufInsSql.append(" ?," );
			  bufInsSql.append(" ?," );
			  bufInsSql.append(" ?," );
			  bufInsSql.append(" 'P'," );
			  bufInsSql.append(" ?," );
			  bufInsSql.append(dbo);
			  bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ''+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )" );
			  
			
			  if((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else 
					insertPrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()));
				if((CommonFunction.checkNull(alloc)).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(alloc).trim())).toString());
				if((CommonFunction.checkNull(tdsAllotedAmount[i])).equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(tdsAllotedAmount[i]).trim())).toString());
				if((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()));
				if((CommonFunction.checkNull(adviseId[i])).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(adviseId[i]).trim()));
				if((CommonFunction.checkNull(paymentMakerForCMVO.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getMakerId()).trim()));
				
				if((CommonFunction.checkNull(paymentMakerForCMVO.getBusinessDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getBusinessDate()).trim()));
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
			  queryList.add(insertPrepStmtObject);
			
		
			  
			  logger.info("In insert of  cr_pmnt_dtl"+insertPrepStmtObject.printQuery());
			 
			/*  insertPrepStmtObject =  new PrepStmtObject();
	           bufInsSql = new StringBuffer();
	           
			  bufInsSql.append(" Update tmp_adv_payrec Set PMNT_AMOUNT=? WHERE  LOAN_ID=? And ADVICE_TYPE='P' And TXNADVICE_ID=? And BP_TYPE=?  And MAKER_ID=?");
			    if((CommonFunction.checkNull(alloc)).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(alloc).trim())).toString());
			    
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
			    
			    
			    if((CommonFunction.checkNull(paymentMakerForCMVO.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getMakerId()).trim()));
			
			      insertPrepStmtObject.setSql(bufInsSql.toString());
				  queryList.add(insertPrepStmtObject);
				
				  logger.info("In Update of  tmp_adv_payrec "+insertPrepStmtObject.printQuery());
		
			  */
              }
			}
			
		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
		logger.info("In closeViewPayable,,,,,"+status);
		
		} 
			catch(Exception e){
			e.printStackTrace();
		}
			finally
			{
				bptype = null;
				loanId = null;
				deleteCount = null;
				data= null;
			}

		return status;

}

public String getpmntId(){
	String pmntId="";
	StringBuilder paymentId = new StringBuilder();
	try{
		paymentId.append("SELECT max(PMNT_ID) from cr_pmnt_dtl  WITH (ROWLOCK) ");
		
		pmntId=ConnectionDAO.singleReturn(paymentId.toString());
  		logger.info("IN paymentId ### "+paymentId);
    }
  	   catch(Exception e){
				e.printStackTrace();
			}
  	   finally
  	   {
  		 paymentId = null;
  	   }

			return pmntId;
		}

public String updateFlagByPaymentAuthor(PaymentMakerForCMVO paymentMakerForCMVO) {
	boolean status=false;				
	String procval =""; 
	StringBuilder s1 = new StringBuilder();
	StringBuilder s2 = new StringBuilder();
//	CallableStatement cst=null;
//	Connection con=ConnectionDAO.getConnection();
	try{
    StringBuffer bufInsSql =	new StringBuffer();
	ArrayList queryList=new ArrayList();
    ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
//	 String s1="";
//	String s2="";
//	con.setAutoCommit(false);
	int statusProc=0;		
	PrepStmtObject updatePrepStmtObject = new PrepStmtObject();	
	
	logger.info(" In updateFlagByPaymentAuthor BeforeProc: ");
	logger.info("In updateFlagByPaymentAuthor getInstrumentID()"+paymentMakerForCMVO.getInstrumentID());
	logger.info("In updateFlagByPaymentAuthor getDecision"+paymentMakerForCMVO.getDecision());
	logger.info("In updateFlagByPaymentAuthor getInstrumentID()"+paymentMakerForCMVO.getComments());
	logger.info(" In pay_approveReject_Rec Procedure ");
//	cst=con.prepareCall("call pay_approveReject_Rec(?,?,?,?,?,?)");
	
	in.add(((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID().trim()))));
	in.add(((CommonFunction.checkNull(paymentMakerForCMVO.getDecision().trim()))));
	in.add(((CommonFunction.checkNull(paymentMakerForCMVO.getComments().trim()))));
	in.add("P");
	in.add(((CommonFunction.checkNull(paymentMakerForCMVO.getUserId().trim()))));
	String date=CommonFunction.changeFormat(paymentMakerForCMVO.getBusinessDate());
	in.add(date);
//	cst.registerOutParameter(5, Types.CHAR);
//	cst.registerOutParameter(6, Types.CHAR);
	out.add(s1.toString());
    out.add(s2.toString());
//	statusProc=cst.executeUpdate();
//	String s1 = CommonFunction.checkNull(cst.getString(5));
//	String s2 = CommonFunction.checkNull(cst.getString(6));

    outMessages=(ArrayList) ConnectionDAO.callSP("pay_approveReject_Rec",in,out);
    s1.append(CommonFunction.checkNull(outMessages.get(0)));
	 s2.append(CommonFunction.checkNull(outMessages.get(1)));
	logger.info("s1: "+s1);
	logger.info("s2: "+s2);
	logger.info("Status for Proc: "+statusProc);
	logger.info("After Proc: ");
	procval =(s2.toString());			
	if((s1.toString()).equalsIgnoreCase("S"))
	{
		logger.info("After proc call..commit.error message."+s2);
//		con.commit();
//		con.close();
	}else{
		logger.info("After proc call..rollback.error message."+s2);
//		con.rollback();
//		con.close();
	}

	
	if(!(s1.toString()).equalsIgnoreCase("S")){
		logger.info("After Proc inside If ");
		status=false;
	}else
		status=true;
	
	
	/*String query="update cr_instrument_dtl set REC_STATUS='"+paymentMakerForCMVO.getDecision()+"'" +
			" ,REMARKS='"+paymentMakerForCMVO.getComments()+"' where INSTRUMENT_ID='"+paymentMakerForCMVO.getInstrumentID()+"' and REC_STATUS ='F'";	
*/
	/*bufInsSql.append("update cr_instrument_dtl set REC_STATUS=? ,REMARKS=? where INSTRUMENT_ID=? and REC_STATUS ='F'");	
	 if(CommonFunction.checkNull(paymentMakerForCMVO.getDecision()).equalsIgnoreCase(""))
			updatePrepStmtObject.addNull();
		else
			updatePrepStmtObject.addString(paymentMakerForCMVO.getDecision());
	 if(CommonFunction.checkNull(paymentMakerForCMVO.getComments()).equalsIgnoreCase(""))
			updatePrepStmtObject.addNull();
		else
			updatePrepStmtObject.addString(paymentMakerForCMVO.getComments());
	
	 if(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).equalsIgnoreCase(""))
			updatePrepStmtObject.addNull();
		else
			updatePrepStmtObject.addString(paymentMakerForCMVO.getInstrumentID());
	
	   
	    updatePrepStmtObject.setSql(bufInsSql.toString());	
		queryList.add(updatePrepStmtObject);
	
		
        
	
	
	updatePrepStmtObject=new PrepStmtObject();
	 bufInsSql =	new StringBuffer();
   String	query2="update cr_pmnt_dtl set REC_STATUS='"+paymentMakerForCMVO.getDecision()+"' where  INSTRUMENT_ID='"+paymentMakerForCMVO.getInstrumentID()+"'  and REC_STATUS ='F'";
	bufInsSql.append("update cr_pmnt_dtl set REC_STATUS=? where  INSTRUMENT_ID=?  and REC_STATUS ='F'");	
	 if(CommonFunction.checkNull(paymentMakerForCMVO.getDecision()).equalsIgnoreCase(""))
			updatePrepStmtObject.addNull();
		else
			updatePrepStmtObject.addString(paymentMakerForCMVO.getDecision());
	
	 if(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).equalsIgnoreCase(""))
			updatePrepStmtObject.addNull();
		else
			updatePrepStmtObject.addString(paymentMakerForCMVO.getInstrumentID());
	
	
	    updatePrepStmtObject.setSql(bufInsSql.toString());	
		queryList.add(updatePrepStmtObject);
	
	

	
	
	queryList.add(query2);
		logger.info("In updateFlagByPaymentAuthor update cr_pmnt_dtl"+query2);
	
	logger.info("In updateFlagByPaymentAuthor paymentMakerForCMVO.getDecision()"+paymentMakerForCMVO.getDecision());
	if( paymentMakerForCMVO.getDecision().equalsIgnoreCase("X") )
	{
	
	updatePrepStmtObject=new PrepStmtObject();
	bufInsSql =	new StringBuffer();
	 
	bufInsSql.append("UPDATE cr_txnadvice_dtl TXN_A ");
    bufInsSql.append(" SET AMOUNT_IN_PROCESS  = AMOUNT_IN_PROCESS - (SELECT PMNT_AMOUNT FROM cr_pmnt_dtl CP  WHERE TXN_A.TXNADVICE_ID=CP.TXNADVICEID AND CP.INSTRUMENT_ID=? AND CP.REC_STATUS='F' )");
    bufInsSql.append(" WHERE TXNADVICE_ID IN (SELECT TXNADVICEID FROM cr_pmnt_dtl  WHERE INSTRUMENT_ID=? AND REC_STATUS='F' ) AND ADVICE_TYPE='P'");
	
    if(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).equalsIgnoreCase(""))
		updatePrepStmtObject.addNull();
	else
		updatePrepStmtObject.addString(paymentMakerForCMVO.getInstrumentID());
    
    if(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).equalsIgnoreCase(""))
		updatePrepStmtObject.addNull();
	else
		updatePrepStmtObject.addString(paymentMakerForCMVO.getInstrumentID());



    updatePrepStmtObject.setSql(bufInsSql.toString());	
	queryList.add(updatePrepStmtObject);	
    
    //bufInsSql.append("update cr_txnadvice_dtl set AMOUNT_IN_PROCESS= AMOUNT_IN_PROCESS-allocated_amount where  INSTRUMENT_ID=?  and REC_STATUS ='F'");
	}
	 
	
	
	logger.info("IN updateOnSave query ## "+updatePrepStmtObject.printQuery());
	logger.info("IN updateOnSave query ## "+queryList.toString());
	status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);*/
	
	logger.info("In updateFlagByPaymentAuthor,,,,,"+status);

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
public ArrayList<PaymentMakerForCMVO> onViewPayable(PaymentMakerForCMVO paymentMakerForCMVO,int loanId,String bpType,int bpId){

 	ArrayList<PaymentMakerForCMVO> getPayableList=new ArrayList<PaymentMakerForCMVO>();
 	StringBuilder query = new StringBuilder();
 	try{
 		ArrayList mainList=new ArrayList ();
 		ArrayList subList =new ArrayList();
// 		CallableStatement cst=null;
// 		Connection con=ConnectionDAO.getConnection();
 				
 		logger.info(" In onViewPayable....");	
 			
 	
 		query.append("SELECT ");
 		query.append(dbo);
 		query.append("DATE_FORMAT(A.ADVICE_DATE,'"+dateFormat+"') AS ADVICE_DATE,(Select CHARGE_DESC From com_charge_code_m "+
 		" Where CHARGE_CODE=a.CHARGE_CODE_ID) CHARGE,A.ORG_ADVICE_AMOUNT,A.WAIVE_OFF_AMOUNT,A.TDS_AMOUNT," +
 		" A.TXN_ADJUSTED_AMOUNT,A.AMOUNT_IN_PROCESS,"+
        " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT, A.TXNADVICE_ID,A.ADVICE_AMOUNT  "+
 		"  FROM cr_txnadvice_dtl A   WHERE A.REC_STATUS IN ('A', 'F') and (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 "+
        "  AND  A.ADVICE_TYPE='P' AND LOAN_ID='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+ "'" +
       	" AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "'" +
       	" AND A.BP_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpId).trim())+"' ORDER BY A.ADVICE_DATE ASC");

 		
 		logger.info("In onViewPayable"+query);	
 		
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
 		    	
 				paymentVO.setTxnAdvicedID((CommonFunction.checkNull(subList.get(8)).trim()));
 				if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAdviceAmount("0");
 				else
 				{
 					Number adviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(9)).trim()));
 					paymentVO.setAdviceAmount(myFormatter.format(adviceAmt));
 				}
 			
 				getPayableList.add(paymentVO);
 			}
 		}
 			}catch(Exception e){
 				e.printStackTrace();
 			}
 			finally
 			{
 				query = null ;
 			}

 			return getPayableList;
 		}
public ArrayList<PaymentMakerForCMVO> getViewPayable(int loanId,String bpType,int bpId,String uId,String amt,int instrumentId,String tdsAmount) {
	
    ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	StringBuilder s1 = new StringBuilder();
	StringBuilder s2 = new StringBuilder();
	StringBuilder procval = new StringBuilder();
	StringBuilder query = new StringBuilder();
	
//	 String s1="";
//	String s2="";
	ArrayList<PaymentMakerForCMVO> getPayableList=new ArrayList<PaymentMakerForCMVO>();
//	CallableStatement cst=null;
//	Connection con=ConnectionDAO.getConnection();
	try{
		ArrayList mainList=new ArrayList ();
		ArrayList subList =new ArrayList();
		
//	    con.setAutoCommit(false);
		logger.info(" In getViewPayable Before proc call....");	
		logger.info("loanId"+loanId);	
		logger.info("bpType"+bpType);
		logger.info("bpId"+bpId);	
		logger.info("uId"+uId);	
		logger.info("amt"+amt);
		logger.info("instrumentId"+instrumentId);
		logger.info("tdsAmount"+tdsAmount);
		logger.info(" IN Advice_Pay_Rec PROCEDURE");
//			cst=con.prepareCall("call Advice_Pay_Rec(?,?,?,?,?,?,?,?,?,?)");
			in.add("P");
			in.add(loanId);
			in.add(bpType);
			in.add(bpId);
			in.add(uId);
			in.add(amt);
			in.add(instrumentId);
			in.add(tdsAmount);
//			cst.registerOutParameter(9, Types.CHAR);
//			cst.registerOutParameter(10, Types.CHAR);
			out.add(s1.toString());
		    out.add(s2.toString());
//			cst.executeUpdate();
//			String s1= cst.getString(9);
//			String s2 = cst.getString(10);
		    outMessages=(ArrayList) ConnectionDAO.callSP("Advice_Pay_Rec",in,out);

		    s1.append(CommonFunction.checkNull(outMessages.get(0)));
		    s2.append(CommonFunction.checkNull(outMessages.get(1)));
			logger.info("s1: "+s1);
			logger.info("s2: "+s2);
//			String procval="";
			if((s1.toString()).equalsIgnoreCase("S"))
			{
				logger.info("After proc call..commit.error message."+s2);
				procval=s2;
//				con.commit();
//				con.close();
			}else{
				logger.info("After proc call..rollback.error message."+s2);
				procval=s2;
//				con.rollback();
//				con.close();
			}
			
			logger.info("After proc call....");	
		query.append("SELECT ");
		query.append(dbo);
		query.append("DATE_FORMAT(A.ADVICE_DATE,'"+dateFormat+"') AS ADVICE_DATE,A.CHARGE,A.ADVICE_AMOUNT,A.BALANCE_AMOUNT,");
		query.append("ISNULL(A.PMNT_AMOUNT,0) PMNT_AMOUNT,A.AMOUNT_IN_PROCESS,A.TDS_AMOUNT ,A.TXNADVICE_ID, ");
		query.append("ISNULL(A.TDS_ALLOCATED_AMOUNT,0)TDS_ALLOCATED_AMOUNT"+
      " FROM tmp_adv_payrec A LEFT OUTER JOIN cr_pmnt_dtl cpd  ON CPD.TXNADVICEID=A.TXNADVICE_ID AND CPD.INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(instrumentId).trim())+"' "+
	  " WHERE  A.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' AND A.ADVICE_TYPE='P' "+
      " and A.BP_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim())+"' and A.BP_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpId).trim())+"'" +
      " and A.MAKER_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(uId).trim())+"' " +
      " Order By A.ADVICE_DATE, A.TXNADVICE_ID ASC; ");

		
		logger.info("In getListOfValues"+query);	
		
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
 					 Number OriginalAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
 					logger.info("OriginalAmount: "+OriginalAmount);			
 		 		 paymentVO.setOriginalAmount(myFormatter.format(OriginalAmount));
 				}
				if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
 					paymentVO.setBalanceAmount("0");
 				else
 				{
				Number BalanceAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
 				logger.info("BalanceAmount: "+BalanceAmount);			
 		    	paymentVO.setBalanceAmount(myFormatter.format(BalanceAmount));
 				}
				if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAllotedAmount("0");
 				else
 				{
 		    	Number AllotedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());
	 			logger.info("AllotedAmount: "+AllotedAmount);			
	 		    paymentVO.setAllotedAmount(myFormatter.format(AllotedAmount));
 				}
				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAmountInProcess("0");
 				else
 				{
				
 		    	Number AmountInProcess = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
	 			logger.info("AmountInProcess: "+AmountInProcess);			
	 		    paymentVO.setAmountInProcess(myFormatter.format(AmountInProcess));
 				}
				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
 					paymentVO.setTdsadviseAmount("0");
 				else
 				{
				
	 		    Number TdsadviseAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
 			    logger.info("TdsadviseAmount: "+TdsadviseAmount);			
 		    	paymentVO.setTdsadviseAmount(myFormatter.format(TdsadviseAmount));
 				}
 		    	paymentVO.setTxnAdvicedID((CommonFunction.checkNull(subList.get(7)).trim()));
 		    	if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
 					paymentVO.setTdsadviseAmount("0");
 				else
 				{
 		    	Number TdsAllocatedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
 			    logger.info("TdsAllocatedAmount: "+TdsAllocatedAmount);			
 		    	paymentVO.setTdsAllocatedAmount(myFormatter.format(TdsAllocatedAmount));
 				}
 		       logger.info("procval:----> "+CommonFunction.checkNull(procval.toString()));			
 		    	paymentVO.setProcVal((CommonFunction.checkNull(procval.toString())));
			
				getPayableList.add(paymentVO);
			}
		}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				in = null;
				out = null;
				outMessages = null;
				s1 = null;
				s2 = null;
				procval = null;
				query = null;
			}

			
			return getPayableList;
		}

public  int existData(Object ob){
	  int result=0;
	  PaymentMakerForCMVO paymentVO =  (PaymentMakerForCMVO)ob;
	  StringBuilder existCount = new StringBuilder();
	   try{
		   		existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getInstrumentNumber()).trim())+"'" +
	  	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getLbxBankID()).trim())+"'and cid.ISSUEING_BRANCH_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getLbxBranchID()).trim())+"' " +
	  	  		" and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getLbxBPNID()).trim())+"'" +
	  	  		" and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getLbxBPType()).trim())+"' and INSTRUMENT_TYPE='P' AND REC_STATUS<>'X'");
	  
	 result=Integer.parseInt(ConnectionDAO.singleReturn(existCount.toString()));
		logger.info("IN existData  ### "+existCount);
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

//change by Mradul
			public String checkPaymentStatusFromInventory(Object ob) {
				
				String result="1";
			 	String inventoryFlag="";	
			 	String bookNoNonMandatory="";
			 	String bookNoValidate="";
			 	String bookNoValidate2="";
				String checkAllBranch="";
			 	PaymentMakerForCMVO paymentVO =  (PaymentMakerForCMVO)ob;
			 	StringBuilder existCount = new StringBuilder();
		  		StringBuilder inventoryFlagInMst = new StringBuilder();
		  		StringBuilder existInStationary = new StringBuilder();
		  		StringBuilder allBranch = new StringBuilder();
		  		StringBuilder checkUser = new StringBuilder();
		  		StringBuilder nonMandatory= new StringBuilder();
		  		StringBuilder bookNoValidatation= new StringBuilder();
		  		StringBuilder bookNoValidation2= new StringBuilder();
			  	String checkUserAvailable="";
		  		String IssuingUser=paymentVO.getMakerId();
		  		String instrumentNumber=paymentVO.getInstrumentNumber();
		  		int receiptChequeNo=0;
		  		/*if(!CommonFunction.checkNull(instrumentNumber).equalsIgnoreCase(""))
		  		{
		  		  receiptChequeNo=(Integer.parseInt(instrumentNumber))-1;
		  		}*/
		 try{
			 		nonMandatory.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='BOOK_NO_NON_MANDATORY'");
			 		bookNoNonMandatory=ConnectionDAO.singleReturn(nonMandatory.toString());
		  		
				  	inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CHEQUE_INVENTORY_FLAG'");
			  		logger.info("IN checkPaymentStatusFromInventory inventoryFlag ### "+inventoryFlagInMst.toString());
			  		inventoryFlag=ConnectionDAO.singleReturn(inventoryFlagInMst.toString());
  		
		  		if(inventoryFlag.equalsIgnoreCase("Y"))
		  	    {
			  		allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='"+paymentVO.getInstrumentNumber()+"' AND STATUS='A'");
			  		logger.info("IN checkAllBranch ### "+allBranch.toString());
			  		checkAllBranch=ConnectionDAO.singleReturn(allBranch.toString());
			  		
		  	if(checkAllBranch.equalsIgnoreCase("0"))
		  		{
		  		if(bookNoNonMandatory.equalsIgnoreCase("N"))
	  			{	
			  		checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+paymentVO.getInstrumentNumber()+"' AND ISNULL(ISSUING_USER,'')<>''");
			  		logger.info("IN checkInventory inventoryFlag ### "+checkUser.toString());
			  		checkUserAvailable=ConnectionDAO.singleReturn(checkUser.toString());	
	  			}else{
  					checkUserAvailable="1";
	  			}
  		if(!(checkUserAvailable.equalsIgnoreCase("0")))
  				{
		  			existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+paymentVO.getInstrumentNumber()+"'  AND ISNULL(ISSUING_USER,'"+IssuingUser+"')='"+IssuingUser+"' ");
			  		logger.info("IN checkUserAvailability  in existInStationary ### "+existInStationary.toString());
			  		result=ConnectionDAO.singleReturn(existInStationary.toString());
  			}else{
		    	 	existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+paymentVO.getInstrumentNumber()+"'  AND ISSUING_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentVO.getDefaultBranch()).trim())+"'");
			  		logger.info("IN checkReciptStatusFromInventory  in existInStationary ### "+existInStationary.toString());
			  		result=ConnectionDAO.singleReturn(existInStationary.toString());
  					}
		  		}
			  	else{
			  		result="1";
			  	}
		  	if(bookNoNonMandatory.equalsIgnoreCase("Y"))
	  		{
	  			bookNoValidatation.append(" SELECT BOOK_NO FROM CR_STATIONARY_STATUS_DTL WHERE RECEPT_CHEQUE_NO='"+paymentVO.getInstrumentNumber()+"' AND STATUS='A'");
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

// change by Ankit 13 dec 2013	
public ArrayList saveEnteredData(Object ob) {
	logger.info("In saveEnteredData DAOImpl......................");
	PaymentMakerForCMVO paymentVO =  (PaymentMakerForCMVO)ob;
	ArrayList status=new ArrayList();
	String taStatus=null;
	String makerDate=null;
	 
	 if(CommonFunction.checkNull(paymentVO.getTaStatus()).equalsIgnoreCase("on"))
	 {
		 taStatus="Y";
		 paymentVO.setPaymentMode("S");
	 }else{
		 taStatus="N";
	 }
	 String date=null;
	 String insDate=null;
	 ArrayList<Object> in =new ArrayList<Object>();
	 ArrayList<Object> out =new ArrayList<Object>();
	 ArrayList outMessages = new ArrayList();
	 StringBuilder s1 = new StringBuilder();
	 StringBuilder s2 = new StringBuilder();
	 StringBuilder instrumentId = new StringBuilder();
	
		try{

			in.add("");   															// for save pass blank instrument id
			in.add(CommonFunction.checkNull(paymentVO.getLbxLoanNoHID()).trim());  //TXNID
			in.add("LIM");														   //TXN_TYPE
			in.add(CommonFunction.checkNull(paymentVO.getLbxBPType()).trim());	   //BPTYPE
			in.add(CommonFunction.checkNull(paymentVO.getLbxBPNID()).trim());      //BPID
			in.add(CommonFunction.checkNull(paymentVO.getPaymentMode()).trim());   //INSTRUMENT_MODE
			
			date=CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getPaymentDate()).trim());
			in.add(date);															//RECEIVED_DATE
			
			in.add(CommonFunction.checkNull(paymentVO.getInstrumentNumber()).trim());  //INSTRUMENT_NO
			
			insDate=CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getInstrumentDate()).trim()); 
			in.add(insDate);														//INSTRUMENT_DATE
			in.add(CommonFunction.checkNull(paymentVO.getBankAccount()).trim());	//ISSUEING_BANK_ACCOUNT
			in.add(CommonFunction.checkNull(paymentVO.getLbxBankID()).trim());		//ISSUEING_BANK_ID
			in.add(CommonFunction.checkNull(paymentVO.getLbxBranchID()).trim());	//ISSUEING_BRANCH_ID
			in.add(CommonFunction.checkNull(paymentVO.getMicr()).trim());			//ISSUING_MICR_CODE
			in.add(CommonFunction.checkNull(paymentVO.getIfsCode()).trim());		//ISSUING_IFSC_CODE
			in.add(CommonFunction.checkNull(paymentVO.getBankAccount()).trim());	//DEPOSIT_BANK_ACCOUNT
			in.add(CommonFunction.checkNull(paymentVO.getLbxBankID()).trim());		//DEPOSIT_BANK_ID
			in.add(CommonFunction.checkNull(paymentVO.getLbxBranchID()).trim());	//DEPOSIT_BRANCH_ID
			in.add(CommonFunction.checkNull(paymentVO.getMicr()).trim());			//DEPOSIT_MICR_CODE
			in.add(CommonFunction.checkNull(paymentVO.getIfsCode()).trim());		//DEPOSIT_IFSC_CODE

			
			if(CommonFunction.checkNull(paymentVO.getPaymentAmount()).trim().equalsIgnoreCase(""))
				in.add("0.00");
			else
				in.add(myFormatter.parse(CommonFunction.checkNull(paymentVO.getPaymentAmount()).trim()).toString()); //INSTRUMENT_AMOUNT

			
			if(CommonFunction.checkNull(paymentVO.getTdsAmount()).trim().equalsIgnoreCase(""))
				in.add("0.00");
			else 
				in.add(myFormatter.parse(CommonFunction.checkNull(paymentVO.getTdsAmount()).trim()).toString());  //TDS_AMOUNT

			in.add(CommonFunction.checkNull(paymentVO.getRemarks()).trim());			//MAKER_REMARKS
			in.add("P");																//REC_STATUS
			in.add("P");																//INSTRUMENT_TYPE
			in.add("N");																//PDC_FLAG
			in.add(CommonFunction.checkNull(paymentVO.getDefaultBranch()).trim());		//DEFAULT_BRANCH
			in.add(CommonFunction.checkNull(paymentVO.getMakerId()).trim());			// MAKER_ID
			
			makerDate=CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getBusinessDate()).trim());
			in.add(makerDate);   //MAKER_DATE
			in.add(CommonFunction.checkNull(paymentVO.getLbxpayTo()).trim());		//PAY_TO
			in.add(CommonFunction.checkNull(paymentVO.getPayeeName()).trim());		//PAYEE_NAME
			in.add(CommonFunction.checkNull(taStatus).trim());
			
			if(!CommonFunction.checkNull(paymentVO.getLbxTaLoanNoHID()).trim().equalsIgnoreCase("")){
				in.add(paymentVO.getLbxTaLoanNoHID());
			}else{
				in.add(0);
			}
			
			in.add(CommonFunction.checkNull(paymentVO.getTaCustomerName()).trim());
			
			out.add(instrumentId.toString());
			out.add(s1.toString());
		    out.add(s2.toString());
		   
			
		     outMessages=(ArrayList) ConnectionDAO.callSP("SAVE_UPDATE_FORWARD_PAYMENT",in,out);
		    
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
			 logger.info("SAVE_UPDATE_FORWARD_PAYMENT instrumentId: "+CommonFunction.checkNull(instrumentId).toString()+" s1: "+CommonFunction.checkNull(s1).toString()+""+CommonFunction.checkNull(s2).toString());
			
	}
		catch(Exception e){
			e.printStackTrace();
		} finally
	  	   {
			in=null;
			out=null;
			outMessages=null;
			s1=null;
			s2=null;
			instrumentId=null;
			taStatus=null;
			makerDate=null;
 
	  	   }

		return status;
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

public String  checkOnPaymentForward(PaymentMakerForCMVO paymentMakerForCMVO) {
	
//	  String query="";
//	  String query1="";
	  String status="";
	  StringBuilder query = new StringBuilder();
	  StringBuilder query1 = new StringBuilder();
	  ArrayList<ReceiptMakerVO> List=new ArrayList ();
   String receiptAmount=  CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount()).trim(); 
   logger.info("In receiptAuthorGrid......receiptAmount  "+receiptAmount);
   double receiptAmnt=0.0;
   double tdsAmnt=0.0;
   try{ 
  	 if(receiptAmount.equalsIgnoreCase("")){
  	 receiptAmount="0";  
   }else{
  	 receiptAmount= myFormatter.parse(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount())).toString();
   }
   receiptAmnt=Double.parseDouble(receiptAmount);
   String tdsAmount=  CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount()).trim(); 
   logger.info("In receiptAuthorGrid......tdsAmount  "+tdsAmount);
   if(tdsAmount.equalsIgnoreCase("")){
  	 tdsAmount="0";  
   }
   else{
  	 tdsAmount= myFormatter.parse(CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount())).toString();
   }
   tdsAmnt=Double.parseDouble(tdsAmount);
   double Amount=(receiptAmnt + tdsAmnt);
   logger.info("In receiptAuthorGrid......Amount  "+Amount);

  	 query .append(" SELECT ");
	query.append("ISNULL(SUM(PMNT_AMOUNT),0) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+"'");
	  logger.info("IN checkAllocation  ### "+query);
   
    String AllocatedAmount=CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
    double VAllocatedAmount=0.0;
    if(AllocatedAmount.equalsIgnoreCase("")){
  	  AllocatedAmount="0";
    }
  	  VAllocatedAmount=Double.parseDouble(CommonFunction.checkNull(AllocatedAmount));

	  logger.info("VAllocatedAmount----"+VAllocatedAmount);
	  
	 query1.append(" SELECT ");
		query1.append("ISNULL(SUM(TDS_AMOUNT),0) FROM cr_pmnt_dtl WHERE INSTRUMENT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim())+"'");
 		logger.info("IN checkAllocation 1  ### "+query1);
		String TdsAllocatedAmount =ConnectionDAO.singleReturn(query1.toString());  		 
		
		double VTdsAllocatedAmount=0.0;
	      if(TdsAllocatedAmount.equalsIgnoreCase("")){
	    	  TdsAllocatedAmount="0";
	      }
	      VTdsAllocatedAmount=Double.parseDouble(CommonFunction.checkNull(TdsAllocatedAmount));
   	logger.info("VTdsAllocatedAmount----"+VTdsAllocatedAmount);
	 
   	if( VAllocatedAmount == 0.0000 ){
			status ="NA";
		  }
   	else if((Amount==VAllocatedAmount) && (tdsAmnt==VTdsAllocatedAmount)){
		  
		  status ="A";
	  }
	  else if((Amount != VAllocatedAmount) || (tdsAmnt != VTdsAllocatedAmount)){
		 
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
public  int existInsNoinPay(PaymentMakerForCMVO vo){
	  int result=0;
	  StringBuilder existCount = new StringBuilder();
	   try{
		   existCount.append("SELECT COUNT(*) FROM cr_instrument_dtl cid WHERE cid.INSTRUMENT_NO='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentNumber()).trim())+"'" +
	  		" and cid.ISSUEING_BANK_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBankID()).trim())+"'and cid.ISSUEING_BRANCH_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBranchID()).trim())+"'" +
	  	    " and BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPNID()).trim())+"'" +
	  	    " and BPTYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxBPType()).trim())+"' " +
	  	    " and INSTRUMENT_ID <>'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstrumentID()).trim())+"' and INSTRUMENT_TYPE='P' AND REC_STATUS<>'X'");
	  
	String res="";
	 res=ConnectionDAO.singleReturn(existCount.toString());
	 
	if(!(CommonFunction.checkNull(res)).equalsIgnoreCase("")){
  	
		result=Integer.parseInt(res);
	    }
		logger.info("IN existInsNoinPay  ### "+result);
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

public String saveAllForwardedData(PaymentMakerForCMVO paymentMakerForCMVO,String amount,String tdsAmount){
	
	boolean status=false;
	logger.info("In saveAllForwardedData,,,,,");
 		
	 String procval="";
	    ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();

	try{
	
		int statusProc=0;	
		in.add(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID().trim())); 
		in.add(((CommonFunction.checkNull(paymentMakerForCMVO.getLbxBPType().trim()))));
		in.add("P");
		in.add(amount);
		in.add(tdsAmount);
		in.add(((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID().trim()))));
		out.add(s1.toString());
	    out.add(s2.toString());
	    outMessages=(ArrayList) ConnectionDAO.callSP("Payment_Maker_Validation_M",in,out);
	    s1.append(CommonFunction.checkNull(outMessages.get(0)));
	    s2.append(CommonFunction.checkNull(outMessages.get(1)));
		logger.info("s1: "+s1);
		logger.info("s2: "+s2);
		logger.info("Status for Proc: "+statusProc);
		logger.info("After Proc: ");
		
		procval=CommonFunction.checkNull(s2.toString());
	
//change by Ankit  functionality move in proc		
	if(!(s1.toString()).equalsIgnoreCase("E")){
		if(!(s2.toString()).equalsIgnoreCase("F")){ 
		logger.info("After Proc inside If ");
		 String makerDate=null;
		 String date=null;
		 String insDate=null;
		 	ArrayList<Object> frdIn =new ArrayList<Object>();
			ArrayList<Object> frdOut =new ArrayList<Object>();
			ArrayList frdOutMessages = new ArrayList();
			StringBuilder frds1 = new StringBuilder();
			StringBuilder frds2 = new StringBuilder();
			StringBuilder instrumentId = new StringBuilder();

	//	 try{
			 	frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()));  // for Forward pass INSTRUMENT_ID
				frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim()));  //TXNID
				frdIn.add("");														  				   //TXN_TYPE
				frdIn.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getLbxBPType()).trim()));	   																	   //BPTYPE 
				frdIn.add("0");																	       //BPID
				frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode()).trim()));   //INSTRUMENT_MODE
				
				date=CommonFunction.changeFormat(CommonFunction.checkNull(paymentMakerForCMVO.getPaymentDate()).trim());
				frdIn.add(date);																	   //RECEIVED_DATE
				
				frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentNumber()).trim()));  //INSTRUMENT_NO
				
				insDate=CommonFunction.changeFormat(CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentDate()).trim());
				frdIn.add(insDate);																	//INSTRUMENT_DATE
				frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getBankAccount()).trim()));	//ISSUEING_BANK_ACCOUNT
				frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getLbxBankID()).trim()));		//ISSUEING_BANK_ID
				frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getLbxBranchID()).trim()));	//ISSUEING_BRANCH_ID
				frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getMicr()).trim()));			//ISSUING_MICR_CODE
				frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getIfsCode()).trim()));		//ISSUING_IFSC_CODE
				frdIn.add("");																			//DEPOSIT_BANK_ACCOUNT
				frdIn.add("0");																			//DEPOSIT_BANK_ID
				frdIn.add("0");																			//DEPOSIT_BRANCH_ID
				frdIn.add("");																			//DEPOSIT_MICR_CODE
				frdIn.add("");																			//DEPOSIT_IFSC_CODE			
				if((CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount())).trim().equalsIgnoreCase(""))
					frdIn.add("0.00");
				else
					frdIn.add(myFormatter.parse((CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount()).trim())).toString()); //INSTRUMENT_AMOUNT
				
				if((CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount())).trim().equalsIgnoreCase(""))
					frdIn.add("0.00");
				else 
					frdIn.add(myFormatter.parse(CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount()).trim()).toString());  //TDS_AMOUNT

				frdIn.add((CommonFunction.checkNull(paymentMakerForCMVO.getRemarks()).trim()));			//MAKER_REMARKS
				frdIn.add("F");																			//REC_STATUS
				frdIn.add("");																	//INSTRUMENT_TYPE
				frdIn.add("");																	//PDC_FLAG
				frdIn.add(CommonFunction.checkNull(paymentMakerForCMVO.getDefaultBranch()).trim());																	//DEFAULT_BRANCH
				frdIn.add(paymentMakerForCMVO.getMakerId().trim());							// MAKER_ID
				
				makerDate=CommonFunction.changeFormat(CommonFunction.checkNull(paymentMakerForCMVO.getBusinessDate().trim()));
				frdIn.add(makerDate);   														//MAKER_DATE
				frdIn.add("");																	//PAY_TO
				frdIn.add("");																	//PAYEE_NAME
				frdIn.add("");
				if(!CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim().equalsIgnoreCase("")){
					frdIn.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim()));
				}else{
					frdIn.add(0);
				}
				frdIn.add("");
				
				frdOut.add(instrumentId.toString());
				frdOut.add(s1.toString());
				frdOut.add(s2.toString());
			   
				
				frdOutMessages=(ArrayList) ConnectionDAO.callSP("SAVE_UPDATE_FORWARD_PAYMENT",frdIn,frdOut);
			    
			    instrumentId.append(CommonFunction.checkNull(frdOutMessages.get(0)));
			    frds1.append(CommonFunction.checkNull(frdOutMessages.get(1)));
			    frds2.append(CommonFunction.checkNull(frdOutMessages.get(2)));
		
			    if(CommonFunction.checkNull(frds1).equalsIgnoreCase("S")){
			    	status=true;
			    }


if(status){
	procval="queryexecuted";
	}}}
}catch(Exception e){
	e.printStackTrace();
} 
			finally
			{
				in = null;
				out = null;
				outMessages = null;
				s1 = null;
				s2 = null;
			}
			
			
				
				return procval;
			}
public boolean updateOnSave(PaymentMakerForCMVO paymentVO){
	
	boolean status=false;
	//mradul starts for payment TA flag
	String taStatus="";
	logger.info("In updateOnSave,,,,,");
	 if(CommonFunction.checkNull(paymentVO.getTaStatus()).equalsIgnoreCase("on"))
	 {
		 taStatus="Y";
		 paymentVO.setPaymentMode("S");
		 String instNo = "ADJ-P-";
		 instNo = instNo.concat(paymentVO.getInstrumentID());
		 paymentVO.setInstrumentNumber(instNo);
		 instNo = null;
	 }else{
		 taStatus="N";
	 }
	 ArrayList queryList=new ArrayList();
	 
	 String makerDate=null;
	 String date=null;
	 String insDate=null;
	 	ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();
		StringBuilder instrumentId = new StringBuilder();
		
	try{      

		in.add((CommonFunction.checkNull(paymentVO.getInstrumentID()).trim()));  // for UPDATE pass INSTRUMENT_ID
		in.add(CommonFunction.checkNull(paymentVO.getLbxLoanNoHID()).trim());  //TXNID
		in.add("");														   //TXN_TYPE
		in.add(CommonFunction.checkNull(paymentVO.getLbxBPType()).trim());	   //BPTYPE 
		in.add(CommonFunction.checkNull(paymentVO.getLbxBPNID()).trim());      //BPID
		in.add(CommonFunction.checkNull(paymentVO.getPaymentMode()).trim());   //INSTRUMENT_MODE
		
		date=CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getPaymentDate()).trim());
		in.add(date);															//RECEIVED_DATE
		
		in.add(CommonFunction.checkNull(paymentVO.getInstrumentNumber()).trim());  //INSTRUMENT_NO
		
		insDate=CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getInstrumentDate()).trim()); 
		in.add(insDate);														//INSTRUMENT_DATE
		in.add(CommonFunction.checkNull(paymentVO.getBankAccount()).trim());	//ISSUEING_BANK_ACCOUNT
		in.add(CommonFunction.checkNull(paymentVO.getLbxBankID()).trim());		//ISSUEING_BANK_ID
		in.add(CommonFunction.checkNull(paymentVO.getLbxBranchID()).trim());	//ISSUEING_BRANCH_ID
		in.add(CommonFunction.checkNull(paymentVO.getMicr()).trim());			//ISSUING_MICR_CODE
		in.add(CommonFunction.checkNull(paymentVO.getIfsCode()).trim());		//ISSUING_IFSC_CODE
		in.add(CommonFunction.checkNull(paymentVO.getBankAccount()).trim());	//DEPOSIT_BANK_ACCOUNT
		in.add(CommonFunction.checkNull(paymentVO.getLbxBankID()).trim());		//DEPOSIT_BANK_ID
		in.add(CommonFunction.checkNull(paymentVO.getLbxBranchID()).trim());	//DEPOSIT_BRANCH_ID
		in.add(CommonFunction.checkNull(paymentVO.getMicr()).trim());			//DEPOSIT_MICR_CODE
		in.add(CommonFunction.checkNull(paymentVO.getIfsCode()).trim());		//DEPOSIT_IFSC_CODE			
		if(CommonFunction.checkNull(paymentVO.getPaymentAmount()).trim().equalsIgnoreCase(""))
			in.add("0.00");
		else
			in.add(myFormatter.parse(CommonFunction.checkNull(paymentVO.getPaymentAmount()).trim()).toString()); //INSTRUMENT_AMOUNT
		
		if(CommonFunction.checkNull(paymentVO.getTdsAmount()).trim().equalsIgnoreCase(""))
			in.add("0.00");
		else 
			in.add(myFormatter.parse(CommonFunction.checkNull(paymentVO.getTdsAmount()).trim()).toString());  //TDS_AMOUNT

		in.add(CommonFunction.checkNull(paymentVO.getRemarks()).trim());			//MAKER_REMARKS
		in.add("");																	//REC_STATUS
		in.add("");																	//INSTRUMENT_TYPE
		in.add("");																	//PDC_FLAG
		in.add(CommonFunction.checkNull(paymentVO.getDefaultBranch()).trim());		//DEFAULT_BRANCH
		in.add(CommonFunction.checkNull(paymentVO.getMakerId()).trim());			// MAKER_ID
		
		makerDate=CommonFunction.changeFormat(CommonFunction.checkNull(paymentVO.getBusinessDate()).trim());
		in.add(makerDate);   //MAKER_DATE
		in.add(CommonFunction.checkNull(paymentVO.getLbxpayTo()).trim());		//PAY_TO
		in.add(CommonFunction.checkNull(paymentVO.getPayeeName()).trim());		//PAYEE_NAME
		in.add(CommonFunction.checkNull(taStatus).trim());
		
		if(!CommonFunction.checkNull(paymentVO.getLbxTaLoanNoHID()).trim().equalsIgnoreCase("")){
			in.add(paymentVO.getLbxTaLoanNoHID());
		}else{
			in.add(0);
		}
		
		in.add(CommonFunction.checkNull(paymentVO.getTaCustomerName()).trim());
		
		out.add(instrumentId.toString());
		out.add(s1.toString());
	    out.add(s2.toString());
	   
		
	     outMessages=(ArrayList) ConnectionDAO.callSP("SAVE_UPDATE_FORWARD_PAYMENT",in,out);
	    
	     instrumentId.append(CommonFunction.checkNull(outMessages.get(0)));
		 s1.append(CommonFunction.checkNull(outMessages.get(1)));
		 s2.append(CommonFunction.checkNull(outMessages.get(2)));
		 
	
        if(CommonFunction.checkNull(s1).equalsIgnoreCase("S")){
        	status=true;
        }
            
            
  
		 /*   	
	String query="update cr_instrument_dtl set BPTYPE='"+paymentVO.getBusinessPartnerType()+"',BPID='"+paymentVO.getLbxBPNID()+"',INSTRUMENT_MODE='"+paymentVO.getPaymentMode()+"',RECEIVED_DATE=STR_TO_DATE('"+paymentVO.getPaymentDate()+"','%d-%m-%Y'),INSTRUMENT_AMOUNT='"+paymentVO.getPaymentAmount()+"'," +
			" INSTRUMENT_DATE=STR_TO_DATE('"+paymentVO.getInstrumentDate()+"','%d-%m-%Y')," +
			" ISSUEING_BANK_ID='"+paymentVO.getLbxBankID()+"',ISSUEING_BRANCH_ID='"+paymentVO.getLbxBranchID()+"',ISSUING_MICR_CODE='"+paymentVO.getMicr()+"',ISSUING_IFSC_CODE='"+paymentVO.getIfsCode()+"'," +
			" REMARKS='"+paymentVO.getRemarks()+"',TDS_AMOUNT='"+paymentVO.getTdsAmount()+"' where TXNID='"+paymentVO.getLbxLoanNoHID()+"' and INSTRUMENT_NO='"+paymentVO.getInstrumentNumber()+"' and INSTRUMENT_ID='"+paymentVO.getInstrumentID()+"'";	
	
	queryList.add(query);
	logger.info("In saveAll update cr_instrument_dtl"+query);
	
	
	
	status =ConnectionDAO.sqlInsUpdDelete(queryList);
	logger.info("In updateOnSave,,,,,"+status);
	
	 */
	
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		taStatus=null;
	}

	return status;
}

public ArrayList<PaymentMakerForCMVO> getchequeDetail(String bankAccountId,String accountType) {
	

	ArrayList<PaymentMakerForCMVO> getChequeList=new ArrayList<PaymentMakerForCMVO>();
	StringBuilder query = new StringBuilder();
	try{
		ArrayList mainList=new ArrayList ();
		ArrayList subList =new ArrayList();
		query.append(" SELECT CBA.BANK_ACCOUNT,CBA.BANK_ID,CB.BANK_NAME,CBA.BANK_BRANCH_ID,CBB.BANK_BRANCH_NAME,"+
       "  CBA.BRANCH_MICR_CODE,CBA.BRANCH_IFCS_CODE FROM com_bank_m CB,com_bankbranch_m CBB,com_bank_accounts_m CBA "+
       " WHERE CB.BANK_ID=CBA.BANK_ID AND CBB.BANK_BRANCH_ID=CBA.BANK_BRANCH_ID AND CBA.REC_STATUS= 'A' AND CBA.BANK_ACCOUNT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankAccountId).trim())+"'"+
       " AND ACCOUNT_TYPE='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(accountType).trim())+"'");
		
		logger.info("In getListOfValues"+query);	
		
		mainList=ConnectionDAO.sqlSelect(query.toString());
		for(int i=0;i<mainList.size();i++)
		{
			subList= (ArrayList)mainList.get(i);
			if(subList.size()>0){
				PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
				paymentVO.setBankAccount((CommonFunction.checkNull(subList.get(0)).trim()));
				paymentVO.setLbxBankID((CommonFunction.checkNull(subList.get(1)).trim()));
				paymentVO.setBank((CommonFunction.checkNull(subList.get(2)).trim()));
				paymentVO.setLbxBranchID((CommonFunction.checkNull(subList.get(3)).trim()));
				paymentVO.setBranch((CommonFunction.checkNull(subList.get(4)).trim()));
				paymentVO.setMicr((CommonFunction.checkNull(subList.get(5)).trim()));
				paymentVO.setIfsCode((CommonFunction.checkNull(subList.get(6)).trim()));
				getChequeList.add(paymentVO);
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

public boolean deletePayment(String id) {
	
	boolean status=false;
	boolean status1=false;
	ArrayList list=new ArrayList();
	ArrayList list1=new ArrayList();
	StringBuilder query=new StringBuilder();
	StringBuilder query1=new StringBuilder();
	try{
	query.append ("delete from cr_instrument_dtl where instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
	list.add(query.toString());
	logger.info("delete()     query------------------" + query.toString());
	status =ConnectionDAO.sqlInsUpdDelete(list);
	
	query1.append ("delete from cr_pmnt_dtl where instrument_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"'");
	list1.add(query1.toString());
	logger.info("delete()     query1------------------" + query1.toString());
	status1 =ConnectionDAO.sqlInsUpdDelete(list1);
	}
  catch (Exception e) {
		e.printStackTrace();
	}finally{
		list.clear();
		list1.clear();
		query=null;
		query1=null;
	}
  return status;

}

public String saveAllForwardedDataWithoutCheck(PaymentMakerForCMVO paymentMakerForCMVO,String amount,String tdsAmount){
	
	ArrayList<PaymentMakerForCMVO> getDataList=new ArrayList<PaymentMakerForCMVO>();
	PrepStmtObject updatePrepStmtObject = new PrepStmtObject();	
	StringBuffer sBUpdQry =	new StringBuffer();
	boolean status=false;
	logger.info("In saveAllForwardedDataWithoutCheck,,,,,");
	//mradul starts for payment TA flag
	String taStatus="";	
	 if(CommonFunction.checkNull(paymentMakerForCMVO.getTaStatus()).equalsIgnoreCase("on"))
	 {
		 taStatus="Y";
		 paymentMakerForCMVO.setPaymentMode("S");
		 String instNo = "ADJ-P-";
		 instNo = instNo.concat(paymentMakerForCMVO.getInstrumentID());
		 paymentMakerForCMVO.setInstrumentNumber(instNo);
		 instNo = null;
	 }else{
		 taStatus="N";
	 }	
	 ArrayList queryList=new ArrayList();
	 ArrayList arrList=new ArrayList();			 		
	 String procval="";
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
		ArrayList tdsAmtAllocated=new ArrayList(); 			 
	 
	
		sBUpdQry.append(" update cr_instrument_dtl set " );
		sBUpdQry.append(" INSTRUMENT_MODE=?,RECEIVED_DATE=");
		sBUpdQry.append(dbo);
		sBUpdQry.append("STR_TO_DATE(?,'"+dateFormat+"'),INSTRUMENT_AMOUNT=?,INSTRUMENT_NO=?,INSTRUMENT_DATE=");
		sBUpdQry.append(dbo);
		sBUpdQry.append("STR_TO_DATE(?,'"+dateFormat+"'), ISSUEING_BANK_ACCOUNT=?,"  );	
		sBUpdQry.append(" ISSUEING_BANK_ID=?,ISSUEING_BRANCH_ID=?,ISSUING_MICR_CODE=?,ISSUING_IFSC_CODE=?, "  );
		sBUpdQry.append(" MAKER_REMARKS=?,TDS_AMOUNT=?, REC_STATUS='F',MAKER_ID=?,MAKER_DATE=");
		sBUpdQry.append(dbo);
		sBUpdQry.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ,TA_Adjustment_FLAG='"+taStatus+"',TA_LOAN_ID=?,TA_ACCOUNT_NAME=?");
		 
		sBUpdQry.append(" where TXNID=? AND INSTRUMENT_ID=? ");	
		
		  
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getPaymentMode()).trim()));
		
		
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getPaymentDate())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getPaymentDate()).trim()));
		
		
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addString("0.00");
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(paymentMakerForCMVO.getPaymentAmount()).trim())).toString());
		
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentNumber())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentNumber()).trim()));
		
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentDate())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentDate()).trim()));
		  
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getBankAccount())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getBankAccount()).trim()));
		
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getLbxBankID())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getLbxBankID()).trim()));
		
		
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getLbxBranchID())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getLbxBranchID()).trim()));
		
		
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getMicr())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getMicr()).trim()));
		
		
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getIfsCode())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getIfsCode()).trim()));

		  if((CommonFunction.checkNull(paymentMakerForCMVO.getRemarks())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getRemarks()).trim()));
		

		  if((CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addString("0.00");
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(paymentMakerForCMVO.getTdsAmount()).trim())).toString());
		
		  if(CommonFunction.checkNull(paymentMakerForCMVO.getMakerId()).trim().equalsIgnoreCase(""))
				 updatePrepStmtObject.addNull();
				else
				updatePrepStmtObject.addString(paymentMakerForCMVO.getMakerId().trim());
				
			 if(CommonFunction.checkNull(paymentMakerForCMVO.getBusinessDate()).trim().equalsIgnoreCase(""))
				 updatePrepStmtObject.addNull();
				else
				updatePrepStmtObject.addString(paymentMakerForCMVO.getBusinessDate().trim());
			 
			if((CommonFunction.checkNull(paymentMakerForCMVO.getLbxTaLoanNoHID())).trim().equalsIgnoreCase(""))
					updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getLbxTaLoanNoHID()).trim()));
				
				if((CommonFunction.checkNull(paymentMakerForCMVO.getTaCustomerName())).trim().equalsIgnoreCase(""))
					updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getTaCustomerName()).trim()));			  
		  
		  
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getLbxLoanNoHID()).trim()));
		
		 		  
		  if((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(paymentMakerForCMVO.getInstrumentID()).trim()));
		
		  updatePrepStmtObject.setSql(sBUpdQry.toString());
			
		  queryList.add(updatePrepStmtObject);
	    logger.info("IN saveAllForwardedData  cr_instrument_dtl query2222 ### "+updatePrepStmtObject.printQuery());
	
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
			
			//mradul ends
			logger.info("In saveAllForwardedData..........::"+status);
		/*	updatePrepStmtObject1=null;
			bufInsSql1=null;*/
			//mradul starts
			PrepStmtObject updatePrepStmtObject1 = new PrepStmtObject();	
			StringBuffer bufInsSql1 =	new StringBuffer();
			StringBuilder inventoryFlagInMst = new StringBuilder();
			StringBuilder existInStationary = new StringBuilder();
			String inventoryFlag="";
			String checkAllBranch="";
			String resultStationary="";
			StringBuilder checkUser = new StringBuilder();
			StringBuilder allBranch = new StringBuilder();
			String checkUserAvailable="";
			boolean updateStatus=false;
			String IssuingUser=paymentMakerForCMVO.getMakerId();
			logger.info("receipt default branch"+paymentMakerForCMVO.getDefaultBranch());


			inventoryFlagInMst.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CHEQUE_INVENTORY_FLAG'");
			logger.info("IN checkReciptStatus inventoryFlag ### "+inventoryFlagInMst.toString());
			inventoryFlag=ConnectionDAO.singleReturn(inventoryFlagInMst.toString());
				if(inventoryFlag.equalsIgnoreCase("Y"))
				  {
						allBranch.append(" SELECT COUNT(1) FROM cr_stationary_status_dtl WHERE ALL_BRANCH='Y' AND RECEPT_CHEQUE_NO='"+paymentMakerForCMVO.getInstrumentNumber()+"' AND STATUS='A'");
						logger.info("IN checkAllBranch ### "+allBranch.toString());
						checkAllBranch=ConnectionDAO.singleReturn(allBranch.toString());
					
				if(!(checkAllBranch.equalsIgnoreCase("0")))
					{
						bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U' where RECEPT_CHEQUE_NO='"+paymentMakerForCMVO.getInstrumentNumber()+"'");
						logger.info("IN updateStationary  ### "+bufInsSql1.toString() + paymentMakerForCMVO.getInstrumentNumber());
					    updatePrepStmtObject1.setSql(bufInsSql1.toString());
						queryList.add(updatePrepStmtObject1);
						status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
				}else{
						checkUser.append(" select COUNT(1) ISSUING_USER from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+paymentMakerForCMVO.getInstrumentNumber()+"' AND IFNULL(ISSUING_USER,'')<>''");
						logger.info("IN checkInventory inventoryFlag ### "+checkUser.toString());
						checkUserAvailable=ConnectionDAO.singleReturn(checkUser.toString());	
				
				if(!(checkUserAvailable.equalsIgnoreCase("0")))
					{
						existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+paymentMakerForCMVO.getInstrumentNumber()+"'  AND IFNULL(ISSUING_USER,'"+IssuingUser+"')='"+IssuingUser+"' ");
						logger.info("IN checkUserAvailability  in existInStationary ### "+existInStationary.toString());
						resultStationary=ConnectionDAO.singleReturn(existInStationary.toString());
				}else{
				 	existInStationary.append("SELECT COUNT(1)  from cr_stationary_status_dtl where status='A' AND RECEPT_CHEQUE_NO='"+paymentMakerForCMVO.getInstrumentNumber()+"'  AND ISSUING_BRANCH='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(paymentMakerForCMVO.getDefaultBranch()).trim())+"'");
						logger.info("IN checkReciptStatus  in existInStationary ### "+existInStationary.toString());
						resultStationary=ConnectionDAO.singleReturn(existInStationary.toString());
				}  			
				if(!(resultStationary.equalsIgnoreCase("0")))
				{
						 bufInsSql1.append("UPDATE cr_stationary_status_dtl SET STATUS='U' where RECEPT_CHEQUE_NO='"+paymentMakerForCMVO.getInstrumentNumber()+"'");
						 logger.info("IN updateStationary  ### "+bufInsSql1.toString() + paymentMakerForCMVO.getInstrumentNumber());
						 updatePrepStmtObject1.setSql(bufInsSql1.toString());
						 queryList.add(updatePrepStmtObject1);
						 status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);					  		 
				}
				else{
					status=false;				  			
				}
				
				logger.info("In saveForwardUpdateOnReceipt....................."+status);
				}}   
				else{
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
				
				//mradul ends
				logger.info("In saveAllForwardedData..........::"+status);
				updatePrepStmtObject1=null;
				bufInsSql1=null;
				
				}
	if(status){
		procval="queryexecuted";
		} //}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally
	{
		in = null;
		out = null;
		outMessages = null;
		s1 = null;
		s2 = null;
		getProcessammountQuery = null;
		getAllotedAmtQuery = null;
	}

	
	return procval;
}

public String allocatePayableCheckFlag() 
{
	String flag="";
	StringBuilder query = new StringBuilder();
	query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='ALLOCATE_PAYABLE_FLAG'");
	logger.info("Query for getting allocatePayableCheckFlag from parameter_mst  : "+query.toString());
	try
	{
		flag = ConnectionDAO.singleReturn(query.toString());
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		query=null;
	}
	return flag;
}

@Override
public String payableAmountCheck(Object ob) {
	
	PaymentMakerForCMVO vo =(PaymentMakerForCMVO)ob;
	String payableAmount="";
	String status="E";
	StringBuilder query = new StringBuilder();
	query.append("SELECT ");
	query.append("ISNULL(L.LOAN_LOAN_AMOUNT,0)-ISNULL(SUM(A.ADVICE_AMOUNT),0) FROM cr_loan_dtl L ");
	//query.append(" LEFT JOIN cr_txnadvice_dtl A ON A.LOAN_ID=L.LOAN_ID AND ADVICE_TYPE='P' AND CHARGE_CODE_ID<>103");
	query.append(" LEFT JOIN cr_txnadvice_dtl A ON A.LOAN_ID=L.LOAN_ID AND ADVICE_TYPE='P' AND CHARGE_CODE_ID IN(2,50) AND A.REC_STATUS='A' ");
	query.append(" WHERE  L.LOAN_ID='"+vo.getLbxLoanNoHID()+"' ");
	query.append(" GROUP BY L.LOAN_LOAN_AMOUNT,A.ADVICE_AMOUNT");
	logger.info("Query for getting payableAmountCheck  : "+query.toString());
	try
	{
		payableAmount = ConnectionDAO.singleReturn(query.toString());
	} catch (Exception e) {
		e.printStackTrace();
	}
	logger.info("payableAmount(L.LOAN_LOAN_AMOUNT-SUM(A.ADVICE_AMOUNT) and ADVICE_TYPE='P'  AND CHARGE_CODE_ID IN(2,50) AND L.REC_STATUS='A': "+payableAmount);
	 if(!(CommonFunction.checkNull(vo.getPaymentAmount())).trim().equalsIgnoreCase(""))
	 {

		try {
			
			Number num=myFormatter.parse((CommonFunction.checkNull(vo.getPaymentAmount()).trim()));
			logger.info("Payment Amount From Front: "+num.doubleValue());
			Double nemDouble=num.doubleValue();
			if(!payableAmount.equalsIgnoreCase(""))
			{
				Double payNum=Double.parseDouble(payableAmount);
				if(payNum.compareTo(nemDouble)>=0)
				{
					status="S";
				}
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			payableAmount=null;
			query=null;
		}
	
	 }
			
	return status;
}
public String cashPaymentLimit() 
{
	String amount=null;
	StringBuilder query = new StringBuilder();
	query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='CASH_PAYMENT_LIMIT'");
	logger.info("Query for getting cashPaymentLimit from parameter_mst  : "+query.toString());
	try
	{
		amount = ConnectionDAO.singleReturn(query.toString());
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		query=null;
	}
	return amount;
}
//Manish
@Override
public ArrayList getDataFromInstrumentPayment(String instrumentId,String loanID) {
	
	String dataFromInstrumentDtlQuery="select top 1 BPTYPE,BPID,INSTRUMENT_AMOUNT,TDS_AMOUNT from cr_instrument_dtl where INSTRUMENT_ID='"+instrumentId+"' AND TXN_TYPE='LIM' AND TXNID='"+loanID+"'";
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
public String getLoanRecStatusPayment(String loanID){
	StringBuilder query = new StringBuilder();
	 String loanRecStatus="";
		try{
			query.append("select rec_status from cr_loan_dtl where loan_id="+loanID+"");
			
			loanRecStatus=ConnectionDAO.singleReturn(query.toString());
	    }
	  	   catch(Exception e){
					e.printStackTrace();
				}finally{
					query=null;
				}
		return loanRecStatus;
}
@Override
public String getCheckDateCountPayment(String instrumentId){
	
	   String checkDateQuery="select count(*) from cr_instrument_dtl I " +
                             " inner join cr_pmnt_dtl P on I.INSTRUMENT_ID=P.INSTRUMENT_ID AND P.PMNT_DATE=I.RECEIVED_DATE " +
                             " where I.INSTRUMENT_ID='"+instrumentId+"' AND INSTRUMENT_TYPE='P' ";

      logger.info("checkDateQuery: "+checkDateQuery);

      String checkDateCount=ConnectionDAO.singleReturn(checkDateQuery);	
      checkDateQuery=null;
	  return checkDateCount;
}
@Override
public String getParamValuePayment(){
	
	  String paramQuery="select PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PAYMENT_AUTHORIZATION_FORWARD'";
	  String paramValue=ConnectionDAO.singleReturn(paramQuery);
	  logger.info("paramQuery: "+paramQuery+" paramValue:  "+paramValue);
	  paramValue=null;
	  return paramQuery;
}
//Code End by Manish
}