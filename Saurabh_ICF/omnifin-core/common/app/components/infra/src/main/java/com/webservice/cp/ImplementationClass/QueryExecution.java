package com.webservice.cp.ImplementationClass;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class QueryExecution{
	private static final Logger logger = Logger.getLogger(QueryExecution.class.getName());
	
	PreparedStatement  preparedStatement= null;
	ResultSet resultSet=null;
	PreparedStatement  preparedStatementInner= null;
	ResultSet resultSetMaxId=null;
	CallableStatement cs = null;
	PrepStmtObject  delPrepStmtObject = null;
	Connection con = null;
	
	public Map<String, String> getDealLoanDtl(int txnId,String type){
		Map<String, String>fileDtl=new HashMap<String,String>();
		fileDtl.put("DEAL_LOAN_AMOUNT", "");
		fileDtl.put("DEAL_APPLICATION_FORM_NO", "");
		try{
			String query="";
			if(StringUtils.equalsIgnoreCase(type, "DC") || StringUtils.equalsIgnoreCase(type, "PDC")){
				query="SELECT LOAN.DEAL_LOAN_AMOUNT,DL.DEAL_APPLICATION_FORM_NO,IFNULL(LOAN.DEAL_TENURE,'')LOAN_TENURE "
				+ " FROM CR_DEAL_DTL DL "
				+ " JOIN CR_DEAL_LOAN_DTL LOAN  ON DL.DEAL_ID=LOAN.DEAL_ID AND LOAN.DEAL_LOAN_ID=(SELECT MIN(DEAL_LOAN_ID)DEAL_LOAN_ID FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID=DL.DEAL_ID)"
				+ " WHERE DL.DEAL_ID='"+txnId+"';";
			}else if(StringUtils.equalsIgnoreCase(type, "LIM")){
				query="SELECT LOAN.LOAN_LOAN_AMOUNT,DL.DEAL_APPLICATION_FORM_NO,IFNULL(LOAN.LOAN_TENURE)LOAN_TENURE "
				+ " FROM CR_LOAN_DTL LOAN  "
				+ " JOIN CR_DEAL_DTL DL ON DL.DEAL_ID=LOAN.LOAN_DEAL_ID WHERE LOAN.LOAN_ID='"+txnId+"';";
			}
			logger.info("Qry : "+query);
			ArrayList source = ConnectionDAO.sqlSelect(query.toString());
			logger.info("List size : "+source.size());
			query=null;
			for(int i=0;i<source.size();i++){
				ArrayList subsource=(ArrayList)source.get(i);
				if(subsource.size()>0)
				{
					fileDtl.put("DEAL_LOAN_AMOUNT", CommonFunction.checkNull(subsource.get(0)).trim());
					fileDtl.put("DEAL_APPLICATION_FORM_NO", CommonFunction.checkNull(subsource.get(1)).trim());
					fileDtl.put("LOAN_TENURE", CommonFunction.checkNull(subsource.get(2)).trim());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileDtl;
	}
	
	public Map<String, String> getGcdCustomerDetails(int customerId,String type,String txnId){
		Map<String, String>fileDtl=new HashMap<String,String>();
		fileDtl.put("CUSTOMER_FNAME", "");
		fileDtl.put("CUSTOMER_MNAME", "");
		fileDtl.put("CUSTOMER_LNAME", "");
		fileDtl.put("GENDER", "");
		fileDtl.put("CUSTOMER_DOB", "");
		fileDtl.put("DRIVING_LICENSE", "");
		fileDtl.put("CUSTMER_PAN", "");
		fileDtl.put("VOTER_ID", "");
		fileDtl.put("PASSPORT_NUMBER", "");
		fileDtl.put("UID_NO", "");
		fileDtl.put("CUSTOMER_NAME", "");
		try{
			String query="";
			if(StringUtils.equalsIgnoreCase(type, "DC") || StringUtils.equalsIgnoreCase(type, "PDC")){
				query=" SELECT CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,GENDER,CUSTOMER_DOB,DECRYPT_STRING(DRIVING_LICENSE)DRIVING_LICENSE,"
				+ " DECRYPT_STRING(CUSTMER_PAN)CUSTMER_PAN,DECRYPT_STRING(VOTER_ID)VOTER_ID,"
				+ " DECRYPT_STRING(PASSPORT_NUMBER)PASSPORT_NUMBER,DECRYPT_STRING(UID_NO)UID_NO,CUSTOMER_NAME,"
				+ " IFNULL(CUSTOMER_EMAIL,'')CUSTOMER_EMAIL,IFNULL(CFDD.FINANCIAL_MONTH,'0.00')FINANCIAL_MONTH "
				+ " FROM CR_DEAL_CUSTOMER_M CDCM LEFT "
				+ " JOIN CR_FINANCIAL_DATA_DTL CFDD on (CDCM.CUSTOMER_ID=CFDD.DEAL_CUSTOMER_ID AND CFDD.DEAL_ID='"+txnId+"') "
				+ " WHERE CUSTOMER_ID='"+customerId+"' LIMIT 1";
			}else if(StringUtils.equalsIgnoreCase(type, "LIM")){
				query=" SELECT CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,GENDER,CUSTOMER_DOB,DECRYPT_STRING(DRIVING_LICENSE)DRIVING_LICENSE,"
				+ " DECRYPT_STRING(CUSTMER_PAN)CUSTMER_PAN,DECRYPT_STRING(VOTER_ID)VOTER_ID,DECRYPT_STRING(PASSPORT_NUMBER)PASSPORT_NUMBER"
				+ " ,DECRYPT_STRING(UID_NO)UID_NO,CUSTOMER_NAME,IFNULL(CUSTOMER_EMAIL,'')CUSTOMER_EMAIL,IFNULL(CFDD.FINANCIAL_MONTH,'')FINANCIAL_MONTH "
				+ " FROM GCD_CUSTOMER_M GCM "
				+ " LEFT JOIN CR_FINANCIAL_DATA_DTL CFDD on (GCM.CUSTOMER_ID=CFDD.DEAL_CUSTOMER_ID) "
				+ " WHERE CUSTOMER_ID='"+customerId+"' LIMIT 1";
			}
			logger.info("Qry : "+query);
			ArrayList source = ConnectionDAO.sqlSelect(query.toString());
			logger.info("List size : "+source.size());
			query=null;
			for(int i=0;i<source.size();i++){
				ArrayList subsource=(ArrayList)source.get(i);
				if(subsource.size()>0)
				{
					fileDtl.put("CUSTOMER_FNAME", CommonFunction.checkNull(subsource.get(0)).trim());
					fileDtl.put("CUSTOMER_MNAME", CommonFunction.checkNull(subsource.get(1)).trim());
					fileDtl.put("CUSTOMER_LNAME", CommonFunction.checkNull(subsource.get(2)).trim());
					fileDtl.put("GENDER", CommonFunction.checkNull(subsource.get(3)).trim());
					fileDtl.put("CUSTOMER_DOB", CommonFunction.checkNull(subsource.get(4)).trim());
					fileDtl.put("DRIVING_LICENSE", CommonFunction.checkNull(subsource.get(5)).trim());
					fileDtl.put("CUSTMER_PAN", CommonFunction.checkNull(subsource.get(6)).trim());
					fileDtl.put("VOTER_ID", CommonFunction.checkNull(subsource.get(7)).trim());
					fileDtl.put("PASSPORT_NUMBER", CommonFunction.checkNull(subsource.get(8)).trim());
					fileDtl.put("UID_NO", CommonFunction.checkNull(subsource.get(9)).trim());
					fileDtl.put("CUSTOMER_NAME", CommonFunction.checkNull(subsource.get(10)).trim());
					fileDtl.put("CUSTOMER_EMAIL", CommonFunction.checkNull(subsource.get(11)).trim());
					fileDtl.put("FINANCIAL_MONTH", CommonFunction.checkNull(subsource.get(12)).trim());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileDtl;
	}
	
	public List<Map<String, String>> getComAddressMDetails(int bpId,String type){
		List<Map<String, String>>addressList=null;
		try{
			String query=""
			+ " SELECT ADDR.ADDRESS_LINE1,ADDR.ADDRESS_LINE2,ADDR.ADDRESS_LINE3,ADDR.PINCODE,ADDR.PRIMARY_PHONE,ADDR.STATE,ST.STATE_DESC,"
			+ " IFNULL(DISTRICT.DISTRICT_DESC,'')DISTRICT_DESC,CASE ADDR.ADDRESS_TYPE WHEN 'REI' THEN 'RESIDENCE' ELSE 'PERMANENT' END ADDRESS_TYPE"
			+ " FROM COM_ADDRESS_M ADDR"
			+ " LEFT JOIN COM_STATE_M ST ON ST.STATE_ID=ADDR.STATE "
			+ " LEFT JOIN COM_DISTRICT_M DISTRICT ON(DISTRICT.DISTRICT_ID=ADDR.DISTRICT)"
			+ " WHERE BPTYPE='CS' AND BPID='"+bpId+"'"
			+ " UNION"
			+ " SELECT ADDR.ADDRESS_LINE1,ADDR.ADDRESS_LINE2,ADDR.ADDRESS_LINE3,ADDR.PINCODE,'' PRIMARY_PHONE,ADDR.STATE,ST.STATE_DESC,"
			+ " IFNULL(DISTRICT.DISTRICT_DESC,'')DISTRICT_DESC,'OFFICE' ADDRESS_TYPE"
			+ " FROM COM_EMPLOYMENT_M ADDR"
			+ " LEFT JOIN COM_STATE_M ST ON ST.STATE_ID=ADDR.STATE"
			+ " LEFT JOIN COM_DISTRICT_M DISTRICT ON(DISTRICT.DISTRICT_ID=ADDR.DISTRICT)"
			+ " WHERE CUSTOMER_ID='"+bpId+"'";
			
				
			logger.info("Qry : "+query);
			ArrayList source = ConnectionDAO.sqlSelect(query.toString());
			logger.info("List size : "+source.size());
			query=null;
			for(int i=0;i<source.size();i++){
				ArrayList subsource=(ArrayList)source.get(i);
				if(subsource.size()>0)
				{
					Map<String, String>fileDtl=new HashMap<String,String>();
					fileDtl.put("ADDRESS_LINE1", CommonFunction.checkNull(subsource.get(0)).trim());
					fileDtl.put("ADDRESS_LINE2", CommonFunction.checkNull(subsource.get(1)).trim());
					fileDtl.put("ADDRESS_LINE3", CommonFunction.checkNull(subsource.get(2)).trim());
					fileDtl.put("PIN_CODE", CommonFunction.checkNull(subsource.get(3)).trim());
					fileDtl.put("PRIMARY_PHONE", CommonFunction.checkNull(subsource.get(4)).trim());
					fileDtl.put("STATE", CommonFunction.checkNull(subsource.get(5)).trim());
					fileDtl.put("STATE_DESCRIPTION", CommonFunction.checkNull(subsource.get(6)).trim());
					fileDtl.put("DISTRICT_DESCRIPTION", CommonFunction.checkNull(subsource.get(7)).trim());
					fileDtl.put("ADDRESS_TYPE", CommonFunction.checkNull(subsource.get(8)).trim());
					if(addressList==null){
						addressList=new ArrayList<Map<String, String>>();
					}
					addressList.add(fileDtl);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return addressList;
	}
	
	public Map<String, String> getCibilRefMaxId(String dealId,String customerId){
		Map<String, String>fileDtl=new HashMap<String,String>();
		fileDtl.put("MAX_CIBIL_ID", "0");
		try{
			String query="SELECT MAX(CIBIL_ID)CIBIL_ID FROM CR_CIBIL_REF_DTL WHERE  DEAL_ID='"+dealId+"' AND CUSTOMER_ID='"+customerId+"' AND CIBIL_RESULT='P'";
			logger.info("Qry : "+query);
			String count = ConnectionDAO.singleReturn(query.toString());
			logger.info("max cibil:"+count);
			fileDtl.put("MAX_CIBIL_ID", count);
			query=null;
		}catch(Exception e){
			e.printStackTrace();
			fileDtl.put("MAX_CIBIL_ID", "0");
		}
		return fileDtl;
	}
	
	public Map<String, String> getDealCount(String txnId,String stage){
		Map<String, String>fileDtl=new HashMap<String,String>();
		fileDtl.put("TXNIDCOUNT", "");
		try{
			String query="";
			if(StringUtils.equalsIgnoreCase(stage, "DC") || StringUtils.equalsIgnoreCase(stage, "PDC")){
				query="SELECT COUNT(1) FROM CR_DEAL_DTL WHERE DEAL_ID='"+txnId+"'; ";
			}else if(StringUtils.equalsIgnoreCase(stage, "LIM")){
				query="SELECT COUNT(1) FROM CR_LOAN_DTL WHERE LOAN_ID='"+txnId+"'; ";
			}
			logger.info("Qry : "+query);
			String count = ConnectionDAO.singleReturn(query.toString());
			fileDtl.put("TXNIDCOUNT", count);
			query=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileDtl;
	}
	
	public Map<String, String> getDealLoanCount(String txnId,String stage,String customerId){
		Map<String, String>fileDtl=new HashMap<String,String>();
		fileDtl.put("DEALCOUNT", "");
		try{
			String query="SELECT COUNT(1) FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID='"+txnId+"' ";
			logger.info("Qry : "+query);
			String count = ConnectionDAO.singleReturn(query.toString());
			fileDtl.put("DEALLOANCOUNT", count);
			query=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileDtl;
	}
	
	public Map<String, String> getAddressCount(String txnId,String stage,String customerId){
		Map<String, String>fileDtl=new HashMap<String,String>();
		fileDtl.put("ADDRESSCOUNT", "");
		try{
			String query="";
			String count="";
			String queryAdd="";
			ArrayList gCheckAddr=new ArrayList();
			if(StringUtils.isBlank(customerId)){
				if(StringUtils.equalsIgnoreCase(stage, "DC") || StringUtils.equalsIgnoreCase(stage, "PDC")){
					query=" SELECT DEAL_CUSTOMER_ID FROM cr_deal_customer_role WHERE DEAL_ID='"+txnId+"'; ";
					gCheckAddr=ConnectionDAO.sqlSelect(query.toString());
					if(!CommonFunction.checkNull(gCheckAddr).equalsIgnoreCase("") && gCheckAddr.size()>0)
					{
						int ff=gCheckAddr.size();
						for(int j=0;j<ff;j++)
						{
							ArrayList data=(ArrayList)gCheckAddr.get(j);
							int flag=data.size();
							if(flag>0){
								queryAdd = "select count(*) FROM cr_deal_address_m where BPID='"+(CommonFunction.checkNull(data.get(0))).trim()+"' and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'";
								count = ConnectionDAO.singleReturn(queryAdd.toString());
							}
							fileDtl.put("ADDRESSCOUNT", count);
						}
					}
				}else if(StringUtils.equalsIgnoreCase(stage, "LIM")){
					query=" SELECT DEAL_CUSTOMER_ID FROM CR_LOAN_CUSTOMER_ROLE WHERE LOAN_ID='"+txnId+"'; ";
					gCheckAddr=ConnectionDAO.sqlSelect(query.toString());
					if(!CommonFunction.checkNull(gCheckAddr).equalsIgnoreCase("") && gCheckAddr.size()>0)
					{
						int ff=gCheckAddr.size();
						for(int j=0;j<ff;j++)
						{
							ArrayList data=(ArrayList)gCheckAddr.get(j);
							int flag=data.size();
							if(flag>0){
								queryAdd = "select count(*) FROM com_address_m where BPID='"+(CommonFunction.checkNull(data.get(0))).trim()+"' and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'";
								count = ConnectionDAO.singleReturn(queryAdd.toString());
							}
							fileDtl.put("ADDRESSCOUNT", count);
						}
					}
				}
			}else{
				query="SELECT COUNT(1) FROM COM_ADDRESS_M WHERE BPID='"+(CommonFunction.checkNull(customerId)).trim()+"' and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'";
				count = ConnectionDAO.singleReturn(query.toString());
				fileDtl.put("ADDRESSCOUNT", count);
			}
			logger.info("Qry : "+query);
			query=null;
			queryAdd=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileDtl;
	}
	
	/*public Map<String, String> getComStateMDetails(String stateId){
		Map<String, String>fileDtl=new HashMap<String,String>();
		fileDtl.put("STATE_DESCRIPTION", "");
		try{
			String query="SELECT STATE_DESC "
					+ " FROM COM_STATE_M WHERE STATE_ID='"+stateId+"'";
			logger.info("Qry : "+query);
			ArrayList source = ConnectionDAO.sqlSelect(query.toString());
			logger.info("List size : "+source.size());
			query=null;
			for(int i=0;i<source.size();i++){
				ArrayList subsource=(ArrayList)source.get(i);
				if(subsource.size()>0)
				{
					fileDtl.put("STATE_DESCRIPTION", CommonFunction.checkNull(subsource.get(0)).trim());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileDtl;
	}*/
	
	/*public Map<String, String> getDealLoanDtl(String dealId){
		Map<String, String>fileDtl=new HashMap<String,String>();
		fileDtl.put("DEAL_LOAN_AMOUNT", "");
		fileDtl.put("CUSTOMER_FNAME", "");
		fileDtl.put("CUSTOMER_MNAME", "");
		fileDtl.put("CUSTOMER_LNAME", "");
		fileDtl.put("GENDER", "");
		fileDtl.put("CUSTOMER_DOB", "");
		fileDtl.put("DRIVING_LICENSE", "");
		fileDtl.put("CUSTMER_PAN", "");
		fileDtl.put("VOTER_ID", "");
		fileDtl.put("PASSPORT_NUMBER", "");
		fileDtl.put("UID_NO", "");
		fileDtl.put("ADDRESS_LINE1", "");
		fileDtl.put("ADDRESS_LINE2", "");
		fileDtl.put("ADDRESS_LINE3", "");
		fileDtl.put("PINCODE", "");
		fileDtl.put("STATE", "");
		fileDtl.put("PRIMARY_PHONE", "");
		fileDtl.put("STATE_DESC", "");
		try{
			String query="SELECT DEAL_LOAN_AMOUNT,cust.CUSTOMER_NAME,cust.CUSTOMER_FNAME,cust.CUSTOMER_MNAME,cust.GENDER,cust.CUSTOMER_DOB, "
					+ " cust.DRIVING_LICENSE,cust.CUSTMER_PAN,cust.VOTER_ID,cust.PASSPORT_NUMBER,cust.UID_NO,"
					+ " address.ADDRESS_LINE1,address.ADDRESS_LINE2,address.ADDRESS_LINE3,address.PINCODE,address.STATE,address.PRIMARY_PHONE,state.STATE_DESC "
					+ " FROM CR_DEAL_CUSTOMER_ROLE role "
					+ " JOIN CR_DEAL_LOAN_DTL LOAN ON ROLE.DEAL_ID=LOAN.DEAL_ID "
					+ " join cr_deal_dtl deal on deal.deal_id=loan.deal_id "
					+ " join cr_deal_customer_m cust on cust.customer_id=deal.DEAL_CUSTOMER_ID "
					+ " join cr_deal_address_m address on address.bpid=deal.DEAL_CUSTOMER_ID "
					+ " join com_state_m state on state.state_id=address.state "
					+ " WHERE role.DEAL_ID='"+dealId+"'";
			logger.info("Qry : "+query);
			ArrayList source = ConnectionDAO.sqlSelect(query.toString());
			logger.info("List size : "+source.size());
			query=null;
			for(int i=0;i<source.size();i++){
				ArrayList subsource=(ArrayList)source.get(i);
				if(subsource.size()>0)
				{
					fileDtl.put("DEAL_LOAN_AMOUNT", CommonFunction.checkNull(subsource.get(0)).trim());
					fileDtl.put("CUSTOMER_FNAME", CommonFunction.checkNull(subsource.get(1)).trim());
					fileDtl.put("CUSTOMER_MNAME", CommonFunction.checkNull(subsource.get(2)).trim());
					fileDtl.put("CUSTOMER_LNAME", CommonFunction.checkNull(subsource.get(3)).trim());
					fileDtl.put("GENDER", CommonFunction.checkNull(subsource.get(4)).trim());
					fileDtl.put("CUSTOMER_DOB", CommonFunction.checkNull(subsource.get(5)).trim());
					fileDtl.put("DRIVING_LICENSE", CommonFunction.checkNull(subsource.get(6)).trim());
					fileDtl.put("CUSTMER_PAN", CommonFunction.checkNull(subsource.get(7)).trim());
					fileDtl.put("VOTER_ID", CommonFunction.checkNull(subsource.get(8)).trim());
					fileDtl.put("PASSPORT_NUMBER", CommonFunction.checkNull(subsource.get(9)).trim());
					fileDtl.put("UID_NO", CommonFunction.checkNull(subsource.get(10)).trim());
					fileDtl.put("ADDRESS_LINE1", CommonFunction.checkNull(subsource.get(11)).trim());
					fileDtl.put("ADDRESS_LINE2", CommonFunction.checkNull(subsource.get(12)).trim());
					fileDtl.put("ADDRESS_LINE3", CommonFunction.checkNull(subsource.get(13)).trim());
					fileDtl.put("PINCODE", CommonFunction.checkNull(subsource.get(14)).trim());
					fileDtl.put("STATE", CommonFunction.checkNull(subsource.get(15)).trim());
					fileDtl.put("PRIMARY_PHONE", CommonFunction.checkNull(subsource.get(16)).trim());
					fileDtl.put("STATE_DESC", CommonFunction.checkNull(subsource.get(20)).trim());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileDtl;
	}*/
}