package com.cp.daoImplMSSQL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.collateralVerification.vo.CollateralCapturingVO;
import com.cp.dao.FieldVerificationDAO;
import com.cp.vo.CodeDescVo;
import com.cp.vo.FieldVerificationVo;
import com.cp.vo.HeaderInfoVo;
import com.cp.vo.NewCaptureVO;
import com.cp.vo.UpdateVo;

public class MSSQLFieldVerificationDAOImpl implements FieldVerificationDAO
{
	private static final Logger logger = Logger.getLogger(MSSQLFieldVerificationDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	Connection con=null;
	Statement stmt=null;
	PreparedStatement pstmt=null;
	CallableStatement cstm=null;
	ResultSet rs=null;
	StringBuffer bufInsUpdSql = null;
	ArrayList qryList = null;
	ArrayList qryList1 = null;
	CallableStatement cs = null;
	PrepStmtObject  delPrepStmtObject = null;
	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	//DecimalFormat myFormatter = new DecimalFormat("###.##");

public ArrayList<FieldVerificationVo> getDefaultCaptureData(FieldVerificationVo vo) 
{
	logger.info("In getDefaultCaptureData() method of FieldVerificationDAOImpl");
	ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	logger.info("In getDefaultCaptureData .......FieldVerificationDAOImpl");
	StringBuilder bufInsSql=new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
	String userId=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId())).trim();
	try
	{
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+userId);
		String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+userId+"'";
		String userName=ConnectionDAO.singleReturn(userNameQ);
		logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		ArrayList header=null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		FieldVerificationVo fieldVo= (FieldVerificationVo) vo;
		boolean appendSQL=false;		
		
		bufInsSql.append("select distinct cr_deal_verification_dtl.DEAL_ID,cr_deal_dtl.DEAL_NO,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,cr_deal_verification_dtl.ENTITY_ID,cr_deal_verification_dtl.ENTITY_DESC,cr_deal_verification_dtl.ENTITY_TYPE,cr_deal_verification_dtl.ENTITY_SUB_TYPE,cr_deal_customer_m.CUSTOMER_NAME,cr_deal_verification_dtl.VERIFICATION_ID ");
		bufInsSql.append(" from cr_deal_verification_dtl ");
		bufInsSql.append(" INNER join cr_deal_dtl on(cr_deal_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID AND cr_deal_dtl.DEAL_BRANCH='"+fieldVo.getBranchId()+"') ");
		bufInsSql.append(" inner join cr_deal_loan_dtl on(cr_deal_loan_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID) ");
		bufInsSql.append(" inner join cr_product_m	on cr_deal_loan_dtl.DEAL_PRODUCT=cr_product_m.PRODUCT_ID  ");
		bufInsSql.append(" inner join cr_scheme_m	on cr_deal_loan_dtl.DEAL_SCHEME=cr_scheme_m.SCHEME_ID	 ");
		bufInsSql.append(" inner join cr_deal_customer_m  on cr_deal_customer_m.CUSTOMER_ID=cr_deal_dtl.DEAL_CUSTOMER_ID  	 ");
		bufInsSql.append(" where cr_deal_verification_dtl.REC_STATUS IN ('F') and cr_deal_verification_dtl.VERIFICATION_ACTION='I' ");
		bufInsSql.append(" and cr_deal_verification_dtl.VERIFICATION_ID in(select VERIFICATION_ID from cr_deal_verification_dtl where REC_STATUS IN ('F')  and VERIFICATION_ACTION='I' and APPRAISER_TYPE='INTERNAL' and INTERNAL_APPRAISER='"+userId+"'   ");
		bufInsSql.append(" union select distinct VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' and '"+userId+"' in(select distinct com_agency_user_mapping.USER_ID from cr_deal_verification_dtl INNER join com_agency_m on cr_deal_verification_dtl.external_appraiser=com_agency_m.AGENCY_CODE  INNER join com_agency_user_mapping on com_agency_m.AGENCY_CODE=com_agency_user_mapping.AGENCY_CODE  where  cr_deal_verification_dtl.REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' ) union select -1) ");
		
		bufInsSqlTempCount.append(" select count(1) from(select distinct cr_deal_verification_dtl.DEAL_ID,cr_deal_dtl.DEAL_NO,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,cr_deal_verification_dtl.ENTITY_ID,cr_deal_verification_dtl.ENTITY_DESC,cr_deal_verification_dtl.ENTITY_TYPE,cr_deal_verification_dtl.ENTITY_SUB_TYPE,cr_deal_customer_m.CUSTOMER_NAME,cr_deal_verification_dtl.VERIFICATION_ID from cr_deal_verification_dtl ");
		bufInsSqlTempCount.append(" INNER join cr_deal_dtl on(cr_deal_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID AND cr_deal_dtl.DEAL_BRANCH='"+fieldVo.getBranchId()+"' ) ");
		bufInsSqlTempCount.append(" inner join cr_deal_loan_dtl on(cr_deal_loan_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID) ");
		bufInsSqlTempCount.append(" inner join cr_product_m	on cr_deal_loan_dtl.DEAL_PRODUCT=cr_product_m.PRODUCT_ID  ");
		bufInsSqlTempCount.append(" inner join cr_scheme_m	on cr_deal_loan_dtl.DEAL_SCHEME=cr_scheme_m.SCHEME_ID	 ");
		
		bufInsSqlTempCount.append(" inner join cr_deal_customer_m  on cr_deal_customer_m.CUSTOMER_ID=cr_deal_dtl.DEAL_CUSTOMER_ID  	 ");
		bufInsSqlTempCount.append(" where cr_deal_verification_dtl.REC_STATUS IN ('F') and cr_deal_verification_dtl.VERIFICATION_ACTION='I' ");
		bufInsSqlTempCount.append(" and cr_deal_verification_dtl.VERIFICATION_ID in(select VERIFICATION_ID from cr_deal_verification_dtl where REC_STATUS IN ('F')  and VERIFICATION_ACTION='I' and APPRAISER_TYPE='INTERNAL' and INTERNAL_APPRAISER='"+userId+"'   ");
		bufInsSqlTempCount.append(" union select distinct VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' and '"+userId+"' in(select distinct com_agency_user_mapping.USER_ID from cr_deal_verification_dtl INNER join com_agency_m on cr_deal_verification_dtl.external_appraiser=com_agency_m.AGENCY_CODE  INNER join com_agency_user_mapping on com_agency_m.AGENCY_CODE=com_agency_user_mapping.AGENCY_CODE  where  cr_deal_verification_dtl.REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' ) union select -1)) as b ");
		
		logger.info("query : "+bufInsSql.toString());
		logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
	    count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		logger.info("current PAge Link no .................... "+fieldVo.getCurrentPageLink());
		if(fieldVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
			bufInsSql.append(" ORDER BY cr_deal_verification_dtl.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search getDefaultCaptureData query for SQL SERVER : " + bufInsSql.toString());

			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));				// asesh changes for mssql...

			
		}
		header = ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size=header.size();		
		logger.info("header: "+header.size());
		for(int i=0;i<size;i++)
		{			
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				fieldVo = new FieldVerificationVo();					
				fieldVo.setDealId((CommonFunction.checkNull(header1.get(0))).trim());
				fieldVo.setDealNo("<a href=fieldVarificationAction.do?method=addFieldCapture&dealId="+(CommonFunction.checkNull(header1.get(0))).trim()+"&entityId="+(CommonFunction.checkNull(header1.get(4))).trim()+"&verificationId="+(CommonFunction.checkNull(header1.get(9))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
				fieldVo.setProduct((CommonFunction.checkNull(header1.get(2))).trim());
				fieldVo.setScheme((CommonFunction.checkNull(header1.get(3))).trim());
				fieldVo.setEntId((CommonFunction.checkNull(header1.get(4))).trim());
				fieldVo.setEntityDesc((CommonFunction.checkNull(header1.get(5))).trim());
				if(CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase("PRAPPL"))
				 {
					fieldVo.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase("COAPPL"))
				 {
					 fieldVo.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 fieldVo.setEntityType(CommonFunction.checkNull(header1.get(6)));
				 }
				fieldVo.setEntitySubType((CommonFunction.checkNull(header1.get(7))).trim());
				fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(8))).trim());
				fieldVo.setUserName(userName);
				fieldVo.setTotalRecordSize(count);
				list.add(fieldVo);
			}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
	finally
	{
		bufInsSql=null;
		bufInsSqlTempCount=null;
	}
	return list;
}
public ArrayList<FieldVerificationVo> getDefaultCompletionData(FieldVerificationVo vo) 
{
	ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	logger.info("In getDefaultCompletionData .......FieldVerificationDAOImpl");
	try
	{
		ArrayList header=null;
        int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		FieldVerificationVo fieldVo= (FieldVerificationVo) vo;
		boolean appendSQL=false;			
		StringBuffer bufInsSql=new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		
		bufInsSql.append(" select distinct A.DEAL_ID,cr_deal_dtl.DEAL_NO,dbo.DATE_FORMAT(cr_deal_dtl.DEAL_DATE,'%d-%m-%Y'), ");
		bufInsSql.append(" CR_DEAL_CUSTOMER_M.CUSTOMER_NAME,CR_PRODUCT_M.PRODUCT_DESC,CR_SCHEME_M.SCHEME_DESC, ");
		bufInsSql.append(" CASE WHEN CR_DEAL_DTL.REC_STATUS='F' THEN 'FORWARDED' END AS STATUS "); 
		bufInsSql.append(" FROM CR_DEAL_VERIFICATION_DTL A "); 
		bufInsSql.append(" INNER JOIN CR_DEAL_DTL ON(CR_DEAL_DTL.DEAL_ID=A.DEAL_ID AND CR_DEAL_DTL.REC_STATUS='F' AND CR_DEAL_DTL.DEAL_BRANCH='"+fieldVo.getBranchId()+"') ");
		bufInsSql.append(" INNER JOIN CR_DEAL_LOAN_DTL ON(CR_DEAL_LOAN_DTL.DEAL_ID=A.DEAL_ID) ");
		bufInsSql.append(" INNER JOIN CR_PRODUCT_M ON(CR_DEAL_LOAN_DTL.DEAL_PRODUCT=CR_PRODUCT_M.PRODUCT_ID) "); 
		bufInsSql.append(" INNER JOIN CR_SCHEME_M ON(CR_DEAL_LOAN_DTL.DEAL_SCHEME=CR_SCHEME_M.SCHEME_ID) ");
		bufInsSql.append(" INNER JOIN CR_DEAL_CUSTOMER_M  ON(CR_DEAL_CUSTOMER_M.CUSTOMER_ID=CR_DEAL_DTL.DEAL_CUSTOMER_ID) "); 
		bufInsSql.append(" INNER JOIN CR_FIELD_VERIFICATION_DTL B ON(B.DEAL_ID=A.DEAL_ID AND B.STAGE=A.STAGE) ");
		bufInsSql.append(" LEFT JOIN ");
		bufInsSql.append("( ");
		bufInsSql.append("		SELECT DEAL_ID,COUNT(1)cnt "); 
		bufInsSql.append("		FROM CR_DEAL_VERIFICATION_DTL "); 
		bufInsSql.append("		WHERE VERIFICATION_ACTION='I' AND STAGE='DC' AND REC_STATUS='F' "); 
		bufInsSql.append("		GROUP BY DEAL_ID ");
		bufInsSql.append(")CT ON(CR_DEAL_DTL.DEAL_ID=CT.DEAL_ID) ");
		bufInsSql.append(" LEFT JOIN ");
		bufInsSql.append("( ");
		bufInsSql.append("		SELECT DEAL_ID,COUNT(1)cnt "); 
		bufInsSql.append("		FROM CR_FIELD_VERIFICATION_DTL "); 
		bufInsSql.append("		WHERE MAKER_ID='"+vo.getUserId()+"' GROUP BY DEAL_ID ");
		bufInsSql.append(")UD ON(CR_DEAL_DTL.DEAL_ID=UD.DEAL_ID) ");
		bufInsSql.append("WHERE A.REC_STATUS<>'C' AND A.VERIFICATION_ACTION='I' AND A.STAGE='DC' AND CT.DEAL_ID IS NULL AND UD.DEAL_ID IS NULL order by A.DEAL_ID");
		
		bufInsSqlTempCount.append(" select COUNT(1) from ( select distinct A.DEAL_ID,cr_deal_dtl.DEAL_NO,dbo.DATE_FORMAT(cr_deal_dtl.DEAL_DATE,'%d-%m-%Y')dt, ");
		bufInsSqlTempCount.append(" CR_DEAL_CUSTOMER_M.CUSTOMER_NAME,CR_PRODUCT_M.PRODUCT_DESC,CR_SCHEME_M.SCHEME_DESC, ");
		bufInsSqlTempCount.append(" CASE WHEN CR_DEAL_DTL.REC_STATUS='F' THEN 'FORWARDED' END AS STATUS "); 
		bufInsSqlTempCount.append(" FROM CR_DEAL_VERIFICATION_DTL A "); 
		bufInsSqlTempCount.append(" INNER JOIN CR_DEAL_DTL ON(CR_DEAL_DTL.DEAL_ID=A.DEAL_ID AND CR_DEAL_DTL.REC_STATUS='F' AND CR_DEAL_DTL.DEAL_BRANCH='"+fieldVo.getBranchId()+"') ");
		bufInsSqlTempCount.append(" INNER JOIN CR_DEAL_LOAN_DTL ON(CR_DEAL_LOAN_DTL.DEAL_ID=A.DEAL_ID) ");
		bufInsSqlTempCount.append(" INNER JOIN CR_PRODUCT_M ON(CR_DEAL_LOAN_DTL.DEAL_PRODUCT=CR_PRODUCT_M.PRODUCT_ID) "); 
		bufInsSqlTempCount.append(" INNER JOIN CR_SCHEME_M ON(CR_DEAL_LOAN_DTL.DEAL_SCHEME=CR_SCHEME_M.SCHEME_ID) ");
		bufInsSqlTempCount.append(" INNER JOIN CR_DEAL_CUSTOMER_M  ON(CR_DEAL_CUSTOMER_M.CUSTOMER_ID=CR_DEAL_DTL.DEAL_CUSTOMER_ID) "); 
		bufInsSqlTempCount.append(" INNER JOIN CR_FIELD_VERIFICATION_DTL B ON(B.DEAL_ID=A.DEAL_ID AND B.STAGE=A.STAGE) ");
		bufInsSqlTempCount.append(" LEFT JOIN ");
		bufInsSqlTempCount.append("( ");
		bufInsSqlTempCount.append("		SELECT DEAL_ID,COUNT(1)cnt "); 
		bufInsSqlTempCount.append("		FROM CR_DEAL_VERIFICATION_DTL "); 
		bufInsSqlTempCount.append("		WHERE VERIFICATION_ACTION='I' AND STAGE='DC' AND REC_STATUS='F' "); 
		bufInsSqlTempCount.append("		GROUP BY DEAL_ID ");
		bufInsSqlTempCount.append(")CT ON(CR_DEAL_DTL.DEAL_ID=CT.DEAL_ID) ");
		bufInsSqlTempCount.append(" LEFT JOIN ");
		bufInsSqlTempCount.append("( ");
		bufInsSqlTempCount.append("		SELECT DEAL_ID,COUNT(1)cnt "); 
		bufInsSqlTempCount.append("		FROM CR_FIELD_VERIFICATION_DTL "); 
		bufInsSqlTempCount.append("		WHERE MAKER_ID='"+vo.getUserId()+"' GROUP BY DEAL_ID ");
		bufInsSqlTempCount.append(" )UD ON(CR_DEAL_DTL.DEAL_ID=UD.DEAL_ID) ");
		bufInsSqlTempCount.append(" WHERE A.REC_STATUS<>'C' AND A.VERIFICATION_ACTION='I' AND A.STAGE='DC' AND CT.DEAL_ID IS NULL AND UD.DEAL_ID IS NULL )b");
		
		logger.info("current PAge Link no .................... "+count);
		if(fieldVo.getCurrentPageLink()>1)
		{
			 startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
			 endRecordIndex = no;
			 logger.info("startRecordIndex .................... "+startRecordIndex);
			 logger.info("endRecordIndex .................... "+endRecordIndex);
			 
			 	
			bufInsSql.append(" ORDER BY cr_deal_verification_dtl.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search getDefaultCompletionData query for SQL SERVER : " + bufInsSql.toString());
	
			 //bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));	
			 
		}
		logger.info("query : "+bufInsSql);
		logger.info("count query : "+bufInsSqlTempCount.toString());
		
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		header = ConnectionDAO.sqlSelect(bufInsSql.toString());
		for(int i=0;i<header.size();i++)
		{
			 ArrayList header1=(ArrayList)header.get(i);
			 if(header1!=null && header1.size()>0)
			 {
				 fieldVo = new FieldVerificationVo();					
				 fieldVo.setDealId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(0))).trim());
				 fieldVo.setDealNo("<a href=fieldVarificationComAction.do?method=addFieldComCapture&dealId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(0))).trim()+">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(1)))+"</a>");
				 fieldVo.setInitiationDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(2))).trim());
				 fieldVo.setCustomerName(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(3))).trim());
				 fieldVo.setProduct(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(4))).trim());
				 fieldVo.setScheme(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(5))).trim());
				 fieldVo.setStatus(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(6))).trim());
				 fieldVo.setTotalRecordSize(count);
				 list.add(fieldVo);
			 }
		 }
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}		
	return list;
}
public ArrayList checkCustomerId(String dealId)
{
	ArrayList list=new ArrayList();
	try
	{
		String query="select distinct ENTITY_ID,VERIFICATION_SUB_TYPE from cr_field_verification_dtl where DEAL_ID='"+dealId+"' order by ENTITY_ID";			
		logger.info("In checkCustomerID......."+query);
		list = ConnectionDAO.sqlSelect(query);
	}
	catch(Exception e)
	{logger.error("Exception in checkDealID() :: "+e.getMessage());}		
	return list;
}
public boolean forwardRec(String dealId, String varID,String makerId,String makerDate)
{
	boolean status= false;
	boolean first=false;
	ArrayList qryList1 = new ArrayList();	
	ArrayList qryList2 = new ArrayList();
	PrepStmtObject insertPrepStmtObject1=new PrepStmtObject();	
	PrepStmtObject insertPrepStmtObject2=new PrepStmtObject();	
	try 
	{
		String query1="update cr_deal_verification_dtl SET rec_status='R' where verification_type='F' and VERIFICATION_ACTION='I' and VERIFICATION_ID in("+varID+") and DEAL_ID='"+dealId+"'";
		insertPrepStmtObject1.setSql(query1);
		logger.info("IN update cr_deal_verification_dtl table : Query  :  "+ insertPrepStmtObject1.printQuery());
		qryList1.add(insertPrepStmtObject1);	
		first = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
		if(first)
		{
				StringBuilder query2=new StringBuilder();
				query2.append("update CR_FIELD_VERIFICATION_DTL set REC_STATUS='F',MAKER_ID='"+makerId+"',MAKER_DATE=");
				//query2.append("DATE_ADD(STR_TO_DATE('"+makerDate+"','"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
				// saurabh space starts
				query2.append(dbo);
				query2.append(" STR_TO_DATE('"+makerDate+"','"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				// saurabh space ends
			 
				query2.append("where DEAL_ID='"+dealId+"' and VERIFICATION_ID in("+varID+") ");
				insertPrepStmtObject2.setSql(query2.toString());
				logger.info("IN update cr_deal_verification_dtl table : Query  :  "+ insertPrepStmtObject2.printQuery());
				qryList2.add(insertPrepStmtObject2);	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
		}
   	}
	catch (Exception e) 
	{e.printStackTrace();}
	return status;
}
//for field initiation
public boolean checkDealID(Object ob)
{
	boolean empSt = false;
	boolean status = true;
	try
	{
		FieldVerificationVo vo = (FieldVerificationVo) ob;
        String empQuery = "SELECT DEAL_ID FROM CR_DEAL_VERIFICATION_DTL where  VERIFICATION_STATUS='I' AND DEAL_ID='"+vo.getLbxDealNo()+"'";
		logger.info("In checkDealID.................Dao Impl"+ empQuery);
		empSt = ConnectionDAO.checkStatus(empQuery);
		if(empSt)
		{
			status=false;
		}
	}
	catch(Exception e)
	{
		logger.error("Exception in checkDealID() :: "+e.getMessage());
	}
	return status;
}	
public ArrayList<UpdateVo> getDetail(String customer_id,String deal_id,String varSub)
{
	ArrayList<UpdateVo> list=new ArrayList<UpdateVo>();
	String query ="select DEAL_ID,ENTITY_ID,REFERENCE_NO,VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,APPRAISER_NAME,APPRAISAL_DATE,"+
				" VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,CONFIRMED,OFFICE_FAILITIES,BUSINESS_BOARD,"+
				" BUSINESS_ACTIVITY,WORK_ENVIRONMENT,OFFICE_INTERIOR,OFFICE_EXTERIOR,FV_STATUS,FV_NEGATIVE_REASON,"+
				" REASON_DESC,FV_REMARKS,CR_FIELD_VERIFICATION_DTL.MAKER_ID,CR_FIELD_VERIFICATION_DTL.MAKER_DATE,ADDRESS_TYPE,VERIFICATION_ID"+
				" from CR_FIELD_VERIFICATION_DTL left outer join com_reason_m on com_reason_m.rec_status='A' And com_reason_m.REASON_ID=CR_FIELD_VERIFICATION_DTL.FV_NEGATIVE_REASON"+
				" where DEAL_ID='"+deal_id+"' and ENTITY_ID = '"+customer_id+"' and VERIFICATION_SUB_TYPE = '"+varSub+"'";
	logger.info("query : "+query);
	UpdateVo vo= null;
	try
	{
		ArrayList header = ConnectionDAO.sqlSelect(query);
		for(int i=0;i<header.size();i++)
		{	ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				vo = new UpdateVo();
				vo.setDeal_id((CommonFunction.checkNull(header1.get(0))).trim());
				vo.setCustomer_id((CommonFunction.checkNull(header1.get(1))).trim());
				vo.setReferenceId((CommonFunction.checkNull(header1.get(2))).trim());
				vo.setVarificationType((CommonFunction.checkNull(header1.get(3))).trim());
				vo.setVarificationSubType((CommonFunction.checkNull(header1.get(4))).trim());
				vo.setAppraisalName((CommonFunction.checkNull(header1.get(5))).trim());
				vo.setAppraisalDate((CommonFunction.checkNull(header1.get(6))).trim());
				vo.setVerificationMode((CommonFunction.checkNull(header1.get(7))).trim());
				vo.setPersonToMeet((CommonFunction.checkNull(header1.get(8))).trim());
				vo.setRelationWithApplicant((CommonFunction.checkNull(header1.get(9))).trim());
				vo.setPhone1((CommonFunction.checkNull(header1.get(10))).trim());
				vo.setPhone2((CommonFunction.checkNull(header1.get(11))).trim());
				vo.setEmail((CommonFunction.checkNull(header1.get(12))).trim());
				vo.setOfficeAddress((CommonFunction.checkNull(header1.get(13))).trim());
				vo.setOfficeFacilities((CommonFunction.checkNull(header1.get(14))).trim());
				vo.setBussinessBoardScene((CommonFunction.checkNull(header1.get(15))).trim());
				vo.setBussinessActivity((CommonFunction.checkNull(header1.get(16))).trim());
				vo.setWorkEnvironment((CommonFunction.checkNull(header1.get(17))).trim());
				vo.setOfficeInteriors((CommonFunction.checkNull(header1.get(18))).trim());
				vo.setOfficeExteriors((CommonFunction.checkNull(header1.get(19))).trim());
				vo.setCPVStatus((CommonFunction.checkNull(header1.get(20))).trim());
				vo.setLbxReasonId((CommonFunction.checkNull(header1.get(21))).trim());
				vo.setCPVNegReasonDesc((CommonFunction.checkNull(header1.get(22))).trim());
				vo.setRemarks((CommonFunction.checkNull(header1.get(23))).trim());
				vo.setMakerId((CommonFunction.checkNull(header1.get(24))).trim());
				vo.setMakerDate((CommonFunction.checkNull(header1.get(25))).trim());
				vo.setAddressType((CommonFunction.checkNull(header1.get(26))).trim());
				vo.setVerificationId((CommonFunction.checkNull(header1.get(27))).trim());
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


	public ArrayList<FieldVerificationVo> initiatedVerificationDealDetail(FieldVerificationVo vo) {
		
		ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
		logger.info("In initiatedVerificationDealDetail ");
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			
			logger.info(" initiatedVerificationDealDetail() here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
 			String userName=ConnectionDAO.singleReturn(userNameQ);
 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			
 			
 			FieldVerificationVo fieldVo= (FieldVerificationVo) vo;
			boolean appendSQL=false;
			
			
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			String dealNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
			String appDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationDate())).trim());
			String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
			String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
			String schemeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
			String initDate= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate())).trim());
			bufInsSql.append(" select distinct d.DEAL_ID,d.DEAL_NO,deal.CUSTOMER_NAME,");
			bufInsSql.append(" p.PRODUCT_DESC,s.SCHEME_DESC,");
			bufInsSqlTempCount.append(" select distinct count(1) from (select distinct d.DEAL_ID,d.DEAL_NO,deal.CUSTOMER_NAME,");
			bufInsSqlTempCount.append(" p.PRODUCT_DESC,s.SCHEME_DESC,");
			
			bufInsSql.append(" CASE WHEN dv.REC_STATUS='P' THEN 'In pross' WHEN d.REC_STATUS IN ('P','F') THEN 'Pending' end as rsstatus,D.DEAL_RM,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(D.DEAL_DATE,'"+dateFormat+"')");
			bufInsSqlTempCount.append(" CASE WHEN dv.REC_STATUS='P' THEN 'In pross' WHEN d.REC_STATUS IN ('P','F') THEN 'Pending' end as rsstatus,D.DEAL_RM,");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("DATE_FORMAT(D.DEAL_DATE,'"+dateFormat+"')DEALDATE");
			
			bufInsSql.append(" ,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CASE WHEN dv.REC_STATUS='P' THEN dv.MAKER_ID ELSE D.MAKER_ID END) MAKER_ID ");
			bufInsSqlTempCount.append(" ,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CASE WHEN dv.REC_STATUS='P' THEN dv.MAKER_ID ELSE D.MAKER_ID END) MAKER_ID ");
			bufInsSql.append(" from cr_deal_dtl d left join cr_deal_verification_dtl dv on dv.stage='DC' and d.DEAL_ID=dv.DEAL_ID ");
			bufInsSqlTempCount.append(" from cr_deal_dtl d left join cr_deal_verification_dtl dv on dv.stage='DC' and d.DEAL_ID=dv.DEAL_ID ");
			
			bufInsSql.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID "); 
			bufInsSqlTempCount.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID "); 
			bufInsSql.append(" INNER join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "); 
			bufInsSqlTempCount.append(" INNER join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "); 
			bufInsSql.append(" INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID AND DM.DEAL_RECEIVED IS NOT NULL AND DM.DEAL_FORWARDED IS NULL AND DM.DEAL_STAGE_ID='FVI' AND DM.REC_STATUS='A' "); 
			bufInsSqlTempCount.append(" INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID  AND DM.DEAL_RECEIVED IS NOT NULL AND DM.DEAL_FORWARDED IS NULL AND DM.DEAL_STAGE_ID='FVI' AND DM.REC_STATUS='A'"); 
			bufInsSql.append(" INNER join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID Where d.REC_STATUS IN ('P','F') "); 
			bufInsSqlTempCount.append(" INNER join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID Where d.REC_STATUS IN ('P','F') "); 
			//bufInsSql.append(" and d.DEAL_ID not in(select DEAL_ID from cr_deal_verification_dtl where  REC_STATUS in ('C')) AND ");
			bufInsSql.append(" AND ((ISNULL(dv.REC_STATUS,'X')='P' AND dv.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' ) OR (ISNULL(dv.REC_STATUS,'X')<>'P' AND 'a'='a')) AND D.DEAL_BRANCH='"+vo.getBranchId()+"'"); 
			//bufInsSqlTempCount.append(" and d.DEAL_ID not in(select DEAL_ID from cr_deal_verification_dtl where  REC_STATUS in ('C')) AND  ");
			bufInsSqlTempCount.append(" AND ((ISNULL(dv.REC_STATUS,'X')='P' AND dv.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' ) OR (ISNULL(dv.REC_STATUS,'X')<>'P' AND 'a'='a')) AND D.DEAL_BRANCH='"+vo.getBranchId()+"'");
			 

			
			if (!((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReportingToUserId())).trim().equalsIgnoreCase(""))))
			{
				bufInsSql.append(" AND d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' ");	
				bufInsSqlTempCount.append(" AND d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"'");
				appendSQL = true;
			}
			
			if((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")) &&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate()).trim()).equalsIgnoreCase("")))
			{
				
		   	  bufInsSqlTempCount.append(") as b");
			}
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase(""))) &&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate()).trim()).equalsIgnoreCase(""))))
			{
		   	  bufInsSql.append("WHERE d.DEAL_ID='"+dealNo+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND ");
		   	  bufInsSql.append(dbo);
		   	  bufInsSql.append("date(dv.MAKER_DATE) =");
		   	  bufInsSql.append(dbo);
		   	  bufInsSql.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+"','"+dateFormat+"')");
		   	  bufInsSqlTempCount.append("WHERE d.DEAL_ID='"+dealNo+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND ");
		   	  bufInsSqlTempCount.append(dbo);
		   	  bufInsSqlTempCount.append("date(dv.MAKER_DATE) =");
		   	  bufInsSqlTempCount.append(dbo);
		   	  bufInsSqlTempCount.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+"','"+dateFormat+"')) as b");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getInitiationDate().equalsIgnoreCase("")))){
				appendSQL=true;
			}
			
			 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
		        bufInsSql.append(" AND d.DEAL_ID='"+dealNo+"' ");
		        bufInsSqlTempCount.append(" AND d.DEAL_ID='"+dealNo+"' ");
		   	 	appendSQL=true;
		   	  
		     }
			 
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND ");
				bufInsSql.append(dbo);
				bufInsSql.append("date(dv.MAKER_DATE) =");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+"','"+dateFormat+"') ");
				bufInsSqlTempCount.append(" AND ");
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("date(dv.MAKER_DATE) =");
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+"','"+dateFormat+"') ");
				appendSQL=true;
		     }
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' ");
				bufInsSqlTempCount.append(" AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%'");
		   	  	appendSQL=true;
		     }
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
			   	  bufInsSqlTempCount.append(" AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'");
			   	  appendSQL=true;
			     }
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append(" AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
			   	 bufInsSqlTempCount.append(" AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"'");
			   	  appendSQL=true;
			     }
			if((!(vo.getLbxDealNo().equalsIgnoreCase("")))||(!(vo.getCustomerName().equalsIgnoreCase("")))||(!(vo.getLbxProductID().equalsIgnoreCase("")))||(!(vo.getLbxscheme().equalsIgnoreCase("")))||(!(vo.getInitiationDate().equalsIgnoreCase("")))){
				bufInsSqlTempCount.append(") as b");
				appendSQL=true;
			}	
			 count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("  initiatedVerificationDealDetail() bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
//			if((dealNo.trim()==null && appDate.trim()==null && custName.trim()==null && productId.trim()==null && schemeId.trim()==null) || (dealNo.trim().equalsIgnoreCase("") && appDate.trim().equalsIgnoreCase("") && custName.trim().equalsIgnoreCase("") && productId.trim().equalsIgnoreCase("") && schemeId.trim().equalsIgnoreCase("")) || fieldVo.getCurrentPageLink()>1)
//			{
			
			logger.info("     current PAge Link no .................... "+fieldVo.getCurrentPageLink());
			if(fieldVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
				
			bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search initiatedVerificationDealDetail query for SQL SERVER : " + bufInsSql.toString());

			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));	
//			}
			logger.info("query : "+bufInsSql);
		     header = ConnectionDAO.sqlSelect(bufInsSql.toString());

						
			for(int i=0;i<header.size();i++){
				logger.info("header: "+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					
					fieldVo = new FieldVerificationVo();
					
					fieldVo.setLbxDealNo((CommonFunction.checkNull(header1.get(0))).trim());
					fieldVo.setDealNo("<a href=\"#\" onclick=\"modifyFieldVerif('"+((CommonFunction.checkNull(header1.get(0))).trim())+"');\" >" +
							((CommonFunction.checkNull(header1.get(1))).trim())+"</a>");
					fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setProduct((CommonFunction.checkNull(header1.get(3))).trim());
					fieldVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
					fieldVo.setStatus((CommonFunction.checkNull(header1.get(5))).trim());
					fieldVo.setDealDate((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setRmName((CommonFunction.checkNull(header1.get(6))).trim());
					fieldVo.setReportingToUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(8))).trim());
					fieldVo.setTotalRecordSize(count);
					list.add(fieldVo);
				}
			}
			
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		logger.info("initiatedVerificationDealDetail : "+list.size());
		
		return list;
	}
	public ArrayList getTradeHeader(String dealId) 
	{
		ArrayList list=new ArrayList();
		try
		{
			StringBuilder query=new StringBuilder();
			query.append(" select d.deal_id, deal_no,deal.CUSTOMER_NAME,");
			query.append(dbo);
			query.append("DATE_FORMAT(deal_date,'"+dateFormat+"'),p.PRODUCT_DESC,s.SCHEME_DESC,l.DEAL_PRODUCT_CATEGORY from cr_deal_dtl d "+
							    " left join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID"+
								" left join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID"+
								" left join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID"+
								" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID"+
								" where d.DEAL_ID="+dealId.trim()+" ");
			logger.info(" getTradeHeader()........................ query : "+query.toString());
			HeaderInfoVo vo= null;
			ArrayList header = ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<header.size();i++)
			{
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
	
	
public ArrayList getCustomerDetail(String dealId, String makerId) 
{
		ArrayList list=new ArrayList();
		logger.info("getCustomerDetail()----------------> ");
		String user=StringEscapeUtils.escapeSql(CommonFunction.checkNull(makerId)).trim();
		String id=StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim();
		String val="";
		String query="";
		ArrayList inList=null;
		try
		{
			query="select VERIFICATION_ID from cr_deal_verification_dtl where VERIFICATION_TYPE='F' and REC_STATUS ='F'  and VERIFICATION_ACTION='I' and  DEAL_ID='"+id+"' and APPRAISER_TYPE='I' and INTERNAL_APPRAISER='"+user+"' union select distinct VERIFICATION_ID from cr_deal_verification_dtl where VERIFICATION_TYPE='F' and REC_STATUS ='F' and VERIFICATION_ACTION='I' and APPRAISER_TYPE='E' and '"+user+"' in(select distinct com_agency_user_mapping.USER_ID from cr_deal_verification_dtl left outer join com_agency_m on cr_deal_verification_dtl.external_appraiser=com_agency_m.AGENCY_CODE  left outer join com_agency_user_mapping on com_agency_m.AGENCY_CODE=com_agency_user_mapping.AGENCY_CODE where VERIFICATION_TYPE='F' and cr_deal_verification_dtl.REC_STATUS ='F' and VERIFICATION_ACTION='I' and APPRAISER_TYPE='E' and DEAL_ID='"+id+"') and DEAL_ID='"+id+"'";
			logger.info("VarificationID Query : "+query);
			inList = ConnectionDAO.sqlSelect(query);
			int sz=0;
			sz=inList.size();
			for(int i=0;i<sz;i++)
				val=val+","+((ArrayList)inList.get(i)).get(0);
			logger.info("VarificationID list : "+val);
			val=val.substring(1);
			logger.info("VarificationID list : "+val);
			val=val+",-1";
			logger.info("VarificationID list : "+val);
	
			StringBuffer bufInsSql=new StringBuffer();			
			bufInsSql.append(" select distinct m.DEAL_CUSTOMER_ID,m.CUSTOMER_NAME,m.Applicant_type,m.verification_sub_type,m.status,m.verificationId,m.VERIFICATION_TYPE,m.REC_STATUS,m.VERIFICATION_ACTION from (select distinct cr_deal_customer_role.DEAL_ID,dv.VERIFICATION_TYPE,dv.REC_STATUS,dv.VERIFICATION_ACTION, cr_deal_customer_role.DEAL_CUSTOMER_ID,cr_deal_customer_m.CUSTOMER_NAME,case when  cr_deal_customer_role.DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' then 'Applicant'  " +
					"  When cr_deal_customer_role.DEAL_CUSTOMER_ROLE_TYPE='COAPPL'  then 'Co-Applicant'    When cr_deal_customer_role.DEAL_CUSTOMER_ROLE_TYPE='GUARANTOR'  then 'Guarantor' end  as Applicant_type,CASE WHEN cr_deal_customer_role.DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' THEN 'Business' ELSE 'Address' END AS verification_sub_type,CASE WHEN cr_deal_customer_role.DEAL_CUSTOMER_ID  in(select ENTITY_ID from cr_field_verification_dtl where deal_Id='"+id+"') THEN CASE WHEN cr_deal_customer_role.DEAL_CUSTOMER_ID   " +
					" in(select distinct DEAL_CUSTOMER_ID  from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_ID='"+id+"') THEN  CASE WHEN 'B' in(select VERIFICATION_SUB_TYPE from cr_field_verification_dtl where DEAL_ID='"+id+"' and ENTITY_ID=(select distinct DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL'  and DEAL_ID='"+id+"')) THEN 'In Process' ELSE 'Pending' END) ELSE 'In Process' END ELSE  'Pending' END as status,  CASE WHEN cr_deal_customer_role.DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' THEN " +
					" U.VERIFICATION_ID ELSE A.VERIFICATION_ID END as  verificationId from cr_deal_verification_dtl dv left outer join cr_deal_customer_role on(dv.DEAL_ID=cr_deal_customer_role.DEAL_ID)left outer join  cr_deal_customer_m  on(cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID) left outer join cr_deal_address_m  on(cr_deal_address_m.BPID=cr_deal_customer_role.DEAL_CUSTOMER_ID)  left outer join   (select VERIFICATION_ID,DEAL_ID  from cr_deal_verification_dtl " +
					" where VERIFICATION_TYPE='F' and REC_STATUS ='F' and VERIFICATION_ACTION='I' and VERIFICATION_SUBTYPE='U' and DEAL_ID='"+id+"') U on(U.DEAL_ID=dv.DEAL_ID) left outer join (select VERIFICATION_ID,DEAL_ID from cr_deal_verification_dtl where VERIFICATION_TYPE='F' and  REC_STATUS ='F'  and VERIFICATION_ACTION='I'and VERIFICATION_SUBTYPE='A' and DEAL_ID='"+id+"') A on(A.DEAL_ID=dv.DEAL_ID)  where cr_deal_customer_role.DEAL_ID='"+id+"' and dv.VERIFICATION_TYPE='F' and dv.REC_STATUS='F'" +
					" and dv.VERIFICATION_ACTION='I'  and dv.VERIFICATION_ID in("+val+") union select distinct cr_deal_customer_role.DEAL_ID,dv.VERIFICATION_TYPE,dv.REC_STATUS,dv.VERIFICATION_ACTION,cr_deal_customer_role.DEAL_CUSTOMER_ID,cr_deal_customer_m.CUSTOMER_NAME,'Applicant' as Applicant_type,'Address' as verification_sub_type, CASE WHEN cr_deal_customer_role.DEAL_CUSTOMER_ID  in(select ENTITY_ID from cr_field_verification_dtl where deal_Id='"+id+"') THEN CASE WHEN cr_deal_customer_role.DEAL_CUSTOMER_ID " +
					" in(select distinct DEAL_CUSTOMER_ID  from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_ID='"+id+"') THEN CASE WHEN 'A' in(select VERIFICATION_SUB_TYPE from cr_field_verification_dtl where DEAL_ID='"+id+"' and ENTITY_ID=(select distinct DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_ID='"+id+"')) THEN 'In Process' ELSE 'Pending' END ELSE  'In Process' ELSE 'Pending' as status,A.VERIFICATION_ID as verificationId from cr_deal_customer_role left outer  join cr_deal_customer_m " +
					" on(cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID)  left outer join cr_deal_address_m on (cr_deal_address_m.BPID=cr_deal_customer_role.DEAL_CUSTOMER_ID)  left outer join cr_deal_verification_dtl dv on(dv.DEAL_ID=cr_deal_customer_role.DEAL_ID) left outer join (select VERIFICATION_ID,DEAL_ID from cr_deal_verification_dtl where VERIFICATION_TYPE='F' and  REC_STATUS ='F'  and VERIFICATION_ACTION='I'and VERIFICATION_SUBTYPE='A' and DEAL_ID='"+id+"') A on(A.DEAL_ID=dv.DEAL_ID) " +
					" where cr_deal_customer_role.DEAL_ID='"+id+"' and dv.VERIFICATION_TYPE='F' and dv.REC_STATUS='F' and dv.VERIFICATION_ACTION='I'  and dv.VERIFICATION_ID in("+val+") and cr_deal_customer_role.DEAL_CUSTOMER_ID=(select distinct DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_ID='"+id+"' ))as m where m.VERIFICATION_TYPE='F' and m.REC_STATUS='F' and m.VERIFICATION_ACTION='I' and m.verificationId in("+val+") order by m.DEAL_CUSTOMER_ID,m.CUSTOMER_NAME,m.Applicant_type" );
	
			logger.info("Customer detail query:::::::::::::::::::::::::::::::"+bufInsSql.toString());						 
			NewCaptureVO vo= null;
			ArrayList detail = ConnectionDAO.sqlSelect(bufInsSql.toString());
			for(int i=0;i<detail.size();i++)
			{
				ArrayList detail1=(ArrayList)detail.get(i);
				if(detail1!=null && detail1.size()>0)
				{
					vo = new NewCaptureVO();
					vo.setCustomer_id((CommonFunction.checkNull(detail1.get(0))).trim());
					vo.setCustomer_name((CommonFunction.checkNull(detail1.get(1))).trim());
					vo.setApplicant_type((CommonFunction.checkNull(detail1.get(2))).trim());
					vo.setVerificationSubType((CommonFunction.checkNull(detail1.get(3))).trim());
					vo.setStatus((CommonFunction.checkNull(detail1.get(4))).trim());
					vo.setVerificationId((CommonFunction.checkNull(detail1.get(5))).trim());
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
public ArrayList getComCustomerDetail(String dealId,String recStats) 
{
	ArrayList list=new ArrayList();
	String id=StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim();
	String stats=StringEscapeUtils.escapeSql(CommonFunction.checkNull(recStats)).trim();
	try
	{
		StringBuffer bufInsSql=new StringBuffer();
		bufInsSql.append(" select distinct ENTITY_ID,cr_deal_customer_m.CUSTOMER_NAME,case when  DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' then 'Applicant'   When DEAL_CUSTOMER_ROLE_TYPE='COAPPL'  then 'Co-Applicant'    When DEAL_CUSTOMER_ROLE_TYPE='GUARANTOR'  then 'Guarantor' end  as Applicant_type,case when VERIFICATION_SUB_TYPE='A' then 'Address' when VERIFICATION_SUB_TYPE='B' then 'Business' end as VERIFICATION_SUB_TYPE from  cr_field_verification_dtl left outer join cr_deal_customer_role on((cr_field_verification_dtl.DEAL_ID=cr_deal_customer_role.DEAL_ID)and(cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_field_verification_dtl.ENTITY_ID))left outer join  cr_deal_customer_m  on(cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID)where  cr_field_verification_dtl.DEAL_ID='"+id+"' and cr_field_verification_dtl.REC_STATUS='"+stats+"' order by cr_deal_customer_role.DEAL_CUSTOMER_ID,cr_deal_customer_m.CUSTOMER_NAME");
//		bufInsSql.append(" select distinct cr_deal_customer_role.DEAL_CUSTOMER_ID,cr_deal_customer_m.CUSTOMER_NAME,case when  DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' then 'Applicant'   When DEAL_CUSTOMER_ROLE_TYPE='COAPPL'  then 'Co-Applicant'    When DEAL_CUSTOMER_ROLE_TYPE='GUARANTOR'  then 'Guarantor' end  as Applicant_type,case when VERIFICATION_SUB_TYPE='A' then 'Address' when VERIFICATION_SUB_TYPE='B' then 'Business' end as VERIFICATION_SUB_TYPE from cr_field_verification_dtl left outer join cr_deal_customer_role on(cr_field_verification_dtl.DEAL_ID=cr_deal_customer_role.DEAL_ID)left outer join  cr_deal_customer_m  on(cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID) where  cr_field_verification_dtl.DEAL_ID='"+id+"' and cr_field_verification_dtl.REC_STATUS='"+stats+"' order by cr_deal_customer_role.DEAL_CUSTOMER_ID,cr_deal_customer_m.CUSTOMER_NAME " );
		logger.info("Customer detail  query in Complition :::::::::::::::::::::::::::::::"+bufInsSql.toString());						 
		NewCaptureVO vo= null;
		ArrayList detail = ConnectionDAO.sqlSelect(bufInsSql.toString());
		for(int i=0;i<detail.size();i++)
		{
			ArrayList detail1=(ArrayList)detail.get(i);
			if(detail1!=null && detail1.size()>0)
			{
				vo = new NewCaptureVO();
				vo.setCustomer_id((CommonFunction.checkNull(detail1.get(0))).trim());
				vo.setCustomer_name((CommonFunction.checkNull(detail1.get(1))).trim());
				vo.setApplicant_type((CommonFunction.checkNull(detail1.get(2))).trim());
				vo.setVerificationSubType((CommonFunction.checkNull(detail1.get(3))).trim());
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


	public boolean insertFieldVerInitiation(FieldVerificationVo fieldVo){
		
		
		boolean status=false;
		ArrayList qryList = new ArrayList();
				
		StringBuffer bufInsSql = null;
		PrepStmtObject insertPrepStmtObject = null;
		String[] action=fieldVo.getVerificationAction();
		String[] entityId=fieldVo.getEntityId();
		String[] verType=fieldVo.getVerType();
		String[] verSubType=fieldVo.getVerSubType();
		
		String[] entType=fieldVo.getEntType();
		String[] entSubType=fieldVo.getEntSubType();
		
		String[] externalAppUserId=fieldVo.getExternalAppUserId();
		String[] internalAppUserId=fieldVo.getInternalAppUserId();
		
		String[] appraiser=fieldVo.getAppraiser();
		String []entityDescription=fieldVo.getEntityDescription();
		String lbxAddressType[]=fieldVo.getLbxAddressType();
		String deleteQuery="";
		try {

			logger.info("entityId.length: "+entityId.length);
			for (int i = 0; i < entityId.length; i++) {
				
						
				if((CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("W")))
				{
					if(entSubType[i].equalsIgnoreCase("BUSINESS")||entSubType[i].equalsIgnoreCase("MARKET"))
					{
						deleteQuery="DELETE FROM cr_deal_verification_dtl WHERE REC_STATUS='F' AND ENTITY_SUB_TYPE='"+entSubType[i]+"' AND STAGE='DC' AND DEAL_ID="+fieldVo.getDealId();
					}
					else
					{
						deleteQuery="DELETE FROM cr_deal_verification_dtl WHERE REC_STATUS='F' AND ENTITY_ID='"+entityId[i]+"' AND STAGE='DC' AND DEAL_ID="+fieldVo.getDealId();
					}
					logger.info("deleteQuery of Deletion is ="+deleteQuery);
					ArrayList deleteArl= new ArrayList();
					deleteArl.add(deleteQuery);
					status=ConnectionDAO.sqlInsUpdDelete(deleteArl);
					deleteArl=null;
					logger.info("Status of Deletion is ="+status);
				}
				if(!(CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("P"))){
					
				 insertPrepStmtObject = new PrepStmtObject();
				 bufInsSql = new StringBuffer();
				 logger.info("In insert Field Verification Initiation");
				
				bufInsSql.append("insert into cr_deal_verification_dtl(DEAL_ID,VERIFICATION_TYPE,VERIFICATION_SUBTYPE,APPRAISER_TYPE,INTERNAL_APPRAISER,");
				bufInsSql.append(" EXTERNAL_APPRAISER,VERIFICATION_ACTION,ENTITY_ID,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,ADDRESS_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,STAGE,DOC_CHILD_IDS)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//DEAL_ID
				bufInsSql.append(" ?,");//V+ERIFICATION_TYPE
				bufInsSql.append(" ?,");//VERIFICATION_SUBTYPE
				bufInsSql.append(" ?,");//APPRAISER_TYPE
				bufInsSql.append(" ?,");//INTERNAL_APPRAISER
				bufInsSql.append(" ?,");//EXTERNAL_APPRAISER
				
				bufInsSql.append(" ?,");//VERIFICATION_ACTION
				bufInsSql.append(" ?,");//ENTITY_ID
				
				bufInsSql.append(" ?,");//ENTITY_TYPE
				bufInsSql.append(" ?,");//ENTITY_SUB_TYPE
				bufInsSql.append(" ?,");//ENTITY_DESC
				bufInsSql.append(" ?,");//ADDRESS_TYPE
				bufInsSql.append(" ?,");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");//MAKER_DATE
				// saurabh space starts
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				// saurabh space ends
				bufInsSql.append(" ?,");//AUTHOR_ID
				//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");//AUTHOR_DATE
				// saurabh space starts
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" 'DC',");//AUTHOR_ID
				bufInsSql.append(" ? )");//DOC_CHILD_IDS
				// saurabh space ends
			
				if (CommonFunction.checkNull(fieldVo.getDealId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fieldVo.getDealId()).trim());

				
				if (CommonFunction.checkNull(verType[i]).trim().equalsIgnoreCase("") )
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((verType[i]).trim());
				
				
				if (CommonFunction.checkNull(verSubType[i]).trim().equalsIgnoreCase("") )
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((verSubType[i]).trim());
				
				logger.info("appraiser size : "+appraiser[i]);	
				logger.info("action size : "+action[i]);	
				if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
				{	
					if (CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("") )
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString((appraiser[i]).trim());
				}else
				{
					insertPrepStmtObject.addNull();
				}	
					logger.info("internalAppUserId size : "+internalAppUserId.length);	
					if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
					{
						if(internalAppUserId.length>0 && CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("INTERNAL"))
						{
							if (CommonFunction.checkNull(internalAppUserId[i]).trim().equalsIgnoreCase("") )
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((internalAppUserId[i]).trim());
						}
						else
						{
							insertPrepStmtObject.addNull();
						}
					}
					else
					{
						insertPrepStmtObject.addNull();
					}
					logger.info("externalAppUserId size  "+externalAppUserId.length);
					if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
					{
						if(externalAppUserId.length>0 && CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("EXTERNAL"))
						{
							if (CommonFunction.checkNull(externalAppUserId[i]).trim().equalsIgnoreCase("") )
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((externalAppUserId[i]).trim());
							}
						else
						{
							insertPrepStmtObject.addNull();
						}
					}
					else
					{
						insertPrepStmtObject.addNull();
					}
					
				
						
				if (CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(action[i].trim());
				
				if (CommonFunction.checkNull(entityId[i]).trim().equalsIgnoreCase("") )
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((entityId[i]).trim());
				
												
				if (CommonFunction.checkNull(entType[i]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				{
					if(entType[i].trim().equalsIgnoreCase("APPLICANT"))
					{
						insertPrepStmtObject.addString("PRAPPL");
					}
					else if(entType[i].trim().equalsIgnoreCase("COAPPLICANT"))
					{
						insertPrepStmtObject.addString("COAPPL");
					}
					else
					{
						insertPrepStmtObject.addString(entType[i].trim());
					}
				}
					
				
				if (CommonFunction.checkNull(entSubType[i]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(entSubType[i].trim());
				
				if(CommonFunction.checkNull(entityDescription[i]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(entityDescription[i].trim());
				
				if(CommonFunction.checkNull(lbxAddressType[i]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(lbxAddressType[i].trim());
				
				
				insertPrepStmtObject.addString("F");

				if (CommonFunction.checkNull(fieldVo.getMakerId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerId().trim());

				if (CommonFunction.checkNull(fieldVo.getMakerDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());

				if (CommonFunction.checkNull(fieldVo.getMakerId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerId().trim());

				if (CommonFunction.checkNull(fieldVo.getMakerDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());
				
				if (CommonFunction.checkNull(verType[i]).trim().equalsIgnoreCase("DOCUMENT VERIFICATION"))
				{
					String docQry="select DOC_CHILD_IDS  from cr_document_dtl where TXN_DOC_ID= '"+CommonFunction.checkNull(entityId[i]).trim()+"' AND IFNULL(DOC_CHILD_IDS,'')<>'' AND TXN_TYPE='DC' AND TXNID='"+CommonFunction.checkNull(fieldVo.getDealId()).trim()+"' ";
					String docId=ConnectionDAO.singleReturn(docQry);
					insertPrepStmtObject.addString(CommonFunction.checkNull(docId).trim());					
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertfieldVerification() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
			
				
			  }
				
			}
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("status....."+status);
				logger.info("IN insertfieldVerification() insert query1 ### "+ insertPrepStmtObject.printQuery());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	 public ArrayList<FieldVerificationVo> searchFieldDetail(FieldVerificationVo vo,String dealId)
	 	{
	 		
	 		ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	 	   
	 		  try{
	 			  ArrayList searchlist=new ArrayList();
	 	      logger.info("In searchInitiationDetail....................");
	 	      boolean appendSQL=false;
	 	      StringBuffer bufInsSql =	new StringBuffer();
	 	    
	 	      logger.info("dealId is..........."+dealId);
	 	     
	 	      
				bufInsSql.append(" SELECT DISTINCT CASE WHEN A.ADDRESS_TYPE='OFFICE' THEN 'Address' ELSE 'Address' END  as AddType ");
				bufInsSql.append(" from cr_deal_address_m A ");
				bufInsSql.append(" LEFT JOIN cr_deal_customer_role B on B.DEAL_CUSTOMER_ID=A.BPID ");
				bufInsSql.append(" where B.DEAL_ID='"+dealId+"' ");
	 	      
	 	    	logger.info("query : "+bufInsSql);
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				logger.info("IN searchCMGrid() search query1 ### "+ bufInsSql.toString());
				logger.info("searchCMGrid " + searchlist.size());
					
	 	      
	 	      for(int i=0;i<searchlist.size();i++){
	 	      logger.info("searchGrid search List "+searchlist.get(i).toString());
	 	      ArrayList data=(ArrayList)searchlist.get(i);

	 	      	if(data.size()>0){
	 	    	 FieldVerificationVo fieldVo = new FieldVerificationVo();
	 	    	  
	 	    	fieldVo.setAddType((CommonFunction.checkNull(data.get(0)).trim()));
	 	    	fieldVo.setAddrType("Contact Verification");
	 	    	list.add(fieldVo);
	 	       }
	   }
	 	     FieldVerificationVo fVo=new FieldVerificationVo();
	 	      fVo.setAddType("Business");
	 	      fVo.setAddrType("Contact Verification");
	 	      list.add(fVo);
	 		}catch(Exception e){
	 			e.printStackTrace();
	 				}
	 		return  list;	
	 	}
	 
	 public ArrayList<FieldVerificationVo> searchTradeDetail(FieldVerificationVo vo,String dealId)
	 	{
	 		
	 		ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	 		String loanCategory=null;
	 		ArrayList subList=new ArrayList();
	 		  try{
	 			  ArrayList searchlist=new ArrayList();
	 			  ArrayList searchLoanCat=new ArrayList();
	 	      logger.info("In searchInitiationDetail....................");
	 	      boolean appendSQL=false;
	 	      StringBuffer bufInsSql =	new StringBuffer();
	 	      StringBuffer bufTemInsSql= new StringBuffer();
	 	      logger.info("dealId is..........."+dealId);
	 	     
				bufInsSql.append(" SELECT DISTINCT CASE WHEN A.DEAL_BUYER_SUPPLIER_TYPE='B' THEN 'Buyer' ");
				bufInsSql.append(" WHEN A.DEAL_BUYER_SUPPLIER_TYPE='S' THEN 'Supplier' END AS DEALTYPE ");
				bufInsSql.append(" FROM cr_deal_buyer_supplier_dtl A WHERE A.DEAL_ID='"+dealId+"' ");
	 	    	
	 	    	logger.info("query : "+bufInsSql);
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				logger.info("IN searchCMGrid() search query1 ### "+ bufInsSql.toString());
				logger.info("searchCMGrid " + searchlist.size());
				
				bufTemInsSql.append("select DEAL_PRODUCT_CATEGORY from cr_deal_loan_dtl where DEAL_ID='"+dealId+"' ");
				logger.info("query For Loan Category : "+bufTemInsSql);
				searchLoanCat = ConnectionDAO.sqlSelect(bufTemInsSql.toString());
				logger.info("IN searchTradeDetail() search query2 ### "+ searchLoanCat);
				
	 	      for(int i=0;i<searchlist.size();i++){
	 	      logger.info("searchGrid search List "+searchlist.get(i).toString());
	 	      ArrayList data=(ArrayList)searchlist.get(i);

	 	      	if(data.size()>0){

	 	    	 FieldVerificationVo fieldVo = new FieldVerificationVo();
	 	    	fieldVo.setAddType((CommonFunction.checkNull(data.get(0)).trim()));
	 	    	fieldVo.setAddrType("Trade Check");
	 	    	list.add(fieldVo);
	 	       }
	   }
	 	     Iterator< ArrayList> itr=searchLoanCat.iterator();

	 	     while (itr.hasNext()){
			    	subList=itr.next();
			    	if(subList.size()>0){
			    		loanCategory=CommonFunction.checkNull(subList.get(0));
			    	}
			    }
	 	          
	 	      if(loanCategory.contains("SME"))
	 	      {
		 	      FieldVerificationVo fVo=new FieldVerificationVo();
		 	      fVo.setAddType("Market");
		 	      fVo.setAddrType("Trade Check");
		 	      list.add(fVo);
	 	      }
	 		}catch(Exception e){
	 			e.printStackTrace();
	 				}
	 		return  list;	
	 	}
	 
    
  
	public ArrayList<FieldVerificationVo> searchCollDetail(FieldVerificationVo vo,String dealId)
 	{
 		
 		ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
 	   
 		  try{
 			  ArrayList searchlist=new ArrayList();
 	      logger.info("In searchInitiationDetail....................");
 	      boolean appendSQL=false;
 	      StringBuffer bufInsSql =	new StringBuffer();
 	    
 	      logger.info("dealId is..........."+dealId);
 	     
 	      
			bufInsSql.append(" select DISTINCT CASE WHEN A.ASSET_COLLATERAL_CLASS='MACHINE' THEN 'MACHINE' ");
			bufInsSql.append(" WHEN A.ASSET_COLLATERAL_CLASS='PROPERTY' THEN 'PROPERTY'");
			bufInsSql.append(" WHEN A.ASSET_COLLATERAL_CLASS='VEHICLE' THEN 'VEHICLE' ");
			bufInsSql.append(" WHEN A.ASSET_COLLATERAL_CLASS='STOCK' THEN 'STOCK' ELSE 'OTHER' END AS ASSET");
			bufInsSql.append(" from cr_asset_collateral_m A ");
			bufInsSql.append(" left join cr_deal_collateral_m B on B.ASSETID=A.ASSET_ID where A.ASSET_TYPE='COLLATERAL' AND B.DEAL_ID='"+dealId+"'");
		   	     
 	    	logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("IN searchCMGrid() search query1 ### "+ bufInsSql.toString());
			logger.info("searchCMGrid " + searchlist.size());
				
 	      
 	      for(int i=0;i<searchlist.size();i++){
 	      logger.info("searchGrid search List "+searchlist.get(i).toString());
 	      ArrayList data=(ArrayList)searchlist.get(i);

 	      if(data.size()>0){
 	    	 FieldVerificationVo fieldVo = new FieldVerificationVo();
 	    	  
 	    	fieldVo.setAddType((CommonFunction.checkNull(data.get(0)).trim()));
 	    	fieldVo.setAddrType("Collateral Verification");
 	    	list.add(fieldVo);
 	       }
   }
 		}catch(Exception e){
 			e.printStackTrace();
 				}
 		return  list;	
 }
    
    public ArrayList<FieldVerificationVo> searchFieldVerInitiation(FieldVerificationVo vo,String dealId)
 	{
 		
 		ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
 	   
 		  try{
 			  ArrayList searchlist=new ArrayList();
 	      logger.info("In searchFieldVerInitiation....................");
 	      boolean appendSQL=false;
 	      StringBuffer bufInsSql =	new StringBuffer();
 	    
 	      logger.info("dealId is..........."+dealId);
 	      	      
			
 	     bufInsSql.append(" select CASE WHEN a.VERIFICATION_TYPE='F' THEN 'Field Verification' WHEN a.VERIFICATION_TYPE='T' THEN 'Trade Check' ");
 	     bufInsSql.append(" WHEN a.VERIFICATION_TYPE='C' THEN 'Collateral Verification' END AS VERIFICATION_TYPE,CASE WHEN a.VERIFICATION_SUBTYPE='A' THEN 'Address'");
 	     bufInsSql.append(" WHEN a.VERIFICATION_SUBTYPE='U' THEN 'Business' WHEN a.VERIFICATION_SUBTYPE='B' THEN 'Buyer' WHEN a.VERIFICATION_SUBTYPE='S' THEN 'Supplier' WHEN a.VERIFICATION_SUBTYPE='M' THEN 'Market'");
 	     bufInsSql.append(" WHEN a.VERIFICATION_SUBTYPE='C' THEN 'Machine' WHEN a.VERIFICATION_SUBTYPE='V' THEN 'Vehicle' WHEN a.VERIFICATION_SUBTYPE='P' THEN 'Property' WHEN a.VERIFICATION_SUBTYPE='T' THEN 'Stock'");
 	     bufInsSql.append(" WHEN a.VERIFICATION_SUBTYPE='O' THEN 'Other' END AS VERIFICATON_SUBTYPE,a.VERIFICATION_ACTION,a.APPRAISER_TYPE,");
 	     bufInsSql.append(" b.USER_NAME,a.INTERNAL_APPRAISER,c.AGENCY_NAME,a.EXTERNAL_APPRAISER,a.VERIFICATION_ID");
		 bufInsSql.append(" FROM cr_deal_verification_dtl A ");
 	     bufInsSql.append(" left join SEC_USER_M b ON a.INTERNAL_APPRAISER=b.USER_ID AND b.REC_STATUS='A'");
 	     bufInsSql.append(" left join com_agency_m c ON a.EXTERNAL_APPRAISER=c.AGENCY_CODE AND c.REC_STATUS='A'");
 	     bufInsSql.append("  where a.DEAL_ID='"+dealId+"'");
		   	     
 	    	logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			logger.info("IN searchCMGrid() search query1 ### "+ bufInsSql.toString());
			logger.info("searchCMGrid " + searchlist.size());
				
 	      
 	      for(int i=0;i<searchlist.size();i++){
 	      logger.info("searchGrid search List "+searchlist.get(i).toString());
 	      ArrayList data=(ArrayList)searchlist.get(i);

 	      if(data.size()>0){
 	    	 FieldVerificationVo fieldVo = new FieldVerificationVo();
 	    	 
 	    	fieldVo.setAddrType((CommonFunction.checkNull(data.get(0)).trim())); 
 	    	fieldVo.setAddType((CommonFunction.checkNull(data.get(1)).trim())); 
 	    	fieldVo.setActionValue((CommonFunction.checkNull(data.get(2)).trim())); 
 	    	fieldVo.setAppraiserType((CommonFunction.checkNull(data.get(3)).trim())); 
 	    	fieldVo.setLbxUseId((CommonFunction.checkNull(data.get(5)).trim())); 
 	    	fieldVo.setInternalAppraiser((CommonFunction.checkNull(data.get(4)).trim())); 
 	    	fieldVo.setExternalAppraiser((CommonFunction.checkNull(data.get(6)).trim()));
 	    	fieldVo.setLbxetApprHID((CommonFunction.checkNull(data.get(7)).trim()));
 	    	fieldVo.setVarId((CommonFunction.checkNull(data.get(8)).trim()));
 	    	list.add(fieldVo);
 	       }
   }
 		}catch(Exception e){
 			e.printStackTrace();
 				}
 		return  list;	
 }
    


//FOR CAPTURE
    public ArrayList<FieldVerificationVo> searchDealForCapture(FieldVerificationVo vo) {
		
		ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		try
		{
			ArrayList header=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;

			FieldVerificationVo fieldVo= (FieldVerificationVo) vo;

			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(fieldVo.getUserId())).trim());
			 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"'";
			 			String userName=ConnectionDAO.singleReturn(userNameQ);
			 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		
			boolean appendSQL=false;			  			

			String dealNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
			String appDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationDate())).trim());
			String date = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate())).trim());
			String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
			String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
			String schemeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());			
				
			bufInsSql.append("select distinct cr_deal_verification_dtl.DEAL_ID,cr_deal_dtl.DEAL_NO,");
			bufInsSql.append(dbo);
			bufInsSql.append(" DATE_FORMAT(cr_deal_dtl.DEAL_DATE,'%d-%m-%Y'),cr_deal_customer_m.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,ENTITY_ID,VERIFICATION_ID ");
			bufInsSql.append(" from cr_deal_verification_dtl left outer join cr_deal_dtl on(cr_deal_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID  AND cr_deal_dtl.DEAL_BRANCH='"+fieldVo.getBranchId()+"')left outer join cr_deal_loan_dtl on(cr_deal_loan_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID)left outer join cr_product_m	on cr_deal_loan_dtl.DEAL_PRODUCT=cr_product_m.PRODUCT_ID left outer join cr_scheme_m	on cr_deal_loan_dtl.DEAL_SCHEME=cr_scheme_m.SCHEME_ID	left outer join cr_deal_customer_m  on cr_deal_customer_m.CUSTOMER_ID=cr_deal_dtl.DEAL_CUSTOMER_ID where 'a'='a' ");
			bufInsSqlTempCount.append("select count(1) from cr_deal_verification_dtl left outer join cr_deal_dtl on(cr_deal_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID  AND cr_deal_dtl.DEAL_BRANCH='"+fieldVo.getBranchId()+"')left outer join cr_deal_loan_dtl on(cr_deal_loan_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID)left outer join cr_product_m	on cr_deal_loan_dtl.DEAL_PRODUCT=cr_product_m.PRODUCT_ID left outer join cr_scheme_m	on cr_deal_loan_dtl.DEAL_SCHEME=cr_scheme_m.SCHEME_ID	left outer join cr_deal_customer_m  on cr_deal_customer_m.CUSTOMER_ID=cr_deal_dtl.DEAL_CUSTOMER_ID where 'a'='a' ");
			if(!(dealNo.trim().length()==0))
			{
				bufInsSql.append(" AND cr_deal_verification_dtl.STAGE='DC'  and cr_deal_verification_dtl.DEAL_ID="+dealNo);
				bufInsSqlTempCount.append(" AND cr_deal_verification_dtl.STAGE='DC'  and cr_deal_verification_dtl.DEAL_ID="+dealNo);
			}
			if(!(date.trim().length()==0))
			{
				bufInsSql.append(" and ");
				bufInsSql.append(dbo);
				bufInsSql.append("date(cr_deal_dtl.DEAL_DATE)=");
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE('"+date+"','"+dateFormat+"')");
				bufInsSqlTempCount.append(" and ");
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("date(cr_deal_dtl.DEAL_DATE)=");
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("STR_TO_DATE('"+date+"','"+dateFormat+"')");
			}
			if(!(custName.trim().length()==0))
			{
				bufInsSql.append(" and cr_deal_customer_m.CUSTOMER_NAME like'%"+custName+"%'");
				bufInsSqlTempCount.append(" and cr_deal_customer_m.CUSTOMER_NAME like'%"+custName+"%'");
			}
			if(!(productId.trim().length()==0))
			{
				bufInsSql.append(" and cr_deal_loan_dtl.DEAL_PRODUCT='"+productId+"'");
				bufInsSqlTempCount.append(" and cr_deal_loan_dtl.DEAL_PRODUCT='"+productId+"'");
			}
			if(!(schemeId.trim().length()==0))
			{
				bufInsSql.append(" and cr_deal_loan_dtl.DEAL_SCHEME='"+schemeId+"'");
				bufInsSqlTempCount.append(" and cr_deal_loan_dtl.DEAL_SCHEME='"+schemeId+"'");
			}
			bufInsSql.append(" and cr_deal_verification_dtl.REC_STATUS IN ('F') AND cr_deal_verification_dtl.STAGE='DC'  and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.VERIFICATION_ID in(select VERIFICATION_ID from cr_deal_verification_dtl where REC_STATUS IN ('F')  and VERIFICATION_ACTION='I' and APPRAISER_TYPE='INTERNAL' and INTERNAL_APPRAISER='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"' union select distinct VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' and '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"' in(select distinct com_agency_user_mapping.USER_ID from cr_deal_verification_dtl left outer join com_agency_m on cr_deal_verification_dtl.external_appraiser=com_agency_m.AGENCY_CODE  left outer join com_agency_user_mapping on com_agency_m.AGENCY_CODE=com_agency_user_mapping.AGENCY_CODE where  cr_deal_verification_dtl.REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' ) union select -1) order by cr_deal_verification_dtl.DEAL_ID,cr_deal_dtl.DEAL_NO");
			bufInsSqlTempCount.append(" and cr_deal_verification_dtl.REC_STATUS IN ('F') AND cr_deal_verification_dtl.STAGE='DC'  and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.VERIFICATION_ID in(select VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F')  and VERIFICATION_ACTION='I' and APPRAISER_TYPE='INTERNAL' and INTERNAL_APPRAISER='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"' union select distinct VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' and '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"' in(select distinct com_agency_user_mapping.USER_ID from cr_deal_verification_dtl left outer join com_agency_m on cr_deal_verification_dtl.external_appraiser=com_agency_m.AGENCY_CODE  left outer join com_agency_user_mapping on com_agency_m.AGENCY_CODE=com_agency_user_mapping.AGENCY_CODE where  cr_deal_verification_dtl.REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' ) union select -1)");
			if(fieldVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search searchDealForCapture query for SQL SERVER : " + bufInsSql.toString());
	
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
		    logger.info("Search query ::::::::::::: "+bufInsSql);
		    logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());
		    count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		    int size=header.size();			
			for(int i=0;i<size;i++)
			{    				
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					fieldVo = new FieldVerificationVo();
					fieldVo.setDealNo("<a href=fieldVarificationAction.do?method=addFieldCapture&dealId="+(CommonFunction.checkNull(header1.get(0))).trim()+"&entityId="+(CommonFunction.checkNull(header1.get(9))).trim()+"&verificationId="+(CommonFunction.checkNull(header1.get(10))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
					fieldVo.setInitiationDate((CommonFunction.checkNull(header1.get(2))).trim());
					fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(3))).trim());
					fieldVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
					fieldVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
					fieldVo.setUserName(userName);
					
					if(CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase("PRAPPL"))
					 {
						fieldVo.setEntityType("APPLICANT");
					 }
					 else if(CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase("COAPPL"))
					 {
						 fieldVo.setEntityType("COAPPLICANT");
					 }
					 else
					 {
						 fieldVo.setEntityType(CommonFunction.checkNull(header1.get(6)));
					 }
					fieldVo.setEntitySubType((CommonFunction.checkNull(header1.get(7))).trim());
					fieldVo.setEntityDesc((CommonFunction.checkNull(header1.get(8))).trim());
					fieldVo.setEntId((CommonFunction.checkNull(header1.get(9))).trim());
					fieldVo.setTotalRecordSize(count);
					list.add(fieldVo);
				}
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		finally
		{
			bufInsSql=null;
			bufInsSqlTempCount=null;
		}
				return list;
	}

public ArrayList<FieldVerificationVo> searchDealForComCapture(FieldVerificationVo vo,HttpServletRequest request) 
{
	ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	try
	{
		ArrayList header=null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		FieldVerificationVo fieldVo= (FieldVerificationVo) vo;
		boolean appendSQL=false;		
		StringBuffer bufInsSql=new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();		
		String dealNo = vo.getLbxDealNo().trim();
		String date = vo.getInitiationDate().trim();
		String custName = vo.getCustomerName().trim();
		String productId = vo.getLbxProductID().trim();
		String schemeId = vo.getLbxscheme().trim();	
		bufInsSql.append(" select distinct cr_deal_verification_dtl.DEAL_ID,cr_deal_dtl.DEAL_NO,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(cr_deal_dtl.DEAL_DATE,'%d-%m-%Y'),cr_deal_customer_m.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,CASE WHEN cr_deal_dtl.REC_STATUS='F' THEN 'Forwarded' END as STATUS  ");
		bufInsSql.append(" from cr_deal_verification_dtl inner join cr_deal_dtl on(cr_deal_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID AND cr_deal_dtl.REC_STATUS='F' AND cr_deal_dtl.DEAL_BRANCH='"+fieldVo.getBranchId()+"')inner join cr_deal_loan_dtl on(cr_deal_loan_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID)inner join cr_product_m on(cr_deal_loan_dtl.DEAL_PRODUCT=cr_product_m.PRODUCT_ID)inner join cr_scheme_m on(cr_deal_loan_dtl.DEAL_SCHEME=cr_scheme_m.SCHEME_ID)inner join cr_deal_customer_m  on(cr_deal_customer_m.CUSTOMER_ID=cr_deal_dtl.DEAL_CUSTOMER_ID) inner join cr_field_verification_dtl on(cr_field_verification_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID) where 1=1 ");
		bufInsSqlTempCount.append("SELECT  COUNT(1) FROM(select distinct cr_deal_verification_dtl.DEAL_ID,cr_deal_dtl.DEAL_NO,");
		bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("DATE_FORMAT(cr_deal_dtl.DEAL_DATE,'%d-%m-%Y') AS DEAL_DATE ,cr_deal_customer_m.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC  from cr_deal_verification_dtl inner join cr_deal_dtl on(cr_deal_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID AND cr_deal_dtl.REC_STATUS='F' AND cr_deal_dtl.DEAL_BRANCH='"+fieldVo.getBranchId()+"')inner join cr_deal_loan_dtl on(cr_deal_loan_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID)inner join cr_product_m on(cr_deal_loan_dtl.DEAL_PRODUCT=cr_product_m.PRODUCT_ID)inner join cr_scheme_m on(cr_deal_loan_dtl.DEAL_SCHEME=cr_scheme_m.SCHEME_ID)inner join cr_deal_customer_m  on(cr_deal_customer_m.CUSTOMER_ID=cr_deal_dtl.DEAL_CUSTOMER_ID) inner join cr_field_verification_dtl on(cr_field_verification_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID) where 1=1");
		if(!(dealNo.trim().length()==0))
		{
			bufInsSql.append(" AND cr_deal_verification_dtl.STAGE='DC' and cr_deal_verification_dtl.DEAL_ID="+dealNo);
			bufInsSqlTempCount.append(" AND cr_deal_verification_dtl.STAGE='DC' and cr_deal_verification_dtl.DEAL_ID="+dealNo);			
		}
		if(!(date.trim().length()==0))
		{
			bufInsSql.append(" and ");
			bufInsSql.append(dbo);
			bufInsSql.append("date(cr_deal_dtl.DEAL_DATE)=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE('"+date+"','"+dateFormat+"')");
			bufInsSqlTempCount.append(" and ");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("date(cr_deal_dtl.DEAL_DATE)=");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("STR_TO_DATE('"+date+"','"+dateFormat+"')");			
		}
		if(!(custName.trim().length()==0))
		{
			bufInsSql.append(" and cr_deal_customer_m.CUSTOMER_NAME like'%"+custName+"%'");
			bufInsSqlTempCount.append(" and cr_deal_customer_m.CUSTOMER_NAME like'%"+custName+"%'");			
		}
		if(!(productId.trim().length()==0))
		{
			bufInsSql.append(" and cr_deal_loan_dtl.DEAL_PRODUCT ='"+productId+"'");
			bufInsSqlTempCount.append(" and cr_deal_loan_dtl.DEAL_PRODUCT ='"+productId+"'");			
		}
		if(!(schemeId.trim().length()==0))
		{
			bufInsSql.append(" and cr_deal_loan_dtl.DEAL_SCHEME ='"+schemeId+"'");
			bufInsSqlTempCount.append(" and cr_deal_loan_dtl.DEAL_SCHEME ='"+schemeId+"'");			
		}
		bufInsSql.append(" and  cr_deal_verification_dtl.REC_STATUS='R' AND cr_deal_verification_dtl.STAGE='DC'  and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.DEAL_ID NOT in(select distinct DEAL_ID from cr_deal_verification_dtl where REC_STATUS='F'  and VERIFICATION_ACTION ='I' ) and cr_deal_verification_dtl.DEAL_ID not in (select deal_id from cr_field_verification_dtl where MAKER_ID='"+vo.getUserId()+"') ");
		bufInsSqlTempCount.append(" and  cr_deal_verification_dtl.REC_STATUS='R' AND cr_deal_verification_dtl.STAGE='DC'  and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.DEAL_ID NOT  in(select distinct DEAL_ID from cr_deal_verification_dtl where REC_STATUS='F'  and VERIFICATION_ACTION ='I') and cr_deal_verification_dtl.DEAL_ID not in (select deal_id from cr_field_verification_dtl where MAKER_ID='"+vo.getUserId()+"') ");
		bufInsSqlTempCount.append(" )as f ");	
		
		logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
		logger.info("current PAge Link no .................... "+fieldVo.getCurrentPageLink());
		if(fieldVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
			
		bufInsSql.append(" ORDER BY cr_deal_verification_dtl.DEAL_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
		logger.info("Search searchDealForComCapture query for SQL SERVER : " + bufInsSql.toString());

		//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
		logger.info("query : "+bufInsSql);
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
	    header = ConnectionDAO.sqlSelect(bufInsSql.toString());
					
		for(int i=0;i<header.size();i++){
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				fieldVo = new FieldVerificationVo();
				fieldVo.setDealNo("<a href=fieldVarificationComAction.do?method=addFieldComCapture&dealId="+(CommonFunction.checkNull(header1.get(0))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
				fieldVo.setInitiationDate((CommonFunction.checkNull(header1.get(2))).trim());
				fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(3))).trim());
				fieldVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
				fieldVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
				fieldVo.setStatus((CommonFunction.checkNull(header1.get(6))).trim());
				fieldVo.setTotalRecordSize(count);
				list.add(fieldVo);
			}
		}
	}
	catch(Exception e)
	{e.printStackTrace();}	
	logger.info("Detail List when searchList is : "+list.size());	
	return list;	
}
public int insertFieldVerCapture(Object ob){
	 int maxId=0;
	FieldVerificationVo fieldVo = (FieldVerificationVo) ob;
	ArrayList deleteQryList=new ArrayList();
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject=null;
	String deleteQuery="Delete from CR_FIELD_VERIFICATION_DTL where VERIFICATION_ID='"+fieldVo.getVerificationId()+"' and STAGE='DC' AND deal_id="+fieldVo.getDealId();
	logger.info("CR_FIELD_VERIFICATION_DTL deleteQuery: "+deleteQuery);
	deleteQryList.add(deleteQuery);
	boolean deleteStatus;
	try {
		deleteStatus = ConnectionDAO.sqlInsUpdDelete(deleteQryList);
		logger.info("deleteStatus: "+deleteStatus);
	} catch (Exception e1) {
	
		e1.printStackTrace();
	} 
	try {
			insertPrepStmtObject = new PrepStmtObject();			
			logger.info("In insert Field Verification Capturing");
			
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" INSERT INTO CR_FIELD_VERIFICATION_DTL(DEAL_ID,ENTITY_ID,ENTITY_TYPE,ENTITY_SUB_TYPE,VERIFICATION_ID,VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,APPRAISER_NAME,APPRAISAL_DATE,VERIFICATION_MODE,");
			bufInsSql.append(" PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,FV_STATUS,FV_REMARKS,MAKER_ID,MAKER_DATE,REC_STATUS,STAGE ) ");
			
			
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");//DEAL_ID
			bufInsSql.append(" ?,");//ENTITY_ID
			bufInsSql.append(" ?,");//ENTITY_TYPE
			bufInsSql.append(" ?,");//ENTITY_SUB_TYPE
			bufInsSql.append(" ?,");//VERIFICATION_ID
			bufInsSql.append(" ?,");//VERIFICATION_TYPE  
			bufInsSql.append(" ?,");//VERIFICATION_SUB_TYPE
			bufInsSql.append(" ?,");//APPRAISER_NAME
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'" + dateFormatWithTime + "'),");//APPRAISAL_DATE
			bufInsSql.append(" ?,");//VERIFICATION_MODE 
			bufInsSql.append(" ?,");//PERSON_MET
			bufInsSql.append(" ?,");//RELATION
			bufInsSql.append(" ?,");//PHONE1
			bufInsSql.append(" ?,");//PHONE2
			bufInsSql.append(" ?,");//E_MAIL
			bufInsSql.append(" ?,");//FV_STATUS
			bufInsSql.append(" ?,");//FV_REMARKS
			bufInsSql.append(" ?,");//MAKER_ID
			//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");//MAKER_DATE
			// saurabh space starts
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			// saurabh space ends
			bufInsSql.append(" ?, ");//REC_STATUS
			bufInsSql.append(" 'DC' )");//STAGE
			
			
		
			if (CommonFunction.checkNull(fieldVo.getDealId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getDealId().trim());
			
		
			if (CommonFunction.checkNull(fieldVo.getEntId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getEntId().trim());
			
		
			if (CommonFunction.checkNull(fieldVo.getEntityType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			{
				String appType=fieldVo.getEntityType();
				if(appType.equalsIgnoreCase("APPLICANT"))
					insertPrepStmtObject.addString("PRAPPL");
				else
					if(appType.equalsIgnoreCase("COAPPLICANT"))
						insertPrepStmtObject.addString("COAPPL");
					else
						insertPrepStmtObject.addString(appType);
			}
			
			if (CommonFunction.checkNull(fieldVo.getEntitySubType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getEntitySubType());
			
									
			if (CommonFunction.checkNull(fieldVo.getVerificationId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getVerificationId().trim());
			
			if (CommonFunction.checkNull(fieldVo.getVarificationType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			{
				insertPrepStmtObject.addString(fieldVo.getVarificationType().trim());
			}
			
			
		if (CommonFunction.checkNull(fieldVo.getVarificationSubType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			{
				insertPrepStmtObject.addString(fieldVo.getVarificationSubType().trim());
			}
						
			if (CommonFunction.checkNull(fieldVo.getAppraisalName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getAppraisalName().trim());
			
			if (CommonFunction.checkNull(fieldVo.getAppraisalDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getAppraisalDate().toUpperCase().trim());
			
			if (CommonFunction.checkNull(fieldVo.getVerificationMode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getVerificationMode().toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(fieldVo.getPersonToMeet()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getPersonToMeet().toUpperCase().trim());

			if (CommonFunction.checkNull(fieldVo.getRelationWithApplicant()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getRelationWithApplicant().trim());
			
			if (CommonFunction.checkNull(fieldVo.getPhone1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getPhone1());
			
			if (CommonFunction.checkNull(fieldVo.getPhone2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getPhone2());
			
			if (CommonFunction.checkNull(fieldVo.getEmail()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getEmail().trim());
			
			
			if (CommonFunction.checkNull(fieldVo.getCpvStatus()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getCpvStatus().trim());
	
			
			if (CommonFunction.checkNull(fieldVo.getRemarks()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getRemarks().trim());
			
			if (CommonFunction.checkNull(fieldVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerId().trim());
			
			if (CommonFunction.checkNull(fieldVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerDate().toUpperCase().trim());
			insertPrepStmtObject.addString("P");
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN insertFieldVerCapture() insert query1 ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
	
			boolean status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			String maxIdQuery="Select distinct max(FIELD_VERIFICATION_ID) from cr_field_verification_dtl  WITH (ROWLOCK) ";
			    
			  
			   String id=ConnectionDAO.singleReturn(maxIdQuery);
			   maxId=Integer.parseInt(id.toString());
			   logger.info("maxId : "+maxId);
			logger.info("print query....."+insertPrepStmtObject.printQuery());
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	return maxId;
}



public boolean updateFieldVerCapture(Object ob) 
{
	boolean status=false;
	FieldVerificationVo fieldVo = (FieldVerificationVo) ob;
	
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject=null;	
	try {
			insertPrepStmtObject = new PrepStmtObject();			
			logger.info("In Update Field Verification Capturing");			
			StringBuffer bufInsSql = new StringBuffer();
			String fieldVerificationUniqueId=fieldVo.getFieldVerificationUniqueId().trim();
			
		
			
			bufInsSql.append(" UPDATE CR_FIELD_VERIFICATION_DTL SET APPRAISER_NAME=?,APPRAISAL_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'" + dateFormatWithTime + "'),VERIFICATION_MODE=?,");
			bufInsSql.append(" PERSON_MET=?,RELATION=?,PHONE1=?,PHONE2=?,E_MAIL=?,");
			bufInsSql.append(" FV_STATUS=?,FV_REMARKS=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE= ");
			//bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");	
			// saurabh space starts
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			// saurabh space ends
			bufInsSql.append(" where STAGE='DC' AND FIELD_VERIFICATION_ID='"+fieldVerificationUniqueId+"' ");			
			
			
			
			
		
			if (CommonFunction.checkNull(fieldVo.getAppraisalName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getAppraisalName().trim());
			
			if (CommonFunction.checkNull(fieldVo.getAppraisalDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getAppraisalDate().toUpperCase().trim());
			
			if (CommonFunction.checkNull(fieldVo.getVerificationMode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getVerificationMode().toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(fieldVo.getPersonToMeet()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getPersonToMeet().toUpperCase().trim());

			if (CommonFunction.checkNull(fieldVo.getRelationWithApplicant()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getRelationWithApplicant().trim());
			
			if (CommonFunction.checkNull(fieldVo.getPhone1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getPhone1());
			
			if (CommonFunction.checkNull(fieldVo.getPhone2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getPhone2());
			
			if (CommonFunction.checkNull(fieldVo.getEmail()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getEmail().trim());
			
			if (CommonFunction.checkNull(fieldVo.getCpvStatus()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getCpvStatus().trim());
			
			
			
			if (CommonFunction.checkNull(fieldVo.getRemarks()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getRemarks().trim());
			
			if (CommonFunction.checkNull(fieldVo.getVerificationStatus()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getVerificationStatus().trim());
			
			if (CommonFunction.checkNull(fieldVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerId().trim());
			
			if (CommonFunction.checkNull(fieldVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerDate().toUpperCase().trim());
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN updateFieldVerCapture() update query ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);	
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("print query....."+status);
		
		} 
		catch (Exception e) 
		{e.printStackTrace();}
	return status;
}


public boolean modifyFieldRemarks(Object ob, String dealId) {
	FieldVerificationVo vo = (FieldVerificationVo) ob;
	boolean status = false;
	
	logger.info("In modifyFieldRemarks........cr_deal_verification_dtl.............................FieldVerificationDAOIMpl ");
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
	try {
		
		
			    StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_deal_verification_dtl  SET VERIFICATION_STATUS=?, VERIFICATION_REMARKS=?,REC_STATUS=? WHERE STAGE='DC' AND deal_id=? and VERIFICATION_ID=? ");
				
				if((CommonFunction.checkNull(vo.getCpvStatus())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCpvStatus()).trim());
				
				if((CommonFunction.checkNull(vo.getRemarks())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRemarks()).trim());
				
				insertPrepStmtObject.addString("R");
				
				if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(dealId).trim());
				
				if((CommonFunction.checkNull(vo.getVerificationId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getVerificationId()).trim());
				
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN modifyFieldRemarks(cr_deal_verification_dtl) UPDATE query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				 
		        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}

public ArrayList getAddressForField(String customerId) 
{ 
	ArrayList list=new ArrayList();
	ArrayList addressList=new ArrayList();
	try
	{
		String query="select distinct cr_deal_address_m.ADDRESS_TYPE,generic_master.DESCRIPTION "+
		             "from cr_deal_address_m,generic_master where generic_master.VALUE=cr_deal_address_m.ADDRESS_TYPE and BPID='"+customerId+"'";			
		logger.info("In getAddressForField......."+query);
		list = ConnectionDAO.sqlSelect(query);
		for(int i=0 ; i<list.size(); i++)
		{
			ArrayList list1 = (ArrayList) list.get(i);
			if(list1.size()>0)
			{
				FieldVerificationVo vo=new FieldVerificationVo();	
				vo.setAddressType(CommonFunction.checkNull(list1.get(0)).toString());
				vo.setAddressValue(CommonFunction.checkNull(list1.get(1)).toString());
				addressList.add(vo);				
			}
		}
	}
	catch(Exception e)
	{logger.error("Exception in checkDealID() :: "+e.getMessage());}		
	return addressList;
}

public ArrayList getGridListForAddress(String dealId, String Cust_id) 
{
	ArrayList list=new ArrayList();
	ArrayList gridList=new ArrayList();
	try
	{
		String query=" select distinct cr_field_verification_dtl.ADDRESS_TYPE,generic_master.DESCRIPTION as AddresValue, "+
		             " cr_deal_customer_role.DEAL_CUSTOMER_ID, cr_deal_customer_m.CUSTOMER_NAME "+
		             " from cr_field_verification_dtl left outer join cr_deal_customer_role"+
		             " on(cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_field_verification_dtl.ENTITY_ID) left outer join cr_deal_customer_m"+
		             " on(cr_deal_customer_role.DEAL_CUSTOMER_ID=cr_deal_customer_m.CUSTOMER_ID) left outer join generic_master"+
		             " on(generic_master.VALUE=cr_field_verification_dtl.ADDRESS_TYPE) "+
		             " where cr_field_verification_dtl.ENTITY_ID='"+Cust_id+"' and cr_field_verification_dtl.DEAL_ID='"+dealId+"' and VERIFICATION_SUB_TYPE='A'";			
		logger.info("In getGridListForAddress......."+query);
		list = ConnectionDAO.sqlSelect(query);
		for(int i=0 ; i<list.size(); i++)
		{
			ArrayList list1 = (ArrayList) list.get(i);
			if(list1.size()>0)
			{
				FieldVerificationVo vo=new FieldVerificationVo();	
				vo.setAddressType(CommonFunction.checkNull(list1.get(0)).toString());
				vo.setAddressValue(CommonFunction.checkNull(list1.get(1)).toString());
				vo.setCustomer_id(CommonFunction.checkNull(list1.get(2)).toString());
				vo.setCustomerName(CommonFunction.checkNull(list1.get(3)).toString());
				gridList.add(vo);				
			}
		}
	}
	catch(Exception e)
	{logger.error("Exception in checkDealID() :: "+e.getMessage());}		
	return gridList;
}

public ArrayList getAddressDetail(String customerId, String dealId,	String addressType) 
{
	ArrayList<UpdateVo> list=new ArrayList<UpdateVo>();
	String query =" select distinct DEAL_ID,ENTITY_ID,REFERENCE_NO,APPRAISER_NAME,VERIFICATION_MODE,APPRAISAL_DATE,ADDRESS_TYPE,generic_master.DESCRIPTION,"+
	" PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,FV_STATUS,FV_NEGATIVE_REASON,REASON_DESC,FV_REMARKS from CR_FIELD_VERIFICATION_DTL left outer join"+
	" com_reason_m on com_reason_m.rec_status='A' And com_reason_m.REASON_ID=CR_FIELD_VERIFICATION_DTL.FV_NEGATIVE_REASON left outer join generic_master on(generic_master.VALUE=ADDRESS_TYPE) "+
	" where DEAL_ID='"+dealId+"' and ENTITY_ID = '"+customerId+"' and ADDRESS_TYPE = '"+addressType+"'";
	logger.info("query : "+query);
	
	UpdateVo vo= null;
	try
	{
		ArrayList header = ConnectionDAO.sqlSelect(query);
		for(int i=0;i<header.size();i++)
		{	ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				vo = new UpdateVo();
				vo.setDeal_id((CommonFunction.checkNull(header1.get(0))).trim());
				vo.setCustomer_id((CommonFunction.checkNull(header1.get(1))).trim());
				vo.setReferenceId((CommonFunction.checkNull(header1.get(2))).trim());
				vo.setAppraisalName((CommonFunction.checkNull(header1.get(3))).trim());
				vo.setVerificationMode((CommonFunction.checkNull(header1.get(4))).trim());
				vo.setAppraisalDate((CommonFunction.checkNull(header1.get(5))).trim());
				vo.setAddressType((CommonFunction.checkNull(header1.get(6))).trim());
				vo.setAddressValue((CommonFunction.checkNull(header1.get(7))).trim());
				vo.setPersonToMeet((CommonFunction.checkNull(header1.get(8))).trim());
				vo.setRelationWithApplicant((CommonFunction.checkNull(header1.get(9))).trim());
				vo.setPhone1((CommonFunction.checkNull(header1.get(10))).trim());
				vo.setPhone2((CommonFunction.checkNull(header1.get(11))).trim());
				vo.setEmail((CommonFunction.checkNull(header1.get(12))).trim());
				vo.setCPVStatus((CommonFunction.checkNull(header1.get(13))).trim());
				vo.setLbxReasonId((CommonFunction.checkNull(header1.get(14))).trim());
				vo.setCPVNegReasonDesc((CommonFunction.checkNull(header1.get(15))).trim());
				vo.setRemarks((CommonFunction.checkNull(header1.get(16))).trim());
				
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

public boolean deleteAddressFieldRecord(String dealId, String customerId,String verificationId, String addressTypeList) 
{
	String query="delete from cr_field_verification_dtl where VERIFICATION_ID="+verificationId+" and DEAL_ID="+dealId+" and ENTITY_ID="+customerId+" and VERIFICATION_SUB_TYPE='A'  and ADDRESS_TYPE in("+addressTypeList+")";
	logger.info("Deletion query is :  ="+query);
	ArrayList deleteArl = new ArrayList();
	deleteArl.add(query);
	boolean status=false;
	try
	{
		status=ConnectionDAO.sqlInsUpdDelete(deleteArl);
		logger.info("Status of Deletion is ="+status);
	}
	catch(Exception e)
	{e.printStackTrace();}
	return status;
}

public ArrayList<CollateralCapturingVO> getfieldVerificationDetail(String dealId, String userId) {
	
	String query = "SELECT distinct VERIFICATION_SUB_TYPE FROM cr_field_verification_dtl fvd,cr_deal_verification_dtl dv " +
			"left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE " +
			"WHERE fvd.REC_STATUS='P'  and fvd.DEAL_ID=dv.DEAL_ID and fvd.VERIFICATION_ID=dv.VERIFICATION_ID " +
			"and (dv.internal_appraiser = '"+StringEscapeUtils.escapeSql(userId)+"' or agm.USER_ID = '"+StringEscapeUtils.escapeSql(userId)+"') AND fvd.DEAL_ID='"+StringEscapeUtils.escapeSql(dealId)+"' ";

	logger.info("In getfieldVerificationDetail (FieldVerification dao Imp).....:---"+query);
	ArrayList<CollateralCapturingVO> collateralsCapturedList = new ArrayList<CollateralCapturingVO>();
	try {
		ArrayList mainList = ConnectionDAO.sqlSelect(query);
		int mainListSize = mainList.size();
		for (int i = 0; i < mainListSize; i++) {
			ArrayList subList = (ArrayList) mainList.get(i);
			if (subList != null && subList.size() > 0) {
				CollateralCapturingVO vo = new CollateralCapturingVO();
				vo.setCollateralId((CommonFunction.checkNull(subList.get(0))).trim());
				collateralsCapturedList.add(vo);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return collateralsCapturedList;
}


public ArrayList getVerificationList(String dealId) {
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getVerificationList()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append(" SELECT DISTINCT v.VERIFICATION_MAPPING_ID,v.VERIFICATION_TYPE,v.VERIFICATION_SUB_TYPE,v.ENTITY_TYPE,v.ENTITY_SUB_TYPE,v.ADDRESS_TYPE,v.APPRAISER_TYPE,M.address_id,C.CUSTOMER_NAME,G.DESCRIPTION  ");
		bufInsSql.append(" FROM cr_deal_verification_m v  ");
		bufInsSql.append(" INNER JOIN cr_deal_customer_role R ON DEAL_ID='"+dealId+"' AND R.DEAL_CUSTOMER_ROLE_TYPE=V.ENTITY_TYPE  ");
		bufInsSql.append(" INNER JOIN cr_deal_customer_m C ON R.DEAL_CUSTOMER_ID=C.CUSTOMER_ID  ");
		bufInsSql.append(" INNER JOIN cr_deal_address_m m ON m.BPID=C.CUSTOMER_ID and m.BPTYPE='CS' AND M.ADDRESS_TYPE=V.ADDRESS_TYPE   ");
		bufInsSql.append(" INNER JOIN generic_master G ON G.GENERIC_KEY='ADDRESS_TYPE' AND G.VALUE=M.ADDRESS_TYPE ");
		bufInsSql.append(" where V.ENTITY_SUB_TYPE<>'BUSINESS' AND v.REC_STATUS='A' ");
		bufInsSql.append(" UNION  ALL ");
		bufInsSql.append(" SELECT DISTINCT v1.VERIFICATION_MAPPING_ID,v1.VERIFICATION_TYPE,v1.VERIFICATION_SUB_TYPE,v1.ENTITY_TYPE,v1.ENTITY_SUB_TYPE,v1.ADDRESS_TYPE,v1.APPRAISER_TYPE,M1.address_id,C1.CUSTOMER_NAME,G1.DESCRIPTION    ");
		bufInsSql.append(" FROM cr_deal_verification_m v1    ");
		bufInsSql.append(" INNER JOIN cr_deal_customer_role R1 ON R1.DEAL_ID='"+dealId+"' AND R1.DEAL_CUSTOMER_ROLE_TYPE=V1.ENTITY_TYPE  AND R1.DEAL_CUSTOMER_ROLE_TYPE='PRAPPL'   ");
		bufInsSql.append(" INNER JOIN cr_deal_customer_m C1 ON R1.DEAL_CUSTOMER_ID=C1.CUSTOMER_ID AND (C1.CUSTOMER_TYPE='C' OR (C1.CUSTOMER_TYPE='I' AND C1.CUSTOMER_CONSTITUTION='SELFEMP'))  ");
		bufInsSql.append(" INNER JOIN cr_deal_address_m m1 ON m1.BPID=C1.CUSTOMER_ID and m1.BPTYPE='CS'   ");
		bufInsSql.append(" INNER JOIN generic_master G1 ON G1.GENERIC_KEY='ADDRESS_TYPE' AND G1.VALUE=M1.ADDRESS_TYPE ");
		bufInsSql.append(" where V1.ENTITY_SUB_TYPE='BUSINESS' AND M1.ADDRESS_TYPE=V1.ADDRESS_TYPE AND v1.REC_STATUS='A'  ");
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT v.VERIFICATION_MAPPING_ID,v.VERIFICATION_TYPE,v.VERIFICATION_SUB_TYPE,v.ENTITY_TYPE,v.ENTITY_SUB_TYPE,v.ADDRESS_TYPE,v.APPRAISER_TYPE,B.ASSETID,m.ASSET_COLLATERAL_DESC,''  ");
		bufInsSql.append(" FROM cr_deal_verification_m v  ");
		bufInsSql.append(" INNER JOIN cr_deal_collateral_m B ON DEAL_ID='"+dealId+"'  ");
		bufInsSql.append(" INNER JOIN cr_asset_collateral_m m ON m.ASSET_ID=b.ASSETID and V.ENTITY_TYPE=M.ASSET_COLLATERAL_CLASS and m.ASSET_TYPE=v.ENTITY_SUB_TYPE ");
		bufInsSql.append(" where v.REC_STATUS='A' ");
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT  v.VERIFICATION_MAPPING_ID,v.VERIFICATION_TYPE,v.VERIFICATION_SUB_TYPE,v.ENTITY_TYPE,v.ENTITY_SUB_TYPE,v.ADDRESS_TYPE,v.APPRAISER_TYPE,B.DEAL_BUYER_SUPPLIER_ID,B.DEAL_BUYER_SUPPLIER_NAME,'' ");
		bufInsSql.append(" FROM cr_deal_verification_m v  ");
		bufInsSql.append(" INNER JOIN cr_deal_buyer_supplier_dtl B ON  CASE WHEN B.DEAL_BUYER_SUPPLIER_TYPE='B' THEN 'BUYER' ELSE 'SUPPLIER' END=V.ENTITY_TYPE  AND DEAL_ID='"+dealId+"'  ");
		bufInsSql.append(" where v.REC_STATUS='A'");
		
		//saurabh changes starts 
		
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append("   SELECT DISTINCT  v.VERIFICATION_MAPPING_ID,v.VERIFICATION_TYPE,v.VERIFICATION_SUB_TYPE,v.ENTITY_TYPE,v.ENTITY_SUB_TYPE,	v.ADDRESS_TYPE,v.APPRAISER_TYPE,B.OTHER_ID,B.OTHER_NAME,'' "); 
		bufInsSql.append("	FROM cr_deal_verification_m v  "); 
		bufInsSql.append("	INNER JOIN cr_deal_other_relation_dtl B ON  B.DEAL_OTHER_TYPE=V.ENTITY_TYPE  AND DEAL_ID='"+dealId+"'  "); 
		bufInsSql.append("	where v.REC_STATUS='A' ");
				
		//saurabh changes  ends 
		
		
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT v1.VERIFICATION_MAPPING_ID,v1.VERIFICATION_TYPE,v1.VERIFICATION_SUB_TYPE,v1.ENTITY_TYPE,v1.ENTITY_SUB_TYPE,v1.ADDRESS_TYPE,v1.APPRAISER_TYPE,'','','' ");
		bufInsSql.append(" FROM cr_deal_verification_m v1 ");
		bufInsSql.append(" INNER JOIN cr_deal_buyer_supplier_dtl B1 ON DEAL_ID='"+dealId+"'  ");
		bufInsSql.append(" where v1.ENTITY_TYPE='MARKET' and v1.REC_STATUS='A' ");
		
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT v2.VERIFICATION_MAPPING_ID,v2.VERIFICATION_TYPE,v2.VERIFICATION_SUB_TYPE,v2.ENTITY_TYPE,v2.ENTITY_SUB_TYPE,v2.ADDRESS_TYPE,v2.APPRAISER_TYPE,C2.CUSTOMER_id,C2.CUSTOMER_NAME,''  ");
		bufInsSql.append(" FROM cr_deal_verification_m v2  ");
		bufInsSql.append(" INNER JOIN cr_deal_customer_role R2 ON R2.DEAL_ID='"+dealId+"' AND R2.DEAL_CUSTOMER_ROLE_TYPE IN ('PRAPPL','COAPPL')  AND R2.DEAL_CUSTOMER_TYPE='I' ");
		bufInsSql.append(" INNER JOIN cr_deal_customer_m C2 ON R2.DEAL_CUSTOMER_ID=C2.CUSTOMER_ID  AND (CASE WHEN C2.CUSTOMER_CONSTITUTION='SALARIED' THEN 'INCOME' ELSE 'N' END)=V2.ENTITY_TYPE ");
		bufInsSql.append(" WHERE v2.REC_STATUS='A' AND V2.ENTITY_TYPE ='INCOME' ");
		
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT  V3.VERIFICATION_MAPPING_ID,V3.VERIFICATION_TYPE,V3.VERIFICATION_SUB_TYPE,V3.ENTITY_TYPE,V3.ENTITY_SUB_TYPE,V3.ADDRESS_TYPE,V3.APPRAISER_TYPE,B.TXN_DOC_ID,m.DOC_DESC,''   ");
		bufInsSql.append(" FROM cr_deal_verification_m V3  ");
		bufInsSql.append(" INNER JOIN CR_DOCUMENT_DTL B ON B.TXNID='"+dealId+"'  AND B.TXN_TYPE='DC'  AND B.DOC_STATUS='R' ");
		bufInsSql.append(" INNER JOIN CR_DOCUMENT_M m ON m.DOC_ID=b.DOC_ID ");
		bufInsSql.append(" where  V3.REC_STATUS='A' AND V3.VERIFICATION_TYPE='DOCUMENT VERIFICATION' AND V3.ENTITY_SUB_TYPE=B.DOC_TYPE ");
		
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT V4.VERIFICATION_MAPPING_ID,V4.VERIFICATION_TYPE,V4.VERIFICATION_SUB_TYPE,V4.ENTITY_TYPE,V4.ENTITY_SUB_TYPE,G.DESCRIPTION,V4.APPRAISER_TYPE,C.CUSTOMER_ID,C.CUSTOMER_NAME,'' ");  
		bufInsSql.append(" FROM cr_deal_verification_m V4 " );
		bufInsSql.append(" INNER JOIN cr_deal_customer_role R ON DEAL_ID='"+dealId+"' AND R.DEAL_CUSTOMER_ROLE_TYPE=V4.ENTITY_TYPE ");  
		bufInsSql.append(" INNER JOIN cr_deal_customer_m C ON R.DEAL_CUSTOMER_ID=C.CUSTOMER_ID   AND C.CUSTOMER_CONSTITUTION=V4.ENTITY_SUB_TYPE ");
		bufInsSql.append(" INNER JOIN generic_master G ON G.GENERIC_KEY='CUST_CONSTITUTION' AND G.VALUE=V4.ENTITY_SUB_TYPE ");
		bufInsSql.append(" where V4.REC_STATUS='A' ");
		
		logger.info("IN getVerificationList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getVerificationList " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
				
				deptMVO.setVerificationMappingId(CommonFunction.checkNull(data.get(0)));
				deptMVO.setVerificationType(CommonFunction.checkNull(data.get(1)));			
				deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(2)));
				
				if(CommonFunction.checkNull(data.get(3)).equalsIgnoreCase("PRAPPL"))
				{
					deptMVO.setEntityType("APPLICANT");
				}
				else if(CommonFunction.checkNull(data.get(3)).equalsIgnoreCase("COAPPL"))
				{
					deptMVO.setEntityType("COAPPLICANT");
				}
				else
				{
					deptMVO.setEntityType(CommonFunction.checkNull(data.get(3)));
				}
				
				deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(4)).toString());
				
				
				if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("PRAPPL"))
				{
					deptMVO.setEntitySubTypeDesc("APPLICANT");
				}
				else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("COAPPL"))
				{
					deptMVO.setEntitySubTypeDesc("COAPPLICANT");
				}
				else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("APPL"))
				{
					deptMVO.setEntitySubTypeDesc("APPLICATION");
				}
				else 
				{
					deptMVO.setEntitySubTypeDesc(CommonFunction.checkNull(data.get(4)));
				}
				if(CommonFunction.checkNull(data.get(1)).equalsIgnoreCase("CONTACT VERIFICATION"))
				{
				  deptMVO.setAddressType(CommonFunction.checkNull(data.get(5)));
				}
				else if(CommonFunction.checkNull(data.get(1)).equalsIgnoreCase("CONSTITUTION VERIFICATION"))
				{
					deptMVO.setEntitySubTypeDesc(CommonFunction.checkNull(data.get(5)));
				}
				
				
			    deptMVO.setAppraiserType(CommonFunction.checkNull(data.get(6)));
				deptMVO.setEntId(CommonFunction.checkNull(data.get(7)));
				deptMVO.setEntityDesc(CommonFunction.checkNull(data.get(8)));
				deptMVO.setAddressTypeDesc(CommonFunction.checkNull(data.get(9)));
				detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}


public ArrayList showVerificationList(String dealId) {
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In showVerificationList()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT DISTINCT VERIFICATION_ID,VERIFICATION_TYPE,APPRAISER_TYPE,U.USER_NAME,VERIFICATION_SUBTYPE,CASE WHEN VERIFICATION_ACTION='I' THEN 'INITIATED' ELSE 'WAIVED' END AS VERIFICATION_ACTION ,ENTITY_ID,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,AG.AGENCY_NAME,G.DESCRIPTION ,CASE WHEN V.REC_STATUS = 'F' THEN 'INITIATED' WHEN V.REC_STATUS = 'R' THEN 'CAPTURED' WHEN V.REC_STATUS = 'C' THEN 'COMPLETED' END AS REC_STATUS,G1.DESCRIPTION FROM cr_deal_verification_dtl V ");
		bufInsSql.append(" LEFT JOIN sec_user_m U ON  U.USER_ID =V.INTERNAL_APPRAISER  ");
        bufInsSql.append(" LEFT JOIN generic_master G ON G.GENERIC_KEY='ADDRESS_TYPE' AND  G.VALUE=V.ADDRESS_TYPE  ");
        bufInsSql.append(" LEFT JOIN generic_master G1 ON G1.GENERIC_KEY='CUST_CONSTITUTION' AND  G1.VALUE=V.ENTITY_SUB_TYPE  ");
		bufInsSql.append(" left join com_agency_m ag on V.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE ");
		bufInsSql.append(" WHERE VERIFICATION_ACTION IN ('I','W')  AND STAGE='DC' AND DEAL_ID= '"+dealId+"' ");
		
		logger.info("IN showVerificationList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("showVerificationList " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
				
				deptMVO.setVerificationMappingId(CommonFunction.checkNull(data.get(0)));
				
				deptMVO.setVerificationType(CommonFunction.checkNull(data.get(1)));
				
				 deptMVO.setAppraiserType(CommonFunction.checkNull(data.get(2)));
				
				 deptMVO.setInternalAppraiser(CommonFunction.checkNull(data.get(3)));
				 deptMVO.setExternalAppraiser(CommonFunction.checkNull(data.get(3)));
				 			 
				 deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(4)));
				 
				 deptMVO.setActionValue(CommonFunction.checkNull(data.get(5)));
				 
				 deptMVO.setEntId(CommonFunction.checkNull(data.get(6)));
				 
				 if(CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("PRAPPL"))
				 {
					 deptMVO.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("COAPPL"))
				 {
					 deptMVO.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 deptMVO.setEntityType(CommonFunction.checkNull(data.get(7)));
				 }
				 if(CommonFunction.checkNull(data.get(8)).equalsIgnoreCase("PRAPPL"))
					{
						deptMVO.setEntitySubType("APPLICANT");
					}
					else if(CommonFunction.checkNull(data.get(8)).equalsIgnoreCase("COAPPL"))
					{
						deptMVO.setEntitySubType("COAPPLICANT");
					}
					else if(CommonFunction.checkNull(data.get(8)).equalsIgnoreCase("APPL"))
					{
						deptMVO.setEntitySubType("APPLICATION");
					}
					else 
					{
						deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(8)));
					}
							
				   deptMVO.setEntityDesc(CommonFunction.checkNull(data.get(9)));
				   deptMVO.setAgencyName(CommonFunction.checkNull(data.get(10)));
                   deptMVO.setAddressTypeDesc(CommonFunction.checkNull(data.get(11)));
                   deptMVO.setStatus(CommonFunction.checkNull(data.get(12)));
              
              if(CommonFunction.checkNull(data.get(1)).equalsIgnoreCase("CONSTITUTION VERIFICATION"))
				 {
					deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(13)));
				 }
			
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public ArrayList getQuestList(String dealId, String entityId,String verificationId) {
	
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getQuestList()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				String sqlquery1="select count(distinct b.question_id) from  cr_deal_verification_question_m a inner join cr_verification_question_product_mapping_dtl b on a.QUESTION_ID=b.QUESTION_ID   ";

		int questionID=Integer.parseInt(ConnectionDAO.singleReturn(sqlquery1.toString()));				
		bufInsSql.append("SELECT q.QUESTION_ID,q.QUESTION,q.QUESTION_SEQ_NO,tq.QUESTION_REMARKS,tq.VERIFICATION_METHOD,tq.QUESTION_VERIFICATION_ID,CASE WHEN q.VERIFICATION_REQD='Y' THEN 'YES' ELSE 'NO' END   FROM cr_deal_verification_question_m q ");
		bufInsSql.append(" inner join cr_deal_verification_dtl v on v.VERIFICATION_ID='"+verificationId+"' ");
		bufInsSql.append(" left join cr_deal_question_verification_dtl tq on tq.QUESTION_ID=q.QUESTION_ID AND TQ.VERIFICATION_ID=v.VERIFICATION_ID ");
		bufInsSql.append(" inner join cr_deal_loan_dtl a on a.deal_id=v.deal_id");
		if(questionID>0)
		{
			bufInsSql.append(" join cr_verification_question_product_mapping_dtl s on s.question_id=q.question_id and s.product_id =a.deal_product");
		}
		
		bufInsSql.append(" WHERE q.REC_STATUS='A' and q.VERIFICATION_TYPE=v.VERIFICATION_TYPE and q.VERIFICATION_SUB_TYPE=v.VERIFICATION_SUBTYPE AND q.ENTITY_TYPE=V.ENTITY_TYPE AND q.ENTITY_SUB_TYPE=V.ENTITY_SUB_TYPE AND ((q.VERIFICATION_TYPE='CONTACT VERIFICATION' AND q.ADDRESS_TYPE=v.ADDRESS_TYPE) OR (q.VERIFICATION_TYPE<>'CONTACT VERIFICATION' and 'a'='a')) and (( s.product_id ='' and s.product_id =a.deal_product)or( s.product_id <> '' and 'a'='a'))");
		
		logger.info("IN getQuestList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getQuestList " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
				
				deptMVO.setQuestId(CommonFunction.checkNull(data.get(0)));
				
					 
				
				 deptMVO.setQuest(CommonFunction.checkNull(data.get(1)));
				 deptMVO.setQuestSeqNo(CommonFunction.checkNull(data.get(2)));
				 
				 deptMVO.setQuestRemarks(CommonFunction.checkNull(data.get(3)));
				 deptMVO.setQuestVerifMethod(CommonFunction.checkNull(data.get(4)));
				 deptMVO.setQuestVerifUniqueId(CommonFunction.checkNull(data.get(5)));
				 deptMVO.setVerifQuestRequired(CommonFunction.checkNull(data.get(6)));
				 
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public ArrayList getVerifList(String dealId, String entityId, String verificationId) {
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getVerifList()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT VERIFICATION_TYPE,VERIFICATION_SUBTYPE,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,(SELECT AGENCY_NAME FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER FROM cr_deal_verification_dtl V ");
		bufInsSql.append(" WHERE STAGE='DC' AND DEAL_ID= '"+dealId+"' and VERIFICATION_ID="+verificationId+" ");
		
		logger.info("IN getVerifList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getVerifList " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
				
				
				
				deptMVO.setVerificationType(CommonFunction.checkNull(data.get(0)));
				
								 			 
				 deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(1)));
				 
				 
				
				 if(CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("PRAPPL"))
				 {
					 deptMVO.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("COAPPL"))
				 {
					 deptMVO.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 deptMVO.setEntityType(CommonFunction.checkNull(data.get(2)));
				 }
				 deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(3)));
							
				 deptMVO.setEntityDesc(CommonFunction.checkNull(data.get(4)));
				 deptMVO.setAgencyName(CommonFunction.checkNull(data.get(5)));
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public ArrayList getAddressWithDeal(String entityId) {
	
	
	// TODO Auto-generated method stub
	return null;
}

public boolean insertQuestionDetails(FieldVerificationVo vo) {
	
	
	qryList=new ArrayList();
	PrepStmtObject insertPrepStmtObject=null;
	
	String[] questionId=vo.getQuestionId();
	String[] questionSeqNo=vo.getQuestionSeqNo();
	String[] questionRemarks=vo.getQuestionRemarks();
	String[] verificationMethod=vo.getVerificationMethod();
	ArrayList deleteQryList=new ArrayList();
	boolean status=false;
	String deleteQuery="Delete from cr_deal_question_verification_dtl where VERIFICATION_ID='"+vo.getVerificationId()+"' and STAGE='DC' AND deal_id="+vo.getDealId();
	logger.info("cr_deal_question_verification_dtl deleteQuery: "+deleteQuery);
	deleteQryList.add(deleteQuery);
	boolean deleteStatus;
	try {
		deleteStatus = ConnectionDAO.sqlInsUpdDelete(deleteQryList);
		logger.info("deleteStatus: "+deleteStatus);
	} catch (Exception e1) {
	
		e1.printStackTrace();
	} 
	
	logger.info("questionId: "+questionId);
	if(questionId.length>0)
	{
	for(int k=0;k<questionId.length;k++)
	{
	
	 StringBuilder query=new StringBuilder();
	 query.append("INSERT INTO cr_deal_question_verification_dtl(VERIFICATION_ID,VERIFICATION_CAPTURING_ID,QUESTION_ID,DEAL_ID,QUESTION_SEQ_NO,QUESTION_REMARKS,VERIFICATION_METHOD,REC_STATUS,MAKER_ID,MAKER_DATE,STAGE)");
     insertPrepStmtObject = new PrepStmtObject();
    
        
     query.append(" values ( ");
     query.append(" ?,");//VERIFICATION_ID
     query.append(" ?,");//VERIFICATION_CAPTURING_ID
     query.append(" ?,");//QUESTION_ID
     query.append(" ?,");//DEAL_ID
     query.append(" ?,");//QUESTION_SEQ_NO
     query.append(" ?,");//QUESTION_REMARKS  
     query.append(" ?,");//VERIFICATION_METHOD
     query.append(" ?,");//REC_STATUS
     query.append(" ?,");//MAKER_ID
     //query.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");//MAKER_DATE
  // saurabh space starts
     query.append(dbo);
     query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9), ");
     query.append(" 'DC' )");//STAGE
     // saurabh space ends
    
		
     if((CommonFunction.checkNull(vo.getVerificationId())).trim().equalsIgnoreCase(""))
 		insertPrepStmtObject.addNull();
 	else
 		insertPrepStmtObject.addString((vo.getVerificationId()).trim());
     
    if((CommonFunction.checkNull(vo.getFieldVerificationUniqueId())).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getFieldVerificationUniqueId()).trim());
    
    if(CommonFunction.checkNull(questionId[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(questionId[k]).trim());
    
    
    if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getDealId()).trim());
  
    if(CommonFunction.checkNull(questionSeqNo[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(questionSeqNo[k]).trim());
    
    
    if(CommonFunction.checkNull(questionRemarks[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(questionRemarks[k]).trim());
    
    
    if(CommonFunction.checkNull(verificationMethod[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(verificationMethod[k]).trim());
    
    insertPrepStmtObject.addString("P");
    
    
	if(CommonFunction.checkNull(vo.getUserId()).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getUserId()).trim());
	
	if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getMakerDate()).trim());
	
	   
        insertPrepStmtObject.setSql(query.toString());
        
        logger.info("IN updateUnderwritingQueryData() update query1 ### "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
	}
	        
	
	try {
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
    //logger.info("In updateUnderwritingQueryData......................status= "+status);
    return status;
}

public ArrayList getCommonListList(String dealId, String entityId,
		String verificationId) {
	
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getCommonListList()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT FIELD_VERIFICATION_ID,APPRAISER_NAME,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(APPRAISAL_DATE,'"+dateFormat+"'),VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,FV_STATUS,FV_REMARKS FROM cr_field_verification_dtl ");
		bufInsSql.append(" WHERE STAGE='DC' AND DEAL_ID= '"+dealId+"' and VERIFICATION_ID="+verificationId+" ");
		
		logger.info("IN getCommonListList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getCommonListList " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
								
				 deptMVO.setFieldVerificationUniqueId(CommonFunction.checkNull(data.get(0)));
				 deptMVO.setAppraisalName(CommonFunction.checkNull(data.get(1)));	 			 
				 deptMVO.setAppraisalDate(CommonFunction.checkNull(data.get(2)));
		    	 deptMVO.setVerificationMode(CommonFunction.checkNull(data.get(3)));
				 deptMVO.setPersonToMeet(CommonFunction.checkNull(data.get(4)));
				 deptMVO.setRelationWithApplicant(CommonFunction.checkNull(data.get(5)));	
				 deptMVO.setPhone1(CommonFunction.checkNull(data.get(6)));	
				 deptMVO.setPhone2(CommonFunction.checkNull(data.get(7)));	
				 deptMVO.setEmail(CommonFunction.checkNull(data.get(8)));	
				 deptMVO.setCpvStatus(CommonFunction.checkNull(data.get(9)));
				 deptMVO.setRemarks(CommonFunction.checkNull(data.get(10)));
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public boolean updateQuestionDetails(FieldVerificationVo vo) {
	
	qryList=new ArrayList();
	PrepStmtObject insertPrepStmtObject=null;
	boolean status=false;
	String[] questionVerificationUniqueId=vo.getQuestionVerificationUniqueId();
	String[] questionRemarks=vo.getQuestionRemarks();
	String[] verificationMethod=vo.getVerificationMethod();
	String checkQuery="Select count(*) from cr_deal_question_verification_dtl where deal_id="+vo.getDealId();
	logger.info("checkQuery: "+checkQuery);
	String statusCount=ConnectionDAO.singleReturn(checkQuery);
	logger.info("statusCount: "+statusCount);
	logger.info("questionVerificationUniqueId: "+questionVerificationUniqueId.length);
	if(!CommonFunction.checkNull(statusCount).equalsIgnoreCase("0") && questionVerificationUniqueId.length>0)
	{
	for(int k=0;k<questionVerificationUniqueId.length;k++)
	{
	 insertPrepStmtObject=new PrepStmtObject();
	 StringBuilder query=new StringBuilder();
	 query.append(" UPDATE cr_deal_question_verification_dtl SET QUESTION_REMARKS=?,VERIFICATION_METHOD=?,REC_STATUS=?,");
	 query.append(" MAKER_ID=?,MAKER_DATE= ");
	 //query.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");	
	// saurabh space starts
	 query.append(dbo);
	 query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
	 // saurabh space ends
	 query.append(" where STAGE='DC' AND QUESTION_VERIFICATION_ID='"+questionVerificationUniqueId[k]+"' ");			
	   
        
    if(CommonFunction.checkNull(questionRemarks[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(questionRemarks[k]).trim());
    
    
    if(CommonFunction.checkNull(verificationMethod[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(verificationMethod[k]).trim());
    
    if (CommonFunction.checkNull(vo.getVerificationStatus()).equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(vo.getVerificationStatus().trim());
       
    if(CommonFunction.checkNull(vo.getUserId()).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getUserId()).trim());
  
    
    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getMakerDate()).trim());
    
		   
        insertPrepStmtObject.setSql(query.toString());
        
        logger.info("IN updateUnderwritingQueryData() update query1 ### "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
	}
	      
	
	try {
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
    //logger.info("In updateUnderwritingQueryData......................status= "+status);
    return status;
}

public ArrayList getCompletionList(String dealId) {
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getCompletionList()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT FIELD_VERIFICATION_ID,APPRAISER_NAME,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(APPRAISAL_DATE,'"+dateFormat+"'),VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,ENTITY_TYPE FROM cr_field_verification_dtl ");
		bufInsSql.append(" WHERE DEAL_ID= '"+dealId+"' AND STAGE='DC' ");
		
		logger.info("IN getCompletionList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getCompletionList " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
								
				 deptMVO.setFieldVerificationUniqueId(CommonFunction.checkNull(data.get(0)));
				 deptMVO.setAppraisalName(CommonFunction.checkNull(data.get(1)));	 			 
				 deptMVO.setAppraisalDate(CommonFunction.checkNull(data.get(2)));
				 deptMVO.setVerificationType(CommonFunction.checkNull(data.get(3)));
		    	 deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(4)));
		    	 if(CommonFunction.checkNull(data.get(5)).equalsIgnoreCase("PRAPPL"))
				 {
					 deptMVO.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(data.get(5)).equalsIgnoreCase("COAPPL"))
				 {
					 deptMVO.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 deptMVO.setEntityType(CommonFunction.checkNull(data.get(5)));
				 }
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public boolean completionAuthorVerf(FieldVerificationVo vo) {
	
	
	boolean status = false;
	
	logger.info("In completionAuthorVerf.....................................FieldVerificationDAOIMpl ");
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	PrepStmtObject insertPrepStmtObjectDealVerification = new PrepStmtObject();
	StringBuffer bufInsSql = new StringBuffer();
	String rec_status_FV = "";
	String rec_status_DV = "";
	try {
			
		  logger.info("In completionAuthorVerf..........................before split...........vo.getVerificationCapId() "+vo.getVerificationCapId());
		   String[] checkedIDArr = vo.getVerificationCapId().split("/");
		   logger.info("In completionAuthorVerf.......................after split..............vo.getVerificationCapId() "+checkedIDArr);
			if(CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("S")){
				rec_status_FV = "P";
				rec_status_DV = "F";
				if(checkedIDArr.length > 0)
				{					
						for(int i =0;i<checkedIDArr.length;i++)
						{	
							 bufInsSql = new StringBuffer();
			                 insertPrepStmtObject = new PrepStmtObject();
			                 insertPrepStmtObjectDealVerification = new PrepStmtObject();
				             bufInsSql.append("UPDATE cr_field_verification_dtl  SET FV_STATUS=?, rec_status=?,COMPL_REMARKS=? WHERE STAGE='DC' AND FIELD_VERIFICATION_ID=?");
				
				if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDecison()).trim());
				
				
				insertPrepStmtObject.addString(rec_status_FV);
				
				
				if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
											    
			    if((CommonFunction.checkNull(checkedIDArr[i])).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(checkedIDArr[i].trim());
					   
				 insertPrepStmtObject.setSql(bufInsSql.toString());
				
						
				  logger.info("IN completionAuthorVerf() update query1(cr_field_verification_dtl) ### "+insertPrepStmtObject.printQuery());
				  qryList.add(insertPrepStmtObject);
				
				
				 StringBuffer bufInsSqlDealVerification = new StringBuffer();
				 bufInsSqlDealVerification.append("UPDATE cr_deal_verification_dtl  SET VERIFICATION_STATUS=?, VERIFICATION_REMARKS=?,AUTHOR_REMARKS=?,"+
							"rec_status=?,AUTHOR_ID=?,AUTHOR_DATE=");
				 //bufInsSqlDealVerification.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
				// saurabh space starts
				 bufInsSqlDealVerification.append(dbo);
				 bufInsSqlDealVerification.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				 // saurabh space ends
				 bufInsSqlDealVerification.append("WHERE VERIFICATION_ID=(select ISNULL(VERIFICATION_ID,0) from cr_field_verification_dtl where STAGE='DC' AND FIELD_VERIFICATION_ID=?)");
					
					if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
						insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getDecison()).trim());
					
					if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
						insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getTextarea()).trim());
					
					if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
						insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getTextarea()).trim());
					
					insertPrepStmtObjectDealVerification.addString(rec_status_DV);
					
					if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getMakerId()).trim());
				  
				    
				    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
				    	insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getMakerDate()).trim());
				    
				    if((CommonFunction.checkNull(checkedIDArr[i].trim()).equalsIgnoreCase("")))
				    	insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString(checkedIDArr[i].trim());

						   
				       insertPrepStmtObjectDealVerification.setSql(bufInsSqlDealVerification.toString());
					
					
						
					  logger.info("IN completionAuthorVerf() update query2(cr_deal_verification_dtl) ### "+insertPrepStmtObjectDealVerification.printQuery());
					   qryList.add(insertPrepStmtObjectDealVerification);
						}
				}
			}
			else
			{
				rec_status_FV = "A";
				rec_status_DV = "C";
				  bufInsSql = new StringBuffer();
					bufInsSql.append("UPDATE cr_field_verification_dtl  SET FV_STATUS=?, rec_status=?,COMPL_REMARKS=? WHERE DEAL_ID=?");
					
					if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDecison()).trim());
					
					
					insertPrepStmtObject.addString(rec_status_FV);
					
					
					if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTextarea()).trim());
												    
				    if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDealId()).trim());
						   
					 insertPrepStmtObject.setSql(bufInsSql.toString());
					
							
					  logger.info("IN completionAuthorVerf() update query1(cr_field_verification_dtl) ### "+insertPrepStmtObject.printQuery());
					  qryList.add(insertPrepStmtObject);
					
					
					 StringBuffer bufInsSqlDealVerification = new StringBuffer();
					 bufInsSqlDealVerification.append("UPDATE cr_deal_verification_dtl  SET VERIFICATION_STATUS=?, VERIFICATION_REMARKS=?,AUTHOR_REMARKS=?,"+
								"rec_status=?,AUTHOR_ID=?,AUTHOR_DATE=");
					 //bufInsSqlDealVerification.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
					// saurabh space starts
					 bufInsSqlDealVerification.append(dbo);
					 bufInsSqlDealVerification.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					 // saurabh space ends
					 bufInsSqlDealVerification.append("WHERE DEAL_ID=?");
						
						if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
							insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getDecison()).trim());
						
						if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
							insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getTextarea()).trim());
						
						if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
							insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getTextarea()).trim());
						
						insertPrepStmtObjectDealVerification.addString(rec_status_DV);
						
						if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
							insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getMakerId()).trim());
					  
					    
					    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
					    	insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getMakerDate()).trim());
					    
					    if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					    	insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getDealId()).trim());

							   
					       insertPrepStmtObjectDealVerification.setSql(bufInsSqlDealVerification.toString());
						
						
							
						  logger.info("IN completionAuthorVerf() update query2(cr_deal_verification_dtl) ### "+insertPrepStmtObjectDealVerification.printQuery());
						qryList.add(insertPrepStmtObjectDealVerification);

				
			}
			
					
				    status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		 
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}

public ArrayList getViewCommonListList(String dealId, String fieldVerificationId) {
	
	
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getViewCommonListList()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,ENTITY_TYPE,ENTITY_SUB_TYPE,APPRAISER_NAME,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(APPRAISAL_DATE,'"+dateFormat+"'),VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,FV_STATUS,FV_REMARKS FROM cr_field_verification_dtl ");
		bufInsSql.append(" WHERE DEAL_ID= '"+dealId+"' AND STAGE='DC' and FIELD_VERIFICATION_ID="+fieldVerificationId+" ");
		
		logger.info("IN getViewCommonListList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getViewCommonListList " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
								
				deptMVO.setVerificationType(CommonFunction.checkNull(data.get(0)));
				
	 			 
				 deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(1)));
				 
				 
				
				 if(CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("PRAPPL"))
				 {
					 deptMVO.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("COAPPL"))
				 {
					 deptMVO.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 deptMVO.setEntityType(CommonFunction.checkNull(data.get(2)));
				 }
				 deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(3)));
				
				 deptMVO.setAppraisalName(CommonFunction.checkNull(data.get(4)));	 			 
				 deptMVO.setAppraisalDate(CommonFunction.checkNull(data.get(5)));
		    	 deptMVO.setVerificationMode(CommonFunction.checkNull(data.get(6)));
				 deptMVO.setPersonToMeet(CommonFunction.checkNull(data.get(7)));
				 deptMVO.setRelationWithApplicant(CommonFunction.checkNull(data.get(8)));	
				 deptMVO.setPhone1(CommonFunction.checkNull(data.get(9)));	
				 deptMVO.setPhone2(CommonFunction.checkNull(data.get(10)));	
				 deptMVO.setEmail(CommonFunction.checkNull(data.get(11)));	
				 deptMVO.setCpvStatus(CommonFunction.checkNull(data.get(12)));
				 deptMVO.setRemarks(CommonFunction.checkNull(data.get(13)));
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
	
}

public ArrayList getViewQuestList(String dealId, String fieldVerificationId) {
	
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getViewQuestList()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT q.QUESTION_ID,q.QUESTION,q.QUESTION_SEQ_NO,tq.QUESTION_REMARKS,tq.VERIFICATION_METHOD,tq.QUESTION_VERIFICATION_ID  FROM cr_deal_question_verification_dtl tq  ");
		bufInsSql.append(" INNER join cr_deal_verification_question_m q on tq.QUESTION_ID=q.QUESTION_ID ");
		bufInsSql.append(" WHERE VERIFICATION_CAPTURING_ID='"+fieldVerificationId+"'  ");
		
		logger.info("IN getViewQuestList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getViewQuestList " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
				
				deptMVO.setQuestId(CommonFunction.checkNull(data.get(0)));
				
					 
				
				 deptMVO.setQuest(CommonFunction.checkNull(data.get(1)));
				 deptMVO.setQuestSeqNo(CommonFunction.checkNull(data.get(2)));
				 
				 deptMVO.setQuestRemarks(CommonFunction.checkNull(data.get(3)));
				 deptMVO.setQuestVerifMethod(CommonFunction.checkNull(data.get(4)));
				 deptMVO.setQuestVerifUniqueId(CommonFunction.checkNull(data.get(5)));
				 
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public ArrayList getVerifMethodListList() {
	
	ArrayList list=new ArrayList();
	try
	{
		String query = " SELECT G.VALUE,G.DESCRIPTION FROM generic_master G WHERE G.REC_STATUS='A' AND G.GENERIC_KEY='VERIFICATION_METHOD'";
		logger.info(" getVerifMethodListList()........................ query : "+query);
		CodeDescVo deptMVO = null;
		ArrayList header = ConnectionDAO.sqlSelect(query);
		for(int i=0;i<header.size();i++)
		{
			logger.info("header"+header.size());
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				deptMVO=new CodeDescVo();
				deptMVO.setId((CommonFunction.checkNull(header1.get(0))).trim());
				deptMVO.setName((CommonFunction.checkNull(header1.get(1))).trim());
				list.add(deptMVO);
			}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return list;
}

public boolean authorAllWavedVerifInit(FieldVerificationVo vo) {
	
	
	boolean status = false;
	
	logger.info("In authorAllWavedVerifInit.....................................FieldVerificationDAOIMpl ");
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	
	try {
		
	
			    StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_deal_verification_dtl SET VERIFICATION_STATUS=?, VERIFICATION_REMARKS=?,AUTHOR_REMARKS=?,"+
						" rec_status='C',AUTHOR_ID=?,AUTHOR_DATE=");
				//bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
				// saurabh space starts
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				// saurabh space ends
				bufInsSql.append("WHERE STAGE='DC' AND DEAL_ID=?");
				
				if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDecison()).trim());
				
				if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
				
				if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
				
				
				if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
			  
			    
			    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
			    
			    if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDealId()).trim());

					   
				 insertPrepStmtObject.setSql(bufInsSql.toString());
				
				
					
				  logger.info("IN authorAllWavedVerifInit() update query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				
					
				    status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		 
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}
public ArrayList<FieldVerificationVo> initiatedVerificationLoanDetail(FieldVerificationVo vo) {
	
	ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	logger.info("In initiatedVerificationLoanDetail ");
	try
	{
		ArrayList header=null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		
		logger.info(" initiatedVerificationLoanDetail() here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
			String userName=ConnectionDAO.singleReturn(userNameQ);
			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		
			
			FieldVerificationVo fieldVo= (FieldVerificationVo) vo;
		boolean appendSQL=false;
		
		
		StringBuffer bufInsSql=new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		
		String loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim());
		String appDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationDate())).trim());
		String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
		String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
		String schemeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
		String initDate= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate())).trim());
		bufInsSql.append(" select distinct d.LOAN_ID,d.LOAN_NO,deal.CUSTOMER_NAME,");
		bufInsSql.append(" p.PRODUCT_DESC,s.SCHEME_DESC ");
		bufInsSqlTempCount.append(" select distinct count(1) from (select distinct d.LOAN_ID,d.LOAN_NO,deal.CUSTOMER_NAME,");
		bufInsSqlTempCount.append(" p.PRODUCT_DESC,s.SCHEME_DESC ");
		
		//bufInsSql.append(" CASE WHEN dv.REC_STATUS='P' THEN 'In pross' WHEN d.REC_STATUS IN ('P','F') THEN 'Pending' end as rsstatus,D.DEAL_RM,");
		//bufInsSql.append(dbo);
		//bufInsSql.append("DATE_FORMAT(D.DEAL_DATE,'"+dateFormat+"')");
		//bufInsSqlTempCount.append(" CASE WHEN dv.REC_STATUS='P' THEN 'In pross' WHEN d.REC_STATUS IN ('P','F') THEN 'Pending' end as rsstatus,D.DEAL_RM,");
		//bufInsSqlTempCount.append(dbo);
		//bufInsSqlTempCount.append("DATE_FORMAT(D.DEAL_DATE,'"+dateFormat+"')DEALDATE");
		
		//bufInsSql.append(" ,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CASE WHEN dv.REC_STATUS='P' THEN dv.MAKER_ID ELSE D.MAKER_ID END) MAKER_ID ");
		//bufInsSqlTempCount.append(" ,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=CASE WHEN dv.REC_STATUS='P' THEN dv.MAKER_ID ELSE D.MAKER_ID END) MAKER_ID ");
		bufInsSql.append(" from cr_loan_dtl d left join cr_deal_verification_dtl dv on  dv.stage='LIM' and d.LOAN_ID=dv.LOAN_ID ");
		bufInsSqlTempCount.append(" from cr_loan_dtl d left join cr_deal_verification_dtl dv on  dv.stage='LIM' and d.LOAN_ID=dv.LOAN_ID ");
		
		//bufInsSql.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID ");
		bufInsSql.append("left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID "); 
		//bufInsSqlTempCount.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID ");
		bufInsSqlTempCount.append("left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID "); 
		bufInsSql.append(" INNER join gcd_customer_m deal on deal.CUSTOMER_ID=d.LOAN_CUSTOMER_ID "); 
		bufInsSqlTempCount.append(" INNER join gcd_customer_m deal on deal.CUSTOMER_ID=d.LOAN_CUSTOMER_ID "); 
		//bufInsSql.append(" INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID AND DM.DEAL_RECEIVED IS NOT NULL AND DM.DEAL_FORWARDED IS NULL AND DM.DEAL_STAGE_ID='FVI' AND DM.REC_STATUS='A' "); 
		//bufInsSqlTempCount.append(" INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID  AND DM.DEAL_RECEIVED IS NOT NULL AND DM.DEAL_FORWARDED IS NULL AND DM.DEAL_STAGE_ID='FVI' AND DM.REC_STATUS='A'"); 
		bufInsSql.append(" INNER join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID Where ISNULL(d.REC_STATUS,'P') <>'X' "); 
		bufInsSqlTempCount.append(" INNER join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID Where ISNULL(d.REC_STATUS,'P') <>'X' "); 
		bufInsSql.append(" and d.LOAN_ID not in(select ISNULL(LOAN_ID,0) from cr_deal_verification_dtl where STAGE='LIM' AND  REC_STATUS in ('C')) AND ");
		bufInsSql.append("((ISNULL(dv.REC_STATUS,'X')='P' AND dv.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' ) OR (ISNULL(dv.REC_STATUS,'X')<>'P' AND 'a'='a')) AND ISNULL(d.DISBURSAL_STATUS,'N')<>'F' AND D.LOAN_BRANCH='"+vo.getBranchId()+"'"); 
		bufInsSqlTempCount.append(" and d.LOAN_ID not in(select ISNULL(LOAN_ID,0) from cr_deal_verification_dtl where STAGE='LIM' AND  REC_STATUS in ('C')) AND  ");
		bufInsSqlTempCount.append("((ISNULL(dv.REC_STATUS,'X')='P' AND dv.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' ) OR (ISNULL(dv.REC_STATUS,'X')<>'P' AND 'a'='a')) AND ISNULL(d.DISBURSAL_STATUS,'N')<>'F' AND D.LOAN_BRANCH='"+vo.getBranchId()+"'");
		 

		
		if (!((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReportingToUserId())).trim().equalsIgnoreCase(""))))
		{
			bufInsSql.append(" AND d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' ");	
			bufInsSqlTempCount.append(" AND d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"'");
			appendSQL = true;
		}
		
		if((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")) &&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))&&(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate()).trim()).equalsIgnoreCase("")))
		{
			
	   	  bufInsSqlTempCount.append(") as b");
		}
		
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()).equalsIgnoreCase(""))) &&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate()).trim()).equalsIgnoreCase(""))))
		{
	   	  bufInsSql.append("WHERE d.LOAN_ID='"+loanId+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND ");
	   	  bufInsSql.append(dbo);
	   	  bufInsSql.append("date(dv.MAKER_DATE) =");
	   	  bufInsSql.append(dbo);
	   	  bufInsSql.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+"','"+dateFormat+"')");
	   	  bufInsSqlTempCount.append("WHERE d.LOAN_ID='"+loanId+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND ");
	   	  bufInsSqlTempCount.append(dbo);
	   	  bufInsSqlTempCount.append("date(dv.MAKER_DATE) =");
	   	  bufInsSqlTempCount.append(dbo);
	   	  bufInsSqlTempCount.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+"','"+dateFormat+"')) as b");
		}
		
		if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getInitiationDate().equalsIgnoreCase("")))){
			appendSQL=true;
		}
		
		 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim().equalsIgnoreCase("")))) {
	        bufInsSql.append(" AND d.LOAN_ID='"+loanId+"' ");
	        bufInsSqlTempCount.append(" AND d.LOAN_ID='"+loanId+"' ");
	   	 	appendSQL=true;
	   	  
	     }
		 
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate())).trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND ");
			bufInsSql.append(dbo);
			bufInsSql.append("date(dv.MAKER_DATE) =");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+"','"+dateFormat+"') ");
			bufInsSqlTempCount.append(" AND ");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("date(dv.MAKER_DATE) =");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+"','"+dateFormat+"') ");
			appendSQL=true;
	     }
		
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' ");
			bufInsSqlTempCount.append(" AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%'");
	   	  	appendSQL=true;
	     }
		
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
		   	  bufInsSqlTempCount.append(" AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'");
		   	  appendSQL=true;
		     }
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
		   	 bufInsSqlTempCount.append(" AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"'");
		   	  appendSQL=true;
		     }
		if((!(vo.getLbxDealNo().equalsIgnoreCase("")))||(!(vo.getCustomerName().equalsIgnoreCase("")))||(!(vo.getLbxProductID().equalsIgnoreCase("")))||(!(vo.getLbxscheme().equalsIgnoreCase("")))||(!(vo.getInitiationDate().equalsIgnoreCase("")))){
			bufInsSqlTempCount.append(") as b");
			appendSQL=true;
		}	
		 count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		logger.info("  initiatedVerificationDealDetail() bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
//		if((dealNo.trim()==null && appDate.trim()==null && custName.trim()==null && productId.trim()==null && schemeId.trim()==null) || (dealNo.trim().equalsIgnoreCase("") && appDate.trim().equalsIgnoreCase("") && custName.trim().equalsIgnoreCase("") && productId.trim().equalsIgnoreCase("") && schemeId.trim().equalsIgnoreCase("")) || fieldVo.getCurrentPageLink()>1)
//		{
		
		logger.info("     current PAge Link no .................... "+fieldVo.getCurrentPageLink());
		if(fieldVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
			
		bufInsSql.append(" ORDER BY d.LOAN_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
		logger.info("Search initiatedVerificationDealDetail query for SQL SERVER : " + bufInsSql.toString());

		//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));	
//		}
		logger.info("query : "+bufInsSql);
	     header = ConnectionDAO.sqlSelect(bufInsSql.toString());

					
		for(int i=0;i<header.size();i++){
			logger.info("header: "+header.size());
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				
				fieldVo = new FieldVerificationVo();
				
				fieldVo.setLbxLoanNoHID((CommonFunction.checkNull(header1.get(0))).trim());
				fieldVo.setLoanNo("<a href=\"#\" onclick=\"openLinkVerificationInitAtCm('"+((CommonFunction.checkNull(header1.get(0))).trim())+"');\" >" +
						((CommonFunction.checkNull(header1.get(1))).trim())+"</a>");
				fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(2))).trim());
				fieldVo.setProduct((CommonFunction.checkNull(header1.get(3))).trim());
				fieldVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
				//fieldVo.setStatus((CommonFunction.checkNull(header1.get(5))).trim());
				//fieldVo.setDealDate((CommonFunction.checkNull(header1.get(7))).trim());
				//fieldVo.setRmName((CommonFunction.checkNull(header1.get(6))).trim());
				//fieldVo.setReportingToUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(8))).trim());
				fieldVo.setTotalRecordSize(count);
				list.add(fieldVo);
			}
		}
		

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	logger.info("initiatedVerificationDealDetail : "+list.size());
	
	return list;
}
public ArrayList getVerificationListAtCM(String loanId) {
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getVerificationListAtCM()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append(" SELECT DISTINCT v.VERIFICATION_MAPPING_ID,v.VERIFICATION_TYPE,v.VERIFICATION_SUB_TYPE,v.ENTITY_TYPE,v.ENTITY_SUB_TYPE,v.ADDRESS_TYPE,v.APPRAISER_TYPE,M.address_id,C.CUSTOMER_NAME,G.DESCRIPTION  ");
		bufInsSql.append(" FROM cr_deal_verification_m v  ");
		bufInsSql.append(" INNER JOIN cr_loan_customer_role R ON LOAN_ID='"+loanId+"' AND R.LOAN_CUSTOMER_ROLE_TYPE=V.ENTITY_TYPE  ");
		bufInsSql.append(" INNER JOIN gcd_customer_m C ON R.GCD_ID=C.CUSTOMER_ID  ");
		bufInsSql.append(" INNER JOIN com_address_m m ON m.BPID=C.CUSTOMER_ID and m.BPTYPE='CS' AND M.ADDRESS_TYPE=V.ADDRESS_TYPE   ");
		bufInsSql.append(" INNER JOIN generic_master G ON G.GENERIC_KEY='ADDRESS_TYPE' AND G.VALUE=M.ADDRESS_TYPE ");
		bufInsSql.append(" where V.ENTITY_SUB_TYPE<>'BUSINESS' AND v.REC_STATUS='A' ");
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT v1.VERIFICATION_MAPPING_ID,v1.VERIFICATION_TYPE,v1.VERIFICATION_SUB_TYPE,v1.ENTITY_TYPE,v1.ENTITY_SUB_TYPE,v1.ADDRESS_TYPE,v1.APPRAISER_TYPE,M1.address_id,C1.CUSTOMER_NAME,G1.DESCRIPTION    ");
		bufInsSql.append(" FROM cr_deal_verification_m v1    ");
		bufInsSql.append(" INNER JOIN cr_loan_customer_role R1 ON R1.LOAN_ID='"+loanId+"' AND R1.LOAN_CUSTOMER_ROLE_TYPE=V1.ENTITY_TYPE  AND R1.LOAN_CUSTOMER_ROLE_TYPE='PRAPPL'  ");
		bufInsSql.append(" INNER JOIN gcd_customer_m C1 ON R1.GCD_ID=C1.CUSTOMER_ID AND (C1.CUSTOMER_TYPE='C' OR (C1.CUSTOMER_TYPE='I' AND C1.CUSTOMER_CONSTITUTION='SELFEMP'))  ");
		bufInsSql.append(" INNER JOIN com_address_m m1 ON m1.BPID=C1.CUSTOMER_ID and m1.BPTYPE='CS'   ");
		bufInsSql.append(" INNER JOIN generic_master G1 ON G1.GENERIC_KEY='ADDRESS_TYPE' AND G1.VALUE=M1.ADDRESS_TYPE ");
		bufInsSql.append(" where V1.ENTITY_SUB_TYPE='BUSINESS' AND M1.ADDRESS_TYPE=V1.ADDRESS_TYPE AND v1.REC_STATUS='A'  ");
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT v.VERIFICATION_MAPPING_ID,v.VERIFICATION_TYPE,v.VERIFICATION_SUB_TYPE,v.ENTITY_TYPE,v.ENTITY_SUB_TYPE,v.ADDRESS_TYPE,v.APPRAISER_TYPE,B.ASSETID,m.ASSET_COLLATERAL_DESC,''  ");
		bufInsSql.append(" FROM cr_deal_verification_m v  ");
		bufInsSql.append(" INNER JOIN cr_loan_collateral_m B ON LOAN_ID='"+loanId+"'  ");
		bufInsSql.append(" INNER JOIN cr_asset_collateral_m m ON m.ASSET_ID=b.ASSETID and V.ENTITY_TYPE=M.ASSET_COLLATERAL_CLASS and m.ASSET_TYPE=v.ENTITY_SUB_TYPE ");
		bufInsSql.append(" where v.REC_STATUS='A' ");
		/*bufInsSql.append(" UNION  ");
		bufInsSql.append(" SELECT DISTINCT  v.VERIFICATION_MAPPING_ID,v.VERIFICATION_TYPE,v.VERIFICATION_SUB_TYPE,v.ENTITY_TYPE,v.ENTITY_SUB_TYPE,v.ADDRESS_TYPE,v.APPRAISER_TYPE,B.DEAL_BUYER_SUPPLIER_ID,B.DEAL_BUYER_SUPPLIER_NAME,'' ");
		bufInsSql.append(" FROM cr_deal_verification_m v  ");
		bufInsSql.append(" INNER JOIN cr_deal_buyer_supplier_dtl B ON  IF(B.DEAL_BUYER_SUPPLIER_TYPE='B','BUYER','SUPPLIER')=V.ENTITY_TYPE  AND DEAL_ID='"+dealId+"'  ");
		bufInsSql.append(" where v.REC_STATUS='A'");
		bufInsSql.append(" UNION  ");
		bufInsSql.append(" SELECT DISTINCT v1.VERIFICATION_MAPPING_ID,v1.VERIFICATION_TYPE,v1.VERIFICATION_SUB_TYPE,v1.ENTITY_TYPE,v1.ENTITY_SUB_TYPE,v1.ADDRESS_TYPE,v1.APPRAISER_TYPE,'','','' ");
		bufInsSql.append(" FROM cr_deal_verification_m v1 ");
		bufInsSql.append(" INNER JOIN cr_deal_buyer_supplier_dtl B1 ON DEAL_ID='"+loanId+"'  ");
		bufInsSql.append(" where v1.ENTITY_TYPE='MARKET' and v1.REC_STATUS='A' ");*/
		
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT v2.VERIFICATION_MAPPING_ID,v2.VERIFICATION_TYPE,v2.VERIFICATION_SUB_TYPE,v2.ENTITY_TYPE,v2.ENTITY_SUB_TYPE,v2.ADDRESS_TYPE,v2.APPRAISER_TYPE,C2.CUSTOMER_id,C2.CUSTOMER_NAME,''  ");
		bufInsSql.append(" FROM cr_deal_verification_m v2  ");
		bufInsSql.append(" INNER JOIN cr_loan_customer_role R2 ON R2.LOAN_ID='"+loanId+"' AND R2.LOAN_CUSTOMER_ROLE_TYPE IN ('PRAPPL','COAPPL')  AND R2.LOAN_CUSTOMER_TYPE='I' ");
		bufInsSql.append(" INNER JOIN gcd_customer_m C2 ON R2.GCD_ID=C2.CUSTOMER_ID  AND IIF(C2.CUSTOMER_CONSTITUTION='SALARIED','INCOME','N')=V2.ENTITY_TYPE ");
		bufInsSql.append(" WHERE v2.REC_STATUS='A' AND V2.ENTITY_TYPE ='INCOME' ");
		
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT  V3.VERIFICATION_MAPPING_ID,V3.VERIFICATION_TYPE,V3.VERIFICATION_SUB_TYPE,V3.ENTITY_TYPE,V3.ENTITY_SUB_TYPE,V3.ADDRESS_TYPE,V3.APPRAISER_TYPE,B.TXN_DOC_ID,m.DOC_DESC,''    ");
		bufInsSql.append(" FROM cr_deal_verification_m V3  ");
		bufInsSql.append(" INNER JOIN CR_DOCUMENT_DTL B ON B.TXNID='"+loanId+"'  AND B.TXN_TYPE='LIM'  AND B.DOC_STATUS='R' ");
		bufInsSql.append(" INNER JOIN CR_DOCUMENT_M m ON m.DOC_ID=b.DOC_ID ");
		bufInsSql.append(" where  V3.REC_STATUS='A' AND V3.VERIFICATION_TYPE='DOCUMENT VERIFICATION' AND V3.ENTITY_SUB_TYPE=B.DOC_TYPE ");
		
		bufInsSql.append(" UNION  ALL  ");
		bufInsSql.append(" SELECT DISTINCT V4.VERIFICATION_MAPPING_ID,V4.VERIFICATION_TYPE,V4.VERIFICATION_SUB_TYPE,V4.ENTITY_TYPE,V4.ENTITY_SUB_TYPE,G.DESCRIPTION,V4.APPRAISER_TYPE,C.CUSTOMER_ID,C.CUSTOMER_NAME,'' ");  
		bufInsSql.append(" FROM cr_deal_verification_m V4 " );
		bufInsSql.append(" INNER JOIN cr_loan_customer_role R ON LOAN_ID='"+loanId+"' AND R.LOAN_CUSTOMER_ROLE_TYPE=V4.ENTITY_TYPE ");  
		bufInsSql.append(" INNER JOIN gcd_customer_m C ON R.GCD_ID=C.CUSTOMER_ID   AND C.CUSTOMER_CONSTITUTION=V4.ENTITY_SUB_TYPE ");
		bufInsSql.append(" INNER JOIN generic_master G ON G.GENERIC_KEY='CUST_CONSTITUTION' AND G.VALUE=V4.ENTITY_SUB_TYPE ");
		bufInsSql.append(" where V4.REC_STATUS='A' ");
		
		logger.info("IN getVerificationListAtCM()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getVerificationListAtCM " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
				
				deptMVO.setVerificationMappingId(CommonFunction.checkNull(data.get(0)));
				deptMVO.setVerificationType(CommonFunction.checkNull(data.get(1)));			
				deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(2)));
				
				if(CommonFunction.checkNull(data.get(3)).equalsIgnoreCase("PRAPPL"))
				{
					deptMVO.setEntityType("APPLICANT");
				}
				else if(CommonFunction.checkNull(data.get(3)).equalsIgnoreCase("COAPPL"))
				{
					deptMVO.setEntityType("COAPPLICANT");
				}
				else
				{
					deptMVO.setEntityType(CommonFunction.checkNull(data.get(3)));
				}
				
				deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(4)).toString());
				
				
				if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("PRAPPL"))
				{
					deptMVO.setEntitySubTypeDesc("APPLICANT");
				}
				else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("COAPPL"))
				{
					deptMVO.setEntitySubTypeDesc("COAPPLICANT");
				}
				else if(CommonFunction.checkNull(data.get(4)).equalsIgnoreCase("APPL"))
				{
					deptMVO.setEntitySubTypeDesc("APPLICATION");
				}
				else 
				{
					deptMVO.setEntitySubTypeDesc(CommonFunction.checkNull(data.get(4)));
				}
				if(CommonFunction.checkNull(data.get(1)).equalsIgnoreCase("CONTACT VERIFICATION"))
				{
				  deptMVO.setAddressType(CommonFunction.checkNull(data.get(5)));
				}
				else if(CommonFunction.checkNull(data.get(1)).equalsIgnoreCase("CONSTITUTION VERIFICATION"))
				{
					deptMVO.setEntitySubTypeDesc(CommonFunction.checkNull(data.get(5)));
				}
				
				
			    deptMVO.setAppraiserType(CommonFunction.checkNull(data.get(6)));
				deptMVO.setEntId(CommonFunction.checkNull(data.get(7)));
				deptMVO.setEntityDesc(CommonFunction.checkNull(data.get(8)));
				deptMVO.setAddressTypeDesc(CommonFunction.checkNull(data.get(9)));
				detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public ArrayList showVerificationListAtCM(String loanId) {
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In showVerificationListAtCM()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT DISTINCT VERIFICATION_ID,VERIFICATION_TYPE,APPRAISER_TYPE,U.USER_NAME,VERIFICATION_SUBTYPE,CASE WHEN VERIFICATION_ACTION='I' THEN 'INITIATED' ELSE 'WAIVED' END AS VERIFICATION_ACTION ,ENTITY_ID,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,AG.AGENCY_NAME,G.DESCRIPTION,CASE WHEN V.REC_STATUS = 'F' THEN 'INITIATED' WHEN V.REC_STATUS = 'R' THEN 'CAPTURED' WHEN V.REC_STATUS = 'C' THEN 'COMPLETED' END AS REC_STATUS,G1.DESCRIPTION FROM cr_deal_verification_dtl V ");
		bufInsSql.append(" LEFT JOIN sec_user_m U ON  U.USER_ID =V.INTERNAL_APPRAISER  ");
        bufInsSql.append(" LEFT JOIN generic_master G ON G.GENERIC_KEY='ADDRESS_TYPE' AND  G.VALUE=V.ADDRESS_TYPE  ");
        bufInsSql.append(" LEFT JOIN generic_master G1 ON G1.GENERIC_KEY='CUST_CONSTITUTION' AND  G1.VALUE=V.ENTITY_SUB_TYPE  ");
		bufInsSql.append(" left join com_agency_m ag on V.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE ");
		bufInsSql.append(" WHERE VERIFICATION_ACTION IN ('I','W') AND STAGE='LIM' AND LOAN_ID= '"+loanId+"' ");
		
		logger.info("IN showVerificationListAtCM()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("showVerificationListAtCM " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
				
				deptMVO.setVerificationMappingId(CommonFunction.checkNull(data.get(0)));
				
				deptMVO.setVerificationType(CommonFunction.checkNull(data.get(1)));
				
				 deptMVO.setAppraiserType(CommonFunction.checkNull(data.get(2)));
				
				 deptMVO.setInternalAppraiser(CommonFunction.checkNull(data.get(3)));
				 deptMVO.setExternalAppraiser(CommonFunction.checkNull(data.get(3)));
				 			 
				 deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(4)));
				 
				 deptMVO.setActionValue(CommonFunction.checkNull(data.get(5)));
				 
				 deptMVO.setEntId(CommonFunction.checkNull(data.get(6)));
				 
				 if(CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("PRAPPL"))
				 {
					 deptMVO.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("COAPPL"))
				 {
					 deptMVO.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 deptMVO.setEntityType(CommonFunction.checkNull(data.get(7)));
				 }
				 if(CommonFunction.checkNull(data.get(8)).equalsIgnoreCase("PRAPPL"))
					{
						deptMVO.setEntitySubType("APPLICANT");
					}
					else if(CommonFunction.checkNull(data.get(8)).equalsIgnoreCase("COAPPL"))
					{
						deptMVO.setEntitySubType("COAPPLICANT");
					}
					else if(CommonFunction.checkNull(data.get(8)).equalsIgnoreCase("APPL"))
					{
						deptMVO.setEntitySubType("APPLICATION");
					}
					else 
					{
						deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(8)));
					}
							
				 deptMVO.setEntityDesc(CommonFunction.checkNull(data.get(9)));
				 deptMVO.setAgencyName(CommonFunction.checkNull(data.get(10)));
                 deptMVO.setAddressTypeDesc(CommonFunction.checkNull(data.get(11)));
                 deptMVO.setStatus(CommonFunction.checkNull(data.get(12)));
                 if(CommonFunction.checkNull(data.get(1)).equalsIgnoreCase("CONSTITUTION VERIFICATION"))
 				 {
 					deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(13)));
 				 }
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public boolean insertFieldVerInitiationAtCM(FieldVerificationVo fieldVo){
	
	
	boolean status=false;
	ArrayList qryList = new ArrayList();
			
	StringBuffer bufInsSql = null;
	PrepStmtObject insertPrepStmtObject = null;
	String[] action=fieldVo.getVerificationAction();
	String[] entityId=fieldVo.getEntityId();
	String[] verType=fieldVo.getVerType();
	String[] verSubType=fieldVo.getVerSubType();
	
	String[] entType=fieldVo.getEntType();
	String[] entSubType=fieldVo.getEntSubType();
	
	String[] externalAppUserId=fieldVo.getExternalAppUserId();
	String[] internalAppUserId=fieldVo.getInternalAppUserId();
	
	String[] appraiser=fieldVo.getAppraiser();
	String []entityDescription=fieldVo.getEntityDescription();
	String lbxAddressType[]=fieldVo.getLbxAddressType();
	String deleteQuery="";
	try {

		logger.info("entityId.length: "+entityId.length);
		for (int i = 0; i < entityId.length; i++) {
			
					
			if((CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("W")))
			{
				if(entSubType[i].equalsIgnoreCase("BUSINESS")||entSubType[i].equalsIgnoreCase("MARKET"))
				{
					deleteQuery="DELETE FROM cr_deal_verification_dtl WHERE REC_STATUS='F' AND ENTITY_SUB_TYPE='"+entSubType[i]+"' AND STAGE='LIM' AND LOAN_ID="+fieldVo.getLbxLoanNoHID();
				}
				else
				{
					deleteQuery="DELETE FROM cr_deal_verification_dtl WHERE REC_STATUS='F' AND ENTITY_ID='"+entityId[i]+"' AND STAGE='LIM' AND LOAN_ID="+fieldVo.getLbxLoanNoHID();
				}
				logger.info("deleteQuery of Deletion is ="+deleteQuery);
				ArrayList deleteArl= new ArrayList();
				deleteArl.add(deleteQuery);
				status=ConnectionDAO.sqlInsUpdDelete(deleteArl);
				deleteArl=null;
				logger.info("Status of Deletion is ="+status);
			}
			if(!(CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("P"))){
				
			 insertPrepStmtObject = new PrepStmtObject();
			 bufInsSql = new StringBuffer();
			 logger.info("In insert Field Verification Initiation");
			
			bufInsSql.append("insert into cr_deal_verification_dtl(DEAL_ID,VERIFICATION_TYPE,VERIFICATION_SUBTYPE,APPRAISER_TYPE,INTERNAL_APPRAISER,");
			bufInsSql.append(" EXTERNAL_APPRAISER,VERIFICATION_ACTION,ENTITY_ID,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,ADDRESS_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,LOAN_ID,STAGE,DOC_CHILD_IDS_CM)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");//DEAL_ID
			bufInsSql.append(" ?,");//V+ERIFICATION_TYPE
			bufInsSql.append(" ?,");//VERIFICATION_SUBTYPE
			bufInsSql.append(" ?,");//APPRAISER_TYPE
			bufInsSql.append(" ?,");//INTERNAL_APPRAISER
			bufInsSql.append(" ?,");//EXTERNAL_APPRAISER
			
			bufInsSql.append(" ?,");//VERIFICATION_ACTION
			bufInsSql.append(" ?,");//ENTITY_ID
			
			bufInsSql.append(" ?,");//ENTITY_TYPE
			bufInsSql.append(" ?,");//ENTITY_SUB_TYPE
			bufInsSql.append(" ?,");//ENTITY_DESC
			bufInsSql.append(" ?,");//ADDRESS_TYPE
			bufInsSql.append(" ?,");//REC_STATUS
			bufInsSql.append(" ?,");//MAKER_ID
			//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");//MAKER_DATE
			// saurabh space starts
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			// saurabh space ends
			bufInsSql.append(" ?,");//AUTHOR_ID
			//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");//AUTHOR_DATE
			// saurabh space starts
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			// saurabh space ends
			bufInsSql.append(" ?,");//LOAN_ID
			bufInsSql.append(" 'LIM',");//STAGE
			bufInsSql.append(" ?) ");//DOC_CHILD_IDS_CM
		
			if (CommonFunction.checkNull(fieldVo.getDealId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fieldVo.getDealId()).trim());

			
			if (CommonFunction.checkNull(verType[i]).trim().equalsIgnoreCase("") )
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((verType[i]).trim());
			
			
			if (CommonFunction.checkNull(verSubType[i]).trim().equalsIgnoreCase("") )
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((verSubType[i]).trim());
			
			logger.info("appraiser size : "+appraiser[i]);	
			logger.info("action size : "+action[i]);	
			if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
			{	
				if (CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("") )
					insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((appraiser[i]).trim());
			}else
			{
				insertPrepStmtObject.addNull();
			}	
				logger.info("internalAppUserId size : "+internalAppUserId.length);	
				if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
				{
					if(internalAppUserId.length>0 && CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("INTERNAL"))
					{
						if (CommonFunction.checkNull(internalAppUserId[i]).trim().equalsIgnoreCase("") )
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((internalAppUserId[i]).trim());
					}
					else
					{
						insertPrepStmtObject.addNull();
					}
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				logger.info("externalAppUserId size  "+externalAppUserId.length);
				if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
				{
					if(externalAppUserId.length>0 && CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("EXTERNAL"))
					{
						if (CommonFunction.checkNull(externalAppUserId[i]).trim().equalsIgnoreCase("") )
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((externalAppUserId[i]).trim());
						}
					else
					{
						insertPrepStmtObject.addNull();
					}
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
			
					
			if (CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(action[i].trim());
			
			if (CommonFunction.checkNull(entityId[i]).trim().equalsIgnoreCase("") )
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((entityId[i]).trim());
			
											
			if (CommonFunction.checkNull(entType[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			{
				if(entType[i].trim().equalsIgnoreCase("APPLICANT"))
				{
					insertPrepStmtObject.addString("PRAPPL");
				}
				else if(entType[i].trim().equalsIgnoreCase("COAPPLICANT"))
				{
					insertPrepStmtObject.addString("COAPPL");
				}
				else
				{
					insertPrepStmtObject.addString(entType[i].trim());
				}
			}
				
			
			if (CommonFunction.checkNull(entSubType[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(entSubType[i].trim());
			
			if(CommonFunction.checkNull(entityDescription[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(entityDescription[i].trim());
			
			if(CommonFunction.checkNull(lbxAddressType[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(lbxAddressType[i].trim());
			
			
			insertPrepStmtObject.addString("F");

			if (CommonFunction.checkNull(fieldVo.getMakerId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerId().trim());

			if (CommonFunction.checkNull(fieldVo.getMakerDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());

			if (CommonFunction.checkNull(fieldVo.getMakerId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerId().trim());

			if (CommonFunction.checkNull(fieldVo.getMakerDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());
		
			if (CommonFunction.checkNull(fieldVo.getLbxLoanNoHID()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getLbxLoanNoHID().trim());
			
			if (CommonFunction.checkNull(verType[i]).trim().equalsIgnoreCase("DOCUMENT VERIFICATION"))
			{
				String docQry="select DOC_CHILD_IDS  from cr_document_dtl where TXN_DOC_ID= '"+CommonFunction.checkNull(entityId[i]).trim()+"' AND IFNULL(DOC_CHILD_IDS,'')<>'' AND TXN_TYPE='LIM' AND TXNID='"+CommonFunction.checkNull(fieldVo.getLbxLoanNoHID()).trim()+"' ";
				String docId=ConnectionDAO.singleReturn(docQry);
				insertPrepStmtObject.addString(CommonFunction.checkNull(docId).trim());					
			}
			else
			{
				insertPrepStmtObject.addNull();
			}
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			logger.info("IN insertfieldVerification() insert query1 ### "+ insertPrepStmtObject.printQuery());
			
		  }
			
		}
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("status....."+status);
			logger.info("IN insertfieldVerification() insert query1 ### "+ insertPrepStmtObject.printQuery());

	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}

public boolean authorAllWavedVerifInitAtCM(FieldVerificationVo vo) {
	
	
	boolean status = false;
	
	logger.info("In authorAllWavedVerifInitAtCM.....................................FieldVerificationDAOIMpl ");
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	
	try {
		
	
			    StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_deal_verification_dtl SET VERIFICATION_STATUS=?, VERIFICATION_REMARKS=?,AUTHOR_REMARKS=?,"+
						" rec_status='C',AUTHOR_ID=?,AUTHOR_DATE=");
				//bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
				// saurabh space starts
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				// saurabh space ends
				bufInsSql.append("WHERE STAGE='LIM' AND LOAN_ID=?");
				
				if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDecison()).trim());
				
				if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
				
				if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
				
				
				if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerId()).trim());
			  
			    
			    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getMakerDate()).trim());
			    
			    if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDealId()).trim());

					   
				 insertPrepStmtObject.setSql(bufInsSql.toString());
				
				
					
				  logger.info("IN authorAllWavedVerifInitAtCM() update query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				
					
				    status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		 
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}

public ArrayList<FieldVerificationVo> getDefaultCaptureDataAtCM(FieldVerificationVo vo) 
{
	logger.info("In getDefaultCaptureDataAtCM() method of FieldVerificationDAOImpl");
	ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	logger.info("In getDefaultCaptureDataAtCM .......FieldVerificationDAOImpl");
	StringBuilder bufInsSql=new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
	String userId=StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId())).trim();
	try
	{
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+userId);
		String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+userId+"'";
		String userName=ConnectionDAO.singleReturn(userNameQ);
		logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		ArrayList header=null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		FieldVerificationVo fieldVo= (FieldVerificationVo) vo;
		boolean appendSQL=false;			
		bufInsSql.append(" select distinct cr_deal_verification_dtl.LOAN_ID,cr_loan_dtl.LOAN_NO,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,cr_deal_verification_dtl.ENTITY_ID,cr_deal_verification_dtl.ENTITY_DESC,cr_deal_verification_dtl.ENTITY_TYPE,cr_deal_verification_dtl.ENTITY_SUB_TYPE,gcd_customer_m.CUSTOMER_NAME,cr_deal_verification_dtl.VERIFICATION_ID from cr_deal_verification_dtl ");
		bufInsSql.append(" INNER join cr_loan_dtl on(cr_loan_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID AND cr_loan_dtl.LOAN_BRANCH='"+fieldVo.getBranchId()+"') ");
		//bufInsSql.append(" inner join cr_deal_loan_dtl on(cr_deal_loan_dtl.DEAL_ID=cr_deal_verification_dtl.DEAL_ID) ");
		bufInsSql.append(" inner join cr_product_m	on cr_loan_dtl.LOAN_PRODUCT=cr_product_m.PRODUCT_ID  ");
		bufInsSql.append(" inner join cr_scheme_m	on cr_loan_dtl.LOAN_SCHEME=cr_scheme_m.SCHEME_ID	 ");
		bufInsSql.append(" inner join gcd_customer_m  on gcd_customer_m.CUSTOMER_ID=cr_loan_dtl.LOAN_CUSTOMER_ID  	 ");
		bufInsSql.append(" where cr_deal_verification_dtl.REC_STATUS IN ('F') and cr_deal_verification_dtl.VERIFICATION_ACTION='I' ");
		bufInsSql.append(" and cr_deal_verification_dtl.VERIFICATION_ID in(select VERIFICATION_ID from cr_deal_verification_dtl where REC_STATUS IN ('F')  and VERIFICATION_ACTION='I' and APPRAISER_TYPE='INTERNAL' and INTERNAL_APPRAISER='"+userId+"'   ");
		bufInsSql.append(" union select distinct VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' and '"+userId+"' in(select distinct com_agency_user_mapping.USER_ID from cr_deal_verification_dtl INNER join com_agency_m on cr_deal_verification_dtl.external_appraiser=com_agency_m.AGENCY_CODE  INNER join com_agency_user_mapping on com_agency_m.AGENCY_CODE=com_agency_user_mapping.AGENCY_CODE  where  cr_deal_verification_dtl.REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' ) union select -1) ");
		
		bufInsSqlTempCount.append(" select count(1) from(select distinct cr_deal_verification_dtl.LOAN_ID,cr_loan_dtl.LOAN_NO,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,cr_deal_verification_dtl.ENTITY_ID,cr_deal_verification_dtl.ENTITY_DESC,cr_deal_verification_dtl.ENTITY_TYPE,cr_deal_verification_dtl.ENTITY_SUB_TYPE,gcd_customer_m.CUSTOMER_NAME,cr_deal_verification_dtl.VERIFICATION_ID from cr_deal_verification_dtl ");
		bufInsSqlTempCount.append(" INNER join cr_loan_dtl on(cr_loan_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID AND cr_loan_dtl.LOAN_BRANCH='"+fieldVo.getBranchId()+"' ) ");
		//bufInsSqlTempCount.append(" inner join cr_deal_loan_dtl on(cr_deal_loan_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID) ");
		bufInsSqlTempCount.append(" inner join cr_product_m	on cr_loan_dtl.LOAN_PRODUCT=cr_product_m.PRODUCT_ID  ");
		bufInsSqlTempCount.append(" inner join cr_scheme_m	on cr_loan_dtl.LOAN_SCHEME=cr_scheme_m.SCHEME_ID	 ");
		
		bufInsSqlTempCount.append(" inner join gcd_customer_m  on gcd_customer_m.CUSTOMER_ID=cr_loan_dtl.LOAN_CUSTOMER_ID  	 ");
		bufInsSqlTempCount.append(" where cr_deal_verification_dtl.REC_STATUS IN ('F') and cr_deal_verification_dtl.VERIFICATION_ACTION='I' ");
		bufInsSqlTempCount.append(" and cr_deal_verification_dtl.VERIFICATION_ID in(select VERIFICATION_ID from cr_deal_verification_dtl where REC_STATUS IN ('F')  and VERIFICATION_ACTION='I' and APPRAISER_TYPE='INTERNAL' and INTERNAL_APPRAISER='"+userId+"'   ");
		bufInsSqlTempCount.append(" union select distinct VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' and '"+userId+"' in(select distinct com_agency_user_mapping.USER_ID from cr_deal_verification_dtl INNER join com_agency_m on cr_deal_verification_dtl.external_appraiser=com_agency_m.AGENCY_CODE  INNER join com_agency_user_mapping on com_agency_m.AGENCY_CODE=com_agency_user_mapping.AGENCY_CODE  where  cr_deal_verification_dtl.REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' ) union select -1)) as b ");
		
		logger.info("query : "+bufInsSql.toString());
		logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
	    count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		logger.info("current PAge Link no .................... "+fieldVo.getCurrentPageLink());
		if(fieldVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
		}
		header = ConnectionDAO.sqlSelect(bufInsSql.toString());
		int size=header.size();		
		logger.info("header: "+header.size());
		for(int i=0;i<size;i++)
		{			
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				fieldVo = new FieldVerificationVo();					
				fieldVo.setLbxLoanNoHID((CommonFunction.checkNull(header1.get(0))).trim());
				fieldVo.setLoanNo("<a href=fieldVarificationAction.do?method=addFieldCaptureAtCM&loanId="+(CommonFunction.checkNull(header1.get(0))).trim()+"&entityId="+(CommonFunction.checkNull(header1.get(4))).trim()+"&verificationId="+(CommonFunction.checkNull(header1.get(9))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
				fieldVo.setProduct((CommonFunction.checkNull(header1.get(2))).trim());
				fieldVo.setScheme((CommonFunction.checkNull(header1.get(3))).trim());
				fieldVo.setEntityDesc((CommonFunction.checkNull(header1.get(5))).trim());
				if(CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase("PRAPPL"))
				 {
					fieldVo.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase("COAPPL"))
				 {
					 fieldVo.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 fieldVo.setEntityType(CommonFunction.checkNull(header1.get(6)));
				 }
				fieldVo.setEntitySubType((CommonFunction.checkNull(header1.get(7))).trim());
				fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(8))).trim());
				fieldVo.setUserName(userName);
				fieldVo.setTotalRecordSize(count);
				list.add(fieldVo);
			}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
	finally
	{
		bufInsSql=null;
		bufInsSqlTempCount=null;
	}
	return list;
}

public ArrayList getVerifListAtCM(String loanId, String entityId, String verificationId) {
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getVerifListAtCM()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT VERIFICATION_TYPE,VERIFICATION_SUBTYPE,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,(SELECT AGENCY_NAME FROM COM_AGENCY_M  WHERE AGENCY_CODE = EXTERNAL_APPRAISER) as EXTERNAL_APPRAISER FROM cr_deal_verification_dtl V ");
		bufInsSql.append(" WHERE STAGE='LIM' AND LOAN_ID= '"+loanId+"' and VERIFICATION_ID="+verificationId+" ");
		
		logger.info("IN getVerifListAtCM()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getVerifListAtCM " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
				
				
				
				deptMVO.setVerificationType(CommonFunction.checkNull(data.get(0)));
				
								 			 
				 deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(1)));
				 
				 
				
				 if(CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("PRAPPL"))
				 {
					 deptMVO.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("COAPPL"))
				 {
					 deptMVO.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 deptMVO.setEntityType(CommonFunction.checkNull(data.get(2)));
				 }
				 deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(3)));
							
				 deptMVO.setEntityDesc(CommonFunction.checkNull(data.get(4)));
				 deptMVO.setAgencyName(CommonFunction.checkNull(data.get(5)));
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public ArrayList getCommonListListAtCM(String loanId, String entityId,
		String verificationId) {
	
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	StringBuffer bufInsSql=null;
	FieldVerificationVo deptMVO=null;
	try {

		logger.info("In getCommonListListAtCM()...........Dao Impl");
		
		
		bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT FIELD_VERIFICATION_ID,APPRAISER_NAME,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(APPRAISAL_DATE,'"+dateFormat+"'),VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,FV_STATUS,FV_REMARKS,ASSET_LOCATION,ASSET_CONDITION,INVOICE_NO,INVOICE_COLLECTED FROM cr_field_verification_dtl ");
		bufInsSql.append(" WHERE STAGE='LIM' AND LOAN_ID= '"+loanId+"' and VERIFICATION_ID="+verificationId+" ");
		
		logger.info("IN getCommonListList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		int size=searchList.size();
		for (int i = 0; i < size; i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				 deptMVO = new FieldVerificationVo();
								
				 deptMVO.setFieldVerificationUniqueId(CommonFunction.checkNull(data.get(0)));
				 deptMVO.setAppraisalName(CommonFunction.checkNull(data.get(1)));	 			 
				 deptMVO.setAppraisalDate(CommonFunction.checkNull(data.get(2)));
		    	 deptMVO.setVerificationMode(CommonFunction.checkNull(data.get(3)));
				 deptMVO.setPersonToMeet(CommonFunction.checkNull(data.get(4)));
				 deptMVO.setRelationWithApplicant(CommonFunction.checkNull(data.get(5)));	
				 deptMVO.setPhone1(CommonFunction.checkNull(data.get(6)));	
				 deptMVO.setPhone2(CommonFunction.checkNull(data.get(7)));	
				 deptMVO.setEmail(CommonFunction.checkNull(data.get(8)));	
				 deptMVO.setCpvStatus(CommonFunction.checkNull(data.get(9)));
				 deptMVO.setRemarks(CommonFunction.checkNull(data.get(10)));
				 deptMVO.setAssetLocation(CommonFunction.checkNull(data.get(11)));
				 deptMVO.setAssetCondition(CommonFunction.checkNull(data.get(12)));
				 deptMVO.setInvoiceNumber(CommonFunction.checkNull(data.get(13)));
				 deptMVO.setInvoiceCollected(CommonFunction.checkNull(data.get(14)));
				 detailList.add(deptMVO);
			}
			data=null;

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		loanId=null;
		entityId=null;
		verificationId=null;
		bufInsSql=null;
		deptMVO=null;
		searchList=null;
	}

	return detailList;
}

public ArrayList getQuestListAtCM(String dealId, String entityId,String verificationId) {
	
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getQuestListAtCM()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT q.QUESTION_ID,q.QUESTION,q.QUESTION_SEQ_NO,tq.QUESTION_REMARKS,tq.VERIFICATION_METHOD,tq.QUESTION_VERIFICATION_ID,CASE WHEN q.VERIFICATION_REQD='Y' THEN 'YES' ELSE 'NO' END   FROM cr_deal_verification_question_m q ");
		bufInsSql.append(" inner join cr_deal_verification_dtl v on v.VERIFICATION_ID='"+verificationId+"' ");
		bufInsSql.append(" left join cr_deal_question_verification_dtl tq on tq.QUESTION_ID=q.QUESTION_ID AND TQ.VERIFICATION_ID=v.VERIFICATION_ID ");
		bufInsSql.append(" WHERE q.REC_STATUS='A' and q.VERIFICATION_TYPE=v.VERIFICATION_TYPE and q.VERIFICATION_SUB_TYPE=v.VERIFICATION_SUBTYPE AND q.ENTITY_TYPE=V.ENTITY_TYPE AND q.ENTITY_SUB_TYPE=V.ENTITY_SUB_TYPE AND ((q.VERIFICATION_TYPE='CONTACT VERIFICATION' AND q.ADDRESS_TYPE=v.ADDRESS_TYPE) OR (q.VERIFICATION_TYPE<>'CONTACT VERIFICATION' and 'a'='a')) ");
		
		logger.info("IN getQuestListAtCM()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getQuestListAtCM " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
				
				deptMVO.setQuestId(CommonFunction.checkNull(data.get(0)));
				
					 
				
				 deptMVO.setQuest(CommonFunction.checkNull(data.get(1)));
				 deptMVO.setQuestSeqNo(CommonFunction.checkNull(data.get(2)));
				 
				 deptMVO.setQuestRemarks(CommonFunction.checkNull(data.get(3)));
				 deptMVO.setQuestVerifMethod(CommonFunction.checkNull(data.get(4)));
				 deptMVO.setQuestVerifUniqueId(CommonFunction.checkNull(data.get(5)));
				 deptMVO.setVerifQuestRequired(CommonFunction.checkNull(data.get(6)));
				 
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}

public boolean insertQuestionDetailsAtCM(FieldVerificationVo vo) {
	
	
	qryList=new ArrayList();
	PrepStmtObject insertPrepStmtObject=null;
	
	String[] questionId=vo.getQuestionId();
	String[] questionSeqNo=vo.getQuestionSeqNo();
	String[] questionRemarks=vo.getQuestionRemarks();
	String[] verificationMethod=vo.getVerificationMethod();
	ArrayList deleteQryList=new ArrayList();
	boolean status=false;
	String deleteQuery="Delete from cr_deal_question_verification_dtl where VERIFICATION_ID='"+vo.getVerificationId()+"' and stage='LIM' and loan_id="+vo.getLbxLoanNoHID();
	logger.info("cr_deal_question_verification_dtl deleteQuery: "+deleteQuery);
	deleteQryList.add(deleteQuery);
	boolean deleteStatus;
	try {
		deleteStatus = ConnectionDAO.sqlInsUpdDelete(deleteQryList);
		logger.info("deleteStatus: "+deleteStatus);
	} catch (Exception e1) {
	
		e1.printStackTrace();
	} 
	
	logger.info("questionId: "+questionId);
	if(questionId.length>0)
	{
	for(int k=0;k<questionId.length;k++)
	{
	
	 StringBuilder query=new StringBuilder();
	 query.append("INSERT INTO cr_deal_question_verification_dtl(VERIFICATION_ID,VERIFICATION_CAPTURING_ID,QUESTION_ID,DEAL_ID,QUESTION_SEQ_NO,QUESTION_REMARKS,VERIFICATION_METHOD,REC_STATUS,MAKER_ID,MAKER_DATE,LOAN_ID,STAGE)");
     insertPrepStmtObject = new PrepStmtObject();
    
        
     query.append(" values ( ");
     query.append(" ?,");//VERIFICATION_ID
     query.append(" ?,");//VERIFICATION_CAPTURING_ID
     query.append(" ?,");//QUESTION_ID
     query.append(" ?,");//DEAL_ID
     query.append(" ?,");//QUESTION_SEQ_NO
     query.append(" ?,");//QUESTION_REMARKS  
     query.append(" ?,");//VERIFICATION_METHOD
     query.append(" ?,");//REC_STATUS
     query.append(" ?,");//MAKER_ID
     //query.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");//MAKER_DATE
  // saurabh space starts
     query.append(dbo);
     query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
     // saurabh space ends
     query.append(" ?,");//LOAN_ID
     query.append(" 'LIM' )");//stage
    
		
     if((CommonFunction.checkNull(vo.getVerificationId())).trim().equalsIgnoreCase(""))
 		insertPrepStmtObject.addNull();
 	else
 		insertPrepStmtObject.addString((vo.getVerificationId()).trim());
     
    if((CommonFunction.checkNull(vo.getFieldVerificationUniqueId())).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getFieldVerificationUniqueId()).trim());
    
    if(CommonFunction.checkNull(questionId[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(questionId[k]).trim());
    
    
    if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getDealId()).trim());
  
    if(CommonFunction.checkNull(questionSeqNo[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(questionSeqNo[k]).trim());
    
    
    if(CommonFunction.checkNull(questionRemarks[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(questionRemarks[k]).trim());
    
    
    if(CommonFunction.checkNull(verificationMethod[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(verificationMethod[k]).trim());
    
    insertPrepStmtObject.addString("P");
    
    
	if(CommonFunction.checkNull(vo.getUserId()).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getUserId()).trim());
	
	if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getMakerDate()).trim());
	
	if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getLbxLoanNoHID()).trim());
    
        insertPrepStmtObject.setSql(query.toString());
        
        logger.info("IN updateUnderwritingQueryData() update query1 ### "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
	}
	        
	
	try {
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
    //logger.info("In updateUnderwritingQueryData......................status= "+status);
    return status;
}

public int insertFieldVerCaptureAtCM(Object ob){
	int maxId=0;
	FieldVerificationVo fieldVo = (FieldVerificationVo) ob;
	ArrayList deleteQryList=new ArrayList();
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject=null;
	StringBuilder deleteQuery=new StringBuilder();
	StringBuilder maxIdQuery=new StringBuilder();
	StringBuffer bufInsSql = null;
	String id=null;
	String appType=null;
	logger.info("In insert Field Verification Capturing");
	deleteQuery.append("Delete from CR_FIELD_VERIFICATION_DTL where VERIFICATION_ID='"+fieldVo.getVerificationId()+"' and stage='LIM' and loan_id="+fieldVo.getLbxLoanNoHID()+"");
	logger.info("CR_FIELD_VERIFICATION_DTL deleteQuery: "+deleteQuery.toString());
	deleteQryList.add(deleteQuery.toString());
	boolean deleteStatus;
	try {
		deleteStatus = ConnectionDAO.sqlInsUpdDelete(deleteQryList);
		logger.info("deleteStatus: "+deleteStatus);
	} catch (Exception e1) {
	
		e1.printStackTrace();
	} 
	finally
	{
		deleteQryList.clear();
		deleteQryList=null;
		deleteQuery=null;
	}
	try {
			insertPrepStmtObject = new PrepStmtObject();			
			bufInsSql = new StringBuffer();
			bufInsSql.append(" INSERT INTO CR_FIELD_VERIFICATION_DTL(DEAL_ID,ENTITY_ID,ENTITY_TYPE,ENTITY_SUB_TYPE,VERIFICATION_ID,VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,APPRAISER_NAME,APPRAISAL_DATE,VERIFICATION_MODE,");
			bufInsSql.append(" PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,FV_STATUS,FV_REMARKS,MAKER_ID,MAKER_DATE,REC_STATUS,LOAN_ID,STAGE,ASSET_LOCATION,ASSET_CONDITION,INVOICE_NO,INVOICE_COLLECTED ) ");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");//DEAL_ID
			bufInsSql.append(" ?,");//ENTITY_ID
			bufInsSql.append(" ?,");//ENTITY_TYPE
			bufInsSql.append(" ?,");//ENTITY_SUB_TYPE
			bufInsSql.append(" ?,");//VERIFICATION_ID
			bufInsSql.append(" ?,");//VERIFICATION_TYPE  
			bufInsSql.append(" ?,");//VERIFICATION_SUB_TYPE
			bufInsSql.append(" ?,");//APPRAISER_NAME
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'" + dateFormatWithTime + "'),");//APPRAISAL_DATE
			bufInsSql.append(" ?,");//VERIFICATION_MODE 
			bufInsSql.append(" ?,");//PERSON_MET
			bufInsSql.append(" ?,");//RELATION
			bufInsSql.append(" ?,");//PHONE1
			bufInsSql.append(" ?,");//PHONE2
			bufInsSql.append(" ?,");//E_MAIL
			bufInsSql.append(" ?,");//FV_STATUS
			bufInsSql.append(" ?,");//FV_REMARKS
			bufInsSql.append(" ?,");//MAKER_ID
			//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");//MAKER_DATE
			// saurabh space starts
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			// saurabh space ends
			bufInsSql.append(" ?,");//REC_STATUS
			bufInsSql.append(" ?,");//LOAN_ID
			bufInsSql.append(" 'LIM',");//STAGE  
			bufInsSql.append(" ?,");//ASSET_LOCATION
			bufInsSql.append(" ?,");//ASSET_CONDITION
			bufInsSql.append(" ?,");//INVOICE_NO
			bufInsSql.append(" ?)");//INVOICE_COLLECTED
			
			
		
			if (CommonFunction.checkNull(fieldVo.getDealId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getDealId().trim());
			
		
			if (CommonFunction.checkNull(fieldVo.getEntId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getEntId().trim());
			
		
			if (CommonFunction.checkNull(fieldVo.getEntityType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			{
				appType=fieldVo.getEntityType();
				if(appType.equalsIgnoreCase("APPLICANT"))
					insertPrepStmtObject.addString("PRAPPL");
				else
					if(appType.equalsIgnoreCase("COAPPLICANT"))
						insertPrepStmtObject.addString("COAPPL");
					else
						insertPrepStmtObject.addString(appType);
			}
			
			if (CommonFunction.checkNull(fieldVo.getEntitySubType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getEntitySubType());
			
									
			if (CommonFunction.checkNull(fieldVo.getVerificationId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getVerificationId().trim());
			
			if (CommonFunction.checkNull(fieldVo.getVarificationType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			{
				insertPrepStmtObject.addString(fieldVo.getVarificationType().trim());
			}
			
			
		if (CommonFunction.checkNull(fieldVo.getVarificationSubType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			{
				insertPrepStmtObject.addString(fieldVo.getVarificationSubType().trim());
			}
						
			if (CommonFunction.checkNull(fieldVo.getAppraisalName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getAppraisalName().trim());
			
			if (CommonFunction.checkNull(fieldVo.getAppraisalDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getAppraisalDate().toUpperCase().trim());
			
			if (CommonFunction.checkNull(fieldVo.getVerificationMode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getVerificationMode().toUpperCase().trim());
			
			
			if (CommonFunction.checkNull(fieldVo.getPersonToMeet()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getPersonToMeet().toUpperCase().trim());

			if (CommonFunction.checkNull(fieldVo.getRelationWithApplicant()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getRelationWithApplicant().trim());
			
			if (CommonFunction.checkNull(fieldVo.getPhone1()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getPhone1());
			
			if (CommonFunction.checkNull(fieldVo.getPhone2()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getPhone2());
			
			if (CommonFunction.checkNull(fieldVo.getEmail()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getEmail().trim());
			
			
			if (CommonFunction.checkNull(fieldVo.getCpvStatus()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getCpvStatus().trim());
	
			
			if (CommonFunction.checkNull(fieldVo.getRemarks()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getRemarks().trim());
			
			if (CommonFunction.checkNull(fieldVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerId().trim());
			
			if (CommonFunction.checkNull(fieldVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerDate().toUpperCase().trim());
			insertPrepStmtObject.addString("P");
			
			if((CommonFunction.checkNull(fieldVo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fieldVo.getLbxLoanNoHID()).trim());		
			
			if (CommonFunction.checkNull(fieldVo.getAssetLocation()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getAssetLocation().trim());
			
			if (CommonFunction.checkNull(fieldVo.getAssetCondition()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getAssetCondition().trim());
			
			if (CommonFunction.checkNull(fieldVo.getInvoiceNumber()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getInvoiceNumber().trim());
			
			if (CommonFunction.checkNull(fieldVo.getInvoiceCollected()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getInvoiceCollected().trim());
		    
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN insertFieldVerCapture() insert query1 ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
	
			boolean status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
			maxIdQuery.append("Select distinct max(FIELD_VERIFICATION_ID) from cr_field_verification_dtl  WITH (ROWLOCK) ");
			    
			  
			   id=ConnectionDAO.singleReturn(maxIdQuery.toString());
			   maxId=Integer.parseInt(id.toString());
			   logger.info("print query....."+insertPrepStmtObject.printQuery());
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		ob=null;
		id=null;
		maxIdQuery=null;
		insertPrepStmtObject=null;
		qryList.clear();
		qryList=null;
		fieldVo=null;
		bufInsSql=null;
		appType=null;
	}

	return maxId;
}

public boolean updateQuestionDetailsAtCM(FieldVerificationVo vo) {
	
	qryList=new ArrayList();
	PrepStmtObject insertPrepStmtObject=null;
	boolean status=false;
	String[] questionVerificationUniqueId=vo.getQuestionVerificationUniqueId();
	String[] questionRemarks=vo.getQuestionRemarks();
	String[] verificationMethod=vo.getVerificationMethod();
	String checkQuery="Select count(*) from cr_deal_question_verification_dtl where stage='LIM' and loan_id="+vo.getLbxLoanNoHID();
	logger.info("checkQuery: "+checkQuery);
	String statusCount=ConnectionDAO.singleReturn(checkQuery);
	logger.info("statusCount: "+statusCount);
	logger.info("questionVerificationUniqueId: "+questionVerificationUniqueId.length);
	if(!CommonFunction.checkNull(statusCount).equalsIgnoreCase("0") && questionVerificationUniqueId.length>0)
	{
	for(int k=0;k<questionVerificationUniqueId.length;k++)
	{
	 insertPrepStmtObject=new PrepStmtObject();
	 StringBuilder query=new StringBuilder();
	 query.append(" UPDATE cr_deal_question_verification_dtl SET QUESTION_REMARKS=?,VERIFICATION_METHOD=?,REC_STATUS=?,");
	 query.append(" MAKER_ID=?,MAKER_DATE= ");
	 //query.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");	
	// saurabh space starts
	 query.append(dbo);
	 query.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
	 // saurabh space ends
	 query.append(" where QUESTION_VERIFICATION_ID='"+questionVerificationUniqueId[k]+"' ");			
	   
        
    if(CommonFunction.checkNull(questionRemarks[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(questionRemarks[k]).trim());
    
    
    if(CommonFunction.checkNull(verificationMethod[k]).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(CommonFunction.checkNull(verificationMethod[k]).trim());
    
    if (CommonFunction.checkNull(vo.getVerificationStatus()).equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString(vo.getVerificationStatus().trim());
       
    if(CommonFunction.checkNull(vo.getUserId()).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getUserId()).trim());
  
    
    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
		insertPrepStmtObject.addNull();
	else
		insertPrepStmtObject.addString((vo.getMakerDate()).trim());
    
		   
        insertPrepStmtObject.setSql(query.toString());
        
        logger.info("IN updateUnderwritingQueryData() update query1 ### "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
	}
	      
	
	try {
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
    //logger.info("In updateUnderwritingQueryData......................status= "+status);
    return status;
}

public boolean modifyFieldRemarksAtCM(Object ob, String loanId) {
	FieldVerificationVo vo = (FieldVerificationVo) ob;
	boolean status = false;
	
	logger.info("In modifyFieldRemarksAtCM........cr_deal_verification_dtl.............................FieldVerificationDAOIMpl ");
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
	try {
		
		
			    StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_deal_verification_dtl  SET VERIFICATION_STATUS=?, VERIFICATION_REMARKS=?,REC_STATUS=? WHERE stage='LIM' and loan_id=? and VERIFICATION_ID=? ");
				
				if((CommonFunction.checkNull(vo.getCpvStatus())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getCpvStatus()).trim());
				
				if((CommonFunction.checkNull(vo.getRemarks())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getRemarks()).trim());
				
				insertPrepStmtObject.addString("R");
				
				if(CommonFunction.checkNull(loanId).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(loanId).trim());
				
				if((CommonFunction.checkNull(vo.getVerificationId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getVerificationId()).trim());
				
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN modifyFieldRemarks(cr_deal_verification_dtl) UPDATE query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				 
		        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}
 
public ArrayList<FieldVerificationVo> searchDealForCaptureAtCM(FieldVerificationVo vo) {
	
	ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	StringBuilder bufInsSql=new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
	try
	{
		ArrayList header=null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		FieldVerificationVo fieldVo= (FieldVerificationVo) vo;

		logger.info("here userid+++++++++++++++++++++++ "+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(fieldVo.getUserId())).trim());
		 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"'";
		 			String userName=ConnectionDAO.singleReturn(userNameQ);
		 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
	
		boolean appendSQL=false;			  			

		String loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim());
		String appDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationDate())).trim());
		String date = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate())).trim());
		String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
		String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
		String schemeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());			
		logger.info("loanId................"+loanId);
		logger.info("appDate................"+date);
		logger.info("custName................"+custName);
		logger.info("productId................"+productId);
		logger.info("schemeId................"+schemeId);			
		bufInsSql.append("select distinct cr_deal_verification_dtl.LOAN_ID,cr_loan_dtl.LOAN_NO,");
		//bufInsSql.append(dbo);
		bufInsSql.append(" '' as date,gcd_customer_m.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,ENTITY_ID,VERIFICATION_ID ");
		bufInsSql.append(" from cr_deal_verification_dtl left outer join cr_loan_dtl on(cr_loan_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID  AND cr_loan_dtl.LOAN_BRANCH='"+fieldVo.getBranchId()+"') left outer join cr_product_m	on cr_loan_dtl.LOAN_PRODUCT=cr_product_m.PRODUCT_ID left outer join cr_scheme_m	on cr_loan_dtl.LOAN_SCHEME=cr_scheme_m.SCHEME_ID	left outer join gcd_customer_m  on gcd_customer_m.CUSTOMER_ID=cr_loan_dtl.LOAN_CUSTOMER_ID where 1=1");
		bufInsSqlTempCount.append(" select count(1) from (select distinct cr_deal_verification_dtl.LOAN_ID,cr_loan_dtl.LOAN_NO,");
		//bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append(" '' as date ,gcd_customer_m.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,ENTITY_ID,VERIFICATION_ID from cr_deal_verification_dtl left outer join cr_loan_dtl on(cr_loan_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID  AND cr_loan_dtl.LOAN_BRANCH='"+fieldVo.getBranchId()+"') left outer join cr_product_m	on cr_loan_dtl.LOAN_PRODUCT=cr_product_m.PRODUCT_ID left outer join cr_scheme_m	on cr_loan_dtl.LOAN_SCHEME=cr_scheme_m.SCHEME_ID	left outer join gcd_customer_m  on gcd_customer_m.CUSTOMER_ID=cr_loan_dtl.LOAN_CUSTOMER_ID where 1=1");
		if(!(loanId.trim().length()==0))
		{
			bufInsSql.append(" and cr_deal_verification_dtl.stage='LIM'  and cr_deal_verification_dtl.LOAN_ID="+loanId);
			bufInsSqlTempCount.append(" and cr_deal_verification_dtl.stage='LIM'   and cr_deal_verification_dtl.LOAN_ID="+loanId);
		}
		/*if(!(date.trim().length()==0))
		{
			bufInsSql.append(" and ");
			bufInsSql.append(dbo);
			bufInsSql.append("date(cr_deal_dtl.DEAL_DATE)=");
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE('"+date+"','"+dateFormat+"')");
			bufInsSqlTempCount.append(" and ");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("date(cr_deal_dtl.DEAL_DATE)=");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("STR_TO_DATE('"+date+"','"+dateFormat+"')");
		}*/
		if(!(custName.trim().length()==0))
		{
			bufInsSql.append(" and gcd_customer_m.CUSTOMER_NAME like'%"+custName+"%'");
			bufInsSqlTempCount.append(" and gcd_customer_m.CUSTOMER_NAME like'%"+custName+"%'");
		}
		if(!(productId.trim().length()==0))
		{
			bufInsSql.append(" and cr__loan_dtl.LOAN_PRODUCT='"+productId+"'");
			bufInsSqlTempCount.append(" and cr_loan_dtl.LOAN_PRODUCT='"+productId+"'");
		}
		if(!(schemeId.trim().length()==0))
		{
			bufInsSql.append(" and cr_loan_dtl.LOAN_SCHEME='"+schemeId+"'");
			bufInsSqlTempCount.append(" and cr_loan_dtl.LOAN_SCHEME='"+schemeId+"'");
		}
		bufInsSql.append(" and cr_deal_verification_dtl.REC_STATUS IN ('F') and cr_deal_verification_dtl.stage='LIM'   and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.VERIFICATION_ID in(select VERIFICATION_ID from cr_deal_verification_dtl where REC_STATUS IN ('F')  and VERIFICATION_ACTION='I' and APPRAISER_TYPE='INTERNAL' and INTERNAL_APPRAISER='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"' union select distinct VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' and '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"' in(select distinct com_agency_user_mapping.USER_ID from cr_deal_verification_dtl left outer join com_agency_m on cr_deal_verification_dtl.external_appraiser=com_agency_m.AGENCY_CODE  left outer join com_agency_user_mapping on com_agency_m.AGENCY_CODE=com_agency_user_mapping.AGENCY_CODE where  cr_deal_verification_dtl.REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' ) union select -1)");
		bufInsSqlTempCount.append(" and cr_deal_verification_dtl.REC_STATUS IN ('F') and cr_deal_verification_dtl.stage='LIM'   and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.VERIFICATION_ID in(select VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F')  and VERIFICATION_ACTION='I' and APPRAISER_TYPE='INTERNAL' and INTERNAL_APPRAISER='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"' union select distinct VERIFICATION_ID from cr_deal_verification_dtl where  REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' and '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(fieldVo.getUserId())).trim()+"' in(select distinct com_agency_user_mapping.USER_ID from cr_deal_verification_dtl left outer join com_agency_m on cr_deal_verification_dtl.external_appraiser=com_agency_m.AGENCY_CODE  left outer join com_agency_user_mapping on com_agency_m.AGENCY_CODE=com_agency_user_mapping.AGENCY_CODE where  cr_deal_verification_dtl.REC_STATUS IN ('F') and VERIFICATION_ACTION='I' and APPRAISER_TYPE='EXTERNAL' ) union select -1))as f");
		if(fieldVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		bufInsSql.append(" ORDER BY cr_deal_verification_dtl.LOAN_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
		logger.info("Search searchDealForCapture query for SQL SERVER : " + bufInsSql.toString());

		//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
	    logger.info("Search query ::::::::::::: "+bufInsSql);
	    logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
	    header = ConnectionDAO.sqlSelect(bufInsSql.toString());
	    count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
	    int size=header.size();			
		for(int i=0;i<size;i++)
		{    				
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				fieldVo = new FieldVerificationVo();
				fieldVo.setLoanNo("<a href=fieldVarificationAction.do?method=addFieldCaptureAtCM&loanId="+(CommonFunction.checkNull(header1.get(0))).trim()+"&entityId="+(CommonFunction.checkNull(header1.get(9))).trim()+"&verificationId="+(CommonFunction.checkNull(header1.get(10))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
				fieldVo.setInitiationDate((CommonFunction.checkNull(header1.get(2))).trim());
				fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(3))).trim());
				fieldVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
				fieldVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
				fieldVo.setUserName(userName);
				
				if(CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase("PRAPPL"))
				 {
					fieldVo.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(header1.get(6)).equalsIgnoreCase("COAPPL"))
				 {
					 fieldVo.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 fieldVo.setEntityType(CommonFunction.checkNull(header1.get(6)));
				 }
				fieldVo.setEntitySubType((CommonFunction.checkNull(header1.get(7))).trim());
				fieldVo.setEntityDesc((CommonFunction.checkNull(header1.get(8))).trim());
				fieldVo.setTotalRecordSize(count);
				list.add(fieldVo);
			}
		}
	}
	catch(Exception e)
	{e.printStackTrace();}
	finally
	{
		bufInsSql=null;
		bufInsSqlTempCount=null;
	}
			return list;
}

public ArrayList getDefaultCompletionDataAtCM(FieldVerificationVo vo) {

	ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	logger.info("In getDefaultCompletionDataAtCM .......FieldVerificationDAOImpl");
	try
	{
		ArrayList header=null;
        int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		FieldVerificationVo fieldVo= (FieldVerificationVo) vo;
		boolean appendSQL=false;			
		StringBuffer bufInsSql=new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		bufInsSql.append("select distinct cr_deal_verification_dtl.LOAN_ID,cr_loan_dtl.LOAN_NO,");
		//bufInsSql.append(dbo);
		bufInsSql.append("'' as date,gcd_customer_m.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,CASE WHEN ISNULL(cr_loan_dtl.DISBURSAL_STATUS,'N')='N' THEN 'NO DISBURSAL' WHEN ISNULL(cr_loan_dtl.DISBURSAL_STATUS,'N')='P' THEN 'PARTIAL DISBURSAL' WHEN ISNULL(cr_loan_dtl.DISBURSAL_STATUS,'N')='F' THEN 'FINAL DISBURSAL' END as STATUS ");
		bufInsSql.append(" from cr_deal_verification_dtl inner join cr_loan_dtl on(cr_loan_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID AND cr_loan_dtl.REC_STATUS<>'X' AND cr_loan_dtl.LOAN_BRANCH='"+fieldVo.getBranchId()+"') inner join cr_product_m on(cr_loan_dtl.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)inner join cr_scheme_m on(cr_loan_dtl.LOAN_SCHEME=cr_scheme_m.SCHEME_ID)inner join gcd_customer_m  on(gcd_customer_m.CUSTOMER_ID=cr_loan_dtl.LOAN_CUSTOMER_ID) inner join cr_field_verification_dtl on(cr_field_verification_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID AND cr_field_verification_dtl.STAGE=cr_deal_verification_dtl.STAGE) where cr_deal_verification_dtl.REC_STATUS='R' AND cr_deal_verification_dtl.STAGE='LIM' and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.LOAN_ID NOT in(select distinct ISNULL(LOAN_ID,0) from cr_deal_verification_dtl where REC_STATUS='F' and VERIFICATION_ACTION ='I' AND STAGE='LIM' ) and cr_deal_verification_dtl.LOAN_ID not in (select ISNULL(LOAN_ID,0) from cr_field_verification_dtl where MAKER_ID='"+vo.getUserId()+"')");
		bufInsSqlTempCount.append("select count(1) from (select distinct cr_deal_verification_dtl.LOAN_ID,cr_loan_dtl.LOAN_NO,");
		//bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("'' as date,gcd_customer_m.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC from cr_deal_verification_dtl inner join cr_loan_dtl on(cr_loan_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID AND cr_loan_dtl.REC_STATUS<>'X' AND cr_loan_dtl.LOAN_BRANCH='"+fieldVo.getBranchId()+"') inner join cr_product_m on(cr_loan_dtl.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)inner join cr_scheme_m on(cr_loan_dtl.LOAN_SCHEME=cr_scheme_m.SCHEME_ID)inner join gcd_customer_m  on(gcd_customer_m.CUSTOMER_ID=cr_loan_dtl.LOAN_CUSTOMER_ID) inner join cr_field_verification_dtl on(cr_field_verification_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID AND cr_field_verification_dtl.STAGE=cr_deal_verification_dtl.STAGE) where cr_deal_verification_dtl.REC_STATUS='R' AND cr_deal_verification_dtl.STAGE='LIM'  and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.LOAN_ID NOT in(select distinct ISNULL(LOAN_ID,0) from cr_deal_verification_dtl where REC_STATUS='F' and VERIFICATION_ACTION ='I' AND  STAGE='LIM') and cr_deal_verification_dtl.LOAN_ID not in (select ISNULL(LOAN_ID,0) from cr_field_verification_dtl where MAKER_ID='"+vo.getUserId()+"') )as f");
		logger.info("current PAge Link no .................... "+count);
		if(fieldVo.getCurrentPageLink()>1)
		{
			 startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
			 endRecordIndex = no;
			 logger.info("startRecordIndex .................... "+startRecordIndex);
			 logger.info("endRecordIndex .................... "+endRecordIndex);
			 
			 	
			bufInsSql.append(" ORDER BY cr_deal_verification_dtl.LOAN_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search getDefaultCompletionData query for SQL SERVER : " + bufInsSql.toString());
	
			 //bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));	
			 
		}
		logger.info("query : "+bufInsSql);
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		header = ConnectionDAO.sqlSelect(bufInsSql.toString());
		for(int i=0;i<header.size();i++)
		{
			 ArrayList header1=(ArrayList)header.get(i);
			 if(header1!=null && header1.size()>0)
			 {
				 fieldVo = new FieldVerificationVo();					
				 fieldVo.setLbxLoanNoHID(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(0))).trim());
				 fieldVo.setLoanNo("<a href=fieldVarificationComAction.do?method=addFieldCompletionAtCM&loanId="+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(0))).trim()+">"+StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(1)))+"</a>");
				 fieldVo.setInitiationDate(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(2))).trim());
				 fieldVo.setCustomerName(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(3))).trim());
				 fieldVo.setProduct(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(4))).trim());
				 fieldVo.setScheme(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(5))).trim());
				 fieldVo.setStatus(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(6))).trim());
				 fieldVo.setTotalRecordSize(count);
				 list.add(fieldVo);
			 }
		 }
	}
	catch(Exception e)
	{
	 e.printStackTrace();
	}		
	return list;

}

public ArrayList searchVerificationCompletionAtCM(FieldVerificationVo vo) {

	ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	try
	{
		ArrayList header=null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		FieldVerificationVo fieldVo= (FieldVerificationVo) vo;
		boolean appendSQL=false;		
		StringBuffer bufInsSql=new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();		
		String loanId = vo.getLbxLoanNoHID().trim();
		String date = vo.getInitiationDate().trim();
		String custName = vo.getCustomerName().trim();
		String productId = vo.getLbxProductID().trim();
		String schemeId = vo.getLbxscheme().trim();	
		bufInsSql.append(" select distinct cr_deal_verification_dtl.LOAN_ID,cr_loan_dtl.LOAN_NO,");
		//bufInsSql.append(dbo);
		bufInsSql.append("'' as date,gcd_customer_m.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC,CASE WHEN ISNULL(cr_loan_dtl.DISBURSAL_STATUS,'N')='N' THEN 'NO DISBURSAL' WHEN ISNULL(cr_loan_dtl.DISBURSAL_STATUS,'N')='P' THEN 'PARTIAL DISBURSAL' WHEN ISNULL(cr_loan_dtl.DISBURSAL_STATUS,'N')='F' THEN 'FINAL DISBURSAL' END as STATUS   ");
		bufInsSql.append(" from cr_deal_verification_dtl inner join cr_loan_dtl on(cr_loan_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID AND cr_loan_dtl.REC_STATUS<>'X' AND cr_loan_dtl.LOAN_BRANCH='"+fieldVo.getBranchId()+"') inner join cr_product_m on(cr_loan_dtl.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)inner join cr_scheme_m on(cr_loan_dtl.LOAN_SCHEME=cr_scheme_m.SCHEME_ID)inner join gcd_customer_m  on(gcd_customer_m.CUSTOMER_ID=cr_loan_dtl.LOAN_CUSTOMER_ID) inner join cr_field_verification_dtl on(cr_field_verification_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID) where 1=1 ");
		bufInsSqlTempCount.append("SELECT  COUNT(1) FROM(select distinct cr_deal_verification_dtl.LOAN_ID,cr_loan_dtl.LOAN_NO,");
		//bufInsSqlTempCount.append(dbo);
		bufInsSqlTempCount.append("'' as date ,gcd_customer_m.CUSTOMER_NAME,cr_product_m.PRODUCT_DESC,cr_scheme_m.SCHEME_DESC  from cr_deal_verification_dtl inner join cr_loan_dtl on(cr_loan_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID AND cr_loan_dtl.REC_STATUS<>'X' AND cr_loan_dtl.LOAN_BRANCH='"+fieldVo.getBranchId()+"') inner join cr_product_m on(cr_loan_dtl.LOAN_PRODUCT=cr_product_m.PRODUCT_ID)inner join cr_scheme_m on(cr_loan_dtl.LOAN_SCHEME=cr_scheme_m.SCHEME_ID)inner join gcd_customer_m  on(gcd_customer_m.CUSTOMER_ID=cr_loan_dtl.LOAN_CUSTOMER_ID) inner join cr_field_verification_dtl on(cr_field_verification_dtl.LOAN_ID=cr_deal_verification_dtl.LOAN_ID) where 1=1");
		if(!(loanId.trim().length()==0))
		{
			bufInsSql.append(" and cr_deal_verification_dtl.stage='LIM'  and cr_deal_verification_dtl.LOAN_ID="+loanId);
			bufInsSqlTempCount.append(" and cr_deal_verification_dtl.stage='LIM'  and cr_deal_verification_dtl.LOAN_ID="+loanId);			
		}
		/*if(!(date.trim().length()==0))
		{
			bufInsSql.append(" and ");
			bufInsSql.append(dbo);
			bufInsSql.append("date(cr_deal_dtl.DEAL_DATE)=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE('"+date+"','"+dateFormat+"')");
			bufInsSqlTempCount.append(" and ");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("date(cr_deal_dtl.DEAL_DATE)=");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("STR_TO_DATE('"+date+"','"+dateFormat+"')");			
		}*/
		if(!(custName.trim().length()==0))
		{
			bufInsSql.append(" and gcd_customer_m.CUSTOMER_NAME like'%"+custName+"%'");
			bufInsSqlTempCount.append(" and gcd_customer_m.CUSTOMER_NAME like'%"+custName+"%'");			
		}
		if(!(productId.trim().length()==0))
		{
			bufInsSql.append(" and cr_loan_dtl.LOAN_PRODUCT ='"+productId+"'");
			bufInsSqlTempCount.append(" and cr_loan_dtl.LOAN_PRODUCT ='"+productId+"'");			
		}
		if(!(schemeId.trim().length()==0))
		{
			bufInsSql.append(" and cr_loan_dtl.LOAN_SCHEME ='"+schemeId+"'");
			bufInsSqlTempCount.append(" and cr_loan_dtl.LOAN_SCHEME ='"+schemeId+"'");			
		}
		bufInsSql.append(" and  cr_deal_verification_dtl.REC_STATUS='R' and cr_deal_verification_dtl.stage='LIM'   and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.LOAN_ID NOT in(select distinct ISNULL(LOAN_ID,0) from cr_deal_verification_dtl where REC_STATUS='F'  and VERIFICATION_ACTION ='I' ) and cr_deal_verification_dtl.LOAN_ID not in (select ISNULL(LOAN_ID,0) from cr_field_verification_dtl where MAKER_ID='"+vo.getUserId()+"') ");
		bufInsSqlTempCount.append(" and  cr_deal_verification_dtl.REC_STATUS='R' and cr_deal_verification_dtl.stage='LIM'   and cr_deal_verification_dtl.VERIFICATION_ACTION='I' and cr_deal_verification_dtl.LOAN_ID NOT  in(select distinct ISNULL(LOAN_ID,0) from cr_deal_verification_dtl where REC_STATUS='F'  and VERIFICATION_ACTION ='I') and cr_deal_verification_dtl.LOAN_ID not in (select ISNULL(LOAN_ID,0) from cr_field_verification_dtl where MAKER_ID='"+vo.getUserId()+"') ");
		bufInsSqlTempCount.append(" )as f ");	
		
		logger.info("bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
		logger.info("current PAge Link no .................... "+fieldVo.getCurrentPageLink());
		if(fieldVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
			
		bufInsSql.append(" ORDER BY cr_deal_verification_dtl.LOAN_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
		logger.info("Search searchDealForComCapture query for SQL SERVER : " + bufInsSql.toString());

		//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
		logger.info("query : "+bufInsSql);
		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
	    header = ConnectionDAO.sqlSelect(bufInsSql.toString());
					
		for(int i=0;i<header.size();i++){
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				fieldVo = new FieldVerificationVo();
				fieldVo.setLoanNo("<a href=fieldVarificationComAction.do?method=addFieldCompletionAtCM&loanId="+(CommonFunction.checkNull(header1.get(0))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
				fieldVo.setInitiationDate((CommonFunction.checkNull(header1.get(2))).trim());
				fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(3))).trim());
				fieldVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
				fieldVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
				fieldVo.setStatus((CommonFunction.checkNull(header1.get(6))).trim());
				fieldVo.setTotalRecordSize(count);
				list.add(fieldVo);
			}
		}
	}
	catch(Exception e)
	{e.printStackTrace();}	
	logger.info("Detail List when searchList is : "+list.size());	
	return list;	

}

public ArrayList getCompletionListAtCM(String loanId,String functionId) {
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	try {

		logger.info("In getCompletionListAtCM()...........Dao Impl");
		
		
		StringBuffer bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT FIELD_VERIFICATION_ID,APPRAISER_NAME,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(APPRAISAL_DATE,'"+dateFormat+"'),VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,ENTITY_TYPE FROM cr_field_verification_dtl ");
		bufInsSql.append(" WHERE STAGE='LIM' AND LOAN_ID= '"+loanId+"' ");
		
		logger.info("IN getCompletionList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

	
		logger.info("getCompletionList " + searchList.size());

		for (int i = 0; i < searchList.size(); i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				FieldVerificationVo deptMVO = new FieldVerificationVo();
								
				 deptMVO.setFieldVerificationUniqueId(CommonFunction.checkNull(data.get(0)));
				 deptMVO.setAppraisalName(CommonFunction.checkNull(data.get(1)));	 			 
				 deptMVO.setAppraisalDate(CommonFunction.checkNull(data.get(2)));
				 deptMVO.setVerificationType(CommonFunction.checkNull(data.get(3)));
		    	 deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(4)));
		    	 if(CommonFunction.checkNull(data.get(5)).equalsIgnoreCase("PRAPPL"))
				 {
					 deptMVO.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(data.get(5)).equalsIgnoreCase("COAPPL"))
				 {
					 deptMVO.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 deptMVO.setEntityType(CommonFunction.checkNull(data.get(5)));
				 }
				 detailList.add(deptMVO);
			}

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;
}
public ArrayList getViewCommonListListAtCM(String loanId, String fieldVerificationId) {
	
	
	ArrayList searchList = null;
	ArrayList detailList = new ArrayList();
	StringBuffer bufInsSql =null;
	FieldVerificationVo deptMVO =null;
	try {

		logger.info("In getViewCommonListListAtCM()...........Dao Impl");
		bufInsSql = new StringBuffer();
				
		bufInsSql.append("SELECT VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,ENTITY_TYPE,ENTITY_SUB_TYPE,APPRAISER_NAME,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(APPRAISAL_DATE,'"+dateFormat+"'),VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,FV_STATUS,FV_REMARKS,ASSET_LOCATION,ASSET_CONDITION,INVOICE_NO,INVOICE_COLLECTED FROM cr_field_verification_dtl ");
		bufInsSql.append(" WHERE STAGE='LIM' AND LOAN_ID= '"+loanId+"' and FIELD_VERIFICATION_ID="+fieldVerificationId+" ");
		
		logger.info("IN getViewCommonListList()  query1 ### "+ bufInsSql.toString());
		searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());

		int size=searchList.size();
		for (int i = 0; i < size; i++) {
		
			ArrayList data = (ArrayList) searchList.get(i);

			if (data.size() > 0) {
				
				deptMVO = new FieldVerificationVo();
								
				deptMVO.setVerificationType(CommonFunction.checkNull(data.get(0)));
				
	 			 
				 deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(1)));
				 
				 
				
				 if(CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("PRAPPL"))
				 {
					 deptMVO.setEntityType("APPLICANT");
				 }
				 else if(CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("COAPPL"))
				 {
					 deptMVO.setEntityType("COAPPLICANT");
				 }
				 else
				 {
					 deptMVO.setEntityType(CommonFunction.checkNull(data.get(2)));
				 }
				 deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(3)));
				
				 deptMVO.setAppraisalName(CommonFunction.checkNull(data.get(4)));	 			 
				 deptMVO.setAppraisalDate(CommonFunction.checkNull(data.get(5)));
		    	 deptMVO.setVerificationMode(CommonFunction.checkNull(data.get(6)));
				 deptMVO.setPersonToMeet(CommonFunction.checkNull(data.get(7)));
				 deptMVO.setRelationWithApplicant(CommonFunction.checkNull(data.get(8)));	
				 deptMVO.setPhone1(CommonFunction.checkNull(data.get(9)));	
				 deptMVO.setPhone2(CommonFunction.checkNull(data.get(10)));	
				 deptMVO.setEmail(CommonFunction.checkNull(data.get(11)));	
				 deptMVO.setCpvStatus(CommonFunction.checkNull(data.get(12)));
				 deptMVO.setRemarks(CommonFunction.checkNull(data.get(13)));
				 
				 deptMVO.setAssetLocation(CommonFunction.checkNull(data.get(14)));
				 deptMVO.setAssetCondition(CommonFunction.checkNull(data.get(15)));
				 deptMVO.setInvoiceNumber(CommonFunction.checkNull(data.get(16)));
				 deptMVO.setInvoiceCollected(CommonFunction.checkNull(data.get(17)));
				 detailList.add(deptMVO);
			}
			data=null;

		}
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		loanId=null;
		fieldVerificationId=null;
		deptMVO=null;
	    searchList=null;
		bufInsSql=null;
	}
	return detailList;
	
}
public boolean completionAuthorVerfAtCM(FieldVerificationVo vo) {
	
	
	boolean status = false;
	
	logger.info("In completionAuthorVerfAtCM...............FieldVerificationDAOIMpl ");
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	PrepStmtObject insertPrepStmtObjectDealVerification = new PrepStmtObject();
	StringBuffer bufInsSql = new StringBuffer();
	String rec_status_FV = "";
	String rec_status_DV = "";
	try {
			
		  logger.info("In completionAuthorVerfAtCM..........................before split...........vo.getVerificationCapId() "+vo.getVerificationCapId());
		   String[] checkedIDArr = vo.getVerificationCapId().split("/");
		   logger.info("In completionAuthorVerfAtCM.......................after split..............vo.getVerificationCapId() "+checkedIDArr);
			if(CommonFunction.checkNull(vo.getDecison()).equalsIgnoreCase("S")){
				rec_status_FV = "P";
				rec_status_DV = "F";
				if(checkedIDArr.length > 0)
				{					
						for(int i =0;i<checkedIDArr.length;i++)
						{	
							 bufInsSql = new StringBuffer();
			                 insertPrepStmtObject = new PrepStmtObject();
			                 insertPrepStmtObjectDealVerification = new PrepStmtObject();
				             bufInsSql.append("UPDATE cr_field_verification_dtl  SET FV_STATUS=?, rec_status=?,COMPL_REMARKS=? WHERE  STAGE='LIM' AND FIELD_VERIFICATION_ID=?");
				
				if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getDecison()).trim());
				
				
				insertPrepStmtObject.addString(rec_status_FV);
				
				
				if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTextarea()).trim());
											    
			    if((CommonFunction.checkNull(checkedIDArr[i])).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(checkedIDArr[i].trim());
					   
				 insertPrepStmtObject.setSql(bufInsSql.toString());
				
						
				  logger.info("IN completionAuthorVerf() update query1(cr_field_verification_dtl) ### "+insertPrepStmtObject.printQuery());
				  qryList.add(insertPrepStmtObject);
				
				
				 StringBuffer bufInsSqlDealVerification = new StringBuffer();
				 bufInsSqlDealVerification.append("UPDATE cr_deal_verification_dtl  SET VERIFICATION_STATUS=?, VERIFICATION_REMARKS=?,AUTHOR_REMARKS=?,"+
							"rec_status=?,AUTHOR_ID=?,AUTHOR_DATE=");
				 //bufInsSqlDealVerification.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
				// saurabh space starts
				 bufInsSqlDealVerification.append(dbo);
				 bufInsSqlDealVerification.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				 // saurabh space ends
				 bufInsSqlDealVerification.append("WHERE VERIFICATION_ID=(select ISNULL(VERIFICATION_ID,0) from cr_field_verification_dtl where STAGE='LIM' AND FIELD_VERIFICATION_ID=?)");
					
					if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
						insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getDecison()).trim());
					
					if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
						insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getTextarea()).trim());
					
					if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
						insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getTextarea()).trim());
					
					insertPrepStmtObjectDealVerification.addString(rec_status_DV);
					
					if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getMakerId()).trim());
				  
				    
				    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
				    	insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString((vo.getMakerDate()).trim());
				    
				    if((CommonFunction.checkNull(checkedIDArr[i].trim()).equalsIgnoreCase("")))
				    	insertPrepStmtObjectDealVerification.addNull();
					else
						insertPrepStmtObjectDealVerification.addString(checkedIDArr[i].trim());

						   
				       insertPrepStmtObjectDealVerification.setSql(bufInsSqlDealVerification.toString());
					
					
						
					  logger.info("IN completionAuthorVerf() update query2(cr_deal_verification_dtl) ### "+insertPrepStmtObjectDealVerification.printQuery());
					   qryList.add(insertPrepStmtObjectDealVerification);
						}
				}
			}
			else
			{
				rec_status_FV = "A";
				rec_status_DV = "C";
				  bufInsSql = new StringBuffer();
					bufInsSql.append("UPDATE cr_field_verification_dtl  SET FV_STATUS=?, rec_status=?,COMPL_REMARKS=? WHERE STAGE='LIM' AND LOAN_ID=?");
					
					if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDecison()).trim());
					
					
					insertPrepStmtObject.addString(rec_status_FV);
					
					
					if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTextarea()).trim());
												    
				    if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLbxLoanNoHID()).trim());
						   
					 insertPrepStmtObject.setSql(bufInsSql.toString());
					
							
					  logger.info("IN completionAuthorVerf() update query1(cr_field_verification_dtl) ### "+insertPrepStmtObject.printQuery());
					  qryList.add(insertPrepStmtObject);
					
					
					 StringBuffer bufInsSqlDealVerification = new StringBuffer();
					 bufInsSqlDealVerification.append("UPDATE cr_deal_verification_dtl  SET VERIFICATION_STATUS=?, VERIFICATION_REMARKS=?,AUTHOR_REMARKS=?,"+
								"rec_status=?,AUTHOR_ID=?,AUTHOR_DATE=");
					 //bufInsSqlDealVerification.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
					// saurabh space starts
					 bufInsSqlDealVerification.append(dbo);
					 bufInsSqlDealVerification.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
					 // saurabh space ends
					 bufInsSqlDealVerification.append("WHERE STAGE='LIM' AND LOAN_ID=?");
						
						if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
							insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getDecison()).trim());
						
						if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
							insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getTextarea()).trim());
						
						if((CommonFunction.checkNull(vo.getTextarea())).trim().equalsIgnoreCase(""))
							insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getTextarea()).trim());
						
						insertPrepStmtObjectDealVerification.addString(rec_status_DV);
						
						if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
							insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getMakerId()).trim());
					  
					    
					    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
					    	insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getMakerDate()).trim());
					    
					    if((CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim().equalsIgnoreCase(""))
					    	insertPrepStmtObjectDealVerification.addNull();
						else
							insertPrepStmtObjectDealVerification.addString((vo.getLbxLoanNoHID()).trim());

							   
					       insertPrepStmtObjectDealVerification.setSql(bufInsSqlDealVerification.toString());
						
						
							
						  logger.info("IN completionAuthorVerf() update query2(cr_deal_verification_dtl) ### "+insertPrepStmtObjectDealVerification.printQuery());
						qryList.add(insertPrepStmtObjectDealVerification);

				
			}
			
					
				    status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		 
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}

public boolean getDealStatus(String dealId) {
	
	boolean status = false;
	try
	{
        String empQuery = "SELECT DEAL_ID FROM CR_DEAL_DTL where  REC_STATUS='F' AND DEAL_ID='"+dealId+"'";
		logger.info("In getDealStatus.................Dao Impl"+ empQuery);
		status = ConnectionDAO.checkStatus(empQuery);
	
	}
	catch(Exception e)
	{
		logger.error("Exception in checkDealID() :: "+e.getMessage());
	}
	return status;
}

public ArrayList<Object> fetchChildDocs(String txnDocId, String txnId,String txnType) {
	
	logger.info("In fetchChildDocs(String txnDocId, String txnId,String txnType) ");
	ArrayList list=new ArrayList();
	try{

	//String collectedDocsQ = "select DISTINCT DOC_CHILD_IDS from cr_document_dtl where TXN_DOC_ID= '"+txnDocId+"' AND ISNULL(DOC_CHILD_IDS,'')<>'' AND TXN_TYPE='"+txnType+"' AND TXNID="+txnId+" ";
	String collectedDocsQ = "select DOC_CHILD_IDS from cr_deal_verification_dtl where ENTITY_ID= '"+txnDocId+"' AND STAGE='DC' AND DEAL_ID='"+txnId+"' ";
	logger.info("In fetchChildDocs(String txnDocId, String txnId,String txnType) query: "+ collectedDocsQ);
    String collectedDocs = ConnectionDAO.singleReturn(collectedDocsQ);
    collectedDocsQ=null;
    String coll="";
	if (!CommonFunction.checkNull(collectedDocs).equalsIgnoreCase("")) {
		String[] collects = collectedDocs.split("\\|");
		
		for (int i = 0; i < collects.length; i++) {
		
				coll+=","+collects[i];
		   }
     	    String docChildId= coll.substring(1, coll.length());
		    logger.info("In fetchChildDocs sub: "+ docChildId);
		    StringBuilder query=new StringBuilder();
		    query.append("select DOC_CHILD_ID,DOC_DESC FROM CR_DOCUMENT_CHILD_M WHERE DOC_CHILD_ID IN ("+docChildId+")" );
			logger.info("fetchChildDocs query................. "+query.toString());
			ArrayList otherDetail = ConnectionDAO.sqlSelect(query.toString());
			query=null;
			docChildId=null;
			coll=null;
			int size=otherDetail.size();
			logger.info("In fetchChildDocs(String txnDocId, String txnId,String txnType)list size... "+size);
			for(int i=0;i<size;i++){
				
				ArrayList data=(ArrayList)otherDetail.get(i);
				if(data.size()>0)
				{
					CodeDescVo child=new CodeDescVo();
					child.setId((CommonFunction.checkNull(data.get(0))).trim());
					child.setName((CommonFunction.checkNull(data.get(1))).trim());
					list.add(child);
					child=null;
				}
				data.clear();
				data=null;
			
			}
			otherDetail.clear();
			otherDetail=null;
	      }
	
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;

	}

public ArrayList verificationCompletionDealDetail(FieldVerificationVo vo) {
	
	
	ArrayList<FieldVerificationVo> list = new ArrayList<FieldVerificationVo>();
	logger.info("In verificationCompletionDealDetail ");
	try
	{
		ArrayList header=null;
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		
		logger.info(" verificationCompletionDealDetail() here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
			String userName=ConnectionDAO.singleReturn(userNameQ);
			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		
			
			FieldVerificationVo fieldVo= (FieldVerificationVo) vo;
		boolean appendSQL=false;
		
		
		StringBuffer bufInsSql=new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		
		String dealNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
		String appDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getApplicationDate())).trim());
		String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
		String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
		String schemeId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
		String initDate= (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate())).trim());
		
		bufInsSql.append(" select DISTINCT d.DEAL_ID,d.DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,sm.USER_NAME");
	
		bufInsSql.append(" from cr_deal_dtl d left join cr_deal_verification_dtl dv on dv.stage='DC' and d.DEAL_ID=dv.DEAL_ID AND (ISNULL(dv.REC_STATUS,'X')='P' AND dv.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' OR 'A'='A' )");
		
		bufInsSql.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID INNER join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID "); 
		 
		bufInsSql.append(" INNER join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "); 
		 
		bufInsSql.append(" INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID AND DM.DEAL_RECEIVED IS NOT NULL AND DM.DEAL_FORWARDED IS NULL AND DM.DEAL_STAGE_ID='FVI' AND DM.REC_STATUS='A' "); 
		bufInsSql.append(" LEFT join com_branch_m cb on d.DEAL_BRANCH=cb.BRANCH_ID " );
		bufInsSql.append(" LEFT join sec_user_m sm ON d.MAKER_ID=sm.USER_ID " );
		bufInsSql.append(" INNER join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID Where d.REC_STATUS IN ('P','F') "); 
	 
		bufInsSqlTempCount.append(" select COUNT(DISTINCT d.DEAL_ID) ");
		
		bufInsSqlTempCount.append(" from cr_deal_dtl d left join cr_deal_verification_dtl dv on dv.stage='DC' and d.DEAL_ID=dv.DEAL_ID AND (ISNULL(dv.REC_STATUS,'X')='P' AND dv.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' OR 'A'='A' ) ");
		
		bufInsSqlTempCount.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID INNER join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID "); 
		 
		bufInsSqlTempCount.append(" INNER join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "); 
		 
		bufInsSqlTempCount.append(" INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID AND DM.DEAL_RECEIVED IS NOT NULL AND DM.DEAL_FORWARDED IS NULL AND DM.DEAL_STAGE_ID='FVI' AND DM.REC_STATUS='A' "); 
		bufInsSqlTempCount.append(" LEFT join com_branch_m cb on d.DEAL_BRANCH=cb.BRANCH_ID " );
		bufInsSqlTempCount.append(" LEFT join sec_user_m sm ON d.MAKER_ID=sm.USER_ID " );
		bufInsSqlTempCount.append(" INNER join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID Where d.REC_STATUS IN ('P','F') "); 
	 
		if (!((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReportingToUserId())).trim().equalsIgnoreCase(""))))
		{
			bufInsSql.append(" AND d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' ");	
			bufInsSqlTempCount.append(" AND d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"'");
			appendSQL = true;
		}
		
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase(""))) &&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate()).trim()).equalsIgnoreCase(""))))
		{
	   	  bufInsSql.append("WHERE d.DEAL_ID='"+dealNo+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
	   	  bufInsSqlTempCount.append("WHERE d.DEAL_ID='"+dealNo+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ) as b");
		}
		
		if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getInitiationDate().equalsIgnoreCase("")))){
			appendSQL=true;
		}
		
		 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
	        bufInsSql.append(" AND d.DEAL_ID='"+dealNo+"' ");
	        bufInsSqlTempCount.append(" AND d.DEAL_ID='"+dealNo+"' ");
	   	 	appendSQL=true;
	   	  
	     }
	
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' ");
			bufInsSqlTempCount.append(" AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%'");
	   	  	appendSQL=true;
	     }
		
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"' ");
		   	  bufInsSqlTempCount.append(" AND p.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'");
		   	  appendSQL=true;
		     }
		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim().equalsIgnoreCase("")))) {
			bufInsSql.append(" AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
		   	 bufInsSqlTempCount.append(" AND s.SCHEME_ID='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"'");
		   	  appendSQL=true;
		     }
		if((!(vo.getLbxDealNo().equalsIgnoreCase("")))||(!(vo.getCustomerName().equalsIgnoreCase("")))||(!(vo.getLbxProductID().equalsIgnoreCase("")))||(!(vo.getLbxscheme().equalsIgnoreCase("")))||(!(vo.getInitiationDate().equalsIgnoreCase("")))){
		
			appendSQL=true;
		}	
		 logger.info("count query : "+bufInsSqlTempCount.toString());
		 String countVar=ConnectionDAO.singleReturn(bufInsSqlTempCount.toString());
		 if(CommonFunction.checkNull(countVar).equalsIgnoreCase(""))
		 {
			 countVar="0";
		 }
			 
		 count =Integer.parseInt(countVar);
		
		if(fieldVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (fieldVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			
		}
		
		bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
		bufInsSql.append(startRecordIndex);
		bufInsSql.append(" ROWS FETCH next ");
		bufInsSql.append(endRecordIndex);
		bufInsSql.append(" ROWS ONLY ");
		
	
		logger.info("main query : "+bufInsSql.toString());
	     header = ConnectionDAO.sqlSelect(bufInsSql.toString());

					
		for(int i=0;i<header.size();i++){
		
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				
				fieldVo = new FieldVerificationVo();
				
				fieldVo.setLbxDealNo((CommonFunction.checkNull(header1.get(0))).trim());
				fieldVo.setDealNo("<a href=\"#\" onclick=\"modifyVerificationCompletion('"+((CommonFunction.checkNull(header1.get(0))).trim())+"');\" >" +
						((CommonFunction.checkNull(header1.get(1))).trim())+"</a>");
				fieldVo.setCustomerName((CommonFunction.checkNull(header1.get(2))).trim());
				fieldVo.setProduct((CommonFunction.checkNull(header1.get(3))).trim());
				fieldVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
				fieldVo.setReportingToUserId((CommonFunction.checkNull(header1.get(5))).trim());
				fieldVo.setTotalRecordSize(count);
				list.add(fieldVo);
			}
		}
		

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	logger.info("initiatedVerificationDealDetail : "+list.size());
	
	return list;
}

 public boolean insertVerCompletionModule(FieldVerificationVo fieldVo){
	
	
	boolean status=false;
	ArrayList qryList = new ArrayList();
			
	StringBuffer bufInsSql = null;
	PrepStmtObject insertPrepStmtObject = null;
	String[] action=fieldVo.getVerificationAction();
	String[] entityId=fieldVo.getEntityId();
	String[] verType=fieldVo.getVerType();
	String[] verSubType=fieldVo.getVerSubType();
	
	String[] entType=fieldVo.getEntType();
	String[] entSubType=fieldVo.getEntSubType();
	
	String[] externalAppUserId=fieldVo.getExternalAppUserId();
	String[] internalAppUserId=fieldVo.getInternalAppUserId();
	
	String[] appraiser=fieldVo.getAppraiser();
	String []entityDescription=fieldVo.getEntityDescription();
	String lbxAddressType[]=fieldVo.getLbxAddressType();
	String [] appraiserRemarksArray=fieldVo.getAppraiserRemarksArray();
	String [] appraiserDateArray=fieldVo.getAppraiserDateArray();
	String [] verfDecisionArray=fieldVo.getVerfDecisionArray();
	String deleteQuery=null;
	try {

		logger.info("entityId.length: "+entityId.length);
		for (int i = 0; i < entityId.length; i++) {
			
					
			/*if((CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("W")))
			{
				if(entSubType[i].equalsIgnoreCase("BUSINESS")||entSubType[i].equalsIgnoreCase("MARKET"))
				{
					deleteQuery="DELETE FROM cr_deal_verification_dtl WHERE REC_STATUS='F' AND ENTITY_SUB_TYPE='"+entSubType[i]+"' AND STAGE='DC' AND DEAL_ID="+fieldVo.getDealId();
				}
				else
				{
					deleteQuery="DELETE FROM cr_deal_verification_dtl WHERE REC_STATUS='F' AND ENTITY_ID='"+entityId[i]+"' AND STAGE='DC' AND DEAL_ID="+fieldVo.getDealId();
				}
				logger.info("deleteQuery of Deletion is ="+deleteQuery);
				ArrayList deleteArl= new ArrayList();
				deleteArl.add(deleteQuery);
				status=ConnectionDAO.sqlInsUpdDelete(deleteArl);
				deleteArl=null;
				logger.info("Status of Deletion is ="+status);
			}*/
			if(!(CommonFunction.checkNull(verfDecisionArray[i]).trim().equalsIgnoreCase(""))){
				
			 insertPrepStmtObject = new PrepStmtObject();
			 bufInsSql = new StringBuffer();
			
			bufInsSql.append("insert into cr_deal_verification_dtl(DEAL_ID,VERIFICATION_TYPE,VERIFICATION_SUBTYPE,APPRAISER_TYPE,INTERNAL_APPRAISER,");
			bufInsSql.append(" EXTERNAL_APPRAISER,VERIFICATION_ACTION,ENTITY_ID,ENTITY_TYPE,ENTITY_SUB_TYPE,ENTITY_DESC,ADDRESS_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,STAGE,VERIFICATION_STATUS,VERIFICATION_REMARKS)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");//DEAL_ID
			bufInsSql.append(" ?,");//V+ERIFICATION_TYPE
			bufInsSql.append(" ?,");//VERIFICATION_SUBTYPE
			bufInsSql.append(" ?,");//APPRAISER_TYPE
			bufInsSql.append(" ?,");//INTERNAL_APPRAISER
			bufInsSql.append(" ?,");//EXTERNAL_APPRAISER
			
			bufInsSql.append(" ?,");//VERIFICATION_ACTION
			bufInsSql.append(" ?,");//ENTITY_ID
			
			bufInsSql.append(" ?,");//ENTITY_TYPE
			bufInsSql.append(" ?,");//ENTITY_SUB_TYPE
			bufInsSql.append(" ?,");//ENTITY_DESC
			bufInsSql.append(" ?,");//ADDRESS_TYPE
			bufInsSql.append(" ?,");//REC_STATUS
			bufInsSql.append(" ?,");//MAKER_ID
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),"); // MAKER_DATE
			bufInsSql.append(" ?,");//AUTHOR_ID
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),"); // AUTHOR_DATE
			bufInsSql.append(" 'DC' ,");//AUTHOR_ID
			bufInsSql.append(" ?,");//VERIFICATION_STATUS
			bufInsSql.append(" ? )");//VERIFICATION_REMARKS
		
			if (CommonFunction.checkNull(fieldVo.getDealId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((fieldVo.getDealId()).trim());

			
			if (CommonFunction.checkNull(verType[i]).trim().equalsIgnoreCase("") )
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((verType[i]).trim());
			
			
			if (CommonFunction.checkNull(verSubType[i]).trim().equalsIgnoreCase("") )
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((verSubType[i]).trim());
				
			if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
			{	
				if (CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("") )
					insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString((appraiser[i]).trim());
			}else
			{
				insertPrepStmtObject.addNull();
			}	
		
				if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
				{
					if(internalAppUserId.length>0 && CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("INTERNAL"))
					{
						if (CommonFunction.checkNull(internalAppUserId[i]).trim().equalsIgnoreCase("") )
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((internalAppUserId[i]).trim());
					}
					else
					{
						insertPrepStmtObject.addNull();
					}
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
			
				if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
				{
					if(externalAppUserId.length>0 && CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("EXTERNAL"))
					{
						if (CommonFunction.checkNull(externalAppUserId[i]).trim().equalsIgnoreCase("") )
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((externalAppUserId[i]).trim());
						}
					else
					{
						insertPrepStmtObject.addNull();
					}
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
			
					
			if (CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(action[i].trim());
			
			if (CommonFunction.checkNull(entityId[i]).trim().equalsIgnoreCase("") )
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((entityId[i]).trim());
			
											
			if (CommonFunction.checkNull(entType[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			{
				if(entType[i].trim().equalsIgnoreCase("APPLICANT"))
				{
					insertPrepStmtObject.addString("PRAPPL");
				}
				else if(entType[i].trim().equalsIgnoreCase("COAPPLICANT"))
				{
					insertPrepStmtObject.addString("COAPPL");
				}
				else
				{
					insertPrepStmtObject.addString(entType[i].trim());
				}
			}
				
			
			if (CommonFunction.checkNull(entSubType[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(entSubType[i].trim());
			
			if(CommonFunction.checkNull(entityDescription[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(entityDescription[i].trim());
			
			if(CommonFunction.checkNull(lbxAddressType[i]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(lbxAddressType[i].trim());
			
			
			insertPrepStmtObject.addString("F");

			if (CommonFunction.checkNull(fieldVo.getMakerId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerId().trim());

			if (CommonFunction.checkNull(fieldVo.getMakerDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());

			if (CommonFunction.checkNull(fieldVo.getMakerId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerId().trim());

			if (CommonFunction.checkNull(fieldVo.getMakerDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());
			
			if(verfDecisionArray.length>0 && !CommonFunction.checkNull(verfDecisionArray[i]).trim().equalsIgnoreCase(""))
			{
				if (CommonFunction.checkNull(verfDecisionArray[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(verfDecisionArray[i].trim());
			}
			else
			{
				insertPrepStmtObject.addNull();
			}
			logger.info("appraiser remarks : "+appraiserRemarksArray.length);
			if(appraiserRemarksArray.length>0 && !CommonFunction.checkNull(appraiserRemarksArray[i]).trim().equalsIgnoreCase(""))
			{
				
				if (CommonFunction.checkNull(appraiserRemarksArray[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(appraiserRemarksArray[i].trim());
			}
			else
			{
				insertPrepStmtObject.addNull();
			}
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			logger.info("IN insertfieldVerification() insert query1 ### "+ insertPrepStmtObject.printQuery());
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			String maxIdQuery="Select distinct max(VERIFICATION_ID) from cr_deal_verification_dtl WITH (ROWLOCK)";
			    
			  
			   String verificationId=ConnectionDAO.singleReturn(maxIdQuery);
			   maxIdQuery=null;
			   logger.info("verificationId : "+verificationId);
			   fieldVo.setVerificationId(verificationId);
			   verificationId=null;
			
			ArrayList deleteQryList=new ArrayList();
		
			deleteQuery="Delete from CR_FIELD_VERIFICATION_DTL where VERIFICATION_ID='"+fieldVo.getVerificationId()+"' and stage='DC' and deal_id="+fieldVo.getDealId();
			logger.info("CR_FIELD_VERIFICATION_DTL deleteQuery: "+deleteQuery);
			deleteQryList.add(deleteQuery);
			boolean deleteStatus;
			
				deleteStatus = ConnectionDAO.sqlInsUpdDelete(deleteQryList);
				logger.info("deleteStatus: "+deleteStatus);
		
			
					insertPrepStmtObject = new PrepStmtObject();			
					logger.info("In insert Field Verification Capturing");
					
					bufInsSql = new StringBuffer();
					bufInsSql.append(" INSERT INTO CR_FIELD_VERIFICATION_DTL(DEAL_ID,ENTITY_ID,ENTITY_TYPE,ENTITY_SUB_TYPE,VERIFICATION_ID,VERIFICATION_TYPE,VERIFICATION_SUB_TYPE,APPRAISER_NAME,APPRAISAL_DATE,VERIFICATION_MODE,");
					bufInsSql.append(" PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,FV_STATUS,FV_REMARKS,MAKER_ID,MAKER_DATE,REC_STATUS,STAGE ) ");
					
					
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");//DEAL_ID
					bufInsSql.append(" ?,");//ENTITY_ID
					bufInsSql.append(" ?,");//ENTITY_TYPE
					bufInsSql.append(" ?,");//ENTITY_SUB_TYPE
					bufInsSql.append(" ?,");//VERIFICATION_ID
					bufInsSql.append(" ?,");//VERIFICATION_TYPE  
					bufInsSql.append(" ?,");//VERIFICATION_SUB_TYPE
					bufInsSql.append(" ?,");//APPRAISER_NAME
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'" + dateFormatWithTime + "'),");//APPRAISAL_DATE
					bufInsSql.append(" ?,");//VERIFICATION_MODE 
					bufInsSql.append(" ?,");//PERSON_MET
					bufInsSql.append(" ?,");//RELATION
					bufInsSql.append(" ?,");//PHONE1
					bufInsSql.append(" ?,");//PHONE2
					bufInsSql.append(" ?,");//E_MAIL
					bufInsSql.append(" ?,");//FV_STATUS
					bufInsSql.append(" ?,");//FV_REMARKS
					bufInsSql.append(" ?,");//MAKER_ID
					bufInsSql.append(dbo);
					bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),"); // MAKER_DATE	
					bufInsSql.append(" ?,");//REC_STATUS
					bufInsSql.append(" 'DC' )");//STAGE
					
					
				
					if (CommonFunction.checkNull(fieldVo.getDealId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getDealId().trim());
					
				
					if (CommonFunction.checkNull(entityId[i]).trim().equalsIgnoreCase("") )
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((entityId[i]).trim());
					
													
					if (CommonFunction.checkNull(entType[i]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					{
						if(entType[i].trim().equalsIgnoreCase("APPLICANT"))
						{
							insertPrepStmtObject.addString("PRAPPL");
						}
						else if(entType[i].trim().equalsIgnoreCase("COAPPLICANT"))
						{
							insertPrepStmtObject.addString("COAPPL");
						}
						else
						{
							insertPrepStmtObject.addString(entType[i].trim());
						}
					}
						
					if (CommonFunction.checkNull(entSubType[i]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(entSubType[i].trim());			
											
					if (CommonFunction.checkNull(fieldVo.getVerificationId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getVerificationId().trim());
					
					if (CommonFunction.checkNull(verType[i]).trim().equalsIgnoreCase("") )
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((verType[i]).trim());
					
					
					if (CommonFunction.checkNull(verSubType[i]).trim().equalsIgnoreCase("") )
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((verSubType[i]).trim());
						// code start for appraiser name		
					if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
					{
						if(internalAppUserId.length>0 && CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("INTERNAL"))
						{
							if (CommonFunction.checkNull(internalAppUserId[i]).trim().equalsIgnoreCase("") )
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((internalAppUserId[i]).trim());
						}
						else
						{
							insertPrepStmtObject.addNull();
						}
					}
					else if(action.length>0 && CommonFunction.checkNull(action[i]).trim().equalsIgnoreCase("I"))
					{
						if(externalAppUserId.length>0 && CommonFunction.checkNull(appraiser[i]).trim().equalsIgnoreCase("EXTERNAL"))
						{
							if (CommonFunction.checkNull(externalAppUserId[i]).trim().equalsIgnoreCase("") )
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((externalAppUserId[i]).trim());
							}
						else
						{
							insertPrepStmtObject.addNull();
						}
					}
					else
					{
						insertPrepStmtObject.addNull();
					}
					// code end for appraiser name	
					logger.info("appraiser remarks : "+appraiserRemarksArray.length);
					if(appraiserDateArray.length>0 && !CommonFunction.checkNull(appraiserDateArray[i]).trim().equalsIgnoreCase(""))
					{
						if (CommonFunction.checkNull(appraiserDateArray[i]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(appraiserDateArray[i].trim());
					}
					else
					{
						insertPrepStmtObject.addNull();
					}
						
					if (CommonFunction.checkNull(fieldVo.getVerificationMode()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getVerificationMode().toUpperCase().trim());
					
					
					if (CommonFunction.checkNull(fieldVo.getPersonToMeet()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getPersonToMeet().toUpperCase().trim());

					if (CommonFunction.checkNull(fieldVo.getRelationWithApplicant()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getRelationWithApplicant().trim());
					
					if (CommonFunction.checkNull(fieldVo.getPhone1()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getPhone1());
					
					if (CommonFunction.checkNull(fieldVo.getPhone2()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getPhone2());
					
					if (CommonFunction.checkNull(fieldVo.getEmail()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getEmail().trim());
					
					if(verfDecisionArray.length>0 && !CommonFunction.checkNull(verfDecisionArray[i]).trim().equalsIgnoreCase(""))
					{
						if (CommonFunction.checkNull(verfDecisionArray[i]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(verfDecisionArray[i].trim());
					}
					else
					{
						insertPrepStmtObject.addNull();
					}
					logger.info("appraiser remarks : "+appraiserRemarksArray.length);
					if(appraiserRemarksArray.length>0 && !CommonFunction.checkNull(appraiserRemarksArray[i]).trim().equalsIgnoreCase(""))
					{
						
						if (CommonFunction.checkNull(appraiserRemarksArray[i]).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(appraiserRemarksArray[i].trim());
					}
					else
					{
						insertPrepStmtObject.addNull();
					}

					if (CommonFunction.checkNull(fieldVo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getMakerId().trim());
					
					if (CommonFunction.checkNull(fieldVo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(fieldVo.getMakerDate().toUpperCase().trim());
					insertPrepStmtObject.addString("P");
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertFieldVerCapture() insert query1 ### "+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
			
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);				
		     }
		}
			
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		 fieldVo=null;
		 qryList=null;
		 insertPrepStmtObject=null;
		 bufInsSql=null;
		 action=null;
		 entityId=null;
		 verType=null;
		 verSubType=null;
		
		 entType=null;
		 entSubType=null;
		
		 externalAppUserId=null;
		 internalAppUserId=null;
		
		 appraiser=null;
		entityDescription=null;
		lbxAddressType=null;
		 appraiserRemarksArray=null;
		 appraiserDateArray=null;
		verfDecisionArray=null;
		 deleteQuery=null;
	}

	return status;
}
 public ArrayList showVerificationListCompletionModule(String dealId) {
	 
		ArrayList searchList = null;
		ArrayList detailList = new ArrayList();
		FieldVerificationVo deptMVO=null;
		try {

			logger.info("In showVerificationListCompletionModule()...........Dao Impl");
			
			
			StringBuffer bufInsSql = new StringBuffer();
					
			bufInsSql.append(" SELECT DISTINCT V.VERIFICATION_ID,V.VERIFICATION_TYPE,V.APPRAISER_TYPE,U.USER_NAME,V.VERIFICATION_SUBTYPE,IIF(V.VERIFICATION_ACTION='I','INITIATED','WAIVED') AS VERIFICATION_ACTION ,V.ENTITY_ID,V.ENTITY_TYPE,V.ENTITY_SUB_TYPE,ENTITY_DESC,AG.AGENCY_NAME,G.DESCRIPTION,CASE WHEN V.REC_STATUS = 'F' THEN 'INITIATED' WHEN V.REC_STATUS = 'R' THEN 'CAPTURED' WHEN V.REC_STATUS = 'C' THEN 'COMPLETED' END AS REC_STATUS,G1.DESCRIPTION,F.APPRAISAL_DATE,F.FV_STATUS,F.FV_REMARKS FROM cr_deal_verification_dtl V ");
			bufInsSql.append(" INNER JOIN cr_field_verification_dtl F ON  F.VERIFICATION_ID =V.VERIFICATION_ID  ");
			bufInsSql.append(" LEFT JOIN sec_user_m U ON  U.USER_ID =V.INTERNAL_APPRAISER  ");
	        bufInsSql.append(" LEFT JOIN generic_master G ON G.GENERIC_KEY='ADDRESS_TYPE' AND  G.VALUE=V.ADDRESS_TYPE  ");
	        bufInsSql.append(" LEFT JOIN generic_master G1 ON G1.GENERIC_KEY='CUST_CONSTITUTION' AND  G1.VALUE=V.ENTITY_SUB_TYPE  ");
			bufInsSql.append(" left join com_agency_m ag on V.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE ");
			bufInsSql.append(" WHERE V.VERIFICATION_ACTION = 'I' AND V.STAGE='DC' AND V.DEAL_ID= '"+dealId+"' ");
			
			logger.info("IN showVerificationListCompletionModule()  query1 ### "+ bufInsSql.toString());
			searchList = ConnectionDAO.sqlSelect(bufInsSql.toString());
			bufInsSql=null;

			for (int i = 0; i < searchList.size(); i++) {
			
				ArrayList data = (ArrayList) searchList.get(i);

				if (data.size() > 0) {
					
					 deptMVO = new FieldVerificationVo();
					
					deptMVO.setVerificationMappingId(CommonFunction.checkNull(data.get(0)));
					
					deptMVO.setVerificationType(CommonFunction.checkNull(data.get(1)));
					
					 deptMVO.setAppraiserType(CommonFunction.checkNull(data.get(2)));
					
					 deptMVO.setInternalAppraiser(CommonFunction.checkNull(data.get(3)));
					 deptMVO.setExternalAppraiser(CommonFunction.checkNull(data.get(3)));
					 			 
					 deptMVO.setVerificationSubType(CommonFunction.checkNull(data.get(4)));
					 
					 deptMVO.setActionValue(CommonFunction.checkNull(data.get(5)));
					 
					 deptMVO.setEntId(CommonFunction.checkNull(data.get(6)));
					 
					 if(CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("PRAPPL"))
					 {
						 deptMVO.setEntityType("APPLICANT");
					 }
					 else if(CommonFunction.checkNull(data.get(7)).equalsIgnoreCase("COAPPL"))
					 {
						 deptMVO.setEntityType("COAPPLICANT");
					 }
					 else
					 {
						 deptMVO.setEntityType(CommonFunction.checkNull(data.get(7)));
					 }
					 			 				 
					 if(CommonFunction.checkNull(data.get(8)).equalsIgnoreCase("PRAPPL"))
						{
							deptMVO.setEntitySubType("APPLICANT");
						}
						else if(CommonFunction.checkNull(data.get(8)).equalsIgnoreCase("COAPPL"))
						{
							deptMVO.setEntitySubType("COAPPLICANT");
						}
						else if(CommonFunction.checkNull(data.get(8)).equalsIgnoreCase("APPL"))
						{
							deptMVO.setEntitySubType("APPLICATION");
						}
						else 
						{
							deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(8)));
						}
								
					 deptMVO.setEntityDesc(CommonFunction.checkNull(data.get(9)));
					 deptMVO.setAgencyName(CommonFunction.checkNull(data.get(10)));
	                 deptMVO.setAddressTypeDesc(CommonFunction.checkNull(data.get(11)));
	                 deptMVO.setStatus(CommonFunction.checkNull(data.get(12)));
	                 
	                if(CommonFunction.checkNull(data.get(1)).equalsIgnoreCase("CONSTITUTION VERIFICATION"))
	 				 {
	 					deptMVO.setEntitySubType(CommonFunction.checkNull(data.get(13)));
	 				 }
	                 deptMVO.setAppraiserDate(CommonFunction.checkNull(data.get(14)));
	                 deptMVO.setVerfDecision(CommonFunction.checkNull(data.get(15)));
	                 deptMVO.setAppraiserRemarks(CommonFunction.checkNull(data.get(16)));
					 detailList.add(deptMVO);
				}
				data=null;

			}
			searchList=null;
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dealId=null;
			deptMVO=null;
		}

		return detailList;
	}
 
 public boolean verifCompletionModule(FieldVerificationVo fieldVo) {
		
		
	 boolean status=false;
		ArrayList qryList = new ArrayList();
				
		StringBuffer bufInsSql = null;
		PrepStmtObject insertPrepStmtObject = null;
		
		String deleteQuery=null;
		try {
		
				 insertPrepStmtObject = new PrepStmtObject();
				 bufInsSql = new StringBuffer();
				 bufInsSql.append(" UPDATE cr_deal_verification_dtl  SET AUTHOR_REMARKS=?," );
				 bufInsSql.append(" rec_status='C',AUTHOR_ID=?,AUTHOR_DATE=" );
				 bufInsSql.append(dbo);
				 bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				
				 bufInsSql.append(" WHERE STAGE='DC' AND  DEAL_ID=?");  
				 
				if (CommonFunction.checkNull(fieldVo.getTextarea()).trim().equalsIgnoreCase("") )
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(fieldVo.getTextarea()).trim());
								
			
				if (CommonFunction.checkNull(fieldVo.getMakerId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerId().trim());

				if (CommonFunction.checkNull(fieldVo.getMakerDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(fieldVo.getMakerDate().trim());

				if (CommonFunction.checkNull(fieldVo.getDealId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((fieldVo.getDealId()).trim());
			
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
				logger.info("IN insertfieldVerification() insert query1 ### "+ insertPrepStmtObject.printQuery());
			
				
						insertPrepStmtObject = new PrepStmtObject();			
						logger.info("In insert Field Verification Capturing");
						
						bufInsSql = new StringBuffer();
							
						bufInsSql.append(" UPDATE CR_FIELD_VERIFICATION_DTL SET  rec_status=? WHERE STAGE='DC' AND  DEAL_ID=? " );

						insertPrepStmtObject.addString("C");
						
						if (CommonFunction.checkNull(fieldVo.getDealId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(fieldVo.getDealId().trim());
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN insertFieldVerCapture() insert query1 ### "+ insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);				
			     
					
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			 fieldVo=null;
			 qryList=null;
			 insertPrepStmtObject=null;
			 bufInsSql=null;
			
	
		}

		return status;
	 
	}
@Override
public ArrayList getBalanceDay(String productId, String business,String schemeID) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public ArrayList getCompletionList(String dealId, String functionId) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public ArrayList getCompletionListAtCM(String loanId) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public ArrayList getReceivedDocs(String dealId, String bpType) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public ArrayList getVerifMethodListRCU() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public boolean updateRCUDocs(String hidRcuStatusStringValue,
		String hidRcuCommentStringValue, String hidDOCIDString,
		String hidRcuChildIDStringValue, FieldVerificationVo vo) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public ArrayList getReceivedDocsSaved(String dealId, String bpType) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public ArrayList getReceivedDocsAtLoan(String loanId, String bpType) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public ArrayList getReceivedDocsSavedAtLoan(String loanId, String bpType) {
	// TODO Auto-generated method stub
	return null;
}

}
