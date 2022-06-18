package com.cp.daoImplMYSQL;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import com.connect.PrepStmtObject;
import com.cp.dao.TradeCheckDAO;
import com.cp.vo.HeaderInfoVo;
import com.cp.vo.TradeCheckInitVo;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;


public class TradeCheckDAOImpl implements TradeCheckDAO {
	private static final Logger logger = Logger.getLogger(TradeCheckDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
	StringBuffer bufInsUpdSql = null;
	ArrayList qryList = null;
	ArrayList qryList1 = null;
	CallableStatement cs = null;
	PrepStmtObject  delPrepStmtObject = null;
	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	//DecimalFormat myFormatter = new DecimalFormat("###.##");
	
	ArrayList qryListB = null;
	ArrayList qryListS = null;

	/* Trade Check Search Method */
	
		public ArrayList<TradeCheckInitVo> searchTradeCheckCapData(TradeCheckInitVo vo,HttpServletRequest request) {
		//ArrayList detailList=new ArrayList();
		ArrayList<TradeCheckInitVo> list = new ArrayList<TradeCheckInitVo>();
		
			logger.info("Date Format: "+dateFormat);
			ArrayList searchlist=null;
			int count=0;
			
			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
 			String userName=ConnectionDAO.singleReturn(userNameQ);
 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
 			
 			userNameQ=null;
 			
			int startRecordIndex=0;
			int endRecordIndex = no;
			TradeCheckInitVo fetchVo= (TradeCheckInitVo) vo;
			boolean appendSQL=false;
			try
			{
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			String userId=(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReportingToUserId())).trim());
			String dealId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
			String appDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim());
			String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername())).trim());
			String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
			
			bufInsSql.append(" SELECT DISTINCT A.DEAL_ID,A.DEAL_NO,B.CUSTOMER_NAME,DATE_FORMAT(DEAL_DATE,'"+dateFormat+"'),E.PRODUCT_DESC,D.SCHEME_DESC FROM cr_deal_dtl A");
			
			bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM (SELECT DISTINCT A.DEAL_ID,A.DEAL_NO,B.CUSTOMER_NAME,DATE_FORMAT(DEAL_DATE,'"+dateFormat+"'),E.PRODUCT_DESC,D.SCHEME_DESC FROM cr_deal_dtl A");
			
			bufInsSql.append(" left join cr_deal_customer_m B ON A.deal_CUSTOMER_ID=B.CUSTOMER_ID  ");
			bufInsSqlTempCount.append(" left join cr_deal_customer_m B ON A.deal_CUSTOMER_ID=B.CUSTOMER_ID  ");
			bufInsSql.append(" left join cr_deal_loan_dtl c ON A.DEAL_ID=C.DEAL_ID  ");
			bufInsSqlTempCount.append(" left join cr_deal_loan_dtl c ON A.DEAL_ID=C.DEAL_ID  ");
			bufInsSql.append(" left join cr_scheme_m D on C.DEAL_SCHEME=D.SCHEME_ID ");
			bufInsSqlTempCount.append(" left join cr_scheme_m D on C.DEAL_SCHEME=D.SCHEME_ID ");
			bufInsSql.append(" left join cr_product_m E on C.DEAL_PRODUCT=E.PRODUCT_ID, cr_deal_verification_dtl dv ");
			bufInsSqlTempCount.append(" left join cr_product_m E on C.DEAL_PRODUCT=E.PRODUCT_ID, cr_deal_verification_dtl dv ");
			bufInsSql.append(" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE ");
			bufInsSqlTempCount.append(" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE ");
			bufInsSql.append(" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE ");
			bufInsSqlTempCount.append(" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE ");
			
			if((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserName()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase("")))
			{
			 bufInsSqlTempCount.append(") as b");
			}
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
			{
		   	  bufInsSql.append("WHERE A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND date(A.DEAL_DATE) =STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') AND B.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND C.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND C.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK' and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='F'");
		   	  bufInsSqlTempCount.append("WHERE A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND date(A.DEAL_DATE) =STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') AND B.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND C.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND C.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='F'");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getApplicationdate().equalsIgnoreCase("")))||((vo.getCustomername().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))){
				appendSQL=true;
			}
			
			if(appendSQL){
				logger.info("In Where Clause");
				bufInsSql.append(" WHERE A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='F'");
				bufInsSqlTempCount.append(" WHERE A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='F'");
			}

			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND A.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
				bufInsSqlTempCount.append(" AND A.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
				appendSQL=true;
		   	  
		     }
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase("")))) {
		   	  bufInsSql.append(" AND date(A.DEAL_DATE) =STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') ");
		   	  bufInsSqlTempCount.append(" AND date(A.DEAL_DATE) =STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') ");
		   	  appendSQL=true;
		     }
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername())).trim().equalsIgnoreCase("")))) {
		   	  bufInsSql.append(" AND B.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");
		   	  bufInsSqlTempCount.append(" AND B.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");
		   	  appendSQL=true;
		     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
			   	  bufInsSql.append(" AND C.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
			   	  bufInsSqlTempCount.append(" AND C.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
			   	  appendSQL=true;
			     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim().equalsIgnoreCase("")))) {
			   	  bufInsSql.append(" AND C.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
			   	  bufInsSqlTempCount.append(" AND C.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
			   	  appendSQL=true;
			     }
				 
			if((!(vo.getUserName().equalsIgnoreCase("")))||(!(vo.getLbxDealNo().equalsIgnoreCase("")))||(!(vo.getCustomername().equalsIgnoreCase("")))||(!(vo.getLbxProductID().equalsIgnoreCase("")))||(!(vo.getLbxscheme().equalsIgnoreCase("")))||(!(vo.getApplicationdate().equalsIgnoreCase("")))){
				bufInsSqlTempCount.append(" and (dv.internal_appraiser = '"+userId+"' or agm.USER_ID = '"+userId+"') ");
				bufInsSql.append(" and (dv.internal_appraiser = '"+userId+"' or agm.USER_ID = '"+userId+"') ");
				bufInsSqlTempCount.append(") as b");
				appendSQL=true;
			}	
	
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
			
			if((dealId.trim()==null && appDate.trim()==null && custName.trim()==null && productId.trim()==null) || (dealId.trim().equalsIgnoreCase("") && appDate.trim().equalsIgnoreCase("") && custName.trim().equalsIgnoreCase("") && productId.trim().equalsIgnoreCase("")) || fetchVo.getCurrentPageLink()>1)
			{
			
			logger.info("current PAge Link no .................... "+fetchVo.getCurrentPageLink());
			if(fetchVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (fetchVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
			}
			logger.info("query : "+bufInsSql);
		     
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			bufInsSql=null;
			bufInsSqlTempCount=null;
			
			logger.info("searchlist" + searchlist.size());			
			for(int i=0;i<searchlist.size();i++){
				logger.info("searchlist: "+searchlist.size());
				ArrayList data=(ArrayList)searchlist.get(i);
				if(data!=null && data.size()>0)
				{
					
					fetchVo = new TradeCheckInitVo();
								
					fetchVo.setLbxDealNo("<a href=tradeCheckCapSearch.do?method=tradeCheckCapturing&dealId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(0)))).trim()+">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1)))+"</a>");
					fetchVo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1))).trim());
					fetchVo.setCustomername(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2))).trim());
					fetchVo.setApplicationdate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
					fetchVo.setProduct(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
					fetchVo.setScheme(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
					fetchVo.setReportingToUserId(userName);
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
			
//			if(searchlist.size()==0)
//			{
//				fetchVo = new TradeCheckInitVo();
//				fetchVo.setTotalRecordSize(count);
//				list.add(fetchVo);
//				request.setAttribute("flag","yes");
//				logger.info("getTotalRecordSize : "+fetchVo.getTotalRecordSize());
//			}
			
			userId=null;
			dealId=null;
			appDate=null;
			custName=null;
			productId=null;

		}
			
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		return list;
	}
	
	public ArrayList<TradeCheckInitVo> searchTradeCheckCapDataAuto(TradeCheckInitVo vo, String recStatus, HttpServletRequest request) {
		//ArrayList detailList=new ArrayList();
		ArrayList<TradeCheckInitVo> list = new ArrayList<TradeCheckInitVo>();
		
			logger.info("searchTradeCheckCapDataAuto()----------");
			ArrayList searchlist=null;

			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getUserId());
			 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getUserId()+"'";
			 			String userName=ConnectionDAO.singleReturn(userNameQ);
			 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			 			
			 			userNameQ=null;
			 			
			 			
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			TradeCheckInitVo fetchVo= (TradeCheckInitVo) vo;
			boolean appendSQL=false;
			try
			{
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			String dealId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
			String appDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim());
			String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername())).trim());
			String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
			
			if(recStatus.equals("R"))
			{
				
				bufInsSql.append(" SELECT distinct A.DEAL_ID,A.DEAL_NO,B.CUSTOMER_NAME,DATE_FORMAT(A.DEAL_DATE,'%d-%m-%Y'),E.PRODUCT_DESC,D.SCHEME_DESC,TRADE_COUNT, D_TRADE_COUNT FROM ");
				bufInsSqlTempCount.append("SELECT COUNT(1)FROM( SELECT distinct A.DEAL_ID,A.DEAL_NO,B.CUSTOMER_NAME,DATE_FORMAT(A.DEAL_DATE,'%d-%m-%Y'),E.PRODUCT_DESC,D.SCHEME_DESC,TRADE_COUNT, D_TRADE_COUNT FROM ");
				bufInsSql.append(" (SELECT COUNT(distinct TRADE_CHECK_TYPE) TRADE_COUNT,TD.DEAL_ID FROM CR_TRADE_CHECK_DTL TD WHERE TD.REC_STATUS='F' GROUP BY TD.DEAL_ID) AS F,");
				bufInsSqlTempCount.append(" (SELECT COUNT(distinct TRADE_CHECK_TYPE) TRADE_COUNT,TD.DEAL_ID FROM CR_TRADE_CHECK_DTL TD WHERE TD.REC_STATUS='F' GROUP BY TD.DEAL_ID) AS F,");
				bufInsSql.append(" (SELECT COUNT(*) D_TRADE_COUNT,X.DEAL_ID FROM cr_deal_verification_dtl X WHERE X.VERIFICATION_TYPE='TRADE CHECK'    and X.VERIFICATION_ACTION='I' GROUP BY X.DEAL_ID) AS G,");
				bufInsSqlTempCount.append(" (SELECT COUNT(*) D_TRADE_COUNT,X.DEAL_ID FROM cr_deal_verification_dtl X WHERE X.VERIFICATION_TYPE='TRADE CHECK'    and X.VERIFICATION_ACTION='I' GROUP BY X.DEAL_ID) AS G,");
				bufInsSql.append(" cr_deal_dtl A left join cr_deal_customer_m B ON A.deal_CUSTOMER_ID=B.CUSTOMER_ID  left join cr_deal_loan_dtl c ON A.DEAL_ID=C.DEAL_ID");   
				bufInsSqlTempCount.append(" cr_deal_dtl A left join cr_deal_customer_m B ON A.deal_CUSTOMER_ID=B.CUSTOMER_ID  left join cr_deal_loan_dtl c ON A.DEAL_ID=C.DEAL_ID");
				bufInsSql.append(" left join cr_scheme_m D on C.DEAL_SCHEME=D.SCHEME_ID left join cr_product_m E on C.DEAL_PRODUCT=E.PRODUCT_ID, cr_deal_verification_dtl dv");  
				bufInsSqlTempCount.append(" left join cr_scheme_m D on C.DEAL_SCHEME=D.SCHEME_ID left join cr_product_m E on C.DEAL_PRODUCT=E.PRODUCT_ID, cr_deal_verification_dtl dv");
				bufInsSql.append(" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE");  
				bufInsSqlTempCount.append(" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE");
				bufInsSql.append(" where dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' ");
				bufInsSqlTempCount.append(" where dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' ");
				bufInsSql.append(" AND F.TRADE_COUNT=G.D_TRADE_COUNT AND F.DEAL_ID = G.DEAL_ID AND A.DEAL_ID=F.DEAL_ID AND A.DEAL_ID=G.DEAL_ID and dv.REC_STATUS='"+StringEscapeUtils.escapeSql(recStatus)+"' and A.DEAL_ID not in(select DEAL_ID from cr_trade_check_dtl where REC_STATUS='F' and MAKER_ID='"+vo.getUserId()+"')");
				bufInsSqlTempCount.append(" AND F.TRADE_COUNT=G.D_TRADE_COUNT AND F.DEAL_ID = G.DEAL_ID AND A.DEAL_ID=F.DEAL_ID AND A.DEAL_ID=G.DEAL_ID and dv.REC_STATUS='"+StringEscapeUtils.escapeSql(recStatus)+"'and A.DEAL_ID not in(select DEAL_ID from cr_trade_check_dtl where REC_STATUS='F' and MAKER_ID='"+vo.getUserId()+"'))AS b");
				//bufInsSql.append(" AND F.DEAL_ID = G.DEAL_ID AND A.DEAL_ID=F.DEAL_ID AND A.DEAL_ID=G.DEAL_ID and dv.REC_STATUS='"+StringEscapeUtils.escapeSql(recStatus)+"' and A.DEAL_ID not in(select DEAL_ID from cr_trade_check_dtl where REC_STATUS='F' and MAKER_ID='"+vo.getUserId()+"')");
				//bufInsSqlTempCount.append(" AND F.DEAL_ID = G.DEAL_ID AND A.DEAL_ID=F.DEAL_ID AND A.DEAL_ID=G.DEAL_ID and dv.REC_STATUS='"+StringEscapeUtils.escapeSql(recStatus)+"'and A.DEAL_ID not in(select DEAL_ID from cr_trade_check_dtl where REC_STATUS='F' and MAKER_ID='"+vo.getUserId()+"'))AS b");

			}
			else
			{
				bufInsSql.append(" SELECT DISTINCT A.DEAL_ID,A.DEAL_NO,B.CUSTOMER_NAME,DATE_FORMAT(DEAL_DATE,'"+dateFormat+"'),E.PRODUCT_DESC,D.SCHEME_DESC,A.MAKER_ID FROM cr_deal_dtl A");
				bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM(SELECT DISTINCT A.DEAL_ID,A.DEAL_NO,B.CUSTOMER_NAME,DATE_FORMAT(DEAL_DATE,'"+dateFormat+"'),E.PRODUCT_DESC,D.SCHEME_DESC FROM cr_deal_dtl A ");
				bufInsSql.append(" left join cr_deal_customer_m B ON A.deal_CUSTOMER_ID=B.CUSTOMER_ID  ");
				bufInsSqlTempCount.append(" left join cr_deal_customer_m B ON A.deal_CUSTOMER_ID=B.CUSTOMER_ID  ");
				bufInsSql.append(" left join cr_deal_loan_dtl c ON A.DEAL_ID=C.DEAL_ID  ");
				bufInsSqlTempCount.append(" left join cr_deal_loan_dtl c ON A.DEAL_ID=C.DEAL_ID  ");
				bufInsSql.append(" left join cr_scheme_m D on C.DEAL_SCHEME=D.SCHEME_ID ");
				bufInsSqlTempCount.append(" left join cr_scheme_m D on C.DEAL_SCHEME=D.SCHEME_ID ");
				bufInsSql.append(" left join cr_product_m E on C.DEAL_PRODUCT=E.PRODUCT_ID, cr_deal_verification_dtl dv ");
				bufInsSqlTempCount.append(" left join cr_product_m E on C.DEAL_PRODUCT=E.PRODUCT_ID, cr_deal_verification_dtl dv ");
				bufInsSql.append(" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE ");
				bufInsSqlTempCount.append(" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE ");
				bufInsSql.append(" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE ");
				bufInsSqlTempCount.append(" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE ");
				bufInsSql.append(" where dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='"+StringEscapeUtils.escapeSql(recStatus)+"'");
				bufInsSqlTempCount.append(" where dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='"+StringEscapeUtils.escapeSql(recStatus)+"'");
				bufInsSql.append(" and (dv.internal_appraiser = '"+CommonFunction.checkNull(fetchVo.getMakerId())+"' or agm.USER_ID = '"+CommonFunction.checkNull(fetchVo.getMakerId())+"')");
				
				bufInsSqlTempCount.append("and (dv.internal_appraiser = '"+CommonFunction.checkNull(fetchVo.getMakerId())+"' or agm.USER_ID = '"+CommonFunction.checkNull(fetchVo.getMakerId())+"') ) as b ");
			}
			
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
			
			if((dealId.trim()==null && appDate.trim()==null && custName.trim()==null && productId.trim()==null) || (dealId.trim().equalsIgnoreCase("") && appDate.trim().equalsIgnoreCase("") && custName.trim().equalsIgnoreCase("") && productId.trim().equalsIgnoreCase("")) || fetchVo.getCurrentPageLink()>1)
			{
			
				logger.info("current PAge Link no .................... "+fetchVo.getCurrentPageLink());
			if(fetchVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (fetchVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			}
			logger.info("query : "+bufInsSql);
		     
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("searchlist" + searchlist.size());	
			
			bufInsSql=null;
			
			for(int i=0;i<searchlist.size();i++){
				ArrayList data=(ArrayList)searchlist.get(i);
				if(data!=null && data.size()>0)
				{
					
					fetchVo = new TradeCheckInitVo();
					if(recStatus.equalsIgnoreCase("F"))			
						fetchVo.setLbxDealNo("<a href=tradeCheckCapSearch.do?method=tradeCheckCapturing&dealId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(0)))).trim()+">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1)))+"</a>");
					if(recStatus.equalsIgnoreCase("R"))
						fetchVo.setLbxDealNo("<a href=tradeCheckComSearch.do?method=tradeCheckCompletion&dealId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(0)))).trim()+">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1)))+"</a>");
					fetchVo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1))).trim());
					fetchVo.setCustomername(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2))).trim());
					fetchVo.setApplicationdate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
					fetchVo.setProduct(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
					fetchVo.setScheme(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
					
					if(!(recStatus.equals("R")))
						fetchVo.setReportingToUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(6))).trim());
//						fetchVo.setReportingToUserId(userName);
					
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
			
			
			dealId=null;
			appDate=null;
			custName=null;
			productId=null;

		}
			
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		return list;
	}

	/* For trade header */
	
	public ArrayList getTradeHeader(String dealId) {
		ArrayList list=new ArrayList();
			try
			{
				StringBuilder query=new StringBuilder();
				
				query.append(" select d.deal_id, deal_no,deal.CUSTOMER_NAME,DATE_FORMAT(deal_date,'"+dateFormat+"'),p.PRODUCT_DESC,s.SCHEME_DESC,l.DEAL_PRODUCT_CATEGORY from cr_deal_dtl d "+
							    " left join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID"+
								" left join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID"+
								" left join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID"+
								" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID"+
								" where d.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" limit 1");
				
				logger.info("getDealHeader Query: "+query);
				
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
						vo.setDealCustomerName((CommonFunction.checkNull(header1.get(2))).trim());
						vo.setDealDate((CommonFunction.checkNull(header1.get(3))).trim());
						vo.setDealProduct((CommonFunction.checkNull(header1.get(4))).trim());
						vo.setDealScheme((CommonFunction.checkNull(header1.get(5))).trim());
						vo.setDealProductCat((CommonFunction.checkNull(header1.get(6))).trim());
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

	/* For trade check completion search */
	
	public ArrayList<TradeCheckInitVo> searchTradeCheckComData(TradeCheckInitVo vo,HttpServletRequest request) {
		//ArrayList detailList=new ArrayList();
		ArrayList<TradeCheckInitVo> list = new ArrayList<TradeCheckInitVo>();
		
			logger.info("Date Format: "+dateFormat);
			ArrayList searchlist=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			TradeCheckInitVo fetchVo= (TradeCheckInitVo) vo;
			boolean appendSQL=false;

			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getUserid());
			 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getUserid()+"'";
			 			String userName=ConnectionDAO.singleReturn(userNameQ);
			 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			 			
			 			userNameQ=null;
			try
			{
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			String dealId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
			String appDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim());
			String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername())).trim());
			String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
			
			bufInsSql.append(" SELECT distinct A.DEAL_ID,A.DEAL_NO,B.CUSTOMER_NAME,DATE_FORMAT(A.DEAL_DATE,'%d-%m-%Y'),E.PRODUCT_DESC,D.SCHEME_DESC,TRADE_COUNT, D_TRADE_COUNT FROM ");
			bufInsSqlTempCount.append("SELECT COUNT(1)FROM( SELECT distinct A.DEAL_ID,A.DEAL_NO,B.CUSTOMER_NAME,DATE_FORMAT(A.DEAL_DATE,'%d-%m-%Y'),E.PRODUCT_DESC,D.SCHEME_DESC,TRADE_COUNT, D_TRADE_COUNT FROM ");
			bufInsSql.append(" (SELECT COUNT(distinct TRADE_CHECK_TYPE) TRADE_COUNT,TD.DEAL_ID FROM CR_TRADE_CHECK_DTL TD WHERE TD.REC_STATUS='F' GROUP BY TD.DEAL_ID) AS F,");
			bufInsSqlTempCount.append(" (SELECT COUNT(distinct TRADE_CHECK_TYPE) TRADE_COUNT,TD.DEAL_ID FROM CR_TRADE_CHECK_DTL TD WHERE TD.REC_STATUS='F' GROUP BY TD.DEAL_ID) AS F,");
			bufInsSql.append(" (SELECT COUNT(*) D_TRADE_COUNT,X.DEAL_ID FROM cr_deal_verification_dtl X WHERE X.VERIFICATION_TYPE='TRADE CHECK'    and X.VERIFICATION_ACTION='I' GROUP BY X.DEAL_ID) AS G,");
			bufInsSqlTempCount.append(" (SELECT COUNT(*) D_TRADE_COUNT,X.DEAL_ID FROM cr_deal_verification_dtl X WHERE X.VERIFICATION_TYPE='TRADE CHECK'    and X.VERIFICATION_ACTION='I' GROUP BY X.DEAL_ID) AS G,");
			bufInsSql.append(" cr_deal_dtl A left join cr_deal_customer_m B ON A.deal_CUSTOMER_ID=B.CUSTOMER_ID  left join cr_deal_loan_dtl c ON A.DEAL_ID=C.DEAL_ID");   
			bufInsSqlTempCount.append(" cr_deal_dtl A left join cr_deal_customer_m B ON A.deal_CUSTOMER_ID=B.CUSTOMER_ID  left join cr_deal_loan_dtl c ON A.DEAL_ID=C.DEAL_ID");
			bufInsSql.append(" left join cr_scheme_m D on C.DEAL_SCHEME=D.SCHEME_ID left join cr_product_m E on C.DEAL_PRODUCT=E.PRODUCT_ID, cr_deal_verification_dtl dv");  
			bufInsSqlTempCount.append(" left join cr_scheme_m D on C.DEAL_SCHEME=D.SCHEME_ID left join cr_product_m E on C.DEAL_PRODUCT=E.PRODUCT_ID, cr_deal_verification_dtl dv");
			bufInsSql.append(" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE where A.DEAL_ID not in(select DEAL_ID from cr_trade_check_dtl where REC_STATUS='F' and MAKER_ID='"+vo.getUserid()+"')");  
			bufInsSqlTempCount.append(" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE  where A.DEAL_ID not in(select DEAL_ID from cr_trade_check_dtl where REC_STATUS='F' and MAKER_ID='"+vo.getUserid()+"')");
		
						
			if((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserName()).trim()).equalsIgnoreCase("")) && (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase("")))
			{
			 bufInsSqlTempCount.append(") as b");
			}
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
			{
		   	  bufInsSql.append("and A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND date(A.DEAL_DATE) =STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') AND B.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND C.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND C.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.REC_STATUS='R'");
		   	  bufInsSqlTempCount.append("and A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND date(A.DEAL_DATE) =STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') AND B.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND C.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND C.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.REC_STATUS='R'");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getApplicationdate().equalsIgnoreCase("")))||((vo.getCustomername().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))){
				appendSQL=true;
			}
			
			if(appendSQL){
				logger.info("In Where Clause");
				bufInsSql.append(" and A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id AND F.TRADE_COUNT=G.D_TRADE_COUNT AND F.DEAL_ID = G.DEAL_ID AND A.DEAL_ID=F.DEAL_ID AND A.DEAL_ID=G.DEAL_ID and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='R'");
				bufInsSqlTempCount.append(" and A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id AND F.TRADE_COUNT=G.D_TRADE_COUNT AND F.DEAL_ID = G.DEAL_ID AND A.DEAL_ID=F.DEAL_ID AND A.DEAL_ID=G.DEAL_ID and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='R'");
				//bufInsSql.append(" and A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id  AND F.DEAL_ID = G.DEAL_ID AND A.DEAL_ID=F.DEAL_ID AND A.DEAL_ID=G.DEAL_ID and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='R'");
				//bufInsSqlTempCount.append(" and A.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and A.Rec_status='"+StringEscapeUtils.escapeSql(vo.getStage()).trim()+"' and dv.DEAL_ID=c.deal_id  AND F.DEAL_ID = G.DEAL_ID AND A.DEAL_ID=F.DEAL_ID AND A.DEAL_ID=G.DEAL_ID and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.VERIFICATION_ACTION='I' and dv.REC_STATUS='R'");
		
			}

			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND A.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
				bufInsSqlTempCount.append(" AND A.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
				appendSQL=true;
		   	  
		     }
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationdate())).trim().equalsIgnoreCase("")))) {
		   	  bufInsSql.append(" AND date(A.DEAL_DATE) =STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') ");
		   	  bufInsSqlTempCount.append(" AND date(A.DEAL_DATE) =STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getApplicationdate()).trim()+"','"+dateFormat+"') ");
		   	  appendSQL=true;
		     }
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername())).trim().equalsIgnoreCase("")))) {
		   	  bufInsSql.append(" AND B.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");
		   	  bufInsSqlTempCount.append(" AND B.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");
		   	  appendSQL=true;
		     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
			   	  bufInsSql.append(" AND C.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
			   	  bufInsSqlTempCount.append(" AND C.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
			   	  appendSQL=true;
			     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim().equalsIgnoreCase("")))) {
			   	  bufInsSql.append(" AND C.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
			   	  bufInsSqlTempCount.append(" AND C.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
			   	  appendSQL=true;
			     }
			
			 
			if((!(vo.getReportingToUserId().equalsIgnoreCase(""))) || (!(vo.getLbxDealNo().equalsIgnoreCase("")))||(!(vo.getCustomername().equalsIgnoreCase("")))||(!(vo.getLbxProductID().equalsIgnoreCase("")))||(!(vo.getLbxscheme().equalsIgnoreCase("")))||(!(vo.getApplicationdate().equalsIgnoreCase("")))){
				bufInsSqlTempCount.append(") as b");
				appendSQL=true;
			}	
//			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));			
//			logger.info("--Hy  +++++++++++++++++++++++++++------------------------------"+count);
//			
//			logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
			
			if((dealId.trim()==null && appDate.trim()==null && custName.trim()==null && productId.trim()==null) || (dealId.trim().equalsIgnoreCase("") && appDate.trim().equalsIgnoreCase("") && custName.trim().equalsIgnoreCase("") && productId.trim().equalsIgnoreCase("")) || fetchVo.getCurrentPageLink()>1)
			{
			
			logger.info("current PAge Link no .................... "+fetchVo.getCurrentPageLink());
			if(fetchVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (fetchVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
			}
			logger.info("query : "+bufInsSql);
		     
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			
			bufInsSql=null;

			logger.info("searchlist" + searchlist.size());			
			for(int i=0;i<searchlist.size();i++){
				logger.info("searchlist: "+searchlist.size());
				ArrayList data=(ArrayList)searchlist.get(i);
				if(data!=null && data.size()>0)
				{
					
					fetchVo = new TradeCheckInitVo();
								
					fetchVo.setLbxDealNo("<a href=tradeCheckComSearch.do?method=tradeCheckCompletion&dealId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(CommonFunction.checkNull(data.get(0)))).trim()+">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1)))+"</a>");
			
					fetchVo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(1))).trim());
					fetchVo.setCustomername(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(2))).trim());
					fetchVo.setApplicationdate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(3))).trim());
					fetchVo.setProduct(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(4))).trim());
					fetchVo.setScheme(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(data.get(5))).trim());
					fetchVo.setReportingToUserId(userName);
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		
			bufInsSqlTempCount=null;
			
			dealId=null;
			appDate=null;
			custName=null;
			productId=null;
				
		}
			catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	public boolean insertTradeBuyer(Object ob) {
		TradeCheckInitVo vo = (TradeCheckInitVo) ob;
		boolean status = false;
		logger.info("In insertTradeBuyer.....................................Dao Impl ");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		try {

				logger.info("In insert TradeBuyer");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("insert into cr_trade_check_dtl(DEAL_ID,VERIFICATION_ID,REFERENCE_NO,APPRAISER_NAME,APPRAISAL_DATE,VERIFICATION_MODE,TRADE_CHECK_TYPE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,AVG_MONTHLY_SALES,PAYMENT_TERMS,PRODUCT_TYPE,VINTAGE_OF_RELATION,ADDRESS,CITY,STATE,COUNTRY,PINCODE,TC_REMARKS,REC_STATUS,ENTITY_ID,ENTITY_TYPE,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //Deal_Id
				bufInsSql.append(" ?,"); //VERIFICATION_ID
				bufInsSql.append(" ?,"); //REFERENCE_NO 
				bufInsSql.append(" ?,"); //APPRAISER_NAME
				bufInsSql.append(" STR_TO_DATE(?,'" + dateFormat+ "'),"); //APPRAISAL_DATE
				bufInsSql.append(" ?,"); //VERIFICATION_MODE
				bufInsSql.append(" ?,"); //TRADE_CHECK_TYPE
				bufInsSql.append(" ?,"); //PERSON_MET
				bufInsSql.append(" ?,"); //RELATION
				bufInsSql.append(" ?,"); //PHONE1
				bufInsSql.append(" ?,"); //PHONE2
				bufInsSql.append(" ?,"); //E_MAIL
				bufInsSql.append(" ?,"); //AVG_MONTHLY_SALES
				bufInsSql.append(" ?,"); //PAYMENT_TERMS
				bufInsSql.append(" ?,"); //PRODUCT_TYPE
				bufInsSql.append(" ?,"); //VINTAGE_OF_RELATION
				bufInsSql.append(" ?,"); //ADDRESS
				bufInsSql.append(" ?,"); //CITY
				bufInsSql.append(" ?,"); //STATE
				bufInsSql.append(" ?,"); //COUNTRY
				bufInsSql.append(" ?,"); //PINCODE
				bufInsSql.append(" ?,"); //TC_REMARKS
				bufInsSql.append(" 'P',");//Status
				bufInsSql.append(" ?,"); //ENTITY_ID
				bufInsSql.append(" ?,"); //ENTITY_TYPE
				bufInsSql.append(" ?,"); //MAKER_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); //MAKER_DATE

				if((CommonFunction.checkNull(vo.getDealNo1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDealNo1()).trim());
				
				if((CommonFunction.checkNull(vo.getVerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getVerId()).trim());
				
				if((CommonFunction.checkNull(vo.getRefrenceNO())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRefrenceNO()).trim());
				
				if((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAppraiser()).trim());
				
				if((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAppraisalDate()).trim());
				
				if((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getVerificationMode()).trim());
				
				if((CommonFunction.checkNull(vo.getTradeCheck1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTradeCheck1()).trim());
				
				if((CommonFunction.checkNull(vo.getContactPerson())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("--");
				else
					insertPrepStmtObject.addString((vo.getContactPerson()).trim());
				
				if((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("--");
				else
					insertPrepStmtObject.addString((vo.getRelation()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPhone1()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getPhone2()).trim()).equalsIgnoreCase(""))
				    insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPhone2()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getEmail()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getAveragePurchase())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getAveragePurchase()).trim()).toString());
								
				if((CommonFunction.checkNull(vo.getPaymentTerms())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("--");
				else
					insertPrepStmtObject.addString((vo.getPaymentTerms()).trim());
				
				if((CommonFunction.checkNull(vo.getProductType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("--");
				else
					insertPrepStmtObject.addString((vo.getProductType()).trim());

				if((CommonFunction.checkNull(vo.getVintageOfRelationship())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString((vo.getVintageOfRelationship()).trim());

				if((CommonFunction.checkNull(vo.getAddress())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddress()).trim());
				
				if((CommonFunction.checkNull(vo.getCity())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCity()).trim());

				if((CommonFunction.checkNull(vo.getTxtStateCode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtStateCode()).trim());

				if((CommonFunction.checkNull(vo.getTxtCountryCode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtCountryCode()).trim());
				
				if((CommonFunction.checkNull(vo.getPincode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPincode()).trim());

				if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
				
				if((CommonFunction.checkNull(vo.getEntityId1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getEntityId1()).trim());

				if((CommonFunction.checkNull(vo.getEntityType1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getEntityType1()).trim());

				if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());

				if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN insert TradeBuyer() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveTradeBuyerData......................"+ status);
				
				
				bufInsSql=null;
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public ArrayList getList(String dealId,String userId) {
		ArrayList list=new ArrayList();
		TradeCheckInitVo vo= null;
			try
			{
				StringBuilder query=new StringBuilder();
				
				query.append("select distinct Case a.VERIFICATION_SUBTYPE When 'B' then 'Buyer' When 'S' then 'Supplier' When 'M' then 'Market' End As TradeType," +
					" b.DEAL_BUYER_SUPPLIER_NAME,b.DEAL_BUYER_SUPPLIER_CONTACT, b.DEAL_BUYER_SUPPLIER_MONTLY_SALES," +
					" b.DEAL_BUYER_SUPPLIER_PAYMENT_TERM,b.DEAL_BUYER_SUPPLIER_PRODUCT_TYPE,b.DEAL_BUYER_SUPPLIER_VINTAGE,a.VERIFICATION_REMARKS," +
					" a.VERIFICATION_ID,if(a.REC_STATUS='F','Pending','Pending'),DEAL_BUYER_SUPPLIER_ID,DEAL_BUYER_SUPPLIER_TYPE  " +
					" from cr_deal_verification_dtl a " +
					" left join com_agency_m ag on a.external_appraiser=ag.AGENCY_CODE " +
					" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE " +
					" left join cr_deal_buyer_supplier_dtl b on a.DEAL_ID=b.DEAL_ID " +
					" and a.VERIFICATION_SUBTYPE=b.DEAL_BUYER_SUPPLIER_TYPE where a.VERIFICATION_TYPE='TRADE CHECK'    and VERIFICATION_ACTION='I' and a.REC_STATUS='F'  " +
					" and ((DEAL_BUYER_SUPPLIER_ID not in (select  c.ENTITY_ID from cr_trade_check_dtl c where c.ENTITY_ID = DEAL_BUYER_SUPPLIER_ID ) ) OR a.VERIFICATION_SUBTYPE = 'M')" +
					" and a.VERIFICATION_SUBTYPE not in(Select c.TRADE_CHECK_TYPE from cr_trade_check_dtl c where c.TRADE_CHECK_TYPE='M' and c.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" )"+
					" and a.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" " +
					" and (a.internal_appraiser = '"+CommonFunction.checkNull(userId)+"' or agm.USER_ID = '"+CommonFunction.checkNull(userId)+"') ORDER BY VERIFICATION_ID "); 
				
						
				logger.info("get list for BuyerSuplier Query: "+query);
				
				
				ArrayList list1 = ConnectionDAO.sqlSelect(query.toString());
				
				query=null;
				
				for(int i=0;i<list1.size();i++){
					logger.info("list1"+list1.size());
					ArrayList header1=(ArrayList)list1.get(i);
					if(header1!=null && header1.size()>0)
					{
						vo = new TradeCheckInitVo();
						vo.setTradeCheck((CommonFunction.checkNull(header1.get(0))).trim());
						vo.setBuyerSuplierName((CommonFunction.checkNull(header1.get(1))).trim());
						vo.setContactPerson((CommonFunction.checkNull(header1.get(2))).trim());
//						vo.setRelation((CommonFunction.checkNull(header1.get(3))).trim());
					
						if(!CommonFunction.checkNull(header1.get(3)).equalsIgnoreCase(""))
						{
							Number avgPurchase = myFormatter.parse((CommonFunction.checkNull(header1.get(3))).trim());
							vo.setAveragePurchase(myFormatter.format(avgPurchase));
						}
						
						vo.setPaymentTerms((CommonFunction.checkNull(header1.get(4))).trim());
						vo.setProductType((CommonFunction.checkNull(header1.get(5))).trim());
						vo.setVintageOfRelationship((CommonFunction.checkNull(header1.get(6))).trim());
						vo.setTextarea((CommonFunction.checkNull(header1.get(7))).trim());
						vo.setVerificationId((CommonFunction.checkNull(header1.get(8))).trim());
						vo.setStatus((CommonFunction.checkNull(header1.get(9))).trim());
						vo.setEntityId((CommonFunction.checkNull(header1.get(10))).trim());
						vo.setEntityType((CommonFunction.checkNull(header1.get(11))).trim());
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

	public ArrayList getTradeList(String dealId,String userId,String recStatus) {
		ArrayList tradeList=new ArrayList();
			try
			{
				StringBuffer bufInsSql=new StringBuffer();
				
				bufInsSql.append(" Select Case TRADE_CHECK_TYPE When 'B' then 'Buyer' When 'S' then 'Supplier' When 'M' then 'Market' End As TradeType, ");
				bufInsSql.append(" b.DEAL_BUYER_SUPPLIER_NAME,PERSON_MET,RELATION,AVG_MONTHLY_SALES,PAYMENT_TERMS,PRODUCT_TYPE,VINTAGE_OF_RELATION,TC_REMARKS,a.VERIFICATION_ID,if(a.REC_STATUS='P','In process','In process'),ENTITY_ID ");
				bufInsSql.append(" from cr_trade_check_dtl a  left join cr_deal_verification_dtl dv on dv.VERIFICATION_ID=a.VERIFICATION_ID left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE ");
				bufInsSql.append(" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE ");
				bufInsSql.append(" left join cr_deal_buyer_supplier_dtl b on a.DEAL_ID=b.DEAL_ID AND A.ENTITY_ID=B.DEAL_BUYER_SUPPLIER_ID and a.TRADE_CHECK_TYPE=b.DEAL_BUYER_SUPPLIER_TYPE ");
				bufInsSql.append(" where a.REC_STATUS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStatus))+"' and a.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"  ");
				
				if((!recStatus.equalsIgnoreCase("F")) && (!recStatus.equalsIgnoreCase("A")))
				{
				bufInsSql.append(" and (dv.internal_appraiser = '"+CommonFunction.checkNull(userId)+"' or agm.USER_ID = '"+CommonFunction.checkNull(userId)+"') ");
				}
				
				bufInsSql.append(" GROUP by TRADE_CHECK_ID ");

					logger.info("get list for BuyerSuplier Query: "+bufInsSql.toString());

					TradeCheckInitVo vo= null;
					ArrayList list1 = ConnectionDAO.sqlSelect(bufInsSql.toString());
					
					bufInsSql=null;
					
					for(int i=0;i<list1.size();i++){
						logger.info("list1"+list1.size());
						ArrayList header1=(ArrayList)list1.get(i);
						if(header1!=null && header1.size()>0)
						{
							vo = new TradeCheckInitVo();
							vo.setTradeCheck((CommonFunction.checkNull(header1.get(0))).trim());
							vo.setBuyerSuplierName((CommonFunction.checkNull(header1.get(1))).trim());
							vo.setContactPerson((CommonFunction.checkNull(header1.get(2))).trim());
							vo.setRelation((CommonFunction.checkNull(header1.get(3))).trim());
	
							if(!CommonFunction.checkNull(header1.get(4)).equalsIgnoreCase(""))
							{
								Number avgPurchase = myFormatter.parse((CommonFunction.checkNull(header1.get(4))).trim());
								vo.setAveragePurchase(myFormatter.format(avgPurchase));
							}
							
							vo.setPaymentTerms((CommonFunction.checkNull(header1.get(5))).trim());
							vo.setProductType((CommonFunction.checkNull(header1.get(6))).trim());
							vo.setVintageOfRelationship((CommonFunction.checkNull(header1.get(7))).trim());
							vo.setTextarea((CommonFunction.checkNull(header1.get(8))).trim());
							vo.setVerificationId((CommonFunction.checkNull(header1.get(9))).trim());
							vo.setStatus((CommonFunction.checkNull(header1.get(10))).trim());
							vo.setEntityId((CommonFunction.checkNull(header1.get(11))).trim());
							tradeList.add(vo);
						}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return tradeList;
		}

	
	public ArrayList searchBuyerSupplierDetials(TradeCheckInitVo vo, String dealId,
			String verId,String entityId) {
		ArrayList list=new ArrayList();
		try
		{
			StringBuilder query=new StringBuilder();
			
			 query.append("Select TRADE_CHECK_ID,REFERENCE_NO,APPRAISER_NAME,DATE_FORMAT(APPRAISAL_DATE,'"+dateFormat+"'),VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,AVG_MONTHLY_SALES,"+
						" PAYMENT_TERMS,PRODUCT_TYPE,VINTAGE_OF_RELATION,ADDRESS,CITY,STATE,COUNTRY,PINCODE,TC_REMARKS from cr_trade_check_dtl "+
						" where VERIFICATION_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(verId)).trim()+" and DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" " +
						" and IFNULL(ENTITY_ID,0)='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(entityId)).trim()+"' ");
			
					logger.info("In TradeCheckDaoImpl------searchBuyerSupplierDetials() query is :-"+query);

					ArrayList list1 = ConnectionDAO.sqlSelect(query.toString());
					
					query=null;
					
					for(int i=0;i<list1.size();i++){
						logger.info("list1"+list1.size());
						ArrayList bSList=(ArrayList)list1.get(i);
						if(bSList!=null && bSList.size()>0)
						{
							vo = new TradeCheckInitVo();
							
							
							vo.setTradeCheckId((CommonFunction.checkNull(bSList.get(0))).trim());
							vo.setRefrenceNO((CommonFunction.checkNull(bSList.get(1))).trim());
							vo.setAppraiser((CommonFunction.checkNull(bSList.get(2))).trim());
							vo.setAppraisalDate((CommonFunction.checkNull(bSList.get(3))).trim());
							vo.setVerificationMode((CommonFunction.checkNull(bSList.get(4))).trim());
							vo.setContactPerson((CommonFunction.checkNull(bSList.get(5))).trim());
							vo.setRelation((CommonFunction.checkNull(bSList.get(6))).trim());
							vo.setPhone1((CommonFunction.checkNull(bSList.get(7))).trim());
							vo.setPhone2((CommonFunction.checkNull(bSList.get(8))).trim());
							vo.setEmail((CommonFunction.checkNull(bSList.get(9))).trim());
	
							if(!CommonFunction.checkNull(bSList.get(10)).equalsIgnoreCase(""))
							{
								Number avgPurchase = myFormatter.parse((CommonFunction.checkNull(bSList.get(10))).trim());
								vo.setAveragePurchase(myFormatter.format(avgPurchase));
							}
							
							vo.setPaymentTerms((CommonFunction.checkNull(bSList.get(11))).trim());
							vo.setProductType((CommonFunction.checkNull(bSList.get(12))).trim());
							vo.setVintageOfRelationship((CommonFunction.checkNull(bSList.get(13))).trim());
							
							vo.setAddress((CommonFunction.checkNull(bSList.get(14))).trim());
							vo.setCity((CommonFunction.checkNull(bSList.get(15))).trim());
							vo.setTxtStateCode((CommonFunction.checkNull(bSList.get(16))).trim());
							vo.setTxtCountryCode((CommonFunction.checkNull(bSList.get(17))).trim());
							vo.setPincode((CommonFunction.checkNull(bSList.get(18))).trim());
							vo.setTextarea((CommonFunction.checkNull(bSList.get(19))).trim());

							list.add(vo);
						}
				}
			}
	
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean modifyTradeBuyer(Object ob, String tradeCheckId) {
		
		TradeCheckInitVo vo = (TradeCheckInitVo) ob;
		boolean status = false;
		
		logger.info("In modifyTradeBuyer.....................................Dao Impl ");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		try {

				logger.info("In insert TradeBuyer");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_trade_check_dtl SET REFERENCE_NO=?,APPRAISER_NAME=?,APPRAISAL_DATE=STR_TO_DATE(?,'" + dateFormat+ "'),VERIFICATION_MODE=?," +
						"PERSON_MET=?,RELATION=?,PHONE1=?,PHONE2=?,E_MAIL=?,AVG_MONTHLY_SALES=?,PAYMENT_TERMS=?,PRODUCT_TYPE=?,VINTAGE_OF_RELATION=?,ADDRESS=?," +
						"CITY=?,STATE=?,COUNTRY=?,PINCODE=?,TC_REMARKS=?,MAKER_ID=?," +
						" MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " +
						" where TRADE_CHECK_ID=?");
				
				
				if((CommonFunction.checkNull(vo.getRefrenceNO())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRefrenceNO()).trim());
				
				if((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAppraiser()).trim());
				
				if((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAppraisalDate()).trim());
				
				if((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getVerificationMode()).trim());
				
				if((CommonFunction.checkNull(vo.getContactPerson())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("--");
				else
					insertPrepStmtObject.addString((vo.getContactPerson()).trim());
				
				if((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("--");
				else
					insertPrepStmtObject.addString((vo.getRelation()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPhone1()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getPhone2()).trim()).equalsIgnoreCase(""))
				    insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPhone2()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getEmail()).trim().toString());
				
				if((CommonFunction.checkNull(vo.getAveragePurchase())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0.0000");
				else
					insertPrepStmtObject.addString(myFormatter.parse((vo.getAveragePurchase()).trim()).toString());
								
				if((CommonFunction.checkNull(vo.getPaymentTerms())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("--");
				else
					insertPrepStmtObject.addString((vo.getPaymentTerms()).trim());
				
				if((CommonFunction.checkNull(vo.getProductType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("--");
				else
					insertPrepStmtObject.addString((vo.getProductType()).trim());

				if((CommonFunction.checkNull(vo.getVintageOfRelationship())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString((vo.getVintageOfRelationship()).trim());

				if((CommonFunction.checkNull(vo.getAddress())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getAddress()).trim());
				
				if((CommonFunction.checkNull(vo.getCity())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCity()).trim());

				if((CommonFunction.checkNull(vo.getTxtStateCode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtStateCode()).trim());

				if((CommonFunction.checkNull(vo.getTxtCountryCode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxtCountryCode()).trim());
				
				if((CommonFunction.checkNull(vo.getPincode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getPincode()).trim());

				if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
				
				//---------------
				if((CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
								
				if((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));

		    	//---------------
				
				if((CommonFunction.checkNull(tradeCheckId)).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((tradeCheckId).trim());

				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN insert TradeBuyer() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveTradeBuyerData......................"+ status);
			
				bufInsSql=null;
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}

	public boolean modifyTradecheck(Object ob, String dealId,String verificationID) {
		TradeCheckInitVo vo = (TradeCheckInitVo) ob;
		boolean status = false;
		
		logger.info("In modifyTradecheck.....................................TradeCheckDaoImpl ");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		try {
				StringBuffer bufInsSql = new StringBuffer();

				bufInsSql.append("UPDATE cr_trade_check_dtl a, cr_deal_verification_dtl b SET a.REC_STATUS='F', b.rec_status='R'," );
				bufInsSql.append("a.MAKER_ID=?,a.MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );
				bufInsSql.append("b.MAKER_ID=?,b.MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " );
				bufInsSql.append(" where a.deal_id=b.deal_id and a.VERIFICATION_ID=b.VERIFICATION_ID and b.VERIFICATION_TYPE='TRADE CHECK'    and a.DEAL_ID=? and a.VERIFICATION_ID in ("+verificationID+")");
				//---------------
		    	if((CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
								
				if((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));

				if((CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
								
				if((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));

		    	//---------------				
				if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((dealId).trim());
				
				

				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN modifyTradecheck() UPDATE query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
				bufInsSql=null;
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
	
	public boolean modifyTradeRemark(Object ob, String dealId) {
		TradeCheckInitVo vo = (TradeCheckInitVo) ob;
		boolean status = false;
		
		logger.info("In modifyTradecheck.....................................TradeCheckDaoImpl ");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
		PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();
	
		try {
			
			 if(!CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("S"))
			 {
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_trade_check_dtl tcd, cr_deal_verification_dtl dv SET dv.VERIFICATION_STATUS=?," +
						"dv.VERIFICATION_REMARKS=?,tcd.rec_status=?, dv.rec_status=?,tcd.MAKER_ID=?," +
						"tcd.MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ," +
						"dv.MAKER_ID=?,dv.MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where " +
						"tcd.deal_id=dv.deal_id and tcd.verification_id=dv.verification_id and tcd.DEAL_ID=?" +
						"and tcd.rec_status='F' and dv.rec_status='R'");
				
				if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDecison()).trim());
				
				if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
				
				insertPrepStmtObject.addString("A");
				insertPrepStmtObject.addString("C");
				
				//---------------
		    	if((CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
								
				if((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));

				if((CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
								
				if((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));

		    	//---------------
				if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((dealId).trim());

				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN modifyTradecheck() UPDATE query0 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
			 }
			 else
			 {
				 StringBuffer bufInsSql = new StringBuffer();
				 bufInsSql.append("UPDATE cr_trade_check_dtl tcd, cr_deal_verification_dtl dv SET dv.VERIFICATION_STATUS=?," +
							"dv.VERIFICATION_REMARKS=?,tcd.rec_status=?, dv.rec_status=?,tcd.MAKER_ID=?," +
							"tcd.MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ," +
							"dv.MAKER_ID=?,dv.MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where " +
							"tcd.deal_id=dv.deal_id and tcd.verification_id=dv.verification_id and tcd.DEAL_ID=?" +
							"and tcd.rec_status='F' and dv.rec_status='R'");
					
					if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDecison()).trim());
					
					if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTextarea()).trim());
					
					insertPrepStmtObject.addString("P");
					insertPrepStmtObject.addString("F");
					
			    	if((CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
									
					if((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));

					if((CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
									
					if((CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));

					if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((dealId).trim());

					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					
					logger.info("IN modifyTradecheck() UPDATE query0 ### "+ insertPrepStmtObject.printQuery());
					
					// UPDATE DEALMOVEMENT TABLE
					 StringBuffer bufInsSql1 = new StringBuffer();
					bufInsSql1.append("UPDATE CR_DEAL_MOVEMENT_DTL SET REC_STATUS = 'X' WHERE REC_STATUS = 'A' AND DEAL_STAGE_ID='TCC' AND DEAL_ID=?");
															
						if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject1.addNull();
						else
							insertPrepStmtObject1.addString((dealId).trim());

						
						insertPrepStmtObject1.setSql(bufInsSql1.toString());
						
						logger.info("IN modifyTradecheck() UPDATE query1 ### "+ insertPrepStmtObject1.printQuery());
						
						
						
						// INSERT DEALMOVEMENT TABLE
						StringBuffer bufInsSql2 = new StringBuffer();
						bufInsSql2.append("INSERT INTO CR_DEAL_MOVEMENT_DTL(DEAL_ID,DEAL_STAGE_ID,DEAL_ACTION,DEAL_RECEIVED,DEAL_RECEIVED_USER,REC_STATUS)VALUES(?, 'TCC', 'I',  DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,?,'A')");
																
							if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
								insertPrepStmtObject2.addNull();
							else
								insertPrepStmtObject2.addString((dealId).trim());
							
							
							if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject2.addNull();
							else
								insertPrepStmtObject2.addString((vo.getMakerDate()).trim());
							
							if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject2.addNull();
							else
								insertPrepStmtObject2.addString((vo.getMakerId()).trim());

							
							
							
	               			insertPrepStmtObject2.setSql(bufInsSql2.toString());
							
							logger.info("IN modifyTradecheck() INSERT query2****"+ insertPrepStmtObject2.printQuery());
							
					
						qryList.add(insertPrepStmtObject);
						qryList.add(insertPrepStmtObject1);
						qryList.add(insertPrepStmtObject2);
					    status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			 }
			 
			 
				
			

				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}


	public ArrayList searchVerId(TradeCheckInitVo vo,String userId, String dealId) {
		ArrayList list=new ArrayList();
		try
		{
			StringBuilder query=new StringBuilder();
			
			 query.append("select dv.VERIFICATION_ID from cr_deal_verification_dtl dv left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE where dv.DEAL_ID="+dealId+" and (dv.internal_appraiser = '"+userId+"' or agm.USER_ID = '"+userId+"') and dv.VERIFICATION_ACTION='I' and dv.VERIFICATION_TYPE='TRADE CHECK'    and dv.REC_STATUS='F'");
			
					logger.info("In TradeCheckDaoImpl------searchVerId() query is :-"+query);

					ArrayList list1 = ConnectionDAO.sqlSelect(query.toString());
						
					
					query=null;
					
					for(int i=0;i<list1.size();i++){
						logger.info("list1"+list1.size());
						ArrayList bSList=(ArrayList)list1.get(i);
						if(bSList!=null && bSList.size()>0)
						{
							vo = new TradeCheckInitVo();
						
							vo.setVerCheckId((CommonFunction.checkNull(bSList.get(0))).trim());
							list.add(vo);
						}
				}
			}
	
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}







	
