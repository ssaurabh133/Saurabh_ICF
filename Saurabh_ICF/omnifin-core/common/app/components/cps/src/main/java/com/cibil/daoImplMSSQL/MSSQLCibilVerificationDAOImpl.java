package com.cibil.daoImplMSSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cibil.dao.CibilVerificationDAO;
import com.cibil.vo.CibilVerificationVO;
import com.communication.engn.vo.EmailVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cibil.vo.CrifVO;


public class MSSQLCibilVerificationDAOImpl implements CibilVerificationDAO 
{
	private static final Logger logger = Logger.getLogger(MSSQLCibilVerificationDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
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
					" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_NAME')MEMBER_NAME,(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_ID')MEMBER_ID," +
					" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='ENQUIRY-PURPOSE')ENQUIRY_PURPOSE,CUSTOMER_ID REF_NO,dbo.date_format(C.CUSTOMER_DOB,'%d-%m-%Y')," +
					" d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,d.ADDRESS_TYPE,d.ADDRESS_DETAIL,e.DISTRICT_DESC,f.STATE_DESC,d.PINCODE,g.COUNTRY_DESC,c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_NO," +
					" voter_id,passport_number,c.DRIVING_LICENSE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX, isnull(CIBIL_DONE,'N')CIBIL_DONE" +
					" from cr_deal_dtl a " +
					" join CR_DEAL_CUSTOMER_ROLE b on(a.DEAL_ID=b.DEAL_ID)" +
					" join CR_DEAL_CUSTOMER_M c on(b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID)" +
					" join CR_DEAL_ADDRESS_M D ON(D.BPID=b.DEAL_CUSTOMER_ID AND D.COMMUNICATION_ADDRESS='Y')" +
					" left join com_district_m e on(e.DISTRICT_ID=d.DISTRICT)" +
					" left join COM_STATE_M f on(f.STATE_ID=d.STATE)" +
					" left join com_country_m g on(g.COUNTRY_ID=d.COUNTRY)" +
					" where a.deal_id='"+obj.getDealID()+"' ");
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
	
	
	public ArrayList getCblViewGridList(CibilVerificationVO vo) 
	{
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
					" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_NAME')MEMBER_NAME,(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_ID')MEMBER_ID," +
					" (select PARAMETER_VALUE from PARAMETER_MST where parameter_key='ENQUIRY-PURPOSE')ENQUIRY_PURPOSE,CB.CUSTOMER_ID REF_NO,dbo.date_format(C.CUSTOMER_DOB,'%d-%m-%Y')," +
					" d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,d.ADDRESS_TYPE,d.ADDRESS_DETAIL,e.DISTRICT_DESC,f.STATE_DESC,d.PINCODE,g.COUNTRY_DESC,c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_NO," +
					" CB.voter_id,passport_number,c.DRIVING_LICENSE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX " +
					" ,CIBIL_ID, CASE CIBIL_RESULT WHEN 'E' THEN 'ERROR RESPONSE' WHEN 'S' THEN 'POSITIVE RESPONSE' WHEN 'I' THEN 'INVALID RESPONSE' WHEN 'N' THEN 'NO RESPONSE' END AS CIBIL_RESULT,dbo.date_format(CIBIL_DATE,'%d-%m-%Y')  "+
					" from cr_deal_dtl a " +
					" join CR_CIBIL_REF_DTL CB on(a.DEAL_ID=CB.DEAL_ID)" +					
					" join CR_DEAL_CUSTOMER_ROLE b on(a.DEAL_ID=b.DEAL_ID and b.DEAL_CUSTOMER_ID=CB.CUSTOMER_ID)" +
					" join CR_DEAL_CUSTOMER_M c on(b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID)" +
					" join CR_DEAL_ADDRESS_M D ON(D.BPID=b.DEAL_CUSTOMER_ID AND D.COMMUNICATION_ADDRESS='Y')" +
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
	
	public ArrayList getCblViewCustomerGridList(CibilVerificationVO vo) {
		logger.info("in getCblViewCustomerGridList() ");
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList formatlist=null;
		ArrayList data=null;
		ArrayList resultList=new ArrayList();		
		CibilVerificationVO obj=(CibilVerificationVO)vo;
		try
		{
			bufInsSql.append("select d.deal_id, deal_no,d.DEAL_CUSTOMER_ID,deal.CUSTOMER_NAME,DATE_FORMAT(deal_date,'%d-%m-%Y'),p.PRODUCT_DESC,"+
					"s.SCHEME_DESC,l.DEAL_PRODUCT_CATEGORY,d.REC_STATUS,S.MIN_AMT_FIN,S.MAX_AMT_FIN  from cr_deal_dtl d "+
					" left join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID left join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID"+
					 "left join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID "+
					"left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID"+ 
										" where a.deal_id='"+obj.getDealID()+"' ");
			 logger.info("in getCblViewGridList() Search Query  :  "+bufInsSql.toString());
	
			  formatlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
              for (int i=0;i<formatlist.size();i++)
              {
					data = (ArrayList) formatlist.get(i);
					CibilVerificationVO dvo= new CibilVerificationVO(); 
					dvo.setDealID(CommonFunction.checkNull(data.get(0)).toString());
					dvo.setDealNO(CommonFunction.checkNull(data.get(1)).toString());
					dvo.setCustomerId(CommonFunction.checkNull(data.get(3)).toString());
					dvo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
					
				/*	dvo.setDeal_date(CommonFunction.checkNull(data.get(4)).toString());
					dvo.set_(CommonFunction.checkNull(data.get(5)).toString());
					dvo.setSCHEME_DESC(CommonFunction.checkNull(data.get(6)).toString());
					dvo.setDEAL_PRODUCT_CATEGORY(CommonFunction.checkNull(data.get(7)).toString());
					dvo.setREC_STATUS(CommonFunction.checkNull(data.get(8)).toString());
					dvo.setRefranceNo(CommonFunction.checkNull(data.get(9)).toString());
					dvo.setMIN_AMT_FIN(CommonFunction.checkNull(data.get(10)).toString());
					dvo.setMAX_AMT_FIN(CommonFunction.checkNull(data.get(11)).toString());*/
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
	    String sqlQuery="SELECT MAX(BATCH_ID) FROM CR_CIBIL_REF_DTL";
	    strBatchId=CommonFunction.checkNull(ConnectionDAO.singleReturn(sqlQuery)).trim();
	    if(CommonFunction.checkNull(strBatchId).trim().equalsIgnoreCase(""))
	    	strBatchId="0";
	    batchId=Integer.parseInt(strBatchId);
	    batchId=batchId+1;
	    
	    try
		{
			bufInsSql.append("select b.DEAL_CUSTOMER_ID,(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_NAME')MEMBER_NAME," +
					"(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='MEMBER_ID')MEMBER_ID, " +
					"(select PARAMETER_VALUE from PARAMETER_MST where parameter_key='ENQUIRY-PURPOSE')ENQUIRY_PURPOSE,  b.DEAL_CUSTOMER_ID," +
					"c.CUSTOMER_NAME,GENDER,dbo.date_format(C.CUSTOMER_DOB,'%d-%m-%Y')CUSTOMER_DOB,d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,ADDRESS_TYPE,ADDRESS_DETAIL,e.DISTRICT_DESC,f.CIBIL_STATE_ID,d.PINCODE," +
					"g.COUNTRY_DESC,c.CUSTMER_PAN,voter_id,passport_number,c.DRIVING_LICENSE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,A.DEAL_ID " +
					"from cr_deal_dtl a  " +
					"join CR_DEAL_CUSTOMER_ROLE b on(a.DEAL_ID=b.DEAL_ID) " +
					"join CR_DEAL_CUSTOMER_M c on(b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID) " +
					"join CR_DEAL_ADDRESS_M D ON(D.BPID=b.DEAL_CUSTOMER_ID AND D.COMMUNICATION_ADDRESS='Y') " +
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
				 " MOBILE_NO1,TELEPHONE_NO1,TELEPHONE_NO2,TELEPHONE_NO3,CIBIL_DATE,CIBIL_RESULT,REC_STATUS,REPORT_STATUS,DEAL_ID)");
				 bufInsSql.append(" values ( ");
				 bufInsSql.append(" ?," );//BATCH_ID
				 bufInsSql.append(" ?," );//CUSTOMER_ID
				 bufInsSql.append(" ?," );//MEMBER_NAME
				 bufInsSql.append(" ?," );//MEMBER_ID				
				 bufInsSql.append(" ?," );//ENQUIRY_PURPOSE 
				 bufInsSql.append(" ?," );//REFERENCE_NUMBER
				 bufInsSql.append(" ?," );//NAME
				 bufInsSql.append(" ?," );//GENDER
				 bufInsSql.append(" dbo.STR_TO_DATE(?, '"+dateFormat+"')," );//DATE_OF_BIRTH				 
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
				 bufInsSql.append(dbo);
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')+ ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");//CIBIL_DATE
				 bufInsSql.append(" ?," );//CIBIL_RESULT
				 bufInsSql.append(" ?," );//REC_STATUS
				 bufInsSql.append(" ?," );//REPORT_STATUS
				 bufInsSql.append(" ?)" );//DEAL_ID
					 	
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

	public boolean updateResString(String cibilId, String resString) 
	{
		boolean status=false;	
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String header="";
		String cibilResult="";
		if(CommonFunction.checkNull(resString).trim().length()>=4)
			header=resString.substring(0, 4);
		if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("TUEF"))
			cibilResult="S";
		else if(CommonFunction.checkNull(header).trim().equalsIgnoreCase("ERRR")) 
			cibilResult="E";
		else
			cibilResult="I";				
		String query="UPDATE  CR_CIBIL_REF_DTL SET CIBIL_RESULT='"+cibilResult+"',REC_STATUS='A',RECEIVED_STRING='"+CommonFunction.checkNull(resString).trim()+"' WHERE  CIBIL_ID='"+CommonFunction.checkNull(cibilId).trim()+"' ";
		insertPrepStmtObject.setSql(query);
		logger.info("IN updateResString() update Query :  "+insertPrepStmtObject.printQuery());
		ArrayList qryList=new ArrayList();
		qryList.add(insertPrepStmtObject);
		try
		{
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
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
		// TODO Auto-generated method stub
		return false;
	} 
	
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
	
	
	public String GenerateView(String cibilId)
	{			
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
		String Query = "select max(CIBIL_ID) from cr_cibil_ref_dtl where deal_id '"+dealId+"' and customer_id = '"+customerId+"'";
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList getpreDealGridList(CibilVerificationVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList SaveRecordPreDeal(String leadId, int customerId,
			String businessDate) {
		// TODO Auto-generated method stub
		return null;
	}
	/*@Override
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
	}*/
//pooja code for crif
	@Override
	public String sendCrifRequest(CrifVO CrifVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getCRIFUserId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getCRIFPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean updatePreDealResString(String cibilId, String resString) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ArrayList getRoleList(String leadId) {
		// TODO Auto-generated method stub
		return null;
	}
}







