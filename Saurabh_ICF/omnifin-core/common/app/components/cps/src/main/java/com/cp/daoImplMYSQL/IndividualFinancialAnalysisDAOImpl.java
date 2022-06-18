package com.cp.daoImplMYSQL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.IndividualFinancialAnalysisDAO;
import com.cp.vo.BankAccountAnalysisVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.FinancialAnalysisParamVo;
import com.cp.vo.FinancialAnalysisVo;
import com.cp.vo.ObligationVo;
import com.logger.LoggerMsg;

public class IndividualFinancialAnalysisDAOImpl implements IndividualFinancialAnalysisDAO {
	
	private static final Logger logger = Logger.getLogger(IndividualFinancialAnalysisDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	
	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	
	public ArrayList<CommonDealVo> individualFinancialSearchGetDetail(CommonDealVo vo) {
		ArrayList list=new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = 5;
		try
		{
			logger.info("Inside individualFinancialSearchGetDetail.....");
			ArrayList header=null;
			CommonDealVo fetchVo= (CommonDealVo) vo;
			boolean appendSQL=false;
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			
			
			bufInsSql.append(" select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,d.DEAL_DATE,n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT from cr_deal_dtl d");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_deal_dtl d ");
			
			bufInsSql.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");			
			bufInsSqlTempCount.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
			
			bufInsSql.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");			
			bufInsSqlTempCount.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
			
			bufInsSql.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");			
			bufInsSqlTempCount.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
			
			bufInsSql.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");			
			bufInsSqlTempCount.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
			{
				bufInsSql.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' d.Rec_status='"+vo.getRecStatus()+"'");
				bufInsSqlTempCount.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' d.Rec_status='"+vo.getRecStatus()+"'");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getInitiationDate().equalsIgnoreCase("")))||((vo.getCustomername().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))){
				appendSQL=true;
			}
			
			if(appendSQL){
				logger.info("In Where Clause");
				bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"'  AND d.Rec_status='"+vo.getRecStatus()+"' ");
				bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"'  AND d.Rec_status='"+vo.getRecStatus()+"'");
			}

			 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
		        bufInsSql.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
		        bufInsSqlTempCount.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
		   	 appendSQL=true;
		   	  
		     }
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername())).trim().equalsIgnoreCase("")))) {
		   	  bufInsSql.append("AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");
		   	  bufInsSqlTempCount.append("AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");
		   	  appendSQL=true;
		     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
			   	bufInsSqlTempCount.append("AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
			   	  appendSQL=true;
			     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
			   	bufInsSqlTempCount.append("AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
			   	  appendSQL=true;
			     }
			
			LoggerMsg.info("query : "+bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()==null && StringEscapeUtils.escapeSql(vo.getCustomername()).trim()==null) || (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && StringEscapeUtils.escapeSql(vo.getCustomername()).trim().equalsIgnoreCase("")) || (StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()==null  && StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim().equalsIgnoreCase("")) || ( StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()==null && StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
			{
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*5;
				endRecordIndex = 5;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			
			}
			logger.info("query : "+bufInsSql);
			
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());

						
		    for(int i=0;i<header.size();i++){
				logger.info("header: "+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					fetchVo = new CommonDealVo();
					fetchVo.setLbxDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
					fetchVo.setDealNo("<a href=\"#\" onclick=\"editIndividualFinancialAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "','"+CommonFunction.checkNull(header1.get(1)).toString()+"','"+CommonFunction.checkNull(header1.get(2)).toString()+"');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					fetchVo.setDeal(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(1))).trim());
					//fetchVo.setDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(1))).trim());
					fetchVo.setCustomername(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setProduct(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setScheme(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(4))).trim());
					
					fetchVo.setApplicationdate(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(5))).trim());
					fetchVo.setTenure(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(6))).trim());
					fetchVo.setLoanAmount(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(7))).trim());
					
					list.add(fetchVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		LoggerMsg.info("Detail List when searchList is : "+list.size());
		
		return list;
	}
	
	public ArrayList<CommonDealVo> individualFinancialGetDetailBehind(CommonDealVo vo,HttpServletRequest request) {
		ArrayList list=new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = 5;
		ArrayList header=new ArrayList();
		try
		{
			logger.info("In individualFinancialGetDetailBehind.....");
			
			CommonDealVo fetchVo= (CommonDealVo) vo;
			boolean appendSQL=false;
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append(" select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC from cr_deal_dtl d");
			bufInsSql.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");			
			bufInsSql.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");			
			bufInsSql.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");			
			bufInsSql.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");			
			bufInsSql.append(" LEFT JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_FORWARDED<>'0000-00-00 00:00:00' AND DEAL_STAGE_ID='DC' AND M.REC_STATUS='A' ");		
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_deal_dtl d ");
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
			{
				bufInsSql.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='F'");
				bufInsSqlTempCount.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='F'");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")) ||(vo.getCustomername().equalsIgnoreCase(""))||(vo.getLbxProductID().equalsIgnoreCase(""))||(vo.getLbxscheme().equalsIgnoreCase("")))){
				appendSQL=true;
			}
			
			if(appendSQL){
				logger.info("In Where Clause");
				bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' and d.DEAL_ID in (select DEAL_ID from cr_financial_data_dtl where REC_STATUS='P' and SOURCE_TYPE='I')");
				bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' and d.DEAL_ID in (select DEAL_ID from cr_financial_data_dtl where REC_STATUS='P' and SOURCE_TYPE='I') ");
			}

			LoggerMsg.info("query : "+bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()==null && StringEscapeUtils.escapeSql(vo.getCustomername()).trim()==null) || (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && StringEscapeUtils.escapeSql(vo.getCustomername()).trim().equalsIgnoreCase("")) || (StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()==null  && StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim().equalsIgnoreCase("")) || ( StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()==null && StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
			{
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*5;
				endRecordIndex = 5;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			
			}
			logger.info("query : "+bufInsSql);
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());
						
			for(int i=0;i<header.size();i++){
				
				logger.info("header: "+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					logger.info("header list size ....................."+header.size());
					fetchVo = new CommonDealVo();
				
					fetchVo.setLbxDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
					fetchVo.setDealNo("<a href=\"#\" onclick=\"editIndividualFinancialAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "','"+CommonFunction.checkNull(header1.get(1)).toString()+"','"+CommonFunction.checkNull(header1.get(2)).toString()+"');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					fetchVo.setCustomername(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setProduct(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setScheme(StringEscapeUtils.escapeSql(CommonFunction.checkNull(header1.get(4))).trim());
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		if(header.size()==0)
//		{
//			CommonDealVo fetchVo = new CommonDealVo();
//			fetchVo.setTotalRecordSize(count);
//			list.add(fetchVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+list.size());
//		}
//		LoggerMsg.info("Detail List when searchList is : "+list.size());
		
		return list;
	}
	
	public ArrayList getParamDetailDetails(String sourceType) {
		ArrayList<Object> list=new ArrayList<Object>();
		String query="";
		try{
			
		if(sourceType.equalsIgnoreCase(""))
		{
			query="SELECT PARAM_CODE,PARAM_NAME FROM cr_financial_param WHERE REC_STATUS='A'";
		}
		else
		{
			query="SELECT PARAM_CODE,PARAM_NAME FROM cr_financial_param WHERE REC_STATUS='A' and SOURCE_TYPE='"+sourceType+"' ";
		}
		logger.info("query : "+query);
		ArrayList paramDetail = ConnectionDAO.sqlSelect(query);
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				FinancialAnalysisVo vo = new FinancialAnalysisVo();
				vo.setParameCode(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(0))).trim());
				//vo.setDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
			vo.setParamName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
                list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	
	public String saveIndividualFinancialIncome(Object ob)
	{
		logger.info("In saveIndividualFinancialIncome DaoImpl.............................. ");
		FinancialAnalysisVo vo=(FinancialAnalysisVo)ob;
		boolean status=false;
		boolean flag=false;
		String maxId ="";
		String ids="";
		String idsinsert="";
		PrepStmtObject insertPrepStmtObject = null;
		StringBuffer bufInsSql =	new StringBuffer();
		 ArrayList qryList=new ArrayList();
		 
	   	 try {
	   		 
	   		 String query="Select FINANCIAL_DATA_ID from cr_financial_data_dtl where DEAL_ID='"+vo.getLbxDealNo()+"' and DEAL_CUSTOMER_ROLE_TYPE='"+vo.getCustomerType()+"' and SOURCE_TYPE='"+vo.getSourceType()+"' and DEAL_CUSTOMER_ID='"+vo.getCustomerName()+"'";
	   		 Boolean check=ConnectionDAO.checkStatus(query);
	   		 logger.info("check:-----"+check)	;
	   		 /*for(int i=0;i<financialIds.length;i++)
		    	 {
		    		 ids=ids+","+financialIds[i];
		    	 }*/
		  
               /*  	for(int n=0;n<pCode.length;n++)
		    		{
			    		logger.info("pCode......"+pCode[n]);
		    			String query1=  "SELECT distinct PARAM_CODE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='I' and  PARAM_CODE='"+CommonFunction.checkNull(pCode[n])+"' ";
				    	logger.info("query1--saveIndividualFinancialIncome->"+query1);
				    	String paramCode=ConnectionDAO.singleReturn(query1);
				     	logger.info("paramCode--saveIndividualFinancialIncome-->"+paramCode);
				     
				     	
				     	if(! CommonFunction.checkNull(paramCode).equals("")){
				     	if(paramVal.equals("")){	
				     		paramVal=paramCode;
				     	}else{
				     		paramVal=paramVal+","+paramCode;
				     	}
				     	count++;
				     	paramValue=paramVal.split(",");
				     	logger.info("paramValue-length------>"+paramValue.length);
				  
				     	logger.info("count-"+count);
				     	}
		    		}
			    	if(! CommonFunction.checkNull(paramValue).equals("")){
			    		logger.info("paramValue-length------>"+paramValue.length);
		    		  if(paramValue.length>0){
		    				logger.info("paramValue-length----->"+paramValue.length);
					    	for(int m=0;m<count;m++)
					    	{
					    		insertPrepStmtObject = new PrepStmtObject();
					    		String query = "DELETE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='I' and  PARAM_CODE='"+CommonFunction.checkNull(paramValue[m])+"' ";
					    		logger.info("query--saveIndividualFinancialIncome-->"+query);
					    		insertPrepStmtObject.setSql(query);
					    		qryList.add(insertPrepStmtObject);
					    	}
						
			    		} 
			    	}*/
		    	/* if(!CommonFunction.checkNull(vo.getpCode()).equalsIgnoreCase(""))
		         {
		    		
		    	 for(k=0,i=0;k<pCode.length; k++,i++)
		    	{
		    		 logger.info("k is in :--"+" length is:---"+pCode.length+"-------"+k);\
		    		 	     String[][] pCodeWithYear=new String [50][50];
		    		 */
		    		 //int currBusinessDateYear =Integer.parseInt(StringEscapeUtils.escapeSql(vo.getCurrBusinessDateYear()));
		    //	maxId = ConnectionDAO.singleReturn("select max(FINANCIAL_DATA_ID) from cr_financial_data_dtl");
	    	//{
	   		 if(!check){
	   			 
	   			 String includeRation = vo.getIncludeInRatio();
	   			 if(includeRation.equalsIgnoreCase("on"))
	   				includeRation="Y";
	   			 else
	   				includeRation="N";
		    	 insertPrepStmtObject = new PrepStmtObject();
    			 bufInsSql =	new StringBuffer();
		    	    			
		    	    			
    	    			bufInsSql.append("insert into cr_financial_data_dtl(DEAL_ID,SOURCE_TYPE,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_ID,FINANCIAL_MONTH,FINANCIAL_YEAR,VERIFICATION_METHOD,REMARKS,REC_STATUS,MAKER_ID,MAKER_DATE,INCLUDE_IN_RATIO)");
    	    			bufInsSql.append(" values ( ");
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" ?," );
    	    			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)," );
    	    			bufInsSql.append(" ?" );
    	    			bufInsSql.append("  )" );
		    	    			
    	    		  	logger.info("individual saveIndividualFinancialIncome deal id **************** "+vo.getLbxDealNo());
    	    		  	
    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
    	    				insertPrepStmtObject.addNull();
    	    			else
    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim());
    	    			
    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSourceType())).trim().equalsIgnoreCase(""))
    	    				insertPrepStmtObject.addNull();
    	    			else
    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getSourceType()).trim());
    	    			
    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerType())).trim().equalsIgnoreCase(""))
    	    				insertPrepStmtObject.addNull();
    	    			else
    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerType()).trim());
    	    				
    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim().equalsIgnoreCase(""))
    	    				insertPrepStmtObject.addNull();
    	    			else
    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim());
    	    			
    	    			    				
    					if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMonthlyIncome())).trim().equalsIgnoreCase(""))
    		    			insertPrepStmtObject.addString("0.00");
    					else
    						try {
    							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getMonthlyIncome())).toString());
    						} catch (ParseException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
    					
						if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAnnuallyIncome())).trim().equalsIgnoreCase(""))
    	    				insertPrepStmtObject.addString("0.00");
    					else
    						try {
    							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getAnnuallyIncome())).toString());
    						} catch (ParseException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
    						
						if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVarificationMethod())).trim().equals(""))//,Verification_Method
    	    				insertPrepStmtObject.addNull();
    	    			else
    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getVarificationMethod()).trim());

						if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRemarks())).trim().equals(""))//,Verification_Method
    	    				insertPrepStmtObject.addNull();
    	    			else
    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getRemarks()).trim());
	
    	    			insertPrepStmtObject.addString("P");//,REC_STATUS

    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
    	    				insertPrepStmtObject.addNull();
    	    			else
    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getUserId()).trim());

    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
    	    				insertPrepStmtObject.addNull();
    	    			else
    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBusinessDate()));
    	    			
    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(includeRation));
    	    			
    	    			
    	    				insertPrepStmtObject.setSql(bufInsSql.toString());
    	    		logger.info("IN saveIndividualFinancialIncome() insert query ### "+insertPrepStmtObject.printQuery());
    	    				
		    	    				
		    	    qryList.add(insertPrepStmtObject);	
		    	  
		    	    logger.info("IN saveIndividualFinancialIncome() qryList "+qryList);
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		    	       //  	}
		    	 
   
		    			
		    			    
		    				  //status=false;
			 }			
			if(status){
				ids="Success";
		    }else if(check){
		    	ids="Already";
		    }
				
		} catch (Exception e) {
		e.printStackTrace();
	   }
	 logger.info("In saveIndividualFinancialIncome......................"+status);
		    			    
		    		 
			 
			return ids;
	}
	
	public String saveMinusIndividualFinancialIncome(FinancialAnalysisParamVo vo)
	{
		logger.info("In saveMinusIndividualFinancialIncome DaoImpl.............................. ");

		boolean status=false;
		boolean flag=false;
		String maxId ="";
		String ids="";
		String idsinsert="";
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuffer bufInsSql =	new StringBuffer();
		 ArrayList qryList=new ArrayList();
		    String pCode[] = vo.getpCode1();
		    String month[] = vo.getMonth1();
		    String year[] = vo.getYear1();
		    logger.info("pCode................ "+pCode.length);
		    /*if(vo.getFinancialIds()!=null && vo.getFinancialIds().length>0)
		    {
		    	*/
		    	String financialIds[]=null;
		    	 financialIds= vo.getFinancialIds1();
		    	 ids=financialIds[0];
		    	 try {
		    	 for(int i=0;i<financialIds.length;i++)
		    	 {
		    		 ids=ids+","+financialIds[i];
		    	 }
		    	 int i=0,k=0;
		    	 int count=0;
			    	String paramValue[]=null;
			    	for(int n=0;n<pCode.length;n++)
		    		{
		    			String query1=  "SELECT PARAM_CODE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='I' and  PARAM_CODE='"+CommonFunction.checkNull(pCode[n])+"' ";
				    	logger.info("query1--saveProfitandBalanceWithMinus->"+query1);
				    	String paramCode=ConnectionDAO.singleReturn(query1);
				     	logger.info("status1--saveProfitandBalanceWithMinus-->"+paramCode);
				     	String paramVal="";
				     	
				     	paramVal=paramVal+","+paramCode;
				     	count++;
				     	paramValue=paramVal.split(",");
				     	
		    		}
		    		  if(count>0){
					    	for(int m=0;m<count;m++){
					    		
					    		String query = "DELETE FROM cr_financial_data_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getLbxDealNo())+"' and SOURCE_TYPE='I' and  PARAM_CODE='"+CommonFunction.checkNull(paramValue[m])+"' ";
					    		logger.info("query--saveProfitandBalanceWithMinus-->"+query);
					    		insertPrepStmtObject.setSql(query);
					    		qryList.add(insertPrepStmtObject);
					    	}
						
			    		} 
		    	 if(!CommonFunction.checkNull(vo.getpCode1()).equalsIgnoreCase(""))
		         {
		    		
		    	 for(k=0,i=0;k<pCode.length; k++,i++)
		    	{
		    		 logger.info("k is in :--"+" length is:---"+pCode.length+"-------"+k);
		    	
		    		
		    		 
		    		
		    		 
		    			 
		    		     String[][] pCodeWithYear=new String [50][50];
		    		  
		    		   
		    		   
		    		    
		    		   
		    		    	maxId = ConnectionDAO.singleReturn("select max(FINANCIAL_DATA_ID) from cr_financial_data_dtl for update");
		    		    	
		    	    		
//		    	    			for(int y=currBusinessDateYear, pVal=0;y >currBusinessDateYear-5;y--,pVal++)
		    	    			//{
		    		    	 insertPrepStmtObject = new PrepStmtObject();
			    			 bufInsSql =	new StringBuffer();
		    	    			
		    	    			
		    	    			bufInsSql.append("insert into cr_financial_data_dtl(DEAL_ID,SOURCE_TYPE,PARAM_CODE,PARAM_VALUE,FINANCIAL_MONTH,REC_STATUS,MAKER_ID,MAKER_DATE)");
		    	    			bufInsSql.append(" values ( ");
		    	    			bufInsSql.append(" ?," );
		    	    			bufInsSql.append(" ?," );
		    	    			bufInsSql.append(" ?," );
		    	    			bufInsSql.append(" ?," );
		    	    			bufInsSql.append(" ?," );
		    	    			bufInsSql.append(" ?," );
		    	    			bufInsSql.append(" ?," );
		    	    			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)" );
		    	    			bufInsSql.append("  )" );
		    	    			
		    	    		  	logger.info("individual financial deal id **************** "+vo.getLbxDealNo());
		    	    		  	
		    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
		    	    				insertPrepStmtObject.addNull();
		    	    			else
		    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim());
		    	    			
		    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSourceType())).trim().equalsIgnoreCase(""))
		    	    				insertPrepStmtObject.addNull();
		    	    			else
		    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getSourceType()).trim());
		    	    			
		    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pCode[k])).trim().equalsIgnoreCase(""))
		    	    				insertPrepStmtObject.addNull();
		    	    			else
		    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(pCode[k]).trim());
		    	    				
		    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year[k])).trim().equalsIgnoreCase(""))
		    	    				insertPrepStmtObject.addString("0.00");
		    					else
		    						try {
		    							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(year[k])).toString());
		    						} catch (ParseException e) {
		    							// TODO Auto-generated catch block
		    							e.printStackTrace();
		    						}
		    	    				
		    					if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(month[k])).trim().equalsIgnoreCase(""))
		    		    			insertPrepStmtObject.addString("0.00");
		    					else
		    						try {
		    							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(month[k])).toString());
		    						} catch (ParseException e) {
		    							// TODO Auto-generated catch block
		    							e.printStackTrace();
		    						}
		    							
		    	    			insertPrepStmtObject.addString("P");//,REC_STATUS

		    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
		    	    				insertPrepStmtObject.addNull();
		    	    			else
		    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getUserId()).trim());

		    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
		    	    				insertPrepStmtObject.addNull();
		    	    			else
		    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBusinessDate()));
		    	    			
		    	    				insertPrepStmtObject.setSql(bufInsSql.toString());
		    	    				logger.info("IN saveMinusIndividualFinancialIncome() insert query ### "+insertPrepStmtObject.printQuery());
		    	    				
		    	    				
		    	    				qryList.add(insertPrepStmtObject);	
		    	    				//  logger.info("IN saveIndividualFinancialIncome() insert query: "+qryList.get(0));
		    		    		 
		    	    			}
		    	            logger.info("IN saveMinusIndividualFinancialIncome() qryList "+qryList);
								status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		    	         	}
		    	 
   
		    			
		    			    
		    				  //status=false;
		    				
		    						if(status)
		    						{
		    							idsinsert = ConnectionDAO.singleReturn("select GROUP_CONCAT(FINANCIAL_DATA_ID SEPARATOR ',') from cr_financial_data_dtl where FINANCIAL_DATA_ID>'"+maxId+"'" );
		    								ids=ids+","+idsinsert;
		    							flag=true;
		    						}
		    					} catch (Exception e) {
		    						e.printStackTrace();
		    					}
		    				    logger.info("In saveMinusIndividualFinancialIncome......................"+status);
		    			    
		    		 
			
			return ids;
	}
	
	
	public ArrayList getdealAllParamDeatils(CommonDealVo vo1) {
		ArrayList<Object> list=new ArrayList<Object>();
		String query="";
		try{

			query ="Select cfdd.FINANCIAL_DATA_ID,cfp.PARAM_CODE,IFNULL(cfdd.FINANCIAL_MONTH,0),IFNULL(cfdd.PARAM_VALUE,0),cfp.PARAM_NAME" +
					" from cr_financial_param cfp  left join cr_financial_data_dtl cfdd on cfp.PARAM_CODE=cfdd.PARAM_CODE and cfdd.REC_STATUS='"+vo1.getRecStatus()+"'" +
					" where cfp.SOURCE_TYPE='"+vo1.getSourceType()+"' and (cfdd.DEAL_ID='"+vo1.getLbxDealNo()+"' or ifnull(cfdd.DEAL_ID,'') = '') ";
			if(!CommonFunction.checkNull(vo1.getCustomerId()).equalsIgnoreCase(""))
				query = query+ " and deal_customer_id='"+vo1.getCustomerId() +"'";
//			query ="Select IFNULL(cfdd.FINANCIAL_DATA_ID,0),cfp.PARAM_CODE,IFNULL(cfdd.FINANCIAL_MONTH,0),IFNULL(cfdd.PARAM_VALUE,0)" +
//			" from cr_financial_param cfp  left join cr_financial_data_dtl cfdd on cfp.PARAM_CODE=cfdd.PARAM_CODE and cfdd.REC_STATUS='P'" +
//			" where cfp.SOURCE_TYPE='"+vo1.getSourceType()+"' and (cfdd.DEAL_ID='"+vo1.getLbxDealNo()+"' or ifnull(cfdd.DEAL_ID,'') = '') ";
		logger.info("query : "+query);
		
		ArrayList dataList = ConnectionDAO.sqlSelect(query);
		
		for(int i=0 ; i< dataList.size(); i++)
		{
			ArrayList dataList1=(ArrayList)dataList.get(i);
			if(dataList1!=null && dataList1.size()>0)
			{
			FinancialAnalysisVo vo = new FinancialAnalysisVo();
			vo.setFinancialId(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dataList1.get(0))).trim());
			vo.setParameCode(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dataList1.get(1))).trim());
			
			if((dataList1.get(1)) != null && !((dataList1.get(1)).toString().equalsIgnoreCase("")))
			{
				if(!CommonFunction.checkNull(dataList1.get(2)).equalsIgnoreCase(""))
	    		{
	    			Number reconNum =myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dataList1.get(2))).trim());  
		    		vo.setMonthValue(myFormatter.format(reconNum));
	    		}
				if(!CommonFunction.checkNull(dataList1.get(3)).equalsIgnoreCase(""))
	    		{
	    			Number reconNum =myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dataList1.get(3))).trim());  
		    		vo.setYearValue(myFormatter.format(reconNum));
	    		}
			}
			else
			{
				
				vo.setMonthValue("0");
				vo.setYearValue("0");
			}
			vo.setParamName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dataList1.get(4))).trim());
			list.add(vo);
			}
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	
	
	public String saveIndividualObligation(Object ob)
	{
		ObligationVo vo=(ObligationVo)ob;
		int maxId=0;
		PrepStmtObject insertPrepStmtObject =null;
		String result="";
			try{
				ArrayList qryList = new ArrayList();
			
			StringBuffer bufInsSql =	new StringBuffer();
			insertPrepStmtObject=new PrepStmtObject();
			String query="select deal_id from cr_obligation_analysis_dtl where DEAL_CUSTOMER_ROLE_TYPE='"+vo.getCustomerType()+"' and OBLIGATION_TYPE='"+vo.getObligationType()+"' and DEAL_ID='"+vo.getDealId()+"'";
			Boolean check=ConnectionDAO.checkStatus(query);
	   		logger.info("check:-----"+check)	;
	   		logger.info("check:-----Data Already Exists")	;
	   		check=false;
	   		if(!check){
			bufInsSql.append("insert into cr_obligation_analysis_dtl(DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_ID,INSTITUTION_NAME,PURPOSE,TENURE,CLOSURE_DATE," +
					"OUTSTANDING_AMOUNT,EMI,FINANCED_AMOUNT,OBLIGATION_TYPE,VERIFICATION_METHOD,REC_STATUS,MAKER_ID,MAKER_DATE,LOAN_NO,LOAN_TYPE,LOAN_AMOUNT,LOAN_MONTH,LOAN_TENURE,EMI_PAID,EMI_PENDING,LOAN_STATUS,OBLIGATION_TO_BE_CONSIDER,TRACK_PER,EMI_PAID_FROM,COMMENTS)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
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
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)," );
			bufInsSql.append(" ?," ); //LOAN_NO
			bufInsSql.append(" ?," ); //LOAN_TYPE
			bufInsSql.append(" ?," ); //LOAN_AMOUNT
			bufInsSql.append(" ?," ); //LOAN_MONTH
			bufInsSql.append(" ?," ); //LOAN_TENURE
			bufInsSql.append(" ?," ); //EMI_PAID
			bufInsSql.append(" ?," ); //EMI_PENDING
			bufInsSql.append(" ?," ); //LOAN_STATUS
			bufInsSql.append(" ?," ); //OBLIGATION_TO_BE_CONSIDER
			bufInsSql.append(" ?," ); //TRACK_PER
			bufInsSql.append(" ?," ); //EMI_PAID_FROM
			bufInsSql.append(" ?" ); //COMMENTS
			bufInsSql.append("  )" );
						
			if(CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))//DEAL_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getDealId()).trim());
			
			if(CommonFunction.checkNull(vo.getCustomerType()).trim().equalsIgnoreCase(""))//DEAL_CUSTOMER_ROLE_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerType()).trim());
			
			if(CommonFunction.checkNull(vo.getCustomerName()).trim().equalsIgnoreCase(""))//DEAL_CUSTOMER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim());
			
			if(CommonFunction.checkNull(vo.getInstitutionName()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getInstitutionName()).trim());
			
			if(CommonFunction.checkNull(vo.getPurpose()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getPurpose()).trim());
				
			if(CommonFunction.checkNull(vo.getTenure()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTenure()).trim());
			if(CommonFunction.checkNull(vo.getMaturityDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMaturityDate()).trim());
			if(CommonFunction.checkNull(vo.getOutstandingLoanAmount()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getOutstandingLoanAmount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getEmiAmount()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getEmiAmount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getFinanceAmount()).trim().equalsIgnoreCase(""))//FINANCED_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getFinanceAmount()).trim()).toString());
				
			
			if(CommonFunction.checkNull(vo.getObligationType()).trim().equalsIgnoreCase(""))//OBLIGATION_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationType()).trim());
			
			if(CommonFunction.checkNull(vo.getVarificationMethod()).trim().equalsIgnoreCase(""))//VERIFICATION_METHOD
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getVarificationMethod()).trim());
			
			insertPrepStmtObject.addString("P");//,REC_STATUS

			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getUserId()).trim());

			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBusinessDate()));
			//Nishant Space starts
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanNo())).trim().equals(""))//LOAN_NO
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanNo()).trim());
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTypeOfLoan())).trim().equals(""))//LOAN_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTypeOfLoan()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanAmount())).trim().equals(""))//LOAN_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getLoanAmount()).trim()).toString());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanMonth())).trim().equals(""))//LOAN_MONTH
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanMonth()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanTenure())).trim().equals(""))//LOAN_TENURE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanTenure()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPaid())).trim().equals(""))//EMI_PAID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPaid()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPending())).trim().equals(""))//EMI_PENDING
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPending()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanStatus())).trim().equals(""))//LOAN_STATUS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanStatus()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getObligationConsider())).trim().equals(""))//OBLIGATION_TO_BE_CONSIDER
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationConsider()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTrackPer())).trim().equals(""))//TRACK_PER 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTrackPer()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPaidFrom())).trim().equals(""))//EMI_PAID_FROM
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPaidFrom()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getComment())).trim().equals(""))//COMMENTS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getComment()).trim());
			//Nishant Space end
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN saveIndividualObligation() insert query ### "+insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
		      
		        boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		        
			    logger.info("In saveIndividualObligation......................"+status);
				if(status)
				{
					 String query3="Select max(OBLIGATION_ID) from cr_obligation_analysis_dtl for update";
					 String id = ConnectionDAO.singleReturn(query3);
					 maxId=Integer.parseInt(id);
					 logger.info("maxId : "+maxId);
					 result="saved";
				}else{
					 result="notsaved";
				}
			}else{
				 result="already";
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return result;
		
	}
	
	public ArrayList getObligationDetails(String obligationId,String dealId,String recStatus) {
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			
		String query="select OBLIGATION_ID,DEAL_ID,INSTITUTION_NAME,PURPOSE,TENURE,DATE_FORMAT(CLOSURE_DATE,'"+dateFormat+"'),OUTSTANDING_AMOUNT,EMI," +
				"DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_ID,FINANCED_AMOUNT,OBLIGATION_TYPE,VERIFICATION_METHOD,(select customer_name from cr_deal_customer_m where customer_id=DEAL_CUSTOMER_ID) Customer_name,LOAN_NO,LOAN_TYPE,LOAN_AMOUNT,LOAN_MONTH,LOAN_TENURE,EMI_PAID,EMI_PENDING,LOAN_STATUS,OBLIGATION_TO_BE_CONSIDER,TRACK_PER,EMI_PAID_FROM,COMMENTS from cr_obligation_analysis_dtl where 'A'='A'";
		if(obligationId.trim()!=null && !obligationId.trim().equalsIgnoreCase(""))
		{
			query =  query+" and OBLIGATION_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(obligationId)).trim();
		}
		if(dealId!=null && !dealId.trim().equalsIgnoreCase(""))
		{
			query = query + " and DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim();
		}
		logger.info("getObligationDetails: "+query);
		ObligationVo vo = null;
		ArrayList obligationDetail = ConnectionDAO.sqlSelect(query);
		for(int i=0;i<obligationDetail.size();i++){

			ArrayList subObligationDetail=(ArrayList)obligationDetail.get(i);
			if(subObligationDetail.size()>0)
			{
				vo = new ObligationVo();
				
				vo.setObligationId(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(0))).trim());
				vo.setDealId(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(1))).trim());
				vo.setInstitutionName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(2))).trim());
				vo.setPurpose(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(3))).trim());
				vo.setTenure(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(4))).trim());
				vo.setMaturityDate(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(5))).trim());
				
				if(!CommonFunction.checkNull(subObligationDetail.get(6)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(6))).trim());
					vo.setOutstandingLoanAmount(myFormatter.format(reconNumb));
				}
				if(!CommonFunction.checkNull(subObligationDetail.get(7)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(7))).trim());
					vo.setEmiAmount(myFormatter.format(reconNumb));
				}
				vo.setCustomerType(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(8))).trim());
				vo.setCustomerId(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(9))).trim());
				if(!CommonFunction.checkNull(subObligationDetail.get(10)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(10))).trim());
					vo.setFinanceAmount(myFormatter.format(reconNumb));
				}
				vo.setObligationType(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(11))).trim());
				vo.setVarificationMethod(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(12))).trim());
				vo.setCustomerName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(13))).trim());
				vo.setLoanNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(14))).trim());
				vo.setTypeOfLoan(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(15))).trim());
				vo.setLoanAmount(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(16))).trim());
				vo.setLoanMonth(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(17))).trim());
				vo.setLoanTenure(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(18))).trim());
				vo.setEmiPaid(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(19))).trim());
				vo.setEmiPending(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(20))).trim());
				vo.setLoanStatus(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(21))).trim());
				vo.setObligationConsider(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(22))).trim());
				vo.setTrackPer(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(23))).trim());
				vo.setEmiPaidFrom(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(24))).trim());
				vo.setComment(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(25))).trim());
				list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("size of list in dao................................"+list.size());
		return list;
	}
	
	public String updateIndividualObligation(Object ob)
	  {
		logger.info("In update updateIndividualObligation........ ");
		ObligationVo vo=(ObligationVo)ob;
		int maxId=0;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		boolean status = false;
		String result="";
		try
		{
			String query="select deal_id from cr_obligation_analysis_dtl where DEAL_CUSTOMER_ROLE_TYPE='"+vo.getCustomerType()+"' and OBLIGATION_TYPE='"+vo.getObligationType()+"' and DEAL_ID='"+vo.getDealId()+"' and OBLIGATION_ID <>'"+vo.getObligationId()+"'";
			Boolean check=ConnectionDAO.checkStatus(query);
	   		logger.info("check:-----"+check)	;
	   		logger.info("check:-----Data Already Exists")	;
	   		if(!check){
			String queryUpdate="update cr_obligation_analysis_dtl set DEAL_CUSTOMER_ROLE_TYPE=?,DEAL_CUSTOMER_ID=?, INSTITUTION_NAME=?,PURPOSE=?,TENURE=?,CLOSURE_DATE=STR_TO_DATE(?,'"+dateFormatWithTime+"'),OUTSTANDING_AMOUNT=?," +
					"EMI=?,FINANCED_AMOUNT=?,OBLIGATION_TYPE=?,VERIFICATION_METHOD=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),LOAN_NO=?,LOAN_TYPE=?,LOAN_AMOUNT=?,LOAN_MONTH=?,LOAN_TENURE=?,EMI_PAID=?,EMI_PENDING=?,LOAN_STATUS=?,OBLIGATION_TO_BE_CONSIDER=?,TRACK_PER=?,EMI_PAID_FROM=?,COMMENTS=? where OBLIGATION_ID=?"; 
			PrepStmtObject prepStmt = new PrepStmtObject();
			
			if(CommonFunction.checkNull(vo.getCustomerType()).trim().equalsIgnoreCase(""))//DEAL_CUSTOMER_ROLE_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerType()).trim());
			
			if(CommonFunction.checkNull(vo.getCustomerName()).trim().equalsIgnoreCase(""))//DEAL_CUSTOMER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstitutionName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getInstitutionName()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPurpose())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getPurpose()).trim());
			
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTenure())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTenure()).trim());
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMaturityDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMaturityDate()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getOutstandingLoanAmount())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getOutstandingLoanAmount()).trim()).toString());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiAmount())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getEmiAmount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getFinanceAmount()).trim().equalsIgnoreCase(""))//FINANCED_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getFinanceAmount()).trim()).toString());
				
			
			if(CommonFunction.checkNull(vo.getObligationType()).trim().equalsIgnoreCase(""))//OBLIGATION_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationType()).trim());
			
			if(CommonFunction.checkNull(vo.getVarificationMethod()).trim().equalsIgnoreCase(""))//VERIFICATION_METHOD
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getVarificationMethod()).trim());
			
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getUserId()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBusinessDate()).trim());
			//Nishant Space starts
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanNo())).trim().equals(""))//LOAN_NO
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanNo()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTypeOfLoan())).trim().equals(""))//LOAN_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTypeOfLoan()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanAmount())).trim().equals(""))//LOAN_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getLoanAmount()).trim()).toString());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanMonth())).trim().equals(""))//LOAN_MONTH
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanMonth()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanTenure())).trim().equals(""))//LOAN_TENURE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanTenure()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPaid())).trim().equals(""))//EMI_PAID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPaid()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPending())).trim().equals(""))//EMI_PENDING
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPending()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanStatus())).trim().equals(""))//LOAN_STATUS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanStatus()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getObligationConsider())).trim().equals(""))//OBLIGATION_TO_BE_CONSIDER
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationConsider()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTrackPer())).trim().equals(""))//TRACK_PER 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTrackPer()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPaidFrom())).trim().equals(""))//EMI_PAID_FROM
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPaidFrom()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getComment())).trim().equals(""))//COMMENTS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getComment()).trim());
			//Nishant Space end
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getObligationId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationId()).trim());
			
			insertPrepStmtObject.setSql(queryUpdate.toString());
			logger.info("IN updateIndividualObligation() update query ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			logger.info("In updateIndividualObligation ........ update query: "+queryUpdate);
			
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In updateIndividualObligation.........update status: "+status);
				if(status){
					result="saved";
				}else{
					result="notsaved";
				}
	   		}else{
	   			result="already";
	   		}
		}catch(Exception e){
			e.printStackTrace();
		}

		return result;

	}
	
	public int deleteIndividualObligationDetails(String id)
	{
		
            int status=0;
            boolean qryStatus = false;
           
            ArrayList qryList = new ArrayList();
   		
		try{
			String query="delete from cr_obligation_analysis_dtl where OBLIGATION_ID=?";
			PrepStmtObject insPrepStmtObject = new PrepStmtObject();
			if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(id)).trim());
			
			insPrepStmtObject.setSql(query.toString());
	        logger.info("IN deleteIndividualObligationDetails() update query ### "+insPrepStmtObject.printQuery());
			qryList.add(insPrepStmtObject);
			qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Deletion Status :."+qryStatus);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(qryStatus)
			{
				status=1;
			}
			return status;
		}
	
	public boolean individualFinancialAnalysisForward(String dealId) {

		logger.info("In individualFinancialAnalysisForward....... "+dealId);
		ArrayList qryList = null;
		qryList=new ArrayList();
		boolean status=false;
		String deal="";
		deal = ConnectionDAO.singleReturn("select DEAL_ID from cr_financial_data_dtl WHERE DEAL_ID='"+dealId+"'");
		if(deal!=null && !deal.equalsIgnoreCase(""))
		{
			String query="update cr_financial_data_dtl set REC_STATUS='A' WHERE DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"";
			logger.info("query.."+query);			
	        qryList.add(query);
	        
	        /*String queryBank="select DEAL_ID from cr_bank_analysis_dtl WHERE DEAL_ID='"+dealId+"'";
	        String dealInBank=ConnectionDAO.singleReturn(queryBank);*/
	        String queryObligation="select DEAL_ID from cr_obligation_analysis_dtl WHERE DEAL_ID='"+dealId+"'";
	        String dealInObligation=ConnectionDAO.singleReturn(queryObligation);
	        
	      /*  if(dealInBank!=null && !dealInBank.equalsIgnoreCase("")){
	        	String queryUpdateBank="update cr_bank_analysis_dtl set REC_STATUS='A' WHERE DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"";	
	        	 qryList.add(queryUpdateBank);
	        }*/
	        if(dealInObligation!=null && !dealInObligation.equalsIgnoreCase("")){
	        	String queryUpdateObligation="update cr_obligation_analysis_dtl set REC_STATUS='A' WHERE DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"";	
	        	 qryList.add(queryUpdateObligation);
	        }
	
			try {
				status = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("In stageForward......................status= "+status);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			status=false;
		}
		return status;
	}
	
	public boolean individualFinancialAnalysisUpdate(String dealId) {

		logger.info("In individualFinancialAnalysisUpdate....... "+dealId);
		ArrayList qryList = null;
		qryList=new ArrayList();
		boolean status=false;
		String deal="";
		deal = ConnectionDAO.singleReturn("select DEAL_ID from cr_financial_data_dtl WHERE DEAL_ID='"+dealId+"'");
		if(deal!=null && !deal.equalsIgnoreCase(""))
		{
			String query="update cr_financial_data_dtl set REC_STATUS='A' WHERE DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"";
			logger.info("query.."+query);			
	        qryList.add(query);
	        
			try {
				status = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("In individualFinancialAnalysisUpdate....... status= "+status);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			status=false;
		}
		return status;
	}
	
	public boolean individualRatioAnalysisUpdate(String dealId) {

		logger.info("In individualRatioAnalysisUpdate....... "+dealId);
		ArrayList qryList = null;
		qryList=new ArrayList();
		boolean status=false;
		String deal="";
		deal = ConnectionDAO.singleReturn("select DEAL_ID from cr_ratio_analysis_dtl WHERE DEAL_ID='"+dealId+"'");
		if(deal!=null && !deal.equalsIgnoreCase(""))
		{
			String query="update cr_ratio_analysis_dtl set REC_STATUS='A' WHERE DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"";
			logger.info("individualRatioAnalysisUpdate.."+query);			
	        qryList.add(query);
	        
			try {
				status = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("In individualRatioAnalysisUpdate......................status= "+status);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			status=false;
		}
		return status;
	}
	/*Code by Arun For Individual Bank Account Analysis*/
	
	public int saveBankAccountAnalysis(Object ob)
		
		{
			BankAccountAnalysisVo vo=(BankAccountAnalysisVo)ob;
			int rowcount=0;
			int maxId=0;
			PrepStmtObject insertPrepStmtObject =null;
			StringBuffer bufInsSql =null;
				try{
					
					ArrayList qryList = new ArrayList();
				
				 bufInsSql =	new StringBuffer();
				insertPrepStmtObject=new PrepStmtObject();
				bufInsSql.append("insert into cr_bank_analysis_dtl(DEAL_ID,BANK_NAME,BANK_BRANCH,ACCOUNT_NO,ACCOUNT_TYPE,STATEMENT_YEAR,STATEMENT_MONTH," +
						"TOTAL_CR,TOTAL_DR,TOTAL_EMI,HIGHEST_BALANCE,LOWEST_BALANCE,CHECK_BOUNCING_FREQ,CHECK_BOUNCING_AMOUNT,OVER_LIMIT_FREQ," +
						" OVER_LIMIT_AMOUNT,REC_STATUS,BOUNCING_INWARD_OUTWARD,LIMIT_OBLIGATION,TOTAL_CR_TXN,TOTAL_DR_TXN ,VERIFICATION_METHOD,BALANCE_AMOUNT,REMARKS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" ?," );
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)" );
				bufInsSql.append("  )" );
							
				if(CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase(""))//DEAL_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
				
				if(CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))//BANK_NAME
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxBankID()).trim());
				
				if(CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))//BANK_BRANCH
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxBranchID()).trim());
					
				if(CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))//ACCOUNT_NO
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAccountNo()).trim());
				if(CommonFunction.checkNull(vo.getAccountType()).trim().equalsIgnoreCase(""))//ACCOUNT_TYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAccountType()).trim());
				
				if(CommonFunction.checkNull(vo.getYear()).trim().equalsIgnoreCase(""))//STATEMENT_YEAR
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getYear()).trim());
				if(CommonFunction.checkNull(vo.getMonth()).trim().equalsIgnoreCase(""))//STATEMENT_MONTH
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMonth()).trim());
				
				if(CommonFunction.checkNull(vo.getCredit()).trim().equalsIgnoreCase(""))//TOTAL_CR
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getCredit()).trim()).toString());
				if(CommonFunction.checkNull(vo.getDebit()).trim().equalsIgnoreCase(""))//TOTAL_DR
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getDebit()).trim()).toString());
				if(CommonFunction.checkNull(vo.getTotalEMI()).trim().equalsIgnoreCase(""))//TOTAL_EMI
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalEMI()).trim()).toString());
				if(CommonFunction.checkNull(vo.getHighestBalance()).trim().equalsIgnoreCase(""))//HIGHEST_BALANCE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getHighestBalance()).trim()).toString());
				if(CommonFunction.checkNull(vo.getLowestBalance()).trim().equalsIgnoreCase(""))//LOWEST_BALANCE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getLowestBalance()).trim()).toString());
						
				if(CommonFunction.checkNull(vo.getCheckBounceFrequency()).trim().equalsIgnoreCase(""))//CHECK_BOUNCING_FREQ
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCheckBounceFrequency()).trim());
				if(CommonFunction.checkNull(vo.getCheckBounceAmount()).trim().equalsIgnoreCase(""))//CHECK_BOUNCING_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getCheckBounceAmount()).trim()).toString());
				if(CommonFunction.checkNull(vo.getOverLimitFrequency()).trim().equalsIgnoreCase(""))//OVER_LIMIT_FREQ
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getOverLimitFrequency()).trim());
				LoggerMsg.info("over limit frequency .... "+(vo.getOverLimitFrequency()).trim());
				if(CommonFunction.checkNull(vo.getOverLimitAmount()).trim().equalsIgnoreCase(""))//OVER_LIMIT_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getOverLimitAmount()).trim()).toString());
				
				insertPrepStmtObject.addString("P");//,REC_STATUS
				
				if(CommonFunction.checkNull(vo.getChequeBouncing()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getChequeBouncing()).trim());
				
				logger.info("value of cyheque bouncing is "+vo.getChequeBouncing());
				
				if(CommonFunction.checkNull(vo.getLimitObligation()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLimitObligation()).trim());			
				logger.info("value of cyheque bouncing is "+vo.getLimitObligation());
				//
				if(CommonFunction.checkNull(vo.getTotalCreditTxn()).trim().equalsIgnoreCase(""))//TOTAL_CR_TXN
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalCreditTxn()).trim()).toString());
				
				if(CommonFunction.checkNull(vo.getTotalDebitTxn()).trim().equalsIgnoreCase(""))//TOTAL_DR_TXN
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalDebitTxn()).trim()).toString());
				
				if(CommonFunction.checkNull(vo.getVarificationMethod()).trim().equalsIgnoreCase(""))//VERIFICATION_METHOD
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getVarificationMethod()).trim());
				
				if(CommonFunction.checkNull(vo.getBalanceAmount()).trim().equalsIgnoreCase(""))//BALANCE_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getBalanceAmount()).trim()).toString());
				
				if(CommonFunction.checkNull(vo.getRemarks()).trim().equalsIgnoreCase(""))//REMARKS
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRemarks()).trim());
				//
				if((CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getUserId()).trim());

				if((CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBusinessDate()));
				
					insertPrepStmtObject.setSql(bufInsSql.toString());
					
					logger.info("IN saveBankAccountAnalysis() insert query1 ### "+insertPrepStmtObject.printQuery());
					
					qryList.add(insertPrepStmtObject);
			      
			        boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			        
				    logger.info("In saveBankAccountAnalysis......................"+status);
					if(status)
					{
						StringBuilder query3=new StringBuilder();
					query3.append("Select max(BANK_ANALYSIS_ID) from cr_bank_analysis_dtl for update");
					StringBuilder id=new StringBuilder();
						 id.append(ConnectionDAO.singleReturn(query3.toString()));
						 maxId=Integer.parseInt(id.toString());
						 logger.info("maxId : "+maxId);
						 query3=null;
						 id=null;
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}finally{
					 bufInsSql =null;
				}
			
				return maxId;
			
		}
		
		public ArrayList getBankAccountAnalysisDetails(String bankAcctAnsId,String dealId,String recStatus) {
			ArrayList<Object> list=new ArrayList<Object>();
			try{
				StringBuilder query=new StringBuilder();
		 query.append("select b.DEAL_ID,d.DEAL_NO,b.BANK_NAME,bnk.BANK_NAME,b.BANK_BRANCH,br.BANK_BRANCH_NAME,ACCOUNT_NO,ACCOUNT_TYPE," +
					"STATEMENT_YEAR,STATEMENT_MONTH,TOTAL_CR,TOTAL_DR,TOTAL_EMI,HIGHEST_BALANCE,LOWEST_BALANCE,CHECK_BOUNCING_FREQ," +
					"CHECK_BOUNCING_AMOUNT,OVER_LIMIT_FREQ,OVER_LIMIT_AMOUNT,BANK_ANALYSIS_ID,BOUNCING_INWARD_OUTWARD,LIMIT_OBLIGATION,deal.CUSTOMER_NAME, " +
					" TOTAL_CR_TXN,TOTAL_DR_TXN ,VERIFICATION_METHOD,BALANCE_AMOUNT,REMARKS " +
					" from cr_bank_analysis_dtl b left join cr_deal_dtl d on b.DEAL_ID=d.DEAL_ID and b.DEAL_ID=d.DEAL_ID" +
					" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID left join com_bank_m bnk on bnk.BANK_ID=b.BANK_NAME" +
					" left join com_bankbranch_m br on br.BANK_BRANCH_ID=b.BANK_BRANCH" +
					" where b.REC_STATUS='"+recStatus+"'");
			if(bankAcctAnsId.trim()!=null && !bankAcctAnsId.trim().equalsIgnoreCase(""))
			{
				logger.info("bankAcctAnsId: "+bankAcctAnsId);
				query.append(" and b.BANK_ANALYSIS_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankAcctAnsId)).trim());
			}
			if(dealId!=null && !dealId.equalsIgnoreCase(""))
			{
				logger.info("dealId: "+dealId);
				query.append(" and b.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			}
			logger.info("getBankAccountAnalysisDetails: "+query);
			BankAccountAnalysisVo vo = null;
			ArrayList bankAcDetail = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			
			for(int i=0;i<bankAcDetail.size();i++){

				ArrayList bankAcDetail1=(ArrayList)bankAcDetail.get(i);
				if(bankAcDetail1.size()>0)
				{
					vo = new BankAccountAnalysisVo();
					vo.setLbxDealNo((CommonFunction.checkNull(bankAcDetail1.get(0))).trim());
					vo.setDealNo((CommonFunction.checkNull(bankAcDetail1.get(1))).trim());
					vo.setLbxBankID((CommonFunction.checkNull(bankAcDetail1.get(2))).trim());
					vo.setBankName((CommonFunction.checkNull(bankAcDetail1.get(3))).trim());
					vo.setLbxBranchID((CommonFunction.checkNull(bankAcDetail1.get(4))).trim());
					vo.setBankBranch((CommonFunction.checkNull(bankAcDetail1.get(5))).trim());
					vo.setAccountNo((CommonFunction.checkNull(bankAcDetail1.get(6))).trim());
					vo.setAccountType((CommonFunction.checkNull(bankAcDetail1.get(7))).trim());
					
					vo.setYear((CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					vo.setMonth((CommonFunction.checkNull(bankAcDetail1.get(9))).trim());
					if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("1"))
					{
						vo.setStatementMonthAndYear("Jan "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
						
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("2"))
					{
						vo.setStatementMonthAndYear("Feb "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("3"))
					{
						vo.setStatementMonthAndYear("Mar "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("4"))
					{
						vo.setStatementMonthAndYear("Apr "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("5"))
					{
						vo.setStatementMonthAndYear("May "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("6"))
					{
						vo.setStatementMonthAndYear("Jun "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("7"))
					{
						vo.setStatementMonthAndYear("Jul "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("8"))
					{
						vo.setStatementMonthAndYear("Aug "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("9"))
					{
						vo.setStatementMonthAndYear("Sep "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("10"))
					{
						vo.setStatementMonthAndYear("Oct "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("11"))
					{
						vo.setStatementMonthAndYear("Nov "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("12"))
					{
						vo.setStatementMonthAndYear("Dec "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					}
					
					
					if(!CommonFunction.checkNull(bankAcDetail1.get(10)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(10))).trim());
						vo.setCredit(myFormatter.format(reconNumb));
					}
					if(!CommonFunction.checkNull(bankAcDetail1.get(11)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(11))).trim());
						vo.setDebit(myFormatter.format(reconNumb));
					}
				
					if(!CommonFunction.checkNull(bankAcDetail1.get(12)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(12))).trim());
						vo.setTotalEMI(myFormatter.format(reconNumb));
					}
					logger.info("total emi:::?????"+bankAcDetail1.get(12));
					Double higherBal=0.0000;
					Double lowerBal=0.0000;
					
					if(!CommonFunction.checkNull(bankAcDetail1.get(13)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(13))).trim());
						vo.setHighestBalance(myFormatter.format(reconNumb));
						higherBal=Double.parseDouble((CommonFunction.checkNull(bankAcDetail1.get(13))).trim());
					}
					logger.info("hjighest bal:::::::"+bankAcDetail1.get(13));
					logger.info("lowest bal:::::::"+bankAcDetail1.get(14));
					
					if(!CommonFunction.checkNull(bankAcDetail1.get(14)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(14))).trim());
						vo.setLowestBalance(myFormatter.format(reconNumb));
						lowerBal=Double.parseDouble((CommonFunction.checkNull(bankAcDetail1.get(14))).trim());
					}
				
					vo.setCheckBounceFrequency((CommonFunction.checkNull(bankAcDetail1.get(15))).trim());
					
					if(!CommonFunction.checkNull(bankAcDetail1.get(16)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(16))).trim());
						vo.setCheckBounceAmount(myFormatter.format(reconNumb));
					}
					vo.setOverLimitFrequency((CommonFunction.checkNull(bankAcDetail1.get(17))).trim());
					if(!CommonFunction.checkNull(bankAcDetail1.get(18)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(18))).trim());
						vo.setOverLimitAmount(myFormatter.format(reconNumb));
					}
					vo.setBankAcAnId((CommonFunction.checkNull(bankAcDetail1.get(19))).trim());
					logger.info("swing amount higherBal-lowerBal ................................."+(higherBal-lowerBal)*100);
					if(higherBal-lowerBal>=0 && !(CommonFunction.checkNull((bankAcDetail1.get(13))).trim().equalsIgnoreCase("")) && !(CommonFunction.checkNull((bankAcDetail1.get(14))).trim().equalsIgnoreCase("")))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(higherBal-lowerBal)).trim());
						vo.setSwingAmt(myFormatter.format(reconNumb));
						
					}
					else{
						Number reconNumb=0;
						vo.setSwingAmt(myFormatter.format(reconNumb));
						logger.info("this is highest:::::::");
					}
					logger.info("swing ((higherBal-lowerBal)*higherBal)/100) ................................."+(((higherBal-lowerBal)/lowerBal)*100));
				
					if(((higherBal-lowerBal)*higherBal)/100 >=0 && lowerBal!=0 &&!(CommonFunction.checkNull((bankAcDetail1.get(13))).trim().equalsIgnoreCase("")) && !(CommonFunction.checkNull((bankAcDetail1.get(14))).trim().equalsIgnoreCase("")))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(((higherBal-lowerBal)/lowerBal)*100)).trim());
						vo.setSwingPercent(myFormatter.format(reconNumb));
					
						
					}
					else{
						Number reconNumb =0;
						vo.setSwingPercent(myFormatter.format(reconNumb));
						logger.info("this is lowest:::::::");
					}
					
					
					vo.setChequeBouncing((CommonFunction.checkNull(bankAcDetail1.get(20))).trim());
					vo.setLimitObligation((CommonFunction.checkNull(bankAcDetail1.get(21))).trim());
					logger.info("limi obligation value is::::::::"+bankAcDetail1.get(21));
					logger.info("cheque bouncing value is::::::::"+bankAcDetail1.get(20));
					vo.setCustomerName((CommonFunction.checkNull(bankAcDetail1.get(22))).trim());
					logger.info("CustomerName value is::::::::"+bankAcDetail1.get(22));
					if(!CommonFunction.checkNull(bankAcDetail1.get(23)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(23))).trim());
						vo.setTotalCreditTxn(myFormatter.format(reconNumb));
						
					}
					if(!CommonFunction.checkNull(bankAcDetail1.get(24)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(24))).trim());
						vo.setTotalDebitTxn(myFormatter.format(reconNumb));
						
					}
					vo.setVarificationMethod((CommonFunction.checkNull(bankAcDetail1.get(25))).trim());
					if(!CommonFunction.checkNull(bankAcDetail1.get(26)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(26))).trim());
						vo.setBalanceAmount(myFormatter.format(reconNumb));
						
					}
					vo.setRemarks((CommonFunction.checkNull(bankAcDetail1.get(27))).trim());
	                list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("bankAcDetail1 size is .................................."+list.size());
			return list;
		}
		
		public int deleteBankAnalysis(String id)
		{
			
	            int status=0;
	            boolean qryStatus = false;
	           
	            ArrayList qryList = new ArrayList();
	   		
			try{
				StringBuilder query=new StringBuilder();
				 query.append("delete from cr_bank_analysis_dtl where BANK_ANALYSIS_ID=?");
				PrepStmtObject insPrepStmtObject = new PrepStmtObject();
				if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
					insPrepStmtObject.addNull();
				else
					insPrepStmtObject.addString((CommonFunction.checkNull(id)).trim());
				
				insPrepStmtObject.setSql(query.toString());
		        logger.info("IN deleteBankAnalysis() delete query ### "+insPrepStmtObject.printQuery());
				qryList.add(insPrepStmtObject);
				qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("Deletion Status :."+qryStatus);
			query=null;	
			}catch(Exception e){
					e.printStackTrace();
				}
				if(qryStatus)
				{
					status=1;
				}
				return status;
	}
		
		
		public boolean updateBankAccountAnalysis(Object ob)
		  {
			logger.info("In update updateBankAccountAnalysis........ ");
			BankAccountAnalysisVo vo=(BankAccountAnalysisVo)ob;
	       	int maxId=0;
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			boolean status = false;
			try
			{
				StringBuilder queryUpdate=new StringBuilder();
				queryUpdate.append("update cr_bank_analysis_dtl set DEAL_ID=?,BANK_NAME=?,BANK_BRANCH=?,ACCOUNT_NO=?,ACCOUNT_TYPE=?,STATEMENT_YEAR=?,STATEMENT_MONTH=?,TOTAL_CR=?," +
						"TOTAL_DR=?,TOTAL_EMI=?,HIGHEST_BALANCE=?,LOWEST_BALANCE=?,CHECK_BOUNCING_FREQ=?,CHECK_BOUNCING_AMOUNT=?,OVER_LIMIT_FREQ=?," +
						"OVER_LIMIT_AMOUNT=?,BOUNCING_INWARD_OUTWARD=?,LIMIT_OBLIGATION=?,TOTAL_CR_TXN=?,TOTAL_DR_TXN=? ,VERIFICATION_METHOD=?,BALANCE_AMOUNT=?,REMARKS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where BANK_ANALYSIS_ID=?"); 
				PrepStmtObject prepStmt = new PrepStmtObject();
				if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
				
				if((CommonFunction.checkNull(vo.getLbxBankID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxBankID()).trim());
				
				if((CommonFunction.checkNull(vo.getLbxBranchID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxBranchID()).trim());
				
				if((CommonFunction.checkNull(vo.getAccountNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAccountNo()).trim());
				
				if((CommonFunction.checkNull(vo.getAccountType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAccountType()).trim());
				
				if((CommonFunction.checkNull(vo.getYear())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getYear()).trim());
				if((CommonFunction.checkNull(vo.getMonth())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMonth()).trim());
				
				if((CommonFunction.checkNull(vo.getCredit())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getCredit()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getDebit())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getDebit().trim())).toString());
				
				if((CommonFunction.checkNull(vo.getTotalEMI())).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalEMI().trim())).toString());
				
				if(CommonFunction.checkNull((vo.getHighestBalance())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getHighestBalance()).trim()).toString());
				
				if((CommonFunction.checkNull(vo.getLowestBalance())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getLowestBalance()).trim()).toString());
				
				
				
				if((CommonFunction.checkNull(vo.getCheckBounceFrequency())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCheckBounceFrequency()).trim());
				if((CommonFunction.checkNull(vo.getCheckBounceAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getCheckBounceAmount()).trim()).toString());
				if((CommonFunction.checkNull(vo.getOverLimitFrequency())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getOverLimitFrequency()).trim());
				if((CommonFunction.checkNull(vo.getOverLimitAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getOverLimitAmount()).trim()).toString());
				if((CommonFunction.checkNull(vo.getChequeBouncing())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getChequeBouncing()).trim());
				if((CommonFunction.checkNull(vo.getLimitObligation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLimitObligation()).trim());					
				//
				if(CommonFunction.checkNull(vo.getTotalCreditTxn()).trim().equalsIgnoreCase(""))//TOTAL_CR_TXN
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalCreditTxn()).trim()).toString());
				
				if(CommonFunction.checkNull(vo.getTotalDebitTxn()).trim().equalsIgnoreCase(""))//TOTAL_DR_TXN
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalDebitTxn()).trim()).toString());
				
				if(CommonFunction.checkNull(vo.getVarificationMethod()).trim().equalsIgnoreCase(""))//VERIFICATION_METHOD
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getVarificationMethod()).trim());
				
				if(CommonFunction.checkNull(vo.getBalanceAmount()).trim().equalsIgnoreCase(""))//BALANCE_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getBalanceAmount()).trim()).toString());
				
				if(CommonFunction.checkNull(vo.getRemarks()).trim().equalsIgnoreCase(""))//REMARKS
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRemarks()).trim());
				//
				
				if((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getUserId()).trim());
				
				if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBusinessDate()).trim());
				
				if((CommonFunction.checkNull(vo.getBankAcAnId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBankAcAnId()).trim());
				
				logger.info("cheque bouncing value is::::::++++"+vo.getChequeBouncing());
				logger.info(" limit obligation value is::::::++++"+vo.getLimitObligation());

				insertPrepStmtObject.setSql(queryUpdate.toString());
				logger.info("IN updateBankAccountAnalysis() update query ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				logger.info("In updateBankAccountAnalysis ........ update query: "+queryUpdate);
				
					status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("In updateBankAccountAnalysis.........update status: "+status);
				
				queryUpdate=null;
				
			}catch(Exception e){
				e.printStackTrace();
			}

			return status;

		}
		/*Code by arun for Individual bank account analysis Ends here*/

		
		public ArrayList getCustomerTypeList(String dealId) {
			ArrayList<Object> list=new ArrayList<Object>();
			StringBuilder query=new StringBuilder();
			try{
				
			
			query.append("select distinct DEAL_CUSTOMER_ROLE_TYPE,DESCRIPTION from cr_deal_customer_role a  ,generic_master b where b.value=a.DEAL_CUSTOMER_ROLE_TYPE");
			query.append(" and DEAL_ID='"+dealId+"' and GENERIC_KEY='CUST_ROLE' and value in ('PRAPPL','COAPPL') order by DESCRIPTION") ;
			
			logger.info("query : "+query.toString());
			ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<paramDetail.size();i++){

				ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
				if(subParamDetail.size()>0)
				{
					FinancialAnalysisVo vo = new FinancialAnalysisVo();
					vo.setParameCode(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(0))).trim());
					//vo.setDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setParamName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
	                list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("paramDetail size is .................................."+list.size());
			return list;
		}

		
		public ArrayList getIncomeSourceTypeList() {
			ArrayList<Object> list=new ArrayList<Object>();
			String query="";
			try{
			
			query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='INCOME_SOURCE_TYPE' and REC_STATUS='A'";
			
			logger.info("query : "+query);
			ArrayList paramDetail = ConnectionDAO.sqlSelect(query);
			for(int i=0;i<paramDetail.size();i++){

				ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
				if(subParamDetail.size()>0)
				{
					FinancialAnalysisVo vo = new FinancialAnalysisVo();
					vo.setParameCode(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(0))).trim());
					//vo.setDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
			    	vo.setParamName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
	                list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("paramDetail size is .................................."+list.size());
			return list;
		}
		public ArrayList getCustomerName(String dealId,String customerType){
			ArrayList<Object> list=new ArrayList<Object>();
			StringBuilder query=new StringBuilder();
			try{
				
			
			query.append("select b.deal_customer_id,CUSTOMER_NAME from cr_deal_customer_m a,cr_deal_customer_role b  ");
			query.append(" where b.deal_customer_id=a.CUSTOMER_ID and DEAL_ID='"+dealId+"' and DEAL_CUSTOMER_ROLE_TYPE in ('PRAPPL','COAPPL')") ;
		
			
			logger.info("query : "+query);
			ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<paramDetail.size();i++){

				ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
				if(subParamDetail.size()>0)
				{
					FinancialAnalysisVo vo = new FinancialAnalysisVo();
					vo.setParameCode(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(0))).trim());
					//vo.setDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
			    	vo.setParamName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
	                list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("paramDetail size is .................................."+list.size());
			return list;
		}


		public ArrayList getIncomeDetailList(Object ob) {
			FinancialAnalysisVo obVo=(FinancialAnalysisVo)ob;
			StringBuilder query=new StringBuilder();
			ArrayList list=new ArrayList();
			try{
				
			
			query.append(" select FINANCIAL_DATA_ID,DEAL_ID,");
			query.append("(select description from generic_master where GENERIC_KEY='INCOME_SOURCE_TYPE' and value=a.SOURCE_TYPE) SOURCE_TYPE,") ;
			query.append(" (select description from generic_master where GENERIC_KEY='CUST_ROLE' and value=a.DEAL_CUSTOMER_ROLE_TYPE) DEAL_CUSTOMER_ROLE_TYPE,");
			query.append("(select CUSTOMER_NAME from cr_deal_customer_m  where CUSTOMER_ID=a.DEAL_CUSTOMER_ID) DEAL_CUSTOMER_ID,FINANCIAL_MONTH,FINANCIAL_YEAR,");
			query.append(" (select description from generic_master where GENERIC_KEY='VERIFICATION_METHOD' and value=VERIFICATION_METHOD) VERIFICATION_METHOD,ifnull(INCLUDE_IN_RATIO,'Y') ");
			query.append("  from cr_financial_data_dtl a where deal_id='"+obVo.getLbxDealNo()+"'");
			if(!CommonFunction.checkNull(obVo.getCustomerId()).equalsIgnoreCase(""))
				query.append("  and  deal_customer_id='"+obVo.getCustomerId()+"'");
			
			query.append("  and  source_type not in ('P','B','O') ");
			logger.info("query : "+query);
			ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<paramDetail.size();i++){

				ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
				if(subParamDetail.size()>0)
				{
					FinancialAnalysisVo vo = new FinancialAnalysisVo();
					vo.setFinancialId(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(0))).trim());
					vo.setLbxDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
					vo.setSourceType(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(2))).trim());
					vo.setCustomerType(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(3))).trim());
					vo.setCustomerName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(4))).trim());
					
					if(!CommonFunction.checkNull(subParamDetail.get(5)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(subParamDetail.get(5))).trim());
						vo.setMonthlyIncome(myFormatter.format(reconNumb));
						
					}else{
						vo.setMonthlyIncome("0.00");
					}
					if(!CommonFunction.checkNull(subParamDetail.get(6)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(subParamDetail.get(6))).trim());
						vo.setAnnuallyIncome(myFormatter.format(reconNumb));
						
					}else{
						vo.setAnnuallyIncome("0.00");
					}
					vo.setVarificationMethod(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(7))).trim());
					vo.setIncludeInRatio(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(8))).trim());

	                list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("paramDetail size is .................................."+list.size());
			return list;
		}
		
		public ArrayList getIncomeDetaisForEdit(Object ob) {
			FinancialAnalysisVo obVo=(FinancialAnalysisVo)ob;
			StringBuilder query=new StringBuilder();
			ArrayList list=new ArrayList();
			try{
				
			//Change in the query by sarvesh
			query.append("select FINANCIAL_DATA_ID,DEAL_ID,SOURCE_TYPE,g.DESCRIPTION,m.customer_name,");
			query.append("FINANCIAL_MONTH,FINANCIAL_YEAR,VERIFICATION_METHOD,remarks,deal_customer_id, case m.customer_type WHEN 'C' THEN 'CORPORATE' WHEN 'I' THEN 'INDIVIDUAL' END customer_type  from cr_financial_data_dtl f "
					+ " left join generic_master g on g.GENERIC_KEY='CUST_ROLE' and g.value=f.DEAL_CUSTOMER_ROLE_TYPE "
					+ " left join cr_deal_customer_m m on m.customer_id = f.deal_customer_id") ;
			query.append(" where FINANCIAL_DATA_ID='"+obVo.getFinancialId()+"'");
			
			
			logger.info("query : "+query);
			ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<paramDetail.size();i++){

				ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
				if(subParamDetail.size()>0)
				{
					FinancialAnalysisVo vo = new FinancialAnalysisVo();
					vo.setFinancialId(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(0))).trim());
					vo.setLbxDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
					vo.setSourceType(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(2))).trim());
					vo.setCustomerType(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(3))).trim());
					vo.setCustomerName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(4))).trim());
					
					if(!CommonFunction.checkNull(subParamDetail.get(5)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(subParamDetail.get(5))).trim());
						vo.setMonthlyIncome(myFormatter.format(reconNumb));
						
					}else{
						vo.setMonthlyIncome("0.00");
					}
					if(!CommonFunction.checkNull(subParamDetail.get(6)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(subParamDetail.get(6))).trim());
						vo.setAnnuallyIncome(myFormatter.format(reconNumb));
						
					}else{
						vo.setAnnuallyIncome("0.00");
					}
					vo.setVarificationMethod(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(7))).trim());
                    vo.setRemarks(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(8))).trim());
                    vo.setCustomerId(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(9))).trim());
                    vo.setLbxCustomerRoleType(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(10))).trim());
	                list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("paramDetail size is .................................."+list.size());
			return list;
		}
	
		public String updateIndividualFinancialIncome(Object ob) {
			logger.info("In updateIndividualFinancialIncome DaoImpl.............................. ");
			FinancialAnalysisVo vo=(FinancialAnalysisVo)ob;
			boolean status=false;
			boolean flag=false;
			String maxId ="";
			String ids="";
			String idsinsert="";
			PrepStmtObject insertPrepStmtObject = null;
			StringBuffer bufInsSql =	new StringBuffer();
			 ArrayList qryList=new ArrayList();
			 
		   	 try {
		   		 
		   		 String query="Select FINANCIAL_DATA_ID from cr_financial_data_dtl where DEAL_ID='"+vo.getLbxDealNo()+"' and DEAL_CUSTOMER_ROLE_TYPE='"+vo.getCustomerType()+"' and SOURCE_TYPE='"+vo.getSourceType()+"' and DEAL_CUSTOMER_ID='"+vo.getCustomerName()+"' and FINANCIAL_DATA_ID <> '"+vo.getFinancialId()+"'";
		   		 Boolean check=ConnectionDAO.checkStatus(query);
		   		 logger.info("check:-----"+check)	;
		   		
		   		 if(!check){
		   			 insertPrepStmtObject = new PrepStmtObject();
	    			 bufInsSql =	new StringBuffer();
			    	    			
	    			 String includeRation = vo.getIncludeInRatio();
		   			 if(includeRation.equalsIgnoreCase("on"))
		   				includeRation="Y";
		   			 else
		   				includeRation="N";	
	    	    			bufInsSql.append("update  cr_financial_data_dtl set SOURCE_TYPE=?,DEAL_CUSTOMER_ROLE_TYPE=?,DEAL_CUSTOMER_ID=?,FINANCIAL_MONTH=?,FINANCIAL_YEAR=?,VERIFICATION_METHOD=?,REMARKS=?,MAKER_ID=?,INCLUDE_IN_RATIO=?,");
	    	    			bufInsSql.append(" MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)" );
	    	    			bufInsSql.append(" where FINANCIAL_DATA_ID=?");
			    	    			
	    	    		  	logger.info("individual saveIndividualFinancialIncome deal id **************** "+vo.getLbxDealNo());
	    	    		  	
	    	    		
	    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSourceType())).trim().equalsIgnoreCase(""))
	    	    				insertPrepStmtObject.addNull();
	    	    			else
	    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getSourceType()).trim());
	    	    			
	    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerType())).trim().equalsIgnoreCase(""))
	    	    				insertPrepStmtObject.addNull();
	    	    			else
	    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerType()).trim());
	    	    				
	    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim().equalsIgnoreCase(""))
	    	    				insertPrepStmtObject.addNull();
	    	    			else
	    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim());
	    	    			
	    	    			    				
	    					if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMonthlyIncome())).trim().equalsIgnoreCase(""))
	    		    			insertPrepStmtObject.addString("0.00");
	    					else
	    						try {
	    							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getMonthlyIncome())).toString());
	    						} catch (ParseException e) {
	    							// TODO Auto-generated catch block
	    							e.printStackTrace();
	    						}
	    					
							if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAnnuallyIncome())).trim().equalsIgnoreCase(""))
	    	    				insertPrepStmtObject.addString("0.00");
	    					else
	    						try {
	    							insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getAnnuallyIncome())).toString());
	    						} catch (ParseException e) {
	    							// TODO Auto-generated catch block
	    							e.printStackTrace();
	    						}
	    						
							if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVarificationMethod())).trim().equals(""))//,Verification_Method
	    	    				insertPrepStmtObject.addNull();
	    	    			else
	    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getVarificationMethod()).trim());

							if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRemarks())).trim().equals(""))//,Verification_Method
	    	    				insertPrepStmtObject.addNull();
	    	    			else
	    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getRemarks()).trim());
		
	    	    	    	if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
	    	    				insertPrepStmtObject.addNull();
	    	    			else
	    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getUserId()).trim());
	    	    	    	
	    	    	    	insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(includeRation).trim());

	    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
	    	    				insertPrepStmtObject.addNull();
	    	    			else
	    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBusinessDate()));
	    	    			
	    	    			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getFinancialId())).trim().equalsIgnoreCase(""))
	    	    				insertPrepStmtObject.addNull();
	    	    			else
	    	    				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getFinancialId()).trim());
	    	    			
	    	    				insertPrepStmtObject.setSql(bufInsSql.toString());
	    	    		logger.info("IN saveIndividualFinancialIncome() insert query ### "+insertPrepStmtObject.printQuery());
	    	    				
			    	    				
			    	    qryList.add(insertPrepStmtObject);	
			    	  
			    	    logger.info("IN saveIndividualFinancialIncome() qryList "+qryList);
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    	   
				 }			
				if(status){
					ids="Success";
			    }else if(check){
			    	ids="Already";
			    }
					
			} catch (Exception e) {
			e.printStackTrace();
		   }
		 logger.info("In saveIndividualFinancialIncome......................"+status);
			    			    
			    		 
				 
				return ids;
		}


		public ArrayList getObligationTypeList() {
			ArrayList<Object> list=new ArrayList<Object>();
			String query="";
			try{
			
			query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='OBLIGATION_TYPE' and REC_STATUS='A'";
			
			logger.info("query : "+query);
			ArrayList paramDetail = ConnectionDAO.sqlSelect(query);
			for(int i=0;i<paramDetail.size();i++){

				ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
				if(subParamDetail.size()>0)
				{
					FinancialAnalysisVo vo = new FinancialAnalysisVo();
					vo.setParameCode(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(0))).trim());
					//vo.setDealNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
			    	vo.setParamName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subParamDetail.get(1))).trim());
	                list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("paramDetail size is .................................."+list.size());
			return list;
		}
		/*Added By arun deleteRatioAnalysis*/
		@Override
		public String deleteRatioAnalysis(String dealId) {
			boolean delete=false;
			String result="";
			try{
			String selQuery="select count(1) from  cr_ratio_analysis_dtl where deal_id='"+CommonFunction.checkNull(dealId).trim()+"'";
			String checkCount=CommonFunction.checkNull(ConnectionDAO.singleReturn(selQuery));
			if(!checkCount.equalsIgnoreCase("0")){
				ArrayList list =new ArrayList();
				String deleteQuery="delete from cr_ratio_analysis_dtl where deal_id='"+CommonFunction.checkNull(dealId).trim()+"'";
				logger.info("deleteQuery:---------"+deleteQuery);
				list.add(deleteQuery);
				delete=ConnectionDAO.sqlInsUpdDelete(list);
			}
			result=delete+"";
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return result;
		}

/*Added By arun deleteRatioAnalysis Ends Here*/	
		public ArrayList getTypeOfLoan(){
			ArrayList typeOfLoanList=new  ArrayList();
			  
			  ArrayList searchList=new ArrayList();
			  ArrayList subList=new ArrayList();
			  ObligationVo vo=null;
				try{
		              String query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='TYPE_OF_LOAN' and rec_status ='A'";	
		              logger.info("In Type of loan list......Dao Impl"+query);
					  
					  searchList=ConnectionDAO.sqlSelect(query);
					  logger.info("query:::::::::::::::::::::::"+query);
					  int size = searchList.size();
					  logger.info("searchList:::::::::::::::::::::::"+size);
					  
					  for(int i=0 ; i<size; i++){
						subList = (ArrayList) searchList.get(i);

						if(subList.size()>0){
							vo=new ObligationVo();	
							vo.setTypeOfLoanValue(CommonFunction.checkNull(subList.get(0)).toString());
							vo.setTypeOfLoanValueId(CommonFunction.checkNull(subList.get(1)).toString());
							typeOfLoanList.add(vo);
						} 
					  }					  
				}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("paramDetail size is .................................."+typeOfLoanList.size());
			return typeOfLoanList;
		}	
		public String updateStatus(String dealId){
			String status="";
			StringBuffer bufIns =new StringBuffer();
			ArrayList list = new ArrayList();
			try
			{
			bufIns.append("Update cr_DEAL_dtl set REC_STATUS='F' where DEAL_ID='"+CommonFunction.checkNull(dealId).trim()+"' ");
			list.add(bufIns.toString());
			logger.info("Deal Movement......."+bufIns.toString());
			boolean st =ConnectionDAO.sqlInsUpdDelete(list);
			if(st)
			{
				status="S";
			}
			else 
				status="E";
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return status;
			
		}

		@Override
		public boolean getCheckBankFinancialStatus(String caseId) {
			logger.info("getCheckBankFinancialStatus .......");
			String qry = "select Count(1) from cr_bank_analysis_dtl where DEAL_Id = '"+caseId+"'  and REC_STATUS = 'A'";
			logger.info("qry ---------"+qry);
			String value = ConnectionDAO.singleReturn(qry);
			int i = 0;
			try {
			i=	Integer.parseInt(value);
			} catch (Exception e) {
				i=0;
			}
			return i>0?true:false;
		}

		@Override
		public ArrayList getGenericMasterInfoList(String genericKey) {
			ArrayList genericmasterList=new  ArrayList();
			  
			  ArrayList searchList=new ArrayList();
			  ArrayList subList=new ArrayList();
			  CodeDescVo vo=null;
				try{
		              String query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='"+genericKey+"' and rec_status ='A'";	
		              logger.info("getGenericMasterInfoList......Dao Impl"+query);
					  
					  searchList=ConnectionDAO.sqlSelect(query);
					  int size = searchList.size();
					  logger.info("searchList:::::::::::::::::::::::"+size);
					  
					  for(int i=0 ; i<size; i++){
						subList = (ArrayList) searchList.get(i);

						if(subList.size()>0){
							vo=new CodeDescVo();	
							vo.setValue(CommonFunction.checkNull(subList.get(0)).toString());
							vo.setDescription(CommonFunction.checkNull(subList.get(1)).toString());
							genericmasterList.add(vo);
						} 
					  }					  
				}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("paramDetail size is .................................."+genericmasterList.size());
			return genericmasterList;
		}
		@Override
		public ArrayList getGenericMasterInfoList(String genericKey,String parentValue) {
			ArrayList genericmasterList=new  ArrayList();
			  
			  ArrayList searchList=new ArrayList();
			  ArrayList subList=new ArrayList();
			  CodeDescVo vo=null;
				try{
		              String query="select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='"+genericKey+"' and parent_value = '"+parentValue+"'and rec_status ='A'";	
		              logger.info("getGenericMasterInfoList......Dao Impl"+query);
					  
					  searchList=ConnectionDAO.sqlSelect(query);
					  int size = searchList.size();
					  logger.info("searchList:::::::::::::::::::::::"+size);
					  
					  for(int i=0 ; i<size; i++){
						subList = (ArrayList) searchList.get(i);

						if(subList.size()>0){
							vo=new CodeDescVo();	
							vo.setValue(CommonFunction.checkNull(subList.get(0)).toString());
							vo.setDescription(CommonFunction.checkNull(subList.get(1)).toString());
							genericmasterList.add(vo);
						} 
					  }					  
				}catch(Exception e){
				e.printStackTrace();
			}
			logger.info("paramDetail size is .................................."+genericmasterList.size());
			return genericmasterList;
		}
}
