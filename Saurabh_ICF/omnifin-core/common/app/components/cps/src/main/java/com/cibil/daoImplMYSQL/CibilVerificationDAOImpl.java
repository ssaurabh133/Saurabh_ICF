package com.cibil.daoImplMYSQL;
import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.cp.vo.CreditProcessingCustomerEntryVo;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.Replace;

import Decoder.BASE64Decoder;



import com.VO.ConstitutionVO;
import com.cibil.dao.CibilVerificationDAO;
import com.cibil.vo.CibilVerificationVO;
import com.commonFunction.vo.CustomerSaveVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.CreditProcessingCustomerEntryVo;
import com.gcd.actions.CustomerAddressAction;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpMessage;
import org.apache.http.impl.client.HttpClientBuilder;

import com.cibil.vo.CrifVO;

import org.json.*;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.codec.binary.StringUtils;

public class CibilVerificationDAOImpl implements CibilVerificationDAO 
{
	private static final Logger logger = Logger.getLogger(CibilVerificationDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	@Override
	public ArrayList getCVGridList(CibilVerificationVO vo) 
	{
		logger.info("in getCVGridList() ");
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList formatlist=null;
		ArrayList data=null;
		ArrayList resultList=new ArrayList();		
		CibilVerificationVO obj=(CibilVerificationVO)vo;
		try
		{
			bufInsSql.append("select a.deal_id,a.deal_no,c.CUSTOMER_NAME,GENDER,case b.DEAL_CUSTOMER_TYPE when 'I' then  'INDUVIDUAL' when 'C' then 'CORPORATE' end as DEAL_CUSTOMER_TYPE," +
					" case b.DEAL_CUSTOMER_ROLE_TYPE when 'PRAPPL' then 'APPLICANT' when 'GUARANTOR' then 'GUARANTOR' else 'CO-APPLICANT' end as DEAL_CUSTOMER_ROLE_TYPE," +
					" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='CIBIL_MEMBER_NAME')MEMBER_NAME,(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_ID_INQUIRY')MEMBER_ID," +
					" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='ENQUIRY-PURPOSE')ENQUIRY_PURPOSE,c.CUSTOMER_ID REF_NO,date_format(C.CUSTOMER_DOB,'%d-%m-%Y')," +
					" d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,d.ADDRESS_TYPE,d.ADDRESS_DETAIL,e.DISTRICT_DESC,f.STATE_DESC,d.PINCODE,g.COUNTRY_DESC,c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_NO," +
					" voter_id,passport_number,c.DRIVING_LICENSE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,ifnull(CIBIL_DONE,'N')CIBIL_DONE,c.UID_NO, ifnull(c.CIBIL_ID,0)CIBIL_ID,ifnull(cm.score,''),ifnull(cm.CRIF_HIGH_MARK_SCORE,'')" +
					" from cr_deal_dtl a " +
					" join CR_DEAL_CUSTOMER_ROLE b on(a.DEAL_ID=b.DEAL_ID)" +
					" join CR_DEAL_CUSTOMER_M c on(b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID)" +
					" left join CR_CIBIL_SCORE_SEGMENT cm on cm.CIBIL_ID=c.CIBIL_ID "+
					" join CR_DEAL_ADDRESS_M D ON(D.BPID=b.DEAL_CUSTOMER_ID AND D.COMMUNICATION_ADDRESS='Y'  and D.BPTYPE='CS' )" +
					" left join com_district_m e on(e.DISTRICT_ID=d.DISTRICT)" +
					" left join COM_STATE_M f on(f.STATE_ID=d.STATE)" +
					" left join com_country_m g on(g.COUNTRY_ID=d.COUNTRY)" +
					" where true " );
			logger.info("Lead Id-------------------------- obj.getlbxLeadId "+obj.getLbxLeadId()+"    ffffffffffffff "+obj.getLeadID());
			if(!CommonFunction.checkNull(obj.getDealID()).equalsIgnoreCase(""))
				bufInsSql.append(" and a.deal_id='"+obj.getDealID()+"' ");
			if(!CommonFunction.checkNull(obj.getLbxLeadId()).equalsIgnoreCase(""))
				bufInsSql.append(" and a.lead_id='"+obj.getLbxLeadId()+"' ");
			
			 logger.info("in getCVGridList() Search Query  :  "+bufInsSql.toString());
			
			  formatlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
              for (int i=0;i<formatlist.size();i++)
              {
					data = (ArrayList) formatlist.get(i);
					CibilVerificationVO dvo= new CibilVerificationVO(); 
					dvo.setDealID(CommonFunction.checkNull(data.get(0)).toString());
					dvo.setDealNO(CommonFunction.checkNull(data.get(1)).toString());
					dvo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
					dvo.setCustomerGender(CommonFunction.checkNull(data.get(3)).toString());
					dvo.setCustomerType(CommonFunction.checkNull(data.get(4)).toString());
					dvo.setCustomerRole(CommonFunction.checkNull(data.get(5)).toString());
					dvo.setMemberName(CommonFunction.checkNull(data.get(6)).toString());
					dvo.setMemberId(CommonFunction.checkNull(data.get(7)).toString());
					dvo.setInquiryPurpus(CommonFunction.checkNull(data.get(8)).toString());
					dvo.setRefranceNo(CommonFunction.checkNull(data.get(9)).toString());
					dvo.setCustomerDob(CommonFunction.checkNull(data.get(10)).toString());
					dvo.setAddress1(CommonFunction.checkNull(data.get(11)).toString());
					dvo.setAddress2(CommonFunction.checkNull(data.get(12)).toString());
					dvo.setAddress3(CommonFunction.checkNull(data.get(13)).toString());
					dvo.setAddressType(CommonFunction.checkNull(data.get(14)).toString());
					dvo.setAddressDetail(CommonFunction.checkNull(data.get(15)).toString());
					dvo.setDistict(CommonFunction.checkNull(data.get(16)).toString());
					dvo.setState(CommonFunction.checkNull(data.get(17)).toString());
					dvo.setPincode(CommonFunction.checkNull(data.get(18)).toString());
					dvo.setCountry(CommonFunction.checkNull(data.get(19)).toString());
					dvo.setPanNo(CommonFunction.checkNull(data.get(20)).toString());
					dvo.setRegNo(CommonFunction.checkNull(data.get(21)).toString());
					dvo.setVoterId(CommonFunction.checkNull(data.get(22)).toString());
					dvo.setPassport(CommonFunction.checkNull(data.get(23)).toString());
					dvo.setDrivingLicense(CommonFunction.checkNull(data.get(24)).toString());
					dvo.setPrimaryPhone(CommonFunction.checkNull(data.get(25)).toString());
					dvo.setAlterPhone(CommonFunction.checkNull(data.get(26)).toString());
					dvo.setTollPhone(CommonFunction.checkNull(data.get(27)).toString());
					dvo.setFaxNo(CommonFunction.checkNull(data.get(28)).toString());
					dvo.setCibilDone(CommonFunction.checkNull(data.get(29)).toString());
					dvo.setUid(CommonFunction.checkNull(data.get(30)).toString());
					dvo.setLbxCibilId(CommonFunction.checkNull(data.get(31)).toString());
					dvo.setCibilScore(CommonFunction.checkNull(data.get(32)).toString());
					dvo.setCrifHighMarkScore(CommonFunction.checkNull(data.get(33)).toString());
					resultList.add(dvo);
					dvo=null;
					data.clear();
					data=null;					
			  }
        }
		catch (Exception e) 
		{e.printStackTrace();}		
		return resultList;
	}
	
	@Override
	public ArrayList getCblViewGridList(CibilVerificationVO vo) {

		logger.info("in getCblViewGridList() ");
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList formatlist=null;
		ArrayList data=null;
		ArrayList resultList=new ArrayList();		
		CibilVerificationVO obj=(CibilVerificationVO)vo;
		try
		{
			bufInsSql.append("select a.deal_id,a.deal_no,c.CUSTOMER_NAME,CB.GENDER,case b.DEAL_CUSTOMER_TYPE when 'I' then  'INDUVIDUAL' when 'C' then 'CORPORATE' end as DEAL_CUSTOMER_TYPE," +
					" case b.DEAL_CUSTOMER_ROLE_TYPE when 'PRAPPL' then 'APPLICANT' when 'GUARANTOR' then 'GUARANTOR' else 'CO-APPLICANT' end as DEAL_CUSTOMER_ROLE_TYPE," +
					" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='CIBIL_MEMBER_NAME')MEMBER_NAME,(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_ID_INQUIRY')MEMBER_ID," +
					" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='ENQUIRY-PURPOSE')ENQUIRY_PURPOSE,CB.CUSTOMER_ID REF_NO,date_format(C.CUSTOMER_DOB,'%d-%m-%Y')," +
					" d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,d.ADDRESS_TYPE,d.ADDRESS_DETAIL,e.DISTRICT_DESC,f.STATE_DESC,d.PINCODE,g.COUNTRY_DESC,c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_NO," +
					" CB.voter_id,passport_number,c.DRIVING_LICENSE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX " +
					" ,ifnull(CIBIL_ID,0)CIBIL_ID, CASE CIBIL_RESULT WHEN 'E' THEN 'ERROR RESPONSE' WHEN 'S' THEN 'POSITIVE RESPONSE' WHEN 'I' THEN 'INVALID RESPONSE' WHEN 'N' THEN 'NO RESPONSE' END AS CIBIL_RESULT,date_format(CB.CIBIL_DATE,'%d-%m-%Y'),c.UID_NO  "+
					" from cr_deal_dtl a " +
					" join CR_CIBIL_REF_DTL CB on(a.DEAL_ID=CB.DEAL_ID)" +					
					" join CR_DEAL_CUSTOMER_ROLE b on(a.DEAL_ID=b.DEAL_ID and b.DEAL_CUSTOMER_ID=CB.CUSTOMER_ID)" +
					" join CR_DEAL_CUSTOMER_M c on(b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID)" +
					" join CR_DEAL_ADDRESS_M D ON(D.BPID=b.DEAL_CUSTOMER_ID AND D.COMMUNICATION_ADDRESS='Y'  and D.BPTYPE='CS' )" +
					" left join com_district_m e on(e.DISTRICT_ID=d.DISTRICT)" +
					" left join COM_STATE_M f on(f.STATE_ID=d.STATE)" +
					" left join com_country_m g on(g.COUNTRY_ID=d.COUNTRY)" +
					" where a.deal_id='"+obj.getDealID()+"' ");
			 logger.info("in getCblViewGridList() Search Query  :  "+bufInsSql.toString());
			
			  formatlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
              for (int i=0;i<formatlist.size();i++)
              {
					data = (ArrayList) formatlist.get(i);
					CibilVerificationVO dvo= new CibilVerificationVO(); 
					dvo.setDealID(CommonFunction.checkNull(data.get(0)).toString());
					dvo.setDealNO(CommonFunction.checkNull(data.get(1)).toString());
					dvo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
					dvo.setCustomerGender(CommonFunction.checkNull(data.get(3)).toString());
					dvo.setCustomerType(CommonFunction.checkNull(data.get(4)).toString());
					dvo.setCustomerRole(CommonFunction.checkNull(data.get(5)).toString());
					dvo.setMemberName(CommonFunction.checkNull(data.get(6)).toString());
					dvo.setMemberId(CommonFunction.checkNull(data.get(7)).toString());
					dvo.setInquiryPurpus(CommonFunction.checkNull(data.get(8)).toString());
					dvo.setRefranceNo(CommonFunction.checkNull(data.get(9)).toString());
					dvo.setCustomerDob(CommonFunction.checkNull(data.get(10)).toString());
					dvo.setAddress1(CommonFunction.checkNull(data.get(11)).toString());
					dvo.setAddress2(CommonFunction.checkNull(data.get(12)).toString());
					dvo.setAddress3(CommonFunction.checkNull(data.get(13)).toString());
					dvo.setAddressType(CommonFunction.checkNull(data.get(14)).toString());
					dvo.setAddressDetail(CommonFunction.checkNull(data.get(15)).toString());
					dvo.setDistict(CommonFunction.checkNull(data.get(16)).toString());
					dvo.setState(CommonFunction.checkNull(data.get(17)).toString());
					dvo.setPincode(CommonFunction.checkNull(data.get(18)).toString());
					dvo.setCountry(CommonFunction.checkNull(data.get(19)).toString());
					dvo.setPanNo(CommonFunction.checkNull(data.get(20)).toString());
					dvo.setRegNo(CommonFunction.checkNull(data.get(21)).toString());
					dvo.setVoterId(CommonFunction.checkNull(data.get(22)).toString());
					dvo.setPassport(CommonFunction.checkNull(data.get(23)).toString());
					dvo.setDrivingLicense(CommonFunction.checkNull(data.get(24)).toString());
					dvo.setPrimaryPhone(CommonFunction.checkNull(data.get(25)).toString());
					dvo.setAlterPhone(CommonFunction.checkNull(data.get(26)).toString());
					dvo.setTollPhone(CommonFunction.checkNull(data.get(27)).toString());
					dvo.setFaxNo(CommonFunction.checkNull(data.get(28)).toString());					
					dvo.setLbxCibilId(CommonFunction.checkNull(data.get(29)).toString());
					dvo.setCibilResponse(CommonFunction.checkNull(data.get(30)).toString());
					dvo.setCibilDate(CommonFunction.checkNull(data.get(31)).toString());
					dvo.setUid(CommonFunction.checkNull(data.get(32)).toString());
					resultList.add(dvo);
					dvo=null;
					data.clear();
					data=null;					
			  }
        }
		catch (Exception e) 
		{e.printStackTrace();}		
		return resultList;
	
	}
	
	
	
	@Override
	public ArrayList getCblViewCustomerGridList(CibilVerificationVO vo) {
		logger.info("in getCblViewCustomerGridList() ");
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList formatlist=null;
		ArrayList data=null;
		ArrayList resultList=new ArrayList();		
		CibilVerificationVO obj=(CibilVerificationVO)vo;
		try
		{
			bufInsSql.append("select d.deal_id, deal_no,d.DEAL_CUSTOMER_ID,deal.CIBIL_DONE,deal.CUSTOMER_NAME,CUSTOMER_TYPE,DATE_FORMAT(deal_date,'%d-%m-%Y'),p.PRODUCT_DESC,"+
					"s.SCHEME_DESC,l.DEAL_PRODUCT_CATEGORY,d.REC_STATUS,S.MIN_AMT_FIN,S.MAX_AMT_FIN  from cr_deal_dtl d "+
					" left join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID left join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID "+
					 "left join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID "+
					"left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "+ 
										" where d.deal_id='"+obj.getDealID()+"' ");
			 logger.info("in getCblViewGridList() Search Query  :  "+bufInsSql.toString());
			
			  formatlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
              for (int i=0;i<formatlist.size();i++)
              {
					data = (ArrayList) formatlist.get(i);
					CibilVerificationVO dvo= new CibilVerificationVO(); 
					dvo.setDealID(CommonFunction.checkNull(data.get(0)).toString());
					dvo.setDealNO(CommonFunction.checkNull(data.get(1)).toString());
					dvo.setCustomerId(CommonFunction.checkNull(data.get(2)).toString());
					dvo.setCibilDone(CommonFunction.checkNull(data.get(3)).toString());
					dvo.setCustomerName(CommonFunction.checkNull(data.get(4)).toString());
					dvo.setCustomerType(CommonFunction.checkNull(data.get(5)).toString());
					dvo.setDealdate(CommonFunction.checkNull(data.get(6)).toString());
					dvo.setProductDesc(CommonFunction.checkNull(data.get(7)).toString());
					dvo.setSchemeDesc(CommonFunction.checkNull(data.get(8)).toString());
					dvo.setDealProductCategory(CommonFunction.checkNull(data.get(9)).toString());
					dvo.setRecStatus(CommonFunction.checkNull(data.get(10)).toString());
					//dvo.setRefranceNo(CommonFunction.checkNull(data.get(9)).toString());
					dvo.setMinAmtFin(CommonFunction.checkNull(data.get(11)).toString());
					dvo.setMaxAmtFin(CommonFunction.checkNull(data.get(12)).toString());
					//dvo.setCibilDone(CommonFunction.checkNull(data.get(12)).toString());
				/*	dvo.setAddress1(CommonFunction.checkNull(data.get(11)).toString());
					dvo.setAddress2(CommonFunction.checkNull(data.get(12)).toString());
					dvo.setAddress3(CommonFunction.checkNull(data.get(13)).toString());
					dvo.setAddressType(CommonFunction.checkNull(data.get(14)).toString());
					dvo.setAddressDetail(CommonFunction.checkNull(data.get(15)).toString());
					dvo.setDistict(CommonFunction.checkNull(data.get(16)).toString());
					dvo.setState(CommonFunction.checkNull(data.get(17)).toString());
					dvo.setPincode(CommonFunction.checkNull(data.get(18)).toString());
					dvo.setCountry(CommonFunction.checkNull(data.get(19)).toString());
					dvo.setPanNo(CommonFunction.checkNull(data.get(20)).toString());
					dvo.setRegNo(CommonFunction.checkNull(data.get(21)).toString());
					dvo.setVoterId(CommonFunction.checkNull(data.get(22)).toString());
					dvo.setPassport(CommonFunction.checkNull(data.get(23)).toString());
					dvo.setDrivingLicense(CommonFunction.checkNull(data.get(24)).toString());
					dvo.setPrimaryPhone(CommonFunction.checkNull(data.get(25)).toString());
					dvo.setAlterPhone(CommonFunction.checkNull(data.get(26)).toString());
					dvo.setTollPhone(CommonFunction.checkNull(data.get(27)).toString());
					dvo.setFaxNo(CommonFunction.checkNull(data.get(28)).toString());					
					dvo.setLbxCibilId(CommonFunction.checkNull(data.get(29)).toString());
					dvo.setCibilResponse(CommonFunction.checkNull(data.get(30)).toString());
					dvo.setCibilDate(CommonFunction.checkNull(data.get(31)).toString());*/
					resultList.add(dvo);
					dvo=null;
					data.clear();
					data=null;					
			  }
        }
		catch (Exception e) 
		{e.printStackTrace();}		
		return resultList;
	
	}

	public ArrayList SaveRecord(String dealID, int customerId,String businessDate)  
	{
		boolean status=false;
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList formatlist=null;
		ArrayList data=null;
		ArrayList resultList=new ArrayList();
		ArrayList qryList=new ArrayList();	
		ArrayList responseList=new ArrayList();			
		PrepStmtObject insertPrepStmtObject=null;
		
		String strBatchId="0";
		int batchId=0;
	   /* String sqlQuery="SELECT MAX(BATCH_ID) FROM CR_CIBIL_REF_DTL";*/
		String sqlQuery="select function_sequence('CIBIL_BATCH_SEQ')";
	    strBatchId=CommonFunction.checkNull(ConnectionDAO.singleReturn(sqlQuery)).trim();
	    if(CommonFunction.checkNull(strBatchId).trim().equalsIgnoreCase(""))
	    	strBatchId="0";
	    batchId=Integer.parseInt(strBatchId);
	   // batchId=batchId+1;
	    if(strBatchId.equalsIgnoreCase("0")){
	    	String sqlQuery1="select max(BATCH_ID) from CR_CIBIL_REF_DTL ";
		    strBatchId=CommonFunction.checkNull(ConnectionDAO.singleReturn(sqlQuery1)).trim();
		    batchId=Integer.parseInt(strBatchId);
		    batchId=batchId+1;
	    }
	 /*   if(strBatchId.equalsIgnoreCase("0")){
	    	String sqlQuery1="select max(BATCH_ID) from CR_CIBIL_REF_DTL ";
		    strBatchId=CommonFunction.checkNull(ConnectionDAO.singleReturn(sqlQuery1)).trim();
		    batchId=Integer.parseInt(strBatchId);
		    batchId=batchId+1;
	    }*/
	    
	    try
		{
			bufInsSql.append("select b.DEAL_CUSTOMER_ID,(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='CIBIL_MEMBER_NAME')MEMBER_NAME," +
					"(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_ID_INQUIRY')MEMBER_ID, " +
					"(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='ENQUIRY-PURPOSE')ENQUIRY_PURPOSE,  b.DEAL_CUSTOMER_ID," +
					"c.CUSTOMER_NAME,GENDER,date_format(C.CUSTOMER_DOB,'%d-%m-%Y')CUSTOMER_DOB,d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,ADDRESS_TYPE,ADDRESS_DETAIL,e.DISTRICT_DESC,f.CIBIL_STATE_ID,d.PINCODE," +
					"g.COUNTRY_DESC,c.CUSTMER_PAN,voter_id,passport_number,c.DRIVING_LICENSE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,A.DEAL_ID,UID_NO " +
					"from cr_deal_dtl a  " +
					"join CR_DEAL_CUSTOMER_ROLE b on(a.DEAL_ID=b.DEAL_ID) " +
					"join CR_DEAL_CUSTOMER_M c on(b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID) " +
					"join CR_DEAL_ADDRESS_M D ON(D.BPID=b.DEAL_CUSTOMER_ID AND D.COMMUNICATION_ADDRESS='Y'  and D.BPTYPE='CS' ) " +
					"left join com_district_m e on(e.DISTRICT_ID=d.DISTRICT) " +
					"left join COM_STATE_M f on(f.STATE_ID=d.STATE) " +
					"left join com_country_m g on(g.COUNTRY_ID=d.COUNTRY) " +
					"where a.deal_id='"+CommonFunction.checkNull(dealID)+"' and c.CUSTOMER_ID in("+customerId+")");
			 logger.info("in SaveRecord() fetch record Query  :  "+bufInsSql.toString());			
			 formatlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			 bufInsSql=null;
			 for (int i=0;i<formatlist.size();i++)
             {
				 data = (ArrayList) formatlist.get(i);
				 bufInsSql =new StringBuilder();					
				 bufInsSql.append("INSERT INTO CR_CIBIL_REF_DTL (BATCH_ID,CUSTOMER_ID,MEMBER_NAME,MEMBER_ID,ENQUIRY_PURPOSE,REFERENCE_NUMBER,NAME,GENDER,DATE_OF_BIRTH," +
						 "ADDRESS_1,ADDRESS_2,ADDRESS_3,ADDRESS_TYPE,ADDRESS_DETAIL,DISTRICT,STATE,PINCODE,COUNTRY,PAN_NO,VOTER_ID,PASSPORT_NO,DRIVING_LICENCE_NO," +
				 " MOBILE_NO1,TELEPHONE_NO1,TELEPHONE_NO2,TELEPHONE_NO3,CIBIL_DATE,CIBIL_RESULT,REC_STATUS,REPORT_STATUS,DEAL_ID,UID_NO)");
				 bufInsSql.append(" values ( ");
				 bufInsSql.append(" ?," );//BATCH_ID
				 bufInsSql.append(" ?," );//CUSTOMER_ID
				 bufInsSql.append(" ?," );//MEMBER_NAME
				 bufInsSql.append(" ?," );//MEMBER_ID				
				 bufInsSql.append(" ?," );//ENQUIRY_PURPOSE 
				 bufInsSql.append(" ?," );//REFERENCE_NUMBER
				 bufInsSql.append(" ?," );//NAME
				 bufInsSql.append(" ?," );//GENDER
				 bufInsSql.append(" STR_TO_DATE(?, '"+dateFormat+"')," );//DATE_OF_BIRTH				 
				 bufInsSql.append(" ?," );//ADDRESS_1
				 bufInsSql.append(" ?," );//ADDRESS_2
				 bufInsSql.append(" ?," );//ADDRESS_3
				 bufInsSql.append(" ?," );//ADDRESS_TYPE
				 bufInsSql.append(" ?," );//ADDRESS_DETAIL
				 bufInsSql.append(" ?," );//DISTRICT
				 bufInsSql.append(" ?," );//STATE
				 bufInsSql.append(" ?," );//PINCODE
				 bufInsSql.append(" ?," );//COUNTRY
				 bufInsSql.append(" ?," );//PAN_NO     
				 bufInsSql.append(" ?," );//VOTER_ID
				 bufInsSql.append(" ?," );//PASSPORT_NO
				 bufInsSql.append(" ?," );//DRIVING_LICENCE_NO				 
				 bufInsSql.append(" ?," );//MOBILE_NO1
				 bufInsSql.append(" ?," );//TELEPHONE_NO1
				 bufInsSql.append(" ?," );//TELEPHONE_NO2
				 bufInsSql.append(" ?," );//TELEPHONE_NO3
				 //bufInsSql.append(dbo);
				 //bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as varchar(30))),0,9),");//CIBIL_DATE
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') ,"); //CIBIL_DATE
				 bufInsSql.append(" ?," );//CIBIL_RESULT
				 bufInsSql.append(" ?," );//REC_STATUS
				 bufInsSql.append(" ?," );//REPORT_STATUS
				 bufInsSql.append(" ?," );//DEAL_ID
				 bufInsSql.append(" ?)" );//UID NO
				// bufInsSql.append(" ?," );//Plot No
				// bufInsSql.append(" ?," );//FLOOR No	
					 	
				 insertPrepStmtObject = new PrepStmtObject();
				 insertPrepStmtObject.addInt(batchId);
				 if(CommonFunction.checkNull(data.get(0)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
				 {
					 String custId=CommonFunction.checkNull(data.get(0)).trim();
					 if(custId.equalsIgnoreCase(""))
						 custId="0";
					 insertPrepStmtObject.addInt(Integer.parseInt(custId));
					 
				 }									 
				 if(CommonFunction.checkNull(data.get(1)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(1)).trim());
				 
				 if(CommonFunction.checkNull(data.get(2)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(2)).trim());
				 
				 if(CommonFunction.checkNull(data.get(3)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(3)).trim());
				 
				 if(CommonFunction.checkNull(data.get(4)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(4)).trim());
				 
				 if(CommonFunction.checkNull(data.get(5)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(5)).trim());
				 
				 if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(6)).trim());
				 
				 if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(7)).trim());
				 
				 if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(8)).trim());
				
				 if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(9)).trim());
				 
				 if(CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(10)).trim());
				 if(CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(11)).trim());
				 
				 if(CommonFunction.checkNull(data.get(12)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(12)).trim());
				 
				 if(CommonFunction.checkNull(data.get(13)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(13)).trim());
				 
				 if(CommonFunction.checkNull(data.get(14)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(14)).trim());
				 
				 if(CommonFunction.checkNull(data.get(15)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(15)).trim());
				 
				 if(CommonFunction.checkNull(data.get(16)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(16)).trim());
				 
				 if(CommonFunction.checkNull(data.get(17)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(17)).trim());
				 
				 if(CommonFunction.checkNull(data.get(18)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(18)).trim());
				 
				 if(CommonFunction.checkNull(data.get(19)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(19)).trim());
				 
				 if(CommonFunction.checkNull(data.get(20)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(20)).trim());
				 
				 if(CommonFunction.checkNull(data.get(21)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(21)).trim());
				 
				 if(CommonFunction.checkNull(data.get(22)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(22)).trim());
				 
				 if(CommonFunction.checkNull(data.get(23)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(23)).trim());
				 
				 if(CommonFunction.checkNull(data.get(24)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(24)).trim());
			
				 
				 insertPrepStmtObject.addString(businessDate);
				 insertPrepStmtObject.addString("N");			 
				 insertPrepStmtObject.addString("F");
				 insertPrepStmtObject.addString("N");
				 
				 if(CommonFunction.checkNull(data.get(25)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(25)).trim());
				 
				 if(CommonFunction.checkNull(data.get(26)).trim().equalsIgnoreCase(""))
					 insertPrepStmtObject.addNull();
				 else
					 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(26)).trim());
				 
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN SaveRecord() insert query("+i+1+")  :  "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				insertPrepStmtObject=null;
				bufInsSql=null;
				data.clear();
				data=null;
             }//end of loop
			 try
			 {
				 status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("In SaveRecord.........insert status: "+status);
					
					// Rajnish start
/*					String cibilProvider = getCibilProvider();
					if(CommonFunction.checkNull(cibilProvider).equalsIgnoreCase("A3S")){*/
						if (status) {
							String resString="SELECT CIBIL_ID FROM CR_CIBIL_REF_DTL WHERE BATCH_ID="+batchId;
				          	logger.info("in SaveRecord() getresponse String query  :  "+resString);			
							 responseList = ConnectionDAO.sqlSelect(resString);
							/*logger.info("Call Procedure after save.");
							ArrayList<Object> in =new ArrayList<Object>();
							ArrayList<Object> out =new ArrayList<Object>();
							ArrayList outMessages = new ArrayList();
							ArrayList result = new ArrayList();
							String s1="";
							String s2="";
							String s3="";
							String error="";
							try
							{
								in.add(batchId);
								out.add(s1);
								out.add(s2);
								
								logger.info("CIBIL_INPUTSTRING_GENERATOR ("+in.toString()+","+out.toString()+")");
								outMessages=(ArrayList) ConnectionDAO.callSP("CIBIL_INPUTSTRING_GENERATOR",in,out);
								s1=CommonFunction.checkNull(outMessages.get(0));
								s2=CommonFunction.checkNull(outMessages.get(1));
								logger.info("CIBIL_INPUTSTRING_GENERATOR  s1  : "+s1);
						        logger.info("CIBIL_INPUTSTRING_GENERATOR  s2  : "+s2);
						        if(CommonFunction.checkNull(s1).trim().equalsIgnoreCase("S"))
						        {
						          	String resString="SELECT CIBIL_ID,FORWORD_STRING FROM CR_CIBIL_REF_DTL WHERE BATCH_ID="+batchId;
						          	logger.info("in SaveRecord() getresponse String query  :  "+resString);			
									 responseList = ConnectionDAO.sqlSelect(resString);
						        	
						        }
						    }
							catch (Exception e) 
							{
								batchId=0;
						        e.printStackTrace();
						    }
							finally
							{
								in=null;
								out =null;
								outMessages=null;
								s1=null;
								s2=null;
								s3=null;
								error=null;			
							}
						}
					}else{*/
						updateCibilRecStatus(batchId);
					}
			 }
			 catch(Exception e)
			 {
				 batchId=0;
				 e.printStackTrace();
			 }	
		}
					/*status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("In SaveRecord.........insert status: "+status);
					// Rajnish start
					String cibilProvider = getCibilProvider();
					if(CommonFunction.checkNull(cibilProvider).equalsIgnoreCase("A3S")){
						if (status) {
							logger.info("Call Procedure after save.");
							ArrayList<Object> in =new ArrayList<Object>();
							ArrayList<Object> out =new ArrayList<Object>();
							ArrayList outMessages = new ArrayList();
							ArrayList result = new ArrayList();
							String s1="";
							String s2="";
							String s3="";
							String error="";
							try
							{
								in.add(batchId);
								out.add(s1);
								out.add(s2);
								
								logger.info("CIBIL_INPUTSTRING_GENERATOR ("+in.toString()+","+out.toString()+")");
								outMessages=(ArrayList) ConnectionDAO.callSP("CIBIL_INPUTSTRING_GENERATOR",in,out);
								s1=CommonFunction.checkNull(outMessages.get(0));
								s2=CommonFunction.checkNull(outMessages.get(1));
								logger.info("CIBIL_INPUTSTRING_GENERATOR  s1  : "+s1);
						        logger.info("CIBIL_INPUTSTRING_GENERATOR  s2  : "+s2);
						        if(CommonFunction.checkNull(s1).trim().equalsIgnoreCase("S"))
						        {
						          	String resString="SELECT CIBIL_ID,FORWORD_STRING FROM CR_CIBIL_REF_DTL WHERE BATCH_ID="+batchId;
						          	logger.info("in SaveRecord() getresponse String query  :  "+resString);			
									 responseList = ConnectionDAO.sqlSelect(resString);
						        	
						        }
						    }
							catch (Exception e) 
							{
								batchId=0;
						        e.printStackTrace();
						    }
							finally
							{
								in=null;
								out =null;
								outMessages=null;
								s1=null;
								s2=null;
								s3=null;
								error=null;			
							}
						}
					}else{
						updateCibilRecStatus(batchId);
					}
			 }*/
			 catch(Exception e)
			 {
				 batchId=0;
				 e.printStackTrace();
			 }	
		

		
	
		finally
		{
			bufInsSql=null;
			formatlist=null;
			data=null;
			resultList.clear();
			resultList=null;
			qryList.clear();
			qryList=null;
			insertPrepStmtObject=null;
		}
		return responseList;
	}


			/* {
					status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("In SaveRecord.........insert status: "+status);
					if(status)
					{
						String resString="SELECT CIBIL_ID FROM CR_CIBIL_REF_DTL WHERE BATCH_ID="+batchId;
				        logger.info("in SaveRecord() getresponse String query  :  "+resString);			
						responseList = ConnectionDAO.sqlSelect(resString);*/
						/*logger.info("Call Procedure after save.");
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						ArrayList result = new ArrayList();
						String s1="";
						String s2="";
						String s3="";
						String error="";
						try
						{
							in.add(batchId);
							out.add(s1);
							out.add(s2);
							
							logger.info("CIBIL_INPUTSTRING_GENERATOR ("+in.toString()+","+out.toString()+")");
							outMessages=(ArrayList) ConnectionDAO.callSP("CIBIL_INPUTSTRING_GENERATOR",in,out);
							s1=CommonFunction.checkNull(outMessages.get(0));
							s2=CommonFunction.checkNull(outMessages.get(1));
							logger.info("CIBIL_INPUTSTRING_GENERATOR  s1  : "+s1);
					        logger.info("CIBIL_INPUTSTRING_GENERATOR  s2  : "+s2);
					        if(CommonFunction.checkNull(s1).trim().equalsIgnoreCase("S"))
					        {
					          	String resString="SELECT CIBIL_ID,FORWORD_STRING FROM CR_CIBIL_REF_DTL WHERE BATCH_ID="+batchId;
					          	logger.info("in SaveRecord() getresponse String query  :  "+resString);			
								 responseList = ConnectionDAO.sqlSelect(resString);
					        	
					        }
					    }
						catch (Exception e) 
						{
							batchId=0;
					        e.printStackTrace();
					    }
						finally
						{
							in=null;
							out =null;
							outMessages=null;
							s1=null;
							s2=null;
							s3=null;
							error=null;			
						}*/
						//updateCibilRecStatus(batchId);
			/*		}
			 }
			 catch(Exception e)
			 {
				 batchId=0;
				 e.printStackTrace();
			 }	
		}
		catch(Exception e)
		{
			 batchId=0;
			 e.printStackTrace();
		}	
		finally
		{
			bufInsSql=null;
			formatlist=null;
			data=null;
			resultList.clear();
			resultList=null;
			qryList.clear();
			qryList=null;
			insertPrepStmtObject=null;
		}
		return responseList;
	}*/

	public boolean updateResString(String cibilId, String resString) 
	{
		boolean status=false;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String header="";
		String cibilResult="";
		CibilVerificationVO cVo=new CibilVerificationVO();
		
		if(CommonFunction.checkNull(resString).trim().length()>=4)
			header=resString.substring(0, 4);
		if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("TUEF"))
			cibilResult="S";
		else if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("ERRR")) 
			cibilResult="E";
		else
			cibilResult="I";	

		resString = resString.replaceAll("\'","");

		String query="UPDATE  CR_CIBIL_REF_DTL SET CIBIL_RESULT='"+cibilResult+"',REC_STATUS='A',RECEIVED_STRING='"+CommonFunction.checkNull(resString).trim()+"' WHERE  CIBIL_ID='"+CommonFunction.checkNull(cibilId).trim()+"' ";
		insertPrepStmtObject.setSql(query);
		logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
		ArrayList qryList=new ArrayList();
		qryList.add(insertPrepStmtObject);
		try
		{
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			//Parvez Starts
			
			String cibilDone="";
			if(status)
				if(CommonFunction.checkNull(resString).trim().length()>=4)
					header=resString.substring(0, 4);
				if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("TUEF"))
				cibilDone="Y";
			else if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("ERRR")) 
				cibilDone="Y";
			else
				cibilDone="N";
			
				String selectQuery ="select customer_Id,cibil_date from cr_cibil_ref_dtl where CIBIL_ID='"+CommonFunction.checkNull(cibilId).trim()+"' ";
				
				ArrayList selectQueryValue =ConnectionDAO.sqlSelect(selectQuery);
				logger.info("Size : "+selectQueryValue.size());
				int size =selectQueryValue.size();
				for(int i=0;i<size;i++)
				{
					ArrayList CibilVerificationVO=(ArrayList)selectQueryValue.get(i);
					if(CibilVerificationVO.size()>0)
					{
						
						cVo.setCustomerId((CommonFunction.checkNull(CibilVerificationVO.get(0)).toString()));
						cVo.setCibilDate((CommonFunction.checkNull(CibilVerificationVO.get(1)).toString()));
						selectQueryValue.add(cVo);
		}
				}
     			/*cVo.setCustomerId(selectQueryValue);
				cVo.setCibilDate(selectQueryValue);
				*/
				
				
			String query1="update cr_deal_customer_m set  CIBIL_DONE='"+cibilDone+"',CIBIL_DATE='"+cVo.getCibilDate()+"', cibil_id = '"+cibilId+"' where customer_Id='"+cVo.getCustomerId()+"' ";
			insertPrepStmtObject.setSql(query1);
			
			
			logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
			ArrayList qryList1=new ArrayList();
			qryList1.add(insertPrepStmtObject);
			try
			{
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
			}
		catch(Exception e)
		{e.printStackTrace();}	
		finally
		{
			insertPrepStmtObject=null;
				query1=null;
				qryList1.clear();
				qryList1=null;
			}
			//Parvez Ends
			// TODO Auto-generated method stub
			
		}
		catch(Exception e)
		{e.printStackTrace();}	
		finally
		{
			insertPrepStmtObject=null;
			query=null;
			qryList.clear();
			qryList=null;
		}
		
		return status;
	}
	
	@Override
	public CibilVerificationVO getIPconfig() {
		CibilVerificationVO vo=new CibilVerificationVO();
		ArrayList list = new ArrayList();
		String cibilUATipRes="";
		String cibilUATPortRes="";
		String qrycibilUATip="select parameter_value from parameter_mst where parameter_key='CIBIL_UAT_IP'";
		logger.info("In getIPconfig()$$$$$$$$$$$$$$$$$$$$$"+qrycibilUATip);
		String qrycibilUAT="select parameter_value from parameter_mst where  parameter_key='CIBIL_UAT_PORT' ";
		logger.info("In getIPconfig()$$$$$$$$$$$$$$$$$$$$$"+qrycibilUAT);
		cibilUATipRes = ConnectionDAO.singleReturn(qrycibilUATip);
		cibilUATPortRes=ConnectionDAO.singleReturn(qrycibilUAT);
			vo.setCibilUATip(cibilUATipRes);
			vo.setCibilUATPort(cibilUATPortRes);
			
			logger.info("ip_address to connect $$$$$$$$$$$$$$$$$$$$$"+cibilUATipRes+"Port Number to connect"+cibilUATPortRes);
		return vo;
		
	} 

	@Override
	public String GenerateView(String cibilId) {			
		String sqlQuery = "";
		sqlQuery="SELECT REPORT_STATUS FROM CR_CIBIL_REF_DTL WHERE CIBIL_ID="+cibilId;
		String strReport_Status=CommonFunction.checkNull(ConnectionDAO.singleReturn(sqlQuery)).trim();
		if( strReport_Status.equalsIgnoreCase("N") )
		{
			try
			{
				logger.info("Call Procedure after Recive response.");
				ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				ArrayList result = new ArrayList();
				String s1="";
				String s2="";
				String error="";
				try
				{
					in.add(cibilId);
					out.add(s1);
					out.add(s2);					
					logger.info("CIBIL_OUTPUTSTRING_GENERATOR ("+in.toString()+","+out.toString()+")");
					outMessages=(ArrayList) ConnectionDAO.callSP("CIBIL_OUTPUTSTRING_GENERATOR",in,out);
					s1=CommonFunction.checkNull(outMessages.get(0));
					s2=CommonFunction.checkNull(outMessages.get(1));
					logger.info("CIBIL_OUTPUTSTRING_GENERATOR  s1  : "+s1);
			        logger.info("CIBIL_OUTPUTSTRING_GENERATOR  s2  : "+s2);
			    }
				catch (Exception e) 
				{
					cibilId="";
			        e.printStackTrace();
			    }
				finally
				{
					in=null;
					out =null;
					outMessages=null;
					s1=null;
					s2=null;
					error=null;			
				}			
			}
			catch(Exception e)
			{
				cibilId="";
				e.printStackTrace();
			}			 
		 }
		 return strReport_Status;		
	}
	public String getCibilId(String dealId, String customerId)
	{
		String cibilId ="";
		String Query = "select max(CIBIL_ID) from cr_cibil_ref_dtl where deal_id= '"+dealId+"' and customer_id = '"+customerId+"'";
		logger.info("Query --------"+Query);
		cibilId = ConnectionDAO.singleReturn(Query);
		logger.info("cibilId --------"+cibilId);
		return cibilId;
	}
	public String getCibilResponse(String cibilId)
	{
		
			String cibilres ="";
		String Query = "select case report_status when 'E' then 'ERROR RESPONSE' when 'I' Then 'INVALID RESPONSE' when 'N' then 'NO RESPONSE' when 'S' then 'POSITIVE RESPONSE'  END as Result_Status from cr_cibil_ref_dtl where cibil_id = '"
				+ cibilId + "' ";
			logger.info("getCibilResponse Query --------"+Query);
			cibilres = ConnectionDAO.singleReturn(Query);
			logger.info("cibilId --------"+cibilId);
			return cibilres;
		
	}
	
	public String getCibilRepotFlag()
	{
		
			String flag ="";
		String Query = "select parameter_value from parameter_mst where parameter_key = 'CIBIL_REPORT_VIEW_UPLOAD'";
				
			logger.info("getCibilRepotFlag Query --------"+Query);
			flag = ConnectionDAO.singleReturn(Query);
			if(CommonFunction.checkNull(flag).equalsIgnoreCase(""))
				flag = "Y";
			logger.info("cibilId --------"+flag);
			return flag;
		
	}
@Override
	public String getCibilProvider() {
		String flag = "";
		try{
			String Query = "select parameter_value from parameter_mst where parameter_key = 'CIBIL_PROVIDER'";
			logger.info("getCibilProvider Query --------" + Query);
			flag = ConnectionDAO.singleReturn(Query);
		}catch(Exception e){
			e.printStackTrace();
}
		if(CommonFunction.checkNull(flag).equalsIgnoreCase("")){
			flag="";
		}
		return flag;
	}
@Override
public ArrayList getpreDealGridList(CibilVerificationVO vo) {
	logger.info("in getCVGridList() ");
	StringBuilder bufInsSql =	new StringBuilder();
	ArrayList formatlist=null;
	ArrayList data=null;
	ArrayList resultList=new ArrayList();		
	CibilVerificationVO obj=(CibilVerificationVO)vo;
	try
	{
		bufInsSql.append("select a.lead_id,a.lead_id,c.CUSTOMER_NAME,c.GENDER,case c.CUSTOMER_TYPE when 'I' then  'INDUVIDUAL' when 'C' then 'CORPORATE' end as CUSTOMER_TYPE," +
				" case c.CUSTOMER_ROLE_TYPE when 'PRAPPL' then 'APPLICANT' when 'GUARANTOR' then 'GUARANTOR' else 'CO-APPLICANT' end as CUSTOMER_ROLE_TYPE," +
				" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='CIBIL_MEMBER_NAME')MEMBER_NAME,(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_ID_INQUIRY')MEMBER_ID," +
				" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='ENQUIRY-PURPOSE')ENQUIRY_PURPOSE,c.CUSTOMER_ID REF_NO,date_format(C.CUSTOMER_DOB,'%d-%m-%Y')," +
				" c.ADDRESS_LINE1,c.ADDRESS_LINE2,c.ADDRESS_LINE3,c.ADDRESS_TYPE,'' as ADDRESS_DETAIL,e.DISTRICT_DESC,f.STATE_DESC,c.PINCODE,g.COUNTRY_DESC,c.PAN,c.REGISTRATION_NO," +
				" c.voter_id,c.passport_number,c.DRIVING_LICENSE,c.PRIMARY_PHONE,'' as ALTERNATE_PHONE,'' as TOLLFREE_NUMBER,'' as FAX,ifnull(c.CIBIL_DONE,'N')CIBIL_DONE,c.UID_NO " +
				" from cr_lead_dtl a " +
				" join CR_LEAD_CUSTOMER_M c on(a.lead_id=c.lead_id)" +
				" left join com_district_m e on(e.DISTRICT_ID=c.DISTRICT)" +
				" left join COM_STATE_M f on(f.STATE_ID=c.STATE)" +
				" left join com_country_m g on(g.COUNTRY_ID=c.COUNTRY)" +
				" where true " );
		logger.info("Lead Id-------------------------- obj.getlbxLeadId "+obj.getLbxLeadId()+"    ffffffffffffff "+obj.getLeadID());
		
		if(!CommonFunction.checkNull(obj.getLbxLeadId()).equalsIgnoreCase(""))
			bufInsSql.append(" and a.lead_id='"+obj.getLbxLeadId()+"' ");
		
		 logger.info("in getCVGridList() Search Query  :  "+bufInsSql.toString());
		
		  formatlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
          for (int i=0;i<formatlist.size();i++)
          {
				data = (ArrayList) formatlist.get(i);
				CibilVerificationVO dvo= new CibilVerificationVO(); 
				dvo.setDealID(CommonFunction.checkNull(data.get(0)).toString());
				dvo.setDealNO(CommonFunction.checkNull(data.get(1)).toString());
				dvo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
				dvo.setCustomerGender(CommonFunction.checkNull(data.get(3)).toString());
				dvo.setCustomerType(CommonFunction.checkNull(data.get(4)).toString());
				dvo.setCustomerRole(CommonFunction.checkNull(data.get(5)).toString());
				dvo.setMemberName(CommonFunction.checkNull(data.get(6)).toString());
				dvo.setMemberId(CommonFunction.checkNull(data.get(7)).toString());
				dvo.setInquiryPurpus(CommonFunction.checkNull(data.get(8)).toString());
				dvo.setRefranceNo(CommonFunction.checkNull(data.get(9)).toString());
				dvo.setCustomerDob(CommonFunction.checkNull(data.get(10)).toString());
				dvo.setAddress1(CommonFunction.checkNull(data.get(11)).toString());
				dvo.setAddress2(CommonFunction.checkNull(data.get(12)).toString());
				dvo.setAddress3(CommonFunction.checkNull(data.get(13)).toString());
				dvo.setAddressType(CommonFunction.checkNull(data.get(14)).toString());
				dvo.setAddressDetail(CommonFunction.checkNull(data.get(15)).toString());
				dvo.setDistict(CommonFunction.checkNull(data.get(16)).toString());
				dvo.setState(CommonFunction.checkNull(data.get(17)).toString());
				dvo.setPincode(CommonFunction.checkNull(data.get(18)).toString());
				dvo.setCountry(CommonFunction.checkNull(data.get(19)).toString());
				dvo.setPanNo(CommonFunction.checkNull(data.get(20)).toString());
				dvo.setRegNo(CommonFunction.checkNull(data.get(21)).toString());
				dvo.setVoterId(CommonFunction.checkNull(data.get(22)).toString());
				dvo.setPassport(CommonFunction.checkNull(data.get(23)).toString());
				dvo.setDrivingLicense(CommonFunction.checkNull(data.get(24)).toString());
				dvo.setPrimaryPhone(CommonFunction.checkNull(data.get(25)).toString());
				dvo.setAlterPhone(CommonFunction.checkNull(data.get(26)).toString());
				dvo.setTollPhone(CommonFunction.checkNull(data.get(27)).toString());
				dvo.setFaxNo(CommonFunction.checkNull(data.get(28)).toString());
				dvo.setCibilDone(CommonFunction.checkNull(data.get(29)).toString());
				dvo.setUid(CommonFunction.checkNull(data.get(30)).toString());
				resultList.add(dvo);
				dvo=null;
				data.clear();
				data=null;					
		  }
    }
	catch (Exception e) 
	{e.printStackTrace();}		
	return resultList;
}
@Override
public ArrayList SaveRecordPreDeal(String leadId, int customerId,
		String businessDate) {
	boolean status=false;
	StringBuilder bufInsSql =	new StringBuilder();
	ArrayList formatlist=null;
	ArrayList data=null;
	ArrayList resultList=new ArrayList();
	ArrayList qryList=new ArrayList();	
	ArrayList responseList=new ArrayList();			
	PrepStmtObject insertPrepStmtObject=null;
	
	String strBatchId="0";
	int batchId=0;
   /* String sqlQuery="SELECT MAX(BATCH_ID) FROM CR_CIBIL_REF_DTL";*/
	String sqlQuery="select function_sequence('CIBIL_BATCH_SEQ')";
    strBatchId=CommonFunction.checkNull(ConnectionDAO.singleReturn(sqlQuery)).trim();
    if(CommonFunction.checkNull(strBatchId).trim().equalsIgnoreCase(""))
    	strBatchId="0";
    batchId=Integer.parseInt(strBatchId);
   // batchId=batchId+1;
    if(strBatchId.equalsIgnoreCase("0")){
    	String sqlQuery1="select max(BATCH_ID) from CR_CIBIL_REF_DTL ";
	    strBatchId=CommonFunction.checkNull(ConnectionDAO.singleReturn(sqlQuery1)).trim();
	    batchId=Integer.parseInt(strBatchId);
	    batchId=batchId+1;
    }
    try
	{
		bufInsSql.append("select c.CUSTOMER_ID,(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='CIBIL_MEMBER_NAME')MEMBER_NAME," +
				"(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_ID_INQUIRY')MEMBER_ID, " +
				"(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='ENQUIRY-PURPOSE')ENQUIRY_PURPOSE,  c.CUSTOMER_ID," +
				"c.CUSTOMER_NAME,c.GENDER,date_format(C.CUSTOMER_DOB,'%d-%m-%Y')CUSTOMER_DOB,c.ADDRESS_LINE1,c.ADDRESS_LINE2,c.ADDRESS_LINE3,c.ADDRESS_TYPE,'' as ADDRESS_DETAIL,e.DISTRICT_DESC,f.CIBIL_STATE_ID,c.PINCODE," +
				"g.COUNTRY_DESC,c.PAN,c.voter_id,c.passport_number,c.DRIVING_LICENSE,c.PRIMARY_PHONE,'' as ALTERNATE_PHONE,'' as TOLLFREE_NUMBER,'' as FAX,A.LEAD_ID,c.UID_NO " +
				"from cr_lead_dtl a  " +
				"join CR_LEAD_CUSTOMER_M c on(a.LEAD_ID=c.LEAD_ID) " +
				"left join com_district_m e on(e.DISTRICT_ID=c.DISTRICT) " +
				"left join COM_STATE_M f on(f.STATE_ID=c.STATE) " +
				"left join com_country_m g on(g.COUNTRY_ID=c.COUNTRY) " +
				"where a.lead_id='"+CommonFunction.checkNull(leadId)+"' and c.CUSTOMER_ID in("+customerId+")");
		 logger.info("in SaveRecord() fetch record Query  :  "+bufInsSql.toString());			
		 formatlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		 bufInsSql=null;
		 for (int i=0;i<formatlist.size();i++)
         {
			 data = (ArrayList) formatlist.get(i);
			 bufInsSql =new StringBuilder();					
			 bufInsSql.append("INSERT INTO CR_CIBIL_REF_DTL (BATCH_ID,CUSTOMER_ID,MEMBER_NAME,MEMBER_ID,ENQUIRY_PURPOSE,REFERENCE_NUMBER,NAME,GENDER,DATE_OF_BIRTH," +
					 "ADDRESS_1,ADDRESS_2,ADDRESS_3,ADDRESS_TYPE,ADDRESS_DETAIL,DISTRICT,STATE,PINCODE,COUNTRY,PAN_NO,VOTER_ID,PASSPORT_NO,DRIVING_LICENCE_NO," +
			 " MOBILE_NO1,TELEPHONE_NO1,TELEPHONE_NO2,TELEPHONE_NO3,CIBIL_DATE,CIBIL_RESULT,REC_STATUS,REPORT_STATUS,LEAD_ID,UID_NO)");
			 bufInsSql.append(" values ( ");
			 bufInsSql.append(" ?," );//BATCH_ID
			 bufInsSql.append(" ?," );//CUSTOMER_ID
			 bufInsSql.append(" ?," );//MEMBER_NAME
			 bufInsSql.append(" ?," );//MEMBER_ID				
			 bufInsSql.append(" ?," );//ENQUIRY_PURPOSE 
			 bufInsSql.append(" ?," );//REFERENCE_NUMBER
			 bufInsSql.append(" ?," );//NAME
			 bufInsSql.append(" ?," );//GENDER
			 bufInsSql.append(" STR_TO_DATE(?, '"+dateFormat+"')," );//DATE_OF_BIRTH				 
			 bufInsSql.append(" ?," );//ADDRESS_1
			 bufInsSql.append(" ?," );//ADDRESS_2
			 bufInsSql.append(" ?," );//ADDRESS_3
			 bufInsSql.append(" ?," );//ADDRESS_TYPE
			 bufInsSql.append(" ?," );//ADDRESS_DETAIL
			 bufInsSql.append(" ?," );//DISTRICT
			 bufInsSql.append(" ?," );//STATE
			 bufInsSql.append(" ?," );//PINCODE
			 bufInsSql.append(" ?," );//COUNTRY
			 bufInsSql.append(" ?," );//PAN_NO     
			 bufInsSql.append(" ?," );//VOTER_ID
			 bufInsSql.append(" ?," );//PASSPORT_NO
			 bufInsSql.append(" ?," );//DRIVING_LICENCE_NO				 
			 bufInsSql.append(" ?," );//MOBILE_NO1
			 bufInsSql.append(" ?," );//TELEPHONE_NO1
			 bufInsSql.append(" ?," );//TELEPHONE_NO2
			 bufInsSql.append(" ?," );//TELEPHONE_NO3
			 //bufInsSql.append(dbo);
			 //bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as varchar(30))),0,9),");//CIBIL_DATE
			 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') ,"); //CIBIL_DATE
			 bufInsSql.append(" ?," );//CIBIL_RESULT
			 bufInsSql.append(" ?," );//REC_STATUS
			 bufInsSql.append(" ?," );//REPORT_STATUS
			 bufInsSql.append(" ?," );//LEAD_ID
			 bufInsSql.append(" ?)" );//UID NO
				 	
			 insertPrepStmtObject = new PrepStmtObject();
			 insertPrepStmtObject.addInt(batchId);
			 if(CommonFunction.checkNull(data.get(0)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
			 {
				 String custId=CommonFunction.checkNull(data.get(0)).trim();
				 if(custId.equalsIgnoreCase(""))
					 custId="0";
				 insertPrepStmtObject.addInt(Integer.parseInt(custId));
				 
			 }									 
			 if(CommonFunction.checkNull(data.get(1)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(1)).trim());
			 
			 if(CommonFunction.checkNull(data.get(2)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(2)).trim());
			 
			 if(CommonFunction.checkNull(data.get(3)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(3)).trim());
			 
			 if(CommonFunction.checkNull(data.get(4)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(4)).trim());
			 
			 if(CommonFunction.checkNull(data.get(5)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(5)).trim());
			 
			 if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(6)).trim());
			 
			 if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(7)).trim());
			 
			 if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(8)).trim());
			
			 if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(9)).trim());
			 
			 if(CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(10)).trim());
			 if(CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(11)).trim());
			 
			 if(CommonFunction.checkNull(data.get(12)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(12)).trim());
			 
			 if(CommonFunction.checkNull(data.get(13)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(13)).trim());
			 
			 if(CommonFunction.checkNull(data.get(14)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(14)).trim());
			 
			 if(CommonFunction.checkNull(data.get(15)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(15)).trim());
			 
			 if(CommonFunction.checkNull(data.get(16)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(16)).trim());
			 
			 if(CommonFunction.checkNull(data.get(17)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(17)).trim());
			 
			 if(CommonFunction.checkNull(data.get(18)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(18)).trim());
			 
			 if(CommonFunction.checkNull(data.get(19)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(19)).trim());
			 
			 if(CommonFunction.checkNull(data.get(20)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(20)).trim());
			 
			 if(CommonFunction.checkNull(data.get(21)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(21)).trim());
			 
			 if(CommonFunction.checkNull(data.get(22)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(22)).trim());
			 
			 if(CommonFunction.checkNull(data.get(23)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(23)).trim());
			 
			 if(CommonFunction.checkNull(data.get(24)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(24)).trim());
		
			 
			 insertPrepStmtObject.addString(businessDate);
			 insertPrepStmtObject.addString("N");			 
			 insertPrepStmtObject.addString("F");
			 insertPrepStmtObject.addString("N");
			 
			 if(CommonFunction.checkNull(data.get(25)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(25)).trim());
			 
			 if(CommonFunction.checkNull(data.get(26)).trim().equalsIgnoreCase(""))
				 insertPrepStmtObject.addNull();
			 else
				 insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(26)).trim());
			 
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN SaveRecord() insert query("+i+1+")  :  "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			insertPrepStmtObject=null;
			bufInsSql=null;
			data.clear();
			data=null;
         }//end of loop
		 try
		 {
			 status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In SaveRecord.........insert status: "+status);
				
				if(status)
				{
				String resString="SELECT CIBIL_ID FROM CR_CIBIL_REF_DTL WHERE BATCH_ID="+batchId;
				          	logger.info("in SaveRecord() getresponse String query  :  "+resString);			
							 responseList = ConnectionDAO.sqlSelect(resString);
							 
					/*logger.info("Call Procedure after save.");
					ArrayList<Object> in =new ArrayList<Object>();
					ArrayList<Object> out =new ArrayList<Object>();
					ArrayList outMessages = new ArrayList();
					ArrayList result = new ArrayList();
					String s1="";
					String s2="";
					String s3="";
					String error="";
					try
					{
						in.add(batchId);
						out.add(s1);
						out.add(s2);
						
						logger.info("CIBIL_INPUTSTRING_GENERATOR ("+in.toString()+","+out.toString()+")");
						outMessages=(ArrayList) ConnectionDAO.callSP("CIBIL_INPUTSTRING_GENERATOR",in,out);
						s1=CommonFunction.checkNull(outMessages.get(0));
						s2=CommonFunction.checkNull(outMessages.get(1));
						logger.info("CIBIL_INPUTSTRING_GENERATOR  s1  : "+s1);
				        logger.info("CIBIL_INPUTSTRING_GENERATOR  s2  : "+s2);
				        if(CommonFunction.checkNull(s1).trim().equalsIgnoreCase("S"))
				        {
				          	String resString="SELECT CIBIL_ID,FORWORD_STRING FROM CR_CIBIL_REF_DTL WHERE BATCH_ID="+batchId;
				          	logger.info("in SaveRecord() getresponse String query  :  "+resString);			
							 responseList = ConnectionDAO.sqlSelect(resString);
				        	
				        }
				    }
					catch (Exception e) 
					{
						batchId=0;
				        e.printStackTrace();
				    }
					finally
					{
						in=null;
						out =null;
						outMessages=null;
						s1=null;
						s2=null;
						s3=null;
						error=null;			
					}*/
				}
		 }
	catch(Exception e)
	{
		 batchId=0;
		 e.printStackTrace();
	}	
	}
    catch(Exception e)
	{
		 batchId=0;
		 e.printStackTrace();
	}
	finally
	{
		bufInsSql=null;
		formatlist=null;
		data=null;
		resultList.clear();
		resultList=null;
		qryList.clear();
		qryList=null;
		insertPrepStmtObject=null;
	}
	return responseList;
}
public boolean updatePreDealResString(String cibilId, String resString) 
{
	boolean status=false;	
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String header="";
	String cibilResult="";
	CibilVerificationVO cVo=new CibilVerificationVO();
	
	if(CommonFunction.checkNull(resString).trim().length()>=4)
		header=resString.substring(0, 4);
	if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("TUEF"))
		cibilResult="S";
	else if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("ERRR")) 
		cibilResult="E";
	else
		cibilResult="I";	
	resString=resString.replaceAll("\'","");
	String query="UPDATE  CR_CIBIL_REF_DTL SET CIBIL_RESULT='"+cibilResult+"',REC_STATUS='A',RECEIVED_STRING='"+CommonFunction.checkNull(resString).trim()+"' WHERE  CIBIL_ID='"+CommonFunction.checkNull(cibilId).trim()+"' ";
	insertPrepStmtObject.setSql(query);
	logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
	ArrayList qryList=new ArrayList();
	qryList.add(insertPrepStmtObject);
	try
	{
		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		//Parvez Starts
		
		String cibilDone="";
		if(status)
			if(CommonFunction.checkNull(resString).trim().length()>=4)
				header=resString.substring(0, 4);
			if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("TUEF"))
			cibilDone="Y";
		else if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("ERRR")) 
			cibilDone="Y";
		else
			cibilDone="N";
		
			String selectQuery ="select customer_Id,cibil_date from cr_cibil_ref_dtl where CIBIL_ID='"+CommonFunction.checkNull(cibilId).trim()+"' ";
			
			ArrayList selectQueryValue =ConnectionDAO.sqlSelect(selectQuery);
			logger.info("Size : "+selectQueryValue.size());
			int size =selectQueryValue.size();
			for(int i=0;i<size;i++)
			{
				ArrayList CibilVerificationVO=(ArrayList)selectQueryValue.get(i);
				if(CibilVerificationVO.size()>0)
				{
					
					cVo.setCustomerId((CommonFunction.checkNull(CibilVerificationVO.get(0)).toString()));
					cVo.setCibilDate((CommonFunction.checkNull(CibilVerificationVO.get(1)).toString()));
					selectQueryValue.add(cVo);
	}
			}
 			/*cVo.setCustomerId(selectQueryValue);
			cVo.setCibilDate(selectQueryValue);
			*/
			
			
		String query1="update cr_lead_customer_m set  CIBIL_DONE='"+cibilDone+"',CIBIL_DATE='"+cVo.getCibilDate()+"', cibil_id = '"+cibilId+"' where customer_Id='"+cVo.getCustomerId()+"' ";
		insertPrepStmtObject.setSql(query1);
		
		
		logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
		ArrayList qryList1=new ArrayList();
		qryList1.add(insertPrepStmtObject);
		try
		{
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
		}
	catch(Exception e)
	{e.printStackTrace();}	
	finally
	{
		insertPrepStmtObject=null;
			query1=null;
			qryList1.clear();
			qryList1=null;
		}
		//Parvez Ends
		// TODO Auto-generated method stub
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
	finally
	{
		insertPrepStmtObject=null;
		query=null;
		qryList.clear();
		qryList=null;
	}
	
	return status;
}
public ArrayList<Object> getRoleList(String leadId) {
	logger.info("id in actiongetRoleList");
	ArrayList<Object> list=new ArrayList<Object>();
	logger.info("In , credit processing , getRoleList() *************************************    :"+leadId);
	try{
	String query=" SELECT '' as DEAL_CUSTOMER_ROLE_ID, d.CUSTOMER_TYPE,d.CUSTOMER_ID,d.EXISTING_CUSTOMER,"+
	" d.CUSTOMER_NAME,m.description,'' as GUARANTEE_AMOUNT, d.cibil_done,d.cibil_date, d.cibil_id,case d.cibil_done when 'Y' then 'BUREAU DONE' else 'BUREAU NOT DONE' end as CIBIL_STATUS,c.LEAD_ID,ifnull(cm.SCORE,''),ifnull(cm.CRIF_HIGH_MARK_SCORE,''),ifnull(cm.EXPERIAN_SCORE,'')  from cr_lead_dtl c "+
	"  join cr_lead_customer_m d on c.LEAD_ID=d.LEAD_ID"+
	" left join generic_master m on d.CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE'"+
	" left join CR_CIBIL_SCORE_SEGMENT cm on d.CIBIL_ID=cm.cibil_id "+
	" where c.lead_Id='"+leadId+"' ";
	logger.info("getRoleList:Query "+query);		
	CreditProcessingCustomerEntryVo vo=null;
	ArrayList roleList = ConnectionDAO.sqlSelect(query);
	logger.info("getRoleList "+roleList.size());
	for(int i=0;i<roleList.size();i++){
		
		ArrayList data=(ArrayList)roleList.get(i);
		if(data.size()>0)
		{
			vo =new CreditProcessingCustomerEntryVo();
			vo.setRoleId((CommonFunction.checkNull(data.get(0)).toString()));
			vo.setApplicantType((CommonFunction.checkNull(data.get(5)).toString()));
			if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("C"))
			{
				vo.setApplicantCategory("CORPORATE");
			}
			else if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("I"))
			{
				vo.setApplicantCategory("INDIVIDUAL");
			}
			
			if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("N"))
			{
				vo.setExistingCustomer("NO");
			}
			else if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("Y"))
			{
				vo.setExistingCustomer("YES");
			}
			
			vo.setCustomerName((CommonFunction.checkNull(data.get(4)).toString()));
			vo.setCustomerId((CommonFunction.checkNull(data.get(2)).toString()));
			
			if(!(CommonFunction.checkNull(data.get(6)).toString()).equalsIgnoreCase(""))
    		{
    			
    			vo.setGuaranteeAmount("0");
    		}	
			
			//vo.setGuaranteeAmount();
			vo.setFlagForUpdate("updateFlag");
			vo.setCibilDone((CommonFunction.checkNull(data.get(7)).toString()));// add by saorabh on 30/july
			vo.setCibilDate((CommonFunction.checkNull(data.get(8)).toString()));// add by saorabh on 30/july
			vo.setLbxCibilId((CommonFunction.checkNull(data.get(9)).toString()));// add by saorabh on 30/july
			vo.setCibilResponse((CommonFunction.checkNull(data.get(10)).toString()));
			vo.setDealId((CommonFunction.checkNull(data.get(11)).toString()));
			vo.setCibilScore((CommonFunction.checkNull(data.get(12)).toString()));
			vo.setCrifHighMarkScore((CommonFunction.checkNull(data.get(13)).toString()));
			vo.setExperianScore((CommonFunction.checkNull(data.get(14)).toString()));
			list.add(vo);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return list;
}
public void updateCibilRecStatus(int batchId) 
{
	String query="UPDATE CR_CIBIL_REF_DTL REF "+
	" JOIN CR_DEAL_CUSTOMER_M CUST ON(REF.CUSTOMER_ID=CUST.CUSTOMER_ID) "+
	" SET REF.REC_STATUS='A',CUST.CIBIL_DONE='N',CUST.CIBIL_DATE=NULL,CUST.CIBIL_ID=NULL "+
	" WHERE REF.BATCH_ID='"+batchId+"'";
	logger.info("updateCibilRecStatus Query --------" + query);
	ArrayList list=new ArrayList();
	list.add(query);
	try {
		boolean status=ConnectionDAO.sqlInsUpdDelete(list);
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
/*public ArrayList<Object> getRoleList(String leadId) {
	logger.info("id in actiongetRoleList");
	ArrayList<Object> list=new ArrayList<Object>();
	logger.info("In , credit processing , getRoleList() *************************************    :"+leadId);
	try{
	String query=" SELECT '' as DEAL_CUSTOMER_ROLE_ID, d.CUSTOMER_TYPE,d.CUSTOMER_ID,d.EXISTING_CUSTOMER,"+
	" d.CUSTOMER_NAME,m.description,'' as GUARANTEE_AMOUNT, d.cibil_done,d.cibil_date, d.cibil_id,case d.cibil_done when 'Y' then 'CIBIL DONE' else 'CIBIL NOT DONE' end as CIBIL_STATUS,c.LEAD_ID,ifnull(cm.SCORE,''),ifnull(cm.CRIF_HIGH_MARK_SCORE,'')  from cr_lead_dtl c "+
	"  join cr_lead_customer_m d on c.LEAD_ID=d.LEAD_ID"+
	" left join generic_master m on d.CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE'"+
	" left join CR_CIBIL_SCORE_SEGMENT cm on d.CIBIL_ID=cm.cibil_id "+
	" where c.lead_Id='"+leadId+"' ";
	logger.info("getRoleList:Query "+query);		
	CreditProcessingCustomerEntryVo vo=null;
	ArrayList roleList = ConnectionDAO.sqlSelect(query);
	logger.info("getRoleList "+roleList.size());
	for(int i=0;i<roleList.size();i++){
		
		ArrayList data=(ArrayList)roleList.get(i);
		if(data.size()>0)
		{
			vo =new CreditProcessingCustomerEntryVo();
			vo.setRoleId((CommonFunction.checkNull(data.get(0)).toString()));
			vo.setApplicantType((CommonFunction.checkNull(data.get(5)).toString()));
			if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("C"))
			{
				vo.setApplicantCategory("CORPORATE");
			}
			else if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("I"))
			{
				vo.setApplicantCategory("INDIVIDUAL");
			}
			
			if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("N"))
			{
				vo.setExistingCustomer("NO");
			}
			else if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("Y"))
			{
				vo.setExistingCustomer("YES");
			}
			
			vo.setCustomerName((CommonFunction.checkNull(data.get(4)).toString()));
			vo.setCustomerId((CommonFunction.checkNull(data.get(2)).toString()));
			
			if(!(CommonFunction.checkNull(data.get(6)).toString()).equalsIgnoreCase(""))
    		{
    			
    			vo.setGuaranteeAmount("0");
    		}	
			
			//vo.setGuaranteeAmount();
			vo.setFlagForUpdate("updateFlag");
			vo.setCibilDone((CommonFunction.checkNull(data.get(7)).toString()));// add by saorabh on 30/july
			vo.setCibilDate((CommonFunction.checkNull(data.get(8)).toString()));// add by saorabh on 30/july
			vo.setLbxCibilId((CommonFunction.checkNull(data.get(9)).toString()));// add by saorabh on 30/july
			vo.setCibilResponse((CommonFunction.checkNull(data.get(10)).toString()));
			vo.setDealId((CommonFunction.checkNull(data.get(11)).toString()));
			vo.setCibilScore((CommonFunction.checkNull(data.get(12)).toString()));
			vo.setCrifHighMarkScore((CommonFunction.checkNull(data.get(13)).toString()));
			list.add(vo);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return list;
}*/
/*public void updateCibilRecStatus(int batchId) 
{
	String query="UPDATE CR_CIBIL_REF_DTL REF "+
	" JOIN CR_DEAL_CUSTOMER_M CUST ON(REF.CUSTOMER_ID=CUST.CUSTOMER_ID) "+
	" SET REF.REC_STATUS='A',CUST.CIBIL_DONE='N',CUST.CIBIL_DATE=NULL,CUST.CIBIL_ID=NULL "+
	" WHERE REF.BATCH_ID='"+batchId+"'";
	logger.info("updateCibilRecStatus Query --------" + query);
	ArrayList list=new ArrayList();
	list.add(query);
	try {
		boolean status=ConnectionDAO.sqlInsUpdDelete(list);
	} catch (SQLException e) {
		e.printStackTrace();
	}
}*/
//pooja code for Crif
@Override
public String getCRIFUserId() {
String UserId="";
try{
	String query="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CRIF_USER_ID'";
	UserId=ConnectionDAO.singleReturn(query);
	logger.info("CRIF User Id is "+UserId);
}catch(Exception e){
	e.printStackTrace();
}
return UserId;

}
@Override
public String getCRIFPassword() {
	String password="";
	try{
		String query="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CRIF_USER_PASSWORD'";
		password=ConnectionDAO.singleReturn(query);
		logger.info("CRIF password is "+password);
	}catch(Exception e){
		e.printStackTrace();
	}
	return password;
}

	
/*	 public ArrayList<Object> getRoleList(String leadId) {
			logger.info("id in actiongetRoleList");
			ArrayList<Object> list=new ArrayList<Object>();
			logger.info("In , credit processing , getRoleList() *************************************    :"+leadId);
			try{
			String query=" SELECT '' as DEAL_CUSTOMER_ROLE_ID, d.CUSTOMER_TYPE,d.CUSTOMER_ID,d.EXISTING_CUSTOMER,"+
			" d.CUSTOMER_NAME,m.description,'' as GUARANTEE_AMOUNT, d.cibil_done,d.cibil_date, d.cibil_id,case d.cibil_done when 'Y' then 'CIBIL DONE' else 'CIBIL NOT DONE' end as CIBIL_STATUS,c.LEAD_ID,ifnull(cm.SCORE,''),ifnull(cm.CRIF_HIGH_MARK_SCORE,'')  from cr_lead_dtl c "+
			"  join cr_lead_customer_m d on c.LEAD_ID=d.LEAD_ID"+
			" left join generic_master m on d.CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE'"+
			" left join CR_CIBIL_SCORE_SEGMENT cm on d.CIBIL_ID=cm.cibil_id "+
			" where c.lead_Id='"+leadId+"' ";
			logger.info("getRoleList:Query "+query);		
			CreditProcessingCustomerEntryVo vo=null;
			ArrayList roleList = ConnectionDAO.sqlSelect(query);
			logger.info("getRoleList "+roleList.size());
			for(int i=0;i<roleList.size();i++){
				
				ArrayList data=(ArrayList)roleList.get(i);
				if(data.size()>0)
				{
					vo =new CreditProcessingCustomerEntryVo();
					vo.setRoleId((CommonFunction.checkNull(data.get(0)).toString()));
					vo.setApplicantType((CommonFunction.checkNull(data.get(5)).toString()));
					if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("C"))
					{
						vo.setApplicantCategory("CORPORATE");
					}
					else if((CommonFunction.checkNull(data.get(1)).toString()).equalsIgnoreCase("I"))
					{
						vo.setApplicantCategory("INDIVIDUAL");
					}
					
					if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("N"))
					{
						vo.setExistingCustomer("NO");
					}
					else if((CommonFunction.checkNull(data.get(3)).toString()).equalsIgnoreCase("Y"))
					{
						vo.setExistingCustomer("YES");
					}
					
					vo.setCustomerName((CommonFunction.checkNull(data.get(4)).toString()));
					vo.setCustomerId((CommonFunction.checkNull(data.get(2)).toString()));
					
					if(!(CommonFunction.checkNull(data.get(6)).toString()).equalsIgnoreCase(""))
		    		{
		    			
		    			vo.setGuaranteeAmount("0");
		    		}	
					
					//vo.setGuaranteeAmount();
					vo.setFlagForUpdate("updateFlag");
					vo.setCibilDone((CommonFunction.checkNull(data.get(7)).toString()));// add by saorabh on 30/july
					vo.setCibilDate((CommonFunction.checkNull(data.get(8)).toString()));// add by saorabh on 30/july
					vo.setLbxCibilId((CommonFunction.checkNull(data.get(9)).toString()));// add by saorabh on 30/july
					vo.setCibilResponse((CommonFunction.checkNull(data.get(10)).toString()));
					vo.setDealId((CommonFunction.checkNull(data.get(11)).toString()));
					vo.setCibilScore((CommonFunction.checkNull(data.get(12)).toString()));
					vo.setCrifHighMarkScore((CommonFunction.checkNull(data.get(13)).toString()));
					list.add(vo);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}
public void updateCibilRecStatus(int batchId) 
{
	String query="UPDATE CR_CIBIL_REF_DTL REF "+
	" JOIN CR_DEAL_CUSTOMER_M CUST ON(REF.CUSTOMER_ID=CUST.CUSTOMER_ID) "+
	" SET REF.REC_STATUS='A',CUST.CIBIL_DONE='N',CUST.CIBIL_DATE=NULL,CUST.CIBIL_ID=NULL "+
	" WHERE REF.BATCH_ID='"+batchId+"'";
	logger.info("updateCibilRecStatus Query --------" + query);
	ArrayList list=new ArrayList();
	list.add(query);
	try {
		boolean status=ConnectionDAO.sqlInsUpdDelete(list);
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
@Override
public String getCRIFUserId() {
	String UserId="";
	try{
		String query="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CRIF_USER_ID'";
		UserId=ConnectionDAO.singleReturn(query);
		logger.info("CRIF User Id is "+UserId);
	}catch(Exception e){
		e.printStackTrace();
	}
	return UserId;

}

@Override
public String getCRIFPassword() {
	String password="";
	try{
		String query="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CRIF_USER_PASSWORD'";
		password=ConnectionDAO.singleReturn(query);
		logger.info("CRIF password is "+password);
	}catch(Exception e){
		e.printStackTrace();
	}
	return password;
}
public String sendCrifRequest(CrifVO CrifVO) throws InvocationTargetException {
	String restype="";
	String strmssidnNumber = "";
	String requestXML=CrifVO.getRequestXML();
	String userId =CrifVO.getUserId();
	//String bdate=CrifVO.getMakerDate();
	String password=CrifVO.getPassword();
	/*String mbrid=CrifVO.getMbrid();
	String productType =CrifVO.getProductType();
	String productVersion=CrifVO.getProductVersion();
	String reqVolType=CrifVO.getReqVolType();
	String crifId=CrifVO.getCrifId();
	ArrayList list=new ArrayList();
	ArrayList Qlist=new ArrayList();
	String msg="";
	String htmlResponse="";
	*//********************************Start SSL Authentication**************************************//*
	try{
		//pooja code starts for crif request
		String output;
		String responseX="";
		HttpPost getRequest = new HttpPost("http://10.3.0.68:8089/InquiryAgentOriginal/doGet.service/request");
		// Add additional header to getRequest which accepts application/xml data
		getRequest.addHeader("accept", "application/xml");
		getRequest.setHeader("requestXml",requestXML);
		getRequest.setHeader("UserId",userId);
		getRequest.setHeader("password",password);
		// Execute your request and catch response
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(getRequest);
		// Check for HTTP response code: 200 = success
		if (response.getStatusLine().getStatusCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ response.getStatusLine().getStatusCode());
		}
		// Get-Capture Complete application/xml body response
		BufferedReader br2 = new BufferedReader(new InputStreamReader(
		(response.getEntity().getContent())));
		// Simply iterate through XML response and show on console.
		while ((output = br2.readLine()) != null) {
		responseX+=output;
		}
		logger.info("pooja Crif "+responseX);
		httpClient.getConnectionManager().shutdown();
		String  requestId=readResponsexml(responseX);
		String applicationId="Aghjj";
		String customerId="CNShjj";
		String crifbureau="CIBIL";
		String responseType="ALL";
		//Thread.sleep(10000);
		//pooja code start for Crif Response
		String crifOutput;
		String crifResponseX="";
		HttpPost getResponse = new HttpPost("http://10.3.0.68:8089/InquiryAgentOriginal/doGet.service/response");
		// Add additional header to getResponse which accepts application/json data
		getResponse.addHeader("accept", "application/json");
//		getResponse.setHeader("applicationId",applicationId);
//		getResponse.setHeader("customerId", customerId);
		getResponse.setHeader("requestId", requestId);
//		getResponse.setHeader("bureau", crifbureau);
//		getResponse.setHeader("responseType", responseType);
		getResponse.setHeader("UserId", userId);
		getResponse.setHeader("password", password);
		
		// Execute your request and catch response
		httpClient = HttpClientBuilder.create().build();
		HttpResponse crifResponse = httpClient.execute(getResponse);
		// Check for HTTP response code: 200 = success
		if (crifResponse.getStatusLine().getStatusCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ crifResponse.getStatusLine().getStatusCode());
		}
		// Get-Capture Complete application/xml body response
		BufferedReader br = new BufferedReader(new InputStreamReader(
		(crifResponse.getEntity().getContent())));
		// Simply iterate through XML response and show on console.
		while ((crifOutput = br.readLine()) != null) {
			crifResponseX+=crifOutput;
		}
		httpClient.getConnectionManager().shutdown();
		JSONObject json=new JSONObject(crifResponseX);
		JSONArray lineItems = json.getJSONArray("bureauResponse");
		//for status list
		JSONArray statusList = json.getJSONArray("status");
		for (int i=0;i< statusList.length();i++) {
		JSONObject statusLine = (JSONObject) statusList.get(i);
		String bureau = statusLine.getString("bureauName");
		String status = statusLine.getString("type");
		String reamrk= statusLine.getString("remark");
		logger.info("pooja Crif bureau "+bureau);
		logger.info("pooja Crif status "+status);
		logger.info("pooja Crif reamrk "+reamrk);

		}
		for (int i=0;i<lineItems.length();i++) {
		JSONObject jsonLineItem = (JSONObject) lineItems.get(i);
		String bureau = jsonLineItem.getString("bureauName");
		htmlResponse = jsonLineItem.getString("htmlResponse");
		String pdfResponse= jsonLineItem.getString("pdfResponse");
		String xmlResponse= jsonLineItem.getString("xmlResponse");
		logger.info("pooja Crif bureau "+bureau);
		logger.info("pooja Crif pdfResponse "+pdfResponse);
		logger.info("Pooja Crif xmlResponse->"+xmlResponse);
		CrifVO.setPdfResponse(pdfResponse);
		CrifVO.setXmlResponse(xmlResponse);
		}
		String crifPdfResponse=CrifVO.getPdfResponse();
		if(!CommonFunction.checkNull(crifPdfResponse).equalsIgnoreCase("")){
			boolean status=false;
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			CibilVerificationVO cVo=new CibilVerificationVO();
			String query="UPDATE  CR_CIBIL_REF_DTL SET CIBIL_RESULT='S',REC_STATUS='A',RECEIVED_STRING='"+CommonFunction.checkNull(crifPdfResponse).trim()+"' WHERE  CIBIL_ID='"+CommonFunction.checkNull(CrifVO.getCibilId()).trim()+"' ";
			insertPrepStmtObject.setSql(query);
			logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
			ArrayList qryList=new ArrayList();
			qryList.add(insertPrepStmtObject);
			try
			{
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				String cibilDone="";
				if(status)
					cibilDone="Y";
				String selectQuery ="select customer_Id,cibil_date from cr_cibil_ref_dtl where CIBIL_ID='"+CommonFunction.checkNull(CrifVO.getCibilId()).trim()+"' ";
				ArrayList selectQueryValue =ConnectionDAO.sqlSelect(selectQuery);
				logger.info("Size : "+selectQueryValue.size());
				int size =selectQueryValue.size();
				for(int i=0;i<size;i++)
				{
					ArrayList CibilVerificationVO=(ArrayList)selectQueryValue.get(i);
					if(CibilVerificationVO.size()>0)
					{
						
						cVo.setCustomerId((CommonFunction.checkNull(CibilVerificationVO.get(0)).toString()));
						cVo.setCibilDate((CommonFunction.checkNull(CibilVerificationVO.get(1)).toString()));
						selectQueryValue.add(cVo);
					}
				}
				String query1="";
				if(CrifVO.getFunctionId().trim().equalsIgnoreCase("9000511"))
				{
				query1="update cr_deal_customer_m set  CIBIL_DONE='"+cibilDone+"',CIBIL_DATE='"+cVo.getCibilDate()+"', cibil_id = '"+CrifVO.getCibilId()+"' where customer_Id='"+cVo.getCustomerId()+"' ";
				}
				else
				{
				query1="update cr_lead_customer_m set  CIBIL_DONE='"+cibilDone+"',CIBIL_DATE='"+cVo.getCibilDate()+"', cibil_id = '"+CrifVO.getCibilId()+"' where customer_Id='"+cVo.getCustomerId()+"' ";
				}
				insertPrepStmtObject.setSql(query1);
				logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
				ArrayList qryList1=new ArrayList();
				qryList1.add(insertPrepStmtObject);
				try
				{
					status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
				}
			catch(Exception e)
			{e.printStackTrace();}	
			finally
			{
				insertPrepStmtObject=null;
					query1=null;
					qryList1.clear();
					qryList1=null;
				}
				//Parvez Ends
				// TODO Auto-generated method stub
				
			}
			catch(Exception e)
			{e.printStackTrace();}	
			finally
			{
				insertPrepStmtObject=null;
				query=null;
				qryList.clear();
				qryList=null;
			}
		}
		String  updateBureauStatus=readBureauResponsexml(CrifVO.getXmlResponse(),CrifVO);
		if(CrifVO.getFunctionId().trim().equalsIgnoreCase("9000511"))
		{
		writePdfStringToFile(CrifVO);
		}
		else
		{
		writePreDealPdfStringToFile(CrifVO);
		}
		httpClient.getConnectionManager().shutdown();
		//pooja code end for crif request
		}catch(Exception e){
		e.printStackTrace();
		msg="E";
	}
	return msg;
}
}*/
public String readResponsexml(String responseXml)
{
	String reportid="";
	try
	{
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource src = new InputSource();
		src.setCharacterStream(new StringReader(responseXml));
		Document doc = builder.parse(src);
		String status = doc.getElementsByTagName("STATUS").item(0).getTextContent();
		reportid = doc.getElementsByTagName("REQUEST-ID").item(0).getTextContent();
		logger.info(status);
		logger.info(reportid);
	}
	catch(Exception ex)
	{
		System.err.println(ex.toString());
	}
	return reportid;
}
private void writePdfStringToFile(CrifVO CrifVO) {
	String DealId=CrifVO.getDealId();
	String ApplicantName=CrifVO.getApplicantName();
	String htmlResponse=CrifVO.getPdfResponse();
	int ApplicantCustId=CrifVO.getApplicantCustId();
	String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CRIF_TEMPLATE'";
	String rpt = ConnectionDAO.singleReturn(query);
	//String rpt = "D:\\CRIF_TEMPLATE";
	File homeDir = new File(rpt);
	boolean isHomeDir = homeDir.isDirectory();
	
	if (isHomeDir) 
	{
		logger.info("the name you have entered is a directory  : " + homeDir);
		logger.info("the path is " + homeDir.getAbsolutePath());
	} 
else 
	{
		boolean success = (new File(rpt)).mkdir();
		logger.info("Directory created successfully with name  : "+ homeDir);
		logger.info("the path is " + homeDir.getAbsolutePath());
	}
	
	File directory = new File(rpt + "\\" +"DEAL_CAPTURING"); 
	
	boolean isDirectory = directory.isDirectory();
	if (isDirectory) 
		{
			logger.info("the name you have entered is a directory  : " + directory);
			logger.info("the path is " + directory.getAbsolutePath());
		} 
	else 
		{
		boolean success = (new File(rpt + "\\" + "DEAL_CAPTURING")).mkdir();      
		logger.info("Directory created successfully with name  : "+ rpt + "\\" +"DEAL_CAPTURING");
		logger.info("the path is " + directory.getAbsolutePath());
		}
	//pooja
			directory = new File(directory.getAbsolutePath() + "\\" + DealId);
			
			if (directory.isDirectory())
			{
				logger.info("the name you have entered is a directory  : " + directory);
				logger.info("the path is " + directory.getAbsolutePath());
			} 
		else 
			{
				boolean success = directory.mkdir();
				logger.info("Directory created successfully with name  : "+ rpt + "\\" + DealId);
				logger.info("the path is " + directory.getAbsolutePath());
			}
			//pooja
	
	directory = new File(directory.getAbsolutePath() + "\\" + ApplicantCustId);
	
	if (directory.isDirectory())
	{
		logger.info("the name you have entered is a directory  : " + directory);
		logger.info("the path is " + directory.getAbsolutePath());
	} 
else 
	{
		boolean success = directory.mkdir();
		logger.info("Directory created successfully with name  : "+ rpt + "\\" + ApplicantCustId);
		logger.info("the path is " + directory.getAbsolutePath());
	}

	String absoluteDirPath = directory.getAbsolutePath();	// Report Path

	String PDFBase64 =htmlResponse;
	String encodedString = PDFBase64; 
	byte[] encodedBytes=encodedString.getBytes();
	StringBuilder bufInsSql =	new StringBuilder();
	PrepStmtObject insertPrepStmtObject=null;
	ArrayList qryList=new ArrayList();
    try {
       // Base64Decoder decoder = new Base64Decoder(encodedBytes);
        //String timeStmp = System.nanoTime()+"";
    	 // String timeStmp =ConnectionDAO.singleReturn("SELECT date_format(NOW(),'%d-%m-%Y %H%i%s') ");
        String timeStmp =ConnectionDAO.singleReturn("SELECT date_format(NOW(),'%d-%m-%Y %H%i%s') ");
        byte[] decodedBytes;
        FileOutputStream fop;
        decodedBytes =  Base64.decodeBase64(encodedBytes); //new BASE64Decoder().decodeBuffer(encodedBytes);
        String fileName = DealId+"_"+ApplicantName+"_"+ApplicantCustId+"_"+"BureauInitiation"+"_"+timeStmp+".pdf"; // Report Name
        File file = new File(absoluteDirPath+"\\"+fileName);
        fop = new FileOutputStream(file);

        fop.write(decodedBytes);

        fop.flush();
        fop.close();
        logger.info("Created");
        
         bufInsSql.append("INSERT INTO GENERATED_REPORT_LINK_DTL (DEAL_ID,CUSTOMER_ID,REPORT_NAME,REPORT_PATH,STAGE_ID,MAKER_ID,MAKER_DATE) ");
         bufInsSql.append(" values ( ");
		 bufInsSql.append(" ?," );//DEAL_ID
		 bufInsSql.append(" ?," );//CUSTOMER_ID
		 bufInsSql.append(" ?," );//REPORT_NAME
		 bufInsSql.append(" ?," );//REPORT_PATH				
		 bufInsSql.append(" ?," );//STAGE_ID 
		 bufInsSql.append(" ?," );//MAKER_ID
		 bufInsSql.append(" STR_TO_DATE(?, '"+dateFormatWithTime+"')" );//MAKER_DATE
		 bufInsSql.append(" ) ");
		 
		 insertPrepStmtObject = new PrepStmtObject();
		 if(CommonFunction.checkNull(DealId).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(DealId).trim());
		 if(CommonFunction.checkNull(ApplicantCustId).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(ApplicantCustId).trim());
		 if(CommonFunction.checkNull(fileName).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(fileName).trim());
		 if(CommonFunction.checkNull(absoluteDirPath).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(absoluteDirPath).trim());
		 if(CommonFunction.checkNull(CrifVO.getStageID()).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(CrifVO.getStageID()).trim()); 
		 if(CommonFunction.checkNull(CrifVO.getMakerId()).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(CrifVO.getMakerId()).trim());
		 if(CommonFunction.checkNull(CrifVO.getMakerDate()).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(CrifVO.getMakerDate()).trim());
		 
		    insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN SaveRecord() insert query for Crif Report history  :  "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			boolean status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			insertPrepStmtObject=null;
			bufInsSql=null;
		 
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}

public String sendCrifRequest(CrifVO CrifVO) throws InvocationTargetException {
	String restype="";
	String strmssidnNumber = "";
	String requestXML=CrifVO.getRequestXML();
	String userId =CrifVO.getUserId();
	//String bdate=CrifVO.getMakerDate();
	String password=CrifVO.getPassword();
	/*String mbrid=CrifVO.getMbrid();
	String productType =CrifVO.getProductType();
	String productVersion=CrifVO.getProductVersion();
	String reqVolType=CrifVO.getReqVolType();
	String crifId=CrifVO.getCrifId();
	ArrayList list=new ArrayList();
	ArrayList Qlist=new ArrayList();*/
	String msg="";
	String htmlResponse="";
	String requestUrl="";
	String responseUrl="";
	try{
		String requestUrlquery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CRIF_REQUEST_URL'";
		requestUrl=ConnectionDAO.singleReturn(requestUrlquery);
		logger.info("CRIF request Url  is "+requestUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	try{
		String responseUrlquery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CRIF_RESPONSE_URL'";
		responseUrl=ConnectionDAO.singleReturn(responseUrlquery);
		logger.info("CRIF request Url  is "+responseUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	/********************************Start SSL Authentication**************************************/
	try{
		//pooja code starts for crif request
		String output;
		String responseX="";
		HttpPost getRequest = new HttpPost(requestUrl);
		// Add additional header to getRequest which accepts application/xml data
		getRequest.addHeader("accept", "application/xml");
		getRequest.setHeader("requestXml",requestXML);
		getRequest.setHeader("UserId",userId);
		getRequest.setHeader("password",password);
		// Execute your request and catch response
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(getRequest);
		// Check for HTTP response code: 200 = success
		if (response.getStatusLine().getStatusCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ response.getStatusLine().getStatusCode());
		}
		// Get-Capture Complete application/xml body response
		BufferedReader br2 = new BufferedReader(new InputStreamReader(
		(response.getEntity().getContent())));
		// Simply iterate through XML response and show on console.
		while ((output = br2.readLine()) != null) {
		responseX+=output;
		}
		logger.info("pooja Crif "+responseX);
		httpClient.getConnectionManager().shutdown();
		String  requestId=readResponsexml(responseX);
		/*String applicationId="Aghjj";
		String customerId="CNShjj";
		String crifbureau="CIBIL";
		String responseType="ALL";*/
		
		Thread.sleep(30000);
		//pooja code start for Crif Response
		String crifOutput;
		String crifResponseX="";
		HttpPost getResponse = new HttpPost(responseUrl);
		// Add additional header to getResponse which accepts application/json data
		getResponse.addHeader("accept", "application/json");
//		getResponse.setHeader("applicationId",applicationId);
//		getResponse.setHeader("customerId", customerId);
		getResponse.setHeader("requestId", requestId);
//		getResponse.setHeader("bureau", crifbureau);
//		getResponse.setHeader("responseType", responseType);
		getResponse.setHeader("UserId", userId);
		getResponse.setHeader("password", password);
		
		// Execute your request and catch response
		httpClient = HttpClientBuilder.create().build();
		HttpResponse crifResponse = httpClient.execute(getResponse);
		// Check for HTTP response code: 200 = success
		if (crifResponse.getStatusLine().getStatusCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ crifResponse.getStatusLine().getStatusCode());
		}
		// Get-Capture Complete application/xml body response
		BufferedReader br = new BufferedReader(new InputStreamReader(
		(crifResponse.getEntity().getContent())));
		// Simply iterate through XML response and show on console.
		while ((crifOutput = br.readLine()) != null) {
			crifResponseX+=crifOutput;
		}
		httpClient.getConnectionManager().shutdown();
		JSONObject json=new JSONObject(crifResponseX);
		JSONArray lineItems = json.getJSONArray("bureauResponse");
		//for status list
		JSONArray statusList = json.getJSONArray("status");
		for (int i=0;i< statusList.length();i++) {
		JSONObject statusLine = (JSONObject) statusList.get(i);
		String bureau = statusLine.getString("bureauName");
		String status = statusLine.getString("type");
		String reamrk= statusLine.getString("remark");
		logger.info("pooja Crif bureau "+bureau);
		logger.info("pooja Crif status "+status);
		logger.info("pooja Crif reamrk "+reamrk);

		}
		for (int i=0;i<lineItems.length();i++) {
		JSONObject jsonLineItem = (JSONObject) lineItems.get(i);
		String bureau = jsonLineItem.getString("bureauName");
		htmlResponse = jsonLineItem.getString("htmlResponse");
		String pdfResponse= jsonLineItem.getString("pdfResponse");
		String xmlResponse= jsonLineItem.getString("xmlResponse");
		logger.info("pooja Crif bureau "+bureau);
		logger.info("pooja Crif pdfResponse "+pdfResponse);
		logger.info("Pooja Crif xmlResponse->"+xmlResponse);
		CrifVO.setPdfResponse(pdfResponse);
		CrifVO.setXmlResponse(xmlResponse);
		}
		String crifPdfResponse=CrifVO.getPdfResponse();
		if(!CommonFunction.checkNull(crifPdfResponse).equalsIgnoreCase("")){
			boolean status=false;
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			CibilVerificationVO cVo=new CibilVerificationVO();
			String forwardString=CrifVO.getRequestXML().replace("'", "");
			String resString=CrifVO.getXmlResponse().replaceAll("\'","");
			String query="UPDATE  CR_CIBIL_REF_DTL SET CIBIL_RESULT='S',REC_STATUS='A',RESPONSE_XML_STRING='"+CommonFunction.checkNull(resString).trim()+"',FORWORD_STRING='"+CommonFunction.checkNull(forwardString).trim()+"',RECEIVED_STRING='"+CommonFunction.checkNull(crifPdfResponse).trim()+"' WHERE  CIBIL_ID='"+CommonFunction.checkNull(CrifVO.getCibilId()).trim()+"' ";
			insertPrepStmtObject.setSql(query);
			logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
			ArrayList qryList=new ArrayList();
			qryList.add(insertPrepStmtObject); 
			try
			{
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				String cibilDone="";
				if(status)
					cibilDone="Y";
				String selectQuery ="select customer_Id,cibil_date from cr_cibil_ref_dtl where CIBIL_ID='"+CommonFunction.checkNull(CrifVO.getCibilId()).trim()+"' ";
				ArrayList selectQueryValue =ConnectionDAO.sqlSelect(selectQuery);
				logger.info("Size : "+selectQueryValue.size());
				int size =selectQueryValue.size();
				for(int i=0;i<size;i++)
				{
					ArrayList CibilVerificationVO=(ArrayList)selectQueryValue.get(i);
					if(CibilVerificationVO.size()>0)
					{
						
						cVo.setCustomerId((CommonFunction.checkNull(CibilVerificationVO.get(0)).toString()));
						cVo.setCibilDate((CommonFunction.checkNull(CibilVerificationVO.get(1)).toString()));
						selectQueryValue.add(cVo);
					}
				}
				String query1="";
				if(CrifVO.getFunctionId().trim().equalsIgnoreCase("9000511"))
				{
				query1="update cr_deal_customer_m set  CIBIL_DONE='"+cibilDone+"',CIBIL_DATE='"+cVo.getCibilDate()+"', cibil_id = '"+CrifVO.getCibilId()+"' where customer_Id='"+cVo.getCustomerId()+"' ";
				}
				else
				{
				query1="update cr_lead_customer_m set  CIBIL_DONE='"+cibilDone+"',CIBIL_DATE='"+cVo.getCibilDate()+"', cibil_id = '"+CrifVO.getCibilId()+"' where customer_Id='"+cVo.getCustomerId()+"' ";
				}
				insertPrepStmtObject.setSql(query1);
				logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
				ArrayList qryList1=new ArrayList();
				qryList1.add(insertPrepStmtObject);
				try
				{
					status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
				}
			catch(Exception e)
			{e.printStackTrace();}	
			finally
			{
				insertPrepStmtObject=null;
					query1=null;
					qryList1.clear();
					qryList1=null;
				}
				//Parvez Ends
				// TODO Auto-generated method stub
				
			}
			catch(Exception e)
			{e.printStackTrace();}	
			finally
			{
				insertPrepStmtObject=null;
				query=null;
				qryList.clear();
				qryList=null;
			}
		}
		String  updateBureauStatus=readBureauResponsexml(CrifVO.getXmlResponse(),CrifVO);
		if(CrifVO.getFunctionId().trim().equalsIgnoreCase("9000511"))
		{
		writePdfStringToFile(CrifVO);
		}
		else
		{
		writePreDealPdfStringToFile(CrifVO);
		}
		httpClient.getConnectionManager().shutdown();
		//pooja code end for crif request
		}catch(Exception e){
		e.printStackTrace();
		msg="E";
	}
	return msg;
}

private void writePreDealPdfStringToFile(CrifVO CrifVO) {
	String LeadId=CrifVO.getLbxLeadId();
	String ApplicantName=CrifVO.getApplicantName();
	String htmlResponse=CrifVO.getPdfResponse();
	int ApplicantCustId=CrifVO.getApplicantCustId();
	String query = "SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='CRIF_TEMPLATE'";
	String rpt = ConnectionDAO.singleReturn(query);
	//String rpt = "D:\\CRIF_TEMPLATE";
	File homeDir = new File(rpt);
	boolean isHomeDir = homeDir.isDirectory();
	
	if (isHomeDir) 
	{
		logger.info("the name you have entered is a directory  : " + homeDir);
		logger.info("the path is " + homeDir.getAbsolutePath());
	} 
else 
	{
		boolean success = (new File(rpt)).mkdir();
		logger.info("Directory created successfully with name  : "+ homeDir);
		logger.info("the path is " + homeDir.getAbsolutePath());
	}
	
	File directory = new File(rpt + "\\" + "SINGLE_PAGE"); 
	
	boolean isDirectory = directory.isDirectory();
	if (isDirectory) 
		{
			logger.info("the name you have entered is a directory  : " + directory);
			logger.info("the path is " + directory.getAbsolutePath());
		} 
	else 
		{
			boolean success = (new File(rpt + "\\" + "SINGLE_PAGE")).mkdir();   
			logger.info("Directory created successfully with name  : "+ rpt + "\\" + "SINGLE_PAGE");
			logger.info("the path is " + directory.getAbsolutePath());
		}
	//pooja
	directory = new File(directory.getAbsolutePath() + "\\" + LeadId);
	
	if (directory.isDirectory())
	{
		logger.info("the name you have entered is a directory  : " + directory);
		logger.info("the path is " + directory.getAbsolutePath());
	} 
	else 
	{
		boolean success = directory.mkdir();
		logger.info("Directory created successfully with name  : "+ rpt + "\\" + LeadId);
		logger.info("the path is " + directory.getAbsolutePath());
	}
	//pooja
	
	directory = new File(directory.getAbsolutePath() + "\\" + ApplicantCustId);
	
	if (directory.isDirectory())
	{
		logger.info("the name you have entered is a directory  : " + directory);
		logger.info("the path is " + directory.getAbsolutePath());
	} 
else 
	{
		boolean success = directory.mkdir();
		logger.info("Directory created successfully with name  : "+ rpt + "\\" + ApplicantCustId);
		logger.info("the path is " + directory.getAbsolutePath());
	}

	String absoluteDirPath = directory.getAbsolutePath();	// Report Path

	String PDFBase64 =htmlResponse;
	String encodedBytes = PDFBase64; 
	StringBuilder bufInsSql =	new StringBuilder();
	PrepStmtObject insertPrepStmtObject=null;
	ArrayList qryList=new ArrayList();
    try {
        BASE64Decoder decoder = new BASE64Decoder();
       // String timeStmp = System.nanoTime()+"";
        String timeStmp =ConnectionDAO.singleReturn("SELECT date_format(NOW(),'%d-%m-%Y %H%i%s') ");
        byte[] decodedBytes;
        FileOutputStream fop;
        decodedBytes = new BASE64Decoder().decodeBuffer(encodedBytes);
        String fileName = LeadId+"_"+ApplicantName+"_"+ApplicantCustId+"_"+"SinglePageBureau"+"_"+timeStmp+".pdf"; // Report Name
        logger.info("fileName--->"+fileName);
        File file = new File(absoluteDirPath+"\\"+fileName);
        fop = new FileOutputStream(file);

        fop.write(decodedBytes);

        fop.flush();
        fop.close();
        logger.info("Created");
        
         bufInsSql.append("INSERT INTO GENERATED_REPORT_LINK_DTL (LEAD_ID,CUSTOMER_ID,REPORT_NAME,REPORT_PATH,STAGE_ID,MAKER_ID,MAKER_DATE) ");
         bufInsSql.append(" values ( ");
		 bufInsSql.append(" ?," );//DEAL_ID
		 bufInsSql.append(" ?," );//CUSTOMER_ID
		 bufInsSql.append(" ?," );//REPORT_NAME
		 bufInsSql.append(" ?," );//REPORT_PATH				
		 bufInsSql.append(" ?," );//STAGE_ID 
		 bufInsSql.append(" ?," );//MAKER_ID
		 bufInsSql.append(" STR_TO_DATE(?, '"+dateFormatWithTime+"')" );//MAKER_DATE
		 bufInsSql.append(" ) ");
		 
		 insertPrepStmtObject = new PrepStmtObject();
		 if(CommonFunction.checkNull(LeadId).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(LeadId).trim());
		 if(CommonFunction.checkNull(ApplicantCustId).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(ApplicantCustId).trim());
		 if(CommonFunction.checkNull(fileName).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(fileName).trim());
		 if(CommonFunction.checkNull(absoluteDirPath).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(absoluteDirPath).trim());
		 if(CommonFunction.checkNull(CrifVO.getStageID()).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(CrifVO.getStageID()).trim()); 
		 if(CommonFunction.checkNull(CrifVO.getMakerId()).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(CrifVO.getMakerId()).trim());
		 if(CommonFunction.checkNull(CrifVO.getMakerDate()).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(CrifVO.getMakerDate()).trim());
		 
		    insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN SaveRecord() insert query for Crif Report history  :  "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			boolean status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			insertPrepStmtObject=null;
			bufInsSql=null;
		 
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }	
}

public String readBureauResponsexml(String responseXml,CrifVO CrifVO){
	String cibilID=CrifVO.getCibilId();
	int ApplicantCustId=CrifVO.getApplicantCustId();
	String rpt=responseXml;
	rpt = rpt.replaceAll("\\s","");
	rpt = rpt.substring(rpt.indexOf("</BUREAU-RESPONSES-SUMMARY><CREDIT-SCORES>"),rpt.indexOf("<ACCOUNT-SUMMARY><NUMBER-OF-ACCOUNTS-INQUIRIES>"));
	rpt= rpt.replace("</BUREAU-RESPONSES-SUMMARY>","");
	
	
	String HighMark = "0";
	String cibil = "-1";
	String experian = "0";
	
	if(rpt.contains("<CREDIT-SCORE><BUREAU>CRIFHighMark</BUREAU>")){
		HighMark = rpt.substring(rpt.indexOf("<CREDIT-SCORE><BUREAU>CRIFHighMark</BUREAU>"), rpt.indexOf("</SCORE><SCORING-FACTOR>"));
		HighMark = HighMark.substring(HighMark.indexOf("<SCORE>"), HighMark.length());
		HighMark = HighMark.replace("<SCORE>","");
	}
	if(rpt.contains("<CREDIT-SCORE><BUREAU>CIBIL</BUREAU>")){
		cibil = rpt.substring(rpt.indexOf("<BUREAU>CIBIL</BUREAU>"),rpt.length());
		cibil = cibil.substring(cibil.indexOf("<SCORE>"), cibil.indexOf("</SCORE>"));
		cibil = cibil.replace("<SCORE>", "");
	}
	if(rpt.contains("<CREDIT-SCORE><BUREAU>Experian</BUREAU>")){
		experian = rpt.substring(rpt.indexOf("<BUREAU>Experian</BUREAU>"),rpt.length());
		experian = experian.substring(experian.indexOf("<SCORE>"), experian.indexOf("</SCORE>"));
		experian = experian.replace("<SCORE>", "");
	}
	logger.info("High Mark Score : "+HighMark);
	logger.info("Cibil Score : "+cibil);
	logger.info("Experian Score : "+experian);
	/*cibilScoreSegment(CrifVO);
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	CibilVerificationVO cVo=new CibilVerificationVO();
	String query="UPDATE  CR_CIBIL_SCORE_SEGMENT SET score='"+cibil+"',CRIF_HIGH_MARK_SCORE='"+HighMark+"' WHERE  CIBIL_ID='"+CommonFunction.checkNull(cibilID).trim()+"' ";
	insertPrepStmtObject.setSql(query);
	logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
	ArrayList qryList=new ArrayList();
	qryList.add(insertPrepStmtObject);
	try
	{
	boolean	status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	logger.info("CRIF Score update--->"+status);
	}
	catch(Exception ex)
	{
		System.err.println(ex.toString());
	}*/
	StringBuilder bufInsSql =	new StringBuilder();
	PrepStmtObject insertPrepStmtObject=null;
	ArrayList qryList=new ArrayList();
	try{
		bufInsSql.append("INSERT INTO CR_CIBIL_SCORE_SEGMENT (CIBIL_ID,CUSTOMER_ID,SCORE,CRIF_HIGH_MARK_SCORE,EXPERIAN_SCORE) ");
        bufInsSql.append(" values ( ");
		 bufInsSql.append(" ?," );//CIBIL_ID
		 bufInsSql.append(" ?," );//CUSTOMER_ID
		 bufInsSql.append(" ?," );//SCORE				
		 bufInsSql.append(" ?," );//SCORE				
		 bufInsSql.append(" ? " );//CRIF_HIGH_MARK_SCORE 
		 bufInsSql.append(" ) ");
		 
		 insertPrepStmtObject = new PrepStmtObject();
		 if(CommonFunction.checkNull(CrifVO.getCibilId()).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(CrifVO.getCibilId()).trim());
		 if(CommonFunction.checkNull(ApplicantCustId).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(ApplicantCustId).trim());
		 if(CommonFunction.checkNull(cibil).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(cibil).trim());
		 if(CommonFunction.checkNull(HighMark).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(HighMark).trim());
		 
		 if(CommonFunction.checkNull(experian).trim().equalsIgnoreCase(""))
			 insertPrepStmtObject.addNull();
		 else 
			 insertPrepStmtObject.addString(CommonFunction.checkNull(experian).trim());
		 
		    insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN SaveRecord() insert query for Crif Report history  :  "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			boolean status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			insertPrepStmtObject=null;
			bufInsSql=null;
		 
   } catch (Exception e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
   }
	return null;
}
}


