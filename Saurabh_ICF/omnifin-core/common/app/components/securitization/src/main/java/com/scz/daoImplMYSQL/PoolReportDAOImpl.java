package com.scz.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.scz.dao.PoolReportDAO;
import com.scz.vo.PoolCreationForCMVO;
import com.connect.ConnectionDAO;

public class PoolReportDAOImpl implements PoolReportDAO{
	
	private static final Logger logger = Logger.getLogger(PoolReportDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	
//	 public ArrayList generatePool(PoolCreationForCMVO poolvo)
//	 {
//		 logger.info("In generatePool() method of PoolReportDAOImpl");
//		 
//	
//			logger.info("In generatePool: ");
//			ArrayList mainlist=new ArrayList();
//			  ArrayList subList=new ArrayList();
//			
//			try{
//
//			 /* 
//			  String query="select POOL_ID,LOAN_ID,LOAN_PRODUCT,LOAN_SCHEME,LOAN_CUSTOMER_ID,LOAN_CUSTOMER_TYPE,LOAN_AMOUNT," +
//			  		" LOAN_CUSTOMER_CATEGORY,LOAN_CUSTOMER_CONSTITUTION,LOAN_ASSET_COST,LOAN_CUSTOMER_BUSINESS_SEGMENT," +
//			  		" LOAN_INDUSTRY,LOAN_SUB_INDUSTRY,LOAN_DPD_STRING,LOAN_DISBURSAL_DATE,LOAN_MATURITY_DATE,LOAN_DPD," +
//			  		" LOAN_TENURE,LOAN_BALANCE_TENURE,LOAN_INSTL_NUM,LOAN_ADV_EMI_NUM,LOAN_INT_RATE,LOAN_NPA_FLAG," +
//			  		" LOAN_DISBURSAL_STATUS,LOAN_ADV_EMI_AMOUNT,LOAN_EMI,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL," +
//			  		" LOAN_RECEIVED_PRINCIPAL,LOAN_OVERDUE_INSTL_NUM,LOAN_OVERDUE_AMOUNT,LOAN_BALANCE_INSTL_NUM," +
//			  		" REC_STATUS,LOAN_BALANCE_INSTL_AMOUNT from cr_securitization_pool_dtl where POOL_ID='"+poolvo.getPoolID()+"'";
//            */
//				String query="select A.POOL_ID,A.LOAN_ID,CLD.LOAN_NO,A.LOAN_PRODUCT,A.LOAN_SCHEME,A.LOAN_CUSTOMER_ID," +
//		  		" A.LOAN_CUSTOMER_TYPE,A.LOAN_AMOUNT, A.LOAN_CUSTOMER_CATEGORY,A.LOAN_CUSTOMER_CONSTITUTION," +
//		  		" A.LOAN_ASSET_COST,A.LOAN_CUSTOMER_BUSINESS_SEGMENT, A.LOAN_INDUSTRY,I.INDUSTRY_DESC, " +
//		  		" SI.SUB_INDUSTRY_DESC, A.LOAN_SUB_INDUSTRY,A.LOAN_DPD_STRING,A.LOAN_DISBURSAL_DATE,A.LOAN_MATURITY_DATE," +
//		  		" A.LOAN_DPD,A.LOAN_TENURE,A.LOAN_BALANCE_TENURE,A.LOAN_INSTL_NUM,A.LOAN_ADV_EMI_NUM," +
//		  		" A.LOAN_INT_RATE,A.LOAN_NPA_FLAG, A.LOAN_DISBURSAL_STATUS,A.LOAN_ADV_EMI_AMOUNT,A.LOAN_EMI," +
//		  		" A.LOAN_BALANCE_PRINCIPAL,A.LOAN_OVERDUE_PRINCIPAL,A.LOAN_RECEIVED_PRINCIPAL," +
//		  		" A.LOAN_OVERDUE_INSTL_NUM,A.LOAN_OVERDUE_AMOUNT,A.LOAN_BALANCE_INSTL_NUM, " +
//		  		" A.REC_STATUS,A.LOAN_BALANCE_INSTL_AMOUNT "+
//		  		" from cr_securitization_pool_dtl A,com_industry_m I ,com_sub_industry_m SI,cr_loan_dtl CLD   " +
//		  		"  where I.INDUSTRY_ID=A.LOAN_INDUSTRY and  SI.sub_industry_id=A.LOAN_SUB_INDUSTRY AND A.LOAN_ID=CLD.LOAN_ID AND POOL_ID='"+poolvo.getPoolID()+"'";
//
//
//			 logger.info("In generatePool "+query);
//			 mainlist=ConnectionDAO.sqlSelect(query);
//			 logger.info("In generatePool,,,,,"+mainlist.size());
//			
//			 		
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//
//			return mainlist;
//		
//	 }

}
