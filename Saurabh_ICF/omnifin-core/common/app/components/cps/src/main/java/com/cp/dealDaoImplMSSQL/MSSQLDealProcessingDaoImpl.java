package com.cp.dealDaoImplMSSQL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.commonFunction.dao.RefreshFlagValueInsert;
import com.commonFunction.vo.RefreshFlagVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dealDao.DealProcessingDao;
import com.cp.vo.ApplicantTypeVO;
import com.cp.vo.ChargeVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.HeaderInfoVo;
import com.cp.vo.LinkCustomerVo;
import com.cp.vo.LoanDetailVo;

public class MSSQLDealProcessingDaoImpl implements DealProcessingDao 
{
	private static final Logger logger = Logger.getLogger(MSSQLDealProcessingDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	//change by sachin
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	//end by sachin
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	StringBuffer bufInsUpdSql = null;
	ArrayList qryList = null;
	
 	public ArrayList<Object> fetchDealDetail(CommonDealVo vo) {
 		ArrayList list=new ArrayList();
 		try
 		{
 			logger.info("vo.getStage(): "+vo.getStage());
 			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
 			String userName=ConnectionDAO.singleReturn(userNameQ);
 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
 			
 			userNameQ=null;
 			//logger.info("here branch id++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getBranchId());
 			logger.info("Date Format: "+dateFormat);
 			ArrayList header=null;
 			int count=0;
 			int startRecordIndex=0;
 			int endRecordIndex = no;
 			CommonDealVo fetchVo= (CommonDealVo) vo;
 			boolean appendSQL=false;
 			StringBuffer bufInsSql=new StringBuffer();
 			StringBuffer bufInsSqlTempCount = new StringBuffer();
 			StringBuilder dealId=new StringBuilder();
 			StringBuilder appNo=new StringBuilder();
 			StringBuilder appDate=new StringBuilder();
 			StringBuilder custName=new StringBuilder();
 			StringBuilder productId=new StringBuilder();
 			
 			 dealId.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim()));
 			 appNo.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationno())).trim()));
 			 appDate.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim()));
 			 custName.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername())).trim()));
 			 productId.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim()));
 					
 		
 		if(vo.getStage()!=null && !vo.getStage().equalsIgnoreCase("F"))
 		{
 			logger.info("select........");	
 			
 			bufInsSql.append("select distinct d.DEAL_ID,DEAL_NO,DEAL_APPLICATION_FORM_NO,");
 			bufInsSql.append(dbo);
 			bufInsSql.append("DATE_FORMAT(DEAL_DATE,'"+dateFormat+"') as DEAL_DATE,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=d.MAKER_ID) MAKER_ID ");
 			bufInsSql.append(" from cr_deal_dtl d");
 			bufInsSqlTempCount.append(" select distinct count(1) FROM (select distinct d.DEAL_ID,DEAL_NO,DEAL_APPLICATION_FORM_NO,");
 			bufInsSqlTempCount.append(dbo);
 			bufInsSqlTempCount.append("DATE_FORMAT(DEAL_DATE,'"+dateFormat+"'),deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC from cr_deal_dtl d ");
 			
 			bufInsSql.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
 			bufInsSqlTempCount.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
 			
 			bufInsSql.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
 			bufInsSqlTempCount.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
 			
 			bufInsSql.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
 			bufInsSqlTempCount.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
 			
 			bufInsSql.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");
 			bufInsSqlTempCount.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");
 						
 			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationno())).trim().equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
 			{
 		   	  bufInsSql.append("WHERE d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND   d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND d.DEAL_APPLICATION_FORM_NO='"+StringEscapeUtils.escapeSql(vo.getApplicationno()).trim()+"' AND ");
 		   	  bufInsSql.append(dbo);
 		   	  bufInsSql.append("date(d.DEAL_DATE) =");
 		   	  bufInsSql.append(dbo); 
 		   	  bufInsSql.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"'");
 		   	  bufInsSqlTempCount.append("WHERE d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND   d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND d.DEAL_APPLICATION_FORM_NO='"+StringEscapeUtils.escapeSql(vo.getApplicationno()).trim()+"' AND ");
 		   	  bufInsSqlTempCount.append(dbo);
 		   	  bufInsSqlTempCount.append("date(d.DEAL_DATE) =");
 		   	  bufInsSqlTempCount.append(dbo);
 		   	  bufInsSqlTempCount.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"'");
 			}
 			
 			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getApplicationno().equalsIgnoreCase("")))||((vo.getApplicationdate().equalsIgnoreCase("")))||((vo.getCustomername().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
 				appendSQL=true;
 			}
 			
 			if(appendSQL){
 				logger.info("In Where Clause");
 				bufInsSql.append(" WHERE d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND   d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' ");
 				bufInsSqlTempCount.append(" WHERE d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' ");
 			}
 			 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
  		        bufInsSql.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
  		        bufInsSqlTempCount.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
  		   	 appendSQL=true;
  		   	  
  		     }
  			 
  			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationno())).trim().equalsIgnoreCase("")))) {
  		   	  bufInsSql.append("AND d.DEAL_APPLICATION_FORM_NO='"+StringEscapeUtils.escapeSql(vo.getApplicationno()).trim()+"' ");
  		   	  bufInsSqlTempCount.append("AND d.DEAL_APPLICATION_FORM_NO='"+StringEscapeUtils.escapeSql(vo.getApplicationno()).trim()+"' ");
  		   	  appendSQL=true;
  		     }
  			
  			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase("")))) {
  		   	  bufInsSql.append("AND ");
  		   	  bufInsSql.append(dbo);
  		   	  bufInsSql.append("date(d.DEAL_DATE) =");
  		   	  bufInsSql.append(dbo);
  		   	  bufInsSql.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') ");
  		   	  bufInsSqlTempCount.append("AND ");
  		   	  bufInsSqlTempCount.append(dbo);
  		   	  bufInsSqlTempCount.append("date(d.DEAL_DATE) =");
  		   	  bufInsSqlTempCount.append(dbo);
  		   	  bufInsSqlTempCount.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') ");
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
  			
  			if((!(vo.getLbxDealNo().equalsIgnoreCase("")))||(!(vo.getApplicationno().equalsIgnoreCase("")))||(!(vo.getApplicationdate().equalsIgnoreCase("")))||(!(vo.getCustomername().equalsIgnoreCase("")))||(!(vo.getLbxProductID().equalsIgnoreCase("")))||(!(vo.getLbxscheme().equalsIgnoreCase("")))||(!(vo.getStage().equalsIgnoreCase("")))){
  				bufInsSqlTempCount.append(") as b");
  				appendSQL=true;
  			}
  			
  			
  			 count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
  			 logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
//			if((dealId.toString().trim()==null && appNo.toString().trim()==null && appDate.toString().trim()==null && custName.toString().trim()==null && productId.toString().trim()==null) || (dealId.toString().trim().equalsIgnoreCase("") && appNo.toString().trim().equalsIgnoreCase("") && appDate.toString().trim().equalsIgnoreCase("") && custName.toString().trim().equalsIgnoreCase("") && productId.toString().trim().equalsIgnoreCase("")) || fetchVo.getCurrentPageLink()>1)
 // 			{
  			
  			 logger.info("current PAge Link no .................... "+fetchVo.getCurrentPageLink());
  			if(fetchVo.getCurrentPageLink()>1)
  			{
  				startRecordIndex = (fetchVo.getCurrentPageLink()-1)*no;
  				endRecordIndex = no;
  				 logger.info("startRecordIndex .................... "+startRecordIndex);
  				 logger.info("endRecordIndex .................... "+endRecordIndex);
  			}
  			bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
  		//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
//  			}
  			 logger.info("query : "+bufInsSql);
  		     header = ConnectionDAO.sqlSelect(bufInsSql.toString());
  		   //logger.info("here &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+vo.getUserid());
  						
  			for(int i=0;i<header.size();i++){
  			//	logger.info("header: "+header.size());
  				ArrayList header1=(ArrayList)header.get(i);
  				if(header1!=null && header1.size()>0)
  				{
  					
  					fetchVo = new CommonDealVo();
  			//		logger.info("vo.getStage(): "+vo.getStage());
  					if(vo.getStage().equalsIgnoreCase("F"))
  					{
  						fetchVo.setLbxDealNo("<a href=commonPageBehind.do?dealId="+(CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
  					}
  					else
  					{
  						fetchVo.setLbxDealNo("<a href=dealCapturing.do?method=leadEntryCapturing&dealId="+(CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
  					}
  					
  					fetchVo.setDealNo((CommonFunction.checkNull(header1.get(1))).trim());
  					fetchVo.setApplicationno((CommonFunction.checkNull(header1.get(2))).trim());
  					fetchVo.setApplicationdate((CommonFunction.checkNull(header1.get(3))).trim());
  					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(4))).trim());
  			//		logger.info("Customer First Name: "+(CommonFunction.checkNull(header1.get(4))).trim());
  					fetchVo.setProduct((CommonFunction.checkNull(header1.get(5))).trim());
  					fetchVo.setScheme((CommonFunction.checkNull(header1.get(6))).trim());
  					fetchVo.setReportingToUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(7))).trim());
  					//fetchVo.setUserId(vo.getUserId());
  					fetchVo.setTotalRecordSize(count);
  					list.add(fetchVo);
  				}
  			}
 		}
 		else if(vo.getStage()!=null && vo.getStage().equalsIgnoreCase("F"))
 		{
 			
 			   logger.info("Deal Id: "+vo.getLbxDealNo());
				logger.info("User Id: "+vo.getReportingToUserId());
				
	 			bufInsSql.append(" select distinct d.DEAL_ID,DEAL_NO,DEAL_APPLICATION_FORM_NO,");
	 			bufInsSql.append(dbo);
	 			bufInsSql.append("DATE_FORMAT(DEAL_DATE,'"+dateFormat+"') as DEAL_DATE,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=d.MAKER_ID) MAKER_ID ");
	 			bufInsSql.append(" from cr_deal_dtl d");
	 			bufInsSqlTempCount.append(" select distinct count(1) FROM (select distinct d.DEAL_ID,DEAL_NO,DEAL_APPLICATION_FORM_NO,");
	 			bufInsSqlTempCount.append(dbo);
	 			bufInsSqlTempCount.append("DATE_FORMAT(DEAL_DATE,'"+dateFormat+"') as DEAL_DATE,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC from cr_deal_dtl d ");
	 			
	 			bufInsSql.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
	 			bufInsSqlTempCount.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
	 			bufInsSql.append(" INNER join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
	 			bufInsSqlTempCount.append(" INNER join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
	 			bufInsSql.append(" INNER join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
	 			bufInsSqlTempCount.append(" INNER join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
	 			bufInsSql.append(" INNER join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");
	 			bufInsSqlTempCount.append(" INNER join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");
	 			bufInsSql.append(" INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED<>'0000-00-00 00:00:00' AND M.DEAL_FORWARDED='0000-00-00 00:00:00' AND DEAL_STAGE_ID='UNC' AND M.REC_STATUS='A',cr_user_approval_m ua ");
	 			bufInsSqlTempCount.append(" INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED<>'0000-00-00 00:00:00' AND M.DEAL_FORWARDED='0000-00-00 00:00:00' AND DEAL_STAGE_ID='UNC' AND M.REC_STATUS='A',cr_user_approval_m ua ");
 			 			
 			
 			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationno())).trim().equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
 			{
 		   	  bufInsSql.append("WHERE d.MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND ua.USER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' and n.DEAL_LOAN_AMOUNT>=ua.AMOUNT_FROM and n.DEAL_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='U' AND d.DEAL_CURRENT_APPROVAL_LEVEL=ua.LEVEL AND   d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND d.DEAL_APPLICATION_FORM_NO='"+StringEscapeUtils.escapeSql(vo.getApplicationno()).trim()+"' AND ");
 		   	  bufInsSql.append(dbo);
 		   	  bufInsSql.append("date(d.DEAL_DATE) =");
 		   	  bufInsSql.append(dbo);
 		   	  bufInsSql.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"'");
 		   	  bufInsSqlTempCount.append("WHERE d.MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND ua.USER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' and n.DEAL_LOAN_AMOUNT>=ua.AMOUNT_FROM and n.DEAL_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='U' AND d.DEAL_CURRENT_APPROVAL_LEVEL=ua.LEVEL AND   d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND d.DEAL_APPLICATION_FORM_NO='"+StringEscapeUtils.escapeSql(vo.getApplicationno()).trim()+"' AND ");
 		   	  bufInsSqlTempCount.append(dbo);
 		   	  bufInsSqlTempCount.append("date(d.DEAL_DATE) =");
 		   	  bufInsSqlTempCount.append(dbo);
 		   	  bufInsSqlTempCount.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"'");
 			}
 			
 			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getApplicationno().equalsIgnoreCase("")))||((vo.getApplicationdate().equalsIgnoreCase("")))||((vo.getCustomername().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
 				appendSQL=true;
 			}
 			
 			if(appendSQL){
 				logger.info("In Where Clause");
 				bufInsSql.append(" WHERE d.MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND ua.USER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' and n.DEAL_LOAN_AMOUNT>=ua.AMOUNT_FROM and n.DEAL_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='U'AND d.DEAL_CURRENT_APPROVAL_LEVEL=ua.LEVEL AND   d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' ");
 				bufInsSqlTempCount.append(" WHERE d.MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND ua.USER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' and n.DEAL_LOAN_AMOUNT>=ua.AMOUNT_FROM and n.DEAL_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='U'AND d.DEAL_CURRENT_APPROVAL_LEVEL=ua.LEVEL AND d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' ");
 			}
 			
 			 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
  		        bufInsSql.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
  		        bufInsSqlTempCount.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
  		   	 appendSQL=true;
  		   	  
  		     }
  			 
  			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationno())).trim().equalsIgnoreCase("")))) {
  		   	  bufInsSql.append("AND d.DEAL_APPLICATION_FORM_NO='"+StringEscapeUtils.escapeSql(vo.getApplicationno()).trim()+"' ");
  		   	  bufInsSqlTempCount.append("AND d.DEAL_APPLICATION_FORM_NO='"+StringEscapeUtils.escapeSql(vo.getApplicationno()).trim()+"' ");
  		   	  appendSQL=true;
  		     }
  			
  			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase("")))) {
  		   	  bufInsSql.append("AND ");
  		   	  bufInsSql.append(dbo);
  		   	  bufInsSql.append("date(d.DEAL_DATE) =");
  		   	  bufInsSql.append(dbo);
  		   	  bufInsSql.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') ");
  		   	  bufInsSqlTempCount.append("AND ");
  		   	  bufInsSqlTempCount.append(dbo);
  		   	  bufInsSqlTempCount.append("date(d.DEAL_DATE) =");
  		   	  bufInsSqlTempCount.append(dbo);
  		   	  bufInsSqlTempCount.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') ");
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
  			
  			if((!(vo.getLbxDealNo().equalsIgnoreCase("")))||(!(vo.getApplicationno().equalsIgnoreCase("")))||(!(vo.getApplicationdate().equalsIgnoreCase("")))||(!(vo.getCustomername().equalsIgnoreCase("")))||(!(vo.getLbxProductID().equalsIgnoreCase("")))||(!(vo.getLbxscheme().equalsIgnoreCase("")))||(!(vo.getStage().equalsIgnoreCase("")))){
  				bufInsSqlTempCount.append(") as b");
  				appendSQL=true;
  			}
  			
  			
  			 count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
  			 logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
 // 			if((dealId.toString().trim()==null && appNo.toString().trim()==null && appDate.toString().trim()==null && custName.toString().trim()==null && productId.toString().trim()==null) || (dealId.toString().trim().equalsIgnoreCase("") && appNo.toString().trim().equalsIgnoreCase("") && appDate.toString().trim().equalsIgnoreCase("") && custName.toString().trim().equalsIgnoreCase("") && productId.toString().trim().equalsIgnoreCase("")) || fetchVo.getCurrentPageLink()>1)
 // 			{
  			
  			 logger.info("current PAge Link no .................... "+fetchVo.getCurrentPageLink());
  			if(fetchVo.getCurrentPageLink()>1)
  			{
  				startRecordIndex = (fetchVo.getCurrentPageLink()-1)*no;
  				endRecordIndex = no;
  				 logger.info("startRecordIndex .................... "+startRecordIndex);
  				 logger.info("endRecordIndex .................... "+endRecordIndex);
  			}
  			bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
  		//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
 // 			}
  			 logger.info("query : "+bufInsSql);
  		     header = ConnectionDAO.sqlSelect(bufInsSql.toString());
  		     
  		     bufInsSql=null;
  		     bufInsSqlTempCount=null;
  		     
  		   //logger.info("here &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+vo.getUserid());
  						
  			for(int i=0;i<header.size();i++){
  			//	logger.info("header: "+header.size());
  				ArrayList header1=(ArrayList)header.get(i);
  				if(header1!=null && header1.size()>0)
  				{
  					
  					fetchVo = new CommonDealVo();
  			//		logger.info("vo.getStage(): "+vo.getStage());
  					if(vo.getStage().equalsIgnoreCase("F"))
  					{
  						fetchVo.setLbxDealNo("<a href=commonPageBehind.do?dealId="+(CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
  					}
  					else
  					{
  						fetchVo.setLbxDealNo("<a href=dealCapturing.do?method=leadEntryCapturing&dealId="+(CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
  					}
  					
  					fetchVo.setDealNo((CommonFunction.checkNull(header1.get(1))).trim());
  					fetchVo.setApplicationno((CommonFunction.checkNull(header1.get(2))).trim());
  					fetchVo.setApplicationdate((CommonFunction.checkNull(header1.get(3))).trim());
  					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(4))).trim());
  			//		logger.info("Customer First Name: "+(CommonFunction.checkNull(header1.get(4))).trim());
  					fetchVo.setProduct((CommonFunction.checkNull(header1.get(5))).trim());
  					fetchVo.setScheme((CommonFunction.checkNull(header1.get(6))).trim());
  					fetchVo.setReportingToUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(7))).trim());
  					//fetchVo.setUserId(vo.getUserId());
  					fetchVo.setTotalRecordSize(count);
  					list.add(fetchVo);
  				}
  			}
 		//}
 	}
 		
 			 logger.info("getTotalRecordSize : "+fetchVo.getTotalRecordSize());
 			 dealId=null;
 			 appNo=null;
 			 appDate=null;
 			 custName=null;
 			 productId=null;
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 		}
 		
 		 logger.info("Detail List when searchList is : "+list);
 		
 		return list;
 	}



	public ArrayList getDealHeader(String id)
	{
		ArrayList list=new ArrayList();
		try
		{
			//change by sachin
			StringBuilder query=new StringBuilder();
			 query.append(" select TOP 1 ");
			 query.append("d.deal_id, deal_no,d.DEAL_CUSTOMER_ID,deal.CUSTOMER_NAME,");
			 query.append(dbo);
			 query.append("DATE_FORMAT(deal_date,'"+dateFormat+"'),p.PRODUCT_DESC,s.SCHEME_DESC,l.DEAL_PRODUCT_CATEGORY,d.REC_STATUS  from cr_deal_dtl d ");
			 query.append(" left join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID");
			 query.append(" left join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID");
			 query.append(" left join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID");
			 query.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID");
			 query.append(" where d.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id)).trim()+" ");

	//end by sachin		
			logger.info("getDealHeader Query: "+query.toString());
			
			HeaderInfoVo vo= null;
			ArrayList header = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			for(int i=0;i<header.size();i++){
				logger.info("header"+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					vo = new HeaderInfoVo();
					vo.setDealId((CommonFunction.checkNull(header1.get(0))).trim());
					vo.setDealNo((CommonFunction.checkNull(header1.get(1))).trim());
					vo.setDealCustomerId((CommonFunction.checkNull(header1.get(2))).trim());
					vo.setDealCustomerName((CommonFunction.checkNull(header1.get(3))).trim());
					vo.setDealDate((CommonFunction.checkNull(header1.get(4))).trim());
					vo.setDealProduct((CommonFunction.checkNull(header1.get(5))).trim());
					vo.setDealScheme((CommonFunction.checkNull(header1.get(6))).trim());
					vo.setDealProductCat((CommonFunction.checkNull(header1.get(7))).trim());
					list.add(vo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}


	public ArrayList<Object> getsourceTypeList() {

		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT M.VALUE,M.DESCRIPTION FROM generic_master M,generic_master_keys K WHERE K.GENERIC_KEY='SOURCE_TYPE' AND K.GENERIC_KEY=M.GENERIC_KEY and m.REC_STATUS='A'");
		logger.info("getsourceTypeList"+query);
		CodeDescVo vo = null;
		ArrayList source = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getsourceTypeList"+source.size());
		
		query=null;
		
		for(int i=0;i<source.size();i++){

			logger.info("getsourceTypeList"+source.get(i).toString());
			ArrayList subsource=(ArrayList)source.get(i);
			if(subsource.size()>0)
			{
				logger.info("getsourceTypeList"+subsource.size());
				vo = new CodeDescVo();
				vo.setId((CommonFunction.checkNull(subsource.get(0))).trim());
				vo.setName((CommonFunction.checkNull(subsource.get(1))).trim());
				list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
		}

	
	public ArrayList<Object> getDealCatList() {
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query =new StringBuilder();
		 query.append("SELECT M.VALUE,M.DESCRIPTION FROM generic_master M,generic_master_keys K WHERE K.GENERIC_KEY='DEAL_CATEGORY' AND K.GENERIC_KEY=M.GENERIC_KEY and m.REC_STATUS='A'");
		logger.info("getDealCatList"+query);
		CodeDescVo vo = null;
		ArrayList source = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getDealCatList"+source.size());
		query=null;
		
		for(int i=0;i<source.size();i++){

			logger.info("getDealCatList"+source.get(i).toString());
			ArrayList subsource=(ArrayList)source.get(i);
			if(subsource.size()>0)
			{
				logger.info("getDealCatList"+subsource.size());
				vo = new CodeDescVo();
				vo.setId((CommonFunction.checkNull(subsource.get(0))).trim());
				vo.setName((CommonFunction.checkNull(subsource.get(1))).trim());
				list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}


	public String checkStage(String dealId) {

		String status="";
//		String commAddr="";
//		String gAddr="";
		StringBuilder commAddr=new StringBuilder();
		StringBuilder gAddr=new StringBuilder();
		
		ArrayList gCheckAddr=new ArrayList();
		//String coAddr="";
		StringBuilder coAddr=new StringBuilder();
		ArrayList coCheckAddr=new ArrayList();
		StringBuilder guram=new StringBuilder();
		StringBuilder tenure=new StringBuilder();
		StringBuilder sdAmount=new StringBuilder();
		StringBuilder checkSDAm=new StringBuilder();
//		String guram="";
//		String tenure="";
//		String sdAmount="";
//		String checkSDAm="";
		StringBuilder q1=new StringBuilder();
		StringBuilder q2=new StringBuilder();
		StringBuilder q3=new StringBuilder();
		StringBuilder q4=new StringBuilder();
		StringBuilder q5=new StringBuilder();
		StringBuilder q8=new StringBuilder();
		StringBuilder q9=new StringBuilder();
		StringBuilder q10=new StringBuilder();
		StringBuilder q11=new StringBuilder();
		 q1.append("select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		 q2.append("select DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		 q3.append("select DEAL_ID from cr_deal_collateral_m where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		 q4.append("select DEAL_CHARGE_DTL_ID from cr_deal_txncharges_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		 q5.append("select DOC_STATUS from cr_document_dtl where TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and ENTITY_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and DOC_MANDATORY='Y' and STAGE_ID='PRS' AND TXN_TYPE='DC'");
		//String q6="select CUSTOMER_TYPE from cr_deal_customer_role,cr_deal_customer_m where DEAL_CUSTOMER_ID=CUSTOMER_ID and DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim();
		 q9.append("select DOC_STATUS from cr_document_dtl where TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and ENTITY_ID in (select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+") and DOC_MANDATORY='Y' and STAGE_ID='PRS' AND TXN_TYPE='DC'");
		 q10.append("select DOC_STATUS from cr_document_dtl where TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and ENTITY_ID in (select ASSETID from cr_deal_collateral_m where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+")  and DOC_MANDATORY='Y' and STAGE_ID='PRS' AND TXN_TYPE='DC'");
		 q8.append("SELECT GUARANTEE_AMOUNT FROM cr_deal_customer_role WHERE DEAL_CUSTOMER_ROLE_TYPE='GUARANTOR' AND DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		 q11.append("select SD_TENURE from cr_deal_sd_m s,cr_deal_loan_dtl d where d.DEAL_ID=s.DEAL_ID and s.SD_TENURE<=d.DEAL_TENURE"+
					" and d.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		 
		 StringBuilder checkSDQ=new StringBuilder();
		 checkSDQ.append("SELECT");
		 checkSDQ.append("ISNULL(S.SD_AMOUNT,0) FROM cr_deal_sd_m s  WHERE  S.DEAL_ID ="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		checkSDAm.append(ConnectionDAO.singleReturn(checkSDQ.toString()));
		logger.info("sdAmount: "+sdAmount+" Query: "+checkSDQ.toString());
		
		StringBuilder q12=new StringBuilder();
		 q12.append("SELECT");
		 q12.append("ISNULL(C.DEAL_CHARGE_FINAL_AMOUNT,0) FROM cr_deal_txncharges_dtl c WHERE  c.DEAL_CHARGE_CODE='103' AND c.DEAL_ID ="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		 logger.info("query..........q12 in checkStage"+q12.toString());
		 sdAmount.append(ConnectionDAO.singleReturn(q12.toString()));
		
		if(sdAmount!=null && !CommonFunction.checkNull(sdAmount).equalsIgnoreCase("0"))
		{
			if(!CommonFunction.checkNull(sdAmount).equalsIgnoreCase(CommonFunction.checkNull(checkSDAm)))
			{
				tenure.append(ConnectionDAO.singleReturn(q11.toString()));
				if(CommonFunction.checkNull(tenure).equalsIgnoreCase(""))
				{
					status="TE";
					return status;
				}
				else
				{
					status="SD";
					return status;
				}
				
			}
			logger.info("tenure: "+tenure+" Query: "+q11.toString());
			logger.info("sdAmount: "+sdAmount+" Query: "+q12.toString());
		}
		
		
		
			guram.append(ConnectionDAO.singleReturn(q8.toString()));
		
		logger.info("amount: "+guram+" Query: "+q8.toString());
		if(guram!=null && guram.toString().equalsIgnoreCase("0.0000"))
		{
			status="GA";
			return status;
		}
		
		//String custType= ConnectionDAO.singleReturn(q6);
		//if(custType!=null && custType.equalsIgnoreCase("I"))
		//{
		StringBuilder q7=new StringBuilder();
			 q7.append("select count(*) FROM cr_deal_address_m where BPID=(SELECT DEAL_CUSTOMER_ID FROM cr_deal_dtl WHERE DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+") and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'");
		    commAddr.append(ConnectionDAO.singleReturn(q7.toString()));
		    StringBuilder q13=new StringBuilder();
			 q13.append("SELECT DEAL_CUSTOMER_ID FROM cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='GUARANTOR' AND DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			try {
				gCheckAddr=ConnectionDAO.sqlSelect(q13.toString());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info("q13: "+q13.toString()+"status "+gCheckAddr);
			
			StringBuilder q15=new StringBuilder();
			 q15.append("SELECT DEAL_CUSTOMER_ID FROM cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='COAPPL' AND DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			try {
				coCheckAddr=ConnectionDAO.sqlSelect(q15.toString());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info("q15: "+q15+"status "+gAddr);
			
			
			if(!CommonFunction.checkNull(gCheckAddr).equalsIgnoreCase("") && gCheckAddr.size()>0)
			{
				int ff=gCheckAddr.size();
				logger.info("Size: "+ff);
				for(int j=0;j<ff;j++)
				{
					ArrayList data=(ArrayList)gCheckAddr.get(j);
					int flag=data.size();
					for(int i=0;i<flag;i++)
					{
						StringBuilder q14=new StringBuilder();
						 q14.append("select count(*) FROM cr_deal_address_m where BPID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(data.get(i))).trim()+"' and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'");
						gAddr.append(ConnectionDAO.singleReturn(q14.toString()));
						logger.info("q14: "+q14+"status "+gAddr);
						
						q14=null;
										
					}
					if(gAddr.toString().equalsIgnoreCase("0"))
					{
						logger.info("Guarunter: ");
						status="CA";
						return status;
					}
					
				}
			}
		   if(!CommonFunction.checkNull(coCheckAddr).equalsIgnoreCase("") && coCheckAddr.size()>0)
			{
				int ff=coCheckAddr.size();
				logger.info("Size: "+ff);
			    for(int j=0;j<ff;j++)
				{
					ArrayList data=(ArrayList)coCheckAddr.get(j);
					int flag=data.size();
					for(int i=0;i<flag;i++)
					{		
						StringBuilder q16=new StringBuilder();
						 q16.append("select count(*) FROM cr_deal_address_m where BPID = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(data.get(i))).trim()+"' and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'");
						coAddr.append(ConnectionDAO.singleReturn(q16.toString()));
						logger.info("q16: "+q16.toString()+"status "+coAddr);
						
						q16=null;
					}
					if(coAddr.toString().equalsIgnoreCase("0"))
					{
						logger.info("Coapplicant: ");
						status="CA";
						return status;
					}
				}
			}
			
		//}
		//else if(custType!=null && custType.equalsIgnoreCase("C"))
		//{
			//String q7="select count(*) FROM cr_deal_address_m where BPID=(SELECT DEAL_CUSTOMER_ID FROM cr_deal_dtl WHERE DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+") and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'";
		   // commAddr = ConnectionDAO.singleReturn(q7);
			
		//}
		logger.info("Customer Detail Query: "+q1.toString());
		boolean cust = ConnectionDAO.checkStatus(q1.toString());
		logger.info("Customer Detail status: "+cust);
		logger.info("Loan Detail Query: "+q2.toString());
		boolean loanDetail = ConnectionDAO.checkStatus(q2.toString());
		logger.info("Loan Detail status: "+loanDetail);
		logger.info("Asset Detail Query: "+q3.toString());
		boolean asset = ConnectionDAO.checkStatus(q3.toString());
		logger.info("Asset Detail status: "+asset);
		logger.info("Charge Detail status: "+q4.toString());
		boolean charge = ConnectionDAO.checkStatus(q4.toString());
		logger.info("Charge Detail status: "+charge);
		ArrayList docs=null;
		try {
			logger.info("Query: "+q5.toString());
			docs = ConnectionDAO.sqlSelect(q5.toString());
			logger.info("Data: "+docs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList docs1=null;
		try {
			logger.info("Query: "+q9.toString());
			docs1 = ConnectionDAO.sqlSelect(q9.toString());
			logger.info("Data: "+docs1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList docs2=null;
		try {
			logger.info("Query: "+q10.toString());
			docs2 = ConnectionDAO.sqlSelect(q10.toString());
			logger.info("Data: "+docs2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			q1=null;
			q2=null;
			q3=null;
			q4=null;
			q5=null;
			q7=null;
			q8=null;
			q9=null;
			q10=null;
			q11=null;
			q12=null;
			q13=null;
			q15=null;
			 
			 gAddr=null;	
			 coAddr=null;
			
			 guram=null;
			 tenure=null;
			 sdAmount=null;
			 checkSDAm=null;
		}
		
		if(!cust)
		{
			status="CS";
			return status;
		}
		else if(!commAddr.toString().equalsIgnoreCase("1"))
		{
			logger.info("Applicant:        ");
			status="CA";
			return status;
		}
	  	else if(!loanDetail)
		{
			status="LD";
			return status;
		}
		else if(!asset)
		{
			StringBuilder prodFlagQ=new StringBuilder();
			 prodFlagQ.append("select count(*) from cr_deal_loan_dtl dl,cr_product_m p where dl.DEAL_PRODUCT=p.PRODUCT_ID and p.ASSET_FLAG='N' and DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"'");
			 StringBuilder prodFlag=new StringBuilder();
			  prodFlag.append(ConnectionDAO.singleReturn(prodFlagQ.toString()));
			logger.info("prodFlagQ: "+prodFlagQ.toString()+"status "+prodFlag);
			
			prodFlagQ=null;
			if(CommonFunction.checkNull(prodFlag).equalsIgnoreCase("0"))
			{
				status="AC";
			}
			
			prodFlag=null;
			return status;
		}
		else if(!charge)
		{
			status="CH";
			return status;
		}
	if(docs!=null || CommonFunction.checkNull(docs).equals(""))
	{
		logger.info("In if ");
		for(int j=0;j<docs.size();j++)
		{
			StringBuilder st=new StringBuilder();
		   st.append(docs.get(j).toString());
		  logger.info("in for status"+ st);
		  if(st.toString().equalsIgnoreCase("[P]")||st.toString().equalsIgnoreCase("[W]"))
			{
			    logger.info("In if in for");
				status="DC";
				break;
				//return status;
			}
			  else
			  {
				  logger.info("In else");
			  }
		  st=null;
		}
	}
	if(docs1!=null || CommonFunction.checkNull(docs1).equals(""))
	{
		logger.info("In if ");
		for(int j=0;j<docs1.size();j++)
		{
			StringBuilder st=new StringBuilder();
		   st.append(docs1.get(j).toString());
		  logger.info("in for status"+ st);
		  if(st.toString().equalsIgnoreCase("[P]")||st.toString().equalsIgnoreCase("[W]"))
			{
			    logger.info("In if in for");
				status="DC";
				break;
				//return status;
			}
			  else
			  {
				  logger.info("In else");
			  }
		  st=null;
		}
	}
	if(docs2!=null || CommonFunction.checkNull(docs2).equals(""))
	{
		logger.info("In if ");
		for(int j=0;j<docs2.size();j++)
		{
			StringBuilder st=new StringBuilder();
		   st.append(docs2.get(j).toString());
		  logger.info("in for status"+ st.toString());
		  if(st.toString().equalsIgnoreCase("[P]")||st.toString().equalsIgnoreCase("[W]"))
			{
			    logger.info("In if in for");
				status="DC";
				break;
				//return status;
			}
			  else
			  {
				  logger.info("In else");
			  }
		  st=null;
		}
	}
	commAddr=null;
	

		return status;
	}


	public String getCollateralDetails(String dealId) {
		StringBuilder query=new StringBuilder();						
		query.append("select count(1) from cr_deal_collateral_m a join cr_asset_collateral_m b on(b.ASSET_ID=a.ASSETID) where b.ASSET_TYPE='ASSET' and b.ASSET_COLLATERAL_CLASS='VEHICLE' and a.DEAL_ID="+dealId.trim());
		logger.info("In execute()  query   :   "+ query.toString());
		String ct=ConnectionDAO.singleReturn(query.toString());
		return ct;
	}


	public boolean stageForward(String dealId) {

			ArrayList qryList = null;
			logger.info("In stageForward.......Update mode "+dealId);
			qryList=new ArrayList();
			StringBuilder query1=new StringBuilder();
			StringBuilder query2=new StringBuilder();
			StringBuilder query3=new StringBuilder();
			StringBuilder query4=new StringBuilder();
			StringBuilder query5=new StringBuilder();
			StringBuilder query6=new StringBuilder();
			StringBuilder query7=new StringBuilder();
			
			 query1.append("update cr_deal_dtl set REC_STATUS='F' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 logger.info("query1........stageForward"+query1.toString());
			 query2.append("update cr_deal_customer_role set STATUS='F' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 logger.info("query2........stageForward"+query2.toString());
			 query3.append("update cr_deal_buyer_supplier_dtl set REC_STATUS='F' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 logger.info("query3........stageForward"+query3.toString());
			 query4.append("update cr_deal_loan_dtl set REC_STATUS='F' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 logger.info("query4........stageForward"+query4.toString());
			 query5.append("update cr_deal_collateral_m set REC_STATUS='F' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 logger.info("query5........stageForward"+query5.toString());			 
			 query6.append("update cr_deal_txncharges_dtl set REC_STATUS='F' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 logger.info("query6........stageForward"+query6.toString());
			 query7.append("update cr_document_dtl set REC_STATUS='F' where TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 logger.info("query7........stageForward"+query7.toString());


	        qryList.add(query1);
	        qryList.add(query2);
	        qryList.add(query3);
	        qryList.add(query4);
	        qryList.add(query5);
	        qryList.add(query6);
	        qryList.add(query7);



			boolean status=false;

			try {
				status = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger.info("In stageForward......................status= "+status);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				query1=null;
				query2=null;
				query3=null;
				query4=null;
				query5=null;
				query6=null;
				query7=null;
			}
			return status;
		}


	@Override
	public String getRefreshFlag(String dealId) {
		String sqlQuery="SELECT REFRESH_FLAG FROM cr_deal_dtl WHERE DEAL_ID='"+dealId+"'";
		logger.info("getRefreshFlag........."+sqlQuery);
		String refreshFlagValue = ConnectionDAO.singleReturn(sqlQuery);
		return refreshFlagValue;
	}


	public String moveFromGCD(String customerId,String applType,String dealId,String tableStatus,String userId,String bDate)
	{
		logger.info("In moveFromGCD");		
		//CallableStatement cst=null;
		int statusProc=0;
		String appQ="";
		String procval="";
		boolean checkApp=false;
		if(applType!=null && applType.equalsIgnoreCase("PRAPPL"))
		{
			    appQ ="select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID="+dealId+" and DEAL_CUSTOMER_ID is not NULL";
				logger.info("In Cr_deal_dtl. query....................."+appQ);
				checkApp=ConnectionDAO.checkStatus(appQ);
		}		
		String q="select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ID="+customerId+" and DEAL_ID="+dealId;		
		logger.info("Query: "+q);		
        boolean exist=ConnectionDAO.checkStatus(q);	    
        if(exist || checkApp)
            	statusProc=0;
        else
        {
        	ArrayList<Object> in =new ArrayList<Object>();
 			ArrayList<Object> out =new ArrayList<Object>();
 			ArrayList outMessages = new ArrayList();
 			String s1="";
 			String s2="";
 			try 
        	{
        	    logger.info("Applicant Type in moveFromGCD:== "+applType+" customerId:  "+customerId+" tableStatus:  "+tableStatus+" userId: "+userId+" bDate:  "+bDate);
        	    logger.info("Gcd_Customer_Link Procedure------>");
        	    in.add(dealId);
        	    in.add(customerId);
        	    in.add(applType);
        	    in.add(tableStatus);
        	    in.add(userId);
        	    String date=CommonFunction.changeFormat(bDate);
        	    if(date != null)
        	    	in.add(date);
        	    out.add(s1);
        	    out.add(s2);
        	    logger.info("Gcd_Customer_Link("+in.toString()+","+out.toString()+")");
        	    outMessages=(ArrayList) ConnectionDAO.callSP("Gcd_Customer_Link",in,out);
        	    s1=CommonFunction.checkNull(outMessages.get(0));
        	    s2=CommonFunction.checkNull(outMessages.get(1));
        	    logger.info("s1: "+s1);
			    logger.info("s2: "+s2);
			    if(s1.equalsIgnoreCase("S"))
			       	statusProc=1;
			   	procval=s2;	
        	}
 			catch(Exception e)
        	{
 				logger.info("Exception In approve: "+e);
        	}
 			finally
 			{
 				in=null;
 	 			out=null;
 	 			outMessages=null; 
			}	
		}
       	return procval;	
	 }


	public String approve(String id,String cusType,String mvmtBy,String pageInfo,String dealId) 
	{
		    logger.info("in approve() of CorpotateDAOImpl");
			String s1="";
			String s2="";
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			try{

				in.add(id);
	     	    in.add(cusType);
	     	    in.add(mvmtBy);
	     	    out.add(s1);
	     	    out.add(s2);
				logger.info("Gcd_Approve("+in.toString()+"+"+out.toString()+")");
			    outMessages=(ArrayList) ConnectionDAO.callSP("Gcd_Approve",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
	    	    s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("out parameter in Apporve S1  :  "+s1);
				logger.info("out parameter in Apporve S2  :  "+s2);	

				
			}	
			catch(Exception e){
				e.printStackTrace();
			}
			
			
			return s2;	
		}


	public boolean deleteroleList(String[] roleId,String[] applType,String dealId) 
	{
		logger.info("deleteroleList() OF CorpotateDAOImpl");
	    boolean status=false;
		try
		{			
			ArrayList qryList = new ArrayList();
			for(int k=0;k<roleId.length;k++)
			{				 
				String query2="delete from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID=?";
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				if(CommonFunction.checkNull(roleId[k]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(roleId[k])).trim());
				insertPrepStmtObject.setSql(query2.toString());
				logger.info("deleteroleList() OF CorpotateDAOImpl query   :   " +insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				String q1="select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim();
				logger.info("In query : ="+q1);
				String id=CommonFunction.checkNull(ConnectionDAO.singleReturn(q1));
				logger.info("Customer ID : ="+id);				
				if(!id.equalsIgnoreCase(""))
				{			
					String q2="select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_CUSTOMER_ID="+id;
					logger.info("q2 "+q2);
					String idInDeal=CommonFunction.checkNull(ConnectionDAO.singleReturn(q2));
					logger.info("Customer ID : ="+idInDeal);				
					if(idInDeal!=null && !idInDeal.equalsIgnoreCase(""))
					{
						String query1="update cr_deal_dtl set DEAL_CUSTOMER_ID=?,DEAL_CUSTOMER_TYPE=?,DEAL_EXISTING_CUSTOMER=? WHERE DEAL_ID=?";
						PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();
		        	   	insertPrepStmtObject2.addNull();
		        	   	insertPrepStmtObject2.addNull();
		        	   	insertPrepStmtObject2.addNull();
		        	   	if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
		        	   		insertPrepStmtObject2.addNull();
		        	   	else
		        	   		insertPrepStmtObject2.addString((CommonFunction.checkNull(dealId)).trim());
		        	   	insertPrepStmtObject2.setSql(query1.toString());
		        	   	logger.info("update this query  :  " +insertPrepStmtObject2.printQuery());
		        	   	qryList.add(insertPrepStmtObject2);
					}
				}
			}
			status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Status of Updation is   ::  "+status);
			}
			catch(Exception e)
			{e.printStackTrace();}
			return status;
	}
	
	
	public boolean deleteCustomerDocs(String[] roleId, String[] applType,
			String dealId) {
		logger.info("deleteApplDocs for deleteApplDocs....roleId.."+roleId+" dealId "+dealId);
		boolean status = false;
		ArrayList qryList=null;
		StringBuffer bufInsUpdSql=null;
		
    	try
    	{
     	qryList = new ArrayList();
    	bufInsUpdSql = new StringBuffer();
	
		for(int k=0;k<roleId.length;k++)
		{
			
			String deleteChatge="delete from cr_document_dtl where TXN_TYPE='DC' AND STAGE_ID='PRS' AND DOC_TYPE=(select DEAL_CUSTOMER_ROLE_TYPE from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" ) AND  TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and ENTITY_ID=(select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()+" )";
			logger.info("delete query:deletedocs "+deleteChatge);
			qryList.add(deleteChatge);
		
		}
		            
		status=ConnectionDAO.sqlInsUpdDelete(qryList);
		logger.info("Status of Deletion is ="+status);
	    }
    	catch(Exception e)
    	{
		e.printStackTrace();
	}
	return status;
	}
	

	public ArrayList<Object> searchApplicant(LinkCustomerVo vo) 
	{
		logger.info("In searchApplicant");

		 ArrayList<Object> list=new ArrayList<Object>();
	 	ArrayList<Object> deatilList=new ArrayList<Object>();
		 LinkCustomerVo appl=null;
		 
		 StringBuilder tableName=new StringBuilder();
		 String loanNo="";
		 tableName.append("cr_deal_customer_m");
		 if(vo.getLoanNO()!=null)
		 	 loanNo=vo.getLoanNO().trim();
		 
			 if(vo.getLoanNO().equalsIgnoreCase(""))
				 tableName.append("cr_deal_customer_m");			 
			 else
				 tableName.append("gcd_customer_m");
		 
//		 if((vo.getStatus()!=null) && (!vo.getStatus().equalsIgnoreCase("")) && vo.getStatus().equalsIgnoreCase("C"))
//		 {
//			
//			 tableName=new StringBuilder();
//			 tableName.append("cr_deal_customer_m");
//		 }
	  try
	  {
		   
			boolean appendSQL=false;
			StringBuffer bufInsSql=new StringBuffer();
			if(vo.getLoanNO().equalsIgnoreCase(""))
				bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,case when CUSTOMER_TYPE='I' then 'INDIVIDUAL' else 'CORPORATE' end as CUSTOMER_TYPE,DEAL_APPLICATION_FORM_NO,loan_no from cr_deal_customer_m left outer join cr_deal_dtl on(cr_deal_dtl.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID) left outer join cr_loan_dtl on(cr_loan_dtl.LOAN_DEAL_ID=cr_deal_dtl.DEAL_ID) where true ");
			else
				bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,case when CUSTOMER_TYPE='I' then 'INDIVIDUAL' else 'CORPORATE' end as CUSTOMER_TYPE,LOAN_REFERENCE_NO,loan_no from gcd_customer_m left outer join cr_loan_dtl on(cr_loan_dtl.LOAN_CUSTOMER_ID=gcd_customer_m.CUSTOMER_ID) where true ");
			
			if(!vo.getLbxcustomerId().trim().equalsIgnoreCase(""))
			   bufInsSql.append(" and CUSTOMER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxcustomerId()).trim()+"'");
			if(!vo.getCustomerName().trim().equalsIgnoreCase(""))
				bufInsSql.append(" and CUSTOMER_NAME like '%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%'");
			if(!vo.getPanNo().trim().equalsIgnoreCase(""))
			   	bufInsSql.append(" and CUSTMER_PAN='"+StringEscapeUtils.escapeSql(vo.getPanNo()).trim()+"'");
			if(!vo.getRegistrationNo().trim().equalsIgnoreCase(""))
			   	bufInsSql.append(" and CUSTOMER_REGISTRATION_NO='"+StringEscapeUtils.escapeSql(vo.getRegistrationNo()).trim()+"'");
			if(vo.getLoanNO().equalsIgnoreCase(""))
			{
				if(!vo.getAppFormNo().trim().equalsIgnoreCase(""))
					bufInsSql.append(" and DEAL_APPLICATION_FORM_NO ='"+StringEscapeUtils.escapeSql(vo.getAppFormNo()).trim()+"'");
			}
			else
			{
				if(!vo.getLoanNO().trim().equalsIgnoreCase(""))
					bufInsSql.append(" and cr_loan_dtl.loan_id ='"+StringEscapeUtils.escapeSql(vo.getLoanID()).trim()+"'");
				if(!vo.getAppFormNo().trim().equalsIgnoreCase(""))
					bufInsSql.append(" and LOAN_REFERENCE_NO ='"+StringEscapeUtils.escapeSql(vo.getAppFormNo()).trim()+"'");
			}
			
//			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxcustomerId()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPanNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRegistrationNo()).trim()).equalsIgnoreCase(""))))
//			{
//		   	  bufInsSql.append("WHERE CUSTOMER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxcustomerId()).trim()+"' AND CUSTOMER_NAME like '%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND CUSTMER_PAN ='"+StringEscapeUtils.escapeSql(vo.getPanNo()).trim()+"'AND CUSTOMER_REGISTRATION_NO='"+StringEscapeUtils.escapeSql(vo.getRegistrationNo()).trim());
//		   }
//			
//			if((vo.getLbxcustomerId().equalsIgnoreCase(""))||(vo.getCustomerName().equalsIgnoreCase(""))||(vo.getPanNo().equalsIgnoreCase(""))||(vo.getRegistrationNo().equalsIgnoreCase(""))){
//				appendSQL=true;
//			}
//			
//			if(appendSQL){
//				logger.info("In Where Clause");
//				bufInsSql.append(" WHERE ");
//		   }
	//
//			 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxcustomerId()).trim()).equalsIgnoreCase("")))) {
//		        bufInsSql.append(" CUSTOMER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxcustomerId()).trim()+"' AND");
//		   	 appendSQL=true;
//		   	  
//		     }
//			 
//			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).equalsIgnoreCase("")))) {
//		   	  bufInsSql.append(" CUSTOMER_NAME like '%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND");
//		   	  appendSQL=true;
//		     }
//			
//			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPanNo()).trim()).equalsIgnoreCase("")))) {
//		   	  bufInsSql.append(" CUSTMER_PAN='"+StringEscapeUtils.escapeSql(vo.getPanNo()).trim()+"' AND");
//		   	  appendSQL=true;
//		     }
//			
//			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRegistrationNo()).trim()).equalsIgnoreCase("")))) {
//		   	  bufInsSql.append(" CUSTOMER_REGISTRATION_NO='"+StringEscapeUtils.escapeSql(vo.getRegistrationNo()).trim()+"' AND");
//		   	  appendSQL=true;
//		     }
	//logger.info("In appendSQL true---- "+appendSQL);
		
//		if(appendSQL)
//		{
//			logger.info("In appendSQL true---- ");
//			
//		StringBuffer tmp =	new StringBuffer();
//			  tmp.append(bufInsSql.toString());
//	     logger.info("In searchApplicant() ## tmp ## "+tmp);
//	     logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
//	     if(tmp.lastIndexOf("AND") == (tmp.length()-3))
//	     { 
//	     logger.info("In appendSQL true----  in check index Of");
//	     String tmp1 = (tmp).substring(0,(tmp.length()-4));
//	     /*tmp=new StringBuffer();*/
//	     tmp.append((tmp).substring(0,(tmp.length()-4)));
//	     logger.info("search Query...tmp."+tmp);
//	     deatilList = ConnectionDAO.sqlSelect(tmp1.toString());
//	      }else
//	      {
//	     	  logger.info("search Query...tmp."+tmp);
//	     	deatilList = ConnectionDAO.sqlSelect(tmp.toString()); 
//	       }
		
		 logger.info("searchApplicant() of CreditProcessingDAOImpl Query : "+bufInsSql.toString());
		  deatilList= ConnectionDAO.sqlSelect(bufInsSql.toString()); 
	     bufInsSql=null;
	     if(deatilList.size()>0){
	     for(int i=0;i<deatilList.size();i++){
				logger.info("getApprovalData...FOR loop "+CommonFunction.checkNull(deatilList.get(i)).toString());
				ArrayList data=(ArrayList)deatilList.get(i);
				if(data.size()>0)	{
					appl = new LinkCustomerVo();
					//vo = new HeaderInfoVo();
					appl.setCustomerId((CommonFunction.checkNull(data.get(0))).trim());
					
					//if(data.get(1)!=null && !data.get(1).equals(""))
				//	{
					//	logger.info("Customer Name: "+CommonFunction.checkNull(data.get(1)).toString());
					///	appl.setCustomerName((CommonFunction.checkNull(data.get(1))).trim());
					//}
					//else if(data.get(2)!=null && !data.get(2).equals(""))
					//{
					//	logger.info("Customer First Name: "+CommonFunction.checkNull(data.get(1)).toString());
						appl.setCustomerName((CommonFunction.checkNull(data.get(1))).trim());
						//appl.setCustomerName((CommonFunction.checkNull(data.get(1))).trim());
						//vo.setDealCustomerName((CommonFunction.checkNull(data.get(1))).trim());
					//	logger.info("Customer First Name: "+(CommonFunction.checkNull(data.get(1))));
					//}
						
					appl.setConstitution((CommonFunction.checkNull(data.get(2))).trim());
					appl.setBusinessSegment((CommonFunction.checkNull(data.get(3))).trim());
					appl.setCustType((CommonFunction.checkNull(data.get(4))).trim());
					appl.setAppFormNo((CommonFunction.checkNull(data.get(5))).trim());
					appl.setLoanNO((CommonFunction.checkNull(data.get(6))).trim());				
					list.add(appl);
				}
	     }
		}
		bufInsSql=null;
	  }
	  
	     catch(Exception e){
			e.printStackTrace();
		}
	     finally{
	    	 tableName=null;
	     }
		
		
		return list;
	}


	public ArrayList<ApplicantTypeVO> getApplicantList()
	{
		ArrayList<ApplicantTypeVO> list=new ArrayList<ApplicantTypeVO>();
		ArrayList applicantList = null;
		ArrayList subList = null;
		try{
			StringBuffer query =	new StringBuffer();
		 query.append("SELECT VALUE,DESCRIPTION FROM generic_master  "
	        		+"where GENERIC_KEY='CUST_ROLE' and REC_STATUS='A'");
		 logger.info("getApplicantList........."+query);
		applicantList = ConnectionDAO.sqlSelect(query.toString());
		query=null;
		for(int i=0;i<applicantList.size();i++){
			subList=(ArrayList)applicantList.get(i);
			logger.info("In getApplicantList......sublist size: "+subList.size());
			if(subList.size()>0){
				ApplicantTypeVO av=new ApplicantTypeVO();
			    av.setApplicant_code((CommonFunction.checkNull(subList.get(0))).trim());
				av.setApplicant_desc((CommonFunction.checkNull(subList.get(1))).trim());
				list.add(av);
			}

		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}



	
	
	public ArrayList getAllLoanDetails(String dealLoanId) 
	{
		logger.info("dealLoanId: "+dealLoanId);
		ArrayList<Object> list=new ArrayList<Object>();
		try{
		//con= (Connection) ConnectionDAO.getConnection();
			StringBuilder query=new StringBuilder();
		 query.append("SELECT DEAL_PRODUCT,DEAL_SCHEME,");
		 query.append(dbo);
		 query.append("DATE_FORMAT(DEAL_DISBURSAL_DATE,'"+dateFormat+"'),DEAL_ASSET_COST," );
		 query.append(" DEAL_MARGIN_RATE,DEAL_MARGIN_AMOUNT,DEAL_LOAN_AMOUNT,DEAL_TENURE,DEAL_REPAYMENT_FREQ,DEAL_RATE_TYPE," );
		 query.append(" DEAL_RATE_METHOD,DEAL_BASE_RATE_TYPE,DEAL_BASE_RATE,DEAL_MARKUP,DEAL_FINAL_RATE,DEAL_REPAYMENT_TYPE,DEAL_INSTALLMENT_TYPE," );
		 query.append(" DEAL_INSTALLMENT_MODE,DEAL_REPAYMENT_MODE,DEAL_LOAN_PURPOSE,DEAL_LOAN_ID,p.PRODUCT_DESC,s.SCHEME_DESC,s.MIN_AMT_FIN,s.MAX_AMT_FIN,s.MIN_MARGIN_RATE,s.MAX_MARGIN_RATE,s.MIN_TENURE,s.MAX_TENURE,p.ASSET_FLAG,d.DEAL_NO_OF_INSTALLMENT,p.ASSET_FLAG,DEAL_SECTOR_TYPE,DEAL_ADVANCE_INSTL,");
		 query.append(dbo);
		 query.append("DATE_FORMAT(DEAL_REPAY_EFF_DATE,'"+dateFormat+"') ,");
		 query.append(dbo);
		 query.append("DATE_FORMAT(DEAL_MATURITY_DATE,'"+dateFormat+"'),DEAL_DUE_DAY,");
		 query.append(dbo);
		 query.append("DATE_FORMAT(NEXT_DUE_DATE,'"+dateFormat+"'),g.value,S.RATE_METHOD from cr_deal_loan_dtl d left join cr_product_m p on d.DEAL_PRODUCT=p.PRODUCT_ID left join cr_scheme_m s on d.DEAL_SCHEME=s.SCHEME_ID left join generic_master g on g.VALUE=d.LOAN_TYPE where d.DEAL_LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealLoanId)).trim());
		logger.info("getAllLoanDetails: "+query);
		//stmt=(Statement) con.createStatement();
		//rs=stmt.executeQuery(query);
		LoanDetailVo loanVo = null;
		ArrayList loandeatail = ConnectionDAO.sqlSelect(query.toString());
	//	logger.info("getAllLoanDetails"+loandeatail.size());
		
		query=null;
		
		if(loandeatail.size()>0){
		for(int i=0;i<loandeatail.size();i++){

			logger.info("getAllLoanDetails"+loandeatail.get(i).toString());
			ArrayList loandeatail1=(ArrayList)loandeatail.get(i);
			if(loandeatail1.size()>0)
			{
				logger.info("getAllLoanDetails"+loandeatail1.size());
				loanVo = new LoanDetailVo();
				loanVo.setLbxProductID((CommonFunction.checkNull(loandeatail1.get(0))).trim());
				loanVo.setLbxscheme((CommonFunction.checkNull(loandeatail1.get(1))).trim());
				loanVo.setDisbursalDate((CommonFunction.checkNull(loandeatail1.get(2))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(3)).equalsIgnoreCase(""))
				{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(3))).trim());
					loanVo.setAssetCost(myFormatter.format(reconNum));
				}
				if(!CommonFunction.checkNull(loandeatail1.get(4)).equalsIgnoreCase(""))
				{
					Number reconNumP =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(4))).trim());
					loanVo.setMarginPerc(myFormatter.format(reconNumP));
					loanVo.setLtvPerc(myFormatter.format(100-reconNumP.doubleValue()));
				}
				else
				{
					loanVo.setLtvPerc("100.00");
				}
				//loanVo.setMarginPerc((CommonFunction.checkNull(loandeatail1.get(4))).trim());
				//logger.info("Margin Amount: "+(CommonFunction.checkNull(loandeatail1.get(5))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(5)).equalsIgnoreCase(""))
				{
					Number reconNumA =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(5))).trim());
					loanVo.setMarginAmount(myFormatter.format(reconNumA));
				}
				//loanVo.setMarginAmount((CommonFunction.checkNull(loandeatail1.get(5))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(6)).equalsIgnoreCase(""))
				{
					Number reconNumL =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(6))).trim());
					loanVo.setRequestedLoanAmount(myFormatter.format(reconNumL));
				}
				//loanVo.setRequestedLoanAmount((CommonFunction.checkNull(loandeatail1.get(6))).trim());
				loanVo.setRequestedLoanTenure((CommonFunction.checkNull(loandeatail1.get(7))).trim());
				loanVo.setFrequency((CommonFunction.checkNull(loandeatail1.get(8))).trim());
				loanVo.setRateType((CommonFunction.checkNull(loandeatail1.get(9))).trim());
				loanVo.setType((CommonFunction.checkNull(loandeatail1.get(10))).trim());
				 logger.info("base rate type............... "+(CommonFunction.checkNull(loandeatail1.get(11))).trim());
				loanVo.setBaseRateType((CommonFunction.checkNull(loandeatail1.get(11))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(12)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(12))).trim());
					loanVo.setBaseRate(myFormatter.format(reconNumb));
				}
				//loanVo.setBaseRate((CommonFunction.checkNull(loandeatail1.get(12))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(13)).equalsIgnoreCase(""))
				{
					Number reconNumU =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(13))).trim());
					loanVo.setMarkUp(myFormatter.format(reconNumU));
				}
				//loanVo.setMarkUp((CommonFunction.checkNull(loandeatail1.get(13))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(14)).equalsIgnoreCase(""))
				{
					Number reconNumE =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(14))).trim());
					loanVo.setEffectiveRate(myFormatter.format(reconNumE));
				}
				//loanVo.setEffectiveRate((CommonFunction.checkNull(loandeatail1.get(14))).trim());

				loanVo.setRepaymentType((CommonFunction.checkNull(loandeatail1.get(15))).trim());
				if(CommonFunction.checkNull(loandeatail1.get(15)).trim().equalsIgnoreCase("I"))
				{
					loanVo.setShowRepaymentType("INSTALLMENT");
				}
				else if(CommonFunction.checkNull(loandeatail1.get(15)).trim().equalsIgnoreCase("N"))
				{
					loanVo.setShowRepaymentType("NON-INSTALLMENT");
				}
				loanVo.setInstallmentType((CommonFunction.checkNull(loandeatail1.get(16))).trim());
				loanVo.setInstMode((CommonFunction.checkNull(loandeatail1.get(17))).trim());
				loanVo.setPaymentMode((CommonFunction.checkNull(loandeatail1.get(18))).trim());
				loanVo.setLoanPurpose((CommonFunction.checkNull(loandeatail1.get(19))).trim());
				loanVo.setDealLoanId((CommonFunction.checkNull(loandeatail1.get(20))).trim());
				loanVo.setProduct((CommonFunction.checkNull(loandeatail1.get(21))).trim());
				loanVo.setScheme((CommonFunction.checkNull(loandeatail1.get(22))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(23)).equalsIgnoreCase(""))
				{
					Number reconNumf =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(23))).trim());
					loanVo.setMinFinanceAmount(myFormatter.format(reconNumf));
				}
               // loanVo.setMinFinanceAmount((CommonFunction.checkNull(loandeatail1.get(23))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(24)).equalsIgnoreCase(""))
				{
					Number reconNummf =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(24))).trim());
					loanVo.setMaxFinanceAmount(myFormatter.format(reconNummf));
				}
               // loanVo.setMaxFinanceAmount((CommonFunction.checkNull(loandeatail1.get(24))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(25)).equalsIgnoreCase(""))
				{
					Number reconNummr =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(25))).trim());
					loanVo.setMinMRate(myFormatter.format(reconNummr));
				}
                //loanVo.setMinMRate((CommonFunction.checkNull(loandeatail1.get(25))).trim());
				if(!CommonFunction.checkNull(loandeatail1.get(26)).equalsIgnoreCase(""))
				{
					Number reconNummm =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(26))).trim());
					loanVo.setMaxMRate(myFormatter.format(reconNummm));
				}
               // loanVo.setMaxMRate((CommonFunction.checkNull(loandeatail1.get(26))).trim());
                loanVo.setMinTenure((CommonFunction.checkNull(loandeatail1.get(27))).trim());
                loanVo.setMaxTenure((CommonFunction.checkNull(loandeatail1.get(28))).trim());
                loanVo.setAssetFlag((CommonFunction.checkNull(loandeatail1.get(29))).trim());
                loanVo.setNoOfInstall((CommonFunction.checkNull(loandeatail1.get(30))).trim());
                loanVo.setProductTypeFlag((CommonFunction.checkNull(loandeatail1.get(31))).trim());
                loanVo.setSectorType((CommonFunction.checkNull(loandeatail1.get(32))).trim());
                
                loanVo.setInstallments((CommonFunction.checkNull(loandeatail1.get(33))).trim());
                loanVo.setRepayEffectiveDate((CommonFunction.checkNull(loandeatail1.get(34))).trim());
                loanVo.setMaturityDate((CommonFunction.checkNull(loandeatail1.get(35))).trim());
                loanVo.setCycleDate((CommonFunction.checkNull(loandeatail1.get(36))).trim());
                loanVo.setNextDueDate((CommonFunction.checkNull(loandeatail1.get(37))).trim());
                loanVo.setLoanType((CommonFunction.checkNull(loandeatail1.get(38))).trim());
                loanVo.setRateMethodType((CommonFunction.checkNull(loandeatail1.get(39))).trim());
                list.add(loanVo);
			}
		}}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}




	
	public ArrayList getBaseRateList(String businessdate) {
		ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT A.BASE_RATE_TYPE FROM cr_base_rate_m A WHERE A.REC_STATUS='A' and A.EFFECTIVE_FROM =" );
		 query.append(" (SELECT MAX(B.EFFECTIVE_FROM) FROM cr_base_rate_m B WHERE B.REC_STATUS='A' and " );
		 query.append("A.BASE_RATE_TYPE= B.BASE_RATE_TYPE AND B.EFFECTIVE_FROM <= ");
		 query.append(dbo);
		 query.append("STR_TO_DATE('"+businessdate+"','"+dateFormat+"'))");
		logger.info("query : "+query.toString());
		CodeDescVo branchVo=null;
		ArrayList baseRate = ConnectionDAO.sqlSelect(query.toString());
		logger.info("query : "+query);
		
		query=null;
		
		for(int i=0;i<baseRate.size();i++){

			logger.info("getProductTypeList"+baseRate.get(i).toString());
			ArrayList baseRate1=(ArrayList)baseRate.get(i);
			if(baseRate1.size()>0)
			{
			    branchVo = new CodeDescVo();
			    
				branchVo.setId((CommonFunction.checkNull(baseRate1.get(0))).trim());
				 logger.info("id.................................. "+(CommonFunction.checkNull(baseRate1.get(0))).trim());
				list.add(branchVo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}




	
	
	public ArrayList getCycleDateList() {
		ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT CAST(VALUE AS UNSIGNED INTEGER) id,CAST(VALUE AS UNSIGNED INTEGER) id1 FROM generic_master WHERE GENERIC_KEY = 'CYCLE_DATE' and rec_status='A' ORDER BY id");

		logger.info("getCycleDateList: "+query.toString());
		CodeDescVo branchVo=null;
		ArrayList baseRate = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getCycleDateList"+baseRate.size());
		query=null;
		for(int i=0;i<baseRate.size();i++){

			logger.info("getCycleDateList"+baseRate.get(i).toString());
			ArrayList baseRate1=(ArrayList)baseRate.get(i);
			if(baseRate1.size()>0)
			{
			    branchVo = new CodeDescVo();
				branchVo.setId((CommonFunction.checkNull(baseRate1.get(0))).trim());
				branchVo.setName((CommonFunction.checkNull(baseRate1.get(1))).trim());
				list.add(branchVo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}

		return list;
	}




	
	
	@Override
	public String getDealId(String dealId) {
		String leadQuery = "select LEAD_ID from cr_deal_dtl where DEAL_ID='"+dealId.trim()+"'";
		logger.info("In LoanBehindAction(execute) leadQuery: " + leadQuery);
		String leadId = ConnectionDAO.singleReturn(leadQuery);
		return leadId;
	}



	
	
	@Override
	public String getDealLoanId(String dealId) {
		String dealQuery = "select DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_ID="+ dealId;
		logger.info("In LoanBehindAction(execute) Query: " + dealQuery);
		String dealloanId = ConnectionDAO.singleReturn(dealQuery);
		return dealloanId;
	}



	
	
	public ArrayList getLoanDetailList(String dealId) {
		ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT DEAL_LOAN_ID,DEAL_PRODUCT,DEAL_SCHEME,p.PRODUCT_DESC,s.SCHEME_DESC,DEAL_LOAN_AMOUNT,DEAL_TENURE,DEAL_RATE_TYPE,DEAL_RATE_METHOD,DEAL_LOAN_PURPOSE from cr_deal_loan_dtl d left join cr_product_m p on d.DEAL_PRODUCT=p.PRODUCT_ID left join cr_scheme_m s on d.DEAL_SCHEME=s.SCHEME_ID where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		logger.info("getLoanDetailList"+query.toString());
		LoanDetailVo loanVo = null;
		ArrayList loandeatail = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getProductTypeList"+loandeatail.size());
		
		query=null;
		
		for(int i=0;i<loandeatail.size();i++){

			logger.info("getProductTypeList"+loandeatail.get(i).toString());
			ArrayList loandeatail1=(ArrayList)loandeatail.get(i);
			if(loandeatail1.size()>0)
			{
				loanVo = new LoanDetailVo();
				loanVo.setDealLoanId((CommonFunction.checkNull(loandeatail1.get(0))).trim());
				loanVo.setLbxProductID((CommonFunction.checkNull(loandeatail1.get(1))).trim());
				loanVo.setLbxscheme((CommonFunction.checkNull(loandeatail1.get(2))).trim());
				loanVo.setProduct((CommonFunction.checkNull(loandeatail1.get(3))).trim());
				loanVo.setScheme((CommonFunction.checkNull(loandeatail1.get(4))).trim());
				
				if(!CommonFunction.checkNull(loandeatail1.get(5)).equalsIgnoreCase(""))	
				{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(loandeatail1.get(5))).trim());
					loanVo.setRequestedLoanAmount(myFormatter.format(reconNum));
				}

				
				loanVo.setRequestedLoanTenure((CommonFunction.checkNull(loandeatail1.get(6))).trim());


				if((CommonFunction.checkNull(loandeatail1.get(7)).trim()).equals("F"))
				{
					loanVo.setRateType("Flat Rate");
				}
				else if((CommonFunction.checkNull(loandeatail1.get(7)).trim()).equals("E"))
				{
					loanVo.setRateType("Effective Rate");
				}


				if((CommonFunction.checkNull(loandeatail1.get(8)).trim()).equals("F"))
				{
					loanVo.setType("Fixed");
				}
				else if((CommonFunction.checkNull(loandeatail1.get(8)).trim()).equals("L"))
				{
					loanVo.setType("Floating");
				}
				else
				{
					loanVo.setType("");
				}


				loanVo.setLoanPurpose((CommonFunction.checkNull(loandeatail1.get(9))).trim());
				list.add(loanVo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}


		return list;
	}




	
	
	public ArrayList getLoanTypeList() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getSourceDetailList..........................DAOImpl");
			StringBuilder query=new StringBuilder();
			
			 query.append("SELECT M.VALUE,M.DESCRIPTION FROM generic_master M,generic_master_keys K WHERE K.GENERIC_KEY='LOAN_TYPE' " +
						"AND K.GENERIC_KEY=M.GENERIC_KEY and m.REC_STATUS='A'");
			logger.info("In getSourceDetailList...............query...........DAOImpl"+ query.toString());
			LoanDetailVo loanVo = null;
			ArrayList product = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			
			logger.info("getSourceDetailList " + product.size());
			for (int i = 0; i < product.size(); i++) {
				logger.info("getSourceDetailList...Outer FOR loop " + CommonFunction.checkNull(product.get(i)).toString());
				ArrayList data = (ArrayList) product.get(i);
				if (data.size() > 0) {
					loanVo = new LoanDetailVo();
					loanVo.setLoanTypeID((CommonFunction.checkNull(data.get(0))).trim());
					loanVo.setLoanTypeName((CommonFunction.checkNull(data.get(1))).trim());
					list.add(loanVo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}




	
	
	public ArrayList getProductSchemeDetailsFromLead(String product, String scheme,
			String loanTenure, String loanAmount, String loanPurpose,String bdate) {
		
			ArrayList<Object> list=new ArrayList<Object>();
			try{
			//change by sachin	
				StringBuilder query=new StringBuilder();
			 query.append("SELECT TOP 1 ");
			 query.append("DEFAULT_MARGIN_RATE,RATE_TYPE,RATE_METHOD,S.BASE_RATE_TYPE,B.BASE_RATE,DEF_FLAT_RATE,");
			 query.append(" DEF_EFF_RATE,DEF_TENURE,REPAYMENT_FREQ,INSTALLMENT_TYPE,REPAYMENT_MODE,INSTALLMENT_MODE ,P.REPAYMENT_TYPE,P.REVOLVING_FLAG");
			 query.append(" ,MIN_MARGIN_RATE,MAX_MARGIN_RATE,MIN_TENURE,MAX_TENURE,MIN_AMT_FIN,MAX_AMT_FIN,P.ASSET_FLAG,p.PRODUCT_DESC,s.SCHEME_DESC,p.PRODUCT_CATEGORY from cr_scheme_m S ");
			 query.append(" LEFT JOIN cr_base_rate_m B ON S.BASE_RATE_TYPE=B.BASE_RATE_TYPE AND B.rec_status='A' and b.EFFECTIVE_FROM <= ");
			 query.append(dbo);
			 query.append("STR_TO_DATE('"+bdate+"','"+dateFormat+"') ");
			 query.append(" LEFT JOIN cr_product_m P ON S.PRODUCT_ID=P.PRODUCT_ID");
		     query.append(" where SCHEME_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(scheme)).trim()+" AND S.REC_STATUS='A' " );
			 query.append(" order by b.EFFECTIVE_FROM desc ");
//end by sachin
			logger.info("getLoanDetailScheme"+query);
			LoanDetailVo loanVo = null;
			ArrayList schemedeatail = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getLeadEntryList"+schemedeatail.size());
			
			query=null;
			
			for(int i=0;i<schemedeatail.size();i++){

				logger.info("getLeadEntryList"+schemedeatail.get(i).toString());
				ArrayList schemedeatail1=(ArrayList)schemedeatail.get(i);
				if(schemedeatail1.size()>0)
				{
					logger.info("getLeadEntryList"+schemedeatail1.size());
					loanVo = new LoanDetailVo();
					if(!CommonFunction.checkNull(schemedeatail1.get(0)).equalsIgnoreCase(""))
					{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(0))).trim());
					loanVo.setMarginPerc(myFormatter.format(reconNum));
					double ltv=100-reconNum.doubleValue();
					loanVo.setLtvPerc(""+ltv);
					}
					loanVo.setRateType((CommonFunction.checkNull(schemedeatail1.get(1))).trim());
					loanVo.setType((CommonFunction.checkNull(schemedeatail1.get(2))).trim());
					loanVo.setBaseRateType((CommonFunction.checkNull(schemedeatail1.get(3))).trim());
					if(!CommonFunction.checkNull(schemedeatail1.get(4)).equalsIgnoreCase(""))
					{
						Number reconNumb =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(4))).trim());
						loanVo.setBaseRate(myFormatter.format(reconNumb));
					}
					
					if((CommonFunction.checkNull(schemedeatail1.get(1))).trim().equalsIgnoreCase("E"))
					{
						Number effRate =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(6))).trim());
						Number baseRate =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(4))).trim());
						Number markUp=effRate.doubleValue()-baseRate.doubleValue();
						loanVo.setEffectiveRate(myFormatter.format(effRate));
						loanVo.setMarkUp(myFormatter.format(markUp));
					}
					else
					{
						Number flatRate =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(5))).trim());
						Number baseRate =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(4))).trim());
						Number markUp=flatRate.doubleValue()-baseRate.doubleValue();
						loanVo.setEffectiveRate(myFormatter.format(flatRate));
						loanVo.setMarkUp(myFormatter.format(markUp));
					}
					//schemeVo.setDefEffRate((CommonFunction.checkNull(schemedeatail1.get(6))).trim());
					loanVo.setRequestedLoanTenure((CommonFunction.checkNull(schemedeatail1.get(7))).trim());
					loanVo.setFrequency((CommonFunction.checkNull(schemedeatail1.get(8))).trim());
					double freqMonth=0;
				 	if((CommonFunction.checkNull(schemedeatail1.get(8))).trim().equalsIgnoreCase("M"))
		    	   	{
		    	   		freqMonth=1;
		    	   	}
		    	   	else if((CommonFunction.checkNull(schemedeatail1.get(8))).trim().equalsIgnoreCase("B"))
		    	   	{
		    	   		freqMonth=2;
		    	   	}
		    	   	else if((CommonFunction.checkNull(schemedeatail1.get(8))).trim().equalsIgnoreCase("Q"))
		    	   	{
		    	   		freqMonth=3;
		    	   	}
		    	   	else if((CommonFunction.checkNull(schemedeatail1.get(8))).trim().equalsIgnoreCase("H"))
		    	   	{
		    	   		freqMonth=6;
		    	   	}
		    	   	else if((CommonFunction.checkNull(schemedeatail1.get(8))).trim().equalsIgnoreCase("Y"))
		    	   	{
		    	   		freqMonth=12;
		    	   	}
		    	   	double parseTenure=Double.parseDouble(loanTenure);
					double calcInsat=parseTenure/freqMonth;
					loanVo.setNoOfInstall(""+calcInsat);
					loanVo.setInstallmentType((CommonFunction.checkNull(schemedeatail1.get(9))).trim());
					loanVo.setPaymentMode((CommonFunction.checkNull(schemedeatail1.get(10))).trim());
					loanVo.setInstMode((CommonFunction.checkNull(schemedeatail1.get(11))).trim());
					loanVo.setRepaymentType((CommonFunction.checkNull(schemedeatail1.get(12))).trim());
					if((CommonFunction.checkNull(schemedeatail1.get(12))).trim().equalsIgnoreCase("I"))
					{
						loanVo.setShowRepaymentType("INSTALLMENT");
					}
					else
					{
						loanVo.setShowRepaymentType("NON-INSTALLMENT");
					}
					
					//loanVo.setr((CommonFunction.checkNull(schemedeatail1.get(13))).trim());
					if(!CommonFunction.checkNull(schemedeatail1.get(14)).equalsIgnoreCase(""))
					{	
						Number reconNumm =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(14))).trim());
						loanVo.setMinMRate(myFormatter.format(reconNumm));
					}
					//schemeVo.setMinMarginRate((CommonFunction.checkNull(schemedeatail1.get(14))).trim());
					if(!CommonFunction.checkNull(schemedeatail1.get(15)).equalsIgnoreCase(""))
					{	
						Number reconNumR =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(15))).trim());
						loanVo.setMaxMRate(myFormatter.format(reconNumR));
					}
					//schemeVo.setMaxMarginRate((CommonFunction.checkNull(schemedeatail1.get(15))).trim());
					loanVo.setMinTenure((CommonFunction.checkNull(schemedeatail1.get(16))).trim());
					loanVo.setMaxTenure((CommonFunction.checkNull(schemedeatail1.get(17))).trim());
				
					if(!CommonFunction.checkNull(schemedeatail1.get(18)).equalsIgnoreCase(""))
					{
						Number reconNumf =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(18))).trim());
						loanVo.setMinFinanceAmount(myFormatter.format(reconNumf));
					}
					
					//schemeVo.setMinFinance((CommonFunction.checkNull(schemedeatail1.get(18))).trim());
					if(!CommonFunction.checkNull(schemedeatail1.get(19)).equalsIgnoreCase(""))
					{
						Number reconNummF =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(19))).trim());
						loanVo.setMaxFinanceAmount(myFormatter.format(reconNummF));
					}
					//schemeVo.setMaxFinance((CommonFunction.checkNull(schemedeatail1.get(19))).trim());
					loanVo.setAssetFlag((CommonFunction.checkNull(schemedeatail1.get(20))).trim());
					loanVo.setProduct((CommonFunction.checkNull(schemedeatail1.get(21))).trim());
					loanVo.setScheme((CommonFunction.checkNull(schemedeatail1.get(22))).trim());
					loanVo.setProductType((CommonFunction.checkNull(schemedeatail1.get(23))).trim());
					loanVo.setLbxProductID(product);
					loanVo.setLbxscheme(scheme);
					
					loanVo.setRequestedLoanTenure(loanTenure);
					if(!CommonFunction.checkNull(loanAmount).equalsIgnoreCase(""))
					{
						logger.info("loanAmount: "+loanAmount);
						Number reconNummF =myFormatter.parse(loanAmount);
						loanVo.setRequestedLoanAmount(myFormatter.format(reconNummF));
					}
					loanVo.setLoanPurpose(loanPurpose);
					list.add(loanVo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}




	
	
	public ArrayList getProductSchemeQuery(String dealId) {
		String productSchemeQuery = "select PRODUCT,SCHEME,LOAN_TENURE,AMOUNT_REQUIRED,LOAN_PURPOSE from cr_lead_dtl where REC_STATUS='A' AND LEAD_ID='"+dealId+"'";
		logger.info("In LoanBehindAction(execute) productSchemeQuery: " + productSchemeQuery);
		ArrayList productScheme = new ArrayList();
		try {
			productScheme = ConnectionDAO.sqlSelect(productSchemeQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productScheme;
	}



	
	
	public ArrayList getProductTypeList() {
		ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT PRODUCT_ID,PRODUCT_DESC from cr_product_m where REC_STATUS='A'");
		logger.info("getProductTypeList"+query.toString());
		CodeDescVo branchVo=null;
		ArrayList product = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		logger.info("getProductTypeList"+product.size());
		for(int i=0;i<product.size();i++){

			logger.info("getProductTypeList"+product.get(i).toString());
			ArrayList product1=(ArrayList)product.get(i);
			if(product1.size()>0)
			{
			    branchVo = new CodeDescVo();
				branchVo.setId((CommonFunction.checkNull(product1.get(0))));
				branchVo.setName((CommonFunction.checkNull(product1.get(1))).trim());
				list.add(branchVo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	public ArrayList getSectorList() {
		ArrayList list=new ArrayList();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT g.VALUE,g.DESCRIPTION from generic_master g where g.GENERIC_KEY='SECTOR_TYPE' and g.REC_STATUS='A'");
		logger.info("getSectorList");
		CodeDescVo branchVo=null;
		ArrayList baseRate = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getSectorList"+baseRate.size());
		
		query=null;
		for(int i=0;i<baseRate.size();i++){

			logger.info("getSectorList"+baseRate.get(i).toString());
			ArrayList baseRate1=(ArrayList)baseRate.get(i);
			if(baseRate1.size()>0)
			{
			    branchVo = new CodeDescVo();
				branchVo.setId((CommonFunction.checkNull(baseRate1.get(0))).trim());
				branchVo.setName((CommonFunction.checkNull(baseRate1.get(1))).trim());
				list.add(branchVo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}

		return list;
	}


	public boolean deleteLoanDetails(String[] id) {
		
		
		logger.info("deleteLoanDetails for deleteLoanDetails......"+id);
		boolean status = false;
    	try
    	{
    	qryList = new ArrayList();
    	bufInsUpdSql = new StringBuffer();
	
		for(int k=0;k<id.length;k++)
		{
			StringBuilder query=new StringBuilder();
			 query.append("delete from cr_deal_loan_dtl where DEAL_LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id[k])).trim());
	        
			qryList.add(query);
			query=null;
		}
		
        logger.info("delete query: "+qryList.get(0));
        
		status=ConnectionDAO.sqlInsUpdDelete(qryList);
		logger.info("Status of Deletion is ="+status);
	    }
    	catch(Exception e)
    	{
		e.printStackTrace();
	}
	return status;
	
}

	
	public boolean deleteChargeInstallmentRepay(String dealId, String[] dealLoanId) {
		
		logger.info("deleteChargeInstallmentRepay for deleteChargeInstallmentRepay....dealLoanId.."+dealLoanId+" dealId "+dealId);
		boolean status = false;
    	try
    	{
    	qryList = new ArrayList();
    	//bufInsUpdSql = new StringBuffer();
	
		for(int k=0;k<dealLoanId.length;k++)
		{
			StringBuilder deleteChatge=new StringBuilder();
			StringBuilder deleteInstallment=new StringBuilder();
			StringBuilder deleteSecurity=new StringBuilder();
			StringBuilder deleteRepay=new StringBuilder();
			 deleteChatge.append("delete from cr_deal_txncharges_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and DEAL_LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealLoanId[k])).trim());
			 deleteSecurity.append("delete from cr_deal_sd_m where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and DEAL_LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealLoanId[k])).trim());
			 deleteInstallment.append("delete from cr_deal_installment_plan where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and DEAL_LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealLoanId[k])).trim());
			 deleteRepay.append("delete from cr_deal_repaysch_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and DEAL_LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealLoanId[k])).trim());
			logger.info("delete query:deleteChatge "+deleteChatge.toString());
			logger.info("delete query:deleteSecurity "+deleteSecurity.toString());
			logger.info("delete query:deleteInstallment "+deleteInstallment.toString());
			logger.info("delete query:deleteRepay "+deleteRepay.toString());
			qryList.add(deleteChatge);
			qryList.add(deleteSecurity);
			qryList.add(deleteInstallment);
			qryList.add(deleteRepay);
			
			deleteChatge=null;
			deleteSecurity=null;
			deleteInstallment=null;
			deleteRepay=null;
		}
		
             
		status=ConnectionDAO.sqlInsUpdDelete(qryList);
		logger.info("Status of Deletion is ="+status);
	    }
    	catch(Exception e)
    	{
		e.printStackTrace();
	}
	return status;
	}

	
	public boolean deleteApplDocs(String dealId, String[] dealLoanId) {
		logger.info("deleteApplDocs for deleteApplDocs....dealLoanId.."+dealLoanId+" dealId "+dealId);
		boolean status = false;
    	try
    	{
    	qryList = new ArrayList();
    	bufInsUpdSql = new StringBuffer();
	
		for(int k=0;k<dealLoanId.length;k++)
		{
			StringBuilder deleteChatge =new StringBuilder();
			 deleteChatge.append("delete from cr_document_dtl where TXN_TYPE='DC' AND STAGE_ID='PRS' AND DOC_TYPE='APPL' AND  TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and ENTITY_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			logger.info("delete query:deleteChatge "+deleteChatge.toString());
			qryList.add(deleteChatge);
			
			deleteChatge=null;
		
		}
		            
		status=ConnectionDAO.sqlInsUpdDelete(qryList);
		logger.info("Status of Deletion is ="+status);
	    }
    	catch(Exception e)
    	{
		e.printStackTrace();
	}
	return status;
	}


	public ArrayList getSchemeList(String product)
	{
		ArrayList<CodeDescVo> list=new ArrayList<CodeDescVo>();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("SELECT SCHEME_ID,SCHEME_DESC from cr_scheme_m where PRODUCT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(product)).trim()+"' AND REC_STATUS='A' " );
		ArrayList schemeList = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getSchemeList "+schemeList.size());
		
		query=null;
		
		for(int i=0;i<schemeList.size();i++){
			logger.info("getSchemeList...Outer FOR loop "+CommonFunction.checkNull(schemeList.get(i)).toString());
			ArrayList data=(ArrayList)schemeList.get(i);
			if(data.size()>0)	{
			CodeDescVo branchVo = new CodeDescVo();
			branchVo.setId((CommonFunction.checkNull(data.get(0))).trim());
			branchVo.setName((CommonFunction.checkNull(data.get(1))).trim());
			list.add(branchVo);
		 }
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	

	public boolean saveLoanDetails(Object ob)
	{
		
		LoanDetailVo loanVo = (LoanDetailVo)ob;

		boolean status=false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = null;
		logger.info("In saveCustomer....saveLoanDetails..."+loanVo.getDealLoanId()+"loanVo.getType() "+loanVo.getType());
		try{
			 StringBuilder q=new StringBuilder();
		 q.append("select DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanVo.getDealLoanId())).trim()+"'");
		 logger.info("saveLoanDetails"+q.toString());
		boolean upd = ConnectionDAO.checkStatus(q.toString());
		
		q=null;
		
			if(upd)
			{
				 StringBuilder instalQuery=new StringBuilder();
				 instalQuery.append("select DEAL_LOAN_ID from cr_deal_loan_dtl where  DEAL_LOAN_ID='"+loanVo.getDealLoanId().trim()+"'");
				logger.info("In LoanDetailProcessAction(saveLoan) Q "+ instalQuery);
				String instalStatus = ConnectionDAO.singleReturn(instalQuery.toString());
				logger.info("instalStatus:  " + instalStatus);
				
				instalQuery=null;
				
				if(CommonFunction.checkNull(instalStatus.toString()).equalsIgnoreCase(""))
				{
					 StringBuilder deleteInst=new StringBuilder();
					 deleteInst.append("DELETE from cr_deal_installment_plan where DEAL_LOAN_ID="+loanVo.getDealLoanId().trim()+" ");
					logger.info("Deletion is Q ="+deleteInst.toString());
					ArrayList deleteArl = new ArrayList();
					deleteArl.add(deleteInst);
					status=ConnectionDAO.sqlInsUpdDelete(deleteArl);
					logger.info("Status of Deletion is ="+status);
					
					instalStatus=null;
				}
				logger.info("In saveCustomer....saveLoanDetails...Update mode");
//change by sachin
				StringBuilder query=new StringBuilder();
				 query.append("update cr_deal_loan_dtl set DEAL_PRODUCT=?, ");
				 query.append(" DEAL_SCHEME=?,DEAL_DISBURSAL_DATE=");
				 query.append(dbo);
				 query.append("STR_TO_DATE(?,'"+dateFormat+"'),");
				 query.append(" DEAL_ASSET_COST=?,DEAL_MARGIN_RATE=?, ");
				 query.append(" DEAL_MARGIN_AMOUNT=?,DEAL_LOAN_AMOUNT=?,DEAL_TENURE=?,");
				 query.append(" DEAL_REPAYMENT_FREQ=?, ");
				 query.append(" DEAL_RATE_TYPE=?,DEAL_RATE_METHOD=?,DEAL_BASE_RATE_TYPE=?,DEAL_BASE_RATE=?, ");
				 query.append(" DEAL_MARKUP=?,DEAL_FINAL_RATE=?,DEAL_REPAYMENT_TYPE=?,DEAL_INSTALLMENT_TYPE=?, DEAL_INSTALLMENT_MODE=?, ");
				 query.append(" DEAL_REPAYMENT_MODE=?,DEAL_NO_OF_INSTALLMENT=?,DEAL_LOAN_PURPOSE=?,DEAL_SECTOR_TYPE=?,DEAL_ADVANCE_INSTL=?,DEAL_REPAY_EFF_DATE=");
				 query.append(dbo);
				 query.append("STR_TO_DATE(?,'"+dateFormat+"'),DEAL_MATURITY_DATE=");
				 query.append(dbo);
				 query.append("STR_TO_DATE(?,'"+dateFormat+"'),DEAL_DUE_DAY=?,NEXT_DUE_DATE=");
				 query.append(dbo);
				 query.append("STR_TO_DATE(?,'"+dateFormat+"'),DEAL_FLAT_RATE=?,DEAL_EFF_RATE=?,MAKER_ID=?,MAKER_DATE=");
				 query.append(dbo);
				 query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				 query.append("LOAN_TYPE=? where DEAL_LOAN_ID=?");
				 logger.info("query.............saveLoanDetails"+query.toString());
//end by sachin				 
				insertPrepStmtObject = new PrepStmtObject();
				
				if ((CommonFunction.checkNull(loanVo.getLbxProductID())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getLbxProductID()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getLbxscheme())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getLbxscheme()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getDisbursalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getDisbursalDate()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getAssetCost())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((loanVo.getAssetCost()).trim()).toString());

				
				if ((CommonFunction.checkNull(loanVo.getMarginPerc())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((loanVo.getMarginPerc()).trim()).toString());
					//insertPrepStmtObject.addString((loanVo.getMarginPerc()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getMarginAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((loanVo.getMarginAmount()).trim()).toString());
					//insertPrepStmtObject.addString((loanVo.getMarginAmount()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getRequestedLoanAmount())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((loanVo.getRequestedLoanAmount()).trim()).toString());
					//insertPrepStmtObject.addString((loanVo.getRequestedLoanAmount()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getRequestedLoanTenure())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getRequestedLoanTenure()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getFrequency())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getFrequency()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getRateType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getRateType()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getType()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getBaseRateType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getBaseRateType()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getBaseRate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((loanVo.getBaseRate()).trim()).toString());
					//insertPrepStmtObject.addString((loanVo.getBaseRate()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getMarkUp())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((loanVo.getMarkUp()).trim()).toString());
					//insertPrepStmtObject.addString((loanVo.getMarkUp()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getEffectiveRate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((loanVo.getEffectiveRate()).trim()).toString());
					//insertPrepStmtObject.addString((loanVo.getEffectiveRate()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getRepaymentType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getRepaymentType()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getInstallmentType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getInstallmentType()).trim());
				
				if ((CommonFunction.checkNull(loanVo.getInstMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getInstMode()).trim());
				
				if (CommonFunction.checkNull((loanVo.getPaymentMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getPaymentMode()).trim());
				
				if (CommonFunction.checkNull((loanVo.getNoOfInstall())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getNoOfInstall()).trim());
				
				
				if ((CommonFunction.checkNull(loanVo.getLoanPurpose())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getLoanPurpose()).trim());
				
				if (CommonFunction.checkNull((loanVo.getSectorType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getSectorType()).trim());
				
				if (CommonFunction.checkNull((loanVo.getInstallments())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString((loanVo.getInstallments()).trim());
				
				if (CommonFunction.checkNull((loanVo.getRepayEffectiveDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getRepayEffectiveDate()).trim());
				
				if (CommonFunction.checkNull((loanVo.getMaturityDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getMaturityDate()).trim());
				
				if (CommonFunction.checkNull((loanVo.getCycleDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getCycleDate()).trim());
				
				if (CommonFunction.checkNull((loanVo.getNextDueDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getNextDueDate()).trim());
				
				if((CommonFunction.checkNull(loanVo.getRateType())).trim().equalsIgnoreCase("F"))
				{
					if((CommonFunction.checkNull(loanVo.getEffectiveRate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((loanVo.getEffectiveRate()).trim()).toString());
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				if((CommonFunction.checkNull(loanVo.getRateType())).trim().equalsIgnoreCase("E"))
				{
					if((CommonFunction.checkNull(loanVo.getEffectiveRate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((loanVo.getEffectiveRate()).trim()).toString());
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
				if (CommonFunction.checkNull((loanVo.getUserId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getUserId()).trim());
				
				if (CommonFunction.checkNull((loanVo.getBgDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getBgDate()).trim());
				//-----Loan_type
				
				if (CommonFunction.checkNull((loanVo.getLoanType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanVo.getLoanType()).trim());
				
				//----Loan_type
				
				if (CommonFunction.checkNull(loanVo.getDealLoanId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(loanVo.getDealLoanId().trim());
				
				insertPrepStmtObject.setSql(query.toString());
				logger.info("IN saveLoanDetails() update query1 ### "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveCustomer......................"+status);
				
				query=null;
			}
			else
			{
				//String q2="select DEAL_PRODUCT from cr_deal_loan_dtl where DEAL_PRODUCT='"+loanVo.getLbxProductID()+"' and DEAL_ID="+loanVo.getDealId();
				
				StringBuilder q2=new StringBuilder();
				 q2.append("select DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_ID="+loanVo.getDealId());
				logger.info("query of loan detail table: "+q2);
				status=ConnectionDAO.checkStatus(q2.toString());
				logger.info("status of loan detail table: "+status);
				
				q2=null;
				
				if(!status)
				{
					StringBuffer bufInsSql =new StringBuffer();
					bufInsSql.append("insert into cr_deal_loan_dtl(DEAL_ID,DEAL_PRODUCT_CATEGORY,DEAL_PRODUCT, " +
							"DEAL_SCHEME,DEAL_PRODUCT_TYPE,DEAL_DISBURSAL_DATE,DEAL_ASSET_COST,DEAL_MARGIN_RATE, " +
							"DEAL_MARGIN_AMOUNT,DEAL_LOAN_AMOUNT,DEAL_TENURE,DEAL_REPAYMENT_FREQ, " +
							"DEAL_RATE_TYPE,DEAL_RATE_METHOD,DEAL_BASE_RATE_TYPE,DEAL_BASE_RATE, " +
							"DEAL_MARKUP,DEAL_FINAL_RATE,DEAL_REPAYMENT_TYPE,DEAL_INSTALLMENT_TYPE, DEAL_INSTALLMENT_MODE, DEAL_NO_OF_INSTALLMENT,  " +
							"DEAL_REPAYMENT_MODE,DEAL_LOAN_PURPOSE,REC_STATUS,MAKER_ID,MAKER_DATE,DEAL_SECTOR_TYPE,DEAL_ADVANCE_INSTL,DEAL_REPAY_EFF_DATE,DEAL_MATURITY_DATE,DEAL_DUE_DAY,NEXT_DUE_DATE,DEAL_FLAT_RATE,DEAL_EFF_RATE,LOAN_TYPE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?," );//DEAL_ID
					bufInsSql.append(" ?," );//DEAL_PRODUCT_CATEGORY
					bufInsSql.append(" ?," );//DEAL_PRODUCT
					bufInsSql.append(" ?," );//DEAL_SCHEME
					bufInsSql.append(" ?," );//DEAL_PRODUCT_TYPE
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );//DEAL_DISBURSAL_DATE
					bufInsSql.append(" ?," );//DEAL_ASSET_COST
					bufInsSql.append(" ?," );//DEAL_MARGIN_RATE
					bufInsSql.append(" ?," );//DEAL_MARGIN_AMOUNT
					bufInsSql.append(" ?," );//DEAL_LOAN_AMOUNT
					bufInsSql.append(" ?," );//DEAL_TENURE
					bufInsSql.append(" ?," );//DEAL_REPAYMENT_FREQ
					bufInsSql.append(" ?," );//DEAL_RATE_TYPE
					bufInsSql.append(" ?," );//DEAL_RATE_METHOD
					bufInsSql.append(" ?," );//DEAL_BASE_RATE_TYPE
					bufInsSql.append(" ?," );//DEAL_BASE_RATE
					bufInsSql.append(" ?," );//DEAL_MARKUP
					bufInsSql.append(" ?," );//DEAL_FINAL_RATE
					bufInsSql.append(" ?," );//DEAL_REPAYMENT_TYPE
					bufInsSql.append(" ?," );//DEAL_INSTALLMENT_TYPE
					bufInsSql.append(" ?," );//DEAL_INSTALLMENT_MODE
					bufInsSql.append(" ?," );//DEAL_NO_OF_INSTALLMENT
					bufInsSql.append(" ?," );//DEAL_REPAYMENT_MODE
					bufInsSql.append(" ?," );//DEAL_LOAN_PURPOSE
					bufInsSql.append(" ?," );//REC_STATUS
					bufInsSql.append(" ?," );//MAKER_ID
					//change by sachin
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//MAKER_DATE
					//end by sachin
					
					bufInsSql.append(" ?," );//DEAL_SECTOR_TYPE
					bufInsSql.append(" ?," );//DEAL_ADVANCE_INSTL
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );//DEAL_REPAY_EFF_DATE
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );//DEAL_MATURITY_DATE
					bufInsSql.append(" ?, " );//DEAL_CYCLE_DATE
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );//DEAL_MATURITY_DATE
					bufInsSql.append(" ?," );//DEAL_FLAT_RATE
					bufInsSql.append(" ?," );//DEAL_EFF_RATE
					bufInsSql.append(" ? )" );//LOAN_TYPE
					
					insertPrepStmtObject = new PrepStmtObject();

					if((CommonFunction.checkNull(loanVo.getDealId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getDealId()).trim());
					if((CommonFunction.checkNull(loanVo.getProductType())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getProductType()).trim());
					if((CommonFunction.checkNull(loanVo.getLbxProductID())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getLbxProductID()).trim());
					if((CommonFunction.checkNull(loanVo.getLbxscheme())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getLbxscheme()).trim());
					
					if((CommonFunction.checkNull(loanVo.getProductTypeFlag())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getProductTypeFlag()).trim());
					
					if((CommonFunction.checkNull(loanVo.getDisbursalDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getDisbursalDate()).trim());
					
					if((loanVo.getAssetCost()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((loanVo.getAssetCost()).trim()).toString());
					
					if((loanVo.getMarginPerc()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((loanVo.getMarginPerc()).trim()).toString());
					if((CommonFunction.checkNull(loanVo.getMarginAmount()).trim()).equalsIgnoreCase(""))
					    insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((loanVo.getMarginAmount()).trim()).toString());
					if((CommonFunction.checkNull(loanVo.getRequestedLoanAmount())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((loanVo.getRequestedLoanAmount()).trim()).toString());
					if((CommonFunction.checkNull(loanVo.getRequestedLoanTenure())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getRequestedLoanTenure()).trim());
					if((CommonFunction.checkNull(loanVo.getFrequency())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getFrequency()).trim());

					if((CommonFunction.checkNull(loanVo.getRateType())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getRateType()).trim());

					if((CommonFunction.checkNull(loanVo.getType())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getType()).trim());

					if((CommonFunction.checkNull(loanVo.getBaseRateType())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getBaseRateType()).trim());

					if((CommonFunction.checkNull(loanVo.getBaseRate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((loanVo.getBaseRate()).trim());

					if((CommonFunction.checkNull(loanVo.getMarkUp())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((loanVo.getMarkUp()).trim()).toString());

					if((CommonFunction.checkNull(loanVo.getEffectiveRate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((loanVo.getEffectiveRate()).trim()).toString());

					if((CommonFunction.checkNull(loanVo.getRepaymentType())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
							insertPrepStmtObject.addString((loanVo.getRepaymentType()).trim());
					if((CommonFunction.checkNull(loanVo.getInstallmentType())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
							insertPrepStmtObject.addString((loanVo.getInstallmentType()).trim());
					if((CommonFunction.checkNull(loanVo.getInstMode())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString((loanVo.getInstMode()).trim());
					
							
				if((CommonFunction.checkNull(loanVo.getNoOfInstall())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString((loanVo.getNoOfInstall()).trim());

					if((CommonFunction.checkNull(loanVo.getPaymentMode())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					else
							insertPrepStmtObject.addString((loanVo.getPaymentMode()).trim());
					if((CommonFunction.checkNull(loanVo.getLoanPurpose())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				     else
						insertPrepStmtObject.addString((loanVo.getLoanPurpose()).trim());

						insertPrepStmtObject.addString("P");//Status
						if((CommonFunction.checkNull(loanVo.getUserId())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					     else
							insertPrepStmtObject.addString((loanVo.getUserId()).trim());
						
						if((CommonFunction.checkNull(loanVo.getBgDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
					     else
							insertPrepStmtObject.addString((loanVo.getBgDate()).trim());
						
						
						if (CommonFunction.checkNull((loanVo.getSectorType())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((loanVo.getSectorType()).trim());
					
						if (CommonFunction.checkNull((loanVo.getInstallments())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString("0");
						else
							insertPrepStmtObject.addString((loanVo.getInstallments()).trim());
						
						if (CommonFunction.checkNull((loanVo.getRepayEffectiveDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((loanVo.getRepayEffectiveDate()).trim());
						
						if (CommonFunction.checkNull((loanVo.getMaturityDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((loanVo.getMaturityDate()).trim());
						
						if (CommonFunction.checkNull((loanVo.getCycleDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((loanVo.getCycleDate()).trim());
						
						if (CommonFunction.checkNull((loanVo.getNextDueDate())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((loanVo.getNextDueDate()).trim());
						
						if((CommonFunction.checkNull(loanVo.getRateType())).trim().equalsIgnoreCase("F"))
						{
							if((CommonFunction.checkNull(loanVo.getEffectiveRate())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(myFormatter.parse((loanVo.getEffectiveRate()).trim()).toString());
						}
						else
						{
							insertPrepStmtObject.addNull();
						}
						if((CommonFunction.checkNull(loanVo.getRateType())).trim().equalsIgnoreCase("E"))
						{
							if((CommonFunction.checkNull(loanVo.getEffectiveRate())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(myFormatter.parse((loanVo.getEffectiveRate()).trim()).toString());
						}
						else
						{
							insertPrepStmtObject.addNull();
						}
						
						if (CommonFunction.checkNull((loanVo.getLoanType())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((loanVo.getLoanType()).trim());
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN SaveLoan() insert query1 ### "+insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
						status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					    logger.info("In saveCustomer......................"+status);
					    
					    bufInsSql=null;
				}
				else
				{
					status=false;
				}
				
			}



		if(status)
		{
			logger.info("status : "+status);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		


		return status;
	}


	public String callProcedure(String dealCap,String dealId)
	{
		ArrayList list =new ArrayList();
		String message="";
		try 
		{
			int dId = Integer.parseInt(dealId);		
			StringBuilder custIdQ=new StringBuilder();
			StringBuilder custId=new StringBuilder();
			StringBuilder suplId=new StringBuilder();
			StringBuilder manfId=new StringBuilder();
			StringBuilder supllierIdQ=new StringBuilder();
			StringBuilder exactManfId=new StringBuilder();
			StringBuilder exactSuplId=new StringBuilder();

			 custIdQ.append("select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID="+dealId);
			 suplId.append("SU;");
			 manfId.append("MF;");
			 custId.append(ConnectionDAO.singleReturn(custIdQ.toString()));
			logger.info("In callProcedure for deal customerId: "+custId);
			 supllierIdQ.append("select distinct ASSET_SUPPLIER from cr_deal_collateral_m d,cr_asset_collateral_m a "+ 
									" where d.ASSETID=a.ASSET_ID and a.ASSET_TYPE='ASSET'  and  DEAL_ID="+dealId);
			 
			 logger.info("In callProcedure for deal supplier: "+supllierIdQ.toString());
			ArrayList supllierIdList=ConnectionDAO.sqlSelect(supllierIdQ.toString());
			
			supllierIdQ=null;
			
			if(supllierIdList.size()>0)
			{
				for(int i=0;i<supllierIdList.size();i++)
				{
					ArrayList subsupllierIdList=(ArrayList)supllierIdList.get(i);
					if(subsupllierIdList.size()>0)
					  suplId.append(CommonFunction.checkNull(subsupllierIdList.get(0))+"|SU;");
				}
			}
			logger.info("In callProcedure for deal ## suplId ## "+suplId);
		    logger.info("In callProcedure for deal appendSQL true----  in check index Of"+suplId.lastIndexOf("SU;") +"------"+(suplId.length()-3));
		    if(suplId.lastIndexOf("SU;") == (suplId.length()-3))
		    {
		    	logger.info("In callProcedure for deal appendSQL true----  in check index Of");
		    	exactSuplId.append((suplId).substring(0,(suplId.length()-3)));
		    	logger.info("In callProcedure for deal ...suplId."+exactSuplId);
		    }
		    StringBuilder manufactIdQ=new StringBuilder();
			 manufactIdQ.append("select distinct ASSET_MANUFATURER from cr_deal_collateral_m d,cr_asset_collateral_m a "+ 
				                   " where d.ASSETID=a.ASSET_ID and a.ASSET_TYPE='ASSET'  and  DEAL_ID="+dealId);
			 logger.info("In callProcedure for deal manufact: "+manufactIdQ.toString());
				ArrayList manufactIdList=ConnectionDAO.sqlSelect(manufactIdQ.toString());	
				logger.info("manufactIdList.size(): "+manufactIdList.size());
				manufactIdQ=null;
			if(manufactIdList.size()>0)
			{
				for(int i=0;i<manufactIdList.size();i++)
				{
					ArrayList submanufactIdList=(ArrayList)manufactIdList.get(i);
					if(submanufactIdList.size()>0)
					{
							  manfId.append(CommonFunction.checkNull(submanufactIdList.get(0))+"|MF;");;
							 // manfId=manfId+2+"|MF;";
					}
				}
			}
			logger.info("In callProcedure for deal ## manfId ## "+manfId);
			logger.info("In callProcedure for deal In appendSQL true----  in check index Of"+manfId.lastIndexOf("MF;") +"------"+(manfId.length()-3));
			if(manfId.lastIndexOf("MF;") == (manfId.length()-3))
			{
			    logger.info("In appendSQL true----  in check index Of");
			    exactManfId.append((manfId).substring(0,(manfId.length()-3)));
			    logger.info("In callProcedure for deal.exactManfId  ."+exactManfId);
			}
			StringBuilder productQuery=new StringBuilder();
			
			 productQuery.append("select DEAL_PRODUCT,DEAL_SCHEME,DEAL_LOAN_AMOUNT,DEAL_MARGIN_AMOUNT,DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			logger.info("In callProcedure for deal productQuery: "+productQuery.toString());
			ArrayList productList=ConnectionDAO.sqlSelect(productQuery.toString());
			
			productQuery=null;
			StringBuilder checkQuery=new StringBuilder();

			 checkQuery.append("select DEAL_CHARGE_DTL_ID from cr_deal_txncharges_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			logger.info("checkQuery: "+checkQuery);
			boolean status = ConnectionDAO.checkStatus(checkQuery.toString());
			
			checkQuery=null;
			if(!status)
			{
				String repayQuery="select DEAL_REPAYMENT_TYPE from cr_deal_loan_dtl where deal_id='"+dealId+"'";
				String repay=ConnectionDAO.singleReturn(repayQuery.toString());
									
				RefreshFlagVo vo1 = new RefreshFlagVo();
				vo1.setRecordId(Integer.parseInt(dealId));
		    	vo1.setTabIndex(6);
		    	vo1.setModuleName("CP");
		    	vo1.setNonInstallment(repay);
	    		RefreshFlagValueInsert.updateRefreshFlag(vo1);
	    		
			  for(int i=0;i<productList.size();i++)
			  {
				  ArrayList subproductList=(ArrayList)productList.get(i);
				  if(subproductList.size()>0)
				  {
					   StringBuffer chargeStr=new StringBuffer();				  
	                   if(!CommonFunction.checkNull(subproductList.get(2)).equalsIgnoreCase(""))
	                	   chargeStr.append("2;"+subproductList.get(2)+"|");
	                   if(!CommonFunction.checkNull(subproductList.get(3)).equalsIgnoreCase(""))
	                	   chargeStr.append("3;"+CommonFunction.checkNull(subproductList.get(3))+"|");                   
	                    String bpStr="CS;"+custId+"|"+exactSuplId+exactManfId;
						String dealLoanId=CommonFunction.checkNull(subproductList.get(4));
						int loanDId=0;
						if(!dealLoanId.equalsIgnoreCase(""))
							loanDId=Integer.parseInt(dealLoanId);					
						logger.info(" STAGE INFO: "+dealCap+"DealLoanId from crdealLoandtl: "+loanDId+ " Total Charges: "+chargeStr+ " bpStr:  "+bpStr+ " Product: "+subproductList.get(0)+ " Scheme: "+subproductList.get(1));
						logger.info("GET_CHARGES in deal capturing");
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();					
						StringBuilder s1=new StringBuilder();
						StringBuilder s2=new StringBuilder();
						try 
						{
							in.add(dealCap);
							in.add(loanDId);
							in.add(CommonFunction.checkNull(subproductList.get(0)).toString());
							in.add(CommonFunction.checkNull(subproductList.get(1)).toString());
							in.add(chargeStr.toString());
							in.add(bpStr);
							out.add(s1);
							out.add(s2);					
							outMessages=(ArrayList) ConnectionDAO.callSP("GET_CHARGES",in,out);
							if(outMessages!=null && outMessages.size()>0)
							{
								s1.append(CommonFunction.checkNull(outMessages.get(0)));
															
								if(s1!=null && s1.toString().equalsIgnoreCase("S"))
								{
									
									message=s1.toString();
									logger.info("Procedure s1----"+s1);
								}
								else if(s1!=null && s1.toString().equalsIgnoreCase("E"))
								{
									s2.append(CommonFunction.checkNull(outMessages.get(1)));
									logger.info("Procedure Error Message----"+s2);
									message=s2.toString();
								}	
								logger.info("s1  :  "+s1);
								logger.info("s2  :  "+s2);
								
								s1=null;
								s2=null;
							}
						}
						catch (Exception e) 
						{e.printStackTrace();}
						finally
						{	
							
							in=null;
							out=null;
							outMessages=null;
						    exactManfId=null;
						    exactSuplId=null;

						}
				  	}
				 }	
			  
			}
		} 
		catch (Exception e) 
		{e.printStackTrace();}	
	return message;
	}

	
	public ArrayList getchargesDetail(String dealCap,String dealId) 
	{
		ArrayList list =new ArrayList();	
		try
		{			
			ChargeVo vo =null;
			StringBuilder query=new StringBuilder();
			 query.append("select DEAL_CHARGE_DTL_ID,DEAL_CHARGE_TYPE,DEAL_CHARGE_CODE,CHARGE_DESC," +
						" GM.DESCRIPTION,v.DEALER_DESC,DEAL_CHARGE_CALCULATED_AMOUNT," +
						" DEAL_CHARGE_FINAL_AMOUNT,d.CUSTOMER_NAME,p.DEAL_LOAN_AMOUNT,p.DEAL_MARGIN_AMOUNT," +
						" DEAL_CHARGE_CALCULATED_ON,case when DEAL_CHARGE_METHOD='P' then 'PERCENTAGE' else 'FLAT' end as DEAL_CHARGE_METHOD,case when DEAL_CHARGE_TAX_INCLUSIVE='N' then 'NO' else 'YES' end as DEAL_CHARGE_TAX_INCLUSIVE," +
						" DEAL_CHARGE_TAX_RATE1,DEAL_CHARGE_TAX_RATE2,DEAL_CHARGE_BP_TYPE,case when DEAL_MIN_CHARGE_METHOD='P' then 'PERCENTAGE' else 'FLAT' end as DEAL_MIN_CHARGE_METHOD,DEAL_CHARGE_MIN_CHARGE_AMOUNT," +
						" DEAL_CHARGE_TAX_APPLICABLE,DEAL_CHARGE_TDS_APPLICABLE,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_TAX_AMOUNT2,DEAL_CHARGE_MIN_CHARGE_AMOUNT,DEAL_CHARGE_TDS_RATE,DEAL_CHARGE_TDS_AMOUNT,DEAL_CHARGE_NET_AMOUNT,DEAL_CHARGE_APPLICATION_STAGE " +
						" from cr_deal_txncharges_dtl t " +
						" left join com_charge_code_m c on t.DEAL_CHARGE_CODE=c.CHARGE_CODE " +
						" left join cr_dsa_dealer_m v on v.DEALER_ID=t.DEAL_CHARGE_BP_ID and v.BP_TYPE=t.DEAL_CHARGE_BP_TYPE and v.REC_STATUS='A' " +
						" left join cr_deal_customer_m d on d.CUSTOMER_ID=t.DEAL_CHARGE_BP_ID "+
						" left join cr_deal_loan_dtl p on p.DEAL_ID=t.DEAL_ID  "+
						" left join generic_master GM on GM.VALUE=DEAL_CHARGE_BP_TYPE and GM.GENERIC_KEY='BPTYPE' "+
						" where  t.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
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


	public String callRefreshChargesDetailPro(String dealCap, String dealId)
	{
		String message="";
		boolean status=false;
		int dId = Integer.parseInt(dealId);
		StringBuilder custIdQ=new StringBuilder();
		 custIdQ.append("select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID="+dealId);
		logger.info("callRefreshChargesDetailPro in deal custIdQ: "+custIdQ);
		StringBuilder suplId=new StringBuilder();
		StringBuilder manfId=new StringBuilder();
		StringBuilder custId=new StringBuilder();
		StringBuilder exactManfId=new StringBuilder();
		StringBuilder exactSuplId=new StringBuilder();
		 suplId.append("SU;");
		 manfId.append("MF;");
		 custId.append(ConnectionDAO.singleReturn(custIdQ.toString()));
		logger.info("callRefreshChargesDetailPro in deal customerId: "+custId.toString());	
		StringBuilder supllierIdQ=new StringBuilder();
		 supllierIdQ.append("select distinct ASSET_SUPPLIER from cr_deal_collateral_m d,cr_asset_collateral_m a "+ 
							" where d.ASSETID=a.ASSET_ID and a.ASSET_TYPE='ASSET'  and  DEAL_ID="+dealId);
		 
		 logger.info("callRefreshChargesDetailPro in deal supllierIdQ : "+supllierIdQ.toString());	
		ArrayList supllierIdList=null;
		
		StringBuilder manufactIdQ=new StringBuilder();
		ArrayList manufactIdList=null;
		ArrayList productList=null;
		logger.info("supllierIdQ: "+supllierIdQ.toString());
		try
		{
				supllierIdList=ConnectionDAO.sqlSelect(supllierIdQ.toString());
				if(supllierIdList.size()>0)
				{
					for(int i=0;i<supllierIdList.size();i++)
					{
						ArrayList subsupllierIdList=(ArrayList)supllierIdList.get(i);
						if(subsupllierIdList.size()>0)
						  suplId.append(CommonFunction.checkNull(subsupllierIdList.get(0))+"|SU;");
					}
				}
				logger.info("callRefreshChargesDetailPro in deal ## suplId ## "+suplId.toString());
				logger.info("callRefreshChargesDetailPro in deal appendSQL true----  in check index Of"+suplId.lastIndexOf("SU;") +"------"+(suplId.length()-3));
				if(suplId.toString().lastIndexOf("SU;") == (suplId.toString().length()-3))
				{
					logger.info("callRefreshChargesDetailPro in deal appendSQL true----  in check index Of");
					exactSuplId.append((suplId).substring(0,(suplId.length()-3)));
					logger.info("callRefreshChargesDetailPro in deal ...exactSuplId."+exactSuplId.toString());
				}
				manufactIdQ.append("select distinct ASSET_MANUFATURER from cr_deal_collateral_m d,cr_asset_collateral_m a "+ 
			                  " where d.ASSETID=a.ASSET_ID and a.ASSET_TYPE='ASSET'  and  DEAL_ID="+dealId);
				
				 logger.info("callRefreshChargesDetailPro in deal manufactIdQ : "+manufactIdQ.toString());	
				manufactIdList=ConnectionDAO.sqlSelect(manufactIdQ.toString());
				if(manufactIdList.size()>0)
				{
					for(int i=0;i<manufactIdList.size();i++)
					{
						ArrayList submanufactIdList=(ArrayList)manufactIdList.get(i);
						if(submanufactIdList.size()>0)
							manfId.append(CommonFunction.checkNull(submanufactIdList.get(0))+"|MF;");;
					}
				}
				logger.info("callRefreshChargesDetailPro in deal ## manfId ## "+manfId.toString());
				logger.info("callRefreshChargesDetailPro in deal appendSQL true----  in check index Of"+manfId.lastIndexOf("MF;") +"------"+(manfId.length()-3));
				if(manfId.lastIndexOf("MF;") == (manfId.length()-3))
				{
					logger.info("callRefreshChargesDetailPro in deal appendSQL true----  in check index Of");
					exactManfId.append((manfId).substring(0,(manfId.length()-3)));
					logger.info("callRefreshChargesDetailPro in deal ...exactManfId."+exactManfId.toString());
				}
				
				StringBuilder productQuery=new StringBuilder();
				 productQuery.append("select DEAL_PRODUCT,DEAL_SCHEME,DEAL_LOAN_AMOUNT,DEAL_MARGIN_AMOUNT,DEAL_LOAN_ID from cr_deal_loan_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
				logger.info("callRefreshChargesDetailPro in deal productQuery: "+productQuery);
				productList=ConnectionDAO.sqlSelect(productQuery.toString());
				logger.info("callRefreshChargesDetailPro in deal productQuery: "+productList);
				productQuery=null;
				
				for(int i=0;i<productList.size();i++)
				{
					ArrayList subproductList=(ArrayList)productList.get(i);
					if(subproductList.size()>0)
					{
						StringBuffer chargeStr=new StringBuffer();
						if(!CommonFunction.checkNull(subproductList.get(2)).equalsIgnoreCase(""))
							chargeStr.append("2;"+subproductList.get(2)+"|");
						if(!CommonFunction.checkNull(subproductList.get(3)).equalsIgnoreCase(""))
							chargeStr.append("3;"+CommonFunction.checkNull(subproductList.get(3))+"|");
						String bpStr="CS;"+custId+"|"+exactSuplId+exactManfId;
						String dealLoanId=CommonFunction.checkNull(subproductList.get(4));
						int loanDId=0;
						if(!dealLoanId.equalsIgnoreCase(""))
							loanDId=Integer.parseInt(dealLoanId);
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						StringBuilder s1=new StringBuilder();
						StringBuilder s2=new StringBuilder();
						try 
						{		
							in.add(dealCap);
							in.add(loanDId);
							in.add(CommonFunction.checkNull(subproductList.get(0)).toString());
							in.add(CommonFunction.checkNull(subproductList.get(1)).toString());
							in.add(chargeStr.toString());
							in.add(bpStr);
							out.add(s1);
							out.add(s2);
							logger.info(" STAGE INFO: "+dealCap+"DealLoanId from crdealLoandtl: "+loanDId+ " Total Charges: "+chargeStr+ " customer Id:  "+bpStr+ " Product: "+subproductList.get(0)+ " Scheme: "+subproductList.get(1));
							logger.info("callRefreshChargesDetailPro in GET_CHARGES deal in deal capturing");
							outMessages=(ArrayList) ConnectionDAO.callSP("GET_CHARGES",in,out);
							if(outMessages!=null && outMessages.size()>0)
							{
								s1.append(CommonFunction.checkNull(outMessages.get(0)));							
								if(s1!=null && s1.toString().equalsIgnoreCase("S"))
								{
									message=s1.toString();
									logger.info("GET_CHARGES Procedure s1----"+s1.toString());
								}
								else if(s1!=null && s1.toString().equalsIgnoreCase("E"))
								{
									s2.append(CommonFunction.checkNull(outMessages.get(1)));
									logger.info("GET_CHARGES Procedure Error Message----"+s2.toString());
									message=s2.toString();
								}					
							}		
						} 
						catch (Exception e) 
						{e.printStackTrace();}
						finally
						{
							s1=null;
							s2=null;
							in=null;
							out=null;
							outMessages=null;	
							chargeStr=null;
							bpStr=null;
							dealLoanId=null;
							exactManfId=null;
							exactSuplId=null;

						}
					}
				}
			}
			catch (Exception e) 
			{e.printStackTrace();}
			finally
			{
				custIdQ=null;
				suplId=null;
				manfId=null;
				custId=null;
				supllierIdQ=null;
				supllierIdList=null;
				manufactIdQ=null;
				manufactIdList=null;
				productList=null;			
			}
			return message;
	}

	
	public ArrayList refreshchargesDetail(String dealCap,String dealId) 
	{
		
		ArrayList list =new ArrayList();
		ChargeVo vo =null;
		StringBuilder query=new StringBuilder();
		ArrayList charges=null;
		ArrayList subcharges=null;
		try
		{
			query.append("select DEAL_CHARGE_DTL_ID,DEAL_CHARGE_TYPE,DEAL_CHARGE_CODE,CHARGE_DESC," +
						" GM.DESCRIPTION,v.DEALER_DESC,DEAL_CHARGE_CALCULATED_AMOUNT," +
						" DEAL_CHARGE_FINAL_AMOUNT,d.CUSTOMER_NAME,p.DEAL_LOAN_AMOUNT,p.DEAL_MARGIN_AMOUNT," +
						" DEAL_CHARGE_CALCULATED_ON,case when DEAL_CHARGE_METHOD='P' then 'PERCENTAGE' else 'FLAT' end as DEAL_CHARGE_METHOD,case when DEAL_CHARGE_TAX_INCLUSIVE='N' then 'NO' else 'YES' end as DEAL_CHARGE_TAX_INCLUSIVE," +
						" DEAL_CHARGE_TAX_RATE1,DEAL_CHARGE_TAX_RATE2,DEAL_CHARGE_BP_TYPE,case when DEAL_MIN_CHARGE_METHOD='P' then 'PERCENTAGE' else 'FLAT' end as DEAL_MIN_CHARGE_METHOD,DEAL_CHARGE_MIN_CHARGE_AMOUNT, " +
						" DEAL_CHARGE_TAX_APPLICABLE,DEAL_CHARGE_TDS_APPLICABLE,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_TAX_AMOUNT2,DEAL_CHARGE_MIN_CHARGE_AMOUNT,DEAL_CHARGE_TDS_RATE,DEAL_CHARGE_TDS_AMOUNT,DEAL_CHARGE_NET_AMOUNT,DEAL_CHARGE_APPLICATION_STAGE " +
						" from cr_deal_txncharges_dtl t " +
						" left join com_charge_code_m c on t.DEAL_CHARGE_CODE=c.CHARGE_CODE " +
						" left join cr_dsa_dealer_m v on v.DEALER_ID=t.DEAL_CHARGE_BP_ID and v.BP_TYPE=t.DEAL_CHARGE_BP_TYPE and v.REC_STATUS='A' " +
						" left join cr_deal_customer_m d on d.CUSTOMER_ID=t.DEAL_CHARGE_BP_ID "+
						" left join cr_deal_loan_dtl p on p.DEAL_ID=t.DEAL_ID  "+
						" left join generic_master GM on GM.VALUE=DEAL_CHARGE_BP_TYPE and GM.GENERIC_KEY='BPTYPE' "+
						" where t.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			logger.info("refreshchargesDetail for getting charges in deal query "+query.toString());
			charges = ConnectionDAO.sqlSelect(query.toString());
			logger.info("refreshchargesDetail for getting charges in deal  size "+charges.size());
			for(int i=0;i<charges.size();i++)
			{
				subcharges=(ArrayList)charges.get(i);
				if(subcharges.size()>0)	
				{
					vo = new ChargeVo();
					vo.setChargeId((CommonFunction.checkNull(subcharges.get(0))).trim());
					if((CommonFunction.checkNull(subcharges.get(1)).trim()).equals("R"))
					vo.setChargeType("Receivable");
					else if((CommonFunction.checkNull(subcharges.get(1)).trim()).equals("P"))
					vo.setChargeType("Payable");
					vo.setChargeCode((CommonFunction.checkNull(subcharges.get(2))).trim());
					vo.setChargeDesc((CommonFunction.checkNull(subcharges.get(3))).trim());
					vo.setChargeBPType((CommonFunction.checkNull(subcharges.get(4)).trim()));
					if((CommonFunction.checkNull(subcharges.get(16)).trim()).equals("CS"))
					vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(8))).trim());
					else
					vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(5))).trim());
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
		finally
		{
			vo =null;
			query=null;
			charges=null;
			subcharges=null;
		}
		return list;
	}

}
