 package com.cp.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.cm.vo.DedupeVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.ACHCapturingDAO;
import com.cp.dao.DedupeDAO;
import com.cp.vo.ACHCapturingVo;
import com.cp.vo.CodeDescVo;

import org.apache.log4j.Logger;


public class ACHCapturingDAOImpl implements ACHCapturingDAO 
{
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	private static final Logger logger = Logger.getLogger(ACHCapturingDAOImpl.class.getName());
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	
	public ArrayList getToDebitList() {
		logger.info("Inside getToDebitList of ACHCapturingDAOImpl");
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
			query.append("select g.VALUE,g.DESCRIPTION from generic_master g where g.GENERIC_KEY='ACH_TO_DEBIT' ORDER BY G.DESCRIPTION");
			ArrayList toDebitList = ConnectionDAO.sqlSelect(query.toString());
		
			query=null;
		
			for(int i=0;i<toDebitList.size();i++){
				ArrayList data=(ArrayList)toDebitList.get(i);
				if(data.size()>0)	{
				ACHCapturingVo achCapturingVo=new ACHCapturingVo();
				achCapturingVo.setSelToDebitValue((CommonFunction.checkNull(data.get(0))).trim());
				achCapturingVo.setSelToDebitDescription((CommonFunction.checkNull(data.get(1))).trim());
				list.add(achCapturingVo);
			}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList getDebitTypeList() {
		logger.info("Inside getDebitTypeList of ACHCapturingDAOImpl");
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
			query.append("select g.VALUE,g.DESCRIPTION from generic_master g where g.GENERIC_KEY='ACH_DEBIT_TYPE' and rec_status='A' ORDER BY G.DESCRIPTION desc");
			ArrayList toDebitList = ConnectionDAO.sqlSelect(query.toString());
		
			query=null;
		
			for(int i=0;i<toDebitList.size();i++){
				ArrayList data=(ArrayList)toDebitList.get(i);
				if(data.size()>0)	{
				ACHCapturingVo achCapturingVo=new ACHCapturingVo();
				achCapturingVo.setSelDebitTypeValue((CommonFunction.checkNull(data.get(0))).trim());
				achCapturingVo.setSelDebitTypeDescription((CommonFunction.checkNull(data.get(1))).trim());
				list.add(achCapturingVo);
			}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList getFixedMaximumAmountList() {
		logger.info("Inside getFixedMaximumAmountList of ACHCapturingDAOImpl");
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
			query.append("select g.VALUE,g.DESCRIPTION from generic_master g where g.GENERIC_KEY='ACH_FIX_MAX_AMT' ORDER BY G.DESCRIPTION");
			ArrayList toDebitList = ConnectionDAO.sqlSelect(query.toString());
		
			query=null;
		
			for(int i=0;i<toDebitList.size();i++){
				ArrayList data=(ArrayList)toDebitList.get(i);
				if(data.size()>0)	{
				ACHCapturingVo achCapturingVo=new ACHCapturingVo();
				achCapturingVo.setSelFixedMaximumAmountValue((CommonFunction.checkNull(data.get(0))).trim());
				achCapturingVo.setSelFixedMaximumAmountDescription((CommonFunction.checkNull(data.get(1))).trim());
				list.add(achCapturingVo);
			}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList getFrequencyList() {
		logger.info("Inside getFrequencyList of ACHCapturingDAOImpl");
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
			query.append("select g.VALUE,g.DESCRIPTION from generic_master g where g.GENERIC_KEY='ACH_Frequency' ORDER BY G.DESCRIPTION");
			ArrayList FrequencyList = ConnectionDAO.sqlSelect(query.toString());
		
			query=null;
		
			for(int i=0;i<FrequencyList.size();i++){
				ArrayList data=(ArrayList)FrequencyList.get(i);
				if(data.size()>0)	{
				ACHCapturingVo achCapturingVo=new ACHCapturingVo();
				achCapturingVo.setSelFrequencyValue((CommonFunction.checkNull(data.get(0))).trim());
				achCapturingVo.setSelFrequencyDescription((CommonFunction.checkNull(data.get(1))).trim());
				list.add(achCapturingVo);
			}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList getACHStatusList() {
		logger.info("Inside getACHStatusList of ACHCapturingDAOImpl");
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
			query.append("select g.VALUE,g.DESCRIPTION from generic_master g where g.GENERIC_KEY='ACH_ach_Status' ORDER BY G.DESCRIPTION");
			ArrayList ACHStatusList = ConnectionDAO.sqlSelect(query.toString());
		
			query=null;
		
			for(int i=0;i<ACHStatusList.size();i++){
				ArrayList data=(ArrayList)ACHStatusList.get(i);
				if(data.size()>0)	{
				ACHCapturingVo achCapturingVo=new ACHCapturingVo();
				achCapturingVo.setSelACHStatusValue((CommonFunction.checkNull(data.get(0))).trim());
				achCapturingVo.setSelACHStatusDescription((CommonFunction.checkNull(data.get(1))).trim());
				list.add(achCapturingVo);
			}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList getACHReceivedStatusList() {
		logger.info("Inside getACHStatusList of ACHCapturingDAOImpl");
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
			query.append("select g.VALUE,g.DESCRIPTION from generic_master g where g.GENERIC_KEY='ACH_Received_Status' ORDER BY G.DESCRIPTION");
			ArrayList ACHReceivedStatusList = ConnectionDAO.sqlSelect(query.toString());
		
			query=null;
		
			for(int i=0;i<ACHReceivedStatusList.size();i++){
				ArrayList data=(ArrayList)ACHReceivedStatusList.get(i);
				if(data.size()>0)	{
				ACHCapturingVo achCapturingVo=new ACHCapturingVo();
				achCapturingVo.setSelACHReceivedStatusValue((CommonFunction.checkNull(data.get(0))).trim());
				achCapturingVo.setSelACHReceivedStatusDescription((CommonFunction.checkNull(data.get(1))).trim());
				list.add(achCapturingVo);
			}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList fetchACHCustomerDetails(String dealId,String functionId)
	{
		logger.info("Inside fetchACHCustomerDetails of ACHCaptufringDAOImpl");
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
	if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000362")){
		
				query.append(" SELECT DISTINCT CDD.DEAL_ID, DATE_FORMAT(CDD.MAKER_DATE,'%d-%m-%Y') LOAN_INITIATION_DATE, ");
				query.append("  IFNULL((SELECT IFNULL(PARAMETER_VALUE,'') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'ACH_HEREBY_AUTHORIZE'),'') ");
				query.append("  HEREBY_AUTHORIZE, IFNULL(CDCBD.BANK_ACCOUNT,''), IFNULL(CDCBD.BANK_ID,''), IFNULL(CBM.BANK_NAME,''), ");
				query.append("  IFNULL(CDCBD.BANK_BRANCH_ID,''), IFNULL(CBBM.BANK_BRANCH_NAME,''), IFNULL(CBBM.BRANCH_MICR_CODE,''), ");
				query.append("  IFNULL(CBBM.BRANCH_IFCS_CODE,''),  ROUND(IFNULL(CDLD.DEAL_LOAN_AMOUNT,0),2) ");
				query.append(" ,ROUND(IFNULL(CRDSS.INSTL_AMOUNT*2,0),2), IFNULL(CAM.PRIMARY_PHONE,''),  ");
				query.append(" IFNULL(CDCM.CUSTOMER_EMAIL,''), IFNULL(CDLD.DEAL_REPAYMENT_FREQ,''), ");
				query.append("  DATE_FORMAT(CDD.MAKER_DATE,'%d-%m-%Y') AS 'FROM_DATE',TBL_REPAY.LAST_INSTL_DATE AS 'TO_DATE', ");
				query.append("   (SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='SPONSOR_BANK_CODE') SPONSOR_CODE, "); 
				query.append(" (SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UTILITY_CODE') UTILITY_CODE, CDD.Loan_no  ");//Virender
				query.append(" FROM CR_DEAL_DTL CDD  ");
				query.append(" join CR_DEAL_LOAN_DTL CDLD on CDD.DEAL_ID=CDLD.deal_id ");
				
				query.append(" LEFT JOIN CR_DEAL_CUSTOMER_M CDCM ON CDD.DEAL_CUSTOMER_ID = CDCM.CUSTOMER_ID "); 
				query.append(" LEFT JOIN CR_DEAL_CUST_BANK_DETAILS_M CDCBD ON CDCBD.CUSTOMER_ID = CDD.DEAL_CUSTOMER_ID "); 
				query.append(" LEFT JOIN COM_BANK_M CBM ON CDCBD.BANK_ID = CBM.BANK_ID and CBM.REC_STATUS='A'  ");
				query.append(" left join cr_repaysch_dtl CRDSS on crdss.LOAN_ID = cdd.loan_id and CRDSS.INSTL_NO=1 ");
				query.append("  and CBM.BANK_ID in (SELECT distinct A.BANK_ID FROM COM_BANK_M A   ");
				query.append(" JOIN COM_BANKBRANCH_M B ON A.BANK_ID=B.BANK_ID WHERE A.REC_STATUS='A' AND B.ACH_STATUS='A') ");
				query.append("  LEFT JOIN COM_BANKBRANCH_M CBBM ON CBBM.BANK_BRANCH_ID = CDCBD.BANK_BRANCH_ID and CBBM.ACH_STATUS='A' "); 
				query.append(" and CBBM.REC_STATUS='A'  ");
				query.append(" LEFT JOIN cr_deal_ADDRESS_M CAM ON CDCM.CUSTOMER_ID = CAM.BPID AND CAM.COMMUNICATION_ADDRESS = 'Y' "); 
				query.append("  LEFT JOIN (SELECT CDRD.DEAL_ID, CDRD.INSTL_NO, IFNULL(CDRD.INSTL_AMOUNT,0)*2 TOTAL_AMOUNT, ");
				query.append("  DATE_FORMAT(DATE_ADD(MAX(CDRD.INSTL_DATE), INTERVAL 0 MONTH) ,'%d-%m-%Y') LAST_INSTL_DATE  ");
				query.append("  FROM CR_DEAL_REPAYSCH_DTL CDRD WHERE DEAL_ID ="+dealId+" "); 
				query.append("  ORDER BY INSTL_NO DESC LIMIT 1) TBL_REPAY ON CDD.DEAL_ID = "+dealId+" ");
				query.append("  WHERE CDD.DEAL_ID ="+dealId+" limit 1 ");
	}else{
		query.append(" SELECT DISTINCT CDD.LOAN_ID, DATE_FORMAT(CDD.MAKER_DATE,'%d-%m-%Y') LOAN_INITIATION_DATE, ");
		query.append("  IFNULL((SELECT IFNULL(PARAMETER_VALUE,'') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'ACH_HEREBY_AUTHORIZE'),'') ");
		query.append("  HEREBY_AUTHORIZE, IFNULL(CDCBD.BANK_ACCOUNT,''), IFNULL(CDCBD.BANK_ID,''), IFNULL(CBM.BANK_NAME,''), ");
		query.append("  IFNULL(CDCBD.BANK_BRANCH_ID,''), IFNULL(CBBM.BANK_BRANCH_NAME,''), IFNULL(CBBM.BRANCH_MICR_CODE,''), ");
		query.append("  IFNULL(CBBM.BRANCH_IFCS_CODE,''),  ROUND(IFNULL(CDD.LOAN_LOAN_AMOUNT,0),2) ");
		query.append(" ,ROUND(IFNULL(CRDSS.INSTL_AMOUNT*2,0),2), IFNULL(CAM.PRIMARY_PHONE,''),  ");
		query.append(" IFNULL(CDCM.CUSTOMER_EMAIL,''), IFNULL(CDD.LOAN_REPAYMENT_FREQ,''), ");
		query.append("  DATE_FORMAT(CDD.MAKER_DATE,'%d-%m-%Y') AS 'FROM_DATE',TBL_REPAY.LAST_INSTL_DATE AS 'TO_DATE', ");
		query.append("   (SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='SPONSOR_BANK_CODE') SPONSOR_CODE, "); 
		query.append(" (SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UTILITY_CODE') UTILITY_CODE, CDD.Loan_no  ");//Virender
		query.append(" FROM CR_LOAN_DTL CDD  ");
		query.append(" LEFT JOIN GCD_CUSTOMER_M CDCM ON CDD.LOAN_CUSTOMER_ID = CDCM.CUSTOMER_ID "); 
		query.append(" LEFT JOIN CUST_BANK_DETAILS_M CDCBD ON CDCBD.CUSTOMER_ID = CDD.LOAN_CUSTOMER_ID ");
		query.append(" left join cr_repaysch_dtl CRDSS on crdss.LOAN_ID = cdd.loan_id and CRDSS.INSTL_NO=1 ");
		query.append(" LEFT JOIN COM_BANK_M CBM ON CDCBD.BANK_ID = CBM.BANK_ID and CBM.REC_STATUS='A'  ");
		query.append("  and CBM.BANK_ID in (SELECT distinct A.BANK_ID FROM COM_BANK_M A   ");
		query.append(" JOIN COM_BANKBRANCH_M B ON A.BANK_ID=B.BANK_ID WHERE A.REC_STATUS='A' AND B.ACH_STATUS='A') ");
		query.append("  LEFT JOIN COM_BANKBRANCH_M CBBM ON CBBM.BANK_BRANCH_ID = CDCBD.BANK_BRANCH_ID and CBBM.ACH_STATUS='A' "); 
		query.append(" and CBBM.REC_STATUS='A'  ");
		query.append(" LEFT JOIN com_ADDRESS_M CAM ON CDCM.CUSTOMER_ID = CAM.BPID AND CAM.COMMUNICATION_ADDRESS = 'Y' "); 
		query.append("  LEFT JOIN (SELECT CDRD.LOAN_ID, CDRD.INSTL_NO, IFNULL(CDRD.INSTL_AMOUNT,0)*2 TOTAL_AMOUNT, ");
		query.append("  DATE_FORMAT(DATE_ADD(MAX(CDRD.INSTL_DATE), INTERVAL 0 MONTH) ,'%d-%m-%Y') LAST_INSTL_DATE  ");
		query.append("  FROM CR_REPAYSCH_DTL CDRD WHERE LOAN_ID ="+dealId+" "); 
		query.append("  ORDER BY INSTL_NO DESC LIMIT 1) TBL_REPAY ON CDD.LOAN_ID = "+dealId+" ");
		query.append("  WHERE CDD.LOAN_ID ="+dealId+" limit 1 ");
}
				
			
			logger.info("ACH Customer Detail Query : "  + query.toString());
			ArrayList ACHStatusList = ConnectionDAO.sqlSelect(query.toString());
		
			query=null;
		
			for(int i=0;i<ACHStatusList.size();i++){
				ArrayList data=(ArrayList)ACHStatusList.get(i);
				if(data.size()>0)	{
				ACHCapturingVo achCapturingVo=new ACHCapturingVo();
				achCapturingVo.setTxtDealNo ((CommonFunction.checkNull(data.get(0))).trim());
				achCapturingVo.setDteDate((CommonFunction.checkNull(data.get(1))).trim());
				achCapturingVo.setTxtWeHerebyAuthorize((CommonFunction.checkNull(data.get(2))).trim());
				achCapturingVo.setTxtBankAccountNo((CommonFunction.checkNull(data.get(3))).trim());
				achCapturingVo.setHidBankName((CommonFunction.checkNull(data.get(4))).trim());
				achCapturingVo.setTxtBankName((CommonFunction.checkNull(data.get(5))).trim());
				achCapturingVo.setHidBankBranchName((CommonFunction.checkNull(data.get(6))).trim());
				achCapturingVo.setTxtBankBranchName((CommonFunction.checkNull(data.get(7))).trim());
				achCapturingVo.setTxtMicr((CommonFunction.checkNull(data.get(8))).trim());
				achCapturingVo.setTxtIfsc((CommonFunction.checkNull(data.get(9))).trim());
				achCapturingVo.setTxtLoanAmount((CommonFunction.checkNull(data.get(10))).trim());
				achCapturingVo.setTxtTotalAmount((CommonFunction.checkNull(data.get(11))).trim());
				achCapturingVo.setTxtPhoneNo((CommonFunction.checkNull(data.get(12))).trim());
				achCapturingVo.setTxtEmailId((CommonFunction.checkNull(data.get(13))).trim());
				achCapturingVo.setSelFrequency((CommonFunction.checkNull(data.get(14))).trim());
				achCapturingVo.setDteFromDate((CommonFunction.checkNull(data.get(15))).trim());
				achCapturingVo.setDteToDate((CommonFunction.checkNull(data.get(16))).trim());
				achCapturingVo.setTxtSponsorBankCode((CommonFunction.checkNull(data.get(17))).trim());
				achCapturingVo.setTxtUtilityCode((CommonFunction.checkNull(data.get(18))).trim());
				achCapturingVo.setTxtReferenceNo((CommonFunction.checkNull(data.get(19))).trim());
				list.add(achCapturingVo);
			}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList fetchAchRecordList( String achCapturingId,String functionId)
	{
		logger.info("Inside fetchAchRecord of ACHCapturingDAOImpl");
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
			if(CommonFunction.checkNull(functionId).equalsIgnoreCase("3000362")){
			query.append("SELECT CACD.ACH_CAPTURING_ID, CACD.DEAL_ID, CDD.DEAL_NO, ");
			query.append("DATE_FORMAT(CACD.ACH_DATE,'%d-%m-%Y'), CACD.HEREBY_AUTHORIZE, CACD.BANK_ACCOUNT_NO, "); 
			query.append("CACD.BANK_ID, CBM.BANK_NAME, CACD.BRANCH_ID,  cbbm.BANK_BRANCH_NAME, CACD.MICR, CACD.IFSC,round(CACD.AMOUNT_RS,2), ");
			query.append("round(crd.INSTL_AMOUNT*2,2), CACD.REFERENCE_NO, CACD.REFERENCE_NO2, CACD.PHONE_NO, CACD.EMAIL_ID, CACD.FREQUENCY, ");
			query.append("DATE_FORMAT(CACD.FROM_DATE,'%d-%m-%Y'), DATE_FORMAT(CACD.TO_DATE,'%d-%m-%Y'), CACD.SPONSOR_BANK_CODE, CACD.UTILITY_CODE, CACD.TO_DEBIT,  ");
			query.append("CACD.DEBIT_TYPE, CACD.NAME_ACCOUNT_HOLDER, CACD.MAKER_ID, CACD.MAKER_DATE, ");
			query.append("CACD.AUTHOR_ID, CACD.AUTHOR_DATE, CACD.CUSTOMER_NAME,  ");
			query.append("(SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='SPONSOR_BANK_CODE') SPONSOR_CODE, ");
			query.append("(SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UTILITY_CODE') UTILITY_CODE,CACD.ACH_DECISION ");
			query.append("FROM CR_ACH_CAPTURING_DTL CACD ");
			query.append("LEFT JOIN CR_DEAL_DTL CDD ON CACD.DEAL_ID = CDD.DEAL_ID ");
			query.append("LEFT JOIN COM_BANK_M CBM ON CACD.BANK_ID = CBM.BANK_ID ");
			query.append("LEFT JOIN COM_BANKBRANCH_M CBBM ON CACD.BRANCH_ID = CBBM.BANK_BRANCH_ID ");
			query.append("left join cr_repaysch_dtl crd on crd.LOAN_ID = CACD.LOAN_ID and INSTL_NO=1");
			query.append("WHERE CACD.ACH_CAPTURING_ID ='"+achCapturingId+"' ");
			}else{
				query.append("SELECT CACD.ACH_CAPTURING_ID, CACD.LOAN_ID, CDD.LOAN_NO, ");
				query.append("DATE_FORMAT(CACD.ACH_DATE,'%d-%m-%Y'), CACD.HEREBY_AUTHORIZE, CACD.BANK_ACCOUNT_NO, "); 
				query.append("CACD.BANK_ID, CBM.BANK_NAME, CACD.BRANCH_ID,  cbbm.BANK_BRANCH_NAME, CACD.MICR, CACD.IFSC,round(CACD.AMOUNT_RS,2), ");
				query.append("ROUND(IFNULL((SELECT INSTL_AMOUNT FROM CR_REPAYSCH_DTL WHERE LOAN_ID=CACD.LOAN_ID AND REC_TYPE='I' AND INSTL_AMOUNT<>0 LIMIT 1),0)*2,2) AS INSTL_AMOUNT, CACD.REFERENCE_NO, CACD.REFERENCE_NO2, CACD.PHONE_NO, CACD.EMAIL_ID, CACD.FREQUENCY, ");
				query.append("DATE_FORMAT(CACD.FROM_DATE,'%d-%m-%Y'), DATE_FORMAT(CACD.TO_DATE,'%d-%m-%Y'), CACD.SPONSOR_BANK_CODE, CACD.UTILITY_CODE, CACD.TO_DEBIT,  ");
				query.append("CACD.DEBIT_TYPE, CACD.NAME_ACCOUNT_HOLDER, CACD.MAKER_ID, CACD.MAKER_DATE, ");
				query.append("CACD.AUTHOR_ID, CACD.AUTHOR_DATE, CACD.CUSTOMER_NAME,  ");
				query.append("(SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='SPONSOR_BANK_CODE') SPONSOR_CODE, ");
				query.append("(SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='UTILITY_CODE') UTILITY_CODE,CACD.ACH_DECISION ");
				query.append("FROM CR_ACH_CAPTURING_DTL CACD ");
				query.append("LEFT JOIN CR_LOAN_DTL CDD ON CACD.LOAN_ID = CDD.LOAN_ID ");
				query.append("LEFT JOIN COM_BANK_M CBM ON CACD.BANK_ID = CBM.BANK_ID ");
				query.append("LEFT JOIN COM_BANKBRANCH_M CBBM ON CACD.BRANCH_ID = CBBM.BANK_BRANCH_ID ");
				//query.append("left join cr_repaysch_dtl crd on crd.LOAN_ID = CACD.LOAN_ID and INSTL_NO=1 ");
				query.append("WHERE CACD.ACH_CAPTURING_ID ='"+achCapturingId+"' ");
				}
			
			
			
			logger.info("ACH Customer Detail Query : "  + query.toString());
			ArrayList ACHStatusList = ConnectionDAO.sqlSelect(query.toString());
		
			query=null;
		
			for(int i=0;i<ACHStatusList.size();i++){
				ArrayList data=(ArrayList)ACHStatusList.get(i);
				if(data.size()>0) {
				ACHCapturingVo achCapturingVo=new ACHCapturingVo();
				achCapturingVo.setHidAchCapturingId((CommonFunction.checkNull(data.get(0))).trim());
				achCapturingVo.setHidDealNo((CommonFunction.checkNull(data.get(1))).trim());
				achCapturingVo.setTxtDealNo((CommonFunction.checkNull(data.get(2))).trim());
				achCapturingVo.setDteDate((CommonFunction.checkNull(data.get(3))).trim());
				achCapturingVo.setTxtWeHerebyAuthorize((CommonFunction.checkNull(data.get(4))).trim());
				achCapturingVo.setTxtBankAccountNo((CommonFunction.checkNull(data.get(5))).trim());
				
				achCapturingVo.setHidBankName((CommonFunction.checkNull(data.get(6))).trim());
				achCapturingVo.setTxtBankName((CommonFunction.checkNull(data.get(7))).trim());
				achCapturingVo.setHidBankBranchName((CommonFunction.checkNull(data.get(8))).trim());
				achCapturingVo.setTxtBankBranchName((CommonFunction.checkNull(data.get(9))).trim());
				achCapturingVo.setTxtMicr((CommonFunction.checkNull(data.get(10))).trim());
				
				achCapturingVo.setTxtIfsc((CommonFunction.checkNull(data.get(11))).trim());
				achCapturingVo.setTxtLoanAmount((CommonFunction.checkNull(data.get(12))).trim());
				achCapturingVo.setTxtTotalAmount((CommonFunction.checkNull(data.get(13))).trim());
				achCapturingVo.setTxtReferenceNo((CommonFunction.checkNull(data.get(14))).trim());
				achCapturingVo.setTxtReferenceNo2((CommonFunction.checkNull(data.get(15))).trim());
				achCapturingVo.setTxtPhoneNo((CommonFunction.checkNull(data.get(16))).trim());
				
				achCapturingVo.setTxtEmailId((CommonFunction.checkNull(data.get(17))).trim());
				achCapturingVo.setSelFrequency((CommonFunction.checkNull(data.get(18))).trim());
				achCapturingVo.setDteFromDate((CommonFunction.checkNull(data.get(19))).trim());
				achCapturingVo.setDteToDate((CommonFunction.checkNull(data.get(20))).trim());
				achCapturingVo.setTxtSponsorBankCode((CommonFunction.checkNull(data.get(21))).trim());
				
				achCapturingVo.setTxtUtilityCode((CommonFunction.checkNull(data.get(22))).trim());
				achCapturingVo.setSelToDebit((CommonFunction.checkNull(data.get(23))).trim());
				achCapturingVo.setSelDebitType((CommonFunction.checkNull(data.get(24))).trim());
				achCapturingVo.setTxtNameAccountHolder((CommonFunction.checkNull(data.get(25))).trim());
				
				achCapturingVo.setMakerId((CommonFunction.checkNull(data.get(26))).trim());
				achCapturingVo.setMakerDate((CommonFunction.checkNull(data.get(27))).trim());
				achCapturingVo.setAuthorId((CommonFunction.checkNull(data.get(28))).trim());
				achCapturingVo.setAuthorDate((CommonFunction.checkNull(data.get(29))).trim());
				achCapturingVo.setTxtCustomerName((CommonFunction.checkNull(data.get(30))).trim());
				
				achCapturingVo.setTxtSponsorBankCode((CommonFunction.checkNull(data.get(31))).trim());
				achCapturingVo.setTxtUtilityCode((CommonFunction.checkNull(data.get(32))).trim());
				achCapturingVo.setAchDecision((CommonFunction.checkNull(data.get(33))).trim()); //pooja code for ACH Decision
				list.add(achCapturingVo);
			}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public int saveNewACHRecordList(ACHCapturingVo achCapturingVo) {
		logger.info("Inside saveNewACHRecordList of ACHCapturingDAOImpl");
		
		boolean status = false; 
		StringBuffer bufInsSql =new StringBuffer();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList qryList=new ArrayList();
		int generatedAchCapId=0;
		try{
			if(CommonFunction.checkNull(achCapturingVo.getFunctionId()).equalsIgnoreCase("3000362")){
			bufInsSql.append("insert into cr_ach_capturing_dtl(DEAL_ID,customer_name,ach_date,hereby_Authorize,Bank_account_no,bank_id,branch_id,micr,ifsc,amount_rs,total_amount,reference_no,reference_no2,phone_no,email_id,frequency,from_date,to_date,SPONSOR_BANK_CODE,UTILITY_CODE,TO_DEBIT,DEBIT_TYPE,FIXED_MAX_AMOUNT,NAME_ACCOUNT_HOLDER,MAKER_ID,MAKER_DATE, REC_STATUS,ACH_DECISION) values (?,?,STR_TO_DATE(?, '"+dateFormatWithTime+"') ,?,?,?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?, '"+dateFormatWithTime+"') ,STR_TO_DATE(?, '"+dateFormatWithTime+"') ,?,?,?,?,?,?,?,STR_TO_DATE(?, '"+dateFormatWithTime+"'),?,?)");
			
			if((CommonFunction.checkNull(achCapturingVo.getHidDealNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getHidDealNo()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtCustomerName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtCustomerName()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getDteDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getDteDate()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtWeHerebyAuthorize())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtWeHerebyAuthorize()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtBankAccountNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtBankAccountNo()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getHidBankName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getHidBankName()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getHidBankBranchName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getHidBankBranchName()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtMicr())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtMicr()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtIfsc())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtIfsc()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtLoanAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtLoanAmount()).replaceAll(",","").trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtTotalAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtTotalAmount()).replaceAll(",","").trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtReferenceNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtReferenceNo()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtReferenceNo2())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtReferenceNo2()).trim());

			
			if((CommonFunction.checkNull(achCapturingVo.getTxtPhoneNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtPhoneNo()).trim());
			

			if((CommonFunction.checkNull(achCapturingVo.getTxtEmailId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtEmailId()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelFrequency())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelFrequency()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getDteFromDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getDteFromDate()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getDteToDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getDteToDate()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtSponsorBankCode())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtSponsorBankCode()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtUtilityCode())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtUtilityCode()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelToDebit())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelToDebit()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelDebitType())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelDebitType()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelFixedMaximumAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelFixedMaximumAmount()).replaceAll(",","").trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtNameAccountHolder())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtNameAccountHolder()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getMakerId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getMakerId()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getMakerDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getMakerDate()).trim());
			
			insertPrepStmtObject.addString("P");
			// pooja code for ACH Decision
			if((CommonFunction.checkNull(achCapturingVo.getAchDecision())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getAchDecision()).trim());
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
			StringBuilder strBufferAchCapId=new StringBuilder();
			strBufferAchCapId.append("select max(ach_capturing_id) from cr_ach_capturing_dtl where DEAL_ID = "+achCapturingVo.getHidDealNo());
			generatedAchCapId = Integer.parseInt(ConnectionDAO.singleReturn(strBufferAchCapId.toString()));
			}else{
				String loanQuery="select loan_deal_id from cr_LOAN_DTL where loan_id='"+achCapturingVo.getHidDealNo()+"' ";
				String dealId=ConnectionDAO.singleReturn(loanQuery);
				bufInsSql.append("insert into cr_ach_capturing_dtl(LOAN_ID,DEAL_ID,customer_name,ach_date,hereby_Authorize,Bank_account_no,bank_id,branch_id,micr,ifsc,amount_rs,total_amount,reference_no,reference_no2,phone_no,email_id,frequency,from_date,to_date,SPONSOR_BANK_CODE,UTILITY_CODE,TO_DEBIT,DEBIT_TYPE,FIXED_MAX_AMOUNT,NAME_ACCOUNT_HOLDER,MAKER_ID,MAKER_DATE, REC_STATUS,ACH_DECISION) values (?,?,?,STR_TO_DATE(?, '"+dateFormatWithTime+"') ,?,?,?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?, '"+dateFormatWithTime+"') ,STR_TO_DATE(?, '"+dateFormatWithTime+"') ,?,?,?,?,?,?,?,STR_TO_DATE(?, '"+dateFormatWithTime+"'),?,?)");
				
				if((CommonFunction.checkNull(achCapturingVo.getHidDealNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getHidDealNo()).trim());
				
				if((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((dealId).trim());
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtCustomerName())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtCustomerName()).trim());
				
				if((CommonFunction.checkNull(achCapturingVo.getDteDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getDteDate()).trim());
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtWeHerebyAuthorize())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtWeHerebyAuthorize()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtBankAccountNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtBankAccountNo()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getHidBankName())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getHidBankName()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getHidBankBranchName())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getHidBankBranchName()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtMicr())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtMicr()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtIfsc())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtIfsc()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtLoanAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtLoanAmount()).replaceAll(",","").trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtTotalAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtTotalAmount()).replaceAll(",","").trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtReferenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtReferenceNo()).trim());
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtReferenceNo2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtReferenceNo2()).trim());

				
				if((CommonFunction.checkNull(achCapturingVo.getTxtPhoneNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtPhoneNo()).trim());
				

				if((CommonFunction.checkNull(achCapturingVo.getTxtEmailId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtEmailId()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getSelFrequency())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getSelFrequency()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getDteFromDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getDteFromDate()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getDteToDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getDteToDate()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtSponsorBankCode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtSponsorBankCode()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtUtilityCode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtUtilityCode()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getSelToDebit())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getSelToDebit()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getSelDebitType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getSelDebitType()).trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getSelFixedMaximumAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getSelFixedMaximumAmount()).replaceAll(",","").trim());
				
				
				if((CommonFunction.checkNull(achCapturingVo.getTxtNameAccountHolder())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getTxtNameAccountHolder()).trim());
				
				if((CommonFunction.checkNull(achCapturingVo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getMakerId()).trim());
				
				if((CommonFunction.checkNull(achCapturingVo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getMakerDate()).trim());
				
				insertPrepStmtObject.addString("P");
				// pooja code for ACH Decision
				if((CommonFunction.checkNull(achCapturingVo.getAchDecision())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((achCapturingVo.getAchDecision()).trim());
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
				StringBuilder strBufferAchCapId=new StringBuilder();
				strBufferAchCapId.append("select max(ach_capturing_id) from cr_ach_capturing_dtl where LOAN_ID = "+achCapturingVo.getHidDealNo());
				generatedAchCapId = Integer.parseInt(ConnectionDAO.singleReturn(strBufferAchCapId.toString()));
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return generatedAchCapId;
	}
	
	
	
	public ArrayList fetchSavedRecordList(ACHCapturingVo achCapturingVo)
	{
		logger.info("Inside fetchsaveNewACHRecord of ACHCapturingDAOImpl");
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder strBuffer=new StringBuilder();
			StringBuilder strBufferCount=new StringBuilder();
			if(achCapturingVo.getFunctionId().equalsIgnoreCase("3000362")){
			strBuffer.append("select cacd.ach_capturing_id, cacd.DEAL_id,cdd.DEAL_NO,customer_name, susm.user_name");
			strBuffer.append(" from cr_ach_capturing_dtl cacd");
			strBuffer.append(" left join CR_DEAL_DTL cdd on cacd.DEAL_id = cdd.DEAL_ID");
			strBuffer.append(" left join sec_user_m susm on susm.user_id = cacd.maker_id");
			strBuffer.append(" where cacd.rec_status = 'P' and cacd.LOAN_ID is null ");
	
			
			strBufferCount.append("select count(1) ");
			strBufferCount.append(" from cr_ach_capturing_dtl cacd");
			strBufferCount.append(" left join CR_DEAL_DTL cdd on cacd.DEAL_id = cdd.DEAL_ID");
			strBufferCount.append(" left join sec_user_m susm on susm.user_id = cacd.maker_id");
			strBufferCount.append(" where cacd.rec_status = 'P'  and cacd.LOAN_ID is null ");
			
			if (!(CommonFunction.checkNull(achCapturingVo.getHidDealNo())).trim().equalsIgnoreCase(""))
			{
				strBuffer.append(" and cacd.DEAL_ID = "+CommonFunction.checkNull(achCapturingVo.getHidDealNo()));
				strBufferCount.append(" and cacd.DEAL_ID = "+CommonFunction.checkNull(achCapturingVo.getHidDealNo()));
			}
			
			strBuffer.append(" and cdd.DEAL_BRANCH = '"+CommonFunction.checkNull(achCapturingVo.getBranchId())+"'");
			strBufferCount.append(" and cdd.DEAL_BRANCH = '"+CommonFunction.checkNull(achCapturingVo.getBranchId())+"'");
			}else{

				strBuffer.append("select cacd.ach_capturing_id, cacd.LOAN_id,cdd.LOAN_NO,customer_name, susm.user_name");
				strBuffer.append(" from cr_ach_capturing_dtl cacd");
				strBuffer.append(" left join CR_LOAN_DTL cdd on cacd.LOAN_id = cdd.LOAN_ID");
				strBuffer.append(" left join sec_user_m susm on susm.user_id = cacd.maker_id");
				strBuffer.append(" where cacd.rec_status in ('P') ");
		
				
				strBufferCount.append("select count(1) ");
				strBufferCount.append(" from cr_ach_capturing_dtl cacd");
				strBufferCount.append(" left join CR_LOAN_DTL cdd on cacd.LOAN_id = cdd.LOAN_ID");
				strBufferCount.append(" left join sec_user_m susm on susm.user_id = cacd.maker_id");
				strBufferCount.append(" where cacd.rec_status in ('P') ");
				
				if (!(CommonFunction.checkNull(achCapturingVo.getHidDealNo())).trim().equalsIgnoreCase(""))
				{
					strBuffer.append(" and cacd.LOAN_ID = "+CommonFunction.checkNull(achCapturingVo.getHidDealNo()));
					strBufferCount.append(" and cacd.LOAN_ID = "+CommonFunction.checkNull(achCapturingVo.getHidDealNo()));
				}
				
				strBuffer.append(" and cdd.LOAN_BRANCH = '"+CommonFunction.checkNull(achCapturingVo.getBranchId())+"'");
				strBufferCount.append(" and cdd.LOAN_BRANCH = '"+CommonFunction.checkNull(achCapturingVo.getBranchId())+"'");
				
				
			}
			logger.info("ACH Search List Query : "  + strBuffer.toString());
			ArrayList ACHStatusList = ConnectionDAO.sqlSelect(strBuffer.toString());
		    int recCount = Integer.parseInt(ConnectionDAO.singleReturn(strBufferCount.toString()));
			strBuffer=null;
			ACHCapturingVo achCapturingVoSearch;
			for(int i=0;i<ACHStatusList.size();i++){
			ArrayList data=(ArrayList)ACHStatusList.get(i);
				if(data.size()>0)	{
					achCapturingVoSearch = new ACHCapturingVo();
					achCapturingVoSearch.setHidDealNo((CommonFunction.checkNull(data.get(1))).trim());
					achCapturingVoSearch.setTxtDealNo("<a href=achCapturingAction.do?method=openModifyACHCapturing&achCapturingId="
							+CommonFunction.checkNull(CommonFunction.checkNull(data.get(0))).trim()+ ">"+
							(CommonFunction.checkNull(data.get(2))).trim()+"</a>");
					achCapturingVoSearch.setTxtCustomerName((CommonFunction.checkNull(data.get(3))).trim());
					achCapturingVoSearch.setMakerId((CommonFunction.checkNull(data.get(4))).trim());
					achCapturingVoSearch.setTotalRecordCount(recCount);
					list.add(achCapturingVoSearch);
				}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}


public boolean saveNewACHTrackingRecordList(ACHCapturingVo achCapturingVo) {
	logger.info("Inside saveNewACHTrackingRecordList of ACHCapturingDAOImpl");
	String statusProc=null;
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	String s1="";
	String s2="";
	boolean status = false; 
	StringBuffer bufInsSql =new StringBuffer();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	ArrayList qryList=new ArrayList();
	try{
		
	
		bufInsSql.append("insert into CR_ACH_TRACKING_DTL(ACH_CAPTURING_ID,ACH_STATUS ,ACH_REMARKS,VENDOR_NAME,SENT_DATE,LOT_NUMBER,RECEIVED_DATE,RECEIVED_STATUS,MAKER_ID,MAKER_DATE, REC_STATUS,UMRN_NO) values (?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,?,?,STR_TO_DATE(?, '"+dateFormatWithTime+"'),?,?)");
				
		if((CommonFunction.checkNull(achCapturingVo.getHidAchCapturingId())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getHidAchCapturingId()).trim());
		
		if((CommonFunction.checkNull(achCapturingVo.getSelACHStatus())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getSelACHStatus()).trim());
		
		if((CommonFunction.checkNull(achCapturingVo.getTxtRemarks())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getTxtRemarks()).trim());
		
		if((CommonFunction.checkNull(achCapturingVo.getHidselVendorName())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getHidselVendorName()).trim());
		
		if((CommonFunction.checkNull(achCapturingVo.getDteSentDate())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getDteSentDate()).trim());
		
		
		if((CommonFunction.checkNull(achCapturingVo.getTxtLotNo())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getTxtLotNo()).trim());
		
		
		if((CommonFunction.checkNull(achCapturingVo.getDteReceivedDate())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getDteReceivedDate()).trim());
		
		
		if((CommonFunction.checkNull(achCapturingVo.getSelACHReceivedStatus())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getSelACHReceivedStatus()).trim());
		
		
		if((CommonFunction.checkNull(achCapturingVo.getMakerId())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getMakerId()).trim());
		
		if((CommonFunction.checkNull(achCapturingVo.getMakerDate())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getMakerDate()).trim());
		
		insertPrepStmtObject.addString("P");
		
		if((CommonFunction.checkNull(achCapturingVo.getTxtUMRN())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((achCapturingVo.getTxtUMRN()).trim());
		
		insertPrepStmtObject.setSql(bufInsSql.toString());
		qryList.add(insertPrepStmtObject);
		status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		
		
		if((CommonFunction.checkNull(achCapturingVo.getSelACHReceivedStatus()).trim().equalsIgnoreCase("A")))
		{
			in.add(CommonFunction.checkNull(achCapturingVo.getHidAchCapturingId()).trim());
			in.add("ACH");
			out.add(s1);
			out.add(s2);
			logger.info("call Proc SP_ACH_INSTRUMENT_CAPTURING ("+in.toString()+","+out.toString()+")");
			outMessages=(ArrayList) ConnectionDAO.callSP("SP_ACH_INSTRUMENT_CAPTURING",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
		    logger.info("s1  : "+s1);
		    logger.info("s2  : "+s2);
			
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}finally
	{
		
		statusProc=null;
		in.clear();
		in=null;
		out.clear();
		out=null;
		outMessages.clear();
		outMessages=null;
	}
	return status;
   }

public ArrayList fetchSavedACHTrackingRecordList( String achCapturingId)
{
	logger.info("Inside fetchSavedACHTrackingRecord of ACHCapturingDAOImpl");
	
	ArrayList<Object> list=new ArrayList<Object>();
	try{
		StringBuilder strBuffer=new StringBuilder();
		StringBuilder strBufferCount=new StringBuilder();
		strBuffer.append("select catd.ACH_TRACKING_ID , gmStatus.description ,catd.ACH_REMARKS,catd.VENDOR_NAME,"
				+ " gm.description as vendor_desc, DATE_FORMAT(catd.SENT_DATE,'%d-%m-%Y'),catd.LOT_NUMBER,DATE_FORMAT(catd.RECEIVED_DATE,'%d-%m-%Y'),gmReceivedStatus.DESCRIPTION,catd.UMRN_NO ");
		strBuffer.append("from CR_ACH_TRACKING_DTL catd ");
		strBuffer.append("left join generic_master gm on gm.GENERIC_KEY = 'ACH_VENDOR_NAME' AND GM.VALUE = CATD.VENDOR_NAME ");
		strBuffer.append("left join generic_master gmStatus on gmStatus.GENERIC_KEY = 'ACH_ach_Status' AND gmStatus.VALUE = CATD.ACH_STATUS ");
		strBuffer.append("left join generic_master gmReceivedStatus on gmReceivedStatus.GENERIC_KEY = 'ACH_Received_Status' AND gmReceivedStatus.VALUE = CATD.RECEIVED_STATUS ");
		strBuffer.append("where ach_capturing_id = '"+achCapturingId+"' ");

		logger.info("ACH Search List Query : "  + strBuffer.toString());
		
		strBufferCount.append("select count(1) ");
		strBufferCount.append("from CR_ACH_TRACKING_DTL catd ");
		strBufferCount.append("left join generic_master gm on gm.GENERIC_KEY = 'ACH_VENDOR_NAME' AND GM.VALUE = CATD.VENDOR_NAME ");
		strBufferCount.append("left join generic_master gmStatus on gmStatus.GENERIC_KEY = 'ACH_ach_Status' AND gmStatus.VALUE = CATD.ACH_STATUS ");
		strBufferCount.append("left join generic_master gmReceivedStatus on gmReceivedStatus.GENERIC_KEY = 'ACH_Received_Status' AND gmReceivedStatus.VALUE = CATD.RECEIVED_STATUS ");
		strBufferCount.append("where ach_capturing_id = '"+achCapturingId+"' ");
		
		logger.info("ACH Search List Query : "  + strBufferCount.toString());
		
		ArrayList ACHStatusList = ConnectionDAO.sqlSelect(strBuffer.toString());
	    int recCount = Integer.parseInt(ConnectionDAO.singleReturn(strBufferCount.toString()));
	    
	    strBuffer=null;
		ACHCapturingVo achCapturingVoSearch;
		for(int i=0;i<ACHStatusList.size();i++){
		ArrayList data=(ArrayList)ACHStatusList.get(i);
			if(data.size()>0)	{
				achCapturingVoSearch = new ACHCapturingVo();
				achCapturingVoSearch.setHidAchTrackingId((CommonFunction.checkNull(data.get(0))).trim());
				achCapturingVoSearch.setSelACHStatus((CommonFunction.checkNull(data.get(1))).trim());
				achCapturingVoSearch.setTxtRemarks((CommonFunction.checkNull(data.get(2))).trim());
				achCapturingVoSearch.setHidselVendorName((CommonFunction.checkNull(data.get(3))).trim());
				achCapturingVoSearch.setTxtselVendorName((CommonFunction.checkNull(data.get(4))).trim());
				achCapturingVoSearch.setDteSentDate((CommonFunction.checkNull(data.get(5))).trim());
				achCapturingVoSearch.setTxtLotNo((CommonFunction.checkNull(data.get(6))).trim());
				achCapturingVoSearch.setDteReceivedDate((CommonFunction.checkNull(data.get(7))).trim());
				achCapturingVoSearch.setSelACHReceivedStatus((CommonFunction.checkNull(data.get(8))).trim());
				achCapturingVoSearch.setTxtUMRN((CommonFunction.checkNull(data.get(9))).trim());
				achCapturingVoSearch.setTotalRecordCount(recCount);
							
				list.add(achCapturingVoSearch);
			}
		data.clear();
		data=null;
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return list;
}


	public boolean updateACHCapturingData(ACHCapturingVo achCapturingVo)
	{
		logger.info("Inside updateACHCapturingData of ACHCapturingDAOImpl");
		boolean status = false; 
		StringBuffer bufInsSql =new StringBuffer();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList qryList=new ArrayList();
		try{
			bufInsSql.append("update cr_ach_capturing_dtl set customer_name=?,ach_date=STR_TO_DATE(?, '"+dateFormatWithTime+"'),"
					+ "hereby_Authorize=?,Bank_account_no=?,bank_id=?,branch_id=?,micr=?,ifsc=?,amount_rs=?,total_amount=?,"
					+ "reference_no=?,reference_no2=?,phone_no=?,email_id=?,frequency=?,from_date=STR_TO_DATE(?, '"+dateFormatWithTime+"'),"
					+ "to_date=STR_TO_DATE(?, '"+dateFormatWithTime+"'), SPONSOR_BANK_CODE=?,UTILITY_CODE=?,TO_DEBIT=?,"
					+ "DEBIT_TYPE=?,FIXED_MAX_AMOUNT=?,NAME_ACCOUNT_HOLDER=?,ACH_DECISION=? "
					+ "WHERE ach_capturing_id = ?");

			logger.info("Update Query Is : " + bufInsSql.toString());
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtCustomerName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtCustomerName()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getDteDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getDteDate()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtWeHerebyAuthorize())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtWeHerebyAuthorize()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtBankAccountNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtBankAccountNo()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getHidBankName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getHidBankName()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getHidBankBranchName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getHidBankBranchName()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtMicr())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtMicr()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtIfsc())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtIfsc()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtLoanAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtLoanAmount()).replaceAll(",","").trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtTotalAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtTotalAmount()).replaceAll(",","").trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtReferenceNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtReferenceNo()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtReferenceNo2())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtReferenceNo2()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtPhoneNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtPhoneNo()).trim());
			

			if((CommonFunction.checkNull(achCapturingVo.getTxtEmailId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtEmailId()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelFrequency())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelFrequency()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getDteFromDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getDteFromDate()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getDteToDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getDteToDate()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtSponsorBankCode())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtSponsorBankCode()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtUtilityCode())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtUtilityCode()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelToDebit())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelToDebit()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelDebitType())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelDebitType()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelFixedMaximumAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelFixedMaximumAmount()).replaceAll(",","").trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtNameAccountHolder())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtNameAccountHolder()).trim());
			//pooja code start for ACH Decidion
			if((CommonFunction.checkNull(achCapturingVo.getAchDecision())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getAchDecision()).trim());
			//pooja code end for ACH Decidion
			if((CommonFunction.checkNull(achCapturingVo.getHidAchCapturingId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getHidAchCapturingId()).trim());
			logger.info("ID:-"+achCapturingVo.getHidDealNo());
		
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	
	
	
	
	//added by rohit sahay
	public boolean updateACHCapturingDataforExist(ACHCapturingVo achCapturingVo)
	{
		logger.info("Inside updateACHCapturingDataForExist of ACHCapturingDAOImpl");
		boolean status = false; 
		StringBuffer bufInsSql =new StringBuffer();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList qryList=new ArrayList();
		String generatedAchCapId="";
		try{
			bufInsSql.append("update cr_ach_capturing_dtl set customer_name=?,ach_date=STR_TO_DATE(?, '"+dateFormatWithTime+"'),"
					+ "hereby_Authorize=?,Bank_account_no=?,bank_id=?,branch_id=?,micr=?,ifsc=?,amount_rs=?,total_amount=?,"
					+ "reference_no=?,reference_no2=?,phone_no=?,email_id=?,frequency=?,from_date=STR_TO_DATE(?, '"+dateFormatWithTime+"'),"
					+ "to_date=STR_TO_DATE(?, '"+dateFormatWithTime+"'), SPONSOR_BANK_CODE=?,UTILITY_CODE=?,TO_DEBIT=?,"
					+ "DEBIT_TYPE=?,FIXED_MAX_AMOUNT=?,NAME_ACCOUNT_HOLDER=?,ACH_DECISION=?,Rec_status='P' "
					+ "WHERE loan_id = ?");

			logger.info("Update Query Is : " + bufInsSql.toString());
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtCustomerName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtCustomerName()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getDteDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getDteDate()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtWeHerebyAuthorize())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtWeHerebyAuthorize()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtBankAccountNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtBankAccountNo()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getHidBankName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getHidBankName()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getHidBankBranchName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getHidBankBranchName()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtMicr())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtMicr()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtIfsc())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtIfsc()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtLoanAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtLoanAmount()).replaceAll(",","").trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtTotalAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtTotalAmount()).replaceAll(",","").trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtReferenceNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtReferenceNo()).trim());
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtReferenceNo2())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtReferenceNo2()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtPhoneNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtPhoneNo()).trim());
			

			if((CommonFunction.checkNull(achCapturingVo.getTxtEmailId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtEmailId()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelFrequency())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelFrequency()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getDteFromDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getDteFromDate()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getDteToDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getDteToDate()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtSponsorBankCode())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtSponsorBankCode()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtUtilityCode())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtUtilityCode()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelToDebit())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelToDebit()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelDebitType())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelDebitType()).trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getSelFixedMaximumAmount())).replaceAll(",","").trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getSelFixedMaximumAmount()).replaceAll(",","").trim());
			
			
			if((CommonFunction.checkNull(achCapturingVo.getTxtNameAccountHolder())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getTxtNameAccountHolder()).trim());
			//pooja code start for ACH Decidion
			if((CommonFunction.checkNull(achCapturingVo.getAchDecision())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getAchDecision()).trim());
			//pooja code end for ACH Decidion
			 
			
			
			
			if((CommonFunction.checkNull(achCapturingVo.getHidDealNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingVo.getHidDealNo()).trim());
			logger.info("ID:-"+achCapturingVo.getHidDealNo());
			logger.info("Update Query1111111 Is : " + bufInsSql.toString());
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			StringBuilder strBufferAchCapId=new StringBuilder();
			strBufferAchCapId.append("select max(ach_capturing_id) from cr_ach_capturing_dtl where LOAN_ID = "+achCapturingVo.getHidDealNo());
			generatedAchCapId = ConnectionDAO.singleReturn(strBufferAchCapId.toString());
			logger.info("max(ach_capturing_id):-"+generatedAchCapId);
			achCapturingVo.setHidAchCapturingId(generatedAchCapId);
			logger.info("ID2:-"+achCapturingVo.getHidDealNo());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

	
	
	
	//ended by rohit sahay
	
	
	
	
	
	
	
	
	
	
	
	 public boolean deleteACHDataList(String achCapturingId) {
		logger.info("Inside deleteACHDataList of ACHCapturingDAOImpl");
		
		boolean status = false; 
		StringBuffer bufInsSql =new StringBuffer();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList qryList=new ArrayList();
		boolean status1 = false; 
		StringBuffer bufInsSql1 =new StringBuffer();
		PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
		ArrayList qryList1=new ArrayList();
		try{
			
			bufInsSql.append("delete from  cr_ach_capturing_dtl  where ach_capturing_id = ?");
			
			if((CommonFunction.checkNull(achCapturingId)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((achCapturingId).trim());
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList.add(insertPrepStmtObject);
			status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
			
	bufInsSql1.append("delete from  cr_ach_tracking_dtl  where ach_capturing_id = ?");
			
			if((CommonFunction.checkNull(achCapturingId)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject1.addNull();
			else
				insertPrepStmtObject1.addString((achCapturingId).trim());
			
			insertPrepStmtObject1.setSql(bufInsSql1.toString());
			qryList1.add(insertPrepStmtObject1);
			status1 =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	 //Rohit Changes Starts for validate and check that ach is sended to vender or not
	 public boolean checkSendtovender(ACHCapturingVo achCapturingVo){
			logger.info("Inside checkSendtovender of ACHCapturingDAOImpl");
			boolean status = false; 
			
			StringBuffer bufInsSql =new StringBuffer();
			try{
				
				bufInsSql.append("select count(1) from CR_ACH_TRACKING_DTL catd left join generic_master gm on gm.GENERIC_KEY = 'ACH_VENDOR_NAME' AND GM.VALUE = CATD.VENDOR_NAME ");
				bufInsSql.append("left join generic_master gmStatus on gmStatus.GENERIC_KEY = 'ACH_ach_Status' AND gmStatus.VALUE = CATD.ACH_STATUS ");
				bufInsSql.append("left join generic_master gmReceivedStatus on gmReceivedStatus.GENERIC_KEY = 'ACH_Received_Status' AND gmReceivedStatus.VALUE = CATD.RECEIVED_STATUS ");
				bufInsSql.append(" where ach_capturing_id = '"+achCapturingVo.getHidAchCapturingId()+"' and gmStatus.description='Sent for Registration' ");
				
				 int recCount = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSql.toString()));
				 if(recCount==0)
				 {
					 status=false; 
				 }
				 else
				 {
					 status=true; 
				 }
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return status;
	 }
	//Rohit Changes end for validate and check that ach is sended to vender or not
}