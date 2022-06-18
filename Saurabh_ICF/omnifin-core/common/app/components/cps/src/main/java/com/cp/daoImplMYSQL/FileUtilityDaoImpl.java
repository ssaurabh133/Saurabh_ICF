package com.cp.daoImplMYSQL;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.cp.dao.FileUtilityDao;
import com.cp.process.ConstantValue;
import com.cp.util.FormatUtility;
import com.cp.vo.CodeDescVo;

public class FileUtilityDaoImpl implements FileUtilityDao {
	private static final Logger logger = Logger.getLogger(FileUtilityDaoImpl.class.getName());
	@Override
	public String getDocPath(String docId) {
		logger.info("getDocPath--------");
		String qry = "select document_path from cr_uploaded_documents where document_id = '"+docId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public String getCustomerRoleType(String caseId) {
		logger.info("getCustomerRoleType--------");
		String qry = "select deal_customer_role_type from cr_deal_customer_role where deal_id  = '"+caseId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}
	@Override
	public String getCustomerRoleType(String caseId,String customerId) {
		logger.info("getCustomerRoleType--------");
		String qry = "select deal_customer_role_type from cr_deal_customer_role where deal_id  = '"+caseId+"' and deal_customer_Id = '"+customerId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}
	@Override
	public String getFileName(String docId) {
		logger.info("getFileName--------");
		String qry = "select file_name from cr_uploaded_documents where document_id = '"+docId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public String getCustomerID(String caseId) {
		logger.info("getCustomerID--------");
		String qry = "select deal_customer_id from cr_deal_dtl where deal_id = '"+caseId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public String getNomenClatureFilePath() {
		logger.info("getNomenClatureFilePathForHeading--------");
		String qry = "select parameter_value from parameter_mst where parameter_key = 'NOMEN_CLATURE_FILE_PATH'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public String getNomenClatureFileForFormatHeading(String str) {
		logger.info("getNomenClatureFileForFormatHeading--------");
		String qry = "select parameter_value from parameter_mst where parameter_key = '"+str+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public String getCustomerNameByCustomerId(String customerId) {
		logger.info("getCustomerNameByCustomerId--------");
		String qry = "select customer_name from cr_deal_customer_m where customer_id = '"+customerId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}
	
	@Override
	public String getBankName(String bankId) {
		logger.info("getBankName--------");
		String qry = "select bank_name from com_bank_m where bank_id = '"+bankId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}
	@Override
	public String getParameterMSTInfo(String key) {
		logger.info("getApplicableFromDate--------");
		
		String qry = "select parameter_value from parameter_mst where parameter_key = '"+key+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public String getApplicationReferenceNo(String caseId) {
		logger.info("getApplicationReferenceNo--------");
		
		String qry = "select deal_no from cr_deal_dtl where deal_id = '"+caseId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public String getProductId(String caseId) {
		logger.info("getProductId--------");
		
		String qry = "select DEAL_PRODUCT from cr_deal_loan_dtl where deal_id = '"+caseId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public String checkWorkFlowStatus(String CaseId, String stageId) {
		logger.info("checkWorkFlowStatus--------");
		
		String qry = "select ifnull(status,'P')status from cr_deal_movement_dtl where deal_id = '"+CaseId+"' and deal_stage_id = '"+stageId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public String getDocType(String documentId) {
		logger.info("getDocType--------");
		
		String qry = "select document_type from cr_uploaded_documents where document_id = '"+documentId+"' ";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public ArrayList getUploadErrorReasons(String caseId, String docId) {
		logger.info("getUploadErrorReasons::::::::");
		ArrayList list = new ArrayList() ;
		String countQuery = "select count(1) from cr_financial_data_temp where deal_id='"+CommonFunction.checkNull(caseId)+"' and DOCUMENT_ID='"+CommonFunction.checkNull(docId)+"' ";
		String countValue = ConnectionDAO.singleReturn(countQuery);
		StringBuffer query=new StringBuffer();
		query.append(" select APPLICATION_REFERENCE_NO,ifnull(PARAM_NAME,'')PARAM_NAME,ifnull(PARAM_VALUE,'')PARAM_VALUE,ifnull(REJECT_REASON,'')REJECT_REASON,if(REJECT_FLAG,'Y','YES')REJECT_FLAG,(select user_name from sec_user_m where  user_Id=a.MAKER_ID)UPLOADED_BY ");
		query.append(" from cr_financial_data_temp A join cr_deal_dtl B on b.deal_id=a.deal_ID ");
		query.append(" where REJECT_FLAG='Y' and b.deal_id='"+CommonFunction.checkNull(caseId)+"' and document_id='"+CommonFunction.checkNull(docId)+"' ");
		logger.info("getUploadErrorReasons Query ------"+query);
		try {
			if(!countValue.equalsIgnoreCase("0"))
			list = 	ConnectionDAO.sqlColumnWithResult(query.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList getUploadErrorReasonsBS(String caseId, String docId) {
		logger.info("getUploadErrorReasonsBS::::::::");
		ArrayList list = new ArrayList() ;
		String countQuery = "select count(1) from cr_bank_stmnt_dtl_temp where deal_id='"+CommonFunction.checkNull(caseId)+"' and DOCUMENT_ID='"+CommonFunction.checkNull(docId)+"' ";
		String countValue = ConnectionDAO.singleReturn(countQuery);
		StringBuffer query=new StringBuffer();
		query.append(" select REF_ID as APPLICATION_REFERENCE_NO,case ENTITY_TYPE when 'PRAPPL' then 'Applicant' when 'COAPPL' then 'Co-Applicant' when 'GUARANTOR' then 'Guarantor' end as ENTITY_TYPE, ");
		query.append(" (select BANK_NAME from com_bank_m cbm where cbm.BANK_ID=a.BANK_NAME)BANK_NAME,BANK_BRANCH,ifnull(ACCOUNT_NO,'')ACCOUNT_NO,STATMENT_DATE, ");
		query.append(" (select user_name from sec_user_m where  user_Id=a.MAKER_ID)UPLOADED_BY, ");
		query.append(" ifnull(TOTAL_CR,0)TOTAL_CR,ifnull(TOTAL_DR,0)TOTAL_DR,ifnull(BALANCE_AMOUNT,0)BALANCE_AMOUNT,NARRATION,ifnull(REJECT_REASON,'')REJECT_REASON,UPLOAD_FLAG,ifnull(CHEQUE_NO,'')CHEQUE_NO,ifnull(TRANSACTION_ID,'')TRANSACTION_ID,VALUE_DATE,ifnull(TRANSACTION_AMOUNT,'')TRANSACTION_AMOUNT ");
		query.append("from cr_bank_stmnt_dtl_temp a where UPLOAD_FLAG='Y' and a.deal_id='"+CommonFunction.checkNull(caseId)+"' and DOCUMENT_ID='"+CommonFunction.checkNull(docId)+"' ");
		logger.info("getUploadErrorReasonsBS Query ------"+query);
		try {
			if(!countValue.equalsIgnoreCase("0"))
			list = 	ConnectionDAO.sqlColumnWithResult(query.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList saveTargetValuesTmp(String caseId, String makerDate,
			String makerId) {
		logger.info("saveTargetValuesTmp-------------");
		ArrayList in = new ArrayList();
		ArrayList out = new ArrayList();
		ArrayList outMessage = new ArrayList();
		ArrayList list =null;
		String s1=null;
		String s2= null;
		in.add(caseId);
		in.add(FormatUtility.dateFormat(makerDate, "yyyy-MM-dd"));
		in.add(makerId);
		out.add(s1);
		out.add(s2);
		 try {
			list = (ArrayList)ConnectionDAOforEJB.callSP("target_values_tmp", in, out);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList generateBankingProgram(String caseId, String makerDate,
			String makerId) {
		
		logger.info("generateBankingProgram-------------");
		ArrayList in = new ArrayList();
		ArrayList out = new ArrayList();
		ArrayList outMessage = new ArrayList();
		ArrayList list =null;
		String s1=null;
		String s2= null;
		in.add(caseId);
		in.add(FormatUtility.dateFormat(makerDate, "yyyy-MM-dd"));
		in.add(makerId);
		out.add(s1);
		out.add(s2);
		 try {
			list = (ArrayList)ConnectionDAOforEJB.callSP("banking_program", in, out);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getFinancialYearFromCustomerDemeographics(String caseId) {
		logger.info("getFinancialYearFromCustomerDemeographics-------------");
		String qry = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' ";
		logger.info("qry-----"+qry);		
		String value = ConnectionDAO.singleReturn(qry);
				
		return value;
	}
	@Override
	public String getSchemeID(String caseId) {
		logger.info("getSchemeID--------");
		
		String qry = "select scheme from cr_deal_dtl where deal_id = '"+caseId+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}

	@Override
	public ArrayList getRTRObligationValue(String caseId,String sheetName) {
		logger.info("getRTRObligationValue-------");
		ArrayList mainList = new ArrayList();
		ArrayList cflList = null;
		ArrayList notCLFList = null;
		StringBuffer buffer = new StringBuffer("select ");
		StringBuffer notCLFBuffer = new StringBuffer("select ");
		
		
		if(CommonFunction.checkNull(sheetName).equalsIgnoreCase("obligation table"))
		{
		buffer.append("INSTITUTION_NAME,customer_name,OBLIGATION_TYPE,FINANCED_AMOUNT,LOAN_TYPE,EMI,LOAN_TENURE,Loan_start_date,MOB,tenure,OUTSTANDING_AMOUNT,status,Case OBLIGATION_TO_BE_CONSIDER when 'Y' Then 'Yes' when 'N' then 'No' end OBLIGATION_TO_BE_CONSIDER");
		}
		else if(CommonFunction.checkNull(sheetName).equalsIgnoreCase("Top Up"))
		{
		buffer.append("@a:=@a+1 as a,INSTITUTION_NAME,OBLIGATION_TYPE,FINANCED_AMOUNT,EMI,LOAN_TENURE,EMI_PAID,OUTSTANDING_AMOUNT,EMI_BOUNCED,EMI_Eligible_for_Mulitplier,FOR_DEBT_CONSOLIDATION,'' as u,'' as v,'' as w, MOB_TOPUP,'' as y, CLOSED_LIVE");
		}
		
		else if(CommonFunction.checkNull(sheetName).equalsIgnoreCase("RTR-DC"))
		{
			buffer.append("@a:=@a+1 as a,INSTITUTION_NAME,customer_name,OBLIGATION_TYPE,FINANCED_AMOUNT,LOAN_TYPE,EMI,LOAN_TENURE,DATE_FORMAT(Loan_start_date,'%d/%m%/%Y')Loan_start_date,MOB,tenure,OUTSTANDING_AMOUNT,status,vanilla_program_status,Case OBLIGATION_TO_BE_CONSIDER when 'Y' Then 'Yes' when 'N' then 'No' end OBLIGATION_TO_BE_CONSIDER");
		}
			
		buffer.append(" from cr_obligation_analysis_dtl o left join cr_deal_customer_m c on c.customer_id= o.deal_customer_id, (select @a:=0) as a where o.deal_id = '"+caseId+"'  limit 20");
		
		logger.info("query--------"+buffer.toString());
		//logger.info("notCLFBuffer query--------"+notCLFBuffer.toString());
		ArrayList returnValueList=new ArrayList();
		logger.info("query -------------"+buffer.toString());
		try {
			 cflList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cflList;
	}

	@Override
	public ArrayList getGenericMasterList(String genericKey) {
		
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
		 query.append("select VALUE,DESCRIPTION from generic_master where GENERIC_KEY='"+genericKey+"' and rec_status='A' ");
		ArrayList getGenericMasterList = ConnectionDAO.sqlSelect(query.toString());
		logger.info("getGenericMasterList "+getGenericMasterList.size());
		logger.info("query--------------------------> "+query);
		
		query=null;
		
		for(int i=0;i<getGenericMasterList.size();i++){
			ArrayList data=(ArrayList)getGenericMasterList.get(i);
			if(data.size()>0)	{
			CodeDescVo vo=new CodeDescVo();
			vo.setId((CommonFunction.checkNull(data.get(0))).trim());
			vo.setName((CommonFunction.checkNull(data.get(1))).trim());
			list.add(vo);
		}
			data.clear();
			data=null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public String getProductCamTemplateId(String productId) {
		logger.info("getProductCamTemplateId--------");
		
		String qry = "select cam_template_id from cr_product_cam_mapping_m where product_id = '"+productId+"' and rec_status='A' ";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}
	
	public ArrayList getTopUpValue(String caseId,String sheetName) {
		logger.info("getTopUpValue-----------------");
		ArrayList mainList = new ArrayList();
		ArrayList cflList = null;
		ArrayList notCLFList = null;
		StringBuffer buffer = new StringBuffer("select ");
		StringBuffer notCLFBuffer = new StringBuffer("select ");
		
		
	
		buffer.append("@a:=@a+1 as a,INSTITUTION_NAME,OBLIGATION_TYPE,round(FINANCED_AMOUNT,2),round(EMI,2),LOAN_TENURE,EMI_PAID,OUTSTANDING_AMOUNT,EMI_BOUNCED,EMI_Eligible_for_Mulitplier,FOR_DEBT_CONSOLIDATION,'' as u,'' as v,'' as w, MOB_TOPUP,'' as y, CLOSED_LIVE");
	
			
		buffer.append(" from cr_obligation_analysis_dtl, (select @a:=0) as a where deal_id= '"+caseId+"' and ifnull(OBLIGATION_TO_BE_CONSIDER,'Y')='Y' ");
		
		buffer.append(" and (INSTITUTION_NAME like '%Capital First%' or INSTITUTION_NAME like '%Future Capital%' or INSTITUTION_NAME like '%CFL%'or INSTITUTION_NAME like 'FCH') ");
	
		logger.info("query--------"+buffer.toString());
		ArrayList returnValueList=new ArrayList();
		logger.info("query -------------"+buffer.toString());
		try {
			 cflList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
			/* if(cflList!=null && cflList.size()>0)
			 {
				for(int i=0;i<cflList.size();i++) 
				{
					mainList.add(cflList.get(i));
				}
			 }
				*/
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cflList;
	}
	@Override
	public String getGenericMasterDesc(String genericKey,String value) {
		logger.info("getGenericMasterDesc--------");
		
		String qry = "select description from  generic_master where generic_key = '"+genericKey+"' and value='"+value+"'";
		logger.info("query--------"+qry);
		String returnValue = ConnectionDAO.singleReturn(qry);
		return returnValue;
	}
}
