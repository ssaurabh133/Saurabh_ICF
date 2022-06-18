package com.cp.daoImplMSSQL;



import java.sql.CallableStatement;
import java.sql.Connection;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;



import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;


import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.NamedParameterStatement;
import com.connect.PrepStmtObject;

import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.cp.vo.dedupeReferalVo;
import com.logger.LoggerMsg;

public class MSSQLDedupeReferalDaoImpl {
	 public static String IDENTITY="DD"; 
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String searchQuery=resource.getString("msg.query");//rohit
	String searchQuery1=resource.getString("msg.query1");//rohit
	String noOfRecord=resource.getString("msg.record");//rohit
	private static final Logger logger = Logger
			.getLogger(MSSQLDedupeReferalDaoImpl.class.getName());
	
	StringBuffer bufInsUpdSql = null;
//	ArrayList qryList = null;
//	ArrayList qryList1 = null;
	CallableStatement cs = null;
	PrepStmtObject  delPrepStmtObject = null;
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	//DecimalFormat myFormatter = new DecimalFormat("###.##");
	
//	ArrayList qryListB = null;
	//ArrayList qryListS = null;
	  public void proForDedupeDecision(String str,String txnID)
	    {
		  logger.info("str..................."+ str);
		  logger.info("txnID................."+txnID);
	    	StringBuilder query=new StringBuilder();
	    	StringBuilder query1=new StringBuilder();
	    	boolean status= false;
			String txnType=CommonFunction.checkNull(str).trim();
			ArrayList list=new ArrayList();
			 CreditProcessingLeadDetailDataVo vo = new CreditProcessingLeadDetailDataVo();
			try{
				ArrayList searchlist=new ArrayList();
				ArrayList searchlist1=new ArrayList();
				if(!CommonFunction.checkNull(txnType).equalsIgnoreCase("") && !CommonFunction.checkNull(txnID).equalsIgnoreCase(""))
				{
					if(txnType.equalsIgnoreCase("LE"))
					{
						query.append("SELECT b.LEAD_ID,a.customer_id,a.CUSTOMER_NAME,a.PRIMARY_PHONE,a.customer_email,a.CUSTOMER_TYPE,a.CUSTOMER_CONSTITUTION,a.CUSTMER_PAN, ");
						query.append("a.CUSTOMER_DOB,a.DRIVING_LICENSE,a.VOTER_ID,a.PASSPORT_NUMBER,a.FATHER_HUSBAND_NAME,a.FIRST_NAME,a.MIDDLE_NAME,a.LAST_NAME,a.DATE_OF_INCORPORATION, a.REGISTRATION_NUMBER,a.UID,a.VAT_REGISTRATION,a.TIN_NUMBER,a.REFERENCE_NO,a.SOURCE_SYSTEM,a.DISTRICT,a.ADDRESS_LINE1,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.ADDRESS_STRING,a.PARTNER,a.DEAL_NO,a.DEAL_STATUS,a.dedupe_remarks,a.pincode,a.STATE,a.ALTERNATE_PHONE  FROM cr_customer_search_dtl  A ");
						query.append("join cr_lead_dtl b on a.customer_id=b.customer_id ");
						query.append(" WHERE  b.LEAD_ID ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnID)).trim()+"' AND '"+txnType+"' = 'LE';");
					}
				   if(txnType.equalsIgnoreCase("DC") ){
					   query.append("SELECT B.DEAL_ID,A.CUSTOMER_ID,A.CUSTOMER_NAME,A.PRIMARY_PHONE,A.CUSTOMER_EMAIL,A.CUSTOMER_TYPE,A.CUSTOMER_CONSTITUTION,A.CUSTMER_PAN, ");
						query.append(" A.CUSTOMER_DOB,A.DRIVING_LICENSE,A.VOTER_ID,A.PASSPORT_NUMBER,A.FATHER_HUSBAND_NAME,a.FIRST_NAME,a.MIDDLE_NAME,a.LAST_NAME,a.DATE_OF_INCORPORATION, a.REGISTRATION_NUMBER,a.UID,a.VAT_REGISTRATION,a.TIN_NUMBER,a.REFERENCE_NO,a.SOURCE_SYSTEM,a.DISTRICT,a.ADDRESS_LINE1,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.ADDRESS_STRING,a.PARTNER,a.DEAL_NO,a.DEAL_STATUS,a.dedupe_remarks,a.pincode,a.STATE,a.ALTERNATE_PHONE  FROM cr_customer_search_dtl  A ");
						query.append(" INNER JOIN CR_DEAL_CUSTOMER_ROLE B ON A.CUSTOMER_ID=B.DEAL_CUSTOMER_ID ");
					query.append("WHERE  B.DEAL_ID ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnID)).trim()+"' AND '"+txnType+"' = 'DC' ;");
				   }
						 
				   if(txnType=="LIM")
					{
					   query.append(" SELECT B.LOAN_ID,A.CUSTOMER_ID ,A.CUSTOMER_NAME,A.PRIMARY_PHONE,A.CUSTOMER_EMAIL,A.CUSTOMER_TYPE,A.CUSTOMER_CONSTITUTION,A.CUSTMER_PAN,CUSTOMER_DOB, ");
						query.append(" A.DRIVING_LICENSE,A.VOTER_ID,A.PASSPORT_NUMBER,A.FATHER_HUSBAND_NAME,a.FIRST_NAME,a.MIDDLE_NAME,a.LAST_NAME,isnull(a.DATE_OF_INCORPORATION,0000-00-00), a.REGISTRATION_NUMBER,a.UID,a.VAT_REGISTRATION,a.TIN_NUMBER,a.REFERENCE_NO,a.SOURCE_SYSTEM,a.ADDRESS_LINE1,a.DISTRICT,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.ADDRESS_STRING,a.PARTNER,a.DEAL_NO,a.DEAL_STATUS,a.dedupe_remarks,a.pincode,a.STATE,a.ALTERNATE_PHONE  FROM cr_customer_search_dtl  A ");
						query.append(" INNER JOIN CR_LOAN_DTL B ON A.CUSTOMER_ID=B.LOAN_CUSTOMER_ID ");	
					query.append(" WHERE  B.LOAN_ID ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnID)).trim()+"' AND '"+txnType+"' = 'LIM' ;");
					}
				}
				   
				   logger.info("In Selected Customers for Dedupe......... query..........."+query.toString());
				   searchlist = ConnectionDAOforEJB.sqlSelect(query.toString());
				   logger.info(" Selected Customers for Dedupe search Data size is...."+searchlist.size());
		
	/*			if(txnType!="" && txnID!="")
				{
					if(txnType=="LE")
					{
						query.append("SELECT b.LEAD_ID,a.customer_id,a.CUSTOMER_NAME,a.PRIMARY_PHONE,a.customer_email,a.CUSTOMER_TYPE,a.CUSTOMER_CONSTITUTION,a.CUSTMER_PAN, ");
						query.append("a.CUSTOMER_DOB,a.DRIVING_LICENSE,a.VOTER_ID,a.PASSPORT_NUMBER,a.FATHER_HUSBAND_NAME,a.FIRST_NAME,a.MIDDLE_NAME,a.LAST_NAME,a.DATE_OF_INCORPORATION, a.REGISTRATION_NUMBER,a.UID,a.VAT_REGISTRATION,a.TIN_NUMBER,a.REFERENCE_NO,a.SOURCE_SYSTEM,a.DISTRICT,a.ADDRESS_LINE1,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.ADDRESS_STRING,a.PARTNER,a.DEAL_NO,a.DEAL_STATUS,a.dedupe_remarks,a.pincode,a.STATE,a.ALTERNATE_PHONE  FROM cr_customer_search_dtl  A ");
						query.append("join cr_lead_dtl b on a.customer_id=b.customer_id ");
					query.append(" WHERE  b.LEAD_ID =? AND '"+txnType+"' = 'LE';");
					}
				   if(txnType=="DC")
				   {
					   query.append("SELECT B.DEAL_ID,A.CUSTOMER_ID,A.CUSTOMER_NAME,A.PRIMARY_PHONE,A.CUSTOMER_EMAIL,A.CUSTOMER_TYPE,A.CUSTOMER_CONSTITUTION,A.CUSTMER_PAN, ");
						query.append(" A.CUSTOMER_DOB,A.DRIVING_LICENSE,A.VOTER_ID,A.PASSPORT_NUMBER,A.FATHER_HUSBAND_NAME,a.FIRST_NAME,a.MIDDLE_NAME,a.LAST_NAME,a.DATE_OF_INCORPORATION, a.REGISTRATION_NUMBER,a.UID,a.VAT_REGISTRATION,a.TIN_NUMBER,a.REFERENCE_NO,a.SOURCE_SYSTEM,a.DISTRICT,a.ADDRESS_LINE1,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.ADDRESS_STRING,a.PARTNER,a.DEAL_NO,a.DEAL_STATUS,a.dedupe_remarks,a.pincode,a.STATE,a.ALTERNATE_PHONE  FROM cr_customer_search_dtl  A ");
						query.append(" INNER JOIN CR_DEAL_CUSTOMER_ROLE B ON A.CUSTOMER_ID=B.DEAL_CUSTOMER_ID ");
					query.append("WHERE b.DEAL_ID =? AND '"+txnType+"' = 'DC' ;");
				   }
						 
				   if(txnType=="LIM")
					{
					   query.append(" SELECT B.LOAN_ID,A.CUSTOMER_ID ,A.CUSTOMER_NAME,A.PRIMARY_PHONE,A.CUSTOMER_EMAIL,A.CUSTOMER_TYPE,A.CUSTOMER_CONSTITUTION,A.CUSTMER_PAN,CUSTOMER_DOB, ");
						query.append(" A.DRIVING_LICENSE,A.VOTER_ID,A.PASSPORT_NUMBER,A.FATHER_HUSBAND_NAME,a.FIRST_NAME,a.MIDDLE_NAME,a.LAST_NAME,isnull(a.DATE_OF_INCORPORATION,0000-00-00), a.REGISTRATION_NUMBER,a.UID,a.VAT_REGISTRATION,a.TIN_NUMBER,a.REFERENCE_NO,a.SOURCE_SYSTEM,a.ADDRESS_LINE1,a.DISTRICT,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.ADDRESS_STRING,a.PARTNER,a.DEAL_NO,a.DEAL_STATUS,a.dedupe_remarks,a.pincode,a.STATE,a.ALTERNATE_PHONE  FROM cr_customer_search_dtl  A ");
						query.append(" INNER JOIN CR_LOAN_DTL B ON A.CUSTOMER_ID=B.LOAN_CUSTOMER_ID ");	
					query.append(" WHERE  b.LOAN_ID =? AND '"+txnType+"' = 'LIM' ;");
					}
				}
				   logger.info("txnID is ........"+txnID);
				   logger.info("In searchDedupeData......... query..........."+query.toString());
				   searchlist = ConnectionDAO.sqlSelectStringPrepStmt(query.toString(), txnID);
				   logger.info("searchDedupeData search Data size is...."+searchlist.size());*/
		
				   for(int i=0;i<searchlist.size();i++){
					   
		
				   ArrayList data=(ArrayList)searchlist.get(i);
		
					 if(data.size()>0){
						
						 vo.setTxnId((CommonFunction.checkNull(data.get(0)).trim()));
						 vo.setCustomerId((CommonFunction.checkNull(data.get(1)).trim()));
						 vo.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
						 vo.setMobile((CommonFunction.checkNull(data.get(3)).trim()));
						 vo.setEmail((CommonFunction.checkNull(data.get(4)).trim()));
						 vo.setCustomerType((CommonFunction.checkNull(data.get(5)).trim()));
						 vo.setConstitution((CommonFunction.checkNull(data.get(6)).trim())); 
					     vo.setCustPan((CommonFunction.checkNull(data.get(7)).trim()));
						 vo.setCustDOB((CommonFunction.checkNull(data.get(8)).trim()));
					     vo.setDlNumber((CommonFunction.checkNull(data.get(9)).trim())); 
					     vo.setVoterId((CommonFunction.checkNull(data.get(10)).trim()));
					     vo.setPassport((CommonFunction.checkNull(data.get(11)).trim()));  
						 vo.setFatherName((CommonFunction.checkNull(data.get(12)).trim()));
						 vo.setFirstName((CommonFunction.checkNull(data.get(13)).trim()));
						 vo.setMiddleName((CommonFunction.checkNull(data.get(14)).trim()));
						 vo.setLastName((CommonFunction.checkNull(data.get(15)).trim()));
						 vo.setDateOfIncorporation((CommonFunction.checkNull(data.get(16)).trim()));
						 vo.setRegistrationNo((CommonFunction.checkNull(data.get(17)).trim()));
						 vo.setUidNo((CommonFunction.checkNull(data.get(18)).trim()));
						 vo.setVatNo((CommonFunction.checkNull(data.get(19)).trim()));
						 vo.setTinNo((CommonFunction.checkNull(data.get(20)).trim()));
						 vo.setRefNo((CommonFunction.checkNull(data.get(21)).trim()));
						 vo.setSource((CommonFunction.checkNull(data.get(22)).trim()));
						 vo.setDist((CommonFunction.checkNull(data.get(23)).trim()));
						 vo.setAddress1((CommonFunction.checkNull(data.get(24)).trim()));
						 vo.setAddress2((CommonFunction.checkNull(data.get(25)).trim()));
						 vo.setAddress3((CommonFunction.checkNull(data.get(26)).trim()));
						 vo.setAddString((CommonFunction.checkNull(data.get(27)).trim()));
						 vo.setPartner((CommonFunction.checkNull(data.get(28)).trim()));
						 vo.setDealNo((CommonFunction.checkNull(data.get(29)).trim()));
						 vo.setDealStatus((CommonFunction.checkNull(data.get(30)).trim()));
						 vo.setRemarks((CommonFunction.checkNull(data.get(31)).trim()));
						 vo.setPincode((CommonFunction.checkNull(data.get(32)).trim()));
						 vo.setState((CommonFunction.checkNull(data.get(33)).trim()));
						 vo.setPhoneOff((CommonFunction.checkNull(data.get(34)).trim()));
						 list.add(vo);
			
						if(!CommonFunction.checkNull(txnID).equalsIgnoreCase(""))
					if(i==(searchlist.size()-1) && txnType.equalsIgnoreCase("DC"))
					{
							query1.append("UPDATE CR_DEAL_DTL SET DEDUPE_STATUS='Y' WHERE DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(txnID)).trim()+"' ");
							searchlist1.add(query1);
							status = ConnectionDAOforEJB.sqlInsUpdDelete(searchlist1);
							logger.info("Upadte for set deal visible in Dedupe Referal"+query1.toString());
					}		
							
							saveDedupeCustomerDetail(txnType,txnID,vo);
							 
						
						  
					 }
						 
				   }
			}
					catch(Exception e)
					{
						e.printStackTrace();
					}
							finally
							{
								query=null;
								vo=null;
							}
	        
	    }
	    public void saveDedupeCustomerDetail(String str,String txnId,CreditProcessingLeadDetailDataVo vo)
	 {

			
			StringBuilder query = new StringBuilder();
			StringBuilder query1 = new StringBuilder();
			String query2 = "";
			String query3 = "";
			// StringBuilder loanDtl=new StringBuilder();
			String txnType = CommonFunction.checkNull(str).trim();
			ArrayList list = new ArrayList();
			// ArrayList qryList=new ArrayList();
			// String txnId=CommonFunction.checkNull(str).trim();
			String CustomerId = CommonFunction.checkNull(vo.getCustomerId()).trim();
			String CustomerName = CommonFunction.checkNull(vo.getCustomerName())
					.trim();
			String Mobile = CommonFunction.checkNull(vo.getMobile()).trim();
			String Email = CommonFunction.checkNull(vo.getEmail()).trim();
			String CustomerType = CommonFunction.checkNull(vo.getCustomerType())
					.trim();
			String Constitution = CommonFunction.checkNull(vo.getConstitution())
					.trim();
			String CustPan = CommonFunction.checkNull(vo.getCustPan()).trim();
			String CustDOB = CommonFunction.checkNull(vo.getCustDOB()).trim();
			String DlNumber = CommonFunction.checkNull(vo.getDlNumber()).trim();
			String VoterId = CommonFunction.checkNull(vo.getVoterId()).trim();
			String Passport = CommonFunction.checkNull(vo.getPassport()).trim();
			String FatherName = CommonFunction.checkNull(vo.getFatherName()).trim();
			String FirstName = CommonFunction.checkNull(vo.getFirstName()).trim();
			String MiddleName = CommonFunction.checkNull(vo.getMiddleName()).trim();
			String LastName = CommonFunction.checkNull(vo.getLastName()).trim();
			String DateOfIncorporation = CommonFunction.checkNull(
					vo.getDateOfIncorporation()).trim();
			String RegistrationNo = CommonFunction
					.checkNull(vo.getRegistrationNo()).trim();
			String UidNo = CommonFunction.checkNull(vo.getUidNo()).trim();
			String VatNo = CommonFunction.checkNull(vo.getVatNo()).trim();
			String TinNo = CommonFunction.checkNull(vo.getTinNo()).trim();
			String RefNo = CommonFunction.checkNull(vo.getRefNo()).trim();
			String Source = CommonFunction.checkNull(vo.getSource()).trim();
			String District = CommonFunction.checkNull(vo.getDist()).trim();
			String Address1 = CommonFunction.checkNull(vo.getAddress1()).trim();
			String Address2 = CommonFunction.checkNull(vo.getAddress2()).trim();
			String Address3 = CommonFunction.checkNull(vo.getAddress3()).trim();
			String AddString = CommonFunction.checkNull(vo.getAddString()).trim();
			String Partner = CommonFunction.checkNull(vo.getPartner()).trim();
			String DealNo = CommonFunction.checkNull(vo.getDealNo()).trim();
			String DealStatus = CommonFunction.checkNull(vo.getDealStatus()).trim();
			String DedupeRemarks = CommonFunction.checkNull(vo.getRemarks()).trim();
			String Pincode = CommonFunction.checkNull(vo.getPincode()).trim();
			String State = CommonFunction.checkNull(vo.getState()).trim();
			String AlternatePhone = CommonFunction.checkNull(vo.getPhoneOff())
					.trim();
			
			String record = "E";
		//	String firstletter1 = "";
			//String firstletter2 = "";
			if (!(CommonFunction.checkNull(vo.getFirstName())).trim()
					.equalsIgnoreCase("")
					&& !(CommonFunction.checkNull(vo.getLastName())).trim()
							.equalsIgnoreCase("")) {
			/*	firstletter1 = String.valueOf((vo.getFirstName()).charAt(0));
				firstletter2 = String.valueOf((vo.getLastName()).charAt(0));*/
			}
			boolean status = false;
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			Connection connection = null;
			connection = NamedParameterStatement.getConnection();
	    	  StringBuffer bufIns =	new StringBuffer(); 
			CreditProcessingLeadDetailDataVo vo1 = new CreditProcessingLeadDetailDataVo();

			try {
				ArrayList searchlist = new ArrayList();

				if (txnType != "" && txnId != "") {
					{

						

						if (vo.getCustomerType().equalsIgnoreCase("I")) {
							
							
							bufIns.append("  select distinct vw.RuleNo, vw.RuleWeightage, tbl.CUSTOMER_ID,CUSTOMER_NAME,PRIMARY_PHONE,CUSTOMER_EMAIL,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTMER_PAN,CUSTOMER_DOB,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,FATHER_HUSBAND_NAME,FIRST_NAME,MIDDLE_NAME,");	
							bufIns.append(" LAST_NAME, DATE_OF_INCORPORATION, REGISTRATION_NUMBER,UID,VAT_REGISTRATION,TIN_NUMBER,REFERENCE_NO,SOURCE_SYSTEM,"); 
							bufIns.append("  DISTRICT,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,ADDRESS_STRING,PARTNER,tbl.DEAL_NO,DEAL_STATUS,dedupe_remarks,pincode,");	
							bufIns.append(" state,ALTERNATE_PHONE  from cr_customer_search_dtl tbl,   (( "); 
							bufIns.append(" select  '1' as RuleNo, '10' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl  ");	  
							bufIns.append(" where customer_type='I'   ");	
							bufIns.append(" and ((VOTER_ID='"+vo.getVoterId()+"' AND ifnull(VOTER_ID,'')!='') OR (DRIVING_LICENSE='"+vo.getDlNumber()+"' AND ifnull(DRIVING_LICENSE,'')!='') OR (PASSPORT_NUMBER='"+vo.getPassport()+"' AND ifnull(PASSPORT_NUMBER,'')!='') OR (CUSTMER_PAN='"+vo.getCustPan()+"' AND ifnull(CUSTMER_PAN,'')!='') OR (UID='"+vo.getUidNo()+"' AND ifnull(UID,'')!='') and (CUSTOMER_EMAIL ='"+vo.getEmail()+"' AND ifnull(CUSTOMER_EMAIL,'')!='')) limit 30 ) ");	  
							bufIns.append(" union all  	( "); 
							bufIns.append(" select  '2' as RuleNo, '7' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl ");	   
							bufIns.append(" where customer_type='I' and  ");	 
							bufIns.append("  ((SOUNDEX(FIRST_NAME) LIKE SOUNDEX('"+vo.getFirstName()+"') AND SOUNDEX(LAST_NAME) LIKE SOUNDEX('"+vo.getLastName()+"') ) AND (( DISTRICT='"+vo.getDist()+"'  AND ADDRESS_STRING LIKE '"+vo.getAddString()+"'  AND PINCODE='"+vo.getPincode()+"' ) AND (PRIMARY_PHONE='"+vo.getMobile()+"'  AND ifnull(PRIMARY_PHONE,'')!=''))  ) limit 3 ) ");	  
							bufIns.append("  union all  (  "); 
							bufIns.append(" 	select  '3' as RuleNo, '5' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl ");   
							bufIns.append("	where customer_type='I' ");
							bufIns.append(" and (FIRST_NAME ='"+vo.getFirstName()+"'  AND LAST_NAME ='"+vo.getLastName()+"'  AND FATHER_HUSBAND_NAME ='"+vo.getFatherName()+"'  )limit 3 ) ");	  
							bufIns.append(" union all  	( ");	  
							bufIns.append(" 	select  '4' as RuleNo, '4' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl ");	   
							bufIns.append(" where customer_type='I'  ");	 
							bufIns.append("and (FIRST_NAME LIKE '"+vo.getFirstName()+"'  AND LAST_NAME LIKE '"+vo.getLastName()+"'  AND (CUSTOMER_DOB='"+vo.getCustDOB()+"'  AND ifnull(CUSTOMER_DOB,'')!='')) limit 3  ) ");	  
							bufIns.append("	union all  	( "); 
							bufIns.append("	select  '5' as RuleNo, '3' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl ");   
							bufIns.append("where customer_type='I'  ");	 
							bufIns.append(" 	and (FIRST_NAME LIKE '"+vo.getFirstName()+"'  AND LAST_NAME LIKE '"+vo.getLastName()+"' AND FATHER_HUSBAND_NAME LIKE '"+vo.getFatherName()+"'   AND (CUSTOMER_DOB='"+vo.getCustDOB()+"' AND ifnull(CUSTOMER_DOB,'')!='') ) limit 3 ) union all ");	  
							bufIns.append("	(  ");
							bufIns.append("	select  '6' as RuleNo, '2' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl ");	   
							bufIns.append("	where customer_type='I'   ");	
							bufIns.append("	and ((CUSTOMER_EMAIL ='"+vo.getEmail()+"'  AND ifnull(CUSTOMER_EMAIL,'')!='')  AND  (CUSTOMER_DOB='"+vo.getCustDOB()+"'  AND ifnull(CUSTOMER_DOB,'')!='') AND (PRIMARY_PHONE='"+vo.getMobile()+"'  AND ifnull(PRIMARY_PHONE,'')!='')) limit 3  ) ");	   
							bufIns.append(" )vw  where tbl.CUSTOMER_ID=vw.CUSTOMER_ID  and  tbl.customer_type='I' limit 20 ;  "); 	
															   
							
							logger.info("Search query for dedupe individual Case :     "+bufIns.toString());
							
							
							query2 =  "	"+searchQuery+" " ;
							NamedParameterStatement parameter = new NamedParameterStatement(
									connection, query2);
							parameter.setString("VoterId", VoterId);
							parameter.setString("DlNumber", DlNumber);
							parameter.setString("Passport", Passport);
							parameter.setString("CustPan", CustPan);
							parameter.setString("UidNo", UidNo);
							parameter.setString("Email", Email);
							parameter.setString("District", District);
							parameter.setString("AddString", AddString);
							parameter.setString("Pincode", Pincode);
							parameter.setString("Mobile", Mobile);
							parameter.setString("FirstName", FirstName);
							parameter.setString("LastName", LastName);
							parameter.setString("FatherName", FatherName);
							parameter.setString("CustDOB", CustDOB);

							//LoggerMsg.info(" CUSTOMER DETAILS  CR_CUSTOMER_SEARCH_DTL : "+ query2.toString());
							//PreparedStatement stmt= connection.prepareStatement(query2);
							//logger.info("Search Query: "+stmt.toString());
							java.sql.ResultSet rs = parameter.executeQuery();
							int noOfFields = (rs.getMetaData()).getColumnCount();
							while (rs.next()) {
								int counter = 1;
								// 1

								ArrayList records = new ArrayList();

								while (counter <= noOfFields) {

									records.add(rs.getString(counter));
									counter++;
								}

								searchlist.add(records);
								//logger.info("records are:"+records);
							}
							
							//logger.info("result set: "+rs);
							NamedParameterStatement.closeConnection(connection, rs);
							parameter.close();
						}

						

						else {
							
							bufIns.append("select distinct vw.RuleNo, vw.RuleWeightage, tbl.CUSTOMER_ID,CUSTOMER_NAME,PRIMARY_PHONE,CUSTOMER_EMAIL,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTMER_PAN,CUSTOMER_DOB,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,FATHER_HUSBAND_NAME,FIRST_NAME,MIDDLE_NAME "); 
							bufIns.append("	 LAST_NAME,DATE_OF_INCORPORATION, REGISTRATION_NUMBER,UID,VAT_REGISTRATION,TIN_NUMBER,REFERENCE_NO,SOURCE_SYSTEM ");
							bufIns.append("	DISTRICT,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,ADDRESS_STRING,PARTNER,tbl.DEAL_NO,DEAL_STATUS,dedupe_remarks,pincode ,state,ALTERNATE_PHONE  from cr_customer_search_dtl tbl, (  ( ");  
							bufIns.append("	select  '1' as RuleNo, '10' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl   ");
							bufIns.append("	where customer_type='C' and ( ((CUSTMER_PAN='"+vo.getCustPan()+"' AND ifnull(CUSTMER_PAN,'')!='') OR  (REGISTRATION_NUMBER='"+vo.getRegistrationNo()+"' AND ifnull(REGISTRATION_NUMBER,'')!='00000')  OR" +
									" (VAT_REGISTRATION='"+vo.getVatNo()+"' AND ifnull(VAT_REGISTRATION,'')!='') OR (TIN_NUMBER='"+vo.getTinNo()+"' AND ifnull(TIN_NUMBER,'')!='') OR (CUSTOMER_EMAIL ='"+vo.getEmail()+"' AND ifnull(CUSTOMER_EMAIL,'')!='')) ) limit 30  ) " +
									" union all ( ");  
							bufIns.append("	select  '2' as RuleNo, '7' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl   ");
							bufIns.append("	where customer_type='C'  and (Customer_Name ='"+vo.getCustomerName()+"' AND (DATE_OF_INCORPORATION='"+vo.getDateOfIncorporation()+"' AND " +
									"ifnull(DATE_OF_INCORPORATION,'')!='')) limit 3  	) union all (  ");
							bufIns.append("		select  '3' as RuleNo, '5' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl   ");
							bufIns.append("	where customer_type='C' and (SOUNDEX(Customer_Name) LIKE SOUNDEX('"+vo.getCustomerName()+"') AND" +
									" (DATE_OF_INCORPORATION='"+vo.getDateOfIncorporation()+"' AND ifnull(DATE_OF_INCORPORATION,'')!='') AND " +
									"(PRIMARY_PHONE='"+vo.getMobile()+"' AND ifnull(PRIMARY_PHONE,'')!='')) limit 3 ) union all  (  ");
							bufIns.append("	select  '4' as RuleNo, '4' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl   ");
							bufIns.append("	where customer_type='C' and (Customer_Name LIKE '"+vo.getCustomerName()+"' AND " +
									"( DISTRICT='"+vo.getDist()+"' AND ADDRESS_STRING LIKE '"+vo.getAddString()+"' AND PINCODE='"+vo.getPincode()+"') AND (PRIMARY_PHONE='"+vo.getMobile()+"' AND" +
									" ifnull(PRIMARY_PHONE,'')!='')) limit 3  ");
							bufIns.append("	) union all (  select  '5' as RuleNo, '3' as RuleWeightage, CUSTOMER_ID,DEAL_NO FROM cr_customer_search_dtl " +
									"where customer_type='C' AND ((PRIMARY_PHONE='"+vo.getMobile()+"' AND ifnull(PRIMARY_PHONE,'')!='') AND " +
									"(DATE_OF_INCORPORATION='"+vo.getDateOfIncorporation()+"' AND ifnull(DATE_OF_INCORPORATION,'')!='')  AND" +
									" (CUSTOMER_EMAIL ='"+vo.getEmail()+"' AND ifnull(CUSTOMER_EMAIL,'')!='') )limit 3 ) )vw where tbl.CUSTOMER_ID=vw.CUSTOMER_ID and " +
									"tbl.customer_type='C' limit 20; ");
							logger.info("Search query for dedupe Corporate Case :     "+bufIns.toString());
							
							query3 =  " "+ searchQuery1 +" " ;
							NamedParameterStatement parameter = new NamedParameterStatement(
									connection, query3);
							parameter.setString("CustPan", CustPan);
							parameter.setString("RegistrationNo", RegistrationNo);
							parameter.setString("VatNo", VatNo);
							parameter.setString("TinNo", TinNo);
							parameter.setString("Email", Email);
							parameter.setString("CustomerName", CustomerName);
							parameter.setString("DateOfIncorporation",
									DateOfIncorporation);
							parameter.setString("Mobile", Mobile);
							parameter.setString("District", District);
							parameter.setString("AddString", AddString);
							parameter.setString("Pincode", Pincode);
							//logger.info("parameter:"+parameter);

							//LoggerMsg.info(" CUSTOMER DETAILS  CR_CUSTOMER_SEARCH_DTL : "+ query3.toString());
							//PreparedStatement stmt= connection.prepareStatement(query3);
						/*	PreparedStatement stmt= parameter.getStatement();*/
							//logger.info("Search Query: "+stmt.toString());
							java.sql.ResultSet rs = parameter.executeQuery();
							int noOfFields = (rs.getMetaData()).getColumnCount();
							while (rs.next()) {
								int counter = 1;
								// 1

								ArrayList records = new ArrayList();

								while (counter <= noOfFields) {

									records.add(rs.getString(counter));
									counter++;
								}

								searchlist.add(records);
								//logger.info("records are:"+records);
							}
						//	logger.info(rs);
							NamedParameterStatement.closeConnection(connection, rs);
							parameter.close();
							
						}

						
					}
					LoggerMsg.info(" CUSTOMER DETAILS  CR_CUSTOMER_SEARCH_DTL : "
							+ searchlist);
					LinkedHashMap<String, CreditProcessingLeadDetailDataVo> map =removeDuplicateValue(searchlist);
					LinkedHashMap<String, CreditProcessingLeadDetailDataVo> linkmap =reArrangeValue(map,vo);
					for(Map.Entry<String, CreditProcessingLeadDetailDataVo> entry  : linkmap.entrySet())
						{
					vo1 = 	entry.getValue();
								int weightage=0;
						  		int totalWeightage=0;
						  		String StateDESC="";
						  		String DistrictDESC="";
						  		String totalMatch="";
						  		String DOB="";
						  		String DOI="";
						  		String ADD1="";
						  		String ADD2="";
						  		String ADD3="";
						  		String qryLimit="";
						//ArrayList data1=(ArrayList)searchlist.get(i);
							int counter = 0;

						PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
						StringBuffer bufInsSql = new StringBuffer();

						 bufInsSql.append( " INSERT INTO CR_DEDUPE_DTL (CUSTOMER_ID,CUSTOMER_NAME,PRIMARY_PHONE,EMAIL_ID,CUSTMER_TYPE,CUSTOMER_CONSTITUTION,PAN,CUSTOMER_DOB,DRIVING_LICENSE,VOTER_ID,PASSPORT_NO,FATHER_HUSBAND_NAME,WEIGHTAGE,TXN_TYPE,COMMON_ID,FIRST_NAME,MIDDLE_NAME,LAST_NAME,DATE_OF_INCORPORATION, REGISTRATION_NUMBER,UID,VAT_REGISTRATION,TIN_NUMBER,REFERENCE_NO,SOURCE_SYSTEM,DISTRICT,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,ADDRESS_STRING,DEAL_CUSTOMER_ID,PARTNER,DEAL_NO,DEAL_STATUS,REMARKS,PINCODE,STATE,ALTERNATE_PHONE,MATCHING_RECORD ) ");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append("STR_TO_DATE(");

						bufInsSql.append("DATE_FORMAT(?,'" + dateFormat + "'),'"
								+ dateFormat + "'),");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append("STR_TO_DATE(");

						bufInsSql.append("DATE_FORMAT(?,'" + dateFormat + "'),'"
								+ dateFormat + "'),");
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
						bufInsSql.append(" ? )");
						if(CustomerType.equalsIgnoreCase("I"))
						 {

												if (vo.getFirstName().equalsIgnoreCase(
														vo1.getFirstName())
														&& !(CommonFunction.checkNull(vo1
																.getFirstName())).trim()
																.equalsIgnoreCase("")) {

													totalMatch = " FIRST_NAME:       " + FirstName
															+ "\n";
												}
												if (vo.getLastName()
														.equalsIgnoreCase(vo1.getLastName())
														&& !(CommonFunction
																.checkNull(vo1.getLastName())).trim()
																.equalsIgnoreCase("")) {

													totalMatch = totalMatch + " LAST_NAME:    " + LastName
															+ "\n";
												}
												if (vo.getCustDOB().equalsIgnoreCase(vo1.getCustDOB())
														&& !(CommonFunction.checkNull(vo1.getCustDOB()))
																.trim().equalsIgnoreCase("")) {
											
														qryLimit = "  select  DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y') from cr_customer_search_dtl   where customer_id='"+vo1.getCustomerId()+"' and DEAL_NO='"+vo1.getDealNo()+"' ";
														
													DOB = ConnectionDAO.singleReturn(qryLimit
															.toString());
													totalMatch = totalMatch + " CUST_DOB:    " + DOB
															+ "\n";
													qryLimit=null;
												}
												if (vo.getFatherName().equalsIgnoreCase(
														vo1.getFatherName())
														&& !(CommonFunction.checkNull(vo1
																.getFatherName())).trim()
																.equalsIgnoreCase("")) {

													totalMatch = totalMatch + " FATHER_HUSBAND_NAME:    "
															+ FatherName + "\n";
												}
												if (vo.getAddress1()
														.equalsIgnoreCase(vo1.getAddress1())
														&& !(CommonFunction
																.checkNull(vo1.getAddress1())).trim()
																.equalsIgnoreCase("")) {
											
													qryLimit = " select b.ADDRESS_LINE1 from cr_customer_search_dtl a left join cr_deal_address_m b on b.bpid=a.customer_id  where a.customer_id='"+vo1.getCustomerId()+"' and DEAL_NO='"+vo1.getDealNo()+"' ";	
												
													ADD1=ConnectionDAO.singleReturn(qryLimit
															.toString());
													totalMatch = totalMatch + " ADDRESS1:    " + ADD1
															+ "\n";
													qryLimit=null;
												}

												if (vo.getAddress2()
														.equalsIgnoreCase(vo1.getAddress2())
														&& !(CommonFunction
																.checkNull(vo1.getAddress2())).trim()
																.equalsIgnoreCase("")) {

													qryLimit = " select b.ADDRESS_LINE2 from cr_customer_search_dtl a left join cr_deal_address_m b on b.bpid=a.customer_id  where a.customer_id='"+vo1.getCustomerId()+"' and DEAL_NO='"+vo1.getDealNo()+"' ";	
														
													ADD2=ConnectionDAO.singleReturn(qryLimit
															.toString());
													totalMatch = totalMatch + " ADDRESS2:    " + ADD2
															+ "\n";
													qryLimit=null;
												}

												if (vo.getAddress3()
														.equalsIgnoreCase(vo1.getAddress3())
														&& !(CommonFunction
																.checkNull(vo1.getAddress3())).trim()
																.equalsIgnoreCase("")) {

													qryLimit = " select b.ADDRESS_LINE3 from cr_customer_search_dtl a left join cr_deal_address_m b on b.bpid=a.customer_id  where a.customer_id='"+vo1.getCustomerId()+"' and DEAL_NO='"+vo1.getDealNo()+"' ";	
												
													ADD3=ConnectionDAO.singleReturn(qryLimit
															.toString());
													totalMatch = totalMatch + " ADDRESS3:    " + ADD3
															+ "\n";
													qryLimit=null;
												}
												
												if (vo.getDist().equalsIgnoreCase(vo1.getDist())
														&& !(CommonFunction.checkNull(vo1.getDist()))
																.trim().equalsIgnoreCase("")) {

													 qryLimit = " select DISTRICT_DESC from COM_DISTRICT_M where DISTRICT_ID='"
															+ vo.getDist() + "' ";
													DistrictDESC = ConnectionDAO.singleReturn(qryLimit
															.toString());
													totalMatch = totalMatch +" DISTRICT:    "
															+ DistrictDESC + "\n";
													qryLimit=null;
												}
												if (vo.getState().equalsIgnoreCase(vo1.getState())
														&& !(CommonFunction.checkNull(vo1.getState()))
																.trim().equalsIgnoreCase("")) {

													 qryLimit = " select STATE_DESC from COM_STATE_M where STATE_ID='"
															+ vo.getState() + "' ";
													StateDESC = ConnectionDAO.singleReturn(qryLimit
															.toString());
													totalMatch = totalMatch + " STATE:    " + StateDESC
															+ "\n";
													qryLimit=null;
												}

											
												if (vo.getPincode().equalsIgnoreCase(vo1.getPincode())
														&& !(CommonFunction.checkNull(vo1.getPincode()))
																.trim().equalsIgnoreCase("")) {

													totalMatch = totalMatch + " PINCODE:    " + Pincode
															+ "\n";
												}
												if (vo.getMobile().equalsIgnoreCase(vo1.getMobile())
														&& !(CommonFunction.checkNull(vo1.getMobile()))
																.trim().equalsIgnoreCase("")) {

													totalMatch = totalMatch + " PRIMARY_PHONE:    "
															+ Mobile + "\n";
												}

												if (vo.getPhoneOff()
														.equalsIgnoreCase(vo1.getPhoneOff())
														&& !(CommonFunction
																.checkNull(vo1.getPhoneOff())).trim()
																.equalsIgnoreCase("")) {

													totalMatch = totalMatch + " ALTERNATE_PHONE:    "
															+ AlternatePhone + "\n";
												}
												if (vo.getEmail().equalsIgnoreCase(vo1.getEmail())
														&& !(CommonFunction.checkNull(vo1.getEmail()))
																.trim().equalsIgnoreCase("")) {

													totalMatch = totalMatch + " EMAIL:    " + Email + "\n";
												}
												if (vo.getCustPan().equalsIgnoreCase(vo1.getCustPan())
														&& !(CommonFunction.checkNull(vo1.getCustPan()))
																.trim().equalsIgnoreCase("")) {

													totalMatch = totalMatch + " CUST_PAN:    " + CustPan
															+ "\n";
												}
												if (vo.getPassport()
														.equalsIgnoreCase(vo1.getPassport())
														&& !(CommonFunction
																.checkNull(vo1.getPassport())).trim()
																.equalsIgnoreCase("")) {

													totalMatch = totalMatch + " PASSPORT:    " + Passport
															+ "\n";
												}
												if (vo.getVoterId().equalsIgnoreCase(vo1.getVoterId())
														&& !(CommonFunction.checkNull(vo1.getVoterId()))
																.trim().equalsIgnoreCase("")) {

													totalMatch = totalMatch + " VOTER_ID:    " + VoterId
															+ "\n";
												}
												if (vo.getUidNo().equalsIgnoreCase(vo1.getUidNo())
														&& !(CommonFunction.checkNull(vo1.getUidNo()))
																.trim().equalsIgnoreCase(""))

												{

													totalMatch = totalMatch + " UIDNO:    " + UidNo + "\n";
												}
												if (vo.getDlNumber()
														.equalsIgnoreCase(vo1.getDlNumber())
														&& !(CommonFunction
																.checkNull(vo1.getDlNumber())).trim()
																.equalsIgnoreCase("")) {

													totalMatch = totalMatch + " DLNUMBER:    " + DlNumber
															+ "\n";
												}

												/*if (firstletter1.equalsIgnoreCase(String.valueOf(vo1
														.getFirstName().charAt(0)))
														&& firstletter2.equalsIgnoreCase(String
																.valueOf(vo1.getLastName().charAt(0)))) {

													totalMatch = totalMatch
															+ " FIRST LETTER OF FNAME & FIRST LETTER OF LNAME:    "
															+ firstletter1 + " , " + firstletter2
															+ "\n";
												}*/

							if ((CommonFunction.checkNull(vo1.getCustomerId()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getCustomerId()).trim());
							if ((CommonFunction.checkNull(vo1.getCustomerName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getCustomerName()).trim());
							if ((CommonFunction.checkNull(vo1.getMobile())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getMobile())
										.trim());
							if ((CommonFunction.checkNull(vo1.getEmail())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getEmail())
										.trim());
							if ((CommonFunction.checkNull(vo1.getCustomerType()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getCustomerType()).trim());
							if ((CommonFunction.checkNull(vo1.getConstitution()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getConstitution()).trim());
							if ((CommonFunction.checkNull(vo1.getCustPan())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getCustPan())
										.trim());
							if ((CommonFunction.checkNull(vo1.getCustDOB())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getCustDOB())
										.trim());
							if ((CommonFunction.checkNull(vo1.getDlNumber()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getDlNumber())
										.trim());
							if ((CommonFunction.checkNull(vo1.getVoterId())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getVoterId())
										.trim());
							if ((CommonFunction.checkNull(vo1.getPassport()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getPassport())
										.trim());
							if ((CommonFunction.checkNull(vo1.getFatherName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getFatherName()).trim());

							if ((CommonFunction.checkNull(vo1.getRuleWeightage()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getRuleWeightage()).trim());
							if ((CommonFunction.checkNull(txnType)).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((txnType).trim());
							if ((CommonFunction.checkNull(txnId)).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((txnId).trim());
							if ((CommonFunction.checkNull(vo1.getFirstName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1
										.addString((vo1.getFirstName()).trim());
							if ((CommonFunction.checkNull(vo1.getMiddleName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getMiddleName()).trim());
							if ((CommonFunction.checkNull(vo1.getLastName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getLastName())
										.trim());
							if ((CommonFunction.checkNull(vo1
									.getDateOfIncorporation())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getDateOfIncorporation()).trim());
							if ((CommonFunction.checkNull(vo1.getRegistrationNo()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getRegistrationNo()).trim());
							if ((CommonFunction.checkNull(vo1.getUidNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getUidNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getVatNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getVatNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getTinNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getTinNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getRefNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getRefNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getSource())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addString(record);
							else
								insertPrepStmtObject1.addString((vo1.getSource())
										.trim());
							if ((CommonFunction.checkNull(vo1.getDist())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getDist())
										.trim());
							if ((CommonFunction.checkNull(vo1.getAddress1()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getAddress1())
										.trim());
							if ((CommonFunction.checkNull(vo1.getAddress2()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getAddress2())
										.trim());
							if ((CommonFunction.checkNull(vo1.getAddress3()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getAddress3())
										.trim());
							if ((CommonFunction.checkNull(vo1.getAddString()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1
										.addString((vo1.getAddString()).trim());
							if ((CommonFunction.checkNull(vo.getCustomerId()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1
										.addString((vo.getCustomerId()).trim());
							if ((CommonFunction.checkNull(vo1.getPartner())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getPartner())
										.trim());

							if ((CommonFunction.checkNull(vo1.getDealNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getDealNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getDealStatus()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addString("F");
							else
								insertPrepStmtObject1.addString((vo1
										.getDealStatus()).trim());
							if ((CommonFunction.checkNull(vo1.getRemarks())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getRemarks())
										.trim());
							if ((CommonFunction.checkNull(vo1.getPincode())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getPincode())
										.trim());
							if ((CommonFunction.checkNull(vo1.getState())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getState())
										.trim());
							if ((CommonFunction.checkNull(vo1.getPhoneOff()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getPhoneOff())
										.trim());
							if ((CommonFunction.checkNull(totalMatch)).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1
										.addString((totalMatch).trim());

						}  else if(CustomerType.equalsIgnoreCase("C"))
	 {
							if (vo.getCustomerName().equalsIgnoreCase(
									vo1.getCustomerName())
									&& !(CommonFunction.checkNull(vo1
											.getCustomerName())).trim()
											.equalsIgnoreCase("")) {
								totalMatch = " CUSTOMER_NAME:    " + CustomerName
										+ "\n";

							}
							if (vo.getDateOfIncorporation().equalsIgnoreCase(
									vo1.getDateOfIncorporation())
									&& !(CommonFunction.checkNull(vo1
											.getDateOfIncorporation())).trim()
											.equalsIgnoreCase("")) {
							
								 qryLimit = " select  DATE_FORMAT(DATE_OF_INCORPORATION,'%d-%m-%Y') from cr_customer_search_dtl  where customer_id='"+vo1.getCustomerId()+"' and DEAL_NO='"+vo1.getDealNo()+"' ";
								
								DOI = ConnectionDAO.singleReturn(qryLimit
										.toString());
								totalMatch = totalMatch + " DOINC.:    "
										+ DOI + "\n";
								qryLimit=null;
							}
							if (vo.getPartner().equalsIgnoreCase(vo1.getPartner())
									&& !(CommonFunction.checkNull(vo1.getPartner()))
											.trim().equalsIgnoreCase("")) {

								totalMatch = totalMatch + " PARTNER:    " + Partner
										+ "\n";
							}
							if (vo.getAddress1()
									.equalsIgnoreCase(vo1.getAddress1())
									&& !(CommonFunction
											.checkNull(vo1.getAddress1())).trim()
											.equalsIgnoreCase("")) {
							
								qryLimit = " select b.ADDRESS_LINE1 from cr_customer_search_dtl a left join cr_deal_address_m b on b.bpid=a.customer_id  where a.customer_id='"+vo1.getCustomerId()+"' and DEAL_NO='"+vo1.getDealNo()+"' ";	
							
								ADD1=ConnectionDAO.singleReturn(qryLimit
										.toString());
								totalMatch = totalMatch + " ADDRESS1:    " + ADD1
										+ "\n";
								qryLimit=null;
							}

							if (vo.getAddress2()
									.equalsIgnoreCase(vo1.getAddress2())
									&& !(CommonFunction
											.checkNull(vo1.getAddress2())).trim()
											.equalsIgnoreCase("")) {
								
								qryLimit = " select b.ADDRESS_LINE2 from cr_customer_search_dtl a left join cr_deal_address_m b on b.bpid=a.customer_id  where a.customer_id='"+vo1.getCustomerId()+"' and DEAL_NO='"+vo1.getDealNo()+"' ";	
									
								ADD2=ConnectionDAO.singleReturn(qryLimit
										.toString());
								totalMatch = totalMatch + " ADDRESS2:    " + ADD2
										+ "\n";
								qryLimit=null;
							}

							if (vo.getAddress3()
									.equalsIgnoreCase(vo1.getAddress3())
									&& !(CommonFunction
											.checkNull(vo1.getAddress3())).trim()
											.equalsIgnoreCase("")) {

								qryLimit = " select b.ADDRESS_LINE3 from cr_customer_search_dtl a left join cr_deal_address_m b on b.bpid=a.customer_id  where a.customer_id='"+vo1.getCustomerId()+"' and DEAL_NO='"+vo1.getDealNo()+"' ";	
						
								ADD3=ConnectionDAO.singleReturn(qryLimit
										.toString());
								totalMatch = totalMatch + " ADDRESS3:    " + ADD3
										+ "\n";
								qryLimit=null;
							}
							
							if (vo.getDist().equalsIgnoreCase(vo1.getDist())
									&& !(CommonFunction.checkNull(vo1.getDist()))
											.trim().equalsIgnoreCase("")) {

								 qryLimit = " select DISTRICT_DESC from COM_DISTRICT_M where DISTRICT_ID='"
										+ vo.getDist() + "' ";
								DistrictDESC = ConnectionDAO.singleReturn(qryLimit
										.toString());
								totalMatch = totalMatch + " DISTRICT:    "
										+ DistrictDESC + "\n";
								qryLimit=null;
							}
							if (vo.getState().equalsIgnoreCase(vo1.getState())
									&& !(CommonFunction.checkNull(vo1.getState()))
											.trim().equalsIgnoreCase("")) {

								 qryLimit = " select STATE_DESC from COM_STATE_M where STATE_ID='"
										+ vo.getState() + "' ";
								StateDESC = ConnectionDAO.singleReturn(qryLimit
										.toString());
								totalMatch = totalMatch + " STATE:    " + StateDESC
										+ "\n";
								qryLimit=null;
							}

							if (vo.getPincode().equalsIgnoreCase(vo1.getPincode())
									&& !(CommonFunction.checkNull(vo1.getPincode()))
											.trim().equalsIgnoreCase("")) {

								totalMatch = totalMatch + " PINCODE:    " + Pincode
										+ "\n";
							}
							if (vo.getMobile().equalsIgnoreCase(vo1.getMobile())
									&& !(CommonFunction.checkNull(vo1.getMobile()))
											.trim().equalsIgnoreCase("")) {

								totalMatch = totalMatch + " PRIMARY_PHONE:    "
										+ Mobile + "\n";
							}

							if (vo.getPhoneOff()
									.equalsIgnoreCase(vo1.getPhoneOff())
									&& !(CommonFunction
											.checkNull(vo1.getPhoneOff())).trim()
											.equalsIgnoreCase("")) {

								totalMatch = totalMatch + " ALTERNATE_PHONE:    "
										+ AlternatePhone + "\n";
							}
							if (vo.getEmail().equalsIgnoreCase(vo1.getEmail())
									&& !(CommonFunction.checkNull(vo1.getEmail()))
											.trim().equalsIgnoreCase("")) {

								totalMatch = totalMatch + " EMAIL:    " + Email
										+ "\n";
							}
							if (vo.getCustPan().equalsIgnoreCase(vo1.getCustPan())
									&& !(CommonFunction.checkNull(vo1.getCustPan()))
											.trim().equalsIgnoreCase("")) {

								totalMatch = totalMatch + " CUST_PAN:    " + CustPan
										+ "\n";
							}

							if (vo.getVatNo().equalsIgnoreCase(vo1.getVatNo())
									&& !(CommonFunction.checkNull(vo1.getVatNo()))
											.trim().equalsIgnoreCase("")) {

								totalMatch = totalMatch + " VAT:    " + VatNo + "\n";
							}
							if (vo.getRegistrationNo().equalsIgnoreCase(
									vo1.getRegistrationNo())
									&& !(CommonFunction.checkNull(vo1
											.getRegistrationNo())).trim()
											.equalsIgnoreCase("")) {

								totalMatch = totalMatch + " REGISTRATION:    "
										+ RegistrationNo + "\n";
							}
							if (vo.getTinNo().equalsIgnoreCase(vo1.getTinNo())
									&& !(CommonFunction.checkNull(vo1.getTinNo()))
											.trim().equalsIgnoreCase("")) {

								totalMatch = totalMatch + " TIN_NUM:     " + TinNo
										+ "\n";
							}

							if ((CommonFunction.checkNull(vo1.getCustomerId()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getCustomerId()).trim());
							if ((CommonFunction.checkNull(vo1.getCustomerName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getCustomerName()).trim());
							if ((CommonFunction.checkNull(vo1.getMobile())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getMobile())
										.trim());
							if ((CommonFunction.checkNull(vo1.getEmail())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getEmail())
										.trim());
							if ((CommonFunction.checkNull(vo1.getCustomerType()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getCustomerType()).trim());
							if ((CommonFunction.checkNull(vo1.getConstitution()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getConstitution()).trim());
							if ((CommonFunction.checkNull(vo1.getCustPan())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getCustPan())
										.trim());
							if ((CommonFunction.checkNull(vo1.getCustDOB())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getCustDOB())
										.trim());
							if ((CommonFunction.checkNull(vo1.getDlNumber()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getDlNumber())
										.trim());
							if ((CommonFunction.checkNull(vo1.getVoterId())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getVoterId())
										.trim());
							if ((CommonFunction.checkNull(vo1.getPassport()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getPassport())
										.trim());
							if ((CommonFunction.checkNull(vo1.getFatherName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getFatherName()).trim());
							if ((CommonFunction.checkNull(vo1.getRuleWeightage()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getRuleWeightage()).trim());
							if ((CommonFunction.checkNull(txnType)).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((txnType).trim());
							if ((CommonFunction.checkNull(txnId)).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((txnId).trim());
							if ((CommonFunction.checkNull(vo1.getFirstName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1
										.addString((vo1.getFirstName()).trim());
							if ((CommonFunction.checkNull(vo1.getMiddleName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getMiddleName()).trim());
							if ((CommonFunction.checkNull(vo1.getLastName()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getLastName())
										.trim());
							if ((CommonFunction.checkNull(vo1
									.getDateOfIncorporation())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getDateOfIncorporation()).trim());
							if ((CommonFunction.checkNull(vo1.getRegistrationNo()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1
										.getRegistrationNo()).trim());
							if ((CommonFunction.checkNull(vo1.getUidNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getUidNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getVatNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getVatNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getTinNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getTinNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getRefNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getRefNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getSource())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addString(record);
							else
								insertPrepStmtObject1.addString((vo1.getSource())
										.trim());
							if ((CommonFunction.checkNull(vo1.getDist())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getDist())
										.trim());
							if ((CommonFunction.checkNull(vo1.getAddress1()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getAddress1())
										.trim());
							if ((CommonFunction.checkNull(vo1.getAddress2()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getAddress2())
										.trim());
							if ((CommonFunction.checkNull(vo1.getAddress3()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getAddress3())
										.trim());
							if ((CommonFunction.checkNull(vo1.getAddString()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1
										.addString((vo1.getAddString()).trim());
							if ((CommonFunction.checkNull(vo.getCustomerId()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1
										.addString((vo.getCustomerId()).trim());
							if ((CommonFunction.checkNull(vo1.getPartner())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getPartner())
										.trim());
							if ((CommonFunction.checkNull(vo1.getDealNo())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getDealNo())
										.trim());
							if ((CommonFunction.checkNull(vo1.getDealStatus()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addString("F");
							else
								insertPrepStmtObject1.addString((vo1
										.getDealStatus()).trim());
							if ((CommonFunction.checkNull(vo1.getRemarks())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getRemarks())
										.trim());
							if ((CommonFunction.checkNull(vo1.getPincode())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getPincode())
										.trim());
							if ((CommonFunction.checkNull(vo1.getState())).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getState())
										.trim());
							if ((CommonFunction.checkNull(vo1.getPhoneOff()))
									.trim().equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1.addString((vo1.getPhoneOff())
										.trim());
							if ((CommonFunction.checkNull(totalMatch)).trim()
									.equalsIgnoreCase(""))
								insertPrepStmtObject1.addNull();
							else
								insertPrepStmtObject1
										.addString((totalMatch).trim());

						}

						logger.info("In Insertion query for Dedupe......... query..........."
								+ bufInsSql.toString());
						insertPrepStmtObject1.setSql(bufInsSql.toString());

						logger.info("IN saveDedupeCustomerDetail() Insertion query for Dedupe query1 ### "
								+ insertPrepStmtObject1.printQuery());

						list.add(insertPrepStmtObject1);
						status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(list);
						logger.info(" Inserted Data size is...."
								+ list.size());
						insertPrepStmtObject1 = null;
						bufInsSql = null;
					}
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			} finally {

				query = null;
				query1 = null;
				vo1 = null;

			}

		}
	    public LinkedHashMap<String,CreditProcessingLeadDetailDataVo> removeDuplicateValue(ArrayList searchlist)
	    {
	    	
	    	LinkedHashMap<String, CreditProcessingLeadDetailDataVo> map = new LinkedHashMap<String, CreditProcessingLeadDetailDataVo>();
	    	CreditProcessingLeadDetailDataVo vo1 = null;

	    	for(int i=0;i<searchlist.size();i++)
	    	   {
	    	vo1 = new CreditProcessingLeadDetailDataVo();
	    	   
	    				int weightage=0;
	    		  		int totalWeightage=0;
	    		  		
	    		  		String totalMatch="";
	    		ArrayList data1=(ArrayList)searchlist.get(i);
	    			int counter = 0;
	    		if(data1.size()>0 ) 
	    	 {
	    			
	    	//vo1.setTxnId((CommonFunction.checkNull(data1.get(0)).trim()));
	    	vo1.setRule((CommonFunction.checkNull(data1.get(0)).trim()));
	    	vo1.setRuleWeightage((CommonFunction.checkNull(data1.get(1)).trim()));
	    	 vo1.setCustomerId((CommonFunction.checkNull(data1.get(2)).trim()));
	    	 vo1.setCustomerName((CommonFunction.checkNull(data1.get(3)).trim()));
	    	 vo1.setMobile((CommonFunction.checkNull(data1.get(4)).trim()));
	    	 vo1.setEmail((CommonFunction.checkNull(data1.get(5)).trim()));
	    	 vo1.setCustomerType((CommonFunction.checkNull(data1.get(6)).trim()));
	    	 vo1.setConstitution((CommonFunction.checkNull(data1.get(7)).trim()));
	    	 vo1.setCustPan((CommonFunction.checkNull(data1.get(8)).trim()));
	    	 vo1.setCustDOB((CommonFunction.checkNull(data1.get(9)).trim()));
	    	 vo1.setDlNumber((CommonFunction.checkNull(data1.get(10)).trim()));
	    	 vo1.setVoterId((CommonFunction.checkNull(data1.get(11)).trim()));
	    	 vo1.setPassport((CommonFunction.checkNull(data1.get(12)).trim()));
	    	 vo1.setFatherName((CommonFunction.checkNull(data1.get(13)).trim()));
	    	 vo1.setFirstName((CommonFunction.checkNull(data1.get(14)).trim()));
	    	 vo1.setMiddleName((CommonFunction.checkNull(data1.get(15)).trim()));
	    	 vo1.setLastName((CommonFunction.checkNull(data1.get(16)).trim()));
	    	 vo1.setDateOfIncorporation((CommonFunction.checkNull(data1.get(17)).trim()));
	    	 vo1.setRegistrationNo((CommonFunction.checkNull(data1.get(18)).trim()));
	    	 vo1.setUidNo((CommonFunction.checkNull(data1.get(19)).trim()));
	    	 vo1.setVatNo((CommonFunction.checkNull(data1.get(20)).trim()));
	    	 vo1.setTinNo((CommonFunction.checkNull(data1.get(21)).trim()));
	    	 vo1.setRefNo((CommonFunction.checkNull(data1.get(22)).trim()));
	    	 vo1.setSource((CommonFunction.checkNull(data1.get(23)).trim()));
	    	 vo1.setDist((CommonFunction.checkNull(data1.get(24)).trim()));
	    	 vo1.setAddress1((CommonFunction.checkNull(data1.get(25)).trim()));
	    	 vo1.setAddress2((CommonFunction.checkNull(data1.get(26)).trim()));
	    	 vo1.setAddress3((CommonFunction.checkNull(data1.get(27)).trim()));
	    	 vo1.setAddString((CommonFunction.checkNull(data1.get(28)).trim()));
	    	 vo1.setPartner((CommonFunction.checkNull(data1.get(29)).trim()));
	    	 vo1.setDealNo((CommonFunction.checkNull(data1.get(30)).trim()));
	    	 vo1.setDealStatus((CommonFunction.checkNull(data1.get(31)).trim()));
	    	 vo1.setRemarks((CommonFunction.checkNull(data1.get(32)).trim()));
	    	 vo1.setPincode((CommonFunction.checkNull(data1.get(33)).trim()));
	    	 vo1.setState((CommonFunction.checkNull(data1.get(34)).trim()));
	    	 vo1.setPhoneOff((CommonFunction.checkNull(data1.get(35)).trim()));
	    	 
	    	 }
	    		if(map.containsKey(vo1.getCustomerId()+"_"+vo1.getDealNo()))
	    		{
	    			Object value = map.get(vo1.getCustomerId()+"_"+vo1.getDealNo());
	    			String val=value.toString();
	    			logger.info("value are.........."+val);
	    		}
	    		else
	    		{
	    		map.put(vo1.getCustomerId()+"_"+vo1.getDealNo(), vo1);
	    		}
	    	
	    }
	    		return map;
	    }
	    private LinkedHashMap<String, CreditProcessingLeadDetailDataVo> reArrangeValue(LinkedHashMap<String, CreditProcessingLeadDetailDataVo> map,CreditProcessingLeadDetailDataVo vo) {
	    	
	    	LinkedHashMap<String, CreditProcessingLeadDetailDataVo> linkmap = new LinkedHashMap<String, CreditProcessingLeadDetailDataVo>();
	    	//ArrayList searchlist =new ArrayList();
	    	for(Map.Entry<String, CreditProcessingLeadDetailDataVo> entry  : map.entrySet())
	    	{

	    	int weightage=0;
	    	int dynamicWeightage=0;
	    		CreditProcessingLeadDetailDataVo	vo1 = new CreditProcessingLeadDetailDataVo();
	    		vo1 = 	entry.getValue();
	    		
	    if(!CommonFunction.checkNull(vo1.getRuleWeightage()).equalsIgnoreCase(""))
	    {
	    		 weightage=Integer.parseInt(vo1.getRuleWeightage());
	    }		
	    		if(vo.getCustomerType().equalsIgnoreCase("I"))
	    		
	    		{ 
	    			
	    		
	    		if(CommonFunction.checkNull(vo.getVoterId()).equalsIgnoreCase(vo1.getVoterId().trim())
	    				&& CommonFunction.checkNull(vo.getDlNumber()).equalsIgnoreCase(vo1.getDlNumber().trim())
	    						&& CommonFunction.checkNull(vo.getPassport()).equalsIgnoreCase(vo1.getPassport().trim())
	    								&& CommonFunction.checkNull(vo.getCustPan()).equalsIgnoreCase(vo1.getCustPan().trim())
	    										&& CommonFunction.checkNull(vo.getUidNo()).equalsIgnoreCase(vo1.getUidNo().trim()) ){
	    			weightage=weightage+15;
	    								}
	    		
	    		if(CommonFunction.checkNull(vo.getVoterId()).equalsIgnoreCase(vo1.getVoterId().trim())
	    				|| CommonFunction.checkNull(vo.getDlNumber()).equalsIgnoreCase(vo1.getDlNumber().trim())
	    						|| CommonFunction.checkNull(vo.getPassport()).equalsIgnoreCase(vo1.getPassport().trim())
	    								|| CommonFunction.checkNull(vo.getCustPan()).equalsIgnoreCase(vo1.getCustPan().trim())
	    										|| CommonFunction.checkNull(vo.getUidNo()).equalsIgnoreCase(vo1.getUidNo().trim()) ){
	    			weightage=weightage+10;
	    								}
	    		
	    		
	    		if ((String.valueOf(vo.getFirstName().charAt(0)).equalsIgnoreCase(String.valueOf(vo1.getFirstName().charAt(0)))
	    				&& String.valueOf(vo.getLastName().charAt(0)).equalsIgnoreCase(String
	    						.valueOf(vo1.getLastName().charAt(0)))) 
	    						&& (CommonFunction.checkNull(vo.getDist()).equalsIgnoreCase(vo1.getDist().trim())
	    								&& CommonFunction.checkNull(vo.getAddString()).equalsIgnoreCase(vo1.getAddString().trim())
	    								&& CommonFunction.checkNull(vo.getPincode()).equalsIgnoreCase(vo1.getPincode().trim())
	    								) && (CommonFunction.checkNull(vo.getMobile()).equalsIgnoreCase(vo1.getMobile().trim()))
	    						) {
	    			weightage=weightage+7;
	    		}
	    		
	    		if(CommonFunction.checkNull(vo.getFirstName()).equalsIgnoreCase(vo1.getFirstName().trim()) 
	    				&& CommonFunction.checkNull(vo.getLastName()).equalsIgnoreCase(vo1.getLastName().trim()) 
	    				&& CommonFunction.checkNull(vo.getFatherName()).equalsIgnoreCase(vo1.getFatherName().trim()) 
	    				&&  CommonFunction.checkNull(vo.getCustDOB()).equalsIgnoreCase(vo1.getCustDOB().trim()) 				
	    				)
	    		{
	    			weightage=weightage+5;
	    		}
	    		
	    		if(CommonFunction.checkNull(vo.getFirstName()).equalsIgnoreCase(vo1.getFirstName().trim()) 
	    				&& CommonFunction.checkNull(vo.getLastName()).equalsIgnoreCase(vo1.getLastName().trim()) 
	    				&& CommonFunction.checkNull(vo.getFatherName()).equalsIgnoreCase(vo1.getFatherName().trim()) 
	    								
	    				)
	    		{
	    			weightage=weightage+4;
	    		}
	    		
	    		if(CommonFunction.checkNull(vo.getCustDOB()).equalsIgnoreCase(vo1.getCustDOB().trim()) 
	    				&& CommonFunction.checkNull(vo.getEmail()).equalsIgnoreCase(vo1.getEmail().trim()) 
	    				&& CommonFunction.checkNull(vo.getMobile()).equalsIgnoreCase(vo1.getMobile().trim()) 
	    								
	    				)
	    		{
	    			weightage=weightage+3;
	    		}
	    		
	    		}
	    		else
	    		{
	    			if(CommonFunction.checkNull(vo.getRegistrationNo()).equalsIgnoreCase(vo1.getRegistrationNo().trim())
	    					&& CommonFunction.checkNull(vo.getTinNo()).equalsIgnoreCase(vo1.getTinNo().trim())
	    							&& CommonFunction.checkNull(vo.getVatNo()).equalsIgnoreCase(vo1.getVatNo().trim())
	    									&& CommonFunction.checkNull(vo.getCustPan()).equalsIgnoreCase(vo1.getCustPan().trim())
	    											 ){
	    				weightage=weightage+15;
	    									}
	    			
	    			if(CommonFunction.checkNull(vo.getRegistrationNo()).equalsIgnoreCase(vo1.getRegistrationNo().trim())
	    					|| CommonFunction.checkNull(vo.getTinNo()).equalsIgnoreCase(vo1.getTinNo().trim())
	    							|| CommonFunction.checkNull(vo.getVatNo()).equalsIgnoreCase(vo1.getVatNo().trim())
	    									|| CommonFunction.checkNull(vo.getCustPan()).equalsIgnoreCase(vo1.getCustPan().trim())
	    											 ){
	    				weightage=weightage+10;
	    									}
	    			
	    			if(CommonFunction.checkNull(vo.getCustomerName()).equalsIgnoreCase(vo1.getCustomerName().trim()) 
	    					&& CommonFunction.checkNull(vo.getDateOfIncorporation()).equalsIgnoreCase(vo1.getDateOfIncorporation().trim()) 
	    								
	    					)
	    			{
	    				weightage=weightage+7;
	    			}
	    			
	    			if ( CommonFunction.checkNull(vo.getCustomerName()).equalsIgnoreCase(vo1.getCustomerName().trim())
	    							&& (CommonFunction.checkNull(vo.getDist()).equalsIgnoreCase(vo1.getDist().trim())
	    									&& CommonFunction.checkNull(vo.getAddString()).equalsIgnoreCase(vo1.getAddString().trim())
	    									&& CommonFunction.checkNull(vo.getPincode()).equalsIgnoreCase(vo1.getPincode().trim())
	    									) && (CommonFunction.checkNull(vo.getMobile()).equalsIgnoreCase(vo1.getMobile().trim()))
	    							) {
	    				weightage=weightage+5;
	    			}
	    			
	    	
	    			if(CommonFunction.checkNull(vo.getDateOfIncorporation()).equalsIgnoreCase(vo1.getDateOfIncorporation().trim()) 
	    					&& CommonFunction.checkNull(vo.getEmail()).equalsIgnoreCase(vo1.getEmail().trim()) 
	    					&& CommonFunction.checkNull(vo.getMobile()).equalsIgnoreCase(vo1.getMobile().trim()) 
	    									
	    					)
	    			{
	    				weightage=weightage+4;
	    			}
	    		}
	    				
	    		vo1.setDynamicWeightage(weightage);
	    		
	    		linkmap.put(vo1.getCustomerId()+"_"+vo1.getDealNo(), vo1);
	    		}

	    	return linkmap;
	    }
	 
	    public ArrayList<Object> searchCustomerList(dedupeReferalVo vo) {
	    	ArrayList list=new ArrayList();
	    	String custName=vo.getCustomerName();
	    	String dealId=vo.getLbxDealNo();
	    	String product=vo.getLbxProductID();
	    	String scheme=vo.getLbxscheme();
	    	String userId=vo.getUserId();
	    	
	    	logger.info(" the value  of dedupe decision is. userId ."+userId+"dealId  "+dealId+"  product  "+product+"  scheme  "+scheme+"  custName  "+custName);
	    	
	    	
	    	String check="";
	    	int count=0;
	    	int startRecordIndex=0;
	    	int endRecordIndex = no;
	    	String lbxDealId=vo.getLbxDealNo();
	    	ArrayList header=new ArrayList();
	    	try
	    	{
	    		logger.info("Inside Searched Customers.....");
	    		
	    		dedupeReferalVo fetchVo= (dedupeReferalVo) vo;
	    		boolean appendSQL=false;
	    		StringBuffer bufInsSql=new StringBuffer();
	    		StringBuffer bufInsSqlTempCount = new StringBuffer();
	    		
	    		bufInsSql.append(" select distinct d.DEAL_ID,d.DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,D.DEAL_RM,DATE_FORMAT(D.DEAL_DATE,'%d-%m-%Y') ,d.MAKER_ID from cr_deal_dtl d ");
	    		bufInsSqlTempCount.append("SELECT COUNT(distinct d.DEAL_ID,d.DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,D.DEAL_RM,DATE_FORMAT(D.DEAL_DATE,'%d-%m-%Y')) from cr_deal_dtl d ");
	    		
	    		bufInsSql.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID    ");
	    		bufInsSqlTempCount.append(" INNER join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID    ");
	    		
	    		bufInsSql.append("  INNER join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID   ");			
	    		bufInsSqlTempCount.append("  INNER join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID  INNER join cr_deal_movement_dtl dm on dm.DEAL_ID=d.DEAL_ID  ");
	    		
	    		bufInsSql.append("  AND DM.DEAL_RECEIVED<>'0000-00-00 00:00:00' AND DM.DEAL_FORWARDED='0000-00-00 00:00:00' AND DM.DEAL_STAGE_ID='DD'  ");			
	    		bufInsSqlTempCount.append("  AND DM.DEAL_RECEIVED<>'0000-00-00 00:00:00' AND DM.DEAL_FORWARDED='0000-00-00 00:00:00' AND DM.DEAL_STAGE_ID='DD'  ");
	    		
	    		bufInsSql.append(" AND DM.REC_STATUS='A'  INNER join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID Where d.REC_STATUS IN ('P','F') and d.Dedupe_status='Y' ");			
	    		bufInsSqlTempCount.append(" AND DM.REC_STATUS='A'  INNER join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID Where d.REC_STATUS IN ('P','F')  and d.Dedupe_status='Y' ");
	    		
	    		if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBranchId())).trim().equalsIgnoreCase("")))) 
				{
					bufInsSql.append("AND d.deal_branch='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' ");
			        bufInsSqlTempCount.append("AND d.deal_branch='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' ");
					
				}
				
	    		
	    		
	    		
	    		
	    		/*if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
	    		{
	    			bufInsSql.append("WHERE D.REC_STATUS NOT IN ('A','X','F')  and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
	    			bufInsSqlTempCount.append("WHERE D.REC_STATUS NOT IN ('A','X','F')  and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' ");
	    			

	    		}*/
	    		
	    		if(((vo.getLbxDealNo().equalsIgnoreCase("")) ||(vo.getCustomerName().equalsIgnoreCase(""))||(vo.getLbxProductID().equalsIgnoreCase(""))||(vo.getLbxscheme().equalsIgnoreCase("")))){
	    			appendSQL=true;
	    		}
	    		
	    		/*if(appendSQL){
	    			logger.info("In Where Clause");
	    			//bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' ");
	    			//bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' ");
	    			
	    			bufInsSql.append(" WHERE D.REC_STATUS  IN ('A','X','F')  ");
	    			bufInsSqlTempCount.append(" WHERE D.REC_STATUS IN ('A','X','F')  ");
	    		
	    				
	    		}*/

	    		 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
	    		        bufInsSql.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
	    		        bufInsSqlTempCount.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
	    		   	 appendSQL=true;
	    		   	  
	    		     }
	    			
	    			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim().equalsIgnoreCase("")))) {
	    		   	  bufInsSql.append("AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' ");
	    		   	  bufInsSqlTempCount.append("AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()+"%' ");
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
	    		LoggerMsg.info("Customers shown for In Dedupe Referal : "+bufInsSql);
	    		logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	    		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
	    		
	    		if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()==null && StringEscapeUtils.escapeSql(vo.getCustomerName()).trim()==null) || (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && StringEscapeUtils.escapeSql(vo.getCustomerName()).trim().equalsIgnoreCase("")) || (StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()==null  && StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim().equalsIgnoreCase("")) || ( StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()==null && StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
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
	    		logger.info("Customers shown for In Dedupe Referal : "+bufInsSql);
	    		
	    		
	    	    header = ConnectionDAO.sqlSelect(bufInsSql.toString());
	    	    
	    	    
	    					
	    		for(int i=0;i<header.size();i++){
	    			
	    			logger.info("header: "+header.size());
	    			ArrayList header1=(ArrayList)header.get(i);
	    			if(header1!=null && header1.size()>0)
	    			{
	    				logger.info("header list size ....................."+header.size());
	    				fetchVo = new dedupeReferalVo();
	    			
	    				fetchVo.setLbxDealNo((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
	    				
	    				//dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA
	    				
	    			//fetchVo.setLeadNo("<a href=dedupeReferalSearch.do?method=openModifyForAuthor&dleadId="+(CommonFunction.checkNull(header1.get(0))).trim()+">"+(CommonFunction.checkNull(header1.get(0)))+"</a>");
	    				fetchVo.setDealNo("<a href=dedupeReferalSearch.do?method=openModifyForAuthor&dealId="+(CommonFunction.checkNull(header1.get(0))).trim()+">"+(CommonFunction.checkNull(header1.get(1)))+"</a>");
	    			//	fetchVo.setLoanNo("<a href=dedupeReferalSearch.do?method=openModifyForAuthor&dealId="+(CommonFunction.checkNull(header1.get(2))).trim()+">"+(CommonFunction.checkNull(header1.get(3)))+"</a>");
	    				fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(2))).trim());
	    				fetchVo.setProduct((CommonFunction.checkNull(header1.get(3))).trim());
	    				fetchVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
	    				fetchVo.setUserName((CommonFunction.checkNull(header1.get(7))).trim());
	    				fetchVo.setTotalRecordSize(count);
	    				list.add(fetchVo);
	    			}
	    		}
	    	}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }

	    return list;
	    }
	    public ArrayList<Object> dedupeValues(dedupeReferalVo vo) {
	    	ArrayList list=new ArrayList();
	    	ArrayList list1=new ArrayList();
	    	String dealId=vo.getLbxDealNo();
	    	String leadId=vo.getLbxLeadNo();
	    	int count=0;
	    	int startRecordIndex=0;
	    	int endRecordIndex = no;
	    	ArrayList header=new ArrayList();
	    	ArrayList header2=new ArrayList();
	    	try
	    	{
	    		logger.info("Customer Details Grid Data of dedupe Referal screen .....");
	    		
	    		dedupeReferalVo fetchVo= (dedupeReferalVo) vo;
	    		boolean appendSQL=false;
	    		StringBuffer bufInsSql=new StringBuffer();
	    		StringBuffer bufInsSqlTempCount = new StringBuffer();
	    		
	    		if ((!CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase("") && !CommonFunction.checkNull(vo.getTxnType()).trim().equalsIgnoreCase("")) || !CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase(""))
	    		{
	    			bufInsSql.append(" select DISTINCT  a.deal_id,b.deal_no,dl.DEAL_LOAN_AMOUNT,  DATE_FORMAT(c.customer_dob,'%d-%m-%Y')as customer_dob , ");
	    			bufInsSqlTempCount.append("SELECT COUNT( a.deal_id )");
	    			
	    			bufInsSql.append("  c.customer_email,c.customer_name,CASE a.DEAL_CUSTOMER_ROLE_TYPE WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR' else a.DEAL_CUSTOMER_ROLE_TYPE END AS CUST_TYPE,a.DEAL_CUSTOMER_ID,case a.DEAL_CUSTOMER_TYPE when 'C' then 'CORPORATE' when 'I' then 'INDIVIDUAL' else a.DEAL_CUSTOMER_TYPE end as DEAL_CUSTOMER_TYPE ,c.VOTER_ID,c.DRIVING_LICENSE,c.PASSPORT_NUMBER,c.CUSTMER_PAN,c.UID_NO,c.SALES_TAX_TIN_NO, (select DISTRICT_DESC from COM_DISTRICT_M where DISTRICT_ID=cr.DISTRICT) as district,d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,( SELECT  GROUP_CONCAT(DISTINCT STAKEHOLDER_NAME ORDER BY STAKEHOLDER_NAME)as PARTNER FROM CR_DEAL_CUSTOMER_STAKEHOLDER_M where customer_id = m.customer_id  GROUP BY customer_id)as PARTNER,c.CUSTOMER_REGISTRATION_NO,c.FATHER_HUSBAND_NAME, DATE_FORMAT(cr.DATE_OF_INCORPORATION,'%d-%m-%Y')as DATE_OF_INCORPORATION , case b.rec_status when 'F' then 'FORWARDED' when 'X' then 'REJECTED' when 'A' then 'APPROVED'  end as rec_status, ");
	    			
	    			
	    			bufInsSql.append(" (select STATE_DESC from COM_STATE_M where STATE_ID=cr.state) as state from cr_deal_customer_role a ");			
	    			bufInsSqlTempCount.append("  from cr_deal_customer_role a ");
	    			
	    			bufInsSql.append("  join cr_deal_dtl b on(a.deal_id=b.deal_id)  ");			
	    			bufInsSqlTempCount.append("  join cr_deal_dtl b on(a.deal_id=b.deal_id)");
	    			
	    			bufInsSql.append("  join cr_deal_loan_dtl dl on(dl.deal_id=b.deal_id) ");			
	    			bufInsSqlTempCount.append("  join cr_deal_loan_dtl dl on(dl.deal_id=b.deal_id) ");
	    			
	    			bufInsSql.append("  join cr_deal_customer_m c on(c.customer_id=a.deal_customer_id) ");			
	    			bufInsSqlTempCount.append("  join cr_deal_customer_m c on(c.customer_id=a.deal_customer_id) ");
	    			
	    			bufInsSql.append("  join cr_deal_address_m d on(d.bpid=a.deal_customer_id and COMMUNICATION_ADDRESS='Y') ");			
	    			bufInsSqlTempCount.append("  join cr_deal_address_m d on(d.bpid=a.deal_customer_id and COMMUNICATION_ADDRESS='Y' ) ");
	    			
	    			bufInsSql.append("  left join CR_DEAL_CUSTOMER_STAKEHOLDER_M m on( m.customer_id=a.deal_customer_id) ");			
	    			bufInsSqlTempCount.append("  left join CR_DEAL_CUSTOMER_STAKEHOLDER_M m on( m.customer_id=a.deal_customer_id) ");
	    			
	    			
	    			bufInsSql.append(" left join cr_customer_search_dtl cr on(cr.customer_id=a.deal_customer_id) ");			
	    			bufInsSqlTempCount.append("  left join cr_customer_search_dtl cr on(cr.customer_id=a.deal_customer_id) ");

	    			if(!CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
	    			{
	    				bufInsSql.append("  where a.deal_id=?  ");			
	    				bufInsSqlTempCount.append("  where a.deal_id='"+StringEscapeUtils.escapeSql(dealId).trim()+"' ");
	    				
	    			
	    			}
	    			else
	    			{
	    				bufInsSql.append("  where a.deal_id=?  ");			
	    				bufInsSqlTempCount.append("  where a.deal_id='"+StringEscapeUtils.escapeSql(vo.getDealId()).trim()+"' ");
	    			}
	    		
		    	
		     		
	    		}
	    		else
	    		{
	    			bufInsSql.append(" SELECT DISTINCT LEAD_ID,LEAD_ID,AMOUNT_REQUIRED,DATE_FORMAT(cm.customer_dob,'%d-%m-%Y')as customer_dob,cm.customer_email,CM.CUSTOMER_NAME,'APPLICANT' as CUST_TYPE,l.CUSTOMER_ID,case cm.CUSTOMER_TYPE when 'C' then 'CORPORATE' when 'I' then 'INDIVIDUAL' else cm.CUSTOMER_TYPE end as CUSTOMER_TYPE ,cr.VOTER_ID,cr.DRIVING_LICENSE,cr.PASSPORT_NUMBER,cr.CUSTMER_PAN,cr.UID,cr.TIN_NUMBER,(select DISTRICT_DESC from COM_DISTRICT_M where DISTRICT_ID=cr.DISTRICT) as district,d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,cm.CUSTOMER_FNAME,cm.CUSTOMER_MNAME,cm.CUSTOMER_LNAME,( SELECT  GROUP_CONCAT(DISTINCT STAKEHOLDER_NAME ORDER BY STAKEHOLDER_NAME)as PARTNER FROM CR_DEAL_CUSTOMER_STAKEHOLDER_M where customer_id = m.customer_id  GROUP BY customer_id)as PARTNER,cm.CUSTOMER_REGISTRATION_NO,cm.FATHER_HUSBAND_NAME, DATE_FORMAT(cr.DATE_OF_INCORPORATION,'%d-%m-%Y')as DATE_OF_INCORPORATION ,");			
	    			bufInsSqlTempCount.append("  select count(1) from CR_LEAD_DTL ");
	    			bufInsSql.append(" case l.rec_status when 'L' then 'ALLOCATED' when 'X' then 'REJECTED' when 'A' then 'APPROVED'  end as rec_status,(select STATE_DESC from COM_STATE_M where STATE_ID=cr.state) as state    ");
	    			bufInsSql.append(" FROM CR_LEAD_DTL L left join cr_deal_customer_m cm on cm.customer_id = l.customer_id  ");
	    			bufInsSql.append("  left join cr_customer_search_dtl cr on(cr.customer_id=l.customer_id) ");
	    			bufInsSql.append("  join cr_deal_address_m d on(d.bpid=l.customer_id and COMMUNICATION_ADDRESS='Y') ");
	    			bufInsSql.append("  left join CR_DEAL_CUSTOMER_STAKEHOLDER_M m on( m.customer_id=l.customer_id) ");	
	    			bufInsSql.append("  where lead_id=? ");	
	    			if(!CommonFunction.checkNull(leadId).trim().equalsIgnoreCase(""))
	    			{
	    			bufInsSqlTempCount.append("  where LEAD_ID='"+StringEscapeUtils.escapeSql(leadId).trim()+"' ");
	    			}
	    			else
	    			{
	    				bufInsSqlTempCount.append("  where LEAD_ID='"+StringEscapeUtils.escapeSql(vo.getLeadId()).trim()+"' ");
	    			}
	    			
	    		}
	    		
	    		LoggerMsg.info("query for Customer Details Grid Data  : "+bufInsSql.toString());
	    		logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	    		count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
	    		
	    		if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && StringEscapeUtils.escapeSql(vo.getCustomerName()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
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
	    		logger.info("query for Customer Details Grid Data  : "+bufInsSql.toString());
	    		
	    		
	    		if(!CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
	    		{
	    			header = ConnectionDAO.sqlSelectStringPrepStmt(bufInsSql.toString(),dealId);
	    		}
	    		else if(!CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase("")) 
	    		{
	    			header = ConnectionDAO.sqlSelectStringPrepStmt(bufInsSql.toString(),vo.getDealId());
	    		}
	    		else if(!CommonFunction.checkNull(leadId).trim().equalsIgnoreCase(""))
	    		{
	    			header = ConnectionDAO.sqlSelectStringPrepStmt(bufInsSql.toString(),leadId);
	    		}
	    		else
	    		{
	    			header = ConnectionDAO.sqlSelectStringPrepStmt(bufInsSql.toString(),vo.getLeadId());
	    		}
	    		
	    	   // header2 = ConnectionDAO.sqlSelect(bufInsSql.toString());
	    	    
	    	    
	    					
	    		for(int i=0;i<header.size();i++){
	    			
	    			logger.info("header: "+header.size());
	    			ArrayList header1=(ArrayList)header.get(i);
	    			if(header1!=null && header1.size()>0)
	    			{
	    				logger.info("header list size ....................."+header.size());
	    				fetchVo = new dedupeReferalVo();
	    				
	    				fetchVo.setRadioButton("<input type='radio' name='chk' id='chk' value="+CommonFunction.checkNull(header1.get(7)).toString()+" />");
	    				fetchVo.setLbxDealNo((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
	    				fetchVo.setDupDealNO((CommonFunction.checkNull(header1.get(1))).trim());
	    				fetchVo.setDealAmount((CommonFunction.checkNull(header1.get(2))).trim());
	    				fetchVo.setDedupeDOB((CommonFunction.checkNull(header1.get(3))).trim());
	    				fetchVo.setDedupeEmail((CommonFunction.checkNull(header1.get(4))).trim());
	    				fetchVo.setDupCustomerName((CommonFunction.checkNull(header1.get(5))).trim());
	    				fetchVo.setCustomerRole((CommonFunction.checkNull(header1.get(6))).trim());
	    				fetchVo.setDupCustomerID((CommonFunction.checkNull(header1.get(7))).trim());
	    				fetchVo.setApplicantType(CommonFunction.checkNull(header1.get(8)).trim());
						fetchVo.setApplicantCategory(CommonFunction.checkNull(header1.get(8)).trim());
						fetchVo.setVoterId(CommonFunction.checkNull(header1.get(9)).trim());
						fetchVo.setDlnumber(CommonFunction.checkNull(header1.get(10)).trim());
						fetchVo.setPasssport(CommonFunction.checkNull(header1.get(11)).trim());
						fetchVo.setPan(CommonFunction.checkNull(header1.get(12)).trim());
						fetchVo.setUidno(CommonFunction.checkNull(header1.get(13)).trim());
						fetchVo.setTinNum(CommonFunction.checkNull(header1.get(14)).trim());
						fetchVo.setDist(CommonFunction.checkNull(header1.get(15)).trim());
						fetchVo.setAddress1(CommonFunction.checkNull(header1.get(16)).trim());
						fetchVo.setAddress2(CommonFunction.checkNull(header1.get(17)).trim());
						fetchVo.setAddress3(CommonFunction.checkNull(header1.get(18)).trim());
						fetchVo.setfName(CommonFunction.checkNull(header1.get(19)).trim());
						fetchVo.setmName(CommonFunction.checkNull(header1.get(20)).trim());
						fetchVo.setlName(CommonFunction.checkNull(header1.get(21)).trim());
					    fetchVo.setPartner(CommonFunction.checkNull(header1.get(22)).trim());
						fetchVo.setRegistration(CommonFunction.checkNull(header1.get(23)).trim());
						fetchVo.setDedupefatherhus(CommonFunction.checkNull(header1.get(24)).trim());
						fetchVo.setDateOfInc(CommonFunction.checkNull(header1.get(25)).trim());
						fetchVo.setDealStatus(CommonFunction.checkNull(header1.get(26)).trim());
						fetchVo.setState(CommonFunction.checkNull(header1.get(27)).trim());
						fetchVo.setTotalRecordSize(count);
						list.add(fetchVo);
	    			}
	    		}
	    		//fetchVo=null;
	    	}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }



	    return list;
	    }
	    public String insertDedupeCustomerData(dedupeReferalVo vo, String cusId1,String dealId,String txn_type){

	    	boolean status = false;
	    	boolean status1=false;
	    	boolean status2=false;
	    	String result="";
	    	ArrayList qryList = new ArrayList();
	    	ArrayList qryList1 = new ArrayList();
	    	ArrayList qryList2 = new ArrayList();
	    	PrepStmtObject insertPrepStmtObject=null;
	    	PrepStmtObject insertPrepStmtObject1=null;
	    	PrepStmtObject insertPrepStmtObject2=null;
	    	//PrepStmtObject insertPrepStmt= null;
	    	StringBuilder bufInsSql = new StringBuilder();	
	    	String cust="";
	    	int count=0;
	    	StringBuilder bufInsSql1 =    new StringBuilder();	 
	    	StringBuilder bufInsSql2 =    new StringBuilder();	 
	    	StringBuilder bufInsSql3 =    new StringBuilder();
	    	String decision=vo.getDecison();
	       String cusId=vo.getDupCustomerID();
	    	//by rohit..

	    	if (decision.equalsIgnoreCase("X"))
	    	{
	    		insertPrepStmtObject = new PrepStmtObject();
	    		if(txn_type.equalsIgnoreCase("DC"))
	    		{
	    		bufInsSql.append("update cr_deal_dtl set REC_STATUS=?,dedupe_remarks=?,dedupe_decision=?,dedupe_cust_id=? where  deal_id=? ; ");
	    		}
	    		else if(txn_type.equalsIgnoreCase("LE"))
	    		{
	    			bufInsSql.append("update cr_lead_dtl set REC_STATUS=?,dedupe_remarks=?,dedupe_decision=?,dedupe_cust_id=? where  lead_id=? ; ");
	    		}
	    		if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    			insertPrepStmtObject.addString(("X"));
	    		else
	    			insertPrepStmtObject.addString((vo.getDecison()).trim());
	    		if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
	    			insertPrepStmtObject.addNull();
	    		else
	    			insertPrepStmtObject.addString((vo.getDedupeRemarks()).trim());
	    		if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    			insertPrepStmtObject.addNull();
	    		else
	    			insertPrepStmtObject.addString((vo.getDecison()).trim());
	    		if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    			insertPrepStmtObject.addNull();
	    		else
	    			insertPrepStmtObject.addString((vo.getDupCustomerID()).trim());
	    		if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
	    			insertPrepStmtObject.addNull();
	    		else
	    			insertPrepStmtObject.addString((dealId).trim());
	    		
	    		//LoggerMsg.info("query upadte lead/deal as reject case : "+bufInsSql);
	    		
	    		//qryList.add(bufInsSql);
	    		logger.info("query upadte lead/deal as reject case : "+bufInsSql.toString());
	    	    insertPrepStmtObject.setSql(bufInsSql.toString());
	    	 
	    		logger.info("Decision  query for Dedupe Customer  ### "+insertPrepStmtObject.printQuery());
	    		

	    		try {
	    			//qryList.add(insertPrepStmtObject);
	    			//status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	    			logger.info("In update cr_deal_dtl......................status= "+status);
	    			
	    			insertPrepStmtObject1 = new PrepStmtObject();
	    			insertPrepStmtObject2 = new PrepStmtObject();
	    			String flag="Y";
	    			if(!CommonFunction.checkNull(cusId1).trim().equalsIgnoreCase(""))
	    			{
	    		   bufInsSql2.append("UPDATE cr_dedupe_dtl SET MATCH_RECORD=? , DEDUPE_FLAG=?,dedupe_cust_id=?,DEDUPE_DECISION=?  ");
	    		   bufInsSql2.append(" where common_id=?  and deal_customer_id=? and customer_id in ("+cusId1+") ; " );
	    		
	    			
	    			/*if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDedupeRemarks()).trim());*/
	    			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDecison()).trim());
	    			if((CommonFunction.checkNull(flag)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((flag).trim());
	    		
	    			
	    			if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDupCustomerID()).trim());
	    			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDecison()).trim());
	    			if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((dealId).trim());
	    			if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDupCustomerID()).trim());
	    		
	    			logger.info("query upadte cr_dedupe_dtl as approved case : "+bufInsSql2.toString());
	    		    insertPrepStmtObject1.setSql(bufInsSql2.toString());
	    		 
	    			logger.info("Decision  query for Dedupe Customer   ### "+insertPrepStmtObject1.printQuery());
	    			qryList1.add(insertPrepStmtObject1);
	    			status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
	    			}	
	    			
	    			
	    			bufInsSql2.append("UPDATE cr_customer_search_dtl SET DEDUPE_REMARKS=?,DEDUPE_DECISION=? ");
	    			bufInsSql2.append(" where customer_id=? ; " );
	    			
	    			if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject2.addNull();
	    			else
	    				insertPrepStmtObject2.addString((vo.getDedupeRemarks()).trim());
	    			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject2.addNull();
	    			else
	    				insertPrepStmtObject2.addString((vo.getDecison()).trim());
	    			if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject2.addNull();
	    			else
	    				insertPrepStmtObject2.addString((vo.getDupCustomerID()).trim());
	    			logger.info("query upadte cr_customer_search_dtl as reject case : "+bufInsSql2.toString());
	    		    insertPrepStmtObject2.setSql(bufInsSql2.toString());
	    		 
	    			logger.info("Decision  query for Dedupe Customer  ### "+insertPrepStmtObject2.printQuery());
	    			
	    			//logger.info("IN cr_dedupe_dtl() update query1 ### "+bufInsSql1);
	    		//	logger.info("IN cr_customer_search_dtl() update query2 ### "+bufInsSql2);
	    						qryList.add(insertPrepStmtObject);
	    						//qryList1.add(insertPrepStmtObject1);
	    						qryList2.add(insertPrepStmtObject2);
	    							
	    								status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	    								//status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
	    								status2 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);	
	    			
	    								if(status  && status2)
	    								{
	    									result="Datareject";
	    								}
	    			
	    		} 					catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		finally
	    		{
	    			bufInsSql=null;
	    			bufInsSql1=null;
	    			bufInsSql2=null;
	    			qryList.clear();
	    			qryList = null;
	    			qryList1.clear();
	    			qryList1 = null;
	    			qryList2.clear();
	    			qryList2 = null;
	    		}
	    		return result;
	    		
	    	}
	    	else
	    	{
	    		
	    	//rohit changes end..
	    	logger.info("decision  :: "+decision);
	    	logger.info("the value matched record customer Id is :: "+cusId1);
	    		
	    	String str="select count(1) from cr_dedupe_dtl where common_id='"+dealId+"'";
	    	logger.info("qry to check data"+str.toString());
	    	if(txn_type.equalsIgnoreCase("LE"))
	    	{
	    		bufInsSql1.append("select customer_id from cr_lead_dtl where lead_id=? ; ");	
	    	}
	    	else if(txn_type.equalsIgnoreCase("DC"))
	    	{
	    		bufInsSql1.append("select deal_customer_id from cr_deal_dtl where deal_id=? ; ");
	    	}
	    		try {
	    			logger.info("the query for cust on the basis of txn type is :: "+bufInsSql1.toString());
	    			cust=CommonFunction.checkNull(ConnectionDAO.sqlSelectStringPrepStmt(bufInsSql1.toString(),dealId));
	    			count =Integer.parseInt(ConnectionDAO.singleReturn(str.toString()));
	    			if(count==0)
	    			{
	    				return result="noDataFound";
	    			}
	    			else
	    			{
	    		insertPrepStmtObject = new PrepStmtObject();
	    		insertPrepStmtObject1 = new PrepStmtObject();
	    		insertPrepStmtObject2 = new PrepStmtObject();
	    		bufInsSql = new StringBuilder();
	    		String flag="Y";
	    		if(vo.getDecison().equalsIgnoreCase("A"))
	    		{
	    			if(txn_type.equalsIgnoreCase("DC"))
	    			{
	    				bufInsSql.append("update cr_deal_dtl set dedupe_remarks=?,dedupe_decision=?,dedupe_cust_id=? where  deal_id=? ; ");
	    			}
	    			else if(txn_type.equalsIgnoreCase("LE"))
	    			{
	    				bufInsSql.append("update cr_lead_dtl set dedupe_remarks=?,dedupe_decision=?,dedupe_cust_id=? where  lead_id=? ; ");
	    			}
	    			
	    			if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getDedupeRemarks()).trim());
	    			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getDecison()).trim());
	    			if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getDupCustomerID()).trim());
	    			if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((dealId).trim());
	    			
	    			//LoggerMsg.info("query upadte lead/deal as reject case : "+bufInsSql);
	    			
	    			//qryList.add(bufInsSql);
	    			logger.info("query upadte lead/deal as approved case : "+bufInsSql.toString());
	    		    insertPrepStmtObject.setSql(bufInsSql.toString());
	    		 
	    			logger.info("Decision  query for Dedupe Customer  ### "+insertPrepStmtObject.printQuery());
	    			

	    			if(!CommonFunction.checkNull(cusId1).trim().equalsIgnoreCase(""))
	    			{
	    		   bufInsSql2.append("UPDATE cr_dedupe_dtl SET MATCH_RECORD=? , DEDUPE_FLAG=?,dedupe_cust_id=?,DEDUPE_DECISION=?  ");
	    		   bufInsSql2.append(" where common_id=?  and deal_customer_id=? and customer_id in ("+cusId1+") ; " );
	    		
	    			
	    			/*if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDedupeRemarks()).trim());*/
	    			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDecison()).trim());
	    			if((CommonFunction.checkNull(flag)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((flag).trim());
	    		
	    			
	    			if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDupCustomerID()).trim());
	    			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDecison()).trim());
	    			if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((dealId).trim());
	    			if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDupCustomerID()).trim());
	    		
	    			logger.info("query upadte cr_dedupe_dtl as approved case : "+bufInsSql2.toString());
	    		    insertPrepStmtObject1.setSql(bufInsSql2.toString());
	    		 
	    			logger.info("Decision  query for Dedupe Customer  ### "+insertPrepStmtObject1.printQuery());
	    			qryList1.add(insertPrepStmtObject1);
	    			status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
	    			}	
	    				
	    				
	    				bufInsSql3.append("UPDATE cr_customer_search_dtl SET DEDUPE_REMARKS=?,DEDUPE_DECISION=? ");
	    				bufInsSql3.append("where customer_id=? ; " );
	    				
	    				if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
	    					insertPrepStmtObject2.addNull();
	    				else
	    					insertPrepStmtObject2.addString((vo.getDedupeRemarks()).trim());
	    				if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    					insertPrepStmtObject2.addNull();
	    				else
	    					insertPrepStmtObject2.addString((vo.getDecison()).trim());
	    				if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    					insertPrepStmtObject2.addNull();
	    				else
	    					insertPrepStmtObject2.addString((vo.getDupCustomerID()).trim());
	    			
	    				logger.info("query upadte cr_customer_search_dtl as approved case : "+bufInsSql3.toString());
	    			    insertPrepStmtObject2.setSql(bufInsSql3.toString());
	    			 
	    				logger.info("Decision  query for Dedupe Customer  ### "+insertPrepStmtObject2.printQuery());
	    				
	    		
	    		}
	    		
	    		else
	    		{
	    			if(txn_type.equalsIgnoreCase("DC"))
	    			{
	    				bufInsSql.append("update cr_deal_dtl set dedupe_remarks=?,dedupe_decision=?,dedupe_cust_id=? where  deal_id=? ; ");
	    			}
	    			else if(txn_type.equalsIgnoreCase("LE"))
	    			{
	    				bufInsSql.append("update cr_lead_dtl set dedupe_remarks=?,dedupe_decision=?,dedupe_cust_id=? where  lead_id=? ; ");
	    			}
	    			
	    			
	    			if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addNull();
	    			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addNull();
	    			if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addNull();
	    			if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((dealId).trim());
	    			
	    			//LoggerMsg.info("query upadte lead/deal as reject case : "+bufInsSql);
	    			
	    			//qryList.add(bufInsSql);
	    			logger.info("query upadte lead/deal   case : "+bufInsSql.toString());
	    		    insertPrepStmtObject.setSql(bufInsSql.toString());
	    		 
	    			logger.info("Decision  query for Dedupe Customer ### "+insertPrepStmtObject.printQuery());
	    			
	    			if(!CommonFunction.checkNull(cusId1).trim().equalsIgnoreCase(""))
	    			{
	    		   bufInsSql2.append("UPDATE cr_dedupe_dtl SET MATCH_RECORD=? , DEDUPE_FLAG=?,dedupe_cust_id=?,DEDUPE_DECISION=?  ");
	    		   bufInsSql2.append(" where common_id=?  and deal_customer_id=? and customer_id in ("+cusId1+") ; " );
	    		
	    			
	    			/*if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addString((vo.getDedupeRemarks()).trim());*/
	    			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addNull();
	    			if((CommonFunction.checkNull(flag)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addNull();
	    		
	    			
	    			if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addNull();
	    			if((CommonFunction.checkNull(vo.getDecison())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addNull();
	    			if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addNull();
	    			if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject1.addNull();
	    			else
	    				insertPrepStmtObject1.addNull();
	    		
	    			logger.info("query upadte cr_dedupe_dtl as approved case : "+bufInsSql2.toString());
	    		    insertPrepStmtObject1.setSql(bufInsSql2.toString());
	    		 
	    			logger.info("Decision  query for Dedupe Customer  ### "+insertPrepStmtObject1.printQuery());
	    			qryList1.add(insertPrepStmtObject1);
	    			status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
	    			}	
	    						
	    			
	    					
	    					
	    					
	    					bufInsSql3.append("UPDATE cr_customer_search_dtl SET DEDUPE_REMARKS=?,DEDUPE_DECISION=? ");
	    					bufInsSql3.append("where customer_id=? ; " );
	    	
	    					if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
	    						insertPrepStmtObject2.addNull();
	    					else
	    						insertPrepStmtObject2.addNull();
	    					if((CommonFunction.checkNull(vo.getDedupeResult())).trim().equalsIgnoreCase(""))
	    						insertPrepStmtObject2.addNull();
	    					else
	    						insertPrepStmtObject2.addNull();
	    					if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
	    						insertPrepStmtObject2.addNull();
	    					else
	    						insertPrepStmtObject2.addString((vo.getDupCustomerID()).trim());
	    				
	    					logger.info("query upadte cr_customer_search_dtl  case : "+bufInsSql3.toString());
	    				    insertPrepStmtObject2.setSql(bufInsSql3.toString());
	    				 
	    					logger.info("Decision  query for Dedupe Customer  ### "+insertPrepStmtObject2.printQuery());
	    					
	    		
	    		
	    		}
	    	
	    		
	    						/*logger.info("IN deal/lead() update query1 ### "+ bufInsSql);
	    						logger.info("IN cr_dedupe_dtl() update query2 ### "+ bufInsSql2);
	    						logger.info("IN cr_customer_search_dtl() update query3 ### "+ bufInsSql3);*/
	    		
	    		qryList.add(insertPrepStmtObject);
	    	//	qryList1.add(insertPrepStmtObject1);
	    		qryList2.add(insertPrepStmtObject2);
	    					
	    			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	    		//	status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
	    			status2 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
	    					if(status  && status2)
	    						{
	    							result="dataSaved";
	    						}
	    					else
	    							result="dataNotSaved";
	    			}
	    		
	    }
	    		catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	finally{
	    		//bufInsSql=null;
	    		bufInsSql1=null;
	    		bufInsSql2=null;
	    		bufInsSql3=null;
	    		qryList.clear();
	    		qryList = null;
	    		qryList1.clear();
	    		qryList1 = null;
	    		qryList2.clear();
	    		qryList2 = null;
	    	         }
	    	return result;
	    	}
	    	


	    }
	    public String getSavedDecisionData(String dealId,String txnType,String cusId) {
	    	
	    	ArrayList list =new ArrayList();
	    	String remarks="";
	    	String flag="Y";
	    	dedupeReferalVo vo=new dedupeReferalVo();
	    	//String cusId=vo.getDupCustomerID();
	    	StringBuilder str=new StringBuilder();
	    	if(CommonFunction.checkNull(cusId).trim().equalsIgnoreCase(""))
	    	{
	    		if(txnType.equalsIgnoreCase("DC"))
	    		{
	    			str.append("select  dedupe_remarks from cr_deal_dtl where   deal_id = ? " );
	    		}
	    		else if(txnType.equalsIgnoreCase("LE"))
	    		{
	    			str.append("select  dedupe_remarks from cr_lead_dtl where   lead_id =? " );
	    		}
	    	}
	    	else 
	    	{
	    		str.append("select  dedupe_remarks from cr_customer_search_dtl where   customer_id =? " );
	    	}
	    	logger.info("qry to get remars  :: "+str);
	    	try
	    	{
	    		//remarks =CommonFunction.checkNull(ConnectionDAO.singleReturn(str.toString()));
	    		if(CommonFunction.checkNull(cusId).trim().equalsIgnoreCase(""))
	    		list =(ConnectionDAO.sqlSelectStringPrepStmt(str.toString(),dealId));
	    		
	    		else
	    		{
	    		list =(ConnectionDAO.sqlSelectStringPrepStmt(str.toString(),cusId));
	    		}
	    		ArrayList subList=(ArrayList)list.get(0);
	    		
	    		vo.setDedupeRemarks(CommonFunction.checkNull((String)subList.get(0)).trim());
	    		remarks=vo.getDedupeRemarks();
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}

	    	return remarks;
	    }
	    public String getDecisonType(String lbxDealNo, String txnType,String cusId) {
			String decison="";
			ArrayList list=new ArrayList();
			dedupeReferalVo vo=new dedupeReferalVo();
//			String cusId=vo.getDupCustomerID();
			StringBuilder str=new StringBuilder();
			if(CommonFunction.checkNull(cusId).trim().equalsIgnoreCase(""))
			{
				if(txnType.equalsIgnoreCase("DC"))
				{
					str.append("select  dedupe_decision from cr_deal_dtl where deal_id = ? " );
				}
				else if(txnType.equalsIgnoreCase("LE"))
				{
					str.append("select  dedupe_decision from cr_lead_dtl where lead_id =? " );
				
				}
			}
			else 
			{
				str.append("select  dedupe_decision from cr_customer_search_dtl where customer_id = ? " );
			}
			logger.info("qry to get decison  :: "+str);
			try
			{
				//decison =CommonFunction.checkNull(ConnectionDAO.singleReturn(str.toString()));
				if(CommonFunction.checkNull(cusId).trim().equalsIgnoreCase(""))
					list =(ConnectionDAO.sqlSelectStringPrepStmt(str.toString(),lbxDealNo));
				else
				list =(ConnectionDAO.sqlSelectStringPrepStmt(str.toString(),cusId));
				ArrayList subList=(ArrayList)list.get(0);
				
			
				vo.setDecison(CommonFunction.checkNull((String)subList.get(0)).trim());
				decison=vo.getDecison().trim();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
			return decison;

		}
		
		public ArrayList getDedupeCustomerData1(dedupeReferalVo vo)
		{	  ArrayList list1=new ArrayList();
			ArrayList list=new ArrayList();
			String cusId=vo.getDupCustomerID();
			logger.info("cus id by amit  ::: "+cusId);
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = 0;
			int noOfData=0;
			ArrayList header=new ArrayList();
			String flag="Y";
			try
			{
				logger.info("Matching Record Grid Data of Dedupe Referal screen.....");
				
				dedupeReferalVo fetchVo= (dedupeReferalVo) vo;
				boolean appendSQL=false;
				StringBuffer bufInsSql11=new StringBuffer();
				StringBuffer bufInsSql=new StringBuffer();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
				ArrayList searchlist=new ArrayList();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				
				
				String qryLimit=" select  PARAMETER_VALUE from parameter_mst WHERE PARAMETER_KEY='DEDUPE_DATA_LIMIT' "; 
				endRecordIndex=Integer.parseInt(ConnectionDAO.singleReturn(qryLimit.toString()));
				
				if(!CommonFunction.checkNull(vo.getTxnType()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getUpdateFlag()).trim().equalsIgnoreCase("UNC"))
				{  
					 bufInsSql.append(" select  distinct  a.customer_id,CUSTOMER_NAME,case CUSTMER_TYPE when 'C' then 'CORPORATE' when 'I' then 'INDIVIDUAL' else CUSTMER_TYPE end as CUSTMER_TYPE,DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y')as CUSTOMER_DOB,EMAIL_ID,FATHER_HUSBAND_NAME,a.PRIMARY_PHONE,case ifnull(SOURCE_SYSTEM,'') when 'I' THEN 'INTERNAL' WHEN 'E' THEN 'EXTERNAL' ELSE '' END AS SOURCESYSTEM,VOTER_ID,DRIVING_LICENSE,PASSPORT_NO,PAN,UID,TIN_NUMBER, (select DISTRICT_DESC from COM_DISTRICT_M where DISTRICT_ID=a.DISTRICT) as DISTRICT,d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,FIRST_NAME,MIDDLE_NAME,LAST_NAME,( SELECT  GROUP_CONCAT(DISTINCT STAKEHOLDER_NAME ORDER BY STAKEHOLDER_NAME)as PARTNER FROM CR_DEAL_CUSTOMER_STAKEHOLDER_M where customer_id = m.customer_id  GROUP BY customer_id)as PARTNER ,DEAL_NO,case DEAL_STATUS when 'F' then 'FORWARDED' when 'X' then 'REJECTED' when 'A' then 'APPROVED' when 'P' then 'PENDING'   end as DEAL_STATUS,remarks, DATE_FORMAT(DATE_OF_INCORPORATION,'%d-%m-%Y')as DATE_OF_INCORPORATION ");
					 bufInsSql.append(" ,REGISTRATION_NUMBER,MATCHING_RECORD,VAT_REGISTRATION,WEIGHTAGE,(select STATE_DESC from COM_STATE_M where STATE_ID=a.state) AS STATE,DEDUPE_FLAG from cr_dedupe_dtl a  ");		
					 bufInsSql.append("  join cr_deal_address_m d on(d.bpid=a.customer_id and COMMUNICATION_ADDRESS='Y') ");
					 bufInsSql.append(" left join CR_DEAL_CUSTOMER_STAKEHOLDER_M m on( m.customer_id=a.customer_id) ");
				    bufInsSqlTempCount.append("select count(1) from cr_dedupe_dtl ");
				   
				    if(vo.getTxnType().equalsIgnoreCase("DC"))
				   {
					   	bufInsSql.append("  where common_id=? and TXN_TYPE=? ");
					   	bufInsSqlTempCount.append("  where common_id='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' and TXN_TYPE='"+vo.getTxnType()+"'  ");
				   }
				   else if(vo.getTxnType().equalsIgnoreCase("LE"))
				   {
					   	bufInsSql.append("  where common_id=? and TXN_TYPE=? ");
					   	bufInsSqlTempCount.append("  where common_id='"+StringEscapeUtils.escapeSql(vo.getLbxLeadNo()).trim()+"' and TXN_TYPE='"+vo.getTxnType()+"'  ");
				   }
						bufInsSql.append("  and MATCH_RECORD in('A','X') and DEDUPE_FLAG=? order by WEIGHTAGE desc;");//rohit
						bufInsSqlTempCount.append(" and MATCH_RECORD in('A','X')  and DEDUPE_FLAG='"+flag+"' order by WEIGHTAGE desc ; ");//rohit
					
						if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addString((vo.getLbxLeadNo()).trim());
						else
							insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
						if((CommonFunction.checkNull(vo.getTxnType())).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((vo.getTxnType()).trim());
						if((CommonFunction.checkNull(flag)).trim().equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((flag));
						
						LoggerMsg.info("UnderWriter/Viewer query of Dedupe Customer : "+bufInsSql.toString());
						logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
						count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
					
						
						//logger.info("query : "+bufInsSql);
						//String tempStr = bufInsSql.toString();
						//logger.info("tempStr : "+tempStr);
						 insertPrepStmtObject.setSql(bufInsSql.toString());
						 
							logger.info("UnderWriter/Viewer  query for dedupe Customer  ### "+insertPrepStmtObject.printQuery());
					    header = ConnectionDAO.sqlSelectPrepStmt(insertPrepStmtObject);
					    logger.info("the value in the header is;;;;;'''''     "+header);
							if(CommonFunction.checkNull(count).equalsIgnoreCase("")){
								noOfData=0;
							logger.info("the value of count is  :  "+noOfData);
							}
							else {
								noOfData=count;
								logger.info("the value of count is  :  "+noOfData);
							}
							for(int i=0;i<header.size();i++){
								
								logger.info("header: "+header.size());
								ArrayList header1=(ArrayList)header.get(i);
								if(header1!=null && header1.size()>0)
								{
									logger.info("header list size ....................."+header.size());
									fetchVo = new dedupeReferalVo();
									
									fetchVo.setRadioButton((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
									fetchVo.setDupCustomerID((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
									fetchVo.setDupCustomerName((CommonFunction.checkNull(header1.get(1))).trim());
									fetchVo.setDedupeDOB((CommonFunction.checkNull(header1.get(3))).trim());
									fetchVo.setDedupeEmail((CommonFunction.checkNull(header1.get(4))).trim());
									fetchVo.setDedupefatherhus((CommonFunction.checkNull(header1.get(5))).trim());
									fetchVo.setCustomerRole((CommonFunction.checkNull(header1.get(6))).trim());
									fetchVo.setApplicantType(CommonFunction.checkNull(header1.get(2)).trim());
									fetchVo.setApplicantCategory(CommonFunction.checkNull(header1.get(2)).trim());
									fetchVo.setSource(CommonFunction.checkNull(header1.get(7)).trim());
									fetchVo.setVoterId(CommonFunction.checkNull(header1.get(8)).trim());
									fetchVo.setDlnumber(CommonFunction.checkNull(header1.get(9)).trim());
									fetchVo.setPasssport(CommonFunction.checkNull(header1.get(10)).trim());
									fetchVo.setPan(CommonFunction.checkNull(header1.get(11)).trim());
									fetchVo.setUidno(CommonFunction.checkNull(header1.get(12)).trim());
									fetchVo.setTinNum(CommonFunction.checkNull(header1.get(13)).trim());
									fetchVo.setDist(CommonFunction.checkNull(header1.get(14)).trim());
									fetchVo.setAddress1(CommonFunction.checkNull(header1.get(15)).trim());
									fetchVo.setAddress2(CommonFunction.checkNull(header1.get(16)).trim());
									fetchVo.setAddress3(CommonFunction.checkNull(header1.get(17)).trim());
									fetchVo.setfName(CommonFunction.checkNull(header1.get(18)).trim());
									fetchVo.setmName(CommonFunction.checkNull(header1.get(19)).trim());
									fetchVo.setlName(CommonFunction.checkNull(header1.get(20)).trim());
									fetchVo.setPartner(CommonFunction.checkNull(header1.get(21)).trim());
									fetchVo.setDupDealNO(CommonFunction.checkNull(header1.get(22)).trim());
									fetchVo.setDealStatus(CommonFunction.checkNull(header1.get(23)).trim());
									String remark = CommonFunction.checkNull(header1.get(24)).trim();
									
									fetchVo.setDedupeRemarks1((CommonFunction.checkNull(header1.get(24))).trim());
									fetchVo.setDateOfInc(CommonFunction.checkNull(header1.get(25)).trim());
									fetchVo.setRegistration(CommonFunction.checkNull(header1.get(26)).trim());
									
									String matchValue = CommonFunction.checkNull(header1.get(27)).trim();
									if(!matchValue.equalsIgnoreCase(""))
									{
											
											matchValue = matchValue.length()>10 ?matchValue.substring(0, 10).concat("..."):matchValue.concat("...");
											fetchVo.setMatchValue(matchValue);
									}
											
									fetchVo.setMatch(CommonFunction.checkNull(header1.get(27)).trim());
									
									
									fetchVo.setVatNo(CommonFunction.checkNull(header1.get(28)).trim());
									fetchVo.setWeightage(CommonFunction.checkNull(header1.get(29)).trim());
									fetchVo.setState(CommonFunction.checkNull(header1.get(30)).trim());
									if(vo.getTxnType().equalsIgnoreCase("LE"))
									{
										if(CommonFunction.checkNull(header1.get(31)).trim().equalsIgnoreCase("Y"))
											fetchVo.setDedupeFlag("<input  type=checkbox name=chk1 id=chk1"+i+"  value="+CommonFunction.checkNull(header1.get(0))+" checked= checked disabled= disabled />");
											else
												fetchVo.setDedupeFlag("<input  type=checkbox name=chk1 id=chk1"+i+"  value="+CommonFunction.checkNull(header1.get(0))+" disabled= disabled />");
						
									}
									fetchVo.setDedupeFlag("<input  type=checkbox name=chk1 id=chk1"+i+"  value="+CommonFunction.checkNull(header1.get(0))+" />");
									fetchVo.setTotalRecordSize(noOfData);
									list.add(fetchVo);
									
								}
				}
				}
				if (!CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getUpdateFlag()).trim().equalsIgnoreCase("")  )
				{	
				  
					 bufInsSql.append(" select  distinct  a.customer_id,CUSTOMER_NAME,case CUSTMER_TYPE when 'C' then 'CORPORATE' when 'I' then 'INDIVIDUAL' else CUSTMER_TYPE end as CUSTMER_TYPE,DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y')as CUSTOMER_DOB,EMAIL_ID,FATHER_HUSBAND_NAME,a.PRIMARY_PHONE,case ifnull(SOURCE_SYSTEM,'') when 'I' THEN 'INTERNAL' WHEN 'E' THEN 'EXTERNAL' ELSE '' END AS SOURCESYSTEM,VOTER_ID,DRIVING_LICENSE,PASSPORT_NO,PAN,UID,TIN_NUMBER, (select DISTRICT_DESC from COM_DISTRICT_M where DISTRICT_ID=a.DISTRICT) as DISTRICT,d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,FIRST_NAME,MIDDLE_NAME,LAST_NAME,( SELECT  GROUP_CONCAT(DISTINCT STAKEHOLDER_NAME ORDER BY STAKEHOLDER_NAME)as PARTNER FROM CR_DEAL_CUSTOMER_STAKEHOLDER_M where customer_id = m.customer_id  GROUP BY customer_id)as PARTNER ,DEAL_NO,case DEAL_STATUS when 'F' then 'FORWARDED' when 'X' then 'REJECTED' when 'A' then 'APPROVED' when 'P' then 'PENDING'   end as DEAL_STATUS,remarks, DATE_FORMAT(DATE_OF_INCORPORATION,'%d-%m-%Y')as DATE_OF_INCORPORATION ");
					 bufInsSql.append(" ,REGISTRATION_NUMBER,MATCHING_RECORD,VAT_REGISTRATION,WEIGHTAGE,(select STATE_DESC from COM_STATE_M where STATE_ID=a.state) AS STATE,DEDUPE_FLAG from cr_dedupe_dtl a  ");		
					 bufInsSql.append("  join cr_deal_address_m d on(d.bpid=a.customer_id and COMMUNICATION_ADDRESS='Y') ");
					 bufInsSql.append(" left join CR_DEAL_CUSTOMER_STAKEHOLDER_M m on( m.customer_id=a.customer_id) ");
				    bufInsSqlTempCount.append("select count(1) from cr_dedupe_dtl ");
					bufInsSql.append("  where common_id=? and TXN_TYPE=? and DEAL_NO != (select DEAL_NO from cr_deal_dtl where deal_id=? ) and DEAL_CUSTOMER_ID=? order by WEIGHTAGE desc;");			
					bufInsSqlTempCount.append("  where common_id='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"'and TXN_TYPE='"+vo.getTxnType()+"' and DEAL_NO != (select DEAL_NO from cr_deal_dtl where deal_id='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"') and DEAL_CUSTOMER_ID='"+StringEscapeUtils.escapeSql(vo.getDupCustomerID()).trim()+"' order by WEIGHTAGE desc ;");
					
					if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
					if((CommonFunction.checkNull(vo.getTxnType())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTxnType()).trim());
					if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
					if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDupCustomerID()).trim());
					LoggerMsg.info("query for get data from cr_dedupe_dtl : "+bufInsSql.toString());
					logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
					count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				
					
					//logger.info("query : "+bufInsSql);
					//String tempStr = bufInsSql.toString();
					//logger.info("tempStr : "+tempStr);
					 insertPrepStmtObject.setSql(bufInsSql.toString());
					 
						logger.info("Query for Matching Records in Dedupe Referal of Deal::::::;  ### "+insertPrepStmtObject.printQuery());
				    header = ConnectionDAO.sqlSelectPrepStmt(insertPrepStmtObject);
				    logger.info("the value in the header is;;;;;'''''     "+header);
						if(CommonFunction.checkNull(count).equalsIgnoreCase("")){
							noOfData=0;
						logger.info("the value of count is  :  "+noOfData);
						}
						else {
							noOfData=count;
							logger.info("the value of count is  :  "+noOfData);
						}
						for(int i=0;i<header.size();i++){
							
							logger.info("header: "+header.size());
							ArrayList header1=(ArrayList)header.get(i);
							if(header1!=null && header1.size()>0)
							{
								logger.info("header list size ....................."+header.size());
								fetchVo = new dedupeReferalVo();
								
								fetchVo.setRadioButton((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
								fetchVo.setDupCustomerID((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
								fetchVo.setDupCustomerName((CommonFunction.checkNull(header1.get(1))).trim());
								fetchVo.setDedupeDOB((CommonFunction.checkNull(header1.get(3))).trim());
								fetchVo.setDedupeEmail((CommonFunction.checkNull(header1.get(4))).trim());
								fetchVo.setDedupefatherhus((CommonFunction.checkNull(header1.get(5))).trim());
								fetchVo.setCustomerRole((CommonFunction.checkNull(header1.get(6))).trim());
								fetchVo.setApplicantType(CommonFunction.checkNull(header1.get(2)).trim());
								fetchVo.setApplicantCategory(CommonFunction.checkNull(header1.get(2)).trim());
								fetchVo.setSource(CommonFunction.checkNull(header1.get(7)).trim());
								fetchVo.setVoterId(CommonFunction.checkNull(header1.get(8)).trim());
								fetchVo.setDlnumber(CommonFunction.checkNull(header1.get(9)).trim());
								fetchVo.setPasssport(CommonFunction.checkNull(header1.get(10)).trim());
								fetchVo.setPan(CommonFunction.checkNull(header1.get(11)).trim());
								fetchVo.setUidno(CommonFunction.checkNull(header1.get(12)).trim());
								fetchVo.setTinNum(CommonFunction.checkNull(header1.get(13)).trim());
								fetchVo.setDist(CommonFunction.checkNull(header1.get(14)).trim());
								fetchVo.setAddress1(CommonFunction.checkNull(header1.get(15)).trim());
								fetchVo.setAddress2(CommonFunction.checkNull(header1.get(16)).trim());
								fetchVo.setAddress3(CommonFunction.checkNull(header1.get(17)).trim());
								fetchVo.setfName(CommonFunction.checkNull(header1.get(18)).trim());
								fetchVo.setmName(CommonFunction.checkNull(header1.get(19)).trim());
								fetchVo.setlName(CommonFunction.checkNull(header1.get(20)).trim());
								fetchVo.setPartner(CommonFunction.checkNull(header1.get(21)).trim());
								fetchVo.setDupDealNO(CommonFunction.checkNull(header1.get(22)).trim());
								fetchVo.setDealStatus(CommonFunction.checkNull(header1.get(23)).trim());
								String remark = CommonFunction.checkNull(header1.get(24)).trim();
								
								fetchVo.setDedupeRemarks1((CommonFunction.checkNull(header1.get(24))).trim());
								fetchVo.setDateOfInc(CommonFunction.checkNull(header1.get(25)).trim());
								fetchVo.setRegistration(CommonFunction.checkNull(header1.get(26)).trim());
								
								String matchValue = CommonFunction.checkNull(header1.get(27)).trim();
								if(!matchValue.equalsIgnoreCase(""))
								{
										
										matchValue = matchValue.length()>10 ?matchValue.substring(0, 10).concat("..."):matchValue.concat("...");
										fetchVo.setMatchValue(matchValue);
								}
										
								fetchVo.setMatch(CommonFunction.checkNull(header1.get(27)).trim());
								
								
								fetchVo.setVatNo(CommonFunction.checkNull(header1.get(28)).trim());
								fetchVo.setWeightage(CommonFunction.checkNull(header1.get(29)).trim());
								fetchVo.setState(CommonFunction.checkNull(header1.get(30)).trim());
								fetchVo.setDedupeFlag("<input  type=checkbox name=chk1 id=chk1"+i+"  value="+CommonFunction.checkNull(header1.get(0))+" />");
								fetchVo.setTotalRecordSize(noOfData);
								list.add(fetchVo);
								
							}
				}
				}
				
			
				else if(!CommonFunction.checkNull(vo.getLbxLeadNo()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLeadId()).trim().equalsIgnoreCase("") && !CommonFunction.checkNull(vo.getUpdateFlag()).trim().equalsIgnoreCase("UNC"))
				{	
					 bufInsSql.append(" select  distinct  a.customer_id,CUSTOMER_NAME,case CUSTMER_TYPE when 'C' then 'CORPORATE' when 'I' then 'INDIVIDUAL' else CUSTMER_TYPE end as CUSTMER_TYPE,DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y')as CUSTOMER_DOB,EMAIL_ID,FATHER_HUSBAND_NAME,a.PRIMARY_PHONE,case ifnull(SOURCE_SYSTEM,'') when 'I' THEN 'INTERNAL' WHEN 'E' THEN 'EXTERNAL' ELSE '' END AS SOURCESYSTEM,VOTER_ID,DRIVING_LICENSE,PASSPORT_NO,PAN,UID,TIN_NUMBER, (select DISTRICT_DESC from COM_DISTRICT_M where DISTRICT_ID=a.DISTRICT) as DISTRICT,d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,FIRST_NAME,MIDDLE_NAME,LAST_NAME,( SELECT  GROUP_CONCAT(DISTINCT STAKEHOLDER_NAME ORDER BY STAKEHOLDER_NAME)as PARTNER FROM CR_DEAL_CUSTOMER_STAKEHOLDER_M where customer_id = m.customer_id  GROUP BY customer_id)as PARTNER ,DEAL_NO,case DEAL_STATUS when 'F' then 'FORWARDED' when 'X' then 'REJECTED' when 'A' then 'APPROVED' when 'P' then 'PENDING'   end as DEAL_STATUS,remarks, DATE_FORMAT(DATE_OF_INCORPORATION,'%d-%m-%Y')as DATE_OF_INCORPORATION ");
				 bufInsSql.append(" ,REGISTRATION_NUMBER,MATCHING_RECORD,VAT_REGISTRATION,WEIGHTAGE,(select STATE_DESC from COM_STATE_M where STATE_ID=a.state) AS STATE,DEDUPE_FLAG from cr_dedupe_dtl a  ");		
				 bufInsSql.append("  join cr_deal_address_m d on(d.bpid=a.customer_id and COMMUNICATION_ADDRESS='Y') ");
				 bufInsSql.append(" left join CR_DEAL_CUSTOMER_STAKEHOLDER_M m on( m.customer_id=a.customer_id) ");
				    bufInsSqlTempCount.append("select count(1) from cr_dedupe_dtl ");
					bufInsSql.append("  where common_id=? and TXN_TYPE=? and DEAL_CUSTOMER_ID=? order by WEIGHTAGE desc; ");			
				    bufInsSqlTempCount.append("  where common_id='"+StringEscapeUtils.escapeSql(vo.getLbxLeadNo()).trim()+"' and TXN_TYPE='"+vo.getTxnType()+"' and DEAL_NO!='"+StringEscapeUtils.escapeSql(vo.getLbxLeadNo()).trim()+"' and DEAL_CUSTOMER_ID='"+StringEscapeUtils.escapeSql(vo.getDupCustomerID()).trim()+"' order by WEIGHTAGE desc ; ");
				   
				    if((CommonFunction.checkNull(vo.getLbxLeadNo())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getLbxLeadNo()).trim());
					if((CommonFunction.checkNull(vo.getTxnType())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTxnType()).trim());
					if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDupCustomerID()).trim());
					LoggerMsg.info("query for get data from cr_dedupe_dtl : "+bufInsSql.toString());
					logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
					count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				
					
					//logger.info("query : "+bufInsSql);
					//String tempStr = bufInsSql.toString();
					//logger.info("tempStr : "+tempStr);
					 insertPrepStmtObject.setSql(bufInsSql.toString());
					 
						logger.info("Query for Matching Records in Dedupe Referal of Lead::::::; ### "+insertPrepStmtObject.printQuery());
				    header = ConnectionDAO.sqlSelectPrepStmt(insertPrepStmtObject);
				    logger.info("the value in the header is;;;;;'''''     "+header);
						if(CommonFunction.checkNull(count).equalsIgnoreCase("")){
							noOfData=0;
						logger.info("the value of count is  :  "+noOfData);
						}
						else {
							noOfData=count;
							logger.info("the value of count is  :  "+noOfData);
						}
						for(int i=0;i<header.size();i++){
							
							logger.info("header: "+header.size());
							ArrayList header1=(ArrayList)header.get(i);
							if(header1!=null && header1.size()>0)
							{
								logger.info("header list size ....................."+header.size());
								fetchVo = new dedupeReferalVo();
								
								fetchVo.setRadioButton((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
								fetchVo.setDupCustomerID((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
								fetchVo.setDupCustomerName((CommonFunction.checkNull(header1.get(1))).trim());
								fetchVo.setDedupeDOB((CommonFunction.checkNull(header1.get(3))).trim());
								fetchVo.setDedupeEmail((CommonFunction.checkNull(header1.get(4))).trim());
								fetchVo.setDedupefatherhus((CommonFunction.checkNull(header1.get(5))).trim());
								fetchVo.setCustomerRole((CommonFunction.checkNull(header1.get(6))).trim());
								fetchVo.setApplicantType(CommonFunction.checkNull(header1.get(2)).trim());
								fetchVo.setApplicantCategory(CommonFunction.checkNull(header1.get(2)).trim());
								fetchVo.setSource(CommonFunction.checkNull(header1.get(7)).trim());
								fetchVo.setVoterId(CommonFunction.checkNull(header1.get(8)).trim());
								fetchVo.setDlnumber(CommonFunction.checkNull(header1.get(9)).trim());
								fetchVo.setPasssport(CommonFunction.checkNull(header1.get(10)).trim());
								fetchVo.setPan(CommonFunction.checkNull(header1.get(11)).trim());
								fetchVo.setUidno(CommonFunction.checkNull(header1.get(12)).trim());
								fetchVo.setTinNum(CommonFunction.checkNull(header1.get(13)).trim());
								fetchVo.setDist(CommonFunction.checkNull(header1.get(14)).trim());
								fetchVo.setAddress1(CommonFunction.checkNull(header1.get(15)).trim());
								fetchVo.setAddress2(CommonFunction.checkNull(header1.get(16)).trim());
								fetchVo.setAddress3(CommonFunction.checkNull(header1.get(17)).trim());
								fetchVo.setfName(CommonFunction.checkNull(header1.get(18)).trim());
								fetchVo.setmName(CommonFunction.checkNull(header1.get(19)).trim());
								fetchVo.setlName(CommonFunction.checkNull(header1.get(20)).trim());
								fetchVo.setPartner(CommonFunction.checkNull(header1.get(21)).trim());
								fetchVo.setDupDealNO(CommonFunction.checkNull(header1.get(22)).trim());
								fetchVo.setDealStatus(CommonFunction.checkNull(header1.get(23)).trim());
								String remark = CommonFunction.checkNull(header1.get(24)).trim();
								
								fetchVo.setDedupeRemarks1((CommonFunction.checkNull(header1.get(24))).trim());
								fetchVo.setDateOfInc(CommonFunction.checkNull(header1.get(25)).trim());
								fetchVo.setRegistration(CommonFunction.checkNull(header1.get(26)).trim());
								
								String matchValue = CommonFunction.checkNull(header1.get(27)).trim();
								if(!matchValue.equalsIgnoreCase(""))
								{
										
										matchValue = matchValue.length()>10 ?matchValue.substring(0, 10).concat("..."):matchValue.concat("...");
										fetchVo.setMatchValue(matchValue);
								}
										
								fetchVo.setMatch(CommonFunction.checkNull(header1.get(27)).trim());
								
								
								fetchVo.setVatNo(CommonFunction.checkNull(header1.get(28)).trim());
								fetchVo.setWeightage(CommonFunction.checkNull(header1.get(29)).trim());
								fetchVo.setState(CommonFunction.checkNull(header1.get(30)).trim());
								fetchVo.setDedupeFlag("<input  type=checkbox name=chk1 id=chk1"+i+"  value="+CommonFunction.checkNull(header1.get(0))+" />");
								fetchVo.setTotalRecordSize(noOfData);
								list.add(fetchVo);
								
							}
						}
				}
				if ( !CommonFunction.checkNull(vo.getDupCustomerID()).trim().equalsIgnoreCase("") && (!CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase("") || !CommonFunction.checkNull(vo.getLeadId()).trim().equalsIgnoreCase("") ))
				{
					 bufInsSql.append(" select  distinct  a.customer_id,CUSTOMER_NAME,case CUSTMER_TYPE when 'C' then 'CORPORATE' when 'I' then 'INDIVIDUAL' else CUSTMER_TYPE end as CUSTMER_TYPE,DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y')as CUSTOMER_DOB,EMAIL_ID,FATHER_HUSBAND_NAME,a.PRIMARY_PHONE,case ifnull(SOURCE_SYSTEM,'') when 'I' THEN 'INTERNAL' WHEN 'E' THEN 'EXTERNAL' ELSE '' END AS SOURCESYSTEM,VOTER_ID,DRIVING_LICENSE,PASSPORT_NO,PAN,UID,TIN_NUMBER, (select DISTRICT_DESC from COM_DISTRICT_M where DISTRICT_ID=a.DISTRICT) as DISTRICT,d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,FIRST_NAME,MIDDLE_NAME,LAST_NAME,( SELECT  GROUP_CONCAT(DISTINCT STAKEHOLDER_NAME ORDER BY STAKEHOLDER_NAME)as PARTNER FROM CR_DEAL_CUSTOMER_STAKEHOLDER_M where customer_id = m.customer_id  GROUP BY customer_id)as PARTNER ,DEAL_NO,case DEAL_STATUS when 'F' then 'FORWARDED' when 'X' then 'REJECTED' when 'A' then 'APPROVED' when 'P' then 'PENDING'   end as DEAL_STATUS,remarks, DATE_FORMAT(DATE_OF_INCORPORATION,'%d-%m-%Y')as DATE_OF_INCORPORATION ");
					 bufInsSql.append(" ,REGISTRATION_NUMBER,MATCHING_RECORD,VAT_REGISTRATION,WEIGHTAGE,(select STATE_DESC from COM_STATE_M where STATE_ID=a.state) AS STATE,DEDUPE_FLAG from cr_dedupe_dtl a  ");		
					 bufInsSql.append("  join cr_deal_address_m d on(d.bpid=a.customer_id and COMMUNICATION_ADDRESS='Y') ");
					 bufInsSql.append(" left join CR_DEAL_CUSTOMER_STAKEHOLDER_M m on( m.customer_id=a.customer_id) ");
				    bufInsSqlTempCount.append("select count(1) from cr_dedupe_dtl ");
				    bufInsSql.append("  where common_id=? and TXN_TYPE=?  ");
				    bufInsSql.append("  and MATCH_RECORD in('A','X') and DEDUPE_FLAG=? and dedupe_cust_id=? order by WEIGHTAGE desc ; ");//rohit
				    if(vo.getTxnType().equalsIgnoreCase("DC"))
				    {
					bufInsSqlTempCount.append("  where common_id='"+StringEscapeUtils.escapeSql(vo.getDealId()).trim()+"' and  TXN_TYPE='"+vo.getTxnType()+"'   ");
					bufInsSqlTempCount.append(" and MATCH_RECORD in('A','X')  and DEDUPE_FLAG='"+flag+"'  and dedupe_cust_id='"+StringEscapeUtils.escapeSql(vo.getDupCustomerID()).trim()+"' order by WEIGHTAGE desc; ");//rohit
				    }
				    else
				    {
				    	bufInsSqlTempCount.append("  where common_id='"+StringEscapeUtils.escapeSql(vo.getLeadId()).trim()+"' and  TXN_TYPE='"+vo.getTxnType()+"'   ");
						bufInsSqlTempCount.append(" and MATCH_RECORD in('A','X')  and DEDUPE_FLAG='"+flag+"'  and dedupe_cust_id='"+StringEscapeUtils.escapeSql(vo.getDupCustomerID()).trim()+"' order by WEIGHTAGE desc ;");//rohit
					  
				    }
				    if(vo.getTxnType().equalsIgnoreCase("DC"))
				    {
				    if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDealId()).trim());
				    }
				    else
				    {
				    	 if((CommonFunction.checkNull(vo.getLeadId())).trim().equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString((vo.getLeadId()).trim());
				    }
					if((CommonFunction.checkNull(vo.getTxnType())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getTxnType()).trim());
					if((CommonFunction.checkNull(flag)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((flag));
					
					if((CommonFunction.checkNull(vo.getDupCustomerID())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDupCustomerID()).trim());
					LoggerMsg.info("query for get data from cr_dedupe_dtl : "+bufInsSql.toString());
					logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
					count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				
					
					//logger.info("query : "+bufInsSql);
					//String tempStr = bufInsSql.toString();
					//logger.info("tempStr : "+tempStr);
					 insertPrepStmtObject.setSql(bufInsSql.toString());
					 
						logger.info("Searching of selected Data performed in dedupe Referal:::: ### "+insertPrepStmtObject.printQuery());
				    header = ConnectionDAO.sqlSelectPrepStmt(insertPrepStmtObject);
				    logger.info("the value in the header is;;;;;'''''     "+header);
						if(CommonFunction.checkNull(count).equalsIgnoreCase("")){
							noOfData=0;
						logger.info("the value of count is  :  "+noOfData);
						}
						else {
							noOfData=count;
							logger.info("the value of count is  :  "+noOfData);
						}
						for(int i=0;i<header.size();i++){
							
							logger.info("header: "+header.size());
							ArrayList header1=(ArrayList)header.get(i);
							if(header1!=null && header1.size()>0)
							{
								logger.info("header list size ....................."+header.size());
								fetchVo = new dedupeReferalVo();
								
								fetchVo.setRadioButton((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
								fetchVo.setDupCustomerID((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
								fetchVo.setDupCustomerName((CommonFunction.checkNull(header1.get(1))).trim());
								fetchVo.setDedupeDOB((CommonFunction.checkNull(header1.get(3))).trim());
								fetchVo.setDedupeEmail((CommonFunction.checkNull(header1.get(4))).trim());
								fetchVo.setDedupefatherhus((CommonFunction.checkNull(header1.get(5))).trim());
								fetchVo.setCustomerRole((CommonFunction.checkNull(header1.get(6))).trim());
								fetchVo.setApplicantType(CommonFunction.checkNull(header1.get(2)).trim());
								fetchVo.setApplicantCategory(CommonFunction.checkNull(header1.get(2)).trim());
								fetchVo.setSource(CommonFunction.checkNull(header1.get(7)).trim());
								fetchVo.setVoterId(CommonFunction.checkNull(header1.get(8)).trim());
								fetchVo.setDlnumber(CommonFunction.checkNull(header1.get(9)).trim());
								fetchVo.setPasssport(CommonFunction.checkNull(header1.get(10)).trim());
								fetchVo.setPan(CommonFunction.checkNull(header1.get(11)).trim());
								fetchVo.setUidno(CommonFunction.checkNull(header1.get(12)).trim());
								fetchVo.setTinNum(CommonFunction.checkNull(header1.get(13)).trim());
								fetchVo.setDist(CommonFunction.checkNull(header1.get(14)).trim());
								fetchVo.setAddress1(CommonFunction.checkNull(header1.get(15)).trim());
								fetchVo.setAddress2(CommonFunction.checkNull(header1.get(16)).trim());
								fetchVo.setAddress3(CommonFunction.checkNull(header1.get(17)).trim());
								fetchVo.setfName(CommonFunction.checkNull(header1.get(18)).trim());
								fetchVo.setmName(CommonFunction.checkNull(header1.get(19)).trim());
								fetchVo.setlName(CommonFunction.checkNull(header1.get(20)).trim());
								fetchVo.setPartner(CommonFunction.checkNull(header1.get(21)).trim());
								fetchVo.setDupDealNO(CommonFunction.checkNull(header1.get(22)).trim());
								fetchVo.setDealStatus(CommonFunction.checkNull(header1.get(23)).trim());
								String remark = CommonFunction.checkNull(header1.get(24)).trim();
								
								fetchVo.setDedupeRemarks1((CommonFunction.checkNull(header1.get(24))).trim());
								fetchVo.setDateOfInc(CommonFunction.checkNull(header1.get(25)).trim());
								fetchVo.setRegistration(CommonFunction.checkNull(header1.get(26)).trim());
								
								String matchValue = CommonFunction.checkNull(header1.get(27)).trim();
								if(!matchValue.equalsIgnoreCase(""))
								{
										
										matchValue = matchValue.length()>10 ?matchValue.substring(0, 10).concat("..."):matchValue.concat("...");
										fetchVo.setMatchValue(matchValue);
								}
										
								fetchVo.setMatch(CommonFunction.checkNull(header1.get(27)).trim());
								
								
								fetchVo.setVatNo(CommonFunction.checkNull(header1.get(28)).trim());
								fetchVo.setWeightage(CommonFunction.checkNull(header1.get(29)).trim());
								fetchVo.setState(CommonFunction.checkNull(header1.get(30)).trim());
								if(CommonFunction.checkNull(header1.get(31)).trim().equalsIgnoreCase("Y"))
									fetchVo.setDedupeFlag("<input  type=checkbox name=chk1 id=chk1"+i+"  value="+CommonFunction.checkNull(header1.get(0))+" checked= checked disabled= disabled />");
									else
										fetchVo.setDedupeFlag("<input  type=checkbox name=chk1 id=chk1"+i+"  value="+CommonFunction.checkNull(header1.get(0))+" disabled= disabled />");
				
								fetchVo.setTotalRecordSize(noOfData);
								list.add(fetchVo);
								
							}
				}
				
				
				
					}
				}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			finally
		{
			//vo1=null;
		}
		logger.info("the value in the Matching Record list is ;;;;;;;;; "+list);
		
		
			return list;
			
		
		
			}
	
		public ArrayList excelExport(dedupeReferalVo vo)
		{
				ArrayList list=new ArrayList();
				String cusId=vo.getCustomerId();

				logger.info("cus id by amit  ::: "+cusId);
				try
				{
					logger.info("Inside excelExport.....");
					
					dedupeReferalVo fetchVo= (dedupeReferalVo) vo;
					boolean appendSQL=false;
					StringBuffer bufInsSql=new StringBuffer();
					
				

					 bufInsSql.append(" select  distinct  a.customer_id,CUSTOMER_NAME,case CUSTMER_TYPE when 'C' then 'CORPORATE' when 'I' then 'INDIVIDUAL' else CUSTMER_TYPE end as CUSTMER_TYPE,DATE_FORMAT(CUSTOMER_DOB,'%d-%m-%Y')as CUSTOMER_DOB,EMAIL_ID,FATHER_HUSBAND_NAME,a.PRIMARY_PHONE,case ifnull(SOURCE_SYSTEM,'') when 'I' THEN 'INTERNAL' WHEN 'E' THEN 'EXTERNAL' ELSE '' END AS SOURCESYSTEM,VOTER_ID,DRIVING_LICENSE,PASSPORT_NO,PAN,UID,TIN_NUMBER, (select DISTRICT_DESC from COM_DISTRICT_M where DISTRICT_ID=a.DISTRICT) as DISTRICT,d.ADDRESS_LINE1,d.ADDRESS_LINE2,d.ADDRESS_LINE3,FIRST_NAME,MIDDLE_NAME,LAST_NAME,( SELECT  GROUP_CONCAT(DISTINCT STAKEHOLDER_NAME ORDER BY STAKEHOLDER_NAME)as PARTNER FROM CR_DEAL_CUSTOMER_STAKEHOLDER_M where customer_id = m.customer_id  GROUP BY customer_id)as PARTNER ,DEAL_NO,case DEAL_STATUS when 'F' then 'FORWARDED' when 'X' then 'REJECTED' when 'A' then 'APPROVED' when 'P' then 'PENDING'   end as DEAL_STATUS,remarks, DATE_FORMAT(DATE_OF_INCORPORATION,'%d-%m-%Y')as DATE_OF_INCORPORATION ");
					 bufInsSql.append(" ,REGISTRATION_NUMBER,MATCHING_RECORD,VAT_REGISTRATION,WEIGHTAGE,(select STATE_DESC from COM_STATE_M where STATE_ID=a.state) AS STATE from cr_dedupe_dtl a  ");		
					
					   bufInsSql.append("  join cr_deal_address_m d on(d.bpid=a.customer_id and COMMUNICATION_ADDRESS='Y') ");
					   bufInsSql.append(" left join CR_DEAL_CUSTOMER_STAKEHOLDER_M m on( m.customer_id=a.customer_id) ");
						if(vo.getTxnType().equalsIgnoreCase("DC"))
						{
					   bufInsSql.append("  where common_id='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' and TXN_TYPE='"+vo.getTxnType()+"'  and DEAL_NO != (select DEAL_NO from cr_deal_dtl where deal_id='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"')   and  DEAL_CUSTOMER_ID='"+StringEscapeUtils.escapeSql(vo.getDupCustomerID()).trim()+"'   order by WEIGHTAGE desc LIMIT 10; ");			
						}
						else
						{
							  bufInsSql.append("  where common_id='"+StringEscapeUtils.escapeSql(vo.getLbxLeadNo()).trim()+"' and TXN_TYPE='"+vo.getTxnType()+"'  and DEAL_NO != (select lead_id from cr_lead_dtl where lead_id='"+StringEscapeUtils.escapeSql(vo.getLbxLeadNo()).trim()+"')   and  DEAL_CUSTOMER_ID='"+StringEscapeUtils.escapeSql(vo.getDupCustomerID()).trim()+"'   order by WEIGHTAGE desc LIMIT 10; ");
						}
						logger.info("Export to Excel file  **************************** : "+bufInsSql.toString());
					    	
						
						 
						
				
						list = ConnectionDAO.sqlColumnWithResult(bufInsSql.toString());
					
							
				
					}
				catch(Exception e)
				{
					e.printStackTrace();
				}
					
				logger.info("the value in the Export list is ;;;;;;;;; "+list);
				
				
				
					
			
			return list;
			
		}
		public int checkDedupeCustomerData(String leadId) {
			int result=0;
			String res="";
			String res1="";
			StringBuilder str= new StringBuilder();
			StringBuilder str1= new StringBuilder();
			StringBuilder str2= new StringBuilder();
			str.append("select count(1) from cr_dedupe_dtl where common_id='"+leadId+"' and MATCH_RECORD='A' ");
			str1.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'CUST_ENTRY_TAB_LEAD' ");
			str2.append(" select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'CHECK_DEDUPE_REFERRAL_TAB' ");
			logger.info("qry to check dedupe customer data is  :: "+str);
			try
			{	res =ConnectionDAO.singleReturn(str1.toString());
				res1 =ConnectionDAO.singleReturn(str2.toString());
				if(res=="Y" && res1=="Y")
				{
				result =Integer.parseInt(ConnectionDAO.singleReturn(str.toString()));
				}
				else
					result=1;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return result;
		}
		
public boolean getDealRejection(dedupeReferalVo vo,String dealId) {
			
			logger.info("In getDealRejection.......Update mode "+dealId);
			ArrayList qryList=new ArrayList();
			ArrayList qryList1=new ArrayList();
			StringBuilder query1=new StringBuilder();
			StringBuilder query2=new StringBuilder();
			StringBuilder query3=new StringBuilder();
			StringBuilder query4=new StringBuilder();
			StringBuilder query5=new StringBuilder();
			StringBuilder query6=new StringBuilder();
			StringBuilder query7=new StringBuilder();
			StringBuilder query8=new StringBuilder();
			StringBuilder query9=new StringBuilder();
			StringBuilder query10=new StringBuilder();
			StringBuilder query11=new StringBuilder();
			StringBuilder query12=new StringBuilder();
			StringBuilder query13=new StringBuilder();
			StringBuilder query14=new StringBuilder();
			StringBuilder query15=new StringBuilder();
			StringBuilder query16=new StringBuilder();
			StringBuilder query17=new StringBuilder();
			StringBuilder query18=new StringBuilder();
			StringBuilder query19=new StringBuilder();
			StringBuilder query20=new StringBuilder();
			
			query20.append("select DEAL_CURRENT_APPROVAL_LEVEL from cr_deal_dtl where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			String DEAL_CURRENT_APPROVAL_LEVEL=ConnectionDAO.singleReturn(query20.toString());
			logger.info("DEAL_CURRENT_APPROVAL_LEVEL::::"+DEAL_CURRENT_APPROVAL_LEVEL);
			
			
			StringBuffer bufInsSql =	new StringBuffer();
			boolean status=false;
			boolean status1=false;
			
			 query1.append("update cr_deal_loan_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query2.append("update cr_deal_customer_role set STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query3.append("update cr_deal_sd_m set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query4.append("update cr_deal_txncharges_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query5.append("update cr_deal_verification_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());			
			 query6.append("update cr_document_dtl set REC_STATUS='X' where txn_type='DC' and TXNID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query7.append("update cr_deal_installment_plan set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query8.append("update cr_deal_buyer_supplier_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query9.append("update cr_bank_analysis_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query10.append("update cr_deal_movement_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query11.append("update cr_deal_repaysch_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query12.append("update cr_field_verification_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query13.append("update cr_financial_data_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query14.append("update cr_trade_check_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query15.append("update cr_policy_decision set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query16.append("update cr_collatetral_check_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query17.append("update cr_bank_analysis_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query18.append("update cr_obligation_analysis_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			 query19.append("update cr_sales_analysis_dtl set REC_STATUS='X' where DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
			
			 qryList.add(query1);
			 qryList.add(query2);
			 qryList.add(query3);
			 qryList.add(query4);
			 qryList.add(query5);
			 qryList.add(query6);
			 qryList.add(query7);
			 qryList.add(query8);
			 qryList.add(query9);
			 qryList.add(query10);
			 qryList.add(query11);
			 qryList.add(query12);
			 qryList.add(query13);
			 qryList.add(query14);
			 qryList.add(query15);
			 qryList.add(query16);
			 qryList.add(query17);
			 qryList.add(query18);
			 qryList.add(query19);
			
			 
			 
			 try{
				 
				 status1 = ConnectionDAO.sqlInsUpdDelete(qryList);
				 logger.info("In getDealRejection......................status= "+status);
				 
				 if(status1){
					 
				 
				 bufInsSql.append(" INSERT INTO cr_deal_approval_dtl (DEAL_ID,APPROVAL_LEVEL,APPROVAL_DECISION,APPROVAL_BY,APPROVAL_DATE,APPROVAL_REMARKS,REC_STATUS) ");
				 bufInsSql.append(" values ( ");
				 bufInsSql.append(" ?," ); // deal id
				 bufInsSql.append(" ?," ); // APPROVAL_LEVEL
				 bufInsSql.append(" 'X'," ); // APPROVAL_DECISION
				 bufInsSql.append(" ?," ); // APPROVAL_BY
				 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)," ); // APPROVAL_DATE
				 bufInsSql.append(" ?," ); // APPROVAL_REMARKS
				 bufInsSql.append(" 'X' )" ); // rec_status
				 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				 
					if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((dealId).trim());
					
					if((CommonFunction.checkNull(DEAL_CURRENT_APPROVAL_LEVEL)).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((DEAL_CURRENT_APPROVAL_LEVEL).trim());
				 
					
					if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerId()).trim());
					
					if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerId()).trim());
					
					if((CommonFunction.checkNull(vo.getDedupeRemarks())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getDedupeRemarks()).trim());
					
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN dedupe deferal rejection insert query1 ### "+insertPrepStmtObject.printQuery());
					
					qryList1.add(insertPrepStmtObject);
					status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
					
				 }	 
				 
			 }
			 catch (Exception e) {
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
					query8=null;
					query9=null;
					query10=null;
					query11=null;
					query12=null;
					query13=null;
					query14=null;
					query15=null;
					query16=null;
					query17=null;
					query18=null;
					query19=null;
					query20=null;
				}
				return status;
					 
		}	
	    
}
