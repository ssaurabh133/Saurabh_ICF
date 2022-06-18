package com.cm.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.SDLiquidationDAO;
import com.cm.vo.LiquidationAuthorVO;
import com.cm.vo.LiquidationMakerVO;
import com.cm.vo.LiquidationSearchVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;


public class SDLiquidationDAOImpl implements SDLiquidationDAO{
	
	private static final Logger logger = Logger.getLogger(SDLiquidationDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	public ArrayList<LiquidationSearchVO> searchLiquidationData(LiquidationSearchVO vo, String type){
		logger.info("In SDLiquidationDAOImpl:::::::::::::::::::::::::::");
//	String loanId="";
//	String customerName="";
//	String userName="";
		StringBuilder loanId=new StringBuilder();
		StringBuilder customerName=new StringBuilder();
		StringBuilder userName=new StringBuilder();
		
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	ArrayList searchlist=new ArrayList();
	StringBuilder bufInsSql =	new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
	ArrayList data=null;
  	LiquidationSearchVO vo1= null;
    
	ArrayList<LiquidationSearchVO> detailList=new ArrayList();
	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
	try{
		String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
		userName.append(ConnectionDAO.singleReturn(userNameQ));
		logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		
		userNameQ=null;
		userName=null;
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
       try{
              logger.info("In searchLiquidationData():::::::::::::::::::::::::::::::::::::::::::Dao Impl");
              
              loanId.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxLoanNoHID()).trim()));
              customerName.append(CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()));
              boolean appendSQL=false;
              
              bufInsSql.append("select distinct a.loan_no, b.customer_name, a.loan_loan_amount, c.product_desc, d.scheme_desc,"+
				 "a.loan_id,e.sd_id,e.sd_liquidation_id, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=E.MAKER_ID) MAKER_ID from cr_loan_dtl a,gcd_customer_m b," +
				 " cr_product_m c, cr_scheme_m d, cr_sd_liquidation_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
				 " and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
              
              bufInsSqlTempCount.append("select count(1) from cr_loan_dtl a,gcd_customer_m b," +
     			" cr_product_m c, cr_scheme_m d, cr_sd_liquidation_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
     			" and d.scheme_id = a.loan_scheme and e.rec_status='"+type+"' and e.loan_id=a.loan_id AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
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
    	    	  bufInsSql.append(" AND a.Loan_Id in (select loan_id from cr_sd_liquidation_dtl where loan_id='"+loanId+"' and rec_status='"+type+"') AND b.customer_name like'%"+customerName+"%'");
    	    	  bufInsSqlTempCount.append(" AND a.Loan_Id in (select loan_id from cr_sd_liquidation_dtl where loan_id='"+loanId+"' and rec_status='"+type+"') AND b.customer_name like'%"+customerName+"%'");
    	      }
				if((!((loanId.toString()).equalsIgnoreCase(""))) || (!((customerName.toString()).equalsIgnoreCase("")))){
					appendSQL=true;
				}
				
				if(appendSQL){
					bufInsSql.append(" AND ");
					bufInsSqlTempCount.append(" AND ");

        	 if((!(loanId.toString().equalsIgnoreCase("")))) {
    	         bufInsSql.append(" a.Loan_Id in (select loan_id from cr_sd_liquidation_dtl where loan_id='"+loanId+"' and rec_status='"+type+"') AND");
    	         bufInsSqlTempCount.append(" a.Loan_Id in (select loan_id from cr_sd_liquidation_dtl where loan_id='"+loanId+"' and rec_status='"+type+"') AND");
    	    	 appendSQL=true;
    	    	  
    	      }
			if((!(customerName.toString().equalsIgnoreCase("")))) {
    	    	  bufInsSql.append(" b.customer_name like'%"+customerName+"%' AND");
    	    	  bufInsSqlTempCount.append(" b.customer_name like'%"+customerName+"%' AND");
    	    	  appendSQL=true;
    	      }
			}
				if((!(vo.getLbxUserId().equalsIgnoreCase(""))) && type.equalsIgnoreCase("F")) {
					bufInsSql.append(" AND E.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"' ");	
					bufInsSqlTempCount.append(" AND E.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"'");
					appendSQL = true;
			      }
			
			logger.info("In appendSQL true---- "+appendSQL);
			
			if(appendSQL){					
				String  tmp = bufInsSql.toString();
				String tmp1 = bufInsSqlTempCount.toString();
            logger.info("In searchLiquidationData() ## tmp ## "+tmp);
            logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
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
			  logger.info("search Query...else-------."+bufInsSql);
			  count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			  
				bufInsSqlTempCount =null;
			  
			  if((loanId==null && customerName==null) || (loanId.toString().equalsIgnoreCase("") && customerName.toString().equalsIgnoreCase("")) 
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
			  logger.info("query : "+bufInsSql);
			  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			}
			
						bufInsSql =null;
						
			int size = searchlist.size();
          logger.info("searchLiquidationData: "+size);
          for(int i=0;i<size;i++){
          logger.info("searchLiquidationDataList: "+searchlist.get(i).toString());
          data=(ArrayList)searchlist.get(i);
          if(data.size()>0){
        	  vo1= new LiquidationSearchVO();
        	  if(type.equalsIgnoreCase("P"))
        	  {
        		  vo1.setLoanNo("<a href=sdLiquidationSearch.do?method=openLiquidationValues&loanId="
  					+ (CommonFunction.checkNull(data.get(5)).trim())+ "&sdId=" 
  					+ (CommonFunction.checkNull(data.get(6)).trim())
  					+ "&sdLiquidId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
        	  }
        	  if(type.equalsIgnoreCase("F"))
        	  {
        		  vo1.setLoanNo("<a href=sdLiquidationSearch.do?method=openLiquidationValuesAuthor&loanId="
  					+ (CommonFunction.checkNull(data.get(5)).trim())+ "&sdId=" 
  					+ (CommonFunction.checkNull(data.get(6)).trim())
  					+ "&sdLiquidId="+(CommonFunction.checkNull(data.get(7)).trim())+">"
  					+ (CommonFunction.checkNull(data.get(0)).trim()) + "</a>");
        	  }
        	  vo1.setCustomerName((CommonFunction.checkNull(data.get(1)).trim()));
        	  vo1.setLoanAmount((CommonFunction.checkNull(data.get(2)).trim()));
        	  vo1.setProduct((CommonFunction.checkNull(data.get(3)).trim()));
        	  vo1.setScheme((CommonFunction.checkNull(data.get(4)).trim()));
        	  vo1.setLbxLoanNoHID((CommonFunction.checkNull(data.get(5)).trim()));
        	  vo1.setSDId((CommonFunction.checkNull(data.get(6)).trim()));
        	  vo1.setSDLiquidationId((CommonFunction.checkNull(data.get(7)).trim()));
        	  vo1.setReportingToUserId((CommonFunction.checkNull(data.get(8)).trim()));
        	  vo1.setTotalRecordSize(count);
        	  
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
		//userName=null;
		searchlist=null;
		
	
		data=null;
	  	vo1= null;
	}
	return detailList;
}

	
	public boolean updateLiquidationData(LiquidationMakerVO vo,String type){
		logger.info("In SDLiquidationDAOImpl::::::::::::::::::::::::::::");
	PrepStmtObject insPrepStmtObject = new PrepStmtObject();
	ArrayList qryList = new ArrayList();
	boolean status = false;
//	String gapInt="";
//	String gapTds="";
	
	StringBuilder gapInt=new StringBuilder();
	StringBuilder gapTds=new StringBuilder();
	
	StringBuilder query = new StringBuilder();
	try {
		if(StringEscapeUtils.escapeSql(vo.getLiquidationFlag()).equalsIgnoreCase("P"))
		{
			if(vo.getGapInterest().trim().equalsIgnoreCase(""))
				gapInt.append("0.00");
			else
				gapInt.append(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGapInterest()).trim())).toString());
			
			if(vo.getGapTDS().trim().equalsIgnoreCase(""))
				gapTds.append("0.00");
			else
				gapTds.append(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGapTDS()).trim())).toString());
		}
		else if(StringEscapeUtils.escapeSql(vo.getLiquidationFlag()).equalsIgnoreCase("F"))
		{
			if(vo.getGapInterestF().trim().equalsIgnoreCase(""))
				gapInt.append("0.00");
			else
				gapInt.append(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGapInterestF()).trim())).toString());
			
			if(vo.getGapTDSF().trim().equalsIgnoreCase(""))
				gapTds.append("0.00");
			else
				gapTds.append(myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGapTDSF()).trim())).toString());
		}
		query.append("update cr_sd_liquidation_dtl set LIQUIDATION_AMOUNT=?, LIQUIDATION_INTEREST=?, LIQUIDATION_DATE=STR_TO_DATE(?,'"+dateFormat+"'), " +
				" GAP_INTEREST=?, GAP_TDS=?, LIQUIDATION_FLAG=?, MAKER_REMARKS=?, REC_STATUS=?, MAKER_ID=?, " +
				" MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where loan_id=? and sd_id=? " +
				" and sd_liquidation_id=?");
		
		if((CommonFunction.checkNull(vo.getLiquidationAmountPrincipal())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getLiquidationAmountPrincipal()).trim())).toString());
		
		if((CommonFunction.checkNull(vo.getLiquidationAmountInterest())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getLiquidationAmountInterest()).trim())).toString());
		
		if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
		
		if((CommonFunction.checkNull(gapInt)).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(gapInt.toString());
		
		if((CommonFunction.checkNull(gapTds)).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(gapTds.toString());
		
		if((CommonFunction.checkNull(vo.getLiquidationFlag())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getLiquidationFlag()).trim()));
		
		if((CommonFunction.checkNull(vo.getRemarks())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getRemarks()).trim()));
		
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
		
		if((CommonFunction.checkNull(vo.getSdNo())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getSdNo()).trim()));
		
		if((CommonFunction.checkNull(vo.getSdLiquidationId())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getSdLiquidationId()).trim()));
		
		insPrepStmtObject.setSql(query.toString());
		logger.info("Update Query: "+insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		
		query = null;
		
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		insPrepStmtObject = null;
		qryList = null;
		gapInt= null;
		gapTds= null;
		
	}
	return status;
}

	public boolean deleteLiquidationData(LiquidationMakerVO vo){
		logger.info("In deleteLiquidationData::::::::::::::::::::::::::::");
	PrepStmtObject insPrepStmtObject = new PrepStmtObject();
	ArrayList qryList = new ArrayList();
	boolean status = false;
	
	StringBuilder query = new StringBuilder();
	try {
		
		query.append("delete from cr_sd_liquidation_dtl where loan_id=? and sd_id=? and sd_liquidation_id=?");
		
		if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
		
		if((CommonFunction.checkNull(vo.getSdNo())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getSdNo()).trim()));
		
		if((CommonFunction.checkNull(vo.getSdLiquidationId())).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString((CommonFunction.checkNull(vo.getSdLiquidationId()).trim()));
		
		insPrepStmtObject.setSql(query.toString());
		logger.info("Delete Query: "+insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		
		query = null;
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	
	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}

	public String getMakerDate(String loanId) 
	{
		String makeDate="";
		StringBuilder query = new StringBuilder();
		query.append("select date_format(MAKER_DATE,'%d-%m-%Y') from cr_sd_liquidation_dtl where LOAN_ID="+loanId.trim());
		logger.info("Query for getting Maker Date of early closure  :  "+query.toString());
		try
		{
			makeDate = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return makeDate;
	}


	public String earlyClosureFlag() 
	{
		String flag="";
		StringBuilder query = new StringBuilder();
		query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='MAKER_AUTHOR_DATE_CHECK_FLAG'");
		logger.info("Query for getting EARLY_CLOSURE_DATE_FLAG from parameter_mst  : "+query.toString());
		try
		{
			flag = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public ArrayList<LiquidationMakerVO> selectLiquidationData(String loanId, String sdId, String sdLiquidationId, String type)
	{
	logger.info("In selectLiquidationData.....SDLiquidationDAOImpl");
	ArrayList<LiquidationMakerVO> liquidationData = new ArrayList<LiquidationMakerVO>();
	LiquidationMakerVO vo = null;
	ArrayList mainlist=new ArrayList();
	ArrayList subList=new ArrayList();
	StringBuilder query= new StringBuilder();
	try{
		  query.append("select a.loan_id,a.loan_no, b.customer_name, a.loan_loan_amount, c.product_desc, " +
		  	 " d.scheme_desc,a.rec_status,a.disbursal_status, e.sd_id, e.sd_amount, e.SD_INTEREST_TYPE, e.SD_INTEREST_RATE, " +
		  	 " e.SD_COMPOUNDING_FREQ,Date_format(e.SD_START_DATE,'"+dateFormat+"'), Date_format(e.SD_MATURITY_DATE,'"+dateFormat+"'), " +
		  	 " e.sd_total_interest, Date_format(e.SD_INTEREST_ACRRUED_DATE,'"+dateFormat+"'), e.TDS_RATE," +
		  	 " e.TDS_DEDUCTED, (select IFNULL(sum(liquidation_amount),0) from cr_sd_liquidation_dtl where rec_STATUS='A' and loan_id="+loanId+" and sd_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sdId).trim())+") as liquidation_amount," +
		  	 " (select IFNULL(sum(liquidation_interest),0) from cr_sd_liquidation_dtl where rec_STATUS='A' and loan_id="+loanId+" and sd_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sdId).trim())+")as liquidation_interest," +
		  	 " Date_format(a.loan_approval_date,'"+dateFormat+"'), f.liquidation_amount,f.liquidation_interest," +
		  	 " f.gap_interest, f.gap_tds,f.liquidation_flag, f.remarks,f.MAKER_REMARKS,sum(IFNULL(e.sd_total_interest,0)+IFNULL(e.SD_INTEREST_ACCURED,0)) as finalInterest "+
			 " from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, cr_sd_dtl e, cr_sd_liquidation_dtl f " +
			 " where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"+
			 " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"" +
			 " and e.sd_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sdId).trim())+" " +
			 " and f.rec_status='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(type).trim())+"'" +
			 " and f.sd_liquidation_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sdLiquidationId).trim())+"'");

		 logger.info("In selectLiquidationData in edit case ::::: "+query.toString());
		 mainlist=ConnectionDAO.sqlSelect(query.toString());
		 
			query= null;
		 
		 int size = mainlist.size();
		 logger.info("In selectLiquidationData.....mainlist size: "+size);
		if(size!=0)
		{
		 for(int i=0;i<size;i++){
			subList=(ArrayList)mainlist.get(i);
			logger.info("In selectLiquidationData......sublist size: "+subList.size());
			if(subList.size()>0){
				vo = new LiquidationMakerVO();
				vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(0)).trim()));
				vo.setLoanNo((CommonFunction.checkNull(subList.get(1)).trim()));
				vo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
				if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
					vo.setLoanAmt("0.00");
				else
				{
					Number loanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());
					vo.setLoanAmt(myFormatter.format(loanAmt));
				}
				vo.setProduct((CommonFunction.checkNull(subList.get(4)).trim()));
				vo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
				if((CommonFunction.checkNull(subList.get(6))).equalsIgnoreCase("A"))
				{
					vo.setLoanStatus("Approved");
				}
				else if((CommonFunction.checkNull(subList.get(6))).equalsIgnoreCase("C"))
				{
					vo.setLoanStatus("Closed");
				}
				if((CommonFunction.checkNull(subList.get(7))).equalsIgnoreCase("N"))
				{
					vo.setDisbursalStatus("Not Disbursed Yet");
				}
				if((CommonFunction.checkNull(subList.get(7))).equalsIgnoreCase("P"))
				{
					vo.setDisbursalStatus("Partial");
				}
				else if((CommonFunction.checkNull(subList.get(7))).equalsIgnoreCase("F"))
				{
					vo.setDisbursalStatus("Final");
				}
				vo.setSdNo((CommonFunction.checkNull(subList.get(8)).trim()));
				if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase(""))
					vo.setSdAmount("0.00");
				else
				{
					Number sdAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(9))).trim());
					vo.setSdAmount(myFormatter.format(sdAmount));
				}
				if((CommonFunction.checkNull(subList.get(10))).equalsIgnoreCase("N"))
				{
					vo.setSdInterestType("No Interest");
				}
				else if((CommonFunction.checkNull(subList.get(10))).equalsIgnoreCase("S"))
				{
					vo.setSdInterestType("Simple Interest");
				}
				else if((CommonFunction.checkNull(subList.get(10))).equalsIgnoreCase("C"))
				{
					vo.setSdInterestType("Compound Interest");
				}
				if((CommonFunction.checkNull(subList.get(11)).trim()).equalsIgnoreCase(""))
					vo.setSdInterestRate("0.00");
				else
				{
					Number interestRate = myFormatter.parse((CommonFunction.checkNull(subList.get(11))).trim());
					vo.setSdInterestRate(myFormatter.format(interestRate));
				}
				if((CommonFunction.checkNull(subList.get(12))).equalsIgnoreCase("M"))
				{
					vo.setSdCompoundingFreq("Monthly");
				}
				else if((CommonFunction.checkNull(subList.get(12))).equalsIgnoreCase("B"))
				{
					vo.setSdCompoundingFreq("Bimonthly");
				}
				else if((CommonFunction.checkNull(subList.get(12))).equalsIgnoreCase("Q"))
				{
					vo.setSdCompoundingFreq("Quarterly");
				}
				else if((CommonFunction.checkNull(subList.get(12))).equalsIgnoreCase("H"))
				{
					vo.setSdCompoundingFreq("Half Yearly");
				}
				else if((CommonFunction.checkNull(subList.get(12))).equalsIgnoreCase("A"))
				{
					vo.setSdCompoundingFreq("Annually");
				}
				vo.setSdStartDate((CommonFunction.checkNull(subList.get(13)).trim()));
				vo.setSdMaturityDate((CommonFunction.checkNull(subList.get(14)).trim()));
				if((CommonFunction.checkNull(subList.get(15)).trim()).equalsIgnoreCase(""))
					vo.setSdInterestAccrued("0.00");
				else
				{
					Number SDInterestAccured = myFormatter.parse((CommonFunction.checkNull(subList.get(15))).trim());
					vo.setSdInterestAccrued(myFormatter.format(SDInterestAccured));
				}
				vo.setSdInterestAccruedDate((CommonFunction.checkNull(subList.get(16)).trim()));
				if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase(""))
					vo.setSdTDSRate("0.00");
				else
				{
					Number sdTDSRate = myFormatter.parse((CommonFunction.checkNull(subList.get(17))).trim());
					vo.setSdTDSRate(myFormatter.format(sdTDSRate));
				}
				if((CommonFunction.checkNull(subList.get(18)).trim()).equalsIgnoreCase(""))
					vo.setSdTDSDeducted("0.00");
				else
				{
					Number SDTDSDeducted = myFormatter.parse((CommonFunction.checkNull(subList.get(18))).trim());
					vo.setSdTDSDeducted(myFormatter.format(SDTDSDeducted));
				}
				if((CommonFunction.checkNull(subList.get(19)).trim()).equalsIgnoreCase(""))
					vo.setLiquidatedAmountPrincipal("0.00");
				else
				{
					Number liquidatedAmountPrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(19))).trim());
					vo.setLiquidatedAmountPrincipal(myFormatter.format(liquidatedAmountPrincipal));
				}
				if((CommonFunction.checkNull(subList.get(20)).trim()).equalsIgnoreCase(""))
					vo.setLiquidatedAmountInterest("0.00");
				else
				{
					Number liquidatedAmountInterest = myFormatter.parse((CommonFunction.checkNull(subList.get(20))).trim());
					vo.setLiquidatedAmountInterest(myFormatter.format(liquidatedAmountInterest));
				}
				vo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(21)).trim()));
				if((CommonFunction.checkNull(subList.get(22)).trim()).equalsIgnoreCase(""))
					vo.setLiquidationAmountPrincipal("0.00");
				else
				{
					Number liquidationAmountPrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(22))).trim());
					vo.setLiquidationAmountPrincipal(myFormatter.format(liquidationAmountPrincipal));
				}
				if((CommonFunction.checkNull(subList.get(23)).trim()).equalsIgnoreCase(""))
					vo.setLiquidationAmountInterest("0.00");
				else
				{
					Number liquidationAmountInterest = myFormatter.parse((CommonFunction.checkNull(subList.get(23))).trim());
					vo.setLiquidationAmountInterest(myFormatter.format(liquidationAmountInterest));
				}
				if((CommonFunction.checkNull(subList.get(24)).trim()).equalsIgnoreCase(""))
					vo.setGapInterest("0.00");
				else
				{
					Number gapInterest = myFormatter.parse((CommonFunction.checkNull(subList.get(24))).trim());
					vo.setGapInterest(myFormatter.format(gapInterest));
				}
				if((CommonFunction.checkNull(subList.get(25)).trim()).equalsIgnoreCase(""))
					vo.setGapTDS("0.00");
				else
				{
					Number gapTDS = myFormatter.parse((CommonFunction.checkNull(subList.get(25))).trim());
					vo.setGapTDS(myFormatter.format(gapTDS));
				}
				vo.setLiquidationFlag((CommonFunction.checkNull(subList.get(26))).trim());
				vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(27)).trim()));
				vo.setRemarks((CommonFunction.checkNull(subList.get(28)).trim()));
				if((CommonFunction.checkNull(subList.get(29)).trim()).equalsIgnoreCase(""))
					vo.setSdFinalInterest("0.00");
				else
				{
					Number sdFinalInterest = myFormatter.parse((CommonFunction.checkNull(subList.get(29))).trim());
					vo.setSdFinalInterest(myFormatter.format(sdFinalInterest));
				}
				liquidationData.add(vo);
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
	
	}
	return liquidationData;
}

	public String saveLiquidationData(LiquidationMakerVO vo)
	{
	logger.info("saveLiquidationData ........SDLiquidationDAOImpl");
//	CallableStatement cst=null;
//    Connection con=ConnectionDAO.getConnection();
    String sdLiquidationId="";
    double sdAmount = 0.0;
    double sdInterest = 0.0;
    double gapInt = 0.0;
    double gapTds = 0.0;
    ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
//	String s3="";
//    String s1= "";
//    String s2 ="";
	
	StringBuilder s3=new StringBuilder();
    StringBuilder s1= new StringBuilder();
    StringBuilder s2 =new StringBuilder();
    
	try
	{
//		con.setAutoCommit(false);
		if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLiquidationAmountPrincipal()).trim()).equalsIgnoreCase(""))
			sdAmount=0.0;
		else
			sdAmount=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLiquidationAmountPrincipal()).trim())).doubleValue();
		if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLiquidationAmountInterest()).trim()).equalsIgnoreCase(""))
			sdInterest=0.0;
		else
			sdInterest=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLiquidationAmountInterest()).trim())).doubleValue();
		if(StringEscapeUtils.escapeSql(vo.getLiquidationFlag()).equalsIgnoreCase("P"))
		{
			if(!(vo.getGapInterest().trim().equalsIgnoreCase("")))
				gapInt = myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGapInterest()).trim())).doubleValue();
			
			if(!(vo.getGapTDS().trim().equalsIgnoreCase("")))
				gapTds = myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGapTDS()).trim())).doubleValue();
		}
		else if(StringEscapeUtils.escapeSql(vo.getLiquidationFlag()).equalsIgnoreCase("F"))
		{
			if(!(vo.getGapInterestF().trim().equalsIgnoreCase("")))
				gapInt = myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGapInterestF()).trim())).doubleValue();
			
			if(!(vo.getGapTDSF().trim().equalsIgnoreCase("")))
				gapTds = myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGapTDSF()).trim())).doubleValue();
		}

		  logger.info("In SD_Liquidation_Dtl Procedure ");
//		cst=con.prepareCall("call SD_Liquidation_Dtl(?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?)");
		 in.add(Integer.parseInt(CommonFunction.checkNull(vo.getSdNo()).trim()));
		 in.add(Integer.parseInt(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
		 in.add(sdAmount);
		 in.add(sdInterest);
		 String date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
  	    if(date1 != null)
  	    	in.add(date1);
//		 in.add((CommonFunction.checkNull(vo.getMakerDate()).trim()));
		 in.add(gapInt);
		 in.add(gapTds);
		 in.add((CommonFunction.checkNull(vo.getLiquidationFlag()).trim()));
		 in.add((CommonFunction.checkNull(vo.getRemarks()).trim()));
		 in.add((CommonFunction.checkNull("P").trim()));
		 in.add((CommonFunction.checkNull(vo.getMakerId()).trim()));
		 String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
  	    if(date != null)
  	    	in.add(date);
//		 in.add((CommonFunction.checkNull(vo.getMakerDate()).trim()));
  	    out.add(s3);
  	    out.add(s1);
 	    out.add(s2);
 	   
 	    logger.info("SD_Liquidation_Dtl procedure is going to call in  : "+in.toString()+"   out : "+out.toString());
 	    outMessages=(ArrayList) ConnectionDAO.callSP("SD_Liquidation_Dtl",in,out);
	    s1.append(CommonFunction.checkNull(outMessages.get(1)));
	    s2.append(CommonFunction.checkNull(outMessages.get(2)));
	    s3.append(CommonFunction.checkNull(outMessages.get(0)));

        logger.info("s1: "+s1);
        logger.info("s2: "+s2);
        logger.info("sdLiquidationId: "+s3);
        sdLiquidationId = s3.toString();
     
	}
	catch (Exception e) {
		
		e.printStackTrace();
	}
	finally
	{
		
	    s1=null;
	    s2=null;
	    s3=null;
	}
	return sdLiquidationId;
}


	
	public String saveLiquidationAuthor(LiquidationAuthorVO vo)
	{
	logger.info("Inside saveLiquidationAuthor.......SDLiquidationDaoImpl");
	String status="";
//	CallableStatement cst=null;
//    Connection con=ConnectionDAO.getConnection();
    ArrayList qryList = new ArrayList();
//    String s1= "";
//    String s2 ="";
    
    StringBuilder s1=new StringBuilder();
    StringBuilder s2 =new StringBuilder();
    
    ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	try
	{

//		con.setAutoCommit(false);

		  logger.info("In SD_Liquidation_Author Procedure ");
//        cst=con.prepareCall("call SD_Liquidation_Author(?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?)");
		    in.add(Integer.parseInt(CommonFunction.checkNull(vo.getCompanyId()).trim()));
		    in.add(Integer.parseInt(CommonFunction.checkNull(vo.getSdLiquidationId()).trim()));
		    in.add(CommonFunction.checkNull(vo.getDecision()).trim());
		    in.add(CommonFunction.checkNull(vo.getComments()).trim());
		    String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAutrhorDate()).trim());
     	    if(date != null)
     	    	in.add(date);
//		    in.add(CommonFunction.checkNull(vo.getAutrhorDate()).trim());
		    in.add(CommonFunction.checkNull(vo.getAuthorId()).trim());
		    out.add(s1);
    	    out.add(s2);
        outMessages=(ArrayList) ConnectionDAO.callSP("SD_Liquidation_Author",in,out);
	    s1.append(CommonFunction.checkNull(outMessages.get(0)));
	    s2.append(CommonFunction.checkNull(outMessages.get(1)));

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
	
		e.printStackTrace();
	}
	finally
	{
		
	    qryList=null;
	    s1=null;
	    s2=null;
	}
return status;
}
	public ArrayList<LiquidationMakerVO> getLoanForLiquidationValues(String lbxLoanNoHID)
	{
		logger.info("Inside getLoanForLiquidationValues ..........CMDAOImpl");
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		LiquidationMakerVO vo = null;
		
	ArrayList<LiquidationMakerVO> loanDetailList = new ArrayList<LiquidationMakerVO>();
	try {

		query.append("select c.product_desc, d.scheme_desc, a.loan_loan_amount," +
				  " Date_format(a.loan_approval_date,'"+dateFormat+"'),a.rec_status,a.disbursal_status from cr_loan_dtl a,gcd_customer_m b,"
				+ " cr_product_m c, cr_scheme_m d where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"
				+ " and d.scheme_id = a.loan_scheme and a.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim()) + "'");

		logger.info("In getLoanForLiquidationValues : " + query.toString());
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		
		query = null;
		
		int size = mainlist.size();
		logger.info("In getLoanForLiquidationValues.....mainlist size: "
				+ size);
		for (int i = 0; i < size; i++) {

			subList = (ArrayList) mainlist.get(i);
			logger.info("In getLoanForLiquidationValues......sublist size: "
					+ subList.size());
			if (subList.size() > 0) {
				vo = new LiquidationMakerVO();
				vo.setProduct((CommonFunction.checkNull(subList.get(0)).trim()));
				vo.setScheme((CommonFunction.checkNull(subList.get(1)).trim()));
				if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
					vo.setLoanAmt("0.00");
				else
				{
					Number loanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
					vo.setLoanAmt(myFormatter.format(loanAmt));
				}
				vo.setLoanApprovalDate((CommonFunction.checkNull(subList.get(3)).trim()));
				if((CommonFunction.checkNull(subList.get(4))).equalsIgnoreCase("A"))
				{
					vo.setLoanStatus("Approved");
				}
				else if((CommonFunction.checkNull(subList.get(4))).equalsIgnoreCase("C"))
				{
					vo.setLoanStatus("Closed");
				}
				if((CommonFunction.checkNull(subList.get(5))).equalsIgnoreCase("N"))
				{
					vo.setDisbursalStatus("Not Disbursed Yet");
				}
				if((CommonFunction.checkNull(subList.get(5))).equalsIgnoreCase("P"))
				{
					vo.setDisbursalStatus("Partial");
				}
				else if((CommonFunction.checkNull(subList.get(5))).equalsIgnoreCase("F"))
				{
					vo.setDisbursalStatus("Final");
				}
				else if((CommonFunction.checkNull(subList.get(5))).equalsIgnoreCase("N"))
				{
					vo.setDisbursalStatus("Not Disbursed Yet");
				}
				loanDetailList.add(vo);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		mainlist = null;
		subList = null;
		
		vo = null;
	}
	return loanDetailList;

	}
	public ArrayList<LiquidationMakerVO> getLiquidationValues(String lbxLoanNoHID, String lbxSdNoHid)
	{
		logger.info("Inside getLiquidationValues ..........CMDAOImpl");
	ArrayList<LiquidationMakerVO> liquidationData = new ArrayList<LiquidationMakerVO>();
	
	StringBuilder queryCheck=new StringBuilder();
	 queryCheck.append("Select count(sd_id) from cr_sd_liquidation_dtl where sd_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxSdNoHid).trim())+"'" +
			" and rec_status in ('P','F')" +
			" and loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'");
	String tempCount = ConnectionDAO.singleReturn(queryCheck.toString());
	int count = Integer.parseInt(tempCount);
	
	queryCheck= null;
	
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder query = new StringBuilder();
	LiquidationMakerVO vo = null;
	if(count==0)
	{
		try {	
			query.append("select distinct e.sd_amount, e.SD_INTEREST_TYPE, e.SD_INTEREST_RATE, " +
				  	 " e.SD_COMPOUNDING_FREQ,Date_format(e.SD_START_DATE,'"+dateFormat+"'), Date_format(e.SD_MATURITY_DATE,'"+dateFormat+"'), " +
				  	 " e.sd_total_interest , Date_format(e.SD_INTEREST_ACRRUED_DATE,'"+dateFormat+"'), e.TDS_RATE," +
				  	 " e.TDS_DEDUCTED, (select IFNULL(sum(liquidation_amount),0) from cr_sd_liquidation_dtl f where f.rec_STATUS='A' and f.loan_id="+lbxLoanNoHID+" and f.sd_id="+lbxSdNoHid+") as LIQUIDATION_AMOUNT," +
				  	 " (select IFNULL(sum(liquidation_interest),0) from cr_sd_liquidation_dtl f where f.rec_STATUS='A'and f.loan_id="+lbxLoanNoHID+" and f.sd_id="+lbxSdNoHid+") as LIQUIDATION_INTEREST ," +
					 " sum(IFNULL(e.sd_total_interest,0)+IFNULL(e.SD_INTEREST_ACCURED,0)) as finalInterest from cr_sd_dtl e" +
					 " where e.loan_id="+lbxLoanNoHID+" and e.sd_id="+lbxSdNoHid+" ");
		
			logger.info("In getLiquidationValues : " + query.toString());
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size =  mainlist.size();
			logger.info("In getLiquidationValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {
		
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					vo = new LiquidationMakerVO();
					if((CommonFunction.checkNull(subList.get(0)).trim()).equalsIgnoreCase(""))
						vo.setSdAmount("0.00");
					else
					{
						Number sdAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(0))).trim());
						vo.setSdAmount(myFormatter.format(sdAmount));
					}
					if((CommonFunction.checkNull(subList.get(1))).equalsIgnoreCase("N"))
					{
						vo.setSdInterestType("No Interest");
					}
					else if((CommonFunction.checkNull(subList.get(1))).equalsIgnoreCase("S"))
					{
						vo.setSdInterestType("Simple Interest");
					}
					else if((CommonFunction.checkNull(subList.get(1))).equalsIgnoreCase("C"))
					{
						vo.setSdInterestType("Compound Interest");
					}
					if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
						vo.setSdInterestRate("0.00");
					else
					{
						Number interestRate = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
						vo.setSdInterestRate(myFormatter.format(interestRate));
					}
					if((CommonFunction.checkNull(subList.get(3))).equalsIgnoreCase("Y"))
					{
						vo.setSdCompoundingFreq("Yearly");
					}
					if((CommonFunction.checkNull(subList.get(3))).equalsIgnoreCase("M"))
					{
						vo.setSdCompoundingFreq("Monthly");
					}
					else if((CommonFunction.checkNull(subList.get(3))).equalsIgnoreCase("B"))
					{
						vo.setSdCompoundingFreq("Bimonthly");
					}
					else if((CommonFunction.checkNull(subList.get(3))).equalsIgnoreCase("Q"))
					{
						vo.setSdCompoundingFreq("Quarterly");
					}
					else if((CommonFunction.checkNull(subList.get(3))).equalsIgnoreCase("H"))
					{
						vo.setSdCompoundingFreq("Half Yearly");
					}
					else if((CommonFunction.checkNull(subList.get(3))).equalsIgnoreCase("A"))
					{
						vo.setSdCompoundingFreq("Annually");
					}
					vo.setSdStartDate((CommonFunction.checkNull(subList.get(4)).trim()));
					vo.setSdMaturityDate((CommonFunction.checkNull(subList.get(5)).trim()));
					if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
						vo.setSdInterestAccrued("0.00");
					else
					{
						Number SDInterestAccured = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
						logger.info("SD Interest Accured: "+SDInterestAccured);
						vo.setSdInterestAccrued(myFormatter.format(SDInterestAccured));
					}
					vo.setSdInterestAccruedDate((CommonFunction.checkNull(subList.get(7)).trim()));
					if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
						vo.setSdTDSRate("0.00");
					else
					{
						Number sdTDSRate = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
						vo.setSdTDSRate(myFormatter.format(sdTDSRate));
					}
					if((CommonFunction.checkNull(subList.get(9)).trim()).equalsIgnoreCase(""))
						vo.setSdTDSDeducted("0.00");
					else
					{
						Number SDTDSDeducted = myFormatter.parse((CommonFunction.checkNull(subList.get(9))).trim());
						vo.setSdTDSDeducted(myFormatter.format(SDTDSDeducted));
					}
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase(""))
						vo.setLiquidatedAmountPrincipal("0.00");
					else
					{
						Number liquidatedAmountPrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());
						vo.setLiquidatedAmountPrincipal(myFormatter.format(liquidatedAmountPrincipal));
					}
					if((CommonFunction.checkNull(subList.get(11)).trim()).equalsIgnoreCase(""))
						vo.setLiquidatedAmountInterest("0.00");
					else
					{
						Number liquidatedAmountInterest = myFormatter.parse((CommonFunction.checkNull(subList.get(11))).trim());
						vo.setLiquidatedAmountInterest(myFormatter.format(liquidatedAmountInterest));
					}
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase(""))
						vo.setSdFinalInterest("0.00");
					else
					{
						Number sdFinalInterest = myFormatter.parse((CommonFunction.checkNull(subList.get(12))).trim());
						vo.setSdFinalInterest(myFormatter.format(sdFinalInterest));
					}
					liquidationData.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			tempCount = null;
			mainlist = null;
			subList = null;
		
			
		}
	}
	else if(count>0)
	{
		logger.info("Count is more than 1");
	}

	return liquidationData;

	}
	public ArrayList<LiquidationMakerVO> generateSDAccrualValues(String businessDate,int companyId, String userId, String loanId,String sdNo)
	{
		logger.info("generateSDAccrualValues AJAX........DAOImpl");
	    String status="";	    
	    StringBuilder s1=new StringBuilder();
	    StringBuilder s2 =new StringBuilder();
	    StringBuilder s3=new StringBuilder();
	    StringBuilder s4 =new StringBuilder();	    
	    ArrayList<LiquidationMakerVO> qryList = new ArrayList();
	    ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		try
		{
			 in.add(Integer.parseInt(CommonFunction.checkNull(companyId).trim()));
			 in.add(0);
			 in.add(Integer.parseInt(CommonFunction.checkNull(loanId).trim()));
			 sdNo=CommonFunction.checkNull(sdNo).trim();
			 if(CommonFunction.checkNull(sdNo).trim().equalsIgnoreCase(""))
				 sdNo="0";
			 in.add(Integer.parseInt(sdNo));
			 String date=CommonFunction.changeFormat(CommonFunction.checkNull(businessDate).trim());
	  	    in.add(date);
	  	    in.add((CommonFunction.checkNull(userId).trim()));
			in.add((CommonFunction.checkNull("SDM").trim()));
			out.add(s1);
	 	    out.add(s2);
	 	    out.add(s3);
	 	    out.add(s4);
	 	    logger.info("Generate_SD_Accrual ("+in.toString()+","+out.toString()+")");
	        outMessages=(ArrayList) ConnectionDAO.callSP("Generate_SD_Accrual",in,out);
	        s3.append(CommonFunction.checkNull(outMessages.get(0)));//7
		    s4.append(CommonFunction.checkNull(outMessages.get(1)));//8
		    s1.append(CommonFunction.checkNull(outMessages.get(2)));//9
		    s2.append(CommonFunction.checkNull(outMessages.get(3)));//10
	        logger.info("gapInterest: "+ s3);
	        logger.info("gapTDS: "+s4);
	        logger.info("s1  : "+s1);
	        logger.info("s2  : "+s2);
	     
	        Number gapInterest=0.0;
			Number gapTDS=0.0;
	        if(s1.toString().equalsIgnoreCase("S"))
	        {
	        	status=s1.toString();
	        	LiquidationMakerVO vo = new LiquidationMakerVO();
	        	if((CommonFunction.checkNull(s3).trim()).equalsIgnoreCase(""))
					vo.setGapInterest("0.00");
				else
				{
					gapInterest = myFormatter.parse((CommonFunction.checkNull(s3).trim()));
					vo.setGapInterest(myFormatter.format(gapInterest));
				}
				if((CommonFunction.checkNull(s4).trim()).equalsIgnoreCase(""))
					vo.setGapTDS("0.00");
				else
				{
					gapTDS = myFormatter.parse((CommonFunction.checkNull(s4).trim()));
					vo.setGapTDS(myFormatter.format(gapTDS));
				}
	        	qryList.add(vo);
	        }
	        else if(s1.toString().equalsIgnoreCase("E"))
	        {	        	
	        	status = s2.toString();
	        }
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		finally
		{
		    status=null;
		    s1=null;
		    s2=null;
		    s3=null;
		    s4=null;
		}
		logger.info("Proc Execution Status: "+status);
		return qryList;
	}
		

	
	
}
